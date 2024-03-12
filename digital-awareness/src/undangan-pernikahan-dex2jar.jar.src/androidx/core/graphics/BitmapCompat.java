package androidx.core.graphics;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BlendMode;
import android.graphics.Canvas;
import android.graphics.ColorSpace;
import android.graphics.ColorSpace.Named;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.hardware.HardwareBuffer;
import android.os.Build.VERSION;

public final class BitmapCompat
{
  public static Bitmap createScaledBitmap(Bitmap paramBitmap, int paramInt1, int paramInt2, Rect paramRect, boolean paramBoolean)
  {
    if ((paramInt1 > 0) && (paramInt2 > 0))
    {
      if ((paramRect != null) && ((paramRect.isEmpty()) || (paramRect.left < 0) || (paramRect.right > paramBitmap.getWidth()) || (paramRect.top < 0) || (paramRect.bottom > paramBitmap.getHeight()))) {
        throw new IllegalArgumentException("srcRect must be contained by srcBm!");
      }
      Object localObject1 = paramBitmap;
      if (Build.VERSION.SDK_INT >= 27) {
        localObject1 = Api27Impl.copyBitmapIfHardware(paramBitmap);
      }
      int m;
      if (paramRect != null) {
        m = paramRect.width();
      } else {
        m = paramBitmap.getWidth();
      }
      int n;
      if (paramRect != null) {
        n = paramRect.height();
      } else {
        n = paramBitmap.getHeight();
      }
      float f1 = paramInt1 / m;
      float f2 = paramInt2 / n;
      int i;
      if (paramRect != null) {
        i = paramRect.left;
      } else {
        i = 0;
      }
      if (paramRect != null) {
        j = paramRect.top;
      } else {
        j = 0;
      }
      if ((i == 0) && (j == 0) && (paramInt1 == paramBitmap.getWidth()) && (paramInt2 == paramBitmap.getHeight()))
      {
        if ((paramBitmap.isMutable()) && (paramBitmap == localObject1)) {
          return paramBitmap.copy(paramBitmap.getConfig(), true);
        }
        return (Bitmap)localObject1;
      }
      Paint localPaint = new Paint(1);
      localPaint.setFilterBitmap(true);
      if (Build.VERSION.SDK_INT >= 29) {
        Api29Impl.setPaintBlendMode(localPaint);
      } else {
        localPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC));
      }
      if ((m == paramInt1) && (n == paramInt2))
      {
        paramBitmap = Bitmap.createBitmap(paramInt1, paramInt2, ((Bitmap)localObject1).getConfig());
        new Canvas(paramBitmap).drawBitmap((Bitmap)localObject1, -i, -j, localPaint);
        return paramBitmap;
      }
      double d = Math.log(2.0D);
      int i1;
      if (f1 > 1.0F) {
        i1 = (int)Math.ceil(Math.log(f1) / d);
      } else {
        i1 = (int)Math.floor(Math.log(f1) / d);
      }
      int i2;
      if (f2 > 1.0F) {
        i2 = (int)Math.ceil(Math.log(f2) / d);
      } else {
        i2 = (int)Math.floor(Math.log(f2) / d);
      }
      paramRect = null;
      int k = 0;
      int i3;
      if (paramBoolean)
      {
        if ((Build.VERSION.SDK_INT >= 27) && (!Api27Impl.isAlreadyF16AndLinear(paramBitmap)))
        {
          if (i1 > 0) {
            k = sizeAtStep(m, paramInt1, 1, i1);
          } else {
            k = m;
          }
          if (i2 > 0) {
            i3 = sizeAtStep(n, paramInt2, 1, i2);
          } else {
            i3 = n;
          }
          localObject2 = Api27Impl.createBitmapWithSourceColorspace(k, i3, paramBitmap, true);
          new Canvas((Bitmap)localObject2).drawBitmap((Bitmap)localObject1, -i, -j, localPaint);
          i = 0;
          i3 = 0;
          paramRect = (Rect)localObject1;
          localObject1 = localObject2;
          k = 1;
        }
        else
        {
          i3 = j;
        }
      }
      else {
        i3 = j;
      }
      int j = i2;
      Object localObject2 = new Rect(i, i3, m, n);
      Rect localRect = new Rect();
      int i4 = i1;
      int i5 = i;
      for (;;)
      {
        if ((i4 == 0) && (j == 0))
        {
          if ((paramRect != paramBitmap) && (paramRect != null)) {
            paramRect.recycle();
          }
          return (Bitmap)localObject1;
        }
        if (i4 < 0)
        {
          i = i4 + 1;
        }
        else
        {
          i = i4;
          if (i4 > 0) {
            i = i4 - 1;
          }
        }
        if (j < 0) {
          j++;
        } else if (j > 0) {
          j--;
        }
        localRect.set(0, 0, sizeAtStep(m, paramInt1, i, i1), sizeAtStep(n, paramInt2, j, i2));
        if ((i == 0) && (j == 0)) {
          i4 = 1;
        } else {
          i4 = 0;
        }
        if ((paramRect != null) && (paramRect.getWidth() == paramInt1) && (paramRect.getHeight() == paramInt2)) {
          i6 = 1;
        } else {
          i6 = 0;
        }
        if ((paramRect != null) && (paramRect != paramBitmap))
        {
          if ((paramBoolean) && (Build.VERSION.SDK_INT >= 27)) {
            if (!Api27Impl.isAlreadyF16AndLinear(paramRect)) {
              break label783;
            }
          }
          if ((i4 == 0) || ((i6 == 0) || (k == 0))) {
            label783:
            break label912;
          }
        }
        if ((paramRect != paramBitmap) && (paramRect != null)) {
          paramRect.recycle();
        }
        if (i > 0) {
          i6 = k;
        } else {
          i6 = i;
        }
        int i7 = sizeAtStep(m, paramInt1, i6, i1);
        if (j > 0) {
          i6 = k;
        } else {
          i6 = j;
        }
        int i6 = sizeAtStep(n, paramInt2, i6, i2);
        if (Build.VERSION.SDK_INT >= 27)
        {
          boolean bool;
          if ((paramBoolean) && (i4 == 0)) {
            bool = true;
          } else {
            bool = false;
          }
          paramRect = Api27Impl.createBitmapWithSourceColorspace(i7, i6, paramBitmap, bool);
        }
        else
        {
          paramRect = Bitmap.createBitmap(i7, i6, ((Bitmap)localObject1).getConfig());
        }
        label912:
        new Canvas(paramRect).drawBitmap((Bitmap)localObject1, (Rect)localObject2, localRect, localPaint);
        Object localObject3 = localObject1;
        ((Rect)localObject2).set(localRect);
        localObject1 = paramRect;
        paramRect = (Rect)localObject3;
        i4 = i;
      }
    }
    throw new IllegalArgumentException("dstW and dstH must be > 0!");
  }
  
  public static int getAllocationByteCount(Bitmap paramBitmap)
  {
    if (Build.VERSION.SDK_INT >= 19) {
      return Api19Impl.getAllocationByteCount(paramBitmap);
    }
    return paramBitmap.getByteCount();
  }
  
  public static boolean hasMipMap(Bitmap paramBitmap)
  {
    if (Build.VERSION.SDK_INT >= 17) {
      return Api17Impl.hasMipMap(paramBitmap);
    }
    return false;
  }
  
  public static void setHasMipMap(Bitmap paramBitmap, boolean paramBoolean)
  {
    if (Build.VERSION.SDK_INT >= 17) {
      Api17Impl.setHasMipMap(paramBitmap, paramBoolean);
    }
  }
  
  public static int sizeAtStep(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    if (paramInt3 == 0) {
      return paramInt2;
    }
    if (paramInt3 > 0) {
      return (1 << paramInt4 - paramInt3) * paramInt1;
    }
    return paramInt2 << -paramInt3 - 1;
  }
  
  static class Api17Impl
  {
    static boolean hasMipMap(Bitmap paramBitmap)
    {
      return paramBitmap.hasMipMap();
    }
    
    static void setHasMipMap(Bitmap paramBitmap, boolean paramBoolean)
    {
      paramBitmap.setHasMipMap(paramBoolean);
    }
  }
  
  static class Api19Impl
  {
    static int getAllocationByteCount(Bitmap paramBitmap)
    {
      return paramBitmap.getAllocationByteCount();
    }
  }
  
  static class Api27Impl
  {
    static Bitmap copyBitmapIfHardware(Bitmap paramBitmap)
    {
      if (paramBitmap.getConfig() == Bitmap.Config.HARDWARE)
      {
        Bitmap.Config localConfig = Bitmap.Config.ARGB_8888;
        if (Build.VERSION.SDK_INT >= 31) {
          localConfig = BitmapCompat.Api31Impl.getHardwareBitmapConfig(paramBitmap);
        }
        return paramBitmap.copy(localConfig, true);
      }
      return paramBitmap;
    }
    
    static Bitmap createBitmapWithSourceColorspace(int paramInt1, int paramInt2, Bitmap paramBitmap, boolean paramBoolean)
    {
      Bitmap.Config localConfig = paramBitmap.getConfig();
      ColorSpace localColorSpace2 = paramBitmap.getColorSpace();
      ColorSpace localColorSpace1 = ColorSpace.get(ColorSpace.Named.LINEAR_EXTENDED_SRGB);
      if ((paramBoolean) && (!paramBitmap.getColorSpace().equals(localColorSpace1)))
      {
        localConfig = Bitmap.Config.RGBA_F16;
      }
      else
      {
        localColorSpace1 = localColorSpace2;
        if (paramBitmap.getConfig() == Bitmap.Config.HARDWARE)
        {
          localConfig = Bitmap.Config.ARGB_8888;
          localColorSpace1 = localColorSpace2;
          if (Build.VERSION.SDK_INT >= 31)
          {
            localConfig = BitmapCompat.Api31Impl.getHardwareBitmapConfig(paramBitmap);
            localColorSpace1 = localColorSpace2;
          }
        }
      }
      return Bitmap.createBitmap(paramInt1, paramInt2, localConfig, paramBitmap.hasAlpha(), localColorSpace1);
    }
    
    static boolean isAlreadyF16AndLinear(Bitmap paramBitmap)
    {
      ColorSpace localColorSpace = ColorSpace.get(ColorSpace.Named.LINEAR_EXTENDED_SRGB);
      boolean bool;
      if ((paramBitmap.getConfig() == Bitmap.Config.RGBA_F16) && (paramBitmap.getColorSpace().equals(localColorSpace))) {
        bool = true;
      } else {
        bool = false;
      }
      return bool;
    }
  }
  
  static class Api29Impl
  {
    static void setPaintBlendMode(Paint paramPaint)
    {
      paramPaint.setBlendMode(BlendMode.SRC);
    }
  }
  
  static class Api31Impl
  {
    static Bitmap.Config getHardwareBitmapConfig(Bitmap paramBitmap)
    {
      if (paramBitmap.getHardwareBuffer().getFormat() == 22) {
        return Bitmap.Config.RGBA_F16;
      }
      return Bitmap.Config.ARGB_8888;
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/core/graphics/BitmapCompat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */