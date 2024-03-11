package androidx.core.telephony.mbms;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build.VERSION;
import android.os.LocaleList;
import android.telephony.mbms.ServiceInfo;
import java.util.Iterator;
import java.util.Locale;
import java.util.Set;

public final class MbmsHelper
{
  public static CharSequence getBestNameForService(Context paramContext, ServiceInfo paramServiceInfo)
  {
    if (Build.VERSION.SDK_INT >= 28) {
      return Api28Impl.getBestNameForService(paramContext, paramServiceInfo);
    }
    return null;
  }
  
  static class Api28Impl
  {
    static CharSequence getBestNameForService(Context paramContext, ServiceInfo paramServiceInfo)
    {
      Object localObject2 = paramServiceInfo.getNamedContentLocales();
      boolean bool = ((Set)localObject2).isEmpty();
      Object localObject1 = null;
      if (bool) {
        return null;
      }
      String[] arrayOfString = new String[((Set)localObject2).size()];
      int i = 0;
      localObject2 = paramServiceInfo.getNamedContentLocales().iterator();
      while (((Iterator)localObject2).hasNext())
      {
        arrayOfString[i] = ((Locale)((Iterator)localObject2).next()).toLanguageTag();
        i++;
      }
      paramContext = paramContext.getResources().getConfiguration().getLocales().getFirstMatch(arrayOfString);
      if (paramContext == null) {
        paramContext = (Context)localObject1;
      } else {
        paramContext = paramServiceInfo.getNameForLocale(paramContext);
      }
      return paramContext;
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/core/telephony/mbms/MbmsHelper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */