package kotlinx.coroutines;

import java.util.concurrent.atomic.AtomicLong;
import kotlin.Metadata;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.ContinuationInterceptor;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.CoroutineContext.Element;
import kotlin.coroutines.CoroutineContext.Key;
import kotlin.coroutines.EmptyCoroutineContext;
import kotlin.coroutines.jvm.internal.CoroutineStackFrame;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.InlineMarker;
import kotlin.jvm.internal.Lambda;
import kotlin.jvm.internal.Ref.ObjectRef;
import kotlinx.coroutines.internal.ThreadContextKt;

@Metadata(d1={"\000>\n\000\n\002\020\016\n\000\n\002\030\002\n\002\b\006\n\002\020\013\n\002\b\003\n\002\030\002\n\000\n\002\020\000\n\000\n\002\030\002\n\002\b\007\n\002\030\002\n\000\n\002\030\002\n\002\030\002\n\002\b\003\032 \020\006\032\0020\0032\006\020\007\032\0020\0032\006\020\b\032\0020\0032\006\020\t\032\0020\nH\002\0328\020\013\032\002H\f\"\004\b\000\020\f2\n\020\r\032\006\022\002\b\0030\0162\b\020\017\032\004\030\0010\0202\f\020\021\032\b\022\004\022\002H\f0\022H\b¢\006\002\020\023\0324\020\024\032\002H\f\"\004\b\000\020\f2\006\020\025\032\0020\0032\b\020\017\032\004\030\0010\0202\f\020\021\032\b\022\004\022\002H\f0\022H\b¢\006\002\020\026\032\f\020\027\032\0020\n*\0020\003H\002\032\024\020\030\032\0020\003*\0020\0032\006\020\031\032\0020\003H\007\032\024\020\030\032\0020\003*\0020\0322\006\020\025\032\0020\003H\007\032\023\020\033\032\b\022\002\b\003\030\0010\034*\0020\035H\020\032(\020\036\032\b\022\002\b\003\030\0010\034*\006\022\002\b\0030\0162\006\020\025\032\0020\0032\b\020\037\032\004\030\0010\020H\000\"\016\020\000\032\0020\001XT¢\006\002\n\000\"\032\020\002\032\004\030\0010\001*\0020\0038@X\004¢\006\006\032\004\b\004\020\005¨\006 "}, d2={"DEBUG_THREAD_NAME_SEPARATOR", "", "coroutineName", "Lkotlin/coroutines/CoroutineContext;", "getCoroutineName", "(Lkotlin/coroutines/CoroutineContext;)Ljava/lang/String;", "foldCopies", "originalContext", "appendContext", "isNewCoroutine", "", "withContinuationContext", "T", "continuation", "Lkotlin/coroutines/Continuation;", "countOrElement", "", "block", "Lkotlin/Function0;", "(Lkotlin/coroutines/Continuation;Ljava/lang/Object;Lkotlin/jvm/functions/Function0;)Ljava/lang/Object;", "withCoroutineContext", "context", "(Lkotlin/coroutines/CoroutineContext;Ljava/lang/Object;Lkotlin/jvm/functions/Function0;)Ljava/lang/Object;", "hasCopyableElements", "newCoroutineContext", "addedContext", "Lkotlinx/coroutines/CoroutineScope;", "undispatchedCompletion", "Lkotlinx/coroutines/UndispatchedCoroutine;", "Lkotlin/coroutines/jvm/internal/CoroutineStackFrame;", "updateUndispatchedCompletion", "oldValue", "kotlinx-coroutines-core"}, k=2, mv={1, 6, 0}, xi=48)
public final class CoroutineContextKt
{
  private static final String DEBUG_THREAD_NAME_SEPARATOR = " @";
  
  private static final CoroutineContext foldCopies(CoroutineContext paramCoroutineContext1, CoroutineContext paramCoroutineContext2, final boolean paramBoolean)
  {
    boolean bool1 = hasCopyableElements(paramCoroutineContext1);
    boolean bool2 = hasCopyableElements(paramCoroutineContext2);
    if ((!bool1) && (!bool2)) {
      return paramCoroutineContext1.plus(paramCoroutineContext2);
    }
    Ref.ObjectRef localObjectRef = new Ref.ObjectRef();
    localObjectRef.element = paramCoroutineContext2;
    paramCoroutineContext1 = (CoroutineContext)paramCoroutineContext1.fold(EmptyCoroutineContext.INSTANCE, (Function2)new Lambda(localObjectRef)
    {
      final Ref.ObjectRef<CoroutineContext> $leftoverContext;
      
      public final CoroutineContext invoke(CoroutineContext paramAnonymousCoroutineContext, CoroutineContext.Element paramAnonymousElement)
      {
        if (!(paramAnonymousElement instanceof CopyableThreadContextElement)) {
          return paramAnonymousCoroutineContext.plus((CoroutineContext)paramAnonymousElement);
        }
        CoroutineContext.Element localElement = ((CoroutineContext)this.$leftoverContext.element).get(paramAnonymousElement.getKey());
        if (localElement == null)
        {
          if (paramBoolean) {
            paramAnonymousElement = ((CopyableThreadContextElement)paramAnonymousElement).copyForChild();
          } else {
            paramAnonymousElement = (CopyableThreadContextElement)paramAnonymousElement;
          }
          return paramAnonymousCoroutineContext.plus((CoroutineContext)paramAnonymousElement);
        }
        Ref.ObjectRef localObjectRef = this.$leftoverContext;
        localObjectRef.element = ((CoroutineContext)localObjectRef.element).minusKey(paramAnonymousElement.getKey());
        return paramAnonymousCoroutineContext.plus(((CopyableThreadContextElement)paramAnonymousElement).mergeForChild(localElement));
      }
    });
    if (bool2) {
      localObjectRef.element = ((CoroutineContext)localObjectRef.element).fold(EmptyCoroutineContext.INSTANCE, (Function2)foldCopies.1.INSTANCE);
    }
    return paramCoroutineContext1.plus((CoroutineContext)localObjectRef.element);
  }
  
  public static final String getCoroutineName(CoroutineContext paramCoroutineContext)
  {
    if (!DebugKt.getDEBUG()) {
      return null;
    }
    CoroutineId localCoroutineId = (CoroutineId)paramCoroutineContext.get((CoroutineContext.Key)CoroutineId.Key);
    if (localCoroutineId == null) {
      return null;
    }
    Object localObject = (CoroutineName)paramCoroutineContext.get((CoroutineContext.Key)CoroutineName.Key);
    paramCoroutineContext = "coroutine";
    if (localObject != null)
    {
      do
      {
        localObject = ((CoroutineName)localObject).getName();
      } while (localObject == null);
      paramCoroutineContext = (CoroutineContext)localObject;
    }
    return paramCoroutineContext + '#' + localCoroutineId.getId();
  }
  
  private static final boolean hasCopyableElements(CoroutineContext paramCoroutineContext)
  {
    return ((Boolean)paramCoroutineContext.fold(Boolean.valueOf(false), (Function2)hasCopyableElements.1.INSTANCE)).booleanValue();
  }
  
  public static final CoroutineContext newCoroutineContext(CoroutineContext paramCoroutineContext1, CoroutineContext paramCoroutineContext2)
  {
    if (!hasCopyableElements(paramCoroutineContext2)) {
      return paramCoroutineContext1.plus(paramCoroutineContext2);
    }
    return foldCopies(paramCoroutineContext1, paramCoroutineContext2, false);
  }
  
  public static final CoroutineContext newCoroutineContext(CoroutineScope paramCoroutineScope, CoroutineContext paramCoroutineContext)
  {
    paramCoroutineContext = foldCopies(paramCoroutineScope.getCoroutineContext(), paramCoroutineContext, true);
    if (DebugKt.getDEBUG()) {
      paramCoroutineScope = paramCoroutineContext.plus((CoroutineContext)new CoroutineId(DebugKt.getCOROUTINE_ID().incrementAndGet()));
    } else {
      paramCoroutineScope = paramCoroutineContext;
    }
    if ((paramCoroutineContext != Dispatchers.getDefault()) && (paramCoroutineContext.get((CoroutineContext.Key)ContinuationInterceptor.Key) == null)) {
      paramCoroutineScope = paramCoroutineScope.plus((CoroutineContext)Dispatchers.getDefault());
    }
    return paramCoroutineScope;
  }
  
  public static final UndispatchedCoroutine<?> undispatchedCompletion(CoroutineStackFrame paramCoroutineStackFrame)
  {
    for (;;)
    {
      if ((paramCoroutineStackFrame instanceof DispatchedCoroutine)) {
        return null;
      }
      paramCoroutineStackFrame = paramCoroutineStackFrame.getCallerFrame();
      if (paramCoroutineStackFrame == null) {
        return null;
      }
      if ((paramCoroutineStackFrame instanceof UndispatchedCoroutine)) {
        return (UndispatchedCoroutine)paramCoroutineStackFrame;
      }
    }
  }
  
  public static final UndispatchedCoroutine<?> updateUndispatchedCompletion(Continuation<?> paramContinuation, CoroutineContext paramCoroutineContext, Object paramObject)
  {
    if (!(paramContinuation instanceof CoroutineStackFrame)) {
      return null;
    }
    int i;
    if (paramCoroutineContext.get((CoroutineContext.Key)UndispatchedMarker.INSTANCE) != null) {
      i = 1;
    } else {
      i = 0;
    }
    if (i == 0) {
      return null;
    }
    paramContinuation = undispatchedCompletion((CoroutineStackFrame)paramContinuation);
    if (paramContinuation != null) {
      paramContinuation.saveThreadContext(paramCoroutineContext, paramObject);
    }
    return paramContinuation;
  }
  
  public static final <T> T withContinuationContext(Continuation<?> paramContinuation, Object paramObject, Function0<? extends T> paramFunction0)
  {
    CoroutineContext localCoroutineContext = paramContinuation.getContext();
    paramObject = ThreadContextKt.updateThreadContext(localCoroutineContext, paramObject);
    if (paramObject != ThreadContextKt.NO_THREAD_ELEMENTS)
    {
      paramContinuation = updateUndispatchedCompletion(paramContinuation, localCoroutineContext, paramObject);
    }
    else
    {
      paramContinuation = null;
      UndispatchedCoroutine localUndispatchedCoroutine = (UndispatchedCoroutine)null;
    }
    try
    {
      paramFunction0 = paramFunction0.invoke();
      return paramFunction0;
    }
    finally
    {
      InlineMarker.finallyStart(1);
      if ((paramContinuation == null) || (paramContinuation.clearThreadContext())) {
        ThreadContextKt.restoreThreadContext(localCoroutineContext, paramObject);
      }
      InlineMarker.finallyEnd(1);
    }
  }
  
  public static final <T> T withCoroutineContext(CoroutineContext paramCoroutineContext, Object paramObject, Function0<? extends T> paramFunction0)
  {
    paramObject = ThreadContextKt.updateThreadContext(paramCoroutineContext, paramObject);
    try
    {
      paramFunction0 = paramFunction0.invoke();
      return paramFunction0;
    }
    finally
    {
      InlineMarker.finallyStart(1);
      ThreadContextKt.restoreThreadContext(paramCoroutineContext, paramObject);
      InlineMarker.finallyEnd(1);
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlinx/coroutines/CoroutineContextKt.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */