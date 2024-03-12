package androidx.core.util;

import android.util.SparseLongArray;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.IntIterator;
import kotlin.collections.LongIterator;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;

@Metadata(d1={"\000D\n\000\n\002\020\b\n\002\030\002\n\002\b\003\n\002\020\013\n\002\b\004\n\002\020\t\n\000\n\002\020\002\n\000\n\002\030\002\n\002\030\002\n\002\b\004\n\002\030\002\n\002\b\003\n\002\030\002\n\002\b\006\n\002\030\002\n\000\032\025\020\005\032\0020\006*\0020\0022\006\020\007\032\0020\001H\n\032\025\020\b\032\0020\006*\0020\0022\006\020\007\032\0020\001H\b\032\025\020\t\032\0020\006*\0020\0022\006\020\n\032\0020\013H\b\032H\020\f\032\0020\r*\0020\00226\020\016\0322\022\023\022\0210\001¢\006\f\b\020\022\b\b\021\022\004\b\b(\007\022\023\022\0210\013¢\006\f\b\020\022\b\b\021\022\004\b\b(\n\022\004\022\0020\r0\017H\bø\001\000\032\035\020\022\032\0020\013*\0020\0022\006\020\007\032\0020\0012\006\020\023\032\0020\013H\b\032&\020\024\032\0020\013*\0020\0022\006\020\007\032\0020\0012\f\020\023\032\b\022\004\022\0020\0130\025H\bø\001\000\032\r\020\026\032\0020\006*\0020\002H\b\032\r\020\027\032\0020\006*\0020\002H\b\032\f\020\030\032\0020\031*\0020\002H\007\032\025\020\032\032\0020\002*\0020\0022\006\020\033\032\0020\002H\002\032\024\020\034\032\0020\r*\0020\0022\006\020\033\032\0020\002H\007\032\034\020\035\032\0020\006*\0020\0022\006\020\007\032\0020\0012\006\020\n\032\0020\013H\007\032\035\020\036\032\0020\r*\0020\0022\006\020\007\032\0020\0012\006\020\n\032\0020\013H\n\032\f\020\037\032\0020 *\0020\002H\007\"\026\020\000\032\0020\001*\0020\0028Ç\002¢\006\006\032\004\b\003\020\004\002\007\n\005\b20\001¨\006!"}, d2={"size", "", "Landroid/util/SparseLongArray;", "getSize", "(Landroid/util/SparseLongArray;)I", "contains", "", "key", "containsKey", "containsValue", "value", "", "forEach", "", "action", "Lkotlin/Function2;", "Lkotlin/ParameterName;", "name", "getOrDefault", "defaultValue", "getOrElse", "Lkotlin/Function0;", "isEmpty", "isNotEmpty", "keyIterator", "Lkotlin/collections/IntIterator;", "plus", "other", "putAll", "remove", "set", "valueIterator", "Lkotlin/collections/LongIterator;", "core-ktx_release"}, k=2, mv={1, 6, 0}, xi=48)
public final class SparseLongArrayKt
{
  public static final boolean contains(SparseLongArray paramSparseLongArray, int paramInt)
  {
    Intrinsics.checkNotNullParameter(paramSparseLongArray, "<this>");
    boolean bool;
    if (paramSparseLongArray.indexOfKey(paramInt) >= 0) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public static final boolean containsKey(SparseLongArray paramSparseLongArray, int paramInt)
  {
    Intrinsics.checkNotNullParameter(paramSparseLongArray, "<this>");
    boolean bool;
    if (paramSparseLongArray.indexOfKey(paramInt) >= 0) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public static final boolean containsValue(SparseLongArray paramSparseLongArray, long paramLong)
  {
    Intrinsics.checkNotNullParameter(paramSparseLongArray, "<this>");
    boolean bool;
    if (paramSparseLongArray.indexOfValue(paramLong) >= 0) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public static final void forEach(SparseLongArray paramSparseLongArray, Function2<? super Integer, ? super Long, Unit> paramFunction2)
  {
    Intrinsics.checkNotNullParameter(paramSparseLongArray, "<this>");
    Intrinsics.checkNotNullParameter(paramFunction2, "action");
    int i = 0;
    int j = paramSparseLongArray.size();
    while (i < j)
    {
      paramFunction2.invoke(Integer.valueOf(paramSparseLongArray.keyAt(i)), Long.valueOf(paramSparseLongArray.valueAt(i)));
      i++;
    }
  }
  
  public static final long getOrDefault(SparseLongArray paramSparseLongArray, int paramInt, long paramLong)
  {
    Intrinsics.checkNotNullParameter(paramSparseLongArray, "<this>");
    return paramSparseLongArray.get(paramInt, paramLong);
  }
  
  public static final long getOrElse(SparseLongArray paramSparseLongArray, int paramInt, Function0<Long> paramFunction0)
  {
    Intrinsics.checkNotNullParameter(paramSparseLongArray, "<this>");
    Intrinsics.checkNotNullParameter(paramFunction0, "defaultValue");
    paramInt = paramSparseLongArray.indexOfKey(paramInt);
    long l;
    if (paramInt >= 0) {
      l = paramSparseLongArray.valueAt(paramInt);
    } else {
      l = ((Number)paramFunction0.invoke()).longValue();
    }
    return l;
  }
  
  public static final int getSize(SparseLongArray paramSparseLongArray)
  {
    Intrinsics.checkNotNullParameter(paramSparseLongArray, "<this>");
    return paramSparseLongArray.size();
  }
  
  public static final boolean isEmpty(SparseLongArray paramSparseLongArray)
  {
    Intrinsics.checkNotNullParameter(paramSparseLongArray, "<this>");
    boolean bool;
    if (paramSparseLongArray.size() == 0) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public static final boolean isNotEmpty(SparseLongArray paramSparseLongArray)
  {
    Intrinsics.checkNotNullParameter(paramSparseLongArray, "<this>");
    boolean bool;
    if (paramSparseLongArray.size() != 0) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public static final IntIterator keyIterator(SparseLongArray paramSparseLongArray)
  {
    Intrinsics.checkNotNullParameter(paramSparseLongArray, "<this>");
    (IntIterator)new IntIterator()
    {
      final SparseLongArray $this_keyIterator;
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
        SparseLongArray localSparseLongArray = this.$this_keyIterator;
        int i = this.index;
        this.index = (i + 1);
        return localSparseLongArray.keyAt(i);
      }
      
      public final void setIndex(int paramAnonymousInt)
      {
        this.index = paramAnonymousInt;
      }
    };
  }
  
  public static final SparseLongArray plus(SparseLongArray paramSparseLongArray1, SparseLongArray paramSparseLongArray2)
  {
    Intrinsics.checkNotNullParameter(paramSparseLongArray1, "<this>");
    Intrinsics.checkNotNullParameter(paramSparseLongArray2, "other");
    SparseLongArray localSparseLongArray = new SparseLongArray(paramSparseLongArray1.size() + paramSparseLongArray2.size());
    putAll(localSparseLongArray, paramSparseLongArray1);
    putAll(localSparseLongArray, paramSparseLongArray2);
    return localSparseLongArray;
  }
  
  public static final void putAll(SparseLongArray paramSparseLongArray1, SparseLongArray paramSparseLongArray2)
  {
    Intrinsics.checkNotNullParameter(paramSparseLongArray1, "<this>");
    Intrinsics.checkNotNullParameter(paramSparseLongArray2, "other");
    int i = 0;
    int j = paramSparseLongArray2.size();
    while (i < j)
    {
      paramSparseLongArray1.put(paramSparseLongArray2.keyAt(i), paramSparseLongArray2.valueAt(i));
      i++;
    }
  }
  
  public static final boolean remove(SparseLongArray paramSparseLongArray, int paramInt, long paramLong)
  {
    Intrinsics.checkNotNullParameter(paramSparseLongArray, "<this>");
    paramInt = paramSparseLongArray.indexOfKey(paramInt);
    if ((paramInt >= 0) && (paramLong == paramSparseLongArray.valueAt(paramInt)))
    {
      paramSparseLongArray.removeAt(paramInt);
      return true;
    }
    return false;
  }
  
  public static final void set(SparseLongArray paramSparseLongArray, int paramInt, long paramLong)
  {
    Intrinsics.checkNotNullParameter(paramSparseLongArray, "<this>");
    paramSparseLongArray.put(paramInt, paramLong);
  }
  
  public static final LongIterator valueIterator(SparseLongArray paramSparseLongArray)
  {
    Intrinsics.checkNotNullParameter(paramSparseLongArray, "<this>");
    (LongIterator)new LongIterator()
    {
      final SparseLongArray $this_valueIterator;
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
      
      public long nextLong()
      {
        SparseLongArray localSparseLongArray = this.$this_valueIterator;
        int i = this.index;
        this.index = (i + 1);
        return localSparseLongArray.valueAt(i);
      }
      
      public final void setIndex(int paramAnonymousInt)
      {
        this.index = paramAnonymousInt;
      }
    };
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/core/util/SparseLongArrayKt.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */