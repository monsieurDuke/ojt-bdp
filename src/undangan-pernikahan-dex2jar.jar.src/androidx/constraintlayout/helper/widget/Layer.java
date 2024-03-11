package androidx.constraintlayout.helper.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build.VERSION;
import android.util.AttributeSet;
import android.view.View;
import androidx.constraintlayout.core.widgets.ConstraintWidget;
import androidx.constraintlayout.widget.ConstraintHelper;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintLayout.LayoutParams;
import androidx.constraintlayout.widget.R.styleable;

public class Layer
  extends ConstraintHelper
{
  private static final String TAG = "Layer";
  private boolean mApplyElevationOnAttach;
  private boolean mApplyVisibilityOnAttach;
  protected float mComputedCenterX = NaN.0F;
  protected float mComputedCenterY = NaN.0F;
  protected float mComputedMaxX = NaN.0F;
  protected float mComputedMaxY = NaN.0F;
  protected float mComputedMinX = NaN.0F;
  protected float mComputedMinY = NaN.0F;
  ConstraintLayout mContainer;
  private float mGroupRotateAngle = NaN.0F;
  boolean mNeedBounds = true;
  private float mRotationCenterX = NaN.0F;
  private float mRotationCenterY = NaN.0F;
  private float mScaleX = 1.0F;
  private float mScaleY = 1.0F;
  private float mShiftX = 0.0F;
  private float mShiftY = 0.0F;
  View[] mViews = null;
  
  public Layer(Context paramContext)
  {
    super(paramContext);
  }
  
  public Layer(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }
  
  public Layer(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
  }
  
  private void reCacheViews()
  {
    if (this.mContainer == null) {
      return;
    }
    if (this.mCount == 0) {
      return;
    }
    View[] arrayOfView = this.mViews;
    if ((arrayOfView == null) || (arrayOfView.length != this.mCount)) {
      this.mViews = new View[this.mCount];
    }
    for (int i = 0; i < this.mCount; i++)
    {
      int j = this.mIds[i];
      this.mViews[i] = this.mContainer.getViewById(j);
    }
  }
  
  private void transform()
  {
    if (this.mContainer == null) {
      return;
    }
    if (this.mViews == null) {
      reCacheViews();
    }
    calcCenters();
    double d;
    if (Float.isNaN(this.mGroupRotateAngle)) {
      d = 0.0D;
    } else {
      d = Math.toRadians(this.mGroupRotateAngle);
    }
    float f1 = (float)Math.sin(d);
    float f5 = (float)Math.cos(d);
    float f2 = this.mScaleX;
    float f8 = this.mScaleY;
    float f9 = -f8;
    for (int i = 0; i < this.mCount; i++)
    {
      View localView = this.mViews[i];
      int j = (localView.getLeft() + localView.getRight()) / 2;
      int k = (localView.getTop() + localView.getBottom()) / 2;
      float f4 = j - this.mComputedCenterX;
      float f7 = k - this.mComputedCenterY;
      float f6 = this.mShiftX;
      float f3 = this.mShiftY;
      localView.setTranslationX(f2 * f5 * f4 + f9 * f1 * f7 - f4 + f6);
      localView.setTranslationY(f2 * f1 * f4 + f8 * f5 * f7 - f7 + f3);
      localView.setScaleY(this.mScaleY);
      localView.setScaleX(this.mScaleX);
      if (!Float.isNaN(this.mGroupRotateAngle)) {
        localView.setRotation(this.mGroupRotateAngle);
      }
    }
  }
  
  protected void applyLayoutFeaturesInConstraintSet(ConstraintLayout paramConstraintLayout)
  {
    applyLayoutFeatures(paramConstraintLayout);
  }
  
  protected void calcCenters()
  {
    if (this.mContainer == null) {
      return;
    }
    if ((!this.mNeedBounds) && (!Float.isNaN(this.mComputedCenterX)) && (!Float.isNaN(this.mComputedCenterY))) {
      return;
    }
    if ((!Float.isNaN(this.mRotationCenterX)) && (!Float.isNaN(this.mRotationCenterY)))
    {
      this.mComputedCenterY = this.mRotationCenterY;
      this.mComputedCenterX = this.mRotationCenterX;
    }
    else
    {
      View[] arrayOfView = getViews(this.mContainer);
      int m = arrayOfView[0].getLeft();
      int k = arrayOfView[0].getTop();
      int j = arrayOfView[0].getRight();
      int n = arrayOfView[0].getBottom();
      for (int i = 0; i < this.mCount; i++)
      {
        View localView = arrayOfView[i];
        m = Math.min(m, localView.getLeft());
        k = Math.min(k, localView.getTop());
        j = Math.max(j, localView.getRight());
        n = Math.max(n, localView.getBottom());
      }
      this.mComputedMaxX = j;
      this.mComputedMaxY = n;
      this.mComputedMinX = m;
      this.mComputedMinY = k;
      if (Float.isNaN(this.mRotationCenterX)) {
        this.mComputedCenterX = ((m + j) / 2);
      } else {
        this.mComputedCenterX = this.mRotationCenterX;
      }
      if (Float.isNaN(this.mRotationCenterY)) {
        this.mComputedCenterY = ((k + n) / 2);
      } else {
        this.mComputedCenterY = this.mRotationCenterY;
      }
    }
  }
  
  protected void init(AttributeSet paramAttributeSet)
  {
    super.init(paramAttributeSet);
    this.mUseViewMeasure = false;
    if (paramAttributeSet != null)
    {
      paramAttributeSet = getContext().obtainStyledAttributes(paramAttributeSet, R.styleable.ConstraintLayout_Layout);
      int j = paramAttributeSet.getIndexCount();
      for (int i = 0; i < j; i++)
      {
        int k = paramAttributeSet.getIndex(i);
        if (k == R.styleable.ConstraintLayout_Layout_android_visibility) {
          this.mApplyVisibilityOnAttach = true;
        } else if (k == R.styleable.ConstraintLayout_Layout_android_elevation) {
          this.mApplyElevationOnAttach = true;
        }
      }
      paramAttributeSet.recycle();
    }
  }
  
  protected void onAttachedToWindow()
  {
    super.onAttachedToWindow();
    this.mContainer = ((ConstraintLayout)getParent());
    if ((this.mApplyVisibilityOnAttach) || (this.mApplyElevationOnAttach))
    {
      int j = getVisibility();
      float f = 0.0F;
      if (Build.VERSION.SDK_INT >= 21) {
        f = getElevation();
      }
      for (int i = 0; i < this.mCount; i++)
      {
        int k = this.mIds[i];
        View localView = this.mContainer.getViewById(k);
        if (localView != null)
        {
          if (this.mApplyVisibilityOnAttach) {
            localView.setVisibility(j);
          }
          if ((this.mApplyElevationOnAttach) && (f > 0.0F) && (Build.VERSION.SDK_INT >= 21)) {
            localView.setTranslationZ(localView.getTranslationZ() + f);
          }
        }
      }
    }
  }
  
  public void setElevation(float paramFloat)
  {
    super.setElevation(paramFloat);
    applyLayoutFeatures();
  }
  
  public void setPivotX(float paramFloat)
  {
    this.mRotationCenterX = paramFloat;
    transform();
  }
  
  public void setPivotY(float paramFloat)
  {
    this.mRotationCenterY = paramFloat;
    transform();
  }
  
  public void setRotation(float paramFloat)
  {
    this.mGroupRotateAngle = paramFloat;
    transform();
  }
  
  public void setScaleX(float paramFloat)
  {
    this.mScaleX = paramFloat;
    transform();
  }
  
  public void setScaleY(float paramFloat)
  {
    this.mScaleY = paramFloat;
    transform();
  }
  
  public void setTranslationX(float paramFloat)
  {
    this.mShiftX = paramFloat;
    transform();
  }
  
  public void setTranslationY(float paramFloat)
  {
    this.mShiftY = paramFloat;
    transform();
  }
  
  public void setVisibility(int paramInt)
  {
    super.setVisibility(paramInt);
    applyLayoutFeatures();
  }
  
  public void updatePostLayout(ConstraintLayout paramConstraintLayout)
  {
    reCacheViews();
    this.mComputedCenterX = NaN.0F;
    this.mComputedCenterY = NaN.0F;
    paramConstraintLayout = ((ConstraintLayout.LayoutParams)getLayoutParams()).getConstraintWidget();
    paramConstraintLayout.setWidth(0);
    paramConstraintLayout.setHeight(0);
    calcCenters();
    layout((int)this.mComputedMinX - getPaddingLeft(), (int)this.mComputedMinY - getPaddingTop(), (int)this.mComputedMaxX + getPaddingRight(), (int)this.mComputedMaxY + getPaddingBottom());
    transform();
  }
  
  public void updatePreDraw(ConstraintLayout paramConstraintLayout)
  {
    this.mContainer = paramConstraintLayout;
    float f = getRotation();
    if (f == 0.0F)
    {
      if (!Float.isNaN(this.mGroupRotateAngle)) {
        this.mGroupRotateAngle = f;
      }
    }
    else {
      this.mGroupRotateAngle = f;
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/constraintlayout/helper/widget/Layer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */