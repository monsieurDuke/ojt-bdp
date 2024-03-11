package androidx.emoji2.viewsintegration;

import android.os.Build.VERSION;
import android.text.method.KeyListener;
import android.text.method.NumberKeyListener;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.widget.EditText;
import androidx.core.util.Preconditions;

public final class EmojiEditTextHelper
{
  private int mEmojiReplaceStrategy = 0;
  private final HelperInternal mHelper;
  private int mMaxEmojiCount = Integer.MAX_VALUE;
  
  public EmojiEditTextHelper(EditText paramEditText)
  {
    this(paramEditText, true);
  }
  
  public EmojiEditTextHelper(EditText paramEditText, boolean paramBoolean)
  {
    Preconditions.checkNotNull(paramEditText, "editText cannot be null");
    if (Build.VERSION.SDK_INT < 19) {
      this.mHelper = new HelperInternal();
    } else {
      this.mHelper = new HelperInternal19(paramEditText, paramBoolean);
    }
  }
  
  public int getEmojiReplaceStrategy()
  {
    return this.mEmojiReplaceStrategy;
  }
  
  public KeyListener getKeyListener(KeyListener paramKeyListener)
  {
    return this.mHelper.getKeyListener(paramKeyListener);
  }
  
  public int getMaxEmojiCount()
  {
    return this.mMaxEmojiCount;
  }
  
  public boolean isEnabled()
  {
    return this.mHelper.isEnabled();
  }
  
  public InputConnection onCreateInputConnection(InputConnection paramInputConnection, EditorInfo paramEditorInfo)
  {
    if (paramInputConnection == null) {
      return null;
    }
    return this.mHelper.onCreateInputConnection(paramInputConnection, paramEditorInfo);
  }
  
  public void setEmojiReplaceStrategy(int paramInt)
  {
    this.mEmojiReplaceStrategy = paramInt;
    this.mHelper.setEmojiReplaceStrategy(paramInt);
  }
  
  public void setEnabled(boolean paramBoolean)
  {
    this.mHelper.setEnabled(paramBoolean);
  }
  
  public void setMaxEmojiCount(int paramInt)
  {
    Preconditions.checkArgumentNonnegative(paramInt, "maxEmojiCount should be greater than 0");
    this.mMaxEmojiCount = paramInt;
    this.mHelper.setMaxEmojiCount(paramInt);
  }
  
  static class HelperInternal
  {
    KeyListener getKeyListener(KeyListener paramKeyListener)
    {
      return paramKeyListener;
    }
    
    boolean isEnabled()
    {
      return false;
    }
    
    InputConnection onCreateInputConnection(InputConnection paramInputConnection, EditorInfo paramEditorInfo)
    {
      return paramInputConnection;
    }
    
    void setEmojiReplaceStrategy(int paramInt) {}
    
    void setEnabled(boolean paramBoolean) {}
    
    void setMaxEmojiCount(int paramInt) {}
  }
  
  private static class HelperInternal19
    extends EmojiEditTextHelper.HelperInternal
  {
    private final EditText mEditText;
    private final EmojiTextWatcher mTextWatcher;
    
    HelperInternal19(EditText paramEditText, boolean paramBoolean)
    {
      this.mEditText = paramEditText;
      EmojiTextWatcher localEmojiTextWatcher = new EmojiTextWatcher(paramEditText, paramBoolean);
      this.mTextWatcher = localEmojiTextWatcher;
      paramEditText.addTextChangedListener(localEmojiTextWatcher);
      paramEditText.setEditableFactory(EmojiEditableFactory.getInstance());
    }
    
    KeyListener getKeyListener(KeyListener paramKeyListener)
    {
      if ((paramKeyListener instanceof EmojiKeyListener)) {
        return paramKeyListener;
      }
      if (paramKeyListener == null) {
        return null;
      }
      if ((paramKeyListener instanceof NumberKeyListener)) {
        return paramKeyListener;
      }
      return new EmojiKeyListener(paramKeyListener);
    }
    
    boolean isEnabled()
    {
      return this.mTextWatcher.isEnabled();
    }
    
    InputConnection onCreateInputConnection(InputConnection paramInputConnection, EditorInfo paramEditorInfo)
    {
      if ((paramInputConnection instanceof EmojiInputConnection)) {
        return paramInputConnection;
      }
      return new EmojiInputConnection(this.mEditText, paramInputConnection, paramEditorInfo);
    }
    
    void setEmojiReplaceStrategy(int paramInt)
    {
      this.mTextWatcher.setEmojiReplaceStrategy(paramInt);
    }
    
    void setEnabled(boolean paramBoolean)
    {
      this.mTextWatcher.setEnabled(paramBoolean);
    }
    
    void setMaxEmojiCount(int paramInt)
    {
      this.mTextWatcher.setMaxEmojiCount(paramInt);
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/emoji2/viewsintegration/EmojiEditTextHelper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */