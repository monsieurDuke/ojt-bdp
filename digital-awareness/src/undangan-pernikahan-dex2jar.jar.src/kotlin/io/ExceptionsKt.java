package kotlin.io;

import java.io.File;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

@Metadata(d1={"\000\020\n\000\n\002\020\016\n\000\n\002\030\002\n\002\b\003\032$\020\000\032\0020\0012\006\020\002\032\0020\0032\b\020\004\032\004\030\0010\0032\b\020\005\032\004\030\0010\001H\002¨\006\006"}, d2={"constructMessage", "", "file", "Ljava/io/File;", "other", "reason", "kotlin-stdlib"}, k=2, mv={1, 7, 1}, xi=48)
public final class ExceptionsKt
{
  private static final String constructMessage(File paramFile1, File paramFile2, String paramString)
  {
    paramFile1 = new StringBuilder(paramFile1.toString());
    if (paramFile2 != null) {
      paramFile1.append(" -> " + paramFile2);
    }
    if (paramString != null) {
      paramFile1.append(": " + paramString);
    }
    paramFile1 = paramFile1.toString();
    Intrinsics.checkNotNullExpressionValue(paramFile1, "sb.toString()");
    return paramFile1;
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlin/io/ExceptionsKt.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */