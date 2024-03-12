package androidx.emoji2.viewsintegration;

import android.text.Editable;
import android.text.method.KeyListener;
import android.view.KeyEvent;
import android.view.View;
import androidx.emoji2.text.EmojiCompat;

final class EmojiKeyListener
  implements KeyListener
{
  private final EmojiCompatHandleKeyDownHelper mEmojiCompatHandleKeyDownHelper;
  private final KeyListener mKeyListener;
  
  EmojiKeyListener(KeyListener paramKeyListener)
  {
    this(paramKeyListener, new EmojiCompatHandleKeyDownHelper());
  }
  
  EmojiKeyListener(KeyListener paramKeyListener, EmojiCompatHandleKeyDownHelper paramEmojiCompatHandleKeyDownHelper)
  {
    this.mKeyListener = paramKeyListener;
    this.mEmojiCompatHandleKeyDownHelper = paramEmojiCompatHandleKeyDownHelper;
  }
  
  public void clearMetaKeyState(View paramView, Editable paramEditable, int paramInt)
  {
    this.mKeyListener.clearMetaKeyState(paramView, paramEditable, paramInt);
  }
  
  public int getInputType()
  {
    return this.mKeyListener.getInputType();
  }
  
  public boolean onKeyDown(View paramView, Editable paramEditable, int paramInt, KeyEvent paramKeyEvent)
  {
    boolean bool;
    if ((!this.mEmojiCompatHandleKeyDownHelper.handleKeyDown(paramEditable, paramInt, paramKeyEvent)) && (!this.mKeyListener.onKeyDown(paramView, paramEditable, paramInt, paramKeyEvent))) {
      bool = false;
    } else {
      bool = true;
    }
    return bool;
  }
  
  public boolean onKeyOther(View paramView, Editable paramEditable, KeyEvent paramKeyEvent)
  {
    return this.mKeyListener.onKeyOther(paramView, paramEditable, paramKeyEvent);
  }
  
  public boolean onKeyUp(View paramView, Editable paramEditable, int paramInt, KeyEvent paramKeyEvent)
  {
    return this.mKeyListener.onKeyUp(paramView, paramEditable, paramInt, paramKeyEvent);
  }
  
  public static class EmojiCompatHandleKeyDownHelper
  {
    public boolean handleKeyDown(Editable paramEditable, int paramInt, KeyEvent paramKeyEvent)
    {
      return EmojiCompat.handleOnKeyDown(paramEditable, paramInt, paramKeyEvent);
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/emoji2/viewsintegration/EmojiKeyListener.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */