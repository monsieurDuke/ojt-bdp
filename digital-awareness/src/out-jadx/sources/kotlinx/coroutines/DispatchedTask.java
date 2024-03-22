package kotlinx.coroutines;

import java.util.concurrent.CancellationException;
import kotlin.Metadata;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.jvm.internal.CoroutineStackFrame;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.internal.DispatchedContinuation;
import kotlinx.coroutines.internal.StackTraceRecoveryKt;
import kotlinx.coroutines.internal.ThreadContextKt;
import kotlinx.coroutines.scheduling.Task;
import kotlinx.coroutines.scheduling.TaskContext;
import okhttp3.HttpUrl;

/* compiled from: DispatchedTask.kt */
@Metadata(d1 = {"\u00004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0003\n\u0002\b\u000e\b \u0018\u0000*\u0006\b\u0000\u0010\u0001 \u00002\u00060\u0002j\u0002`\u0003B\r\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u001f\u0010\u000b\u001a\u00020\f2\b\u0010\r\u001a\u0004\u0018\u00010\u000e2\u0006\u0010\u000f\u001a\u00020\u0010H\u0010¢\u0006\u0002\b\u0011J\u0019\u0010\u0012\u001a\u0004\u0018\u00010\u00102\b\u0010\u0013\u001a\u0004\u0018\u00010\u000eH\u0010¢\u0006\u0002\b\u0014J\u001f\u0010\u0015\u001a\u0002H\u0001\"\u0004\b\u0001\u0010\u00012\b\u0010\u0013\u001a\u0004\u0018\u00010\u000eH\u0010¢\u0006\u0004\b\u0016\u0010\u0017J\u001a\u0010\u0018\u001a\u00020\f2\b\u0010\u0019\u001a\u0004\u0018\u00010\u00102\b\u0010\u001a\u001a\u0004\u0018\u00010\u0010J\u0006\u0010\u001b\u001a\u00020\fJ\u000f\u0010\u001c\u001a\u0004\u0018\u00010\u000eH ¢\u0006\u0002\b\u001dR\u0018\u0010\u0007\u001a\b\u0012\u0004\u0012\u00028\u00000\bX \u0004¢\u0006\u0006\u001a\u0004\b\t\u0010\nR\u0012\u0010\u0004\u001a\u00020\u00058\u0006@\u0006X\u0087\u000e¢\u0006\u0002\n\u0000¨\u0006\u001e"}, d2 = {"Lkotlinx/coroutines/DispatchedTask;", "T", "Lkotlinx/coroutines/scheduling/Task;", "Lkotlinx/coroutines/SchedulerTask;", "resumeMode", HttpUrl.FRAGMENT_ENCODE_SET, "(I)V", "delegate", "Lkotlin/coroutines/Continuation;", "getDelegate$kotlinx_coroutines_core", "()Lkotlin/coroutines/Continuation;", "cancelCompletedResult", HttpUrl.FRAGMENT_ENCODE_SET, "takenState", HttpUrl.FRAGMENT_ENCODE_SET, "cause", HttpUrl.FRAGMENT_ENCODE_SET, "cancelCompletedResult$kotlinx_coroutines_core", "getExceptionalResult", "state", "getExceptionalResult$kotlinx_coroutines_core", "getSuccessfulResult", "getSuccessfulResult$kotlinx_coroutines_core", "(Ljava/lang/Object;)Ljava/lang/Object;", "handleFatalException", "exception", "finallyException", "run", "takeState", "takeState$kotlinx_coroutines_core", "kotlinx-coroutines-core"}, k = 1, mv = {1, 6, 0}, xi = 48)
/* loaded from: classes.dex */
public abstract class DispatchedTask<T> extends Task {
    public int resumeMode;

    public DispatchedTask(int resumeMode) {
        this.resumeMode = resumeMode;
    }

    public void cancelCompletedResult$kotlinx_coroutines_core(Object takenState, Throwable cause) {
    }

    public abstract Continuation<T> getDelegate$kotlinx_coroutines_core();

    public Throwable getExceptionalResult$kotlinx_coroutines_core(Object state) {
        CompletedExceptionally completedExceptionally = state instanceof CompletedExceptionally ? (CompletedExceptionally) state : null;
        if (completedExceptionally == null) {
            return null;
        }
        return completedExceptionally.cause;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public <T> T getSuccessfulResult$kotlinx_coroutines_core(Object state) {
        return state;
    }

    public final void handleFatalException(Throwable exception, Throwable finallyException) {
        if (exception == null && finallyException == null) {
            return;
        }
        if (exception != null && finallyException != null) {
            kotlin.ExceptionsKt.addSuppressed(exception, finallyException);
        }
        Throwable th = exception == null ? finallyException : exception;
        Intrinsics.checkNotNull(th);
        CoroutineExceptionHandlerKt.handleCoroutineException(getDelegate$kotlinx_coroutines_core().getContext(), new CoroutinesInternalError("Fatal exception in coroutines machinery for " + this + ". Please read KDoc to 'handleFatalException' method and report this incident to maintainers", th));
    }

    @Override // java.lang.Runnable
    public final void run() {
        Object m44constructorimpl;
        UndispatchedCoroutine<?> undispatchedCoroutine;
        CancellationException cancellationException;
        if (DebugKt.getASSERTIONS_ENABLED()) {
            if (!(this.resumeMode != -1)) {
                throw new AssertionError();
            }
        }
        TaskContext taskContext = this.taskContext;
        Throwable th = null;
        try {
            DispatchedContinuation dispatchedContinuation = (DispatchedContinuation) getDelegate$kotlinx_coroutines_core();
            Continuation<T> continuation = dispatchedContinuation.continuation;
            Object obj = dispatchedContinuation.countOrElement;
            CoroutineContext context = continuation.getContext();
            Object updateThreadContext = ThreadContextKt.updateThreadContext(context, obj);
            Job job = null;
            if (updateThreadContext != ThreadContextKt.NO_THREAD_ELEMENTS) {
                undispatchedCoroutine = CoroutineContextKt.updateUndispatchedCompletion(continuation, context, updateThreadContext);
            } else {
                undispatchedCoroutine = null;
            }
            UndispatchedCoroutine<?> undispatchedCoroutine2 = undispatchedCoroutine;
            try {
                CoroutineContext context2 = continuation.getContext();
                Object takeState$kotlinx_coroutines_core = takeState$kotlinx_coroutines_core();
                Throwable exceptionalResult$kotlinx_coroutines_core = getExceptionalResult$kotlinx_coroutines_core(takeState$kotlinx_coroutines_core);
                if (exceptionalResult$kotlinx_coroutines_core == null) {
                    try {
                        if (DispatchedTaskKt.isCancellableMode(this.resumeMode)) {
                            job = (Job) context2.get(Job.INSTANCE);
                        }
                    } catch (Throwable th2) {
                        th = th2;
                        if (undispatchedCoroutine2 != null) {
                        }
                        ThreadContextKt.restoreThreadContext(context, updateThreadContext);
                        throw th;
                    }
                }
                try {
                    if (job != null && !job.isActive()) {
                        CancellationException cancellationException2 = job.getCancellationException();
                        cancelCompletedResult$kotlinx_coroutines_core(takeState$kotlinx_coroutines_core, cancellationException2);
                        Result.Companion companion = Result.INSTANCE;
                        if (DebugKt.getRECOVER_STACK_TRACES()) {
                            try {
                                if (continuation instanceof CoroutineStackFrame) {
                                    cancellationException = StackTraceRecoveryKt.recoverFromStackFrame(cancellationException2, (CoroutineStackFrame) continuation);
                                    continuation.resumeWith(Result.m44constructorimpl(ResultKt.createFailure(cancellationException)));
                                }
                            } catch (Throwable th3) {
                                th = th3;
                                if (undispatchedCoroutine2 != null || undispatchedCoroutine2.clearThreadContext()) {
                                    ThreadContextKt.restoreThreadContext(context, updateThreadContext);
                                }
                                throw th;
                            }
                        }
                        cancellationException = cancellationException2;
                        continuation.resumeWith(Result.m44constructorimpl(ResultKt.createFailure(cancellationException)));
                    } else if (exceptionalResult$kotlinx_coroutines_core != null) {
                        Result.Companion companion2 = Result.INSTANCE;
                        continuation.resumeWith(Result.m44constructorimpl(ResultKt.createFailure(exceptionalResult$kotlinx_coroutines_core)));
                    } else {
                        T successfulResult$kotlinx_coroutines_core = getSuccessfulResult$kotlinx_coroutines_core(takeState$kotlinx_coroutines_core);
                        Result.Companion companion3 = Result.INSTANCE;
                        continuation.resumeWith(Result.m44constructorimpl(successfulResult$kotlinx_coroutines_core));
                    }
                    Unit unit = Unit.INSTANCE;
                    if (undispatchedCoroutine2 == null || undispatchedCoroutine2.clearThreadContext()) {
                        ThreadContextKt.restoreThreadContext(context, updateThreadContext);
                    }
                    try {
                        Result.Companion companion4 = Result.INSTANCE;
                        DispatchedTask<T> dispatchedTask = this;
                        taskContext.afterTask();
                        m44constructorimpl = Result.m44constructorimpl(Unit.INSTANCE);
                    } catch (Throwable th4) {
                        th = th4;
                        Result.Companion companion5 = Result.INSTANCE;
                        m44constructorimpl = Result.m44constructorimpl(ResultKt.createFailure(th));
                        handleFatalException(th, Result.m47exceptionOrNullimpl(m44constructorimpl));
                    }
                } catch (Throwable th5) {
                    th = th5;
                }
            } catch (Throwable th6) {
                th = th6;
            }
        } catch (Throwable th7) {
            th = th7;
            try {
                Result.Companion companion6 = Result.INSTANCE;
                DispatchedTask<T> dispatchedTask2 = this;
                taskContext.afterTask();
                m44constructorimpl = Result.m44constructorimpl(Unit.INSTANCE);
            } catch (Throwable th8) {
                th = th8;
                Result.Companion companion52 = Result.INSTANCE;
                m44constructorimpl = Result.m44constructorimpl(ResultKt.createFailure(th));
                handleFatalException(th, Result.m47exceptionOrNullimpl(m44constructorimpl));
            }
        }
        handleFatalException(th, Result.m47exceptionOrNullimpl(m44constructorimpl));
    }

    public abstract Object takeState$kotlinx_coroutines_core();
}
