package okio;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.util.concurrent.TimeUnit;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.InlineMarker;
import kotlin.jvm.internal.Intrinsics;

@Metadata(bv={1, 0, 3}, d1={"\0000\n\002\030\002\n\002\020\000\n\002\b\002\n\002\020\t\n\000\n\002\020\013\n\002\b\006\n\002\030\002\n\000\n\002\020\002\n\002\b\002\n\002\030\002\n\002\b\006\b\026\030\000 \0272\0020\001:\001\027B\005¢\006\002\020\002J\b\020\b\032\0020\000H\026J\b\020\t\032\0020\000H\026J\026\020\n\032\0020\0002\006\020\013\032\0020\0042\006\020\f\032\0020\rJ\b\020\003\032\0020\004H\026J\020\020\003\032\0020\0002\006\020\003\032\0020\004H\026J\b\020\005\032\0020\006H\026J\"\020\016\032\0020\0172\006\020\020\032\0020\0002\f\020\021\032\b\022\004\022\0020\0170\022H\bø\001\000J\b\020\023\032\0020\017H\026J\030\020\024\032\0020\0002\006\020\024\032\0020\0042\006\020\f\032\0020\rH\026J\b\020\007\032\0020\004H\026J\016\020\025\032\0020\0172\006\020\026\032\0020\001R\016\020\003\032\0020\004X\016¢\006\002\n\000R\016\020\005\032\0020\006X\016¢\006\002\n\000R\016\020\007\032\0020\004X\016¢\006\002\n\000\002\007\n\005\b20\001¨\006\030"}, d2={"Lokio/Timeout;", "", "()V", "deadlineNanoTime", "", "hasDeadline", "", "timeoutNanos", "clearDeadline", "clearTimeout", "deadline", "duration", "unit", "Ljava/util/concurrent/TimeUnit;", "intersectWith", "", "other", "block", "Lkotlin/Function0;", "throwIfReached", "timeout", "waitUntilNotified", "monitor", "Companion", "okio"}, k=1, mv={1, 4, 0})
public class Timeout
{
  public static final Companion Companion = new Companion(null);
  public static final Timeout NONE = (Timeout)new Timeout.Companion.NONE.1();
  private long deadlineNanoTime;
  private boolean hasDeadline;
  private long timeoutNanos;
  
  public Timeout clearDeadline()
  {
    this.hasDeadline = false;
    return this;
  }
  
  public Timeout clearTimeout()
  {
    this.timeoutNanos = 0L;
    return this;
  }
  
  public final Timeout deadline(long paramLong, TimeUnit paramTimeUnit)
  {
    Intrinsics.checkNotNullParameter(paramTimeUnit, "unit");
    int i;
    if (paramLong > 0L) {
      i = 1;
    } else {
      i = 0;
    }
    if (i != 0) {
      return deadlineNanoTime(System.nanoTime() + paramTimeUnit.toNanos(paramLong));
    }
    throw ((Throwable)new IllegalArgumentException(("duration <= 0: " + paramLong).toString()));
  }
  
  public long deadlineNanoTime()
  {
    if (this.hasDeadline) {
      return this.deadlineNanoTime;
    }
    throw ((Throwable)new IllegalStateException("No deadline".toString()));
  }
  
  public Timeout deadlineNanoTime(long paramLong)
  {
    this.hasDeadline = true;
    this.deadlineNanoTime = paramLong;
    return this;
  }
  
  public boolean hasDeadline()
  {
    return this.hasDeadline;
  }
  
  public final void intersectWith(Timeout paramTimeout, Function0<Unit> paramFunction0)
  {
    Intrinsics.checkNotNullParameter(paramTimeout, "other");
    Intrinsics.checkNotNullParameter(paramFunction0, "block");
    long l2 = timeoutNanos();
    timeout(Companion.minTimeout(paramTimeout.timeoutNanos(), timeoutNanos()), TimeUnit.NANOSECONDS);
    long l1;
    if (hasDeadline())
    {
      l1 = deadlineNanoTime();
      if (paramTimeout.hasDeadline()) {
        deadlineNanoTime(Math.min(deadlineNanoTime(), paramTimeout.deadlineNanoTime()));
      }
    }
    try
    {
      paramFunction0.invoke();
      InlineMarker.finallyStart(1);
      timeout(l2, TimeUnit.NANOSECONDS);
      if (paramTimeout.hasDeadline()) {
        deadlineNanoTime(l1);
      }
      InlineMarker.finallyEnd(1);
    }
    finally
    {
      InlineMarker.finallyStart(1);
      timeout(l2, TimeUnit.NANOSECONDS);
      if (paramTimeout.hasDeadline()) {
        deadlineNanoTime(l1);
      }
      InlineMarker.finallyEnd(1);
    }
    try
    {
      paramFunction0.invoke();
      return;
    }
    finally
    {
      InlineMarker.finallyStart(1);
      timeout(l2, TimeUnit.NANOSECONDS);
      if (paramTimeout.hasDeadline()) {
        clearDeadline();
      }
      InlineMarker.finallyEnd(1);
    }
  }
  
  public void throwIfReached()
    throws IOException
  {
    if (!Thread.interrupted())
    {
      if ((this.hasDeadline) && (this.deadlineNanoTime - System.nanoTime() <= 0L)) {
        throw ((Throwable)new InterruptedIOException("deadline reached"));
      }
      return;
    }
    Thread.currentThread().interrupt();
    throw ((Throwable)new InterruptedIOException("interrupted"));
  }
  
  public Timeout timeout(long paramLong, TimeUnit paramTimeUnit)
  {
    Intrinsics.checkNotNullParameter(paramTimeUnit, "unit");
    int i;
    if (paramLong >= 0L) {
      i = 1;
    } else {
      i = 0;
    }
    if (i != 0)
    {
      this.timeoutNanos = paramTimeUnit.toNanos(paramLong);
      return this;
    }
    throw ((Throwable)new IllegalArgumentException(("timeout < 0: " + paramLong).toString()));
  }
  
  public long timeoutNanos()
  {
    return this.timeoutNanos;
  }
  
  public final void waitUntilNotified(Object paramObject)
    throws InterruptedIOException
  {
    Intrinsics.checkNotNullParameter(paramObject, "monitor");
    try
    {
      boolean bool = hasDeadline();
      long l1 = timeoutNanos();
      if ((!bool) && (l1 == 0L))
      {
        paramObject.wait();
        return;
      }
      long l3 = System.nanoTime();
      if ((bool) && (l1 != 0L)) {
        l1 = Math.min(l1, deadlineNanoTime() - l3);
      } else if (bool) {
        l1 = deadlineNanoTime() - l3;
      }
      long l2 = 0L;
      if (l1 > 0L)
      {
        l2 = l1 / 1000000L;
        paramObject.wait(l2, (int)(l1 - 1000000L * l2));
        l2 = System.nanoTime() - l3;
      }
      if (l2 < l1) {
        return;
      }
      paramObject = new java/io/InterruptedIOException;
      ((InterruptedIOException)paramObject).<init>("timeout");
      throw ((Throwable)paramObject);
    }
    catch (InterruptedException paramObject)
    {
      Thread.currentThread().interrupt();
      throw ((Throwable)new InterruptedIOException("interrupted"));
    }
  }
  
  @Metadata(bv={1, 0, 3}, d1={"\000\032\n\002\030\002\n\002\020\000\n\002\b\002\n\002\030\002\n\000\n\002\020\t\n\002\b\003\b\003\030\0002\0020\001B\007\b\002¢\006\002\020\002J\026\020\005\032\0020\0062\006\020\007\032\0020\0062\006\020\b\032\0020\006R\020\020\003\032\0020\0048\006X\004¢\006\002\n\000¨\006\t"}, d2={"Lokio/Timeout$Companion;", "", "()V", "NONE", "Lokio/Timeout;", "minTimeout", "", "aNanos", "bNanos", "okio"}, k=1, mv={1, 4, 0})
  public static final class Companion
  {
    public final long minTimeout(long paramLong1, long paramLong2)
    {
      if ((paramLong1 == 0L) || ((paramLong2 == 0L) || (paramLong1 >= paramLong2))) {
        paramLong1 = paramLong2;
      }
      return paramLong1;
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/okio/Timeout.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */