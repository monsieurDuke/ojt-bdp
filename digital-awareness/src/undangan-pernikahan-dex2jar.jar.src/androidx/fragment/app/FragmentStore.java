package androidx.fragment.app;

import android.util.Log;
import android.view.ViewGroup;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

class FragmentStore
{
  private static final String TAG = "FragmentManager";
  private final HashMap<String, FragmentStateManager> mActive = new HashMap();
  private final ArrayList<Fragment> mAdded = new ArrayList();
  private FragmentManagerViewModel mNonConfig;
  
  void addFragment(Fragment paramFragment)
  {
    if (!this.mAdded.contains(paramFragment)) {
      synchronized (this.mAdded)
      {
        this.mAdded.add(paramFragment);
        paramFragment.mAdded = true;
        return;
      }
    }
    throw new IllegalStateException("Fragment already added: " + paramFragment);
  }
  
  void burpActive()
  {
    this.mActive.values().removeAll(Collections.singleton(null));
  }
  
  boolean containsActiveFragment(String paramString)
  {
    boolean bool;
    if (this.mActive.get(paramString) != null) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  void dispatchStateChange(int paramInt)
  {
    Iterator localIterator = this.mActive.values().iterator();
    while (localIterator.hasNext())
    {
      FragmentStateManager localFragmentStateManager = (FragmentStateManager)localIterator.next();
      if (localFragmentStateManager != null) {
        localFragmentStateManager.setFragmentManagerState(paramInt);
      }
    }
  }
  
  void dump(String paramString, FileDescriptor paramFileDescriptor, PrintWriter paramPrintWriter, String[] paramArrayOfString)
  {
    String str = paramString + "    ";
    if (!this.mActive.isEmpty())
    {
      paramPrintWriter.print(paramString);
      paramPrintWriter.println("Active Fragments:");
      Iterator localIterator = this.mActive.values().iterator();
      while (localIterator.hasNext())
      {
        Object localObject = (FragmentStateManager)localIterator.next();
        paramPrintWriter.print(paramString);
        if (localObject != null)
        {
          localObject = ((FragmentStateManager)localObject).getFragment();
          paramPrintWriter.println(localObject);
          ((Fragment)localObject).dump(str, paramFileDescriptor, paramPrintWriter, paramArrayOfString);
        }
        else
        {
          paramPrintWriter.println("null");
        }
      }
    }
    int j = this.mAdded.size();
    if (j > 0)
    {
      paramPrintWriter.print(paramString);
      paramPrintWriter.println("Added Fragments:");
      for (int i = 0; i < j; i++)
      {
        paramFileDescriptor = (Fragment)this.mAdded.get(i);
        paramPrintWriter.print(paramString);
        paramPrintWriter.print("  #");
        paramPrintWriter.print(i);
        paramPrintWriter.print(": ");
        paramPrintWriter.println(paramFileDescriptor.toString());
      }
    }
  }
  
  Fragment findActiveFragment(String paramString)
  {
    paramString = (FragmentStateManager)this.mActive.get(paramString);
    if (paramString != null) {
      return paramString.getFragment();
    }
    return null;
  }
  
  Fragment findFragmentById(int paramInt)
  {
    for (int i = this.mAdded.size() - 1; i >= 0; i--)
    {
      localObject1 = (Fragment)this.mAdded.get(i);
      if ((localObject1 != null) && (((Fragment)localObject1).mFragmentId == paramInt)) {
        return (Fragment)localObject1;
      }
    }
    Object localObject1 = this.mActive.values().iterator();
    while (((Iterator)localObject1).hasNext())
    {
      Object localObject2 = (FragmentStateManager)((Iterator)localObject1).next();
      if (localObject2 != null)
      {
        localObject2 = ((FragmentStateManager)localObject2).getFragment();
        if (((Fragment)localObject2).mFragmentId == paramInt) {
          return (Fragment)localObject2;
        }
      }
    }
    return null;
  }
  
  Fragment findFragmentByTag(String paramString)
  {
    Object localObject1;
    if (paramString != null) {
      for (int i = this.mAdded.size() - 1; i >= 0; i--)
      {
        localObject1 = (Fragment)this.mAdded.get(i);
        if ((localObject1 != null) && (paramString.equals(((Fragment)localObject1).mTag))) {
          return (Fragment)localObject1;
        }
      }
    }
    if (paramString != null)
    {
      localObject1 = this.mActive.values().iterator();
      while (((Iterator)localObject1).hasNext())
      {
        Object localObject2 = (FragmentStateManager)((Iterator)localObject1).next();
        if (localObject2 != null)
        {
          localObject2 = ((FragmentStateManager)localObject2).getFragment();
          if (paramString.equals(((Fragment)localObject2).mTag)) {
            return (Fragment)localObject2;
          }
        }
      }
    }
    return null;
  }
  
  Fragment findFragmentByWho(String paramString)
  {
    Iterator localIterator = this.mActive.values().iterator();
    while (localIterator.hasNext())
    {
      Object localObject = (FragmentStateManager)localIterator.next();
      if (localObject != null)
      {
        localObject = ((FragmentStateManager)localObject).getFragment().findFragmentByWho(paramString);
        if (localObject != null) {
          return (Fragment)localObject;
        }
      }
    }
    return null;
  }
  
  int findFragmentIndexInContainer(Fragment paramFragment)
  {
    ViewGroup localViewGroup = paramFragment.mContainer;
    if (localViewGroup == null) {
      return -1;
    }
    int j = this.mAdded.indexOf(paramFragment);
    for (int i = j - 1; i >= 0; i--)
    {
      paramFragment = (Fragment)this.mAdded.get(i);
      if ((paramFragment.mContainer == localViewGroup) && (paramFragment.mView != null)) {
        return localViewGroup.indexOfChild(paramFragment.mView) + 1;
      }
    }
    for (i = j + 1; i < this.mAdded.size(); i++)
    {
      paramFragment = (Fragment)this.mAdded.get(i);
      if ((paramFragment.mContainer == localViewGroup) && (paramFragment.mView != null)) {
        return localViewGroup.indexOfChild(paramFragment.mView);
      }
    }
    return -1;
  }
  
  int getActiveFragmentCount()
  {
    return this.mActive.size();
  }
  
  List<FragmentStateManager> getActiveFragmentStateManagers()
  {
    ArrayList localArrayList = new ArrayList();
    Iterator localIterator = this.mActive.values().iterator();
    while (localIterator.hasNext())
    {
      FragmentStateManager localFragmentStateManager = (FragmentStateManager)localIterator.next();
      if (localFragmentStateManager != null) {
        localArrayList.add(localFragmentStateManager);
      }
    }
    return localArrayList;
  }
  
  List<Fragment> getActiveFragments()
  {
    ArrayList localArrayList = new ArrayList();
    Iterator localIterator = this.mActive.values().iterator();
    while (localIterator.hasNext())
    {
      FragmentStateManager localFragmentStateManager = (FragmentStateManager)localIterator.next();
      if (localFragmentStateManager != null) {
        localArrayList.add(localFragmentStateManager.getFragment());
      } else {
        localArrayList.add(null);
      }
    }
    return localArrayList;
  }
  
  FragmentStateManager getFragmentStateManager(String paramString)
  {
    return (FragmentStateManager)this.mActive.get(paramString);
  }
  
  List<Fragment> getFragments()
  {
    if (this.mAdded.isEmpty()) {
      return Collections.emptyList();
    }
    synchronized (this.mAdded)
    {
      ArrayList localArrayList2 = new java/util/ArrayList;
      localArrayList2.<init>(this.mAdded);
      return localArrayList2;
    }
  }
  
  FragmentManagerViewModel getNonConfig()
  {
    return this.mNonConfig;
  }
  
  void makeActive(FragmentStateManager paramFragmentStateManager)
  {
    Fragment localFragment = paramFragmentStateManager.getFragment();
    if (containsActiveFragment(localFragment.mWho)) {
      return;
    }
    this.mActive.put(localFragment.mWho, paramFragmentStateManager);
    if (localFragment.mRetainInstanceChangedWhileDetached)
    {
      if (localFragment.mRetainInstance) {
        this.mNonConfig.addRetainedFragment(localFragment);
      } else {
        this.mNonConfig.removeRetainedFragment(localFragment);
      }
      localFragment.mRetainInstanceChangedWhileDetached = false;
    }
    if (FragmentManager.isLoggingEnabled(2)) {
      Log.v("FragmentManager", "Added fragment to active set " + localFragment);
    }
  }
  
  void makeInactive(FragmentStateManager paramFragmentStateManager)
  {
    paramFragmentStateManager = paramFragmentStateManager.getFragment();
    if (paramFragmentStateManager.mRetainInstance) {
      this.mNonConfig.removeRetainedFragment(paramFragmentStateManager);
    }
    if ((FragmentStateManager)this.mActive.put(paramFragmentStateManager.mWho, null) == null) {
      return;
    }
    if (FragmentManager.isLoggingEnabled(2)) {
      Log.v("FragmentManager", "Removed fragment from active set " + paramFragmentStateManager);
    }
  }
  
  void moveToExpectedState()
  {
    Object localObject1 = this.mAdded.iterator();
    Object localObject2;
    while (((Iterator)localObject1).hasNext())
    {
      localObject2 = (Fragment)((Iterator)localObject1).next();
      localObject2 = (FragmentStateManager)this.mActive.get(((Fragment)localObject2).mWho);
      if (localObject2 != null) {
        ((FragmentStateManager)localObject2).moveToExpectedState();
      }
    }
    Iterator localIterator = this.mActive.values().iterator();
    while (localIterator.hasNext())
    {
      localObject1 = (FragmentStateManager)localIterator.next();
      if (localObject1 != null)
      {
        ((FragmentStateManager)localObject1).moveToExpectedState();
        localObject2 = ((FragmentStateManager)localObject1).getFragment();
        int i;
        if ((((Fragment)localObject2).mRemoving) && (!((Fragment)localObject2).isInBackStack())) {
          i = 1;
        } else {
          i = 0;
        }
        if (i != 0) {
          makeInactive((FragmentStateManager)localObject1);
        }
      }
    }
  }
  
  void removeFragment(Fragment paramFragment)
  {
    synchronized (this.mAdded)
    {
      this.mAdded.remove(paramFragment);
      paramFragment.mAdded = false;
      return;
    }
  }
  
  void resetActiveFragments()
  {
    this.mActive.clear();
  }
  
  void restoreAddedFragments(List<String> paramList)
  {
    this.mAdded.clear();
    if (paramList != null)
    {
      Iterator localIterator = paramList.iterator();
      while (localIterator.hasNext())
      {
        paramList = (String)localIterator.next();
        Fragment localFragment = findActiveFragment(paramList);
        if (localFragment != null)
        {
          if (FragmentManager.isLoggingEnabled(2)) {
            Log.v("FragmentManager", "restoreSaveState: added (" + paramList + "): " + localFragment);
          }
          addFragment(localFragment);
        }
        else
        {
          throw new IllegalStateException("No instantiated fragment for (" + paramList + ")");
        }
      }
    }
  }
  
  ArrayList<FragmentState> saveActiveFragments()
  {
    ArrayList localArrayList = new ArrayList(this.mActive.size());
    Iterator localIterator = this.mActive.values().iterator();
    while (localIterator.hasNext())
    {
      Object localObject = (FragmentStateManager)localIterator.next();
      if (localObject != null)
      {
        Fragment localFragment = ((FragmentStateManager)localObject).getFragment();
        localObject = ((FragmentStateManager)localObject).saveState();
        localArrayList.add(localObject);
        if (FragmentManager.isLoggingEnabled(2)) {
          Log.v("FragmentManager", "Saved state of " + localFragment + ": " + ((FragmentState)localObject).mSavedFragmentState);
        }
      }
    }
    return localArrayList;
  }
  
  ArrayList<String> saveAddedFragments()
  {
    synchronized (this.mAdded)
    {
      if (this.mAdded.isEmpty()) {
        return null;
      }
      ArrayList localArrayList2 = new java/util/ArrayList;
      localArrayList2.<init>(this.mAdded.size());
      Iterator localIterator = this.mAdded.iterator();
      while (localIterator.hasNext())
      {
        Fragment localFragment = (Fragment)localIterator.next();
        localArrayList2.add(localFragment.mWho);
        if (FragmentManager.isLoggingEnabled(2))
        {
          StringBuilder localStringBuilder = new java/lang/StringBuilder;
          localStringBuilder.<init>();
          Log.v("FragmentManager", "saveAllState: adding fragment (" + localFragment.mWho + "): " + localFragment);
        }
      }
      return localArrayList2;
    }
  }
  
  void setNonConfig(FragmentManagerViewModel paramFragmentManagerViewModel)
  {
    this.mNonConfig = paramFragmentManagerViewModel;
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/fragment/app/FragmentStore.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */