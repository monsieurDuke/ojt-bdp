package androidx.lifecycle.viewmodel;

import androidx.lifecycle.ViewModelProvider.Factory;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

@Metadata(d1={"\000&\n\000\n\002\030\002\n\000\n\002\030\002\n\002\030\002\n\002\020\002\n\002\030\002\n\002\b\002\n\002\030\002\n\002\030\002\n\000\032%\020\000\032\0020\0012\027\020\002\032\023\022\004\022\0020\004\022\004\022\0020\0050\003¢\006\002\b\006H\bø\001\000\0327\020\007\032\0020\005\"\n\b\000\020\b\030\001*\0020\t*\0020\0042\031\b\b\020\007\032\023\022\004\022\0020\n\022\004\022\002H\b0\003¢\006\002\b\006H\bø\001\000\002\007\n\005\b20\001¨\006\013"}, d2={"viewModelFactory", "Landroidx/lifecycle/ViewModelProvider$Factory;", "builder", "Lkotlin/Function1;", "Landroidx/lifecycle/viewmodel/InitializerViewModelFactoryBuilder;", "", "Lkotlin/ExtensionFunctionType;", "initializer", "VM", "Landroidx/lifecycle/ViewModel;", "Landroidx/lifecycle/viewmodel/CreationExtras;", "lifecycle-viewmodel_release"}, k=2, mv={1, 6, 0}, xi=48)
public final class InitializerViewModelFactoryKt
{
  public static final ViewModelProvider.Factory viewModelFactory(Function1<? super InitializerViewModelFactoryBuilder, Unit> paramFunction1)
  {
    Intrinsics.checkNotNullParameter(paramFunction1, "builder");
    InitializerViewModelFactoryBuilder localInitializerViewModelFactoryBuilder = new InitializerViewModelFactoryBuilder();
    paramFunction1.invoke(localInitializerViewModelFactoryBuilder);
    return localInitializerViewModelFactoryBuilder.build();
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/lifecycle/viewmodel/InitializerViewModelFactoryKt.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */