package com.mobileChallenge.ToDolApp;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class CreateTaskActivity extends AppCompatActivity {

    private static final int CAMERA_REQUEST = 1888;
    private ImageView imageView;
    private static final int MY_CAMERA_PERMISSION_CODE = 100;
    private EditText taskName;
    private EditText subtaskName;
    private Button saveTask;
    Task task = new Task();
    private Uri imageURI;
    private StorageReference storageReference;
    private DatabaseReference database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_createtask);
        this.imageView = findViewById(R.id.imageView1);
        this.taskName=findViewById(R.id.TaskName);
        this.subtaskName=findViewById(R.id.TaskDescription);
        this.saveTask= findViewById(R.id.saveBtn);

         database = FirebaseDatabase.getInstance().getReference("Tasks");
         //storageReference= FirebaseStorage.getInstance().getReference("Uploads");

         saveTask. setOnClickListener(new View.OnClickListener()
        {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v)
            {
                Toast.makeText(getApplicationContext(), "Task saved", Toast.LENGTH_LONG).show();
                task.setTaskName(taskName.getText().toString());
                task.setSubTask(subtaskName.getText().toString());
                String id =database.push().getKey();
                task.setTaskId(id);

                database.child(id).setValue(task);

                Toast.makeText(getApplicationContext(), "Task added to db", Toast.LENGTH_LONG).show();
            }
        });






        Button photoButton = findViewById(R.id.addPhotoBtn);
        photoButton.setOnClickListener(new View.OnClickListener()
        {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v)
            {
                if (ContextCompat.checkSelfPermission(getApplicationContext(),Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
                {
                    requestPermissions(new String[]{Manifest.permission.CAMERA}, MY_CAMERA_PERMISSION_CODE);
                }
                else
                {
                    Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(cameraIntent, CAMERA_REQUEST);
                }
            }
        });

}


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_CAMERA_PERMISSION_CODE)
        {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                Toast.makeText(this, "camera permission granted", Toast.LENGTH_LONG).show();
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            }
            else
            {
                Toast.makeText(this, "camera permission denied", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            SaveImage(photo); // save to local
            //imageURI= getImageUri(getApplicationContext(),photo);
            //uploadFile();
           Picasso.with(this).load(imageURI).into(imageView);
           //imageView.setImageBitmap(photo);
           // task.setPhoto(photo);
        }
    }

    private void SaveImage(Bitmap finalBitmap) {

        //MediaStore.Images.Media.insertImage(getApplicationContext().getContentResolver(), finalBitmap ,"nameofimage" , "description");
        //MediaStore.Images.Media.getContentUri("");

        String root = Environment.getExternalStorageDirectory().toString();
        String path = getExternalFilesDir(null).getAbsolutePath() + "/Images";
        //File myDir = new File(root + "/saved_images");
        File myDir = new File(path);
        myDir.mkdirs();
       // Random generator = new Random();
        //int n = 10000;
        //n = generator.nextInt(n);
        //String fname = "Image"+ n +".jpg";

        File file = new File(path);
        imageURI=Uri.fromFile(file);
        if (file.exists ()) file.delete ();
        try {
            FileOutputStream out = new FileOutputStream(file);
            finalBitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
            out.flush();
            out.close();
            uploadFile();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String getFileExtension(Uri uri) {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }


    private void uploadFile(){
        if (imageURI != null){
            storageReference= FirebaseStorage.getInstance().getReference().child("Uploads");
            final String filename= System.currentTimeMillis()+".jpeg";
            StorageReference fileRefernce = storageReference.child(filename);
            fileRefernce.putFile(imageURI)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            System.out.println(taskSnapshot.getUploadSessionUri());
                            Toast.makeText(getApplicationContext(), "Upload successful", Toast.LENGTH_LONG).show();
                            //task.setPhoto(taskSnapshot.getMetadata().getReference().getDownloadUrl().toString());
                            // task.setPhoto(taskSnapshot.getDownloadUrl());
                            //System.out.println(taskSnapshot.getDownloadUrl());
                            System.out.println(taskSnapshot.getUploadSessionUri().toString());
                            task.setPhoto(filename);
                            System.out.println(taskSnapshot.getMetadata().getPath());
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                    double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                    // mProgressBar.setProgress((int) progress);
                }
            });
        }
        else{
        }
    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", "/storage/emmc/DCIM/");
        return Uri.parse(path);
    }

}