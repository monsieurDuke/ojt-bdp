package androidx.lifecycle;

import android.os.Binder;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Size;
import android.util.SizeF;
import android.util.SparseArray;
import androidx.core.os.BundleKt;
import androidx.savedstate.SavedStateRegistry.SavedStateProvider;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.MapsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.MutableStateFlow;
import kotlinx.coroutines.flow.StateFlow;
import kotlinx.coroutines.flow.StateFlowKt;

@Metadata(d1={"\000X\n\002\030\002\n\002\020\000\n\000\n\002\020$\n\002\020\016\n\002\b\003\n\002\020%\n\002\030\002\n\000\n\002\030\002\n\002\b\002\n\002\030\002\n\002\b\002\n\002\020\002\n\002\b\002\n\002\020\013\n\002\b\004\n\002\030\002\n\002\b\006\n\002\030\002\n\002\b\002\n\002\020\"\n\002\b\t\030\000 *2\0020\001:\002*+B\035\b\026\022\024\020\002\032\020\022\004\022\0020\004\022\006\022\004\030\0010\0010\003¢\006\002\020\005B\007\b\026¢\006\002\020\006J\020\020\020\032\0020\0212\006\020\022\032\0020\004H\007J\021\020\023\032\0020\0242\006\020\022\032\0020\004H\002J\036\020\025\032\004\030\001H\026\"\004\b\000\020\0262\006\020\022\032\0020\004H\002¢\006\002\020\027J\034\020\030\032\b\022\004\022\002H\0260\031\"\004\b\000\020\0262\006\020\022\032\0020\004H\007J)\020\030\032\b\022\004\022\002H\0260\031\"\004\b\000\020\0262\006\020\022\032\0020\0042\006\020\032\032\002H\026H\007¢\006\002\020\033J1\020\034\032\b\022\004\022\002H\0260\031\"\004\b\000\020\0262\006\020\022\032\0020\0042\006\020\035\032\0020\0242\006\020\032\032\002H\026H\002¢\006\002\020\036J)\020\037\032\b\022\004\022\002H\0260 \"\004\b\000\020\0262\006\020\022\032\0020\0042\006\020\032\032\002H\026H\007¢\006\002\020!J\016\020\"\032\b\022\004\022\0020\0040#H\007J\035\020$\032\004\030\001H\026\"\004\b\000\020\0262\006\020\022\032\0020\004H\007¢\006\002\020\027J\b\020\r\032\0020\016H\007J&\020%\032\0020\021\"\004\b\000\020\0262\006\020\022\032\0020\0042\b\020&\032\004\030\001H\026H\002¢\006\002\020'J\030\020(\032\0020\0212\006\020\022\032\0020\0042\006\020)\032\0020\016H\007R\"\020\007\032\026\022\004\022\0020\004\022\f\022\n\022\006\022\004\030\0010\0010\t0\bX\004¢\006\002\n\000R\036\020\n\032\022\022\004\022\0020\004\022\b\022\006\022\002\b\0030\0130\bX\004¢\006\002\n\000R\034\020\f\032\020\022\004\022\0020\004\022\006\022\004\030\0010\0010\bX\004¢\006\002\n\000R\016\020\r\032\0020\016X\004¢\006\002\n\000R\032\020\017\032\016\022\004\022\0020\004\022\004\022\0020\0160\bX\004¢\006\002\n\000¨\006,"}, d2={"Landroidx/lifecycle/SavedStateHandle;", "", "initialState", "", "", "(Ljava/util/Map;)V", "()V", "flows", "", "Lkotlinx/coroutines/flow/MutableStateFlow;", "liveDatas", "Landroidx/lifecycle/SavedStateHandle$SavingStateLiveData;", "regular", "savedStateProvider", "Landroidx/savedstate/SavedStateRegistry$SavedStateProvider;", "savedStateProviders", "clearSavedStateProvider", "", "key", "contains", "", "get", "T", "(Ljava/lang/String;)Ljava/lang/Object;", "getLiveData", "Landroidx/lifecycle/MutableLiveData;", "initialValue", "(Ljava/lang/String;Ljava/lang/Object;)Landroidx/lifecycle/MutableLiveData;", "getLiveDataInternal", "hasInitialValue", "(Ljava/lang/String;ZLjava/lang/Object;)Landroidx/lifecycle/MutableLiveData;", "getStateFlow", "Lkotlinx/coroutines/flow/StateFlow;", "(Ljava/lang/String;Ljava/lang/Object;)Lkotlinx/coroutines/flow/StateFlow;", "keys", "", "remove", "set", "value", "(Ljava/lang/String;Ljava/lang/Object;)V", "setSavedStateProvider", "provider", "Companion", "SavingStateLiveData", "lifecycle-viewmodel-savedstate_release"}, k=1, mv={1, 6, 0}, xi=48)
public final class SavedStateHandle
{
  private static final Class<? extends Object>[] ACCEPTABLE_CLASSES;
  public static final Companion Companion = new Companion(null);
  private static final String KEYS = "keys";
  private static final String VALUES = "values";
  private final Map<String, MutableStateFlow<Object>> flows;
  private final Map<String, SavingStateLiveData<?>> liveDatas;
  private final Map<String, Object> regular;
  private final SavedStateRegistry.SavedStateProvider savedStateProvider;
  private final Map<String, SavedStateRegistry.SavedStateProvider> savedStateProviders;
  
  static
  {
    Class localClass10 = Boolean.TYPE;
    Class localClass9 = Double.TYPE;
    Class localClass5 = Integer.TYPE;
    Class localClass6 = Long.TYPE;
    Class localClass8 = Byte.TYPE;
    Class localClass7 = Character.TYPE;
    Class localClass3 = Float.TYPE;
    Class localClass4 = Short.TYPE;
    Class localClass1;
    if (Build.VERSION.SDK_INT >= 21) {
      localClass1 = Size.class;
    } else {
      localClass1 = Integer.TYPE;
    }
    Class localClass2;
    if (Build.VERSION.SDK_INT >= 21) {
      localClass2 = SizeF.class;
    } else {
      localClass2 = Integer.TYPE;
    }
    ACCEPTABLE_CLASSES = new Class[] { localClass10, boolean[].class, localClass9, double[].class, localClass5, int[].class, localClass6, long[].class, String.class, String[].class, Binder.class, Bundle.class, localClass8, byte[].class, localClass7, char[].class, CharSequence.class, CharSequence[].class, ArrayList.class, localClass3, float[].class, Parcelable.class, Parcelable[].class, Serializable.class, localClass4, short[].class, SparseArray.class, localClass1, localClass2 };
  }
  
  public SavedStateHandle()
  {
    this.regular = ((Map)new LinkedHashMap());
    this.savedStateProviders = ((Map)new LinkedHashMap());
    this.liveDatas = ((Map)new LinkedHashMap());
    this.flows = ((Map)new LinkedHashMap());
    this.savedStateProvider = new SavedStateHandle..ExternalSyntheticLambda0(this);
  }
  
  public SavedStateHandle(Map<String, ? extends Object> paramMap)
  {
    Map localMap = (Map)new LinkedHashMap();
    this.regular = localMap;
    this.savedStateProviders = ((Map)new LinkedHashMap());
    this.liveDatas = ((Map)new LinkedHashMap());
    this.flows = ((Map)new LinkedHashMap());
    this.savedStateProvider = new SavedStateHandle..ExternalSyntheticLambda0(this);
    localMap.putAll(paramMap);
  }
  
  @JvmStatic
  public static final SavedStateHandle createHandle(Bundle paramBundle1, Bundle paramBundle2)
  {
    return Companion.createHandle(paramBundle1, paramBundle2);
  }
  
  private final <T> MutableLiveData<T> getLiveDataInternal(String paramString, boolean paramBoolean, T paramT)
  {
    Object localObject = this.liveDatas.get(paramString);
    if ((localObject instanceof MutableLiveData)) {
      localObject = (MutableLiveData)localObject;
    } else {
      localObject = null;
    }
    if (localObject != null) {
      return (MutableLiveData<T>)localObject;
    }
    if (this.regular.containsKey(paramString))
    {
      paramT = new SavingStateLiveData(this, paramString, this.regular.get(paramString));
    }
    else if (paramBoolean)
    {
      this.regular.put(paramString, paramT);
      paramT = new SavingStateLiveData(this, paramString, paramT);
    }
    else
    {
      paramT = new SavingStateLiveData(this, paramString);
    }
    this.liveDatas.put(paramString, paramT);
    return (MutableLiveData)paramT;
  }
  
  private static final Bundle savedStateProvider$lambda-0(SavedStateHandle paramSavedStateHandle)
  {
    Intrinsics.checkNotNullParameter(paramSavedStateHandle, "this$0");
    Object localObject2 = MapsKt.toMap(paramSavedStateHandle.savedStateProviders).entrySet().iterator();
    while (((Iterator)localObject2).hasNext())
    {
      localObject1 = (Map.Entry)((Iterator)localObject2).next();
      paramSavedStateHandle.set((String)((Map.Entry)localObject1).getKey(), ((SavedStateRegistry.SavedStateProvider)((Map.Entry)localObject1).getValue()).saveState());
    }
    Object localObject3 = paramSavedStateHandle.regular.keySet();
    Object localObject1 = new ArrayList(((Set)localObject3).size());
    localObject2 = new ArrayList(((ArrayList)localObject1).size());
    Iterator localIterator = ((Set)localObject3).iterator();
    while (localIterator.hasNext())
    {
      localObject3 = (String)localIterator.next();
      ((ArrayList)localObject1).add(localObject3);
      ((ArrayList)localObject2).add(paramSavedStateHandle.regular.get(localObject3));
    }
    return BundleKt.bundleOf(new Pair[] { TuplesKt.to("keys", localObject1), TuplesKt.to("values", localObject2) });
  }
  
  public final void clearSavedStateProvider(String paramString)
  {
    Intrinsics.checkNotNullParameter(paramString, "key");
    this.savedStateProviders.remove(paramString);
  }
  
  public final boolean contains(String paramString)
  {
    Intrinsics.checkNotNullParameter(paramString, "key");
    return this.regular.containsKey(paramString);
  }
  
  public final <T> T get(String paramString)
  {
    Intrinsics.checkNotNullParameter(paramString, "key");
    return (T)this.regular.get(paramString);
  }
  
  public final <T> MutableLiveData<T> getLiveData(String paramString)
  {
    Intrinsics.checkNotNullParameter(paramString, "key");
    return getLiveDataInternal(paramString, false, null);
  }
  
  public final <T> MutableLiveData<T> getLiveData(String paramString, T paramT)
  {
    Intrinsics.checkNotNullParameter(paramString, "key");
    return getLiveDataInternal(paramString, true, paramT);
  }
  
  public final <T> StateFlow<T> getStateFlow(String paramString, T paramT)
  {
    Intrinsics.checkNotNullParameter(paramString, "key");
    Map localMap = this.flows;
    Object localObject = localMap.get(paramString);
    if (localObject == null)
    {
      if (!this.regular.containsKey(paramString)) {
        this.regular.put(paramString, paramT);
      }
      paramT = StateFlowKt.MutableStateFlow(this.regular.get(paramString));
      this.flows.put(paramString, paramT);
      localMap.put(paramString, paramT);
      paramString = paramT;
    }
    else
    {
      paramString = (String)localObject;
    }
    return FlowKt.asStateFlow((MutableStateFlow)paramString);
  }
  
  public final Set<String> keys()
  {
    return SetsKt.plus(SetsKt.plus(this.regular.keySet(), (Iterable)this.savedStateProviders.keySet()), (Iterable)this.liveDatas.keySet());
  }
  
  public final <T> T remove(String paramString)
  {
    Intrinsics.checkNotNullParameter(paramString, "key");
    Object localObject = this.regular.remove(paramString);
    SavingStateLiveData localSavingStateLiveData = (SavingStateLiveData)this.liveDatas.remove(paramString);
    if (localSavingStateLiveData != null) {
      localSavingStateLiveData.detach();
    }
    this.flows.remove(paramString);
    return (T)localObject;
  }
  
  public final SavedStateRegistry.SavedStateProvider savedStateProvider()
  {
    return this.savedStateProvider;
  }
  
  public final <T> void set(String paramString, T paramT)
  {
    Intrinsics.checkNotNullParameter(paramString, "key");
    if (Companion.validateValue(paramT))
    {
      Object localObject = this.liveDatas.get(paramString);
      if ((localObject instanceof MutableLiveData)) {
        localObject = (MutableLiveData)localObject;
      } else {
        localObject = null;
      }
      if (localObject != null) {
        ((MutableLiveData)localObject).setValue(paramT);
      } else {
        this.regular.put(paramString, paramT);
      }
      paramString = (MutableStateFlow)this.flows.get(paramString);
      if (paramString != null) {
        paramString.setValue(paramT);
      }
      return;
    }
    paramString = new StringBuilder().append("Can't put value with type ");
    Intrinsics.checkNotNull(paramT);
    throw new IllegalArgumentException(paramT.getClass() + " into saved state");
  }
  
  public final void setSavedStateProvider(String paramString, SavedStateRegistry.SavedStateProvider paramSavedStateProvider)
  {
    Intrinsics.checkNotNullParameter(paramString, "key");
    Intrinsics.checkNotNullParameter(paramSavedStateProvider, "provider");
    this.savedStateProviders.put(paramString, paramSavedStateProvider);
  }
  
  @Metadata(d1={"\0006\n\002\030\002\n\002\020\000\n\002\b\002\n\002\020\021\n\002\030\002\n\002\b\002\n\002\020\016\n\002\b\002\n\002\030\002\n\000\n\002\030\002\n\002\b\002\n\002\020\013\n\002\b\002\b\003\030\0002\0020\001B\007\b\002¢\006\002\020\002J\034\020\n\032\0020\0132\b\020\f\032\004\030\0010\r2\b\020\016\032\004\030\0010\rH\007J\022\020\017\032\0020\0202\b\020\021\032\004\030\0010\001H\007R \020\003\032\022\022\016\022\f\022\006\b\001\022\0020\001\030\0010\0050\004X\004¢\006\004\n\002\020\006R\016\020\007\032\0020\bXT¢\006\002\n\000R\016\020\t\032\0020\bXT¢\006\002\n\000¨\006\022"}, d2={"Landroidx/lifecycle/SavedStateHandle$Companion;", "", "()V", "ACCEPTABLE_CLASSES", "", "Ljava/lang/Class;", "[Ljava/lang/Class;", "KEYS", "", "VALUES", "createHandle", "Landroidx/lifecycle/SavedStateHandle;", "restoredState", "Landroid/os/Bundle;", "defaultState", "validateValue", "", "value", "lifecycle-viewmodel-savedstate_release"}, k=1, mv={1, 6, 0}, xi=48)
  public static final class Companion
  {
    @JvmStatic
    public final SavedStateHandle createHandle(Bundle paramBundle1, Bundle paramBundle2)
    {
      Object localObject1;
      if (paramBundle1 == null)
      {
        if (paramBundle2 == null)
        {
          paramBundle1 = new SavedStateHandle();
        }
        else
        {
          localObject2 = (Map)new HashMap();
          paramBundle1 = paramBundle2.keySet().iterator();
          while (paramBundle1.hasNext())
          {
            localObject1 = (String)paramBundle1.next();
            Intrinsics.checkNotNullExpressionValue(localObject1, "key");
            ((Map)localObject2).put(localObject1, paramBundle2.get((String)localObject1));
          }
          paramBundle1 = new SavedStateHandle((Map)localObject2);
        }
        return paramBundle1;
      }
      paramBundle2 = paramBundle1.getParcelableArrayList("keys");
      Object localObject2 = paramBundle1.getParcelableArrayList("values");
      int i;
      if ((paramBundle2 != null) && (localObject2 != null) && (paramBundle2.size() == ((ArrayList)localObject2).size())) {
        i = 1;
      } else {
        i = 0;
      }
      if (i != 0)
      {
        paramBundle1 = (Map)new LinkedHashMap();
        i = 0;
        int j = paramBundle2.size();
        while (i < j)
        {
          localObject1 = paramBundle2.get(i);
          if (localObject1 != null)
          {
            paramBundle1.put((String)localObject1, ((ArrayList)localObject2).get(i));
            i++;
          }
          else
          {
            throw new NullPointerException("null cannot be cast to non-null type kotlin.String");
          }
        }
        return new SavedStateHandle(paramBundle1);
      }
      throw new IllegalStateException("Invalid bundle passed as restored state".toString());
    }
    
    public final boolean validateValue(Object paramObject)
    {
      if (paramObject == null) {
        return true;
      }
      for (Class localClass : SavedStateHandle.access$getACCEPTABLE_CLASSES$cp())
      {
        Intrinsics.checkNotNull(localClass);
        if (localClass.isInstance(paramObject)) {
          return true;
        }
      }
      return false;
    }
  }
  
  @Metadata(d1={"\000\"\n\002\030\002\n\000\n\002\030\002\n\000\n\002\030\002\n\000\n\002\020\016\n\002\b\004\n\002\020\002\n\002\b\003\b\000\030\000*\004\b\000\020\0012\b\022\004\022\002H\0010\002B!\b\026\022\b\020\003\032\004\030\0010\004\022\006\020\005\032\0020\006\022\006\020\007\032\0028\000¢\006\002\020\bB\031\b\026\022\b\020\003\032\004\030\0010\004\022\006\020\005\032\0020\006¢\006\002\020\tJ\006\020\n\032\0020\013J\025\020\f\032\0020\0132\006\020\007\032\0028\000H\026¢\006\002\020\rR\020\020\003\032\004\030\0010\004X\016¢\006\002\n\000R\016\020\005\032\0020\006X\016¢\006\002\n\000¨\006\016"}, d2={"Landroidx/lifecycle/SavedStateHandle$SavingStateLiveData;", "T", "Landroidx/lifecycle/MutableLiveData;", "handle", "Landroidx/lifecycle/SavedStateHandle;", "key", "", "value", "(Landroidx/lifecycle/SavedStateHandle;Ljava/lang/String;Ljava/lang/Object;)V", "(Landroidx/lifecycle/SavedStateHandle;Ljava/lang/String;)V", "detach", "", "setValue", "(Ljava/lang/Object;)V", "lifecycle-viewmodel-savedstate_release"}, k=1, mv={1, 6, 0}, xi=48)
  public static final class SavingStateLiveData<T>
    extends MutableLiveData<T>
  {
    private SavedStateHandle handle;
    private String key;
    
    public SavingStateLiveData(SavedStateHandle paramSavedStateHandle, String paramString)
    {
      this.key = paramString;
      this.handle = paramSavedStateHandle;
    }
    
    public SavingStateLiveData(SavedStateHandle paramSavedStateHandle, String paramString, T paramT)
    {
      super();
      this.key = paramString;
      this.handle = paramSavedStateHandle;
    }
    
    public final void detach()
    {
      this.handle = null;
    }
    
    public void setValue(T paramT)
    {
      Object localObject = this.handle;
      if (localObject != null)
      {
        SavedStateHandle.access$getRegular$p((SavedStateHandle)localObject).put(this.key, paramT);
        localObject = (MutableStateFlow)SavedStateHandle.access$getFlows$p((SavedStateHandle)localObject).get(this.key);
        if (localObject != null) {
          ((MutableStateFlow)localObject).setValue(paramT);
        }
      }
      super.setValue(paramT);
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/lifecycle/SavedStateHandle.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */