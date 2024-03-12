package androidx.coordinatorlayout.widget;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Region.Op;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.ClassLoaderCreator;
import android.os.Parcelable.Creator;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.SparseArray;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.BaseSavedState;
import android.view.View.MeasureSpec;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewGroup.MarginLayoutParams;
import android.view.ViewGroup.OnHierarchyChangeListener;
import android.view.ViewParent;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnPreDrawListener;
import androidx.coordinatorlayout.R.attr;
import androidx.coordinatorlayout.R.style;
import androidx.coordinatorlayout.R.styleable;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.core.util.ObjectsCompat;
import androidx.core.util.Pools.Pool;
import androidx.core.util.Pools.SynchronizedPool;
import androidx.core.view.GravityCompat;
import androidx.core.view.NestedScrollingParent2;
import androidx.core.view.NestedScrollingParent3;
import androidx.core.view.NestedScrollingParentHelper;
import androidx.core.view.OnApplyWindowInsetsListener;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.customview.view.AbsSavedState;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CoordinatorLayout
  extends ViewGroup
  implements NestedScrollingParent2, NestedScrollingParent3
{
  static final Class<?>[] CONSTRUCTOR_PARAMS = { Context.class, AttributeSet.class };
  static final int EVENT_NESTED_SCROLL = 1;
  static final int EVENT_PRE_DRAW = 0;
  static final int EVENT_VIEW_REMOVED = 2;
  static final String TAG = "CoordinatorLayout";
  static final Comparator<View> TOP_SORTED_CHILDREN_COMPARATOR;
  private static final int TYPE_ON_INTERCEPT = 0;
  private static final int TYPE_ON_TOUCH = 1;
  static final String WIDGET_PACKAGE_NAME;
  static final ThreadLocal<Map<String, Constructor<Behavior>>> sConstructors = new ThreadLocal();
  private static final Pools.Pool<Rect> sRectPool = new Pools.SynchronizedPool(12);
  private OnApplyWindowInsetsListener mApplyWindowInsetsListener;
  private final int[] mBehaviorConsumed = new int[2];
  private View mBehaviorTouchView;
  private final DirectedAcyclicGraph<View> mChildDag = new DirectedAcyclicGraph();
  private final List<View> mDependencySortedChildren = new ArrayList();
  private boolean mDisallowInterceptReset;
  private boolean mDrawStatusBarBackground;
  private boolean mIsAttachedToWindow;
  private int[] mKeylines;
  private WindowInsetsCompat mLastInsets;
  private boolean mNeedsPreDrawListener;
  private final NestedScrollingParentHelper mNestedScrollingParentHelper = new NestedScrollingParentHelper(this);
  private View mNestedScrollingTarget;
  private final int[] mNestedScrollingV2ConsumedCompat = new int[2];
  ViewGroup.OnHierarchyChangeListener mOnHierarchyChangeListener;
  private OnPreDrawListener mOnPreDrawListener;
  private Paint mScrimPaint;
  private Drawable mStatusBarBackground;
  private final List<View> mTempDependenciesList = new ArrayList();
  private final List<View> mTempList1 = new ArrayList();
  
  static
  {
    Object localObject = CoordinatorLayout.class.getPackage();
    if (localObject != null) {
      localObject = ((Package)localObject).getName();
    } else {
      localObject = null;
    }
    WIDGET_PACKAGE_NAME = (String)localObject;
    if (Build.VERSION.SDK_INT >= 21) {
      TOP_SORTED_CHILDREN_COMPARATOR = new ViewElevationComparator();
    } else {
      TOP_SORTED_CHILDREN_COMPARATOR = null;
    }
  }
  
  public CoordinatorLayout(Context paramContext)
  {
    this(paramContext, null);
  }
  
  public CoordinatorLayout(Context paramContext, AttributeSet paramAttributeSet)
  {
    this(paramContext, paramAttributeSet, R.attr.coordinatorLayoutStyle);
  }
  
  public CoordinatorLayout(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
    TypedArray localTypedArray;
    if (paramInt == 0) {
      localTypedArray = paramContext.obtainStyledAttributes(paramAttributeSet, R.styleable.CoordinatorLayout, 0, R.style.Widget_Support_CoordinatorLayout);
    } else {
      localTypedArray = paramContext.obtainStyledAttributes(paramAttributeSet, R.styleable.CoordinatorLayout, paramInt, 0);
    }
    if (Build.VERSION.SDK_INT >= 29) {
      if (paramInt == 0) {
        saveAttributeDataForStyleable(paramContext, R.styleable.CoordinatorLayout, paramAttributeSet, localTypedArray, 0, R.style.Widget_Support_CoordinatorLayout);
      } else {
        saveAttributeDataForStyleable(paramContext, R.styleable.CoordinatorLayout, paramAttributeSet, localTypedArray, paramInt, 0);
      }
    }
    paramInt = localTypedArray.getResourceId(R.styleable.CoordinatorLayout_keylines, 0);
    if (paramInt != 0)
    {
      paramContext = paramContext.getResources();
      this.mKeylines = paramContext.getIntArray(paramInt);
      float f = paramContext.getDisplayMetrics().density;
      int i = this.mKeylines.length;
      for (paramInt = 0; paramInt < i; paramInt++)
      {
        paramContext = this.mKeylines;
        paramContext[paramInt] = ((int)(paramContext[paramInt] * f));
      }
    }
    this.mStatusBarBackground = localTypedArray.getDrawable(R.styleable.CoordinatorLayout_statusBarBackground);
    localTypedArray.recycle();
    setupForInsets();
    super.setOnHierarchyChangeListener(new HierarchyChangeListener());
    if (ViewCompat.getImportantForAccessibility(this) == 0) {
      ViewCompat.setImportantForAccessibility(this, 1);
    }
  }
  
  private static Rect acquireTempRect()
  {
    Rect localRect2 = (Rect)sRectPool.acquire();
    Rect localRect1 = localRect2;
    if (localRect2 == null) {
      localRect1 = new Rect();
    }
    return localRect1;
  }
  
  private static int clamp(int paramInt1, int paramInt2, int paramInt3)
  {
    if (paramInt1 < paramInt2) {
      return paramInt2;
    }
    if (paramInt1 > paramInt3) {
      return paramInt3;
    }
    return paramInt1;
  }
  
  private void constrainChildRect(LayoutParams paramLayoutParams, Rect paramRect, int paramInt1, int paramInt2)
  {
    int i = getWidth();
    int j = getHeight();
    i = Math.max(getPaddingLeft() + paramLayoutParams.leftMargin, Math.min(paramRect.left, i - getPaddingRight() - paramInt1 - paramLayoutParams.rightMargin));
    j = Math.max(getPaddingTop() + paramLayoutParams.topMargin, Math.min(paramRect.top, j - getPaddingBottom() - paramInt2 - paramLayoutParams.bottomMargin));
    paramRect.set(i, j, i + paramInt1, j + paramInt2);
  }
  
  private WindowInsetsCompat dispatchApplyWindowInsetsToBehaviors(WindowInsetsCompat paramWindowInsetsCompat)
  {
    if (paramWindowInsetsCompat.isConsumed()) {
      return paramWindowInsetsCompat;
    }
    int i = 0;
    int j = getChildCount();
    WindowInsetsCompat localWindowInsetsCompat;
    for (;;)
    {
      localWindowInsetsCompat = paramWindowInsetsCompat;
      if (i >= j) {
        break;
      }
      View localView = getChildAt(i);
      localWindowInsetsCompat = paramWindowInsetsCompat;
      if (ViewCompat.getFitsSystemWindows(localView))
      {
        Behavior localBehavior = ((LayoutParams)localView.getLayoutParams()).getBehavior();
        localWindowInsetsCompat = paramWindowInsetsCompat;
        if (localBehavior != null)
        {
          paramWindowInsetsCompat = localBehavior.onApplyWindowInsets(this, localView, paramWindowInsetsCompat);
          localWindowInsetsCompat = paramWindowInsetsCompat;
          if (paramWindowInsetsCompat.isConsumed())
          {
            localWindowInsetsCompat = paramWindowInsetsCompat;
            break;
          }
        }
      }
      i++;
      paramWindowInsetsCompat = localWindowInsetsCompat;
    }
    return localWindowInsetsCompat;
  }
  
  private void getDesiredAnchoredChildRectWithoutConstraints(View paramView, int paramInt1, Rect paramRect1, Rect paramRect2, LayoutParams paramLayoutParams, int paramInt2, int paramInt3)
  {
    int j = GravityCompat.getAbsoluteGravity(resolveAnchoredChildGravity(paramLayoutParams.gravity), paramInt1);
    int i = GravityCompat.getAbsoluteGravity(resolveGravity(paramLayoutParams.anchorGravity), paramInt1);
    switch (i & 0x7)
    {
    default: 
      paramInt1 = paramRect1.left;
      break;
    case 5: 
      paramInt1 = paramRect1.right;
      break;
    case 1: 
      paramInt1 = paramRect1.left + paramRect1.width() / 2;
    }
    switch (i & 0x70)
    {
    default: 
      i = paramRect1.top;
      break;
    case 80: 
      i = paramRect1.bottom;
      break;
    case 16: 
      i = paramRect1.top + paramRect1.height() / 2;
    }
    switch (j & 0x7)
    {
    default: 
      paramInt1 -= paramInt2;
      break;
    case 5: 
      break;
    case 1: 
      paramInt1 -= paramInt2 / 2;
    }
    switch (j & 0x70)
    {
    default: 
      i -= paramInt3;
      break;
    case 80: 
      break;
    case 16: 
      i -= paramInt3 / 2;
    }
    paramRect2.set(paramInt1, i, paramInt1 + paramInt2, i + paramInt3);
  }
  
  private int getKeyline(int paramInt)
  {
    int[] arrayOfInt = this.mKeylines;
    if (arrayOfInt == null)
    {
      Log.e("CoordinatorLayout", "No keylines defined for " + this + " - attempted index lookup " + paramInt);
      return 0;
    }
    if ((paramInt >= 0) && (paramInt < arrayOfInt.length)) {
      return arrayOfInt[paramInt];
    }
    Log.e("CoordinatorLayout", "Keyline index " + paramInt + " out of range for " + this);
    return 0;
  }
  
  private void getTopSortedChildren(List<View> paramList)
  {
    paramList.clear();
    boolean bool = isChildrenDrawingOrderEnabled();
    int k = getChildCount();
    for (int i = k - 1; i >= 0; i--)
    {
      int j;
      if (bool) {
        j = getChildDrawingOrder(k, i);
      } else {
        j = i;
      }
      paramList.add(getChildAt(j));
    }
    Comparator localComparator = TOP_SORTED_CHILDREN_COMPARATOR;
    if (localComparator != null) {
      Collections.sort(paramList, localComparator);
    }
  }
  
  private boolean hasDependencies(View paramView)
  {
    return this.mChildDag.hasOutgoingEdges(paramView);
  }
  
  private void layoutChild(View paramView, int paramInt)
  {
    LayoutParams localLayoutParams = (LayoutParams)paramView.getLayoutParams();
    Rect localRect2 = acquireTempRect();
    localRect2.set(getPaddingLeft() + localLayoutParams.leftMargin, getPaddingTop() + localLayoutParams.topMargin, getWidth() - getPaddingRight() - localLayoutParams.rightMargin, getHeight() - getPaddingBottom() - localLayoutParams.bottomMargin);
    if ((this.mLastInsets != null) && (ViewCompat.getFitsSystemWindows(this)) && (!ViewCompat.getFitsSystemWindows(paramView)))
    {
      localRect2.left += this.mLastInsets.getSystemWindowInsetLeft();
      localRect2.top += this.mLastInsets.getSystemWindowInsetTop();
      localRect2.right -= this.mLastInsets.getSystemWindowInsetRight();
      localRect2.bottom -= this.mLastInsets.getSystemWindowInsetBottom();
    }
    Rect localRect1 = acquireTempRect();
    GravityCompat.apply(resolveGravity(localLayoutParams.gravity), paramView.getMeasuredWidth(), paramView.getMeasuredHeight(), localRect2, localRect1, paramInt);
    paramView.layout(localRect1.left, localRect1.top, localRect1.right, localRect1.bottom);
    releaseTempRect(localRect2);
    releaseTempRect(localRect1);
  }
  
  private void layoutChildWithAnchor(View paramView1, View paramView2, int paramInt)
  {
    Rect localRect1 = acquireTempRect();
    Rect localRect2 = acquireTempRect();
    try
    {
      getDescendantRect(paramView2, localRect1);
      getDesiredAnchoredChildRect(paramView1, paramInt, localRect1, localRect2);
      paramView1.layout(localRect2.left, localRect2.top, localRect2.right, localRect2.bottom);
      return;
    }
    finally
    {
      releaseTempRect(localRect1);
      releaseTempRect(localRect2);
    }
  }
  
  private void layoutChildWithKeyline(View paramView, int paramInt1, int paramInt2)
  {
    LayoutParams localLayoutParams = (LayoutParams)paramView.getLayoutParams();
    int n = GravityCompat.getAbsoluteGravity(resolveKeylineGravity(localLayoutParams.gravity), paramInt2);
    int m = getWidth();
    int k = getHeight();
    int i = paramView.getMeasuredWidth();
    int j = paramView.getMeasuredHeight();
    if (paramInt2 == 1) {
      paramInt1 = m - paramInt1;
    }
    paramInt1 = getKeyline(paramInt1) - i;
    paramInt2 = 0;
    switch (n & 0x7)
    {
    default: 
      break;
    case 5: 
      paramInt1 += i;
      break;
    case 1: 
      paramInt1 += i / 2;
    }
    switch (n & 0x70)
    {
    default: 
      break;
    case 80: 
      paramInt2 = 0 + j;
      break;
    case 16: 
      paramInt2 = 0 + j / 2;
    }
    paramInt1 = Math.max(getPaddingLeft() + localLayoutParams.leftMargin, Math.min(paramInt1, m - getPaddingRight() - i - localLayoutParams.rightMargin));
    paramInt2 = Math.max(getPaddingTop() + localLayoutParams.topMargin, Math.min(paramInt2, k - getPaddingBottom() - j - localLayoutParams.bottomMargin));
    paramView.layout(paramInt1, paramInt2, paramInt1 + i, paramInt2 + j);
  }
  
  private void offsetChildByInset(View paramView, Rect paramRect, int paramInt)
  {
    if (!ViewCompat.isLaidOut(paramView)) {
      return;
    }
    if ((paramView.getWidth() > 0) && (paramView.getHeight() > 0))
    {
      LayoutParams localLayoutParams = (LayoutParams)paramView.getLayoutParams();
      Behavior localBehavior = localLayoutParams.getBehavior();
      Rect localRect1 = acquireTempRect();
      Rect localRect2 = acquireTempRect();
      localRect2.set(paramView.getLeft(), paramView.getTop(), paramView.getRight(), paramView.getBottom());
      if ((localBehavior != null) && (localBehavior.getInsetDodgeRect(this, paramView, localRect1)))
      {
        if (!localRect2.contains(localRect1)) {
          throw new IllegalArgumentException("Rect should be within the child's bounds. Rect:" + localRect1.toShortString() + " | Bounds:" + localRect2.toShortString());
        }
      }
      else {
        localRect1.set(localRect2);
      }
      releaseTempRect(localRect2);
      if (localRect1.isEmpty())
      {
        releaseTempRect(localRect1);
        return;
      }
      int j = GravityCompat.getAbsoluteGravity(localLayoutParams.dodgeInsetEdges, paramInt);
      int i = 0;
      paramInt = i;
      int k;
      if ((j & 0x30) == 48)
      {
        k = localRect1.top - localLayoutParams.topMargin - localLayoutParams.mInsetOffsetY;
        paramInt = i;
        if (k < paramRect.top)
        {
          setInsetOffsetY(paramView, paramRect.top - k);
          paramInt = 1;
        }
      }
      i = paramInt;
      if ((j & 0x50) == 80)
      {
        k = getHeight() - localRect1.bottom - localLayoutParams.bottomMargin + localLayoutParams.mInsetOffsetY;
        i = paramInt;
        if (k < paramRect.bottom)
        {
          setInsetOffsetY(paramView, k - paramRect.bottom);
          i = 1;
        }
      }
      if (i == 0) {
        setInsetOffsetY(paramView, 0);
      }
      i = 0;
      paramInt = i;
      if ((j & 0x3) == 3)
      {
        k = localRect1.left - localLayoutParams.leftMargin - localLayoutParams.mInsetOffsetX;
        paramInt = i;
        if (k < paramRect.left)
        {
          setInsetOffsetX(paramView, paramRect.left - k);
          paramInt = 1;
        }
      }
      i = paramInt;
      if ((j & 0x5) == 5)
      {
        j = getWidth() - localRect1.right - localLayoutParams.rightMargin + localLayoutParams.mInsetOffsetX;
        i = paramInt;
        if (j < paramRect.right)
        {
          setInsetOffsetX(paramView, j - paramRect.right);
          i = 1;
        }
      }
      if (i == 0) {
        setInsetOffsetX(paramView, 0);
      }
      releaseTempRect(localRect1);
      return;
    }
  }
  
  static Behavior parseBehavior(Context paramContext, AttributeSet paramAttributeSet, String paramString)
  {
    if (TextUtils.isEmpty(paramString)) {
      return null;
    }
    Object localObject1;
    if (paramString.startsWith("."))
    {
      paramString = paramContext.getPackageName() + paramString;
    }
    else if (paramString.indexOf('.') < 0)
    {
      localObject1 = WIDGET_PACKAGE_NAME;
      if (!TextUtils.isEmpty((CharSequence)localObject1)) {
        paramString = (String)localObject1 + '.' + paramString;
      }
    }
    try
    {
      Object localObject3 = sConstructors;
      Object localObject2 = (Map)((ThreadLocal)localObject3).get();
      localObject1 = localObject2;
      if (localObject2 == null)
      {
        localObject1 = new java/util/HashMap;
        ((HashMap)localObject1).<init>();
        ((ThreadLocal)localObject3).set(localObject1);
      }
      localObject3 = (Constructor)((Map)localObject1).get(paramString);
      localObject2 = localObject3;
      if (localObject3 == null)
      {
        localObject2 = Class.forName(paramString, false, paramContext.getClassLoader()).getConstructor(CONSTRUCTOR_PARAMS);
        ((Constructor)localObject2).setAccessible(true);
        ((Map)localObject1).put(paramString, localObject2);
      }
      paramContext = (Behavior)((Constructor)localObject2).newInstance(new Object[] { paramContext, paramAttributeSet });
      return paramContext;
    }
    catch (Exception paramContext)
    {
      throw new RuntimeException("Could not inflate Behavior subclass " + paramString, paramContext);
    }
  }
  
  private boolean performIntercept(MotionEvent paramMotionEvent, int paramInt)
  {
    boolean bool1 = false;
    int k = 0;
    Object localObject1 = null;
    int n = paramMotionEvent.getActionMasked();
    List localList = this.mTempList1;
    getTopSortedChildren(localList);
    int m = localList.size();
    int j = 0;
    boolean bool2;
    for (;;)
    {
      bool2 = bool1;
      if (j >= m) {
        break;
      }
      View localView = (View)localList.get(j);
      Object localObject2 = (LayoutParams)localView.getLayoutParams();
      Behavior localBehavior = ((LayoutParams)localObject2).getBehavior();
      boolean bool3;
      int i;
      if (((bool1) || (k != 0)) && (n != 0))
      {
        bool3 = bool1;
        i = k;
        localObject2 = localObject1;
        if (localBehavior != null)
        {
          localObject2 = localObject1;
          if (localObject1 == null)
          {
            long l = SystemClock.uptimeMillis();
            localObject2 = MotionEvent.obtain(l, l, 3, 0.0F, 0.0F, 0);
          }
          switch (paramInt)
          {
          default: 
            break;
          case 1: 
            localBehavior.onTouchEvent(this, localView, (MotionEvent)localObject2);
            break;
          case 0: 
            localBehavior.onInterceptTouchEvent(this, localView, (MotionEvent)localObject2);
          }
          bool3 = bool1;
          i = k;
        }
      }
      else
      {
        bool2 = bool1;
        if (!bool1)
        {
          bool2 = bool1;
          if (localBehavior != null)
          {
            switch (paramInt)
            {
            default: 
              break;
            case 1: 
              bool1 = localBehavior.onTouchEvent(this, localView, paramMotionEvent);
              break;
            case 0: 
              bool1 = localBehavior.onInterceptTouchEvent(this, localView, paramMotionEvent);
            }
            bool2 = bool1;
            if (bool1)
            {
              this.mBehaviorTouchView = localView;
              bool2 = bool1;
            }
          }
        }
        bool3 = ((LayoutParams)localObject2).didBlockInteraction();
        bool1 = ((LayoutParams)localObject2).isBlockingInteractionBelow(this, localView);
        if ((bool1) && (!bool3)) {
          i = 1;
        } else {
          i = 0;
        }
        k = i;
        bool3 = bool2;
        i = k;
        localObject2 = localObject1;
        if (bool1)
        {
          bool3 = bool2;
          i = k;
          localObject2 = localObject1;
          if (k == 0) {
            break;
          }
        }
      }
      j++;
      bool1 = bool3;
      k = i;
      localObject1 = localObject2;
    }
    localList.clear();
    return bool2;
  }
  
  private void prepareChildren()
  {
    this.mDependencySortedChildren.clear();
    this.mChildDag.clear();
    int i = 0;
    int k = getChildCount();
    while (i < k)
    {
      View localView1 = getChildAt(i);
      LayoutParams localLayoutParams = getResolvedLayoutParams(localView1);
      localLayoutParams.findAnchorView(this, localView1);
      this.mChildDag.addNode(localView1);
      for (int j = 0; j < k; j++) {
        if (j != i)
        {
          View localView2 = getChildAt(j);
          if (localLayoutParams.dependsOn(this, localView1, localView2))
          {
            if (!this.mChildDag.contains(localView2)) {
              this.mChildDag.addNode(localView2);
            }
            this.mChildDag.addEdge(localView2, localView1);
          }
        }
      }
      i++;
    }
    this.mDependencySortedChildren.addAll(this.mChildDag.getSortedList());
    Collections.reverse(this.mDependencySortedChildren);
  }
  
  private static void releaseTempRect(Rect paramRect)
  {
    paramRect.setEmpty();
    sRectPool.release(paramRect);
  }
  
  private void resetTouchBehaviors(boolean paramBoolean)
  {
    int j = getChildCount();
    for (int i = 0; i < j; i++)
    {
      View localView = getChildAt(i);
      Behavior localBehavior = ((LayoutParams)localView.getLayoutParams()).getBehavior();
      if (localBehavior != null)
      {
        long l = SystemClock.uptimeMillis();
        MotionEvent localMotionEvent = MotionEvent.obtain(l, l, 3, 0.0F, 0.0F, 0);
        if (paramBoolean) {
          localBehavior.onInterceptTouchEvent(this, localView, localMotionEvent);
        } else {
          localBehavior.onTouchEvent(this, localView, localMotionEvent);
        }
        localMotionEvent.recycle();
      }
    }
    for (i = 0; i < j; i++) {
      ((LayoutParams)getChildAt(i).getLayoutParams()).resetTouchBehaviorTracking();
    }
    this.mBehaviorTouchView = null;
    this.mDisallowInterceptReset = false;
  }
  
  private static int resolveAnchoredChildGravity(int paramInt)
  {
    if (paramInt == 0) {
      paramInt = 17;
    }
    return paramInt;
  }
  
  private static int resolveGravity(int paramInt)
  {
    int i = paramInt;
    if ((paramInt & 0x7) == 0) {
      i = paramInt | 0x800003;
    }
    paramInt = i;
    if ((i & 0x70) == 0) {
      paramInt = i | 0x30;
    }
    return paramInt;
  }
  
  private static int resolveKeylineGravity(int paramInt)
  {
    if (paramInt == 0) {
      paramInt = 8388661;
    }
    return paramInt;
  }
  
  private void setInsetOffsetX(View paramView, int paramInt)
  {
    LayoutParams localLayoutParams = (LayoutParams)paramView.getLayoutParams();
    if (localLayoutParams.mInsetOffsetX != paramInt)
    {
      ViewCompat.offsetLeftAndRight(paramView, paramInt - localLayoutParams.mInsetOffsetX);
      localLayoutParams.mInsetOffsetX = paramInt;
    }
  }
  
  private void setInsetOffsetY(View paramView, int paramInt)
  {
    LayoutParams localLayoutParams = (LayoutParams)paramView.getLayoutParams();
    if (localLayoutParams.mInsetOffsetY != paramInt)
    {
      ViewCompat.offsetTopAndBottom(paramView, paramInt - localLayoutParams.mInsetOffsetY);
      localLayoutParams.mInsetOffsetY = paramInt;
    }
  }
  
  private void setupForInsets()
  {
    if (Build.VERSION.SDK_INT < 21) {
      return;
    }
    if (ViewCompat.getFitsSystemWindows(this))
    {
      if (this.mApplyWindowInsetsListener == null) {
        this.mApplyWindowInsetsListener = new OnApplyWindowInsetsListener()
        {
          public WindowInsetsCompat onApplyWindowInsets(View paramAnonymousView, WindowInsetsCompat paramAnonymousWindowInsetsCompat)
          {
            return CoordinatorLayout.this.setWindowInsets(paramAnonymousWindowInsetsCompat);
          }
        };
      }
      ViewCompat.setOnApplyWindowInsetsListener(this, this.mApplyWindowInsetsListener);
      setSystemUiVisibility(1280);
    }
    else
    {
      ViewCompat.setOnApplyWindowInsetsListener(this, null);
    }
  }
  
  void addPreDrawListener()
  {
    if (this.mIsAttachedToWindow)
    {
      if (this.mOnPreDrawListener == null) {
        this.mOnPreDrawListener = new OnPreDrawListener();
      }
      getViewTreeObserver().addOnPreDrawListener(this.mOnPreDrawListener);
    }
    this.mNeedsPreDrawListener = true;
  }
  
  protected boolean checkLayoutParams(ViewGroup.LayoutParams paramLayoutParams)
  {
    boolean bool;
    if (((paramLayoutParams instanceof LayoutParams)) && (super.checkLayoutParams(paramLayoutParams))) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public void dispatchDependentViewsChanged(View paramView)
  {
    List localList = this.mChildDag.getIncomingEdges(paramView);
    if ((localList != null) && (!localList.isEmpty())) {
      for (int i = 0; i < localList.size(); i++)
      {
        View localView = (View)localList.get(i);
        Behavior localBehavior = ((LayoutParams)localView.getLayoutParams()).getBehavior();
        if (localBehavior != null) {
          localBehavior.onDependentViewChanged(this, localView, paramView);
        }
      }
    }
  }
  
  public boolean doViewsOverlap(View paramView1, View paramView2)
  {
    int i = paramView1.getVisibility();
    boolean bool2 = false;
    if ((i == 0) && (paramView2.getVisibility() == 0))
    {
      Rect localRect = acquireTempRect();
      if (paramView1.getParent() != this) {
        bool1 = true;
      } else {
        bool1 = false;
      }
      getChildRect(paramView1, bool1, localRect);
      paramView1 = acquireTempRect();
      if (paramView2.getParent() != this) {
        bool1 = true;
      } else {
        bool1 = false;
      }
      getChildRect(paramView2, bool1, paramView1);
      boolean bool1 = bool2;
      try
      {
        if (localRect.left <= paramView1.right)
        {
          bool1 = bool2;
          if (localRect.top <= paramView1.bottom)
          {
            bool1 = bool2;
            if (localRect.right >= paramView1.left)
            {
              int j = localRect.bottom;
              i = paramView1.top;
              bool1 = bool2;
              if (j >= i) {
                bool1 = true;
              }
            }
          }
        }
        return bool1;
      }
      finally
      {
        releaseTempRect(localRect);
        releaseTempRect(paramView1);
      }
    }
    return false;
  }
  
  protected boolean drawChild(Canvas paramCanvas, View paramView, long paramLong)
  {
    LayoutParams localLayoutParams = (LayoutParams)paramView.getLayoutParams();
    if (localLayoutParams.mBehavior != null)
    {
      float f = localLayoutParams.mBehavior.getScrimOpacity(this, paramView);
      if (f > 0.0F)
      {
        if (this.mScrimPaint == null) {
          this.mScrimPaint = new Paint();
        }
        this.mScrimPaint.setColor(localLayoutParams.mBehavior.getScrimColor(this, paramView));
        this.mScrimPaint.setAlpha(clamp(Math.round(255.0F * f), 0, 255));
        int i = paramCanvas.save();
        if (paramView.isOpaque()) {
          paramCanvas.clipRect(paramView.getLeft(), paramView.getTop(), paramView.getRight(), paramView.getBottom(), Region.Op.DIFFERENCE);
        }
        paramCanvas.drawRect(getPaddingLeft(), getPaddingTop(), getWidth() - getPaddingRight(), getHeight() - getPaddingBottom(), this.mScrimPaint);
        paramCanvas.restoreToCount(i);
      }
    }
    return super.drawChild(paramCanvas, paramView, paramLong);
  }
  
  protected void drawableStateChanged()
  {
    super.drawableStateChanged();
    int[] arrayOfInt = getDrawableState();
    boolean bool2 = false;
    Drawable localDrawable = this.mStatusBarBackground;
    boolean bool1 = bool2;
    if (localDrawable != null)
    {
      bool1 = bool2;
      if (localDrawable.isStateful()) {
        bool1 = false | localDrawable.setState(arrayOfInt);
      }
    }
    if (bool1) {
      invalidate();
    }
  }
  
  void ensurePreDrawListener()
  {
    int m = 0;
    int j = getChildCount();
    int k;
    for (int i = 0;; i++)
    {
      k = m;
      if (i >= j) {
        break;
      }
      if (hasDependencies(getChildAt(i)))
      {
        k = 1;
        break;
      }
    }
    if (k != this.mNeedsPreDrawListener) {
      if (k != 0) {
        addPreDrawListener();
      } else {
        removePreDrawListener();
      }
    }
  }
  
  protected LayoutParams generateDefaultLayoutParams()
  {
    return new LayoutParams(-2, -2);
  }
  
  public LayoutParams generateLayoutParams(AttributeSet paramAttributeSet)
  {
    return new LayoutParams(getContext(), paramAttributeSet);
  }
  
  protected LayoutParams generateLayoutParams(ViewGroup.LayoutParams paramLayoutParams)
  {
    if ((paramLayoutParams instanceof LayoutParams)) {
      return new LayoutParams((LayoutParams)paramLayoutParams);
    }
    if ((paramLayoutParams instanceof ViewGroup.MarginLayoutParams)) {
      return new LayoutParams((ViewGroup.MarginLayoutParams)paramLayoutParams);
    }
    return new LayoutParams(paramLayoutParams);
  }
  
  void getChildRect(View paramView, boolean paramBoolean, Rect paramRect)
  {
    if ((!paramView.isLayoutRequested()) && (paramView.getVisibility() != 8))
    {
      if (paramBoolean) {
        getDescendantRect(paramView, paramRect);
      } else {
        paramRect.set(paramView.getLeft(), paramView.getTop(), paramView.getRight(), paramView.getBottom());
      }
      return;
    }
    paramRect.setEmpty();
  }
  
  public List<View> getDependencies(View paramView)
  {
    paramView = this.mChildDag.getOutgoingEdges(paramView);
    this.mTempDependenciesList.clear();
    if (paramView != null) {
      this.mTempDependenciesList.addAll(paramView);
    }
    return this.mTempDependenciesList;
  }
  
  final List<View> getDependencySortedChildren()
  {
    prepareChildren();
    return Collections.unmodifiableList(this.mDependencySortedChildren);
  }
  
  public List<View> getDependents(View paramView)
  {
    paramView = this.mChildDag.getIncomingEdges(paramView);
    this.mTempDependenciesList.clear();
    if (paramView != null) {
      this.mTempDependenciesList.addAll(paramView);
    }
    return this.mTempDependenciesList;
  }
  
  void getDescendantRect(View paramView, Rect paramRect)
  {
    ViewGroupUtils.getDescendantRect(this, paramView, paramRect);
  }
  
  void getDesiredAnchoredChildRect(View paramView, int paramInt, Rect paramRect1, Rect paramRect2)
  {
    LayoutParams localLayoutParams = (LayoutParams)paramView.getLayoutParams();
    int j = paramView.getMeasuredWidth();
    int i = paramView.getMeasuredHeight();
    getDesiredAnchoredChildRectWithoutConstraints(paramView, paramInt, paramRect1, paramRect2, localLayoutParams, j, i);
    constrainChildRect(localLayoutParams, paramRect2, j, i);
  }
  
  void getLastChildRect(View paramView, Rect paramRect)
  {
    paramRect.set(((LayoutParams)paramView.getLayoutParams()).getLastChildRect());
  }
  
  public final WindowInsetsCompat getLastWindowInsets()
  {
    return this.mLastInsets;
  }
  
  public int getNestedScrollAxes()
  {
    return this.mNestedScrollingParentHelper.getNestedScrollAxes();
  }
  
  LayoutParams getResolvedLayoutParams(View paramView)
  {
    LayoutParams localLayoutParams = (LayoutParams)paramView.getLayoutParams();
    if (!localLayoutParams.mBehaviorResolved) {
      if ((paramView instanceof AttachedBehavior))
      {
        paramView = ((AttachedBehavior)paramView).getBehavior();
        if (paramView == null) {
          Log.e("CoordinatorLayout", "Attached behavior class is null");
        }
        localLayoutParams.setBehavior(paramView);
        localLayoutParams.mBehaviorResolved = true;
      }
      else
      {
        Class localClass = paramView.getClass();
        paramView = null;
        View localView;
        for (;;)
        {
          localView = paramView;
          if (localClass == null) {
            break;
          }
          DefaultBehavior localDefaultBehavior = (DefaultBehavior)localClass.getAnnotation(DefaultBehavior.class);
          paramView = localDefaultBehavior;
          localView = paramView;
          if (localDefaultBehavior != null) {
            break;
          }
          localClass = localClass.getSuperclass();
        }
        if (localView != null) {
          try
          {
            localLayoutParams.setBehavior((Behavior)localView.value().getDeclaredConstructor(new Class[0]).newInstance(new Object[0]));
          }
          catch (Exception paramView)
          {
            Log.e("CoordinatorLayout", "Default behavior class " + localView.value().getName() + " could not be instantiated. Did you forget a default constructor?", paramView);
          }
        }
        localLayoutParams.mBehaviorResolved = true;
      }
    }
    return localLayoutParams;
  }
  
  public Drawable getStatusBarBackground()
  {
    return this.mStatusBarBackground;
  }
  
  protected int getSuggestedMinimumHeight()
  {
    return Math.max(super.getSuggestedMinimumHeight(), getPaddingTop() + getPaddingBottom());
  }
  
  protected int getSuggestedMinimumWidth()
  {
    return Math.max(super.getSuggestedMinimumWidth(), getPaddingLeft() + getPaddingRight());
  }
  
  public boolean isPointInChildBounds(View paramView, int paramInt1, int paramInt2)
  {
    Rect localRect = acquireTempRect();
    getDescendantRect(paramView, localRect);
    try
    {
      boolean bool = localRect.contains(paramInt1, paramInt2);
      return bool;
    }
    finally
    {
      releaseTempRect(localRect);
    }
  }
  
  void offsetChildToAnchor(View paramView, int paramInt)
  {
    LayoutParams localLayoutParams = (LayoutParams)paramView.getLayoutParams();
    if (localLayoutParams.mAnchorView != null)
    {
      Rect localRect2 = acquireTempRect();
      Rect localRect3 = acquireTempRect();
      Rect localRect1 = acquireTempRect();
      getDescendantRect(localLayoutParams.mAnchorView, localRect2);
      int i = 0;
      getChildRect(paramView, false, localRect3);
      int j = paramView.getMeasuredWidth();
      int k = paramView.getMeasuredHeight();
      getDesiredAnchoredChildRectWithoutConstraints(paramView, paramInt, localRect2, localRect1, localLayoutParams, j, k);
      if (localRect1.left == localRect3.left)
      {
        paramInt = i;
        if (localRect1.top == localRect3.top) {}
      }
      else
      {
        paramInt = 1;
      }
      constrainChildRect(localLayoutParams, localRect1, j, k);
      j = localRect1.left - localRect3.left;
      i = localRect1.top - localRect3.top;
      if (j != 0) {
        ViewCompat.offsetLeftAndRight(paramView, j);
      }
      if (i != 0) {
        ViewCompat.offsetTopAndBottom(paramView, i);
      }
      if (paramInt != 0)
      {
        Behavior localBehavior = localLayoutParams.getBehavior();
        if (localBehavior != null) {
          localBehavior.onDependentViewChanged(this, paramView, localLayoutParams.mAnchorView);
        }
      }
      releaseTempRect(localRect2);
      releaseTempRect(localRect3);
      releaseTempRect(localRect1);
    }
  }
  
  public void onAttachedToWindow()
  {
    super.onAttachedToWindow();
    resetTouchBehaviors(false);
    if (this.mNeedsPreDrawListener)
    {
      if (this.mOnPreDrawListener == null) {
        this.mOnPreDrawListener = new OnPreDrawListener();
      }
      getViewTreeObserver().addOnPreDrawListener(this.mOnPreDrawListener);
    }
    if ((this.mLastInsets == null) && (ViewCompat.getFitsSystemWindows(this))) {
      ViewCompat.requestApplyInsets(this);
    }
    this.mIsAttachedToWindow = true;
  }
  
  final void onChildViewsChanged(int paramInt)
  {
    int k = ViewCompat.getLayoutDirection(this);
    int m = this.mDependencySortedChildren.size();
    Rect localRect1 = acquireTempRect();
    Rect localRect2 = acquireTempRect();
    Rect localRect3 = acquireTempRect();
    for (int i = 0; i < m; i++)
    {
      View localView1 = (View)this.mDependencySortedChildren.get(i);
      LayoutParams localLayoutParams = (LayoutParams)localView1.getLayoutParams();
      if ((paramInt != 0) || (localView1.getVisibility() != 8))
      {
        Object localObject;
        for (int j = 0; j < i; j++)
        {
          localObject = (View)this.mDependencySortedChildren.get(j);
          if (localLayoutParams.mAnchorDirectChild == localObject) {
            offsetChildToAnchor(localView1, k);
          }
        }
        getChildRect(localView1, true, localRect2);
        if ((localLayoutParams.insetEdge != 0) && (!localRect2.isEmpty()))
        {
          j = GravityCompat.getAbsoluteGravity(localLayoutParams.insetEdge, k);
          switch (j & 0x70)
          {
          default: 
            break;
          case 80: 
            localRect1.bottom = Math.max(localRect1.bottom, getHeight() - localRect2.top);
            break;
          case 48: 
            localRect1.top = Math.max(localRect1.top, localRect2.bottom);
          }
          switch (j & 0x7)
          {
          case 4: 
          default: 
            break;
          case 5: 
            localRect1.right = Math.max(localRect1.right, getWidth() - localRect2.left);
            break;
          case 3: 
            localRect1.left = Math.max(localRect1.left, localRect2.right);
          }
        }
        if ((localLayoutParams.dodgeInsetEdges != 0) && (localView1.getVisibility() == 0)) {
          offsetChildByInset(localView1, localRect1, k);
        }
        if (paramInt != 2)
        {
          getLastChildRect(localView1, localRect3);
          if (!localRect3.equals(localRect2)) {
            recordLastChildRect(localView1, localRect2);
          }
        }
        else
        {
          for (j = i + 1; j < m; j++)
          {
            View localView2 = (View)this.mDependencySortedChildren.get(j);
            localLayoutParams = (LayoutParams)localView2.getLayoutParams();
            localObject = localLayoutParams.getBehavior();
            if ((localObject != null) && (((Behavior)localObject).layoutDependsOn(this, localView2, localView1))) {
              if ((paramInt == 0) && (localLayoutParams.getChangedAfterNestedScroll()))
              {
                localLayoutParams.resetChangedAfterNestedScroll();
              }
              else
              {
                boolean bool;
                switch (paramInt)
                {
                default: 
                  bool = ((Behavior)localObject).onDependentViewChanged(this, localView2, localView1);
                  break;
                case 2: 
                  ((Behavior)localObject).onDependentViewRemoved(this, localView2, localView1);
                  bool = true;
                }
                if (paramInt == 1) {
                  localLayoutParams.setChangedAfterNestedScroll(bool);
                }
              }
            }
          }
        }
      }
    }
    releaseTempRect(localRect1);
    releaseTempRect(localRect2);
    releaseTempRect(localRect3);
  }
  
  public void onDetachedFromWindow()
  {
    super.onDetachedFromWindow();
    resetTouchBehaviors(false);
    if ((this.mNeedsPreDrawListener) && (this.mOnPreDrawListener != null)) {
      getViewTreeObserver().removeOnPreDrawListener(this.mOnPreDrawListener);
    }
    View localView = this.mNestedScrollingTarget;
    if (localView != null) {
      onStopNestedScroll(localView);
    }
    this.mIsAttachedToWindow = false;
  }
  
  public void onDraw(Canvas paramCanvas)
  {
    super.onDraw(paramCanvas);
    if ((this.mDrawStatusBarBackground) && (this.mStatusBarBackground != null))
    {
      WindowInsetsCompat localWindowInsetsCompat = this.mLastInsets;
      int i;
      if (localWindowInsetsCompat != null) {
        i = localWindowInsetsCompat.getSystemWindowInsetTop();
      } else {
        i = 0;
      }
      if (i > 0)
      {
        this.mStatusBarBackground.setBounds(0, 0, getWidth(), i);
        this.mStatusBarBackground.draw(paramCanvas);
      }
    }
  }
  
  public boolean onInterceptTouchEvent(MotionEvent paramMotionEvent)
  {
    int i = paramMotionEvent.getActionMasked();
    if (i == 0) {
      resetTouchBehaviors(true);
    }
    boolean bool = performIntercept(paramMotionEvent, 0);
    if ((i == 1) || (i == 3)) {
      resetTouchBehaviors(true);
    }
    return bool;
  }
  
  protected void onLayout(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    paramInt2 = ViewCompat.getLayoutDirection(this);
    paramInt3 = this.mDependencySortedChildren.size();
    for (paramInt1 = 0; paramInt1 < paramInt3; paramInt1++)
    {
      View localView = (View)this.mDependencySortedChildren.get(paramInt1);
      if (localView.getVisibility() != 8)
      {
        Behavior localBehavior = ((LayoutParams)localView.getLayoutParams()).getBehavior();
        if ((localBehavior == null) || (!localBehavior.onLayoutChild(this, localView, paramInt2))) {
          onLayoutChild(localView, paramInt2);
        }
      }
    }
  }
  
  public void onLayoutChild(View paramView, int paramInt)
  {
    LayoutParams localLayoutParams = (LayoutParams)paramView.getLayoutParams();
    if (!localLayoutParams.checkAnchorChanged())
    {
      if (localLayoutParams.mAnchorView != null) {
        layoutChildWithAnchor(paramView, localLayoutParams.mAnchorView, paramInt);
      } else if (localLayoutParams.keyline >= 0) {
        layoutChildWithKeyline(paramView, localLayoutParams.keyline, paramInt);
      } else {
        layoutChild(paramView, paramInt);
      }
      return;
    }
    throw new IllegalStateException("An anchor may not be changed after CoordinatorLayout measurement begins before layout is complete.");
  }
  
  protected void onMeasure(int paramInt1, int paramInt2)
  {
    prepareChildren();
    ensurePreDrawListener();
    int i9 = getPaddingLeft();
    int i10 = getPaddingTop();
    int i7 = getPaddingRight();
    int i11 = getPaddingBottom();
    int i8 = ViewCompat.getLayoutDirection(this);
    int k = 1;
    int j;
    if (i8 == 1) {
      j = 1;
    } else {
      j = 0;
    }
    int i13 = View.MeasureSpec.getMode(paramInt1);
    int i12 = View.MeasureSpec.getSize(paramInt1);
    int m = View.MeasureSpec.getMode(paramInt2);
    int i14 = View.MeasureSpec.getSize(paramInt2);
    int i3 = getSuggestedMinimumWidth();
    int i4 = getSuggestedMinimumHeight();
    if ((this.mLastInsets == null) || (!ViewCompat.getFitsSystemWindows(this))) {
      k = 0;
    }
    int n = this.mDependencySortedChildren.size();
    int i1 = 0;
    int i2 = 0;
    while (i1 < n)
    {
      View localView = (View)this.mDependencySortedChildren.get(i1);
      if (localView.getVisibility() != 8)
      {
        LayoutParams localLayoutParams = (LayoutParams)localView.getLayoutParams();
        int i = 0;
        int i6;
        int i5;
        if ((localLayoutParams.keyline >= 0) && (i13 != 0))
        {
          i6 = getKeyline(localLayoutParams.keyline);
          i5 = GravityCompat.getAbsoluteGravity(resolveKeylineGravity(localLayoutParams.gravity), i8) & 0x7;
          if (((i5 == 3) && (j == 0)) || ((i5 == 5) && (j != 0))) {
            i = Math.max(0, i12 - i7 - i6);
          } else if (((i5 == 5) && (j == 0)) || ((i5 == 3) && (j != 0))) {
            i = Math.max(0, i6 - i9);
          } else {}
        }
        if ((k != 0) && (!ViewCompat.getFitsSystemWindows(localView)))
        {
          i6 = this.mLastInsets.getSystemWindowInsetLeft();
          int i16 = this.mLastInsets.getSystemWindowInsetRight();
          int i15 = this.mLastInsets.getSystemWindowInsetTop();
          i5 = this.mLastInsets.getSystemWindowInsetBottom();
          i6 = View.MeasureSpec.makeMeasureSpec(i12 - (i6 + i16), i13);
          i5 = View.MeasureSpec.makeMeasureSpec(i14 - (i15 + i5), m);
        }
        else
        {
          i6 = paramInt1;
          i5 = paramInt2;
        }
        Behavior localBehavior = localLayoutParams.getBehavior();
        if (localBehavior != null) {
          if (localBehavior.onMeasureChild(this, localView, i6, i, i5, 0)) {
            break label427;
          }
        }
        onMeasureChild(localView, i6, i, i5, 0);
        label427:
        i3 = Math.max(i3, i9 + i7 + localView.getMeasuredWidth() + localLayoutParams.leftMargin + localLayoutParams.rightMargin);
        i4 = Math.max(i4, i10 + i11 + localView.getMeasuredHeight() + localLayoutParams.topMargin + localLayoutParams.bottomMargin);
        i2 = View.combineMeasuredStates(i2, localView.getMeasuredState());
      }
      i1++;
    }
    setMeasuredDimension(View.resolveSizeAndState(i3, paramInt1, 0xFF000000 & i2), View.resolveSizeAndState(i4, paramInt2, i2 << 16));
  }
  
  public void onMeasureChild(View paramView, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    measureChildWithMargins(paramView, paramInt1, paramInt2, paramInt3, paramInt4);
  }
  
  public boolean onNestedFling(View paramView, float paramFloat1, float paramFloat2, boolean paramBoolean)
  {
    int j = getChildCount();
    boolean bool2 = false;
    int i = 0;
    while (i < j)
    {
      View localView = getChildAt(i);
      boolean bool1;
      if (localView.getVisibility() == 8)
      {
        bool1 = bool2;
      }
      else
      {
        Object localObject = (LayoutParams)localView.getLayoutParams();
        if (!((LayoutParams)localObject).isNestedScrollAccepted(0))
        {
          bool1 = bool2;
        }
        else
        {
          localObject = ((LayoutParams)localObject).getBehavior();
          bool1 = bool2;
          if (localObject != null) {
            bool1 = ((Behavior)localObject).onNestedFling(this, localView, paramView, paramFloat1, paramFloat2, paramBoolean) | bool2;
          }
        }
      }
      i++;
      bool2 = bool1;
    }
    if (bool2) {
      onChildViewsChanged(1);
    }
    return bool2;
  }
  
  public boolean onNestedPreFling(View paramView, float paramFloat1, float paramFloat2)
  {
    boolean bool2 = false;
    int j = getChildCount();
    int i = 0;
    while (i < j)
    {
      View localView = getChildAt(i);
      boolean bool1;
      if (localView.getVisibility() == 8)
      {
        bool1 = bool2;
      }
      else
      {
        Object localObject = (LayoutParams)localView.getLayoutParams();
        if (!((LayoutParams)localObject).isNestedScrollAccepted(0))
        {
          bool1 = bool2;
        }
        else
        {
          localObject = ((LayoutParams)localObject).getBehavior();
          bool1 = bool2;
          if (localObject != null) {
            bool1 = bool2 | ((Behavior)localObject).onNestedPreFling(this, localView, paramView, paramFloat1, paramFloat2);
          }
        }
      }
      i++;
      bool2 = bool1;
    }
    return bool2;
  }
  
  public void onNestedPreScroll(View paramView, int paramInt1, int paramInt2, int[] paramArrayOfInt)
  {
    onNestedPreScroll(paramView, paramInt1, paramInt2, paramArrayOfInt, 0);
  }
  
  public void onNestedPreScroll(View paramView, int paramInt1, int paramInt2, int[] paramArrayOfInt, int paramInt3)
  {
    int i1 = getChildCount();
    int m = 0;
    int j = 0;
    int i = 0;
    for (int k = 0; k < i1; k++)
    {
      Object localObject1 = getChildAt(k);
      if (((View)localObject1).getVisibility() != 8)
      {
        Object localObject2 = (LayoutParams)((View)localObject1).getLayoutParams();
        if (((LayoutParams)localObject2).isNestedScrollAccepted(paramInt3))
        {
          localObject2 = ((LayoutParams)localObject2).getBehavior();
          if (localObject2 != null)
          {
            int[] arrayOfInt = this.mBehaviorConsumed;
            arrayOfInt[0] = 0;
            arrayOfInt[1] = 0;
            ((Behavior)localObject2).onNestedPreScroll(this, (View)localObject1, paramView, paramInt1, paramInt2, arrayOfInt, paramInt3);
            localObject1 = this.mBehaviorConsumed;
            if (paramInt1 > 0) {
              i = Math.max(m, localObject1[0]);
            } else {
              i = Math.min(m, localObject1[0]);
            }
            localObject1 = this.mBehaviorConsumed;
            if (paramInt2 > 0) {
              j = Math.max(j, localObject1[1]);
            } else {
              j = Math.min(j, localObject1[1]);
            }
            int n = 1;
            m = i;
            i = n;
          }
        }
      }
    }
    paramArrayOfInt[0] = m;
    paramArrayOfInt[1] = j;
    if (i != 0) {
      onChildViewsChanged(1);
    }
  }
  
  public void onNestedScroll(View paramView, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    onNestedScroll(paramView, paramInt1, paramInt2, paramInt3, paramInt4, 0);
  }
  
  public void onNestedScroll(View paramView, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5)
  {
    onNestedScroll(paramView, paramInt1, paramInt2, paramInt3, paramInt4, 0, this.mNestedScrollingV2ConsumedCompat);
  }
  
  public void onNestedScroll(View paramView, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int[] paramArrayOfInt)
  {
    int m = getChildCount();
    int i = 0;
    int n = 0;
    int j = 0;
    for (int k = 0; k < m; k++)
    {
      Object localObject1 = getChildAt(k);
      if (((View)localObject1).getVisibility() != 8)
      {
        Object localObject2 = (LayoutParams)((View)localObject1).getLayoutParams();
        if (((LayoutParams)localObject2).isNestedScrollAccepted(paramInt5))
        {
          Behavior localBehavior = ((LayoutParams)localObject2).getBehavior();
          if (localBehavior != null)
          {
            localObject2 = this.mBehaviorConsumed;
            localObject2[0] = 0;
            localObject2[1] = 0;
            localBehavior.onNestedScroll(this, (View)localObject1, paramView, paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, (int[])localObject2);
            localObject1 = this.mBehaviorConsumed;
            if (paramInt3 > 0) {
              i = Math.max(n, localObject1[0]);
            } else {
              i = Math.min(n, localObject1[0]);
            }
            localObject1 = this.mBehaviorConsumed;
            if (paramInt4 > 0) {
              j = Math.max(j, localObject1[1]);
            } else {
              j = Math.min(j, localObject1[1]);
            }
            n = i;
            i = 1;
          }
        }
      }
    }
    paramArrayOfInt[0] += n;
    paramArrayOfInt[1] += j;
    if (i != 0) {
      onChildViewsChanged(1);
    }
  }
  
  public void onNestedScrollAccepted(View paramView1, View paramView2, int paramInt)
  {
    onNestedScrollAccepted(paramView1, paramView2, paramInt, 0);
  }
  
  public void onNestedScrollAccepted(View paramView1, View paramView2, int paramInt1, int paramInt2)
  {
    this.mNestedScrollingParentHelper.onNestedScrollAccepted(paramView1, paramView2, paramInt1, paramInt2);
    this.mNestedScrollingTarget = paramView2;
    int j = getChildCount();
    for (int i = 0; i < j; i++)
    {
      View localView = getChildAt(i);
      Object localObject = (LayoutParams)localView.getLayoutParams();
      if (((LayoutParams)localObject).isNestedScrollAccepted(paramInt2))
      {
        localObject = ((LayoutParams)localObject).getBehavior();
        if (localObject != null) {
          ((Behavior)localObject).onNestedScrollAccepted(this, localView, paramView1, paramView2, paramInt1, paramInt2);
        }
      }
    }
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
    SparseArray localSparseArray = paramParcelable.behaviorStates;
    int i = 0;
    int j = getChildCount();
    while (i < j)
    {
      View localView = getChildAt(i);
      int k = localView.getId();
      paramParcelable = getResolvedLayoutParams(localView).getBehavior();
      if ((k != -1) && (paramParcelable != null))
      {
        Parcelable localParcelable = (Parcelable)localSparseArray.get(k);
        if (localParcelable != null) {
          paramParcelable.onRestoreInstanceState(this, localView, localParcelable);
        }
      }
      i++;
    }
  }
  
  protected Parcelable onSaveInstanceState()
  {
    SavedState localSavedState = new SavedState(super.onSaveInstanceState());
    SparseArray localSparseArray = new SparseArray();
    int i = 0;
    int j = getChildCount();
    while (i < j)
    {
      View localView = getChildAt(i);
      int k = localView.getId();
      Object localObject = ((LayoutParams)localView.getLayoutParams()).getBehavior();
      if ((k != -1) && (localObject != null))
      {
        localObject = ((Behavior)localObject).onSaveInstanceState(this, localView);
        if (localObject != null) {
          localSparseArray.append(k, localObject);
        }
      }
      i++;
    }
    localSavedState.behaviorStates = localSparseArray;
    return localSavedState;
  }
  
  public boolean onStartNestedScroll(View paramView1, View paramView2, int paramInt)
  {
    return onStartNestedScroll(paramView1, paramView2, paramInt, 0);
  }
  
  public boolean onStartNestedScroll(View paramView1, View paramView2, int paramInt1, int paramInt2)
  {
    int j = getChildCount();
    boolean bool1 = false;
    for (int i = 0; i < j; i++)
    {
      View localView = getChildAt(i);
      if (localView.getVisibility() != 8)
      {
        LayoutParams localLayoutParams = (LayoutParams)localView.getLayoutParams();
        Behavior localBehavior = localLayoutParams.getBehavior();
        if (localBehavior != null)
        {
          boolean bool2 = localBehavior.onStartNestedScroll(this, localView, paramView1, paramView2, paramInt1, paramInt2);
          localLayoutParams.setNestedScrollAccepted(paramInt2, bool2);
          bool1 |= bool2;
        }
        else
        {
          localLayoutParams.setNestedScrollAccepted(paramInt2, false);
        }
      }
    }
    return bool1;
  }
  
  public void onStopNestedScroll(View paramView)
  {
    onStopNestedScroll(paramView, 0);
  }
  
  public void onStopNestedScroll(View paramView, int paramInt)
  {
    this.mNestedScrollingParentHelper.onStopNestedScroll(paramView, paramInt);
    int j = getChildCount();
    for (int i = 0; i < j; i++)
    {
      View localView = getChildAt(i);
      LayoutParams localLayoutParams = (LayoutParams)localView.getLayoutParams();
      if (localLayoutParams.isNestedScrollAccepted(paramInt))
      {
        Behavior localBehavior = localLayoutParams.getBehavior();
        if (localBehavior != null) {
          localBehavior.onStopNestedScroll(this, localView, paramView, paramInt);
        }
        localLayoutParams.resetNestedScroll(paramInt);
        localLayoutParams.resetChangedAfterNestedScroll();
      }
    }
    this.mNestedScrollingTarget = null;
  }
  
  public boolean onTouchEvent(MotionEvent paramMotionEvent)
  {
    boolean bool4 = false;
    boolean bool2 = false;
    Object localObject2 = null;
    Object localObject1 = null;
    int i = paramMotionEvent.getActionMasked();
    boolean bool1;
    boolean bool3;
    if (this.mBehaviorTouchView == null)
    {
      boolean bool5 = performIntercept(paramMotionEvent, 1);
      bool2 = bool5;
      bool1 = bool4;
      bool3 = bool2;
      if (!bool5) {}
    }
    else
    {
      Behavior localBehavior = ((LayoutParams)this.mBehaviorTouchView.getLayoutParams()).getBehavior();
      bool1 = bool4;
      bool3 = bool2;
      if (localBehavior != null)
      {
        bool1 = localBehavior.onTouchEvent(this, this.mBehaviorTouchView, paramMotionEvent);
        bool3 = bool2;
      }
    }
    if (this.mBehaviorTouchView == null)
    {
      bool2 = bool1 | super.onTouchEvent(paramMotionEvent);
      paramMotionEvent = (MotionEvent)localObject2;
    }
    else
    {
      bool2 = bool1;
      paramMotionEvent = (MotionEvent)localObject2;
      if (bool3)
      {
        paramMotionEvent = (MotionEvent)localObject1;
        if (0 == 0)
        {
          long l = SystemClock.uptimeMillis();
          paramMotionEvent = MotionEvent.obtain(l, l, 3, 0.0F, 0.0F, 0);
        }
        super.onTouchEvent(paramMotionEvent);
        bool2 = bool1;
      }
    }
    if (paramMotionEvent != null) {
      paramMotionEvent.recycle();
    }
    if ((i == 1) || (i == 3)) {
      resetTouchBehaviors(false);
    }
    return bool2;
  }
  
  void recordLastChildRect(View paramView, Rect paramRect)
  {
    ((LayoutParams)paramView.getLayoutParams()).setLastChildRect(paramRect);
  }
  
  void removePreDrawListener()
  {
    if ((this.mIsAttachedToWindow) && (this.mOnPreDrawListener != null)) {
      getViewTreeObserver().removeOnPreDrawListener(this.mOnPreDrawListener);
    }
    this.mNeedsPreDrawListener = false;
  }
  
  public boolean requestChildRectangleOnScreen(View paramView, Rect paramRect, boolean paramBoolean)
  {
    Behavior localBehavior = ((LayoutParams)paramView.getLayoutParams()).getBehavior();
    if ((localBehavior != null) && (localBehavior.onRequestChildRectangleOnScreen(this, paramView, paramRect, paramBoolean))) {
      return true;
    }
    return super.requestChildRectangleOnScreen(paramView, paramRect, paramBoolean);
  }
  
  public void requestDisallowInterceptTouchEvent(boolean paramBoolean)
  {
    super.requestDisallowInterceptTouchEvent(paramBoolean);
    if ((paramBoolean) && (!this.mDisallowInterceptReset))
    {
      resetTouchBehaviors(false);
      this.mDisallowInterceptReset = true;
    }
  }
  
  public void setFitsSystemWindows(boolean paramBoolean)
  {
    super.setFitsSystemWindows(paramBoolean);
    setupForInsets();
  }
  
  public void setOnHierarchyChangeListener(ViewGroup.OnHierarchyChangeListener paramOnHierarchyChangeListener)
  {
    this.mOnHierarchyChangeListener = paramOnHierarchyChangeListener;
  }
  
  public void setStatusBarBackground(Drawable paramDrawable)
  {
    Drawable localDrawable2 = this.mStatusBarBackground;
    if (localDrawable2 != paramDrawable)
    {
      Drawable localDrawable1 = null;
      if (localDrawable2 != null) {
        localDrawable2.setCallback(null);
      }
      if (paramDrawable != null) {
        localDrawable1 = paramDrawable.mutate();
      }
      this.mStatusBarBackground = localDrawable1;
      if (localDrawable1 != null)
      {
        if (localDrawable1.isStateful()) {
          this.mStatusBarBackground.setState(getDrawableState());
        }
        DrawableCompat.setLayoutDirection(this.mStatusBarBackground, ViewCompat.getLayoutDirection(this));
        paramDrawable = this.mStatusBarBackground;
        boolean bool;
        if (getVisibility() == 0) {
          bool = true;
        } else {
          bool = false;
        }
        paramDrawable.setVisible(bool, false);
        this.mStatusBarBackground.setCallback(this);
      }
      ViewCompat.postInvalidateOnAnimation(this);
    }
  }
  
  public void setStatusBarBackgroundColor(int paramInt)
  {
    setStatusBarBackground(new ColorDrawable(paramInt));
  }
  
  public void setStatusBarBackgroundResource(int paramInt)
  {
    Drawable localDrawable;
    if (paramInt != 0) {
      localDrawable = ContextCompat.getDrawable(getContext(), paramInt);
    } else {
      localDrawable = null;
    }
    setStatusBarBackground(localDrawable);
  }
  
  public void setVisibility(int paramInt)
  {
    super.setVisibility(paramInt);
    boolean bool;
    if (paramInt == 0) {
      bool = true;
    } else {
      bool = false;
    }
    Drawable localDrawable = this.mStatusBarBackground;
    if ((localDrawable != null) && (localDrawable.isVisible() != bool)) {
      this.mStatusBarBackground.setVisible(bool, false);
    }
  }
  
  final WindowInsetsCompat setWindowInsets(WindowInsetsCompat paramWindowInsetsCompat)
  {
    WindowInsetsCompat localWindowInsetsCompat = paramWindowInsetsCompat;
    if (!ObjectsCompat.equals(this.mLastInsets, paramWindowInsetsCompat))
    {
      this.mLastInsets = paramWindowInsetsCompat;
      boolean bool2 = true;
      boolean bool1;
      if ((paramWindowInsetsCompat != null) && (paramWindowInsetsCompat.getSystemWindowInsetTop() > 0)) {
        bool1 = true;
      } else {
        bool1 = false;
      }
      this.mDrawStatusBarBackground = bool1;
      if ((!bool1) && (getBackground() == null)) {
        bool1 = bool2;
      } else {
        bool1 = false;
      }
      setWillNotDraw(bool1);
      localWindowInsetsCompat = dispatchApplyWindowInsetsToBehaviors(paramWindowInsetsCompat);
      requestLayout();
    }
    return localWindowInsetsCompat;
  }
  
  protected boolean verifyDrawable(Drawable paramDrawable)
  {
    boolean bool;
    if ((!super.verifyDrawable(paramDrawable)) && (paramDrawable != this.mStatusBarBackground)) {
      bool = false;
    } else {
      bool = true;
    }
    return bool;
  }
  
  public static abstract interface AttachedBehavior
  {
    public abstract CoordinatorLayout.Behavior getBehavior();
  }
  
  public static abstract class Behavior<V extends View>
  {
    public Behavior() {}
    
    public Behavior(Context paramContext, AttributeSet paramAttributeSet) {}
    
    public static Object getTag(View paramView)
    {
      return ((CoordinatorLayout.LayoutParams)paramView.getLayoutParams()).mBehaviorTag;
    }
    
    public static void setTag(View paramView, Object paramObject)
    {
      ((CoordinatorLayout.LayoutParams)paramView.getLayoutParams()).mBehaviorTag = paramObject;
    }
    
    public boolean blocksInteractionBelow(CoordinatorLayout paramCoordinatorLayout, V paramV)
    {
      boolean bool;
      if (getScrimOpacity(paramCoordinatorLayout, paramV) > 0.0F) {
        bool = true;
      } else {
        bool = false;
      }
      return bool;
    }
    
    public boolean getInsetDodgeRect(CoordinatorLayout paramCoordinatorLayout, V paramV, Rect paramRect)
    {
      return false;
    }
    
    public int getScrimColor(CoordinatorLayout paramCoordinatorLayout, V paramV)
    {
      return -16777216;
    }
    
    public float getScrimOpacity(CoordinatorLayout paramCoordinatorLayout, V paramV)
    {
      return 0.0F;
    }
    
    public boolean layoutDependsOn(CoordinatorLayout paramCoordinatorLayout, V paramV, View paramView)
    {
      return false;
    }
    
    public WindowInsetsCompat onApplyWindowInsets(CoordinatorLayout paramCoordinatorLayout, V paramV, WindowInsetsCompat paramWindowInsetsCompat)
    {
      return paramWindowInsetsCompat;
    }
    
    public void onAttachedToLayoutParams(CoordinatorLayout.LayoutParams paramLayoutParams) {}
    
    public boolean onDependentViewChanged(CoordinatorLayout paramCoordinatorLayout, V paramV, View paramView)
    {
      return false;
    }
    
    public void onDependentViewRemoved(CoordinatorLayout paramCoordinatorLayout, V paramV, View paramView) {}
    
    public void onDetachedFromLayoutParams() {}
    
    public boolean onInterceptTouchEvent(CoordinatorLayout paramCoordinatorLayout, V paramV, MotionEvent paramMotionEvent)
    {
      return false;
    }
    
    public boolean onLayoutChild(CoordinatorLayout paramCoordinatorLayout, V paramV, int paramInt)
    {
      return false;
    }
    
    public boolean onMeasureChild(CoordinatorLayout paramCoordinatorLayout, V paramV, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
    {
      return false;
    }
    
    public boolean onNestedFling(CoordinatorLayout paramCoordinatorLayout, V paramV, View paramView, float paramFloat1, float paramFloat2, boolean paramBoolean)
    {
      return false;
    }
    
    public boolean onNestedPreFling(CoordinatorLayout paramCoordinatorLayout, V paramV, View paramView, float paramFloat1, float paramFloat2)
    {
      return false;
    }
    
    @Deprecated
    public void onNestedPreScroll(CoordinatorLayout paramCoordinatorLayout, V paramV, View paramView, int paramInt1, int paramInt2, int[] paramArrayOfInt) {}
    
    public void onNestedPreScroll(CoordinatorLayout paramCoordinatorLayout, V paramV, View paramView, int paramInt1, int paramInt2, int[] paramArrayOfInt, int paramInt3)
    {
      if (paramInt3 == 0) {
        onNestedPreScroll(paramCoordinatorLayout, paramV, paramView, paramInt1, paramInt2, paramArrayOfInt);
      }
    }
    
    @Deprecated
    public void onNestedScroll(CoordinatorLayout paramCoordinatorLayout, V paramV, View paramView, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {}
    
    @Deprecated
    public void onNestedScroll(CoordinatorLayout paramCoordinatorLayout, V paramV, View paramView, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5)
    {
      if (paramInt5 == 0) {
        onNestedScroll(paramCoordinatorLayout, paramV, paramView, paramInt1, paramInt2, paramInt3, paramInt4);
      }
    }
    
    public void onNestedScroll(CoordinatorLayout paramCoordinatorLayout, V paramV, View paramView, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int[] paramArrayOfInt)
    {
      paramArrayOfInt[0] += paramInt3;
      paramArrayOfInt[1] += paramInt4;
      onNestedScroll(paramCoordinatorLayout, paramV, paramView, paramInt1, paramInt2, paramInt3, paramInt4, paramInt5);
    }
    
    @Deprecated
    public void onNestedScrollAccepted(CoordinatorLayout paramCoordinatorLayout, V paramV, View paramView1, View paramView2, int paramInt) {}
    
    public void onNestedScrollAccepted(CoordinatorLayout paramCoordinatorLayout, V paramV, View paramView1, View paramView2, int paramInt1, int paramInt2)
    {
      if (paramInt2 == 0) {
        onNestedScrollAccepted(paramCoordinatorLayout, paramV, paramView1, paramView2, paramInt1);
      }
    }
    
    public boolean onRequestChildRectangleOnScreen(CoordinatorLayout paramCoordinatorLayout, V paramV, Rect paramRect, boolean paramBoolean)
    {
      return false;
    }
    
    public void onRestoreInstanceState(CoordinatorLayout paramCoordinatorLayout, V paramV, Parcelable paramParcelable) {}
    
    public Parcelable onSaveInstanceState(CoordinatorLayout paramCoordinatorLayout, V paramV)
    {
      return View.BaseSavedState.EMPTY_STATE;
    }
    
    @Deprecated
    public boolean onStartNestedScroll(CoordinatorLayout paramCoordinatorLayout, V paramV, View paramView1, View paramView2, int paramInt)
    {
      return false;
    }
    
    public boolean onStartNestedScroll(CoordinatorLayout paramCoordinatorLayout, V paramV, View paramView1, View paramView2, int paramInt1, int paramInt2)
    {
      if (paramInt2 == 0) {
        return onStartNestedScroll(paramCoordinatorLayout, paramV, paramView1, paramView2, paramInt1);
      }
      return false;
    }
    
    @Deprecated
    public void onStopNestedScroll(CoordinatorLayout paramCoordinatorLayout, V paramV, View paramView) {}
    
    public void onStopNestedScroll(CoordinatorLayout paramCoordinatorLayout, V paramV, View paramView, int paramInt)
    {
      if (paramInt == 0) {
        onStopNestedScroll(paramCoordinatorLayout, paramV, paramView);
      }
    }
    
    public boolean onTouchEvent(CoordinatorLayout paramCoordinatorLayout, V paramV, MotionEvent paramMotionEvent)
    {
      return false;
    }
  }
  
  @Deprecated
  @Retention(RetentionPolicy.RUNTIME)
  public static @interface DefaultBehavior
  {
    Class<? extends CoordinatorLayout.Behavior> value();
  }
  
  @Retention(RetentionPolicy.SOURCE)
  public static @interface DispatchChangeEvent {}
  
  private class HierarchyChangeListener
    implements ViewGroup.OnHierarchyChangeListener
  {
    HierarchyChangeListener() {}
    
    public void onChildViewAdded(View paramView1, View paramView2)
    {
      if (CoordinatorLayout.this.mOnHierarchyChangeListener != null) {
        CoordinatorLayout.this.mOnHierarchyChangeListener.onChildViewAdded(paramView1, paramView2);
      }
    }
    
    public void onChildViewRemoved(View paramView1, View paramView2)
    {
      CoordinatorLayout.this.onChildViewsChanged(2);
      if (CoordinatorLayout.this.mOnHierarchyChangeListener != null) {
        CoordinatorLayout.this.mOnHierarchyChangeListener.onChildViewRemoved(paramView1, paramView2);
      }
    }
  }
  
  public static class LayoutParams
    extends ViewGroup.MarginLayoutParams
  {
    public int anchorGravity = 0;
    public int dodgeInsetEdges = 0;
    public int gravity = 0;
    public int insetEdge = 0;
    public int keyline = -1;
    View mAnchorDirectChild;
    int mAnchorId = -1;
    View mAnchorView;
    CoordinatorLayout.Behavior mBehavior;
    boolean mBehaviorResolved = false;
    Object mBehaviorTag;
    private boolean mDidAcceptNestedScrollNonTouch;
    private boolean mDidAcceptNestedScrollTouch;
    private boolean mDidBlockInteraction;
    private boolean mDidChangeAfterNestedScroll;
    int mInsetOffsetX;
    int mInsetOffsetY;
    final Rect mLastChildRect = new Rect();
    
    public LayoutParams(int paramInt1, int paramInt2)
    {
      super(paramInt2);
    }
    
    LayoutParams(Context paramContext, AttributeSet paramAttributeSet)
    {
      super(paramAttributeSet);
      TypedArray localTypedArray = paramContext.obtainStyledAttributes(paramAttributeSet, R.styleable.CoordinatorLayout_Layout);
      this.gravity = localTypedArray.getInteger(R.styleable.CoordinatorLayout_Layout_android_layout_gravity, 0);
      this.mAnchorId = localTypedArray.getResourceId(R.styleable.CoordinatorLayout_Layout_layout_anchor, -1);
      this.anchorGravity = localTypedArray.getInteger(R.styleable.CoordinatorLayout_Layout_layout_anchorGravity, 0);
      this.keyline = localTypedArray.getInteger(R.styleable.CoordinatorLayout_Layout_layout_keyline, -1);
      this.insetEdge = localTypedArray.getInt(R.styleable.CoordinatorLayout_Layout_layout_insetEdge, 0);
      this.dodgeInsetEdges = localTypedArray.getInt(R.styleable.CoordinatorLayout_Layout_layout_dodgeInsetEdges, 0);
      boolean bool = localTypedArray.hasValue(R.styleable.CoordinatorLayout_Layout_layout_behavior);
      this.mBehaviorResolved = bool;
      if (bool) {
        this.mBehavior = CoordinatorLayout.parseBehavior(paramContext, paramAttributeSet, localTypedArray.getString(R.styleable.CoordinatorLayout_Layout_layout_behavior));
      }
      localTypedArray.recycle();
      paramContext = this.mBehavior;
      if (paramContext != null) {
        paramContext.onAttachedToLayoutParams(this);
      }
    }
    
    public LayoutParams(ViewGroup.LayoutParams paramLayoutParams)
    {
      super();
    }
    
    public LayoutParams(ViewGroup.MarginLayoutParams paramMarginLayoutParams)
    {
      super();
    }
    
    public LayoutParams(LayoutParams paramLayoutParams)
    {
      super();
    }
    
    private void resolveAnchorView(View paramView, CoordinatorLayout paramCoordinatorLayout)
    {
      Object localObject = paramCoordinatorLayout.findViewById(this.mAnchorId);
      this.mAnchorView = ((View)localObject);
      if (localObject != null)
      {
        if (localObject == paramCoordinatorLayout)
        {
          if (paramCoordinatorLayout.isInEditMode())
          {
            this.mAnchorDirectChild = null;
            this.mAnchorView = null;
            return;
          }
          throw new IllegalStateException("View can not be anchored to the the parent CoordinatorLayout");
        }
        View localView = this.mAnchorView;
        for (localObject = ((View)localObject).getParent(); (localObject != paramCoordinatorLayout) && (localObject != null); localObject = ((ViewParent)localObject).getParent())
        {
          if (localObject == paramView)
          {
            if (paramCoordinatorLayout.isInEditMode())
            {
              this.mAnchorDirectChild = null;
              this.mAnchorView = null;
              return;
            }
            throw new IllegalStateException("Anchor must not be a descendant of the anchored view");
          }
          if ((localObject instanceof View)) {
            localView = (View)localObject;
          }
        }
        this.mAnchorDirectChild = localView;
        return;
      }
      if (paramCoordinatorLayout.isInEditMode())
      {
        this.mAnchorDirectChild = null;
        this.mAnchorView = null;
        return;
      }
      throw new IllegalStateException("Could not find CoordinatorLayout descendant view with id " + paramCoordinatorLayout.getResources().getResourceName(this.mAnchorId) + " to anchor view " + paramView);
    }
    
    private boolean shouldDodge(View paramView, int paramInt)
    {
      int i = GravityCompat.getAbsoluteGravity(((LayoutParams)paramView.getLayoutParams()).insetEdge, paramInt);
      boolean bool;
      if ((i != 0) && ((GravityCompat.getAbsoluteGravity(this.dodgeInsetEdges, paramInt) & i) == i)) {
        bool = true;
      } else {
        bool = false;
      }
      return bool;
    }
    
    private boolean verifyAnchorView(View paramView, CoordinatorLayout paramCoordinatorLayout)
    {
      if (this.mAnchorView.getId() != this.mAnchorId) {
        return false;
      }
      View localView = this.mAnchorView;
      ViewParent localViewParent = this.mAnchorView.getParent();
      while (localViewParent != paramCoordinatorLayout) {
        if ((localViewParent != null) && (localViewParent != paramView))
        {
          if ((localViewParent instanceof View)) {
            localView = (View)localViewParent;
          }
          localViewParent = localViewParent.getParent();
        }
        else
        {
          this.mAnchorDirectChild = null;
          this.mAnchorView = null;
          return false;
        }
      }
      this.mAnchorDirectChild = localView;
      return true;
    }
    
    boolean checkAnchorChanged()
    {
      boolean bool;
      if ((this.mAnchorView == null) && (this.mAnchorId != -1)) {
        bool = true;
      } else {
        bool = false;
      }
      return bool;
    }
    
    boolean dependsOn(CoordinatorLayout paramCoordinatorLayout, View paramView1, View paramView2)
    {
      if ((paramView2 != this.mAnchorDirectChild) && (!shouldDodge(paramView2, ViewCompat.getLayoutDirection(paramCoordinatorLayout))))
      {
        CoordinatorLayout.Behavior localBehavior = this.mBehavior;
        if ((localBehavior == null) || (!localBehavior.layoutDependsOn(paramCoordinatorLayout, paramView1, paramView2)))
        {
          bool = false;
          break label54;
        }
      }
      boolean bool = true;
      label54:
      return bool;
    }
    
    boolean didBlockInteraction()
    {
      if (this.mBehavior == null) {
        this.mDidBlockInteraction = false;
      }
      return this.mDidBlockInteraction;
    }
    
    View findAnchorView(CoordinatorLayout paramCoordinatorLayout, View paramView)
    {
      if (this.mAnchorId == -1)
      {
        this.mAnchorDirectChild = null;
        this.mAnchorView = null;
        return null;
      }
      if ((this.mAnchorView == null) || (!verifyAnchorView(paramView, paramCoordinatorLayout))) {
        resolveAnchorView(paramView, paramCoordinatorLayout);
      }
      return this.mAnchorView;
    }
    
    public int getAnchorId()
    {
      return this.mAnchorId;
    }
    
    public CoordinatorLayout.Behavior getBehavior()
    {
      return this.mBehavior;
    }
    
    boolean getChangedAfterNestedScroll()
    {
      return this.mDidChangeAfterNestedScroll;
    }
    
    Rect getLastChildRect()
    {
      return this.mLastChildRect;
    }
    
    void invalidateAnchor()
    {
      this.mAnchorDirectChild = null;
      this.mAnchorView = null;
    }
    
    boolean isBlockingInteractionBelow(CoordinatorLayout paramCoordinatorLayout, View paramView)
    {
      boolean bool2 = this.mDidBlockInteraction;
      if (bool2) {
        return true;
      }
      CoordinatorLayout.Behavior localBehavior = this.mBehavior;
      if (localBehavior != null) {
        bool1 = localBehavior.blocksInteractionBelow(paramCoordinatorLayout, paramView);
      } else {
        bool1 = false;
      }
      boolean bool1 = bool2 | bool1;
      this.mDidBlockInteraction = bool1;
      return bool1;
    }
    
    boolean isNestedScrollAccepted(int paramInt)
    {
      switch (paramInt)
      {
      default: 
        return false;
      case 1: 
        return this.mDidAcceptNestedScrollNonTouch;
      }
      return this.mDidAcceptNestedScrollTouch;
    }
    
    void resetChangedAfterNestedScroll()
    {
      this.mDidChangeAfterNestedScroll = false;
    }
    
    void resetNestedScroll(int paramInt)
    {
      setNestedScrollAccepted(paramInt, false);
    }
    
    void resetTouchBehaviorTracking()
    {
      this.mDidBlockInteraction = false;
    }
    
    public void setAnchorId(int paramInt)
    {
      invalidateAnchor();
      this.mAnchorId = paramInt;
    }
    
    public void setBehavior(CoordinatorLayout.Behavior paramBehavior)
    {
      CoordinatorLayout.Behavior localBehavior = this.mBehavior;
      if (localBehavior != paramBehavior)
      {
        if (localBehavior != null) {
          localBehavior.onDetachedFromLayoutParams();
        }
        this.mBehavior = paramBehavior;
        this.mBehaviorTag = null;
        this.mBehaviorResolved = true;
        if (paramBehavior != null) {
          paramBehavior.onAttachedToLayoutParams(this);
        }
      }
    }
    
    void setChangedAfterNestedScroll(boolean paramBoolean)
    {
      this.mDidChangeAfterNestedScroll = paramBoolean;
    }
    
    void setLastChildRect(Rect paramRect)
    {
      this.mLastChildRect.set(paramRect);
    }
    
    void setNestedScrollAccepted(int paramInt, boolean paramBoolean)
    {
      switch (paramInt)
      {
      default: 
        break;
      case 1: 
        this.mDidAcceptNestedScrollNonTouch = paramBoolean;
        break;
      case 0: 
        this.mDidAcceptNestedScrollTouch = paramBoolean;
      }
    }
  }
  
  class OnPreDrawListener
    implements ViewTreeObserver.OnPreDrawListener
  {
    OnPreDrawListener() {}
    
    public boolean onPreDraw()
    {
      CoordinatorLayout.this.onChildViewsChanged(0);
      return true;
    }
  }
  
  protected static class SavedState
    extends AbsSavedState
  {
    public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.ClassLoaderCreator()
    {
      public CoordinatorLayout.SavedState createFromParcel(Parcel paramAnonymousParcel)
      {
        return new CoordinatorLayout.SavedState(paramAnonymousParcel, null);
      }
      
      public CoordinatorLayout.SavedState createFromParcel(Parcel paramAnonymousParcel, ClassLoader paramAnonymousClassLoader)
      {
        return new CoordinatorLayout.SavedState(paramAnonymousParcel, paramAnonymousClassLoader);
      }
      
      public CoordinatorLayout.SavedState[] newArray(int paramAnonymousInt)
      {
        return new CoordinatorLayout.SavedState[paramAnonymousInt];
      }
    };
    SparseArray<Parcelable> behaviorStates;
    
    public SavedState(Parcel paramParcel, ClassLoader paramClassLoader)
    {
      super(paramClassLoader);
      int j = paramParcel.readInt();
      int[] arrayOfInt = new int[j];
      paramParcel.readIntArray(arrayOfInt);
      paramParcel = paramParcel.readParcelableArray(paramClassLoader);
      this.behaviorStates = new SparseArray(j);
      for (int i = 0; i < j; i++) {
        this.behaviorStates.append(arrayOfInt[i], paramParcel[i]);
      }
    }
    
    public SavedState(Parcelable paramParcelable)
    {
      super();
    }
    
    public void writeToParcel(Parcel paramParcel, int paramInt)
    {
      super.writeToParcel(paramParcel, paramInt);
      Object localObject = this.behaviorStates;
      int i;
      if (localObject != null) {
        i = ((SparseArray)localObject).size();
      } else {
        i = 0;
      }
      paramParcel.writeInt(i);
      localObject = new int[i];
      Parcelable[] arrayOfParcelable = new Parcelable[i];
      for (int j = 0; j < i; j++)
      {
        localObject[j] = this.behaviorStates.keyAt(j);
        arrayOfParcelable[j] = ((Parcelable)this.behaviorStates.valueAt(j));
      }
      paramParcel.writeIntArray((int[])localObject);
      paramParcel.writeParcelableArray(arrayOfParcelable, paramInt);
    }
  }
  
  static class ViewElevationComparator
    implements Comparator<View>
  {
    public int compare(View paramView1, View paramView2)
    {
      float f1 = ViewCompat.getZ(paramView1);
      float f2 = ViewCompat.getZ(paramView2);
      if (f1 > f2) {
        return -1;
      }
      if (f1 < f2) {
        return 1;
      }
      return 0;
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/coordinatorlayout/widget/CoordinatorLayout.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */