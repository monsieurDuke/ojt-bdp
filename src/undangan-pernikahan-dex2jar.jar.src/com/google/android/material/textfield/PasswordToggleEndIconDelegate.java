package com.google.android.material.textfield;

import android.content.res.Resources;
import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import com.google.android.material.R.drawable;
import com.google.android.material.R.string;
import com.google.android.material.internal.CheckableImageButton;
import com.google.android.material.internal.TextWatcherAdapter;

class PasswordToggleEndIconDelegate
  extends EndIconDelegate
{
  private final TextInputLayout.OnEditTextAttachedListener onEditTextAttachedListener = new TextInputLayout.OnEditTextAttachedListener()
  {
    public void onEditTextAttached(TextInputLayout paramAnonymousTextInputLayout)
    {
      paramAnonymousTextInputLayout = paramAnonymousTextInputLayout.getEditText();
      PasswordToggleEndIconDelegate.this.endIconView.setChecked(PasswordToggleEndIconDelegate.this.hasPasswordTransformation() ^ true);
      paramAnonymousTextInputLayout.removeTextChangedListener(PasswordToggleEndIconDelegate.this.textWatcher);
      paramAnonymousTextInputLayout.addTextChangedListener(PasswordToggleEndIconDelegate.this.textWatcher);
    }
  };
  private final TextInputLayout.OnEndIconChangedListener onEndIconChangedListener = new TextInputLayout.OnEndIconChangedListener()
  {
    public void onEndIconChanged(final TextInputLayout paramAnonymousTextInputLayout, int paramAnonymousInt)
    {
      paramAnonymousTextInputLayout = paramAnonymousTextInputLayout.getEditText();
      if ((paramAnonymousTextInputLayout != null) && (paramAnonymousInt == 1))
      {
        paramAnonymousTextInputLayout.setTransformationMethod(PasswordTransformationMethod.getInstance());
        paramAnonymousTextInputLayout.post(new Runnable()
        {
          public void run()
          {
            paramAnonymousTextInputLayout.removeTextChangedListener(PasswordToggleEndIconDelegate.this.textWatcher);
          }
        });
      }
    }
  };
  private final TextWatcher textWatcher = new TextWatcherAdapter()
  {
    public void beforeTextChanged(CharSequence paramAnonymousCharSequence, int paramAnonymousInt1, int paramAnonymousInt2, int paramAnonymousInt3)
    {
      PasswordToggleEndIconDelegate.this.endIconView.setChecked(PasswordToggleEndIconDelegate.this.hasPasswordTransformation() ^ true);
    }
  };
  
  PasswordToggleEndIconDelegate(TextInputLayout paramTextInputLayout, int paramInt)
  {
    super(paramTextInputLayout, paramInt);
  }
  
  private boolean hasPasswordTransformation()
  {
    EditText localEditText = this.textInputLayout.getEditText();
    boolean bool;
    if ((localEditText != null) && ((localEditText.getTransformationMethod() instanceof PasswordTransformationMethod))) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  private static boolean isInputTypePassword(EditText paramEditText)
  {
    boolean bool;
    if ((paramEditText != null) && ((paramEditText.getInputType() == 16) || (paramEditText.getInputType() == 128) || (paramEditText.getInputType() == 144) || (paramEditText.getInputType() == 224))) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  void initialize()
  {
    Object localObject = this.textInputLayout;
    int i;
    if (this.customEndIcon == 0) {
      i = R.drawable.design_password_eye;
    } else {
      i = this.customEndIcon;
    }
    ((TextInputLayout)localObject).setEndIconDrawable(i);
    this.textInputLayout.setEndIconContentDescription(this.textInputLayout.getResources().getText(R.string.password_toggle_content_description));
    this.textInputLayout.setEndIconVisible(true);
    this.textInputLayout.setEndIconCheckable(true);
    this.textInputLayout.setEndIconOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        paramAnonymousView = PasswordToggleEndIconDelegate.this.textInputLayout.getEditText();
        if (paramAnonymousView == null) {
          return;
        }
        int i = paramAnonymousView.getSelectionEnd();
        if (PasswordToggleEndIconDelegate.this.hasPasswordTransformation()) {
          paramAnonymousView.setTransformationMethod(null);
        } else {
          paramAnonymousView.setTransformationMethod(PasswordTransformationMethod.getInstance());
        }
        if (i >= 0) {
          paramAnonymousView.setSelection(i);
        }
        PasswordToggleEndIconDelegate.this.textInputLayout.refreshEndIconDrawableState();
      }
    });
    this.textInputLayout.addOnEditTextAttachedListener(this.onEditTextAttachedListener);
    this.textInputLayout.addOnEndIconChangedListener(this.onEndIconChangedListener);
    localObject = this.textInputLayout.getEditText();
    if (isInputTypePassword((EditText)localObject)) {
      ((EditText)localObject).setTransformationMethod(PasswordTransformationMethod.getInstance());
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/com/google/android/material/textfield/PasswordToggleEndIconDelegate.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */