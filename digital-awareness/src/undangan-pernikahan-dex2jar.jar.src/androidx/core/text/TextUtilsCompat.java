package androidx.core.text;

import android.os.Build.VERSION;
import android.text.TextUtils;
import java.util.Locale;
import mt.Log229316;
import mt.Log5ECF72;
import mt.LogE84000;

public final class TextUtilsCompat
{
  private static final String ARAB_SCRIPT_SUBTAG = "Arab";
  private static final String HEBR_SCRIPT_SUBTAG = "Hebr";
  private static final Locale ROOT = new Locale("", "");
  
  private static int getLayoutDirectionFromFirstChar(Locale paramLocale)
  {
    switch (Character.getDirectionality(paramLocale.getDisplayName(paramLocale).charAt(0)))
    {
    default: 
      return 0;
    }
    return 1;
  }
  
  public static int getLayoutDirectionFromLocale(Locale paramLocale)
  {
    if (Build.VERSION.SDK_INT >= 17) {
      return Api17Impl.getLayoutDirectionFromLocale(paramLocale);
    }
    if ((paramLocale != null) && (!paramLocale.equals(ROOT)))
    {
      String str = ICUCompat.maximizeAndGetScript(paramLocale);
      Log5ECF72.a(str);
      LogE84000.a(str);
      Log229316.a(str);
      if (str == null) {
        return getLayoutDirectionFromFirstChar(paramLocale);
      }
      if ((str.equalsIgnoreCase("Arab")) || (str.equalsIgnoreCase("Hebr"))) {
        return 1;
      }
    }
    return 0;
  }
  
  public static String htmlEncode(String paramString)
  {
    if (Build.VERSION.SDK_INT >= 17)
    {
      paramString = TextUtils.htmlEncode(paramString);
      Log5ECF72.a(paramString);
      LogE84000.a(paramString);
      Log229316.a(paramString);
      return paramString;
    }
    StringBuilder localStringBuilder = new StringBuilder();
    for (int i = 0; i < paramString.length(); i++)
    {
      char c = paramString.charAt(i);
      switch (c)
      {
      default: 
        localStringBuilder.append(c);
        break;
      case '>': 
        localStringBuilder.append("&gt;");
        break;
      case '<': 
        localStringBuilder.append("&lt;");
        break;
      case '\'': 
        localStringBuilder.append("&#39;");
        break;
      case '&': 
        localStringBuilder.append("&amp;");
        break;
      case '"': 
        localStringBuilder.append("&quot;");
      }
    }
    return localStringBuilder.toString();
  }
  
  static class Api17Impl
  {
    static int getLayoutDirectionFromLocale(Locale paramLocale)
    {
      return TextUtils.getLayoutDirectionFromLocale(paramLocale);
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/core/text/TextUtilsCompat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */