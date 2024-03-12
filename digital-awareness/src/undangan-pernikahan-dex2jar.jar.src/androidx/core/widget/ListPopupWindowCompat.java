package androidx.core.widget;

import android.os.Build.VERSION;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ListPopupWindow;

public final class ListPopupWindowCompat
{
  public static View.OnTouchListener createDragToOpenListener(ListPopupWindow paramListPopupWindow, View paramView)
  {
    if (Build.VERSION.SDK_INT >= 19) {
      return Api19Impl.createDragToOpenListener(paramListPopupWindow, paramView);
    }
    return null;
  }
  
  @Deprecated
  public static View.OnTouchListener createDragToOpenListener(Object paramObject, View paramView)
  {
    return createDragToOpenListener((ListPopupWindow)paramObject, paramView);
  }
  
  static class Api19Impl
  {
    static View.OnTouchListener createDragToOpenListener(ListPopupWindow paramListPopupWindow, View paramView)
    {
      return paramListPopupWindow.createDragToOpenListener(paramView);
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/core/widget/ListPopupWindowCompat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */