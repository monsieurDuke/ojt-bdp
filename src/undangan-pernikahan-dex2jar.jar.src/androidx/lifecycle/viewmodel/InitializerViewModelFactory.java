package androidx.lifecycle.viewmodel;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider.Factory;
import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

@Metadata(d1={"\000*\n\002\030\002\n\002\030\002\n\000\n\002\020\021\n\002\030\002\n\002\b\004\n\002\030\002\n\000\n\002\030\002\n\000\n\002\030\002\n\002\b\002\b\000\030\0002\0020\001B!\022\032\020\002\032\016\022\n\b\001\022\006\022\002\b\0030\0040\003\"\006\022\002\b\0030\004¢\006\002\020\005J-\020\007\032\002H\b\"\b\b\000\020\b*\0020\t2\f\020\n\032\b\022\004\022\002H\b0\0132\006\020\f\032\0020\rH\026¢\006\002\020\016R\034\020\002\032\016\022\n\b\001\022\006\022\002\b\0030\0040\003X\004¢\006\004\n\002\020\006¨\006\017"}, d2={"Landroidx/lifecycle/viewmodel/InitializerViewModelFactory;", "Landroidx/lifecycle/ViewModelProvider$Factory;", "initializers", "", "Landroidx/lifecycle/viewmodel/ViewModelInitializer;", "([Landroidx/lifecycle/viewmodel/ViewModelInitializer;)V", "[Landroidx/lifecycle/viewmodel/ViewModelInitializer;", "create", "T", "Landroidx/lifecycle/ViewModel;", "modelClass", "Ljava/lang/Class;", "extras", "Landroidx/lifecycle/viewmodel/CreationExtras;", "(Ljava/lang/Class;Landroidx/lifecycle/viewmodel/CreationExtras;)Landroidx/lifecycle/ViewModel;", "lifecycle-viewmodel_release"}, k=1, mv={1, 6, 0}, xi=48)
public final class InitializerViewModelFactory
  implements ViewModelProvider.Factory
{
  private final ViewModelInitializer<?>[] initializers;
  
  public InitializerViewModelFactory(ViewModelInitializer<?>... paramVarArgs)
  {
    this.initializers = paramVarArgs;
  }
  
  public <T extends ViewModel> T create(Class<T> paramClass, CreationExtras paramCreationExtras)
  {
    Intrinsics.checkNotNullParameter(paramClass, "modelClass");
    Intrinsics.checkNotNullParameter(paramCreationExtras, "extras");
    Object localObject = null;
    for (ViewModelInitializer localViewModelInitializer : this.initializers) {
      if (Intrinsics.areEqual(localViewModelInitializer.getClazz$lifecycle_viewmodel_release(), paramClass))
      {
        localObject = localViewModelInitializer.getInitializer$lifecycle_viewmodel_release().invoke(paramCreationExtras);
        if ((localObject instanceof ViewModel)) {
          localObject = (ViewModel)localObject;
        } else {
          localObject = null;
        }
      }
    }
    if (localObject != null) {
      return (T)localObject;
    }
    throw new IllegalArgumentException("No initializer set for given class " + paramClass.getName());
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/lifecycle/viewmodel/InitializerViewModelFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */