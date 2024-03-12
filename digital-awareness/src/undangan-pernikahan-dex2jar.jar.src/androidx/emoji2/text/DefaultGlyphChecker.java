package androidx.emoji2.text;

import android.os.Build.VERSION;
import android.text.TextPaint;
import androidx.core.graphics.PaintCompat;

class DefaultGlyphChecker
  implements EmojiCompat.GlyphChecker
{
  private static final int PAINT_TEXT_SIZE = 10;
  private static final ThreadLocal<StringBuilder> sStringBuilder = new ThreadLocal();
  private final TextPaint mTextPaint;
  
  DefaultGlyphChecker()
  {
    TextPaint localTextPaint = new TextPaint();
    this.mTextPaint = localTextPaint;
    localTextPaint.setTextSize(10.0F);
  }
  
  private static StringBuilder getStringBuilder()
  {
    ThreadLocal localThreadLocal = sStringBuilder;
    if (localThreadLocal.get() == null) {
      localThreadLocal.set(new StringBuilder());
    }
    return (StringBuilder)localThreadLocal.get();
  }
  
  public boolean hasGlyph(CharSequence paramCharSequence, int paramInt1, int paramInt2, int paramInt3)
  {
    if ((Build.VERSION.SDK_INT < 23) && (paramInt3 > Build.VERSION.SDK_INT)) {
      return false;
    }
    StringBuilder localStringBuilder = getStringBuilder();
    localStringBuilder.setLength(0);
    while (paramInt1 < paramInt2)
    {
      localStringBuilder.append(paramCharSequence.charAt(paramInt1));
      paramInt1++;
    }
    return PaintCompat.hasGlyph(this.mTextPaint, localStringBuilder.toString());
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/emoji2/text/DefaultGlyphChecker.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */