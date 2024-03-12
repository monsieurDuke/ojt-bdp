package com.google.android.material.slider;

import java.util.Locale;
import mt.Log229316;
import mt.Log5ECF72;
import mt.LogE84000;

public final class BasicLabelFormatter
  implements LabelFormatter
{
  private static final int BILLION = 1000000000;
  private static final int MILLION = 1000000;
  private static final int THOUSAND = 1000;
  private static final long TRILLION = 1000000000000L;
  
  public String getFormattedValue(float paramFloat)
  {
    if (paramFloat >= 1.0E12F)
    {
      str = String.format(Locale.US, "%.1fT", new Object[] { Float.valueOf(paramFloat / 1.0E12F) });
      Log5ECF72.a(str);
      LogE84000.a(str);
      Log229316.a(str);
      return str;
    }
    if (paramFloat >= 1.0E9F)
    {
      str = String.format(Locale.US, "%.1fB", new Object[] { Float.valueOf(paramFloat / 1.0E9F) });
      Log5ECF72.a(str);
      LogE84000.a(str);
      Log229316.a(str);
      return str;
    }
    if (paramFloat >= 1000000.0F)
    {
      str = String.format(Locale.US, "%.1fM", new Object[] { Float.valueOf(paramFloat / 1000000.0F) });
      Log5ECF72.a(str);
      LogE84000.a(str);
      Log229316.a(str);
      return str;
    }
    if (paramFloat >= 1000.0F)
    {
      str = String.format(Locale.US, "%.1fK", new Object[] { Float.valueOf(paramFloat / 1000.0F) });
      Log5ECF72.a(str);
      LogE84000.a(str);
      Log229316.a(str);
      return str;
    }
    String str = String.format(Locale.US, "%.0f", new Object[] { Float.valueOf(paramFloat) });
    Log5ECF72.a(str);
    LogE84000.a(str);
    Log229316.a(str);
    return str;
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/com/google/android/material/slider/BasicLabelFormatter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */