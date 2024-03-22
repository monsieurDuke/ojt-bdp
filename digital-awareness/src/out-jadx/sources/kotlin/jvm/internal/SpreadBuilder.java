package kotlin.jvm.internal;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;

/* loaded from: classes.dex */
public class SpreadBuilder {
    private final ArrayList<Object> list;

    public SpreadBuilder(int size) {
        this.list = new ArrayList<>(size);
    }

    public void add(Object element) {
        this.list.add(element);
    }

    public void addSpread(Object container) {
        if (container == null) {
            return;
        }
        if (container instanceof Object[]) {
            Object[] objArr = (Object[]) container;
            if (objArr.length > 0) {
                ArrayList<Object> arrayList = this.list;
                arrayList.ensureCapacity(arrayList.size() + objArr.length);
                Collections.addAll(this.list, objArr);
                return;
            }
            return;
        }
        if (container instanceof Collection) {
            this.list.addAll((Collection) container);
            return;
        }
        if (container instanceof Iterable) {
            Iterator it = ((Iterable) container).iterator();
            while (it.hasNext()) {
                this.list.add(it.next());
            }
            return;
        }
        if (!(container instanceof Iterator)) {
            throw new UnsupportedOperationException("Don't know how to spread " + container.getClass());
        }
        Iterator it2 = (Iterator) container;
        while (it2.hasNext()) {
            this.list.add(it2.next());
        }
    }

    public int size() {
        return this.list.size();
    }

    public Object[] toArray(Object[] a) {
        return this.list.toArray(a);
    }
}
