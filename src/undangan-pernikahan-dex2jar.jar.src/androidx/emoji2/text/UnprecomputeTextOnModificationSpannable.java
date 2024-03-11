package androidx.emoji2.text;

import android.os.Build.VERSION;
import android.text.PrecomputedText;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import androidx.core.text.PrecomputedTextCompat;
import java.util.stream.IntStream;

class UnprecomputeTextOnModificationSpannable
  implements Spannable
{
  private Spannable mDelegate;
  private boolean mSafeToWrite = false;
  
  UnprecomputeTextOnModificationSpannable(Spannable paramSpannable)
  {
    this.mDelegate = paramSpannable;
  }
  
  UnprecomputeTextOnModificationSpannable(Spanned paramSpanned)
  {
    this.mDelegate = new SpannableString(paramSpanned);
  }
  
  UnprecomputeTextOnModificationSpannable(CharSequence paramCharSequence)
  {
    this.mDelegate = new SpannableString(paramCharSequence);
  }
  
  private void ensureSafeWrites()
  {
    Spannable localSpannable = this.mDelegate;
    if ((!this.mSafeToWrite) && (precomputedTextDetector().isPrecomputedText(localSpannable))) {
      this.mDelegate = new SpannableString(localSpannable);
    }
    this.mSafeToWrite = true;
  }
  
  static PrecomputedTextDetector precomputedTextDetector()
  {
    Object localObject;
    if (Build.VERSION.SDK_INT < 28) {
      localObject = new PrecomputedTextDetector();
    } else {
      localObject = new PrecomputedTextDetector_28();
    }
    return (PrecomputedTextDetector)localObject;
  }
  
  public char charAt(int paramInt)
  {
    return this.mDelegate.charAt(paramInt);
  }
  
  public IntStream chars()
  {
    return CharSequenceHelper_API24.chars(this.mDelegate);
  }
  
  public IntStream codePoints()
  {
    return CharSequenceHelper_API24.codePoints(this.mDelegate);
  }
  
  public int getSpanEnd(Object paramObject)
  {
    return this.mDelegate.getSpanEnd(paramObject);
  }
  
  public int getSpanFlags(Object paramObject)
  {
    return this.mDelegate.getSpanFlags(paramObject);
  }
  
  public int getSpanStart(Object paramObject)
  {
    return this.mDelegate.getSpanStart(paramObject);
  }
  
  public <T> T[] getSpans(int paramInt1, int paramInt2, Class<T> paramClass)
  {
    return this.mDelegate.getSpans(paramInt1, paramInt2, paramClass);
  }
  
  Spannable getUnwrappedSpannable()
  {
    return this.mDelegate;
  }
  
  public int length()
  {
    return this.mDelegate.length();
  }
  
  public int nextSpanTransition(int paramInt1, int paramInt2, Class paramClass)
  {
    return this.mDelegate.nextSpanTransition(paramInt1, paramInt2, paramClass);
  }
  
  public void removeSpan(Object paramObject)
  {
    ensureSafeWrites();
    this.mDelegate.removeSpan(paramObject);
  }
  
  public void setSpan(Object paramObject, int paramInt1, int paramInt2, int paramInt3)
  {
    ensureSafeWrites();
    this.mDelegate.setSpan(paramObject, paramInt1, paramInt2, paramInt3);
  }
  
  public CharSequence subSequence(int paramInt1, int paramInt2)
  {
    return this.mDelegate.subSequence(paramInt1, paramInt2);
  }
  
  public String toString()
  {
    return this.mDelegate.toString();
  }
  
  private static class CharSequenceHelper_API24
  {
    static IntStream chars(CharSequence paramCharSequence)
    {
      return paramCharSequence.chars();
    }
    
    static IntStream codePoints(CharSequence paramCharSequence)
    {
      return paramCharSequence.codePoints();
    }
  }
  
  static class PrecomputedTextDetector
  {
    boolean isPrecomputedText(CharSequence paramCharSequence)
    {
      return paramCharSequence instanceof PrecomputedTextCompat;
    }
  }
  
  static class PrecomputedTextDetector_28
    extends UnprecomputeTextOnModificationSpannable.PrecomputedTextDetector
  {
    boolean isPrecomputedText(CharSequence paramCharSequence)
    {
      boolean bool;
      if ((!(paramCharSequence instanceof PrecomputedText)) && (!(paramCharSequence instanceof PrecomputedTextCompat))) {
        bool = false;
      } else {
        bool = true;
      }
      return bool;
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/emoji2/text/UnprecomputeTextOnModificationSpannable.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */