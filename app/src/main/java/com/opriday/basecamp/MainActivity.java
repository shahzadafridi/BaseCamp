package com.opriday.basecamp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    String TAG = "MainActivity";

    TextView title, description;
    Button button1, button2;
    String str_title, str_message, str_website_register, str_website_login;
    boolean isWebview = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        title = (TextView) findViewById(R.id.title);
        description = (TextView) findViewById(R.id.description);
        button1 = (Button) findViewById(R.id.button1);
        button2 = (Button) findViewById(R.id.button2);
        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
        displayData();
    }

    private void displayData() {
        str_title = getIntent().getStringExtra("remote_board_title");
        str_message = getIntent().getStringExtra("remote_board_message");
        Log.e("remote","title:"+str_title);
        Log.e("remote","description:"+str_message);
        title.setText(str_title);
        isWebview = getIntent().getBooleanExtra("isWebview",true);
        description.setText(str_message);
        str_website_register = getIntent().getStringExtra("remote_web_site_register");
        str_website_login = getIntent().getStringExtra("remote_web_site_login");
    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.button1) {
            if (isWebview) {
                Intent intent = new Intent(MainActivity.this, WebViewActivity.class);
                intent.putExtra("url", str_website_register);
                startActivity(intent);
            }else {
                openBrowser(str_website_register);
            }
        } else if (v.getId() == R.id.button2) {
            if (isWebview) {
                Intent intent = new Intent(MainActivity.this, WebViewActivity.class);
                intent.putExtra("url", str_website_login);
                startActivity(intent);
            }else {
                openBrowser(str_website_login);
            }
        }
    }

    public void openBrowser(String url){
        if (!url.startsWith("http://") && !url.startsWith("https://")) {
            url = "http://" + url;
        }
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(browserIntent);
    }
}
