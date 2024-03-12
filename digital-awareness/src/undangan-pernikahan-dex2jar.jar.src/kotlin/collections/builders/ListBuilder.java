package kotlin.collections.builders;

import java.io.NotSerializableException;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.util.RandomAccess;
import kotlin.Metadata;
import kotlin.collections.AbstractList;
import kotlin.collections.AbstractList.Companion;
import kotlin.collections.AbstractMutableList;
import kotlin.collections.ArrayDeque;
import kotlin.collections.ArrayDeque.Companion;
import kotlin.collections.ArraysKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.markers.KMutableList;
import kotlin.jvm.internal.markers.KMutableListIterator;
import mt.Log229316;
import mt.Log5ECF72;
import mt.LogE84000;

@Metadata(d1={"\000r\n\002\030\002\n\000\n\002\020!\n\002\030\002\n\002\030\002\n\002\030\002\n\002\030\002\n\002\030\002\n\002\b\002\n\002\020\b\n\002\b\002\n\002\020\021\n\002\b\003\n\002\020\013\n\002\b\f\n\002\020\002\n\002\b\004\n\002\020\036\n\002\b\005\n\002\020 \n\002\b\b\n\002\020\000\n\002\b\b\n\002\020)\n\002\b\002\n\002\020+\n\002\b\025\n\002\020\016\n\002\b\003\b\000\030\000*\004\b\000\020\0012\b\022\004\022\002H\0010\0022\0060\003j\002`\0042\b\022\004\022\002H\0010\0052\0060\006j\002`\007:\001VB\007\b\026¢\006\002\020\bB\017\b\026\022\006\020\t\032\0020\n¢\006\002\020\013BM\b\002\022\f\020\f\032\b\022\004\022\0028\0000\r\022\006\020\016\032\0020\n\022\006\020\017\032\0020\n\022\006\020\020\032\0020\021\022\016\020\022\032\n\022\004\022\0028\000\030\0010\000\022\016\020\023\032\n\022\004\022\0028\000\030\0010\000¢\006\002\020\024J\025\020\033\032\0020\0212\006\020\034\032\0028\000H\026¢\006\002\020\035J\035\020\033\032\0020\0362\006\020\037\032\0020\n2\006\020\034\032\0028\000H\026¢\006\002\020 J\036\020!\032\0020\0212\006\020\037\032\0020\n2\f\020\"\032\b\022\004\022\0028\0000#H\026J\026\020!\032\0020\0212\f\020\"\032\b\022\004\022\0028\0000#H\026J&\020$\032\0020\0362\006\020%\032\0020\n2\f\020\"\032\b\022\004\022\0028\0000#2\006\020&\032\0020\nH\002J\035\020'\032\0020\0362\006\020%\032\0020\n2\006\020\034\032\0028\000H\002¢\006\002\020 J\f\020(\032\b\022\004\022\0028\0000)J\b\020*\032\0020\036H\002J\b\020+\032\0020\036H\026J\024\020,\032\0020\0212\n\020-\032\006\022\002\b\0030)H\002J\020\020.\032\0020\0362\006\020/\032\0020\nH\002J\020\0200\032\0020\0362\006\020&\032\0020\nH\002J\023\0201\032\0020\0212\b\020-\032\004\030\00102H\002J\026\0203\032\0028\0002\006\020\037\032\0020\nH\002¢\006\002\0204J\b\0205\032\0020\nH\026J\025\0206\032\0020\n2\006\020\034\032\0028\000H\026¢\006\002\0207J\030\0208\032\0020\0362\006\020%\032\0020\n2\006\020&\032\0020\nH\002J\b\0209\032\0020\021H\026J\017\020:\032\b\022\004\022\0028\0000;H\002J\025\020<\032\0020\n2\006\020\034\032\0028\000H\026¢\006\002\0207J\016\020=\032\b\022\004\022\0028\0000>H\026J\026\020=\032\b\022\004\022\0028\0000>2\006\020\037\032\0020\nH\026J\025\020?\032\0020\0212\006\020\034\032\0028\000H\026¢\006\002\020\035J\026\020@\032\0020\0212\f\020\"\032\b\022\004\022\0028\0000#H\026J\025\020A\032\0028\0002\006\020\037\032\0020\nH\026¢\006\002\0204J\025\020B\032\0028\0002\006\020%\032\0020\nH\002¢\006\002\0204J\030\020C\032\0020\0362\006\020D\032\0020\n2\006\020E\032\0020\nH\002J\026\020F\032\0020\0212\f\020\"\032\b\022\004\022\0028\0000#H\026J.\020G\032\0020\n2\006\020D\032\0020\n2\006\020E\032\0020\n2\f\020\"\032\b\022\004\022\0028\0000#2\006\020H\032\0020\021H\002J\036\020I\032\0028\0002\006\020\037\032\0020\n2\006\020\034\032\0028\000H\002¢\006\002\020JJ\036\020K\032\b\022\004\022\0028\0000\0022\006\020L\032\0020\n2\006\020M\032\0020\nH\026J\025\020N\032\n\022\006\022\004\030\001020\rH\026¢\006\002\020OJ'\020N\032\b\022\004\022\002HP0\r\"\004\b\001\020P2\f\020Q\032\b\022\004\022\002HP0\rH\026¢\006\002\020RJ\b\020S\032\0020TH\026J\b\020U\032\00202H\002R\026\020\f\032\b\022\004\022\0028\0000\rX\016¢\006\004\n\002\020\025R\026\020\022\032\n\022\004\022\0028\000\030\0010\000X\004¢\006\002\n\000R\024\020\026\032\0020\0218BX\004¢\006\006\032\004\b\026\020\027R\016\020\020\032\0020\021X\016¢\006\002\n\000R\016\020\017\032\0020\nX\016¢\006\002\n\000R\016\020\016\032\0020\nX\016¢\006\002\n\000R\026\020\023\032\n\022\004\022\0028\000\030\0010\000X\004¢\006\002\n\000R\024\020\030\032\0020\n8VX\004¢\006\006\032\004\b\031\020\032¨\006W"}, d2={"Lkotlin/collections/builders/ListBuilder;", "E", "", "Ljava/util/RandomAccess;", "Lkotlin/collections/RandomAccess;", "Lkotlin/collections/AbstractMutableList;", "Ljava/io/Serializable;", "Lkotlin/io/Serializable;", "()V", "initialCapacity", "", "(I)V", "array", "", "offset", "length", "isReadOnly", "", "backing", "root", "([Ljava/lang/Object;IIZLkotlin/collections/builders/ListBuilder;Lkotlin/collections/builders/ListBuilder;)V", "[Ljava/lang/Object;", "isEffectivelyReadOnly", "()Z", "size", "getSize", "()I", "add", "element", "(Ljava/lang/Object;)Z", "", "index", "(ILjava/lang/Object;)V", "addAll", "elements", "", "addAllInternal", "i", "n", "addAtInternal", "build", "", "checkIsMutable", "clear", "contentEquals", "other", "ensureCapacity", "minCapacity", "ensureExtraCapacity", "equals", "", "get", "(I)Ljava/lang/Object;", "hashCode", "indexOf", "(Ljava/lang/Object;)I", "insertAtInternal", "isEmpty", "iterator", "", "lastIndexOf", "listIterator", "", "remove", "removeAll", "removeAt", "removeAtInternal", "removeRangeInternal", "rangeOffset", "rangeLength", "retainAll", "retainOrRemoveAllInternal", "retain", "set", "(ILjava/lang/Object;)Ljava/lang/Object;", "subList", "fromIndex", "toIndex", "toArray", "()[Ljava/lang/Object;", "T", "destination", "([Ljava/lang/Object;)[Ljava/lang/Object;", "toString", "", "writeReplace", "Itr", "kotlin-stdlib"}, k=1, mv={1, 7, 1}, xi=48)
public final class ListBuilder<E>
  extends AbstractMutableList<E>
  implements List<E>, RandomAccess, Serializable, KMutableList
{
  private E[] array;
  private final ListBuilder<E> backing;
  private boolean isReadOnly;
  private int length;
  private int offset;
  private final ListBuilder<E> root;
  
  public ListBuilder()
  {
    this(10);
  }
  
  public ListBuilder(int paramInt)
  {
    this(ListBuilderKt.arrayOfUninitializedElements(paramInt), 0, 0, false, null, null);
  }
  
  private ListBuilder(E[] paramArrayOfE, int paramInt1, int paramInt2, boolean paramBoolean, ListBuilder<E> paramListBuilder1, ListBuilder<E> paramListBuilder2)
  {
    this.array = paramArrayOfE;
    this.offset = paramInt1;
    this.length = paramInt2;
    this.isReadOnly = paramBoolean;
    this.backing = paramListBuilder1;
    this.root = paramListBuilder2;
  }
  
  private final void addAllInternal(int paramInt1, Collection<? extends E> paramCollection, int paramInt2)
  {
    ListBuilder localListBuilder = this.backing;
    if (localListBuilder != null)
    {
      localListBuilder.addAllInternal(paramInt1, paramCollection, paramInt2);
      this.array = this.backing.array;
      this.length += paramInt2;
    }
    else
    {
      insertAtInternal(paramInt1, paramInt2);
      int i = 0;
      paramCollection = paramCollection.iterator();
      while (i < paramInt2)
      {
        this.array[(paramInt1 + i)] = paramCollection.next();
        i++;
      }
    }
  }
  
  private final void addAtInternal(int paramInt, E paramE)
  {
    ListBuilder localListBuilder = this.backing;
    if (localListBuilder != null)
    {
      localListBuilder.addAtInternal(paramInt, paramE);
      this.array = this.backing.array;
      this.length += 1;
    }
    else
    {
      insertAtInternal(paramInt, 1);
      this.array[paramInt] = paramE;
    }
  }
  
  private final void checkIsMutable()
  {
    if (!isEffectivelyReadOnly()) {
      return;
    }
    throw new UnsupportedOperationException();
  }
  
  private final boolean contentEquals(List<?> paramList)
  {
    return ListBuilderKt.access$subarrayContentEquals(this.array, this.offset, this.length, paramList);
  }
  
  private final void ensureCapacity(int paramInt)
  {
    if (this.backing == null)
    {
      if (paramInt >= 0)
      {
        if (paramInt > this.array.length)
        {
          paramInt = ArrayDeque.Companion.newCapacity$kotlin_stdlib(this.array.length, paramInt);
          this.array = ListBuilderKt.copyOfUninitializedElements(this.array, paramInt);
        }
        return;
      }
      throw new OutOfMemoryError();
    }
    throw new IllegalStateException();
  }
  
  private final void ensureExtraCapacity(int paramInt)
  {
    ensureCapacity(this.length + paramInt);
  }
  
  private final void insertAtInternal(int paramInt1, int paramInt2)
  {
    ensureExtraCapacity(paramInt2);
    Object[] arrayOfObject = this.array;
    ArraysKt.copyInto(arrayOfObject, arrayOfObject, paramInt1 + paramInt2, paramInt1, this.offset + this.length);
    this.length += paramInt2;
  }
  
  private final boolean isEffectivelyReadOnly()
  {
    if (!this.isReadOnly)
    {
      ListBuilder localListBuilder = this.root;
      if ((localListBuilder == null) || (!localListBuilder.isReadOnly)) {
        return false;
      }
    }
    boolean bool = true;
    return bool;
  }
  
  private final E removeAtInternal(int paramInt)
  {
    Object localObject = this.backing;
    if (localObject != null)
    {
      localObject = ((ListBuilder)localObject).removeAtInternal(paramInt);
      this.length -= 1;
      return (E)localObject;
    }
    localObject = this.array;
    E ? = localObject[paramInt];
    ArraysKt.copyInto((Object[])localObject, (Object[])localObject, paramInt, paramInt + 1, this.offset + this.length);
    ListBuilderKt.resetAt(this.array, this.offset + this.length - 1);
    this.length -= 1;
    return ?;
  }
  
  private final void removeRangeInternal(int paramInt1, int paramInt2)
  {
    Object localObject = this.backing;
    if (localObject != null)
    {
      ((ListBuilder)localObject).removeRangeInternal(paramInt1, paramInt2);
    }
    else
    {
      localObject = this.array;
      ArraysKt.copyInto((Object[])localObject, (Object[])localObject, paramInt1, paramInt1 + paramInt2, this.length);
      localObject = this.array;
      paramInt1 = this.length;
      ListBuilderKt.resetRange((Object[])localObject, paramInt1 - paramInt2, paramInt1);
    }
    this.length -= paramInt2;
  }
  
  private final int retainOrRemoveAllInternal(int paramInt1, int paramInt2, Collection<? extends E> paramCollection, boolean paramBoolean)
  {
    Object localObject = this.backing;
    if (localObject != null)
    {
      paramInt1 = ((ListBuilder)localObject).retainOrRemoveAllInternal(paramInt1, paramInt2, paramCollection, paramBoolean);
      this.length -= paramInt1;
      return paramInt1;
    }
    int i = 0;
    int j = 0;
    while (i < paramInt2) {
      if (paramCollection.contains(this.array[(paramInt1 + i)]) == paramBoolean)
      {
        localObject = this.array;
        localObject[(j + paramInt1)] = localObject[(i + paramInt1)];
        j++;
        i++;
      }
      else
      {
        i++;
      }
    }
    i = paramInt2 - j;
    paramCollection = this.array;
    ArraysKt.copyInto(paramCollection, paramCollection, paramInt1 + j, paramInt1 + paramInt2, this.length);
    paramCollection = this.array;
    paramInt1 = this.length;
    ListBuilderKt.resetRange(paramCollection, paramInt1 - i, paramInt1);
    this.length -= i;
    return i;
  }
  
  private final Object writeReplace()
  {
    if (isEffectivelyReadOnly()) {
      return new SerializedCollection((Collection)this, 0);
    }
    throw new NotSerializableException("The list cannot be serialized while it is being built.");
  }
  
  public void add(int paramInt, E paramE)
  {
    checkIsMutable();
    AbstractList.Companion.checkPositionIndex$kotlin_stdlib(paramInt, this.length);
    addAtInternal(this.offset + paramInt, paramE);
  }
  
  public boolean add(E paramE)
  {
    checkIsMutable();
    addAtInternal(this.offset + this.length, paramE);
    return true;
  }
  
  public boolean addAll(int paramInt, Collection<? extends E> paramCollection)
  {
    Intrinsics.checkNotNullParameter(paramCollection, "elements");
    checkIsMutable();
    AbstractList.Companion.checkPositionIndex$kotlin_stdlib(paramInt, this.length);
    int i = paramCollection.size();
    addAllInternal(this.offset + paramInt, paramCollection, i);
    boolean bool;
    if (i > 0) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public boolean addAll(Collection<? extends E> paramCollection)
  {
    Intrinsics.checkNotNullParameter(paramCollection, "elements");
    checkIsMutable();
    int i = paramCollection.size();
    addAllInternal(this.offset + this.length, paramCollection, i);
    boolean bool;
    if (i > 0) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public final List<E> build()
  {
    if (this.backing == null)
    {
      checkIsMutable();
      this.isReadOnly = true;
      return (List)this;
    }
    throw new IllegalStateException();
  }
  
  public void clear()
  {
    checkIsMutable();
    removeRangeInternal(this.offset, this.length);
  }
  
  public boolean equals(Object paramObject)
  {
    boolean bool;
    if ((paramObject != this) && ((!(paramObject instanceof List)) || (!contentEquals((List)paramObject)))) {
      bool = false;
    } else {
      bool = true;
    }
    return bool;
  }
  
  public E get(int paramInt)
  {
    AbstractList.Companion.checkElementIndex$kotlin_stdlib(paramInt, this.length);
    return (E)this.array[(this.offset + paramInt)];
  }
  
  public int getSize()
  {
    return this.length;
  }
  
  public int hashCode()
  {
    return ListBuilderKt.access$subarrayContentHashCode(this.array, this.offset, this.length);
  }
  
  public int indexOf(Object paramObject)
  {
    for (int i = 0; i < this.length; i++) {
      if (Intrinsics.areEqual(this.array[(this.offset + i)], paramObject)) {
        return i;
      }
    }
    return -1;
  }
  
  public boolean isEmpty()
  {
    boolean bool;
    if (this.length == 0) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public Iterator<E> iterator()
  {
    return (Iterator)new Itr(this, 0);
  }
  
  public int lastIndexOf(Object paramObject)
  {
    for (int i = this.length - 1; i >= 0; i--) {
      if (Intrinsics.areEqual(this.array[(this.offset + i)], paramObject)) {
        return i;
      }
    }
    return -1;
  }
  
  public ListIterator<E> listIterator()
  {
    return (ListIterator)new Itr(this, 0);
  }
  
  public ListIterator<E> listIterator(int paramInt)
  {
    AbstractList.Companion.checkPositionIndex$kotlin_stdlib(paramInt, this.length);
    return (ListIterator)new Itr(this, paramInt);
  }
  
  public boolean remove(Object paramObject)
  {
    checkIsMutable();
    int i = indexOf(paramObject);
    if (i >= 0) {
      remove(i);
    }
    boolean bool;
    if (i >= 0) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public boolean removeAll(Collection<? extends Object> paramCollection)
  {
    Intrinsics.checkNotNullParameter(paramCollection, "elements");
    checkIsMutable();
    int j = this.offset;
    int i = this.length;
    boolean bool = false;
    if (retainOrRemoveAllInternal(j, i, paramCollection, false) > 0) {
      bool = true;
    }
    return bool;
  }
  
  public E removeAt(int paramInt)
  {
    checkIsMutable();
    AbstractList.Companion.checkElementIndex$kotlin_stdlib(paramInt, this.length);
    return (E)removeAtInternal(this.offset + paramInt);
  }
  
  public boolean retainAll(Collection<? extends Object> paramCollection)
  {
    Intrinsics.checkNotNullParameter(paramCollection, "elements");
    checkIsMutable();
    int j = this.offset;
    int i = this.length;
    boolean bool = true;
    if (retainOrRemoveAllInternal(j, i, paramCollection, true) <= 0) {
      bool = false;
    }
    return bool;
  }
  
  public E set(int paramInt, E paramE)
  {
    checkIsMutable();
    AbstractList.Companion.checkElementIndex$kotlin_stdlib(paramInt, this.length);
    Object[] arrayOfObject = this.array;
    int i = this.offset;
    Object localObject = arrayOfObject[(i + paramInt)];
    arrayOfObject[(i + paramInt)] = paramE;
    return (E)localObject;
  }
  
  public List<E> subList(int paramInt1, int paramInt2)
  {
    AbstractList.Companion.checkRangeIndexes$kotlin_stdlib(paramInt1, paramInt2, this.length);
    Object[] arrayOfObject = this.array;
    int i = this.offset;
    boolean bool = this.isReadOnly;
    ListBuilder localListBuilder = this.root;
    if (localListBuilder == null) {
      localListBuilder = this;
    }
    return (List)new ListBuilder(arrayOfObject, i + paramInt1, paramInt2 - paramInt1, bool, this, localListBuilder);
  }
  
  public Object[] toArray()
  {
    Object[] arrayOfObject = this.array;
    int i = this.offset;
    arrayOfObject = ArraysKt.copyOfRange(arrayOfObject, i, this.length + i);
    Intrinsics.checkNotNull(arrayOfObject, "null cannot be cast to non-null type kotlin.Array<kotlin.Any?>");
    return arrayOfObject;
  }
  
  public <T> T[] toArray(T[] paramArrayOfT)
  {
    Intrinsics.checkNotNullParameter(paramArrayOfT, "destination");
    int j = paramArrayOfT.length;
    int i = this.length;
    if (j < i)
    {
      arrayOfObject = this.array;
      j = this.offset;
      paramArrayOfT = Arrays.copyOfRange(arrayOfObject, j, i + j, paramArrayOfT.getClass());
      Intrinsics.checkNotNullExpressionValue(paramArrayOfT, "copyOfRange(array, offse…h, destination.javaClass)");
      return paramArrayOfT;
    }
    Object[] arrayOfObject = this.array;
    Intrinsics.checkNotNull(arrayOfObject, "null cannot be cast to non-null type kotlin.Array<T of kotlin.collections.builders.ListBuilder.toArray>");
    i = this.offset;
    ArraysKt.copyInto(arrayOfObject, paramArrayOfT, 0, i, this.length + i);
    i = paramArrayOfT.length;
    j = this.length;
    if (i > j) {
      paramArrayOfT[j] = null;
    }
    return paramArrayOfT;
  }
  
  public String toString()
  {
    String str = ListBuilderKt.access$subarrayContentToString(this.array, this.offset, this.length);
    Log5ECF72.a(str);
    LogE84000.a(str);
    Log229316.a(str);
    return str;
  }
  
  @Metadata(d1={"\000*\n\002\030\002\n\000\n\002\020+\n\000\n\002\030\002\n\000\n\002\020\b\n\002\b\003\n\002\020\002\n\002\b\003\n\002\020\013\n\002\b\t\b\002\030\000*\004\b\001\020\0012\b\022\004\022\002H\0010\002B\035\b\026\022\f\020\003\032\b\022\004\022\0028\0010\004\022\006\020\005\032\0020\006¢\006\002\020\007J\025\020\t\032\0020\n2\006\020\013\032\0028\001H\026¢\006\002\020\fJ\t\020\r\032\0020\016H\002J\b\020\017\032\0020\016H\026J\016\020\020\032\0028\001H\002¢\006\002\020\021J\b\020\022\032\0020\006H\026J\r\020\023\032\0028\001H\026¢\006\002\020\021J\b\020\024\032\0020\006H\026J\b\020\025\032\0020\nH\026J\025\020\026\032\0020\n2\006\020\013\032\0028\001H\026¢\006\002\020\fR\016\020\005\032\0020\006X\016¢\006\002\n\000R\016\020\b\032\0020\006X\016¢\006\002\n\000R\024\020\003\032\b\022\004\022\0028\0010\004X\004¢\006\002\n\000¨\006\027"}, d2={"Lkotlin/collections/builders/ListBuilder$Itr;", "E", "", "list", "Lkotlin/collections/builders/ListBuilder;", "index", "", "(Lkotlin/collections/builders/ListBuilder;I)V", "lastIndex", "add", "", "element", "(Ljava/lang/Object;)V", "hasNext", "", "hasPrevious", "next", "()Ljava/lang/Object;", "nextIndex", "previous", "previousIndex", "remove", "set", "kotlin-stdlib"}, k=1, mv={1, 7, 1}, xi=48)
  private static final class Itr<E>
    implements ListIterator<E>, KMutableListIterator
  {
    private int index;
    private int lastIndex;
    private final ListBuilder<E> list;
    
    public Itr(ListBuilder<E> paramListBuilder, int paramInt)
    {
      this.list = paramListBuilder;
      this.index = paramInt;
      this.lastIndex = -1;
    }
    
    public void add(E paramE)
    {
      ListBuilder localListBuilder = this.list;
      int i = this.index;
      this.index = (i + 1);
      localListBuilder.add(i, paramE);
      this.lastIndex = -1;
    }
    
    public boolean hasNext()
    {
      boolean bool;
      if (this.index < ListBuilder.access$getLength$p(this.list)) {
        bool = true;
      } else {
        bool = false;
      }
      return bool;
    }
    
    public boolean hasPrevious()
    {
      boolean bool;
      if (this.index > 0) {
        bool = true;
      } else {
        bool = false;
      }
      return bool;
    }
    
    public E next()
    {
      if (this.index < ListBuilder.access$getLength$p(this.list))
      {
        int i = this.index;
        this.index = (i + 1);
        this.lastIndex = i;
        return (E)ListBuilder.access$getArray$p(this.list)[(ListBuilder.access$getOffset$p(this.list) + this.lastIndex)];
      }
      throw new NoSuchElementException();
    }
    
    public int nextIndex()
    {
      return this.index;
    }
    
    public E previous()
    {
      int i = this.index;
      if (i > 0)
      {
        i--;
        this.index = i;
        this.lastIndex = i;
        return (E)ListBuilder.access$getArray$p(this.list)[(ListBuilder.access$getOffset$p(this.list) + this.lastIndex)];
      }
      throw new NoSuchElementException();
    }
    
    public int previousIndex()
    {
      return this.index - 1;
    }
    
    public void remove()
    {
      int j = this.lastIndex;
      int i;
      if (j != -1) {
        i = 1;
      } else {
        i = 0;
      }
      if (i != 0)
      {
        this.list.remove(j);
        this.index = this.lastIndex;
        this.lastIndex = -1;
        return;
      }
      throw new IllegalStateException("Call next() or previous() before removing element from the iterator.".toString());
    }
    
    public void set(E paramE)
    {
      int j = this.lastIndex;
      int i;
      if (j != -1) {
        i = 1;
      } else {
        i = 0;
      }
      if (i != 0)
      {
        this.list.set(j, paramE);
        return;
      }
      throw new IllegalStateException("Call next() or previous() before replacing element from the iterator.".toString());
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlin/collections/builders/ListBuilder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */