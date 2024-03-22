package okio.internal;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import androidx.work.WorkRequest;
import java.io.EOFException;
import kotlin.Metadata;
import kotlin.UByte;
import kotlin.collections.ArraysKt;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.ByteCompanionObject;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.Typography;
import mt.Log229316;
import mt.Log5ECF72;
import mt.LogE84000;
import okhttp3.HttpUrl;
import okhttp3.internal.connection.RealConnection;
import okio.Buffer;
import okio.ByteString;
import okio.Options;
import okio.Platform;
import okio.Segment;
import okio.SegmentPool;
import okio.SegmentedByteString;
import okio.Sink;
import okio.Source;
import okio.Utf8;
import okio.Util;

/* compiled from: 01F2.java */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000v\n\u0000\n\u0002\u0010\u0012\n\u0002\b\u0003\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0005\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\n\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0015\n\u0002\u0018\u0002\n\u0002\b\u0004\u001a0\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\b2\u0006\u0010\u000e\u001a\u00020\u00012\u0006\u0010\u000f\u001a\u00020\b2\u0006\u0010\u0010\u001a\u00020\bH\u0000\u001a\r\u0010\u0011\u001a\u00020\u0012*\u00020\u0013H\u0080\b\u001a\r\u0010\u0014\u001a\u00020\u0005*\u00020\u0013H\u0080\b\u001a\r\u0010\u0015\u001a\u00020\u0013*\u00020\u0013H\u0080\b\u001a%\u0010\u0016\u001a\u00020\u0013*\u00020\u00132\u0006\u0010\u0017\u001a\u00020\u00132\u0006\u0010\u0018\u001a\u00020\u00052\u0006\u0010\u0019\u001a\u00020\u0005H\u0080\b\u001a\u0017\u0010\u001a\u001a\u00020\n*\u00020\u00132\b\u0010\u001b\u001a\u0004\u0018\u00010\u001cH\u0080\b\u001a\u0015\u0010\u001d\u001a\u00020\u001e*\u00020\u00132\u0006\u0010\u001f\u001a\u00020\u0005H\u0080\b\u001a\r\u0010 \u001a\u00020\b*\u00020\u0013H\u0080\b\u001a%\u0010!\u001a\u00020\u0005*\u00020\u00132\u0006\u0010\"\u001a\u00020\u001e2\u0006\u0010#\u001a\u00020\u00052\u0006\u0010$\u001a\u00020\u0005H\u0080\b\u001a\u001d\u0010!\u001a\u00020\u0005*\u00020\u00132\u0006\u0010\u000e\u001a\u00020%2\u0006\u0010#\u001a\u00020\u0005H\u0080\b\u001a\u001d\u0010&\u001a\u00020\u0005*\u00020\u00132\u0006\u0010'\u001a\u00020%2\u0006\u0010#\u001a\u00020\u0005H\u0080\b\u001a-\u0010(\u001a\u00020\n*\u00020\u00132\u0006\u0010\u0018\u001a\u00020\u00052\u0006\u0010\u000e\u001a\u00020%2\u0006\u0010\u000f\u001a\u00020\b2\u0006\u0010\u0019\u001a\u00020\bH\u0080\b\u001a\u0015\u0010)\u001a\u00020\b*\u00020\u00132\u0006\u0010*\u001a\u00020\u0001H\u0080\b\u001a%\u0010)\u001a\u00020\b*\u00020\u00132\u0006\u0010*\u001a\u00020\u00012\u0006\u0010\u0018\u001a\u00020\b2\u0006\u0010\u0019\u001a\u00020\bH\u0080\b\u001a\u001d\u0010)\u001a\u00020\u0005*\u00020\u00132\u0006\u0010*\u001a\u00020\u00132\u0006\u0010\u0019\u001a\u00020\u0005H\u0080\b\u001a\u0015\u0010+\u001a\u00020\u0005*\u00020\u00132\u0006\u0010*\u001a\u00020,H\u0080\b\u001a\r\u0010-\u001a\u00020\u001e*\u00020\u0013H\u0080\b\u001a\r\u0010.\u001a\u00020\u0001*\u00020\u0013H\u0080\b\u001a\u0015\u0010.\u001a\u00020\u0001*\u00020\u00132\u0006\u0010\u0019\u001a\u00020\u0005H\u0080\b\u001a\r\u0010/\u001a\u00020%*\u00020\u0013H\u0080\b\u001a\u0015\u0010/\u001a\u00020%*\u00020\u00132\u0006\u0010\u0019\u001a\u00020\u0005H\u0080\b\u001a\r\u00100\u001a\u00020\u0005*\u00020\u0013H\u0080\b\u001a\u0015\u00101\u001a\u00020\u0012*\u00020\u00132\u0006\u0010*\u001a\u00020\u0001H\u0080\b\u001a\u001d\u00101\u001a\u00020\u0012*\u00020\u00132\u0006\u0010*\u001a\u00020\u00132\u0006\u0010\u0019\u001a\u00020\u0005H\u0080\b\u001a\r\u00102\u001a\u00020\u0005*\u00020\u0013H\u0080\b\u001a\r\u00103\u001a\u00020\b*\u00020\u0013H\u0080\b\u001a\r\u00104\u001a\u00020\u0005*\u00020\u0013H\u0080\b\u001a\r\u00105\u001a\u000206*\u00020\u0013H\u0080\b\u001a\u0015\u00107\u001a\u000208*\u00020\u00132\u0006\u0010\u0019\u001a\u00020\u0005H\u0080\b\u001a\r\u00109\u001a\u00020\b*\u00020\u0013H\u0080\b\u001a\u000f\u0010:\u001a\u0004\u0018\u000108*\u00020\u0013H\u0080\b\u001a\u0015\u0010;\u001a\u000208*\u00020\u00132\u0006\u0010<\u001a\u00020\u0005H\u0080\b\u001a\u0015\u0010=\u001a\u00020\b*\u00020\u00132\u0006\u0010>\u001a\u00020?H\u0080\b\u001a\u0015\u0010@\u001a\u00020\u0012*\u00020\u00132\u0006\u0010\u0019\u001a\u00020\u0005H\u0080\b\u001a\r\u0010A\u001a\u00020%*\u00020\u0013H\u0080\b\u001a\u0015\u0010A\u001a\u00020%*\u00020\u00132\u0006\u0010\u0019\u001a\u00020\bH\u0080\b\u001a\u0015\u0010B\u001a\u00020\f*\u00020\u00132\u0006\u0010C\u001a\u00020\bH\u0080\b\u001a\u0015\u0010D\u001a\u00020\u0013*\u00020\u00132\u0006\u0010E\u001a\u00020\u0001H\u0080\b\u001a%\u0010D\u001a\u00020\u0013*\u00020\u00132\u0006\u0010E\u001a\u00020\u00012\u0006\u0010\u0018\u001a\u00020\b2\u0006\u0010\u0019\u001a\u00020\bH\u0080\b\u001a\u001d\u0010D\u001a\u00020\u0012*\u00020\u00132\u0006\u0010E\u001a\u00020\u00132\u0006\u0010\u0019\u001a\u00020\u0005H\u0080\b\u001a)\u0010D\u001a\u00020\u0013*\u00020\u00132\u0006\u0010F\u001a\u00020%2\b\b\u0002\u0010\u0018\u001a\u00020\b2\b\b\u0002\u0010\u0019\u001a\u00020\bH\u0080\b\u001a\u001d\u0010D\u001a\u00020\u0013*\u00020\u00132\u0006\u0010E\u001a\u00020G2\u0006\u0010\u0019\u001a\u00020\u0005H\u0080\b\u001a\u0015\u0010H\u001a\u00020\u0005*\u00020\u00132\u0006\u0010E\u001a\u00020GH\u0080\b\u001a\u0015\u0010I\u001a\u00020\u0013*\u00020\u00132\u0006\u0010\"\u001a\u00020\bH\u0080\b\u001a\u0015\u0010J\u001a\u00020\u0013*\u00020\u00132\u0006\u0010K\u001a\u00020\u0005H\u0080\b\u001a\u0015\u0010L\u001a\u00020\u0013*\u00020\u00132\u0006\u0010K\u001a\u00020\u0005H\u0080\b\u001a\u0015\u0010M\u001a\u00020\u0013*\u00020\u00132\u0006\u0010N\u001a\u00020\bH\u0080\b\u001a\u0015\u0010O\u001a\u00020\u0013*\u00020\u00132\u0006\u0010K\u001a\u00020\u0005H\u0080\b\u001a\u0015\u0010P\u001a\u00020\u0013*\u00020\u00132\u0006\u0010Q\u001a\u00020\bH\u0080\b\u001a%\u0010R\u001a\u00020\u0013*\u00020\u00132\u0006\u0010S\u001a\u0002082\u0006\u0010T\u001a\u00020\b2\u0006\u0010U\u001a\u00020\bH\u0080\b\u001a\u0015\u0010V\u001a\u00020\u0013*\u00020\u00132\u0006\u0010W\u001a\u00020\bH\u0080\b\u001a\u0014\u0010X\u001a\u000208*\u00020\u00132\u0006\u0010Y\u001a\u00020\u0005H\u0000\u001a?\u0010Z\u001a\u0002H[\"\u0004\b\u0000\u0010[*\u00020\u00132\u0006\u0010#\u001a\u00020\u00052\u001a\u0010\\\u001a\u0016\u0012\u0006\u0012\u0004\u0018\u00010\f\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u0002H[0]H\u0080\bø\u0001\u0000¢\u0006\u0002\u0010^\u001a\u001e\u0010_\u001a\u00020\b*\u00020\u00132\u0006\u0010>\u001a\u00020?2\b\b\u0002\u0010`\u001a\u00020\nH\u0000\"\u0014\u0010\u0000\u001a\u00020\u0001X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0002\u0010\u0003\"\u000e\u0010\u0004\u001a\u00020\u0005X\u0080T¢\u0006\u0002\n\u0000\"\u000e\u0010\u0006\u001a\u00020\u0005X\u0080T¢\u0006\u0002\n\u0000\"\u000e\u0010\u0007\u001a\u00020\bX\u0080T¢\u0006\u0002\n\u0000\u0082\u0002\u0007\n\u0005\b\u009920\u0001¨\u0006a"}, d2 = {"HEX_DIGIT_BYTES", HttpUrl.FRAGMENT_ENCODE_SET, "getHEX_DIGIT_BYTES", "()[B", "OVERFLOW_DIGIT_START", HttpUrl.FRAGMENT_ENCODE_SET, "OVERFLOW_ZONE", "SEGMENTING_THRESHOLD", HttpUrl.FRAGMENT_ENCODE_SET, "rangeEquals", HttpUrl.FRAGMENT_ENCODE_SET, "segment", "Lokio/Segment;", "segmentPos", "bytes", "bytesOffset", "bytesLimit", "commonClear", HttpUrl.FRAGMENT_ENCODE_SET, "Lokio/Buffer;", "commonCompleteSegmentByteCount", "commonCopy", "commonCopyTo", "out", TypedValues.CycleType.S_WAVE_OFFSET, "byteCount", "commonEquals", "other", HttpUrl.FRAGMENT_ENCODE_SET, "commonGet", HttpUrl.FRAGMENT_ENCODE_SET, "pos", "commonHashCode", "commonIndexOf", "b", "fromIndex", "toIndex", "Lokio/ByteString;", "commonIndexOfElement", "targetBytes", "commonRangeEquals", "commonRead", "sink", "commonReadAll", "Lokio/Sink;", "commonReadByte", "commonReadByteArray", "commonReadByteString", "commonReadDecimalLong", "commonReadFully", "commonReadHexadecimalUnsignedLong", "commonReadInt", "commonReadLong", "commonReadShort", HttpUrl.FRAGMENT_ENCODE_SET, "commonReadUtf8", HttpUrl.FRAGMENT_ENCODE_SET, "commonReadUtf8CodePoint", "commonReadUtf8Line", "commonReadUtf8LineStrict", "limit", "commonSelect", "options", "Lokio/Options;", "commonSkip", "commonSnapshot", "commonWritableSegment", "minimumCapacity", "commonWrite", "source", "byteString", "Lokio/Source;", "commonWriteAll", "commonWriteByte", "commonWriteDecimalLong", "v", "commonWriteHexadecimalUnsignedLong", "commonWriteInt", "i", "commonWriteLong", "commonWriteShort", "s", "commonWriteUtf8", TypedValues.Custom.S_STRING, "beginIndex", "endIndex", "commonWriteUtf8CodePoint", "codePoint", "readUtf8Line", "newline", "seek", "T", "lambda", "Lkotlin/Function2;", "(Lokio/Buffer;JLkotlin/jvm/functions/Function2;)Ljava/lang/Object;", "selectPrefix", "selectTruncated", "okio"}, k = 2, mv = {1, 4, 0})
/* loaded from: classes.dex */
public final class BufferKt {
    private static final byte[] HEX_DIGIT_BYTES = Platform.asUtf8ToByteArray("0123456789abcdef");
    public static final long OVERFLOW_DIGIT_START = -7;
    public static final long OVERFLOW_ZONE = -922337203685477580L;
    public static final int SEGMENTING_THRESHOLD = 4096;

    public static final void commonClear(Buffer commonClear) {
        Intrinsics.checkNotNullParameter(commonClear, "$this$commonClear");
        commonClear.skip(commonClear.size());
    }

    public static final long commonCompleteSegmentByteCount(Buffer commonCompleteSegmentByteCount) {
        Intrinsics.checkNotNullParameter(commonCompleteSegmentByteCount, "$this$commonCompleteSegmentByteCount");
        long size = commonCompleteSegmentByteCount.size();
        if (size == 0) {
            return 0L;
        }
        Segment segment = commonCompleteSegmentByteCount.head;
        Intrinsics.checkNotNull(segment);
        Segment segment2 = segment.prev;
        Intrinsics.checkNotNull(segment2);
        return (segment2.limit >= 8192 || !segment2.owner) ? size : size - (segment2.limit - segment2.pos);
    }

    public static final Buffer commonCopy(Buffer commonCopy) {
        Intrinsics.checkNotNullParameter(commonCopy, "$this$commonCopy");
        Buffer buffer = new Buffer();
        if (commonCopy.size() == 0) {
            return buffer;
        }
        Segment segment = commonCopy.head;
        Intrinsics.checkNotNull(segment);
        Segment sharedCopy = segment.sharedCopy();
        buffer.head = sharedCopy;
        sharedCopy.prev = buffer.head;
        sharedCopy.next = sharedCopy.prev;
        for (Segment segment2 = segment.next; segment2 != segment; segment2 = segment2.next) {
            Segment segment3 = sharedCopy.prev;
            Intrinsics.checkNotNull(segment3);
            Intrinsics.checkNotNull(segment2);
            segment3.push(segment2.sharedCopy());
        }
        buffer.setSize$okio(commonCopy.size());
        return buffer;
    }

    public static final Buffer commonCopyTo(Buffer commonCopyTo, Buffer out, long offset, long byteCount) {
        Intrinsics.checkNotNullParameter(commonCopyTo, "$this$commonCopyTo");
        Intrinsics.checkNotNullParameter(out, "out");
        long j = offset;
        long j2 = byteCount;
        Util.checkOffsetAndCount(commonCopyTo.size(), j, j2);
        if (j2 == 0) {
            return commonCopyTo;
        }
        out.setSize$okio(out.size() + j2);
        Segment segment = commonCopyTo.head;
        while (true) {
            Intrinsics.checkNotNull(segment);
            if (j < segment.limit - segment.pos) {
                break;
            }
            j -= segment.limit - segment.pos;
            segment = segment.next;
        }
        while (j2 > 0) {
            Intrinsics.checkNotNull(segment);
            Segment sharedCopy = segment.sharedCopy();
            sharedCopy.pos += (int) j;
            sharedCopy.limit = Math.min(sharedCopy.pos + ((int) j2), sharedCopy.limit);
            if (out.head == null) {
                sharedCopy.prev = sharedCopy;
                sharedCopy.next = sharedCopy.prev;
                out.head = sharedCopy.next;
            } else {
                Segment segment2 = out.head;
                Intrinsics.checkNotNull(segment2);
                Segment segment3 = segment2.prev;
                Intrinsics.checkNotNull(segment3);
                segment3.push(sharedCopy);
            }
            j2 -= sharedCopy.limit - sharedCopy.pos;
            j = 0;
            segment = segment.next;
        }
        return commonCopyTo;
    }

    public static final boolean commonEquals(Buffer commonEquals, Object other) {
        Intrinsics.checkNotNullParameter(commonEquals, "$this$commonEquals");
        if (commonEquals == other) {
            return true;
        }
        if (!(other instanceof Buffer) || commonEquals.size() != ((Buffer) other).size()) {
            return false;
        }
        if (commonEquals.size() == 0) {
            return true;
        }
        Segment segment = commonEquals.head;
        Intrinsics.checkNotNull(segment);
        Segment segment2 = ((Buffer) other).head;
        Intrinsics.checkNotNull(segment2);
        int i = segment.pos;
        int i2 = segment2.pos;
        long j = 0;
        while (j < commonEquals.size()) {
            long min = Math.min(segment.limit - i, segment2.limit - i2);
            for (long j2 = 0; j2 < min; j2++) {
                int i3 = i + 1;
                int i4 = i2 + 1;
                if (segment.data[i] != segment2.data[i2]) {
                    return false;
                }
                i = i3;
                i2 = i4;
            }
            if (i == segment.limit) {
                Segment segment3 = segment.next;
                Intrinsics.checkNotNull(segment3);
                segment = segment3;
                i = segment.pos;
            }
            if (i2 == segment2.limit) {
                Segment segment4 = segment2.next;
                Intrinsics.checkNotNull(segment4);
                segment2 = segment4;
                i2 = segment2.pos;
            }
            j += min;
        }
        return true;
    }

    public static final byte commonGet(Buffer commonGet, long pos) {
        Intrinsics.checkNotNullParameter(commonGet, "$this$commonGet");
        Util.checkOffsetAndCount(commonGet.size(), pos, 1L);
        Segment segment = commonGet.head;
        if (segment == null) {
            Segment segment2 = (Segment) null;
            Intrinsics.checkNotNull(segment2);
            return segment2.data[(int) ((segment2.pos + pos) - (-1))];
        }
        if (commonGet.size() - pos < pos) {
            long size = commonGet.size();
            while (size > pos) {
                Segment segment3 = segment.prev;
                Intrinsics.checkNotNull(segment3);
                segment = segment3;
                size -= segment.limit - segment.pos;
            }
            Segment segment4 = segment;
            Intrinsics.checkNotNull(segment4);
            return segment4.data[(int) ((segment4.pos + pos) - size)];
        }
        long j = 0;
        while (true) {
            long j2 = (segment.limit - segment.pos) + j;
            if (j2 > pos) {
                Segment segment5 = segment;
                Intrinsics.checkNotNull(segment5);
                return segment5.data[(int) ((segment5.pos + pos) - j)];
            }
            Segment segment6 = segment.next;
            Intrinsics.checkNotNull(segment6);
            segment = segment6;
            j = j2;
        }
    }

    public static final int commonHashCode(Buffer commonHashCode) {
        Intrinsics.checkNotNullParameter(commonHashCode, "$this$commonHashCode");
        Segment segment = commonHashCode.head;
        if (segment == null) {
            return 0;
        }
        int i = 1;
        do {
            int i2 = segment.limit;
            for (int i3 = segment.pos; i3 < i2; i3++) {
                i = (i * 31) + segment.data[i3];
            }
            Segment segment2 = segment.next;
            Intrinsics.checkNotNull(segment2);
            segment = segment2;
        } while (segment != commonHashCode.head);
        return i;
    }

    public static final long commonIndexOf(Buffer commonIndexOf, byte b, long fromIndex, long toIndex) {
        Intrinsics.checkNotNullParameter(commonIndexOf, "$this$commonIndexOf");
        long j = fromIndex;
        long j2 = toIndex;
        if (!(0 <= j && j2 >= j)) {
            throw new IllegalArgumentException(("size=" + commonIndexOf.size() + " fromIndex=" + j + " toIndex=" + j2).toString());
        }
        if (j2 > commonIndexOf.size()) {
            j2 = commonIndexOf.size();
        }
        if (j == j2) {
            return -1L;
        }
        Buffer buffer = commonIndexOf;
        boolean z = false;
        Segment segment = buffer.head;
        if (segment == null) {
            return -1L;
        }
        if (buffer.size() - j >= j) {
            long j3 = 0;
            while (true) {
                long j4 = (segment.limit - segment.pos) + j3;
                if (j4 > j) {
                    break;
                }
                Segment segment2 = segment.next;
                Intrinsics.checkNotNull(segment2);
                segment = segment2;
                j3 = j4;
            }
            Segment segment3 = segment;
            long j5 = j3;
            if (segment3 == null) {
                return -1L;
            }
            Segment segment4 = segment3;
            long j6 = j5;
            while (j6 < j2) {
                byte[] bArr = segment4.data;
                long j7 = j3;
                Segment segment5 = segment3;
                long j8 = j5;
                int min = (int) Math.min(segment4.limit, (segment4.pos + j2) - j6);
                for (int i = (int) ((segment4.pos + j) - j6); i < min; i++) {
                    if (bArr[i] == b) {
                        return (i - segment4.pos) + j6;
                    }
                }
                j6 += segment4.limit - segment4.pos;
                j = j6;
                Segment segment6 = segment4.next;
                Intrinsics.checkNotNull(segment6);
                segment4 = segment6;
                j3 = j7;
                segment3 = segment5;
                j5 = j8;
            }
            return -1L;
        }
        long size = buffer.size();
        while (size > j) {
            Segment segment7 = segment.prev;
            Intrinsics.checkNotNull(segment7);
            segment = segment7;
            size -= segment.limit - segment.pos;
        }
        Segment segment8 = segment;
        long j9 = size;
        boolean z2 = false;
        if (segment8 == null) {
            return -1L;
        }
        long j10 = j9;
        Segment segment9 = segment8;
        while (j10 < j2) {
            Buffer buffer2 = buffer;
            byte[] bArr2 = segment9.data;
            Segment segment10 = segment8;
            boolean z3 = z2;
            boolean z4 = z;
            Segment segment11 = segment;
            int min2 = (int) Math.min(segment9.limit, (segment9.pos + j2) - j10);
            for (int i2 = (int) ((segment9.pos + j) - j10); i2 < min2; i2++) {
                if (bArr2[i2] == b) {
                    return (i2 - segment9.pos) + j10;
                }
            }
            j10 += segment9.limit - segment9.pos;
            j = j10;
            Segment segment12 = segment9.next;
            Intrinsics.checkNotNull(segment12);
            segment9 = segment12;
            buffer = buffer2;
            segment8 = segment10;
            z2 = z3;
            z = z4;
            segment = segment11;
        }
        return -1L;
    }

    public static final long commonIndexOf(Buffer commonIndexOf, ByteString bytes, long fromIndex) {
        Intrinsics.checkNotNullParameter(commonIndexOf, "$this$commonIndexOf");
        Intrinsics.checkNotNullParameter(bytes, "bytes");
        long j = fromIndex;
        if (!(bytes.size() > 0)) {
            throw new IllegalArgumentException("bytes is empty".toString());
        }
        if (!(j >= 0)) {
            throw new IllegalArgumentException(("fromIndex < 0: " + j).toString());
        }
        long j2 = j;
        boolean z = false;
        Segment segment = commonIndexOf.head;
        if (segment == null) {
            return -1L;
        }
        if (commonIndexOf.size() - j2 < j2) {
            long size = commonIndexOf.size();
            while (size > j2) {
                Segment segment2 = segment.prev;
                Intrinsics.checkNotNull(segment2);
                segment = segment2;
                size -= segment.limit - segment.pos;
            }
            Segment segment3 = segment;
            long j3 = size;
            if (segment3 == null) {
                return -1L;
            }
            long j4 = j3;
            byte[] internalArray$okio = bytes.internalArray$okio();
            byte b = internalArray$okio[0];
            int size2 = bytes.size();
            long size3 = (commonIndexOf.size() - size2) + 1;
            Segment segment4 = segment3;
            while (j4 < size3) {
                byte[] bArr = segment4.data;
                boolean z2 = z;
                Segment segment5 = segment;
                long j5 = j3;
                Segment segment6 = segment3;
                int min = (int) Math.min(segment4.limit, (segment4.pos + size3) - j4);
                for (int i = (int) ((segment4.pos + j) - j4); i < min; i++) {
                    if (bArr[i] == b && rangeEquals(segment4, i + 1, internalArray$okio, 1, size2)) {
                        return (i - segment4.pos) + j4;
                    }
                }
                j4 += segment4.limit - segment4.pos;
                j = j4;
                Segment segment7 = segment4.next;
                Intrinsics.checkNotNull(segment7);
                segment4 = segment7;
                segment3 = segment6;
                z = z2;
                segment = segment5;
                j3 = j5;
            }
            return -1L;
        }
        long j6 = 0;
        while (true) {
            long j7 = (segment.limit - segment.pos) + j6;
            if (j7 > j2) {
                break;
            }
            Segment segment8 = segment.next;
            Intrinsics.checkNotNull(segment8);
            segment = segment8;
            j6 = j7;
            j2 = j2;
        }
        Segment segment9 = segment;
        long j8 = j6;
        boolean z3 = false;
        if (segment9 == null) {
            return -1L;
        }
        Segment segment10 = segment9;
        long j9 = j8;
        byte[] internalArray$okio2 = bytes.internalArray$okio();
        byte b2 = internalArray$okio2[0];
        int size4 = bytes.size();
        long size5 = (commonIndexOf.size() - size4) + 1;
        while (j9 < size5) {
            byte[] bArr2 = segment10.data;
            boolean z4 = z3;
            long j10 = j2;
            long j11 = j8;
            int min2 = (int) Math.min(segment10.limit, (segment10.pos + size5) - j9);
            for (int i2 = (int) ((segment10.pos + j) - j9); i2 < min2; i2++) {
                if (bArr2[i2] == b2 && rangeEquals(segment10, i2 + 1, internalArray$okio2, 1, size4)) {
                    return (i2 - segment10.pos) + j9;
                }
            }
            j9 += segment10.limit - segment10.pos;
            j = j9;
            Segment segment11 = segment10.next;
            Intrinsics.checkNotNull(segment11);
            segment10 = segment11;
            z3 = z4;
            j2 = j10;
            j8 = j11;
        }
        return -1L;
    }

    public static final long commonIndexOfElement(Buffer commonIndexOfElement, ByteString targetBytes, long fromIndex) {
        ByteString targetBytes2 = targetBytes;
        boolean z = false;
        Intrinsics.checkNotNullParameter(commonIndexOfElement, "$this$commonIndexOfElement");
        Intrinsics.checkNotNullParameter(targetBytes2, "targetBytes");
        long j = fromIndex;
        if (!(j >= 0)) {
            throw new IllegalArgumentException(("fromIndex < 0: " + j).toString());
        }
        Buffer buffer = commonIndexOfElement;
        Segment segment = buffer.head;
        if (segment == null) {
            return -1L;
        }
        if (buffer.size() - j < j) {
            long size = buffer.size();
            while (size > j) {
                Segment segment2 = segment.prev;
                Intrinsics.checkNotNull(segment2);
                segment = segment2;
                size -= segment.limit - segment.pos;
            }
            Segment segment3 = segment;
            long j2 = size;
            if (segment3 == null) {
                return -1L;
            }
            long j3 = j2;
            if (targetBytes.size() == 2) {
                byte b = targetBytes2.getByte(0);
                byte b2 = targetBytes2.getByte(1);
                Segment segment4 = segment3;
                while (j3 < commonIndexOfElement.size()) {
                    boolean z2 = z;
                    byte[] bArr = segment4.data;
                    Buffer buffer2 = buffer;
                    int i = (int) ((segment4.pos + j) - j3);
                    int i2 = segment4.limit;
                    while (i < i2) {
                        int i3 = i2;
                        byte b3 = bArr[i];
                        if (b3 == b || b3 == b2) {
                            return (i - segment4.pos) + j3;
                        }
                        i++;
                        i2 = i3;
                    }
                    j3 += segment4.limit - segment4.pos;
                    j = j3;
                    Segment segment5 = segment4.next;
                    Intrinsics.checkNotNull(segment5);
                    segment4 = segment5;
                    z = z2;
                    buffer = buffer2;
                }
                return -1L;
            }
            byte[] internalArray$okio = targetBytes.internalArray$okio();
            Segment segment6 = segment3;
            while (j3 < commonIndexOfElement.size()) {
                byte[] bArr2 = segment6.data;
                int i4 = (int) ((segment6.pos + j) - j3);
                int i5 = segment6.limit;
                while (i4 < i5) {
                    byte b4 = bArr2[i4];
                    byte[] bArr3 = bArr2;
                    long j4 = j;
                    for (byte b5 : internalArray$okio) {
                        if (b4 == b5) {
                            return (i4 - segment6.pos) + j3;
                        }
                    }
                    i4++;
                    bArr2 = bArr3;
                    j = j4;
                }
                j3 += segment6.limit - segment6.pos;
                j = j3;
                Segment segment7 = segment6.next;
                Intrinsics.checkNotNull(segment7);
                segment6 = segment7;
            }
            return -1L;
        }
        long j5 = 0;
        while (true) {
            long j6 = (segment.limit - segment.pos) + j5;
            if (j6 > j) {
                break;
            }
            Segment segment8 = segment.next;
            Intrinsics.checkNotNull(segment8);
            segment = segment8;
            j5 = j6;
            targetBytes2 = targetBytes;
        }
        Segment segment9 = segment;
        long j7 = j5;
        if (segment9 == null) {
            return -1L;
        }
        Segment segment10 = segment9;
        long j8 = j7;
        if (targetBytes.size() == 2) {
            byte b6 = targetBytes2.getByte(0);
            byte b7 = targetBytes2.getByte(1);
            while (j8 < commonIndexOfElement.size()) {
                byte[] bArr4 = segment10.data;
                long j9 = j5;
                int i6 = (int) ((segment10.pos + j) - j8);
                int i7 = segment10.limit;
                while (i6 < i7) {
                    int i8 = i7;
                    byte b8 = bArr4[i6];
                    if (b8 == b6 || b8 == b7) {
                        return (i6 - segment10.pos) + j8;
                    }
                    i6++;
                    i7 = i8;
                }
                j8 += segment10.limit - segment10.pos;
                j = j8;
                Segment segment11 = segment10.next;
                Intrinsics.checkNotNull(segment11);
                segment10 = segment11;
                j5 = j9;
            }
            return -1L;
        }
        byte[] internalArray$okio2 = targetBytes.internalArray$okio();
        while (j8 < commonIndexOfElement.size()) {
            byte[] bArr5 = segment10.data;
            int i9 = (int) ((segment10.pos + j) - j8);
            int i10 = segment10.limit;
            while (i9 < i10) {
                byte b9 = bArr5[i9];
                byte[] bArr6 = bArr5;
                int length = internalArray$okio2.length;
                Segment segment12 = segment9;
                int i11 = 0;
                while (i11 < length) {
                    int i12 = length;
                    if (b9 == internalArray$okio2[i11]) {
                        return (i9 - segment10.pos) + j8;
                    }
                    i11++;
                    length = i12;
                }
                i9++;
                bArr5 = bArr6;
                segment9 = segment12;
            }
            byte[] bArr7 = internalArray$okio2;
            j8 += segment10.limit - segment10.pos;
            j = j8;
            Segment segment13 = segment10.next;
            Intrinsics.checkNotNull(segment13);
            segment10 = segment13;
            internalArray$okio2 = bArr7;
        }
        return -1L;
    }

    public static final boolean commonRangeEquals(Buffer commonRangeEquals, long offset, ByteString bytes, int bytesOffset, int byteCount) {
        Intrinsics.checkNotNullParameter(commonRangeEquals, "$this$commonRangeEquals");
        Intrinsics.checkNotNullParameter(bytes, "bytes");
        if (offset < 0 || bytesOffset < 0 || byteCount < 0 || commonRangeEquals.size() - offset < byteCount || bytes.size() - bytesOffset < byteCount) {
            return false;
        }
        for (int i = 0; i < byteCount; i++) {
            if (commonRangeEquals.getByte(i + offset) != bytes.getByte(bytesOffset + i)) {
                return false;
            }
        }
        return true;
    }

    public static final int commonRead(Buffer commonRead, byte[] sink) {
        Intrinsics.checkNotNullParameter(commonRead, "$this$commonRead");
        Intrinsics.checkNotNullParameter(sink, "sink");
        return commonRead.read(sink, 0, sink.length);
    }

    public static final int commonRead(Buffer commonRead, byte[] sink, int offset, int byteCount) {
        Intrinsics.checkNotNullParameter(commonRead, "$this$commonRead");
        Intrinsics.checkNotNullParameter(sink, "sink");
        Util.checkOffsetAndCount(sink.length, offset, byteCount);
        Segment segment = commonRead.head;
        if (segment == null) {
            return -1;
        }
        int min = Math.min(byteCount, segment.limit - segment.pos);
        ArraysKt.copyInto(segment.data, sink, offset, segment.pos, segment.pos + min);
        segment.pos += min;
        commonRead.setSize$okio(commonRead.size() - min);
        if (segment.pos == segment.limit) {
            commonRead.head = segment.pop();
            SegmentPool.recycle(segment);
        }
        return min;
    }

    public static final long commonRead(Buffer commonRead, Buffer sink, long byteCount) {
        Intrinsics.checkNotNullParameter(commonRead, "$this$commonRead");
        Intrinsics.checkNotNullParameter(sink, "sink");
        long j = byteCount;
        if (!(j >= 0)) {
            throw new IllegalArgumentException(("byteCount < 0: " + j).toString());
        }
        if (commonRead.size() == 0) {
            return -1L;
        }
        if (j > commonRead.size()) {
            j = commonRead.size();
        }
        sink.write(commonRead, j);
        return j;
    }

    public static final long commonReadAll(Buffer commonReadAll, Sink sink) {
        Intrinsics.checkNotNullParameter(commonReadAll, "$this$commonReadAll");
        Intrinsics.checkNotNullParameter(sink, "sink");
        long size = commonReadAll.size();
        if (size > 0) {
            sink.write(commonReadAll, size);
        }
        return size;
    }

    public static final byte commonReadByte(Buffer commonReadByte) {
        Intrinsics.checkNotNullParameter(commonReadByte, "$this$commonReadByte");
        if (commonReadByte.size() == 0) {
            throw new EOFException();
        }
        Segment segment = commonReadByte.head;
        Intrinsics.checkNotNull(segment);
        int i = segment.pos;
        int i2 = segment.limit;
        int i3 = i + 1;
        byte b = segment.data[i];
        commonReadByte.setSize$okio(commonReadByte.size() - 1);
        if (i3 == i2) {
            commonReadByte.head = segment.pop();
            SegmentPool.recycle(segment);
        } else {
            segment.pos = i3;
        }
        return b;
    }

    public static final byte[] commonReadByteArray(Buffer commonReadByteArray) {
        Intrinsics.checkNotNullParameter(commonReadByteArray, "$this$commonReadByteArray");
        return commonReadByteArray.readByteArray(commonReadByteArray.size());
    }

    public static final byte[] commonReadByteArray(Buffer commonReadByteArray, long byteCount) {
        Intrinsics.checkNotNullParameter(commonReadByteArray, "$this$commonReadByteArray");
        if (!(byteCount >= 0 && byteCount <= ((long) Integer.MAX_VALUE))) {
            throw new IllegalArgumentException(("byteCount: " + byteCount).toString());
        }
        if (commonReadByteArray.size() < byteCount) {
            throw new EOFException();
        }
        byte[] bArr = new byte[(int) byteCount];
        commonReadByteArray.readFully(bArr);
        return bArr;
    }

    public static final ByteString commonReadByteString(Buffer commonReadByteString) {
        Intrinsics.checkNotNullParameter(commonReadByteString, "$this$commonReadByteString");
        return commonReadByteString.readByteString(commonReadByteString.size());
    }

    public static final ByteString commonReadByteString(Buffer commonReadByteString, long byteCount) {
        Intrinsics.checkNotNullParameter(commonReadByteString, "$this$commonReadByteString");
        if (!(byteCount >= 0 && byteCount <= ((long) Integer.MAX_VALUE))) {
            throw new IllegalArgumentException(("byteCount: " + byteCount).toString());
        }
        if (commonReadByteString.size() < byteCount) {
            throw new EOFException();
        }
        if (byteCount < 4096) {
            return new ByteString(commonReadByteString.readByteArray(byteCount));
        }
        ByteString snapshot = commonReadByteString.snapshot((int) byteCount);
        commonReadByteString.skip(byteCount);
        return snapshot;
    }

    /* JADX WARN: Code restructure failed: missing block: B:44:0x00f8, code lost:
    
        r1.setSize$okio(r18.size() - r4);
     */
    /* JADX WARN: Code restructure failed: missing block: B:45:0x0101, code lost:
    
        if (r5 == false) goto L111;
     */
    /* JADX WARN: Code restructure failed: missing block: B:47:?, code lost:
    
        return r2;
     */
    /* JADX WARN: Code restructure failed: missing block: B:49:0x0106, code lost:
    
        return -r2;
     */
    /* JADX WARN: Failed to parse debug info
    jadx.core.utils.exceptions.JadxRuntimeException: Failed to parse type string: null
    	at jadx.core.dex.instructions.args.ArgType.parse(ArgType.java:736)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.getVarType(DebugInfoAttachVisitor.java:139)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.attachDebugInfo(DebugInfoAttachVisitor.java:94)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.processDebugInfo(DebugInfoAttachVisitor.java:51)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.visit(DebugInfoAttachVisitor.java:41)
     */
    /* JADX WARN: Removed duplicated region for block: B:38:0x00dc  */
    /* JADX WARN: Removed duplicated region for block: B:51:0x00e8  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static final long commonReadDecimalLong(okio.Buffer r18) {
        /*
            Method dump skipped, instructions count: 273
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: okio.internal.BufferKt.commonReadDecimalLong(okio.Buffer):long");
    }

    public static final void commonReadFully(Buffer commonReadFully, Buffer sink, long byteCount) {
        Intrinsics.checkNotNullParameter(commonReadFully, "$this$commonReadFully");
        Intrinsics.checkNotNullParameter(sink, "sink");
        if (commonReadFully.size() >= byteCount) {
            sink.write(commonReadFully, byteCount);
        } else {
            sink.write(commonReadFully, commonReadFully.size());
            throw new EOFException();
        }
    }

    public static final void commonReadFully(Buffer commonReadFully, byte[] sink) {
        Intrinsics.checkNotNullParameter(commonReadFully, "$this$commonReadFully");
        Intrinsics.checkNotNullParameter(sink, "sink");
        int i = 0;
        while (i < sink.length) {
            int read = commonReadFully.read(sink, i, sink.length - i);
            if (read == -1) {
                throw new EOFException();
            }
            i += read;
        }
    }

    /* JADX WARN: Failed to parse debug info
    jadx.core.utils.exceptions.JadxRuntimeException: Failed to parse type string: null
    	at jadx.core.dex.instructions.args.ArgType.parse(ArgType.java:736)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.getVarType(DebugInfoAttachVisitor.java:139)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.attachDebugInfo(DebugInfoAttachVisitor.java:94)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.processDebugInfo(DebugInfoAttachVisitor.java:51)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.visit(DebugInfoAttachVisitor.java:41)
     */
    /* JADX WARN: Removed duplicated region for block: B:32:0x00bb  */
    /* JADX WARN: Removed duplicated region for block: B:34:0x00ca  */
    /* JADX WARN: Removed duplicated region for block: B:40:0x00ce A[EDGE_INSN: B:40:0x00ce->B:37:0x00ce BREAK  A[LOOP:0: B:4:0x0014->B:39:?], SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:41:0x00c5  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static final long commonReadHexadecimalUnsignedLong(okio.Buffer r15) {
        /*
            Method dump skipped, instructions count: 224
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: okio.internal.BufferKt.commonReadHexadecimalUnsignedLong(okio.Buffer):long");
    }

    public static final int commonReadInt(Buffer commonReadInt) {
        Intrinsics.checkNotNullParameter(commonReadInt, "$this$commonReadInt");
        if (commonReadInt.size() < 4) {
            throw new EOFException();
        }
        Segment segment = commonReadInt.head;
        Intrinsics.checkNotNull(segment);
        int i = segment.pos;
        int i2 = segment.limit;
        if (i2 - i < 4) {
            return ((commonReadInt.readByte() & UByte.MAX_VALUE) << 24) | ((commonReadInt.readByte() & UByte.MAX_VALUE) << 16) | ((commonReadInt.readByte() & UByte.MAX_VALUE) << 8) | (commonReadInt.readByte() & UByte.MAX_VALUE);
        }
        byte[] bArr = segment.data;
        int i3 = i + 1;
        int i4 = i3 + 1;
        int i5 = ((bArr[i] & UByte.MAX_VALUE) << 24) | ((bArr[i3] & UByte.MAX_VALUE) << 16);
        int i6 = i4 + 1;
        int i7 = i5 | ((bArr[i4] & UByte.MAX_VALUE) << 8);
        int i8 = i6 + 1;
        int i9 = i7 | (bArr[i6] & UByte.MAX_VALUE);
        commonReadInt.setSize$okio(commonReadInt.size() - 4);
        if (i8 == i2) {
            commonReadInt.head = segment.pop();
            SegmentPool.recycle(segment);
        } else {
            segment.pos = i8;
        }
        return i9;
    }

    public static final long commonReadLong(Buffer commonReadLong) {
        Intrinsics.checkNotNullParameter(commonReadLong, "$this$commonReadLong");
        if (commonReadLong.size() < 8) {
            throw new EOFException();
        }
        Segment segment = commonReadLong.head;
        Intrinsics.checkNotNull(segment);
        int i = segment.pos;
        int i2 = segment.limit;
        if (i2 - i < 8) {
            return ((commonReadLong.readInt() & 4294967295L) << 32) | (commonReadLong.readInt() & 4294967295L);
        }
        byte[] bArr = segment.data;
        long j = (255 & bArr[i]) << 56;
        long j2 = ((bArr[r9] & 255) << 48) | j;
        int i3 = i + 1 + 1 + 1 + 1 + 1;
        long j3 = j2 | ((255 & bArr[r3]) << 40) | ((bArr[r9] & 255) << 32) | ((255 & bArr[r5]) << 24);
        long j4 = j3 | ((bArr[i3] & 255) << 16);
        long j5 = j4 | ((255 & bArr[r5]) << 8);
        int i4 = i3 + 1 + 1 + 1;
        long j6 = j5 | (bArr[r8] & 255);
        commonReadLong.setSize$okio(commonReadLong.size() - 8);
        if (i4 == i2) {
            commonReadLong.head = segment.pop();
            SegmentPool.recycle(segment);
        } else {
            segment.pos = i4;
        }
        return j6;
    }

    public static final short commonReadShort(Buffer commonReadShort) {
        Intrinsics.checkNotNullParameter(commonReadShort, "$this$commonReadShort");
        if (commonReadShort.size() < 2) {
            throw new EOFException();
        }
        Segment segment = commonReadShort.head;
        Intrinsics.checkNotNull(segment);
        int i = segment.pos;
        int i2 = segment.limit;
        if (i2 - i < 2) {
            return (short) (((commonReadShort.readByte() & UByte.MAX_VALUE) << 8) | (commonReadShort.readByte() & UByte.MAX_VALUE));
        }
        byte[] bArr = segment.data;
        int i3 = i + 1;
        int i4 = i3 + 1;
        int i5 = ((bArr[i] & UByte.MAX_VALUE) << 8) | (bArr[i3] & UByte.MAX_VALUE);
        commonReadShort.setSize$okio(commonReadShort.size() - 2);
        if (i4 == i2) {
            commonReadShort.head = segment.pop();
            SegmentPool.recycle(segment);
        } else {
            segment.pos = i4;
        }
        return (short) i5;
    }

    /* JADX WARN: Failed to parse debug info
    jadx.core.utils.exceptions.JadxRuntimeException: Failed to parse type string: null
    	at jadx.core.dex.instructions.args.ArgType.parse(ArgType.java:736)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.getVarType(DebugInfoAttachVisitor.java:139)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.attachDebugInfo(DebugInfoAttachVisitor.java:94)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.processDebugInfo(DebugInfoAttachVisitor.java:51)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.visit(DebugInfoAttachVisitor.java:41)
     */
    public static final String commonReadUtf8(Buffer commonReadUtf8, long j) {
        Intrinsics.checkNotNullParameter(commonReadUtf8, "$this$commonReadUtf8");
        if (!(j >= 0 && j <= ((long) Integer.MAX_VALUE))) {
            throw new IllegalArgumentException(("byteCount: " + j).toString());
        }
        if (commonReadUtf8.size() < j) {
            throw new EOFException();
        }
        if (j == 0) {
            return HttpUrl.FRAGMENT_ENCODE_SET;
        }
        Segment segment = commonReadUtf8.head;
        Intrinsics.checkNotNull(segment);
        if (segment.pos + j > segment.limit) {
            String commonToUtf8String$default = _Utf8Kt.commonToUtf8String$default(commonReadUtf8.readByteArray(j), 0, 0, 3, null);
            Log5ECF72.a(commonToUtf8String$default);
            LogE84000.a(commonToUtf8String$default);
            Log229316.a(commonToUtf8String$default);
            return commonToUtf8String$default;
        }
        String commonToUtf8String = _Utf8Kt.commonToUtf8String(segment.data, segment.pos, segment.pos + ((int) j));
        Log5ECF72.a(commonToUtf8String);
        LogE84000.a(commonToUtf8String);
        Log229316.a(commonToUtf8String);
        segment.pos += (int) j;
        commonReadUtf8.setSize$okio(commonReadUtf8.size() - j);
        if (segment.pos == segment.limit) {
            commonReadUtf8.head = segment.pop();
            SegmentPool.recycle(segment);
        }
        return commonToUtf8String;
    }

    /* JADX WARN: Failed to parse debug info
    jadx.core.utils.exceptions.JadxRuntimeException: Failed to parse type string: null
    	at jadx.core.dex.instructions.args.ArgType.parse(ArgType.java:736)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.getVarType(DebugInfoAttachVisitor.java:139)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.attachDebugInfo(DebugInfoAttachVisitor.java:94)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.processDebugInfo(DebugInfoAttachVisitor.java:51)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.visit(DebugInfoAttachVisitor.java:41)
     */
    public static final int commonReadUtf8CodePoint(Buffer commonReadUtf8CodePoint) {
        int i;
        int i2;
        int i3;
        Intrinsics.checkNotNullParameter(commonReadUtf8CodePoint, "$this$commonReadUtf8CodePoint");
        if (commonReadUtf8CodePoint.size() == 0) {
            throw new EOFException();
        }
        byte b = commonReadUtf8CodePoint.getByte(0L);
        if ((128 & b) == 0) {
            i = b & ByteCompanionObject.MAX_VALUE;
            i2 = 1;
            i3 = 0;
        } else if ((224 & b) == 192) {
            i = b & 31;
            i2 = 2;
            i3 = 128;
        } else if ((240 & b) == 224) {
            i = b & 15;
            i2 = 3;
            i3 = 2048;
        } else {
            if ((248 & b) != 240) {
                commonReadUtf8CodePoint.skip(1L);
                return Utf8.REPLACEMENT_CODE_POINT;
            }
            i = b & 7;
            i2 = 4;
            i3 = 65536;
        }
        if (commonReadUtf8CodePoint.size() < i2) {
            StringBuilder append = new StringBuilder().append("size < ").append(i2).append(": ").append(commonReadUtf8CodePoint.size()).append(" (to read code point prefixed 0x");
            String hexString = Util.toHexString(b);
            Log5ECF72.a(hexString);
            LogE84000.a(hexString);
            Log229316.a(hexString);
            throw new EOFException(append.append(hexString).append(')').toString());
        }
        for (int i4 = 1; i4 < i2; i4++) {
            byte b2 = commonReadUtf8CodePoint.getByte(i4);
            if ((192 & b2) != 128) {
                commonReadUtf8CodePoint.skip(i4);
                return Utf8.REPLACEMENT_CODE_POINT;
            }
            i = (i << 6) | (63 & b2);
        }
        commonReadUtf8CodePoint.skip(i2);
        return i > 1114111 ? Utf8.REPLACEMENT_CODE_POINT : ((55296 <= i && 57343 >= i) || i < i3) ? Utf8.REPLACEMENT_CODE_POINT : i;
    }

    /* JADX WARN: Failed to parse debug info
    jadx.core.utils.exceptions.JadxRuntimeException: Failed to parse type string: null
    	at jadx.core.dex.instructions.args.ArgType.parse(ArgType.java:736)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.getVarType(DebugInfoAttachVisitor.java:139)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.attachDebugInfo(DebugInfoAttachVisitor.java:94)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.processDebugInfo(DebugInfoAttachVisitor.java:51)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.visit(DebugInfoAttachVisitor.java:41)
     */
    public static final String commonReadUtf8Line(Buffer commonReadUtf8Line) {
        Intrinsics.checkNotNullParameter(commonReadUtf8Line, "$this$commonReadUtf8Line");
        long indexOf = commonReadUtf8Line.indexOf((byte) 10);
        if (indexOf == -1) {
            if (commonReadUtf8Line.size() != 0) {
                return commonReadUtf8Line.readUtf8(commonReadUtf8Line.size());
            }
            return null;
        }
        String readUtf8Line = readUtf8Line(commonReadUtf8Line, indexOf);
        Log5ECF72.a(readUtf8Line);
        LogE84000.a(readUtf8Line);
        Log229316.a(readUtf8Line);
        return readUtf8Line;
    }

    /* JADX WARN: Failed to parse debug info
    jadx.core.utils.exceptions.JadxRuntimeException: Failed to parse type string: null
    	at jadx.core.dex.instructions.args.ArgType.parse(ArgType.java:736)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.getVarType(DebugInfoAttachVisitor.java:139)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.attachDebugInfo(DebugInfoAttachVisitor.java:94)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.processDebugInfo(DebugInfoAttachVisitor.java:51)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.visit(DebugInfoAttachVisitor.java:41)
     */
    public static final String commonReadUtf8LineStrict(Buffer commonReadUtf8LineStrict, long j) {
        Intrinsics.checkNotNullParameter(commonReadUtf8LineStrict, "$this$commonReadUtf8LineStrict");
        if (!(j >= 0)) {
            throw new IllegalArgumentException(("limit < 0: " + j).toString());
        }
        long j2 = j != Long.MAX_VALUE ? j + 1 : Long.MAX_VALUE;
        byte b = (byte) 10;
        long indexOf = commonReadUtf8LineStrict.indexOf(b, 0L, j2);
        if (indexOf != -1) {
            String readUtf8Line = readUtf8Line(commonReadUtf8LineStrict, indexOf);
            Log5ECF72.a(readUtf8Line);
            LogE84000.a(readUtf8Line);
            Log229316.a(readUtf8Line);
            return readUtf8Line;
        }
        if (j2 >= commonReadUtf8LineStrict.size() || commonReadUtf8LineStrict.getByte(j2 - 1) != ((byte) 13) || commonReadUtf8LineStrict.getByte(j2) != b) {
            Buffer buffer = new Buffer();
            commonReadUtf8LineStrict.copyTo(buffer, 0L, Math.min(32, commonReadUtf8LineStrict.size()));
            throw new EOFException("\\n not found: limit=" + Math.min(commonReadUtf8LineStrict.size(), j) + " content=" + buffer.readByteString().hex() + Typography.ellipsis);
        }
        String readUtf8Line2 = readUtf8Line(commonReadUtf8LineStrict, j2);
        Log5ECF72.a(readUtf8Line2);
        LogE84000.a(readUtf8Line2);
        Log229316.a(readUtf8Line2);
        return readUtf8Line2;
    }

    public static final int commonSelect(Buffer commonSelect, Options options) {
        Intrinsics.checkNotNullParameter(commonSelect, "$this$commonSelect");
        Intrinsics.checkNotNullParameter(options, "options");
        int selectPrefix$default = selectPrefix$default(commonSelect, options, false, 2, null);
        if (selectPrefix$default == -1) {
            return -1;
        }
        commonSelect.skip(options.getByteStrings()[selectPrefix$default].size());
        return selectPrefix$default;
    }

    public static final void commonSkip(Buffer commonSkip, long byteCount) {
        Intrinsics.checkNotNullParameter(commonSkip, "$this$commonSkip");
        long j = byteCount;
        while (j > 0) {
            Segment segment = commonSkip.head;
            if (segment == null) {
                throw new EOFException();
            }
            int min = (int) Math.min(j, segment.limit - segment.pos);
            commonSkip.setSize$okio(commonSkip.size() - min);
            j -= min;
            segment.pos += min;
            if (segment.pos == segment.limit) {
                commonSkip.head = segment.pop();
                SegmentPool.recycle(segment);
            }
        }
    }

    public static final ByteString commonSnapshot(Buffer commonSnapshot) {
        Intrinsics.checkNotNullParameter(commonSnapshot, "$this$commonSnapshot");
        if (commonSnapshot.size() <= ((long) Integer.MAX_VALUE)) {
            return commonSnapshot.snapshot((int) commonSnapshot.size());
        }
        throw new IllegalStateException(("size > Int.MAX_VALUE: " + commonSnapshot.size()).toString());
    }

    public static final ByteString commonSnapshot(Buffer commonSnapshot, int byteCount) {
        Intrinsics.checkNotNullParameter(commonSnapshot, "$this$commonSnapshot");
        if (byteCount == 0) {
            return ByteString.EMPTY;
        }
        Util.checkOffsetAndCount(commonSnapshot.size(), 0L, byteCount);
        int i = 0;
        int i2 = 0;
        Segment segment = commonSnapshot.head;
        while (i < byteCount) {
            Intrinsics.checkNotNull(segment);
            if (segment.limit == segment.pos) {
                throw new AssertionError("s.limit == s.pos");
            }
            i += segment.limit - segment.pos;
            i2++;
            segment = segment.next;
        }
        byte[][] bArr = new byte[i2];
        int[] iArr = new int[i2 * 2];
        int i3 = 0;
        int i4 = 0;
        Segment segment2 = commonSnapshot.head;
        while (i3 < byteCount) {
            Intrinsics.checkNotNull(segment2);
            bArr[i4] = segment2.data;
            i3 += segment2.limit - segment2.pos;
            iArr[i4] = Math.min(i3, byteCount);
            iArr[bArr.length + i4] = segment2.pos;
            segment2.shared = true;
            i4++;
            segment2 = segment2.next;
        }
        return new SegmentedByteString(bArr, iArr);
    }

    public static final Segment commonWritableSegment(Buffer commonWritableSegment, int minimumCapacity) {
        Intrinsics.checkNotNullParameter(commonWritableSegment, "$this$commonWritableSegment");
        if (!(minimumCapacity >= 1 && minimumCapacity <= 8192)) {
            throw new IllegalArgumentException("unexpected capacity".toString());
        }
        if (commonWritableSegment.head == null) {
            Segment take = SegmentPool.take();
            commonWritableSegment.head = take;
            take.prev = take;
            take.next = take;
            return take;
        }
        Segment segment = commonWritableSegment.head;
        Intrinsics.checkNotNull(segment);
        Segment segment2 = segment.prev;
        Intrinsics.checkNotNull(segment2);
        return (segment2.limit + minimumCapacity > 8192 || !segment2.owner) ? segment2.push(SegmentPool.take()) : segment2;
    }

    public static final Buffer commonWrite(Buffer commonWrite, ByteString byteString, int offset, int byteCount) {
        Intrinsics.checkNotNullParameter(commonWrite, "$this$commonWrite");
        Intrinsics.checkNotNullParameter(byteString, "byteString");
        byteString.write$okio(commonWrite, offset, byteCount);
        return commonWrite;
    }

    public static final Buffer commonWrite(Buffer commonWrite, Source source, long byteCount) {
        Intrinsics.checkNotNullParameter(commonWrite, "$this$commonWrite");
        Intrinsics.checkNotNullParameter(source, "source");
        long j = byteCount;
        while (j > 0) {
            long read = source.read(commonWrite, j);
            if (read == -1) {
                throw new EOFException();
            }
            j -= read;
        }
        return commonWrite;
    }

    public static final Buffer commonWrite(Buffer commonWrite, byte[] source) {
        Intrinsics.checkNotNullParameter(commonWrite, "$this$commonWrite");
        Intrinsics.checkNotNullParameter(source, "source");
        return commonWrite.write(source, 0, source.length);
    }

    public static final Buffer commonWrite(Buffer commonWrite, byte[] source, int offset, int byteCount) {
        Intrinsics.checkNotNullParameter(commonWrite, "$this$commonWrite");
        Intrinsics.checkNotNullParameter(source, "source");
        int i = offset;
        Util.checkOffsetAndCount(source.length, i, byteCount);
        int i2 = i + byteCount;
        while (i < i2) {
            Segment writableSegment$okio = commonWrite.writableSegment$okio(1);
            int min = Math.min(i2 - i, 8192 - writableSegment$okio.limit);
            ArraysKt.copyInto(source, writableSegment$okio.data, writableSegment$okio.limit, i, i + min);
            i += min;
            writableSegment$okio.limit += min;
        }
        commonWrite.setSize$okio(commonWrite.size() + byteCount);
        return commonWrite;
    }

    public static final void commonWrite(Buffer commonWrite, Buffer source, long byteCount) {
        Segment segment;
        Intrinsics.checkNotNullParameter(commonWrite, "$this$commonWrite");
        Intrinsics.checkNotNullParameter(source, "source");
        long j = byteCount;
        if (!(source != commonWrite)) {
            throw new IllegalArgumentException("source == this".toString());
        }
        Util.checkOffsetAndCount(source.size(), 0L, j);
        while (j > 0) {
            Segment segment2 = source.head;
            Intrinsics.checkNotNull(segment2);
            int i = segment2.limit;
            Intrinsics.checkNotNull(source.head);
            if (j < i - r2.pos) {
                if (commonWrite.head != null) {
                    Segment segment3 = commonWrite.head;
                    Intrinsics.checkNotNull(segment3);
                    segment = segment3.prev;
                } else {
                    segment = null;
                }
                if (segment != null && segment.owner) {
                    if ((segment.limit + j) - (segment.shared ? 0 : segment.pos) <= 8192) {
                        Segment segment4 = source.head;
                        Intrinsics.checkNotNull(segment4);
                        segment4.writeTo(segment, (int) j);
                        source.setSize$okio(source.size() - j);
                        commonWrite.setSize$okio(commonWrite.size() + j);
                        return;
                    }
                }
                Segment segment5 = source.head;
                Intrinsics.checkNotNull(segment5);
                source.head = segment5.split((int) j);
            }
            Segment segment6 = source.head;
            Intrinsics.checkNotNull(segment6);
            long j2 = segment6.limit - segment6.pos;
            source.head = segment6.pop();
            if (commonWrite.head == null) {
                commonWrite.head = segment6;
                segment6.prev = segment6;
                segment6.next = segment6.prev;
            } else {
                Segment segment7 = commonWrite.head;
                Intrinsics.checkNotNull(segment7);
                Segment segment8 = segment7.prev;
                Intrinsics.checkNotNull(segment8);
                segment8.push(segment6).compact();
            }
            source.setSize$okio(source.size() - j2);
            commonWrite.setSize$okio(commonWrite.size() + j2);
            j -= j2;
        }
    }

    public static /* synthetic */ Buffer commonWrite$default(Buffer commonWrite, ByteString byteString, int offset, int byteCount, int i, Object obj) {
        if ((i & 2) != 0) {
            offset = 0;
        }
        if ((i & 4) != 0) {
            byteCount = byteString.size();
        }
        Intrinsics.checkNotNullParameter(commonWrite, "$this$commonWrite");
        Intrinsics.checkNotNullParameter(byteString, "byteString");
        byteString.write$okio(commonWrite, offset, byteCount);
        return commonWrite;
    }

    public static final long commonWriteAll(Buffer commonWriteAll, Source source) {
        Intrinsics.checkNotNullParameter(commonWriteAll, "$this$commonWriteAll");
        Intrinsics.checkNotNullParameter(source, "source");
        long j = 0;
        while (true) {
            long read = source.read(commonWriteAll, 8192);
            if (read == -1) {
                return j;
            }
            j += read;
        }
    }

    public static final Buffer commonWriteByte(Buffer commonWriteByte, int b) {
        Intrinsics.checkNotNullParameter(commonWriteByte, "$this$commonWriteByte");
        Segment writableSegment$okio = commonWriteByte.writableSegment$okio(1);
        byte[] bArr = writableSegment$okio.data;
        int i = writableSegment$okio.limit;
        writableSegment$okio.limit = i + 1;
        bArr[i] = (byte) b;
        commonWriteByte.setSize$okio(commonWriteByte.size() + 1);
        return commonWriteByte;
    }

    public static final Buffer commonWriteDecimalLong(Buffer commonWriteDecimalLong, long v) {
        Intrinsics.checkNotNullParameter(commonWriteDecimalLong, "$this$commonWriteDecimalLong");
        long j = v;
        if (j == 0) {
            return commonWriteDecimalLong.writeByte(48);
        }
        boolean z = false;
        if (j < 0) {
            j = -j;
            if (j < 0) {
                return commonWriteDecimalLong.writeUtf8("-9223372036854775808");
            }
            z = true;
        }
        int i = j < 100000000 ? j < WorkRequest.MIN_BACKOFF_MILLIS ? j < 100 ? j < 10 ? 1 : 2 : j < 1000 ? 3 : 4 : j < 1000000 ? j < 100000 ? 5 : 6 : j < 10000000 ? 7 : 8 : j < 1000000000000L ? j < RealConnection.IDLE_CONNECTION_HEALTHY_NS ? j < 1000000000 ? 9 : 10 : j < 100000000000L ? 11 : 12 : j < 1000000000000000L ? j < 10000000000000L ? 13 : j < 100000000000000L ? 14 : 15 : j < 100000000000000000L ? j < 10000000000000000L ? 16 : 17 : j < 1000000000000000000L ? 18 : 19;
        if (z) {
            i++;
        }
        Segment writableSegment$okio = commonWriteDecimalLong.writableSegment$okio(i);
        byte[] bArr = writableSegment$okio.data;
        int i2 = writableSegment$okio.limit + i;
        while (j != 0) {
            long j2 = 10;
            i2--;
            bArr[i2] = getHEX_DIGIT_BYTES()[(int) (j % j2)];
            j /= j2;
        }
        if (z) {
            bArr[i2 - 1] = (byte) 45;
        }
        writableSegment$okio.limit += i;
        commonWriteDecimalLong.setSize$okio(commonWriteDecimalLong.size() + i);
        return commonWriteDecimalLong;
    }

    public static final Buffer commonWriteHexadecimalUnsignedLong(Buffer commonWriteHexadecimalUnsignedLong, long v) {
        Intrinsics.checkNotNullParameter(commonWriteHexadecimalUnsignedLong, "$this$commonWriteHexadecimalUnsignedLong");
        long j = v;
        if (j == 0) {
            return commonWriteHexadecimalUnsignedLong.writeByte(48);
        }
        long j2 = j | (j >>> 1);
        long j3 = j2 | (j2 >>> 2);
        long j4 = j3 | (j3 >>> 4);
        long j5 = j4 | (j4 >>> 8);
        long j6 = j5 | (j5 >>> 16);
        long j7 = j6 | (j6 >>> 32);
        long j8 = j7 - ((j7 >>> 1) & 6148914691236517205L);
        long j9 = ((j8 >>> 2) & 3689348814741910323L) + (3689348814741910323L & j8);
        long j10 = ((j9 >>> 4) + j9) & 1085102592571150095L;
        long j11 = j10 + (j10 >>> 8);
        long j12 = j11 + (j11 >>> 16);
        int i = (int) ((3 + ((j12 & 63) + (63 & (j12 >>> 32)))) / 4);
        Segment writableSegment$okio = commonWriteHexadecimalUnsignedLong.writableSegment$okio(i);
        byte[] bArr = writableSegment$okio.data;
        int i2 = writableSegment$okio.limit;
        for (int i3 = (writableSegment$okio.limit + i) - 1; i3 >= i2; i3--) {
            bArr[i3] = getHEX_DIGIT_BYTES()[(int) (15 & j)];
            j >>>= 4;
        }
        writableSegment$okio.limit += i;
        commonWriteHexadecimalUnsignedLong.setSize$okio(commonWriteHexadecimalUnsignedLong.size() + i);
        return commonWriteHexadecimalUnsignedLong;
    }

    public static final Buffer commonWriteInt(Buffer commonWriteInt, int i) {
        Intrinsics.checkNotNullParameter(commonWriteInt, "$this$commonWriteInt");
        Segment writableSegment$okio = commonWriteInt.writableSegment$okio(4);
        byte[] bArr = writableSegment$okio.data;
        int i2 = writableSegment$okio.limit;
        int i3 = i2 + 1;
        bArr[i2] = (byte) ((i >>> 24) & 255);
        int i4 = i3 + 1;
        bArr[i3] = (byte) ((i >>> 16) & 255);
        int i5 = i4 + 1;
        bArr[i4] = (byte) ((i >>> 8) & 255);
        bArr[i5] = (byte) (i & 255);
        writableSegment$okio.limit = i5 + 1;
        commonWriteInt.setSize$okio(commonWriteInt.size() + 4);
        return commonWriteInt;
    }

    public static final Buffer commonWriteLong(Buffer commonWriteLong, long v) {
        Intrinsics.checkNotNullParameter(commonWriteLong, "$this$commonWriteLong");
        Segment writableSegment$okio = commonWriteLong.writableSegment$okio(8);
        byte[] bArr = writableSegment$okio.data;
        int i = writableSegment$okio.limit;
        int i2 = i + 1;
        bArr[i] = (byte) ((v >>> 56) & 255);
        int i3 = i2 + 1;
        bArr[i2] = (byte) ((v >>> 48) & 255);
        int i4 = i3 + 1;
        bArr[i3] = (byte) ((v >>> 40) & 255);
        int i5 = i4 + 1;
        bArr[i4] = (byte) ((v >>> 32) & 255);
        int i6 = i5 + 1;
        bArr[i5] = (byte) ((v >>> 24) & 255);
        int i7 = i6 + 1;
        bArr[i6] = (byte) ((v >>> 16) & 255);
        int i8 = i7 + 1;
        bArr[i7] = (byte) ((v >>> 8) & 255);
        bArr[i8] = (byte) (v & 255);
        writableSegment$okio.limit = i8 + 1;
        commonWriteLong.setSize$okio(commonWriteLong.size() + 8);
        return commonWriteLong;
    }

    public static final Buffer commonWriteShort(Buffer commonWriteShort, int s) {
        Intrinsics.checkNotNullParameter(commonWriteShort, "$this$commonWriteShort");
        Segment writableSegment$okio = commonWriteShort.writableSegment$okio(2);
        byte[] bArr = writableSegment$okio.data;
        int i = writableSegment$okio.limit;
        int i2 = i + 1;
        bArr[i] = (byte) ((s >>> 8) & 255);
        bArr[i2] = (byte) (s & 255);
        writableSegment$okio.limit = i2 + 1;
        commonWriteShort.setSize$okio(commonWriteShort.size() + 2);
        return commonWriteShort;
    }

    public static final Buffer commonWriteUtf8(Buffer commonWriteUtf8, String string, int beginIndex, int endIndex) {
        int i;
        Intrinsics.checkNotNullParameter(commonWriteUtf8, "$this$commonWriteUtf8");
        Intrinsics.checkNotNullParameter(string, "string");
        int i2 = 1;
        if (!(beginIndex >= 0)) {
            throw new IllegalArgumentException(("beginIndex < 0: " + beginIndex).toString());
        }
        if (!(endIndex >= beginIndex)) {
            throw new IllegalArgumentException(("endIndex < beginIndex: " + endIndex + " < " + beginIndex).toString());
        }
        if (!(endIndex <= string.length())) {
            throw new IllegalArgumentException(("endIndex > string.length: " + endIndex + " > " + string.length()).toString());
        }
        int i3 = beginIndex;
        while (i3 < endIndex) {
            char charAt = string.charAt(i3);
            if (charAt < 128) {
                Segment writableSegment$okio = commonWriteUtf8.writableSegment$okio(i2);
                byte[] bArr = writableSegment$okio.data;
                int i4 = writableSegment$okio.limit - i3;
                int min = Math.min(endIndex, 8192 - i4);
                int i5 = i3 + 1;
                bArr[i3 + i4] = (byte) charAt;
                while (i5 < min) {
                    char charAt2 = string.charAt(i5);
                    if (charAt2 >= 128) {
                        break;
                    }
                    bArr[i5 + i4] = (byte) charAt2;
                    i5++;
                }
                int i6 = (i5 + i4) - writableSegment$okio.limit;
                writableSegment$okio.limit += i6;
                commonWriteUtf8.setSize$okio(i6 + commonWriteUtf8.size());
                i3 = i5;
                i = 1;
            } else if (charAt < 2048) {
                Segment writableSegment$okio2 = commonWriteUtf8.writableSegment$okio(2);
                writableSegment$okio2.data[writableSegment$okio2.limit] = (byte) ((charAt >> 6) | 192);
                writableSegment$okio2.data[writableSegment$okio2.limit + 1] = (byte) (128 | (charAt & '?'));
                writableSegment$okio2.limit += 2;
                commonWriteUtf8.setSize$okio(commonWriteUtf8.size() + 2);
                i3++;
                i = 1;
            } else if (charAt < 55296 || charAt > 57343) {
                Segment writableSegment$okio3 = commonWriteUtf8.writableSegment$okio(3);
                writableSegment$okio3.data[writableSegment$okio3.limit] = (byte) ((charAt >> '\f') | 224);
                i = 1;
                writableSegment$okio3.data[writableSegment$okio3.limit + 1] = (byte) ((63 & (charAt >> 6)) | 128);
                writableSegment$okio3.data[writableSegment$okio3.limit + 2] = (byte) ((charAt & '?') | 128);
                writableSegment$okio3.limit += 3;
                commonWriteUtf8.setSize$okio(commonWriteUtf8.size() + 3);
                i3++;
            } else {
                char charAt3 = i3 + 1 < endIndex ? string.charAt(i3 + 1) : (char) 0;
                if (charAt > 56319 || 56320 > charAt3 || 57343 < charAt3) {
                    commonWriteUtf8.writeByte(63);
                    i3++;
                    i = 1;
                } else {
                    int i7 = (((charAt & 1023) << 10) | (charAt3 & 1023)) + 65536;
                    Segment writableSegment$okio4 = commonWriteUtf8.writableSegment$okio(4);
                    writableSegment$okio4.data[writableSegment$okio4.limit] = (byte) ((i7 >> 18) | 240);
                    writableSegment$okio4.data[writableSegment$okio4.limit + 1] = (byte) (((i7 >> 12) & 63) | 128);
                    writableSegment$okio4.data[writableSegment$okio4.limit + 2] = (byte) (((i7 >> 6) & 63) | 128);
                    writableSegment$okio4.data[writableSegment$okio4.limit + 3] = (byte) (128 | (i7 & 63));
                    writableSegment$okio4.limit += 4;
                    commonWriteUtf8.setSize$okio(commonWriteUtf8.size() + 4);
                    i3 += 2;
                    i = 1;
                }
            }
            i2 = i;
        }
        return commonWriteUtf8;
    }

    /* JADX WARN: Failed to parse debug info
    jadx.core.utils.exceptions.JadxRuntimeException: Failed to parse type string: null
    	at jadx.core.dex.instructions.args.ArgType.parse(ArgType.java:736)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.getVarType(DebugInfoAttachVisitor.java:139)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.attachDebugInfo(DebugInfoAttachVisitor.java:94)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.processDebugInfo(DebugInfoAttachVisitor.java:51)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.visit(DebugInfoAttachVisitor.java:41)
     */
    public static final Buffer commonWriteUtf8CodePoint(Buffer commonWriteUtf8CodePoint, int i) {
        Intrinsics.checkNotNullParameter(commonWriteUtf8CodePoint, "$this$commonWriteUtf8CodePoint");
        if (i < 128) {
            commonWriteUtf8CodePoint.writeByte(i);
        } else if (i < 2048) {
            Segment writableSegment$okio = commonWriteUtf8CodePoint.writableSegment$okio(2);
            writableSegment$okio.data[writableSegment$okio.limit] = (byte) ((i >> 6) | 192);
            writableSegment$okio.data[writableSegment$okio.limit + 1] = (byte) (128 | (i & 63));
            writableSegment$okio.limit += 2;
            commonWriteUtf8CodePoint.setSize$okio(commonWriteUtf8CodePoint.size() + 2);
        } else if (55296 <= i && 57343 >= i) {
            commonWriteUtf8CodePoint.writeByte(63);
        } else if (i < 65536) {
            Segment writableSegment$okio2 = commonWriteUtf8CodePoint.writableSegment$okio(3);
            writableSegment$okio2.data[writableSegment$okio2.limit] = (byte) ((i >> 12) | 224);
            writableSegment$okio2.data[writableSegment$okio2.limit + 1] = (byte) ((63 & (i >> 6)) | 128);
            writableSegment$okio2.data[writableSegment$okio2.limit + 2] = (byte) (128 | (i & 63));
            writableSegment$okio2.limit += 3;
            commonWriteUtf8CodePoint.setSize$okio(commonWriteUtf8CodePoint.size() + 3);
        } else {
            if (i > 1114111) {
                StringBuilder append = new StringBuilder().append("Unexpected code point: 0x");
                String hexString = Util.toHexString(i);
                Log5ECF72.a(hexString);
                LogE84000.a(hexString);
                Log229316.a(hexString);
                throw new IllegalArgumentException(append.append(hexString).toString());
            }
            Segment writableSegment$okio3 = commonWriteUtf8CodePoint.writableSegment$okio(4);
            writableSegment$okio3.data[writableSegment$okio3.limit] = (byte) ((i >> 18) | 240);
            writableSegment$okio3.data[writableSegment$okio3.limit + 1] = (byte) (((i >> 12) & 63) | 128);
            writableSegment$okio3.data[writableSegment$okio3.limit + 2] = (byte) (((i >> 6) & 63) | 128);
            writableSegment$okio3.data[writableSegment$okio3.limit + 3] = (byte) (128 | (i & 63));
            writableSegment$okio3.limit += 4;
            commonWriteUtf8CodePoint.setSize$okio(commonWriteUtf8CodePoint.size() + 4);
        }
        return commonWriteUtf8CodePoint;
    }

    public static final byte[] getHEX_DIGIT_BYTES() {
        return HEX_DIGIT_BYTES;
    }

    public static final boolean rangeEquals(Segment segment, int segmentPos, byte[] bytes, int bytesOffset, int bytesLimit) {
        Intrinsics.checkNotNullParameter(segment, "segment");
        Intrinsics.checkNotNullParameter(bytes, "bytes");
        Segment segment2 = segment;
        int i = segmentPos;
        int i2 = segment2.limit;
        byte[] bArr = segment2.data;
        for (int i3 = bytesOffset; i3 < bytesLimit; i3++) {
            if (i == i2) {
                Segment segment3 = segment2.next;
                Intrinsics.checkNotNull(segment3);
                segment2 = segment3;
                bArr = segment2.data;
                i = segment2.pos;
                i2 = segment2.limit;
            }
            if (bArr[i] != bytes[i3]) {
                return false;
            }
            i++;
        }
        return true;
    }

    public static final String readUtf8Line(Buffer readUtf8Line, long newline) {
        Intrinsics.checkNotNullParameter(readUtf8Line, "$this$readUtf8Line");
        if (newline <= 0 || readUtf8Line.getByte(newline - 1) != ((byte) 13)) {
            String readUtf8 = readUtf8Line.readUtf8(newline);
            readUtf8Line.skip(1L);
            return readUtf8;
        }
        String readUtf82 = readUtf8Line.readUtf8(newline - 1);
        readUtf8Line.skip(2L);
        return readUtf82;
    }

    public static final <T> T seek(Buffer seek, long fromIndex, Function2<? super Segment, ? super Long, ? extends T> lambda) {
        Intrinsics.checkNotNullParameter(seek, "$this$seek");
        Intrinsics.checkNotNullParameter(lambda, "lambda");
        Segment segment = seek.head;
        if (segment == null) {
            return lambda.invoke(null, -1L);
        }
        if (seek.size() - fromIndex < fromIndex) {
            long size = seek.size();
            while (size > fromIndex) {
                Segment segment2 = segment.prev;
                Intrinsics.checkNotNull(segment2);
                segment = segment2;
                size -= segment.limit - segment.pos;
            }
            return lambda.invoke(segment, Long.valueOf(size));
        }
        long j = 0;
        while (true) {
            long j2 = (segment.limit - segment.pos) + j;
            if (j2 > fromIndex) {
                return lambda.invoke(segment, Long.valueOf(j));
            }
            Segment segment3 = segment.next;
            Intrinsics.checkNotNull(segment3);
            segment = segment3;
            j = j2;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:31:0x0066, code lost:
    
        if (r21 == false) goto L92;
     */
    /* JADX WARN: Code restructure failed: missing block: B:32:0x0068, code lost:
    
        return -2;
     */
    /* JADX WARN: Code restructure failed: missing block: B:33:0x006b, code lost:
    
        return r11;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static final int selectPrefix(okio.Buffer r19, okio.Options r20, boolean r21) {
        /*
            Method dump skipped, instructions count: 210
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: okio.internal.BufferKt.selectPrefix(okio.Buffer, okio.Options, boolean):int");
    }

    public static /* synthetic */ int selectPrefix$default(Buffer buffer, Options options, boolean z, int i, Object obj) {
        if ((i & 2) != 0) {
            z = false;
        }
        return selectPrefix(buffer, options, z);
    }
}
