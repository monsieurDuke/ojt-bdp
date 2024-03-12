package com.google.android.material.textfield;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.PorterDuff.Mode;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup.MarginLayoutParams;
import android.widget.EditText;
import android.widget.FrameLayout.LayoutParams;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.TintTypedArray;
import androidx.core.view.MarginLayoutParamsCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import androidx.core.widget.TextViewCompat;
import com.google.android.material.R.dimen;
import com.google.android.material.R.id;
import com.google.android.material.R.layout;
import com.google.android.material.R.styleable;
import com.google.android.material.internal.CheckableImageButton;
import com.google.android.material.internal.ViewUtils;
import com.google.android.material.resources.MaterialResources;

class StartCompoundLayout
  extends LinearLayout
{
  private boolean hintExpanded;
  private CharSequence prefixText;
  private final TextView prefixTextView;
  private View.OnLongClickListener startIconOnLongClickListener;
  private ColorStateList startIconTintList;
  private PorterDuff.Mode startIconTintMode;
  private final CheckableImageButton startIconView;
  private final TextInputLayout textInputLayout;
  
  StartCompoundLayout(TextInputLayout paramTextInputLayout, TintTypedArray paramTintTypedArray)
  {
    super(paramTextInputLayout.getContext());
    this.textInputLayout = paramTextInputLayout;
    setVisibility(8);
    setOrientation(0);
    setLayoutParams(new FrameLayout.LayoutParams(-2, -1, 8388611));
    CheckableImageButton localCheckableImageButton = (CheckableImageButton)LayoutInflater.from(getContext()).inflate(R.layout.design_text_input_start_icon, this, false);
    this.startIconView = localCheckableImageButton;
    paramTextInputLayout = new AppCompatTextView(getContext());
    this.prefixTextView = paramTextInputLayout;
    initStartIconView(paramTintTypedArray);
    initPrefixTextView(paramTintTypedArray);
    addView(localCheckableImageButton);
    addView(paramTextInputLayout);
  }
  
  private void initPrefixTextView(TintTypedArray paramTintTypedArray)
  {
    this.prefixTextView.setVisibility(8);
    this.prefixTextView.setId(R.id.textinput_prefix_text);
    this.prefixTextView.setLayoutParams(new LinearLayout.LayoutParams(-2, -2));
    ViewCompat.setAccessibilityLiveRegion(this.prefixTextView, 1);
    setPrefixTextAppearance(paramTintTypedArray.getResourceId(R.styleable.TextInputLayout_prefixTextAppearance, 0));
    if (paramTintTypedArray.hasValue(R.styleable.TextInputLayout_prefixTextColor)) {
      setPrefixTextColor(paramTintTypedArray.getColorStateList(R.styleable.TextInputLayout_prefixTextColor));
    }
    setPrefixText(paramTintTypedArray.getText(R.styleable.TextInputLayout_prefixText));
  }
  
  private void initStartIconView(TintTypedArray paramTintTypedArray)
  {
    if (MaterialResources.isFontScaleAtLeast1_3(getContext())) {
      MarginLayoutParamsCompat.setMarginEnd((ViewGroup.MarginLayoutParams)this.startIconView.getLayoutParams(), 0);
    }
    setStartIconOnClickListener(null);
    setStartIconOnLongClickListener(null);
    if (paramTintTypedArray.hasValue(R.styleable.TextInputLayout_startIconTint)) {
      this.startIconTintList = MaterialResources.getColorStateList(getContext(), paramTintTypedArray, R.styleable.TextInputLayout_startIconTint);
    }
    if (paramTintTypedArray.hasValue(R.styleable.TextInputLayout_startIconTintMode)) {
      this.startIconTintMode = ViewUtils.parseTintMode(paramTintTypedArray.getInt(R.styleable.TextInputLayout_startIconTintMode, -1), null);
    }
    if (paramTintTypedArray.hasValue(R.styleable.TextInputLayout_startIconDrawable))
    {
      setStartIconDrawable(paramTintTypedArray.getDrawable(R.styleable.TextInputLayout_startIconDrawable));
      if (paramTintTypedArray.hasValue(R.styleable.TextInputLayout_startIconContentDescription)) {
        setStartIconContentDescription(paramTintTypedArray.getText(R.styleable.TextInputLayout_startIconContentDescription));
      }
      setStartIconCheckable(paramTintTypedArray.getBoolean(R.styleable.TextInputLayout_startIconCheckable, true));
    }
  }
  
  private void updateVisibility()
  {
    CharSequence localCharSequence = this.prefixText;
    int k = 8;
    int i;
    if ((localCharSequence != null) && (!this.hintExpanded)) {
      i = 0;
    } else {
      i = 8;
    }
    int j;
    if ((this.startIconView.getVisibility() != 0) && (i != 0)) {
      j = 0;
    } else {
      j = 1;
    }
    if (j != 0) {
      k = 0;
    }
    setVisibility(k);
    this.prefixTextView.setVisibility(i);
    this.textInputLayout.updateDummyDrawables();
  }
  
  CharSequence getPrefixText()
  {
    return this.prefixText;
  }
  
  ColorStateList getPrefixTextColor()
  {
    return this.prefixTextView.getTextColors();
  }
  
  TextView getPrefixTextView()
  {
    return this.prefixTextView;
  }
  
  CharSequence getStartIconContentDescription()
  {
    return this.startIconView.getContentDescription();
  }
  
  Drawable getStartIconDrawable()
  {
    return this.startIconView.getDrawable();
  }
  
  boolean isStartIconCheckable()
  {
    return this.startIconView.isCheckable();
  }
  
  boolean isStartIconVisible()
  {
    boolean bool;
    if (this.startIconView.getVisibility() == 0) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  void onHintStateChanged(boolean paramBoolean)
  {
    this.hintExpanded = paramBoolean;
    updateVisibility();
  }
  
  protected void onMeasure(int paramInt1, int paramInt2)
  {
    super.onMeasure(paramInt1, paramInt2);
    updatePrefixTextViewPadding();
  }
  
  void refreshStartIconDrawableState()
  {
    IconHelper.refreshIconDrawableState(this.textInputLayout, this.startIconView, this.startIconTintList);
  }
  
  void setPrefixText(CharSequence paramCharSequence)
  {
    CharSequence localCharSequence;
    if (TextUtils.isEmpty(paramCharSequence)) {
      localCharSequence = null;
    } else {
      localCharSequence = paramCharSequence;
    }
    this.prefixText = localCharSequence;
    this.prefixTextView.setText(paramCharSequence);
    updateVisibility();
  }
  
  void setPrefixTextAppearance(int paramInt)
  {
    TextViewCompat.setTextAppearance(this.prefixTextView, paramInt);
  }
  
  void setPrefixTextColor(ColorStateList paramColorStateList)
  {
    this.prefixTextView.setTextColor(paramColorStateList);
  }
  
  void setStartIconCheckable(boolean paramBoolean)
  {
    this.startIconView.setCheckable(paramBoolean);
  }
  
  void setStartIconContentDescription(CharSequence paramCharSequence)
  {
    if (getStartIconContentDescription() != paramCharSequence) {
      this.startIconView.setContentDescription(paramCharSequence);
    }
  }
  
  void setStartIconDrawable(Drawable paramDrawable)
  {
    this.startIconView.setImageDrawable(paramDrawable);
    if (paramDrawable != null)
    {
      IconHelper.applyIconTint(this.textInputLayout, this.startIconView, this.startIconTintList, this.startIconTintMode);
      setStartIconVisible(true);
      refreshStartIconDrawableState();
    }
    else
    {
      setStartIconVisible(false);
      setStartIconOnClickListener(null);
      setStartIconOnLongClickListener(null);
      setStartIconContentDescription(null);
    }
  }
  
  void setStartIconOnClickListener(View.OnClickListener paramOnClickListener)
  {
    IconHelper.setIconOnClickListener(this.startIconView, paramOnClickListener, this.startIconOnLongClickListener);
  }
  
  void setStartIconOnLongClickListener(View.OnLongClickListener paramOnLongClickListener)
  {
    this.startIconOnLongClickListener = paramOnLongClickListener;
    IconHelper.setIconOnLongClickListener(this.startIconView, paramOnLongClickListener);
  }
  
  void setStartIconTintList(ColorStateList paramColorStateList)
  {
    if (this.startIconTintList != paramColorStateList)
    {
      this.startIconTintList = paramColorStateList;
      IconHelper.applyIconTint(this.textInputLayout, this.startIconView, paramColorStateList, this.startIconTintMode);
    }
  }
  
  void setStartIconTintMode(PorterDuff.Mode paramMode)
  {
    if (this.startIconTintMode != paramMode)
    {
      this.startIconTintMode = paramMode;
      IconHelper.applyIconTint(this.textInputLayout, this.startIconView, this.startIconTintList, paramMode);
    }
  }
  
  void setStartIconVisible(boolean paramBoolean)
  {
    if (isStartIconVisible() != paramBoolean)
    {
      CheckableImageButton localCheckableImageButton = this.startIconView;
      int i;
      if (paramBoolean) {
        i = 0;
      } else {
        i = 8;
      }
      localCheckableImageButton.setVisibility(i);
      updatePrefixTextViewPadding();
      updateVisibility();
    }
  }
  
  void setupAccessibilityNodeInfo(AccessibilityNodeInfoCompat paramAccessibilityNodeInfoCompat)
  {
    if (this.prefixTextView.getVisibility() == 0)
    {
      paramAccessibilityNodeInfoCompat.setLabelFor(this.prefixTextView);
      paramAccessibilityNodeInfoCompat.setTraversalAfter(this.prefixTextView);
    }
    else
    {
      paramAccessibilityNodeInfoCompat.setTraversalAfter(this.startIconView);
    }
  }
  
  void updatePrefixTextViewPadding()
  {
    EditText localEditText = this.textInputLayout.editText;
    if (localEditText == null) {
      return;
    }
    int i;
    if (isStartIconVisible()) {
      i = 0;
    } else {
      i = ViewCompat.getPaddingStart(localEditText);
    }
    ViewCompat.setPaddingRelative(this.prefixTextView, i, localEditText.getCompoundPaddingTop(), getContext().getResources().getDimensionPixelSize(R.dimen.material_input_text_to_prefix_suffix_padding), localEditText.getCompoundPaddingBottom());
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/com/google/android/material/textfield/StartCompoundLayout.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */