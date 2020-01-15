package com.mobileChallenge.ToDolApp;

import android.graphics.Bitmap;

public class Task {

    public String getTaskId() {
        return TaskId;
    }

    public void setTaskId(String taskId) {
        TaskId = taskId;
    }

    private String TaskId;
    private String TaskName;
    private String SubTask;
    private String photo;

    public String getTaskName() {
        return TaskName;
    }

    public void setTaskName(String taskName) {
        TaskName = taskName;
    }

    public String getSubTask() {
        return SubTask;
    }

    public void setSubTask(String subTask) {
        SubTask = subTask;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photourl) {
        this.photo = photourl;
    }




}

