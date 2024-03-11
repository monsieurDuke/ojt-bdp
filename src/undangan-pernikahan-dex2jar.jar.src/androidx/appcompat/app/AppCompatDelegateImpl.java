package androidx.appcompat.app;

import android.app.Activity;
import android.app.Dialog;
import android.app.UiModeManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.Resources.NotFoundException;
import android.content.res.Resources.Theme;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.LocaleList;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.ClassLoaderCreator;
import android.os.Parcelable.Creator;
import android.os.PowerManager;
import android.text.TextUtils;
import android.util.AndroidRuntimeException;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.view.KeyboardShortcutGroup;
import android.view.LayoutInflater;
import android.view.LayoutInflater.Factory2;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewGroup.MarginLayoutParams;
import android.view.ViewParent;
import android.view.Window;
import android.view.Window.Callback;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.ListAdapter;
import android.widget.PopupWindow;
import android.widget.TextView;
import androidx.appcompat.R.attr;
import androidx.appcompat.R.color;
import androidx.appcompat.R.id;
import androidx.appcompat.R.layout;
import androidx.appcompat.R.style;
import androidx.appcompat.R.styleable;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.appcompat.view.StandaloneActionMode;
import androidx.appcompat.view.SupportActionModeWrapper.CallbackWrapper;
import androidx.appcompat.view.SupportMenuInflater;
import androidx.appcompat.view.WindowCallbackWrapper;
import androidx.appcompat.view.menu.ListMenuPresenter;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.view.menu.MenuBuilder.Callback;
import androidx.appcompat.view.menu.MenuPresenter;
import androidx.appcompat.view.menu.MenuPresenter.Callback;
import androidx.appcompat.view.menu.MenuView;
import androidx.appcompat.widget.ActionBarContextView;
import androidx.appcompat.widget.AppCompatDrawableManager;
import androidx.appcompat.widget.ContentFrameLayout;
import androidx.appcompat.widget.ContentFrameLayout.OnAttachListener;
import androidx.appcompat.widget.DecorContentParent;
import androidx.appcompat.widget.FitWindowsViewGroup;
import androidx.appcompat.widget.FitWindowsViewGroup.OnFitSystemWindowsListener;
import androidx.appcompat.widget.TintTypedArray;
import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.widget.ViewStubCompat;
import androidx.appcompat.widget.ViewUtils;
import androidx.collection.SimpleArrayMap;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NavUtils;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat.ThemeCompat;
import androidx.core.util.ObjectsCompat;
import androidx.core.view.KeyEventDispatcher;
import androidx.core.view.KeyEventDispatcher.Component;
import androidx.core.view.LayoutInflaterCompat;
import androidx.core.view.OnApplyWindowInsetsListener;
import androidx.core.view.ViewCompat;
import androidx.core.view.ViewPropertyAnimatorCompat;
import androidx.core.view.ViewPropertyAnimatorListenerAdapter;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.widget.PopupWindowCompat;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.Lifecycle.State;
import androidx.lifecycle.LifecycleOwner;
import java.util.List;
import mt.Log229316;
import mt.Log5ECF72;
import mt.LogE84000;

class AppCompatDelegateImpl
  extends AppCompatDelegate
  implements MenuBuilder.Callback, LayoutInflater.Factory2
{
  static final String EXCEPTION_HANDLER_MESSAGE_SUFFIX = ". If the resource you are trying to use is a vector resource, you may be referencing it in an unsupported way. See AppCompatDelegate.setCompatVectorFromResourcesEnabled() for more info.";
  private static final boolean IS_PRE_LOLLIPOP;
  private static final boolean sCanApplyOverrideConfiguration;
  private static final boolean sCanReturnDifferentContext;
  private static boolean sInstalledExceptionHandler;
  private static final SimpleArrayMap<String, Integer> sLocalNightModes = new SimpleArrayMap();
  private static final int[] sWindowBackgroundStyleable;
  ActionBar mActionBar;
  private ActionMenuPresenterCallback mActionMenuPresenterCallback;
  androidx.appcompat.view.ActionMode mActionMode;
  PopupWindow mActionModePopup;
  ActionBarContextView mActionModeView;
  private boolean mActivityHandlesUiMode;
  private boolean mActivityHandlesUiModeChecked;
  final AppCompatCallback mAppCompatCallback;
  private AppCompatViewInflater mAppCompatViewInflater;
  private AppCompatWindowCallback mAppCompatWindowCallback;
  private AutoNightModeManager mAutoBatteryNightModeManager;
  private AutoNightModeManager mAutoTimeNightModeManager;
  private boolean mBaseContextAttached;
  private boolean mClosingActionMenu;
  final Context mContext;
  private boolean mCreated;
  private DecorContentParent mDecorContentParent;
  boolean mDestroyed;
  private Configuration mEffectiveConfiguration;
  private boolean mEnableDefaultActionBarUp;
  ViewPropertyAnimatorCompat mFadeAnim = null;
  private boolean mFeatureIndeterminateProgress;
  private boolean mFeatureProgress;
  private boolean mHandleNativeActionModes = true;
  boolean mHasActionBar;
  final Object mHost;
  int mInvalidatePanelMenuFeatures;
  boolean mInvalidatePanelMenuPosted;
  private final Runnable mInvalidatePanelMenuRunnable = new Runnable()
  {
    public void run()
    {
      if ((AppCompatDelegateImpl.this.mInvalidatePanelMenuFeatures & 0x1) != 0) {
        AppCompatDelegateImpl.this.doInvalidatePanelMenu(0);
      }
      if ((AppCompatDelegateImpl.this.mInvalidatePanelMenuFeatures & 0x1000) != 0) {
        AppCompatDelegateImpl.this.doInvalidatePanelMenu(108);
      }
      AppCompatDelegateImpl.this.mInvalidatePanelMenuPosted = false;
      AppCompatDelegateImpl.this.mInvalidatePanelMenuFeatures = 0;
    }
  };
  boolean mIsFloating;
  private LayoutIncludeDetector mLayoutIncludeDetector;
  private int mLocalNightMode = -100;
  private boolean mLongPressBackDown;
  MenuInflater mMenuInflater;
  boolean mOverlayActionBar;
  boolean mOverlayActionMode;
  private PanelMenuPresenterCallback mPanelMenuPresenterCallback;
  private PanelFeatureState[] mPanels;
  private PanelFeatureState mPreparedPanel;
  Runnable mShowActionModePopup;
  private View mStatusGuard;
  ViewGroup mSubDecor;
  private boolean mSubDecorInstalled;
  private Rect mTempRect1;
  private Rect mTempRect2;
  private int mThemeResId;
  private CharSequence mTitle;
  private TextView mTitleView;
  Window mWindow;
  boolean mWindowNoTitle;
  
  static
  {
    int i = Build.VERSION.SDK_INT;
    boolean bool2 = false;
    boolean bool1;
    if (i < 21) {
      bool1 = true;
    } else {
      bool1 = false;
    }
    IS_PRE_LOLLIPOP = bool1;
    sWindowBackgroundStyleable = new int[] { 16842836 };
    sCanReturnDifferentContext = "robolectric".equals(Build.FINGERPRINT) ^ true;
    if (Build.VERSION.SDK_INT >= 17) {
      bool2 = true;
    }
    sCanApplyOverrideConfiguration = bool2;
    if ((bool1) && (!sInstalledExceptionHandler))
    {
      Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler()
      {
        private boolean shouldWrapException(Throwable paramAnonymousThrowable)
        {
          boolean bool2 = paramAnonymousThrowable instanceof Resources.NotFoundException;
          boolean bool1 = false;
          if (bool2)
          {
            paramAnonymousThrowable = paramAnonymousThrowable.getMessage();
            if ((paramAnonymousThrowable != null) && ((paramAnonymousThrowable.contains("drawable")) || (paramAnonymousThrowable.contains("Drawable")))) {
              bool1 = true;
            }
            return bool1;
          }
          return false;
        }
        
        public void uncaughtException(Thread paramAnonymousThread, Throwable paramAnonymousThrowable)
        {
          if (shouldWrapException(paramAnonymousThrowable))
          {
            Resources.NotFoundException localNotFoundException = new Resources.NotFoundException(paramAnonymousThrowable.getMessage() + ". If the resource you are trying to use is a vector resource, you may be referencing it in an unsupported way. See AppCompatDelegate.setCompatVectorFromResourcesEnabled() for more info.");
            localNotFoundException.initCause(paramAnonymousThrowable.getCause());
            localNotFoundException.setStackTrace(paramAnonymousThrowable.getStackTrace());
            AppCompatDelegateImpl.this.uncaughtException(paramAnonymousThread, localNotFoundException);
          }
          else
          {
            AppCompatDelegateImpl.this.uncaughtException(paramAnonymousThread, paramAnonymousThrowable);
          }
        }
      });
      sInstalledExceptionHandler = true;
    }
  }
  
  AppCompatDelegateImpl(Activity paramActivity, AppCompatCallback paramAppCompatCallback)
  {
    this(paramActivity, null, paramAppCompatCallback, paramActivity);
  }
  
  AppCompatDelegateImpl(Dialog paramDialog, AppCompatCallback paramAppCompatCallback)
  {
    this(paramDialog.getContext(), paramDialog.getWindow(), paramAppCompatCallback, paramDialog);
  }
  
  AppCompatDelegateImpl(Context paramContext, Activity paramActivity, AppCompatCallback paramAppCompatCallback)
  {
    this(paramContext, null, paramAppCompatCallback, paramActivity);
  }
  
  AppCompatDelegateImpl(Context paramContext, Window paramWindow, AppCompatCallback paramAppCompatCallback)
  {
    this(paramContext, paramWindow, paramAppCompatCallback, paramContext);
  }
  
  private AppCompatDelegateImpl(Context paramContext, Window paramWindow, AppCompatCallback paramAppCompatCallback, Object paramObject)
  {
    this.mContext = paramContext;
    this.mAppCompatCallback = paramAppCompatCallback;
    this.mHost = paramObject;
    if ((this.mLocalNightMode == -100) && ((paramObject instanceof Dialog)))
    {
      paramContext = tryUnwrapContext();
      if (paramContext != null) {
        this.mLocalNightMode = paramContext.getDelegate().getLocalNightMode();
      }
    }
    if (this.mLocalNightMode == -100)
    {
      paramAppCompatCallback = sLocalNightModes;
      paramContext = (Integer)paramAppCompatCallback.get(paramObject.getClass().getName());
      if (paramContext != null)
      {
        this.mLocalNightMode = paramContext.intValue();
        paramAppCompatCallback.remove(paramObject.getClass().getName());
      }
    }
    if (paramWindow != null) {
      attachToWindow(paramWindow);
    }
    AppCompatDrawableManager.preload();
  }
  
  private boolean applyDayNight(boolean paramBoolean)
  {
    if (this.mDestroyed) {
      return false;
    }
    int i = calculateNightMode();
    paramBoolean = updateForNightMode(mapNightMode(this.mContext, i), paramBoolean);
    AutoNightModeManager localAutoNightModeManager;
    if (i == 0)
    {
      getAutoTimeNightModeManager(this.mContext).setup();
    }
    else
    {
      localAutoNightModeManager = this.mAutoTimeNightModeManager;
      if (localAutoNightModeManager != null) {
        localAutoNightModeManager.cleanup();
      }
    }
    if (i == 3)
    {
      getAutoBatteryNightModeManager(this.mContext).setup();
    }
    else
    {
      localAutoNightModeManager = this.mAutoBatteryNightModeManager;
      if (localAutoNightModeManager != null) {
        localAutoNightModeManager.cleanup();
      }
    }
    return paramBoolean;
  }
  
  private void applyFixedSizeWindow()
  {
    ContentFrameLayout localContentFrameLayout = (ContentFrameLayout)this.mSubDecor.findViewById(16908290);
    Object localObject = this.mWindow.getDecorView();
    localContentFrameLayout.setDecorPadding(((View)localObject).getPaddingLeft(), ((View)localObject).getPaddingTop(), ((View)localObject).getPaddingRight(), ((View)localObject).getPaddingBottom());
    localObject = this.mContext.obtainStyledAttributes(R.styleable.AppCompatTheme);
    ((TypedArray)localObject).getValue(R.styleable.AppCompatTheme_windowMinWidthMajor, localContentFrameLayout.getMinWidthMajor());
    ((TypedArray)localObject).getValue(R.styleable.AppCompatTheme_windowMinWidthMinor, localContentFrameLayout.getMinWidthMinor());
    if (((TypedArray)localObject).hasValue(R.styleable.AppCompatTheme_windowFixedWidthMajor)) {
      ((TypedArray)localObject).getValue(R.styleable.AppCompatTheme_windowFixedWidthMajor, localContentFrameLayout.getFixedWidthMajor());
    }
    if (((TypedArray)localObject).hasValue(R.styleable.AppCompatTheme_windowFixedWidthMinor)) {
      ((TypedArray)localObject).getValue(R.styleable.AppCompatTheme_windowFixedWidthMinor, localContentFrameLayout.getFixedWidthMinor());
    }
    if (((TypedArray)localObject).hasValue(R.styleable.AppCompatTheme_windowFixedHeightMajor)) {
      ((TypedArray)localObject).getValue(R.styleable.AppCompatTheme_windowFixedHeightMajor, localContentFrameLayout.getFixedHeightMajor());
    }
    if (((TypedArray)localObject).hasValue(R.styleable.AppCompatTheme_windowFixedHeightMinor)) {
      ((TypedArray)localObject).getValue(R.styleable.AppCompatTheme_windowFixedHeightMinor, localContentFrameLayout.getFixedHeightMinor());
    }
    ((TypedArray)localObject).recycle();
    localContentFrameLayout.requestLayout();
  }
  
  private void attachToWindow(Window paramWindow)
  {
    if (this.mWindow == null)
    {
      Object localObject = paramWindow.getCallback();
      if (!(localObject instanceof AppCompatWindowCallback))
      {
        localObject = new AppCompatWindowCallback((Window.Callback)localObject);
        this.mAppCompatWindowCallback = ((AppCompatWindowCallback)localObject);
        paramWindow.setCallback((Window.Callback)localObject);
        localObject = TintTypedArray.obtainStyledAttributes(this.mContext, null, sWindowBackgroundStyleable);
        Drawable localDrawable = ((TintTypedArray)localObject).getDrawableIfKnown(0);
        if (localDrawable != null) {
          paramWindow.setBackgroundDrawable(localDrawable);
        }
        ((TintTypedArray)localObject).recycle();
        this.mWindow = paramWindow;
        return;
      }
      throw new IllegalStateException("AppCompat has already installed itself into the Window");
    }
    throw new IllegalStateException("AppCompat has already installed itself into the Window");
  }
  
  private int calculateNightMode()
  {
    int i = this.mLocalNightMode;
    if (i == -100) {
      i = getDefaultNightMode();
    }
    return i;
  }
  
  private void cleanupAutoManagers()
  {
    AutoNightModeManager localAutoNightModeManager = this.mAutoTimeNightModeManager;
    if (localAutoNightModeManager != null) {
      localAutoNightModeManager.cleanup();
    }
    localAutoNightModeManager = this.mAutoBatteryNightModeManager;
    if (localAutoNightModeManager != null) {
      localAutoNightModeManager.cleanup();
    }
  }
  
  private Configuration createOverrideConfigurationForDayNight(Context paramContext, int paramInt, Configuration paramConfiguration, boolean paramBoolean)
  {
    switch (paramInt)
    {
    default: 
      if (paramBoolean) {
        paramInt = 0;
      }
      break;
    case 2: 
      paramInt = 32;
      break;
    case 1: 
      paramInt = 16;
      break;
    }
    paramInt = paramContext.getApplicationContext().getResources().getConfiguration().uiMode & 0x30;
    paramContext = new Configuration();
    paramContext.fontScale = 0.0F;
    if (paramConfiguration != null) {
      paramContext.setTo(paramConfiguration);
    }
    paramContext.uiMode = (paramContext.uiMode & 0xFFFFFFCF | paramInt);
    return paramContext;
  }
  
  private ViewGroup createSubDecor()
  {
    Object localObject1 = this.mContext.obtainStyledAttributes(R.styleable.AppCompatTheme);
    if (((TypedArray)localObject1).hasValue(R.styleable.AppCompatTheme_windowActionBar))
    {
      if (((TypedArray)localObject1).getBoolean(R.styleable.AppCompatTheme_windowNoTitle, false)) {
        requestWindowFeature(1);
      } else if (((TypedArray)localObject1).getBoolean(R.styleable.AppCompatTheme_windowActionBar, false)) {
        requestWindowFeature(108);
      }
      if (((TypedArray)localObject1).getBoolean(R.styleable.AppCompatTheme_windowActionBarOverlay, false)) {
        requestWindowFeature(109);
      }
      if (((TypedArray)localObject1).getBoolean(R.styleable.AppCompatTheme_windowActionModeOverlay, false)) {
        requestWindowFeature(10);
      }
      this.mIsFloating = ((TypedArray)localObject1).getBoolean(R.styleable.AppCompatTheme_android_windowIsFloating, false);
      ((TypedArray)localObject1).recycle();
      ensureWindow();
      this.mWindow.getDecorView();
      Object localObject2 = LayoutInflater.from(this.mContext);
      localObject1 = null;
      if (!this.mWindowNoTitle)
      {
        if (this.mIsFloating)
        {
          localObject1 = (ViewGroup)((LayoutInflater)localObject2).inflate(R.layout.abc_dialog_title_material, null);
          this.mOverlayActionBar = false;
          this.mHasActionBar = false;
        }
        else if (this.mHasActionBar)
        {
          localObject1 = new TypedValue();
          this.mContext.getTheme().resolveAttribute(R.attr.actionBarTheme, (TypedValue)localObject1, true);
          if (((TypedValue)localObject1).resourceId != 0) {
            localObject1 = new androidx.appcompat.view.ContextThemeWrapper(this.mContext, ((TypedValue)localObject1).resourceId);
          } else {
            localObject1 = this.mContext;
          }
          localObject1 = (ViewGroup)LayoutInflater.from((Context)localObject1).inflate(R.layout.abc_screen_toolbar, null);
          localObject2 = (DecorContentParent)((ViewGroup)localObject1).findViewById(R.id.decor_content_parent);
          this.mDecorContentParent = ((DecorContentParent)localObject2);
          ((DecorContentParent)localObject2).setWindowCallback(getWindowCallback());
          if (this.mOverlayActionBar) {
            this.mDecorContentParent.initFeature(109);
          }
          if (this.mFeatureProgress) {
            this.mDecorContentParent.initFeature(2);
          }
          if (this.mFeatureIndeterminateProgress) {
            this.mDecorContentParent.initFeature(5);
          }
        }
      }
      else if (this.mOverlayActionMode) {
        localObject1 = (ViewGroup)((LayoutInflater)localObject2).inflate(R.layout.abc_screen_simple_overlay_action_mode, null);
      } else {
        localObject1 = (ViewGroup)((LayoutInflater)localObject2).inflate(R.layout.abc_screen_simple, null);
      }
      if (localObject1 != null)
      {
        if (Build.VERSION.SDK_INT >= 21) {
          ViewCompat.setOnApplyWindowInsetsListener((View)localObject1, new OnApplyWindowInsetsListener()
          {
            public WindowInsetsCompat onApplyWindowInsets(View paramAnonymousView, WindowInsetsCompat paramAnonymousWindowInsetsCompat)
            {
              int i = paramAnonymousWindowInsetsCompat.getSystemWindowInsetTop();
              int j = AppCompatDelegateImpl.this.updateStatusGuard(paramAnonymousWindowInsetsCompat, null);
              WindowInsetsCompat localWindowInsetsCompat = paramAnonymousWindowInsetsCompat;
              if (i != j) {
                localWindowInsetsCompat = paramAnonymousWindowInsetsCompat.replaceSystemWindowInsets(paramAnonymousWindowInsetsCompat.getSystemWindowInsetLeft(), j, paramAnonymousWindowInsetsCompat.getSystemWindowInsetRight(), paramAnonymousWindowInsetsCompat.getSystemWindowInsetBottom());
              }
              return ViewCompat.onApplyWindowInsets(paramAnonymousView, localWindowInsetsCompat);
            }
          });
        } else if ((localObject1 instanceof FitWindowsViewGroup)) {
          ((FitWindowsViewGroup)localObject1).setOnFitSystemWindowsListener(new FitWindowsViewGroup.OnFitSystemWindowsListener()
          {
            public void onFitSystemWindows(Rect paramAnonymousRect)
            {
              paramAnonymousRect.top = AppCompatDelegateImpl.this.updateStatusGuard(null, paramAnonymousRect);
            }
          });
        }
        if (this.mDecorContentParent == null) {
          this.mTitleView = ((TextView)((ViewGroup)localObject1).findViewById(R.id.title));
        }
        ViewUtils.makeOptionalFitsSystemWindows((View)localObject1);
        ContentFrameLayout localContentFrameLayout = (ContentFrameLayout)((ViewGroup)localObject1).findViewById(R.id.action_bar_activity_content);
        ViewGroup localViewGroup = (ViewGroup)this.mWindow.findViewById(16908290);
        if (localViewGroup != null)
        {
          while (localViewGroup.getChildCount() > 0)
          {
            localObject2 = localViewGroup.getChildAt(0);
            localViewGroup.removeViewAt(0);
            localContentFrameLayout.addView((View)localObject2);
          }
          localViewGroup.setId(-1);
          localContentFrameLayout.setId(16908290);
          if ((localViewGroup instanceof FrameLayout)) {
            ((FrameLayout)localViewGroup).setForeground(null);
          }
        }
        this.mWindow.setContentView((View)localObject1);
        localContentFrameLayout.setAttachListener(new ContentFrameLayout.OnAttachListener()
        {
          public void onAttachedFromWindow() {}
          
          public void onDetachedFromWindow()
          {
            AppCompatDelegateImpl.this.dismissPopups();
          }
        });
        return (ViewGroup)localObject1;
      }
      throw new IllegalArgumentException("AppCompat does not support the current theme features: { windowActionBar: " + this.mHasActionBar + ", windowActionBarOverlay: " + this.mOverlayActionBar + ", android:windowIsFloating: " + this.mIsFloating + ", windowActionModeOverlay: " + this.mOverlayActionMode + ", windowNoTitle: " + this.mWindowNoTitle + " }");
    }
    ((TypedArray)localObject1).recycle();
    throw new IllegalStateException("You need to use a Theme.AppCompat theme (or descendant) with this activity.");
  }
  
  private void ensureSubDecor()
  {
    if (!this.mSubDecorInstalled)
    {
      this.mSubDecor = createSubDecor();
      Object localObject1 = getTitle();
      if (!TextUtils.isEmpty((CharSequence)localObject1))
      {
        Object localObject2 = this.mDecorContentParent;
        if (localObject2 != null)
        {
          ((DecorContentParent)localObject2).setWindowTitle((CharSequence)localObject1);
        }
        else if (peekSupportActionBar() != null)
        {
          peekSupportActionBar().setWindowTitle((CharSequence)localObject1);
        }
        else
        {
          localObject2 = this.mTitleView;
          if (localObject2 != null) {
            ((TextView)localObject2).setText((CharSequence)localObject1);
          }
        }
      }
      applyFixedSizeWindow();
      onSubDecorInstalled(this.mSubDecor);
      this.mSubDecorInstalled = true;
      localObject1 = getPanelState(0, false);
      if ((!this.mDestroyed) && ((localObject1 == null) || (((PanelFeatureState)localObject1).menu == null))) {
        invalidatePanelMenu(108);
      }
    }
  }
  
  private void ensureWindow()
  {
    if (this.mWindow == null)
    {
      Object localObject = this.mHost;
      if ((localObject instanceof Activity)) {
        attachToWindow(((Activity)localObject).getWindow());
      }
    }
    if (this.mWindow != null) {
      return;
    }
    throw new IllegalStateException("We have not been given a Window");
  }
  
  private static Configuration generateConfigDelta(Configuration paramConfiguration1, Configuration paramConfiguration2)
  {
    Configuration localConfiguration = new Configuration();
    localConfiguration.fontScale = 0.0F;
    if ((paramConfiguration2 != null) && (paramConfiguration1.diff(paramConfiguration2) != 0))
    {
      if (paramConfiguration1.fontScale != paramConfiguration2.fontScale) {
        localConfiguration.fontScale = paramConfiguration2.fontScale;
      }
      if (paramConfiguration1.mcc != paramConfiguration2.mcc) {
        localConfiguration.mcc = paramConfiguration2.mcc;
      }
      if (paramConfiguration1.mnc != paramConfiguration2.mnc) {
        localConfiguration.mnc = paramConfiguration2.mnc;
      }
      if (Build.VERSION.SDK_INT >= 24) {
        Api24Impl.generateConfigDelta_locale(paramConfiguration1, paramConfiguration2, localConfiguration);
      } else if (!ObjectsCompat.equals(paramConfiguration1.locale, paramConfiguration2.locale)) {
        localConfiguration.locale = paramConfiguration2.locale;
      }
      if (paramConfiguration1.touchscreen != paramConfiguration2.touchscreen) {
        localConfiguration.touchscreen = paramConfiguration2.touchscreen;
      }
      if (paramConfiguration1.keyboard != paramConfiguration2.keyboard) {
        localConfiguration.keyboard = paramConfiguration2.keyboard;
      }
      if (paramConfiguration1.keyboardHidden != paramConfiguration2.keyboardHidden) {
        localConfiguration.keyboardHidden = paramConfiguration2.keyboardHidden;
      }
      if (paramConfiguration1.navigation != paramConfiguration2.navigation) {
        localConfiguration.navigation = paramConfiguration2.navigation;
      }
      if (paramConfiguration1.navigationHidden != paramConfiguration2.navigationHidden) {
        localConfiguration.navigationHidden = paramConfiguration2.navigationHidden;
      }
      if (paramConfiguration1.orientation != paramConfiguration2.orientation) {
        localConfiguration.orientation = paramConfiguration2.orientation;
      }
      if ((paramConfiguration1.screenLayout & 0xF) != (paramConfiguration2.screenLayout & 0xF)) {
        localConfiguration.screenLayout |= paramConfiguration2.screenLayout & 0xF;
      }
      if ((paramConfiguration1.screenLayout & 0xC0) != (paramConfiguration2.screenLayout & 0xC0)) {
        localConfiguration.screenLayout |= paramConfiguration2.screenLayout & 0xC0;
      }
      if ((paramConfiguration1.screenLayout & 0x30) != (paramConfiguration2.screenLayout & 0x30)) {
        localConfiguration.screenLayout |= paramConfiguration2.screenLayout & 0x30;
      }
      if ((paramConfiguration1.screenLayout & 0x300) != (paramConfiguration2.screenLayout & 0x300)) {
        localConfiguration.screenLayout |= paramConfiguration2.screenLayout & 0x300;
      }
      if (Build.VERSION.SDK_INT >= 26) {
        Api26Impl.generateConfigDelta_colorMode(paramConfiguration1, paramConfiguration2, localConfiguration);
      }
      if ((paramConfiguration1.uiMode & 0xF) != (paramConfiguration2.uiMode & 0xF)) {
        localConfiguration.uiMode |= paramConfiguration2.uiMode & 0xF;
      }
      if ((paramConfiguration1.uiMode & 0x30) != (paramConfiguration2.uiMode & 0x30)) {
        localConfiguration.uiMode |= paramConfiguration2.uiMode & 0x30;
      }
      if (paramConfiguration1.screenWidthDp != paramConfiguration2.screenWidthDp) {
        localConfiguration.screenWidthDp = paramConfiguration2.screenWidthDp;
      }
      if (paramConfiguration1.screenHeightDp != paramConfiguration2.screenHeightDp) {
        localConfiguration.screenHeightDp = paramConfiguration2.screenHeightDp;
      }
      if (paramConfiguration1.smallestScreenWidthDp != paramConfiguration2.smallestScreenWidthDp) {
        localConfiguration.smallestScreenWidthDp = paramConfiguration2.smallestScreenWidthDp;
      }
      if (Build.VERSION.SDK_INT >= 17) {
        Api17Impl.generateConfigDelta_densityDpi(paramConfiguration1, paramConfiguration2, localConfiguration);
      }
      return localConfiguration;
    }
    return localConfiguration;
  }
  
  private AutoNightModeManager getAutoBatteryNightModeManager(Context paramContext)
  {
    if (this.mAutoBatteryNightModeManager == null) {
      this.mAutoBatteryNightModeManager = new AutoBatteryNightModeManager(paramContext);
    }
    return this.mAutoBatteryNightModeManager;
  }
  
  private AutoNightModeManager getAutoTimeNightModeManager(Context paramContext)
  {
    if (this.mAutoTimeNightModeManager == null) {
      this.mAutoTimeNightModeManager = new AutoTimeNightModeManager(TwilightManager.getInstance(paramContext));
    }
    return this.mAutoTimeNightModeManager;
  }
  
  private void initWindowDecorActionBar()
  {
    ensureSubDecor();
    if ((this.mHasActionBar) && (this.mActionBar == null))
    {
      Object localObject = this.mHost;
      if ((localObject instanceof Activity)) {
        this.mActionBar = new WindowDecorActionBar((Activity)this.mHost, this.mOverlayActionBar);
      } else if ((localObject instanceof Dialog)) {
        this.mActionBar = new WindowDecorActionBar((Dialog)this.mHost);
      }
      localObject = this.mActionBar;
      if (localObject != null) {
        ((ActionBar)localObject).setDefaultDisplayHomeAsUpEnabled(this.mEnableDefaultActionBarUp);
      }
      return;
    }
  }
  
  private boolean initializePanelContent(PanelFeatureState paramPanelFeatureState)
  {
    View localView = paramPanelFeatureState.createdPanelView;
    boolean bool = true;
    if (localView != null)
    {
      paramPanelFeatureState.shownPanelView = paramPanelFeatureState.createdPanelView;
      return true;
    }
    if (paramPanelFeatureState.menu == null) {
      return false;
    }
    if (this.mPanelMenuPresenterCallback == null) {
      this.mPanelMenuPresenterCallback = new PanelMenuPresenterCallback();
    }
    paramPanelFeatureState.shownPanelView = ((View)paramPanelFeatureState.getListMenuView(this.mPanelMenuPresenterCallback));
    if (paramPanelFeatureState.shownPanelView == null) {
      bool = false;
    }
    return bool;
  }
  
  private boolean initializePanelDecor(PanelFeatureState paramPanelFeatureState)
  {
    paramPanelFeatureState.setStyle(getActionBarThemedContext());
    paramPanelFeatureState.decorView = new ListMenuDecorView(paramPanelFeatureState.listPresenterContext);
    paramPanelFeatureState.gravity = 81;
    return true;
  }
  
  private boolean initializePanelMenu(PanelFeatureState paramPanelFeatureState)
  {
    Context localContext = this.mContext;
    if (paramPanelFeatureState.featureId != 0)
    {
      localObject1 = localContext;
      if (paramPanelFeatureState.featureId != 108) {}
    }
    else
    {
      localObject1 = localContext;
      if (this.mDecorContentParent != null)
      {
        TypedValue localTypedValue = new TypedValue();
        Resources.Theme localTheme = localContext.getTheme();
        localTheme.resolveAttribute(R.attr.actionBarTheme, localTypedValue, true);
        localObject1 = null;
        if (localTypedValue.resourceId != 0)
        {
          localObject1 = localContext.getResources().newTheme();
          ((Resources.Theme)localObject1).setTo(localTheme);
          ((Resources.Theme)localObject1).applyStyle(localTypedValue.resourceId, true);
          ((Resources.Theme)localObject1).resolveAttribute(R.attr.actionBarWidgetTheme, localTypedValue, true);
        }
        else
        {
          localTheme.resolveAttribute(R.attr.actionBarWidgetTheme, localTypedValue, true);
        }
        Object localObject2 = localObject1;
        if (localTypedValue.resourceId != 0)
        {
          localObject2 = localObject1;
          if (localObject1 == null)
          {
            localObject2 = localContext.getResources().newTheme();
            ((Resources.Theme)localObject2).setTo(localTheme);
          }
          ((Resources.Theme)localObject2).applyStyle(localTypedValue.resourceId, true);
        }
        localObject1 = localContext;
        if (localObject2 != null)
        {
          localObject1 = new androidx.appcompat.view.ContextThemeWrapper(localContext, 0);
          ((Context)localObject1).getTheme().setTo((Resources.Theme)localObject2);
        }
      }
    }
    Object localObject1 = new MenuBuilder((Context)localObject1);
    ((MenuBuilder)localObject1).setCallback(this);
    paramPanelFeatureState.setMenu((MenuBuilder)localObject1);
    return true;
  }
  
  private void invalidatePanelMenu(int paramInt)
  {
    this.mInvalidatePanelMenuFeatures |= 1 << paramInt;
    if (!this.mInvalidatePanelMenuPosted)
    {
      ViewCompat.postOnAnimation(this.mWindow.getDecorView(), this.mInvalidatePanelMenuRunnable);
      this.mInvalidatePanelMenuPosted = true;
    }
  }
  
  private boolean isActivityManifestHandlingUiMode(Context paramContext)
  {
    if ((!this.mActivityHandlesUiModeChecked) && ((this.mHost instanceof Activity)))
    {
      PackageManager localPackageManager = paramContext.getPackageManager();
      if (localPackageManager == null) {
        return false;
      }
      int i = 0;
      try
      {
        if (Build.VERSION.SDK_INT >= 29) {
          i = 269221888;
        } else if (Build.VERSION.SDK_INT >= 24) {
          i = 786432;
        }
        ComponentName localComponentName = new android/content/ComponentName;
        localComponentName.<init>(paramContext, this.mHost.getClass());
        paramContext = localPackageManager.getActivityInfo(localComponentName, i);
        boolean bool;
        if ((paramContext != null) && ((paramContext.configChanges & 0x200) != 0)) {
          bool = true;
        } else {
          bool = false;
        }
        this.mActivityHandlesUiMode = bool;
      }
      catch (PackageManager.NameNotFoundException paramContext)
      {
        Log.d("AppCompatDelegate", "Exception while getting ActivityInfo", paramContext);
        this.mActivityHandlesUiMode = false;
      }
    }
    this.mActivityHandlesUiModeChecked = true;
    return this.mActivityHandlesUiMode;
  }
  
  private boolean onKeyDownPanel(int paramInt, KeyEvent paramKeyEvent)
  {
    if (paramKeyEvent.getRepeatCount() == 0)
    {
      PanelFeatureState localPanelFeatureState = getPanelState(paramInt, true);
      if (!localPanelFeatureState.isOpen) {
        return preparePanel(localPanelFeatureState, paramKeyEvent);
      }
    }
    return false;
  }
  
  private boolean onKeyUpPanel(int paramInt, KeyEvent paramKeyEvent)
  {
    if (this.mActionMode != null) {
      return false;
    }
    boolean bool3 = false;
    PanelFeatureState localPanelFeatureState = getPanelState(paramInt, true);
    boolean bool1;
    if (paramInt == 0)
    {
      DecorContentParent localDecorContentParent = this.mDecorContentParent;
      if ((localDecorContentParent != null) && (localDecorContentParent.canShowOverflowMenu()) && (!ViewConfiguration.get(this.mContext).hasPermanentMenuKey()))
      {
        if (!this.mDecorContentParent.isOverflowMenuShowing())
        {
          bool1 = bool3;
          if (this.mDestroyed) {
            break label208;
          }
          bool1 = bool3;
          if (!preparePanel(localPanelFeatureState, paramKeyEvent)) {
            break label208;
          }
          bool1 = this.mDecorContentParent.showOverflowMenu();
          break label208;
        }
        bool1 = this.mDecorContentParent.hideOverflowMenu();
        break label208;
      }
    }
    if ((!localPanelFeatureState.isOpen) && (!localPanelFeatureState.isHandled))
    {
      bool1 = bool3;
      if (localPanelFeatureState.isPrepared)
      {
        boolean bool2 = true;
        if (localPanelFeatureState.refreshMenuContent)
        {
          localPanelFeatureState.isPrepared = false;
          bool2 = preparePanel(localPanelFeatureState, paramKeyEvent);
        }
        bool1 = bool3;
        if (bool2)
        {
          openPanel(localPanelFeatureState, paramKeyEvent);
          bool1 = true;
        }
      }
    }
    else
    {
      bool1 = localPanelFeatureState.isOpen;
      closePanel(localPanelFeatureState, true);
    }
    label208:
    if (bool1)
    {
      paramKeyEvent = (AudioManager)this.mContext.getApplicationContext().getSystemService("audio");
      if (paramKeyEvent != null) {
        paramKeyEvent.playSoundEffect(0);
      } else {
        Log.w("AppCompatDelegate", "Couldn't get audio manager");
      }
    }
    return bool1;
  }
  
  private void openPanel(PanelFeatureState paramPanelFeatureState, KeyEvent paramKeyEvent)
  {
    if ((!paramPanelFeatureState.isOpen) && (!this.mDestroyed))
    {
      int i;
      if (paramPanelFeatureState.featureId == 0)
      {
        if ((this.mContext.getResources().getConfiguration().screenLayout & 0xF) == 4) {
          i = 1;
        } else {
          i = 0;
        }
        if (i != 0) {
          return;
        }
      }
      Object localObject = getWindowCallback();
      if ((localObject != null) && (!((Window.Callback)localObject).onMenuOpened(paramPanelFeatureState.featureId, paramPanelFeatureState.menu)))
      {
        closePanel(paramPanelFeatureState, true);
        return;
      }
      WindowManager localWindowManager = (WindowManager)this.mContext.getSystemService("window");
      if (localWindowManager == null) {
        return;
      }
      if (!preparePanel(paramPanelFeatureState, paramKeyEvent)) {
        return;
      }
      int j = -2;
      if ((paramPanelFeatureState.decorView != null) && (!paramPanelFeatureState.refreshDecorView)) {
        if (paramPanelFeatureState.createdPanelView != null)
        {
          paramKeyEvent = paramPanelFeatureState.createdPanelView.getLayoutParams();
          i = j;
          if (paramKeyEvent == null) {
            break label357;
          }
          i = j;
          if (paramKeyEvent.width != -1) {
            break label357;
          }
          i = -1;
          break label357;
        }
      }
      for (;;)
      {
        i = j;
        break;
        if (paramPanelFeatureState.decorView == null)
        {
          if ((initializePanelDecor(paramPanelFeatureState)) && (paramPanelFeatureState.decorView != null)) {}
        }
        else if ((paramPanelFeatureState.refreshDecorView) && (paramPanelFeatureState.decorView.getChildCount() > 0)) {
          paramPanelFeatureState.decorView.removeAllViews();
        }
        if ((!initializePanelContent(paramPanelFeatureState)) || (!paramPanelFeatureState.hasPanelItems())) {
          break label423;
        }
        localObject = paramPanelFeatureState.shownPanelView.getLayoutParams();
        paramKeyEvent = (KeyEvent)localObject;
        if (localObject == null) {
          paramKeyEvent = new ViewGroup.LayoutParams(-2, -2);
        }
        i = paramPanelFeatureState.background;
        paramPanelFeatureState.decorView.setBackgroundResource(i);
        localObject = paramPanelFeatureState.shownPanelView.getParent();
        if ((localObject instanceof ViewGroup)) {
          ((ViewGroup)localObject).removeView(paramPanelFeatureState.shownPanelView);
        }
        paramPanelFeatureState.decorView.addView(paramPanelFeatureState.shownPanelView, paramKeyEvent);
        if (!paramPanelFeatureState.shownPanelView.hasFocus()) {
          paramPanelFeatureState.shownPanelView.requestFocus();
        }
      }
      label357:
      paramPanelFeatureState.isHandled = false;
      paramKeyEvent = new WindowManager.LayoutParams(i, -2, paramPanelFeatureState.x, paramPanelFeatureState.y, 1002, 8519680, -3);
      paramKeyEvent.gravity = paramPanelFeatureState.gravity;
      paramKeyEvent.windowAnimations = paramPanelFeatureState.windowAnimations;
      localWindowManager.addView(paramPanelFeatureState.decorView, paramKeyEvent);
      paramPanelFeatureState.isOpen = true;
      return;
      label423:
      paramPanelFeatureState.refreshDecorView = true;
      return;
    }
  }
  
  private boolean performPanelShortcut(PanelFeatureState paramPanelFeatureState, int paramInt1, KeyEvent paramKeyEvent, int paramInt2)
  {
    if (paramKeyEvent.isSystem()) {
      return false;
    }
    boolean bool2 = false;
    boolean bool1;
    if (!paramPanelFeatureState.isPrepared)
    {
      bool1 = bool2;
      if (!preparePanel(paramPanelFeatureState, paramKeyEvent)) {}
    }
    else
    {
      bool1 = bool2;
      if (paramPanelFeatureState.menu != null) {
        bool1 = paramPanelFeatureState.menu.performShortcut(paramInt1, paramKeyEvent, paramInt2);
      }
    }
    if ((bool1) && ((paramInt2 & 0x1) == 0) && (this.mDecorContentParent == null)) {
      closePanel(paramPanelFeatureState, true);
    }
    return bool1;
  }
  
  private boolean preparePanel(PanelFeatureState paramPanelFeatureState, KeyEvent paramKeyEvent)
  {
    if (this.mDestroyed) {
      return false;
    }
    if (paramPanelFeatureState.isPrepared) {
      return true;
    }
    Object localObject = this.mPreparedPanel;
    if ((localObject != null) && (localObject != paramPanelFeatureState)) {
      closePanel((PanelFeatureState)localObject, false);
    }
    localObject = getWindowCallback();
    if (localObject != null) {
      paramPanelFeatureState.createdPanelView = ((Window.Callback)localObject).onCreatePanelView(paramPanelFeatureState.featureId);
    }
    int i;
    if ((paramPanelFeatureState.featureId != 0) && (paramPanelFeatureState.featureId != 108)) {
      i = 0;
    } else {
      i = 1;
    }
    if (i != 0)
    {
      DecorContentParent localDecorContentParent = this.mDecorContentParent;
      if (localDecorContentParent != null) {
        localDecorContentParent.setMenuPrepared();
      }
    }
    if ((paramPanelFeatureState.createdPanelView == null) && ((i == 0) || (!(peekSupportActionBar() instanceof ToolbarActionBar))))
    {
      if ((paramPanelFeatureState.menu == null) || (paramPanelFeatureState.refreshMenuContent))
      {
        if ((paramPanelFeatureState.menu == null) && ((!initializePanelMenu(paramPanelFeatureState)) || (paramPanelFeatureState.menu == null))) {
          return false;
        }
        if ((i != 0) && (this.mDecorContentParent != null))
        {
          if (this.mActionMenuPresenterCallback == null) {
            this.mActionMenuPresenterCallback = new ActionMenuPresenterCallback();
          }
          this.mDecorContentParent.setMenu(paramPanelFeatureState.menu, this.mActionMenuPresenterCallback);
        }
        paramPanelFeatureState.menu.stopDispatchingItemsChanged();
        if (!((Window.Callback)localObject).onCreatePanelMenu(paramPanelFeatureState.featureId, paramPanelFeatureState.menu))
        {
          paramPanelFeatureState.setMenu(null);
          if (i != 0)
          {
            paramPanelFeatureState = this.mDecorContentParent;
            if (paramPanelFeatureState != null) {
              paramPanelFeatureState.setMenu(null, this.mActionMenuPresenterCallback);
            }
          }
          return false;
        }
        paramPanelFeatureState.refreshMenuContent = false;
      }
      paramPanelFeatureState.menu.stopDispatchingItemsChanged();
      if (paramPanelFeatureState.frozenActionViewState != null)
      {
        paramPanelFeatureState.menu.restoreActionViewStates(paramPanelFeatureState.frozenActionViewState);
        paramPanelFeatureState.frozenActionViewState = null;
      }
      if (!((Window.Callback)localObject).onPreparePanel(0, paramPanelFeatureState.createdPanelView, paramPanelFeatureState.menu))
      {
        if (i != 0)
        {
          paramKeyEvent = this.mDecorContentParent;
          if (paramKeyEvent != null) {
            paramKeyEvent.setMenu(null, this.mActionMenuPresenterCallback);
          }
        }
        paramPanelFeatureState.menu.startDispatchingItemsChanged();
        return false;
      }
      if (paramKeyEvent != null) {
        i = paramKeyEvent.getDeviceId();
      } else {
        i = -1;
      }
      boolean bool;
      if (KeyCharacterMap.load(i).getKeyboardType() != 1) {
        bool = true;
      } else {
        bool = false;
      }
      paramPanelFeatureState.qwertyMode = bool;
      paramPanelFeatureState.menu.setQwertyMode(paramPanelFeatureState.qwertyMode);
      paramPanelFeatureState.menu.startDispatchingItemsChanged();
    }
    paramPanelFeatureState.isPrepared = true;
    paramPanelFeatureState.isHandled = false;
    this.mPreparedPanel = paramPanelFeatureState;
    return true;
  }
  
  private void reopenMenu(boolean paramBoolean)
  {
    Object localObject = this.mDecorContentParent;
    if ((localObject != null) && (((DecorContentParent)localObject).canShowOverflowMenu()) && ((!ViewConfiguration.get(this.mContext).hasPermanentMenuKey()) || (this.mDecorContentParent.isOverflowMenuShowPending())))
    {
      Window.Callback localCallback = getWindowCallback();
      if ((this.mDecorContentParent.isOverflowMenuShowing()) && (paramBoolean))
      {
        this.mDecorContentParent.hideOverflowMenu();
        if (!this.mDestroyed) {
          localCallback.onPanelClosed(108, getPanelState(0, true).menu);
        }
      }
      else if ((localCallback != null) && (!this.mDestroyed))
      {
        if ((this.mInvalidatePanelMenuPosted) && ((this.mInvalidatePanelMenuFeatures & 0x1) != 0))
        {
          this.mWindow.getDecorView().removeCallbacks(this.mInvalidatePanelMenuRunnable);
          this.mInvalidatePanelMenuRunnable.run();
        }
        localObject = getPanelState(0, true);
        if ((((PanelFeatureState)localObject).menu != null) && (!((PanelFeatureState)localObject).refreshMenuContent) && (localCallback.onPreparePanel(0, ((PanelFeatureState)localObject).createdPanelView, ((PanelFeatureState)localObject).menu)))
        {
          localCallback.onMenuOpened(108, ((PanelFeatureState)localObject).menu);
          this.mDecorContentParent.showOverflowMenu();
        }
      }
      return;
    }
    localObject = getPanelState(0, true);
    ((PanelFeatureState)localObject).refreshDecorView = true;
    closePanel((PanelFeatureState)localObject, false);
    openPanel((PanelFeatureState)localObject, null);
  }
  
  private int sanitizeWindowFeatureId(int paramInt)
  {
    if (paramInt == 8)
    {
      Log.i("AppCompatDelegate", "You should now use the AppCompatDelegate.FEATURE_SUPPORT_ACTION_BAR id when requesting this feature.");
      return 108;
    }
    if (paramInt == 9)
    {
      Log.i("AppCompatDelegate", "You should now use the AppCompatDelegate.FEATURE_SUPPORT_ACTION_BAR_OVERLAY id when requesting this feature.");
      return 109;
    }
    return paramInt;
  }
  
  private boolean shouldInheritContext(ViewParent paramViewParent)
  {
    if (paramViewParent == null) {
      return false;
    }
    View localView = this.mWindow.getDecorView();
    for (;;)
    {
      if (paramViewParent == null) {
        return true;
      }
      if ((paramViewParent == localView) || (!(paramViewParent instanceof View)) || (ViewCompat.isAttachedToWindow((View)paramViewParent))) {
        break;
      }
      paramViewParent = paramViewParent.getParent();
    }
    return false;
  }
  
  private void throwFeatureRequestIfSubDecorInstalled()
  {
    if (!this.mSubDecorInstalled) {
      return;
    }
    throw new AndroidRuntimeException("Window feature must be requested before adding content");
  }
  
  private AppCompatActivity tryUnwrapContext()
  {
    Context localContext = this.mContext;
    while (localContext != null)
    {
      if ((localContext instanceof AppCompatActivity)) {
        return (AppCompatActivity)localContext;
      }
      if ((localContext instanceof ContextWrapper)) {
        localContext = ((ContextWrapper)localContext).getBaseContext();
      } else {
        return null;
      }
    }
    return null;
  }
  
  private boolean updateForNightMode(int paramInt, boolean paramBoolean)
  {
    boolean bool2 = false;
    Configuration localConfiguration = createOverrideConfigurationForDayNight(this.mContext, paramInt, null, false);
    boolean bool3 = isActivityManifestHandlingUiMode(this.mContext);
    Object localObject = this.mEffectiveConfiguration;
    if (localObject == null) {
      localObject = this.mContext.getResources().getConfiguration();
    }
    int i = ((Configuration)localObject).uiMode & 0x30;
    int j = localConfiguration.uiMode & 0x30;
    boolean bool1 = bool2;
    if (i != j)
    {
      bool1 = bool2;
      if (paramBoolean)
      {
        bool1 = bool2;
        if (!bool3)
        {
          bool1 = bool2;
          if (this.mBaseContextAttached) {
            if (!sCanReturnDifferentContext)
            {
              bool1 = bool2;
              if (!this.mCreated) {}
            }
            else
            {
              localObject = this.mHost;
              bool1 = bool2;
              if ((localObject instanceof Activity))
              {
                bool1 = bool2;
                if (!((Activity)localObject).isChild())
                {
                  ActivityCompat.recreate((Activity)this.mHost);
                  bool1 = true;
                }
              }
            }
          }
        }
      }
    }
    paramBoolean = bool1;
    if (!bool1)
    {
      paramBoolean = bool1;
      if (i != j)
      {
        updateResourcesConfigurationForNightMode(j, bool3, null);
        paramBoolean = true;
      }
    }
    if (paramBoolean)
    {
      localObject = this.mHost;
      if ((localObject instanceof AppCompatActivity)) {
        ((AppCompatActivity)localObject).onNightModeChanged(paramInt);
      }
    }
    return paramBoolean;
  }
  
  private void updateResourcesConfigurationForNightMode(int paramInt, boolean paramBoolean, Configuration paramConfiguration)
  {
    Resources localResources = this.mContext.getResources();
    Configuration localConfiguration = new Configuration(localResources.getConfiguration());
    if (paramConfiguration != null) {
      localConfiguration.updateFrom(paramConfiguration);
    }
    localConfiguration.uiMode = (localResources.getConfiguration().uiMode & 0xFFFFFFCF | paramInt);
    localResources.updateConfiguration(localConfiguration, null);
    if (Build.VERSION.SDK_INT < 26) {
      ResourcesFlusher.flush(localResources);
    }
    paramInt = this.mThemeResId;
    if (paramInt != 0)
    {
      this.mContext.setTheme(paramInt);
      if (Build.VERSION.SDK_INT >= 23) {
        this.mContext.getTheme().applyStyle(this.mThemeResId, true);
      }
    }
    if (paramBoolean)
    {
      paramConfiguration = this.mHost;
      if ((paramConfiguration instanceof Activity))
      {
        paramConfiguration = (Activity)paramConfiguration;
        if ((paramConfiguration instanceof LifecycleOwner))
        {
          if (((LifecycleOwner)paramConfiguration).getLifecycle().getCurrentState().isAtLeast(Lifecycle.State.CREATED)) {
            paramConfiguration.onConfigurationChanged(localConfiguration);
          }
        }
        else if ((this.mCreated) && (!this.mDestroyed)) {
          paramConfiguration.onConfigurationChanged(localConfiguration);
        }
      }
    }
  }
  
  private void updateStatusGuardColor(View paramView)
  {
    int i;
    if ((ViewCompat.getWindowSystemUiVisibility(paramView) & 0x2000) != 0) {
      i = 1;
    } else {
      i = 0;
    }
    if (i != 0) {
      i = ContextCompat.getColor(this.mContext, R.color.abc_decor_view_status_guard_light);
    } else {
      i = ContextCompat.getColor(this.mContext, R.color.abc_decor_view_status_guard);
    }
    paramView.setBackgroundColor(i);
  }
  
  public void addContentView(View paramView, ViewGroup.LayoutParams paramLayoutParams)
  {
    ensureSubDecor();
    ((ViewGroup)this.mSubDecor.findViewById(16908290)).addView(paramView, paramLayoutParams);
    this.mAppCompatWindowCallback.bypassOnContentChanged(this.mWindow.getCallback());
  }
  
  public boolean applyDayNight()
  {
    return applyDayNight(true);
  }
  
  public Context attachBaseContext2(Context paramContext)
  {
    int i = 1;
    this.mBaseContextAttached = true;
    int j = mapNightMode(paramContext, calculateNightMode());
    if ((sCanApplyOverrideConfiguration) && ((paramContext instanceof android.view.ContextThemeWrapper)))
    {
      Configuration localConfiguration1 = createOverrideConfigurationForDayNight(paramContext, j, null, false);
      try
      {
        ContextThemeWrapperCompatApi17Impl.applyOverrideConfiguration((android.view.ContextThemeWrapper)paramContext, localConfiguration1);
        return paramContext;
      }
      catch (IllegalStateException localIllegalStateException1) {}
    }
    if ((paramContext instanceof androidx.appcompat.view.ContextThemeWrapper))
    {
      Configuration localConfiguration2 = createOverrideConfigurationForDayNight(paramContext, j, null, false);
      try
      {
        ((androidx.appcompat.view.ContextThemeWrapper)paramContext).applyOverrideConfiguration(localConfiguration2);
        return paramContext;
      }
      catch (IllegalStateException localIllegalStateException2) {}
    }
    if (!sCanReturnDifferentContext) {
      return super.attachBaseContext2(paramContext);
    }
    Configuration localConfiguration3 = null;
    Object localObject = localConfiguration3;
    if (Build.VERSION.SDK_INT >= 17)
    {
      localObject = new Configuration();
      ((Configuration)localObject).uiMode = -1;
      ((Configuration)localObject).fontScale = 0.0F;
      Configuration localConfiguration5 = Api17Impl.createConfigurationContext(paramContext, (Configuration)localObject).getResources().getConfiguration();
      Configuration localConfiguration4 = paramContext.getResources().getConfiguration();
      localConfiguration5.uiMode = localConfiguration4.uiMode;
      localObject = localConfiguration3;
      if (!localConfiguration5.equals(localConfiguration4)) {
        localObject = generateConfigDelta(localConfiguration5, localConfiguration4);
      }
    }
    localConfiguration3 = createOverrideConfigurationForDayNight(paramContext, j, (Configuration)localObject, true);
    localObject = new androidx.appcompat.view.ContextThemeWrapper(paramContext, R.style.Theme_AppCompat_Empty);
    ((androidx.appcompat.view.ContextThemeWrapper)localObject).applyOverrideConfiguration(localConfiguration3);
    try
    {
      paramContext = paramContext.getTheme();
      if (paramContext == null) {
        i = 0;
      }
    }
    catch (NullPointerException paramContext)
    {
      i = 0;
    }
    if (i != 0) {
      ResourcesCompat.ThemeCompat.rebase(((androidx.appcompat.view.ContextThemeWrapper)localObject).getTheme());
    }
    return super.attachBaseContext2((Context)localObject);
  }
  
  void callOnPanelClosed(int paramInt, PanelFeatureState paramPanelFeatureState, Menu paramMenu)
  {
    Object localObject2 = paramPanelFeatureState;
    Object localObject1 = paramMenu;
    if (paramMenu == null)
    {
      PanelFeatureState localPanelFeatureState = paramPanelFeatureState;
      if (paramPanelFeatureState == null)
      {
        localPanelFeatureState = paramPanelFeatureState;
        if (paramInt >= 0)
        {
          localObject1 = this.mPanels;
          localPanelFeatureState = paramPanelFeatureState;
          if (paramInt < localObject1.length) {
            localPanelFeatureState = localObject1[paramInt];
          }
        }
      }
      localObject2 = localPanelFeatureState;
      localObject1 = paramMenu;
      if (localPanelFeatureState != null)
      {
        localObject1 = localPanelFeatureState.menu;
        localObject2 = localPanelFeatureState;
      }
    }
    if ((localObject2 != null) && (!((PanelFeatureState)localObject2).isOpen)) {
      return;
    }
    if (!this.mDestroyed) {
      this.mAppCompatWindowCallback.bypassOnPanelClosed(this.mWindow.getCallback(), paramInt, (Menu)localObject1);
    }
  }
  
  void checkCloseActionMenu(MenuBuilder paramMenuBuilder)
  {
    if (this.mClosingActionMenu) {
      return;
    }
    this.mClosingActionMenu = true;
    this.mDecorContentParent.dismissPopups();
    Window.Callback localCallback = getWindowCallback();
    if ((localCallback != null) && (!this.mDestroyed)) {
      localCallback.onPanelClosed(108, paramMenuBuilder);
    }
    this.mClosingActionMenu = false;
  }
  
  void closePanel(int paramInt)
  {
    closePanel(getPanelState(paramInt, true), true);
  }
  
  void closePanel(PanelFeatureState paramPanelFeatureState, boolean paramBoolean)
  {
    if ((paramBoolean) && (paramPanelFeatureState.featureId == 0))
    {
      localObject = this.mDecorContentParent;
      if ((localObject != null) && (((DecorContentParent)localObject).isOverflowMenuShowing()))
      {
        checkCloseActionMenu(paramPanelFeatureState.menu);
        return;
      }
    }
    Object localObject = (WindowManager)this.mContext.getSystemService("window");
    if ((localObject != null) && (paramPanelFeatureState.isOpen) && (paramPanelFeatureState.decorView != null))
    {
      ((WindowManager)localObject).removeView(paramPanelFeatureState.decorView);
      if (paramBoolean) {
        callOnPanelClosed(paramPanelFeatureState.featureId, paramPanelFeatureState, null);
      }
    }
    paramPanelFeatureState.isPrepared = false;
    paramPanelFeatureState.isHandled = false;
    paramPanelFeatureState.isOpen = false;
    paramPanelFeatureState.shownPanelView = null;
    paramPanelFeatureState.refreshDecorView = true;
    if (this.mPreparedPanel == paramPanelFeatureState) {
      this.mPreparedPanel = null;
    }
  }
  
  /* Error */
  public View createView(View paramView, String paramString, Context paramContext, AttributeSet paramAttributeSet)
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 1371	androidx/appcompat/app/AppCompatDelegateImpl:mAppCompatViewInflater	Landroidx/appcompat/app/AppCompatViewInflater;
    //   4: astore 8
    //   6: iconst_0
    //   7: istore 6
    //   9: aload 8
    //   11: ifnonnull +125 -> 136
    //   14: aload_0
    //   15: getfield 254	androidx/appcompat/app/AppCompatDelegateImpl:mContext	Landroid/content/Context;
    //   18: getstatic 380	androidx/appcompat/R$styleable:AppCompatTheme	[I
    //   21: invokevirtual 386	android/content/Context:obtainStyledAttributes	([I)Landroid/content/res/TypedArray;
    //   24: getstatic 1374	androidx/appcompat/R$styleable:AppCompatTheme_viewInflaterClass	I
    //   27: invokevirtual 1378	android/content/res/TypedArray:getString	(I)Ljava/lang/String;
    //   30: astore 9
    //   32: aload 9
    //   34: ifnonnull +17 -> 51
    //   37: aload_0
    //   38: new 1380	androidx/appcompat/app/AppCompatViewInflater
    //   41: dup
    //   42: invokespecial 1381	androidx/appcompat/app/AppCompatViewInflater:<init>	()V
    //   45: putfield 1371	androidx/appcompat/app/AppCompatDelegateImpl:mAppCompatViewInflater	Landroidx/appcompat/app/AppCompatViewInflater;
    //   48: goto +88 -> 136
    //   51: aload_0
    //   52: aload_0
    //   53: getfield 254	androidx/appcompat/app/AppCompatDelegateImpl:mContext	Landroid/content/Context;
    //   56: invokevirtual 1385	android/content/Context:getClassLoader	()Ljava/lang/ClassLoader;
    //   59: aload 9
    //   61: invokevirtual 1391	java/lang/ClassLoader:loadClass	(Ljava/lang/String;)Ljava/lang/Class;
    //   64: iconst_0
    //   65: anewarray 280	java/lang/Class
    //   68: invokevirtual 1395	java/lang/Class:getDeclaredConstructor	([Ljava/lang/Class;)Ljava/lang/reflect/Constructor;
    //   71: iconst_0
    //   72: anewarray 274	java/lang/Object
    //   75: invokevirtual 1401	java/lang/reflect/Constructor:newInstance	([Ljava/lang/Object;)Ljava/lang/Object;
    //   78: checkcast 1380	androidx/appcompat/app/AppCompatViewInflater
    //   81: putfield 1371	androidx/appcompat/app/AppCompatDelegateImpl:mAppCompatViewInflater	Landroidx/appcompat/app/AppCompatViewInflater;
    //   84: goto +52 -> 136
    //   87: astore 8
    //   89: ldc_w 949
    //   92: new 679	java/lang/StringBuilder
    //   95: dup
    //   96: invokespecial 680	java/lang/StringBuilder:<init>	()V
    //   99: ldc_w 1403
    //   102: invokevirtual 686	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   105: aload 9
    //   107: invokevirtual 686	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   110: ldc_w 1405
    //   113: invokevirtual 686	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   116: invokevirtual 702	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   119: aload 8
    //   121: invokestatic 1407	android/util/Log:i	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I
    //   124: pop
    //   125: aload_0
    //   126: new 1380	androidx/appcompat/app/AppCompatViewInflater
    //   129: dup
    //   130: invokespecial 1381	androidx/appcompat/app/AppCompatViewInflater:<init>	()V
    //   133: putfield 1371	androidx/appcompat/app/AppCompatDelegateImpl:mAppCompatViewInflater	Landroidx/appcompat/app/AppCompatViewInflater;
    //   136: iconst_0
    //   137: istore 5
    //   139: getstatic 188	androidx/appcompat/app/AppCompatDelegateImpl:IS_PRE_LOLLIPOP	Z
    //   142: istore 7
    //   144: iload 7
    //   146: ifeq +81 -> 227
    //   149: aload_0
    //   150: getfield 1409	androidx/appcompat/app/AppCompatDelegateImpl:mLayoutIncludeDetector	Landroidx/appcompat/app/LayoutIncludeDetector;
    //   153: ifnonnull +14 -> 167
    //   156: aload_0
    //   157: new 1411	androidx/appcompat/app/LayoutIncludeDetector
    //   160: dup
    //   161: invokespecial 1412	androidx/appcompat/app/LayoutIncludeDetector:<init>	()V
    //   164: putfield 1409	androidx/appcompat/app/AppCompatDelegateImpl:mLayoutIncludeDetector	Landroidx/appcompat/app/LayoutIncludeDetector;
    //   167: aload_0
    //   168: getfield 1409	androidx/appcompat/app/AppCompatDelegateImpl:mLayoutIncludeDetector	Landroidx/appcompat/app/LayoutIncludeDetector;
    //   171: aload 4
    //   173: invokevirtual 1416	androidx/appcompat/app/LayoutIncludeDetector:detect	(Landroid/util/AttributeSet;)Z
    //   176: ifeq +9 -> 185
    //   179: iconst_1
    //   180: istore 5
    //   182: goto +45 -> 227
    //   185: aload 4
    //   187: instanceof 1418
    //   190: ifeq +27 -> 217
    //   193: iload 6
    //   195: istore 5
    //   197: aload 4
    //   199: checkcast 1418	org/xmlpull/v1/XmlPullParser
    //   202: invokeinterface 1421 1 0
    //   207: iconst_1
    //   208: if_icmple +19 -> 227
    //   211: iconst_1
    //   212: istore 5
    //   214: goto +13 -> 227
    //   217: aload_0
    //   218: aload_1
    //   219: checkcast 1204	android/view/ViewParent
    //   222: invokespecial 1423	androidx/appcompat/app/AppCompatDelegateImpl:shouldInheritContext	(Landroid/view/ViewParent;)Z
    //   225: istore 5
    //   227: aload_0
    //   228: getfield 1371	androidx/appcompat/app/AppCompatDelegateImpl:mAppCompatViewInflater	Landroidx/appcompat/app/AppCompatViewInflater;
    //   231: aload_1
    //   232: aload_2
    //   233: aload_3
    //   234: aload 4
    //   236: iload 5
    //   238: iload 7
    //   240: iconst_1
    //   241: invokestatic 1428	androidx/appcompat/widget/VectorEnabledTintResources:shouldBeUsed	()Z
    //   244: invokevirtual 1431	androidx/appcompat/app/AppCompatViewInflater:createView	(Landroid/view/View;Ljava/lang/String;Landroid/content/Context;Landroid/util/AttributeSet;ZZZZ)Landroid/view/View;
    //   247: areturn
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	248	0	this	AppCompatDelegateImpl
    //   0	248	1	paramView	View
    //   0	248	2	paramString	String
    //   0	248	3	paramContext	Context
    //   0	248	4	paramAttributeSet	AttributeSet
    //   137	100	5	bool1	boolean
    //   7	187	6	bool2	boolean
    //   142	97	7	bool3	boolean
    //   4	6	8	localAppCompatViewInflater	AppCompatViewInflater
    //   87	33	8	localThrowable	Throwable
    //   30	76	9	str	String
    // Exception table:
    //   from	to	target	type
    //   51	84	87	finally
  }
  
  void dismissPopups()
  {
    DecorContentParent localDecorContentParent = this.mDecorContentParent;
    if (localDecorContentParent != null) {
      localDecorContentParent.dismissPopups();
    }
    if (this.mActionModePopup != null)
    {
      this.mWindow.getDecorView().removeCallbacks(this.mShowActionModePopup);
      if (this.mActionModePopup.isShowing()) {
        try
        {
          this.mActionModePopup.dismiss();
        }
        catch (IllegalArgumentException localIllegalArgumentException) {}
      }
      this.mActionModePopup = null;
    }
    endOnGoingFadeAnimation();
    PanelFeatureState localPanelFeatureState = getPanelState(0, false);
    if ((localPanelFeatureState != null) && (localPanelFeatureState.menu != null)) {
      localPanelFeatureState.menu.close();
    }
  }
  
  boolean dispatchKeyEvent(KeyEvent paramKeyEvent)
  {
    Object localObject = this.mHost;
    boolean bool = localObject instanceof KeyEventDispatcher.Component;
    int i = 1;
    if ((bool) || ((localObject instanceof AppCompatDialog)))
    {
      localObject = this.mWindow.getDecorView();
      if ((localObject != null) && (KeyEventDispatcher.dispatchBeforeHierarchy((View)localObject, paramKeyEvent))) {
        return true;
      }
    }
    if ((paramKeyEvent.getKeyCode() == 82) && (this.mAppCompatWindowCallback.bypassDispatchKeyEvent(this.mWindow.getCallback(), paramKeyEvent))) {
      return true;
    }
    int j = paramKeyEvent.getKeyCode();
    if (paramKeyEvent.getAction() != 0) {
      i = 0;
    }
    if (i != 0) {
      bool = onKeyDown(j, paramKeyEvent);
    } else {
      bool = onKeyUp(j, paramKeyEvent);
    }
    return bool;
  }
  
  void doInvalidatePanelMenu(int paramInt)
  {
    PanelFeatureState localPanelFeatureState = getPanelState(paramInt, true);
    if (localPanelFeatureState.menu != null)
    {
      Bundle localBundle = new Bundle();
      localPanelFeatureState.menu.saveActionViewStates(localBundle);
      if (localBundle.size() > 0) {
        localPanelFeatureState.frozenActionViewState = localBundle;
      }
      localPanelFeatureState.menu.stopDispatchingItemsChanged();
      localPanelFeatureState.menu.clear();
    }
    localPanelFeatureState.refreshMenuContent = true;
    localPanelFeatureState.refreshDecorView = true;
    if (((paramInt == 108) || (paramInt == 0)) && (this.mDecorContentParent != null))
    {
      localPanelFeatureState = getPanelState(0, false);
      if (localPanelFeatureState != null)
      {
        localPanelFeatureState.isPrepared = false;
        preparePanel(localPanelFeatureState, null);
      }
    }
  }
  
  void endOnGoingFadeAnimation()
  {
    ViewPropertyAnimatorCompat localViewPropertyAnimatorCompat = this.mFadeAnim;
    if (localViewPropertyAnimatorCompat != null) {
      localViewPropertyAnimatorCompat.cancel();
    }
  }
  
  PanelFeatureState findMenuPanel(Menu paramMenu)
  {
    PanelFeatureState[] arrayOfPanelFeatureState = this.mPanels;
    int i;
    if (arrayOfPanelFeatureState != null) {
      i = arrayOfPanelFeatureState.length;
    } else {
      i = 0;
    }
    for (int j = 0; j < i; j++)
    {
      PanelFeatureState localPanelFeatureState = arrayOfPanelFeatureState[j];
      if ((localPanelFeatureState != null) && (localPanelFeatureState.menu == paramMenu)) {
        return localPanelFeatureState;
      }
    }
    return null;
  }
  
  public <T extends View> T findViewById(int paramInt)
  {
    ensureSubDecor();
    return this.mWindow.findViewById(paramInt);
  }
  
  final Context getActionBarThemedContext()
  {
    Context localContext = null;
    Object localObject = getSupportActionBar();
    if (localObject != null) {
      localContext = ((ActionBar)localObject).getThemedContext();
    }
    localObject = localContext;
    if (localContext == null) {
      localObject = this.mContext;
    }
    return (Context)localObject;
  }
  
  final AutoNightModeManager getAutoTimeNightModeManager()
  {
    return getAutoTimeNightModeManager(this.mContext);
  }
  
  public final ActionBarDrawerToggle.Delegate getDrawerToggleDelegate()
  {
    return new ActionBarDrawableToggleImpl();
  }
  
  public int getLocalNightMode()
  {
    return this.mLocalNightMode;
  }
  
  public MenuInflater getMenuInflater()
  {
    if (this.mMenuInflater == null)
    {
      initWindowDecorActionBar();
      Object localObject = this.mActionBar;
      if (localObject != null) {
        localObject = ((ActionBar)localObject).getThemedContext();
      } else {
        localObject = this.mContext;
      }
      this.mMenuInflater = new SupportMenuInflater((Context)localObject);
    }
    return this.mMenuInflater;
  }
  
  protected PanelFeatureState getPanelState(int paramInt, boolean paramBoolean)
  {
    Object localObject1 = this.mPanels;
    Object localObject2 = localObject1;
    if (localObject1 != null)
    {
      localObject1 = localObject2;
      if (localObject2.length > paramInt) {}
    }
    else
    {
      localObject3 = new PanelFeatureState[paramInt + 1];
      if (localObject2 != null) {
        System.arraycopy(localObject2, 0, localObject3, 0, localObject2.length);
      }
      localObject1 = localObject3;
      this.mPanels = ((PanelFeatureState[])localObject3);
    }
    Object localObject3 = localObject1[paramInt];
    localObject2 = localObject3;
    if (localObject3 == null)
    {
      localObject3 = new PanelFeatureState(paramInt);
      localObject2 = localObject3;
      localObject1[paramInt] = localObject3;
    }
    return (PanelFeatureState)localObject2;
  }
  
  ViewGroup getSubDecor()
  {
    return this.mSubDecor;
  }
  
  public ActionBar getSupportActionBar()
  {
    initWindowDecorActionBar();
    return this.mActionBar;
  }
  
  final CharSequence getTitle()
  {
    Object localObject = this.mHost;
    if ((localObject instanceof Activity)) {
      return ((Activity)localObject).getTitle();
    }
    return this.mTitle;
  }
  
  final Window.Callback getWindowCallback()
  {
    return this.mWindow.getCallback();
  }
  
  public boolean hasWindowFeature(int paramInt)
  {
    boolean bool = false;
    switch (sanitizeWindowFeatureId(paramInt))
    {
    default: 
      break;
    case 109: 
      bool = this.mOverlayActionBar;
      break;
    case 108: 
      bool = this.mHasActionBar;
      break;
    case 10: 
      bool = this.mOverlayActionMode;
      break;
    case 5: 
      bool = this.mFeatureIndeterminateProgress;
      break;
    case 2: 
      bool = this.mFeatureProgress;
      break;
    case 1: 
      bool = this.mWindowNoTitle;
    }
    if ((!bool) && (!this.mWindow.hasFeature(paramInt))) {
      bool = false;
    } else {
      bool = true;
    }
    return bool;
  }
  
  public void installViewFactory()
  {
    LayoutInflater localLayoutInflater = LayoutInflater.from(this.mContext);
    if (localLayoutInflater.getFactory() == null) {
      LayoutInflaterCompat.setFactory2(localLayoutInflater, this);
    } else if (!(localLayoutInflater.getFactory2() instanceof AppCompatDelegateImpl)) {
      Log.i("AppCompatDelegate", "The Activity's LayoutInflater already has a Factory installed so we can not install AppCompat's");
    }
  }
  
  public void invalidateOptionsMenu()
  {
    if ((peekSupportActionBar() != null) && (!getSupportActionBar().invalidateOptionsMenu()))
    {
      invalidatePanelMenu(0);
      return;
    }
  }
  
  public boolean isHandleNativeActionModesEnabled()
  {
    return this.mHandleNativeActionModes;
  }
  
  int mapNightMode(Context paramContext, int paramInt)
  {
    switch (paramInt)
    {
    default: 
      throw new IllegalStateException("Unknown value set for night mode. Please use one of the MODE_NIGHT values from AppCompatDelegate.");
    case 3: 
      return getAutoBatteryNightModeManager(paramContext).getApplyableNightMode();
    case 0: 
      if ((Build.VERSION.SDK_INT >= 23) && (((UiModeManager)paramContext.getApplicationContext().getSystemService("uimode")).getNightMode() == 0)) {
        return -1;
      }
      return getAutoTimeNightModeManager(paramContext).getApplyableNightMode();
    case -1: 
    case 1: 
    case 2: 
      return paramInt;
    }
    return -1;
  }
  
  boolean onBackPressed()
  {
    Object localObject = this.mActionMode;
    if (localObject != null)
    {
      ((androidx.appcompat.view.ActionMode)localObject).finish();
      return true;
    }
    localObject = getSupportActionBar();
    return (localObject != null) && (((ActionBar)localObject).collapseActionView());
  }
  
  public void onConfigurationChanged(Configuration paramConfiguration)
  {
    if ((this.mHasActionBar) && (this.mSubDecorInstalled))
    {
      ActionBar localActionBar = getSupportActionBar();
      if (localActionBar != null) {
        localActionBar.onConfigurationChanged(paramConfiguration);
      }
    }
    AppCompatDrawableManager.get().onConfigurationChanged(this.mContext);
    this.mEffectiveConfiguration = new Configuration(this.mContext.getResources().getConfiguration());
    applyDayNight(false);
    paramConfiguration.updateFrom(this.mContext.getResources().getConfiguration());
  }
  
  public void onCreate(Bundle paramBundle)
  {
    this.mBaseContextAttached = true;
    applyDayNight(false);
    ensureWindow();
    Object localObject = this.mHost;
    if ((localObject instanceof Activity))
    {
      paramBundle = null;
      try
      {
        localObject = NavUtils.getParentActivityName((Activity)localObject);
        Log5ECF72.a(localObject);
        LogE84000.a(localObject);
        Log229316.a(localObject);
        paramBundle = (Bundle)localObject;
      }
      catch (IllegalArgumentException localIllegalArgumentException) {}
      if (paramBundle != null)
      {
        paramBundle = peekSupportActionBar();
        if (paramBundle == null) {
          this.mEnableDefaultActionBarUp = true;
        } else {
          paramBundle.setDefaultDisplayHomeAsUpEnabled(true);
        }
      }
      addActiveDelegate(this);
    }
    this.mEffectiveConfiguration = new Configuration(this.mContext.getResources().getConfiguration());
    this.mCreated = true;
  }
  
  public final View onCreateView(View paramView, String paramString, Context paramContext, AttributeSet paramAttributeSet)
  {
    return createView(paramView, paramString, paramContext, paramAttributeSet);
  }
  
  public View onCreateView(String paramString, Context paramContext, AttributeSet paramAttributeSet)
  {
    return onCreateView(null, paramString, paramContext, paramAttributeSet);
  }
  
  public void onDestroy()
  {
    if ((this.mHost instanceof Activity)) {
      removeActivityDelegate(this);
    }
    if (this.mInvalidatePanelMenuPosted) {
      this.mWindow.getDecorView().removeCallbacks(this.mInvalidatePanelMenuRunnable);
    }
    this.mDestroyed = true;
    if (this.mLocalNightMode != -100)
    {
      localObject = this.mHost;
      if (((localObject instanceof Activity)) && (((Activity)localObject).isChangingConfigurations()))
      {
        sLocalNightModes.put(this.mHost.getClass().getName(), Integer.valueOf(this.mLocalNightMode));
        break label116;
      }
    }
    sLocalNightModes.remove(this.mHost.getClass().getName());
    label116:
    Object localObject = this.mActionBar;
    if (localObject != null) {
      ((ActionBar)localObject).onDestroy();
    }
    cleanupAutoManagers();
  }
  
  boolean onKeyDown(int paramInt, KeyEvent paramKeyEvent)
  {
    boolean bool = true;
    switch (paramInt)
    {
    default: 
      break;
    case 82: 
      onKeyDownPanel(0, paramKeyEvent);
      return true;
    case 4: 
      if ((paramKeyEvent.getFlags() & 0x80) == 0) {
        bool = false;
      }
      this.mLongPressBackDown = bool;
    }
    return false;
  }
  
  boolean onKeyShortcut(int paramInt, KeyEvent paramKeyEvent)
  {
    Object localObject = getSupportActionBar();
    if ((localObject != null) && (((ActionBar)localObject).onKeyShortcut(paramInt, paramKeyEvent))) {
      return true;
    }
    localObject = this.mPreparedPanel;
    if ((localObject != null) && (performPanelShortcut((PanelFeatureState)localObject, paramKeyEvent.getKeyCode(), paramKeyEvent, 1)))
    {
      paramKeyEvent = this.mPreparedPanel;
      if (paramKeyEvent != null) {
        paramKeyEvent.isHandled = true;
      }
      return true;
    }
    if (this.mPreparedPanel == null)
    {
      localObject = getPanelState(0, true);
      preparePanel((PanelFeatureState)localObject, paramKeyEvent);
      boolean bool = performPanelShortcut((PanelFeatureState)localObject, paramKeyEvent.getKeyCode(), paramKeyEvent, 1);
      ((PanelFeatureState)localObject).isPrepared = false;
      if (bool) {
        return true;
      }
    }
    return false;
  }
  
  boolean onKeyUp(int paramInt, KeyEvent paramKeyEvent)
  {
    switch (paramInt)
    {
    default: 
      break;
    case 82: 
      onKeyUpPanel(0, paramKeyEvent);
      return true;
    case 4: 
      boolean bool = this.mLongPressBackDown;
      this.mLongPressBackDown = false;
      paramKeyEvent = getPanelState(0, false);
      if ((paramKeyEvent != null) && (paramKeyEvent.isOpen))
      {
        if (!bool) {
          closePanel(paramKeyEvent, true);
        }
        return true;
      }
      if (onBackPressed()) {
        return true;
      }
      break;
    }
    return false;
  }
  
  public boolean onMenuItemSelected(MenuBuilder paramMenuBuilder, MenuItem paramMenuItem)
  {
    Window.Callback localCallback = getWindowCallback();
    if ((localCallback != null) && (!this.mDestroyed))
    {
      paramMenuBuilder = findMenuPanel(paramMenuBuilder.getRootMenu());
      if (paramMenuBuilder != null) {
        return localCallback.onMenuItemSelected(paramMenuBuilder.featureId, paramMenuItem);
      }
    }
    return false;
  }
  
  public void onMenuModeChange(MenuBuilder paramMenuBuilder)
  {
    reopenMenu(true);
  }
  
  void onMenuOpened(int paramInt)
  {
    if (paramInt == 108)
    {
      ActionBar localActionBar = getSupportActionBar();
      if (localActionBar != null) {
        localActionBar.dispatchMenuVisibilityChanged(true);
      }
    }
  }
  
  void onPanelClosed(int paramInt)
  {
    Object localObject;
    if (paramInt == 108)
    {
      localObject = getSupportActionBar();
      if (localObject != null) {
        ((ActionBar)localObject).dispatchMenuVisibilityChanged(false);
      }
    }
    else if (paramInt == 0)
    {
      localObject = getPanelState(paramInt, true);
      if (((PanelFeatureState)localObject).isOpen) {
        closePanel((PanelFeatureState)localObject, false);
      }
    }
  }
  
  public void onPostCreate(Bundle paramBundle)
  {
    ensureSubDecor();
  }
  
  public void onPostResume()
  {
    ActionBar localActionBar = getSupportActionBar();
    if (localActionBar != null) {
      localActionBar.setShowHideAnimationEnabled(true);
    }
  }
  
  public void onSaveInstanceState(Bundle paramBundle) {}
  
  public void onStart()
  {
    applyDayNight();
  }
  
  public void onStop()
  {
    ActionBar localActionBar = getSupportActionBar();
    if (localActionBar != null) {
      localActionBar.setShowHideAnimationEnabled(false);
    }
  }
  
  void onSubDecorInstalled(ViewGroup paramViewGroup) {}
  
  final ActionBar peekSupportActionBar()
  {
    return this.mActionBar;
  }
  
  public boolean requestWindowFeature(int paramInt)
  {
    paramInt = sanitizeWindowFeatureId(paramInt);
    if ((this.mWindowNoTitle) && (paramInt == 108)) {
      return false;
    }
    if ((this.mHasActionBar) && (paramInt == 1)) {
      this.mHasActionBar = false;
    }
    switch (paramInt)
    {
    default: 
      return this.mWindow.requestFeature(paramInt);
    case 109: 
      throwFeatureRequestIfSubDecorInstalled();
      this.mOverlayActionBar = true;
      return true;
    case 108: 
      throwFeatureRequestIfSubDecorInstalled();
      this.mHasActionBar = true;
      return true;
    case 10: 
      throwFeatureRequestIfSubDecorInstalled();
      this.mOverlayActionMode = true;
      return true;
    case 5: 
      throwFeatureRequestIfSubDecorInstalled();
      this.mFeatureIndeterminateProgress = true;
      return true;
    case 2: 
      throwFeatureRequestIfSubDecorInstalled();
      this.mFeatureProgress = true;
      return true;
    }
    throwFeatureRequestIfSubDecorInstalled();
    this.mWindowNoTitle = true;
    return true;
  }
  
  public void setContentView(int paramInt)
  {
    ensureSubDecor();
    ViewGroup localViewGroup = (ViewGroup)this.mSubDecor.findViewById(16908290);
    localViewGroup.removeAllViews();
    LayoutInflater.from(this.mContext).inflate(paramInt, localViewGroup);
    this.mAppCompatWindowCallback.bypassOnContentChanged(this.mWindow.getCallback());
  }
  
  public void setContentView(View paramView)
  {
    ensureSubDecor();
    ViewGroup localViewGroup = (ViewGroup)this.mSubDecor.findViewById(16908290);
    localViewGroup.removeAllViews();
    localViewGroup.addView(paramView);
    this.mAppCompatWindowCallback.bypassOnContentChanged(this.mWindow.getCallback());
  }
  
  public void setContentView(View paramView, ViewGroup.LayoutParams paramLayoutParams)
  {
    ensureSubDecor();
    ViewGroup localViewGroup = (ViewGroup)this.mSubDecor.findViewById(16908290);
    localViewGroup.removeAllViews();
    localViewGroup.addView(paramView, paramLayoutParams);
    this.mAppCompatWindowCallback.bypassOnContentChanged(this.mWindow.getCallback());
  }
  
  public void setHandleNativeActionModesEnabled(boolean paramBoolean)
  {
    this.mHandleNativeActionModes = paramBoolean;
  }
  
  public void setLocalNightMode(int paramInt)
  {
    if (this.mLocalNightMode != paramInt)
    {
      this.mLocalNightMode = paramInt;
      if (this.mBaseContextAttached) {
        applyDayNight();
      }
    }
  }
  
  public void setSupportActionBar(Toolbar paramToolbar)
  {
    if (!(this.mHost instanceof Activity)) {
      return;
    }
    ActionBar localActionBar = getSupportActionBar();
    if (!(localActionBar instanceof WindowDecorActionBar))
    {
      this.mMenuInflater = null;
      if (localActionBar != null) {
        localActionBar.onDestroy();
      }
      this.mActionBar = null;
      if (paramToolbar != null)
      {
        paramToolbar = new ToolbarActionBar(paramToolbar, getTitle(), this.mAppCompatWindowCallback);
        this.mActionBar = paramToolbar;
        this.mAppCompatWindowCallback.setActionBarCallback(paramToolbar.mMenuCallback);
      }
      else
      {
        this.mAppCompatWindowCallback.setActionBarCallback(null);
      }
      invalidateOptionsMenu();
      return;
    }
    throw new IllegalStateException("This Activity already has an action bar supplied by the window decor. Do not request Window.FEATURE_SUPPORT_ACTION_BAR and set windowActionBar to false in your theme to use a Toolbar instead.");
  }
  
  public void setTheme(int paramInt)
  {
    this.mThemeResId = paramInt;
  }
  
  public final void setTitle(CharSequence paramCharSequence)
  {
    this.mTitle = paramCharSequence;
    Object localObject = this.mDecorContentParent;
    if (localObject != null)
    {
      ((DecorContentParent)localObject).setWindowTitle(paramCharSequence);
    }
    else if (peekSupportActionBar() != null)
    {
      peekSupportActionBar().setWindowTitle(paramCharSequence);
    }
    else
    {
      localObject = this.mTitleView;
      if (localObject != null) {
        ((TextView)localObject).setText(paramCharSequence);
      }
    }
  }
  
  final boolean shouldAnimateActionModeView()
  {
    if (this.mSubDecorInstalled)
    {
      ViewGroup localViewGroup = this.mSubDecor;
      if ((localViewGroup != null) && (ViewCompat.isLaidOut(localViewGroup))) {
        return true;
      }
    }
    boolean bool = false;
    return bool;
  }
  
  public androidx.appcompat.view.ActionMode startSupportActionMode(androidx.appcompat.view.ActionMode.Callback paramCallback)
  {
    if (paramCallback != null)
    {
      Object localObject = this.mActionMode;
      if (localObject != null) {
        ((androidx.appcompat.view.ActionMode)localObject).finish();
      }
      paramCallback = new ActionModeCallbackWrapperV9(paramCallback);
      localObject = getSupportActionBar();
      if (localObject != null)
      {
        localObject = ((ActionBar)localObject).startActionMode(paramCallback);
        this.mActionMode = ((androidx.appcompat.view.ActionMode)localObject);
        if (localObject != null)
        {
          AppCompatCallback localAppCompatCallback = this.mAppCompatCallback;
          if (localAppCompatCallback != null) {
            localAppCompatCallback.onSupportActionModeStarted((androidx.appcompat.view.ActionMode)localObject);
          }
        }
      }
      if (this.mActionMode == null) {
        this.mActionMode = startSupportActionModeFromWindow(paramCallback);
      }
      return this.mActionMode;
    }
    throw new IllegalArgumentException("ActionMode callback can not be null.");
  }
  
  androidx.appcompat.view.ActionMode startSupportActionModeFromWindow(androidx.appcompat.view.ActionMode.Callback paramCallback)
  {
    endOnGoingFadeAnimation();
    Object localObject1 = this.mActionMode;
    if (localObject1 != null) {
      ((androidx.appcompat.view.ActionMode)localObject1).finish();
    }
    localObject1 = paramCallback;
    if (!(paramCallback instanceof ActionModeCallbackWrapperV9)) {
      localObject1 = new ActionModeCallbackWrapperV9(paramCallback);
    }
    Object localObject2 = null;
    Object localObject3 = this.mAppCompatCallback;
    paramCallback = (androidx.appcompat.view.ActionMode.Callback)localObject2;
    if (localObject3 != null)
    {
      paramCallback = (androidx.appcompat.view.ActionMode.Callback)localObject2;
      if (!this.mDestroyed) {
        try
        {
          paramCallback = ((AppCompatCallback)localObject3).onWindowStartingSupportActionMode((androidx.appcompat.view.ActionMode.Callback)localObject1);
        }
        catch (AbstractMethodError paramCallback)
        {
          paramCallback = (androidx.appcompat.view.ActionMode.Callback)localObject2;
        }
      }
    }
    if (paramCallback != null)
    {
      this.mActionMode = paramCallback;
    }
    else
    {
      paramCallback = this.mActionModeView;
      boolean bool = true;
      if (paramCallback == null) {
        if (this.mIsFloating)
        {
          localObject2 = new TypedValue();
          paramCallback = this.mContext.getTheme();
          paramCallback.resolveAttribute(R.attr.actionBarTheme, (TypedValue)localObject2, true);
          if (((TypedValue)localObject2).resourceId != 0)
          {
            localObject3 = this.mContext.getResources().newTheme();
            ((Resources.Theme)localObject3).setTo(paramCallback);
            ((Resources.Theme)localObject3).applyStyle(((TypedValue)localObject2).resourceId, true);
            paramCallback = new androidx.appcompat.view.ContextThemeWrapper(this.mContext, 0);
            paramCallback.getTheme().setTo((Resources.Theme)localObject3);
          }
          else
          {
            paramCallback = this.mContext;
          }
          this.mActionModeView = new ActionBarContextView(paramCallback);
          localObject3 = new PopupWindow(paramCallback, null, R.attr.actionModePopupWindowStyle);
          this.mActionModePopup = ((PopupWindow)localObject3);
          PopupWindowCompat.setWindowLayoutType((PopupWindow)localObject3, 2);
          this.mActionModePopup.setContentView(this.mActionModeView);
          this.mActionModePopup.setWidth(-1);
          paramCallback.getTheme().resolveAttribute(R.attr.actionBarSize, (TypedValue)localObject2, true);
          int i = TypedValue.complexToDimensionPixelSize(((TypedValue)localObject2).data, paramCallback.getResources().getDisplayMetrics());
          this.mActionModeView.setContentHeight(i);
          this.mActionModePopup.setHeight(-2);
          this.mShowActionModePopup = new Runnable()
          {
            public void run()
            {
              AppCompatDelegateImpl.this.mActionModePopup.showAtLocation(AppCompatDelegateImpl.this.mActionModeView, 55, 0, 0);
              AppCompatDelegateImpl.this.endOnGoingFadeAnimation();
              if (AppCompatDelegateImpl.this.shouldAnimateActionModeView())
              {
                AppCompatDelegateImpl.this.mActionModeView.setAlpha(0.0F);
                AppCompatDelegateImpl localAppCompatDelegateImpl = AppCompatDelegateImpl.this;
                localAppCompatDelegateImpl.mFadeAnim = ViewCompat.animate(localAppCompatDelegateImpl.mActionModeView).alpha(1.0F);
                AppCompatDelegateImpl.this.mFadeAnim.setListener(new ViewPropertyAnimatorListenerAdapter()
                {
                  public void onAnimationEnd(View paramAnonymous2View)
                  {
                    AppCompatDelegateImpl.this.mActionModeView.setAlpha(1.0F);
                    AppCompatDelegateImpl.this.mFadeAnim.setListener(null);
                    AppCompatDelegateImpl.this.mFadeAnim = null;
                  }
                  
                  public void onAnimationStart(View paramAnonymous2View)
                  {
                    AppCompatDelegateImpl.this.mActionModeView.setVisibility(0);
                  }
                });
              }
              else
              {
                AppCompatDelegateImpl.this.mActionModeView.setAlpha(1.0F);
                AppCompatDelegateImpl.this.mActionModeView.setVisibility(0);
              }
            }
          };
        }
        else
        {
          paramCallback = (ViewStubCompat)this.mSubDecor.findViewById(R.id.action_mode_bar_stub);
          if (paramCallback != null)
          {
            paramCallback.setLayoutInflater(LayoutInflater.from(getActionBarThemedContext()));
            this.mActionModeView = ((ActionBarContextView)paramCallback.inflate());
          }
        }
      }
      if (this.mActionModeView != null)
      {
        endOnGoingFadeAnimation();
        this.mActionModeView.killMode();
        localObject2 = this.mActionModeView.getContext();
        paramCallback = this.mActionModeView;
        if (this.mActionModePopup != null) {
          bool = false;
        }
        paramCallback = new StandaloneActionMode((Context)localObject2, paramCallback, (androidx.appcompat.view.ActionMode.Callback)localObject1, bool);
        if (((androidx.appcompat.view.ActionMode.Callback)localObject1).onCreateActionMode(paramCallback, paramCallback.getMenu()))
        {
          paramCallback.invalidate();
          this.mActionModeView.initForMode(paramCallback);
          this.mActionMode = paramCallback;
          if (shouldAnimateActionModeView())
          {
            this.mActionModeView.setAlpha(0.0F);
            paramCallback = ViewCompat.animate(this.mActionModeView).alpha(1.0F);
            this.mFadeAnim = paramCallback;
            paramCallback.setListener(new ViewPropertyAnimatorListenerAdapter()
            {
              public void onAnimationEnd(View paramAnonymousView)
              {
                AppCompatDelegateImpl.this.mActionModeView.setAlpha(1.0F);
                AppCompatDelegateImpl.this.mFadeAnim.setListener(null);
                AppCompatDelegateImpl.this.mFadeAnim = null;
              }
              
              public void onAnimationStart(View paramAnonymousView)
              {
                AppCompatDelegateImpl.this.mActionModeView.setVisibility(0);
                if ((AppCompatDelegateImpl.this.mActionModeView.getParent() instanceof View)) {
                  ViewCompat.requestApplyInsets((View)AppCompatDelegateImpl.this.mActionModeView.getParent());
                }
              }
            });
          }
          else
          {
            this.mActionModeView.setAlpha(1.0F);
            this.mActionModeView.setVisibility(0);
            if ((this.mActionModeView.getParent() instanceof View)) {
              ViewCompat.requestApplyInsets((View)this.mActionModeView.getParent());
            }
          }
          if (this.mActionModePopup != null) {
            this.mWindow.getDecorView().post(this.mShowActionModePopup);
          }
        }
        else
        {
          this.mActionMode = null;
        }
      }
    }
    localObject1 = this.mActionMode;
    if (localObject1 != null)
    {
      paramCallback = this.mAppCompatCallback;
      if (paramCallback != null) {
        paramCallback.onSupportActionModeStarted((androidx.appcompat.view.ActionMode)localObject1);
      }
    }
    return this.mActionMode;
  }
  
  final int updateStatusGuard(WindowInsetsCompat paramWindowInsetsCompat, Rect paramRect)
  {
    int i = 0;
    if (paramWindowInsetsCompat != null) {
      i = paramWindowInsetsCompat.getSystemWindowInsetTop();
    } else if (paramRect != null) {
      i = paramRect.top;
    }
    int n = 0;
    int k = 0;
    Object localObject = this.mActionModeView;
    if (localObject != null) {
      if ((((ActionBarContextView)localObject).getLayoutParams() instanceof ViewGroup.MarginLayoutParams))
      {
        localObject = (ViewGroup.MarginLayoutParams)this.mActionModeView.getLayoutParams();
        m = 0;
        int j = 0;
        if (this.mActionModeView.isShown())
        {
          if (this.mTempRect1 == null)
          {
            this.mTempRect1 = new Rect();
            this.mTempRect2 = new Rect();
          }
          Rect localRect1 = this.mTempRect1;
          Rect localRect2 = this.mTempRect2;
          if (paramWindowInsetsCompat == null) {
            localRect1.set(paramRect);
          } else {
            localRect1.set(paramWindowInsetsCompat.getSystemWindowInsetLeft(), paramWindowInsetsCompat.getSystemWindowInsetTop(), paramWindowInsetsCompat.getSystemWindowInsetRight(), paramWindowInsetsCompat.getSystemWindowInsetBottom());
          }
          ViewUtils.computeFitSystemWindows(this.mSubDecor, localRect1, localRect2);
          n = localRect1.top;
          int i1 = localRect1.left;
          int i2 = localRect1.right;
          paramWindowInsetsCompat = ViewCompat.getRootWindowInsets(this.mSubDecor);
          if (paramWindowInsetsCompat == null) {
            k = 0;
          } else {
            k = paramWindowInsetsCompat.getSystemWindowInsetLeft();
          }
          if (paramWindowInsetsCompat == null) {
            m = 0;
          } else {
            m = paramWindowInsetsCompat.getSystemWindowInsetRight();
          }
          if ((((ViewGroup.MarginLayoutParams)localObject).topMargin != n) || (((ViewGroup.MarginLayoutParams)localObject).leftMargin != i1) || (((ViewGroup.MarginLayoutParams)localObject).rightMargin != i2))
          {
            ((ViewGroup.MarginLayoutParams)localObject).topMargin = n;
            ((ViewGroup.MarginLayoutParams)localObject).leftMargin = i1;
            ((ViewGroup.MarginLayoutParams)localObject).rightMargin = i2;
            j = 1;
          }
          if ((n > 0) && (this.mStatusGuard == null))
          {
            paramWindowInsetsCompat = new View(this.mContext);
            this.mStatusGuard = paramWindowInsetsCompat;
            paramWindowInsetsCompat.setVisibility(8);
            paramWindowInsetsCompat = new FrameLayout.LayoutParams(-1, ((ViewGroup.MarginLayoutParams)localObject).topMargin, 51);
            paramWindowInsetsCompat.leftMargin = k;
            paramWindowInsetsCompat.rightMargin = m;
            this.mSubDecor.addView(this.mStatusGuard, -1, paramWindowInsetsCompat);
          }
          else
          {
            paramWindowInsetsCompat = this.mStatusGuard;
            if (paramWindowInsetsCompat != null)
            {
              paramWindowInsetsCompat = (ViewGroup.MarginLayoutParams)paramWindowInsetsCompat.getLayoutParams();
              if ((paramWindowInsetsCompat.height != ((ViewGroup.MarginLayoutParams)localObject).topMargin) || (paramWindowInsetsCompat.leftMargin != k) || (paramWindowInsetsCompat.rightMargin != m))
              {
                paramWindowInsetsCompat.height = ((ViewGroup.MarginLayoutParams)localObject).topMargin;
                paramWindowInsetsCompat.leftMargin = k;
                paramWindowInsetsCompat.rightMargin = m;
                this.mStatusGuard.setLayoutParams(paramWindowInsetsCompat);
              }
            }
          }
          paramWindowInsetsCompat = this.mStatusGuard;
          if (paramWindowInsetsCompat != null) {
            k = 1;
          } else {
            k = 0;
          }
          if ((k != 0) && (paramWindowInsetsCompat.getVisibility() != 0)) {
            updateStatusGuardColor(this.mStatusGuard);
          }
          if ((!this.mOverlayActionMode) && (k != 0)) {
            i = 0;
          }
        }
        else if (((ViewGroup.MarginLayoutParams)localObject).topMargin != 0)
        {
          j = 1;
          ((ViewGroup.MarginLayoutParams)localObject).topMargin = 0;
        }
        else
        {
          j = m;
        }
        m = i;
        n = k;
        if (j == 0) {
          break label557;
        }
        this.mActionModeView.setLayoutParams((ViewGroup.LayoutParams)localObject);
        m = i;
        n = k;
        break label557;
      }
    }
    int m = i;
    label557:
    paramWindowInsetsCompat = this.mStatusGuard;
    if (paramWindowInsetsCompat != null)
    {
      if (n != 0) {
        i = 0;
      } else {
        i = 8;
      }
      paramWindowInsetsCompat.setVisibility(i);
    }
    return m;
  }
  
  private class ActionBarDrawableToggleImpl
    implements ActionBarDrawerToggle.Delegate
  {
    ActionBarDrawableToggleImpl() {}
    
    public Context getActionBarThemedContext()
    {
      return AppCompatDelegateImpl.this.getActionBarThemedContext();
    }
    
    public Drawable getThemeUpIndicator()
    {
      TintTypedArray localTintTypedArray = TintTypedArray.obtainStyledAttributes(getActionBarThemedContext(), null, new int[] { R.attr.homeAsUpIndicator });
      Drawable localDrawable = localTintTypedArray.getDrawable(0);
      localTintTypedArray.recycle();
      return localDrawable;
    }
    
    public boolean isNavigationVisible()
    {
      ActionBar localActionBar = AppCompatDelegateImpl.this.getSupportActionBar();
      boolean bool;
      if ((localActionBar != null) && ((localActionBar.getDisplayOptions() & 0x4) != 0)) {
        bool = true;
      } else {
        bool = false;
      }
      return bool;
    }
    
    public void setActionBarDescription(int paramInt)
    {
      ActionBar localActionBar = AppCompatDelegateImpl.this.getSupportActionBar();
      if (localActionBar != null) {
        localActionBar.setHomeActionContentDescription(paramInt);
      }
    }
    
    public void setActionBarUpIndicator(Drawable paramDrawable, int paramInt)
    {
      ActionBar localActionBar = AppCompatDelegateImpl.this.getSupportActionBar();
      if (localActionBar != null)
      {
        localActionBar.setHomeAsUpIndicator(paramDrawable);
        localActionBar.setHomeActionContentDescription(paramInt);
      }
    }
  }
  
  static abstract interface ActionBarMenuCallback
  {
    public abstract View onCreatePanelView(int paramInt);
    
    public abstract boolean onPreparePanel(int paramInt);
  }
  
  private final class ActionMenuPresenterCallback
    implements MenuPresenter.Callback
  {
    ActionMenuPresenterCallback() {}
    
    public void onCloseMenu(MenuBuilder paramMenuBuilder, boolean paramBoolean)
    {
      AppCompatDelegateImpl.this.checkCloseActionMenu(paramMenuBuilder);
    }
    
    public boolean onOpenSubMenu(MenuBuilder paramMenuBuilder)
    {
      Window.Callback localCallback = AppCompatDelegateImpl.this.getWindowCallback();
      if (localCallback != null) {
        localCallback.onMenuOpened(108, paramMenuBuilder);
      }
      return true;
    }
  }
  
  class ActionModeCallbackWrapperV9
    implements androidx.appcompat.view.ActionMode.Callback
  {
    private androidx.appcompat.view.ActionMode.Callback mWrapped;
    
    public ActionModeCallbackWrapperV9(androidx.appcompat.view.ActionMode.Callback paramCallback)
    {
      this.mWrapped = paramCallback;
    }
    
    public boolean onActionItemClicked(androidx.appcompat.view.ActionMode paramActionMode, MenuItem paramMenuItem)
    {
      return this.mWrapped.onActionItemClicked(paramActionMode, paramMenuItem);
    }
    
    public boolean onCreateActionMode(androidx.appcompat.view.ActionMode paramActionMode, Menu paramMenu)
    {
      return this.mWrapped.onCreateActionMode(paramActionMode, paramMenu);
    }
    
    public void onDestroyActionMode(androidx.appcompat.view.ActionMode paramActionMode)
    {
      this.mWrapped.onDestroyActionMode(paramActionMode);
      if (AppCompatDelegateImpl.this.mActionModePopup != null) {
        AppCompatDelegateImpl.this.mWindow.getDecorView().removeCallbacks(AppCompatDelegateImpl.this.mShowActionModePopup);
      }
      if (AppCompatDelegateImpl.this.mActionModeView != null)
      {
        AppCompatDelegateImpl.this.endOnGoingFadeAnimation();
        paramActionMode = AppCompatDelegateImpl.this;
        paramActionMode.mFadeAnim = ViewCompat.animate(paramActionMode.mActionModeView).alpha(0.0F);
        AppCompatDelegateImpl.this.mFadeAnim.setListener(new ViewPropertyAnimatorListenerAdapter()
        {
          public void onAnimationEnd(View paramAnonymousView)
          {
            AppCompatDelegateImpl.this.mActionModeView.setVisibility(8);
            if (AppCompatDelegateImpl.this.mActionModePopup != null) {
              AppCompatDelegateImpl.this.mActionModePopup.dismiss();
            } else if ((AppCompatDelegateImpl.this.mActionModeView.getParent() instanceof View)) {
              ViewCompat.requestApplyInsets((View)AppCompatDelegateImpl.this.mActionModeView.getParent());
            }
            AppCompatDelegateImpl.this.mActionModeView.killMode();
            AppCompatDelegateImpl.this.mFadeAnim.setListener(null);
            AppCompatDelegateImpl.this.mFadeAnim = null;
            ViewCompat.requestApplyInsets(AppCompatDelegateImpl.this.mSubDecor);
          }
        });
      }
      if (AppCompatDelegateImpl.this.mAppCompatCallback != null) {
        AppCompatDelegateImpl.this.mAppCompatCallback.onSupportActionModeFinished(AppCompatDelegateImpl.this.mActionMode);
      }
      AppCompatDelegateImpl.this.mActionMode = null;
      ViewCompat.requestApplyInsets(AppCompatDelegateImpl.this.mSubDecor);
    }
    
    public boolean onPrepareActionMode(androidx.appcompat.view.ActionMode paramActionMode, Menu paramMenu)
    {
      ViewCompat.requestApplyInsets(AppCompatDelegateImpl.this.mSubDecor);
      return this.mWrapped.onPrepareActionMode(paramActionMode, paramMenu);
    }
  }
  
  static class Api17Impl
  {
    static Context createConfigurationContext(Context paramContext, Configuration paramConfiguration)
    {
      return paramContext.createConfigurationContext(paramConfiguration);
    }
    
    static void generateConfigDelta_densityDpi(Configuration paramConfiguration1, Configuration paramConfiguration2, Configuration paramConfiguration3)
    {
      if (paramConfiguration1.densityDpi != paramConfiguration2.densityDpi) {
        paramConfiguration3.densityDpi = paramConfiguration2.densityDpi;
      }
    }
  }
  
  static class Api21Impl
  {
    static boolean isPowerSaveMode(PowerManager paramPowerManager)
    {
      return paramPowerManager.isPowerSaveMode();
    }
  }
  
  static class Api24Impl
  {
    static void generateConfigDelta_locale(Configuration paramConfiguration1, Configuration paramConfiguration2, Configuration paramConfiguration3)
    {
      paramConfiguration1 = paramConfiguration1.getLocales();
      LocaleList localLocaleList = paramConfiguration2.getLocales();
      if (!paramConfiguration1.equals(localLocaleList))
      {
        paramConfiguration3.setLocales(localLocaleList);
        paramConfiguration3.locale = paramConfiguration2.locale;
      }
    }
  }
  
  static class Api26Impl
  {
    static void generateConfigDelta_colorMode(Configuration paramConfiguration1, Configuration paramConfiguration2, Configuration paramConfiguration3)
    {
      if ((paramConfiguration1.colorMode & 0x3) != (paramConfiguration2.colorMode & 0x3)) {
        paramConfiguration3.colorMode |= paramConfiguration2.colorMode & 0x3;
      }
      if ((paramConfiguration1.colorMode & 0xC) != (paramConfiguration2.colorMode & 0xC)) {
        paramConfiguration3.colorMode |= paramConfiguration2.colorMode & 0xC;
      }
    }
  }
  
  class AppCompatWindowCallback
    extends WindowCallbackWrapper
  {
    private AppCompatDelegateImpl.ActionBarMenuCallback mActionBarCallback;
    private boolean mDispatchKeyEventBypassEnabled;
    private boolean mOnContentChangedBypassEnabled;
    private boolean mOnPanelClosedBypassEnabled;
    
    AppCompatWindowCallback(Window.Callback paramCallback)
    {
      super();
    }
    
    public boolean bypassDispatchKeyEvent(Window.Callback paramCallback, KeyEvent paramKeyEvent)
    {
      try
      {
        this.mDispatchKeyEventBypassEnabled = true;
        boolean bool = paramCallback.dispatchKeyEvent(paramKeyEvent);
        return bool;
      }
      finally
      {
        this.mDispatchKeyEventBypassEnabled = false;
      }
    }
    
    public void bypassOnContentChanged(Window.Callback paramCallback)
    {
      try
      {
        this.mOnContentChangedBypassEnabled = true;
        paramCallback.onContentChanged();
        return;
      }
      finally
      {
        this.mOnContentChangedBypassEnabled = false;
      }
    }
    
    public void bypassOnPanelClosed(Window.Callback paramCallback, int paramInt, Menu paramMenu)
    {
      try
      {
        this.mOnPanelClosedBypassEnabled = true;
        paramCallback.onPanelClosed(paramInt, paramMenu);
        return;
      }
      finally
      {
        this.mOnPanelClosedBypassEnabled = false;
      }
    }
    
    public boolean dispatchKeyEvent(KeyEvent paramKeyEvent)
    {
      if (this.mDispatchKeyEventBypassEnabled) {
        return getWrapped().dispatchKeyEvent(paramKeyEvent);
      }
      boolean bool;
      if ((!AppCompatDelegateImpl.this.dispatchKeyEvent(paramKeyEvent)) && (!super.dispatchKeyEvent(paramKeyEvent))) {
        bool = false;
      } else {
        bool = true;
      }
      return bool;
    }
    
    public boolean dispatchKeyShortcutEvent(KeyEvent paramKeyEvent)
    {
      boolean bool;
      if ((!super.dispatchKeyShortcutEvent(paramKeyEvent)) && (!AppCompatDelegateImpl.this.onKeyShortcut(paramKeyEvent.getKeyCode(), paramKeyEvent))) {
        bool = false;
      } else {
        bool = true;
      }
      return bool;
    }
    
    public void onContentChanged()
    {
      if (this.mOnContentChangedBypassEnabled)
      {
        getWrapped().onContentChanged();
        return;
      }
    }
    
    public boolean onCreatePanelMenu(int paramInt, Menu paramMenu)
    {
      if ((paramInt == 0) && (!(paramMenu instanceof MenuBuilder))) {
        return false;
      }
      return super.onCreatePanelMenu(paramInt, paramMenu);
    }
    
    public View onCreatePanelView(int paramInt)
    {
      Object localObject = this.mActionBarCallback;
      if (localObject != null)
      {
        localObject = ((AppCompatDelegateImpl.ActionBarMenuCallback)localObject).onCreatePanelView(paramInt);
        if (localObject != null) {
          return (View)localObject;
        }
      }
      return super.onCreatePanelView(paramInt);
    }
    
    public boolean onMenuOpened(int paramInt, Menu paramMenu)
    {
      super.onMenuOpened(paramInt, paramMenu);
      AppCompatDelegateImpl.this.onMenuOpened(paramInt);
      return true;
    }
    
    public void onPanelClosed(int paramInt, Menu paramMenu)
    {
      if (this.mOnPanelClosedBypassEnabled)
      {
        getWrapped().onPanelClosed(paramInt, paramMenu);
        return;
      }
      super.onPanelClosed(paramInt, paramMenu);
      AppCompatDelegateImpl.this.onPanelClosed(paramInt);
    }
    
    public boolean onPreparePanel(int paramInt, View paramView, Menu paramMenu)
    {
      MenuBuilder localMenuBuilder;
      if ((paramMenu instanceof MenuBuilder)) {
        localMenuBuilder = (MenuBuilder)paramMenu;
      } else {
        localMenuBuilder = null;
      }
      if ((paramInt == 0) && (localMenuBuilder == null)) {
        return false;
      }
      if (localMenuBuilder != null) {
        localMenuBuilder.setOverrideVisibleItems(true);
      }
      boolean bool2 = false;
      AppCompatDelegateImpl.ActionBarMenuCallback localActionBarMenuCallback = this.mActionBarCallback;
      boolean bool1 = bool2;
      if (localActionBarMenuCallback != null)
      {
        bool1 = bool2;
        if (localActionBarMenuCallback.onPreparePanel(paramInt)) {
          bool1 = true;
        }
      }
      bool2 = bool1;
      if (!bool1) {
        bool2 = super.onPreparePanel(paramInt, paramView, paramMenu);
      }
      if (localMenuBuilder != null) {
        localMenuBuilder.setOverrideVisibleItems(false);
      }
      return bool2;
    }
    
    public void onProvideKeyboardShortcuts(List<KeyboardShortcutGroup> paramList, Menu paramMenu, int paramInt)
    {
      AppCompatDelegateImpl.PanelFeatureState localPanelFeatureState = AppCompatDelegateImpl.this.getPanelState(0, true);
      if ((localPanelFeatureState != null) && (localPanelFeatureState.menu != null)) {
        super.onProvideKeyboardShortcuts(paramList, localPanelFeatureState.menu, paramInt);
      } else {
        super.onProvideKeyboardShortcuts(paramList, paramMenu, paramInt);
      }
    }
    
    public android.view.ActionMode onWindowStartingActionMode(android.view.ActionMode.Callback paramCallback)
    {
      if (Build.VERSION.SDK_INT >= 23) {
        return null;
      }
      if (AppCompatDelegateImpl.this.isHandleNativeActionModesEnabled()) {
        return startAsSupportActionMode(paramCallback);
      }
      return super.onWindowStartingActionMode(paramCallback);
    }
    
    public android.view.ActionMode onWindowStartingActionMode(android.view.ActionMode.Callback paramCallback, int paramInt)
    {
      if (AppCompatDelegateImpl.this.isHandleNativeActionModesEnabled()) {
        switch (paramInt)
        {
        default: 
          break;
        case 0: 
          return startAsSupportActionMode(paramCallback);
        }
      }
      return super.onWindowStartingActionMode(paramCallback, paramInt);
    }
    
    void setActionBarCallback(AppCompatDelegateImpl.ActionBarMenuCallback paramActionBarMenuCallback)
    {
      this.mActionBarCallback = paramActionBarMenuCallback;
    }
    
    final android.view.ActionMode startAsSupportActionMode(android.view.ActionMode.Callback paramCallback)
    {
      SupportActionModeWrapper.CallbackWrapper localCallbackWrapper = new SupportActionModeWrapper.CallbackWrapper(AppCompatDelegateImpl.this.mContext, paramCallback);
      paramCallback = AppCompatDelegateImpl.this.startSupportActionMode(localCallbackWrapper);
      if (paramCallback != null) {
        return localCallbackWrapper.getActionModeWrapper(paramCallback);
      }
      return null;
    }
  }
  
  private class AutoBatteryNightModeManager
    extends AppCompatDelegateImpl.AutoNightModeManager
  {
    private final PowerManager mPowerManager;
    
    AutoBatteryNightModeManager(Context paramContext)
    {
      super();
      this.mPowerManager = ((PowerManager)paramContext.getApplicationContext().getSystemService("power"));
    }
    
    IntentFilter createIntentFilterForBroadcastReceiver()
    {
      if (Build.VERSION.SDK_INT >= 21)
      {
        IntentFilter localIntentFilter = new IntentFilter();
        localIntentFilter.addAction("android.os.action.POWER_SAVE_MODE_CHANGED");
        return localIntentFilter;
      }
      return null;
    }
    
    public int getApplyableNightMode()
    {
      int j = Build.VERSION.SDK_INT;
      int i = 1;
      if (j >= 21)
      {
        if (AppCompatDelegateImpl.Api21Impl.isPowerSaveMode(this.mPowerManager)) {
          i = 2;
        }
        return i;
      }
      return 1;
    }
    
    public void onChange()
    {
      AppCompatDelegateImpl.this.applyDayNight();
    }
  }
  
  abstract class AutoNightModeManager
  {
    private BroadcastReceiver mReceiver;
    
    AutoNightModeManager() {}
    
    void cleanup()
    {
      if (this.mReceiver != null)
      {
        try
        {
          AppCompatDelegateImpl.this.mContext.unregisterReceiver(this.mReceiver);
        }
        catch (IllegalArgumentException localIllegalArgumentException) {}
        this.mReceiver = null;
      }
    }
    
    abstract IntentFilter createIntentFilterForBroadcastReceiver();
    
    abstract int getApplyableNightMode();
    
    boolean isListening()
    {
      boolean bool;
      if (this.mReceiver != null) {
        bool = true;
      } else {
        bool = false;
      }
      return bool;
    }
    
    abstract void onChange();
    
    void setup()
    {
      cleanup();
      IntentFilter localIntentFilter = createIntentFilterForBroadcastReceiver();
      if ((localIntentFilter != null) && (localIntentFilter.countActions() != 0))
      {
        if (this.mReceiver == null) {
          this.mReceiver = new BroadcastReceiver()
          {
            public void onReceive(Context paramAnonymousContext, Intent paramAnonymousIntent)
            {
              AppCompatDelegateImpl.AutoNightModeManager.this.onChange();
            }
          };
        }
        AppCompatDelegateImpl.this.mContext.registerReceiver(this.mReceiver, localIntentFilter);
        return;
      }
    }
  }
  
  private class AutoTimeNightModeManager
    extends AppCompatDelegateImpl.AutoNightModeManager
  {
    private final TwilightManager mTwilightManager;
    
    AutoTimeNightModeManager(TwilightManager paramTwilightManager)
    {
      super();
      this.mTwilightManager = paramTwilightManager;
    }
    
    IntentFilter createIntentFilterForBroadcastReceiver()
    {
      IntentFilter localIntentFilter = new IntentFilter();
      localIntentFilter.addAction("android.intent.action.TIME_SET");
      localIntentFilter.addAction("android.intent.action.TIMEZONE_CHANGED");
      localIntentFilter.addAction("android.intent.action.TIME_TICK");
      return localIntentFilter;
    }
    
    public int getApplyableNightMode()
    {
      int i;
      if (this.mTwilightManager.isNight()) {
        i = 2;
      } else {
        i = 1;
      }
      return i;
    }
    
    public void onChange()
    {
      AppCompatDelegateImpl.this.applyDayNight();
    }
  }
  
  private static class ContextThemeWrapperCompatApi17Impl
  {
    static void applyOverrideConfiguration(android.view.ContextThemeWrapper paramContextThemeWrapper, Configuration paramConfiguration)
    {
      paramContextThemeWrapper.applyOverrideConfiguration(paramConfiguration);
    }
  }
  
  private class ListMenuDecorView
    extends ContentFrameLayout
  {
    public ListMenuDecorView(Context paramContext)
    {
      super();
    }
    
    private boolean isOutOfBounds(int paramInt1, int paramInt2)
    {
      boolean bool;
      if ((paramInt1 >= -5) && (paramInt2 >= -5) && (paramInt1 <= getWidth() + 5) && (paramInt2 <= getHeight() + 5)) {
        bool = false;
      } else {
        bool = true;
      }
      return bool;
    }
    
    public boolean dispatchKeyEvent(KeyEvent paramKeyEvent)
    {
      boolean bool;
      if ((!AppCompatDelegateImpl.this.dispatchKeyEvent(paramKeyEvent)) && (!super.dispatchKeyEvent(paramKeyEvent))) {
        bool = false;
      } else {
        bool = true;
      }
      return bool;
    }
    
    public boolean onInterceptTouchEvent(MotionEvent paramMotionEvent)
    {
      if ((paramMotionEvent.getAction() == 0) && (isOutOfBounds((int)paramMotionEvent.getX(), (int)paramMotionEvent.getY())))
      {
        AppCompatDelegateImpl.this.closePanel(0);
        return true;
      }
      return super.onInterceptTouchEvent(paramMotionEvent);
    }
    
    public void setBackgroundResource(int paramInt)
    {
      setBackgroundDrawable(AppCompatResources.getDrawable(getContext(), paramInt));
    }
  }
  
  protected static final class PanelFeatureState
  {
    int background;
    View createdPanelView;
    ViewGroup decorView;
    int featureId;
    Bundle frozenActionViewState;
    Bundle frozenMenuState;
    int gravity;
    boolean isHandled;
    boolean isOpen;
    boolean isPrepared;
    ListMenuPresenter listMenuPresenter;
    Context listPresenterContext;
    MenuBuilder menu;
    public boolean qwertyMode;
    boolean refreshDecorView;
    boolean refreshMenuContent;
    View shownPanelView;
    boolean wasLastOpen;
    int windowAnimations;
    int x;
    int y;
    
    PanelFeatureState(int paramInt)
    {
      this.featureId = paramInt;
      this.refreshDecorView = false;
    }
    
    void applyFrozenState()
    {
      MenuBuilder localMenuBuilder = this.menu;
      if (localMenuBuilder != null)
      {
        Bundle localBundle = this.frozenMenuState;
        if (localBundle != null)
        {
          localMenuBuilder.restorePresenterStates(localBundle);
          this.frozenMenuState = null;
        }
      }
    }
    
    public void clearMenuPresenters()
    {
      MenuBuilder localMenuBuilder = this.menu;
      if (localMenuBuilder != null) {
        localMenuBuilder.removeMenuPresenter(this.listMenuPresenter);
      }
      this.listMenuPresenter = null;
    }
    
    MenuView getListMenuView(MenuPresenter.Callback paramCallback)
    {
      if (this.menu == null) {
        return null;
      }
      if (this.listMenuPresenter == null)
      {
        ListMenuPresenter localListMenuPresenter = new ListMenuPresenter(this.listPresenterContext, R.layout.abc_list_menu_item_layout);
        this.listMenuPresenter = localListMenuPresenter;
        localListMenuPresenter.setCallback(paramCallback);
        this.menu.addMenuPresenter(this.listMenuPresenter);
      }
      return this.listMenuPresenter.getMenuView(this.decorView);
    }
    
    public boolean hasPanelItems()
    {
      View localView = this.shownPanelView;
      boolean bool = false;
      if (localView == null) {
        return false;
      }
      if (this.createdPanelView != null) {
        return true;
      }
      if (this.listMenuPresenter.getAdapter().getCount() > 0) {
        bool = true;
      }
      return bool;
    }
    
    void onRestoreInstanceState(Parcelable paramParcelable)
    {
      paramParcelable = (SavedState)paramParcelable;
      this.featureId = paramParcelable.featureId;
      this.wasLastOpen = paramParcelable.isOpen;
      this.frozenMenuState = paramParcelable.menuState;
      this.shownPanelView = null;
      this.decorView = null;
    }
    
    Parcelable onSaveInstanceState()
    {
      SavedState localSavedState = new SavedState();
      localSavedState.featureId = this.featureId;
      localSavedState.isOpen = this.isOpen;
      if (this.menu != null)
      {
        localSavedState.menuState = new Bundle();
        this.menu.savePresenterStates(localSavedState.menuState);
      }
      return localSavedState;
    }
    
    void setMenu(MenuBuilder paramMenuBuilder)
    {
      Object localObject = this.menu;
      if (paramMenuBuilder == localObject) {
        return;
      }
      if (localObject != null) {
        ((MenuBuilder)localObject).removeMenuPresenter(this.listMenuPresenter);
      }
      this.menu = paramMenuBuilder;
      if (paramMenuBuilder != null)
      {
        localObject = this.listMenuPresenter;
        if (localObject != null) {
          paramMenuBuilder.addMenuPresenter((MenuPresenter)localObject);
        }
      }
    }
    
    void setStyle(Context paramContext)
    {
      TypedValue localTypedValue = new TypedValue();
      Resources.Theme localTheme = paramContext.getResources().newTheme();
      localTheme.setTo(paramContext.getTheme());
      localTheme.resolveAttribute(R.attr.actionBarPopupTheme, localTypedValue, true);
      if (localTypedValue.resourceId != 0) {
        localTheme.applyStyle(localTypedValue.resourceId, true);
      }
      localTheme.resolveAttribute(R.attr.panelMenuListTheme, localTypedValue, true);
      if (localTypedValue.resourceId != 0) {
        localTheme.applyStyle(localTypedValue.resourceId, true);
      } else {
        localTheme.applyStyle(R.style.Theme_AppCompat_CompactMenu, true);
      }
      paramContext = new androidx.appcompat.view.ContextThemeWrapper(paramContext, 0);
      paramContext.getTheme().setTo(localTheme);
      this.listPresenterContext = paramContext;
      paramContext = paramContext.obtainStyledAttributes(R.styleable.AppCompatTheme);
      this.background = paramContext.getResourceId(R.styleable.AppCompatTheme_panelBackground, 0);
      this.windowAnimations = paramContext.getResourceId(R.styleable.AppCompatTheme_android_windowAnimationStyle, 0);
      paramContext.recycle();
    }
    
    private static class SavedState
      implements Parcelable
    {
      public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.ClassLoaderCreator()
      {
        public AppCompatDelegateImpl.PanelFeatureState.SavedState createFromParcel(Parcel paramAnonymousParcel)
        {
          return AppCompatDelegateImpl.PanelFeatureState.SavedState.readFromParcel(paramAnonymousParcel, null);
        }
        
        public AppCompatDelegateImpl.PanelFeatureState.SavedState createFromParcel(Parcel paramAnonymousParcel, ClassLoader paramAnonymousClassLoader)
        {
          return AppCompatDelegateImpl.PanelFeatureState.SavedState.readFromParcel(paramAnonymousParcel, paramAnonymousClassLoader);
        }
        
        public AppCompatDelegateImpl.PanelFeatureState.SavedState[] newArray(int paramAnonymousInt)
        {
          return new AppCompatDelegateImpl.PanelFeatureState.SavedState[paramAnonymousInt];
        }
      };
      int featureId;
      boolean isOpen;
      Bundle menuState;
      
      static SavedState readFromParcel(Parcel paramParcel, ClassLoader paramClassLoader)
      {
        SavedState localSavedState = new SavedState();
        localSavedState.featureId = paramParcel.readInt();
        int i = paramParcel.readInt();
        boolean bool = true;
        if (i != 1) {
          bool = false;
        }
        localSavedState.isOpen = bool;
        if (bool) {
          localSavedState.menuState = paramParcel.readBundle(paramClassLoader);
        }
        return localSavedState;
      }
      
      public int describeContents()
      {
        return 0;
      }
      
      public void writeToParcel(Parcel paramParcel, int paramInt)
      {
        paramParcel.writeInt(this.featureId);
        paramParcel.writeInt(this.isOpen);
        if (this.isOpen) {
          paramParcel.writeBundle(this.menuState);
        }
      }
    }
  }
  
  private final class PanelMenuPresenterCallback
    implements MenuPresenter.Callback
  {
    PanelMenuPresenterCallback() {}
    
    public void onCloseMenu(MenuBuilder paramMenuBuilder, boolean paramBoolean)
    {
      MenuBuilder localMenuBuilder = paramMenuBuilder.getRootMenu();
      int i;
      if (localMenuBuilder != paramMenuBuilder) {
        i = 1;
      } else {
        i = 0;
      }
      AppCompatDelegateImpl localAppCompatDelegateImpl = AppCompatDelegateImpl.this;
      if (i != 0) {
        paramMenuBuilder = localMenuBuilder;
      }
      paramMenuBuilder = localAppCompatDelegateImpl.findMenuPanel(paramMenuBuilder);
      if (paramMenuBuilder != null) {
        if (i != 0)
        {
          AppCompatDelegateImpl.this.callOnPanelClosed(paramMenuBuilder.featureId, paramMenuBuilder, localMenuBuilder);
          AppCompatDelegateImpl.this.closePanel(paramMenuBuilder, true);
        }
        else
        {
          AppCompatDelegateImpl.this.closePanel(paramMenuBuilder, paramBoolean);
        }
      }
    }
    
    public boolean onOpenSubMenu(MenuBuilder paramMenuBuilder)
    {
      if ((paramMenuBuilder == paramMenuBuilder.getRootMenu()) && (AppCompatDelegateImpl.this.mHasActionBar))
      {
        Window.Callback localCallback = AppCompatDelegateImpl.this.getWindowCallback();
        if ((localCallback != null) && (!AppCompatDelegateImpl.this.mDestroyed)) {
          localCallback.onMenuOpened(108, paramMenuBuilder);
        }
      }
      return true;
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/appcompat/app/AppCompatDelegateImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */