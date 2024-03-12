package androidx.emoji2.viewsintegration;

import android.text.Editable;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.view.inputmethod.InputConnectionWrapper;
import android.widget.TextView;
import androidx.emoji2.text.EmojiCompat;

final class EmojiInputConnection
  extends InputConnectionWrapper
{
  private final EmojiCompatDeleteHelper mEmojiCompatDeleteHelper;
  private final TextView mTextView;
  
  EmojiInputConnection(TextView paramTextView, InputConnection paramInputConnection, EditorInfo paramEditorInfo)
  {
    this(paramTextView, paramInputConnection, paramEditorInfo, new EmojiCompatDeleteHelper());
  }
  
  EmojiInputConnection(TextView paramTextView, InputConnection paramInputConnection, EditorInfo paramEditorInfo, EmojiCompatDeleteHelper paramEmojiCompatDeleteHelper)
  {
    super(paramInputConnection, false);
    this.mTextView = paramTextView;
    this.mEmojiCompatDeleteHelper = paramEmojiCompatDeleteHelper;
    paramEmojiCompatDeleteHelper.updateEditorInfoAttrs(paramEditorInfo);
  }
  
  private Editable getEditable()
  {
    return this.mTextView.getEditableText();
  }
  
  public boolean deleteSurroundingText(int paramInt1, int paramInt2)
  {
    boolean bool;
    if ((!this.mEmojiCompatDeleteHelper.handleDeleteSurroundingText(this, getEditable(), paramInt1, paramInt2, false)) && (!super.deleteSurroundingText(paramInt1, paramInt2))) {
      bool = false;
    } else {
      bool = true;
    }
    return bool;
  }
  
  public boolean deleteSurroundingTextInCodePoints(int paramInt1, int paramInt2)
  {
    boolean bool;
    if ((!this.mEmojiCompatDeleteHelper.handleDeleteSurroundingText(this, getEditable(), paramInt1, paramInt2, true)) && (!super.deleteSurroundingTextInCodePoints(paramInt1, paramInt2))) {
      bool = false;
    } else {
      bool = true;
    }
    return bool;
  }
  
  public static class EmojiCompatDeleteHelper
  {
    public boolean handleDeleteSurroundingText(InputConnection paramInputConnection, Editable paramEditable, int paramInt1, int paramInt2, boolean paramBoolean)
    {
      return EmojiCompat.handleDeleteSurroundingText(paramInputConnection, paramEditable, paramInt1, paramInt2, paramBoolean);
    }
    
    public void updateEditorInfoAttrs(EditorInfo paramEditorInfo)
    {
      if (EmojiCompat.isConfigured()) {
        EmojiCompat.get().updateEditorInfo(paramEditorInfo);
      }
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/emoji2/viewsintegration/EmojiInputConnection.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */