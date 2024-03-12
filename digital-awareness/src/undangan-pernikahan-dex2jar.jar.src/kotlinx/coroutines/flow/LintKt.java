package kotlinx.coroutines.flow;

import java.util.List;
import java.util.Set;
import java.util.concurrent.CancellationException;
import kotlin.Deprecated;
import kotlin.DeprecationLevel;
import kotlin.KotlinNothingValueException;
import kotlin.Metadata;
import kotlin.ReplaceWith;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.Boxing;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.functions.Function4;
import kotlin.jvm.internal.InlineMarker;

@Metadata(d1={"\000\001\n\000\n\002\030\002\n\002\030\002\n\002\b\005\n\002\020\013\n\002\b\003\n\002\020\002\n\000\n\002\030\002\n\002\030\002\n\000\n\002\030\002\n\000\n\002\030\002\n\002\b\002\n\002\030\002\n\002\020\003\n\002\030\002\n\000\n\002\030\002\n\002\020\000\n\002\030\002\n\002\b\002\n\002\030\002\n\000\n\002\020\b\n\002\b\006\n\002\020\t\n\000\n\002\030\002\n\002\b\002\n\002\030\002\n\002\b\003\n\002\020 \n\000\n\002\020\"\n\000\032\"\020\013\032\0020\f*\006\022\002\b\0030\0022\020\b\002\020\r\032\n\030\0010\016j\004\030\001`\017H\007\032\036\020\020\032\b\022\004\022\002H\0220\021\"\004\b\000\020\022*\b\022\004\022\002H\0220\023H\007\032m\020\024\032\b\022\004\022\002H\0220\021\"\004\b\000\020\022*\b\022\004\022\002H\0220\0232D\b\b\020\025\032>\b\001\022\n\022\b\022\004\022\002H\0220\002\022\023\022\0210\027¢\006\f\b\030\022\b\b\031\022\004\b\b(\r\022\n\022\b\022\004\022\0020\f0\032\022\006\022\004\030\0010\0330\026¢\006\002\b\034H\bø\001\000¢\006\002\020\035\032\036\020\036\032\b\022\004\022\002H\0220\021\"\004\b\000\020\022*\b\022\004\022\002H\0220\037H\007\032!\020 \032\0020!\"\004\b\000\020\022*\b\022\004\022\002H\0220\023HHø\001\000¢\006\002\020\"\032\036\020#\032\b\022\004\022\002H\0220\021\"\004\b\000\020\022*\b\022\004\022\002H\0220\037H\007\032&\020$\032\b\022\004\022\002H\0220\021\"\004\b\000\020\022*\b\022\004\022\002H\0220\0232\006\020%\032\0020\001H\007\032f\020&\032\b\022\004\022\002H\0220\021\"\004\b\000\020\022*\b\022\004\022\002H\0220\0232\b\b\002\020'\032\0020(23\b\n\020)\032-\b\001\022\023\022\0210\027¢\006\f\b\030\022\b\b\031\022\004\b\b(\r\022\n\022\b\022\004\022\0020\b0\032\022\006\022\004\030\0010\0330*H\bø\001\000¢\006\002\020+\032\001\020,\032\b\022\004\022\002H\0220\021\"\004\b\000\020\022*\b\022\004\022\002H\0220\0232Y\b\b\020)\032S\b\001\022\n\022\b\022\004\022\002H\0220\002\022\023\022\0210\027¢\006\f\b\030\022\b\b\031\022\004\b\b(\r\022\023\022\0210(¢\006\f\b\030\022\b\b\031\022\004\b\b(.\022\n\022\b\022\004\022\0020\b0\032\022\006\022\004\030\0010\0330-¢\006\002\b\034H\bø\001\000¢\006\002\020/\032'\0200\032\b\022\004\022\002H\02201\"\004\b\000\020\022*\b\022\004\022\002H\0220\023HHø\001\000¢\006\002\020\"\032'\0202\032\b\022\004\022\002H\02203\"\004\b\000\020\022*\b\022\004\022\002H\0220\023HHø\001\000¢\006\002\020\"\"\"\020\000\032\0020\001*\006\022\002\b\0030\0028FX\004¢\006\f\022\004\b\003\020\004\032\004\b\005\020\006\"\"\020\007\032\0020\b*\006\022\002\b\0030\0028FX\004¢\006\f\022\004\b\t\020\004\032\004\b\007\020\n\002\004\n\002\b\031¨\0064"}, d2={"coroutineContext", "Lkotlin/coroutines/CoroutineContext;", "Lkotlinx/coroutines/flow/FlowCollector;", "getCoroutineContext$annotations", "(Lkotlinx/coroutines/flow/FlowCollector;)V", "getCoroutineContext", "(Lkotlinx/coroutines/flow/FlowCollector;)Lkotlin/coroutines/CoroutineContext;", "isActive", "", "isActive$annotations", "(Lkotlinx/coroutines/flow/FlowCollector;)Z", "cancel", "", "cause", "Ljava/util/concurrent/CancellationException;", "Lkotlinx/coroutines/CancellationException;", "cancellable", "Lkotlinx/coroutines/flow/Flow;", "T", "Lkotlinx/coroutines/flow/SharedFlow;", "catch", "action", "Lkotlin/Function3;", "", "Lkotlin/ParameterName;", "name", "Lkotlin/coroutines/Continuation;", "", "Lkotlin/ExtensionFunctionType;", "(Lkotlinx/coroutines/flow/SharedFlow;Lkotlin/jvm/functions/Function3;)Lkotlinx/coroutines/flow/Flow;", "conflate", "Lkotlinx/coroutines/flow/StateFlow;", "count", "", "(Lkotlinx/coroutines/flow/SharedFlow;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "distinctUntilChanged", "flowOn", "context", "retry", "retries", "", "predicate", "Lkotlin/Function2;", "(Lkotlinx/coroutines/flow/SharedFlow;JLkotlin/jvm/functions/Function2;)Lkotlinx/coroutines/flow/Flow;", "retryWhen", "Lkotlin/Function4;", "attempt", "(Lkotlinx/coroutines/flow/SharedFlow;Lkotlin/jvm/functions/Function4;)Lkotlinx/coroutines/flow/Flow;", "toList", "", "toSet", "", "kotlinx-coroutines-core"}, k=2, mv={1, 6, 0}, xi=48)
public final class LintKt
{
  @Deprecated(level=DeprecationLevel.ERROR, message="cancel() is resolved into the extension of outer CoroutineScope which is likely to be an error.Use currentCoroutineContext().cancel() instead or specify the receiver of cancel() explicitly", replaceWith=@ReplaceWith(expression="currentCoroutineContext().cancel(cause)", imports={}))
  public static final void cancel(FlowCollector<?> paramFlowCollector, CancellationException paramCancellationException)
  {
    FlowKt.noImpl();
    throw new KotlinNothingValueException();
  }
  
  @Deprecated(level=DeprecationLevel.ERROR, message="Applying 'cancellable' to a SharedFlow has no effect. See the SharedFlow documentation on Operator Fusion.", replaceWith=@ReplaceWith(expression="this", imports={}))
  public static final <T> Flow<T> cancellable(SharedFlow<? extends T> paramSharedFlow)
  {
    FlowKt.noImpl();
    throw new KotlinNothingValueException();
  }
  
  @Deprecated(level=DeprecationLevel.WARNING, message="SharedFlow never completes, so this operator typically has not effect, it can only catch exceptions from 'onSubscribe' operator", replaceWith=@ReplaceWith(expression="this", imports={}))
  private static final <T> Flow<T> jdMethod_catch(SharedFlow<? extends T> paramSharedFlow, Function3<? super FlowCollector<? super T>, ? super Throwable, ? super Continuation<? super Unit>, ? extends Object> paramFunction3)
  {
    return FlowKt.jdMethod_catch((Flow)paramSharedFlow, paramFunction3);
  }
  
  @Deprecated(level=DeprecationLevel.ERROR, message="Applying 'conflate' to StateFlow has no effect. See the StateFlow documentation on Operator Fusion.", replaceWith=@ReplaceWith(expression="this", imports={}))
  public static final <T> Flow<T> conflate(StateFlow<? extends T> paramStateFlow)
  {
    FlowKt.noImpl();
    throw new KotlinNothingValueException();
  }
  
  @Deprecated(level=DeprecationLevel.WARNING, message="SharedFlow never completes, so this terminal operation never completes.")
  private static final <T> Object count(SharedFlow<? extends T> paramSharedFlow, Continuation<? super Integer> paramContinuation)
  {
    paramSharedFlow = (Flow)paramSharedFlow;
    InlineMarker.mark(0);
    paramSharedFlow = FlowKt.count(paramSharedFlow, paramContinuation);
    InlineMarker.mark(1);
    return paramSharedFlow;
  }
  
  @Deprecated(level=DeprecationLevel.ERROR, message="Applying 'distinctUntilChanged' to StateFlow has no effect. See the StateFlow documentation on Operator Fusion.", replaceWith=@ReplaceWith(expression="this", imports={}))
  public static final <T> Flow<T> distinctUntilChanged(StateFlow<? extends T> paramStateFlow)
  {
    FlowKt.noImpl();
    throw new KotlinNothingValueException();
  }
  
  @Deprecated(level=DeprecationLevel.ERROR, message="Applying 'flowOn' to SharedFlow has no effect. See the SharedFlow documentation on Operator Fusion.", replaceWith=@ReplaceWith(expression="this", imports={}))
  public static final <T> Flow<T> flowOn(SharedFlow<? extends T> paramSharedFlow, CoroutineContext paramCoroutineContext)
  {
    FlowKt.noImpl();
    throw new KotlinNothingValueException();
  }
  
  public static final CoroutineContext getCoroutineContext(FlowCollector<?> paramFlowCollector)
  {
    FlowKt.noImpl();
    throw new KotlinNothingValueException();
  }
  
  public static final boolean isActive(FlowCollector<?> paramFlowCollector)
  {
    FlowKt.noImpl();
    throw new KotlinNothingValueException();
  }
  
  @Deprecated(level=DeprecationLevel.WARNING, message="SharedFlow never completes, so this operator has no effect.", replaceWith=@ReplaceWith(expression="this", imports={}))
  private static final <T> Flow<T> retry(SharedFlow<? extends T> paramSharedFlow, long paramLong, Function2<? super Throwable, ? super Continuation<? super Boolean>, ? extends Object> paramFunction2)
  {
    return FlowKt.retry((Flow)paramSharedFlow, paramLong, paramFunction2);
  }
  
  @Deprecated(level=DeprecationLevel.WARNING, message="SharedFlow never completes, so this operator has no effect.", replaceWith=@ReplaceWith(expression="this", imports={}))
  private static final <T> Flow<T> retryWhen(SharedFlow<? extends T> paramSharedFlow, Function4<? super FlowCollector<? super T>, ? super Throwable, ? super Long, ? super Continuation<? super Boolean>, ? extends Object> paramFunction4)
  {
    return FlowKt.retryWhen((Flow)paramSharedFlow, paramFunction4);
  }
  
  @Deprecated(level=DeprecationLevel.WARNING, message="SharedFlow never completes, so this terminal operation never completes.")
  private static final <T> Object toList(SharedFlow<? extends T> paramSharedFlow, Continuation<? super List<? extends T>> paramContinuation)
  {
    paramSharedFlow = (Flow)paramSharedFlow;
    InlineMarker.mark(0);
    paramSharedFlow = FlowKt.toList$default(paramSharedFlow, null, paramContinuation, 1, null);
    InlineMarker.mark(1);
    return paramSharedFlow;
  }
  
  @Deprecated(level=DeprecationLevel.WARNING, message="SharedFlow never completes, so this terminal operation never completes.")
  private static final <T> Object toSet(SharedFlow<? extends T> paramSharedFlow, Continuation<? super Set<? extends T>> paramContinuation)
  {
    paramSharedFlow = (Flow)paramSharedFlow;
    InlineMarker.mark(0);
    paramSharedFlow = FlowKt.toSet$default(paramSharedFlow, null, paramContinuation, 1, null);
    InlineMarker.mark(1);
    return paramSharedFlow;
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlinx/coroutines/flow/LintKt.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */