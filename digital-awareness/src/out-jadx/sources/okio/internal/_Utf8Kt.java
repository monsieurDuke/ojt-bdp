package okio.internal;

import java.util.Arrays;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.internal.ByteCompanionObject;
import kotlin.jvm.internal.Intrinsics;
import mt.Log229316;
import mt.Log5ECF72;
import mt.LogE84000;
import okhttp3.HttpUrl;
import okio.Utf8;

/* compiled from: 01F5.java */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u0016\n\u0000\n\u0002\u0010\u0012\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\u001a\n\u0010\u0000\u001a\u00020\u0001*\u00020\u0002\u001a\u001e\u0010\u0003\u001a\u00020\u0002*\u00020\u00012\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u0005¨\u0006\u0007"}, d2 = {"commonAsUtf8ToByteArray", HttpUrl.FRAGMENT_ENCODE_SET, HttpUrl.FRAGMENT_ENCODE_SET, "commonToUtf8String", "beginIndex", HttpUrl.FRAGMENT_ENCODE_SET, "endIndex", "okio"}, k = 2, mv = {1, 4, 0})
/* loaded from: classes.dex */
public final class _Utf8Kt {
    public static final byte[] commonAsUtf8ToByteArray(String commonAsUtf8ToByteArray) {
        char charAt;
        Intrinsics.checkNotNullParameter(commonAsUtf8ToByteArray, "$this$commonAsUtf8ToByteArray");
        byte[] bArr = new byte[commonAsUtf8ToByteArray.length() * 4];
        int length = commonAsUtf8ToByteArray.length();
        for (int i = 0; i < length; i++) {
            char charAt2 = commonAsUtf8ToByteArray.charAt(i);
            if (Intrinsics.compare((int) charAt2, 128) >= 0) {
                int i2 = i;
                int length2 = commonAsUtf8ToByteArray.length();
                int i3 = i;
                while (i3 < length2) {
                    char charAt3 = commonAsUtf8ToByteArray.charAt(i3);
                    if (Intrinsics.compare((int) charAt3, 128) < 0) {
                        int i4 = i2 + 1;
                        bArr[i2] = (byte) charAt3;
                        i3++;
                        while (i3 < length2 && Intrinsics.compare((int) commonAsUtf8ToByteArray.charAt(i3), 128) < 0) {
                            bArr[i4] = (byte) commonAsUtf8ToByteArray.charAt(i3);
                            i3++;
                            i4++;
                        }
                        i2 = i4;
                    } else if (Intrinsics.compare((int) charAt3, 2048) < 0) {
                        int i5 = i2 + 1;
                        bArr[i2] = (byte) ((charAt3 >> 6) | 192);
                        bArr[i5] = (byte) ((charAt3 & '?') | 128);
                        i3++;
                        i2 = i5 + 1;
                    } else if (55296 > charAt3 || 57343 < charAt3) {
                        int i6 = i2 + 1;
                        bArr[i2] = (byte) ((charAt3 >> '\f') | 224);
                        int i7 = i6 + 1;
                        bArr[i6] = (byte) (((charAt3 >> 6) & 63) | 128);
                        bArr[i7] = (byte) ((charAt3 & '?') | 128);
                        i3++;
                        i2 = i7 + 1;
                    } else if (Intrinsics.compare((int) charAt3, 56319) > 0 || length2 <= i3 + 1 || 56320 > (charAt = commonAsUtf8ToByteArray.charAt(i3 + 1)) || 57343 < charAt) {
                        bArr[i2] = Utf8.REPLACEMENT_BYTE;
                        i3++;
                        i2++;
                    } else {
                        int charAt4 = ((charAt3 << '\n') + commonAsUtf8ToByteArray.charAt(i3 + 1)) - 56613888;
                        int i8 = i2 + 1;
                        bArr[i2] = (byte) ((charAt4 >> 18) | 240);
                        int i9 = i8 + 1;
                        bArr[i8] = (byte) (((charAt4 >> 12) & 63) | 128);
                        int i10 = i9 + 1;
                        bArr[i9] = (byte) (((charAt4 >> 6) & 63) | 128);
                        bArr[i10] = (byte) ((charAt4 & 63) | 128);
                        i3 += 2;
                        i2 = i10 + 1;
                    }
                }
                byte[] copyOf = Arrays.copyOf(bArr, i2);
                Intrinsics.checkNotNullExpressionValue(copyOf, "java.util.Arrays.copyOf(this, newSize)");
                return copyOf;
            }
            bArr[i] = (byte) charAt2;
        }
        byte[] copyOf2 = Arrays.copyOf(bArr, commonAsUtf8ToByteArray.length());
        Intrinsics.checkNotNullExpressionValue(copyOf2, "java.util.Arrays.copyOf(this, newSize)");
        return copyOf2;
    }

    /* JADX WARN: Failed to parse debug info
    jadx.core.utils.exceptions.JadxRuntimeException: Failed to parse type string: null
    	at jadx.core.dex.instructions.args.ArgType.parse(ArgType.java:736)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.getVarType(DebugInfoAttachVisitor.java:139)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.attachDebugInfo(DebugInfoAttachVisitor.java:94)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.processDebugInfo(DebugInfoAttachVisitor.java:51)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.visit(DebugInfoAttachVisitor.java:41)
     */
    public static final String commonToUtf8String(byte[] commonToUtf8String, int i, int i2) {
        boolean z;
        int i3;
        int i4;
        int i5;
        int i6;
        int i7;
        int i8;
        int i9;
        int i10;
        int i11;
        int i12;
        int i13;
        Intrinsics.checkNotNullParameter(commonToUtf8String, "$this$commonToUtf8String");
        if (i < 0 || i2 > commonToUtf8String.length || i > i2) {
            throw new ArrayIndexOutOfBoundsException("size=" + commonToUtf8String.length + " beginIndex=" + i + " endIndex=" + i2);
        }
        char[] cArr = new char[i2 - i];
        int i14 = 0;
        boolean z2 = false;
        int i15 = i;
        while (i15 < i2) {
            byte b = commonToUtf8String[i15];
            if (b >= 0) {
                int i16 = i14 + 1;
                cArr[i14] = (char) b;
                i15++;
                while (i15 < i2 && commonToUtf8String[i15] >= 0) {
                    cArr[i16] = (char) commonToUtf8String[i15];
                    i15++;
                    i16++;
                }
                z = z2;
                i14 = i16;
            } else if ((b >> 5) == -2) {
                if (i2 <= i15 + 1) {
                    int i17 = i14 + 1;
                    cArr[i14] = (char) Utf8.REPLACEMENT_CODE_POINT;
                    Unit unit = Unit.INSTANCE;
                    i12 = i17;
                    i13 = 1;
                    z = z2;
                } else {
                    byte b2 = commonToUtf8String[i15];
                    byte b3 = commonToUtf8String[i15 + 1];
                    if ((b3 & 192) == 128) {
                        int i18 = (b3 ^ ByteCompanionObject.MIN_VALUE) ^ (b2 << 6);
                        if (i18 < 128) {
                            z = z2;
                            i11 = i14 + 1;
                            cArr[i14] = (char) Utf8.REPLACEMENT_CODE_POINT;
                        } else {
                            z = z2;
                            i11 = i14 + 1;
                            cArr[i14] = (char) i18;
                        }
                        Unit unit2 = Unit.INSTANCE;
                        i12 = i11;
                        i13 = 2;
                    } else {
                        i12 = i14 + 1;
                        cArr[i14] = (char) Utf8.REPLACEMENT_CODE_POINT;
                        Unit unit3 = Unit.INSTANCE;
                        z = z2;
                        i13 = 1;
                    }
                }
                i15 += i13;
                i14 = i12;
            } else {
                z = z2;
                if ((b >> 4) == -2) {
                    if (i2 <= i15 + 2) {
                        i9 = i14 + 1;
                        cArr[i14] = (char) Utf8.REPLACEMENT_CODE_POINT;
                        Unit unit4 = Unit.INSTANCE;
                        if (i2 > i15 + 1) {
                            if ((192 & commonToUtf8String[i15 + 1]) == 128) {
                                i10 = 2;
                            }
                        }
                        i10 = 1;
                    } else {
                        byte b4 = commonToUtf8String[i15];
                        byte b5 = commonToUtf8String[i15 + 1];
                        if ((b5 & 192) == 128) {
                            byte b6 = commonToUtf8String[i15 + 2];
                            if ((b6 & 192) == 128) {
                                int i19 = (((-123008) ^ b6) ^ (b5 << 6)) ^ (b4 << 12);
                                if (i19 < 2048) {
                                    i8 = i14 + 1;
                                    cArr[i14] = (char) Utf8.REPLACEMENT_CODE_POINT;
                                } else if (55296 <= i19 && 57343 >= i19) {
                                    i8 = i14 + 1;
                                    cArr[i14] = (char) Utf8.REPLACEMENT_CODE_POINT;
                                } else {
                                    i8 = i14 + 1;
                                    cArr[i14] = (char) i19;
                                }
                                Unit unit5 = Unit.INSTANCE;
                                i9 = i8;
                                i10 = 3;
                            } else {
                                int i20 = i14 + 1;
                                cArr[i14] = (char) Utf8.REPLACEMENT_CODE_POINT;
                                Unit unit6 = Unit.INSTANCE;
                                i9 = i20;
                                i10 = 2;
                            }
                        } else {
                            int i21 = i14 + 1;
                            cArr[i14] = (char) Utf8.REPLACEMENT_CODE_POINT;
                            Unit unit7 = Unit.INSTANCE;
                            i9 = i21;
                            i10 = 1;
                        }
                    }
                    i15 += i10;
                    i14 = i9;
                } else if ((b >> 3) == -2) {
                    if (i2 <= i15 + 3) {
                        if (65533 != 65533) {
                            int i22 = i14 + 1;
                            cArr[i14] = (char) ((Utf8.REPLACEMENT_CODE_POINT >>> 10) + Utf8.HIGH_SURROGATE_HEADER);
                            i6 = i22 + 1;
                            cArr[i22] = (char) ((65533 & 1023) + Utf8.LOG_SURROGATE_HEADER);
                        } else {
                            cArr[i14] = Utf8.REPLACEMENT_CHARACTER;
                            i6 = i14 + 1;
                        }
                        Unit unit8 = Unit.INSTANCE;
                        if (i2 > i15 + 1) {
                            if ((192 & commonToUtf8String[i15 + 1]) == 128) {
                                if (i2 > i15 + 2) {
                                    if ((192 & commonToUtf8String[i15 + 2]) == 128) {
                                        i5 = 3;
                                    }
                                }
                                i5 = 2;
                            }
                        }
                        i5 = 1;
                    } else {
                        byte b7 = commonToUtf8String[i15];
                        byte b8 = commonToUtf8String[i15 + 1];
                        if ((b8 & 192) == 128) {
                            byte b9 = commonToUtf8String[i15 + 2];
                            if ((b9 & 192) == 128) {
                                byte b10 = commonToUtf8String[i15 + 3];
                                if ((b10 & 192) == 128) {
                                    int i23 = (((3678080 ^ b10) ^ (b9 << 6)) ^ (b8 << 12)) ^ (b7 << 18);
                                    if (i23 > 1114111) {
                                        if (65533 != 65533) {
                                            int i24 = i14 + 1;
                                            cArr[i14] = (char) ((Utf8.REPLACEMENT_CODE_POINT >>> 10) + Utf8.HIGH_SURROGATE_HEADER);
                                            i4 = i24 + 1;
                                            cArr[i24] = (char) ((65533 & 1023) + Utf8.LOG_SURROGATE_HEADER);
                                            Unit unit9 = Unit.INSTANCE;
                                            i5 = 4;
                                            i6 = i4;
                                        } else {
                                            i3 = i14 + 1;
                                            cArr[i14] = Utf8.REPLACEMENT_CHARACTER;
                                            i4 = i3;
                                            Unit unit92 = Unit.INSTANCE;
                                            i5 = 4;
                                            i6 = i4;
                                        }
                                    } else if (55296 <= i23 && 57343 >= i23) {
                                        if (65533 != 65533) {
                                            int i25 = i14 + 1;
                                            cArr[i14] = (char) ((Utf8.REPLACEMENT_CODE_POINT >>> 10) + Utf8.HIGH_SURROGATE_HEADER);
                                            i4 = i25 + 1;
                                            cArr[i25] = (char) ((65533 & 1023) + Utf8.LOG_SURROGATE_HEADER);
                                            Unit unit922 = Unit.INSTANCE;
                                            i5 = 4;
                                            i6 = i4;
                                        } else {
                                            i3 = i14 + 1;
                                            cArr[i14] = Utf8.REPLACEMENT_CHARACTER;
                                            i4 = i3;
                                            Unit unit9222 = Unit.INSTANCE;
                                            i5 = 4;
                                            i6 = i4;
                                        }
                                    } else if (i23 < 65536) {
                                        if (65533 != 65533) {
                                            int i26 = i14 + 1;
                                            cArr[i14] = (char) ((Utf8.REPLACEMENT_CODE_POINT >>> 10) + Utf8.HIGH_SURROGATE_HEADER);
                                            i4 = i26 + 1;
                                            cArr[i26] = (char) ((65533 & 1023) + Utf8.LOG_SURROGATE_HEADER);
                                            Unit unit92222 = Unit.INSTANCE;
                                            i5 = 4;
                                            i6 = i4;
                                        } else {
                                            i3 = i14 + 1;
                                            cArr[i14] = Utf8.REPLACEMENT_CHARACTER;
                                            i4 = i3;
                                            Unit unit922222 = Unit.INSTANCE;
                                            i5 = 4;
                                            i6 = i4;
                                        }
                                    } else if (i23 != 65533) {
                                        int i27 = i14 + 1;
                                        cArr[i14] = (char) ((i23 >>> 10) + Utf8.HIGH_SURROGATE_HEADER);
                                        i4 = i27 + 1;
                                        cArr[i27] = (char) ((i23 & 1023) + Utf8.LOG_SURROGATE_HEADER);
                                        Unit unit9222222 = Unit.INSTANCE;
                                        i5 = 4;
                                        i6 = i4;
                                    } else {
                                        i3 = i14 + 1;
                                        cArr[i14] = Utf8.REPLACEMENT_CHARACTER;
                                        i4 = i3;
                                        Unit unit92222222 = Unit.INSTANCE;
                                        i5 = 4;
                                        i6 = i4;
                                    }
                                } else {
                                    if (65533 != 65533) {
                                        int i28 = i14 + 1;
                                        cArr[i14] = (char) ((Utf8.REPLACEMENT_CODE_POINT >>> 10) + Utf8.HIGH_SURROGATE_HEADER);
                                        i7 = i28 + 1;
                                        cArr[i28] = (char) ((65533 & 1023) + Utf8.LOG_SURROGATE_HEADER);
                                    } else {
                                        cArr[i14] = Utf8.REPLACEMENT_CHARACTER;
                                        i7 = i14 + 1;
                                    }
                                    Unit unit10 = Unit.INSTANCE;
                                    i6 = i7;
                                    i5 = 3;
                                }
                            } else {
                                if (65533 != 65533) {
                                    int i29 = i14 + 1;
                                    cArr[i14] = (char) ((Utf8.REPLACEMENT_CODE_POINT >>> 10) + Utf8.HIGH_SURROGATE_HEADER);
                                    cArr[i29] = (char) ((65533 & 1023) + Utf8.LOG_SURROGATE_HEADER);
                                    i6 = i29 + 1;
                                } else {
                                    cArr[i14] = Utf8.REPLACEMENT_CHARACTER;
                                    i6 = i14 + 1;
                                }
                                Unit unit11 = Unit.INSTANCE;
                                i5 = 2;
                            }
                        } else {
                            if (65533 != 65533) {
                                int i30 = i14 + 1;
                                cArr[i14] = (char) ((Utf8.REPLACEMENT_CODE_POINT >>> 10) + Utf8.HIGH_SURROGATE_HEADER);
                                i6 = i30 + 1;
                                cArr[i30] = (char) ((65533 & 1023) + Utf8.LOG_SURROGATE_HEADER);
                            } else {
                                cArr[i14] = Utf8.REPLACEMENT_CHARACTER;
                                i6 = i14 + 1;
                            }
                            Unit unit12 = Unit.INSTANCE;
                            i5 = 1;
                        }
                    }
                    i15 += i5;
                    i14 = i6;
                } else {
                    cArr[i14] = Utf8.REPLACEMENT_CHARACTER;
                    i15++;
                    i14++;
                }
            }
            z2 = z;
        }
        String str = new String(cArr, 0, i14);
        Log5ECF72.a(str);
        LogE84000.a(str);
        Log229316.a(str);
        return str;
    }

    /* JADX WARN: Failed to parse debug info
    jadx.core.utils.exceptions.JadxRuntimeException: Failed to parse type string: null
    	at jadx.core.dex.instructions.args.ArgType.parse(ArgType.java:736)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.getVarType(DebugInfoAttachVisitor.java:139)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.attachDebugInfo(DebugInfoAttachVisitor.java:94)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.processDebugInfo(DebugInfoAttachVisitor.java:51)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.visit(DebugInfoAttachVisitor.java:41)
     */
    public static /* synthetic */ String commonToUtf8String$default(byte[] bArr, int i, int i2, int i3, Object obj) {
        if ((i3 & 1) != 0) {
            i = 0;
        }
        if ((i3 & 2) != 0) {
            i2 = bArr.length;
        }
        String commonToUtf8String = commonToUtf8String(bArr, i, i2);
        Log5ECF72.a(commonToUtf8String);
        LogE84000.a(commonToUtf8String);
        Log229316.a(commonToUtf8String);
        return commonToUtf8String;
    }
}
