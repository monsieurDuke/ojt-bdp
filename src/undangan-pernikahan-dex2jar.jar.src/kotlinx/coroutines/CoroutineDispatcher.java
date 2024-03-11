package kotlinx.coroutines;

import kotlin.Deprecated;
import kotlin.DeprecationLevel;
import kotlin.Metadata;
import kotlin.coroutines.AbstractCoroutineContextElement;
import kotlin.coroutines.AbstractCoroutineContextKey;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.ContinuationInterceptor;
import kotlin.coroutines.ContinuationInterceptor.DefaultImpls;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.CoroutineContext.Element;
import kotlin.coroutines.CoroutineContext.Key;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;
import kotlinx.coroutines.internal.DispatchedContinuation;
import kotlinx.coroutines.internal.LimitedDispatcher;
import kotlinx.coroutines.internal.LimitedDispatcherKt;
import mt.Log229316;
import mt.Log5ECF72;
import mt.LogE84000;

@Metadata(d1={"\000H\n\002\030\002\n\002\030\002\n\002\030\002\n\002\b\002\n\002\020\002\n\000\n\002\030\002\n\000\n\002\030\002\n\002\030\002\n\002\b\002\n\002\030\002\n\002\b\003\n\002\020\013\n\002\b\002\n\002\020\b\n\002\b\004\n\002\020\016\n\002\b\002\b&\030\000 \0322\0020\0012\0020\002:\001\032B\005¢\006\002\020\003J\034\020\004\032\0020\0052\006\020\006\032\0020\0072\n\020\b\032\0060\tj\002`\nH&J\034\020\013\032\0020\0052\006\020\006\032\0020\0072\n\020\b\032\0060\tj\002`\nH\027J \020\f\032\b\022\004\022\002H\0160\r\"\004\b\000\020\0162\f\020\017\032\b\022\004\022\002H\0160\rJ\020\020\020\032\0020\0212\006\020\006\032\0020\007H\026J\020\020\022\032\0020\0002\006\020\023\032\0020\024H\027J\021\020\025\032\0020\0002\006\020\026\032\0020\000H\002J\022\020\027\032\0020\0052\n\020\017\032\006\022\002\b\0030\rJ\b\020\030\032\0020\031H\026¨\006\033"}, d2={"Lkotlinx/coroutines/CoroutineDispatcher;", "Lkotlin/coroutines/AbstractCoroutineContextElement;", "Lkotlin/coroutines/ContinuationInterceptor;", "()V", "dispatch", "", "context", "Lkotlin/coroutines/CoroutineContext;", "block", "Ljava/lang/Runnable;", "Lkotlinx/coroutines/Runnable;", "dispatchYield", "interceptContinuation", "Lkotlin/coroutines/Continuation;", "T", "continuation", "isDispatchNeeded", "", "limitedParallelism", "parallelism", "", "plus", "other", "releaseInterceptedContinuation", "toString", "", "Key", "kotlinx-coroutines-core"}, k=1, mv={1, 6, 0}, xi=48)
public abstract class CoroutineDispatcher
  extends AbstractCoroutineContextElement
  implements ContinuationInterceptor
{
  public static final Key Key = new Key(null);
  
  public CoroutineDispatcher()
  {
    super((CoroutineContext.Key)ContinuationInterceptor.Key);
  }
  
  public abstract void dispatch(CoroutineContext paramCoroutineContext, Runnable paramRunnable);
  
  public void dispatchYield(CoroutineContext paramCoroutineContext, Runnable paramRunnable)
  {
    dispatch(paramCoroutineContext, paramRunnable);
  }
  
  public <E extends CoroutineContext.Element> E get(CoroutineContext.Key<E> paramKey)
  {
    return ContinuationInterceptor.DefaultImpls.get((ContinuationInterceptor)this, paramKey);
  }
  
  public final <T> Continuation<T> interceptContinuation(Continuation<? super T> paramContinuation)
  {
    return (Continuation)new DispatchedContinuation(this, paramContinuation);
  }
  
  public boolean isDispatchNeeded(CoroutineContext paramCoroutineContext)
  {
    return true;
  }
  
  public CoroutineDispatcher limitedParallelism(int paramInt)
  {
    LimitedDispatcherKt.checkParallelism(paramInt);
    return (CoroutineDispatcher)new LimitedDispatcher(this, paramInt);
  }
  
  public CoroutineContext minusKey(CoroutineContext.Key<?> paramKey)
  {
    return ContinuationInterceptor.DefaultImpls.minusKey((ContinuationInterceptor)this, paramKey);
  }
  
  @Deprecated(level=DeprecationLevel.ERROR, message="Operator '+' on two CoroutineDispatcher objects is meaningless. CoroutineDispatcher is a coroutine context element and `+` is a set-sum operator for coroutine contexts. The dispatcher to the right of `+` just replaces the dispatcher to the left.")
  public final CoroutineDispatcher plus(CoroutineDispatcher paramCoroutineDispatcher)
  {
    return paramCoroutineDispatcher;
  }
  
  public final void releaseInterceptedContinuation(Continuation<?> paramContinuation)
  {
    ((DispatchedContinuation)paramContinuation).release();
  }
  
  public String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    String str = DebugStringsKt.getClassSimpleName(this);
    Log5ECF72.a(str);
    LogE84000.a(str);
    Log229316.a(str);
    localStringBuilder = localStringBuilder.append(str).append('@');
    str = DebugStringsKt.getHexAddress(this);
    Log5ECF72.a(str);
    LogE84000.a(str);
    Log229316.a(str);
    return str;
  }
  
  @Metadata(d1={"\000\024\n\002\030\002\n\002\030\002\n\002\030\002\n\002\030\002\n\002\b\002\b\003\030\0002\016\022\004\022\0020\002\022\004\022\0020\0030\001B\007\b\002¢\006\002\020\004¨\006\005"}, d2={"Lkotlinx/coroutines/CoroutineDispatcher$Key;", "Lkotlin/coroutines/AbstractCoroutineContextKey;", "Lkotlin/coroutines/ContinuationInterceptor;", "Lkotlinx/coroutines/CoroutineDispatcher;", "()V", "kotlinx-coroutines-core"}, k=1, mv={1, 6, 0}, xi=48)
  public static final class Key
    extends AbstractCoroutineContextKey<ContinuationInterceptor, CoroutineDispatcher>
  {
    private Key()
    {
      super((Function1)1.INSTANCE);
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlinx/coroutines/CoroutineDispatcher.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */