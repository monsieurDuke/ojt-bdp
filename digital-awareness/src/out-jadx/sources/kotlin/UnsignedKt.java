package kotlin;

import kotlin.jvm.internal.Intrinsics;
import kotlin.text.CharsKt;
import mt.Log229316;
import mt.Log5ECF72;
import mt.LogE84000;
import okhttp3.HttpUrl;

/* compiled from: 011F.java */
@Metadata(d1 = {"\u00000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0006\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\t\n\u0002\u0010\t\n\u0002\b\u0007\n\u0002\u0010\u000e\n\u0002\b\u0002\u001a\u0018\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003H\u0001ø\u0001\u0000¢\u0006\u0002\u0010\u0004\u001a\u0018\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0002\u001a\u00020\u0003H\u0001ø\u0001\u0000¢\u0006\u0002\u0010\u0007\u001a\u0018\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\t2\u0006\u0010\u000b\u001a\u00020\tH\u0001\u001a\"\u0010\f\u001a\u00020\u00012\u0006\u0010\n\u001a\u00020\u00012\u0006\u0010\u000b\u001a\u00020\u0001H\u0001ø\u0001\u0000¢\u0006\u0004\b\r\u0010\u000e\u001a\"\u0010\u000f\u001a\u00020\u00012\u0006\u0010\n\u001a\u00020\u00012\u0006\u0010\u000b\u001a\u00020\u0001H\u0001ø\u0001\u0000¢\u0006\u0004\b\u0010\u0010\u000e\u001a\u0010\u0010\u0011\u001a\u00020\u00032\u0006\u0010\u0002\u001a\u00020\tH\u0001\u001a\u0018\u0010\u0012\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u00132\u0006\u0010\u000b\u001a\u00020\u0013H\u0001\u001a\"\u0010\u0014\u001a\u00020\u00062\u0006\u0010\n\u001a\u00020\u00062\u0006\u0010\u000b\u001a\u00020\u0006H\u0001ø\u0001\u0000¢\u0006\u0004\b\u0015\u0010\u0016\u001a\"\u0010\u0017\u001a\u00020\u00062\u0006\u0010\n\u001a\u00020\u00062\u0006\u0010\u000b\u001a\u00020\u0006H\u0001ø\u0001\u0000¢\u0006\u0004\b\u0018\u0010\u0016\u001a\u0010\u0010\u0019\u001a\u00020\u00032\u0006\u0010\u0002\u001a\u00020\u0013H\u0001\u001a\u0010\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u0002\u001a\u00020\u0013H\u0000\u001a\u0018\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u0002\u001a\u00020\u00132\u0006\u0010\u001c\u001a\u00020\tH\u0000\u0082\u0002\u0004\n\u0002\b\u0019¨\u0006\u001d"}, d2 = {"doubleToUInt", "Lkotlin/UInt;", "v", HttpUrl.FRAGMENT_ENCODE_SET, "(D)I", "doubleToULong", "Lkotlin/ULong;", "(D)J", "uintCompare", HttpUrl.FRAGMENT_ENCODE_SET, "v1", "v2", "uintDivide", "uintDivide-J1ME1BU", "(II)I", "uintRemainder", "uintRemainder-J1ME1BU", "uintToDouble", "ulongCompare", HttpUrl.FRAGMENT_ENCODE_SET, "ulongDivide", "ulongDivide-eb3DHEI", "(JJ)J", "ulongRemainder", "ulongRemainder-eb3DHEI", "ulongToDouble", "ulongToString", HttpUrl.FRAGMENT_ENCODE_SET, "base", "kotlin-stdlib"}, k = 2, mv = {1, 7, 1}, xi = 48)
/* loaded from: classes.dex */
public final class UnsignedKt {
    public static final int doubleToUInt(double v) {
        if (!Double.isNaN(v) && v > uintToDouble(0)) {
            if (v >= uintToDouble(-1)) {
                return -1;
            }
            return v <= 2.147483647E9d ? UInt.m138constructorimpl((int) v) : UInt.m138constructorimpl(UInt.m138constructorimpl((int) (v - Integer.MAX_VALUE)) + UInt.m138constructorimpl(Integer.MAX_VALUE));
        }
        return 0;
    }

    public static final long doubleToULong(double v) {
        if (!Double.isNaN(v) && v > ulongToDouble(0L)) {
            if (v >= ulongToDouble(-1L)) {
                return -1L;
            }
            return v < 9.223372036854776E18d ? ULong.m216constructorimpl((long) v) : ULong.m216constructorimpl(ULong.m216constructorimpl((long) (v - 9.223372036854776E18d)) - Long.MIN_VALUE);
        }
        return 0L;
    }

    public static final int uintCompare(int v1, int v2) {
        return Intrinsics.compare(v1 ^ Integer.MIN_VALUE, Integer.MIN_VALUE ^ v2);
    }

    /* renamed from: uintDivide-J1ME1BU, reason: not valid java name */
    public static final int m391uintDivideJ1ME1BU(int v1, int v2) {
        return UInt.m138constructorimpl((int) ((v1 & 4294967295L) / (4294967295L & v2)));
    }

    /* renamed from: uintRemainder-J1ME1BU, reason: not valid java name */
    public static final int m392uintRemainderJ1ME1BU(int v1, int v2) {
        return UInt.m138constructorimpl((int) ((v1 & 4294967295L) % (4294967295L & v2)));
    }

    public static final double uintToDouble(int v) {
        return (Integer.MAX_VALUE & v) + (((v >>> 31) << 30) * 2);
    }

    public static final int ulongCompare(long v1, long v2) {
        return Intrinsics.compare(v1 ^ Long.MIN_VALUE, Long.MIN_VALUE ^ v2);
    }

    /* renamed from: ulongDivide-eb3DHEI, reason: not valid java name */
    public static final long m393ulongDivideeb3DHEI(long v1, long v2) {
        if (v2 < 0) {
            return ULong.m216constructorimpl(ulongCompare(v1, v2) >= 0 ? 1L : 0L);
        }
        if (v1 >= 0) {
            return ULong.m216constructorimpl(v1 / v2);
        }
        long j = ((v1 >>> 1) / v2) << 1;
        return ULong.m216constructorimpl((ulongCompare(ULong.m216constructorimpl(v1 - (j * v2)), ULong.m216constructorimpl(v2)) < 0 ? 0 : 1) + j);
    }

    /* renamed from: ulongRemainder-eb3DHEI, reason: not valid java name */
    public static final long m394ulongRemaindereb3DHEI(long v1, long v2) {
        if (v2 < 0) {
            return ulongCompare(v1, v2) < 0 ? v1 : ULong.m216constructorimpl(v1 - v2);
        }
        if (v1 >= 0) {
            return ULong.m216constructorimpl(v1 % v2);
        }
        long j = v1 - ((((v1 >>> 1) / v2) << 1) * v2);
        return ULong.m216constructorimpl(j - (ulongCompare(ULong.m216constructorimpl(j), ULong.m216constructorimpl(v2)) >= 0 ? v2 : 0L));
    }

    public static final double ulongToDouble(long v) {
        return ((v >>> 11) * 2048) + (2047 & v);
    }

    /* JADX WARN: Failed to parse debug info
    jadx.core.utils.exceptions.JadxRuntimeException: Failed to parse type string: null
    	at jadx.core.dex.instructions.args.ArgType.parse(ArgType.java:736)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.getVarType(DebugInfoAttachVisitor.java:139)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.attachDebugInfo(DebugInfoAttachVisitor.java:94)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.processDebugInfo(DebugInfoAttachVisitor.java:51)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.visit(DebugInfoAttachVisitor.java:41)
     */
    public static final String ulongToString(long j) {
        String ulongToString = ulongToString(j, 10);
        Log5ECF72.a(ulongToString);
        LogE84000.a(ulongToString);
        Log229316.a(ulongToString);
        return ulongToString;
    }

    /* JADX WARN: Failed to parse debug info
    jadx.core.utils.exceptions.JadxRuntimeException: Failed to parse type string: null
    	at jadx.core.dex.instructions.args.ArgType.parse(ArgType.java:736)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.getVarType(DebugInfoAttachVisitor.java:139)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.attachDebugInfo(DebugInfoAttachVisitor.java:94)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.processDebugInfo(DebugInfoAttachVisitor.java:51)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.visit(DebugInfoAttachVisitor.java:41)
     */
    public static final String ulongToString(long j, int i) {
        if (j >= 0) {
            String l = Long.toString(j, CharsKt.checkRadix(i));
            Log5ECF72.a(l);
            LogE84000.a(l);
            Log229316.a(l);
            Intrinsics.checkNotNullExpressionValue(l, "toString(this, checkRadix(radix))");
            return l;
        }
        long j2 = ((j >>> 1) / i) << 1;
        long j3 = j - (i * j2);
        if (j3 >= i) {
            j3 -= i;
            j2++;
        }
        StringBuilder sb = new StringBuilder();
        String l2 = Long.toString(j2, CharsKt.checkRadix(i));
        Log5ECF72.a(l2);
        LogE84000.a(l2);
        Log229316.a(l2);
        Intrinsics.checkNotNullExpressionValue(l2, "toString(this, checkRadix(radix))");
        StringBuilder append = sb.append(l2);
        String l3 = Long.toString(j3, CharsKt.checkRadix(i));
        Log5ECF72.a(l3);
        LogE84000.a(l3);
        Log229316.a(l3);
        Intrinsics.checkNotNullExpressionValue(l3, "toString(this, checkRadix(radix))");
        return append.append(l3).toString();
    }
}
