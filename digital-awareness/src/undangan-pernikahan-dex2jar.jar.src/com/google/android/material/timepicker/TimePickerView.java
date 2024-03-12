package com.google.android.material.timepicker;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Checkable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.core.view.AccessibilityDelegateCompat;
import androidx.core.view.ViewCompat;
import com.google.android.material.R.id;
import com.google.android.material.R.layout;
import com.google.android.material.button.MaterialButtonToggleGroup;
import com.google.android.material.button.MaterialButtonToggleGroup.OnButtonCheckedListener;
import com.google.android.material.chip.Chip;
import java.util.Locale;
import mt.Log229316;
import mt.Log5ECF72;
import mt.LogE84000;

class TimePickerView
  extends ConstraintLayout
  implements TimePickerControls
{
  static final String GENERIC_VIEW_ACCESSIBILITY_CLASS_NAME = "android.view.View";
  private final ClockFaceView clockFace;
  private final ClockHandView clockHandView;
  private final Chip hourView;
  private final Chip minuteView;
  private OnDoubleTapListener onDoubleTapListener;
  private OnPeriodChangeListener onPeriodChangeListener;
  private OnSelectionChange onSelectionChangeListener;
  private final View.OnClickListener selectionListener = new View.OnClickListener()
  {
    public void onClick(View paramAnonymousView)
    {
      if (TimePickerView.this.onSelectionChangeListener != null) {
        TimePickerView.this.onSelectionChangeListener.onSelectionChanged(((Integer)paramAnonymousView.getTag(R.id.selection_type)).intValue());
      }
    }
  };
  private final MaterialButtonToggleGroup toggle;
  
  public TimePickerView(Context paramContext)
  {
    this(paramContext, null);
  }
  
  public TimePickerView(Context paramContext, AttributeSet paramAttributeSet)
  {
    this(paramContext, paramAttributeSet, 0);
  }
  
  public TimePickerView(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
    LayoutInflater.from(paramContext).inflate(R.layout.material_timepicker, this);
    this.clockFace = ((ClockFaceView)findViewById(R.id.material_clock_face));
    paramContext = (MaterialButtonToggleGroup)findViewById(R.id.material_clock_period_toggle);
    this.toggle = paramContext;
    paramContext.addOnButtonCheckedListener(new MaterialButtonToggleGroup.OnButtonCheckedListener()
    {
      public void onButtonChecked(MaterialButtonToggleGroup paramAnonymousMaterialButtonToggleGroup, int paramAnonymousInt, boolean paramAnonymousBoolean)
      {
        if (paramAnonymousInt == R.id.material_clock_period_pm_button) {
          paramAnonymousInt = 1;
        } else {
          paramAnonymousInt = 0;
        }
        if ((TimePickerView.this.onPeriodChangeListener != null) && (paramAnonymousBoolean)) {
          TimePickerView.this.onPeriodChangeListener.onPeriodChange(paramAnonymousInt);
        }
      }
    });
    this.minuteView = ((Chip)findViewById(R.id.material_minute_tv));
    this.hourView = ((Chip)findViewById(R.id.material_hour_tv));
    this.clockHandView = ((ClockHandView)findViewById(R.id.material_clock_hand));
    setupDoubleTap();
    setUpDisplay();
  }
  
  private void setUpDisplay()
  {
    this.minuteView.setTag(R.id.selection_type, Integer.valueOf(12));
    this.hourView.setTag(R.id.selection_type, Integer.valueOf(10));
    this.minuteView.setOnClickListener(this.selectionListener);
    this.hourView.setOnClickListener(this.selectionListener);
    this.minuteView.setAccessibilityClassName("android.view.View");
    this.hourView.setAccessibilityClassName("android.view.View");
  }
  
  private void setupDoubleTap()
  {
    View.OnTouchListener local4 = new View.OnTouchListener()
    {
      public boolean onDoubleTap(MotionEvent paramAnonymousMotionEvent)
      {
        paramAnonymousMotionEvent = TimePickerView.this.onDoubleTapListener;
        if (paramAnonymousMotionEvent != null)
        {
          paramAnonymousMotionEvent.onDoubleTap();
          return true;
        }
        return false;
      }
    }
    {
      public boolean onTouch(View paramAnonymousView, MotionEvent paramAnonymousMotionEvent)
      {
        if (((Checkable)paramAnonymousView).isChecked()) {
          return this.val$gestureDetector.onTouchEvent(paramAnonymousMotionEvent);
        }
        return false;
      }
    };
    this.minuteView.setOnTouchListener(local4);
    this.hourView.setOnTouchListener(local4);
  }
  
  private void updateSelection(Chip paramChip, boolean paramBoolean)
  {
    paramChip.setChecked(paramBoolean);
    int i;
    if (paramBoolean) {
      i = 2;
    } else {
      i = 0;
    }
    ViewCompat.setAccessibilityLiveRegion(paramChip, i);
  }
  
  private void updateToggleConstraints()
  {
    if (this.toggle.getVisibility() == 0)
    {
      ConstraintSet localConstraintSet = new ConstraintSet();
      localConstraintSet.clone(this);
      int i = ViewCompat.getLayoutDirection(this);
      int j = 1;
      if (i == 0) {
        i = 1;
      } else {
        i = 0;
      }
      if (i != 0) {
        j = 2;
      }
      localConstraintSet.clear(R.id.material_clock_display, j);
      localConstraintSet.applyTo(this);
    }
  }
  
  public void addOnRotateListener(ClockHandView.OnRotateListener paramOnRotateListener)
  {
    this.clockHandView.addOnRotateListener(paramOnRotateListener);
  }
  
  protected void onAttachedToWindow()
  {
    super.onAttachedToWindow();
    updateToggleConstraints();
  }
  
  protected void onVisibilityChanged(View paramView, int paramInt)
  {
    super.onVisibilityChanged(paramView, paramInt);
    if ((paramView == this) && (paramInt == 0)) {
      updateToggleConstraints();
    }
  }
  
  public void setActiveSelection(int paramInt)
  {
    Chip localChip = this.minuteView;
    boolean bool2 = true;
    boolean bool1;
    if (paramInt == 12) {
      bool1 = true;
    } else {
      bool1 = false;
    }
    updateSelection(localChip, bool1);
    localChip = this.hourView;
    if (paramInt == 10) {
      bool1 = bool2;
    } else {
      bool1 = false;
    }
    updateSelection(localChip, bool1);
  }
  
  public void setAnimateOnTouchUp(boolean paramBoolean)
  {
    this.clockHandView.setAnimateOnTouchUp(paramBoolean);
  }
  
  public void setHandRotation(float paramFloat)
  {
    this.clockHandView.setHandRotation(paramFloat);
  }
  
  public void setHandRotation(float paramFloat, boolean paramBoolean)
  {
    this.clockHandView.setHandRotation(paramFloat, paramBoolean);
  }
  
  public void setHourClickDelegate(AccessibilityDelegateCompat paramAccessibilityDelegateCompat)
  {
    ViewCompat.setAccessibilityDelegate(this.minuteView, paramAccessibilityDelegateCompat);
  }
  
  public void setMinuteHourDelegate(AccessibilityDelegateCompat paramAccessibilityDelegateCompat)
  {
    ViewCompat.setAccessibilityDelegate(this.hourView, paramAccessibilityDelegateCompat);
  }
  
  public void setOnActionUpListener(ClockHandView.OnActionUpListener paramOnActionUpListener)
  {
    this.clockHandView.setOnActionUpListener(paramOnActionUpListener);
  }
  
  void setOnDoubleTapListener(OnDoubleTapListener paramOnDoubleTapListener)
  {
    this.onDoubleTapListener = paramOnDoubleTapListener;
  }
  
  void setOnPeriodChangeListener(OnPeriodChangeListener paramOnPeriodChangeListener)
  {
    this.onPeriodChangeListener = paramOnPeriodChangeListener;
  }
  
  void setOnSelectionChangeListener(OnSelectionChange paramOnSelectionChange)
  {
    this.onSelectionChangeListener = paramOnSelectionChange;
  }
  
  public void setValues(String[] paramArrayOfString, int paramInt)
  {
    this.clockFace.setValues(paramArrayOfString, paramInt);
  }
  
  public void showToggle()
  {
    this.toggle.setVisibility(0);
  }
  
  public void updateTime(int paramInt1, int paramInt2, int paramInt3)
  {
    if (paramInt1 == 1) {
      paramInt1 = R.id.material_clock_period_pm_button;
    } else {
      paramInt1 = R.id.material_clock_period_am_button;
    }
    this.toggle.check(paramInt1);
    Object localObject = getResources().getConfiguration().locale;
    String str = String.format((Locale)localObject, "%02d", new Object[] { Integer.valueOf(paramInt3) });
    Log5ECF72.a(str);
    LogE84000.a(str);
    Log229316.a(str);
    localObject = String.format((Locale)localObject, "%02d", new Object[] { Integer.valueOf(paramInt2) });
    Log5ECF72.a(localObject);
    LogE84000.a(localObject);
    Log229316.a(localObject);
    if (!TextUtils.equals(this.minuteView.getText(), str)) {
      this.minuteView.setText(str);
    }
    if (!TextUtils.equals(this.hourView.getText(), (CharSequence)localObject)) {
      this.hourView.setText((CharSequence)localObject);
    }
  }
  
  static abstract interface OnDoubleTapListener
  {
    public abstract void onDoubleTap();
  }
  
  static abstract interface OnPeriodChangeListener
  {
    public abstract void onPeriodChange(int paramInt);
  }
  
  static abstract interface OnSelectionChange
  {
    public abstract void onSelectionChanged(int paramInt);
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/com/google/android/material/timepicker/TimePickerView.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */