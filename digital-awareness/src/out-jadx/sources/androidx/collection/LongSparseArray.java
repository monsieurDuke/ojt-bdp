package androidx.collection;

/* loaded from: classes.dex */
public class LongSparseArray<E> implements Cloneable {
    private static final Object DELETED = new Object();
    private boolean mGarbage;
    private long[] mKeys;
    private int mSize;
    private Object[] mValues;

    public LongSparseArray() {
        this(10);
    }

    public LongSparseArray(int initialCapacity) {
        this.mGarbage = false;
        if (initialCapacity == 0) {
            this.mKeys = ContainerHelpers.EMPTY_LONGS;
            this.mValues = ContainerHelpers.EMPTY_OBJECTS;
        } else {
            int initialCapacity2 = ContainerHelpers.idealLongArraySize(initialCapacity);
            this.mKeys = new long[initialCapacity2];
            this.mValues = new Object[initialCapacity2];
        }
    }

    private void gc() {
        int i = this.mSize;
        int i2 = 0;
        long[] jArr = this.mKeys;
        Object[] objArr = this.mValues;
        for (int i3 = 0; i3 < i; i3++) {
            Object obj = objArr[i3];
            if (obj != DELETED) {
                if (i3 != i2) {
                    jArr[i2] = jArr[i3];
                    objArr[i2] = obj;
                    objArr[i3] = null;
                }
                i2++;
            }
        }
        this.mGarbage = false;
        this.mSize = i2;
    }

    public void append(long key, E e) {
        int i = this.mSize;
        if (i != 0 && key <= this.mKeys[i - 1]) {
            put(key, e);
            return;
        }
        if (this.mGarbage && i >= this.mKeys.length) {
            gc();
        }
        int i2 = this.mSize;
        if (i2 >= this.mKeys.length) {
            int idealLongArraySize = ContainerHelpers.idealLongArraySize(i2 + 1);
            long[] jArr = new long[idealLongArraySize];
            Object[] objArr = new Object[idealLongArraySize];
            long[] jArr2 = this.mKeys;
            System.arraycopy(jArr2, 0, jArr, 0, jArr2.length);
            Object[] objArr2 = this.mValues;
            System.arraycopy(objArr2, 0, objArr, 0, objArr2.length);
            this.mKeys = jArr;
            this.mValues = objArr;
        }
        this.mKeys[i2] = key;
        this.mValues[i2] = e;
        this.mSize = i2 + 1;
    }

    public void clear() {
        int i = this.mSize;
        Object[] objArr = this.mValues;
        for (int i2 = 0; i2 < i; i2++) {
            objArr[i2] = null;
        }
        this.mSize = 0;
        this.mGarbage = false;
    }

    /* renamed from: clone, reason: merged with bridge method [inline-methods] */
    public LongSparseArray<E> m4clone() {
        try {
            LongSparseArray<E> longSparseArray = (LongSparseArray) super.clone();
            longSparseArray.mKeys = (long[]) this.mKeys.clone();
            longSparseArray.mValues = (Object[]) this.mValues.clone();
            return longSparseArray;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }

    public boolean containsKey(long key) {
        return indexOfKey(key) >= 0;
    }

    public boolean containsValue(E e) {
        return indexOfValue(e) >= 0;
    }

    @Deprecated
    public void delete(long key) {
        remove(key);
    }

    public E get(long key) {
        return get(key, null);
    }

    public E get(long j, E e) {
        E e2;
        int binarySearch = ContainerHelpers.binarySearch(this.mKeys, this.mSize, j);
        return (binarySearch < 0 || (e2 = (E) this.mValues[binarySearch]) == DELETED) ? e : e2;
    }

    public int indexOfKey(long key) {
        if (this.mGarbage) {
            gc();
        }
        return ContainerHelpers.binarySearch(this.mKeys, this.mSize, key);
    }

    public int indexOfValue(E e) {
        if (this.mGarbage) {
            gc();
        }
        for (int i = 0; i < this.mSize; i++) {
            if (this.mValues[i] == e) {
                return i;
            }
        }
        return -1;
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    public long keyAt(int index) {
        if (this.mGarbage) {
            gc();
        }
        return this.mKeys[index];
    }

    public void put(long key, E e) {
        int binarySearch = ContainerHelpers.binarySearch(this.mKeys, this.mSize, key);
        if (binarySearch >= 0) {
            this.mValues[binarySearch] = e;
            return;
        }
        int i = ~binarySearch;
        int i2 = this.mSize;
        if (i < i2) {
            Object[] objArr = this.mValues;
            if (objArr[i] == DELETED) {
                this.mKeys[i] = key;
                objArr[i] = e;
                return;
            }
        }
        if (this.mGarbage && i2 >= this.mKeys.length) {
            gc();
            i = ~ContainerHelpers.binarySearch(this.mKeys, this.mSize, key);
        }
        int i3 = this.mSize;
        if (i3 >= this.mKeys.length) {
            int idealLongArraySize = ContainerHelpers.idealLongArraySize(i3 + 1);
            long[] jArr = new long[idealLongArraySize];
            Object[] objArr2 = new Object[idealLongArraySize];
            long[] jArr2 = this.mKeys;
            System.arraycopy(jArr2, 0, jArr, 0, jArr2.length);
            Object[] objArr3 = this.mValues;
            System.arraycopy(objArr3, 0, objArr2, 0, objArr3.length);
            this.mKeys = jArr;
            this.mValues = objArr2;
        }
        int i4 = this.mSize;
        if (i4 - i != 0) {
            long[] jArr3 = this.mKeys;
            System.arraycopy(jArr3, i, jArr3, i + 1, i4 - i);
            Object[] objArr4 = this.mValues;
            System.arraycopy(objArr4, i, objArr4, i + 1, this.mSize - i);
        }
        this.mKeys[i] = key;
        this.mValues[i] = e;
        this.mSize++;
    }

    public void putAll(LongSparseArray<? extends E> longSparseArray) {
        int size = longSparseArray.size();
        for (int i = 0; i < size; i++) {
            put(longSparseArray.keyAt(i), longSparseArray.valueAt(i));
        }
    }

    public E putIfAbsent(long key, E e) {
        E e2 = get(key);
        if (e2 == null) {
            put(key, e);
        }
        return e2;
    }

    public void remove(long key) {
        int binarySearch = ContainerHelpers.binarySearch(this.mKeys, this.mSize, key);
        if (binarySearch >= 0) {
            Object[] objArr = this.mValues;
            Object obj = objArr[binarySearch];
            Object obj2 = DELETED;
            if (obj != obj2) {
                objArr[binarySearch] = obj2;
                this.mGarbage = true;
            }
        }
    }

    public boolean remove(long key, Object value) {
        int indexOfKey = indexOfKey(key);
        if (indexOfKey < 0) {
            return false;
        }
        E valueAt = valueAt(indexOfKey);
        if (value != valueAt && (value == null || !value.equals(valueAt))) {
            return false;
        }
        removeAt(indexOfKey);
        return true;
    }

    public void removeAt(int index) {
        Object[] objArr = this.mValues;
        Object obj = objArr[index];
        Object obj2 = DELETED;
        if (obj != obj2) {
            objArr[index] = obj2;
            this.mGarbage = true;
        }
    }

    public E replace(long j, E e) {
        int indexOfKey = indexOfKey(j);
        if (indexOfKey < 0) {
            return null;
        }
        Object[] objArr = this.mValues;
        E e2 = (E) objArr[indexOfKey];
        objArr[indexOfKey] = e;
        return e2;
    }

    public boolean replace(long key, E e, E e2) {
        int indexOfKey = indexOfKey(key);
        if (indexOfKey < 0) {
            return false;
        }
        Object obj = this.mValues[indexOfKey];
        if (obj != e && (e == null || !e.equals(obj))) {
            return false;
        }
        this.mValues[indexOfKey] = e2;
        return true;
    }

    public void setValueAt(int index, E e) {
        if (this.mGarbage) {
            gc();
        }
        this.mValues[index] = e;
    }

    public int size() {
        if (this.mGarbage) {
            gc();
        }
        return this.mSize;
    }

    public String toString() {
        if (size() <= 0) {
            return "{}";
        }
        StringBuilder sb = new StringBuilder(this.mSize * 28);
        sb.append('{');
        for (int i = 0; i < this.mSize; i++) {
            if (i > 0) {
                sb.append(", ");
            }
            sb.append(keyAt(i));
            sb.append('=');
            E valueAt = valueAt(i);
            if (valueAt != this) {
                sb.append(valueAt);
            } else {
                sb.append("(this Map)");
            }
        }
        sb.append('}');
        return sb.toString();
    }

    public E valueAt(int i) {
        if (this.mGarbage) {
            gc();
        }
        return (E) this.mValues[i];
    }
}
