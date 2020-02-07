package com.opriday.basecamp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;

public class SplashScreen extends AppCompatActivity {

    FirebaseRemoteConfig mFirebaseRemoteConfig;
    String str_title, str_message, str_website_register, str_website_login;
    boolean isWebview = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_splash_screen);

        mFirebaseRemoteConfig = FirebaseRemoteConfig.getInstance();
        FirebaseRemoteConfigSettings configSettings = new FirebaseRemoteConfigSettings.Builder()
                .setMinimumFetchIntervalInSeconds(3600)
                .build();
        mFirebaseRemoteConfig.setConfigSettingsAsync(configSettings);
        mFirebaseRemoteConfig.fetch(0).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    mFirebaseRemoteConfig.activateFetched();
                    displayData();
                }
            }
        });


    }

    private void displayData() {
        str_title = mFirebaseRemoteConfig.getString("remote_board_title");
        str_message = mFirebaseRemoteConfig.getString("remote_board_message");
        Log.e("remote", "title:" + str_title);
        Log.e("remote", "description:" + str_message);
        isWebview = mFirebaseRemoteConfig.getBoolean("isWebview");
        str_website_register = mFirebaseRemoteConfig.getString("remote_web_site_register");
        str_website_login = mFirebaseRemoteConfig.getString("remote_web_site_login");
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashScreen.this, MainActivity.class);
                intent.putExtra("remote_board_title", str_title);
                intent.putExtra("remote_board_message", str_message);
                intent.putExtra("isWebview", isWebview);
                intent.putExtra("remote_web_site_register", str_website_register);
                intent.putExtra("remote_web_site_login", str_website_login);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        }, 3000);
    }
}
