package com.cdestes.notetakingapp;

import android.animation.FloatArrayEvaluator;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    EditText editText1;
    String fileName;
    final Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fileName = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
                Save(fileName+".txt");
            }
        });

        editText1 = (EditText) findViewById(R.id.Note1);
        editText1.setText(Open(fileName+".txt"));

        String extras = getIntent().getStringExtra("fileName");
        if (extras != null) {
            editText1.setText(Open(extras));
        }


        FloatingActionButton captureImage = ( FloatingActionButton) findViewById(R.id.captureImage);
        captureImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent captureIntent = new Intent(getApplicationContext(), Capture_Image.class);
                startActivity(captureIntent);
            }
        });

        FloatingActionButton captureRecording = (FloatingActionButton) findViewById(R.id.capture);
        captureRecording.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //places function to use recording

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        Intent myIntent = new Intent(MainActivity.this, SelectNote.class);
        MainActivity.this.startActivity(myIntent);

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void Save(String fileName) {
        FileOutputStream outputStream;
        try {
            outputStream = openFileOutput(fileName, Context.MODE_PRIVATE);
            outputStream.write(editText1.getText().toString().getBytes());
            outputStream.close();

            Toast.makeText(this, "Note Saved!"+fileName, Toast.LENGTH_SHORT).show();
            Intent saved = new Intent(MainActivity.this, SelectNote.class);
            MainActivity.this.startActivity(saved);
        } catch (Throwable t) {
            Toast.makeText(this, "Exception: " + t.toString(), Toast.LENGTH_LONG).show();
        }
    }

    public String Open(String fileName) {
        String note = "";
            try {
                FileInputStream in = openFileInput(fileName);
                if ( in != null) {
                    int c;
                    while( (c = in.read()) != -1){
                        note = note + Character.toString((char)c);
                    } in .close();
                }
            } catch (java.io.FileNotFoundException e) {} catch (Throwable t) {
                Toast.makeText(this, "Exception: " + t.toString(), Toast.LENGTH_LONG).show();
            }
        return note;
    }
}
