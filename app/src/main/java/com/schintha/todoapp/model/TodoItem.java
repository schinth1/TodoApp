package com.schintha.todoapp.model;

/**
 * Created by schinth1 on 6/21/16.
 */
public class TodoItem {

    private String body;
    private Priority priority;


    public TodoItem(String body, Priority priority)
    {
        this.body = body;
        this.priority = priority;
    }

    public String getBody() {
        return body;
    }

    public Priority getPriority() { return priority; }

    public void setBody(String body) {
        this.body = body;
    }

    public void setPriority(Priority priority){
        this.priority = priority;
    }


}
