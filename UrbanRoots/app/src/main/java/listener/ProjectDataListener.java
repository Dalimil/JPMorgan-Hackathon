package listener;

import java.util.List;

import entity.Project;

public interface ProjectDataListener {
    void onSuccess(List<Project> projects);
    void onError();
}