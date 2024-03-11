package androidx.constraintlayout.core.motion.utils;

public class VelocityMatrix
{
  private static String TAG = "VelocityMatrix";
  float mDRotate;
  float mDScaleX;
  float mDScaleY;
  float mDTranslateX;
  float mDTranslateY;
  float mRotate;
  
  public void applyTransform(float paramFloat1, float paramFloat2, int paramInt1, int paramInt2, float[] paramArrayOfFloat)
  {
    float f2 = paramArrayOfFloat[0];
    float f1 = paramArrayOfFloat[1];
    paramFloat1 = (paramFloat1 - 0.5F) * 2.0F;
    paramFloat2 = (paramFloat2 - 0.5F) * 2.0F;
    float f7 = this.mDTranslateX;
    float f6 = this.mDTranslateY;
    float f3 = this.mDScaleX;
    float f8 = this.mDScaleY;
    float f9 = (float)Math.toRadians(this.mRotate);
    float f5 = (float)Math.toRadians(this.mDRotate);
    float f4 = (float)(-paramInt1 * paramFloat1 * Math.sin(f9) - paramInt2 * paramFloat2 * Math.cos(f9));
    f9 = (float)(paramInt1 * paramFloat1 * Math.cos(f9) - paramInt2 * paramFloat2 * Math.sin(f9));
    paramArrayOfFloat[0] = (f2 + f7 + f3 * paramFloat1 + f4 * f5);
    paramArrayOfFloat[1] = (f1 + f6 + f8 * paramFloat2 + f9 * f5);
  }
  
  public void clear()
  {
    this.mDRotate = 0.0F;
    this.mDTranslateY = 0.0F;
    this.mDTranslateX = 0.0F;
    this.mDScaleY = 0.0F;
    this.mDScaleX = 0.0F;
  }
  
  public void setRotationVelocity(KeyCycleOscillator paramKeyCycleOscillator, float paramFloat)
  {
    if (paramKeyCycleOscillator != null) {
      this.mDRotate = paramKeyCycleOscillator.getSlope(paramFloat);
    }
  }
  
  public void setRotationVelocity(SplineSet paramSplineSet, float paramFloat)
  {
    if (paramSplineSet != null)
    {
      this.mDRotate = paramSplineSet.getSlope(paramFloat);
      this.mRotate = paramSplineSet.get(paramFloat);
    }
  }
  
  public void setScaleVelocity(KeyCycleOscillator paramKeyCycleOscillator1, KeyCycleOscillator paramKeyCycleOscillator2, float paramFloat)
  {
    if (paramKeyCycleOscillator1 != null) {
      this.mDScaleX = paramKeyCycleOscillator1.getSlope(paramFloat);
    }
    if (paramKeyCycleOscillator2 != null) {
      this.mDScaleY = paramKeyCycleOscillator2.getSlope(paramFloat);
    }
  }
  
  public void setScaleVelocity(SplineSet paramSplineSet1, SplineSet paramSplineSet2, float paramFloat)
  {
    if (paramSplineSet1 != null) {
      this.mDScaleX = paramSplineSet1.getSlope(paramFloat);
    }
    if (paramSplineSet2 != null) {
      this.mDScaleY = paramSplineSet2.getSlope(paramFloat);
    }
  }
  
  public void setTranslationVelocity(KeyCycleOscillator paramKeyCycleOscillator1, KeyCycleOscillator paramKeyCycleOscillator2, float paramFloat)
  {
    if (paramKeyCycleOscillator1 != null) {
      this.mDTranslateX = paramKeyCycleOscillator1.getSlope(paramFloat);
    }
    if (paramKeyCycleOscillator2 != null) {
      this.mDTranslateY = paramKeyCycleOscillator2.getSlope(paramFloat);
    }
  }
  
  public void setTranslationVelocity(SplineSet paramSplineSet1, SplineSet paramSplineSet2, float paramFloat)
  {
    if (paramSplineSet1 != null) {
      this.mDTranslateX = paramSplineSet1.getSlope(paramFloat);
    }
    if (paramSplineSet2 != null) {
      this.mDTranslateY = paramSplineSet2.getSlope(paramFloat);
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/constraintlayout/core/motion/utils/VelocityMatrix.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */