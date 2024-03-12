package com.google.android.material.progressindicator;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import androidx.core.view.ViewCompat;
import com.google.android.material.R.attr;
import com.google.android.material.R.style;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public final class LinearProgressIndicator
  extends BaseProgressIndicator<LinearProgressIndicatorSpec>
{
  public static final int DEF_STYLE_RES = R.style.Widget_MaterialComponents_LinearProgressIndicator;
  public static final int INDETERMINATE_ANIMATION_TYPE_CONTIGUOUS = 0;
  public static final int INDETERMINATE_ANIMATION_TYPE_DISJOINT = 1;
  public static final int INDICATOR_DIRECTION_END_TO_START = 3;
  public static final int INDICATOR_DIRECTION_LEFT_TO_RIGHT = 0;
  public static final int INDICATOR_DIRECTION_RIGHT_TO_LEFT = 1;
  public static final int INDICATOR_DIRECTION_START_TO_END = 2;
  
  public LinearProgressIndicator(Context paramContext)
  {
    this(paramContext, null);
  }
  
  public LinearProgressIndicator(Context paramContext, AttributeSet paramAttributeSet)
  {
    this(paramContext, paramAttributeSet, R.attr.linearProgressIndicatorStyle);
  }
  
  public LinearProgressIndicator(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt, DEF_STYLE_RES);
    initializeDrawables();
  }
  
  private void initializeDrawables()
  {
    setIndeterminateDrawable(IndeterminateDrawable.createLinearDrawable(getContext(), (LinearProgressIndicatorSpec)this.spec));
    setProgressDrawable(DeterminateDrawable.createLinearDrawable(getContext(), (LinearProgressIndicatorSpec)this.spec));
  }
  
  LinearProgressIndicatorSpec createSpec(Context paramContext, AttributeSet paramAttributeSet)
  {
    return new LinearProgressIndicatorSpec(paramContext, paramAttributeSet);
  }
  
  public int getIndeterminateAnimationType()
  {
    return ((LinearProgressIndicatorSpec)this.spec).indeterminateAnimationType;
  }
  
  public int getIndicatorDirection()
  {
    return ((LinearProgressIndicatorSpec)this.spec).indicatorDirection;
  }
  
  protected void onLayout(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    super.onLayout(paramBoolean, paramInt1, paramInt2, paramInt3, paramInt4);
    LinearProgressIndicatorSpec localLinearProgressIndicatorSpec = (LinearProgressIndicatorSpec)this.spec;
    paramInt1 = ((LinearProgressIndicatorSpec)this.spec).indicatorDirection;
    paramBoolean = true;
    if ((paramInt1 != 1) && ((ViewCompat.getLayoutDirection(this) != 1) || (((LinearProgressIndicatorSpec)this.spec).indicatorDirection != 2)) && ((ViewCompat.getLayoutDirection(this) != 0) || (((LinearProgressIndicatorSpec)this.spec).indicatorDirection != 3))) {
      paramBoolean = false;
    }
    localLinearProgressIndicatorSpec.drawHorizontallyInverse = paramBoolean;
  }
  
  protected void onSizeChanged(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    paramInt1 -= getPaddingLeft() + getPaddingRight();
    paramInt2 -= getPaddingTop() + getPaddingBottom();
    Object localObject = getIndeterminateDrawable();
    if (localObject != null) {
      ((Drawable)localObject).setBounds(0, 0, paramInt1, paramInt2);
    }
    localObject = getProgressDrawable();
    if (localObject != null) {
      ((Drawable)localObject).setBounds(0, 0, paramInt1, paramInt2);
    }
  }
  
  public void setIndeterminateAnimationType(int paramInt)
  {
    if (((LinearProgressIndicatorSpec)this.spec).indeterminateAnimationType == paramInt) {
      return;
    }
    if ((visibleToUser()) && (isIndeterminate())) {
      throw new IllegalStateException("Cannot change indeterminate animation type while the progress indicator is show in indeterminate mode.");
    }
    ((LinearProgressIndicatorSpec)this.spec).indeterminateAnimationType = paramInt;
    ((LinearProgressIndicatorSpec)this.spec).validateSpec();
    if (paramInt == 0) {
      getIndeterminateDrawable().setAnimatorDelegate(new LinearIndeterminateContiguousAnimatorDelegate((LinearProgressIndicatorSpec)this.spec));
    } else {
      getIndeterminateDrawable().setAnimatorDelegate(new LinearIndeterminateDisjointAnimatorDelegate(getContext(), (LinearProgressIndicatorSpec)this.spec));
    }
    invalidate();
  }
  
  public void setIndicatorColor(int... paramVarArgs)
  {
    super.setIndicatorColor(paramVarArgs);
    ((LinearProgressIndicatorSpec)this.spec).validateSpec();
  }
  
  public void setIndicatorDirection(int paramInt)
  {
    ((LinearProgressIndicatorSpec)this.spec).indicatorDirection = paramInt;
    LinearProgressIndicatorSpec localLinearProgressIndicatorSpec = (LinearProgressIndicatorSpec)this.spec;
    boolean bool = true;
    if ((paramInt != 1) && ((ViewCompat.getLayoutDirection(this) != 1) || (((LinearProgressIndicatorSpec)this.spec).indicatorDirection != 2)) && ((ViewCompat.getLayoutDirection(this) != 0) || (paramInt != 3))) {
      bool = false;
    }
    localLinearProgressIndicatorSpec.drawHorizontallyInverse = bool;
    invalidate();
  }
  
  public void setProgressCompat(int paramInt, boolean paramBoolean)
  {
    if ((this.spec != null) && (((LinearProgressIndicatorSpec)this.spec).indeterminateAnimationType == 0) && (isIndeterminate())) {
      return;
    }
    super.setProgressCompat(paramInt, paramBoolean);
  }
  
  public void setTrackCornerRadius(int paramInt)
  {
    super.setTrackCornerRadius(paramInt);
    ((LinearProgressIndicatorSpec)this.spec).validateSpec();
    invalidate();
  }
  
  @Retention(RetentionPolicy.SOURCE)
  public static @interface IndeterminateAnimationType {}
  
  @Retention(RetentionPolicy.SOURCE)
  public static @interface IndicatorDirection {}
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/com/google/android/material/progressindicator/LinearProgressIndicator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */