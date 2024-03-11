package androidx.core.graphics;

import android.graphics.BlendMode;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.os.Build.VERSION;
import androidx.core.util.Pair;

public final class PaintCompat
{
  private static final String EM_STRING = "m";
  private static final String TOFU_STRING = "󟿽";
  private static final ThreadLocal<Pair<Rect, Rect>> sRectThreadLocal = new ThreadLocal();
  
  public static boolean hasGlyph(Paint paramPaint, String paramString)
  {
    if (Build.VERSION.SDK_INT >= 23) {
      return Api23Impl.hasGlyph(paramPaint, paramString);
    }
    int j = paramString.length();
    if ((j == 1) && (Character.isWhitespace(paramString.charAt(0)))) {
      return true;
    }
    float f2 = paramPaint.measureText("󟿽");
    float f1 = paramPaint.measureText("m");
    float f3 = paramPaint.measureText(paramString);
    if (f3 == 0.0F) {
      return false;
    }
    if (paramString.codePointCount(0, paramString.length()) > 1)
    {
      if (f3 > 2.0F * f1) {
        return false;
      }
      f1 = 0.0F;
      int i = 0;
      while (i < j)
      {
        int k = Character.charCount(paramString.codePointAt(i));
        f1 += paramPaint.measureText(paramString, i, i + k);
        i += k;
      }
      if (f3 >= f1) {
        return false;
      }
    }
    if (f3 != f2) {
      return true;
    }
    Pair localPair = obtainEmptyRects();
    paramPaint.getTextBounds("󟿽", 0, "󟿽".length(), (Rect)localPair.first);
    paramPaint.getTextBounds(paramString, 0, j, (Rect)localPair.second);
    return true ^ ((Rect)localPair.first).equals(localPair.second);
  }
  
  private static Pair<Rect, Rect> obtainEmptyRects()
  {
    ThreadLocal localThreadLocal = sRectThreadLocal;
    Pair localPair = (Pair)localThreadLocal.get();
    if (localPair == null)
    {
      localPair = new Pair(new Rect(), new Rect());
      localThreadLocal.set(localPair);
    }
    else
    {
      ((Rect)localPair.first).setEmpty();
      ((Rect)localPair.second).setEmpty();
    }
    return localPair;
  }
  
  public static boolean setBlendMode(Paint paramPaint, BlendModeCompat paramBlendModeCompat)
  {
    int i = Build.VERSION.SDK_INT;
    boolean bool = true;
    Object localObject = null;
    PorterDuff.Mode localMode = null;
    if (i >= 29)
    {
      if (paramBlendModeCompat != null) {
        paramBlendModeCompat = BlendModeUtils.Api29Impl.obtainBlendModeFromCompat(paramBlendModeCompat);
      } else {
        paramBlendModeCompat = localMode;
      }
      Api29Impl.setBlendMode(paramPaint, paramBlendModeCompat);
      return true;
    }
    if (paramBlendModeCompat != null)
    {
      localMode = BlendModeUtils.obtainPorterDuffFromCompat(paramBlendModeCompat);
      paramBlendModeCompat = (BlendModeCompat)localObject;
      if (localMode != null) {
        paramBlendModeCompat = new PorterDuffXfermode(localMode);
      }
      paramPaint.setXfermode(paramBlendModeCompat);
      if (localMode == null) {
        bool = false;
      }
      return bool;
    }
    paramPaint.setXfermode(null);
    return true;
  }
  
  static class Api23Impl
  {
    static boolean hasGlyph(Paint paramPaint, String paramString)
    {
      return paramPaint.hasGlyph(paramString);
    }
  }
  
  static class Api29Impl
  {
    static void setBlendMode(Paint paramPaint, Object paramObject)
    {
      paramPaint.setBlendMode((BlendMode)paramObject);
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/core/graphics/PaintCompat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */