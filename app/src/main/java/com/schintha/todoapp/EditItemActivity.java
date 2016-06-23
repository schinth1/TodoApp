package com.schintha.todoapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

public class EditItemActivity extends AppCompatActivity {

    private final int SAVE_REQUEST_CODE = 1;
    EditText etEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        etEditText = (EditText) findViewById(R.id.etEditText);
        etEditText.setText(getIntent().getStringExtra("edit_item"));
        etEditText.setSelection(etEditText.getText().length());

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    public void onUpdateItem(View view) {
        Intent saveIntent = new Intent(EditItemActivity.this, MainActivity.class);
        saveIntent.putExtra("save_item", etEditText.getText().toString());
        setResult(SAVE_REQUEST_CODE, saveIntent);
        finish();
        //return true;
    }

}
