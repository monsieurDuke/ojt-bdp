package kotlin.collections;

import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentMap;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.Unit;
import kotlin.collections.builders.MapBuilder;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

@Metadata(d1={"\000d\n\000\n\002\020\b\n\000\n\002\020$\n\002\b\003\n\002\020%\n\002\b\003\n\002\030\002\n\002\020\002\n\002\030\002\n\002\b\005\n\002\030\002\n\000\n\002\030\002\n\000\n\002\030\002\n\000\n\002\020\021\n\000\n\002\020\017\n\002\b\002\n\002\030\002\n\002\b\002\n\002\030\002\n\002\b\002\n\002\030\002\n\002\020\016\n\002\b\004\0324\020\002\032\016\022\004\022\002H\004\022\004\022\002H\0050\003\"\004\b\000\020\004\"\004\b\001\020\0052\022\020\006\032\016\022\004\022\002H\004\022\004\022\002H\0050\007H\001\032Q\020\b\032\016\022\004\022\002H\004\022\004\022\002H\0050\003\"\004\b\000\020\004\"\004\b\001\020\0052\006\020\t\032\0020\0012#\020\n\032\037\022\020\022\016\022\004\022\002H\004\022\004\022\002H\0050\007\022\004\022\0020\f0\013¢\006\002\b\rH\bø\001\000\032I\020\b\032\016\022\004\022\002H\004\022\004\022\002H\0050\003\"\004\b\000\020\004\"\004\b\001\020\0052#\020\n\032\037\022\020\022\016\022\004\022\002H\004\022\004\022\002H\0050\007\022\004\022\0020\f0\013¢\006\002\b\rH\bø\001\000\032 \020\016\032\016\022\004\022\002H\004\022\004\022\002H\0050\007\"\004\b\000\020\004\"\004\b\001\020\005H\001\032(\020\016\032\016\022\004\022\002H\004\022\004\022\002H\0050\007\"\004\b\000\020\004\"\004\b\001\020\0052\006\020\t\032\0020\001H\001\032\020\020\017\032\0020\0012\006\020\020\032\0020\001H\001\0322\020\021\032\016\022\004\022\002H\004\022\004\022\002H\0050\003\"\004\b\000\020\004\"\004\b\001\020\0052\022\020\022\032\016\022\004\022\002H\004\022\004\022\002H\0050\023\032a\020\024\032\016\022\004\022\002H\004\022\004\022\002H\0050\025\"\004\b\000\020\004\"\004\b\001\020\0052\016\020\026\032\n\022\006\b\000\022\002H\0040\0272*\020\030\032\026\022\022\b\001\022\016\022\004\022\002H\004\022\004\022\002H\0050\0230\031\"\016\022\004\022\002H\004\022\004\022\002H\0050\023H\007¢\006\002\020\032\032Y\020\024\032\016\022\004\022\002H\004\022\004\022\002H\0050\025\"\016\b\000\020\004*\b\022\004\022\002H\0040\033\"\004\b\001\020\0052*\020\030\032\026\022\022\b\001\022\016\022\004\022\002H\004\022\004\022\002H\0050\0230\031\"\016\022\004\022\002H\004\022\004\022\002H\0050\023¢\006\002\020\034\032C\020\035\032\002H\005\"\004\b\000\020\004\"\004\b\001\020\005*\016\022\004\022\002H\004\022\004\022\002H\0050\0362\006\020\037\032\002H\0042\f\020 \032\b\022\004\022\002H\0050!H\bø\001\000¢\006\002\020\"\032\031\020#\032\0020$*\016\022\004\022\0020%\022\004\022\0020%0\003H\b\0322\020&\032\016\022\004\022\002H\004\022\004\022\002H\0050\003\"\004\b\000\020\004\"\004\b\001\020\005*\020\022\006\b\001\022\002H\004\022\004\022\002H\0050\003H\000\0321\020'\032\016\022\004\022\002H\004\022\004\022\002H\0050\003\"\004\b\000\020\004\"\004\b\001\020\005*\016\022\004\022\002H\004\022\004\022\002H\0050\003H\b\032:\020(\032\016\022\004\022\002H\004\022\004\022\002H\0050\025\"\016\b\000\020\004*\b\022\004\022\002H\0040\033\"\004\b\001\020\005*\020\022\006\b\001\022\002H\004\022\004\022\002H\0050\003\032@\020(\032\016\022\004\022\002H\004\022\004\022\002H\0050\025\"\004\b\000\020\004\"\004\b\001\020\005*\020\022\006\b\001\022\002H\004\022\004\022\002H\0050\0032\016\020\026\032\n\022\006\b\000\022\002H\0040\027\"\016\020\000\032\0020\001XT¢\006\002\n\000\002\007\n\005\b20\001¨\006)"}, d2={"INT_MAX_POWER_OF_TWO", "", "build", "", "K", "V", "builder", "", "buildMapInternal", "capacity", "builderAction", "Lkotlin/Function1;", "", "Lkotlin/ExtensionFunctionType;", "createMapBuilder", "mapCapacity", "expectedSize", "mapOf", "pair", "Lkotlin/Pair;", "sortedMapOf", "Ljava/util/SortedMap;", "comparator", "Ljava/util/Comparator;", "pairs", "", "(Ljava/util/Comparator;[Lkotlin/Pair;)Ljava/util/SortedMap;", "", "([Lkotlin/Pair;)Ljava/util/SortedMap;", "getOrPut", "Ljava/util/concurrent/ConcurrentMap;", "key", "defaultValue", "Lkotlin/Function0;", "(Ljava/util/concurrent/ConcurrentMap;Ljava/lang/Object;Lkotlin/jvm/functions/Function0;)Ljava/lang/Object;", "toProperties", "Ljava/util/Properties;", "", "toSingletonMap", "toSingletonMapOrSelf", "toSortedMap", "kotlin-stdlib"}, k=5, mv={1, 7, 1}, xi=49, xs="kotlin/collections/MapsKt")
class MapsKt__MapsJVMKt
  extends MapsKt__MapWithDefaultKt
{
  private static final int INT_MAX_POWER_OF_TWO = 1073741824;
  
  public static final <K, V> Map<K, V> build(Map<K, V> paramMap)
  {
    Intrinsics.checkNotNullParameter(paramMap, "builder");
    return ((MapBuilder)paramMap).build();
  }
  
  private static final <K, V> Map<K, V> buildMapInternal(int paramInt, Function1<? super Map<K, V>, Unit> paramFunction1)
  {
    Intrinsics.checkNotNullParameter(paramFunction1, "builderAction");
    Map localMap = MapsKt.createMapBuilder(paramInt);
    paramFunction1.invoke(localMap);
    return MapsKt.build(localMap);
  }
  
  private static final <K, V> Map<K, V> buildMapInternal(Function1<? super Map<K, V>, Unit> paramFunction1)
  {
    Intrinsics.checkNotNullParameter(paramFunction1, "builderAction");
    Map localMap = MapsKt.createMapBuilder();
    paramFunction1.invoke(localMap);
    return MapsKt.build(localMap);
  }
  
  public static final <K, V> Map<K, V> createMapBuilder()
  {
    return (Map)new MapBuilder();
  }
  
  public static final <K, V> Map<K, V> createMapBuilder(int paramInt)
  {
    return (Map)new MapBuilder(paramInt);
  }
  
  public static final <K, V> V getOrPut(ConcurrentMap<K, V> paramConcurrentMap, K paramK, Function0<? extends V> paramFunction0)
  {
    Intrinsics.checkNotNullParameter(paramConcurrentMap, "<this>");
    Intrinsics.checkNotNullParameter(paramFunction0, "defaultValue");
    Object localObject2 = paramConcurrentMap.get(paramK);
    Object localObject1 = localObject2;
    if (localObject2 == null)
    {
      localObject1 = paramFunction0.invoke();
      paramConcurrentMap = paramConcurrentMap.putIfAbsent(paramK, localObject1);
      if (paramConcurrentMap != null) {
        localObject1 = paramConcurrentMap;
      }
    }
    return (V)localObject1;
  }
  
  public static final int mapCapacity(int paramInt)
  {
    if (paramInt >= 0) {
      if (paramInt < 3) {
        paramInt++;
      } else if (paramInt < 1073741824) {
        paramInt = (int)(paramInt / 0.75F + 1.0F);
      } else {
        paramInt = Integer.MAX_VALUE;
      }
    }
    return paramInt;
  }
  
  public static final <K, V> Map<K, V> mapOf(Pair<? extends K, ? extends V> paramPair)
  {
    Intrinsics.checkNotNullParameter(paramPair, "pair");
    paramPair = Collections.singletonMap(paramPair.getFirst(), paramPair.getSecond());
    Intrinsics.checkNotNullExpressionValue(paramPair, "singletonMap(pair.first, pair.second)");
    return paramPair;
  }
  
  public static final <K, V> SortedMap<K, V> sortedMapOf(Comparator<? super K> paramComparator, Pair<? extends K, ? extends V>... paramVarArgs)
  {
    Intrinsics.checkNotNullParameter(paramComparator, "comparator");
    Intrinsics.checkNotNullParameter(paramVarArgs, "pairs");
    paramComparator = new TreeMap(paramComparator);
    MapsKt.putAll((Map)paramComparator, paramVarArgs);
    return (SortedMap)paramComparator;
  }
  
  public static final <K extends Comparable<? super K>, V> SortedMap<K, V> sortedMapOf(Pair<? extends K, ? extends V>... paramVarArgs)
  {
    Intrinsics.checkNotNullParameter(paramVarArgs, "pairs");
    TreeMap localTreeMap = new TreeMap();
    MapsKt.putAll((Map)localTreeMap, paramVarArgs);
    return (SortedMap)localTreeMap;
  }
  
  private static final Properties toProperties(Map<String, String> paramMap)
  {
    Intrinsics.checkNotNullParameter(paramMap, "<this>");
    Properties localProperties = new Properties();
    localProperties.putAll(paramMap);
    return localProperties;
  }
  
  public static final <K, V> Map<K, V> toSingletonMap(Map<? extends K, ? extends V> paramMap)
  {
    Intrinsics.checkNotNullParameter(paramMap, "<this>");
    paramMap = (Map.Entry)paramMap.entrySet().iterator().next();
    paramMap = Collections.singletonMap(paramMap.getKey(), paramMap.getValue());
    Intrinsics.checkNotNullExpressionValue(paramMap, "with(entries.iterator().…ingletonMap(key, value) }");
    return paramMap;
  }
  
  private static final <K, V> Map<K, V> toSingletonMapOrSelf(Map<K, ? extends V> paramMap)
  {
    Intrinsics.checkNotNullParameter(paramMap, "<this>");
    return MapsKt.toSingletonMap(paramMap);
  }
  
  public static final <K extends Comparable<? super K>, V> SortedMap<K, V> toSortedMap(Map<? extends K, ? extends V> paramMap)
  {
    Intrinsics.checkNotNullParameter(paramMap, "<this>");
    return (SortedMap)new TreeMap(paramMap);
  }
  
  public static final <K, V> SortedMap<K, V> toSortedMap(Map<? extends K, ? extends V> paramMap, Comparator<? super K> paramComparator)
  {
    Intrinsics.checkNotNullParameter(paramMap, "<this>");
    Intrinsics.checkNotNullParameter(paramComparator, "comparator");
    paramComparator = new TreeMap(paramComparator);
    paramComparator.putAll(paramMap);
    return (SortedMap)paramComparator;
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlin/collections/MapsKt__MapsJVMKt.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */