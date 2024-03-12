package kotlin.text;

import java.util.regex.Pattern;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

@Metadata(d1={"\000\f\n\000\n\002\030\002\n\002\030\002\n\000\032\r\020\000\032\0020\001*\0020\002H\b¨\006\003"}, d2={"toRegex", "Lkotlin/text/Regex;", "Ljava/util/regex/Pattern;", "kotlin-stdlib"}, k=5, mv={1, 7, 1}, xi=49, xs="kotlin/text/StringsKt")
class StringsKt__RegexExtensionsJVMKt
  extends StringsKt__IndentKt
{
  private static final Regex toRegex(Pattern paramPattern)
  {
    Intrinsics.checkNotNullParameter(paramPattern, "<this>");
    return new Regex(paramPattern);
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlin/text/StringsKt__RegexExtensionsJVMKt.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */