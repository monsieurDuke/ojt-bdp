package androidx.core.os;

import android.os.PersistableBundle;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.jvm.internal.Intrinsics;

@Metadata(d1={"\000\"\n\000\n\002\030\002\n\000\n\002\020\021\n\002\030\002\n\002\020\016\n\002\020\000\n\002\b\002\n\002\020$\n\000\032\b\020\000\032\0020\001H\007\032=\020\000\032\0020\0012.\020\002\032\030\022\024\b\001\022\020\022\004\022\0020\005\022\006\022\004\030\0010\0060\0040\003\"\020\022\004\022\0020\005\022\006\022\004\030\0010\0060\004H\007¢\006\002\020\007\032\032\020\b\032\0020\001*\020\022\004\022\0020\005\022\006\022\004\030\0010\0060\tH\007¨\006\n"}, d2={"persistableBundleOf", "Landroid/os/PersistableBundle;", "pairs", "", "Lkotlin/Pair;", "", "", "([Lkotlin/Pair;)Landroid/os/PersistableBundle;", "toPersistableBundle", "", "core-ktx_release"}, k=2, mv={1, 6, 0}, xi=48)
public final class PersistableBundleKt
{
  public static final PersistableBundle persistableBundleOf()
  {
    return PersistableBundleApi21ImplKt.createPersistableBundle(0);
  }
  
  public static final PersistableBundle persistableBundleOf(Pair<String, ? extends Object>... paramVarArgs)
  {
    Intrinsics.checkNotNullParameter(paramVarArgs, "pairs");
    PersistableBundle localPersistableBundle = PersistableBundleApi21ImplKt.createPersistableBundle(paramVarArgs.length);
    int j = paramVarArgs.length;
    for (int i = 0; i < j; i++)
    {
      Pair<String, ? extends Object> localPair = paramVarArgs[i];
      PersistableBundleApi21ImplKt.putValue(localPersistableBundle, (String)localPair.component1(), localPair.component2());
    }
    return localPersistableBundle;
  }
  
  public static final PersistableBundle toPersistableBundle(Map<String, ? extends Object> paramMap)
  {
    Intrinsics.checkNotNullParameter(paramMap, "<this>");
    PersistableBundle localPersistableBundle = PersistableBundleApi21ImplKt.createPersistableBundle(paramMap.size());
    paramMap = paramMap.entrySet().iterator();
    while (paramMap.hasNext())
    {
      Map.Entry localEntry = (Map.Entry)paramMap.next();
      PersistableBundleApi21ImplKt.putValue(localPersistableBundle, (String)localEntry.getKey(), localEntry.getValue());
    }
    return localPersistableBundle;
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/core/os/PersistableBundleKt.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */