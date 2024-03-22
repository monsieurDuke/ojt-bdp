package com.google.android.material.timepicker;

import android.text.Editable;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import com.google.android.material.textfield.TextInputLayout;

/* loaded from: classes.dex */
class TimePickerTextInputKeyController implements TextView.OnEditorActionListener, View.OnKeyListener {
    private final ChipTextInputComboView hourLayoutComboView;
    private boolean keyListenerRunning = false;
    private final ChipTextInputComboView minuteLayoutComboView;
    private final TimeModel time;

    /* JADX INFO: Access modifiers changed from: package-private */
    public TimePickerTextInputKeyController(ChipTextInputComboView hourLayoutComboView, ChipTextInputComboView minuteLayoutComboView, TimeModel time) {
        this.hourLayoutComboView = hourLayoutComboView;
        this.minuteLayoutComboView = minuteLayoutComboView;
        this.time = time;
    }

    private void moveSelection(int selection) {
        this.minuteLayoutComboView.setChecked(selection == 12);
        this.hourLayoutComboView.setChecked(selection == 10);
        this.time.selection = selection;
    }

    private boolean onHourKeyPress(int keyCode, KeyEvent event, EditText editText) {
        Editable text = editText.getText();
        if (text == null) {
            return false;
        }
        if (!(keyCode >= 7 && keyCode <= 16 && event.getAction() == 1 && editText.getSelectionStart() == 2 && text.length() == 2)) {
            return false;
        }
        moveSelection(12);
        return true;
    }

    private boolean onMinuteKeyPress(int keyCode, KeyEvent event, EditText editText) {
        if (!(keyCode == 67 && event.getAction() == 0 && TextUtils.isEmpty(editText.getText()))) {
            return false;
        }
        moveSelection(10);
        return true;
    }

    public void bind() {
        TextInputLayout textInput = this.hourLayoutComboView.getTextInput();
        TextInputLayout textInput2 = this.minuteLayoutComboView.getTextInput();
        EditText editText = textInput.getEditText();
        EditText editText2 = textInput2.getEditText();
        editText.setImeOptions(268435461);
        editText2.setImeOptions(268435462);
        editText.setOnEditorActionListener(this);
        editText.setOnKeyListener(this);
        editText2.setOnKeyListener(this);
    }

    @Override // android.widget.TextView.OnEditorActionListener
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        boolean z = actionId == 5;
        if (z) {
            moveSelection(12);
        }
        return z;
    }

    @Override // android.view.View.OnKeyListener
    public boolean onKey(View view, int keyCode, KeyEvent event) {
        if (this.keyListenerRunning) {
            return false;
        }
        this.keyListenerRunning = true;
        EditText editText = (EditText) view;
        boolean onMinuteKeyPress = this.time.selection == 12 ? onMinuteKeyPress(keyCode, event, editText) : onHourKeyPress(keyCode, event, editText);
        this.keyListenerRunning = false;
        return onMinuteKeyPress;
    }
}
