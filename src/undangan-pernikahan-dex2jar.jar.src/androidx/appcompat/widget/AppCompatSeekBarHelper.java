package androidx.appcompat.widget;

import android.content.res.ColorStateList;
import android.graphics.Canvas;
import android.graphics.PorterDuff.Mode;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.SeekBar;
import androidx.appcompat.R.styleable;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.core.view.ViewCompat;

class AppCompatSeekBarHelper
  extends AppCompatProgressBarHelper
{
  private boolean mHasTickMarkTint = false;
  private boolean mHasTickMarkTintMode = false;
  private Drawable mTickMark;
  private ColorStateList mTickMarkTintList = null;
  private PorterDuff.Mode mTickMarkTintMode = null;
  private final SeekBar mView;
  
  AppCompatSeekBarHelper(SeekBar paramSeekBar)
  {
    super(paramSeekBar);
    this.mView = paramSeekBar;
  }
  
  private void applyTickMarkTint()
  {
    Drawable localDrawable = this.mTickMark;
    if ((localDrawable != null) && ((this.mHasTickMarkTint) || (this.mHasTickMarkTintMode)))
    {
      localDrawable = DrawableCompat.wrap(localDrawable.mutate());
      this.mTickMark = localDrawable;
      if (this.mHasTickMarkTint) {
        DrawableCompat.setTintList(localDrawable, this.mTickMarkTintList);
      }
      if (this.mHasTickMarkTintMode) {
        DrawableCompat.setTintMode(this.mTickMark, this.mTickMarkTintMode);
      }
      if (this.mTickMark.isStateful()) {
        this.mTickMark.setState(this.mView.getDrawableState());
      }
    }
  }
  
  void drawTickMarks(Canvas paramCanvas)
  {
    if (this.mTickMark != null)
    {
      int k = this.mView.getMax();
      int j = 1;
      if (k > 1)
      {
        int i = this.mTickMark.getIntrinsicWidth();
        int m = this.mTickMark.getIntrinsicHeight();
        if (i >= 0) {
          i /= 2;
        } else {
          i = 1;
        }
        if (m >= 0) {
          j = m / 2;
        }
        this.mTickMark.setBounds(-i, -j, i, j);
        float f = (this.mView.getWidth() - this.mView.getPaddingLeft() - this.mView.getPaddingRight()) / k;
        j = paramCanvas.save();
        paramCanvas.translate(this.mView.getPaddingLeft(), this.mView.getHeight() / 2);
        for (i = 0; i <= k; i++)
        {
          this.mTickMark.draw(paramCanvas);
          paramCanvas.translate(f, 0.0F);
        }
        paramCanvas.restoreToCount(j);
      }
    }
  }
  
  void drawableStateChanged()
  {
    Drawable localDrawable = this.mTickMark;
    if ((localDrawable != null) && (localDrawable.isStateful()) && (localDrawable.setState(this.mView.getDrawableState()))) {
      this.mView.invalidateDrawable(localDrawable);
    }
  }
  
  Drawable getTickMark()
  {
    return this.mTickMark;
  }
  
  ColorStateList getTickMarkTintList()
  {
    return this.mTickMarkTintList;
  }
  
  PorterDuff.Mode getTickMarkTintMode()
  {
    return this.mTickMarkTintMode;
  }
  
  void jumpDrawablesToCurrentState()
  {
    Drawable localDrawable = this.mTickMark;
    if (localDrawable != null) {
      localDrawable.jumpToCurrentState();
    }
  }
  
  void loadFromAttributes(AttributeSet paramAttributeSet, int paramInt)
  {
    super.loadFromAttributes(paramAttributeSet, paramInt);
    TintTypedArray localTintTypedArray = TintTypedArray.obtainStyledAttributes(this.mView.getContext(), paramAttributeSet, R.styleable.AppCompatSeekBar, paramInt, 0);
    SeekBar localSeekBar = this.mView;
    ViewCompat.saveAttributeDataForStyleable(localSeekBar, localSeekBar.getContext(), R.styleable.AppCompatSeekBar, paramAttributeSet, localTintTypedArray.getWrappedTypeArray(), paramInt, 0);
    paramAttributeSet = localTintTypedArray.getDrawableIfKnown(R.styleable.AppCompatSeekBar_android_thumb);
    if (paramAttributeSet != null) {
      this.mView.setThumb(paramAttributeSet);
    }
    setTickMark(localTintTypedArray.getDrawable(R.styleable.AppCompatSeekBar_tickMark));
    if (localTintTypedArray.hasValue(R.styleable.AppCompatSeekBar_tickMarkTintMode))
    {
      this.mTickMarkTintMode = DrawableUtils.parseTintMode(localTintTypedArray.getInt(R.styleable.AppCompatSeekBar_tickMarkTintMode, -1), this.mTickMarkTintMode);
      this.mHasTickMarkTintMode = true;
    }
    if (localTintTypedArray.hasValue(R.styleable.AppCompatSeekBar_tickMarkTint))
    {
      this.mTickMarkTintList = localTintTypedArray.getColorStateList(R.styleable.AppCompatSeekBar_tickMarkTint);
      this.mHasTickMarkTint = true;
    }
    localTintTypedArray.recycle();
    applyTickMarkTint();
  }
  
  void setTickMark(Drawable paramDrawable)
  {
    Drawable localDrawable = this.mTickMark;
    if (localDrawable != null) {
      localDrawable.setCallback(null);
    }
    this.mTickMark = paramDrawable;
    if (paramDrawable != null)
    {
      paramDrawable.setCallback(this.mView);
      DrawableCompat.setLayoutDirection(paramDrawable, ViewCompat.getLayoutDirection(this.mView));
      if (paramDrawable.isStateful()) {
        paramDrawable.setState(this.mView.getDrawableState());
      }
      applyTickMarkTint();
    }
    this.mView.invalidate();
  }
  
  void setTickMarkTintList(ColorStateList paramColorStateList)
  {
    this.mTickMarkTintList = paramColorStateList;
    this.mHasTickMarkTint = true;
    applyTickMarkTint();
  }
  
  void setTickMarkTintMode(PorterDuff.Mode paramMode)
  {
    this.mTickMarkTintMode = paramMode;
    this.mHasTickMarkTintMode = true;
    applyTickMarkTint();
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/appcompat/widget/AppCompatSeekBarHelper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */