package kotlinx.coroutines;

import java.util.List;
import java.util.ServiceLoader;
import kotlin.Metadata;
import kotlin.sequences.SequencesKt;

@Metadata(d1={"\000\036\n\000\n\002\020 \n\002\030\002\n\000\n\002\020\002\n\000\n\002\030\002\n\000\n\002\020\003\n\000\032\030\020\003\032\0020\0042\006\020\005\032\0020\0062\006\020\007\032\0020\bH\000\"\024\020\000\032\b\022\004\022\0020\0020\001X\004¢\006\002\n\000¨\006\t"}, d2={"handlers", "", "Lkotlinx/coroutines/CoroutineExceptionHandler;", "handleCoroutineExceptionImpl", "", "context", "Lkotlin/coroutines/CoroutineContext;", "exception", "", "kotlinx-coroutines-core"}, k=2, mv={1, 6, 0}, xi=48)
public final class CoroutineExceptionHandlerImplKt
{
  private static final List<CoroutineExceptionHandler> handlers = SequencesKt.toList(SequencesKt.asSequence(ServiceLoader.load(CoroutineExceptionHandler.class, CoroutineExceptionHandler.class.getClassLoader()).iterator()));
  
  /* Error */
  public static final void handleCoroutineExceptionImpl(kotlin.coroutines.CoroutineContext paramCoroutineContext, Throwable paramThrowable)
  {
    // Byte code:
    //   0: getstatic 58	kotlinx/coroutines/CoroutineExceptionHandlerImplKt:handlers	Ljava/util/List;
    //   3: invokeinterface 63 1 0
    //   8: astore_2
    //   9: aload_2
    //   10: invokeinterface 69 1 0
    //   15: ifeq +49 -> 64
    //   18: aload_2
    //   19: invokeinterface 73 1 0
    //   24: checkcast 30	kotlinx/coroutines/CoroutineExceptionHandler
    //   27: astore_3
    //   28: aload_3
    //   29: aload_0
    //   30: aload_1
    //   31: invokeinterface 76 3 0
    //   36: goto -27 -> 9
    //   39: astore 4
    //   41: invokestatic 82	java/lang/Thread:currentThread	()Ljava/lang/Thread;
    //   44: astore_3
    //   45: aload_3
    //   46: invokevirtual 86	java/lang/Thread:getUncaughtExceptionHandler	()Ljava/lang/Thread$UncaughtExceptionHandler;
    //   49: aload_3
    //   50: aload_1
    //   51: aload 4
    //   53: invokestatic 92	kotlinx/coroutines/CoroutineExceptionHandlerKt:handlerException	(Ljava/lang/Throwable;Ljava/lang/Throwable;)Ljava/lang/Throwable;
    //   56: invokeinterface 98 3 0
    //   61: goto -52 -> 9
    //   64: invokestatic 82	java/lang/Thread:currentThread	()Ljava/lang/Thread;
    //   67: astore_2
    //   68: getstatic 104	kotlin/Result:Companion	Lkotlin/Result$Companion;
    //   71: astore_3
    //   72: new 106	kotlinx/coroutines/DiagnosticCoroutineContextException
    //   75: astore_3
    //   76: aload_3
    //   77: aload_0
    //   78: invokespecial 110	kotlinx/coroutines/DiagnosticCoroutineContextException:<init>	(Lkotlin/coroutines/CoroutineContext;)V
    //   81: aload_1
    //   82: aload_3
    //   83: checkcast 112	java/lang/Throwable
    //   86: invokestatic 118	kotlin/ExceptionsKt:addSuppressed	(Ljava/lang/Throwable;Ljava/lang/Throwable;)V
    //   89: getstatic 124	kotlin/Unit:INSTANCE	Lkotlin/Unit;
    //   92: invokestatic 128	kotlin/Result:constructor-impl	(Ljava/lang/Object;)Ljava/lang/Object;
    //   95: pop
    //   96: goto +16 -> 112
    //   99: astore_0
    //   100: getstatic 104	kotlin/Result:Companion	Lkotlin/Result$Companion;
    //   103: astore_3
    //   104: aload_0
    //   105: invokestatic 134	kotlin/ResultKt:createFailure	(Ljava/lang/Throwable;)Ljava/lang/Object;
    //   108: invokestatic 128	kotlin/Result:constructor-impl	(Ljava/lang/Object;)Ljava/lang/Object;
    //   111: pop
    //   112: aload_2
    //   113: invokevirtual 86	java/lang/Thread:getUncaughtExceptionHandler	()Ljava/lang/Thread$UncaughtExceptionHandler;
    //   116: aload_2
    //   117: aload_1
    //   118: invokeinterface 98 3 0
    //   123: return
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	124	0	paramCoroutineContext	kotlin.coroutines.CoroutineContext
    //   0	124	1	paramThrowable	Throwable
    //   8	109	2	localObject1	Object
    //   27	77	3	localObject2	Object
    //   39	13	4	localThrowable	Throwable
    // Exception table:
    //   from	to	target	type
    //   28	36	39	finally
    //   68	96	99	finally
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlinx/coroutines/CoroutineExceptionHandlerImplKt.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */