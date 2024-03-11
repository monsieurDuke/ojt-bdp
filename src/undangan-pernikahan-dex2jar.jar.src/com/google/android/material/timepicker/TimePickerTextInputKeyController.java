package com.google.android.material.timepicker;

import android.text.Editable;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnKeyListener;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import com.google.android.material.textfield.TextInputLayout;

class TimePickerTextInputKeyController
  implements TextView.OnEditorActionListener, View.OnKeyListener
{
  private final ChipTextInputComboView hourLayoutComboView;
  private boolean keyListenerRunning = false;
  private final ChipTextInputComboView minuteLayoutComboView;
  private final TimeModel time;
  
  TimePickerTextInputKeyController(ChipTextInputComboView paramChipTextInputComboView1, ChipTextInputComboView paramChipTextInputComboView2, TimeModel paramTimeModel)
  {
    this.hourLayoutComboView = paramChipTextInputComboView1;
    this.minuteLayoutComboView = paramChipTextInputComboView2;
    this.time = paramTimeModel;
  }
  
  private void moveSelection(int paramInt)
  {
    ChipTextInputComboView localChipTextInputComboView = this.minuteLayoutComboView;
    boolean bool2 = true;
    boolean bool1;
    if (paramInt == 12) {
      bool1 = true;
    } else {
      bool1 = false;
    }
    localChipTextInputComboView.setChecked(bool1);
    localChipTextInputComboView = this.hourLayoutComboView;
    if (paramInt == 10) {
      bool1 = bool2;
    } else {
      bool1 = false;
    }
    localChipTextInputComboView.setChecked(bool1);
    this.time.selection = paramInt;
  }
  
  private boolean onHourKeyPress(int paramInt, KeyEvent paramKeyEvent, EditText paramEditText)
  {
    Editable localEditable = paramEditText.getText();
    if (localEditable == null) {
      return false;
    }
    if ((paramInt >= 7) && (paramInt <= 16) && (paramKeyEvent.getAction() == 1) && (paramEditText.getSelectionStart() == 2) && (localEditable.length() == 2)) {
      paramInt = 1;
    } else {
      paramInt = 0;
    }
    if (paramInt != 0)
    {
      moveSelection(12);
      return true;
    }
    return false;
  }
  
  private boolean onMinuteKeyPress(int paramInt, KeyEvent paramKeyEvent, EditText paramEditText)
  {
    if ((paramInt == 67) && (paramKeyEvent.getAction() == 0) && (TextUtils.isEmpty(paramEditText.getText()))) {
      paramInt = 1;
    } else {
      paramInt = 0;
    }
    if (paramInt != 0)
    {
      moveSelection(10);
      return true;
    }
    return false;
  }
  
  public void bind()
  {
    Object localObject2 = this.hourLayoutComboView.getTextInput();
    Object localObject1 = this.minuteLayoutComboView.getTextInput();
    localObject2 = ((TextInputLayout)localObject2).getEditText();
    localObject1 = ((TextInputLayout)localObject1).getEditText();
    ((EditText)localObject2).setImeOptions(268435461);
    ((EditText)localObject1).setImeOptions(268435462);
    ((EditText)localObject2).setOnEditorActionListener(this);
    ((EditText)localObject2).setOnKeyListener(this);
    ((EditText)localObject1).setOnKeyListener(this);
  }
  
  public boolean onEditorAction(TextView paramTextView, int paramInt, KeyEvent paramKeyEvent)
  {
    boolean bool;
    if (paramInt == 5) {
      bool = true;
    } else {
      bool = false;
    }
    if (bool) {
      moveSelection(12);
    }
    return bool;
  }
  
  public boolean onKey(View paramView, int paramInt, KeyEvent paramKeyEvent)
  {
    if (this.keyListenerRunning) {
      return false;
    }
    this.keyListenerRunning = true;
    paramView = (EditText)paramView;
    boolean bool;
    if (this.time.selection == 12) {
      bool = onMinuteKeyPress(paramInt, paramKeyEvent, paramView);
    } else {
      bool = onHourKeyPress(paramInt, paramKeyEvent, paramView);
    }
    this.keyListenerRunning = false;
    return bool;
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/com/google/android/material/timepicker/TimePickerTextInputKeyController.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */