package com.example.musek;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    RecyclerView var_recyclerView;
    TextView var_tidakDitemukan;
    ArrayList<DataModel> laguList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        var_recyclerView = findViewById(R.id.id_recyclerView);
        var_tidakDitemukan = findViewById(R.id.id_tidakDitemukan);

        if (checkPermission() == false) {
            requestPermission();
            return;
        }

        String[] projection = {
                MediaStore.Audio.Media.TITLE,
                MediaStore.Audio.Media.DATA,
                MediaStore.Audio.Media.DURATION
        };

        String selection = MediaStore.Audio.Media.IS_MUSIC + " != 0";

        Cursor cursor = getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, projection, selection, null, null);
        while (cursor.moveToNext()){
            DataModel laguData = new DataModel(cursor.getString(1),cursor.getString(0),cursor.getString(2));

            if (new File(laguData.getPath()).exists())
                laguList.add(laguData);
        }

        if (laguList.size() == 0) {
            var_tidakDitemukan.setVisibility(View.VISIBLE);
        } else {
            // recyclerview
            var_recyclerView.setLayoutManager(new LinearLayoutManager(this));
            var_recyclerView.setAdapter(new DataListAdapter(laguList, getApplicationContext()));
        }
    }

    boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE);
        if(result == PackageManager.PERMISSION_GRANTED){
            return true;
        }else{
            return false;
        }
    }

    void requestPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)){
            Toast.makeText(MainActivity.this,"Mohon izinkan Musek mengakses penyimpananmu!",Toast.LENGTH_SHORT).show();
        } else
            ActivityCompat.requestPermissions(MainActivity.this, new String[] {Manifest.permission.READ_EXTERNAL_STORAGE}, 123);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (var_recyclerView != null) {
            var_recyclerView.setAdapter(new DataListAdapter(laguList, getApplicationContext()));
        }
    }
}