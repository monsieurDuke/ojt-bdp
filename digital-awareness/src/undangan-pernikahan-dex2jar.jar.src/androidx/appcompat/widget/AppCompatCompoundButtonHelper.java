package androidx.appcompat.widget;

import android.content.res.ColorStateList;
import android.content.res.Resources.NotFoundException;
import android.graphics.PorterDuff.Mode;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.util.AttributeSet;
import android.widget.CompoundButton;
import androidx.appcompat.R.styleable;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.core.view.ViewCompat;
import androidx.core.widget.CompoundButtonCompat;

class AppCompatCompoundButtonHelper
{
  private ColorStateList mButtonTintList = null;
  private PorterDuff.Mode mButtonTintMode = null;
  private boolean mHasButtonTint = false;
  private boolean mHasButtonTintMode = false;
  private boolean mSkipNextApply;
  private final CompoundButton mView;
  
  AppCompatCompoundButtonHelper(CompoundButton paramCompoundButton)
  {
    this.mView = paramCompoundButton;
  }
  
  void applyButtonTint()
  {
    Drawable localDrawable = CompoundButtonCompat.getButtonDrawable(this.mView);
    if ((localDrawable != null) && ((this.mHasButtonTint) || (this.mHasButtonTintMode)))
    {
      localDrawable = DrawableCompat.wrap(localDrawable).mutate();
      if (this.mHasButtonTint) {
        DrawableCompat.setTintList(localDrawable, this.mButtonTintList);
      }
      if (this.mHasButtonTintMode) {
        DrawableCompat.setTintMode(localDrawable, this.mButtonTintMode);
      }
      if (localDrawable.isStateful()) {
        localDrawable.setState(this.mView.getDrawableState());
      }
      this.mView.setButtonDrawable(localDrawable);
    }
  }
  
  int getCompoundPaddingLeft(int paramInt)
  {
    int i = paramInt;
    if (Build.VERSION.SDK_INT < 17)
    {
      Drawable localDrawable = CompoundButtonCompat.getButtonDrawable(this.mView);
      i = paramInt;
      if (localDrawable != null) {
        i = paramInt + localDrawable.getIntrinsicWidth();
      }
    }
    return i;
  }
  
  ColorStateList getSupportButtonTintList()
  {
    return this.mButtonTintList;
  }
  
  PorterDuff.Mode getSupportButtonTintMode()
  {
    return this.mButtonTintMode;
  }
  
  void loadFromAttributes(AttributeSet paramAttributeSet, int paramInt)
  {
    TintTypedArray localTintTypedArray = TintTypedArray.obtainStyledAttributes(this.mView.getContext(), paramAttributeSet, R.styleable.CompoundButton, paramInt, 0);
    CompoundButton localCompoundButton = this.mView;
    ViewCompat.saveAttributeDataForStyleable(localCompoundButton, localCompoundButton.getContext(), R.styleable.CompoundButton, paramAttributeSet, localTintTypedArray.getWrappedTypeArray(), paramInt, 0);
    int i = 0;
    paramInt = i;
    try
    {
      if (localTintTypedArray.hasValue(R.styleable.CompoundButton_buttonCompat))
      {
        int j = localTintTypedArray.getResourceId(R.styleable.CompoundButton_buttonCompat, 0);
        paramInt = i;
        if (j != 0) {
          try
          {
            paramAttributeSet = this.mView;
            paramAttributeSet.setButtonDrawable(AppCompatResources.getDrawable(paramAttributeSet.getContext(), j));
            paramInt = 1;
          }
          catch (Resources.NotFoundException paramAttributeSet)
          {
            paramInt = i;
          }
        }
      }
      if ((paramInt == 0) && (localTintTypedArray.hasValue(R.styleable.CompoundButton_android_button)))
      {
        paramInt = localTintTypedArray.getResourceId(R.styleable.CompoundButton_android_button, 0);
        if (paramInt != 0)
        {
          paramAttributeSet = this.mView;
          paramAttributeSet.setButtonDrawable(AppCompatResources.getDrawable(paramAttributeSet.getContext(), paramInt));
        }
      }
      if (localTintTypedArray.hasValue(R.styleable.CompoundButton_buttonTint)) {
        CompoundButtonCompat.setButtonTintList(this.mView, localTintTypedArray.getColorStateList(R.styleable.CompoundButton_buttonTint));
      }
      if (localTintTypedArray.hasValue(R.styleable.CompoundButton_buttonTintMode)) {
        CompoundButtonCompat.setButtonTintMode(this.mView, DrawableUtils.parseTintMode(localTintTypedArray.getInt(R.styleable.CompoundButton_buttonTintMode, -1), null));
      }
      return;
    }
    finally
    {
      localTintTypedArray.recycle();
    }
  }
  
  void onSetButtonDrawable()
  {
    if (this.mSkipNextApply)
    {
      this.mSkipNextApply = false;
      return;
    }
    this.mSkipNextApply = true;
    applyButtonTint();
  }
  
  void setSupportButtonTintList(ColorStateList paramColorStateList)
  {
    this.mButtonTintList = paramColorStateList;
    this.mHasButtonTint = true;
    applyButtonTint();
  }
  
  void setSupportButtonTintMode(PorterDuff.Mode paramMode)
  {
    this.mButtonTintMode = paramMode;
    this.mHasButtonTintMode = true;
    applyButtonTint();
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/appcompat/widget/AppCompatCompoundButtonHelper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */