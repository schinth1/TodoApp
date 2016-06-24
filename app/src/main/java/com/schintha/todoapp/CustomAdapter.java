package com.schintha.todoapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.schintha.todoapp.model.TodoItem;

import java.util.ArrayList;

/**
 * Created by sc043016 on 6/23/16.
 */
public class CustomAdapter extends ArrayAdapter<TodoItem> {

    private final Context context;
    private final ArrayList<TodoItem> todoItems;

    public CustomAdapter(Context context, ArrayList<TodoItem> todoItems) {
        super(context, R.layout.todo_item, todoItems);
        this.context = context;
        this.todoItems = todoItems;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.todo_item, parent, false);
        TextView taskView = (TextView) rowView.findViewById(R.id.item_task);
        taskView.setText(todoItems.get(position).getBody());


        return rowView;
    }

}
