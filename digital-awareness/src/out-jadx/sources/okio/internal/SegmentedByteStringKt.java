package okio.internal;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.ArraysKt;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;
import okhttp3.HttpUrl;
import okio.Buffer;
import okio.ByteString;
import okio.Segment;
import okio.SegmentedByteString;
import okio.Util;

/* compiled from: SegmentedByteString.kt */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000R\n\u0000\n\u0002\u0010\b\n\u0002\u0010\u0015\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u0005\n\u0002\b\u0003\n\u0002\u0010\u0012\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\u001a$\u0010\u0000\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u00012\u0006\u0010\u0004\u001a\u00020\u00012\u0006\u0010\u0005\u001a\u00020\u0001H\u0000\u001a\u0017\u0010\u0006\u001a\u00020\u0007*\u00020\b2\b\u0010\t\u001a\u0004\u0018\u00010\nH\u0080\b\u001a\r\u0010\u000b\u001a\u00020\u0001*\u00020\bH\u0080\b\u001a\r\u0010\f\u001a\u00020\u0001*\u00020\bH\u0080\b\u001a\u0015\u0010\r\u001a\u00020\u000e*\u00020\b2\u0006\u0010\u000f\u001a\u00020\u0001H\u0080\b\u001a-\u0010\u0010\u001a\u00020\u0007*\u00020\b2\u0006\u0010\u0011\u001a\u00020\u00012\u0006\u0010\t\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u00012\u0006\u0010\u0014\u001a\u00020\u0001H\u0080\b\u001a-\u0010\u0010\u001a\u00020\u0007*\u00020\b2\u0006\u0010\u0011\u001a\u00020\u00012\u0006\u0010\t\u001a\u00020\u00152\u0006\u0010\u0013\u001a\u00020\u00012\u0006\u0010\u0014\u001a\u00020\u0001H\u0080\b\u001a\u001d\u0010\u0016\u001a\u00020\u0015*\u00020\b2\u0006\u0010\u0017\u001a\u00020\u00012\u0006\u0010\u0018\u001a\u00020\u0001H\u0080\b\u001a\r\u0010\u0019\u001a\u00020\u0012*\u00020\bH\u0080\b\u001a%\u0010\u001a\u001a\u00020\u001b*\u00020\b2\u0006\u0010\u001c\u001a\u00020\u001d2\u0006\u0010\u0011\u001a\u00020\u00012\u0006\u0010\u0014\u001a\u00020\u0001H\u0080\b\u001a]\u0010\u001e\u001a\u00020\u001b*\u00020\b2K\u0010\u001f\u001aG\u0012\u0013\u0012\u00110\u0012¢\u0006\f\b!\u0012\b\b\"\u0012\u0004\b\b(#\u0012\u0013\u0012\u00110\u0001¢\u0006\f\b!\u0012\b\b\"\u0012\u0004\b\b(\u0011\u0012\u0013\u0012\u00110\u0001¢\u0006\f\b!\u0012\b\b\"\u0012\u0004\b\b(\u0014\u0012\u0004\u0012\u00020\u001b0 H\u0080\bø\u0001\u0000\u001aj\u0010\u001e\u001a\u00020\u001b*\u00020\b2\u0006\u0010\u0017\u001a\u00020\u00012\u0006\u0010\u0018\u001a\u00020\u00012K\u0010\u001f\u001aG\u0012\u0013\u0012\u00110\u0012¢\u0006\f\b!\u0012\b\b\"\u0012\u0004\b\b(#\u0012\u0013\u0012\u00110\u0001¢\u0006\f\b!\u0012\b\b\"\u0012\u0004\b\b(\u0011\u0012\u0013\u0012\u00110\u0001¢\u0006\f\b!\u0012\b\b\"\u0012\u0004\b\b(\u0014\u0012\u0004\u0012\u00020\u001b0 H\u0082\b\u001a\u0014\u0010$\u001a\u00020\u0001*\u00020\b2\u0006\u0010\u000f\u001a\u00020\u0001H\u0000\u0082\u0002\u0007\n\u0005\b\u009920\u0001¨\u0006%"}, d2 = {"binarySearch", HttpUrl.FRAGMENT_ENCODE_SET, HttpUrl.FRAGMENT_ENCODE_SET, "value", "fromIndex", "toIndex", "commonEquals", HttpUrl.FRAGMENT_ENCODE_SET, "Lokio/SegmentedByteString;", "other", HttpUrl.FRAGMENT_ENCODE_SET, "commonGetSize", "commonHashCode", "commonInternalGet", HttpUrl.FRAGMENT_ENCODE_SET, "pos", "commonRangeEquals", TypedValues.CycleType.S_WAVE_OFFSET, HttpUrl.FRAGMENT_ENCODE_SET, "otherOffset", "byteCount", "Lokio/ByteString;", "commonSubstring", "beginIndex", "endIndex", "commonToByteArray", "commonWrite", HttpUrl.FRAGMENT_ENCODE_SET, "buffer", "Lokio/Buffer;", "forEachSegment", "action", "Lkotlin/Function3;", "Lkotlin/ParameterName;", "name", "data", "segment", "okio"}, k = 2, mv = {1, 4, 0})
/* loaded from: classes.dex */
public final class SegmentedByteStringKt {
    public static final int binarySearch(int[] binarySearch, int value, int fromIndex, int toIndex) {
        Intrinsics.checkNotNullParameter(binarySearch, "$this$binarySearch");
        int i = fromIndex;
        int i2 = toIndex - 1;
        while (i <= i2) {
            int i3 = (i + i2) >>> 1;
            int i4 = binarySearch[i3];
            if (i4 < value) {
                i = i3 + 1;
            } else {
                if (i4 <= value) {
                    return i3;
                }
                i2 = i3 - 1;
            }
        }
        return (-i) - 1;
    }

    public static final boolean commonEquals(SegmentedByteString commonEquals, Object other) {
        Intrinsics.checkNotNullParameter(commonEquals, "$this$commonEquals");
        if (other == commonEquals) {
            return true;
        }
        if (other instanceof ByteString) {
            return ((ByteString) other).size() == commonEquals.size() && commonEquals.rangeEquals(0, (ByteString) other, 0, commonEquals.size());
        }
        return false;
    }

    public static final int commonGetSize(SegmentedByteString commonGetSize) {
        Intrinsics.checkNotNullParameter(commonGetSize, "$this$commonGetSize");
        return commonGetSize.getDirectory()[commonGetSize.getSegments().length - 1];
    }

    public static final int commonHashCode(SegmentedByteString commonHashCode) {
        Intrinsics.checkNotNullParameter(commonHashCode, "$this$commonHashCode");
        int hashCode$okio = commonHashCode.getHashCode();
        if (hashCode$okio != 0) {
            return hashCode$okio;
        }
        int i = 1;
        int length = commonHashCode.getSegments().length;
        int i2 = 0;
        for (int i3 = 0; i3 < length; i3++) {
            int i4 = commonHashCode.getDirectory()[length + i3];
            int i5 = commonHashCode.getDirectory()[i3];
            byte[] bArr = commonHashCode.getSegments()[i3];
            int i6 = i4 + (i5 - i2);
            for (int i7 = i4; i7 < i6; i7++) {
                i = (i * 31) + bArr[i7];
            }
            i2 = i5;
        }
        commonHashCode.setHashCode$okio(i);
        return i;
    }

    public static final byte commonInternalGet(SegmentedByteString commonInternalGet, int pos) {
        Intrinsics.checkNotNullParameter(commonInternalGet, "$this$commonInternalGet");
        Util.checkOffsetAndCount(commonInternalGet.getDirectory()[commonInternalGet.getSegments().length - 1], pos, 1L);
        int segment = segment(commonInternalGet, pos);
        return commonInternalGet.getSegments()[segment][(pos - (segment == 0 ? 0 : commonInternalGet.getDirectory()[segment - 1])) + commonInternalGet.getDirectory()[commonInternalGet.getSegments().length + segment]];
    }

    public static final boolean commonRangeEquals(SegmentedByteString commonRangeEquals, int offset, ByteString other, int otherOffset, int byteCount) {
        boolean z = false;
        Intrinsics.checkNotNullParameter(commonRangeEquals, "$this$commonRangeEquals");
        Intrinsics.checkNotNullParameter(other, "other");
        if (offset >= 0 && offset <= commonRangeEquals.size() - byteCount) {
            int i = otherOffset;
            int i2 = offset + byteCount;
            int segment = segment(commonRangeEquals, offset);
            int i3 = offset;
            while (i3 < i2) {
                int i4 = segment == 0 ? 0 : commonRangeEquals.getDirectory()[segment - 1];
                int i5 = commonRangeEquals.getDirectory()[segment] - i4;
                int i6 = commonRangeEquals.getDirectory()[commonRangeEquals.getSegments().length + segment];
                int min = Math.min(i2, i4 + i5) - i3;
                boolean z2 = z;
                if (!other.rangeEquals(i, commonRangeEquals.getSegments()[segment], (i3 - i4) + i6, min)) {
                    return false;
                }
                i += min;
                i3 += min;
                segment++;
                z = z2;
            }
            return true;
        }
        return false;
    }

    public static final boolean commonRangeEquals(SegmentedByteString commonRangeEquals, int offset, byte[] other, int otherOffset, int byteCount) {
        Intrinsics.checkNotNullParameter(commonRangeEquals, "$this$commonRangeEquals");
        Intrinsics.checkNotNullParameter(other, "other");
        if (offset < 0 || offset > commonRangeEquals.size() - byteCount || otherOffset < 0 || otherOffset > other.length - byteCount) {
            return false;
        }
        int i = otherOffset;
        int i2 = offset + byteCount;
        int segment = segment(commonRangeEquals, offset);
        int i3 = offset;
        while (i3 < i2) {
            int i4 = segment == 0 ? 0 : commonRangeEquals.getDirectory()[segment - 1];
            int i5 = commonRangeEquals.getDirectory()[segment] - i4;
            int i6 = commonRangeEquals.getDirectory()[commonRangeEquals.getSegments().length + segment];
            int min = Math.min(i2, i4 + i5) - i3;
            if (!Util.arrayRangeEquals(commonRangeEquals.getSegments()[segment], i6 + (i3 - i4), other, i, min)) {
                return false;
            }
            i += min;
            i3 += min;
            segment++;
        }
        return true;
    }

    public static final ByteString commonSubstring(SegmentedByteString commonSubstring, int beginIndex, int endIndex) {
        Intrinsics.checkNotNullParameter(commonSubstring, "$this$commonSubstring");
        if (!(beginIndex >= 0)) {
            throw new IllegalArgumentException(("beginIndex=" + beginIndex + " < 0").toString());
        }
        if (!(endIndex <= commonSubstring.size())) {
            throw new IllegalArgumentException(("endIndex=" + endIndex + " > length(" + commonSubstring.size() + ')').toString());
        }
        int i = endIndex - beginIndex;
        if (!(i >= 0)) {
            throw new IllegalArgumentException(("endIndex=" + endIndex + " < beginIndex=" + beginIndex).toString());
        }
        if (beginIndex == 0 && endIndex == commonSubstring.size()) {
            return commonSubstring;
        }
        if (beginIndex == endIndex) {
            return ByteString.EMPTY;
        }
        int segment = segment(commonSubstring, beginIndex);
        int segment2 = segment(commonSubstring, endIndex - 1);
        byte[][] bArr = (byte[][]) ArraysKt.copyOfRange(commonSubstring.getSegments(), segment, segment2 + 1);
        int[] iArr = new int[bArr.length * 2];
        int i2 = 0;
        if (segment <= segment2) {
            int i3 = segment;
            while (true) {
                iArr[i2] = Math.min(commonSubstring.getDirectory()[i3] - beginIndex, i);
                int i4 = i2 + 1;
                iArr[i2 + bArr.length] = commonSubstring.getDirectory()[commonSubstring.getSegments().length + i3];
                if (i3 == segment2) {
                    break;
                }
                i3++;
                i2 = i4;
            }
        }
        int i5 = segment != 0 ? commonSubstring.getDirectory()[segment - 1] : 0;
        int length = bArr.length;
        iArr[length] = iArr[length] + (beginIndex - i5);
        return new SegmentedByteString(bArr, iArr);
    }

    public static final byte[] commonToByteArray(SegmentedByteString commonToByteArray) {
        Intrinsics.checkNotNullParameter(commonToByteArray, "$this$commonToByteArray");
        byte[] bArr = new byte[commonToByteArray.size()];
        int i = 0;
        int length = commonToByteArray.getSegments().length;
        int i2 = 0;
        for (int i3 = 0; i3 < length; i3++) {
            int i4 = commonToByteArray.getDirectory()[length + i3];
            int i5 = commonToByteArray.getDirectory()[i3];
            int i6 = i5 - i2;
            ArraysKt.copyInto(commonToByteArray.getSegments()[i3], bArr, i, i4, i4 + i6);
            i += i6;
            i2 = i5;
        }
        return bArr;
    }

    public static final void commonWrite(SegmentedByteString commonWrite, Buffer buffer, int offset, int byteCount) {
        Intrinsics.checkNotNullParameter(commonWrite, "$this$commonWrite");
        Intrinsics.checkNotNullParameter(buffer, "buffer");
        int i = offset + byteCount;
        int segment = segment(commonWrite, offset);
        int i2 = offset;
        while (i2 < i) {
            int i3 = segment == 0 ? 0 : commonWrite.getDirectory()[segment - 1];
            int i4 = commonWrite.getDirectory()[segment] - i3;
            int i5 = commonWrite.getDirectory()[commonWrite.getSegments().length + segment];
            int min = Math.min(i, i3 + i4) - i2;
            int i6 = (i2 - i3) + i5;
            Segment segment2 = new Segment(commonWrite.getSegments()[segment], i6, i6 + min, true, false);
            if (buffer.head == null) {
                segment2.prev = segment2;
                segment2.next = segment2.prev;
                buffer.head = segment2.next;
            } else {
                Segment segment3 = buffer.head;
                Intrinsics.checkNotNull(segment3);
                Segment segment4 = segment3.prev;
                Intrinsics.checkNotNull(segment4);
                segment4.push(segment2);
            }
            i2 += min;
            segment++;
        }
        buffer.setSize$okio(buffer.size() + commonWrite.size());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void forEachSegment(SegmentedByteString $this$forEachSegment, int beginIndex, int endIndex, Function3<? super byte[], ? super Integer, ? super Integer, Unit> function3) {
        int segment = segment($this$forEachSegment, beginIndex);
        int i = beginIndex;
        while (i < endIndex) {
            int i2 = segment == 0 ? 0 : $this$forEachSegment.getDirectory()[segment - 1];
            int i3 = $this$forEachSegment.getDirectory()[segment] - i2;
            int i4 = $this$forEachSegment.getDirectory()[$this$forEachSegment.getSegments().length + segment];
            int min = Math.min(endIndex, i2 + i3) - i;
            function3.invoke($this$forEachSegment.getSegments()[segment], Integer.valueOf((i - i2) + i4), Integer.valueOf(min));
            i += min;
            segment++;
        }
    }

    public static final void forEachSegment(SegmentedByteString forEachSegment, Function3<? super byte[], ? super Integer, ? super Integer, Unit> action) {
        Intrinsics.checkNotNullParameter(forEachSegment, "$this$forEachSegment");
        Intrinsics.checkNotNullParameter(action, "action");
        int length = forEachSegment.getSegments().length;
        int i = 0;
        for (int i2 = 0; i2 < length; i2++) {
            int i3 = forEachSegment.getDirectory()[length + i2];
            int i4 = forEachSegment.getDirectory()[i2];
            action.invoke(forEachSegment.getSegments()[i2], Integer.valueOf(i3), Integer.valueOf(i4 - i));
            i = i4;
        }
    }

    public static final int segment(SegmentedByteString segment, int pos) {
        Intrinsics.checkNotNullParameter(segment, "$this$segment");
        int binarySearch = binarySearch(segment.getDirectory(), pos + 1, 0, segment.getSegments().length);
        return binarySearch >= 0 ? binarySearch : ~binarySearch;
    }
}
