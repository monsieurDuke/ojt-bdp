package androidx.core.os;

import android.content.res.Configuration;
import android.os.Build.VERSION;
import android.os.LocaleList;
import java.util.Locale;

public final class ConfigurationCompat
{
  public static LocaleListCompat getLocales(Configuration paramConfiguration)
  {
    if (Build.VERSION.SDK_INT >= 24) {
      return LocaleListCompat.wrap(Api24Impl.getLocales(paramConfiguration));
    }
    return LocaleListCompat.create(new Locale[] { paramConfiguration.locale });
  }
  
  static class Api24Impl
  {
    static LocaleList getLocales(Configuration paramConfiguration)
    {
      return paramConfiguration.getLocales();
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/core/os/ConfigurationCompat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */