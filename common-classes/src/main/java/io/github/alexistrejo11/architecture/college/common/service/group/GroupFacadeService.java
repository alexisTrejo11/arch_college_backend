package io.github.alexistrejo11.architecture.college.common.service.group;

import io.github.alexistrejo11.architecture.college.common.dto.Group.GroupDTO;
import io.github.alexistrejo11.architecture.college.common.utils.CustomPage;
import io.github.alexistrejo11.architecture.college.common.utils.response.Result;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface GroupFacadeService {
    CompletableFuture<Boolean> validateExisitingGroup(Long groupId);
    CompletableFuture<GroupDTO> getGroupById(Long groupId);
    CompletableFuture<GroupDTO> getCurrentGroupByKey(String groupKey);
    CompletableFuture<Result<Void>> takeSpot(Long groupId);
    CompletableFuture<Result<Void>> returnSpot(Long groupId);
    CompletableFuture<Result<List<GroupDTO>>> getGroupsByIds(List<Long> idList);
    CustomPage<GroupDTO> getCurrentGroups(int page, int size);
}
