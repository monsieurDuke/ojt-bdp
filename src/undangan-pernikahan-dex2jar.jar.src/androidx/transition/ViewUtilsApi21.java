package androidx.transition;

import android.graphics.Matrix;
import android.view.View;

class ViewUtilsApi21
  extends ViewUtilsApi19
{
  private static boolean sTryHiddenSetAnimationMatrix = true;
  private static boolean sTryHiddenTransformMatrixToGlobal = true;
  private static boolean sTryHiddenTransformMatrixToLocal = true;
  
  public void setAnimationMatrix(View paramView, Matrix paramMatrix)
  {
    if (sTryHiddenSetAnimationMatrix) {
      try
      {
        paramView.setAnimationMatrix(paramMatrix);
      }
      catch (NoSuchMethodError paramView)
      {
        sTryHiddenSetAnimationMatrix = false;
      }
    }
  }
  
  public void transformMatrixToGlobal(View paramView, Matrix paramMatrix)
  {
    if (sTryHiddenTransformMatrixToGlobal) {
      try
      {
        paramView.transformMatrixToGlobal(paramMatrix);
      }
      catch (NoSuchMethodError paramView)
      {
        sTryHiddenTransformMatrixToGlobal = false;
      }
    }
  }
  
  public void transformMatrixToLocal(View paramView, Matrix paramMatrix)
  {
    if (sTryHiddenTransformMatrixToLocal) {
      try
      {
        paramView.transformMatrixToLocal(paramMatrix);
      }
      catch (NoSuchMethodError paramView)
      {
        sTryHiddenTransformMatrixToLocal = false;
      }
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/transition/ViewUtilsApi21.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */