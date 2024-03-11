package androidx.core.view;

import android.app.UiModeManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Point;
import android.os.Build;
import android.os.Build.VERSION;
import android.text.TextUtils;
import android.view.Display;
import android.view.Display.Mode;
import androidx.core.util.Preconditions;
import java.lang.reflect.Method;
import mt.Log229316;
import mt.Log5ECF72;
import mt.LogE84000;

public final class DisplayCompat
{
  private static final int DISPLAY_SIZE_4K_HEIGHT = 2160;
  private static final int DISPLAY_SIZE_4K_WIDTH = 3840;
  
  static Point getCurrentDisplaySizeFromWorkarounds(Context paramContext, Display paramDisplay)
  {
    Point localPoint;
    if (Build.VERSION.SDK_INT < 28) {
      localPoint = parsePhysicalDisplaySizeFromSystemProperties("sys.display-size", paramDisplay);
    } else {
      localPoint = parsePhysicalDisplaySizeFromSystemProperties("vendor.display-size", paramDisplay);
    }
    if (localPoint != null) {
      return localPoint;
    }
    boolean bool = isSonyBravia4kTv(paramContext);
    paramContext = null;
    if (bool)
    {
      if (isCurrentModeTheLargestMode(paramDisplay)) {
        paramContext = new Point(3840, 2160);
      }
      return paramContext;
    }
    return null;
  }
  
  private static Point getDisplaySize(Context paramContext, Display paramDisplay)
  {
    paramContext = getCurrentDisplaySizeFromWorkarounds(paramContext, paramDisplay);
    if (paramContext != null) {
      return paramContext;
    }
    paramContext = new Point();
    if (Build.VERSION.SDK_INT >= 17) {
      Api17Impl.getRealSize(paramDisplay, paramContext);
    } else {
      paramDisplay.getSize(paramContext);
    }
    return paramContext;
  }
  
  public static ModeCompat getMode(Context paramContext, Display paramDisplay)
  {
    if (Build.VERSION.SDK_INT >= 23) {
      return Api23Impl.getMode(paramContext, paramDisplay);
    }
    return new ModeCompat(getDisplaySize(paramContext, paramDisplay));
  }
  
  public static ModeCompat[] getSupportedModes(Context paramContext, Display paramDisplay)
  {
    if (Build.VERSION.SDK_INT >= 23) {
      return Api23Impl.getSupportedModes(paramContext, paramDisplay);
    }
    return new ModeCompat[] { getMode(paramContext, paramDisplay) };
  }
  
  private static String getSystemProperty(String paramString)
  {
    try
    {
      Class localClass = Class.forName("android.os.SystemProperties");
      paramString = (String)localClass.getMethod("get", new Class[] { String.class }).invoke(localClass, new Object[] { paramString });
      return paramString;
    }
    catch (Exception paramString) {}
    return null;
  }
  
  static boolean isCurrentModeTheLargestMode(Display paramDisplay)
  {
    if (Build.VERSION.SDK_INT >= 23) {
      return Api23Impl.isCurrentModeTheLargestMode(paramDisplay);
    }
    return true;
  }
  
  private static boolean isSonyBravia4kTv(Context paramContext)
  {
    boolean bool;
    if ((isTv(paramContext)) && ("Sony".equals(Build.MANUFACTURER)) && (Build.MODEL.startsWith("BRAVIA")) && (paramContext.getPackageManager().hasSystemFeature("com.sony.dtv.hardware.panel.qfhd"))) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  private static boolean isTv(Context paramContext)
  {
    paramContext = (UiModeManager)paramContext.getSystemService("uimode");
    boolean bool;
    if ((paramContext != null) && (paramContext.getCurrentModeType() == 4)) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  private static Point parseDisplaySize(String paramString)
    throws NumberFormatException
  {
    paramString = paramString.trim().split("x", -1);
    if (paramString.length == 2)
    {
      int i = Integer.parseInt(paramString[0]);
      int j = Integer.parseInt(paramString[1]);
      if ((i > 0) && (j > 0)) {
        return new Point(i, j);
      }
    }
    throw new NumberFormatException();
  }
  
  private static Point parsePhysicalDisplaySizeFromSystemProperties(String paramString, Display paramDisplay)
  {
    if (paramDisplay.getDisplayId() != 0) {
      return null;
    }
    paramString = getSystemProperty(paramString);
    Log5ECF72.a(paramString);
    LogE84000.a(paramString);
    Log229316.a(paramString);
    if ((!TextUtils.isEmpty(paramString)) && (paramString != null)) {
      try
      {
        paramString = parseDisplaySize(paramString);
        return paramString;
      }
      catch (NumberFormatException paramString)
      {
        return null;
      }
    }
    return null;
  }
  
  static class Api17Impl
  {
    static void getRealSize(Display paramDisplay, Point paramPoint)
    {
      paramDisplay.getRealSize(paramPoint);
    }
  }
  
  static class Api23Impl
  {
    static DisplayCompat.ModeCompat getMode(Context paramContext, Display paramDisplay)
    {
      Display.Mode localMode = paramDisplay.getMode();
      paramContext = DisplayCompat.getCurrentDisplaySizeFromWorkarounds(paramContext, paramDisplay);
      if ((paramContext != null) && (!physicalSizeEquals(localMode, paramContext))) {
        paramContext = new DisplayCompat.ModeCompat(localMode, paramContext);
      } else {
        paramContext = new DisplayCompat.ModeCompat(localMode, true);
      }
      return paramContext;
    }
    
    public static DisplayCompat.ModeCompat[] getSupportedModes(Context paramContext, Display paramDisplay)
    {
      Display.Mode[] arrayOfMode = paramDisplay.getSupportedModes();
      DisplayCompat.ModeCompat[] arrayOfModeCompat = new DisplayCompat.ModeCompat[arrayOfMode.length];
      Display.Mode localMode = paramDisplay.getMode();
      paramDisplay = DisplayCompat.getCurrentDisplaySizeFromWorkarounds(paramContext, paramDisplay);
      int i;
      if ((paramDisplay != null) && (!physicalSizeEquals(localMode, paramDisplay))) {
        i = 0;
      }
      while (i < arrayOfMode.length)
      {
        if (physicalSizeEquals(arrayOfMode[i], localMode)) {
          paramContext = new DisplayCompat.ModeCompat(arrayOfMode[i], paramDisplay);
        } else {
          paramContext = new DisplayCompat.ModeCompat(arrayOfMode[i], false);
        }
        arrayOfModeCompat[i] = paramContext;
        i++;
        continue;
        for (i = 0; i < arrayOfMode.length; i++)
        {
          boolean bool = physicalSizeEquals(arrayOfMode[i], localMode);
          arrayOfModeCompat[i] = new DisplayCompat.ModeCompat(arrayOfMode[i], bool);
        }
      }
      return arrayOfModeCompat;
    }
    
    static boolean isCurrentModeTheLargestMode(Display paramDisplay)
    {
      Display.Mode localMode = paramDisplay.getMode();
      paramDisplay = paramDisplay.getSupportedModes();
      int j = paramDisplay.length;
      int i = 0;
      while (i < j)
      {
        Object localObject = paramDisplay[i];
        if ((localMode.getPhysicalHeight() >= ((Display.Mode)localObject).getPhysicalHeight()) && (localMode.getPhysicalWidth() >= ((Display.Mode)localObject).getPhysicalWidth())) {
          i++;
        } else {
          return false;
        }
      }
      return true;
    }
    
    static boolean physicalSizeEquals(Display.Mode paramMode, Point paramPoint)
    {
      boolean bool;
      if (((paramMode.getPhysicalWidth() == paramPoint.x) && (paramMode.getPhysicalHeight() == paramPoint.y)) || ((paramMode.getPhysicalWidth() == paramPoint.y) && (paramMode.getPhysicalHeight() == paramPoint.x))) {
        bool = true;
      } else {
        bool = false;
      }
      return bool;
    }
    
    static boolean physicalSizeEquals(Display.Mode paramMode1, Display.Mode paramMode2)
    {
      boolean bool;
      if ((paramMode1.getPhysicalWidth() == paramMode2.getPhysicalWidth()) && (paramMode1.getPhysicalHeight() == paramMode2.getPhysicalHeight())) {
        bool = true;
      } else {
        bool = false;
      }
      return bool;
    }
  }
  
  public static final class ModeCompat
  {
    private final boolean mIsNative;
    private final Display.Mode mMode;
    private final Point mPhysicalSize;
    
    ModeCompat(Point paramPoint)
    {
      Preconditions.checkNotNull(paramPoint, "physicalSize == null");
      this.mPhysicalSize = paramPoint;
      this.mMode = null;
      this.mIsNative = true;
    }
    
    ModeCompat(Display.Mode paramMode, Point paramPoint)
    {
      Preconditions.checkNotNull(paramMode, "mode == null, can't wrap a null reference");
      Preconditions.checkNotNull(paramPoint, "physicalSize == null");
      this.mPhysicalSize = paramPoint;
      this.mMode = paramMode;
      this.mIsNative = true;
    }
    
    ModeCompat(Display.Mode paramMode, boolean paramBoolean)
    {
      Preconditions.checkNotNull(paramMode, "mode == null, can't wrap a null reference");
      this.mPhysicalSize = new Point(Api23Impl.getPhysicalWidth(paramMode), Api23Impl.getPhysicalHeight(paramMode));
      this.mMode = paramMode;
      this.mIsNative = paramBoolean;
    }
    
    public int getPhysicalHeight()
    {
      return this.mPhysicalSize.y;
    }
    
    public int getPhysicalWidth()
    {
      return this.mPhysicalSize.x;
    }
    
    @Deprecated
    public boolean isNative()
    {
      return this.mIsNative;
    }
    
    public Display.Mode toMode()
    {
      return this.mMode;
    }
    
    static class Api23Impl
    {
      static int getPhysicalHeight(Display.Mode paramMode)
      {
        return paramMode.getPhysicalHeight();
      }
      
      static int getPhysicalWidth(Display.Mode paramMode)
      {
        return paramMode.getPhysicalWidth();
      }
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/core/view/DisplayCompat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */