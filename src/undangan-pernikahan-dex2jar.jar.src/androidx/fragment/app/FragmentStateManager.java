package androidx.fragment.app;

import android.app.Activity;
import android.content.res.Resources;
import android.content.res.Resources.NotFoundException;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnAttachStateChangeListener;
import android.view.ViewGroup;
import android.view.ViewParent;
import androidx.core.view.ViewCompat;
import androidx.fragment.R.id;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelStoreOwner;
import java.util.Iterator;
import java.util.List;
import mt.Log229316;
import mt.Log5ECF72;
import mt.LogE84000;

class FragmentStateManager
{
  private static final String TAG = "FragmentManager";
  private static final String TARGET_REQUEST_CODE_STATE_TAG = "android:target_req_state";
  private static final String TARGET_STATE_TAG = "android:target_state";
  private static final String USER_VISIBLE_HINT_TAG = "android:user_visible_hint";
  private static final String VIEW_REGISTRY_STATE_TAG = "android:view_registry_state";
  private static final String VIEW_STATE_TAG = "android:view_state";
  private final FragmentLifecycleCallbacksDispatcher mDispatcher;
  private final Fragment mFragment;
  private int mFragmentManagerState = -1;
  private final FragmentStore mFragmentStore;
  private boolean mMovingToState = false;
  
  FragmentStateManager(FragmentLifecycleCallbacksDispatcher paramFragmentLifecycleCallbacksDispatcher, FragmentStore paramFragmentStore, Fragment paramFragment)
  {
    this.mDispatcher = paramFragmentLifecycleCallbacksDispatcher;
    this.mFragmentStore = paramFragmentStore;
    this.mFragment = paramFragment;
  }
  
  FragmentStateManager(FragmentLifecycleCallbacksDispatcher paramFragmentLifecycleCallbacksDispatcher, FragmentStore paramFragmentStore, Fragment paramFragment, FragmentState paramFragmentState)
  {
    this.mDispatcher = paramFragmentLifecycleCallbacksDispatcher;
    this.mFragmentStore = paramFragmentStore;
    this.mFragment = paramFragment;
    paramFragment.mSavedViewState = null;
    paramFragment.mSavedViewRegistryState = null;
    paramFragment.mBackStackNesting = 0;
    paramFragment.mInLayout = false;
    paramFragment.mAdded = false;
    if (paramFragment.mTarget != null) {
      paramFragmentLifecycleCallbacksDispatcher = paramFragment.mTarget.mWho;
    } else {
      paramFragmentLifecycleCallbacksDispatcher = null;
    }
    paramFragment.mTargetWho = paramFragmentLifecycleCallbacksDispatcher;
    paramFragment.mTarget = null;
    if (paramFragmentState.mSavedFragmentState != null) {
      paramFragment.mSavedFragmentState = paramFragmentState.mSavedFragmentState;
    } else {
      paramFragment.mSavedFragmentState = new Bundle();
    }
  }
  
  FragmentStateManager(FragmentLifecycleCallbacksDispatcher paramFragmentLifecycleCallbacksDispatcher, FragmentStore paramFragmentStore, ClassLoader paramClassLoader, FragmentFactory paramFragmentFactory, FragmentState paramFragmentState)
  {
    this.mDispatcher = paramFragmentLifecycleCallbacksDispatcher;
    this.mFragmentStore = paramFragmentStore;
    paramFragmentLifecycleCallbacksDispatcher = paramFragmentFactory.instantiate(paramClassLoader, paramFragmentState.mClassName);
    this.mFragment = paramFragmentLifecycleCallbacksDispatcher;
    if (paramFragmentState.mArguments != null) {
      paramFragmentState.mArguments.setClassLoader(paramClassLoader);
    }
    paramFragmentLifecycleCallbacksDispatcher.setArguments(paramFragmentState.mArguments);
    paramFragmentLifecycleCallbacksDispatcher.mWho = paramFragmentState.mWho;
    paramFragmentLifecycleCallbacksDispatcher.mFromLayout = paramFragmentState.mFromLayout;
    paramFragmentLifecycleCallbacksDispatcher.mRestored = true;
    paramFragmentLifecycleCallbacksDispatcher.mFragmentId = paramFragmentState.mFragmentId;
    paramFragmentLifecycleCallbacksDispatcher.mContainerId = paramFragmentState.mContainerId;
    paramFragmentLifecycleCallbacksDispatcher.mTag = paramFragmentState.mTag;
    paramFragmentLifecycleCallbacksDispatcher.mRetainInstance = paramFragmentState.mRetainInstance;
    paramFragmentLifecycleCallbacksDispatcher.mRemoving = paramFragmentState.mRemoving;
    paramFragmentLifecycleCallbacksDispatcher.mDetached = paramFragmentState.mDetached;
    paramFragmentLifecycleCallbacksDispatcher.mHidden = paramFragmentState.mHidden;
    paramFragmentLifecycleCallbacksDispatcher.mMaxState = androidx.lifecycle.Lifecycle.State.values()[paramFragmentState.mMaxLifecycleState];
    if (paramFragmentState.mSavedFragmentState != null) {
      paramFragmentLifecycleCallbacksDispatcher.mSavedFragmentState = paramFragmentState.mSavedFragmentState;
    } else {
      paramFragmentLifecycleCallbacksDispatcher.mSavedFragmentState = new Bundle();
    }
    if (FragmentManager.isLoggingEnabled(2)) {
      Log.v("FragmentManager", "Instantiated fragment " + paramFragmentLifecycleCallbacksDispatcher);
    }
  }
  
  private boolean isFragmentViewChild(View paramView)
  {
    if (paramView == this.mFragment.mView) {
      return true;
    }
    for (paramView = paramView.getParent(); paramView != null; paramView = paramView.getParent()) {
      if (paramView == this.mFragment.mView) {
        return true;
      }
    }
    return false;
  }
  
  private Bundle saveBasicState()
  {
    Object localObject2 = new Bundle();
    this.mFragment.performSaveInstanceState((Bundle)localObject2);
    this.mDispatcher.dispatchOnFragmentSaveInstanceState(this.mFragment, (Bundle)localObject2, false);
    Object localObject1 = localObject2;
    if (((Bundle)localObject2).isEmpty()) {
      localObject1 = null;
    }
    if (this.mFragment.mView != null) {
      saveViewState();
    }
    localObject2 = localObject1;
    if (this.mFragment.mSavedViewState != null)
    {
      localObject2 = localObject1;
      if (localObject1 == null) {
        localObject2 = new Bundle();
      }
      ((Bundle)localObject2).putSparseParcelableArray("android:view_state", this.mFragment.mSavedViewState);
    }
    localObject1 = localObject2;
    if (this.mFragment.mSavedViewRegistryState != null)
    {
      localObject1 = localObject2;
      if (localObject2 == null) {
        localObject1 = new Bundle();
      }
      ((Bundle)localObject1).putBundle("android:view_registry_state", this.mFragment.mSavedViewRegistryState);
    }
    localObject2 = localObject1;
    if (!this.mFragment.mUserVisibleHint)
    {
      localObject2 = localObject1;
      if (localObject1 == null) {
        localObject2 = new Bundle();
      }
      ((Bundle)localObject2).putBoolean("android:user_visible_hint", this.mFragment.mUserVisibleHint);
    }
    return (Bundle)localObject2;
  }
  
  void activityCreated()
  {
    if (FragmentManager.isLoggingEnabled(3)) {
      Log.d("FragmentManager", "moveto ACTIVITY_CREATED: " + this.mFragment);
    }
    Fragment localFragment = this.mFragment;
    localFragment.performActivityCreated(localFragment.mSavedFragmentState);
    FragmentLifecycleCallbacksDispatcher localFragmentLifecycleCallbacksDispatcher = this.mDispatcher;
    localFragment = this.mFragment;
    localFragmentLifecycleCallbacksDispatcher.dispatchOnFragmentActivityCreated(localFragment, localFragment.mSavedFragmentState, false);
  }
  
  void addViewToContainer()
  {
    int i = this.mFragmentStore.findFragmentIndexInContainer(this.mFragment);
    this.mFragment.mContainer.addView(this.mFragment.mView, i);
  }
  
  void attach()
  {
    if (FragmentManager.isLoggingEnabled(3)) {
      Log.d("FragmentManager", "moveto ATTACHED: " + this.mFragment);
    }
    if (this.mFragment.mTarget != null)
    {
      localObject = this.mFragmentStore.getFragmentStateManager(this.mFragment.mTarget.mWho);
      if (localObject != null)
      {
        Fragment localFragment = this.mFragment;
        localFragment.mTargetWho = localFragment.mTarget.mWho;
        this.mFragment.mTarget = null;
      }
      else
      {
        throw new IllegalStateException("Fragment " + this.mFragment + " declared target fragment " + this.mFragment.mTarget + " that does not belong to this FragmentManager!");
      }
    }
    else if (this.mFragment.mTargetWho != null)
    {
      localObject = this.mFragmentStore.getFragmentStateManager(this.mFragment.mTargetWho);
      if (localObject == null) {
        throw new IllegalStateException("Fragment " + this.mFragment + " declared target fragment " + this.mFragment.mTargetWho + " that does not belong to this FragmentManager!");
      }
    }
    else
    {
      localObject = null;
    }
    if ((localObject != null) && ((FragmentManager.USE_STATE_MANAGER) || (((FragmentStateManager)localObject).getFragment().mState < 1))) {
      ((FragmentStateManager)localObject).moveToExpectedState();
    }
    Object localObject = this.mFragment;
    ((Fragment)localObject).mHost = ((Fragment)localObject).mFragmentManager.getHost();
    localObject = this.mFragment;
    ((Fragment)localObject).mParentFragment = ((Fragment)localObject).mFragmentManager.getParent();
    this.mDispatcher.dispatchOnFragmentPreAttached(this.mFragment, false);
    this.mFragment.performAttach();
    this.mDispatcher.dispatchOnFragmentAttached(this.mFragment, false);
  }
  
  int computeExpectedState()
  {
    if (this.mFragment.mFragmentManager == null) {
      return this.mFragment.mState;
    }
    int j = this.mFragmentManagerState;
    switch (2.$SwitchMap$androidx$lifecycle$Lifecycle$State[this.mFragment.mMaxState.ordinal()])
    {
    default: 
      j = Math.min(j, -1);
      break;
    case 4: 
      j = Math.min(j, 0);
      break;
    case 3: 
      j = Math.min(j, 1);
      break;
    case 2: 
      j = Math.min(j, 5);
      break;
    }
    int i = j;
    if (this.mFragment.mFromLayout) {
      if (this.mFragment.mInLayout)
      {
        j = Math.max(this.mFragmentManagerState, 2);
        i = j;
        if (this.mFragment.mView != null)
        {
          i = j;
          if (this.mFragment.mView.getParent() == null) {
            i = Math.min(j, 2);
          }
        }
      }
      else if (this.mFragmentManagerState < 4)
      {
        i = Math.min(j, this.mFragment.mState);
      }
      else
      {
        i = Math.min(j, 1);
      }
    }
    j = i;
    if (!this.mFragment.mAdded) {
      j = Math.min(i, 1);
    }
    Object localObject2 = null;
    Object localObject1 = localObject2;
    if (FragmentManager.USE_STATE_MANAGER)
    {
      localObject1 = localObject2;
      if (this.mFragment.mContainer != null) {
        localObject1 = SpecialEffectsController.getOrCreateController(this.mFragment.mContainer, this.mFragment.getParentFragmentManager()).getAwaitingCompletionLifecycleImpact(this);
      }
    }
    if (localObject1 == SpecialEffectsController.Operation.LifecycleImpact.ADDING)
    {
      i = Math.min(j, 6);
    }
    else if (localObject1 == SpecialEffectsController.Operation.LifecycleImpact.REMOVING)
    {
      i = Math.max(j, 3);
    }
    else
    {
      i = j;
      if (this.mFragment.mRemoving) {
        if (this.mFragment.isInBackStack()) {
          i = Math.min(j, 1);
        } else {
          i = Math.min(j, -1);
        }
      }
    }
    j = i;
    if (this.mFragment.mDeferStart)
    {
      j = i;
      if (this.mFragment.mState < 5) {
        j = Math.min(i, 4);
      }
    }
    if (FragmentManager.isLoggingEnabled(2)) {
      Log.v("FragmentManager", "computeExpectedState() of " + j + " for " + this.mFragment);
    }
    return j;
  }
  
  void create()
  {
    if (FragmentManager.isLoggingEnabled(3)) {
      Log.d("FragmentManager", "moveto CREATED: " + this.mFragment);
    }
    Object localObject;
    if (!this.mFragment.mIsCreated)
    {
      localObject = this.mDispatcher;
      Fragment localFragment = this.mFragment;
      ((FragmentLifecycleCallbacksDispatcher)localObject).dispatchOnFragmentPreCreated(localFragment, localFragment.mSavedFragmentState, false);
      localObject = this.mFragment;
      ((Fragment)localObject).performCreate(((Fragment)localObject).mSavedFragmentState);
      localObject = this.mDispatcher;
      localFragment = this.mFragment;
      ((FragmentLifecycleCallbacksDispatcher)localObject).dispatchOnFragmentCreated(localFragment, localFragment.mSavedFragmentState, false);
    }
    else
    {
      localObject = this.mFragment;
      ((Fragment)localObject).restoreChildFragmentState(((Fragment)localObject).mSavedFragmentState);
      this.mFragment.mState = 1;
    }
  }
  
  void createView()
  {
    if (this.mFragment.mFromLayout) {
      return;
    }
    if (FragmentManager.isLoggingEnabled(3)) {
      Log.d("FragmentManager", "moveto CREATE_VIEW: " + this.mFragment);
    }
    Object localObject1 = this.mFragment;
    Object localObject4 = ((Fragment)localObject1).performGetLayoutInflater(((Fragment)localObject1).mSavedFragmentState);
    localObject1 = null;
    final Object localObject2;
    if (this.mFragment.mContainer != null) {
      localObject1 = this.mFragment.mContainer;
    } else if (this.mFragment.mContainerId != 0) {
      if (this.mFragment.mContainerId != -1)
      {
        localObject3 = (ViewGroup)this.mFragment.mFragmentManager.getContainer().onFindViewById(this.mFragment.mContainerId);
        localObject1 = localObject3;
        if (localObject3 == null) {
          if (this.mFragment.mRestored)
          {
            localObject1 = localObject3;
          }
          else
          {
            try
            {
              localObject1 = this.mFragment.getResources().getResourceName(this.mFragment.mContainerId);
            }
            catch (Resources.NotFoundException localNotFoundException)
            {
              localObject2 = "unknown";
            }
            localObject3 = new StringBuilder().append("No view found for id 0x");
            localObject4 = Integer.toHexString(this.mFragment.mContainerId);
            Log5ECF72.a(localObject4);
            LogE84000.a(localObject4);
            Log229316.a(localObject4);
            throw new IllegalArgumentException((String)localObject4 + " (" + (String)localObject2 + ") for fragment " + this.mFragment);
          }
        }
      }
      else
      {
        throw new IllegalArgumentException("Cannot create fragment " + this.mFragment + " for a container view with no id");
      }
    }
    this.mFragment.mContainer = ((ViewGroup)localObject2);
    Object localObject3 = this.mFragment;
    ((Fragment)localObject3).performCreateView((LayoutInflater)localObject4, (ViewGroup)localObject2, ((Fragment)localObject3).mSavedFragmentState);
    if (this.mFragment.mView != null)
    {
      localObject3 = this.mFragment.mView;
      boolean bool2 = false;
      ((View)localObject3).setSaveFromParentEnabled(false);
      this.mFragment.mView.setTag(R.id.fragment_container_view_tag, this.mFragment);
      if (localObject2 != null) {
        addViewToContainer();
      }
      if (this.mFragment.mHidden) {
        this.mFragment.mView.setVisibility(8);
      }
      if (ViewCompat.isAttachedToWindow(this.mFragment.mView))
      {
        ViewCompat.requestApplyInsets(this.mFragment.mView);
      }
      else
      {
        localObject2 = this.mFragment.mView;
        ((View)localObject2).addOnAttachStateChangeListener(new View.OnAttachStateChangeListener()
        {
          public void onViewAttachedToWindow(View paramAnonymousView)
          {
            localObject2.removeOnAttachStateChangeListener(this);
            ViewCompat.requestApplyInsets(localObject2);
          }
          
          public void onViewDetachedFromWindow(View paramAnonymousView) {}
        });
      }
      this.mFragment.performViewCreated();
      localObject2 = this.mDispatcher;
      localObject3 = this.mFragment;
      ((FragmentLifecycleCallbacksDispatcher)localObject2).dispatchOnFragmentViewCreated((Fragment)localObject3, ((Fragment)localObject3).mView, this.mFragment.mSavedFragmentState, false);
      int i = this.mFragment.mView.getVisibility();
      float f = this.mFragment.mView.getAlpha();
      if (FragmentManager.USE_STATE_MANAGER)
      {
        this.mFragment.setPostOnViewCreatedAlpha(f);
        if ((this.mFragment.mContainer != null) && (i == 0))
        {
          localObject2 = this.mFragment.mView.findFocus();
          if (localObject2 != null)
          {
            this.mFragment.setFocusedView((View)localObject2);
            if (FragmentManager.isLoggingEnabled(2)) {
              Log.v("FragmentManager", "requestFocus: Saved focused view " + localObject2 + " for Fragment " + this.mFragment);
            }
          }
          this.mFragment.mView.setAlpha(0.0F);
        }
      }
      else
      {
        localObject2 = this.mFragment;
        boolean bool1 = bool2;
        if (i == 0)
        {
          bool1 = bool2;
          if (((Fragment)localObject2).mContainer != null) {
            bool1 = true;
          }
        }
        ((Fragment)localObject2).mIsNewlyAdded = bool1;
      }
    }
    this.mFragment.mState = 2;
  }
  
  void destroy()
  {
    if (FragmentManager.isLoggingEnabled(3)) {
      Log.d("FragmentManager", "movefrom CREATED: " + this.mFragment);
    }
    int i;
    if ((this.mFragment.mRemoving) && (!this.mFragment.isInBackStack())) {
      i = 1;
    } else {
      i = 0;
    }
    int j;
    if ((i == 0) && (!this.mFragmentStore.getNonConfig().shouldDestroy(this.mFragment))) {
      j = 0;
    } else {
      j = 1;
    }
    Object localObject1;
    if (j != 0)
    {
      localObject1 = this.mFragment.mHost;
      boolean bool;
      if ((localObject1 instanceof ViewModelStoreOwner)) {
        bool = this.mFragmentStore.getNonConfig().isCleared();
      } else if ((((FragmentHostCallback)localObject1).getContext() instanceof Activity)) {
        bool = true ^ ((Activity)((FragmentHostCallback)localObject1).getContext()).isChangingConfigurations();
      } else {
        bool = true;
      }
      if ((i != 0) || (bool)) {
        this.mFragmentStore.getNonConfig().clearNonConfigState(this.mFragment);
      }
      this.mFragment.performDestroy();
      this.mDispatcher.dispatchOnFragmentDestroyed(this.mFragment, false);
      localObject1 = this.mFragmentStore.getActiveFragmentStateManagers().iterator();
      while (((Iterator)localObject1).hasNext())
      {
        Object localObject2 = (FragmentStateManager)((Iterator)localObject1).next();
        if (localObject2 != null)
        {
          localObject2 = ((FragmentStateManager)localObject2).getFragment();
          if (this.mFragment.mWho.equals(((Fragment)localObject2).mTargetWho))
          {
            ((Fragment)localObject2).mTarget = this.mFragment;
            ((Fragment)localObject2).mTargetWho = null;
          }
        }
      }
      if (this.mFragment.mTargetWho != null)
      {
        localObject1 = this.mFragment;
        ((Fragment)localObject1).mTarget = this.mFragmentStore.findActiveFragment(((Fragment)localObject1).mTargetWho);
      }
      this.mFragmentStore.makeInactive(this);
    }
    else
    {
      if (this.mFragment.mTargetWho != null)
      {
        localObject1 = this.mFragmentStore.findActiveFragment(this.mFragment.mTargetWho);
        if ((localObject1 != null) && (((Fragment)localObject1).mRetainInstance)) {
          this.mFragment.mTarget = ((Fragment)localObject1);
        }
      }
      this.mFragment.mState = 0;
    }
  }
  
  void destroyFragmentView()
  {
    if (FragmentManager.isLoggingEnabled(3)) {
      Log.d("FragmentManager", "movefrom CREATE_VIEW: " + this.mFragment);
    }
    if ((this.mFragment.mContainer != null) && (this.mFragment.mView != null)) {
      this.mFragment.mContainer.removeView(this.mFragment.mView);
    }
    this.mFragment.performDestroyView();
    this.mDispatcher.dispatchOnFragmentViewDestroyed(this.mFragment, false);
    this.mFragment.mContainer = null;
    this.mFragment.mView = null;
    this.mFragment.mViewLifecycleOwner = null;
    this.mFragment.mViewLifecycleOwnerLiveData.setValue(null);
    this.mFragment.mInLayout = false;
  }
  
  void detach()
  {
    if (FragmentManager.isLoggingEnabled(3)) {
      Log.d("FragmentManager", "movefrom ATTACHED: " + this.mFragment);
    }
    this.mFragment.performDetach();
    FragmentLifecycleCallbacksDispatcher localFragmentLifecycleCallbacksDispatcher = this.mDispatcher;
    Fragment localFragment = this.mFragment;
    int j = 0;
    localFragmentLifecycleCallbacksDispatcher.dispatchOnFragmentDetached(localFragment, false);
    this.mFragment.mState = -1;
    this.mFragment.mHost = null;
    this.mFragment.mParentFragment = null;
    this.mFragment.mFragmentManager = null;
    int i = j;
    if (this.mFragment.mRemoving)
    {
      i = j;
      if (!this.mFragment.isInBackStack()) {
        i = 1;
      }
    }
    if ((i != 0) || (this.mFragmentStore.getNonConfig().shouldDestroy(this.mFragment)))
    {
      if (FragmentManager.isLoggingEnabled(3)) {
        Log.d("FragmentManager", "initState called for fragment: " + this.mFragment);
      }
      this.mFragment.initState();
    }
  }
  
  void ensureInflatedView()
  {
    if ((this.mFragment.mFromLayout) && (this.mFragment.mInLayout) && (!this.mFragment.mPerformedCreateView))
    {
      if (FragmentManager.isLoggingEnabled(3)) {
        Log.d("FragmentManager", "moveto CREATE_VIEW: " + this.mFragment);
      }
      Object localObject = this.mFragment;
      ((Fragment)localObject).performCreateView(((Fragment)localObject).performGetLayoutInflater(((Fragment)localObject).mSavedFragmentState), null, this.mFragment.mSavedFragmentState);
      if (this.mFragment.mView != null)
      {
        this.mFragment.mView.setSaveFromParentEnabled(false);
        this.mFragment.mView.setTag(R.id.fragment_container_view_tag, this.mFragment);
        if (this.mFragment.mHidden) {
          this.mFragment.mView.setVisibility(8);
        }
        this.mFragment.performViewCreated();
        localObject = this.mDispatcher;
        Fragment localFragment = this.mFragment;
        ((FragmentLifecycleCallbacksDispatcher)localObject).dispatchOnFragmentViewCreated(localFragment, localFragment.mView, this.mFragment.mSavedFragmentState, false);
        this.mFragment.mState = 2;
      }
    }
  }
  
  Fragment getFragment()
  {
    return this.mFragment;
  }
  
  void moveToExpectedState()
  {
    if (this.mMovingToState)
    {
      if (FragmentManager.isLoggingEnabled(2)) {
        Log.v("FragmentManager", "Ignoring re-entrant call to moveToExpectedState() for " + getFragment());
      }
      return;
    }
    try
    {
      this.mMovingToState = true;
      Object localObject1;
      for (;;)
      {
        int i = computeExpectedState();
        if (i == this.mFragment.mState) {
          break;
        }
        if (i > this.mFragment.mState) {
          switch (this.mFragment.mState + 1)
          {
          default: 
            break;
          case 7: 
            resume();
            break;
          case 6: 
            this.mFragment.mState = 6;
            break;
          case 5: 
            start();
            break;
          case 4: 
            if ((this.mFragment.mView != null) && (this.mFragment.mContainer != null))
            {
              localObject1 = SpecialEffectsController.getOrCreateController(this.mFragment.mContainer, this.mFragment.getParentFragmentManager());
              i = this.mFragment.mView.getVisibility();
              ((SpecialEffectsController)localObject1).enqueueAdd(SpecialEffectsController.Operation.State.from(i), this);
            }
            this.mFragment.mState = 4;
            break;
          case 3: 
            activityCreated();
            break;
          case 2: 
            ensureInflatedView();
            createView();
            break;
          case 1: 
            create();
            break;
          case 0: 
            attach();
          }
        } else {
          switch (this.mFragment.mState - 1)
          {
          default: 
            break;
          case 6: 
            pause();
            break;
          case 5: 
            this.mFragment.mState = 5;
            break;
          case 4: 
            stop();
            break;
          case 3: 
            if (FragmentManager.isLoggingEnabled(3))
            {
              localObject1 = new java/lang/StringBuilder;
              ((StringBuilder)localObject1).<init>();
              Log.d("FragmentManager", "movefrom ACTIVITY_CREATED: " + this.mFragment);
            }
            if ((this.mFragment.mView != null) && (this.mFragment.mSavedViewState == null)) {
              saveViewState();
            }
            if ((this.mFragment.mView != null) && (this.mFragment.mContainer != null)) {
              SpecialEffectsController.getOrCreateController(this.mFragment.mContainer, this.mFragment.getParentFragmentManager()).enqueueRemove(this);
            }
            this.mFragment.mState = 3;
            break;
          case 2: 
            this.mFragment.mInLayout = false;
            this.mFragment.mState = 2;
            break;
          case 1: 
            destroyFragmentView();
            this.mFragment.mState = 1;
            break;
          case 0: 
            destroy();
            break;
          case -1: 
            detach();
          }
        }
      }
      if ((FragmentManager.USE_STATE_MANAGER) && (this.mFragment.mHiddenChanged))
      {
        if ((this.mFragment.mView != null) && (this.mFragment.mContainer != null))
        {
          localObject1 = SpecialEffectsController.getOrCreateController(this.mFragment.mContainer, this.mFragment.getParentFragmentManager());
          if (this.mFragment.mHidden) {
            ((SpecialEffectsController)localObject1).enqueueHide(this);
          } else {
            ((SpecialEffectsController)localObject1).enqueueShow(this);
          }
        }
        if (this.mFragment.mFragmentManager != null) {
          this.mFragment.mFragmentManager.invalidateMenuForFragment(this.mFragment);
        }
        this.mFragment.mHiddenChanged = false;
        localObject1 = this.mFragment;
        ((Fragment)localObject1).onHiddenChanged(((Fragment)localObject1).mHidden);
      }
      return;
    }
    finally
    {
      this.mMovingToState = false;
    }
  }
  
  void pause()
  {
    if (FragmentManager.isLoggingEnabled(3)) {
      Log.d("FragmentManager", "movefrom RESUMED: " + this.mFragment);
    }
    this.mFragment.performPause();
    this.mDispatcher.dispatchOnFragmentPaused(this.mFragment, false);
  }
  
  void restoreState(ClassLoader paramClassLoader)
  {
    if (this.mFragment.mSavedFragmentState == null) {
      return;
    }
    this.mFragment.mSavedFragmentState.setClassLoader(paramClassLoader);
    paramClassLoader = this.mFragment;
    paramClassLoader.mSavedViewState = paramClassLoader.mSavedFragmentState.getSparseParcelableArray("android:view_state");
    paramClassLoader = this.mFragment;
    paramClassLoader.mSavedViewRegistryState = paramClassLoader.mSavedFragmentState.getBundle("android:view_registry_state");
    paramClassLoader = this.mFragment;
    paramClassLoader.mTargetWho = paramClassLoader.mSavedFragmentState.getString("android:target_state");
    if (this.mFragment.mTargetWho != null)
    {
      paramClassLoader = this.mFragment;
      paramClassLoader.mTargetRequestCode = paramClassLoader.mSavedFragmentState.getInt("android:target_req_state", 0);
    }
    if (this.mFragment.mSavedUserVisibleHint != null)
    {
      paramClassLoader = this.mFragment;
      paramClassLoader.mUserVisibleHint = paramClassLoader.mSavedUserVisibleHint.booleanValue();
      this.mFragment.mSavedUserVisibleHint = null;
    }
    else
    {
      paramClassLoader = this.mFragment;
      paramClassLoader.mUserVisibleHint = paramClassLoader.mSavedFragmentState.getBoolean("android:user_visible_hint", true);
    }
    if (!this.mFragment.mUserVisibleHint) {
      this.mFragment.mDeferStart = true;
    }
  }
  
  void resume()
  {
    if (FragmentManager.isLoggingEnabled(3)) {
      Log.d("FragmentManager", "moveto RESUMED: " + this.mFragment);
    }
    Object localObject = this.mFragment.getFocusedView();
    if ((localObject != null) && (isFragmentViewChild((View)localObject)))
    {
      boolean bool = ((View)localObject).requestFocus();
      if (FragmentManager.isLoggingEnabled(2))
      {
        StringBuilder localStringBuilder = new StringBuilder().append("requestFocus: Restoring focused view ").append(localObject).append(" ");
        if (bool) {
          localObject = "succeeded";
        } else {
          localObject = "failed";
        }
        Log.v("FragmentManager", (String)localObject + " on Fragment " + this.mFragment + " resulting in focused view " + this.mFragment.mView.findFocus());
      }
    }
    this.mFragment.setFocusedView(null);
    this.mFragment.performResume();
    this.mDispatcher.dispatchOnFragmentResumed(this.mFragment, false);
    this.mFragment.mSavedFragmentState = null;
    this.mFragment.mSavedViewState = null;
    this.mFragment.mSavedViewRegistryState = null;
  }
  
  Fragment.SavedState saveInstanceState()
  {
    int i = this.mFragment.mState;
    Fragment.SavedState localSavedState = null;
    if (i > -1)
    {
      Bundle localBundle = saveBasicState();
      if (localBundle != null) {
        localSavedState = new Fragment.SavedState(localBundle);
      }
      return localSavedState;
    }
    return null;
  }
  
  FragmentState saveState()
  {
    FragmentState localFragmentState = new FragmentState(this.mFragment);
    if ((this.mFragment.mState > -1) && (localFragmentState.mSavedFragmentState == null))
    {
      localFragmentState.mSavedFragmentState = saveBasicState();
      if (this.mFragment.mTargetWho != null)
      {
        if (localFragmentState.mSavedFragmentState == null) {
          localFragmentState.mSavedFragmentState = new Bundle();
        }
        localFragmentState.mSavedFragmentState.putString("android:target_state", this.mFragment.mTargetWho);
        if (this.mFragment.mTargetRequestCode != 0) {
          localFragmentState.mSavedFragmentState.putInt("android:target_req_state", this.mFragment.mTargetRequestCode);
        }
      }
    }
    else
    {
      localFragmentState.mSavedFragmentState = this.mFragment.mSavedFragmentState;
    }
    return localFragmentState;
  }
  
  void saveViewState()
  {
    if (this.mFragment.mView == null) {
      return;
    }
    Object localObject = new SparseArray();
    this.mFragment.mView.saveHierarchyState((SparseArray)localObject);
    if (((SparseArray)localObject).size() > 0) {
      this.mFragment.mSavedViewState = ((SparseArray)localObject);
    }
    localObject = new Bundle();
    this.mFragment.mViewLifecycleOwner.performSave((Bundle)localObject);
    if (!((Bundle)localObject).isEmpty()) {
      this.mFragment.mSavedViewRegistryState = ((Bundle)localObject);
    }
  }
  
  void setFragmentManagerState(int paramInt)
  {
    this.mFragmentManagerState = paramInt;
  }
  
  void start()
  {
    if (FragmentManager.isLoggingEnabled(3)) {
      Log.d("FragmentManager", "moveto STARTED: " + this.mFragment);
    }
    this.mFragment.performStart();
    this.mDispatcher.dispatchOnFragmentStarted(this.mFragment, false);
  }
  
  void stop()
  {
    if (FragmentManager.isLoggingEnabled(3)) {
      Log.d("FragmentManager", "movefrom STARTED: " + this.mFragment);
    }
    this.mFragment.performStop();
    this.mDispatcher.dispatchOnFragmentStopped(this.mFragment, false);
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/fragment/app/FragmentStateManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */