package kotlin.time;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;

@Metadata(d1={"\000(\n\000\n\002\030\002\n\000\n\002\030\002\n\002\020\002\n\002\b\002\n\002\030\002\n\000\n\002\030\002\n\000\n\002\030\002\n\002\b\002\032/\020\000\032\0020\0012\f\020\002\032\b\022\004\022\0020\0040\003H\bø\001\000ø\001\001\002\n\n\b\b\001\022\002\020\001 \001¢\006\002\020\005\0323\020\006\032\b\022\004\022\002H\b0\007\"\004\b\000\020\b2\f\020\002\032\b\022\004\022\002H\b0\003H\bø\001\000\002\n\n\b\b\001\022\002\020\001 \001\0323\020\000\032\0020\001*\0020\t2\f\020\002\032\b\022\004\022\0020\0040\003H\bø\001\000ø\001\001\002\n\n\b\b\001\022\002\020\001 \001¢\006\002\020\n\0323\020\000\032\0020\001*\0020\0132\f\020\002\032\b\022\004\022\0020\0040\003H\bø\001\000ø\001\001\002\n\n\b\b\001\022\002\020\001 \001¢\006\002\020\f\0327\020\006\032\b\022\004\022\002H\b0\007\"\004\b\000\020\b*\0020\t2\f\020\002\032\b\022\004\022\002H\b0\003H\bø\001\000\002\n\n\b\b\001\022\002\020\001 \001\0327\020\006\032\b\022\004\022\002H\b0\007\"\004\b\000\020\b*\0020\0132\f\020\002\032\b\022\004\022\002H\b0\003H\bø\001\000\002\n\n\b\b\001\022\002\020\001 \001\002\013\n\005\b20\001\n\002\b\031¨\006\r"}, d2={"measureTime", "Lkotlin/time/Duration;", "block", "Lkotlin/Function0;", "", "(Lkotlin/jvm/functions/Function0;)J", "measureTimedValue", "Lkotlin/time/TimedValue;", "T", "Lkotlin/time/TimeSource;", "(Lkotlin/time/TimeSource;Lkotlin/jvm/functions/Function0;)J", "Lkotlin/time/TimeSource$Monotonic;", "(Lkotlin/time/TimeSource$Monotonic;Lkotlin/jvm/functions/Function0;)J", "kotlin-stdlib"}, k=2, mv={1, 7, 1}, xi=48)
public final class MeasureTimeKt
{
  public static final long measureTime(Function0<Unit> paramFunction0)
  {
    Intrinsics.checkNotNullParameter(paramFunction0, "block");
    TimeSource.Monotonic localMonotonic = TimeSource.Monotonic.INSTANCE;
    long l = localMonotonic.markNow-z9LOYto();
    paramFunction0.invoke();
    return TimeSource.Monotonic.ValueTimeMark.elapsedNow-UwyO8pc(l);
  }
  
  public static final long measureTime(TimeSource.Monotonic paramMonotonic, Function0<Unit> paramFunction0)
  {
    Intrinsics.checkNotNullParameter(paramMonotonic, "<this>");
    Intrinsics.checkNotNullParameter(paramFunction0, "block");
    long l = paramMonotonic.markNow-z9LOYto();
    paramFunction0.invoke();
    return TimeSource.Monotonic.ValueTimeMark.elapsedNow-UwyO8pc(l);
  }
  
  public static final long measureTime(TimeSource paramTimeSource, Function0<Unit> paramFunction0)
  {
    Intrinsics.checkNotNullParameter(paramTimeSource, "<this>");
    Intrinsics.checkNotNullParameter(paramFunction0, "block");
    paramTimeSource = paramTimeSource.markNow();
    paramFunction0.invoke();
    return paramTimeSource.elapsedNow-UwyO8pc();
  }
  
  public static final <T> TimedValue<T> measureTimedValue(Function0<? extends T> paramFunction0)
  {
    Intrinsics.checkNotNullParameter(paramFunction0, "block");
    TimeSource.Monotonic localMonotonic = TimeSource.Monotonic.INSTANCE;
    long l = localMonotonic.markNow-z9LOYto();
    return new TimedValue(paramFunction0.invoke(), TimeSource.Monotonic.ValueTimeMark.elapsedNow-UwyO8pc(l), null);
  }
  
  public static final <T> TimedValue<T> measureTimedValue(TimeSource.Monotonic paramMonotonic, Function0<? extends T> paramFunction0)
  {
    Intrinsics.checkNotNullParameter(paramMonotonic, "<this>");
    Intrinsics.checkNotNullParameter(paramFunction0, "block");
    long l = paramMonotonic.markNow-z9LOYto();
    return new TimedValue(paramFunction0.invoke(), TimeSource.Monotonic.ValueTimeMark.elapsedNow-UwyO8pc(l), null);
  }
  
  public static final <T> TimedValue<T> measureTimedValue(TimeSource paramTimeSource, Function0<? extends T> paramFunction0)
  {
    Intrinsics.checkNotNullParameter(paramTimeSource, "<this>");
    Intrinsics.checkNotNullParameter(paramFunction0, "block");
    paramTimeSource = paramTimeSource.markNow();
    return new TimedValue(paramFunction0.invoke(), paramTimeSource.elapsedNow-UwyO8pc(), null);
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlin/time/MeasureTimeKt.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */