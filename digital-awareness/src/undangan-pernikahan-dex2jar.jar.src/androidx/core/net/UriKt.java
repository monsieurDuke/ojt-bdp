package androidx.core.net;

import android.net.Uri;
import java.io.File;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

@Metadata(d1={"\000\022\n\000\n\002\030\002\n\002\030\002\n\000\n\002\020\016\n\000\032\n\020\000\032\0020\001*\0020\002\032\r\020\003\032\0020\002*\0020\001H\b\032\r\020\003\032\0020\002*\0020\004H\b¨\006\005"}, d2={"toFile", "Ljava/io/File;", "Landroid/net/Uri;", "toUri", "", "core-ktx_release"}, k=2, mv={1, 6, 0}, xi=48)
public final class UriKt
{
  public static final File toFile(Uri paramUri)
  {
    Intrinsics.checkNotNullParameter(paramUri, "<this>");
    if (Intrinsics.areEqual(paramUri.getScheme(), "file"))
    {
      String str = paramUri.getPath();
      if (str != null) {
        return new File(str);
      }
      throw new IllegalArgumentException(("Uri path is null: " + paramUri).toString());
    }
    throw new IllegalArgumentException(("Uri lacks 'file' scheme: " + paramUri).toString());
  }
  
  public static final Uri toUri(File paramFile)
  {
    Intrinsics.checkNotNullParameter(paramFile, "<this>");
    paramFile = Uri.fromFile(paramFile);
    Intrinsics.checkNotNullExpressionValue(paramFile, "fromFile(this)");
    return paramFile;
  }
  
  public static final Uri toUri(String paramString)
  {
    Intrinsics.checkNotNullParameter(paramString, "<this>");
    paramString = Uri.parse(paramString);
    Intrinsics.checkNotNullExpressionValue(paramString, "parse(this)");
    return paramString;
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/core/net/UriKt.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */