package kotlinx.coroutines.flow.internal;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.SuspendFunction;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.FunctionReferenceImpl;
import kotlin.jvm.internal.TypeIntrinsics;
import kotlinx.coroutines.flow.FlowCollector;

@Metadata(d1={"\000\032\n\000\n\002\030\002\n\002\030\002\n\002\020\000\n\002\030\002\n\002\020\002\n\002\b\003\">\020\000\032,\022\f\022\n\022\006\022\004\030\0010\0030\002\022\006\022\004\030\0010\003\022\n\022\b\022\004\022\0020\0050\004\022\006\022\004\030\0010\0030\001X\004¢\006\b\n\000\022\004\b\006\020\007¨\006\b"}, d2={"emitFun", "Lkotlin/Function3;", "Lkotlinx/coroutines/flow/FlowCollector;", "", "Lkotlin/coroutines/Continuation;", "", "getEmitFun$annotations", "()V", "kotlinx-coroutines-core"}, k=2, mv={1, 6, 0}, xi=48)
public final class SafeCollectorKt
{
  private static final Function3<FlowCollector<Object>, Object, Continuation<? super Unit>, Object> emitFun = (Function3)TypeIntrinsics.beforeCheckcastToFunctionOfArity(emitFun.1.INSTANCE, 3);
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlinx/coroutines/flow/internal/SafeCollectorKt.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */