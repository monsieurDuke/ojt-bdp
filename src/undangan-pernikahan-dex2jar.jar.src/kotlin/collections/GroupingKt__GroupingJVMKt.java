package kotlin.collections;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref.IntRef;
import kotlin.jvm.internal.TypeIntrinsics;

@Metadata(d1={"\000&\n\000\n\002\020$\n\000\n\002\020\b\n\000\n\002\030\002\n\000\n\002\020%\n\002\b\003\n\002\030\002\n\002\020&\n\000\0320\020\000\032\016\022\004\022\002H\002\022\004\022\0020\0030\001\"\004\b\000\020\004\"\004\b\001\020\002*\016\022\004\022\002H\004\022\004\022\002H\0020\005H\007\032Z\020\006\032\016\022\004\022\002H\002\022\004\022\002H\b0\007\"\004\b\000\020\002\"\004\b\001\020\t\"\004\b\002\020\b*\016\022\004\022\002H\002\022\004\022\002H\t0\0072\036\020\n\032\032\022\020\022\016\022\004\022\002H\002\022\004\022\002H\t0\f\022\004\022\002H\b0\013H\bø\001\000\002\007\n\005\b20\001¨\006\r"}, d2={"eachCount", "", "K", "", "T", "Lkotlin/collections/Grouping;", "mapValuesInPlace", "", "R", "V", "f", "Lkotlin/Function1;", "", "kotlin-stdlib"}, k=5, mv={1, 7, 1}, xi=49, xs="kotlin/collections/GroupingKt")
class GroupingKt__GroupingJVMKt
{
  public static final <T, K> Map<K, Integer> eachCount(Grouping<T, ? extends K> paramGrouping)
  {
    Intrinsics.checkNotNullParameter(paramGrouping, "<this>");
    Map localMap = (Map)new LinkedHashMap();
    Object localObject1 = paramGrouping;
    Iterator localIterator = ((Grouping)localObject1).sourceIterator();
    paramGrouping = (Grouping<T, ? extends K>)localObject1;
    while (localIterator.hasNext())
    {
      Object localObject3 = ((Grouping)localObject1).keyOf(localIterator.next());
      localObject2 = localMap.get(localObject3);
      int i;
      if ((localObject2 == null) && (!localMap.containsKey(localObject3))) {
        i = 1;
      } else {
        i = 0;
      }
      if (i != 0) {
        localObject2 = new Ref.IntRef();
      }
      localObject2 = (Ref.IntRef)localObject2;
      ((Ref.IntRef)localObject2).element += 1;
      localMap.put(localObject3, localObject2);
    }
    Object localObject2 = ((Iterable)localMap.entrySet()).iterator();
    while (((Iterator)localObject2).hasNext())
    {
      paramGrouping = (Map.Entry)((Iterator)localObject2).next();
      Intrinsics.checkNotNull(paramGrouping, "null cannot be cast to non-null type kotlin.collections.MutableMap.MutableEntry<K of kotlin.collections.GroupingKt__GroupingJVMKt.mapValuesInPlace$lambda-4, R of kotlin.collections.GroupingKt__GroupingJVMKt.mapValuesInPlace$lambda-4>");
      localObject1 = TypeIntrinsics.asMutableMapEntry(paramGrouping);
      ((Map.Entry)localObject1).setValue(Integer.valueOf(((Ref.IntRef)paramGrouping.getValue()).element));
    }
    return TypeIntrinsics.asMutableMap(localMap);
  }
  
  private static final <K, V, R> Map<K, R> mapValuesInPlace(Map<K, V> paramMap, Function1<? super Map.Entry<? extends K, ? extends V>, ? extends R> paramFunction1)
  {
    Intrinsics.checkNotNullParameter(paramMap, "<this>");
    Intrinsics.checkNotNullParameter(paramFunction1, "f");
    Iterator localIterator = ((Iterable)paramMap.entrySet()).iterator();
    while (localIterator.hasNext())
    {
      Map.Entry localEntry = (Map.Entry)localIterator.next();
      Intrinsics.checkNotNull(localEntry, "null cannot be cast to non-null type kotlin.collections.MutableMap.MutableEntry<K of kotlin.collections.GroupingKt__GroupingJVMKt.mapValuesInPlace$lambda-4, R of kotlin.collections.GroupingKt__GroupingJVMKt.mapValuesInPlace$lambda-4>");
      TypeIntrinsics.asMutableMapEntry(localEntry).setValue(paramFunction1.invoke(localEntry));
    }
    return TypeIntrinsics.asMutableMap(paramMap);
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlin/collections/GroupingKt__GroupingJVMKt.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */