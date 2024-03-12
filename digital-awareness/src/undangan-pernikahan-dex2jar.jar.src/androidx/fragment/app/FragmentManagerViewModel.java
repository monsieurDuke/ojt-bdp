package androidx.fragment.app;

import android.util.Log;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProvider.Factory;
import androidx.lifecycle.ViewModelStore;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import mt.Log229316;
import mt.Log5ECF72;
import mt.LogE84000;

final class FragmentManagerViewModel
  extends ViewModel
{
  private static final ViewModelProvider.Factory FACTORY = new ViewModelProvider.Factory()
  {
    public <T extends ViewModel> T create(Class<T> paramAnonymousClass)
    {
      return new FragmentManagerViewModel(true);
    }
  };
  private static final String TAG = "FragmentManager";
  private final HashMap<String, FragmentManagerViewModel> mChildNonConfigs = new HashMap();
  private boolean mHasBeenCleared = false;
  private boolean mHasSavedSnapshot = false;
  private boolean mIsStateSaved = false;
  private final HashMap<String, Fragment> mRetainedFragments = new HashMap();
  private final boolean mStateAutomaticallySaved;
  private final HashMap<String, ViewModelStore> mViewModelStores = new HashMap();
  
  FragmentManagerViewModel(boolean paramBoolean)
  {
    this.mStateAutomaticallySaved = paramBoolean;
  }
  
  static FragmentManagerViewModel getInstance(ViewModelStore paramViewModelStore)
  {
    return (FragmentManagerViewModel)new ViewModelProvider(paramViewModelStore, FACTORY).get(FragmentManagerViewModel.class);
  }
  
  void addRetainedFragment(Fragment paramFragment)
  {
    if (this.mIsStateSaved)
    {
      if (FragmentManager.isLoggingEnabled(2)) {
        Log.v("FragmentManager", "Ignoring addRetainedFragment as the state is already saved");
      }
      return;
    }
    if (this.mRetainedFragments.containsKey(paramFragment.mWho)) {
      return;
    }
    this.mRetainedFragments.put(paramFragment.mWho, paramFragment);
    if (FragmentManager.isLoggingEnabled(2)) {
      Log.v("FragmentManager", "Updating retained Fragments: Added " + paramFragment);
    }
  }
  
  void clearNonConfigState(Fragment paramFragment)
  {
    if (FragmentManager.isLoggingEnabled(3)) {
      Log.d("FragmentManager", "Clearing non-config state for " + paramFragment);
    }
    Object localObject = (FragmentManagerViewModel)this.mChildNonConfigs.get(paramFragment.mWho);
    if (localObject != null)
    {
      ((FragmentManagerViewModel)localObject).onCleared();
      this.mChildNonConfigs.remove(paramFragment.mWho);
    }
    localObject = (ViewModelStore)this.mViewModelStores.get(paramFragment.mWho);
    if (localObject != null)
    {
      ((ViewModelStore)localObject).clear();
      this.mViewModelStores.remove(paramFragment.mWho);
    }
  }
  
  public boolean equals(Object paramObject)
  {
    boolean bool = true;
    if (this == paramObject) {
      return true;
    }
    if ((paramObject != null) && (getClass() == paramObject.getClass()))
    {
      paramObject = (FragmentManagerViewModel)paramObject;
      if ((!this.mRetainedFragments.equals(((FragmentManagerViewModel)paramObject).mRetainedFragments)) || (!this.mChildNonConfigs.equals(((FragmentManagerViewModel)paramObject).mChildNonConfigs)) || (!this.mViewModelStores.equals(((FragmentManagerViewModel)paramObject).mViewModelStores))) {
        bool = false;
      }
      return bool;
    }
    return false;
  }
  
  Fragment findRetainedFragmentByWho(String paramString)
  {
    return (Fragment)this.mRetainedFragments.get(paramString);
  }
  
  FragmentManagerViewModel getChildNonConfig(Fragment paramFragment)
  {
    FragmentManagerViewModel localFragmentManagerViewModel2 = (FragmentManagerViewModel)this.mChildNonConfigs.get(paramFragment.mWho);
    FragmentManagerViewModel localFragmentManagerViewModel1 = localFragmentManagerViewModel2;
    if (localFragmentManagerViewModel2 == null)
    {
      localFragmentManagerViewModel1 = new FragmentManagerViewModel(this.mStateAutomaticallySaved);
      this.mChildNonConfigs.put(paramFragment.mWho, localFragmentManagerViewModel1);
    }
    return localFragmentManagerViewModel1;
  }
  
  Collection<Fragment> getRetainedFragments()
  {
    return new ArrayList(this.mRetainedFragments.values());
  }
  
  @Deprecated
  FragmentManagerNonConfig getSnapshot()
  {
    if ((this.mRetainedFragments.isEmpty()) && (this.mChildNonConfigs.isEmpty()) && (this.mViewModelStores.isEmpty())) {
      return null;
    }
    HashMap localHashMap = new HashMap();
    Iterator localIterator = this.mChildNonConfigs.entrySet().iterator();
    while (localIterator.hasNext())
    {
      Map.Entry localEntry = (Map.Entry)localIterator.next();
      FragmentManagerNonConfig localFragmentManagerNonConfig = ((FragmentManagerViewModel)localEntry.getValue()).getSnapshot();
      if (localFragmentManagerNonConfig != null) {
        localHashMap.put(localEntry.getKey(), localFragmentManagerNonConfig);
      }
    }
    this.mHasSavedSnapshot = true;
    if ((this.mRetainedFragments.isEmpty()) && (localHashMap.isEmpty()) && (this.mViewModelStores.isEmpty())) {
      return null;
    }
    return new FragmentManagerNonConfig(new ArrayList(this.mRetainedFragments.values()), localHashMap, new HashMap(this.mViewModelStores));
  }
  
  ViewModelStore getViewModelStore(Fragment paramFragment)
  {
    ViewModelStore localViewModelStore2 = (ViewModelStore)this.mViewModelStores.get(paramFragment.mWho);
    ViewModelStore localViewModelStore1 = localViewModelStore2;
    if (localViewModelStore2 == null)
    {
      localViewModelStore1 = new ViewModelStore();
      this.mViewModelStores.put(paramFragment.mWho, localViewModelStore1);
    }
    return localViewModelStore1;
  }
  
  public int hashCode()
  {
    return (this.mRetainedFragments.hashCode() * 31 + this.mChildNonConfigs.hashCode()) * 31 + this.mViewModelStores.hashCode();
  }
  
  boolean isCleared()
  {
    return this.mHasBeenCleared;
  }
  
  protected void onCleared()
  {
    if (FragmentManager.isLoggingEnabled(3)) {
      Log.d("FragmentManager", "onCleared called for " + this);
    }
    this.mHasBeenCleared = true;
  }
  
  void removeRetainedFragment(Fragment paramFragment)
  {
    if (this.mIsStateSaved)
    {
      if (FragmentManager.isLoggingEnabled(2)) {
        Log.v("FragmentManager", "Ignoring removeRetainedFragment as the state is already saved");
      }
      return;
    }
    int i;
    if (this.mRetainedFragments.remove(paramFragment.mWho) != null) {
      i = 1;
    } else {
      i = 0;
    }
    if ((i != 0) && (FragmentManager.isLoggingEnabled(2))) {
      Log.v("FragmentManager", "Updating retained Fragments: Removed " + paramFragment);
    }
  }
  
  @Deprecated
  void restoreFromSnapshot(FragmentManagerNonConfig paramFragmentManagerNonConfig)
  {
    this.mRetainedFragments.clear();
    this.mChildNonConfigs.clear();
    this.mViewModelStores.clear();
    if (paramFragmentManagerNonConfig != null)
    {
      Object localObject1 = paramFragmentManagerNonConfig.getFragments();
      Object localObject2;
      if (localObject1 != null)
      {
        localObject1 = ((Collection)localObject1).iterator();
        while (((Iterator)localObject1).hasNext())
        {
          localObject2 = (Fragment)((Iterator)localObject1).next();
          if (localObject2 != null) {
            this.mRetainedFragments.put(((Fragment)localObject2).mWho, localObject2);
          }
        }
      }
      localObject1 = paramFragmentManagerNonConfig.getChildNonConfigs();
      if (localObject1 != null)
      {
        localObject1 = ((Map)localObject1).entrySet().iterator();
        while (((Iterator)localObject1).hasNext())
        {
          localObject2 = (Map.Entry)((Iterator)localObject1).next();
          FragmentManagerViewModel localFragmentManagerViewModel = new FragmentManagerViewModel(this.mStateAutomaticallySaved);
          localFragmentManagerViewModel.restoreFromSnapshot((FragmentManagerNonConfig)((Map.Entry)localObject2).getValue());
          this.mChildNonConfigs.put(((Map.Entry)localObject2).getKey(), localFragmentManagerViewModel);
        }
      }
      paramFragmentManagerNonConfig = paramFragmentManagerNonConfig.getViewModelStores();
      if (paramFragmentManagerNonConfig != null) {
        this.mViewModelStores.putAll(paramFragmentManagerNonConfig);
      }
    }
    this.mHasSavedSnapshot = false;
  }
  
  void setIsStateSaved(boolean paramBoolean)
  {
    this.mIsStateSaved = paramBoolean;
  }
  
  boolean shouldDestroy(Fragment paramFragment)
  {
    if (!this.mRetainedFragments.containsKey(paramFragment.mWho)) {
      return true;
    }
    if (this.mStateAutomaticallySaved) {
      return this.mHasBeenCleared;
    }
    return this.mHasSavedSnapshot ^ true;
  }
  
  public String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder("FragmentManagerViewModel{");
    Object localObject = Integer.toHexString(System.identityHashCode(this));
    Log5ECF72.a(localObject);
    LogE84000.a(localObject);
    Log229316.a(localObject);
    localStringBuilder.append((String)localObject);
    localStringBuilder.append("} Fragments (");
    localObject = this.mRetainedFragments.values().iterator();
    while (((Iterator)localObject).hasNext())
    {
      localStringBuilder.append(((Iterator)localObject).next());
      if (((Iterator)localObject).hasNext()) {
        localStringBuilder.append(", ");
      }
    }
    localStringBuilder.append(") Child Non Config (");
    localObject = this.mChildNonConfigs.keySet().iterator();
    while (((Iterator)localObject).hasNext())
    {
      localStringBuilder.append((String)((Iterator)localObject).next());
      if (((Iterator)localObject).hasNext()) {
        localStringBuilder.append(", ");
      }
    }
    localStringBuilder.append(") ViewModelStores (");
    localObject = this.mViewModelStores.keySet().iterator();
    while (((Iterator)localObject).hasNext())
    {
      localStringBuilder.append((String)((Iterator)localObject).next());
      if (((Iterator)localObject).hasNext()) {
        localStringBuilder.append(", ");
      }
    }
    localStringBuilder.append(')');
    return localStringBuilder.toString();
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/fragment/app/FragmentManagerViewModel.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */