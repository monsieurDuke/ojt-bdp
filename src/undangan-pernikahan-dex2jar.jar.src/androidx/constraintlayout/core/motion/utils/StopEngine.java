package androidx.constraintlayout.core.motion.utils;

public abstract interface StopEngine
{
  public abstract String debug(String paramString, float paramFloat);
  
  public abstract float getInterpolation(float paramFloat);
  
  public abstract float getVelocity();
  
  public abstract float getVelocity(float paramFloat);
  
  public abstract boolean isStopped();
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/constraintlayout/core/motion/utils/StopEngine.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */