package androidx.appcompat.widget;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.MeasureSpec;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import androidx.appcompat.R.id;
import androidx.appcompat.R.styleable;
import androidx.core.view.ViewCompat;

public class ButtonBarLayout
  extends LinearLayout
{
  private static final int PEEK_BUTTON_DP = 16;
  private boolean mAllowStacking;
  private int mLastWidthSize = -1;
  private boolean mStacked;
  
  public ButtonBarLayout(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    TypedArray localTypedArray = paramContext.obtainStyledAttributes(paramAttributeSet, R.styleable.ButtonBarLayout);
    ViewCompat.saveAttributeDataForStyleable(this, paramContext, R.styleable.ButtonBarLayout, paramAttributeSet, localTypedArray, 0, 0);
    this.mAllowStacking = localTypedArray.getBoolean(R.styleable.ButtonBarLayout_allowStacking, true);
    localTypedArray.recycle();
    if (getOrientation() == 1) {
      setStacked(this.mAllowStacking);
    }
  }
  
  private int getNextVisibleChildIndex(int paramInt)
  {
    int i = getChildCount();
    while (paramInt < i)
    {
      if (getChildAt(paramInt).getVisibility() == 0) {
        return paramInt;
      }
      paramInt++;
    }
    return -1;
  }
  
  private boolean isStacked()
  {
    return this.mStacked;
  }
  
  private void setStacked(boolean paramBoolean)
  {
    if ((this.mStacked != paramBoolean) && ((!paramBoolean) || (this.mAllowStacking)))
    {
      this.mStacked = paramBoolean;
      setOrientation(paramBoolean);
      int i;
      if (paramBoolean) {
        i = 8388613;
      } else {
        i = 80;
      }
      setGravity(i);
      View localView = findViewById(R.id.spacer);
      if (localView != null)
      {
        if (paramBoolean) {
          paramBoolean = true;
        } else {
          paramBoolean = true;
        }
        localView.setVisibility(paramBoolean);
      }
      for (paramBoolean = getChildCount() - 2; !paramBoolean; paramBoolean--) {
        bringChildToFront(getChildAt(paramBoolean));
      }
    }
  }
  
  protected void onMeasure(int paramInt1, int paramInt2)
  {
    int j = View.MeasureSpec.getSize(paramInt1);
    if (this.mAllowStacking)
    {
      if ((j > this.mLastWidthSize) && (isStacked())) {
        setStacked(false);
      }
      this.mLastWidthSize = j;
    }
    int i = 0;
    if ((!isStacked()) && (View.MeasureSpec.getMode(paramInt1) == 1073741824))
    {
      j = View.MeasureSpec.makeMeasureSpec(j, Integer.MIN_VALUE);
      i = 1;
    }
    else
    {
      j = paramInt1;
    }
    super.onMeasure(j, paramInt2);
    int k = i;
    if (this.mAllowStacking)
    {
      k = i;
      if (!isStacked())
      {
        if ((0xFF000000 & getMeasuredWidthAndState()) == 16777216) {
          j = 1;
        } else {
          j = 0;
        }
        k = i;
        if (j != 0)
        {
          setStacked(true);
          k = 1;
        }
      }
    }
    if (k != 0) {
      super.onMeasure(paramInt1, paramInt2);
    }
    i = 0;
    k = getNextVisibleChildIndex(0);
    if (k >= 0)
    {
      View localView = getChildAt(k);
      LinearLayout.LayoutParams localLayoutParams = (LinearLayout.LayoutParams)localView.getLayoutParams();
      j = 0 + (getPaddingTop() + localView.getMeasuredHeight() + localLayoutParams.topMargin + localLayoutParams.bottomMargin);
      if (isStacked())
      {
        k = getNextVisibleChildIndex(k + 1);
        i = j;
        if (k >= 0) {
          i = j + (getChildAt(k).getPaddingTop() + (int)(getResources().getDisplayMetrics().density * 16.0F));
        }
      }
      else
      {
        i = j + getPaddingBottom();
      }
    }
    if (ViewCompat.getMinimumHeight(this) != i)
    {
      setMinimumHeight(i);
      if (paramInt2 == 0) {
        super.onMeasure(paramInt1, paramInt2);
      }
    }
  }
  
  public void setAllowStacking(boolean paramBoolean)
  {
    if (this.mAllowStacking != paramBoolean)
    {
      this.mAllowStacking = paramBoolean;
      if ((!paramBoolean) && (isStacked())) {
        setStacked(false);
      }
      requestLayout();
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/appcompat/widget/ButtonBarLayout.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */