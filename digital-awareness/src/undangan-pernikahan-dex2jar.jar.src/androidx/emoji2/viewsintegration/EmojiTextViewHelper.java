package androidx.emoji2.viewsintegration;

import android.os.Build.VERSION;
import android.text.InputFilter;
import android.text.method.PasswordTransformationMethod;
import android.text.method.TransformationMethod;
import android.util.SparseArray;
import android.widget.TextView;
import androidx.core.util.Preconditions;
import androidx.emoji2.text.EmojiCompat;

public final class EmojiTextViewHelper
{
  private final HelperInternal mHelper;
  
  public EmojiTextViewHelper(TextView paramTextView)
  {
    this(paramTextView, true);
  }
  
  public EmojiTextViewHelper(TextView paramTextView, boolean paramBoolean)
  {
    Preconditions.checkNotNull(paramTextView, "textView cannot be null");
    if (Build.VERSION.SDK_INT < 19) {
      this.mHelper = new HelperInternal();
    } else if (!paramBoolean) {
      this.mHelper = new SkippingHelper19(paramTextView);
    } else {
      this.mHelper = new HelperInternal19(paramTextView);
    }
  }
  
  public InputFilter[] getFilters(InputFilter[] paramArrayOfInputFilter)
  {
    return this.mHelper.getFilters(paramArrayOfInputFilter);
  }
  
  public boolean isEnabled()
  {
    return this.mHelper.isEnabled();
  }
  
  public void setAllCaps(boolean paramBoolean)
  {
    this.mHelper.setAllCaps(paramBoolean);
  }
  
  public void setEnabled(boolean paramBoolean)
  {
    this.mHelper.setEnabled(paramBoolean);
  }
  
  public void updateTransformationMethod()
  {
    this.mHelper.updateTransformationMethod();
  }
  
  public TransformationMethod wrapTransformationMethod(TransformationMethod paramTransformationMethod)
  {
    return this.mHelper.wrapTransformationMethod(paramTransformationMethod);
  }
  
  static class HelperInternal
  {
    InputFilter[] getFilters(InputFilter[] paramArrayOfInputFilter)
    {
      return paramArrayOfInputFilter;
    }
    
    public boolean isEnabled()
    {
      return false;
    }
    
    void setAllCaps(boolean paramBoolean) {}
    
    void setEnabled(boolean paramBoolean) {}
    
    void updateTransformationMethod() {}
    
    TransformationMethod wrapTransformationMethod(TransformationMethod paramTransformationMethod)
    {
      return paramTransformationMethod;
    }
  }
  
  private static class HelperInternal19
    extends EmojiTextViewHelper.HelperInternal
  {
    private final EmojiInputFilter mEmojiInputFilter;
    private boolean mEnabled;
    private final TextView mTextView;
    
    HelperInternal19(TextView paramTextView)
    {
      this.mTextView = paramTextView;
      this.mEnabled = true;
      this.mEmojiInputFilter = new EmojiInputFilter(paramTextView);
    }
    
    private InputFilter[] addEmojiInputFilterIfMissing(InputFilter[] paramArrayOfInputFilter)
    {
      int j = paramArrayOfInputFilter.length;
      for (int i = 0; i < j; i++) {
        if (paramArrayOfInputFilter[i] == this.mEmojiInputFilter) {
          return paramArrayOfInputFilter;
        }
      }
      InputFilter[] arrayOfInputFilter = new InputFilter[paramArrayOfInputFilter.length + 1];
      System.arraycopy(paramArrayOfInputFilter, 0, arrayOfInputFilter, 0, j);
      arrayOfInputFilter[j] = this.mEmojiInputFilter;
      return arrayOfInputFilter;
    }
    
    private SparseArray<InputFilter> getEmojiInputFilterPositionArray(InputFilter[] paramArrayOfInputFilter)
    {
      SparseArray localSparseArray = new SparseArray(1);
      for (int i = 0; i < paramArrayOfInputFilter.length; i++) {
        if ((paramArrayOfInputFilter[i] instanceof EmojiInputFilter)) {
          localSparseArray.put(i, paramArrayOfInputFilter[i]);
        }
      }
      return localSparseArray;
    }
    
    private InputFilter[] removeEmojiInputFilterIfPresent(InputFilter[] paramArrayOfInputFilter)
    {
      SparseArray localSparseArray = getEmojiInputFilterPositionArray(paramArrayOfInputFilter);
      if (localSparseArray.size() == 0) {
        return paramArrayOfInputFilter;
      }
      int m = paramArrayOfInputFilter.length;
      InputFilter[] arrayOfInputFilter = new InputFilter[paramArrayOfInputFilter.length - localSparseArray.size()];
      int j = 0;
      int i = 0;
      while (i < m)
      {
        int k = j;
        if (localSparseArray.indexOfKey(i) < 0)
        {
          arrayOfInputFilter[j] = paramArrayOfInputFilter[i];
          k = j + 1;
        }
        i++;
        j = k;
      }
      return arrayOfInputFilter;
    }
    
    private TransformationMethod unwrapForDisabled(TransformationMethod paramTransformationMethod)
    {
      if ((paramTransformationMethod instanceof EmojiTransformationMethod)) {
        return ((EmojiTransformationMethod)paramTransformationMethod).getOriginalTransformationMethod();
      }
      return paramTransformationMethod;
    }
    
    private void updateFilters()
    {
      InputFilter[] arrayOfInputFilter = this.mTextView.getFilters();
      this.mTextView.setFilters(getFilters(arrayOfInputFilter));
    }
    
    private TransformationMethod wrapForEnabled(TransformationMethod paramTransformationMethod)
    {
      if ((paramTransformationMethod instanceof EmojiTransformationMethod)) {
        return paramTransformationMethod;
      }
      if ((paramTransformationMethod instanceof PasswordTransformationMethod)) {
        return paramTransformationMethod;
      }
      return new EmojiTransformationMethod(paramTransformationMethod);
    }
    
    InputFilter[] getFilters(InputFilter[] paramArrayOfInputFilter)
    {
      if (!this.mEnabled) {
        return removeEmojiInputFilterIfPresent(paramArrayOfInputFilter);
      }
      return addEmojiInputFilterIfMissing(paramArrayOfInputFilter);
    }
    
    public boolean isEnabled()
    {
      return this.mEnabled;
    }
    
    void setAllCaps(boolean paramBoolean)
    {
      if (paramBoolean) {
        updateTransformationMethod();
      }
    }
    
    void setEnabled(boolean paramBoolean)
    {
      this.mEnabled = paramBoolean;
      updateTransformationMethod();
      updateFilters();
    }
    
    void setEnabledUnsafe(boolean paramBoolean)
    {
      this.mEnabled = paramBoolean;
    }
    
    void updateTransformationMethod()
    {
      TransformationMethod localTransformationMethod = wrapTransformationMethod(this.mTextView.getTransformationMethod());
      this.mTextView.setTransformationMethod(localTransformationMethod);
    }
    
    TransformationMethod wrapTransformationMethod(TransformationMethod paramTransformationMethod)
    {
      if (this.mEnabled) {
        return wrapForEnabled(paramTransformationMethod);
      }
      return unwrapForDisabled(paramTransformationMethod);
    }
  }
  
  private static class SkippingHelper19
    extends EmojiTextViewHelper.HelperInternal
  {
    private final EmojiTextViewHelper.HelperInternal19 mHelperDelegate;
    
    SkippingHelper19(TextView paramTextView)
    {
      this.mHelperDelegate = new EmojiTextViewHelper.HelperInternal19(paramTextView);
    }
    
    private boolean skipBecauseEmojiCompatNotInitialized()
    {
      return EmojiCompat.isConfigured() ^ true;
    }
    
    InputFilter[] getFilters(InputFilter[] paramArrayOfInputFilter)
    {
      if (skipBecauseEmojiCompatNotInitialized()) {
        return paramArrayOfInputFilter;
      }
      return this.mHelperDelegate.getFilters(paramArrayOfInputFilter);
    }
    
    public boolean isEnabled()
    {
      return this.mHelperDelegate.isEnabled();
    }
    
    void setAllCaps(boolean paramBoolean)
    {
      if (skipBecauseEmojiCompatNotInitialized()) {
        return;
      }
      this.mHelperDelegate.setAllCaps(paramBoolean);
    }
    
    void setEnabled(boolean paramBoolean)
    {
      if (skipBecauseEmojiCompatNotInitialized()) {
        this.mHelperDelegate.setEnabledUnsafe(paramBoolean);
      } else {
        this.mHelperDelegate.setEnabled(paramBoolean);
      }
    }
    
    void updateTransformationMethod()
    {
      if (skipBecauseEmojiCompatNotInitialized()) {
        return;
      }
      this.mHelperDelegate.updateTransformationMethod();
    }
    
    TransformationMethod wrapTransformationMethod(TransformationMethod paramTransformationMethod)
    {
      if (skipBecauseEmojiCompatNotInitialized()) {
        return paramTransformationMethod;
      }
      return this.mHelperDelegate.wrapTransformationMethod(paramTransformationMethod);
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/emoji2/viewsintegration/EmojiTextViewHelper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */