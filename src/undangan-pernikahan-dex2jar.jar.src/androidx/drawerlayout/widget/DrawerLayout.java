package androidx.drawerlayout.widget;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.ClassLoaderCreator;
import android.os.Parcelable.Creator;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnApplyWindowInsetsListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewGroup.MarginLayoutParams;
import android.view.ViewParent;
import android.view.WindowInsets;
import android.view.accessibility.AccessibilityEvent;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.core.view.AccessibilityDelegateCompat;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat.AccessibilityActionCompat;
import androidx.core.view.accessibility.AccessibilityViewCommand;
import androidx.core.view.accessibility.AccessibilityViewCommand.CommandArguments;
import androidx.customview.view.AbsSavedState;
import androidx.customview.widget.Openable;
import androidx.customview.widget.ViewDragHelper;
import androidx.customview.widget.ViewDragHelper.Callback;
import androidx.drawerlayout.R.attr;
import androidx.drawerlayout.R.dimen;
import androidx.drawerlayout.R.styleable;
import java.util.ArrayList;
import java.util.List;
import mt.Log229316;
import mt.Log5ECF72;
import mt.LogE84000;

public class DrawerLayout
  extends ViewGroup
  implements Openable
{
  private static final String ACCESSIBILITY_CLASS_NAME = "androidx.drawerlayout.widget.DrawerLayout";
  private static final boolean ALLOW_EDGE_LOCK = false;
  static final boolean CAN_HIDE_DESCENDANTS;
  private static final boolean CHILDREN_DISALLOW_INTERCEPT = true;
  private static final int DEFAULT_SCRIM_COLOR = -1728053248;
  static final int[] LAYOUT_ATTRS;
  public static final int LOCK_MODE_LOCKED_CLOSED = 1;
  public static final int LOCK_MODE_LOCKED_OPEN = 2;
  public static final int LOCK_MODE_UNDEFINED = 3;
  public static final int LOCK_MODE_UNLOCKED = 0;
  private static final int MIN_DRAWER_MARGIN = 64;
  private static final int MIN_FLING_VELOCITY = 400;
  private static final int PEEK_DELAY = 160;
  private static final boolean SET_DRAWER_SHADOW_FROM_ELEVATION;
  public static final int STATE_DRAGGING = 1;
  public static final int STATE_IDLE = 0;
  public static final int STATE_SETTLING = 2;
  private static final String TAG = "DrawerLayout";
  private static final int[] THEME_ATTRS;
  private static final float TOUCH_SLOP_SENSITIVITY = 1.0F;
  private static boolean sEdgeSizeUsingSystemGestureInsets;
  private final AccessibilityViewCommand mActionDismiss = new AccessibilityViewCommand()
  {
    public boolean perform(View paramAnonymousView, AccessibilityViewCommand.CommandArguments paramAnonymousCommandArguments)
    {
      if ((DrawerLayout.this.isDrawerOpen(paramAnonymousView)) && (DrawerLayout.this.getDrawerLockMode(paramAnonymousView) != 2))
      {
        DrawerLayout.this.closeDrawer(paramAnonymousView);
        return true;
      }
      return false;
    }
  };
  private final ChildAccessibilityDelegate mChildAccessibilityDelegate = new ChildAccessibilityDelegate();
  private Rect mChildHitRect;
  private Matrix mChildInvertedMatrix;
  private boolean mChildrenCanceledTouch;
  private boolean mDrawStatusBarBackground;
  private float mDrawerElevation;
  private int mDrawerState;
  private boolean mFirstLayout = true;
  private boolean mInLayout;
  private float mInitialMotionX;
  private float mInitialMotionY;
  private Object mLastInsets;
  private final ViewDragCallback mLeftCallback;
  private final ViewDragHelper mLeftDragger;
  private DrawerListener mListener;
  private List<DrawerListener> mListeners;
  private int mLockModeEnd = 3;
  private int mLockModeLeft = 3;
  private int mLockModeRight = 3;
  private int mLockModeStart = 3;
  private int mMinDrawerMargin;
  private final ArrayList<View> mNonDrawerViews;
  private final ViewDragCallback mRightCallback;
  private final ViewDragHelper mRightDragger;
  private int mScrimColor = -1728053248;
  private float mScrimOpacity;
  private Paint mScrimPaint = new Paint();
  private Drawable mShadowEnd = null;
  private Drawable mShadowLeft = null;
  private Drawable mShadowLeftResolved;
  private Drawable mShadowRight = null;
  private Drawable mShadowRightResolved;
  private Drawable mShadowStart = null;
  private Drawable mStatusBarBackground;
  private CharSequence mTitleLeft;
  private CharSequence mTitleRight;
  
  static
  {
    boolean bool2 = true;
    THEME_ATTRS = new int[] { 16843828 };
    LAYOUT_ATTRS = new int[] { 16842931 };
    boolean bool1;
    if (Build.VERSION.SDK_INT >= 19) {
      bool1 = true;
    } else {
      bool1 = false;
    }
    CAN_HIDE_DESCENDANTS = bool1;
    if (Build.VERSION.SDK_INT >= 21) {
      bool1 = true;
    } else {
      bool1 = false;
    }
    SET_DRAWER_SHADOW_FROM_ELEVATION = bool1;
    if (Build.VERSION.SDK_INT >= 29) {
      bool1 = bool2;
    } else {
      bool1 = false;
    }
    sEdgeSizeUsingSystemGestureInsets = bool1;
  }
  
  public DrawerLayout(Context paramContext)
  {
    this(paramContext, null);
  }
  
  public DrawerLayout(Context paramContext, AttributeSet paramAttributeSet)
  {
    this(paramContext, paramAttributeSet, R.attr.drawerLayoutStyle);
  }
  
  public DrawerLayout(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
    setDescendantFocusability(262144);
    float f = getResources().getDisplayMetrics().density;
    this.mMinDrawerMargin = ((int)(64.0F * f + 0.5F));
    f = 400.0F * f;
    ViewDragCallback localViewDragCallback = new ViewDragCallback(3);
    this.mLeftCallback = localViewDragCallback;
    Object localObject = new ViewDragCallback(5);
    this.mRightCallback = ((ViewDragCallback)localObject);
    ViewDragHelper localViewDragHelper = ViewDragHelper.create(this, 1.0F, localViewDragCallback);
    this.mLeftDragger = localViewDragHelper;
    localViewDragHelper.setEdgeTrackingEnabled(1);
    localViewDragHelper.setMinVelocity(f);
    localViewDragCallback.setDragger(localViewDragHelper);
    localViewDragHelper = ViewDragHelper.create(this, 1.0F, (ViewDragHelper.Callback)localObject);
    this.mRightDragger = localViewDragHelper;
    localViewDragHelper.setEdgeTrackingEnabled(2);
    localViewDragHelper.setMinVelocity(f);
    ((ViewDragCallback)localObject).setDragger(localViewDragHelper);
    setFocusableInTouchMode(true);
    ViewCompat.setImportantForAccessibility(this, 1);
    ViewCompat.setAccessibilityDelegate(this, new AccessibilityDelegate());
    setMotionEventSplittingEnabled(false);
    if (ViewCompat.getFitsSystemWindows(this))
    {
      if (Build.VERSION.SDK_INT >= 21)
      {
        setOnApplyWindowInsetsListener(new View.OnApplyWindowInsetsListener()
        {
          public WindowInsets onApplyWindowInsets(View paramAnonymousView, WindowInsets paramAnonymousWindowInsets)
          {
            paramAnonymousView = (DrawerLayout)paramAnonymousView;
            boolean bool;
            if (paramAnonymousWindowInsets.getSystemWindowInsetTop() > 0) {
              bool = true;
            } else {
              bool = false;
            }
            paramAnonymousView.setChildInsets(paramAnonymousWindowInsets, bool);
            return paramAnonymousWindowInsets.consumeSystemWindowInsets();
          }
        });
        setSystemUiVisibility(1280);
        localObject = paramContext.obtainStyledAttributes(THEME_ATTRS);
      }
      try
      {
        this.mStatusBarBackground = ((TypedArray)localObject).getDrawable(0);
        ((TypedArray)localObject).recycle();
      }
      finally
      {
        ((TypedArray)localObject).recycle();
      }
    }
    paramAttributeSet = paramContext.obtainStyledAttributes(paramAttributeSet, R.styleable.DrawerLayout, paramInt, 0);
    try
    {
      if (paramAttributeSet.hasValue(R.styleable.DrawerLayout_elevation)) {
        this.mDrawerElevation = paramAttributeSet.getDimension(R.styleable.DrawerLayout_elevation, 0.0F);
      } else {
        this.mDrawerElevation = getResources().getDimension(R.dimen.def_drawer_elevation);
      }
      paramAttributeSet.recycle();
      this.mNonDrawerViews = new ArrayList();
      return;
    }
    finally
    {
      paramAttributeSet.recycle();
    }
  }
  
  private boolean dispatchTransformedGenericPointerEvent(MotionEvent paramMotionEvent, View paramView)
  {
    boolean bool;
    if (!paramView.getMatrix().isIdentity())
    {
      paramMotionEvent = getTransformedMotionEvent(paramMotionEvent, paramView);
      bool = paramView.dispatchGenericMotionEvent(paramMotionEvent);
      paramMotionEvent.recycle();
    }
    else
    {
      float f2 = getScrollX() - paramView.getLeft();
      float f1 = getScrollY() - paramView.getTop();
      paramMotionEvent.offsetLocation(f2, f1);
      bool = paramView.dispatchGenericMotionEvent(paramMotionEvent);
      paramMotionEvent.offsetLocation(-f2, -f1);
    }
    return bool;
  }
  
  private MotionEvent getTransformedMotionEvent(MotionEvent paramMotionEvent, View paramView)
  {
    float f1 = getScrollX() - paramView.getLeft();
    float f2 = getScrollY() - paramView.getTop();
    paramMotionEvent = MotionEvent.obtain(paramMotionEvent);
    paramMotionEvent.offsetLocation(f1, f2);
    paramView = paramView.getMatrix();
    if (!paramView.isIdentity())
    {
      if (this.mChildInvertedMatrix == null) {
        this.mChildInvertedMatrix = new Matrix();
      }
      paramView.invert(this.mChildInvertedMatrix);
      paramMotionEvent.transform(this.mChildInvertedMatrix);
    }
    return paramMotionEvent;
  }
  
  static String gravityToString(int paramInt)
  {
    if ((paramInt & 0x3) == 3) {
      return "LEFT";
    }
    if ((paramInt & 0x5) == 5) {
      return "RIGHT";
    }
    String str = Integer.toHexString(paramInt);
    Log5ECF72.a(str);
    LogE84000.a(str);
    Log229316.a(str);
    return str;
  }
  
  private static boolean hasOpaqueBackground(View paramView)
  {
    paramView = paramView.getBackground();
    boolean bool = false;
    if (paramView != null)
    {
      if (paramView.getOpacity() == -1) {
        bool = true;
      }
      return bool;
    }
    return false;
  }
  
  private boolean hasPeekingDrawer()
  {
    int j = getChildCount();
    for (int i = 0; i < j; i++) {
      if (((LayoutParams)getChildAt(i).getLayoutParams()).isPeeking) {
        return true;
      }
    }
    return false;
  }
  
  private boolean hasVisibleDrawer()
  {
    boolean bool;
    if (findVisibleDrawer() != null) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  static boolean includeChildForAccessibility(View paramView)
  {
    boolean bool;
    if ((ViewCompat.getImportantForAccessibility(paramView) != 4) && (ViewCompat.getImportantForAccessibility(paramView) != 2)) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  private boolean isInBoundsOfChild(float paramFloat1, float paramFloat2, View paramView)
  {
    if (this.mChildHitRect == null) {
      this.mChildHitRect = new Rect();
    }
    paramView.getHitRect(this.mChildHitRect);
    return this.mChildHitRect.contains((int)paramFloat1, (int)paramFloat2);
  }
  
  private void mirror(Drawable paramDrawable, int paramInt)
  {
    if ((paramDrawable != null) && (DrawableCompat.isAutoMirrored(paramDrawable))) {
      DrawableCompat.setLayoutDirection(paramDrawable, paramInt);
    }
  }
  
  private Drawable resolveLeftShadow()
  {
    int i = ViewCompat.getLayoutDirection(this);
    Drawable localDrawable;
    if (i == 0)
    {
      localDrawable = this.mShadowStart;
      if (localDrawable != null)
      {
        mirror(localDrawable, i);
        return this.mShadowStart;
      }
    }
    else
    {
      localDrawable = this.mShadowEnd;
      if (localDrawable != null)
      {
        mirror(localDrawable, i);
        return this.mShadowEnd;
      }
    }
    return this.mShadowLeft;
  }
  
  private Drawable resolveRightShadow()
  {
    int i = ViewCompat.getLayoutDirection(this);
    Drawable localDrawable;
    if (i == 0)
    {
      localDrawable = this.mShadowEnd;
      if (localDrawable != null)
      {
        mirror(localDrawable, i);
        return this.mShadowEnd;
      }
    }
    else
    {
      localDrawable = this.mShadowStart;
      if (localDrawable != null)
      {
        mirror(localDrawable, i);
        return this.mShadowStart;
      }
    }
    return this.mShadowRight;
  }
  
  private void resolveShadowDrawables()
  {
    if (SET_DRAWER_SHADOW_FROM_ELEVATION) {
      return;
    }
    this.mShadowLeftResolved = resolveLeftShadow();
    this.mShadowRightResolved = resolveRightShadow();
  }
  
  private void updateChildAccessibilityAction(View paramView)
  {
    ViewCompat.removeAccessibilityAction(paramView, AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_DISMISS.getId());
    if ((isDrawerOpen(paramView)) && (getDrawerLockMode(paramView) != 2)) {
      ViewCompat.replaceAccessibilityAction(paramView, AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_DISMISS, null, this.mActionDismiss);
    }
  }
  
  private void updateChildrenImportantForAccessibility(View paramView, boolean paramBoolean)
  {
    int j = getChildCount();
    for (int i = 0; i < j; i++)
    {
      View localView = getChildAt(i);
      if (((!paramBoolean) && (!isDrawerView(localView))) || ((paramBoolean) && (localView == paramView))) {
        ViewCompat.setImportantForAccessibility(localView, 1);
      } else {
        ViewCompat.setImportantForAccessibility(localView, 4);
      }
    }
  }
  
  public void addDrawerListener(DrawerListener paramDrawerListener)
  {
    if (paramDrawerListener == null) {
      return;
    }
    if (this.mListeners == null) {
      this.mListeners = new ArrayList();
    }
    this.mListeners.add(paramDrawerListener);
  }
  
  public void addFocusables(ArrayList<View> paramArrayList, int paramInt1, int paramInt2)
  {
    if (getDescendantFocusability() == 393216) {
      return;
    }
    int k = getChildCount();
    int j = 0;
    View localView;
    for (int i = 0; i < k; i++)
    {
      localView = getChildAt(i);
      if (isDrawerView(localView))
      {
        if (isDrawerOpen(localView))
        {
          j = 1;
          localView.addFocusables(paramArrayList, paramInt1, paramInt2);
        }
      }
      else {
        this.mNonDrawerViews.add(localView);
      }
    }
    if (j == 0)
    {
      j = this.mNonDrawerViews.size();
      for (i = 0; i < j; i++)
      {
        localView = (View)this.mNonDrawerViews.get(i);
        if (localView.getVisibility() == 0) {
          localView.addFocusables(paramArrayList, paramInt1, paramInt2);
        }
      }
    }
    this.mNonDrawerViews.clear();
  }
  
  public void addView(View paramView, int paramInt, ViewGroup.LayoutParams paramLayoutParams)
  {
    super.addView(paramView, paramInt, paramLayoutParams);
    if ((findOpenDrawer() == null) && (!isDrawerView(paramView))) {
      ViewCompat.setImportantForAccessibility(paramView, 1);
    } else {
      ViewCompat.setImportantForAccessibility(paramView, 4);
    }
    if (!CAN_HIDE_DESCENDANTS) {
      ViewCompat.setAccessibilityDelegate(paramView, this.mChildAccessibilityDelegate);
    }
  }
  
  void cancelChildViewTouch()
  {
    if (!this.mChildrenCanceledTouch)
    {
      long l = SystemClock.uptimeMillis();
      MotionEvent localMotionEvent = MotionEvent.obtain(l, l, 3, 0.0F, 0.0F, 0);
      int j = getChildCount();
      for (int i = 0; i < j; i++) {
        getChildAt(i).dispatchTouchEvent(localMotionEvent);
      }
      localMotionEvent.recycle();
      this.mChildrenCanceledTouch = true;
    }
  }
  
  boolean checkDrawerViewAbsoluteGravity(View paramView, int paramInt)
  {
    boolean bool;
    if ((getDrawerViewAbsoluteGravity(paramView) & paramInt) == paramInt) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
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
  
  public void close()
  {
    closeDrawer(8388611);
  }
  
  public void closeDrawer(int paramInt)
  {
    closeDrawer(paramInt, true);
  }
  
  public void closeDrawer(int paramInt, boolean paramBoolean)
  {
    Object localObject = findDrawerWithGravity(paramInt);
    if (localObject != null)
    {
      closeDrawer((View)localObject, paramBoolean);
      return;
    }
    localObject = new StringBuilder().append("No drawer view found with gravity ");
    String str = gravityToString(paramInt);
    Log5ECF72.a(str);
    LogE84000.a(str);
    Log229316.a(str);
    throw new IllegalArgumentException(str);
  }
  
  public void closeDrawer(View paramView)
  {
    closeDrawer(paramView, true);
  }
  
  public void closeDrawer(View paramView, boolean paramBoolean)
  {
    if (isDrawerView(paramView))
    {
      LayoutParams localLayoutParams = (LayoutParams)paramView.getLayoutParams();
      if (this.mFirstLayout)
      {
        localLayoutParams.onScreen = 0.0F;
        localLayoutParams.openState = 0;
      }
      else if (paramBoolean)
      {
        localLayoutParams.openState = (0x4 | localLayoutParams.openState);
        if (checkDrawerViewAbsoluteGravity(paramView, 3)) {
          this.mLeftDragger.smoothSlideViewTo(paramView, -paramView.getWidth(), paramView.getTop());
        } else {
          this.mRightDragger.smoothSlideViewTo(paramView, getWidth(), paramView.getTop());
        }
      }
      else
      {
        moveDrawerToOffset(paramView, 0.0F);
        updateDrawerState(0, paramView);
        paramView.setVisibility(4);
      }
      invalidate();
      return;
    }
    throw new IllegalArgumentException("View " + paramView + " is not a sliding drawer");
  }
  
  public void closeDrawers()
  {
    closeDrawers(false);
  }
  
  void closeDrawers(boolean paramBoolean)
  {
    int i = 0;
    int m = getChildCount();
    int j = 0;
    while (j < m)
    {
      View localView = getChildAt(j);
      LayoutParams localLayoutParams = (LayoutParams)localView.getLayoutParams();
      boolean bool = i;
      int k;
      if (isDrawerView(localView)) {
        if ((paramBoolean) && (!localLayoutParams.isPeeking))
        {
          bool = i;
        }
        else
        {
          k = localView.getWidth();
          if (checkDrawerViewAbsoluteGravity(localView, 3)) {
            i |= this.mLeftDragger.smoothSlideViewTo(localView, -k, localView.getTop());
          } else {
            i |= this.mRightDragger.smoothSlideViewTo(localView, getWidth(), localView.getTop());
          }
          localLayoutParams.isPeeking = false;
          k = i;
        }
      }
      j++;
      i = k;
    }
    this.mLeftCallback.removeCallbacks();
    this.mRightCallback.removeCallbacks();
    if (i != 0) {
      invalidate();
    }
  }
  
  public void computeScroll()
  {
    int j = getChildCount();
    float f = 0.0F;
    for (int i = 0; i < j; i++) {
      f = Math.max(f, ((LayoutParams)getChildAt(i).getLayoutParams()).onScreen);
    }
    this.mScrimOpacity = f;
    boolean bool2 = this.mLeftDragger.continueSettling(true);
    boolean bool1 = this.mRightDragger.continueSettling(true);
    if ((bool2) || (bool1)) {
      ViewCompat.postInvalidateOnAnimation(this);
    }
  }
  
  public boolean dispatchGenericMotionEvent(MotionEvent paramMotionEvent)
  {
    if (((paramMotionEvent.getSource() & 0x2) != 0) && (paramMotionEvent.getAction() != 10) && (this.mScrimOpacity > 0.0F))
    {
      int i = getChildCount();
      if (i != 0)
      {
        float f2 = paramMotionEvent.getX();
        float f1 = paramMotionEvent.getY();
        i--;
        while (i >= 0)
        {
          View localView = getChildAt(i);
          if ((isInBoundsOfChild(f2, f1, localView)) && (!isContentView(localView)) && (dispatchTransformedGenericPointerEvent(paramMotionEvent, localView))) {
            return true;
          }
          i--;
        }
      }
      return false;
    }
    return super.dispatchGenericMotionEvent(paramMotionEvent);
  }
  
  void dispatchOnDrawerClosed(View paramView)
  {
    Object localObject = (LayoutParams)paramView.getLayoutParams();
    if ((((LayoutParams)localObject).openState & 0x1) == 1)
    {
      ((LayoutParams)localObject).openState = 0;
      localObject = this.mListeners;
      if (localObject != null) {
        for (int i = ((List)localObject).size() - 1; i >= 0; i--) {
          ((DrawerListener)this.mListeners.get(i)).onDrawerClosed(paramView);
        }
      }
      updateChildrenImportantForAccessibility(paramView, false);
      updateChildAccessibilityAction(paramView);
      if (hasWindowFocus())
      {
        paramView = getRootView();
        if (paramView != null) {
          paramView.sendAccessibilityEvent(32);
        }
      }
    }
  }
  
  void dispatchOnDrawerOpened(View paramView)
  {
    Object localObject = (LayoutParams)paramView.getLayoutParams();
    if ((((LayoutParams)localObject).openState & 0x1) == 0)
    {
      ((LayoutParams)localObject).openState = 1;
      localObject = this.mListeners;
      if (localObject != null) {
        for (int i = ((List)localObject).size() - 1; i >= 0; i--) {
          ((DrawerListener)this.mListeners.get(i)).onDrawerOpened(paramView);
        }
      }
      updateChildrenImportantForAccessibility(paramView, true);
      updateChildAccessibilityAction(paramView);
      if (hasWindowFocus()) {
        sendAccessibilityEvent(32);
      }
    }
  }
  
  void dispatchOnDrawerSlide(View paramView, float paramFloat)
  {
    List localList = this.mListeners;
    if (localList != null) {
      for (int i = localList.size() - 1; i >= 0; i--) {
        ((DrawerListener)this.mListeners.get(i)).onDrawerSlide(paramView, paramFloat);
      }
    }
  }
  
  protected boolean drawChild(Canvas paramCanvas, View paramView, long paramLong)
  {
    int i3 = getHeight();
    boolean bool2 = isContentView(paramView);
    int i = 0;
    int j = getWidth();
    int i2 = paramCanvas.save();
    int m;
    int k;
    if (bool2)
    {
      int i4 = getChildCount();
      m = 0;
      while (m < i4)
      {
        View localView = getChildAt(m);
        int n = i;
        k = j;
        if (localView != paramView)
        {
          n = i;
          k = j;
          if (localView.getVisibility() == 0)
          {
            n = i;
            k = j;
            if (hasOpaqueBackground(localView))
            {
              n = i;
              k = j;
              if (isDrawerView(localView)) {
                if (localView.getHeight() < i3)
                {
                  n = i;
                  k = j;
                }
                else if (checkDrawerViewAbsoluteGravity(localView, 3))
                {
                  n = localView.getRight();
                  k = i;
                  if (n > i) {
                    k = n;
                  }
                  n = k;
                  k = j;
                }
                else
                {
                  int i1 = localView.getLeft();
                  n = i;
                  k = j;
                  if (i1 < j)
                  {
                    k = i1;
                    n = i;
                  }
                }
              }
            }
          }
        }
        m++;
        i = n;
        j = k;
      }
      paramCanvas.clipRect(i, 0, j, getHeight());
    }
    else
    {
      i = 0;
    }
    boolean bool1 = super.drawChild(paramCanvas, paramView, paramLong);
    paramCanvas.restoreToCount(i2);
    float f = this.mScrimOpacity;
    if ((f > 0.0F) && (bool2))
    {
      k = this.mScrimColor;
      m = (int)(((0xFF000000 & k) >>> 24) * f);
      this.mScrimPaint.setColor(m << 24 | k & 0xFFFFFF);
      paramCanvas.drawRect(i, 0.0F, j, getHeight(), this.mScrimPaint);
    }
    else if ((this.mShadowLeftResolved != null) && (checkDrawerViewAbsoluteGravity(paramView, 3)))
    {
      j = this.mShadowLeftResolved.getIntrinsicWidth();
      i = paramView.getRight();
      k = this.mLeftDragger.getEdgeSize();
      f = Math.max(0.0F, Math.min(i / k, 1.0F));
      this.mShadowLeftResolved.setBounds(i, paramView.getTop(), i + j, paramView.getBottom());
      this.mShadowLeftResolved.setAlpha((int)(255.0F * f));
      this.mShadowLeftResolved.draw(paramCanvas);
    }
    else if ((this.mShadowRightResolved != null) && (checkDrawerViewAbsoluteGravity(paramView, 5)))
    {
      k = this.mShadowRightResolved.getIntrinsicWidth();
      j = paramView.getLeft();
      i = getWidth();
      m = this.mRightDragger.getEdgeSize();
      f = Math.max(0.0F, Math.min((i - j) / m, 1.0F));
      this.mShadowRightResolved.setBounds(j - k, paramView.getTop(), j, paramView.getBottom());
      this.mShadowRightResolved.setAlpha((int)(255.0F * f));
      this.mShadowRightResolved.draw(paramCanvas);
    }
    return bool1;
  }
  
  View findDrawerWithGravity(int paramInt)
  {
    int j = GravityCompat.getAbsoluteGravity(paramInt, ViewCompat.getLayoutDirection(this));
    int i = getChildCount();
    for (paramInt = 0; paramInt < i; paramInt++)
    {
      View localView = getChildAt(paramInt);
      if ((getDrawerViewAbsoluteGravity(localView) & 0x7) == (j & 0x7)) {
        return localView;
      }
    }
    return null;
  }
  
  View findOpenDrawer()
  {
    int j = getChildCount();
    for (int i = 0; i < j; i++)
    {
      View localView = getChildAt(i);
      if ((((LayoutParams)localView.getLayoutParams()).openState & 0x1) == 1) {
        return localView;
      }
    }
    return null;
  }
  
  View findVisibleDrawer()
  {
    int j = getChildCount();
    for (int i = 0; i < j; i++)
    {
      View localView = getChildAt(i);
      if ((isDrawerView(localView)) && (isDrawerVisible(localView))) {
        return localView;
      }
    }
    return null;
  }
  
  protected ViewGroup.LayoutParams generateDefaultLayoutParams()
  {
    return new LayoutParams(-1, -1);
  }
  
  public ViewGroup.LayoutParams generateLayoutParams(AttributeSet paramAttributeSet)
  {
    return new LayoutParams(getContext(), paramAttributeSet);
  }
  
  protected ViewGroup.LayoutParams generateLayoutParams(ViewGroup.LayoutParams paramLayoutParams)
  {
    if ((paramLayoutParams instanceof LayoutParams)) {
      paramLayoutParams = new LayoutParams((LayoutParams)paramLayoutParams);
    } else if ((paramLayoutParams instanceof ViewGroup.MarginLayoutParams)) {
      paramLayoutParams = new LayoutParams((ViewGroup.MarginLayoutParams)paramLayoutParams);
    } else {
      paramLayoutParams = new LayoutParams(paramLayoutParams);
    }
    return paramLayoutParams;
  }
  
  public float getDrawerElevation()
  {
    if (SET_DRAWER_SHADOW_FROM_ELEVATION) {
      return this.mDrawerElevation;
    }
    return 0.0F;
  }
  
  public int getDrawerLockMode(int paramInt)
  {
    int i = ViewCompat.getLayoutDirection(this);
    switch (paramInt)
    {
    default: 
      break;
    case 8388613: 
      paramInt = this.mLockModeEnd;
      if (paramInt != 3) {
        return paramInt;
      }
      if (i == 0) {
        paramInt = this.mLockModeRight;
      } else {
        paramInt = this.mLockModeLeft;
      }
      if (paramInt != 3) {
        return paramInt;
      }
      break;
    case 8388611: 
      paramInt = this.mLockModeStart;
      if (paramInt != 3) {
        return paramInt;
      }
      if (i == 0) {
        paramInt = this.mLockModeLeft;
      } else {
        paramInt = this.mLockModeRight;
      }
      if (paramInt != 3) {
        return paramInt;
      }
      break;
    case 5: 
      paramInt = this.mLockModeRight;
      if (paramInt != 3) {
        return paramInt;
      }
      if (i == 0) {
        paramInt = this.mLockModeEnd;
      } else {
        paramInt = this.mLockModeStart;
      }
      if (paramInt != 3) {
        return paramInt;
      }
      break;
    case 3: 
      paramInt = this.mLockModeLeft;
      if (paramInt != 3) {
        return paramInt;
      }
      if (i == 0) {
        paramInt = this.mLockModeStart;
      } else {
        paramInt = this.mLockModeEnd;
      }
      if (paramInt != 3) {
        return paramInt;
      }
      break;
    }
    return 0;
  }
  
  public int getDrawerLockMode(View paramView)
  {
    if (isDrawerView(paramView)) {
      return getDrawerLockMode(((LayoutParams)paramView.getLayoutParams()).gravity);
    }
    throw new IllegalArgumentException("View " + paramView + " is not a drawer");
  }
  
  public CharSequence getDrawerTitle(int paramInt)
  {
    paramInt = GravityCompat.getAbsoluteGravity(paramInt, ViewCompat.getLayoutDirection(this));
    if (paramInt == 3) {
      return this.mTitleLeft;
    }
    if (paramInt == 5) {
      return this.mTitleRight;
    }
    return null;
  }
  
  int getDrawerViewAbsoluteGravity(View paramView)
  {
    return GravityCompat.getAbsoluteGravity(((LayoutParams)paramView.getLayoutParams()).gravity, ViewCompat.getLayoutDirection(this));
  }
  
  float getDrawerViewOffset(View paramView)
  {
    return ((LayoutParams)paramView.getLayoutParams()).onScreen;
  }
  
  public Drawable getStatusBarBackgroundDrawable()
  {
    return this.mStatusBarBackground;
  }
  
  boolean isContentView(View paramView)
  {
    boolean bool;
    if (((LayoutParams)paramView.getLayoutParams()).gravity == 0) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public boolean isDrawerOpen(int paramInt)
  {
    View localView = findDrawerWithGravity(paramInt);
    if (localView != null) {
      return isDrawerOpen(localView);
    }
    return false;
  }
  
  public boolean isDrawerOpen(View paramView)
  {
    if (isDrawerView(paramView))
    {
      int i = ((LayoutParams)paramView.getLayoutParams()).openState;
      boolean bool = true;
      if ((i & 0x1) != 1) {
        bool = false;
      }
      return bool;
    }
    throw new IllegalArgumentException("View " + paramView + " is not a drawer");
  }
  
  boolean isDrawerView(View paramView)
  {
    int i = ((LayoutParams)paramView.getLayoutParams()).gravity;
    i = GravityCompat.getAbsoluteGravity(i, ViewCompat.getLayoutDirection(paramView));
    if ((i & 0x3) != 0) {
      return true;
    }
    return (i & 0x5) != 0;
  }
  
  public boolean isDrawerVisible(int paramInt)
  {
    View localView = findDrawerWithGravity(paramInt);
    if (localView != null) {
      return isDrawerVisible(localView);
    }
    return false;
  }
  
  public boolean isDrawerVisible(View paramView)
  {
    if (isDrawerView(paramView))
    {
      boolean bool;
      if (((LayoutParams)paramView.getLayoutParams()).onScreen > 0.0F) {
        bool = true;
      } else {
        bool = false;
      }
      return bool;
    }
    throw new IllegalArgumentException("View " + paramView + " is not a drawer");
  }
  
  public boolean isOpen()
  {
    return isDrawerOpen(8388611);
  }
  
  void moveDrawerToOffset(View paramView, float paramFloat)
  {
    float f = getDrawerViewOffset(paramView);
    int j = paramView.getWidth();
    int i = (int)(j * f);
    i = (int)(j * paramFloat) - i;
    if (!checkDrawerViewAbsoluteGravity(paramView, 3)) {
      i = -i;
    }
    paramView.offsetLeftAndRight(i);
    setDrawerViewOffset(paramView, paramFloat);
  }
  
  protected void onAttachedToWindow()
  {
    super.onAttachedToWindow();
    this.mFirstLayout = true;
  }
  
  protected void onDetachedFromWindow()
  {
    super.onDetachedFromWindow();
    this.mFirstLayout = true;
  }
  
  public void onDraw(Canvas paramCanvas)
  {
    super.onDraw(paramCanvas);
    if ((this.mDrawStatusBarBackground) && (this.mStatusBarBackground != null))
    {
      int i;
      if (Build.VERSION.SDK_INT >= 21)
      {
        Object localObject = this.mLastInsets;
        if (localObject != null) {
          i = ((WindowInsets)localObject).getSystemWindowInsetTop();
        } else {
          i = 0;
        }
      }
      else
      {
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
    boolean bool3 = this.mLeftDragger.shouldInterceptTouchEvent(paramMotionEvent);
    boolean bool4 = this.mRightDragger.shouldInterceptTouchEvent(paramMotionEvent);
    int j = 0;
    int k = 0;
    boolean bool2 = true;
    switch (i)
    {
    default: 
      i = j;
      break;
    case 2: 
      i = j;
      if (this.mLeftDragger.checkTouchSlop(3))
      {
        this.mLeftCallback.removeCallbacks();
        this.mRightCallback.removeCallbacks();
        i = j;
      }
      break;
    case 1: 
    case 3: 
      closeDrawers(true);
      this.mChildrenCanceledTouch = false;
      i = j;
      break;
    case 0: 
      float f1 = paramMotionEvent.getX();
      float f2 = paramMotionEvent.getY();
      this.mInitialMotionX = f1;
      this.mInitialMotionY = f2;
      i = k;
      if (this.mScrimOpacity > 0.0F)
      {
        paramMotionEvent = this.mLeftDragger.findTopChildUnder((int)f1, (int)f2);
        i = k;
        if (paramMotionEvent != null)
        {
          i = k;
          if (isContentView(paramMotionEvent)) {
            i = 1;
          }
        }
      }
      this.mChildrenCanceledTouch = false;
    }
    boolean bool1 = bool2;
    if (!(bool3 | bool4))
    {
      bool1 = bool2;
      if (i == 0)
      {
        bool1 = bool2;
        if (!hasPeekingDrawer()) {
          if (this.mChildrenCanceledTouch) {
            bool1 = bool2;
          } else {
            bool1 = false;
          }
        }
      }
    }
    return bool1;
  }
  
  public boolean onKeyDown(int paramInt, KeyEvent paramKeyEvent)
  {
    if ((paramInt == 4) && (hasVisibleDrawer()))
    {
      paramKeyEvent.startTracking();
      return true;
    }
    return super.onKeyDown(paramInt, paramKeyEvent);
  }
  
  public boolean onKeyUp(int paramInt, KeyEvent paramKeyEvent)
  {
    if (paramInt == 4)
    {
      paramKeyEvent = findVisibleDrawer();
      if ((paramKeyEvent != null) && (getDrawerLockMode(paramKeyEvent) == 0)) {
        closeDrawers();
      }
      boolean bool;
      if (paramKeyEvent != null) {
        bool = true;
      } else {
        bool = false;
      }
      return bool;
    }
    return super.onKeyUp(paramInt, paramKeyEvent);
  }
  
  protected void onLayout(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    this.mInLayout = true;
    int n = paramInt3 - paramInt1;
    int m = getChildCount();
    Object localObject1;
    Object localObject2;
    for (paramInt3 = 0; paramInt3 < m; paramInt3++)
    {
      localObject1 = getChildAt(paramInt3);
      if (((View)localObject1).getVisibility() != 8)
      {
        localObject2 = (LayoutParams)((View)localObject1).getLayoutParams();
        if (isContentView((View)localObject1))
        {
          ((View)localObject1).layout(((LayoutParams)localObject2).leftMargin, ((LayoutParams)localObject2).topMargin, ((LayoutParams)localObject2).leftMargin + ((View)localObject1).getMeasuredWidth(), ((LayoutParams)localObject2).topMargin + ((View)localObject1).getMeasuredHeight());
        }
        else
        {
          int i1 = ((View)localObject1).getMeasuredWidth();
          int i2 = ((View)localObject1).getMeasuredHeight();
          int i;
          float f;
          if (checkDrawerViewAbsoluteGravity((View)localObject1, 3))
          {
            i = -i1 + (int)(i1 * ((LayoutParams)localObject2).onScreen);
            f = (i1 + i) / i1;
          }
          else
          {
            i = n - (int)(i1 * ((LayoutParams)localObject2).onScreen);
            f = (n - i) / i1;
          }
          int j;
          if (f != ((LayoutParams)localObject2).onScreen) {
            j = 1;
          } else {
            j = 0;
          }
          switch (((LayoutParams)localObject2).gravity & 0x70)
          {
          default: 
            ((View)localObject1).layout(i, ((LayoutParams)localObject2).topMargin, i + i1, ((LayoutParams)localObject2).topMargin + i2);
            break;
          case 80: 
            paramInt1 = paramInt4 - paramInt2;
            ((View)localObject1).layout(i, paramInt1 - ((LayoutParams)localObject2).bottomMargin - ((View)localObject1).getMeasuredHeight(), i + i1, paramInt1 - ((LayoutParams)localObject2).bottomMargin);
            break;
          case 16: 
            int i3 = paramInt4 - paramInt2;
            int k = (i3 - i2) / 2;
            if (k < ((LayoutParams)localObject2).topMargin)
            {
              paramInt1 = ((LayoutParams)localObject2).topMargin;
            }
            else
            {
              paramInt1 = k;
              if (k + i2 > i3 - ((LayoutParams)localObject2).bottomMargin) {
                paramInt1 = i3 - ((LayoutParams)localObject2).bottomMargin - i2;
              }
            }
            ((View)localObject1).layout(i, paramInt1, i + i1, paramInt1 + i2);
          }
          if (j != 0) {
            setDrawerViewOffset((View)localObject1, f);
          }
          if (((LayoutParams)localObject2).onScreen > 0.0F) {
            paramInt1 = 0;
          } else {
            paramInt1 = 4;
          }
          if (((View)localObject1).getVisibility() != paramInt1) {
            ((View)localObject1).setVisibility(paramInt1);
          }
        }
      }
    }
    if (sEdgeSizeUsingSystemGestureInsets)
    {
      localObject1 = getRootWindowInsets();
      if (localObject1 != null)
      {
        localObject1 = WindowInsetsCompat.toWindowInsetsCompat((WindowInsets)localObject1).getSystemGestureInsets();
        localObject2 = this.mLeftDragger;
        ((ViewDragHelper)localObject2).setEdgeSize(Math.max(((ViewDragHelper)localObject2).getDefaultEdgeSize(), ((Insets)localObject1).left));
        localObject2 = this.mRightDragger;
        ((ViewDragHelper)localObject2).setEdgeSize(Math.max(((ViewDragHelper)localObject2).getDefaultEdgeSize(), ((Insets)localObject1).right));
      }
    }
    this.mInLayout = false;
    this.mFirstLayout = false;
  }
  
  protected void onMeasure(int paramInt1, int paramInt2)
  {
    int i3 = View.MeasureSpec.getMode(paramInt1);
    int i2 = View.MeasureSpec.getMode(paramInt2);
    int i = View.MeasureSpec.getSize(paramInt1);
    int j = View.MeasureSpec.getSize(paramInt2);
    int m;
    int n;
    if (i3 == 1073741824)
    {
      m = i;
      n = j;
      if (i2 == 1073741824) {}
    }
    else
    {
      if (!isInEditMode()) {
        break label761;
      }
      if (i3 == 0) {
        i = 300;
      }
      m = i;
      n = j;
      if (i2 == 0)
      {
        n = 300;
        m = i;
      }
    }
    setMeasuredDimension(m, n);
    int k;
    if ((this.mLastInsets != null) && (ViewCompat.getFitsSystemWindows(this))) {
      k = 1;
    } else {
      k = 0;
    }
    int i6 = ViewCompat.getLayoutDirection(this);
    j = 0;
    i = 0;
    int i5 = getChildCount();
    int i1 = 0;
    while (i1 < i5)
    {
      View localView = getChildAt(i1);
      LayoutParams localLayoutParams;
      int i4;
      Object localObject2;
      Object localObject1;
      if (localView.getVisibility() != 8)
      {
        localLayoutParams = (LayoutParams)localView.getLayoutParams();
        if (k != 0)
        {
          i4 = GravityCompat.getAbsoluteGravity(localLayoutParams.gravity, i6);
          if (ViewCompat.getFitsSystemWindows(localView))
          {
            if (Build.VERSION.SDK_INT >= 21)
            {
              localObject2 = (WindowInsets)this.mLastInsets;
              if (i4 == 3)
              {
                localObject1 = ((WindowInsets)localObject2).replaceSystemWindowInsets(((WindowInsets)localObject2).getSystemWindowInsetLeft(), ((WindowInsets)localObject2).getSystemWindowInsetTop(), 0, ((WindowInsets)localObject2).getSystemWindowInsetBottom());
              }
              else
              {
                localObject1 = localObject2;
                if (i4 == 5) {
                  localObject1 = ((WindowInsets)localObject2).replaceSystemWindowInsets(0, ((WindowInsets)localObject2).getSystemWindowInsetTop(), ((WindowInsets)localObject2).getSystemWindowInsetRight(), ((WindowInsets)localObject2).getSystemWindowInsetBottom());
                }
              }
              localView.dispatchApplyWindowInsets((WindowInsets)localObject1);
            }
          }
          else if (Build.VERSION.SDK_INT >= 21)
          {
            localObject2 = (WindowInsets)this.mLastInsets;
            if (i4 == 3)
            {
              localObject1 = ((WindowInsets)localObject2).replaceSystemWindowInsets(((WindowInsets)localObject2).getSystemWindowInsetLeft(), ((WindowInsets)localObject2).getSystemWindowInsetTop(), 0, ((WindowInsets)localObject2).getSystemWindowInsetBottom());
            }
            else
            {
              localObject1 = localObject2;
              if (i4 == 5) {
                localObject1 = ((WindowInsets)localObject2).replaceSystemWindowInsets(0, ((WindowInsets)localObject2).getSystemWindowInsetTop(), ((WindowInsets)localObject2).getSystemWindowInsetRight(), ((WindowInsets)localObject2).getSystemWindowInsetBottom());
              }
            }
            localLayoutParams.leftMargin = ((WindowInsets)localObject1).getSystemWindowInsetLeft();
            localLayoutParams.topMargin = ((WindowInsets)localObject1).getSystemWindowInsetTop();
            localLayoutParams.rightMargin = ((WindowInsets)localObject1).getSystemWindowInsetRight();
            localLayoutParams.bottomMargin = ((WindowInsets)localObject1).getSystemWindowInsetBottom();
          }
          else {}
        }
        if (isContentView(localView)) {
          localView.measure(View.MeasureSpec.makeMeasureSpec(m - localLayoutParams.leftMargin - localLayoutParams.rightMargin, 1073741824), View.MeasureSpec.makeMeasureSpec(n - localLayoutParams.topMargin - localLayoutParams.bottomMargin, 1073741824));
        }
      }
      else
      {
        break label708;
      }
      if (isDrawerView(localView))
      {
        if (SET_DRAWER_SHADOW_FROM_ELEVATION)
        {
          float f2 = ViewCompat.getElevation(localView);
          float f1 = this.mDrawerElevation;
          if (f2 != f1) {
            ViewCompat.setElevation(localView, f1);
          }
        }
        int i7 = getDrawerViewAbsoluteGravity(localView) & 0x7;
        if (i7 == 3) {
          i4 = 1;
        } else {
          i4 = 0;
        }
        if (((i4 != 0) && (j != 0)) || ((i4 == 0) && (i != 0)))
        {
          localObject1 = new StringBuilder().append("Child drawer has absolute gravity ");
          localObject2 = gravityToString(i7);
          Log5ECF72.a(localObject2);
          LogE84000.a(localObject2);
          Log229316.a(localObject2);
          throw new IllegalStateException((String)localObject2 + " but this " + "DrawerLayout" + " already has a drawer view along that edge");
        }
        if (i4 != 0) {
          j = 1;
        } else {
          i = 1;
        }
        localView.measure(getChildMeasureSpec(paramInt1, this.mMinDrawerMargin + localLayoutParams.leftMargin + localLayoutParams.rightMargin, localLayoutParams.width), getChildMeasureSpec(paramInt2, localLayoutParams.topMargin + localLayoutParams.bottomMargin, localLayoutParams.height));
        label708:
        i1++;
      }
      else
      {
        throw new IllegalStateException("Child " + localView + " at index " + i1 + " does not have a valid layout_gravity - must be Gravity.LEFT, Gravity.RIGHT or Gravity.NO_GRAVITY");
      }
    }
    return;
    label761:
    throw new IllegalArgumentException("DrawerLayout must be measured with MeasureSpec.EXACTLY.");
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
    if (paramParcelable.openDrawerGravity != 0)
    {
      View localView = findDrawerWithGravity(paramParcelable.openDrawerGravity);
      if (localView != null) {
        openDrawer(localView);
      }
    }
    if (paramParcelable.lockModeLeft != 3) {
      setDrawerLockMode(paramParcelable.lockModeLeft, 3);
    }
    if (paramParcelable.lockModeRight != 3) {
      setDrawerLockMode(paramParcelable.lockModeRight, 5);
    }
    if (paramParcelable.lockModeStart != 3) {
      setDrawerLockMode(paramParcelable.lockModeStart, 8388611);
    }
    if (paramParcelable.lockModeEnd != 3) {
      setDrawerLockMode(paramParcelable.lockModeEnd, 8388613);
    }
  }
  
  public void onRtlPropertiesChanged(int paramInt)
  {
    resolveShadowDrawables();
  }
  
  protected Parcelable onSaveInstanceState()
  {
    SavedState localSavedState = new SavedState(super.onSaveInstanceState());
    int m = getChildCount();
    int i = 0;
    while (i < m)
    {
      LayoutParams localLayoutParams = (LayoutParams)getChildAt(i).getLayoutParams();
      int j = localLayoutParams.openState;
      int k = 0;
      if (j == 1) {
        j = 1;
      } else {
        j = 0;
      }
      if (localLayoutParams.openState == 2) {
        k = 1;
      }
      if ((j == 0) && (k == 0)) {
        i++;
      } else {
        localSavedState.openDrawerGravity = localLayoutParams.gravity;
      }
    }
    localSavedState.lockModeLeft = this.mLockModeLeft;
    localSavedState.lockModeRight = this.mLockModeRight;
    localSavedState.lockModeStart = this.mLockModeStart;
    localSavedState.lockModeEnd = this.mLockModeEnd;
    return localSavedState;
  }
  
  public boolean onTouchEvent(MotionEvent paramMotionEvent)
  {
    this.mLeftDragger.processTouchEvent(paramMotionEvent);
    this.mRightDragger.processTouchEvent(paramMotionEvent);
    int i = paramMotionEvent.getAction();
    boolean bool2 = false;
    float f1;
    float f2;
    switch (i & 0xFF)
    {
    case 2: 
    default: 
      break;
    case 3: 
      closeDrawers(true);
      this.mChildrenCanceledTouch = false;
      break;
    case 1: 
      f1 = paramMotionEvent.getX();
      f2 = paramMotionEvent.getY();
      boolean bool3 = true;
      paramMotionEvent = this.mLeftDragger.findTopChildUnder((int)f1, (int)f2);
      boolean bool1 = bool3;
      if (paramMotionEvent != null)
      {
        bool1 = bool3;
        if (isContentView(paramMotionEvent))
        {
          f1 -= this.mInitialMotionX;
          f2 -= this.mInitialMotionY;
          i = this.mLeftDragger.getTouchSlop();
          bool1 = bool3;
          if (f1 * f1 + f2 * f2 < i * i)
          {
            paramMotionEvent = findOpenDrawer();
            bool1 = bool3;
            if (paramMotionEvent != null)
            {
              bool1 = bool2;
              if (getDrawerLockMode(paramMotionEvent) == 2) {
                bool1 = true;
              }
            }
          }
        }
      }
      closeDrawers(bool1);
      break;
    case 0: 
      f2 = paramMotionEvent.getX();
      f1 = paramMotionEvent.getY();
      this.mInitialMotionX = f2;
      this.mInitialMotionY = f1;
      this.mChildrenCanceledTouch = false;
    }
    return true;
  }
  
  public void open()
  {
    openDrawer(8388611);
  }
  
  public void openDrawer(int paramInt)
  {
    openDrawer(paramInt, true);
  }
  
  public void openDrawer(int paramInt, boolean paramBoolean)
  {
    Object localObject = findDrawerWithGravity(paramInt);
    if (localObject != null)
    {
      openDrawer((View)localObject, paramBoolean);
      return;
    }
    StringBuilder localStringBuilder = new StringBuilder().append("No drawer view found with gravity ");
    localObject = gravityToString(paramInt);
    Log5ECF72.a(localObject);
    LogE84000.a(localObject);
    Log229316.a(localObject);
    throw new IllegalArgumentException((String)localObject);
  }
  
  public void openDrawer(View paramView)
  {
    openDrawer(paramView, true);
  }
  
  public void openDrawer(View paramView, boolean paramBoolean)
  {
    if (isDrawerView(paramView))
    {
      LayoutParams localLayoutParams = (LayoutParams)paramView.getLayoutParams();
      if (this.mFirstLayout)
      {
        localLayoutParams.onScreen = 1.0F;
        localLayoutParams.openState = 1;
        updateChildrenImportantForAccessibility(paramView, true);
        updateChildAccessibilityAction(paramView);
      }
      else if (paramBoolean)
      {
        localLayoutParams.openState |= 0x2;
        if (checkDrawerViewAbsoluteGravity(paramView, 3)) {
          this.mLeftDragger.smoothSlideViewTo(paramView, 0, paramView.getTop());
        } else {
          this.mRightDragger.smoothSlideViewTo(paramView, getWidth() - paramView.getWidth(), paramView.getTop());
        }
      }
      else
      {
        moveDrawerToOffset(paramView, 1.0F);
        updateDrawerState(0, paramView);
        paramView.setVisibility(0);
      }
      invalidate();
      return;
    }
    throw new IllegalArgumentException("View " + paramView + " is not a sliding drawer");
  }
  
  public void removeDrawerListener(DrawerListener paramDrawerListener)
  {
    if (paramDrawerListener == null) {
      return;
    }
    List localList = this.mListeners;
    if (localList == null) {
      return;
    }
    localList.remove(paramDrawerListener);
  }
  
  public void requestDisallowInterceptTouchEvent(boolean paramBoolean)
  {
    super.requestDisallowInterceptTouchEvent(paramBoolean);
    if (paramBoolean) {
      closeDrawers(true);
    }
  }
  
  public void requestLayout()
  {
    if (!this.mInLayout) {
      super.requestLayout();
    }
  }
  
  public void setChildInsets(Object paramObject, boolean paramBoolean)
  {
    this.mLastInsets = paramObject;
    this.mDrawStatusBarBackground = paramBoolean;
    if ((!paramBoolean) && (getBackground() == null)) {
      paramBoolean = true;
    } else {
      paramBoolean = false;
    }
    setWillNotDraw(paramBoolean);
    requestLayout();
  }
  
  public void setDrawerElevation(float paramFloat)
  {
    this.mDrawerElevation = paramFloat;
    for (int i = 0; i < getChildCount(); i++)
    {
      View localView = getChildAt(i);
      if (isDrawerView(localView)) {
        ViewCompat.setElevation(localView, this.mDrawerElevation);
      }
    }
  }
  
  @Deprecated
  public void setDrawerListener(DrawerListener paramDrawerListener)
  {
    DrawerListener localDrawerListener = this.mListener;
    if (localDrawerListener != null) {
      removeDrawerListener(localDrawerListener);
    }
    if (paramDrawerListener != null) {
      addDrawerListener(paramDrawerListener);
    }
    this.mListener = paramDrawerListener;
  }
  
  public void setDrawerLockMode(int paramInt)
  {
    setDrawerLockMode(paramInt, 3);
    setDrawerLockMode(paramInt, 5);
  }
  
  public void setDrawerLockMode(int paramInt1, int paramInt2)
  {
    int i = GravityCompat.getAbsoluteGravity(paramInt2, ViewCompat.getLayoutDirection(this));
    switch (paramInt2)
    {
    default: 
      break;
    case 8388613: 
      this.mLockModeEnd = paramInt1;
      break;
    case 8388611: 
      this.mLockModeStart = paramInt1;
      break;
    case 5: 
      this.mLockModeRight = paramInt1;
      break;
    case 3: 
      this.mLockModeLeft = paramInt1;
    }
    Object localObject;
    if (paramInt1 != 0)
    {
      if (i == 3) {
        localObject = this.mLeftDragger;
      } else {
        localObject = this.mRightDragger;
      }
      ((ViewDragHelper)localObject).cancel();
    }
    switch (paramInt1)
    {
    default: 
      break;
    case 2: 
      localObject = findDrawerWithGravity(i);
      if (localObject != null) {
        openDrawer((View)localObject);
      }
      break;
    case 1: 
      localObject = findDrawerWithGravity(i);
      if (localObject != null) {
        closeDrawer((View)localObject);
      }
      break;
    }
  }
  
  public void setDrawerLockMode(int paramInt, View paramView)
  {
    if (isDrawerView(paramView))
    {
      setDrawerLockMode(paramInt, ((LayoutParams)paramView.getLayoutParams()).gravity);
      return;
    }
    throw new IllegalArgumentException("View " + paramView + " is not a drawer with appropriate layout_gravity");
  }
  
  public void setDrawerShadow(int paramInt1, int paramInt2)
  {
    setDrawerShadow(ContextCompat.getDrawable(getContext(), paramInt1), paramInt2);
  }
  
  public void setDrawerShadow(Drawable paramDrawable, int paramInt)
  {
    if (SET_DRAWER_SHADOW_FROM_ELEVATION) {
      return;
    }
    if ((paramInt & 0x800003) == 8388611)
    {
      this.mShadowStart = paramDrawable;
    }
    else if ((paramInt & 0x800005) == 8388613)
    {
      this.mShadowEnd = paramDrawable;
    }
    else if ((paramInt & 0x3) == 3)
    {
      this.mShadowLeft = paramDrawable;
    }
    else
    {
      if ((paramInt & 0x5) != 5) {
        return;
      }
      this.mShadowRight = paramDrawable;
    }
    resolveShadowDrawables();
    invalidate();
    return;
  }
  
  public void setDrawerTitle(int paramInt, CharSequence paramCharSequence)
  {
    paramInt = GravityCompat.getAbsoluteGravity(paramInt, ViewCompat.getLayoutDirection(this));
    if (paramInt == 3) {
      this.mTitleLeft = paramCharSequence;
    } else if (paramInt == 5) {
      this.mTitleRight = paramCharSequence;
    }
  }
  
  void setDrawerViewOffset(View paramView, float paramFloat)
  {
    LayoutParams localLayoutParams = (LayoutParams)paramView.getLayoutParams();
    if (paramFloat == localLayoutParams.onScreen) {
      return;
    }
    localLayoutParams.onScreen = paramFloat;
    dispatchOnDrawerSlide(paramView, paramFloat);
  }
  
  public void setScrimColor(int paramInt)
  {
    this.mScrimColor = paramInt;
    invalidate();
  }
  
  public void setStatusBarBackground(int paramInt)
  {
    Drawable localDrawable;
    if (paramInt != 0) {
      localDrawable = ContextCompat.getDrawable(getContext(), paramInt);
    } else {
      localDrawable = null;
    }
    this.mStatusBarBackground = localDrawable;
    invalidate();
  }
  
  public void setStatusBarBackground(Drawable paramDrawable)
  {
    this.mStatusBarBackground = paramDrawable;
    invalidate();
  }
  
  public void setStatusBarBackgroundColor(int paramInt)
  {
    this.mStatusBarBackground = new ColorDrawable(paramInt);
    invalidate();
  }
  
  void updateDrawerState(int paramInt, View paramView)
  {
    int j = this.mLeftDragger.getViewDragState();
    int i = this.mRightDragger.getViewDragState();
    if ((j != 1) && (i != 1))
    {
      if ((j != 2) && (i != 2)) {
        i = 0;
      } else {
        i = 2;
      }
    }
    else {
      i = 1;
    }
    if ((paramView != null) && (paramInt == 0))
    {
      LayoutParams localLayoutParams = (LayoutParams)paramView.getLayoutParams();
      if (localLayoutParams.onScreen == 0.0F) {
        dispatchOnDrawerClosed(paramView);
      } else if (localLayoutParams.onScreen == 1.0F) {
        dispatchOnDrawerOpened(paramView);
      }
    }
    if (i != this.mDrawerState)
    {
      this.mDrawerState = i;
      paramView = this.mListeners;
      if (paramView != null) {
        for (paramInt = paramView.size() - 1; paramInt >= 0; paramInt--) {
          ((DrawerListener)this.mListeners.get(paramInt)).onDrawerStateChanged(i);
        }
      }
    }
  }
  
  class AccessibilityDelegate
    extends AccessibilityDelegateCompat
  {
    private final Rect mTmpRect = new Rect();
    
    AccessibilityDelegate() {}
    
    private void addChildrenForAccessibility(AccessibilityNodeInfoCompat paramAccessibilityNodeInfoCompat, ViewGroup paramViewGroup)
    {
      int j = paramViewGroup.getChildCount();
      for (int i = 0; i < j; i++)
      {
        View localView = paramViewGroup.getChildAt(i);
        if (DrawerLayout.includeChildForAccessibility(localView)) {
          paramAccessibilityNodeInfoCompat.addChild(localView);
        }
      }
    }
    
    private void copyNodeInfoNoChildren(AccessibilityNodeInfoCompat paramAccessibilityNodeInfoCompat1, AccessibilityNodeInfoCompat paramAccessibilityNodeInfoCompat2)
    {
      Rect localRect = this.mTmpRect;
      paramAccessibilityNodeInfoCompat2.getBoundsInScreen(localRect);
      paramAccessibilityNodeInfoCompat1.setBoundsInScreen(localRect);
      paramAccessibilityNodeInfoCompat1.setVisibleToUser(paramAccessibilityNodeInfoCompat2.isVisibleToUser());
      paramAccessibilityNodeInfoCompat1.setPackageName(paramAccessibilityNodeInfoCompat2.getPackageName());
      paramAccessibilityNodeInfoCompat1.setClassName(paramAccessibilityNodeInfoCompat2.getClassName());
      paramAccessibilityNodeInfoCompat1.setContentDescription(paramAccessibilityNodeInfoCompat2.getContentDescription());
      paramAccessibilityNodeInfoCompat1.setEnabled(paramAccessibilityNodeInfoCompat2.isEnabled());
      paramAccessibilityNodeInfoCompat1.setFocused(paramAccessibilityNodeInfoCompat2.isFocused());
      paramAccessibilityNodeInfoCompat1.setAccessibilityFocused(paramAccessibilityNodeInfoCompat2.isAccessibilityFocused());
      paramAccessibilityNodeInfoCompat1.setSelected(paramAccessibilityNodeInfoCompat2.isSelected());
      paramAccessibilityNodeInfoCompat1.addAction(paramAccessibilityNodeInfoCompat2.getActions());
    }
    
    public boolean dispatchPopulateAccessibilityEvent(View paramView, AccessibilityEvent paramAccessibilityEvent)
    {
      if (paramAccessibilityEvent.getEventType() == 32)
      {
        paramView = paramAccessibilityEvent.getText();
        paramAccessibilityEvent = DrawerLayout.this.findVisibleDrawer();
        if (paramAccessibilityEvent != null)
        {
          int i = DrawerLayout.this.getDrawerViewAbsoluteGravity(paramAccessibilityEvent);
          paramAccessibilityEvent = DrawerLayout.this.getDrawerTitle(i);
          if (paramAccessibilityEvent != null) {
            paramView.add(paramAccessibilityEvent);
          }
        }
        return true;
      }
      return super.dispatchPopulateAccessibilityEvent(paramView, paramAccessibilityEvent);
    }
    
    public void onInitializeAccessibilityEvent(View paramView, AccessibilityEvent paramAccessibilityEvent)
    {
      super.onInitializeAccessibilityEvent(paramView, paramAccessibilityEvent);
      paramAccessibilityEvent.setClassName("androidx.drawerlayout.widget.DrawerLayout");
    }
    
    public void onInitializeAccessibilityNodeInfo(View paramView, AccessibilityNodeInfoCompat paramAccessibilityNodeInfoCompat)
    {
      if (DrawerLayout.CAN_HIDE_DESCENDANTS)
      {
        super.onInitializeAccessibilityNodeInfo(paramView, paramAccessibilityNodeInfoCompat);
      }
      else
      {
        AccessibilityNodeInfoCompat localAccessibilityNodeInfoCompat = AccessibilityNodeInfoCompat.obtain(paramAccessibilityNodeInfoCompat);
        super.onInitializeAccessibilityNodeInfo(paramView, localAccessibilityNodeInfoCompat);
        paramAccessibilityNodeInfoCompat.setSource(paramView);
        ViewParent localViewParent = ViewCompat.getParentForAccessibility(paramView);
        if ((localViewParent instanceof View)) {
          paramAccessibilityNodeInfoCompat.setParent((View)localViewParent);
        }
        copyNodeInfoNoChildren(paramAccessibilityNodeInfoCompat, localAccessibilityNodeInfoCompat);
        localAccessibilityNodeInfoCompat.recycle();
        addChildrenForAccessibility(paramAccessibilityNodeInfoCompat, (ViewGroup)paramView);
      }
      paramAccessibilityNodeInfoCompat.setClassName("androidx.drawerlayout.widget.DrawerLayout");
      paramAccessibilityNodeInfoCompat.setFocusable(false);
      paramAccessibilityNodeInfoCompat.setFocused(false);
      paramAccessibilityNodeInfoCompat.removeAction(AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_FOCUS);
      paramAccessibilityNodeInfoCompat.removeAction(AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_CLEAR_FOCUS);
    }
    
    public boolean onRequestSendAccessibilityEvent(ViewGroup paramViewGroup, View paramView, AccessibilityEvent paramAccessibilityEvent)
    {
      if ((!DrawerLayout.CAN_HIDE_DESCENDANTS) && (!DrawerLayout.includeChildForAccessibility(paramView))) {
        return false;
      }
      return super.onRequestSendAccessibilityEvent(paramViewGroup, paramView, paramAccessibilityEvent);
    }
  }
  
  static final class ChildAccessibilityDelegate
    extends AccessibilityDelegateCompat
  {
    public void onInitializeAccessibilityNodeInfo(View paramView, AccessibilityNodeInfoCompat paramAccessibilityNodeInfoCompat)
    {
      super.onInitializeAccessibilityNodeInfo(paramView, paramAccessibilityNodeInfoCompat);
      if (!DrawerLayout.includeChildForAccessibility(paramView)) {
        paramAccessibilityNodeInfoCompat.setParent(null);
      }
    }
  }
  
  public static abstract interface DrawerListener
  {
    public abstract void onDrawerClosed(View paramView);
    
    public abstract void onDrawerOpened(View paramView);
    
    public abstract void onDrawerSlide(View paramView, float paramFloat);
    
    public abstract void onDrawerStateChanged(int paramInt);
  }
  
  public static class LayoutParams
    extends ViewGroup.MarginLayoutParams
  {
    private static final int FLAG_IS_CLOSING = 4;
    private static final int FLAG_IS_OPENED = 1;
    private static final int FLAG_IS_OPENING = 2;
    public int gravity = 0;
    boolean isPeeking;
    float onScreen;
    int openState;
    
    public LayoutParams(int paramInt1, int paramInt2)
    {
      super(paramInt2);
    }
    
    public LayoutParams(int paramInt1, int paramInt2, int paramInt3)
    {
      this(paramInt1, paramInt2);
    }
    
    public LayoutParams(Context paramContext, AttributeSet paramAttributeSet)
    {
      super(paramAttributeSet);
      paramContext = paramContext.obtainStyledAttributes(paramAttributeSet, DrawerLayout.LAYOUT_ATTRS);
      this.gravity = paramContext.getInt(0, 0);
      paramContext.recycle();
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
      this.gravity = paramLayoutParams.gravity;
    }
  }
  
  protected static class SavedState
    extends AbsSavedState
  {
    public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.ClassLoaderCreator()
    {
      public DrawerLayout.SavedState createFromParcel(Parcel paramAnonymousParcel)
      {
        return new DrawerLayout.SavedState(paramAnonymousParcel, null);
      }
      
      public DrawerLayout.SavedState createFromParcel(Parcel paramAnonymousParcel, ClassLoader paramAnonymousClassLoader)
      {
        return new DrawerLayout.SavedState(paramAnonymousParcel, paramAnonymousClassLoader);
      }
      
      public DrawerLayout.SavedState[] newArray(int paramAnonymousInt)
      {
        return new DrawerLayout.SavedState[paramAnonymousInt];
      }
    };
    int lockModeEnd;
    int lockModeLeft;
    int lockModeRight;
    int lockModeStart;
    int openDrawerGravity = 0;
    
    public SavedState(Parcel paramParcel, ClassLoader paramClassLoader)
    {
      super(paramClassLoader);
      this.openDrawerGravity = paramParcel.readInt();
      this.lockModeLeft = paramParcel.readInt();
      this.lockModeRight = paramParcel.readInt();
      this.lockModeStart = paramParcel.readInt();
      this.lockModeEnd = paramParcel.readInt();
    }
    
    public SavedState(Parcelable paramParcelable)
    {
      super();
    }
    
    public void writeToParcel(Parcel paramParcel, int paramInt)
    {
      super.writeToParcel(paramParcel, paramInt);
      paramParcel.writeInt(this.openDrawerGravity);
      paramParcel.writeInt(this.lockModeLeft);
      paramParcel.writeInt(this.lockModeRight);
      paramParcel.writeInt(this.lockModeStart);
      paramParcel.writeInt(this.lockModeEnd);
    }
  }
  
  public static abstract class SimpleDrawerListener
    implements DrawerLayout.DrawerListener
  {
    public void onDrawerClosed(View paramView) {}
    
    public void onDrawerOpened(View paramView) {}
    
    public void onDrawerSlide(View paramView, float paramFloat) {}
    
    public void onDrawerStateChanged(int paramInt) {}
  }
  
  private class ViewDragCallback
    extends ViewDragHelper.Callback
  {
    private final int mAbsGravity;
    private ViewDragHelper mDragger;
    private final Runnable mPeekRunnable = new Runnable()
    {
      public void run()
      {
        DrawerLayout.ViewDragCallback.this.peekDrawer();
      }
    };
    
    ViewDragCallback(int paramInt)
    {
      this.mAbsGravity = paramInt;
    }
    
    private void closeOtherDrawer()
    {
      int j = this.mAbsGravity;
      int i = 3;
      if (j == 3) {
        i = 5;
      }
      View localView = DrawerLayout.this.findDrawerWithGravity(i);
      if (localView != null) {
        DrawerLayout.this.closeDrawer(localView);
      }
    }
    
    public int clampViewPositionHorizontal(View paramView, int paramInt1, int paramInt2)
    {
      if (DrawerLayout.this.checkDrawerViewAbsoluteGravity(paramView, 3)) {
        return Math.max(-paramView.getWidth(), Math.min(paramInt1, 0));
      }
      paramInt2 = DrawerLayout.this.getWidth();
      return Math.max(paramInt2 - paramView.getWidth(), Math.min(paramInt1, paramInt2));
    }
    
    public int clampViewPositionVertical(View paramView, int paramInt1, int paramInt2)
    {
      return paramView.getTop();
    }
    
    public int getViewHorizontalDragRange(View paramView)
    {
      int i;
      if (DrawerLayout.this.isDrawerView(paramView)) {
        i = paramView.getWidth();
      } else {
        i = 0;
      }
      return i;
    }
    
    public void onEdgeDragStarted(int paramInt1, int paramInt2)
    {
      View localView;
      if ((paramInt1 & 0x1) == 1) {
        localView = DrawerLayout.this.findDrawerWithGravity(3);
      } else {
        localView = DrawerLayout.this.findDrawerWithGravity(5);
      }
      if ((localView != null) && (DrawerLayout.this.getDrawerLockMode(localView) == 0)) {
        this.mDragger.captureChildView(localView, paramInt2);
      }
    }
    
    public boolean onEdgeLock(int paramInt)
    {
      return false;
    }
    
    public void onEdgeTouched(int paramInt1, int paramInt2)
    {
      DrawerLayout.this.postDelayed(this.mPeekRunnable, 160L);
    }
    
    public void onViewCaptured(View paramView, int paramInt)
    {
      ((DrawerLayout.LayoutParams)paramView.getLayoutParams()).isPeeking = false;
      closeOtherDrawer();
    }
    
    public void onViewDragStateChanged(int paramInt)
    {
      DrawerLayout.this.updateDrawerState(paramInt, this.mDragger.getCapturedView());
    }
    
    public void onViewPositionChanged(View paramView, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
    {
      paramInt2 = paramView.getWidth();
      float f;
      if (DrawerLayout.this.checkDrawerViewAbsoluteGravity(paramView, 3)) {
        f = (paramInt2 + paramInt1) / paramInt2;
      } else {
        f = (DrawerLayout.this.getWidth() - paramInt1) / paramInt2;
      }
      DrawerLayout.this.setDrawerViewOffset(paramView, f);
      if (f == 0.0F) {
        paramInt1 = 4;
      } else {
        paramInt1 = 0;
      }
      paramView.setVisibility(paramInt1);
      DrawerLayout.this.invalidate();
    }
    
    public void onViewReleased(View paramView, float paramFloat1, float paramFloat2)
    {
      paramFloat2 = DrawerLayout.this.getDrawerViewOffset(paramView);
      int j = paramView.getWidth();
      int i;
      if (DrawerLayout.this.checkDrawerViewAbsoluteGravity(paramView, 3))
      {
        if ((paramFloat1 <= 0.0F) && ((paramFloat1 != 0.0F) || (paramFloat2 <= 0.5F))) {
          i = -j;
        } else {
          i = 0;
        }
      }
      else
      {
        i = DrawerLayout.this.getWidth();
        if ((paramFloat1 >= 0.0F) && ((paramFloat1 != 0.0F) || (paramFloat2 <= 0.5F))) {
          break label104;
        }
        i -= j;
      }
      label104:
      this.mDragger.settleCapturedViewAt(i, paramView.getTop());
      DrawerLayout.this.invalidate();
    }
    
    void peekDrawer()
    {
      int k = this.mDragger.getEdgeSize();
      int i = this.mAbsGravity;
      int j = 0;
      if (i == 3) {
        i = 1;
      } else {
        i = 0;
      }
      View localView;
      if (i != 0)
      {
        localView = DrawerLayout.this.findDrawerWithGravity(3);
        if (localView != null) {
          j = -localView.getWidth();
        }
        j += k;
      }
      else
      {
        localView = DrawerLayout.this.findDrawerWithGravity(5);
        j = DrawerLayout.this.getWidth() - k;
      }
      if ((localView != null) && (((i != 0) && (localView.getLeft() < j)) || ((i == 0) && (localView.getLeft() > j) && (DrawerLayout.this.getDrawerLockMode(localView) == 0))))
      {
        DrawerLayout.LayoutParams localLayoutParams = (DrawerLayout.LayoutParams)localView.getLayoutParams();
        this.mDragger.smoothSlideViewTo(localView, j, localView.getTop());
        localLayoutParams.isPeeking = true;
        DrawerLayout.this.invalidate();
        closeOtherDrawer();
        DrawerLayout.this.cancelChildViewTouch();
      }
    }
    
    public void removeCallbacks()
    {
      DrawerLayout.this.removeCallbacks(this.mPeekRunnable);
    }
    
    public void setDragger(ViewDragHelper paramViewDragHelper)
    {
      this.mDragger = paramViewDragHelper;
    }
    
    public boolean tryCaptureView(View paramView, int paramInt)
    {
      boolean bool;
      if ((DrawerLayout.this.isDrawerView(paramView)) && (DrawerLayout.this.checkDrawerViewAbsoluteGravity(paramView, this.mAbsGravity)) && (DrawerLayout.this.getDrawerLockMode(paramView) == 0)) {
        bool = true;
      } else {
        bool = false;
      }
      return bool;
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/drawerlayout/widget/DrawerLayout.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */