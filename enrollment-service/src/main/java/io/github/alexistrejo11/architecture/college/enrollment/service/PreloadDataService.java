package io.github.alexistrejo11.architecture.college.enrollment.service;

public interface PreloadDataService<T> {
    void clear();
    void preload(String processId);
    void startPreload(String processId);
    String getPreloadStatus(String processId);
}
