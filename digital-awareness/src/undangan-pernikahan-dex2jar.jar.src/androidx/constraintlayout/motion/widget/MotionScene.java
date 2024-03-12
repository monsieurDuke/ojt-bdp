package androidx.constraintlayout.motion.widget;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.util.SparseIntArray;
import android.util.TypedValue;
import android.util.Xml;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AnimationUtils;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.BounceInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.OvershootInterpolator;
import androidx.constraintlayout.core.motion.utils.Easing;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.constraintlayout.widget.R.id;
import androidx.constraintlayout.widget.R.styleable;
import androidx.constraintlayout.widget.StateSet;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;
import mt.Log229316;
import mt.Log5ECF72;
import mt.LogE84000;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

public class MotionScene
{
  static final int ANTICIPATE = 6;
  static final int BOUNCE = 4;
  private static final String CONSTRAINTSET_TAG = "ConstraintSet";
  private static final boolean DEBUG = false;
  static final int EASE_IN = 1;
  static final int EASE_IN_OUT = 0;
  static final int EASE_OUT = 2;
  private static final String INCLUDE_TAG = "include";
  private static final String INCLUDE_TAG_UC = "Include";
  private static final int INTERPOLATOR_REFERENCE_ID = -2;
  private static final String KEYFRAMESET_TAG = "KeyFrameSet";
  public static final int LAYOUT_CALL_MEASURE = 2;
  public static final int LAYOUT_HONOR_REQUEST = 1;
  public static final int LAYOUT_IGNORE_REQUEST = 0;
  static final int LINEAR = 3;
  private static final int MIN_DURATION = 8;
  private static final String MOTIONSCENE_TAG = "MotionScene";
  private static final String ONCLICK_TAG = "OnClick";
  private static final String ONSWIPE_TAG = "OnSwipe";
  static final int OVERSHOOT = 5;
  private static final int SPLINE_STRING = -1;
  private static final String STATESET_TAG = "StateSet";
  private static final String TAG = "MotionScene";
  static final int TRANSITION_BACKWARD = 0;
  static final int TRANSITION_FORWARD = 1;
  private static final String TRANSITION_TAG = "Transition";
  public static final int UNSET = -1;
  private static final String VIEW_TRANSITION = "ViewTransition";
  private boolean DEBUG_DESKTOP = false;
  private ArrayList<Transition> mAbstractTransitionList = new ArrayList();
  private HashMap<String, Integer> mConstraintSetIdMap = new HashMap();
  private SparseArray<ConstraintSet> mConstraintSetMap = new SparseArray();
  Transition mCurrentTransition = null;
  private int mDefaultDuration = 400;
  private Transition mDefaultTransition = null;
  private SparseIntArray mDeriveMap = new SparseIntArray();
  private boolean mDisableAutoTransition = false;
  private boolean mIgnoreTouch = false;
  private MotionEvent mLastTouchDown;
  float mLastTouchX;
  float mLastTouchY;
  private int mLayoutDuringTransition = 0;
  private final MotionLayout mMotionLayout;
  private boolean mMotionOutsideRegion = false;
  private boolean mRtl;
  StateSet mStateSet = null;
  private ArrayList<Transition> mTransitionList = new ArrayList();
  private MotionLayout.MotionTracker mVelocityTracker;
  final ViewTransitionController mViewTransitionController;
  
  MotionScene(Context paramContext, MotionLayout paramMotionLayout, int paramInt)
  {
    this.mMotionLayout = paramMotionLayout;
    this.mViewTransitionController = new ViewTransitionController(paramMotionLayout);
    load(paramContext, paramInt);
    this.mConstraintSetMap.put(R.id.motion_base, new ConstraintSet());
    this.mConstraintSetIdMap.put("motion_base", Integer.valueOf(R.id.motion_base));
  }
  
  public MotionScene(MotionLayout paramMotionLayout)
  {
    this.mMotionLayout = paramMotionLayout;
    this.mViewTransitionController = new ViewTransitionController(paramMotionLayout);
  }
  
  private int getId(Context paramContext, String paramString)
  {
    int i = -1;
    if (paramString.contains("/"))
    {
      String str = paramString.substring(paramString.indexOf('/') + 1);
      j = paramContext.getResources().getIdentifier(str, "id", paramContext.getPackageName());
      i = j;
      if (this.DEBUG_DESKTOP)
      {
        System.out.println("id getMap res = " + j);
        i = j;
      }
    }
    int j = i;
    if (i == -1) {
      if ((paramString != null) && (paramString.length() > 1))
      {
        j = Integer.parseInt(paramString.substring(1));
      }
      else
      {
        Log.e("MotionScene", "error in parsing id");
        j = i;
      }
    }
    return j;
  }
  
  private int getIndex(Transition paramTransition)
  {
    int j = paramTransition.mId;
    if (j != -1)
    {
      for (int i = 0; i < this.mTransitionList.size(); i++) {
        if (((Transition)this.mTransitionList.get(i)).mId == j) {
          return i;
        }
      }
      return -1;
    }
    throw new IllegalArgumentException("The transition must have an id");
  }
  
  static String getLine(Context paramContext, int paramInt, XmlPullParser paramXmlPullParser)
  {
    StringBuilder localStringBuilder = new StringBuilder().append(".(");
    paramContext = Debug.getName(paramContext, paramInt);
    Log5ECF72.a(paramContext);
    LogE84000.a(paramContext);
    Log229316.a(paramContext);
    return paramContext + ".xml:" + paramXmlPullParser.getLineNumber() + ") \"" + paramXmlPullParser.getName() + "\"";
  }
  
  private int getRealID(int paramInt)
  {
    StateSet localStateSet = this.mStateSet;
    if (localStateSet != null)
    {
      int i = localStateSet.stateGetConstraintID(paramInt, -1, -1);
      if (i != -1) {
        return i;
      }
    }
    return paramInt;
  }
  
  private boolean hasCycleDependency(int paramInt)
  {
    int j = this.mDeriveMap.get(paramInt);
    for (int i = this.mDeriveMap.size(); j > 0; i--)
    {
      if (j == paramInt) {
        return true;
      }
      if (i < 0) {
        return true;
      }
      j = this.mDeriveMap.get(j);
    }
    return false;
  }
  
  private boolean isProcessingTouch()
  {
    boolean bool;
    if (this.mVelocityTracker != null) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  private void load(Context paramContext, int paramInt)
  {
    XmlResourceParser localXmlResourceParser = paramContext.getResources().getXml(paramInt);
    Object localObject1 = null;
    try
    {
      for (int i = localXmlResourceParser.getEventType();; i = localXmlResourceParser.next())
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
          break;
        case 2: 
          Object localObject2 = localXmlResourceParser.getName();
          Object localObject3;
          if (this.DEBUG_DESKTOP)
          {
            localObject3 = System.out;
            StringBuilder localStringBuilder = new java/lang/StringBuilder;
            localStringBuilder.<init>();
            ((PrintStream)localObject3).println("parsing = " + (String)localObject2);
          }
          i = ((String)localObject2).hashCode();
          switch (i)
          {
          }
          for (;;)
          {
            break;
            if (((String)localObject2).equals("include"))
            {
              i = 6;
              break label376;
              if (((String)localObject2).equals("StateSet"))
              {
                i = 4;
                break label376;
                if (((String)localObject2).equals("MotionScene"))
                {
                  i = 0;
                  break label376;
                  if (((String)localObject2).equals("OnSwipe"))
                  {
                    i = 2;
                    break label376;
                    if (((String)localObject2).equals("OnClick"))
                    {
                      i = 3;
                      break label376;
                      if (((String)localObject2).equals("Transition"))
                      {
                        i = j;
                        break label376;
                        if (((String)localObject2).equals("ViewTransition"))
                        {
                          i = 9;
                          break label376;
                          if (((String)localObject2).equals("Include"))
                          {
                            i = 7;
                            break label376;
                            if (((String)localObject2).equals("KeyFrameSet"))
                            {
                              i = 8;
                              break label376;
                              if (((String)localObject2).equals("ConstraintSet"))
                              {
                                i = 5;
                                break label376;
                              }
                            }
                          }
                        }
                      }
                    }
                  }
                }
              }
            }
          }
          i = -1;
          switch (i)
          {
          default: 
            localObject2 = localObject1;
            break;
          case 9: 
            localObject2 = new androidx/constraintlayout/motion/widget/ViewTransition;
            ((ViewTransition)localObject2).<init>(paramContext, localXmlResourceParser);
            this.mViewTransitionController.add((ViewTransition)localObject2);
            localObject2 = localObject1;
            break;
          case 8: 
            localObject3 = new androidx/constraintlayout/motion/widget/KeyFrames;
            ((KeyFrames)localObject3).<init>(paramContext, localXmlResourceParser);
            localObject2 = localObject1;
            if (localObject1 != null)
            {
              ((Transition)localObject1).mKeyFramesList.add(localObject3);
              localObject2 = localObject1;
            }
            break;
          case 6: 
          case 7: 
            parseInclude(paramContext, localXmlResourceParser);
            localObject2 = localObject1;
            break;
          case 5: 
            parseConstraintSet(paramContext, localXmlResourceParser);
            localObject2 = localObject1;
            break;
          case 4: 
            localObject2 = new androidx/constraintlayout/widget/StateSet;
            ((StateSet)localObject2).<init>(paramContext, localXmlResourceParser);
            this.mStateSet = ((StateSet)localObject2);
            localObject2 = localObject1;
            break;
          case 3: 
            localObject2 = localObject1;
            if (localObject1 != null)
            {
              ((Transition)localObject1).addOnClick(paramContext, localXmlResourceParser);
              localObject2 = localObject1;
            }
            break;
          case 2: 
            if (localObject1 == null)
            {
              localObject2 = paramContext.getResources().getResourceEntryName(paramInt);
              i = localXmlResourceParser.getLineNumber();
              localObject3 = new java/lang/StringBuilder;
              ((StringBuilder)localObject3).<init>();
              Log.v("MotionScene", " OnSwipe (" + (String)localObject2 + ".xml:" + i + ")");
            }
            localObject2 = localObject1;
            if (localObject1 != null)
            {
              localObject2 = new androidx/constraintlayout/motion/widget/TouchResponse;
              ((TouchResponse)localObject2).<init>(paramContext, this.mMotionLayout, localXmlResourceParser);
              Transition.access$202((Transition)localObject1, (TouchResponse)localObject2);
              localObject2 = localObject1;
            }
            break;
          case 1: 
            localObject3 = this.mTransitionList;
            localObject2 = new androidx/constraintlayout/motion/widget/MotionScene$Transition;
            ((Transition)localObject2).<init>(this, paramContext, localXmlResourceParser);
            localObject1 = localObject2;
            ((ArrayList)localObject3).add(localObject2);
            if ((this.mCurrentTransition == null) && (!((Transition)localObject1).mIsAbstract))
            {
              this.mCurrentTransition = ((Transition)localObject1);
              if (((Transition)localObject1).mTouchResponse != null) {
                this.mCurrentTransition.mTouchResponse.setRTL(this.mRtl);
              }
            }
            localObject2 = localObject1;
            if (((Transition)localObject1).mIsAbstract)
            {
              if (((Transition)localObject1).mConstraintSetEnd == -1) {
                this.mDefaultTransition = ((Transition)localObject1);
              } else {
                this.mAbstractTransitionList.add(localObject1);
              }
              this.mTransitionList.remove(localObject1);
              localObject2 = localObject1;
            }
            break;
          case 0: 
            parseMotionSceneTags(paramContext, localXmlResourceParser);
            localObject2 = localObject1;
          }
          localObject1 = localObject2;
          break;
        case 0: 
          label376:
          localXmlResourceParser.getName();
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
  
  private int parseConstraintSet(Context paramContext, XmlPullParser paramXmlPullParser)
  {
    ConstraintSet localConstraintSet = new ConstraintSet();
    int j = 0;
    localConstraintSet.setForceId(false);
    int i1 = paramXmlPullParser.getAttributeCount();
    int k = 0;
    int m = -1;
    int n = -1;
    while (k < i1)
    {
      String str2 = paramXmlPullParser.getAttributeName(k);
      String str1 = paramXmlPullParser.getAttributeValue(k);
      if (this.DEBUG_DESKTOP) {
        System.out.println("id string = " + str1);
      }
      switch (str2.hashCode())
      {
      }
      for (;;)
      {
        break;
        if (str2.equals("id"))
        {
          i = j;
          break label189;
          if (str2.equals("constraintRotate"))
          {
            i = 2;
            break label189;
            if (str2.equals("deriveConstraintsFrom"))
            {
              i = 1;
              break label189;
            }
          }
        }
      }
      int i = -1;
      switch (i)
      {
      default: 
        i = j;
        break;
      case 2: 
        try
        {
          localConstraintSet.mRotate = Integer.parseInt(str1);
          i = j;
        }
        catch (NumberFormatException localNumberFormatException)
        {
          switch (str1.hashCode())
          {
          }
          for (;;)
          {
            break;
            if (str1.equals("x_right"))
            {
              i = 3;
              break label381;
              if (str1.equals("right"))
              {
                i = 1;
                break label381;
                if (str1.equals("none"))
                {
                  i = 0;
                  break label381;
                  if (str1.equals("left"))
                  {
                    i = 2;
                    break label381;
                    if (str1.equals("x_left"))
                    {
                      i = 4;
                      break label381;
                    }
                  }
                }
              }
            }
          }
          i = -1;
          switch (i)
          {
          default: 
            i = 0;
          }
        }
        localConstraintSet.mRotate = 4;
        i = 0;
        break;
        localConstraintSet.mRotate = 3;
        i = 0;
        break;
        localConstraintSet.mRotate = 2;
        i = 0;
        break;
        localConstraintSet.mRotate = 1;
        i = 0;
        break;
        i = 0;
        localConstraintSet.mRotate = 0;
        break;
      case 1: 
        m = getId(paramContext, str1);
        i = j;
        break;
      case 0: 
        label189:
        label381:
        n = getId(paramContext, str1);
        HashMap localHashMap = this.mConstraintSetIdMap;
        str1 = stripID(str1);
        Log5ECF72.a(str1);
        LogE84000.a(str1);
        Log229316.a(str1);
        localHashMap.put(str1, Integer.valueOf(n));
        str1 = Debug.getName(paramContext, n);
        Log5ECF72.a(str1);
        LogE84000.a(str1);
        Log229316.a(str1);
        localConstraintSet.mIdString = str1;
        i = j;
      }
      k++;
      j = i;
    }
    if (n != -1)
    {
      if (this.mMotionLayout.mDebugPath != 0) {
        localConstraintSet.setValidateOnParse(true);
      }
      localConstraintSet.load(paramContext, paramXmlPullParser);
      if (m != -1) {
        this.mDeriveMap.put(n, m);
      }
      this.mConstraintSetMap.put(n, localConstraintSet);
    }
    return n;
  }
  
  private int parseInclude(Context paramContext, int paramInt)
  {
    XmlResourceParser localXmlResourceParser = paramContext.getResources().getXml(paramInt);
    try
    {
      for (paramInt = localXmlResourceParser.getEventType(); paramInt != 1; paramInt = localXmlResourceParser.next())
      {
        String str = localXmlResourceParser.getName();
        if ((2 == paramInt) && ("ConstraintSet".equals(str))) {
          return parseConstraintSet(paramContext, localXmlResourceParser);
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
    return -1;
  }
  
  private void parseInclude(Context paramContext, XmlPullParser paramXmlPullParser)
  {
    paramXmlPullParser = paramContext.obtainStyledAttributes(Xml.asAttributeSet(paramXmlPullParser), R.styleable.include);
    int j = paramXmlPullParser.getIndexCount();
    for (int i = 0; i < j; i++)
    {
      int k = paramXmlPullParser.getIndex(i);
      if (k == R.styleable.include_constraintSet) {
        parseInclude(paramContext, paramXmlPullParser.getResourceId(k, -1));
      }
    }
    paramXmlPullParser.recycle();
  }
  
  private void parseMotionSceneTags(Context paramContext, XmlPullParser paramXmlPullParser)
  {
    paramContext = paramContext.obtainStyledAttributes(Xml.asAttributeSet(paramXmlPullParser), R.styleable.MotionScene);
    int j = paramContext.getIndexCount();
    for (int i = 0; i < j; i++)
    {
      int k = paramContext.getIndex(i);
      if (k == R.styleable.MotionScene_defaultDuration)
      {
        k = paramContext.getInt(k, this.mDefaultDuration);
        this.mDefaultDuration = k;
        if (k < 8) {
          this.mDefaultDuration = 8;
        }
      }
      else if (k == R.styleable.MotionScene_layoutDuringTransition)
      {
        this.mLayoutDuringTransition = paramContext.getInteger(k, 0);
      }
    }
    paramContext.recycle();
  }
  
  private void readConstraintChain(int paramInt, MotionLayout paramMotionLayout)
  {
    Object localObject = (ConstraintSet)this.mConstraintSetMap.get(paramInt);
    ((ConstraintSet)localObject).derivedState = ((ConstraintSet)localObject).mIdString;
    paramInt = this.mDeriveMap.get(paramInt);
    if (paramInt > 0)
    {
      readConstraintChain(paramInt, paramMotionLayout);
      paramMotionLayout = (ConstraintSet)this.mConstraintSetMap.get(paramInt);
      if (paramMotionLayout == null)
      {
        localObject = new StringBuilder().append("ERROR! invalid deriveConstraintsFrom: @id/");
        paramMotionLayout = Debug.getName(this.mMotionLayout.getContext(), paramInt);
        Log5ECF72.a(paramMotionLayout);
        LogE84000.a(paramMotionLayout);
        Log229316.a(paramMotionLayout);
        Log.e("MotionScene", paramMotionLayout);
        return;
      }
      ((ConstraintSet)localObject).derivedState = (((ConstraintSet)localObject).derivedState + "/" + paramMotionLayout.derivedState);
      ((ConstraintSet)localObject).readFallback(paramMotionLayout);
    }
    else
    {
      ((ConstraintSet)localObject).derivedState += "  layout";
      ((ConstraintSet)localObject).readFallback(paramMotionLayout);
    }
    ((ConstraintSet)localObject).applyDeltaFrom((ConstraintSet)localObject);
  }
  
  public static String stripID(String paramString)
  {
    if (paramString == null) {
      return "";
    }
    int i = paramString.indexOf('/');
    if (i < 0) {
      return paramString;
    }
    return paramString.substring(i + 1);
  }
  
  public void addOnClickListeners(MotionLayout paramMotionLayout, int paramInt)
  {
    Object localObject1 = this.mTransitionList.iterator();
    while (((Iterator)localObject1).hasNext())
    {
      localObject2 = (Transition)((Iterator)localObject1).next();
      if (((Transition)localObject2).mOnClicks.size() > 0)
      {
        localObject2 = ((Transition)localObject2).mOnClicks.iterator();
        while (((Iterator)localObject2).hasNext()) {
          ((MotionScene.Transition.TransitionOnClick)((Iterator)localObject2).next()).removeOnClickListeners(paramMotionLayout);
        }
      }
    }
    localObject1 = this.mAbstractTransitionList.iterator();
    while (((Iterator)localObject1).hasNext())
    {
      localObject2 = (Transition)((Iterator)localObject1).next();
      if (((Transition)localObject2).mOnClicks.size() > 0)
      {
        localObject2 = ((Transition)localObject2).mOnClicks.iterator();
        while (((Iterator)localObject2).hasNext()) {
          ((MotionScene.Transition.TransitionOnClick)((Iterator)localObject2).next()).removeOnClickListeners(paramMotionLayout);
        }
      }
    }
    Object localObject2 = this.mTransitionList.iterator();
    while (((Iterator)localObject2).hasNext())
    {
      localObject1 = (Transition)((Iterator)localObject2).next();
      if (((Transition)localObject1).mOnClicks.size() > 0)
      {
        localIterator = ((Transition)localObject1).mOnClicks.iterator();
        while (localIterator.hasNext()) {
          ((MotionScene.Transition.TransitionOnClick)localIterator.next()).addOnClickListeners(paramMotionLayout, paramInt, (Transition)localObject1);
        }
      }
    }
    Iterator localIterator = this.mAbstractTransitionList.iterator();
    while (localIterator.hasNext())
    {
      localObject2 = (Transition)localIterator.next();
      if (((Transition)localObject2).mOnClicks.size() > 0)
      {
        localObject1 = ((Transition)localObject2).mOnClicks.iterator();
        while (((Iterator)localObject1).hasNext()) {
          ((MotionScene.Transition.TransitionOnClick)((Iterator)localObject1).next()).addOnClickListeners(paramMotionLayout, paramInt, (Transition)localObject2);
        }
      }
    }
  }
  
  public void addTransition(Transition paramTransition)
  {
    int i = getIndex(paramTransition);
    if (i == -1) {
      this.mTransitionList.add(paramTransition);
    } else {
      this.mTransitionList.set(i, paramTransition);
    }
  }
  
  public boolean applyViewTransition(int paramInt, MotionController paramMotionController)
  {
    return this.mViewTransitionController.applyViewTransition(paramInt, paramMotionController);
  }
  
  boolean autoTransition(MotionLayout paramMotionLayout, int paramInt)
  {
    if (isProcessingTouch()) {
      return false;
    }
    if (this.mDisableAutoTransition) {
      return false;
    }
    Iterator localIterator = this.mTransitionList.iterator();
    while (localIterator.hasNext())
    {
      Transition localTransition1 = (Transition)localIterator.next();
      if (localTransition1.mAutoTransition != 0)
      {
        Transition localTransition2 = this.mCurrentTransition;
        if ((localTransition2 != localTransition1) || (!localTransition2.isTransitionFlag(2)))
        {
          if ((paramInt == localTransition1.mConstraintSetStart) && ((localTransition1.mAutoTransition == 4) || (localTransition1.mAutoTransition == 2)))
          {
            paramMotionLayout.setState(MotionLayout.TransitionState.FINISHED);
            paramMotionLayout.setTransition(localTransition1);
            if (localTransition1.mAutoTransition == 4)
            {
              paramMotionLayout.transitionToEnd();
              paramMotionLayout.setState(MotionLayout.TransitionState.SETUP);
              paramMotionLayout.setState(MotionLayout.TransitionState.MOVING);
            }
            else
            {
              paramMotionLayout.setProgress(1.0F);
              paramMotionLayout.evaluate(true);
              paramMotionLayout.setState(MotionLayout.TransitionState.SETUP);
              paramMotionLayout.setState(MotionLayout.TransitionState.MOVING);
              paramMotionLayout.setState(MotionLayout.TransitionState.FINISHED);
              paramMotionLayout.onNewStateAttachHandlers();
            }
            return true;
          }
          if ((paramInt == localTransition1.mConstraintSetEnd) && ((localTransition1.mAutoTransition == 3) || (localTransition1.mAutoTransition == 1)))
          {
            paramMotionLayout.setState(MotionLayout.TransitionState.FINISHED);
            paramMotionLayout.setTransition(localTransition1);
            if (localTransition1.mAutoTransition == 3)
            {
              paramMotionLayout.transitionToStart();
              paramMotionLayout.setState(MotionLayout.TransitionState.SETUP);
              paramMotionLayout.setState(MotionLayout.TransitionState.MOVING);
            }
            else
            {
              paramMotionLayout.setProgress(0.0F);
              paramMotionLayout.evaluate(true);
              paramMotionLayout.setState(MotionLayout.TransitionState.SETUP);
              paramMotionLayout.setState(MotionLayout.TransitionState.MOVING);
              paramMotionLayout.setState(MotionLayout.TransitionState.FINISHED);
              paramMotionLayout.onNewStateAttachHandlers();
            }
            return true;
          }
        }
      }
    }
    return false;
  }
  
  public Transition bestTransitionFor(int paramInt, float paramFloat1, float paramFloat2, MotionEvent paramMotionEvent)
  {
    if (paramInt != -1)
    {
      Object localObject1 = getTransitionsWithState(paramInt);
      float f2 = 0.0F;
      Object localObject2 = null;
      RectF localRectF1 = new RectF();
      localObject1 = ((List)localObject1).iterator();
      while (((Iterator)localObject1).hasNext())
      {
        Transition localTransition = (Transition)((Iterator)localObject1).next();
        if (!localTransition.mDisable)
        {
          float f3;
          if (localTransition.mTouchResponse != null)
          {
            localTransition.mTouchResponse.setRTL(this.mRtl);
            RectF localRectF2 = localTransition.mTouchResponse.getTouchRegion(this.mMotionLayout, localRectF1);
            if ((localRectF2 != null) && (paramMotionEvent != null) && (!localRectF2.contains(paramMotionEvent.getX(), paramMotionEvent.getY()))) {
              continue;
            }
            localRectF2 = localTransition.mTouchResponse.getLimitBoundsTo(this.mMotionLayout, localRectF1);
            if ((localRectF2 != null) && (paramMotionEvent != null) && (!localRectF2.contains(paramMotionEvent.getX(), paramMotionEvent.getY()))) {
              continue;
            }
            float f1 = localTransition.mTouchResponse.dot(paramFloat1, paramFloat2);
            if ((localTransition.mTouchResponse.mIsRotateMode) && (paramMotionEvent != null))
            {
              f1 = paramMotionEvent.getX() - localTransition.mTouchResponse.mRotateCenterX;
              f3 = paramMotionEvent.getY() - localTransition.mTouchResponse.mRotateCenterY;
              f1 = 10.0F * (float)(Math.atan2(paramFloat2 + f3, paramFloat1 + f1) - Math.atan2(f1, f3));
            }
            if (localTransition.mConstraintSetEnd == paramInt) {
              f1 *= -1.0F;
            } else {
              f1 *= 1.1F;
            }
            f3 = f2;
            if (f1 > f2)
            {
              localObject2 = localTransition;
              f3 = f1;
            }
          }
          else
          {
            f3 = f2;
          }
          f2 = f3;
        }
      }
      return (Transition)localObject2;
    }
    return this.mCurrentTransition;
  }
  
  public void disableAutoTransition(boolean paramBoolean)
  {
    this.mDisableAutoTransition = paramBoolean;
  }
  
  public void enableViewTransition(int paramInt, boolean paramBoolean)
  {
    this.mViewTransitionController.enableViewTransition(paramInt, paramBoolean);
  }
  
  public int gatPathMotionArc()
  {
    Transition localTransition = this.mCurrentTransition;
    int i;
    if (localTransition != null) {
      i = localTransition.mPathMotionArc;
    } else {
      i = -1;
    }
    return i;
  }
  
  int getAutoCompleteMode()
  {
    Transition localTransition = this.mCurrentTransition;
    if ((localTransition != null) && (localTransition.mTouchResponse != null)) {
      return this.mCurrentTransition.mTouchResponse.getAutoCompleteMode();
    }
    return 0;
  }
  
  ConstraintSet getConstraintSet(int paramInt)
  {
    return getConstraintSet(paramInt, -1, -1);
  }
  
  ConstraintSet getConstraintSet(int paramInt1, int paramInt2, int paramInt3)
  {
    if (this.DEBUG_DESKTOP)
    {
      System.out.println("id " + paramInt1);
      System.out.println("size " + this.mConstraintSetMap.size());
    }
    Object localObject = this.mStateSet;
    int i = paramInt1;
    if (localObject != null)
    {
      paramInt2 = ((StateSet)localObject).stateGetConstraintID(paramInt1, paramInt2, paramInt3);
      i = paramInt1;
      if (paramInt2 != -1) {
        i = paramInt2;
      }
    }
    if (this.mConstraintSetMap.get(i) == null)
    {
      localObject = new StringBuilder().append("Warning could not find ConstraintSet id/");
      String str = Debug.getName(this.mMotionLayout.getContext(), i);
      Log5ECF72.a(str);
      LogE84000.a(str);
      Log229316.a(str);
      Log.e("MotionScene", str + " In MotionScene");
      localObject = this.mConstraintSetMap;
      return (ConstraintSet)((SparseArray)localObject).get(((SparseArray)localObject).keyAt(0));
    }
    return (ConstraintSet)this.mConstraintSetMap.get(i);
  }
  
  public ConstraintSet getConstraintSet(Context paramContext, String paramString)
  {
    if (this.DEBUG_DESKTOP)
    {
      System.out.println("id " + paramString);
      System.out.println("size " + this.mConstraintSetMap.size());
    }
    for (int i = 0; i < this.mConstraintSetMap.size(); i++)
    {
      int j = this.mConstraintSetMap.keyAt(i);
      String str = paramContext.getResources().getResourceName(j);
      if (this.DEBUG_DESKTOP) {
        System.out.println("Id for <" + i + "> is <" + str + "> looking for <" + paramString + ">");
      }
      if (paramString.equals(str)) {
        return (ConstraintSet)this.mConstraintSetMap.get(j);
      }
    }
    return null;
  }
  
  public int[] getConstraintSetIds()
  {
    int[] arrayOfInt = new int[this.mConstraintSetMap.size()];
    for (int i = 0; i < arrayOfInt.length; i++) {
      arrayOfInt[i] = this.mConstraintSetMap.keyAt(i);
    }
    return arrayOfInt;
  }
  
  public ArrayList<Transition> getDefinedTransitions()
  {
    return this.mTransitionList;
  }
  
  public int getDuration()
  {
    Transition localTransition = this.mCurrentTransition;
    if (localTransition != null) {
      return localTransition.mDuration;
    }
    return this.mDefaultDuration;
  }
  
  int getEndId()
  {
    Transition localTransition = this.mCurrentTransition;
    if (localTransition == null) {
      return -1;
    }
    return localTransition.mConstraintSetEnd;
  }
  
  public Interpolator getInterpolator()
  {
    switch (this.mCurrentTransition.mDefaultInterpolator)
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
      String str = this.mCurrentTransition.mDefaultInterpolatorString;
      Log5ECF72.a(str);
      LogE84000.a(str);
      Log229316.a(str);
      new Interpolator()
      {
        public float getInterpolation(float paramAnonymousFloat)
        {
          return (float)this.val$easing.get(paramAnonymousFloat);
        }
      };
    }
    return AnimationUtils.loadInterpolator(this.mMotionLayout.getContext(), this.mCurrentTransition.mDefaultInterpolatorID);
  }
  
  Key getKeyFrame(Context paramContext, int paramInt1, int paramInt2, int paramInt3)
  {
    paramContext = this.mCurrentTransition;
    if (paramContext == null) {
      return null;
    }
    paramContext = paramContext.mKeyFramesList.iterator();
    while (paramContext.hasNext())
    {
      KeyFrames localKeyFrames = (KeyFrames)paramContext.next();
      Iterator localIterator1 = localKeyFrames.getKeys().iterator();
      while (localIterator1.hasNext())
      {
        Object localObject = (Integer)localIterator1.next();
        if (paramInt2 == ((Integer)localObject).intValue())
        {
          Iterator localIterator2 = localKeyFrames.getKeyFramesForView(((Integer)localObject).intValue()).iterator();
          while (localIterator2.hasNext())
          {
            localObject = (Key)localIterator2.next();
            if ((((Key)localObject).mFramePosition == paramInt3) && (((Key)localObject).mType == paramInt1)) {
              return (Key)localObject;
            }
          }
        }
      }
    }
    return null;
  }
  
  public void getKeyFrames(MotionController paramMotionController)
  {
    Object localObject = this.mCurrentTransition;
    if (localObject == null)
    {
      localObject = this.mDefaultTransition;
      if (localObject != null)
      {
        localObject = ((Transition)localObject).mKeyFramesList.iterator();
        while (((Iterator)localObject).hasNext()) {
          ((KeyFrames)((Iterator)localObject).next()).addFrames(paramMotionController);
        }
      }
      return;
    }
    localObject = ((Transition)localObject).mKeyFramesList.iterator();
    while (((Iterator)localObject).hasNext()) {
      ((KeyFrames)((Iterator)localObject).next()).addFrames(paramMotionController);
    }
  }
  
  float getMaxAcceleration()
  {
    Transition localTransition = this.mCurrentTransition;
    if ((localTransition != null) && (localTransition.mTouchResponse != null)) {
      return this.mCurrentTransition.mTouchResponse.getMaxAcceleration();
    }
    return 0.0F;
  }
  
  float getMaxVelocity()
  {
    Transition localTransition = this.mCurrentTransition;
    if ((localTransition != null) && (localTransition.mTouchResponse != null)) {
      return this.mCurrentTransition.mTouchResponse.getMaxVelocity();
    }
    return 0.0F;
  }
  
  boolean getMoveWhenScrollAtTop()
  {
    Transition localTransition = this.mCurrentTransition;
    if ((localTransition != null) && (localTransition.mTouchResponse != null)) {
      return this.mCurrentTransition.mTouchResponse.getMoveWhenScrollAtTop();
    }
    return false;
  }
  
  public float getPathPercent(View paramView, int paramInt)
  {
    return 0.0F;
  }
  
  float getProgressDirection(float paramFloat1, float paramFloat2)
  {
    Transition localTransition = this.mCurrentTransition;
    if ((localTransition != null) && (localTransition.mTouchResponse != null)) {
      return this.mCurrentTransition.mTouchResponse.getProgressDirection(paramFloat1, paramFloat2);
    }
    return 0.0F;
  }
  
  int getSpringBoundary()
  {
    Transition localTransition = this.mCurrentTransition;
    if ((localTransition != null) && (localTransition.mTouchResponse != null)) {
      return this.mCurrentTransition.mTouchResponse.getSpringBoundary();
    }
    return 0;
  }
  
  float getSpringDamping()
  {
    Transition localTransition = this.mCurrentTransition;
    if ((localTransition != null) && (localTransition.mTouchResponse != null)) {
      return this.mCurrentTransition.mTouchResponse.getSpringDamping();
    }
    return 0.0F;
  }
  
  float getSpringMass()
  {
    Transition localTransition = this.mCurrentTransition;
    if ((localTransition != null) && (localTransition.mTouchResponse != null)) {
      return this.mCurrentTransition.mTouchResponse.getSpringMass();
    }
    return 0.0F;
  }
  
  float getSpringStiffiness()
  {
    Transition localTransition = this.mCurrentTransition;
    if ((localTransition != null) && (localTransition.mTouchResponse != null)) {
      return this.mCurrentTransition.mTouchResponse.getSpringStiffness();
    }
    return 0.0F;
  }
  
  float getSpringStopThreshold()
  {
    Transition localTransition = this.mCurrentTransition;
    if ((localTransition != null) && (localTransition.mTouchResponse != null)) {
      return this.mCurrentTransition.mTouchResponse.getSpringStopThreshold();
    }
    return 0.0F;
  }
  
  public float getStaggered()
  {
    Transition localTransition = this.mCurrentTransition;
    if (localTransition != null) {
      return localTransition.mStagger;
    }
    return 0.0F;
  }
  
  int getStartId()
  {
    Transition localTransition = this.mCurrentTransition;
    if (localTransition == null) {
      return -1;
    }
    return localTransition.mConstraintSetStart;
  }
  
  public Transition getTransitionById(int paramInt)
  {
    Iterator localIterator = this.mTransitionList.iterator();
    while (localIterator.hasNext())
    {
      Transition localTransition = (Transition)localIterator.next();
      if (localTransition.mId == paramInt) {
        return localTransition;
      }
    }
    return null;
  }
  
  int getTransitionDirection(int paramInt)
  {
    Iterator localIterator = this.mTransitionList.iterator();
    while (localIterator.hasNext()) {
      if (((Transition)localIterator.next()).mConstraintSetStart == paramInt) {
        return 0;
      }
    }
    return 1;
  }
  
  public List<Transition> getTransitionsWithState(int paramInt)
  {
    paramInt = getRealID(paramInt);
    ArrayList localArrayList = new ArrayList();
    Iterator localIterator = this.mTransitionList.iterator();
    while (localIterator.hasNext())
    {
      Transition localTransition = (Transition)localIterator.next();
      if ((localTransition.mConstraintSetStart == paramInt) || (localTransition.mConstraintSetEnd == paramInt)) {
        localArrayList.add(localTransition);
      }
    }
    return localArrayList;
  }
  
  boolean hasKeyFramePosition(View paramView, int paramInt)
  {
    Object localObject = this.mCurrentTransition;
    if (localObject == null) {
      return false;
    }
    Iterator localIterator = ((Transition)localObject).mKeyFramesList.iterator();
    while (localIterator.hasNext())
    {
      localObject = ((KeyFrames)localIterator.next()).getKeyFramesForView(paramView.getId()).iterator();
      while (((Iterator)localObject).hasNext()) {
        if (((Key)((Iterator)localObject).next()).mFramePosition == paramInt) {
          return true;
        }
      }
    }
    return false;
  }
  
  public boolean isViewTransitionEnabled(int paramInt)
  {
    return this.mViewTransitionController.isViewTransitionEnabled(paramInt);
  }
  
  public int lookUpConstraintId(String paramString)
  {
    paramString = (Integer)this.mConstraintSetIdMap.get(paramString);
    if (paramString == null) {
      return 0;
    }
    return paramString.intValue();
  }
  
  public String lookUpConstraintName(int paramInt)
  {
    Iterator localIterator = this.mConstraintSetIdMap.entrySet().iterator();
    while (localIterator.hasNext())
    {
      Map.Entry localEntry = (Map.Entry)localIterator.next();
      Integer localInteger = (Integer)localEntry.getValue();
      if (localInteger != null) {
        if (localInteger.intValue() == paramInt) {
          return (String)localEntry.getKey();
        }
      }
    }
    return null;
  }
  
  protected void onLayout(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {}
  
  void processScrollMove(float paramFloat1, float paramFloat2)
  {
    Transition localTransition = this.mCurrentTransition;
    if ((localTransition != null) && (localTransition.mTouchResponse != null)) {
      this.mCurrentTransition.mTouchResponse.scrollMove(paramFloat1, paramFloat2);
    }
  }
  
  void processScrollUp(float paramFloat1, float paramFloat2)
  {
    Transition localTransition = this.mCurrentTransition;
    if ((localTransition != null) && (localTransition.mTouchResponse != null)) {
      this.mCurrentTransition.mTouchResponse.scrollUp(paramFloat1, paramFloat2);
    }
  }
  
  void processTouchEvent(MotionEvent paramMotionEvent, int paramInt, MotionLayout paramMotionLayout)
  {
    Object localObject1 = new RectF();
    if (this.mVelocityTracker == null) {
      this.mVelocityTracker = this.mMotionLayout.obtainVelocityTracker();
    }
    this.mVelocityTracker.addMovement(paramMotionEvent);
    if (paramInt != -1)
    {
      int i = paramMotionEvent.getAction();
      boolean bool = false;
      switch (i)
      {
      case 1: 
      default: 
        break;
      case 2: 
        if (!this.mIgnoreTouch)
        {
          float f2 = paramMotionEvent.getRawY() - this.mLastTouchY;
          float f1 = paramMotionEvent.getRawX() - this.mLastTouchX;
          if ((f1 != 0.0D) || (f2 != 0.0D))
          {
            localObject2 = this.mLastTouchDown;
            if (localObject2 != null) {}
          }
          else
          {
            return;
          }
          Object localObject2 = bestTransitionFor(paramInt, f1, f2, (MotionEvent)localObject2);
          if (localObject2 != null)
          {
            paramMotionLayout.setTransition((Transition)localObject2);
            localObject1 = this.mCurrentTransition.mTouchResponse.getTouchRegion(this.mMotionLayout, (RectF)localObject1);
            if ((localObject1 != null) && (!((RectF)localObject1).contains(this.mLastTouchDown.getX(), this.mLastTouchDown.getY()))) {
              bool = true;
            }
            this.mMotionOutsideRegion = bool;
            this.mCurrentTransition.mTouchResponse.setUpTouchEvent(this.mLastTouchX, this.mLastTouchY);
          }
        }
        break;
      case 0: 
        this.mLastTouchX = paramMotionEvent.getRawX();
        this.mLastTouchY = paramMotionEvent.getRawY();
        this.mLastTouchDown = paramMotionEvent;
        this.mIgnoreTouch = false;
        if (this.mCurrentTransition.mTouchResponse != null)
        {
          paramMotionEvent = this.mCurrentTransition.mTouchResponse.getLimitBoundsTo(this.mMotionLayout, (RectF)localObject1);
          if ((paramMotionEvent != null) && (!paramMotionEvent.contains(this.mLastTouchDown.getX(), this.mLastTouchDown.getY())))
          {
            this.mLastTouchDown = null;
            this.mIgnoreTouch = true;
            return;
          }
          paramMotionEvent = this.mCurrentTransition.mTouchResponse.getTouchRegion(this.mMotionLayout, (RectF)localObject1);
          if ((paramMotionEvent != null) && (!paramMotionEvent.contains(this.mLastTouchDown.getX(), this.mLastTouchDown.getY()))) {
            this.mMotionOutsideRegion = true;
          } else {
            this.mMotionOutsideRegion = false;
          }
          this.mCurrentTransition.mTouchResponse.setDown(this.mLastTouchX, this.mLastTouchY);
        }
        return;
      }
    }
    if (this.mIgnoreTouch) {
      return;
    }
    localObject1 = this.mCurrentTransition;
    if ((localObject1 != null) && (((Transition)localObject1).mTouchResponse != null) && (!this.mMotionOutsideRegion)) {
      this.mCurrentTransition.mTouchResponse.processTouchEvent(paramMotionEvent, this.mVelocityTracker, paramInt, this);
    }
    this.mLastTouchX = paramMotionEvent.getRawX();
    this.mLastTouchY = paramMotionEvent.getRawY();
    if (paramMotionEvent.getAction() == 1)
    {
      paramMotionEvent = this.mVelocityTracker;
      if (paramMotionEvent != null)
      {
        paramMotionEvent.recycle();
        this.mVelocityTracker = null;
        if (paramMotionLayout.mCurrentState != -1) {
          autoTransition(paramMotionLayout, paramMotionLayout.mCurrentState);
        }
      }
    }
  }
  
  void readFallback(MotionLayout paramMotionLayout)
  {
    for (int i = 0; i < this.mConstraintSetMap.size(); i++)
    {
      int j = this.mConstraintSetMap.keyAt(i);
      if (hasCycleDependency(j))
      {
        Log.e("MotionScene", "Cannot be derived from yourself");
        return;
      }
      readConstraintChain(j, paramMotionLayout);
    }
  }
  
  public void removeTransition(Transition paramTransition)
  {
    int i = getIndex(paramTransition);
    if (i != -1) {
      this.mTransitionList.remove(i);
    }
  }
  
  public void setConstraintSet(int paramInt, ConstraintSet paramConstraintSet)
  {
    this.mConstraintSetMap.put(paramInt, paramConstraintSet);
  }
  
  public void setDuration(int paramInt)
  {
    Transition localTransition = this.mCurrentTransition;
    if (localTransition != null) {
      localTransition.setDuration(paramInt);
    } else {
      this.mDefaultDuration = paramInt;
    }
  }
  
  public void setKeyframe(View paramView, int paramInt, String paramString, Object paramObject)
  {
    Object localObject = this.mCurrentTransition;
    if (localObject == null) {
      return;
    }
    localObject = ((Transition)localObject).mKeyFramesList.iterator();
    while (((Iterator)localObject).hasNext())
    {
      Iterator localIterator = ((KeyFrames)((Iterator)localObject).next()).getKeyFramesForView(paramView.getId()).iterator();
      while (localIterator.hasNext()) {
        if (((Key)localIterator.next()).mFramePosition == paramInt)
        {
          float f = 0.0F;
          if (paramObject != null) {
            f = ((Float)paramObject).floatValue();
          }
          if (f == 0.0F) {}
          paramString.equalsIgnoreCase("app:PerpendicularPath_percent");
        }
      }
    }
  }
  
  public void setRtl(boolean paramBoolean)
  {
    this.mRtl = paramBoolean;
    Transition localTransition = this.mCurrentTransition;
    if ((localTransition != null) && (localTransition.mTouchResponse != null)) {
      this.mCurrentTransition.mTouchResponse.setRTL(this.mRtl);
    }
  }
  
  void setTransition(int paramInt1, int paramInt2)
  {
    int i = paramInt1;
    int j = paramInt2;
    Object localObject1 = this.mStateSet;
    int k = i;
    int m = j;
    if (localObject1 != null)
    {
      k = ((StateSet)localObject1).stateGetConstraintID(paramInt1, -1, -1);
      if (k != -1) {
        i = k;
      }
      int n = this.mStateSet.stateGetConstraintID(paramInt2, -1, -1);
      k = i;
      m = j;
      if (n != -1)
      {
        m = n;
        k = i;
      }
    }
    localObject1 = this.mCurrentTransition;
    if ((localObject1 != null) && (((Transition)localObject1).mConstraintSetEnd == paramInt2) && (this.mCurrentTransition.mConstraintSetStart == paramInt1)) {
      return;
    }
    Object localObject2 = this.mTransitionList.iterator();
    while (((Iterator)localObject2).hasNext())
    {
      localObject1 = (Transition)((Iterator)localObject2).next();
      if (((((Transition)localObject1).mConstraintSetEnd == m) && (((Transition)localObject1).mConstraintSetStart == k)) || ((((Transition)localObject1).mConstraintSetEnd == paramInt2) && (((Transition)localObject1).mConstraintSetStart == paramInt1)))
      {
        this.mCurrentTransition = ((Transition)localObject1);
        if ((localObject1 != null) && (((Transition)localObject1).mTouchResponse != null)) {
          this.mCurrentTransition.mTouchResponse.setRTL(this.mRtl);
        }
        return;
      }
    }
    localObject1 = this.mDefaultTransition;
    Iterator localIterator = this.mAbstractTransitionList.iterator();
    while (localIterator.hasNext())
    {
      localObject2 = (Transition)localIterator.next();
      if (((Transition)localObject2).mConstraintSetEnd == paramInt2) {
        localObject1 = localObject2;
      }
    }
    localObject1 = new Transition(this, (Transition)localObject1);
    Transition.access$102((Transition)localObject1, k);
    Transition.access$002((Transition)localObject1, m);
    if (k != -1) {
      this.mTransitionList.add(localObject1);
    }
    this.mCurrentTransition = ((Transition)localObject1);
  }
  
  public void setTransition(Transition paramTransition)
  {
    this.mCurrentTransition = paramTransition;
    if ((paramTransition != null) && (paramTransition.mTouchResponse != null)) {
      this.mCurrentTransition.mTouchResponse.setRTL(this.mRtl);
    }
  }
  
  void setupTouch()
  {
    Transition localTransition = this.mCurrentTransition;
    if ((localTransition != null) && (localTransition.mTouchResponse != null)) {
      this.mCurrentTransition.mTouchResponse.setupTouch();
    }
  }
  
  boolean supportTouch()
  {
    Object localObject = this.mTransitionList.iterator();
    boolean bool1;
    for (;;)
    {
      boolean bool2 = ((Iterator)localObject).hasNext();
      bool1 = true;
      if (!bool2) {
        break;
      }
      if (((Transition)((Iterator)localObject).next()).mTouchResponse != null) {
        return true;
      }
    }
    localObject = this.mCurrentTransition;
    if ((localObject == null) || (((Transition)localObject).mTouchResponse == null)) {
      bool1 = false;
    }
    return bool1;
  }
  
  public boolean validateLayout(MotionLayout paramMotionLayout)
  {
    boolean bool;
    if ((paramMotionLayout == this.mMotionLayout) && (paramMotionLayout.mScene == this)) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public void viewTransition(int paramInt, View... paramVarArgs)
  {
    this.mViewTransitionController.viewTransition(paramInt, paramVarArgs);
  }
  
  public static class Transition
  {
    public static final int AUTO_ANIMATE_TO_END = 4;
    public static final int AUTO_ANIMATE_TO_START = 3;
    public static final int AUTO_JUMP_TO_END = 2;
    public static final int AUTO_JUMP_TO_START = 1;
    public static final int AUTO_NONE = 0;
    public static final int INTERPOLATE_ANTICIPATE = 6;
    public static final int INTERPOLATE_BOUNCE = 4;
    public static final int INTERPOLATE_EASE_IN = 1;
    public static final int INTERPOLATE_EASE_IN_OUT = 0;
    public static final int INTERPOLATE_EASE_OUT = 2;
    public static final int INTERPOLATE_LINEAR = 3;
    public static final int INTERPOLATE_OVERSHOOT = 5;
    public static final int INTERPOLATE_REFERENCE_ID = -2;
    public static final int INTERPOLATE_SPLINE_STRING = -1;
    static final int TRANSITION_FLAG_FIRST_DRAW = 1;
    static final int TRANSITION_FLAG_INTERCEPT_TOUCH = 4;
    static final int TRANSITION_FLAG_INTRA_AUTO = 2;
    private int mAutoTransition = 0;
    private int mConstraintSetEnd = -1;
    private int mConstraintSetStart = -1;
    private int mDefaultInterpolator = 0;
    private int mDefaultInterpolatorID = -1;
    private String mDefaultInterpolatorString = null;
    private boolean mDisable = false;
    private int mDuration = 400;
    private int mId = -1;
    private boolean mIsAbstract = false;
    private ArrayList<KeyFrames> mKeyFramesList = new ArrayList();
    private int mLayoutDuringTransition = 0;
    private final MotionScene mMotionScene;
    private ArrayList<TransitionOnClick> mOnClicks = new ArrayList();
    private int mPathMotionArc = -1;
    private float mStagger = 0.0F;
    private TouchResponse mTouchResponse = null;
    private int mTransitionFlags = 0;
    
    public Transition(int paramInt1, MotionScene paramMotionScene, int paramInt2, int paramInt3)
    {
      this.mId = paramInt1;
      this.mMotionScene = paramMotionScene;
      this.mConstraintSetStart = paramInt2;
      this.mConstraintSetEnd = paramInt3;
      this.mDuration = paramMotionScene.mDefaultDuration;
      this.mLayoutDuringTransition = paramMotionScene.mLayoutDuringTransition;
    }
    
    Transition(MotionScene paramMotionScene, Context paramContext, XmlPullParser paramXmlPullParser)
    {
      this.mDuration = paramMotionScene.mDefaultDuration;
      this.mLayoutDuringTransition = paramMotionScene.mLayoutDuringTransition;
      this.mMotionScene = paramMotionScene;
      fillFromAttributeList(paramMotionScene, paramContext, Xml.asAttributeSet(paramXmlPullParser));
    }
    
    Transition(MotionScene paramMotionScene, Transition paramTransition)
    {
      this.mMotionScene = paramMotionScene;
      this.mDuration = paramMotionScene.mDefaultDuration;
      if (paramTransition != null)
      {
        this.mPathMotionArc = paramTransition.mPathMotionArc;
        this.mDefaultInterpolator = paramTransition.mDefaultInterpolator;
        this.mDefaultInterpolatorString = paramTransition.mDefaultInterpolatorString;
        this.mDefaultInterpolatorID = paramTransition.mDefaultInterpolatorID;
        this.mDuration = paramTransition.mDuration;
        this.mKeyFramesList = paramTransition.mKeyFramesList;
        this.mStagger = paramTransition.mStagger;
        this.mLayoutDuringTransition = paramTransition.mLayoutDuringTransition;
      }
    }
    
    private void fill(MotionScene paramMotionScene, Context paramContext, TypedArray paramTypedArray)
    {
      int j = paramTypedArray.getIndexCount();
      for (int i = 0; i < j; i++)
      {
        int k = paramTypedArray.getIndex(i);
        Object localObject;
        if (k == R.styleable.Transition_constraintSetEnd)
        {
          this.mConstraintSetEnd = paramTypedArray.getResourceId(k, -1);
          localObject = paramContext.getResources().getResourceTypeName(this.mConstraintSetEnd);
          if ("layout".equals(localObject))
          {
            localObject = new ConstraintSet();
            ((ConstraintSet)localObject).load(paramContext, this.mConstraintSetEnd);
            paramMotionScene.mConstraintSetMap.append(this.mConstraintSetEnd, localObject);
          }
          else if ("xml".equals(localObject))
          {
            this.mConstraintSetEnd = paramMotionScene.parseInclude(paramContext, this.mConstraintSetEnd);
          }
        }
        else if (k == R.styleable.Transition_constraintSetStart)
        {
          this.mConstraintSetStart = paramTypedArray.getResourceId(k, this.mConstraintSetStart);
          localObject = paramContext.getResources().getResourceTypeName(this.mConstraintSetStart);
          if ("layout".equals(localObject))
          {
            localObject = new ConstraintSet();
            ((ConstraintSet)localObject).load(paramContext, this.mConstraintSetStart);
            paramMotionScene.mConstraintSetMap.append(this.mConstraintSetStart, localObject);
          }
          else if ("xml".equals(localObject))
          {
            this.mConstraintSetStart = paramMotionScene.parseInclude(paramContext, this.mConstraintSetStart);
          }
        }
        else if (k == R.styleable.Transition_motionInterpolator)
        {
          localObject = paramTypedArray.peekValue(k);
          if (((TypedValue)localObject).type == 1)
          {
            k = paramTypedArray.getResourceId(k, -1);
            this.mDefaultInterpolatorID = k;
            if (k != -1) {
              this.mDefaultInterpolator = -2;
            }
          }
          else if (((TypedValue)localObject).type == 3)
          {
            localObject = paramTypedArray.getString(k);
            this.mDefaultInterpolatorString = ((String)localObject);
            if (localObject != null) {
              if (((String)localObject).indexOf("/") > 0)
              {
                this.mDefaultInterpolatorID = paramTypedArray.getResourceId(k, -1);
                this.mDefaultInterpolator = -2;
              }
              else
              {
                this.mDefaultInterpolator = -1;
              }
            }
          }
          else
          {
            this.mDefaultInterpolator = paramTypedArray.getInteger(k, this.mDefaultInterpolator);
          }
        }
        else if (k == R.styleable.Transition_duration)
        {
          k = paramTypedArray.getInt(k, this.mDuration);
          this.mDuration = k;
          if (k < 8) {
            this.mDuration = 8;
          }
        }
        else if (k == R.styleable.Transition_staggered)
        {
          this.mStagger = paramTypedArray.getFloat(k, this.mStagger);
        }
        else if (k == R.styleable.Transition_autoTransition)
        {
          this.mAutoTransition = paramTypedArray.getInteger(k, this.mAutoTransition);
        }
        else if (k == R.styleable.Transition_android_id)
        {
          this.mId = paramTypedArray.getResourceId(k, this.mId);
        }
        else if (k == R.styleable.Transition_transitionDisable)
        {
          this.mDisable = paramTypedArray.getBoolean(k, this.mDisable);
        }
        else if (k == R.styleable.Transition_pathMotionArc)
        {
          this.mPathMotionArc = paramTypedArray.getInteger(k, -1);
        }
        else if (k == R.styleable.Transition_layoutDuringTransition)
        {
          this.mLayoutDuringTransition = paramTypedArray.getInteger(k, 0);
        }
        else if (k == R.styleable.Transition_transitionFlags)
        {
          this.mTransitionFlags = paramTypedArray.getInteger(k, 0);
        }
      }
      if (this.mConstraintSetStart == -1) {
        this.mIsAbstract = true;
      }
    }
    
    private void fillFromAttributeList(MotionScene paramMotionScene, Context paramContext, AttributeSet paramAttributeSet)
    {
      paramAttributeSet = paramContext.obtainStyledAttributes(paramAttributeSet, R.styleable.Transition);
      fill(paramMotionScene, paramContext, paramAttributeSet);
      paramAttributeSet.recycle();
    }
    
    public void addKeyFrame(KeyFrames paramKeyFrames)
    {
      this.mKeyFramesList.add(paramKeyFrames);
    }
    
    public void addOnClick(int paramInt1, int paramInt2)
    {
      Iterator localIterator = this.mOnClicks.iterator();
      while (localIterator.hasNext())
      {
        localTransitionOnClick = (TransitionOnClick)localIterator.next();
        if (localTransitionOnClick.mTargetId == paramInt1)
        {
          localTransitionOnClick.mMode = paramInt2;
          return;
        }
      }
      TransitionOnClick localTransitionOnClick = new TransitionOnClick(this, paramInt1, paramInt2);
      this.mOnClicks.add(localTransitionOnClick);
    }
    
    public void addOnClick(Context paramContext, XmlPullParser paramXmlPullParser)
    {
      this.mOnClicks.add(new TransitionOnClick(paramContext, this, paramXmlPullParser));
    }
    
    public String debugString(Context paramContext)
    {
      String str;
      if (this.mConstraintSetStart == -1) {
        str = "null";
      } else {
        str = paramContext.getResources().getResourceEntryName(this.mConstraintSetStart);
      }
      if (this.mConstraintSetEnd == -1) {
        paramContext = str + " -> null";
      } else {
        paramContext = str + " -> " + paramContext.getResources().getResourceEntryName(this.mConstraintSetEnd);
      }
      return paramContext;
    }
    
    public int getAutoTransition()
    {
      return this.mAutoTransition;
    }
    
    public int getDuration()
    {
      return this.mDuration;
    }
    
    public int getEndConstraintSetId()
    {
      return this.mConstraintSetEnd;
    }
    
    public int getId()
    {
      return this.mId;
    }
    
    public List<KeyFrames> getKeyFrameList()
    {
      return this.mKeyFramesList;
    }
    
    public int getLayoutDuringTransition()
    {
      return this.mLayoutDuringTransition;
    }
    
    public List<TransitionOnClick> getOnClickList()
    {
      return this.mOnClicks;
    }
    
    public int getPathMotionArc()
    {
      return this.mPathMotionArc;
    }
    
    public float getStagger()
    {
      return this.mStagger;
    }
    
    public int getStartConstraintSetId()
    {
      return this.mConstraintSetStart;
    }
    
    public TouchResponse getTouchResponse()
    {
      return this.mTouchResponse;
    }
    
    public boolean isEnabled()
    {
      return this.mDisable ^ true;
    }
    
    public boolean isTransitionFlag(int paramInt)
    {
      boolean bool;
      if ((this.mTransitionFlags & paramInt) != 0) {
        bool = true;
      } else {
        bool = false;
      }
      return bool;
    }
    
    public void removeOnClick(int paramInt)
    {
      Object localObject2 = null;
      Iterator localIterator = this.mOnClicks.iterator();
      Object localObject1;
      for (;;)
      {
        localObject1 = localObject2;
        if (!localIterator.hasNext()) {
          break;
        }
        localObject1 = (TransitionOnClick)localIterator.next();
        if (((TransitionOnClick)localObject1).mTargetId == paramInt) {
          break;
        }
      }
      if (localObject1 != null) {
        this.mOnClicks.remove(localObject1);
      }
    }
    
    public void setAutoTransition(int paramInt)
    {
      this.mAutoTransition = paramInt;
    }
    
    public void setDuration(int paramInt)
    {
      this.mDuration = Math.max(paramInt, 8);
    }
    
    public void setEnable(boolean paramBoolean)
    {
      setEnabled(paramBoolean);
    }
    
    public void setEnabled(boolean paramBoolean)
    {
      this.mDisable = (paramBoolean ^ true);
    }
    
    public void setInterpolatorInfo(int paramInt1, String paramString, int paramInt2)
    {
      this.mDefaultInterpolator = paramInt1;
      this.mDefaultInterpolatorString = paramString;
      this.mDefaultInterpolatorID = paramInt2;
    }
    
    public void setLayoutDuringTransition(int paramInt)
    {
      this.mLayoutDuringTransition = paramInt;
    }
    
    public void setOnSwipe(OnSwipe paramOnSwipe)
    {
      if (paramOnSwipe == null) {
        paramOnSwipe = null;
      } else {
        paramOnSwipe = new TouchResponse(this.mMotionScene.mMotionLayout, paramOnSwipe);
      }
      this.mTouchResponse = paramOnSwipe;
    }
    
    public void setOnTouchUp(int paramInt)
    {
      TouchResponse localTouchResponse = getTouchResponse();
      if (localTouchResponse != null) {
        localTouchResponse.setTouchUpMode(paramInt);
      }
    }
    
    public void setPathMotionArc(int paramInt)
    {
      this.mPathMotionArc = paramInt;
    }
    
    public void setStagger(float paramFloat)
    {
      this.mStagger = paramFloat;
    }
    
    public void setTransitionFlag(int paramInt)
    {
      this.mTransitionFlags = paramInt;
    }
    
    public static class TransitionOnClick
      implements View.OnClickListener
    {
      public static final int ANIM_TOGGLE = 17;
      public static final int ANIM_TO_END = 1;
      public static final int ANIM_TO_START = 16;
      public static final int JUMP_TO_END = 256;
      public static final int JUMP_TO_START = 4096;
      int mMode = 17;
      int mTargetId = -1;
      private final MotionScene.Transition mTransition;
      
      public TransitionOnClick(Context paramContext, MotionScene.Transition paramTransition, XmlPullParser paramXmlPullParser)
      {
        this.mTransition = paramTransition;
        paramContext = paramContext.obtainStyledAttributes(Xml.asAttributeSet(paramXmlPullParser), R.styleable.OnClick);
        int j = paramContext.getIndexCount();
        for (int i = 0; i < j; i++)
        {
          int k = paramContext.getIndex(i);
          if (k == R.styleable.OnClick_targetId) {
            this.mTargetId = paramContext.getResourceId(k, this.mTargetId);
          } else if (k == R.styleable.OnClick_clickAction) {
            this.mMode = paramContext.getInt(k, this.mMode);
          }
        }
        paramContext.recycle();
      }
      
      public TransitionOnClick(MotionScene.Transition paramTransition, int paramInt1, int paramInt2)
      {
        this.mTransition = paramTransition;
        this.mTargetId = paramInt1;
        this.mMode = paramInt2;
      }
      
      public void addOnClickListeners(MotionLayout paramMotionLayout, int paramInt, MotionScene.Transition paramTransition)
      {
        int i = this.mTargetId;
        if (i != -1) {
          paramMotionLayout = paramMotionLayout.findViewById(i);
        }
        if (paramMotionLayout == null)
        {
          Log.e("MotionScene", "OnClick could not find id " + this.mTargetId);
          return;
        }
        int k = paramTransition.mConstraintSetStart;
        int i3 = paramTransition.mConstraintSetEnd;
        if (k == -1)
        {
          paramMotionLayout.setOnClickListener(this);
          return;
        }
        int i2 = this.mMode;
        int i1 = 0;
        if (((i2 & 0x1) != 0) && (paramInt == k)) {
          i = 1;
        } else {
          i = 0;
        }
        int j;
        if (((i2 & 0x100) != 0) && (paramInt == k)) {
          j = 1;
        } else {
          j = 0;
        }
        if (((i2 & 0x1) != 0) && (paramInt == k)) {
          k = 1;
        } else {
          k = 0;
        }
        int m;
        if (((i2 & 0x10) != 0) && (paramInt == i3)) {
          m = 1;
        } else {
          m = 0;
        }
        int n = i1;
        if ((i2 & 0x1000) != 0)
        {
          n = i1;
          if (paramInt == i3) {
            n = 1;
          }
        }
        if ((i | j | k | m | n) != 0) {
          paramMotionLayout.setOnClickListener(this);
        }
      }
      
      boolean isTransitionViable(MotionScene.Transition paramTransition, MotionLayout paramMotionLayout)
      {
        MotionScene.Transition localTransition = this.mTransition;
        boolean bool2 = true;
        boolean bool1 = true;
        if (localTransition == paramTransition) {
          return true;
        }
        int j = localTransition.mConstraintSetEnd;
        int i = this.mTransition.mConstraintSetStart;
        if (i == -1)
        {
          if (paramMotionLayout.mCurrentState == j) {
            bool1 = false;
          }
          return bool1;
        }
        bool1 = bool2;
        if (paramMotionLayout.mCurrentState != i) {
          if (paramMotionLayout.mCurrentState == j) {
            bool1 = bool2;
          } else {
            bool1 = false;
          }
        }
        return bool1;
      }
      
      public void onClick(View paramView)
      {
        paramView = MotionScene.Transition.access$800(this.mTransition).mMotionLayout;
        if (!paramView.isInteractionEnabled()) {
          return;
        }
        int i;
        if (this.mTransition.mConstraintSetStart == -1)
        {
          i = paramView.getCurrentState();
          if (i == -1)
          {
            paramView.transitionToState(this.mTransition.mConstraintSetEnd);
            return;
          }
          localTransition1 = new MotionScene.Transition(this.mTransition.mMotionScene, this.mTransition);
          MotionScene.Transition.access$102(localTransition1, i);
          MotionScene.Transition.access$002(localTransition1, this.mTransition.mConstraintSetEnd);
          paramView.setTransition(localTransition1);
          paramView.transitionToEnd();
          return;
        }
        MotionScene.Transition localTransition1 = this.mTransition.mMotionScene.mCurrentTransition;
        int j = this.mMode;
        int m = 0;
        if (((j & 0x1) == 0) && ((j & 0x100) == 0)) {
          i = 0;
        } else {
          i = 1;
        }
        if (((j & 0x10) == 0) && ((j & 0x1000) == 0)) {
          j = 0;
        } else {
          j = 1;
        }
        int k = m;
        if (i != 0)
        {
          k = m;
          if (j != 0) {
            k = 1;
          }
        }
        int n = j;
        m = i;
        if (k != 0)
        {
          MotionScene.Transition localTransition3 = this.mTransition.mMotionScene.mCurrentTransition;
          MotionScene.Transition localTransition2 = this.mTransition;
          if (localTransition3 != localTransition2) {
            paramView.setTransition(localTransition2);
          }
          if ((paramView.getCurrentState() != paramView.getEndState()) && (paramView.getProgress() <= 0.5F))
          {
            n = 0;
            m = i;
          }
          else
          {
            m = 0;
            n = j;
          }
        }
        if (isTransitionViable(localTransition1, paramView)) {
          if ((m != 0) && ((0x1 & this.mMode) != 0))
          {
            paramView.setTransition(this.mTransition);
            paramView.transitionToEnd();
          }
          else if ((n != 0) && ((this.mMode & 0x10) != 0))
          {
            paramView.setTransition(this.mTransition);
            paramView.transitionToStart();
          }
          else if ((m != 0) && ((this.mMode & 0x100) != 0))
          {
            paramView.setTransition(this.mTransition);
            paramView.setProgress(1.0F);
          }
          else if ((n != 0) && ((this.mMode & 0x1000) != 0))
          {
            paramView.setTransition(this.mTransition);
            paramView.setProgress(0.0F);
          }
        }
      }
      
      public void removeOnClickListeners(MotionLayout paramMotionLayout)
      {
        int i = this.mTargetId;
        if (i == -1) {
          return;
        }
        paramMotionLayout = paramMotionLayout.findViewById(i);
        if (paramMotionLayout == null)
        {
          Log.e("MotionScene", " (*)  could not find id " + this.mTargetId);
          return;
        }
        paramMotionLayout.setOnClickListener(null);
      }
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/constraintlayout/motion/widget/MotionScene.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */