package okhttp3.internal.platform.android;

import java.util.logging.Level;
import java.util.logging.LogRecord;
import kotlin.Metadata;

@Metadata(bv={1, 0, 3}, d1={"\000\016\n\000\n\002\020\b\n\002\030\002\n\002\b\003\"\030\020\000\032\0020\001*\0020\0028BX\004¢\006\006\032\004\b\003\020\004¨\006\005"}, d2={"androidLevel", "", "Ljava/util/logging/LogRecord;", "getAndroidLevel", "(Ljava/util/logging/LogRecord;)I", "okhttp"}, k=2, mv={1, 4, 0})
public final class AndroidLogKt
{
  private static final int getAndroidLevel(LogRecord paramLogRecord)
  {
    int i;
    if (paramLogRecord.getLevel().intValue() > Level.INFO.intValue()) {
      i = 5;
    } else if (paramLogRecord.getLevel().intValue() == Level.INFO.intValue()) {
      i = 4;
    } else {
      i = 3;
    }
    return i;
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/okhttp3/internal/platform/android/AndroidLogKt.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */