package androidx.constraintlayout.motion.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.util.Log;
import android.util.TypedValue;
import android.util.Xml;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AnimationUtils;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.BounceInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.OvershootInterpolator;
import androidx.constraintlayout.core.motion.utils.Easing;
import androidx.constraintlayout.core.motion.utils.KeyCache;
import androidx.constraintlayout.widget.ConstraintAttribute;
import androidx.constraintlayout.widget.ConstraintLayout.LayoutParams;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.constraintlayout.widget.ConstraintSet.Constraint;
import androidx.constraintlayout.widget.R.id;
import androidx.constraintlayout.widget.R.styleable;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import mt.Log229316;
import mt.Log5ECF72;
import mt.LogE84000;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

public class ViewTransition
{
  static final int ANTICIPATE = 6;
  static final int BOUNCE = 4;
  public static final String CONSTRAINT_OVERRIDE = "ConstraintOverride";
  public static final String CUSTOM_ATTRIBUTE = "CustomAttribute";
  public static final String CUSTOM_METHOD = "CustomMethod";
  static final int EASE_IN = 1;
  static final int EASE_IN_OUT = 0;
  static final int EASE_OUT = 2;
  private static final int INTERPOLATOR_REFERENCE_ID = -2;
  public static final String KEY_FRAME_SET_TAG = "KeyFrameSet";
  static final int LINEAR = 3;
  public static final int ONSTATE_ACTION_DOWN = 1;
  public static final int ONSTATE_ACTION_DOWN_UP = 3;
  public static final int ONSTATE_ACTION_UP = 2;
  public static final int ONSTATE_SHARED_VALUE_SET = 4;
  public static final int ONSTATE_SHARED_VALUE_UNSET = 5;
  static final int OVERSHOOT = 5;
  private static final int SPLINE_STRING = -1;
  private static String TAG = "ViewTransition";
  private static final int UNSET = -1;
  static final int VIEWTRANSITIONMODE_ALLSTATES = 1;
  static final int VIEWTRANSITIONMODE_CURRENTSTATE = 0;
  static final int VIEWTRANSITIONMODE_NOSTATE = 2;
  public static final String VIEW_TRANSITION_TAG = "ViewTransition";
  private int mClearsTag = -1;
  ConstraintSet.Constraint mConstraintDelta;
  Context mContext;
  private int mDefaultInterpolator = 0;
  private int mDefaultInterpolatorID = -1;
  private String mDefaultInterpolatorString = null;
  private boolean mDisabled = false;
  private int mDuration = -1;
  private int mId;
  private int mIfTagNotSet = -1;
  private int mIfTagSet = -1;
  KeyFrames mKeyFrames;
  private int mOnStateTransition = -1;
  private int mPathMotionArc = 0;
  private int mSetsTag = -1;
  private int mSharedValueCurrent = -1;
  private int mSharedValueID = -1;
  private int mSharedValueTarget = -1;
  private int mTargetId;
  private String mTargetString;
  private int mUpDuration = -1;
  int mViewTransitionMode;
  ConstraintSet set;
  
  ViewTransition(Context paramContext, XmlPullParser paramXmlPullParser)
  {
    this.mContext = paramContext;
    try
    {
      for (int i = paramXmlPullParser.getEventType();; i = paramXmlPullParser.next())
      {
        int j = 1;
        if (i == 1) {
          break;
        }
        switch (i)
        {
        case 1: 
        default: 
          break;
        case 3: 
          if ("ViewTransition".equals(paramXmlPullParser.getName())) {
            return;
          }
          break;
        case 2: 
          Object localObject2 = paramXmlPullParser.getName();
          switch (((String)localObject2).hashCode())
          {
          }
          for (;;)
          {
            break;
            if (((String)localObject2).equals("CustomAttribute"))
            {
              i = 3;
              break label293;
              if (((String)localObject2).equals("CustomMethod"))
              {
                i = 4;
                break label293;
                if (((String)localObject2).equals("ViewTransition"))
                {
                  i = 0;
                  break label293;
                  if (((String)localObject2).equals("KeyFrameSet"))
                  {
                    i = j;
                    break label293;
                    if (((String)localObject2).equals("ConstraintOverride"))
                    {
                      i = 2;
                      break label293;
                    }
                  }
                }
              }
            }
          }
          i = -1;
          label293:
          String str;
          switch (i)
          {
          default: 
            str = TAG;
            break;
          case 3: 
          case 4: 
            ConstraintAttribute.parse(paramContext, paramXmlPullParser, this.mConstraintDelta.mCustomConstraints);
            break;
          case 2: 
            this.mConstraintDelta = ConstraintSet.buildDelta(paramContext, paramXmlPullParser);
            break;
          case 1: 
            localObject1 = new androidx/constraintlayout/motion/widget/KeyFrames;
            ((KeyFrames)localObject1).<init>(paramContext, paramXmlPullParser);
            this.mKeyFrames = ((KeyFrames)localObject1);
            break;
          case 0: 
            parseViewTransitionTags(paramContext, paramXmlPullParser);
            break;
          }
          StringBuilder localStringBuilder = new java/lang/StringBuilder;
          localStringBuilder.<init>();
          Object localObject1 = Debug.getLoc();
          Log5ECF72.a(localObject1);
          LogE84000.a(localObject1);
          Log229316.a(localObject1);
          Log.e(str, (String)localObject1 + " unknown tag " + (String)localObject2);
          localObject1 = TAG;
          localObject2 = new java/lang/StringBuilder;
          ((StringBuilder)localObject2).<init>();
          Log.e((String)localObject1, ".xml:" + paramXmlPullParser.getLineNumber());
        }
      }
    }
    catch (IOException paramContext)
    {
      paramContext.printStackTrace();
    }
    catch (XmlPullParserException paramContext)
    {
      paramContext.printStackTrace();
    }
  }
  
  private void parseViewTransitionTags(Context paramContext, XmlPullParser paramXmlPullParser)
  {
    paramContext = paramContext.obtainStyledAttributes(Xml.asAttributeSet(paramXmlPullParser), R.styleable.ViewTransition);
    int j = paramContext.getIndexCount();
    for (int i = 0; i < j; i++)
    {
      int m = paramContext.getIndex(i);
      if (m == R.styleable.ViewTransition_android_id)
      {
        this.mId = paramContext.getResourceId(m, this.mId);
      }
      else
      {
        int k;
        if (m == R.styleable.ViewTransition_motionTarget)
        {
          if (MotionLayout.IS_IN_EDIT_MODE)
          {
            k = paramContext.getResourceId(m, this.mTargetId);
            this.mTargetId = k;
            if (k == -1) {
              this.mTargetString = paramContext.getString(m);
            }
          }
          else if (paramContext.peekValue(m).type == 3)
          {
            this.mTargetString = paramContext.getString(m);
          }
          else
          {
            this.mTargetId = paramContext.getResourceId(m, this.mTargetId);
          }
        }
        else if (m == R.styleable.ViewTransition_onStateTransition)
        {
          this.mOnStateTransition = paramContext.getInt(m, this.mOnStateTransition);
        }
        else if (m == R.styleable.ViewTransition_transitionDisable)
        {
          this.mDisabled = paramContext.getBoolean(m, this.mDisabled);
        }
        else if (m == R.styleable.ViewTransition_pathMotionArc)
        {
          this.mPathMotionArc = paramContext.getInt(m, this.mPathMotionArc);
        }
        else if (m == R.styleable.ViewTransition_duration)
        {
          this.mDuration = paramContext.getInt(m, this.mDuration);
        }
        else if (m == R.styleable.ViewTransition_upDuration)
        {
          this.mUpDuration = paramContext.getInt(m, this.mUpDuration);
        }
        else if (m == R.styleable.ViewTransition_viewTransitionMode)
        {
          this.mViewTransitionMode = paramContext.getInt(m, this.mViewTransitionMode);
        }
        else if (m == R.styleable.ViewTransition_motionInterpolator)
        {
          paramXmlPullParser = paramContext.peekValue(m);
          if (paramXmlPullParser.type == 1)
          {
            k = paramContext.getResourceId(m, -1);
            this.mDefaultInterpolatorID = k;
            if (k != -1) {
              this.mDefaultInterpolator = -2;
            }
          }
          else if (paramXmlPullParser.type == 3)
          {
            paramXmlPullParser = paramContext.getString(m);
            this.mDefaultInterpolatorString = paramXmlPullParser;
            if ((paramXmlPullParser != null) && (paramXmlPullParser.indexOf("/") > 0))
            {
              this.mDefaultInterpolatorID = paramContext.getResourceId(m, -1);
              this.mDefaultInterpolator = -2;
            }
            else
            {
              this.mDefaultInterpolator = -1;
            }
          }
          else
          {
            this.mDefaultInterpolator = paramContext.getInteger(m, this.mDefaultInterpolator);
          }
        }
        else if (m == R.styleable.ViewTransition_setsTag)
        {
          this.mSetsTag = paramContext.getResourceId(m, this.mSetsTag);
        }
        else if (m == R.styleable.ViewTransition_clearsTag)
        {
          this.mClearsTag = paramContext.getResourceId(m, this.mClearsTag);
        }
        else if (m == R.styleable.ViewTransition_ifTagSet)
        {
          this.mIfTagSet = paramContext.getResourceId(m, this.mIfTagSet);
        }
        else if (m == R.styleable.ViewTransition_ifTagNotSet)
        {
          this.mIfTagNotSet = paramContext.getResourceId(m, this.mIfTagNotSet);
        }
        else if (m == R.styleable.ViewTransition_SharedValueId)
        {
          this.mSharedValueID = paramContext.getResourceId(m, this.mSharedValueID);
        }
        else if (m == R.styleable.ViewTransition_SharedValue)
        {
          this.mSharedValueTarget = paramContext.getInteger(m, this.mSharedValueTarget);
        }
      }
    }
    paramContext.recycle();
  }
  
  private void updateTransition(MotionScene.Transition paramTransition, View paramView)
  {
    int i = this.mDuration;
    if (i != -1) {
      paramTransition.setDuration(i);
    }
    paramTransition.setPathMotionArc(this.mPathMotionArc);
    paramTransition.setInterpolatorInfo(this.mDefaultInterpolator, this.mDefaultInterpolatorString, this.mDefaultInterpolatorID);
    i = paramView.getId();
    paramView = this.mKeyFrames;
    if (paramView != null)
    {
      Object localObject = paramView.getKeyFramesForView(-1);
      paramView = new KeyFrames();
      localObject = ((ArrayList)localObject).iterator();
      while (((Iterator)localObject).hasNext()) {
        paramView.addKey(((Key)((Iterator)localObject).next()).clone().setViewId(i));
      }
      paramTransition.addKeyFrame(paramView);
    }
  }
  
  void applyIndependentTransition(ViewTransitionController paramViewTransitionController, MotionLayout paramMotionLayout, View paramView)
  {
    MotionController localMotionController = new MotionController(paramView);
    localMotionController.setBothStates(paramView);
    this.mKeyFrames.addAllFrames(localMotionController);
    localMotionController.setup(paramMotionLayout.getWidth(), paramMotionLayout.getHeight(), this.mDuration, System.nanoTime());
    new Animate(paramViewTransitionController, localMotionController, this.mDuration, this.mUpDuration, this.mOnStateTransition, getInterpolator(paramMotionLayout.getContext()), this.mSetsTag, this.mClearsTag);
  }
  
  void applyTransition(ViewTransitionController paramViewTransitionController, MotionLayout paramMotionLayout, int paramInt, ConstraintSet paramConstraintSet, View... paramVarArgs)
  {
    if (this.mDisabled) {
      return;
    }
    int i = this.mViewTransitionMode;
    if (i == 2)
    {
      applyIndependentTransition(paramViewTransitionController, paramMotionLayout, paramVarArgs[0]);
      return;
    }
    Object localObject2;
    Object localObject1;
    if (i == 1)
    {
      localObject2 = paramMotionLayout.getConstraintSetIds();
      for (i = 0; i < localObject2.length; i++)
      {
        j = localObject2[i];
        if (j != paramInt)
        {
          localObject1 = paramMotionLayout.getConstraintSet(j);
          int k = paramVarArgs.length;
          for (j = 0; j < k; j++)
          {
            paramViewTransitionController = ((ConstraintSet)localObject1).getConstraint(paramVarArgs[j].getId());
            ConstraintSet.Constraint localConstraint = this.mConstraintDelta;
            if (localConstraint != null)
            {
              localConstraint.applyDelta(paramViewTransitionController);
              paramViewTransitionController.mCustomConstraints.putAll(this.mConstraintDelta.mCustomConstraints);
            }
          }
        }
      }
    }
    paramViewTransitionController = new ConstraintSet();
    paramViewTransitionController.clone(paramConstraintSet);
    int j = paramVarArgs.length;
    for (i = 0; i < j; i++)
    {
      localObject1 = paramViewTransitionController.getConstraint(paramVarArgs[i].getId());
      localObject2 = this.mConstraintDelta;
      if (localObject2 != null)
      {
        ((ConstraintSet.Constraint)localObject2).applyDelta((ConstraintSet.Constraint)localObject1);
        ((ConstraintSet.Constraint)localObject1).mCustomConstraints.putAll(this.mConstraintDelta.mCustomConstraints);
      }
    }
    paramMotionLayout.updateState(paramInt, paramViewTransitionController);
    paramMotionLayout.updateState(R.id.view_transition, paramConstraintSet);
    paramMotionLayout.setState(R.id.view_transition, -1, -1);
    paramViewTransitionController = new MotionScene.Transition(-1, paramMotionLayout.mScene, R.id.view_transition, paramInt);
    i = paramVarArgs.length;
    for (paramInt = 0; paramInt < i; paramInt++) {
      updateTransition(paramViewTransitionController, paramVarArgs[paramInt]);
    }
    paramMotionLayout.setTransition(paramViewTransitionController);
    paramMotionLayout.transitionToEnd(new ViewTransition..ExternalSyntheticLambda0(this, paramVarArgs));
  }
  
  boolean checkTags(View paramView)
  {
    int i = this.mIfTagSet;
    boolean bool2 = false;
    if (i == -1) {}
    while (paramView.getTag(i) != null)
    {
      i = 1;
      break;
    }
    i = 0;
    int j = this.mIfTagNotSet;
    if (j == -1) {}
    while (paramView.getTag(j) == null)
    {
      j = 1;
      break;
    }
    j = 0;
    boolean bool1 = bool2;
    if (i != 0)
    {
      bool1 = bool2;
      if (j != 0) {
        bool1 = true;
      }
    }
    return bool1;
  }
  
  int getId()
  {
    return this.mId;
  }
  
  Interpolator getInterpolator(Context paramContext)
  {
    switch (this.mDefaultInterpolator)
    {
    default: 
      return null;
    case 6: 
      return new AnticipateInterpolator();
    case 5: 
      return new OvershootInterpolator();
    case 4: 
      return new BounceInterpolator();
    case 3: 
      return null;
    case 2: 
      return new DecelerateInterpolator();
    case 1: 
      return new AccelerateInterpolator();
    case 0: 
      return new AccelerateDecelerateInterpolator();
    case -1: 
      new Interpolator()
      {
        public float getInterpolation(float paramAnonymousFloat)
        {
          return (float)this.val$easing.get(paramAnonymousFloat);
        }
      };
    }
    return AnimationUtils.loadInterpolator(paramContext, this.mDefaultInterpolatorID);
  }
  
  public int getSharedValue()
  {
    return this.mSharedValueTarget;
  }
  
  public int getSharedValueCurrent()
  {
    return this.mSharedValueCurrent;
  }
  
  public int getSharedValueID()
  {
    return this.mSharedValueID;
  }
  
  public int getStateTransition()
  {
    return this.mOnStateTransition;
  }
  
  boolean isEnabled()
  {
    return this.mDisabled ^ true;
  }
  
  boolean matchesView(View paramView)
  {
    if (paramView == null) {
      return false;
    }
    if ((this.mTargetId == -1) && (this.mTargetString == null)) {
      return false;
    }
    if (!checkTags(paramView)) {
      return false;
    }
    if (paramView.getId() == this.mTargetId) {
      return true;
    }
    if (this.mTargetString == null) {
      return false;
    }
    if ((paramView.getLayoutParams() instanceof ConstraintLayout.LayoutParams))
    {
      paramView = ((ConstraintLayout.LayoutParams)paramView.getLayoutParams()).constraintTag;
      if ((paramView != null) && (paramView.matches(this.mTargetString))) {
        return true;
      }
    }
    return false;
  }
  
  void setEnabled(boolean paramBoolean)
  {
    this.mDisabled = (paramBoolean ^ true);
  }
  
  void setId(int paramInt)
  {
    this.mId = paramInt;
  }
  
  public void setSharedValue(int paramInt)
  {
    this.mSharedValueTarget = paramInt;
  }
  
  public void setSharedValueCurrent(int paramInt)
  {
    this.mSharedValueCurrent = paramInt;
  }
  
  public void setSharedValueID(int paramInt)
  {
    this.mSharedValueID = paramInt;
  }
  
  public void setStateTransition(int paramInt)
  {
    this.mOnStateTransition = paramInt;
  }
  
  boolean supports(int paramInt)
  {
    int i = this.mOnStateTransition;
    boolean bool3 = false;
    boolean bool1 = false;
    boolean bool2 = false;
    if (i == 1)
    {
      bool1 = bool2;
      if (paramInt == 0) {
        bool1 = true;
      }
      return bool1;
    }
    if (i == 2)
    {
      bool1 = bool3;
      if (paramInt == 1) {
        bool1 = true;
      }
      return bool1;
    }
    if (i == 3)
    {
      if (paramInt == 0) {
        bool1 = true;
      }
      return bool1;
    }
    return false;
  }
  
  public String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder().append("ViewTransition(");
    String str = Debug.getName(this.mContext, this.mId);
    Log5ECF72.a(str);
    LogE84000.a(str);
    Log229316.a(str);
    return str + ")";
  }
  
  static class Animate
  {
    boolean hold_at_100 = false;
    KeyCache mCache = new KeyCache();
    private final int mClearsTag;
    float mDpositionDt;
    int mDuration;
    Interpolator mInterpolator;
    long mLastRender;
    MotionController mMC;
    float mPosition;
    private final int mSetsTag;
    long mStart;
    Rect mTempRec = new Rect();
    int mUpDuration;
    ViewTransitionController mVtController;
    boolean reverse = false;
    
    Animate(ViewTransitionController paramViewTransitionController, MotionController paramMotionController, int paramInt1, int paramInt2, int paramInt3, Interpolator paramInterpolator, int paramInt4, int paramInt5)
    {
      this.mVtController = paramViewTransitionController;
      this.mMC = paramMotionController;
      this.mDuration = paramInt1;
      this.mUpDuration = paramInt2;
      long l = System.nanoTime();
      this.mStart = l;
      this.mLastRender = l;
      this.mVtController.addAnimation(this);
      this.mInterpolator = paramInterpolator;
      this.mSetsTag = paramInt4;
      this.mClearsTag = paramInt5;
      if (paramInt3 == 3) {
        this.hold_at_100 = true;
      }
      float f;
      if (paramInt1 == 0) {
        f = Float.MAX_VALUE;
      } else {
        f = 1.0F / paramInt1;
      }
      this.mDpositionDt = f;
      mutate();
    }
    
    void mutate()
    {
      if (this.reverse) {
        mutateReverse();
      } else {
        mutateForward();
      }
    }
    
    void mutateForward()
    {
      long l1 = System.nanoTime();
      long l2 = this.mLastRender;
      this.mLastRender = l1;
      float f = this.mPosition + (float)((l1 - l2) * 1.0E-6D) * this.mDpositionDt;
      this.mPosition = f;
      if (f >= 1.0F) {
        this.mPosition = 1.0F;
      }
      Object localObject = this.mInterpolator;
      if (localObject == null) {
        f = this.mPosition;
      } else {
        f = ((Interpolator)localObject).getInterpolation(this.mPosition);
      }
      localObject = this.mMC;
      boolean bool = ((MotionController)localObject).interpolate(((MotionController)localObject).mView, f, l1, this.mCache);
      if (this.mPosition >= 1.0F)
      {
        if (this.mSetsTag != -1) {
          this.mMC.getView().setTag(this.mSetsTag, Long.valueOf(System.nanoTime()));
        }
        if (this.mClearsTag != -1) {
          this.mMC.getView().setTag(this.mClearsTag, null);
        }
        if (!this.hold_at_100) {
          this.mVtController.removeAnimation(this);
        }
      }
      if ((this.mPosition < 1.0F) || (bool)) {
        this.mVtController.invalidate();
      }
    }
    
    void mutateReverse()
    {
      long l2 = System.nanoTime();
      long l1 = this.mLastRender;
      this.mLastRender = l2;
      float f = this.mPosition - (float)((l2 - l1) * 1.0E-6D) * this.mDpositionDt;
      this.mPosition = f;
      if (f < 0.0F) {
        this.mPosition = 0.0F;
      }
      Object localObject = this.mInterpolator;
      if (localObject == null) {
        f = this.mPosition;
      } else {
        f = ((Interpolator)localObject).getInterpolation(this.mPosition);
      }
      localObject = this.mMC;
      boolean bool = ((MotionController)localObject).interpolate(((MotionController)localObject).mView, f, l2, this.mCache);
      if (this.mPosition <= 0.0F)
      {
        if (this.mSetsTag != -1) {
          this.mMC.getView().setTag(this.mSetsTag, Long.valueOf(System.nanoTime()));
        }
        if (this.mClearsTag != -1) {
          this.mMC.getView().setTag(this.mClearsTag, null);
        }
        this.mVtController.removeAnimation(this);
      }
      if ((this.mPosition > 0.0F) || (bool)) {
        this.mVtController.invalidate();
      }
    }
    
    public void reactTo(int paramInt, float paramFloat1, float paramFloat2)
    {
      switch (paramInt)
      {
      default: 
        break;
      case 2: 
        this.mMC.getView().getHitRect(this.mTempRec);
        if ((!this.mTempRec.contains((int)paramFloat1, (int)paramFloat2)) && (!this.reverse)) {
          reverse(true);
        }
        break;
      case 1: 
        if (!this.reverse) {
          reverse(true);
        }
        return;
      }
    }
    
    void reverse(boolean paramBoolean)
    {
      this.reverse = paramBoolean;
      if (paramBoolean)
      {
        int i = this.mUpDuration;
        if (i != -1)
        {
          float f;
          if (i == 0) {
            f = Float.MAX_VALUE;
          } else {
            f = 1.0F / i;
          }
          this.mDpositionDt = f;
        }
      }
      this.mVtController.invalidate();
      this.mLastRender = System.nanoTime();
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/constraintlayout/motion/widget/ViewTransition.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */