package androidx.transition;

import android.os.Build.VERSION;
import android.view.ViewGroup;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

class ViewGroupUtils
{
  private static Method sGetChildDrawingOrderMethod;
  private static boolean sGetChildDrawingOrderMethodFetched;
  private static boolean sTryHiddenSuppressLayout = true;
  
  static int getChildDrawingOrder(ViewGroup paramViewGroup, int paramInt)
  {
    if (Build.VERSION.SDK_INT >= 29) {
      return paramViewGroup.getChildDrawingOrder(paramInt);
    }
    if (!sGetChildDrawingOrderMethodFetched)
    {
      try
      {
        Method localMethod1 = ViewGroup.class.getDeclaredMethod("getChildDrawingOrder", new Class[] { Integer.TYPE, Integer.TYPE });
        sGetChildDrawingOrderMethod = localMethod1;
        localMethod1.setAccessible(true);
      }
      catch (NoSuchMethodException localNoSuchMethodException) {}
      sGetChildDrawingOrderMethodFetched = true;
    }
    Method localMethod2 = sGetChildDrawingOrderMethod;
    if (localMethod2 != null) {
      try
      {
        int i = ((Integer)localMethod2.invoke(paramViewGroup, new Object[] { Integer.valueOf(paramViewGroup.getChildCount()), Integer.valueOf(paramInt) })).intValue();
        return i;
      }
      catch (InvocationTargetException paramViewGroup) {}catch (IllegalAccessException paramViewGroup) {}
    }
    return paramInt;
  }
  
  static ViewGroupOverlayImpl getOverlay(ViewGroup paramViewGroup)
  {
    if (Build.VERSION.SDK_INT >= 18) {
      return new ViewGroupOverlayApi18(paramViewGroup);
    }
    return ViewGroupOverlayApi14.createFrom(paramViewGroup);
  }
  
  private static void hiddenSuppressLayout(ViewGroup paramViewGroup, boolean paramBoolean)
  {
    if (sTryHiddenSuppressLayout) {
      try
      {
        paramViewGroup.suppressLayout(paramBoolean);
      }
      catch (NoSuchMethodError paramViewGroup)
      {
        sTryHiddenSuppressLayout = false;
      }
    }
  }
  
  static void suppressLayout(ViewGroup paramViewGroup, boolean paramBoolean)
  {
    if (Build.VERSION.SDK_INT >= 29) {
      paramViewGroup.suppressLayout(paramBoolean);
    } else if (Build.VERSION.SDK_INT >= 18) {
      hiddenSuppressLayout(paramViewGroup, paramBoolean);
    } else {
      ViewGroupUtilsApi14.suppressLayout(paramViewGroup, paramBoolean);
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/transition/ViewGroupUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */