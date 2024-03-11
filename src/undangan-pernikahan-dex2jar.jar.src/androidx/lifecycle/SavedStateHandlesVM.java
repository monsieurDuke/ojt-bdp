package androidx.lifecycle;

import java.util.LinkedHashMap;
import java.util.Map;
import kotlin.Metadata;

@Metadata(d1={"\000\034\n\002\030\002\n\002\030\002\n\002\b\002\n\002\020%\n\002\020\016\n\002\030\002\n\002\b\003\b\000\030\0002\0020\001B\005¢\006\002\020\002R\035\020\003\032\016\022\004\022\0020\005\022\004\022\0020\0060\004¢\006\b\n\000\032\004\b\007\020\b¨\006\t"}, d2={"Landroidx/lifecycle/SavedStateHandlesVM;", "Landroidx/lifecycle/ViewModel;", "()V", "handles", "", "", "Landroidx/lifecycle/SavedStateHandle;", "getHandles", "()Ljava/util/Map;", "lifecycle-viewmodel-savedstate_release"}, k=1, mv={1, 6, 0}, xi=48)
public final class SavedStateHandlesVM
  extends ViewModel
{
  private final Map<String, SavedStateHandle> handles = (Map)new LinkedHashMap();
  
  public final Map<String, SavedStateHandle> getHandles()
  {
    return this.handles;
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/lifecycle/SavedStateHandlesVM.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */