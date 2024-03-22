package okio;

import java.io.IOException;
import java.io.InputStream;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import okhttp3.HttpUrl;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: JvmOkio.kt */
@Metadata(bv = {1, 0, 3}, d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\b\u0002\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\b\u0010\u0007\u001a\u00020\bH\u0016J\u0018\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\nH\u0016J\b\u0010\u0004\u001a\u00020\u0005H\u0016J\b\u0010\u000e\u001a\u00020\u000fH\u0016R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0010"}, d2 = {"Lokio/InputStreamSource;", "Lokio/Source;", "input", "Ljava/io/InputStream;", "timeout", "Lokio/Timeout;", "(Ljava/io/InputStream;Lokio/Timeout;)V", "close", HttpUrl.FRAGMENT_ENCODE_SET, "read", HttpUrl.FRAGMENT_ENCODE_SET, "sink", "Lokio/Buffer;", "byteCount", "toString", HttpUrl.FRAGMENT_ENCODE_SET, "okio"}, k = 1, mv = {1, 4, 0})
/* renamed from: okio.InputStreamSource, reason: from toString */
/* loaded from: classes.dex */
public final class source implements Source {
    private final InputStream input;
    private final Timeout timeout;

    public source(InputStream input, Timeout timeout) {
        Intrinsics.checkNotNullParameter(input, "input");
        Intrinsics.checkNotNullParameter(timeout, "timeout");
        this.input = input;
        this.timeout = timeout;
    }

    @Override // okio.Source, java.io.Closeable, java.lang.AutoCloseable
    public void close() {
        this.input.close();
    }

    @Override // okio.Source
    public long read(Buffer sink, long byteCount) {
        Intrinsics.checkNotNullParameter(sink, "sink");
        if (byteCount == 0) {
            return 0L;
        }
        if (!(byteCount >= 0)) {
            throw new IllegalArgumentException(("byteCount < 0: " + byteCount).toString());
        }
        try {
            this.timeout.throwIfReached();
            Segment writableSegment$okio = sink.writableSegment$okio(1);
            int read = this.input.read(writableSegment$okio.data, writableSegment$okio.limit, (int) Math.min(byteCount, 8192 - writableSegment$okio.limit));
            if (read != -1) {
                writableSegment$okio.limit += read;
                sink.setSize$okio(sink.size() + read);
                return read;
            }
            if (writableSegment$okio.pos != writableSegment$okio.limit) {
                return -1L;
            }
            sink.head = writableSegment$okio.pop();
            SegmentPool.recycle(writableSegment$okio);
            return -1L;
        } catch (AssertionError e) {
            if (Okio.isAndroidGetsocknameError(e)) {
                throw new IOException(e);
            }
            throw e;
        }
    }

    @Override // okio.Source
    /* renamed from: timeout, reason: from getter */
    public Timeout getTimeout() {
        return this.timeout;
    }

    public String toString() {
        return "source(" + this.input + ')';
    }
}
