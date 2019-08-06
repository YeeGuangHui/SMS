package com.example.sms;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

public class Messager extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        try {
            if (bundle != null) {
                Object[] pdusObj = (Object[]) bundle.get("pdus");
                SmsMessage currentMessage;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    String format = bundle.getString("format");
                    currentMessage = SmsMessage.createFromPdu((byte[]) pdusObj[0], format);
                } else {
                    currentMessage = SmsMessage.createFromPdu((byte[]) pdusObj[0]);
                }
                String senderNum = currentMessage.getDisplayOriginatingAddress();
                String message = currentMessage.getDisplayMessageBody();
                Toast.makeText(context, "Sender Number: " + senderNum +
                        ", Message: " + message, Toast.LENGTH_LONG).show();
                Log.d("SMS-REC", "Received a message");
            }
        } catch (Exception e) {
            Log.e("SmsReceiver", "Error: " + e);
        }

    }
}
