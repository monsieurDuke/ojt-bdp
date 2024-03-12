package androidx.lifecycle;

import androidx.lifecycle.viewmodel.CreationExtras;
import androidx.lifecycle.viewmodel.CreationExtras.Empty;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

@Metadata(d1={"\000\034\n\000\n\002\030\002\n\000\n\002\030\002\n\002\b\002\n\002\030\002\n\002\030\002\n\002\b\002\032\020\020\000\032\0020\0012\006\020\002\032\0020\003H\000\032\036\020\004\032\002H\005\"\n\b\000\020\005\030\001*\0020\006*\0020\007H\b¢\006\002\020\b¨\006\t"}, d2={"defaultCreationExtras", "Landroidx/lifecycle/viewmodel/CreationExtras;", "owner", "Landroidx/lifecycle/ViewModelStoreOwner;", "get", "VM", "Landroidx/lifecycle/ViewModel;", "Landroidx/lifecycle/ViewModelProvider;", "(Landroidx/lifecycle/ViewModelProvider;)Landroidx/lifecycle/ViewModel;", "lifecycle-viewmodel_release"}, k=2, mv={1, 6, 0}, xi=48)
public final class ViewModelProviderGetKt
{
  public static final CreationExtras defaultCreationExtras(ViewModelStoreOwner paramViewModelStoreOwner)
  {
    Intrinsics.checkNotNullParameter(paramViewModelStoreOwner, "owner");
    if ((paramViewModelStoreOwner instanceof HasDefaultViewModelProviderFactory))
    {
      paramViewModelStoreOwner = ((HasDefaultViewModelProviderFactory)paramViewModelStoreOwner).getDefaultViewModelCreationExtras();
      Intrinsics.checkNotNullExpressionValue(paramViewModelStoreOwner, "{\n        owner.defaultV…ModelCreationExtras\n    }");
    }
    else
    {
      paramViewModelStoreOwner = (CreationExtras)CreationExtras.Empty.INSTANCE;
    }
    return paramViewModelStoreOwner;
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/lifecycle/ViewModelProviderGetKt.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */