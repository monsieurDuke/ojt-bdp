package okhttp3.internal.cache2;

import java.io.IOException;
import java.nio.channels.FileChannel;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import okhttp3.HttpUrl;
import okio.Buffer;

/* compiled from: FileOperator.kt */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u001e\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\bJ\u001e\u0010\f\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\r\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\bR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u000e"}, d2 = {"Lokhttp3/internal/cache2/FileOperator;", HttpUrl.FRAGMENT_ENCODE_SET, "fileChannel", "Ljava/nio/channels/FileChannel;", "(Ljava/nio/channels/FileChannel;)V", "read", HttpUrl.FRAGMENT_ENCODE_SET, "pos", HttpUrl.FRAGMENT_ENCODE_SET, "sink", "Lokio/Buffer;", "byteCount", "write", "source", "okhttp"}, k = 1, mv = {1, 4, 0})
/* loaded from: classes.dex */
public final class FileOperator {
    private final FileChannel fileChannel;

    public FileOperator(FileChannel fileChannel) {
        Intrinsics.checkNotNullParameter(fileChannel, "fileChannel");
        this.fileChannel = fileChannel;
    }

    public final void read(long pos, Buffer sink, long byteCount) {
        Intrinsics.checkNotNullParameter(sink, "sink");
        if (byteCount < 0) {
            throw new IndexOutOfBoundsException();
        }
        long j = pos;
        long j2 = byteCount;
        while (j2 > 0) {
            long transferTo = this.fileChannel.transferTo(j, j2, sink);
            j += transferTo;
            j2 -= transferTo;
        }
    }

    public final void write(long pos, Buffer source, long byteCount) throws IOException {
        Intrinsics.checkNotNullParameter(source, "source");
        if (byteCount < 0 || byteCount > source.size()) {
            throw new IndexOutOfBoundsException();
        }
        long j = pos;
        long j2 = byteCount;
        while (j2 > 0) {
            long transferFrom = this.fileChannel.transferFrom(source, j, j2);
            j += transferFrom;
            j2 -= transferFrom;
        }
    }
}