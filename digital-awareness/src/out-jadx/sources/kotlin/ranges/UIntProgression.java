package kotlin.ranges;

import java.util.Iterator;
import kotlin.Metadata;
import kotlin.UInt;
import kotlin.UnsignedKt;
import kotlin.internal.UProgressionUtilKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.markers.KMappedMarker;
import mt.Log229316;
import mt.Log5ECF72;
import mt.LogE84000;
import okhttp3.HttpUrl;

/* compiled from: 014C.java */
@Metadata(d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0010\u001c\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0007\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010(\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\b\u0017\u0018\u0000 \u00172\b\u0012\u0004\u0012\u00020\u00020\u0001:\u0001\u0017B\"\b\u0000\u0012\u0006\u0010\u0003\u001a\u00020\u0002\u0012\u0006\u0010\u0004\u001a\u00020\u0002\u0012\u0006\u0010\u0005\u001a\u00020\u0006ø\u0001\u0000¢\u0006\u0002\u0010\u0007J\u0013\u0010\r\u001a\u00020\u000e2\b\u0010\u000f\u001a\u0004\u0018\u00010\u0010H\u0096\u0002J\b\u0010\u0011\u001a\u00020\u0006H\u0016J\b\u0010\u0012\u001a\u00020\u000eH\u0016J\u0012\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00020\u0014H\u0086\u0002ø\u0001\u0000J\b\u0010\u0015\u001a\u00020\u0016H\u0016R\u0013\u0010\b\u001a\u00020\u0002ø\u0001\u0000ø\u0001\u0001¢\u0006\u0004\n\u0002\u0010\tR\u0013\u0010\n\u001a\u00020\u0002ø\u0001\u0000ø\u0001\u0001¢\u0006\u0004\n\u0002\u0010\tR\u0011\u0010\u0005\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fø\u0001\u0000\u0082\u0002\b\n\u0002\b\u0019\n\u0002\b!¨\u0006\u0018"}, d2 = {"Lkotlin/ranges/UIntProgression;", HttpUrl.FRAGMENT_ENCODE_SET, "Lkotlin/UInt;", "start", "endInclusive", "step", HttpUrl.FRAGMENT_ENCODE_SET, "(IIILkotlin/jvm/internal/DefaultConstructorMarker;)V", "first", "I", "last", "getStep", "()I", "equals", HttpUrl.FRAGMENT_ENCODE_SET, "other", HttpUrl.FRAGMENT_ENCODE_SET, "hashCode", "isEmpty", "iterator", HttpUrl.FRAGMENT_ENCODE_SET, "toString", HttpUrl.FRAGMENT_ENCODE_SET, "Companion", "kotlin-stdlib"}, k = 1, mv = {1, 7, 1}, xi = 48)
/* loaded from: classes.dex */
public class UIntProgression implements Iterable<UInt>, KMappedMarker {

    /* renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private final int first;
    private final int last;
    private final int step;

    /* compiled from: UIntRange.kt */
    @Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J(\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00062\u0006\u0010\b\u001a\u00020\tø\u0001\u0000¢\u0006\u0004\b\n\u0010\u000b\u0082\u0002\u0004\n\u0002\b\u0019¨\u0006\f"}, d2 = {"Lkotlin/ranges/UIntProgression$Companion;", HttpUrl.FRAGMENT_ENCODE_SET, "()V", "fromClosedRange", "Lkotlin/ranges/UIntProgression;", "rangeStart", "Lkotlin/UInt;", "rangeEnd", "step", HttpUrl.FRAGMENT_ENCODE_SET, "fromClosedRange-Nkh28Cs", "(III)Lkotlin/ranges/UIntProgression;", "kotlin-stdlib"}, k = 1, mv = {1, 7, 1}, xi = 48)
    /* loaded from: classes.dex */
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        /* renamed from: fromClosedRange-Nkh28Cs, reason: not valid java name */
        public final UIntProgression m1275fromClosedRangeNkh28Cs(int rangeStart, int rangeEnd, int step) {
            return new UIntProgression(rangeStart, rangeEnd, step, null);
        }
    }

    private UIntProgression(int start, int endInclusive, int step) {
        if (step == 0) {
            throw new IllegalArgumentException("Step must be non-zero.");
        }
        if (step == Integer.MIN_VALUE) {
            throw new IllegalArgumentException("Step must be greater than Int.MIN_VALUE to avoid overflow on negation.");
        }
        this.first = start;
        this.last = UProgressionUtilKt.m1259getProgressionLastElementNkh28Cs(start, endInclusive, step);
        this.step = step;
    }

    public /* synthetic */ UIntProgression(int i, int i2, int i3, DefaultConstructorMarker defaultConstructorMarker) {
        this(i, i2, i3);
    }

    public boolean equals(Object other) {
        return (other instanceof UIntProgression) && ((isEmpty() && ((UIntProgression) other).isEmpty()) || (this.first == ((UIntProgression) other).first && this.last == ((UIntProgression) other).last && this.step == ((UIntProgression) other).step));
    }

    /* renamed from: getFirst-pVg5ArA, reason: not valid java name and from getter */
    public final int getFirst() {
        return this.first;
    }

    /* renamed from: getLast-pVg5ArA, reason: not valid java name and from getter */
    public final int getLast() {
        return this.last;
    }

    public final int getStep() {
        return this.step;
    }

    public int hashCode() {
        if (isEmpty()) {
            return -1;
        }
        return (((this.first * 31) + this.last) * 31) + this.step;
    }

    public boolean isEmpty() {
        if (this.step > 0) {
            if (UnsignedKt.uintCompare(this.first, this.last) > 0) {
                return true;
            }
        } else if (UnsignedKt.uintCompare(this.first, this.last) < 0) {
            return true;
        }
        return false;
    }

    @Override // java.lang.Iterable
    public final Iterator<UInt> iterator() {
        return new UIntProgressionIterator(this.first, this.last, this.step, null);
    }

    /* JADX WARN: Failed to parse debug info
    jadx.core.utils.exceptions.JadxRuntimeException: Failed to parse type string: null
    	at jadx.core.dex.instructions.args.ArgType.parse(ArgType.java:736)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.getVarType(DebugInfoAttachVisitor.java:139)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.attachDebugInfo(DebugInfoAttachVisitor.java:94)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.processDebugInfo(DebugInfoAttachVisitor.java:51)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.visit(DebugInfoAttachVisitor.java:41)
     */
    public String toString() {
        StringBuilder append;
        int i;
        if (this.step > 0) {
            StringBuilder sb = new StringBuilder();
            String m183toStringimpl = UInt.m183toStringimpl(this.first);
            Log5ECF72.a(m183toStringimpl);
            LogE84000.a(m183toStringimpl);
            Log229316.a(m183toStringimpl);
            StringBuilder append2 = sb.append((Object) m183toStringimpl).append("..");
            String m183toStringimpl2 = UInt.m183toStringimpl(this.last);
            Log5ECF72.a(m183toStringimpl2);
            LogE84000.a(m183toStringimpl2);
            Log229316.a(m183toStringimpl2);
            append = append2.append((Object) m183toStringimpl2).append(" step ");
            i = this.step;
        } else {
            StringBuilder sb2 = new StringBuilder();
            String m183toStringimpl3 = UInt.m183toStringimpl(this.first);
            Log5ECF72.a(m183toStringimpl3);
            LogE84000.a(m183toStringimpl3);
            Log229316.a(m183toStringimpl3);
            StringBuilder append3 = sb2.append((Object) m183toStringimpl3).append(" downTo ");
            String m183toStringimpl4 = UInt.m183toStringimpl(this.last);
            Log5ECF72.a(m183toStringimpl4);
            LogE84000.a(m183toStringimpl4);
            Log229316.a(m183toStringimpl4);
            append = append3.append((Object) m183toStringimpl4).append(" step ");
            i = -this.step;
        }
        return append.append(i).toString();
    }
}
