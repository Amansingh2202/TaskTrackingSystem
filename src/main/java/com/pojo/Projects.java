package com.pojo;



import java.util.List;

public class Projects {

    private String projectId;
    private String projectName;
    private List<EnrolledIndividuals> enrolledIndividuals;
    private List<Tasks> tasks;

    public Projects() {}

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public List<EnrolledIndividuals> getEnrolledIndividuals() {
        return enrolledIndividuals;
    }

    public void setEnrolledIndividuals(List<EnrolledIndividuals> enrolledIndividuals) {
        this.enrolledIndividuals = enrolledIndividuals;
    }

    public List<Tasks> getTasks() {
        return tasks;
    }

    public void setTasks(List<Tasks> tasks) {
        this.tasks = tasks;
    }
}
