package com.cdestes.notetakingapp;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.GridView;
import android.widget.ImageView;

import java.io.File;
import java.util.ArrayList;

public class Images extends Activity {

    private String path;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.image_list);


        ImageView imageView = (ImageView) findViewById(R.id.imageView2);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        File directory;
        directory = getFilesDir();
        File[] files = directory.listFiles();
        String theFile;

        for (int i = 0; i < files.length; i++) {
            Log.d("Files", "FileName:" + files[i].getName()); //these are the different filenames in the directory

            //You can now use these file names along with the directory path
            // and convert the image there to a bitmap and set it to recycler view's image view

            File imgFile = new File(path + files[i].getName());
            if (imgFile.exists()) {

                Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath()); //this is the bitmap for the image

                //your image view in the recycler view
                imageView.setImageBitmap(myBitmap);//image set to the image view

            }
        }
    }

}
