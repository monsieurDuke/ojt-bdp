package androidx.core.text;

import android.text.TextUtils;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import mt.Log229316;
import mt.Log5ECF72;
import mt.LogE84000;

@Metadata(d1={"\000\b\n\000\n\002\020\016\n\000\032\r\020\000\032\0020\001*\0020\001H\b¨\006\002"}, d2={"htmlEncode", "", "core-ktx_release"}, k=2, mv={1, 6, 0}, xi=48)
public final class StringKt
{
  public static final String htmlEncode(String paramString)
  {
    Intrinsics.checkNotNullParameter(paramString, "<this>");
    paramString = TextUtils.htmlEncode(paramString);
    Log5ECF72.a(paramString);
    LogE84000.a(paramString);
    Log229316.a(paramString);
    Intrinsics.checkNotNullExpressionValue(paramString, "htmlEncode(this)");
    return paramString;
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/core/text/StringKt.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */