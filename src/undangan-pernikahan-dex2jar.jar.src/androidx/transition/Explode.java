package androidx.transition;

import android.animation.Animator;
import android.animation.TimeInterpolator;
import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import java.util.Map;

public class Explode
  extends Visibility
{
  private static final String PROPNAME_SCREEN_BOUNDS = "android:explode:screenBounds";
  private static final TimeInterpolator sAccelerate = new AccelerateInterpolator();
  private static final TimeInterpolator sDecelerate = new DecelerateInterpolator();
  private int[] mTempLoc = new int[2];
  
  public Explode()
  {
    setPropagation(new CircularPropagation());
  }
  
  public Explode(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    setPropagation(new CircularPropagation());
  }
  
  private static float calculateDistance(float paramFloat1, float paramFloat2)
  {
    return (float)Math.sqrt(paramFloat1 * paramFloat1 + paramFloat2 * paramFloat2);
  }
  
  private static float calculateMaxDistance(View paramView, int paramInt1, int paramInt2)
  {
    paramInt1 = Math.max(paramInt1, paramView.getWidth() - paramInt1);
    paramInt2 = Math.max(paramInt2, paramView.getHeight() - paramInt2);
    return calculateDistance(paramInt1, paramInt2);
  }
  
  private void calculateOut(View paramView, Rect paramRect, int[] paramArrayOfInt)
  {
    paramView.getLocationOnScreen(this.mTempLoc);
    Object localObject = this.mTempLoc;
    int m = localObject[0];
    int k = localObject[1];
    localObject = getEpicenter();
    int i;
    int j;
    if (localObject == null)
    {
      i = paramView.getWidth() / 2 + m + Math.round(paramView.getTranslationX());
      j = paramView.getHeight() / 2 + k + Math.round(paramView.getTranslationY());
    }
    else
    {
      i = ((Rect)localObject).centerX();
      j = ((Rect)localObject).centerY();
    }
    int i1 = paramRect.centerX();
    int n = paramRect.centerY();
    float f2 = i1 - i;
    float f1 = n - j;
    if ((f2 == 0.0F) && (f1 == 0.0F))
    {
      f2 = (float)(Math.random() * 2.0D) - 1.0F;
      f1 = (float)(Math.random() * 2.0D) - 1.0F;
    }
    float f3 = calculateDistance(f2, f1);
    f2 /= f3;
    f3 = f1 / f3;
    f1 = calculateMaxDistance(paramView, i - m, j - k);
    paramArrayOfInt[0] = Math.round(f1 * f2);
    paramArrayOfInt[1] = Math.round(f1 * f3);
  }
  
  private void captureValues(TransitionValues paramTransitionValues)
  {
    View localView = paramTransitionValues.view;
    localView.getLocationOnScreen(this.mTempLoc);
    int[] arrayOfInt = this.mTempLoc;
    int k = arrayOfInt[0];
    int i = arrayOfInt[1];
    int m = localView.getWidth();
    int j = localView.getHeight();
    paramTransitionValues.values.put("android:explode:screenBounds", new Rect(k, i, m + k, j + i));
  }
  
  public void captureEndValues(TransitionValues paramTransitionValues)
  {
    super.captureEndValues(paramTransitionValues);
    captureValues(paramTransitionValues);
  }
  
  public void captureStartValues(TransitionValues paramTransitionValues)
  {
    super.captureStartValues(paramTransitionValues);
    captureValues(paramTransitionValues);
  }
  
  public Animator onAppear(ViewGroup paramViewGroup, View paramView, TransitionValues paramTransitionValues1, TransitionValues paramTransitionValues2)
  {
    if (paramTransitionValues2 == null) {
      return null;
    }
    paramTransitionValues1 = (Rect)paramTransitionValues2.values.get("android:explode:screenBounds");
    float f4 = paramView.getTranslationX();
    float f2 = paramView.getTranslationY();
    calculateOut(paramViewGroup, paramTransitionValues1, this.mTempLoc);
    paramViewGroup = this.mTempLoc;
    float f1 = paramViewGroup[0];
    float f3 = paramViewGroup[1];
    return TranslationAnimationCreator.createAnimation(paramView, paramTransitionValues2, paramTransitionValues1.left, paramTransitionValues1.top, f4 + f1, f2 + f3, f4, f2, sDecelerate, this);
  }
  
  public Animator onDisappear(ViewGroup paramViewGroup, View paramView, TransitionValues paramTransitionValues1, TransitionValues paramTransitionValues2)
  {
    if (paramTransitionValues1 == null) {
      return null;
    }
    Rect localRect = (Rect)paramTransitionValues1.values.get("android:explode:screenBounds");
    int i = localRect.left;
    int j = localRect.top;
    float f5 = paramView.getTranslationX();
    float f6 = paramView.getTranslationY();
    float f3 = f5;
    float f1 = f6;
    paramTransitionValues2 = (int[])paramTransitionValues1.view.getTag(R.id.transition_position);
    float f4 = f3;
    float f2 = f1;
    if (paramTransitionValues2 != null)
    {
      f4 = f3 + (paramTransitionValues2[0] - localRect.left);
      f2 = f1 + (paramTransitionValues2[1] - localRect.top);
      localRect.offsetTo(paramTransitionValues2[0], paramTransitionValues2[1]);
    }
    calculateOut(paramViewGroup, localRect, this.mTempLoc);
    paramViewGroup = this.mTempLoc;
    return TranslationAnimationCreator.createAnimation(paramView, paramTransitionValues1, i, j, f5, f6, f4 + paramViewGroup[0], f2 + paramViewGroup[1], sAccelerate, this);
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/transition/Explode.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */