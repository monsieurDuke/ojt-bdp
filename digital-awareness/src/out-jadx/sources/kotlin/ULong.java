package kotlin;

import kotlin.jvm.JvmInline;
import kotlin.ranges.ULongRange;
import mt.Log229316;
import mt.Log5ECF72;
import mt.LogE84000;
import okhttp3.HttpUrl;
import okhttp3.internal.ws.WebSocketProtocol;

/* compiled from: 0118.java */
@Metadata(d1 = {"\u0000j\n\u0002\u0018\u0002\n\u0002\u0010\u000f\n\u0000\n\u0002\u0010\t\n\u0002\b\t\n\u0002\u0010\b\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\r\n\u0002\u0010\u000b\n\u0002\u0010\u0000\n\u0002\b\"\n\u0002\u0018\u0002\n\u0002\b\u0012\n\u0002\u0010\u0005\n\u0002\b\u0003\n\u0002\u0010\u0006\n\u0002\b\u0003\n\u0002\u0010\u0007\n\u0002\b\u0007\n\u0002\u0010\n\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u000e\b\u0087@\u0018\u0000 |2\b\u0012\u0004\u0012\u00020\u00000\u0001:\u0001|B\u0014\b\u0001\u0012\u0006\u0010\u0002\u001a\u00020\u0003ø\u0001\u0000¢\u0006\u0004\b\u0004\u0010\u0005J\u001b\u0010\b\u001a\u00020\u00002\u0006\u0010\t\u001a\u00020\u0000H\u0087\fø\u0001\u0000¢\u0006\u0004\b\n\u0010\u000bJ\u001b\u0010\f\u001a\u00020\r2\u0006\u0010\t\u001a\u00020\u000eH\u0087\nø\u0001\u0000¢\u0006\u0004\b\u000f\u0010\u0010J\u001b\u0010\f\u001a\u00020\r2\u0006\u0010\t\u001a\u00020\u0011H\u0087\nø\u0001\u0000¢\u0006\u0004\b\u0012\u0010\u0013J\u001b\u0010\f\u001a\u00020\r2\u0006\u0010\t\u001a\u00020\u0000H\u0097\nø\u0001\u0000¢\u0006\u0004\b\u0014\u0010\u0015J\u001b\u0010\f\u001a\u00020\r2\u0006\u0010\t\u001a\u00020\u0016H\u0087\nø\u0001\u0000¢\u0006\u0004\b\u0017\u0010\u0018J\u0016\u0010\u0019\u001a\u00020\u0000H\u0087\nø\u0001\u0001ø\u0001\u0000¢\u0006\u0004\b\u001a\u0010\u0005J\u001b\u0010\u001b\u001a\u00020\u00002\u0006\u0010\t\u001a\u00020\u000eH\u0087\nø\u0001\u0000¢\u0006\u0004\b\u001c\u0010\u001dJ\u001b\u0010\u001b\u001a\u00020\u00002\u0006\u0010\t\u001a\u00020\u0011H\u0087\nø\u0001\u0000¢\u0006\u0004\b\u001e\u0010\u001fJ\u001b\u0010\u001b\u001a\u00020\u00002\u0006\u0010\t\u001a\u00020\u0000H\u0087\nø\u0001\u0000¢\u0006\u0004\b \u0010\u000bJ\u001b\u0010\u001b\u001a\u00020\u00002\u0006\u0010\t\u001a\u00020\u0016H\u0087\nø\u0001\u0000¢\u0006\u0004\b!\u0010\"J\u001a\u0010#\u001a\u00020$2\b\u0010\t\u001a\u0004\u0018\u00010%HÖ\u0003¢\u0006\u0004\b&\u0010'J\u001b\u0010(\u001a\u00020\u00002\u0006\u0010\t\u001a\u00020\u000eH\u0087\bø\u0001\u0000¢\u0006\u0004\b)\u0010\u001dJ\u001b\u0010(\u001a\u00020\u00002\u0006\u0010\t\u001a\u00020\u0011H\u0087\bø\u0001\u0000¢\u0006\u0004\b*\u0010\u001fJ\u001b\u0010(\u001a\u00020\u00002\u0006\u0010\t\u001a\u00020\u0000H\u0087\bø\u0001\u0000¢\u0006\u0004\b+\u0010\u000bJ\u001b\u0010(\u001a\u00020\u00002\u0006\u0010\t\u001a\u00020\u0016H\u0087\bø\u0001\u0000¢\u0006\u0004\b,\u0010\"J\u0010\u0010-\u001a\u00020\rHÖ\u0001¢\u0006\u0004\b.\u0010/J\u0016\u00100\u001a\u00020\u0000H\u0087\nø\u0001\u0001ø\u0001\u0000¢\u0006\u0004\b1\u0010\u0005J\u0016\u00102\u001a\u00020\u0000H\u0087\bø\u0001\u0001ø\u0001\u0000¢\u0006\u0004\b3\u0010\u0005J\u001b\u00104\u001a\u00020\u00002\u0006\u0010\t\u001a\u00020\u000eH\u0087\nø\u0001\u0000¢\u0006\u0004\b5\u0010\u001dJ\u001b\u00104\u001a\u00020\u00002\u0006\u0010\t\u001a\u00020\u0011H\u0087\nø\u0001\u0000¢\u0006\u0004\b6\u0010\u001fJ\u001b\u00104\u001a\u00020\u00002\u0006\u0010\t\u001a\u00020\u0000H\u0087\nø\u0001\u0000¢\u0006\u0004\b7\u0010\u000bJ\u001b\u00104\u001a\u00020\u00002\u0006\u0010\t\u001a\u00020\u0016H\u0087\nø\u0001\u0000¢\u0006\u0004\b8\u0010\"J\u001b\u00109\u001a\u00020\u000e2\u0006\u0010\t\u001a\u00020\u000eH\u0087\bø\u0001\u0000¢\u0006\u0004\b:\u0010;J\u001b\u00109\u001a\u00020\u00112\u0006\u0010\t\u001a\u00020\u0011H\u0087\bø\u0001\u0000¢\u0006\u0004\b<\u0010\u0013J\u001b\u00109\u001a\u00020\u00002\u0006\u0010\t\u001a\u00020\u0000H\u0087\bø\u0001\u0000¢\u0006\u0004\b=\u0010\u000bJ\u001b\u00109\u001a\u00020\u00162\u0006\u0010\t\u001a\u00020\u0016H\u0087\bø\u0001\u0000¢\u0006\u0004\b>\u0010?J\u001b\u0010@\u001a\u00020\u00002\u0006\u0010\t\u001a\u00020\u0000H\u0087\fø\u0001\u0000¢\u0006\u0004\bA\u0010\u000bJ\u001b\u0010B\u001a\u00020\u00002\u0006\u0010\t\u001a\u00020\u000eH\u0087\nø\u0001\u0000¢\u0006\u0004\bC\u0010\u001dJ\u001b\u0010B\u001a\u00020\u00002\u0006\u0010\t\u001a\u00020\u0011H\u0087\nø\u0001\u0000¢\u0006\u0004\bD\u0010\u001fJ\u001b\u0010B\u001a\u00020\u00002\u0006\u0010\t\u001a\u00020\u0000H\u0087\nø\u0001\u0000¢\u0006\u0004\bE\u0010\u000bJ\u001b\u0010B\u001a\u00020\u00002\u0006\u0010\t\u001a\u00020\u0016H\u0087\nø\u0001\u0000¢\u0006\u0004\bF\u0010\"J\u001b\u0010G\u001a\u00020H2\u0006\u0010\t\u001a\u00020\u0000H\u0087\nø\u0001\u0000¢\u0006\u0004\bI\u0010JJ\u001b\u0010K\u001a\u00020\u00002\u0006\u0010\t\u001a\u00020\u000eH\u0087\nø\u0001\u0000¢\u0006\u0004\bL\u0010\u001dJ\u001b\u0010K\u001a\u00020\u00002\u0006\u0010\t\u001a\u00020\u0011H\u0087\nø\u0001\u0000¢\u0006\u0004\bM\u0010\u001fJ\u001b\u0010K\u001a\u00020\u00002\u0006\u0010\t\u001a\u00020\u0000H\u0087\nø\u0001\u0000¢\u0006\u0004\bN\u0010\u000bJ\u001b\u0010K\u001a\u00020\u00002\u0006\u0010\t\u001a\u00020\u0016H\u0087\nø\u0001\u0000¢\u0006\u0004\bO\u0010\"J\u001e\u0010P\u001a\u00020\u00002\u0006\u0010Q\u001a\u00020\rH\u0087\fø\u0001\u0001ø\u0001\u0000¢\u0006\u0004\bR\u0010\u001fJ\u001e\u0010S\u001a\u00020\u00002\u0006\u0010Q\u001a\u00020\rH\u0087\fø\u0001\u0001ø\u0001\u0000¢\u0006\u0004\bT\u0010\u001fJ\u001b\u0010U\u001a\u00020\u00002\u0006\u0010\t\u001a\u00020\u000eH\u0087\nø\u0001\u0000¢\u0006\u0004\bV\u0010\u001dJ\u001b\u0010U\u001a\u00020\u00002\u0006\u0010\t\u001a\u00020\u0011H\u0087\nø\u0001\u0000¢\u0006\u0004\bW\u0010\u001fJ\u001b\u0010U\u001a\u00020\u00002\u0006\u0010\t\u001a\u00020\u0000H\u0087\nø\u0001\u0000¢\u0006\u0004\bX\u0010\u000bJ\u001b\u0010U\u001a\u00020\u00002\u0006\u0010\t\u001a\u00020\u0016H\u0087\nø\u0001\u0000¢\u0006\u0004\bY\u0010\"J\u0010\u0010Z\u001a\u00020[H\u0087\b¢\u0006\u0004\b\\\u0010]J\u0010\u0010^\u001a\u00020_H\u0087\b¢\u0006\u0004\b`\u0010aJ\u0010\u0010b\u001a\u00020cH\u0087\b¢\u0006\u0004\bd\u0010eJ\u0010\u0010f\u001a\u00020\rH\u0087\b¢\u0006\u0004\bg\u0010/J\u0010\u0010h\u001a\u00020\u0003H\u0087\b¢\u0006\u0004\bi\u0010\u0005J\u0010\u0010j\u001a\u00020kH\u0087\b¢\u0006\u0004\bl\u0010mJ\u000f\u0010n\u001a\u00020oH\u0016¢\u0006\u0004\bp\u0010qJ\u0016\u0010r\u001a\u00020\u000eH\u0087\bø\u0001\u0001ø\u0001\u0000¢\u0006\u0004\bs\u0010]J\u0016\u0010t\u001a\u00020\u0011H\u0087\bø\u0001\u0001ø\u0001\u0000¢\u0006\u0004\bu\u0010/J\u0016\u0010v\u001a\u00020\u0000H\u0087\bø\u0001\u0001ø\u0001\u0000¢\u0006\u0004\bw\u0010\u0005J\u0016\u0010x\u001a\u00020\u0016H\u0087\bø\u0001\u0001ø\u0001\u0000¢\u0006\u0004\by\u0010mJ\u001b\u0010z\u001a\u00020\u00002\u0006\u0010\t\u001a\u00020\u0000H\u0087\fø\u0001\u0000¢\u0006\u0004\b{\u0010\u000bR\u0016\u0010\u0002\u001a\u00020\u00038\u0000X\u0081\u0004¢\u0006\b\n\u0000\u0012\u0004\b\u0006\u0010\u0007\u0088\u0001\u0002\u0092\u0001\u00020\u0003ø\u0001\u0000\u0082\u0002\b\n\u0002\b\u0019\n\u0002\b!¨\u0006}"}, d2 = {"Lkotlin/ULong;", HttpUrl.FRAGMENT_ENCODE_SET, "data", HttpUrl.FRAGMENT_ENCODE_SET, "constructor-impl", "(J)J", "getData$annotations", "()V", "and", "other", "and-VKZWuLQ", "(JJ)J", "compareTo", HttpUrl.FRAGMENT_ENCODE_SET, "Lkotlin/UByte;", "compareTo-7apg3OU", "(JB)I", "Lkotlin/UInt;", "compareTo-WZ4Q5Ns", "(JI)I", "compareTo-VKZWuLQ", "(JJ)I", "Lkotlin/UShort;", "compareTo-xj2QHRw", "(JS)I", "dec", "dec-s-VKNKU", "div", "div-7apg3OU", "(JB)J", "div-WZ4Q5Ns", "(JI)J", "div-VKZWuLQ", "div-xj2QHRw", "(JS)J", "equals", HttpUrl.FRAGMENT_ENCODE_SET, HttpUrl.FRAGMENT_ENCODE_SET, "equals-impl", "(JLjava/lang/Object;)Z", "floorDiv", "floorDiv-7apg3OU", "floorDiv-WZ4Q5Ns", "floorDiv-VKZWuLQ", "floorDiv-xj2QHRw", "hashCode", "hashCode-impl", "(J)I", "inc", "inc-s-VKNKU", "inv", "inv-s-VKNKU", "minus", "minus-7apg3OU", "minus-WZ4Q5Ns", "minus-VKZWuLQ", "minus-xj2QHRw", "mod", "mod-7apg3OU", "(JB)B", "mod-WZ4Q5Ns", "mod-VKZWuLQ", "mod-xj2QHRw", "(JS)S", "or", "or-VKZWuLQ", "plus", "plus-7apg3OU", "plus-WZ4Q5Ns", "plus-VKZWuLQ", "plus-xj2QHRw", "rangeTo", "Lkotlin/ranges/ULongRange;", "rangeTo-VKZWuLQ", "(JJ)Lkotlin/ranges/ULongRange;", "rem", "rem-7apg3OU", "rem-WZ4Q5Ns", "rem-VKZWuLQ", "rem-xj2QHRw", "shl", "bitCount", "shl-s-VKNKU", "shr", "shr-s-VKNKU", "times", "times-7apg3OU", "times-WZ4Q5Ns", "times-VKZWuLQ", "times-xj2QHRw", "toByte", HttpUrl.FRAGMENT_ENCODE_SET, "toByte-impl", "(J)B", "toDouble", HttpUrl.FRAGMENT_ENCODE_SET, "toDouble-impl", "(J)D", "toFloat", HttpUrl.FRAGMENT_ENCODE_SET, "toFloat-impl", "(J)F", "toInt", "toInt-impl", "toLong", "toLong-impl", "toShort", HttpUrl.FRAGMENT_ENCODE_SET, "toShort-impl", "(J)S", "toString", HttpUrl.FRAGMENT_ENCODE_SET, "toString-impl", "(J)Ljava/lang/String;", "toUByte", "toUByte-w2LRezQ", "toUInt", "toUInt-pVg5ArA", "toULong", "toULong-s-VKNKU", "toUShort", "toUShort-Mh2AYeg", "xor", "xor-VKZWuLQ", "Companion", "kotlin-stdlib"}, k = 1, mv = {1, 7, 1}, xi = 48)
@JvmInline
/* loaded from: classes.dex */
public final class ULong implements Comparable<ULong> {
    public static final long MAX_VALUE = -1;
    public static final long MIN_VALUE = 0;
    public static final int SIZE_BITS = 64;
    public static final int SIZE_BYTES = 8;
    private final long data;

    private /* synthetic */ ULong(long data) {
        this.data = data;
    }

    /* renamed from: and-VKZWuLQ */
    private static final long m209andVKZWuLQ(long arg0, long other) {
        return m216constructorimpl(arg0 & other);
    }

    /* renamed from: box-impl */
    public static final /* synthetic */ ULong m210boximpl(long j) {
        return new ULong(j);
    }

    /* renamed from: compareTo-7apg3OU */
    private static final int m211compareTo7apg3OU(long arg0, byte other) {
        return UnsignedKt.ulongCompare(arg0, m216constructorimpl(other & 255));
    }

    /* renamed from: compareTo-VKZWuLQ */
    private int m212compareToVKZWuLQ(long other) {
        return UnsignedKt.ulongCompare(getData(), other);
    }

    /* renamed from: compareTo-VKZWuLQ */
    private static int m213compareToVKZWuLQ(long arg0, long other) {
        return UnsignedKt.ulongCompare(arg0, other);
    }

    /* renamed from: compareTo-WZ4Q5Ns */
    private static final int m214compareToWZ4Q5Ns(long arg0, int other) {
        return UnsignedKt.ulongCompare(arg0, m216constructorimpl(other & 4294967295L));
    }

    /* renamed from: compareTo-xj2QHRw */
    private static final int m215compareToxj2QHRw(long arg0, short other) {
        return UnsignedKt.ulongCompare(arg0, m216constructorimpl(other & WebSocketProtocol.PAYLOAD_SHORT_MAX));
    }

    /* renamed from: constructor-impl */
    public static long m216constructorimpl(long j) {
        return j;
    }

    /* renamed from: dec-s-VKNKU */
    private static final long m217decsVKNKU(long arg0) {
        return m216constructorimpl((-1) + arg0);
    }

    /* renamed from: div-7apg3OU */
    private static final long m218div7apg3OU(long arg0, byte other) {
        return UnsignedKt.m393ulongDivideeb3DHEI(arg0, m216constructorimpl(other & 255));
    }

    /* renamed from: div-VKZWuLQ */
    private static final long m219divVKZWuLQ(long arg0, long other) {
        return UnsignedKt.m393ulongDivideeb3DHEI(arg0, other);
    }

    /* renamed from: div-WZ4Q5Ns */
    private static final long m220divWZ4Q5Ns(long arg0, int other) {
        return UnsignedKt.m393ulongDivideeb3DHEI(arg0, m216constructorimpl(other & 4294967295L));
    }

    /* renamed from: div-xj2QHRw */
    private static final long m221divxj2QHRw(long arg0, short other) {
        return UnsignedKt.m393ulongDivideeb3DHEI(arg0, m216constructorimpl(other & WebSocketProtocol.PAYLOAD_SHORT_MAX));
    }

    /* renamed from: equals-impl */
    public static boolean m222equalsimpl(long j, Object obj) {
        return (obj instanceof ULong) && j == ((ULong) obj).getData();
    }

    /* renamed from: equals-impl0 */
    public static final boolean m223equalsimpl0(long j, long j2) {
        return j == j2;
    }

    /* renamed from: floorDiv-7apg3OU */
    private static final long m224floorDiv7apg3OU(long arg0, byte other) {
        return UnsignedKt.m393ulongDivideeb3DHEI(arg0, m216constructorimpl(other & 255));
    }

    /* renamed from: floorDiv-VKZWuLQ */
    private static final long m225floorDivVKZWuLQ(long arg0, long other) {
        return UnsignedKt.m393ulongDivideeb3DHEI(arg0, other);
    }

    /* renamed from: floorDiv-WZ4Q5Ns */
    private static final long m226floorDivWZ4Q5Ns(long arg0, int other) {
        return UnsignedKt.m393ulongDivideeb3DHEI(arg0, m216constructorimpl(other & 4294967295L));
    }

    /* renamed from: floorDiv-xj2QHRw */
    private static final long m227floorDivxj2QHRw(long arg0, short other) {
        return UnsignedKt.m393ulongDivideeb3DHEI(arg0, m216constructorimpl(other & WebSocketProtocol.PAYLOAD_SHORT_MAX));
    }

    public static /* synthetic */ void getData$annotations() {
    }

    /* renamed from: hashCode-impl */
    public static int m228hashCodeimpl(long j) {
        return (int) ((j >>> 32) ^ j);
    }

    /* renamed from: inc-s-VKNKU */
    private static final long m229incsVKNKU(long arg0) {
        return m216constructorimpl(1 + arg0);
    }

    /* renamed from: inv-s-VKNKU */
    private static final long m230invsVKNKU(long arg0) {
        return m216constructorimpl(~arg0);
    }

    /* renamed from: minus-7apg3OU */
    private static final long m231minus7apg3OU(long arg0, byte other) {
        return m216constructorimpl(arg0 - m216constructorimpl(other & 255));
    }

    /* renamed from: minus-VKZWuLQ */
    private static final long m232minusVKZWuLQ(long arg0, long other) {
        return m216constructorimpl(arg0 - other);
    }

    /* renamed from: minus-WZ4Q5Ns */
    private static final long m233minusWZ4Q5Ns(long arg0, int other) {
        return m216constructorimpl(arg0 - m216constructorimpl(other & 4294967295L));
    }

    /* renamed from: minus-xj2QHRw */
    private static final long m234minusxj2QHRw(long arg0, short other) {
        return m216constructorimpl(arg0 - m216constructorimpl(other & WebSocketProtocol.PAYLOAD_SHORT_MAX));
    }

    /* renamed from: mod-7apg3OU */
    private static final byte m235mod7apg3OU(long arg0, byte other) {
        return UByte.m62constructorimpl((byte) UnsignedKt.m394ulongRemaindereb3DHEI(arg0, m216constructorimpl(other & 255)));
    }

    /* renamed from: mod-VKZWuLQ */
    private static final long m236modVKZWuLQ(long arg0, long other) {
        return UnsignedKt.m394ulongRemaindereb3DHEI(arg0, other);
    }

    /* renamed from: mod-WZ4Q5Ns */
    private static final int m237modWZ4Q5Ns(long arg0, int other) {
        return UInt.m138constructorimpl((int) UnsignedKt.m394ulongRemaindereb3DHEI(arg0, m216constructorimpl(other & 4294967295L)));
    }

    /* renamed from: mod-xj2QHRw */
    private static final short m238modxj2QHRw(long arg0, short other) {
        return UShort.m322constructorimpl((short) UnsignedKt.m394ulongRemaindereb3DHEI(arg0, m216constructorimpl(other & WebSocketProtocol.PAYLOAD_SHORT_MAX)));
    }

    /* renamed from: or-VKZWuLQ */
    private static final long m239orVKZWuLQ(long arg0, long other) {
        return m216constructorimpl(arg0 | other);
    }

    /* renamed from: plus-7apg3OU */
    private static final long m240plus7apg3OU(long arg0, byte other) {
        return m216constructorimpl(m216constructorimpl(other & 255) + arg0);
    }

    /* renamed from: plus-VKZWuLQ */
    private static final long m241plusVKZWuLQ(long arg0, long other) {
        return m216constructorimpl(arg0 + other);
    }

    /* renamed from: plus-WZ4Q5Ns */
    private static final long m242plusWZ4Q5Ns(long arg0, int other) {
        return m216constructorimpl(m216constructorimpl(other & 4294967295L) + arg0);
    }

    /* renamed from: plus-xj2QHRw */
    private static final long m243plusxj2QHRw(long arg0, short other) {
        return m216constructorimpl(m216constructorimpl(other & WebSocketProtocol.PAYLOAD_SHORT_MAX) + arg0);
    }

    /* renamed from: rangeTo-VKZWuLQ */
    private static final ULongRange m244rangeToVKZWuLQ(long arg0, long other) {
        return new ULongRange(arg0, other, null);
    }

    /* renamed from: rem-7apg3OU */
    private static final long m245rem7apg3OU(long arg0, byte other) {
        return UnsignedKt.m394ulongRemaindereb3DHEI(arg0, m216constructorimpl(other & 255));
    }

    /* renamed from: rem-VKZWuLQ */
    private static final long m246remVKZWuLQ(long arg0, long other) {
        return UnsignedKt.m394ulongRemaindereb3DHEI(arg0, other);
    }

    /* renamed from: rem-WZ4Q5Ns */
    private static final long m247remWZ4Q5Ns(long arg0, int other) {
        return UnsignedKt.m394ulongRemaindereb3DHEI(arg0, m216constructorimpl(other & 4294967295L));
    }

    /* renamed from: rem-xj2QHRw */
    private static final long m248remxj2QHRw(long arg0, short other) {
        return UnsignedKt.m394ulongRemaindereb3DHEI(arg0, m216constructorimpl(other & WebSocketProtocol.PAYLOAD_SHORT_MAX));
    }

    /* renamed from: shl-s-VKNKU */
    private static final long m249shlsVKNKU(long arg0, int bitCount) {
        return m216constructorimpl(arg0 << bitCount);
    }

    /* renamed from: shr-s-VKNKU */
    private static final long m250shrsVKNKU(long arg0, int bitCount) {
        return m216constructorimpl(arg0 >>> bitCount);
    }

    /* renamed from: times-7apg3OU */
    private static final long m251times7apg3OU(long arg0, byte other) {
        return m216constructorimpl(m216constructorimpl(other & 255) * arg0);
    }

    /* renamed from: times-VKZWuLQ */
    private static final long m252timesVKZWuLQ(long arg0, long other) {
        return m216constructorimpl(arg0 * other);
    }

    /* renamed from: times-WZ4Q5Ns */
    private static final long m253timesWZ4Q5Ns(long arg0, int other) {
        return m216constructorimpl(m216constructorimpl(other & 4294967295L) * arg0);
    }

    /* renamed from: times-xj2QHRw */
    private static final long m254timesxj2QHRw(long arg0, short other) {
        return m216constructorimpl(m216constructorimpl(other & WebSocketProtocol.PAYLOAD_SHORT_MAX) * arg0);
    }

    /* renamed from: toByte-impl */
    private static final byte m255toByteimpl(long arg0) {
        return (byte) arg0;
    }

    /* renamed from: toDouble-impl */
    private static final double m256toDoubleimpl(long arg0) {
        return UnsignedKt.ulongToDouble(arg0);
    }

    /* renamed from: toFloat-impl */
    private static final float m257toFloatimpl(long arg0) {
        return (float) UnsignedKt.ulongToDouble(arg0);
    }

    /* renamed from: toInt-impl */
    private static final int m258toIntimpl(long arg0) {
        return (int) arg0;
    }

    /* renamed from: toLong-impl */
    private static final long m259toLongimpl(long arg0) {
        return arg0;
    }

    /* renamed from: toShort-impl */
    private static final short m260toShortimpl(long arg0) {
        return (short) arg0;
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
    public static String m261toStringimpl(long j) {
        String ulongToString = UnsignedKt.ulongToString(j);
        Log5ECF72.a(ulongToString);
        LogE84000.a(ulongToString);
        Log229316.a(ulongToString);
        return ulongToString;
    }

    /* renamed from: toUByte-w2LRezQ */
    private static final byte m262toUBytew2LRezQ(long arg0) {
        return UByte.m62constructorimpl((byte) arg0);
    }

    /* renamed from: toUInt-pVg5ArA */
    private static final int m263toUIntpVg5ArA(long arg0) {
        return UInt.m138constructorimpl((int) arg0);
    }

    /* renamed from: toULong-s-VKNKU */
    private static final long m264toULongsVKNKU(long arg0) {
        return arg0;
    }

    /* renamed from: toUShort-Mh2AYeg */
    private static final short m265toUShortMh2AYeg(long arg0) {
        return UShort.m322constructorimpl((short) arg0);
    }

    /* renamed from: xor-VKZWuLQ */
    private static final long m266xorVKZWuLQ(long arg0, long other) {
        return m216constructorimpl(arg0 ^ other);
    }

    @Override // java.lang.Comparable
    public /* bridge */ /* synthetic */ int compareTo(ULong uLong) {
        return UnsignedKt.ulongCompare(getData(), uLong.getData());
    }

    public boolean equals(Object obj) {
        return m222equalsimpl(this.data, obj);
    }

    public int hashCode() {
        return m228hashCodeimpl(this.data);
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
        String m261toStringimpl = m261toStringimpl(this.data);
        Log5ECF72.a(m261toStringimpl);
        LogE84000.a(m261toStringimpl);
        Log229316.a(m261toStringimpl);
        return m261toStringimpl;
    }

    /* renamed from: unbox-impl, reason: from getter */
    public final /* synthetic */ long getData() {
        return this.data;
    }
}
