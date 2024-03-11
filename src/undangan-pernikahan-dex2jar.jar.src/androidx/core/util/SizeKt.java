package androidx.core.util;

import android.util.Size;
import android.util.SizeF;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

@Metadata(d1={"\000\032\n\000\n\002\020\b\n\002\030\002\n\002\020\007\n\002\030\002\n\002\030\002\n\002\b\002\032\r\020\000\032\0020\001*\0020\002H\n\032\r\020\000\032\0020\003*\0020\004H\n\032\r\020\000\032\0020\003*\0020\005H\n\032\r\020\006\032\0020\001*\0020\002H\n\032\r\020\006\032\0020\003*\0020\004H\n\032\r\020\006\032\0020\003*\0020\005H\n¨\006\007"}, d2={"component1", "", "Landroid/util/Size;", "", "Landroid/util/SizeF;", "Landroidx/core/util/SizeFCompat;", "component2", "core-ktx_release"}, k=2, mv={1, 6, 0}, xi=48)
public final class SizeKt
{
  public static final float component1(SizeF paramSizeF)
  {
    Intrinsics.checkNotNullParameter(paramSizeF, "<this>");
    return paramSizeF.getWidth();
  }
  
  public static final float component1(SizeFCompat paramSizeFCompat)
  {
    Intrinsics.checkNotNullParameter(paramSizeFCompat, "<this>");
    return paramSizeFCompat.getWidth();
  }
  
  public static final int component1(Size paramSize)
  {
    Intrinsics.checkNotNullParameter(paramSize, "<this>");
    return paramSize.getWidth();
  }
  
  public static final float component2(SizeF paramSizeF)
  {
    Intrinsics.checkNotNullParameter(paramSizeF, "<this>");
    return paramSizeF.getHeight();
  }
  
  public static final float component2(SizeFCompat paramSizeFCompat)
  {
    Intrinsics.checkNotNullParameter(paramSizeFCompat, "<this>");
    return paramSizeFCompat.getHeight();
  }
  
  public static final int component2(Size paramSize)
  {
    Intrinsics.checkNotNullParameter(paramSize, "<this>");
    return paramSize.getHeight();
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/core/util/SizeKt.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */