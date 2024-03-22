package kotlinx.coroutines.flow;

import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Ref;
import kotlinx.coroutines.channels.ChannelResult;
import kotlinx.coroutines.flow.internal.NullSurrogateKt;
import kotlinx.coroutines.internal.Symbol;
import okhttp3.HttpUrl;

/* compiled from: Delay.kt */
@Metadata(d1 = {"\u0000\u0012\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u00022\f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004H\u008a@"}, d2 = {"<anonymous>", HttpUrl.FRAGMENT_ENCODE_SET, "T", "value", "Lkotlinx/coroutines/channels/ChannelResult;", HttpUrl.FRAGMENT_ENCODE_SET}, k = 3, mv = {1, 6, 0}, xi = 48)
@DebugMetadata(c = "kotlinx.coroutines.flow.FlowKt__DelayKt$debounceInternal$1$3$2", f = "Delay.kt", i = {0}, l = {243}, m = "invokeSuspend", n = {"$this$onFailure$iv"}, s = {"L$0"})
/* loaded from: classes.dex */
final class FlowKt__DelayKt$debounceInternal$1$3$2 extends SuspendLambda implements Function2<ChannelResult<? extends Object>, Continuation<? super Unit>, Object> {
    final /* synthetic */ FlowCollector<T> $downstream;
    final /* synthetic */ Ref.ObjectRef<Object> $lastValue;
    /* synthetic */ Object L$0;
    Object L$1;
    int label;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    /* JADX WARN: Multi-variable type inference failed */
    public FlowKt__DelayKt$debounceInternal$1$3$2(Ref.ObjectRef<Object> objectRef, FlowCollector<? super T> flowCollector, Continuation<? super FlowKt__DelayKt$debounceInternal$1$3$2> continuation) {
        super(2, continuation);
        this.$lastValue = objectRef;
        this.$downstream = flowCollector;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        FlowKt__DelayKt$debounceInternal$1$3$2 flowKt__DelayKt$debounceInternal$1$3$2 = new FlowKt__DelayKt$debounceInternal$1$3$2(this.$lastValue, this.$downstream, continuation);
        flowKt__DelayKt$debounceInternal$1$3$2.L$0 = obj;
        return flowKt__DelayKt$debounceInternal$1$3$2;
    }

    @Override // kotlin.jvm.functions.Function2
    public /* bridge */ /* synthetic */ Object invoke(ChannelResult<? extends Object> channelResult, Continuation<? super Unit> continuation) {
        return m1563invokeWpGqRn0(channelResult.getHolder(), continuation);
    }

    /* renamed from: invoke-WpGqRn0, reason: not valid java name */
    public final Object m1563invokeWpGqRn0(Object obj, Continuation<? super Unit> continuation) {
        return ((FlowKt__DelayKt$debounceInternal$1$3$2) create(ChannelResult.m1540boximpl(obj), continuation)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Failed to find 'out' block for switch in B:2:0x0006. Please report as an issue. */
    /* JADX WARN: Type inference failed for: r0v4, types: [kotlinx.coroutines.internal.Symbol, T] */
    /* JADX WARN: Type inference failed for: r2v2, types: [T, java.lang.Object] */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        Ref.ObjectRef<Object> objectRef;
        FlowKt__DelayKt$debounceInternal$1$3$2 flowKt__DelayKt$debounceInternal$1$3$2;
        boolean z;
        boolean z2;
        Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        switch (this.label) {
            case 0:
                ResultKt.throwOnFailure(obj);
                ?? holder = ((ChannelResult) this.L$0).getHolder();
                Ref.ObjectRef<Object> objectRef2 = this.$lastValue;
                if (!(holder instanceof ChannelResult.Failed)) {
                    objectRef2.element = holder;
                }
                objectRef = this.$lastValue;
                FlowCollector<T> flowCollector = this.$downstream;
                if (holder instanceof ChannelResult.Failed) {
                    Throwable m1544exceptionOrNullimpl = ChannelResult.m1544exceptionOrNullimpl(holder);
                    if (m1544exceptionOrNullimpl != null) {
                        throw m1544exceptionOrNullimpl;
                    }
                    if (objectRef.element != null) {
                        Symbol symbol = NullSurrogateKt.NULL;
                        Object obj2 = objectRef.element;
                        if (obj2 == symbol) {
                            obj2 = null;
                        }
                        this.L$0 = holder;
                        this.L$1 = objectRef;
                        this.label = 1;
                        if (flowCollector.emit(obj2, this) == coroutine_suspended) {
                            return coroutine_suspended;
                        }
                        flowKt__DelayKt$debounceInternal$1$3$2 = this;
                        z = false;
                        z2 = false;
                    }
                    objectRef.element = NullSurrogateKt.DONE;
                }
                return Unit.INSTANCE;
            case 1:
                flowKt__DelayKt$debounceInternal$1$3$2 = this;
                z = false;
                z2 = false;
                objectRef = (Ref.ObjectRef) flowKt__DelayKt$debounceInternal$1$3$2.L$1;
                Object obj3 = flowKt__DelayKt$debounceInternal$1$3$2.L$0;
                ResultKt.throwOnFailure(obj);
                objectRef.element = NullSurrogateKt.DONE;
                return Unit.INSTANCE;
            default:
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
    }
}