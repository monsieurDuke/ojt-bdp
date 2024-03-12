package androidx.appcompat.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.method.KeyListener;
import android.text.method.NumberKeyListener;
import android.util.AttributeSet;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.widget.EditText;
import androidx.appcompat.R.styleable;
import androidx.emoji2.viewsintegration.EmojiEditTextHelper;

class AppCompatEmojiEditTextHelper
{
  private final EmojiEditTextHelper mEmojiEditTextHelper;
  private final EditText mView;
  
  AppCompatEmojiEditTextHelper(EditText paramEditText)
  {
    this.mView = paramEditText;
    this.mEmojiEditTextHelper = new EmojiEditTextHelper(paramEditText, false);
  }
  
  KeyListener getKeyListener(KeyListener paramKeyListener)
  {
    if (isEmojiCapableKeyListener(paramKeyListener)) {
      return this.mEmojiEditTextHelper.getKeyListener(paramKeyListener);
    }
    return paramKeyListener;
  }
  
  boolean isEmojiCapableKeyListener(KeyListener paramKeyListener)
  {
    return paramKeyListener instanceof NumberKeyListener ^ true;
  }
  
  boolean isEnabled()
  {
    return this.mEmojiEditTextHelper.isEnabled();
  }
  
  void loadFromAttributes(AttributeSet paramAttributeSet, int paramInt)
  {
    TypedArray localTypedArray = this.mView.getContext().obtainStyledAttributes(paramAttributeSet, R.styleable.AppCompatTextView, paramInt, 0);
    boolean bool = true;
    try
    {
      if (localTypedArray.hasValue(R.styleable.AppCompatTextView_emojiCompatEnabled)) {
        bool = localTypedArray.getBoolean(R.styleable.AppCompatTextView_emojiCompatEnabled, true);
      }
      localTypedArray.recycle();
      setEnabled(bool);
      return;
    }
    finally
    {
      localTypedArray.recycle();
    }
  }
  
  InputConnection onCreateInputConnection(InputConnection paramInputConnection, EditorInfo paramEditorInfo)
  {
    return this.mEmojiEditTextHelper.onCreateInputConnection(paramInputConnection, paramEditorInfo);
  }
  
  void setEnabled(boolean paramBoolean)
  {
    this.mEmojiEditTextHelper.setEnabled(paramBoolean);
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/appcompat/widget/AppCompatEmojiEditTextHelper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */