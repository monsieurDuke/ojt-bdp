package androidx.lifecycle;

import android.view.View;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

@Metadata(d1={"\000\f\n\000\n\002\030\002\n\002\030\002\n\000\032\f\020\000\032\004\030\0010\001*\0020\002¨\006\003"}, d2={"findViewTreeViewModelStoreOwner", "Landroidx/lifecycle/ViewModelStoreOwner;", "Landroid/view/View;", "lifecycle-viewmodel_release"}, k=2, mv={1, 6, 0}, xi=48)
public final class ViewTreeViewModelKt
{
  public static final ViewModelStoreOwner findViewTreeViewModelStoreOwner(View paramView)
  {
    Intrinsics.checkNotNullParameter(paramView, "<this>");
    return ViewTreeViewModelStoreOwner.get(paramView);
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/lifecycle/ViewTreeViewModelKt.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */