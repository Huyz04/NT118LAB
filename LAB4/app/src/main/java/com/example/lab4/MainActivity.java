package com.example.lab4;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    private BroadcastReceiver broadcastReceiver;
    private IntentFilter filter;
    public void processReceive(Context context, Intent intent) {
        Toast.makeText(context, getString(R.string.you_have_a_new_message),
                Toast.LENGTH_LONG).show();

        TextView tvContent = (TextView) findViewById(R.id.tv_noti);

// Use "pdus" as key to get message
        final String SMS_EXTRA = "pdus";
        Bundle bundle = intent.getExtras();
// Get array of messages which vere received at the same time
        Object[] messages = (Object[]) bundle.get(SMS_EXTRA);
        String sms = "";

        SmsMessage smsMsg;
        for (int i = 0; i < messages.length; i++) {
            if (android.os.Build.VERSION.SDK_INT >= 23) {
                smsMsg = SmsMessage.createFromPdu((byte[]) messages[i], "3gpp2");
            } else {
                smsMsg = SmsMessage.createFromPdu((byte[]) messages[i]);
            }

            if (smsMsg != null) {
                String msgBody = smsMsg.getDisplayMessageBody(); // Thay đổi ở đây
                String address = smsMsg.getDisplayOriginatingAddress();
                sms += address + ":\n" + msgBody + "\n";
            }
        }

        // Show messages in textviev
        if (tvContent != null) {
            tvContent.setText(sms);
        }
    }
    private void initBroadcastReceiver () {
// Create filter to listen to incoming messages
        filter = new IntentFilter
                ("android.provider.Telephony.SMS_RECEIVED");

// Create broadcastReceiver
        broadcastReceiver = new BroadcastReceiver() {
            // Process when a message comes
            public void onReceive(Context context, Intent intent) {
                processReceive(context, intent);
            }
        };
    }
    @Override
    protected void onResume () {
        super.onResume();

// Make sure broadcastReceiver vas created
        if (broadcastReceiver == null) initBroadcastReceiver();

// Register Receiver
        registerReceiver(broadcastReceiver, filter);
    }
    @Override
    protected void onStop() {
        super.onStop();
        // UnregisterReceiver when app is destroyed
        unregisterReceiver(broadcastReceiver);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        initBroadcastReceiver();
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

}