package com.cvetici.beeorganised;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.Locale;

import javax.crypto.spec.SecretKeySpec;

public class MainActivity extends AppCompatActivity {

   private ImageButton personal_use,child_use,parent_use;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        personal_use = (ImageButton) findViewById(R.id.personalUse);
        child_use = (ImageButton) findViewById(R.id.child);
        parent_use =(ImageButton) findViewById(R.id.parent);
        personal_use.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToPersonal();
                finish();
            }
        });
        child_use.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(load()==2){
                    Intent intent = new Intent(MainActivity.this, ChildActivity.class);
                    startActivity(intent);
                    finish();
                }else {
                    goToChild();
                }

            }
        });
        parent_use.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(load()==3){
                    goToParent();
                }else{
                    Intent intent = new Intent(MainActivity.this, ParentBuilderActivity.class);
                    startActivity(intent);
                }
            }
        });
        setLocale("en");

    }
    private void setLocale( String lng){
        Locale locale = new Locale (lng);
        Locale.setDefault(locale);
        Configuration con = new Configuration();
        con.locale = locale;

        getBaseContext().getResources().updateConfiguration(con, getBaseContext().getResources().getDisplayMetrics());

        getApplicationContext().getResources().updateConfiguration(con, getApplicationContext().getResources().getDisplayMetrics());

        SharedPreferences.Editor editor = getSharedPreferences("Settings", MODE_PRIVATE).edit();
        editor.putString("my lan", lng);
        editor.apply();
    }
    private void goToPersonal(){
        Intent intent = new Intent(MainActivity.this, WorkerActivity.class);
        startActivity(intent);
        save(1);
    }
    private void goToChild(){
        Intent intent = new Intent(MainActivity.this, ChildWorkerActivity.class);
        startActivity(intent);
    }
    private void goToParent(){
        Intent intent = new Intent(MainActivity.this, ParentWorkerActivity.class);
        startActivity(intent);
    }

    public void save(int user){
        String FILE_NAME="UserData";
        SharedPreferences sharedPreferences = getSharedPreferences(FILE_NAME,MODE_PRIVATE);
        SharedPreferences.Editor  editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(user);
        editor.putString("brUsera",json);
        editor.apply();
    }
    public int load(){
        String FILE_NAME="UserData";
        SharedPreferences sharedPreferences = getSharedPreferences(FILE_NAME,MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("brUsera",null);
        if(json==null){
            return 0;
        }else{
            return Integer.parseInt(json);
        }

    }


}