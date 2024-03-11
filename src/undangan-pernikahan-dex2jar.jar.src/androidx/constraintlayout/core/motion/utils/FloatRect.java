package androidx.constraintlayout.core.motion.utils;

public class FloatRect
{
  public float bottom;
  public float left;
  public float right;
  public float top;
  
  public final float centerX()
  {
    return (this.left + this.right) * 0.5F;
  }
  
  public final float centerY()
  {
    return (this.top + this.bottom) * 0.5F;
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/constraintlayout/core/motion/utils/FloatRect.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */