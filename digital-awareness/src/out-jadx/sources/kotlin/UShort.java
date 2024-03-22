package kotlin;

import kotlin.jvm.JvmInline;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.UIntRange;
import mt.Log229316;
import mt.Log5ECF72;
import mt.LogE84000;
import okhttp3.HttpUrl;
import okhttp3.internal.ws.WebSocketProtocol;

/* compiled from: 011B.java */
@Metadata(d1 = {"\u0000j\n\u0002\u0018\u0002\n\u0002\u0010\u000f\n\u0000\n\u0002\u0010\n\n\u0002\b\t\n\u0002\u0010\b\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\r\n\u0002\u0010\u000b\n\u0002\u0010\u0000\n\u0002\b!\n\u0002\u0018\u0002\n\u0002\b\r\n\u0002\u0010\u0005\n\u0002\b\u0003\n\u0002\u0010\u0006\n\u0002\b\u0003\n\u0002\u0010\u0007\n\u0002\b\u0005\n\u0002\u0010\t\n\u0002\b\u0005\n\u0002\u0010\u000e\n\u0002\b\u000e\b\u0087@\u0018\u0000 t2\b\u0012\u0004\u0012\u00020\u00000\u0001:\u0001tB\u0014\b\u0001\u0012\u0006\u0010\u0002\u001a\u00020\u0003ø\u0001\u0000¢\u0006\u0004\b\u0004\u0010\u0005J\u001b\u0010\b\u001a\u00020\u00002\u0006\u0010\t\u001a\u00020\u0000H\u0087\fø\u0001\u0000¢\u0006\u0004\b\n\u0010\u000bJ\u001b\u0010\f\u001a\u00020\r2\u0006\u0010\t\u001a\u00020\u000eH\u0087\nø\u0001\u0000¢\u0006\u0004\b\u000f\u0010\u0010J\u001b\u0010\f\u001a\u00020\r2\u0006\u0010\t\u001a\u00020\u0011H\u0087\nø\u0001\u0000¢\u0006\u0004\b\u0012\u0010\u0013J\u001b\u0010\f\u001a\u00020\r2\u0006\u0010\t\u001a\u00020\u0014H\u0087\nø\u0001\u0000¢\u0006\u0004\b\u0015\u0010\u0016J\u001b\u0010\f\u001a\u00020\r2\u0006\u0010\t\u001a\u00020\u0000H\u0097\nø\u0001\u0000¢\u0006\u0004\b\u0017\u0010\u0018J\u0016\u0010\u0019\u001a\u00020\u0000H\u0087\nø\u0001\u0001ø\u0001\u0000¢\u0006\u0004\b\u001a\u0010\u0005J\u001b\u0010\u001b\u001a\u00020\u00112\u0006\u0010\t\u001a\u00020\u000eH\u0087\nø\u0001\u0000¢\u0006\u0004\b\u001c\u0010\u0010J\u001b\u0010\u001b\u001a\u00020\u00112\u0006\u0010\t\u001a\u00020\u0011H\u0087\nø\u0001\u0000¢\u0006\u0004\b\u001d\u0010\u0013J\u001b\u0010\u001b\u001a\u00020\u00142\u0006\u0010\t\u001a\u00020\u0014H\u0087\nø\u0001\u0000¢\u0006\u0004\b\u001e\u0010\u001fJ\u001b\u0010\u001b\u001a\u00020\u00112\u0006\u0010\t\u001a\u00020\u0000H\u0087\nø\u0001\u0000¢\u0006\u0004\b \u0010\u0018J\u001a\u0010!\u001a\u00020\"2\b\u0010\t\u001a\u0004\u0018\u00010#HÖ\u0003¢\u0006\u0004\b$\u0010%J\u001b\u0010&\u001a\u00020\u00112\u0006\u0010\t\u001a\u00020\u000eH\u0087\bø\u0001\u0000¢\u0006\u0004\b'\u0010\u0010J\u001b\u0010&\u001a\u00020\u00112\u0006\u0010\t\u001a\u00020\u0011H\u0087\bø\u0001\u0000¢\u0006\u0004\b(\u0010\u0013J\u001b\u0010&\u001a\u00020\u00142\u0006\u0010\t\u001a\u00020\u0014H\u0087\bø\u0001\u0000¢\u0006\u0004\b)\u0010\u001fJ\u001b\u0010&\u001a\u00020\u00112\u0006\u0010\t\u001a\u00020\u0000H\u0087\bø\u0001\u0000¢\u0006\u0004\b*\u0010\u0018J\u0010\u0010+\u001a\u00020\rHÖ\u0001¢\u0006\u0004\b,\u0010-J\u0016\u0010.\u001a\u00020\u0000H\u0087\nø\u0001\u0001ø\u0001\u0000¢\u0006\u0004\b/\u0010\u0005J\u0016\u00100\u001a\u00020\u0000H\u0087\bø\u0001\u0001ø\u0001\u0000¢\u0006\u0004\b1\u0010\u0005J\u001b\u00102\u001a\u00020\u00112\u0006\u0010\t\u001a\u00020\u000eH\u0087\nø\u0001\u0000¢\u0006\u0004\b3\u0010\u0010J\u001b\u00102\u001a\u00020\u00112\u0006\u0010\t\u001a\u00020\u0011H\u0087\nø\u0001\u0000¢\u0006\u0004\b4\u0010\u0013J\u001b\u00102\u001a\u00020\u00142\u0006\u0010\t\u001a\u00020\u0014H\u0087\nø\u0001\u0000¢\u0006\u0004\b5\u0010\u001fJ\u001b\u00102\u001a\u00020\u00112\u0006\u0010\t\u001a\u00020\u0000H\u0087\nø\u0001\u0000¢\u0006\u0004\b6\u0010\u0018J\u001b\u00107\u001a\u00020\u000e2\u0006\u0010\t\u001a\u00020\u000eH\u0087\bø\u0001\u0000¢\u0006\u0004\b8\u00109J\u001b\u00107\u001a\u00020\u00112\u0006\u0010\t\u001a\u00020\u0011H\u0087\bø\u0001\u0000¢\u0006\u0004\b:\u0010\u0013J\u001b\u00107\u001a\u00020\u00142\u0006\u0010\t\u001a\u00020\u0014H\u0087\bø\u0001\u0000¢\u0006\u0004\b;\u0010\u001fJ\u001b\u00107\u001a\u00020\u00002\u0006\u0010\t\u001a\u00020\u0000H\u0087\bø\u0001\u0000¢\u0006\u0004\b<\u0010\u000bJ\u001b\u0010=\u001a\u00020\u00002\u0006\u0010\t\u001a\u00020\u0000H\u0087\fø\u0001\u0000¢\u0006\u0004\b>\u0010\u000bJ\u001b\u0010?\u001a\u00020\u00112\u0006\u0010\t\u001a\u00020\u000eH\u0087\nø\u0001\u0000¢\u0006\u0004\b@\u0010\u0010J\u001b\u0010?\u001a\u00020\u00112\u0006\u0010\t\u001a\u00020\u0011H\u0087\nø\u0001\u0000¢\u0006\u0004\bA\u0010\u0013J\u001b\u0010?\u001a\u00020\u00142\u0006\u0010\t\u001a\u00020\u0014H\u0087\nø\u0001\u0000¢\u0006\u0004\bB\u0010\u001fJ\u001b\u0010?\u001a\u00020\u00112\u0006\u0010\t\u001a\u00020\u0000H\u0087\nø\u0001\u0000¢\u0006\u0004\bC\u0010\u0018J\u001b\u0010D\u001a\u00020E2\u0006\u0010\t\u001a\u00020\u0000H\u0087\nø\u0001\u0000¢\u0006\u0004\bF\u0010GJ\u001b\u0010H\u001a\u00020\u00112\u0006\u0010\t\u001a\u00020\u000eH\u0087\nø\u0001\u0000¢\u0006\u0004\bI\u0010\u0010J\u001b\u0010H\u001a\u00020\u00112\u0006\u0010\t\u001a\u00020\u0011H\u0087\nø\u0001\u0000¢\u0006\u0004\bJ\u0010\u0013J\u001b\u0010H\u001a\u00020\u00142\u0006\u0010\t\u001a\u00020\u0014H\u0087\nø\u0001\u0000¢\u0006\u0004\bK\u0010\u001fJ\u001b\u0010H\u001a\u00020\u00112\u0006\u0010\t\u001a\u00020\u0000H\u0087\nø\u0001\u0000¢\u0006\u0004\bL\u0010\u0018J\u001b\u0010M\u001a\u00020\u00112\u0006\u0010\t\u001a\u00020\u000eH\u0087\nø\u0001\u0000¢\u0006\u0004\bN\u0010\u0010J\u001b\u0010M\u001a\u00020\u00112\u0006\u0010\t\u001a\u00020\u0011H\u0087\nø\u0001\u0000¢\u0006\u0004\bO\u0010\u0013J\u001b\u0010M\u001a\u00020\u00142\u0006\u0010\t\u001a\u00020\u0014H\u0087\nø\u0001\u0000¢\u0006\u0004\bP\u0010\u001fJ\u001b\u0010M\u001a\u00020\u00112\u0006\u0010\t\u001a\u00020\u0000H\u0087\nø\u0001\u0000¢\u0006\u0004\bQ\u0010\u0018J\u0010\u0010R\u001a\u00020SH\u0087\b¢\u0006\u0004\bT\u0010UJ\u0010\u0010V\u001a\u00020WH\u0087\b¢\u0006\u0004\bX\u0010YJ\u0010\u0010Z\u001a\u00020[H\u0087\b¢\u0006\u0004\b\\\u0010]J\u0010\u0010^\u001a\u00020\rH\u0087\b¢\u0006\u0004\b_\u0010-J\u0010\u0010`\u001a\u00020aH\u0087\b¢\u0006\u0004\bb\u0010cJ\u0010\u0010d\u001a\u00020\u0003H\u0087\b¢\u0006\u0004\be\u0010\u0005J\u000f\u0010f\u001a\u00020gH\u0016¢\u0006\u0004\bh\u0010iJ\u0016\u0010j\u001a\u00020\u000eH\u0087\bø\u0001\u0001ø\u0001\u0000¢\u0006\u0004\bk\u0010UJ\u0016\u0010l\u001a\u00020\u0011H\u0087\bø\u0001\u0001ø\u0001\u0000¢\u0006\u0004\bm\u0010-J\u0016\u0010n\u001a\u00020\u0014H\u0087\bø\u0001\u0001ø\u0001\u0000¢\u0006\u0004\bo\u0010cJ\u0016\u0010p\u001a\u00020\u0000H\u0087\bø\u0001\u0001ø\u0001\u0000¢\u0006\u0004\bq\u0010\u0005J\u001b\u0010r\u001a\u00020\u00002\u0006\u0010\t\u001a\u00020\u0000H\u0087\fø\u0001\u0000¢\u0006\u0004\bs\u0010\u000bR\u0016\u0010\u0002\u001a\u00020\u00038\u0000X\u0081\u0004¢\u0006\b\n\u0000\u0012\u0004\b\u0006\u0010\u0007\u0088\u0001\u0002\u0092\u0001\u00020\u0003ø\u0001\u0000\u0082\u0002\b\n\u0002\b\u0019\n\u0002\b!¨\u0006u"}, d2 = {"Lkotlin/UShort;", HttpUrl.FRAGMENT_ENCODE_SET, "data", HttpUrl.FRAGMENT_ENCODE_SET, "constructor-impl", "(S)S", "getData$annotations", "()V", "and", "other", "and-xj2QHRw", "(SS)S", "compareTo", HttpUrl.FRAGMENT_ENCODE_SET, "Lkotlin/UByte;", "compareTo-7apg3OU", "(SB)I", "Lkotlin/UInt;", "compareTo-WZ4Q5Ns", "(SI)I", "Lkotlin/ULong;", "compareTo-VKZWuLQ", "(SJ)I", "compareTo-xj2QHRw", "(SS)I", "dec", "dec-Mh2AYeg", "div", "div-7apg3OU", "div-WZ4Q5Ns", "div-VKZWuLQ", "(SJ)J", "div-xj2QHRw", "equals", HttpUrl.FRAGMENT_ENCODE_SET, HttpUrl.FRAGMENT_ENCODE_SET, "equals-impl", "(SLjava/lang/Object;)Z", "floorDiv", "floorDiv-7apg3OU", "floorDiv-WZ4Q5Ns", "floorDiv-VKZWuLQ", "floorDiv-xj2QHRw", "hashCode", "hashCode-impl", "(S)I", "inc", "inc-Mh2AYeg", "inv", "inv-Mh2AYeg", "minus", "minus-7apg3OU", "minus-WZ4Q5Ns", "minus-VKZWuLQ", "minus-xj2QHRw", "mod", "mod-7apg3OU", "(SB)B", "mod-WZ4Q5Ns", "mod-VKZWuLQ", "mod-xj2QHRw", "or", "or-xj2QHRw", "plus", "plus-7apg3OU", "plus-WZ4Q5Ns", "plus-VKZWuLQ", "plus-xj2QHRw", "rangeTo", "Lkotlin/ranges/UIntRange;", "rangeTo-xj2QHRw", "(SS)Lkotlin/ranges/UIntRange;", "rem", "rem-7apg3OU", "rem-WZ4Q5Ns", "rem-VKZWuLQ", "rem-xj2QHRw", "times", "times-7apg3OU", "times-WZ4Q5Ns", "times-VKZWuLQ", "times-xj2QHRw", "toByte", HttpUrl.FRAGMENT_ENCODE_SET, "toByte-impl", "(S)B", "toDouble", HttpUrl.FRAGMENT_ENCODE_SET, "toDouble-impl", "(S)D", "toFloat", HttpUrl.FRAGMENT_ENCODE_SET, "toFloat-impl", "(S)F", "toInt", "toInt-impl", "toLong", HttpUrl.FRAGMENT_ENCODE_SET, "toLong-impl", "(S)J", "toShort", "toShort-impl", "toString", HttpUrl.FRAGMENT_ENCODE_SET, "toString-impl", "(S)Ljava/lang/String;", "toUByte", "toUByte-w2LRezQ", "toUInt", "toUInt-pVg5ArA", "toULong", "toULong-s-VKNKU", "toUShort", "toUShort-Mh2AYeg", "xor", "xor-xj2QHRw", "Companion", "kotlin-stdlib"}, k = 1, mv = {1, 7, 1}, xi = 48)
@JvmInline
/* loaded from: classes.dex */
public final class UShort implements Comparable<UShort> {
    public static final short MAX_VALUE = -1;
    public static final short MIN_VALUE = 0;
    public static final int SIZE_BITS = 16;
    public static final int SIZE_BYTES = 2;
    private final short data;

    private /* synthetic */ UShort(short data) {
        this.data = data;
    }

    /* renamed from: and-xj2QHRw */
    private static final short m315andxj2QHRw(short arg0, short other) {
        return m322constructorimpl((short) (arg0 & other));
    }

    /* renamed from: box-impl */
    public static final /* synthetic */ UShort m316boximpl(short s) {
        return new UShort(s);
    }

    /* renamed from: compareTo-7apg3OU */
    private static final int m317compareTo7apg3OU(short arg0, byte other) {
        return Intrinsics.compare(65535 & arg0, other & UByte.MAX_VALUE);
    }

    /* renamed from: compareTo-VKZWuLQ */
    private static final int m318compareToVKZWuLQ(short arg0, long other) {
        return UnsignedKt.ulongCompare(ULong.m216constructorimpl(arg0 & WebSocketProtocol.PAYLOAD_SHORT_MAX), other);
    }

    /* renamed from: compareTo-WZ4Q5Ns */
    private static final int m319compareToWZ4Q5Ns(short arg0, int other) {
        return UnsignedKt.uintCompare(UInt.m138constructorimpl(65535 & arg0), other);
    }

    /* renamed from: compareTo-xj2QHRw */
    private int m320compareToxj2QHRw(short other) {
        return Intrinsics.compare(getData() & MAX_VALUE, 65535 & other);
    }

    /* renamed from: compareTo-xj2QHRw */
    private static int m321compareToxj2QHRw(short arg0, short other) {
        return Intrinsics.compare(arg0 & MAX_VALUE, 65535 & other);
    }

    /* renamed from: constructor-impl */
    public static short m322constructorimpl(short s) {
        return s;
    }

    /* renamed from: dec-Mh2AYeg */
    private static final short m323decMh2AYeg(short arg0) {
        return m322constructorimpl((short) (arg0 - 1));
    }

    /* renamed from: div-7apg3OU */
    private static final int m324div7apg3OU(short arg0, byte other) {
        return UnsignedKt.m391uintDivideJ1ME1BU(UInt.m138constructorimpl(65535 & arg0), UInt.m138constructorimpl(other & UByte.MAX_VALUE));
    }

    /* renamed from: div-VKZWuLQ */
    private static final long m325divVKZWuLQ(short arg0, long other) {
        return UnsignedKt.m393ulongDivideeb3DHEI(ULong.m216constructorimpl(arg0 & WebSocketProtocol.PAYLOAD_SHORT_MAX), other);
    }

    /* renamed from: div-WZ4Q5Ns */
    private static final int m326divWZ4Q5Ns(short arg0, int other) {
        return UnsignedKt.m391uintDivideJ1ME1BU(UInt.m138constructorimpl(65535 & arg0), other);
    }

    /* renamed from: div-xj2QHRw */
    private static final int m327divxj2QHRw(short arg0, short other) {
        return UnsignedKt.m391uintDivideJ1ME1BU(UInt.m138constructorimpl(arg0 & MAX_VALUE), UInt.m138constructorimpl(65535 & other));
    }

    /* renamed from: equals-impl */
    public static boolean m328equalsimpl(short s, Object obj) {
        return (obj instanceof UShort) && s == ((UShort) obj).getData();
    }

    /* renamed from: equals-impl0 */
    public static final boolean m329equalsimpl0(short s, short s2) {
        return s == s2;
    }

    /* renamed from: floorDiv-7apg3OU */
    private static final int m330floorDiv7apg3OU(short arg0, byte other) {
        return UnsignedKt.m391uintDivideJ1ME1BU(UInt.m138constructorimpl(65535 & arg0), UInt.m138constructorimpl(other & UByte.MAX_VALUE));
    }

    /* renamed from: floorDiv-VKZWuLQ */
    private static final long m331floorDivVKZWuLQ(short arg0, long other) {
        return UnsignedKt.m393ulongDivideeb3DHEI(ULong.m216constructorimpl(arg0 & WebSocketProtocol.PAYLOAD_SHORT_MAX), other);
    }

    /* renamed from: floorDiv-WZ4Q5Ns */
    private static final int m332floorDivWZ4Q5Ns(short arg0, int other) {
        return UnsignedKt.m391uintDivideJ1ME1BU(UInt.m138constructorimpl(65535 & arg0), other);
    }

    /* renamed from: floorDiv-xj2QHRw */
    private static final int m333floorDivxj2QHRw(short arg0, short other) {
        return UnsignedKt.m391uintDivideJ1ME1BU(UInt.m138constructorimpl(arg0 & MAX_VALUE), UInt.m138constructorimpl(65535 & other));
    }

    public static /* synthetic */ void getData$annotations() {
    }

    /* renamed from: hashCode-impl */
    public static int m334hashCodeimpl(short s) {
        return s;
    }

    /* renamed from: inc-Mh2AYeg */
    private static final short m335incMh2AYeg(short arg0) {
        return m322constructorimpl((short) (arg0 + 1));
    }

    /* renamed from: inv-Mh2AYeg */
    private static final short m336invMh2AYeg(short arg0) {
        return m322constructorimpl((short) (~arg0));
    }

    /* renamed from: minus-7apg3OU */
    private static final int m337minus7apg3OU(short arg0, byte other) {
        return UInt.m138constructorimpl(UInt.m138constructorimpl(65535 & arg0) - UInt.m138constructorimpl(other & UByte.MAX_VALUE));
    }

    /* renamed from: minus-VKZWuLQ */
    private static final long m338minusVKZWuLQ(short arg0, long other) {
        return ULong.m216constructorimpl(ULong.m216constructorimpl(arg0 & WebSocketProtocol.PAYLOAD_SHORT_MAX) - other);
    }

    /* renamed from: minus-WZ4Q5Ns */
    private static final int m339minusWZ4Q5Ns(short arg0, int other) {
        return UInt.m138constructorimpl(UInt.m138constructorimpl(65535 & arg0) - other);
    }

    /* renamed from: minus-xj2QHRw */
    private static final int m340minusxj2QHRw(short arg0, short other) {
        return UInt.m138constructorimpl(UInt.m138constructorimpl(arg0 & MAX_VALUE) - UInt.m138constructorimpl(65535 & other));
    }

    /* renamed from: mod-7apg3OU */
    private static final byte m341mod7apg3OU(short arg0, byte other) {
        return UByte.m62constructorimpl((byte) UnsignedKt.m392uintRemainderJ1ME1BU(UInt.m138constructorimpl(65535 & arg0), UInt.m138constructorimpl(other & UByte.MAX_VALUE)));
    }

    /* renamed from: mod-VKZWuLQ */
    private static final long m342modVKZWuLQ(short arg0, long other) {
        return UnsignedKt.m394ulongRemaindereb3DHEI(ULong.m216constructorimpl(arg0 & WebSocketProtocol.PAYLOAD_SHORT_MAX), other);
    }

    /* renamed from: mod-WZ4Q5Ns */
    private static final int m343modWZ4Q5Ns(short arg0, int other) {
        return UnsignedKt.m392uintRemainderJ1ME1BU(UInt.m138constructorimpl(65535 & arg0), other);
    }

    /* renamed from: mod-xj2QHRw */
    private static final short m344modxj2QHRw(short arg0, short other) {
        return m322constructorimpl((short) UnsignedKt.m392uintRemainderJ1ME1BU(UInt.m138constructorimpl(arg0 & MAX_VALUE), UInt.m138constructorimpl(65535 & other)));
    }

    /* renamed from: or-xj2QHRw */
    private static final short m345orxj2QHRw(short arg0, short other) {
        return m322constructorimpl((short) (arg0 | other));
    }

    /* renamed from: plus-7apg3OU */
    private static final int m346plus7apg3OU(short arg0, byte other) {
        return UInt.m138constructorimpl(UInt.m138constructorimpl(65535 & arg0) + UInt.m138constructorimpl(other & UByte.MAX_VALUE));
    }

    /* renamed from: plus-VKZWuLQ */
    private static final long m347plusVKZWuLQ(short arg0, long other) {
        return ULong.m216constructorimpl(ULong.m216constructorimpl(arg0 & WebSocketProtocol.PAYLOAD_SHORT_MAX) + other);
    }

    /* renamed from: plus-WZ4Q5Ns */
    private static final int m348plusWZ4Q5Ns(short arg0, int other) {
        return UInt.m138constructorimpl(UInt.m138constructorimpl(65535 & arg0) + other);
    }

    /* renamed from: plus-xj2QHRw */
    private static final int m349plusxj2QHRw(short arg0, short other) {
        return UInt.m138constructorimpl(UInt.m138constructorimpl(arg0 & MAX_VALUE) + UInt.m138constructorimpl(65535 & other));
    }

    /* renamed from: rangeTo-xj2QHRw */
    private static final UIntRange m350rangeToxj2QHRw(short arg0, short other) {
        return new UIntRange(UInt.m138constructorimpl(arg0 & MAX_VALUE), UInt.m138constructorimpl(65535 & other), null);
    }

    /* renamed from: rem-7apg3OU */
    private static final int m351rem7apg3OU(short arg0, byte other) {
        return UnsignedKt.m392uintRemainderJ1ME1BU(UInt.m138constructorimpl(65535 & arg0), UInt.m138constructorimpl(other & UByte.MAX_VALUE));
    }

    /* renamed from: rem-VKZWuLQ */
    private static final long m352remVKZWuLQ(short arg0, long other) {
        return UnsignedKt.m394ulongRemaindereb3DHEI(ULong.m216constructorimpl(arg0 & WebSocketProtocol.PAYLOAD_SHORT_MAX), other);
    }

    /* renamed from: rem-WZ4Q5Ns */
    private static final int m353remWZ4Q5Ns(short arg0, int other) {
        return UnsignedKt.m392uintRemainderJ1ME1BU(UInt.m138constructorimpl(65535 & arg0), other);
    }

    /* renamed from: rem-xj2QHRw */
    private static final int m354remxj2QHRw(short arg0, short other) {
        return UnsignedKt.m392uintRemainderJ1ME1BU(UInt.m138constructorimpl(arg0 & MAX_VALUE), UInt.m138constructorimpl(65535 & other));
    }

    /* renamed from: times-7apg3OU */
    private static final int m355times7apg3OU(short arg0, byte other) {
        return UInt.m138constructorimpl(UInt.m138constructorimpl(65535 & arg0) * UInt.m138constructorimpl(other & UByte.MAX_VALUE));
    }

    /* renamed from: times-VKZWuLQ */
    private static final long m356timesVKZWuLQ(short arg0, long other) {
        return ULong.m216constructorimpl(ULong.m216constructorimpl(arg0 & WebSocketProtocol.PAYLOAD_SHORT_MAX) * other);
    }

    /* renamed from: times-WZ4Q5Ns */
    private static final int m357timesWZ4Q5Ns(short arg0, int other) {
        return UInt.m138constructorimpl(UInt.m138constructorimpl(65535 & arg0) * other);
    }

    /* renamed from: times-xj2QHRw */
    private static final int m358timesxj2QHRw(short arg0, short other) {
        return UInt.m138constructorimpl(UInt.m138constructorimpl(arg0 & MAX_VALUE) * UInt.m138constructorimpl(65535 & other));
    }

    /* renamed from: toByte-impl */
    private static final byte m359toByteimpl(short arg0) {
        return (byte) arg0;
    }

    /* renamed from: toDouble-impl */
    private static final double m360toDoubleimpl(short arg0) {
        return 65535 & arg0;
    }

    /* renamed from: toFloat-impl */
    private static final float m361toFloatimpl(short arg0) {
        return 65535 & arg0;
    }

    /* renamed from: toInt-impl */
    private static final int m362toIntimpl(short arg0) {
        return 65535 & arg0;
    }

    /* renamed from: toLong-impl */
    private static final long m363toLongimpl(short arg0) {
        return arg0 & WebSocketProtocol.PAYLOAD_SHORT_MAX;
    }

    /* renamed from: toShort-impl */
    private static final short m364toShortimpl(short arg0) {
        return arg0;
    }

    /* JADX WARN: Failed to parse debug info
    jadx.core.utils.exceptions.JadxRuntimeException: Failed to parse type string: null
    	at jadx.core.dex.instructions.args.ArgType.parse(ArgType.java:736)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.getVarType(DebugInfoAttachVisitor.java:139)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.attachDebugInfo(DebugInfoAttachVisitor.java:94)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.processDebugInfo(DebugInfoAttachVisitor.java:51)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.visit(DebugInfoAttachVisitor.java:41)
     */
    /* renamed from: toString-impl */
    public static String m365toStringimpl(short s) {
        String valueOf = String.valueOf(65535 & s);
        Log5ECF72.a(valueOf);
        LogE84000.a(valueOf);
        Log229316.a(valueOf);
        return valueOf;
    }

    /* renamed from: toUByte-w2LRezQ */
    private static final byte m366toUBytew2LRezQ(short arg0) {
        return UByte.m62constructorimpl((byte) arg0);
    }

    /* renamed from: toUInt-pVg5ArA */
    private static final int m367toUIntpVg5ArA(short arg0) {
        return UInt.m138constructorimpl(65535 & arg0);
    }

    /* renamed from: toULong-s-VKNKU */
    private static final long m368toULongsVKNKU(short arg0) {
        return ULong.m216constructorimpl(arg0 & WebSocketProtocol.PAYLOAD_SHORT_MAX);
    }

    /* renamed from: toUShort-Mh2AYeg */
    private static final short m369toUShortMh2AYeg(short arg0) {
        return arg0;
    }

    /* renamed from: xor-xj2QHRw */
    private static final short m370xorxj2QHRw(short arg0, short other) {
        return m322constructorimpl((short) (arg0 ^ other));
    }

    @Override // java.lang.Comparable
    public /* bridge */ /* synthetic */ int compareTo(UShort uShort) {
        return Intrinsics.compare(getData() & MAX_VALUE, uShort.getData() & MAX_VALUE);
    }

    public boolean equals(Object obj) {
        return m328equalsimpl(this.data, obj);
    }

    public int hashCode() {
        return m334hashCodeimpl(this.data);
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
        String m365toStringimpl = m365toStringimpl(this.data);
        Log5ECF72.a(m365toStringimpl);
        LogE84000.a(m365toStringimpl);
        Log229316.a(m365toStringimpl);
        return m365toStringimpl;
    }

    /* renamed from: unbox-impl, reason: from getter */
    public final /* synthetic */ short getData() {
        return this.data;
    }
}
