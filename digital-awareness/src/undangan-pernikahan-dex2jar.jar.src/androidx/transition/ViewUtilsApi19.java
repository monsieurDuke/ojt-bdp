package androidx.transition;

import android.view.View;

class ViewUtilsApi19
  extends ViewUtilsBase
{
  private static boolean sTryHiddenTransitionAlpha = true;
  
  public void clearNonTransitionAlpha(View paramView) {}
  
  public float getTransitionAlpha(View paramView)
  {
    if (sTryHiddenTransitionAlpha) {
      try
      {
        float f = paramView.getTransitionAlpha();
        return f;
      }
      catch (NoSuchMethodError localNoSuchMethodError)
      {
        sTryHiddenTransitionAlpha = false;
      }
    }
    return paramView.getAlpha();
  }
  
  public void saveNonTransitionAlpha(View paramView) {}
  
  public void setTransitionAlpha(View paramView, float paramFloat)
  {
    if (sTryHiddenTransitionAlpha) {
      try
      {
        paramView.setTransitionAlpha(paramFloat);
        return;
      }
      catch (NoSuchMethodError localNoSuchMethodError)
      {
        sTryHiddenTransitionAlpha = false;
      }
    }
    paramView.setAlpha(paramFloat);
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/transition/ViewUtilsApi19.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */