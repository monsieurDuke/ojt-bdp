package androidx.lifecycle;

import java.io.Closeable;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

/* loaded from: classes.dex */
public abstract class ViewModel {
    private final Map<String, Object> mBagOfTags;
    private volatile boolean mCleared;
    private final Set<Closeable> mCloseables;

    public ViewModel() {
        this.mBagOfTags = new HashMap();
        this.mCloseables = new LinkedHashSet();
        this.mCleared = false;
    }

    public ViewModel(Closeable... closeables) {
        this.mBagOfTags = new HashMap();
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        this.mCloseables = linkedHashSet;
        this.mCleared = false;
        linkedHashSet.addAll(Arrays.asList(closeables));
    }

    private static void closeWithRuntimeException(Object obj) {
        if (obj instanceof Closeable) {
            try {
                ((Closeable) obj).close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void addCloseable(Closeable closeable) {
        Set<Closeable> set = this.mCloseables;
        if (set != null) {
            synchronized (set) {
                this.mCloseables.add(closeable);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final void clear() {
        this.mCleared = true;
        Map<String, Object> map = this.mBagOfTags;
        if (map != null) {
            synchronized (map) {
                Iterator<Object> it = this.mBagOfTags.values().iterator();
                while (it.hasNext()) {
                    closeWithRuntimeException(it.next());
                }
            }
        }
        Set<Closeable> set = this.mCloseables;
        if (set != null) {
            synchronized (set) {
                Iterator<Closeable> it2 = this.mCloseables.iterator();
                while (it2.hasNext()) {
                    closeWithRuntimeException(it2.next());
                }
            }
        }
        onCleared();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public <T> T getTag(String str) {
        T t;
        Map<String, Object> map = this.mBagOfTags;
        if (map == null) {
            return null;
        }
        synchronized (map) {
            t = (T) this.mBagOfTags.get(str);
        }
        return t;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onCleared() {
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: Multi-variable type inference failed */
    public <T> T setTagIfAbsent(String key, T t) {
        Object obj;
        synchronized (this.mBagOfTags) {
            obj = this.mBagOfTags.get(key);
            if (obj == 0) {
                this.mBagOfTags.put(key, t);
            }
        }
        T t2 = obj == 0 ? t : obj;
        if (this.mCleared) {
            closeWithRuntimeException(t2);
        }
        return t2;
    }
}
