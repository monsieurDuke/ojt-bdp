package com.google.android.material.bottomappbar;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.ClassLoaderCreator;
import android.os.Parcelable.Creator;
import android.util.AttributeSet;
import android.view.Menu;
import android.view.View;
import android.view.View.OnLayoutChangeListener;
import android.view.ViewGroup;
import androidx.appcompat.widget.ActionMenuView;
import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.widget.Toolbar.LayoutParams;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.coordinatorlayout.widget.CoordinatorLayout.AttachedBehavior;
import androidx.coordinatorlayout.widget.CoordinatorLayout.LayoutParams;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.customview.view.AbsSavedState;
import com.google.android.material.R.animator;
import com.google.android.material.R.attr;
import com.google.android.material.R.dimen;
import com.google.android.material.R.style;
import com.google.android.material.R.styleable;
import com.google.android.material.animation.TransformationCallback;
import com.google.android.material.behavior.HideBottomViewOnScrollBehavior;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton.OnVisibilityChangedListener;
import com.google.android.material.internal.ThemeEnforcement;
import com.google.android.material.internal.ViewUtils;
import com.google.android.material.internal.ViewUtils.OnApplyWindowInsetsListener;
import com.google.android.material.internal.ViewUtils.RelativePadding;
import com.google.android.material.resources.MaterialResources;
import com.google.android.material.shape.CornerSize;
import com.google.android.material.shape.EdgeTreatment;
import com.google.android.material.shape.MaterialShapeDrawable;
import com.google.android.material.shape.MaterialShapeUtils;
import com.google.android.material.shape.ShapeAppearanceModel;
import com.google.android.material.shape.ShapeAppearanceModel.Builder;
import com.google.android.material.theme.overlay.MaterialThemeOverlay;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class BottomAppBar
  extends Toolbar
  implements CoordinatorLayout.AttachedBehavior
{
  private static final long ANIMATION_DURATION = 300L;
  private static final int DEF_STYLE_RES = R.style.Widget_MaterialComponents_BottomAppBar;
  public static final int FAB_ALIGNMENT_MODE_CENTER = 0;
  public static final int FAB_ALIGNMENT_MODE_END = 1;
  public static final int FAB_ANIMATION_MODE_SCALE = 0;
  public static final int FAB_ANIMATION_MODE_SLIDE = 1;
  private static final int NO_MENU_RES_ID = 0;
  private int animatingModeChangeCounter;
  private ArrayList<AnimationListener> animationListeners;
  private Behavior behavior;
  private int bottomInset;
  private int fabAlignmentMode;
  AnimatorListenerAdapter fabAnimationListener;
  private int fabAnimationMode;
  private boolean fabAttached;
  private final int fabOffsetEndMode;
  TransformationCallback<FloatingActionButton> fabTransformationCallback;
  private boolean hideOnScroll;
  private int leftInset;
  private final MaterialShapeDrawable materialShapeDrawable;
  private boolean menuAnimatingWithFabAlignmentMode;
  private Animator menuAnimator;
  private Animator modeAnimator;
  private Integer navigationIconTint;
  private final boolean paddingBottomSystemWindowInsets;
  private final boolean paddingLeftSystemWindowInsets;
  private final boolean paddingRightSystemWindowInsets;
  private int pendingMenuResId;
  private int rightInset;
  
  public BottomAppBar(Context paramContext)
  {
    this(paramContext, null);
  }
  
  public BottomAppBar(Context paramContext, AttributeSet paramAttributeSet)
  {
    this(paramContext, paramAttributeSet, R.attr.bottomAppBarStyle);
  }
  
  public BottomAppBar(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(MaterialThemeOverlay.wrap(paramContext, paramAttributeSet, paramInt, i), paramAttributeSet, paramInt);
    paramContext = new MaterialShapeDrawable();
    this.materialShapeDrawable = paramContext;
    this.animatingModeChangeCounter = 0;
    this.pendingMenuResId = 0;
    this.menuAnimatingWithFabAlignmentMode = false;
    this.fabAttached = true;
    this.fabAnimationListener = new AnimatorListenerAdapter()
    {
      public void onAnimationStart(Animator paramAnonymousAnimator)
      {
        if (!BottomAppBar.this.menuAnimatingWithFabAlignmentMode)
        {
          paramAnonymousAnimator = BottomAppBar.this;
          paramAnonymousAnimator.maybeAnimateMenuView(paramAnonymousAnimator.fabAlignmentMode, BottomAppBar.this.fabAttached);
        }
      }
    };
    this.fabTransformationCallback = new TransformationCallback()
    {
      public void onScaleChanged(FloatingActionButton paramAnonymousFloatingActionButton)
      {
        MaterialShapeDrawable localMaterialShapeDrawable = BottomAppBar.this.materialShapeDrawable;
        float f;
        if (paramAnonymousFloatingActionButton.getVisibility() == 0) {
          f = paramAnonymousFloatingActionButton.getScaleY();
        } else {
          f = 0.0F;
        }
        localMaterialShapeDrawable.setInterpolation(f);
      }
      
      public void onTranslationChanged(FloatingActionButton paramAnonymousFloatingActionButton)
      {
        float f1 = paramAnonymousFloatingActionButton.getTranslationX();
        if (BottomAppBar.this.getTopEdgeTreatment().getHorizontalOffset() != f1)
        {
          BottomAppBar.this.getTopEdgeTreatment().setHorizontalOffset(f1);
          BottomAppBar.this.materialShapeDrawable.invalidateSelf();
        }
        float f2 = -paramAnonymousFloatingActionButton.getTranslationY();
        f1 = 0.0F;
        f2 = Math.max(0.0F, f2);
        if (BottomAppBar.this.getTopEdgeTreatment().getCradleVerticalOffset() != f2)
        {
          BottomAppBar.this.getTopEdgeTreatment().setCradleVerticalOffset(f2);
          BottomAppBar.this.materialShapeDrawable.invalidateSelf();
        }
        MaterialShapeDrawable localMaterialShapeDrawable = BottomAppBar.this.materialShapeDrawable;
        if (paramAnonymousFloatingActionButton.getVisibility() == 0) {
          f1 = paramAnonymousFloatingActionButton.getScaleY();
        }
        localMaterialShapeDrawable.setInterpolation(f1);
      }
    };
    Context localContext = getContext();
    Object localObject = ThemeEnforcement.obtainStyledAttributes(localContext, paramAttributeSet, R.styleable.BottomAppBar, paramInt, i, new int[0]);
    ColorStateList localColorStateList = MaterialResources.getColorStateList(localContext, (TypedArray)localObject, R.styleable.BottomAppBar_backgroundTint);
    if (((TypedArray)localObject).hasValue(R.styleable.BottomAppBar_navigationIconTint)) {
      setNavigationIconTint(((TypedArray)localObject).getColor(R.styleable.BottomAppBar_navigationIconTint, -1));
    }
    int j = ((TypedArray)localObject).getDimensionPixelSize(R.styleable.BottomAppBar_elevation, 0);
    float f1 = ((TypedArray)localObject).getDimensionPixelOffset(R.styleable.BottomAppBar_fabCradleMargin, 0);
    float f2 = ((TypedArray)localObject).getDimensionPixelOffset(R.styleable.BottomAppBar_fabCradleRoundedCornerRadius, 0);
    float f3 = ((TypedArray)localObject).getDimensionPixelOffset(R.styleable.BottomAppBar_fabCradleVerticalOffset, 0);
    this.fabAlignmentMode = ((TypedArray)localObject).getInt(R.styleable.BottomAppBar_fabAlignmentMode, 0);
    this.fabAnimationMode = ((TypedArray)localObject).getInt(R.styleable.BottomAppBar_fabAnimationMode, 0);
    this.hideOnScroll = ((TypedArray)localObject).getBoolean(R.styleable.BottomAppBar_hideOnScroll, false);
    this.paddingBottomSystemWindowInsets = ((TypedArray)localObject).getBoolean(R.styleable.BottomAppBar_paddingBottomSystemWindowInsets, false);
    this.paddingLeftSystemWindowInsets = ((TypedArray)localObject).getBoolean(R.styleable.BottomAppBar_paddingLeftSystemWindowInsets, false);
    this.paddingRightSystemWindowInsets = ((TypedArray)localObject).getBoolean(R.styleable.BottomAppBar_paddingRightSystemWindowInsets, false);
    ((TypedArray)localObject).recycle();
    this.fabOffsetEndMode = getResources().getDimensionPixelOffset(R.dimen.mtrl_bottomappbar_fabOffsetEndMode);
    localObject = new BottomAppBarTopEdgeTreatment(f1, f2, f3);
    paramContext.setShapeAppearanceModel(ShapeAppearanceModel.builder().setTopEdge((EdgeTreatment)localObject).build());
    paramContext.setShadowCompatibilityMode(2);
    paramContext.setPaintStyle(Paint.Style.FILL);
    paramContext.initializeElevationOverlay(localContext);
    setElevation(j);
    DrawableCompat.setTintList(paramContext, localColorStateList);
    ViewCompat.setBackground(this, paramContext);
    ViewUtils.doOnApplyWindowInsets(this, paramAttributeSet, paramInt, i, new ViewUtils.OnApplyWindowInsetsListener()
    {
      public WindowInsetsCompat onApplyWindowInsets(View paramAnonymousView, WindowInsetsCompat paramAnonymousWindowInsetsCompat, ViewUtils.RelativePadding paramAnonymousRelativePadding)
      {
        int j = 0;
        int m = 0;
        if (BottomAppBar.this.paddingBottomSystemWindowInsets) {
          BottomAppBar.access$702(BottomAppBar.this, paramAnonymousWindowInsetsCompat.getSystemWindowInsetBottom());
        }
        boolean bool = BottomAppBar.this.paddingLeftSystemWindowInsets;
        int k = 1;
        if (bool)
        {
          if (BottomAppBar.this.leftInset != paramAnonymousWindowInsetsCompat.getSystemWindowInsetLeft()) {
            i = 1;
          } else {
            i = 0;
          }
          BottomAppBar.access$902(BottomAppBar.this, paramAnonymousWindowInsetsCompat.getSystemWindowInsetLeft());
          j = i;
        }
        int i = m;
        if (BottomAppBar.this.paddingRightSystemWindowInsets)
        {
          if (BottomAppBar.this.rightInset != paramAnonymousWindowInsetsCompat.getSystemWindowInsetRight()) {
            i = k;
          } else {
            i = 0;
          }
          BottomAppBar.access$1102(BottomAppBar.this, paramAnonymousWindowInsetsCompat.getSystemWindowInsetRight());
        }
        if ((j != 0) || (i != 0))
        {
          BottomAppBar.this.cancelAnimations();
          BottomAppBar.this.setCutoutState();
          BottomAppBar.this.setActionMenuViewPosition();
        }
        return paramAnonymousWindowInsetsCompat;
      }
    });
  }
  
  private void addFabAnimationListeners(FloatingActionButton paramFloatingActionButton)
  {
    paramFloatingActionButton.addOnHideAnimationListener(this.fabAnimationListener);
    paramFloatingActionButton.addOnShowAnimationListener(new AnimatorListenerAdapter()
    {
      public void onAnimationStart(Animator paramAnonymousAnimator)
      {
        BottomAppBar.this.fabAnimationListener.onAnimationStart(paramAnonymousAnimator);
        paramAnonymousAnimator = BottomAppBar.this.findDependentFab();
        if (paramAnonymousAnimator != null) {
          paramAnonymousAnimator.setTranslationX(BottomAppBar.this.getFabTranslationX());
        }
      }
    });
    paramFloatingActionButton.addTransformationCallback(this.fabTransformationCallback);
  }
  
  private void cancelAnimations()
  {
    Animator localAnimator = this.menuAnimator;
    if (localAnimator != null) {
      localAnimator.cancel();
    }
    localAnimator = this.modeAnimator;
    if (localAnimator != null) {
      localAnimator.cancel();
    }
  }
  
  private void createFabTranslationXAnimation(int paramInt, List<Animator> paramList)
  {
    ObjectAnimator localObjectAnimator = ObjectAnimator.ofFloat(findDependentFab(), "translationX", new float[] { getFabTranslationX(paramInt) });
    localObjectAnimator.setDuration(300L);
    paramList.add(localObjectAnimator);
  }
  
  private void createMenuViewTranslationAnimation(final int paramInt, final boolean paramBoolean, List<Animator> paramList)
  {
    final Object localObject = getActionMenuView();
    if (localObject == null) {
      return;
    }
    ObjectAnimator localObjectAnimator2 = ObjectAnimator.ofFloat(localObject, "alpha", new float[] { 1.0F });
    if (Math.abs(((ActionMenuView)localObject).getTranslationX() - getActionMenuViewTranslationX((ActionMenuView)localObject, paramInt, paramBoolean)) > 1.0F)
    {
      ObjectAnimator localObjectAnimator1 = ObjectAnimator.ofFloat(localObject, "alpha", new float[] { 0.0F });
      localObjectAnimator1.addListener(new AnimatorListenerAdapter()
      {
        public boolean cancelled;
        
        public void onAnimationCancel(Animator paramAnonymousAnimator)
        {
          this.cancelled = true;
        }
        
        public void onAnimationEnd(Animator paramAnonymousAnimator)
        {
          if (!this.cancelled)
          {
            boolean bool;
            if (BottomAppBar.this.pendingMenuResId != 0) {
              bool = true;
            } else {
              bool = false;
            }
            paramAnonymousAnimator = BottomAppBar.this;
            paramAnonymousAnimator.replaceMenu(paramAnonymousAnimator.pendingMenuResId);
            BottomAppBar.this.translateActionMenuView(localObject, paramInt, paramBoolean, bool);
          }
        }
      });
      localObject = new AnimatorSet();
      ((AnimatorSet)localObject).setDuration(150L);
      ((AnimatorSet)localObject).playSequentially(new Animator[] { localObjectAnimator1, localObjectAnimator2 });
      paramList.add(localObject);
    }
    else if (((ActionMenuView)localObject).getAlpha() < 1.0F)
    {
      paramList.add(localObjectAnimator2);
    }
  }
  
  private void dispatchAnimationEnd()
  {
    int i = this.animatingModeChangeCounter - 1;
    this.animatingModeChangeCounter = i;
    if (i == 0)
    {
      Object localObject = this.animationListeners;
      if (localObject != null)
      {
        localObject = ((ArrayList)localObject).iterator();
        while (((Iterator)localObject).hasNext()) {
          ((AnimationListener)((Iterator)localObject).next()).onAnimationEnd(this);
        }
      }
    }
  }
  
  private void dispatchAnimationStart()
  {
    int i = this.animatingModeChangeCounter;
    this.animatingModeChangeCounter = (i + 1);
    if (i == 0)
    {
      Object localObject = this.animationListeners;
      if (localObject != null)
      {
        localObject = ((ArrayList)localObject).iterator();
        while (((Iterator)localObject).hasNext()) {
          ((AnimationListener)((Iterator)localObject).next()).onAnimationStart(this);
        }
      }
    }
  }
  
  private FloatingActionButton findDependentFab()
  {
    Object localObject = findDependentView();
    if ((localObject instanceof FloatingActionButton)) {
      localObject = (FloatingActionButton)localObject;
    } else {
      localObject = null;
    }
    return (FloatingActionButton)localObject;
  }
  
  private View findDependentView()
  {
    if (!(getParent() instanceof CoordinatorLayout)) {
      return null;
    }
    Iterator localIterator = ((CoordinatorLayout)getParent()).getDependents(this).iterator();
    while (localIterator.hasNext())
    {
      View localView = (View)localIterator.next();
      if ((!(localView instanceof FloatingActionButton)) && (!(localView instanceof ExtendedFloatingActionButton))) {}
      return localView;
    }
    return null;
  }
  
  private ActionMenuView getActionMenuView()
  {
    for (int i = 0; i < getChildCount(); i++)
    {
      View localView = getChildAt(i);
      if ((localView instanceof ActionMenuView)) {
        return (ActionMenuView)localView;
      }
    }
    return null;
  }
  
  private int getBottomInset()
  {
    return this.bottomInset;
  }
  
  private float getFabTranslationX()
  {
    return getFabTranslationX(this.fabAlignmentMode);
  }
  
  private float getFabTranslationX(int paramInt)
  {
    boolean bool = ViewUtils.isLayoutRtl(this);
    int i = 1;
    if (paramInt == 1)
    {
      if (bool) {
        paramInt = this.leftInset;
      } else {
        paramInt = this.rightInset;
      }
      int k = this.fabOffsetEndMode;
      int j = getMeasuredWidth() / 2;
      if (bool) {
        i = -1;
      }
      return (j - (k + paramInt)) * i;
    }
    return 0.0F;
  }
  
  private float getFabTranslationY()
  {
    return -getTopEdgeTreatment().getCradleVerticalOffset();
  }
  
  private int getLeftInset()
  {
    return this.leftInset;
  }
  
  private int getRightInset()
  {
    return this.rightInset;
  }
  
  private BottomAppBarTopEdgeTreatment getTopEdgeTreatment()
  {
    return (BottomAppBarTopEdgeTreatment)this.materialShapeDrawable.getShapeAppearanceModel().getTopEdge();
  }
  
  private boolean isFabVisibleOrWillBeShown()
  {
    FloatingActionButton localFloatingActionButton = findDependentFab();
    boolean bool;
    if ((localFloatingActionButton != null) && (localFloatingActionButton.isOrWillBeShown())) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  private void maybeAnimateMenuView(int paramInt, boolean paramBoolean)
  {
    if (!ViewCompat.isLaidOut(this))
    {
      this.menuAnimatingWithFabAlignmentMode = false;
      replaceMenu(this.pendingMenuResId);
      return;
    }
    Object localObject = this.menuAnimator;
    if (localObject != null) {
      ((Animator)localObject).cancel();
    }
    ArrayList localArrayList = new ArrayList();
    if (!isFabVisibleOrWillBeShown())
    {
      paramInt = 0;
      paramBoolean = false;
    }
    createMenuViewTranslationAnimation(paramInt, paramBoolean, localArrayList);
    localObject = new AnimatorSet();
    ((AnimatorSet)localObject).playTogether(localArrayList);
    this.menuAnimator = ((Animator)localObject);
    ((Animator)localObject).addListener(new AnimatorListenerAdapter()
    {
      public void onAnimationEnd(Animator paramAnonymousAnimator)
      {
        BottomAppBar.this.dispatchAnimationEnd();
        BottomAppBar.access$002(BottomAppBar.this, false);
        BottomAppBar.access$1902(BottomAppBar.this, null);
      }
      
      public void onAnimationStart(Animator paramAnonymousAnimator)
      {
        BottomAppBar.this.dispatchAnimationStart();
      }
    });
    this.menuAnimator.start();
  }
  
  private void maybeAnimateModeChange(int paramInt)
  {
    if ((this.fabAlignmentMode != paramInt) && (ViewCompat.isLaidOut(this)))
    {
      Object localObject = this.modeAnimator;
      if (localObject != null) {
        ((Animator)localObject).cancel();
      }
      ArrayList localArrayList = new ArrayList();
      if (this.fabAnimationMode == 1) {
        createFabTranslationXAnimation(paramInt, localArrayList);
      } else {
        createFabDefaultXAnimation(paramInt, localArrayList);
      }
      localObject = new AnimatorSet();
      ((AnimatorSet)localObject).playTogether(localArrayList);
      this.modeAnimator = ((Animator)localObject);
      ((Animator)localObject).addListener(new AnimatorListenerAdapter()
      {
        public void onAnimationEnd(Animator paramAnonymousAnimator)
        {
          BottomAppBar.this.dispatchAnimationEnd();
          BottomAppBar.access$1702(BottomAppBar.this, null);
        }
        
        public void onAnimationStart(Animator paramAnonymousAnimator)
        {
          BottomAppBar.this.dispatchAnimationStart();
        }
      });
      this.modeAnimator.start();
      return;
    }
  }
  
  private Drawable maybeTintNavigationIcon(Drawable paramDrawable)
  {
    if ((paramDrawable != null) && (this.navigationIconTint != null))
    {
      paramDrawable = DrawableCompat.wrap(paramDrawable.mutate());
      DrawableCompat.setTint(paramDrawable, this.navigationIconTint.intValue());
      return paramDrawable;
    }
    return paramDrawable;
  }
  
  private void setActionMenuViewPosition()
  {
    ActionMenuView localActionMenuView = getActionMenuView();
    if ((localActionMenuView != null) && (this.menuAnimator == null))
    {
      localActionMenuView.setAlpha(1.0F);
      if (!isFabVisibleOrWillBeShown()) {
        translateActionMenuView(localActionMenuView, 0, false);
      } else {
        translateActionMenuView(localActionMenuView, this.fabAlignmentMode, this.fabAttached);
      }
    }
  }
  
  private void setCutoutState()
  {
    getTopEdgeTreatment().setHorizontalOffset(getFabTranslationX());
    View localView = findDependentView();
    MaterialShapeDrawable localMaterialShapeDrawable = this.materialShapeDrawable;
    float f;
    if ((this.fabAttached) && (isFabVisibleOrWillBeShown())) {
      f = 1.0F;
    } else {
      f = 0.0F;
    }
    localMaterialShapeDrawable.setInterpolation(f);
    if (localView != null)
    {
      localView.setTranslationY(getFabTranslationY());
      localView.setTranslationX(getFabTranslationX());
    }
  }
  
  private void translateActionMenuView(ActionMenuView paramActionMenuView, int paramInt, boolean paramBoolean)
  {
    translateActionMenuView(paramActionMenuView, paramInt, paramBoolean, false);
  }
  
  private void translateActionMenuView(final ActionMenuView paramActionMenuView, final int paramInt, final boolean paramBoolean1, boolean paramBoolean2)
  {
    Runnable local8 = new Runnable()
    {
      public void run()
      {
        ActionMenuView localActionMenuView = paramActionMenuView;
        localActionMenuView.setTranslationX(BottomAppBar.this.getActionMenuViewTranslationX(localActionMenuView, paramInt, paramBoolean1));
      }
    };
    if (paramBoolean2) {
      paramActionMenuView.post(local8);
    } else {
      local8.run();
    }
  }
  
  void addAnimationListener(AnimationListener paramAnimationListener)
  {
    if (this.animationListeners == null) {
      this.animationListeners = new ArrayList();
    }
    this.animationListeners.add(paramAnimationListener);
  }
  
  protected void createFabDefaultXAnimation(final int paramInt, List<Animator> paramList)
  {
    paramList = findDependentFab();
    if ((paramList != null) && (!paramList.isOrWillBeHidden()))
    {
      dispatchAnimationStart();
      paramList.hide(new FloatingActionButton.OnVisibilityChangedListener()
      {
        public void onHidden(FloatingActionButton paramAnonymousFloatingActionButton)
        {
          paramAnonymousFloatingActionButton.setTranslationX(BottomAppBar.this.getFabTranslationX(paramInt));
          paramAnonymousFloatingActionButton.show(new FloatingActionButton.OnVisibilityChangedListener()
          {
            public void onShown(FloatingActionButton paramAnonymous2FloatingActionButton)
            {
              BottomAppBar.this.dispatchAnimationEnd();
            }
          });
        }
      });
      return;
    }
  }
  
  protected int getActionMenuViewTranslationX(ActionMenuView paramActionMenuView, int paramInt, boolean paramBoolean)
  {
    if ((paramInt == 1) && (paramBoolean))
    {
      paramBoolean = ViewUtils.isLayoutRtl(this);
      if (paramBoolean) {
        paramInt = getMeasuredWidth();
      } else {
        paramInt = 0;
      }
      int i = 0;
      int j;
      while (i < getChildCount())
      {
        View localView = getChildAt(i);
        int k;
        if (((localView.getLayoutParams() instanceof Toolbar.LayoutParams)) && ((((Toolbar.LayoutParams)localView.getLayoutParams()).gravity & 0x800007) == 8388611)) {
          k = 1;
        } else {
          k = 0;
        }
        j = paramInt;
        if (k != 0)
        {
          if (paramBoolean) {
            paramInt = Math.min(paramInt, localView.getLeft());
          } else {
            paramInt = Math.max(paramInt, localView.getRight());
          }
          j = paramInt;
        }
        i++;
        paramInt = j;
      }
      if (paramBoolean) {
        i = paramActionMenuView.getRight();
      } else {
        i = paramActionMenuView.getLeft();
      }
      if (paramBoolean) {
        j = this.rightInset;
      } else {
        j = -this.leftInset;
      }
      return paramInt - (i + j);
    }
    return 0;
  }
  
  public ColorStateList getBackgroundTint()
  {
    return this.materialShapeDrawable.getTintList();
  }
  
  public Behavior getBehavior()
  {
    if (this.behavior == null) {
      this.behavior = new Behavior();
    }
    return this.behavior;
  }
  
  public float getCradleVerticalOffset()
  {
    return getTopEdgeTreatment().getCradleVerticalOffset();
  }
  
  public int getFabAlignmentMode()
  {
    return this.fabAlignmentMode;
  }
  
  public int getFabAnimationMode()
  {
    return this.fabAnimationMode;
  }
  
  public float getFabCradleMargin()
  {
    return getTopEdgeTreatment().getFabCradleMargin();
  }
  
  public float getFabCradleRoundedCornerRadius()
  {
    return getTopEdgeTreatment().getFabCradleRoundedCornerRadius();
  }
  
  public boolean getHideOnScroll()
  {
    return this.hideOnScroll;
  }
  
  public boolean isScrolledDown()
  {
    return getBehavior().isScrolledDown();
  }
  
  public boolean isScrolledUp()
  {
    return getBehavior().isScrolledUp();
  }
  
  protected void onAttachedToWindow()
  {
    super.onAttachedToWindow();
    MaterialShapeUtils.setParentAbsoluteElevation(this, this.materialShapeDrawable);
    if ((getParent() instanceof ViewGroup)) {
      ((ViewGroup)getParent()).setClipChildren(false);
    }
  }
  
  protected void onLayout(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    super.onLayout(paramBoolean, paramInt1, paramInt2, paramInt3, paramInt4);
    if (paramBoolean)
    {
      cancelAnimations();
      setCutoutState();
    }
    setActionMenuViewPosition();
  }
  
  protected void onRestoreInstanceState(Parcelable paramParcelable)
  {
    if (!(paramParcelable instanceof SavedState))
    {
      super.onRestoreInstanceState(paramParcelable);
      return;
    }
    paramParcelable = (SavedState)paramParcelable;
    super.onRestoreInstanceState(paramParcelable.getSuperState());
    this.fabAlignmentMode = paramParcelable.fabAlignmentMode;
    this.fabAttached = paramParcelable.fabAttached;
  }
  
  protected Parcelable onSaveInstanceState()
  {
    SavedState localSavedState = new SavedState(super.onSaveInstanceState());
    localSavedState.fabAlignmentMode = this.fabAlignmentMode;
    localSavedState.fabAttached = this.fabAttached;
    return localSavedState;
  }
  
  public void performHide()
  {
    performHide(true);
  }
  
  public void performHide(boolean paramBoolean)
  {
    getBehavior().slideDown(this, paramBoolean);
  }
  
  public void performShow()
  {
    performShow(true);
  }
  
  public void performShow(boolean paramBoolean)
  {
    getBehavior().slideUp(this, paramBoolean);
  }
  
  void removeAnimationListener(AnimationListener paramAnimationListener)
  {
    ArrayList localArrayList = this.animationListeners;
    if (localArrayList == null) {
      return;
    }
    localArrayList.remove(paramAnimationListener);
  }
  
  public void replaceMenu(int paramInt)
  {
    if (paramInt != 0)
    {
      this.pendingMenuResId = 0;
      getMenu().clear();
      inflateMenu(paramInt);
    }
  }
  
  public void setBackgroundTint(ColorStateList paramColorStateList)
  {
    DrawableCompat.setTintList(this.materialShapeDrawable, paramColorStateList);
  }
  
  public void setCradleVerticalOffset(float paramFloat)
  {
    if (paramFloat != getCradleVerticalOffset())
    {
      getTopEdgeTreatment().setCradleVerticalOffset(paramFloat);
      this.materialShapeDrawable.invalidateSelf();
      setCutoutState();
    }
  }
  
  public void setElevation(float paramFloat)
  {
    this.materialShapeDrawable.setElevation(paramFloat);
    int i = this.materialShapeDrawable.getShadowRadius();
    int j = this.materialShapeDrawable.getShadowOffsetY();
    getBehavior().setAdditionalHiddenOffsetY(this, i - j);
  }
  
  public void setFabAlignmentMode(int paramInt)
  {
    setFabAlignmentModeAndReplaceMenu(paramInt, 0);
  }
  
  public void setFabAlignmentModeAndReplaceMenu(int paramInt1, int paramInt2)
  {
    this.pendingMenuResId = paramInt2;
    this.menuAnimatingWithFabAlignmentMode = true;
    maybeAnimateMenuView(paramInt1, this.fabAttached);
    maybeAnimateModeChange(paramInt1);
    this.fabAlignmentMode = paramInt1;
  }
  
  public void setFabAnimationMode(int paramInt)
  {
    this.fabAnimationMode = paramInt;
  }
  
  void setFabCornerSize(float paramFloat)
  {
    if (paramFloat != getTopEdgeTreatment().getFabCornerRadius())
    {
      getTopEdgeTreatment().setFabCornerSize(paramFloat);
      this.materialShapeDrawable.invalidateSelf();
    }
  }
  
  public void setFabCradleMargin(float paramFloat)
  {
    if (paramFloat != getFabCradleMargin())
    {
      getTopEdgeTreatment().setFabCradleMargin(paramFloat);
      this.materialShapeDrawable.invalidateSelf();
    }
  }
  
  public void setFabCradleRoundedCornerRadius(float paramFloat)
  {
    if (paramFloat != getFabCradleRoundedCornerRadius())
    {
      getTopEdgeTreatment().setFabCradleRoundedCornerRadius(paramFloat);
      this.materialShapeDrawable.invalidateSelf();
    }
  }
  
  boolean setFabDiameter(int paramInt)
  {
    if (paramInt != getTopEdgeTreatment().getFabDiameter())
    {
      getTopEdgeTreatment().setFabDiameter(paramInt);
      this.materialShapeDrawable.invalidateSelf();
      return true;
    }
    return false;
  }
  
  public void setHideOnScroll(boolean paramBoolean)
  {
    this.hideOnScroll = paramBoolean;
  }
  
  public void setNavigationIcon(Drawable paramDrawable)
  {
    super.setNavigationIcon(maybeTintNavigationIcon(paramDrawable));
  }
  
  public void setNavigationIconTint(int paramInt)
  {
    this.navigationIconTint = Integer.valueOf(paramInt);
    Drawable localDrawable = getNavigationIcon();
    if (localDrawable != null) {
      setNavigationIcon(localDrawable);
    }
  }
  
  public void setSubtitle(CharSequence paramCharSequence) {}
  
  public void setTitle(CharSequence paramCharSequence) {}
  
  static abstract interface AnimationListener
  {
    public abstract void onAnimationEnd(BottomAppBar paramBottomAppBar);
    
    public abstract void onAnimationStart(BottomAppBar paramBottomAppBar);
  }
  
  public static class Behavior
    extends HideBottomViewOnScrollBehavior<BottomAppBar>
  {
    private final Rect fabContentRect = new Rect();
    private final View.OnLayoutChangeListener fabLayoutListener = new View.OnLayoutChangeListener()
    {
      public void onLayoutChange(View paramAnonymousView, int paramAnonymousInt1, int paramAnonymousInt2, int paramAnonymousInt3, int paramAnonymousInt4, int paramAnonymousInt5, int paramAnonymousInt6, int paramAnonymousInt7, int paramAnonymousInt8)
      {
        BottomAppBar localBottomAppBar = (BottomAppBar)BottomAppBar.Behavior.this.viewRef.get();
        if ((localBottomAppBar != null) && ((paramAnonymousView instanceof FloatingActionButton)))
        {
          FloatingActionButton localFloatingActionButton = (FloatingActionButton)paramAnonymousView;
          localFloatingActionButton.getMeasuredContentRect(BottomAppBar.Behavior.this.fabContentRect);
          paramAnonymousInt1 = BottomAppBar.Behavior.this.fabContentRect.height();
          localBottomAppBar.setFabDiameter(paramAnonymousInt1);
          localBottomAppBar.setFabCornerSize(localFloatingActionButton.getShapeAppearanceModel().getTopLeftCornerSize().getCornerSize(new RectF(BottomAppBar.Behavior.this.fabContentRect)));
          paramAnonymousView = (CoordinatorLayout.LayoutParams)paramAnonymousView.getLayoutParams();
          if (BottomAppBar.Behavior.this.originalBottomMargin == 0)
          {
            paramAnonymousInt2 = (localFloatingActionButton.getMeasuredHeight() - paramAnonymousInt1) / 2;
            paramAnonymousInt1 = localBottomAppBar.getResources().getDimensionPixelOffset(R.dimen.mtrl_bottomappbar_fab_bottom_margin);
            paramAnonymousView.bottomMargin = (localBottomAppBar.getBottomInset() + (paramAnonymousInt1 - paramAnonymousInt2));
            paramAnonymousView.leftMargin = localBottomAppBar.getLeftInset();
            paramAnonymousView.rightMargin = localBottomAppBar.getRightInset();
            if (ViewUtils.isLayoutRtl(localFloatingActionButton)) {
              paramAnonymousView.leftMargin += localBottomAppBar.fabOffsetEndMode;
            } else {
              paramAnonymousView.rightMargin += localBottomAppBar.fabOffsetEndMode;
            }
          }
          return;
        }
        paramAnonymousView.removeOnLayoutChangeListener(this);
      }
    };
    private int originalBottomMargin;
    private WeakReference<BottomAppBar> viewRef;
    
    public Behavior() {}
    
    public Behavior(Context paramContext, AttributeSet paramAttributeSet)
    {
      super(paramAttributeSet);
    }
    
    public boolean onLayoutChild(CoordinatorLayout paramCoordinatorLayout, BottomAppBar paramBottomAppBar, int paramInt)
    {
      this.viewRef = new WeakReference(paramBottomAppBar);
      Object localObject = paramBottomAppBar.findDependentView();
      if ((localObject != null) && (!ViewCompat.isLaidOut((View)localObject)))
      {
        CoordinatorLayout.LayoutParams localLayoutParams = (CoordinatorLayout.LayoutParams)((View)localObject).getLayoutParams();
        localLayoutParams.anchorGravity = 49;
        this.originalBottomMargin = localLayoutParams.bottomMargin;
        if ((localObject instanceof FloatingActionButton))
        {
          localObject = (FloatingActionButton)localObject;
          if (((FloatingActionButton)localObject).getShowMotionSpec() == null) {
            ((FloatingActionButton)localObject).setShowMotionSpecResource(R.animator.mtrl_fab_show_motion_spec);
          }
          if (((FloatingActionButton)localObject).getHideMotionSpec() == null) {
            ((FloatingActionButton)localObject).setHideMotionSpecResource(R.animator.mtrl_fab_hide_motion_spec);
          }
          ((FloatingActionButton)localObject).addOnLayoutChangeListener(this.fabLayoutListener);
          paramBottomAppBar.addFabAnimationListeners((FloatingActionButton)localObject);
        }
        paramBottomAppBar.setCutoutState();
      }
      paramCoordinatorLayout.onLayoutChild(paramBottomAppBar, paramInt);
      return super.onLayoutChild(paramCoordinatorLayout, paramBottomAppBar, paramInt);
    }
    
    public boolean onStartNestedScroll(CoordinatorLayout paramCoordinatorLayout, BottomAppBar paramBottomAppBar, View paramView1, View paramView2, int paramInt1, int paramInt2)
    {
      boolean bool;
      if ((paramBottomAppBar.getHideOnScroll()) && (super.onStartNestedScroll(paramCoordinatorLayout, paramBottomAppBar, paramView1, paramView2, paramInt1, paramInt2))) {
        bool = true;
      } else {
        bool = false;
      }
      return bool;
    }
  }
  
  @Retention(RetentionPolicy.SOURCE)
  public static @interface FabAlignmentMode {}
  
  @Retention(RetentionPolicy.SOURCE)
  public static @interface FabAnimationMode {}
  
  static class SavedState
    extends AbsSavedState
  {
    public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.ClassLoaderCreator()
    {
      public BottomAppBar.SavedState createFromParcel(Parcel paramAnonymousParcel)
      {
        return new BottomAppBar.SavedState(paramAnonymousParcel, null);
      }
      
      public BottomAppBar.SavedState createFromParcel(Parcel paramAnonymousParcel, ClassLoader paramAnonymousClassLoader)
      {
        return new BottomAppBar.SavedState(paramAnonymousParcel, paramAnonymousClassLoader);
      }
      
      public BottomAppBar.SavedState[] newArray(int paramAnonymousInt)
      {
        return new BottomAppBar.SavedState[paramAnonymousInt];
      }
    };
    int fabAlignmentMode;
    boolean fabAttached;
    
    public SavedState(Parcel paramParcel, ClassLoader paramClassLoader)
    {
      super(paramClassLoader);
      this.fabAlignmentMode = paramParcel.readInt();
      boolean bool;
      if (paramParcel.readInt() != 0) {
        bool = true;
      } else {
        bool = false;
      }
      this.fabAttached = bool;
    }
    
    public SavedState(Parcelable paramParcelable)
    {
      super();
    }
    
    public void writeToParcel(Parcel paramParcel, int paramInt)
    {
      super.writeToParcel(paramParcel, paramInt);
      paramParcel.writeInt(this.fabAlignmentMode);
      paramParcel.writeInt(this.fabAttached);
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/com/google/android/material/bottomappbar/BottomAppBar.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */