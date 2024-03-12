package kotlinx.coroutines.internal;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.CoroutineContext;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;
import kotlinx.coroutines.CoroutineExceptionHandlerKt;

@Metadata(d1={"\000(\n\000\n\002\030\002\n\002\020\003\n\002\020\002\n\000\n\002\030\002\n\002\b\002\n\002\030\002\n\002\b\004\n\002\030\002\n\002\b\004\032I\020\000\032\016\022\004\022\0020\002\022\004\022\0020\0030\001\"\004\b\000\020\004*\030\022\004\022\002H\004\022\004\022\0020\0030\001j\b\022\004\022\002H\004`\0052\006\020\006\032\002H\0042\006\020\007\032\0020\bH\000¢\006\002\020\t\032=\020\n\032\0020\003\"\004\b\000\020\004*\030\022\004\022\002H\004\022\004\022\0020\0030\001j\b\022\004\022\002H\004`\0052\006\020\006\032\002H\0042\006\020\007\032\0020\bH\000¢\006\002\020\013\032C\020\f\032\004\030\0010\r\"\004\b\000\020\004*\030\022\004\022\002H\004\022\004\022\0020\0030\001j\b\022\004\022\002H\004`\0052\006\020\006\032\002H\0042\n\b\002\020\016\032\004\030\0010\rH\000¢\006\002\020\017**\b\000\020\020\032\004\b\000\020\004\"\016\022\004\022\002H\004\022\004\022\0020\0030\0012\016\022\004\022\002H\004\022\004\022\0020\0030\001¨\006\021"}, d2={"bindCancellationFun", "Lkotlin/Function1;", "", "", "E", "Lkotlinx/coroutines/internal/OnUndeliveredElement;", "element", "context", "Lkotlin/coroutines/CoroutineContext;", "(Lkotlin/jvm/functions/Function1;Ljava/lang/Object;Lkotlin/coroutines/CoroutineContext;)Lkotlin/jvm/functions/Function1;", "callUndeliveredElement", "(Lkotlin/jvm/functions/Function1;Ljava/lang/Object;Lkotlin/coroutines/CoroutineContext;)V", "callUndeliveredElementCatchingException", "Lkotlinx/coroutines/internal/UndeliveredElementException;", "undeliveredElementException", "(Lkotlin/jvm/functions/Function1;Ljava/lang/Object;Lkotlinx/coroutines/internal/UndeliveredElementException;)Lkotlinx/coroutines/internal/UndeliveredElementException;", "OnUndeliveredElement", "kotlinx-coroutines-core"}, k=2, mv={1, 6, 0}, xi=48)
public final class OnUndeliveredElementKt
{
  public static final <E> Function1<Throwable, Unit> bindCancellationFun(Function1<? super E, Unit> paramFunction1, final E paramE, final CoroutineContext paramCoroutineContext)
  {
    (Function1)new Lambda(paramFunction1)
    {
      final Function1<E, Unit> $this_bindCancellationFun;
      
      public final void invoke(Throwable paramAnonymousThrowable)
      {
        OnUndeliveredElementKt.callUndeliveredElement(this.$this_bindCancellationFun, paramE, paramCoroutineContext);
      }
    };
  }
  
  public static final <E> void callUndeliveredElement(Function1<? super E, Unit> paramFunction1, E paramE, CoroutineContext paramCoroutineContext)
  {
    paramFunction1 = callUndeliveredElementCatchingException(paramFunction1, paramE, null);
    if (paramFunction1 != null) {
      CoroutineExceptionHandlerKt.handleCoroutineException(paramCoroutineContext, (Throwable)paramFunction1);
    }
  }
  
  /* Error */
  public static final <E> UndeliveredElementException callUndeliveredElementCatchingException(Function1<? super E, Unit> paramFunction1, E paramE, UndeliveredElementException paramUndeliveredElementException)
  {
    // Byte code:
    //   0: aload_0
    //   1: aload_1
    //   2: invokeinterface 59 2 0
    //   7: pop
    //   8: goto +24 -> 32
    //   11: astore_0
    //   12: aload_2
    //   13: ifnull +21 -> 34
    //   16: aload_2
    //   17: invokevirtual 65	kotlinx/coroutines/internal/UndeliveredElementException:getCause	()Ljava/lang/Throwable;
    //   20: aload_0
    //   21: if_acmpeq +13 -> 34
    //   24: aload_2
    //   25: checkcast 48	java/lang/Throwable
    //   28: aload_0
    //   29: invokestatic 71	kotlin/ExceptionsKt:addSuppressed	(Ljava/lang/Throwable;Ljava/lang/Throwable;)V
    //   32: aload_2
    //   33: areturn
    //   34: ldc 73
    //   36: aload_1
    //   37: invokestatic 79	kotlin/jvm/internal/Intrinsics:stringPlus	(Ljava/lang/String;Ljava/lang/Object;)Ljava/lang/String;
    //   40: astore_1
    //   41: aload_1
    //   42: invokestatic 85	mt/Log5ECF72:a	(Ljava/lang/Object;)V
    //   45: aload_1
    //   46: invokestatic 88	mt/LogE84000:a	(Ljava/lang/Object;)V
    //   49: aload_1
    //   50: invokestatic 91	mt/Log229316:a	(Ljava/lang/Object;)V
    //   53: new 61	kotlinx/coroutines/internal/UndeliveredElementException
    //   56: dup
    //   57: aload_1
    //   58: aload_0
    //   59: invokespecial 94	kotlinx/coroutines/internal/UndeliveredElementException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   62: areturn
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	63	0	paramFunction1	Function1<? super E, Unit>
    //   0	63	1	paramE	E
    //   0	63	2	paramUndeliveredElementException	UndeliveredElementException
    // Exception table:
    //   from	to	target	type
    //   0	8	11	finally
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlinx/coroutines/internal/OnUndeliveredElementKt.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */