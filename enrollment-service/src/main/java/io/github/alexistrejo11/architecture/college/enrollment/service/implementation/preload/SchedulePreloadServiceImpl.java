package io.github.alexistrejo11.architecture.college.enrollment.service.implementation.preload;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import io.github.alexistrejo11.architecture.college.common.dto.Group.GroupDTO;
import io.github.alexistrejo11.architecture.college.common.service.group.GroupFacadeService;
import io.github.alexistrejo11.architecture.college.common.utils.CustomPage;
import io.github.alexistrejo11.architecture.college.enrollment.mappers.GroupMapper;
import io.github.alexistrejo11.architecture.college.enrollment.model.Preload.Group;
import io.github.alexistrejo11.architecture.college.enrollment.repository.GroupRepository;
import io.github.alexistrejo11.architecture.college.enrollment.service.PreloadDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Service
@Slf4j
public class SchedulePreloadServiceImpl implements PreloadDataService<Group> {

    private final GroupFacadeService groupFacadeService;
    private final GroupMapper groupMapper;
    private final GroupRepository groupRepository;
    private final Map<String, String> processStatus = new ConcurrentHashMap<>();

    @Autowired
    public SchedulePreloadServiceImpl(@Qualifier("GroupFacadeServiceImpl") GroupFacadeService groupFacadeService,
                                      GroupMapper groupMapper, GroupRepository groupRepository) {
        this.groupFacadeService = groupFacadeService;
        this.groupMapper = groupMapper;
        this.groupRepository = groupRepository;
    }

    @Override
    public void startPreload(String processId) {
        processStatus.put(processId, "Started");

        new Thread(() -> preload(processId)).start();
    }

    @Override
    public String getPreloadStatus(String processId) {
        return processStatus.get(processId);
    }

    @Override
    @Transactional
    public void preload(String processId) {
        int pageSize = 1;
        processStatus.put(processId, "Processing");

        try {
            List<Group> allGroups = getCurrentSchoolPeriodGroups(pageSize);

            saveGroups(allGroups);
            processStatus.put(processId, "Completed");

            log.info("Preloaded {} schedules into enrollment-service", allGroups.size());
        } catch (Exception e) {
            processStatus.put(processId, "Failed");

            log.error("Failed to preload schedules: {}", e.getMessage());
        }
    }

    @Override
    public void clear() {
        groupRepository.deleteAll();
    }

    private List<Group> getCurrentSchoolPeriodGroups(int pageSize)  {
        int page = 0;
        boolean hasMorePages = true;
        List<Group> allGroups = new ArrayList<>();

        while (hasMorePages) {
            CustomPage<GroupDTO> groupPage = groupFacadeService.getCurrentGroups(page, pageSize);
            List<Group> groups = groupPage.getContent().stream()
                    .map(groupMapper::dtoToEntity)
                    .toList();
            allGroups.addAll(groups);

            hasMorePages = groupPage.hasNext();
            page++;
        }

        return allGroups;
    }

    private void saveGroups(List<Group> groups) {
        groupRepository.deleteAll();
        groupRepository.saveAll(groups);
    }
}
