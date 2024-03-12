package kotlin.time.jdk8;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.time.DurationKt;
import kotlin.time.DurationUnit;

@Metadata(d1={"\000\016\n\000\n\002\030\002\n\002\030\002\n\002\b\005\032\032\020\000\032\0020\001*\0020\002H\bø\001\000ø\001\001¢\006\004\b\003\020\004\032\025\020\005\032\0020\002*\0020\001H\bø\001\000¢\006\002\020\006\002\013\n\002\b\031\n\005\b¡\0360\001¨\006\007"}, d2={"toJavaDuration", "Ljava/time/Duration;", "Lkotlin/time/Duration;", "toJavaDuration-LRDsOJo", "(J)Ljava/time/Duration;", "toKotlinDuration", "(Ljava/time/Duration;)J", "kotlin-stdlib-jdk8"}, k=2, mv={1, 6, 0}, pn="kotlin.time", xi=48)
public final class DurationConversionsJDK8Kt
{
  private static final java.time.Duration toJavaDuration-LRDsOJo(long paramLong)
  {
    java.time.Duration localDuration = java.time.Duration.ofSeconds(kotlin.time.Duration.getInWholeSeconds-impl(paramLong), kotlin.time.Duration.getNanosecondsComponent-impl(paramLong));
    Intrinsics.checkNotNullExpressionValue(localDuration, "toJavaDuration-LRDsOJo");
    return localDuration;
  }
  
  private static final long toKotlinDuration(java.time.Duration paramDuration)
  {
    Intrinsics.checkNotNullParameter(paramDuration, "<this>");
    return kotlin.time.Duration.plus-LRDsOJo(DurationKt.toDuration(paramDuration.getSeconds(), DurationUnit.SECONDS), DurationKt.toDuration(paramDuration.getNano(), DurationUnit.NANOSECONDS));
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlin/time/jdk8/DurationConversionsJDK8Kt.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */