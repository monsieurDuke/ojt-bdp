package com.google.android.material.timepicker;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnDismissListener;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Pair;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.DialogFragment;
import com.google.android.material.R.attr;
import com.google.android.material.R.id;
import com.google.android.material.R.layout;
import com.google.android.material.R.string;
import com.google.android.material.R.style;
import com.google.android.material.R.styleable;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.resources.MaterialAttributes;
import com.google.android.material.shape.MaterialShapeDrawable;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

public final class MaterialTimePicker
  extends DialogFragment
  implements TimePickerView.OnDoubleTapListener
{
  public static final int INPUT_MODE_CLOCK = 0;
  static final String INPUT_MODE_EXTRA = "TIME_PICKER_INPUT_MODE";
  public static final int INPUT_MODE_KEYBOARD = 1;
  static final String NEGATIVE_BUTTON_TEXT_EXTRA = "TIME_PICKER_NEGATIVE_BUTTON_TEXT";
  static final String NEGATIVE_BUTTON_TEXT_RES_EXTRA = "TIME_PICKER_NEGATIVE_BUTTON_TEXT_RES";
  static final String OVERRIDE_THEME_RES_ID = "TIME_PICKER_OVERRIDE_THEME_RES_ID";
  static final String POSITIVE_BUTTON_TEXT_EXTRA = "TIME_PICKER_POSITIVE_BUTTON_TEXT";
  static final String POSITIVE_BUTTON_TEXT_RES_EXTRA = "TIME_PICKER_POSITIVE_BUTTON_TEXT_RES";
  static final String TIME_MODEL_EXTRA = "TIME_PICKER_TIME_MODEL";
  static final String TITLE_RES_EXTRA = "TIME_PICKER_TITLE_RES";
  static final String TITLE_TEXT_EXTRA = "TIME_PICKER_TITLE_TEXT";
  private TimePickerPresenter activePresenter;
  private Button cancelButton;
  private final Set<DialogInterface.OnCancelListener> cancelListeners = new LinkedHashSet();
  private int clockIcon;
  private final Set<DialogInterface.OnDismissListener> dismissListeners = new LinkedHashSet();
  private int inputMode = 0;
  private int keyboardIcon;
  private MaterialButton modeButton;
  private final Set<View.OnClickListener> negativeButtonListeners = new LinkedHashSet();
  private CharSequence negativeButtonText;
  private int negativeButtonTextResId = 0;
  private int overrideThemeResId = 0;
  private final Set<View.OnClickListener> positiveButtonListeners = new LinkedHashSet();
  private CharSequence positiveButtonText;
  private int positiveButtonTextResId = 0;
  private ViewStub textInputStub;
  private TimeModel time;
  private TimePickerClockPresenter timePickerClockPresenter;
  private TimePickerTextInputPresenter timePickerTextInputPresenter;
  private TimePickerView timePickerView;
  private int titleResId = 0;
  private CharSequence titleText;
  
  private Pair<Integer, Integer> dataForMode(int paramInt)
  {
    switch (paramInt)
    {
    default: 
      throw new IllegalArgumentException("no icon for mode: " + paramInt);
    case 1: 
      return new Pair(Integer.valueOf(this.clockIcon), Integer.valueOf(R.string.material_timepicker_clock_mode_description));
    }
    return new Pair(Integer.valueOf(this.keyboardIcon), Integer.valueOf(R.string.material_timepicker_text_input_mode_description));
  }
  
  private int getThemeResId()
  {
    int i = this.overrideThemeResId;
    if (i != 0) {
      return i;
    }
    TypedValue localTypedValue = MaterialAttributes.resolve(requireContext(), R.attr.materialTimePickerTheme);
    if (localTypedValue == null) {
      i = 0;
    } else {
      i = localTypedValue.data;
    }
    return i;
  }
  
  private TimePickerPresenter initializeOrRetrieveActivePresenterForMode(int paramInt, TimePickerView paramTimePickerView, ViewStub paramViewStub)
  {
    if (paramInt == 0)
    {
      paramViewStub = this.timePickerClockPresenter;
      if (paramViewStub == null) {
        paramTimePickerView = new TimePickerClockPresenter(paramTimePickerView, this.time);
      } else {
        paramTimePickerView = paramViewStub;
      }
      this.timePickerClockPresenter = paramTimePickerView;
      return paramTimePickerView;
    }
    if (this.timePickerTextInputPresenter == null) {
      this.timePickerTextInputPresenter = new TimePickerTextInputPresenter((LinearLayout)paramViewStub.inflate(), this.time);
    }
    this.timePickerTextInputPresenter.clearCheck();
    return this.timePickerTextInputPresenter;
  }
  
  private static MaterialTimePicker newInstance(Builder paramBuilder)
  {
    MaterialTimePicker localMaterialTimePicker = new MaterialTimePicker();
    Bundle localBundle = new Bundle();
    localBundle.putParcelable("TIME_PICKER_TIME_MODEL", paramBuilder.time);
    localBundle.putInt("TIME_PICKER_INPUT_MODE", paramBuilder.inputMode);
    localBundle.putInt("TIME_PICKER_TITLE_RES", paramBuilder.titleTextResId);
    if (paramBuilder.titleText != null) {
      localBundle.putCharSequence("TIME_PICKER_TITLE_TEXT", paramBuilder.titleText);
    }
    localBundle.putInt("TIME_PICKER_POSITIVE_BUTTON_TEXT_RES", paramBuilder.positiveButtonTextResId);
    if (paramBuilder.positiveButtonText != null) {
      localBundle.putCharSequence("TIME_PICKER_POSITIVE_BUTTON_TEXT", paramBuilder.positiveButtonText);
    }
    localBundle.putInt("TIME_PICKER_NEGATIVE_BUTTON_TEXT_RES", paramBuilder.negativeButtonTextResId);
    if (paramBuilder.negativeButtonText != null) {
      localBundle.putCharSequence("TIME_PICKER_NEGATIVE_BUTTON_TEXT", paramBuilder.negativeButtonText);
    }
    localBundle.putInt("TIME_PICKER_OVERRIDE_THEME_RES_ID", paramBuilder.overrideThemeResId);
    localMaterialTimePicker.setArguments(localBundle);
    return localMaterialTimePicker;
  }
  
  private void restoreState(Bundle paramBundle)
  {
    if (paramBundle == null) {
      return;
    }
    TimeModel localTimeModel = (TimeModel)paramBundle.getParcelable("TIME_PICKER_TIME_MODEL");
    this.time = localTimeModel;
    if (localTimeModel == null) {
      this.time = new TimeModel();
    }
    this.inputMode = paramBundle.getInt("TIME_PICKER_INPUT_MODE", 0);
    this.titleResId = paramBundle.getInt("TIME_PICKER_TITLE_RES", 0);
    this.titleText = paramBundle.getCharSequence("TIME_PICKER_TITLE_TEXT");
    this.positiveButtonTextResId = paramBundle.getInt("TIME_PICKER_POSITIVE_BUTTON_TEXT_RES", 0);
    this.positiveButtonText = paramBundle.getCharSequence("TIME_PICKER_POSITIVE_BUTTON_TEXT");
    this.negativeButtonTextResId = paramBundle.getInt("TIME_PICKER_NEGATIVE_BUTTON_TEXT_RES", 0);
    this.negativeButtonText = paramBundle.getCharSequence("TIME_PICKER_NEGATIVE_BUTTON_TEXT");
    this.overrideThemeResId = paramBundle.getInt("TIME_PICKER_OVERRIDE_THEME_RES_ID", 0);
  }
  
  private void updateCancelButtonVisibility()
  {
    Button localButton = this.cancelButton;
    if (localButton != null)
    {
      int i;
      if (isCancelable()) {
        i = 0;
      } else {
        i = 8;
      }
      localButton.setVisibility(i);
    }
  }
  
  private void updateInputMode(MaterialButton paramMaterialButton)
  {
    if ((paramMaterialButton != null) && (this.timePickerView != null) && (this.textInputStub != null))
    {
      Object localObject = this.activePresenter;
      if (localObject != null) {
        ((TimePickerPresenter)localObject).hide();
      }
      localObject = initializeOrRetrieveActivePresenterForMode(this.inputMode, this.timePickerView, this.textInputStub);
      this.activePresenter = ((TimePickerPresenter)localObject);
      ((TimePickerPresenter)localObject).show();
      this.activePresenter.invalidate();
      localObject = dataForMode(this.inputMode);
      paramMaterialButton.setIconResource(((Integer)((Pair)localObject).first).intValue());
      paramMaterialButton.setContentDescription(getResources().getString(((Integer)((Pair)localObject).second).intValue()));
      paramMaterialButton.sendAccessibilityEvent(4);
      return;
    }
  }
  
  public boolean addOnCancelListener(DialogInterface.OnCancelListener paramOnCancelListener)
  {
    return this.cancelListeners.add(paramOnCancelListener);
  }
  
  public boolean addOnDismissListener(DialogInterface.OnDismissListener paramOnDismissListener)
  {
    return this.dismissListeners.add(paramOnDismissListener);
  }
  
  public boolean addOnNegativeButtonClickListener(View.OnClickListener paramOnClickListener)
  {
    return this.negativeButtonListeners.add(paramOnClickListener);
  }
  
  public boolean addOnPositiveButtonClickListener(View.OnClickListener paramOnClickListener)
  {
    return this.positiveButtonListeners.add(paramOnClickListener);
  }
  
  public void clearOnCancelListeners()
  {
    this.cancelListeners.clear();
  }
  
  public void clearOnDismissListeners()
  {
    this.dismissListeners.clear();
  }
  
  public void clearOnNegativeButtonClickListeners()
  {
    this.negativeButtonListeners.clear();
  }
  
  public void clearOnPositiveButtonClickListeners()
  {
    this.positiveButtonListeners.clear();
  }
  
  public int getHour()
  {
    return this.time.hour % 24;
  }
  
  public int getInputMode()
  {
    return this.inputMode;
  }
  
  public int getMinute()
  {
    return this.time.minute;
  }
  
  TimePickerClockPresenter getTimePickerClockPresenter()
  {
    return this.timePickerClockPresenter;
  }
  
  public final void onCancel(DialogInterface paramDialogInterface)
  {
    Iterator localIterator = this.cancelListeners.iterator();
    while (localIterator.hasNext()) {
      ((DialogInterface.OnCancelListener)localIterator.next()).onCancel(paramDialogInterface);
    }
    super.onCancel(paramDialogInterface);
  }
  
  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    if (paramBundle == null) {
      paramBundle = getArguments();
    }
    restoreState(paramBundle);
  }
  
  public final Dialog onCreateDialog(Bundle paramBundle)
  {
    Dialog localDialog = new Dialog(requireContext(), getThemeResId());
    Context localContext = localDialog.getContext();
    int i = MaterialAttributes.resolveOrThrow(localContext, R.attr.colorSurface, MaterialTimePicker.class.getCanonicalName());
    paramBundle = new MaterialShapeDrawable(localContext, null, R.attr.materialTimePickerStyle, R.style.Widget_MaterialComponents_TimePicker);
    Object localObject = localContext.obtainStyledAttributes(null, R.styleable.MaterialTimePicker, R.attr.materialTimePickerStyle, R.style.Widget_MaterialComponents_TimePicker);
    this.clockIcon = ((TypedArray)localObject).getResourceId(R.styleable.MaterialTimePicker_clockIcon, 0);
    this.keyboardIcon = ((TypedArray)localObject).getResourceId(R.styleable.MaterialTimePicker_keyboardIcon, 0);
    ((TypedArray)localObject).recycle();
    paramBundle.initializeElevationOverlay(localContext);
    paramBundle.setFillColor(ColorStateList.valueOf(i));
    localObject = localDialog.getWindow();
    ((Window)localObject).setBackgroundDrawable(paramBundle);
    ((Window)localObject).requestFeature(1);
    ((Window)localObject).setLayout(-2, -2);
    paramBundle.setElevation(ViewCompat.getElevation(((Window)localObject).getDecorView()));
    return localDialog;
  }
  
  public final View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    paramLayoutInflater = (ViewGroup)paramLayoutInflater.inflate(R.layout.material_timepicker_dialog, paramViewGroup);
    paramViewGroup = (TimePickerView)paramLayoutInflater.findViewById(R.id.material_timepicker_view);
    this.timePickerView = paramViewGroup;
    paramViewGroup.setOnDoubleTapListener(this);
    this.textInputStub = ((ViewStub)paramLayoutInflater.findViewById(R.id.material_textinput_timepicker));
    this.modeButton = ((MaterialButton)paramLayoutInflater.findViewById(R.id.material_timepicker_mode_button));
    paramViewGroup = (TextView)paramLayoutInflater.findViewById(R.id.header_title);
    int i = this.titleResId;
    if (i != 0) {
      paramViewGroup.setText(i);
    } else if (!TextUtils.isEmpty(this.titleText)) {
      paramViewGroup.setText(this.titleText);
    }
    updateInputMode(this.modeButton);
    paramViewGroup = (Button)paramLayoutInflater.findViewById(R.id.material_timepicker_ok_button);
    paramViewGroup.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        Iterator localIterator = MaterialTimePicker.this.positiveButtonListeners.iterator();
        while (localIterator.hasNext()) {
          ((View.OnClickListener)localIterator.next()).onClick(paramAnonymousView);
        }
        MaterialTimePicker.this.dismiss();
      }
    });
    i = this.positiveButtonTextResId;
    if (i != 0) {
      paramViewGroup.setText(i);
    } else if (!TextUtils.isEmpty(this.positiveButtonText)) {
      paramViewGroup.setText(this.positiveButtonText);
    }
    paramViewGroup = (Button)paramLayoutInflater.findViewById(R.id.material_timepicker_cancel_button);
    this.cancelButton = paramViewGroup;
    paramViewGroup.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        Iterator localIterator = MaterialTimePicker.this.negativeButtonListeners.iterator();
        while (localIterator.hasNext()) {
          ((View.OnClickListener)localIterator.next()).onClick(paramAnonymousView);
        }
        MaterialTimePicker.this.dismiss();
      }
    });
    i = this.negativeButtonTextResId;
    if (i != 0) {
      this.cancelButton.setText(i);
    } else if (!TextUtils.isEmpty(this.negativeButtonText)) {
      this.cancelButton.setText(this.negativeButtonText);
    }
    updateCancelButtonVisibility();
    this.modeButton.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        paramAnonymousView = MaterialTimePicker.this;
        int i;
        if (paramAnonymousView.inputMode == 0) {
          i = 1;
        } else {
          i = 0;
        }
        MaterialTimePicker.access$1102(paramAnonymousView, i);
        paramAnonymousView = MaterialTimePicker.this;
        paramAnonymousView.updateInputMode(paramAnonymousView.modeButton);
      }
    });
    return paramLayoutInflater;
  }
  
  public void onDestroyView()
  {
    super.onDestroyView();
    this.activePresenter = null;
    this.timePickerClockPresenter = null;
    this.timePickerTextInputPresenter = null;
    TimePickerView localTimePickerView = this.timePickerView;
    if (localTimePickerView != null)
    {
      localTimePickerView.setOnDoubleTapListener(null);
      this.timePickerView = null;
    }
  }
  
  public final void onDismiss(DialogInterface paramDialogInterface)
  {
    Iterator localIterator = this.dismissListeners.iterator();
    while (localIterator.hasNext()) {
      ((DialogInterface.OnDismissListener)localIterator.next()).onDismiss(paramDialogInterface);
    }
    super.onDismiss(paramDialogInterface);
  }
  
  public void onDoubleTap()
  {
    this.inputMode = 1;
    updateInputMode(this.modeButton);
    this.timePickerTextInputPresenter.resetChecked();
  }
  
  public void onSaveInstanceState(Bundle paramBundle)
  {
    super.onSaveInstanceState(paramBundle);
    paramBundle.putParcelable("TIME_PICKER_TIME_MODEL", this.time);
    paramBundle.putInt("TIME_PICKER_INPUT_MODE", this.inputMode);
    paramBundle.putInt("TIME_PICKER_TITLE_RES", this.titleResId);
    paramBundle.putCharSequence("TIME_PICKER_TITLE_TEXT", this.titleText);
    paramBundle.putInt("TIME_PICKER_POSITIVE_BUTTON_TEXT_RES", this.positiveButtonTextResId);
    paramBundle.putCharSequence("TIME_PICKER_POSITIVE_BUTTON_TEXT", this.positiveButtonText);
    paramBundle.putInt("TIME_PICKER_NEGATIVE_BUTTON_TEXT_RES", this.negativeButtonTextResId);
    paramBundle.putCharSequence("TIME_PICKER_NEGATIVE_BUTTON_TEXT", this.negativeButtonText);
    paramBundle.putInt("TIME_PICKER_OVERRIDE_THEME_RES_ID", this.overrideThemeResId);
  }
  
  public boolean removeOnCancelListener(DialogInterface.OnCancelListener paramOnCancelListener)
  {
    return this.cancelListeners.remove(paramOnCancelListener);
  }
  
  public boolean removeOnDismissListener(DialogInterface.OnDismissListener paramOnDismissListener)
  {
    return this.dismissListeners.remove(paramOnDismissListener);
  }
  
  public boolean removeOnNegativeButtonClickListener(View.OnClickListener paramOnClickListener)
  {
    return this.negativeButtonListeners.remove(paramOnClickListener);
  }
  
  public boolean removeOnPositiveButtonClickListener(View.OnClickListener paramOnClickListener)
  {
    return this.positiveButtonListeners.remove(paramOnClickListener);
  }
  
  void setActivePresenter(TimePickerPresenter paramTimePickerPresenter)
  {
    this.activePresenter = paramTimePickerPresenter;
  }
  
  public void setCancelable(boolean paramBoolean)
  {
    super.setCancelable(paramBoolean);
    updateCancelButtonVisibility();
  }
  
  public void setHour(int paramInt)
  {
    this.time.setHour(paramInt);
    TimePickerPresenter localTimePickerPresenter = this.activePresenter;
    if (localTimePickerPresenter != null) {
      localTimePickerPresenter.invalidate();
    }
  }
  
  public void setMinute(int paramInt)
  {
    this.time.setMinute(paramInt);
    TimePickerPresenter localTimePickerPresenter = this.activePresenter;
    if (localTimePickerPresenter != null) {
      localTimePickerPresenter.invalidate();
    }
  }
  
  public static final class Builder
  {
    private int inputMode;
    private CharSequence negativeButtonText;
    private int negativeButtonTextResId = 0;
    private int overrideThemeResId = 0;
    private CharSequence positiveButtonText;
    private int positiveButtonTextResId = 0;
    private TimeModel time = new TimeModel();
    private CharSequence titleText;
    private int titleTextResId = 0;
    
    public MaterialTimePicker build()
    {
      return MaterialTimePicker.newInstance(this);
    }
    
    public Builder setHour(int paramInt)
    {
      this.time.setHourOfDay(paramInt);
      return this;
    }
    
    public Builder setInputMode(int paramInt)
    {
      this.inputMode = paramInt;
      return this;
    }
    
    public Builder setMinute(int paramInt)
    {
      this.time.setMinute(paramInt);
      return this;
    }
    
    public Builder setNegativeButtonText(int paramInt)
    {
      this.negativeButtonTextResId = paramInt;
      return this;
    }
    
    public Builder setNegativeButtonText(CharSequence paramCharSequence)
    {
      this.negativeButtonText = paramCharSequence;
      return this;
    }
    
    public Builder setPositiveButtonText(int paramInt)
    {
      this.positiveButtonTextResId = paramInt;
      return this;
    }
    
    public Builder setPositiveButtonText(CharSequence paramCharSequence)
    {
      this.positiveButtonText = paramCharSequence;
      return this;
    }
    
    public Builder setTheme(int paramInt)
    {
      this.overrideThemeResId = paramInt;
      return this;
    }
    
    public Builder setTimeFormat(int paramInt)
    {
      int j = this.time.hour;
      int i = this.time.minute;
      TimeModel localTimeModel = new TimeModel(paramInt);
      this.time = localTimeModel;
      localTimeModel.setMinute(i);
      this.time.setHourOfDay(j);
      return this;
    }
    
    public Builder setTitleText(int paramInt)
    {
      this.titleTextResId = paramInt;
      return this;
    }
    
    public Builder setTitleText(CharSequence paramCharSequence)
    {
      this.titleText = paramCharSequence;
      return this;
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/com/google/android/material/timepicker/MaterialTimePicker.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */