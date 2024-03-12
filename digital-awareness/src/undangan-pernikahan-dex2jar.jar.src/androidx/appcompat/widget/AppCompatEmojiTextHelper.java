package androidx.appcompat.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.InputFilter;
import android.text.method.TransformationMethod;
import android.util.AttributeSet;
import android.widget.TextView;
import androidx.appcompat.R.styleable;
import androidx.emoji2.viewsintegration.EmojiTextViewHelper;

class AppCompatEmojiTextHelper
{
  private final EmojiTextViewHelper mEmojiTextViewHelper;
  private final TextView mView;
  
  AppCompatEmojiTextHelper(TextView paramTextView)
  {
    this.mView = paramTextView;
    this.mEmojiTextViewHelper = new EmojiTextViewHelper(paramTextView, false);
  }
  
  InputFilter[] getFilters(InputFilter[] paramArrayOfInputFilter)
  {
    return this.mEmojiTextViewHelper.getFilters(paramArrayOfInputFilter);
  }
  
  public boolean isEnabled()
  {
    return this.mEmojiTextViewHelper.isEnabled();
  }
  
  void loadFromAttributes(AttributeSet paramAttributeSet, int paramInt)
  {
    paramAttributeSet = this.mView.getContext().obtainStyledAttributes(paramAttributeSet, R.styleable.AppCompatTextView, paramInt, 0);
    boolean bool = true;
    try
    {
      if (paramAttributeSet.hasValue(R.styleable.AppCompatTextView_emojiCompatEnabled)) {
        bool = paramAttributeSet.getBoolean(R.styleable.AppCompatTextView_emojiCompatEnabled, true);
      }
      paramAttributeSet.recycle();
      setEnabled(bool);
      return;
    }
    finally
    {
      paramAttributeSet.recycle();
    }
  }
  
  void setAllCaps(boolean paramBoolean)
  {
    this.mEmojiTextViewHelper.setAllCaps(paramBoolean);
  }
  
  void setEnabled(boolean paramBoolean)
  {
    this.mEmojiTextViewHelper.setEnabled(paramBoolean);
  }
  
  public TransformationMethod wrapTransformationMethod(TransformationMethod paramTransformationMethod)
  {
    return this.mEmojiTextViewHelper.wrapTransformationMethod(paramTransformationMethod);
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/appcompat/widget/AppCompatEmojiTextHelper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */