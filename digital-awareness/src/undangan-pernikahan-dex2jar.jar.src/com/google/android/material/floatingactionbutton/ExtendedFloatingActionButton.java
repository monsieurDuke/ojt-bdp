package com.google.android.material.floatingactionbutton;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.PropertyValuesHolder;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Property;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.coordinatorlayout.widget.CoordinatorLayout.AttachedBehavior;
import androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior;
import androidx.coordinatorlayout.widget.CoordinatorLayout.LayoutParams;
import androidx.core.view.ViewCompat;
import com.google.android.material.R.animator;
import com.google.android.material.R.attr;
import com.google.android.material.R.style;
import com.google.android.material.R.styleable;
import com.google.android.material.animation.MotionSpec;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.internal.DescendantOffsetUtils;
import com.google.android.material.internal.ThemeEnforcement;
import com.google.android.material.shape.ShapeAppearanceModel;
import com.google.android.material.shape.ShapeAppearanceModel.Builder;
import com.google.android.material.theme.overlay.MaterialThemeOverlay;
import java.util.Iterator;
import java.util.List;

public class ExtendedFloatingActionButton
  extends MaterialButton
  implements CoordinatorLayout.AttachedBehavior
{
  private static final int ANIM_STATE_HIDING = 1;
  private static final int ANIM_STATE_NONE = 0;
  private static final int ANIM_STATE_SHOWING = 2;
  private static final int DEF_STYLE_RES = R.style.Widget_MaterialComponents_ExtendedFloatingActionButton_Icon;
  static final Property<View, Float> HEIGHT;
  static final Property<View, Float> PADDING_END = new Property(Float.class, "paddingEnd")
  {
    public Float get(View paramAnonymousView)
    {
      return Float.valueOf(ViewCompat.getPaddingEnd(paramAnonymousView));
    }
    
    public void set(View paramAnonymousView, Float paramAnonymousFloat)
    {
      ViewCompat.setPaddingRelative(paramAnonymousView, ViewCompat.getPaddingStart(paramAnonymousView), paramAnonymousView.getPaddingTop(), paramAnonymousFloat.intValue(), paramAnonymousView.getPaddingBottom());
    }
  };
  static final Property<View, Float> PADDING_START;
  static final Property<View, Float> WIDTH = new Property(Float.class, "width")
  {
    public Float get(View paramAnonymousView)
    {
      return Float.valueOf(paramAnonymousView.getLayoutParams().width);
    }
    
    public void set(View paramAnonymousView, Float paramAnonymousFloat)
    {
      paramAnonymousView.getLayoutParams().width = paramAnonymousFloat.intValue();
      paramAnonymousView.requestLayout();
    }
  };
  private int animState = 0;
  private boolean animateShowBeforeLayout;
  private final CoordinatorLayout.Behavior<ExtendedFloatingActionButton> behavior;
  private final AnimatorTracker changeVisibilityTracker;
  private final int collapsedSize;
  private final MotionStrategy extendStrategy;
  private int extendedPaddingEnd;
  private int extendedPaddingStart;
  private final MotionStrategy hideStrategy;
  private boolean isExtended;
  private boolean isTransforming;
  protected ColorStateList originalTextCsl;
  private final MotionStrategy showStrategy;
  private final MotionStrategy shrinkStrategy;
  
  static
  {
    HEIGHT = new Property(Float.class, "height")
    {
      public Float get(View paramAnonymousView)
      {
        return Float.valueOf(paramAnonymousView.getLayoutParams().height);
      }
      
      public void set(View paramAnonymousView, Float paramAnonymousFloat)
      {
        paramAnonymousView.getLayoutParams().height = paramAnonymousFloat.intValue();
        paramAnonymousView.requestLayout();
      }
    };
    PADDING_START = new Property(Float.class, "paddingStart")
    {
      public Float get(View paramAnonymousView)
      {
        return Float.valueOf(ViewCompat.getPaddingStart(paramAnonymousView));
      }
      
      public void set(View paramAnonymousView, Float paramAnonymousFloat)
      {
        ViewCompat.setPaddingRelative(paramAnonymousView, paramAnonymousFloat.intValue(), paramAnonymousView.getPaddingTop(), ViewCompat.getPaddingEnd(paramAnonymousView), paramAnonymousView.getPaddingBottom());
      }
    };
  }
  
  public ExtendedFloatingActionButton(Context paramContext)
  {
    this(paramContext, null);
  }
  
  public ExtendedFloatingActionButton(Context paramContext, AttributeSet paramAttributeSet)
  {
    this(paramContext, paramAttributeSet, R.attr.extendedFloatingActionButtonStyle);
  }
  
  public ExtendedFloatingActionButton(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(MaterialThemeOverlay.wrap(paramContext, paramAttributeSet, paramInt, i), paramAttributeSet, paramInt);
    Object localObject1 = new AnimatorTracker();
    this.changeVisibilityTracker = ((AnimatorTracker)localObject1);
    paramContext = new ShowStrategy((AnimatorTracker)localObject1);
    this.showStrategy = paramContext;
    localObject1 = new HideStrategy((AnimatorTracker)localObject1);
    this.hideStrategy = ((MotionStrategy)localObject1);
    this.isExtended = true;
    this.isTransforming = false;
    this.animateShowBeforeLayout = false;
    Context localContext = getContext();
    this.behavior = new ExtendedFloatingActionButtonBehavior(localContext, paramAttributeSet);
    TypedArray localTypedArray = ThemeEnforcement.obtainStyledAttributes(localContext, paramAttributeSet, R.styleable.ExtendedFloatingActionButton, paramInt, i, new int[0]);
    MotionSpec localMotionSpec1 = MotionSpec.createFromAttribute(localContext, localTypedArray, R.styleable.ExtendedFloatingActionButton_showMotionSpec);
    MotionSpec localMotionSpec3 = MotionSpec.createFromAttribute(localContext, localTypedArray, R.styleable.ExtendedFloatingActionButton_hideMotionSpec);
    MotionSpec localMotionSpec2 = MotionSpec.createFromAttribute(localContext, localTypedArray, R.styleable.ExtendedFloatingActionButton_extendMotionSpec);
    MotionSpec localMotionSpec4 = MotionSpec.createFromAttribute(localContext, localTypedArray, R.styleable.ExtendedFloatingActionButton_shrinkMotionSpec);
    this.collapsedSize = localTypedArray.getDimensionPixelSize(R.styleable.ExtendedFloatingActionButton_collapsedSize, -1);
    this.extendedPaddingStart = ViewCompat.getPaddingStart(this);
    this.extendedPaddingEnd = ViewCompat.getPaddingEnd(this);
    Object localObject2 = new AnimatorTracker();
    ChangeSizeStrategy localChangeSizeStrategy = new ChangeSizeStrategy((AnimatorTracker)localObject2, new Size()
    {
      public int getHeight()
      {
        return ExtendedFloatingActionButton.this.getMeasuredHeight();
      }
      
      public ViewGroup.LayoutParams getLayoutParams()
      {
        return new ViewGroup.LayoutParams(-2, -2);
      }
      
      public int getPaddingEnd()
      {
        return ExtendedFloatingActionButton.this.extendedPaddingEnd;
      }
      
      public int getPaddingStart()
      {
        return ExtendedFloatingActionButton.this.extendedPaddingStart;
      }
      
      public int getWidth()
      {
        return ExtendedFloatingActionButton.this.getMeasuredWidth() - ExtendedFloatingActionButton.this.getCollapsedPadding() * 2 + ExtendedFloatingActionButton.this.extendedPaddingStart + ExtendedFloatingActionButton.this.extendedPaddingEnd;
      }
    }, true);
    this.extendStrategy = localChangeSizeStrategy;
    localObject2 = new ChangeSizeStrategy((AnimatorTracker)localObject2, new Size()
    {
      public int getHeight()
      {
        return ExtendedFloatingActionButton.this.getCollapsedSize();
      }
      
      public ViewGroup.LayoutParams getLayoutParams()
      {
        return new ViewGroup.LayoutParams(getWidth(), getHeight());
      }
      
      public int getPaddingEnd()
      {
        return ExtendedFloatingActionButton.this.getCollapsedPadding();
      }
      
      public int getPaddingStart()
      {
        return ExtendedFloatingActionButton.this.getCollapsedPadding();
      }
      
      public int getWidth()
      {
        return ExtendedFloatingActionButton.this.getCollapsedSize();
      }
    }, false);
    this.shrinkStrategy = ((MotionStrategy)localObject2);
    paramContext.setMotionSpec(localMotionSpec1);
    ((MotionStrategy)localObject1).setMotionSpec(localMotionSpec3);
    localChangeSizeStrategy.setMotionSpec(localMotionSpec2);
    ((MotionStrategy)localObject2).setMotionSpec(localMotionSpec4);
    localTypedArray.recycle();
    setShapeAppearanceModel(ShapeAppearanceModel.builder(localContext, paramAttributeSet, paramInt, i, ShapeAppearanceModel.PILL).build());
    saveOriginalTextCsl();
  }
  
  private boolean isOrWillBeHidden()
  {
    int i = getVisibility();
    boolean bool2 = false;
    boolean bool1 = false;
    if (i == 0)
    {
      if (this.animState == 1) {
        bool1 = true;
      }
      return bool1;
    }
    bool1 = bool2;
    if (this.animState != 2) {
      bool1 = true;
    }
    return bool1;
  }
  
  private boolean isOrWillBeShown()
  {
    int i = getVisibility();
    boolean bool1 = false;
    boolean bool2 = false;
    if (i != 0)
    {
      bool1 = bool2;
      if (this.animState == 2) {
        bool1 = true;
      }
      return bool1;
    }
    if (this.animState != 1) {
      bool1 = true;
    }
    return bool1;
  }
  
  private void performMotion(final MotionStrategy paramMotionStrategy, final OnChangedCallback paramOnChangedCallback)
  {
    if (paramMotionStrategy.shouldCancel()) {
      return;
    }
    if (!shouldAnimateVisibilityChange())
    {
      paramMotionStrategy.performNow();
      paramMotionStrategy.onChange(paramOnChangedCallback);
      return;
    }
    measure(0, 0);
    AnimatorSet localAnimatorSet = paramMotionStrategy.createAnimator();
    localAnimatorSet.addListener(new AnimatorListenerAdapter()
    {
      private boolean cancelled;
      
      public void onAnimationCancel(Animator paramAnonymousAnimator)
      {
        this.cancelled = true;
        paramMotionStrategy.onAnimationCancel();
      }
      
      public void onAnimationEnd(Animator paramAnonymousAnimator)
      {
        paramMotionStrategy.onAnimationEnd();
        if (!this.cancelled) {
          paramMotionStrategy.onChange(paramOnChangedCallback);
        }
      }
      
      public void onAnimationStart(Animator paramAnonymousAnimator)
      {
        paramMotionStrategy.onAnimationStart(paramAnonymousAnimator);
        this.cancelled = false;
      }
    });
    paramMotionStrategy = paramMotionStrategy.getListeners().iterator();
    while (paramMotionStrategy.hasNext()) {
      localAnimatorSet.addListener((Animator.AnimatorListener)paramMotionStrategy.next());
    }
    localAnimatorSet.start();
  }
  
  private void saveOriginalTextCsl()
  {
    this.originalTextCsl = getTextColors();
  }
  
  private boolean shouldAnimateVisibilityChange()
  {
    boolean bool;
    if (((ViewCompat.isLaidOut(this)) || ((!isOrWillBeShown()) && (this.animateShowBeforeLayout))) && (!isInEditMode())) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public void addOnExtendAnimationListener(Animator.AnimatorListener paramAnimatorListener)
  {
    this.extendStrategy.addAnimationListener(paramAnimatorListener);
  }
  
  public void addOnHideAnimationListener(Animator.AnimatorListener paramAnimatorListener)
  {
    this.hideStrategy.addAnimationListener(paramAnimatorListener);
  }
  
  public void addOnShowAnimationListener(Animator.AnimatorListener paramAnimatorListener)
  {
    this.showStrategy.addAnimationListener(paramAnimatorListener);
  }
  
  public void addOnShrinkAnimationListener(Animator.AnimatorListener paramAnimatorListener)
  {
    this.shrinkStrategy.addAnimationListener(paramAnimatorListener);
  }
  
  public void extend()
  {
    performMotion(this.extendStrategy, null);
  }
  
  public void extend(OnChangedCallback paramOnChangedCallback)
  {
    performMotion(this.extendStrategy, paramOnChangedCallback);
  }
  
  public CoordinatorLayout.Behavior<ExtendedFloatingActionButton> getBehavior()
  {
    return this.behavior;
  }
  
  int getCollapsedPadding()
  {
    return (getCollapsedSize() - getIconSize()) / 2;
  }
  
  int getCollapsedSize()
  {
    int i = this.collapsedSize;
    if (i < 0) {
      i = Math.min(ViewCompat.getPaddingStart(this), ViewCompat.getPaddingEnd(this)) * 2 + getIconSize();
    }
    return i;
  }
  
  public MotionSpec getExtendMotionSpec()
  {
    return this.extendStrategy.getMotionSpec();
  }
  
  public MotionSpec getHideMotionSpec()
  {
    return this.hideStrategy.getMotionSpec();
  }
  
  public MotionSpec getShowMotionSpec()
  {
    return this.showStrategy.getMotionSpec();
  }
  
  public MotionSpec getShrinkMotionSpec()
  {
    return this.shrinkStrategy.getMotionSpec();
  }
  
  public void hide()
  {
    performMotion(this.hideStrategy, null);
  }
  
  public void hide(OnChangedCallback paramOnChangedCallback)
  {
    performMotion(this.hideStrategy, paramOnChangedCallback);
  }
  
  public final boolean isExtended()
  {
    return this.isExtended;
  }
  
  protected void onAttachedToWindow()
  {
    super.onAttachedToWindow();
    if ((this.isExtended) && (TextUtils.isEmpty(getText())) && (getIcon() != null))
    {
      this.isExtended = false;
      this.shrinkStrategy.performNow();
    }
  }
  
  public void removeOnExtendAnimationListener(Animator.AnimatorListener paramAnimatorListener)
  {
    this.extendStrategy.removeAnimationListener(paramAnimatorListener);
  }
  
  public void removeOnHideAnimationListener(Animator.AnimatorListener paramAnimatorListener)
  {
    this.hideStrategy.removeAnimationListener(paramAnimatorListener);
  }
  
  public void removeOnShowAnimationListener(Animator.AnimatorListener paramAnimatorListener)
  {
    this.showStrategy.removeAnimationListener(paramAnimatorListener);
  }
  
  public void removeOnShrinkAnimationListener(Animator.AnimatorListener paramAnimatorListener)
  {
    this.shrinkStrategy.removeAnimationListener(paramAnimatorListener);
  }
  
  public void setAnimateShowBeforeLayout(boolean paramBoolean)
  {
    this.animateShowBeforeLayout = paramBoolean;
  }
  
  public void setExtendMotionSpec(MotionSpec paramMotionSpec)
  {
    this.extendStrategy.setMotionSpec(paramMotionSpec);
  }
  
  public void setExtendMotionSpecResource(int paramInt)
  {
    setExtendMotionSpec(MotionSpec.createFromResource(getContext(), paramInt));
  }
  
  public void setExtended(boolean paramBoolean)
  {
    if (this.isExtended == paramBoolean) {
      return;
    }
    MotionStrategy localMotionStrategy;
    if (paramBoolean) {
      localMotionStrategy = this.extendStrategy;
    } else {
      localMotionStrategy = this.shrinkStrategy;
    }
    if (localMotionStrategy.shouldCancel()) {
      return;
    }
    localMotionStrategy.performNow();
  }
  
  public void setHideMotionSpec(MotionSpec paramMotionSpec)
  {
    this.hideStrategy.setMotionSpec(paramMotionSpec);
  }
  
  public void setHideMotionSpecResource(int paramInt)
  {
    setHideMotionSpec(MotionSpec.createFromResource(getContext(), paramInt));
  }
  
  public void setPadding(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    super.setPadding(paramInt1, paramInt2, paramInt3, paramInt4);
    if ((this.isExtended) && (!this.isTransforming))
    {
      this.extendedPaddingStart = ViewCompat.getPaddingStart(this);
      this.extendedPaddingEnd = ViewCompat.getPaddingEnd(this);
    }
  }
  
  public void setPaddingRelative(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    super.setPaddingRelative(paramInt1, paramInt2, paramInt3, paramInt4);
    if ((this.isExtended) && (!this.isTransforming))
    {
      this.extendedPaddingStart = paramInt1;
      this.extendedPaddingEnd = paramInt3;
    }
  }
  
  public void setShowMotionSpec(MotionSpec paramMotionSpec)
  {
    this.showStrategy.setMotionSpec(paramMotionSpec);
  }
  
  public void setShowMotionSpecResource(int paramInt)
  {
    setShowMotionSpec(MotionSpec.createFromResource(getContext(), paramInt));
  }
  
  public void setShrinkMotionSpec(MotionSpec paramMotionSpec)
  {
    this.shrinkStrategy.setMotionSpec(paramMotionSpec);
  }
  
  public void setShrinkMotionSpecResource(int paramInt)
  {
    setShrinkMotionSpec(MotionSpec.createFromResource(getContext(), paramInt));
  }
  
  public void setTextColor(int paramInt)
  {
    super.setTextColor(paramInt);
    saveOriginalTextCsl();
  }
  
  public void setTextColor(ColorStateList paramColorStateList)
  {
    super.setTextColor(paramColorStateList);
    saveOriginalTextCsl();
  }
  
  public void show()
  {
    performMotion(this.showStrategy, null);
  }
  
  public void show(OnChangedCallback paramOnChangedCallback)
  {
    performMotion(this.showStrategy, paramOnChangedCallback);
  }
  
  public void shrink()
  {
    performMotion(this.shrinkStrategy, null);
  }
  
  public void shrink(OnChangedCallback paramOnChangedCallback)
  {
    performMotion(this.shrinkStrategy, paramOnChangedCallback);
  }
  
  protected void silentlyUpdateTextColor(ColorStateList paramColorStateList)
  {
    super.setTextColor(paramColorStateList);
  }
  
  class ChangeSizeStrategy
    extends BaseMotionStrategy
  {
    private final boolean extending;
    private final ExtendedFloatingActionButton.Size size;
    
    ChangeSizeStrategy(AnimatorTracker paramAnimatorTracker, ExtendedFloatingActionButton.Size paramSize, boolean paramBoolean)
    {
      super(paramAnimatorTracker);
      this.size = paramSize;
      this.extending = paramBoolean;
    }
    
    public AnimatorSet createAnimator()
    {
      MotionSpec localMotionSpec = getCurrentMotionSpec();
      PropertyValuesHolder[] arrayOfPropertyValuesHolder;
      if (localMotionSpec.hasPropertyValues("width"))
      {
        arrayOfPropertyValuesHolder = localMotionSpec.getPropertyValues("width");
        arrayOfPropertyValuesHolder[0].setFloatValues(new float[] { ExtendedFloatingActionButton.this.getWidth(), this.size.getWidth() });
        localMotionSpec.setPropertyValues("width", arrayOfPropertyValuesHolder);
      }
      if (localMotionSpec.hasPropertyValues("height"))
      {
        arrayOfPropertyValuesHolder = localMotionSpec.getPropertyValues("height");
        arrayOfPropertyValuesHolder[0].setFloatValues(new float[] { ExtendedFloatingActionButton.this.getHeight(), this.size.getHeight() });
        localMotionSpec.setPropertyValues("height", arrayOfPropertyValuesHolder);
      }
      if (localMotionSpec.hasPropertyValues("paddingStart"))
      {
        arrayOfPropertyValuesHolder = localMotionSpec.getPropertyValues("paddingStart");
        arrayOfPropertyValuesHolder[0].setFloatValues(new float[] { ViewCompat.getPaddingStart(ExtendedFloatingActionButton.this), this.size.getPaddingStart() });
        localMotionSpec.setPropertyValues("paddingStart", arrayOfPropertyValuesHolder);
      }
      if (localMotionSpec.hasPropertyValues("paddingEnd"))
      {
        arrayOfPropertyValuesHolder = localMotionSpec.getPropertyValues("paddingEnd");
        arrayOfPropertyValuesHolder[0].setFloatValues(new float[] { ViewCompat.getPaddingEnd(ExtendedFloatingActionButton.this), this.size.getPaddingEnd() });
        localMotionSpec.setPropertyValues("paddingEnd", arrayOfPropertyValuesHolder);
      }
      if (localMotionSpec.hasPropertyValues("labelOpacity"))
      {
        arrayOfPropertyValuesHolder = localMotionSpec.getPropertyValues("labelOpacity");
        boolean bool = this.extending;
        float f2 = 0.0F;
        float f1;
        if (bool) {
          f1 = 0.0F;
        } else {
          f1 = 1.0F;
        }
        if (bool) {
          f2 = 1.0F;
        }
        arrayOfPropertyValuesHolder[0].setFloatValues(new float[] { f1, f2 });
        localMotionSpec.setPropertyValues("labelOpacity", arrayOfPropertyValuesHolder);
      }
      return super.createAnimator(localMotionSpec);
    }
    
    public int getDefaultMotionSpecResource()
    {
      int i;
      if (this.extending) {
        i = R.animator.mtrl_extended_fab_change_size_expand_motion_spec;
      } else {
        i = R.animator.mtrl_extended_fab_change_size_collapse_motion_spec;
      }
      return i;
    }
    
    public void onAnimationEnd()
    {
      super.onAnimationEnd();
      ExtendedFloatingActionButton.access$802(ExtendedFloatingActionButton.this, false);
      ExtendedFloatingActionButton.this.setHorizontallyScrolling(false);
      ViewGroup.LayoutParams localLayoutParams = ExtendedFloatingActionButton.this.getLayoutParams();
      if (localLayoutParams == null) {
        return;
      }
      localLayoutParams.width = this.size.getLayoutParams().width;
      localLayoutParams.height = this.size.getLayoutParams().height;
    }
    
    public void onAnimationStart(Animator paramAnimator)
    {
      super.onAnimationStart(paramAnimator);
      ExtendedFloatingActionButton.access$702(ExtendedFloatingActionButton.this, this.extending);
      ExtendedFloatingActionButton.access$802(ExtendedFloatingActionButton.this, true);
      ExtendedFloatingActionButton.this.setHorizontallyScrolling(true);
    }
    
    public void onChange(ExtendedFloatingActionButton.OnChangedCallback paramOnChangedCallback)
    {
      if (paramOnChangedCallback == null) {
        return;
      }
      if (this.extending) {
        paramOnChangedCallback.onExtended(ExtendedFloatingActionButton.this);
      } else {
        paramOnChangedCallback.onShrunken(ExtendedFloatingActionButton.this);
      }
    }
    
    public void performNow()
    {
      ExtendedFloatingActionButton.access$702(ExtendedFloatingActionButton.this, this.extending);
      ViewGroup.LayoutParams localLayoutParams = ExtendedFloatingActionButton.this.getLayoutParams();
      if (localLayoutParams == null) {
        return;
      }
      localLayoutParams.width = this.size.getLayoutParams().width;
      localLayoutParams.height = this.size.getLayoutParams().height;
      ViewCompat.setPaddingRelative(ExtendedFloatingActionButton.this, this.size.getPaddingStart(), ExtendedFloatingActionButton.this.getPaddingTop(), this.size.getPaddingEnd(), ExtendedFloatingActionButton.this.getPaddingBottom());
      ExtendedFloatingActionButton.this.requestLayout();
    }
    
    public boolean shouldCancel()
    {
      boolean bool;
      if ((this.extending != ExtendedFloatingActionButton.this.isExtended) && (ExtendedFloatingActionButton.this.getIcon() != null) && (!TextUtils.isEmpty(ExtendedFloatingActionButton.this.getText()))) {
        bool = false;
      } else {
        bool = true;
      }
      return bool;
    }
  }
  
  protected static class ExtendedFloatingActionButtonBehavior<T extends ExtendedFloatingActionButton>
    extends CoordinatorLayout.Behavior<T>
  {
    private static final boolean AUTO_HIDE_DEFAULT = false;
    private static final boolean AUTO_SHRINK_DEFAULT = true;
    private boolean autoHideEnabled;
    private boolean autoShrinkEnabled;
    private ExtendedFloatingActionButton.OnChangedCallback internalAutoHideCallback;
    private ExtendedFloatingActionButton.OnChangedCallback internalAutoShrinkCallback;
    private Rect tmpRect;
    
    public ExtendedFloatingActionButtonBehavior()
    {
      this.autoHideEnabled = false;
      this.autoShrinkEnabled = true;
    }
    
    public ExtendedFloatingActionButtonBehavior(Context paramContext, AttributeSet paramAttributeSet)
    {
      super(paramAttributeSet);
      paramContext = paramContext.obtainStyledAttributes(paramAttributeSet, R.styleable.ExtendedFloatingActionButton_Behavior_Layout);
      this.autoHideEnabled = paramContext.getBoolean(R.styleable.ExtendedFloatingActionButton_Behavior_Layout_behavior_autoHide, false);
      this.autoShrinkEnabled = paramContext.getBoolean(R.styleable.ExtendedFloatingActionButton_Behavior_Layout_behavior_autoShrink, true);
      paramContext.recycle();
    }
    
    private static boolean isBottomSheet(View paramView)
    {
      paramView = paramView.getLayoutParams();
      if ((paramView instanceof CoordinatorLayout.LayoutParams)) {
        return ((CoordinatorLayout.LayoutParams)paramView).getBehavior() instanceof BottomSheetBehavior;
      }
      return false;
    }
    
    private boolean shouldUpdateVisibility(View paramView, ExtendedFloatingActionButton paramExtendedFloatingActionButton)
    {
      paramExtendedFloatingActionButton = (CoordinatorLayout.LayoutParams)paramExtendedFloatingActionButton.getLayoutParams();
      if ((!this.autoHideEnabled) && (!this.autoShrinkEnabled)) {
        return false;
      }
      return paramExtendedFloatingActionButton.getAnchorId() == paramView.getId();
    }
    
    private boolean updateFabVisibilityForAppBarLayout(CoordinatorLayout paramCoordinatorLayout, AppBarLayout paramAppBarLayout, ExtendedFloatingActionButton paramExtendedFloatingActionButton)
    {
      if (!shouldUpdateVisibility(paramAppBarLayout, paramExtendedFloatingActionButton)) {
        return false;
      }
      if (this.tmpRect == null) {
        this.tmpRect = new Rect();
      }
      Rect localRect = this.tmpRect;
      DescendantOffsetUtils.getDescendantRect(paramCoordinatorLayout, paramAppBarLayout, localRect);
      if (localRect.bottom <= paramAppBarLayout.getMinimumHeightForVisibleOverlappingContent()) {
        shrinkOrHide(paramExtendedFloatingActionButton);
      } else {
        extendOrShow(paramExtendedFloatingActionButton);
      }
      return true;
    }
    
    private boolean updateFabVisibilityForBottomSheet(View paramView, ExtendedFloatingActionButton paramExtendedFloatingActionButton)
    {
      if (!shouldUpdateVisibility(paramView, paramExtendedFloatingActionButton)) {
        return false;
      }
      CoordinatorLayout.LayoutParams localLayoutParams = (CoordinatorLayout.LayoutParams)paramExtendedFloatingActionButton.getLayoutParams();
      if (paramView.getTop() < paramExtendedFloatingActionButton.getHeight() / 2 + localLayoutParams.topMargin) {
        shrinkOrHide(paramExtendedFloatingActionButton);
      } else {
        extendOrShow(paramExtendedFloatingActionButton);
      }
      return true;
    }
    
    protected void extendOrShow(ExtendedFloatingActionButton paramExtendedFloatingActionButton)
    {
      boolean bool = this.autoShrinkEnabled;
      ExtendedFloatingActionButton.OnChangedCallback localOnChangedCallback;
      if (bool) {
        localOnChangedCallback = this.internalAutoShrinkCallback;
      } else {
        localOnChangedCallback = this.internalAutoHideCallback;
      }
      MotionStrategy localMotionStrategy;
      if (bool) {
        localMotionStrategy = paramExtendedFloatingActionButton.extendStrategy;
      } else {
        localMotionStrategy = paramExtendedFloatingActionButton.showStrategy;
      }
      paramExtendedFloatingActionButton.performMotion(localMotionStrategy, localOnChangedCallback);
    }
    
    public boolean getInsetDodgeRect(CoordinatorLayout paramCoordinatorLayout, ExtendedFloatingActionButton paramExtendedFloatingActionButton, Rect paramRect)
    {
      return super.getInsetDodgeRect(paramCoordinatorLayout, paramExtendedFloatingActionButton, paramRect);
    }
    
    public boolean isAutoHideEnabled()
    {
      return this.autoHideEnabled;
    }
    
    public boolean isAutoShrinkEnabled()
    {
      return this.autoShrinkEnabled;
    }
    
    public void onAttachedToLayoutParams(CoordinatorLayout.LayoutParams paramLayoutParams)
    {
      if (paramLayoutParams.dodgeInsetEdges == 0) {
        paramLayoutParams.dodgeInsetEdges = 80;
      }
    }
    
    public boolean onDependentViewChanged(CoordinatorLayout paramCoordinatorLayout, ExtendedFloatingActionButton paramExtendedFloatingActionButton, View paramView)
    {
      if ((paramView instanceof AppBarLayout)) {
        updateFabVisibilityForAppBarLayout(paramCoordinatorLayout, (AppBarLayout)paramView, paramExtendedFloatingActionButton);
      } else if (isBottomSheet(paramView)) {
        updateFabVisibilityForBottomSheet(paramView, paramExtendedFloatingActionButton);
      }
      return false;
    }
    
    public boolean onLayoutChild(CoordinatorLayout paramCoordinatorLayout, ExtendedFloatingActionButton paramExtendedFloatingActionButton, int paramInt)
    {
      List localList = paramCoordinatorLayout.getDependencies(paramExtendedFloatingActionButton);
      int i = 0;
      int j = localList.size();
      while (i < j)
      {
        View localView = (View)localList.get(i);
        if ((localView instanceof AppBarLayout) ? !updateFabVisibilityForAppBarLayout(paramCoordinatorLayout, (AppBarLayout)localView, paramExtendedFloatingActionButton) : (isBottomSheet(localView)) && (updateFabVisibilityForBottomSheet(localView, paramExtendedFloatingActionButton))) {
          break;
        }
        i++;
      }
      paramCoordinatorLayout.onLayoutChild(paramExtendedFloatingActionButton, paramInt);
      return true;
    }
    
    public void setAutoHideEnabled(boolean paramBoolean)
    {
      this.autoHideEnabled = paramBoolean;
    }
    
    public void setAutoShrinkEnabled(boolean paramBoolean)
    {
      this.autoShrinkEnabled = paramBoolean;
    }
    
    void setInternalAutoHideCallback(ExtendedFloatingActionButton.OnChangedCallback paramOnChangedCallback)
    {
      this.internalAutoHideCallback = paramOnChangedCallback;
    }
    
    void setInternalAutoShrinkCallback(ExtendedFloatingActionButton.OnChangedCallback paramOnChangedCallback)
    {
      this.internalAutoShrinkCallback = paramOnChangedCallback;
    }
    
    protected void shrinkOrHide(ExtendedFloatingActionButton paramExtendedFloatingActionButton)
    {
      boolean bool = this.autoShrinkEnabled;
      ExtendedFloatingActionButton.OnChangedCallback localOnChangedCallback;
      if (bool) {
        localOnChangedCallback = this.internalAutoShrinkCallback;
      } else {
        localOnChangedCallback = this.internalAutoHideCallback;
      }
      MotionStrategy localMotionStrategy;
      if (bool) {
        localMotionStrategy = paramExtendedFloatingActionButton.shrinkStrategy;
      } else {
        localMotionStrategy = paramExtendedFloatingActionButton.hideStrategy;
      }
      paramExtendedFloatingActionButton.performMotion(localMotionStrategy, localOnChangedCallback);
    }
  }
  
  class HideStrategy
    extends BaseMotionStrategy
  {
    private boolean isCancelled;
    
    public HideStrategy(AnimatorTracker paramAnimatorTracker)
    {
      super(paramAnimatorTracker);
    }
    
    public int getDefaultMotionSpecResource()
    {
      return R.animator.mtrl_extended_fab_hide_motion_spec;
    }
    
    public void onAnimationCancel()
    {
      super.onAnimationCancel();
      this.isCancelled = true;
    }
    
    public void onAnimationEnd()
    {
      super.onAnimationEnd();
      ExtendedFloatingActionButton.access$902(ExtendedFloatingActionButton.this, 0);
      if (!this.isCancelled) {
        ExtendedFloatingActionButton.this.setVisibility(8);
      }
    }
    
    public void onAnimationStart(Animator paramAnimator)
    {
      super.onAnimationStart(paramAnimator);
      this.isCancelled = false;
      ExtendedFloatingActionButton.this.setVisibility(0);
      ExtendedFloatingActionButton.access$902(ExtendedFloatingActionButton.this, 1);
    }
    
    public void onChange(ExtendedFloatingActionButton.OnChangedCallback paramOnChangedCallback)
    {
      if (paramOnChangedCallback != null) {
        paramOnChangedCallback.onHidden(ExtendedFloatingActionButton.this);
      }
    }
    
    public void performNow()
    {
      ExtendedFloatingActionButton.this.setVisibility(8);
    }
    
    public boolean shouldCancel()
    {
      return ExtendedFloatingActionButton.this.isOrWillBeHidden();
    }
  }
  
  public static abstract class OnChangedCallback
  {
    public void onExtended(ExtendedFloatingActionButton paramExtendedFloatingActionButton) {}
    
    public void onHidden(ExtendedFloatingActionButton paramExtendedFloatingActionButton) {}
    
    public void onShown(ExtendedFloatingActionButton paramExtendedFloatingActionButton) {}
    
    public void onShrunken(ExtendedFloatingActionButton paramExtendedFloatingActionButton) {}
  }
  
  class ShowStrategy
    extends BaseMotionStrategy
  {
    public ShowStrategy(AnimatorTracker paramAnimatorTracker)
    {
      super(paramAnimatorTracker);
    }
    
    public int getDefaultMotionSpecResource()
    {
      return R.animator.mtrl_extended_fab_show_motion_spec;
    }
    
    public void onAnimationEnd()
    {
      super.onAnimationEnd();
      ExtendedFloatingActionButton.access$902(ExtendedFloatingActionButton.this, 0);
    }
    
    public void onAnimationStart(Animator paramAnimator)
    {
      super.onAnimationStart(paramAnimator);
      ExtendedFloatingActionButton.this.setVisibility(0);
      ExtendedFloatingActionButton.access$902(ExtendedFloatingActionButton.this, 2);
    }
    
    public void onChange(ExtendedFloatingActionButton.OnChangedCallback paramOnChangedCallback)
    {
      if (paramOnChangedCallback != null) {
        paramOnChangedCallback.onShown(ExtendedFloatingActionButton.this);
      }
    }
    
    public void performNow()
    {
      ExtendedFloatingActionButton.this.setVisibility(0);
      ExtendedFloatingActionButton.this.setAlpha(1.0F);
      ExtendedFloatingActionButton.this.setScaleY(1.0F);
      ExtendedFloatingActionButton.this.setScaleX(1.0F);
    }
    
    public boolean shouldCancel()
    {
      return ExtendedFloatingActionButton.this.isOrWillBeShown();
    }
  }
  
  static abstract interface Size
  {
    public abstract int getHeight();
    
    public abstract ViewGroup.LayoutParams getLayoutParams();
    
    public abstract int getPaddingEnd();
    
    public abstract int getPaddingStart();
    
    public abstract int getWidth();
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/com/google/android/material/floatingactionbutton/ExtendedFloatingActionButton.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */