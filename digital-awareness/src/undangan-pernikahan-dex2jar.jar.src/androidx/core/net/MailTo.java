package androidx.core.net;

import android.net.Uri;
import androidx.core.util.Preconditions;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import mt.Log229316;
import mt.Log5ECF72;
import mt.LogE84000;

public final class MailTo
{
  private static final String BCC = "bcc";
  private static final String BODY = "body";
  private static final String CC = "cc";
  private static final String MAILTO = "mailto";
  public static final String MAILTO_SCHEME = "mailto:";
  private static final String SUBJECT = "subject";
  private static final String TO = "to";
  private HashMap<String, String> mHeaders = new HashMap();
  
  public static boolean isMailTo(Uri paramUri)
  {
    boolean bool;
    if ((paramUri != null) && ("mailto".equals(paramUri.getScheme()))) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public static boolean isMailTo(String paramString)
  {
    boolean bool;
    if ((paramString != null) && (paramString.startsWith("mailto:"))) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public static MailTo parse(Uri paramUri)
    throws ParseException
  {
    return parse(paramUri.toString());
  }
  
  public static MailTo parse(String paramString)
    throws ParseException
  {
    Preconditions.checkNotNull(paramString);
    if (isMailTo(paramString))
    {
      int i = paramString.indexOf('#');
      Object localObject1 = paramString;
      if (i != -1) {
        localObject1 = paramString.substring(0, i);
      }
      i = ((String)localObject1).indexOf('?');
      if (i == -1)
      {
        paramString = Uri.decode(((String)localObject1).substring("mailto:".length()));
        Log5ECF72.a(paramString);
        LogE84000.a(paramString);
        Log229316.a(paramString);
        localObject1 = null;
      }
      else
      {
        paramString = Uri.decode(((String)localObject1).substring("mailto:".length(), i));
        Log5ECF72.a(paramString);
        LogE84000.a(paramString);
        Log229316.a(paramString);
        localObject1 = ((String)localObject1).substring(i + 1);
      }
      MailTo localMailTo = new MailTo();
      if (localObject1 != null)
      {
        localObject2 = ((String)localObject1).split("&");
        int j = localObject2.length;
        for (i = 0; i < j; i++)
        {
          localObject1 = localObject2[i].split("=", 2);
          if (localObject1.length != 0)
          {
            String str = Uri.decode(localObject1[0]);
            Log5ECF72.a(str);
            LogE84000.a(str);
            Log229316.a(str);
            str = str.toLowerCase(Locale.ROOT);
            if (localObject1.length > 1)
            {
              localObject1 = Uri.decode(localObject1[1]);
              Log5ECF72.a(localObject1);
              LogE84000.a(localObject1);
              Log229316.a(localObject1);
            }
            else
            {
              localObject1 = null;
            }
            localMailTo.mHeaders.put(str, localObject1);
          }
        }
      }
      Object localObject2 = localMailTo.getTo();
      localObject1 = paramString;
      if (localObject2 != null) {
        localObject1 = paramString + ", " + (String)localObject2;
      }
      localMailTo.mHeaders.put("to", localObject1);
      return localMailTo;
    }
    throw new ParseException("Not a mailto scheme");
  }
  
  public String getBcc()
  {
    return (String)this.mHeaders.get("bcc");
  }
  
  public String getBody()
  {
    return (String)this.mHeaders.get("body");
  }
  
  public String getCc()
  {
    return (String)this.mHeaders.get("cc");
  }
  
  public Map<String, String> getHeaders()
  {
    return this.mHeaders;
  }
  
  public String getSubject()
  {
    return (String)this.mHeaders.get("subject");
  }
  
  public String getTo()
  {
    return (String)this.mHeaders.get("to");
  }
  
  public String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder("mailto:");
    localStringBuilder.append('?');
    Iterator localIterator = this.mHeaders.entrySet().iterator();
    while (localIterator.hasNext())
    {
      Map.Entry localEntry = (Map.Entry)localIterator.next();
      String str = Uri.encode((String)localEntry.getKey());
      Log5ECF72.a(str);
      LogE84000.a(str);
      Log229316.a(str);
      localStringBuilder.append(str);
      localStringBuilder.append('=');
      str = Uri.encode((String)localEntry.getValue());
      Log5ECF72.a(str);
      LogE84000.a(str);
      Log229316.a(str);
      localStringBuilder.append(str);
      localStringBuilder.append('&');
    }
    return localStringBuilder.toString();
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/core/net/MailTo.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */