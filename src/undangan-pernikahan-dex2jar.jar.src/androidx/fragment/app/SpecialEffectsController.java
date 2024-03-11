package androidx.fragment.app;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import androidx.core.os.CancellationSignal;
import androidx.core.os.CancellationSignal.OnCancelListener;
import androidx.core.view.ViewCompat;
import androidx.fragment.R.id;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import mt.Log229316;
import mt.Log5ECF72;
import mt.LogE84000;

abstract class SpecialEffectsController
{
  private final ViewGroup mContainer;
  boolean mIsContainerPostponed = false;
  boolean mOperationDirectionIsPop = false;
  final ArrayList<Operation> mPendingOperations = new ArrayList();
  final ArrayList<Operation> mRunningOperations = new ArrayList();
  
  SpecialEffectsController(ViewGroup paramViewGroup)
  {
    this.mContainer = paramViewGroup;
  }
  
  private void enqueue(SpecialEffectsController.Operation.State paramState, SpecialEffectsController.Operation.LifecycleImpact paramLifecycleImpact, FragmentStateManager paramFragmentStateManager)
  {
    synchronized (this.mPendingOperations)
    {
      CancellationSignal localCancellationSignal = new androidx/core/os/CancellationSignal;
      localCancellationSignal.<init>();
      Object localObject = findPendingOperation(paramFragmentStateManager.getFragment());
      if (localObject != null)
      {
        ((Operation)localObject).mergeWith(paramState, paramLifecycleImpact);
        return;
      }
      localObject = new androidx/fragment/app/SpecialEffectsController$FragmentStateManagerOperation;
      ((FragmentStateManagerOperation)localObject).<init>(paramState, paramLifecycleImpact, paramFragmentStateManager, localCancellationSignal);
      this.mPendingOperations.add(localObject);
      paramState = new androidx/fragment/app/SpecialEffectsController$1;
      paramState.<init>(this, (FragmentStateManagerOperation)localObject);
      ((FragmentStateManagerOperation)localObject).addCompletionListener(paramState);
      paramState = new androidx/fragment/app/SpecialEffectsController$2;
      paramState.<init>(this, (FragmentStateManagerOperation)localObject);
      ((FragmentStateManagerOperation)localObject).addCompletionListener(paramState);
      return;
    }
  }
  
  private Operation findPendingOperation(Fragment paramFragment)
  {
    Iterator localIterator = this.mPendingOperations.iterator();
    while (localIterator.hasNext())
    {
      Operation localOperation = (Operation)localIterator.next();
      if ((localOperation.getFragment().equals(paramFragment)) && (!localOperation.isCanceled())) {
        return localOperation;
      }
    }
    return null;
  }
  
  private Operation findRunningOperation(Fragment paramFragment)
  {
    Iterator localIterator = this.mRunningOperations.iterator();
    while (localIterator.hasNext())
    {
      Operation localOperation = (Operation)localIterator.next();
      if ((localOperation.getFragment().equals(paramFragment)) && (!localOperation.isCanceled())) {
        return localOperation;
      }
    }
    return null;
  }
  
  static SpecialEffectsController getOrCreateController(ViewGroup paramViewGroup, FragmentManager paramFragmentManager)
  {
    return getOrCreateController(paramViewGroup, paramFragmentManager.getSpecialEffectsControllerFactory());
  }
  
  static SpecialEffectsController getOrCreateController(ViewGroup paramViewGroup, SpecialEffectsControllerFactory paramSpecialEffectsControllerFactory)
  {
    Object localObject = paramViewGroup.getTag(R.id.special_effects_controller_view_tag);
    if ((localObject instanceof SpecialEffectsController)) {
      return (SpecialEffectsController)localObject;
    }
    paramSpecialEffectsControllerFactory = paramSpecialEffectsControllerFactory.createController(paramViewGroup);
    paramViewGroup.setTag(R.id.special_effects_controller_view_tag, paramSpecialEffectsControllerFactory);
    return paramSpecialEffectsControllerFactory;
  }
  
  private void updateFinalState()
  {
    Iterator localIterator = this.mPendingOperations.iterator();
    while (localIterator.hasNext())
    {
      Operation localOperation = (Operation)localIterator.next();
      if (localOperation.getLifecycleImpact() == SpecialEffectsController.Operation.LifecycleImpact.ADDING) {
        localOperation.mergeWith(SpecialEffectsController.Operation.State.from(localOperation.getFragment().requireView().getVisibility()), SpecialEffectsController.Operation.LifecycleImpact.NONE);
      }
    }
  }
  
  void enqueueAdd(SpecialEffectsController.Operation.State paramState, FragmentStateManager paramFragmentStateManager)
  {
    if (FragmentManager.isLoggingEnabled(2)) {
      Log.v("FragmentManager", "SpecialEffectsController: Enqueuing add operation for fragment " + paramFragmentStateManager.getFragment());
    }
    enqueue(paramState, SpecialEffectsController.Operation.LifecycleImpact.ADDING, paramFragmentStateManager);
  }
  
  void enqueueHide(FragmentStateManager paramFragmentStateManager)
  {
    if (FragmentManager.isLoggingEnabled(2)) {
      Log.v("FragmentManager", "SpecialEffectsController: Enqueuing hide operation for fragment " + paramFragmentStateManager.getFragment());
    }
    enqueue(SpecialEffectsController.Operation.State.GONE, SpecialEffectsController.Operation.LifecycleImpact.NONE, paramFragmentStateManager);
  }
  
  void enqueueRemove(FragmentStateManager paramFragmentStateManager)
  {
    if (FragmentManager.isLoggingEnabled(2)) {
      Log.v("FragmentManager", "SpecialEffectsController: Enqueuing remove operation for fragment " + paramFragmentStateManager.getFragment());
    }
    enqueue(SpecialEffectsController.Operation.State.REMOVED, SpecialEffectsController.Operation.LifecycleImpact.REMOVING, paramFragmentStateManager);
  }
  
  void enqueueShow(FragmentStateManager paramFragmentStateManager)
  {
    if (FragmentManager.isLoggingEnabled(2)) {
      Log.v("FragmentManager", "SpecialEffectsController: Enqueuing show operation for fragment " + paramFragmentStateManager.getFragment());
    }
    enqueue(SpecialEffectsController.Operation.State.VISIBLE, SpecialEffectsController.Operation.LifecycleImpact.NONE, paramFragmentStateManager);
  }
  
  abstract void executeOperations(List<Operation> paramList, boolean paramBoolean);
  
  void executePendingOperations()
  {
    if (this.mIsContainerPostponed) {
      return;
    }
    if (!ViewCompat.isAttachedToWindow(this.mContainer))
    {
      forceCompleteAllOperations();
      this.mOperationDirectionIsPop = false;
      return;
    }
    synchronized (this.mPendingOperations)
    {
      if (!this.mPendingOperations.isEmpty())
      {
        Object localObject1 = new java/util/ArrayList;
        ((ArrayList)localObject1).<init>(this.mRunningOperations);
        this.mRunningOperations.clear();
        Iterator localIterator = ((ArrayList)localObject1).iterator();
        while (localIterator.hasNext())
        {
          localObject1 = (Operation)localIterator.next();
          if (FragmentManager.isLoggingEnabled(2))
          {
            localObject3 = new java/lang/StringBuilder;
            ((StringBuilder)localObject3).<init>();
            Log.v("FragmentManager", "SpecialEffectsController: Cancelling operation " + localObject1);
          }
          ((Operation)localObject1).cancel();
          if (!((Operation)localObject1).isComplete()) {
            this.mRunningOperations.add(localObject1);
          }
        }
        updateFinalState();
        Object localObject3 = new java/util/ArrayList;
        ((ArrayList)localObject3).<init>(this.mPendingOperations);
        this.mPendingOperations.clear();
        this.mRunningOperations.addAll((Collection)localObject3);
        localObject1 = ((ArrayList)localObject3).iterator();
        while (((Iterator)localObject1).hasNext()) {
          ((Operation)((Iterator)localObject1).next()).onStart();
        }
        executeOperations((List)localObject3, this.mOperationDirectionIsPop);
        this.mOperationDirectionIsPop = false;
      }
      return;
    }
  }
  
  void forceCompleteAllOperations()
  {
    boolean bool = ViewCompat.isAttachedToWindow(this.mContainer);
    synchronized (this.mPendingOperations)
    {
      updateFinalState();
      Object localObject1 = this.mPendingOperations.iterator();
      while (((Iterator)localObject1).hasNext()) {
        ((Operation)((Iterator)localObject1).next()).onStart();
      }
      localObject1 = new java/util/ArrayList;
      ((ArrayList)localObject1).<init>(this.mRunningOperations);
      Object localObject4 = ((ArrayList)localObject1).iterator();
      StringBuilder localStringBuilder;
      while (((Iterator)localObject4).hasNext())
      {
        localObject3 = (Operation)((Iterator)localObject4).next();
        if (FragmentManager.isLoggingEnabled(2))
        {
          localObject1 = new java/lang/StringBuilder;
          ((StringBuilder)localObject1).<init>();
          localStringBuilder = ((StringBuilder)localObject1).append("SpecialEffectsController: ");
          if (bool)
          {
            localObject1 = "";
          }
          else
          {
            localObject1 = new java/lang/StringBuilder;
            ((StringBuilder)localObject1).<init>();
            localObject1 = "Container " + this.mContainer + " is not attached to window. ";
          }
          Log.v("FragmentManager", (String)localObject1 + "Cancelling running operation " + localObject3);
        }
        ((Operation)localObject3).cancel();
      }
      localObject1 = new java/util/ArrayList;
      ((ArrayList)localObject1).<init>(this.mPendingOperations);
      Object localObject3 = ((ArrayList)localObject1).iterator();
      while (((Iterator)localObject3).hasNext())
      {
        localObject4 = (Operation)((Iterator)localObject3).next();
        if (FragmentManager.isLoggingEnabled(2))
        {
          localObject1 = new java/lang/StringBuilder;
          ((StringBuilder)localObject1).<init>();
          localStringBuilder = ((StringBuilder)localObject1).append("SpecialEffectsController: ");
          if (bool)
          {
            localObject1 = "";
          }
          else
          {
            localObject1 = new java/lang/StringBuilder;
            ((StringBuilder)localObject1).<init>();
            localObject1 = "Container " + this.mContainer + " is not attached to window. ";
          }
          Log.v("FragmentManager", (String)localObject1 + "Cancelling pending operation " + localObject4);
        }
        ((Operation)localObject4).cancel();
      }
      return;
    }
  }
  
  void forcePostponedExecutePendingOperations()
  {
    if (this.mIsContainerPostponed)
    {
      this.mIsContainerPostponed = false;
      executePendingOperations();
    }
  }
  
  SpecialEffectsController.Operation.LifecycleImpact getAwaitingCompletionLifecycleImpact(FragmentStateManager paramFragmentStateManager)
  {
    SpecialEffectsController.Operation.LifecycleImpact localLifecycleImpact = null;
    Operation localOperation = findPendingOperation(paramFragmentStateManager.getFragment());
    if (localOperation != null) {
      localLifecycleImpact = localOperation.getLifecycleImpact();
    }
    paramFragmentStateManager = findRunningOperation(paramFragmentStateManager.getFragment());
    if ((paramFragmentStateManager != null) && ((localLifecycleImpact == null) || (localLifecycleImpact == SpecialEffectsController.Operation.LifecycleImpact.NONE))) {
      return paramFragmentStateManager.getLifecycleImpact();
    }
    return localLifecycleImpact;
  }
  
  public ViewGroup getContainer()
  {
    return this.mContainer;
  }
  
  void markPostponedState()
  {
    synchronized (this.mPendingOperations)
    {
      updateFinalState();
      this.mIsContainerPostponed = false;
      for (int i = this.mPendingOperations.size() - 1; i >= 0; i--)
      {
        Operation localOperation = (Operation)this.mPendingOperations.get(i);
        SpecialEffectsController.Operation.State localState = SpecialEffectsController.Operation.State.from(localOperation.getFragment().mView);
        if ((localOperation.getFinalState() == SpecialEffectsController.Operation.State.VISIBLE) && (localState != SpecialEffectsController.Operation.State.VISIBLE))
        {
          this.mIsContainerPostponed = localOperation.getFragment().isPostponed();
          break;
        }
      }
      return;
    }
  }
  
  void updateOperationDirection(boolean paramBoolean)
  {
    this.mOperationDirectionIsPop = paramBoolean;
  }
  
  private static class FragmentStateManagerOperation
    extends SpecialEffectsController.Operation
  {
    private final FragmentStateManager mFragmentStateManager;
    
    FragmentStateManagerOperation(SpecialEffectsController.Operation.State paramState, SpecialEffectsController.Operation.LifecycleImpact paramLifecycleImpact, FragmentStateManager paramFragmentStateManager, CancellationSignal paramCancellationSignal)
    {
      super(paramLifecycleImpact, paramFragmentStateManager.getFragment(), paramCancellationSignal);
      this.mFragmentStateManager = paramFragmentStateManager;
    }
    
    public void complete()
    {
      super.complete();
      this.mFragmentStateManager.moveToExpectedState();
    }
    
    void onStart()
    {
      if (getLifecycleImpact() == SpecialEffectsController.Operation.LifecycleImpact.ADDING)
      {
        Fragment localFragment = this.mFragmentStateManager.getFragment();
        View localView = localFragment.mView.findFocus();
        if (localView != null)
        {
          localFragment.setFocusedView(localView);
          if (FragmentManager.isLoggingEnabled(2)) {
            Log.v("FragmentManager", "requestFocus: Saved focused view " + localView + " for Fragment " + localFragment);
          }
        }
        localView = getFragment().requireView();
        if (localView.getParent() == null)
        {
          this.mFragmentStateManager.addViewToContainer();
          localView.setAlpha(0.0F);
        }
        if ((localView.getAlpha() == 0.0F) && (localView.getVisibility() == 0)) {
          localView.setVisibility(4);
        }
        localView.setAlpha(localFragment.getPostOnViewCreatedAlpha());
      }
    }
  }
  
  static class Operation
  {
    private final List<Runnable> mCompletionListeners = new ArrayList();
    private State mFinalState;
    private final Fragment mFragment;
    private boolean mIsCanceled = false;
    private boolean mIsComplete = false;
    private LifecycleImpact mLifecycleImpact;
    private final HashSet<CancellationSignal> mSpecialEffectsSignals = new HashSet();
    
    Operation(State paramState, LifecycleImpact paramLifecycleImpact, Fragment paramFragment, CancellationSignal paramCancellationSignal)
    {
      this.mFinalState = paramState;
      this.mLifecycleImpact = paramLifecycleImpact;
      this.mFragment = paramFragment;
      paramCancellationSignal.setOnCancelListener(new CancellationSignal.OnCancelListener()
      {
        public void onCancel()
        {
          SpecialEffectsController.Operation.this.cancel();
        }
      });
    }
    
    final void addCompletionListener(Runnable paramRunnable)
    {
      this.mCompletionListeners.add(paramRunnable);
    }
    
    final void cancel()
    {
      if (isCanceled()) {
        return;
      }
      this.mIsCanceled = true;
      if (this.mSpecialEffectsSignals.isEmpty())
      {
        complete();
      }
      else
      {
        Iterator localIterator = new ArrayList(this.mSpecialEffectsSignals).iterator();
        while (localIterator.hasNext()) {
          ((CancellationSignal)localIterator.next()).cancel();
        }
      }
    }
    
    public void complete()
    {
      if (this.mIsComplete) {
        return;
      }
      if (FragmentManager.isLoggingEnabled(2)) {
        Log.v("FragmentManager", "SpecialEffectsController: " + this + " has called complete.");
      }
      this.mIsComplete = true;
      Iterator localIterator = this.mCompletionListeners.iterator();
      while (localIterator.hasNext()) {
        ((Runnable)localIterator.next()).run();
      }
    }
    
    public final void completeSpecialEffect(CancellationSignal paramCancellationSignal)
    {
      if ((this.mSpecialEffectsSignals.remove(paramCancellationSignal)) && (this.mSpecialEffectsSignals.isEmpty())) {
        complete();
      }
    }
    
    public State getFinalState()
    {
      return this.mFinalState;
    }
    
    public final Fragment getFragment()
    {
      return this.mFragment;
    }
    
    LifecycleImpact getLifecycleImpact()
    {
      return this.mLifecycleImpact;
    }
    
    final boolean isCanceled()
    {
      return this.mIsCanceled;
    }
    
    final boolean isComplete()
    {
      return this.mIsComplete;
    }
    
    public final void markStartedSpecialEffect(CancellationSignal paramCancellationSignal)
    {
      onStart();
      this.mSpecialEffectsSignals.add(paramCancellationSignal);
    }
    
    final void mergeWith(State paramState, LifecycleImpact paramLifecycleImpact)
    {
      switch (SpecialEffectsController.3.$SwitchMap$androidx$fragment$app$SpecialEffectsController$Operation$LifecycleImpact[paramLifecycleImpact.ordinal()])
      {
      default: 
        break;
      case 3: 
        if (this.mFinalState != State.REMOVED)
        {
          if (FragmentManager.isLoggingEnabled(2)) {
            Log.v("FragmentManager", "SpecialEffectsController: For fragment " + this.mFragment + " mFinalState = " + this.mFinalState + " -> " + paramState + ". ");
          }
          this.mFinalState = paramState;
        }
        break;
      case 2: 
        if (FragmentManager.isLoggingEnabled(2)) {
          Log.v("FragmentManager", "SpecialEffectsController: For fragment " + this.mFragment + " mFinalState = " + this.mFinalState + " -> REMOVED. mLifecycleImpact  = " + this.mLifecycleImpact + " to REMOVING.");
        }
        this.mFinalState = State.REMOVED;
        this.mLifecycleImpact = LifecycleImpact.REMOVING;
        break;
      case 1: 
        if (this.mFinalState == State.REMOVED)
        {
          if (FragmentManager.isLoggingEnabled(2)) {
            Log.v("FragmentManager", "SpecialEffectsController: For fragment " + this.mFragment + " mFinalState = REMOVED -> VISIBLE. mLifecycleImpact = " + this.mLifecycleImpact + " to ADDING.");
          }
          this.mFinalState = State.VISIBLE;
          this.mLifecycleImpact = LifecycleImpact.ADDING;
        }
        break;
      }
    }
    
    void onStart() {}
    
    public String toString()
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("Operation ");
      localStringBuilder.append("{");
      String str = Integer.toHexString(System.identityHashCode(this));
      Log5ECF72.a(str);
      LogE84000.a(str);
      Log229316.a(str);
      localStringBuilder.append(str);
      localStringBuilder.append("} ");
      localStringBuilder.append("{");
      localStringBuilder.append("mFinalState = ");
      localStringBuilder.append(this.mFinalState);
      localStringBuilder.append("} ");
      localStringBuilder.append("{");
      localStringBuilder.append("mLifecycleImpact = ");
      localStringBuilder.append(this.mLifecycleImpact);
      localStringBuilder.append("} ");
      localStringBuilder.append("{");
      localStringBuilder.append("mFragment = ");
      localStringBuilder.append(this.mFragment);
      localStringBuilder.append("}");
      return localStringBuilder.toString();
    }
    
    static enum LifecycleImpact
    {
      private static final LifecycleImpact[] $VALUES;
      
      static
      {
        LifecycleImpact localLifecycleImpact2 = new LifecycleImpact("NONE", 0);
        NONE = localLifecycleImpact2;
        LifecycleImpact localLifecycleImpact1 = new LifecycleImpact("ADDING", 1);
        ADDING = localLifecycleImpact1;
        LifecycleImpact localLifecycleImpact3 = new LifecycleImpact("REMOVING", 2);
        REMOVING = localLifecycleImpact3;
        $VALUES = new LifecycleImpact[] { localLifecycleImpact2, localLifecycleImpact1, localLifecycleImpact3 };
      }
      
      private LifecycleImpact() {}
    }
    
    static enum State
    {
      private static final State[] $VALUES;
      
      static
      {
        State localState1 = new State("REMOVED", 0);
        REMOVED = localState1;
        State localState2 = new State("VISIBLE", 1);
        VISIBLE = localState2;
        State localState3 = new State("GONE", 2);
        GONE = localState3;
        State localState4 = new State("INVISIBLE", 3);
        INVISIBLE = localState4;
        $VALUES = new State[] { localState1, localState2, localState3, localState4 };
      }
      
      private State() {}
      
      static State from(int paramInt)
      {
        switch (paramInt)
        {
        default: 
          throw new IllegalArgumentException("Unknown visibility " + paramInt);
        case 8: 
          return GONE;
        case 4: 
          return INVISIBLE;
        }
        return VISIBLE;
      }
      
      static State from(View paramView)
      {
        if ((paramView.getAlpha() == 0.0F) && (paramView.getVisibility() == 0)) {
          return INVISIBLE;
        }
        return from(paramView.getVisibility());
      }
      
      void applyState(View paramView)
      {
        switch (SpecialEffectsController.3.$SwitchMap$androidx$fragment$app$SpecialEffectsController$Operation$State[ordinal()])
        {
        default: 
          break;
        case 4: 
          if (FragmentManager.isLoggingEnabled(2)) {
            Log.v("FragmentManager", "SpecialEffectsController: Setting view " + paramView + " to INVISIBLE");
          }
          paramView.setVisibility(4);
          break;
        case 3: 
          if (FragmentManager.isLoggingEnabled(2)) {
            Log.v("FragmentManager", "SpecialEffectsController: Setting view " + paramView + " to GONE");
          }
          paramView.setVisibility(8);
          break;
        case 2: 
          if (FragmentManager.isLoggingEnabled(2)) {
            Log.v("FragmentManager", "SpecialEffectsController: Setting view " + paramView + " to VISIBLE");
          }
          paramView.setVisibility(0);
          break;
        case 1: 
          ViewGroup localViewGroup = (ViewGroup)paramView.getParent();
          if (localViewGroup != null)
          {
            if (FragmentManager.isLoggingEnabled(2)) {
              Log.v("FragmentManager", "SpecialEffectsController: Removing view " + paramView + " from container " + localViewGroup);
            }
            localViewGroup.removeView(paramView);
          }
          break;
        }
      }
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/fragment/app/SpecialEffectsController.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */