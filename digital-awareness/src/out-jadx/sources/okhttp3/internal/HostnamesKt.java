package okhttp3.internal;

import java.net.IDN;
import java.net.InetAddress;
import java.util.Locale;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import mt.Log229316;
import mt.Log5ECF72;
import mt.LogE84000;
import okhttp3.HttpUrl;
import okio.Buffer;

/* compiled from: 01C3.java */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000&\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0012\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\u001a0\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00052\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\u0005H\u0002\u001a\"\u0010\n\u001a\u0004\u0018\u00010\u000b2\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0005H\u0002\u001a\u0010\u0010\f\u001a\u00020\u00032\u0006\u0010\u0007\u001a\u00020\bH\u0002\u001a\f\u0010\r\u001a\u00020\u0001*\u00020\u0003H\u0002\u001a\f\u0010\u000e\u001a\u0004\u0018\u00010\u0003*\u00020\u0003¨\u0006\u000f"}, d2 = {"decodeIpv4Suffix", HttpUrl.FRAGMENT_ENCODE_SET, "input", HttpUrl.FRAGMENT_ENCODE_SET, "pos", HttpUrl.FRAGMENT_ENCODE_SET, "limit", "address", HttpUrl.FRAGMENT_ENCODE_SET, "addressOffset", "decodeIpv6", "Ljava/net/InetAddress;", "inet6AddressToAscii", "containsInvalidHostnameAsciiCodes", "toCanonicalHost", "okhttp"}, k = 2, mv = {1, 4, 0})
/* loaded from: classes.dex */
public final class HostnamesKt {
    private static final boolean containsInvalidHostnameAsciiCodes(String $this$containsInvalidHostnameAsciiCodes) {
        int length = $this$containsInvalidHostnameAsciiCodes.length();
        for (int i = 0; i < length; i++) {
            char charAt = $this$containsInvalidHostnameAsciiCodes.charAt(i);
            if (Intrinsics.compare((int) charAt, 31) <= 0 || Intrinsics.compare((int) charAt, 127) >= 0 || StringsKt.indexOf$default((CharSequence) " #%/:?@[\\]", charAt, 0, false, 6, (Object) null) != -1) {
                return true;
            }
        }
        return false;
    }

    private static final boolean decodeIpv4Suffix(String input, int pos, int limit, byte[] address, int addressOffset) {
        int i = addressOffset;
        int i2 = pos;
        while (i2 < limit) {
            if (i == address.length) {
                return false;
            }
            if (i != addressOffset) {
                if (input.charAt(i2) != '.') {
                    return false;
                }
                i2++;
            }
            int i3 = 0;
            int i4 = i2;
            while (i2 < limit) {
                char charAt = input.charAt(i2);
                if (Intrinsics.compare((int) charAt, 48) < 0 || Intrinsics.compare((int) charAt, 57) > 0) {
                    break;
                }
                if ((i3 == 0 && i4 != i2) || ((i3 * 10) + charAt) - 48 > 255) {
                    return false;
                }
                i2++;
            }
            if (i2 - i4 == 0) {
                return false;
            }
            address[i] = (byte) i3;
            i++;
        }
        return i == addressOffset + 4;
    }

    /* JADX WARN: Code restructure failed: missing block: B:25:0x008d, code lost:
    
        return null;
     */
    /* JADX WARN: Removed duplicated region for block: B:15:0x0060  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private static final java.net.InetAddress decodeIpv6(java.lang.String r13, int r14, int r15) {
        /*
            r0 = 16
            byte[] r0 = new byte[r0]
            r1 = 0
            r2 = -1
            r3 = -1
            r4 = r14
            r10 = r4
        L9:
            r11 = -1
            r12 = 0
            if (r10 >= r15) goto L8e
            int r4 = r0.length
            if (r1 != r4) goto L11
            return r12
        L11:
            int r4 = r10 + 2
            if (r4 > r15) goto L2e
            r7 = 0
            r8 = 4
            r9 = 0
            java.lang.String r5 = "::"
            r4 = r13
            r6 = r10
            boolean r4 = kotlin.text.StringsKt.startsWith$default(r4, r5, r6, r7, r8, r9)
            if (r4 == 0) goto L2e
            if (r2 == r11) goto L25
            return r12
        L25:
            int r10 = r10 + 2
            int r1 = r1 + 2
            r2 = r1
            if (r10 != r15) goto L5a
            goto L8e
        L2e:
            if (r1 == 0) goto L5a
            r7 = 0
            r8 = 4
            r9 = 0
            java.lang.String r5 = ":"
            r4 = r13
            r6 = r10
            boolean r4 = kotlin.text.StringsKt.startsWith$default(r4, r5, r6, r7, r8, r9)
            if (r4 == 0) goto L40
            int r10 = r10 + 1
            goto L5a
        L40:
            r7 = 0
            r8 = 4
            r9 = 0
            java.lang.String r5 = "."
            r4 = r13
            r6 = r10
            boolean r4 = kotlin.text.StringsKt.startsWith$default(r4, r5, r6, r7, r8, r9)
            if (r4 == 0) goto L59
            int r4 = r1 + (-2)
            boolean r4 = decodeIpv4Suffix(r13, r3, r15, r0, r4)
            if (r4 != 0) goto L56
            return r12
        L56:
            int r1 = r1 + 2
            goto L8e
        L59:
            return r12
        L5a:
            r4 = 0
            r3 = r10
        L5e:
            if (r10 >= r15) goto L73
            char r5 = r13.charAt(r10)
            int r5 = okhttp3.internal.Util.parseHexDigit(r5)
            if (r5 != r11) goto L6b
            goto L73
        L6b:
            int r6 = r4 << 4
            int r4 = r6 + r5
            int r10 = r10 + 1
            goto L5e
        L73:
            int r5 = r10 - r3
            if (r5 == 0) goto L8d
            r6 = 4
            if (r5 <= r6) goto L7b
            goto L8d
        L7b:
            int r6 = r1 + 1
            int r7 = r4 >>> 8
            r7 = r7 & 255(0xff, float:3.57E-43)
            byte r7 = (byte) r7
            r0[r1] = r7
            int r1 = r6 + 1
            r7 = r4 & 255(0xff, float:3.57E-43)
            byte r7 = (byte) r7
            r0[r6] = r7
            goto L9
        L8d:
            return r12
        L8e:
            int r4 = r0.length
            if (r1 == r4) goto La5
            if (r2 != r11) goto L94
            return r12
        L94:
            int r4 = r0.length
            int r5 = r1 - r2
            int r4 = r4 - r5
            int r5 = r1 - r2
            java.lang.System.arraycopy(r0, r2, r0, r4, r5)
            int r4 = r0.length
            int r4 = r4 - r1
            int r4 = r4 + r2
            r5 = 0
            byte r5 = (byte) r5
            java.util.Arrays.fill(r0, r2, r4, r5)
        La5:
            java.net.InetAddress r4 = java.net.InetAddress.getByAddress(r0)
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: okhttp3.internal.HostnamesKt.decodeIpv6(java.lang.String, int, int):java.net.InetAddress");
    }

    private static final String inet6AddressToAscii(byte[] address) {
        int i = -1;
        int i2 = 0;
        int i3 = 0;
        while (i3 < address.length) {
            int i4 = i3;
            while (i3 < 16 && address[i3] == 0 && address[i3 + 1] == 0) {
                i3 += 2;
            }
            int i5 = i3 - i4;
            if (i5 > i2 && i5 >= 4) {
                i = i4;
                i2 = i5;
            }
            i3 += 2;
        }
        Buffer buffer = new Buffer();
        int i6 = 0;
        while (i6 < address.length) {
            if (i6 == i) {
                buffer.writeByte(58);
                i6 += i2;
                if (i6 == 16) {
                    buffer.writeByte(58);
                }
            } else {
                if (i6 > 0) {
                    buffer.writeByte(58);
                }
                buffer.writeHexadecimalUnsignedLong((Util.and(address[i6], 255) << 8) | Util.and(address[i6 + 1], 255));
                i6 += 2;
            }
        }
        return buffer.readUtf8();
    }

    /* JADX WARN: Failed to parse debug info
    jadx.core.utils.exceptions.JadxRuntimeException: Failed to parse type string: null
    	at jadx.core.dex.instructions.args.ArgType.parse(ArgType.java:736)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.getVarType(DebugInfoAttachVisitor.java:139)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.attachDebugInfo(DebugInfoAttachVisitor.java:94)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.processDebugInfo(DebugInfoAttachVisitor.java:51)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.visit(DebugInfoAttachVisitor.java:41)
     */
    public static final String toCanonicalHost(String toCanonicalHost) {
        Intrinsics.checkNotNullParameter(toCanonicalHost, "$this$toCanonicalHost");
        if (StringsKt.contains$default((CharSequence) toCanonicalHost, (CharSequence) ":", false, 2, (Object) null)) {
            InetAddress decodeIpv6 = (StringsKt.startsWith$default(toCanonicalHost, "[", false, 2, (Object) null) && StringsKt.endsWith$default(toCanonicalHost, "]", false, 2, (Object) null)) ? decodeIpv6(toCanonicalHost, 1, toCanonicalHost.length() - 1) : decodeIpv6(toCanonicalHost, 0, toCanonicalHost.length());
            if (decodeIpv6 == null) {
                return null;
            }
            byte[] address = decodeIpv6.getAddress();
            if (address.length != 16) {
                if (address.length == 4) {
                    return decodeIpv6.getHostAddress();
                }
                throw new AssertionError("Invalid IPv6 address: '" + toCanonicalHost + '\'');
            }
            Intrinsics.checkNotNullExpressionValue(address, "address");
            String inet6AddressToAscii = inet6AddressToAscii(address);
            Log5ECF72.a(inet6AddressToAscii);
            LogE84000.a(inet6AddressToAscii);
            Log229316.a(inet6AddressToAscii);
            return inet6AddressToAscii;
        }
        try {
            String ascii = IDN.toASCII(toCanonicalHost);
            Log5ECF72.a(ascii);
            LogE84000.a(ascii);
            Log229316.a(ascii);
            Intrinsics.checkNotNullExpressionValue(ascii, "IDN.toASCII(host)");
            Locale locale = Locale.US;
            Intrinsics.checkNotNullExpressionValue(locale, "Locale.US");
            if (ascii == null) {
                throw new NullPointerException("null cannot be cast to non-null type java.lang.String");
            }
            String lowerCase = ascii.toLowerCase(locale);
            Intrinsics.checkNotNullExpressionValue(lowerCase, "(this as java.lang.String).toLowerCase(locale)");
            if (!(lowerCase.length() == 0) && !containsInvalidHostnameAsciiCodes(lowerCase)) {
                return lowerCase;
            }
            return null;
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}
