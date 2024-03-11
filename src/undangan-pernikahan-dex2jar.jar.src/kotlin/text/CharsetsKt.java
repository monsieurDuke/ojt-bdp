package kotlin.text;

import java.nio.charset.Charset;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

@Metadata(d1={"\000\016\n\000\n\002\030\002\n\000\n\002\020\016\n\000\032\021\020\000\032\0020\0012\006\020\002\032\0020\003H\b¨\006\004"}, d2={"charset", "Ljava/nio/charset/Charset;", "charsetName", "", "kotlin-stdlib"}, k=2, mv={1, 7, 1}, xi=48)
public final class CharsetsKt
{
  private static final Charset charset(String paramString)
  {
    Intrinsics.checkNotNullParameter(paramString, "charsetName");
    paramString = Charset.forName(paramString);
    Intrinsics.checkNotNullExpressionValue(paramString, "forName(charsetName)");
    return paramString;
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlin/text/CharsetsKt.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */