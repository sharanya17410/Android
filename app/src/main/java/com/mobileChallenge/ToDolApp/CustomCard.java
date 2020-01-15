package com.mobileChallenge.ToDolApp;

import android.widget.ImageView;

public class CustomCard {

    public String getPhotouri() {
        return photouri;
    }

    public void setPhotouri(String photouri) {
        this.photouri = photouri;
    }

    private String photouri;

    public String getTaskName() {
        return TaskName;
    }

    public void setTaskName(String taskName) {
        TaskName = taskName;
    }

    private String TaskName;

    public String getTaskDescription() {
        return TaskDescription;
    }

    public void setTaskDescription(String taskDescription) {
        TaskDescription = taskDescription;
    }

    private String TaskDescription;


    public CustomCard(String taskName,String taskDescription,String photouri){

        this.TaskName=taskName;
        this.TaskDescription=taskDescription;
        this.photouri=photouri;
    }

   // public CustomCard(String s){
    //    textView.setText(s);
    //}

    public String getTextView() {

        return TaskName;
    }


    public void setTextView(String textView) {

        this.TaskName = textView;
    }




}
