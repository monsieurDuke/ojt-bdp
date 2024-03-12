package androidx.core.graphics;

import android.graphics.Canvas;
import android.graphics.Picture;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.InlineMarker;
import kotlin.jvm.internal.Intrinsics;

@Metadata(d1={"\000\"\n\000\n\002\030\002\n\000\n\002\020\b\n\002\b\002\n\002\030\002\n\002\030\002\n\002\020\002\n\002\030\002\n\000\0329\020\000\032\0020\001*\0020\0012\006\020\002\032\0020\0032\006\020\004\032\0020\0032\027\020\005\032\023\022\004\022\0020\007\022\004\022\0020\b0\006¢\006\002\b\tH\bø\001\000\002\007\n\005\b20\001¨\006\n"}, d2={"record", "Landroid/graphics/Picture;", "width", "", "height", "block", "Lkotlin/Function1;", "Landroid/graphics/Canvas;", "", "Lkotlin/ExtensionFunctionType;", "core-ktx_release"}, k=2, mv={1, 6, 0}, xi=48)
public final class PictureKt
{
  public static final Picture record(Picture paramPicture, int paramInt1, int paramInt2, Function1<? super Canvas, Unit> paramFunction1)
  {
    Intrinsics.checkNotNullParameter(paramPicture, "<this>");
    Intrinsics.checkNotNullParameter(paramFunction1, "block");
    Canvas localCanvas = paramPicture.beginRecording(paramInt1, paramInt2);
    Intrinsics.checkNotNullExpressionValue(localCanvas, "beginRecording(width, height)");
    try
    {
      paramFunction1.invoke(localCanvas);
      return paramPicture;
    }
    finally
    {
      InlineMarker.finallyStart(1);
      paramPicture.endRecording();
      InlineMarker.finallyEnd(1);
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/core/graphics/PictureKt.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */