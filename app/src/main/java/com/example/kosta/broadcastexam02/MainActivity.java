package com.example.kosta.broadcastexam02;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView phone;
    private TextView message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        phone = (TextView)findViewById(R.id.phoneNumber);
        message = (TextView)findViewById(R.id.message);
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(myReceiver, new IntentFilter("android.provider.Telephony.SMS_RECEIVED"));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(myReceiver);
    }

    private BroadcastReceiver myReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle bundle = intent.getExtras();

            if(bundle != null) {
                Object[] pdus = (Object[])bundle.get("pdus");
                String format = bundle.getString("format");

                for(int i = 0 ; i < pdus.length ; i++) {
                    SmsMessage msg = SmsMessage.createFromPdu((byte[])pdus[i], format);
                    phone.setText(msg.getOriginatingAddress());
                    message.setText(msg.getMessageBody());
                }
            }
        }
    };
}
