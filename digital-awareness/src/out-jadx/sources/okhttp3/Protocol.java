package okhttp3;

import java.io.IOException;
import kotlin.Metadata;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import mt.Log229316;
import mt.Log5ECF72;
import mt.LogE84000;

/* compiled from: Protocol.kt */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0000\n\u0002\u0010\u000e\n\u0002\b\n\b\u0086\u0001\u0018\u0000 \f2\b\u0012\u0004\u0012\u00020\u00000\u0001:\u0001\fB\u000f\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\b\u0010\u0005\u001a\u00020\u0003H\u0016R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000j\u0002\b\u0006j\u0002\b\u0007j\u0002\b\bj\u0002\b\tj\u0002\b\nj\u0002\b\u000b¨\u0006\r"}, d2 = {"Lokhttp3/Protocol;", HttpUrl.FRAGMENT_ENCODE_SET, "protocol", HttpUrl.FRAGMENT_ENCODE_SET, "(Ljava/lang/String;ILjava/lang/String;)V", "toString", "HTTP_1_0", "HTTP_1_1", "SPDY_3", "HTTP_2", "H2_PRIOR_KNOWLEDGE", "QUIC", "Companion", "okhttp"}, k = 1, mv = {1, 4, 0})
/* loaded from: classes.dex */
public enum Protocol {
    HTTP_1_0("http/1.0"),
    HTTP_1_1("http/1.1"),
    SPDY_3("spdy/3.1"),
    HTTP_2("h2"),
    H2_PRIOR_KNOWLEDGE("h2_prior_knowledge"),
    QUIC("quic");


    /* renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private final String protocol;

    /* compiled from: 01C1.java */
    @Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0007¨\u0006\u0007"}, d2 = {"Lokhttp3/Protocol$Companion;", HttpUrl.FRAGMENT_ENCODE_SET, "()V", "get", "Lokhttp3/Protocol;", "protocol", HttpUrl.FRAGMENT_ENCODE_SET, "okhttp"}, k = 1, mv = {1, 4, 0})
    /* loaded from: classes.dex */
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }

        /* JADX WARN: Failed to parse debug info
        jadx.core.utils.exceptions.JadxRuntimeException: Failed to parse type string: null
        	at jadx.core.dex.instructions.args.ArgType.parse(ArgType.java:736)
        	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.getVarType(DebugInfoAttachVisitor.java:139)
        	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.attachDebugInfo(DebugInfoAttachVisitor.java:94)
        	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.processDebugInfo(DebugInfoAttachVisitor.java:51)
        	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.visit(DebugInfoAttachVisitor.java:41)
         */
        @JvmStatic
        public final Protocol get(String protocol) throws IOException {
            Intrinsics.checkNotNullParameter(protocol, "protocol");
            String str = Protocol.HTTP_1_0.protocol;
            Log5ECF72.a(str);
            LogE84000.a(str);
            Log229316.a(str);
            if (Intrinsics.areEqual(protocol, str)) {
                return Protocol.HTTP_1_0;
            }
            String str2 = Protocol.HTTP_1_1.protocol;
            Log5ECF72.a(str2);
            LogE84000.a(str2);
            Log229316.a(str2);
            if (Intrinsics.areEqual(protocol, str2)) {
                return Protocol.HTTP_1_1;
            }
            String str3 = Protocol.H2_PRIOR_KNOWLEDGE.protocol;
            Log5ECF72.a(str3);
            LogE84000.a(str3);
            Log229316.a(str3);
            if (Intrinsics.areEqual(protocol, str3)) {
                return Protocol.H2_PRIOR_KNOWLEDGE;
            }
            String str4 = Protocol.HTTP_2.protocol;
            Log5ECF72.a(str4);
            LogE84000.a(str4);
            Log229316.a(str4);
            if (Intrinsics.areEqual(protocol, str4)) {
                return Protocol.HTTP_2;
            }
            String str5 = Protocol.SPDY_3.protocol;
            Log5ECF72.a(str5);
            LogE84000.a(str5);
            Log229316.a(str5);
            if (Intrinsics.areEqual(protocol, str5)) {
                return Protocol.SPDY_3;
            }
            String str6 = Protocol.QUIC.protocol;
            Log5ECF72.a(str6);
            LogE84000.a(str6);
            Log229316.a(str6);
            if (Intrinsics.areEqual(protocol, str6)) {
                return Protocol.QUIC;
            }
            throw new IOException("Unexpected protocol: " + protocol);
        }
    }

    Protocol(String protocol) {
        this.protocol = protocol;
    }

    @JvmStatic
    public static final Protocol get(String str) throws IOException {
        return INSTANCE.get(str);
    }

    @Override // java.lang.Enum
    public String toString() {
        return this.protocol;
    }
}
