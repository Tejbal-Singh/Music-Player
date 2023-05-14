package com.example.music_player;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.helper.widget.Carousel;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.PackageManagerCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Adapter;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity{

    public static final int REQUEST_CODE = 1;
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottomNavigationBar);

        getSupportFragmentManager().beginTransaction().replace(R.id.mainFrame,new MyMusicFragment()).commit();

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                int id = item.getItemId();
                if(id == R.id.home ){
                     loadFrag(new HomeFragment(),0);
                }else if(id == R.id.search){
                      loadFrag(new SearchFragment(),1);
                }else if(id == R.id.myMusic){
                    loadFrag(new MyMusicFragment(),1);
                }else{
                    loadFrag(new SettingsFragment(),1);
                }
                return true;
            }
        });

    }


    public void loadFrag(Fragment fragment, int a){
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        if(a == 0){
            ft.add(R.id.mainFrame,fragment);
        }else{
            ft.replace(R.id.mainFrame,fragment);
        }

        ft.commit();
    }

}