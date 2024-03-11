package androidx.core.util;

import android.util.LongSparseArray;
import java.util.Iterator;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.LongIterator;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.markers.KMappedMarker;

@Metadata(d1={"\000F\n\000\n\002\020\b\n\000\n\002\030\002\n\002\b\003\n\002\020\013\n\000\n\002\020\t\n\002\b\005\n\002\020\002\n\000\n\002\030\002\n\002\030\002\n\002\b\005\n\002\030\002\n\002\b\004\n\002\030\002\n\002\b\b\n\002\020(\n\000\032!\020\006\032\0020\007\"\004\b\000\020\002*\b\022\004\022\002H\0020\0032\006\020\b\032\0020\tH\n\032!\020\n\032\0020\007\"\004\b\000\020\002*\b\022\004\022\002H\0020\0032\006\020\b\032\0020\tH\b\032&\020\013\032\0020\007\"\004\b\000\020\002*\b\022\004\022\002H\0020\0032\006\020\f\032\002H\002H\b¢\006\002\020\r\032T\020\016\032\0020\017\"\004\b\000\020\002*\b\022\004\022\002H\0020\00326\020\020\0322\022\023\022\0210\t¢\006\f\b\022\022\b\b\023\022\004\b\b(\b\022\023\022\021H\002¢\006\f\b\022\022\b\b\023\022\004\b\b(\f\022\004\022\0020\0170\021H\bø\001\000\032.\020\024\032\002H\002\"\004\b\000\020\002*\b\022\004\022\002H\0020\0032\006\020\b\032\0020\t2\006\020\025\032\002H\002H\b¢\006\002\020\026\0327\020\027\032\002H\002\"\004\b\000\020\002*\b\022\004\022\002H\0020\0032\006\020\b\032\0020\t2\f\020\025\032\b\022\004\022\002H\0020\030H\bø\001\000¢\006\002\020\031\032\031\020\032\032\0020\007\"\004\b\000\020\002*\b\022\004\022\002H\0020\003H\b\032\031\020\033\032\0020\007\"\004\b\000\020\002*\b\022\004\022\002H\0020\003H\b\032\030\020\034\032\0020\035\"\004\b\000\020\002*\b\022\004\022\002H\0020\003H\007\032-\020\036\032\b\022\004\022\002H\0020\003\"\004\b\000\020\002*\b\022\004\022\002H\0020\0032\f\020\037\032\b\022\004\022\002H\0020\003H\002\032&\020 \032\0020\017\"\004\b\000\020\002*\b\022\004\022\002H\0020\0032\f\020\037\032\b\022\004\022\002H\0020\003H\007\032-\020!\032\0020\007\"\004\b\000\020\002*\b\022\004\022\002H\0020\0032\006\020\b\032\0020\t2\006\020\f\032\002H\002H\007¢\006\002\020\"\032.\020#\032\0020\017\"\004\b\000\020\002*\b\022\004\022\002H\0020\0032\006\020\b\032\0020\t2\006\020\f\032\002H\002H\n¢\006\002\020$\032\036\020%\032\b\022\004\022\002H\0020&\"\004\b\000\020\002*\b\022\004\022\002H\0020\003H\007\"\"\020\000\032\0020\001\"\004\b\000\020\002*\b\022\004\022\002H\0020\0038Ç\002¢\006\006\032\004\b\004\020\005\002\007\n\005\b20\001¨\006'"}, d2={"size", "", "T", "Landroid/util/LongSparseArray;", "getSize", "(Landroid/util/LongSparseArray;)I", "contains", "", "key", "", "containsKey", "containsValue", "value", "(Landroid/util/LongSparseArray;Ljava/lang/Object;)Z", "forEach", "", "action", "Lkotlin/Function2;", "Lkotlin/ParameterName;", "name", "getOrDefault", "defaultValue", "(Landroid/util/LongSparseArray;JLjava/lang/Object;)Ljava/lang/Object;", "getOrElse", "Lkotlin/Function0;", "(Landroid/util/LongSparseArray;JLkotlin/jvm/functions/Function0;)Ljava/lang/Object;", "isEmpty", "isNotEmpty", "keyIterator", "Lkotlin/collections/LongIterator;", "plus", "other", "putAll", "remove", "(Landroid/util/LongSparseArray;JLjava/lang/Object;)Z", "set", "(Landroid/util/LongSparseArray;JLjava/lang/Object;)V", "valueIterator", "", "core-ktx_release"}, k=2, mv={1, 6, 0}, xi=48)
public final class LongSparseArrayKt
{
  public static final <T> boolean contains(LongSparseArray<T> paramLongSparseArray, long paramLong)
  {
    Intrinsics.checkNotNullParameter(paramLongSparseArray, "<this>");
    boolean bool;
    if (paramLongSparseArray.indexOfKey(paramLong) >= 0) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public static final <T> boolean containsKey(LongSparseArray<T> paramLongSparseArray, long paramLong)
  {
    Intrinsics.checkNotNullParameter(paramLongSparseArray, "<this>");
    boolean bool;
    if (paramLongSparseArray.indexOfKey(paramLong) >= 0) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public static final <T> boolean containsValue(LongSparseArray<T> paramLongSparseArray, T paramT)
  {
    Intrinsics.checkNotNullParameter(paramLongSparseArray, "<this>");
    boolean bool;
    if (paramLongSparseArray.indexOfValue(paramT) >= 0) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public static final <T> void forEach(LongSparseArray<T> paramLongSparseArray, Function2<? super Long, ? super T, Unit> paramFunction2)
  {
    Intrinsics.checkNotNullParameter(paramLongSparseArray, "<this>");
    Intrinsics.checkNotNullParameter(paramFunction2, "action");
    int i = 0;
    int j = paramLongSparseArray.size();
    while (i < j)
    {
      paramFunction2.invoke(Long.valueOf(paramLongSparseArray.keyAt(i)), paramLongSparseArray.valueAt(i));
      i++;
    }
  }
  
  public static final <T> T getOrDefault(LongSparseArray<T> paramLongSparseArray, long paramLong, T paramT)
  {
    Intrinsics.checkNotNullParameter(paramLongSparseArray, "<this>");
    Object localObject = paramLongSparseArray.get(paramLong);
    paramLongSparseArray = (LongSparseArray<T>)localObject;
    if (localObject == null) {
      paramLongSparseArray = paramT;
    }
    return paramLongSparseArray;
  }
  
  public static final <T> T getOrElse(LongSparseArray<T> paramLongSparseArray, long paramLong, Function0<? extends T> paramFunction0)
  {
    Intrinsics.checkNotNullParameter(paramLongSparseArray, "<this>");
    Intrinsics.checkNotNullParameter(paramFunction0, "defaultValue");
    Object localObject = paramLongSparseArray.get(paramLong);
    paramLongSparseArray = (LongSparseArray<T>)localObject;
    if (localObject == null) {
      paramLongSparseArray = paramFunction0.invoke();
    }
    return paramLongSparseArray;
  }
  
  public static final <T> int getSize(LongSparseArray<T> paramLongSparseArray)
  {
    Intrinsics.checkNotNullParameter(paramLongSparseArray, "<this>");
    return paramLongSparseArray.size();
  }
  
  public static final <T> boolean isEmpty(LongSparseArray<T> paramLongSparseArray)
  {
    Intrinsics.checkNotNullParameter(paramLongSparseArray, "<this>");
    boolean bool;
    if (paramLongSparseArray.size() == 0) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public static final <T> boolean isNotEmpty(LongSparseArray<T> paramLongSparseArray)
  {
    Intrinsics.checkNotNullParameter(paramLongSparseArray, "<this>");
    boolean bool;
    if (paramLongSparseArray.size() != 0) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public static final <T> LongIterator keyIterator(LongSparseArray<T> paramLongSparseArray)
  {
    Intrinsics.checkNotNullParameter(paramLongSparseArray, "<this>");
    (LongIterator)new LongIterator()
    {
      final LongSparseArray<T> $this_keyIterator;
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
      
      public long nextLong()
      {
        LongSparseArray localLongSparseArray = this.$this_keyIterator;
        int i = this.index;
        this.index = (i + 1);
        return localLongSparseArray.keyAt(i);
      }
      
      public final void setIndex(int paramAnonymousInt)
      {
        this.index = paramAnonymousInt;
      }
    };
  }
  
  public static final <T> LongSparseArray<T> plus(LongSparseArray<T> paramLongSparseArray1, LongSparseArray<T> paramLongSparseArray2)
  {
    Intrinsics.checkNotNullParameter(paramLongSparseArray1, "<this>");
    Intrinsics.checkNotNullParameter(paramLongSparseArray2, "other");
    LongSparseArray localLongSparseArray = new LongSparseArray(paramLongSparseArray1.size() + paramLongSparseArray2.size());
    putAll(localLongSparseArray, paramLongSparseArray1);
    putAll(localLongSparseArray, paramLongSparseArray2);
    return localLongSparseArray;
  }
  
  public static final <T> void putAll(LongSparseArray<T> paramLongSparseArray1, LongSparseArray<T> paramLongSparseArray2)
  {
    Intrinsics.checkNotNullParameter(paramLongSparseArray1, "<this>");
    Intrinsics.checkNotNullParameter(paramLongSparseArray2, "other");
    int i = 0;
    int j = paramLongSparseArray2.size();
    while (i < j)
    {
      paramLongSparseArray1.put(paramLongSparseArray2.keyAt(i), paramLongSparseArray2.valueAt(i));
      i++;
    }
  }
  
  public static final <T> boolean remove(LongSparseArray<T> paramLongSparseArray, long paramLong, T paramT)
  {
    Intrinsics.checkNotNullParameter(paramLongSparseArray, "<this>");
    int i = paramLongSparseArray.indexOfKey(paramLong);
    if ((i >= 0) && (Intrinsics.areEqual(paramT, paramLongSparseArray.valueAt(i))))
    {
      paramLongSparseArray.removeAt(i);
      return true;
    }
    return false;
  }
  
  public static final <T> void set(LongSparseArray<T> paramLongSparseArray, long paramLong, T paramT)
  {
    Intrinsics.checkNotNullParameter(paramLongSparseArray, "<this>");
    paramLongSparseArray.put(paramLong, paramT);
  }
  
  public static final <T> Iterator<T> valueIterator(LongSparseArray<T> paramLongSparseArray)
  {
    Intrinsics.checkNotNullParameter(paramLongSparseArray, "<this>");
    (Iterator)new Iterator()
    {
      final LongSparseArray<T> $this_valueIterator;
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
        LongSparseArray localLongSparseArray = this.$this_valueIterator;
        int i = this.index;
        this.index = (i + 1);
        return (T)localLongSparseArray.valueAt(i);
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


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/core/util/LongSparseArrayKt.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */