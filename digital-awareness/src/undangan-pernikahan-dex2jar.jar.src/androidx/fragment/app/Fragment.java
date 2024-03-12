package androidx.fragment.app;

import android.animation.Animator;
import android.app.Activity;
import android.app.Application;
import android.content.ComponentCallbacks;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.IntentSender;
import android.content.IntentSender.SendIntentException;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.ClassLoaderCreator;
import android.os.Parcelable.Creator;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnCreateContextMenuListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultCaller;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.ActivityResultRegistry;
import androidx.activity.result.ActivityResultRegistryOwner;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.arch.core.util.Function;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.app.SharedElementCallback;
import androidx.core.view.LayoutInflaterCompat;
import androidx.lifecycle.HasDefaultViewModelProviderFactory;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.Lifecycle.Event;
import androidx.lifecycle.Lifecycle.State;
import androidx.lifecycle.LifecycleEventObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LifecycleRegistry;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.SavedStateViewModelFactory;
import androidx.lifecycle.ViewModelProvider.Factory;
import androidx.lifecycle.ViewModelStore;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.lifecycle.ViewTreeLifecycleOwner;
import androidx.lifecycle.ViewTreeViewModelStoreOwner;
import androidx.loader.app.LoaderManager;
import androidx.savedstate.SavedStateRegistry;
import androidx.savedstate.SavedStateRegistryController;
import androidx.savedstate.SavedStateRegistryOwner;
import androidx.savedstate.ViewTreeSavedStateRegistryOwner;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import mt.Log229316;
import mt.Log5ECF72;
import mt.LogE84000;

public class Fragment
  implements ComponentCallbacks, View.OnCreateContextMenuListener, LifecycleOwner, ViewModelStoreOwner, HasDefaultViewModelProviderFactory, SavedStateRegistryOwner, ActivityResultCaller
{
  static final int ACTIVITY_CREATED = 4;
  static final int ATTACHED = 0;
  static final int AWAITING_ENTER_EFFECTS = 6;
  static final int AWAITING_EXIT_EFFECTS = 3;
  static final int CREATED = 1;
  static final int INITIALIZING = -1;
  static final int RESUMED = 7;
  static final int STARTED = 5;
  static final Object USE_DEFAULT_TRANSITION = new Object();
  static final int VIEW_CREATED = 2;
  boolean mAdded;
  AnimationInfo mAnimationInfo;
  Bundle mArguments;
  int mBackStackNesting;
  private boolean mCalled;
  FragmentManager mChildFragmentManager = new FragmentManagerImpl();
  ViewGroup mContainer;
  int mContainerId;
  private int mContentLayoutId;
  ViewModelProvider.Factory mDefaultFactory;
  boolean mDeferStart;
  boolean mDetached;
  int mFragmentId;
  FragmentManager mFragmentManager;
  boolean mFromLayout;
  boolean mHasMenu;
  boolean mHidden;
  boolean mHiddenChanged;
  FragmentHostCallback<?> mHost;
  boolean mInLayout;
  boolean mIsCreated;
  boolean mIsNewlyAdded;
  private Boolean mIsPrimaryNavigationFragment = null;
  LayoutInflater mLayoutInflater;
  LifecycleRegistry mLifecycleRegistry;
  Lifecycle.State mMaxState = Lifecycle.State.RESUMED;
  boolean mMenuVisible = true;
  private final AtomicInteger mNextLocalRequestCode = new AtomicInteger();
  private final ArrayList<OnPreAttachedListener> mOnPreAttachedListeners = new ArrayList();
  Fragment mParentFragment;
  boolean mPerformedCreateView;
  float mPostponedAlpha;
  Runnable mPostponedDurationRunnable = new Runnable()
  {
    public void run()
    {
      Fragment.this.startPostponedEnterTransition();
    }
  };
  boolean mRemoving;
  boolean mRestored;
  boolean mRetainInstance;
  boolean mRetainInstanceChangedWhileDetached;
  Bundle mSavedFragmentState;
  SavedStateRegistryController mSavedStateRegistryController;
  Boolean mSavedUserVisibleHint;
  Bundle mSavedViewRegistryState;
  SparseArray<Parcelable> mSavedViewState;
  int mState = -1;
  String mTag;
  Fragment mTarget;
  int mTargetRequestCode;
  String mTargetWho = null;
  boolean mUserVisibleHint = true;
  View mView;
  FragmentViewLifecycleOwner mViewLifecycleOwner;
  MutableLiveData<LifecycleOwner> mViewLifecycleOwnerLiveData = new MutableLiveData();
  String mWho = UUID.randomUUID().toString();
  
  public Fragment()
  {
    initLifecycle();
  }
  
  public Fragment(int paramInt)
  {
    this();
    this.mContentLayoutId = paramInt;
  }
  
  private AnimationInfo ensureAnimationInfo()
  {
    if (this.mAnimationInfo == null) {
      this.mAnimationInfo = new AnimationInfo();
    }
    return this.mAnimationInfo;
  }
  
  private int getMinimumMaxLifecycleState()
  {
    if ((this.mMaxState != Lifecycle.State.INITIALIZED) && (this.mParentFragment != null)) {
      return Math.min(this.mMaxState.ordinal(), this.mParentFragment.getMinimumMaxLifecycleState());
    }
    return this.mMaxState.ordinal();
  }
  
  private void initLifecycle()
  {
    this.mLifecycleRegistry = new LifecycleRegistry(this);
    this.mSavedStateRegistryController = SavedStateRegistryController.create(this);
    this.mDefaultFactory = null;
  }
  
  @Deprecated
  public static Fragment instantiate(Context paramContext, String paramString)
  {
    return instantiate(paramContext, paramString, null);
  }
  
  @Deprecated
  public static Fragment instantiate(Context paramContext, String paramString, Bundle paramBundle)
  {
    try
    {
      paramContext = (Fragment)FragmentFactory.loadFragmentClass(paramContext.getClassLoader(), paramString).getConstructor(new Class[0]).newInstance(new Object[0]);
      if (paramBundle != null)
      {
        paramBundle.setClassLoader(paramContext.getClass().getClassLoader());
        paramContext.setArguments(paramBundle);
      }
      return paramContext;
    }
    catch (InvocationTargetException paramContext)
    {
      throw new InstantiationException("Unable to instantiate fragment " + paramString + ": calling Fragment constructor caused an exception", paramContext);
    }
    catch (NoSuchMethodException paramContext)
    {
      throw new InstantiationException("Unable to instantiate fragment " + paramString + ": could not find Fragment constructor", paramContext);
    }
    catch (IllegalAccessException paramContext)
    {
      throw new InstantiationException("Unable to instantiate fragment " + paramString + ": make sure class name exists, is public, and has an empty constructor that is public", paramContext);
    }
    catch (InstantiationException paramContext)
    {
      throw new InstantiationException("Unable to instantiate fragment " + paramString + ": make sure class name exists, is public, and has an empty constructor that is public", paramContext);
    }
  }
  
  private <I, O> ActivityResultLauncher<I> prepareCallInternal(final ActivityResultContract<I, O> paramActivityResultContract, final Function<Void, ActivityResultRegistry> paramFunction, final ActivityResultCallback<O> paramActivityResultCallback)
  {
    if (this.mState <= 1)
    {
      final AtomicReference localAtomicReference = new AtomicReference();
      registerOnPreAttachListener(new OnPreAttachedListener(paramFunction)
      {
        void onPreAttached()
        {
          String str = Fragment.this.generateActivityResultKey();
          ActivityResultRegistry localActivityResultRegistry = (ActivityResultRegistry)paramFunction.apply(null);
          localAtomicReference.set(localActivityResultRegistry.register(str, Fragment.this, paramActivityResultContract, paramActivityResultCallback));
        }
      });
      new ActivityResultLauncher()
      {
        public ActivityResultContract<I, ?> getContract()
        {
          return paramActivityResultContract;
        }
        
        public void launch(I paramAnonymousI, ActivityOptionsCompat paramAnonymousActivityOptionsCompat)
        {
          ActivityResultLauncher localActivityResultLauncher = (ActivityResultLauncher)localAtomicReference.get();
          if (localActivityResultLauncher != null)
          {
            localActivityResultLauncher.launch(paramAnonymousI, paramAnonymousActivityOptionsCompat);
            return;
          }
          throw new IllegalStateException("Operation cannot be started before fragment is in created state");
        }
        
        public void unregister()
        {
          ActivityResultLauncher localActivityResultLauncher = (ActivityResultLauncher)localAtomicReference.getAndSet(null);
          if (localActivityResultLauncher != null) {
            localActivityResultLauncher.unregister();
          }
        }
      };
    }
    throw new IllegalStateException("Fragment " + this + " is attempting to registerForActivityResult after being created. Fragments must call registerForActivityResult() before they are created (i.e. initialization, onAttach(), or onCreate()).");
  }
  
  private void registerOnPreAttachListener(OnPreAttachedListener paramOnPreAttachedListener)
  {
    if (this.mState >= 0) {
      paramOnPreAttachedListener.onPreAttached();
    } else {
      this.mOnPreAttachedListeners.add(paramOnPreAttachedListener);
    }
  }
  
  private void restoreViewState()
  {
    if (FragmentManager.isLoggingEnabled(3)) {
      Log.d("FragmentManager", "moveto RESTORE_VIEW_STATE: " + this);
    }
    if (this.mView != null) {
      restoreViewState(this.mSavedFragmentState);
    }
    this.mSavedFragmentState = null;
  }
  
  void callStartTransitionListener(boolean paramBoolean)
  {
    final Object localObject = this.mAnimationInfo;
    if (localObject == null)
    {
      localObject = null;
    }
    else
    {
      ((AnimationInfo)localObject).mEnterTransitionPostponed = false;
      localObject = this.mAnimationInfo.mStartEnterTransitionListener;
      this.mAnimationInfo.mStartEnterTransitionListener = null;
    }
    if (localObject != null)
    {
      ((OnStartEnterTransitionListener)localObject).onStartEnterTransition();
    }
    else if ((FragmentManager.USE_STATE_MANAGER) && (this.mView != null))
    {
      localObject = this.mContainer;
      if (localObject != null)
      {
        FragmentManager localFragmentManager = this.mFragmentManager;
        if (localFragmentManager != null)
        {
          localObject = SpecialEffectsController.getOrCreateController((ViewGroup)localObject, localFragmentManager);
          ((SpecialEffectsController)localObject).markPostponedState();
          if (paramBoolean) {
            this.mHost.getHandler().post(new Runnable()
            {
              public void run()
              {
                localObject.executePendingOperations();
              }
            });
          } else {
            ((SpecialEffectsController)localObject).executePendingOperations();
          }
        }
      }
    }
  }
  
  FragmentContainer createFragmentContainer()
  {
    new FragmentContainer()
    {
      public View onFindViewById(int paramAnonymousInt)
      {
        if (Fragment.this.mView != null) {
          return Fragment.this.mView.findViewById(paramAnonymousInt);
        }
        throw new IllegalStateException("Fragment " + Fragment.this + " does not have a view");
      }
      
      public boolean onHasView()
      {
        boolean bool;
        if (Fragment.this.mView != null) {
          bool = true;
        } else {
          bool = false;
        }
        return bool;
      }
    };
  }
  
  public void dump(String paramString, FileDescriptor paramFileDescriptor, PrintWriter paramPrintWriter, String[] paramArrayOfString)
  {
    paramPrintWriter.print(paramString);
    paramPrintWriter.print("mFragmentId=#");
    Object localObject = Integer.toHexString(this.mFragmentId);
    Log5ECF72.a(localObject);
    LogE84000.a(localObject);
    Log229316.a(localObject);
    paramPrintWriter.print((String)localObject);
    paramPrintWriter.print(" mContainerId=#");
    localObject = Integer.toHexString(this.mContainerId);
    Log5ECF72.a(localObject);
    LogE84000.a(localObject);
    Log229316.a(localObject);
    paramPrintWriter.print((String)localObject);
    paramPrintWriter.print(" mTag=");
    paramPrintWriter.println(this.mTag);
    paramPrintWriter.print(paramString);
    paramPrintWriter.print("mState=");
    paramPrintWriter.print(this.mState);
    paramPrintWriter.print(" mWho=");
    paramPrintWriter.print(this.mWho);
    paramPrintWriter.print(" mBackStackNesting=");
    paramPrintWriter.println(this.mBackStackNesting);
    paramPrintWriter.print(paramString);
    paramPrintWriter.print("mAdded=");
    paramPrintWriter.print(this.mAdded);
    paramPrintWriter.print(" mRemoving=");
    paramPrintWriter.print(this.mRemoving);
    paramPrintWriter.print(" mFromLayout=");
    paramPrintWriter.print(this.mFromLayout);
    paramPrintWriter.print(" mInLayout=");
    paramPrintWriter.println(this.mInLayout);
    paramPrintWriter.print(paramString);
    paramPrintWriter.print("mHidden=");
    paramPrintWriter.print(this.mHidden);
    paramPrintWriter.print(" mDetached=");
    paramPrintWriter.print(this.mDetached);
    paramPrintWriter.print(" mMenuVisible=");
    paramPrintWriter.print(this.mMenuVisible);
    paramPrintWriter.print(" mHasMenu=");
    paramPrintWriter.println(this.mHasMenu);
    paramPrintWriter.print(paramString);
    paramPrintWriter.print("mRetainInstance=");
    paramPrintWriter.print(this.mRetainInstance);
    paramPrintWriter.print(" mUserVisibleHint=");
    paramPrintWriter.println(this.mUserVisibleHint);
    if (this.mFragmentManager != null)
    {
      paramPrintWriter.print(paramString);
      paramPrintWriter.print("mFragmentManager=");
      paramPrintWriter.println(this.mFragmentManager);
    }
    if (this.mHost != null)
    {
      paramPrintWriter.print(paramString);
      paramPrintWriter.print("mHost=");
      paramPrintWriter.println(this.mHost);
    }
    if (this.mParentFragment != null)
    {
      paramPrintWriter.print(paramString);
      paramPrintWriter.print("mParentFragment=");
      paramPrintWriter.println(this.mParentFragment);
    }
    if (this.mArguments != null)
    {
      paramPrintWriter.print(paramString);
      paramPrintWriter.print("mArguments=");
      paramPrintWriter.println(this.mArguments);
    }
    if (this.mSavedFragmentState != null)
    {
      paramPrintWriter.print(paramString);
      paramPrintWriter.print("mSavedFragmentState=");
      paramPrintWriter.println(this.mSavedFragmentState);
    }
    if (this.mSavedViewState != null)
    {
      paramPrintWriter.print(paramString);
      paramPrintWriter.print("mSavedViewState=");
      paramPrintWriter.println(this.mSavedViewState);
    }
    if (this.mSavedViewRegistryState != null)
    {
      paramPrintWriter.print(paramString);
      paramPrintWriter.print("mSavedViewRegistryState=");
      paramPrintWriter.println(this.mSavedViewRegistryState);
    }
    localObject = getTargetFragment();
    if (localObject != null)
    {
      paramPrintWriter.print(paramString);
      paramPrintWriter.print("mTarget=");
      paramPrintWriter.print(localObject);
      paramPrintWriter.print(" mTargetRequestCode=");
      paramPrintWriter.println(this.mTargetRequestCode);
    }
    paramPrintWriter.print(paramString);
    paramPrintWriter.print("mPopDirection=");
    paramPrintWriter.println(getPopDirection());
    if (getEnterAnim() != 0)
    {
      paramPrintWriter.print(paramString);
      paramPrintWriter.print("getEnterAnim=");
      paramPrintWriter.println(getEnterAnim());
    }
    if (getExitAnim() != 0)
    {
      paramPrintWriter.print(paramString);
      paramPrintWriter.print("getExitAnim=");
      paramPrintWriter.println(getExitAnim());
    }
    if (getPopEnterAnim() != 0)
    {
      paramPrintWriter.print(paramString);
      paramPrintWriter.print("getPopEnterAnim=");
      paramPrintWriter.println(getPopEnterAnim());
    }
    if (getPopExitAnim() != 0)
    {
      paramPrintWriter.print(paramString);
      paramPrintWriter.print("getPopExitAnim=");
      paramPrintWriter.println(getPopExitAnim());
    }
    if (this.mContainer != null)
    {
      paramPrintWriter.print(paramString);
      paramPrintWriter.print("mContainer=");
      paramPrintWriter.println(this.mContainer);
    }
    if (this.mView != null)
    {
      paramPrintWriter.print(paramString);
      paramPrintWriter.print("mView=");
      paramPrintWriter.println(this.mView);
    }
    if (getAnimatingAway() != null)
    {
      paramPrintWriter.print(paramString);
      paramPrintWriter.print("mAnimatingAway=");
      paramPrintWriter.println(getAnimatingAway());
    }
    if (getContext() != null) {
      LoaderManager.getInstance(this).dump(paramString, paramFileDescriptor, paramPrintWriter, paramArrayOfString);
    }
    paramPrintWriter.print(paramString);
    paramPrintWriter.println("Child " + this.mChildFragmentManager + ":");
    this.mChildFragmentManager.dump(paramString + "  ", paramFileDescriptor, paramPrintWriter, paramArrayOfString);
  }
  
  public final boolean equals(Object paramObject)
  {
    return super.equals(paramObject);
  }
  
  Fragment findFragmentByWho(String paramString)
  {
    if (paramString.equals(this.mWho)) {
      return this;
    }
    return this.mChildFragmentManager.findFragmentByWho(paramString);
  }
  
  String generateActivityResultKey()
  {
    return "fragment_" + this.mWho + "_rq#" + this.mNextLocalRequestCode.getAndIncrement();
  }
  
  public final FragmentActivity getActivity()
  {
    Object localObject = this.mHost;
    if (localObject == null) {
      localObject = null;
    } else {
      localObject = (FragmentActivity)((FragmentHostCallback)localObject).getActivity();
    }
    return (FragmentActivity)localObject;
  }
  
  public boolean getAllowEnterTransitionOverlap()
  {
    AnimationInfo localAnimationInfo = this.mAnimationInfo;
    boolean bool;
    if ((localAnimationInfo != null) && (localAnimationInfo.mAllowEnterTransitionOverlap != null)) {
      bool = this.mAnimationInfo.mAllowEnterTransitionOverlap.booleanValue();
    } else {
      bool = true;
    }
    return bool;
  }
  
  public boolean getAllowReturnTransitionOverlap()
  {
    AnimationInfo localAnimationInfo = this.mAnimationInfo;
    boolean bool;
    if ((localAnimationInfo != null) && (localAnimationInfo.mAllowReturnTransitionOverlap != null)) {
      bool = this.mAnimationInfo.mAllowReturnTransitionOverlap.booleanValue();
    } else {
      bool = true;
    }
    return bool;
  }
  
  View getAnimatingAway()
  {
    AnimationInfo localAnimationInfo = this.mAnimationInfo;
    if (localAnimationInfo == null) {
      return null;
    }
    return localAnimationInfo.mAnimatingAway;
  }
  
  Animator getAnimator()
  {
    AnimationInfo localAnimationInfo = this.mAnimationInfo;
    if (localAnimationInfo == null) {
      return null;
    }
    return localAnimationInfo.mAnimator;
  }
  
  public final Bundle getArguments()
  {
    return this.mArguments;
  }
  
  public final FragmentManager getChildFragmentManager()
  {
    if (this.mHost != null) {
      return this.mChildFragmentManager;
    }
    throw new IllegalStateException("Fragment " + this + " has not been attached yet.");
  }
  
  public Context getContext()
  {
    Object localObject = this.mHost;
    if (localObject == null) {
      localObject = null;
    } else {
      localObject = ((FragmentHostCallback)localObject).getContext();
    }
    return (Context)localObject;
  }
  
  public ViewModelProvider.Factory getDefaultViewModelProviderFactory()
  {
    if (this.mFragmentManager != null)
    {
      if (this.mDefaultFactory == null)
      {
        Object localObject2 = null;
        Object localObject1;
        for (Context localContext = requireContext().getApplicationContext();; localContext = ((ContextWrapper)localContext).getBaseContext())
        {
          localObject1 = localObject2;
          if (!(localContext instanceof ContextWrapper)) {
            break;
          }
          if ((localContext instanceof Application))
          {
            localObject1 = (Application)localContext;
            break;
          }
        }
        if ((localObject1 == null) && (FragmentManager.isLoggingEnabled(3))) {
          Log.d("FragmentManager", "Could not find Application instance from Context " + requireContext().getApplicationContext() + ", you will not be able to use AndroidViewModel with the default ViewModelProvider.Factory");
        }
        this.mDefaultFactory = new SavedStateViewModelFactory((Application)localObject1, this, getArguments());
      }
      return this.mDefaultFactory;
    }
    throw new IllegalStateException("Can't access ViewModels from detached fragment");
  }
  
  int getEnterAnim()
  {
    AnimationInfo localAnimationInfo = this.mAnimationInfo;
    if (localAnimationInfo == null) {
      return 0;
    }
    return localAnimationInfo.mEnterAnim;
  }
  
  public Object getEnterTransition()
  {
    AnimationInfo localAnimationInfo = this.mAnimationInfo;
    if (localAnimationInfo == null) {
      return null;
    }
    return localAnimationInfo.mEnterTransition;
  }
  
  SharedElementCallback getEnterTransitionCallback()
  {
    AnimationInfo localAnimationInfo = this.mAnimationInfo;
    if (localAnimationInfo == null) {
      return null;
    }
    return localAnimationInfo.mEnterTransitionCallback;
  }
  
  int getExitAnim()
  {
    AnimationInfo localAnimationInfo = this.mAnimationInfo;
    if (localAnimationInfo == null) {
      return 0;
    }
    return localAnimationInfo.mExitAnim;
  }
  
  public Object getExitTransition()
  {
    AnimationInfo localAnimationInfo = this.mAnimationInfo;
    if (localAnimationInfo == null) {
      return null;
    }
    return localAnimationInfo.mExitTransition;
  }
  
  SharedElementCallback getExitTransitionCallback()
  {
    AnimationInfo localAnimationInfo = this.mAnimationInfo;
    if (localAnimationInfo == null) {
      return null;
    }
    return localAnimationInfo.mExitTransitionCallback;
  }
  
  View getFocusedView()
  {
    AnimationInfo localAnimationInfo = this.mAnimationInfo;
    if (localAnimationInfo == null) {
      return null;
    }
    return localAnimationInfo.mFocusedView;
  }
  
  @Deprecated
  public final FragmentManager getFragmentManager()
  {
    return this.mFragmentManager;
  }
  
  public final Object getHost()
  {
    Object localObject = this.mHost;
    if (localObject == null) {
      localObject = null;
    } else {
      localObject = ((FragmentHostCallback)localObject).onGetHost();
    }
    return localObject;
  }
  
  public final int getId()
  {
    return this.mFragmentId;
  }
  
  public final LayoutInflater getLayoutInflater()
  {
    LayoutInflater localLayoutInflater = this.mLayoutInflater;
    if (localLayoutInflater == null) {
      return performGetLayoutInflater(null);
    }
    return localLayoutInflater;
  }
  
  @Deprecated
  public LayoutInflater getLayoutInflater(Bundle paramBundle)
  {
    paramBundle = this.mHost;
    if (paramBundle != null)
    {
      paramBundle = paramBundle.onGetLayoutInflater();
      LayoutInflaterCompat.setFactory2(paramBundle, this.mChildFragmentManager.getLayoutInflaterFactory());
      return paramBundle;
    }
    throw new IllegalStateException("onGetLayoutInflater() cannot be executed until the Fragment is attached to the FragmentManager.");
  }
  
  public Lifecycle getLifecycle()
  {
    return this.mLifecycleRegistry;
  }
  
  @Deprecated
  public LoaderManager getLoaderManager()
  {
    return LoaderManager.getInstance(this);
  }
  
  int getNextTransition()
  {
    AnimationInfo localAnimationInfo = this.mAnimationInfo;
    if (localAnimationInfo == null) {
      return 0;
    }
    return localAnimationInfo.mNextTransition;
  }
  
  public final Fragment getParentFragment()
  {
    return this.mParentFragment;
  }
  
  public final FragmentManager getParentFragmentManager()
  {
    FragmentManager localFragmentManager = this.mFragmentManager;
    if (localFragmentManager != null) {
      return localFragmentManager;
    }
    throw new IllegalStateException("Fragment " + this + " not associated with a fragment manager.");
  }
  
  boolean getPopDirection()
  {
    AnimationInfo localAnimationInfo = this.mAnimationInfo;
    if (localAnimationInfo == null) {
      return false;
    }
    return localAnimationInfo.mIsPop;
  }
  
  int getPopEnterAnim()
  {
    AnimationInfo localAnimationInfo = this.mAnimationInfo;
    if (localAnimationInfo == null) {
      return 0;
    }
    return localAnimationInfo.mPopEnterAnim;
  }
  
  int getPopExitAnim()
  {
    AnimationInfo localAnimationInfo = this.mAnimationInfo;
    if (localAnimationInfo == null) {
      return 0;
    }
    return localAnimationInfo.mPopExitAnim;
  }
  
  float getPostOnViewCreatedAlpha()
  {
    AnimationInfo localAnimationInfo = this.mAnimationInfo;
    if (localAnimationInfo == null) {
      return 1.0F;
    }
    return localAnimationInfo.mPostOnViewCreatedAlpha;
  }
  
  public Object getReenterTransition()
  {
    Object localObject = this.mAnimationInfo;
    if (localObject == null) {
      return null;
    }
    if (((AnimationInfo)localObject).mReenterTransition == USE_DEFAULT_TRANSITION) {
      localObject = getExitTransition();
    } else {
      localObject = this.mAnimationInfo.mReenterTransition;
    }
    return localObject;
  }
  
  public final Resources getResources()
  {
    return requireContext().getResources();
  }
  
  @Deprecated
  public final boolean getRetainInstance()
  {
    return this.mRetainInstance;
  }
  
  public Object getReturnTransition()
  {
    Object localObject = this.mAnimationInfo;
    if (localObject == null) {
      return null;
    }
    if (((AnimationInfo)localObject).mReturnTransition == USE_DEFAULT_TRANSITION) {
      localObject = getEnterTransition();
    } else {
      localObject = this.mAnimationInfo.mReturnTransition;
    }
    return localObject;
  }
  
  public final SavedStateRegistry getSavedStateRegistry()
  {
    return this.mSavedStateRegistryController.getSavedStateRegistry();
  }
  
  public Object getSharedElementEnterTransition()
  {
    AnimationInfo localAnimationInfo = this.mAnimationInfo;
    if (localAnimationInfo == null) {
      return null;
    }
    return localAnimationInfo.mSharedElementEnterTransition;
  }
  
  public Object getSharedElementReturnTransition()
  {
    Object localObject = this.mAnimationInfo;
    if (localObject == null) {
      return null;
    }
    if (((AnimationInfo)localObject).mSharedElementReturnTransition == USE_DEFAULT_TRANSITION) {
      localObject = getSharedElementEnterTransition();
    } else {
      localObject = this.mAnimationInfo.mSharedElementReturnTransition;
    }
    return localObject;
  }
  
  ArrayList<String> getSharedElementSourceNames()
  {
    AnimationInfo localAnimationInfo = this.mAnimationInfo;
    if ((localAnimationInfo != null) && (localAnimationInfo.mSharedElementSourceNames != null)) {
      return this.mAnimationInfo.mSharedElementSourceNames;
    }
    return new ArrayList();
  }
  
  ArrayList<String> getSharedElementTargetNames()
  {
    AnimationInfo localAnimationInfo = this.mAnimationInfo;
    if ((localAnimationInfo != null) && (localAnimationInfo.mSharedElementTargetNames != null)) {
      return this.mAnimationInfo.mSharedElementTargetNames;
    }
    return new ArrayList();
  }
  
  public final String getString(int paramInt)
  {
    return getResources().getString(paramInt);
  }
  
  public final String getString(int paramInt, Object... paramVarArgs)
  {
    return getResources().getString(paramInt, paramVarArgs);
  }
  
  public final String getTag()
  {
    return this.mTag;
  }
  
  @Deprecated
  public final Fragment getTargetFragment()
  {
    Object localObject = this.mTarget;
    if (localObject != null) {
      return (Fragment)localObject;
    }
    localObject = this.mFragmentManager;
    if (localObject != null)
    {
      String str = this.mTargetWho;
      if (str != null) {
        return ((FragmentManager)localObject).findActiveFragment(str);
      }
    }
    return null;
  }
  
  @Deprecated
  public final int getTargetRequestCode()
  {
    return this.mTargetRequestCode;
  }
  
  public final CharSequence getText(int paramInt)
  {
    return getResources().getText(paramInt);
  }
  
  @Deprecated
  public boolean getUserVisibleHint()
  {
    return this.mUserVisibleHint;
  }
  
  public View getView()
  {
    return this.mView;
  }
  
  public LifecycleOwner getViewLifecycleOwner()
  {
    FragmentViewLifecycleOwner localFragmentViewLifecycleOwner = this.mViewLifecycleOwner;
    if (localFragmentViewLifecycleOwner != null) {
      return localFragmentViewLifecycleOwner;
    }
    throw new IllegalStateException("Can't access the Fragment View's LifecycleOwner when getView() is null i.e., before onCreateView() or after onDestroyView()");
  }
  
  public LiveData<LifecycleOwner> getViewLifecycleOwnerLiveData()
  {
    return this.mViewLifecycleOwnerLiveData;
  }
  
  public ViewModelStore getViewModelStore()
  {
    if (this.mFragmentManager != null)
    {
      if (getMinimumMaxLifecycleState() != Lifecycle.State.INITIALIZED.ordinal()) {
        return this.mFragmentManager.getViewModelStore(this);
      }
      throw new IllegalStateException("Calling getViewModelStore() before a Fragment reaches onCreate() when using setMaxLifecycle(INITIALIZED) is not supported");
    }
    throw new IllegalStateException("Can't access ViewModels from detached fragment");
  }
  
  public final boolean hasOptionsMenu()
  {
    return this.mHasMenu;
  }
  
  public final int hashCode()
  {
    return super.hashCode();
  }
  
  void initState()
  {
    initLifecycle();
    this.mWho = UUID.randomUUID().toString();
    this.mAdded = false;
    this.mRemoving = false;
    this.mFromLayout = false;
    this.mInLayout = false;
    this.mRestored = false;
    this.mBackStackNesting = 0;
    this.mFragmentManager = null;
    this.mChildFragmentManager = new FragmentManagerImpl();
    this.mHost = null;
    this.mFragmentId = 0;
    this.mContainerId = 0;
    this.mTag = null;
    this.mHidden = false;
    this.mDetached = false;
  }
  
  public final boolean isAdded()
  {
    boolean bool;
    if ((this.mHost != null) && (this.mAdded)) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public final boolean isDetached()
  {
    return this.mDetached;
  }
  
  public final boolean isHidden()
  {
    return this.mHidden;
  }
  
  boolean isHideReplaced()
  {
    AnimationInfo localAnimationInfo = this.mAnimationInfo;
    if (localAnimationInfo == null) {
      return false;
    }
    return localAnimationInfo.mIsHideReplaced;
  }
  
  final boolean isInBackStack()
  {
    boolean bool;
    if (this.mBackStackNesting > 0) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public final boolean isInLayout()
  {
    return this.mInLayout;
  }
  
  public final boolean isMenuVisible()
  {
    if (this.mMenuVisible)
    {
      FragmentManager localFragmentManager = this.mFragmentManager;
      if ((localFragmentManager == null) || (localFragmentManager.isParentMenuVisible(this.mParentFragment))) {
        return true;
      }
    }
    boolean bool = false;
    return bool;
  }
  
  boolean isPostponed()
  {
    AnimationInfo localAnimationInfo = this.mAnimationInfo;
    if (localAnimationInfo == null) {
      return false;
    }
    return localAnimationInfo.mEnterTransitionPostponed;
  }
  
  public final boolean isRemoving()
  {
    return this.mRemoving;
  }
  
  final boolean isRemovingParent()
  {
    Fragment localFragment = getParentFragment();
    boolean bool;
    if ((localFragment != null) && ((localFragment.isRemoving()) || (localFragment.isRemovingParent()))) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public final boolean isResumed()
  {
    boolean bool;
    if (this.mState >= 7) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public final boolean isStateSaved()
  {
    FragmentManager localFragmentManager = this.mFragmentManager;
    if (localFragmentManager == null) {
      return false;
    }
    return localFragmentManager.isStateSaved();
  }
  
  public final boolean isVisible()
  {
    if ((isAdded()) && (!isHidden()))
    {
      View localView = this.mView;
      if ((localView != null) && (localView.getWindowToken() != null) && (this.mView.getVisibility() == 0)) {
        return true;
      }
    }
    boolean bool = false;
    return bool;
  }
  
  void noteStateNotSaved()
  {
    this.mChildFragmentManager.noteStateNotSaved();
  }
  
  @Deprecated
  public void onActivityCreated(Bundle paramBundle)
  {
    this.mCalled = true;
  }
  
  @Deprecated
  public void onActivityResult(int paramInt1, int paramInt2, Intent paramIntent)
  {
    if (FragmentManager.isLoggingEnabled(2)) {
      Log.v("FragmentManager", "Fragment " + this + " received the following in onActivityResult(): requestCode: " + paramInt1 + " resultCode: " + paramInt2 + " data: " + paramIntent);
    }
  }
  
  @Deprecated
  public void onAttach(Activity paramActivity)
  {
    this.mCalled = true;
  }
  
  public void onAttach(Context paramContext)
  {
    this.mCalled = true;
    paramContext = this.mHost;
    if (paramContext == null) {
      paramContext = null;
    } else {
      paramContext = paramContext.getActivity();
    }
    if (paramContext != null)
    {
      this.mCalled = false;
      onAttach(paramContext);
    }
  }
  
  @Deprecated
  public void onAttachFragment(Fragment paramFragment) {}
  
  public void onConfigurationChanged(Configuration paramConfiguration)
  {
    this.mCalled = true;
  }
  
  public boolean onContextItemSelected(MenuItem paramMenuItem)
  {
    return false;
  }
  
  public void onCreate(Bundle paramBundle)
  {
    this.mCalled = true;
    restoreChildFragmentState(paramBundle);
    if (!this.mChildFragmentManager.isStateAtLeast(1)) {
      this.mChildFragmentManager.dispatchCreate();
    }
  }
  
  public Animation onCreateAnimation(int paramInt1, boolean paramBoolean, int paramInt2)
  {
    return null;
  }
  
  public Animator onCreateAnimator(int paramInt1, boolean paramBoolean, int paramInt2)
  {
    return null;
  }
  
  public void onCreateContextMenu(ContextMenu paramContextMenu, View paramView, ContextMenu.ContextMenuInfo paramContextMenuInfo)
  {
    requireActivity().onCreateContextMenu(paramContextMenu, paramView, paramContextMenuInfo);
  }
  
  public void onCreateOptionsMenu(Menu paramMenu, MenuInflater paramMenuInflater) {}
  
  public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    int i = this.mContentLayoutId;
    if (i != 0) {
      return paramLayoutInflater.inflate(i, paramViewGroup, false);
    }
    return null;
  }
  
  public void onDestroy()
  {
    this.mCalled = true;
  }
  
  public void onDestroyOptionsMenu() {}
  
  public void onDestroyView()
  {
    this.mCalled = true;
  }
  
  public void onDetach()
  {
    this.mCalled = true;
  }
  
  public LayoutInflater onGetLayoutInflater(Bundle paramBundle)
  {
    return getLayoutInflater(paramBundle);
  }
  
  public void onHiddenChanged(boolean paramBoolean) {}
  
  @Deprecated
  public void onInflate(Activity paramActivity, AttributeSet paramAttributeSet, Bundle paramBundle)
  {
    this.mCalled = true;
  }
  
  public void onInflate(Context paramContext, AttributeSet paramAttributeSet, Bundle paramBundle)
  {
    this.mCalled = true;
    paramContext = this.mHost;
    if (paramContext == null) {
      paramContext = null;
    } else {
      paramContext = paramContext.getActivity();
    }
    if (paramContext != null)
    {
      this.mCalled = false;
      onInflate(paramContext, paramAttributeSet, paramBundle);
    }
  }
  
  public void onLowMemory()
  {
    this.mCalled = true;
  }
  
  public void onMultiWindowModeChanged(boolean paramBoolean) {}
  
  public boolean onOptionsItemSelected(MenuItem paramMenuItem)
  {
    return false;
  }
  
  public void onOptionsMenuClosed(Menu paramMenu) {}
  
  public void onPause()
  {
    this.mCalled = true;
  }
  
  public void onPictureInPictureModeChanged(boolean paramBoolean) {}
  
  public void onPrepareOptionsMenu(Menu paramMenu) {}
  
  public void onPrimaryNavigationFragmentChanged(boolean paramBoolean) {}
  
  @Deprecated
  public void onRequestPermissionsResult(int paramInt, String[] paramArrayOfString, int[] paramArrayOfInt) {}
  
  public void onResume()
  {
    this.mCalled = true;
  }
  
  public void onSaveInstanceState(Bundle paramBundle) {}
  
  public void onStart()
  {
    this.mCalled = true;
  }
  
  public void onStop()
  {
    this.mCalled = true;
  }
  
  public void onViewCreated(View paramView, Bundle paramBundle) {}
  
  public void onViewStateRestored(Bundle paramBundle)
  {
    this.mCalled = true;
  }
  
  void performActivityCreated(Bundle paramBundle)
  {
    this.mChildFragmentManager.noteStateNotSaved();
    this.mState = 3;
    this.mCalled = false;
    onActivityCreated(paramBundle);
    if (this.mCalled)
    {
      restoreViewState();
      this.mChildFragmentManager.dispatchActivityCreated();
      return;
    }
    throw new SuperNotCalledException("Fragment " + this + " did not call through to super.onActivityCreated()");
  }
  
  void performAttach()
  {
    Iterator localIterator = this.mOnPreAttachedListeners.iterator();
    while (localIterator.hasNext()) {
      ((OnPreAttachedListener)localIterator.next()).onPreAttached();
    }
    this.mOnPreAttachedListeners.clear();
    this.mChildFragmentManager.attachController(this.mHost, createFragmentContainer(), this);
    this.mState = 0;
    this.mCalled = false;
    onAttach(this.mHost.getContext());
    if (this.mCalled)
    {
      this.mFragmentManager.dispatchOnAttachFragment(this);
      this.mChildFragmentManager.dispatchAttach();
      return;
    }
    throw new SuperNotCalledException("Fragment " + this + " did not call through to super.onAttach()");
  }
  
  void performConfigurationChanged(Configuration paramConfiguration)
  {
    onConfigurationChanged(paramConfiguration);
    this.mChildFragmentManager.dispatchConfigurationChanged(paramConfiguration);
  }
  
  boolean performContextItemSelected(MenuItem paramMenuItem)
  {
    if (!this.mHidden)
    {
      if (onContextItemSelected(paramMenuItem)) {
        return true;
      }
      return this.mChildFragmentManager.dispatchContextItemSelected(paramMenuItem);
    }
    return false;
  }
  
  void performCreate(Bundle paramBundle)
  {
    this.mChildFragmentManager.noteStateNotSaved();
    this.mState = 1;
    this.mCalled = false;
    if (Build.VERSION.SDK_INT >= 19) {
      this.mLifecycleRegistry.addObserver(new LifecycleEventObserver()
      {
        public void onStateChanged(LifecycleOwner paramAnonymousLifecycleOwner, Lifecycle.Event paramAnonymousEvent)
        {
          if ((paramAnonymousEvent == Lifecycle.Event.ON_STOP) && (Fragment.this.mView != null)) {
            Fragment.this.mView.cancelPendingInputEvents();
          }
        }
      });
    }
    this.mSavedStateRegistryController.performRestore(paramBundle);
    onCreate(paramBundle);
    this.mIsCreated = true;
    if (this.mCalled)
    {
      this.mLifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_CREATE);
      return;
    }
    throw new SuperNotCalledException("Fragment " + this + " did not call through to super.onCreate()");
  }
  
  boolean performCreateOptionsMenu(Menu paramMenu, MenuInflater paramMenuInflater)
  {
    boolean bool = false;
    int j = 0;
    if (!this.mHidden)
    {
      int i = j;
      if (this.mHasMenu)
      {
        i = j;
        if (this.mMenuVisible)
        {
          i = 1;
          onCreateOptionsMenu(paramMenu, paramMenuInflater);
        }
      }
      bool = i | this.mChildFragmentManager.dispatchCreateOptionsMenu(paramMenu, paramMenuInflater);
    }
    return bool;
  }
  
  void performCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    this.mChildFragmentManager.noteStateNotSaved();
    this.mPerformedCreateView = true;
    this.mViewLifecycleOwner = new FragmentViewLifecycleOwner(this, getViewModelStore());
    paramLayoutInflater = onCreateView(paramLayoutInflater, paramViewGroup, paramBundle);
    this.mView = paramLayoutInflater;
    if (paramLayoutInflater != null)
    {
      this.mViewLifecycleOwner.initialize();
      ViewTreeLifecycleOwner.set(this.mView, this.mViewLifecycleOwner);
      ViewTreeViewModelStoreOwner.set(this.mView, this.mViewLifecycleOwner);
      ViewTreeSavedStateRegistryOwner.set(this.mView, this.mViewLifecycleOwner);
      this.mViewLifecycleOwnerLiveData.setValue(this.mViewLifecycleOwner);
    }
    else
    {
      if (this.mViewLifecycleOwner.isInitialized()) {
        break label115;
      }
      this.mViewLifecycleOwner = null;
    }
    return;
    label115:
    throw new IllegalStateException("Called getViewLifecycleOwner() but onCreateView() returned null");
  }
  
  void performDestroy()
  {
    this.mChildFragmentManager.dispatchDestroy();
    this.mLifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_DESTROY);
    this.mState = 0;
    this.mCalled = false;
    this.mIsCreated = false;
    onDestroy();
    if (this.mCalled) {
      return;
    }
    throw new SuperNotCalledException("Fragment " + this + " did not call through to super.onDestroy()");
  }
  
  void performDestroyView()
  {
    this.mChildFragmentManager.dispatchDestroyView();
    if ((this.mView != null) && (this.mViewLifecycleOwner.getLifecycle().getCurrentState().isAtLeast(Lifecycle.State.CREATED))) {
      this.mViewLifecycleOwner.handleLifecycleEvent(Lifecycle.Event.ON_DESTROY);
    }
    this.mState = 1;
    this.mCalled = false;
    onDestroyView();
    if (this.mCalled)
    {
      LoaderManager.getInstance(this).markForRedelivery();
      this.mPerformedCreateView = false;
      return;
    }
    throw new SuperNotCalledException("Fragment " + this + " did not call through to super.onDestroyView()");
  }
  
  void performDetach()
  {
    this.mState = -1;
    this.mCalled = false;
    onDetach();
    this.mLayoutInflater = null;
    if (this.mCalled)
    {
      if (!this.mChildFragmentManager.isDestroyed())
      {
        this.mChildFragmentManager.dispatchDestroy();
        this.mChildFragmentManager = new FragmentManagerImpl();
      }
      return;
    }
    throw new SuperNotCalledException("Fragment " + this + " did not call through to super.onDetach()");
  }
  
  LayoutInflater performGetLayoutInflater(Bundle paramBundle)
  {
    paramBundle = onGetLayoutInflater(paramBundle);
    this.mLayoutInflater = paramBundle;
    return paramBundle;
  }
  
  void performLowMemory()
  {
    onLowMemory();
    this.mChildFragmentManager.dispatchLowMemory();
  }
  
  void performMultiWindowModeChanged(boolean paramBoolean)
  {
    onMultiWindowModeChanged(paramBoolean);
    this.mChildFragmentManager.dispatchMultiWindowModeChanged(paramBoolean);
  }
  
  boolean performOptionsItemSelected(MenuItem paramMenuItem)
  {
    if (!this.mHidden)
    {
      if ((this.mHasMenu) && (this.mMenuVisible) && (onOptionsItemSelected(paramMenuItem))) {
        return true;
      }
      return this.mChildFragmentManager.dispatchOptionsItemSelected(paramMenuItem);
    }
    return false;
  }
  
  void performOptionsMenuClosed(Menu paramMenu)
  {
    if (!this.mHidden)
    {
      if ((this.mHasMenu) && (this.mMenuVisible)) {
        onOptionsMenuClosed(paramMenu);
      }
      this.mChildFragmentManager.dispatchOptionsMenuClosed(paramMenu);
    }
  }
  
  void performPause()
  {
    this.mChildFragmentManager.dispatchPause();
    if (this.mView != null) {
      this.mViewLifecycleOwner.handleLifecycleEvent(Lifecycle.Event.ON_PAUSE);
    }
    this.mLifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_PAUSE);
    this.mState = 6;
    this.mCalled = false;
    onPause();
    if (this.mCalled) {
      return;
    }
    throw new SuperNotCalledException("Fragment " + this + " did not call through to super.onPause()");
  }
  
  void performPictureInPictureModeChanged(boolean paramBoolean)
  {
    onPictureInPictureModeChanged(paramBoolean);
    this.mChildFragmentManager.dispatchPictureInPictureModeChanged(paramBoolean);
  }
  
  boolean performPrepareOptionsMenu(Menu paramMenu)
  {
    boolean bool = false;
    int j = 0;
    if (!this.mHidden)
    {
      int i = j;
      if (this.mHasMenu)
      {
        i = j;
        if (this.mMenuVisible)
        {
          i = 1;
          onPrepareOptionsMenu(paramMenu);
        }
      }
      bool = i | this.mChildFragmentManager.dispatchPrepareOptionsMenu(paramMenu);
    }
    return bool;
  }
  
  void performPrimaryNavigationFragmentChanged()
  {
    boolean bool = this.mFragmentManager.isPrimaryNavigation(this);
    Boolean localBoolean = this.mIsPrimaryNavigationFragment;
    if ((localBoolean == null) || (localBoolean.booleanValue() != bool))
    {
      this.mIsPrimaryNavigationFragment = Boolean.valueOf(bool);
      onPrimaryNavigationFragmentChanged(bool);
      this.mChildFragmentManager.dispatchPrimaryNavigationFragmentChanged();
    }
  }
  
  void performResume()
  {
    this.mChildFragmentManager.noteStateNotSaved();
    this.mChildFragmentManager.execPendingActions(true);
    this.mState = 7;
    this.mCalled = false;
    onResume();
    if (this.mCalled)
    {
      this.mLifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_RESUME);
      if (this.mView != null) {
        this.mViewLifecycleOwner.handleLifecycleEvent(Lifecycle.Event.ON_RESUME);
      }
      this.mChildFragmentManager.dispatchResume();
      return;
    }
    throw new SuperNotCalledException("Fragment " + this + " did not call through to super.onResume()");
  }
  
  void performSaveInstanceState(Bundle paramBundle)
  {
    onSaveInstanceState(paramBundle);
    this.mSavedStateRegistryController.performSave(paramBundle);
    Parcelable localParcelable = this.mChildFragmentManager.saveAllState();
    if (localParcelable != null) {
      paramBundle.putParcelable("android:support:fragments", localParcelable);
    }
  }
  
  void performStart()
  {
    this.mChildFragmentManager.noteStateNotSaved();
    this.mChildFragmentManager.execPendingActions(true);
    this.mState = 5;
    this.mCalled = false;
    onStart();
    if (this.mCalled)
    {
      this.mLifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_START);
      if (this.mView != null) {
        this.mViewLifecycleOwner.handleLifecycleEvent(Lifecycle.Event.ON_START);
      }
      this.mChildFragmentManager.dispatchStart();
      return;
    }
    throw new SuperNotCalledException("Fragment " + this + " did not call through to super.onStart()");
  }
  
  void performStop()
  {
    this.mChildFragmentManager.dispatchStop();
    if (this.mView != null) {
      this.mViewLifecycleOwner.handleLifecycleEvent(Lifecycle.Event.ON_STOP);
    }
    this.mLifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_STOP);
    this.mState = 4;
    this.mCalled = false;
    onStop();
    if (this.mCalled) {
      return;
    }
    throw new SuperNotCalledException("Fragment " + this + " did not call through to super.onStop()");
  }
  
  void performViewCreated()
  {
    onViewCreated(this.mView, this.mSavedFragmentState);
    this.mChildFragmentManager.dispatchViewCreated();
  }
  
  public void postponeEnterTransition()
  {
    ensureAnimationInfo().mEnterTransitionPostponed = true;
  }
  
  public final void postponeEnterTransition(long paramLong, TimeUnit paramTimeUnit)
  {
    ensureAnimationInfo().mEnterTransitionPostponed = true;
    Object localObject = this.mFragmentManager;
    if (localObject != null) {
      localObject = ((FragmentManager)localObject).getHost().getHandler();
    } else {
      localObject = new Handler(Looper.getMainLooper());
    }
    ((Handler)localObject).removeCallbacks(this.mPostponedDurationRunnable);
    ((Handler)localObject).postDelayed(this.mPostponedDurationRunnable, paramTimeUnit.toMillis(paramLong));
  }
  
  public final <I, O> ActivityResultLauncher<I> registerForActivityResult(ActivityResultContract<I, O> paramActivityResultContract, ActivityResultCallback<O> paramActivityResultCallback)
  {
    prepareCallInternal(paramActivityResultContract, new Function()
    {
      public ActivityResultRegistry apply(Void paramAnonymousVoid)
      {
        if ((Fragment.this.mHost instanceof ActivityResultRegistryOwner)) {
          return ((ActivityResultRegistryOwner)Fragment.this.mHost).getActivityResultRegistry();
        }
        return Fragment.this.requireActivity().getActivityResultRegistry();
      }
    }, paramActivityResultCallback);
  }
  
  public final <I, O> ActivityResultLauncher<I> registerForActivityResult(ActivityResultContract<I, O> paramActivityResultContract, final ActivityResultRegistry paramActivityResultRegistry, ActivityResultCallback<O> paramActivityResultCallback)
  {
    prepareCallInternal(paramActivityResultContract, new Function()
    {
      public ActivityResultRegistry apply(Void paramAnonymousVoid)
      {
        return paramActivityResultRegistry;
      }
    }, paramActivityResultCallback);
  }
  
  public void registerForContextMenu(View paramView)
  {
    paramView.setOnCreateContextMenuListener(this);
  }
  
  @Deprecated
  public final void requestPermissions(String[] paramArrayOfString, int paramInt)
  {
    if (this.mHost != null)
    {
      getParentFragmentManager().launchRequestPermissions(this, paramArrayOfString, paramInt);
      return;
    }
    throw new IllegalStateException("Fragment " + this + " not attached to Activity");
  }
  
  public final FragmentActivity requireActivity()
  {
    FragmentActivity localFragmentActivity = getActivity();
    if (localFragmentActivity != null) {
      return localFragmentActivity;
    }
    throw new IllegalStateException("Fragment " + this + " not attached to an activity.");
  }
  
  public final Bundle requireArguments()
  {
    Bundle localBundle = getArguments();
    if (localBundle != null) {
      return localBundle;
    }
    throw new IllegalStateException("Fragment " + this + " does not have any arguments.");
  }
  
  public final Context requireContext()
  {
    Context localContext = getContext();
    if (localContext != null) {
      return localContext;
    }
    throw new IllegalStateException("Fragment " + this + " not attached to a context.");
  }
  
  @Deprecated
  public final FragmentManager requireFragmentManager()
  {
    return getParentFragmentManager();
  }
  
  public final Object requireHost()
  {
    Object localObject = getHost();
    if (localObject != null) {
      return localObject;
    }
    throw new IllegalStateException("Fragment " + this + " not attached to a host.");
  }
  
  public final Fragment requireParentFragment()
  {
    Fragment localFragment = getParentFragment();
    if (localFragment == null)
    {
      if (getContext() == null) {
        throw new IllegalStateException("Fragment " + this + " is not attached to any Fragment or host");
      }
      throw new IllegalStateException("Fragment " + this + " is not a child Fragment, it is directly attached to " + getContext());
    }
    return localFragment;
  }
  
  public final View requireView()
  {
    View localView = getView();
    if (localView != null) {
      return localView;
    }
    throw new IllegalStateException("Fragment " + this + " did not return a View from onCreateView() or this was called before onCreateView().");
  }
  
  void restoreChildFragmentState(Bundle paramBundle)
  {
    if (paramBundle != null)
    {
      paramBundle = paramBundle.getParcelable("android:support:fragments");
      if (paramBundle != null)
      {
        this.mChildFragmentManager.restoreSaveState(paramBundle);
        this.mChildFragmentManager.dispatchCreate();
      }
    }
  }
  
  final void restoreViewState(Bundle paramBundle)
  {
    SparseArray localSparseArray = this.mSavedViewState;
    if (localSparseArray != null)
    {
      this.mView.restoreHierarchyState(localSparseArray);
      this.mSavedViewState = null;
    }
    if (this.mView != null)
    {
      this.mViewLifecycleOwner.performRestore(this.mSavedViewRegistryState);
      this.mSavedViewRegistryState = null;
    }
    this.mCalled = false;
    onViewStateRestored(paramBundle);
    if (this.mCalled)
    {
      if (this.mView != null) {
        this.mViewLifecycleOwner.handleLifecycleEvent(Lifecycle.Event.ON_CREATE);
      }
      return;
    }
    throw new SuperNotCalledException("Fragment " + this + " did not call through to super.onViewStateRestored()");
  }
  
  public void setAllowEnterTransitionOverlap(boolean paramBoolean)
  {
    ensureAnimationInfo().mAllowEnterTransitionOverlap = Boolean.valueOf(paramBoolean);
  }
  
  public void setAllowReturnTransitionOverlap(boolean paramBoolean)
  {
    ensureAnimationInfo().mAllowReturnTransitionOverlap = Boolean.valueOf(paramBoolean);
  }
  
  void setAnimatingAway(View paramView)
  {
    ensureAnimationInfo().mAnimatingAway = paramView;
  }
  
  void setAnimations(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    if ((this.mAnimationInfo == null) && (paramInt1 == 0) && (paramInt2 == 0) && (paramInt3 == 0) && (paramInt4 == 0)) {
      return;
    }
    ensureAnimationInfo().mEnterAnim = paramInt1;
    ensureAnimationInfo().mExitAnim = paramInt2;
    ensureAnimationInfo().mPopEnterAnim = paramInt3;
    ensureAnimationInfo().mPopExitAnim = paramInt4;
  }
  
  void setAnimator(Animator paramAnimator)
  {
    ensureAnimationInfo().mAnimator = paramAnimator;
  }
  
  public void setArguments(Bundle paramBundle)
  {
    if ((this.mFragmentManager != null) && (isStateSaved())) {
      throw new IllegalStateException("Fragment already added and state has been saved");
    }
    this.mArguments = paramBundle;
  }
  
  public void setEnterSharedElementCallback(SharedElementCallback paramSharedElementCallback)
  {
    ensureAnimationInfo().mEnterTransitionCallback = paramSharedElementCallback;
  }
  
  public void setEnterTransition(Object paramObject)
  {
    ensureAnimationInfo().mEnterTransition = paramObject;
  }
  
  public void setExitSharedElementCallback(SharedElementCallback paramSharedElementCallback)
  {
    ensureAnimationInfo().mExitTransitionCallback = paramSharedElementCallback;
  }
  
  public void setExitTransition(Object paramObject)
  {
    ensureAnimationInfo().mExitTransition = paramObject;
  }
  
  void setFocusedView(View paramView)
  {
    ensureAnimationInfo().mFocusedView = paramView;
  }
  
  public void setHasOptionsMenu(boolean paramBoolean)
  {
    if (this.mHasMenu != paramBoolean)
    {
      this.mHasMenu = paramBoolean;
      if ((isAdded()) && (!isHidden())) {
        this.mHost.onSupportInvalidateOptionsMenu();
      }
    }
  }
  
  void setHideReplaced(boolean paramBoolean)
  {
    ensureAnimationInfo().mIsHideReplaced = paramBoolean;
  }
  
  public void setInitialSavedState(SavedState paramSavedState)
  {
    if (this.mFragmentManager == null)
    {
      if ((paramSavedState != null) && (paramSavedState.mState != null)) {
        paramSavedState = paramSavedState.mState;
      } else {
        paramSavedState = null;
      }
      this.mSavedFragmentState = paramSavedState;
      return;
    }
    throw new IllegalStateException("Fragment already added");
  }
  
  public void setMenuVisibility(boolean paramBoolean)
  {
    if (this.mMenuVisible != paramBoolean)
    {
      this.mMenuVisible = paramBoolean;
      if ((this.mHasMenu) && (isAdded()) && (!isHidden())) {
        this.mHost.onSupportInvalidateOptionsMenu();
      }
    }
  }
  
  void setNextTransition(int paramInt)
  {
    if ((this.mAnimationInfo == null) && (paramInt == 0)) {
      return;
    }
    ensureAnimationInfo();
    this.mAnimationInfo.mNextTransition = paramInt;
  }
  
  void setOnStartEnterTransitionListener(OnStartEnterTransitionListener paramOnStartEnterTransitionListener)
  {
    ensureAnimationInfo();
    if (paramOnStartEnterTransitionListener == this.mAnimationInfo.mStartEnterTransitionListener) {
      return;
    }
    if ((paramOnStartEnterTransitionListener != null) && (this.mAnimationInfo.mStartEnterTransitionListener != null)) {
      throw new IllegalStateException("Trying to set a replacement startPostponedEnterTransition on " + this);
    }
    if (this.mAnimationInfo.mEnterTransitionPostponed) {
      this.mAnimationInfo.mStartEnterTransitionListener = paramOnStartEnterTransitionListener;
    }
    if (paramOnStartEnterTransitionListener != null) {
      paramOnStartEnterTransitionListener.startListening();
    }
  }
  
  void setPopDirection(boolean paramBoolean)
  {
    if (this.mAnimationInfo == null) {
      return;
    }
    ensureAnimationInfo().mIsPop = paramBoolean;
  }
  
  void setPostOnViewCreatedAlpha(float paramFloat)
  {
    ensureAnimationInfo().mPostOnViewCreatedAlpha = paramFloat;
  }
  
  public void setReenterTransition(Object paramObject)
  {
    ensureAnimationInfo().mReenterTransition = paramObject;
  }
  
  @Deprecated
  public void setRetainInstance(boolean paramBoolean)
  {
    this.mRetainInstance = paramBoolean;
    FragmentManager localFragmentManager = this.mFragmentManager;
    if (localFragmentManager != null)
    {
      if (paramBoolean) {
        localFragmentManager.addRetainedFragment(this);
      } else {
        localFragmentManager.removeRetainedFragment(this);
      }
    }
    else {
      this.mRetainInstanceChangedWhileDetached = true;
    }
  }
  
  public void setReturnTransition(Object paramObject)
  {
    ensureAnimationInfo().mReturnTransition = paramObject;
  }
  
  public void setSharedElementEnterTransition(Object paramObject)
  {
    ensureAnimationInfo().mSharedElementEnterTransition = paramObject;
  }
  
  void setSharedElementNames(ArrayList<String> paramArrayList1, ArrayList<String> paramArrayList2)
  {
    ensureAnimationInfo();
    this.mAnimationInfo.mSharedElementSourceNames = paramArrayList1;
    this.mAnimationInfo.mSharedElementTargetNames = paramArrayList2;
  }
  
  public void setSharedElementReturnTransition(Object paramObject)
  {
    ensureAnimationInfo().mSharedElementReturnTransition = paramObject;
  }
  
  @Deprecated
  public void setTargetFragment(Fragment paramFragment, int paramInt)
  {
    FragmentManager localFragmentManager = this.mFragmentManager;
    if (paramFragment != null) {
      localObject = paramFragment.mFragmentManager;
    } else {
      localObject = null;
    }
    if ((localFragmentManager != null) && (localObject != null) && (localFragmentManager != localObject)) {
      throw new IllegalArgumentException("Fragment " + paramFragment + " must share the same FragmentManager to be set as a target fragment");
    }
    Object localObject = paramFragment;
    while (localObject != null) {
      if (!((Fragment)localObject).equals(this)) {
        localObject = ((Fragment)localObject).getTargetFragment();
      } else {
        throw new IllegalArgumentException("Setting " + paramFragment + " as the target of " + this + " would create a target cycle");
      }
    }
    if (paramFragment == null)
    {
      this.mTargetWho = null;
      this.mTarget = null;
    }
    else if ((this.mFragmentManager != null) && (paramFragment.mFragmentManager != null))
    {
      this.mTargetWho = paramFragment.mWho;
      this.mTarget = null;
    }
    else
    {
      this.mTargetWho = null;
      this.mTarget = paramFragment;
    }
    this.mTargetRequestCode = paramInt;
  }
  
  @Deprecated
  public void setUserVisibleHint(boolean paramBoolean)
  {
    if ((!this.mUserVisibleHint) && (paramBoolean) && (this.mState < 5) && (this.mFragmentManager != null) && (isAdded()) && (this.mIsCreated))
    {
      FragmentManager localFragmentManager = this.mFragmentManager;
      localFragmentManager.performPendingDeferredStart(localFragmentManager.createOrGetFragmentStateManager(this));
    }
    this.mUserVisibleHint = paramBoolean;
    boolean bool;
    if ((this.mState < 5) && (!paramBoolean)) {
      bool = true;
    } else {
      bool = false;
    }
    this.mDeferStart = bool;
    if (this.mSavedFragmentState != null) {
      this.mSavedUserVisibleHint = Boolean.valueOf(paramBoolean);
    }
  }
  
  public boolean shouldShowRequestPermissionRationale(String paramString)
  {
    FragmentHostCallback localFragmentHostCallback = this.mHost;
    if (localFragmentHostCallback != null) {
      return localFragmentHostCallback.onShouldShowRequestPermissionRationale(paramString);
    }
    return false;
  }
  
  public void startActivity(Intent paramIntent)
  {
    startActivity(paramIntent, null);
  }
  
  public void startActivity(Intent paramIntent, Bundle paramBundle)
  {
    FragmentHostCallback localFragmentHostCallback = this.mHost;
    if (localFragmentHostCallback != null)
    {
      localFragmentHostCallback.onStartActivityFromFragment(this, paramIntent, -1, paramBundle);
      return;
    }
    throw new IllegalStateException("Fragment " + this + " not attached to Activity");
  }
  
  @Deprecated
  public void startActivityForResult(Intent paramIntent, int paramInt)
  {
    startActivityForResult(paramIntent, paramInt, null);
  }
  
  @Deprecated
  public void startActivityForResult(Intent paramIntent, int paramInt, Bundle paramBundle)
  {
    if (this.mHost != null)
    {
      getParentFragmentManager().launchStartActivityForResult(this, paramIntent, paramInt, paramBundle);
      return;
    }
    throw new IllegalStateException("Fragment " + this + " not attached to Activity");
  }
  
  @Deprecated
  public void startIntentSenderForResult(IntentSender paramIntentSender, int paramInt1, Intent paramIntent, int paramInt2, int paramInt3, int paramInt4, Bundle paramBundle)
    throws IntentSender.SendIntentException
  {
    if (this.mHost != null)
    {
      if (FragmentManager.isLoggingEnabled(2)) {
        Log.v("FragmentManager", "Fragment " + this + " received the following in startIntentSenderForResult() requestCode: " + paramInt1 + " IntentSender: " + paramIntentSender + " fillInIntent: " + paramIntent + " options: " + paramBundle);
      }
      getParentFragmentManager().launchStartIntentSenderForResult(this, paramIntentSender, paramInt1, paramIntent, paramInt2, paramInt3, paramInt4, paramBundle);
      return;
    }
    throw new IllegalStateException("Fragment " + this + " not attached to Activity");
  }
  
  public void startPostponedEnterTransition()
  {
    if ((this.mAnimationInfo != null) && (ensureAnimationInfo().mEnterTransitionPostponed))
    {
      if (this.mHost == null) {
        ensureAnimationInfo().mEnterTransitionPostponed = false;
      } else if (Looper.myLooper() != this.mHost.getHandler().getLooper()) {
        this.mHost.getHandler().postAtFrontOfQueue(new Runnable()
        {
          public void run()
          {
            Fragment.this.callStartTransitionListener(false);
          }
        });
      } else {
        callStartTransitionListener(true);
      }
      return;
    }
  }
  
  public String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder(128);
    localStringBuilder.append(getClass().getSimpleName());
    localStringBuilder.append("{");
    String str = Integer.toHexString(System.identityHashCode(this));
    Log5ECF72.a(str);
    LogE84000.a(str);
    Log229316.a(str);
    localStringBuilder.append(str);
    localStringBuilder.append("}");
    localStringBuilder.append(" (");
    localStringBuilder.append(this.mWho);
    if (this.mFragmentId != 0)
    {
      localStringBuilder.append(" id=0x");
      str = Integer.toHexString(this.mFragmentId);
      Log5ECF72.a(str);
      LogE84000.a(str);
      Log229316.a(str);
      localStringBuilder.append(str);
    }
    if (this.mTag != null)
    {
      localStringBuilder.append(" tag=");
      localStringBuilder.append(this.mTag);
    }
    localStringBuilder.append(")");
    return localStringBuilder.toString();
  }
  
  public void unregisterForContextMenu(View paramView)
  {
    paramView.setOnCreateContextMenuListener(null);
  }
  
  static class AnimationInfo
  {
    Boolean mAllowEnterTransitionOverlap;
    Boolean mAllowReturnTransitionOverlap;
    View mAnimatingAway;
    Animator mAnimator;
    int mEnterAnim;
    Object mEnterTransition = null;
    SharedElementCallback mEnterTransitionCallback = null;
    boolean mEnterTransitionPostponed;
    int mExitAnim;
    Object mExitTransition = null;
    SharedElementCallback mExitTransitionCallback = null;
    View mFocusedView = null;
    boolean mIsHideReplaced;
    boolean mIsPop;
    int mNextTransition;
    int mPopEnterAnim;
    int mPopExitAnim;
    float mPostOnViewCreatedAlpha = 1.0F;
    Object mReenterTransition = Fragment.USE_DEFAULT_TRANSITION;
    Object mReturnTransition = Fragment.USE_DEFAULT_TRANSITION;
    Object mSharedElementEnterTransition = null;
    Object mSharedElementReturnTransition = Fragment.USE_DEFAULT_TRANSITION;
    ArrayList<String> mSharedElementSourceNames;
    ArrayList<String> mSharedElementTargetNames;
    Fragment.OnStartEnterTransitionListener mStartEnterTransitionListener;
  }
  
  public static class InstantiationException
    extends RuntimeException
  {
    public InstantiationException(String paramString, Exception paramException)
    {
      super(paramException);
    }
  }
  
  private static abstract class OnPreAttachedListener
  {
    abstract void onPreAttached();
  }
  
  static abstract interface OnStartEnterTransitionListener
  {
    public abstract void onStartEnterTransition();
    
    public abstract void startListening();
  }
  
  public static class SavedState
    implements Parcelable
  {
    public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.ClassLoaderCreator()
    {
      public Fragment.SavedState createFromParcel(Parcel paramAnonymousParcel)
      {
        return new Fragment.SavedState(paramAnonymousParcel, null);
      }
      
      public Fragment.SavedState createFromParcel(Parcel paramAnonymousParcel, ClassLoader paramAnonymousClassLoader)
      {
        return new Fragment.SavedState(paramAnonymousParcel, paramAnonymousClassLoader);
      }
      
      public Fragment.SavedState[] newArray(int paramAnonymousInt)
      {
        return new Fragment.SavedState[paramAnonymousInt];
      }
    };
    final Bundle mState;
    
    SavedState(Bundle paramBundle)
    {
      this.mState = paramBundle;
    }
    
    SavedState(Parcel paramParcel, ClassLoader paramClassLoader)
    {
      paramParcel = paramParcel.readBundle();
      this.mState = paramParcel;
      if ((paramClassLoader != null) && (paramParcel != null)) {
        paramParcel.setClassLoader(paramClassLoader);
      }
    }
    
    public int describeContents()
    {
      return 0;
    }
    
    public void writeToParcel(Parcel paramParcel, int paramInt)
    {
      paramParcel.writeBundle(this.mState);
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/fragment/app/Fragment.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */