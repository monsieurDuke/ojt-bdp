package androidx.core.graphics;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.ColorSpace;
import android.graphics.Point;
import android.graphics.PointF;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

@Metadata(d1={"\000D\n\000\n\002\030\002\n\000\n\002\020\b\n\002\b\002\n\002\030\002\n\000\n\002\020\013\n\000\n\002\030\002\n\002\b\002\n\002\030\002\n\002\030\002\n\002\020\002\n\002\030\002\n\002\b\002\n\002\030\002\n\002\030\002\n\002\b\b\032#\020\000\032\0020\0012\006\020\002\032\0020\0032\006\020\004\032\0020\0032\b\b\002\020\005\032\0020\006H\b\0327\020\000\032\0020\0012\006\020\002\032\0020\0032\006\020\004\032\0020\0032\b\b\002\020\005\032\0020\0062\b\b\002\020\007\032\0020\b2\b\b\002\020\t\032\0020\nH\b\032)\020\013\032\0020\001*\0020\0012\027\020\f\032\023\022\004\022\0020\016\022\004\022\0020\0170\r¢\006\002\b\020H\bø\001\000\032\025\020\021\032\0020\b*\0020\0012\006\020\022\032\0020\023H\n\032\025\020\021\032\0020\b*\0020\0012\006\020\022\032\0020\024H\n\032\035\020\025\032\0020\003*\0020\0012\006\020\026\032\0020\0032\006\020\027\032\0020\003H\n\032'\020\030\032\0020\001*\0020\0012\006\020\002\032\0020\0032\006\020\004\032\0020\0032\b\b\002\020\031\032\0020\bH\b\032'\020\032\032\0020\017*\0020\0012\006\020\026\032\0020\0032\006\020\027\032\0020\0032\b\b\001\020\033\032\0020\003H\n\002\007\n\005\b20\001¨\006\034"}, d2={"createBitmap", "Landroid/graphics/Bitmap;", "width", "", "height", "config", "Landroid/graphics/Bitmap$Config;", "hasAlpha", "", "colorSpace", "Landroid/graphics/ColorSpace;", "applyCanvas", "block", "Lkotlin/Function1;", "Landroid/graphics/Canvas;", "", "Lkotlin/ExtensionFunctionType;", "contains", "p", "Landroid/graphics/Point;", "Landroid/graphics/PointF;", "get", "x", "y", "scale", "filter", "set", "color", "core-ktx_release"}, k=2, mv={1, 6, 0}, xi=48)
public final class BitmapKt
{
  public static final Bitmap applyCanvas(Bitmap paramBitmap, Function1<? super Canvas, Unit> paramFunction1)
  {
    Intrinsics.checkNotNullParameter(paramBitmap, "<this>");
    Intrinsics.checkNotNullParameter(paramFunction1, "block");
    paramFunction1.invoke(new Canvas(paramBitmap));
    return paramBitmap;
  }
  
  public static final boolean contains(Bitmap paramBitmap, Point paramPoint)
  {
    Intrinsics.checkNotNullParameter(paramBitmap, "<this>");
    Intrinsics.checkNotNullParameter(paramPoint, "p");
    int i = paramBitmap.getWidth();
    int j = paramPoint.x;
    boolean bool = true;
    if ((j >= 0) && (j < i)) {
      i = 1;
    } else {
      i = 0;
    }
    if ((i == 0) || (paramPoint.y < 0) || (paramPoint.y >= paramBitmap.getHeight())) {
      bool = false;
    }
    return bool;
  }
  
  public static final boolean contains(Bitmap paramBitmap, PointF paramPointF)
  {
    Intrinsics.checkNotNullParameter(paramBitmap, "<this>");
    Intrinsics.checkNotNullParameter(paramPointF, "p");
    boolean bool;
    if ((paramPointF.x >= 0.0F) && (paramPointF.x < paramBitmap.getWidth()) && (paramPointF.y >= 0.0F) && (paramPointF.y < paramBitmap.getHeight())) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public static final Bitmap createBitmap(int paramInt1, int paramInt2, Bitmap.Config paramConfig)
  {
    Intrinsics.checkNotNullParameter(paramConfig, "config");
    paramConfig = Bitmap.createBitmap(paramInt1, paramInt2, paramConfig);
    Intrinsics.checkNotNullExpressionValue(paramConfig, "createBitmap(width, height, config)");
    return paramConfig;
  }
  
  public static final Bitmap createBitmap(int paramInt1, int paramInt2, Bitmap.Config paramConfig, boolean paramBoolean, ColorSpace paramColorSpace)
  {
    Intrinsics.checkNotNullParameter(paramConfig, "config");
    Intrinsics.checkNotNullParameter(paramColorSpace, "colorSpace");
    paramConfig = Bitmap.createBitmap(paramInt1, paramInt2, paramConfig, paramBoolean, paramColorSpace);
    Intrinsics.checkNotNullExpressionValue(paramConfig, "createBitmap(width, heig…ig, hasAlpha, colorSpace)");
    return paramConfig;
  }
  
  public static final int get(Bitmap paramBitmap, int paramInt1, int paramInt2)
  {
    Intrinsics.checkNotNullParameter(paramBitmap, "<this>");
    return paramBitmap.getPixel(paramInt1, paramInt2);
  }
  
  public static final Bitmap scale(Bitmap paramBitmap, int paramInt1, int paramInt2, boolean paramBoolean)
  {
    Intrinsics.checkNotNullParameter(paramBitmap, "<this>");
    paramBitmap = Bitmap.createScaledBitmap(paramBitmap, paramInt1, paramInt2, paramBoolean);
    Intrinsics.checkNotNullExpressionValue(paramBitmap, "createScaledBitmap(this, width, height, filter)");
    return paramBitmap;
  }
  
  public static final void set(Bitmap paramBitmap, int paramInt1, int paramInt2, int paramInt3)
  {
    Intrinsics.checkNotNullParameter(paramBitmap, "<this>");
    paramBitmap.setPixel(paramInt1, paramInt2, paramInt3);
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/core/graphics/BitmapKt.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */