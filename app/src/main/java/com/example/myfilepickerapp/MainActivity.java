package com.example.myfilepickerapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.VolumeShaper;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.jaiselrahman.filepicker.activity.FilePickerActivity;
import com.jaiselrahman.filepicker.config.Configurations;
import com.jaiselrahman.filepicker.model.MediaFile;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Button btnImg,btnFile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnImg = findViewById(R.id.uploudImage);
        btnFile = findViewById(R.id.uploudFile);


//

        btnImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ContextCompat.checkSelfPermission(MainActivity.this,Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.CAMERA},1);
                } else{
                    imagePicker();
                }
            }
        });
//
        btnFile.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,FilePickerActivity.class);

                intent.putExtra(FilePickerActivity.CONFIGS, new Configurations.Builder().setCheckPermission(true).setShowFiles(true).setShowImages(false).setShowVideos(false).setMaxSelection(1).setSuffixes("txt","pdf","doc","docx").setSkipZeroSizeFiles(true).build());
                startActivityForResult(intent,102);

            }


        });


    }

    private void imagePicker(){
        Intent intent = new Intent(this, FilePickerActivity.class);

        intent.putExtra(FilePickerActivity.CONFIGS,new Configurations.Builder().setCheckPermission(true)
                .setShowImages(true).setShowVideos(false).enableImageCapture(true).setMaxSelection(1).setSkipZeroSizeFiles(true).build());


    }

    public void onRequestPermissionResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResult) {
        super.onRequestPermissionsResult(requestCode,permissions,grantResult);

        if (grantResult.length > 0 && (grantResult[0] == PackageManager.PERMISSION_GRANTED)) {
            if (requestCode == 1){
                imagePicker();
            }
        } else {
            Toast.makeText(getApplicationContext(),"Permission Denied", Toast.LENGTH_LONG).show();

        }
    }

    public void onActivityResult(int requestCode,int resultCode,@Nullable Intent data){
        super.onActivityResult(requestCode,resultCode,data);

        if (resultCode == RESULT_OK && data != null ) {
            ArrayList<MediaFile> mediaFiles = data.getParcelableArrayListExtra(
                    FilePickerActivity.MEDIA_FILES
            );
            String path =  mediaFiles.get(0).getPath();

            switch (requestCode) {
                case 101:
                    String s = "Image path : " + path;
                    Toast.makeText(getApplicationContext(),s,Toast.LENGTH_SHORT).show();
                case 102:
                String d = "File path :" + path;
                    Toast.makeText(getApplicationContext(),d,Toast.LENGTH_SHORT).show();
                    break;

            }
        }
    }


}