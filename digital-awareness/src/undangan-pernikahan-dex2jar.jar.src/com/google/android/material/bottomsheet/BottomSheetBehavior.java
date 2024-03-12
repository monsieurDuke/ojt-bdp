package com.google.android.material.bottomsheet;

import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.Resources.Theme;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.ClassLoaderCreator;
import android.os.Parcelable.Creator;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewGroup.MarginLayoutParams;
import android.view.ViewParent;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior;
import androidx.coordinatorlayout.widget.CoordinatorLayout.LayoutParams;
import androidx.core.graphics.Insets;
import androidx.core.math.MathUtils;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.WindowInsetsCompat.Type;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat.AccessibilityActionCompat;
import androidx.core.view.accessibility.AccessibilityViewCommand;
import androidx.core.view.accessibility.AccessibilityViewCommand.CommandArguments;
import androidx.customview.view.AbsSavedState;
import androidx.customview.widget.ViewDragHelper;
import androidx.customview.widget.ViewDragHelper.Callback;
import com.google.android.material.R.attr;
import com.google.android.material.R.dimen;
import com.google.android.material.R.string;
import com.google.android.material.R.style;
import com.google.android.material.R.styleable;
import com.google.android.material.internal.ViewUtils;
import com.google.android.material.internal.ViewUtils.OnApplyWindowInsetsListener;
import com.google.android.material.internal.ViewUtils.RelativePadding;
import com.google.android.material.resources.MaterialResources;
import com.google.android.material.shape.MaterialShapeDrawable;
import com.google.android.material.shape.ShapeAppearanceModel;
import com.google.android.material.shape.ShapeAppearanceModel.Builder;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class BottomSheetBehavior<V extends View>
  extends CoordinatorLayout.Behavior<V>
{
  private static final int CORNER_ANIMATION_DURATION = 500;
  private static final int DEF_STYLE_RES = R.style.Widget_Design_BottomSheet_Modal;
  private static final float HIDE_FRICTION = 0.1F;
  private static final float HIDE_THRESHOLD = 0.5F;
  private static final int NO_MAX_SIZE = -1;
  public static final int PEEK_HEIGHT_AUTO = -1;
  public static final int SAVE_ALL = -1;
  public static final int SAVE_FIT_TO_CONTENTS = 2;
  public static final int SAVE_HIDEABLE = 4;
  public static final int SAVE_NONE = 0;
  public static final int SAVE_PEEK_HEIGHT = 1;
  public static final int SAVE_SKIP_COLLAPSED = 8;
  private static final int SIGNIFICANT_VEL_THRESHOLD = 500;
  public static final int STATE_COLLAPSED = 4;
  public static final int STATE_DRAGGING = 1;
  public static final int STATE_EXPANDED = 3;
  public static final int STATE_HALF_EXPANDED = 6;
  public static final int STATE_HIDDEN = 5;
  public static final int STATE_SETTLING = 2;
  private static final String TAG = "BottomSheetBehavior";
  int activePointerId;
  private ColorStateList backgroundTint;
  private final ArrayList<BottomSheetCallback> callbacks = new ArrayList();
  private int childHeight;
  int collapsedOffset;
  private final ViewDragHelper.Callback dragCallback = new ViewDragHelper.Callback()
  {
    private long viewCapturedMillis;
    
    private boolean releasedLow(View paramAnonymousView)
    {
      boolean bool;
      if (paramAnonymousView.getTop() > (BottomSheetBehavior.this.parentHeight + BottomSheetBehavior.this.getExpandedOffset()) / 2) {
        bool = true;
      } else {
        bool = false;
      }
      return bool;
    }
    
    public int clampViewPositionHorizontal(View paramAnonymousView, int paramAnonymousInt1, int paramAnonymousInt2)
    {
      return paramAnonymousView.getLeft();
    }
    
    public int clampViewPositionVertical(View paramAnonymousView, int paramAnonymousInt1, int paramAnonymousInt2)
    {
      int i = BottomSheetBehavior.this.getExpandedOffset();
      if (BottomSheetBehavior.this.hideable) {
        paramAnonymousInt2 = BottomSheetBehavior.this.parentHeight;
      } else {
        paramAnonymousInt2 = BottomSheetBehavior.this.collapsedOffset;
      }
      return MathUtils.clamp(paramAnonymousInt1, i, paramAnonymousInt2);
    }
    
    public int getViewVerticalDragRange(View paramAnonymousView)
    {
      if (BottomSheetBehavior.this.hideable) {
        return BottomSheetBehavior.this.parentHeight;
      }
      return BottomSheetBehavior.this.collapsedOffset;
    }
    
    public void onViewDragStateChanged(int paramAnonymousInt)
    {
      if ((paramAnonymousInt == 1) && (BottomSheetBehavior.this.draggable)) {
        BottomSheetBehavior.this.setStateInternal(1);
      }
    }
    
    public void onViewPositionChanged(View paramAnonymousView, int paramAnonymousInt1, int paramAnonymousInt2, int paramAnonymousInt3, int paramAnonymousInt4)
    {
      BottomSheetBehavior.this.dispatchOnSlide(paramAnonymousInt2);
    }
    
    public void onViewReleased(View paramAnonymousView, float paramAnonymousFloat1, float paramAnonymousFloat2)
    {
      int i;
      if (paramAnonymousFloat2 < 0.0F)
      {
        if (BottomSheetBehavior.this.fitToContents)
        {
          i = 3;
        }
        else
        {
          i = paramAnonymousView.getTop();
          long l2 = System.currentTimeMillis();
          long l1 = this.viewCapturedMillis;
          if (BottomSheetBehavior.this.shouldSkipHalfExpandedStateWhenDragging())
          {
            paramAnonymousFloat1 = i * 100.0F / BottomSheetBehavior.this.parentHeight;
            if (BottomSheetBehavior.this.shouldExpandOnUpwardDrag(l2 - l1, paramAnonymousFloat1)) {
              i = 3;
            } else {
              i = 4;
            }
          }
          else if (i > BottomSheetBehavior.this.halfExpandedOffset)
          {
            i = 6;
          }
          else
          {
            i = 3;
          }
        }
      }
      else if ((BottomSheetBehavior.this.hideable) && (BottomSheetBehavior.this.shouldHide(paramAnonymousView, paramAnonymousFloat2)))
      {
        if (((Math.abs(paramAnonymousFloat1) < Math.abs(paramAnonymousFloat2)) && (paramAnonymousFloat2 > 500.0F)) || (releasedLow(paramAnonymousView))) {
          i = 5;
        } else if (BottomSheetBehavior.this.fitToContents) {
          i = 3;
        } else if (Math.abs(paramAnonymousView.getTop() - BottomSheetBehavior.this.getExpandedOffset()) < Math.abs(paramAnonymousView.getTop() - BottomSheetBehavior.this.halfExpandedOffset)) {
          i = 3;
        } else {
          i = 6;
        }
      }
      else if ((paramAnonymousFloat2 != 0.0F) && (Math.abs(paramAnonymousFloat1) <= Math.abs(paramAnonymousFloat2)))
      {
        if (BottomSheetBehavior.this.fitToContents)
        {
          i = 4;
        }
        else
        {
          i = paramAnonymousView.getTop();
          if (Math.abs(i - BottomSheetBehavior.this.halfExpandedOffset) < Math.abs(i - BottomSheetBehavior.this.collapsedOffset))
          {
            if (BottomSheetBehavior.this.shouldSkipHalfExpandedStateWhenDragging()) {
              i = 4;
            } else {
              i = 6;
            }
          }
          else {
            i = 4;
          }
        }
      }
      else
      {
        i = paramAnonymousView.getTop();
        if (BottomSheetBehavior.this.fitToContents)
        {
          if (Math.abs(i - BottomSheetBehavior.this.fitToContentsOffset) < Math.abs(i - BottomSheetBehavior.this.collapsedOffset)) {
            i = 3;
          } else {
            i = 4;
          }
        }
        else if (i < BottomSheetBehavior.this.halfExpandedOffset)
        {
          if (i < Math.abs(i - BottomSheetBehavior.this.collapsedOffset)) {
            i = 3;
          } else if (BottomSheetBehavior.this.shouldSkipHalfExpandedStateWhenDragging()) {
            i = 4;
          } else {
            i = 6;
          }
        }
        else if (Math.abs(i - BottomSheetBehavior.this.halfExpandedOffset) < Math.abs(i - BottomSheetBehavior.this.collapsedOffset))
        {
          if (BottomSheetBehavior.this.shouldSkipHalfExpandedStateWhenDragging()) {
            i = 4;
          } else {
            i = 6;
          }
        }
        else {
          i = 4;
        }
      }
      BottomSheetBehavior localBottomSheetBehavior = BottomSheetBehavior.this;
      localBottomSheetBehavior.startSettling(paramAnonymousView, i, localBottomSheetBehavior.shouldSkipSmoothAnimation());
    }
    
    public boolean tryCaptureView(View paramAnonymousView, int paramAnonymousInt)
    {
      int i = BottomSheetBehavior.this.state;
      boolean bool = true;
      if (i == 1) {
        return false;
      }
      if (BottomSheetBehavior.this.touchingScrollingChild) {
        return false;
      }
      if ((BottomSheetBehavior.this.state == 3) && (BottomSheetBehavior.this.activePointerId == paramAnonymousInt))
      {
        View localView;
        if (BottomSheetBehavior.this.nestedScrollingChildRef != null) {
          localView = (View)BottomSheetBehavior.this.nestedScrollingChildRef.get();
        } else {
          localView = null;
        }
        if ((localView != null) && (localView.canScrollVertically(-1))) {
          return false;
        }
      }
      this.viewCapturedMillis = System.currentTimeMillis();
      if ((BottomSheetBehavior.this.viewRef == null) || (BottomSheetBehavior.this.viewRef.get() != paramAnonymousView)) {
        bool = false;
      }
      return bool;
    }
  };
  private boolean draggable = true;
  float elevation = -1.0F;
  private int expandHalfwayActionId = -1;
  int expandedOffset;
  private boolean fitToContents = true;
  int fitToContentsOffset;
  private int gestureInsetBottom;
  private boolean gestureInsetBottomIgnored;
  int halfExpandedOffset;
  float halfExpandedRatio = 0.5F;
  boolean hideable;
  private boolean ignoreEvents;
  private Map<View, Integer> importantForAccessibilityMap;
  private int initialY;
  private int insetBottom;
  private int insetTop;
  private ValueAnimator interpolatorAnimator;
  private boolean isShapeExpanded;
  private int lastNestedScrollDy;
  int lastStableState = 4;
  private boolean marginLeftSystemWindowInsets;
  private boolean marginRightSystemWindowInsets;
  private boolean marginTopSystemWindowInsets;
  private MaterialShapeDrawable materialShapeDrawable;
  private int maxHeight = -1;
  private int maxWidth = -1;
  private float maximumVelocity;
  private boolean nestedScrolled;
  WeakReference<View> nestedScrollingChildRef;
  private boolean paddingBottomSystemWindowInsets;
  private boolean paddingLeftSystemWindowInsets;
  private boolean paddingRightSystemWindowInsets;
  private boolean paddingTopSystemWindowInsets;
  int parentHeight;
  int parentWidth;
  private int peekHeight;
  private boolean peekHeightAuto;
  private int peekHeightGestureInsetBuffer;
  private int peekHeightMin;
  private int saveFlags = 0;
  private ShapeAppearanceModel shapeAppearanceModelDefault;
  private boolean skipCollapsed;
  int state = 4;
  private final BottomSheetBehavior<V>.StateSettlingTracker stateSettlingTracker = new StateSettlingTracker(null);
  boolean touchingScrollingChild;
  private boolean updateImportantForAccessibilityOnSiblings = false;
  private VelocityTracker velocityTracker;
  ViewDragHelper viewDragHelper;
  WeakReference<V> viewRef;
  
  public BottomSheetBehavior() {}
  
  public BottomSheetBehavior(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    this.peekHeightGestureInsetBuffer = paramContext.getResources().getDimensionPixelSize(R.dimen.mtrl_min_touch_target_size);
    TypedArray localTypedArray = paramContext.obtainStyledAttributes(paramAttributeSet, R.styleable.BottomSheetBehavior_Layout);
    if (localTypedArray.hasValue(R.styleable.BottomSheetBehavior_Layout_backgroundTint)) {
      this.backgroundTint = MaterialResources.getColorStateList(paramContext, localTypedArray, R.styleable.BottomSheetBehavior_Layout_backgroundTint);
    }
    if (localTypedArray.hasValue(R.styleable.BottomSheetBehavior_Layout_shapeAppearance)) {
      this.shapeAppearanceModelDefault = ShapeAppearanceModel.builder(paramContext, paramAttributeSet, R.attr.bottomSheetStyle, DEF_STYLE_RES).build();
    }
    createMaterialShapeDrawableIfNeeded(paramContext);
    createShapeValueAnimator();
    if (Build.VERSION.SDK_INT >= 21) {
      this.elevation = localTypedArray.getDimension(R.styleable.BottomSheetBehavior_Layout_android_elevation, -1.0F);
    }
    if (localTypedArray.hasValue(R.styleable.BottomSheetBehavior_Layout_android_maxWidth)) {
      setMaxWidth(localTypedArray.getDimensionPixelSize(R.styleable.BottomSheetBehavior_Layout_android_maxWidth, -1));
    }
    if (localTypedArray.hasValue(R.styleable.BottomSheetBehavior_Layout_android_maxHeight)) {
      setMaxHeight(localTypedArray.getDimensionPixelSize(R.styleable.BottomSheetBehavior_Layout_android_maxHeight, -1));
    }
    paramAttributeSet = localTypedArray.peekValue(R.styleable.BottomSheetBehavior_Layout_behavior_peekHeight);
    if ((paramAttributeSet != null) && (paramAttributeSet.data == -1)) {
      setPeekHeight(paramAttributeSet.data);
    } else {
      setPeekHeight(localTypedArray.getDimensionPixelSize(R.styleable.BottomSheetBehavior_Layout_behavior_peekHeight, -1));
    }
    setHideable(localTypedArray.getBoolean(R.styleable.BottomSheetBehavior_Layout_behavior_hideable, false));
    setGestureInsetBottomIgnored(localTypedArray.getBoolean(R.styleable.BottomSheetBehavior_Layout_gestureInsetBottomIgnored, false));
    setFitToContents(localTypedArray.getBoolean(R.styleable.BottomSheetBehavior_Layout_behavior_fitToContents, true));
    setSkipCollapsed(localTypedArray.getBoolean(R.styleable.BottomSheetBehavior_Layout_behavior_skipCollapsed, false));
    setDraggable(localTypedArray.getBoolean(R.styleable.BottomSheetBehavior_Layout_behavior_draggable, true));
    setSaveFlags(localTypedArray.getInt(R.styleable.BottomSheetBehavior_Layout_behavior_saveFlags, 0));
    setHalfExpandedRatio(localTypedArray.getFloat(R.styleable.BottomSheetBehavior_Layout_behavior_halfExpandedRatio, 0.5F));
    paramAttributeSet = localTypedArray.peekValue(R.styleable.BottomSheetBehavior_Layout_behavior_expandedOffset);
    if ((paramAttributeSet != null) && (paramAttributeSet.type == 16)) {
      setExpandedOffset(paramAttributeSet.data);
    } else {
      setExpandedOffset(localTypedArray.getDimensionPixelOffset(R.styleable.BottomSheetBehavior_Layout_behavior_expandedOffset, 0));
    }
    this.paddingBottomSystemWindowInsets = localTypedArray.getBoolean(R.styleable.BottomSheetBehavior_Layout_paddingBottomSystemWindowInsets, false);
    this.paddingLeftSystemWindowInsets = localTypedArray.getBoolean(R.styleable.BottomSheetBehavior_Layout_paddingLeftSystemWindowInsets, false);
    this.paddingRightSystemWindowInsets = localTypedArray.getBoolean(R.styleable.BottomSheetBehavior_Layout_paddingRightSystemWindowInsets, false);
    this.paddingTopSystemWindowInsets = localTypedArray.getBoolean(R.styleable.BottomSheetBehavior_Layout_paddingTopSystemWindowInsets, true);
    this.marginLeftSystemWindowInsets = localTypedArray.getBoolean(R.styleable.BottomSheetBehavior_Layout_marginLeftSystemWindowInsets, false);
    this.marginRightSystemWindowInsets = localTypedArray.getBoolean(R.styleable.BottomSheetBehavior_Layout_marginRightSystemWindowInsets, false);
    this.marginTopSystemWindowInsets = localTypedArray.getBoolean(R.styleable.BottomSheetBehavior_Layout_marginTopSystemWindowInsets, false);
    localTypedArray.recycle();
    this.maximumVelocity = ViewConfiguration.get(paramContext).getScaledMaximumFlingVelocity();
  }
  
  private int addAccessibilityActionForState(V paramV, int paramInt1, int paramInt2)
  {
    return ViewCompat.addAccessibilityAction(paramV, paramV.getResources().getString(paramInt1), createAccessibilityViewCommandForState(paramInt2));
  }
  
  private void calculateCollapsedOffset()
  {
    int i = calculatePeekHeight();
    if (this.fitToContents) {
      this.collapsedOffset = Math.max(this.parentHeight - i, this.fitToContentsOffset);
    } else {
      this.collapsedOffset = (this.parentHeight - i);
    }
  }
  
  private void calculateHalfExpandedOffset()
  {
    this.halfExpandedOffset = ((int)(this.parentHeight * (1.0F - this.halfExpandedRatio)));
  }
  
  private int calculatePeekHeight()
  {
    if (this.peekHeightAuto) {
      return Math.min(Math.max(this.peekHeightMin, this.parentHeight - this.parentWidth * 9 / 16), this.childHeight) + this.insetBottom;
    }
    if ((!this.gestureInsetBottomIgnored) && (!this.paddingBottomSystemWindowInsets))
    {
      int i = this.gestureInsetBottom;
      if (i > 0) {
        return Math.max(this.peekHeight, i + this.peekHeightGestureInsetBuffer);
      }
    }
    return this.peekHeight + this.insetBottom;
  }
  
  private AccessibilityViewCommand createAccessibilityViewCommandForState(final int paramInt)
  {
    new AccessibilityViewCommand()
    {
      public boolean perform(View paramAnonymousView, AccessibilityViewCommand.CommandArguments paramAnonymousCommandArguments)
      {
        BottomSheetBehavior.this.setState(paramInt);
        return true;
      }
    };
  }
  
  private void createMaterialShapeDrawableIfNeeded(Context paramContext)
  {
    if (this.shapeAppearanceModelDefault == null) {
      return;
    }
    Object localObject = new MaterialShapeDrawable(this.shapeAppearanceModelDefault);
    this.materialShapeDrawable = ((MaterialShapeDrawable)localObject);
    ((MaterialShapeDrawable)localObject).initializeElevationOverlay(paramContext);
    localObject = this.backgroundTint;
    if (localObject != null)
    {
      this.materialShapeDrawable.setFillColor((ColorStateList)localObject);
    }
    else
    {
      localObject = new TypedValue();
      paramContext.getTheme().resolveAttribute(16842801, (TypedValue)localObject, true);
      this.materialShapeDrawable.setTint(((TypedValue)localObject).data);
    }
  }
  
  private void createShapeValueAnimator()
  {
    ValueAnimator localValueAnimator = ValueAnimator.ofFloat(new float[] { 0.0F, 1.0F });
    this.interpolatorAnimator = localValueAnimator;
    localValueAnimator.setDuration(500L);
    this.interpolatorAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener()
    {
      public void onAnimationUpdate(ValueAnimator paramAnonymousValueAnimator)
      {
        float f = ((Float)paramAnonymousValueAnimator.getAnimatedValue()).floatValue();
        if (BottomSheetBehavior.this.materialShapeDrawable != null) {
          BottomSheetBehavior.this.materialShapeDrawable.setInterpolation(f);
        }
      }
    });
  }
  
  public static <V extends View> BottomSheetBehavior<V> from(V paramV)
  {
    paramV = paramV.getLayoutParams();
    if ((paramV instanceof CoordinatorLayout.LayoutParams))
    {
      paramV = ((CoordinatorLayout.LayoutParams)paramV).getBehavior();
      if ((paramV instanceof BottomSheetBehavior)) {
        return (BottomSheetBehavior)paramV;
      }
      throw new IllegalArgumentException("The view is not associated with BottomSheetBehavior");
    }
    throw new IllegalArgumentException("The view is not a child of CoordinatorLayout");
  }
  
  private int getChildMeasureSpec(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    paramInt2 = ViewGroup.getChildMeasureSpec(paramInt1, paramInt2, paramInt4);
    if (paramInt3 == -1) {
      return paramInt2;
    }
    paramInt1 = View.MeasureSpec.getMode(paramInt2);
    paramInt2 = View.MeasureSpec.getSize(paramInt2);
    switch (paramInt1)
    {
    default: 
      if (paramInt2 != 0) {
        break;
      }
      break;
    case 1073741824: 
      return View.MeasureSpec.makeMeasureSpec(Math.min(paramInt2, paramInt3), 1073741824);
    }
    paramInt3 = Math.min(paramInt2, paramInt3);
    return View.MeasureSpec.makeMeasureSpec(paramInt3, Integer.MIN_VALUE);
  }
  
  private int getTopOffsetForState(int paramInt)
  {
    switch (paramInt)
    {
    default: 
      throw new IllegalArgumentException("Invalid state to get top offset: " + paramInt);
    case 6: 
      return this.halfExpandedOffset;
    case 5: 
      return this.parentHeight;
    case 4: 
      return this.collapsedOffset;
    }
    return getExpandedOffset();
  }
  
  private float getYVelocity()
  {
    VelocityTracker localVelocityTracker = this.velocityTracker;
    if (localVelocityTracker == null) {
      return 0.0F;
    }
    localVelocityTracker.computeCurrentVelocity(1000, this.maximumVelocity);
    return this.velocityTracker.getYVelocity(this.activePointerId);
  }
  
  private boolean isLayouting(V paramV)
  {
    ViewParent localViewParent = paramV.getParent();
    boolean bool;
    if ((localViewParent != null) && (localViewParent.isLayoutRequested()) && (ViewCompat.isAttachedToWindow(paramV))) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  private void replaceAccessibilityActionForState(V paramV, AccessibilityNodeInfoCompat.AccessibilityActionCompat paramAccessibilityActionCompat, int paramInt)
  {
    ViewCompat.replaceAccessibilityAction(paramV, paramAccessibilityActionCompat, null, createAccessibilityViewCommandForState(paramInt));
  }
  
  private void reset()
  {
    this.activePointerId = -1;
    VelocityTracker localVelocityTracker = this.velocityTracker;
    if (localVelocityTracker != null)
    {
      localVelocityTracker.recycle();
      this.velocityTracker = null;
    }
  }
  
  private void restoreOptionalState(SavedState paramSavedState)
  {
    int i = this.saveFlags;
    if (i == 0) {
      return;
    }
    if ((i == -1) || ((i & 0x1) == 1)) {
      this.peekHeight = paramSavedState.peekHeight;
    }
    i = this.saveFlags;
    if ((i == -1) || ((i & 0x2) == 2)) {
      this.fitToContents = paramSavedState.fitToContents;
    }
    i = this.saveFlags;
    if ((i == -1) || ((i & 0x4) == 4)) {
      this.hideable = paramSavedState.hideable;
    }
    i = this.saveFlags;
    if ((i == -1) || ((i & 0x8) == 8)) {
      this.skipCollapsed = paramSavedState.skipCollapsed;
    }
  }
  
  private void runAfterLayout(V paramV, Runnable paramRunnable)
  {
    if (isLayouting(paramV)) {
      paramV.post(paramRunnable);
    } else {
      paramRunnable.run();
    }
  }
  
  private void setWindowInsetsListener(View paramView)
  {
    final boolean bool;
    if ((Build.VERSION.SDK_INT >= 29) && (!isGestureInsetBottomIgnored()) && (!this.peekHeightAuto)) {
      bool = true;
    } else {
      bool = false;
    }
    if ((!this.paddingBottomSystemWindowInsets) && (!this.paddingLeftSystemWindowInsets) && (!this.paddingRightSystemWindowInsets) && (!this.marginLeftSystemWindowInsets) && (!this.marginRightSystemWindowInsets) && (!this.marginTopSystemWindowInsets) && (!bool)) {
      return;
    }
    ViewUtils.doOnApplyWindowInsets(paramView, new ViewUtils.OnApplyWindowInsetsListener()
    {
      public WindowInsetsCompat onApplyWindowInsets(View paramAnonymousView, WindowInsetsCompat paramAnonymousWindowInsetsCompat, ViewUtils.RelativePadding paramAnonymousRelativePadding)
      {
        Insets localInsets2 = paramAnonymousWindowInsetsCompat.getInsets(WindowInsetsCompat.Type.systemBars());
        Insets localInsets1 = paramAnonymousWindowInsetsCompat.getInsets(WindowInsetsCompat.Type.mandatorySystemGestures());
        BottomSheetBehavior.access$302(BottomSheetBehavior.this, localInsets2.top);
        boolean bool = ViewUtils.isLayoutRtl(paramAnonymousView);
        int k = paramAnonymousView.getPaddingBottom();
        int m = paramAnonymousView.getPaddingLeft();
        int n = paramAnonymousView.getPaddingRight();
        if (BottomSheetBehavior.this.paddingBottomSystemWindowInsets)
        {
          BottomSheetBehavior.access$502(BottomSheetBehavior.this, paramAnonymousWindowInsetsCompat.getSystemWindowInsetBottom());
          k = paramAnonymousRelativePadding.bottom + BottomSheetBehavior.this.insetBottom;
        }
        if (BottomSheetBehavior.this.paddingLeftSystemWindowInsets)
        {
          if (bool) {
            i = paramAnonymousRelativePadding.end;
          } else {
            i = paramAnonymousRelativePadding.start;
          }
          m = i + localInsets2.left;
        }
        if (BottomSheetBehavior.this.paddingRightSystemWindowInsets)
        {
          if (bool) {
            i = paramAnonymousRelativePadding.start;
          } else {
            i = paramAnonymousRelativePadding.end;
          }
          n = i + localInsets2.right;
        }
        paramAnonymousRelativePadding = (ViewGroup.MarginLayoutParams)paramAnonymousView.getLayoutParams();
        int i = 0;
        int j = i;
        if (BottomSheetBehavior.this.marginLeftSystemWindowInsets)
        {
          j = i;
          if (paramAnonymousRelativePadding.leftMargin != localInsets2.left)
          {
            paramAnonymousRelativePadding.leftMargin = localInsets2.left;
            j = 1;
          }
        }
        i = j;
        if (BottomSheetBehavior.this.marginRightSystemWindowInsets)
        {
          i = j;
          if (paramAnonymousRelativePadding.rightMargin != localInsets2.right)
          {
            paramAnonymousRelativePadding.rightMargin = localInsets2.right;
            i = 1;
          }
        }
        j = i;
        if (BottomSheetBehavior.this.marginTopSystemWindowInsets)
        {
          j = i;
          if (paramAnonymousRelativePadding.topMargin != localInsets2.top)
          {
            paramAnonymousRelativePadding.topMargin = localInsets2.top;
            j = 1;
          }
        }
        if (j != 0) {
          paramAnonymousView.setLayoutParams(paramAnonymousRelativePadding);
        }
        paramAnonymousView.setPadding(m, paramAnonymousView.getPaddingTop(), n, k);
        if (bool) {
          BottomSheetBehavior.access$1102(BottomSheetBehavior.this, localInsets1.bottom);
        }
        if ((BottomSheetBehavior.this.paddingBottomSystemWindowInsets) || (bool)) {
          BottomSheetBehavior.this.updatePeekHeight(false);
        }
        return paramAnonymousWindowInsetsCompat;
      }
    });
  }
  
  private boolean shouldHandleDraggingWithHelper()
  {
    ViewDragHelper localViewDragHelper = this.viewDragHelper;
    boolean bool = true;
    if ((localViewDragHelper == null) || ((this.draggable) || (this.state != 1))) {
      bool = false;
    }
    return bool;
  }
  
  private void startSettling(View paramView, int paramInt, boolean paramBoolean)
  {
    int i = getTopOffsetForState(paramInt);
    ViewDragHelper localViewDragHelper = this.viewDragHelper;
    if ((localViewDragHelper != null) && (paramBoolean ? localViewDragHelper.settleCapturedViewAt(paramView.getLeft(), i) : localViewDragHelper.smoothSlideViewTo(paramView, paramView.getLeft(), i))) {
      i = 1;
    } else {
      i = 0;
    }
    if (i != 0)
    {
      setStateInternal(2);
      updateDrawableForTargetState(paramInt);
      this.stateSettlingTracker.continueSettlingToState(paramInt);
    }
    else
    {
      setStateInternal(paramInt);
    }
  }
  
  private void updateAccessibilityActions()
  {
    Object localObject = this.viewRef;
    if (localObject == null) {
      return;
    }
    localObject = (View)((WeakReference)localObject).get();
    if (localObject == null) {
      return;
    }
    ViewCompat.removeAccessibilityAction((View)localObject, 524288);
    ViewCompat.removeAccessibilityAction((View)localObject, 262144);
    ViewCompat.removeAccessibilityAction((View)localObject, 1048576);
    int i = this.expandHalfwayActionId;
    if (i != -1) {
      ViewCompat.removeAccessibilityAction((View)localObject, i);
    }
    boolean bool = this.fitToContents;
    i = 6;
    if ((!bool) && (this.state != 6)) {
      this.expandHalfwayActionId = addAccessibilityActionForState((View)localObject, R.string.bottomsheet_action_expand_halfway, 6);
    }
    if ((this.hideable) && (this.state != 5)) {
      replaceAccessibilityActionForState((View)localObject, AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_DISMISS, 5);
    }
    switch (this.state)
    {
    case 5: 
    default: 
      break;
    case 6: 
      replaceAccessibilityActionForState((View)localObject, AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_COLLAPSE, 4);
      replaceAccessibilityActionForState((View)localObject, AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_EXPAND, 3);
      break;
    case 4: 
      if (this.fitToContents) {
        i = 3;
      }
      replaceAccessibilityActionForState((View)localObject, AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_EXPAND, i);
      break;
    case 3: 
      if (this.fitToContents) {
        i = 4;
      }
      replaceAccessibilityActionForState((View)localObject, AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_COLLAPSE, i);
    }
  }
  
  private void updateDrawableForTargetState(int paramInt)
  {
    if (paramInt == 2) {
      return;
    }
    boolean bool;
    if (paramInt == 3) {
      bool = true;
    } else {
      bool = false;
    }
    if (this.isShapeExpanded != bool)
    {
      this.isShapeExpanded = bool;
      if (this.materialShapeDrawable != null)
      {
        ValueAnimator localValueAnimator = this.interpolatorAnimator;
        if (localValueAnimator != null) {
          if (localValueAnimator.isRunning())
          {
            this.interpolatorAnimator.reverse();
          }
          else
          {
            float f;
            if (bool) {
              f = 0.0F;
            } else {
              f = 1.0F;
            }
            this.interpolatorAnimator.setFloatValues(new float[] { 1.0F - f, f });
            this.interpolatorAnimator.start();
          }
        }
      }
    }
  }
  
  private void updateImportantForAccessibility(boolean paramBoolean)
  {
    Object localObject = this.viewRef;
    if (localObject == null) {
      return;
    }
    localObject = ((View)((WeakReference)localObject).get()).getParent();
    if (!(localObject instanceof CoordinatorLayout)) {
      return;
    }
    localObject = (CoordinatorLayout)localObject;
    int j = ((CoordinatorLayout)localObject).getChildCount();
    if ((Build.VERSION.SDK_INT >= 16) && (paramBoolean)) {
      if (this.importantForAccessibilityMap == null) {
        this.importantForAccessibilityMap = new HashMap(j);
      } else {
        return;
      }
    }
    for (int i = 0; i < j; i++)
    {
      View localView = ((CoordinatorLayout)localObject).getChildAt(i);
      if (localView != this.viewRef.get()) {
        if (paramBoolean)
        {
          if (Build.VERSION.SDK_INT >= 16) {
            this.importantForAccessibilityMap.put(localView, Integer.valueOf(localView.getImportantForAccessibility()));
          }
          if (this.updateImportantForAccessibilityOnSiblings) {
            ViewCompat.setImportantForAccessibility(localView, 4);
          }
        }
        else if (this.updateImportantForAccessibilityOnSiblings)
        {
          Map localMap = this.importantForAccessibilityMap;
          if ((localMap != null) && (localMap.containsKey(localView))) {
            ViewCompat.setImportantForAccessibility(localView, ((Integer)this.importantForAccessibilityMap.get(localView)).intValue());
          }
        }
      }
    }
    if (!paramBoolean) {
      this.importantForAccessibilityMap = null;
    } else if (this.updateImportantForAccessibilityOnSiblings) {
      ((View)this.viewRef.get()).sendAccessibilityEvent(8);
    }
  }
  
  private void updatePeekHeight(boolean paramBoolean)
  {
    if (this.viewRef != null)
    {
      calculateCollapsedOffset();
      if (this.state == 4)
      {
        View localView = (View)this.viewRef.get();
        if (localView != null) {
          if (paramBoolean) {
            setState(4);
          } else {
            localView.requestLayout();
          }
        }
      }
    }
  }
  
  public void addBottomSheetCallback(BottomSheetCallback paramBottomSheetCallback)
  {
    if (!this.callbacks.contains(paramBottomSheetCallback)) {
      this.callbacks.add(paramBottomSheetCallback);
    }
  }
  
  public void disableShapeAnimations()
  {
    this.interpolatorAnimator = null;
  }
  
  void dispatchOnSlide(int paramInt)
  {
    View localView = (View)this.viewRef.get();
    if ((localView != null) && (!this.callbacks.isEmpty()))
    {
      int i = this.collapsedOffset;
      float f;
      if ((paramInt <= i) && (i != getExpandedOffset()))
      {
        i = this.collapsedOffset;
        f = (i - paramInt) / (i - getExpandedOffset());
      }
      else
      {
        i = this.collapsedOffset;
        f = (i - paramInt) / (this.parentHeight - i);
      }
      for (paramInt = 0; paramInt < this.callbacks.size(); paramInt++) {
        ((BottomSheetCallback)this.callbacks.get(paramInt)).onSlide(localView, f);
      }
    }
  }
  
  View findScrollingChild(View paramView)
  {
    if (ViewCompat.isNestedScrollingEnabled(paramView)) {
      return paramView;
    }
    if ((paramView instanceof ViewGroup))
    {
      ViewGroup localViewGroup = (ViewGroup)paramView;
      int i = 0;
      int j = localViewGroup.getChildCount();
      while (i < j)
      {
        paramView = findScrollingChild(localViewGroup.getChildAt(i));
        if (paramView != null) {
          return paramView;
        }
        i++;
      }
    }
    return null;
  }
  
  public int getExpandedOffset()
  {
    int i;
    if (this.fitToContents)
    {
      i = this.fitToContentsOffset;
    }
    else
    {
      int j = this.expandedOffset;
      if (this.paddingTopSystemWindowInsets) {
        i = 0;
      } else {
        i = this.insetTop;
      }
      i = Math.max(j, i);
    }
    return i;
  }
  
  public float getHalfExpandedRatio()
  {
    return this.halfExpandedRatio;
  }
  
  public int getLastStableState()
  {
    return this.lastStableState;
  }
  
  MaterialShapeDrawable getMaterialShapeDrawable()
  {
    return this.materialShapeDrawable;
  }
  
  public int getMaxHeight()
  {
    return this.maxHeight;
  }
  
  public int getMaxWidth()
  {
    return this.maxWidth;
  }
  
  public int getPeekHeight()
  {
    int i;
    if (this.peekHeightAuto) {
      i = -1;
    } else {
      i = this.peekHeight;
    }
    return i;
  }
  
  int getPeekHeightMin()
  {
    return this.peekHeightMin;
  }
  
  public int getSaveFlags()
  {
    return this.saveFlags;
  }
  
  public boolean getSkipCollapsed()
  {
    return this.skipCollapsed;
  }
  
  public int getState()
  {
    return this.state;
  }
  
  public boolean isDraggable()
  {
    return this.draggable;
  }
  
  public boolean isFitToContents()
  {
    return this.fitToContents;
  }
  
  public boolean isGestureInsetBottomIgnored()
  {
    return this.gestureInsetBottomIgnored;
  }
  
  public boolean isHideable()
  {
    return this.hideable;
  }
  
  public boolean isNestedScrollingCheckEnabled()
  {
    return true;
  }
  
  public void onAttachedToLayoutParams(CoordinatorLayout.LayoutParams paramLayoutParams)
  {
    super.onAttachedToLayoutParams(paramLayoutParams);
    this.viewRef = null;
    this.viewDragHelper = null;
  }
  
  public void onDetachedFromLayoutParams()
  {
    super.onDetachedFromLayoutParams();
    this.viewRef = null;
    this.viewDragHelper = null;
  }
  
  public boolean onInterceptTouchEvent(CoordinatorLayout paramCoordinatorLayout, V paramV, MotionEvent paramMotionEvent)
  {
    boolean bool1 = paramV.isShown();
    boolean bool2 = false;
    if ((bool1) && (this.draggable))
    {
      int i = paramMotionEvent.getActionMasked();
      if (i == 0) {
        reset();
      }
      if (this.velocityTracker == null) {
        this.velocityTracker = VelocityTracker.obtain();
      }
      this.velocityTracker.addMovement(paramMotionEvent);
      Object localObject2 = null;
      switch (i)
      {
      case 2: 
      default: 
        break;
      case 1: 
      case 3: 
        this.touchingScrollingChild = false;
        this.activePointerId = -1;
        if (this.ignoreEvents)
        {
          this.ignoreEvents = false;
          return false;
        }
        break;
      case 0: 
        int j = (int)paramMotionEvent.getX();
        this.initialY = ((int)paramMotionEvent.getY());
        if (this.state != 2)
        {
          localObject1 = this.nestedScrollingChildRef;
          if (localObject1 != null) {
            localObject1 = (View)((WeakReference)localObject1).get();
          } else {
            localObject1 = null;
          }
          if ((localObject1 != null) && (paramCoordinatorLayout.isPointInChildBounds((View)localObject1, j, this.initialY)))
          {
            this.activePointerId = paramMotionEvent.getPointerId(paramMotionEvent.getActionIndex());
            this.touchingScrollingChild = true;
          }
        }
        if ((this.activePointerId == -1) && (!paramCoordinatorLayout.isPointInChildBounds(paramV, j, this.initialY))) {
          bool1 = true;
        } else {
          bool1 = false;
        }
        this.ignoreEvents = bool1;
      }
      if (!this.ignoreEvents)
      {
        paramV = this.viewDragHelper;
        if ((paramV != null) && (paramV.shouldInterceptTouchEvent(paramMotionEvent))) {
          return true;
        }
      }
      Object localObject1 = this.nestedScrollingChildRef;
      paramV = (V)localObject2;
      if (localObject1 != null) {
        paramV = (View)((WeakReference)localObject1).get();
      }
      if ((i == 2) && (paramV != null) && (!this.ignoreEvents) && (this.state != 1) && (!paramCoordinatorLayout.isPointInChildBounds(paramV, (int)paramMotionEvent.getX(), (int)paramMotionEvent.getY())) && (this.viewDragHelper != null) && (Math.abs(this.initialY - paramMotionEvent.getY()) > this.viewDragHelper.getTouchSlop())) {
        bool1 = true;
      } else {
        bool1 = bool2;
      }
      return bool1;
    }
    this.ignoreEvents = true;
    return false;
  }
  
  public boolean onLayoutChild(CoordinatorLayout paramCoordinatorLayout, V paramV, int paramInt)
  {
    if ((ViewCompat.getFitsSystemWindows(paramCoordinatorLayout)) && (!ViewCompat.getFitsSystemWindows(paramV))) {
      paramV.setFitsSystemWindows(true);
    }
    if (this.viewRef == null)
    {
      this.peekHeightMin = paramCoordinatorLayout.getResources().getDimensionPixelSize(R.dimen.design_bottom_sheet_peek_height_min);
      setWindowInsetsListener(paramV);
      this.viewRef = new WeakReference(paramV);
      Object localObject = this.materialShapeDrawable;
      if (localObject != null)
      {
        ViewCompat.setBackground(paramV, (Drawable)localObject);
        localObject = this.materialShapeDrawable;
        float f2 = this.elevation;
        float f1 = f2;
        if (f2 == -1.0F) {
          f1 = ViewCompat.getElevation(paramV);
        }
        ((MaterialShapeDrawable)localObject).setElevation(f1);
        boolean bool;
        if (this.state == 3) {
          bool = true;
        } else {
          bool = false;
        }
        this.isShapeExpanded = bool;
        localObject = this.materialShapeDrawable;
        if (bool) {
          f1 = 0.0F;
        } else {
          f1 = 1.0F;
        }
        ((MaterialShapeDrawable)localObject).setInterpolation(f1);
      }
      else
      {
        localObject = this.backgroundTint;
        if (localObject != null) {
          ViewCompat.setBackgroundTintList(paramV, (ColorStateList)localObject);
        }
      }
      updateAccessibilityActions();
      if (ViewCompat.getImportantForAccessibility(paramV) == 0) {
        ViewCompat.setImportantForAccessibility(paramV, 1);
      }
    }
    if (this.viewDragHelper == null) {
      this.viewDragHelper = ViewDragHelper.create(paramCoordinatorLayout, this.dragCallback);
    }
    int i = paramV.getTop();
    paramCoordinatorLayout.onLayoutChild(paramV, paramInt);
    this.parentWidth = paramCoordinatorLayout.getWidth();
    this.parentHeight = paramCoordinatorLayout.getHeight();
    paramInt = paramV.getHeight();
    this.childHeight = paramInt;
    int k = this.parentHeight;
    int j = this.insetTop;
    if (k - paramInt < j) {
      if (this.paddingTopSystemWindowInsets) {
        this.childHeight = k;
      } else {
        this.childHeight = (k - j);
      }
    }
    this.fitToContentsOffset = Math.max(0, k - this.childHeight);
    calculateHalfExpandedOffset();
    calculateCollapsedOffset();
    paramInt = this.state;
    if (paramInt == 3) {
      ViewCompat.offsetTopAndBottom(paramV, getExpandedOffset());
    } else if (paramInt == 6) {
      ViewCompat.offsetTopAndBottom(paramV, this.halfExpandedOffset);
    } else if ((this.hideable) && (paramInt == 5)) {
      ViewCompat.offsetTopAndBottom(paramV, this.parentHeight);
    } else if (paramInt == 4) {
      ViewCompat.offsetTopAndBottom(paramV, this.collapsedOffset);
    } else if ((paramInt == 1) || (paramInt == 2)) {
      ViewCompat.offsetTopAndBottom(paramV, i - paramV.getTop());
    }
    this.nestedScrollingChildRef = new WeakReference(findScrollingChild(paramV));
    for (paramInt = 0; paramInt < this.callbacks.size(); paramInt++) {
      ((BottomSheetCallback)this.callbacks.get(paramInt)).onLayout(paramV);
    }
    return true;
  }
  
  public boolean onMeasureChild(CoordinatorLayout paramCoordinatorLayout, V paramV, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    ViewGroup.MarginLayoutParams localMarginLayoutParams = (ViewGroup.MarginLayoutParams)paramV.getLayoutParams();
    paramInt1 = getChildMeasureSpec(paramInt1, paramCoordinatorLayout.getPaddingLeft() + paramCoordinatorLayout.getPaddingRight() + localMarginLayoutParams.leftMargin + localMarginLayoutParams.rightMargin + paramInt2, this.maxWidth, localMarginLayoutParams.width);
    paramV.measure(paramInt1, getChildMeasureSpec(paramInt3, paramCoordinatorLayout.getPaddingTop() + paramCoordinatorLayout.getPaddingBottom() + localMarginLayoutParams.topMargin + localMarginLayoutParams.bottomMargin + paramInt4, this.maxHeight, localMarginLayoutParams.height));
    return true;
  }
  
  public boolean onNestedPreFling(CoordinatorLayout paramCoordinatorLayout, V paramV, View paramView, float paramFloat1, float paramFloat2)
  {
    boolean bool2 = isNestedScrollingCheckEnabled();
    boolean bool1 = false;
    if (bool2)
    {
      WeakReference localWeakReference = this.nestedScrollingChildRef;
      if (localWeakReference != null)
      {
        if ((paramView == localWeakReference.get()) && ((this.state != 3) || (super.onNestedPreFling(paramCoordinatorLayout, paramV, paramView, paramFloat1, paramFloat2)))) {
          bool1 = true;
        }
        return bool1;
      }
    }
    return false;
  }
  
  public void onNestedPreScroll(CoordinatorLayout paramCoordinatorLayout, V paramV, View paramView, int paramInt1, int paramInt2, int[] paramArrayOfInt, int paramInt3)
  {
    if (paramInt3 == 1) {
      return;
    }
    paramCoordinatorLayout = this.nestedScrollingChildRef;
    if (paramCoordinatorLayout != null) {
      paramCoordinatorLayout = (View)paramCoordinatorLayout.get();
    } else {
      paramCoordinatorLayout = null;
    }
    if ((isNestedScrollingCheckEnabled()) && (paramView != paramCoordinatorLayout)) {
      return;
    }
    int i = paramV.getTop();
    paramInt1 = i - paramInt2;
    if (paramInt2 > 0)
    {
      if (paramInt1 < getExpandedOffset())
      {
        paramArrayOfInt[1] = (i - getExpandedOffset());
        ViewCompat.offsetTopAndBottom(paramV, -paramArrayOfInt[1]);
        setStateInternal(3);
      }
      else
      {
        if (!this.draggable) {
          return;
        }
        paramArrayOfInt[1] = paramInt2;
        ViewCompat.offsetTopAndBottom(paramV, -paramInt2);
        setStateInternal(1);
      }
    }
    else if ((paramInt2 < 0) && (!paramView.canScrollVertically(-1)))
    {
      paramInt3 = this.collapsedOffset;
      if ((paramInt1 > paramInt3) && (!this.hideable))
      {
        paramArrayOfInt[1] = (i - paramInt3);
        ViewCompat.offsetTopAndBottom(paramV, -paramArrayOfInt[1]);
        setStateInternal(4);
      }
      else
      {
        if (!this.draggable) {
          return;
        }
        paramArrayOfInt[1] = paramInt2;
        ViewCompat.offsetTopAndBottom(paramV, -paramInt2);
        setStateInternal(1);
      }
    }
    dispatchOnSlide(paramV.getTop());
    this.lastNestedScrollDy = paramInt2;
    this.nestedScrolled = true;
  }
  
  public void onNestedScroll(CoordinatorLayout paramCoordinatorLayout, V paramV, View paramView, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int[] paramArrayOfInt) {}
  
  public void onRestoreInstanceState(CoordinatorLayout paramCoordinatorLayout, V paramV, Parcelable paramParcelable)
  {
    paramParcelable = (SavedState)paramParcelable;
    super.onRestoreInstanceState(paramCoordinatorLayout, paramV, paramParcelable.getSuperState());
    restoreOptionalState(paramParcelable);
    if ((paramParcelable.state != 1) && (paramParcelable.state != 2))
    {
      int i = paramParcelable.state;
      this.state = i;
      this.lastStableState = i;
    }
    else
    {
      this.state = 4;
      this.lastStableState = 4;
    }
  }
  
  public Parcelable onSaveInstanceState(CoordinatorLayout paramCoordinatorLayout, V paramV)
  {
    return new SavedState(super.onSaveInstanceState(paramCoordinatorLayout, paramV), this);
  }
  
  public boolean onStartNestedScroll(CoordinatorLayout paramCoordinatorLayout, V paramV, View paramView1, View paramView2, int paramInt1, int paramInt2)
  {
    boolean bool = false;
    this.lastNestedScrollDy = 0;
    this.nestedScrolled = false;
    if ((paramInt1 & 0x2) != 0) {
      bool = true;
    }
    return bool;
  }
  
  public void onStopNestedScroll(CoordinatorLayout paramCoordinatorLayout, V paramV, View paramView, int paramInt)
  {
    if (paramV.getTop() == getExpandedOffset())
    {
      setStateInternal(3);
      return;
    }
    if (isNestedScrollingCheckEnabled())
    {
      paramCoordinatorLayout = this.nestedScrollingChildRef;
      if ((paramCoordinatorLayout == null) || (paramView != paramCoordinatorLayout.get()) || (!this.nestedScrolled)) {
        return;
      }
    }
    if (this.lastNestedScrollDy > 0)
    {
      if (this.fitToContents) {
        paramInt = 3;
      } else if (paramV.getTop() > this.halfExpandedOffset) {
        paramInt = 6;
      } else {
        paramInt = 3;
      }
    }
    else if ((this.hideable) && (shouldHide(paramV, getYVelocity())))
    {
      paramInt = 5;
    }
    else if (this.lastNestedScrollDy == 0)
    {
      paramInt = paramV.getTop();
      if (this.fitToContents)
      {
        if (Math.abs(paramInt - this.fitToContentsOffset) < Math.abs(paramInt - this.collapsedOffset)) {
          paramInt = 3;
        } else {
          paramInt = 4;
        }
      }
      else
      {
        int i = this.halfExpandedOffset;
        if (paramInt < i)
        {
          if (paramInt < Math.abs(paramInt - this.collapsedOffset)) {
            paramInt = 3;
          } else if (shouldSkipHalfExpandedStateWhenDragging()) {
            paramInt = 4;
          } else {
            paramInt = 6;
          }
        }
        else if (Math.abs(paramInt - i) < Math.abs(paramInt - this.collapsedOffset)) {
          paramInt = 6;
        } else {
          paramInt = 4;
        }
      }
    }
    else if (this.fitToContents)
    {
      paramInt = 4;
    }
    else
    {
      paramInt = paramV.getTop();
      if (Math.abs(paramInt - this.halfExpandedOffset) < Math.abs(paramInt - this.collapsedOffset)) {
        paramInt = 6;
      } else {
        paramInt = 4;
      }
    }
    startSettling(paramV, paramInt, false);
    this.nestedScrolled = false;
  }
  
  public boolean onTouchEvent(CoordinatorLayout paramCoordinatorLayout, V paramV, MotionEvent paramMotionEvent)
  {
    if (!paramV.isShown()) {
      return false;
    }
    int i = paramMotionEvent.getActionMasked();
    if ((this.state == 1) && (i == 0)) {
      return true;
    }
    if (shouldHandleDraggingWithHelper()) {
      this.viewDragHelper.processTouchEvent(paramMotionEvent);
    }
    if (i == 0) {
      reset();
    }
    if (this.velocityTracker == null) {
      this.velocityTracker = VelocityTracker.obtain();
    }
    this.velocityTracker.addMovement(paramMotionEvent);
    if ((shouldHandleDraggingWithHelper()) && (i == 2) && (!this.ignoreEvents) && (Math.abs(this.initialY - paramMotionEvent.getY()) > this.viewDragHelper.getTouchSlop())) {
      this.viewDragHelper.captureChildView(paramV, paramMotionEvent.getPointerId(paramMotionEvent.getActionIndex()));
    }
    return this.ignoreEvents ^ true;
  }
  
  public void removeBottomSheetCallback(BottomSheetCallback paramBottomSheetCallback)
  {
    this.callbacks.remove(paramBottomSheetCallback);
  }
  
  @Deprecated
  public void setBottomSheetCallback(BottomSheetCallback paramBottomSheetCallback)
  {
    Log.w("BottomSheetBehavior", "BottomSheetBehavior now supports multiple callbacks. `setBottomSheetCallback()` removes all existing callbacks, including ones set internally by library authors, which may result in unintended behavior. This may change in the future. Please use `addBottomSheetCallback()` and `removeBottomSheetCallback()` instead to set your own callbacks.");
    this.callbacks.clear();
    if (paramBottomSheetCallback != null) {
      this.callbacks.add(paramBottomSheetCallback);
    }
  }
  
  public void setDraggable(boolean paramBoolean)
  {
    this.draggable = paramBoolean;
  }
  
  public void setExpandedOffset(int paramInt)
  {
    if (paramInt >= 0)
    {
      this.expandedOffset = paramInt;
      return;
    }
    throw new IllegalArgumentException("offset must be greater than or equal to 0");
  }
  
  public void setFitToContents(boolean paramBoolean)
  {
    if (this.fitToContents == paramBoolean) {
      return;
    }
    this.fitToContents = paramBoolean;
    if (this.viewRef != null) {
      calculateCollapsedOffset();
    }
    int i;
    if ((this.fitToContents) && (this.state == 6)) {
      i = 3;
    } else {
      i = this.state;
    }
    setStateInternal(i);
    updateAccessibilityActions();
  }
  
  public void setGestureInsetBottomIgnored(boolean paramBoolean)
  {
    this.gestureInsetBottomIgnored = paramBoolean;
  }
  
  public void setHalfExpandedRatio(float paramFloat)
  {
    if ((paramFloat > 0.0F) && (paramFloat < 1.0F))
    {
      this.halfExpandedRatio = paramFloat;
      if (this.viewRef != null) {
        calculateHalfExpandedOffset();
      }
      return;
    }
    throw new IllegalArgumentException("ratio must be a float value between 0 and 1");
  }
  
  public void setHideable(boolean paramBoolean)
  {
    if (this.hideable != paramBoolean)
    {
      this.hideable = paramBoolean;
      if ((!paramBoolean) && (this.state == 5)) {
        setState(4);
      }
      updateAccessibilityActions();
    }
  }
  
  public void setHideableInternal(boolean paramBoolean)
  {
    this.hideable = paramBoolean;
  }
  
  public void setMaxHeight(int paramInt)
  {
    this.maxHeight = paramInt;
  }
  
  public void setMaxWidth(int paramInt)
  {
    this.maxWidth = paramInt;
  }
  
  public void setPeekHeight(int paramInt)
  {
    setPeekHeight(paramInt, false);
  }
  
  public final void setPeekHeight(int paramInt, boolean paramBoolean)
  {
    int i = 0;
    if (paramInt == -1)
    {
      if (!this.peekHeightAuto)
      {
        this.peekHeightAuto = true;
        i = 1;
      }
    }
    else if ((this.peekHeightAuto) || (this.peekHeight != paramInt))
    {
      this.peekHeightAuto = false;
      this.peekHeight = Math.max(0, paramInt);
      i = 1;
    }
    if (i != 0) {
      updatePeekHeight(paramBoolean);
    }
  }
  
  public void setSaveFlags(int paramInt)
  {
    this.saveFlags = paramInt;
  }
  
  public void setSkipCollapsed(boolean paramBoolean)
  {
    this.skipCollapsed = paramBoolean;
  }
  
  public void setState(int paramInt)
  {
    final Object localObject;
    if ((paramInt != 1) && (paramInt != 2))
    {
      if ((!this.hideable) && (paramInt == 5))
      {
        Log.w("BottomSheetBehavior", "Cannot set state: " + paramInt);
        return;
      }
      final int i;
      if ((paramInt == 6) && (this.fitToContents) && (getTopOffsetForState(paramInt) <= this.fitToContentsOffset)) {
        i = 3;
      } else {
        i = paramInt;
      }
      localObject = this.viewRef;
      if ((localObject != null) && (((WeakReference)localObject).get() != null))
      {
        localObject = (View)this.viewRef.get();
        runAfterLayout((View)localObject, new Runnable()
        {
          public void run()
          {
            BottomSheetBehavior.this.startSettling(localObject, i, false);
          }
        });
      }
      else
      {
        setStateInternal(paramInt);
      }
      return;
    }
    StringBuilder localStringBuilder = new StringBuilder().append("STATE_");
    if (paramInt == 1) {
      localObject = "DRAGGING";
    } else {
      localObject = "SETTLING";
    }
    throw new IllegalArgumentException((String)localObject + " should not be set externally.");
  }
  
  void setStateInternal(int paramInt)
  {
    if (this.state == paramInt) {
      return;
    }
    this.state = paramInt;
    if ((paramInt == 4) || (paramInt == 3) || (paramInt == 6) || ((this.hideable) && (paramInt == 5))) {
      this.lastStableState = paramInt;
    }
    Object localObject = this.viewRef;
    if (localObject == null) {
      return;
    }
    localObject = (View)((WeakReference)localObject).get();
    if (localObject == null) {
      return;
    }
    if (paramInt == 3) {
      updateImportantForAccessibility(true);
    } else if ((paramInt == 6) || (paramInt == 5) || (paramInt == 4)) {
      updateImportantForAccessibility(false);
    }
    updateDrawableForTargetState(paramInt);
    for (int i = 0; i < this.callbacks.size(); i++) {
      ((BottomSheetCallback)this.callbacks.get(i)).onStateChanged((View)localObject, paramInt);
    }
    updateAccessibilityActions();
  }
  
  public void setUpdateImportantForAccessibilityOnSiblings(boolean paramBoolean)
  {
    this.updateImportantForAccessibilityOnSiblings = paramBoolean;
  }
  
  public boolean shouldExpandOnUpwardDrag(long paramLong, float paramFloat)
  {
    return false;
  }
  
  boolean shouldHide(View paramView, float paramFloat)
  {
    boolean bool2 = this.skipCollapsed;
    boolean bool1 = true;
    if (bool2) {
      return true;
    }
    if (paramView.getTop() < this.collapsedOffset) {
      return false;
    }
    int i = calculatePeekHeight();
    if (Math.abs(paramView.getTop() + 0.1F * paramFloat - this.collapsedOffset) / i <= 0.5F) {
      bool1 = false;
    }
    return bool1;
  }
  
  public boolean shouldSkipHalfExpandedStateWhenDragging()
  {
    return false;
  }
  
  public boolean shouldSkipSmoothAnimation()
  {
    return true;
  }
  
  public static abstract class BottomSheetCallback
  {
    void onLayout(View paramView) {}
    
    public abstract void onSlide(View paramView, float paramFloat);
    
    public abstract void onStateChanged(View paramView, int paramInt);
  }
  
  @Retention(RetentionPolicy.SOURCE)
  public static @interface SaveFlags {}
  
  protected static class SavedState
    extends AbsSavedState
  {
    public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.ClassLoaderCreator()
    {
      public BottomSheetBehavior.SavedState createFromParcel(Parcel paramAnonymousParcel)
      {
        return new BottomSheetBehavior.SavedState(paramAnonymousParcel, null);
      }
      
      public BottomSheetBehavior.SavedState createFromParcel(Parcel paramAnonymousParcel, ClassLoader paramAnonymousClassLoader)
      {
        return new BottomSheetBehavior.SavedState(paramAnonymousParcel, paramAnonymousClassLoader);
      }
      
      public BottomSheetBehavior.SavedState[] newArray(int paramAnonymousInt)
      {
        return new BottomSheetBehavior.SavedState[paramAnonymousInt];
      }
    };
    boolean fitToContents;
    boolean hideable;
    int peekHeight;
    boolean skipCollapsed;
    final int state;
    
    public SavedState(Parcel paramParcel)
    {
      this(paramParcel, null);
    }
    
    public SavedState(Parcel paramParcel, ClassLoader paramClassLoader)
    {
      super(paramClassLoader);
      this.state = paramParcel.readInt();
      this.peekHeight = paramParcel.readInt();
      int i = paramParcel.readInt();
      boolean bool2 = false;
      if (i == 1) {
        bool1 = true;
      } else {
        bool1 = false;
      }
      this.fitToContents = bool1;
      if (paramParcel.readInt() == 1) {
        bool1 = true;
      } else {
        bool1 = false;
      }
      this.hideable = bool1;
      boolean bool1 = bool2;
      if (paramParcel.readInt() == 1) {
        bool1 = true;
      }
      this.skipCollapsed = bool1;
    }
    
    @Deprecated
    public SavedState(Parcelable paramParcelable, int paramInt)
    {
      super();
      this.state = paramInt;
    }
    
    public SavedState(Parcelable paramParcelable, BottomSheetBehavior<?> paramBottomSheetBehavior)
    {
      super();
      this.state = paramBottomSheetBehavior.state;
      this.peekHeight = paramBottomSheetBehavior.peekHeight;
      this.fitToContents = paramBottomSheetBehavior.fitToContents;
      this.hideable = paramBottomSheetBehavior.hideable;
      this.skipCollapsed = paramBottomSheetBehavior.skipCollapsed;
    }
    
    public void writeToParcel(Parcel paramParcel, int paramInt)
    {
      super.writeToParcel(paramParcel, paramInt);
      paramParcel.writeInt(this.state);
      paramParcel.writeInt(this.peekHeight);
      paramParcel.writeInt(this.fitToContents);
      paramParcel.writeInt(this.hideable);
      paramParcel.writeInt(this.skipCollapsed);
    }
  }
  
  @Retention(RetentionPolicy.SOURCE)
  public static @interface StableState {}
  
  @Retention(RetentionPolicy.SOURCE)
  public static @interface State {}
  
  private class StateSettlingTracker
  {
    private final Runnable continueSettlingRunnable = new Runnable()
    {
      public void run()
      {
        BottomSheetBehavior.StateSettlingTracker.access$1502(BottomSheetBehavior.StateSettlingTracker.this, false);
        if ((BottomSheetBehavior.this.viewDragHelper != null) && (BottomSheetBehavior.this.viewDragHelper.continueSettling(true)))
        {
          BottomSheetBehavior.StateSettlingTracker localStateSettlingTracker = BottomSheetBehavior.StateSettlingTracker.this;
          localStateSettlingTracker.continueSettlingToState(localStateSettlingTracker.targetState);
        }
        else if (BottomSheetBehavior.this.state == 2)
        {
          BottomSheetBehavior.this.setStateInternal(BottomSheetBehavior.StateSettlingTracker.this.targetState);
        }
      }
    };
    private boolean isContinueSettlingRunnablePosted;
    private int targetState;
    
    private StateSettlingTracker() {}
    
    void continueSettlingToState(int paramInt)
    {
      if ((BottomSheetBehavior.this.viewRef != null) && (BottomSheetBehavior.this.viewRef.get() != null))
      {
        this.targetState = paramInt;
        if (!this.isContinueSettlingRunnablePosted)
        {
          ViewCompat.postOnAnimation((View)BottomSheetBehavior.this.viewRef.get(), this.continueSettlingRunnable);
          this.isContinueSettlingRunnablePosted = true;
        }
        return;
      }
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/com/google/android/material/bottomsheet/BottomSheetBehavior.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */