package okio;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import kotlin.Deprecated;
import kotlin.DeprecationLevel;
import kotlin.Metadata;
import kotlin.ReplaceWith;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.InlineMarker;
import kotlin.jvm.internal.Intrinsics;
import okhttp3.HttpUrl;

/* compiled from: Pipe.kt */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000D\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0006\u0010!\u001a\u00020\"J\u000e\u0010#\u001a\u00020\"2\u0006\u0010\u0017\u001a\u00020\u0010J\r\u0010\u0017\u001a\u00020\u0010H\u0007¢\u0006\u0002\b$J\r\u0010\u001b\u001a\u00020\u001cH\u0007¢\u0006\u0002\b%J&\u0010&\u001a\u00020\"*\u00020\u00102\u0017\u0010'\u001a\u0013\u0012\u0004\u0012\u00020\u0010\u0012\u0004\u0012\u00020\"0(¢\u0006\u0002\b)H\u0082\bR\u0014\u0010\u0005\u001a\u00020\u0006X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u001a\u0010\t\u001a\u00020\nX\u0080\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000eR\u001c\u0010\u000f\u001a\u0004\u0018\u00010\u0010X\u0080\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0011\u0010\u0012\"\u0004\b\u0013\u0010\u0014R\u0014\u0010\u0002\u001a\u00020\u0003X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0016R\u0013\u0010\u0017\u001a\u00020\u00108G¢\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0012R\u001a\u0010\u0018\u001a\u00020\nX\u0080\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0019\u0010\f\"\u0004\b\u001a\u0010\u000eR\u0013\u0010\u001b\u001a\u00020\u001c8G¢\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\u001dR\u001a\u0010\u001e\u001a\u00020\nX\u0080\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001f\u0010\f\"\u0004\b \u0010\u000e¨\u0006*"}, d2 = {"Lokio/Pipe;", HttpUrl.FRAGMENT_ENCODE_SET, "maxBufferSize", HttpUrl.FRAGMENT_ENCODE_SET, "(J)V", "buffer", "Lokio/Buffer;", "getBuffer$okio", "()Lokio/Buffer;", "canceled", HttpUrl.FRAGMENT_ENCODE_SET, "getCanceled$okio", "()Z", "setCanceled$okio", "(Z)V", "foldedSink", "Lokio/Sink;", "getFoldedSink$okio", "()Lokio/Sink;", "setFoldedSink$okio", "(Lokio/Sink;)V", "getMaxBufferSize$okio", "()J", "sink", "sinkClosed", "getSinkClosed$okio", "setSinkClosed$okio", "source", "Lokio/Source;", "()Lokio/Source;", "sourceClosed", "getSourceClosed$okio", "setSourceClosed$okio", "cancel", HttpUrl.FRAGMENT_ENCODE_SET, "fold", "-deprecated_sink", "-deprecated_source", "forward", "block", "Lkotlin/Function1;", "Lkotlin/ExtensionFunctionType;", "okio"}, k = 1, mv = {1, 4, 0})
/* loaded from: classes.dex */
public final class Pipe {
    private final Buffer buffer = new Buffer();
    private boolean canceled;
    private Sink foldedSink;
    private final long maxBufferSize;
    private final Sink sink;
    private boolean sinkClosed;
    private final Source source;
    private boolean sourceClosed;

    public Pipe(long maxBufferSize) {
        this.maxBufferSize = maxBufferSize;
        if (!(maxBufferSize >= 1)) {
            throw new IllegalArgumentException(("maxBufferSize < 1: " + maxBufferSize).toString());
        }
        this.sink = new Sink() { // from class: okio.Pipe$sink$1
            private final Timeout timeout = new Timeout();

            @Override // okio.Sink, java.io.Closeable, java.lang.AutoCloseable
            public void close() {
                boolean hasDeadline;
                Sink sink = (Sink) null;
                synchronized (Pipe.this.getBuffer()) {
                    if (Pipe.this.getSinkClosed()) {
                        return;
                    }
                    Sink foldedSink = Pipe.this.getFoldedSink();
                    if (foldedSink != null) {
                        sink = foldedSink;
                    } else {
                        if (Pipe.this.getSourceClosed() && Pipe.this.getBuffer().size() > 0) {
                            throw new IOException("source is closed");
                        }
                        Pipe.this.setSinkClosed$okio(true);
                        Buffer buffer = Pipe.this.getBuffer();
                        if (buffer == null) {
                            throw new NullPointerException("null cannot be cast to non-null type java.lang.Object");
                        }
                        buffer.notifyAll();
                    }
                    Unit unit = Unit.INSTANCE;
                    if (sink != null) {
                        Pipe pipe = Pipe.this;
                        Sink sink2 = sink;
                        Timeout timeout = sink2.getTimeout();
                        Timeout timeout2 = pipe.sink().getTimeout();
                        long timeoutNanos = timeout.getTimeoutNanos();
                        timeout.timeout(Timeout.INSTANCE.minTimeout(timeout2.getTimeoutNanos(), timeout.getTimeoutNanos()), TimeUnit.NANOSECONDS);
                        if (!timeout.getHasDeadline()) {
                            if (timeout2.getHasDeadline()) {
                                timeout.deadlineNanoTime(timeout2.deadlineNanoTime());
                            }
                            try {
                                sink2.close();
                                if (hasDeadline) {
                                    return;
                                } else {
                                    return;
                                }
                            } finally {
                                timeout.timeout(timeoutNanos, TimeUnit.NANOSECONDS);
                                if (timeout2.getHasDeadline()) {
                                    timeout.clearDeadline();
                                }
                            }
                        }
                        long deadlineNanoTime = timeout.deadlineNanoTime();
                        if (timeout2.getHasDeadline()) {
                            timeout.deadlineNanoTime(Math.min(timeout.deadlineNanoTime(), timeout2.deadlineNanoTime()));
                        }
                        try {
                            sink2.close();
                        } finally {
                            timeout.timeout(timeoutNanos, TimeUnit.NANOSECONDS);
                            if (timeout2.getHasDeadline()) {
                                timeout.deadlineNanoTime(deadlineNanoTime);
                            }
                        }
                    }
                }
            }

            @Override // okio.Sink, java.io.Flushable
            public void flush() {
                boolean hasDeadline;
                Sink sink = (Sink) null;
                synchronized (Pipe.this.getBuffer()) {
                    if ((!Pipe.this.getSinkClosed()) != true) {
                        throw new IllegalStateException("closed".toString());
                    }
                    if (Pipe.this.getCanceled()) {
                        throw new IOException("canceled");
                    }
                    Sink foldedSink = Pipe.this.getFoldedSink();
                    if (foldedSink != null) {
                        sink = foldedSink;
                    } else if (Pipe.this.getSourceClosed() && Pipe.this.getBuffer().size() > 0) {
                        throw new IOException("source is closed");
                    }
                    Unit unit = Unit.INSTANCE;
                }
                if (sink != null) {
                    Pipe pipe = Pipe.this;
                    Sink sink2 = sink;
                    Timeout timeout = sink2.getTimeout();
                    Timeout timeout2 = pipe.sink().getTimeout();
                    long timeoutNanos = timeout.getTimeoutNanos();
                    timeout.timeout(Timeout.INSTANCE.minTimeout(timeout2.getTimeoutNanos(), timeout.getTimeoutNanos()), TimeUnit.NANOSECONDS);
                    if (!timeout.getHasDeadline()) {
                        if (timeout2.getHasDeadline()) {
                            timeout.deadlineNanoTime(timeout2.deadlineNanoTime());
                        }
                        try {
                            sink2.flush();
                            if (hasDeadline) {
                                return;
                            } else {
                                return;
                            }
                        } finally {
                            timeout.timeout(timeoutNanos, TimeUnit.NANOSECONDS);
                            if (timeout2.getHasDeadline()) {
                                timeout.clearDeadline();
                            }
                        }
                    }
                    long deadlineNanoTime = timeout.deadlineNanoTime();
                    if (timeout2.getHasDeadline()) {
                        timeout.deadlineNanoTime(Math.min(timeout.deadlineNanoTime(), timeout2.deadlineNanoTime()));
                    }
                    try {
                        sink2.flush();
                    } finally {
                        timeout.timeout(timeoutNanos, TimeUnit.NANOSECONDS);
                        if (timeout2.getHasDeadline()) {
                            timeout.deadlineNanoTime(deadlineNanoTime);
                        }
                    }
                }
            }

            @Override // okio.Sink
            /* renamed from: timeout, reason: from getter */
            public Timeout getTimeout() {
                return this.timeout;
            }

            @Override // okio.Sink
            public void write(Buffer source, long byteCount) {
                Intrinsics.checkNotNullParameter(source, "source");
                long j = byteCount;
                Sink sink = (Sink) null;
                synchronized (Pipe.this.getBuffer()) {
                    if ((!Pipe.this.getSinkClosed()) != true) {
                        throw new IllegalStateException("closed".toString());
                    }
                    if (Pipe.this.getCanceled()) {
                        throw new IOException("canceled");
                    }
                    while (true) {
                        if (j <= 0) {
                            break;
                        }
                        Sink foldedSink = Pipe.this.getFoldedSink();
                        if (foldedSink != null) {
                            sink = foldedSink;
                            break;
                        }
                        if (Pipe.this.getSourceClosed()) {
                            throw new IOException("source is closed");
                        }
                        long maxBufferSize2 = Pipe.this.getMaxBufferSize() - Pipe.this.getBuffer().size();
                        if (maxBufferSize2 == 0) {
                            this.timeout.waitUntilNotified(Pipe.this.getBuffer());
                            if (Pipe.this.getCanceled()) {
                                throw new IOException("canceled");
                            }
                        } else {
                            long min = Math.min(maxBufferSize2, j);
                            Pipe.this.getBuffer().write(source, min);
                            j -= min;
                            Buffer buffer = Pipe.this.getBuffer();
                            if (buffer == null) {
                                throw new NullPointerException("null cannot be cast to non-null type java.lang.Object");
                            }
                            buffer.notifyAll();
                        }
                    }
                    Unit unit = Unit.INSTANCE;
                }
                if (sink == null) {
                    return;
                }
                Pipe pipe = Pipe.this;
                Sink sink2 = sink;
                Timeout timeout = sink2.getTimeout();
                Timeout timeout2 = pipe.sink().getTimeout();
                long timeoutNanos = timeout.getTimeoutNanos();
                timeout.timeout(Timeout.INSTANCE.minTimeout(timeout2.getTimeoutNanos(), timeout.getTimeoutNanos()), TimeUnit.NANOSECONDS);
                if (timeout.getHasDeadline()) {
                    long deadlineNanoTime = timeout.deadlineNanoTime();
                    if (timeout2.getHasDeadline()) {
                        timeout.deadlineNanoTime(Math.min(timeout.deadlineNanoTime(), timeout2.deadlineNanoTime()));
                    }
                    try {
                        sink2.write(source, j);
                        timeout.timeout(timeoutNanos, TimeUnit.NANOSECONDS);
                        if (timeout2.getHasDeadline()) {
                            timeout.deadlineNanoTime(deadlineNanoTime);
                        }
                    } finally {
                    }
                } else {
                    if (timeout2.getHasDeadline()) {
                        timeout.deadlineNanoTime(timeout2.deadlineNanoTime());
                    }
                    try {
                        sink2.write(source, j);
                        timeout.timeout(timeoutNanos, TimeUnit.NANOSECONDS);
                        if (timeout2.getHasDeadline()) {
                            timeout.clearDeadline();
                        }
                    } finally {
                    }
                }
            }
        };
        this.source = new Source() { // from class: okio.Pipe$source$1
            private final Timeout timeout = new Timeout();

            @Override // okio.Source, java.io.Closeable, java.lang.AutoCloseable
            public void close() {
                synchronized (Pipe.this.getBuffer()) {
                    Pipe.this.setSourceClosed$okio(true);
                    Buffer buffer = Pipe.this.getBuffer();
                    if (buffer == null) {
                        throw new NullPointerException("null cannot be cast to non-null type java.lang.Object");
                    }
                    buffer.notifyAll();
                    Unit unit = Unit.INSTANCE;
                }
            }

            @Override // okio.Source
            public long read(Buffer sink, long byteCount) {
                Intrinsics.checkNotNullParameter(sink, "sink");
                synchronized (Pipe.this.getBuffer()) {
                    if ((!Pipe.this.getSourceClosed()) != true) {
                        throw new IllegalStateException("closed".toString());
                    }
                    if (Pipe.this.getCanceled()) {
                        throw new IOException("canceled");
                    }
                    while (Pipe.this.getBuffer().size() == 0) {
                        if (Pipe.this.getSinkClosed()) {
                            return -1L;
                        }
                        this.timeout.waitUntilNotified(Pipe.this.getBuffer());
                        if (Pipe.this.getCanceled()) {
                            throw new IOException("canceled");
                        }
                    }
                    long read = Pipe.this.getBuffer().read(sink, byteCount);
                    Buffer buffer = Pipe.this.getBuffer();
                    if (buffer == null) {
                        throw new NullPointerException("null cannot be cast to non-null type java.lang.Object");
                    }
                    buffer.notifyAll();
                    return read;
                }
            }

            @Override // okio.Source
            /* renamed from: timeout, reason: from getter */
            public Timeout getTimeout() {
                return this.timeout;
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void forward(Sink $this$forward, Function1<? super Sink, Unit> function1) {
        Timeout timeout = $this$forward.getTimeout();
        Timeout timeout2 = sink().getTimeout();
        long timeoutNanos = timeout.getTimeoutNanos();
        timeout.timeout(Timeout.INSTANCE.minTimeout(timeout2.getTimeoutNanos(), timeout.getTimeoutNanos()), TimeUnit.NANOSECONDS);
        if (!timeout.getHasDeadline()) {
            if (timeout2.getHasDeadline()) {
                timeout.deadlineNanoTime(timeout2.deadlineNanoTime());
            }
            try {
                function1.invoke($this$forward);
                return;
            } finally {
                InlineMarker.finallyStart(1);
                timeout.timeout(timeoutNanos, TimeUnit.NANOSECONDS);
                if (timeout2.getHasDeadline()) {
                    timeout.clearDeadline();
                }
                InlineMarker.finallyEnd(1);
            }
        }
        long deadlineNanoTime = timeout.deadlineNanoTime();
        if (timeout2.getHasDeadline()) {
            timeout.deadlineNanoTime(Math.min(timeout.deadlineNanoTime(), timeout2.deadlineNanoTime()));
        }
        try {
            function1.invoke($this$forward);
        } finally {
            InlineMarker.finallyStart(1);
            timeout.timeout(timeoutNanos, TimeUnit.NANOSECONDS);
            if (timeout2.getHasDeadline()) {
                timeout.deadlineNanoTime(deadlineNanoTime);
            }
            InlineMarker.finallyEnd(1);
        }
    }

    @Deprecated(level = DeprecationLevel.ERROR, message = "moved to val", replaceWith = @ReplaceWith(expression = "sink", imports = {}))
    /* renamed from: -deprecated_sink, reason: not valid java name and from getter */
    public final Sink getSink() {
        return this.sink;
    }

    @Deprecated(level = DeprecationLevel.ERROR, message = "moved to val", replaceWith = @ReplaceWith(expression = "source", imports = {}))
    /* renamed from: -deprecated_source, reason: not valid java name and from getter */
    public final Source getSource() {
        return this.source;
    }

    public final void cancel() {
        synchronized (this.buffer) {
            this.canceled = true;
            this.buffer.clear();
            Buffer buffer = this.buffer;
            if (buffer == null) {
                throw new NullPointerException("null cannot be cast to non-null type java.lang.Object");
            }
            buffer.notifyAll();
            Unit unit = Unit.INSTANCE;
        }
    }

    public final void fold(Sink sink) throws IOException {
        boolean z;
        Buffer buffer;
        Intrinsics.checkNotNullParameter(sink, "sink");
        while (true) {
            synchronized (this.buffer) {
                if (!(this.foldedSink == null)) {
                    throw new IllegalStateException("sink already folded".toString());
                }
                if (this.canceled) {
                    this.foldedSink = sink;
                    throw new IOException("canceled");
                }
                if (this.buffer.exhausted()) {
                    this.sourceClosed = true;
                    this.foldedSink = sink;
                    return;
                }
                z = this.sinkClosed;
                buffer = new Buffer();
                Buffer buffer2 = this.buffer;
                buffer.write(buffer2, buffer2.size());
                Buffer buffer3 = this.buffer;
                if (buffer3 == null) {
                    throw new NullPointerException("null cannot be cast to non-null type java.lang.Object");
                }
                buffer3.notifyAll();
                Unit unit = Unit.INSTANCE;
            }
            try {
                sink.write(buffer, buffer.size());
                if (z) {
                    sink.close();
                } else {
                    sink.flush();
                }
            } catch (Throwable th) {
                synchronized (this.buffer) {
                    this.sourceClosed = true;
                    Buffer buffer4 = this.buffer;
                    if (buffer4 == null) {
                        throw new NullPointerException("null cannot be cast to non-null type java.lang.Object");
                    }
                    buffer4.notifyAll();
                    Unit unit2 = Unit.INSTANCE;
                    throw th;
                }
            }
        }
    }

    /* renamed from: getBuffer$okio, reason: from getter */
    public final Buffer getBuffer() {
        return this.buffer;
    }

    /* renamed from: getCanceled$okio, reason: from getter */
    public final boolean getCanceled() {
        return this.canceled;
    }

    /* renamed from: getFoldedSink$okio, reason: from getter */
    public final Sink getFoldedSink() {
        return this.foldedSink;
    }

    /* renamed from: getMaxBufferSize$okio, reason: from getter */
    public final long getMaxBufferSize() {
        return this.maxBufferSize;
    }

    /* renamed from: getSinkClosed$okio, reason: from getter */
    public final boolean getSinkClosed() {
        return this.sinkClosed;
    }

    /* renamed from: getSourceClosed$okio, reason: from getter */
    public final boolean getSourceClosed() {
        return this.sourceClosed;
    }

    public final void setCanceled$okio(boolean z) {
        this.canceled = z;
    }

    public final void setFoldedSink$okio(Sink sink) {
        this.foldedSink = sink;
    }

    public final void setSinkClosed$okio(boolean z) {
        this.sinkClosed = z;
    }

    public final void setSourceClosed$okio(boolean z) {
        this.sourceClosed = z;
    }

    public final Sink sink() {
        return this.sink;
    }

    public final Source source() {
        return this.source;
    }
}
