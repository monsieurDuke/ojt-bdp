package androidx.core.view;

import android.os.Build.VERSION;
import android.view.View;
import android.view.Window;

public final class WindowCompat
{
  public static final int FEATURE_ACTION_BAR = 8;
  public static final int FEATURE_ACTION_BAR_OVERLAY = 9;
  public static final int FEATURE_ACTION_MODE_OVERLAY = 10;
  
  public static WindowInsetsControllerCompat getInsetsController(Window paramWindow, View paramView)
  {
    return new WindowInsetsControllerCompat(paramWindow, paramView);
  }
  
  public static <T extends View> T requireViewById(Window paramWindow, int paramInt)
  {
    if (Build.VERSION.SDK_INT >= 28) {
      return (View)Api28Impl.requireViewById(paramWindow, paramInt);
    }
    paramWindow = paramWindow.findViewById(paramInt);
    if (paramWindow != null) {
      return paramWindow;
    }
    throw new IllegalArgumentException("ID does not reference a View inside this Window");
  }
  
  public static void setDecorFitsSystemWindows(Window paramWindow, boolean paramBoolean)
  {
    if (Build.VERSION.SDK_INT >= 30) {
      Api30Impl.setDecorFitsSystemWindows(paramWindow, paramBoolean);
    } else if (Build.VERSION.SDK_INT >= 16) {
      Api16Impl.setDecorFitsSystemWindows(paramWindow, paramBoolean);
    }
  }
  
  static class Api16Impl
  {
    static void setDecorFitsSystemWindows(Window paramWindow, boolean paramBoolean)
    {
      paramWindow = paramWindow.getDecorView();
      int i = paramWindow.getSystemUiVisibility();
      if (paramBoolean) {
        i &= 0xF8FF;
      } else {
        i |= 0x700;
      }
      paramWindow.setSystemUiVisibility(i);
    }
  }
  
  static class Api28Impl
  {
    static <T> T requireViewById(Window paramWindow, int paramInt)
    {
      return paramWindow.requireViewById(paramInt);
    }
  }
  
  static class Api30Impl
  {
    static void setDecorFitsSystemWindows(Window paramWindow, boolean paramBoolean)
    {
      paramWindow.setDecorFitsSystemWindows(paramBoolean);
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/core/view/WindowCompat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */