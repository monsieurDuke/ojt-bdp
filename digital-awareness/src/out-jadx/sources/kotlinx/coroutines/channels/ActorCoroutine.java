package kotlinx.coroutines.channels;

import java.util.concurrent.CancellationException;
import kotlin.Metadata;
import kotlin.coroutines.CoroutineContext;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.CoroutineExceptionHandlerKt;
import kotlinx.coroutines.DebugStringsKt;
import kotlinx.coroutines.ExceptionsKt;
import kotlinx.coroutines.Job;
import mt.Log229316;
import mt.Log5ECF72;
import mt.LogE84000;
import okhttp3.HttpUrl;

/* compiled from: 0186.java */
@Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u0003\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\b\u0012\u0018\u0000*\u0004\b\u0000\u0010\u00012\b\u0012\u0004\u0012\u0002H\u00010\u00022\b\u0012\u0004\u0012\u0002H\u00010\u0003B#\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00028\u00000\u0007\u0012\u0006\u0010\b\u001a\u00020\t¢\u0006\u0002\u0010\nJ\u0010\u0010\u000b\u001a\u00020\t2\u0006\u0010\f\u001a\u00020\rH\u0014J\u0012\u0010\u000e\u001a\u00020\u000f2\b\u0010\u0010\u001a\u0004\u0018\u00010\rH\u0014¨\u0006\u0011"}, d2 = {"Lkotlinx/coroutines/channels/ActorCoroutine;", "E", "Lkotlinx/coroutines/channels/ChannelCoroutine;", "Lkotlinx/coroutines/channels/ActorScope;", "parentContext", "Lkotlin/coroutines/CoroutineContext;", "channel", "Lkotlinx/coroutines/channels/Channel;", "active", HttpUrl.FRAGMENT_ENCODE_SET, "(Lkotlin/coroutines/CoroutineContext;Lkotlinx/coroutines/channels/Channel;Z)V", "handleJobException", "exception", HttpUrl.FRAGMENT_ENCODE_SET, "onCancelling", HttpUrl.FRAGMENT_ENCODE_SET, "cause", "kotlinx-coroutines-core"}, k = 1, mv = {1, 6, 0}, xi = 48)
/* loaded from: classes.dex */
class ActorCoroutine<E> extends ChannelCoroutine<E> implements ActorScope<E> {
    public ActorCoroutine(CoroutineContext parentContext, Channel<E> channel, boolean active) {
        super(parentContext, channel, false, active);
        initParentJob((Job) parentContext.get(Job.INSTANCE));
    }

    @Override // kotlinx.coroutines.JobSupport
    protected boolean handleJobException(Throwable exception) {
        CoroutineExceptionHandlerKt.handleCoroutineException(getContext(), exception);
        return true;
    }

    /* JADX WARN: Failed to parse debug info
    jadx.core.utils.exceptions.JadxRuntimeException: Failed to parse type string: null
    	at jadx.core.dex.instructions.args.ArgType.parse(ArgType.java:736)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.getVarType(DebugInfoAttachVisitor.java:139)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.attachDebugInfo(DebugInfoAttachVisitor.java:94)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.processDebugInfo(DebugInfoAttachVisitor.java:51)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.visit(DebugInfoAttachVisitor.java:41)
     */
    @Override // kotlinx.coroutines.JobSupport
    protected void onCancelling(Throwable cause) {
        Channel<E> channel = get_channel();
        if (cause != null) {
            r1 = cause instanceof CancellationException ? (CancellationException) cause : null;
            if (r1 == null) {
                String classSimpleName = DebugStringsKt.getClassSimpleName(this);
                Log5ECF72.a(classSimpleName);
                LogE84000.a(classSimpleName);
                Log229316.a(classSimpleName);
                String stringPlus = Intrinsics.stringPlus(classSimpleName, " was cancelled");
                Log5ECF72.a(stringPlus);
                LogE84000.a(stringPlus);
                Log229316.a(stringPlus);
                r1 = ExceptionsKt.CancellationException(stringPlus, cause);
            }
        }
        channel.cancel(r1);
    }
}
