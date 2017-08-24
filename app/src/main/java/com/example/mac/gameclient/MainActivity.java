package com.example.mac.gameclient;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void clickRestUserTestButton(View view) {
        //Toast.makeText(getBaseContext(), "RESTful API - user", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(MainActivity.this, RestUserActivity.class);
        startActivity(intent);
    }
    public void clickRestItemTestButton(View view) {
        //Toast.makeText(getBaseContext(), "RESTful API - item", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(MainActivity.this, RestItemActivity.class);
        startActivity(intent);
    }
    public void clickRestEtcTestButton(View view) {
        //Toast.makeText(getBaseContext(), "RESTful API - etc", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(MainActivity.this, RestEtcActivity.class);
        startActivity(intent);
    }
    public void clickMqttPubSubTestButton(View view) {
        //Toast.makeText(getBaseContext(), "MQTT - publish/subscribe", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(MainActivity.this, MqttPubSubActivity.class);
        startActivity(intent);
    }
}
