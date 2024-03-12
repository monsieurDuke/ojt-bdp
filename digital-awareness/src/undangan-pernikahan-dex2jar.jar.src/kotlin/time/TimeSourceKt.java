package kotlin.time;

import kotlin.Deprecated;
import kotlin.DeprecationLevel;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

@Metadata(d1={"\000\026\n\000\n\002\020\b\n\002\030\002\n\002\b\002\n\002\030\002\n\002\b\002\032\025\020\000\032\0020\001*\0020\0022\006\020\003\032\0020\002H\n\032\035\020\004\032\0020\005*\0020\0022\006\020\003\032\0020\002H\nø\001\000¢\006\002\020\006\002\004\n\002\b\031¨\006\007"}, d2={"compareTo", "", "Lkotlin/time/TimeMark;", "other", "minus", "Lkotlin/time/Duration;", "(Lkotlin/time/TimeMark;Lkotlin/time/TimeMark;)J", "kotlin-stdlib"}, k=2, mv={1, 7, 1}, xi=48)
public final class TimeSourceKt
{
  @Deprecated(level=DeprecationLevel.ERROR, message="Comparing one TimeMark to another is not a well defined operation because these time marks could have been obtained from the different time sources.")
  private static final int compareTo(TimeMark paramTimeMark1, TimeMark paramTimeMark2)
  {
    Intrinsics.checkNotNullParameter(paramTimeMark1, "<this>");
    Intrinsics.checkNotNullParameter(paramTimeMark2, "other");
    throw new Error("Operation is disallowed.");
  }
  
  @Deprecated(level=DeprecationLevel.ERROR, message="Subtracting one TimeMark from another is not a well defined operation because these time marks could have been obtained from the different time sources.")
  private static final long minus(TimeMark paramTimeMark1, TimeMark paramTimeMark2)
  {
    Intrinsics.checkNotNullParameter(paramTimeMark1, "<this>");
    Intrinsics.checkNotNullParameter(paramTimeMark2, "other");
    throw new Error("Operation is disallowed.");
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlin/time/TimeSourceKt.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */