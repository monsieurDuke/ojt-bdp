package androidx.core.graphics;

import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.InlineMarker;
import kotlin.jvm.internal.Intrinsics;

@Metadata(d1={"\000>\n\000\n\002\020\002\n\002\030\002\n\000\n\002\030\002\n\000\n\002\030\002\n\002\030\002\n\000\n\002\030\002\n\002\030\002\n\000\n\002\020\007\n\002\b\003\n\002\020\b\n\002\b\002\n\002\030\002\n\002\b\013\0321\020\000\032\0020\001*\0020\0022\006\020\003\032\0020\0042\027\020\005\032\023\022\004\022\0020\002\022\004\022\0020\0010\006¢\006\002\b\007H\bø\001\000\0321\020\000\032\0020\001*\0020\0022\006\020\b\032\0020\t2\027\020\005\032\023\022\004\022\0020\002\022\004\022\0020\0010\006¢\006\002\b\007H\bø\001\000\0321\020\000\032\0020\001*\0020\0022\006\020\b\032\0020\n2\027\020\005\032\023\022\004\022\0020\002\022\004\022\0020\0010\006¢\006\002\b\007H\bø\001\000\032I\020\000\032\0020\001*\0020\0022\006\020\013\032\0020\f2\006\020\r\032\0020\f2\006\020\016\032\0020\f2\006\020\017\032\0020\f2\027\020\005\032\023\022\004\022\0020\002\022\004\022\0020\0010\006¢\006\002\b\007H\bø\001\000\032I\020\000\032\0020\001*\0020\0022\006\020\013\032\0020\0202\006\020\r\032\0020\0202\006\020\016\032\0020\0202\006\020\017\032\0020\0202\027\020\005\032\023\022\004\022\0020\002\022\004\022\0020\0010\006¢\006\002\b\007H\bø\001\000\0323\020\021\032\0020\001*\0020\0022\b\b\002\020\022\032\0020\0232\027\020\005\032\023\022\004\022\0020\002\022\004\022\0020\0010\006¢\006\002\b\007H\bø\001\000\032G\020\024\032\0020\001*\0020\0022\b\b\002\020\025\032\0020\f2\b\b\002\020\026\032\0020\f2\b\b\002\020\027\032\0020\f2\027\020\005\032\023\022\004\022\0020\002\022\004\022\0020\0010\006¢\006\002\b\007H\bø\001\000\032)\020\030\032\0020\001*\0020\0022\027\020\005\032\023\022\004\022\0020\002\022\004\022\0020\0010\006¢\006\002\b\007H\bø\001\000\032Q\020\031\032\0020\001*\0020\0022\b\b\002\020\032\032\0020\f2\b\b\002\020\033\032\0020\f2\b\b\002\020\026\032\0020\f2\b\b\002\020\027\032\0020\f2\027\020\005\032\023\022\004\022\0020\002\022\004\022\0020\0010\006¢\006\002\b\007H\bø\001\000\032=\020\034\032\0020\001*\0020\0022\b\b\002\020\032\032\0020\f2\b\b\002\020\033\032\0020\f2\027\020\005\032\023\022\004\022\0020\002\022\004\022\0020\0010\006¢\006\002\b\007H\bø\001\000\032=\020\035\032\0020\001*\0020\0022\b\b\002\020\032\032\0020\f2\b\b\002\020\033\032\0020\f2\027\020\005\032\023\022\004\022\0020\002\022\004\022\0020\0010\006¢\006\002\b\007H\bø\001\000\002\007\n\005\b20\001¨\006\036"}, d2={"withClip", "", "Landroid/graphics/Canvas;", "clipPath", "Landroid/graphics/Path;", "block", "Lkotlin/Function1;", "Lkotlin/ExtensionFunctionType;", "clipRect", "Landroid/graphics/Rect;", "Landroid/graphics/RectF;", "left", "", "top", "right", "bottom", "", "withMatrix", "matrix", "Landroid/graphics/Matrix;", "withRotation", "degrees", "pivotX", "pivotY", "withSave", "withScale", "x", "y", "withSkew", "withTranslation", "core-ktx_release"}, k=2, mv={1, 6, 0}, xi=48)
public final class CanvasKt
{
  public static final void withClip(Canvas paramCanvas, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, Function1<? super Canvas, Unit> paramFunction1)
  {
    Intrinsics.checkNotNullParameter(paramCanvas, "<this>");
    Intrinsics.checkNotNullParameter(paramFunction1, "block");
    int i = paramCanvas.save();
    paramCanvas.clipRect(paramFloat1, paramFloat2, paramFloat3, paramFloat4);
    try
    {
      paramFunction1.invoke(paramCanvas);
      return;
    }
    finally
    {
      InlineMarker.finallyStart(1);
      paramCanvas.restoreToCount(i);
      InlineMarker.finallyEnd(1);
    }
  }
  
  public static final void withClip(Canvas paramCanvas, int paramInt1, int paramInt2, int paramInt3, int paramInt4, Function1<? super Canvas, Unit> paramFunction1)
  {
    Intrinsics.checkNotNullParameter(paramCanvas, "<this>");
    Intrinsics.checkNotNullParameter(paramFunction1, "block");
    int i = paramCanvas.save();
    paramCanvas.clipRect(paramInt1, paramInt2, paramInt3, paramInt4);
    try
    {
      paramFunction1.invoke(paramCanvas);
      return;
    }
    finally
    {
      InlineMarker.finallyStart(1);
      paramCanvas.restoreToCount(i);
      InlineMarker.finallyEnd(1);
    }
  }
  
  public static final void withClip(Canvas paramCanvas, Path paramPath, Function1<? super Canvas, Unit> paramFunction1)
  {
    Intrinsics.checkNotNullParameter(paramCanvas, "<this>");
    Intrinsics.checkNotNullParameter(paramPath, "clipPath");
    Intrinsics.checkNotNullParameter(paramFunction1, "block");
    int i = paramCanvas.save();
    paramCanvas.clipPath(paramPath);
    try
    {
      paramFunction1.invoke(paramCanvas);
      return;
    }
    finally
    {
      InlineMarker.finallyStart(1);
      paramCanvas.restoreToCount(i);
      InlineMarker.finallyEnd(1);
    }
  }
  
  public static final void withClip(Canvas paramCanvas, Rect paramRect, Function1<? super Canvas, Unit> paramFunction1)
  {
    Intrinsics.checkNotNullParameter(paramCanvas, "<this>");
    Intrinsics.checkNotNullParameter(paramRect, "clipRect");
    Intrinsics.checkNotNullParameter(paramFunction1, "block");
    int i = paramCanvas.save();
    paramCanvas.clipRect(paramRect);
    try
    {
      paramFunction1.invoke(paramCanvas);
      return;
    }
    finally
    {
      InlineMarker.finallyStart(1);
      paramCanvas.restoreToCount(i);
      InlineMarker.finallyEnd(1);
    }
  }
  
  public static final void withClip(Canvas paramCanvas, RectF paramRectF, Function1<? super Canvas, Unit> paramFunction1)
  {
    Intrinsics.checkNotNullParameter(paramCanvas, "<this>");
    Intrinsics.checkNotNullParameter(paramRectF, "clipRect");
    Intrinsics.checkNotNullParameter(paramFunction1, "block");
    int i = paramCanvas.save();
    paramCanvas.clipRect(paramRectF);
    try
    {
      paramFunction1.invoke(paramCanvas);
      return;
    }
    finally
    {
      InlineMarker.finallyStart(1);
      paramCanvas.restoreToCount(i);
      InlineMarker.finallyEnd(1);
    }
  }
  
  public static final void withMatrix(Canvas paramCanvas, Matrix paramMatrix, Function1<? super Canvas, Unit> paramFunction1)
  {
    Intrinsics.checkNotNullParameter(paramCanvas, "<this>");
    Intrinsics.checkNotNullParameter(paramMatrix, "matrix");
    Intrinsics.checkNotNullParameter(paramFunction1, "block");
    int i = paramCanvas.save();
    paramCanvas.concat(paramMatrix);
    try
    {
      paramFunction1.invoke(paramCanvas);
      return;
    }
    finally
    {
      InlineMarker.finallyStart(1);
      paramCanvas.restoreToCount(i);
      InlineMarker.finallyEnd(1);
    }
  }
  
  public static final void withRotation(Canvas paramCanvas, float paramFloat1, float paramFloat2, float paramFloat3, Function1<? super Canvas, Unit> paramFunction1)
  {
    Intrinsics.checkNotNullParameter(paramCanvas, "<this>");
    Intrinsics.checkNotNullParameter(paramFunction1, "block");
    int i = paramCanvas.save();
    paramCanvas.rotate(paramFloat1, paramFloat2, paramFloat3);
    try
    {
      paramFunction1.invoke(paramCanvas);
      return;
    }
    finally
    {
      InlineMarker.finallyStart(1);
      paramCanvas.restoreToCount(i);
      InlineMarker.finallyEnd(1);
    }
  }
  
  public static final void withSave(Canvas paramCanvas, Function1<? super Canvas, Unit> paramFunction1)
  {
    Intrinsics.checkNotNullParameter(paramCanvas, "<this>");
    Intrinsics.checkNotNullParameter(paramFunction1, "block");
    int i = paramCanvas.save();
    try
    {
      paramFunction1.invoke(paramCanvas);
      return;
    }
    finally
    {
      InlineMarker.finallyStart(1);
      paramCanvas.restoreToCount(i);
      InlineMarker.finallyEnd(1);
    }
  }
  
  public static final void withScale(Canvas paramCanvas, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, Function1<? super Canvas, Unit> paramFunction1)
  {
    Intrinsics.checkNotNullParameter(paramCanvas, "<this>");
    Intrinsics.checkNotNullParameter(paramFunction1, "block");
    int i = paramCanvas.save();
    paramCanvas.scale(paramFloat1, paramFloat2, paramFloat3, paramFloat4);
    try
    {
      paramFunction1.invoke(paramCanvas);
      return;
    }
    finally
    {
      InlineMarker.finallyStart(1);
      paramCanvas.restoreToCount(i);
      InlineMarker.finallyEnd(1);
    }
  }
  
  public static final void withSkew(Canvas paramCanvas, float paramFloat1, float paramFloat2, Function1<? super Canvas, Unit> paramFunction1)
  {
    Intrinsics.checkNotNullParameter(paramCanvas, "<this>");
    Intrinsics.checkNotNullParameter(paramFunction1, "block");
    int i = paramCanvas.save();
    paramCanvas.skew(paramFloat1, paramFloat2);
    try
    {
      paramFunction1.invoke(paramCanvas);
      return;
    }
    finally
    {
      InlineMarker.finallyStart(1);
      paramCanvas.restoreToCount(i);
      InlineMarker.finallyEnd(1);
    }
  }
  
  public static final void withTranslation(Canvas paramCanvas, float paramFloat1, float paramFloat2, Function1<? super Canvas, Unit> paramFunction1)
  {
    Intrinsics.checkNotNullParameter(paramCanvas, "<this>");
    Intrinsics.checkNotNullParameter(paramFunction1, "block");
    int i = paramCanvas.save();
    paramCanvas.translate(paramFloat1, paramFloat2);
    try
    {
      paramFunction1.invoke(paramCanvas);
      return;
    }
    finally
    {
      InlineMarker.finallyStart(1);
      paramCanvas.restoreToCount(i);
      InlineMarker.finallyEnd(1);
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/core/graphics/CanvasKt.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */