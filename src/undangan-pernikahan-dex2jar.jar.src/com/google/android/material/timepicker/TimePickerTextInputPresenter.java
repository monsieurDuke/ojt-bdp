package com.google.android.material.timepicker;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build.VERSION;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.core.content.ContextCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import com.google.android.material.R.attr;
import com.google.android.material.R.id;
import com.google.android.material.R.string;
import com.google.android.material.button.MaterialButtonToggleGroup;
import com.google.android.material.button.MaterialButtonToggleGroup.OnButtonCheckedListener;
import com.google.android.material.color.MaterialColors;
import com.google.android.material.internal.TextWatcherAdapter;
import com.google.android.material.textfield.TextInputLayout;
import java.util.Locale;
import mt.Log229316;
import mt.Log5ECF72;
import mt.LogE84000;

class TimePickerTextInputPresenter
  implements TimePickerView.OnSelectionChange, TimePickerPresenter
{
  private final TimePickerTextInputKeyController controller;
  private final EditText hourEditText;
  private final ChipTextInputComboView hourTextInput;
  private final TextWatcher hourTextWatcher = new TextWatcherAdapter()
  {
    public void afterTextChanged(Editable paramAnonymousEditable)
    {
      try
      {
        if (TextUtils.isEmpty(paramAnonymousEditable))
        {
          TimePickerTextInputPresenter.this.time.setHour(0);
          return;
        }
        int i = Integer.parseInt(paramAnonymousEditable.toString());
        TimePickerTextInputPresenter.this.time.setHour(i);
      }
      catch (NumberFormatException paramAnonymousEditable) {}
    }
  };
  private final EditText minuteEditText;
  private final ChipTextInputComboView minuteTextInput;
  private final TextWatcher minuteTextWatcher = new TextWatcherAdapter()
  {
    public void afterTextChanged(Editable paramAnonymousEditable)
    {
      try
      {
        if (TextUtils.isEmpty(paramAnonymousEditable))
        {
          TimePickerTextInputPresenter.this.time.setMinute(0);
          return;
        }
        int i = Integer.parseInt(paramAnonymousEditable.toString());
        TimePickerTextInputPresenter.this.time.setMinute(i);
      }
      catch (NumberFormatException paramAnonymousEditable) {}
    }
  };
  private final TimeModel time;
  private final LinearLayout timePickerView;
  private MaterialButtonToggleGroup toggle;
  
  public TimePickerTextInputPresenter(LinearLayout paramLinearLayout, final TimeModel paramTimeModel)
  {
    this.timePickerView = paramLinearLayout;
    this.time = paramTimeModel;
    Resources localResources = paramLinearLayout.getResources();
    ChipTextInputComboView localChipTextInputComboView1 = (ChipTextInputComboView)paramLinearLayout.findViewById(R.id.material_minute_text_input);
    this.minuteTextInput = localChipTextInputComboView1;
    ChipTextInputComboView localChipTextInputComboView2 = (ChipTextInputComboView)paramLinearLayout.findViewById(R.id.material_hour_text_input);
    this.hourTextInput = localChipTextInputComboView2;
    Object localObject1 = (TextView)localChipTextInputComboView1.findViewById(R.id.material_label);
    Object localObject2 = (TextView)localChipTextInputComboView2.findViewById(R.id.material_label);
    ((TextView)localObject1).setText(localResources.getString(R.string.material_timepicker_minute));
    ((TextView)localObject2).setText(localResources.getString(R.string.material_timepicker_hour));
    localChipTextInputComboView1.setTag(R.id.selection_type, Integer.valueOf(12));
    localChipTextInputComboView2.setTag(R.id.selection_type, Integer.valueOf(10));
    if (paramTimeModel.format == 0) {
      setupPeriodToggle();
    }
    localObject1 = new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        TimePickerTextInputPresenter.this.onSelectionChanged(((Integer)paramAnonymousView.getTag(R.id.selection_type)).intValue());
      }
    };
    localChipTextInputComboView2.setOnClickListener((View.OnClickListener)localObject1);
    localChipTextInputComboView1.setOnClickListener((View.OnClickListener)localObject1);
    localChipTextInputComboView2.addInputFilter(paramTimeModel.getHourInputValidator());
    localChipTextInputComboView1.addInputFilter(paramTimeModel.getMinuteInputValidator());
    localObject1 = localChipTextInputComboView2.getTextInput().getEditText();
    this.hourEditText = ((EditText)localObject1);
    localObject2 = localChipTextInputComboView1.getTextInput().getEditText();
    this.minuteEditText = ((EditText)localObject2);
    if (Build.VERSION.SDK_INT < 21)
    {
      int i = MaterialColors.getColor(paramLinearLayout, R.attr.colorPrimary);
      setCursorDrawableColor((EditText)localObject1, i);
      setCursorDrawableColor((EditText)localObject2, i);
    }
    this.controller = new TimePickerTextInputKeyController(localChipTextInputComboView2, localChipTextInputComboView1, paramTimeModel);
    localChipTextInputComboView2.setChipDelegate(new ClickActionDelegate(paramLinearLayout.getContext(), R.string.material_hour_selection)
    {
      public void onInitializeAccessibilityNodeInfo(View paramAnonymousView, AccessibilityNodeInfoCompat paramAnonymousAccessibilityNodeInfoCompat)
      {
        super.onInitializeAccessibilityNodeInfo(paramAnonymousView, paramAnonymousAccessibilityNodeInfoCompat);
        paramAnonymousView = paramAnonymousView.getResources();
        int i = R.string.material_hour_suffix;
        String str = String.valueOf(paramTimeModel.getHourForDisplay());
        Log5ECF72.a(str);
        LogE84000.a(str);
        Log229316.a(str);
        paramAnonymousAccessibilityNodeInfoCompat.setContentDescription(paramAnonymousView.getString(i, new Object[] { str }));
      }
    });
    localChipTextInputComboView1.setChipDelegate(new ClickActionDelegate(paramLinearLayout.getContext(), R.string.material_minute_selection)
    {
      public void onInitializeAccessibilityNodeInfo(View paramAnonymousView, AccessibilityNodeInfoCompat paramAnonymousAccessibilityNodeInfoCompat)
      {
        super.onInitializeAccessibilityNodeInfo(paramAnonymousView, paramAnonymousAccessibilityNodeInfoCompat);
        Resources localResources = paramAnonymousView.getResources();
        int i = R.string.material_minute_suffix;
        paramAnonymousView = String.valueOf(paramTimeModel.minute);
        Log5ECF72.a(paramAnonymousView);
        LogE84000.a(paramAnonymousView);
        Log229316.a(paramAnonymousView);
        paramAnonymousAccessibilityNodeInfoCompat.setContentDescription(localResources.getString(i, new Object[] { paramAnonymousView }));
      }
    });
    initialize();
  }
  
  private void addTextWatchers()
  {
    this.hourEditText.addTextChangedListener(this.hourTextWatcher);
    this.minuteEditText.addTextChangedListener(this.minuteTextWatcher);
  }
  
  private void removeTextWatchers()
  {
    this.hourEditText.removeTextChangedListener(this.hourTextWatcher);
    this.minuteEditText.removeTextChangedListener(this.minuteTextWatcher);
  }
  
  /* Error */
  private static void setCursorDrawableColor(EditText paramEditText, int paramInt)
  {
    // Byte code:
    //   0: aload_0
    //   1: invokevirtual 217	android/widget/EditText:getContext	()Landroid/content/Context;
    //   4: astore_3
    //   5: ldc 85
    //   7: ldc -37
    //   9: invokevirtual 225	java/lang/Class:getDeclaredField	(Ljava/lang/String;)Ljava/lang/reflect/Field;
    //   12: astore 4
    //   14: aload 4
    //   16: iconst_1
    //   17: invokevirtual 231	java/lang/reflect/Field:setAccessible	(Z)V
    //   20: aload 4
    //   22: aload_0
    //   23: invokevirtual 235	java/lang/reflect/Field:getInt	(Ljava/lang/Object;)I
    //   26: istore_2
    //   27: ldc 85
    //   29: ldc -19
    //   31: invokevirtual 225	java/lang/Class:getDeclaredField	(Ljava/lang/String;)Ljava/lang/reflect/Field;
    //   34: astore 4
    //   36: aload 4
    //   38: iconst_1
    //   39: invokevirtual 231	java/lang/reflect/Field:setAccessible	(Z)V
    //   42: aload 4
    //   44: aload_0
    //   45: invokevirtual 241	java/lang/reflect/Field:get	(Ljava/lang/Object;)Ljava/lang/Object;
    //   48: astore_0
    //   49: aload_0
    //   50: invokevirtual 245	java/lang/Object:getClass	()Ljava/lang/Class;
    //   53: ldc -9
    //   55: invokevirtual 225	java/lang/Class:getDeclaredField	(Ljava/lang/String;)Ljava/lang/reflect/Field;
    //   58: astore 4
    //   60: aload 4
    //   62: iconst_1
    //   63: invokevirtual 231	java/lang/reflect/Field:setAccessible	(Z)V
    //   66: aload_3
    //   67: iload_2
    //   68: invokestatic 253	androidx/appcompat/content/res/AppCompatResources:getDrawable	(Landroid/content/Context;I)Landroid/graphics/drawable/Drawable;
    //   71: astore_3
    //   72: aload_3
    //   73: iload_1
    //   74: getstatic 259	android/graphics/PorterDuff$Mode:SRC_IN	Landroid/graphics/PorterDuff$Mode;
    //   77: invokevirtual 265	android/graphics/drawable/Drawable:setColorFilter	(ILandroid/graphics/PorterDuff$Mode;)V
    //   80: aload 4
    //   82: aload_0
    //   83: iconst_2
    //   84: anewarray 261	android/graphics/drawable/Drawable
    //   87: dup
    //   88: iconst_0
    //   89: aload_3
    //   90: aastore
    //   91: dup
    //   92: iconst_1
    //   93: aload_3
    //   94: aastore
    //   95: invokevirtual 269	java/lang/reflect/Field:set	(Ljava/lang/Object;Ljava/lang/Object;)V
    //   98: goto +4 -> 102
    //   101: astore_0
    //   102: return
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	103	0	paramEditText	EditText
    //   0	103	1	paramInt	int
    //   26	42	2	i	int
    //   4	90	3	localObject	Object
    //   12	69	4	localField	java.lang.reflect.Field
    // Exception table:
    //   from	to	target	type
    //   0	98	101	finally
  }
  
  private void setTime(TimeModel paramTimeModel)
  {
    removeTextWatchers();
    Locale localLocale = this.timePickerView.getResources().getConfiguration().locale;
    String str = String.format(localLocale, "%02d", new Object[] { Integer.valueOf(paramTimeModel.minute) });
    Log5ECF72.a(str);
    LogE84000.a(str);
    Log229316.a(str);
    paramTimeModel = String.format(localLocale, "%02d", new Object[] { Integer.valueOf(paramTimeModel.getHourForDisplay()) });
    Log5ECF72.a(paramTimeModel);
    LogE84000.a(paramTimeModel);
    Log229316.a(paramTimeModel);
    this.minuteTextInput.setText(str);
    this.hourTextInput.setText(paramTimeModel);
    addTextWatchers();
    updateSelection();
  }
  
  private void setupPeriodToggle()
  {
    MaterialButtonToggleGroup localMaterialButtonToggleGroup = (MaterialButtonToggleGroup)this.timePickerView.findViewById(R.id.material_clock_period_toggle);
    this.toggle = localMaterialButtonToggleGroup;
    localMaterialButtonToggleGroup.addOnButtonCheckedListener(new MaterialButtonToggleGroup.OnButtonCheckedListener()
    {
      public void onButtonChecked(MaterialButtonToggleGroup paramAnonymousMaterialButtonToggleGroup, int paramAnonymousInt, boolean paramAnonymousBoolean)
      {
        if (paramAnonymousInt == R.id.material_clock_period_pm_button) {
          paramAnonymousInt = 1;
        } else {
          paramAnonymousInt = 0;
        }
        TimePickerTextInputPresenter.this.time.setPeriod(paramAnonymousInt);
      }
    });
    this.toggle.setVisibility(0);
    updateSelection();
  }
  
  private void updateSelection()
  {
    MaterialButtonToggleGroup localMaterialButtonToggleGroup = this.toggle;
    if (localMaterialButtonToggleGroup == null) {
      return;
    }
    int i;
    if (this.time.period == 0) {
      i = R.id.material_clock_period_am_button;
    } else {
      i = R.id.material_clock_period_pm_button;
    }
    localMaterialButtonToggleGroup.check(i);
  }
  
  public void clearCheck()
  {
    this.minuteTextInput.setChecked(false);
    this.hourTextInput.setChecked(false);
  }
  
  public void hide()
  {
    View localView = this.timePickerView.getFocusedChild();
    if (localView == null)
    {
      this.timePickerView.setVisibility(8);
      return;
    }
    InputMethodManager localInputMethodManager = (InputMethodManager)ContextCompat.getSystemService(this.timePickerView.getContext(), InputMethodManager.class);
    if (localInputMethodManager != null) {
      localInputMethodManager.hideSoftInputFromWindow(localView.getWindowToken(), 0);
    }
    this.timePickerView.setVisibility(8);
  }
  
  public void initialize()
  {
    addTextWatchers();
    setTime(this.time);
    this.controller.bind();
  }
  
  public void invalidate()
  {
    setTime(this.time);
  }
  
  public void onSelectionChanged(int paramInt)
  {
    this.time.selection = paramInt;
    ChipTextInputComboView localChipTextInputComboView = this.minuteTextInput;
    boolean bool2 = true;
    boolean bool1;
    if (paramInt == 12) {
      bool1 = true;
    } else {
      bool1 = false;
    }
    localChipTextInputComboView.setChecked(bool1);
    localChipTextInputComboView = this.hourTextInput;
    if (paramInt == 10) {
      bool1 = bool2;
    } else {
      bool1 = false;
    }
    localChipTextInputComboView.setChecked(bool1);
    updateSelection();
  }
  
  public void resetChecked()
  {
    ChipTextInputComboView localChipTextInputComboView = this.minuteTextInput;
    int i = this.time.selection;
    boolean bool2 = true;
    boolean bool1;
    if (i == 12) {
      bool1 = true;
    } else {
      bool1 = false;
    }
    localChipTextInputComboView.setChecked(bool1);
    localChipTextInputComboView = this.hourTextInput;
    if (this.time.selection == 10) {
      bool1 = bool2;
    } else {
      bool1 = false;
    }
    localChipTextInputComboView.setChecked(bool1);
  }
  
  public void show()
  {
    this.timePickerView.setVisibility(0);
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/com/google/android/material/timepicker/TimePickerTextInputPresenter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */