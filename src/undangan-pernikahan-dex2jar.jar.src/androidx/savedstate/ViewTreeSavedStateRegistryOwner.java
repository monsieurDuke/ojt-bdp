package androidx.savedstate;

import android.view.View;
import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import kotlin.sequences.SequencesKt;

@Metadata(d1={"\000\026\n\000\n\002\030\002\n\002\030\002\n\002\b\002\n\002\020\002\n\002\b\003\032\023\020\000\032\004\030\0010\001*\0020\002H\007¢\006\002\b\003\032\033\020\004\032\0020\005*\0020\0022\b\020\006\032\004\030\0010\001H\007¢\006\002\b\007¨\006\b"}, d2={"findViewTreeSavedStateRegistryOwner", "Landroidx/savedstate/SavedStateRegistryOwner;", "Landroid/view/View;", "get", "setViewTreeSavedStateRegistryOwner", "", "owner", "set", "savedstate_release"}, k=2, mv={1, 6, 0}, xi=48)
public final class ViewTreeSavedStateRegistryOwner
{
  public static final SavedStateRegistryOwner get(View paramView)
  {
    Intrinsics.checkNotNullParameter(paramView, "<this>");
    return (SavedStateRegistryOwner)SequencesKt.firstOrNull(SequencesKt.mapNotNull(SequencesKt.generateSequence(paramView, (Function1)findViewTreeSavedStateRegistryOwner.1.INSTANCE), (Function1)findViewTreeSavedStateRegistryOwner.2.INSTANCE));
  }
  
  public static final void set(View paramView, SavedStateRegistryOwner paramSavedStateRegistryOwner)
  {
    Intrinsics.checkNotNullParameter(paramView, "<this>");
    paramView.setTag(R.id.view_tree_saved_state_registry_owner, paramSavedStateRegistryOwner);
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/savedstate/ViewTreeSavedStateRegistryOwner.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */