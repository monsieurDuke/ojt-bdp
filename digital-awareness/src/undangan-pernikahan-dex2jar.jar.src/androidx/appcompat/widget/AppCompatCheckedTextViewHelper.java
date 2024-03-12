package androidx.appcompat.widget;

import android.content.res.ColorStateList;
import android.content.res.Resources.NotFoundException;
import android.graphics.PorterDuff.Mode;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.CheckedTextView;
import androidx.appcompat.R.styleable;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.core.view.ViewCompat;
import androidx.core.widget.CheckedTextViewCompat;

class AppCompatCheckedTextViewHelper
{
  private ColorStateList mCheckMarkTintList = null;
  private PorterDuff.Mode mCheckMarkTintMode = null;
  private boolean mHasCheckMarkTint = false;
  private boolean mHasCheckMarkTintMode = false;
  private boolean mSkipNextApply;
  private final CheckedTextView mView;
  
  AppCompatCheckedTextViewHelper(CheckedTextView paramCheckedTextView)
  {
    this.mView = paramCheckedTextView;
  }
  
  void applyCheckMarkTint()
  {
    Drawable localDrawable = CheckedTextViewCompat.getCheckMarkDrawable(this.mView);
    if ((localDrawable != null) && ((this.mHasCheckMarkTint) || (this.mHasCheckMarkTintMode)))
    {
      localDrawable = DrawableCompat.wrap(localDrawable).mutate();
      if (this.mHasCheckMarkTint) {
        DrawableCompat.setTintList(localDrawable, this.mCheckMarkTintList);
      }
      if (this.mHasCheckMarkTintMode) {
        DrawableCompat.setTintMode(localDrawable, this.mCheckMarkTintMode);
      }
      if (localDrawable.isStateful()) {
        localDrawable.setState(this.mView.getDrawableState());
      }
      this.mView.setCheckMarkDrawable(localDrawable);
    }
  }
  
  ColorStateList getSupportCheckMarkTintList()
  {
    return this.mCheckMarkTintList;
  }
  
  PorterDuff.Mode getSupportCheckMarkTintMode()
  {
    return this.mCheckMarkTintMode;
  }
  
  void loadFromAttributes(AttributeSet paramAttributeSet, int paramInt)
  {
    TintTypedArray localTintTypedArray = TintTypedArray.obtainStyledAttributes(this.mView.getContext(), paramAttributeSet, R.styleable.CheckedTextView, paramInt, 0);
    CheckedTextView localCheckedTextView = this.mView;
    ViewCompat.saveAttributeDataForStyleable(localCheckedTextView, localCheckedTextView.getContext(), R.styleable.CheckedTextView, paramAttributeSet, localTintTypedArray.getWrappedTypeArray(), paramInt, 0);
    int i = 0;
    paramInt = i;
    try
    {
      if (localTintTypedArray.hasValue(R.styleable.CheckedTextView_checkMarkCompat))
      {
        int j = localTintTypedArray.getResourceId(R.styleable.CheckedTextView_checkMarkCompat, 0);
        paramInt = i;
        if (j != 0) {
          try
          {
            paramAttributeSet = this.mView;
            paramAttributeSet.setCheckMarkDrawable(AppCompatResources.getDrawable(paramAttributeSet.getContext(), j));
            paramInt = 1;
          }
          catch (Resources.NotFoundException paramAttributeSet)
          {
            paramInt = i;
          }
        }
      }
      if ((paramInt == 0) && (localTintTypedArray.hasValue(R.styleable.CheckedTextView_android_checkMark)))
      {
        paramInt = localTintTypedArray.getResourceId(R.styleable.CheckedTextView_android_checkMark, 0);
        if (paramInt != 0)
        {
          paramAttributeSet = this.mView;
          paramAttributeSet.setCheckMarkDrawable(AppCompatResources.getDrawable(paramAttributeSet.getContext(), paramInt));
        }
      }
      if (localTintTypedArray.hasValue(R.styleable.CheckedTextView_checkMarkTint)) {
        CheckedTextViewCompat.setCheckMarkTintList(this.mView, localTintTypedArray.getColorStateList(R.styleable.CheckedTextView_checkMarkTint));
      }
      if (localTintTypedArray.hasValue(R.styleable.CheckedTextView_checkMarkTintMode)) {
        CheckedTextViewCompat.setCheckMarkTintMode(this.mView, DrawableUtils.parseTintMode(localTintTypedArray.getInt(R.styleable.CheckedTextView_checkMarkTintMode, -1), null));
      }
      return;
    }
    finally
    {
      localTintTypedArray.recycle();
    }
  }
  
  void onSetCheckMarkDrawable()
  {
    if (this.mSkipNextApply)
    {
      this.mSkipNextApply = false;
      return;
    }
    this.mSkipNextApply = true;
    applyCheckMarkTint();
  }
  
  void setSupportCheckMarkTintList(ColorStateList paramColorStateList)
  {
    this.mCheckMarkTintList = paramColorStateList;
    this.mHasCheckMarkTint = true;
    applyCheckMarkTint();
  }
  
  void setSupportCheckMarkTintMode(PorterDuff.Mode paramMode)
  {
    this.mCheckMarkTintMode = paramMode;
    this.mHasCheckMarkTintMode = true;
    applyCheckMarkTint();
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/appcompat/widget/AppCompatCheckedTextViewHelper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */