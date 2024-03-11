package kotlinx.coroutines;

import kotlin.ExceptionsKt;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.AbstractCoroutineContextElement;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.CoroutineContext.Key;
import kotlin.jvm.functions.Function2;

@Metadata(d1={"\000\034\n\000\n\002\030\002\n\000\n\002\030\002\n\002\030\002\n\002\020\003\n\002\020\002\n\002\b\007\032%\020\000\032\0020\0012\032\b\004\020\002\032\024\022\004\022\0020\004\022\004\022\0020\005\022\004\022\0020\0060\003H\b\032\030\020\007\032\0020\0062\006\020\b\032\0020\0042\006\020\t\032\0020\005H\007\032\030\020\n\032\0020\0052\006\020\013\032\0020\0052\006\020\f\032\0020\005H\000¨\006\r"}, d2={"CoroutineExceptionHandler", "Lkotlinx/coroutines/CoroutineExceptionHandler;", "handler", "Lkotlin/Function2;", "Lkotlin/coroutines/CoroutineContext;", "", "", "handleCoroutineException", "context", "exception", "handlerException", "originalException", "thrownException", "kotlinx-coroutines-core"}, k=2, mv={1, 6, 0}, xi=48)
public final class CoroutineExceptionHandlerKt
{
  public static final CoroutineExceptionHandler CoroutineExceptionHandler(Function2<? super CoroutineContext, ? super Throwable, Unit> paramFunction2)
  {
    (CoroutineExceptionHandler)new AbstractCoroutineContextElement(paramFunction2)
    {
      final Function2<CoroutineContext, Throwable, Unit> $handler;
      
      public void handleException(CoroutineContext paramAnonymousCoroutineContext, Throwable paramAnonymousThrowable)
      {
        this.$handler.invoke(paramAnonymousCoroutineContext, paramAnonymousThrowable);
      }
    };
  }
  
  public static final void handleCoroutineException(CoroutineContext paramCoroutineContext, Throwable paramThrowable)
  {
    try
    {
      CoroutineExceptionHandler localCoroutineExceptionHandler = (CoroutineExceptionHandler)paramCoroutineContext.get((CoroutineContext.Key)CoroutineExceptionHandler.Key);
      if (localCoroutineExceptionHandler == null)
      {
        CoroutineExceptionHandlerImplKt.handleCoroutineExceptionImpl(paramCoroutineContext, paramThrowable);
        return;
      }
      localCoroutineExceptionHandler.handleException(paramCoroutineContext, paramThrowable);
      return;
    }
    finally
    {
      CoroutineExceptionHandlerImplKt.handleCoroutineExceptionImpl(paramCoroutineContext, handlerException(paramThrowable, localThrowable));
    }
  }
  
  public static final Throwable handlerException(Throwable paramThrowable1, Throwable paramThrowable2)
  {
    if (paramThrowable1 == paramThrowable2) {
      return paramThrowable1;
    }
    paramThrowable2 = new RuntimeException("Exception while trying to handle coroutine exception", paramThrowable2);
    ExceptionsKt.addSuppressed((Throwable)paramThrowable2, paramThrowable1);
    return (Throwable)paramThrowable2;
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlinx/coroutines/CoroutineExceptionHandlerKt.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */