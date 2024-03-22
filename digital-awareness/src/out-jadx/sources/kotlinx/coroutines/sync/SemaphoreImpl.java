package kotlinx.coroutines.sync;

import androidx.concurrent.futures.AbstractResolvableFuture$SafeAtomicHelper$$ExternalSyntheticBackportWithForwarding0;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.concurrent.atomic.AtomicLongFieldUpdater;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugProbesKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.CancellableContinuation;
import kotlinx.coroutines.CancellableContinuationImpl;
import kotlinx.coroutines.CancellableContinuationKt;
import kotlinx.coroutines.DebugKt;
import kotlinx.coroutines.debug.internal.ConcurrentWeakMap$Core$$ExternalSyntheticBackportWithForwarding0;
import kotlinx.coroutines.internal.ConcurrentLinkedListKt;
import kotlinx.coroutines.internal.ConcurrentLinkedListNode;
import kotlinx.coroutines.internal.Segment;
import kotlinx.coroutines.internal.SegmentOrClosed;
import kotlinx.coroutines.internal.Symbol;
import mt.Log229316;
import mt.Log5ECF72;
import mt.LogE84000;
import okhttp3.HttpUrl;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: 01AF.java */
@Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\u0010\u0003\n\u0002\b\u0004\n\u0002\u0018\u0002\b\u0002\u0018\u00002\u00020\u001eB\u0017\u0012\u0006\u0010\u0002\u001a\u00020\u0001\u0012\u0006\u0010\u0003\u001a\u00020\u0001¢\u0006\u0004\b\u0004\u0010\u0005J\u0013\u0010\u0007\u001a\u00020\u0006H\u0096@ø\u0001\u0000¢\u0006\u0004\b\u0007\u0010\bJ\u0013\u0010\t\u001a\u00020\u0006H\u0082@ø\u0001\u0000¢\u0006\u0004\b\t\u0010\bJ\u001d\u0010\r\u001a\u00020\f2\f\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\u00060\nH\u0002¢\u0006\u0004\b\r\u0010\u000eJ\u000f\u0010\u000f\u001a\u00020\u0006H\u0016¢\u0006\u0004\b\u000f\u0010\u0010J\u000f\u0010\u0011\u001a\u00020\fH\u0016¢\u0006\u0004\b\u0011\u0010\u0012J\u000f\u0010\u0013\u001a\u00020\fH\u0002¢\u0006\u0004\b\u0013\u0010\u0012J\u0019\u0010\u0014\u001a\u00020\f*\b\u0012\u0004\u0012\u00020\u00060\nH\u0002¢\u0006\u0004\b\u0014\u0010\u000eR\u0014\u0010\u0017\u001a\u00020\u00018VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0015\u0010\u0016R \u0010\u001a\u001a\u000e\u0012\u0004\u0012\u00020\u0019\u0012\u0004\u0012\u00020\u00060\u00188\u0002X\u0082\u0004¢\u0006\u0006\n\u0004\b\u001a\u0010\u001bR\u0014\u0010\u0002\u001a\u00020\u00018\u0002X\u0082\u0004¢\u0006\u0006\n\u0004\b\u0002\u0010\u001c\u0082\u0002\u0004\n\u0002\b\u0019¨\u0006\u001d"}, d2 = {"Lkotlinx/coroutines/sync/SemaphoreImpl;", HttpUrl.FRAGMENT_ENCODE_SET, "permits", "acquiredPermits", "<init>", "(II)V", HttpUrl.FRAGMENT_ENCODE_SET, "acquire", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "acquireSlowPath", "Lkotlinx/coroutines/CancellableContinuation;", "cont", HttpUrl.FRAGMENT_ENCODE_SET, "addAcquireToQueue", "(Lkotlinx/coroutines/CancellableContinuation;)Z", "release", "()V", "tryAcquire", "()Z", "tryResumeNextFromQueue", "tryResumeAcquire", "getAvailablePermits", "()I", "availablePermits", "Lkotlin/Function1;", HttpUrl.FRAGMENT_ENCODE_SET, "onCancellationRelease", "Lkotlin/jvm/functions/Function1;", "I", "kotlinx-coroutines-core", "Lkotlinx/coroutines/sync/Semaphore;"}, k = 1, mv = {1, 6, 0}, xi = 48)
/* loaded from: classes.dex */
public final class SemaphoreImpl implements Semaphore {
    volatile /* synthetic */ int _availablePermits;
    private volatile /* synthetic */ long deqIdx = 0;
    private volatile /* synthetic */ long enqIdx = 0;
    private volatile /* synthetic */ Object head;
    private final Function1<Throwable, Unit> onCancellationRelease;
    private final int permits;
    private volatile /* synthetic */ Object tail;
    private static final /* synthetic */ AtomicReferenceFieldUpdater head$FU = AtomicReferenceFieldUpdater.newUpdater(SemaphoreImpl.class, Object.class, "head");
    private static final /* synthetic */ AtomicLongFieldUpdater deqIdx$FU = AtomicLongFieldUpdater.newUpdater(SemaphoreImpl.class, "deqIdx");
    private static final /* synthetic */ AtomicReferenceFieldUpdater tail$FU = AtomicReferenceFieldUpdater.newUpdater(SemaphoreImpl.class, Object.class, "tail");
    private static final /* synthetic */ AtomicLongFieldUpdater enqIdx$FU = AtomicLongFieldUpdater.newUpdater(SemaphoreImpl.class, "enqIdx");
    static final /* synthetic */ AtomicIntegerFieldUpdater _availablePermits$FU = AtomicIntegerFieldUpdater.newUpdater(SemaphoreImpl.class, "_availablePermits");

    /* JADX WARN: Failed to parse debug info
    jadx.core.utils.exceptions.JadxRuntimeException: Failed to parse type string: null
    	at jadx.core.dex.instructions.args.ArgType.parse(ArgType.java:736)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.getVarType(DebugInfoAttachVisitor.java:139)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.attachDebugInfo(DebugInfoAttachVisitor.java:94)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.processDebugInfo(DebugInfoAttachVisitor.java:51)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.visit(DebugInfoAttachVisitor.java:41)
     */
    public SemaphoreImpl(int i, int i2) {
        this.permits = i;
        if (!(i > 0)) {
            String stringPlus = Intrinsics.stringPlus("Semaphore should have at least 1 permit, but had ", Integer.valueOf(i));
            Log5ECF72.a(stringPlus);
            LogE84000.a(stringPlus);
            Log229316.a(stringPlus);
            throw new IllegalArgumentException(stringPlus.toString());
        }
        if (!(i2 >= 0 && i2 <= i)) {
            String stringPlus2 = Intrinsics.stringPlus("The number of acquired permits should be in 0..", Integer.valueOf(i));
            Log5ECF72.a(stringPlus2);
            LogE84000.a(stringPlus2);
            Log229316.a(stringPlus2);
            throw new IllegalArgumentException(stringPlus2.toString());
        }
        SemaphoreSegment semaphoreSegment = new SemaphoreSegment(0L, null, 2);
        this.head = semaphoreSegment;
        this.tail = semaphoreSegment;
        this._availablePermits = i - i2;
        this.onCancellationRelease = new Function1<Throwable, Unit>() { // from class: kotlinx.coroutines.sync.SemaphoreImpl$onCancellationRelease$1
            /* JADX INFO: Access modifiers changed from: package-private */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(Throwable th) {
                invoke2(th);
                return Unit.INSTANCE;
            }

            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(Throwable $noName_0) {
                SemaphoreImpl.this.release();
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final Object acquireSlowPath(Continuation<? super Unit> continuation) {
        CancellableContinuationImpl orCreateCancellableContinuation = CancellableContinuationKt.getOrCreateCancellableContinuation(IntrinsicsKt.intercepted(continuation));
        CancellableContinuationImpl cancellableContinuationImpl = orCreateCancellableContinuation;
        while (true) {
            if (addAcquireToQueue(cancellableContinuationImpl)) {
                break;
            }
            if (_availablePermits$FU.getAndDecrement(this) > 0) {
                cancellableContinuationImpl.resume(Unit.INSTANCE, this.onCancellationRelease);
                break;
            }
        }
        Object result = orCreateCancellableContinuation.getResult();
        if (result == IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
            DebugProbesKt.probeCoroutineSuspended(continuation);
        }
        return result == IntrinsicsKt.getCOROUTINE_SUSPENDED() ? result : Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r2v8, types: [kotlinx.coroutines.internal.Segment] */
    public final boolean addAcquireToQueue(CancellableContinuation<? super Unit> cont) {
        int i;
        SemaphoreSegment semaphoreSegment;
        Object m1583constructorimpl;
        SemaphoreSegment createSegment;
        Object obj;
        int i2;
        Symbol symbol;
        Symbol symbol2;
        Symbol symbol3;
        boolean z;
        SemaphoreSegment semaphoreSegment2 = (SemaphoreSegment) this.tail;
        long andIncrement = enqIdx$FU.getAndIncrement(this);
        i = SemaphoreKt.SEGMENT_SIZE;
        long j = andIncrement / i;
        while (true) {
            SemaphoreSegment semaphoreSegment3 = semaphoreSegment2;
            while (true) {
                if (semaphoreSegment3.getId() >= j && !semaphoreSegment3.getRemoved()) {
                    m1583constructorimpl = SegmentOrClosed.m1583constructorimpl(semaphoreSegment3);
                    semaphoreSegment = semaphoreSegment2;
                    break;
                }
                Object obj2 = semaphoreSegment3.get_next();
                semaphoreSegment = semaphoreSegment2;
                if (obj2 == ConcurrentLinkedListKt.CLOSED) {
                    m1583constructorimpl = SegmentOrClosed.m1583constructorimpl(ConcurrentLinkedListKt.CLOSED);
                    break;
                }
                ?? r2 = (Segment) ((ConcurrentLinkedListNode) obj2);
                if (r2 != 0) {
                    semaphoreSegment3 = r2;
                    semaphoreSegment2 = semaphoreSegment;
                } else {
                    createSegment = SemaphoreKt.createSegment(semaphoreSegment3.getId() + 1, semaphoreSegment3);
                    SemaphoreSegment semaphoreSegment4 = createSegment;
                    if (semaphoreSegment3.trySetNext(semaphoreSegment4)) {
                        if (semaphoreSegment3.getRemoved()) {
                            semaphoreSegment3.remove();
                        }
                        semaphoreSegment3 = semaphoreSegment4;
                        semaphoreSegment2 = semaphoreSegment;
                    } else {
                        semaphoreSegment2 = semaphoreSegment;
                    }
                }
            }
            obj = m1583constructorimpl;
            if (SegmentOrClosed.m1588isClosedimpl(obj)) {
                break;
            }
            Segment m1586getSegmentimpl = SegmentOrClosed.m1586getSegmentimpl(obj);
            while (true) {
                Segment segment = (Segment) this.tail;
                if (segment.getId() >= m1586getSegmentimpl.getId()) {
                    z = true;
                    break;
                }
                if (!m1586getSegmentimpl.tryIncPointers$kotlinx_coroutines_core()) {
                    z = false;
                    break;
                }
                if (AbstractResolvableFuture$SafeAtomicHelper$$ExternalSyntheticBackportWithForwarding0.m(tail$FU, this, segment, m1586getSegmentimpl)) {
                    if (segment.decPointers$kotlinx_coroutines_core()) {
                        segment.remove();
                    }
                    z = true;
                } else if (m1586getSegmentimpl.decPointers$kotlinx_coroutines_core()) {
                    m1586getSegmentimpl.remove();
                }
            }
            if (z) {
                break;
            }
            semaphoreSegment2 = semaphoreSegment;
        }
        SemaphoreSegment semaphoreSegment5 = (SemaphoreSegment) SegmentOrClosed.m1586getSegmentimpl(obj);
        i2 = SemaphoreKt.SEGMENT_SIZE;
        int i3 = (int) (andIncrement % i2);
        if (ConcurrentWeakMap$Core$$ExternalSyntheticBackportWithForwarding0.m(semaphoreSegment5.acquirers, i3, null, cont)) {
            cont.invokeOnCancellation(new CancelSemaphoreAcquisitionHandler(semaphoreSegment5, i3));
            return true;
        }
        symbol = SemaphoreKt.PERMIT;
        symbol2 = SemaphoreKt.TAKEN;
        if (ConcurrentWeakMap$Core$$ExternalSyntheticBackportWithForwarding0.m(semaphoreSegment5.acquirers, i3, symbol, symbol2)) {
            cont.resume(Unit.INSTANCE, this.onCancellationRelease);
            return true;
        }
        if (!DebugKt.getASSERTIONS_ENABLED()) {
            return false;
        }
        Object obj3 = semaphoreSegment5.acquirers.get(i3);
        symbol3 = SemaphoreKt.BROKEN;
        if (obj3 == symbol3) {
            return false;
        }
        throw new AssertionError();
    }

    private final boolean tryResumeAcquire(CancellableContinuation<? super Unit> cancellableContinuation) {
        Object tryResume = cancellableContinuation.tryResume(Unit.INSTANCE, null, this.onCancellationRelease);
        if (tryResume == null) {
            return false;
        }
        cancellableContinuation.completeResume(tryResume);
        return true;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r11v6, types: [kotlinx.coroutines.internal.Segment] */
    private final boolean tryResumeNextFromQueue() {
        int i;
        Object m1583constructorimpl;
        SemaphoreSegment createSegment;
        Object obj;
        int i2;
        Symbol symbol;
        Symbol symbol2;
        int i3;
        Symbol symbol3;
        Symbol symbol4;
        Symbol symbol5;
        boolean z;
        SemaphoreSegment semaphoreSegment = (SemaphoreSegment) this.head;
        long andIncrement = deqIdx$FU.getAndIncrement(this);
        i = SemaphoreKt.SEGMENT_SIZE;
        long j = andIncrement / i;
        do {
            SemaphoreSegment semaphoreSegment2 = semaphoreSegment;
            while (true) {
                if (semaphoreSegment2.getId() >= j && !semaphoreSegment2.getRemoved()) {
                    m1583constructorimpl = SegmentOrClosed.m1583constructorimpl(semaphoreSegment2);
                    break;
                }
                Object obj2 = semaphoreSegment2.get_next();
                if (obj2 == ConcurrentLinkedListKt.CLOSED) {
                    m1583constructorimpl = SegmentOrClosed.m1583constructorimpl(ConcurrentLinkedListKt.CLOSED);
                    break;
                }
                ?? r11 = (Segment) ((ConcurrentLinkedListNode) obj2);
                if (r11 != 0) {
                    semaphoreSegment2 = r11;
                } else {
                    createSegment = SemaphoreKt.createSegment(semaphoreSegment2.getId() + 1, semaphoreSegment2);
                    SemaphoreSegment semaphoreSegment3 = createSegment;
                    if (semaphoreSegment2.trySetNext(semaphoreSegment3)) {
                        if (semaphoreSegment2.getRemoved()) {
                            semaphoreSegment2.remove();
                        }
                        semaphoreSegment2 = semaphoreSegment3;
                    }
                }
            }
            obj = m1583constructorimpl;
            if (SegmentOrClosed.m1588isClosedimpl(obj)) {
                break;
            }
            Segment m1586getSegmentimpl = SegmentOrClosed.m1586getSegmentimpl(obj);
            while (true) {
                Segment segment = (Segment) this.head;
                if (segment.getId() >= m1586getSegmentimpl.getId()) {
                    z = true;
                    break;
                }
                if (!m1586getSegmentimpl.tryIncPointers$kotlinx_coroutines_core()) {
                    z = false;
                    break;
                }
                if (AbstractResolvableFuture$SafeAtomicHelper$$ExternalSyntheticBackportWithForwarding0.m(head$FU, this, segment, m1586getSegmentimpl)) {
                    if (segment.decPointers$kotlinx_coroutines_core()) {
                        segment.remove();
                    }
                    z = true;
                } else if (m1586getSegmentimpl.decPointers$kotlinx_coroutines_core()) {
                    m1586getSegmentimpl.remove();
                }
            }
        } while (!z);
        SemaphoreSegment semaphoreSegment4 = (SemaphoreSegment) SegmentOrClosed.m1586getSegmentimpl(obj);
        semaphoreSegment4.cleanPrev();
        if (semaphoreSegment4.getId() > j) {
            return false;
        }
        i2 = SemaphoreKt.SEGMENT_SIZE;
        int i4 = (int) (andIncrement % i2);
        symbol = SemaphoreKt.PERMIT;
        Object andSet = semaphoreSegment4.acquirers.getAndSet(i4, symbol);
        if (andSet != null) {
            symbol2 = SemaphoreKt.CANCELLED;
            if (andSet == symbol2) {
                return false;
            }
            return tryResumeAcquire((CancellableContinuation) andSet);
        }
        i3 = SemaphoreKt.MAX_SPIN_CYCLES;
        int i5 = 0;
        while (i5 < i3) {
            int i6 = i5 + 1;
            Object obj3 = semaphoreSegment4.acquirers.get(i4);
            symbol5 = SemaphoreKt.TAKEN;
            if (obj3 == symbol5) {
                return true;
            }
            i5 = i6;
        }
        symbol3 = SemaphoreKt.PERMIT;
        symbol4 = SemaphoreKt.BROKEN;
        return !ConcurrentWeakMap$Core$$ExternalSyntheticBackportWithForwarding0.m(semaphoreSegment4.acquirers, i4, symbol3, symbol4);
    }

    @Override // kotlinx.coroutines.sync.Semaphore
    public Object acquire(Continuation<? super Unit> continuation) {
        Object acquireSlowPath;
        return (_availablePermits$FU.getAndDecrement(this) <= 0 && (acquireSlowPath = acquireSlowPath(continuation)) == IntrinsicsKt.getCOROUTINE_SUSPENDED()) ? acquireSlowPath : Unit.INSTANCE;
    }

    @Override // kotlinx.coroutines.sync.Semaphore
    public int getAvailablePermits() {
        return Math.max(this._availablePermits, 0);
    }

    /* JADX WARN: Failed to parse debug info
    jadx.core.utils.exceptions.JadxRuntimeException: Failed to parse type string: null
    	at jadx.core.dex.instructions.args.ArgType.parse(ArgType.java:736)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.getVarType(DebugInfoAttachVisitor.java:139)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.attachDebugInfo(DebugInfoAttachVisitor.java:94)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.processDebugInfo(DebugInfoAttachVisitor.java:51)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.visit(DebugInfoAttachVisitor.java:41)
     */
    @Override // kotlinx.coroutines.sync.Semaphore
    public void release() {
        int i;
        do {
            do {
                i = this._availablePermits;
                int i2 = this.permits;
                if (!(i < i2)) {
                    String stringPlus = Intrinsics.stringPlus("The number of released permits cannot be greater than ", Integer.valueOf(i2));
                    Log5ECF72.a(stringPlus);
                    LogE84000.a(stringPlus);
                    Log229316.a(stringPlus);
                    throw new IllegalStateException(stringPlus.toString());
                }
            } while (!_availablePermits$FU.compareAndSet(this, i, i + 1));
            if (i >= 0) {
                return;
            }
        } while (!tryResumeNextFromQueue());
    }

    @Override // kotlinx.coroutines.sync.Semaphore
    public boolean tryAcquire() {
        int i;
        do {
            i = this._availablePermits;
            if (i <= 0) {
                return false;
            }
        } while (!_availablePermits$FU.compareAndSet(this, i, i - 1));
        return true;
    }
}
