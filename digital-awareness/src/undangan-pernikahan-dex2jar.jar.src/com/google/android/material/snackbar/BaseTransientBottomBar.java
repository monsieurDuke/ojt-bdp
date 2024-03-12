package com.google.android.material.snackbar;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Insets;
import android.graphics.PorterDuff.Mode;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Looper;
import android.os.Message;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnAttachStateChangeListener;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewGroup.MarginLayoutParams;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.WindowInsets;
import android.view.WindowManager;
import android.view.accessibility.AccessibilityManager;
import android.widget.FrameLayout;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior;
import androidx.coordinatorlayout.widget.CoordinatorLayout.LayoutParams;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.core.view.AccessibilityDelegateCompat;
import androidx.core.view.OnApplyWindowInsetsListener;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import com.google.android.material.R.attr;
import com.google.android.material.R.dimen;
import com.google.android.material.R.layout;
import com.google.android.material.R.styleable;
import com.google.android.material.animation.AnimationUtils;
import com.google.android.material.behavior.SwipeDismissBehavior;
import com.google.android.material.behavior.SwipeDismissBehavior.OnDismissListener;
import com.google.android.material.color.MaterialColors;
import com.google.android.material.internal.ThemeEnforcement;
import com.google.android.material.internal.ViewUtils;
import com.google.android.material.resources.MaterialResources;
import com.google.android.material.theme.overlay.MaterialThemeOverlay;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import mt.Log229316;
import mt.Log5ECF72;
import mt.LogE84000;

public abstract class BaseTransientBottomBar<B extends BaseTransientBottomBar<B>>
{
  static final int ANIMATION_DURATION = 250;
  static final int ANIMATION_FADE_DURATION = 180;
  private static final int ANIMATION_FADE_IN_DURATION = 150;
  private static final int ANIMATION_FADE_OUT_DURATION = 75;
  public static final int ANIMATION_MODE_FADE = 1;
  public static final int ANIMATION_MODE_SLIDE = 0;
  private static final float ANIMATION_SCALE_FROM_VALUE = 0.8F;
  public static final int LENGTH_INDEFINITE = -2;
  public static final int LENGTH_LONG = 0;
  public static final int LENGTH_SHORT = -1;
  static final int MSG_DISMISS = 1;
  static final int MSG_SHOW = 0;
  private static final int[] SNACKBAR_STYLE_ATTR = { R.attr.snackbarStyle };
  private static final String TAG = BaseTransientBottomBar.class.getSimpleName();
  private static final boolean USE_OFFSET_API;
  static final Handler handler = new Handler(Looper.getMainLooper(), new Handler.Callback()
  {
    public boolean handleMessage(Message paramAnonymousMessage)
    {
      switch (paramAnonymousMessage.what)
      {
      default: 
        return false;
      case 1: 
        ((BaseTransientBottomBar)paramAnonymousMessage.obj).hideView(paramAnonymousMessage.arg1);
        return true;
      }
      ((BaseTransientBottomBar)paramAnonymousMessage.obj).showView();
      return true;
    }
  });
  private final AccessibilityManager accessibilityManager;
  private Anchor anchor;
  private boolean anchorViewLayoutListenerEnabled = false;
  private Behavior behavior;
  private final Runnable bottomMarginGestureInsetRunnable = new Runnable()
  {
    public void run()
    {
      if ((BaseTransientBottomBar.this.view != null) && (BaseTransientBottomBar.this.context != null))
      {
        int i = BaseTransientBottomBar.this.getScreenHeight() - BaseTransientBottomBar.this.getViewAbsoluteBottom() + (int)BaseTransientBottomBar.this.view.getTranslationY();
        if (i >= BaseTransientBottomBar.this.extraBottomMarginGestureInset) {
          return;
        }
        Object localObject = BaseTransientBottomBar.this.view.getLayoutParams();
        if (!(localObject instanceof ViewGroup.MarginLayoutParams))
        {
          localObject = BaseTransientBottomBar.TAG;
          Log5ECF72.a(localObject);
          LogE84000.a(localObject);
          Log229316.a(localObject);
          Log.w((String)localObject, "Unable to apply gesture inset because layout params are not MarginLayoutParams");
          return;
        }
        localObject = (ViewGroup.MarginLayoutParams)localObject;
        ((ViewGroup.MarginLayoutParams)localObject).bottomMargin += BaseTransientBottomBar.this.extraBottomMarginGestureInset - i;
        BaseTransientBottomBar.this.view.requestLayout();
        return;
      }
    }
  };
  private List<BaseCallback<B>> callbacks;
  private final ContentViewCallback contentViewCallback;
  private final Context context;
  private int duration;
  private int extraBottomMarginAnchorView;
  private int extraBottomMarginGestureInset;
  private int extraBottomMarginWindowInset;
  private int extraLeftMarginWindowInset;
  private int extraRightMarginWindowInset;
  private boolean gestureInsetBottomIgnored;
  SnackbarManager.Callback managerCallback = new SnackbarManager.Callback()
  {
    public void dismiss(int paramAnonymousInt)
    {
      BaseTransientBottomBar.handler.sendMessage(BaseTransientBottomBar.handler.obtainMessage(1, paramAnonymousInt, 0, BaseTransientBottomBar.this));
    }
    
    public void show()
    {
      BaseTransientBottomBar.handler.sendMessage(BaseTransientBottomBar.handler.obtainMessage(0, BaseTransientBottomBar.this));
    }
  };
  private boolean pendingShowingView;
  private final ViewGroup targetParent;
  protected final SnackbarBaseLayout view;
  
  static
  {
    boolean bool;
    if ((Build.VERSION.SDK_INT >= 16) && (Build.VERSION.SDK_INT <= 19)) {
      bool = true;
    } else {
      bool = false;
    }
    USE_OFFSET_API = bool;
  }
  
  protected BaseTransientBottomBar(Context paramContext, ViewGroup paramViewGroup, View paramView, ContentViewCallback paramContentViewCallback)
  {
    if (paramViewGroup != null)
    {
      if (paramView != null)
      {
        if (paramContentViewCallback != null)
        {
          this.targetParent = paramViewGroup;
          this.contentViewCallback = paramContentViewCallback;
          this.context = paramContext;
          ThemeEnforcement.checkAppCompatTheme(paramContext);
          paramViewGroup = (SnackbarBaseLayout)LayoutInflater.from(paramContext).inflate(getSnackbarBaseLayoutResId(), paramViewGroup, false);
          this.view = paramViewGroup;
          paramViewGroup.setBaseTransientBottomBar(this);
          if ((paramView instanceof SnackbarContentLayout))
          {
            ((SnackbarContentLayout)paramView).updateActionTextColorAlphaIfNeeded(paramViewGroup.getActionTextColorAlpha());
            ((SnackbarContentLayout)paramView).setMaxInlineActionWidth(paramViewGroup.getMaxInlineActionWidth());
          }
          paramViewGroup.addView(paramView);
          ViewCompat.setAccessibilityLiveRegion(paramViewGroup, 1);
          ViewCompat.setImportantForAccessibility(paramViewGroup, 1);
          ViewCompat.setFitsSystemWindows(paramViewGroup, true);
          ViewCompat.setOnApplyWindowInsetsListener(paramViewGroup, new OnApplyWindowInsetsListener()
          {
            public WindowInsetsCompat onApplyWindowInsets(View paramAnonymousView, WindowInsetsCompat paramAnonymousWindowInsetsCompat)
            {
              BaseTransientBottomBar.access$602(BaseTransientBottomBar.this, paramAnonymousWindowInsetsCompat.getSystemWindowInsetBottom());
              BaseTransientBottomBar.access$702(BaseTransientBottomBar.this, paramAnonymousWindowInsetsCompat.getSystemWindowInsetLeft());
              BaseTransientBottomBar.access$802(BaseTransientBottomBar.this, paramAnonymousWindowInsetsCompat.getSystemWindowInsetRight());
              BaseTransientBottomBar.this.updateMargins();
              return paramAnonymousWindowInsetsCompat;
            }
          });
          ViewCompat.setAccessibilityDelegate(paramViewGroup, new AccessibilityDelegateCompat()
          {
            public void onInitializeAccessibilityNodeInfo(View paramAnonymousView, AccessibilityNodeInfoCompat paramAnonymousAccessibilityNodeInfoCompat)
            {
              super.onInitializeAccessibilityNodeInfo(paramAnonymousView, paramAnonymousAccessibilityNodeInfoCompat);
              paramAnonymousAccessibilityNodeInfoCompat.addAction(1048576);
              paramAnonymousAccessibilityNodeInfoCompat.setDismissable(true);
            }
            
            public boolean performAccessibilityAction(View paramAnonymousView, int paramAnonymousInt, Bundle paramAnonymousBundle)
            {
              if (paramAnonymousInt == 1048576)
              {
                BaseTransientBottomBar.this.dismiss();
                return true;
              }
              return super.performAccessibilityAction(paramAnonymousView, paramAnonymousInt, paramAnonymousBundle);
            }
          });
          this.accessibilityManager = ((AccessibilityManager)paramContext.getSystemService("accessibility"));
          return;
        }
        throw new IllegalArgumentException("Transient bottom bar must have non-null callback");
      }
      throw new IllegalArgumentException("Transient bottom bar must have non-null content");
    }
    throw new IllegalArgumentException("Transient bottom bar must have non-null parent");
  }
  
  protected BaseTransientBottomBar(ViewGroup paramViewGroup, View paramView, ContentViewCallback paramContentViewCallback)
  {
    this(paramViewGroup.getContext(), paramViewGroup, paramView, paramContentViewCallback);
  }
  
  private void animateViewOut(int paramInt)
  {
    if (this.view.getAnimationMode() == 1) {
      startFadeOutAnimation(paramInt);
    } else {
      startSlideOutAnimation(paramInt);
    }
  }
  
  private int calculateBottomMarginForAnchorView()
  {
    if (getAnchorView() == null) {
      return 0;
    }
    int[] arrayOfInt = new int[2];
    getAnchorView().getLocationOnScreen(arrayOfInt);
    int i = arrayOfInt[1];
    arrayOfInt = new int[2];
    this.targetParent.getLocationOnScreen(arrayOfInt);
    return arrayOfInt[1] + this.targetParent.getHeight() - i;
  }
  
  private ValueAnimator getAlphaAnimator(float... paramVarArgs)
  {
    paramVarArgs = ValueAnimator.ofFloat(paramVarArgs);
    paramVarArgs.setInterpolator(AnimationUtils.LINEAR_INTERPOLATOR);
    paramVarArgs.addUpdateListener(new ValueAnimator.AnimatorUpdateListener()
    {
      public void onAnimationUpdate(ValueAnimator paramAnonymousValueAnimator)
      {
        BaseTransientBottomBar.this.view.setAlpha(((Float)paramAnonymousValueAnimator.getAnimatedValue()).floatValue());
      }
    });
    return paramVarArgs;
  }
  
  private ValueAnimator getScaleAnimator(float... paramVarArgs)
  {
    paramVarArgs = ValueAnimator.ofFloat(paramVarArgs);
    paramVarArgs.setInterpolator(AnimationUtils.LINEAR_OUT_SLOW_IN_INTERPOLATOR);
    paramVarArgs.addUpdateListener(new ValueAnimator.AnimatorUpdateListener()
    {
      public void onAnimationUpdate(ValueAnimator paramAnonymousValueAnimator)
      {
        float f = ((Float)paramAnonymousValueAnimator.getAnimatedValue()).floatValue();
        BaseTransientBottomBar.this.view.setScaleX(f);
        BaseTransientBottomBar.this.view.setScaleY(f);
      }
    });
    return paramVarArgs;
  }
  
  private int getScreenHeight()
  {
    WindowManager localWindowManager = (WindowManager)this.context.getSystemService("window");
    DisplayMetrics localDisplayMetrics = new DisplayMetrics();
    localWindowManager.getDefaultDisplay().getRealMetrics(localDisplayMetrics);
    return localDisplayMetrics.heightPixels;
  }
  
  private int getTranslationYBottom()
  {
    int j = this.view.getHeight();
    ViewGroup.LayoutParams localLayoutParams = this.view.getLayoutParams();
    int i = j;
    if ((localLayoutParams instanceof ViewGroup.MarginLayoutParams)) {
      i = j + ((ViewGroup.MarginLayoutParams)localLayoutParams).bottomMargin;
    }
    return i;
  }
  
  private int getViewAbsoluteBottom()
  {
    int[] arrayOfInt = new int[2];
    this.view.getLocationOnScreen(arrayOfInt);
    return arrayOfInt[1] + this.view.getHeight();
  }
  
  private boolean isSwipeDismissable()
  {
    ViewGroup.LayoutParams localLayoutParams = this.view.getLayoutParams();
    boolean bool;
    if (((localLayoutParams instanceof CoordinatorLayout.LayoutParams)) && ((((CoordinatorLayout.LayoutParams)localLayoutParams).getBehavior() instanceof SwipeDismissBehavior))) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  private void recalculateAndUpdateMargins()
  {
    this.extraBottomMarginAnchorView = calculateBottomMarginForAnchorView();
    updateMargins();
  }
  
  private void setUpBehavior(CoordinatorLayout.LayoutParams paramLayoutParams)
  {
    Behavior localBehavior = this.behavior;
    Object localObject = localBehavior;
    if (localBehavior == null) {
      localObject = getNewBehavior();
    }
    if ((localObject instanceof Behavior)) {
      ((Behavior)localObject).setBaseTransientBottomBar(this);
    }
    ((SwipeDismissBehavior)localObject).setListener(new SwipeDismissBehavior.OnDismissListener()
    {
      public void onDismiss(View paramAnonymousView)
      {
        if (paramAnonymousView.getParent() != null) {
          paramAnonymousView.setVisibility(8);
        }
        BaseTransientBottomBar.this.dispatchDismiss(0);
      }
      
      public void onDragStateChanged(int paramAnonymousInt)
      {
        switch (paramAnonymousInt)
        {
        default: 
          break;
        case 1: 
        case 2: 
          SnackbarManager.getInstance().pauseTimeout(BaseTransientBottomBar.this.managerCallback);
          break;
        case 0: 
          SnackbarManager.getInstance().restoreTimeoutIfPaused(BaseTransientBottomBar.this.managerCallback);
        }
      }
    });
    paramLayoutParams.setBehavior((CoordinatorLayout.Behavior)localObject);
    if (getAnchorView() == null) {
      paramLayoutParams.insetEdge = 80;
    }
  }
  
  private boolean shouldUpdateGestureInset()
  {
    boolean bool;
    if ((this.extraBottomMarginGestureInset > 0) && (!this.gestureInsetBottomIgnored) && (isSwipeDismissable())) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  private void showViewImpl()
  {
    if (shouldAnimate())
    {
      animateViewIn();
    }
    else
    {
      if (this.view.getParent() != null) {
        this.view.setVisibility(0);
      }
      onViewShown();
    }
  }
  
  private void startFadeInAnimation()
  {
    ValueAnimator localValueAnimator2 = getAlphaAnimator(new float[] { 0.0F, 1.0F });
    ValueAnimator localValueAnimator1 = getScaleAnimator(new float[] { 0.8F, 1.0F });
    AnimatorSet localAnimatorSet = new AnimatorSet();
    localAnimatorSet.playTogether(new Animator[] { localValueAnimator2, localValueAnimator1 });
    localAnimatorSet.setDuration(150L);
    localAnimatorSet.addListener(new AnimatorListenerAdapter()
    {
      public void onAnimationEnd(Animator paramAnonymousAnimator)
      {
        BaseTransientBottomBar.this.onViewShown();
      }
    });
    localAnimatorSet.start();
  }
  
  private void startFadeOutAnimation(final int paramInt)
  {
    ValueAnimator localValueAnimator = getAlphaAnimator(new float[] { 1.0F, 0.0F });
    localValueAnimator.setDuration(75L);
    localValueAnimator.addListener(new AnimatorListenerAdapter()
    {
      public void onAnimationEnd(Animator paramAnonymousAnimator)
      {
        BaseTransientBottomBar.this.onViewHidden(paramInt);
      }
    });
    localValueAnimator.start();
  }
  
  private void startSlideInAnimation()
  {
    final int i = getTranslationYBottom();
    if (USE_OFFSET_API) {
      ViewCompat.offsetTopAndBottom(this.view, i);
    } else {
      this.view.setTranslationY(i);
    }
    ValueAnimator localValueAnimator = new ValueAnimator();
    localValueAnimator.setIntValues(new int[] { i, 0 });
    localValueAnimator.setInterpolator(AnimationUtils.FAST_OUT_SLOW_IN_INTERPOLATOR);
    localValueAnimator.setDuration(250L);
    localValueAnimator.addListener(new AnimatorListenerAdapter()
    {
      public void onAnimationEnd(Animator paramAnonymousAnimator)
      {
        BaseTransientBottomBar.this.onViewShown();
      }
      
      public void onAnimationStart(Animator paramAnonymousAnimator)
      {
        BaseTransientBottomBar.this.contentViewCallback.animateContentIn(70, 180);
      }
    });
    localValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener()
    {
      private int previousAnimatedIntValue;
      
      public void onAnimationUpdate(ValueAnimator paramAnonymousValueAnimator)
      {
        int i = ((Integer)paramAnonymousValueAnimator.getAnimatedValue()).intValue();
        if (BaseTransientBottomBar.USE_OFFSET_API) {
          ViewCompat.offsetTopAndBottom(BaseTransientBottomBar.this.view, i - this.previousAnimatedIntValue);
        } else {
          BaseTransientBottomBar.this.view.setTranslationY(i);
        }
        this.previousAnimatedIntValue = i;
      }
    });
    localValueAnimator.start();
  }
  
  private void startSlideOutAnimation(final int paramInt)
  {
    ValueAnimator localValueAnimator = new ValueAnimator();
    localValueAnimator.setIntValues(new int[] { 0, getTranslationYBottom() });
    localValueAnimator.setInterpolator(AnimationUtils.FAST_OUT_SLOW_IN_INTERPOLATOR);
    localValueAnimator.setDuration(250L);
    localValueAnimator.addListener(new AnimatorListenerAdapter()
    {
      public void onAnimationEnd(Animator paramAnonymousAnimator)
      {
        BaseTransientBottomBar.this.onViewHidden(paramInt);
      }
      
      public void onAnimationStart(Animator paramAnonymousAnimator)
      {
        BaseTransientBottomBar.this.contentViewCallback.animateContentOut(0, 180);
      }
    });
    localValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener()
    {
      private int previousAnimatedIntValue = 0;
      
      public void onAnimationUpdate(ValueAnimator paramAnonymousValueAnimator)
      {
        int i = ((Integer)paramAnonymousValueAnimator.getAnimatedValue()).intValue();
        if (BaseTransientBottomBar.USE_OFFSET_API) {
          ViewCompat.offsetTopAndBottom(BaseTransientBottomBar.this.view, i - this.previousAnimatedIntValue);
        } else {
          BaseTransientBottomBar.this.view.setTranslationY(i);
        }
        this.previousAnimatedIntValue = i;
      }
    });
    localValueAnimator.start();
  }
  
  private void updateMargins()
  {
    Object localObject = this.view.getLayoutParams();
    if (((localObject instanceof ViewGroup.MarginLayoutParams)) && (this.view.originalMargins != null))
    {
      if (this.view.getParent() == null) {
        return;
      }
      int i;
      if (getAnchorView() != null) {
        i = this.extraBottomMarginAnchorView;
      } else {
        i = this.extraBottomMarginWindowInset;
      }
      localObject = (ViewGroup.MarginLayoutParams)localObject;
      ((ViewGroup.MarginLayoutParams)localObject).bottomMargin = (this.view.originalMargins.bottom + i);
      ((ViewGroup.MarginLayoutParams)localObject).leftMargin = (this.view.originalMargins.left + this.extraLeftMarginWindowInset);
      ((ViewGroup.MarginLayoutParams)localObject).rightMargin = (this.view.originalMargins.right + this.extraRightMarginWindowInset);
      ((ViewGroup.MarginLayoutParams)localObject).topMargin = this.view.originalMargins.top;
      this.view.requestLayout();
      if ((Build.VERSION.SDK_INT >= 29) && (shouldUpdateGestureInset()))
      {
        this.view.removeCallbacks(this.bottomMarginGestureInsetRunnable);
        this.view.post(this.bottomMarginGestureInsetRunnable);
      }
      return;
    }
    Log.w(TAG, "Unable to update margins because layout params are not MarginLayoutParams");
  }
  
  public B addCallback(BaseCallback<B> paramBaseCallback)
  {
    if (paramBaseCallback == null) {
      return this;
    }
    if (this.callbacks == null) {
      this.callbacks = new ArrayList();
    }
    this.callbacks.add(paramBaseCallback);
    return this;
  }
  
  void animateViewIn()
  {
    this.view.post(new Runnable()
    {
      public void run()
      {
        if (BaseTransientBottomBar.this.view == null) {
          return;
        }
        if (BaseTransientBottomBar.this.view.getParent() != null) {
          BaseTransientBottomBar.this.view.setVisibility(0);
        }
        if (BaseTransientBottomBar.this.view.getAnimationMode() == 1) {
          BaseTransientBottomBar.this.startFadeInAnimation();
        } else {
          BaseTransientBottomBar.this.startSlideInAnimation();
        }
      }
    });
  }
  
  public void dismiss()
  {
    dispatchDismiss(3);
  }
  
  protected void dispatchDismiss(int paramInt)
  {
    SnackbarManager.getInstance().dismiss(this.managerCallback, paramInt);
  }
  
  public View getAnchorView()
  {
    Object localObject = this.anchor;
    if (localObject == null) {
      localObject = null;
    } else {
      localObject = ((Anchor)localObject).getAnchorView();
    }
    return (View)localObject;
  }
  
  public int getAnimationMode()
  {
    return this.view.getAnimationMode();
  }
  
  public Behavior getBehavior()
  {
    return this.behavior;
  }
  
  public Context getContext()
  {
    return this.context;
  }
  
  public int getDuration()
  {
    return this.duration;
  }
  
  protected SwipeDismissBehavior<? extends View> getNewBehavior()
  {
    return new Behavior();
  }
  
  protected int getSnackbarBaseLayoutResId()
  {
    int i;
    if (hasSnackbarStyleAttr()) {
      i = R.layout.mtrl_layout_snackbar;
    } else {
      i = R.layout.design_layout_snackbar;
    }
    return i;
  }
  
  public View getView()
  {
    return this.view;
  }
  
  protected boolean hasSnackbarStyleAttr()
  {
    TypedArray localTypedArray = this.context.obtainStyledAttributes(SNACKBAR_STYLE_ATTR);
    boolean bool = false;
    int i = localTypedArray.getResourceId(0, -1);
    localTypedArray.recycle();
    if (i != -1) {
      bool = true;
    }
    return bool;
  }
  
  final void hideView(int paramInt)
  {
    if ((shouldAnimate()) && (this.view.getVisibility() == 0)) {
      animateViewOut(paramInt);
    } else {
      onViewHidden(paramInt);
    }
  }
  
  public boolean isAnchorViewLayoutListenerEnabled()
  {
    return this.anchorViewLayoutListenerEnabled;
  }
  
  public boolean isGestureInsetBottomIgnored()
  {
    return this.gestureInsetBottomIgnored;
  }
  
  public boolean isShown()
  {
    return SnackbarManager.getInstance().isCurrent(this.managerCallback);
  }
  
  public boolean isShownOrQueued()
  {
    return SnackbarManager.getInstance().isCurrentOrNext(this.managerCallback);
  }
  
  void onAttachedToWindow()
  {
    if (Build.VERSION.SDK_INT >= 29)
    {
      WindowInsets localWindowInsets = this.view.getRootWindowInsets();
      if (localWindowInsets != null)
      {
        this.extraBottomMarginGestureInset = localWindowInsets.getMandatorySystemGestureInsets().bottom;
        updateMargins();
      }
    }
  }
  
  void onDetachedFromWindow()
  {
    if (isShownOrQueued()) {
      handler.post(new Runnable()
      {
        public void run()
        {
          BaseTransientBottomBar.this.onViewHidden(3);
        }
      });
    }
  }
  
  void onLayoutChange()
  {
    if (this.pendingShowingView)
    {
      showViewImpl();
      this.pendingShowingView = false;
    }
  }
  
  void onViewHidden(int paramInt)
  {
    SnackbarManager.getInstance().onDismissed(this.managerCallback);
    Object localObject = this.callbacks;
    if (localObject != null) {
      for (int i = ((List)localObject).size() - 1; i >= 0; i--) {
        ((BaseCallback)this.callbacks.get(i)).onDismissed(this, paramInt);
      }
    }
    localObject = this.view.getParent();
    if ((localObject instanceof ViewGroup)) {
      ((ViewGroup)localObject).removeView(this.view);
    }
  }
  
  void onViewShown()
  {
    SnackbarManager.getInstance().onShown(this.managerCallback);
    List localList = this.callbacks;
    if (localList != null) {
      for (int i = localList.size() - 1; i >= 0; i--) {
        ((BaseCallback)this.callbacks.get(i)).onShown(this);
      }
    }
  }
  
  public B removeCallback(BaseCallback<B> paramBaseCallback)
  {
    if (paramBaseCallback == null) {
      return this;
    }
    List localList = this.callbacks;
    if (localList == null) {
      return this;
    }
    localList.remove(paramBaseCallback);
    return this;
  }
  
  public B setAnchorView(int paramInt)
  {
    View localView = this.targetParent.findViewById(paramInt);
    if (localView != null) {
      return setAnchorView(localView);
    }
    throw new IllegalArgumentException("Unable to find anchor view with id: " + paramInt);
  }
  
  public B setAnchorView(View paramView)
  {
    Anchor localAnchor = this.anchor;
    if (localAnchor != null) {
      localAnchor.unanchor();
    }
    if (paramView == null) {
      paramView = null;
    } else {
      paramView = Anchor.anchor(this, paramView);
    }
    this.anchor = paramView;
    return this;
  }
  
  public void setAnchorViewLayoutListenerEnabled(boolean paramBoolean)
  {
    this.anchorViewLayoutListenerEnabled = paramBoolean;
  }
  
  public B setAnimationMode(int paramInt)
  {
    this.view.setAnimationMode(paramInt);
    return this;
  }
  
  public B setBehavior(Behavior paramBehavior)
  {
    this.behavior = paramBehavior;
    return this;
  }
  
  public B setDuration(int paramInt)
  {
    this.duration = paramInt;
    return this;
  }
  
  public B setGestureInsetBottomIgnored(boolean paramBoolean)
  {
    this.gestureInsetBottomIgnored = paramBoolean;
    return this;
  }
  
  boolean shouldAnimate()
  {
    Object localObject = this.accessibilityManager;
    boolean bool = true;
    if (localObject == null) {
      return true;
    }
    localObject = ((AccessibilityManager)localObject).getEnabledAccessibilityServiceList(1);
    if ((localObject == null) || (!((List)localObject).isEmpty())) {
      bool = false;
    }
    return bool;
  }
  
  public void show()
  {
    SnackbarManager.getInstance().show(getDuration(), this.managerCallback);
  }
  
  final void showView()
  {
    if (this.view.getParent() == null)
    {
      ViewGroup.LayoutParams localLayoutParams = this.view.getLayoutParams();
      if ((localLayoutParams instanceof CoordinatorLayout.LayoutParams)) {
        setUpBehavior((CoordinatorLayout.LayoutParams)localLayoutParams);
      }
      this.view.addToTargetParent(this.targetParent);
      recalculateAndUpdateMargins();
      this.view.setVisibility(4);
    }
    if (ViewCompat.isLaidOut(this.view))
    {
      showViewImpl();
      return;
    }
    this.pendingShowingView = true;
  }
  
  static class Anchor
    implements View.OnAttachStateChangeListener, ViewTreeObserver.OnGlobalLayoutListener
  {
    private final WeakReference<View> anchorView;
    private final WeakReference<BaseTransientBottomBar> transientBottomBar;
    
    private Anchor(BaseTransientBottomBar paramBaseTransientBottomBar, View paramView)
    {
      this.transientBottomBar = new WeakReference(paramBaseTransientBottomBar);
      this.anchorView = new WeakReference(paramView);
    }
    
    static Anchor anchor(BaseTransientBottomBar paramBaseTransientBottomBar, View paramView)
    {
      paramBaseTransientBottomBar = new Anchor(paramBaseTransientBottomBar, paramView);
      if (ViewCompat.isAttachedToWindow(paramView)) {
        ViewUtils.addOnGlobalLayoutListener(paramView, paramBaseTransientBottomBar);
      }
      paramView.addOnAttachStateChangeListener(paramBaseTransientBottomBar);
      return paramBaseTransientBottomBar;
    }
    
    private boolean unanchorIfNoTransientBottomBar()
    {
      if (this.transientBottomBar.get() == null)
      {
        unanchor();
        return true;
      }
      return false;
    }
    
    View getAnchorView()
    {
      return (View)this.anchorView.get();
    }
    
    public void onGlobalLayout()
    {
      if ((!unanchorIfNoTransientBottomBar()) && (((BaseTransientBottomBar)this.transientBottomBar.get()).anchorViewLayoutListenerEnabled))
      {
        ((BaseTransientBottomBar)this.transientBottomBar.get()).recalculateAndUpdateMargins();
        return;
      }
    }
    
    public void onViewAttachedToWindow(View paramView)
    {
      if (unanchorIfNoTransientBottomBar()) {
        return;
      }
      ViewUtils.addOnGlobalLayoutListener(paramView, this);
    }
    
    public void onViewDetachedFromWindow(View paramView)
    {
      if (unanchorIfNoTransientBottomBar()) {
        return;
      }
      ViewUtils.removeOnGlobalLayoutListener(paramView, this);
    }
    
    void unanchor()
    {
      if (this.anchorView.get() != null)
      {
        ((View)this.anchorView.get()).removeOnAttachStateChangeListener(this);
        ViewUtils.removeOnGlobalLayoutListener((View)this.anchorView.get(), this);
      }
      this.anchorView.clear();
      this.transientBottomBar.clear();
    }
  }
  
  @Retention(RetentionPolicy.SOURCE)
  public static @interface AnimationMode {}
  
  public static abstract class BaseCallback<B>
  {
    public static final int DISMISS_EVENT_ACTION = 1;
    public static final int DISMISS_EVENT_CONSECUTIVE = 4;
    public static final int DISMISS_EVENT_MANUAL = 3;
    public static final int DISMISS_EVENT_SWIPE = 0;
    public static final int DISMISS_EVENT_TIMEOUT = 2;
    
    public void onDismissed(B paramB, int paramInt) {}
    
    public void onShown(B paramB) {}
    
    @Retention(RetentionPolicy.SOURCE)
    public static @interface DismissEvent {}
  }
  
  public static class Behavior
    extends SwipeDismissBehavior<View>
  {
    private final BaseTransientBottomBar.BehaviorDelegate delegate = new BaseTransientBottomBar.BehaviorDelegate(this);
    
    private void setBaseTransientBottomBar(BaseTransientBottomBar<?> paramBaseTransientBottomBar)
    {
      this.delegate.setBaseTransientBottomBar(paramBaseTransientBottomBar);
    }
    
    public boolean canSwipeDismissView(View paramView)
    {
      return this.delegate.canSwipeDismissView(paramView);
    }
    
    public boolean onInterceptTouchEvent(CoordinatorLayout paramCoordinatorLayout, View paramView, MotionEvent paramMotionEvent)
    {
      this.delegate.onInterceptTouchEvent(paramCoordinatorLayout, paramView, paramMotionEvent);
      return super.onInterceptTouchEvent(paramCoordinatorLayout, paramView, paramMotionEvent);
    }
  }
  
  public static class BehaviorDelegate
  {
    private SnackbarManager.Callback managerCallback;
    
    public BehaviorDelegate(SwipeDismissBehavior<?> paramSwipeDismissBehavior)
    {
      paramSwipeDismissBehavior.setStartAlphaSwipeDistance(0.1F);
      paramSwipeDismissBehavior.setEndAlphaSwipeDistance(0.6F);
      paramSwipeDismissBehavior.setSwipeDirection(0);
    }
    
    public boolean canSwipeDismissView(View paramView)
    {
      return paramView instanceof BaseTransientBottomBar.SnackbarBaseLayout;
    }
    
    public void onInterceptTouchEvent(CoordinatorLayout paramCoordinatorLayout, View paramView, MotionEvent paramMotionEvent)
    {
      switch (paramMotionEvent.getActionMasked())
      {
      case 2: 
      default: 
        break;
      case 1: 
      case 3: 
        SnackbarManager.getInstance().restoreTimeoutIfPaused(this.managerCallback);
        break;
      case 0: 
        if (paramCoordinatorLayout.isPointInChildBounds(paramView, (int)paramMotionEvent.getX(), (int)paramMotionEvent.getY())) {
          SnackbarManager.getInstance().pauseTimeout(this.managerCallback);
        }
        break;
      }
    }
    
    public void setBaseTransientBottomBar(BaseTransientBottomBar<?> paramBaseTransientBottomBar)
    {
      this.managerCallback = paramBaseTransientBottomBar.managerCallback;
    }
  }
  
  @Deprecated
  public static abstract interface ContentViewCallback
    extends ContentViewCallback
  {}
  
  @Retention(RetentionPolicy.SOURCE)
  public static @interface Duration {}
  
  protected static class SnackbarBaseLayout
    extends FrameLayout
  {
    private static final View.OnTouchListener consumeAllTouchListener = new View.OnTouchListener()
    {
      public boolean onTouch(View paramAnonymousView, MotionEvent paramAnonymousMotionEvent)
      {
        return true;
      }
    };
    private final float actionTextColorAlpha;
    private boolean addingToTargetParent;
    private int animationMode;
    private final float backgroundOverlayColorAlpha;
    private ColorStateList backgroundTint;
    private PorterDuff.Mode backgroundTintMode;
    private BaseTransientBottomBar<?> baseTransientBottomBar;
    private final int maxInlineActionWidth;
    private final int maxWidth;
    private Rect originalMargins;
    
    protected SnackbarBaseLayout(Context paramContext)
    {
      this(paramContext, null);
    }
    
    protected SnackbarBaseLayout(Context paramContext, AttributeSet paramAttributeSet)
    {
      super(paramAttributeSet);
      paramContext = getContext();
      paramAttributeSet = paramContext.obtainStyledAttributes(paramAttributeSet, R.styleable.SnackbarLayout);
      if (paramAttributeSet.hasValue(R.styleable.SnackbarLayout_elevation)) {
        ViewCompat.setElevation(this, paramAttributeSet.getDimensionPixelSize(R.styleable.SnackbarLayout_elevation, 0));
      }
      this.animationMode = paramAttributeSet.getInt(R.styleable.SnackbarLayout_animationMode, 0);
      this.backgroundOverlayColorAlpha = paramAttributeSet.getFloat(R.styleable.SnackbarLayout_backgroundOverlayColorAlpha, 1.0F);
      setBackgroundTintList(MaterialResources.getColorStateList(paramContext, paramAttributeSet, R.styleable.SnackbarLayout_backgroundTint));
      setBackgroundTintMode(ViewUtils.parseTintMode(paramAttributeSet.getInt(R.styleable.SnackbarLayout_backgroundTintMode, -1), PorterDuff.Mode.SRC_IN));
      this.actionTextColorAlpha = paramAttributeSet.getFloat(R.styleable.SnackbarLayout_actionTextColorAlpha, 1.0F);
      this.maxWidth = paramAttributeSet.getDimensionPixelSize(R.styleable.SnackbarLayout_android_maxWidth, -1);
      this.maxInlineActionWidth = paramAttributeSet.getDimensionPixelSize(R.styleable.SnackbarLayout_maxActionInlineWidth, -1);
      paramAttributeSet.recycle();
      setOnTouchListener(consumeAllTouchListener);
      setFocusable(true);
      if (getBackground() == null) {
        ViewCompat.setBackground(this, createThemedBackground());
      }
    }
    
    private Drawable createThemedBackground()
    {
      float f = getResources().getDimension(R.dimen.mtrl_snackbar_background_corner_radius);
      Object localObject = new GradientDrawable();
      ((GradientDrawable)localObject).setShape(0);
      ((GradientDrawable)localObject).setCornerRadius(f);
      ((GradientDrawable)localObject).setColor(MaterialColors.layer(this, R.attr.colorSurface, R.attr.colorOnSurface, getBackgroundOverlayColorAlpha()));
      if (this.backgroundTint != null)
      {
        localObject = DrawableCompat.wrap((Drawable)localObject);
        DrawableCompat.setTintList((Drawable)localObject, this.backgroundTint);
        return (Drawable)localObject;
      }
      return DrawableCompat.wrap((Drawable)localObject);
    }
    
    private void setBaseTransientBottomBar(BaseTransientBottomBar<?> paramBaseTransientBottomBar)
    {
      this.baseTransientBottomBar = paramBaseTransientBottomBar;
    }
    
    private void updateOriginalMargins(ViewGroup.MarginLayoutParams paramMarginLayoutParams)
    {
      this.originalMargins = new Rect(paramMarginLayoutParams.leftMargin, paramMarginLayoutParams.topMargin, paramMarginLayoutParams.rightMargin, paramMarginLayoutParams.bottomMargin);
    }
    
    void addToTargetParent(ViewGroup paramViewGroup)
    {
      this.addingToTargetParent = true;
      paramViewGroup.addView(this);
      this.addingToTargetParent = false;
    }
    
    float getActionTextColorAlpha()
    {
      return this.actionTextColorAlpha;
    }
    
    int getAnimationMode()
    {
      return this.animationMode;
    }
    
    float getBackgroundOverlayColorAlpha()
    {
      return this.backgroundOverlayColorAlpha;
    }
    
    int getMaxInlineActionWidth()
    {
      return this.maxInlineActionWidth;
    }
    
    int getMaxWidth()
    {
      return this.maxWidth;
    }
    
    protected void onAttachedToWindow()
    {
      super.onAttachedToWindow();
      BaseTransientBottomBar localBaseTransientBottomBar = this.baseTransientBottomBar;
      if (localBaseTransientBottomBar != null) {
        localBaseTransientBottomBar.onAttachedToWindow();
      }
      ViewCompat.requestApplyInsets(this);
    }
    
    protected void onDetachedFromWindow()
    {
      super.onDetachedFromWindow();
      BaseTransientBottomBar localBaseTransientBottomBar = this.baseTransientBottomBar;
      if (localBaseTransientBottomBar != null) {
        localBaseTransientBottomBar.onDetachedFromWindow();
      }
    }
    
    protected void onLayout(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
    {
      super.onLayout(paramBoolean, paramInt1, paramInt2, paramInt3, paramInt4);
      BaseTransientBottomBar localBaseTransientBottomBar = this.baseTransientBottomBar;
      if (localBaseTransientBottomBar != null) {
        localBaseTransientBottomBar.onLayoutChange();
      }
    }
    
    protected void onMeasure(int paramInt1, int paramInt2)
    {
      super.onMeasure(paramInt1, paramInt2);
      if (this.maxWidth > 0)
      {
        int i = getMeasuredWidth();
        paramInt1 = this.maxWidth;
        if (i > paramInt1) {
          super.onMeasure(View.MeasureSpec.makeMeasureSpec(paramInt1, 1073741824), paramInt2);
        }
      }
    }
    
    void setAnimationMode(int paramInt)
    {
      this.animationMode = paramInt;
    }
    
    public void setBackground(Drawable paramDrawable)
    {
      setBackgroundDrawable(paramDrawable);
    }
    
    public void setBackgroundDrawable(Drawable paramDrawable)
    {
      Drawable localDrawable = paramDrawable;
      if (paramDrawable != null)
      {
        localDrawable = paramDrawable;
        if (this.backgroundTint != null)
        {
          localDrawable = DrawableCompat.wrap(paramDrawable.mutate());
          DrawableCompat.setTintList(localDrawable, this.backgroundTint);
          DrawableCompat.setTintMode(localDrawable, this.backgroundTintMode);
        }
      }
      super.setBackgroundDrawable(localDrawable);
    }
    
    public void setBackgroundTintList(ColorStateList paramColorStateList)
    {
      this.backgroundTint = paramColorStateList;
      if (getBackground() != null)
      {
        Drawable localDrawable = DrawableCompat.wrap(getBackground().mutate());
        DrawableCompat.setTintList(localDrawable, paramColorStateList);
        DrawableCompat.setTintMode(localDrawable, this.backgroundTintMode);
        if (localDrawable != getBackground()) {
          super.setBackgroundDrawable(localDrawable);
        }
      }
    }
    
    public void setBackgroundTintMode(PorterDuff.Mode paramMode)
    {
      this.backgroundTintMode = paramMode;
      if (getBackground() != null)
      {
        Drawable localDrawable = DrawableCompat.wrap(getBackground().mutate());
        DrawableCompat.setTintMode(localDrawable, paramMode);
        if (localDrawable != getBackground()) {
          super.setBackgroundDrawable(localDrawable);
        }
      }
    }
    
    public void setLayoutParams(ViewGroup.LayoutParams paramLayoutParams)
    {
      super.setLayoutParams(paramLayoutParams);
      if ((!this.addingToTargetParent) && ((paramLayoutParams instanceof ViewGroup.MarginLayoutParams)))
      {
        updateOriginalMargins((ViewGroup.MarginLayoutParams)paramLayoutParams);
        paramLayoutParams = this.baseTransientBottomBar;
        if (paramLayoutParams != null) {
          paramLayoutParams.updateMargins();
        }
      }
    }
    
    public void setOnClickListener(View.OnClickListener paramOnClickListener)
    {
      View.OnTouchListener localOnTouchListener;
      if (paramOnClickListener != null) {
        localOnTouchListener = null;
      } else {
        localOnTouchListener = consumeAllTouchListener;
      }
      setOnTouchListener(localOnTouchListener);
      super.setOnClickListener(paramOnClickListener);
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/com/google/android/material/snackbar/BaseTransientBottomBar.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */