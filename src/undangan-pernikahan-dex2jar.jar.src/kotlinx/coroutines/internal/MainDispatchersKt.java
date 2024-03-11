package kotlinx.coroutines.internal;

import kotlin.KotlinNothingValueException;
import kotlin.Metadata;
import kotlinx.coroutines.MainCoroutineDispatcher;

@Metadata(d1={"\0006\n\000\n\002\020\016\n\000\n\002\020\013\n\002\b\003\n\002\030\002\n\000\n\002\020\003\n\002\b\002\n\002\020\001\n\000\n\002\030\002\n\000\n\002\030\002\n\000\n\002\020 \n\000\032 \020\006\032\0020\0072\n\b\002\020\b\032\004\030\0010\t2\n\b\002\020\n\032\004\030\0010\001H\002\032\b\020\013\032\0020\fH\000\032\f\020\r\032\0020\003*\0020\016H\007\032\032\020\017\032\0020\016*\0020\0202\f\020\021\032\b\022\004\022\0020\0200\022H\007\"\016\020\000\032\0020\001XT¢\006\002\n\000\"\024\020\002\032\0020\003XD¢\006\b\n\000\022\004\b\004\020\005¨\006\023"}, d2={"FAST_SERVICE_LOADER_PROPERTY_NAME", "", "SUPPORT_MISSING", "", "getSUPPORT_MISSING$annotations", "()V", "createMissingDispatcher", "Lkotlinx/coroutines/internal/MissingMainCoroutineDispatcher;", "cause", "", "errorHint", "throwMissingMainDispatcherException", "", "isMissing", "Lkotlinx/coroutines/MainCoroutineDispatcher;", "tryCreateDispatcher", "Lkotlinx/coroutines/internal/MainDispatcherFactory;", "factories", "", "kotlinx-coroutines-core"}, k=2, mv={1, 6, 0}, xi=48)
public final class MainDispatchersKt
{
  private static final String FAST_SERVICE_LOADER_PROPERTY_NAME = "kotlinx.coroutines.fast.service.loader";
  private static final boolean SUPPORT_MISSING = true;
  
  private static final MissingMainCoroutineDispatcher createMissingDispatcher(Throwable paramThrowable, String paramString)
  {
    if (SUPPORT_MISSING) {
      return new MissingMainCoroutineDispatcher(paramThrowable, paramString);
    }
    if (paramThrowable == null)
    {
      throwMissingMainDispatcherException();
      throw new KotlinNothingValueException();
    }
    throw paramThrowable;
  }
  
  public static final boolean isMissing(MainCoroutineDispatcher paramMainCoroutineDispatcher)
  {
    return paramMainCoroutineDispatcher.getImmediate() instanceof MissingMainCoroutineDispatcher;
  }
  
  public static final Void throwMissingMainDispatcherException()
  {
    throw new IllegalStateException("Module with the Main dispatcher is missing. Add dependency providing the Main dispatcher, e.g. 'kotlinx-coroutines-android' and ensure it has the same version as 'kotlinx-coroutines-core'");
  }
  
  /* Error */
  public static final MainCoroutineDispatcher tryCreateDispatcher(MainDispatcherFactory paramMainDispatcherFactory, java.util.List<? extends MainDispatcherFactory> paramList)
  {
    // Byte code:
    //   0: aload_0
    //   1: aload_1
    //   2: invokeinterface 80 2 0
    //   7: astore_1
    //   8: aload_1
    //   9: astore_0
    //   10: goto +18 -> 28
    //   13: astore_1
    //   14: aload_1
    //   15: aload_0
    //   16: invokeinterface 84 1 0
    //   21: invokestatic 58	kotlinx/coroutines/internal/MainDispatchersKt:createMissingDispatcher	(Ljava/lang/Throwable;Ljava/lang/String;)Lkotlinx/coroutines/internal/MissingMainCoroutineDispatcher;
    //   24: checkcast 61	kotlinx/coroutines/MainCoroutineDispatcher
    //   27: astore_0
    //   28: aload_0
    //   29: areturn
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	30	0	paramMainDispatcherFactory	MainDispatcherFactory
    //   0	30	1	paramList	java.util.List<? extends MainDispatcherFactory>
    // Exception table:
    //   from	to	target	type
    //   0	8	13	finally
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlinx/coroutines/internal/MainDispatchersKt.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */