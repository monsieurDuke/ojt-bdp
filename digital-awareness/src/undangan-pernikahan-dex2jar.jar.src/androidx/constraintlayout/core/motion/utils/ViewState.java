package androidx.constraintlayout.core.motion.utils;

import androidx.constraintlayout.core.motion.MotionWidget;

public class ViewState
{
  public int bottom;
  public int left;
  public int right;
  public float rotation;
  public int top;
  
  public void getState(MotionWidget paramMotionWidget)
  {
    this.left = paramMotionWidget.getLeft();
    this.top = paramMotionWidget.getTop();
    this.right = paramMotionWidget.getRight();
    this.bottom = paramMotionWidget.getBottom();
    this.rotation = ((int)paramMotionWidget.getRotationZ());
  }
  
  public int height()
  {
    return this.bottom - this.top;
  }
  
  public int width()
  {
    return this.right - this.left;
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/constraintlayout/core/motion/utils/ViewState.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */