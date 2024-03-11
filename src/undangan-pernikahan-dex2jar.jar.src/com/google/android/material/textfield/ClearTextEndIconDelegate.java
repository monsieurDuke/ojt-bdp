package com.google.android.material.textfield;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.content.res.Resources;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.EditText;
import com.google.android.material.R.drawable;
import com.google.android.material.R.string;
import com.google.android.material.animation.AnimationUtils;
import com.google.android.material.internal.CheckableImageButton;

class ClearTextEndIconDelegate
  extends EndIconDelegate
{
  private static final int ANIMATION_FADE_DURATION = 100;
  private static final int ANIMATION_SCALE_DURATION = 150;
  private static final float ANIMATION_SCALE_FROM_VALUE = 0.8F;
  private final TextWatcher clearTextEndIconTextWatcher = new TextWatcher()
  {
    public void afterTextChanged(Editable paramAnonymousEditable)
    {
      if (ClearTextEndIconDelegate.this.textInputLayout.getSuffixText() != null) {
        return;
      }
      paramAnonymousEditable = ClearTextEndIconDelegate.this;
      paramAnonymousEditable.animateIcon(ClearTextEndIconDelegate.access$000(paramAnonymousEditable));
    }
    
    public void beforeTextChanged(CharSequence paramAnonymousCharSequence, int paramAnonymousInt1, int paramAnonymousInt2, int paramAnonymousInt3) {}
    
    public void onTextChanged(CharSequence paramAnonymousCharSequence, int paramAnonymousInt1, int paramAnonymousInt2, int paramAnonymousInt3) {}
  };
  private final TextInputLayout.OnEditTextAttachedListener clearTextOnEditTextAttachedListener = new TextInputLayout.OnEditTextAttachedListener()
  {
    public void onEditTextAttached(TextInputLayout paramAnonymousTextInputLayout)
    {
      EditText localEditText = paramAnonymousTextInputLayout.getEditText();
      paramAnonymousTextInputLayout.setEndIconVisible(ClearTextEndIconDelegate.this.shouldBeVisible());
      localEditText.setOnFocusChangeListener(ClearTextEndIconDelegate.this.onFocusChangeListener);
      ClearTextEndIconDelegate.this.endIconView.setOnFocusChangeListener(ClearTextEndIconDelegate.this.onFocusChangeListener);
      localEditText.removeTextChangedListener(ClearTextEndIconDelegate.this.clearTextEndIconTextWatcher);
      localEditText.addTextChangedListener(ClearTextEndIconDelegate.this.clearTextEndIconTextWatcher);
    }
  };
  private final TextInputLayout.OnEndIconChangedListener endIconChangedListener = new TextInputLayout.OnEndIconChangedListener()
  {
    public void onEndIconChanged(final TextInputLayout paramAnonymousTextInputLayout, int paramAnonymousInt)
    {
      paramAnonymousTextInputLayout = paramAnonymousTextInputLayout.getEditText();
      if ((paramAnonymousTextInputLayout != null) && (paramAnonymousInt == 2))
      {
        paramAnonymousTextInputLayout.post(new Runnable()
        {
          public void run()
          {
            paramAnonymousTextInputLayout.removeTextChangedListener(ClearTextEndIconDelegate.this.clearTextEndIconTextWatcher);
            ClearTextEndIconDelegate.this.animateIcon(true);
          }
        });
        if (paramAnonymousTextInputLayout.getOnFocusChangeListener() == ClearTextEndIconDelegate.this.onFocusChangeListener) {
          paramAnonymousTextInputLayout.setOnFocusChangeListener(null);
        }
        if (ClearTextEndIconDelegate.this.endIconView.getOnFocusChangeListener() == ClearTextEndIconDelegate.this.onFocusChangeListener) {
          ClearTextEndIconDelegate.this.endIconView.setOnFocusChangeListener(null);
        }
      }
    }
  };
  private AnimatorSet iconInAnim;
  private ValueAnimator iconOutAnim;
  private final View.OnFocusChangeListener onFocusChangeListener = new View.OnFocusChangeListener()
  {
    public void onFocusChange(View paramAnonymousView, boolean paramAnonymousBoolean)
    {
      paramAnonymousView = ClearTextEndIconDelegate.this;
      paramAnonymousView.animateIcon(ClearTextEndIconDelegate.access$000(paramAnonymousView));
    }
  };
  
  ClearTextEndIconDelegate(TextInputLayout paramTextInputLayout, int paramInt)
  {
    super(paramTextInputLayout, paramInt);
  }
  
  private void animateIcon(boolean paramBoolean)
  {
    int i;
    if (this.textInputLayout.isEndIconVisible() == paramBoolean) {
      i = 1;
    } else {
      i = 0;
    }
    if ((paramBoolean) && (!this.iconInAnim.isRunning()))
    {
      this.iconOutAnim.cancel();
      this.iconInAnim.start();
      if (i != 0) {
        this.iconInAnim.end();
      }
    }
    else if (!paramBoolean)
    {
      this.iconInAnim.cancel();
      this.iconOutAnim.start();
      if (i != 0) {
        this.iconOutAnim.end();
      }
    }
  }
  
  private ValueAnimator getAlphaAnimator(float... paramVarArgs)
  {
    paramVarArgs = ValueAnimator.ofFloat(paramVarArgs);
    paramVarArgs.setInterpolator(AnimationUtils.LINEAR_INTERPOLATOR);
    paramVarArgs.setDuration(100L);
    paramVarArgs.addUpdateListener(new ValueAnimator.AnimatorUpdateListener()
    {
      public void onAnimationUpdate(ValueAnimator paramAnonymousValueAnimator)
      {
        float f = ((Float)paramAnonymousValueAnimator.getAnimatedValue()).floatValue();
        ClearTextEndIconDelegate.this.endIconView.setAlpha(f);
      }
    });
    return paramVarArgs;
  }
  
  private ValueAnimator getScaleAnimator()
  {
    ValueAnimator localValueAnimator = ValueAnimator.ofFloat(new float[] { 0.8F, 1.0F });
    localValueAnimator.setInterpolator(AnimationUtils.LINEAR_OUT_SLOW_IN_INTERPOLATOR);
    localValueAnimator.setDuration(150L);
    localValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener()
    {
      public void onAnimationUpdate(ValueAnimator paramAnonymousValueAnimator)
      {
        float f = ((Float)paramAnonymousValueAnimator.getAnimatedValue()).floatValue();
        ClearTextEndIconDelegate.this.endIconView.setScaleX(f);
        ClearTextEndIconDelegate.this.endIconView.setScaleY(f);
      }
    });
    return localValueAnimator;
  }
  
  private void initAnimators()
  {
    ValueAnimator localValueAnimator2 = getScaleAnimator();
    ValueAnimator localValueAnimator1 = getAlphaAnimator(new float[] { 0.0F, 1.0F });
    Object localObject = new AnimatorSet();
    this.iconInAnim = ((AnimatorSet)localObject);
    ((AnimatorSet)localObject).playTogether(new Animator[] { localValueAnimator2, localValueAnimator1 });
    this.iconInAnim.addListener(new AnimatorListenerAdapter()
    {
      public void onAnimationStart(Animator paramAnonymousAnimator)
      {
        ClearTextEndIconDelegate.this.textInputLayout.setEndIconVisible(true);
      }
    });
    localObject = getAlphaAnimator(new float[] { 1.0F, 0.0F });
    this.iconOutAnim = ((ValueAnimator)localObject);
    ((ValueAnimator)localObject).addListener(new AnimatorListenerAdapter()
    {
      public void onAnimationEnd(Animator paramAnonymousAnimator)
      {
        ClearTextEndIconDelegate.this.textInputLayout.setEndIconVisible(false);
      }
    });
  }
  
  private boolean shouldBeVisible()
  {
    EditText localEditText = this.textInputLayout.getEditText();
    boolean bool;
    if ((localEditText != null) && ((localEditText.hasFocus()) || (this.endIconView.hasFocus())) && (localEditText.getText().length() > 0)) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  void initialize()
  {
    TextInputLayout localTextInputLayout = this.textInputLayout;
    int i;
    if (this.customEndIcon == 0) {
      i = R.drawable.mtrl_ic_cancel;
    } else {
      i = this.customEndIcon;
    }
    localTextInputLayout.setEndIconDrawable(i);
    this.textInputLayout.setEndIconContentDescription(this.textInputLayout.getResources().getText(R.string.clear_text_end_icon_content_description));
    this.textInputLayout.setEndIconCheckable(false);
    this.textInputLayout.setEndIconOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        paramAnonymousView = ClearTextEndIconDelegate.this.textInputLayout.getEditText().getText();
        if (paramAnonymousView != null) {
          paramAnonymousView.clear();
        }
        ClearTextEndIconDelegate.this.textInputLayout.refreshEndIconDrawableState();
      }
    });
    this.textInputLayout.addOnEditTextAttachedListener(this.clearTextOnEditTextAttachedListener);
    this.textInputLayout.addOnEndIconChangedListener(this.endIconChangedListener);
    initAnimators();
  }
  
  void onSuffixVisibilityChanged(boolean paramBoolean)
  {
    if (this.textInputLayout.getSuffixText() == null) {
      return;
    }
    animateIcon(paramBoolean);
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/com/google/android/material/textfield/ClearTextEndIconDelegate.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */