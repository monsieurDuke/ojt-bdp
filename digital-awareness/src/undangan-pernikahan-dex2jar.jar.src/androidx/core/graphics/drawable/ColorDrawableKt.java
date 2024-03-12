package androidx.core.graphics.drawable;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

@Metadata(d1={"\000\020\n\000\n\002\030\002\n\002\030\002\n\002\020\b\n\000\032\r\020\000\032\0020\001*\0020\002H\b\032\r\020\000\032\0020\001*\0020\003H\b¨\006\004"}, d2={"toDrawable", "Landroid/graphics/drawable/ColorDrawable;", "Landroid/graphics/Color;", "", "core-ktx_release"}, k=2, mv={1, 6, 0}, xi=48)
public final class ColorDrawableKt
{
  public static final ColorDrawable toDrawable(int paramInt)
  {
    return new ColorDrawable(paramInt);
  }
  
  public static final ColorDrawable toDrawable(Color paramColor)
  {
    Intrinsics.checkNotNullParameter(paramColor, "<this>");
    return new ColorDrawable(paramColor.toArgb());
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/core/graphics/drawable/ColorDrawableKt.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */