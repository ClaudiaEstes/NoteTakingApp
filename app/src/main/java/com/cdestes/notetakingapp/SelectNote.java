package com.cdestes.notetakingapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class SelectNote extends AppCompatActivity {

    private List<Notes> notesList = new ArrayList<>();
    private NotesAdapter nAdapter;
    private RecyclerView notesRecycler;
    private final Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_note);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {noteSelector();}
        });

        notesRecycler = (RecyclerView) findViewById(R.id.notes);

        nAdapter = new NotesAdapter(notesList);
        RecyclerView.LayoutManager mLayoutManager =
                new LinearLayoutManager(context);
        notesRecycler.setLayoutManager(mLayoutManager);
        notesRecycler.setItemAnimator(new DefaultItemAnimator());
        notesRecycler.setAdapter(nAdapter);

        notesRecycler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int itemPosition = notesRecycler.getChildLayoutPosition(view);
                Notes item = notesList.get(itemPosition);
                Intent openNote = new Intent( SelectNote.this, MainActivity.class);
                openNote.putExtra("fileName", item.getTitle());
                SelectNote.this.startActivity(openNote);
            }
        });

        prepareNotes();
    }

    private void noteSelector(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);

    }

    private void prepareNotes() {
        String[] files = context.fileList();
        //if (files.length == 0) Log.i("prepareNotes", "No files in the fileList()");
        //else Log.i("prepareNotes", "There are files in the fileList," + files[0] + files[1]);
        String theFile;
        for (int f = 0; f < files.length; f++) {
            theFile = files[f];
            Notes note = new Notes(theFile, Open(theFile));
            notesList.add(note);
        }
    }

    public String Open(String fileName) {
        String content = "";
        try {
            InputStream in = openFileInput(fileName);
            if ( in != null) {
                InputStreamReader tmp = new InputStreamReader( in );
                BufferedReader reader = new BufferedReader(tmp);
                String str;
                StringBuilder buf = new StringBuilder();
                while ((str = reader.readLine()) != null) {
                    buf.append(str + "\n");
                } in .close();

                content = buf.toString();
            }
            else Log.i("open", "The file is null");
        } catch (java.io.FileNotFoundException e) {} catch (Throwable t) {
            Toast.makeText(this, "Exception: " + t.toString(), Toast.LENGTH_LONG).show();
        }

        return content;
    }

}
