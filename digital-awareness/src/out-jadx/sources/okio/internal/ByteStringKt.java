package okio.internal;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import java.util.Arrays;
import kotlin.Metadata;
import kotlin.UByte;
import kotlin.collections.ArraysKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import mt.Log229316;
import mt.Log5ECF72;
import mt.LogE84000;
import okhttp3.HttpUrl;
import okio.Base64;
import okio.Buffer;
import okio.ByteString;
import okio.Platform;
import okio.Util;

/* compiled from: 01F3.java */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000P\n\u0000\n\u0002\u0010\u0019\n\u0002\b\u0003\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0012\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\f\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0007\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0005\n\u0002\b\u0018\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a\u0018\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\u0005H\u0002\u001a\u0011\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\u0007H\u0080\b\u001a\u0010\u0010\f\u001a\u00020\u00052\u0006\u0010\r\u001a\u00020\u000eH\u0002\u001a\r\u0010\u000f\u001a\u00020\u0010*\u00020\nH\u0080\b\u001a\r\u0010\u0011\u001a\u00020\u0010*\u00020\nH\u0080\b\u001a\u0015\u0010\u0012\u001a\u00020\u0005*\u00020\n2\u0006\u0010\u0013\u001a\u00020\nH\u0080\b\u001a\u000f\u0010\u0014\u001a\u0004\u0018\u00010\n*\u00020\u0010H\u0080\b\u001a\r\u0010\u0015\u001a\u00020\n*\u00020\u0010H\u0080\b\u001a\r\u0010\u0016\u001a\u00020\n*\u00020\u0010H\u0080\b\u001a\u0015\u0010\u0017\u001a\u00020\u0018*\u00020\n2\u0006\u0010\u0019\u001a\u00020\u0007H\u0080\b\u001a\u0015\u0010\u0017\u001a\u00020\u0018*\u00020\n2\u0006\u0010\u0019\u001a\u00020\nH\u0080\b\u001a\u0017\u0010\u001a\u001a\u00020\u0018*\u00020\n2\b\u0010\u0013\u001a\u0004\u0018\u00010\u001bH\u0080\b\u001a\u0015\u0010\u001c\u001a\u00020\u001d*\u00020\n2\u0006\u0010\u001e\u001a\u00020\u0005H\u0080\b\u001a\r\u0010\u001f\u001a\u00020\u0005*\u00020\nH\u0080\b\u001a\r\u0010 \u001a\u00020\u0005*\u00020\nH\u0080\b\u001a\r\u0010!\u001a\u00020\u0010*\u00020\nH\u0080\b\u001a\u001d\u0010\"\u001a\u00020\u0005*\u00020\n2\u0006\u0010\u0013\u001a\u00020\u00072\u0006\u0010#\u001a\u00020\u0005H\u0080\b\u001a\r\u0010$\u001a\u00020\u0007*\u00020\nH\u0080\b\u001a\u001d\u0010%\u001a\u00020\u0005*\u00020\n2\u0006\u0010\u0013\u001a\u00020\u00072\u0006\u0010#\u001a\u00020\u0005H\u0080\b\u001a\u001d\u0010%\u001a\u00020\u0005*\u00020\n2\u0006\u0010\u0013\u001a\u00020\n2\u0006\u0010#\u001a\u00020\u0005H\u0080\b\u001a-\u0010&\u001a\u00020\u0018*\u00020\n2\u0006\u0010'\u001a\u00020\u00052\u0006\u0010\u0013\u001a\u00020\u00072\u0006\u0010(\u001a\u00020\u00052\u0006\u0010)\u001a\u00020\u0005H\u0080\b\u001a-\u0010&\u001a\u00020\u0018*\u00020\n2\u0006\u0010'\u001a\u00020\u00052\u0006\u0010\u0013\u001a\u00020\n2\u0006\u0010(\u001a\u00020\u00052\u0006\u0010)\u001a\u00020\u0005H\u0080\b\u001a\u0015\u0010*\u001a\u00020\u0018*\u00020\n2\u0006\u0010+\u001a\u00020\u0007H\u0080\b\u001a\u0015\u0010*\u001a\u00020\u0018*\u00020\n2\u0006\u0010+\u001a\u00020\nH\u0080\b\u001a\u001d\u0010,\u001a\u00020\n*\u00020\n2\u0006\u0010-\u001a\u00020\u00052\u0006\u0010.\u001a\u00020\u0005H\u0080\b\u001a\r\u0010/\u001a\u00020\n*\u00020\nH\u0080\b\u001a\r\u00100\u001a\u00020\n*\u00020\nH\u0080\b\u001a\r\u00101\u001a\u00020\u0007*\u00020\nH\u0080\b\u001a\u001d\u00102\u001a\u00020\n*\u00020\u00072\u0006\u0010'\u001a\u00020\u00052\u0006\u0010)\u001a\u00020\u0005H\u0080\b\u001a\r\u00103\u001a\u00020\u0010*\u00020\nH\u0080\b\u001a\r\u00104\u001a\u00020\u0010*\u00020\nH\u0080\b\u001a$\u00105\u001a\u000206*\u00020\n2\u0006\u00107\u001a\u0002082\u0006\u0010'\u001a\u00020\u00052\u0006\u0010)\u001a\u00020\u0005H\u0000\"\u0014\u0010\u0000\u001a\u00020\u0001X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0002\u0010\u0003¨\u00069"}, d2 = {"HEX_DIGIT_CHARS", HttpUrl.FRAGMENT_ENCODE_SET, "getHEX_DIGIT_CHARS", "()[C", "codePointIndexToCharIndex", HttpUrl.FRAGMENT_ENCODE_SET, "s", HttpUrl.FRAGMENT_ENCODE_SET, "codePointCount", "commonOf", "Lokio/ByteString;", "data", "decodeHexDigit", "c", HttpUrl.FRAGMENT_ENCODE_SET, "commonBase64", HttpUrl.FRAGMENT_ENCODE_SET, "commonBase64Url", "commonCompareTo", "other", "commonDecodeBase64", "commonDecodeHex", "commonEncodeUtf8", "commonEndsWith", HttpUrl.FRAGMENT_ENCODE_SET, "suffix", "commonEquals", HttpUrl.FRAGMENT_ENCODE_SET, "commonGetByte", HttpUrl.FRAGMENT_ENCODE_SET, "pos", "commonGetSize", "commonHashCode", "commonHex", "commonIndexOf", "fromIndex", "commonInternalArray", "commonLastIndexOf", "commonRangeEquals", TypedValues.CycleType.S_WAVE_OFFSET, "otherOffset", "byteCount", "commonStartsWith", "prefix", "commonSubstring", "beginIndex", "endIndex", "commonToAsciiLowercase", "commonToAsciiUppercase", "commonToByteArray", "commonToByteString", "commonToString", "commonUtf8", "commonWrite", HttpUrl.FRAGMENT_ENCODE_SET, "buffer", "Lokio/Buffer;", "okio"}, k = 2, mv = {1, 4, 0})
/* loaded from: classes.dex */
public final class ByteStringKt {
    private static final char[] HEX_DIGIT_CHARS = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    /* JADX WARN: Code restructure failed: missing block: B:111:0x012d, code lost:
    
        if (31 >= 65533(0xfffd, float:9.1831E-41)) goto L892;
     */
    /* JADX WARN: Code restructure failed: missing block: B:112:0x013b, code lost:
    
        r16 = true;
     */
    /* JADX WARN: Code restructure failed: missing block: B:119:0x0139, code lost:
    
        if (159(0x9f, float:2.23E-43) < 65533(0xfffd, float:9.1831E-41)) goto L893;
     */
    /* JADX WARN: Code restructure failed: missing block: B:142:0x0185, code lost:
    
        if (31 >= 65533(0xfffd, float:9.1831E-41)) goto L922;
     */
    /* JADX WARN: Code restructure failed: missing block: B:143:0x0193, code lost:
    
        r16 = true;
     */
    /* JADX WARN: Code restructure failed: missing block: B:150:0x0191, code lost:
    
        if (159(0x9f, float:2.23E-43) < 65533(0xfffd, float:9.1831E-41)) goto L923;
     */
    /* JADX WARN: Code restructure failed: missing block: B:171:0x01c8, code lost:
    
        if (31 >= r3) goto L951;
     */
    /* JADX WARN: Code restructure failed: missing block: B:172:0x01d6, code lost:
    
        r16 = true;
     */
    /* JADX WARN: Code restructure failed: missing block: B:179:0x01d4, code lost:
    
        if (159 < r3) goto L952;
     */
    /* JADX WARN: Code restructure failed: missing block: B:225:0x025b, code lost:
    
        if (((192 & r29[r8 + 1]) == 128) == false) goto L1004;
     */
    /* JADX WARN: Code restructure failed: missing block: B:247:0x029b, code lost:
    
        if (31 >= 65533(0xfffd, float:9.1831E-41)) goto L1029;
     */
    /* JADX WARN: Code restructure failed: missing block: B:248:0x02a9, code lost:
    
        r16 = true;
     */
    /* JADX WARN: Code restructure failed: missing block: B:255:0x02a7, code lost:
    
        if (159(0x9f, float:2.23E-43) < 65533(0xfffd, float:9.1831E-41)) goto L1030;
     */
    /* JADX WARN: Code restructure failed: missing block: B:280:0x02fb, code lost:
    
        if (31 >= 65533(0xfffd, float:9.1831E-41)) goto L1063;
     */
    /* JADX WARN: Code restructure failed: missing block: B:281:0x0309, code lost:
    
        r16 = true;
     */
    /* JADX WARN: Code restructure failed: missing block: B:288:0x0307, code lost:
    
        if (159(0x9f, float:2.23E-43) < 65533(0xfffd, float:9.1831E-41)) goto L1064;
     */
    /* JADX WARN: Code restructure failed: missing block: B:311:0x0357, code lost:
    
        if (31 >= 65533(0xfffd, float:9.1831E-41)) goto L1094;
     */
    /* JADX WARN: Code restructure failed: missing block: B:312:0x0365, code lost:
    
        r16 = true;
     */
    /* JADX WARN: Code restructure failed: missing block: B:319:0x0363, code lost:
    
        if (159(0x9f, float:2.23E-43) < 65533(0xfffd, float:9.1831E-41)) goto L1095;
     */
    /* JADX WARN: Code restructure failed: missing block: B:346:0x03a7, code lost:
    
        if (31 >= 65533(0xfffd, float:9.1831E-41)) goto L1128;
     */
    /* JADX WARN: Code restructure failed: missing block: B:347:0x03b5, code lost:
    
        r16 = true;
     */
    /* JADX WARN: Code restructure failed: missing block: B:354:0x03b3, code lost:
    
        if (159(0x9f, float:2.23E-43) < 65533(0xfffd, float:9.1831E-41)) goto L1129;
     */
    /* JADX WARN: Code restructure failed: missing block: B:359:0x03c2, code lost:
    
        if (65533(0xfffd, float:9.1831E-41) < 65536(0x10000, float:9.18355E-41)) goto L1163;
     */
    /* JADX WARN: Code restructure failed: missing block: B:360:0x03ff, code lost:
    
        r1 = r1 + r17;
     */
    /* JADX WARN: Code restructure failed: missing block: B:361:0x03fd, code lost:
    
        r17 = 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:375:0x03e0, code lost:
    
        if (31 >= r4) goto L1155;
     */
    /* JADX WARN: Code restructure failed: missing block: B:376:0x03ee, code lost:
    
        r16 = true;
     */
    /* JADX WARN: Code restructure failed: missing block: B:383:0x03ec, code lost:
    
        if (159 < r4) goto L1156;
     */
    /* JADX WARN: Code restructure failed: missing block: B:388:0x03fb, code lost:
    
        if (r4 < 65536) goto L1163;
     */
    /* JADX WARN: Code restructure failed: missing block: B:458:0x04cc, code lost:
    
        if (31 >= 65533(0xfffd, float:9.1831E-41)) goto L1242;
     */
    /* JADX WARN: Code restructure failed: missing block: B:459:0x04da, code lost:
    
        r16 = true;
     */
    /* JADX WARN: Code restructure failed: missing block: B:466:0x04d8, code lost:
    
        if (159(0x9f, float:2.23E-43) < 65533(0xfffd, float:9.1831E-41)) goto L1243;
     */
    /* JADX WARN: Code restructure failed: missing block: B:491:0x052a, code lost:
    
        if (31 >= 65533(0xfffd, float:9.1831E-41)) goto L1276;
     */
    /* JADX WARN: Code restructure failed: missing block: B:492:0x0538, code lost:
    
        r16 = true;
     */
    /* JADX WARN: Code restructure failed: missing block: B:499:0x0536, code lost:
    
        if (159(0x9f, float:2.23E-43) < 65533(0xfffd, float:9.1831E-41)) goto L1277;
     */
    /* JADX WARN: Code restructure failed: missing block: B:524:0x058b, code lost:
    
        if (31 >= 65533(0xfffd, float:9.1831E-41)) goto L1311;
     */
    /* JADX WARN: Code restructure failed: missing block: B:525:0x0599, code lost:
    
        r16 = true;
     */
    /* JADX WARN: Code restructure failed: missing block: B:532:0x0597, code lost:
    
        if (159(0x9f, float:2.23E-43) < 65533(0xfffd, float:9.1831E-41)) goto L1312;
     */
    /* JADX WARN: Code restructure failed: missing block: B:555:0x05ec, code lost:
    
        if (31 >= 65533(0xfffd, float:9.1831E-41)) goto L1341;
     */
    /* JADX WARN: Code restructure failed: missing block: B:556:0x05fa, code lost:
    
        r16 = true;
     */
    /* JADX WARN: Code restructure failed: missing block: B:563:0x05f8, code lost:
    
        if (159(0x9f, float:2.23E-43) < 65533(0xfffd, float:9.1831E-41)) goto L1342;
     */
    /* JADX WARN: Code restructure failed: missing block: B:590:0x063e, code lost:
    
        if (31 >= 65533(0xfffd, float:9.1831E-41)) goto L1375;
     */
    /* JADX WARN: Code restructure failed: missing block: B:591:0x064c, code lost:
    
        r16 = true;
     */
    /* JADX WARN: Code restructure failed: missing block: B:598:0x064a, code lost:
    
        if (159(0x9f, float:2.23E-43) < 65533(0xfffd, float:9.1831E-41)) goto L1376;
     */
    /* JADX WARN: Code restructure failed: missing block: B:603:0x0659, code lost:
    
        if (65533(0xfffd, float:9.1831E-41) < 65536(0x10000, float:9.18355E-41)) goto L1383;
     */
    /* JADX WARN: Code restructure failed: missing block: B:604:0x065b, code lost:
    
        r17 = 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:605:0x065d, code lost:
    
        r1 = r1 + r17;
     */
    /* JADX WARN: Code restructure failed: missing block: B:621:0x0682, code lost:
    
        if (31 >= 65533(0xfffd, float:9.1831E-41)) goto L1405;
     */
    /* JADX WARN: Code restructure failed: missing block: B:622:0x0690, code lost:
    
        r16 = true;
     */
    /* JADX WARN: Code restructure failed: missing block: B:629:0x068e, code lost:
    
        if (159(0x9f, float:2.23E-43) < 65533(0xfffd, float:9.1831E-41)) goto L1406;
     */
    /* JADX WARN: Code restructure failed: missing block: B:634:0x069d, code lost:
    
        if (65533(0xfffd, float:9.1831E-41) < 65536(0x10000, float:9.18355E-41)) goto L1383;
     */
    /* JADX WARN: Code restructure failed: missing block: B:648:0x06bb, code lost:
    
        if (31 >= r3) goto L1432;
     */
    /* JADX WARN: Code restructure failed: missing block: B:649:0x06c9, code lost:
    
        r16 = true;
     */
    /* JADX WARN: Code restructure failed: missing block: B:656:0x06c7, code lost:
    
        if (159 < r3) goto L1433;
     */
    /* JADX WARN: Code restructure failed: missing block: B:661:0x06d6, code lost:
    
        if (r3 < 65536) goto L1383;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static final int codePointIndexToCharIndex(byte[] r29, int r30) {
        /*
            Method dump skipped, instructions count: 1827
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: okio.internal.ByteStringKt.codePointIndexToCharIndex(byte[], int):int");
    }

    /* JADX WARN: Failed to parse debug info
    jadx.core.utils.exceptions.JadxRuntimeException: Failed to parse type string: null
    	at jadx.core.dex.instructions.args.ArgType.parse(ArgType.java:736)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.getVarType(DebugInfoAttachVisitor.java:139)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.attachDebugInfo(DebugInfoAttachVisitor.java:94)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.processDebugInfo(DebugInfoAttachVisitor.java:51)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.visit(DebugInfoAttachVisitor.java:41)
     */
    public static final String commonBase64(ByteString commonBase64) {
        Intrinsics.checkNotNullParameter(commonBase64, "$this$commonBase64");
        String encodeBase64$default = Base64.encodeBase64$default(commonBase64.getData(), null, 1, null);
        Log5ECF72.a(encodeBase64$default);
        LogE84000.a(encodeBase64$default);
        Log229316.a(encodeBase64$default);
        return encodeBase64$default;
    }

    /* JADX WARN: Failed to parse debug info
    jadx.core.utils.exceptions.JadxRuntimeException: Failed to parse type string: null
    	at jadx.core.dex.instructions.args.ArgType.parse(ArgType.java:736)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.getVarType(DebugInfoAttachVisitor.java:139)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.attachDebugInfo(DebugInfoAttachVisitor.java:94)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.processDebugInfo(DebugInfoAttachVisitor.java:51)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.visit(DebugInfoAttachVisitor.java:41)
     */
    public static final String commonBase64Url(ByteString commonBase64Url) {
        Intrinsics.checkNotNullParameter(commonBase64Url, "$this$commonBase64Url");
        String encodeBase64 = Base64.encodeBase64(commonBase64Url.getData(), Base64.getBASE64_URL_SAFE());
        Log5ECF72.a(encodeBase64);
        LogE84000.a(encodeBase64);
        Log229316.a(encodeBase64);
        return encodeBase64;
    }

    public static final int commonCompareTo(ByteString commonCompareTo, ByteString other) {
        Intrinsics.checkNotNullParameter(commonCompareTo, "$this$commonCompareTo");
        Intrinsics.checkNotNullParameter(other, "other");
        int size = commonCompareTo.size();
        int size2 = other.size();
        int min = Math.min(size, size2);
        for (int i = 0; i < min; i++) {
            int i2 = commonCompareTo.getByte(i) & UByte.MAX_VALUE;
            int i3 = other.getByte(i) & UByte.MAX_VALUE;
            if (i2 != i3) {
                return i2 < i3 ? -1 : 1;
            }
        }
        if (size == size2) {
            return 0;
        }
        return size < size2 ? -1 : 1;
    }

    public static final ByteString commonDecodeBase64(String commonDecodeBase64) {
        Intrinsics.checkNotNullParameter(commonDecodeBase64, "$this$commonDecodeBase64");
        byte[] decodeBase64ToArray = Base64.decodeBase64ToArray(commonDecodeBase64);
        if (decodeBase64ToArray != null) {
            return new ByteString(decodeBase64ToArray);
        }
        return null;
    }

    public static final ByteString commonDecodeHex(String commonDecodeHex) {
        Intrinsics.checkNotNullParameter(commonDecodeHex, "$this$commonDecodeHex");
        if (!(commonDecodeHex.length() % 2 == 0)) {
            throw new IllegalArgumentException(("Unexpected hex string: " + commonDecodeHex).toString());
        }
        byte[] bArr = new byte[commonDecodeHex.length() / 2];
        int length = bArr.length;
        for (int i = 0; i < length; i++) {
            bArr[i] = (byte) ((decodeHexDigit(commonDecodeHex.charAt(i * 2)) << 4) + decodeHexDigit(commonDecodeHex.charAt((i * 2) + 1)));
        }
        return new ByteString(bArr);
    }

    public static final ByteString commonEncodeUtf8(String commonEncodeUtf8) {
        Intrinsics.checkNotNullParameter(commonEncodeUtf8, "$this$commonEncodeUtf8");
        ByteString byteString = new ByteString(Platform.asUtf8ToByteArray(commonEncodeUtf8));
        byteString.setUtf8$okio(commonEncodeUtf8);
        return byteString;
    }

    public static final boolean commonEndsWith(ByteString commonEndsWith, ByteString suffix) {
        Intrinsics.checkNotNullParameter(commonEndsWith, "$this$commonEndsWith");
        Intrinsics.checkNotNullParameter(suffix, "suffix");
        return commonEndsWith.rangeEquals(commonEndsWith.size() - suffix.size(), suffix, 0, suffix.size());
    }

    public static final boolean commonEndsWith(ByteString commonEndsWith, byte[] suffix) {
        Intrinsics.checkNotNullParameter(commonEndsWith, "$this$commonEndsWith");
        Intrinsics.checkNotNullParameter(suffix, "suffix");
        return commonEndsWith.rangeEquals(commonEndsWith.size() - suffix.length, suffix, 0, suffix.length);
    }

    public static final boolean commonEquals(ByteString commonEquals, Object other) {
        Intrinsics.checkNotNullParameter(commonEquals, "$this$commonEquals");
        if (other == commonEquals) {
            return true;
        }
        if (other instanceof ByteString) {
            return ((ByteString) other).size() == commonEquals.getData().length && ((ByteString) other).rangeEquals(0, commonEquals.getData(), 0, commonEquals.getData().length);
        }
        return false;
    }

    public static final byte commonGetByte(ByteString commonGetByte, int pos) {
        Intrinsics.checkNotNullParameter(commonGetByte, "$this$commonGetByte");
        return commonGetByte.getData()[pos];
    }

    public static final int commonGetSize(ByteString commonGetSize) {
        Intrinsics.checkNotNullParameter(commonGetSize, "$this$commonGetSize");
        return commonGetSize.getData().length;
    }

    public static final int commonHashCode(ByteString commonHashCode) {
        Intrinsics.checkNotNullParameter(commonHashCode, "$this$commonHashCode");
        int hashCode = commonHashCode.getHashCode();
        if (hashCode != 0) {
            return hashCode;
        }
        int hashCode2 = Arrays.hashCode(commonHashCode.getData());
        commonHashCode.setHashCode$okio(hashCode2);
        return hashCode2;
    }

    /* JADX WARN: Failed to parse debug info
    jadx.core.utils.exceptions.JadxRuntimeException: Failed to parse type string: null
    	at jadx.core.dex.instructions.args.ArgType.parse(ArgType.java:736)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.getVarType(DebugInfoAttachVisitor.java:139)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.attachDebugInfo(DebugInfoAttachVisitor.java:94)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.processDebugInfo(DebugInfoAttachVisitor.java:51)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.visit(DebugInfoAttachVisitor.java:41)
     */
    public static final String commonHex(ByteString commonHex) {
        Intrinsics.checkNotNullParameter(commonHex, "$this$commonHex");
        char[] cArr = new char[commonHex.getData().length * 2];
        int i = 0;
        for (byte b : commonHex.getData()) {
            int i2 = i + 1;
            cArr[i] = getHEX_DIGIT_CHARS()[(b >> 4) & 15];
            i = i2 + 1;
            cArr[i2] = getHEX_DIGIT_CHARS()[15 & b];
        }
        String str = new String(cArr);
        Log5ECF72.a(str);
        LogE84000.a(str);
        Log229316.a(str);
        return str;
    }

    public static final int commonIndexOf(ByteString commonIndexOf, byte[] other, int fromIndex) {
        Intrinsics.checkNotNullParameter(commonIndexOf, "$this$commonIndexOf");
        Intrinsics.checkNotNullParameter(other, "other");
        int length = commonIndexOf.getData().length - other.length;
        int max = Math.max(fromIndex, 0);
        if (max > length) {
            return -1;
        }
        while (!Util.arrayRangeEquals(commonIndexOf.getData(), max, other, 0, other.length)) {
            if (max == length) {
                return -1;
            }
            max++;
        }
        return max;
    }

    public static final byte[] commonInternalArray(ByteString commonInternalArray) {
        Intrinsics.checkNotNullParameter(commonInternalArray, "$this$commonInternalArray");
        return commonInternalArray.getData();
    }

    public static final int commonLastIndexOf(ByteString commonLastIndexOf, ByteString other, int fromIndex) {
        Intrinsics.checkNotNullParameter(commonLastIndexOf, "$this$commonLastIndexOf");
        Intrinsics.checkNotNullParameter(other, "other");
        return commonLastIndexOf.lastIndexOf(other.internalArray$okio(), fromIndex);
    }

    public static final int commonLastIndexOf(ByteString commonLastIndexOf, byte[] other, int fromIndex) {
        Intrinsics.checkNotNullParameter(commonLastIndexOf, "$this$commonLastIndexOf");
        Intrinsics.checkNotNullParameter(other, "other");
        for (int min = Math.min(fromIndex, commonLastIndexOf.getData().length - other.length); min >= 0; min--) {
            if (Util.arrayRangeEquals(commonLastIndexOf.getData(), min, other, 0, other.length)) {
                return min;
            }
        }
        return -1;
    }

    public static final ByteString commonOf(byte[] data) {
        Intrinsics.checkNotNullParameter(data, "data");
        byte[] copyOf = Arrays.copyOf(data, data.length);
        Intrinsics.checkNotNullExpressionValue(copyOf, "java.util.Arrays.copyOf(this, size)");
        return new ByteString(copyOf);
    }

    public static final boolean commonRangeEquals(ByteString commonRangeEquals, int offset, ByteString other, int otherOffset, int byteCount) {
        Intrinsics.checkNotNullParameter(commonRangeEquals, "$this$commonRangeEquals");
        Intrinsics.checkNotNullParameter(other, "other");
        return other.rangeEquals(otherOffset, commonRangeEquals.getData(), offset, byteCount);
    }

    public static final boolean commonRangeEquals(ByteString commonRangeEquals, int offset, byte[] other, int otherOffset, int byteCount) {
        Intrinsics.checkNotNullParameter(commonRangeEquals, "$this$commonRangeEquals");
        Intrinsics.checkNotNullParameter(other, "other");
        return offset >= 0 && offset <= commonRangeEquals.getData().length - byteCount && otherOffset >= 0 && otherOffset <= other.length - byteCount && Util.arrayRangeEquals(commonRangeEquals.getData(), offset, other, otherOffset, byteCount);
    }

    public static final boolean commonStartsWith(ByteString commonStartsWith, ByteString prefix) {
        Intrinsics.checkNotNullParameter(commonStartsWith, "$this$commonStartsWith");
        Intrinsics.checkNotNullParameter(prefix, "prefix");
        return commonStartsWith.rangeEquals(0, prefix, 0, prefix.size());
    }

    public static final boolean commonStartsWith(ByteString commonStartsWith, byte[] prefix) {
        Intrinsics.checkNotNullParameter(commonStartsWith, "$this$commonStartsWith");
        Intrinsics.checkNotNullParameter(prefix, "prefix");
        return commonStartsWith.rangeEquals(0, prefix, 0, prefix.length);
    }

    public static final ByteString commonSubstring(ByteString commonSubstring, int beginIndex, int endIndex) {
        Intrinsics.checkNotNullParameter(commonSubstring, "$this$commonSubstring");
        if (!(beginIndex >= 0)) {
            throw new IllegalArgumentException("beginIndex < 0".toString());
        }
        if (!(endIndex <= commonSubstring.getData().length)) {
            throw new IllegalArgumentException(("endIndex > length(" + commonSubstring.getData().length + ')').toString());
        }
        if (endIndex - beginIndex >= 0) {
            return (beginIndex == 0 && endIndex == commonSubstring.getData().length) ? commonSubstring : new ByteString(ArraysKt.copyOfRange(commonSubstring.getData(), beginIndex, endIndex));
        }
        throw new IllegalArgumentException("endIndex < beginIndex".toString());
    }

    public static final ByteString commonToAsciiLowercase(ByteString commonToAsciiLowercase) {
        byte b;
        Intrinsics.checkNotNullParameter(commonToAsciiLowercase, "$this$commonToAsciiLowercase");
        for (int i = 0; i < commonToAsciiLowercase.getData().length; i++) {
            byte b2 = commonToAsciiLowercase.getData()[i];
            byte b3 = (byte) 65;
            if (b2 >= b3 && b2 <= (b = (byte) 90)) {
                byte[] data = commonToAsciiLowercase.getData();
                byte[] copyOf = Arrays.copyOf(data, data.length);
                Intrinsics.checkNotNullExpressionValue(copyOf, "java.util.Arrays.copyOf(this, size)");
                int i2 = i + 1;
                copyOf[i] = (byte) (b2 + 32);
                while (i2 < copyOf.length) {
                    byte b4 = copyOf[i2];
                    if (b4 < b3 || b4 > b) {
                        i2++;
                    } else {
                        copyOf[i2] = (byte) (b4 + 32);
                        i2++;
                    }
                }
                return new ByteString(copyOf);
            }
        }
        return commonToAsciiLowercase;
    }

    public static final ByteString commonToAsciiUppercase(ByteString commonToAsciiUppercase) {
        byte b;
        Intrinsics.checkNotNullParameter(commonToAsciiUppercase, "$this$commonToAsciiUppercase");
        for (int i = 0; i < commonToAsciiUppercase.getData().length; i++) {
            byte b2 = commonToAsciiUppercase.getData()[i];
            byte b3 = (byte) 97;
            if (b2 >= b3 && b2 <= (b = (byte) 122)) {
                byte[] data = commonToAsciiUppercase.getData();
                byte[] copyOf = Arrays.copyOf(data, data.length);
                Intrinsics.checkNotNullExpressionValue(copyOf, "java.util.Arrays.copyOf(this, size)");
                int i2 = i + 1;
                copyOf[i] = (byte) (b2 - 32);
                while (i2 < copyOf.length) {
                    byte b4 = copyOf[i2];
                    if (b4 < b3 || b4 > b) {
                        i2++;
                    } else {
                        copyOf[i2] = (byte) (b4 - 32);
                        i2++;
                    }
                }
                return new ByteString(copyOf);
            }
        }
        return commonToAsciiUppercase;
    }

    public static final byte[] commonToByteArray(ByteString commonToByteArray) {
        Intrinsics.checkNotNullParameter(commonToByteArray, "$this$commonToByteArray");
        byte[] data = commonToByteArray.getData();
        byte[] copyOf = Arrays.copyOf(data, data.length);
        Intrinsics.checkNotNullExpressionValue(copyOf, "java.util.Arrays.copyOf(this, size)");
        return copyOf;
    }

    public static final ByteString commonToByteString(byte[] commonToByteString, int offset, int byteCount) {
        Intrinsics.checkNotNullParameter(commonToByteString, "$this$commonToByteString");
        Util.checkOffsetAndCount(commonToByteString.length, offset, byteCount);
        return new ByteString(ArraysKt.copyOfRange(commonToByteString, offset, offset + byteCount));
    }

    /* JADX WARN: Failed to parse debug info
    jadx.core.utils.exceptions.JadxRuntimeException: Failed to parse type string: null
    	at jadx.core.dex.instructions.args.ArgType.parse(ArgType.java:736)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.getVarType(DebugInfoAttachVisitor.java:139)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.attachDebugInfo(DebugInfoAttachVisitor.java:94)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.processDebugInfo(DebugInfoAttachVisitor.java:51)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.visit(DebugInfoAttachVisitor.java:41)
     */
    public static final String commonToString(ByteString commonToString) {
        Intrinsics.checkNotNullParameter(commonToString, "$this$commonToString");
        if (commonToString.getData().length == 0) {
            return "[size=0]";
        }
        int codePointIndexToCharIndex = codePointIndexToCharIndex(commonToString.getData(), 64);
        if (codePointIndexToCharIndex == -1) {
            if (commonToString.getData().length <= 64) {
                return "[hex=" + commonToString.hex() + ']';
            }
            StringBuilder append = new StringBuilder().append("[size=").append(commonToString.getData().length).append(" hex=");
            ByteString byteString = commonToString;
            if (!(64 <= byteString.getData().length)) {
                throw new IllegalArgumentException(("endIndex > length(" + byteString.getData().length + ')').toString());
            }
            if (!(64 - 0 >= 0)) {
                throw new IllegalArgumentException("endIndex < beginIndex".toString());
            }
            if (64 != byteString.getData().length) {
                byteString = new ByteString(ArraysKt.copyOfRange(byteString.getData(), 0, 64));
            }
            return append.append(byteString.hex()).append("…]").toString();
        }
        String utf8 = commonToString.utf8();
        if (utf8 == null) {
            throw new NullPointerException("null cannot be cast to non-null type java.lang.String");
        }
        String substring = utf8.substring(0, codePointIndexToCharIndex);
        Intrinsics.checkNotNullExpressionValue(substring, "(this as java.lang.Strin…ing(startIndex, endIndex)");
        String replace$default = StringsKt.replace$default(substring, "\\", "\\\\", false, 4, (Object) null);
        Log5ECF72.a(replace$default);
        LogE84000.a(replace$default);
        Log229316.a(replace$default);
        String replace$default2 = StringsKt.replace$default(replace$default, "\n", "\\n", false, 4, (Object) null);
        Log5ECF72.a(replace$default2);
        LogE84000.a(replace$default2);
        Log229316.a(replace$default2);
        String replace$default3 = StringsKt.replace$default(replace$default2, "\r", "\\r", false, 4, (Object) null);
        Log5ECF72.a(replace$default3);
        LogE84000.a(replace$default3);
        Log229316.a(replace$default3);
        return codePointIndexToCharIndex < utf8.length() ? "[size=" + commonToString.getData().length + " text=" + replace$default3 + "…]" : "[text=" + replace$default3 + ']';
    }

    /* JADX WARN: Failed to parse debug info
    jadx.core.utils.exceptions.JadxRuntimeException: Failed to parse type string: null
    	at jadx.core.dex.instructions.args.ArgType.parse(ArgType.java:736)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.getVarType(DebugInfoAttachVisitor.java:139)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.attachDebugInfo(DebugInfoAttachVisitor.java:94)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.processDebugInfo(DebugInfoAttachVisitor.java:51)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.visit(DebugInfoAttachVisitor.java:41)
     */
    public static final String commonUtf8(ByteString commonUtf8) {
        Intrinsics.checkNotNullParameter(commonUtf8, "$this$commonUtf8");
        String utf8 = commonUtf8.getUtf8();
        if (utf8 != null) {
            return utf8;
        }
        String utf8String = Platform.toUtf8String(commonUtf8.internalArray$okio());
        Log5ECF72.a(utf8String);
        LogE84000.a(utf8String);
        Log229316.a(utf8String);
        commonUtf8.setUtf8$okio(utf8String);
        return utf8String;
    }

    public static final void commonWrite(ByteString commonWrite, Buffer buffer, int offset, int byteCount) {
        Intrinsics.checkNotNullParameter(commonWrite, "$this$commonWrite");
        Intrinsics.checkNotNullParameter(buffer, "buffer");
        buffer.write(commonWrite.getData(), offset, byteCount);
    }

    public static final int decodeHexDigit(char c) {
        if ('0' <= c && '9' >= c) {
            return c - '0';
        }
        if ('a' <= c && 'f' >= c) {
            return (c - 'a') + 10;
        }
        if ('A' > c || 'F' < c) {
            throw new IllegalArgumentException("Unexpected hex digit: " + c);
        }
        return (c - 'A') + 10;
    }

    public static final char[] getHEX_DIGIT_CHARS() {
        return HEX_DIGIT_CHARS;
    }
}
