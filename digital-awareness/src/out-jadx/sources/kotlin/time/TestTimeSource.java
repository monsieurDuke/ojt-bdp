package kotlin.time;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import kotlin.Metadata;
import mt.Log229316;
import mt.Log5ECF72;
import mt.LogE84000;
import okhttp3.HttpUrl;

/* compiled from: 0168.java */
@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\t\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\b\u0007\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u001a\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0002ø\u0001\u0000¢\u0006\u0004\b\t\u0010\nJ\u001b\u0010\u000b\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0086\u0002ø\u0001\u0000¢\u0006\u0004\b\f\u0010\nJ\b\u0010\r\u001a\u00020\u0004H\u0014R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000\u0082\u0002\u0004\n\u0002\b\u0019¨\u0006\u000e"}, d2 = {"Lkotlin/time/TestTimeSource;", "Lkotlin/time/AbstractLongTimeSource;", "()V", "reading", HttpUrl.FRAGMENT_ENCODE_SET, "overflow", HttpUrl.FRAGMENT_ENCODE_SET, TypedValues.TransitionType.S_DURATION, "Lkotlin/time/Duration;", "overflow-LRDsOJo", "(J)V", "plusAssign", "plusAssign-LRDsOJo", "read", "kotlin-stdlib"}, k = 1, mv = {1, 7, 1}, xi = 48)
/* loaded from: classes.dex */
public final class TestTimeSource extends AbstractLongTimeSource {
    private long reading;

    public TestTimeSource() {
        super(DurationUnit.NANOSECONDS);
    }

    /* JADX WARN: Failed to parse debug info
    jadx.core.utils.exceptions.JadxRuntimeException: Failed to parse type string: null
    	at jadx.core.dex.instructions.args.ArgType.parse(ArgType.java:736)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.getVarType(DebugInfoAttachVisitor.java:139)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.attachDebugInfo(DebugInfoAttachVisitor.java:94)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.processDebugInfo(DebugInfoAttachVisitor.java:51)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.visit(DebugInfoAttachVisitor.java:41)
     */
    /* renamed from: overflow-LRDsOJo, reason: not valid java name */
    private final void m1492overflowLRDsOJo(long duration) {
        StringBuilder append = new StringBuilder().append("TestTimeSource will overflow if its reading ").append(this.reading).append("ns is advanced by ");
        String m1408toStringimpl = Duration.m1408toStringimpl(duration);
        Log5ECF72.a(m1408toStringimpl);
        LogE84000.a(m1408toStringimpl);
        Log229316.a(m1408toStringimpl);
        throw new IllegalStateException(append.append((Object) m1408toStringimpl).append('.').toString());
    }

    /* renamed from: plusAssign-LRDsOJo, reason: not valid java name */
    public final void m1493plusAssignLRDsOJo(long duration) {
        long j;
        long m1405toLongimpl = Duration.m1405toLongimpl(duration, getUnit());
        if (m1405toLongimpl == Long.MIN_VALUE || m1405toLongimpl == Long.MAX_VALUE) {
            double m1402toDoubleimpl = this.reading + Duration.m1402toDoubleimpl(duration, getUnit());
            if (m1402toDoubleimpl > 9.223372036854776E18d || m1402toDoubleimpl < -9.223372036854776E18d) {
                m1492overflowLRDsOJo(duration);
            }
            j = (long) m1402toDoubleimpl;
        } else {
            long j2 = this.reading;
            j = j2 + m1405toLongimpl;
            if ((j2 ^ m1405toLongimpl) >= 0 && (j2 ^ j) < 0) {
                m1492overflowLRDsOJo(duration);
            }
        }
        this.reading = j;
    }

    @Override // kotlin.time.AbstractLongTimeSource
    /* renamed from: read, reason: from getter */
    protected long getReading() {
        return this.reading;
    }
}
