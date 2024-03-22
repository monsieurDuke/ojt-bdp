package kotlin.sequences;

import java.util.Iterator;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.Boxing;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.RestrictedSuspendLambda;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import okhttp3.HttpUrl;

/* JADX INFO: Access modifiers changed from: package-private */
/* JADX INFO: Add missing generic type declarations: [R] */
/* compiled from: Sequences.kt */
@Metadata(d1 = {"\u0000\u000e\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003\"\u0004\b\u0002\u0010\u0004*\b\u0012\u0004\u0012\u0002H\u00040\u0005H\u008a@"}, d2 = {"<anonymous>", HttpUrl.FRAGMENT_ENCODE_SET, "T", "C", "R", "Lkotlin/sequences/SequenceScope;"}, k = 3, mv = {1, 7, 1}, xi = 48)
@DebugMetadata(c = "kotlin.sequences.SequencesKt__SequencesKt$flatMapIndexed$1", f = "Sequences.kt", i = {0, 0}, l = {332}, m = "invokeSuspend", n = {"$this$sequence", "index"}, s = {"L$0", "I$0"})
/* loaded from: classes.dex */
public final class SequencesKt__SequencesKt$flatMapIndexed$1<R> extends RestrictedSuspendLambda implements Function2<SequenceScope<? super R>, Continuation<? super Unit>, Object> {
    final /* synthetic */ Function1<C, Iterator<R>> $iterator;
    final /* synthetic */ Sequence<T> $source;
    final /* synthetic */ Function2<Integer, T, C> $transform;
    int I$0;
    private /* synthetic */ Object L$0;
    Object L$1;
    int label;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    /* JADX WARN: Multi-variable type inference failed */
    public SequencesKt__SequencesKt$flatMapIndexed$1(Sequence<? extends T> sequence, Function2<? super Integer, ? super T, ? extends C> function2, Function1<? super C, ? extends Iterator<? extends R>> function1, Continuation<? super SequencesKt__SequencesKt$flatMapIndexed$1> continuation) {
        super(2, continuation);
        this.$source = sequence;
        this.$transform = function2;
        this.$iterator = function1;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        SequencesKt__SequencesKt$flatMapIndexed$1 sequencesKt__SequencesKt$flatMapIndexed$1 = new SequencesKt__SequencesKt$flatMapIndexed$1(this.$source, this.$transform, this.$iterator, continuation);
        sequencesKt__SequencesKt$flatMapIndexed$1.L$0 = obj;
        return sequencesKt__SequencesKt$flatMapIndexed$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(SequenceScope<? super R> sequenceScope, Continuation<? super Unit> continuation) {
        return ((SequencesKt__SequencesKt$flatMapIndexed$1) create(sequenceScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        SequencesKt__SequencesKt$flatMapIndexed$1<R> sequencesKt__SequencesKt$flatMapIndexed$1;
        SequenceScope sequenceScope;
        int i;
        Iterator it;
        Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        switch (this.label) {
            case 0:
                ResultKt.throwOnFailure(obj);
                sequencesKt__SequencesKt$flatMapIndexed$1 = this;
                sequenceScope = (SequenceScope) sequencesKt__SequencesKt$flatMapIndexed$1.L$0;
                i = 0;
                it = sequencesKt__SequencesKt$flatMapIndexed$1.$source.iterator();
                break;
            case 1:
                sequencesKt__SequencesKt$flatMapIndexed$1 = this;
                i = sequencesKt__SequencesKt$flatMapIndexed$1.I$0;
                it = (Iterator) sequencesKt__SequencesKt$flatMapIndexed$1.L$1;
                sequenceScope = (SequenceScope) sequencesKt__SequencesKt$flatMapIndexed$1.L$0;
                ResultKt.throwOnFailure(obj);
                break;
            default:
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        while (it.hasNext()) {
            Object next = it.next();
            Function2<Integer, T, C> function2 = sequencesKt__SequencesKt$flatMapIndexed$1.$transform;
            int i2 = i + 1;
            if (i < 0) {
                CollectionsKt.throwIndexOverflow();
            }
            Object invoke = function2.invoke(Boxing.boxInt(i), next);
            sequencesKt__SequencesKt$flatMapIndexed$1.L$0 = sequenceScope;
            sequencesKt__SequencesKt$flatMapIndexed$1.L$1 = it;
            sequencesKt__SequencesKt$flatMapIndexed$1.I$0 = i2;
            sequencesKt__SequencesKt$flatMapIndexed$1.label = 1;
            if (sequenceScope.yieldAll(sequencesKt__SequencesKt$flatMapIndexed$1.$iterator.invoke(invoke), sequencesKt__SequencesKt$flatMapIndexed$1) == coroutine_suspended) {
                return coroutine_suspended;
            }
            i = i2;
        }
        return Unit.INSTANCE;
    }
}
