package androidx.savedstate;

import android.os.Bundle;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.Lifecycle.Event;
import androidx.lifecycle.LifecycleEventObserver;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

@Metadata(d1={"\000.\n\002\030\002\n\002\030\002\n\000\n\002\030\002\n\002\b\002\n\002\020\002\n\000\n\002\030\002\n\000\n\002\030\002\n\002\b\002\n\002\020\016\n\002\b\003\b\000\030\000 \0162\0020\001:\002\016\017B\r\022\006\020\002\032\0020\003¢\006\002\020\004J\030\020\005\032\0020\0062\006\020\007\032\0020\b2\006\020\t\032\0020\nH\026J\020\020\013\032\0020\0062\006\020\f\032\0020\rH\002R\016\020\002\032\0020\003X\004¢\006\002\n\000¨\006\020"}, d2={"Landroidx/savedstate/Recreator;", "Landroidx/lifecycle/LifecycleEventObserver;", "owner", "Landroidx/savedstate/SavedStateRegistryOwner;", "(Landroidx/savedstate/SavedStateRegistryOwner;)V", "onStateChanged", "", "source", "Landroidx/lifecycle/LifecycleOwner;", "event", "Landroidx/lifecycle/Lifecycle$Event;", "reflectiveNew", "className", "", "Companion", "SavedStateProvider", "savedstate_release"}, k=1, mv={1, 6, 0}, xi=48)
public final class Recreator
  implements LifecycleEventObserver
{
  public static final String CLASSES_KEY = "classes_to_restore";
  public static final String COMPONENT_KEY = "androidx.savedstate.Restarter";
  public static final Companion Companion = new Companion(null);
  private final SavedStateRegistryOwner owner;
  
  public Recreator(SavedStateRegistryOwner paramSavedStateRegistryOwner)
  {
    this.owner = paramSavedStateRegistryOwner;
  }
  
  /* Error */
  private final void reflectiveNew(String paramString)
  {
    // Byte code:
    //   0: aload_1
    //   1: iconst_0
    //   2: ldc 2
    //   4: invokevirtual 78	java/lang/Class:getClassLoader	()Ljava/lang/ClassLoader;
    //   7: invokestatic 82	java/lang/Class:forName	(Ljava/lang/String;ZLjava/lang/ClassLoader;)Ljava/lang/Class;
    //   10: ldc 84
    //   12: invokevirtual 88	java/lang/Class:asSubclass	(Ljava/lang/Class;)Ljava/lang/Class;
    //   15: astore_2
    //   16: aload_2
    //   17: ldc 90
    //   19: invokestatic 93	kotlin/jvm/internal/Intrinsics:checkNotNullExpressionValue	(Ljava/lang/Object;Ljava/lang/String;)V
    //   22: aload_2
    //   23: iconst_0
    //   24: anewarray 74	java/lang/Class
    //   27: invokevirtual 97	java/lang/Class:getDeclaredConstructor	([Ljava/lang/Class;)Ljava/lang/reflect/Constructor;
    //   30: astore_3
    //   31: aload_3
    //   32: iconst_1
    //   33: invokevirtual 103	java/lang/reflect/Constructor:setAccessible	(Z)V
    //   36: aload_3
    //   37: iconst_0
    //   38: anewarray 4	java/lang/Object
    //   41: invokevirtual 107	java/lang/reflect/Constructor:newInstance	([Ljava/lang/Object;)Ljava/lang/Object;
    //   44: astore_2
    //   45: aload_2
    //   46: ldc 109
    //   48: invokestatic 93	kotlin/jvm/internal/Intrinsics:checkNotNullExpressionValue	(Ljava/lang/Object;Ljava/lang/String;)V
    //   51: aload_2
    //   52: checkcast 84	androidx/savedstate/SavedStateRegistry$AutoRecreated
    //   55: astore_2
    //   56: aload_2
    //   57: aload_0
    //   58: getfield 65	androidx/savedstate/Recreator:owner	Landroidx/savedstate/SavedStateRegistryOwner;
    //   61: invokeinterface 112 2 0
    //   66: return
    //   67: astore_2
    //   68: new 114	java/lang/RuntimeException
    //   71: dup
    //   72: new 116	java/lang/StringBuilder
    //   75: dup
    //   76: invokespecial 117	java/lang/StringBuilder:<init>	()V
    //   79: ldc 119
    //   81: invokevirtual 123	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   84: aload_1
    //   85: invokevirtual 123	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   88: invokevirtual 127	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   91: aload_2
    //   92: checkcast 129	java/lang/Throwable
    //   95: invokespecial 132	java/lang/RuntimeException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   98: athrow
    //   99: astore_1
    //   100: new 134	java/lang/IllegalStateException
    //   103: dup
    //   104: new 116	java/lang/StringBuilder
    //   107: dup
    //   108: invokespecial 117	java/lang/StringBuilder:<init>	()V
    //   111: ldc -120
    //   113: invokevirtual 123	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   116: aload_2
    //   117: invokevirtual 139	java/lang/Class:getSimpleName	()Ljava/lang/String;
    //   120: invokevirtual 123	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   123: ldc -115
    //   125: invokevirtual 123	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   128: invokevirtual 127	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   131: aload_1
    //   132: checkcast 129	java/lang/Throwable
    //   135: invokespecial 142	java/lang/IllegalStateException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   138: athrow
    //   139: astore_2
    //   140: new 114	java/lang/RuntimeException
    //   143: dup
    //   144: new 116	java/lang/StringBuilder
    //   147: dup
    //   148: invokespecial 117	java/lang/StringBuilder:<init>	()V
    //   151: ldc -120
    //   153: invokevirtual 123	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   156: aload_1
    //   157: invokevirtual 123	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   160: ldc -112
    //   162: invokevirtual 123	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   165: invokevirtual 127	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   168: aload_2
    //   169: checkcast 129	java/lang/Throwable
    //   172: invokespecial 132	java/lang/RuntimeException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   175: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	176	0	this	Recreator
    //   0	176	1	paramString	String
    //   15	42	2	localObject	Object
    //   67	50	2	localException	Exception
    //   139	30	2	localClassNotFoundException	ClassNotFoundException
    //   30	7	3	localConstructor	java.lang.reflect.Constructor
    // Exception table:
    //   from	to	target	type
    //   36	56	67	java/lang/Exception
    //   22	31	99	java/lang/NoSuchMethodException
    //   0	22	139	java/lang/ClassNotFoundException
  }
  
  public void onStateChanged(LifecycleOwner paramLifecycleOwner, Lifecycle.Event paramEvent)
  {
    Intrinsics.checkNotNullParameter(paramLifecycleOwner, "source");
    Intrinsics.checkNotNullParameter(paramEvent, "event");
    if (paramEvent == Lifecycle.Event.ON_CREATE)
    {
      paramLifecycleOwner.getLifecycle().removeObserver((LifecycleObserver)this);
      paramLifecycleOwner = this.owner.getSavedStateRegistry().consumeRestoredStateForKey("androidx.savedstate.Restarter");
      if (paramLifecycleOwner == null) {
        return;
      }
      paramLifecycleOwner = paramLifecycleOwner.getStringArrayList("classes_to_restore");
      if (paramLifecycleOwner != null)
      {
        paramLifecycleOwner = ((List)paramLifecycleOwner).iterator();
        while (paramLifecycleOwner.hasNext()) {
          reflectiveNew((String)paramLifecycleOwner.next());
        }
        return;
      }
      throw new IllegalStateException("Bundle with restored state for the component \"androidx.savedstate.Restarter\" must contain list of strings by the key \"classes_to_restore\"");
    }
    throw new AssertionError("Next event must be ON_CREATE");
  }
  
  @Metadata(d1={"\000\024\n\002\030\002\n\002\020\000\n\002\b\002\n\002\020\016\n\002\b\002\b\003\030\0002\0020\001B\007\b\002¢\006\002\020\002R\016\020\003\032\0020\004XT¢\006\002\n\000R\016\020\005\032\0020\004XT¢\006\002\n\000¨\006\006"}, d2={"Landroidx/savedstate/Recreator$Companion;", "", "()V", "CLASSES_KEY", "", "COMPONENT_KEY", "savedstate_release"}, k=1, mv={1, 6, 0}, xi=48)
  public static final class Companion {}
  
  @Metadata(d1={"\000*\n\002\030\002\n\002\030\002\n\000\n\002\030\002\n\002\b\002\n\002\020#\n\002\020\016\n\000\n\002\020\002\n\002\b\002\n\002\030\002\n\000\b\000\030\0002\0020\001B\r\022\006\020\002\032\0020\003¢\006\002\020\004J\016\020\b\032\0020\t2\006\020\n\032\0020\007J\b\020\013\032\0020\fH\026R\024\020\005\032\b\022\004\022\0020\0070\006X\004¢\006\002\n\000¨\006\r"}, d2={"Landroidx/savedstate/Recreator$SavedStateProvider;", "Landroidx/savedstate/SavedStateRegistry$SavedStateProvider;", "registry", "Landroidx/savedstate/SavedStateRegistry;", "(Landroidx/savedstate/SavedStateRegistry;)V", "classes", "", "", "add", "", "className", "saveState", "Landroid/os/Bundle;", "savedstate_release"}, k=1, mv={1, 6, 0}, xi=48)
  public static final class SavedStateProvider
    implements SavedStateRegistry.SavedStateProvider
  {
    private final Set<String> classes = (Set)new LinkedHashSet();
    
    public SavedStateProvider(SavedStateRegistry paramSavedStateRegistry)
    {
      paramSavedStateRegistry.registerSavedStateProvider("androidx.savedstate.Restarter", (SavedStateRegistry.SavedStateProvider)this);
    }
    
    public final void add(String paramString)
    {
      Intrinsics.checkNotNullParameter(paramString, "className");
      this.classes.add(paramString);
    }
    
    public Bundle saveState()
    {
      Bundle localBundle = new Bundle();
      localBundle.putStringArrayList("classes_to_restore", new ArrayList((Collection)this.classes));
      return localBundle;
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/savedstate/Recreator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */