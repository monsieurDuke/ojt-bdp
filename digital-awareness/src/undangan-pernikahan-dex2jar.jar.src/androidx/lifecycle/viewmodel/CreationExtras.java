package androidx.lifecycle.viewmodel;

import java.util.LinkedHashMap;
import java.util.Map;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

@Metadata(d1={"\000\030\n\002\030\002\n\002\020\000\n\002\b\002\n\002\020%\n\002\030\002\n\002\b\t\b&\030\0002\0020\001:\002\f\rB\007\b\000¢\006\002\020\002J$\020\b\032\004\030\001H\t\"\004\b\000\020\t2\f\020\n\032\b\022\004\022\002H\t0\005H¦\002¢\006\002\020\013R&\020\003\032\024\022\b\022\006\022\002\b\0030\005\022\006\022\004\030\0010\0010\004X\004¢\006\b\n\000\032\004\b\006\020\007¨\006\016"}, d2={"Landroidx/lifecycle/viewmodel/CreationExtras;", "", "()V", "map", "", "Landroidx/lifecycle/viewmodel/CreationExtras$Key;", "getMap$lifecycle_viewmodel_release", "()Ljava/util/Map;", "get", "T", "key", "(Landroidx/lifecycle/viewmodel/CreationExtras$Key;)Ljava/lang/Object;", "Empty", "Key", "lifecycle-viewmodel_release"}, k=1, mv={1, 6, 0}, xi=48)
public abstract class CreationExtras
{
  private final Map<Key<?>, Object> map = (Map)new LinkedHashMap();
  
  public abstract <T> T get(Key<T> paramKey);
  
  public final Map<Key<?>, Object> getMap$lifecycle_viewmodel_release()
  {
    return this.map;
  }
  
  @Metadata(d1={"\000\024\n\002\030\002\n\002\030\002\n\002\b\004\n\002\030\002\n\002\b\002\bÆ\002\030\0002\0020\001B\007\b\002¢\006\002\020\002J$\020\003\032\004\030\001H\004\"\004\b\000\020\0042\f\020\005\032\b\022\004\022\002H\0040\006H\002¢\006\002\020\007¨\006\b"}, d2={"Landroidx/lifecycle/viewmodel/CreationExtras$Empty;", "Landroidx/lifecycle/viewmodel/CreationExtras;", "()V", "get", "T", "key", "Landroidx/lifecycle/viewmodel/CreationExtras$Key;", "(Landroidx/lifecycle/viewmodel/CreationExtras$Key;)Ljava/lang/Object;", "lifecycle-viewmodel_release"}, k=1, mv={1, 6, 0}, xi=48)
  public static final class Empty
    extends CreationExtras
  {
    public static final Empty INSTANCE = new Empty();
    
    public <T> T get(CreationExtras.Key<T> paramKey)
    {
      Intrinsics.checkNotNullParameter(paramKey, "key");
      return null;
    }
  }
  
  @Metadata(d1={"\000\f\n\002\030\002\n\000\n\002\020\000\n\000\bf\030\000*\004\b\000\020\0012\0020\002ø\001\000\002\006\n\004\b!0\001¨\006\003À\006\001"}, d2={"Landroidx/lifecycle/viewmodel/CreationExtras$Key;", "T", "", "lifecycle-viewmodel_release"}, k=1, mv={1, 6, 0}, xi=48)
  public static abstract interface Key<T> {}
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/lifecycle/viewmodel/CreationExtras.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */