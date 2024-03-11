package androidx.lifecycle.viewmodel;

import java.util.Map;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

@Metadata(d1={"\000\034\n\002\030\002\n\002\030\002\n\002\b\005\n\002\030\002\n\002\b\002\n\002\020\002\n\002\b\003\030\0002\0020\001B\017\022\b\b\002\020\002\032\0020\001¢\006\002\020\003J$\020\004\032\004\030\001H\005\"\004\b\000\020\0052\f\020\006\032\b\022\004\022\002H\0050\007H\002¢\006\002\020\bJ*\020\t\032\0020\n\"\004\b\000\020\0052\f\020\006\032\b\022\004\022\002H\0050\0072\006\020\013\032\002H\005H\002¢\006\002\020\f¨\006\r"}, d2={"Landroidx/lifecycle/viewmodel/MutableCreationExtras;", "Landroidx/lifecycle/viewmodel/CreationExtras;", "initialExtras", "(Landroidx/lifecycle/viewmodel/CreationExtras;)V", "get", "T", "key", "Landroidx/lifecycle/viewmodel/CreationExtras$Key;", "(Landroidx/lifecycle/viewmodel/CreationExtras$Key;)Ljava/lang/Object;", "set", "", "t", "(Landroidx/lifecycle/viewmodel/CreationExtras$Key;Ljava/lang/Object;)V", "lifecycle-viewmodel_release"}, k=1, mv={1, 6, 0}, xi=48)
public final class MutableCreationExtras
  extends CreationExtras
{
  public MutableCreationExtras()
  {
    this(null, 1, null);
  }
  
  public MutableCreationExtras(CreationExtras paramCreationExtras)
  {
    getMap$lifecycle_viewmodel_release().putAll(paramCreationExtras.getMap$lifecycle_viewmodel_release());
  }
  
  public <T> T get(CreationExtras.Key<T> paramKey)
  {
    Intrinsics.checkNotNullParameter(paramKey, "key");
    return (T)getMap$lifecycle_viewmodel_release().get(paramKey);
  }
  
  public final <T> void set(CreationExtras.Key<T> paramKey, T paramT)
  {
    Intrinsics.checkNotNullParameter(paramKey, "key");
    getMap$lifecycle_viewmodel_release().put(paramKey, paramT);
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/lifecycle/viewmodel/MutableCreationExtras.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */