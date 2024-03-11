package androidx.constraintlayout.core.motion.utils;

public class Rect
{
  public int bottom;
  public int left;
  public int right;
  public int top;
  
  public int height()
  {
    return this.bottom - this.top;
  }
  
  public int width()
  {
    return this.right - this.left;
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/constraintlayout/core/motion/utils/Rect.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */