package androidx.appcompat.widget;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup;
import androidx.appcompat.R.id;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;

public class AlertDialogLayout
  extends LinearLayoutCompat
{
  public AlertDialogLayout(Context paramContext)
  {
    super(paramContext);
  }
  
  public AlertDialogLayout(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }
  
  private void forceUniformWidth(int paramInt1, int paramInt2)
  {
    int j = View.MeasureSpec.makeMeasureSpec(getMeasuredWidth(), 1073741824);
    for (int i = 0; i < paramInt1; i++)
    {
      View localView = getChildAt(i);
      if (localView.getVisibility() != 8)
      {
        LinearLayoutCompat.LayoutParams localLayoutParams = (LinearLayoutCompat.LayoutParams)localView.getLayoutParams();
        if (localLayoutParams.width == -1)
        {
          int k = localLayoutParams.height;
          localLayoutParams.height = localView.getMeasuredHeight();
          measureChildWithMargins(localView, j, 0, paramInt2, 0);
          localLayoutParams.height = k;
        }
      }
    }
  }
  
  private static int resolveMinimumHeight(View paramView)
  {
    int i = ViewCompat.getMinimumHeight(paramView);
    if (i > 0) {
      return i;
    }
    if ((paramView instanceof ViewGroup))
    {
      paramView = (ViewGroup)paramView;
      if (paramView.getChildCount() == 1) {
        return resolveMinimumHeight(paramView.getChildAt(0));
      }
    }
    return 0;
  }
  
  private void setChildFrame(View paramView, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    paramView.layout(paramInt1, paramInt2, paramInt1 + paramInt3, paramInt2 + paramInt4);
  }
  
  private boolean tryOnMeasure(int paramInt1, int paramInt2)
  {
    Object localObject3 = null;
    Object localObject1 = null;
    Object localObject2 = null;
    int i5 = getChildCount();
    View localView;
    for (int i = 0; i < i5; i++)
    {
      localView = getChildAt(i);
      if (localView.getVisibility() != 8)
      {
        j = localView.getId();
        if (j == R.id.topPanel)
        {
          localObject3 = localView;
        }
        else if (j == R.id.buttonPanel)
        {
          localObject1 = localView;
        }
        else
        {
          if ((j != R.id.contentPanel) && (j != R.id.customPanel)) {
            return false;
          }
          if (localObject2 != null) {
            return false;
          }
          localObject2 = localView;
        }
      }
    }
    int i7 = View.MeasureSpec.getMode(paramInt2);
    int i1 = View.MeasureSpec.getSize(paramInt2);
    int i6 = View.MeasureSpec.getMode(paramInt1);
    int n = 0;
    i = getPaddingTop() + getPaddingBottom();
    int m = i;
    if (localObject3 != null)
    {
      ((View)localObject3).measure(paramInt1, 0);
      m = i + ((View)localObject3).getMeasuredHeight();
      n = View.combineMeasuredStates(0, ((View)localObject3).getMeasuredState());
    }
    i = 0;
    int i3 = 0;
    int j = n;
    int k = m;
    if (localObject1 != null)
    {
      ((View)localObject1).measure(paramInt1, 0);
      i = resolveMinimumHeight((View)localObject1);
      i3 = ((View)localObject1).getMeasuredHeight() - i;
      k = m + i;
      j = View.combineMeasuredStates(n, ((View)localObject1).getMeasuredState());
    }
    int i2 = 0;
    if (localObject2 != null)
    {
      if (i7 == 0) {
        m = 0;
      } else {
        m = View.MeasureSpec.makeMeasureSpec(Math.max(0, i1 - k), i7);
      }
      ((View)localObject2).measure(paramInt1, m);
      i2 = ((View)localObject2).getMeasuredHeight();
      k += i2;
      j = View.combineMeasuredStates(j, ((View)localObject2).getMeasuredState());
    }
    int i4 = i1 - k;
    n = i4;
    m = j;
    i1 = k;
    if (localObject1 != null)
    {
      i1 = Math.min(i4, i3);
      m = i4;
      n = i;
      if (i1 > 0)
      {
        m = i4 - i1;
        n = i + i1;
      }
      ((View)localObject1).measure(paramInt1, View.MeasureSpec.makeMeasureSpec(n, 1073741824));
      i1 = k - i + ((View)localObject1).getMeasuredHeight();
      i = View.combineMeasuredStates(j, ((View)localObject1).getMeasuredState());
      n = m;
      m = i;
    }
    k = n;
    j = m;
    i = i1;
    if (localObject2 != null)
    {
      k = n;
      j = m;
      i = i1;
      if (n > 0)
      {
        ((View)localObject2).measure(paramInt1, View.MeasureSpec.makeMeasureSpec(i2 + n, i7));
        i = i1 - i2 + ((View)localObject2).getMeasuredHeight();
        j = View.combineMeasuredStates(m, ((View)localObject2).getMeasuredState());
        k = n - n;
      }
    }
    k = 0;
    n = 0;
    while (n < i5)
    {
      localView = getChildAt(n);
      m = k;
      if (localView.getVisibility() != 8) {
        m = Math.max(k, localView.getMeasuredWidth());
      }
      n++;
      k = m;
    }
    setMeasuredDimension(View.resolveSizeAndState(k + (getPaddingLeft() + getPaddingRight()), paramInt1, j), View.resolveSizeAndState(i, paramInt2, 0));
    if (i6 != 1073741824) {
      forceUniformWidth(i5, paramInt2);
    }
    return true;
  }
  
  protected void onLayout(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    int j = getPaddingLeft();
    int m = paramInt3 - paramInt1;
    int n = getPaddingRight();
    int i2 = getPaddingRight();
    paramInt1 = getMeasuredHeight();
    int i1 = getChildCount();
    int k = getGravity();
    switch (k & 0x70)
    {
    default: 
      paramInt1 = getPaddingTop();
      break;
    case 80: 
      paramInt1 = getPaddingTop() + paramInt4 - paramInt2 - paramInt1;
      break;
    case 16: 
      paramInt1 = getPaddingTop() + (paramInt4 - paramInt2 - paramInt1) / 2;
    }
    Object localObject = getDividerDrawable();
    if (localObject == null) {
      paramInt3 = 0;
    } else {
      paramInt3 = ((Drawable)localObject).getIntrinsicHeight();
    }
    for (paramInt4 = 0; paramInt4 < i1; paramInt4++)
    {
      View localView = getChildAt(paramInt4);
      if ((localView != null) && (localView.getVisibility() != 8))
      {
        int i4 = localView.getMeasuredWidth();
        int i3 = localView.getMeasuredHeight();
        localObject = (LinearLayoutCompat.LayoutParams)localView.getLayoutParams();
        paramInt2 = ((LinearLayoutCompat.LayoutParams)localObject).gravity;
        if (paramInt2 < 0) {
          paramInt2 = k & 0x800007;
        }
        switch (GravityCompat.getAbsoluteGravity(paramInt2, ViewCompat.getLayoutDirection(this)) & 0x7)
        {
        default: 
          paramInt2 = ((LinearLayoutCompat.LayoutParams)localObject).leftMargin + j;
          break;
        case 5: 
          paramInt2 = m - n - i4 - ((LinearLayoutCompat.LayoutParams)localObject).rightMargin;
          break;
        case 1: 
          paramInt2 = (m - j - i2 - i4) / 2 + j + ((LinearLayoutCompat.LayoutParams)localObject).leftMargin - ((LinearLayoutCompat.LayoutParams)localObject).rightMargin;
        }
        int i = paramInt1;
        if (hasDividerBeforeChildAt(paramInt4)) {
          i = paramInt1 + paramInt3;
        }
        paramInt1 = i + ((LinearLayoutCompat.LayoutParams)localObject).topMargin;
        setChildFrame(localView, paramInt2, paramInt1, i4, i3);
        paramInt1 += i3 + ((LinearLayoutCompat.LayoutParams)localObject).bottomMargin;
      }
    }
  }
  
  protected void onMeasure(int paramInt1, int paramInt2)
  {
    if (!tryOnMeasure(paramInt1, paramInt2)) {
      super.onMeasure(paramInt1, paramInt2);
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/appcompat/widget/AlertDialogLayout.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */