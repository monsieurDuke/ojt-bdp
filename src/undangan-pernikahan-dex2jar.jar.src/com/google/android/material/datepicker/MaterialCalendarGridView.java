package com.google.android.material.datepicker;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.MeasureSpec;
import android.widget.GridView;
import android.widget.ListAdapter;
import androidx.core.util.Pair;
import androidx.core.view.AccessibilityDelegateCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import com.google.android.material.R.id;
import com.google.android.material.internal.ViewUtils;
import java.util.Calendar;
import java.util.Collection;
import java.util.Iterator;
import mt.Log229316;
import mt.Log5ECF72;
import mt.LogE84000;

final class MaterialCalendarGridView
  extends GridView
{
  private final Calendar dayCompute = UtcDates.getUtcCalendar();
  private final boolean nestedScrollable;
  
  public MaterialCalendarGridView(Context paramContext)
  {
    this(paramContext, null);
  }
  
  public MaterialCalendarGridView(Context paramContext, AttributeSet paramAttributeSet)
  {
    this(paramContext, paramAttributeSet, 0);
  }
  
  public MaterialCalendarGridView(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
    if (MaterialDatePicker.isFullscreen(getContext()))
    {
      setNextFocusLeftId(R.id.cancel_button);
      setNextFocusRightId(R.id.confirm_button);
    }
    this.nestedScrollable = MaterialDatePicker.isNestedScrollable(getContext());
    ViewCompat.setAccessibilityDelegate(this, new AccessibilityDelegateCompat()
    {
      public void onInitializeAccessibilityNodeInfo(View paramAnonymousView, AccessibilityNodeInfoCompat paramAnonymousAccessibilityNodeInfoCompat)
      {
        super.onInitializeAccessibilityNodeInfo(paramAnonymousView, paramAnonymousAccessibilityNodeInfoCompat);
        paramAnonymousAccessibilityNodeInfoCompat.setCollectionInfo(null);
      }
    });
  }
  
  private void gainFocus(int paramInt, Rect paramRect)
  {
    if (paramInt == 33) {
      setSelection(getAdapter().lastPositionInMonth());
    } else if (paramInt == 130) {
      setSelection(getAdapter().firstPositionInMonth());
    } else {
      super.onFocusChanged(true, paramInt, paramRect);
    }
  }
  
  private View getChildAtPosition(int paramInt)
  {
    return getChildAt(paramInt - getFirstVisiblePosition());
  }
  
  private static int horizontalMidPoint(View paramView)
  {
    return paramView.getLeft() + paramView.getWidth() / 2;
  }
  
  private static boolean skipMonth(Long paramLong1, Long paramLong2, Long paramLong3, Long paramLong4)
  {
    boolean bool2 = true;
    if ((paramLong1 != null) && (paramLong2 != null) && (paramLong3 != null) && (paramLong4 != null))
    {
      boolean bool1 = bool2;
      if (paramLong3.longValue() <= paramLong2.longValue()) {
        if (paramLong4.longValue() < paramLong1.longValue()) {
          bool1 = bool2;
        } else {
          bool1 = false;
        }
      }
      return bool1;
    }
    return true;
  }
  
  public MonthAdapter getAdapter()
  {
    return (MonthAdapter)super.getAdapter();
  }
  
  protected void onAttachedToWindow()
  {
    super.onAttachedToWindow();
    getAdapter().notifyDataSetChanged();
  }
  
  protected final void onDraw(Canvas paramCanvas)
  {
    super.onDraw(paramCanvas);
    MonthAdapter localMonthAdapter = getAdapter();
    DateSelector localDateSelector = localMonthAdapter.dateSelector;
    CalendarStyle localCalendarStyle = localMonthAdapter.calendarStyle;
    int m = Math.max(localMonthAdapter.firstPositionInMonth(), getFirstVisiblePosition());
    int k = Math.min(localMonthAdapter.lastPositionInMonth(), getLastVisiblePosition());
    Long localLong1 = localMonthAdapter.getItem(m);
    Long localLong2 = localMonthAdapter.getItem(k);
    Iterator localIterator = localDateSelector.getSelectedRanges().iterator();
    while (localIterator.hasNext())
    {
      Object localObject = (Pair)localIterator.next();
      if (((Pair)localObject).first != null) {
        if (((Pair)localObject).second != null)
        {
          long l1 = ((Long)((Pair)localObject).first).longValue();
          long l2 = ((Long)((Pair)localObject).second).longValue();
          if (!skipMonth(localLong1, localLong2, Long.valueOf(l1), Long.valueOf(l2)))
          {
            boolean bool = ViewUtils.isLayoutRtl(this);
            int i2;
            int i;
            if (l1 < localLong1.longValue())
            {
              i2 = m;
              if (localMonthAdapter.isFirstInRow(i2)) {
                i = 0;
              } else if (!bool) {
                i = getChildAtPosition(i2 - 1).getRight();
              } else {
                i = getChildAtPosition(i2 - 1).getLeft();
              }
            }
            else
            {
              this.dayCompute.setTimeInMillis(l1);
              i2 = localMonthAdapter.dayToPosition(this.dayCompute.get(5));
              i = horizontalMidPoint(getChildAtPosition(i2));
            }
            int n;
            int j;
            if (l2 > localLong2.longValue())
            {
              n = k;
              if (localMonthAdapter.isLastInRow(n)) {
                j = getWidth();
              } else if (!bool) {
                j = getChildAtPosition(n).getRight();
              } else {
                j = getChildAtPosition(n).getLeft();
              }
            }
            else
            {
              this.dayCompute.setTimeInMillis(l2);
              n = localMonthAdapter.dayToPosition(this.dayCompute.get(5));
              j = horizontalMidPoint(getChildAtPosition(n));
            }
            int i5 = (int)localMonthAdapter.getItemId(i2);
            int i3 = (int)localMonthAdapter.getItemId(n);
            int i4 = i5;
            int i6 = n;
            while (i4 <= i3)
            {
              int i1 = i4 * getNumColumns();
              int i11 = i1 + getNumColumns() - 1;
              localObject = getChildAtPosition(i1);
              int i9 = ((View)localObject).getTop();
              int i7 = localCalendarStyle.day.getTopInset();
              int i8 = ((View)localObject).getBottom();
              int i10 = localCalendarStyle.day.getBottomInset();
              if (!bool)
              {
                if (i1 > i2) {
                  n = 0;
                } else {
                  n = i;
                }
                if (i6 > i11) {
                  i1 = getWidth();
                } else {
                  i1 = j;
                }
              }
              else
              {
                if (i6 > i11) {
                  n = 0;
                } else {
                  n = j;
                }
                if (i1 > i2) {
                  i1 = getWidth();
                } else {
                  i1 = i;
                }
              }
              paramCanvas.drawRect(n, i9 + i7, i1, i8 - i10, localCalendarStyle.rangeFill);
              i4++;
            }
          }
        }
      }
    }
  }
  
  protected void onFocusChanged(boolean paramBoolean, int paramInt, Rect paramRect)
  {
    if (paramBoolean) {
      gainFocus(paramInt, paramRect);
    } else {
      super.onFocusChanged(false, paramInt, paramRect);
    }
  }
  
  public boolean onKeyDown(int paramInt, KeyEvent paramKeyEvent)
  {
    if (!super.onKeyDown(paramInt, paramKeyEvent)) {
      return false;
    }
    if ((getSelectedItemPosition() != -1) && (getSelectedItemPosition() < getAdapter().firstPositionInMonth()))
    {
      if (19 == paramInt)
      {
        setSelection(getAdapter().firstPositionInMonth());
        return true;
      }
      return false;
    }
    return true;
  }
  
  public void onMeasure(int paramInt1, int paramInt2)
  {
    if (this.nestedScrollable)
    {
      super.onMeasure(paramInt1, View.MeasureSpec.makeMeasureSpec(16777215, Integer.MIN_VALUE));
      getLayoutParams().height = getMeasuredHeight();
    }
    else
    {
      super.onMeasure(paramInt1, paramInt2);
    }
  }
  
  public final void setAdapter(ListAdapter paramListAdapter)
  {
    if ((paramListAdapter instanceof MonthAdapter))
    {
      super.setAdapter(paramListAdapter);
      return;
    }
    paramListAdapter = String.format("%1$s must have its Adapter set to a %2$s", new Object[] { MaterialCalendarGridView.class.getCanonicalName(), MonthAdapter.class.getCanonicalName() });
    Log5ECF72.a(paramListAdapter);
    LogE84000.a(paramListAdapter);
    Log229316.a(paramListAdapter);
    throw new IllegalArgumentException(paramListAdapter);
  }
  
  public void setSelection(int paramInt)
  {
    if (paramInt < getAdapter().firstPositionInMonth()) {
      super.setSelection(getAdapter().firstPositionInMonth());
    } else {
      super.setSelection(paramInt);
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/com/google/android/material/datepicker/MaterialCalendarGridView.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */