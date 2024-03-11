package okhttp3.internal.platform.android;

import android.util.Log;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import kotlin.Metadata;
import kotlin.collections.MapsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import mt.Log229316;
import mt.Log5ECF72;
import mt.LogE84000;
import okhttp3.OkHttpClient;
import okhttp3.internal.concurrent.TaskRunner;
import okhttp3.internal.http2.Http2;

@Metadata(bv={1, 0, 3}, d1={"\0006\n\002\030\002\n\002\020\000\n\002\b\002\n\002\020\b\n\000\n\002\030\002\n\002\030\002\n\000\n\002\020$\n\002\020\016\n\000\n\002\020\002\n\002\b\004\n\002\020\003\n\002\b\007\bÇ\002\030\0002\0020\001B\007\b\002¢\006\002\020\002J/\020\013\032\0020\f2\006\020\r\032\0020\n2\006\020\016\032\0020\0042\006\020\017\032\0020\n2\b\020\020\032\004\030\0010\021H\000¢\006\002\b\022J\006\020\023\032\0020\fJ\030\020\024\032\0020\f2\006\020\025\032\0020\n2\006\020\026\032\0020\nH\002J\020\020\027\032\0020\n2\006\020\r\032\0020\nH\002R\016\020\003\032\0020\004XT¢\006\002\n\000R\024\020\005\032\b\022\004\022\0020\0070\006X\004¢\006\002\n\000R\032\020\b\032\016\022\004\022\0020\n\022\004\022\0020\n0\tX\004¢\006\002\n\000¨\006\030"}, d2={"Lokhttp3/internal/platform/android/AndroidLog;", "", "()V", "MAX_LOG_LENGTH", "", "configuredLoggers", "Ljava/util/concurrent/CopyOnWriteArraySet;", "Ljava/util/logging/Logger;", "knownLoggers", "", "", "androidLog", "", "loggerName", "logLevel", "message", "t", "", "androidLog$okhttp", "enable", "enableLogging", "logger", "tag", "loggerTag", "okhttp"}, k=1, mv={1, 4, 0})
public final class AndroidLog
{
  public static final AndroidLog INSTANCE = new AndroidLog();
  private static final int MAX_LOG_LENGTH = 4000;
  private static final CopyOnWriteArraySet<Logger> configuredLoggers = new CopyOnWriteArraySet();
  private static final Map<String, String> knownLoggers;
  
  static
  {
    LinkedHashMap localLinkedHashMap = new LinkedHashMap();
    Object localObject = OkHttpClient.class.getPackage();
    if (localObject != null) {
      localObject = ((Package)localObject).getName();
    } else {
      localObject = null;
    }
    if (localObject != null) {
      ((Map)localLinkedHashMap).put(localObject, "OkHttp");
    }
    localObject = (Map)localLinkedHashMap;
    String str = OkHttpClient.class.getName();
    Intrinsics.checkNotNullExpressionValue(str, "OkHttpClient::class.java.name");
    ((Map)localObject).put(str, "okhttp.OkHttpClient");
    localObject = (Map)localLinkedHashMap;
    str = Http2.class.getName();
    Intrinsics.checkNotNullExpressionValue(str, "Http2::class.java.name");
    ((Map)localObject).put(str, "okhttp.Http2");
    localObject = (Map)localLinkedHashMap;
    str = TaskRunner.class.getName();
    Intrinsics.checkNotNullExpressionValue(str, "TaskRunner::class.java.name");
    ((Map)localObject).put(str, "okhttp.TaskRunner");
    ((Map)localLinkedHashMap).put("okhttp3.mockwebserver.MockWebServer", "okhttp.MockWebServer");
    knownLoggers = MapsKt.toMap((Map)localLinkedHashMap);
  }
  
  private final void enableLogging(String paramString1, String paramString2)
  {
    Logger localLogger = Logger.getLogger(paramString1);
    if (configuredLoggers.add(localLogger))
    {
      Intrinsics.checkNotNullExpressionValue(localLogger, "logger");
      localLogger.setUseParentHandlers(false);
      if (Log.isLoggable(paramString2, 3)) {
        paramString1 = Level.FINE;
      } else if (Log.isLoggable(paramString2, 4)) {
        paramString1 = Level.INFO;
      } else {
        paramString1 = Level.WARNING;
      }
      localLogger.setLevel(paramString1);
      localLogger.addHandler((Handler)AndroidLogHandler.INSTANCE);
    }
  }
  
  private final String loggerTag(String paramString)
  {
    String str = (String)knownLoggers.get(paramString);
    if (str != null)
    {
      paramString = str;
    }
    else
    {
      paramString = StringsKt.take(paramString, 23);
      Log5ECF72.a(paramString);
      LogE84000.a(paramString);
      Log229316.a(paramString);
    }
    return paramString;
  }
  
  public final void androidLog$okhttp(String paramString1, int paramInt, String paramString2, Throwable paramThrowable)
  {
    Intrinsics.checkNotNullParameter(paramString1, "loggerName");
    Intrinsics.checkNotNullParameter(paramString2, "message");
    String str = loggerTag(paramString1);
    if (Log.isLoggable(str, paramInt))
    {
      paramString1 = paramString2;
      if (paramThrowable != null)
      {
        paramString1 = new StringBuilder().append(paramString2).append("\n");
        paramString2 = Log.getStackTraceString(paramThrowable);
        Log5ECF72.a(paramString2);
        LogE84000.a(paramString2);
        Log229316.a(paramString2);
        paramString1 = paramString2;
      }
      int i = 0;
      int m = paramString1.length();
      while (i < m)
      {
        int j = StringsKt.indexOf$default((CharSequence)paramString1, '\n', i, false, 4, null);
        if (j == -1) {
          j = m;
        }
        int k;
        do
        {
          k = Math.min(j, i + 4000);
          if (paramString1 == null) {
            break;
          }
          paramString2 = paramString1.substring(i, k);
          Intrinsics.checkNotNullExpressionValue(paramString2, "(this as java.lang.Strin…ing(startIndex, endIndex)");
          Log.println(paramInt, str, paramString2);
          i = k;
        } while (k < j);
        i = k + 1;
        continue;
        throw new NullPointerException("null cannot be cast to non-null type java.lang.String");
      }
    }
  }
  
  public final void enable()
  {
    Iterator localIterator = knownLoggers.entrySet().iterator();
    while (localIterator.hasNext())
    {
      Map.Entry localEntry = (Map.Entry)localIterator.next();
      enableLogging((String)localEntry.getKey(), (String)localEntry.getValue());
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/okhttp3/internal/platform/android/AndroidLog.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */