package androidx.lifecycle;

import android.os.Bundle;
import androidx.lifecycle.viewmodel.CreationExtras;
import androidx.lifecycle.viewmodel.CreationExtras.Key;
import androidx.lifecycle.viewmodel.InitializerViewModelFactoryBuilder;
import androidx.savedstate.SavedStateRegistry;
import androidx.savedstate.SavedStateRegistry.SavedStateProvider;
import androidx.savedstate.SavedStateRegistryOwner;
import java.util.Map;
import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import kotlin.jvm.internal.Reflection;

@Metadata(d1={"\000F\n\000\n\002\030\002\n\002\030\002\n\000\n\002\020\016\n\000\n\002\030\002\n\002\b\002\n\002\030\002\n\000\n\002\030\002\n\002\b\003\n\002\030\002\n\002\b\003\n\002\030\002\n\002\b\004\n\002\030\002\n\000\n\002\020\002\n\002\b\003\032*\020\022\032\0020\0232\006\020\024\032\0020\0062\006\020\025\032\0020\t2\006\020\026\032\0020\0042\b\020\027\032\004\030\0010\002H\002\032\f\020\022\032\0020\023*\0020\030H\007\032\037\020\031\032\0020\032\"\f\b\000\020\033*\0020\006*\0020\t*\002H\033H\007¢\006\002\020\034\"\026\020\000\032\b\022\004\022\0020\0020\0018\006X\004¢\006\002\n\000\"\016\020\003\032\0020\004XT¢\006\002\n\000\"\026\020\005\032\b\022\004\022\0020\0060\0018\006X\004¢\006\002\n\000\"\016\020\007\032\0020\004XT¢\006\002\n\000\"\026\020\b\032\b\022\004\022\0020\t0\0018\006X\004¢\006\002\n\000\"\030\020\n\032\0020\013*\0020\0068@X\004¢\006\006\032\004\b\f\020\r\"\030\020\016\032\0020\017*\0020\t8@X\004¢\006\006\032\004\b\020\020\021¨\006\035"}, d2={"DEFAULT_ARGS_KEY", "Landroidx/lifecycle/viewmodel/CreationExtras$Key;", "Landroid/os/Bundle;", "SAVED_STATE_KEY", "", "SAVED_STATE_REGISTRY_OWNER_KEY", "Landroidx/savedstate/SavedStateRegistryOwner;", "VIEWMODEL_KEY", "VIEW_MODEL_STORE_OWNER_KEY", "Landroidx/lifecycle/ViewModelStoreOwner;", "savedStateHandlesProvider", "Landroidx/lifecycle/SavedStateHandlesProvider;", "getSavedStateHandlesProvider", "(Landroidx/savedstate/SavedStateRegistryOwner;)Landroidx/lifecycle/SavedStateHandlesProvider;", "savedStateHandlesVM", "Landroidx/lifecycle/SavedStateHandlesVM;", "getSavedStateHandlesVM", "(Landroidx/lifecycle/ViewModelStoreOwner;)Landroidx/lifecycle/SavedStateHandlesVM;", "createSavedStateHandle", "Landroidx/lifecycle/SavedStateHandle;", "savedStateRegistryOwner", "viewModelStoreOwner", "key", "defaultArgs", "Landroidx/lifecycle/viewmodel/CreationExtras;", "enableSavedStateHandles", "", "T", "(Landroidx/savedstate/SavedStateRegistryOwner;)V", "lifecycle-viewmodel-savedstate_release"}, k=2, mv={1, 6, 0}, xi=48)
public final class SavedStateHandleSupport
{
  public static final CreationExtras.Key<Bundle> DEFAULT_ARGS_KEY = (CreationExtras.Key)new CreationExtras.Key() {};
  private static final String SAVED_STATE_KEY = "androidx.lifecycle.internal.SavedStateHandlesProvider";
  public static final CreationExtras.Key<SavedStateRegistryOwner> SAVED_STATE_REGISTRY_OWNER_KEY = (CreationExtras.Key)new CreationExtras.Key() {};
  private static final String VIEWMODEL_KEY = "androidx.lifecycle.internal.SavedStateHandlesVM";
  public static final CreationExtras.Key<ViewModelStoreOwner> VIEW_MODEL_STORE_OWNER_KEY = (CreationExtras.Key)new CreationExtras.Key() {};
  
  public static final SavedStateHandle createSavedStateHandle(CreationExtras paramCreationExtras)
  {
    Intrinsics.checkNotNullParameter(paramCreationExtras, "<this>");
    SavedStateRegistryOwner localSavedStateRegistryOwner = (SavedStateRegistryOwner)paramCreationExtras.get(SAVED_STATE_REGISTRY_OWNER_KEY);
    if (localSavedStateRegistryOwner != null)
    {
      ViewModelStoreOwner localViewModelStoreOwner = (ViewModelStoreOwner)paramCreationExtras.get(VIEW_MODEL_STORE_OWNER_KEY);
      if (localViewModelStoreOwner != null)
      {
        Bundle localBundle = (Bundle)paramCreationExtras.get(DEFAULT_ARGS_KEY);
        paramCreationExtras = (String)paramCreationExtras.get(ViewModelProvider.NewInstanceFactory.VIEW_MODEL_KEY);
        if (paramCreationExtras != null) {
          return createSavedStateHandle(localSavedStateRegistryOwner, localViewModelStoreOwner, paramCreationExtras, localBundle);
        }
        throw new IllegalArgumentException("CreationExtras must have a value by `VIEW_MODEL_KEY`");
      }
      throw new IllegalArgumentException("CreationExtras must have a value by `VIEW_MODEL_STORE_OWNER_KEY`");
    }
    throw new IllegalArgumentException("CreationExtras must have a value by `SAVED_STATE_REGISTRY_OWNER_KEY`");
  }
  
  private static final SavedStateHandle createSavedStateHandle(SavedStateRegistryOwner paramSavedStateRegistryOwner, ViewModelStoreOwner paramViewModelStoreOwner, String paramString, Bundle paramBundle)
  {
    SavedStateHandlesProvider localSavedStateHandlesProvider = getSavedStateHandlesProvider(paramSavedStateRegistryOwner);
    SavedStateHandlesVM localSavedStateHandlesVM = getSavedStateHandlesVM(paramViewModelStoreOwner);
    paramViewModelStoreOwner = (SavedStateHandle)localSavedStateHandlesVM.getHandles().get(paramString);
    paramSavedStateRegistryOwner = paramViewModelStoreOwner;
    if (paramViewModelStoreOwner == null)
    {
      paramSavedStateRegistryOwner = SavedStateHandle.Companion.createHandle(localSavedStateHandlesProvider.consumeRestoredStateForKey(paramString), paramBundle);
      localSavedStateHandlesVM.getHandles().put(paramString, paramSavedStateRegistryOwner);
    }
    return paramSavedStateRegistryOwner;
  }
  
  public static final <T extends SavedStateRegistryOwner,  extends ViewModelStoreOwner> void enableSavedStateHandles(T paramT)
  {
    Intrinsics.checkNotNullParameter(paramT, "<this>");
    Object localObject = paramT.getLifecycle().getCurrentState();
    Intrinsics.checkNotNullExpressionValue(localObject, "lifecycle.currentState");
    int i;
    if ((localObject != Lifecycle.State.INITIALIZED) && (localObject != Lifecycle.State.CREATED)) {
      i = 0;
    } else {
      i = 1;
    }
    if (i != 0)
    {
      if (paramT.getSavedStateRegistry().getSavedStateProvider("androidx.lifecycle.internal.SavedStateHandlesProvider") == null)
      {
        localObject = new SavedStateHandlesProvider(paramT.getSavedStateRegistry(), (ViewModelStoreOwner)paramT);
        paramT.getSavedStateRegistry().registerSavedStateProvider("androidx.lifecycle.internal.SavedStateHandlesProvider", (SavedStateRegistry.SavedStateProvider)localObject);
        paramT.getLifecycle().addObserver((LifecycleObserver)new SavedStateHandleAttacher((SavedStateHandlesProvider)localObject));
      }
      return;
    }
    throw new IllegalArgumentException("Failed requirement.".toString());
  }
  
  public static final SavedStateHandlesProvider getSavedStateHandlesProvider(SavedStateRegistryOwner paramSavedStateRegistryOwner)
  {
    Intrinsics.checkNotNullParameter(paramSavedStateRegistryOwner, "<this>");
    paramSavedStateRegistryOwner = paramSavedStateRegistryOwner.getSavedStateRegistry().getSavedStateProvider("androidx.lifecycle.internal.SavedStateHandlesProvider");
    if ((paramSavedStateRegistryOwner instanceof SavedStateHandlesProvider)) {
      paramSavedStateRegistryOwner = (SavedStateHandlesProvider)paramSavedStateRegistryOwner;
    } else {
      paramSavedStateRegistryOwner = null;
    }
    if (paramSavedStateRegistryOwner != null) {
      return paramSavedStateRegistryOwner;
    }
    throw new IllegalStateException("enableSavedStateHandles() wasn't called prior to createSavedStateHandle() call");
  }
  
  public static final SavedStateHandlesVM getSavedStateHandlesVM(ViewModelStoreOwner paramViewModelStoreOwner)
  {
    Intrinsics.checkNotNullParameter(paramViewModelStoreOwner, "<this>");
    InitializerViewModelFactoryBuilder localInitializerViewModelFactoryBuilder = new InitializerViewModelFactoryBuilder();
    Function1 localFunction1 = (Function1)savedStateHandlesVM.1.1.INSTANCE;
    localInitializerViewModelFactoryBuilder.addInitializer(Reflection.getOrCreateKotlinClass(SavedStateHandlesVM.class), localFunction1);
    return (SavedStateHandlesVM)new ViewModelProvider(paramViewModelStoreOwner, localInitializerViewModelFactoryBuilder.build()).get("androidx.lifecycle.internal.SavedStateHandlesVM", SavedStateHandlesVM.class);
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/lifecycle/SavedStateHandleSupport.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */