package androidx.constraintlayout.motion.widget;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.DashPathEffect;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.SparseArray;
import android.util.SparseBooleanArray;
import android.util.SparseIntArray;
import android.view.Display;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.Interpolator;
import android.widget.TextView;
import androidx.constraintlayout.core.motion.utils.KeyCache;
import androidx.constraintlayout.core.widgets.ConstraintAnchor;
import androidx.constraintlayout.core.widgets.ConstraintAnchor.Type;
import androidx.constraintlayout.core.widgets.ConstraintWidget;
import androidx.constraintlayout.core.widgets.ConstraintWidget.DimensionBehaviour;
import androidx.constraintlayout.core.widgets.ConstraintWidgetContainer;
import androidx.constraintlayout.core.widgets.Flow;
import androidx.constraintlayout.core.widgets.Guideline;
import androidx.constraintlayout.core.widgets.Helper;
import androidx.constraintlayout.core.widgets.HelperWidget;
import androidx.constraintlayout.core.widgets.Placeholder;
import androidx.constraintlayout.core.widgets.VirtualLayout;
import androidx.constraintlayout.motion.utils.StopLogic;
import androidx.constraintlayout.motion.utils.ViewState;
import androidx.constraintlayout.widget.ConstraintHelper;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintLayout.LayoutParams;
import androidx.constraintlayout.widget.ConstraintLayoutStates;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.constraintlayout.widget.Constraints.LayoutParams;
import androidx.constraintlayout.widget.R.id;
import androidx.constraintlayout.widget.R.styleable;
import androidx.constraintlayout.widget.StateSet;
import androidx.core.view.NestedScrollingParent3;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import mt.Log229316;
import mt.Log5ECF72;
import mt.LogE84000;

public class MotionLayout
  extends ConstraintLayout
  implements NestedScrollingParent3
{
  private static final boolean DEBUG = false;
  public static final int DEBUG_SHOW_NONE = 0;
  public static final int DEBUG_SHOW_PATH = 2;
  public static final int DEBUG_SHOW_PROGRESS = 1;
  private static final float EPSILON = 1.0E-5F;
  public static boolean IS_IN_EDIT_MODE = false;
  static final int MAX_KEY_FRAMES = 50;
  static final String TAG = "MotionLayout";
  public static final int TOUCH_UP_COMPLETE = 0;
  public static final int TOUCH_UP_COMPLETE_TO_END = 2;
  public static final int TOUCH_UP_COMPLETE_TO_START = 1;
  public static final int TOUCH_UP_DECELERATE = 4;
  public static final int TOUCH_UP_DECELERATE_AND_COMPLETE = 5;
  public static final int TOUCH_UP_NEVER_TO_END = 7;
  public static final int TOUCH_UP_NEVER_TO_START = 6;
  public static final int TOUCH_UP_STOP = 3;
  public static final int VELOCITY_LAYOUT = 1;
  public static final int VELOCITY_POST_LAYOUT = 0;
  public static final int VELOCITY_STATIC_LAYOUT = 3;
  public static final int VELOCITY_STATIC_POST_LAYOUT = 2;
  boolean firstDown = true;
  private float lastPos;
  private float lastY;
  private long mAnimationStartTime = 0L;
  private int mBeginState = -1;
  private RectF mBoundsCheck = new RectF();
  int mCurrentState = -1;
  int mDebugPath = 0;
  private DecelerateInterpolator mDecelerateLogic = new DecelerateInterpolator();
  private ArrayList<MotionHelper> mDecoratorsHelpers = null;
  private boolean mDelayedApply = false;
  private DesignTool mDesignTool;
  DevModeDraw mDevModeDraw;
  private int mEndState = -1;
  int mEndWrapHeight;
  int mEndWrapWidth;
  HashMap<View, MotionController> mFrameArrayList = new HashMap();
  private int mFrames = 0;
  int mHeightMeasureMode;
  private boolean mInLayout = false;
  private boolean mInRotation = false;
  boolean mInTransition = false;
  boolean mIndirectTransition = false;
  private boolean mInteractionEnabled = true;
  Interpolator mInterpolator;
  private Matrix mInverseMatrix = null;
  boolean mIsAnimating = false;
  private boolean mKeepAnimating = false;
  private KeyCache mKeyCache = new KeyCache();
  private long mLastDrawTime = -1L;
  private float mLastFps = 0.0F;
  private int mLastHeightMeasureSpec = 0;
  int mLastLayoutHeight;
  int mLastLayoutWidth;
  float mLastVelocity = 0.0F;
  private int mLastWidthMeasureSpec = 0;
  private float mListenerPosition = 0.0F;
  private int mListenerState = 0;
  protected boolean mMeasureDuringTransition = false;
  Model mModel = new Model();
  private boolean mNeedsFireTransitionCompleted = false;
  int mOldHeight;
  int mOldWidth;
  private Runnable mOnComplete = null;
  private ArrayList<MotionHelper> mOnHideHelpers = null;
  private ArrayList<MotionHelper> mOnShowHelpers = null;
  float mPostInterpolationPosition;
  HashMap<View, ViewState> mPreRotate = new HashMap();
  private int mPreRotateHeight;
  private int mPreRotateWidth;
  private int mPreviouseRotation;
  Interpolator mProgressInterpolator = null;
  private View mRegionView = null;
  int mRotatMode = 0;
  MotionScene mScene;
  private int[] mScheduledTransitionTo = null;
  int mScheduledTransitions = 0;
  float mScrollTargetDT;
  float mScrollTargetDX;
  float mScrollTargetDY;
  long mScrollTargetTime;
  int mStartWrapHeight;
  int mStartWrapWidth;
  private StateCache mStateCache;
  private StopLogic mStopLogic = new StopLogic();
  Rect mTempRect = new Rect();
  private boolean mTemporalInterpolator = false;
  ArrayList<Integer> mTransitionCompleted = new ArrayList();
  private float mTransitionDuration = 1.0F;
  float mTransitionGoalPosition = 0.0F;
  private boolean mTransitionInstantly;
  float mTransitionLastPosition = 0.0F;
  private long mTransitionLastTime;
  private TransitionListener mTransitionListener;
  private CopyOnWriteArrayList<TransitionListener> mTransitionListeners = null;
  float mTransitionPosition = 0.0F;
  TransitionState mTransitionState = TransitionState.UNDEFINED;
  boolean mUndergoingMotion = false;
  int mWidthMeasureMode;
  
  public MotionLayout(Context paramContext)
  {
    super(paramContext);
    init(null);
  }
  
  public MotionLayout(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    init(paramAttributeSet);
  }
  
  public MotionLayout(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
    init(paramAttributeSet);
  }
  
  private boolean callTransformedTouchEvent(View paramView, MotionEvent paramMotionEvent, float paramFloat1, float paramFloat2)
  {
    Matrix localMatrix = paramView.getMatrix();
    if (localMatrix.isIdentity())
    {
      paramMotionEvent.offsetLocation(paramFloat1, paramFloat2);
      bool = paramView.onTouchEvent(paramMotionEvent);
      paramMotionEvent.offsetLocation(-paramFloat1, -paramFloat2);
      return bool;
    }
    paramMotionEvent = MotionEvent.obtain(paramMotionEvent);
    paramMotionEvent.offsetLocation(paramFloat1, paramFloat2);
    if (this.mInverseMatrix == null) {
      this.mInverseMatrix = new Matrix();
    }
    localMatrix.invert(this.mInverseMatrix);
    paramMotionEvent.transform(this.mInverseMatrix);
    boolean bool = paramView.onTouchEvent(paramMotionEvent);
    paramMotionEvent.recycle();
    return bool;
  }
  
  private void checkStructure()
  {
    Object localObject1 = this.mScene;
    if (localObject1 == null)
    {
      Log.e("MotionLayout", "CHECK: motion scene not set! set \"app:layoutDescription=\"@xml/file\"");
      return;
    }
    int i = ((MotionScene)localObject1).getStartId();
    localObject1 = this.mScene;
    checkStructure(i, ((MotionScene)localObject1).getConstraintSet(((MotionScene)localObject1).getStartId()));
    localObject1 = new SparseIntArray();
    SparseIntArray localSparseIntArray = new SparseIntArray();
    Iterator localIterator = this.mScene.getDefinedTransitions().iterator();
    while (localIterator.hasNext())
    {
      Object localObject2 = (MotionScene.Transition)localIterator.next();
      if (localObject2 == this.mScene.mCurrentTransition) {
        Log.v("MotionLayout", "CHECK: CURRENT");
      }
      checkStructure((MotionScene.Transition)localObject2);
      i = ((MotionScene.Transition)localObject2).getStartConstraintSetId();
      int j = ((MotionScene.Transition)localObject2).getEndConstraintSetId();
      localObject2 = Debug.getName(getContext(), i);
      Log5ECF72.a(localObject2);
      LogE84000.a(localObject2);
      Log229316.a(localObject2);
      String str = Debug.getName(getContext(), j);
      Log5ECF72.a(str);
      LogE84000.a(str);
      Log229316.a(str);
      if (((SparseIntArray)localObject1).get(i) == j) {
        Log.e("MotionLayout", "CHECK: two transitions with the same start and end " + (String)localObject2 + "->" + str);
      }
      if (localSparseIntArray.get(j) == i) {
        Log.e("MotionLayout", "CHECK: you can't have reverse transitions" + (String)localObject2 + "->" + str);
      }
      ((SparseIntArray)localObject1).put(i, j);
      localSparseIntArray.put(j, i);
      if (this.mScene.getConstraintSet(i) == null) {
        Log.e("MotionLayout", " no such constraintSetStart " + (String)localObject2);
      }
      if (this.mScene.getConstraintSet(j) == null) {
        Log.e("MotionLayout", " no such constraintSetEnd " + (String)localObject2);
      }
    }
  }
  
  private void checkStructure(int paramInt, ConstraintSet paramConstraintSet)
  {
    String str = Debug.getName(getContext(), paramInt);
    Log5ECF72.a(str);
    LogE84000.a(str);
    Log229316.a(str);
    int i = getChildCount();
    Object localObject2;
    for (paramInt = 0; paramInt < i; paramInt++)
    {
      localObject2 = getChildAt(paramInt);
      int j = ((View)localObject2).getId();
      if (j == -1) {
        Log.w("MotionLayout", "CHECK: " + str + " ALL VIEWS SHOULD HAVE ID's " + localObject2.getClass().getName() + " does not!");
      }
      if (paramConstraintSet.getConstraint(j) == null)
      {
        localObject1 = new StringBuilder().append("CHECK: ").append(str).append(" NO CONSTRAINTS for ");
        localObject2 = Debug.getName((View)localObject2);
        Log5ECF72.a(localObject2);
        LogE84000.a(localObject2);
        Log229316.a(localObject2);
        Log.w("MotionLayout", (String)localObject2);
      }
    }
    Object localObject1 = paramConstraintSet.getKnownIds();
    for (paramInt = 0; paramInt < localObject1.length; paramInt++)
    {
      i = localObject1[paramInt];
      localObject2 = Debug.getName(getContext(), i);
      Log5ECF72.a(localObject2);
      LogE84000.a(localObject2);
      Log229316.a(localObject2);
      if (findViewById(localObject1[paramInt]) == null) {
        Log.w("MotionLayout", "CHECK: " + str + " NO View matches id " + (String)localObject2);
      }
      if (paramConstraintSet.getHeight(i) == -1) {
        Log.w("MotionLayout", "CHECK: " + str + "(" + (String)localObject2 + ") no LAYOUT_HEIGHT");
      }
      if (paramConstraintSet.getWidth(i) == -1) {
        Log.w("MotionLayout", "CHECK: " + str + "(" + (String)localObject2 + ") no LAYOUT_HEIGHT");
      }
    }
  }
  
  private void checkStructure(MotionScene.Transition paramTransition)
  {
    if (paramTransition.getStartConstraintSetId() == paramTransition.getEndConstraintSetId()) {
      Log.e("MotionLayout", "CHECK: start and end constraint set should not be the same!");
    }
  }
  
  private void computeCurrentPositions()
  {
    int j = getChildCount();
    for (int i = 0; i < j; i++)
    {
      View localView = getChildAt(i);
      MotionController localMotionController = (MotionController)this.mFrameArrayList.get(localView);
      if (localMotionController != null) {
        localMotionController.setStartCurrentState(localView);
      }
    }
  }
  
  private void debugPos()
  {
    for (int i = 0; i < getChildCount(); i++)
    {
      View localView = getChildAt(i);
      Object localObject2 = new StringBuilder().append(" ");
      Object localObject1 = Debug.getLocation();
      Log5ECF72.a(localObject1);
      LogE84000.a(localObject1);
      Log229316.a(localObject1);
      localObject2 = ((StringBuilder)localObject2).append((String)localObject1).append(" ");
      localObject1 = Debug.getName(this);
      Log5ECF72.a(localObject1);
      LogE84000.a(localObject1);
      Log229316.a(localObject1);
      localObject1 = ((StringBuilder)localObject2).append((String)localObject1).append(" ");
      localObject2 = Debug.getName(getContext(), this.mCurrentState);
      Log5ECF72.a(localObject2);
      LogE84000.a(localObject2);
      Log229316.a(localObject2);
      localObject2 = ((StringBuilder)localObject1).append((String)localObject2).append(" ");
      localObject1 = Debug.getName(localView);
      Log5ECF72.a(localObject1);
      LogE84000.a(localObject1);
      Log229316.a(localObject1);
      Log.v("MotionLayout", (String)localObject1 + localView.getLeft() + " " + localView.getTop());
    }
  }
  
  private void evaluateLayout()
  {
    float f3 = Math.signum(this.mTransitionGoalPosition - this.mTransitionLastPosition);
    long l = getNanoTime();
    float f1 = 0.0F;
    Object localObject = this.mInterpolator;
    if (!(localObject instanceof StopLogic)) {
      f1 = (float)(l - this.mTransitionLastTime) * f3 * 1.0E-9F / this.mTransitionDuration;
    }
    f1 = this.mTransitionLastPosition + f1;
    int j = 0;
    if (this.mTransitionInstantly) {
      f1 = this.mTransitionGoalPosition;
    }
    float f2;
    if ((f3 <= 0.0F) || (f1 < this.mTransitionGoalPosition))
    {
      f2 = f1;
      i = j;
      if (f3 <= 0.0F)
      {
        f2 = f1;
        i = j;
        if (f1 > this.mTransitionGoalPosition) {}
      }
    }
    else
    {
      f2 = this.mTransitionGoalPosition;
      i = 1;
    }
    f1 = f2;
    if (localObject != null)
    {
      f1 = f2;
      if (i == 0) {
        if (this.mTemporalInterpolator) {
          f1 = ((Interpolator)localObject).getInterpolation((float)(l - this.mAnimationStartTime) * 1.0E-9F);
        } else {
          f1 = ((Interpolator)localObject).getInterpolation(f2);
        }
      }
    }
    if ((f3 <= 0.0F) || (f1 < this.mTransitionGoalPosition))
    {
      f2 = f1;
      if (f3 <= 0.0F)
      {
        f2 = f1;
        if (f1 > this.mTransitionGoalPosition) {}
      }
    }
    else
    {
      f2 = this.mTransitionGoalPosition;
    }
    this.mPostInterpolationPosition = f2;
    j = getChildCount();
    l = getNanoTime();
    localObject = this.mProgressInterpolator;
    if (localObject != null) {
      f2 = ((Interpolator)localObject).getInterpolation(f2);
    }
    for (int i = 0; i < j; i++)
    {
      localObject = getChildAt(i);
      MotionController localMotionController = (MotionController)this.mFrameArrayList.get(localObject);
      if (localMotionController != null) {
        localMotionController.interpolate((View)localObject, f2, l, this.mKeyCache);
      }
    }
    if (this.mMeasureDuringTransition) {
      requestLayout();
    }
  }
  
  private void fireTransitionChange()
  {
    Object localObject;
    if (this.mTransitionListener == null)
    {
      localObject = this.mTransitionListeners;
      if ((localObject == null) || (((CopyOnWriteArrayList)localObject).isEmpty())) {}
    }
    else if (this.mListenerPosition != this.mTransitionPosition)
    {
      if (this.mListenerState != -1)
      {
        localObject = this.mTransitionListener;
        if (localObject != null) {
          ((TransitionListener)localObject).onTransitionStarted(this, this.mBeginState, this.mEndState);
        }
        localObject = this.mTransitionListeners;
        if (localObject != null)
        {
          localObject = ((CopyOnWriteArrayList)localObject).iterator();
          while (((Iterator)localObject).hasNext()) {
            ((TransitionListener)((Iterator)localObject).next()).onTransitionStarted(this, this.mBeginState, this.mEndState);
          }
        }
        this.mIsAnimating = true;
      }
      this.mListenerState = -1;
      float f = this.mTransitionPosition;
      this.mListenerPosition = f;
      localObject = this.mTransitionListener;
      if (localObject != null) {
        ((TransitionListener)localObject).onTransitionChange(this, this.mBeginState, this.mEndState, f);
      }
      localObject = this.mTransitionListeners;
      if (localObject != null)
      {
        localObject = ((CopyOnWriteArrayList)localObject).iterator();
        while (((Iterator)localObject).hasNext()) {
          ((TransitionListener)((Iterator)localObject).next()).onTransitionChange(this, this.mBeginState, this.mEndState, this.mTransitionPosition);
        }
      }
      this.mIsAnimating = true;
    }
  }
  
  private void fireTransitionStarted(MotionLayout paramMotionLayout, int paramInt1, int paramInt2)
  {
    Object localObject = this.mTransitionListener;
    if (localObject != null) {
      ((TransitionListener)localObject).onTransitionStarted(this, paramInt1, paramInt2);
    }
    localObject = this.mTransitionListeners;
    if (localObject != null)
    {
      localObject = ((CopyOnWriteArrayList)localObject).iterator();
      while (((Iterator)localObject).hasNext()) {
        ((TransitionListener)((Iterator)localObject).next()).onTransitionStarted(paramMotionLayout, paramInt1, paramInt2);
      }
    }
  }
  
  private boolean handlesTouchEvent(float paramFloat1, float paramFloat2, View paramView, MotionEvent paramMotionEvent)
  {
    boolean bool2 = false;
    boolean bool1 = bool2;
    if ((paramView instanceof ViewGroup))
    {
      ViewGroup localViewGroup = (ViewGroup)paramView;
      for (int i = localViewGroup.getChildCount() - 1;; i--)
      {
        bool1 = bool2;
        if (i < 0) {
          break;
        }
        View localView = localViewGroup.getChildAt(i);
        if (handlesTouchEvent(localView.getLeft() + paramFloat1 - paramView.getScrollX(), localView.getTop() + paramFloat2 - paramView.getScrollY(), localView, paramMotionEvent))
        {
          bool1 = true;
          break;
        }
      }
    }
    bool2 = bool1;
    if (!bool1)
    {
      this.mBoundsCheck.set(paramFloat1, paramFloat2, paramView.getRight() + paramFloat1 - paramView.getLeft(), paramView.getBottom() + paramFloat2 - paramView.getTop());
      if (paramMotionEvent.getAction() == 0)
      {
        bool2 = bool1;
        if (!this.mBoundsCheck.contains(paramMotionEvent.getX(), paramMotionEvent.getY())) {}
      }
      else
      {
        bool2 = bool1;
        if (callTransformedTouchEvent(paramView, paramMotionEvent, -paramFloat1, -paramFloat2)) {
          bool2 = true;
        }
      }
    }
    return bool2;
  }
  
  private void init(AttributeSet paramAttributeSet)
  {
    IS_IN_EDIT_MODE = isInEditMode();
    if (paramAttributeSet != null)
    {
      paramAttributeSet = getContext().obtainStyledAttributes(paramAttributeSet, R.styleable.MotionLayout);
      int k = paramAttributeSet.getIndexCount();
      boolean bool2 = true;
      int i = 0;
      while (i < k)
      {
        int m = paramAttributeSet.getIndex(i);
        int j;
        boolean bool1;
        if (m == R.styleable.MotionLayout_layoutDescription)
        {
          j = paramAttributeSet.getResourceId(m, -1);
          this.mScene = new MotionScene(getContext(), this, j);
          bool1 = bool2;
        }
        else if (m == R.styleable.MotionLayout_currentState)
        {
          this.mCurrentState = paramAttributeSet.getResourceId(m, -1);
          bool1 = bool2;
        }
        else if (m == R.styleable.MotionLayout_motionProgress)
        {
          this.mTransitionGoalPosition = paramAttributeSet.getFloat(m, 0.0F);
          this.mInTransition = true;
          bool1 = bool2;
        }
        else if (m == R.styleable.MotionLayout_applyMotionScene)
        {
          bool1 = paramAttributeSet.getBoolean(m, bool2);
        }
        else
        {
          int n = R.styleable.MotionLayout_showPaths;
          j = 0;
          if (m == n)
          {
            bool1 = bool2;
            if (this.mDebugPath == 0)
            {
              if (paramAttributeSet.getBoolean(m, false)) {
                j = 2;
              }
              this.mDebugPath = j;
              bool1 = bool2;
            }
          }
          else
          {
            bool1 = bool2;
            if (m == R.styleable.MotionLayout_motionDebug)
            {
              this.mDebugPath = paramAttributeSet.getInt(m, 0);
              bool1 = bool2;
            }
          }
        }
        i++;
        bool2 = bool1;
      }
      paramAttributeSet.recycle();
      if (this.mScene == null) {
        Log.e("MotionLayout", "WARNING NO app:layoutDescription tag");
      }
      if (!bool2) {
        this.mScene = null;
      }
    }
    if (this.mDebugPath != 0) {
      checkStructure();
    }
    if (this.mCurrentState == -1)
    {
      paramAttributeSet = this.mScene;
      if (paramAttributeSet != null)
      {
        this.mCurrentState = paramAttributeSet.getStartId();
        this.mBeginState = this.mScene.getStartId();
        this.mEndState = this.mScene.getEndId();
      }
    }
  }
  
  private void processTransitionCompleted()
  {
    Object localObject1;
    if (this.mTransitionListener == null)
    {
      localObject1 = this.mTransitionListeners;
      if ((localObject1 == null) || (((CopyOnWriteArrayList)localObject1).isEmpty())) {
        return;
      }
    }
    this.mIsAnimating = false;
    Iterator localIterator = this.mTransitionCompleted.iterator();
    while (localIterator.hasNext())
    {
      localObject1 = (Integer)localIterator.next();
      Object localObject2 = this.mTransitionListener;
      if (localObject2 != null) {
        ((TransitionListener)localObject2).onTransitionCompleted(this, ((Integer)localObject1).intValue());
      }
      localObject2 = this.mTransitionListeners;
      if (localObject2 != null)
      {
        localObject2 = ((CopyOnWriteArrayList)localObject2).iterator();
        while (((Iterator)localObject2).hasNext()) {
          ((TransitionListener)((Iterator)localObject2).next()).onTransitionCompleted(this, ((Integer)localObject1).intValue());
        }
      }
    }
    this.mTransitionCompleted.clear();
  }
  
  private void setupMotionViews()
  {
    int i1 = getChildCount();
    this.mModel.build();
    int n = 1;
    this.mInTransition = true;
    Object localObject1 = new SparseArray();
    for (int i = 0; i < i1; i++)
    {
      localObject2 = getChildAt(i);
      ((SparseArray)localObject1).put(((View)localObject2).getId(), (MotionController)this.mFrameArrayList.get(localObject2));
    }
    int m = getWidth();
    int i2 = getHeight();
    int j = this.mScene.gatPathMotionArc();
    if (j != -1) {
      for (i = 0; i < i1; i++)
      {
        localObject2 = (MotionController)this.mFrameArrayList.get(getChildAt(i));
        if (localObject2 != null) {
          ((MotionController)localObject2).setPathMotionArc(j);
        }
      }
    }
    Object localObject2 = new SparseBooleanArray();
    Object localObject3 = new int[this.mFrameArrayList.size()];
    int k = 0;
    Object localObject4;
    for (i = 0; k < i1; i = j)
    {
      localObject4 = getChildAt(k);
      localObject4 = (MotionController)this.mFrameArrayList.get(localObject4);
      j = i;
      if (((MotionController)localObject4).getAnimateRelativeTo() != -1)
      {
        ((SparseBooleanArray)localObject2).put(((MotionController)localObject4).getAnimateRelativeTo(), true);
        localObject3[i] = ((MotionController)localObject4).getAnimateRelativeTo();
        j = i + 1;
      }
      k++;
    }
    if (this.mDecoratorsHelpers != null)
    {
      for (j = 0; j < i; j++)
      {
        localObject4 = (MotionController)this.mFrameArrayList.get(findViewById(localObject3[j]));
        if (localObject4 != null) {
          this.mScene.getKeyFrames((MotionController)localObject4);
        }
      }
      localObject4 = this.mDecoratorsHelpers.iterator();
      while (((Iterator)localObject4).hasNext()) {
        ((MotionHelper)((Iterator)localObject4).next()).onPreSetup(this, this.mFrameArrayList);
      }
      for (j = 0; j < i; j++)
      {
        localObject4 = (MotionController)this.mFrameArrayList.get(findViewById(localObject3[j]));
        if (localObject4 != null) {
          ((MotionController)localObject4).setup(m, i2, this.mTransitionDuration, getNanoTime());
        }
      }
    }
    else
    {
      for (j = 0; j < i; j++)
      {
        localObject4 = (MotionController)this.mFrameArrayList.get(findViewById(localObject3[j]));
        if (localObject4 != null)
        {
          this.mScene.getKeyFrames((MotionController)localObject4);
          ((MotionController)localObject4).setup(m, i2, this.mTransitionDuration, getNanoTime());
        }
      }
    }
    for (i = 0; i < i1; i++)
    {
      localObject4 = getChildAt(i);
      localObject3 = (MotionController)this.mFrameArrayList.get(localObject4);
      if ((!((SparseBooleanArray)localObject2).get(((View)localObject4).getId())) && (localObject3 != null))
      {
        this.mScene.getKeyFrames((MotionController)localObject3);
        ((MotionController)localObject3).setup(m, i2, this.mTransitionDuration, getNanoTime());
      }
    }
    float f1 = this.mScene.getStaggered();
    if (f1 != 0.0F)
    {
      if (f1 < 0.0D) {
        i = n;
      } else {
        i = 0;
      }
      k = 0;
      float f5 = Math.abs(f1);
      float f2 = Float.MAX_VALUE;
      f1 = -3.4028235E38F;
      float f4;
      float f3;
      for (j = 0; j < i1; j++)
      {
        localObject2 = (MotionController)this.mFrameArrayList.get(getChildAt(j));
        if (!Float.isNaN(((MotionController)localObject2).mMotionStagger))
        {
          j = 1;
          break label725;
        }
        f4 = ((MotionController)localObject2).getFinalX();
        f3 = ((MotionController)localObject2).getFinalY();
        if (i != 0) {
          f3 -= f4;
        } else {
          f3 += f4;
        }
        f2 = Math.min(f2, f3);
        f1 = Math.max(f1, f3);
      }
      j = k;
      label725:
      if (j != 0)
      {
        f4 = Float.MAX_VALUE;
        f1 = -3.4028235E38F;
        j = 0;
        while (j < i1)
        {
          localObject1 = (MotionController)this.mFrameArrayList.get(getChildAt(j));
          f3 = f4;
          f2 = f1;
          if (!Float.isNaN(((MotionController)localObject1).mMotionStagger))
          {
            f3 = Math.min(f4, ((MotionController)localObject1).mMotionStagger);
            f2 = Math.max(f1, ((MotionController)localObject1).mMotionStagger);
          }
          j++;
          f4 = f3;
          f1 = f2;
        }
        k = 0;
        j = m;
        while (k < i1)
        {
          localObject1 = (MotionController)this.mFrameArrayList.get(getChildAt(k));
          if (!Float.isNaN(((MotionController)localObject1).mMotionStagger))
          {
            ((MotionController)localObject1).mStaggerScale = (1.0F / (1.0F - f5));
            if (i != 0) {
              ((MotionController)localObject1).mStaggerOffset = (f5 - (f1 - ((MotionController)localObject1).mMotionStagger) / (f1 - f4) * f5);
            } else {
              ((MotionController)localObject1).mStaggerOffset = (f5 - (((MotionController)localObject1).mMotionStagger - f4) * f5 / (f1 - f4));
            }
          }
          k++;
        }
      }
      else
      {
        for (j = 0; j < i1; j++)
        {
          localObject1 = (MotionController)this.mFrameArrayList.get(getChildAt(j));
          f3 = ((MotionController)localObject1).getFinalX();
          f4 = ((MotionController)localObject1).getFinalY();
          if (i != 0) {
            f3 = f4 - f3;
          } else {
            f3 = f4 + f3;
          }
          ((MotionController)localObject1).mStaggerScale = (1.0F / (1.0F - f5));
          ((MotionController)localObject1).mStaggerOffset = (f5 - (f3 - f2) * f5 / (f1 - f2));
        }
      }
    }
  }
  
  private Rect toRect(ConstraintWidget paramConstraintWidget)
  {
    this.mTempRect.top = paramConstraintWidget.getY();
    this.mTempRect.left = paramConstraintWidget.getX();
    this.mTempRect.right = (paramConstraintWidget.getWidth() + this.mTempRect.left);
    this.mTempRect.bottom = (paramConstraintWidget.getHeight() + this.mTempRect.top);
    return this.mTempRect;
  }
  
  private static boolean willJump(float paramFloat1, float paramFloat2, float paramFloat3)
  {
    boolean bool2 = true;
    boolean bool1 = true;
    if (paramFloat1 > 0.0F)
    {
      f = paramFloat1 / paramFloat3;
      if (paramFloat2 + (paramFloat1 * f - paramFloat3 * f * f / 2.0F) <= 1.0F) {
        bool1 = false;
      }
      return bool1;
    }
    float f = -paramFloat1 / paramFloat3;
    if (paramFloat2 + (paramFloat1 * f + paramFloat3 * f * f / 2.0F) < 0.0F) {
      bool1 = bool2;
    } else {
      bool1 = false;
    }
    return bool1;
  }
  
  public void addTransitionListener(TransitionListener paramTransitionListener)
  {
    if (this.mTransitionListeners == null) {
      this.mTransitionListeners = new CopyOnWriteArrayList();
    }
    this.mTransitionListeners.add(paramTransitionListener);
  }
  
  void animateTo(float paramFloat)
  {
    MotionScene localMotionScene = this.mScene;
    if (localMotionScene == null) {
      return;
    }
    float f2 = this.mTransitionLastPosition;
    float f1 = this.mTransitionPosition;
    if ((f2 != f1) && (this.mTransitionInstantly)) {
      this.mTransitionLastPosition = f1;
    }
    if (this.mTransitionLastPosition == paramFloat) {
      return;
    }
    this.mTemporalInterpolator = false;
    f1 = this.mTransitionLastPosition;
    this.mTransitionGoalPosition = paramFloat;
    this.mTransitionDuration = (localMotionScene.getDuration() / 1000.0F);
    setProgress(this.mTransitionGoalPosition);
    this.mInterpolator = null;
    this.mProgressInterpolator = this.mScene.getInterpolator();
    this.mTransitionInstantly = false;
    this.mAnimationStartTime = getNanoTime();
    this.mInTransition = true;
    this.mTransitionPosition = f1;
    this.mTransitionLastPosition = f1;
    invalidate();
  }
  
  public boolean applyViewTransition(int paramInt, MotionController paramMotionController)
  {
    MotionScene localMotionScene = this.mScene;
    if (localMotionScene != null) {
      return localMotionScene.applyViewTransition(paramInt, paramMotionController);
    }
    return false;
  }
  
  public ConstraintSet cloneConstraintSet(int paramInt)
  {
    Object localObject = this.mScene;
    if (localObject == null) {
      return null;
    }
    ConstraintSet localConstraintSet = ((MotionScene)localObject).getConstraintSet(paramInt);
    localObject = new ConstraintSet();
    ((ConstraintSet)localObject).clone(localConstraintSet);
    return (ConstraintSet)localObject;
  }
  
  void disableAutoTransition(boolean paramBoolean)
  {
    MotionScene localMotionScene = this.mScene;
    if (localMotionScene == null) {
      return;
    }
    localMotionScene.disableAutoTransition(paramBoolean);
  }
  
  protected void dispatchDraw(Canvas paramCanvas)
  {
    Object localObject = this.mDecoratorsHelpers;
    if (localObject != null)
    {
      localObject = ((ArrayList)localObject).iterator();
      while (((Iterator)localObject).hasNext()) {
        ((MotionHelper)((Iterator)localObject).next()).onPreDraw(paramCanvas);
      }
    }
    evaluate(false);
    localObject = this.mScene;
    if ((localObject != null) && (((MotionScene)localObject).mViewTransitionController != null)) {
      this.mScene.mViewTransitionController.animate();
    }
    super.dispatchDraw(paramCanvas);
    if (this.mScene == null) {
      return;
    }
    if (((this.mDebugPath & 0x1) == 1) && (!isInEditMode()))
    {
      this.mFrames += 1;
      long l1 = getNanoTime();
      long l2 = this.mLastDrawTime;
      if (l2 != -1L)
      {
        l2 = l1 - l2;
        if (l2 > 200000000L)
        {
          this.mLastFps = ((int)(this.mFrames / ((float)l2 * 1.0E-9F) * 100.0F) / 100.0F);
          this.mFrames = 0;
          this.mLastDrawTime = l1;
        }
      }
      else
      {
        this.mLastDrawTime = l1;
      }
      Paint localPaint = new Paint();
      localPaint.setTextSize(42.0F);
      float f = (int)(getProgress() * 1000.0F) / 10.0F;
      StringBuilder localStringBuilder = new StringBuilder().append(this.mLastFps).append(" fps ");
      localObject = Debug.getState(this, this.mBeginState);
      Log5ECF72.a(localObject);
      LogE84000.a(localObject);
      Log229316.a(localObject);
      localObject = (String)localObject + " -> ";
      localStringBuilder = new StringBuilder().append((String)localObject);
      localObject = Debug.getState(this, this.mEndState);
      Log5ECF72.a(localObject);
      LogE84000.a(localObject);
      Log229316.a(localObject);
      localStringBuilder = localStringBuilder.append((String)localObject).append(" (progress: ").append(f).append(" ) state=");
      int i = this.mCurrentState;
      if (i == -1)
      {
        localObject = "undefined";
      }
      else
      {
        localObject = Debug.getState(this, i);
        Log5ECF72.a(localObject);
        LogE84000.a(localObject);
        Log229316.a(localObject);
      }
      localObject = (String)localObject;
      localPaint.setColor(-16777216);
      paramCanvas.drawText((String)localObject, 11.0F, getHeight() - 29, localPaint);
      localPaint.setColor(-7864184);
      paramCanvas.drawText((String)localObject, 10.0F, getHeight() - 30, localPaint);
    }
    if (this.mDebugPath > 1)
    {
      if (this.mDevModeDraw == null) {
        this.mDevModeDraw = new DevModeDraw();
      }
      this.mDevModeDraw.draw(paramCanvas, this.mFrameArrayList, this.mScene.getDuration(), this.mDebugPath);
    }
    localObject = this.mDecoratorsHelpers;
    if (localObject != null)
    {
      localObject = ((ArrayList)localObject).iterator();
      while (((Iterator)localObject).hasNext()) {
        ((MotionHelper)((Iterator)localObject).next()).onPostDraw(paramCanvas);
      }
    }
  }
  
  public void enableTransition(int paramInt, boolean paramBoolean)
  {
    MotionScene.Transition localTransition1 = getTransition(paramInt);
    if (paramBoolean)
    {
      localTransition1.setEnabled(true);
      return;
    }
    if (localTransition1 == this.mScene.mCurrentTransition)
    {
      Iterator localIterator = this.mScene.getTransitionsWithState(this.mCurrentState).iterator();
      while (localIterator.hasNext())
      {
        MotionScene.Transition localTransition2 = (MotionScene.Transition)localIterator.next();
        if (localTransition2.isEnabled())
        {
          this.mScene.mCurrentTransition = localTransition2;
          break;
        }
      }
    }
    localTransition1.setEnabled(false);
  }
  
  public void enableViewTransition(int paramInt, boolean paramBoolean)
  {
    MotionScene localMotionScene = this.mScene;
    if (localMotionScene != null) {
      localMotionScene.enableViewTransition(paramInt, paramBoolean);
    }
  }
  
  void endTrigger(boolean paramBoolean)
  {
    int j = getChildCount();
    for (int i = 0; i < j; i++)
    {
      Object localObject = getChildAt(i);
      localObject = (MotionController)this.mFrameArrayList.get(localObject);
      if (localObject != null) {
        ((MotionController)localObject).endTrigger(paramBoolean);
      }
    }
  }
  
  void evaluate(boolean paramBoolean)
  {
    if (this.mTransitionLastTime == -1L) {
      this.mTransitionLastTime = getNanoTime();
    }
    float f1 = this.mTransitionLastPosition;
    if ((f1 > 0.0F) && (f1 < 1.0F)) {
      this.mCurrentState = -1;
    }
    boolean bool2 = false;
    if (!this.mKeepAnimating)
    {
      bool1 = bool2;
      if (!this.mInTransition) {
        break label1148;
      }
      if (!paramBoolean)
      {
        bool1 = bool2;
        if (this.mTransitionGoalPosition == f1) {
          break label1148;
        }
      }
    }
    float f4 = Math.signum(this.mTransitionGoalPosition - f1);
    long l = getNanoTime();
    float f3 = 0.0F;
    Object localObject1 = this.mInterpolator;
    if (!(localObject1 instanceof MotionInterpolator)) {
      f3 = (float)(l - this.mTransitionLastTime) * f4 * 1.0E-9F / this.mTransitionDuration;
    }
    float f2 = this.mTransitionLastPosition + f3;
    bool2 = false;
    if (this.mTransitionInstantly) {
      f2 = this.mTransitionGoalPosition;
    }
    if ((f4 <= 0.0F) || (f2 < this.mTransitionGoalPosition))
    {
      f1 = f2;
      bool1 = bool2;
      if (f4 <= 0.0F)
      {
        f1 = f2;
        bool1 = bool2;
        if (f2 > this.mTransitionGoalPosition) {}
      }
    }
    else
    {
      f1 = this.mTransitionGoalPosition;
      this.mInTransition = false;
      bool1 = true;
    }
    this.mTransitionLastPosition = f1;
    this.mTransitionPosition = f1;
    this.mTransitionLastTime = l;
    Object localObject2;
    if ((localObject1 != null) && (!bool1))
    {
      if (this.mTemporalInterpolator)
      {
        f3 = ((Interpolator)localObject1).getInterpolation((float)(l - this.mAnimationStartTime) * 1.0E-9F);
        localObject1 = this.mInterpolator;
        localObject2 = this.mStopLogic;
        if (localObject1 == localObject2)
        {
          if (((StopLogic)localObject2).isStopped()) {
            bool1 = true;
          } else {
            bool1 = true;
          }
        }
        else {
          bool1 = false;
        }
        this.mTransitionLastPosition = f3;
        this.mTransitionLastTime = l;
        localObject1 = this.mInterpolator;
        f1 = f3;
        if ((localObject1 instanceof MotionInterpolator))
        {
          float f5 = ((MotionInterpolator)localObject1).getVelocity();
          this.mLastVelocity = f5;
          if ((Math.abs(f5) * this.mTransitionDuration <= 1.0E-5F) && (bool1 == true)) {
            this.mInTransition = false;
          }
          f2 = f3;
          if (f5 > 0.0F)
          {
            f2 = f3;
            if (f3 >= 1.0F)
            {
              f2 = 1.0F;
              this.mTransitionLastPosition = 1.0F;
              this.mInTransition = false;
            }
          }
          f1 = f2;
          if (f5 < 0.0F)
          {
            f1 = f2;
            if (f2 <= 0.0F)
            {
              f1 = 0.0F;
              this.mTransitionLastPosition = 0.0F;
              this.mInTransition = false;
            }
          }
        }
      }
      else
      {
        f2 = ((Interpolator)localObject1).getInterpolation(f1);
        localObject1 = this.mInterpolator;
        if ((localObject1 instanceof MotionInterpolator)) {
          this.mLastVelocity = ((MotionInterpolator)localObject1).getVelocity();
        } else {
          this.mLastVelocity = ((((Interpolator)localObject1).getInterpolation(f1 + f3) - f2) * f4 / f3);
        }
        bool1 = false;
        f1 = f2;
      }
    }
    else
    {
      this.mLastVelocity = f3;
      bool1 = false;
    }
    boolean bool3 = false;
    if (Math.abs(this.mLastVelocity) > 1.0E-5F) {
      setState(TransitionState.MOVING);
    }
    f2 = f1;
    if (bool1 != true)
    {
      if ((f4 <= 0.0F) || (f1 < this.mTransitionGoalPosition))
      {
        f3 = f1;
        if (f4 <= 0.0F)
        {
          f3 = f1;
          if (f1 > this.mTransitionGoalPosition) {}
        }
      }
      else
      {
        f3 = this.mTransitionGoalPosition;
        this.mInTransition = false;
      }
      if (f3 < 1.0F)
      {
        f2 = f3;
        if (f3 > 0.0F) {}
      }
      else
      {
        this.mInTransition = false;
        setState(TransitionState.FINISHED);
        f2 = f3;
      }
    }
    int i = getChildCount();
    this.mKeepAnimating = false;
    l = getNanoTime();
    this.mPostInterpolationPosition = f2;
    localObject1 = this.mProgressInterpolator;
    if (localObject1 == null) {
      f1 = f2;
    } else {
      f1 = ((Interpolator)localObject1).getInterpolation(f2);
    }
    localObject1 = this.mProgressInterpolator;
    if (localObject1 != null)
    {
      f3 = ((Interpolator)localObject1).getInterpolation(f4 / this.mTransitionDuration + f2);
      this.mLastVelocity = f3;
      this.mLastVelocity = (f3 - this.mProgressInterpolator.getInterpolation(f2));
    }
    for (bool2 = false; bool2 < i; bool2++)
    {
      localObject2 = getChildAt(bool2);
      localObject1 = (MotionController)this.mFrameArrayList.get(localObject2);
      if (localObject1 != null)
      {
        paramBoolean = this.mKeepAnimating;
        this.mKeepAnimating = (((MotionController)localObject1).interpolate((View)localObject2, f1, l, this.mKeyCache) | paramBoolean);
      }
    }
    if (((f4 > 0.0F) && (f2 >= this.mTransitionGoalPosition)) || ((f4 <= 0.0F) && (f2 <= this.mTransitionGoalPosition))) {
      bool1 = true;
    } else {
      bool1 = false;
    }
    if ((!this.mKeepAnimating) && (!this.mInTransition) && (bool1)) {
      setState(TransitionState.FINISHED);
    }
    if (this.mMeasureDuringTransition) {
      requestLayout();
    }
    paramBoolean = this.mKeepAnimating;
    if (!bool1) {
      bool1 = true;
    } else {
      bool1 = false;
    }
    this.mKeepAnimating = (paramBoolean | bool1);
    boolean bool1 = bool3;
    if (f2 <= 0.0F)
    {
      bool2 = this.mBeginState;
      bool1 = bool3;
      if (bool2 != true)
      {
        bool1 = bool3;
        if (this.mCurrentState != bool2)
        {
          this.mCurrentState = bool2;
          this.mScene.getConstraintSet(bool2).applyCustomAttributes(this);
          setState(TransitionState.FINISHED);
          bool1 = true;
        }
      }
    }
    bool2 = bool1;
    if (f2 >= 1.0D)
    {
      i = this.mCurrentState;
      bool3 = this.mEndState;
      bool2 = bool1;
      if (i != bool3)
      {
        this.mCurrentState = bool3;
        this.mScene.getConstraintSet(bool3).applyCustomAttributes(this);
        setState(TransitionState.FINISHED);
        bool2 = true;
      }
    }
    if ((!this.mKeepAnimating) && (!this.mInTransition))
    {
      if (((f4 > 0.0F) && (f2 == 1.0F)) || ((f4 < 0.0F) && (f2 == 0.0F))) {
        setState(TransitionState.FINISHED);
      }
    }
    else {
      invalidate();
    }
    if ((!this.mKeepAnimating) && (!this.mInTransition) && (((f4 > 0.0F) && (f2 == 1.0F)) || ((f4 < 0.0F) && (f2 == 0.0F)))) {
      onNewStateAttachHandlers();
    }
    bool1 = bool2;
    label1148:
    f1 = this.mTransitionLastPosition;
    if (f1 >= 1.0F)
    {
      bool3 = this.mCurrentState;
      bool2 = this.mEndState;
      if (bool3 != bool2) {
        bool1 = true;
      }
      this.mCurrentState = bool2;
      bool2 = bool1;
    }
    else
    {
      bool2 = bool1;
      if (f1 <= 0.0F)
      {
        bool3 = this.mCurrentState;
        bool2 = this.mBeginState;
        if (bool3 != bool2) {
          bool1 = true;
        }
        this.mCurrentState = bool2;
        bool2 = bool1;
      }
    }
    this.mNeedsFireTransitionCompleted |= bool2;
    if ((bool2) && (!this.mInLayout)) {
      requestLayout();
    }
    this.mTransitionPosition = this.mTransitionLastPosition;
  }
  
  protected void fireTransitionCompleted()
  {
    if (this.mTransitionListener == null)
    {
      localObject = this.mTransitionListeners;
      if ((localObject == null) || (((CopyOnWriteArrayList)localObject).isEmpty())) {}
    }
    else if (this.mListenerState == -1)
    {
      this.mListenerState = this.mCurrentState;
      int i = -1;
      if (!this.mTransitionCompleted.isEmpty())
      {
        localObject = this.mTransitionCompleted;
        i = ((Integer)((ArrayList)localObject).get(((ArrayList)localObject).size() - 1)).intValue();
      }
      int j = this.mCurrentState;
      if ((i != j) && (j != -1)) {
        this.mTransitionCompleted.add(Integer.valueOf(j));
      }
    }
    processTransitionCompleted();
    Object localObject = this.mOnComplete;
    if (localObject != null) {
      ((Runnable)localObject).run();
    }
    localObject = this.mScheduledTransitionTo;
    if ((localObject != null) && (this.mScheduledTransitions > 0))
    {
      transitionToState(localObject[0]);
      localObject = this.mScheduledTransitionTo;
      System.arraycopy(localObject, 1, localObject, 0, localObject.length - 1);
      this.mScheduledTransitions -= 1;
    }
  }
  
  public void fireTrigger(int paramInt, boolean paramBoolean, float paramFloat)
  {
    Object localObject = this.mTransitionListener;
    if (localObject != null) {
      ((TransitionListener)localObject).onTransitionTrigger(this, paramInt, paramBoolean, paramFloat);
    }
    localObject = this.mTransitionListeners;
    if (localObject != null)
    {
      localObject = ((CopyOnWriteArrayList)localObject).iterator();
      while (((Iterator)localObject).hasNext()) {
        ((TransitionListener)((Iterator)localObject).next()).onTransitionTrigger(this, paramInt, paramBoolean, paramFloat);
      }
    }
  }
  
  void getAnchorDpDt(int paramInt, float paramFloat1, float paramFloat2, float paramFloat3, float[] paramArrayOfFloat)
  {
    Object localObject = this.mFrameArrayList;
    View localView = getViewById(paramInt);
    localObject = (MotionController)((HashMap)localObject).get(localView);
    if (localObject != null)
    {
      ((MotionController)localObject).getDpDt(paramFloat1, paramFloat2, paramFloat3, paramArrayOfFloat);
      paramFloat2 = localView.getY();
      paramFloat3 = paramFloat1 - this.lastPos;
      float f = this.lastY;
      if (paramFloat3 != 0.0F) {
        paramFloat3 = (paramFloat2 - f) / paramFloat3;
      }
      this.lastPos = paramFloat1;
      this.lastY = paramFloat2;
    }
    else
    {
      if (localView == null) {
        paramArrayOfFloat = "" + paramInt;
      } else {
        paramArrayOfFloat = localView.getContext().getResources().getResourceName(paramInt);
      }
      Log.w("MotionLayout", "WARNING could not find view id " + paramArrayOfFloat);
    }
  }
  
  public ConstraintSet getConstraintSet(int paramInt)
  {
    MotionScene localMotionScene = this.mScene;
    if (localMotionScene == null) {
      return null;
    }
    return localMotionScene.getConstraintSet(paramInt);
  }
  
  public int[] getConstraintSetIds()
  {
    MotionScene localMotionScene = this.mScene;
    if (localMotionScene == null) {
      return null;
    }
    return localMotionScene.getConstraintSetIds();
  }
  
  String getConstraintSetNames(int paramInt)
  {
    MotionScene localMotionScene = this.mScene;
    if (localMotionScene == null) {
      return null;
    }
    return localMotionScene.lookUpConstraintName(paramInt);
  }
  
  public int getCurrentState()
  {
    return this.mCurrentState;
  }
  
  public void getDebugMode(boolean paramBoolean)
  {
    int i;
    if (paramBoolean) {
      i = 2;
    } else {
      i = 1;
    }
    this.mDebugPath = i;
    invalidate();
  }
  
  public ArrayList<MotionScene.Transition> getDefinedTransitions()
  {
    MotionScene localMotionScene = this.mScene;
    if (localMotionScene == null) {
      return null;
    }
    return localMotionScene.getDefinedTransitions();
  }
  
  public DesignTool getDesignTool()
  {
    if (this.mDesignTool == null) {
      this.mDesignTool = new DesignTool(this);
    }
    return this.mDesignTool;
  }
  
  public int getEndState()
  {
    return this.mEndState;
  }
  
  MotionController getMotionController(int paramInt)
  {
    return (MotionController)this.mFrameArrayList.get(findViewById(paramInt));
  }
  
  protected long getNanoTime()
  {
    return System.nanoTime();
  }
  
  public float getProgress()
  {
    return this.mTransitionLastPosition;
  }
  
  public MotionScene getScene()
  {
    return this.mScene;
  }
  
  public int getStartState()
  {
    return this.mBeginState;
  }
  
  public float getTargetPosition()
  {
    return this.mTransitionGoalPosition;
  }
  
  public MotionScene.Transition getTransition(int paramInt)
  {
    return this.mScene.getTransitionById(paramInt);
  }
  
  public Bundle getTransitionState()
  {
    if (this.mStateCache == null) {
      this.mStateCache = new StateCache();
    }
    this.mStateCache.recordState();
    return this.mStateCache.getTransitionState();
  }
  
  public long getTransitionTimeMs()
  {
    MotionScene localMotionScene = this.mScene;
    if (localMotionScene != null) {
      this.mTransitionDuration = (localMotionScene.getDuration() / 1000.0F);
    }
    return (this.mTransitionDuration * 1000.0F);
  }
  
  public float getVelocity()
  {
    return this.mLastVelocity;
  }
  
  public void getViewVelocity(View paramView, float paramFloat1, float paramFloat2, float[] paramArrayOfFloat, int paramInt)
  {
    float f1 = this.mLastVelocity;
    float f2 = this.mTransitionLastPosition;
    if (this.mInterpolator != null)
    {
      f1 = Math.signum(this.mTransitionGoalPosition - this.mTransitionLastPosition);
      float f3 = this.mInterpolator.getInterpolation(this.mTransitionLastPosition + 1.0E-5F);
      f2 = this.mInterpolator.getInterpolation(this.mTransitionLastPosition);
      f1 = f1 * ((f3 - f2) / 1.0E-5F) / this.mTransitionDuration;
    }
    Object localObject = this.mInterpolator;
    if ((localObject instanceof MotionInterpolator)) {
      f1 = ((MotionInterpolator)localObject).getVelocity();
    }
    localObject = (MotionController)this.mFrameArrayList.get(paramView);
    if ((paramInt & 0x1) == 0) {
      ((MotionController)localObject).getPostLayoutDvDp(f2, paramView.getWidth(), paramView.getHeight(), paramFloat1, paramFloat2, paramArrayOfFloat);
    } else {
      ((MotionController)localObject).getDpDt(f2, paramFloat1, paramFloat2, paramArrayOfFloat);
    }
    if (paramInt < 2)
    {
      paramArrayOfFloat[0] *= f1;
      paramArrayOfFloat[1] *= f1;
    }
  }
  
  public boolean isAttachedToWindow()
  {
    if (Build.VERSION.SDK_INT >= 19) {
      return super.isAttachedToWindow();
    }
    boolean bool;
    if (getWindowToken() != null) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public boolean isDelayedApplicationOfInitialState()
  {
    return this.mDelayedApply;
  }
  
  public boolean isInRotation()
  {
    return this.mInRotation;
  }
  
  public boolean isInteractionEnabled()
  {
    return this.mInteractionEnabled;
  }
  
  public boolean isViewTransitionEnabled(int paramInt)
  {
    MotionScene localMotionScene = this.mScene;
    if (localMotionScene != null) {
      return localMotionScene.isViewTransitionEnabled(paramInt);
    }
    return false;
  }
  
  public void jumpToState(int paramInt)
  {
    if (!isAttachedToWindow()) {
      this.mCurrentState = paramInt;
    }
    if (this.mBeginState == paramInt) {
      setProgress(0.0F);
    } else if (this.mEndState == paramInt) {
      setProgress(1.0F);
    } else {
      setTransition(paramInt, paramInt);
    }
  }
  
  public void loadLayoutDescription(int paramInt)
  {
    if (paramInt != 0) {
      try
      {
        Object localObject1 = new androidx/constraintlayout/motion/widget/MotionScene;
        ((MotionScene)localObject1).<init>(getContext(), this, paramInt);
        this.mScene = ((MotionScene)localObject1);
        if (this.mCurrentState == -1)
        {
          this.mCurrentState = ((MotionScene)localObject1).getStartId();
          this.mBeginState = this.mScene.getStartId();
          this.mEndState = this.mScene.getEndId();
        }
        if ((Build.VERSION.SDK_INT >= 19) && (!isAttachedToWindow())) {
          this.mScene = null;
        }
        try
        {
          if (Build.VERSION.SDK_INT >= 17)
          {
            localObject1 = getDisplay();
            if (localObject1 == null) {
              paramInt = 0;
            } else {
              paramInt = ((Display)localObject1).getRotation();
            }
            this.mPreviouseRotation = paramInt;
          }
          localObject1 = this.mScene;
          if (localObject1 != null)
          {
            localObject1 = ((MotionScene)localObject1).getConstraintSet(this.mCurrentState);
            this.mScene.readFallback(this);
            localObject2 = this.mDecoratorsHelpers;
            if (localObject2 != null)
            {
              localObject2 = ((ArrayList)localObject2).iterator();
              while (((Iterator)localObject2).hasNext()) {
                ((MotionHelper)((Iterator)localObject2).next()).onFinishedMotionScene(this);
              }
            }
            if (localObject1 != null) {
              ((ConstraintSet)localObject1).applyTo(this);
            }
            this.mBeginState = this.mCurrentState;
          }
          onNewStateAttachHandlers();
          localObject1 = this.mStateCache;
          if (localObject1 != null)
          {
            if (this.mDelayedApply)
            {
              localObject1 = new androidx/constraintlayout/motion/widget/MotionLayout$1;
              ((1)localObject1).<init>(this);
              post((Runnable)localObject1);
            }
            else
            {
              ((StateCache)localObject1).apply();
            }
          }
          else
          {
            localObject1 = this.mScene;
            if ((localObject1 != null) && (((MotionScene)localObject1).mCurrentTransition != null) && (this.mScene.mCurrentTransition.getAutoTransition() == 4))
            {
              transitionToEnd();
              setState(TransitionState.SETUP);
              setState(TransitionState.MOVING);
            }
          }
        }
        catch (Exception localException1)
        {
          Object localObject2 = new java/lang/IllegalArgumentException;
          ((IllegalArgumentException)localObject2).<init>("unable to parse MotionScene file", localException1);
          throw ((Throwable)localObject2);
        }
        this.mScene = null;
      }
      catch (Exception localException2)
      {
        throw new IllegalArgumentException("unable to parse MotionScene file", localException2);
      }
    }
  }
  
  int lookUpConstraintId(String paramString)
  {
    MotionScene localMotionScene = this.mScene;
    if (localMotionScene == null) {
      return 0;
    }
    return localMotionScene.lookUpConstraintId(paramString);
  }
  
  protected MotionTracker obtainVelocityTracker()
  {
    return MyTracker.obtain();
  }
  
  protected void onAttachedToWindow()
  {
    super.onAttachedToWindow();
    if (Build.VERSION.SDK_INT >= 17)
    {
      localObject1 = getDisplay();
      if (localObject1 != null) {
        this.mPreviouseRotation = ((Display)localObject1).getRotation();
      }
    }
    Object localObject1 = this.mScene;
    if (localObject1 != null)
    {
      int i = this.mCurrentState;
      if (i != -1)
      {
        localObject1 = ((MotionScene)localObject1).getConstraintSet(i);
        this.mScene.readFallback(this);
        Object localObject2 = this.mDecoratorsHelpers;
        if (localObject2 != null)
        {
          localObject2 = ((ArrayList)localObject2).iterator();
          while (((Iterator)localObject2).hasNext()) {
            ((MotionHelper)((Iterator)localObject2).next()).onFinishedMotionScene(this);
          }
        }
        if (localObject1 != null) {
          ((ConstraintSet)localObject1).applyTo(this);
        }
        this.mBeginState = this.mCurrentState;
      }
    }
    onNewStateAttachHandlers();
    localObject1 = this.mStateCache;
    if (localObject1 != null)
    {
      if (this.mDelayedApply) {
        post(new Runnable()
        {
          public void run()
          {
            MotionLayout.this.mStateCache.apply();
          }
        });
      } else {
        ((StateCache)localObject1).apply();
      }
    }
    else
    {
      localObject1 = this.mScene;
      if ((localObject1 != null) && (((MotionScene)localObject1).mCurrentTransition != null) && (this.mScene.mCurrentTransition.getAutoTransition() == 4))
      {
        transitionToEnd();
        setState(TransitionState.SETUP);
        setState(TransitionState.MOVING);
      }
    }
  }
  
  public boolean onInterceptTouchEvent(MotionEvent paramMotionEvent)
  {
    Object localObject = this.mScene;
    if ((localObject != null) && (this.mInteractionEnabled))
    {
      if (((MotionScene)localObject).mViewTransitionController != null) {
        this.mScene.mViewTransitionController.touchEvent(paramMotionEvent);
      }
      localObject = this.mScene.mCurrentTransition;
      if ((localObject != null) && (((MotionScene.Transition)localObject).isEnabled()))
      {
        localObject = ((MotionScene.Transition)localObject).getTouchResponse();
        if (localObject != null)
        {
          if (paramMotionEvent.getAction() == 0)
          {
            RectF localRectF = ((TouchResponse)localObject).getTouchRegion(this, new RectF());
            if ((localRectF != null) && (!localRectF.contains(paramMotionEvent.getX(), paramMotionEvent.getY()))) {
              return false;
            }
          }
          int i = ((TouchResponse)localObject).getTouchRegionId();
          if (i != -1)
          {
            localObject = this.mRegionView;
            if ((localObject == null) || (((View)localObject).getId() != i)) {
              this.mRegionView = findViewById(i);
            }
            localObject = this.mRegionView;
            if (localObject != null)
            {
              this.mBoundsCheck.set(((View)localObject).getLeft(), this.mRegionView.getTop(), this.mRegionView.getRight(), this.mRegionView.getBottom());
              if ((this.mBoundsCheck.contains(paramMotionEvent.getX(), paramMotionEvent.getY())) && (!handlesTouchEvent(this.mRegionView.getLeft(), this.mRegionView.getTop(), this.mRegionView, paramMotionEvent))) {
                return onTouchEvent(paramMotionEvent);
              }
            }
          }
        }
      }
      return false;
    }
    return false;
  }
  
  protected void onLayout(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    this.mInLayout = true;
    try
    {
      if (this.mScene == null)
      {
        super.onLayout(paramBoolean, paramInt1, paramInt2, paramInt3, paramInt4);
        return;
      }
      paramInt1 = paramInt3 - paramInt1;
      paramInt2 = paramInt4 - paramInt2;
      if ((this.mLastLayoutWidth != paramInt1) || (this.mLastLayoutHeight != paramInt2))
      {
        rebuildScene();
        evaluate(true);
      }
      this.mLastLayoutWidth = paramInt1;
      this.mLastLayoutHeight = paramInt2;
      this.mOldWidth = paramInt1;
      this.mOldHeight = paramInt2;
      return;
    }
    finally
    {
      this.mInLayout = false;
    }
  }
  
  protected void onMeasure(int paramInt1, int paramInt2)
  {
    if (this.mScene == null)
    {
      super.onMeasure(paramInt1, paramInt2);
      return;
    }
    int i;
    if ((this.mLastWidthMeasureSpec == paramInt1) && (this.mLastHeightMeasureSpec == paramInt2)) {
      i = 0;
    } else {
      i = 1;
    }
    if (this.mNeedsFireTransitionCompleted)
    {
      this.mNeedsFireTransitionCompleted = false;
      onNewStateAttachHandlers();
      processTransitionCompleted();
      i = 1;
    }
    if (this.mDirtyHierarchy) {
      i = 1;
    }
    this.mLastWidthMeasureSpec = paramInt1;
    this.mLastHeightMeasureSpec = paramInt2;
    int m = this.mScene.getStartId();
    int j = this.mScene.getEndId();
    int k = 1;
    if (((i != 0) || (this.mModel.isNotConfiguredWith(m, j))) && (this.mBeginState != -1))
    {
      super.onMeasure(paramInt1, paramInt2);
      this.mModel.initFrom(this.mLayoutWidget, this.mScene.getConstraintSet(m), this.mScene.getConstraintSet(j));
      this.mModel.reEvaluateState();
      this.mModel.setMeasuredId(m, j);
      j = 0;
    }
    else
    {
      j = k;
      if (i != 0)
      {
        super.onMeasure(paramInt1, paramInt2);
        j = k;
      }
    }
    if ((this.mMeasureDuringTransition) || (j != 0))
    {
      i = getPaddingTop();
      paramInt2 = getPaddingBottom();
      paramInt1 = getPaddingLeft();
      j = getPaddingRight();
      paramInt1 = this.mLayoutWidget.getWidth() + (paramInt1 + j);
      paramInt2 = this.mLayoutWidget.getHeight() + (i + paramInt2);
      i = this.mWidthMeasureMode;
      if ((i == Integer.MIN_VALUE) || (i == 0))
      {
        paramInt1 = this.mStartWrapWidth;
        paramInt1 = (int)(paramInt1 + this.mPostInterpolationPosition * (this.mEndWrapWidth - paramInt1));
        requestLayout();
      }
      i = this.mHeightMeasureMode;
      if ((i == Integer.MIN_VALUE) || (i == 0))
      {
        paramInt2 = this.mStartWrapHeight;
        paramInt2 = (int)(paramInt2 + this.mPostInterpolationPosition * (this.mEndWrapHeight - paramInt2));
        requestLayout();
      }
      setMeasuredDimension(paramInt1, paramInt2);
    }
    evaluateLayout();
  }
  
  public boolean onNestedFling(View paramView, float paramFloat1, float paramFloat2, boolean paramBoolean)
  {
    return false;
  }
  
  public boolean onNestedPreFling(View paramView, float paramFloat1, float paramFloat2)
  {
    return false;
  }
  
  public void onNestedPreScroll(final View paramView, int paramInt1, int paramInt2, int[] paramArrayOfInt, int paramInt3)
  {
    MotionScene localMotionScene = this.mScene;
    if (localMotionScene == null) {
      return;
    }
    MotionScene.Transition localTransition = localMotionScene.mCurrentTransition;
    if ((localTransition != null) && (localTransition.isEnabled()))
    {
      TouchResponse localTouchResponse;
      if (localTransition.isEnabled())
      {
        localTouchResponse = localTransition.getTouchResponse();
        if (localTouchResponse != null)
        {
          paramInt3 = localTouchResponse.getTouchRegionId();
          if ((paramInt3 != -1) && (paramView.getId() != paramInt3)) {
            return;
          }
        }
      }
      if (localMotionScene.getMoveWhenScrollAtTop())
      {
        localTouchResponse = localTransition.getTouchResponse();
        int i = -1;
        paramInt3 = i;
        if (localTouchResponse != null)
        {
          paramInt3 = i;
          if ((localTouchResponse.getFlags() & 0x4) != 0) {
            paramInt3 = paramInt2;
          }
        }
        f1 = this.mTransitionPosition;
        if (((f1 == 1.0F) || (f1 == 0.0F)) && (paramView.canScrollVertically(paramInt3))) {
          return;
        }
      }
      if ((localTransition.getTouchResponse() != null) && ((localTransition.getTouchResponse().getFlags() & 0x1) != 0))
      {
        float f2 = localMotionScene.getProgressDirection(paramInt1, paramInt2);
        f1 = this.mTransitionLastPosition;
        if (((f1 <= 0.0F) && (f2 < 0.0F)) || ((f1 >= 1.0F) && (f2 > 0.0F)))
        {
          if (Build.VERSION.SDK_INT >= 21)
          {
            paramView.setNestedScrollingEnabled(false);
            paramView.post(new Runnable()
            {
              public void run()
              {
                paramView.setNestedScrollingEnabled(true);
              }
            });
          }
          return;
        }
      }
      float f1 = this.mTransitionPosition;
      long l = getNanoTime();
      this.mScrollTargetDX = paramInt1;
      this.mScrollTargetDY = paramInt2;
      this.mScrollTargetDT = ((float)((l - this.mScrollTargetTime) * 1.0E-9D));
      this.mScrollTargetTime = l;
      localMotionScene.processScrollMove(paramInt1, paramInt2);
      if (f1 != this.mTransitionPosition)
      {
        paramArrayOfInt[0] = paramInt1;
        paramArrayOfInt[1] = paramInt2;
      }
      evaluate(false);
      if ((paramArrayOfInt[0] != 0) || (paramArrayOfInt[1] != 0)) {
        this.mUndergoingMotion = true;
      }
      return;
    }
  }
  
  public void onNestedScroll(View paramView, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5) {}
  
  public void onNestedScroll(View paramView, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int[] paramArrayOfInt)
  {
    if ((this.mUndergoingMotion) || (paramInt1 != 0) || (paramInt2 != 0))
    {
      paramArrayOfInt[0] += paramInt3;
      paramArrayOfInt[1] += paramInt4;
    }
    this.mUndergoingMotion = false;
  }
  
  public void onNestedScrollAccepted(View paramView1, View paramView2, int paramInt1, int paramInt2)
  {
    this.mScrollTargetTime = getNanoTime();
    this.mScrollTargetDT = 0.0F;
    this.mScrollTargetDX = 0.0F;
    this.mScrollTargetDY = 0.0F;
  }
  
  void onNewStateAttachHandlers()
  {
    MotionScene localMotionScene = this.mScene;
    if (localMotionScene == null) {
      return;
    }
    if (localMotionScene.autoTransition(this, this.mCurrentState))
    {
      requestLayout();
      return;
    }
    int i = this.mCurrentState;
    if (i != -1) {
      this.mScene.addOnClickListeners(this, i);
    }
    if (this.mScene.supportTouch()) {
      this.mScene.setupTouch();
    }
  }
  
  public void onRtlPropertiesChanged(int paramInt)
  {
    MotionScene localMotionScene = this.mScene;
    if (localMotionScene != null) {
      localMotionScene.setRtl(isRtl());
    }
  }
  
  public boolean onStartNestedScroll(View paramView1, View paramView2, int paramInt1, int paramInt2)
  {
    paramView1 = this.mScene;
    return (paramView1 != null) && (paramView1.mCurrentTransition != null) && (this.mScene.mCurrentTransition.getTouchResponse() != null) && ((this.mScene.mCurrentTransition.getTouchResponse().getFlags() & 0x2) == 0);
  }
  
  public void onStopNestedScroll(View paramView, int paramInt)
  {
    paramView = this.mScene;
    if (paramView != null)
    {
      float f = this.mScrollTargetDT;
      if (f != 0.0F)
      {
        paramView.processScrollUp(this.mScrollTargetDX / f, this.mScrollTargetDY / f);
        return;
      }
    }
  }
  
  public boolean onTouchEvent(MotionEvent paramMotionEvent)
  {
    Object localObject = this.mScene;
    if ((localObject != null) && (this.mInteractionEnabled) && (((MotionScene)localObject).supportTouch()))
    {
      localObject = this.mScene.mCurrentTransition;
      if ((localObject != null) && (!((MotionScene.Transition)localObject).isEnabled())) {
        return super.onTouchEvent(paramMotionEvent);
      }
      this.mScene.processTouchEvent(paramMotionEvent, getCurrentState(), this);
      if (this.mScene.mCurrentTransition.isTransitionFlag(4)) {
        return this.mScene.mCurrentTransition.getTouchResponse().isDragStarted();
      }
      return true;
    }
    return super.onTouchEvent(paramMotionEvent);
  }
  
  public void onViewAdded(View paramView)
  {
    super.onViewAdded(paramView);
    if ((paramView instanceof MotionHelper))
    {
      paramView = (MotionHelper)paramView;
      if (this.mTransitionListeners == null) {
        this.mTransitionListeners = new CopyOnWriteArrayList();
      }
      this.mTransitionListeners.add(paramView);
      if (paramView.isUsedOnShow())
      {
        if (this.mOnShowHelpers == null) {
          this.mOnShowHelpers = new ArrayList();
        }
        this.mOnShowHelpers.add(paramView);
      }
      if (paramView.isUseOnHide())
      {
        if (this.mOnHideHelpers == null) {
          this.mOnHideHelpers = new ArrayList();
        }
        this.mOnHideHelpers.add(paramView);
      }
      if (paramView.isDecorator())
      {
        if (this.mDecoratorsHelpers == null) {
          this.mDecoratorsHelpers = new ArrayList();
        }
        this.mDecoratorsHelpers.add(paramView);
      }
    }
  }
  
  public void onViewRemoved(View paramView)
  {
    super.onViewRemoved(paramView);
    ArrayList localArrayList = this.mOnShowHelpers;
    if (localArrayList != null) {
      localArrayList.remove(paramView);
    }
    localArrayList = this.mOnHideHelpers;
    if (localArrayList != null) {
      localArrayList.remove(paramView);
    }
  }
  
  protected void parseLayoutDescription(int paramInt)
  {
    this.mConstraintLayoutSpec = null;
  }
  
  @Deprecated
  public void rebuildMotion()
  {
    Log.e("MotionLayout", "This method is deprecated. Please call rebuildScene() instead.");
    rebuildScene();
  }
  
  public void rebuildScene()
  {
    this.mModel.reEvaluateState();
    invalidate();
  }
  
  public boolean removeTransitionListener(TransitionListener paramTransitionListener)
  {
    CopyOnWriteArrayList localCopyOnWriteArrayList = this.mTransitionListeners;
    if (localCopyOnWriteArrayList == null) {
      return false;
    }
    return localCopyOnWriteArrayList.remove(paramTransitionListener);
  }
  
  public void requestLayout()
  {
    if ((!this.mMeasureDuringTransition) && (this.mCurrentState == -1))
    {
      Object localObject = this.mScene;
      if ((localObject != null) && (((MotionScene)localObject).mCurrentTransition != null))
      {
        int i = this.mScene.mCurrentTransition.getLayoutDuringTransition();
        if (i == 0) {
          return;
        }
        if (i == 2)
        {
          int j = getChildCount();
          for (i = 0; i < j; i++)
          {
            localObject = getChildAt(i);
            ((MotionController)this.mFrameArrayList.get(localObject)).remeasure();
          }
          return;
        }
      }
    }
    super.requestLayout();
  }
  
  public void rotateTo(int paramInt1, int paramInt2)
  {
    int i = 1;
    this.mInRotation = true;
    this.mPreRotateWidth = getWidth();
    this.mPreRotateHeight = getHeight();
    int j = getDisplay().getRotation();
    if ((j + 1) % 4 <= (this.mPreviouseRotation + 1) % 4) {
      i = 2;
    }
    this.mRotatMode = i;
    this.mPreviouseRotation = j;
    j = getChildCount();
    for (i = 0; i < j; i++)
    {
      View localView = getChildAt(i);
      ViewState localViewState2 = (ViewState)this.mPreRotate.get(localView);
      ViewState localViewState1 = localViewState2;
      if (localViewState2 == null)
      {
        localViewState1 = new ViewState();
        this.mPreRotate.put(localView, localViewState1);
      }
      localViewState1.getState(localView);
    }
    this.mBeginState = -1;
    this.mEndState = paramInt1;
    this.mScene.setTransition(-1, paramInt1);
    this.mModel.initFrom(this.mLayoutWidget, null, this.mScene.getConstraintSet(this.mEndState));
    this.mTransitionPosition = 0.0F;
    this.mTransitionLastPosition = 0.0F;
    invalidate();
    transitionToEnd(new Runnable()
    {
      public void run()
      {
        MotionLayout.access$302(MotionLayout.this, false);
      }
    });
    if (paramInt2 > 0) {
      this.mTransitionDuration = (paramInt2 / 1000.0F);
    }
  }
  
  public void scheduleTransitionTo(int paramInt)
  {
    if (getCurrentState() == -1)
    {
      transitionToState(paramInt);
    }
    else
    {
      int[] arrayOfInt = this.mScheduledTransitionTo;
      if (arrayOfInt == null) {
        this.mScheduledTransitionTo = new int[4];
      } else if (arrayOfInt.length <= this.mScheduledTransitions) {
        this.mScheduledTransitionTo = Arrays.copyOf(arrayOfInt, arrayOfInt.length * 2);
      }
      arrayOfInt = this.mScheduledTransitionTo;
      int i = this.mScheduledTransitions;
      this.mScheduledTransitions = (i + 1);
      arrayOfInt[i] = paramInt;
    }
  }
  
  public void setDebugMode(int paramInt)
  {
    this.mDebugPath = paramInt;
    invalidate();
  }
  
  public void setDelayedApplicationOfInitialState(boolean paramBoolean)
  {
    this.mDelayedApply = paramBoolean;
  }
  
  public void setInteractionEnabled(boolean paramBoolean)
  {
    this.mInteractionEnabled = paramBoolean;
  }
  
  public void setInterpolatedProgress(float paramFloat)
  {
    if (this.mScene != null)
    {
      setState(TransitionState.MOVING);
      Interpolator localInterpolator = this.mScene.getInterpolator();
      if (localInterpolator != null)
      {
        setProgress(localInterpolator.getInterpolation(paramFloat));
        return;
      }
    }
    setProgress(paramFloat);
  }
  
  public void setOnHide(float paramFloat)
  {
    ArrayList localArrayList = this.mOnHideHelpers;
    if (localArrayList != null)
    {
      int j = localArrayList.size();
      for (int i = 0; i < j; i++) {
        ((MotionHelper)this.mOnHideHelpers.get(i)).setProgress(paramFloat);
      }
    }
  }
  
  public void setOnShow(float paramFloat)
  {
    ArrayList localArrayList = this.mOnShowHelpers;
    if (localArrayList != null)
    {
      int j = localArrayList.size();
      for (int i = 0; i < j; i++) {
        ((MotionHelper)this.mOnShowHelpers.get(i)).setProgress(paramFloat);
      }
    }
  }
  
  public void setProgress(float paramFloat)
  {
    if ((paramFloat < 0.0F) || (paramFloat > 1.0F)) {
      Log.w("MotionLayout", "Warning! Progress is defined for values between 0.0 and 1.0 inclusive");
    }
    if (!isAttachedToWindow())
    {
      if (this.mStateCache == null) {
        this.mStateCache = new StateCache();
      }
      this.mStateCache.setProgress(paramFloat);
      return;
    }
    if (paramFloat <= 0.0F)
    {
      if ((this.mTransitionLastPosition == 1.0F) && (this.mCurrentState == this.mEndState)) {
        setState(TransitionState.MOVING);
      }
      this.mCurrentState = this.mBeginState;
      if (this.mTransitionLastPosition == 0.0F) {
        setState(TransitionState.FINISHED);
      }
    }
    else if (paramFloat >= 1.0F)
    {
      if ((this.mTransitionLastPosition == 0.0F) && (this.mCurrentState == this.mBeginState)) {
        setState(TransitionState.MOVING);
      }
      this.mCurrentState = this.mEndState;
      if (this.mTransitionLastPosition == 1.0F) {
        setState(TransitionState.FINISHED);
      }
    }
    else
    {
      this.mCurrentState = -1;
      setState(TransitionState.MOVING);
    }
    if (this.mScene == null) {
      return;
    }
    this.mTransitionInstantly = true;
    this.mTransitionGoalPosition = paramFloat;
    this.mTransitionPosition = paramFloat;
    this.mTransitionLastTime = -1L;
    this.mAnimationStartTime = -1L;
    this.mInterpolator = null;
    this.mInTransition = true;
    invalidate();
  }
  
  public void setProgress(float paramFloat1, float paramFloat2)
  {
    if (!isAttachedToWindow())
    {
      if (this.mStateCache == null) {
        this.mStateCache = new StateCache();
      }
      this.mStateCache.setProgress(paramFloat1);
      this.mStateCache.setVelocity(paramFloat2);
      return;
    }
    setProgress(paramFloat1);
    setState(TransitionState.MOVING);
    this.mLastVelocity = paramFloat2;
    float f1 = 0.0F;
    float f2 = 0.0F;
    if (paramFloat2 != 0.0F)
    {
      paramFloat1 = f2;
      if (paramFloat2 > 0.0F) {
        paramFloat1 = 1.0F;
      }
      animateTo(paramFloat1);
    }
    else if ((paramFloat1 != 0.0F) && (paramFloat1 != 1.0F))
    {
      paramFloat2 = f1;
      if (paramFloat1 > 0.5F) {
        paramFloat2 = 1.0F;
      }
      animateTo(paramFloat2);
    }
  }
  
  public void setScene(MotionScene paramMotionScene)
  {
    this.mScene = paramMotionScene;
    paramMotionScene.setRtl(isRtl());
    rebuildScene();
  }
  
  void setStartState(int paramInt)
  {
    if (!isAttachedToWindow())
    {
      if (this.mStateCache == null) {
        this.mStateCache = new StateCache();
      }
      this.mStateCache.setStartState(paramInt);
      this.mStateCache.setEndState(paramInt);
      return;
    }
    this.mCurrentState = paramInt;
  }
  
  public void setState(int paramInt1, int paramInt2, int paramInt3)
  {
    setState(TransitionState.SETUP);
    this.mCurrentState = paramInt1;
    this.mBeginState = -1;
    this.mEndState = -1;
    if (this.mConstraintLayoutSpec != null)
    {
      this.mConstraintLayoutSpec.updateConstraints(paramInt1, paramInt2, paramInt3);
    }
    else
    {
      MotionScene localMotionScene = this.mScene;
      if (localMotionScene != null) {
        localMotionScene.getConstraintSet(paramInt1).applyTo(this);
      }
    }
  }
  
  void setState(TransitionState paramTransitionState)
  {
    if ((paramTransitionState == TransitionState.FINISHED) && (this.mCurrentState == -1)) {
      return;
    }
    TransitionState localTransitionState = this.mTransitionState;
    this.mTransitionState = paramTransitionState;
    if ((localTransitionState == TransitionState.MOVING) && (paramTransitionState == TransitionState.MOVING)) {
      fireTransitionChange();
    }
    switch (5.$SwitchMap$androidx$constraintlayout$motion$widget$MotionLayout$TransitionState[localTransitionState.ordinal()])
    {
    default: 
      break;
    case 3: 
      if (paramTransitionState == TransitionState.FINISHED) {
        fireTransitionCompleted();
      }
      break;
    case 1: 
    case 2: 
      if (paramTransitionState == TransitionState.MOVING) {
        fireTransitionChange();
      }
      if (paramTransitionState == TransitionState.FINISHED) {
        fireTransitionCompleted();
      }
      break;
    }
  }
  
  public void setTransition(int paramInt)
  {
    if (this.mScene != null)
    {
      Object localObject = getTransition(paramInt);
      paramInt = this.mCurrentState;
      this.mBeginState = ((MotionScene.Transition)localObject).getStartConstraintSetId();
      this.mEndState = ((MotionScene.Transition)localObject).getEndConstraintSetId();
      if (!isAttachedToWindow())
      {
        if (this.mStateCache == null) {
          this.mStateCache = new StateCache();
        }
        this.mStateCache.setStartState(this.mBeginState);
        this.mStateCache.setEndState(this.mEndState);
        return;
      }
      float f1 = NaN.0F;
      paramInt = this.mCurrentState;
      if (paramInt == this.mBeginState) {
        f1 = 0.0F;
      } else if (paramInt == this.mEndState) {
        f1 = 1.0F;
      }
      this.mScene.setTransition((MotionScene.Transition)localObject);
      this.mModel.initFrom(this.mLayoutWidget, this.mScene.getConstraintSet(this.mBeginState), this.mScene.getConstraintSet(this.mEndState));
      rebuildScene();
      float f3 = this.mTransitionLastPosition;
      float f2 = 0.0F;
      if (f3 != f1) {
        if (f1 == 0.0F)
        {
          endTrigger(true);
          this.mScene.getConstraintSet(this.mBeginState).applyTo(this);
        }
        else if (f1 == 1.0F)
        {
          endTrigger(false);
          this.mScene.getConstraintSet(this.mEndState).applyTo(this);
        }
      }
      if (!Float.isNaN(f1)) {
        f2 = f1;
      }
      this.mTransitionLastPosition = f2;
      if (Float.isNaN(f1))
      {
        StringBuilder localStringBuilder = new StringBuilder();
        localObject = Debug.getLocation();
        Log5ECF72.a(localObject);
        LogE84000.a(localObject);
        Log229316.a(localObject);
        Log.v("MotionLayout", (String)localObject + " transitionToStart ");
        transitionToStart();
      }
      else
      {
        setProgress(f1);
      }
    }
  }
  
  public void setTransition(int paramInt1, int paramInt2)
  {
    if (!isAttachedToWindow())
    {
      if (this.mStateCache == null) {
        this.mStateCache = new StateCache();
      }
      this.mStateCache.setStartState(paramInt1);
      this.mStateCache.setEndState(paramInt2);
      return;
    }
    MotionScene localMotionScene = this.mScene;
    if (localMotionScene != null)
    {
      this.mBeginState = paramInt1;
      this.mEndState = paramInt2;
      localMotionScene.setTransition(paramInt1, paramInt2);
      this.mModel.initFrom(this.mLayoutWidget, this.mScene.getConstraintSet(paramInt1), this.mScene.getConstraintSet(paramInt2));
      rebuildScene();
      this.mTransitionLastPosition = 0.0F;
      transitionToStart();
    }
  }
  
  protected void setTransition(MotionScene.Transition paramTransition)
  {
    this.mScene.setTransition(paramTransition);
    setState(TransitionState.SETUP);
    if (this.mCurrentState == this.mScene.getEndId())
    {
      this.mTransitionLastPosition = 1.0F;
      this.mTransitionPosition = 1.0F;
      this.mTransitionGoalPosition = 1.0F;
    }
    else
    {
      this.mTransitionLastPosition = 0.0F;
      this.mTransitionPosition = 0.0F;
      this.mTransitionGoalPosition = 0.0F;
    }
    long l;
    if (paramTransition.isTransitionFlag(1)) {
      l = -1L;
    } else {
      l = getNanoTime();
    }
    this.mTransitionLastTime = l;
    int i = this.mScene.getStartId();
    int j = this.mScene.getEndId();
    if ((i == this.mBeginState) && (j == this.mEndState)) {
      return;
    }
    this.mBeginState = i;
    this.mEndState = j;
    this.mScene.setTransition(i, j);
    this.mModel.initFrom(this.mLayoutWidget, this.mScene.getConstraintSet(this.mBeginState), this.mScene.getConstraintSet(this.mEndState));
    this.mModel.setMeasuredId(this.mBeginState, this.mEndState);
    this.mModel.reEvaluateState();
    rebuildScene();
  }
  
  public void setTransitionDuration(int paramInt)
  {
    MotionScene localMotionScene = this.mScene;
    if (localMotionScene == null)
    {
      Log.e("MotionLayout", "MotionScene not defined");
      return;
    }
    localMotionScene.setDuration(paramInt);
  }
  
  public void setTransitionListener(TransitionListener paramTransitionListener)
  {
    this.mTransitionListener = paramTransitionListener;
  }
  
  public void setTransitionState(Bundle paramBundle)
  {
    if (this.mStateCache == null) {
      this.mStateCache = new StateCache();
    }
    this.mStateCache.setTransitionState(paramBundle);
    if (isAttachedToWindow()) {
      this.mStateCache.apply();
    }
  }
  
  public String toString()
  {
    Object localObject = getContext();
    StringBuilder localStringBuilder = new StringBuilder();
    String str = Debug.getName((Context)localObject, this.mBeginState);
    Log5ECF72.a(str);
    LogE84000.a(str);
    Log229316.a(str);
    localStringBuilder = localStringBuilder.append(str).append("->");
    localObject = Debug.getName((Context)localObject, this.mEndState);
    Log5ECF72.a(localObject);
    LogE84000.a(localObject);
    Log229316.a(localObject);
    return (String)localObject + " (pos:" + this.mTransitionLastPosition + " Dpos/Dt:" + this.mLastVelocity;
  }
  
  public void touchAnimateTo(int paramInt, float paramFloat1, float paramFloat2)
  {
    if (this.mScene == null) {
      return;
    }
    if (this.mTransitionLastPosition == paramFloat1) {
      return;
    }
    this.mTemporalInterpolator = true;
    this.mAnimationStartTime = getNanoTime();
    this.mTransitionDuration = (this.mScene.getDuration() / 1000.0F);
    this.mTransitionGoalPosition = paramFloat1;
    this.mInTransition = true;
    switch (paramInt)
    {
    default: 
      break;
    case 5: 
      if (willJump(paramFloat2, this.mTransitionLastPosition, this.mScene.getMaxAcceleration()))
      {
        this.mDecelerateLogic.config(paramFloat2, this.mTransitionLastPosition, this.mScene.getMaxAcceleration());
        this.mInterpolator = this.mDecelerateLogic;
      }
      else
      {
        this.mStopLogic.config(this.mTransitionLastPosition, paramFloat1, paramFloat2, this.mTransitionDuration, this.mScene.getMaxAcceleration(), this.mScene.getMaxVelocity());
        this.mLastVelocity = 0.0F;
        paramInt = this.mCurrentState;
        this.mTransitionGoalPosition = paramFloat1;
        this.mCurrentState = paramInt;
        this.mInterpolator = this.mStopLogic;
      }
      break;
    case 4: 
      this.mDecelerateLogic.config(paramFloat2, this.mTransitionLastPosition, this.mScene.getMaxAcceleration());
      this.mInterpolator = this.mDecelerateLogic;
      break;
    case 3: 
      break;
    case 0: 
    case 1: 
    case 2: 
    case 6: 
    case 7: 
      if ((paramInt != 1) && (paramInt != 7))
      {
        if ((paramInt == 2) || (paramInt == 6)) {
          paramFloat1 = 1.0F;
        }
      }
      else {
        paramFloat1 = 0.0F;
      }
      if (this.mScene.getAutoCompleteMode() == 0) {
        this.mStopLogic.config(this.mTransitionLastPosition, paramFloat1, paramFloat2, this.mTransitionDuration, this.mScene.getMaxAcceleration(), this.mScene.getMaxVelocity());
      } else {
        this.mStopLogic.springConfig(this.mTransitionLastPosition, paramFloat1, paramFloat2, this.mScene.getSpringMass(), this.mScene.getSpringStiffiness(), this.mScene.getSpringDamping(), this.mScene.getSpringStopThreshold(), this.mScene.getSpringBoundary());
      }
      paramInt = this.mCurrentState;
      this.mTransitionGoalPosition = paramFloat1;
      this.mCurrentState = paramInt;
      this.mInterpolator = this.mStopLogic;
    }
    this.mTransitionInstantly = false;
    this.mAnimationStartTime = getNanoTime();
    invalidate();
  }
  
  public void touchSpringTo(float paramFloat1, float paramFloat2)
  {
    if (this.mScene == null) {
      return;
    }
    if (this.mTransitionLastPosition == paramFloat1) {
      return;
    }
    this.mTemporalInterpolator = true;
    this.mAnimationStartTime = getNanoTime();
    this.mTransitionDuration = (this.mScene.getDuration() / 1000.0F);
    this.mTransitionGoalPosition = paramFloat1;
    this.mInTransition = true;
    this.mStopLogic.springConfig(this.mTransitionLastPosition, paramFloat1, paramFloat2, this.mScene.getSpringMass(), this.mScene.getSpringStiffiness(), this.mScene.getSpringDamping(), this.mScene.getSpringStopThreshold(), this.mScene.getSpringBoundary());
    int i = this.mCurrentState;
    this.mTransitionGoalPosition = paramFloat1;
    this.mCurrentState = i;
    this.mInterpolator = this.mStopLogic;
    this.mTransitionInstantly = false;
    this.mAnimationStartTime = getNanoTime();
    invalidate();
  }
  
  public void transitionToEnd()
  {
    animateTo(1.0F);
    this.mOnComplete = null;
  }
  
  public void transitionToEnd(Runnable paramRunnable)
  {
    animateTo(1.0F);
    this.mOnComplete = paramRunnable;
  }
  
  public void transitionToStart()
  {
    animateTo(0.0F);
  }
  
  public void transitionToState(int paramInt)
  {
    if (!isAttachedToWindow())
    {
      if (this.mStateCache == null) {
        this.mStateCache = new StateCache();
      }
      this.mStateCache.setEndState(paramInt);
      return;
    }
    transitionToState(paramInt, -1, -1);
  }
  
  public void transitionToState(int paramInt1, int paramInt2)
  {
    if (!isAttachedToWindow())
    {
      if (this.mStateCache == null) {
        this.mStateCache = new StateCache();
      }
      this.mStateCache.setEndState(paramInt1);
      return;
    }
    transitionToState(paramInt1, -1, -1, paramInt2);
  }
  
  public void transitionToState(int paramInt1, int paramInt2, int paramInt3)
  {
    transitionToState(paramInt1, paramInt2, paramInt3, -1);
  }
  
  public void transitionToState(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    Object localObject = this.mScene;
    if ((localObject != null) && (((MotionScene)localObject).mStateSet != null))
    {
      paramInt2 = this.mScene.mStateSet.convertToConstraintSet(this.mCurrentState, paramInt1, paramInt2, paramInt3);
      if (paramInt2 != -1) {
        paramInt1 = paramInt2;
      }
    }
    paramInt2 = this.mCurrentState;
    if (paramInt2 == paramInt1) {
      return;
    }
    if (this.mBeginState == paramInt1)
    {
      animateTo(0.0F);
      if (paramInt4 > 0) {
        this.mTransitionDuration = (paramInt4 / 1000.0F);
      }
      return;
    }
    if (this.mEndState == paramInt1)
    {
      animateTo(1.0F);
      if (paramInt4 > 0) {
        this.mTransitionDuration = (paramInt4 / 1000.0F);
      }
      return;
    }
    this.mEndState = paramInt1;
    if (paramInt2 != -1)
    {
      setTransition(paramInt2, paramInt1);
      animateTo(1.0F);
      this.mTransitionLastPosition = 0.0F;
      transitionToEnd();
      if (paramInt4 > 0) {
        this.mTransitionDuration = (paramInt4 / 1000.0F);
      }
      return;
    }
    this.mTemporalInterpolator = false;
    this.mTransitionGoalPosition = 1.0F;
    this.mTransitionPosition = 0.0F;
    this.mTransitionLastPosition = 0.0F;
    this.mTransitionLastTime = getNanoTime();
    this.mAnimationStartTime = getNanoTime();
    this.mTransitionInstantly = false;
    this.mInterpolator = null;
    if (paramInt4 == -1) {
      this.mTransitionDuration = (this.mScene.getDuration() / 1000.0F);
    }
    this.mBeginState = -1;
    this.mScene.setTransition(-1, this.mEndState);
    SparseArray localSparseArray = new SparseArray();
    if (paramInt4 == 0) {
      this.mTransitionDuration = (this.mScene.getDuration() / 1000.0F);
    } else if (paramInt4 > 0) {
      this.mTransitionDuration = (paramInt4 / 1000.0F);
    }
    paramInt3 = getChildCount();
    this.mFrameArrayList.clear();
    for (paramInt2 = 0; paramInt2 < paramInt3; paramInt2++)
    {
      localObject = getChildAt(paramInt2);
      MotionController localMotionController = new MotionController((View)localObject);
      this.mFrameArrayList.put(localObject, localMotionController);
      localSparseArray.put(((View)localObject).getId(), (MotionController)this.mFrameArrayList.get(localObject));
    }
    this.mInTransition = true;
    this.mModel.initFrom(this.mLayoutWidget, null, this.mScene.getConstraintSet(paramInt1));
    rebuildScene();
    this.mModel.build();
    computeCurrentPositions();
    paramInt2 = getWidth();
    paramInt4 = getHeight();
    if (this.mDecoratorsHelpers != null)
    {
      for (paramInt1 = 0; paramInt1 < paramInt3; paramInt1++)
      {
        localObject = (MotionController)this.mFrameArrayList.get(getChildAt(paramInt1));
        if (localObject != null) {
          this.mScene.getKeyFrames((MotionController)localObject);
        }
      }
      localObject = this.mDecoratorsHelpers.iterator();
      while (((Iterator)localObject).hasNext()) {
        ((MotionHelper)((Iterator)localObject).next()).onPreSetup(this, this.mFrameArrayList);
      }
      for (paramInt1 = 0; paramInt1 < paramInt3; paramInt1++)
      {
        localObject = (MotionController)this.mFrameArrayList.get(getChildAt(paramInt1));
        if (localObject != null) {
          ((MotionController)localObject).setup(paramInt2, paramInt4, this.mTransitionDuration, getNanoTime());
        }
      }
    }
    else
    {
      for (paramInt1 = 0; paramInt1 < paramInt3; paramInt1++)
      {
        localObject = (MotionController)this.mFrameArrayList.get(getChildAt(paramInt1));
        if (localObject != null)
        {
          this.mScene.getKeyFrames((MotionController)localObject);
          ((MotionController)localObject).setup(paramInt2, paramInt4, this.mTransitionDuration, getNanoTime());
        }
      }
    }
    float f3 = this.mScene.getStaggered();
    if (f3 != 0.0F)
    {
      float f2 = Float.MAX_VALUE;
      float f1 = -3.4028235E38F;
      float f5;
      float f4;
      for (paramInt1 = 0; paramInt1 < paramInt3; paramInt1++)
      {
        localObject = (MotionController)this.mFrameArrayList.get(getChildAt(paramInt1));
        f5 = ((MotionController)localObject).getFinalX();
        f4 = ((MotionController)localObject).getFinalY();
        f2 = Math.min(f2, f4 + f5);
        f1 = Math.max(f1, f4 + f5);
      }
      for (paramInt1 = 0; paramInt1 < paramInt3; paramInt1++)
      {
        localObject = (MotionController)this.mFrameArrayList.get(getChildAt(paramInt1));
        f5 = ((MotionController)localObject).getFinalX();
        f4 = ((MotionController)localObject).getFinalY();
        ((MotionController)localObject).mStaggerScale = (1.0F / (1.0F - f3));
        ((MotionController)localObject).mStaggerOffset = (f3 - (f5 + f4 - f2) * f3 / (f1 - f2));
      }
    }
    this.mTransitionPosition = 0.0F;
    this.mTransitionLastPosition = 0.0F;
    this.mInTransition = true;
    invalidate();
  }
  
  public void updateState()
  {
    this.mModel.initFrom(this.mLayoutWidget, this.mScene.getConstraintSet(this.mBeginState), this.mScene.getConstraintSet(this.mEndState));
    rebuildScene();
  }
  
  public void updateState(int paramInt, ConstraintSet paramConstraintSet)
  {
    MotionScene localMotionScene = this.mScene;
    if (localMotionScene != null) {
      localMotionScene.setConstraintSet(paramInt, paramConstraintSet);
    }
    updateState();
    if (this.mCurrentState == paramInt) {
      paramConstraintSet.applyTo(this);
    }
  }
  
  public void updateStateAnimate(int paramInt1, ConstraintSet paramConstraintSet, int paramInt2)
  {
    if (this.mScene == null) {
      return;
    }
    if (this.mCurrentState == paramInt1)
    {
      updateState(R.id.view_transition, getConstraintSet(paramInt1));
      setState(R.id.view_transition, -1, -1);
      updateState(paramInt1, paramConstraintSet);
      paramConstraintSet = new MotionScene.Transition(-1, this.mScene, R.id.view_transition, paramInt1);
      paramConstraintSet.setDuration(paramInt2);
      setTransition(paramConstraintSet);
      transitionToEnd();
    }
  }
  
  public void viewTransition(int paramInt, View... paramVarArgs)
  {
    MotionScene localMotionScene = this.mScene;
    if (localMotionScene != null) {
      localMotionScene.viewTransition(paramInt, paramVarArgs);
    } else {
      Log.e("MotionLayout", " no motionScene");
    }
  }
  
  class DecelerateInterpolator
    extends MotionInterpolator
  {
    float currentP = 0.0F;
    float initalV = 0.0F;
    float maxA;
    
    DecelerateInterpolator() {}
    
    public void config(float paramFloat1, float paramFloat2, float paramFloat3)
    {
      this.initalV = paramFloat1;
      this.currentP = paramFloat2;
      this.maxA = paramFloat3;
    }
    
    public float getInterpolation(float paramFloat)
    {
      float f2 = this.initalV;
      if (f2 > 0.0F)
      {
        f3 = this.maxA;
        f1 = paramFloat;
        if (f2 / f3 < paramFloat) {
          f1 = f2 / f3;
        }
        MotionLayout.this.mLastVelocity = (f2 - f3 * f1);
        paramFloat = this.initalV;
        f2 = this.maxA * f1 * f1 / 2.0F;
        return this.currentP + (paramFloat * f1 - f2);
      }
      float f4 = -f2;
      float f3 = this.maxA;
      float f1 = paramFloat;
      if (f4 / f3 < paramFloat) {
        f1 = -f2 / f3;
      }
      MotionLayout.this.mLastVelocity = (f2 + f3 * f1);
      paramFloat = this.initalV;
      f2 = this.maxA * f1 * f1 / 2.0F;
      return this.currentP + (paramFloat * f1 + f2);
    }
    
    public float getVelocity()
    {
      return MotionLayout.this.mLastVelocity;
    }
  }
  
  private class DevModeDraw
  {
    private static final int DEBUG_PATH_TICKS_PER_MS = 16;
    final int DIAMOND_SIZE = 10;
    final int GRAPH_COLOR = -13391360;
    final int KEYFRAME_COLOR = -2067046;
    final int RED_COLOR = 43571;
    final int SHADOW_COLOR = 1996488704;
    Rect mBounds = new Rect();
    DashPathEffect mDashPathEffect;
    Paint mFillPaint;
    int mKeyFrameCount;
    float[] mKeyFramePoints;
    Paint mPaint;
    Paint mPaintGraph;
    Paint mPaintKeyframes;
    Path mPath;
    int[] mPathMode;
    float[] mPoints;
    boolean mPresentationMode = false;
    private float[] mRectangle;
    int mShadowTranslate = 1;
    Paint mTextPaint;
    
    public DevModeDraw()
    {
      Paint localPaint = new Paint();
      this.mPaint = localPaint;
      localPaint.setAntiAlias(true);
      this.mPaint.setColor(43571);
      this.mPaint.setStrokeWidth(2.0F);
      this.mPaint.setStyle(Paint.Style.STROKE);
      localPaint = new Paint();
      this.mPaintKeyframes = localPaint;
      localPaint.setAntiAlias(true);
      this.mPaintKeyframes.setColor(-2067046);
      this.mPaintKeyframes.setStrokeWidth(2.0F);
      this.mPaintKeyframes.setStyle(Paint.Style.STROKE);
      localPaint = new Paint();
      this.mPaintGraph = localPaint;
      localPaint.setAntiAlias(true);
      this.mPaintGraph.setColor(-13391360);
      this.mPaintGraph.setStrokeWidth(2.0F);
      this.mPaintGraph.setStyle(Paint.Style.STROKE);
      localPaint = new Paint();
      this.mTextPaint = localPaint;
      localPaint.setAntiAlias(true);
      this.mTextPaint.setColor(-13391360);
      this.mTextPaint.setTextSize(MotionLayout.this.getContext().getResources().getDisplayMetrics().density * 12.0F);
      this.mRectangle = new float[8];
      this$1 = new Paint();
      this.mFillPaint = MotionLayout.this;
      MotionLayout.this.setAntiAlias(true);
      this$1 = new DashPathEffect(new float[] { 4.0F, 8.0F }, 0.0F);
      this.mDashPathEffect = MotionLayout.this;
      this.mPaintGraph.setPathEffect(MotionLayout.this);
      this.mKeyFramePoints = new float[100];
      this.mPathMode = new int[50];
      if (this.mPresentationMode)
      {
        this.mPaint.setStrokeWidth(8.0F);
        this.mFillPaint.setStrokeWidth(8.0F);
        this.mPaintKeyframes.setStrokeWidth(8.0F);
        this.mShadowTranslate = 4;
      }
    }
    
    private void drawBasicPath(Canvas paramCanvas)
    {
      paramCanvas.drawLines(this.mPoints, this.mPaint);
    }
    
    private void drawPathAsConfigured(Canvas paramCanvas)
    {
      int k = 0;
      int j = 0;
      for (int i = 0; i < this.mKeyFrameCount; i++)
      {
        int m = this.mPathMode[i];
        if (m == 1) {
          k = 1;
        }
        if (m == 0) {
          j = 1;
        }
      }
      if (k != 0) {
        drawPathRelative(paramCanvas);
      }
      if (j != 0) {
        drawPathCartesian(paramCanvas);
      }
    }
    
    private void drawPathCartesian(Canvas paramCanvas)
    {
      float[] arrayOfFloat = this.mPoints;
      float f1 = arrayOfFloat[0];
      float f2 = arrayOfFloat[1];
      float f4 = arrayOfFloat[(arrayOfFloat.length - 2)];
      float f3 = arrayOfFloat[(arrayOfFloat.length - 1)];
      paramCanvas.drawLine(Math.min(f1, f4), Math.max(f2, f3), Math.max(f1, f4), Math.max(f2, f3), this.mPaintGraph);
      paramCanvas.drawLine(Math.min(f1, f4), Math.min(f2, f3), Math.min(f1, f4), Math.max(f2, f3), this.mPaintGraph);
    }
    
    private void drawPathCartesianTicks(Canvas paramCanvas, float paramFloat1, float paramFloat2)
    {
      Object localObject = this.mPoints;
      float f1 = localObject[0];
      float f7 = localObject[1];
      float f5 = localObject[(localObject.length - 2)];
      float f2 = localObject[(localObject.length - 1)];
      float f4 = Math.min(f1, f5);
      float f6 = Math.max(f7, f2);
      float f3 = paramFloat1 - Math.min(f1, f5);
      float f8 = Math.max(f7, f2) - paramFloat2;
      localObject = "" + (int)(f3 * 100.0F / Math.abs(f5 - f1) + 0.5D) / 100.0F;
      getTextBounds((String)localObject, this.mTextPaint);
      paramCanvas.drawText((String)localObject, f3 / 2.0F - this.mBounds.width() / 2 + f4, paramFloat2 - 20.0F, this.mTextPaint);
      paramCanvas.drawLine(paramFloat1, paramFloat2, Math.min(f1, f5), paramFloat2, this.mPaintGraph);
      localObject = "" + (int)(f8 * 100.0F / Math.abs(f2 - f7) + 0.5D) / 100.0F;
      getTextBounds((String)localObject, this.mTextPaint);
      paramCanvas.drawText((String)localObject, paramFloat1 + 5.0F, f6 - (f8 / 2.0F - this.mBounds.height() / 2), this.mTextPaint);
      paramCanvas.drawLine(paramFloat1, paramFloat2, paramFloat1, Math.max(f7, f2), this.mPaintGraph);
    }
    
    private void drawPathRelative(Canvas paramCanvas)
    {
      float[] arrayOfFloat = this.mPoints;
      paramCanvas.drawLine(arrayOfFloat[0], arrayOfFloat[1], arrayOfFloat[(arrayOfFloat.length - 2)], arrayOfFloat[(arrayOfFloat.length - 1)], this.mPaintGraph);
    }
    
    private void drawPathRelativeTicks(Canvas paramCanvas, float paramFloat1, float paramFloat2)
    {
      Object localObject = this.mPoints;
      float f3 = localObject[0];
      float f5 = localObject[1];
      float f6 = localObject[(localObject.length - 2)];
      float f2 = localObject[(localObject.length - 1)];
      float f1 = (float)Math.hypot(f3 - f6, f5 - f2);
      float f4 = ((paramFloat1 - f3) * (f6 - f3) + (paramFloat2 - f5) * (f2 - f5)) / (f1 * f1);
      f3 += (f6 - f3) * f4;
      f4 = f5 + (f2 - f5) * f4;
      localObject = new Path();
      ((Path)localObject).moveTo(paramFloat1, paramFloat2);
      ((Path)localObject).lineTo(f3, f4);
      f2 = (float)Math.hypot(f3 - paramFloat1, f4 - paramFloat2);
      String str = "" + (int)(f2 * 100.0F / f1) / 100.0F;
      getTextBounds(str, this.mTextPaint);
      paramCanvas.drawTextOnPath(str, (Path)localObject, f2 / 2.0F - this.mBounds.width() / 2, -20.0F, this.mTextPaint);
      paramCanvas.drawLine(paramFloat1, paramFloat2, f3, f4, this.mPaintGraph);
    }
    
    private void drawPathScreenTicks(Canvas paramCanvas, float paramFloat1, float paramFloat2, int paramInt1, int paramInt2)
    {
      String str = "" + (int)((paramFloat1 - paramInt1 / 2) * 100.0F / (MotionLayout.this.getWidth() - paramInt1) + 0.5D) / 100.0F;
      getTextBounds(str, this.mTextPaint);
      paramCanvas.drawText(str, paramFloat1 / 2.0F - this.mBounds.width() / 2 + 0.0F, paramFloat2 - 20.0F, this.mTextPaint);
      paramCanvas.drawLine(paramFloat1, paramFloat2, Math.min(0.0F, 1.0F), paramFloat2, this.mPaintGraph);
      str = "" + (int)((paramFloat2 - paramInt2 / 2) * 100.0F / (MotionLayout.this.getHeight() - paramInt2) + 0.5D) / 100.0F;
      getTextBounds(str, this.mTextPaint);
      paramCanvas.drawText(str, paramFloat1 + 5.0F, 0.0F - (paramFloat2 / 2.0F - this.mBounds.height() / 2), this.mTextPaint);
      paramCanvas.drawLine(paramFloat1, paramFloat2, paramFloat1, Math.max(0.0F, 1.0F), this.mPaintGraph);
    }
    
    private void drawRectangle(Canvas paramCanvas, MotionController paramMotionController)
    {
      this.mPath.reset();
      for (int i = 0; i <= 50; i++)
      {
        paramMotionController.buildRect(i / 50, this.mRectangle, 0);
        Object localObject2 = this.mPath;
        Object localObject1 = this.mRectangle;
        ((Path)localObject2).moveTo(localObject1[0], localObject1[1]);
        localObject1 = this.mPath;
        localObject2 = this.mRectangle;
        ((Path)localObject1).lineTo(localObject2[2], localObject2[3]);
        localObject2 = this.mPath;
        localObject1 = this.mRectangle;
        ((Path)localObject2).lineTo(localObject1[4], localObject1[5]);
        localObject2 = this.mPath;
        localObject1 = this.mRectangle;
        ((Path)localObject2).lineTo(localObject1[6], localObject1[7]);
        this.mPath.close();
      }
      this.mPaint.setColor(1140850688);
      paramCanvas.translate(2.0F, 2.0F);
      paramCanvas.drawPath(this.mPath, this.mPaint);
      paramCanvas.translate(-2.0F, -2.0F);
      this.mPaint.setColor(-65536);
      paramCanvas.drawPath(this.mPath, this.mPaint);
    }
    
    private void drawTicks(Canvas paramCanvas, int paramInt1, int paramInt2, MotionController paramMotionController)
    {
      int i;
      int j;
      if (paramMotionController.mView != null)
      {
        i = paramMotionController.mView.getWidth();
        j = paramMotionController.mView.getHeight();
      }
      else
      {
        i = 0;
        j = 0;
      }
      for (int k = 1; k < paramInt2 - 1; k++) {
        if ((paramInt1 != 4) || (this.mPathMode[(k - 1)] != 0))
        {
          Object localObject = this.mKeyFramePoints;
          float f1 = localObject[(k * 2)];
          float f2 = localObject[(k * 2 + 1)];
          this.mPath.reset();
          this.mPath.moveTo(f1, f2 + 10.0F);
          this.mPath.lineTo(f1 + 10.0F, f2);
          this.mPath.lineTo(f1, f2 - 10.0F);
          this.mPath.lineTo(f1 - 10.0F, f2);
          this.mPath.close();
          paramMotionController.getKeyFrame(k - 1);
          if (paramInt1 == 4)
          {
            localObject = this.mPathMode;
            if (localObject[(k - 1)] == 1) {
              drawPathRelativeTicks(paramCanvas, f1 - 0.0F, f2 - 0.0F);
            } else if (localObject[(k - 1)] == 0) {
              drawPathCartesianTicks(paramCanvas, f1 - 0.0F, f2 - 0.0F);
            } else if (localObject[(k - 1)] == 2) {
              drawPathScreenTicks(paramCanvas, f1 - 0.0F, f2 - 0.0F, i, j);
            }
            paramCanvas.drawPath(this.mPath, this.mFillPaint);
          }
          if (paramInt1 == 2) {
            drawPathRelativeTicks(paramCanvas, f1 - 0.0F, f2 - 0.0F);
          }
          if (paramInt1 == 3) {
            drawPathCartesianTicks(paramCanvas, f1 - 0.0F, f2 - 0.0F);
          }
          if (paramInt1 == 6) {
            drawPathScreenTicks(paramCanvas, f1 - 0.0F, f2 - 0.0F, i, j);
          }
          if ((0.0F == 0.0F) && (0.0F == 0.0F)) {
            paramCanvas.drawPath(this.mPath, this.mFillPaint);
          } else {
            drawTranslation(paramCanvas, f1 - 0.0F, f2 - 0.0F, f1, f2);
          }
        }
      }
      paramMotionController = this.mPoints;
      if (paramMotionController.length > 1)
      {
        paramCanvas.drawCircle(paramMotionController[0], paramMotionController[1], 8.0F, this.mPaintKeyframes);
        paramMotionController = this.mPoints;
        paramCanvas.drawCircle(paramMotionController[(paramMotionController.length - 2)], paramMotionController[(paramMotionController.length - 1)], 8.0F, this.mPaintKeyframes);
      }
    }
    
    private void drawTranslation(Canvas paramCanvas, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
    {
      paramCanvas.drawRect(paramFloat1, paramFloat2, paramFloat3, paramFloat4, this.mPaintGraph);
      paramCanvas.drawLine(paramFloat1, paramFloat2, paramFloat3, paramFloat4, this.mPaintGraph);
    }
    
    public void draw(Canvas paramCanvas, HashMap<View, MotionController> paramHashMap, int paramInt1, int paramInt2)
    {
      if ((paramHashMap != null) && (paramHashMap.size() != 0))
      {
        paramCanvas.save();
        Object localObject;
        if ((!MotionLayout.this.isInEditMode()) && ((paramInt2 & 0x1) == 2))
        {
          localObject = MotionLayout.this.getContext().getResources().getResourceName(MotionLayout.this.mEndState) + ":" + MotionLayout.this.getProgress();
          paramCanvas.drawText((String)localObject, 10.0F, MotionLayout.this.getHeight() - 30, this.mTextPaint);
          paramCanvas.drawText((String)localObject, 11.0F, MotionLayout.this.getHeight() - 29, this.mPaint);
        }
        Iterator localIterator = paramHashMap.values().iterator();
        while (localIterator.hasNext())
        {
          paramHashMap = (MotionController)localIterator.next();
          int j = paramHashMap.getDrawPath();
          int i = j;
          if (paramInt2 > 0)
          {
            i = j;
            if (j == 0) {
              i = 1;
            }
          }
          if (i != 0)
          {
            this.mKeyFrameCount = paramHashMap.buildKeyFrames(this.mKeyFramePoints, this.mPathMode);
            if (i >= 1)
            {
              int k = paramInt1 / 16;
              localObject = this.mPoints;
              if ((localObject == null) || (localObject.length != k * 2))
              {
                this.mPoints = new float[k * 2];
                this.mPath = new Path();
              }
              j = this.mShadowTranslate;
              paramCanvas.translate(j, j);
              this.mPaint.setColor(1996488704);
              this.mFillPaint.setColor(1996488704);
              this.mPaintKeyframes.setColor(1996488704);
              this.mPaintGraph.setColor(1996488704);
              paramHashMap.buildPath(this.mPoints, k);
              drawAll(paramCanvas, i, this.mKeyFrameCount, paramHashMap);
              this.mPaint.setColor(43571);
              this.mPaintKeyframes.setColor(-2067046);
              this.mFillPaint.setColor(-2067046);
              this.mPaintGraph.setColor(-13391360);
              j = this.mShadowTranslate;
              paramCanvas.translate(-j, -j);
              drawAll(paramCanvas, i, this.mKeyFrameCount, paramHashMap);
              if (i == 5) {
                drawRectangle(paramCanvas, paramHashMap);
              }
            }
          }
        }
        paramCanvas.restore();
        return;
      }
    }
    
    public void drawAll(Canvas paramCanvas, int paramInt1, int paramInt2, MotionController paramMotionController)
    {
      if (paramInt1 == 4) {
        drawPathAsConfigured(paramCanvas);
      }
      if (paramInt1 == 2) {
        drawPathRelative(paramCanvas);
      }
      if (paramInt1 == 3) {
        drawPathCartesian(paramCanvas);
      }
      drawBasicPath(paramCanvas);
      drawTicks(paramCanvas, paramInt1, paramInt2, paramMotionController);
    }
    
    void getTextBounds(String paramString, Paint paramPaint)
    {
      paramPaint.getTextBounds(paramString, 0, paramString.length(), this.mBounds);
    }
  }
  
  class Model
  {
    ConstraintSet mEnd = null;
    int mEndId;
    ConstraintWidgetContainer mLayoutEnd = new ConstraintWidgetContainer();
    ConstraintWidgetContainer mLayoutStart = new ConstraintWidgetContainer();
    ConstraintSet mStart = null;
    int mStartId;
    
    Model() {}
    
    private void computeStartEndSize(int paramInt1, int paramInt2)
    {
      int k = MotionLayout.this.getOptimizationLevel();
      Object localObject2;
      Object localObject1;
      Object localObject3;
      int i;
      int j;
      if (MotionLayout.this.mCurrentState == MotionLayout.this.getStartState())
      {
        localObject2 = MotionLayout.this;
        localObject1 = this.mLayoutEnd;
        localObject3 = this.mEnd;
        if ((localObject3 != null) && (((ConstraintSet)localObject3).mRotate != 0)) {
          i = paramInt2;
        } else {
          i = paramInt1;
        }
        localObject3 = this.mEnd;
        if ((localObject3 != null) && (((ConstraintSet)localObject3).mRotate != 0)) {
          j = paramInt1;
        } else {
          j = paramInt2;
        }
        ((MotionLayout)localObject2).resolveSystem((ConstraintWidgetContainer)localObject1, k, i, j);
        localObject3 = this.mStart;
        if (localObject3 != null)
        {
          localObject2 = MotionLayout.this;
          localObject1 = this.mLayoutStart;
          if (((ConstraintSet)localObject3).mRotate == 0) {
            i = paramInt1;
          } else {
            i = paramInt2;
          }
          if (this.mStart.mRotate == 0) {
            paramInt1 = paramInt2;
          }
          ((MotionLayout)localObject2).resolveSystem((ConstraintWidgetContainer)localObject1, k, i, paramInt1);
        }
      }
      else
      {
        localObject2 = this.mStart;
        if (localObject2 != null)
        {
          localObject1 = MotionLayout.this;
          localObject3 = this.mLayoutStart;
          if (((ConstraintSet)localObject2).mRotate == 0) {
            i = paramInt1;
          } else {
            i = paramInt2;
          }
          if (this.mStart.mRotate == 0) {
            j = paramInt2;
          } else {
            j = paramInt1;
          }
          ((MotionLayout)localObject1).resolveSystem((ConstraintWidgetContainer)localObject3, k, i, j);
        }
        localObject2 = MotionLayout.this;
        localObject1 = this.mLayoutEnd;
        localObject3 = this.mEnd;
        if ((localObject3 != null) && (((ConstraintSet)localObject3).mRotate != 0)) {
          i = paramInt2;
        } else {
          i = paramInt1;
        }
        localObject3 = this.mEnd;
        if ((localObject3 != null) && (((ConstraintSet)localObject3).mRotate != 0)) {
          break label314;
        }
        paramInt1 = paramInt2;
        label314:
        ((MotionLayout)localObject2).resolveSystem((ConstraintWidgetContainer)localObject1, k, i, paramInt1);
      }
    }
    
    private void debugLayout(String paramString, ConstraintWidgetContainer paramConstraintWidgetContainer)
    {
      Object localObject1 = (View)paramConstraintWidgetContainer.getCompanionWidget();
      paramString = new StringBuilder().append(paramString).append(" ");
      localObject1 = Debug.getName((View)localObject1);
      Log5ECF72.a(localObject1);
      LogE84000.a(localObject1);
      Log229316.a(localObject1);
      String str1 = (String)localObject1;
      Log.v("MotionLayout", str1 + "  ========= " + paramConstraintWidgetContainer);
      int j = paramConstraintWidgetContainer.getChildren().size();
      for (int i = 0; i < j; i++)
      {
        String str2 = str1 + "[" + i + "] ";
        ConstraintWidget localConstraintWidget = (ConstraintWidget)paramConstraintWidgetContainer.getChildren().get(i);
        Object localObject2 = new StringBuilder().append("");
        paramString = localConstraintWidget.mTop.mTarget;
        localObject1 = "_";
        if (paramString != null) {
          paramString = "T";
        } else {
          paramString = "_";
        }
        paramString = paramString;
        localObject2 = new StringBuilder().append(paramString);
        if (localConstraintWidget.mBottom.mTarget != null) {
          paramString = "B";
        } else {
          paramString = "_";
        }
        paramString = paramString;
        localObject2 = new StringBuilder().append(paramString);
        if (localConstraintWidget.mLeft.mTarget != null) {
          paramString = "L";
        } else {
          paramString = "_";
        }
        paramString = paramString;
        localObject2 = new StringBuilder().append(paramString);
        paramString = (String)localObject1;
        if (localConstraintWidget.mRight.mTarget != null) {
          paramString = "R";
        }
        localObject2 = paramString;
        View localView = (View)localConstraintWidget.getCompanionWidget();
        localObject1 = Debug.getName(localView);
        Log5ECF72.a(localObject1);
        LogE84000.a(localObject1);
        Log229316.a(localObject1);
        paramString = (String)localObject1;
        if ((localView instanceof TextView)) {
          paramString = (String)localObject1 + "(" + ((TextView)localView).getText() + ")";
        }
        Log.v("MotionLayout", str2 + "  " + paramString + " " + localConstraintWidget + " " + (String)localObject2);
      }
      Log.v("MotionLayout", str1 + " done. ");
    }
    
    private void debugLayoutParam(String paramString, ConstraintLayout.LayoutParams paramLayoutParams)
    {
      Object localObject2 = new StringBuilder().append(" ");
      if (paramLayoutParams.startToStart != -1) {
        localObject1 = "SS";
      } else {
        localObject1 = "__";
      }
      Object localObject1 = (String)localObject1;
      StringBuilder localStringBuilder = new StringBuilder().append((String)localObject1);
      int i = paramLayoutParams.startToEnd;
      localObject2 = "|__";
      if (i != -1) {
        localObject1 = "|SE";
      } else {
        localObject1 = "|__";
      }
      localObject1 = (String)localObject1;
      localStringBuilder = new StringBuilder().append((String)localObject1);
      if (paramLayoutParams.endToStart != -1) {
        localObject1 = "|ES";
      } else {
        localObject1 = "|__";
      }
      localObject1 = (String)localObject1;
      localStringBuilder = new StringBuilder().append((String)localObject1);
      if (paramLayoutParams.endToEnd != -1) {
        localObject1 = "|EE";
      } else {
        localObject1 = "|__";
      }
      localObject1 = (String)localObject1;
      localStringBuilder = new StringBuilder().append((String)localObject1);
      if (paramLayoutParams.leftToLeft != -1) {
        localObject1 = "|LL";
      } else {
        localObject1 = "|__";
      }
      localObject1 = (String)localObject1;
      localStringBuilder = new StringBuilder().append((String)localObject1);
      if (paramLayoutParams.leftToRight != -1) {
        localObject1 = "|LR";
      } else {
        localObject1 = "|__";
      }
      localObject1 = (String)localObject1;
      localStringBuilder = new StringBuilder().append((String)localObject1);
      if (paramLayoutParams.rightToLeft != -1) {
        localObject1 = "|RL";
      } else {
        localObject1 = "|__";
      }
      localObject1 = (String)localObject1;
      localStringBuilder = new StringBuilder().append((String)localObject1);
      if (paramLayoutParams.rightToRight != -1) {
        localObject1 = "|RR";
      } else {
        localObject1 = "|__";
      }
      localObject1 = (String)localObject1;
      localStringBuilder = new StringBuilder().append((String)localObject1);
      if (paramLayoutParams.topToTop != -1) {
        localObject1 = "|TT";
      } else {
        localObject1 = "|__";
      }
      localObject1 = (String)localObject1;
      localStringBuilder = new StringBuilder().append((String)localObject1);
      if (paramLayoutParams.topToBottom != -1) {
        localObject1 = "|TB";
      } else {
        localObject1 = "|__";
      }
      localObject1 = (String)localObject1;
      localStringBuilder = new StringBuilder().append((String)localObject1);
      if (paramLayoutParams.bottomToTop != -1) {
        localObject1 = "|BT";
      } else {
        localObject1 = "|__";
      }
      localObject1 = (String)localObject1;
      localStringBuilder = new StringBuilder().append((String)localObject1);
      localObject1 = localObject2;
      if (paramLayoutParams.bottomToBottom != -1) {
        localObject1 = "|BB";
      }
      paramLayoutParams = (String)localObject1;
      Log.v("MotionLayout", paramString + paramLayoutParams);
    }
    
    private void debugWidget(String paramString, ConstraintWidget paramConstraintWidget)
    {
      StringBuilder localStringBuilder1 = new StringBuilder().append(" ");
      Object localObject1 = paramConstraintWidget.mTop.mTarget;
      String str = "B";
      Object localObject2 = "__";
      StringBuilder localStringBuilder2;
      if (localObject1 != null)
      {
        localStringBuilder2 = new StringBuilder().append("T");
        if (paramConstraintWidget.mTop.mTarget.mType == ConstraintAnchor.Type.TOP) {
          localObject1 = "T";
        } else {
          localObject1 = "B";
        }
        localObject1 = (String)localObject1;
      }
      else
      {
        localObject1 = "__";
      }
      localObject1 = (String)localObject1;
      localStringBuilder1 = new StringBuilder().append((String)localObject1);
      if (paramConstraintWidget.mBottom.mTarget != null)
      {
        localStringBuilder2 = new StringBuilder().append("B");
        localObject1 = str;
        if (paramConstraintWidget.mBottom.mTarget.mType == ConstraintAnchor.Type.TOP) {
          localObject1 = "T";
        }
        localObject1 = (String)localObject1;
      }
      else
      {
        localObject1 = "__";
      }
      localObject1 = (String)localObject1;
      localStringBuilder1 = new StringBuilder().append((String)localObject1);
      localObject1 = paramConstraintWidget.mLeft.mTarget;
      str = "R";
      if (localObject1 != null)
      {
        localStringBuilder2 = new StringBuilder().append("L");
        if (paramConstraintWidget.mLeft.mTarget.mType == ConstraintAnchor.Type.LEFT) {
          localObject1 = "L";
        } else {
          localObject1 = "R";
        }
        localObject1 = (String)localObject1;
      }
      else
      {
        localObject1 = "__";
      }
      localObject1 = (String)localObject1;
      localStringBuilder1 = new StringBuilder().append((String)localObject1);
      localObject1 = localObject2;
      if (paramConstraintWidget.mRight.mTarget != null)
      {
        localObject2 = new StringBuilder().append("R");
        localObject1 = str;
        if (paramConstraintWidget.mRight.mTarget.mType == ConstraintAnchor.Type.LEFT) {
          localObject1 = "L";
        }
        localObject1 = (String)localObject1;
      }
      localObject1 = (String)localObject1;
      Log.v("MotionLayout", paramString + (String)localObject1 + " ---  " + paramConstraintWidget);
    }
    
    private void setupConstraintWidget(ConstraintWidgetContainer paramConstraintWidgetContainer, ConstraintSet paramConstraintSet)
    {
      SparseArray localSparseArray = new SparseArray();
      Object localObject1 = new Constraints.LayoutParams(-2, -2);
      localSparseArray.clear();
      localSparseArray.put(0, paramConstraintWidgetContainer);
      localSparseArray.put(MotionLayout.this.getId(), paramConstraintWidgetContainer);
      Object localObject2;
      if ((paramConstraintSet != null) && (paramConstraintSet.mRotate != 0))
      {
        localObject2 = MotionLayout.this;
        ((MotionLayout)localObject2).resolveSystem(this.mLayoutEnd, ((MotionLayout)localObject2).getOptimizationLevel(), View.MeasureSpec.makeMeasureSpec(MotionLayout.this.getHeight(), 1073741824), View.MeasureSpec.makeMeasureSpec(MotionLayout.this.getWidth(), 1073741824));
      }
      Object localObject3 = paramConstraintWidgetContainer.getChildren().iterator();
      while (((Iterator)localObject3).hasNext())
      {
        localObject2 = (ConstraintWidget)((Iterator)localObject3).next();
        ((ConstraintWidget)localObject2).setAnimated(true);
        localSparseArray.put(((View)((ConstraintWidget)localObject2).getCompanionWidget()).getId(), localObject2);
      }
      Iterator localIterator = paramConstraintWidgetContainer.getChildren().iterator();
      while (localIterator.hasNext())
      {
        localObject3 = (ConstraintWidget)localIterator.next();
        localObject2 = (View)((ConstraintWidget)localObject3).getCompanionWidget();
        paramConstraintSet.applyToLayoutParams(((View)localObject2).getId(), (ConstraintLayout.LayoutParams)localObject1);
        ((ConstraintWidget)localObject3).setWidth(paramConstraintSet.getWidth(((View)localObject2).getId()));
        ((ConstraintWidget)localObject3).setHeight(paramConstraintSet.getHeight(((View)localObject2).getId()));
        if ((localObject2 instanceof ConstraintHelper))
        {
          paramConstraintSet.applyToHelper((ConstraintHelper)localObject2, (ConstraintWidget)localObject3, (ConstraintLayout.LayoutParams)localObject1, localSparseArray);
          if ((localObject2 instanceof androidx.constraintlayout.widget.Barrier)) {
            ((androidx.constraintlayout.widget.Barrier)localObject2).validateParams();
          }
        }
        if (Build.VERSION.SDK_INT >= 17) {
          ((Constraints.LayoutParams)localObject1).resolveLayoutDirection(MotionLayout.this.getLayoutDirection());
        } else {
          ((Constraints.LayoutParams)localObject1).resolveLayoutDirection(0);
        }
        MotionLayout.this.applyConstraintsFromLayoutParams(false, (View)localObject2, (ConstraintWidget)localObject3, (ConstraintLayout.LayoutParams)localObject1, localSparseArray);
        if (paramConstraintSet.getVisibilityMode(((View)localObject2).getId()) == 1) {
          ((ConstraintWidget)localObject3).setVisibility(((View)localObject2).getVisibility());
        } else {
          ((ConstraintWidget)localObject3).setVisibility(paramConstraintSet.getVisibility(((View)localObject2).getId()));
        }
      }
      paramConstraintSet = paramConstraintWidgetContainer.getChildren().iterator();
      while (paramConstraintSet.hasNext())
      {
        localObject2 = (ConstraintWidget)paramConstraintSet.next();
        if ((localObject2 instanceof VirtualLayout))
        {
          localObject1 = (ConstraintHelper)((ConstraintWidget)localObject2).getCompanionWidget();
          localObject2 = (Helper)localObject2;
          ((ConstraintHelper)localObject1).updatePreLayout(paramConstraintWidgetContainer, (Helper)localObject2, localSparseArray);
          ((VirtualLayout)localObject2).captureWidgets();
        }
      }
    }
    
    public void build()
    {
      int j = MotionLayout.this.getChildCount();
      MotionLayout.this.mFrameArrayList.clear();
      SparseArray localSparseArray = new SparseArray();
      int[] arrayOfInt = new int[j];
      Object localObject2;
      Object localObject1;
      int k;
      for (int i = 0; i < j; i++)
      {
        localObject2 = MotionLayout.this.getChildAt(i);
        localObject1 = new MotionController((View)localObject2);
        k = ((View)localObject2).getId();
        arrayOfInt[i] = k;
        localSparseArray.put(k, localObject1);
        MotionLayout.this.mFrameArrayList.put(localObject2, localObject1);
      }
      for (i = 0; i < j; i++)
      {
        localObject1 = MotionLayout.this.getChildAt(i);
        localObject2 = (MotionController)MotionLayout.this.mFrameArrayList.get(localObject1);
        if (localObject2 != null)
        {
          Object localObject3;
          if (this.mStart != null)
          {
            localObject3 = getWidget(this.mLayoutStart, (View)localObject1);
            if (localObject3 != null)
            {
              ((MotionController)localObject2).setStartState(MotionLayout.this.toRect((ConstraintWidget)localObject3), this.mStart, MotionLayout.this.getWidth(), MotionLayout.this.getHeight());
            }
            else if (MotionLayout.this.mDebugPath != 0)
            {
              localObject3 = new StringBuilder();
              String str = Debug.getLocation();
              Log5ECF72.a(str);
              LogE84000.a(str);
              Log229316.a(str);
              localObject3 = ((StringBuilder)localObject3).append(str).append("no widget for  ");
              str = Debug.getName((View)localObject1);
              Log5ECF72.a(str);
              LogE84000.a(str);
              Log229316.a(str);
              Log.e("MotionLayout", str + " (" + localObject1.getClass().getName() + ")");
            }
          }
          else if (MotionLayout.this.mInRotation)
          {
            ((MotionController)localObject2).setStartState((ViewState)MotionLayout.this.mPreRotate.get(localObject1), (View)localObject1, MotionLayout.this.mRotatMode, MotionLayout.this.mPreRotateWidth, MotionLayout.this.mPreRotateHeight);
          }
          if (this.mEnd != null)
          {
            localObject3 = getWidget(this.mLayoutEnd, (View)localObject1);
            if (localObject3 != null)
            {
              ((MotionController)localObject2).setEndState(MotionLayout.this.toRect((ConstraintWidget)localObject3), this.mEnd, MotionLayout.this.getWidth(), MotionLayout.this.getHeight());
            }
            else if (MotionLayout.this.mDebugPath != 0)
            {
              localObject3 = new StringBuilder();
              localObject2 = Debug.getLocation();
              Log5ECF72.a(localObject2);
              LogE84000.a(localObject2);
              Log229316.a(localObject2);
              localObject3 = ((StringBuilder)localObject3).append((String)localObject2).append("no widget for  ");
              localObject2 = Debug.getName((View)localObject1);
              Log5ECF72.a(localObject2);
              LogE84000.a(localObject2);
              Log229316.a(localObject2);
              Log.e("MotionLayout", (String)localObject2 + " (" + localObject1.getClass().getName() + ")");
            }
          }
        }
      }
      for (i = 0; i < j; i++)
      {
        localObject1 = (MotionController)localSparseArray.get(arrayOfInt[i]);
        k = ((MotionController)localObject1).getAnimateRelativeTo();
        if (k != -1) {
          ((MotionController)localObject1).setupRelative((MotionController)localSparseArray.get(k));
        }
      }
    }
    
    void copy(ConstraintWidgetContainer paramConstraintWidgetContainer1, ConstraintWidgetContainer paramConstraintWidgetContainer2)
    {
      ArrayList localArrayList = paramConstraintWidgetContainer1.getChildren();
      HashMap localHashMap = new HashMap();
      localHashMap.put(paramConstraintWidgetContainer1, paramConstraintWidgetContainer2);
      paramConstraintWidgetContainer2.getChildren().clear();
      paramConstraintWidgetContainer2.copy(paramConstraintWidgetContainer1, localHashMap);
      Iterator localIterator = localArrayList.iterator();
      while (localIterator.hasNext())
      {
        ConstraintWidget localConstraintWidget = (ConstraintWidget)localIterator.next();
        if ((localConstraintWidget instanceof androidx.constraintlayout.core.widgets.Barrier)) {
          paramConstraintWidgetContainer1 = new androidx.constraintlayout.core.widgets.Barrier();
        } else if ((localConstraintWidget instanceof Guideline)) {
          paramConstraintWidgetContainer1 = new Guideline();
        } else if ((localConstraintWidget instanceof Flow)) {
          paramConstraintWidgetContainer1 = new Flow();
        } else if ((localConstraintWidget instanceof Placeholder)) {
          paramConstraintWidgetContainer1 = new Placeholder();
        } else if ((localConstraintWidget instanceof Helper)) {
          paramConstraintWidgetContainer1 = new HelperWidget();
        } else {
          paramConstraintWidgetContainer1 = new ConstraintWidget();
        }
        paramConstraintWidgetContainer2.add(paramConstraintWidgetContainer1);
        localHashMap.put(localConstraintWidget, paramConstraintWidgetContainer1);
      }
      paramConstraintWidgetContainer1 = localArrayList.iterator();
      while (paramConstraintWidgetContainer1.hasNext())
      {
        paramConstraintWidgetContainer2 = (ConstraintWidget)paramConstraintWidgetContainer1.next();
        ((ConstraintWidget)localHashMap.get(paramConstraintWidgetContainer2)).copy(paramConstraintWidgetContainer2, localHashMap);
      }
    }
    
    ConstraintWidget getWidget(ConstraintWidgetContainer paramConstraintWidgetContainer, View paramView)
    {
      if (paramConstraintWidgetContainer.getCompanionWidget() == paramView) {
        return paramConstraintWidgetContainer;
      }
      paramConstraintWidgetContainer = paramConstraintWidgetContainer.getChildren();
      int j = paramConstraintWidgetContainer.size();
      for (int i = 0; i < j; i++)
      {
        ConstraintWidget localConstraintWidget = (ConstraintWidget)paramConstraintWidgetContainer.get(i);
        if (localConstraintWidget.getCompanionWidget() == paramView) {
          return localConstraintWidget;
        }
      }
      return null;
    }
    
    void initFrom(ConstraintWidgetContainer paramConstraintWidgetContainer, ConstraintSet paramConstraintSet1, ConstraintSet paramConstraintSet2)
    {
      this.mStart = paramConstraintSet1;
      this.mEnd = paramConstraintSet2;
      this.mLayoutStart = new ConstraintWidgetContainer();
      this.mLayoutEnd = new ConstraintWidgetContainer();
      this.mLayoutStart.setMeasurer(MotionLayout.this.mLayoutWidget.getMeasurer());
      this.mLayoutEnd.setMeasurer(MotionLayout.this.mLayoutWidget.getMeasurer());
      this.mLayoutStart.removeAllChildren();
      this.mLayoutEnd.removeAllChildren();
      copy(MotionLayout.this.mLayoutWidget, this.mLayoutStart);
      copy(MotionLayout.this.mLayoutWidget, this.mLayoutEnd);
      if (MotionLayout.this.mTransitionLastPosition > 0.5D)
      {
        if (paramConstraintSet1 != null) {
          setupConstraintWidget(this.mLayoutStart, paramConstraintSet1);
        }
        setupConstraintWidget(this.mLayoutEnd, paramConstraintSet2);
      }
      else
      {
        setupConstraintWidget(this.mLayoutEnd, paramConstraintSet2);
        if (paramConstraintSet1 != null) {
          setupConstraintWidget(this.mLayoutStart, paramConstraintSet1);
        }
      }
      this.mLayoutStart.setRtl(MotionLayout.this.isRtl());
      this.mLayoutStart.updateHierarchy();
      this.mLayoutEnd.setRtl(MotionLayout.this.isRtl());
      this.mLayoutEnd.updateHierarchy();
      paramConstraintWidgetContainer = MotionLayout.this.getLayoutParams();
      if (paramConstraintWidgetContainer != null)
      {
        if (paramConstraintWidgetContainer.width == -2)
        {
          this.mLayoutStart.setHorizontalDimensionBehaviour(ConstraintWidget.DimensionBehaviour.WRAP_CONTENT);
          this.mLayoutEnd.setHorizontalDimensionBehaviour(ConstraintWidget.DimensionBehaviour.WRAP_CONTENT);
        }
        if (paramConstraintWidgetContainer.height == -2)
        {
          this.mLayoutStart.setVerticalDimensionBehaviour(ConstraintWidget.DimensionBehaviour.WRAP_CONTENT);
          this.mLayoutEnd.setVerticalDimensionBehaviour(ConstraintWidget.DimensionBehaviour.WRAP_CONTENT);
        }
      }
    }
    
    public boolean isNotConfiguredWith(int paramInt1, int paramInt2)
    {
      boolean bool;
      if ((paramInt1 == this.mStartId) && (paramInt2 == this.mEndId)) {
        bool = false;
      } else {
        bool = true;
      }
      return bool;
    }
    
    public void measure(int paramInt1, int paramInt2)
    {
      int m = View.MeasureSpec.getMode(paramInt1);
      int k = View.MeasureSpec.getMode(paramInt2);
      MotionLayout.this.mWidthMeasureMode = m;
      MotionLayout.this.mHeightMeasureMode = k;
      MotionLayout.this.getOptimizationLevel();
      computeStartEndSize(paramInt1, paramInt2);
      int j = 1;
      int i = j;
      if ((MotionLayout.this.getParent() instanceof MotionLayout))
      {
        i = j;
        if (m == 1073741824)
        {
          i = j;
          if (k == 1073741824) {
            i = 0;
          }
        }
      }
      boolean bool1;
      if (i != 0)
      {
        computeStartEndSize(paramInt1, paramInt2);
        MotionLayout.this.mStartWrapWidth = this.mLayoutStart.getWidth();
        MotionLayout.this.mStartWrapHeight = this.mLayoutStart.getHeight();
        MotionLayout.this.mEndWrapWidth = this.mLayoutEnd.getWidth();
        MotionLayout.this.mEndWrapHeight = this.mLayoutEnd.getHeight();
        MotionLayout localMotionLayout = MotionLayout.this;
        if ((localMotionLayout.mStartWrapWidth == MotionLayout.this.mEndWrapWidth) && (MotionLayout.this.mStartWrapHeight == MotionLayout.this.mEndWrapHeight)) {
          bool1 = false;
        } else {
          bool1 = true;
        }
        localMotionLayout.mMeasureDuringTransition = bool1;
      }
      i = MotionLayout.this.mStartWrapWidth;
      j = MotionLayout.this.mStartWrapHeight;
      if ((MotionLayout.this.mWidthMeasureMode == Integer.MIN_VALUE) || (MotionLayout.this.mWidthMeasureMode == 0)) {
        i = (int)(MotionLayout.this.mStartWrapWidth + MotionLayout.this.mPostInterpolationPosition * (MotionLayout.this.mEndWrapWidth - MotionLayout.this.mStartWrapWidth));
      }
      if ((MotionLayout.this.mHeightMeasureMode == Integer.MIN_VALUE) || (MotionLayout.this.mHeightMeasureMode == 0)) {
        j = (int)(MotionLayout.this.mStartWrapHeight + MotionLayout.this.mPostInterpolationPosition * (MotionLayout.this.mEndWrapHeight - MotionLayout.this.mStartWrapHeight));
      }
      if ((!this.mLayoutStart.isWidthMeasuredTooSmall()) && (!this.mLayoutEnd.isWidthMeasuredTooSmall())) {
        bool1 = false;
      } else {
        bool1 = true;
      }
      boolean bool2;
      if ((!this.mLayoutStart.isHeightMeasuredTooSmall()) && (!this.mLayoutEnd.isHeightMeasuredTooSmall())) {
        bool2 = false;
      } else {
        bool2 = true;
      }
      MotionLayout.this.resolveMeasuredDimension(paramInt1, paramInt2, i, j, bool1, bool2);
    }
    
    public void reEvaluateState()
    {
      measure(MotionLayout.this.mLastWidthMeasureSpec, MotionLayout.this.mLastHeightMeasureSpec);
      MotionLayout.this.setupMotionViews();
    }
    
    public void setMeasuredId(int paramInt1, int paramInt2)
    {
      this.mStartId = paramInt1;
      this.mEndId = paramInt2;
    }
  }
  
  protected static abstract interface MotionTracker
  {
    public abstract void addMovement(MotionEvent paramMotionEvent);
    
    public abstract void clear();
    
    public abstract void computeCurrentVelocity(int paramInt);
    
    public abstract void computeCurrentVelocity(int paramInt, float paramFloat);
    
    public abstract float getXVelocity();
    
    public abstract float getXVelocity(int paramInt);
    
    public abstract float getYVelocity();
    
    public abstract float getYVelocity(int paramInt);
    
    public abstract void recycle();
  }
  
  private static class MyTracker
    implements MotionLayout.MotionTracker
  {
    private static MyTracker me = new MyTracker();
    VelocityTracker tracker;
    
    public static MyTracker obtain()
    {
      me.tracker = VelocityTracker.obtain();
      return me;
    }
    
    public void addMovement(MotionEvent paramMotionEvent)
    {
      VelocityTracker localVelocityTracker = this.tracker;
      if (localVelocityTracker != null) {
        localVelocityTracker.addMovement(paramMotionEvent);
      }
    }
    
    public void clear()
    {
      VelocityTracker localVelocityTracker = this.tracker;
      if (localVelocityTracker != null) {
        localVelocityTracker.clear();
      }
    }
    
    public void computeCurrentVelocity(int paramInt)
    {
      VelocityTracker localVelocityTracker = this.tracker;
      if (localVelocityTracker != null) {
        localVelocityTracker.computeCurrentVelocity(paramInt);
      }
    }
    
    public void computeCurrentVelocity(int paramInt, float paramFloat)
    {
      VelocityTracker localVelocityTracker = this.tracker;
      if (localVelocityTracker != null) {
        localVelocityTracker.computeCurrentVelocity(paramInt, paramFloat);
      }
    }
    
    public float getXVelocity()
    {
      VelocityTracker localVelocityTracker = this.tracker;
      if (localVelocityTracker != null) {
        return localVelocityTracker.getXVelocity();
      }
      return 0.0F;
    }
    
    public float getXVelocity(int paramInt)
    {
      VelocityTracker localVelocityTracker = this.tracker;
      if (localVelocityTracker != null) {
        return localVelocityTracker.getXVelocity(paramInt);
      }
      return 0.0F;
    }
    
    public float getYVelocity()
    {
      VelocityTracker localVelocityTracker = this.tracker;
      if (localVelocityTracker != null) {
        return localVelocityTracker.getYVelocity();
      }
      return 0.0F;
    }
    
    public float getYVelocity(int paramInt)
    {
      if (this.tracker != null) {
        return getYVelocity(paramInt);
      }
      return 0.0F;
    }
    
    public void recycle()
    {
      VelocityTracker localVelocityTracker = this.tracker;
      if (localVelocityTracker != null)
      {
        localVelocityTracker.recycle();
        this.tracker = null;
      }
    }
  }
  
  class StateCache
  {
    final String KeyEndState = "motion.EndState";
    final String KeyProgress = "motion.progress";
    final String KeyStartState = "motion.StartState";
    final String KeyVelocity = "motion.velocity";
    int endState = -1;
    float mProgress = NaN.0F;
    float mVelocity = NaN.0F;
    int startState = -1;
    
    StateCache() {}
    
    void apply()
    {
      int i = this.startState;
      if ((i != -1) || (this.endState != -1))
      {
        if (i == -1)
        {
          MotionLayout.this.transitionToState(this.endState);
        }
        else
        {
          int j = this.endState;
          if (j == -1) {
            MotionLayout.this.setState(i, -1, -1);
          } else {
            MotionLayout.this.setTransition(i, j);
          }
        }
        MotionLayout.this.setState(MotionLayout.TransitionState.SETUP);
      }
      if (Float.isNaN(this.mVelocity))
      {
        if (Float.isNaN(this.mProgress)) {
          return;
        }
        MotionLayout.this.setProgress(this.mProgress);
        return;
      }
      MotionLayout.this.setProgress(this.mProgress, this.mVelocity);
      this.mProgress = NaN.0F;
      this.mVelocity = NaN.0F;
      this.startState = -1;
      this.endState = -1;
    }
    
    public Bundle getTransitionState()
    {
      Bundle localBundle = new Bundle();
      localBundle.putFloat("motion.progress", this.mProgress);
      localBundle.putFloat("motion.velocity", this.mVelocity);
      localBundle.putInt("motion.StartState", this.startState);
      localBundle.putInt("motion.EndState", this.endState);
      return localBundle;
    }
    
    public void recordState()
    {
      this.endState = MotionLayout.this.mEndState;
      this.startState = MotionLayout.this.mBeginState;
      this.mVelocity = MotionLayout.this.getVelocity();
      this.mProgress = MotionLayout.this.getProgress();
    }
    
    public void setEndState(int paramInt)
    {
      this.endState = paramInt;
    }
    
    public void setProgress(float paramFloat)
    {
      this.mProgress = paramFloat;
    }
    
    public void setStartState(int paramInt)
    {
      this.startState = paramInt;
    }
    
    public void setTransitionState(Bundle paramBundle)
    {
      this.mProgress = paramBundle.getFloat("motion.progress");
      this.mVelocity = paramBundle.getFloat("motion.velocity");
      this.startState = paramBundle.getInt("motion.StartState");
      this.endState = paramBundle.getInt("motion.EndState");
    }
    
    public void setVelocity(float paramFloat)
    {
      this.mVelocity = paramFloat;
    }
  }
  
  public static abstract interface TransitionListener
  {
    public abstract void onTransitionChange(MotionLayout paramMotionLayout, int paramInt1, int paramInt2, float paramFloat);
    
    public abstract void onTransitionCompleted(MotionLayout paramMotionLayout, int paramInt);
    
    public abstract void onTransitionStarted(MotionLayout paramMotionLayout, int paramInt1, int paramInt2);
    
    public abstract void onTransitionTrigger(MotionLayout paramMotionLayout, int paramInt, boolean paramBoolean, float paramFloat);
  }
  
  static enum TransitionState
  {
    private static final TransitionState[] $VALUES = $values();
    
    static
    {
      SETUP = new TransitionState("SETUP", 1);
      MOVING = new TransitionState("MOVING", 2);
      FINISHED = new TransitionState("FINISHED", 3);
    }
    
    private TransitionState() {}
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/constraintlayout/motion/widget/MotionLayout.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */