package okio;

import java.util.List;
import java.util.RandomAccess;
import kotlin.Metadata;
import kotlin.UByte;
import kotlin.collections.AbstractList;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.DefaultConstructorMarker;
import okhttp3.HttpUrl;

/* compiled from: Options.kt */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0011\n\u0000\n\u0002\u0010\u0015\n\u0002\b\u0005\n\u0002\u0010\b\n\u0002\b\b\u0018\u0000 \u00152\b\u0012\u0004\u0012\u00020\u00020\u00012\u00060\u0003j\u0002`\u0004:\u0001\u0015B\u001f\b\u0002\u0012\u000e\u0010\u0005\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\b¢\u0006\u0002\u0010\tJ\u0011\u0010\u0013\u001a\u00020\u00022\u0006\u0010\u0014\u001a\u00020\u000eH\u0096\u0002R\u001e\u0010\u0005\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00020\u0006X\u0080\u0004¢\u0006\n\n\u0002\u0010\f\u001a\u0004\b\n\u0010\u000bR\u0014\u0010\r\u001a\u00020\u000e8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u000f\u0010\u0010R\u0014\u0010\u0007\u001a\u00020\bX\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012¨\u0006\u0016"}, d2 = {"Lokio/Options;", "Lkotlin/collections/AbstractList;", "Lokio/ByteString;", "Ljava/util/RandomAccess;", "Lkotlin/collections/RandomAccess;", "byteStrings", HttpUrl.FRAGMENT_ENCODE_SET, "trie", HttpUrl.FRAGMENT_ENCODE_SET, "([Lokio/ByteString;[I)V", "getByteStrings$okio", "()[Lokio/ByteString;", "[Lokio/ByteString;", "size", HttpUrl.FRAGMENT_ENCODE_SET, "getSize", "()I", "getTrie$okio", "()[I", "get", "index", "Companion", "okio"}, k = 1, mv = {1, 4, 0})
/* loaded from: classes.dex */
public final class Options extends AbstractList<ByteString> implements RandomAccess {

    /* renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private final ByteString[] byteStrings;
    private final int[] trie;

    /* compiled from: Options.kt */
    @Metadata(bv = {1, 0, 3}, d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\t\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0010\u0011\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002JT\u0010\b\u001a\u00020\t2\b\b\u0002\u0010\n\u001a\u00020\u00042\u0006\u0010\u000b\u001a\u00020\u00052\b\b\u0002\u0010\f\u001a\u00020\r2\f\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00100\u000f2\b\b\u0002\u0010\u0011\u001a\u00020\r2\b\b\u0002\u0010\u0012\u001a\u00020\r2\f\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\r0\u000fH\u0002J!\u0010\u0014\u001a\u00020\u00152\u0012\u0010\u000e\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00100\u0016\"\u00020\u0010H\u0007¢\u0006\u0002\u0010\u0017R\u0018\u0010\u0003\u001a\u00020\u0004*\u00020\u00058BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0006\u0010\u0007¨\u0006\u0018"}, d2 = {"Lokio/Options$Companion;", HttpUrl.FRAGMENT_ENCODE_SET, "()V", "intCount", HttpUrl.FRAGMENT_ENCODE_SET, "Lokio/Buffer;", "getIntCount", "(Lokio/Buffer;)J", "buildTrieRecursive", HttpUrl.FRAGMENT_ENCODE_SET, "nodeOffset", "node", "byteStringOffset", HttpUrl.FRAGMENT_ENCODE_SET, "byteStrings", HttpUrl.FRAGMENT_ENCODE_SET, "Lokio/ByteString;", "fromIndex", "toIndex", "indexes", "of", "Lokio/Options;", HttpUrl.FRAGMENT_ENCODE_SET, "([Lokio/ByteString;)Lokio/Options;", "okio"}, k = 1, mv = {1, 4, 0})
    /* loaded from: classes.dex */
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }

        private final void buildTrieRecursive(long nodeOffset, Buffer node, int byteStringOffset, List<? extends ByteString> byteStrings, int fromIndex, int toIndex, List<Integer> indexes) {
            int i;
            ByteString byteString;
            int i2;
            ByteString byteString2;
            int i3;
            Buffer buffer;
            int i4;
            int i5;
            ByteString byteString3;
            int i6;
            List<Integer> list = indexes;
            if (!(fromIndex < toIndex)) {
                throw new IllegalArgumentException("Failed requirement.".toString());
            }
            for (int i7 = fromIndex; i7 < toIndex; i7++) {
                if (!(byteStrings.get(i7).size() >= byteStringOffset)) {
                    throw new IllegalArgumentException("Failed requirement.".toString());
                }
            }
            ByteString byteString4 = byteStrings.get(fromIndex);
            ByteString byteString5 = byteStrings.get(toIndex - 1);
            if (byteStringOffset == byteString4.size()) {
                int intValue = list.get(fromIndex).intValue();
                int i8 = fromIndex + 1;
                i = i8;
                byteString = byteStrings.get(i8);
                i2 = intValue;
            } else {
                i = fromIndex;
                byteString = byteString4;
                i2 = -1;
            }
            if (byteString.getByte(byteStringOffset) == byteString5.getByte(byteStringOffset)) {
                int i9 = i2;
                ByteString byteString6 = byteString;
                int i10 = i;
                int min = Math.min(byteString6.size(), byteString5.size());
                int i11 = 0;
                int i12 = byteStringOffset;
                while (true) {
                    if (i12 >= min) {
                        byteString2 = byteString6;
                        break;
                    }
                    byteString2 = byteString6;
                    if (byteString2.getByte(i12) != byteString5.getByte(i12)) {
                        break;
                    }
                    i11++;
                    i12++;
                    byteString6 = byteString2;
                }
                long intCount = nodeOffset + getIntCount(node) + 2 + i11 + 1;
                node.writeInt(-i11);
                node.writeInt(i9);
                int i13 = byteStringOffset + i11;
                for (int i14 = byteStringOffset; i14 < i13; i14++) {
                    node.writeInt(byteString2.getByte(i14) & UByte.MAX_VALUE);
                }
                if (i10 + 1 == toIndex) {
                    if (!(byteStringOffset + i11 == byteStrings.get(i10).size())) {
                        throw new IllegalStateException("Check failed.".toString());
                    }
                    node.writeInt(indexes.get(i10).intValue());
                    return;
                } else {
                    Buffer buffer2 = new Buffer();
                    node.writeInt(((int) (intCount + getIntCount(buffer2))) * (-1));
                    buildTrieRecursive(intCount, buffer2, byteStringOffset + i11, byteStrings, i10, toIndex, indexes);
                    node.writeAll(buffer2);
                    return;
                }
            }
            int i15 = i + 1;
            int i16 = 1;
            while (i15 < toIndex) {
                int i17 = i15;
                if (byteStrings.get(i17 - 1).getByte(byteStringOffset) != byteStrings.get(i17).getByte(byteStringOffset)) {
                    i16++;
                }
                i15 = i17 + 1;
            }
            long intCount2 = nodeOffset + getIntCount(node) + 2 + (i16 * 2);
            node.writeInt(i16);
            node.writeInt(i2);
            for (int i18 = i; i18 < toIndex; i18++) {
                byte b = byteStrings.get(i18).getByte(byteStringOffset);
                if (i18 == i || b != byteStrings.get(i18 - 1).getByte(byteStringOffset)) {
                    node.writeInt(255 & b);
                }
            }
            Buffer buffer3 = new Buffer();
            int i19 = i;
            while (i19 < toIndex) {
                byte b2 = byteStrings.get(i19).getByte(byteStringOffset);
                int i20 = toIndex;
                int i21 = i19 + 1;
                while (true) {
                    if (i21 >= toIndex) {
                        i21 = i20;
                        break;
                    }
                    int i22 = i20;
                    if (b2 != byteStrings.get(i21).getByte(byteStringOffset)) {
                        break;
                    }
                    i21++;
                    i20 = i22;
                }
                if (i19 + 1 == i21 && byteStringOffset + 1 == byteStrings.get(i19).size()) {
                    node.writeInt(list.get(i19).intValue());
                    i3 = i21;
                    buffer = buffer3;
                    i4 = i16;
                    i5 = i2;
                    byteString3 = byteString;
                    i6 = i;
                    i19 = i3;
                    i = i6;
                    i2 = i5;
                    i16 = i4;
                    buffer3 = buffer;
                    byteString = byteString3;
                    list = indexes;
                }
                node.writeInt(((int) (intCount2 + getIntCount(buffer3))) * (-1));
                i3 = i21;
                buffer = buffer3;
                i4 = i16;
                i5 = i2;
                byteString3 = byteString;
                i6 = i;
                buildTrieRecursive(intCount2, buffer3, byteStringOffset + 1, byteStrings, i19, i3, indexes);
                i19 = i3;
                i = i6;
                i2 = i5;
                i16 = i4;
                buffer3 = buffer;
                byteString = byteString3;
                list = indexes;
            }
            node.writeAll(buffer3);
        }

        static /* synthetic */ void buildTrieRecursive$default(Companion companion, long j, Buffer buffer, int i, List list, int i2, int i3, List list2, int i4, Object obj) {
            companion.buildTrieRecursive((i4 & 1) != 0 ? 0L : j, buffer, (i4 & 4) != 0 ? 0 : i, list, (i4 & 16) != 0 ? 0 : i2, (i4 & 32) != 0 ? list.size() : i3, list2);
        }

        private final long getIntCount(Buffer $this$intCount) {
            return $this$intCount.size() / 4;
        }

        /* JADX WARN: Code restructure failed: missing block: B:48:0x0116, code lost:
        
            continue;
         */
        @kotlin.jvm.JvmStatic
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct add '--show-bad-code' argument
        */
        public final okio.Options of(okio.ByteString... r22) {
            /*
                Method dump skipped, instructions count: 392
                To view this dump add '--comments-level debug' option
            */
            throw new UnsupportedOperationException("Method not decompiled: okio.Options.Companion.of(okio.ByteString[]):okio.Options");
        }
    }

    private Options(ByteString[] byteStrings, int[] trie) {
        this.byteStrings = byteStrings;
        this.trie = trie;
    }

    public /* synthetic */ Options(ByteString[] byteStrings, int[] trie, DefaultConstructorMarker $constructor_marker) {
        this(byteStrings, trie);
    }

    @JvmStatic
    public static final Options of(ByteString... byteStringArr) {
        return INSTANCE.of(byteStringArr);
    }

    @Override // kotlin.collections.AbstractCollection, java.util.Collection
    public final /* bridge */ boolean contains(Object obj) {
        if (obj instanceof ByteString) {
            return contains((ByteString) obj);
        }
        return false;
    }

    public /* bridge */ boolean contains(ByteString byteString) {
        return super.contains((Options) byteString);
    }

    @Override // kotlin.collections.AbstractList, java.util.List
    public ByteString get(int index) {
        return this.byteStrings[index];
    }

    /* renamed from: getByteStrings$okio, reason: from getter */
    public final ByteString[] getByteStrings() {
        return this.byteStrings;
    }

    @Override // kotlin.collections.AbstractList, kotlin.collections.AbstractCollection
    /* renamed from: getSize */
    public int get_size() {
        return this.byteStrings.length;
    }

    /* renamed from: getTrie$okio, reason: from getter */
    public final int[] getTrie() {
        return this.trie;
    }

    @Override // kotlin.collections.AbstractList, java.util.List
    public final /* bridge */ int indexOf(Object obj) {
        if (obj instanceof ByteString) {
            return indexOf((ByteString) obj);
        }
        return -1;
    }

    public /* bridge */ int indexOf(ByteString byteString) {
        return super.indexOf((Options) byteString);
    }

    @Override // kotlin.collections.AbstractList, java.util.List
    public final /* bridge */ int lastIndexOf(Object obj) {
        if (obj instanceof ByteString) {
            return lastIndexOf((ByteString) obj);
        }
        return -1;
    }

    public /* bridge */ int lastIndexOf(ByteString byteString) {
        return super.lastIndexOf((Options) byteString);
    }
}
