package androidx.constraintlayout.motion.widget;

abstract interface ProxyInterface
{
  public abstract int designAccess(int paramInt1, String paramString, Object paramObject, float[] paramArrayOfFloat1, int paramInt2, float[] paramArrayOfFloat2, int paramInt3);
  
  public abstract float getKeyFramePosition(Object paramObject, int paramInt, float paramFloat1, float paramFloat2);
  
  public abstract Object getKeyframeAtLocation(Object paramObject, float paramFloat1, float paramFloat2);
  
  public abstract Boolean getPositionKeyframe(Object paramObject1, Object paramObject2, float paramFloat1, float paramFloat2, String[] paramArrayOfString, float[] paramArrayOfFloat);
  
  public abstract long getTransitionTimeMs();
  
  public abstract void setAttributes(int paramInt, String paramString, Object paramObject1, Object paramObject2);
  
  public abstract void setKeyFrame(Object paramObject1, int paramInt, String paramString, Object paramObject2);
  
  public abstract boolean setKeyFramePosition(Object paramObject, int paramInt1, int paramInt2, float paramFloat1, float paramFloat2);
  
  public abstract void setToolPosition(float paramFloat);
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/constraintlayout/motion/widget/ProxyInterface.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */