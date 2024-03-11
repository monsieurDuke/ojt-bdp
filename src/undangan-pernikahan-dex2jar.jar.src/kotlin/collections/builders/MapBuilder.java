package kotlin.collections.builders;

import java.io.NotSerializableException;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.NoSuchElementException;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.ArraysKt;
import kotlin.collections.IntIterator;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.markers.KMutableIterator;
import kotlin.jvm.internal.markers.KMutableMap;
import kotlin.jvm.internal.markers.KMutableMap.Entry;
import kotlin.ranges.IntRange;
import kotlin.ranges.RangesKt;

@Metadata(d1={"\000¨\001\n\002\030\002\n\002\b\002\n\002\020%\n\002\030\002\n\002\030\002\n\002\b\002\n\002\020\b\n\002\b\002\n\002\020\021\n\002\b\002\n\002\020\025\n\002\b\b\n\002\020#\n\002\020'\n\002\b\003\n\002\030\002\n\002\b\004\n\002\020\013\n\002\b\007\n\002\030\002\n\002\b\003\n\002\020\037\n\002\b\003\n\002\030\002\n\002\b\007\n\002\020$\n\000\n\002\020\002\n\002\b\005\n\002\020\036\n\002\b\003\n\002\020&\n\002\b\013\n\002\030\002\n\002\b\002\n\002\020\000\n\002\b\b\n\002\030\002\n\002\b\030\n\002\020\016\n\000\n\002\030\002\n\002\b\t\b\000\030\000 {*\004\b\000\020\001*\004\b\001\020\0022\016\022\004\022\002H\001\022\004\022\002H\0020\0032\0060\004j\002`\005:\007{|}~\001B\007\b\026¢\006\002\020\006B\017\b\026\022\006\020\007\032\0020\b¢\006\002\020\tBE\b\002\022\f\020\n\032\b\022\004\022\0028\0000\013\022\016\020\f\032\n\022\004\022\0028\001\030\0010\013\022\006\020\r\032\0020\016\022\006\020\017\032\0020\016\022\006\020\020\032\0020\b\022\006\020\021\032\0020\b¢\006\002\020\022J\027\0202\032\0020\b2\006\0203\032\0028\000H\000¢\006\004\b4\0205J\023\0206\032\b\022\004\022\0028\0010\013H\002¢\006\002\0207J\022\0208\032\016\022\004\022\0028\000\022\004\022\0028\00109J\r\020:\032\0020;H\000¢\006\002\b<J\b\020=\032\0020;H\026J\b\020>\032\0020;H\002J\031\020?\032\0020!2\n\020@\032\006\022\002\b\0030AH\000¢\006\002\bBJ!\020C\032\0020!2\022\020D\032\016\022\004\022\0028\000\022\004\022\0028\0010EH\000¢\006\002\bFJ\025\020G\032\0020!2\006\0203\032\0028\000H\026¢\006\002\020HJ\025\020I\032\0020!2\006\020J\032\0028\001H\026¢\006\002\020HJ\030\020K\032\0020!2\016\020L\032\n\022\002\b\003\022\002\b\00309H\002J\020\020M\032\0020;2\006\020\023\032\0020\bH\002J\020\020N\032\0020;2\006\020O\032\0020\bH\002J\031\020P\032\016\022\004\022\0028\000\022\004\022\0028\0010QH\000¢\006\002\bRJ\023\020S\032\0020!2\b\020L\032\004\030\0010TH\002J\025\020U\032\0020\b2\006\0203\032\0028\000H\002¢\006\002\0205J\025\020V\032\0020\b2\006\020J\032\0028\001H\002¢\006\002\0205J\030\020W\032\004\030\0018\0012\006\0203\032\0028\000H\002¢\006\002\020XJ\025\020Y\032\0020\b2\006\0203\032\0028\000H\002¢\006\002\0205J\b\020Z\032\0020\bH\026J\b\020[\032\0020!H\026J\031\020\\\032\016\022\004\022\0028\000\022\004\022\0028\0010]H\000¢\006\002\b^J\037\020_\032\004\030\0018\0012\006\0203\032\0028\0002\006\020J\032\0028\001H\026¢\006\002\020`J\036\020a\032\0020;2\024\020b\032\020\022\006\b\001\022\0028\000\022\004\022\0028\00109H\026J\"\020c\032\0020!2\030\020b\032\024\022\020\022\016\022\004\022\0028\000\022\004\022\0028\0010E0AH\002J\034\020d\032\0020!2\022\020D\032\016\022\004\022\0028\000\022\004\022\0028\0010EH\002J\020\020e\032\0020!2\006\020f\032\0020\bH\002J\020\020g\032\0020;2\006\020h\032\0020\bH\002J\027\020i\032\004\030\0018\0012\006\0203\032\0028\000H\026¢\006\002\020XJ!\020j\032\0020!2\022\020D\032\016\022\004\022\0028\000\022\004\022\0028\0010EH\000¢\006\002\bkJ\020\020l\032\0020;2\006\020m\032\0020\bH\002J\027\020n\032\0020\b2\006\0203\032\0028\000H\000¢\006\004\bo\0205J\020\020p\032\0020;2\006\020q\032\0020\bH\002J\027\020r\032\0020!2\006\020s\032\0028\001H\000¢\006\004\bt\020HJ\b\020u\032\0020vH\026J\031\020w\032\016\022\004\022\0028\000\022\004\022\0028\0010xH\000¢\006\002\byJ\b\020z\032\0020TH\002R\024\020\023\032\0020\b8BX\004¢\006\006\032\004\b\024\020\025R&\020\026\032\024\022\020\022\016\022\004\022\0028\000\022\004\022\0028\0010\0300\0278VX\004¢\006\006\032\004\b\031\020\032R\034\020\033\032\020\022\004\022\0028\000\022\004\022\0028\001\030\0010\034X\016¢\006\002\n\000R\016\020\017\032\0020\016X\016¢\006\002\n\000R\016\020\035\032\0020\bX\016¢\006\002\n\000R\024\020\036\032\0020\b8BX\004¢\006\006\032\004\b\037\020\025R\036\020\"\032\0020!2\006\020 \032\0020!@BX\016¢\006\b\n\000\032\004\b#\020$R\032\020%\032\b\022\004\022\0028\0000\0278VX\004¢\006\006\032\004\b&\020\032R\026\020\n\032\b\022\004\022\0028\0000\013X\016¢\006\004\n\002\020'R\026\020(\032\n\022\004\022\0028\000\030\0010)X\016¢\006\002\n\000R\016\020\021\032\0020\bX\016¢\006\002\n\000R\016\020\020\032\0020\bX\016¢\006\002\n\000R\016\020\r\032\0020\016X\016¢\006\002\n\000R\036\020*\032\0020\b2\006\020 \032\0020\b@RX\016¢\006\b\n\000\032\004\b+\020\025R\032\020,\032\b\022\004\022\0028\0010-8VX\004¢\006\006\032\004\b.\020/R\030\020\f\032\n\022\004\022\0028\001\030\0010\013X\016¢\006\004\n\002\020'R\026\0200\032\n\022\004\022\0028\001\030\00101X\016¢\006\002\n\000¨\006\001"}, d2={"Lkotlin/collections/builders/MapBuilder;", "K", "V", "", "Ljava/io/Serializable;", "Lkotlin/io/Serializable;", "()V", "initialCapacity", "", "(I)V", "keysArray", "", "valuesArray", "presenceArray", "", "hashArray", "maxProbeDistance", "length", "([Ljava/lang/Object;[Ljava/lang/Object;[I[III)V", "capacity", "getCapacity", "()I", "entries", "", "", "getEntries", "()Ljava/util/Set;", "entriesView", "Lkotlin/collections/builders/MapBuilderEntries;", "hashShift", "hashSize", "getHashSize", "<set-?>", "", "isReadOnly", "isReadOnly$kotlin_stdlib", "()Z", "keys", "getKeys", "[Ljava/lang/Object;", "keysView", "Lkotlin/collections/builders/MapBuilderKeys;", "size", "getSize", "values", "", "getValues", "()Ljava/util/Collection;", "valuesView", "Lkotlin/collections/builders/MapBuilderValues;", "addKey", "key", "addKey$kotlin_stdlib", "(Ljava/lang/Object;)I", "allocateValuesArray", "()[Ljava/lang/Object;", "build", "", "checkIsMutable", "", "checkIsMutable$kotlin_stdlib", "clear", "compact", "containsAllEntries", "m", "", "containsAllEntries$kotlin_stdlib", "containsEntry", "entry", "", "containsEntry$kotlin_stdlib", "containsKey", "(Ljava/lang/Object;)Z", "containsValue", "value", "contentEquals", "other", "ensureCapacity", "ensureExtraCapacity", "n", "entriesIterator", "Lkotlin/collections/builders/MapBuilder$EntriesItr;", "entriesIterator$kotlin_stdlib", "equals", "", "findKey", "findValue", "get", "(Ljava/lang/Object;)Ljava/lang/Object;", "hash", "hashCode", "isEmpty", "keysIterator", "Lkotlin/collections/builders/MapBuilder$KeysItr;", "keysIterator$kotlin_stdlib", "put", "(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;", "putAll", "from", "putAllEntries", "putEntry", "putRehash", "i", "rehash", "newHashSize", "remove", "removeEntry", "removeEntry$kotlin_stdlib", "removeHashAt", "removedHash", "removeKey", "removeKey$kotlin_stdlib", "removeKeyAt", "index", "removeValue", "element", "removeValue$kotlin_stdlib", "toString", "", "valuesIterator", "Lkotlin/collections/builders/MapBuilder$ValuesItr;", "valuesIterator$kotlin_stdlib", "writeReplace", "Companion", "EntriesItr", "EntryRef", "Itr", "KeysItr", "ValuesItr", "kotlin-stdlib"}, k=1, mv={1, 7, 1}, xi=48)
public final class MapBuilder<K, V>
  implements Map<K, V>, Serializable, KMutableMap
{
  private static final Companion Companion = new Companion(null);
  @Deprecated
  private static final int INITIAL_CAPACITY = 8;
  @Deprecated
  private static final int INITIAL_MAX_PROBE_DISTANCE = 2;
  @Deprecated
  private static final int MAGIC = -1640531527;
  @Deprecated
  private static final int TOMBSTONE = -1;
  private MapBuilderEntries<K, V> entriesView;
  private int[] hashArray;
  private int hashShift;
  private boolean isReadOnly;
  private K[] keysArray;
  private MapBuilderKeys<K> keysView;
  private int length;
  private int maxProbeDistance;
  private int[] presenceArray;
  private int size;
  private V[] valuesArray;
  private MapBuilderValues<V> valuesView;
  
  public MapBuilder()
  {
    this(8);
  }
  
  public MapBuilder(int paramInt)
  {
    this(arrayOfObject, null, arrayOfInt2, arrayOfInt1, 2, 0);
  }
  
  private MapBuilder(K[] paramArrayOfK, V[] paramArrayOfV, int[] paramArrayOfInt1, int[] paramArrayOfInt2, int paramInt1, int paramInt2)
  {
    this.keysArray = paramArrayOfK;
    this.valuesArray = paramArrayOfV;
    this.presenceArray = paramArrayOfInt1;
    this.hashArray = paramArrayOfInt2;
    this.maxProbeDistance = paramInt1;
    this.length = paramInt2;
    this.hashShift = Companion.access$computeShift(Companion, getHashSize());
  }
  
  private final V[] allocateValuesArray()
  {
    Object[] arrayOfObject = this.valuesArray;
    if (arrayOfObject != null) {
      return arrayOfObject;
    }
    arrayOfObject = ListBuilderKt.arrayOfUninitializedElements(getCapacity());
    this.valuesArray = arrayOfObject;
    return arrayOfObject;
  }
  
  private final void compact()
  {
    int i = 0;
    int j = 0;
    Object[] arrayOfObject1 = this.valuesArray;
    int k;
    for (;;)
    {
      k = this.length;
      if (i >= k) {
        break;
      }
      k = j;
      if (this.presenceArray[i] >= 0)
      {
        Object[] arrayOfObject2 = this.keysArray;
        arrayOfObject2[j] = arrayOfObject2[i];
        if (arrayOfObject1 != null) {
          arrayOfObject1[j] = arrayOfObject1[i];
        }
        k = j + 1;
      }
      i++;
      j = k;
    }
    ListBuilderKt.resetRange(this.keysArray, j, k);
    if (arrayOfObject1 != null) {
      ListBuilderKt.resetRange(arrayOfObject1, j, this.length);
    }
    this.length = j;
  }
  
  private final boolean contentEquals(Map<?, ?> paramMap)
  {
    boolean bool;
    if ((size() == paramMap.size()) && (containsAllEntries$kotlin_stdlib((Collection)paramMap.entrySet()))) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  private final void ensureCapacity(int paramInt)
  {
    if (paramInt >= 0)
    {
      if (paramInt > getCapacity())
      {
        int j = getCapacity() * 3 / 2;
        int i = j;
        if (paramInt > j) {
          i = paramInt;
        }
        this.keysArray = ListBuilderKt.copyOfUninitializedElements(this.keysArray, i);
        Object localObject = this.valuesArray;
        if (localObject != null) {
          localObject = ListBuilderKt.copyOfUninitializedElements((Object[])localObject, i);
        } else {
          localObject = null;
        }
        this.valuesArray = ((Object[])localObject);
        localObject = Arrays.copyOf(this.presenceArray, i);
        Intrinsics.checkNotNullExpressionValue(localObject, "copyOf(this, newSize)");
        this.presenceArray = ((int[])localObject);
        paramInt = Companion.access$computeHashSize(Companion, i);
        if (paramInt > getHashSize()) {
          rehash(paramInt);
        }
      }
      else if (this.length + paramInt - size() > getCapacity())
      {
        rehash(getHashSize());
      }
      return;
    }
    throw new OutOfMemoryError();
  }
  
  private final void ensureExtraCapacity(int paramInt)
  {
    ensureCapacity(this.length + paramInt);
  }
  
  private final int findKey(K paramK)
  {
    int i = hash(paramK);
    int j = this.maxProbeDistance;
    for (;;)
    {
      int k = this.hashArray[i];
      if (k == 0) {
        return -1;
      }
      if ((k > 0) && (Intrinsics.areEqual(this.keysArray[(k - 1)], paramK))) {
        return k - 1;
      }
      j--;
      if (j < 0) {
        return -1;
      }
      if (i == 0) {
        i = getHashSize() - 1;
      } else {
        i--;
      }
    }
  }
  
  private final int findValue(V paramV)
  {
    int i = this.length;
    int j;
    Object[] arrayOfObject;
    do
    {
      do
      {
        j = i - 1;
        if (j < 0) {
          break;
        }
        i = j;
      } while (this.presenceArray[j] < 0);
      arrayOfObject = this.valuesArray;
      Intrinsics.checkNotNull(arrayOfObject);
      i = j;
    } while (!Intrinsics.areEqual(arrayOfObject[j], paramV));
    return j;
    return -1;
  }
  
  private final int getCapacity()
  {
    return this.keysArray.length;
  }
  
  private final int getHashSize()
  {
    return this.hashArray.length;
  }
  
  private final int hash(K paramK)
  {
    int i;
    if (paramK != null) {
      i = paramK.hashCode();
    } else {
      i = 0;
    }
    return i * -1640531527 >>> this.hashShift;
  }
  
  private final boolean putAllEntries(Collection<? extends Map.Entry<? extends K, ? extends V>> paramCollection)
  {
    if (paramCollection.isEmpty()) {
      return false;
    }
    ensureExtraCapacity(paramCollection.size());
    paramCollection = paramCollection.iterator();
    boolean bool = false;
    while (paramCollection.hasNext()) {
      if (putEntry((Map.Entry)paramCollection.next())) {
        bool = true;
      }
    }
    return bool;
  }
  
  private final boolean putEntry(Map.Entry<? extends K, ? extends V> paramEntry)
  {
    int i = addKey$kotlin_stdlib(paramEntry.getKey());
    Object[] arrayOfObject = allocateValuesArray();
    if (i >= 0)
    {
      arrayOfObject[i] = paramEntry.getValue();
      return true;
    }
    Object localObject = arrayOfObject[(-i - 1)];
    if (!Intrinsics.areEqual(paramEntry.getValue(), localObject))
    {
      arrayOfObject[(-i - 1)] = paramEntry.getValue();
      return true;
    }
    return false;
  }
  
  private final boolean putRehash(int paramInt)
  {
    int i = hash(this.keysArray[paramInt]);
    int j = this.maxProbeDistance;
    for (;;)
    {
      int[] arrayOfInt = this.hashArray;
      if (arrayOfInt[i] == 0)
      {
        arrayOfInt[i] = (paramInt + 1);
        this.presenceArray[paramInt] = i;
        return true;
      }
      j--;
      if (j < 0) {
        return false;
      }
      if (i == 0) {
        i = getHashSize() - 1;
      } else {
        i--;
      }
    }
  }
  
  private final void rehash(int paramInt)
  {
    if (this.length > size()) {
      compact();
    }
    if (paramInt != getHashSize())
    {
      this.hashArray = new int[paramInt];
      this.hashShift = Companion.access$computeShift(Companion, paramInt);
    }
    else
    {
      ArraysKt.fill(this.hashArray, 0, 0, getHashSize());
    }
    paramInt = 0;
    while (paramInt < this.length) {
      if (putRehash(paramInt)) {
        paramInt++;
      } else {
        throw new IllegalStateException("This cannot happen with fixed magic multiplier and grow-only hash array. Have object hashCodes changed?");
      }
    }
  }
  
  private final void removeHashAt(int paramInt)
  {
    int j = paramInt;
    int i = 0;
    int k = RangesKt.coerceAtMost(this.maxProbeDistance * 2, getHashSize() / 2);
    int m = paramInt;
    paramInt = j;
    for (;;)
    {
      if (paramInt == 0) {
        paramInt = getHashSize() - 1;
      } else {
        paramInt--;
      }
      int n = i + 1;
      if (n > this.maxProbeDistance)
      {
        this.hashArray[m] = 0;
        return;
      }
      int[] arrayOfInt = this.hashArray;
      int i1 = arrayOfInt[paramInt];
      if (i1 == 0)
      {
        arrayOfInt[m] = 0;
        return;
      }
      if (i1 < 0)
      {
        arrayOfInt[m] = -1;
        j = paramInt;
        i = 0;
      }
      else
      {
        j = m;
        i = n;
        if ((hash(this.keysArray[(i1 - 1)]) - paramInt & getHashSize() - 1) >= n)
        {
          this.hashArray[m] = i1;
          this.presenceArray[(i1 - 1)] = m;
          j = paramInt;
          i = 0;
        }
      }
      k--;
      if (k < 0)
      {
        this.hashArray[j] = -1;
        return;
      }
      m = j;
    }
  }
  
  private final void removeKeyAt(int paramInt)
  {
    ListBuilderKt.resetAt(this.keysArray, paramInt);
    removeHashAt(this.presenceArray[paramInt]);
    this.presenceArray[paramInt] = -1;
    this.size = (size() - 1);
  }
  
  private final Object writeReplace()
  {
    if (this.isReadOnly) {
      return new SerializedMap((Map)this);
    }
    throw new NotSerializableException("The map cannot be serialized while it is being built.");
  }
  
  public final int addKey$kotlin_stdlib(K paramK)
  {
    checkIsMutable$kotlin_stdlib();
    int i = hash(paramK);
    int k = RangesKt.coerceAtMost(this.maxProbeDistance * 2, getHashSize() / 2);
    int j = 0;
    for (;;)
    {
      int m = this.hashArray[i];
      if (m <= 0)
      {
        if (this.length >= getCapacity())
        {
          ensureExtraCapacity(1);
          break;
        }
        k = this.length;
        this.length = (k + 1);
        this.keysArray[k] = paramK;
        this.presenceArray[k] = i;
        this.hashArray[i] = (k + 1);
        this.size = (size() + 1);
        if (j > this.maxProbeDistance) {
          this.maxProbeDistance = j;
        }
        return k;
      }
      if (Intrinsics.areEqual(this.keysArray[(m - 1)], paramK)) {
        return -m;
      }
      j++;
      if (j > k)
      {
        rehash(getHashSize() * 2);
        break;
      }
      if (i == 0) {
        i = getHashSize() - 1;
      } else {
        i--;
      }
    }
  }
  
  public final Map<K, V> build()
  {
    checkIsMutable$kotlin_stdlib();
    this.isReadOnly = true;
    return (Map)this;
  }
  
  public final void checkIsMutable$kotlin_stdlib()
  {
    if (!this.isReadOnly) {
      return;
    }
    throw new UnsupportedOperationException();
  }
  
  public void clear()
  {
    checkIsMutable$kotlin_stdlib();
    IntIterator localIntIterator = new IntRange(0, this.length - 1).iterator();
    while (localIntIterator.hasNext())
    {
      int i = localIntIterator.nextInt();
      localObject = this.presenceArray;
      int j = localObject[i];
      if (j >= 0)
      {
        this.hashArray[j] = 0;
        localObject[i] = -1;
      }
    }
    ListBuilderKt.resetRange(this.keysArray, 0, this.length);
    Object localObject = this.valuesArray;
    if (localObject != null) {
      ListBuilderKt.resetRange((Object[])localObject, 0, this.length);
    }
    this.size = 0;
    this.length = 0;
  }
  
  public final boolean containsAllEntries$kotlin_stdlib(Collection<?> paramCollection)
  {
    Intrinsics.checkNotNullParameter(paramCollection, "m");
    paramCollection = paramCollection.iterator();
    for (;;)
    {
      if (!paramCollection.hasNext()) {
        break label55;
      }
      Object localObject = paramCollection.next();
      if (localObject != null) {
        try
        {
          boolean bool = containsEntry$kotlin_stdlib((Map.Entry)localObject);
          if (bool) {}
        }
        catch (ClassCastException paramCollection)
        {
          return false;
        }
      }
    }
    return false;
    label55:
    return true;
  }
  
  public final boolean containsEntry$kotlin_stdlib(Map.Entry<? extends K, ? extends V> paramEntry)
  {
    Intrinsics.checkNotNullParameter(paramEntry, "entry");
    int i = findKey(paramEntry.getKey());
    if (i < 0) {
      return false;
    }
    Object[] arrayOfObject = this.valuesArray;
    Intrinsics.checkNotNull(arrayOfObject);
    return Intrinsics.areEqual(arrayOfObject[i], paramEntry.getValue());
  }
  
  public boolean containsKey(Object paramObject)
  {
    boolean bool;
    if (findKey(paramObject) >= 0) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public boolean containsValue(Object paramObject)
  {
    boolean bool;
    if (findValue(paramObject) >= 0) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public final EntriesItr<K, V> entriesIterator$kotlin_stdlib()
  {
    return new EntriesItr(this);
  }
  
  public boolean equals(Object paramObject)
  {
    boolean bool;
    if ((paramObject != this) && ((!(paramObject instanceof Map)) || (!contentEquals((Map)paramObject)))) {
      bool = false;
    } else {
      bool = true;
    }
    return bool;
  }
  
  public V get(Object paramObject)
  {
    int i = findKey(paramObject);
    if (i < 0) {
      return null;
    }
    paramObject = this.valuesArray;
    Intrinsics.checkNotNull(paramObject);
    return paramObject[i];
  }
  
  public Set<Map.Entry<K, V>> getEntries()
  {
    MapBuilderEntries localMapBuilderEntries = this.entriesView;
    if (localMapBuilderEntries == null)
    {
      localMapBuilderEntries = new MapBuilderEntries(this);
      this.entriesView = localMapBuilderEntries;
      return (Set)localMapBuilderEntries;
    }
    return (Set)localMapBuilderEntries;
  }
  
  public Set<K> getKeys()
  {
    Object localObject = this.keysView;
    if (localObject == null)
    {
      localObject = new MapBuilderKeys(this);
      this.keysView = ((MapBuilderKeys)localObject);
      localObject = (Set)localObject;
    }
    else
    {
      localObject = (Set)localObject;
    }
    return (Set<K>)localObject;
  }
  
  public int getSize()
  {
    return this.size;
  }
  
  public Collection<V> getValues()
  {
    Object localObject = this.valuesView;
    if (localObject == null)
    {
      localObject = new MapBuilderValues(this);
      this.valuesView = ((MapBuilderValues)localObject);
      localObject = (Collection)localObject;
    }
    else
    {
      localObject = (Collection)localObject;
    }
    return (Collection<V>)localObject;
  }
  
  public int hashCode()
  {
    int i = 0;
    EntriesItr localEntriesItr = entriesIterator$kotlin_stdlib();
    while (localEntriesItr.hasNext()) {
      i += localEntriesItr.nextHashCode$kotlin_stdlib();
    }
    return i;
  }
  
  public boolean isEmpty()
  {
    boolean bool;
    if (size() == 0) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public final boolean isReadOnly$kotlin_stdlib()
  {
    return this.isReadOnly;
  }
  
  public final KeysItr<K, V> keysIterator$kotlin_stdlib()
  {
    return new KeysItr(this);
  }
  
  public V put(K paramK, V paramV)
  {
    checkIsMutable$kotlin_stdlib();
    int i = addKey$kotlin_stdlib(paramK);
    paramK = allocateValuesArray();
    if (i < 0)
    {
      V ? = paramK[(-i - 1)];
      paramK[(-i - 1)] = paramV;
      return ?;
    }
    paramK[i] = paramV;
    return null;
  }
  
  public void putAll(Map<? extends K, ? extends V> paramMap)
  {
    Intrinsics.checkNotNullParameter(paramMap, "from");
    checkIsMutable$kotlin_stdlib();
    putAllEntries((Collection)paramMap.entrySet());
  }
  
  public V remove(Object paramObject)
  {
    int i = removeKey$kotlin_stdlib(paramObject);
    if (i < 0) {
      return null;
    }
    Object[] arrayOfObject = this.valuesArray;
    Intrinsics.checkNotNull(arrayOfObject);
    paramObject = arrayOfObject[i];
    ListBuilderKt.resetAt(arrayOfObject, i);
    return (V)paramObject;
  }
  
  public final boolean removeEntry$kotlin_stdlib(Map.Entry<? extends K, ? extends V> paramEntry)
  {
    Intrinsics.checkNotNullParameter(paramEntry, "entry");
    checkIsMutable$kotlin_stdlib();
    int i = findKey(paramEntry.getKey());
    if (i < 0) {
      return false;
    }
    Object[] arrayOfObject = this.valuesArray;
    Intrinsics.checkNotNull(arrayOfObject);
    if (!Intrinsics.areEqual(arrayOfObject[i], paramEntry.getValue())) {
      return false;
    }
    removeKeyAt(i);
    return true;
  }
  
  public final int removeKey$kotlin_stdlib(K paramK)
  {
    checkIsMutable$kotlin_stdlib();
    int i = findKey(paramK);
    if (i < 0) {
      return -1;
    }
    removeKeyAt(i);
    return i;
  }
  
  public final boolean removeValue$kotlin_stdlib(V paramV)
  {
    checkIsMutable$kotlin_stdlib();
    int i = findValue(paramV);
    if (i < 0) {
      return false;
    }
    removeKeyAt(i);
    return true;
  }
  
  public String toString()
  {
    Object localObject = new StringBuilder(size() * 3 + 2);
    ((StringBuilder)localObject).append("{");
    int i = 0;
    EntriesItr localEntriesItr = entriesIterator$kotlin_stdlib();
    while (localEntriesItr.hasNext())
    {
      if (i > 0) {
        ((StringBuilder)localObject).append(", ");
      }
      localEntriesItr.nextAppendString((StringBuilder)localObject);
      i++;
    }
    ((StringBuilder)localObject).append("}");
    localObject = ((StringBuilder)localObject).toString();
    Intrinsics.checkNotNullExpressionValue(localObject, "sb.toString()");
    return (String)localObject;
  }
  
  public final ValuesItr<K, V> valuesIterator$kotlin_stdlib()
  {
    return new ValuesItr(this);
  }
  
  @Metadata(d1={"\000\024\n\002\030\002\n\002\020\000\n\002\b\002\n\002\020\b\n\002\b\b\b\003\030\0002\0020\001B\007\b\002¢\006\002\020\002J\020\020\b\032\0020\0042\006\020\t\032\0020\004H\002J\020\020\n\032\0020\0042\006\020\013\032\0020\004H\002R\016\020\003\032\0020\004XT¢\006\002\n\000R\016\020\005\032\0020\004XT¢\006\002\n\000R\016\020\006\032\0020\004XT¢\006\002\n\000R\016\020\007\032\0020\004XT¢\006\002\n\000¨\006\f"}, d2={"Lkotlin/collections/builders/MapBuilder$Companion;", "", "()V", "INITIAL_CAPACITY", "", "INITIAL_MAX_PROBE_DISTANCE", "MAGIC", "TOMBSTONE", "computeHashSize", "capacity", "computeShift", "hashSize", "kotlin-stdlib"}, k=1, mv={1, 7, 1}, xi=48)
  private static final class Companion
  {
    private final int computeHashSize(int paramInt)
    {
      return Integer.highestOneBit(RangesKt.coerceAtLeast(paramInt, 1) * 3);
    }
    
    private final int computeShift(int paramInt)
    {
      return Integer.numberOfLeadingZeros(paramInt) + 1;
    }
  }
  
  @Metadata(d1={"\000<\n\002\030\002\n\002\b\002\n\002\030\002\n\002\020)\n\002\020'\n\000\n\002\030\002\n\002\b\002\n\002\030\002\n\000\n\002\020\002\n\000\n\002\030\002\n\002\030\002\n\000\n\002\020\b\n\002\b\002\b\000\030\000*\004\b\002\020\001*\004\b\003\020\0022\016\022\004\022\002H\001\022\004\022\002H\0020\0032\024\022\020\022\016\022\004\022\002H\001\022\004\022\002H\0020\0050\004B\031\022\022\020\006\032\016\022\004\022\0028\002\022\004\022\0028\0030\007¢\006\002\020\bJ\025\020\t\032\016\022\004\022\0028\002\022\004\022\0028\0030\nH\002J\022\020\013\032\0020\f2\n\020\r\032\0060\016j\002`\017J\r\020\020\032\0020\021H\000¢\006\002\b\022¨\006\023"}, d2={"Lkotlin/collections/builders/MapBuilder$EntriesItr;", "K", "V", "Lkotlin/collections/builders/MapBuilder$Itr;", "", "", "map", "Lkotlin/collections/builders/MapBuilder;", "(Lkotlin/collections/builders/MapBuilder;)V", "next", "Lkotlin/collections/builders/MapBuilder$EntryRef;", "nextAppendString", "", "sb", "Ljava/lang/StringBuilder;", "Lkotlin/text/StringBuilder;", "nextHashCode", "", "nextHashCode$kotlin_stdlib", "kotlin-stdlib"}, k=1, mv={1, 7, 1}, xi=48)
  public static final class EntriesItr<K, V>
    extends MapBuilder.Itr<K, V>
    implements Iterator<Map.Entry<K, V>>, KMutableIterator
  {
    public EntriesItr(MapBuilder<K, V> paramMapBuilder)
    {
      super();
    }
    
    public MapBuilder.EntryRef<K, V> next()
    {
      if (getIndex$kotlin_stdlib() < MapBuilder.access$getLength$p(getMap$kotlin_stdlib()))
      {
        int i = getIndex$kotlin_stdlib();
        setIndex$kotlin_stdlib(i + 1);
        setLastIndex$kotlin_stdlib(i);
        MapBuilder.EntryRef localEntryRef = new MapBuilder.EntryRef(getMap$kotlin_stdlib(), getLastIndex$kotlin_stdlib());
        initNext$kotlin_stdlib();
        return localEntryRef;
      }
      throw new NoSuchElementException();
    }
    
    public final void nextAppendString(StringBuilder paramStringBuilder)
    {
      Intrinsics.checkNotNullParameter(paramStringBuilder, "sb");
      if (getIndex$kotlin_stdlib() < MapBuilder.access$getLength$p(getMap$kotlin_stdlib()))
      {
        int i = getIndex$kotlin_stdlib();
        setIndex$kotlin_stdlib(i + 1);
        setLastIndex$kotlin_stdlib(i);
        Object localObject = MapBuilder.access$getKeysArray$p(getMap$kotlin_stdlib())[getLastIndex$kotlin_stdlib()];
        if (Intrinsics.areEqual(localObject, getMap$kotlin_stdlib())) {
          paramStringBuilder.append("(this Map)");
        } else {
          paramStringBuilder.append(localObject);
        }
        paramStringBuilder.append('=');
        localObject = MapBuilder.access$getValuesArray$p(getMap$kotlin_stdlib());
        Intrinsics.checkNotNull(localObject);
        localObject = localObject[getLastIndex$kotlin_stdlib()];
        if (Intrinsics.areEqual(localObject, getMap$kotlin_stdlib())) {
          paramStringBuilder.append("(this Map)");
        } else {
          paramStringBuilder.append(localObject);
        }
        initNext$kotlin_stdlib();
        return;
      }
      throw new NoSuchElementException();
    }
    
    public final int nextHashCode$kotlin_stdlib()
    {
      if (getIndex$kotlin_stdlib() < MapBuilder.access$getLength$p(getMap$kotlin_stdlib()))
      {
        int i = getIndex$kotlin_stdlib();
        setIndex$kotlin_stdlib(i + 1);
        setLastIndex$kotlin_stdlib(i);
        Object localObject = MapBuilder.access$getKeysArray$p(getMap$kotlin_stdlib())[getLastIndex$kotlin_stdlib()];
        int j = 0;
        if (localObject != null) {
          i = localObject.hashCode();
        } else {
          i = 0;
        }
        localObject = MapBuilder.access$getValuesArray$p(getMap$kotlin_stdlib());
        Intrinsics.checkNotNull(localObject);
        localObject = localObject[getLastIndex$kotlin_stdlib()];
        if (localObject != null) {
          j = localObject.hashCode();
        }
        initNext$kotlin_stdlib();
        return i ^ j;
      }
      throw new NoSuchElementException();
    }
  }
  
  @Metadata(d1={"\0000\n\002\030\002\n\002\b\002\n\002\020'\n\000\n\002\030\002\n\000\n\002\020\b\n\002\b\007\n\002\020\013\n\000\n\002\020\000\n\002\b\005\n\002\020\016\n\000\b\000\030\000*\004\b\002\020\001*\004\b\003\020\0022\016\022\004\022\002H\001\022\004\022\002H\0020\003B!\022\022\020\004\032\016\022\004\022\0028\002\022\004\022\0028\0030\005\022\006\020\006\032\0020\007¢\006\002\020\bJ\023\020\016\032\0020\0172\b\020\020\032\004\030\0010\021H\002J\b\020\022\032\0020\007H\026J\025\020\023\032\0028\0032\006\020\024\032\0028\003H\026¢\006\002\020\025J\b\020\026\032\0020\027H\026R\016\020\006\032\0020\007X\004¢\006\002\n\000R\024\020\t\032\0028\0028VX\004¢\006\006\032\004\b\n\020\013R\032\020\004\032\016\022\004\022\0028\002\022\004\022\0028\0030\005X\004¢\006\002\n\000R\024\020\f\032\0028\0038VX\004¢\006\006\032\004\b\r\020\013¨\006\030"}, d2={"Lkotlin/collections/builders/MapBuilder$EntryRef;", "K", "V", "", "map", "Lkotlin/collections/builders/MapBuilder;", "index", "", "(Lkotlin/collections/builders/MapBuilder;I)V", "key", "getKey", "()Ljava/lang/Object;", "value", "getValue", "equals", "", "other", "", "hashCode", "setValue", "newValue", "(Ljava/lang/Object;)Ljava/lang/Object;", "toString", "", "kotlin-stdlib"}, k=1, mv={1, 7, 1}, xi=48)
  public static final class EntryRef<K, V>
    implements Map.Entry<K, V>, KMutableMap.Entry
  {
    private final int index;
    private final MapBuilder<K, V> map;
    
    public EntryRef(MapBuilder<K, V> paramMapBuilder, int paramInt)
    {
      this.map = paramMapBuilder;
      this.index = paramInt;
    }
    
    public boolean equals(Object paramObject)
    {
      boolean bool;
      if (((paramObject instanceof Map.Entry)) && (Intrinsics.areEqual(((Map.Entry)paramObject).getKey(), getKey())) && (Intrinsics.areEqual(((Map.Entry)paramObject).getValue(), getValue()))) {
        bool = true;
      } else {
        bool = false;
      }
      return bool;
    }
    
    public K getKey()
    {
      return (K)MapBuilder.access$getKeysArray$p(this.map)[this.index];
    }
    
    public V getValue()
    {
      Object[] arrayOfObject = MapBuilder.access$getValuesArray$p(this.map);
      Intrinsics.checkNotNull(arrayOfObject);
      return (V)arrayOfObject[this.index];
    }
    
    public int hashCode()
    {
      Object localObject = getKey();
      int j = 0;
      int i;
      if (localObject != null) {
        i = localObject.hashCode();
      } else {
        i = 0;
      }
      localObject = getValue();
      if (localObject != null) {
        j = localObject.hashCode();
      }
      return i ^ j;
    }
    
    public V setValue(V paramV)
    {
      this.map.checkIsMutable$kotlin_stdlib();
      Object[] arrayOfObject = MapBuilder.access$allocateValuesArray(this.map);
      int i = this.index;
      Object localObject = arrayOfObject[i];
      arrayOfObject[i] = paramV;
      return (V)localObject;
    }
    
    public String toString()
    {
      return getKey() + '=' + getValue();
    }
  }
  
  @Metadata(d1={"\000,\n\002\030\002\n\002\b\002\n\002\020\000\n\000\n\002\030\002\n\002\b\002\n\002\020\b\n\002\b\n\n\002\020\013\n\000\n\002\020\002\n\002\b\003\b\020\030\000*\004\b\002\020\001*\004\b\003\020\0022\0020\003B\031\022\022\020\004\032\016\022\004\022\0028\002\022\004\022\0028\0030\005¢\006\002\020\006J\006\020\022\032\0020\023J\r\020\024\032\0020\025H\000¢\006\002\b\026J\006\020\027\032\0020\025R\032\020\007\032\0020\bX\016¢\006\016\n\000\032\004\b\t\020\n\"\004\b\013\020\fR\032\020\r\032\0020\bX\016¢\006\016\n\000\032\004\b\016\020\n\"\004\b\017\020\fR \020\004\032\016\022\004\022\0028\002\022\004\022\0028\0030\005X\004¢\006\b\n\000\032\004\b\020\020\021¨\006\030"}, d2={"Lkotlin/collections/builders/MapBuilder$Itr;", "K", "V", "", "map", "Lkotlin/collections/builders/MapBuilder;", "(Lkotlin/collections/builders/MapBuilder;)V", "index", "", "getIndex$kotlin_stdlib", "()I", "setIndex$kotlin_stdlib", "(I)V", "lastIndex", "getLastIndex$kotlin_stdlib", "setLastIndex$kotlin_stdlib", "getMap$kotlin_stdlib", "()Lkotlin/collections/builders/MapBuilder;", "hasNext", "", "initNext", "", "initNext$kotlin_stdlib", "remove", "kotlin-stdlib"}, k=1, mv={1, 7, 1}, xi=48)
  public static class Itr<K, V>
  {
    private int index;
    private int lastIndex;
    private final MapBuilder<K, V> map;
    
    public Itr(MapBuilder<K, V> paramMapBuilder)
    {
      this.map = paramMapBuilder;
      this.lastIndex = -1;
      initNext$kotlin_stdlib();
    }
    
    public final int getIndex$kotlin_stdlib()
    {
      return this.index;
    }
    
    public final int getLastIndex$kotlin_stdlib()
    {
      return this.lastIndex;
    }
    
    public final MapBuilder<K, V> getMap$kotlin_stdlib()
    {
      return this.map;
    }
    
    public final boolean hasNext()
    {
      boolean bool;
      if (this.index < MapBuilder.access$getLength$p(this.map)) {
        bool = true;
      } else {
        bool = false;
      }
      return bool;
    }
    
    public final void initNext$kotlin_stdlib()
    {
      while (this.index < MapBuilder.access$getLength$p(this.map))
      {
        int[] arrayOfInt = MapBuilder.access$getPresenceArray$p(this.map);
        int i = this.index;
        if (arrayOfInt[i] >= 0) {
          break;
        }
        this.index = (i + 1);
      }
    }
    
    public final void remove()
    {
      int i;
      if (this.lastIndex != -1) {
        i = 1;
      } else {
        i = 0;
      }
      if (i != 0)
      {
        this.map.checkIsMutable$kotlin_stdlib();
        MapBuilder.access$removeKeyAt(this.map, this.lastIndex);
        this.lastIndex = -1;
        return;
      }
      throw new IllegalStateException("Call next() before removing element from the iterator.".toString());
    }
    
    public final void setIndex$kotlin_stdlib(int paramInt)
    {
      this.index = paramInt;
    }
    
    public final void setLastIndex$kotlin_stdlib(int paramInt)
    {
      this.lastIndex = paramInt;
    }
  }
  
  @Metadata(d1={"\000\032\n\002\030\002\n\002\b\002\n\002\030\002\n\002\020)\n\000\n\002\030\002\n\002\b\004\b\000\030\000*\004\b\002\020\001*\004\b\003\020\0022\016\022\004\022\002H\001\022\004\022\002H\0020\0032\b\022\004\022\002H\0010\004B\031\022\022\020\005\032\016\022\004\022\0028\002\022\004\022\0028\0030\006¢\006\002\020\007J\016\020\b\032\0028\002H\002¢\006\002\020\t¨\006\n"}, d2={"Lkotlin/collections/builders/MapBuilder$KeysItr;", "K", "V", "Lkotlin/collections/builders/MapBuilder$Itr;", "", "map", "Lkotlin/collections/builders/MapBuilder;", "(Lkotlin/collections/builders/MapBuilder;)V", "next", "()Ljava/lang/Object;", "kotlin-stdlib"}, k=1, mv={1, 7, 1}, xi=48)
  public static final class KeysItr<K, V>
    extends MapBuilder.Itr<K, V>
    implements Iterator<K>, KMutableIterator
  {
    public KeysItr(MapBuilder<K, V> paramMapBuilder)
    {
      super();
    }
    
    public K next()
    {
      if (getIndex$kotlin_stdlib() < MapBuilder.access$getLength$p(getMap$kotlin_stdlib()))
      {
        int i = getIndex$kotlin_stdlib();
        setIndex$kotlin_stdlib(i + 1);
        setLastIndex$kotlin_stdlib(i);
        Object localObject = MapBuilder.access$getKeysArray$p(getMap$kotlin_stdlib())[getLastIndex$kotlin_stdlib()];
        initNext$kotlin_stdlib();
        return (K)localObject;
      }
      throw new NoSuchElementException();
    }
  }
  
  @Metadata(d1={"\000\032\n\002\030\002\n\002\b\002\n\002\030\002\n\002\020)\n\000\n\002\030\002\n\002\b\004\b\000\030\000*\004\b\002\020\001*\004\b\003\020\0022\016\022\004\022\002H\001\022\004\022\002H\0020\0032\b\022\004\022\002H\0020\004B\031\022\022\020\005\032\016\022\004\022\0028\002\022\004\022\0028\0030\006¢\006\002\020\007J\016\020\b\032\0028\003H\002¢\006\002\020\t¨\006\n"}, d2={"Lkotlin/collections/builders/MapBuilder$ValuesItr;", "K", "V", "Lkotlin/collections/builders/MapBuilder$Itr;", "", "map", "Lkotlin/collections/builders/MapBuilder;", "(Lkotlin/collections/builders/MapBuilder;)V", "next", "()Ljava/lang/Object;", "kotlin-stdlib"}, k=1, mv={1, 7, 1}, xi=48)
  public static final class ValuesItr<K, V>
    extends MapBuilder.Itr<K, V>
    implements Iterator<V>, KMutableIterator
  {
    public ValuesItr(MapBuilder<K, V> paramMapBuilder)
    {
      super();
    }
    
    public V next()
    {
      if (getIndex$kotlin_stdlib() < MapBuilder.access$getLength$p(getMap$kotlin_stdlib()))
      {
        int i = getIndex$kotlin_stdlib();
        setIndex$kotlin_stdlib(i + 1);
        setLastIndex$kotlin_stdlib(i);
        Object localObject = MapBuilder.access$getValuesArray$p(getMap$kotlin_stdlib());
        Intrinsics.checkNotNull(localObject);
        localObject = localObject[getLastIndex$kotlin_stdlib()];
        initNext$kotlin_stdlib();
        return (V)localObject;
      }
      throw new NoSuchElementException();
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlin/collections/builders/MapBuilder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */