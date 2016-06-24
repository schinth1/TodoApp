package com.schintha.todoapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.activeandroid.query.Select;
import com.schintha.todoapp.model.TodoItem;
import com.schintha.todoapp.model.TodoModel;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private final int EDIT_REQUEST_CODE = 1;
    private final int EDIT_REQUEST_OK = 1;

    private final int ADD_REQUEST_CODE = 2;
    private final int ADD_REQUEST_OK = 2;

    ArrayList<TodoItem> todoItems = new ArrayList<TodoItem>();
    ArrayAdapter<TodoItem> aToDoAdapter;
    ListView lvItems;
    EditText etEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        populateArrayItems();
        lvItems = (ListView) findViewById(R.id.lvItems);
        lvItems.setAdapter(aToDoAdapter);
        etEditText = (EditText) findViewById(R.id.etEditText);
        lvItems.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long l) {
                TodoItem itemRemove = todoItems.get(position);
                todoItems.remove(position);
                aToDoAdapter.notifyDataSetChanged();
                deleteItem(itemRemove);
                return true;
            }
        });

        lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent editIntent = new Intent(MainActivity.this, EditItemActivity.class);
                editIntent.putExtra("edit_item", todoItems.get(position).getBody());
                editIntent.putExtra("position", position);
                startActivityForResult(editIntent, EDIT_REQUEST_CODE);
            }
        });


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    public void populateArrayItems(){
        readItems();
        aToDoAdapter = new ArrayAdapter<TodoItem>(this, android.R.layout.simple_list_item_1, todoItems);
        //aToDoAdapter = new CustomAdapter(this, todoItems);
    }

    private void readItems(){
        List<TodoModel> taskList = new Select().from(TodoModel.class).execute();
        todoItems = new ArrayList<TodoItem>();
        for(TodoModel task : taskList)
        {
            todoItems.add(new TodoItem(task.task));
        }
    }

    private void writeItem(TodoItem item){
        TodoModel row = new TodoModel(item.getBody());
        row.save();
    }

    private void updateItem(TodoItem oldItem, TodoItem newItem)
    {
        TodoModel row = new Select().from(TodoModel.class).where("Task = ?", oldItem.getBody()).executeSingle();
        row.task = newItem.getBody();
        row.save();
    }

    private void deleteItem(TodoItem deleteRow)
    {
        TodoModel row = new Select().from(TodoModel.class).where("Task = ?", deleteRow.getBody()).executeSingle();
        row.delete();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == EDIT_REQUEST_CODE && resultCode == EDIT_REQUEST_OK)
        {
            String task = data.getExtras().getString("save_item");
            //String newItem = new TodoItem(task, p);
            int position = data.getExtras().getInt("position");
            TodoItem itemOld = todoItems.get(position);
            TodoItem itemNew = new TodoItem(task);
            todoItems.set(position, itemNew);

            aToDoAdapter.notifyDataSetChanged();
            //modifyItemDB(taskToEdit, newItem);
            updateItem(itemOld, itemNew);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onAddItem(View view) {
        TodoItem item = new TodoItem(etEditText.getText().toString());
        aToDoAdapter.add(item);
        etEditText.setText("");
        writeItem(item);
    }

}
