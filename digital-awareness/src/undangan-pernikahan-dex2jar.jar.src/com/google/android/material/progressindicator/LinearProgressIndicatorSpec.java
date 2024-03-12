package com.google.android.material.progressindicator;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import com.google.android.material.R.attr;
import com.google.android.material.R.styleable;
import com.google.android.material.internal.ThemeEnforcement;

public final class LinearProgressIndicatorSpec
  extends BaseProgressIndicatorSpec
{
  boolean drawHorizontallyInverse;
  public int indeterminateAnimationType;
  public int indicatorDirection;
  
  public LinearProgressIndicatorSpec(Context paramContext, AttributeSet paramAttributeSet)
  {
    this(paramContext, paramAttributeSet, R.attr.linearProgressIndicatorStyle);
  }
  
  public LinearProgressIndicatorSpec(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    this(paramContext, paramAttributeSet, paramInt, LinearProgressIndicator.DEF_STYLE_RES);
  }
  
  public LinearProgressIndicatorSpec(Context paramContext, AttributeSet paramAttributeSet, int paramInt1, int paramInt2)
  {
    super(paramContext, paramAttributeSet, paramInt1, paramInt2);
    int[] arrayOfInt = R.styleable.LinearProgressIndicator;
    paramInt1 = R.attr.linearProgressIndicatorStyle;
    paramInt2 = LinearProgressIndicator.DEF_STYLE_RES;
    boolean bool = false;
    paramContext = ThemeEnforcement.obtainStyledAttributes(paramContext, paramAttributeSet, arrayOfInt, paramInt1, paramInt2, new int[0]);
    this.indeterminateAnimationType = paramContext.getInt(R.styleable.LinearProgressIndicator_indeterminateAnimationType, 1);
    this.indicatorDirection = paramContext.getInt(R.styleable.LinearProgressIndicator_indicatorDirectionLinear, 0);
    paramContext.recycle();
    validateSpec();
    if (this.indicatorDirection == 1) {
      bool = true;
    }
    this.drawHorizontallyInverse = bool;
  }
  
  void validateSpec()
  {
    if (this.indeterminateAnimationType == 0) {
      if (this.trackCornerRadius <= 0)
      {
        if (this.indicatorColors.length < 3) {
          throw new IllegalArgumentException("Contiguous indeterminate animation must be used with 3 or more indicator colors.");
        }
      }
      else {
        throw new IllegalArgumentException("Rounded corners are not supported in contiguous indeterminate animation.");
      }
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/com/google/android/material/progressindicator/LinearProgressIndicatorSpec.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */