package kotlinx.coroutines.debug.internal;

import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.concurrent.atomic.AtomicReferenceArray;
import kotlin.KotlinNothingValueException;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.AbstractMutableMap;
import kotlin.collections.AbstractMutableSet;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.markers.KMutableIterator;
import kotlin.jvm.internal.markers.KMutableMap;
import kotlin.ranges.RangesKt;
import kotlinx.coroutines.debug.internal.ConcurrentWeakMap;
import kotlinx.coroutines.internal.Symbol;
import okhttp3.HttpUrl;

/* compiled from: ConcurrentWeakMap.kt */
@Metadata(d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u000e\n\u0002\u0010#\n\u0002\u0010'\n\u0002\b\u0005\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\b\u0000\u0018\u0000*\b\b\u0000\u0010\u0002*\u00020\u0001*\b\b\u0001\u0010\u0003*\u00020\u00012\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010*:\u0003&'(B\u0011\u0012\b\b\u0002\u0010\u0005\u001a\u00020\u0004¢\u0006\u0004\b\u0006\u0010\u0007J\u001b\u0010\u000b\u001a\u00020\n2\n\u0010\t\u001a\u0006\u0012\u0002\b\u00030\bH\u0002¢\u0006\u0004\b\u000b\u0010\fJ\u000f\u0010\r\u001a\u00020\nH\u0016¢\u0006\u0004\b\r\u0010\u000eJ\u000f\u0010\u000f\u001a\u00020\nH\u0002¢\u0006\u0004\b\u000f\u0010\u000eJ\u001a\u0010\u0011\u001a\u0004\u0018\u00018\u00012\u0006\u0010\u0010\u001a\u00028\u0000H\u0096\u0002¢\u0006\u0004\b\u0011\u0010\u0012J!\u0010\u0014\u001a\u0004\u0018\u00018\u00012\u0006\u0010\u0010\u001a\u00028\u00002\u0006\u0010\u0013\u001a\u00028\u0001H\u0016¢\u0006\u0004\b\u0014\u0010\u0015J#\u0010\u0016\u001a\u0004\u0018\u00018\u00012\u0006\u0010\u0010\u001a\u00028\u00002\b\u0010\u0013\u001a\u0004\u0018\u00018\u0001H\u0002¢\u0006\u0004\b\u0016\u0010\u0015J\u0019\u0010\u0017\u001a\u0004\u0018\u00018\u00012\u0006\u0010\u0010\u001a\u00028\u0000H\u0016¢\u0006\u0004\b\u0017\u0010\u0012J\r\u0010\u0018\u001a\u00020\n¢\u0006\u0004\b\u0018\u0010\u000eR&\u0010\u001d\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010\u001a0\u00198VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u001b\u0010\u001cR\u001a\u0010\u001f\u001a\b\u0012\u0004\u0012\u00028\u00000\u00198VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u001e\u0010\u001cR\u0014\u0010#\u001a\u00020 8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b!\u0010\"R\u001c\u0010\u0005\u001a\n\u0012\u0004\u0012\u00028\u0000\u0018\u00010$8\u0002X\u0082\u0004¢\u0006\u0006\n\u0004\b\u0005\u0010%¨\u0006)"}, d2 = {"Lkotlinx/coroutines/debug/internal/ConcurrentWeakMap;", HttpUrl.FRAGMENT_ENCODE_SET, "K", "V", HttpUrl.FRAGMENT_ENCODE_SET, "weakRefQueue", "<init>", "(Z)V", "Lkotlinx/coroutines/debug/internal/HashedWeakRef;", "w", HttpUrl.FRAGMENT_ENCODE_SET, "cleanWeakRef", "(Lkotlinx/coroutines/debug/internal/HashedWeakRef;)V", "clear", "()V", "decrementSize", "key", "get", "(Ljava/lang/Object;)Ljava/lang/Object;", "value", "put", "(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;", "putSynchronized", "remove", "runWeakRefQueueCleaningLoopUntilInterrupted", HttpUrl.FRAGMENT_ENCODE_SET, HttpUrl.FRAGMENT_ENCODE_SET, "getEntries", "()Ljava/util/Set;", "entries", "getKeys", "keys", HttpUrl.FRAGMENT_ENCODE_SET, "getSize", "()I", "size", "Ljava/lang/ref/ReferenceQueue;", "Ljava/lang/ref/ReferenceQueue;", "Core", "Entry", "KeyValueSet", "kotlinx-coroutines-core", "Lkotlin/collections/AbstractMutableMap;"}, k = 1, mv = {1, 6, 0}, xi = 48)
/* loaded from: classes.dex */
public final class ConcurrentWeakMap<K, V> extends AbstractMutableMap<K, V> {
    private static final /* synthetic */ AtomicIntegerFieldUpdater _size$FU = AtomicIntegerFieldUpdater.newUpdater(ConcurrentWeakMap.class, "_size");
    private volatile /* synthetic */ int _size;
    volatile /* synthetic */ Object core;
    private final ReferenceQueue<K> weakRefQueue;

    /* JADX INFO: Access modifiers changed from: private */
    /* compiled from: ConcurrentWeakMap.kt */
    @Metadata(d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010)\n\u0002\b\u0004\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\t\b\u0082\u0004\u0018\u00002\u00020\u0018:\u0001#B\u000f\u0012\u0006\u0010\u0002\u001a\u00020\u0001¢\u0006\u0004\b\u0003\u0010\u0004J\u0019\u0010\b\u001a\u00020\u00072\n\u0010\u0006\u001a\u0006\u0012\u0002\b\u00030\u0005¢\u0006\u0004\b\b\u0010\tJ\u0017\u0010\u000b\u001a\u0004\u0018\u00018\u00012\u0006\u0010\n\u001a\u00028\u0000¢\u0006\u0004\b\u000b\u0010\fJ\u0017\u0010\u000e\u001a\u00020\u00012\u0006\u0010\r\u001a\u00020\u0001H\u0002¢\u0006\u0004\b\u000e\u0010\u000fJ3\u0010\u0014\u001a\b\u0012\u0004\u0012\u00028\u00020\u0013\"\u0004\b\u0002\u0010\u00102\u0018\u0010\u0012\u001a\u0014\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u0001\u0012\u0004\u0012\u00028\u00020\u0011¢\u0006\u0004\b\u0014\u0010\u0015J3\u0010\u0019\u001a\u0004\u0018\u00010\u00182\u0006\u0010\n\u001a\u00028\u00002\b\u0010\u0016\u001a\u0004\u0018\u00018\u00012\u0010\b\u0002\u0010\u0017\u001a\n\u0012\u0004\u0012\u00028\u0000\u0018\u00010\u0005¢\u0006\u0004\b\u0019\u0010\u001aJ\u001d\u0010\u001c\u001a\u00120\u0000R\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010\u001b¢\u0006\u0004\b\u001c\u0010\u001dJ\u0017\u0010\u001e\u001a\u00020\u00072\u0006\u0010\u000e\u001a\u00020\u0001H\u0002¢\u0006\u0004\b\u001e\u0010\u001fR\u0014\u0010\u0002\u001a\u00020\u00018\u0002X\u0082\u0004¢\u0006\u0006\n\u0004\b\u0002\u0010 R\u0014\u0010!\u001a\u00020\u00018\u0002X\u0082\u0004¢\u0006\u0006\n\u0004\b!\u0010 R\u0014\u0010\"\u001a\u00020\u00018\u0002X\u0082\u0004¢\u0006\u0006\n\u0004\b\"\u0010 ¨\u0006$"}, d2 = {"Lkotlinx/coroutines/debug/internal/ConcurrentWeakMap$Core;", HttpUrl.FRAGMENT_ENCODE_SET, "allocated", "<init>", "(Lkotlinx/coroutines/debug/internal/ConcurrentWeakMap;I)V", "Lkotlinx/coroutines/debug/internal/HashedWeakRef;", "weakRef", HttpUrl.FRAGMENT_ENCODE_SET, "cleanWeakRef", "(Lkotlinx/coroutines/debug/internal/HashedWeakRef;)V", "key", "getImpl", "(Ljava/lang/Object;)Ljava/lang/Object;", "hash", "index", "(I)I", "E", "Lkotlin/Function2;", "factory", HttpUrl.FRAGMENT_ENCODE_SET, "keyValueIterator", "(Lkotlin/jvm/functions/Function2;)Ljava/util/Iterator;", "value", "weakKey0", HttpUrl.FRAGMENT_ENCODE_SET, "putImpl", "(Ljava/lang/Object;Ljava/lang/Object;Lkotlinx/coroutines/debug/internal/HashedWeakRef;)Ljava/lang/Object;", "Lkotlinx/coroutines/debug/internal/ConcurrentWeakMap;", "rehash", "()Lkotlinx/coroutines/debug/internal/ConcurrentWeakMap$Core;", "removeCleanedAt", "(I)V", "I", "shift", "threshold", "KeyValueIterator", "kotlinx-coroutines-core"}, k = 1, mv = {1, 6, 0}, xi = 48)
    /* loaded from: classes.dex */
    public final class Core {
        private static final /* synthetic */ AtomicIntegerFieldUpdater load$FU = AtomicIntegerFieldUpdater.newUpdater(Core.class, "load");
        private final int allocated;
        /* synthetic */ AtomicReferenceArray keys;
        private volatile /* synthetic */ int load = 0;
        private final int shift;
        private final int threshold;
        /* synthetic */ AtomicReferenceArray values;

        /* JADX INFO: Access modifiers changed from: private */
        /* compiled from: ConcurrentWeakMap.kt */
        @Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010)\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u0001\n\u0000\b\u0082\u0004\u0018\u0000*\u0004\b\u0002\u0010\u00012\b\u0012\u0004\u0012\u0002H\u00010\u0002B\u001f\u0012\u0018\u0010\u0003\u001a\u0014\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u0001\u0012\u0004\u0012\u00028\u00020\u0004¢\u0006\u0002\u0010\u0005J\b\u0010\u000b\u001a\u00020\fH\u0002J\t\u0010\r\u001a\u00020\u000eH\u0096\u0002J\u000e\u0010\u000f\u001a\u00028\u0002H\u0096\u0002¢\u0006\u0002\u0010\u0010J\b\u0010\u0011\u001a\u00020\u0012H\u0016R \u0010\u0003\u001a\u0014\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u0001\u0012\u0004\u0012\u00028\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\b\u001a\u00028\u0000X\u0082.¢\u0006\u0004\n\u0002\u0010\tR\u0010\u0010\n\u001a\u00028\u0001X\u0082.¢\u0006\u0004\n\u0002\u0010\t¨\u0006\u0013"}, d2 = {"Lkotlinx/coroutines/debug/internal/ConcurrentWeakMap$Core$KeyValueIterator;", "E", HttpUrl.FRAGMENT_ENCODE_SET, "factory", "Lkotlin/Function2;", "(Lkotlinx/coroutines/debug/internal/ConcurrentWeakMap$Core;Lkotlin/jvm/functions/Function2;)V", "index", HttpUrl.FRAGMENT_ENCODE_SET, "key", "Ljava/lang/Object;", "value", "findNext", HttpUrl.FRAGMENT_ENCODE_SET, "hasNext", HttpUrl.FRAGMENT_ENCODE_SET, "next", "()Ljava/lang/Object;", "remove", HttpUrl.FRAGMENT_ENCODE_SET, "kotlinx-coroutines-core"}, k = 1, mv = {1, 6, 0}, xi = 48)
        /* loaded from: classes.dex */
        public final class KeyValueIterator<E> implements Iterator<E>, KMutableIterator {
            private final Function2<K, V, E> factory;
            private int index = -1;
            private K key;
            private V value;

            /* JADX WARN: Multi-variable type inference failed */
            public KeyValueIterator(Function2<? super K, ? super V, ? extends E> function2) {
                this.factory = function2;
                findNext();
            }

            private final void findNext() {
                while (true) {
                    int i = this.index + 1;
                    this.index = i;
                    if (i >= ((Core) Core.this).allocated) {
                        return;
                    }
                    HashedWeakRef hashedWeakRef = (HashedWeakRef) Core.this.keys.get(this.index);
                    K k = hashedWeakRef == null ? null : (K) hashedWeakRef.get();
                    if (k != null) {
                        this.key = k;
                        Object obj = (V) Core.this.values.get(this.index);
                        if (obj instanceof Marked) {
                            obj = (V) ((Marked) obj).ref;
                        }
                        if (obj != null) {
                            this.value = (V) obj;
                            return;
                        }
                    }
                }
            }

            @Override // java.util.Iterator
            public boolean hasNext() {
                return this.index < ((Core) Core.this).allocated;
            }

            @Override // java.util.Iterator
            public E next() {
                if (this.index >= ((Core) Core.this).allocated) {
                    throw new NoSuchElementException();
                }
                Function2<K, V, E> function2 = this.factory;
                K k = this.key;
                if (k == false) {
                    Intrinsics.throwUninitializedPropertyAccessException("key");
                    k = (K) Unit.INSTANCE;
                }
                V v = this.value;
                if (v == false) {
                    Intrinsics.throwUninitializedPropertyAccessException("value");
                    v = (V) Unit.INSTANCE;
                }
                E e = (E) function2.invoke(k, v);
                findNext();
                return e;
            }

            @Override // java.util.Iterator
            public Void remove() {
                ConcurrentWeakMapKt.noImpl();
                throw new KotlinNothingValueException();
            }
        }

        public Core(int allocated) {
            this.allocated = allocated;
            this.shift = Integer.numberOfLeadingZeros(allocated) + 1;
            this.threshold = (allocated * 2) / 3;
            this.keys = new AtomicReferenceArray(allocated);
            this.values = new AtomicReferenceArray(allocated);
        }

        private final int index(int hash) {
            return ((-1640531527) * hash) >>> this.shift;
        }

        /* JADX WARN: Multi-variable type inference failed */
        public static /* synthetic */ Object putImpl$default(Core core, Object obj, Object obj2, HashedWeakRef hashedWeakRef, int i, Object obj3) {
            if ((i & 4) != 0) {
                hashedWeakRef = null;
            }
            return core.putImpl(obj, obj2, hashedWeakRef);
        }

        private final void removeCleanedAt(int index) {
            Object obj;
            do {
                obj = this.values.get(index);
                if (obj == null || (obj instanceof Marked)) {
                    return;
                }
            } while (!ConcurrentWeakMap$Core$$ExternalSyntheticBackportWithForwarding0.m(this.values, index, obj, null));
            ConcurrentWeakMap.this.decrementSize();
        }

        public final void cleanWeakRef(HashedWeakRef<?> weakRef) {
            int index = index(weakRef.hash);
            while (true) {
                HashedWeakRef<?> hashedWeakRef = (HashedWeakRef) this.keys.get(index);
                if (hashedWeakRef == null) {
                    return;
                }
                if (hashedWeakRef == weakRef) {
                    removeCleanedAt(index);
                    return;
                } else {
                    if (index == 0) {
                        index = this.allocated;
                    }
                    index--;
                }
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        public final V getImpl(K key) {
            int index = index(key.hashCode());
            while (true) {
                HashedWeakRef hashedWeakRef = (HashedWeakRef) this.keys.get(index);
                if (hashedWeakRef == null) {
                    return null;
                }
                Object obj = hashedWeakRef.get();
                if (Intrinsics.areEqual(key, obj)) {
                    V v = (V) this.values.get(index);
                    return v instanceof Marked ? (V) ((Marked) v).ref : v;
                }
                if (obj == null) {
                    removeCleanedAt(index);
                }
                if (index == 0) {
                    index = this.allocated;
                }
                index--;
            }
        }

        public final <E> Iterator<E> keyValueIterator(Function2<? super K, ? super V, ? extends E> factory) {
            return new KeyValueIterator(factory);
        }

        /* JADX WARN: Code restructure failed: missing block: B:19:0x0066, code lost:
        
            r1 = r11.values.get(r0);
         */
        /* JADX WARN: Code restructure failed: missing block: B:20:0x006f, code lost:
        
            if ((r1 instanceof kotlinx.coroutines.debug.internal.Marked) == false) goto L34;
         */
        /* JADX WARN: Code restructure failed: missing block: B:22:0x007c, code lost:
        
            if (kotlinx.coroutines.debug.internal.ConcurrentWeakMap$Core$$ExternalSyntheticBackportWithForwarding0.m(r11.values, r0, r1, r13) == false) goto L53;
         */
        /* JADX WARN: Code restructure failed: missing block: B:24:0x007e, code lost:
        
            return r1;
         */
        /* JADX WARN: Code restructure failed: missing block: B:28:0x0071, code lost:
        
            r2 = kotlinx.coroutines.debug.internal.ConcurrentWeakMapKt.REHASH;
         */
        /* JADX WARN: Code restructure failed: missing block: B:29:0x0075, code lost:
        
            return r2;
         */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct add '--show-bad-code' argument
        */
        public final java.lang.Object putImpl(K r12, V r13, kotlinx.coroutines.debug.internal.HashedWeakRef<K> r14) {
            /*
                r11 = this;
                int r0 = r12.hashCode()
                int r0 = r11.index(r0)
                r1 = 0
                r2 = r14
            La:
                java.util.concurrent.atomic.AtomicReferenceArray r3 = r11.keys
                java.lang.Object r3 = r3.get(r0)
                kotlinx.coroutines.debug.internal.HashedWeakRef r3 = (kotlinx.coroutines.debug.internal.HashedWeakRef) r3
                if (r3 != 0) goto L52
                r4 = 0
                if (r13 != 0) goto L19
                return r4
            L19:
                if (r1 != 0) goto L38
                r5 = r11
                r6 = 0
            L1d:
                int r7 = r5.load
                r8 = r7
                r9 = 0
                int r10 = r11.threshold
                if (r8 < r10) goto L2b
                kotlinx.coroutines.internal.Symbol r4 = kotlinx.coroutines.debug.internal.ConcurrentWeakMapKt.access$getREHASH$p()
                return r4
            L2b:
                int r8 = r8 + 1
                java.util.concurrent.atomic.AtomicIntegerFieldUpdater r9 = kotlinx.coroutines.debug.internal.ConcurrentWeakMap.Core.load$FU
                boolean r9 = r9.compareAndSet(r5, r7, r8)
                if (r9 == 0) goto L37
                r1 = 1
                goto L38
            L37:
                goto L1d
            L38:
                if (r2 != 0) goto L46
                kotlinx.coroutines.debug.internal.HashedWeakRef r5 = new kotlinx.coroutines.debug.internal.HashedWeakRef
                kotlinx.coroutines.debug.internal.ConcurrentWeakMap<K, V> r6 = kotlinx.coroutines.debug.internal.ConcurrentWeakMap.this
                java.lang.ref.ReferenceQueue r6 = kotlinx.coroutines.debug.internal.ConcurrentWeakMap.access$getWeakRefQueue$p(r6)
                r5.<init>(r12, r6)
                r2 = r5
            L46:
                java.util.concurrent.atomic.AtomicReferenceArray r5 = r11.keys
                boolean r4 = kotlinx.coroutines.debug.internal.ConcurrentWeakMap$Core$$ExternalSyntheticBackportWithForwarding0.m(r5, r0, r4, r2)
                if (r4 == 0) goto L51
                r5 = r1
                r6 = r2
                goto L65
            L51:
                goto La
            L52:
                java.lang.Object r4 = r3.get()
                boolean r5 = kotlin.jvm.internal.Intrinsics.areEqual(r12, r4)
                if (r5 == 0) goto L7f
                if (r1 == 0) goto L63
                java.util.concurrent.atomic.AtomicIntegerFieldUpdater r5 = kotlinx.coroutines.debug.internal.ConcurrentWeakMap.Core.load$FU
                r5.decrementAndGet(r11)
            L63:
                r5 = r1
                r6 = r2
            L65:
                r1 = 0
            L66:
                java.util.concurrent.atomic.AtomicReferenceArray r2 = r11.values
                java.lang.Object r1 = r2.get(r0)
                boolean r2 = r1 instanceof kotlinx.coroutines.debug.internal.Marked
                if (r2 == 0) goto L76
                kotlinx.coroutines.internal.Symbol r2 = kotlinx.coroutines.debug.internal.ConcurrentWeakMapKt.access$getREHASH$p()
                return r2
            L76:
                java.util.concurrent.atomic.AtomicReferenceArray r2 = r11.values
                boolean r2 = kotlinx.coroutines.debug.internal.ConcurrentWeakMap$Core$$ExternalSyntheticBackportWithForwarding0.m(r2, r0, r1, r13)
                if (r2 == 0) goto L66
                return r1
            L7f:
                if (r4 != 0) goto L84
                r11.removeCleanedAt(r0)
            L84:
                if (r0 != 0) goto L88
                int r0 = r11.allocated
            L88:
                int r0 = r0 + (-1)
                goto La
            */
            throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.debug.internal.ConcurrentWeakMap.Core.putImpl(java.lang.Object, java.lang.Object, kotlinx.coroutines.debug.internal.HashedWeakRef):java.lang.Object");
        }

        /* JADX WARN: Multi-variable type inference failed */
        public final ConcurrentWeakMap<K, V>.Core rehash() {
            Object obj;
            Symbol symbol;
            Marked mark;
            while (true) {
                ConcurrentWeakMap<K, V>.Core core = (ConcurrentWeakMap<K, V>.Core) new Core(Integer.highestOneBit(RangesKt.coerceAtLeast(ConcurrentWeakMap.this.size(), 4)) * 4);
                int i = this.allocated;
                int i2 = 0;
                while (i2 < i) {
                    int i3 = i2;
                    i2++;
                    HashedWeakRef hashedWeakRef = (HashedWeakRef) this.keys.get(i3);
                    Object obj2 = hashedWeakRef == null ? null : hashedWeakRef.get();
                    if (hashedWeakRef != null && obj2 == null) {
                        removeCleanedAt(i3);
                    }
                    while (true) {
                        obj = this.values.get(i3);
                        if (obj instanceof Marked) {
                            obj = ((Marked) obj).ref;
                            break;
                        }
                        AtomicReferenceArray atomicReferenceArray = this.values;
                        mark = ConcurrentWeakMapKt.mark(obj);
                        if (ConcurrentWeakMap$Core$$ExternalSyntheticBackportWithForwarding0.m(atomicReferenceArray, i3, obj, mark)) {
                            break;
                        }
                    }
                    if (obj2 != null && obj != null) {
                        Object putImpl = core.putImpl(obj2, obj, hashedWeakRef);
                        symbol = ConcurrentWeakMapKt.REHASH;
                        if (putImpl == symbol) {
                            break;
                        }
                        if (!(putImpl == null)) {
                            throw new AssertionError("Assertion failed");
                        }
                    }
                }
                return core;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* compiled from: ConcurrentWeakMap.kt */
    @Metadata(d1 = {"\u0000\u0010\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010'\n\u0002\b\u000b\b\u0002\u0018\u0000*\u0004\b\u0002\u0010\u0001*\u0004\b\u0003\u0010\u00022\u000e\u0012\u0004\u0012\u0002H\u0001\u0012\u0004\u0012\u0002H\u00020\u0003B\u0015\u0012\u0006\u0010\u0004\u001a\u00028\u0002\u0012\u0006\u0010\u0005\u001a\u00028\u0003¢\u0006\u0002\u0010\u0006J\u0015\u0010\u000b\u001a\u00028\u00032\u0006\u0010\f\u001a\u00028\u0003H\u0016¢\u0006\u0002\u0010\rR\u0016\u0010\u0004\u001a\u00028\u0002X\u0096\u0004¢\u0006\n\n\u0002\u0010\t\u001a\u0004\b\u0007\u0010\bR\u0016\u0010\u0005\u001a\u00028\u0003X\u0096\u0004¢\u0006\n\n\u0002\u0010\t\u001a\u0004\b\n\u0010\b¨\u0006\u000e"}, d2 = {"Lkotlinx/coroutines/debug/internal/ConcurrentWeakMap$Entry;", "K", "V", HttpUrl.FRAGMENT_ENCODE_SET, "key", "value", "(Ljava/lang/Object;Ljava/lang/Object;)V", "getKey", "()Ljava/lang/Object;", "Ljava/lang/Object;", "getValue", "setValue", "newValue", "(Ljava/lang/Object;)Ljava/lang/Object;", "kotlinx-coroutines-core"}, k = 1, mv = {1, 6, 0}, xi = 48)
    /* loaded from: classes.dex */
    public static final class Entry<K, V> implements Map.Entry<K, V>, KMutableMap.Entry {
        private final K key;
        private final V value;

        public Entry(K k, V v) {
            this.key = k;
            this.value = v;
        }

        @Override // java.util.Map.Entry
        public K getKey() {
            return this.key;
        }

        @Override // java.util.Map.Entry
        public V getValue() {
            return this.value;
        }

        @Override // java.util.Map.Entry
        public V setValue(V newValue) {
            ConcurrentWeakMapKt.noImpl();
            throw new KotlinNothingValueException();
        }
    }

    /* compiled from: ConcurrentWeakMap.kt */
    @Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010)\n\u0000\b\u0082\u0004\u0018\u0000*\u0004\b\u0002\u0010\u00012\b\u0012\u0004\u0012\u0002H\u00010\u0002B\u001f\u0012\u0018\u0010\u0003\u001a\u0014\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u0001\u0012\u0004\u0012\u00028\u00020\u0004¢\u0006\u0002\u0010\u0005J\u0015\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00028\u0002H\u0016¢\u0006\u0002\u0010\rJ\u000f\u0010\u000e\u001a\b\u0012\u0004\u0012\u00028\u00020\u000fH\u0096\u0002R \u0010\u0003\u001a\u0014\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u0001\u0012\u0004\u0012\u00028\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0006\u001a\u00020\u00078VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\b\u0010\t¨\u0006\u0010"}, d2 = {"Lkotlinx/coroutines/debug/internal/ConcurrentWeakMap$KeyValueSet;", "E", "Lkotlin/collections/AbstractMutableSet;", "factory", "Lkotlin/Function2;", "(Lkotlinx/coroutines/debug/internal/ConcurrentWeakMap;Lkotlin/jvm/functions/Function2;)V", "size", HttpUrl.FRAGMENT_ENCODE_SET, "getSize", "()I", "add", HttpUrl.FRAGMENT_ENCODE_SET, "element", "(Ljava/lang/Object;)Z", "iterator", HttpUrl.FRAGMENT_ENCODE_SET, "kotlinx-coroutines-core"}, k = 1, mv = {1, 6, 0}, xi = 48)
    /* loaded from: classes.dex */
    private final class KeyValueSet<E> extends AbstractMutableSet<E> {
        private final Function2<K, V, E> factory;

        /* JADX WARN: Multi-variable type inference failed */
        public KeyValueSet(Function2<? super K, ? super V, ? extends E> function2) {
            this.factory = function2;
        }

        @Override // kotlin.collections.AbstractMutableSet, java.util.AbstractCollection, java.util.Collection, java.util.Set
        public boolean add(E element) {
            ConcurrentWeakMapKt.noImpl();
            throw new KotlinNothingValueException();
        }

        @Override // kotlin.collections.AbstractMutableSet
        public int getSize() {
            return ConcurrentWeakMap.this.size();
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.Set
        public Iterator<E> iterator() {
            return ((Core) ConcurrentWeakMap.this.core).keyValueIterator(this.factory);
        }
    }

    public ConcurrentWeakMap() {
        this(false, 1, null);
    }

    public ConcurrentWeakMap(boolean weakRefQueue) {
        this._size = 0;
        this.core = new Core(16);
        this.weakRefQueue = weakRefQueue ? new ReferenceQueue<>() : null;
    }

    public /* synthetic */ ConcurrentWeakMap(boolean z, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? false : z);
    }

    private final void cleanWeakRef(HashedWeakRef<?> w) {
        ((Core) this.core).cleanWeakRef(w);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void decrementSize() {
        _size$FU.decrementAndGet(this);
    }

    private final synchronized V putSynchronized(K key, V value) {
        V v;
        Symbol symbol;
        Core core = (Core) this.core;
        while (true) {
            v = (V) Core.putImpl$default(core, key, value, null, 4, null);
            symbol = ConcurrentWeakMapKt.REHASH;
            if (v == symbol) {
                core = core.rehash();
                this.core = core;
            }
        }
        return v;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public void clear() {
        Iterator<K> it = keySet().iterator();
        while (it.hasNext()) {
            remove(it.next());
        }
    }

    @Override // java.util.AbstractMap, java.util.Map
    public V get(Object key) {
        if (key == null) {
            return null;
        }
        return (V) ((Core) this.core).getImpl(key);
    }

    @Override // kotlin.collections.AbstractMutableMap
    public Set<Map.Entry<K, V>> getEntries() {
        return new KeyValueSet(new Function2<K, V, Map.Entry<K, V>>() { // from class: kotlinx.coroutines.debug.internal.ConcurrentWeakMap$entries$1
            /* JADX WARN: Multi-variable type inference failed */
            @Override // kotlin.jvm.functions.Function2
            public /* bridge */ /* synthetic */ Object invoke(Object obj, Object obj2) {
                return invoke((ConcurrentWeakMap$entries$1<K, V>) obj, obj2);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Map.Entry<K, V> invoke(K k, V v) {
                return new ConcurrentWeakMap.Entry(k, v);
            }
        });
    }

    @Override // kotlin.collections.AbstractMutableMap
    public Set<K> getKeys() {
        return new KeyValueSet(new Function2<K, V, K>() { // from class: kotlinx.coroutines.debug.internal.ConcurrentWeakMap$keys$1
            @Override // kotlin.jvm.functions.Function2
            public final K invoke(K k, V v) {
                return k;
            }
        });
    }

    @Override // kotlin.collections.AbstractMutableMap
    /* renamed from: getSize, reason: from getter */
    public int get_size() {
        return this._size;
    }

    @Override // kotlin.collections.AbstractMutableMap, java.util.AbstractMap, java.util.Map
    public V put(K key, V value) {
        Symbol symbol;
        V v = (V) Core.putImpl$default((Core) this.core, key, value, null, 4, null);
        symbol = ConcurrentWeakMapKt.REHASH;
        if (v == symbol) {
            v = putSynchronized(key, value);
        }
        if (v == null) {
            _size$FU.incrementAndGet(this);
        }
        return v;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // java.util.AbstractMap, java.util.Map
    public V remove(Object key) {
        Symbol symbol;
        if (key == 0) {
            return null;
        }
        V v = (V) Core.putImpl$default((Core) this.core, key, null, null, 4, null);
        symbol = ConcurrentWeakMapKt.REHASH;
        if (v == symbol) {
            v = putSynchronized(key, null);
        }
        if (v != null) {
            _size$FU.decrementAndGet(this);
        }
        return v;
    }

    public final void runWeakRefQueueCleaningLoopUntilInterrupted() {
        if (!(this.weakRefQueue != null)) {
            throw new IllegalStateException("Must be created with weakRefQueue = true".toString());
        }
        while (true) {
            try {
                Reference<? extends K> remove = this.weakRefQueue.remove();
                if (remove == null) {
                    break;
                } else {
                    cleanWeakRef((HashedWeakRef) remove);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
        }
        throw new NullPointerException("null cannot be cast to non-null type kotlinx.coroutines.debug.internal.HashedWeakRef<*>");
    }
}
