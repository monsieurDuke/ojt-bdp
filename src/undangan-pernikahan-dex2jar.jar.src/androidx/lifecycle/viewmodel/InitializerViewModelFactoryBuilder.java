package androidx.lifecycle.viewmodel;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider.Factory;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.JvmClassMappingKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.KClass;

@ViewModelFactoryDsl
@Metadata(d1={"\000<\n\002\030\002\n\002\020\000\n\002\b\002\n\002\020!\n\002\030\002\n\000\n\002\020\002\n\000\n\002\030\002\n\000\n\002\030\002\n\000\n\002\030\002\n\002\030\002\n\002\030\002\n\000\n\002\030\002\n\000\b\007\030\0002\0020\001B\005¢\006\002\020\002J7\020\006\032\0020\007\"\b\b\000\020\b*\0020\t2\f\020\n\032\b\022\004\022\002H\b0\0132\027\020\f\032\023\022\004\022\0020\016\022\004\022\002H\b0\r¢\006\002\b\017J\006\020\020\032\0020\021R\030\020\003\032\f\022\b\022\006\022\002\b\0030\0050\004X\004¢\006\002\n\000¨\006\022"}, d2={"Landroidx/lifecycle/viewmodel/InitializerViewModelFactoryBuilder;", "", "()V", "initializers", "", "Landroidx/lifecycle/viewmodel/ViewModelInitializer;", "addInitializer", "", "T", "Landroidx/lifecycle/ViewModel;", "clazz", "Lkotlin/reflect/KClass;", "initializer", "Lkotlin/Function1;", "Landroidx/lifecycle/viewmodel/CreationExtras;", "Lkotlin/ExtensionFunctionType;", "build", "Landroidx/lifecycle/ViewModelProvider$Factory;", "lifecycle-viewmodel_release"}, k=1, mv={1, 6, 0}, xi=48)
public final class InitializerViewModelFactoryBuilder
{
  private final List<ViewModelInitializer<?>> initializers = (List)new ArrayList();
  
  public final <T extends ViewModel> void addInitializer(KClass<T> paramKClass, Function1<? super CreationExtras, ? extends T> paramFunction1)
  {
    Intrinsics.checkNotNullParameter(paramKClass, "clazz");
    Intrinsics.checkNotNullParameter(paramFunction1, "initializer");
    this.initializers.add(new ViewModelInitializer(JvmClassMappingKt.getJavaClass(paramKClass), paramFunction1));
  }
  
  public final ViewModelProvider.Factory build()
  {
    Object localObject = (Collection)this.initializers;
    localObject = ((Collection)localObject).toArray(new ViewModelInitializer[0]);
    if (localObject != null)
    {
      localObject = (ViewModelInitializer[])localObject;
      return (ViewModelProvider.Factory)new InitializerViewModelFactory((ViewModelInitializer[])Arrays.copyOf((Object[])localObject, localObject.length));
    }
    throw new NullPointerException("null cannot be cast to non-null type kotlin.Array<T of kotlin.collections.ArraysKt__ArraysJVMKt.toTypedArray>");
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/lifecycle/viewmodel/InitializerViewModelFactoryBuilder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */