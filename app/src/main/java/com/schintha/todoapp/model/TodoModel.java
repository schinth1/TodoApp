package com.schintha.todoapp.model;

/**
 * Created by schinth1 on 6/21/16.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

@Table(name = "Tasks", id = "_task")
public class TodoModel extends Model {

    @Column(name = "Task", unique = true, onUniqueConflict = Column.ConflictAction.REPLACE)
    public String task;

    @Column(name = "Priority"   )
    public Priority priority;

    public TodoModel() {
        super();
    }

    public TodoModel(String task, Priority priority)
    {
        super();
        this.task = task;
        this.priority = priority;
    }
}
