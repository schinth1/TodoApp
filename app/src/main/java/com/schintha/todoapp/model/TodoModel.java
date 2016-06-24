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

//    private static final int DATABASE_VERSION = 1;
//    private static final String DATABASE_NAME = "todoItem.db";
//    private static final String TABLE_TODO = "todo_items";
//    private static final String KEY_ID = "id";
//    private static final String KEY_BODY = "body";
//    //private static final String KEY_PRIORITY = "priority";
//
//    public TodoItemDatabase(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
//        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
//    }
//
//    //This is called when database is created
//    @Override
//    public void onCreate(SQLiteDatabase db) {
//        final String CREATE_TODO_TABLE = "CREATE TABLE " + TABLE_TODO +
//                "(" +
//                    KEY_ID + " INTEGER PRIMARY KEY," +
//                    KEY_BODY + " TEXT," +
//                 //   KEY_PRIORITY + " INTEGER" +
//                ")";
//        db.execSQL(CREATE_TODO_TABLE);
//
//    }
//
//    // This is called when db is updated.
//    // example: adding new columns, index, constraints...
//    @Override
//    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//        // SQL for upgrading the tables
//        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TODO);
//        onCreate(db);
//
//    }
//
//    // This method adds item to the database
//    public void addTodoItem(TodoItem item)
//    {
//        SQLiteDatabase db = getWritableDatabase();
//
//        // It's a good idea to wrap our insert in a transaction. This helps with performance and ensures
//        // consistency of the database.
//        db.beginTransaction();
//        try {
//            // The user might already exist in the database (i.e. the same user created multiple posts).
//
//            ContentValues values = new ContentValues();
//            //values.put(KEY_ID, item.getId());
//            values.put(KEY_BODY, item.getBody());
//
//            // Notice how we haven't specified the primary key. SQLite auto increments the primary key column.
//            db.insertOrThrow(TABLE_TODO, null, values);
//            db.setTransactionSuccessful();
//        } catch (Exception e) {
//            //Log.d(TAG, "Error while trying to add post to database");
//        } finally {
//            db.endTransaction();
//        }
//    }
}
