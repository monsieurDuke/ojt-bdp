package androidx.constraintlayout.motion.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseIntArray;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import androidx.constraintlayout.motion.utils.ViewSpline;
import androidx.constraintlayout.widget.ConstraintAttribute;
import androidx.constraintlayout.widget.R.styleable;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Locale;
import java.util.Set;
import mt.Log229316;
import mt.Log5ECF72;
import mt.LogE84000;

public class KeyTrigger
  extends Key
{
  public static final String CROSS = "CROSS";
  public static final int KEY_TYPE = 5;
  static final String NAME = "KeyTrigger";
  public static final String NEGATIVE_CROSS = "negativeCross";
  public static final String POSITIVE_CROSS = "positiveCross";
  public static final String POST_LAYOUT = "postLayout";
  private static final String TAG = "KeyTrigger";
  public static final String TRIGGER_COLLISION_ID = "triggerCollisionId";
  public static final String TRIGGER_COLLISION_VIEW = "triggerCollisionView";
  public static final String TRIGGER_ID = "triggerID";
  public static final String TRIGGER_RECEIVER = "triggerReceiver";
  public static final String TRIGGER_SLACK = "triggerSlack";
  public static final String VIEW_TRANSITION_ON_CROSS = "viewTransitionOnCross";
  public static final String VIEW_TRANSITION_ON_NEGATIVE_CROSS = "viewTransitionOnNegativeCross";
  public static final String VIEW_TRANSITION_ON_POSITIVE_CROSS = "viewTransitionOnPositiveCross";
  RectF mCollisionRect = new RectF();
  private String mCross = null;
  private int mCurveFit = -1;
  private boolean mFireCrossReset = true;
  private float mFireLastPos;
  private boolean mFireNegativeReset = true;
  private boolean mFirePositiveReset = true;
  private float mFireThreshold = NaN.0F;
  HashMap<String, Method> mMethodHashMap = new HashMap();
  private String mNegativeCross = null;
  private String mPositiveCross = null;
  private boolean mPostLayout = false;
  RectF mTargetRect = new RectF();
  private int mTriggerCollisionId = UNSET;
  private View mTriggerCollisionView = null;
  private int mTriggerID = UNSET;
  private int mTriggerReceiver = UNSET;
  float mTriggerSlack = 0.1F;
  int mViewTransitionOnCross = UNSET;
  int mViewTransitionOnNegativeCross = UNSET;
  int mViewTransitionOnPositiveCross = UNSET;
  
  public KeyTrigger()
  {
    this.mType = 5;
    this.mCustomConstraints = new HashMap();
  }
  
  private void fire(String paramString, View paramView)
  {
    if (paramString == null) {
      return;
    }
    if (paramString.startsWith("."))
    {
      fireCustom(paramString, paramView);
      return;
    }
    Object localObject1 = null;
    if (this.mMethodHashMap.containsKey(paramString))
    {
      localObject2 = (Method)this.mMethodHashMap.get(paramString);
      localObject1 = localObject2;
      if (localObject2 == null) {
        return;
      }
    }
    Object localObject2 = localObject1;
    if (localObject1 == null) {
      try
      {
        localObject2 = paramView.getClass().getMethod(paramString, new Class[0]);
        this.mMethodHashMap.put(paramString, localObject2);
      }
      catch (NoSuchMethodException localNoSuchMethodException)
      {
        this.mMethodHashMap.put(paramString, null);
        paramString = new StringBuilder().append("Could not find method \"").append(paramString).append("\"on class ").append(paramView.getClass().getSimpleName()).append(" ");
        paramView = Debug.getName(paramView);
        Log5ECF72.a(paramView);
        LogE84000.a(paramView);
        Log229316.a(paramView);
        Log.e("KeyTrigger", paramView);
        return;
      }
    }
    try
    {
      ((Method)localObject2).invoke(paramView, new Object[0]);
    }
    catch (Exception paramString)
    {
      paramString = new StringBuilder().append("Exception in call \"").append(this.mCross).append("\"on class ").append(paramView.getClass().getSimpleName()).append(" ");
      paramView = Debug.getName(paramView);
      Log5ECF72.a(paramView);
      LogE84000.a(paramView);
      Log229316.a(paramView);
      Log.e("KeyTrigger", paramView);
    }
  }
  
  private void fireCustom(String paramString, View paramView)
  {
    int i;
    if (paramString.length() == 1) {
      i = 1;
    } else {
      i = 0;
    }
    String str1 = paramString;
    if (i == 0) {
      str1 = paramString.substring(1).toLowerCase(Locale.ROOT);
    }
    paramString = this.mCustomConstraints.keySet().iterator();
    while (paramString.hasNext())
    {
      Object localObject = (String)paramString.next();
      String str2 = ((String)localObject).toLowerCase(Locale.ROOT);
      if ((i != 0) || (str2.matches(str1)))
      {
        localObject = (ConstraintAttribute)this.mCustomConstraints.get(localObject);
        if (localObject != null) {
          ((ConstraintAttribute)localObject).applyCustom(paramView);
        }
      }
    }
  }
  
  private void setUpRect(RectF paramRectF, View paramView, boolean paramBoolean)
  {
    paramRectF.top = paramView.getTop();
    paramRectF.bottom = paramView.getBottom();
    paramRectF.left = paramView.getLeft();
    paramRectF.right = paramView.getRight();
    if (paramBoolean) {
      paramView.getMatrix().mapRect(paramRectF);
    }
  }
  
  public void addValues(HashMap<String, ViewSpline> paramHashMap) {}
  
  public Key clone()
  {
    return new KeyTrigger().copy(this);
  }
  
  public void conditionallyFire(float paramFloat, View paramView)
  {
    int i2 = 0;
    int i3 = 0;
    int i1 = 0;
    int i = 0;
    int j = 0;
    int k = 0;
    int m = 0;
    int n = 0;
    boolean bool4 = false;
    boolean bool3 = false;
    boolean bool2 = false;
    boolean bool1 = false;
    if (this.mTriggerCollisionId != UNSET)
    {
      if (this.mTriggerCollisionView == null) {
        this.mTriggerCollisionView = ((ViewGroup)paramView.getParent()).findViewById(this.mTriggerCollisionId);
      }
      setUpRect(this.mCollisionRect, this.mTriggerCollisionView, this.mPostLayout);
      setUpRect(this.mTargetRect, paramView, this.mPostLayout);
      if (this.mCollisionRect.intersect(this.mTargetRect))
      {
        if (this.mFireCrossReset)
        {
          i = 1;
          this.mFireCrossReset = false;
        }
        if (this.mFirePositiveReset)
        {
          bool1 = true;
          this.mFirePositiveReset = false;
        }
        this.mFireNegativeReset = true;
      }
      else
      {
        i = i2;
        if (!this.mFireCrossReset)
        {
          i = 1;
          this.mFireCrossReset = true;
        }
        j = n;
        if (this.mFireNegativeReset)
        {
          j = 1;
          this.mFireNegativeReset = false;
        }
        this.mFirePositiveReset = true;
        bool1 = bool4;
      }
      k = i;
      m = j;
    }
    else
    {
      float f1;
      if (this.mFireCrossReset)
      {
        f1 = this.mFireThreshold;
        i = i3;
        if ((paramFloat - f1) * (this.mFireLastPos - f1) < 0.0F)
        {
          i = 1;
          this.mFireCrossReset = false;
        }
      }
      else
      {
        i = i1;
        if (Math.abs(paramFloat - this.mFireThreshold) > this.mTriggerSlack)
        {
          this.mFireCrossReset = true;
          i = i1;
        }
      }
      float f2;
      if (this.mFireNegativeReset)
      {
        f2 = this.mFireThreshold;
        f1 = paramFloat - f2;
        j = k;
        if (f1 * (this.mFireLastPos - f2) < 0.0F)
        {
          j = k;
          if (f1 < 0.0F)
          {
            j = 1;
            this.mFireNegativeReset = false;
          }
        }
      }
      else
      {
        j = m;
        if (Math.abs(paramFloat - this.mFireThreshold) > this.mTriggerSlack)
        {
          this.mFireNegativeReset = true;
          j = m;
        }
      }
      if (this.mFirePositiveReset)
      {
        f2 = this.mFireThreshold;
        f1 = paramFloat - f2;
        bool1 = bool3;
        if (f1 * (this.mFireLastPos - f2) < 0.0F)
        {
          bool1 = bool3;
          if (f1 > 0.0F)
          {
            bool1 = true;
            this.mFirePositiveReset = false;
          }
        }
        k = i;
        m = j;
      }
      else
      {
        k = i;
        m = j;
        bool1 = bool2;
        if (Math.abs(paramFloat - this.mFireThreshold) > this.mTriggerSlack)
        {
          this.mFirePositiveReset = true;
          bool1 = bool2;
          m = j;
          k = i;
        }
      }
    }
    this.mFireLastPos = paramFloat;
    if ((m != 0) || (k != 0) || (bool1)) {
      ((MotionLayout)paramView.getParent()).fireTrigger(this.mTriggerID, bool1, paramFloat);
    }
    View localView;
    if (this.mTriggerReceiver == UNSET) {
      localView = paramView;
    } else {
      localView = ((MotionLayout)paramView.getParent()).findViewById(this.mTriggerReceiver);
    }
    String str;
    if (m != 0)
    {
      str = this.mNegativeCross;
      if (str != null) {
        fire(str, localView);
      }
      if (this.mViewTransitionOnNegativeCross != UNSET) {
        ((MotionLayout)paramView.getParent()).viewTransition(this.mViewTransitionOnNegativeCross, new View[] { localView });
      }
    }
    if (bool1)
    {
      str = this.mPositiveCross;
      if (str != null) {
        fire(str, localView);
      }
      if (this.mViewTransitionOnPositiveCross != UNSET) {
        ((MotionLayout)paramView.getParent()).viewTransition(this.mViewTransitionOnPositiveCross, new View[] { localView });
      }
    }
    if (k != 0)
    {
      str = this.mCross;
      if (str != null) {
        fire(str, localView);
      }
      if (this.mViewTransitionOnCross != UNSET) {
        ((MotionLayout)paramView.getParent()).viewTransition(this.mViewTransitionOnCross, new View[] { localView });
      }
    }
  }
  
  public Key copy(Key paramKey)
  {
    super.copy(paramKey);
    paramKey = (KeyTrigger)paramKey;
    this.mCurveFit = paramKey.mCurveFit;
    this.mCross = paramKey.mCross;
    this.mTriggerReceiver = paramKey.mTriggerReceiver;
    this.mNegativeCross = paramKey.mNegativeCross;
    this.mPositiveCross = paramKey.mPositiveCross;
    this.mTriggerID = paramKey.mTriggerID;
    this.mTriggerCollisionId = paramKey.mTriggerCollisionId;
    this.mTriggerCollisionView = paramKey.mTriggerCollisionView;
    this.mTriggerSlack = paramKey.mTriggerSlack;
    this.mFireCrossReset = paramKey.mFireCrossReset;
    this.mFireNegativeReset = paramKey.mFireNegativeReset;
    this.mFirePositiveReset = paramKey.mFirePositiveReset;
    this.mFireThreshold = paramKey.mFireThreshold;
    this.mFireLastPos = paramKey.mFireLastPos;
    this.mPostLayout = paramKey.mPostLayout;
    this.mCollisionRect = paramKey.mCollisionRect;
    this.mTargetRect = paramKey.mTargetRect;
    this.mMethodHashMap = paramKey.mMethodHashMap;
    return this;
  }
  
  public void getAttributeNames(HashSet<String> paramHashSet) {}
  
  int getCurveFit()
  {
    return this.mCurveFit;
  }
  
  public void load(Context paramContext, AttributeSet paramAttributeSet)
  {
    Loader.read(this, paramContext.obtainStyledAttributes(paramAttributeSet, R.styleable.KeyTrigger), paramContext);
  }
  
  public void setValue(String paramString, Object paramObject)
  {
    switch (paramString.hashCode())
    {
    }
    for (;;)
    {
      break;
      if (paramString.equals("triggerReceiver"))
      {
        i = 1;
        break label291;
        if (paramString.equals("postLayout"))
        {
          i = 8;
          break label291;
          if (paramString.equals("viewTransitionOnCross"))
          {
            i = 11;
            break label291;
            if (paramString.equals("triggerSlack"))
            {
              i = 7;
              break label291;
              if (paramString.equals("CROSS"))
              {
                i = 0;
                break label291;
                if (paramString.equals("viewTransitionOnNegativeCross"))
                {
                  i = 9;
                  break label291;
                  if (paramString.equals("triggerCollisionView"))
                  {
                    i = 6;
                    break label291;
                    if (paramString.equals("negativeCross"))
                    {
                      i = 2;
                      break label291;
                      if (paramString.equals("triggerID"))
                      {
                        i = 4;
                        break label291;
                        if (paramString.equals("triggerCollisionId"))
                        {
                          i = 5;
                          break label291;
                          if (paramString.equals("viewTransitionOnPositiveCross"))
                          {
                            i = 10;
                            break label291;
                            if (paramString.equals("positiveCross"))
                            {
                              i = 3;
                              break label291;
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
      }
    }
    int i = -1;
    switch (i)
    {
    default: 
      break;
    case 11: 
      this.mViewTransitionOnCross = toInt(paramObject);
      break;
    case 10: 
      this.mViewTransitionOnPositiveCross = toInt(paramObject);
      break;
    case 9: 
      this.mViewTransitionOnNegativeCross = toInt(paramObject);
      break;
    case 8: 
      this.mPostLayout = toBoolean(paramObject);
      break;
    case 7: 
      this.mTriggerSlack = toFloat(paramObject);
      break;
    case 6: 
      this.mTriggerCollisionView = ((View)paramObject);
      break;
    case 5: 
      this.mTriggerCollisionId = toInt(paramObject);
      break;
    case 4: 
      this.mTriggerID = toInt(paramObject);
      break;
    case 3: 
      this.mPositiveCross = paramObject.toString();
      break;
    case 2: 
      this.mNegativeCross = paramObject.toString();
      break;
    case 1: 
      this.mTriggerReceiver = toInt(paramObject);
      break;
    case 0: 
      label291:
      this.mCross = paramObject.toString();
    }
  }
  
  private static class Loader
  {
    private static final int COLLISION = 9;
    private static final int CROSS = 4;
    private static final int FRAME_POS = 8;
    private static final int NEGATIVE_CROSS = 1;
    private static final int POSITIVE_CROSS = 2;
    private static final int POST_LAYOUT = 10;
    private static final int TARGET_ID = 7;
    private static final int TRIGGER_ID = 6;
    private static final int TRIGGER_RECEIVER = 11;
    private static final int TRIGGER_SLACK = 5;
    private static final int VT_CROSS = 12;
    private static final int VT_NEGATIVE_CROSS = 13;
    private static final int VT_POSITIVE_CROSS = 14;
    private static SparseIntArray mAttrMap;
    
    static
    {
      SparseIntArray localSparseIntArray = new SparseIntArray();
      mAttrMap = localSparseIntArray;
      localSparseIntArray.append(R.styleable.KeyTrigger_framePosition, 8);
      mAttrMap.append(R.styleable.KeyTrigger_onCross, 4);
      mAttrMap.append(R.styleable.KeyTrigger_onNegativeCross, 1);
      mAttrMap.append(R.styleable.KeyTrigger_onPositiveCross, 2);
      mAttrMap.append(R.styleable.KeyTrigger_motionTarget, 7);
      mAttrMap.append(R.styleable.KeyTrigger_triggerId, 6);
      mAttrMap.append(R.styleable.KeyTrigger_triggerSlack, 5);
      mAttrMap.append(R.styleable.KeyTrigger_motion_triggerOnCollision, 9);
      mAttrMap.append(R.styleable.KeyTrigger_motion_postLayoutCollision, 10);
      mAttrMap.append(R.styleable.KeyTrigger_triggerReceiver, 11);
      mAttrMap.append(R.styleable.KeyTrigger_viewTransitionOnCross, 12);
      mAttrMap.append(R.styleable.KeyTrigger_viewTransitionOnNegativeCross, 13);
      mAttrMap.append(R.styleable.KeyTrigger_viewTransitionOnPositiveCross, 14);
    }
    
    public static void read(KeyTrigger paramKeyTrigger, TypedArray paramTypedArray, Context paramContext)
    {
      int j = paramTypedArray.getIndexCount();
      for (int i = 0; i < j; i++)
      {
        int k = paramTypedArray.getIndex(i);
        switch (mAttrMap.get(k))
        {
        case 3: 
        default: 
          paramContext = new StringBuilder().append("unused attribute 0x");
          String str = Integer.toHexString(k);
          Log5ECF72.a(str);
          LogE84000.a(str);
          Log229316.a(str);
          Log.e("KeyTrigger", str + "   " + mAttrMap.get(k));
          break;
        case 14: 
          paramKeyTrigger.mViewTransitionOnPositiveCross = paramTypedArray.getResourceId(k, paramKeyTrigger.mViewTransitionOnPositiveCross);
          break;
        case 13: 
          paramKeyTrigger.mViewTransitionOnNegativeCross = paramTypedArray.getResourceId(k, paramKeyTrigger.mViewTransitionOnNegativeCross);
          break;
        case 12: 
          paramKeyTrigger.mViewTransitionOnCross = paramTypedArray.getResourceId(k, paramKeyTrigger.mViewTransitionOnCross);
          break;
        case 11: 
          KeyTrigger.access$702(paramKeyTrigger, paramTypedArray.getResourceId(k, paramKeyTrigger.mTriggerReceiver));
          break;
        case 10: 
          KeyTrigger.access$602(paramKeyTrigger, paramTypedArray.getBoolean(k, paramKeyTrigger.mPostLayout));
          break;
        case 9: 
          KeyTrigger.access$502(paramKeyTrigger, paramTypedArray.getResourceId(k, paramKeyTrigger.mTriggerCollisionId));
          break;
        case 8: 
          paramKeyTrigger.mFramePosition = paramTypedArray.getInteger(k, paramKeyTrigger.mFramePosition);
          KeyTrigger.access$002(paramKeyTrigger, (paramKeyTrigger.mFramePosition + 0.5F) / 100.0F);
          break;
        case 7: 
          if (MotionLayout.IS_IN_EDIT_MODE)
          {
            paramKeyTrigger.mTargetId = paramTypedArray.getResourceId(k, paramKeyTrigger.mTargetId);
            if (paramKeyTrigger.mTargetId == -1) {
              paramKeyTrigger.mTargetString = paramTypedArray.getString(k);
            }
          }
          else if (paramTypedArray.peekValue(k).type == 3)
          {
            paramKeyTrigger.mTargetString = paramTypedArray.getString(k);
          }
          else
          {
            paramKeyTrigger.mTargetId = paramTypedArray.getResourceId(k, paramKeyTrigger.mTargetId);
          }
          break;
        case 6: 
          KeyTrigger.access$402(paramKeyTrigger, paramTypedArray.getResourceId(k, paramKeyTrigger.mTriggerID));
          break;
        case 5: 
          paramKeyTrigger.mTriggerSlack = paramTypedArray.getFloat(k, paramKeyTrigger.mTriggerSlack);
          break;
        case 4: 
          KeyTrigger.access$302(paramKeyTrigger, paramTypedArray.getString(k));
          break;
        case 2: 
          KeyTrigger.access$202(paramKeyTrigger, paramTypedArray.getString(k));
          break;
        case 1: 
          KeyTrigger.access$102(paramKeyTrigger, paramTypedArray.getString(k));
        }
      }
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/constraintlayout/motion/widget/KeyTrigger.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */