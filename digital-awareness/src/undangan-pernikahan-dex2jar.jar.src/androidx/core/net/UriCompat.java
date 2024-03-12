package androidx.core.net;

import android.net.Uri;

public final class UriCompat
{
  public static String toSafeString(Uri paramUri)
  {
    String str3 = paramUri.getScheme();
    String str2 = paramUri.getSchemeSpecificPart();
    String str1 = str2;
    if (str3 != null) {
      if ((!str3.equalsIgnoreCase("tel")) && (!str3.equalsIgnoreCase("sip")) && (!str3.equalsIgnoreCase("sms")) && (!str3.equalsIgnoreCase("smsto")) && (!str3.equalsIgnoreCase("mailto")) && (!str3.equalsIgnoreCase("nfc")))
      {
        if ((!str3.equalsIgnoreCase("http")) && (!str3.equalsIgnoreCase("https")) && (!str3.equalsIgnoreCase("ftp")))
        {
          str1 = str2;
          if (!str3.equalsIgnoreCase("rtsp")) {}
        }
        else
        {
          StringBuilder localStringBuilder = new StringBuilder().append("//");
          str1 = paramUri.getHost();
          str2 = "";
          if (str1 != null) {
            str1 = paramUri.getHost();
          } else {
            str1 = "";
          }
          localStringBuilder = localStringBuilder.append(str1);
          str1 = str2;
          if (paramUri.getPort() != -1) {
            str1 = ":" + paramUri.getPort();
          }
          str1 = str1 + "/...";
        }
      }
      else
      {
        paramUri = new StringBuilder(64);
        paramUri.append(str3);
        paramUri.append(':');
        if (str2 != null) {
          for (int i = 0; i < str2.length(); i++)
          {
            char c = str2.charAt(i);
            if ((c != '-') && (c != '@') && (c != '.')) {
              paramUri.append('x');
            } else {
              paramUri.append(c);
            }
          }
        }
        return paramUri.toString();
      }
    }
    paramUri = new StringBuilder(64);
    if (str3 != null)
    {
      paramUri.append(str3);
      paramUri.append(':');
    }
    if (str1 != null) {
      paramUri.append(str1);
    }
    return paramUri.toString();
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/core/net/UriCompat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */