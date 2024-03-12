package androidx.core.view;

import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.ClipData;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.view.ContentInfo;
import android.view.Display;
import android.view.KeyEvent;
import android.view.PointerIcon;
import android.view.View;
import android.view.View.AccessibilityDelegate;
import android.view.View.DragShadowBuilder;
import android.view.View.OnApplyWindowInsetsListener;
import android.view.View.OnAttachStateChangeListener;
import android.view.View.OnUnhandledKeyEventListener;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.Window;
import android.view.WindowInsets;
import android.view.WindowManager;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityManager;
import android.view.accessibility.AccessibilityNodeProvider;
import androidx.collection.SimpleArrayMap;
import androidx.core.R.id;
import androidx.core.util.Preconditions;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat.AccessibilityActionCompat;
import androidx.core.view.accessibility.AccessibilityNodeProviderCompat;
import androidx.core.view.accessibility.AccessibilityViewCommand;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Set;
import java.util.WeakHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import mt.Log229316;
import mt.Log5ECF72;
import mt.LogE84000;

public class ViewCompat
{
  private static final int[] ACCESSIBILITY_ACTIONS_RESOURCE_IDS = { R.id.accessibility_custom_action_0, R.id.accessibility_custom_action_1, R.id.accessibility_custom_action_2, R.id.accessibility_custom_action_3, R.id.accessibility_custom_action_4, R.id.accessibility_custom_action_5, R.id.accessibility_custom_action_6, R.id.accessibility_custom_action_7, R.id.accessibility_custom_action_8, R.id.accessibility_custom_action_9, R.id.accessibility_custom_action_10, R.id.accessibility_custom_action_11, R.id.accessibility_custom_action_12, R.id.accessibility_custom_action_13, R.id.accessibility_custom_action_14, R.id.accessibility_custom_action_15, R.id.accessibility_custom_action_16, R.id.accessibility_custom_action_17, R.id.accessibility_custom_action_18, R.id.accessibility_custom_action_19, R.id.accessibility_custom_action_20, R.id.accessibility_custom_action_21, R.id.accessibility_custom_action_22, R.id.accessibility_custom_action_23, R.id.accessibility_custom_action_24, R.id.accessibility_custom_action_25, R.id.accessibility_custom_action_26, R.id.accessibility_custom_action_27, R.id.accessibility_custom_action_28, R.id.accessibility_custom_action_29, R.id.accessibility_custom_action_30, R.id.accessibility_custom_action_31 };
  public static final int ACCESSIBILITY_LIVE_REGION_ASSERTIVE = 2;
  public static final int ACCESSIBILITY_LIVE_REGION_NONE = 0;
  public static final int ACCESSIBILITY_LIVE_REGION_POLITE = 1;
  public static final int IMPORTANT_FOR_ACCESSIBILITY_AUTO = 0;
  public static final int IMPORTANT_FOR_ACCESSIBILITY_NO = 2;
  public static final int IMPORTANT_FOR_ACCESSIBILITY_NO_HIDE_DESCENDANTS = 4;
  public static final int IMPORTANT_FOR_ACCESSIBILITY_YES = 1;
  @Deprecated
  public static final int LAYER_TYPE_HARDWARE = 2;
  @Deprecated
  public static final int LAYER_TYPE_NONE = 0;
  @Deprecated
  public static final int LAYER_TYPE_SOFTWARE = 1;
  public static final int LAYOUT_DIRECTION_INHERIT = 2;
  public static final int LAYOUT_DIRECTION_LOCALE = 3;
  public static final int LAYOUT_DIRECTION_LTR = 0;
  public static final int LAYOUT_DIRECTION_RTL = 1;
  @Deprecated
  public static final int MEASURED_HEIGHT_STATE_SHIFT = 16;
  @Deprecated
  public static final int MEASURED_SIZE_MASK = 16777215;
  @Deprecated
  public static final int MEASURED_STATE_MASK = -16777216;
  @Deprecated
  public static final int MEASURED_STATE_TOO_SMALL = 16777216;
  private static final OnReceiveContentViewBehavior NO_OP_ON_RECEIVE_CONTENT_VIEW_BEHAVIOR = new ViewCompat..ExternalSyntheticLambda0();
  @Deprecated
  public static final int OVER_SCROLL_ALWAYS = 0;
  @Deprecated
  public static final int OVER_SCROLL_IF_CONTENT_SCROLLS = 1;
  @Deprecated
  public static final int OVER_SCROLL_NEVER = 2;
  public static final int SCROLL_AXIS_HORIZONTAL = 1;
  public static final int SCROLL_AXIS_NONE = 0;
  public static final int SCROLL_AXIS_VERTICAL = 2;
  public static final int SCROLL_INDICATOR_BOTTOM = 2;
  public static final int SCROLL_INDICATOR_END = 32;
  public static final int SCROLL_INDICATOR_LEFT = 4;
  public static final int SCROLL_INDICATOR_RIGHT = 8;
  public static final int SCROLL_INDICATOR_START = 16;
  public static final int SCROLL_INDICATOR_TOP = 1;
  private static final String TAG = "ViewCompat";
  public static final int TYPE_NON_TOUCH = 1;
  public static final int TYPE_TOUCH = 0;
  private static boolean sAccessibilityDelegateCheckFailed;
  private static Field sAccessibilityDelegateField;
  private static final AccessibilityPaneVisibilityManager sAccessibilityPaneVisibilityManager = new AccessibilityPaneVisibilityManager();
  private static Method sChildrenDrawingOrderMethod;
  private static Method sDispatchFinishTemporaryDetach;
  private static Method sDispatchStartTemporaryDetach;
  private static Field sMinHeightField;
  private static boolean sMinHeightFieldFetched;
  private static Field sMinWidthField;
  private static boolean sMinWidthFieldFetched;
  private static final AtomicInteger sNextGeneratedId = new AtomicInteger(1);
  private static boolean sTempDetachBound;
  private static ThreadLocal<Rect> sThreadLocalRect;
  private static WeakHashMap<View, String> sTransitionNameMap;
  private static WeakHashMap<View, ViewPropertyAnimatorCompat> sViewPropertyAnimatorMap = null;
  
  static
  {
    sAccessibilityDelegateCheckFailed = false;
  }
  
  private static AccessibilityViewProperty<Boolean> accessibilityHeadingProperty()
  {
    new AccessibilityViewProperty(R.id.tag_accessibility_heading, Boolean.class, 28)
    {
      Boolean frameworkGet(View paramAnonymousView)
      {
        return Boolean.valueOf(ViewCompat.Api28Impl.isAccessibilityHeading(paramAnonymousView));
      }
      
      void frameworkSet(View paramAnonymousView, Boolean paramAnonymousBoolean)
      {
        ViewCompat.Api28Impl.setAccessibilityHeading(paramAnonymousView, paramAnonymousBoolean.booleanValue());
      }
      
      boolean shouldUpdate(Boolean paramAnonymousBoolean1, Boolean paramAnonymousBoolean2)
      {
        return booleanNullToFalseEquals(paramAnonymousBoolean1, paramAnonymousBoolean2) ^ true;
      }
    };
  }
  
  public static int addAccessibilityAction(View paramView, CharSequence paramCharSequence, AccessibilityViewCommand paramAccessibilityViewCommand)
  {
    int i = getAvailableActionIdFromResources(paramView, paramCharSequence);
    if (i != -1) {
      addAccessibilityAction(paramView, new AccessibilityNodeInfoCompat.AccessibilityActionCompat(i, paramCharSequence, paramAccessibilityViewCommand));
    }
    return i;
  }
  
  private static void addAccessibilityAction(View paramView, AccessibilityNodeInfoCompat.AccessibilityActionCompat paramAccessibilityActionCompat)
  {
    if (Build.VERSION.SDK_INT >= 21)
    {
      ensureAccessibilityDelegateCompat(paramView);
      removeActionWithId(paramAccessibilityActionCompat.getId(), paramView);
      getActionList(paramView).add(paramAccessibilityActionCompat);
      notifyViewAccessibilityStateChangedIfNeeded(paramView, 0);
    }
  }
  
  public static void addKeyboardNavigationClusters(View paramView, Collection<View> paramCollection, int paramInt)
  {
    if (Build.VERSION.SDK_INT >= 26) {
      Api26Impl.addKeyboardNavigationClusters(paramView, paramCollection, paramInt);
    }
  }
  
  public static void addOnUnhandledKeyEventListener(View paramView, OnUnhandledKeyEventListenerCompat paramOnUnhandledKeyEventListenerCompat)
  {
    if (Build.VERSION.SDK_INT >= 28)
    {
      Api28Impl.addOnUnhandledKeyEventListener(paramView, paramOnUnhandledKeyEventListenerCompat);
      return;
    }
    ArrayList localArrayList2 = (ArrayList)paramView.getTag(R.id.tag_unhandled_key_listeners);
    ArrayList localArrayList1 = localArrayList2;
    if (localArrayList2 == null)
    {
      localArrayList1 = new ArrayList();
      paramView.setTag(R.id.tag_unhandled_key_listeners, localArrayList1);
    }
    localArrayList1.add(paramOnUnhandledKeyEventListenerCompat);
    if (localArrayList1.size() == 1) {
      UnhandledKeyEventManager.registerListeningView(paramView);
    }
  }
  
  public static ViewPropertyAnimatorCompat animate(View paramView)
  {
    if (sViewPropertyAnimatorMap == null) {
      sViewPropertyAnimatorMap = new WeakHashMap();
    }
    ViewPropertyAnimatorCompat localViewPropertyAnimatorCompat2 = (ViewPropertyAnimatorCompat)sViewPropertyAnimatorMap.get(paramView);
    ViewPropertyAnimatorCompat localViewPropertyAnimatorCompat1 = localViewPropertyAnimatorCompat2;
    if (localViewPropertyAnimatorCompat2 == null)
    {
      localViewPropertyAnimatorCompat1 = new ViewPropertyAnimatorCompat(paramView);
      sViewPropertyAnimatorMap.put(paramView, localViewPropertyAnimatorCompat1);
    }
    return localViewPropertyAnimatorCompat1;
  }
  
  private static void bindTempDetach()
  {
    try
    {
      sDispatchStartTemporaryDetach = View.class.getDeclaredMethod("dispatchStartTemporaryDetach", new Class[0]);
      sDispatchFinishTemporaryDetach = View.class.getDeclaredMethod("dispatchFinishTemporaryDetach", new Class[0]);
    }
    catch (NoSuchMethodException localNoSuchMethodException)
    {
      Log.e("ViewCompat", "Couldn't find method", localNoSuchMethodException);
    }
    sTempDetachBound = true;
  }
  
  @Deprecated
  public static boolean canScrollHorizontally(View paramView, int paramInt)
  {
    return paramView.canScrollHorizontally(paramInt);
  }
  
  @Deprecated
  public static boolean canScrollVertically(View paramView, int paramInt)
  {
    return paramView.canScrollVertically(paramInt);
  }
  
  public static void cancelDragAndDrop(View paramView)
  {
    if (Build.VERSION.SDK_INT >= 24) {
      Api24Impl.cancelDragAndDrop(paramView);
    }
  }
  
  @Deprecated
  public static int combineMeasuredStates(int paramInt1, int paramInt2)
  {
    return View.combineMeasuredStates(paramInt1, paramInt2);
  }
  
  private static void compatOffsetLeftAndRight(View paramView, int paramInt)
  {
    paramView.offsetLeftAndRight(paramInt);
    if (paramView.getVisibility() == 0)
    {
      tickleInvalidationFlag(paramView);
      paramView = paramView.getParent();
      if ((paramView instanceof View)) {
        tickleInvalidationFlag((View)paramView);
      }
    }
  }
  
  private static void compatOffsetTopAndBottom(View paramView, int paramInt)
  {
    paramView.offsetTopAndBottom(paramInt);
    if (paramView.getVisibility() == 0)
    {
      tickleInvalidationFlag(paramView);
      paramView = paramView.getParent();
      if ((paramView instanceof View)) {
        tickleInvalidationFlag((View)paramView);
      }
    }
  }
  
  public static WindowInsetsCompat computeSystemWindowInsets(View paramView, WindowInsetsCompat paramWindowInsetsCompat, Rect paramRect)
  {
    if (Build.VERSION.SDK_INT >= 21) {
      return Api21Impl.computeSystemWindowInsets(paramView, paramWindowInsetsCompat, paramRect);
    }
    return paramWindowInsetsCompat;
  }
  
  public static WindowInsetsCompat dispatchApplyWindowInsets(View paramView, WindowInsetsCompat paramWindowInsetsCompat)
  {
    if (Build.VERSION.SDK_INT >= 21)
    {
      WindowInsets localWindowInsets2 = paramWindowInsetsCompat.toWindowInsets();
      if (localWindowInsets2 != null)
      {
        WindowInsets localWindowInsets1 = Api20Impl.dispatchApplyWindowInsets(paramView, localWindowInsets2);
        if (!localWindowInsets1.equals(localWindowInsets2)) {
          return WindowInsetsCompat.toWindowInsetsCompat(localWindowInsets1, paramView);
        }
      }
    }
    return paramWindowInsetsCompat;
  }
  
  public static void dispatchFinishTemporaryDetach(View paramView)
  {
    if (Build.VERSION.SDK_INT >= 24)
    {
      Api24Impl.dispatchFinishTemporaryDetach(paramView);
    }
    else
    {
      if (!sTempDetachBound) {
        bindTempDetach();
      }
      Method localMethod = sDispatchFinishTemporaryDetach;
      if (localMethod != null) {
        try
        {
          localMethod.invoke(paramView, new Object[0]);
        }
        catch (Exception paramView)
        {
          Log.d("ViewCompat", "Error calling dispatchFinishTemporaryDetach", paramView);
        }
      } else {
        paramView.onFinishTemporaryDetach();
      }
    }
  }
  
  public static boolean dispatchNestedFling(View paramView, float paramFloat1, float paramFloat2, boolean paramBoolean)
  {
    if (Build.VERSION.SDK_INT >= 21) {
      return Api21Impl.dispatchNestedFling(paramView, paramFloat1, paramFloat2, paramBoolean);
    }
    if ((paramView instanceof NestedScrollingChild)) {
      return ((NestedScrollingChild)paramView).dispatchNestedFling(paramFloat1, paramFloat2, paramBoolean);
    }
    return false;
  }
  
  public static boolean dispatchNestedPreFling(View paramView, float paramFloat1, float paramFloat2)
  {
    if (Build.VERSION.SDK_INT >= 21) {
      return Api21Impl.dispatchNestedPreFling(paramView, paramFloat1, paramFloat2);
    }
    if ((paramView instanceof NestedScrollingChild)) {
      return ((NestedScrollingChild)paramView).dispatchNestedPreFling(paramFloat1, paramFloat2);
    }
    return false;
  }
  
  public static boolean dispatchNestedPreScroll(View paramView, int paramInt1, int paramInt2, int[] paramArrayOfInt1, int[] paramArrayOfInt2)
  {
    if (Build.VERSION.SDK_INT >= 21) {
      return Api21Impl.dispatchNestedPreScroll(paramView, paramInt1, paramInt2, paramArrayOfInt1, paramArrayOfInt2);
    }
    if ((paramView instanceof NestedScrollingChild)) {
      return ((NestedScrollingChild)paramView).dispatchNestedPreScroll(paramInt1, paramInt2, paramArrayOfInt1, paramArrayOfInt2);
    }
    return false;
  }
  
  public static boolean dispatchNestedPreScroll(View paramView, int paramInt1, int paramInt2, int[] paramArrayOfInt1, int[] paramArrayOfInt2, int paramInt3)
  {
    if ((paramView instanceof NestedScrollingChild2)) {
      return ((NestedScrollingChild2)paramView).dispatchNestedPreScroll(paramInt1, paramInt2, paramArrayOfInt1, paramArrayOfInt2, paramInt3);
    }
    if (paramInt3 == 0) {
      return dispatchNestedPreScroll(paramView, paramInt1, paramInt2, paramArrayOfInt1, paramArrayOfInt2);
    }
    return false;
  }
  
  public static void dispatchNestedScroll(View paramView, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int[] paramArrayOfInt1, int paramInt5, int[] paramArrayOfInt2)
  {
    if ((paramView instanceof NestedScrollingChild3)) {
      ((NestedScrollingChild3)paramView).dispatchNestedScroll(paramInt1, paramInt2, paramInt3, paramInt4, paramArrayOfInt1, paramInt5, paramArrayOfInt2);
    } else {
      dispatchNestedScroll(paramView, paramInt1, paramInt2, paramInt3, paramInt4, paramArrayOfInt1, paramInt5);
    }
  }
  
  public static boolean dispatchNestedScroll(View paramView, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int[] paramArrayOfInt)
  {
    if (Build.VERSION.SDK_INT >= 21) {
      return Api21Impl.dispatchNestedScroll(paramView, paramInt1, paramInt2, paramInt3, paramInt4, paramArrayOfInt);
    }
    if ((paramView instanceof NestedScrollingChild)) {
      return ((NestedScrollingChild)paramView).dispatchNestedScroll(paramInt1, paramInt2, paramInt3, paramInt4, paramArrayOfInt);
    }
    return false;
  }
  
  public static boolean dispatchNestedScroll(View paramView, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int[] paramArrayOfInt, int paramInt5)
  {
    if ((paramView instanceof NestedScrollingChild2)) {
      return ((NestedScrollingChild2)paramView).dispatchNestedScroll(paramInt1, paramInt2, paramInt3, paramInt4, paramArrayOfInt, paramInt5);
    }
    if (paramInt5 == 0) {
      return dispatchNestedScroll(paramView, paramInt1, paramInt2, paramInt3, paramInt4, paramArrayOfInt);
    }
    return false;
  }
  
  public static void dispatchStartTemporaryDetach(View paramView)
  {
    if (Build.VERSION.SDK_INT >= 24)
    {
      Api24Impl.dispatchStartTemporaryDetach(paramView);
    }
    else
    {
      if (!sTempDetachBound) {
        bindTempDetach();
      }
      Method localMethod = sDispatchStartTemporaryDetach;
      if (localMethod != null) {
        try
        {
          localMethod.invoke(paramView, new Object[0]);
        }
        catch (Exception paramView)
        {
          Log.d("ViewCompat", "Error calling dispatchStartTemporaryDetach", paramView);
        }
      } else {
        paramView.onStartTemporaryDetach();
      }
    }
  }
  
  static boolean dispatchUnhandledKeyEventBeforeCallback(View paramView, KeyEvent paramKeyEvent)
  {
    if (Build.VERSION.SDK_INT >= 28) {
      return false;
    }
    return UnhandledKeyEventManager.at(paramView).dispatch(paramView, paramKeyEvent);
  }
  
  static boolean dispatchUnhandledKeyEventBeforeHierarchy(View paramView, KeyEvent paramKeyEvent)
  {
    if (Build.VERSION.SDK_INT >= 28) {
      return false;
    }
    return UnhandledKeyEventManager.at(paramView).preDispatch(paramKeyEvent);
  }
  
  public static void enableAccessibleClickableSpanSupport(View paramView)
  {
    if (Build.VERSION.SDK_INT >= 19) {
      ensureAccessibilityDelegateCompat(paramView);
    }
  }
  
  static void ensureAccessibilityDelegateCompat(View paramView)
  {
    AccessibilityDelegateCompat localAccessibilityDelegateCompat2 = getAccessibilityDelegate(paramView);
    AccessibilityDelegateCompat localAccessibilityDelegateCompat1 = localAccessibilityDelegateCompat2;
    if (localAccessibilityDelegateCompat2 == null) {
      localAccessibilityDelegateCompat1 = new AccessibilityDelegateCompat();
    }
    setAccessibilityDelegate(paramView, localAccessibilityDelegateCompat1);
  }
  
  public static int generateViewId()
  {
    if (Build.VERSION.SDK_INT >= 17) {
      return Api17Impl.generateViewId();
    }
    for (;;)
    {
      AtomicInteger localAtomicInteger = sNextGeneratedId;
      int k = localAtomicInteger.get();
      int j = k + 1;
      int i = j;
      if (j > 16777215) {
        i = 1;
      }
      if (localAtomicInteger.compareAndSet(k, i)) {
        return k;
      }
    }
  }
  
  public static AccessibilityDelegateCompat getAccessibilityDelegate(View paramView)
  {
    paramView = getAccessibilityDelegateInternal(paramView);
    if (paramView == null) {
      return null;
    }
    if ((paramView instanceof AccessibilityDelegateCompat.AccessibilityDelegateAdapter)) {
      return ((AccessibilityDelegateCompat.AccessibilityDelegateAdapter)paramView).mCompat;
    }
    return new AccessibilityDelegateCompat(paramView);
  }
  
  private static View.AccessibilityDelegate getAccessibilityDelegateInternal(View paramView)
  {
    if (Build.VERSION.SDK_INT >= 29) {
      return Api29Impl.getAccessibilityDelegate(paramView);
    }
    return getAccessibilityDelegateThroughReflection(paramView);
  }
  
  /* Error */
  private static View.AccessibilityDelegate getAccessibilityDelegateThroughReflection(View paramView)
  {
    // Byte code:
    //   0: getstatic 181	androidx/core/view/ViewCompat:sAccessibilityDelegateCheckFailed	Z
    //   3: ifeq +5 -> 8
    //   6: aconst_null
    //   7: areturn
    //   8: getstatic 615	androidx/core/view/ViewCompat:sAccessibilityDelegateField	Ljava/lang/reflect/Field;
    //   11: ifnonnull +32 -> 43
    //   14: ldc_w 364
    //   17: ldc_w 617
    //   20: invokevirtual 621	java/lang/Class:getDeclaredField	(Ljava/lang/String;)Ljava/lang/reflect/Field;
    //   23: astore_1
    //   24: aload_1
    //   25: putstatic 615	androidx/core/view/ViewCompat:sAccessibilityDelegateField	Ljava/lang/reflect/Field;
    //   28: aload_1
    //   29: iconst_1
    //   30: invokevirtual 627	java/lang/reflect/Field:setAccessible	(Z)V
    //   33: goto +10 -> 43
    //   36: astore_0
    //   37: iconst_1
    //   38: putstatic 181	androidx/core/view/ViewCompat:sAccessibilityDelegateCheckFailed	Z
    //   41: aconst_null
    //   42: areturn
    //   43: getstatic 615	androidx/core/view/ViewCompat:sAccessibilityDelegateField	Ljava/lang/reflect/Field;
    //   46: aload_0
    //   47: invokevirtual 628	java/lang/reflect/Field:get	(Ljava/lang/Object;)Ljava/lang/Object;
    //   50: astore_0
    //   51: aload_0
    //   52: instanceof 630
    //   55: ifeq +10 -> 65
    //   58: aload_0
    //   59: checkcast 630	android/view/View$AccessibilityDelegate
    //   62: astore_0
    //   63: aload_0
    //   64: areturn
    //   65: aconst_null
    //   66: areturn
    //   67: astore_0
    //   68: iconst_1
    //   69: putstatic 181	androidx/core/view/ViewCompat:sAccessibilityDelegateCheckFailed	Z
    //   72: aconst_null
    //   73: areturn
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	74	0	paramView	View
    //   23	6	1	localField	Field
    // Exception table:
    //   from	to	target	type
    //   14	33	36	finally
    //   43	63	67	finally
  }
  
  public static int getAccessibilityLiveRegion(View paramView)
  {
    if (Build.VERSION.SDK_INT >= 19) {
      return Api19Impl.getAccessibilityLiveRegion(paramView);
    }
    return 0;
  }
  
  public static AccessibilityNodeProviderCompat getAccessibilityNodeProvider(View paramView)
  {
    if (Build.VERSION.SDK_INT >= 16)
    {
      paramView = Api16Impl.getAccessibilityNodeProvider(paramView);
      if (paramView != null) {
        return new AccessibilityNodeProviderCompat(paramView);
      }
    }
    return null;
  }
  
  public static CharSequence getAccessibilityPaneTitle(View paramView)
  {
    return (CharSequence)paneTitleProperty().get(paramView);
  }
  
  private static List<AccessibilityNodeInfoCompat.AccessibilityActionCompat> getActionList(View paramView)
  {
    ArrayList localArrayList2 = (ArrayList)paramView.getTag(R.id.tag_accessibility_actions);
    ArrayList localArrayList1 = localArrayList2;
    if (localArrayList2 == null)
    {
      localArrayList1 = new ArrayList();
      paramView.setTag(R.id.tag_accessibility_actions, localArrayList1);
    }
    return localArrayList1;
  }
  
  @Deprecated
  public static float getAlpha(View paramView)
  {
    return paramView.getAlpha();
  }
  
  private static int getAvailableActionIdFromResources(View paramView, CharSequence paramCharSequence)
  {
    int j = -1;
    paramView = getActionList(paramView);
    for (int i = 0; i < paramView.size(); i++) {
      if (TextUtils.equals(paramCharSequence, ((AccessibilityNodeInfoCompat.AccessibilityActionCompat)paramView.get(i)).getLabel())) {
        return ((AccessibilityNodeInfoCompat.AccessibilityActionCompat)paramView.get(i)).getId();
      }
    }
    for (i = 0;; i++)
    {
      paramCharSequence = ACCESSIBILITY_ACTIONS_RESOURCE_IDS;
      if ((i >= paramCharSequence.length) || (j != -1)) {
        break;
      }
      int i1 = paramCharSequence[i];
      int m = 1;
      for (int k = 0; k < paramView.size(); k++)
      {
        int n;
        if (((AccessibilityNodeInfoCompat.AccessibilityActionCompat)paramView.get(k)).getId() != i1) {
          n = 1;
        } else {
          n = 0;
        }
        m &= n;
      }
      if (m != 0) {
        j = i1;
      }
    }
    return j;
  }
  
  public static ColorStateList getBackgroundTintList(View paramView)
  {
    if (Build.VERSION.SDK_INT >= 21) {
      return Api21Impl.getBackgroundTintList(paramView);
    }
    if ((paramView instanceof TintableBackgroundView)) {
      paramView = ((TintableBackgroundView)paramView).getSupportBackgroundTintList();
    } else {
      paramView = null;
    }
    return paramView;
  }
  
  public static PorterDuff.Mode getBackgroundTintMode(View paramView)
  {
    if (Build.VERSION.SDK_INT >= 21) {
      return Api21Impl.getBackgroundTintMode(paramView);
    }
    if ((paramView instanceof TintableBackgroundView)) {
      paramView = ((TintableBackgroundView)paramView).getSupportBackgroundTintMode();
    } else {
      paramView = null;
    }
    return paramView;
  }
  
  public static Rect getClipBounds(View paramView)
  {
    if (Build.VERSION.SDK_INT >= 18) {
      return Api18Impl.getClipBounds(paramView);
    }
    return null;
  }
  
  public static Display getDisplay(View paramView)
  {
    if (Build.VERSION.SDK_INT >= 17) {
      return Api17Impl.getDisplay(paramView);
    }
    if (isAttachedToWindow(paramView)) {
      return ((WindowManager)paramView.getContext().getSystemService("window")).getDefaultDisplay();
    }
    return null;
  }
  
  public static float getElevation(View paramView)
  {
    if (Build.VERSION.SDK_INT >= 21) {
      return Api21Impl.getElevation(paramView);
    }
    return 0.0F;
  }
  
  private static Rect getEmptyTempRect()
  {
    if (sThreadLocalRect == null) {
      sThreadLocalRect = new ThreadLocal();
    }
    Rect localRect2 = (Rect)sThreadLocalRect.get();
    Rect localRect1 = localRect2;
    if (localRect2 == null)
    {
      localRect1 = new Rect();
      sThreadLocalRect.set(localRect1);
    }
    localRect1.setEmpty();
    return localRect1;
  }
  
  private static OnReceiveContentViewBehavior getFallback(View paramView)
  {
    if ((paramView instanceof OnReceiveContentViewBehavior)) {
      return (OnReceiveContentViewBehavior)paramView;
    }
    return NO_OP_ON_RECEIVE_CONTENT_VIEW_BEHAVIOR;
  }
  
  public static boolean getFitsSystemWindows(View paramView)
  {
    if (Build.VERSION.SDK_INT >= 16) {
      return Api16Impl.getFitsSystemWindows(paramView);
    }
    return false;
  }
  
  public static int getImportantForAccessibility(View paramView)
  {
    if (Build.VERSION.SDK_INT >= 16) {
      return Api16Impl.getImportantForAccessibility(paramView);
    }
    return 0;
  }
  
  public static int getImportantForAutofill(View paramView)
  {
    if (Build.VERSION.SDK_INT >= 26) {
      return Api26Impl.getImportantForAutofill(paramView);
    }
    return 0;
  }
  
  public static int getLabelFor(View paramView)
  {
    if (Build.VERSION.SDK_INT >= 17) {
      return Api17Impl.getLabelFor(paramView);
    }
    return 0;
  }
  
  @Deprecated
  public static int getLayerType(View paramView)
  {
    return paramView.getLayerType();
  }
  
  public static int getLayoutDirection(View paramView)
  {
    if (Build.VERSION.SDK_INT >= 17) {
      return Api17Impl.getLayoutDirection(paramView);
    }
    return 0;
  }
  
  @Deprecated
  public static Matrix getMatrix(View paramView)
  {
    return paramView.getMatrix();
  }
  
  @Deprecated
  public static int getMeasuredHeightAndState(View paramView)
  {
    return paramView.getMeasuredHeightAndState();
  }
  
  @Deprecated
  public static int getMeasuredState(View paramView)
  {
    return paramView.getMeasuredState();
  }
  
  @Deprecated
  public static int getMeasuredWidthAndState(View paramView)
  {
    return paramView.getMeasuredWidthAndState();
  }
  
  public static int getMinimumHeight(View paramView)
  {
    if (Build.VERSION.SDK_INT >= 16) {
      return Api16Impl.getMinimumHeight(paramView);
    }
    if (!sMinHeightFieldFetched)
    {
      try
      {
        Field localField1 = View.class.getDeclaredField("mMinHeight");
        sMinHeightField = localField1;
        localField1.setAccessible(true);
      }
      catch (NoSuchFieldException localNoSuchFieldException) {}
      sMinHeightFieldFetched = true;
    }
    Field localField2 = sMinHeightField;
    if (localField2 != null) {
      try
      {
        int i = ((Integer)localField2.get(paramView)).intValue();
        return i;
      }
      catch (Exception paramView) {}
    }
    return 0;
  }
  
  public static int getMinimumWidth(View paramView)
  {
    if (Build.VERSION.SDK_INT >= 16) {
      return Api16Impl.getMinimumWidth(paramView);
    }
    if (!sMinWidthFieldFetched)
    {
      try
      {
        Field localField1 = View.class.getDeclaredField("mMinWidth");
        sMinWidthField = localField1;
        localField1.setAccessible(true);
      }
      catch (NoSuchFieldException localNoSuchFieldException) {}
      sMinWidthFieldFetched = true;
    }
    Field localField2 = sMinWidthField;
    if (localField2 != null) {
      try
      {
        int i = ((Integer)localField2.get(paramView)).intValue();
        return i;
      }
      catch (Exception paramView) {}
    }
    return 0;
  }
  
  public static int getNextClusterForwardId(View paramView)
  {
    if (Build.VERSION.SDK_INT >= 26) {
      return Api26Impl.getNextClusterForwardId(paramView);
    }
    return -1;
  }
  
  public static String[] getOnReceiveContentMimeTypes(View paramView)
  {
    if (Build.VERSION.SDK_INT >= 31) {
      return Api31Impl.getReceiveContentMimeTypes(paramView);
    }
    return (String[])paramView.getTag(R.id.tag_on_receive_content_mime_types);
  }
  
  @Deprecated
  public static int getOverScrollMode(View paramView)
  {
    return paramView.getOverScrollMode();
  }
  
  public static int getPaddingEnd(View paramView)
  {
    if (Build.VERSION.SDK_INT >= 17) {
      return Api17Impl.getPaddingEnd(paramView);
    }
    return paramView.getPaddingRight();
  }
  
  public static int getPaddingStart(View paramView)
  {
    if (Build.VERSION.SDK_INT >= 17) {
      return Api17Impl.getPaddingStart(paramView);
    }
    return paramView.getPaddingLeft();
  }
  
  public static ViewParent getParentForAccessibility(View paramView)
  {
    if (Build.VERSION.SDK_INT >= 16) {
      return Api16Impl.getParentForAccessibility(paramView);
    }
    return paramView.getParent();
  }
  
  @Deprecated
  public static float getPivotX(View paramView)
  {
    return paramView.getPivotX();
  }
  
  @Deprecated
  public static float getPivotY(View paramView)
  {
    return paramView.getPivotY();
  }
  
  public static WindowInsetsCompat getRootWindowInsets(View paramView)
  {
    if (Build.VERSION.SDK_INT >= 23) {
      return Api23Impl.getRootWindowInsets(paramView);
    }
    if (Build.VERSION.SDK_INT >= 21) {
      return Api21Impl.getRootWindowInsets(paramView);
    }
    return null;
  }
  
  @Deprecated
  public static float getRotation(View paramView)
  {
    return paramView.getRotation();
  }
  
  @Deprecated
  public static float getRotationX(View paramView)
  {
    return paramView.getRotationX();
  }
  
  @Deprecated
  public static float getRotationY(View paramView)
  {
    return paramView.getRotationY();
  }
  
  @Deprecated
  public static float getScaleX(View paramView)
  {
    return paramView.getScaleX();
  }
  
  @Deprecated
  public static float getScaleY(View paramView)
  {
    return paramView.getScaleY();
  }
  
  public static int getScrollIndicators(View paramView)
  {
    if (Build.VERSION.SDK_INT >= 23) {
      return Api23Impl.getScrollIndicators(paramView);
    }
    return 0;
  }
  
  public static CharSequence getStateDescription(View paramView)
  {
    return (CharSequence)stateDescriptionProperty().get(paramView);
  }
  
  public static List<Rect> getSystemGestureExclusionRects(View paramView)
  {
    if (Build.VERSION.SDK_INT >= 29) {
      return Api29Impl.getSystemGestureExclusionRects(paramView);
    }
    return Collections.emptyList();
  }
  
  public static String getTransitionName(View paramView)
  {
    if (Build.VERSION.SDK_INT >= 21)
    {
      paramView = Api21Impl.getTransitionName(paramView);
      Log5ECF72.a(paramView);
      LogE84000.a(paramView);
      Log229316.a(paramView);
      return paramView;
    }
    WeakHashMap localWeakHashMap = sTransitionNameMap;
    if (localWeakHashMap == null) {
      return null;
    }
    return (String)localWeakHashMap.get(paramView);
  }
  
  @Deprecated
  public static float getTranslationX(View paramView)
  {
    return paramView.getTranslationX();
  }
  
  @Deprecated
  public static float getTranslationY(View paramView)
  {
    return paramView.getTranslationY();
  }
  
  public static float getTranslationZ(View paramView)
  {
    if (Build.VERSION.SDK_INT >= 21) {
      return Api21Impl.getTranslationZ(paramView);
    }
    return 0.0F;
  }
  
  @Deprecated
  public static WindowInsetsControllerCompat getWindowInsetsController(View paramView)
  {
    if (Build.VERSION.SDK_INT >= 30) {
      return Api30Impl.getWindowInsetsController(paramView);
    }
    for (Object localObject1 = paramView.getContext();; localObject1 = ((ContextWrapper)localObject1).getBaseContext())
    {
      boolean bool = localObject1 instanceof ContextWrapper;
      Object localObject2 = null;
      if (!bool) {
        break;
      }
      if ((localObject1 instanceof Activity))
      {
        Window localWindow = ((Activity)localObject1).getWindow();
        localObject1 = localObject2;
        if (localWindow != null) {
          localObject1 = WindowCompat.getInsetsController(localWindow, paramView);
        }
        return (WindowInsetsControllerCompat)localObject1;
      }
    }
    return null;
  }
  
  @Deprecated
  public static int getWindowSystemUiVisibility(View paramView)
  {
    if (Build.VERSION.SDK_INT >= 16) {
      return Api16Impl.getWindowSystemUiVisibility(paramView);
    }
    return 0;
  }
  
  @Deprecated
  public static float getX(View paramView)
  {
    return paramView.getX();
  }
  
  @Deprecated
  public static float getY(View paramView)
  {
    return paramView.getY();
  }
  
  public static float getZ(View paramView)
  {
    if (Build.VERSION.SDK_INT >= 21) {
      return Api21Impl.getZ(paramView);
    }
    return 0.0F;
  }
  
  public static boolean hasAccessibilityDelegate(View paramView)
  {
    boolean bool;
    if (getAccessibilityDelegateInternal(paramView) != null) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public static boolean hasExplicitFocusable(View paramView)
  {
    if (Build.VERSION.SDK_INT >= 26) {
      return Api26Impl.hasExplicitFocusable(paramView);
    }
    return paramView.hasFocusable();
  }
  
  public static boolean hasNestedScrollingParent(View paramView)
  {
    if (Build.VERSION.SDK_INT >= 21) {
      return Api21Impl.hasNestedScrollingParent(paramView);
    }
    if ((paramView instanceof NestedScrollingChild)) {
      return ((NestedScrollingChild)paramView).hasNestedScrollingParent();
    }
    return false;
  }
  
  public static boolean hasNestedScrollingParent(View paramView, int paramInt)
  {
    if ((paramView instanceof NestedScrollingChild2)) {
      ((NestedScrollingChild2)paramView).hasNestedScrollingParent(paramInt);
    } else if (paramInt == 0) {
      return hasNestedScrollingParent(paramView);
    }
    return false;
  }
  
  public static boolean hasOnClickListeners(View paramView)
  {
    if (Build.VERSION.SDK_INT >= 15) {
      return Api15Impl.hasOnClickListeners(paramView);
    }
    return false;
  }
  
  public static boolean hasOverlappingRendering(View paramView)
  {
    if (Build.VERSION.SDK_INT >= 16) {
      return Api16Impl.hasOverlappingRendering(paramView);
    }
    return true;
  }
  
  public static boolean hasTransientState(View paramView)
  {
    if (Build.VERSION.SDK_INT >= 16) {
      return Api16Impl.hasTransientState(paramView);
    }
    return false;
  }
  
  public static boolean isAccessibilityHeading(View paramView)
  {
    paramView = (Boolean)accessibilityHeadingProperty().get(paramView);
    boolean bool;
    if ((paramView != null) && (paramView.booleanValue())) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public static boolean isAttachedToWindow(View paramView)
  {
    if (Build.VERSION.SDK_INT >= 19) {
      return Api19Impl.isAttachedToWindow(paramView);
    }
    boolean bool;
    if (paramView.getWindowToken() != null) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public static boolean isFocusedByDefault(View paramView)
  {
    if (Build.VERSION.SDK_INT >= 26) {
      return Api26Impl.isFocusedByDefault(paramView);
    }
    return false;
  }
  
  public static boolean isImportantForAccessibility(View paramView)
  {
    if (Build.VERSION.SDK_INT >= 21) {
      return Api21Impl.isImportantForAccessibility(paramView);
    }
    return true;
  }
  
  public static boolean isImportantForAutofill(View paramView)
  {
    if (Build.VERSION.SDK_INT >= 26) {
      return Api26Impl.isImportantForAutofill(paramView);
    }
    return true;
  }
  
  public static boolean isInLayout(View paramView)
  {
    if (Build.VERSION.SDK_INT >= 18) {
      return Api18Impl.isInLayout(paramView);
    }
    return false;
  }
  
  public static boolean isKeyboardNavigationCluster(View paramView)
  {
    if (Build.VERSION.SDK_INT >= 26) {
      return Api26Impl.isKeyboardNavigationCluster(paramView);
    }
    return false;
  }
  
  public static boolean isLaidOut(View paramView)
  {
    if (Build.VERSION.SDK_INT >= 19) {
      return Api19Impl.isLaidOut(paramView);
    }
    boolean bool;
    if ((paramView.getWidth() > 0) && (paramView.getHeight() > 0)) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public static boolean isLayoutDirectionResolved(View paramView)
  {
    if (Build.VERSION.SDK_INT >= 19) {
      return Api19Impl.isLayoutDirectionResolved(paramView);
    }
    return false;
  }
  
  public static boolean isNestedScrollingEnabled(View paramView)
  {
    if (Build.VERSION.SDK_INT >= 21) {
      return Api21Impl.isNestedScrollingEnabled(paramView);
    }
    if ((paramView instanceof NestedScrollingChild)) {
      return ((NestedScrollingChild)paramView).isNestedScrollingEnabled();
    }
    return false;
  }
  
  @Deprecated
  public static boolean isOpaque(View paramView)
  {
    return paramView.isOpaque();
  }
  
  public static boolean isPaddingRelative(View paramView)
  {
    if (Build.VERSION.SDK_INT >= 17) {
      return Api17Impl.isPaddingRelative(paramView);
    }
    return false;
  }
  
  public static boolean isScreenReaderFocusable(View paramView)
  {
    paramView = (Boolean)screenReaderFocusableProperty().get(paramView);
    boolean bool;
    if ((paramView != null) && (paramView.booleanValue())) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  @Deprecated
  public static void jumpDrawablesToCurrentState(View paramView)
  {
    paramView.jumpDrawablesToCurrentState();
  }
  
  public static View keyboardNavigationClusterSearch(View paramView1, View paramView2, int paramInt)
  {
    if (Build.VERSION.SDK_INT >= 26) {
      return Api26Impl.keyboardNavigationClusterSearch(paramView1, paramView2, paramInt);
    }
    return null;
  }
  
  static void notifyViewAccessibilityStateChangedIfNeeded(View paramView, int paramInt)
  {
    Object localObject = (AccessibilityManager)paramView.getContext().getSystemService("accessibility");
    if (!((AccessibilityManager)localObject).isEnabled()) {
      return;
    }
    int i;
    if ((getAccessibilityPaneTitle(paramView) != null) && (paramView.isShown()) && (paramView.getWindowVisibility() == 0)) {
      i = 1;
    } else {
      i = 0;
    }
    int k = getAccessibilityLiveRegion(paramView);
    int j = 32;
    if ((k == 0) && (i == 0))
    {
      if (paramInt == 32)
      {
        AccessibilityEvent localAccessibilityEvent2 = AccessibilityEvent.obtain();
        paramView.onInitializeAccessibilityEvent(localAccessibilityEvent2);
        localAccessibilityEvent2.setEventType(32);
        Api19Impl.setContentChangeTypes(localAccessibilityEvent2, paramInt);
        localAccessibilityEvent2.setSource(paramView);
        paramView.onPopulateAccessibilityEvent(localAccessibilityEvent2);
        localAccessibilityEvent2.getText().add(getAccessibilityPaneTitle(paramView));
        ((AccessibilityManager)localObject).sendAccessibilityEvent(localAccessibilityEvent2);
      }
      else if (paramView.getParent() != null)
      {
        localObject = paramView.getParent();
        try
        {
          Api19Impl.notifySubtreeAccessibilityStateChanged((ViewParent)localObject, paramView, paramView, paramInt);
        }
        catch (AbstractMethodError localAbstractMethodError)
        {
          Log.e("ViewCompat", paramView.getParent().getClass().getSimpleName() + " does not fully implement ViewParent", localAbstractMethodError);
        }
      }
    }
    else
    {
      AccessibilityEvent localAccessibilityEvent1 = AccessibilityEvent.obtain();
      if (i == 0) {
        j = 2048;
      }
      localAccessibilityEvent1.setEventType(j);
      Api19Impl.setContentChangeTypes(localAccessibilityEvent1, paramInt);
      if (i != 0)
      {
        localAccessibilityEvent1.getText().add(getAccessibilityPaneTitle(paramView));
        setViewImportanceForAccessibilityIfNeeded(paramView);
      }
      paramView.sendAccessibilityEventUnchecked(localAccessibilityEvent1);
    }
  }
  
  public static void offsetLeftAndRight(View paramView, int paramInt)
  {
    if (Build.VERSION.SDK_INT >= 23)
    {
      paramView.offsetLeftAndRight(paramInt);
    }
    else if (Build.VERSION.SDK_INT >= 21)
    {
      Rect localRect = getEmptyTempRect();
      boolean bool = false;
      ViewParent localViewParent = paramView.getParent();
      if ((localViewParent instanceof View))
      {
        View localView = (View)localViewParent;
        localRect.set(localView.getLeft(), localView.getTop(), localView.getRight(), localView.getBottom());
        bool = localRect.intersects(paramView.getLeft(), paramView.getTop(), paramView.getRight(), paramView.getBottom()) ^ true;
      }
      compatOffsetLeftAndRight(paramView, paramInt);
      if ((bool) && (localRect.intersect(paramView.getLeft(), paramView.getTop(), paramView.getRight(), paramView.getBottom()))) {
        ((View)localViewParent).invalidate(localRect);
      }
    }
    else
    {
      compatOffsetLeftAndRight(paramView, paramInt);
    }
  }
  
  public static void offsetTopAndBottom(View paramView, int paramInt)
  {
    if (Build.VERSION.SDK_INT >= 23)
    {
      paramView.offsetTopAndBottom(paramInt);
    }
    else if (Build.VERSION.SDK_INT >= 21)
    {
      Rect localRect = getEmptyTempRect();
      boolean bool = false;
      ViewParent localViewParent = paramView.getParent();
      if ((localViewParent instanceof View))
      {
        View localView = (View)localViewParent;
        localRect.set(localView.getLeft(), localView.getTop(), localView.getRight(), localView.getBottom());
        bool = localRect.intersects(paramView.getLeft(), paramView.getTop(), paramView.getRight(), paramView.getBottom()) ^ true;
      }
      compatOffsetTopAndBottom(paramView, paramInt);
      if ((bool) && (localRect.intersect(paramView.getLeft(), paramView.getTop(), paramView.getRight(), paramView.getBottom()))) {
        ((View)localViewParent).invalidate(localRect);
      }
    }
    else
    {
      compatOffsetTopAndBottom(paramView, paramInt);
    }
  }
  
  public static WindowInsetsCompat onApplyWindowInsets(View paramView, WindowInsetsCompat paramWindowInsetsCompat)
  {
    if (Build.VERSION.SDK_INT >= 21)
    {
      WindowInsets localWindowInsets1 = paramWindowInsetsCompat.toWindowInsets();
      if (localWindowInsets1 != null)
      {
        WindowInsets localWindowInsets2 = Api20Impl.onApplyWindowInsets(paramView, localWindowInsets1);
        if (!localWindowInsets2.equals(localWindowInsets1)) {
          return WindowInsetsCompat.toWindowInsetsCompat(localWindowInsets2, paramView);
        }
      }
    }
    return paramWindowInsetsCompat;
  }
  
  @Deprecated
  public static void onInitializeAccessibilityEvent(View paramView, AccessibilityEvent paramAccessibilityEvent)
  {
    paramView.onInitializeAccessibilityEvent(paramAccessibilityEvent);
  }
  
  public static void onInitializeAccessibilityNodeInfo(View paramView, AccessibilityNodeInfoCompat paramAccessibilityNodeInfoCompat)
  {
    paramView.onInitializeAccessibilityNodeInfo(paramAccessibilityNodeInfoCompat.unwrap());
  }
  
  @Deprecated
  public static void onPopulateAccessibilityEvent(View paramView, AccessibilityEvent paramAccessibilityEvent)
  {
    paramView.onPopulateAccessibilityEvent(paramAccessibilityEvent);
  }
  
  private static AccessibilityViewProperty<CharSequence> paneTitleProperty()
  {
    new AccessibilityViewProperty(R.id.tag_accessibility_pane_title, CharSequence.class, 8, 28)
    {
      CharSequence frameworkGet(View paramAnonymousView)
      {
        return ViewCompat.Api28Impl.getAccessibilityPaneTitle(paramAnonymousView);
      }
      
      void frameworkSet(View paramAnonymousView, CharSequence paramAnonymousCharSequence)
      {
        ViewCompat.Api28Impl.setAccessibilityPaneTitle(paramAnonymousView, paramAnonymousCharSequence);
      }
      
      boolean shouldUpdate(CharSequence paramAnonymousCharSequence1, CharSequence paramAnonymousCharSequence2)
      {
        return TextUtils.equals(paramAnonymousCharSequence1, paramAnonymousCharSequence2) ^ true;
      }
    };
  }
  
  public static boolean performAccessibilityAction(View paramView, int paramInt, Bundle paramBundle)
  {
    if (Build.VERSION.SDK_INT >= 16) {
      return Api16Impl.performAccessibilityAction(paramView, paramInt, paramBundle);
    }
    return false;
  }
  
  public static ContentInfoCompat performReceiveContent(View paramView, ContentInfoCompat paramContentInfoCompat)
  {
    if (Log.isLoggable("ViewCompat", 3)) {
      Log.d("ViewCompat", "performReceiveContent: " + paramContentInfoCompat + ", view=" + paramView.getClass().getSimpleName() + "[" + paramView.getId() + "]");
    }
    if (Build.VERSION.SDK_INT >= 31) {
      return Api31Impl.performReceiveContent(paramView, paramContentInfoCompat);
    }
    OnReceiveContentListener localOnReceiveContentListener = (OnReceiveContentListener)paramView.getTag(R.id.tag_on_receive_content_listener);
    if (localOnReceiveContentListener != null)
    {
      paramContentInfoCompat = localOnReceiveContentListener.onReceiveContent(paramView, paramContentInfoCompat);
      if (paramContentInfoCompat == null) {
        paramView = null;
      } else {
        paramView = getFallback(paramView).onReceiveContent(paramContentInfoCompat);
      }
      return paramView;
    }
    return getFallback(paramView).onReceiveContent(paramContentInfoCompat);
  }
  
  public static void postInvalidateOnAnimation(View paramView)
  {
    if (Build.VERSION.SDK_INT >= 16) {
      Api16Impl.postInvalidateOnAnimation(paramView);
    } else {
      paramView.postInvalidate();
    }
  }
  
  public static void postInvalidateOnAnimation(View paramView, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    if (Build.VERSION.SDK_INT >= 16) {
      Api16Impl.postInvalidateOnAnimation(paramView, paramInt1, paramInt2, paramInt3, paramInt4);
    } else {
      paramView.postInvalidate(paramInt1, paramInt2, paramInt3, paramInt4);
    }
  }
  
  public static void postOnAnimation(View paramView, Runnable paramRunnable)
  {
    if (Build.VERSION.SDK_INT >= 16) {
      Api16Impl.postOnAnimation(paramView, paramRunnable);
    } else {
      paramView.postDelayed(paramRunnable, ValueAnimator.getFrameDelay());
    }
  }
  
  public static void postOnAnimationDelayed(View paramView, Runnable paramRunnable, long paramLong)
  {
    if (Build.VERSION.SDK_INT >= 16) {
      Api16Impl.postOnAnimationDelayed(paramView, paramRunnable, paramLong);
    } else {
      paramView.postDelayed(paramRunnable, ValueAnimator.getFrameDelay() + paramLong);
    }
  }
  
  public static void removeAccessibilityAction(View paramView, int paramInt)
  {
    if (Build.VERSION.SDK_INT >= 21)
    {
      removeActionWithId(paramInt, paramView);
      notifyViewAccessibilityStateChangedIfNeeded(paramView, 0);
    }
  }
  
  private static void removeActionWithId(int paramInt, View paramView)
  {
    paramView = getActionList(paramView);
    for (int i = 0; i < paramView.size(); i++) {
      if (((AccessibilityNodeInfoCompat.AccessibilityActionCompat)paramView.get(i)).getId() == paramInt)
      {
        paramView.remove(i);
        break;
      }
    }
  }
  
  public static void removeOnUnhandledKeyEventListener(View paramView, OnUnhandledKeyEventListenerCompat paramOnUnhandledKeyEventListenerCompat)
  {
    if (Build.VERSION.SDK_INT >= 28)
    {
      Api28Impl.removeOnUnhandledKeyEventListener(paramView, paramOnUnhandledKeyEventListenerCompat);
      return;
    }
    ArrayList localArrayList = (ArrayList)paramView.getTag(R.id.tag_unhandled_key_listeners);
    if (localArrayList != null)
    {
      localArrayList.remove(paramOnUnhandledKeyEventListenerCompat);
      if (localArrayList.size() == 0) {
        UnhandledKeyEventManager.unregisterListeningView(paramView);
      }
    }
  }
  
  public static void replaceAccessibilityAction(View paramView, AccessibilityNodeInfoCompat.AccessibilityActionCompat paramAccessibilityActionCompat, CharSequence paramCharSequence, AccessibilityViewCommand paramAccessibilityViewCommand)
  {
    if ((paramAccessibilityViewCommand == null) && (paramCharSequence == null)) {
      removeAccessibilityAction(paramView, paramAccessibilityActionCompat.getId());
    } else {
      addAccessibilityAction(paramView, paramAccessibilityActionCompat.createReplacementAction(paramCharSequence, paramAccessibilityViewCommand));
    }
  }
  
  public static void requestApplyInsets(View paramView)
  {
    if (Build.VERSION.SDK_INT >= 20) {
      Api20Impl.requestApplyInsets(paramView);
    } else if (Build.VERSION.SDK_INT >= 16) {
      Api16Impl.requestFitSystemWindows(paramView);
    }
  }
  
  public static <T extends View> T requireViewById(View paramView, int paramInt)
  {
    if (Build.VERSION.SDK_INT >= 28) {
      return (View)Api28Impl.requireViewById(paramView, paramInt);
    }
    paramView = paramView.findViewById(paramInt);
    if (paramView != null) {
      return paramView;
    }
    throw new IllegalArgumentException("ID does not reference a View inside this View");
  }
  
  @Deprecated
  public static int resolveSizeAndState(int paramInt1, int paramInt2, int paramInt3)
  {
    return View.resolveSizeAndState(paramInt1, paramInt2, paramInt3);
  }
  
  public static boolean restoreDefaultFocus(View paramView)
  {
    if (Build.VERSION.SDK_INT >= 26) {
      return Api26Impl.restoreDefaultFocus(paramView);
    }
    return paramView.requestFocus();
  }
  
  public static void saveAttributeDataForStyleable(View paramView, Context paramContext, int[] paramArrayOfInt, AttributeSet paramAttributeSet, TypedArray paramTypedArray, int paramInt1, int paramInt2)
  {
    if (Build.VERSION.SDK_INT >= 29) {
      Api29Impl.saveAttributeDataForStyleable(paramView, paramContext, paramArrayOfInt, paramAttributeSet, paramTypedArray, paramInt1, paramInt2);
    }
  }
  
  private static AccessibilityViewProperty<Boolean> screenReaderFocusableProperty()
  {
    new AccessibilityViewProperty(R.id.tag_screen_reader_focusable, Boolean.class, 28)
    {
      Boolean frameworkGet(View paramAnonymousView)
      {
        return Boolean.valueOf(ViewCompat.Api28Impl.isScreenReaderFocusable(paramAnonymousView));
      }
      
      void frameworkSet(View paramAnonymousView, Boolean paramAnonymousBoolean)
      {
        ViewCompat.Api28Impl.setScreenReaderFocusable(paramAnonymousView, paramAnonymousBoolean.booleanValue());
      }
      
      boolean shouldUpdate(Boolean paramAnonymousBoolean1, Boolean paramAnonymousBoolean2)
      {
        return booleanNullToFalseEquals(paramAnonymousBoolean1, paramAnonymousBoolean2) ^ true;
      }
    };
  }
  
  public static void setAccessibilityDelegate(View paramView, AccessibilityDelegateCompat paramAccessibilityDelegateCompat)
  {
    AccessibilityDelegateCompat localAccessibilityDelegateCompat = paramAccessibilityDelegateCompat;
    if (paramAccessibilityDelegateCompat == null)
    {
      localAccessibilityDelegateCompat = paramAccessibilityDelegateCompat;
      if ((getAccessibilityDelegateInternal(paramView) instanceof AccessibilityDelegateCompat.AccessibilityDelegateAdapter)) {
        localAccessibilityDelegateCompat = new AccessibilityDelegateCompat();
      }
    }
    if (localAccessibilityDelegateCompat == null) {
      paramAccessibilityDelegateCompat = null;
    } else {
      paramAccessibilityDelegateCompat = localAccessibilityDelegateCompat.getBridge();
    }
    paramView.setAccessibilityDelegate(paramAccessibilityDelegateCompat);
  }
  
  public static void setAccessibilityHeading(View paramView, boolean paramBoolean)
  {
    accessibilityHeadingProperty().set(paramView, Boolean.valueOf(paramBoolean));
  }
  
  public static void setAccessibilityLiveRegion(View paramView, int paramInt)
  {
    if (Build.VERSION.SDK_INT >= 19) {
      Api19Impl.setAccessibilityLiveRegion(paramView, paramInt);
    }
  }
  
  public static void setAccessibilityPaneTitle(View paramView, CharSequence paramCharSequence)
  {
    if (Build.VERSION.SDK_INT >= 19)
    {
      paneTitleProperty().set(paramView, paramCharSequence);
      if (paramCharSequence != null) {
        sAccessibilityPaneVisibilityManager.addAccessibilityPane(paramView);
      } else {
        sAccessibilityPaneVisibilityManager.removeAccessibilityPane(paramView);
      }
    }
  }
  
  @Deprecated
  public static void setActivated(View paramView, boolean paramBoolean)
  {
    paramView.setActivated(paramBoolean);
  }
  
  @Deprecated
  public static void setAlpha(View paramView, float paramFloat)
  {
    paramView.setAlpha(paramFloat);
  }
  
  public static void setAutofillHints(View paramView, String... paramVarArgs)
  {
    if (Build.VERSION.SDK_INT >= 26) {
      Api26Impl.setAutofillHints(paramView, paramVarArgs);
    }
  }
  
  public static void setBackground(View paramView, Drawable paramDrawable)
  {
    if (Build.VERSION.SDK_INT >= 16) {
      Api16Impl.setBackground(paramView, paramDrawable);
    } else {
      paramView.setBackgroundDrawable(paramDrawable);
    }
  }
  
  public static void setBackgroundTintList(View paramView, ColorStateList paramColorStateList)
  {
    if (Build.VERSION.SDK_INT >= 21)
    {
      Api21Impl.setBackgroundTintList(paramView, paramColorStateList);
      if (Build.VERSION.SDK_INT == 21)
      {
        paramColorStateList = paramView.getBackground();
        int i;
        if ((Api21Impl.getBackgroundTintList(paramView) == null) && (Api21Impl.getBackgroundTintMode(paramView) == null)) {
          i = 0;
        } else {
          i = 1;
        }
        if ((paramColorStateList != null) && (i != 0))
        {
          if (paramColorStateList.isStateful()) {
            paramColorStateList.setState(paramView.getDrawableState());
          }
          Api16Impl.setBackground(paramView, paramColorStateList);
        }
      }
    }
    else if ((paramView instanceof TintableBackgroundView))
    {
      ((TintableBackgroundView)paramView).setSupportBackgroundTintList(paramColorStateList);
    }
  }
  
  public static void setBackgroundTintMode(View paramView, PorterDuff.Mode paramMode)
  {
    if (Build.VERSION.SDK_INT >= 21)
    {
      Api21Impl.setBackgroundTintMode(paramView, paramMode);
      if (Build.VERSION.SDK_INT == 21)
      {
        paramMode = paramView.getBackground();
        int i;
        if ((Api21Impl.getBackgroundTintList(paramView) == null) && (Api21Impl.getBackgroundTintMode(paramView) == null)) {
          i = 0;
        } else {
          i = 1;
        }
        if ((paramMode != null) && (i != 0))
        {
          if (paramMode.isStateful()) {
            paramMode.setState(paramView.getDrawableState());
          }
          Api16Impl.setBackground(paramView, paramMode);
        }
      }
    }
    else if ((paramView instanceof TintableBackgroundView))
    {
      ((TintableBackgroundView)paramView).setSupportBackgroundTintMode(paramMode);
    }
  }
  
  @Deprecated
  public static void setChildrenDrawingOrderEnabled(ViewGroup paramViewGroup, boolean paramBoolean)
  {
    if (sChildrenDrawingOrderMethod == null)
    {
      try
      {
        sChildrenDrawingOrderMethod = ViewGroup.class.getDeclaredMethod("setChildrenDrawingOrderEnabled", new Class[] { Boolean.TYPE });
      }
      catch (NoSuchMethodException localNoSuchMethodException)
      {
        Log.e("ViewCompat", "Unable to find childrenDrawingOrderEnabled", localNoSuchMethodException);
      }
      sChildrenDrawingOrderMethod.setAccessible(true);
    }
    try
    {
      sChildrenDrawingOrderMethod.invoke(paramViewGroup, new Object[] { Boolean.valueOf(paramBoolean) });
    }
    catch (InvocationTargetException paramViewGroup)
    {
      Log.e("ViewCompat", "Unable to invoke childrenDrawingOrderEnabled", paramViewGroup);
    }
    catch (IllegalArgumentException paramViewGroup)
    {
      Log.e("ViewCompat", "Unable to invoke childrenDrawingOrderEnabled", paramViewGroup);
    }
    catch (IllegalAccessException paramViewGroup)
    {
      Log.e("ViewCompat", "Unable to invoke childrenDrawingOrderEnabled", paramViewGroup);
    }
  }
  
  public static void setClipBounds(View paramView, Rect paramRect)
  {
    if (Build.VERSION.SDK_INT >= 18) {
      Api18Impl.setClipBounds(paramView, paramRect);
    }
  }
  
  public static void setElevation(View paramView, float paramFloat)
  {
    if (Build.VERSION.SDK_INT >= 21) {
      Api21Impl.setElevation(paramView, paramFloat);
    }
  }
  
  @Deprecated
  public static void setFitsSystemWindows(View paramView, boolean paramBoolean)
  {
    paramView.setFitsSystemWindows(paramBoolean);
  }
  
  public static void setFocusedByDefault(View paramView, boolean paramBoolean)
  {
    if (Build.VERSION.SDK_INT >= 26) {
      Api26Impl.setFocusedByDefault(paramView, paramBoolean);
    }
  }
  
  public static void setHasTransientState(View paramView, boolean paramBoolean)
  {
    if (Build.VERSION.SDK_INT >= 16) {
      Api16Impl.setHasTransientState(paramView, paramBoolean);
    }
  }
  
  public static void setImportantForAccessibility(View paramView, int paramInt)
  {
    if (Build.VERSION.SDK_INT >= 19)
    {
      Api16Impl.setImportantForAccessibility(paramView, paramInt);
    }
    else if (Build.VERSION.SDK_INT >= 16)
    {
      int i = paramInt;
      if (paramInt == 4) {
        i = 2;
      }
      Api16Impl.setImportantForAccessibility(paramView, i);
    }
  }
  
  public static void setImportantForAutofill(View paramView, int paramInt)
  {
    if (Build.VERSION.SDK_INT >= 26) {
      Api26Impl.setImportantForAutofill(paramView, paramInt);
    }
  }
  
  public static void setKeyboardNavigationCluster(View paramView, boolean paramBoolean)
  {
    if (Build.VERSION.SDK_INT >= 26) {
      Api26Impl.setKeyboardNavigationCluster(paramView, paramBoolean);
    }
  }
  
  public static void setLabelFor(View paramView, int paramInt)
  {
    if (Build.VERSION.SDK_INT >= 17) {
      Api17Impl.setLabelFor(paramView, paramInt);
    }
  }
  
  public static void setLayerPaint(View paramView, Paint paramPaint)
  {
    if (Build.VERSION.SDK_INT >= 17)
    {
      Api17Impl.setLayerPaint(paramView, paramPaint);
    }
    else
    {
      paramView.setLayerType(paramView.getLayerType(), paramPaint);
      paramView.invalidate();
    }
  }
  
  @Deprecated
  public static void setLayerType(View paramView, int paramInt, Paint paramPaint)
  {
    paramView.setLayerType(paramInt, paramPaint);
  }
  
  public static void setLayoutDirection(View paramView, int paramInt)
  {
    if (Build.VERSION.SDK_INT >= 17) {
      Api17Impl.setLayoutDirection(paramView, paramInt);
    }
  }
  
  public static void setNestedScrollingEnabled(View paramView, boolean paramBoolean)
  {
    if (Build.VERSION.SDK_INT >= 21) {
      Api21Impl.setNestedScrollingEnabled(paramView, paramBoolean);
    } else if ((paramView instanceof NestedScrollingChild)) {
      ((NestedScrollingChild)paramView).setNestedScrollingEnabled(paramBoolean);
    }
  }
  
  public static void setNextClusterForwardId(View paramView, int paramInt)
  {
    if (Build.VERSION.SDK_INT >= 26) {
      Api26Impl.setNextClusterForwardId(paramView, paramInt);
    }
  }
  
  public static void setOnApplyWindowInsetsListener(View paramView, OnApplyWindowInsetsListener paramOnApplyWindowInsetsListener)
  {
    if (Build.VERSION.SDK_INT >= 21) {
      Api21Impl.setOnApplyWindowInsetsListener(paramView, paramOnApplyWindowInsetsListener);
    }
  }
  
  public static void setOnReceiveContentListener(View paramView, String[] paramArrayOfString, OnReceiveContentListener paramOnReceiveContentListener)
  {
    if (Build.VERSION.SDK_INT >= 31)
    {
      Api31Impl.setOnReceiveContentListener(paramView, paramArrayOfString, paramOnReceiveContentListener);
      return;
    }
    if ((paramArrayOfString != null) && (paramArrayOfString.length != 0)) {
      break label32;
    }
    paramArrayOfString = null;
    label32:
    int i = 0;
    if (paramOnReceiveContentListener != null)
    {
      boolean bool;
      if (paramArrayOfString != null) {
        bool = true;
      } else {
        bool = false;
      }
      Preconditions.checkArgument(bool, "When the listener is set, MIME types must also be set");
    }
    if (paramArrayOfString != null)
    {
      int k = 0;
      int m = paramArrayOfString.length;
      int j;
      for (;;)
      {
        j = k;
        if (i >= m) {
          break;
        }
        if (paramArrayOfString[i].startsWith("*"))
        {
          j = 1;
          break;
        }
        i++;
      }
      StringBuilder localStringBuilder = new StringBuilder().append("A MIME type set here must not start with *: ");
      String str = Arrays.toString(paramArrayOfString);
      Log5ECF72.a(str);
      LogE84000.a(str);
      Log229316.a(str);
      Preconditions.checkArgument(j ^ 0x1, str);
    }
    paramView.setTag(R.id.tag_on_receive_content_mime_types, paramArrayOfString);
    paramView.setTag(R.id.tag_on_receive_content_listener, paramOnReceiveContentListener);
  }
  
  @Deprecated
  public static void setOverScrollMode(View paramView, int paramInt)
  {
    paramView.setOverScrollMode(paramInt);
  }
  
  public static void setPaddingRelative(View paramView, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    if (Build.VERSION.SDK_INT >= 17) {
      Api17Impl.setPaddingRelative(paramView, paramInt1, paramInt2, paramInt3, paramInt4);
    } else {
      paramView.setPadding(paramInt1, paramInt2, paramInt3, paramInt4);
    }
  }
  
  @Deprecated
  public static void setPivotX(View paramView, float paramFloat)
  {
    paramView.setPivotX(paramFloat);
  }
  
  @Deprecated
  public static void setPivotY(View paramView, float paramFloat)
  {
    paramView.setPivotY(paramFloat);
  }
  
  public static void setPointerIcon(View paramView, PointerIconCompat paramPointerIconCompat)
  {
    if (Build.VERSION.SDK_INT >= 24)
    {
      if (paramPointerIconCompat != null) {
        paramPointerIconCompat = paramPointerIconCompat.getPointerIcon();
      } else {
        paramPointerIconCompat = null;
      }
      Api24Impl.setPointerIcon(paramView, (PointerIcon)paramPointerIconCompat);
    }
  }
  
  @Deprecated
  public static void setRotation(View paramView, float paramFloat)
  {
    paramView.setRotation(paramFloat);
  }
  
  @Deprecated
  public static void setRotationX(View paramView, float paramFloat)
  {
    paramView.setRotationX(paramFloat);
  }
  
  @Deprecated
  public static void setRotationY(View paramView, float paramFloat)
  {
    paramView.setRotationY(paramFloat);
  }
  
  @Deprecated
  public static void setSaveFromParentEnabled(View paramView, boolean paramBoolean)
  {
    paramView.setSaveFromParentEnabled(paramBoolean);
  }
  
  @Deprecated
  public static void setScaleX(View paramView, float paramFloat)
  {
    paramView.setScaleX(paramFloat);
  }
  
  @Deprecated
  public static void setScaleY(View paramView, float paramFloat)
  {
    paramView.setScaleY(paramFloat);
  }
  
  public static void setScreenReaderFocusable(View paramView, boolean paramBoolean)
  {
    screenReaderFocusableProperty().set(paramView, Boolean.valueOf(paramBoolean));
  }
  
  public static void setScrollIndicators(View paramView, int paramInt)
  {
    if (Build.VERSION.SDK_INT >= 23) {
      Api23Impl.setScrollIndicators(paramView, paramInt);
    }
  }
  
  public static void setScrollIndicators(View paramView, int paramInt1, int paramInt2)
  {
    if (Build.VERSION.SDK_INT >= 23) {
      Api23Impl.setScrollIndicators(paramView, paramInt1, paramInt2);
    }
  }
  
  public static void setStateDescription(View paramView, CharSequence paramCharSequence)
  {
    if (Build.VERSION.SDK_INT >= 19) {
      stateDescriptionProperty().set(paramView, paramCharSequence);
    }
  }
  
  public static void setSystemGestureExclusionRects(View paramView, List<Rect> paramList)
  {
    if (Build.VERSION.SDK_INT >= 29) {
      Api29Impl.setSystemGestureExclusionRects(paramView, paramList);
    }
  }
  
  public static void setTooltipText(View paramView, CharSequence paramCharSequence)
  {
    if (Build.VERSION.SDK_INT >= 26) {
      Api26Impl.setTooltipText(paramView, paramCharSequence);
    }
  }
  
  public static void setTransitionName(View paramView, String paramString)
  {
    if (Build.VERSION.SDK_INT >= 21)
    {
      Api21Impl.setTransitionName(paramView, paramString);
    }
    else
    {
      if (sTransitionNameMap == null) {
        sTransitionNameMap = new WeakHashMap();
      }
      sTransitionNameMap.put(paramView, paramString);
    }
  }
  
  @Deprecated
  public static void setTranslationX(View paramView, float paramFloat)
  {
    paramView.setTranslationX(paramFloat);
  }
  
  @Deprecated
  public static void setTranslationY(View paramView, float paramFloat)
  {
    paramView.setTranslationY(paramFloat);
  }
  
  public static void setTranslationZ(View paramView, float paramFloat)
  {
    if (Build.VERSION.SDK_INT >= 21) {
      Api21Impl.setTranslationZ(paramView, paramFloat);
    }
  }
  
  private static void setViewImportanceForAccessibilityIfNeeded(View paramView)
  {
    if (getImportantForAccessibility(paramView) == 0) {
      setImportantForAccessibility(paramView, 1);
    }
    for (ViewParent localViewParent = paramView.getParent(); (localViewParent instanceof View); localViewParent = localViewParent.getParent()) {
      if (getImportantForAccessibility((View)localViewParent) == 4)
      {
        setImportantForAccessibility(paramView, 2);
        break;
      }
    }
  }
  
  public static void setWindowInsetsAnimationCallback(View paramView, WindowInsetsAnimationCompat.Callback paramCallback)
  {
    WindowInsetsAnimationCompat.setCallback(paramView, paramCallback);
  }
  
  @Deprecated
  public static void setX(View paramView, float paramFloat)
  {
    paramView.setX(paramFloat);
  }
  
  @Deprecated
  public static void setY(View paramView, float paramFloat)
  {
    paramView.setY(paramFloat);
  }
  
  public static void setZ(View paramView, float paramFloat)
  {
    if (Build.VERSION.SDK_INT >= 21) {
      Api21Impl.setZ(paramView, paramFloat);
    }
  }
  
  public static boolean startDragAndDrop(View paramView, ClipData paramClipData, View.DragShadowBuilder paramDragShadowBuilder, Object paramObject, int paramInt)
  {
    if (Build.VERSION.SDK_INT >= 24) {
      return Api24Impl.startDragAndDrop(paramView, paramClipData, paramDragShadowBuilder, paramObject, paramInt);
    }
    return paramView.startDrag(paramClipData, paramDragShadowBuilder, paramObject, paramInt);
  }
  
  public static boolean startNestedScroll(View paramView, int paramInt)
  {
    if (Build.VERSION.SDK_INT >= 21) {
      return Api21Impl.startNestedScroll(paramView, paramInt);
    }
    if ((paramView instanceof NestedScrollingChild)) {
      return ((NestedScrollingChild)paramView).startNestedScroll(paramInt);
    }
    return false;
  }
  
  public static boolean startNestedScroll(View paramView, int paramInt1, int paramInt2)
  {
    if ((paramView instanceof NestedScrollingChild2)) {
      return ((NestedScrollingChild2)paramView).startNestedScroll(paramInt1, paramInt2);
    }
    if (paramInt2 == 0) {
      return startNestedScroll(paramView, paramInt1);
    }
    return false;
  }
  
  private static AccessibilityViewProperty<CharSequence> stateDescriptionProperty()
  {
    new AccessibilityViewProperty(R.id.tag_state_description, CharSequence.class, 64, 30)
    {
      CharSequence frameworkGet(View paramAnonymousView)
      {
        return ViewCompat.Api30Impl.getStateDescription(paramAnonymousView);
      }
      
      void frameworkSet(View paramAnonymousView, CharSequence paramAnonymousCharSequence)
      {
        ViewCompat.Api30Impl.setStateDescription(paramAnonymousView, paramAnonymousCharSequence);
      }
      
      boolean shouldUpdate(CharSequence paramAnonymousCharSequence1, CharSequence paramAnonymousCharSequence2)
      {
        return TextUtils.equals(paramAnonymousCharSequence1, paramAnonymousCharSequence2) ^ true;
      }
    };
  }
  
  public static void stopNestedScroll(View paramView)
  {
    if (Build.VERSION.SDK_INT >= 21) {
      Api21Impl.stopNestedScroll(paramView);
    } else if ((paramView instanceof NestedScrollingChild)) {
      ((NestedScrollingChild)paramView).stopNestedScroll();
    }
  }
  
  public static void stopNestedScroll(View paramView, int paramInt)
  {
    if ((paramView instanceof NestedScrollingChild2)) {
      ((NestedScrollingChild2)paramView).stopNestedScroll(paramInt);
    } else if (paramInt == 0) {
      stopNestedScroll(paramView);
    }
  }
  
  private static void tickleInvalidationFlag(View paramView)
  {
    float f = paramView.getTranslationY();
    paramView.setTranslationY(1.0F + f);
    paramView.setTranslationY(f);
  }
  
  public static void updateDragShadow(View paramView, View.DragShadowBuilder paramDragShadowBuilder)
  {
    if (Build.VERSION.SDK_INT >= 24) {
      Api24Impl.updateDragShadow(paramView, paramDragShadowBuilder);
    }
  }
  
  static class AccessibilityPaneVisibilityManager
    implements ViewTreeObserver.OnGlobalLayoutListener, View.OnAttachStateChangeListener
  {
    private final WeakHashMap<View, Boolean> mPanesToVisible = new WeakHashMap();
    
    private void checkPaneVisibility(View paramView, boolean paramBoolean)
    {
      boolean bool;
      if ((paramView.isShown()) && (paramView.getWindowVisibility() == 0)) {
        bool = true;
      } else {
        bool = false;
      }
      if (paramBoolean != bool)
      {
        int i;
        if (bool) {
          i = 16;
        } else {
          i = 32;
        }
        ViewCompat.notifyViewAccessibilityStateChangedIfNeeded(paramView, i);
        this.mPanesToVisible.put(paramView, Boolean.valueOf(bool));
      }
    }
    
    private void registerForLayoutCallback(View paramView)
    {
      paramView.getViewTreeObserver().addOnGlobalLayoutListener(this);
    }
    
    private void unregisterForLayoutCallback(View paramView)
    {
      ViewCompat.Api16Impl.removeOnGlobalLayoutListener(paramView.getViewTreeObserver(), this);
    }
    
    void addAccessibilityPane(View paramView)
    {
      WeakHashMap localWeakHashMap = this.mPanesToVisible;
      boolean bool;
      if ((paramView.isShown()) && (paramView.getWindowVisibility() == 0)) {
        bool = true;
      } else {
        bool = false;
      }
      localWeakHashMap.put(paramView, Boolean.valueOf(bool));
      paramView.addOnAttachStateChangeListener(this);
      if (ViewCompat.Api19Impl.isAttachedToWindow(paramView)) {
        registerForLayoutCallback(paramView);
      }
    }
    
    public void onGlobalLayout()
    {
      if (Build.VERSION.SDK_INT < 28)
      {
        Iterator localIterator = this.mPanesToVisible.entrySet().iterator();
        while (localIterator.hasNext())
        {
          Map.Entry localEntry = (Map.Entry)localIterator.next();
          checkPaneVisibility((View)localEntry.getKey(), ((Boolean)localEntry.getValue()).booleanValue());
        }
      }
    }
    
    public void onViewAttachedToWindow(View paramView)
    {
      registerForLayoutCallback(paramView);
    }
    
    public void onViewDetachedFromWindow(View paramView) {}
    
    void removeAccessibilityPane(View paramView)
    {
      this.mPanesToVisible.remove(paramView);
      paramView.removeOnAttachStateChangeListener(this);
      unregisterForLayoutCallback(paramView);
    }
  }
  
  static abstract class AccessibilityViewProperty<T>
  {
    private final int mContentChangeType;
    private final int mFrameworkMinimumSdk;
    private final int mTagKey;
    private final Class<T> mType;
    
    AccessibilityViewProperty(int paramInt1, Class<T> paramClass, int paramInt2)
    {
      this(paramInt1, paramClass, 0, paramInt2);
    }
    
    AccessibilityViewProperty(int paramInt1, Class<T> paramClass, int paramInt2, int paramInt3)
    {
      this.mTagKey = paramInt1;
      this.mType = paramClass;
      this.mContentChangeType = paramInt2;
      this.mFrameworkMinimumSdk = paramInt3;
    }
    
    private boolean extrasAvailable()
    {
      boolean bool;
      if (Build.VERSION.SDK_INT >= 19) {
        bool = true;
      } else {
        bool = false;
      }
      return bool;
    }
    
    private boolean frameworkAvailable()
    {
      boolean bool;
      if (Build.VERSION.SDK_INT >= this.mFrameworkMinimumSdk) {
        bool = true;
      } else {
        bool = false;
      }
      return bool;
    }
    
    boolean booleanNullToFalseEquals(Boolean paramBoolean1, Boolean paramBoolean2)
    {
      boolean bool = true;
      int i;
      if ((paramBoolean1 != null) && (paramBoolean1.booleanValue())) {
        i = 1;
      } else {
        i = 0;
      }
      int j;
      if ((paramBoolean2 != null) && (paramBoolean2.booleanValue())) {
        j = 1;
      } else {
        j = 0;
      }
      if (i != j) {
        bool = false;
      }
      return bool;
    }
    
    abstract T frameworkGet(View paramView);
    
    abstract void frameworkSet(View paramView, T paramT);
    
    T get(View paramView)
    {
      if (frameworkAvailable()) {
        return (T)frameworkGet(paramView);
      }
      if (extrasAvailable())
      {
        paramView = paramView.getTag(this.mTagKey);
        if (this.mType.isInstance(paramView)) {
          return paramView;
        }
      }
      return null;
    }
    
    void set(View paramView, T paramT)
    {
      if (frameworkAvailable())
      {
        frameworkSet(paramView, paramT);
      }
      else if ((extrasAvailable()) && (shouldUpdate(get(paramView), paramT)))
      {
        ViewCompat.ensureAccessibilityDelegateCompat(paramView);
        paramView.setTag(this.mTagKey, paramT);
        ViewCompat.notifyViewAccessibilityStateChangedIfNeeded(paramView, this.mContentChangeType);
      }
    }
    
    boolean shouldUpdate(T paramT1, T paramT2)
    {
      return paramT2.equals(paramT1) ^ true;
    }
  }
  
  static class Api15Impl
  {
    static boolean hasOnClickListeners(View paramView)
    {
      return paramView.hasOnClickListeners();
    }
  }
  
  static class Api16Impl
  {
    static AccessibilityNodeProvider getAccessibilityNodeProvider(View paramView)
    {
      return paramView.getAccessibilityNodeProvider();
    }
    
    static boolean getFitsSystemWindows(View paramView)
    {
      return paramView.getFitsSystemWindows();
    }
    
    static int getImportantForAccessibility(View paramView)
    {
      return paramView.getImportantForAccessibility();
    }
    
    static int getMinimumHeight(View paramView)
    {
      return paramView.getMinimumHeight();
    }
    
    static int getMinimumWidth(View paramView)
    {
      return paramView.getMinimumWidth();
    }
    
    static ViewParent getParentForAccessibility(View paramView)
    {
      return paramView.getParentForAccessibility();
    }
    
    static int getWindowSystemUiVisibility(View paramView)
    {
      return paramView.getWindowSystemUiVisibility();
    }
    
    static boolean hasOverlappingRendering(View paramView)
    {
      return paramView.hasOverlappingRendering();
    }
    
    static boolean hasTransientState(View paramView)
    {
      return paramView.hasTransientState();
    }
    
    static boolean performAccessibilityAction(View paramView, int paramInt, Bundle paramBundle)
    {
      return paramView.performAccessibilityAction(paramInt, paramBundle);
    }
    
    static void postInvalidateOnAnimation(View paramView)
    {
      paramView.postInvalidateOnAnimation();
    }
    
    static void postInvalidateOnAnimation(View paramView, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
    {
      paramView.postInvalidateOnAnimation(paramInt1, paramInt2, paramInt3, paramInt4);
    }
    
    static void postOnAnimation(View paramView, Runnable paramRunnable)
    {
      paramView.postOnAnimation(paramRunnable);
    }
    
    static void postOnAnimationDelayed(View paramView, Runnable paramRunnable, long paramLong)
    {
      paramView.postOnAnimationDelayed(paramRunnable, paramLong);
    }
    
    static void removeOnGlobalLayoutListener(ViewTreeObserver paramViewTreeObserver, ViewTreeObserver.OnGlobalLayoutListener paramOnGlobalLayoutListener)
    {
      paramViewTreeObserver.removeOnGlobalLayoutListener(paramOnGlobalLayoutListener);
    }
    
    static void requestFitSystemWindows(View paramView)
    {
      paramView.requestFitSystemWindows();
    }
    
    static void setBackground(View paramView, Drawable paramDrawable)
    {
      paramView.setBackground(paramDrawable);
    }
    
    static void setHasTransientState(View paramView, boolean paramBoolean)
    {
      paramView.setHasTransientState(paramBoolean);
    }
    
    static void setImportantForAccessibility(View paramView, int paramInt)
    {
      paramView.setImportantForAccessibility(paramInt);
    }
  }
  
  static class Api17Impl
  {
    static int generateViewId()
    {
      return View.generateViewId();
    }
    
    static Display getDisplay(View paramView)
    {
      return paramView.getDisplay();
    }
    
    static int getLabelFor(View paramView)
    {
      return paramView.getLabelFor();
    }
    
    static int getLayoutDirection(View paramView)
    {
      return paramView.getLayoutDirection();
    }
    
    static int getPaddingEnd(View paramView)
    {
      return paramView.getPaddingEnd();
    }
    
    static int getPaddingStart(View paramView)
    {
      return paramView.getPaddingStart();
    }
    
    static boolean isPaddingRelative(View paramView)
    {
      return paramView.isPaddingRelative();
    }
    
    static void setLabelFor(View paramView, int paramInt)
    {
      paramView.setLabelFor(paramInt);
    }
    
    static void setLayerPaint(View paramView, Paint paramPaint)
    {
      paramView.setLayerPaint(paramPaint);
    }
    
    static void setLayoutDirection(View paramView, int paramInt)
    {
      paramView.setLayoutDirection(paramInt);
    }
    
    static void setPaddingRelative(View paramView, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
    {
      paramView.setPaddingRelative(paramInt1, paramInt2, paramInt3, paramInt4);
    }
  }
  
  static class Api18Impl
  {
    static Rect getClipBounds(View paramView)
    {
      return paramView.getClipBounds();
    }
    
    static boolean isInLayout(View paramView)
    {
      return paramView.isInLayout();
    }
    
    static void setClipBounds(View paramView, Rect paramRect)
    {
      paramView.setClipBounds(paramRect);
    }
  }
  
  static class Api19Impl
  {
    static int getAccessibilityLiveRegion(View paramView)
    {
      return paramView.getAccessibilityLiveRegion();
    }
    
    static boolean isAttachedToWindow(View paramView)
    {
      return paramView.isAttachedToWindow();
    }
    
    static boolean isLaidOut(View paramView)
    {
      return paramView.isLaidOut();
    }
    
    static boolean isLayoutDirectionResolved(View paramView)
    {
      return paramView.isLayoutDirectionResolved();
    }
    
    static void notifySubtreeAccessibilityStateChanged(ViewParent paramViewParent, View paramView1, View paramView2, int paramInt)
    {
      paramViewParent.notifySubtreeAccessibilityStateChanged(paramView1, paramView2, paramInt);
    }
    
    static void setAccessibilityLiveRegion(View paramView, int paramInt)
    {
      paramView.setAccessibilityLiveRegion(paramInt);
    }
    
    static void setContentChangeTypes(AccessibilityEvent paramAccessibilityEvent, int paramInt)
    {
      paramAccessibilityEvent.setContentChangeTypes(paramInt);
    }
  }
  
  static class Api20Impl
  {
    static WindowInsets dispatchApplyWindowInsets(View paramView, WindowInsets paramWindowInsets)
    {
      return paramView.dispatchApplyWindowInsets(paramWindowInsets);
    }
    
    static WindowInsets onApplyWindowInsets(View paramView, WindowInsets paramWindowInsets)
    {
      return paramView.onApplyWindowInsets(paramWindowInsets);
    }
    
    static void requestApplyInsets(View paramView)
    {
      paramView.requestApplyInsets();
    }
  }
  
  private static class Api21Impl
  {
    static void callCompatInsetAnimationCallback(WindowInsets paramWindowInsets, View paramView)
    {
      View.OnApplyWindowInsetsListener localOnApplyWindowInsetsListener = (View.OnApplyWindowInsetsListener)paramView.getTag(R.id.tag_window_insets_animation_callback);
      if (localOnApplyWindowInsetsListener != null) {
        localOnApplyWindowInsetsListener.onApplyWindowInsets(paramView, paramWindowInsets);
      }
    }
    
    static WindowInsetsCompat computeSystemWindowInsets(View paramView, WindowInsetsCompat paramWindowInsetsCompat, Rect paramRect)
    {
      WindowInsets localWindowInsets = paramWindowInsetsCompat.toWindowInsets();
      if (localWindowInsets != null) {
        return WindowInsetsCompat.toWindowInsetsCompat(paramView.computeSystemWindowInsets(localWindowInsets, paramRect), paramView);
      }
      paramRect.setEmpty();
      return paramWindowInsetsCompat;
    }
    
    static boolean dispatchNestedFling(View paramView, float paramFloat1, float paramFloat2, boolean paramBoolean)
    {
      return paramView.dispatchNestedFling(paramFloat1, paramFloat2, paramBoolean);
    }
    
    static boolean dispatchNestedPreFling(View paramView, float paramFloat1, float paramFloat2)
    {
      return paramView.dispatchNestedPreFling(paramFloat1, paramFloat2);
    }
    
    static boolean dispatchNestedPreScroll(View paramView, int paramInt1, int paramInt2, int[] paramArrayOfInt1, int[] paramArrayOfInt2)
    {
      return paramView.dispatchNestedPreScroll(paramInt1, paramInt2, paramArrayOfInt1, paramArrayOfInt2);
    }
    
    static boolean dispatchNestedScroll(View paramView, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int[] paramArrayOfInt)
    {
      return paramView.dispatchNestedScroll(paramInt1, paramInt2, paramInt3, paramInt4, paramArrayOfInt);
    }
    
    static ColorStateList getBackgroundTintList(View paramView)
    {
      return paramView.getBackgroundTintList();
    }
    
    static PorterDuff.Mode getBackgroundTintMode(View paramView)
    {
      return paramView.getBackgroundTintMode();
    }
    
    static float getElevation(View paramView)
    {
      return paramView.getElevation();
    }
    
    public static WindowInsetsCompat getRootWindowInsets(View paramView)
    {
      return WindowInsetsCompat.Api21ReflectionHolder.getRootWindowInsets(paramView);
    }
    
    static String getTransitionName(View paramView)
    {
      return paramView.getTransitionName();
    }
    
    static float getTranslationZ(View paramView)
    {
      return paramView.getTranslationZ();
    }
    
    static float getZ(View paramView)
    {
      return paramView.getZ();
    }
    
    static boolean hasNestedScrollingParent(View paramView)
    {
      return paramView.hasNestedScrollingParent();
    }
    
    static boolean isImportantForAccessibility(View paramView)
    {
      return paramView.isImportantForAccessibility();
    }
    
    static boolean isNestedScrollingEnabled(View paramView)
    {
      return paramView.isNestedScrollingEnabled();
    }
    
    static void setBackgroundTintList(View paramView, ColorStateList paramColorStateList)
    {
      paramView.setBackgroundTintList(paramColorStateList);
    }
    
    static void setBackgroundTintMode(View paramView, PorterDuff.Mode paramMode)
    {
      paramView.setBackgroundTintMode(paramMode);
    }
    
    static void setElevation(View paramView, float paramFloat)
    {
      paramView.setElevation(paramFloat);
    }
    
    static void setNestedScrollingEnabled(View paramView, boolean paramBoolean)
    {
      paramView.setNestedScrollingEnabled(paramBoolean);
    }
    
    static void setOnApplyWindowInsetsListener(View paramView, final OnApplyWindowInsetsListener paramOnApplyWindowInsetsListener)
    {
      if (Build.VERSION.SDK_INT < 30) {
        paramView.setTag(R.id.tag_on_apply_window_listener, paramOnApplyWindowInsetsListener);
      }
      if (paramOnApplyWindowInsetsListener == null)
      {
        paramView.setOnApplyWindowInsetsListener((View.OnApplyWindowInsetsListener)paramView.getTag(R.id.tag_window_insets_animation_callback));
        return;
      }
      paramView.setOnApplyWindowInsetsListener(new View.OnApplyWindowInsetsListener()
      {
        WindowInsetsCompat mLastInsets = null;
        
        public WindowInsets onApplyWindowInsets(View paramAnonymousView, WindowInsets paramAnonymousWindowInsets)
        {
          WindowInsetsCompat localWindowInsetsCompat = WindowInsetsCompat.toWindowInsetsCompat(paramAnonymousWindowInsets, paramAnonymousView);
          if (Build.VERSION.SDK_INT < 30)
          {
            ViewCompat.Api21Impl.callCompatInsetAnimationCallback(paramAnonymousWindowInsets, ViewCompat.Api21Impl.this);
            if (localWindowInsetsCompat.equals(this.mLastInsets)) {
              return paramOnApplyWindowInsetsListener.onApplyWindowInsets(paramAnonymousView, localWindowInsetsCompat).toWindowInsets();
            }
          }
          this.mLastInsets = localWindowInsetsCompat;
          paramAnonymousWindowInsets = paramOnApplyWindowInsetsListener.onApplyWindowInsets(paramAnonymousView, localWindowInsetsCompat);
          if (Build.VERSION.SDK_INT >= 30) {
            return paramAnonymousWindowInsets.toWindowInsets();
          }
          ViewCompat.requestApplyInsets(paramAnonymousView);
          return paramAnonymousWindowInsets.toWindowInsets();
        }
      });
    }
    
    static void setTransitionName(View paramView, String paramString)
    {
      paramView.setTransitionName(paramString);
    }
    
    static void setTranslationZ(View paramView, float paramFloat)
    {
      paramView.setTranslationZ(paramFloat);
    }
    
    static void setZ(View paramView, float paramFloat)
    {
      paramView.setZ(paramFloat);
    }
    
    static boolean startNestedScroll(View paramView, int paramInt)
    {
      return paramView.startNestedScroll(paramInt);
    }
    
    static void stopNestedScroll(View paramView)
    {
      paramView.stopNestedScroll();
    }
  }
  
  private static class Api23Impl
  {
    public static WindowInsetsCompat getRootWindowInsets(View paramView)
    {
      Object localObject = paramView.getRootWindowInsets();
      if (localObject == null) {
        return null;
      }
      localObject = WindowInsetsCompat.toWindowInsetsCompat((WindowInsets)localObject);
      ((WindowInsetsCompat)localObject).setRootWindowInsets((WindowInsetsCompat)localObject);
      ((WindowInsetsCompat)localObject).copyRootViewBounds(paramView.getRootView());
      return (WindowInsetsCompat)localObject;
    }
    
    static int getScrollIndicators(View paramView)
    {
      return paramView.getScrollIndicators();
    }
    
    static void setScrollIndicators(View paramView, int paramInt)
    {
      paramView.setScrollIndicators(paramInt);
    }
    
    static void setScrollIndicators(View paramView, int paramInt1, int paramInt2)
    {
      paramView.setScrollIndicators(paramInt1, paramInt2);
    }
  }
  
  static class Api24Impl
  {
    static void cancelDragAndDrop(View paramView)
    {
      paramView.cancelDragAndDrop();
    }
    
    static void dispatchFinishTemporaryDetach(View paramView)
    {
      paramView.dispatchFinishTemporaryDetach();
    }
    
    static void dispatchStartTemporaryDetach(View paramView)
    {
      paramView.dispatchStartTemporaryDetach();
    }
    
    static void setPointerIcon(View paramView, PointerIcon paramPointerIcon)
    {
      paramView.setPointerIcon(paramPointerIcon);
    }
    
    static boolean startDragAndDrop(View paramView, ClipData paramClipData, View.DragShadowBuilder paramDragShadowBuilder, Object paramObject, int paramInt)
    {
      return paramView.startDragAndDrop(paramClipData, paramDragShadowBuilder, paramObject, paramInt);
    }
    
    static void updateDragShadow(View paramView, View.DragShadowBuilder paramDragShadowBuilder)
    {
      paramView.updateDragShadow(paramDragShadowBuilder);
    }
  }
  
  static class Api26Impl
  {
    static void addKeyboardNavigationClusters(View paramView, Collection<View> paramCollection, int paramInt)
    {
      paramView.addKeyboardNavigationClusters(paramCollection, paramInt);
    }
    
    static int getImportantForAutofill(View paramView)
    {
      return paramView.getImportantForAutofill();
    }
    
    static int getNextClusterForwardId(View paramView)
    {
      return paramView.getNextClusterForwardId();
    }
    
    static boolean hasExplicitFocusable(View paramView)
    {
      return paramView.hasExplicitFocusable();
    }
    
    static boolean isFocusedByDefault(View paramView)
    {
      return paramView.isFocusedByDefault();
    }
    
    static boolean isImportantForAutofill(View paramView)
    {
      return paramView.isImportantForAutofill();
    }
    
    static boolean isKeyboardNavigationCluster(View paramView)
    {
      return paramView.isKeyboardNavigationCluster();
    }
    
    static View keyboardNavigationClusterSearch(View paramView1, View paramView2, int paramInt)
    {
      return paramView1.keyboardNavigationClusterSearch(paramView2, paramInt);
    }
    
    static boolean restoreDefaultFocus(View paramView)
    {
      return paramView.restoreDefaultFocus();
    }
    
    static void setAutofillHints(View paramView, String... paramVarArgs)
    {
      paramView.setAutofillHints(paramVarArgs);
    }
    
    static void setFocusedByDefault(View paramView, boolean paramBoolean)
    {
      paramView.setFocusedByDefault(paramBoolean);
    }
    
    static void setImportantForAutofill(View paramView, int paramInt)
    {
      paramView.setImportantForAutofill(paramInt);
    }
    
    static void setKeyboardNavigationCluster(View paramView, boolean paramBoolean)
    {
      paramView.setKeyboardNavigationCluster(paramBoolean);
    }
    
    static void setNextClusterForwardId(View paramView, int paramInt)
    {
      paramView.setNextClusterForwardId(paramInt);
    }
    
    static void setTooltipText(View paramView, CharSequence paramCharSequence)
    {
      paramView.setTooltipText(paramCharSequence);
    }
  }
  
  static class Api28Impl
  {
    static void addOnUnhandledKeyEventListener(View paramView, ViewCompat.OnUnhandledKeyEventListenerCompat paramOnUnhandledKeyEventListenerCompat)
    {
      Object localObject2 = (SimpleArrayMap)paramView.getTag(R.id.tag_unhandled_key_listeners);
      Object localObject1 = localObject2;
      if (localObject2 == null)
      {
        localObject1 = new SimpleArrayMap();
        paramView.setTag(R.id.tag_unhandled_key_listeners, localObject1);
      }
      Objects.requireNonNull(paramOnUnhandledKeyEventListenerCompat);
      localObject2 = new ViewCompat.Api28Impl..ExternalSyntheticLambda0(paramOnUnhandledKeyEventListenerCompat);
      ((SimpleArrayMap)localObject1).put(paramOnUnhandledKeyEventListenerCompat, localObject2);
      paramView.addOnUnhandledKeyEventListener((View.OnUnhandledKeyEventListener)localObject2);
    }
    
    static CharSequence getAccessibilityPaneTitle(View paramView)
    {
      return paramView.getAccessibilityPaneTitle();
    }
    
    static boolean isAccessibilityHeading(View paramView)
    {
      return paramView.isAccessibilityHeading();
    }
    
    static boolean isScreenReaderFocusable(View paramView)
    {
      return paramView.isScreenReaderFocusable();
    }
    
    static void removeOnUnhandledKeyEventListener(View paramView, ViewCompat.OnUnhandledKeyEventListenerCompat paramOnUnhandledKeyEventListenerCompat)
    {
      SimpleArrayMap localSimpleArrayMap = (SimpleArrayMap)paramView.getTag(R.id.tag_unhandled_key_listeners);
      if (localSimpleArrayMap == null) {
        return;
      }
      paramOnUnhandledKeyEventListenerCompat = (View.OnUnhandledKeyEventListener)localSimpleArrayMap.get(paramOnUnhandledKeyEventListenerCompat);
      if (paramOnUnhandledKeyEventListenerCompat != null) {
        paramView.removeOnUnhandledKeyEventListener(paramOnUnhandledKeyEventListenerCompat);
      }
    }
    
    static <T> T requireViewById(View paramView, int paramInt)
    {
      return paramView.requireViewById(paramInt);
    }
    
    static void setAccessibilityHeading(View paramView, boolean paramBoolean)
    {
      paramView.setAccessibilityHeading(paramBoolean);
    }
    
    static void setAccessibilityPaneTitle(View paramView, CharSequence paramCharSequence)
    {
      paramView.setAccessibilityPaneTitle(paramCharSequence);
    }
    
    static void setScreenReaderFocusable(View paramView, boolean paramBoolean)
    {
      paramView.setScreenReaderFocusable(paramBoolean);
    }
  }
  
  private static class Api29Impl
  {
    static View.AccessibilityDelegate getAccessibilityDelegate(View paramView)
    {
      return paramView.getAccessibilityDelegate();
    }
    
    static List<Rect> getSystemGestureExclusionRects(View paramView)
    {
      return paramView.getSystemGestureExclusionRects();
    }
    
    static void saveAttributeDataForStyleable(View paramView, Context paramContext, int[] paramArrayOfInt, AttributeSet paramAttributeSet, TypedArray paramTypedArray, int paramInt1, int paramInt2)
    {
      paramView.saveAttributeDataForStyleable(paramContext, paramArrayOfInt, paramAttributeSet, paramTypedArray, paramInt1, paramInt2);
    }
    
    static void setSystemGestureExclusionRects(View paramView, List<Rect> paramList)
    {
      paramView.setSystemGestureExclusionRects(paramList);
    }
  }
  
  private static class Api30Impl
  {
    static CharSequence getStateDescription(View paramView)
    {
      return paramView.getStateDescription();
    }
    
    public static WindowInsetsControllerCompat getWindowInsetsController(View paramView)
    {
      paramView = paramView.getWindowInsetsController();
      if (paramView != null) {
        paramView = WindowInsetsControllerCompat.toWindowInsetsControllerCompat(paramView);
      } else {
        paramView = null;
      }
      return paramView;
    }
    
    static void setStateDescription(View paramView, CharSequence paramCharSequence)
    {
      paramView.setStateDescription(paramCharSequence);
    }
  }
  
  private static final class Api31Impl
  {
    public static String[] getReceiveContentMimeTypes(View paramView)
    {
      return paramView.getReceiveContentMimeTypes();
    }
    
    public static ContentInfoCompat performReceiveContent(View paramView, ContentInfoCompat paramContentInfoCompat)
    {
      ContentInfo localContentInfo = paramContentInfoCompat.toContentInfo();
      paramView = paramView.performReceiveContent(localContentInfo);
      if (paramView == null) {
        return null;
      }
      if (paramView == localContentInfo) {
        return paramContentInfoCompat;
      }
      return ContentInfoCompat.toContentInfoCompat(paramView);
    }
    
    public static void setOnReceiveContentListener(View paramView, String[] paramArrayOfString, OnReceiveContentListener paramOnReceiveContentListener)
    {
      if (paramOnReceiveContentListener == null) {
        paramView.setOnReceiveContentListener(paramArrayOfString, null);
      } else {
        paramView.setOnReceiveContentListener(paramArrayOfString, new ViewCompat.OnReceiveContentListenerAdapter(paramOnReceiveContentListener));
      }
    }
  }
  
  @Retention(RetentionPolicy.SOURCE)
  public static @interface FocusDirection {}
  
  @Retention(RetentionPolicy.SOURCE)
  public static @interface FocusRealDirection {}
  
  @Retention(RetentionPolicy.SOURCE)
  public static @interface FocusRelativeDirection {}
  
  @Retention(RetentionPolicy.SOURCE)
  public static @interface NestedScrollType {}
  
  private static final class OnReceiveContentListenerAdapter
    implements android.view.OnReceiveContentListener
  {
    private final OnReceiveContentListener mJetpackListener;
    
    OnReceiveContentListenerAdapter(OnReceiveContentListener paramOnReceiveContentListener)
    {
      this.mJetpackListener = paramOnReceiveContentListener;
    }
    
    public ContentInfo onReceiveContent(View paramView, ContentInfo paramContentInfo)
    {
      ContentInfoCompat localContentInfoCompat = ContentInfoCompat.toContentInfoCompat(paramContentInfo);
      paramView = this.mJetpackListener.onReceiveContent(paramView, localContentInfoCompat);
      if (paramView == null) {
        return null;
      }
      if (paramView == localContentInfoCompat) {
        return paramContentInfo;
      }
      return paramView.toContentInfo();
    }
  }
  
  public static abstract interface OnUnhandledKeyEventListenerCompat
  {
    public abstract boolean onUnhandledKeyEvent(View paramView, KeyEvent paramKeyEvent);
  }
  
  @Retention(RetentionPolicy.SOURCE)
  public static @interface ScrollAxis {}
  
  @Retention(RetentionPolicy.SOURCE)
  public static @interface ScrollIndicators {}
  
  static class UnhandledKeyEventManager
  {
    private static final ArrayList<WeakReference<View>> sViewsWithListeners = new ArrayList();
    private SparseArray<WeakReference<View>> mCapturedKeys = null;
    private WeakReference<KeyEvent> mLastDispatchedPreViewKeyEvent = null;
    private WeakHashMap<View, Boolean> mViewsContainingListeners = null;
    
    static UnhandledKeyEventManager at(View paramView)
    {
      UnhandledKeyEventManager localUnhandledKeyEventManager2 = (UnhandledKeyEventManager)paramView.getTag(R.id.tag_unhandled_key_event_manager);
      UnhandledKeyEventManager localUnhandledKeyEventManager1 = localUnhandledKeyEventManager2;
      if (localUnhandledKeyEventManager2 == null)
      {
        localUnhandledKeyEventManager1 = new UnhandledKeyEventManager();
        paramView.setTag(R.id.tag_unhandled_key_event_manager, localUnhandledKeyEventManager1);
      }
      return localUnhandledKeyEventManager1;
    }
    
    private View dispatchInOrder(View paramView, KeyEvent paramKeyEvent)
    {
      Object localObject = this.mViewsContainingListeners;
      if ((localObject != null) && (((WeakHashMap)localObject).containsKey(paramView)))
      {
        if ((paramView instanceof ViewGroup))
        {
          ViewGroup localViewGroup = (ViewGroup)paramView;
          for (int i = localViewGroup.getChildCount() - 1; i >= 0; i--)
          {
            localObject = dispatchInOrder(localViewGroup.getChildAt(i), paramKeyEvent);
            if (localObject != null) {
              return (View)localObject;
            }
          }
        }
        if (onUnhandledKeyEvent(paramView, paramKeyEvent)) {
          return paramView;
        }
        return null;
      }
      return null;
    }
    
    private SparseArray<WeakReference<View>> getCapturedKeys()
    {
      if (this.mCapturedKeys == null) {
        this.mCapturedKeys = new SparseArray();
      }
      return this.mCapturedKeys;
    }
    
    private boolean onUnhandledKeyEvent(View paramView, KeyEvent paramKeyEvent)
    {
      ArrayList localArrayList = (ArrayList)paramView.getTag(R.id.tag_unhandled_key_listeners);
      if (localArrayList != null) {
        for (int i = localArrayList.size() - 1; i >= 0; i--) {
          if (((ViewCompat.OnUnhandledKeyEventListenerCompat)localArrayList.get(i)).onUnhandledKeyEvent(paramView, paramKeyEvent)) {
            return true;
          }
        }
      }
      return false;
    }
    
    private void recalcViewsWithUnhandled()
    {
      Object localObject1 = this.mViewsContainingListeners;
      if (localObject1 != null) {
        ((WeakHashMap)localObject1).clear();
      }
      ArrayList localArrayList = sViewsWithListeners;
      if (localArrayList.isEmpty()) {
        return;
      }
      try
      {
        if (this.mViewsContainingListeners == null)
        {
          localObject1 = new java/util/WeakHashMap;
          ((WeakHashMap)localObject1).<init>();
          this.mViewsContainingListeners = ((WeakHashMap)localObject1);
        }
        for (int i = localArrayList.size() - 1; i >= 0; i--)
        {
          localObject1 = sViewsWithListeners;
          View localView = (View)((WeakReference)((ArrayList)localObject1).get(i)).get();
          if (localView == null)
          {
            ((ArrayList)localObject1).remove(i);
          }
          else
          {
            this.mViewsContainingListeners.put(localView, Boolean.TRUE);
            for (localObject1 = localView.getParent(); (localObject1 instanceof View); localObject1 = ((ViewParent)localObject1).getParent()) {
              this.mViewsContainingListeners.put((View)localObject1, Boolean.TRUE);
            }
          }
        }
        return;
      }
      finally {}
    }
    
    static void registerListeningView(View paramView)
    {
      synchronized (sViewsWithListeners)
      {
        Object localObject = ???.iterator();
        while (((Iterator)localObject).hasNext()) {
          if (((WeakReference)((Iterator)localObject).next()).get() == paramView) {
            return;
          }
        }
        localObject = sViewsWithListeners;
        WeakReference localWeakReference = new java/lang/ref/WeakReference;
        localWeakReference.<init>(paramView);
        ((ArrayList)localObject).add(localWeakReference);
        return;
      }
    }
    
    static void unregisterListeningView(View paramView)
    {
      ArrayList localArrayList1 = sViewsWithListeners;
      int i = 0;
      try
      {
        for (;;)
        {
          ArrayList localArrayList2 = sViewsWithListeners;
          if (i >= localArrayList2.size()) {
            break;
          }
          if (((WeakReference)localArrayList2.get(i)).get() == paramView)
          {
            localArrayList2.remove(i);
            return;
          }
          i++;
        }
        return;
      }
      finally {}
    }
    
    boolean dispatch(View paramView, KeyEvent paramKeyEvent)
    {
      if (paramKeyEvent.getAction() == 0) {
        recalcViewsWithUnhandled();
      }
      paramView = dispatchInOrder(paramView, paramKeyEvent);
      if (paramKeyEvent.getAction() == 0)
      {
        int i = paramKeyEvent.getKeyCode();
        if ((paramView != null) && (!KeyEvent.isModifierKey(i))) {
          getCapturedKeys().put(i, new WeakReference(paramView));
        }
      }
      boolean bool;
      if (paramView != null) {
        bool = true;
      } else {
        bool = false;
      }
      return bool;
    }
    
    boolean preDispatch(KeyEvent paramKeyEvent)
    {
      Object localObject1 = this.mLastDispatchedPreViewKeyEvent;
      if ((localObject1 != null) && (((WeakReference)localObject1).get() == paramKeyEvent)) {
        return false;
      }
      this.mLastDispatchedPreViewKeyEvent = new WeakReference(paramKeyEvent);
      Object localObject2 = null;
      SparseArray localSparseArray = getCapturedKeys();
      localObject1 = localObject2;
      if (paramKeyEvent.getAction() == 1)
      {
        int i = localSparseArray.indexOfKey(paramKeyEvent.getKeyCode());
        localObject1 = localObject2;
        if (i >= 0)
        {
          localObject1 = (WeakReference)localSparseArray.valueAt(i);
          localSparseArray.removeAt(i);
        }
      }
      localObject2 = localObject1;
      if (localObject1 == null) {
        localObject2 = (WeakReference)localSparseArray.get(paramKeyEvent.getKeyCode());
      }
      if (localObject2 != null)
      {
        localObject1 = (View)((WeakReference)localObject2).get();
        if ((localObject1 != null) && (ViewCompat.isAttachedToWindow((View)localObject1))) {
          onUnhandledKeyEvent((View)localObject1, paramKeyEvent);
        }
        return true;
      }
      return false;
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/core/view/ViewCompat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */