package com.google.android.material.transition.platform;

import android.graphics.RectF;

class FitModeEvaluators
{
  private static final FitModeEvaluator HEIGHT = new FitModeEvaluator()
  {
    public void applyMask(RectF paramAnonymousRectF, float paramAnonymousFloat, FitModeResult paramAnonymousFitModeResult)
    {
      float f = Math.abs(paramAnonymousFitModeResult.currentEndWidth - paramAnonymousFitModeResult.currentStartWidth);
      paramAnonymousRectF.left += f / 2.0F * paramAnonymousFloat;
      paramAnonymousRectF.right -= f / 2.0F * paramAnonymousFloat;
    }
    
    public FitModeResult evaluate(float paramAnonymousFloat1, float paramAnonymousFloat2, float paramAnonymousFloat3, float paramAnonymousFloat4, float paramAnonymousFloat5, float paramAnonymousFloat6, float paramAnonymousFloat7)
    {
      paramAnonymousFloat1 = TransitionUtils.lerp(paramAnonymousFloat5, paramAnonymousFloat7, paramAnonymousFloat2, paramAnonymousFloat3, paramAnonymousFloat1, true);
      paramAnonymousFloat2 = paramAnonymousFloat1 / paramAnonymousFloat5;
      paramAnonymousFloat3 = paramAnonymousFloat1 / paramAnonymousFloat7;
      return new FitModeResult(paramAnonymousFloat2, paramAnonymousFloat3, paramAnonymousFloat4 * paramAnonymousFloat2, paramAnonymousFloat1, paramAnonymousFloat6 * paramAnonymousFloat3, paramAnonymousFloat1);
    }
    
    public boolean shouldMaskStartBounds(FitModeResult paramAnonymousFitModeResult)
    {
      boolean bool;
      if (paramAnonymousFitModeResult.currentStartWidth > paramAnonymousFitModeResult.currentEndWidth) {
        bool = true;
      } else {
        bool = false;
      }
      return bool;
    }
  };
  private static final FitModeEvaluator WIDTH = new FitModeEvaluator()
  {
    public void applyMask(RectF paramAnonymousRectF, float paramAnonymousFloat, FitModeResult paramAnonymousFitModeResult)
    {
      float f = Math.abs(paramAnonymousFitModeResult.currentEndHeight - paramAnonymousFitModeResult.currentStartHeight);
      paramAnonymousRectF.bottom -= f * paramAnonymousFloat;
    }
    
    public FitModeResult evaluate(float paramAnonymousFloat1, float paramAnonymousFloat2, float paramAnonymousFloat3, float paramAnonymousFloat4, float paramAnonymousFloat5, float paramAnonymousFloat6, float paramAnonymousFloat7)
    {
      paramAnonymousFloat1 = TransitionUtils.lerp(paramAnonymousFloat4, paramAnonymousFloat6, paramAnonymousFloat2, paramAnonymousFloat3, paramAnonymousFloat1, true);
      paramAnonymousFloat2 = paramAnonymousFloat1 / paramAnonymousFloat4;
      paramAnonymousFloat3 = paramAnonymousFloat1 / paramAnonymousFloat6;
      return new FitModeResult(paramAnonymousFloat2, paramAnonymousFloat3, paramAnonymousFloat1, paramAnonymousFloat5 * paramAnonymousFloat2, paramAnonymousFloat1, paramAnonymousFloat7 * paramAnonymousFloat3);
    }
    
    public boolean shouldMaskStartBounds(FitModeResult paramAnonymousFitModeResult)
    {
      boolean bool;
      if (paramAnonymousFitModeResult.currentStartHeight > paramAnonymousFitModeResult.currentEndHeight) {
        bool = true;
      } else {
        bool = false;
      }
      return bool;
    }
  };
  
  static FitModeEvaluator get(int paramInt, boolean paramBoolean, RectF paramRectF1, RectF paramRectF2)
  {
    switch (paramInt)
    {
    default: 
      throw new IllegalArgumentException("Invalid fit mode: " + paramInt);
    case 2: 
      return HEIGHT;
    case 1: 
      return WIDTH;
    }
    if (shouldAutoFitToWidth(paramBoolean, paramRectF1, paramRectF2)) {
      paramRectF1 = WIDTH;
    } else {
      paramRectF1 = HEIGHT;
    }
    return paramRectF1;
  }
  
  private static boolean shouldAutoFitToWidth(boolean paramBoolean, RectF paramRectF1, RectF paramRectF2)
  {
    float f4 = paramRectF1.width();
    float f2 = paramRectF1.height();
    float f5 = paramRectF2.width();
    float f1 = paramRectF2.height();
    float f3 = f1 * f4 / f5;
    f4 = f2 * f5 / f4;
    boolean bool = true;
    if (paramBoolean ? f3 >= f2 : f4 >= f1) {
      paramBoolean = bool;
    } else {
      paramBoolean = false;
    }
    return paramBoolean;
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/com/google/android/material/transition/platform/FitModeEvaluators.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */