package kotlin.collections;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;

@Metadata(d1={"\000L\n\002\030\002\n\000\n\002\030\002\n\000\n\002\020\b\n\002\b\003\n\002\020\036\n\002\b\002\n\002\020\021\n\002\020\000\n\002\b\007\n\002\020\013\n\002\b\002\n\002\020\002\n\002\b\021\n\002\030\002\n\002\b\013\n\002\030\002\n\002\030\002\n\002\b\033\b\007\030\000 P*\004\b\000\020\0012\b\022\004\022\002H\0010\002:\001PB\017\b\026\022\006\020\003\032\0020\004¢\006\002\020\005B\007\b\026¢\006\002\020\006B\025\b\026\022\f\020\007\032\b\022\004\022\0028\0000\b¢\006\002\020\tJ\025\020\023\032\0020\0242\006\020\025\032\0028\000H\026¢\006\002\020\026J\035\020\023\032\0020\0272\006\020\030\032\0020\0042\006\020\025\032\0028\000H\026¢\006\002\020\031J\036\020\032\032\0020\0242\006\020\030\032\0020\0042\f\020\007\032\b\022\004\022\0028\0000\bH\026J\026\020\032\032\0020\0242\f\020\007\032\b\022\004\022\0028\0000\bH\026J\023\020\033\032\0020\0272\006\020\025\032\0028\000¢\006\002\020\034J\023\020\035\032\0020\0272\006\020\025\032\0028\000¢\006\002\020\034J\b\020\036\032\0020\027H\026J\026\020\037\032\0020\0242\006\020\025\032\0028\000H\002¢\006\002\020\026J\036\020 \032\0020\0272\006\020!\032\0020\0042\f\020\007\032\b\022\004\022\0028\0000\bH\002J\020\020\"\032\0020\0272\006\020#\032\0020\004H\002J\020\020$\032\0020\0042\006\020\030\032\0020\004H\002J\020\020%\032\0020\0272\006\020&\032\0020\004H\002J\035\020'\032\0020\0242\022\020(\032\016\022\004\022\0028\000\022\004\022\0020\0240)H\bJ\013\020*\032\0028\000¢\006\002\020+J\r\020,\032\004\030\0018\000¢\006\002\020+J\026\020-\032\0028\0002\006\020\030\032\0020\004H\002¢\006\002\020.J\020\020/\032\0020\0042\006\020\030\032\0020\004H\002J\025\0200\032\0020\0042\006\020\025\032\0028\000H\026¢\006\002\0201J\026\0202\032\0028\0002\006\020!\032\0020\004H\b¢\006\002\020.J\021\020!\032\0020\0042\006\020\030\032\0020\004H\bJM\0203\032\0020\0272>\0204\032:\022\023\022\0210\004¢\006\f\b6\022\b\b7\022\004\b\b(\016\022\033\022\031\022\006\022\004\030\0010\f0\013¢\006\f\b6\022\b\b7\022\004\b\b(\007\022\004\022\0020\02705H\000¢\006\002\b8J\b\0209\032\0020\024H\026J\013\020:\032\0028\000¢\006\002\020+J\025\020;\032\0020\0042\006\020\025\032\0028\000H\026¢\006\002\0201J\r\020<\032\004\030\0018\000¢\006\002\020+J\020\020=\032\0020\0042\006\020\030\032\0020\004H\002J\020\020>\032\0020\0042\006\020\030\032\0020\004H\002J\025\020?\032\0020\0242\006\020\025\032\0028\000H\026¢\006\002\020\026J\026\020@\032\0020\0242\f\020\007\032\b\022\004\022\0028\0000\bH\026J\025\020A\032\0028\0002\006\020\030\032\0020\004H\026¢\006\002\020.J\013\020B\032\0028\000¢\006\002\020+J\r\020C\032\004\030\0018\000¢\006\002\020+J\013\020D\032\0028\000¢\006\002\020+J\r\020E\032\004\030\0018\000¢\006\002\020+J\026\020F\032\0020\0242\f\020\007\032\b\022\004\022\0028\0000\bH\026J\036\020G\032\0028\0002\006\020\030\032\0020\0042\006\020\025\032\0028\000H\002¢\006\002\020HJ\027\020I\032\n\022\006\022\004\030\0010\f0\013H\000¢\006\004\bJ\020KJ)\020I\032\b\022\004\022\002HL0\013\"\004\b\001\020L2\f\020M\032\b\022\004\022\002HL0\013H\000¢\006\004\bJ\020NJ\025\020O\032\n\022\006\022\004\030\0010\f0\013H\026¢\006\002\020KJ'\020O\032\b\022\004\022\002HL0\013\"\004\b\001\020L2\f\020M\032\b\022\004\022\002HL0\013H\026¢\006\002\020NR\030\020\n\032\n\022\006\022\004\030\0010\f0\013X\016¢\006\004\n\002\020\rR\016\020\016\032\0020\004X\016¢\006\002\n\000R\036\020\020\032\0020\0042\006\020\017\032\0020\004@RX\016¢\006\b\n\000\032\004\b\021\020\022¨\006Q"}, d2={"Lkotlin/collections/ArrayDeque;", "E", "Lkotlin/collections/AbstractMutableList;", "initialCapacity", "", "(I)V", "()V", "elements", "", "(Ljava/util/Collection;)V", "elementData", "", "", "[Ljava/lang/Object;", "head", "<set-?>", "size", "getSize", "()I", "add", "", "element", "(Ljava/lang/Object;)Z", "", "index", "(ILjava/lang/Object;)V", "addAll", "addFirst", "(Ljava/lang/Object;)V", "addLast", "clear", "contains", "copyCollectionElements", "internalIndex", "copyElements", "newCapacity", "decremented", "ensureCapacity", "minCapacity", "filterInPlace", "predicate", "Lkotlin/Function1;", "first", "()Ljava/lang/Object;", "firstOrNull", "get", "(I)Ljava/lang/Object;", "incremented", "indexOf", "(Ljava/lang/Object;)I", "internalGet", "internalStructure", "structure", "Lkotlin/Function2;", "Lkotlin/ParameterName;", "name", "internalStructure$kotlin_stdlib", "isEmpty", "last", "lastIndexOf", "lastOrNull", "negativeMod", "positiveMod", "remove", "removeAll", "removeAt", "removeFirst", "removeFirstOrNull", "removeLast", "removeLastOrNull", "retainAll", "set", "(ILjava/lang/Object;)Ljava/lang/Object;", "testToArray", "testToArray$kotlin_stdlib", "()[Ljava/lang/Object;", "T", "array", "([Ljava/lang/Object;)[Ljava/lang/Object;", "toArray", "Companion", "kotlin-stdlib"}, k=1, mv={1, 7, 1}, xi=48)
public final class ArrayDeque<E>
  extends AbstractMutableList<E>
{
  public static final Companion Companion = new Companion(null);
  private static final int defaultMinCapacity = 10;
  private static final Object[] emptyElementData = new Object[0];
  private static final int maxArraySize = 2147483639;
  private Object[] elementData;
  private int head;
  private int size;
  
  public ArrayDeque()
  {
    this.elementData = emptyElementData;
  }
  
  public ArrayDeque(int paramInt)
  {
    Object[] arrayOfObject;
    if (paramInt == 0)
    {
      arrayOfObject = emptyElementData;
    }
    else
    {
      if (paramInt <= 0) {
        break label30;
      }
      arrayOfObject = new Object[paramInt];
    }
    this.elementData = arrayOfObject;
    return;
    label30:
    throw new IllegalArgumentException("Illegal Capacity: " + paramInt);
  }
  
  public ArrayDeque(Collection<? extends E> paramCollection)
  {
    int i = 0;
    paramCollection = paramCollection.toArray(new Object[0]);
    Intrinsics.checkNotNull(paramCollection, "null cannot be cast to non-null type kotlin.Array<T of kotlin.collections.ArraysKt__ArraysJVMKt.toTypedArray>");
    this.elementData = paramCollection;
    this.size = paramCollection.length;
    if (paramCollection.length == 0) {
      i = 1;
    }
    if (i != 0) {
      this.elementData = emptyElementData;
    }
  }
  
  private final void copyCollectionElements(int paramInt, Collection<? extends E> paramCollection)
  {
    Iterator localIterator = paramCollection.iterator();
    int i = this.elementData.length;
    while ((paramInt < i) && (localIterator.hasNext()))
    {
      this.elementData[paramInt] = localIterator.next();
      paramInt++;
    }
    paramInt = 0;
    i = this.head;
    while ((paramInt < i) && (localIterator.hasNext()))
    {
      this.elementData[paramInt] = localIterator.next();
      paramInt++;
    }
    this.size = (size() + paramCollection.size());
  }
  
  private final void copyElements(int paramInt)
  {
    Object[] arrayOfObject1 = new Object[paramInt];
    Object[] arrayOfObject2 = this.elementData;
    ArraysKt.copyInto(arrayOfObject2, arrayOfObject1, 0, this.head, arrayOfObject2.length);
    arrayOfObject2 = this.elementData;
    int i = arrayOfObject2.length;
    paramInt = this.head;
    ArraysKt.copyInto(arrayOfObject2, arrayOfObject1, i - paramInt, 0, paramInt);
    this.head = 0;
    this.elementData = arrayOfObject1;
  }
  
  private final int decremented(int paramInt)
  {
    if (paramInt == 0) {
      paramInt = ArraysKt.getLastIndex(this.elementData);
    } else {
      paramInt--;
    }
    return paramInt;
  }
  
  private final void ensureCapacity(int paramInt)
  {
    if (paramInt >= 0)
    {
      Object[] arrayOfObject = this.elementData;
      if (paramInt <= arrayOfObject.length) {
        return;
      }
      if (arrayOfObject == emptyElementData)
      {
        this.elementData = new Object[RangesKt.coerceAtLeast(paramInt, 10)];
        return;
      }
      copyElements(Companion.newCapacity$kotlin_stdlib(arrayOfObject.length, paramInt));
      return;
    }
    throw new IllegalStateException("Deque is too big.");
  }
  
  private final boolean filterInPlace(Function1<? super E, Boolean> paramFunction1)
  {
    if (!isEmpty())
    {
      int i;
      if (this.elementData.length == 0) {
        i = 1;
      } else {
        i = 0;
      }
      if (i == 0)
      {
        int m = positiveMod(this.head + size());
        int j = this.head;
        boolean bool2 = false;
        boolean bool1 = false;
        int k;
        Object localObject1;
        if (this.head < m)
        {
          k = this.head;
          i = j;
          while (k < m)
          {
            localObject1 = this.elementData[k];
            if (((Boolean)paramFunction1.invoke(localObject1)).booleanValue())
            {
              this.elementData[i] = localObject1;
              i++;
            }
            else
            {
              bool1 = true;
            }
            k++;
          }
          ArraysKt.fill(this.elementData, null, i, m);
          k = i;
          bool2 = bool1;
        }
        else
        {
          i = this.head;
          k = this.elementData.length;
          bool1 = bool2;
          Object localObject2;
          while (i < k)
          {
            localObject1 = this.elementData;
            localObject2 = localObject1[i];
            localObject1[i] = null;
            if (((Boolean)paramFunction1.invoke(localObject2)).booleanValue())
            {
              this.elementData[j] = localObject2;
              j++;
            }
            else
            {
              bool1 = true;
            }
            i++;
          }
          i = positiveMod(j);
          for (j = 0;; j++)
          {
            k = i;
            bool2 = bool1;
            if (j >= m) {
              break;
            }
            localObject1 = this.elementData;
            localObject2 = localObject1[j];
            localObject1[j] = null;
            if (((Boolean)paramFunction1.invoke(localObject2)).booleanValue())
            {
              this.elementData[i] = localObject2;
              i = incremented(i);
            }
            else
            {
              bool1 = true;
            }
          }
        }
        if (bool2) {
          this.size = negativeMod(k - this.head);
        }
        return bool2;
      }
    }
    return false;
  }
  
  private final int incremented(int paramInt)
  {
    if (paramInt == ArraysKt.getLastIndex(this.elementData)) {
      paramInt = 0;
    } else {
      paramInt++;
    }
    return paramInt;
  }
  
  private final E internalGet(int paramInt)
  {
    return (E)this.elementData[paramInt];
  }
  
  private final int internalIndex(int paramInt)
  {
    return positiveMod(this.head + paramInt);
  }
  
  private final int negativeMod(int paramInt)
  {
    if (paramInt < 0) {
      paramInt = this.elementData.length + paramInt;
    }
    return paramInt;
  }
  
  private final int positiveMod(int paramInt)
  {
    Object[] arrayOfObject = this.elementData;
    if (paramInt >= arrayOfObject.length) {
      paramInt -= arrayOfObject.length;
    }
    return paramInt;
  }
  
  public void add(int paramInt, E paramE)
  {
    AbstractList.Companion.checkPositionIndex$kotlin_stdlib(paramInt, size());
    if (paramInt == size())
    {
      addLast(paramE);
      return;
    }
    if (paramInt == 0)
    {
      addFirst(paramE);
      return;
    }
    ensureCapacity(size() + 1);
    int i = positiveMod(this.head + paramInt);
    Object[] arrayOfObject;
    if (paramInt < size() + 1 >> 1)
    {
      int j = decremented(i);
      paramInt = decremented(this.head);
      i = this.head;
      if (j >= i)
      {
        arrayOfObject = this.elementData;
        arrayOfObject[paramInt] = arrayOfObject[i];
        ArraysKt.copyInto(arrayOfObject, arrayOfObject, i, i + 1, j + 1);
      }
      else
      {
        arrayOfObject = this.elementData;
        ArraysKt.copyInto(arrayOfObject, arrayOfObject, i - 1, i, arrayOfObject.length);
        arrayOfObject = this.elementData;
        arrayOfObject[(arrayOfObject.length - 1)] = arrayOfObject[0];
        ArraysKt.copyInto(arrayOfObject, arrayOfObject, 0, 1, j + 1);
      }
      this.elementData[j] = paramE;
      this.head = paramInt;
    }
    else
    {
      paramInt = positiveMod(this.head + size());
      if (i < paramInt)
      {
        arrayOfObject = this.elementData;
        ArraysKt.copyInto(arrayOfObject, arrayOfObject, i + 1, i, paramInt);
      }
      else
      {
        arrayOfObject = this.elementData;
        ArraysKt.copyInto(arrayOfObject, arrayOfObject, 1, 0, paramInt);
        arrayOfObject = this.elementData;
        arrayOfObject[0] = arrayOfObject[(arrayOfObject.length - 1)];
        ArraysKt.copyInto(arrayOfObject, arrayOfObject, i + 1, i, arrayOfObject.length - 1);
      }
      this.elementData[i] = paramE;
    }
    this.size = (size() + 1);
  }
  
  public boolean add(E paramE)
  {
    addLast(paramE);
    return true;
  }
  
  public boolean addAll(int paramInt, Collection<? extends E> paramCollection)
  {
    Intrinsics.checkNotNullParameter(paramCollection, "elements");
    AbstractList.Companion.checkPositionIndex$kotlin_stdlib(paramInt, size());
    if (paramCollection.isEmpty()) {
      return false;
    }
    if (paramInt == size()) {
      return addAll(paramCollection);
    }
    ensureCapacity(size() + paramCollection.size());
    int k = positiveMod(this.head + size());
    int i = positiveMod(this.head + paramInt);
    int j = paramCollection.size();
    Object[] arrayOfObject;
    if (paramInt < size() + 1 >> 1)
    {
      k = this.head;
      paramInt = k - j;
      if (i >= k)
      {
        if (paramInt >= 0)
        {
          arrayOfObject = this.elementData;
          ArraysKt.copyInto(arrayOfObject, arrayOfObject, paramInt, k, i);
        }
        else
        {
          arrayOfObject = this.elementData;
          paramInt += arrayOfObject.length;
          int m = arrayOfObject.length - paramInt;
          if (m >= i - k)
          {
            ArraysKt.copyInto(arrayOfObject, arrayOfObject, paramInt, k, i);
          }
          else
          {
            ArraysKt.copyInto(arrayOfObject, arrayOfObject, paramInt, k, k + m);
            arrayOfObject = this.elementData;
            ArraysKt.copyInto(arrayOfObject, arrayOfObject, 0, this.head + m, i);
          }
        }
      }
      else
      {
        arrayOfObject = this.elementData;
        ArraysKt.copyInto(arrayOfObject, arrayOfObject, paramInt, k, arrayOfObject.length);
        if (j >= i)
        {
          arrayOfObject = this.elementData;
          ArraysKt.copyInto(arrayOfObject, arrayOfObject, arrayOfObject.length - j, 0, i);
        }
        else
        {
          arrayOfObject = this.elementData;
          ArraysKt.copyInto(arrayOfObject, arrayOfObject, arrayOfObject.length - j, 0, j);
          arrayOfObject = this.elementData;
          ArraysKt.copyInto(arrayOfObject, arrayOfObject, 0, j, i);
        }
      }
      this.head = paramInt;
      copyCollectionElements(negativeMod(i - j), paramCollection);
    }
    else
    {
      paramInt = i + j;
      if (i < k)
      {
        arrayOfObject = this.elementData;
        if (k + j <= arrayOfObject.length)
        {
          ArraysKt.copyInto(arrayOfObject, arrayOfObject, paramInt, i, k);
        }
        else if (paramInt >= arrayOfObject.length)
        {
          ArraysKt.copyInto(arrayOfObject, arrayOfObject, paramInt - arrayOfObject.length, i, k);
        }
        else
        {
          j = k + j - arrayOfObject.length;
          ArraysKt.copyInto(arrayOfObject, arrayOfObject, 0, k - j, k);
          arrayOfObject = this.elementData;
          ArraysKt.copyInto(arrayOfObject, arrayOfObject, paramInt, i, k - j);
        }
      }
      else
      {
        arrayOfObject = this.elementData;
        ArraysKt.copyInto(arrayOfObject, arrayOfObject, j, 0, k);
        arrayOfObject = this.elementData;
        if (paramInt >= arrayOfObject.length)
        {
          ArraysKt.copyInto(arrayOfObject, arrayOfObject, paramInt - arrayOfObject.length, i, arrayOfObject.length);
        }
        else
        {
          ArraysKt.copyInto(arrayOfObject, arrayOfObject, 0, arrayOfObject.length - j, arrayOfObject.length);
          arrayOfObject = this.elementData;
          ArraysKt.copyInto(arrayOfObject, arrayOfObject, paramInt, i, arrayOfObject.length - j);
        }
      }
      copyCollectionElements(i, paramCollection);
    }
    return true;
  }
  
  public boolean addAll(Collection<? extends E> paramCollection)
  {
    Intrinsics.checkNotNullParameter(paramCollection, "elements");
    if (paramCollection.isEmpty()) {
      return false;
    }
    ensureCapacity(size() + paramCollection.size());
    copyCollectionElements(positiveMod(this.head + size()), paramCollection);
    return true;
  }
  
  public final void addFirst(E paramE)
  {
    ensureCapacity(size() + 1);
    int i = decremented(this.head);
    this.head = i;
    this.elementData[i] = paramE;
    this.size = (size() + 1);
  }
  
  public final void addLast(E paramE)
  {
    ensureCapacity(size() + 1);
    this.elementData[positiveMod(this.head + size())] = paramE;
    this.size = (size() + 1);
  }
  
  public void clear()
  {
    int i = positiveMod(this.head + size());
    int j = this.head;
    if (j < i)
    {
      ArraysKt.fill(this.elementData, null, j, i);
    }
    else if ((((Collection)this).isEmpty() ^ true))
    {
      Object[] arrayOfObject = this.elementData;
      ArraysKt.fill(arrayOfObject, null, this.head, arrayOfObject.length);
      ArraysKt.fill(this.elementData, null, 0, i);
    }
    this.head = 0;
    this.size = 0;
  }
  
  public boolean contains(Object paramObject)
  {
    boolean bool;
    if (indexOf(paramObject) != -1) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public final E first()
  {
    if (!isEmpty()) {
      return (E)this.elementData[this.head];
    }
    throw new NoSuchElementException("ArrayDeque is empty.");
  }
  
  public final E firstOrNull()
  {
    Object localObject;
    if (isEmpty()) {
      localObject = null;
    } else {
      localObject = this.elementData[this.head];
    }
    return (E)localObject;
  }
  
  public E get(int paramInt)
  {
    AbstractList.Companion.checkElementIndex$kotlin_stdlib(paramInt, size());
    return (E)this.elementData[positiveMod(this.head + paramInt)];
  }
  
  public int getSize()
  {
    return this.size;
  }
  
  public int indexOf(Object paramObject)
  {
    int j = positiveMod(this.head + size());
    int i = this.head;
    if (i < j) {
      for (i = this.head; i < j; i++) {
        if (Intrinsics.areEqual(paramObject, this.elementData[i])) {
          return i - this.head;
        }
      }
    }
    if (i >= j)
    {
      i = this.head;
      int k = this.elementData.length;
      while (i < k)
      {
        if (Intrinsics.areEqual(paramObject, this.elementData[i])) {
          return i - this.head;
        }
        i++;
      }
      for (i = 0; i < j; i++) {
        if (Intrinsics.areEqual(paramObject, this.elementData[i])) {
          return this.elementData.length + i - this.head;
        }
      }
    }
    return -1;
  }
  
  public final void internalStructure$kotlin_stdlib(Function2<? super Integer, ? super Object[], Unit> paramFunction2)
  {
    Intrinsics.checkNotNullParameter(paramFunction2, "structure");
    int i = positiveMod(this.head + size());
    if (!isEmpty())
    {
      int j = this.head;
      if (j >= i)
      {
        i = j - this.elementData.length;
        break label57;
      }
    }
    i = this.head;
    label57:
    paramFunction2.invoke(Integer.valueOf(i), toArray());
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
  
  public final E last()
  {
    if (!isEmpty()) {
      return (E)this.elementData[positiveMod(this.head + CollectionsKt.getLastIndex((List)this))];
    }
    throw new NoSuchElementException("ArrayDeque is empty.");
  }
  
  public int lastIndexOf(Object paramObject)
  {
    int i = positiveMod(this.head + size());
    int j = this.head;
    if (j < i)
    {
      i--;
      if (j <= i) {
        for (;;)
        {
          if (Intrinsics.areEqual(paramObject, this.elementData[i])) {
            return i - this.head;
          }
          if (i == j) {
            break;
          }
          i--;
        }
      }
    }
    else if (j > i)
    {
      i--;
      while (-1 < i)
      {
        if (Intrinsics.areEqual(paramObject, this.elementData[i])) {
          return this.elementData.length + i - this.head;
        }
        i--;
      }
      i = ArraysKt.getLastIndex(this.elementData);
      j = this.head;
      if (j <= i) {
        for (;;)
        {
          if (Intrinsics.areEqual(paramObject, this.elementData[i])) {
            return i - this.head;
          }
          if (i == j) {
            break;
          }
          i--;
        }
      }
    }
    return -1;
  }
  
  public final E lastOrNull()
  {
    Object localObject;
    if (isEmpty()) {
      localObject = null;
    } else {
      localObject = this.elementData[positiveMod(this.head + CollectionsKt.getLastIndex((List)this))];
    }
    return (E)localObject;
  }
  
  public boolean remove(Object paramObject)
  {
    int i = indexOf(paramObject);
    if (i == -1) {
      return false;
    }
    remove(i);
    return true;
  }
  
  public boolean removeAll(Collection<? extends Object> paramCollection)
  {
    Intrinsics.checkNotNullParameter(paramCollection, "elements");
    boolean bool2 = isEmpty();
    boolean bool1 = false;
    if (!bool2)
    {
      int i;
      if (this.elementData.length == 0) {
        i = 1;
      } else {
        i = 0;
      }
      if (i == 0)
      {
        int m = positiveMod(this.head + size());
        int j = this.head;
        bool1 = false;
        bool2 = false;
        int k;
        Object localObject;
        if (this.head < m)
        {
          k = this.head;
          bool1 = bool2;
          i = j;
          while (k < m)
          {
            localObject = this.elementData[k];
            if ((paramCollection.contains(localObject) ^ true))
            {
              this.elementData[i] = localObject;
              i++;
            }
            else
            {
              bool1 = true;
            }
            k++;
          }
          ArraysKt.fill(this.elementData, null, i, m);
        }
        else
        {
          i = this.head;
          k = this.elementData.length;
          Object[] arrayOfObject;
          while (i < k)
          {
            arrayOfObject = this.elementData;
            localObject = arrayOfObject[i];
            arrayOfObject[i] = null;
            if ((paramCollection.contains(localObject) ^ true))
            {
              this.elementData[j] = localObject;
              j++;
            }
            else
            {
              bool1 = true;
            }
            i++;
          }
          i = positiveMod(j);
          for (j = 0; j < m; j++)
          {
            localObject = this.elementData;
            arrayOfObject = localObject[j];
            localObject[j] = null;
            if ((paramCollection.contains(arrayOfObject) ^ true))
            {
              this.elementData[i] = arrayOfObject;
              i = incremented(i);
            }
            else
            {
              bool1 = true;
            }
          }
        }
        if (bool1) {
          this.size = negativeMod(i - this.head);
        }
      }
    }
    return bool1;
  }
  
  public E removeAt(int paramInt)
  {
    AbstractList.Companion.checkElementIndex$kotlin_stdlib(paramInt, size());
    if (paramInt == CollectionsKt.getLastIndex((List)this)) {
      return (E)removeLast();
    }
    if (paramInt == 0) {
      return (E)removeFirst();
    }
    int i = positiveMod(this.head + paramInt);
    Object localObject = this.elementData[i];
    Object[] arrayOfObject;
    if (paramInt < size() >> 1)
    {
      paramInt = this.head;
      if (i >= paramInt)
      {
        arrayOfObject = this.elementData;
        ArraysKt.copyInto(arrayOfObject, arrayOfObject, paramInt + 1, paramInt, i);
      }
      else
      {
        arrayOfObject = this.elementData;
        ArraysKt.copyInto(arrayOfObject, arrayOfObject, 1, 0, i);
        arrayOfObject = this.elementData;
        arrayOfObject[0] = arrayOfObject[(arrayOfObject.length - 1)];
        paramInt = this.head;
        ArraysKt.copyInto(arrayOfObject, arrayOfObject, paramInt + 1, paramInt, arrayOfObject.length - 1);
      }
      arrayOfObject = this.elementData;
      paramInt = this.head;
      arrayOfObject[paramInt] = null;
      this.head = incremented(paramInt);
    }
    else
    {
      paramInt = positiveMod(this.head + CollectionsKt.getLastIndex((List)this));
      if (i <= paramInt)
      {
        arrayOfObject = this.elementData;
        ArraysKt.copyInto(arrayOfObject, arrayOfObject, i, i + 1, paramInt + 1);
      }
      else
      {
        arrayOfObject = this.elementData;
        ArraysKt.copyInto(arrayOfObject, arrayOfObject, i, i + 1, arrayOfObject.length);
        arrayOfObject = this.elementData;
        arrayOfObject[(arrayOfObject.length - 1)] = arrayOfObject[0];
        ArraysKt.copyInto(arrayOfObject, arrayOfObject, 0, 1, paramInt + 1);
      }
      this.elementData[paramInt] = null;
    }
    this.size = (size() - 1);
    return (E)localObject;
  }
  
  public final E removeFirst()
  {
    if (!isEmpty())
    {
      Object[] arrayOfObject = this.elementData;
      int i = this.head;
      Object localObject = arrayOfObject[i];
      arrayOfObject[i] = null;
      this.head = incremented(i);
      this.size = (size() - 1);
      return (E)localObject;
    }
    throw new NoSuchElementException("ArrayDeque is empty.");
  }
  
  public final E removeFirstOrNull()
  {
    Object localObject;
    if (isEmpty()) {
      localObject = null;
    } else {
      localObject = removeFirst();
    }
    return (E)localObject;
  }
  
  public final E removeLast()
  {
    if (!isEmpty())
    {
      int i = positiveMod(this.head + CollectionsKt.getLastIndex((List)this));
      Object[] arrayOfObject = this.elementData;
      Object localObject = arrayOfObject[i];
      arrayOfObject[i] = null;
      this.size = (size() - 1);
      return (E)localObject;
    }
    throw new NoSuchElementException("ArrayDeque is empty.");
  }
  
  public final E removeLastOrNull()
  {
    Object localObject;
    if (isEmpty()) {
      localObject = null;
    } else {
      localObject = removeLast();
    }
    return (E)localObject;
  }
  
  public boolean retainAll(Collection<? extends Object> paramCollection)
  {
    Intrinsics.checkNotNullParameter(paramCollection, "elements");
    boolean bool2 = isEmpty();
    boolean bool1 = false;
    if (!bool2)
    {
      int i;
      if (this.elementData.length == 0) {
        i = 1;
      } else {
        i = 0;
      }
      if (i == 0)
      {
        int m = positiveMod(this.head + size());
        int j = this.head;
        bool2 = false;
        bool1 = false;
        int k;
        Object localObject;
        if (this.head < m)
        {
          k = this.head;
          i = j;
          while (k < m)
          {
            localObject = this.elementData[k];
            if (paramCollection.contains(localObject))
            {
              this.elementData[i] = localObject;
              i++;
            }
            else
            {
              bool1 = true;
            }
            k++;
          }
          ArraysKt.fill(this.elementData, null, i, m);
        }
        else
        {
          i = this.head;
          k = this.elementData.length;
          bool1 = bool2;
          Object[] arrayOfObject;
          while (i < k)
          {
            arrayOfObject = this.elementData;
            localObject = arrayOfObject[i];
            arrayOfObject[i] = null;
            if (paramCollection.contains(localObject))
            {
              this.elementData[j] = localObject;
              j++;
            }
            else
            {
              bool1 = true;
            }
            i++;
          }
          i = positiveMod(j);
          for (j = 0; j < m; j++)
          {
            arrayOfObject = this.elementData;
            localObject = arrayOfObject[j];
            arrayOfObject[j] = null;
            if (paramCollection.contains(localObject))
            {
              this.elementData[i] = localObject;
              i = incremented(i);
            }
            else
            {
              bool1 = true;
            }
          }
        }
        if (bool1) {
          this.size = negativeMod(i - this.head);
        }
      }
    }
    return bool1;
  }
  
  public E set(int paramInt, E paramE)
  {
    AbstractList.Companion.checkElementIndex$kotlin_stdlib(paramInt, size());
    paramInt = positiveMod(this.head + paramInt);
    Object[] arrayOfObject = this.elementData;
    Object localObject = arrayOfObject[paramInt];
    arrayOfObject[paramInt] = paramE;
    return (E)localObject;
  }
  
  public final Object[] testToArray$kotlin_stdlib()
  {
    return toArray();
  }
  
  public final <T> T[] testToArray$kotlin_stdlib(T[] paramArrayOfT)
  {
    Intrinsics.checkNotNullParameter(paramArrayOfT, "array");
    return toArray(paramArrayOfT);
  }
  
  public Object[] toArray()
  {
    return toArray(new Object[size()]);
  }
  
  public <T> T[] toArray(T[] paramArrayOfT)
  {
    Intrinsics.checkNotNullParameter(paramArrayOfT, "array");
    if (paramArrayOfT.length < size()) {
      paramArrayOfT = ArraysKt.arrayOfNulls(paramArrayOfT, size());
    }
    Intrinsics.checkNotNull(paramArrayOfT, "null cannot be cast to non-null type kotlin.Array<kotlin.Any?>");
    int i = positiveMod(this.head + size());
    int j = this.head;
    if (j < i)
    {
      ArraysKt.copyInto$default(this.elementData, paramArrayOfT, 0, j, i, 2, null);
    }
    else if ((((Collection)this).isEmpty() ^ true))
    {
      Object[] arrayOfObject = this.elementData;
      ArraysKt.copyInto(arrayOfObject, paramArrayOfT, 0, this.head, arrayOfObject.length);
      arrayOfObject = this.elementData;
      ArraysKt.copyInto(arrayOfObject, paramArrayOfT, arrayOfObject.length - this.head, 0, i);
    }
    if (paramArrayOfT.length > size()) {
      paramArrayOfT[size()] = null;
    }
    return paramArrayOfT;
  }
  
  @Metadata(d1={"\000\032\n\002\030\002\n\002\020\000\n\002\b\002\n\002\020\b\n\000\n\002\020\021\n\002\b\007\b\003\030\0002\0020\001B\007\b\002¢\006\002\020\002J\035\020\t\032\0020\0042\006\020\n\032\0020\0042\006\020\013\032\0020\004H\000¢\006\002\b\fR\016\020\003\032\0020\004XT¢\006\002\n\000R\030\020\005\032\n\022\006\022\004\030\0010\0010\006X\004¢\006\004\n\002\020\007R\016\020\b\032\0020\004XT¢\006\002\n\000¨\006\r"}, d2={"Lkotlin/collections/ArrayDeque$Companion;", "", "()V", "defaultMinCapacity", "", "emptyElementData", "", "[Ljava/lang/Object;", "maxArraySize", "newCapacity", "oldCapacity", "minCapacity", "newCapacity$kotlin_stdlib", "kotlin-stdlib"}, k=1, mv={1, 7, 1}, xi=48)
  public static final class Companion
  {
    public final int newCapacity$kotlin_stdlib(int paramInt1, int paramInt2)
    {
      int i = (paramInt1 >> 1) + paramInt1;
      paramInt1 = i;
      if (i - paramInt2 < 0) {
        paramInt1 = paramInt2;
      }
      int j = 2147483639;
      i = paramInt1;
      if (paramInt1 - 2147483639 > 0)
      {
        paramInt1 = j;
        if (paramInt2 > 2147483639) {
          paramInt1 = Integer.MAX_VALUE;
        }
        i = paramInt1;
      }
      return i;
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlin/collections/ArrayDeque.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */