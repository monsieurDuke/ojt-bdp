package com.google.android.material.timepicker;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build.VERSION;
import android.os.LocaleList;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View.OnClickListener;
import android.widget.Checkable;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import androidx.core.view.AccessibilityDelegateCompat;
import androidx.core.view.ViewCompat;
import com.google.android.material.R.id;
import com.google.android.material.R.layout;
import com.google.android.material.chip.Chip;
import com.google.android.material.internal.TextWatcherAdapter;
import com.google.android.material.internal.ViewUtils;
import com.google.android.material.textfield.TextInputLayout;
import java.util.Arrays;
import mt.Log229316;
import mt.Log5ECF72;
import mt.LogE84000;

class ChipTextInputComboView
  extends FrameLayout
  implements Checkable
{
  private final Chip chip;
  private final EditText editText;
  private TextView label;
  private final TextInputLayout textInputLayout;
  private TextWatcher watcher;
  
  public ChipTextInputComboView(Context paramContext)
  {
    this(paramContext, null);
  }
  
  public ChipTextInputComboView(Context paramContext, AttributeSet paramAttributeSet)
  {
    this(paramContext, paramAttributeSet, 0);
  }
  
  public ChipTextInputComboView(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
    paramAttributeSet = LayoutInflater.from(paramContext);
    paramContext = (Chip)paramAttributeSet.inflate(R.layout.material_time_chip, this, false);
    this.chip = paramContext;
    paramContext.setAccessibilityClassName("android.view.View");
    TextInputLayout localTextInputLayout = (TextInputLayout)paramAttributeSet.inflate(R.layout.material_time_input, this, false);
    this.textInputLayout = localTextInputLayout;
    EditText localEditText = localTextInputLayout.getEditText();
    this.editText = localEditText;
    localEditText.setVisibility(4);
    paramAttributeSet = new TextFormatter(null);
    this.watcher = paramAttributeSet;
    localEditText.addTextChangedListener(paramAttributeSet);
    updateHintLocales();
    addView(paramContext);
    addView(localTextInputLayout);
    this.label = ((TextView)findViewById(R.id.material_label));
    localEditText.setSaveEnabled(false);
    localEditText.setLongClickable(false);
  }
  
  private String formatText(CharSequence paramCharSequence)
  {
    paramCharSequence = TimeModel.formatText(getResources(), paramCharSequence);
    Log5ECF72.a(paramCharSequence);
    LogE84000.a(paramCharSequence);
    Log229316.a(paramCharSequence);
    return paramCharSequence;
  }
  
  private void updateHintLocales()
  {
    if (Build.VERSION.SDK_INT >= 24)
    {
      LocaleList localLocaleList = getContext().getResources().getConfiguration().getLocales();
      this.editText.setImeHintLocales(localLocaleList);
    }
  }
  
  public void addInputFilter(InputFilter paramInputFilter)
  {
    InputFilter[] arrayOfInputFilter1 = this.editText.getFilters();
    InputFilter[] arrayOfInputFilter2 = (InputFilter[])Arrays.copyOf(arrayOfInputFilter1, arrayOfInputFilter1.length + 1);
    arrayOfInputFilter2[arrayOfInputFilter1.length] = paramInputFilter;
    this.editText.setFilters(arrayOfInputFilter2);
  }
  
  public TextInputLayout getTextInput()
  {
    return this.textInputLayout;
  }
  
  public boolean isChecked()
  {
    return this.chip.isChecked();
  }
  
  protected void onConfigurationChanged(Configuration paramConfiguration)
  {
    super.onConfigurationChanged(paramConfiguration);
    updateHintLocales();
  }
  
  public void setChecked(boolean paramBoolean)
  {
    this.chip.setChecked(paramBoolean);
    Object localObject = this.editText;
    int j = 0;
    if (paramBoolean) {
      i = 0;
    } else {
      i = 4;
    }
    ((EditText)localObject).setVisibility(i);
    localObject = this.chip;
    int i = j;
    if (paramBoolean) {
      i = 8;
    }
    ((Chip)localObject).setVisibility(i);
    if (isChecked())
    {
      ViewUtils.requestFocusAndShowKeyboard(this.editText);
      if (!TextUtils.isEmpty(this.editText.getText()))
      {
        localObject = this.editText;
        ((EditText)localObject).setSelection(((EditText)localObject).getText().length());
      }
    }
  }
  
  public void setChipDelegate(AccessibilityDelegateCompat paramAccessibilityDelegateCompat)
  {
    ViewCompat.setAccessibilityDelegate(this.chip, paramAccessibilityDelegateCompat);
  }
  
  public void setCursorVisible(boolean paramBoolean)
  {
    this.editText.setCursorVisible(paramBoolean);
  }
  
  public void setHelperText(CharSequence paramCharSequence)
  {
    this.label.setText(paramCharSequence);
  }
  
  public void setOnClickListener(View.OnClickListener paramOnClickListener)
  {
    this.chip.setOnClickListener(paramOnClickListener);
  }
  
  public void setTag(int paramInt, Object paramObject)
  {
    this.chip.setTag(paramInt, paramObject);
  }
  
  public void setText(CharSequence paramCharSequence)
  {
    this.chip.setText(formatText(paramCharSequence));
    if (!TextUtils.isEmpty(this.editText.getText()))
    {
      this.editText.removeTextChangedListener(this.watcher);
      this.editText.setText(null);
      this.editText.addTextChangedListener(this.watcher);
    }
  }
  
  public void toggle()
  {
    this.chip.toggle();
  }
  
  private class TextFormatter
    extends TextWatcherAdapter
  {
    private static final String DEFAULT_TEXT = "00";
    
    private TextFormatter() {}
    
    public void afterTextChanged(Editable paramEditable)
    {
      if (TextUtils.isEmpty(paramEditable))
      {
        localChip = ChipTextInputComboView.this.chip;
        paramEditable = ChipTextInputComboView.this.formatText("00");
        Log5ECF72.a(paramEditable);
        LogE84000.a(paramEditable);
        Log229316.a(paramEditable);
        localChip.setText(paramEditable);
        return;
      }
      Chip localChip = ChipTextInputComboView.this.chip;
      paramEditable = ChipTextInputComboView.this.formatText(paramEditable);
      Log5ECF72.a(paramEditable);
      LogE84000.a(paramEditable);
      Log229316.a(paramEditable);
      localChip.setText(paramEditable);
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/com/google/android/material/timepicker/ChipTextInputComboView.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */