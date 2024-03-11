package androidx.fragment.app;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.IntentSender;
import android.content.IntentSender.SendIntentException;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.util.Log;
import android.view.LayoutInflater.Factory2;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import androidx.activity.OnBackPressedCallback;
import androidx.activity.OnBackPressedDispatcher;
import androidx.activity.OnBackPressedDispatcherOwner;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.ActivityResultRegistry;
import androidx.activity.result.ActivityResultRegistryOwner;
import androidx.activity.result.IntentSenderRequest;
import androidx.activity.result.IntentSenderRequest.Builder;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts.RequestMultiplePermissions;
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult;
import androidx.collection.ArraySet;
import androidx.core.os.CancellationSignal;
import androidx.fragment.R.id;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.Lifecycle.Event;
import androidx.lifecycle.Lifecycle.State;
import androidx.lifecycle.LifecycleEventObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelStore;
import androidx.lifecycle.ViewModelStoreOwner;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;
import mt.Log229316;
import mt.Log5ECF72;
import mt.LogE84000;

public abstract class FragmentManager
  implements FragmentResultOwner
{
  private static boolean DEBUG = false;
  private static final String EXTRA_CREATED_FILLIN_INTENT = "androidx.fragment.extra.ACTIVITY_OPTIONS_BUNDLE";
  public static final int POP_BACK_STACK_INCLUSIVE = 1;
  static final String TAG = "FragmentManager";
  static boolean USE_STATE_MANAGER = true;
  ArrayList<BackStackRecord> mBackStack;
  private ArrayList<OnBackStackChangedListener> mBackStackChangeListeners;
  private final AtomicInteger mBackStackIndex = new AtomicInteger();
  private FragmentContainer mContainer;
  private ArrayList<Fragment> mCreatedMenus;
  int mCurState = -1;
  private SpecialEffectsControllerFactory mDefaultSpecialEffectsControllerFactory = new SpecialEffectsControllerFactory()
  {
    public SpecialEffectsController createController(ViewGroup paramAnonymousViewGroup)
    {
      return new DefaultSpecialEffectsController(paramAnonymousViewGroup);
    }
  };
  private boolean mDestroyed;
  private Runnable mExecCommit = new Runnable()
  {
    public void run()
    {
      FragmentManager.this.execPendingActions(true);
    }
  };
  private boolean mExecutingActions;
  private Map<Fragment, HashSet<CancellationSignal>> mExitAnimationCancellationSignals = Collections.synchronizedMap(new HashMap());
  private FragmentFactory mFragmentFactory = null;
  private final FragmentStore mFragmentStore = new FragmentStore();
  private final FragmentTransition.Callback mFragmentTransitionCallback = new FragmentTransition.Callback()
  {
    public void onComplete(Fragment paramAnonymousFragment, CancellationSignal paramAnonymousCancellationSignal)
    {
      if (!paramAnonymousCancellationSignal.isCanceled()) {
        FragmentManager.this.removeCancellationSignal(paramAnonymousFragment, paramAnonymousCancellationSignal);
      }
    }
    
    public void onStart(Fragment paramAnonymousFragment, CancellationSignal paramAnonymousCancellationSignal)
    {
      FragmentManager.this.addCancellationSignal(paramAnonymousFragment, paramAnonymousCancellationSignal);
    }
  };
  private boolean mHavePendingDeferredStart;
  private FragmentHostCallback<?> mHost;
  private FragmentFactory mHostFragmentFactory = new FragmentFactory()
  {
    public Fragment instantiate(ClassLoader paramAnonymousClassLoader, String paramAnonymousString)
    {
      return FragmentManager.this.getHost().instantiate(FragmentManager.this.getHost().getContext(), paramAnonymousString, null);
    }
  };
  ArrayDeque<LaunchedFragmentInfo> mLaunchedFragments = new ArrayDeque();
  private final FragmentLayoutInflaterFactory mLayoutInflaterFactory = new FragmentLayoutInflaterFactory(this);
  private final FragmentLifecycleCallbacksDispatcher mLifecycleCallbacksDispatcher = new FragmentLifecycleCallbacksDispatcher(this);
  private boolean mNeedMenuInvalidate;
  private FragmentManagerViewModel mNonConfig;
  private final CopyOnWriteArrayList<FragmentOnAttachListener> mOnAttachListeners = new CopyOnWriteArrayList();
  private final OnBackPressedCallback mOnBackPressedCallback = new OnBackPressedCallback(false)
  {
    public void handleOnBackPressed()
    {
      FragmentManager.this.handleOnBackPressed();
    }
  };
  private OnBackPressedDispatcher mOnBackPressedDispatcher;
  private Fragment mParent;
  private final ArrayList<OpGenerator> mPendingActions = new ArrayList();
  private ArrayList<StartEnterTransitionListener> mPostponedTransactions;
  Fragment mPrimaryNav;
  private ActivityResultLauncher<String[]> mRequestPermissions;
  private final Map<String, LifecycleAwareResultListener> mResultListeners = Collections.synchronizedMap(new HashMap());
  private final Map<String, Bundle> mResults = Collections.synchronizedMap(new HashMap());
  private SpecialEffectsControllerFactory mSpecialEffectsControllerFactory = null;
  private ActivityResultLauncher<Intent> mStartActivityForResult;
  private ActivityResultLauncher<IntentSenderRequest> mStartIntentSenderForResult;
  private boolean mStateSaved;
  private boolean mStopped;
  private ArrayList<Fragment> mTmpAddedFragments;
  private ArrayList<Boolean> mTmpIsPop;
  private ArrayList<BackStackRecord> mTmpRecords;
  
  private void addAddedFragments(ArraySet<Fragment> paramArraySet)
  {
    int i = this.mCurState;
    if (i < 1) {
      return;
    }
    i = Math.min(i, 5);
    Iterator localIterator = this.mFragmentStore.getFragments().iterator();
    while (localIterator.hasNext())
    {
      Fragment localFragment = (Fragment)localIterator.next();
      if (localFragment.mState < i)
      {
        moveToState(localFragment, i);
        if ((localFragment.mView != null) && (!localFragment.mHidden) && (localFragment.mIsNewlyAdded)) {
          paramArraySet.add(localFragment);
        }
      }
    }
  }
  
  private void cancelExitAnimation(Fragment paramFragment)
  {
    HashSet localHashSet = (HashSet)this.mExitAnimationCancellationSignals.get(paramFragment);
    if (localHashSet != null)
    {
      Iterator localIterator = localHashSet.iterator();
      while (localIterator.hasNext()) {
        ((CancellationSignal)localIterator.next()).cancel();
      }
      localHashSet.clear();
      destroyFragmentView(paramFragment);
      this.mExitAnimationCancellationSignals.remove(paramFragment);
    }
  }
  
  private void checkStateLoss()
  {
    if (!isStateSaved()) {
      return;
    }
    throw new IllegalStateException("Can not perform this action after onSaveInstanceState");
  }
  
  private void cleanupExec()
  {
    this.mExecutingActions = false;
    this.mTmpIsPop.clear();
    this.mTmpRecords.clear();
  }
  
  private Set<SpecialEffectsController> collectAllSpecialEffectsController()
  {
    HashSet localHashSet = new HashSet();
    Iterator localIterator = this.mFragmentStore.getActiveFragmentStateManagers().iterator();
    while (localIterator.hasNext())
    {
      ViewGroup localViewGroup = ((FragmentStateManager)localIterator.next()).getFragment().mContainer;
      if (localViewGroup != null) {
        localHashSet.add(SpecialEffectsController.getOrCreateController(localViewGroup, getSpecialEffectsControllerFactory()));
      }
    }
    return localHashSet;
  }
  
  private Set<SpecialEffectsController> collectChangedControllers(ArrayList<BackStackRecord> paramArrayList, int paramInt1, int paramInt2)
  {
    HashSet localHashSet = new HashSet();
    while (paramInt1 < paramInt2)
    {
      Iterator localIterator = ((BackStackRecord)paramArrayList.get(paramInt1)).mOps.iterator();
      while (localIterator.hasNext())
      {
        Object localObject = ((FragmentTransaction.Op)localIterator.next()).mFragment;
        if (localObject != null)
        {
          localObject = ((Fragment)localObject).mContainer;
          if (localObject != null) {
            localHashSet.add(SpecialEffectsController.getOrCreateController((ViewGroup)localObject, this));
          }
        }
      }
      paramInt1++;
    }
    return localHashSet;
  }
  
  private void completeShowHideFragment(final Fragment paramFragment)
  {
    if (paramFragment.mView != null)
    {
      FragmentAnim.AnimationOrAnimator localAnimationOrAnimator = FragmentAnim.loadAnimation(this.mHost.getContext(), paramFragment, paramFragment.mHidden ^ true, paramFragment.getPopDirection());
      if ((localAnimationOrAnimator != null) && (localAnimationOrAnimator.animator != null))
      {
        localAnimationOrAnimator.animator.setTarget(paramFragment.mView);
        if (paramFragment.mHidden)
        {
          if (paramFragment.isHideReplaced())
          {
            paramFragment.setHideReplaced(false);
          }
          else
          {
            final ViewGroup localViewGroup = paramFragment.mContainer;
            final View localView = paramFragment.mView;
            localViewGroup.startViewTransition(localView);
            localAnimationOrAnimator.animator.addListener(new AnimatorListenerAdapter()
            {
              public void onAnimationEnd(Animator paramAnonymousAnimator)
              {
                localViewGroup.endViewTransition(localView);
                paramAnonymousAnimator.removeListener(this);
                if ((paramFragment.mView != null) && (paramFragment.mHidden)) {
                  paramFragment.mView.setVisibility(8);
                }
              }
            });
          }
        }
        else {
          paramFragment.mView.setVisibility(0);
        }
        localAnimationOrAnimator.animator.start();
      }
      else
      {
        if (localAnimationOrAnimator != null)
        {
          paramFragment.mView.startAnimation(localAnimationOrAnimator.animation);
          localAnimationOrAnimator.animation.start();
        }
        int i;
        if ((paramFragment.mHidden) && (!paramFragment.isHideReplaced())) {
          i = 8;
        } else {
          i = 0;
        }
        paramFragment.mView.setVisibility(i);
        if (paramFragment.isHideReplaced()) {
          paramFragment.setHideReplaced(false);
        }
      }
    }
    invalidateMenuForFragment(paramFragment);
    paramFragment.mHiddenChanged = false;
    paramFragment.onHiddenChanged(paramFragment.mHidden);
  }
  
  private void destroyFragmentView(Fragment paramFragment)
  {
    paramFragment.performDestroyView();
    this.mLifecycleCallbacksDispatcher.dispatchOnFragmentViewDestroyed(paramFragment, false);
    paramFragment.mContainer = null;
    paramFragment.mView = null;
    paramFragment.mViewLifecycleOwner = null;
    paramFragment.mViewLifecycleOwnerLiveData.setValue(null);
    paramFragment.mInLayout = false;
  }
  
  private void dispatchParentPrimaryNavigationFragmentChanged(Fragment paramFragment)
  {
    if ((paramFragment != null) && (paramFragment.equals(findActiveFragment(paramFragment.mWho)))) {
      paramFragment.performPrimaryNavigationFragmentChanged();
    }
  }
  
  private void dispatchStateChange(int paramInt)
  {
    try
    {
      this.mExecutingActions = true;
      this.mFragmentStore.dispatchStateChange(paramInt);
      moveToState(paramInt, false);
      if (USE_STATE_MANAGER)
      {
        Iterator localIterator = collectAllSpecialEffectsController().iterator();
        while (localIterator.hasNext()) {
          ((SpecialEffectsController)localIterator.next()).forceCompleteAllOperations();
        }
      }
      this.mExecutingActions = false;
      execPendingActions(true);
      return;
    }
    finally
    {
      this.mExecutingActions = false;
    }
  }
  
  private void doPendingDeferredStart()
  {
    if (this.mHavePendingDeferredStart)
    {
      this.mHavePendingDeferredStart = false;
      startPendingDeferredFragments();
    }
  }
  
  @Deprecated
  public static void enableDebugLogging(boolean paramBoolean)
  {
    DEBUG = paramBoolean;
  }
  
  public static void enableNewStateManager(boolean paramBoolean)
  {
    USE_STATE_MANAGER = paramBoolean;
  }
  
  private void endAnimatingAwayFragments()
  {
    Object localObject;
    if (USE_STATE_MANAGER)
    {
      localObject = collectAllSpecialEffectsController().iterator();
      while (((Iterator)localObject).hasNext()) {
        ((SpecialEffectsController)((Iterator)localObject).next()).forceCompleteAllOperations();
      }
    }
    else if (!this.mExitAnimationCancellationSignals.isEmpty())
    {
      Iterator localIterator = this.mExitAnimationCancellationSignals.keySet().iterator();
      while (localIterator.hasNext())
      {
        localObject = (Fragment)localIterator.next();
        cancelExitAnimation((Fragment)localObject);
        moveToState((Fragment)localObject);
      }
    }
  }
  
  private void ensureExecReady(boolean paramBoolean)
  {
    if (!this.mExecutingActions)
    {
      if (this.mHost == null)
      {
        if (this.mDestroyed) {
          throw new IllegalStateException("FragmentManager has been destroyed");
        }
        throw new IllegalStateException("FragmentManager has not been attached to a host.");
      }
      if (Looper.myLooper() == this.mHost.getHandler().getLooper())
      {
        if (!paramBoolean) {
          checkStateLoss();
        }
        if (this.mTmpRecords == null)
        {
          this.mTmpRecords = new ArrayList();
          this.mTmpIsPop = new ArrayList();
        }
        this.mExecutingActions = true;
        try
        {
          executePostponedTransaction(null, null);
          return;
        }
        finally
        {
          this.mExecutingActions = false;
        }
      }
      throw new IllegalStateException("Must be called from main thread of fragment host");
    }
    throw new IllegalStateException("FragmentManager is already executing transactions");
  }
  
  private static void executeOps(ArrayList<BackStackRecord> paramArrayList, ArrayList<Boolean> paramArrayList1, int paramInt1, int paramInt2)
  {
    while (paramInt1 < paramInt2)
    {
      BackStackRecord localBackStackRecord = (BackStackRecord)paramArrayList.get(paramInt1);
      boolean bool2 = ((Boolean)paramArrayList1.get(paramInt1)).booleanValue();
      boolean bool1 = true;
      if (bool2)
      {
        localBackStackRecord.bumpBackStackNesting(-1);
        if (paramInt1 != paramInt2 - 1) {
          bool1 = false;
        }
        localBackStackRecord.executePopOps(bool1);
      }
      else
      {
        localBackStackRecord.bumpBackStackNesting(1);
        localBackStackRecord.executeOps();
      }
      paramInt1++;
    }
  }
  
  private void executeOpsTogether(ArrayList<BackStackRecord> paramArrayList, ArrayList<Boolean> paramArrayList1, int paramInt1, int paramInt2)
  {
    boolean bool = ((BackStackRecord)paramArrayList.get(paramInt1)).mReorderingAllowed;
    Object localObject1 = this.mTmpAddedFragments;
    if (localObject1 == null) {
      this.mTmpAddedFragments = new ArrayList();
    } else {
      ((ArrayList)localObject1).clear();
    }
    this.mTmpAddedFragments.addAll(this.mFragmentStore.getFragments());
    localObject1 = getPrimaryNavigationFragment();
    int k = paramInt1;
    Object localObject2;
    int i;
    for (int j = 0;; j = i)
    {
      int m = 1;
      if (k >= paramInt2) {
        break;
      }
      localObject2 = (BackStackRecord)paramArrayList.get(k);
      if (!((Boolean)paramArrayList1.get(k)).booleanValue()) {
        localObject1 = ((BackStackRecord)localObject2).expandOps(this.mTmpAddedFragments, (Fragment)localObject1);
      } else {
        localObject1 = ((BackStackRecord)localObject2).trackAddedFragmentsInPop(this.mTmpAddedFragments, (Fragment)localObject1);
      }
      i = m;
      if (j == 0) {
        if (((BackStackRecord)localObject2).mAddToBackStack) {
          i = m;
        } else {
          i = 0;
        }
      }
      k++;
    }
    this.mTmpAddedFragments.clear();
    if ((!bool) && (this.mCurState >= 1)) {
      if (USE_STATE_MANAGER) {
        for (i = paramInt1; i < paramInt2; i++)
        {
          localObject1 = ((BackStackRecord)paramArrayList.get(i)).mOps.iterator();
          while (((Iterator)localObject1).hasNext())
          {
            localObject2 = ((FragmentTransaction.Op)((Iterator)localObject1).next()).mFragment;
            if ((localObject2 != null) && (((Fragment)localObject2).mFragmentManager != null))
            {
              localObject2 = createOrGetFragmentStateManager((Fragment)localObject2);
              this.mFragmentStore.makeActive((FragmentStateManager)localObject2);
            }
          }
        }
      } else {
        FragmentTransition.startTransitions(this.mHost.getContext(), this.mContainer, paramArrayList, paramArrayList1, paramInt1, paramInt2, false, this.mFragmentTransitionCallback);
      }
    }
    executeOps(paramArrayList, paramArrayList1, paramInt1, paramInt2);
    if (USE_STATE_MANAGER)
    {
      bool = ((Boolean)paramArrayList1.get(paramInt2 - 1)).booleanValue();
      for (i = paramInt1; i < paramInt2; i++)
      {
        localObject2 = (BackStackRecord)paramArrayList.get(i);
        if (bool)
        {
          for (k = ((BackStackRecord)localObject2).mOps.size() - 1; k >= 0; k--)
          {
            localObject1 = ((FragmentTransaction.Op)((BackStackRecord)localObject2).mOps.get(k)).mFragment;
            if (localObject1 != null) {
              createOrGetFragmentStateManager((Fragment)localObject1).moveToExpectedState();
            }
          }
        }
        else
        {
          localObject2 = ((BackStackRecord)localObject2).mOps.iterator();
          while (((Iterator)localObject2).hasNext())
          {
            localObject1 = ((FragmentTransaction.Op)((Iterator)localObject2).next()).mFragment;
            if (localObject1 != null) {
              createOrGetFragmentStateManager((Fragment)localObject1).moveToExpectedState();
            }
          }
        }
      }
      moveToState(this.mCurState, true);
      localObject2 = collectChangedControllers(paramArrayList, paramInt1, paramInt2).iterator();
      while (((Iterator)localObject2).hasNext())
      {
        localObject1 = (SpecialEffectsController)((Iterator)localObject2).next();
        ((SpecialEffectsController)localObject1).updateOperationDirection(bool);
        ((SpecialEffectsController)localObject1).markPostponedState();
        ((SpecialEffectsController)localObject1).executePendingOperations();
      }
      i = paramInt2;
      localObject1 = paramArrayList1;
    }
    else
    {
      if (bool)
      {
        localObject1 = new ArraySet();
        addAddedFragments((ArraySet)localObject1);
        i = postponePostponableTransactions(paramArrayList, paramArrayList1, paramInt1, paramInt2, (ArraySet)localObject1);
        makeRemovedFragmentsInvisible((ArraySet)localObject1);
      }
      else
      {
        i = paramInt2;
      }
      if ((i != paramInt1) && (bool))
      {
        if (this.mCurState >= 1) {
          FragmentTransition.startTransitions(this.mHost.getContext(), this.mContainer, paramArrayList, paramArrayList1, paramInt1, i, true, this.mFragmentTransitionCallback);
        }
        i = paramInt2;
        localObject1 = paramArrayList1;
        moveToState(this.mCurState, true);
      }
      else
      {
        localObject1 = paramArrayList1;
        i = paramInt2;
      }
    }
    while (paramInt1 < paramInt2)
    {
      localObject1 = (BackStackRecord)paramArrayList.get(paramInt1);
      if ((((Boolean)paramArrayList1.get(paramInt1)).booleanValue()) && (((BackStackRecord)localObject1).mIndex >= 0)) {
        ((BackStackRecord)localObject1).mIndex = -1;
      }
      ((BackStackRecord)localObject1).runOnCommitRunnables();
      paramInt1++;
    }
    if (j != 0) {
      reportBackStackChanged();
    }
  }
  
  private void executePostponedTransaction(ArrayList<BackStackRecord> paramArrayList, ArrayList<Boolean> paramArrayList1)
  {
    Object localObject = this.mPostponedTransactions;
    int i;
    if (localObject == null) {
      i = 0;
    } else {
      i = ((ArrayList)localObject).size();
    }
    int j = 0;
    for (int m = i; j < m; m = i)
    {
      localObject = (StartEnterTransitionListener)this.mPostponedTransactions.get(j);
      int k;
      if ((paramArrayList != null) && (!((StartEnterTransitionListener)localObject).mIsBack))
      {
        i = paramArrayList.indexOf(((StartEnterTransitionListener)localObject).mRecord);
        if ((i != -1) && (paramArrayList1 != null) && (((Boolean)paramArrayList1.get(i)).booleanValue()))
        {
          this.mPostponedTransactions.remove(j);
          k = j - 1;
          i = m - 1;
          ((StartEnterTransitionListener)localObject).cancelTransaction();
          break label248;
        }
      }
      if (!((StartEnterTransitionListener)localObject).isReady())
      {
        i = m;
        k = j;
        if (paramArrayList != null)
        {
          i = m;
          k = j;
          if (!((StartEnterTransitionListener)localObject).mRecord.interactsWith(paramArrayList, 0, paramArrayList.size())) {}
        }
      }
      else
      {
        this.mPostponedTransactions.remove(j);
        k = j - 1;
        i = m - 1;
        if ((paramArrayList != null) && (!((StartEnterTransitionListener)localObject).mIsBack))
        {
          j = paramArrayList.indexOf(((StartEnterTransitionListener)localObject).mRecord);
          if ((j != -1) && (paramArrayList1 != null) && (((Boolean)paramArrayList1.get(j)).booleanValue()))
          {
            ((StartEnterTransitionListener)localObject).cancelTransaction();
            break label248;
          }
        }
        ((StartEnterTransitionListener)localObject).completeTransaction();
      }
      label248:
      j = k + 1;
    }
  }
  
  public static <F extends Fragment> F findFragment(View paramView)
  {
    Fragment localFragment = findViewFragment(paramView);
    if (localFragment != null) {
      return localFragment;
    }
    throw new IllegalStateException("View " + paramView + " does not have a Fragment set");
  }
  
  static FragmentManager findFragmentManager(View paramView)
  {
    Object localObject1 = findViewFragment(paramView);
    if (localObject1 != null)
    {
      if (((Fragment)localObject1).isAdded()) {
        paramView = ((Fragment)localObject1).getChildFragmentManager();
      } else {
        throw new IllegalStateException("The Fragment " + localObject1 + " that owns View " + paramView + " has already been destroyed. Nested fragments should always use the child FragmentManager.");
      }
    }
    else
    {
      Context localContext = paramView.getContext();
      Object localObject2 = null;
      for (;;)
      {
        localObject1 = localObject2;
        if (!(localContext instanceof ContextWrapper)) {
          break;
        }
        if ((localContext instanceof FragmentActivity))
        {
          localObject1 = (FragmentActivity)localContext;
          break;
        }
        localContext = ((ContextWrapper)localContext).getBaseContext();
      }
      if (localObject1 == null) {
        break label121;
      }
      paramView = ((FragmentActivity)localObject1).getSupportFragmentManager();
    }
    return paramView;
    label121:
    throw new IllegalStateException("View " + paramView + " is not within a subclass of FragmentActivity.");
  }
  
  private static Fragment findViewFragment(View paramView)
  {
    for (;;)
    {
      Object localObject1 = null;
      if (paramView == null) {
        break;
      }
      Object localObject2 = getViewFragment(paramView);
      if (localObject2 != null) {
        return (Fragment)localObject2;
      }
      localObject2 = paramView.getParent();
      paramView = (View)localObject1;
      if ((localObject2 instanceof View)) {
        paramView = (View)localObject2;
      }
    }
    return null;
  }
  
  private void forcePostponedTransactions()
  {
    if (USE_STATE_MANAGER)
    {
      Iterator localIterator = collectAllSpecialEffectsController().iterator();
      while (localIterator.hasNext()) {
        ((SpecialEffectsController)localIterator.next()).forcePostponedExecutePendingOperations();
      }
    }
    else if (this.mPostponedTransactions != null)
    {
      while (!this.mPostponedTransactions.isEmpty()) {
        ((StartEnterTransitionListener)this.mPostponedTransactions.remove(0)).completeTransaction();
      }
    }
  }
  
  private boolean generateOpsForPendingActions(ArrayList<BackStackRecord> paramArrayList, ArrayList<Boolean> paramArrayList1)
  {
    boolean bool = false;
    synchronized (this.mPendingActions)
    {
      if (this.mPendingActions.isEmpty()) {
        return false;
      }
      int j = this.mPendingActions.size();
      for (int i = 0; i < j; i++) {
        bool |= ((OpGenerator)this.mPendingActions.get(i)).generateOps(paramArrayList, paramArrayList1);
      }
      this.mPendingActions.clear();
      this.mHost.getHandler().removeCallbacks(this.mExecCommit);
      return bool;
    }
  }
  
  private FragmentManagerViewModel getChildNonConfig(Fragment paramFragment)
  {
    return this.mNonConfig.getChildNonConfig(paramFragment);
  }
  
  private ViewGroup getFragmentContainer(Fragment paramFragment)
  {
    if (paramFragment.mContainer != null) {
      return paramFragment.mContainer;
    }
    if (paramFragment.mContainerId <= 0) {
      return null;
    }
    if (this.mContainer.onHasView())
    {
      paramFragment = this.mContainer.onFindViewById(paramFragment.mContainerId);
      if ((paramFragment instanceof ViewGroup)) {
        return (ViewGroup)paramFragment;
      }
    }
    return null;
  }
  
  static Fragment getViewFragment(View paramView)
  {
    paramView = paramView.getTag(R.id.fragment_container_view_tag);
    if ((paramView instanceof Fragment)) {
      return (Fragment)paramView;
    }
    return null;
  }
  
  static boolean isLoggingEnabled(int paramInt)
  {
    boolean bool;
    if ((!DEBUG) && (!Log.isLoggable("FragmentManager", paramInt))) {
      bool = false;
    } else {
      bool = true;
    }
    return bool;
  }
  
  private boolean isMenuAvailable(Fragment paramFragment)
  {
    boolean bool;
    if (((paramFragment.mHasMenu) && (paramFragment.mMenuVisible)) || (paramFragment.mChildFragmentManager.checkForMenus())) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  private void makeRemovedFragmentsInvisible(ArraySet<Fragment> paramArraySet)
  {
    int j = paramArraySet.size();
    for (int i = 0; i < j; i++)
    {
      Fragment localFragment = (Fragment)paramArraySet.valueAt(i);
      if (!localFragment.mAdded)
      {
        View localView = localFragment.requireView();
        localFragment.mPostponedAlpha = localView.getAlpha();
        localView.setAlpha(0.0F);
      }
    }
  }
  
  private boolean popBackStackImmediate(String paramString, int paramInt1, int paramInt2)
  {
    execPendingActions(false);
    ensureExecReady(true);
    Fragment localFragment = this.mPrimaryNav;
    if ((localFragment != null) && (paramInt1 < 0) && (paramString == null) && (localFragment.getChildFragmentManager().popBackStackImmediate())) {
      return true;
    }
    boolean bool = popBackStackState(this.mTmpRecords, this.mTmpIsPop, paramString, paramInt1, paramInt2);
    if (bool) {
      this.mExecutingActions = true;
    }
    try
    {
      removeRedundantOperationsAndExecute(this.mTmpRecords, this.mTmpIsPop);
      cleanupExec();
    }
    finally
    {
      cleanupExec();
    }
    doPendingDeferredStart();
    this.mFragmentStore.burpActive();
    return bool;
  }
  
  private int postponePostponableTransactions(ArrayList<BackStackRecord> paramArrayList, ArrayList<Boolean> paramArrayList1, int paramInt1, int paramInt2, ArraySet<Fragment> paramArraySet)
  {
    int j = paramInt2;
    int i = paramInt2 - 1;
    while (i >= paramInt1)
    {
      BackStackRecord localBackStackRecord = (BackStackRecord)paramArrayList.get(i);
      boolean bool = ((Boolean)paramArrayList1.get(i)).booleanValue();
      int m;
      if ((localBackStackRecord.isPostponed()) && (!localBackStackRecord.interactsWith(paramArrayList, i + 1, paramInt2))) {
        m = 1;
      } else {
        m = 0;
      }
      int k = j;
      if (m != 0)
      {
        if (this.mPostponedTransactions == null) {
          this.mPostponedTransactions = new ArrayList();
        }
        StartEnterTransitionListener localStartEnterTransitionListener = new StartEnterTransitionListener(localBackStackRecord, bool);
        this.mPostponedTransactions.add(localStartEnterTransitionListener);
        localBackStackRecord.setOnStartPostponedListener(localStartEnterTransitionListener);
        if (bool) {
          localBackStackRecord.executeOps();
        } else {
          localBackStackRecord.executePopOps(false);
        }
        k = j - 1;
        if (i != k)
        {
          paramArrayList.remove(i);
          paramArrayList.add(k, localBackStackRecord);
        }
        addAddedFragments(paramArraySet);
      }
      i--;
      j = k;
    }
    return j;
  }
  
  private void removeRedundantOperationsAndExecute(ArrayList<BackStackRecord> paramArrayList, ArrayList<Boolean> paramArrayList1)
  {
    if (paramArrayList.isEmpty()) {
      return;
    }
    if (paramArrayList.size() == paramArrayList1.size())
    {
      executePostponedTransaction(paramArrayList, paramArrayList1);
      int n = paramArrayList.size();
      int k = 0;
      int i = 0;
      while (i < n)
      {
        int m = k;
        int j = i;
        if (!((BackStackRecord)paramArrayList.get(i)).mReorderingAllowed)
        {
          if (k != i) {
            executeOpsTogether(paramArrayList, paramArrayList1, k, i);
          }
          k = i + 1;
          j = k;
          if (((Boolean)paramArrayList1.get(i)).booleanValue()) {
            for (;;)
            {
              j = k;
              if (k >= n) {
                break;
              }
              j = k;
              if (!((Boolean)paramArrayList1.get(k)).booleanValue()) {
                break;
              }
              j = k;
              if (((BackStackRecord)paramArrayList.get(k)).mReorderingAllowed) {
                break;
              }
              k++;
            }
          }
          executeOpsTogether(paramArrayList, paramArrayList1, i, j);
          m = j;
          j--;
        }
        i = j + 1;
        k = m;
      }
      if (k != n) {
        executeOpsTogether(paramArrayList, paramArrayList1, k, n);
      }
      return;
    }
    throw new IllegalStateException("Internal error with the back stack records");
  }
  
  private void reportBackStackChanged()
  {
    if (this.mBackStackChangeListeners != null) {
      for (int i = 0; i < this.mBackStackChangeListeners.size(); i++) {
        ((OnBackStackChangedListener)this.mBackStackChangeListeners.get(i)).onBackStackChanged();
      }
    }
  }
  
  static int reverseTransit(int paramInt)
  {
    int i = 0;
    switch (paramInt)
    {
    default: 
      paramInt = i;
      break;
    case 8194: 
      paramInt = 4097;
      break;
    case 4099: 
      paramInt = 4099;
      break;
    case 4097: 
      paramInt = 8194;
    }
    return paramInt;
  }
  
  private void setVisibleRemovingFragment(Fragment paramFragment)
  {
    ViewGroup localViewGroup = getFragmentContainer(paramFragment);
    if ((localViewGroup != null) && (paramFragment.getEnterAnim() + paramFragment.getExitAnim() + paramFragment.getPopEnterAnim() + paramFragment.getPopExitAnim() > 0))
    {
      if (localViewGroup.getTag(R.id.visible_removing_fragment_view_tag) == null) {
        localViewGroup.setTag(R.id.visible_removing_fragment_view_tag, paramFragment);
      }
      ((Fragment)localViewGroup.getTag(R.id.visible_removing_fragment_view_tag)).setPopDirection(paramFragment.getPopDirection());
    }
  }
  
  private void startPendingDeferredFragments()
  {
    Iterator localIterator = this.mFragmentStore.getActiveFragmentStateManagers().iterator();
    while (localIterator.hasNext()) {
      performPendingDeferredStart((FragmentStateManager)localIterator.next());
    }
  }
  
  private void throwException(RuntimeException paramRuntimeException)
  {
    Log.e("FragmentManager", paramRuntimeException.getMessage());
    Log.e("FragmentManager", "Activity state:");
    PrintWriter localPrintWriter = new PrintWriter(new LogWriter("FragmentManager"));
    FragmentHostCallback localFragmentHostCallback = this.mHost;
    if (localFragmentHostCallback != null) {
      try
      {
        localFragmentHostCallback.onDump("  ", null, localPrintWriter, new String[0]);
      }
      catch (Exception localException1)
      {
        Log.e("FragmentManager", "Failed dumping state", localException1);
      }
    } else {
      try
      {
        dump("  ", null, localPrintWriter, new String[0]);
      }
      catch (Exception localException2)
      {
        Log.e("FragmentManager", "Failed dumping state", localException2);
      }
    }
    throw paramRuntimeException;
  }
  
  private void updateOnBackPressedCallbackEnabled()
  {
    synchronized (this.mPendingActions)
    {
      boolean bool2 = this.mPendingActions.isEmpty();
      boolean bool1 = true;
      if (!bool2)
      {
        this.mOnBackPressedCallback.setEnabled(true);
        return;
      }
      OnBackPressedCallback localOnBackPressedCallback = this.mOnBackPressedCallback;
      if ((getBackStackEntryCount() <= 0) || (!isPrimaryNavigation(this.mParent))) {
        bool1 = false;
      }
      localOnBackPressedCallback.setEnabled(bool1);
      return;
    }
  }
  
  void addBackStackState(BackStackRecord paramBackStackRecord)
  {
    if (this.mBackStack == null) {
      this.mBackStack = new ArrayList();
    }
    this.mBackStack.add(paramBackStackRecord);
  }
  
  void addCancellationSignal(Fragment paramFragment, CancellationSignal paramCancellationSignal)
  {
    if (this.mExitAnimationCancellationSignals.get(paramFragment) == null) {
      this.mExitAnimationCancellationSignals.put(paramFragment, new HashSet());
    }
    ((HashSet)this.mExitAnimationCancellationSignals.get(paramFragment)).add(paramCancellationSignal);
  }
  
  FragmentStateManager addFragment(Fragment paramFragment)
  {
    if (isLoggingEnabled(2)) {
      Log.v("FragmentManager", "add: " + paramFragment);
    }
    FragmentStateManager localFragmentStateManager = createOrGetFragmentStateManager(paramFragment);
    paramFragment.mFragmentManager = this;
    this.mFragmentStore.makeActive(localFragmentStateManager);
    if (!paramFragment.mDetached)
    {
      this.mFragmentStore.addFragment(paramFragment);
      paramFragment.mRemoving = false;
      if (paramFragment.mView == null) {
        paramFragment.mHiddenChanged = false;
      }
      if (isMenuAvailable(paramFragment)) {
        this.mNeedMenuInvalidate = true;
      }
    }
    return localFragmentStateManager;
  }
  
  public void addFragmentOnAttachListener(FragmentOnAttachListener paramFragmentOnAttachListener)
  {
    this.mOnAttachListeners.add(paramFragmentOnAttachListener);
  }
  
  public void addOnBackStackChangedListener(OnBackStackChangedListener paramOnBackStackChangedListener)
  {
    if (this.mBackStackChangeListeners == null) {
      this.mBackStackChangeListeners = new ArrayList();
    }
    this.mBackStackChangeListeners.add(paramOnBackStackChangedListener);
  }
  
  void addRetainedFragment(Fragment paramFragment)
  {
    this.mNonConfig.addRetainedFragment(paramFragment);
  }
  
  int allocBackStackIndex()
  {
    return this.mBackStackIndex.getAndIncrement();
  }
  
  void attachController(FragmentHostCallback<?> paramFragmentHostCallback, FragmentContainer paramFragmentContainer, final Fragment paramFragment)
  {
    if (this.mHost == null)
    {
      this.mHost = paramFragmentHostCallback;
      this.mContainer = paramFragmentContainer;
      this.mParent = paramFragment;
      if (paramFragment != null) {
        addFragmentOnAttachListener(new FragmentOnAttachListener()
        {
          public void onAttachFragment(FragmentManager paramAnonymousFragmentManager, Fragment paramAnonymousFragment)
          {
            paramFragment.onAttachFragment(paramAnonymousFragment);
          }
        });
      } else if ((paramFragmentHostCallback instanceof FragmentOnAttachListener)) {
        addFragmentOnAttachListener((FragmentOnAttachListener)paramFragmentHostCallback);
      }
      if (this.mParent != null) {
        updateOnBackPressedCallbackEnabled();
      }
      if ((paramFragmentHostCallback instanceof OnBackPressedDispatcherOwner))
      {
        paramFragmentContainer = (OnBackPressedDispatcherOwner)paramFragmentHostCallback;
        OnBackPressedDispatcher localOnBackPressedDispatcher = paramFragmentContainer.getOnBackPressedDispatcher();
        this.mOnBackPressedDispatcher = localOnBackPressedDispatcher;
        if (paramFragment != null) {
          paramFragmentContainer = paramFragment;
        }
        localOnBackPressedDispatcher.addCallback(paramFragmentContainer, this.mOnBackPressedCallback);
      }
      if (paramFragment != null) {
        this.mNonConfig = paramFragment.mFragmentManager.getChildNonConfig(paramFragment);
      } else if ((paramFragmentHostCallback instanceof ViewModelStoreOwner)) {
        this.mNonConfig = FragmentManagerViewModel.getInstance(((ViewModelStoreOwner)paramFragmentHostCallback).getViewModelStore());
      } else {
        this.mNonConfig = new FragmentManagerViewModel(false);
      }
      this.mNonConfig.setIsStateSaved(isStateSaved());
      this.mFragmentStore.setNonConfig(this.mNonConfig);
      paramFragmentHostCallback = this.mHost;
      if ((paramFragmentHostCallback instanceof ActivityResultRegistryOwner))
      {
        paramFragmentContainer = ((ActivityResultRegistryOwner)paramFragmentHostCallback).getActivityResultRegistry();
        if (paramFragment != null) {
          paramFragmentHostCallback = paramFragment.mWho + ":";
        } else {
          paramFragmentHostCallback = "";
        }
        paramFragmentHostCallback = "FragmentManager:" + paramFragmentHostCallback;
        this.mStartActivityForResult = paramFragmentContainer.register(paramFragmentHostCallback + "StartActivityForResult", new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback()
        {
          public void onActivityResult(ActivityResult paramAnonymousActivityResult)
          {
            Object localObject = (FragmentManager.LaunchedFragmentInfo)FragmentManager.this.mLaunchedFragments.pollFirst();
            if (localObject == null)
            {
              Log.w("FragmentManager", "No Activities were started for result for " + this);
              return;
            }
            String str = ((FragmentManager.LaunchedFragmentInfo)localObject).mWho;
            int i = ((FragmentManager.LaunchedFragmentInfo)localObject).mRequestCode;
            localObject = FragmentManager.this.mFragmentStore.findFragmentByWho(str);
            if (localObject == null)
            {
              Log.w("FragmentManager", "Activity result delivered for unknown Fragment " + str);
              return;
            }
            ((Fragment)localObject).onActivityResult(i, paramAnonymousActivityResult.getResultCode(), paramAnonymousActivityResult.getData());
          }
        });
        this.mStartIntentSenderForResult = paramFragmentContainer.register(paramFragmentHostCallback + "StartIntentSenderForResult", new FragmentIntentSenderContract(), new ActivityResultCallback()
        {
          public void onActivityResult(ActivityResult paramAnonymousActivityResult)
          {
            Object localObject = (FragmentManager.LaunchedFragmentInfo)FragmentManager.this.mLaunchedFragments.pollFirst();
            if (localObject == null)
            {
              Log.w("FragmentManager", "No IntentSenders were started for " + this);
              return;
            }
            String str = ((FragmentManager.LaunchedFragmentInfo)localObject).mWho;
            int i = ((FragmentManager.LaunchedFragmentInfo)localObject).mRequestCode;
            localObject = FragmentManager.this.mFragmentStore.findFragmentByWho(str);
            if (localObject == null)
            {
              Log.w("FragmentManager", "Intent Sender result delivered for unknown Fragment " + str);
              return;
            }
            ((Fragment)localObject).onActivityResult(i, paramAnonymousActivityResult.getResultCode(), paramAnonymousActivityResult.getData());
          }
        });
        this.mRequestPermissions = paramFragmentContainer.register(paramFragmentHostCallback + "RequestPermissions", new ActivityResultContracts.RequestMultiplePermissions(), new ActivityResultCallback()
        {
          public void onActivityResult(Map<String, Boolean> paramAnonymousMap)
          {
            String[] arrayOfString = (String[])paramAnonymousMap.keySet().toArray(new String[0]);
            Object localObject1 = new ArrayList(paramAnonymousMap.values());
            paramAnonymousMap = new int[((ArrayList)localObject1).size()];
            for (int i = 0; i < ((ArrayList)localObject1).size(); i++)
            {
              int j;
              if (((Boolean)((ArrayList)localObject1).get(i)).booleanValue()) {
                j = 0;
              } else {
                j = -1;
              }
              paramAnonymousMap[i] = j;
            }
            Object localObject2 = (FragmentManager.LaunchedFragmentInfo)FragmentManager.this.mLaunchedFragments.pollFirst();
            if (localObject2 == null)
            {
              Log.w("FragmentManager", "No permissions were requested for " + this);
              return;
            }
            localObject1 = ((FragmentManager.LaunchedFragmentInfo)localObject2).mWho;
            i = ((FragmentManager.LaunchedFragmentInfo)localObject2).mRequestCode;
            localObject2 = FragmentManager.this.mFragmentStore.findFragmentByWho((String)localObject1);
            if (localObject2 == null)
            {
              Log.w("FragmentManager", "Permission request result delivered for unknown Fragment " + (String)localObject1);
              return;
            }
            ((Fragment)localObject2).onRequestPermissionsResult(i, arrayOfString, paramAnonymousMap);
          }
        });
      }
      return;
    }
    throw new IllegalStateException("Already attached");
  }
  
  void attachFragment(Fragment paramFragment)
  {
    if (isLoggingEnabled(2)) {
      Log.v("FragmentManager", "attach: " + paramFragment);
    }
    if (paramFragment.mDetached)
    {
      paramFragment.mDetached = false;
      if (!paramFragment.mAdded)
      {
        this.mFragmentStore.addFragment(paramFragment);
        if (isLoggingEnabled(2)) {
          Log.v("FragmentManager", "add from attach: " + paramFragment);
        }
        if (isMenuAvailable(paramFragment)) {
          this.mNeedMenuInvalidate = true;
        }
      }
    }
  }
  
  public FragmentTransaction beginTransaction()
  {
    return new BackStackRecord(this);
  }
  
  boolean checkForMenus()
  {
    boolean bool = false;
    Iterator localIterator = this.mFragmentStore.getActiveFragments().iterator();
    while (localIterator.hasNext())
    {
      Fragment localFragment = (Fragment)localIterator.next();
      if (localFragment != null) {
        bool = isMenuAvailable(localFragment);
      }
      if (bool) {
        return true;
      }
    }
    return false;
  }
  
  public final void clearFragmentResult(String paramString)
  {
    this.mResults.remove(paramString);
  }
  
  public final void clearFragmentResultListener(String paramString)
  {
    paramString = (LifecycleAwareResultListener)this.mResultListeners.remove(paramString);
    if (paramString != null) {
      paramString.removeObserver();
    }
  }
  
  void completeExecute(BackStackRecord paramBackStackRecord, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3)
  {
    if (paramBoolean1) {
      paramBackStackRecord.executePopOps(paramBoolean3);
    } else {
      paramBackStackRecord.executeOps();
    }
    Object localObject2 = new ArrayList(1);
    Object localObject1 = new ArrayList(1);
    ((ArrayList)localObject2).add(paramBackStackRecord);
    ((ArrayList)localObject1).add(Boolean.valueOf(paramBoolean1));
    if ((paramBoolean2) && (this.mCurState >= 1)) {
      FragmentTransition.startTransitions(this.mHost.getContext(), this.mContainer, (ArrayList)localObject2, (ArrayList)localObject1, 0, 1, true, this.mFragmentTransitionCallback);
    }
    if (paramBoolean3) {
      moveToState(this.mCurState, true);
    }
    localObject2 = this.mFragmentStore.getActiveFragments().iterator();
    while (((Iterator)localObject2).hasNext())
    {
      localObject1 = (Fragment)((Iterator)localObject2).next();
      if ((localObject1 != null) && (((Fragment)localObject1).mView != null) && (((Fragment)localObject1).mIsNewlyAdded) && (paramBackStackRecord.interactsWith(((Fragment)localObject1).mContainerId)))
      {
        if (((Fragment)localObject1).mPostponedAlpha > 0.0F) {
          ((Fragment)localObject1).mView.setAlpha(((Fragment)localObject1).mPostponedAlpha);
        }
        if (paramBoolean3)
        {
          ((Fragment)localObject1).mPostponedAlpha = 0.0F;
        }
        else
        {
          ((Fragment)localObject1).mPostponedAlpha = -1.0F;
          ((Fragment)localObject1).mIsNewlyAdded = false;
        }
      }
    }
  }
  
  FragmentStateManager createOrGetFragmentStateManager(Fragment paramFragment)
  {
    FragmentStateManager localFragmentStateManager = this.mFragmentStore.getFragmentStateManager(paramFragment.mWho);
    if (localFragmentStateManager != null) {
      return localFragmentStateManager;
    }
    paramFragment = new FragmentStateManager(this.mLifecycleCallbacksDispatcher, this.mFragmentStore, paramFragment);
    paramFragment.restoreState(this.mHost.getContext().getClassLoader());
    paramFragment.setFragmentManagerState(this.mCurState);
    return paramFragment;
  }
  
  void detachFragment(Fragment paramFragment)
  {
    if (isLoggingEnabled(2)) {
      Log.v("FragmentManager", "detach: " + paramFragment);
    }
    if (!paramFragment.mDetached)
    {
      paramFragment.mDetached = true;
      if (paramFragment.mAdded)
      {
        if (isLoggingEnabled(2)) {
          Log.v("FragmentManager", "remove from detach: " + paramFragment);
        }
        this.mFragmentStore.removeFragment(paramFragment);
        if (isMenuAvailable(paramFragment)) {
          this.mNeedMenuInvalidate = true;
        }
        setVisibleRemovingFragment(paramFragment);
      }
    }
  }
  
  void dispatchActivityCreated()
  {
    this.mStateSaved = false;
    this.mStopped = false;
    this.mNonConfig.setIsStateSaved(false);
    dispatchStateChange(4);
  }
  
  void dispatchAttach()
  {
    this.mStateSaved = false;
    this.mStopped = false;
    this.mNonConfig.setIsStateSaved(false);
    dispatchStateChange(0);
  }
  
  void dispatchConfigurationChanged(Configuration paramConfiguration)
  {
    Iterator localIterator = this.mFragmentStore.getFragments().iterator();
    while (localIterator.hasNext())
    {
      Fragment localFragment = (Fragment)localIterator.next();
      if (localFragment != null) {
        localFragment.performConfigurationChanged(paramConfiguration);
      }
    }
  }
  
  boolean dispatchContextItemSelected(MenuItem paramMenuItem)
  {
    if (this.mCurState < 1) {
      return false;
    }
    Iterator localIterator = this.mFragmentStore.getFragments().iterator();
    while (localIterator.hasNext())
    {
      Fragment localFragment = (Fragment)localIterator.next();
      if ((localFragment != null) && (localFragment.performContextItemSelected(paramMenuItem))) {
        return true;
      }
    }
    return false;
  }
  
  void dispatchCreate()
  {
    this.mStateSaved = false;
    this.mStopped = false;
    this.mNonConfig.setIsStateSaved(false);
    dispatchStateChange(1);
  }
  
  boolean dispatchCreateOptionsMenu(Menu paramMenu, MenuInflater paramMenuInflater)
  {
    if (this.mCurState < 1) {
      return false;
    }
    boolean bool1 = false;
    Object localObject1 = null;
    Iterator localIterator = this.mFragmentStore.getFragments().iterator();
    while (localIterator.hasNext())
    {
      Fragment localFragment = (Fragment)localIterator.next();
      boolean bool2 = bool1;
      Object localObject2 = localObject1;
      if (localFragment != null)
      {
        bool2 = bool1;
        localObject2 = localObject1;
        if (isParentMenuVisible(localFragment))
        {
          bool2 = bool1;
          localObject2 = localObject1;
          if (localFragment.performCreateOptionsMenu(paramMenu, paramMenuInflater))
          {
            bool2 = true;
            localObject2 = localObject1;
            if (localObject1 == null) {
              localObject2 = new ArrayList();
            }
            ((ArrayList)localObject2).add(localFragment);
          }
        }
      }
      bool1 = bool2;
      localObject1 = localObject2;
    }
    if (this.mCreatedMenus != null) {
      for (int i = 0; i < this.mCreatedMenus.size(); i++)
      {
        paramMenu = (Fragment)this.mCreatedMenus.get(i);
        if ((localObject1 == null) || (!((ArrayList)localObject1).contains(paramMenu))) {
          paramMenu.onDestroyOptionsMenu();
        }
      }
    }
    this.mCreatedMenus = ((ArrayList)localObject1);
    return bool1;
  }
  
  void dispatchDestroy()
  {
    this.mDestroyed = true;
    execPendingActions(true);
    endAnimatingAwayFragments();
    dispatchStateChange(-1);
    this.mHost = null;
    this.mContainer = null;
    this.mParent = null;
    if (this.mOnBackPressedDispatcher != null)
    {
      this.mOnBackPressedCallback.remove();
      this.mOnBackPressedDispatcher = null;
    }
    ActivityResultLauncher localActivityResultLauncher = this.mStartActivityForResult;
    if (localActivityResultLauncher != null)
    {
      localActivityResultLauncher.unregister();
      this.mStartIntentSenderForResult.unregister();
      this.mRequestPermissions.unregister();
    }
  }
  
  void dispatchDestroyView()
  {
    dispatchStateChange(1);
  }
  
  void dispatchLowMemory()
  {
    Iterator localIterator = this.mFragmentStore.getFragments().iterator();
    while (localIterator.hasNext())
    {
      Fragment localFragment = (Fragment)localIterator.next();
      if (localFragment != null) {
        localFragment.performLowMemory();
      }
    }
  }
  
  void dispatchMultiWindowModeChanged(boolean paramBoolean)
  {
    Iterator localIterator = this.mFragmentStore.getFragments().iterator();
    while (localIterator.hasNext())
    {
      Fragment localFragment = (Fragment)localIterator.next();
      if (localFragment != null) {
        localFragment.performMultiWindowModeChanged(paramBoolean);
      }
    }
  }
  
  void dispatchOnAttachFragment(Fragment paramFragment)
  {
    Iterator localIterator = this.mOnAttachListeners.iterator();
    while (localIterator.hasNext()) {
      ((FragmentOnAttachListener)localIterator.next()).onAttachFragment(this, paramFragment);
    }
  }
  
  boolean dispatchOptionsItemSelected(MenuItem paramMenuItem)
  {
    if (this.mCurState < 1) {
      return false;
    }
    Iterator localIterator = this.mFragmentStore.getFragments().iterator();
    while (localIterator.hasNext())
    {
      Fragment localFragment = (Fragment)localIterator.next();
      if ((localFragment != null) && (localFragment.performOptionsItemSelected(paramMenuItem))) {
        return true;
      }
    }
    return false;
  }
  
  void dispatchOptionsMenuClosed(Menu paramMenu)
  {
    if (this.mCurState < 1) {
      return;
    }
    Iterator localIterator = this.mFragmentStore.getFragments().iterator();
    while (localIterator.hasNext())
    {
      Fragment localFragment = (Fragment)localIterator.next();
      if (localFragment != null) {
        localFragment.performOptionsMenuClosed(paramMenu);
      }
    }
  }
  
  void dispatchPause()
  {
    dispatchStateChange(5);
  }
  
  void dispatchPictureInPictureModeChanged(boolean paramBoolean)
  {
    Iterator localIterator = this.mFragmentStore.getFragments().iterator();
    while (localIterator.hasNext())
    {
      Fragment localFragment = (Fragment)localIterator.next();
      if (localFragment != null) {
        localFragment.performPictureInPictureModeChanged(paramBoolean);
      }
    }
  }
  
  boolean dispatchPrepareOptionsMenu(Menu paramMenu)
  {
    if (this.mCurState < 1) {
      return false;
    }
    boolean bool1 = false;
    Iterator localIterator = this.mFragmentStore.getFragments().iterator();
    while (localIterator.hasNext())
    {
      Fragment localFragment = (Fragment)localIterator.next();
      boolean bool2 = bool1;
      if (localFragment != null)
      {
        bool2 = bool1;
        if (isParentMenuVisible(localFragment))
        {
          bool2 = bool1;
          if (localFragment.performPrepareOptionsMenu(paramMenu)) {
            bool2 = true;
          }
        }
      }
      bool1 = bool2;
    }
    return bool1;
  }
  
  void dispatchPrimaryNavigationFragmentChanged()
  {
    updateOnBackPressedCallbackEnabled();
    dispatchParentPrimaryNavigationFragmentChanged(this.mPrimaryNav);
  }
  
  void dispatchResume()
  {
    this.mStateSaved = false;
    this.mStopped = false;
    this.mNonConfig.setIsStateSaved(false);
    dispatchStateChange(7);
  }
  
  void dispatchStart()
  {
    this.mStateSaved = false;
    this.mStopped = false;
    this.mNonConfig.setIsStateSaved(false);
    dispatchStateChange(5);
  }
  
  void dispatchStop()
  {
    this.mStopped = true;
    this.mNonConfig.setIsStateSaved(true);
    dispatchStateChange(4);
  }
  
  void dispatchViewCreated()
  {
    dispatchStateChange(2);
  }
  
  public void dump(String paramString, FileDescriptor arg2, PrintWriter paramPrintWriter, String[] paramArrayOfString)
  {
    String str = paramString + "    ";
    this.mFragmentStore.dump(paramString, ???, paramPrintWriter, paramArrayOfString);
    ??? = this.mCreatedMenus;
    int j;
    int i;
    if (??? != null)
    {
      j = ???.size();
      if (j > 0)
      {
        paramPrintWriter.print(paramString);
        paramPrintWriter.println("Fragments Created Menus:");
        for (i = 0; i < j; i++)
        {
          ??? = (Fragment)this.mCreatedMenus.get(i);
          paramPrintWriter.print(paramString);
          paramPrintWriter.print("  #");
          paramPrintWriter.print(i);
          paramPrintWriter.print(": ");
          paramPrintWriter.println(???.toString());
        }
      }
    }
    ??? = this.mBackStack;
    if (??? != null)
    {
      j = ???.size();
      if (j > 0)
      {
        paramPrintWriter.print(paramString);
        paramPrintWriter.println("Back Stack:");
        for (i = 0; i < j; i++)
        {
          ??? = (BackStackRecord)this.mBackStack.get(i);
          paramPrintWriter.print(paramString);
          paramPrintWriter.print("  #");
          paramPrintWriter.print(i);
          paramPrintWriter.print(": ");
          paramPrintWriter.println(???.toString());
          ???.dump(str, paramPrintWriter);
        }
      }
    }
    paramPrintWriter.print(paramString);
    paramPrintWriter.println("Back Stack Index: " + this.mBackStackIndex.get());
    synchronized (this.mPendingActions)
    {
      j = this.mPendingActions.size();
      if (j > 0)
      {
        paramPrintWriter.print(paramString);
        paramPrintWriter.println("Pending Actions:");
        for (i = 0; i < j; i++)
        {
          paramArrayOfString = (OpGenerator)this.mPendingActions.get(i);
          paramPrintWriter.print(paramString);
          paramPrintWriter.print("  #");
          paramPrintWriter.print(i);
          paramPrintWriter.print(": ");
          paramPrintWriter.println(paramArrayOfString);
        }
      }
      paramPrintWriter.print(paramString);
      paramPrintWriter.println("FragmentManager misc state:");
      paramPrintWriter.print(paramString);
      paramPrintWriter.print("  mHost=");
      paramPrintWriter.println(this.mHost);
      paramPrintWriter.print(paramString);
      paramPrintWriter.print("  mContainer=");
      paramPrintWriter.println(this.mContainer);
      if (this.mParent != null)
      {
        paramPrintWriter.print(paramString);
        paramPrintWriter.print("  mParent=");
        paramPrintWriter.println(this.mParent);
      }
      paramPrintWriter.print(paramString);
      paramPrintWriter.print("  mCurState=");
      paramPrintWriter.print(this.mCurState);
      paramPrintWriter.print(" mStateSaved=");
      paramPrintWriter.print(this.mStateSaved);
      paramPrintWriter.print(" mStopped=");
      paramPrintWriter.print(this.mStopped);
      paramPrintWriter.print(" mDestroyed=");
      paramPrintWriter.println(this.mDestroyed);
      if (this.mNeedMenuInvalidate)
      {
        paramPrintWriter.print(paramString);
        paramPrintWriter.print("  mNeedMenuInvalidate=");
        paramPrintWriter.println(this.mNeedMenuInvalidate);
      }
      return;
    }
  }
  
  void enqueueAction(OpGenerator paramOpGenerator, boolean paramBoolean)
  {
    if (!paramBoolean)
    {
      if (this.mHost == null)
      {
        if (this.mDestroyed) {
          throw new IllegalStateException("FragmentManager has been destroyed");
        }
        throw new IllegalStateException("FragmentManager has not been attached to a host.");
      }
      checkStateLoss();
    }
    synchronized (this.mPendingActions)
    {
      if (this.mHost == null)
      {
        if (paramBoolean) {
          return;
        }
        paramOpGenerator = new java/lang/IllegalStateException;
        paramOpGenerator.<init>("Activity has been destroyed");
        throw paramOpGenerator;
      }
      this.mPendingActions.add(paramOpGenerator);
      scheduleCommit();
      return;
    }
  }
  
  /* Error */
  boolean execPendingActions(boolean paramBoolean)
  {
    // Byte code:
    //   0: aload_0
    //   1: iload_1
    //   2: invokespecial 848	androidx/fragment/app/FragmentManager:ensureExecReady	(Z)V
    //   5: iconst_0
    //   6: istore_1
    //   7: aload_0
    //   8: aload_0
    //   9: getfield 334	androidx/fragment/app/FragmentManager:mTmpRecords	Ljava/util/ArrayList;
    //   12: aload_0
    //   13: getfield 331	androidx/fragment/app/FragmentManager:mTmpIsPop	Ljava/util/ArrayList;
    //   16: invokespecial 1311	androidx/fragment/app/FragmentManager:generateOpsForPendingActions	(Ljava/util/ArrayList;Ljava/util/ArrayList;)Z
    //   19: ifeq +36 -> 55
    //   22: aload_0
    //   23: iconst_1
    //   24: putfield 329	androidx/fragment/app/FragmentManager:mExecutingActions	Z
    //   27: aload_0
    //   28: aload_0
    //   29: getfield 334	androidx/fragment/app/FragmentManager:mTmpRecords	Ljava/util/ArrayList;
    //   32: aload_0
    //   33: getfield 331	androidx/fragment/app/FragmentManager:mTmpIsPop	Ljava/util/ArrayList;
    //   36: invokespecial 859	androidx/fragment/app/FragmentManager:removeRedundantOperationsAndExecute	(Ljava/util/ArrayList;Ljava/util/ArrayList;)V
    //   39: aload_0
    //   40: invokespecial 861	androidx/fragment/app/FragmentManager:cleanupExec	()V
    //   43: iconst_1
    //   44: istore_1
    //   45: goto -38 -> 7
    //   48: astore_2
    //   49: aload_0
    //   50: invokespecial 861	androidx/fragment/app/FragmentManager:cleanupExec	()V
    //   53: aload_2
    //   54: athrow
    //   55: aload_0
    //   56: invokespecial 864	androidx/fragment/app/FragmentManager:updateOnBackPressedCallbackEnabled	()V
    //   59: aload_0
    //   60: invokespecial 866	androidx/fragment/app/FragmentManager:doPendingDeferredStart	()V
    //   63: aload_0
    //   64: getfield 165	androidx/fragment/app/FragmentManager:mFragmentStore	Landroidx/fragment/app/FragmentStore;
    //   67: invokevirtual 869	androidx/fragment/app/FragmentStore:burpActive	()V
    //   70: iload_1
    //   71: ireturn
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	72	0	this	FragmentManager
    //   0	72	1	paramBoolean	boolean
    //   48	6	2	localObject	Object
    // Exception table:
    //   from	to	target	type
    //   27	39	48	finally
  }
  
  void execSingleAction(OpGenerator paramOpGenerator, boolean paramBoolean)
  {
    if ((paramBoolean) && ((this.mHost == null) || (this.mDestroyed))) {
      return;
    }
    ensureExecReady(paramBoolean);
    if (paramOpGenerator.generateOps(this.mTmpRecords, this.mTmpIsPop)) {
      this.mExecutingActions = true;
    }
    try
    {
      removeRedundantOperationsAndExecute(this.mTmpRecords, this.mTmpIsPop);
      cleanupExec();
    }
    finally
    {
      cleanupExec();
    }
    doPendingDeferredStart();
    this.mFragmentStore.burpActive();
  }
  
  public boolean executePendingTransactions()
  {
    boolean bool = execPendingActions(true);
    forcePostponedTransactions();
    return bool;
  }
  
  Fragment findActiveFragment(String paramString)
  {
    return this.mFragmentStore.findActiveFragment(paramString);
  }
  
  public Fragment findFragmentById(int paramInt)
  {
    return this.mFragmentStore.findFragmentById(paramInt);
  }
  
  public Fragment findFragmentByTag(String paramString)
  {
    return this.mFragmentStore.findFragmentByTag(paramString);
  }
  
  Fragment findFragmentByWho(String paramString)
  {
    return this.mFragmentStore.findFragmentByWho(paramString);
  }
  
  int getActiveFragmentCount()
  {
    return this.mFragmentStore.getActiveFragmentCount();
  }
  
  List<Fragment> getActiveFragments()
  {
    return this.mFragmentStore.getActiveFragments();
  }
  
  public BackStackEntry getBackStackEntryAt(int paramInt)
  {
    return (BackStackEntry)this.mBackStack.get(paramInt);
  }
  
  public int getBackStackEntryCount()
  {
    ArrayList localArrayList = this.mBackStack;
    int i;
    if (localArrayList != null) {
      i = localArrayList.size();
    } else {
      i = 0;
    }
    return i;
  }
  
  FragmentContainer getContainer()
  {
    return this.mContainer;
  }
  
  public Fragment getFragment(Bundle paramBundle, String paramString)
  {
    paramBundle = paramBundle.getString(paramString);
    if (paramBundle == null) {
      return null;
    }
    Fragment localFragment = findActiveFragment(paramBundle);
    if (localFragment == null) {
      throwException(new IllegalStateException("Fragment no longer exists for key " + paramString + ": unique id " + paramBundle));
    }
    return localFragment;
  }
  
  public FragmentFactory getFragmentFactory()
  {
    Object localObject = this.mFragmentFactory;
    if (localObject != null) {
      return (FragmentFactory)localObject;
    }
    localObject = this.mParent;
    if (localObject != null) {
      return ((Fragment)localObject).mFragmentManager.getFragmentFactory();
    }
    return this.mHostFragmentFactory;
  }
  
  FragmentStore getFragmentStore()
  {
    return this.mFragmentStore;
  }
  
  public List<Fragment> getFragments()
  {
    return this.mFragmentStore.getFragments();
  }
  
  FragmentHostCallback<?> getHost()
  {
    return this.mHost;
  }
  
  LayoutInflater.Factory2 getLayoutInflaterFactory()
  {
    return this.mLayoutInflaterFactory;
  }
  
  FragmentLifecycleCallbacksDispatcher getLifecycleCallbacksDispatcher()
  {
    return this.mLifecycleCallbacksDispatcher;
  }
  
  Fragment getParent()
  {
    return this.mParent;
  }
  
  public Fragment getPrimaryNavigationFragment()
  {
    return this.mPrimaryNav;
  }
  
  SpecialEffectsControllerFactory getSpecialEffectsControllerFactory()
  {
    Object localObject = this.mSpecialEffectsControllerFactory;
    if (localObject != null) {
      return (SpecialEffectsControllerFactory)localObject;
    }
    localObject = this.mParent;
    if (localObject != null) {
      return ((Fragment)localObject).mFragmentManager.getSpecialEffectsControllerFactory();
    }
    return this.mDefaultSpecialEffectsControllerFactory;
  }
  
  ViewModelStore getViewModelStore(Fragment paramFragment)
  {
    return this.mNonConfig.getViewModelStore(paramFragment);
  }
  
  void handleOnBackPressed()
  {
    execPendingActions(true);
    if (this.mOnBackPressedCallback.isEnabled()) {
      popBackStackImmediate();
    } else {
      this.mOnBackPressedDispatcher.onBackPressed();
    }
  }
  
  void hideFragment(Fragment paramFragment)
  {
    if (isLoggingEnabled(2)) {
      Log.v("FragmentManager", "hide: " + paramFragment);
    }
    if (!paramFragment.mHidden)
    {
      paramFragment.mHidden = true;
      paramFragment.mHiddenChanged = (true ^ paramFragment.mHiddenChanged);
      setVisibleRemovingFragment(paramFragment);
    }
  }
  
  void invalidateMenuForFragment(Fragment paramFragment)
  {
    if ((paramFragment.mAdded) && (isMenuAvailable(paramFragment))) {
      this.mNeedMenuInvalidate = true;
    }
  }
  
  public boolean isDestroyed()
  {
    return this.mDestroyed;
  }
  
  boolean isParentMenuVisible(Fragment paramFragment)
  {
    if (paramFragment == null) {
      return true;
    }
    return paramFragment.isMenuVisible();
  }
  
  boolean isPrimaryNavigation(Fragment paramFragment)
  {
    boolean bool = true;
    if (paramFragment == null) {
      return true;
    }
    FragmentManager localFragmentManager = paramFragment.mFragmentManager;
    if ((!paramFragment.equals(localFragmentManager.getPrimaryNavigationFragment())) || (!isPrimaryNavigation(localFragmentManager.mParent))) {
      bool = false;
    }
    return bool;
  }
  
  boolean isStateAtLeast(int paramInt)
  {
    boolean bool;
    if (this.mCurState >= paramInt) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public boolean isStateSaved()
  {
    boolean bool;
    if ((!this.mStateSaved) && (!this.mStopped)) {
      bool = false;
    } else {
      bool = true;
    }
    return bool;
  }
  
  void launchRequestPermissions(Fragment paramFragment, String[] paramArrayOfString, int paramInt)
  {
    if (this.mRequestPermissions != null)
    {
      paramFragment = new LaunchedFragmentInfo(paramFragment.mWho, paramInt);
      this.mLaunchedFragments.addLast(paramFragment);
      this.mRequestPermissions.launch(paramArrayOfString);
    }
    else
    {
      this.mHost.onRequestPermissionsFromFragment(paramFragment, paramArrayOfString, paramInt);
    }
  }
  
  void launchStartActivityForResult(Fragment paramFragment, Intent paramIntent, int paramInt, Bundle paramBundle)
  {
    if (this.mStartActivityForResult != null)
    {
      paramFragment = new LaunchedFragmentInfo(paramFragment.mWho, paramInt);
      this.mLaunchedFragments.addLast(paramFragment);
      if ((paramIntent != null) && (paramBundle != null)) {
        paramIntent.putExtra("androidx.activity.result.contract.extra.ACTIVITY_OPTIONS_BUNDLE", paramBundle);
      }
      this.mStartActivityForResult.launch(paramIntent);
    }
    else
    {
      this.mHost.onStartActivityFromFragment(paramFragment, paramIntent, paramInt, paramBundle);
    }
  }
  
  void launchStartIntentSenderForResult(Fragment paramFragment, IntentSender paramIntentSender, int paramInt1, Intent paramIntent, int paramInt2, int paramInt3, int paramInt4, Bundle paramBundle)
    throws IntentSender.SendIntentException
  {
    if (this.mStartIntentSenderForResult != null)
    {
      if (paramBundle != null)
      {
        if (paramIntent == null)
        {
          paramIntent = new Intent();
          paramIntent.putExtra("androidx.fragment.extra.ACTIVITY_OPTIONS_BUNDLE", true);
        }
        if (isLoggingEnabled(2)) {
          Log.v("FragmentManager", "ActivityOptions " + paramBundle + " were added to fillInIntent " + paramIntent + " for fragment " + paramFragment);
        }
        paramIntent.putExtra("androidx.activity.result.contract.extra.ACTIVITY_OPTIONS_BUNDLE", paramBundle);
      }
      paramIntent = new IntentSenderRequest.Builder(paramIntentSender).setFillInIntent(paramIntent).setFlags(paramInt3, paramInt2).build();
      paramIntentSender = new LaunchedFragmentInfo(paramFragment.mWho, paramInt1);
      this.mLaunchedFragments.addLast(paramIntentSender);
      if (isLoggingEnabled(2)) {
        Log.v("FragmentManager", "Fragment " + paramFragment + "is launching an IntentSender for result ");
      }
      this.mStartIntentSenderForResult.launch(paramIntent);
    }
    else
    {
      this.mHost.onStartIntentSenderFromFragment(paramFragment, paramIntentSender, paramInt1, paramIntent, paramInt2, paramInt3, paramInt4, paramBundle);
    }
  }
  
  void moveFragmentToExpectedState(Fragment paramFragment)
  {
    if (!this.mFragmentStore.containsActiveFragment(paramFragment.mWho))
    {
      if (isLoggingEnabled(3)) {
        Log.d("FragmentManager", "Ignoring moving " + paramFragment + " to state " + this.mCurState + "since it is not added to " + this);
      }
      return;
    }
    moveToState(paramFragment);
    if ((paramFragment.mView != null) && (paramFragment.mIsNewlyAdded) && (paramFragment.mContainer != null))
    {
      if (paramFragment.mPostponedAlpha > 0.0F) {
        paramFragment.mView.setAlpha(paramFragment.mPostponedAlpha);
      }
      paramFragment.mPostponedAlpha = 0.0F;
      paramFragment.mIsNewlyAdded = false;
      FragmentAnim.AnimationOrAnimator localAnimationOrAnimator = FragmentAnim.loadAnimation(this.mHost.getContext(), paramFragment, true, paramFragment.getPopDirection());
      if (localAnimationOrAnimator != null) {
        if (localAnimationOrAnimator.animation != null)
        {
          paramFragment.mView.startAnimation(localAnimationOrAnimator.animation);
        }
        else
        {
          localAnimationOrAnimator.animator.setTarget(paramFragment.mView);
          localAnimationOrAnimator.animator.start();
        }
      }
    }
    if (paramFragment.mHiddenChanged) {
      completeShowHideFragment(paramFragment);
    }
  }
  
  void moveToState(int paramInt, boolean paramBoolean)
  {
    if ((this.mHost == null) && (paramInt != -1)) {
      throw new IllegalStateException("No activity");
    }
    if ((!paramBoolean) && (paramInt == this.mCurState)) {
      return;
    }
    this.mCurState = paramInt;
    Object localObject;
    if (USE_STATE_MANAGER)
    {
      this.mFragmentStore.moveToExpectedState();
    }
    else
    {
      localObject = this.mFragmentStore.getFragments().iterator();
      while (((Iterator)localObject).hasNext()) {
        moveFragmentToExpectedState((Fragment)((Iterator)localObject).next());
      }
      localObject = this.mFragmentStore.getActiveFragmentStateManagers().iterator();
      while (((Iterator)localObject).hasNext())
      {
        FragmentStateManager localFragmentStateManager = (FragmentStateManager)((Iterator)localObject).next();
        Fragment localFragment = localFragmentStateManager.getFragment();
        if (!localFragment.mIsNewlyAdded) {
          moveFragmentToExpectedState(localFragment);
        }
        if ((localFragment.mRemoving) && (!localFragment.isInBackStack())) {
          paramInt = 1;
        } else {
          paramInt = 0;
        }
        if (paramInt != 0) {
          this.mFragmentStore.makeInactive(localFragmentStateManager);
        }
      }
    }
    startPendingDeferredFragments();
    if (this.mNeedMenuInvalidate)
    {
      localObject = this.mHost;
      if ((localObject != null) && (this.mCurState == 7))
      {
        ((FragmentHostCallback)localObject).onSupportInvalidateOptionsMenu();
        this.mNeedMenuInvalidate = false;
      }
    }
  }
  
  void moveToState(Fragment paramFragment)
  {
    moveToState(paramFragment, this.mCurState);
  }
  
  void moveToState(Fragment paramFragment, int paramInt)
  {
    Object localObject2 = this.mFragmentStore.getFragmentStateManager(paramFragment.mWho);
    Object localObject1 = localObject2;
    if (localObject2 == null)
    {
      localObject1 = new FragmentStateManager(this.mLifecycleCallbacksDispatcher, this.mFragmentStore, paramFragment);
      ((FragmentStateManager)localObject1).setFragmentManagerState(1);
    }
    int i = paramInt;
    if (paramFragment.mFromLayout)
    {
      i = paramInt;
      if (paramFragment.mInLayout)
      {
        i = paramInt;
        if (paramFragment.mState == 2) {
          i = Math.max(paramInt, 2);
        }
      }
    }
    int j = Math.min(i, ((FragmentStateManager)localObject1).computeExpectedState());
    if (paramFragment.mState <= j)
    {
      if ((paramFragment.mState < j) && (!this.mExitAnimationCancellationSignals.isEmpty())) {
        cancelExitAnimation(paramFragment);
      }
      switch (paramFragment.mState)
      {
      case 3: 
      default: 
        break;
      case -1: 
        if (j > -1) {
          ((FragmentStateManager)localObject1).attach();
        }
      case 0: 
        if (j > 0) {
          ((FragmentStateManager)localObject1).create();
        }
      case 1: 
        if (j > -1) {
          ((FragmentStateManager)localObject1).ensureInflatedView();
        }
        if (j > 1) {
          ((FragmentStateManager)localObject1).createView();
        }
      case 2: 
        if (j > 2) {
          ((FragmentStateManager)localObject1).activityCreated();
        }
      case 4: 
        if (j > 4) {
          ((FragmentStateManager)localObject1).start();
        }
        break;
      }
      if (j > 5) {
        ((FragmentStateManager)localObject1).resume();
      }
      i = j;
    }
    else
    {
      i = j;
      if (paramFragment.mState > j)
      {
        paramInt = j;
        switch (paramFragment.mState)
        {
        case 3: 
        case 6: 
        default: 
          i = j;
          break;
        case 7: 
          if (j < 7) {
            ((FragmentStateManager)localObject1).pause();
          }
        case 5: 
          if (j < 5) {
            ((FragmentStateManager)localObject1).stop();
          }
        case 4: 
          if (j < 4)
          {
            if (isLoggingEnabled(3)) {
              Log.d("FragmentManager", "movefrom ACTIVITY_CREATED: " + paramFragment);
            }
            if ((paramFragment.mView != null) && (this.mHost.onShouldSaveFragmentState(paramFragment)) && (paramFragment.mSavedViewState == null)) {
              ((FragmentStateManager)localObject1).saveViewState();
            }
          }
        case 2: 
          if (j < 2)
          {
            ViewGroup localViewGroup = null;
            if ((paramFragment.mView != null) && (paramFragment.mContainer != null))
            {
              paramFragment.mContainer.endViewTransition(paramFragment.mView);
              paramFragment.mView.clearAnimation();
              if (!paramFragment.isRemovingParent())
              {
                localObject2 = localViewGroup;
                if (this.mCurState > -1)
                {
                  localObject2 = localViewGroup;
                  if (!this.mDestroyed)
                  {
                    localObject2 = localViewGroup;
                    if (paramFragment.mView.getVisibility() == 0)
                    {
                      localObject2 = localViewGroup;
                      if (paramFragment.mPostponedAlpha >= 0.0F) {
                        localObject2 = FragmentAnim.loadAnimation(this.mHost.getContext(), paramFragment, false, paramFragment.getPopDirection());
                      }
                    }
                  }
                }
                paramFragment.mPostponedAlpha = 0.0F;
                localViewGroup = paramFragment.mContainer;
                View localView = paramFragment.mView;
                if (localObject2 != null) {
                  FragmentAnim.animateRemoveFragment(paramFragment, (FragmentAnim.AnimationOrAnimator)localObject2, this.mFragmentTransitionCallback);
                }
                localViewGroup.removeView(localView);
                if (isLoggingEnabled(2)) {
                  Log.v("FragmentManager", "Removing view " + localView + " for fragment " + paramFragment + " from container " + localViewGroup);
                }
                if (localViewGroup != paramFragment.mContainer) {
                  return;
                }
              }
            }
            if (this.mExitAnimationCancellationSignals.get(paramFragment) == null) {
              ((FragmentStateManager)localObject1).destroyFragmentView();
            }
          }
        case 1: 
          paramInt = j;
          if (j < 1) {
            if (this.mExitAnimationCancellationSignals.get(paramFragment) != null)
            {
              paramInt = 1;
            }
            else
            {
              ((FragmentStateManager)localObject1).destroy();
              paramInt = j;
            }
          }
          break;
        }
        i = paramInt;
        if (paramInt < 0)
        {
          ((FragmentStateManager)localObject1).detach();
          i = paramInt;
        }
      }
    }
    if (paramFragment.mState != i)
    {
      if (isLoggingEnabled(3)) {
        Log.d("FragmentManager", "moveToState: Fragment state for " + paramFragment + " not updated inline; expected state " + i + " found " + paramFragment.mState);
      }
      paramFragment.mState = i;
    }
  }
  
  void noteStateNotSaved()
  {
    if (this.mHost == null) {
      return;
    }
    this.mStateSaved = false;
    this.mStopped = false;
    this.mNonConfig.setIsStateSaved(false);
    Iterator localIterator = this.mFragmentStore.getFragments().iterator();
    while (localIterator.hasNext())
    {
      Fragment localFragment = (Fragment)localIterator.next();
      if (localFragment != null) {
        localFragment.noteStateNotSaved();
      }
    }
  }
  
  void onContainerAvailable(FragmentContainerView paramFragmentContainerView)
  {
    Iterator localIterator = this.mFragmentStore.getActiveFragmentStateManagers().iterator();
    while (localIterator.hasNext())
    {
      FragmentStateManager localFragmentStateManager = (FragmentStateManager)localIterator.next();
      Fragment localFragment = localFragmentStateManager.getFragment();
      if ((localFragment.mContainerId == paramFragmentContainerView.getId()) && (localFragment.mView != null) && (localFragment.mView.getParent() == null))
      {
        localFragment.mContainer = paramFragmentContainerView;
        localFragmentStateManager.addViewToContainer();
      }
    }
  }
  
  @Deprecated
  public FragmentTransaction openTransaction()
  {
    return beginTransaction();
  }
  
  void performPendingDeferredStart(FragmentStateManager paramFragmentStateManager)
  {
    Fragment localFragment = paramFragmentStateManager.getFragment();
    if (localFragment.mDeferStart)
    {
      if (this.mExecutingActions)
      {
        this.mHavePendingDeferredStart = true;
        return;
      }
      localFragment.mDeferStart = false;
      if (USE_STATE_MANAGER) {
        paramFragmentStateManager.moveToExpectedState();
      } else {
        moveToState(localFragment);
      }
    }
  }
  
  public void popBackStack()
  {
    enqueueAction(new PopBackStackState(null, -1, 0), false);
  }
  
  public void popBackStack(int paramInt1, int paramInt2)
  {
    if (paramInt1 >= 0)
    {
      enqueueAction(new PopBackStackState(null, paramInt1, paramInt2), false);
      return;
    }
    throw new IllegalArgumentException("Bad id: " + paramInt1);
  }
  
  public void popBackStack(String paramString, int paramInt)
  {
    enqueueAction(new PopBackStackState(paramString, -1, paramInt), false);
  }
  
  public boolean popBackStackImmediate()
  {
    return popBackStackImmediate(null, -1, 0);
  }
  
  public boolean popBackStackImmediate(int paramInt1, int paramInt2)
  {
    if (paramInt1 >= 0) {
      return popBackStackImmediate(null, paramInt1, paramInt2);
    }
    throw new IllegalArgumentException("Bad id: " + paramInt1);
  }
  
  public boolean popBackStackImmediate(String paramString, int paramInt)
  {
    return popBackStackImmediate(paramString, -1, paramInt);
  }
  
  boolean popBackStackState(ArrayList<BackStackRecord> paramArrayList, ArrayList<Boolean> paramArrayList1, String paramString, int paramInt1, int paramInt2)
  {
    Object localObject = this.mBackStack;
    if (localObject == null) {
      return false;
    }
    if ((paramString == null) && (paramInt1 < 0) && ((paramInt2 & 0x1) == 0))
    {
      paramInt1 = ((ArrayList)localObject).size() - 1;
      if (paramInt1 < 0) {
        return false;
      }
      paramArrayList.add(this.mBackStack.remove(paramInt1));
      paramArrayList1.add(Boolean.valueOf(true));
    }
    else
    {
      int i = -1;
      if ((paramString != null) || (paramInt1 >= 0))
      {
        for (int j = ((ArrayList)localObject).size() - 1; j >= 0; j--)
        {
          localObject = (BackStackRecord)this.mBackStack.get(j);
          if (((paramString != null) && (paramString.equals(((BackStackRecord)localObject).getName()))) || ((paramInt1 >= 0) && (paramInt1 == ((BackStackRecord)localObject).mIndex))) {
            break;
          }
        }
        if (j < 0) {
          return false;
        }
        i = j;
        if ((paramInt2 & 0x1) != 0) {
          for (paramInt2 = j - 1;; paramInt2--)
          {
            i = paramInt2;
            if (paramInt2 < 0) {
              break;
            }
            localObject = (BackStackRecord)this.mBackStack.get(paramInt2);
            if ((paramString == null) || (!paramString.equals(((BackStackRecord)localObject).getName())))
            {
              i = paramInt2;
              if (paramInt1 < 0) {
                break;
              }
              i = paramInt2;
              if (paramInt1 != ((BackStackRecord)localObject).mIndex) {
                break;
              }
            }
          }
        }
      }
      if (i == this.mBackStack.size() - 1) {
        return false;
      }
      for (paramInt1 = this.mBackStack.size() - 1; paramInt1 > i; paramInt1--)
      {
        paramArrayList.add(this.mBackStack.remove(paramInt1));
        paramArrayList1.add(Boolean.valueOf(true));
      }
    }
    return true;
  }
  
  public void putFragment(Bundle paramBundle, String paramString, Fragment paramFragment)
  {
    if (paramFragment.mFragmentManager != this) {
      throwException(new IllegalStateException("Fragment " + paramFragment + " is not currently in the FragmentManager"));
    }
    paramBundle.putString(paramString, paramFragment.mWho);
  }
  
  public void registerFragmentLifecycleCallbacks(FragmentLifecycleCallbacks paramFragmentLifecycleCallbacks, boolean paramBoolean)
  {
    this.mLifecycleCallbacksDispatcher.registerFragmentLifecycleCallbacks(paramFragmentLifecycleCallbacks, paramBoolean);
  }
  
  void removeCancellationSignal(Fragment paramFragment, CancellationSignal paramCancellationSignal)
  {
    HashSet localHashSet = (HashSet)this.mExitAnimationCancellationSignals.get(paramFragment);
    if ((localHashSet != null) && (localHashSet.remove(paramCancellationSignal)) && (localHashSet.isEmpty()))
    {
      this.mExitAnimationCancellationSignals.remove(paramFragment);
      if (paramFragment.mState < 5)
      {
        destroyFragmentView(paramFragment);
        moveToState(paramFragment);
      }
    }
  }
  
  void removeFragment(Fragment paramFragment)
  {
    if (isLoggingEnabled(2)) {
      Log.v("FragmentManager", "remove: " + paramFragment + " nesting=" + paramFragment.mBackStackNesting);
    }
    boolean bool = paramFragment.isInBackStack();
    if ((!paramFragment.mDetached) || ((bool ^ true)))
    {
      this.mFragmentStore.removeFragment(paramFragment);
      if (isMenuAvailable(paramFragment)) {
        this.mNeedMenuInvalidate = true;
      }
      paramFragment.mRemoving = true;
      setVisibleRemovingFragment(paramFragment);
    }
  }
  
  public void removeFragmentOnAttachListener(FragmentOnAttachListener paramFragmentOnAttachListener)
  {
    this.mOnAttachListeners.remove(paramFragmentOnAttachListener);
  }
  
  public void removeOnBackStackChangedListener(OnBackStackChangedListener paramOnBackStackChangedListener)
  {
    ArrayList localArrayList = this.mBackStackChangeListeners;
    if (localArrayList != null) {
      localArrayList.remove(paramOnBackStackChangedListener);
    }
  }
  
  void removeRetainedFragment(Fragment paramFragment)
  {
    this.mNonConfig.removeRetainedFragment(paramFragment);
  }
  
  void restoreAllState(Parcelable paramParcelable, FragmentManagerNonConfig paramFragmentManagerNonConfig)
  {
    if ((this.mHost instanceof ViewModelStoreOwner)) {
      throwException(new IllegalStateException("You must use restoreSaveState when your FragmentHostCallback implements ViewModelStoreOwner"));
    }
    this.mNonConfig.restoreFromSnapshot(paramFragmentManagerNonConfig);
    restoreSaveState(paramParcelable);
  }
  
  void restoreSaveState(Parcelable paramParcelable)
  {
    if (paramParcelable == null) {
      return;
    }
    FragmentManagerState localFragmentManagerState = (FragmentManagerState)paramParcelable;
    if (localFragmentManagerState.mActive == null) {
      return;
    }
    this.mFragmentStore.resetActiveFragments();
    Object localObject = localFragmentManagerState.mActive.iterator();
    Fragment localFragment;
    while (((Iterator)localObject).hasNext())
    {
      paramParcelable = (FragmentState)((Iterator)localObject).next();
      if (paramParcelable != null)
      {
        localFragment = this.mNonConfig.findRetainedFragmentByWho(paramParcelable.mWho);
        if (localFragment != null)
        {
          if (isLoggingEnabled(2)) {
            Log.v("FragmentManager", "restoreSaveState: re-attaching retained " + localFragment);
          }
          paramParcelable = new FragmentStateManager(this.mLifecycleCallbacksDispatcher, this.mFragmentStore, localFragment, paramParcelable);
        }
        else
        {
          paramParcelable = new FragmentStateManager(this.mLifecycleCallbacksDispatcher, this.mFragmentStore, this.mHost.getContext().getClassLoader(), getFragmentFactory(), paramParcelable);
        }
        localFragment = paramParcelable.getFragment();
        localFragment.mFragmentManager = this;
        if (isLoggingEnabled(2)) {
          Log.v("FragmentManager", "restoreSaveState: active (" + localFragment.mWho + "): " + localFragment);
        }
        paramParcelable.restoreState(this.mHost.getContext().getClassLoader());
        this.mFragmentStore.makeActive(paramParcelable);
        paramParcelable.setFragmentManagerState(this.mCurState);
      }
    }
    paramParcelable = this.mNonConfig.getRetainedFragments().iterator();
    while (paramParcelable.hasNext())
    {
      localFragment = (Fragment)paramParcelable.next();
      if (!this.mFragmentStore.containsActiveFragment(localFragment.mWho))
      {
        if (isLoggingEnabled(2)) {
          Log.v("FragmentManager", "Discarding retained Fragment " + localFragment + " that was not found in the set of active Fragments " + localFragmentManagerState.mActive);
        }
        this.mNonConfig.removeRetainedFragment(localFragment);
        localFragment.mFragmentManager = this;
        localObject = new FragmentStateManager(this.mLifecycleCallbacksDispatcher, this.mFragmentStore, localFragment);
        ((FragmentStateManager)localObject).setFragmentManagerState(1);
        ((FragmentStateManager)localObject).moveToExpectedState();
        localFragment.mRemoving = true;
        ((FragmentStateManager)localObject).moveToExpectedState();
      }
    }
    this.mFragmentStore.restoreAddedFragments(localFragmentManagerState.mAdded);
    int i;
    if (localFragmentManagerState.mBackStack != null)
    {
      this.mBackStack = new ArrayList(localFragmentManagerState.mBackStack.length);
      for (i = 0; i < localFragmentManagerState.mBackStack.length; i++)
      {
        localObject = localFragmentManagerState.mBackStack[i].instantiate(this);
        if (isLoggingEnabled(2))
        {
          Log.v("FragmentManager", "restoreAllState: back stack #" + i + " (index " + ((BackStackRecord)localObject).mIndex + "): " + localObject);
          paramParcelable = new PrintWriter(new LogWriter("FragmentManager"));
          ((BackStackRecord)localObject).dump("  ", paramParcelable, false);
          paramParcelable.close();
        }
        this.mBackStack.add(localObject);
      }
    }
    else
    {
      this.mBackStack = null;
    }
    this.mBackStackIndex.set(localFragmentManagerState.mBackStackIndex);
    if (localFragmentManagerState.mPrimaryNavActiveWho != null)
    {
      paramParcelable = findActiveFragment(localFragmentManagerState.mPrimaryNavActiveWho);
      this.mPrimaryNav = paramParcelable;
      dispatchParentPrimaryNavigationFragmentChanged(paramParcelable);
    }
    localObject = localFragmentManagerState.mResultKeys;
    if (localObject != null) {
      for (i = 0; i < ((ArrayList)localObject).size(); i++)
      {
        paramParcelable = (Bundle)localFragmentManagerState.mResults.get(i);
        paramParcelable.setClassLoader(this.mHost.getContext().getClassLoader());
        this.mResults.put(((ArrayList)localObject).get(i), paramParcelable);
      }
    }
    this.mLaunchedFragments = new ArrayDeque(localFragmentManagerState.mLaunchedFragments);
  }
  
  @Deprecated
  FragmentManagerNonConfig retainNonConfig()
  {
    if ((this.mHost instanceof ViewModelStoreOwner)) {
      throwException(new IllegalStateException("You cannot use retainNonConfig when your FragmentHostCallback implements ViewModelStoreOwner."));
    }
    return this.mNonConfig.getSnapshot();
  }
  
  Parcelable saveAllState()
  {
    forcePostponedTransactions();
    endAnimatingAwayFragments();
    execPendingActions(true);
    this.mStateSaved = true;
    this.mNonConfig.setIsStateSaved(true);
    ArrayList localArrayList1 = this.mFragmentStore.saveActiveFragments();
    if (localArrayList1.isEmpty())
    {
      if (isLoggingEnabled(2)) {
        Log.v("FragmentManager", "saveAllState: no fragments!");
      }
      return null;
    }
    ArrayList localArrayList2 = this.mFragmentStore.saveAddedFragments();
    Object localObject2 = null;
    ArrayList localArrayList3 = this.mBackStack;
    Object localObject1 = localObject2;
    if (localArrayList3 != null)
    {
      int j = localArrayList3.size();
      localObject1 = localObject2;
      if (j > 0)
      {
        localObject2 = new BackStackState[j];
        for (int i = 0;; i++)
        {
          localObject1 = localObject2;
          if (i >= j) {
            break;
          }
          localObject2[i] = new BackStackState((BackStackRecord)this.mBackStack.get(i));
          if (isLoggingEnabled(2)) {
            Log.v("FragmentManager", "saveAllState: adding back stack #" + i + ": " + this.mBackStack.get(i));
          }
        }
      }
    }
    localObject2 = new FragmentManagerState();
    ((FragmentManagerState)localObject2).mActive = localArrayList1;
    ((FragmentManagerState)localObject2).mAdded = localArrayList2;
    ((FragmentManagerState)localObject2).mBackStack = ((BackStackState[])localObject1);
    ((FragmentManagerState)localObject2).mBackStackIndex = this.mBackStackIndex.get();
    localObject1 = this.mPrimaryNav;
    if (localObject1 != null) {
      ((FragmentManagerState)localObject2).mPrimaryNavActiveWho = ((Fragment)localObject1).mWho;
    }
    ((FragmentManagerState)localObject2).mResultKeys.addAll(this.mResults.keySet());
    ((FragmentManagerState)localObject2).mResults.addAll(this.mResults.values());
    ((FragmentManagerState)localObject2).mLaunchedFragments = new ArrayList(this.mLaunchedFragments);
    return (Parcelable)localObject2;
  }
  
  public Fragment.SavedState saveFragmentInstanceState(Fragment paramFragment)
  {
    FragmentStateManager localFragmentStateManager = this.mFragmentStore.getFragmentStateManager(paramFragment.mWho);
    if ((localFragmentStateManager == null) || (!localFragmentStateManager.getFragment().equals(paramFragment))) {
      throwException(new IllegalStateException("Fragment " + paramFragment + " is not currently in the FragmentManager"));
    }
    return localFragmentStateManager.saveInstanceState();
  }
  
  void scheduleCommit()
  {
    synchronized (this.mPendingActions)
    {
      ArrayList localArrayList2 = this.mPostponedTransactions;
      int j = 0;
      int i;
      if ((localArrayList2 != null) && (!localArrayList2.isEmpty())) {
        i = 1;
      } else {
        i = 0;
      }
      if (this.mPendingActions.size() == 1) {
        j = 1;
      }
      if ((i != 0) || (j != 0))
      {
        this.mHost.getHandler().removeCallbacks(this.mExecCommit);
        this.mHost.getHandler().post(this.mExecCommit);
        updateOnBackPressedCallbackEnabled();
      }
      return;
    }
  }
  
  void setExitAnimationOrder(Fragment paramFragment, boolean paramBoolean)
  {
    paramFragment = getFragmentContainer(paramFragment);
    if ((paramFragment != null) && ((paramFragment instanceof FragmentContainerView))) {
      ((FragmentContainerView)paramFragment).setDrawDisappearingViewsLast(paramBoolean ^ true);
    }
  }
  
  public void setFragmentFactory(FragmentFactory paramFragmentFactory)
  {
    this.mFragmentFactory = paramFragmentFactory;
  }
  
  public final void setFragmentResult(String paramString, Bundle paramBundle)
  {
    LifecycleAwareResultListener localLifecycleAwareResultListener = (LifecycleAwareResultListener)this.mResultListeners.get(paramString);
    if ((localLifecycleAwareResultListener != null) && (localLifecycleAwareResultListener.isAtLeast(Lifecycle.State.STARTED))) {
      localLifecycleAwareResultListener.onFragmentResult(paramString, paramBundle);
    } else {
      this.mResults.put(paramString, paramBundle);
    }
  }
  
  public final void setFragmentResultListener(final String paramString, LifecycleOwner paramLifecycleOwner, final FragmentResultListener paramFragmentResultListener)
  {
    final Lifecycle localLifecycle = paramLifecycleOwner.getLifecycle();
    if (localLifecycle.getCurrentState() == Lifecycle.State.DESTROYED) {
      return;
    }
    paramLifecycleOwner = new LifecycleEventObserver()
    {
      public void onStateChanged(LifecycleOwner paramAnonymousLifecycleOwner, Lifecycle.Event paramAnonymousEvent)
      {
        if (paramAnonymousEvent == Lifecycle.Event.ON_START)
        {
          paramAnonymousLifecycleOwner = (Bundle)FragmentManager.this.mResults.get(paramString);
          if (paramAnonymousLifecycleOwner != null)
          {
            paramFragmentResultListener.onFragmentResult(paramString, paramAnonymousLifecycleOwner);
            FragmentManager.this.clearFragmentResult(paramString);
          }
        }
        if (paramAnonymousEvent == Lifecycle.Event.ON_DESTROY)
        {
          localLifecycle.removeObserver(this);
          FragmentManager.this.mResultListeners.remove(paramString);
        }
      }
    };
    localLifecycle.addObserver(paramLifecycleOwner);
    paramString = (LifecycleAwareResultListener)this.mResultListeners.put(paramString, new LifecycleAwareResultListener(localLifecycle, paramFragmentResultListener, paramLifecycleOwner));
    if (paramString != null) {
      paramString.removeObserver();
    }
  }
  
  void setMaxLifecycle(Fragment paramFragment, Lifecycle.State paramState)
  {
    if ((paramFragment.equals(findActiveFragment(paramFragment.mWho))) && ((paramFragment.mHost == null) || (paramFragment.mFragmentManager == this)))
    {
      paramFragment.mMaxState = paramState;
      return;
    }
    throw new IllegalArgumentException("Fragment " + paramFragment + " is not an active fragment of FragmentManager " + this);
  }
  
  void setPrimaryNavigationFragment(Fragment paramFragment)
  {
    if ((paramFragment != null) && ((!paramFragment.equals(findActiveFragment(paramFragment.mWho))) || ((paramFragment.mHost != null) && (paramFragment.mFragmentManager != this)))) {
      throw new IllegalArgumentException("Fragment " + paramFragment + " is not an active fragment of FragmentManager " + this);
    }
    Fragment localFragment = this.mPrimaryNav;
    this.mPrimaryNav = paramFragment;
    dispatchParentPrimaryNavigationFragmentChanged(localFragment);
    dispatchParentPrimaryNavigationFragmentChanged(this.mPrimaryNav);
  }
  
  void setSpecialEffectsControllerFactory(SpecialEffectsControllerFactory paramSpecialEffectsControllerFactory)
  {
    this.mSpecialEffectsControllerFactory = paramSpecialEffectsControllerFactory;
  }
  
  void showFragment(Fragment paramFragment)
  {
    if (isLoggingEnabled(2)) {
      Log.v("FragmentManager", "show: " + paramFragment);
    }
    if (paramFragment.mHidden)
    {
      paramFragment.mHidden = false;
      paramFragment.mHiddenChanged ^= true;
    }
  }
  
  public String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder(128);
    localStringBuilder.append("FragmentManager{");
    Object localObject = Integer.toHexString(System.identityHashCode(this));
    Log5ECF72.a(localObject);
    LogE84000.a(localObject);
    Log229316.a(localObject);
    localStringBuilder.append((String)localObject);
    localStringBuilder.append(" in ");
    localObject = this.mParent;
    if (localObject != null)
    {
      localStringBuilder.append(localObject.getClass().getSimpleName());
      localStringBuilder.append("{");
      localObject = Integer.toHexString(System.identityHashCode(this.mParent));
      Log5ECF72.a(localObject);
      LogE84000.a(localObject);
      Log229316.a(localObject);
      localStringBuilder.append((String)localObject);
      localStringBuilder.append("}");
    }
    else
    {
      localObject = this.mHost;
      if (localObject != null)
      {
        localStringBuilder.append(localObject.getClass().getSimpleName());
        localStringBuilder.append("{");
        localObject = Integer.toHexString(System.identityHashCode(this.mHost));
        Log5ECF72.a(localObject);
        LogE84000.a(localObject);
        Log229316.a(localObject);
        localStringBuilder.append((String)localObject);
        localStringBuilder.append("}");
      }
      else
      {
        localStringBuilder.append("null");
      }
    }
    localStringBuilder.append("}}");
    return localStringBuilder.toString();
  }
  
  public void unregisterFragmentLifecycleCallbacks(FragmentLifecycleCallbacks paramFragmentLifecycleCallbacks)
  {
    this.mLifecycleCallbacksDispatcher.unregisterFragmentLifecycleCallbacks(paramFragmentLifecycleCallbacks);
  }
  
  public static abstract interface BackStackEntry
  {
    @Deprecated
    public abstract CharSequence getBreadCrumbShortTitle();
    
    @Deprecated
    public abstract int getBreadCrumbShortTitleRes();
    
    @Deprecated
    public abstract CharSequence getBreadCrumbTitle();
    
    @Deprecated
    public abstract int getBreadCrumbTitleRes();
    
    public abstract int getId();
    
    public abstract String getName();
  }
  
  static class FragmentIntentSenderContract
    extends ActivityResultContract<IntentSenderRequest, ActivityResult>
  {
    public Intent createIntent(Context paramContext, IntentSenderRequest paramIntentSenderRequest)
    {
      Intent localIntent1 = new Intent("androidx.activity.result.contract.action.INTENT_SENDER_REQUEST");
      Intent localIntent2 = paramIntentSenderRequest.getFillInIntent();
      paramContext = paramIntentSenderRequest;
      if (localIntent2 != null)
      {
        Bundle localBundle = localIntent2.getBundleExtra("androidx.activity.result.contract.extra.ACTIVITY_OPTIONS_BUNDLE");
        paramContext = paramIntentSenderRequest;
        if (localBundle != null)
        {
          localIntent1.putExtra("androidx.activity.result.contract.extra.ACTIVITY_OPTIONS_BUNDLE", localBundle);
          localIntent2.removeExtra("androidx.activity.result.contract.extra.ACTIVITY_OPTIONS_BUNDLE");
          paramContext = paramIntentSenderRequest;
          if (localIntent2.getBooleanExtra("androidx.fragment.extra.ACTIVITY_OPTIONS_BUNDLE", false)) {
            paramContext = new IntentSenderRequest.Builder(paramIntentSenderRequest.getIntentSender()).setFillInIntent(null).setFlags(paramIntentSenderRequest.getFlagsValues(), paramIntentSenderRequest.getFlagsMask()).build();
          }
        }
      }
      localIntent1.putExtra("androidx.activity.result.contract.extra.INTENT_SENDER_REQUEST", paramContext);
      if (FragmentManager.isLoggingEnabled(2)) {
        Log.v("FragmentManager", "CreateIntent created the following intent: " + localIntent1);
      }
      return localIntent1;
    }
    
    public ActivityResult parseResult(int paramInt, Intent paramIntent)
    {
      return new ActivityResult(paramInt, paramIntent);
    }
  }
  
  public static abstract class FragmentLifecycleCallbacks
  {
    @Deprecated
    public void onFragmentActivityCreated(FragmentManager paramFragmentManager, Fragment paramFragment, Bundle paramBundle) {}
    
    public void onFragmentAttached(FragmentManager paramFragmentManager, Fragment paramFragment, Context paramContext) {}
    
    public void onFragmentCreated(FragmentManager paramFragmentManager, Fragment paramFragment, Bundle paramBundle) {}
    
    public void onFragmentDestroyed(FragmentManager paramFragmentManager, Fragment paramFragment) {}
    
    public void onFragmentDetached(FragmentManager paramFragmentManager, Fragment paramFragment) {}
    
    public void onFragmentPaused(FragmentManager paramFragmentManager, Fragment paramFragment) {}
    
    public void onFragmentPreAttached(FragmentManager paramFragmentManager, Fragment paramFragment, Context paramContext) {}
    
    public void onFragmentPreCreated(FragmentManager paramFragmentManager, Fragment paramFragment, Bundle paramBundle) {}
    
    public void onFragmentResumed(FragmentManager paramFragmentManager, Fragment paramFragment) {}
    
    public void onFragmentSaveInstanceState(FragmentManager paramFragmentManager, Fragment paramFragment, Bundle paramBundle) {}
    
    public void onFragmentStarted(FragmentManager paramFragmentManager, Fragment paramFragment) {}
    
    public void onFragmentStopped(FragmentManager paramFragmentManager, Fragment paramFragment) {}
    
    public void onFragmentViewCreated(FragmentManager paramFragmentManager, Fragment paramFragment, View paramView, Bundle paramBundle) {}
    
    public void onFragmentViewDestroyed(FragmentManager paramFragmentManager, Fragment paramFragment) {}
  }
  
  static class LaunchedFragmentInfo
    implements Parcelable
  {
    public static final Parcelable.Creator<LaunchedFragmentInfo> CREATOR = new Parcelable.Creator()
    {
      public FragmentManager.LaunchedFragmentInfo createFromParcel(Parcel paramAnonymousParcel)
      {
        return new FragmentManager.LaunchedFragmentInfo(paramAnonymousParcel);
      }
      
      public FragmentManager.LaunchedFragmentInfo[] newArray(int paramAnonymousInt)
      {
        return new FragmentManager.LaunchedFragmentInfo[paramAnonymousInt];
      }
    };
    int mRequestCode;
    String mWho;
    
    LaunchedFragmentInfo(Parcel paramParcel)
    {
      this.mWho = paramParcel.readString();
      this.mRequestCode = paramParcel.readInt();
    }
    
    LaunchedFragmentInfo(String paramString, int paramInt)
    {
      this.mWho = paramString;
      this.mRequestCode = paramInt;
    }
    
    public int describeContents()
    {
      return 0;
    }
    
    public void writeToParcel(Parcel paramParcel, int paramInt)
    {
      paramParcel.writeString(this.mWho);
      paramParcel.writeInt(this.mRequestCode);
    }
  }
  
  private static class LifecycleAwareResultListener
    implements FragmentResultListener
  {
    private final Lifecycle mLifecycle;
    private final FragmentResultListener mListener;
    private final LifecycleEventObserver mObserver;
    
    LifecycleAwareResultListener(Lifecycle paramLifecycle, FragmentResultListener paramFragmentResultListener, LifecycleEventObserver paramLifecycleEventObserver)
    {
      this.mLifecycle = paramLifecycle;
      this.mListener = paramFragmentResultListener;
      this.mObserver = paramLifecycleEventObserver;
    }
    
    public boolean isAtLeast(Lifecycle.State paramState)
    {
      return this.mLifecycle.getCurrentState().isAtLeast(paramState);
    }
    
    public void onFragmentResult(String paramString, Bundle paramBundle)
    {
      this.mListener.onFragmentResult(paramString, paramBundle);
    }
    
    public void removeObserver()
    {
      this.mLifecycle.removeObserver(this.mObserver);
    }
  }
  
  public static abstract interface OnBackStackChangedListener
  {
    public abstract void onBackStackChanged();
  }
  
  static abstract interface OpGenerator
  {
    public abstract boolean generateOps(ArrayList<BackStackRecord> paramArrayList, ArrayList<Boolean> paramArrayList1);
  }
  
  private class PopBackStackState
    implements FragmentManager.OpGenerator
  {
    final int mFlags;
    final int mId;
    final String mName;
    
    PopBackStackState(String paramString, int paramInt1, int paramInt2)
    {
      this.mName = paramString;
      this.mId = paramInt1;
      this.mFlags = paramInt2;
    }
    
    public boolean generateOps(ArrayList<BackStackRecord> paramArrayList, ArrayList<Boolean> paramArrayList1)
    {
      if ((FragmentManager.this.mPrimaryNav != null) && (this.mId < 0) && (this.mName == null) && (FragmentManager.this.mPrimaryNav.getChildFragmentManager().popBackStackImmediate())) {
        return false;
      }
      return FragmentManager.this.popBackStackState(paramArrayList, paramArrayList1, this.mName, this.mId, this.mFlags);
    }
  }
  
  static class StartEnterTransitionListener
    implements Fragment.OnStartEnterTransitionListener
  {
    final boolean mIsBack;
    private int mNumPostponed;
    final BackStackRecord mRecord;
    
    StartEnterTransitionListener(BackStackRecord paramBackStackRecord, boolean paramBoolean)
    {
      this.mIsBack = paramBoolean;
      this.mRecord = paramBackStackRecord;
    }
    
    void cancelTransaction()
    {
      this.mRecord.mManager.completeExecute(this.mRecord, this.mIsBack, false, false);
    }
    
    void completeTransaction()
    {
      int i = this.mNumPostponed;
      boolean bool1 = false;
      if (i > 0) {
        i = 1;
      } else {
        i = 0;
      }
      Object localObject2 = this.mRecord.mManager.getFragments().iterator();
      while (((Iterator)localObject2).hasNext())
      {
        localObject1 = (Fragment)((Iterator)localObject2).next();
        ((Fragment)localObject1).setOnStartEnterTransitionListener(null);
        if ((i != 0) && (((Fragment)localObject1).isPostponed())) {
          ((Fragment)localObject1).startPostponedEnterTransition();
        }
      }
      localObject2 = this.mRecord.mManager;
      Object localObject1 = this.mRecord;
      boolean bool2 = this.mIsBack;
      if (i == 0) {
        bool1 = true;
      }
      ((FragmentManager)localObject2).completeExecute((BackStackRecord)localObject1, bool2, bool1, true);
    }
    
    public boolean isReady()
    {
      boolean bool;
      if (this.mNumPostponed == 0) {
        bool = true;
      } else {
        bool = false;
      }
      return bool;
    }
    
    public void onStartEnterTransition()
    {
      int i = this.mNumPostponed - 1;
      this.mNumPostponed = i;
      if (i != 0) {
        return;
      }
      this.mRecord.mManager.scheduleCommit();
    }
    
    public void startListening()
    {
      this.mNumPostponed += 1;
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/fragment/app/FragmentManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */