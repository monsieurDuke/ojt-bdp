package androidx.lifecycle;

import androidx.arch.core.executor.ArchTaskExecutor;
import androidx.arch.core.internal.FastSafeIterableMap;
import androidx.arch.core.internal.SafeIterableMap.IteratorWithAdditions;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map.Entry;

public class LifecycleRegistry
  extends Lifecycle
{
  private int mAddingObserverCounter = 0;
  private final boolean mEnforceMainThread;
  private boolean mHandlingEvent = false;
  private final WeakReference<LifecycleOwner> mLifecycleOwner;
  private boolean mNewEventOccurred = false;
  private FastSafeIterableMap<LifecycleObserver, ObserverWithState> mObserverMap = new FastSafeIterableMap();
  private ArrayList<Lifecycle.State> mParentStates = new ArrayList();
  private Lifecycle.State mState;
  
  public LifecycleRegistry(LifecycleOwner paramLifecycleOwner)
  {
    this(paramLifecycleOwner, true);
  }
  
  private LifecycleRegistry(LifecycleOwner paramLifecycleOwner, boolean paramBoolean)
  {
    this.mLifecycleOwner = new WeakReference(paramLifecycleOwner);
    this.mState = Lifecycle.State.INITIALIZED;
    this.mEnforceMainThread = paramBoolean;
  }
  
  private void backwardPass(LifecycleOwner paramLifecycleOwner)
  {
    Iterator localIterator = this.mObserverMap.descendingIterator();
    while ((localIterator.hasNext()) && (!this.mNewEventOccurred))
    {
      Map.Entry localEntry = (Map.Entry)localIterator.next();
      ObserverWithState localObserverWithState = (ObserverWithState)localEntry.getValue();
      while ((localObserverWithState.mState.compareTo(this.mState) > 0) && (!this.mNewEventOccurred) && (this.mObserverMap.contains((LifecycleObserver)localEntry.getKey())))
      {
        Lifecycle.Event localEvent = Lifecycle.Event.downFrom(localObserverWithState.mState);
        if (localEvent != null)
        {
          pushParentState(localEvent.getTargetState());
          localObserverWithState.dispatchEvent(paramLifecycleOwner, localEvent);
          popParentState();
        }
        else
        {
          throw new IllegalStateException("no event down from " + localObserverWithState.mState);
        }
      }
    }
  }
  
  private Lifecycle.State calculateTargetState(LifecycleObserver paramLifecycleObserver)
  {
    paramLifecycleObserver = this.mObserverMap.ceil(paramLifecycleObserver);
    Object localObject = null;
    if (paramLifecycleObserver != null) {
      paramLifecycleObserver = ((ObserverWithState)paramLifecycleObserver.getValue()).mState;
    } else {
      paramLifecycleObserver = null;
    }
    if (!this.mParentStates.isEmpty())
    {
      localObject = this.mParentStates;
      localObject = (Lifecycle.State)((ArrayList)localObject).get(((ArrayList)localObject).size() - 1);
    }
    return min(min(this.mState, paramLifecycleObserver), (Lifecycle.State)localObject);
  }
  
  public static LifecycleRegistry createUnsafe(LifecycleOwner paramLifecycleOwner)
  {
    return new LifecycleRegistry(paramLifecycleOwner, false);
  }
  
  private void enforceMainThreadIfNeeded(String paramString)
  {
    if ((this.mEnforceMainThread) && (!ArchTaskExecutor.getInstance().isMainThread())) {
      throw new IllegalStateException("Method " + paramString + " must be called on the main thread");
    }
  }
  
  private void forwardPass(LifecycleOwner paramLifecycleOwner)
  {
    SafeIterableMap.IteratorWithAdditions localIteratorWithAdditions = this.mObserverMap.iteratorWithAdditions();
    while ((localIteratorWithAdditions.hasNext()) && (!this.mNewEventOccurred))
    {
      Map.Entry localEntry = (Map.Entry)localIteratorWithAdditions.next();
      ObserverWithState localObserverWithState = (ObserverWithState)localEntry.getValue();
      while ((localObserverWithState.mState.compareTo(this.mState) < 0) && (!this.mNewEventOccurred) && (this.mObserverMap.contains((LifecycleObserver)localEntry.getKey())))
      {
        pushParentState(localObserverWithState.mState);
        Lifecycle.Event localEvent = Lifecycle.Event.upFrom(localObserverWithState.mState);
        if (localEvent != null)
        {
          localObserverWithState.dispatchEvent(paramLifecycleOwner, localEvent);
          popParentState();
        }
        else
        {
          throw new IllegalStateException("no event up from " + localObserverWithState.mState);
        }
      }
    }
  }
  
  private boolean isSynced()
  {
    int i = this.mObserverMap.size();
    boolean bool = true;
    if (i == 0) {
      return true;
    }
    Lifecycle.State localState2 = ((ObserverWithState)this.mObserverMap.eldest().getValue()).mState;
    Lifecycle.State localState1 = ((ObserverWithState)this.mObserverMap.newest().getValue()).mState;
    if ((localState2 != localState1) || (this.mState != localState1)) {
      bool = false;
    }
    return bool;
  }
  
  static Lifecycle.State min(Lifecycle.State paramState1, Lifecycle.State paramState2)
  {
    if ((paramState2 != null) && (paramState2.compareTo(paramState1) < 0)) {
      paramState1 = paramState2;
    }
    return paramState1;
  }
  
  private void moveToState(Lifecycle.State paramState)
  {
    Lifecycle.State localState = this.mState;
    if (localState == paramState) {
      return;
    }
    if ((localState == Lifecycle.State.INITIALIZED) && (paramState == Lifecycle.State.DESTROYED)) {
      throw new IllegalStateException("no event down from " + this.mState);
    }
    this.mState = paramState;
    if ((!this.mHandlingEvent) && (this.mAddingObserverCounter == 0))
    {
      this.mHandlingEvent = true;
      sync();
      this.mHandlingEvent = false;
      if (this.mState == Lifecycle.State.DESTROYED) {
        this.mObserverMap = new FastSafeIterableMap();
      }
      return;
    }
    this.mNewEventOccurred = true;
  }
  
  private void popParentState()
  {
    ArrayList localArrayList = this.mParentStates;
    localArrayList.remove(localArrayList.size() - 1);
  }
  
  private void pushParentState(Lifecycle.State paramState)
  {
    this.mParentStates.add(paramState);
  }
  
  private void sync()
  {
    LifecycleOwner localLifecycleOwner = (LifecycleOwner)this.mLifecycleOwner.get();
    if (localLifecycleOwner != null)
    {
      while (!isSynced())
      {
        this.mNewEventOccurred = false;
        if (this.mState.compareTo(((ObserverWithState)this.mObserverMap.eldest().getValue()).mState) < 0) {
          backwardPass(localLifecycleOwner);
        }
        Map.Entry localEntry = this.mObserverMap.newest();
        if ((!this.mNewEventOccurred) && (localEntry != null) && (this.mState.compareTo(((ObserverWithState)localEntry.getValue()).mState) > 0)) {
          forwardPass(localLifecycleOwner);
        }
      }
      this.mNewEventOccurred = false;
      return;
    }
    throw new IllegalStateException("LifecycleOwner of this LifecycleRegistry is alreadygarbage collected. It is too late to change lifecycle state.");
  }
  
  public void addObserver(LifecycleObserver paramLifecycleObserver)
  {
    enforceMainThreadIfNeeded("addObserver");
    if (this.mState == Lifecycle.State.DESTROYED) {
      localObject = Lifecycle.State.DESTROYED;
    } else {
      localObject = Lifecycle.State.INITIALIZED;
    }
    ObserverWithState localObserverWithState = new ObserverWithState(paramLifecycleObserver, (Lifecycle.State)localObject);
    if ((ObserverWithState)this.mObserverMap.putIfAbsent(paramLifecycleObserver, localObserverWithState) != null) {
      return;
    }
    LifecycleOwner localLifecycleOwner = (LifecycleOwner)this.mLifecycleOwner.get();
    if (localLifecycleOwner == null) {
      return;
    }
    int i;
    if ((this.mAddingObserverCounter == 0) && (!this.mHandlingEvent)) {
      i = 0;
    } else {
      i = 1;
    }
    Object localObject = calculateTargetState(paramLifecycleObserver);
    this.mAddingObserverCounter += 1;
    while ((localObserverWithState.mState.compareTo((Enum)localObject) < 0) && (this.mObserverMap.contains(paramLifecycleObserver)))
    {
      pushParentState(localObserverWithState.mState);
      localObject = Lifecycle.Event.upFrom(localObserverWithState.mState);
      if (localObject != null)
      {
        localObserverWithState.dispatchEvent(localLifecycleOwner, (Lifecycle.Event)localObject);
        popParentState();
        localObject = calculateTargetState(paramLifecycleObserver);
      }
      else
      {
        throw new IllegalStateException("no event up from " + localObserverWithState.mState);
      }
    }
    if (i == 0) {
      sync();
    }
    this.mAddingObserverCounter -= 1;
  }
  
  public Lifecycle.State getCurrentState()
  {
    return this.mState;
  }
  
  public int getObserverCount()
  {
    enforceMainThreadIfNeeded("getObserverCount");
    return this.mObserverMap.size();
  }
  
  public void handleLifecycleEvent(Lifecycle.Event paramEvent)
  {
    enforceMainThreadIfNeeded("handleLifecycleEvent");
    moveToState(paramEvent.getTargetState());
  }
  
  @Deprecated
  public void markState(Lifecycle.State paramState)
  {
    enforceMainThreadIfNeeded("markState");
    setCurrentState(paramState);
  }
  
  public void removeObserver(LifecycleObserver paramLifecycleObserver)
  {
    enforceMainThreadIfNeeded("removeObserver");
    this.mObserverMap.remove(paramLifecycleObserver);
  }
  
  public void setCurrentState(Lifecycle.State paramState)
  {
    enforceMainThreadIfNeeded("setCurrentState");
    moveToState(paramState);
  }
  
  static class ObserverWithState
  {
    LifecycleEventObserver mLifecycleObserver;
    Lifecycle.State mState;
    
    ObserverWithState(LifecycleObserver paramLifecycleObserver, Lifecycle.State paramState)
    {
      this.mLifecycleObserver = Lifecycling.lifecycleEventObserver(paramLifecycleObserver);
      this.mState = paramState;
    }
    
    void dispatchEvent(LifecycleOwner paramLifecycleOwner, Lifecycle.Event paramEvent)
    {
      Lifecycle.State localState = paramEvent.getTargetState();
      this.mState = LifecycleRegistry.min(this.mState, localState);
      this.mLifecycleObserver.onStateChanged(paramLifecycleOwner, paramEvent);
      this.mState = localState;
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/lifecycle/LifecycleRegistry.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */