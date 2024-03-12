package androidx.core.util;

import android.util.SparseArray;
import java.util.Iterator;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.IntIterator;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.markers.KMappedMarker;

@Metadata(d1={"\000@\n\000\n\002\020\b\n\000\n\002\030\002\n\002\b\003\n\002\020\013\n\002\b\006\n\002\020\002\n\000\n\002\030\002\n\002\030\002\n\002\b\005\n\002\030\002\n\002\b\004\n\002\030\002\n\002\b\b\n\002\020(\n\000\032!\020\006\032\0020\007\"\004\b\000\020\002*\b\022\004\022\002H\0020\0032\006\020\b\032\0020\001H\n\032!\020\t\032\0020\007\"\004\b\000\020\002*\b\022\004\022\002H\0020\0032\006\020\b\032\0020\001H\b\032&\020\n\032\0020\007\"\004\b\000\020\002*\b\022\004\022\002H\0020\0032\006\020\013\032\002H\002H\b¢\006\002\020\f\032T\020\r\032\0020\016\"\004\b\000\020\002*\b\022\004\022\002H\0020\00326\020\017\0322\022\023\022\0210\001¢\006\f\b\021\022\b\b\022\022\004\b\b(\b\022\023\022\021H\002¢\006\f\b\021\022\b\b\022\022\004\b\b(\013\022\004\022\0020\0160\020H\bø\001\000\032.\020\023\032\002H\002\"\004\b\000\020\002*\b\022\004\022\002H\0020\0032\006\020\b\032\0020\0012\006\020\024\032\002H\002H\b¢\006\002\020\025\0327\020\026\032\002H\002\"\004\b\000\020\002*\b\022\004\022\002H\0020\0032\006\020\b\032\0020\0012\f\020\024\032\b\022\004\022\002H\0020\027H\bø\001\000¢\006\002\020\030\032\031\020\031\032\0020\007\"\004\b\000\020\002*\b\022\004\022\002H\0020\003H\b\032\031\020\032\032\0020\007\"\004\b\000\020\002*\b\022\004\022\002H\0020\003H\b\032\026\020\033\032\0020\034\"\004\b\000\020\002*\b\022\004\022\002H\0020\003\032-\020\035\032\b\022\004\022\002H\0020\003\"\004\b\000\020\002*\b\022\004\022\002H\0020\0032\f\020\036\032\b\022\004\022\002H\0020\003H\002\032$\020\037\032\0020\016\"\004\b\000\020\002*\b\022\004\022\002H\0020\0032\f\020\036\032\b\022\004\022\002H\0020\003\032+\020 \032\0020\007\"\004\b\000\020\002*\b\022\004\022\002H\0020\0032\006\020\b\032\0020\0012\006\020\013\032\002H\002¢\006\002\020!\032.\020\"\032\0020\016\"\004\b\000\020\002*\b\022\004\022\002H\0020\0032\006\020\b\032\0020\0012\006\020\013\032\002H\002H\n¢\006\002\020#\032\034\020$\032\b\022\004\022\002H\0020%\"\004\b\000\020\002*\b\022\004\022\002H\0020\003\"\"\020\000\032\0020\001\"\004\b\000\020\002*\b\022\004\022\002H\0020\0038Æ\002¢\006\006\032\004\b\004\020\005\002\007\n\005\b20\001¨\006&"}, d2={"size", "", "T", "Landroid/util/SparseArray;", "getSize", "(Landroid/util/SparseArray;)I", "contains", "", "key", "containsKey", "containsValue", "value", "(Landroid/util/SparseArray;Ljava/lang/Object;)Z", "forEach", "", "action", "Lkotlin/Function2;", "Lkotlin/ParameterName;", "name", "getOrDefault", "defaultValue", "(Landroid/util/SparseArray;ILjava/lang/Object;)Ljava/lang/Object;", "getOrElse", "Lkotlin/Function0;", "(Landroid/util/SparseArray;ILkotlin/jvm/functions/Function0;)Ljava/lang/Object;", "isEmpty", "isNotEmpty", "keyIterator", "Lkotlin/collections/IntIterator;", "plus", "other", "putAll", "remove", "(Landroid/util/SparseArray;ILjava/lang/Object;)Z", "set", "(Landroid/util/SparseArray;ILjava/lang/Object;)V", "valueIterator", "", "core-ktx_release"}, k=2, mv={1, 6, 0}, xi=48)
public final class SparseArrayKt
{
  public static final <T> boolean contains(SparseArray<T> paramSparseArray, int paramInt)
  {
    Intrinsics.checkNotNullParameter(paramSparseArray, "<this>");
    boolean bool;
    if (paramSparseArray.indexOfKey(paramInt) >= 0) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public static final <T> boolean containsKey(SparseArray<T> paramSparseArray, int paramInt)
  {
    Intrinsics.checkNotNullParameter(paramSparseArray, "<this>");
    boolean bool;
    if (paramSparseArray.indexOfKey(paramInt) >= 0) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public static final <T> boolean containsValue(SparseArray<T> paramSparseArray, T paramT)
  {
    Intrinsics.checkNotNullParameter(paramSparseArray, "<this>");
    boolean bool;
    if (paramSparseArray.indexOfValue(paramT) >= 0) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public static final <T> void forEach(SparseArray<T> paramSparseArray, Function2<? super Integer, ? super T, Unit> paramFunction2)
  {
    Intrinsics.checkNotNullParameter(paramSparseArray, "<this>");
    Intrinsics.checkNotNullParameter(paramFunction2, "action");
    int i = 0;
    int j = paramSparseArray.size();
    while (i < j)
    {
      paramFunction2.invoke(Integer.valueOf(paramSparseArray.keyAt(i)), paramSparseArray.valueAt(i));
      i++;
    }
  }
  
  public static final <T> T getOrDefault(SparseArray<T> paramSparseArray, int paramInt, T paramT)
  {
    Intrinsics.checkNotNullParameter(paramSparseArray, "<this>");
    Object localObject = paramSparseArray.get(paramInt);
    paramSparseArray = (SparseArray<T>)localObject;
    if (localObject == null) {
      paramSparseArray = paramT;
    }
    return paramSparseArray;
  }
  
  public static final <T> T getOrElse(SparseArray<T> paramSparseArray, int paramInt, Function0<? extends T> paramFunction0)
  {
    Intrinsics.checkNotNullParameter(paramSparseArray, "<this>");
    Intrinsics.checkNotNullParameter(paramFunction0, "defaultValue");
    Object localObject = paramSparseArray.get(paramInt);
    paramSparseArray = (SparseArray<T>)localObject;
    if (localObject == null) {
      paramSparseArray = paramFunction0.invoke();
    }
    return paramSparseArray;
  }
  
  public static final <T> int getSize(SparseArray<T> paramSparseArray)
  {
    Intrinsics.checkNotNullParameter(paramSparseArray, "<this>");
    return paramSparseArray.size();
  }
  
  public static final <T> boolean isEmpty(SparseArray<T> paramSparseArray)
  {
    Intrinsics.checkNotNullParameter(paramSparseArray, "<this>");
    boolean bool;
    if (paramSparseArray.size() == 0) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public static final <T> boolean isNotEmpty(SparseArray<T> paramSparseArray)
  {
    Intrinsics.checkNotNullParameter(paramSparseArray, "<this>");
    boolean bool;
    if (paramSparseArray.size() != 0) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public static final <T> IntIterator keyIterator(SparseArray<T> paramSparseArray)
  {
    Intrinsics.checkNotNullParameter(paramSparseArray, "<this>");
    (IntIterator)new IntIterator()
    {
      final SparseArray<T> $this_keyIterator;
      private int index;
      
      public final int getIndex()
      {
        return this.index;
      }
      
      public boolean hasNext()
      {
        boolean bool;
        if (this.index < this.$this_keyIterator.size()) {
          bool = true;
        } else {
          bool = false;
        }
        return bool;
      }
      
      public int nextInt()
      {
        SparseArray localSparseArray = this.$this_keyIterator;
        int i = this.index;
        this.index = (i + 1);
        return localSparseArray.keyAt(i);
      }
      
      public final void setIndex(int paramAnonymousInt)
      {
        this.index = paramAnonymousInt;
      }
    };
  }
  
  public static final <T> SparseArray<T> plus(SparseArray<T> paramSparseArray1, SparseArray<T> paramSparseArray2)
  {
    Intrinsics.checkNotNullParameter(paramSparseArray1, "<this>");
    Intrinsics.checkNotNullParameter(paramSparseArray2, "other");
    SparseArray localSparseArray = new SparseArray(paramSparseArray1.size() + paramSparseArray2.size());
    putAll(localSparseArray, paramSparseArray1);
    putAll(localSparseArray, paramSparseArray2);
    return localSparseArray;
  }
  
  public static final <T> void putAll(SparseArray<T> paramSparseArray1, SparseArray<T> paramSparseArray2)
  {
    Intrinsics.checkNotNullParameter(paramSparseArray1, "<this>");
    Intrinsics.checkNotNullParameter(paramSparseArray2, "other");
    int i = 0;
    int j = paramSparseArray2.size();
    while (i < j)
    {
      paramSparseArray1.put(paramSparseArray2.keyAt(i), paramSparseArray2.valueAt(i));
      i++;
    }
  }
  
  public static final <T> boolean remove(SparseArray<T> paramSparseArray, int paramInt, T paramT)
  {
    Intrinsics.checkNotNullParameter(paramSparseArray, "<this>");
    paramInt = paramSparseArray.indexOfKey(paramInt);
    if ((paramInt >= 0) && (Intrinsics.areEqual(paramT, paramSparseArray.valueAt(paramInt))))
    {
      paramSparseArray.removeAt(paramInt);
      return true;
    }
    return false;
  }
  
  public static final <T> void set(SparseArray<T> paramSparseArray, int paramInt, T paramT)
  {
    Intrinsics.checkNotNullParameter(paramSparseArray, "<this>");
    paramSparseArray.put(paramInt, paramT);
  }
  
  public static final <T> Iterator<T> valueIterator(SparseArray<T> paramSparseArray)
  {
    Intrinsics.checkNotNullParameter(paramSparseArray, "<this>");
    (Iterator)new Iterator()
    {
      final SparseArray<T> $this_valueIterator;
      private int index;
      
      public final int getIndex()
      {
        return this.index;
      }
      
      public boolean hasNext()
      {
        boolean bool;
        if (this.index < this.$this_valueIterator.size()) {
          bool = true;
        } else {
          bool = false;
        }
        return bool;
      }
      
      public T next()
      {
        SparseArray localSparseArray = this.$this_valueIterator;
        int i = this.index;
        this.index = (i + 1);
        return (T)localSparseArray.valueAt(i);
      }
      
      public void remove()
      {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
      }
      
      public final void setIndex(int paramAnonymousInt)
      {
        this.index = paramAnonymousInt;
      }
    };
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/core/util/SparseArrayKt.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */