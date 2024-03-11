package kotlin.collections;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.markers.KMappedMarker;

@Metadata(d1={"\000*\n\000\n\002\020\034\n\002\b\002\n\002\030\002\n\002\020(\n\000\n\002\020\b\n\002\b\004\n\002\020 \n\000\n\002\030\002\n\002\b\002\032.\020\000\032\b\022\004\022\002H\0020\001\"\004\b\000\020\0022\024\b\004\020\003\032\016\022\n\022\b\022\004\022\002H\0020\0050\004H\bø\001\000\032 \020\006\032\0020\007\"\004\b\000\020\002*\b\022\004\022\002H\0020\0012\006\020\b\032\0020\007H\001\032\037\020\t\032\004\030\0010\007\"\004\b\000\020\002*\b\022\004\022\002H\0020\001H\001¢\006\002\020\n\032\"\020\013\032\b\022\004\022\002H\0020\f\"\004\b\000\020\002*\016\022\n\022\b\022\004\022\002H\0020\0010\001\032@\020\r\032\032\022\n\022\b\022\004\022\002H\0020\f\022\n\022\b\022\004\022\002H\0170\f0\016\"\004\b\000\020\002\"\004\b\001\020\017*\024\022\020\022\016\022\004\022\002H\002\022\004\022\002H\0170\0160\001\002\007\n\005\b20\001¨\006\020"}, d2={"Iterable", "", "T", "iterator", "Lkotlin/Function0;", "", "collectionSizeOrDefault", "", "default", "collectionSizeOrNull", "(Ljava/lang/Iterable;)Ljava/lang/Integer;", "flatten", "", "unzip", "Lkotlin/Pair;", "R", "kotlin-stdlib"}, k=5, mv={1, 7, 1}, xi=49, xs="kotlin/collections/CollectionsKt")
class CollectionsKt__IterablesKt
  extends CollectionsKt__CollectionsKt
{
  private static final <T> Iterable<T> Iterable(Function0<? extends Iterator<? extends T>> paramFunction0)
  {
    Intrinsics.checkNotNullParameter(paramFunction0, "iterator");
    (Iterable)new Iterable()
    {
      final Function0<Iterator<T>> $iterator;
      
      public Iterator<T> iterator()
      {
        return (Iterator)this.$iterator.invoke();
      }
    };
  }
  
  public static final <T> int collectionSizeOrDefault(Iterable<? extends T> paramIterable, int paramInt)
  {
    Intrinsics.checkNotNullParameter(paramIterable, "<this>");
    if ((paramIterable instanceof Collection)) {
      paramInt = ((Collection)paramIterable).size();
    }
    return paramInt;
  }
  
  public static final <T> Integer collectionSizeOrNull(Iterable<? extends T> paramIterable)
  {
    Intrinsics.checkNotNullParameter(paramIterable, "<this>");
    if ((paramIterable instanceof Collection)) {
      paramIterable = Integer.valueOf(((Collection)paramIterable).size());
    } else {
      paramIterable = null;
    }
    return paramIterable;
  }
  
  public static final <T> List<T> flatten(Iterable<? extends Iterable<? extends T>> paramIterable)
  {
    Intrinsics.checkNotNullParameter(paramIterable, "<this>");
    ArrayList localArrayList = new ArrayList();
    paramIterable = paramIterable.iterator();
    while (paramIterable.hasNext())
    {
      Iterable localIterable = (Iterable)paramIterable.next();
      CollectionsKt.addAll((Collection)localArrayList, localIterable);
    }
    return (List)localArrayList;
  }
  
  public static final <T, R> Pair<List<T>, List<R>> unzip(Iterable<? extends Pair<? extends T, ? extends R>> paramIterable)
  {
    Intrinsics.checkNotNullParameter(paramIterable, "<this>");
    int i = CollectionsKt.collectionSizeOrDefault(paramIterable, 10);
    ArrayList localArrayList1 = new ArrayList(i);
    ArrayList localArrayList2 = new ArrayList(i);
    paramIterable = paramIterable.iterator();
    while (paramIterable.hasNext())
    {
      Pair localPair = (Pair)paramIterable.next();
      localArrayList1.add(localPair.getFirst());
      localArrayList2.add(localPair.getSecond());
    }
    return TuplesKt.to(localArrayList1, localArrayList2);
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlin/collections/CollectionsKt__IterablesKt.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */