package okhttp3.internal.platform.android;

import java.util.logging.Handler;
import java.util.logging.LogRecord;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

@Metadata(bv={1, 0, 3}, d1={"\000\032\n\002\030\002\n\002\030\002\n\002\b\002\n\002\020\002\n\002\b\003\n\002\030\002\n\000\bÆ\002\030\0002\0020\001B\007\b\002¢\006\002\020\002J\b\020\003\032\0020\004H\026J\b\020\005\032\0020\004H\026J\020\020\006\032\0020\0042\006\020\007\032\0020\bH\026¨\006\t"}, d2={"Lokhttp3/internal/platform/android/AndroidLogHandler;", "Ljava/util/logging/Handler;", "()V", "close", "", "flush", "publish", "record", "Ljava/util/logging/LogRecord;", "okhttp"}, k=1, mv={1, 4, 0})
public final class AndroidLogHandler
  extends Handler
{
  public static final AndroidLogHandler INSTANCE = new AndroidLogHandler();
  
  public void close() {}
  
  public void flush() {}
  
  public void publish(LogRecord paramLogRecord)
  {
    Intrinsics.checkNotNullParameter(paramLogRecord, "record");
    AndroidLog localAndroidLog = AndroidLog.INSTANCE;
    String str1 = paramLogRecord.getLoggerName();
    Intrinsics.checkNotNullExpressionValue(str1, "record.loggerName");
    int i = AndroidLogKt.access$getAndroidLevel$p(paramLogRecord);
    String str2 = paramLogRecord.getMessage();
    Intrinsics.checkNotNullExpressionValue(str2, "record.message");
    localAndroidLog.androidLog$okhttp(str1, i, str2, paramLogRecord.getThrown());
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/okhttp3/internal/platform/android/AndroidLogHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */