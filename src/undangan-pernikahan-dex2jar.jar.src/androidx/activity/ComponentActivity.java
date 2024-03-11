package androidx.activity;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.IntentSender.SendIntentException;
import android.content.res.Configuration;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import androidx.activity.contextaware.ContextAware;
import androidx.activity.contextaware.ContextAwareHelper;
import androidx.activity.contextaware.OnContextAvailableListener;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultCaller;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.ActivityResultRegistry;
import androidx.activity.result.ActivityResultRegistryOwner;
import androidx.activity.result.IntentSenderRequest;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContract.SynchronousResult;
import androidx.core.app.ActivityCompat;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.app.MultiWindowModeChangedInfo;
import androidx.core.app.OnMultiWindowModeChangedProvider;
import androidx.core.app.OnNewIntentProvider;
import androidx.core.app.OnPictureInPictureModeChangedProvider;
import androidx.core.app.PictureInPictureModeChangedInfo;
import androidx.core.content.ContextCompat;
import androidx.core.content.OnConfigurationChangedProvider;
import androidx.core.content.OnTrimMemoryProvider;
import androidx.core.util.Consumer;
import androidx.core.view.MenuHost;
import androidx.core.view.MenuHostHelper;
import androidx.core.view.MenuProvider;
import androidx.lifecycle.HasDefaultViewModelProviderFactory;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.Lifecycle.Event;
import androidx.lifecycle.Lifecycle.State;
import androidx.lifecycle.LifecycleEventObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LifecycleRegistry;
import androidx.lifecycle.ReportFragment;
import androidx.lifecycle.SavedStateHandleSupport;
import androidx.lifecycle.SavedStateViewModelFactory;
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory;
import androidx.lifecycle.ViewModelProvider.Factory;
import androidx.lifecycle.ViewModelStore;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.lifecycle.ViewTreeLifecycleOwner;
import androidx.lifecycle.ViewTreeViewModelStoreOwner;
import androidx.lifecycle.viewmodel.CreationExtras;
import androidx.lifecycle.viewmodel.MutableCreationExtras;
import androidx.savedstate.SavedStateRegistry;
import androidx.savedstate.SavedStateRegistryController;
import androidx.savedstate.SavedStateRegistryOwner;
import androidx.savedstate.ViewTreeSavedStateRegistryOwner;
import androidx.tracing.Trace;
import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class ComponentActivity
  extends androidx.core.app.ComponentActivity
  implements ContextAware, LifecycleOwner, ViewModelStoreOwner, HasDefaultViewModelProviderFactory, SavedStateRegistryOwner, OnBackPressedDispatcherOwner, ActivityResultRegistryOwner, ActivityResultCaller, OnConfigurationChangedProvider, OnTrimMemoryProvider, OnNewIntentProvider, OnMultiWindowModeChangedProvider, OnPictureInPictureModeChangedProvider, MenuHost
{
  private static final String ACTIVITY_RESULT_TAG = "android:support:activity-result";
  private final ActivityResultRegistry mActivityResultRegistry;
  private int mContentLayoutId;
  final ContextAwareHelper mContextAwareHelper = new ContextAwareHelper();
  private ViewModelProvider.Factory mDefaultFactory;
  private final LifecycleRegistry mLifecycleRegistry = new LifecycleRegistry(this);
  private final MenuHostHelper mMenuHostHelper = new MenuHostHelper(new ComponentActivity..ExternalSyntheticLambda0(this));
  private final AtomicInteger mNextLocalRequestCode;
  private final OnBackPressedDispatcher mOnBackPressedDispatcher;
  private final CopyOnWriteArrayList<Consumer<Configuration>> mOnConfigurationChangedListeners;
  private final CopyOnWriteArrayList<Consumer<MultiWindowModeChangedInfo>> mOnMultiWindowModeChangedListeners;
  private final CopyOnWriteArrayList<Consumer<Intent>> mOnNewIntentListeners;
  private final CopyOnWriteArrayList<Consumer<PictureInPictureModeChangedInfo>> mOnPictureInPictureModeChangedListeners;
  private final CopyOnWriteArrayList<Consumer<Integer>> mOnTrimMemoryListeners;
  final SavedStateRegistryController mSavedStateRegistryController;
  private ViewModelStore mViewModelStore;
  
  public ComponentActivity()
  {
    SavedStateRegistryController localSavedStateRegistryController = SavedStateRegistryController.create(this);
    this.mSavedStateRegistryController = localSavedStateRegistryController;
    this.mOnBackPressedDispatcher = new OnBackPressedDispatcher(new Runnable()
    {
      public void run()
      {
        try
        {
          ComponentActivity.this.onBackPressed();
        }
        catch (IllegalStateException localIllegalStateException)
        {
          if (!TextUtils.equals(localIllegalStateException.getMessage(), "Can not perform this action after onSaveInstanceState")) {
            break label24;
          }
        }
        return;
        label24:
        throw localIllegalStateException;
      }
    });
    this.mNextLocalRequestCode = new AtomicInteger();
    this.mActivityResultRegistry = new ActivityResultRegistry()
    {
      public <I, O> void onLaunch(final int paramAnonymousInt, final ActivityResultContract<I, O> paramAnonymousActivityResultContract, I paramAnonymousI, ActivityOptionsCompat paramAnonymousActivityOptionsCompat)
      {
        ComponentActivity localComponentActivity = ComponentActivity.this;
        final Object localObject = paramAnonymousActivityResultContract.getSynchronousResult(localComponentActivity, paramAnonymousI);
        if (localObject != null)
        {
          new Handler(Looper.getMainLooper()).post(new Runnable()
          {
            public void run()
            {
              ComponentActivity.2.this.dispatchResult(paramAnonymousInt, localObject.getValue());
            }
          });
          return;
        }
        paramAnonymousI = paramAnonymousActivityResultContract.createIntent(localComponentActivity, paramAnonymousI);
        if ((paramAnonymousI.getExtras() != null) && (paramAnonymousI.getExtras().getClassLoader() == null)) {
          paramAnonymousI.setExtrasClassLoader(localComponentActivity.getClassLoader());
        }
        if (paramAnonymousI.hasExtra("androidx.activity.result.contract.extra.ACTIVITY_OPTIONS_BUNDLE"))
        {
          paramAnonymousActivityResultContract = paramAnonymousI.getBundleExtra("androidx.activity.result.contract.extra.ACTIVITY_OPTIONS_BUNDLE");
          paramAnonymousI.removeExtra("androidx.activity.result.contract.extra.ACTIVITY_OPTIONS_BUNDLE");
        }
        else if (paramAnonymousActivityOptionsCompat != null)
        {
          paramAnonymousActivityResultContract = paramAnonymousActivityOptionsCompat.toBundle();
        }
        else
        {
          paramAnonymousActivityResultContract = null;
        }
        if ("androidx.activity.result.contract.action.REQUEST_PERMISSIONS".equals(paramAnonymousI.getAction()))
        {
          paramAnonymousI = paramAnonymousI.getStringArrayExtra("androidx.activity.result.contract.extra.PERMISSIONS");
          paramAnonymousActivityResultContract = paramAnonymousI;
          if (paramAnonymousI == null) {
            paramAnonymousActivityResultContract = new String[0];
          }
          ActivityCompat.requestPermissions(localComponentActivity, paramAnonymousActivityResultContract, paramAnonymousInt);
        }
        else if ("androidx.activity.result.contract.action.INTENT_SENDER_REQUEST".equals(paramAnonymousI.getAction()))
        {
          paramAnonymousI = (IntentSenderRequest)paramAnonymousI.getParcelableExtra("androidx.activity.result.contract.extra.INTENT_SENDER_REQUEST");
          try
          {
            localObject = paramAnonymousI.getIntentSender();
            paramAnonymousActivityOptionsCompat = paramAnonymousI.getFillInIntent();
            int j = paramAnonymousI.getFlagsMask();
            int i = paramAnonymousI.getFlagsValues();
            try
            {
              ActivityCompat.startIntentSenderForResult(localComponentActivity, (IntentSender)localObject, paramAnonymousInt, paramAnonymousActivityOptionsCompat, j, i, 0, paramAnonymousActivityResultContract);
            }
            catch (IntentSender.SendIntentException paramAnonymousActivityResultContract) {}
            new Handler(Looper.getMainLooper()).post(new Runnable()
            {
              public void run()
              {
                ComponentActivity.2.this.dispatchResult(paramAnonymousInt, 0, new Intent().setAction("androidx.activity.result.contract.action.INTENT_SENDER_REQUEST").putExtra("androidx.activity.result.contract.extra.SEND_INTENT_EXCEPTION", paramAnonymousActivityResultContract));
              }
            });
          }
          catch (IntentSender.SendIntentException paramAnonymousActivityResultContract) {}
        }
        else
        {
          ActivityCompat.startActivityForResult(localComponentActivity, paramAnonymousI, paramAnonymousInt, paramAnonymousActivityResultContract);
        }
      }
    };
    this.mOnConfigurationChangedListeners = new CopyOnWriteArrayList();
    this.mOnTrimMemoryListeners = new CopyOnWriteArrayList();
    this.mOnNewIntentListeners = new CopyOnWriteArrayList();
    this.mOnMultiWindowModeChangedListeners = new CopyOnWriteArrayList();
    this.mOnPictureInPictureModeChangedListeners = new CopyOnWriteArrayList();
    if (getLifecycle() != null)
    {
      if (Build.VERSION.SDK_INT >= 19) {
        getLifecycle().addObserver(new LifecycleEventObserver()
        {
          public void onStateChanged(LifecycleOwner paramAnonymousLifecycleOwner, Lifecycle.Event paramAnonymousEvent)
          {
            if (paramAnonymousEvent == Lifecycle.Event.ON_STOP)
            {
              paramAnonymousLifecycleOwner = ComponentActivity.this.getWindow();
              if (paramAnonymousLifecycleOwner != null) {
                paramAnonymousLifecycleOwner = paramAnonymousLifecycleOwner.peekDecorView();
              } else {
                paramAnonymousLifecycleOwner = null;
              }
              if (paramAnonymousLifecycleOwner != null) {
                ComponentActivity.Api19Impl.cancelPendingInputEvents(paramAnonymousLifecycleOwner);
              }
            }
          }
        });
      }
      getLifecycle().addObserver(new LifecycleEventObserver()
      {
        public void onStateChanged(LifecycleOwner paramAnonymousLifecycleOwner, Lifecycle.Event paramAnonymousEvent)
        {
          if (paramAnonymousEvent == Lifecycle.Event.ON_DESTROY)
          {
            ComponentActivity.this.mContextAwareHelper.clearAvailableContext();
            if (!ComponentActivity.this.isChangingConfigurations()) {
              ComponentActivity.this.getViewModelStore().clear();
            }
          }
        }
      });
      getLifecycle().addObserver(new LifecycleEventObserver()
      {
        public void onStateChanged(LifecycleOwner paramAnonymousLifecycleOwner, Lifecycle.Event paramAnonymousEvent)
        {
          ComponentActivity.this.ensureViewModelStore();
          ComponentActivity.this.getLifecycle().removeObserver(this);
        }
      });
      localSavedStateRegistryController.performAttach();
      SavedStateHandleSupport.enableSavedStateHandles(this);
      if ((19 <= Build.VERSION.SDK_INT) && (Build.VERSION.SDK_INT <= 23)) {
        getLifecycle().addObserver(new ImmLeaksCleaner(this));
      }
      getSavedStateRegistry().registerSavedStateProvider("android:support:activity-result", new ComponentActivity..ExternalSyntheticLambda1(this));
      addOnContextAvailableListener(new ComponentActivity..ExternalSyntheticLambda2(this));
      return;
    }
    throw new IllegalStateException("getLifecycle() returned null in ComponentActivity's constructor. Please make sure you are lazily constructing your Lifecycle in the first call to getLifecycle() rather than relying on field initialization.");
  }
  
  public ComponentActivity(int paramInt)
  {
    this();
    this.mContentLayoutId = paramInt;
  }
  
  private void initViewTreeOwners()
  {
    ViewTreeLifecycleOwner.set(getWindow().getDecorView(), this);
    ViewTreeViewModelStoreOwner.set(getWindow().getDecorView(), this);
    ViewTreeSavedStateRegistryOwner.set(getWindow().getDecorView(), this);
    ViewTreeOnBackPressedDispatcherOwner.set(getWindow().getDecorView(), this);
  }
  
  public void addContentView(View paramView, ViewGroup.LayoutParams paramLayoutParams)
  {
    initViewTreeOwners();
    super.addContentView(paramView, paramLayoutParams);
  }
  
  public void addMenuProvider(MenuProvider paramMenuProvider)
  {
    this.mMenuHostHelper.addMenuProvider(paramMenuProvider);
  }
  
  public void addMenuProvider(MenuProvider paramMenuProvider, LifecycleOwner paramLifecycleOwner)
  {
    this.mMenuHostHelper.addMenuProvider(paramMenuProvider, paramLifecycleOwner);
  }
  
  public void addMenuProvider(MenuProvider paramMenuProvider, LifecycleOwner paramLifecycleOwner, Lifecycle.State paramState)
  {
    this.mMenuHostHelper.addMenuProvider(paramMenuProvider, paramLifecycleOwner, paramState);
  }
  
  public final void addOnConfigurationChangedListener(Consumer<Configuration> paramConsumer)
  {
    this.mOnConfigurationChangedListeners.add(paramConsumer);
  }
  
  public final void addOnContextAvailableListener(OnContextAvailableListener paramOnContextAvailableListener)
  {
    this.mContextAwareHelper.addOnContextAvailableListener(paramOnContextAvailableListener);
  }
  
  public final void addOnMultiWindowModeChangedListener(Consumer<MultiWindowModeChangedInfo> paramConsumer)
  {
    this.mOnMultiWindowModeChangedListeners.add(paramConsumer);
  }
  
  public final void addOnNewIntentListener(Consumer<Intent> paramConsumer)
  {
    this.mOnNewIntentListeners.add(paramConsumer);
  }
  
  public final void addOnPictureInPictureModeChangedListener(Consumer<PictureInPictureModeChangedInfo> paramConsumer)
  {
    this.mOnPictureInPictureModeChangedListeners.add(paramConsumer);
  }
  
  public final void addOnTrimMemoryListener(Consumer<Integer> paramConsumer)
  {
    this.mOnTrimMemoryListeners.add(paramConsumer);
  }
  
  void ensureViewModelStore()
  {
    if (this.mViewModelStore == null)
    {
      NonConfigurationInstances localNonConfigurationInstances = (NonConfigurationInstances)getLastNonConfigurationInstance();
      if (localNonConfigurationInstances != null) {
        this.mViewModelStore = localNonConfigurationInstances.viewModelStore;
      }
      if (this.mViewModelStore == null) {
        this.mViewModelStore = new ViewModelStore();
      }
    }
  }
  
  public final ActivityResultRegistry getActivityResultRegistry()
  {
    return this.mActivityResultRegistry;
  }
  
  public CreationExtras getDefaultViewModelCreationExtras()
  {
    MutableCreationExtras localMutableCreationExtras = new MutableCreationExtras();
    if (getApplication() != null) {
      localMutableCreationExtras.set(ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY, getApplication());
    }
    localMutableCreationExtras.set(SavedStateHandleSupport.SAVED_STATE_REGISTRY_OWNER_KEY, this);
    localMutableCreationExtras.set(SavedStateHandleSupport.VIEW_MODEL_STORE_OWNER_KEY, this);
    if ((getIntent() != null) && (getIntent().getExtras() != null)) {
      localMutableCreationExtras.set(SavedStateHandleSupport.DEFAULT_ARGS_KEY, getIntent().getExtras());
    }
    return localMutableCreationExtras;
  }
  
  public ViewModelProvider.Factory getDefaultViewModelProviderFactory()
  {
    if (this.mDefaultFactory == null)
    {
      Application localApplication = getApplication();
      Bundle localBundle;
      if (getIntent() != null) {
        localBundle = getIntent().getExtras();
      } else {
        localBundle = null;
      }
      this.mDefaultFactory = new SavedStateViewModelFactory(localApplication, this, localBundle);
    }
    return this.mDefaultFactory;
  }
  
  @Deprecated
  public Object getLastCustomNonConfigurationInstance()
  {
    Object localObject = (NonConfigurationInstances)getLastNonConfigurationInstance();
    if (localObject != null) {
      localObject = ((NonConfigurationInstances)localObject).custom;
    } else {
      localObject = null;
    }
    return localObject;
  }
  
  public Lifecycle getLifecycle()
  {
    return this.mLifecycleRegistry;
  }
  
  public final OnBackPressedDispatcher getOnBackPressedDispatcher()
  {
    return this.mOnBackPressedDispatcher;
  }
  
  public final SavedStateRegistry getSavedStateRegistry()
  {
    return this.mSavedStateRegistryController.getSavedStateRegistry();
  }
  
  public ViewModelStore getViewModelStore()
  {
    if (getApplication() != null)
    {
      ensureViewModelStore();
      return this.mViewModelStore;
    }
    throw new IllegalStateException("Your activity is not yet attached to the Application instance. You can't request ViewModel before onCreate call.");
  }
  
  public void invalidateMenu()
  {
    invalidateOptionsMenu();
  }
  
  @Deprecated
  protected void onActivityResult(int paramInt1, int paramInt2, Intent paramIntent)
  {
    if (!this.mActivityResultRegistry.dispatchResult(paramInt1, paramInt2, paramIntent)) {
      super.onActivityResult(paramInt1, paramInt2, paramIntent);
    }
  }
  
  public void onBackPressed()
  {
    this.mOnBackPressedDispatcher.onBackPressed();
  }
  
  public void onConfigurationChanged(Configuration paramConfiguration)
  {
    super.onConfigurationChanged(paramConfiguration);
    Iterator localIterator = this.mOnConfigurationChangedListeners.iterator();
    while (localIterator.hasNext()) {
      ((Consumer)localIterator.next()).accept(paramConfiguration);
    }
  }
  
  protected void onCreate(Bundle paramBundle)
  {
    this.mSavedStateRegistryController.performRestore(paramBundle);
    this.mContextAwareHelper.dispatchOnContextAvailable(this);
    super.onCreate(paramBundle);
    ReportFragment.injectIfNeededIn(this);
    int i = this.mContentLayoutId;
    if (i != 0) {
      setContentView(i);
    }
  }
  
  public boolean onCreatePanelMenu(int paramInt, Menu paramMenu)
  {
    if (paramInt == 0)
    {
      super.onCreatePanelMenu(paramInt, paramMenu);
      this.mMenuHostHelper.onCreateMenu(paramMenu, getMenuInflater());
    }
    return true;
  }
  
  public boolean onMenuItemSelected(int paramInt, MenuItem paramMenuItem)
  {
    if (super.onMenuItemSelected(paramInt, paramMenuItem)) {
      return true;
    }
    if (paramInt == 0) {
      return this.mMenuHostHelper.onMenuItemSelected(paramMenuItem);
    }
    return false;
  }
  
  public void onMultiWindowModeChanged(boolean paramBoolean)
  {
    Iterator localIterator = this.mOnMultiWindowModeChangedListeners.iterator();
    while (localIterator.hasNext()) {
      ((Consumer)localIterator.next()).accept(new MultiWindowModeChangedInfo(paramBoolean));
    }
  }
  
  public void onMultiWindowModeChanged(boolean paramBoolean, Configuration paramConfiguration)
  {
    Iterator localIterator = this.mOnMultiWindowModeChangedListeners.iterator();
    while (localIterator.hasNext()) {
      ((Consumer)localIterator.next()).accept(new MultiWindowModeChangedInfo(paramBoolean, paramConfiguration));
    }
  }
  
  protected void onNewIntent(Intent paramIntent)
  {
    super.onNewIntent(paramIntent);
    Iterator localIterator = this.mOnNewIntentListeners.iterator();
    while (localIterator.hasNext()) {
      ((Consumer)localIterator.next()).accept(paramIntent);
    }
  }
  
  public void onPanelClosed(int paramInt, Menu paramMenu)
  {
    this.mMenuHostHelper.onMenuClosed(paramMenu);
    super.onPanelClosed(paramInt, paramMenu);
  }
  
  public void onPictureInPictureModeChanged(boolean paramBoolean)
  {
    Iterator localIterator = this.mOnPictureInPictureModeChangedListeners.iterator();
    while (localIterator.hasNext()) {
      ((Consumer)localIterator.next()).accept(new PictureInPictureModeChangedInfo(paramBoolean));
    }
  }
  
  public void onPictureInPictureModeChanged(boolean paramBoolean, Configuration paramConfiguration)
  {
    Iterator localIterator = this.mOnPictureInPictureModeChangedListeners.iterator();
    while (localIterator.hasNext()) {
      ((Consumer)localIterator.next()).accept(new PictureInPictureModeChangedInfo(paramBoolean, paramConfiguration));
    }
  }
  
  public boolean onPreparePanel(int paramInt, View paramView, Menu paramMenu)
  {
    if (paramInt == 0)
    {
      super.onPreparePanel(paramInt, paramView, paramMenu);
      this.mMenuHostHelper.onPrepareMenu(paramMenu);
    }
    return true;
  }
  
  @Deprecated
  public void onRequestPermissionsResult(int paramInt, String[] paramArrayOfString, int[] paramArrayOfInt)
  {
    if ((!this.mActivityResultRegistry.dispatchResult(paramInt, -1, new Intent().putExtra("androidx.activity.result.contract.extra.PERMISSIONS", paramArrayOfString).putExtra("androidx.activity.result.contract.extra.PERMISSION_GRANT_RESULTS", paramArrayOfInt))) && (Build.VERSION.SDK_INT >= 23)) {
      super.onRequestPermissionsResult(paramInt, paramArrayOfString, paramArrayOfInt);
    }
  }
  
  @Deprecated
  public Object onRetainCustomNonConfigurationInstance()
  {
    return null;
  }
  
  public final Object onRetainNonConfigurationInstance()
  {
    Object localObject3 = onRetainCustomNonConfigurationInstance();
    Object localObject2 = this.mViewModelStore;
    Object localObject1 = localObject2;
    if (localObject2 == null)
    {
      NonConfigurationInstances localNonConfigurationInstances = (NonConfigurationInstances)getLastNonConfigurationInstance();
      localObject1 = localObject2;
      if (localNonConfigurationInstances != null) {
        localObject1 = localNonConfigurationInstances.viewModelStore;
      }
    }
    if ((localObject1 == null) && (localObject3 == null)) {
      return null;
    }
    localObject2 = new NonConfigurationInstances();
    ((NonConfigurationInstances)localObject2).custom = localObject3;
    ((NonConfigurationInstances)localObject2).viewModelStore = ((ViewModelStore)localObject1);
    return localObject2;
  }
  
  protected void onSaveInstanceState(Bundle paramBundle)
  {
    Lifecycle localLifecycle = getLifecycle();
    if ((localLifecycle instanceof LifecycleRegistry)) {
      ((LifecycleRegistry)localLifecycle).setCurrentState(Lifecycle.State.CREATED);
    }
    super.onSaveInstanceState(paramBundle);
    this.mSavedStateRegistryController.performSave(paramBundle);
  }
  
  public void onTrimMemory(int paramInt)
  {
    super.onTrimMemory(paramInt);
    Iterator localIterator = this.mOnTrimMemoryListeners.iterator();
    while (localIterator.hasNext()) {
      ((Consumer)localIterator.next()).accept(Integer.valueOf(paramInt));
    }
  }
  
  public Context peekAvailableContext()
  {
    return this.mContextAwareHelper.peekAvailableContext();
  }
  
  public final <I, O> ActivityResultLauncher<I> registerForActivityResult(ActivityResultContract<I, O> paramActivityResultContract, ActivityResultCallback<O> paramActivityResultCallback)
  {
    return registerForActivityResult(paramActivityResultContract, this.mActivityResultRegistry, paramActivityResultCallback);
  }
  
  public final <I, O> ActivityResultLauncher<I> registerForActivityResult(ActivityResultContract<I, O> paramActivityResultContract, ActivityResultRegistry paramActivityResultRegistry, ActivityResultCallback<O> paramActivityResultCallback)
  {
    return paramActivityResultRegistry.register("activity_rq#" + this.mNextLocalRequestCode.getAndIncrement(), this, paramActivityResultContract, paramActivityResultCallback);
  }
  
  public void removeMenuProvider(MenuProvider paramMenuProvider)
  {
    this.mMenuHostHelper.removeMenuProvider(paramMenuProvider);
  }
  
  public final void removeOnConfigurationChangedListener(Consumer<Configuration> paramConsumer)
  {
    this.mOnConfigurationChangedListeners.remove(paramConsumer);
  }
  
  public final void removeOnContextAvailableListener(OnContextAvailableListener paramOnContextAvailableListener)
  {
    this.mContextAwareHelper.removeOnContextAvailableListener(paramOnContextAvailableListener);
  }
  
  public final void removeOnMultiWindowModeChangedListener(Consumer<MultiWindowModeChangedInfo> paramConsumer)
  {
    this.mOnMultiWindowModeChangedListeners.remove(paramConsumer);
  }
  
  public final void removeOnNewIntentListener(Consumer<Intent> paramConsumer)
  {
    this.mOnNewIntentListeners.remove(paramConsumer);
  }
  
  public final void removeOnPictureInPictureModeChangedListener(Consumer<PictureInPictureModeChangedInfo> paramConsumer)
  {
    this.mOnPictureInPictureModeChangedListeners.remove(paramConsumer);
  }
  
  public final void removeOnTrimMemoryListener(Consumer<Integer> paramConsumer)
  {
    this.mOnTrimMemoryListeners.remove(paramConsumer);
  }
  
  public void reportFullyDrawn()
  {
    try
    {
      if (Trace.isEnabled()) {
        Trace.beginSection("reportFullyDrawn() for ComponentActivity");
      }
      if (Build.VERSION.SDK_INT > 19) {
        super.reportFullyDrawn();
      } else if ((Build.VERSION.SDK_INT == 19) && (ContextCompat.checkSelfPermission(this, "android.permission.UPDATE_DEVICE_STATS") == 0)) {
        super.reportFullyDrawn();
      }
      return;
    }
    finally
    {
      Trace.endSection();
    }
  }
  
  public void setContentView(int paramInt)
  {
    initViewTreeOwners();
    super.setContentView(paramInt);
  }
  
  public void setContentView(View paramView)
  {
    initViewTreeOwners();
    super.setContentView(paramView);
  }
  
  public void setContentView(View paramView, ViewGroup.LayoutParams paramLayoutParams)
  {
    initViewTreeOwners();
    super.setContentView(paramView, paramLayoutParams);
  }
  
  @Deprecated
  public void startActivityForResult(Intent paramIntent, int paramInt)
  {
    super.startActivityForResult(paramIntent, paramInt);
  }
  
  @Deprecated
  public void startActivityForResult(Intent paramIntent, int paramInt, Bundle paramBundle)
  {
    super.startActivityForResult(paramIntent, paramInt, paramBundle);
  }
  
  @Deprecated
  public void startIntentSenderForResult(IntentSender paramIntentSender, int paramInt1, Intent paramIntent, int paramInt2, int paramInt3, int paramInt4)
    throws IntentSender.SendIntentException
  {
    super.startIntentSenderForResult(paramIntentSender, paramInt1, paramIntent, paramInt2, paramInt3, paramInt4);
  }
  
  @Deprecated
  public void startIntentSenderForResult(IntentSender paramIntentSender, int paramInt1, Intent paramIntent, int paramInt2, int paramInt3, int paramInt4, Bundle paramBundle)
    throws IntentSender.SendIntentException
  {
    super.startIntentSenderForResult(paramIntentSender, paramInt1, paramIntent, paramInt2, paramInt3, paramInt4, paramBundle);
  }
  
  static class Api19Impl
  {
    static void cancelPendingInputEvents(View paramView)
    {
      paramView.cancelPendingInputEvents();
    }
  }
  
  static final class NonConfigurationInstances
  {
    Object custom;
    ViewModelStore viewModelStore;
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/activity/ComponentActivity.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */