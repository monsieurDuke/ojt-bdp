package androidx.core.hardware.display;

import android.content.Context;
import android.hardware.display.DisplayManager;
import android.os.Build.VERSION;
import android.view.Display;
import android.view.WindowManager;
import java.util.WeakHashMap;

public final class DisplayManagerCompat
{
  public static final String DISPLAY_CATEGORY_PRESENTATION = "android.hardware.display.category.PRESENTATION";
  private static final WeakHashMap<Context, DisplayManagerCompat> sInstances = new WeakHashMap();
  private final Context mContext;
  
  private DisplayManagerCompat(Context paramContext)
  {
    this.mContext = paramContext;
  }
  
  public static DisplayManagerCompat getInstance(Context paramContext)
  {
    synchronized (sInstances)
    {
      DisplayManagerCompat localDisplayManagerCompat2 = (DisplayManagerCompat)???.get(paramContext);
      DisplayManagerCompat localDisplayManagerCompat1 = localDisplayManagerCompat2;
      if (localDisplayManagerCompat2 == null)
      {
        localDisplayManagerCompat1 = new androidx/core/hardware/display/DisplayManagerCompat;
        localDisplayManagerCompat1.<init>(paramContext);
        ???.put(paramContext, localDisplayManagerCompat1);
      }
      return localDisplayManagerCompat1;
    }
  }
  
  public Display getDisplay(int paramInt)
  {
    if (Build.VERSION.SDK_INT >= 17) {
      return Api17Impl.getDisplay((DisplayManager)this.mContext.getSystemService("display"), paramInt);
    }
    Display localDisplay = ((WindowManager)this.mContext.getSystemService("window")).getDefaultDisplay();
    if (localDisplay.getDisplayId() == paramInt) {
      return localDisplay;
    }
    return null;
  }
  
  public Display[] getDisplays()
  {
    if (Build.VERSION.SDK_INT >= 17) {
      return Api17Impl.getDisplays((DisplayManager)this.mContext.getSystemService("display"));
    }
    return new Display[] { ((WindowManager)this.mContext.getSystemService("window")).getDefaultDisplay() };
  }
  
  public Display[] getDisplays(String paramString)
  {
    if (Build.VERSION.SDK_INT >= 17) {
      return Api17Impl.getDisplays((DisplayManager)this.mContext.getSystemService("display"));
    }
    if (paramString == null) {
      return new Display[0];
    }
    return new Display[] { ((WindowManager)this.mContext.getSystemService("window")).getDefaultDisplay() };
  }
  
  static class Api17Impl
  {
    static Display getDisplay(DisplayManager paramDisplayManager, int paramInt)
    {
      return paramDisplayManager.getDisplay(paramInt);
    }
    
    static Display[] getDisplays(DisplayManager paramDisplayManager)
    {
      return paramDisplayManager.getDisplays();
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/core/hardware/display/DisplayManagerCompat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */