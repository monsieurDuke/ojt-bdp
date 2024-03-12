package androidx.fragment.app;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;

class FragmentLifecycleCallbacksDispatcher
{
  private final FragmentManager mFragmentManager;
  private final CopyOnWriteArrayList<FragmentLifecycleCallbacksHolder> mLifecycleCallbacks = new CopyOnWriteArrayList();
  
  FragmentLifecycleCallbacksDispatcher(FragmentManager paramFragmentManager)
  {
    this.mFragmentManager = paramFragmentManager;
  }
  
  void dispatchOnFragmentActivityCreated(Fragment paramFragment, Bundle paramBundle, boolean paramBoolean)
  {
    Object localObject = this.mFragmentManager.getParent();
    if (localObject != null) {
      ((Fragment)localObject).getParentFragmentManager().getLifecycleCallbacksDispatcher().dispatchOnFragmentActivityCreated(paramFragment, paramBundle, true);
    }
    localObject = this.mLifecycleCallbacks.iterator();
    while (((Iterator)localObject).hasNext())
    {
      FragmentLifecycleCallbacksHolder localFragmentLifecycleCallbacksHolder = (FragmentLifecycleCallbacksHolder)((Iterator)localObject).next();
      if ((!paramBoolean) || (localFragmentLifecycleCallbacksHolder.mRecursive)) {
        localFragmentLifecycleCallbacksHolder.mCallback.onFragmentActivityCreated(this.mFragmentManager, paramFragment, paramBundle);
      }
    }
  }
  
  void dispatchOnFragmentAttached(Fragment paramFragment, boolean paramBoolean)
  {
    Context localContext = this.mFragmentManager.getHost().getContext();
    Object localObject = this.mFragmentManager.getParent();
    if (localObject != null) {
      ((Fragment)localObject).getParentFragmentManager().getLifecycleCallbacksDispatcher().dispatchOnFragmentAttached(paramFragment, true);
    }
    localObject = this.mLifecycleCallbacks.iterator();
    while (((Iterator)localObject).hasNext())
    {
      FragmentLifecycleCallbacksHolder localFragmentLifecycleCallbacksHolder = (FragmentLifecycleCallbacksHolder)((Iterator)localObject).next();
      if ((!paramBoolean) || (localFragmentLifecycleCallbacksHolder.mRecursive)) {
        localFragmentLifecycleCallbacksHolder.mCallback.onFragmentAttached(this.mFragmentManager, paramFragment, localContext);
      }
    }
  }
  
  void dispatchOnFragmentCreated(Fragment paramFragment, Bundle paramBundle, boolean paramBoolean)
  {
    Object localObject = this.mFragmentManager.getParent();
    if (localObject != null) {
      ((Fragment)localObject).getParentFragmentManager().getLifecycleCallbacksDispatcher().dispatchOnFragmentCreated(paramFragment, paramBundle, true);
    }
    Iterator localIterator = this.mLifecycleCallbacks.iterator();
    while (localIterator.hasNext())
    {
      localObject = (FragmentLifecycleCallbacksHolder)localIterator.next();
      if ((!paramBoolean) || (((FragmentLifecycleCallbacksHolder)localObject).mRecursive)) {
        ((FragmentLifecycleCallbacksHolder)localObject).mCallback.onFragmentCreated(this.mFragmentManager, paramFragment, paramBundle);
      }
    }
  }
  
  void dispatchOnFragmentDestroyed(Fragment paramFragment, boolean paramBoolean)
  {
    Object localObject = this.mFragmentManager.getParent();
    if (localObject != null) {
      ((Fragment)localObject).getParentFragmentManager().getLifecycleCallbacksDispatcher().dispatchOnFragmentDestroyed(paramFragment, true);
    }
    localObject = this.mLifecycleCallbacks.iterator();
    while (((Iterator)localObject).hasNext())
    {
      FragmentLifecycleCallbacksHolder localFragmentLifecycleCallbacksHolder = (FragmentLifecycleCallbacksHolder)((Iterator)localObject).next();
      if ((!paramBoolean) || (localFragmentLifecycleCallbacksHolder.mRecursive)) {
        localFragmentLifecycleCallbacksHolder.mCallback.onFragmentDestroyed(this.mFragmentManager, paramFragment);
      }
    }
  }
  
  void dispatchOnFragmentDetached(Fragment paramFragment, boolean paramBoolean)
  {
    Object localObject = this.mFragmentManager.getParent();
    if (localObject != null) {
      ((Fragment)localObject).getParentFragmentManager().getLifecycleCallbacksDispatcher().dispatchOnFragmentDetached(paramFragment, true);
    }
    localObject = this.mLifecycleCallbacks.iterator();
    while (((Iterator)localObject).hasNext())
    {
      FragmentLifecycleCallbacksHolder localFragmentLifecycleCallbacksHolder = (FragmentLifecycleCallbacksHolder)((Iterator)localObject).next();
      if ((!paramBoolean) || (localFragmentLifecycleCallbacksHolder.mRecursive)) {
        localFragmentLifecycleCallbacksHolder.mCallback.onFragmentDetached(this.mFragmentManager, paramFragment);
      }
    }
  }
  
  void dispatchOnFragmentPaused(Fragment paramFragment, boolean paramBoolean)
  {
    Object localObject = this.mFragmentManager.getParent();
    if (localObject != null) {
      ((Fragment)localObject).getParentFragmentManager().getLifecycleCallbacksDispatcher().dispatchOnFragmentPaused(paramFragment, true);
    }
    localObject = this.mLifecycleCallbacks.iterator();
    while (((Iterator)localObject).hasNext())
    {
      FragmentLifecycleCallbacksHolder localFragmentLifecycleCallbacksHolder = (FragmentLifecycleCallbacksHolder)((Iterator)localObject).next();
      if ((!paramBoolean) || (localFragmentLifecycleCallbacksHolder.mRecursive)) {
        localFragmentLifecycleCallbacksHolder.mCallback.onFragmentPaused(this.mFragmentManager, paramFragment);
      }
    }
  }
  
  void dispatchOnFragmentPreAttached(Fragment paramFragment, boolean paramBoolean)
  {
    Context localContext = this.mFragmentManager.getHost().getContext();
    Object localObject = this.mFragmentManager.getParent();
    if (localObject != null) {
      ((Fragment)localObject).getParentFragmentManager().getLifecycleCallbacksDispatcher().dispatchOnFragmentPreAttached(paramFragment, true);
    }
    localObject = this.mLifecycleCallbacks.iterator();
    while (((Iterator)localObject).hasNext())
    {
      FragmentLifecycleCallbacksHolder localFragmentLifecycleCallbacksHolder = (FragmentLifecycleCallbacksHolder)((Iterator)localObject).next();
      if ((!paramBoolean) || (localFragmentLifecycleCallbacksHolder.mRecursive)) {
        localFragmentLifecycleCallbacksHolder.mCallback.onFragmentPreAttached(this.mFragmentManager, paramFragment, localContext);
      }
    }
  }
  
  void dispatchOnFragmentPreCreated(Fragment paramFragment, Bundle paramBundle, boolean paramBoolean)
  {
    Object localObject = this.mFragmentManager.getParent();
    if (localObject != null) {
      ((Fragment)localObject).getParentFragmentManager().getLifecycleCallbacksDispatcher().dispatchOnFragmentPreCreated(paramFragment, paramBundle, true);
    }
    Iterator localIterator = this.mLifecycleCallbacks.iterator();
    while (localIterator.hasNext())
    {
      localObject = (FragmentLifecycleCallbacksHolder)localIterator.next();
      if ((!paramBoolean) || (((FragmentLifecycleCallbacksHolder)localObject).mRecursive)) {
        ((FragmentLifecycleCallbacksHolder)localObject).mCallback.onFragmentPreCreated(this.mFragmentManager, paramFragment, paramBundle);
      }
    }
  }
  
  void dispatchOnFragmentResumed(Fragment paramFragment, boolean paramBoolean)
  {
    Object localObject = this.mFragmentManager.getParent();
    if (localObject != null) {
      ((Fragment)localObject).getParentFragmentManager().getLifecycleCallbacksDispatcher().dispatchOnFragmentResumed(paramFragment, true);
    }
    localObject = this.mLifecycleCallbacks.iterator();
    while (((Iterator)localObject).hasNext())
    {
      FragmentLifecycleCallbacksHolder localFragmentLifecycleCallbacksHolder = (FragmentLifecycleCallbacksHolder)((Iterator)localObject).next();
      if ((!paramBoolean) || (localFragmentLifecycleCallbacksHolder.mRecursive)) {
        localFragmentLifecycleCallbacksHolder.mCallback.onFragmentResumed(this.mFragmentManager, paramFragment);
      }
    }
  }
  
  void dispatchOnFragmentSaveInstanceState(Fragment paramFragment, Bundle paramBundle, boolean paramBoolean)
  {
    Object localObject = this.mFragmentManager.getParent();
    if (localObject != null) {
      ((Fragment)localObject).getParentFragmentManager().getLifecycleCallbacksDispatcher().dispatchOnFragmentSaveInstanceState(paramFragment, paramBundle, true);
    }
    localObject = this.mLifecycleCallbacks.iterator();
    while (((Iterator)localObject).hasNext())
    {
      FragmentLifecycleCallbacksHolder localFragmentLifecycleCallbacksHolder = (FragmentLifecycleCallbacksHolder)((Iterator)localObject).next();
      if ((!paramBoolean) || (localFragmentLifecycleCallbacksHolder.mRecursive)) {
        localFragmentLifecycleCallbacksHolder.mCallback.onFragmentSaveInstanceState(this.mFragmentManager, paramFragment, paramBundle);
      }
    }
  }
  
  void dispatchOnFragmentStarted(Fragment paramFragment, boolean paramBoolean)
  {
    Object localObject = this.mFragmentManager.getParent();
    if (localObject != null) {
      ((Fragment)localObject).getParentFragmentManager().getLifecycleCallbacksDispatcher().dispatchOnFragmentStarted(paramFragment, true);
    }
    localObject = this.mLifecycleCallbacks.iterator();
    while (((Iterator)localObject).hasNext())
    {
      FragmentLifecycleCallbacksHolder localFragmentLifecycleCallbacksHolder = (FragmentLifecycleCallbacksHolder)((Iterator)localObject).next();
      if ((!paramBoolean) || (localFragmentLifecycleCallbacksHolder.mRecursive)) {
        localFragmentLifecycleCallbacksHolder.mCallback.onFragmentStarted(this.mFragmentManager, paramFragment);
      }
    }
  }
  
  void dispatchOnFragmentStopped(Fragment paramFragment, boolean paramBoolean)
  {
    Object localObject = this.mFragmentManager.getParent();
    if (localObject != null) {
      ((Fragment)localObject).getParentFragmentManager().getLifecycleCallbacksDispatcher().dispatchOnFragmentStopped(paramFragment, true);
    }
    Iterator localIterator = this.mLifecycleCallbacks.iterator();
    while (localIterator.hasNext())
    {
      localObject = (FragmentLifecycleCallbacksHolder)localIterator.next();
      if ((!paramBoolean) || (((FragmentLifecycleCallbacksHolder)localObject).mRecursive)) {
        ((FragmentLifecycleCallbacksHolder)localObject).mCallback.onFragmentStopped(this.mFragmentManager, paramFragment);
      }
    }
  }
  
  void dispatchOnFragmentViewCreated(Fragment paramFragment, View paramView, Bundle paramBundle, boolean paramBoolean)
  {
    Object localObject = this.mFragmentManager.getParent();
    if (localObject != null) {
      ((Fragment)localObject).getParentFragmentManager().getLifecycleCallbacksDispatcher().dispatchOnFragmentViewCreated(paramFragment, paramView, paramBundle, true);
    }
    localObject = this.mLifecycleCallbacks.iterator();
    while (((Iterator)localObject).hasNext())
    {
      FragmentLifecycleCallbacksHolder localFragmentLifecycleCallbacksHolder = (FragmentLifecycleCallbacksHolder)((Iterator)localObject).next();
      if ((!paramBoolean) || (localFragmentLifecycleCallbacksHolder.mRecursive)) {
        localFragmentLifecycleCallbacksHolder.mCallback.onFragmentViewCreated(this.mFragmentManager, paramFragment, paramView, paramBundle);
      }
    }
  }
  
  void dispatchOnFragmentViewDestroyed(Fragment paramFragment, boolean paramBoolean)
  {
    Object localObject = this.mFragmentManager.getParent();
    if (localObject != null) {
      ((Fragment)localObject).getParentFragmentManager().getLifecycleCallbacksDispatcher().dispatchOnFragmentViewDestroyed(paramFragment, true);
    }
    localObject = this.mLifecycleCallbacks.iterator();
    while (((Iterator)localObject).hasNext())
    {
      FragmentLifecycleCallbacksHolder localFragmentLifecycleCallbacksHolder = (FragmentLifecycleCallbacksHolder)((Iterator)localObject).next();
      if ((!paramBoolean) || (localFragmentLifecycleCallbacksHolder.mRecursive)) {
        localFragmentLifecycleCallbacksHolder.mCallback.onFragmentViewDestroyed(this.mFragmentManager, paramFragment);
      }
    }
  }
  
  public void registerFragmentLifecycleCallbacks(FragmentManager.FragmentLifecycleCallbacks paramFragmentLifecycleCallbacks, boolean paramBoolean)
  {
    this.mLifecycleCallbacks.add(new FragmentLifecycleCallbacksHolder(paramFragmentLifecycleCallbacks, paramBoolean));
  }
  
  public void unregisterFragmentLifecycleCallbacks(FragmentManager.FragmentLifecycleCallbacks paramFragmentLifecycleCallbacks)
  {
    CopyOnWriteArrayList localCopyOnWriteArrayList = this.mLifecycleCallbacks;
    int i = 0;
    try
    {
      int j = this.mLifecycleCallbacks.size();
      while (i < j)
      {
        if (((FragmentLifecycleCallbacksHolder)this.mLifecycleCallbacks.get(i)).mCallback == paramFragmentLifecycleCallbacks)
        {
          this.mLifecycleCallbacks.remove(i);
          break;
        }
        i++;
      }
      return;
    }
    finally {}
  }
  
  private static final class FragmentLifecycleCallbacksHolder
  {
    final FragmentManager.FragmentLifecycleCallbacks mCallback;
    final boolean mRecursive;
    
    FragmentLifecycleCallbacksHolder(FragmentManager.FragmentLifecycleCallbacks paramFragmentLifecycleCallbacks, boolean paramBoolean)
    {
      this.mCallback = paramFragmentLifecycleCallbacks;
      this.mRecursive = paramBoolean;
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/fragment/app/FragmentLifecycleCallbacksDispatcher.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */