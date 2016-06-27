package com.schintha.todoapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.NumberPicker;

import com.schintha.todoapp.model.Priority;

public class EditItemActivity extends AppCompatActivity {

    private final int SAVE_REQUEST_CODE = 1;
    //private final int ADD_REQUEST_CODE = 2;

    private NumberPicker npPriority;

    EditText etEditText;
    int position;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        etEditText = (EditText) findViewById(R.id.etEditText);
        npPriority = (NumberPicker) findViewById(R.id.npPriority);
        npPriority.setMinValue(Priority.LOW.ordinal());
        npPriority.setMaxValue(Priority.HIGH.ordinal());
        npPriority.setDisplayedValues(new String[]{"LOW", "MEDIUM", "HIGH"});
        npPriority.setWrapSelectorWheel(false);

        etEditText.setText(getIntent().getStringExtra("edit_item"));
        npPriority.setValue(getIntent().getIntExtra("edit_priority", 0));
        position = getIntent().getIntExtra("position", -1);
        etEditText.setSelection(etEditText.getText().length());
    }

    public void onUpdateItem(View view) {
        Intent saveIntent = new Intent(EditItemActivity.this, MainActivity.class);
        saveIntent.putExtra("save_item", etEditText.getText().toString());
        saveIntent.putExtra("save_priority", npPriority.getValue());
        saveIntent.putExtra("position", position);
        setResult(SAVE_REQUEST_CODE, saveIntent);
        finish();
    }

}
