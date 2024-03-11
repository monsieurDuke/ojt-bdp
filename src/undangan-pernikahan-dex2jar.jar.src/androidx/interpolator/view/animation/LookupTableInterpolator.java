package androidx.interpolator.view.animation;

import android.view.animation.Interpolator;

abstract class LookupTableInterpolator
  implements Interpolator
{
  private final float mStepSize;
  private final float[] mValues;
  
  protected LookupTableInterpolator(float[] paramArrayOfFloat)
  {
    this.mValues = paramArrayOfFloat;
    this.mStepSize = (1.0F / (paramArrayOfFloat.length - 1));
  }
  
  public float getInterpolation(float paramFloat)
  {
    if (paramFloat >= 1.0F) {
      return 1.0F;
    }
    if (paramFloat <= 0.0F) {
      return 0.0F;
    }
    float[] arrayOfFloat = this.mValues;
    int i = Math.min((int)((arrayOfFloat.length - 1) * paramFloat), arrayOfFloat.length - 2);
    float f1 = i;
    float f2 = this.mStepSize;
    f1 = (paramFloat - f1 * f2) / f2;
    arrayOfFloat = this.mValues;
    paramFloat = arrayOfFloat[i];
    return paramFloat + (arrayOfFloat[(i + 1)] - paramFloat) * f1;
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/interpolator/view/animation/LookupTableInterpolator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */