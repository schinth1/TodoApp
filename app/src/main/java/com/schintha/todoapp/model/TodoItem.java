package com.schintha.todoapp.model;

/**
 * Created by sc043016 on 6/21/16.
 */
public class TodoItem {

    //private int id;
    private String body;

    public TodoItem(String body)
    {
        //this.id = id;
        this.body = body;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

//    public int getId() {
//        return id;
//    }
//
//    public void setId(int id) {
//        this.id = id;
//    }
}
