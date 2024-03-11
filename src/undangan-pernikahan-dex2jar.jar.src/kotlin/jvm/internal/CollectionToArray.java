package kotlin.jvm.internal;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;

@Metadata(d1={"\0002\n\000\n\002\020\021\n\002\020\000\n\002\b\002\n\002\020\b\n\002\b\002\n\002\020\036\n\002\b\006\n\002\030\002\n\000\n\002\030\002\n\000\n\002\030\002\n\002\b\002\032#\020\006\032\n\022\006\022\004\030\0010\0020\0012\n\020\007\032\006\022\002\b\0030\bH\007¢\006\004\b\t\020\n\0325\020\006\032\n\022\006\022\004\030\0010\0020\0012\n\020\007\032\006\022\002\b\0030\b2\020\020\013\032\f\022\006\022\004\030\0010\002\030\0010\001H\007¢\006\004\b\t\020\f\032~\020\r\032\n\022\006\022\004\030\0010\0020\0012\n\020\007\032\006\022\002\b\0030\b2\024\020\016\032\020\022\f\022\n\022\006\022\004\030\0010\0020\0010\0172\032\020\020\032\026\022\004\022\0020\005\022\f\022\n\022\006\022\004\030\0010\0020\0010\0212(\020\022\032$\022\f\022\n\022\006\022\004\030\0010\0020\001\022\004\022\0020\005\022\f\022\n\022\006\022\004\030\0010\0020\0010\023H\b¢\006\002\020\024\"\030\020\000\032\n\022\006\022\004\030\0010\0020\001X\004¢\006\004\n\002\020\003\"\016\020\004\032\0020\005XT¢\006\002\n\000¨\006\025"}, d2={"EMPTY", "", "", "[Ljava/lang/Object;", "MAX_SIZE", "", "collectionToArray", "collection", "", "toArray", "(Ljava/util/Collection;)[Ljava/lang/Object;", "a", "(Ljava/util/Collection;[Ljava/lang/Object;)[Ljava/lang/Object;", "toArrayImpl", "empty", "Lkotlin/Function0;", "alloc", "Lkotlin/Function1;", "trim", "Lkotlin/Function2;", "(Ljava/util/Collection;Lkotlin/jvm/functions/Function0;Lkotlin/jvm/functions/Function1;Lkotlin/jvm/functions/Function2;)[Ljava/lang/Object;", "kotlin-stdlib"}, k=2, mv={1, 7, 1}, xi=48)
public final class CollectionToArray
{
  private static final Object[] EMPTY = new Object[0];
  private static final int MAX_SIZE = 2147483645;
  
  public static final Object[] toArray(Collection<?> paramCollection)
  {
    Intrinsics.checkNotNullParameter(paramCollection, "collection");
    int i = paramCollection.size();
    Iterator localIterator;
    if (i == 0)
    {
      paramCollection = EMPTY;
    }
    else
    {
      localIterator = paramCollection.iterator();
      if (!localIterator.hasNext())
      {
        paramCollection = EMPTY;
      }
      else
      {
        paramCollection = new Object[i];
        i = 0;
      }
    }
    for (;;)
    {
      int j = i + 1;
      paramCollection[i] = localIterator.next();
      if (j >= paramCollection.length)
      {
        if (localIterator.hasNext())
        {
          int k = j * 3 + 1 >>> 1;
          i = k;
          if (k <= j) {
            if (j < 2147483645) {
              i = 2147483645;
            } else {
              throw new OutOfMemoryError();
            }
          }
          paramCollection = Arrays.copyOf(paramCollection, i);
          Intrinsics.checkNotNullExpressionValue(paramCollection, "copyOf(result, newSize)");
          i = j;
        }
      }
      else
      {
        if (localIterator.hasNext()) {
          break label165;
        }
        paramCollection = Arrays.copyOf(paramCollection, j);
        Intrinsics.checkNotNullExpressionValue(paramCollection, "copyOf(result, size)");
      }
      return paramCollection;
      label165:
      i = j;
    }
  }
  
  public static final Object[] toArray(Collection<?> paramCollection, Object[] paramArrayOfObject)
  {
    Intrinsics.checkNotNullParameter(paramCollection, "collection");
    if (paramArrayOfObject != null)
    {
      int i = paramCollection.size();
      Iterator localIterator;
      if (i == 0)
      {
        if (paramArrayOfObject.length > 0) {
          paramArrayOfObject[0] = null;
        }
      }
      else
      {
        localIterator = paramCollection.iterator();
        if (localIterator.hasNext()) {
          break label65;
        }
        if (paramArrayOfObject.length > 0) {
          paramArrayOfObject[0] = null;
        }
      }
      paramCollection = paramArrayOfObject;
      break label225;
      label65:
      if (i <= paramArrayOfObject.length)
      {
        paramCollection = paramArrayOfObject;
      }
      else
      {
        paramCollection = Array.newInstance(paramArrayOfObject.getClass().getComponentType(), i);
        Intrinsics.checkNotNull(paramCollection, "null cannot be cast to non-null type kotlin.Array<kotlin.Any?>");
        paramCollection = (Object[])paramCollection;
      }
      i = 0;
      for (;;)
      {
        int j = i + 1;
        paramCollection[i] = localIterator.next();
        if (j >= paramCollection.length)
        {
          if (localIterator.hasNext())
          {
            int k = j * 3 + 1 >>> 1;
            i = k;
            if (k <= j) {
              if (j < 2147483645) {
                i = 2147483645;
              } else {
                throw new OutOfMemoryError();
              }
            }
            paramCollection = Arrays.copyOf(paramCollection, i);
            Intrinsics.checkNotNullExpressionValue(paramCollection, "copyOf(result, newSize)");
            i = j;
          }
        }
        else
        {
          if (localIterator.hasNext()) {
            break label227;
          }
          if (paramCollection == paramArrayOfObject)
          {
            paramArrayOfObject[j] = null;
            paramCollection = paramArrayOfObject;
          }
          else
          {
            paramCollection = Arrays.copyOf(paramCollection, j);
            Intrinsics.checkNotNullExpressionValue(paramCollection, "copyOf(result, size)");
          }
        }
        label225:
        return paramCollection;
        label227:
        i = j;
      }
    }
    throw new NullPointerException();
  }
  
  private static final Object[] toArrayImpl(Collection<?> paramCollection, Function0<Object[]> paramFunction0, Function1<? super Integer, Object[]> paramFunction1, Function2<? super Object[], ? super Integer, Object[]> paramFunction2)
  {
    int i = paramCollection.size();
    if (i == 0) {
      return (Object[])paramFunction0.invoke();
    }
    Iterator localIterator = paramCollection.iterator();
    if (!localIterator.hasNext()) {
      return (Object[])paramFunction0.invoke();
    }
    paramCollection = (Object[])paramFunction1.invoke(Integer.valueOf(i));
    i = 0;
    for (;;)
    {
      int j = i + 1;
      paramCollection[i] = localIterator.next();
      if (j >= paramCollection.length)
      {
        if (!localIterator.hasNext()) {
          return paramCollection;
        }
        int k = j * 3 + 1 >>> 1;
        i = k;
        if (k <= j) {
          if (j < 2147483645) {
            i = 2147483645;
          } else {
            throw new OutOfMemoryError();
          }
        }
        paramCollection = Arrays.copyOf(paramCollection, i);
        Intrinsics.checkNotNullExpressionValue(paramCollection, "copyOf(result, newSize)");
        i = j;
      }
      else
      {
        if (!localIterator.hasNext()) {
          return (Object[])paramFunction2.invoke(paramCollection, Integer.valueOf(j));
        }
        i = j;
      }
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlin/jvm/internal/CollectionToArray.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */