package kotlin.internal;

import kotlin.Metadata;
import kotlin.UInt;
import kotlin.ULong;
import kotlin.UnsignedKt;
import okhttp3.HttpUrl;

/* compiled from: UProgressionUtil.kt */
@Metadata(d1 = {"\u0000 \n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\b\n\u0000\n\u0002\u0010\t\n\u0002\b\u0002\u001a*\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00012\u0006\u0010\u0003\u001a\u00020\u00012\u0006\u0010\u0004\u001a\u00020\u0001H\u0002ø\u0001\u0000¢\u0006\u0004\b\u0005\u0010\u0006\u001a*\u0010\u0000\u001a\u00020\u00072\u0006\u0010\u0002\u001a\u00020\u00072\u0006\u0010\u0003\u001a\u00020\u00072\u0006\u0010\u0004\u001a\u00020\u0007H\u0002ø\u0001\u0000¢\u0006\u0004\b\b\u0010\t\u001a*\u0010\n\u001a\u00020\u00012\u0006\u0010\u000b\u001a\u00020\u00012\u0006\u0010\f\u001a\u00020\u00012\u0006\u0010\r\u001a\u00020\u000eH\u0001ø\u0001\u0000¢\u0006\u0004\b\u000f\u0010\u0006\u001a*\u0010\n\u001a\u00020\u00072\u0006\u0010\u000b\u001a\u00020\u00072\u0006\u0010\f\u001a\u00020\u00072\u0006\u0010\r\u001a\u00020\u0010H\u0001ø\u0001\u0000¢\u0006\u0004\b\u0011\u0010\t\u0082\u0002\u0004\n\u0002\b\u0019¨\u0006\u0012"}, d2 = {"differenceModulo", "Lkotlin/UInt;", "a", "b", "c", "differenceModulo-WZ9TVnA", "(III)I", "Lkotlin/ULong;", "differenceModulo-sambcqE", "(JJJ)J", "getProgressionLastElement", "start", "end", "step", HttpUrl.FRAGMENT_ENCODE_SET, "getProgressionLastElement-Nkh28Cs", HttpUrl.FRAGMENT_ENCODE_SET, "getProgressionLastElement-7ftBX0g", "kotlin-stdlib"}, k = 2, mv = {1, 7, 1}, xi = 48)
/* loaded from: classes.dex */
public final class UProgressionUtilKt {
    /* renamed from: differenceModulo-WZ9TVnA, reason: not valid java name */
    private static final int m1256differenceModuloWZ9TVnA(int a, int b, int c) {
        int m392uintRemainderJ1ME1BU = UnsignedKt.m392uintRemainderJ1ME1BU(a, c);
        int m392uintRemainderJ1ME1BU2 = UnsignedKt.m392uintRemainderJ1ME1BU(b, c);
        return UInt.m138constructorimpl(UnsignedKt.uintCompare(m392uintRemainderJ1ME1BU, m392uintRemainderJ1ME1BU2) >= 0 ? m392uintRemainderJ1ME1BU - m392uintRemainderJ1ME1BU2 : UInt.m138constructorimpl(m392uintRemainderJ1ME1BU - m392uintRemainderJ1ME1BU2) + c);
    }

    /* renamed from: differenceModulo-sambcqE, reason: not valid java name */
    private static final long m1257differenceModulosambcqE(long a, long b, long c) {
        long m394ulongRemaindereb3DHEI = UnsignedKt.m394ulongRemaindereb3DHEI(a, c);
        long m394ulongRemaindereb3DHEI2 = UnsignedKt.m394ulongRemaindereb3DHEI(b, c);
        return ULong.m216constructorimpl(UnsignedKt.ulongCompare(m394ulongRemaindereb3DHEI, m394ulongRemaindereb3DHEI2) >= 0 ? m394ulongRemaindereb3DHEI - m394ulongRemaindereb3DHEI2 : ULong.m216constructorimpl(m394ulongRemaindereb3DHEI - m394ulongRemaindereb3DHEI2) + c);
    }

    /* renamed from: getProgressionLastElement-7ftBX0g, reason: not valid java name */
    public static final long m1258getProgressionLastElement7ftBX0g(long start, long end, long step) {
        if (step > 0) {
            if (UnsignedKt.ulongCompare(start, end) < 0) {
                return ULong.m216constructorimpl(end - m1257differenceModulosambcqE(end, start, ULong.m216constructorimpl(step)));
            }
        } else {
            if (step >= 0) {
                throw new IllegalArgumentException("Step is zero.");
            }
            if (UnsignedKt.ulongCompare(start, end) > 0) {
                return ULong.m216constructorimpl(m1257differenceModulosambcqE(start, end, ULong.m216constructorimpl(-step)) + end);
            }
        }
        return end;
    }

    /* renamed from: getProgressionLastElement-Nkh28Cs, reason: not valid java name */
    public static final int m1259getProgressionLastElementNkh28Cs(int start, int end, int step) {
        if (step > 0) {
            if (UnsignedKt.uintCompare(start, end) < 0) {
                return UInt.m138constructorimpl(end - m1256differenceModuloWZ9TVnA(end, start, UInt.m138constructorimpl(step)));
            }
        } else {
            if (step >= 0) {
                throw new IllegalArgumentException("Step is zero.");
            }
            if (UnsignedKt.uintCompare(start, end) > 0) {
                return UInt.m138constructorimpl(m1256differenceModuloWZ9TVnA(start, end, UInt.m138constructorimpl(-step)) + end);
            }
        }
        return end;
    }
}
