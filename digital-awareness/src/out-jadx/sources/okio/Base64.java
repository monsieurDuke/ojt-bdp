package okio;

import java.util.Arrays;
import kotlin.Metadata;
import kotlin.UByte;
import kotlin.jvm.internal.Intrinsics;
import mt.Log229316;
import mt.Log5ECF72;
import mt.LogE84000;
import okhttp3.HttpUrl;

/* compiled from: 01EA.java */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u0012\n\u0000\n\u0002\u0010\u0012\n\u0002\b\u0005\n\u0002\u0010\u000e\n\u0002\b\u0003\u001a\u000e\u0010\u0006\u001a\u0004\u0018\u00010\u0001*\u00020\u0007H\u0000\u001a\u0016\u0010\b\u001a\u00020\u0007*\u00020\u00012\b\b\u0002\u0010\t\u001a\u00020\u0001H\u0000\"\u0014\u0010\u0000\u001a\u00020\u0001X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0002\u0010\u0003\"\u0014\u0010\u0004\u001a\u00020\u0001X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0003¨\u0006\n"}, d2 = {"BASE64", HttpUrl.FRAGMENT_ENCODE_SET, "getBASE64", "()[B", "BASE64_URL_SAFE", "getBASE64_URL_SAFE", "decodeBase64ToArray", HttpUrl.FRAGMENT_ENCODE_SET, "encodeBase64", "map", "okio"}, k = 2, mv = {1, 4, 0})
/* renamed from: okio.-Base64 */
/* loaded from: classes.dex */
public final class Base64 {
    private static final byte[] BASE64 = ByteString.INSTANCE.encodeUtf8("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/").getData();
    private static final byte[] BASE64_URL_SAFE = ByteString.INSTANCE.encodeUtf8("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789-_").getData();

    public static final byte[] decodeBase64ToArray(String decodeBase64ToArray) {
        int i;
        char charAt;
        Intrinsics.checkNotNullParameter(decodeBase64ToArray, "$this$decodeBase64ToArray");
        int length = decodeBase64ToArray.length();
        while (length > 0 && ((charAt = decodeBase64ToArray.charAt(length - 1)) == '=' || charAt == '\n' || charAt == '\r' || charAt == ' ' || charAt == '\t')) {
            length--;
        }
        byte[] bArr = new byte[(int) ((length * 6) / 8)];
        int i2 = 0;
        int i3 = 0;
        int i4 = 0;
        for (int i5 = 0; i5 < length; i5++) {
            char charAt2 = decodeBase64ToArray.charAt(i5);
            if ('A' <= charAt2 && 'Z' >= charAt2) {
                i = charAt2 - 'A';
            } else if ('a' <= charAt2 && 'z' >= charAt2) {
                i = charAt2 - 'G';
            } else if ('0' <= charAt2 && '9' >= charAt2) {
                i = charAt2 + 4;
            } else if (charAt2 == '+' || charAt2 == '-') {
                i = 62;
            } else if (charAt2 == '/' || charAt2 == '_') {
                i = 63;
            } else {
                if (charAt2 != '\n' && charAt2 != '\r' && charAt2 != ' ' && charAt2 != '\t') {
                    return null;
                }
            }
            i4 = (i4 << 6) | i;
            i3++;
            if (i3 % 4 == 0) {
                int i6 = i2 + 1;
                bArr[i2] = (byte) (i4 >> 16);
                int i7 = i6 + 1;
                bArr[i6] = (byte) (i4 >> 8);
                bArr[i7] = (byte) i4;
                i2 = i7 + 1;
            }
        }
        switch (i3 % 4) {
            case 1:
                return null;
            case 2:
                bArr[i2] = (byte) ((i4 << 12) >> 16);
                i2++;
                break;
            case 3:
                int i8 = i4 << 6;
                int i9 = i2 + 1;
                bArr[i2] = (byte) (i8 >> 16);
                i2 = i9 + 1;
                bArr[i9] = (byte) (i8 >> 8);
                break;
        }
        if (i2 == bArr.length) {
            return bArr;
        }
        byte[] copyOf = Arrays.copyOf(bArr, i2);
        Intrinsics.checkNotNullExpressionValue(copyOf, "java.util.Arrays.copyOf(this, newSize)");
        return copyOf;
    }

    /* JADX WARN: Failed to parse debug info
    jadx.core.utils.exceptions.JadxRuntimeException: Failed to parse type string: null
    	at jadx.core.dex.instructions.args.ArgType.parse(ArgType.java:736)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.getVarType(DebugInfoAttachVisitor.java:139)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.attachDebugInfo(DebugInfoAttachVisitor.java:94)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.processDebugInfo(DebugInfoAttachVisitor.java:51)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.visit(DebugInfoAttachVisitor.java:41)
     */
    public static final String encodeBase64(byte[] encodeBase64, byte[] map) {
        Intrinsics.checkNotNullParameter(encodeBase64, "$this$encodeBase64");
        Intrinsics.checkNotNullParameter(map, "map");
        byte[] bArr = new byte[((encodeBase64.length + 2) / 3) * 4];
        int i = 0;
        int length = encodeBase64.length - (encodeBase64.length % 3);
        int i2 = 0;
        while (i2 < length) {
            int i3 = i2 + 1;
            byte b = encodeBase64[i2];
            int i4 = i3 + 1;
            byte b2 = encodeBase64[i3];
            int i5 = i4 + 1;
            byte b3 = encodeBase64[i4];
            int i6 = i + 1;
            bArr[i] = map[(b & UByte.MAX_VALUE) >> 2];
            int i7 = i6 + 1;
            bArr[i6] = map[((b & 3) << 4) | ((b2 & UByte.MAX_VALUE) >> 4)];
            int i8 = i7 + 1;
            bArr[i7] = map[((b2 & 15) << 2) | ((b3 & UByte.MAX_VALUE) >> 6)];
            i = i8 + 1;
            bArr[i8] = map[b3 & Utf8.REPLACEMENT_BYTE];
            i2 = i5;
        }
        switch (encodeBase64.length - length) {
            case 1:
                byte b4 = encodeBase64[i2];
                int i9 = i + 1;
                bArr[i] = map[(b4 & UByte.MAX_VALUE) >> 2];
                int i10 = i9 + 1;
                bArr[i9] = map[(b4 & 3) << 4];
                byte b5 = (byte) 61;
                bArr[i10] = b5;
                bArr[i10 + 1] = b5;
                break;
            case 2:
                int i11 = i2 + 1;
                byte b6 = encodeBase64[i2];
                byte b7 = encodeBase64[i11];
                int i12 = i + 1;
                bArr[i] = map[(b6 & UByte.MAX_VALUE) >> 2];
                int i13 = i12 + 1;
                bArr[i12] = map[((b6 & 3) << 4) | ((b7 & UByte.MAX_VALUE) >> 4)];
                bArr[i13] = map[(b7 & 15) << 2];
                bArr[i13 + 1] = (byte) 61;
                break;
        }
        String utf8String = Platform.toUtf8String(bArr);
        Log5ECF72.a(utf8String);
        LogE84000.a(utf8String);
        Log229316.a(utf8String);
        return utf8String;
    }

    /* JADX WARN: Failed to parse debug info
    jadx.core.utils.exceptions.JadxRuntimeException: Failed to parse type string: null
    	at jadx.core.dex.instructions.args.ArgType.parse(ArgType.java:736)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.getVarType(DebugInfoAttachVisitor.java:139)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.attachDebugInfo(DebugInfoAttachVisitor.java:94)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.processDebugInfo(DebugInfoAttachVisitor.java:51)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.visit(DebugInfoAttachVisitor.java:41)
     */
    public static /* synthetic */ String encodeBase64$default(byte[] bArr, byte[] bArr2, int i, Object obj) {
        if ((i & 1) != 0) {
            bArr2 = BASE64;
        }
        String encodeBase64 = encodeBase64(bArr, bArr2);
        Log5ECF72.a(encodeBase64);
        LogE84000.a(encodeBase64);
        Log229316.a(encodeBase64);
        return encodeBase64;
    }

    public static final byte[] getBASE64() {
        return BASE64;
    }

    public static final byte[] getBASE64_URL_SAFE() {
        return BASE64_URL_SAFE;
    }
}
