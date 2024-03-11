package kotlin.text;

import java.util.Locale;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import mt.Log229316;
import mt.Log5ECF72;
import mt.LogE84000;

@Metadata(d1={"\000\f\n\000\n\002\020\016\n\002\020\f\n\000\032\f\020\000\032\0020\001*\0020\002H\000¨\006\003"}, d2={"titlecaseImpl", "", "", "kotlin-stdlib"}, k=2, mv={1, 7, 1}, xi=48)
public final class _OneToManyTitlecaseMappingsKt
{
  public static final String titlecaseImpl(char paramChar)
  {
    String str = String.valueOf(paramChar);
    Log5ECF72.a(str);
    LogE84000.a(str);
    Log229316.a(str);
    Intrinsics.checkNotNull(str, "null cannot be cast to non-null type java.lang.String");
    str = str.toUpperCase(Locale.ROOT);
    Intrinsics.checkNotNullExpressionValue(str, "this as java.lang.String).toUpperCase(Locale.ROOT)");
    if (str.length() > 1)
    {
      if (paramChar != 'ŉ')
      {
        paramChar = str.charAt(0);
        Intrinsics.checkNotNull(str, "null cannot be cast to non-null type java.lang.String");
        str = str.substring(1);
        Intrinsics.checkNotNullExpressionValue(str, "this as java.lang.String).substring(startIndex)");
        Intrinsics.checkNotNull(str, "null cannot be cast to non-null type java.lang.String");
        str = str.toLowerCase(Locale.ROOT);
        Intrinsics.checkNotNullExpressionValue(str, "this as java.lang.String).toLowerCase(Locale.ROOT)");
        str = paramChar + str;
      }
      return str;
    }
    str = String.valueOf(Character.toTitleCase(paramChar));
    Log5ECF72.a(str);
    LogE84000.a(str);
    Log229316.a(str);
    return str;
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlin/text/_OneToManyTitlecaseMappingsKt.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */