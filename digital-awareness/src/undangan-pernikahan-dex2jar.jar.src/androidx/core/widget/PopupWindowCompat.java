package androidx.core.widget;

import android.os.Build.VERSION;
import android.util.Log;
import android.view.View;
import android.widget.PopupWindow;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public final class PopupWindowCompat
{
  private static final String TAG = "PopupWindowCompatApi21";
  private static Method sGetWindowLayoutTypeMethod;
  private static boolean sGetWindowLayoutTypeMethodAttempted;
  private static Field sOverlapAnchorField;
  private static boolean sOverlapAnchorFieldAttempted;
  private static Method sSetWindowLayoutTypeMethod;
  private static boolean sSetWindowLayoutTypeMethodAttempted;
  
  public static boolean getOverlapAnchor(PopupWindow paramPopupWindow)
  {
    if (Build.VERSION.SDK_INT >= 23) {
      return Api23Impl.getOverlapAnchor(paramPopupWindow);
    }
    if (Build.VERSION.SDK_INT >= 21)
    {
      if (!sOverlapAnchorFieldAttempted)
      {
        try
        {
          Field localField1 = PopupWindow.class.getDeclaredField("mOverlapAnchor");
          sOverlapAnchorField = localField1;
          localField1.setAccessible(true);
        }
        catch (NoSuchFieldException localNoSuchFieldException)
        {
          Log.i("PopupWindowCompatApi21", "Could not fetch mOverlapAnchor field from PopupWindow", localNoSuchFieldException);
        }
        sOverlapAnchorFieldAttempted = true;
      }
      Field localField2 = sOverlapAnchorField;
      if (localField2 != null) {
        try
        {
          boolean bool = ((Boolean)localField2.get(paramPopupWindow)).booleanValue();
          return bool;
        }
        catch (IllegalAccessException paramPopupWindow)
        {
          Log.i("PopupWindowCompatApi21", "Could not get overlap anchor field in PopupWindow", paramPopupWindow);
        }
      }
    }
    return false;
  }
  
  public static int getWindowLayoutType(PopupWindow paramPopupWindow)
  {
    if (Build.VERSION.SDK_INT >= 23) {
      return Api23Impl.getWindowLayoutType(paramPopupWindow);
    }
    if (!sGetWindowLayoutTypeMethodAttempted)
    {
      try
      {
        Method localMethod1 = PopupWindow.class.getDeclaredMethod("getWindowLayoutType", new Class[0]);
        sGetWindowLayoutTypeMethod = localMethod1;
        localMethod1.setAccessible(true);
      }
      catch (Exception localException) {}
      sGetWindowLayoutTypeMethodAttempted = true;
    }
    Method localMethod2 = sGetWindowLayoutTypeMethod;
    if (localMethod2 != null) {
      try
      {
        int i = ((Integer)localMethod2.invoke(paramPopupWindow, new Object[0])).intValue();
        return i;
      }
      catch (Exception paramPopupWindow) {}
    }
    return 0;
  }
  
  public static void setOverlapAnchor(PopupWindow paramPopupWindow, boolean paramBoolean)
  {
    if (Build.VERSION.SDK_INT >= 23)
    {
      Api23Impl.setOverlapAnchor(paramPopupWindow, paramBoolean);
    }
    else if (Build.VERSION.SDK_INT >= 21)
    {
      if (!sOverlapAnchorFieldAttempted)
      {
        try
        {
          Field localField1 = PopupWindow.class.getDeclaredField("mOverlapAnchor");
          sOverlapAnchorField = localField1;
          localField1.setAccessible(true);
        }
        catch (NoSuchFieldException localNoSuchFieldException)
        {
          Log.i("PopupWindowCompatApi21", "Could not fetch mOverlapAnchor field from PopupWindow", localNoSuchFieldException);
        }
        sOverlapAnchorFieldAttempted = true;
      }
      Field localField2 = sOverlapAnchorField;
      if (localField2 != null) {
        try
        {
          localField2.set(paramPopupWindow, Boolean.valueOf(paramBoolean));
        }
        catch (IllegalAccessException paramPopupWindow)
        {
          Log.i("PopupWindowCompatApi21", "Could not set overlap anchor field in PopupWindow", paramPopupWindow);
        }
      }
    }
  }
  
  public static void setWindowLayoutType(PopupWindow paramPopupWindow, int paramInt)
  {
    if (Build.VERSION.SDK_INT >= 23)
    {
      Api23Impl.setWindowLayoutType(paramPopupWindow, paramInt);
    }
    else
    {
      if (!sSetWindowLayoutTypeMethodAttempted)
      {
        try
        {
          Method localMethod1 = PopupWindow.class.getDeclaredMethod("setWindowLayoutType", new Class[] { Integer.TYPE });
          sSetWindowLayoutTypeMethod = localMethod1;
          localMethod1.setAccessible(true);
        }
        catch (Exception localException) {}
        sSetWindowLayoutTypeMethodAttempted = true;
      }
      Method localMethod2 = sSetWindowLayoutTypeMethod;
      if (localMethod2 != null) {
        try
        {
          localMethod2.invoke(paramPopupWindow, new Object[] { Integer.valueOf(paramInt) });
        }
        catch (Exception paramPopupWindow) {}
      }
    }
  }
  
  public static void showAsDropDown(PopupWindow paramPopupWindow, View paramView, int paramInt1, int paramInt2, int paramInt3)
  {
    if (Build.VERSION.SDK_INT >= 19)
    {
      Api19Impl.showAsDropDown(paramPopupWindow, paramView, paramInt1, paramInt2, paramInt3);
    }
    else
    {
      int i = paramInt1;
      if ((GravityCompat.getAbsoluteGravity(paramInt3, ViewCompat.getLayoutDirection(paramView)) & 0x7) == 5) {
        i = paramInt1 - (paramPopupWindow.getWidth() - paramView.getWidth());
      }
      paramPopupWindow.showAsDropDown(paramView, i, paramInt2);
    }
  }
  
  static class Api19Impl
  {
    static void showAsDropDown(PopupWindow paramPopupWindow, View paramView, int paramInt1, int paramInt2, int paramInt3)
    {
      paramPopupWindow.showAsDropDown(paramView, paramInt1, paramInt2, paramInt3);
    }
  }
  
  static class Api23Impl
  {
    static boolean getOverlapAnchor(PopupWindow paramPopupWindow)
    {
      return paramPopupWindow.getOverlapAnchor();
    }
    
    static int getWindowLayoutType(PopupWindow paramPopupWindow)
    {
      return paramPopupWindow.getWindowLayoutType();
    }
    
    static void setOverlapAnchor(PopupWindow paramPopupWindow, boolean paramBoolean)
    {
      paramPopupWindow.setOverlapAnchor(paramBoolean);
    }
    
    static void setWindowLayoutType(PopupWindow paramPopupWindow, int paramInt)
    {
      paramPopupWindow.setWindowLayoutType(paramInt);
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/core/widget/PopupWindowCompat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */