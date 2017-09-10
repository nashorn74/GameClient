package com.example.mac.gameclient;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.internal.ExceptionHelper;

import java.util.ArrayList;

public class MqttPubSubActivity extends AppCompatActivity {
    PahoMqttClient pahoMqttClient = null;
    MqttAndroidClient mqttAndroidClient = null;
    static final String MQTT_BROKER_URL = "tcp://172.30.1.22:1883";
    static final String CLIENT_ID = "android";
    ArrayList<String> messageList = new ArrayList<String>();
    ArrayAdapter<String> adapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mqtt_pub_sub);

        ListView listView = (ListView)findViewById(R.id.message_list);
        adapter = new ArrayAdapter<String>(MqttPubSubActivity.this,
                android.R.layout.simple_list_item_1, messageList);
        listView.setAdapter(adapter);

        pahoMqttClient = new PahoMqttClient();
        mqttAndroidClient = pahoMqttClient.getMqttClient(
                getApplicationContext(), MQTT_BROKER_URL, CLIENT_ID);

        mqttAndroidClient.setCallback(new MqttCallbackExtended() {
            @Override
            public void connectComplete(boolean b, String s) {
                Log.i("MqttPubSubActivity", "connectComplete");
                try {
                    pahoMqttClient.subscribe(mqttAndroidClient, "game_server", 0);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void connectionLost(Throwable throwable) {
                Log.i("MqttPubSubActivity", "connectionLost");
            }
            @Override
            public void messageArrived(String s, MqttMessage mqttMessage) throws Exception {
                Log.i(s, new String(mqttMessage.getPayload()));
                adapter.add(new String(mqttMessage.getPayload()));
            }
            @Override
            public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {
                Log.i("MqttPubSubActivity", "deliveryComplete");
            }
        });
    }

    public void clickPublishButton(View view) {
        EditText messageText = (EditText)findViewById(R.id.message);
        String message = messageText.getText().toString();
        if (!message.isEmpty()) {
            try {
                pahoMqttClient.publishMessage(mqttAndroidClient, message, 0, "game_server");
                messageText.setText("");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
