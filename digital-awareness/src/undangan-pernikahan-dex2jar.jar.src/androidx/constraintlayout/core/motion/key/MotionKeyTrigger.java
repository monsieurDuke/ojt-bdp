package androidx.constraintlayout.core.motion.key;

import androidx.constraintlayout.core.motion.CustomVariable;
import androidx.constraintlayout.core.motion.MotionWidget;
import androidx.constraintlayout.core.motion.utils.FloatRect;
import androidx.constraintlayout.core.motion.utils.SplineSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Locale;
import java.util.Set;

public class MotionKeyTrigger
  extends MotionKey
{
  public static final String CROSS = "CROSS";
  public static final int KEY_TYPE = 5;
  public static final String NEGATIVE_CROSS = "negativeCross";
  public static final String POSITIVE_CROSS = "positiveCross";
  public static final String POST_LAYOUT = "postLayout";
  private static final String TAG = "KeyTrigger";
  public static final String TRIGGER_COLLISION_ID = "triggerCollisionId";
  public static final String TRIGGER_COLLISION_VIEW = "triggerCollisionView";
  public static final String TRIGGER_ID = "triggerID";
  public static final String TRIGGER_RECEIVER = "triggerReceiver";
  public static final String TRIGGER_SLACK = "triggerSlack";
  public static final int TYPE_CROSS = 312;
  public static final int TYPE_NEGATIVE_CROSS = 310;
  public static final int TYPE_POSITIVE_CROSS = 309;
  public static final int TYPE_POST_LAYOUT = 304;
  public static final int TYPE_TRIGGER_COLLISION_ID = 307;
  public static final int TYPE_TRIGGER_COLLISION_VIEW = 306;
  public static final int TYPE_TRIGGER_ID = 308;
  public static final int TYPE_TRIGGER_RECEIVER = 311;
  public static final int TYPE_TRIGGER_SLACK = 305;
  public static final int TYPE_VIEW_TRANSITION_ON_CROSS = 301;
  public static final int TYPE_VIEW_TRANSITION_ON_NEGATIVE_CROSS = 303;
  public static final int TYPE_VIEW_TRANSITION_ON_POSITIVE_CROSS = 302;
  public static final String VIEW_TRANSITION_ON_CROSS = "viewTransitionOnCross";
  public static final String VIEW_TRANSITION_ON_NEGATIVE_CROSS = "viewTransitionOnNegativeCross";
  public static final String VIEW_TRANSITION_ON_POSITIVE_CROSS = "viewTransitionOnPositiveCross";
  FloatRect mCollisionRect = new FloatRect();
  private String mCross = null;
  private int mCurveFit = -1;
  private boolean mFireCrossReset = true;
  private float mFireLastPos;
  private boolean mFireNegativeReset = true;
  private boolean mFirePositiveReset = true;
  private float mFireThreshold = NaN.0F;
  private String mNegativeCross = null;
  private String mPositiveCross = null;
  private boolean mPostLayout = false;
  FloatRect mTargetRect = new FloatRect();
  private int mTriggerCollisionId = UNSET;
  private int mTriggerID = UNSET;
  private int mTriggerReceiver = UNSET;
  float mTriggerSlack = 0.1F;
  int mViewTransitionOnCross = UNSET;
  int mViewTransitionOnNegativeCross = UNSET;
  int mViewTransitionOnPositiveCross = UNSET;
  
  public MotionKeyTrigger()
  {
    this.mType = 5;
    this.mCustom = new HashMap();
  }
  
  private void fireCustom(String paramString, MotionWidget paramMotionWidget)
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
    paramString = this.mCustom.keySet().iterator();
    while (paramString.hasNext())
    {
      String str2 = (String)paramString.next();
      Object localObject = str2.toLowerCase(Locale.ROOT);
      if ((i != 0) || (((String)localObject).matches(str1)))
      {
        localObject = (CustomVariable)this.mCustom.get(str2);
        if (localObject != null) {
          ((CustomVariable)localObject).applyToWidget(paramMotionWidget);
        }
      }
    }
  }
  
  public void addValues(HashMap<String, SplineSet> paramHashMap) {}
  
  public MotionKey clone()
  {
    return new MotionKeyTrigger().copy(this);
  }
  
  public void conditionallyFire(float paramFloat, MotionWidget paramMotionWidget) {}
  
  public MotionKeyTrigger copy(MotionKey paramMotionKey)
  {
    super.copy(paramMotionKey);
    paramMotionKey = (MotionKeyTrigger)paramMotionKey;
    this.mCurveFit = paramMotionKey.mCurveFit;
    this.mCross = paramMotionKey.mCross;
    this.mTriggerReceiver = paramMotionKey.mTriggerReceiver;
    this.mNegativeCross = paramMotionKey.mNegativeCross;
    this.mPositiveCross = paramMotionKey.mPositiveCross;
    this.mTriggerID = paramMotionKey.mTriggerID;
    this.mTriggerCollisionId = paramMotionKey.mTriggerCollisionId;
    this.mTriggerSlack = paramMotionKey.mTriggerSlack;
    this.mFireCrossReset = paramMotionKey.mFireCrossReset;
    this.mFireNegativeReset = paramMotionKey.mFireNegativeReset;
    this.mFirePositiveReset = paramMotionKey.mFirePositiveReset;
    this.mFireThreshold = paramMotionKey.mFireThreshold;
    this.mFireLastPos = paramMotionKey.mFireLastPos;
    this.mPostLayout = paramMotionKey.mPostLayout;
    this.mCollisionRect = paramMotionKey.mCollisionRect;
    this.mTargetRect = paramMotionKey.mTargetRect;
    return this;
  }
  
  public void getAttributeNames(HashSet<String> paramHashSet) {}
  
  public int getId(String paramString)
  {
    switch (paramString.hashCode())
    {
    }
    for (;;)
    {
      break;
      if (paramString.equals("triggerReceiver"))
      {
        i = 10;
        break label268;
        if (paramString.equals("postLayout"))
        {
          i = 3;
          break label268;
          if (paramString.equals("viewTransitionOnCross"))
          {
            i = 0;
            break label268;
            if (paramString.equals("triggerSlack"))
            {
              i = 4;
              break label268;
              if (paramString.equals("viewTransitionOnNegativeCross"))
              {
                i = 2;
                break label268;
                if (paramString.equals("triggerCollisionView"))
                {
                  i = 5;
                  break label268;
                  if (paramString.equals("negativeCross"))
                  {
                    i = 9;
                    break label268;
                    if (paramString.equals("triggerID"))
                    {
                      i = 7;
                      break label268;
                      if (paramString.equals("triggerCollisionId"))
                      {
                        i = 6;
                        break label268;
                        if (paramString.equals("viewTransitionOnPositiveCross"))
                        {
                          i = 1;
                          break label268;
                          if (paramString.equals("positiveCross"))
                          {
                            i = 8;
                            break label268;
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
      return -1;
    case 10: 
      return 311;
    case 9: 
      return 310;
    case 8: 
      return 309;
    case 7: 
      return 308;
    case 6: 
      return 307;
    case 5: 
      return 306;
    case 4: 
      return 305;
    case 3: 
      return 304;
    case 2: 
      return 303;
    case 1: 
      label268:
      return 302;
    }
    return 301;
  }
  
  public boolean setValue(int paramInt, float paramFloat)
  {
    switch (paramInt)
    {
    default: 
      return super.setValue(paramInt, paramFloat);
    }
    this.mTriggerSlack = paramFloat;
    return true;
  }
  
  public boolean setValue(int paramInt1, int paramInt2)
  {
    switch (paramInt1)
    {
    case 304: 
    case 305: 
    case 306: 
    case 309: 
    case 310: 
    default: 
      return super.setValue(paramInt1, paramInt2);
    case 311: 
      this.mTriggerReceiver = paramInt2;
      break;
    case 308: 
      this.mTriggerID = toInt(Integer.valueOf(paramInt2));
      break;
    case 307: 
      this.mTriggerCollisionId = paramInt2;
      break;
    case 303: 
      this.mViewTransitionOnNegativeCross = paramInt2;
      break;
    case 302: 
      this.mViewTransitionOnPositiveCross = paramInt2;
      break;
    case 301: 
      this.mViewTransitionOnCross = paramInt2;
    }
    return true;
  }
  
  public boolean setValue(int paramInt, String paramString)
  {
    switch (paramInt)
    {
    case 311: 
    default: 
      return super.setValue(paramInt, paramString);
    case 312: 
      this.mCross = paramString;
      break;
    case 310: 
      this.mNegativeCross = paramString;
      break;
    case 309: 
      this.mPositiveCross = paramString;
    }
    return true;
  }
  
  public boolean setValue(int paramInt, boolean paramBoolean)
  {
    switch (paramInt)
    {
    default: 
      return super.setValue(paramInt, paramBoolean);
    }
    this.mPostLayout = paramBoolean;
    return true;
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/constraintlayout/core/motion/key/MotionKeyTrigger.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */