package com.cdestes.notetakingapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Capture_Image extends Activity {

    private ImageView imageView;
    private final int requestCode = 20;
    String mCurrentPhotoPath;
    static ArrayList<String> myList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_capture_image);
        Button photo_button = (Button) findViewById(R.id.photo_button);
        photo_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispatchTakePictureIntent();
            }
        });
    }

    private void dispatchTakePictureIntent(){
            Intent photoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

             //Ensure that there's a camera activity to handle the intent
            if (photoIntent.resolveActivity(getPackageManager()) != null) {
                //startActivityForResult(photoIntent, requestCode);

                // Create the File where the photo should go
                File photoFile = null;
                try {
                    photoFile = createImageFile();
                } catch (IOException ex) {
                    // Error occurred while creating the File
                }
                // Continue only if the File was successfully created
                if (photoFile != null) {
                    Uri photoURI = FileProvider.getUriForFile(this,
                            "com.cdestes.notetakingapp.fileprovider",
                            photoFile);
                    Toast.makeText(this, "uri created", Toast.LENGTH_SHORT).show();
                    photoIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                    startActivityForResult(photoIntent, requestCode);
                }
            }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(this.requestCode == requestCode && resultCode== RESULT_OK){

//            Bitmap bitmap = (Bitmap)data.getExtras().get("data");
//            imageView.setImageBitmap(bitmap);
//


            //Add image Path To List
            myList = new ArrayList<String>();
            myList.add( mCurrentPhotoPath);


            File imgFile = new  File(mCurrentPhotoPath);
            if(imgFile.exists())
            {
                Bitmap myBitmap = BitmapFactory.decodeFile(mCurrentPhotoPath);
                imageView.setImageBitmap(myBitmap);
                Toast.makeText(this, "Image should show up ", Toast.LENGTH_SHORT).show();

                Toast.makeText(this, "File exists and saved!", Toast.LENGTH_SHORT).show();
                Intent allImages = new Intent(this, Images.class);
                allImages.putStringArrayListExtra("myList", myList);
                startActivity(allImages);
                //imageView.setImageURI(Uri.fromFile(imgFile));
            }
        }
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }


}
