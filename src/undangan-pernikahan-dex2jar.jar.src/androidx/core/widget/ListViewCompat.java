package androidx.core.widget;

import android.os.Build.VERSION;
import android.view.View;
import android.widget.ListView;

public final class ListViewCompat
{
  public static boolean canScrollList(ListView paramListView, int paramInt)
  {
    if (Build.VERSION.SDK_INT >= 19) {
      return Api19Impl.canScrollList(paramListView, paramInt);
    }
    int j = paramListView.getChildCount();
    boolean bool1 = false;
    boolean bool2 = false;
    if (j == 0) {
      return false;
    }
    int i = paramListView.getFirstVisiblePosition();
    if (paramInt > 0)
    {
      paramInt = paramListView.getChildAt(j - 1).getBottom();
      if (i + j >= paramListView.getCount())
      {
        bool1 = bool2;
        if (paramInt <= paramListView.getHeight() - paramListView.getListPaddingBottom()) {}
      }
      else
      {
        bool1 = true;
      }
      return bool1;
    }
    paramInt = paramListView.getChildAt(0).getTop();
    if ((i > 0) || (paramInt < paramListView.getListPaddingTop())) {
      bool1 = true;
    }
    return bool1;
  }
  
  public static void scrollListBy(ListView paramListView, int paramInt)
  {
    if (Build.VERSION.SDK_INT >= 19)
    {
      Api19Impl.scrollListBy(paramListView, paramInt);
    }
    else
    {
      int i = paramListView.getFirstVisiblePosition();
      if (i == -1) {
        return;
      }
      View localView = paramListView.getChildAt(0);
      if (localView == null) {
        return;
      }
      paramListView.setSelectionFromTop(i, localView.getTop() - paramInt);
    }
  }
  
  static class Api19Impl
  {
    static boolean canScrollList(ListView paramListView, int paramInt)
    {
      return paramListView.canScrollList(paramInt);
    }
    
    static void scrollListBy(ListView paramListView, int paramInt)
    {
      paramListView.scrollListBy(paramInt);
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/core/widget/ListViewCompat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */