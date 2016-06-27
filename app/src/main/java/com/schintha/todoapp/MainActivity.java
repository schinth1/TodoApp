package com.schintha.todoapp;

import android.content.Intent;
import android.net.Uri;
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
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.schintha.todoapp.model.Priority;
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
    private final int ADD_REQUEST_OK = 1;

    ArrayList<TodoItem> todoItems = new ArrayList<TodoItem>();
    ArrayAdapter<TodoItem> aToDoAdapter;
    ListView lvItems;
    //EditText etEditText;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        populateArrayItems();
        lvItems = (ListView) findViewById(R.id.lvItems);
        lvItems.setAdapter(aToDoAdapter);
        //etEditText = (EditText) findViewById(R.id.etEditText);
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
                editIntent.putExtra("edit_priority", todoItems.get(position).getPriority().ordinal());
                editIntent.putExtra("position", position);
                startActivityForResult(editIntent, EDIT_REQUEST_CODE);
            }
        });


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    public void populateArrayItems() {
        readItems();
        aToDoAdapter = new CustomAdapter(this, todoItems);
    }

    private void readItems() {
        List<TodoModel> taskList = new Select().from(TodoModel.class).execute();
        todoItems = new ArrayList<TodoItem>();
        for (TodoModel task : taskList) {
            todoItems.add(new TodoItem(task.task, task.priority));
        }
    }

    private void writeItem(TodoItem item) {
        TodoModel row = new TodoModel(item.getBody(), item.getPriority());
        row.save();
    }

    private void updateItem(TodoItem oldItem, TodoItem newItem) {
        TodoModel row = new Select().from(TodoModel.class).where("Task = ?", oldItem.getBody()).executeSingle();
        row.task = newItem.getBody();
        row.save();
    }

    private void deleteItem(TodoItem deleteRow) {
        TodoModel row = new Select().from(TodoModel.class).where("Task = ?", deleteRow.getBody()).executeSingle();
        row.delete();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == EDIT_REQUEST_CODE && resultCode == EDIT_REQUEST_OK) {
            String task = data.getExtras().getString("save_item");
            Priority pr = Priority.values()[data.getExtras().getInt("save_priority",0)];
            //String newItem = new TodoItem(task, p);
            int position = data.getExtras().getInt("position");
            TodoItem itemOld = todoItems.get(position);
            TodoItem itemNew = new TodoItem(task, pr);
            todoItems.set(position, itemNew);
            aToDoAdapter.notifyDataSetChanged();
            updateItem(itemOld, itemNew);
        }
        else if (requestCode == ADD_REQUEST_CODE && resultCode == ADD_REQUEST_OK){
            String task = data.getExtras().getString("save_item");
            Priority pr = Priority.values()[data.getExtras().getInt("save_priority",0)];
            TodoItem item = new TodoItem(task, pr);
            aToDoAdapter.add(item);
            //etEditText.setText("");
            writeItem(item);
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
        Intent addIntent = new Intent(MainActivity.this, EditItemActivity.class);
        addIntent.putExtra("edit_item", "");
        startActivityForResult(addIntent, ADD_REQUEST_CODE);

//        TodoItem item = new TodoItem(etEditText.getText().toString());
//        aToDoAdapter.add(item);
//        etEditText.setText("");
//        writeItem(item);
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.schintha.todoapp/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.schintha.todoapp/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }
}
