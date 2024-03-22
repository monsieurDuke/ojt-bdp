package kotlinx.coroutines.channels;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.internal.AtomicKt;
import kotlinx.coroutines.selects.SelectInstance;
import kotlinx.coroutines.selects.SelectKt;
import mt.Log229316;
import mt.Log5ECF72;
import mt.LogE84000;
import okhttp3.HttpUrl;

/* compiled from: 018D.java */
@Metadata(d1 = {"\u0000F\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0010\u0000\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0010\u0018\u0000*\u0004\b\u0000\u0010\u00012\b\u0012\u0004\u0012\u0002H\u00010\u0002B'\u0012 \u0010\u0003\u001a\u001c\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00020\u0005\u0018\u00010\u0004j\n\u0012\u0004\u0012\u00028\u0000\u0018\u0001`\u0006¢\u0006\u0002\u0010\u0007J\u0015\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00028\u0000H\u0014¢\u0006\u0002\u0010\u0011J!\u0010\u0012\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00028\u00002\n\u0010\u0013\u001a\u0006\u0012\u0002\b\u00030\u0014H\u0014¢\u0006\u0002\u0010\u0015J/\u0010\u0016\u001a\u00020\u00052\f\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00190\u00182\n\u0010\u001a\u001a\u0006\u0012\u0002\b\u00030\u001bH\u0014ø\u0001\u0000ø\u0001\u0001¢\u0006\u0004\b\u001c\u0010\u001dR\u0014\u0010\b\u001a\u00020\t8DX\u0084\u0004¢\u0006\u0006\u001a\u0004\b\b\u0010\nR\u0014\u0010\u000b\u001a\u00020\t8DX\u0084\u0004¢\u0006\u0006\u001a\u0004\b\u000b\u0010\nR\u0014\u0010\f\u001a\u00020\t8DX\u0084\u0004¢\u0006\u0006\u001a\u0004\b\f\u0010\nR\u0014\u0010\r\u001a\u00020\t8DX\u0084\u0004¢\u0006\u0006\u001a\u0004\b\r\u0010\n\u0082\u0002\u000b\n\u0002\b\u0019\n\u0005\b¡\u001e0\u0001¨\u0006\u001e"}, d2 = {"Lkotlinx/coroutines/channels/LinkedListChannel;", "E", "Lkotlinx/coroutines/channels/AbstractChannel;", "onUndeliveredElement", "Lkotlin/Function1;", HttpUrl.FRAGMENT_ENCODE_SET, "Lkotlinx/coroutines/internal/OnUndeliveredElement;", "(Lkotlin/jvm/functions/Function1;)V", "isBufferAlwaysEmpty", HttpUrl.FRAGMENT_ENCODE_SET, "()Z", "isBufferAlwaysFull", "isBufferEmpty", "isBufferFull", "offerInternal", HttpUrl.FRAGMENT_ENCODE_SET, "element", "(Ljava/lang/Object;)Ljava/lang/Object;", "offerSelectInternal", "select", "Lkotlinx/coroutines/selects/SelectInstance;", "(Ljava/lang/Object;Lkotlinx/coroutines/selects/SelectInstance;)Ljava/lang/Object;", "onCancelIdempotentList", "list", "Lkotlinx/coroutines/internal/InlineList;", "Lkotlinx/coroutines/channels/Send;", "closed", "Lkotlinx/coroutines/channels/Closed;", "onCancelIdempotentList-w-w6eGU", "(Ljava/lang/Object;Lkotlinx/coroutines/channels/Closed;)V", "kotlinx-coroutines-core"}, k = 1, mv = {1, 6, 0}, xi = 48)
/* loaded from: classes.dex */
public class LinkedListChannel<E> extends AbstractChannel<E> {
    public LinkedListChannel(Function1<? super E, Unit> function1) {
        super(function1);
    }

    @Override // kotlinx.coroutines.channels.AbstractChannel
    protected final boolean isBufferAlwaysEmpty() {
        return true;
    }

    @Override // kotlinx.coroutines.channels.AbstractSendChannel
    protected final boolean isBufferAlwaysFull() {
        return false;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // kotlinx.coroutines.channels.AbstractChannel
    public final boolean isBufferEmpty() {
        return true;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // kotlinx.coroutines.channels.AbstractSendChannel
    public final boolean isBufferFull() {
        return false;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* JADX WARN: Failed to parse debug info
    jadx.core.utils.exceptions.JadxRuntimeException: Failed to parse type string: null
    	at jadx.core.dex.instructions.args.ArgType.parse(ArgType.java:736)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.getVarType(DebugInfoAttachVisitor.java:139)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.attachDebugInfo(DebugInfoAttachVisitor.java:94)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.processDebugInfo(DebugInfoAttachVisitor.java:51)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.visit(DebugInfoAttachVisitor.java:41)
     */
    @Override // kotlinx.coroutines.channels.AbstractSendChannel
    public Object offerInternal(E element) {
        ReceiveOrClosed<?> sendBuffered;
        do {
            Object offerInternal = super.offerInternal(element);
            if (offerInternal == AbstractChannelKt.OFFER_SUCCESS) {
                return AbstractChannelKt.OFFER_SUCCESS;
            }
            if (offerInternal != AbstractChannelKt.OFFER_FAILED) {
                if (offerInternal instanceof Closed) {
                    return offerInternal;
                }
                String stringPlus = Intrinsics.stringPlus("Invalid offerInternal result ", offerInternal);
                Log5ECF72.a(stringPlus);
                LogE84000.a(stringPlus);
                Log229316.a(stringPlus);
                throw new IllegalStateException(stringPlus.toString());
            }
            sendBuffered = sendBuffered(element);
            if (sendBuffered == null) {
                return AbstractChannelKt.OFFER_SUCCESS;
            }
        } while (!(sendBuffered instanceof Closed));
        return sendBuffered;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* JADX WARN: Failed to parse debug info
    jadx.core.utils.exceptions.JadxRuntimeException: Failed to parse type string: null
    	at jadx.core.dex.instructions.args.ArgType.parse(ArgType.java:736)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.getVarType(DebugInfoAttachVisitor.java:139)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.attachDebugInfo(DebugInfoAttachVisitor.java:94)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.processDebugInfo(DebugInfoAttachVisitor.java:51)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.visit(DebugInfoAttachVisitor.java:41)
     */
    @Override // kotlinx.coroutines.channels.AbstractSendChannel
    public Object offerSelectInternal(E element, SelectInstance<?> select) {
        Object performAtomicTrySelect;
        while (true) {
            if (getHasReceiveOrClosed()) {
                performAtomicTrySelect = super.offerSelectInternal(element, select);
            } else {
                performAtomicTrySelect = select.performAtomicTrySelect(describeSendBuffered(element));
                if (performAtomicTrySelect == null) {
                    performAtomicTrySelect = AbstractChannelKt.OFFER_SUCCESS;
                }
            }
            if (performAtomicTrySelect == SelectKt.getALREADY_SELECTED()) {
                return SelectKt.getALREADY_SELECTED();
            }
            if (performAtomicTrySelect == AbstractChannelKt.OFFER_SUCCESS) {
                return AbstractChannelKt.OFFER_SUCCESS;
            }
            if (performAtomicTrySelect != AbstractChannelKt.OFFER_FAILED && performAtomicTrySelect != AtomicKt.RETRY_ATOMIC) {
                if (performAtomicTrySelect instanceof Closed) {
                    return performAtomicTrySelect;
                }
                String stringPlus = Intrinsics.stringPlus("Invalid result ", performAtomicTrySelect);
                Log5ECF72.a(stringPlus);
                LogE84000.a(stringPlus);
                Log229316.a(stringPlus);
                throw new IllegalStateException(stringPlus.toString());
            }
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:14:0x0037, code lost:
    
        if (r4 >= 0) goto L19;
     */
    /* JADX WARN: Code restructure failed: missing block: B:15:0x0039, code lost:
    
        r5 = r4;
        r4 = r4 - 1;
        r6 = (kotlinx.coroutines.channels.Send) r2.get(r5);
     */
    /* JADX WARN: Code restructure failed: missing block: B:16:0x0046, code lost:
    
        if ((r6 instanceof kotlinx.coroutines.channels.AbstractSendChannel.SendBuffered) == false) goto L26;
     */
    /* JADX WARN: Code restructure failed: missing block: B:17:0x0048, code lost:
    
        r8 = r10.onUndeliveredElement;
     */
    /* JADX WARN: Code restructure failed: missing block: B:18:0x004a, code lost:
    
        if (r8 != null) goto L24;
     */
    /* JADX WARN: Code restructure failed: missing block: B:19:0x004c, code lost:
    
        r8 = null;
     */
    /* JADX WARN: Code restructure failed: missing block: B:20:0x0057, code lost:
    
        r0 = r8;
     */
    /* JADX WARN: Code restructure failed: missing block: B:22:0x005d, code lost:
    
        if (r4 >= 0) goto L39;
     */
    /* JADX WARN: Code restructure failed: missing block: B:25:0x004e, code lost:
    
        r8 = kotlinx.coroutines.internal.OnUndeliveredElementKt.callUndeliveredElementCatchingException(r8, ((kotlinx.coroutines.channels.AbstractSendChannel.SendBuffered) r6).element, r0);
     */
    /* JADX WARN: Code restructure failed: missing block: B:26:0x0059, code lost:
    
        r6.resumeSendClosed(r12);
     */
    @Override // kotlinx.coroutines.channels.AbstractChannel
    /* renamed from: onCancelIdempotentList-w-w6eGU */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    protected void mo1532onCancelIdempotentListww6eGU(java.lang.Object r11, kotlinx.coroutines.channels.Closed<?> r12) {
        /*
            r10 = this;
            r0 = 0
            r1 = 0
            if (r11 != 0) goto L6
            goto L60
        L6:
            boolean r2 = r11 instanceof java.util.ArrayList
            r3 = 0
            if (r2 != 0) goto L2c
            r2 = r11
            kotlinx.coroutines.channels.Send r2 = (kotlinx.coroutines.channels.Send) r2
            r4 = 0
            boolean r5 = r2 instanceof kotlinx.coroutines.channels.AbstractSendChannel.SendBuffered
            if (r5 == 0) goto L27
            kotlin.jvm.functions.Function1<E, kotlin.Unit> r5 = r10.onUndeliveredElement
            if (r5 != 0) goto L19
            goto L25
        L19:
            r3 = r2
            kotlinx.coroutines.channels.AbstractSendChannel$SendBuffered r3 = (kotlinx.coroutines.channels.AbstractSendChannel.SendBuffered) r3
            E r3 = r3.element
            r6 = r0
            kotlinx.coroutines.internal.UndeliveredElementException r6 = (kotlinx.coroutines.internal.UndeliveredElementException) r6
            kotlinx.coroutines.internal.UndeliveredElementException r3 = kotlinx.coroutines.internal.OnUndeliveredElementKt.callUndeliveredElementCatchingException(r5, r3, r6)
        L25:
            r0 = r3
            goto L2a
        L27:
            r2.resumeSendClosed(r12)
        L2a:
            goto L5f
        L2c:
            if (r11 == 0) goto L66
            r2 = r11
            java.util.ArrayList r2 = (java.util.ArrayList) r2
            int r4 = r2.size()
            int r4 = r4 + (-1)
            if (r4 < 0) goto L5f
        L39:
            r5 = r4
            int r4 = r4 + (-1)
            java.lang.Object r6 = r2.get(r5)
            kotlinx.coroutines.channels.Send r6 = (kotlinx.coroutines.channels.Send) r6
            r7 = 0
            boolean r8 = r6 instanceof kotlinx.coroutines.channels.AbstractSendChannel.SendBuffered
            if (r8 == 0) goto L59
            kotlin.jvm.functions.Function1<E, kotlin.Unit> r8 = r10.onUndeliveredElement
            if (r8 != 0) goto L4e
            r8 = r3
            goto L57
        L4e:
            r9 = r6
            kotlinx.coroutines.channels.AbstractSendChannel$SendBuffered r9 = (kotlinx.coroutines.channels.AbstractSendChannel.SendBuffered) r9
            E r9 = r9.element
            kotlinx.coroutines.internal.UndeliveredElementException r8 = kotlinx.coroutines.internal.OnUndeliveredElementKt.callUndeliveredElementCatchingException(r8, r9, r0)
        L57:
            r0 = r8
            goto L5c
        L59:
            r6.resumeSendClosed(r12)
        L5c:
            if (r4 >= 0) goto L39
        L5f:
        L60:
            if (r0 != 0) goto L63
            return
        L63:
            r1 = r0
            r2 = 0
            throw r1
        L66:
            java.lang.NullPointerException r2 = new java.lang.NullPointerException
            java.lang.String r3 = "null cannot be cast to non-null type java.util.ArrayList<E of kotlinx.coroutines.internal.InlineList>{ kotlin.collections.TypeAliasesKt.ArrayList<E of kotlinx.coroutines.internal.InlineList> }"
            r2.<init>(r3)
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.channels.LinkedListChannel.mo1532onCancelIdempotentListww6eGU(java.lang.Object, kotlinx.coroutines.channels.Closed):void");
    }
}
