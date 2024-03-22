package okio;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.ByteCompanionObject;
import kotlin.jvm.internal.Intrinsics;
import okhttp3.HttpUrl;

/* compiled from: Utf8.kt */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000D\n\u0000\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0010\u0005\n\u0000\n\u0002\u0010\f\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010\u0012\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\t\n\u0002\b\u0002\u001a\u0011\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u0001H\u0080\b\u001a\u0011\u0010\u000e\u001a\u00020\f2\u0006\u0010\u000f\u001a\u00020\u0007H\u0080\b\u001a4\u0010\u0010\u001a\u00020\u0001*\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u00012\u0006\u0010\u0013\u001a\u00020\u00012\u0012\u0010\u0014\u001a\u000e\u0012\u0004\u0012\u00020\u0001\u0012\u0004\u0012\u00020\u00160\u0015H\u0080\bø\u0001\u0000\u001a4\u0010\u0017\u001a\u00020\u0001*\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u00012\u0006\u0010\u0013\u001a\u00020\u00012\u0012\u0010\u0014\u001a\u000e\u0012\u0004\u0012\u00020\u0001\u0012\u0004\u0012\u00020\u00160\u0015H\u0080\bø\u0001\u0000\u001a4\u0010\u0018\u001a\u00020\u0001*\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u00012\u0006\u0010\u0013\u001a\u00020\u00012\u0012\u0010\u0014\u001a\u000e\u0012\u0004\u0012\u00020\u0001\u0012\u0004\u0012\u00020\u00160\u0015H\u0080\bø\u0001\u0000\u001a4\u0010\u0019\u001a\u00020\u0016*\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u00012\u0006\u0010\u0013\u001a\u00020\u00012\u0012\u0010\u0014\u001a\u000e\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\u00160\u0015H\u0080\bø\u0001\u0000\u001a4\u0010\u001a\u001a\u00020\u0016*\u00020\u001b2\u0006\u0010\u0012\u001a\u00020\u00012\u0006\u0010\u0013\u001a\u00020\u00012\u0012\u0010\u0014\u001a\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\u00160\u0015H\u0080\bø\u0001\u0000\u001a4\u0010\u001c\u001a\u00020\u0016*\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u00012\u0006\u0010\u0013\u001a\u00020\u00012\u0012\u0010\u0014\u001a\u000e\u0012\u0004\u0012\u00020\u0001\u0012\u0004\u0012\u00020\u00160\u0015H\u0080\bø\u0001\u0000\u001a%\u0010\u001d\u001a\u00020\u001e*\u00020\u001b2\b\b\u0002\u0010\u0012\u001a\u00020\u00012\b\b\u0002\u0010\u0013\u001a\u00020\u0001H\u0007¢\u0006\u0002\b\u001f\"\u000e\u0010\u0000\u001a\u00020\u0001X\u0080T¢\u0006\u0002\n\u0000\"\u000e\u0010\u0002\u001a\u00020\u0001X\u0080T¢\u0006\u0002\n\u0000\"\u000e\u0010\u0003\u001a\u00020\u0001X\u0080T¢\u0006\u0002\n\u0000\"\u000e\u0010\u0004\u001a\u00020\u0001X\u0080T¢\u0006\u0002\n\u0000\"\u000e\u0010\u0005\u001a\u00020\u0001X\u0080T¢\u0006\u0002\n\u0000\"\u000e\u0010\u0006\u001a\u00020\u0007X\u0080T¢\u0006\u0002\n\u0000\"\u000e\u0010\b\u001a\u00020\tX\u0080T¢\u0006\u0002\n\u0000\"\u000e\u0010\n\u001a\u00020\u0001X\u0080T¢\u0006\u0002\n\u0000\u0082\u0002\u0007\n\u0005\b\u009920\u0001¨\u0006 "}, d2 = {"HIGH_SURROGATE_HEADER", HttpUrl.FRAGMENT_ENCODE_SET, "LOG_SURROGATE_HEADER", "MASK_2BYTES", "MASK_3BYTES", "MASK_4BYTES", "REPLACEMENT_BYTE", HttpUrl.FRAGMENT_ENCODE_SET, "REPLACEMENT_CHARACTER", HttpUrl.FRAGMENT_ENCODE_SET, "REPLACEMENT_CODE_POINT", "isIsoControl", HttpUrl.FRAGMENT_ENCODE_SET, "codePoint", "isUtf8Continuation", "byte", "process2Utf8Bytes", HttpUrl.FRAGMENT_ENCODE_SET, "beginIndex", "endIndex", "yield", "Lkotlin/Function1;", HttpUrl.FRAGMENT_ENCODE_SET, "process3Utf8Bytes", "process4Utf8Bytes", "processUtf16Chars", "processUtf8Bytes", HttpUrl.FRAGMENT_ENCODE_SET, "processUtf8CodePoints", "utf8Size", HttpUrl.FRAGMENT_ENCODE_SET, "size", "okio"}, k = 2, mv = {1, 4, 0})
/* loaded from: classes.dex */
public final class Utf8 {
    public static final int HIGH_SURROGATE_HEADER = 55232;
    public static final int LOG_SURROGATE_HEADER = 56320;
    public static final int MASK_2BYTES = 3968;
    public static final int MASK_3BYTES = -123008;
    public static final int MASK_4BYTES = 3678080;
    public static final byte REPLACEMENT_BYTE = 63;
    public static final char REPLACEMENT_CHARACTER = 65533;
    public static final int REPLACEMENT_CODE_POINT = 65533;

    public static final boolean isIsoControl(int codePoint) {
        return (codePoint >= 0 && 31 >= codePoint) || (127 <= codePoint && 159 >= codePoint);
    }

    public static final boolean isUtf8Continuation(byte b) {
        return (192 & b) == 128;
    }

    public static final int process2Utf8Bytes(byte[] process2Utf8Bytes, int beginIndex, int endIndex, Function1<? super Integer, Unit> yield) {
        Intrinsics.checkNotNullParameter(process2Utf8Bytes, "$this$process2Utf8Bytes");
        Intrinsics.checkNotNullParameter(yield, "yield");
        int i = beginIndex + 1;
        Integer valueOf = Integer.valueOf((int) REPLACEMENT_CODE_POINT);
        if (endIndex <= i) {
            yield.invoke(valueOf);
            return 1;
        }
        byte b = process2Utf8Bytes[beginIndex];
        byte b2 = process2Utf8Bytes[beginIndex + 1];
        if (!((192 & b2) == 128)) {
            yield.invoke(valueOf);
            return 1;
        }
        int i2 = (b2 ^ ByteCompanionObject.MIN_VALUE) ^ (b << 6);
        if (i2 < 128) {
            yield.invoke(valueOf);
            return 2;
        }
        yield.invoke(Integer.valueOf(i2));
        return 2;
    }

    public static final int process3Utf8Bytes(byte[] process3Utf8Bytes, int beginIndex, int endIndex, Function1<? super Integer, Unit> yield) {
        Intrinsics.checkNotNullParameter(process3Utf8Bytes, "$this$process3Utf8Bytes");
        Intrinsics.checkNotNullParameter(yield, "yield");
        int i = beginIndex + 2;
        Integer valueOf = Integer.valueOf((int) REPLACEMENT_CODE_POINT);
        if (endIndex <= i) {
            yield.invoke(valueOf);
            if (endIndex > beginIndex + 1) {
                if ((192 & process3Utf8Bytes[beginIndex + 1]) == 128) {
                    return 2;
                }
            }
            return 1;
        }
        byte b = process3Utf8Bytes[beginIndex];
        byte b2 = process3Utf8Bytes[beginIndex + 1];
        if (!((192 & b2) == 128)) {
            yield.invoke(valueOf);
            return 1;
        }
        byte b3 = process3Utf8Bytes[beginIndex + 2];
        if (!((192 & b3) == 128)) {
            yield.invoke(valueOf);
            return 2;
        }
        int i2 = (((-123008) ^ b3) ^ (b2 << 6)) ^ (b << 12);
        if (i2 < 2048) {
            yield.invoke(valueOf);
            return 3;
        }
        if (55296 <= i2 && 57343 >= i2) {
            yield.invoke(valueOf);
            return 3;
        }
        yield.invoke(Integer.valueOf(i2));
        return 3;
    }

    public static final int process4Utf8Bytes(byte[] process4Utf8Bytes, int beginIndex, int endIndex, Function1<? super Integer, Unit> yield) {
        Intrinsics.checkNotNullParameter(process4Utf8Bytes, "$this$process4Utf8Bytes");
        Intrinsics.checkNotNullParameter(yield, "yield");
        int i = beginIndex + 3;
        Integer valueOf = Integer.valueOf((int) REPLACEMENT_CODE_POINT);
        if (endIndex <= i) {
            yield.invoke(valueOf);
            if (endIndex > beginIndex + 1) {
                if ((192 & process4Utf8Bytes[beginIndex + 1]) == 128) {
                    if (endIndex > beginIndex + 2) {
                        if ((192 & process4Utf8Bytes[beginIndex + 2]) == 128) {
                            return 3;
                        }
                    }
                    return 2;
                }
            }
            return 1;
        }
        byte b = process4Utf8Bytes[beginIndex];
        byte b2 = process4Utf8Bytes[beginIndex + 1];
        if (!((192 & b2) == 128)) {
            yield.invoke(valueOf);
            return 1;
        }
        byte b3 = process4Utf8Bytes[beginIndex + 2];
        if (!((192 & b3) == 128)) {
            yield.invoke(valueOf);
            return 2;
        }
        byte b4 = process4Utf8Bytes[beginIndex + 3];
        if (!((192 & b4) == 128)) {
            yield.invoke(valueOf);
            return 3;
        }
        int i2 = (((3678080 ^ b4) ^ (b3 << 6)) ^ (b2 << 12)) ^ (b << 18);
        if (i2 > 1114111) {
            yield.invoke(valueOf);
            return 4;
        }
        if (55296 <= i2 && 57343 >= i2) {
            yield.invoke(valueOf);
            return 4;
        }
        if (i2 < 65536) {
            yield.invoke(valueOf);
            return 4;
        }
        yield.invoke(Integer.valueOf(i2));
        return 4;
    }

    /*  JADX ERROR: JadxRuntimeException in pass: IfRegionVisitor
        jadx.core.utils.exceptions.JadxRuntimeException: Can't remove SSA var: r12v12 int, still in use, count: 2, list:
          (r12v12 int) from 0x02fc: IF  (r12v12 int) != (65533 int)  -> B:75:0x02bd A[HIDDEN]
          (r12v12 int) from 0x02bd: PHI (r12v16 int) = (r12v12 int), (r12v13 int), (r12v15 int), (r12v17 int) binds: [B:89:0x02fc, B:87:0x02f7, B:83:0x02ec, B:74:0x02bb] A[DONT_GENERATE, DONT_INLINE]
        	at jadx.core.utils.InsnRemover.removeSsaVar(InsnRemover.java:152)
        	at jadx.core.utils.InsnRemover.unbindResult(InsnRemover.java:117)
        	at jadx.core.dex.visitors.regions.TernaryMod.makeTernaryInsn(TernaryMod.java:125)
        	at jadx.core.dex.visitors.regions.TernaryMod.processRegion(TernaryMod.java:62)
        	at jadx.core.dex.visitors.regions.TernaryMod.enterRegion(TernaryMod.java:45)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseInternal(DepthRegionTraversal.java:67)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.lambda$traverseInternal$0(DepthRegionTraversal.java:68)
        	at java.base/java.util.ArrayList.forEach(ArrayList.java:1511)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseInternal(DepthRegionTraversal.java:68)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.lambda$traverseInternal$0(DepthRegionTraversal.java:68)
        	at java.base/java.util.ArrayList.forEach(ArrayList.java:1511)
        	at java.base/java.util.Collections$UnmodifiableCollection.forEach(Collections.java:1092)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseInternal(DepthRegionTraversal.java:68)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.lambda$traverseInternal$0(DepthRegionTraversal.java:68)
        	at java.base/java.util.ArrayList.forEach(ArrayList.java:1511)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseInternal(DepthRegionTraversal.java:68)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.lambda$traverseInternal$0(DepthRegionTraversal.java:68)
        	at java.base/java.util.ArrayList.forEach(ArrayList.java:1511)
        	at java.base/java.util.Collections$UnmodifiableCollection.forEach(Collections.java:1092)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseInternal(DepthRegionTraversal.java:68)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.lambda$traverseInternal$0(DepthRegionTraversal.java:68)
        	at java.base/java.util.ArrayList.forEach(ArrayList.java:1511)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseInternal(DepthRegionTraversal.java:68)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.lambda$traverseInternal$0(DepthRegionTraversal.java:68)
        	at java.base/java.util.ArrayList.forEach(ArrayList.java:1511)
        	at java.base/java.util.Collections$UnmodifiableCollection.forEach(Collections.java:1092)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseInternal(DepthRegionTraversal.java:68)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.lambda$traverseInternal$0(DepthRegionTraversal.java:68)
        	at java.base/java.util.ArrayList.forEach(ArrayList.java:1511)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseInternal(DepthRegionTraversal.java:68)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.lambda$traverseInternal$0(DepthRegionTraversal.java:68)
        	at java.base/java.util.ArrayList.forEach(ArrayList.java:1511)
        	at java.base/java.util.Collections$UnmodifiableCollection.forEach(Collections.java:1092)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseInternal(DepthRegionTraversal.java:68)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.lambda$traverseInternal$0(DepthRegionTraversal.java:68)
        	at java.base/java.util.ArrayList.forEach(ArrayList.java:1511)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseInternal(DepthRegionTraversal.java:68)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.lambda$traverseInternal$0(DepthRegionTraversal.java:68)
        	at java.base/java.util.ArrayList.forEach(ArrayList.java:1511)
        	at java.base/java.util.Collections$UnmodifiableCollection.forEach(Collections.java:1092)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseInternal(DepthRegionTraversal.java:68)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.lambda$traverseInternal$0(DepthRegionTraversal.java:68)
        	at java.base/java.util.ArrayList.forEach(ArrayList.java:1511)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseInternal(DepthRegionTraversal.java:68)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.lambda$traverseInternal$0(DepthRegionTraversal.java:68)
        	at java.base/java.util.ArrayList.forEach(ArrayList.java:1511)
        	at java.base/java.util.Collections$UnmodifiableCollection.forEach(Collections.java:1092)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseInternal(DepthRegionTraversal.java:68)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.lambda$traverseInternal$0(DepthRegionTraversal.java:68)
        	at java.base/java.util.ArrayList.forEach(ArrayList.java:1511)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseInternal(DepthRegionTraversal.java:68)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.lambda$traverseInternal$0(DepthRegionTraversal.java:68)
        	at java.base/java.util.ArrayList.forEach(ArrayList.java:1511)
        	at java.base/java.util.Collections$UnmodifiableCollection.forEach(Collections.java:1092)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseInternal(DepthRegionTraversal.java:68)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.lambda$traverseInternal$0(DepthRegionTraversal.java:68)
        	at java.base/java.util.ArrayList.forEach(ArrayList.java:1511)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseInternal(DepthRegionTraversal.java:68)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.lambda$traverseInternal$0(DepthRegionTraversal.java:68)
        	at java.base/java.util.ArrayList.forEach(ArrayList.java:1511)
        	at java.base/java.util.Collections$UnmodifiableCollection.forEach(Collections.java:1092)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseInternal(DepthRegionTraversal.java:68)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.lambda$traverseInternal$0(DepthRegionTraversal.java:68)
        	at java.base/java.util.ArrayList.forEach(ArrayList.java:1511)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseInternal(DepthRegionTraversal.java:68)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.lambda$traverseInternal$0(DepthRegionTraversal.java:68)
        	at java.base/java.util.ArrayList.forEach(ArrayList.java:1511)
        	at java.base/java.util.Collections$UnmodifiableCollection.forEach(Collections.java:1092)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseInternal(DepthRegionTraversal.java:68)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.lambda$traverseInternal$0(DepthRegionTraversal.java:68)
        	at java.base/java.util.ArrayList.forEach(ArrayList.java:1511)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseInternal(DepthRegionTraversal.java:68)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.lambda$traverseInternal$0(DepthRegionTraversal.java:68)
        	at java.base/java.util.ArrayList.forEach(ArrayList.java:1511)
        	at java.base/java.util.Collections$UnmodifiableCollection.forEach(Collections.java:1092)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseInternal(DepthRegionTraversal.java:68)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.lambda$traverseInternal$0(DepthRegionTraversal.java:68)
        	at java.base/java.util.ArrayList.forEach(ArrayList.java:1511)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseInternal(DepthRegionTraversal.java:68)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.lambda$traverseInternal$0(DepthRegionTraversal.java:68)
        	at java.base/java.util.ArrayList.forEach(ArrayList.java:1511)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseInternal(DepthRegionTraversal.java:68)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.lambda$traverseInternal$0(DepthRegionTraversal.java:68)
        	at java.base/java.util.ArrayList.forEach(ArrayList.java:1511)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseInternal(DepthRegionTraversal.java:68)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverse(DepthRegionTraversal.java:19)
        	at jadx.core.dex.visitors.regions.TernaryMod.process(TernaryMod.java:35)
        	at jadx.core.dex.visitors.regions.IfRegionVisitor.process(IfRegionVisitor.java:34)
        	at jadx.core.dex.visitors.regions.IfRegionVisitor.visit(IfRegionVisitor.java:30)
        */
    public static final void processUtf16Chars(byte[] r24, int r25, int r26, kotlin.jvm.functions.Function1<? super java.lang.Character, kotlin.Unit> r27) {
        /*
            Method dump skipped, instructions count: 785
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: okio.Utf8.processUtf16Chars(byte[], int, int, kotlin.jvm.functions.Function1):void");
    }

    public static final void processUtf8Bytes(String processUtf8Bytes, int beginIndex, int endIndex, Function1<? super Byte, Unit> yield) {
        char charAt;
        Intrinsics.checkNotNullParameter(processUtf8Bytes, "$this$processUtf8Bytes");
        Intrinsics.checkNotNullParameter(yield, "yield");
        int i = beginIndex;
        while (i < endIndex) {
            char charAt2 = processUtf8Bytes.charAt(i);
            if (Intrinsics.compare((int) charAt2, 128) < 0) {
                yield.invoke(Byte.valueOf((byte) charAt2));
                i++;
                while (i < endIndex && Intrinsics.compare((int) processUtf8Bytes.charAt(i), 128) < 0) {
                    yield.invoke(Byte.valueOf((byte) processUtf8Bytes.charAt(i)));
                    i++;
                }
            } else if (Intrinsics.compare((int) charAt2, 2048) < 0) {
                yield.invoke(Byte.valueOf((byte) ((charAt2 >> 6) | 192)));
                yield.invoke(Byte.valueOf((byte) (128 | (charAt2 & '?'))));
                i++;
            } else if (55296 > charAt2 || 57343 < charAt2) {
                yield.invoke(Byte.valueOf((byte) ((charAt2 >> '\f') | 224)));
                yield.invoke(Byte.valueOf((byte) (((charAt2 >> 6) & 63) | 128)));
                yield.invoke(Byte.valueOf((byte) (128 | (charAt2 & '?'))));
                i++;
            } else if (Intrinsics.compare((int) charAt2, 56319) > 0 || endIndex <= i + 1 || 56320 > (charAt = processUtf8Bytes.charAt(i + 1)) || 57343 < charAt) {
                yield.invoke(Byte.valueOf((byte) REPLACEMENT_BYTE));
                i++;
            } else {
                int charAt3 = ((charAt2 << '\n') + processUtf8Bytes.charAt(i + 1)) - 56613888;
                yield.invoke(Byte.valueOf((byte) ((charAt3 >> 18) | 240)));
                yield.invoke(Byte.valueOf((byte) (((charAt3 >> 12) & 63) | 128)));
                yield.invoke(Byte.valueOf((byte) ((63 & (charAt3 >> 6)) | 128)));
                yield.invoke(Byte.valueOf((byte) (128 | (charAt3 & 63))));
                i += 2;
            }
        }
    }

    public static final void processUtf8CodePoints(byte[] processUtf8CodePoints, int beginIndex, int endIndex, Function1<? super Integer, Unit> yield) {
        int i;
        int i2;
        int i3;
        Intrinsics.checkNotNullParameter(processUtf8CodePoints, "$this$processUtf8CodePoints");
        Intrinsics.checkNotNullParameter(yield, "yield");
        int i4 = beginIndex;
        while (i4 < endIndex) {
            byte b = processUtf8CodePoints[i4];
            if (b >= 0) {
                yield.invoke(Integer.valueOf(b));
                i4++;
                while (i4 < endIndex && processUtf8CodePoints[i4] >= 0) {
                    yield.invoke(Integer.valueOf(processUtf8CodePoints[i4]));
                    i4++;
                }
            } else if ((b >> 5) == -2) {
                if (endIndex <= i4 + 1) {
                    yield.invoke(Integer.valueOf((int) REPLACEMENT_CODE_POINT));
                    Unit unit = Unit.INSTANCE;
                    i = 1;
                } else {
                    byte b2 = processUtf8CodePoints[i4];
                    byte b3 = processUtf8CodePoints[i4 + 1];
                    if ((b3 & 192) == 128) {
                        int i5 = (b3 ^ ByteCompanionObject.MIN_VALUE) ^ (b2 << 6);
                        yield.invoke(Integer.valueOf(i5 < 128 ? REPLACEMENT_CODE_POINT : i5));
                        Unit unit2 = Unit.INSTANCE;
                        i = 2;
                    } else {
                        yield.invoke(Integer.valueOf((int) REPLACEMENT_CODE_POINT));
                        Unit unit3 = Unit.INSTANCE;
                        i = 1;
                    }
                }
                i4 += i;
            } else if ((b >> 4) == -2) {
                if (endIndex <= i4 + 2) {
                    yield.invoke(Integer.valueOf((int) REPLACEMENT_CODE_POINT));
                    Unit unit4 = Unit.INSTANCE;
                    if (endIndex > i4 + 1) {
                        if ((192 & processUtf8CodePoints[i4 + 1]) == 128) {
                            i2 = 2;
                        }
                    }
                    i2 = 1;
                } else {
                    byte b4 = processUtf8CodePoints[i4];
                    byte b5 = processUtf8CodePoints[i4 + 1];
                    if ((b5 & 192) == 128) {
                        byte b6 = processUtf8CodePoints[i4 + 2];
                        if ((b6 & 192) == 128) {
                            int i6 = (((-123008) ^ b6) ^ (b5 << 6)) ^ (b4 << 12);
                            yield.invoke(Integer.valueOf(i6 < 2048 ? REPLACEMENT_CODE_POINT : (55296 <= i6 && 57343 >= i6) ? REPLACEMENT_CODE_POINT : i6));
                            Unit unit5 = Unit.INSTANCE;
                            i2 = 3;
                        } else {
                            yield.invoke(Integer.valueOf((int) REPLACEMENT_CODE_POINT));
                            Unit unit6 = Unit.INSTANCE;
                            i2 = 2;
                        }
                    } else {
                        yield.invoke(Integer.valueOf((int) REPLACEMENT_CODE_POINT));
                        Unit unit7 = Unit.INSTANCE;
                        i2 = 1;
                    }
                }
                i4 += i2;
            } else if ((b >> 3) == -2) {
                if (endIndex <= i4 + 3) {
                    yield.invoke(Integer.valueOf((int) REPLACEMENT_CODE_POINT));
                    Unit unit8 = Unit.INSTANCE;
                    if (endIndex > i4 + 1) {
                        if ((192 & processUtf8CodePoints[i4 + 1]) == 128) {
                            if (endIndex > i4 + 2) {
                                if ((192 & processUtf8CodePoints[i4 + 2]) == 128) {
                                    i3 = 3;
                                }
                            }
                            i3 = 2;
                        }
                    }
                    i3 = 1;
                } else {
                    byte b7 = processUtf8CodePoints[i4];
                    byte b8 = processUtf8CodePoints[i4 + 1];
                    if ((b8 & 192) == 128) {
                        byte b9 = processUtf8CodePoints[i4 + 2];
                        if ((b9 & 192) == 128) {
                            byte b10 = processUtf8CodePoints[i4 + 3];
                            if ((b10 & 192) == 128) {
                                int i7 = (((3678080 ^ b10) ^ (b9 << 6)) ^ (b8 << 12)) ^ (b7 << 18);
                                yield.invoke(Integer.valueOf(i7 > 1114111 ? REPLACEMENT_CODE_POINT : (55296 <= i7 && 57343 >= i7) ? REPLACEMENT_CODE_POINT : i7 < 65536 ? REPLACEMENT_CODE_POINT : i7));
                                Unit unit9 = Unit.INSTANCE;
                                i3 = 4;
                            } else {
                                yield.invoke(Integer.valueOf((int) REPLACEMENT_CODE_POINT));
                                Unit unit10 = Unit.INSTANCE;
                                i3 = 3;
                            }
                        } else {
                            yield.invoke(Integer.valueOf((int) REPLACEMENT_CODE_POINT));
                            Unit unit11 = Unit.INSTANCE;
                            i3 = 2;
                        }
                    } else {
                        yield.invoke(Integer.valueOf((int) REPLACEMENT_CODE_POINT));
                        Unit unit12 = Unit.INSTANCE;
                        i3 = 1;
                    }
                }
                i4 += i3;
            } else {
                yield.invoke(Integer.valueOf((int) REPLACEMENT_CODE_POINT));
                i4++;
            }
        }
    }

    public static final long size(String str) {
        return size$default(str, 0, 0, 3, null);
    }

    public static final long size(String str, int i) {
        return size$default(str, i, 0, 2, null);
    }

    public static final long size(String utf8Size, int beginIndex, int endIndex) {
        Intrinsics.checkNotNullParameter(utf8Size, "$this$utf8Size");
        if (!(beginIndex >= 0)) {
            throw new IllegalArgumentException(("beginIndex < 0: " + beginIndex).toString());
        }
        if (!(endIndex >= beginIndex)) {
            throw new IllegalArgumentException(("endIndex < beginIndex: " + endIndex + " < " + beginIndex).toString());
        }
        if (!(endIndex <= utf8Size.length())) {
            throw new IllegalArgumentException(("endIndex > string.length: " + endIndex + " > " + utf8Size.length()).toString());
        }
        long j = 0;
        int i = beginIndex;
        while (i < endIndex) {
            char charAt = utf8Size.charAt(i);
            if (charAt < 128) {
                j++;
                i++;
            } else if (charAt < 2048) {
                j += 2;
                i++;
            } else if (charAt < 55296 || charAt > 57343) {
                j += 3;
                i++;
            } else {
                char charAt2 = i + 1 < endIndex ? utf8Size.charAt(i + 1) : (char) 0;
                if (charAt > 56319 || charAt2 < 56320 || charAt2 > 57343) {
                    j++;
                    i++;
                } else {
                    j += 4;
                    i += 2;
                }
            }
        }
        return j;
    }

    public static /* synthetic */ long size$default(String str, int i, int i2, int i3, Object obj) {
        if ((i3 & 1) != 0) {
            i = 0;
        }
        if ((i3 & 2) != 0) {
            i2 = str.length();
        }
        return size(str, i, i2);
    }
}
