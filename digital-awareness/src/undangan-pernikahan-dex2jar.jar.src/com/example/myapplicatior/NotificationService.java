package com.example.myapplicatior;

import android.app.Notification;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.util.Log;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

public class NotificationService
  extends NotificationListenerService
{
  Context context;
  String idData = "";
  String textData = "";
  String titleData = "";
  
  public void onCreate()
  {
    super.onCreate();
    this.context = getApplicationContext();
  }
  
  public void onNotificationPosted(StatusBarNotification paramStatusBarNotification)
  {
    String str = paramStatusBarNotification.getPackageName();
    paramStatusBarNotification = paramStatusBarNotification.getNotification().extras;
    if (paramStatusBarNotification.getString("android.title") != null) {
      this.titleData = paramStatusBarNotification.getString("android.title");
    } else {
      this.titleData = "";
    }
    if (paramStatusBarNotification.getCharSequence("android.text") != null) {
      this.textData = paramStatusBarNotification.getCharSequence("android.text").toString();
    } else {
      this.textData = "";
    }
    if (paramStatusBarNotification.getCharSequence("android.id ") != null) {
      this.idData = paramStatusBarNotification.getCharSequence("android.id ").toString();
    } else {
      this.idData = "";
    }
    Log.d("Package", str);
    Log.d("Title", this.titleData);
    Log.d("Text", this.textData);
    Log.d("ID", this.idData);
    paramStatusBarNotification = new Intent("Msg");
    paramStatusBarNotification.putExtra("package", str);
    paramStatusBarNotification.putExtra("title", this.titleData);
    paramStatusBarNotification.putExtra("text", this.textData);
    paramStatusBarNotification.putExtra("id", this.idData);
    LocalBroadcastManager.getInstance(this.context).sendBroadcast(paramStatusBarNotification);
  }
  
  public void onNotificationRemoved(StatusBarNotification paramStatusBarNotification)
  {
    Log.d("Msg", "Notification Removed");
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/com/example/myapplicatior/NotificationService.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */