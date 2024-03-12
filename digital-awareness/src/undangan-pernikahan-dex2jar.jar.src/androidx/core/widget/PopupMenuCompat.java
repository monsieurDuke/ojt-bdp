package androidx.core.widget;

import android.os.Build.VERSION;
import android.view.View.OnTouchListener;
import android.widget.PopupMenu;

public final class PopupMenuCompat
{
  public static View.OnTouchListener getDragToOpenListener(Object paramObject)
  {
    if (Build.VERSION.SDK_INT >= 19) {
      return Api19Impl.getDragToOpenListener((PopupMenu)paramObject);
    }
    return null;
  }
  
  static class Api19Impl
  {
    static View.OnTouchListener getDragToOpenListener(PopupMenu paramPopupMenu)
    {
      return paramPopupMenu.getDragToOpenListener();
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/core/widget/PopupMenuCompat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */