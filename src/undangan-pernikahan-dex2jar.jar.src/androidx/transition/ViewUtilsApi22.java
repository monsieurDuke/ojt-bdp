package androidx.transition;

import android.view.View;

class ViewUtilsApi22
  extends ViewUtilsApi21
{
  private static boolean sTryHiddenSetLeftTopRightBottom = true;
  
  public void setLeftTopRightBottom(View paramView, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    if (sTryHiddenSetLeftTopRightBottom) {
      try
      {
        paramView.setLeftTopRightBottom(paramInt1, paramInt2, paramInt3, paramInt4);
      }
      catch (NoSuchMethodError paramView)
      {
        sTryHiddenSetLeftTopRightBottom = false;
      }
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/transition/ViewUtilsApi22.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */