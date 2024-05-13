package com.example.lab4_03;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;

import java.util.ArrayList;

public class SmsReceiver extends BroadcastReceiver {

    public static final String SMS_FORWARD_BROADCAST_RECEIVER = "sms_forward broadcast_receiver";
    public static final String SMS_MESSAGE_ADDRESS_KEY = "sms_messages_key";

    @Override
    public void onReceive(Context context, Intent intent) {
        // Kiểm tra null trước khi xử lý
        if (intent != null && intent.getAction() != null && intent.getAction().equals("android.provider.Telephony.SMS_RECEIVED")) {
            String queryString = "Are you OK?".toLowerCase();

            Bundle bundle = intent.getExtras();
            if (bundle != null) {
                Object[] pdus = (Object[]) bundle.get("pdus");
                assert pdus != null;
                SmsMessage[] messages = new SmsMessage[pdus.length];
                for (int i = 0; i < pdus.length; i++) {
                    messages[i] = SmsMessage.createFromPdu((byte[]) pdus[i], "");
                }
                // Create ArrayList of OriginatingAddress of messages which contain queryString
                ArrayList<String> addresses = new ArrayList<>();

                for (SmsMessage message : messages) {
                    if (message.getMessageBody().toLowerCase().contains(queryString)) {
                        addresses.add(message.getOriginatingAddress());
                    }
                }
                if (!addresses.isEmpty()) {
                    if (!MainActivity.isRunning) {
                        // Start MainActivity if it stopped
                    } else {
                        // Forward these addresses to MainActivity to process
                        Intent iForwardBroadcastReceiver = new Intent(SMS_FORWARD_BROADCAST_RECEIVER);
                        iForwardBroadcastReceiver.putStringArrayListExtra(SMS_MESSAGE_ADDRESS_KEY, addresses);
                        context.sendBroadcast(iForwardBroadcastReceiver);
                    }
                }
            }
        }
    }
}
