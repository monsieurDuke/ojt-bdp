package androidx.core.graphics.drawable;

import android.graphics.Bitmap;
import android.graphics.drawable.Icon;
import android.net.Uri;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

@Metadata(d1={"\000\026\n\000\n\002\030\002\n\002\030\002\n\000\n\002\030\002\n\002\020\022\n\000\032\r\020\000\032\0020\001*\0020\002H\b\032\r\020\003\032\0020\001*\0020\002H\b\032\r\020\003\032\0020\001*\0020\004H\b\032\r\020\003\032\0020\001*\0020\005H\b¨\006\006"}, d2={"toAdaptiveIcon", "Landroid/graphics/drawable/Icon;", "Landroid/graphics/Bitmap;", "toIcon", "Landroid/net/Uri;", "", "core-ktx_release"}, k=2, mv={1, 6, 0}, xi=48)
public final class IconKt
{
  public static final Icon toAdaptiveIcon(Bitmap paramBitmap)
  {
    Intrinsics.checkNotNullParameter(paramBitmap, "<this>");
    paramBitmap = Icon.createWithAdaptiveBitmap(paramBitmap);
    Intrinsics.checkNotNullExpressionValue(paramBitmap, "createWithAdaptiveBitmap(this)");
    return paramBitmap;
  }
  
  public static final Icon toIcon(Bitmap paramBitmap)
  {
    Intrinsics.checkNotNullParameter(paramBitmap, "<this>");
    paramBitmap = Icon.createWithBitmap(paramBitmap);
    Intrinsics.checkNotNullExpressionValue(paramBitmap, "createWithBitmap(this)");
    return paramBitmap;
  }
  
  public static final Icon toIcon(Uri paramUri)
  {
    Intrinsics.checkNotNullParameter(paramUri, "<this>");
    paramUri = Icon.createWithContentUri(paramUri);
    Intrinsics.checkNotNullExpressionValue(paramUri, "createWithContentUri(this)");
    return paramUri;
  }
  
  public static final Icon toIcon(byte[] paramArrayOfByte)
  {
    Intrinsics.checkNotNullParameter(paramArrayOfByte, "<this>");
    paramArrayOfByte = Icon.createWithData(paramArrayOfByte, 0, paramArrayOfByte.length);
    Intrinsics.checkNotNullExpressionValue(paramArrayOfByte, "createWithData(this, 0, size)");
    return paramArrayOfByte;
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/core/graphics/drawable/IconKt.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */