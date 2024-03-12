package com.google.android.material.animation;

import android.animation.TypeEvaluator;
import android.graphics.Matrix;

public class MatrixEvaluator
  implements TypeEvaluator<Matrix>
{
  private final float[] tempEndValues = new float[9];
  private final Matrix tempMatrix = new Matrix();
  private final float[] tempStartValues = new float[9];
  
  public Matrix evaluate(float paramFloat, Matrix paramMatrix1, Matrix paramMatrix2)
  {
    paramMatrix1.getValues(this.tempStartValues);
    paramMatrix2.getValues(this.tempEndValues);
    for (int i = 0; i < 9; i++)
    {
      paramMatrix1 = this.tempEndValues;
      float f2 = paramMatrix1[i];
      float f1 = this.tempStartValues[i];
      paramMatrix1[i] = (f1 + paramFloat * (f2 - f1));
    }
    this.tempMatrix.setValues(this.tempEndValues);
    return this.tempMatrix;
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/com/google/android/material/animation/MatrixEvaluator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */