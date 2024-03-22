package androidx.collection;

import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import mt.Log229316;
import mt.Log5ECF72;
import mt.LogE84000;

/* compiled from: 0008.java */
/* loaded from: classes.dex */
public class LruCache<K, V> {
    private int createCount;
    private int evictionCount;
    private int hitCount;
    private final LinkedHashMap<K, V> map;
    private int maxSize;
    private int missCount;
    private int putCount;
    private int size;

    public LruCache(int maxSize) {
        if (maxSize <= 0) {
            throw new IllegalArgumentException("maxSize <= 0");
        }
        this.maxSize = maxSize;
        this.map = new LinkedHashMap<>(0, 0.75f, true);
    }

    private int safeSizeOf(K k, V v) {
        int sizeOf = sizeOf(k, v);
        if (sizeOf >= 0) {
            return sizeOf;
        }
        throw new IllegalStateException("Negative size: " + k + "=" + v);
    }

    protected V create(K k) {
        return null;
    }

    public final synchronized int createCount() {
        return this.createCount;
    }

    protected void entryRemoved(boolean evicted, K k, V v, V v2) {
    }

    public final void evictAll() {
        trimToSize(-1);
    }

    public final synchronized int evictionCount() {
        return this.evictionCount;
    }

    public final V get(K k) {
        V v;
        if (k == null) {
            throw new NullPointerException("key == null");
        }
        synchronized (this) {
            V v2 = this.map.get(k);
            if (v2 != null) {
                this.hitCount++;
                return v2;
            }
            this.missCount++;
            V create = create(k);
            if (create == null) {
                return null;
            }
            synchronized (this) {
                this.createCount++;
                v = (V) this.map.put(k, create);
                if (v != null) {
                    this.map.put(k, v);
                } else {
                    this.size += safeSizeOf(k, create);
                }
            }
            if (v != null) {
                entryRemoved(false, k, create, v);
                return v;
            }
            trimToSize(this.maxSize);
            return create;
        }
    }

    public final synchronized int hitCount() {
        return this.hitCount;
    }

    public final synchronized int maxSize() {
        return this.maxSize;
    }

    public final synchronized int missCount() {
        return this.missCount;
    }

    public final V put(K k, V v) {
        V put;
        if (k == null || v == null) {
            throw new NullPointerException("key == null || value == null");
        }
        synchronized (this) {
            this.putCount++;
            this.size += safeSizeOf(k, v);
            put = this.map.put(k, v);
            if (put != null) {
                this.size -= safeSizeOf(k, put);
            }
        }
        if (put != null) {
            entryRemoved(false, k, put, v);
        }
        trimToSize(this.maxSize);
        return put;
    }

    public final synchronized int putCount() {
        return this.putCount;
    }

    public final V remove(K k) {
        V remove;
        if (k == null) {
            throw new NullPointerException("key == null");
        }
        synchronized (this) {
            remove = this.map.remove(k);
            if (remove != null) {
                this.size -= safeSizeOf(k, remove);
            }
        }
        if (remove != null) {
            entryRemoved(false, k, remove, null);
        }
        return remove;
    }

    public void resize(int maxSize) {
        if (maxSize <= 0) {
            throw new IllegalArgumentException("maxSize <= 0");
        }
        synchronized (this) {
            this.maxSize = maxSize;
        }
        trimToSize(maxSize);
    }

    public final synchronized int size() {
        return this.size;
    }

    protected int sizeOf(K k, V v) {
        return 1;
    }

    public final synchronized Map<K, V> snapshot() {
        return new LinkedHashMap(this.map);
    }

    /* JADX WARN: Failed to parse debug info
    jadx.core.utils.exceptions.JadxRuntimeException: Failed to parse type string: null
    	at jadx.core.dex.instructions.args.ArgType.parse(ArgType.java:736)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.getVarType(DebugInfoAttachVisitor.java:139)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.attachDebugInfo(DebugInfoAttachVisitor.java:94)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.processDebugInfo(DebugInfoAttachVisitor.java:51)
    	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.visit(DebugInfoAttachVisitor.java:41)
     */
    public final synchronized String toString() {
        String format;
        int i = this.hitCount;
        int i2 = this.missCount + i;
        format = String.format(Locale.US, "LruCache[maxSize=%d,hits=%d,misses=%d,hitRate=%d%%]", Integer.valueOf(this.maxSize), Integer.valueOf(this.hitCount), Integer.valueOf(this.missCount), Integer.valueOf(i2 != 0 ? (i * 100) / i2 : 0));
        Log5ECF72.a(format);
        LogE84000.a(format);
        Log229316.a(format);
        return format;
    }

    /* JADX WARN: Code restructure failed: missing block: B:11:0x0072, code lost:
    
        throw new java.lang.IllegalStateException(getClass().getName() + ".sizeOf() is reporting inconsistent results!");
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public void trimToSize(int r6) {
        /*
            r5 = this;
        L0:
            monitor-enter(r5)
            int r0 = r5.size     // Catch: java.lang.Throwable -> L73
            if (r0 < 0) goto L52
            java.util.LinkedHashMap<K, V> r0 = r5.map     // Catch: java.lang.Throwable -> L73
            boolean r0 = r0.isEmpty()     // Catch: java.lang.Throwable -> L73
            if (r0 == 0) goto L11
            int r0 = r5.size     // Catch: java.lang.Throwable -> L73
            if (r0 != 0) goto L52
        L11:
            int r0 = r5.size     // Catch: java.lang.Throwable -> L73
            if (r0 <= r6) goto L50
            java.util.LinkedHashMap<K, V> r0 = r5.map     // Catch: java.lang.Throwable -> L73
            boolean r0 = r0.isEmpty()     // Catch: java.lang.Throwable -> L73
            if (r0 == 0) goto L1e
            goto L50
        L1e:
            java.util.LinkedHashMap<K, V> r0 = r5.map     // Catch: java.lang.Throwable -> L73
            java.util.Set r0 = r0.entrySet()     // Catch: java.lang.Throwable -> L73
            java.util.Iterator r0 = r0.iterator()     // Catch: java.lang.Throwable -> L73
            java.lang.Object r0 = r0.next()     // Catch: java.lang.Throwable -> L73
            java.util.Map$Entry r0 = (java.util.Map.Entry) r0     // Catch: java.lang.Throwable -> L73
            java.lang.Object r1 = r0.getKey()     // Catch: java.lang.Throwable -> L73
            java.lang.Object r2 = r0.getValue()     // Catch: java.lang.Throwable -> L73
            java.util.LinkedHashMap<K, V> r3 = r5.map     // Catch: java.lang.Throwable -> L73
            r3.remove(r1)     // Catch: java.lang.Throwable -> L73
            int r3 = r5.size     // Catch: java.lang.Throwable -> L73
            int r4 = r5.safeSizeOf(r1, r2)     // Catch: java.lang.Throwable -> L73
            int r3 = r3 - r4
            r5.size = r3     // Catch: java.lang.Throwable -> L73
            int r3 = r5.evictionCount     // Catch: java.lang.Throwable -> L73
            r4 = 1
            int r3 = r3 + r4
            r5.evictionCount = r3     // Catch: java.lang.Throwable -> L73
            monitor-exit(r5)     // Catch: java.lang.Throwable -> L73
            r0 = 0
            r5.entryRemoved(r4, r1, r2, r0)
            goto L0
        L50:
            monitor-exit(r5)     // Catch: java.lang.Throwable -> L73
            return
        L52:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException     // Catch: java.lang.Throwable -> L73
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L73
            r1.<init>()     // Catch: java.lang.Throwable -> L73
            java.lang.Class r2 = r5.getClass()     // Catch: java.lang.Throwable -> L73
            java.lang.String r2 = r2.getName()     // Catch: java.lang.Throwable -> L73
            java.lang.StringBuilder r1 = r1.append(r2)     // Catch: java.lang.Throwable -> L73
            java.lang.String r2 = ".sizeOf() is reporting inconsistent results!"
            java.lang.StringBuilder r1 = r1.append(r2)     // Catch: java.lang.Throwable -> L73
            java.lang.String r1 = r1.toString()     // Catch: java.lang.Throwable -> L73
            r0.<init>(r1)     // Catch: java.lang.Throwable -> L73
            throw r0     // Catch: java.lang.Throwable -> L73
        L73:
            r0 = move-exception
            monitor-exit(r5)     // Catch: java.lang.Throwable -> L73
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.collection.LruCache.trimToSize(int):void");
    }
}