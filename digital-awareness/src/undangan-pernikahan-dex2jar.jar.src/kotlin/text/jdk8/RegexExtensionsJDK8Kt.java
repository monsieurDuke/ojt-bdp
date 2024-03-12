package kotlin.text.jdk8;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.MatchGroup;
import kotlin.text.MatchGroupCollection;
import kotlin.text.MatchNamedGroupCollection;

@Metadata(d1={"\000\022\n\000\n\002\030\002\n\002\030\002\n\000\n\002\020\016\n\000\032\027\020\000\032\004\030\0010\001*\0020\0022\006\020\003\032\0020\004H\002¨\006\005"}, d2={"get", "Lkotlin/text/MatchGroup;", "Lkotlin/text/MatchGroupCollection;", "name", "", "kotlin-stdlib-jdk8"}, k=2, mv={1, 6, 0}, pn="kotlin.text", xi=48)
public final class RegexExtensionsJDK8Kt
{
  public static final MatchGroup get(MatchGroupCollection paramMatchGroupCollection, String paramString)
  {
    Intrinsics.checkNotNullParameter(paramMatchGroupCollection, "<this>");
    Intrinsics.checkNotNullParameter(paramString, "name");
    if ((paramMatchGroupCollection instanceof MatchNamedGroupCollection)) {
      paramMatchGroupCollection = (MatchNamedGroupCollection)paramMatchGroupCollection;
    } else {
      paramMatchGroupCollection = null;
    }
    if (paramMatchGroupCollection != null) {
      return paramMatchGroupCollection.get(paramString);
    }
    throw new UnsupportedOperationException("Retrieving groups by name is not supported on this platform.");
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlin/text/jdk8/RegexExtensionsJDK8Kt.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */