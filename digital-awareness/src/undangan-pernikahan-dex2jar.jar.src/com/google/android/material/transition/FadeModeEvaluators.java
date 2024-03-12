package com.google.android.material.transition;

class FadeModeEvaluators
{
  private static final FadeModeEvaluator CROSS = new FadeModeEvaluator()
  {
    public FadeModeResult evaluate(float paramAnonymousFloat1, float paramAnonymousFloat2, float paramAnonymousFloat3, float paramAnonymousFloat4)
    {
      return FadeModeResult.startOnTop(TransitionUtils.lerp(255, 0, paramAnonymousFloat2, paramAnonymousFloat3, paramAnonymousFloat1), TransitionUtils.lerp(0, 255, paramAnonymousFloat2, paramAnonymousFloat3, paramAnonymousFloat1));
    }
  };
  private static final FadeModeEvaluator IN = new FadeModeEvaluator()
  {
    public FadeModeResult evaluate(float paramAnonymousFloat1, float paramAnonymousFloat2, float paramAnonymousFloat3, float paramAnonymousFloat4)
    {
      return FadeModeResult.endOnTop(255, TransitionUtils.lerp(0, 255, paramAnonymousFloat2, paramAnonymousFloat3, paramAnonymousFloat1));
    }
  };
  private static final FadeModeEvaluator OUT = new FadeModeEvaluator()
  {
    public FadeModeResult evaluate(float paramAnonymousFloat1, float paramAnonymousFloat2, float paramAnonymousFloat3, float paramAnonymousFloat4)
    {
      return FadeModeResult.startOnTop(TransitionUtils.lerp(255, 0, paramAnonymousFloat2, paramAnonymousFloat3, paramAnonymousFloat1), 255);
    }
  };
  private static final FadeModeEvaluator THROUGH = new FadeModeEvaluator()
  {
    public FadeModeResult evaluate(float paramAnonymousFloat1, float paramAnonymousFloat2, float paramAnonymousFloat3, float paramAnonymousFloat4)
    {
      paramAnonymousFloat4 = (paramAnonymousFloat3 - paramAnonymousFloat2) * paramAnonymousFloat4 + paramAnonymousFloat2;
      return FadeModeResult.startOnTop(TransitionUtils.lerp(255, 0, paramAnonymousFloat2, paramAnonymousFloat4, paramAnonymousFloat1), TransitionUtils.lerp(0, 255, paramAnonymousFloat4, paramAnonymousFloat3, paramAnonymousFloat1));
    }
  };
  
  static FadeModeEvaluator get(int paramInt, boolean paramBoolean)
  {
    FadeModeEvaluator localFadeModeEvaluator;
    switch (paramInt)
    {
    default: 
      throw new IllegalArgumentException("Invalid fade mode: " + paramInt);
    case 3: 
      return THROUGH;
    case 2: 
      return CROSS;
    case 1: 
      if (paramBoolean) {
        localFadeModeEvaluator = OUT;
      } else {
        localFadeModeEvaluator = IN;
      }
      return localFadeModeEvaluator;
    }
    if (paramBoolean) {
      localFadeModeEvaluator = IN;
    } else {
      localFadeModeEvaluator = OUT;
    }
    return localFadeModeEvaluator;
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/com/google/android/material/transition/FadeModeEvaluators.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */