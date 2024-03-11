package okhttp3.internal.concurrent;

import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.InlineMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.StringCompanionObject;
import mt.Log229316;
import mt.Log5ECF72;
import mt.LogE84000;

@Metadata(bv={1, 0, 3}, d1={"\000*\n\000\n\002\020\016\n\000\n\002\020\t\n\000\n\002\020\002\n\000\n\002\030\002\n\000\n\002\030\002\n\002\b\004\n\002\030\002\n\002\b\004\032\016\020\000\032\0020\0012\006\020\002\032\0020\003\032 \020\004\032\0020\0052\006\020\006\032\0020\0072\006\020\b\032\0020\t2\006\020\n\032\0020\001H\002\0325\020\013\032\002H\f\"\004\b\000\020\f2\006\020\006\032\0020\0072\006\020\b\032\0020\t2\f\020\r\032\b\022\004\022\002H\f0\016H\bø\001\000¢\006\002\020\017\032*\020\020\032\0020\0052\006\020\006\032\0020\0072\006\020\b\032\0020\t2\f\020\021\032\b\022\004\022\0020\0010\016H\bø\001\000\002\007\n\005\b20\001¨\006\022"}, d2={"formatDuration", "", "ns", "", "log", "", "task", "Lokhttp3/internal/concurrent/Task;", "queue", "Lokhttp3/internal/concurrent/TaskQueue;", "message", "logElapsed", "T", "block", "Lkotlin/Function0;", "(Lokhttp3/internal/concurrent/Task;Lokhttp3/internal/concurrent/TaskQueue;Lkotlin/jvm/functions/Function0;)Ljava/lang/Object;", "taskLog", "messageBlock", "okhttp"}, k=2, mv={1, 4, 0})
public final class TaskLoggerKt
{
  public static final String formatDuration(long paramLong)
  {
    if (paramLong <= -999500000) {
      str = (paramLong - 500000000) / 1000000000 + " s ";
    } else if (paramLong <= -999500) {
      str = (paramLong - 500000) / 1000000 + " ms";
    } else if (paramLong <= 0L) {
      str = (paramLong - 'Ǵ') / 'Ϩ' + " µs";
    } else if (paramLong < 999500) {
      str = ('Ǵ' + paramLong) / 'Ϩ' + " µs";
    } else if (paramLong < 999500000) {
      str = (500000 + paramLong) / 1000000 + " ms";
    } else {
      str = (500000000 + paramLong) / 1000000000 + " s ";
    }
    StringCompanionObject localStringCompanionObject = StringCompanionObject.INSTANCE;
    String str = String.format("%6s", Arrays.copyOf(new Object[] { str }, 1));
    Log5ECF72.a(str);
    LogE84000.a(str);
    Log229316.a(str);
    Intrinsics.checkNotNullExpressionValue(str, "java.lang.String.format(format, *args)");
    return str;
  }
  
  private static final void log(Task paramTask, TaskQueue paramTaskQueue, String paramString)
  {
    Logger localLogger = TaskRunner.Companion.getLogger();
    paramTaskQueue = new StringBuilder().append(paramTaskQueue.getName$okhttp()).append(' ');
    StringCompanionObject localStringCompanionObject = StringCompanionObject.INSTANCE;
    paramString = String.format("%-22s", Arrays.copyOf(new Object[] { paramString }, 1));
    Log5ECF72.a(paramString);
    LogE84000.a(paramString);
    Log229316.a(paramString);
    Intrinsics.checkNotNullExpressionValue(paramString, "java.lang.String.format(format, *args)");
    localLogger.fine(paramString + ": " + paramTask.getName());
  }
  
  public static final <T> T logElapsed(Task paramTask, TaskQueue paramTaskQueue, Function0<? extends T> paramFunction0)
  {
    Intrinsics.checkNotNullParameter(paramTask, "task");
    Intrinsics.checkNotNullParameter(paramTaskQueue, "queue");
    Intrinsics.checkNotNullParameter(paramFunction0, "block");
    long l1 = -1L;
    boolean bool = TaskRunner.Companion.getLogger().isLoggable(Level.FINE);
    if (bool)
    {
      l1 = paramTaskQueue.getTaskRunner$okhttp().getBackend().nanoTime();
      access$log(paramTask, paramTaskQueue, "starting");
    }
    try
    {
      localObject2 = paramFunction0.invoke();
      InlineMarker.finallyStart(1);
      if (bool)
      {
        long l2 = paramTaskQueue.getTaskRunner$okhttp().getBackend().nanoTime();
        paramFunction0 = new StringBuilder().append("finished run in ");
        localObject1 = formatDuration(l2 - l1);
        Log5ECF72.a(localObject1);
        LogE84000.a(localObject1);
        Log229316.a(localObject1);
        access$log(paramTask, paramTaskQueue, (String)localObject1);
      }
      InlineMarker.finallyEnd(1);
      return (T)localObject2;
    }
    finally
    {
      Object localObject2;
      Object localObject1;
      InlineMarker.finallyStart(1);
      if (bool)
      {
        l1 = paramTaskQueue.getTaskRunner$okhttp().getBackend().nanoTime() - l1;
        if (0 != 0)
        {
          localObject1 = new StringBuilder().append("finished run in ");
          localObject2 = formatDuration(l1);
          Log5ECF72.a(localObject2);
          LogE84000.a(localObject2);
          Log229316.a(localObject2);
          access$log(paramTask, paramTaskQueue, (String)localObject2);
        }
        else
        {
          localObject2 = new StringBuilder().append("failed a run in ");
          localObject1 = formatDuration(l1);
          Log5ECF72.a(localObject1);
          LogE84000.a(localObject1);
          Log229316.a(localObject1);
          access$log(paramTask, paramTaskQueue, (String)localObject1);
        }
      }
      InlineMarker.finallyEnd(1);
    }
  }
  
  public static final void taskLog(Task paramTask, TaskQueue paramTaskQueue, Function0<String> paramFunction0)
  {
    Intrinsics.checkNotNullParameter(paramTask, "task");
    Intrinsics.checkNotNullParameter(paramTaskQueue, "queue");
    Intrinsics.checkNotNullParameter(paramFunction0, "messageBlock");
    if (TaskRunner.Companion.getLogger().isLoggable(Level.FINE)) {
      access$log(paramTask, paramTaskQueue, (String)paramFunction0.invoke());
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/okhttp3/internal/concurrent/TaskLoggerKt.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */