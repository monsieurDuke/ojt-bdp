package androidx.transition;

import android.graphics.Rect;
import android.view.View;
import android.view.ViewGroup;
import androidx.core.view.ViewCompat;

public class SidePropagation
  extends VisibilityPropagation
{
  private float mPropagationSpeed = 3.0F;
  private int mSide = 80;
  
  private int distance(View paramView, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8)
  {
    int m = this.mSide;
    int i = 5;
    int j = 0;
    int k = 0;
    if (m == 8388611)
    {
      j = k;
      if (ViewCompat.getLayoutDirection(paramView) == 1) {
        j = 1;
      }
      if (j == 0) {
        i = 3;
      }
    }
    else if (m == 8388613)
    {
      if (ViewCompat.getLayoutDirection(paramView) == 1) {
        j = 1;
      }
      if (j != 0) {
        i = 3;
      }
    }
    else
    {
      i = this.mSide;
    }
    j = 0;
    switch (i)
    {
    default: 
      paramInt1 = j;
      break;
    case 80: 
      paramInt1 = paramInt2 - paramInt6 + Math.abs(paramInt3 - paramInt1);
      break;
    case 48: 
      paramInt1 = paramInt8 - paramInt2 + Math.abs(paramInt3 - paramInt1);
      break;
    case 5: 
      paramInt1 = paramInt1 - paramInt5 + Math.abs(paramInt4 - paramInt2);
      break;
    case 3: 
      paramInt1 = paramInt7 - paramInt1 + Math.abs(paramInt4 - paramInt2);
    }
    return paramInt1;
  }
  
  private int getMaxDistance(ViewGroup paramViewGroup)
  {
    switch (this.mSide)
    {
    default: 
      return paramViewGroup.getHeight();
    }
    return paramViewGroup.getWidth();
  }
  
  public long getStartDelay(ViewGroup paramViewGroup, Transition paramTransition, TransitionValues paramTransitionValues1, TransitionValues paramTransitionValues2)
  {
    if ((paramTransitionValues1 == null) && (paramTransitionValues2 == null)) {
      return 0L;
    }
    Rect localRect = paramTransition.getEpicenter();
    int i;
    if ((paramTransitionValues2 != null) && (getViewVisibility(paramTransitionValues1) != 0))
    {
      i = 1;
    }
    else
    {
      i = -1;
      paramTransitionValues2 = paramTransitionValues1;
    }
    int m = getViewX(paramTransitionValues2);
    int i4 = getViewY(paramTransitionValues2);
    paramTransitionValues1 = new int[2];
    paramViewGroup.getLocationOnScreen(paramTransitionValues1);
    int i1 = paramTransitionValues1[0] + Math.round(paramViewGroup.getTranslationX());
    int n = paramTransitionValues1[1] + Math.round(paramViewGroup.getTranslationY());
    int i3 = i1 + paramViewGroup.getWidth();
    int i2 = n + paramViewGroup.getHeight();
    int j;
    int k;
    if (localRect != null)
    {
      j = localRect.centerX();
      k = localRect.centerY();
    }
    else
    {
      j = (i1 + i3) / 2;
      k = (n + i2) / 2;
    }
    float f = distance(paramViewGroup, m, i4, j, k, i1, n, i3, i2) / getMaxDistance(paramViewGroup);
    long l2 = paramTransition.getDuration();
    long l1 = l2;
    if (l2 < 0L) {
      l1 = 300L;
    }
    return Math.round((float)(i * l1) / this.mPropagationSpeed * f);
  }
  
  public void setPropagationSpeed(float paramFloat)
  {
    if (paramFloat != 0.0F)
    {
      this.mPropagationSpeed = paramFloat;
      return;
    }
    throw new IllegalArgumentException("propagationSpeed may not be 0");
  }
  
  public void setSide(int paramInt)
  {
    this.mSide = paramInt;
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/transition/SidePropagation.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */