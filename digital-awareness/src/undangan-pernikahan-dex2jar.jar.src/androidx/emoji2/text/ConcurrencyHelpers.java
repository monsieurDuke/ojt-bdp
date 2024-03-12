package androidx.emoji2.text;

import android.os.Build.VERSION;
import android.os.Handler;
import android.os.Looper;
import java.util.Objects;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

class ConcurrencyHelpers
{
  private static final int FONT_LOAD_TIMEOUT_SECONDS = 15;
  
  @Deprecated
  static Executor convertHandlerToExecutor(Handler paramHandler)
  {
    Objects.requireNonNull(paramHandler);
    return new ConcurrencyHelpers..ExternalSyntheticLambda1(paramHandler);
  }
  
  static ThreadPoolExecutor createBackgroundPriorityExecutor(String paramString)
  {
    paramString = new ConcurrencyHelpers..ExternalSyntheticLambda0(paramString);
    paramString = new ThreadPoolExecutor(0, 1, 15L, TimeUnit.SECONDS, new LinkedBlockingDeque(), paramString);
    paramString.allowCoreThreadTimeOut(true);
    return paramString;
  }
  
  static Handler mainHandlerAsync()
  {
    if (Build.VERSION.SDK_INT >= 28) {
      return Handler28Impl.createAsync(Looper.getMainLooper());
    }
    return new Handler(Looper.getMainLooper());
  }
  
  static class Handler28Impl
  {
    public static Handler createAsync(Looper paramLooper)
    {
      return Handler.createAsync(paramLooper);
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/emoji2/text/ConcurrencyHelpers.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */