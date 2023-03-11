package model;

import java.sql.Date;

public class TaskModel {

    private int id;
    private String taskName;
    private String projectName;
    private String user;
    private int user_id;
    private Date startDate;
    private Date endDate;
    private String status;

//    public TaskModel() {
//
//    }
//
//    public TaskModel(int id, String taskName, String projectName, String user, int user_id, Date startDate, Date endDate, String status) {
//        this.id = id;
//        this.taskName = taskName;
//        this.projectName = projectName;
//        this.user = user;
//        this.user_id = user_id;
//        this.startDate = startDate;
//        this.endDate = endDate;
//        this.status = status;
//    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
