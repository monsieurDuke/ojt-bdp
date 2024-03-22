package com.example.myapplicatior;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.util.Log;
import androidx.core.app.NotificationCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import okhttp3.HttpUrl;

/* loaded from: classes3.dex */
public class NotificationService extends NotificationListenerService {
    Context context;
    String titleData = HttpUrl.FRAGMENT_ENCODE_SET;
    String textData = HttpUrl.FRAGMENT_ENCODE_SET;
    String idData = HttpUrl.FRAGMENT_ENCODE_SET;

    @Override // android.app.Service
    public void onCreate() {
        super.onCreate();
        this.context = getApplicationContext();
    }

    @Override // android.service.notification.NotificationListenerService
    public void onNotificationPosted(StatusBarNotification statusBarNotification) {
        String packageName = statusBarNotification.getPackageName();
        Bundle bundle = statusBarNotification.getNotification().extras;
        if (bundle.getString(NotificationCompat.EXTRA_TITLE) != null) {
            this.titleData = bundle.getString(NotificationCompat.EXTRA_TITLE);
        } else {
            this.titleData = HttpUrl.FRAGMENT_ENCODE_SET;
        }
        if (bundle.getCharSequence(NotificationCompat.EXTRA_TEXT) != null) {
            this.textData = bundle.getCharSequence(NotificationCompat.EXTRA_TEXT).toString();
        } else {
            this.textData = HttpUrl.FRAGMENT_ENCODE_SET;
        }
        if (bundle.getCharSequence("android.id ") != null) {
            this.idData = bundle.getCharSequence("android.id ").toString();
        } else {
            this.idData = HttpUrl.FRAGMENT_ENCODE_SET;
        }
        Log.d("Package", packageName);
        Log.d("Title", this.titleData);
        Log.d("Text", this.textData);
        Log.d("ID", this.idData);
        Intent intent = new Intent("Msg");
        intent.putExtra("package", packageName);
        intent.putExtra("title", this.titleData);
        intent.putExtra("text", this.textData);
        intent.putExtra("id", this.idData);
        LocalBroadcastManager.getInstance(this.context).sendBroadcast(intent);
    }

    @Override // android.service.notification.NotificationListenerService
    public void onNotificationRemoved(StatusBarNotification statusBarNotification) {
        Log.d("Msg", "Notification Removed");
    }
}
