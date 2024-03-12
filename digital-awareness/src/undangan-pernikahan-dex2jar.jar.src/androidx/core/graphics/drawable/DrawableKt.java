package androidx.core.graphics.drawable;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

@Metadata(d1={"\000$\n\000\n\002\030\002\n\002\030\002\n\000\n\002\020\b\n\002\b\002\n\002\030\002\n\002\b\002\n\002\020\002\n\002\b\005\032*\020\000\032\0020\001*\0020\0022\b\b\003\020\003\032\0020\0042\b\b\003\020\005\032\0020\0042\n\b\002\020\006\032\004\030\0010\007\032,\020\b\032\004\030\0010\001*\0020\0022\b\b\003\020\003\032\0020\0042\b\b\003\020\005\032\0020\0042\n\b\002\020\006\032\004\030\0010\007\0322\020\t\032\0020\n*\0020\0022\b\b\003\020\013\032\0020\0042\b\b\003\020\f\032\0020\0042\b\b\003\020\r\032\0020\0042\b\b\003\020\016\032\0020\004¨\006\017"}, d2={"toBitmap", "Landroid/graphics/Bitmap;", "Landroid/graphics/drawable/Drawable;", "width", "", "height", "config", "Landroid/graphics/Bitmap$Config;", "toBitmapOrNull", "updateBounds", "", "left", "top", "right", "bottom", "core-ktx_release"}, k=2, mv={1, 6, 0}, xi=48)
public final class DrawableKt
{
  public static final Bitmap toBitmap(Drawable paramDrawable, int paramInt1, int paramInt2, Bitmap.Config paramConfig)
  {
    Intrinsics.checkNotNullParameter(paramDrawable, "<this>");
    if ((paramDrawable instanceof BitmapDrawable)) {
      if (((BitmapDrawable)paramDrawable).getBitmap() != null)
      {
        if ((paramConfig == null) || (((BitmapDrawable)paramDrawable).getBitmap().getConfig() == paramConfig))
        {
          if ((paramInt1 == ((BitmapDrawable)paramDrawable).getBitmap().getWidth()) && (paramInt2 == ((BitmapDrawable)paramDrawable).getBitmap().getHeight()))
          {
            paramDrawable = ((BitmapDrawable)paramDrawable).getBitmap();
            Intrinsics.checkNotNullExpressionValue(paramDrawable, "bitmap");
            return paramDrawable;
          }
          paramDrawable = Bitmap.createScaledBitmap(((BitmapDrawable)paramDrawable).getBitmap(), paramInt1, paramInt2, true);
          Intrinsics.checkNotNullExpressionValue(paramDrawable, "createScaledBitmap(bitmap, width, height, true)");
          return paramDrawable;
        }
      }
      else {
        throw new IllegalArgumentException("bitmap is null");
      }
    }
    Rect localRect = paramDrawable.getBounds();
    Intrinsics.checkNotNullExpressionValue(localRect, "bounds");
    int k = localRect.left;
    int i = localRect.top;
    int m = localRect.right;
    int j = localRect.bottom;
    if (paramConfig == null) {
      paramConfig = Bitmap.Config.ARGB_8888;
    }
    paramConfig = Bitmap.createBitmap(paramInt1, paramInt2, paramConfig);
    paramDrawable.setBounds(0, 0, paramInt1, paramInt2);
    paramDrawable.draw(new Canvas(paramConfig));
    paramDrawable.setBounds(k, i, m, j);
    Intrinsics.checkNotNullExpressionValue(paramConfig, "bitmap");
    return paramConfig;
  }
  
  public static final Bitmap toBitmapOrNull(Drawable paramDrawable, int paramInt1, int paramInt2, Bitmap.Config paramConfig)
  {
    Intrinsics.checkNotNullParameter(paramDrawable, "<this>");
    if (((paramDrawable instanceof BitmapDrawable)) && (((BitmapDrawable)paramDrawable).getBitmap() == null)) {
      return null;
    }
    return toBitmap(paramDrawable, paramInt1, paramInt2, paramConfig);
  }
  
  public static final void updateBounds(Drawable paramDrawable, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    Intrinsics.checkNotNullParameter(paramDrawable, "<this>");
    paramDrawable.setBounds(paramInt1, paramInt2, paramInt3, paramInt4);
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/core/graphics/drawable/DrawableKt.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */