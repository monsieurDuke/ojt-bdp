package kotlin.internal.jdk8;

import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import kotlin.Metadata;
import kotlin.internal.jdk7.JDK7PlatformImplementations;
import kotlin.jvm.internal.Intrinsics;
import kotlin.random.Random;
import kotlin.random.jdk8.PlatformThreadLocalRandom;
import kotlin.ranges.IntRange;
import kotlin.text.MatchGroup;

@Metadata(d1={"\000$\n\002\030\002\n\002\030\002\n\002\b\002\n\002\030\002\n\000\n\002\030\002\n\000\n\002\030\002\n\000\n\002\020\016\n\000\b\020\030\0002\0020\001B\005¢\006\002\020\002J\b\020\003\032\0020\004H\026J\032\020\005\032\004\030\0010\0062\006\020\007\032\0020\b2\006\020\t\032\0020\nH\026¨\006\013"}, d2={"Lkotlin/internal/jdk8/JDK8PlatformImplementations;", "Lkotlin/internal/jdk7/JDK7PlatformImplementations;", "()V", "defaultPlatformRandom", "Lkotlin/random/Random;", "getMatchResultNamedGroup", "Lkotlin/text/MatchGroup;", "matchResult", "Ljava/util/regex/MatchResult;", "name", "", "kotlin-stdlib-jdk8"}, k=1, mv={1, 6, 0}, xi=48)
public class JDK8PlatformImplementations
  extends JDK7PlatformImplementations
{
  public Random defaultPlatformRandom()
  {
    return (Random)new PlatformThreadLocalRandom();
  }
  
  public MatchGroup getMatchResultNamedGroup(MatchResult paramMatchResult, String paramString)
  {
    Intrinsics.checkNotNullParameter(paramMatchResult, "matchResult");
    Intrinsics.checkNotNullParameter(paramString, "name");
    boolean bool = paramMatchResult instanceof Matcher;
    Object localObject = null;
    if (bool) {
      paramMatchResult = (Matcher)paramMatchResult;
    } else {
      paramMatchResult = null;
    }
    if (paramMatchResult != null)
    {
      IntRange localIntRange = new IntRange(paramMatchResult.start(paramString), paramMatchResult.end(paramString) - 1);
      if (localIntRange.getStart().intValue() >= 0)
      {
        paramMatchResult = paramMatchResult.group(paramString);
        Intrinsics.checkNotNullExpressionValue(paramMatchResult, "matcher.group(name)");
        paramMatchResult = new MatchGroup(paramMatchResult, localIntRange);
      }
      else
      {
        paramMatchResult = (MatchResult)localObject;
      }
      return paramMatchResult;
    }
    throw new UnsupportedOperationException("Retrieving groups by name is not supported on this platform.");
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlin/internal/jdk8/JDK8PlatformImplementations.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */