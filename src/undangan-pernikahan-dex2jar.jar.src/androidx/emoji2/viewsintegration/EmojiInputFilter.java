package androidx.emoji2.viewsintegration;

import android.text.InputFilter;
import android.text.Selection;
import android.text.Spannable;
import android.text.Spanned;
import android.widget.TextView;
import androidx.emoji2.text.EmojiCompat;
import androidx.emoji2.text.EmojiCompat.InitCallback;
import java.lang.ref.Reference;
import java.lang.ref.WeakReference;

final class EmojiInputFilter
  implements InputFilter
{
  private EmojiCompat.InitCallback mInitCallback;
  private final TextView mTextView;
  
  EmojiInputFilter(TextView paramTextView)
  {
    this.mTextView = paramTextView;
  }
  
  private EmojiCompat.InitCallback getInitCallback()
  {
    if (this.mInitCallback == null) {
      this.mInitCallback = new InitCallbackImpl(this.mTextView, this);
    }
    return this.mInitCallback;
  }
  
  static void updateSelection(Spannable paramSpannable, int paramInt1, int paramInt2)
  {
    if ((paramInt1 >= 0) && (paramInt2 >= 0)) {
      Selection.setSelection(paramSpannable, paramInt1, paramInt2);
    } else if (paramInt1 >= 0) {
      Selection.setSelection(paramSpannable, paramInt1);
    } else if (paramInt2 >= 0) {
      Selection.setSelection(paramSpannable, paramInt2);
    }
  }
  
  public CharSequence filter(CharSequence paramCharSequence, int paramInt1, int paramInt2, Spanned paramSpanned, int paramInt3, int paramInt4)
  {
    if (this.mTextView.isInEditMode()) {
      return paramCharSequence;
    }
    switch (EmojiCompat.get().getLoadState())
    {
    case 2: 
    default: 
      return paramCharSequence;
    case 1: 
      int j = 1;
      int i = j;
      if (paramInt4 == 0)
      {
        i = j;
        if (paramInt3 == 0)
        {
          i = j;
          if (paramSpanned.length() == 0)
          {
            i = j;
            if (paramCharSequence == this.mTextView.getText()) {
              i = 0;
            }
          }
        }
      }
      if ((i != 0) && (paramCharSequence != null))
      {
        if ((paramInt1 != 0) || (paramInt2 != paramCharSequence.length())) {
          paramCharSequence = paramCharSequence.subSequence(paramInt1, paramInt2);
        }
        return EmojiCompat.get().process(paramCharSequence, 0, paramCharSequence.length());
      }
      return paramCharSequence;
    }
    EmojiCompat.get().registerInitCallback(getInitCallback());
    return paramCharSequence;
  }
  
  private static class InitCallbackImpl
    extends EmojiCompat.InitCallback
  {
    private final Reference<EmojiInputFilter> mEmojiInputFilterReference;
    private final Reference<TextView> mViewRef;
    
    InitCallbackImpl(TextView paramTextView, EmojiInputFilter paramEmojiInputFilter)
    {
      this.mViewRef = new WeakReference(paramTextView);
      this.mEmojiInputFilterReference = new WeakReference(paramEmojiInputFilter);
    }
    
    private boolean isInputFilterCurrentlyRegisteredOnTextView(TextView paramTextView, InputFilter paramInputFilter)
    {
      if ((paramInputFilter != null) && (paramTextView != null))
      {
        paramTextView = paramTextView.getFilters();
        if (paramTextView == null) {
          return false;
        }
        for (int i = 0; i < paramTextView.length; i++) {
          if (paramTextView[i] == paramInputFilter) {
            return true;
          }
        }
        return false;
      }
      return false;
    }
    
    public void onInitialized()
    {
      super.onInitialized();
      TextView localTextView = (TextView)this.mViewRef.get();
      if (!isInputFilterCurrentlyRegisteredOnTextView(localTextView, (InputFilter)this.mEmojiInputFilterReference.get())) {
        return;
      }
      if (localTextView.isAttachedToWindow())
      {
        CharSequence localCharSequence1 = localTextView.getText();
        CharSequence localCharSequence2 = EmojiCompat.get().process(localCharSequence1);
        if (localCharSequence1 == localCharSequence2) {
          return;
        }
        int j = Selection.getSelectionStart(localCharSequence2);
        int i = Selection.getSelectionEnd(localCharSequence2);
        localTextView.setText(localCharSequence2);
        if ((localCharSequence2 instanceof Spannable)) {
          EmojiInputFilter.updateSelection((Spannable)localCharSequence2, j, i);
        }
      }
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/emoji2/viewsintegration/EmojiInputFilter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */