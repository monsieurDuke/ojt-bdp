package androidx.savedstate;

import android.os.Bundle;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.Lifecycle.State;
import androidx.lifecycle.LifecycleObserver;
import kotlin.Metadata;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.Intrinsics;

@Metadata(d1={"\0000\n\002\030\002\n\002\020\000\n\000\n\002\030\002\n\002\b\002\n\002\020\013\n\000\n\002\030\002\n\002\b\003\n\002\020\002\n\002\b\002\n\002\030\002\n\002\b\004\030\000 \0222\0020\001:\001\022B\017\b\002\022\006\020\002\032\0020\003¢\006\002\020\004J\b\020\013\032\0020\fH\007J\022\020\r\032\0020\f2\b\020\016\032\004\030\0010\017H\007J\020\020\020\032\0020\f2\006\020\021\032\0020\017H\007R\016\020\005\032\0020\006X\016¢\006\002\n\000R\016\020\002\032\0020\003X\004¢\006\002\n\000R\021\020\007\032\0020\b¢\006\b\n\000\032\004\b\t\020\n¨\006\023"}, d2={"Landroidx/savedstate/SavedStateRegistryController;", "", "owner", "Landroidx/savedstate/SavedStateRegistryOwner;", "(Landroidx/savedstate/SavedStateRegistryOwner;)V", "attached", "", "savedStateRegistry", "Landroidx/savedstate/SavedStateRegistry;", "getSavedStateRegistry", "()Landroidx/savedstate/SavedStateRegistry;", "performAttach", "", "performRestore", "savedState", "Landroid/os/Bundle;", "performSave", "outBundle", "Companion", "savedstate_release"}, k=1, mv={1, 6, 0}, xi=48)
public final class SavedStateRegistryController
{
  public static final Companion Companion = new Companion(null);
  private boolean attached;
  private final SavedStateRegistryOwner owner;
  private final SavedStateRegistry savedStateRegistry;
  
  private SavedStateRegistryController(SavedStateRegistryOwner paramSavedStateRegistryOwner)
  {
    this.owner = paramSavedStateRegistryOwner;
    this.savedStateRegistry = new SavedStateRegistry();
  }
  
  @JvmStatic
  public static final SavedStateRegistryController create(SavedStateRegistryOwner paramSavedStateRegistryOwner)
  {
    return Companion.create(paramSavedStateRegistryOwner);
  }
  
  public final SavedStateRegistry getSavedStateRegistry()
  {
    return this.savedStateRegistry;
  }
  
  public final void performAttach()
  {
    Lifecycle localLifecycle = this.owner.getLifecycle();
    Intrinsics.checkNotNullExpressionValue(localLifecycle, "owner.lifecycle");
    int i;
    if (localLifecycle.getCurrentState() == Lifecycle.State.INITIALIZED) {
      i = 1;
    } else {
      i = 0;
    }
    if (i != 0)
    {
      localLifecycle.addObserver((LifecycleObserver)new Recreator(this.owner));
      this.savedStateRegistry.performAttach$savedstate_release(localLifecycle);
      this.attached = true;
      return;
    }
    throw new IllegalStateException("Restarter must be created only during owner's initialization stage".toString());
  }
  
  public final void performRestore(Bundle paramBundle)
  {
    if (!this.attached) {
      performAttach();
    }
    Lifecycle localLifecycle = this.owner.getLifecycle();
    Intrinsics.checkNotNullExpressionValue(localLifecycle, "owner.lifecycle");
    if ((localLifecycle.getCurrentState().isAtLeast(Lifecycle.State.STARTED) ^ true))
    {
      this.savedStateRegistry.performRestore$savedstate_release(paramBundle);
      return;
    }
    throw new IllegalStateException(("performRestore cannot be called when owner is " + localLifecycle.getCurrentState()).toString());
  }
  
  public final void performSave(Bundle paramBundle)
  {
    Intrinsics.checkNotNullParameter(paramBundle, "outBundle");
    this.savedStateRegistry.performSave(paramBundle);
  }
  
  @Metadata(d1={"\000\030\n\002\030\002\n\002\020\000\n\002\b\002\n\002\030\002\n\000\n\002\030\002\n\000\b\003\030\0002\0020\001B\007\b\002¢\006\002\020\002J\020\020\003\032\0020\0042\006\020\005\032\0020\006H\007¨\006\007"}, d2={"Landroidx/savedstate/SavedStateRegistryController$Companion;", "", "()V", "create", "Landroidx/savedstate/SavedStateRegistryController;", "owner", "Landroidx/savedstate/SavedStateRegistryOwner;", "savedstate_release"}, k=1, mv={1, 6, 0}, xi=48)
  public static final class Companion
  {
    @JvmStatic
    public final SavedStateRegistryController create(SavedStateRegistryOwner paramSavedStateRegistryOwner)
    {
      Intrinsics.checkNotNullParameter(paramSavedStateRegistryOwner, "owner");
      return new SavedStateRegistryController(paramSavedStateRegistryOwner, null);
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/savedstate/SavedStateRegistryController.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */