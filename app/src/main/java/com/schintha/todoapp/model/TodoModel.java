package com.schintha.todoapp.model;

/**
 * Created by sc043016 on 6/21/16.
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

    public TodoModel() {
        super();
    }

    public TodoModel(String task)
    {
        super();
        this.task = task;
    }
}
