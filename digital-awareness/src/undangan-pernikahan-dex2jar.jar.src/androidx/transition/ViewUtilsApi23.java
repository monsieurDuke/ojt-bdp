package androidx.transition;

import android.os.Build.VERSION;
import android.view.View;

class ViewUtilsApi23
  extends ViewUtilsApi22
{
  private static boolean sTryHiddenSetTransitionVisibility = true;
  
  public void setTransitionVisibility(View paramView, int paramInt)
  {
    if (Build.VERSION.SDK_INT == 28) {
      super.setTransitionVisibility(paramView, paramInt);
    } else if (sTryHiddenSetTransitionVisibility) {
      try
      {
        paramView.setTransitionVisibility(paramInt);
      }
      catch (NoSuchMethodError paramView)
      {
        sTryHiddenSetTransitionVisibility = false;
      }
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/transition/ViewUtilsApi23.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */