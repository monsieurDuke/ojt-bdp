package androidx.constraintlayout.widget;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.os.Build.VERSION;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.SparseArray;
import androidx.constraintlayout.core.widgets.ConstraintWidget;
import androidx.constraintlayout.core.widgets.ConstraintWidgetContainer;
import androidx.constraintlayout.core.widgets.HelperWidget;

public class Barrier
  extends ConstraintHelper
{
  public static final int BOTTOM = 3;
  public static final int END = 6;
  public static final int LEFT = 0;
  public static final int RIGHT = 1;
  public static final int START = 5;
  public static final int TOP = 2;
  private androidx.constraintlayout.core.widgets.Barrier mBarrier;
  private int mIndicatedType;
  private int mResolvedType;
  
  public Barrier(Context paramContext)
  {
    super(paramContext);
    super.setVisibility(8);
  }
  
  public Barrier(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    super.setVisibility(8);
  }
  
  public Barrier(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
    super.setVisibility(8);
  }
  
  private void updateType(ConstraintWidget paramConstraintWidget, int paramInt, boolean paramBoolean)
  {
    this.mResolvedType = paramInt;
    if (Build.VERSION.SDK_INT < 17)
    {
      paramInt = this.mIndicatedType;
      if (paramInt == 5) {
        this.mResolvedType = 0;
      } else if (paramInt == 6) {
        this.mResolvedType = 1;
      }
    }
    else if (paramBoolean)
    {
      paramInt = this.mIndicatedType;
      if (paramInt == 5) {
        this.mResolvedType = 1;
      } else if (paramInt == 6) {
        this.mResolvedType = 0;
      }
    }
    else
    {
      paramInt = this.mIndicatedType;
      if (paramInt == 5) {
        this.mResolvedType = 0;
      } else if (paramInt == 6) {
        this.mResolvedType = 1;
      }
    }
    if ((paramConstraintWidget instanceof androidx.constraintlayout.core.widgets.Barrier)) {
      ((androidx.constraintlayout.core.widgets.Barrier)paramConstraintWidget).setBarrierType(this.mResolvedType);
    }
  }
  
  @Deprecated
  public boolean allowsGoneWidget()
  {
    return this.mBarrier.getAllowsGoneWidget();
  }
  
  public boolean getAllowsGoneWidget()
  {
    return this.mBarrier.getAllowsGoneWidget();
  }
  
  public int getMargin()
  {
    return this.mBarrier.getMargin();
  }
  
  public int getType()
  {
    return this.mIndicatedType;
  }
  
  protected void init(AttributeSet paramAttributeSet)
  {
    super.init(paramAttributeSet);
    this.mBarrier = new androidx.constraintlayout.core.widgets.Barrier();
    if (paramAttributeSet != null)
    {
      paramAttributeSet = getContext().obtainStyledAttributes(paramAttributeSet, R.styleable.ConstraintLayout_Layout);
      int j = paramAttributeSet.getIndexCount();
      for (int i = 0; i < j; i++)
      {
        int k = paramAttributeSet.getIndex(i);
        if (k == R.styleable.ConstraintLayout_Layout_barrierDirection)
        {
          setType(paramAttributeSet.getInt(k, 0));
        }
        else if (k == R.styleable.ConstraintLayout_Layout_barrierAllowsGoneWidgets)
        {
          this.mBarrier.setAllowsGoneWidget(paramAttributeSet.getBoolean(k, true));
        }
        else if (k == R.styleable.ConstraintLayout_Layout_barrierMargin)
        {
          k = paramAttributeSet.getDimensionPixelSize(k, 0);
          this.mBarrier.setMargin(k);
        }
      }
      paramAttributeSet.recycle();
    }
    this.mHelperWidget = this.mBarrier;
    validateParams();
  }
  
  public void loadParameters(ConstraintSet.Constraint paramConstraint, HelperWidget paramHelperWidget, ConstraintLayout.LayoutParams paramLayoutParams, SparseArray<ConstraintWidget> paramSparseArray)
  {
    super.loadParameters(paramConstraint, paramHelperWidget, paramLayoutParams, paramSparseArray);
    if ((paramHelperWidget instanceof androidx.constraintlayout.core.widgets.Barrier))
    {
      paramLayoutParams = (androidx.constraintlayout.core.widgets.Barrier)paramHelperWidget;
      boolean bool = ((ConstraintWidgetContainer)paramHelperWidget.getParent()).isRtl();
      updateType(paramLayoutParams, paramConstraint.layout.mBarrierDirection, bool);
      paramLayoutParams.setAllowsGoneWidget(paramConstraint.layout.mBarrierAllowsGoneWidgets);
      paramLayoutParams.setMargin(paramConstraint.layout.mBarrierMargin);
    }
  }
  
  public void resolveRtl(ConstraintWidget paramConstraintWidget, boolean paramBoolean)
  {
    updateType(paramConstraintWidget, this.mIndicatedType, paramBoolean);
  }
  
  public void setAllowsGoneWidget(boolean paramBoolean)
  {
    this.mBarrier.setAllowsGoneWidget(paramBoolean);
  }
  
  public void setDpMargin(int paramInt)
  {
    float f = getResources().getDisplayMetrics().density;
    paramInt = (int)(paramInt * f + 0.5F);
    this.mBarrier.setMargin(paramInt);
  }
  
  public void setMargin(int paramInt)
  {
    this.mBarrier.setMargin(paramInt);
  }
  
  public void setType(int paramInt)
  {
    this.mIndicatedType = paramInt;
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/constraintlayout/widget/Barrier.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */