package androidx.constraintlayout.core.state;

import androidx.constraintlayout.core.motion.Motion;
import androidx.constraintlayout.core.motion.MotionWidget;
import androidx.constraintlayout.core.motion.key.MotionKeyAttributes;
import androidx.constraintlayout.core.motion.key.MotionKeyCycle;
import androidx.constraintlayout.core.motion.key.MotionKeyPosition;
import androidx.constraintlayout.core.motion.utils.Easing;
import androidx.constraintlayout.core.motion.utils.KeyCache;
import androidx.constraintlayout.core.motion.utils.TypedBundle;
import androidx.constraintlayout.core.motion.utils.TypedValues;
import androidx.constraintlayout.core.widgets.ConstraintWidget;
import androidx.constraintlayout.core.widgets.ConstraintWidgetContainer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

public class Transition
  implements TypedValues
{
  static final int ANTICIPATE = 6;
  static final int BOUNCE = 4;
  static final int EASE_IN = 1;
  static final int EASE_IN_OUT = 0;
  static final int EASE_OUT = 2;
  public static final int END = 1;
  public static final int INTERPOLATED = 2;
  private static final int INTERPOLATOR_REFERENCE_ID = -2;
  static final int LINEAR = 3;
  static final int OVERSHOOT = 5;
  private static final int SPLINE_STRING = -1;
  public static final int START = 0;
  HashMap<Integer, HashMap<String, KeyPosition>> keyPositions = new HashMap();
  private int mAutoTransition = 0;
  TypedBundle mBundle = new TypedBundle();
  private int mDefaultInterpolator = 0;
  private String mDefaultInterpolatorString = null;
  private int mDuration = 400;
  private Easing mEasing = null;
  private float mStagger = 0.0F;
  private HashMap<String, WidgetState> state = new HashMap();
  
  public static Interpolator getInterpolator(int paramInt, String paramString)
  {
    switch (paramInt)
    {
    default: 
      return null;
    case 6: 
      return new Transition..ExternalSyntheticLambda5();
    case 5: 
      return new Transition..ExternalSyntheticLambda6();
    case 4: 
      return new Transition..ExternalSyntheticLambda7();
    case 3: 
      return new Transition..ExternalSyntheticLambda4();
    case 2: 
      return new Transition..ExternalSyntheticLambda3();
    case 1: 
      return new Transition..ExternalSyntheticLambda2();
    case 0: 
      return new Transition..ExternalSyntheticLambda1();
    }
    return new Transition..ExternalSyntheticLambda0(paramString);
  }
  
  private WidgetState getWidgetState(String paramString)
  {
    return (WidgetState)this.state.get(paramString);
  }
  
  private WidgetState getWidgetState(String paramString, ConstraintWidget paramConstraintWidget, int paramInt)
  {
    WidgetState localWidgetState2 = (WidgetState)this.state.get(paramString);
    WidgetState localWidgetState1 = localWidgetState2;
    if (localWidgetState2 == null)
    {
      localWidgetState2 = new WidgetState();
      this.mBundle.applyDelta(localWidgetState2.motionControl);
      this.state.put(paramString, localWidgetState2);
      localWidgetState1 = localWidgetState2;
      if (paramConstraintWidget != null)
      {
        localWidgetState2.update(paramConstraintWidget, paramInt);
        localWidgetState1 = localWidgetState2;
      }
    }
    return localWidgetState1;
  }
  
  public void addCustomColor(int paramInt1, String paramString1, String paramString2, int paramInt2)
  {
    getWidgetState(paramString1, null, paramInt1).getFrame(paramInt1).addCustomColor(paramString2, paramInt2);
  }
  
  public void addCustomFloat(int paramInt, String paramString1, String paramString2, float paramFloat)
  {
    getWidgetState(paramString1, null, paramInt).getFrame(paramInt).addCustomFloat(paramString2, paramFloat);
  }
  
  public void addKeyAttribute(String paramString, TypedBundle paramTypedBundle)
  {
    getWidgetState(paramString, null, 0).setKeyAttribute(paramTypedBundle);
  }
  
  public void addKeyCycle(String paramString, TypedBundle paramTypedBundle)
  {
    getWidgetState(paramString, null, 0).setKeyCycle(paramTypedBundle);
  }
  
  public void addKeyPosition(String paramString, int paramInt1, int paramInt2, float paramFloat1, float paramFloat2)
  {
    Object localObject = new TypedBundle();
    ((TypedBundle)localObject).add(510, 2);
    ((TypedBundle)localObject).add(100, paramInt1);
    ((TypedBundle)localObject).add(506, paramFloat1);
    ((TypedBundle)localObject).add(507, paramFloat2);
    getWidgetState(paramString, null, 0).setKeyPosition((TypedBundle)localObject);
    KeyPosition localKeyPosition = new KeyPosition(paramString, paramInt1, paramInt2, paramFloat1, paramFloat2);
    HashMap localHashMap = (HashMap)this.keyPositions.get(Integer.valueOf(paramInt1));
    localObject = localHashMap;
    if (localHashMap == null)
    {
      localObject = new HashMap();
      this.keyPositions.put(Integer.valueOf(paramInt1), localObject);
    }
    ((HashMap)localObject).put(paramString, localKeyPosition);
  }
  
  public void addKeyPosition(String paramString, TypedBundle paramTypedBundle)
  {
    getWidgetState(paramString, null, 0).setKeyPosition(paramTypedBundle);
  }
  
  public void clear()
  {
    this.state.clear();
  }
  
  public boolean contains(String paramString)
  {
    return this.state.containsKey(paramString);
  }
  
  public void fillKeyPositions(WidgetFrame paramWidgetFrame, float[] paramArrayOfFloat1, float[] paramArrayOfFloat2, float[] paramArrayOfFloat3)
  {
    int j = 0;
    int i = 0;
    while (i <= 100)
    {
      Object localObject = (HashMap)this.keyPositions.get(Integer.valueOf(i));
      int k = j;
      if (localObject != null)
      {
        localObject = (KeyPosition)((HashMap)localObject).get(paramWidgetFrame.widget.stringId);
        k = j;
        if (localObject != null)
        {
          paramArrayOfFloat1[j] = ((KeyPosition)localObject).x;
          paramArrayOfFloat2[j] = ((KeyPosition)localObject).y;
          paramArrayOfFloat3[j] = ((KeyPosition)localObject).frame;
          k = j + 1;
        }
      }
      i++;
      j = k;
    }
  }
  
  public KeyPosition findNextPosition(String paramString, int paramInt)
  {
    while (paramInt <= 100)
    {
      Object localObject = (HashMap)this.keyPositions.get(Integer.valueOf(paramInt));
      if (localObject != null)
      {
        localObject = (KeyPosition)((HashMap)localObject).get(paramString);
        if (localObject != null) {
          return (KeyPosition)localObject;
        }
      }
      paramInt++;
    }
    return null;
  }
  
  public KeyPosition findPreviousPosition(String paramString, int paramInt)
  {
    while (paramInt >= 0)
    {
      Object localObject = (HashMap)this.keyPositions.get(Integer.valueOf(paramInt));
      if (localObject != null)
      {
        localObject = (KeyPosition)((HashMap)localObject).get(paramString);
        if (localObject != null) {
          return (KeyPosition)localObject;
        }
      }
      paramInt--;
    }
    return null;
  }
  
  public int getAutoTransition()
  {
    return this.mAutoTransition;
  }
  
  public WidgetFrame getEnd(ConstraintWidget paramConstraintWidget)
  {
    return getWidgetState(paramConstraintWidget.stringId, null, 1).end;
  }
  
  public WidgetFrame getEnd(String paramString)
  {
    paramString = (WidgetState)this.state.get(paramString);
    if (paramString == null) {
      return null;
    }
    return paramString.end;
  }
  
  public int getId(String paramString)
  {
    return 0;
  }
  
  public WidgetFrame getInterpolated(ConstraintWidget paramConstraintWidget)
  {
    return getWidgetState(paramConstraintWidget.stringId, null, 2).interpolated;
  }
  
  public WidgetFrame getInterpolated(String paramString)
  {
    paramString = (WidgetState)this.state.get(paramString);
    if (paramString == null) {
      return null;
    }
    return paramString.interpolated;
  }
  
  public Interpolator getInterpolator()
  {
    return getInterpolator(this.mDefaultInterpolator, this.mDefaultInterpolatorString);
  }
  
  public int getKeyFrames(String paramString, float[] paramArrayOfFloat, int[] paramArrayOfInt1, int[] paramArrayOfInt2)
  {
    return ((WidgetState)this.state.get(paramString)).motionControl.buildKeyFrames(paramArrayOfFloat, paramArrayOfInt1, paramArrayOfInt2);
  }
  
  public Motion getMotion(String paramString)
  {
    return getWidgetState(paramString, null, 0).motionControl;
  }
  
  public int getNumberKeyPositions(WidgetFrame paramWidgetFrame)
  {
    int j = 0;
    int i = 0;
    while (i <= 100)
    {
      HashMap localHashMap = (HashMap)this.keyPositions.get(Integer.valueOf(i));
      int k = j;
      if (localHashMap != null)
      {
        k = j;
        if ((KeyPosition)localHashMap.get(paramWidgetFrame.widget.stringId) != null) {
          k = j + 1;
        }
      }
      i++;
      j = k;
    }
    return j;
  }
  
  public float[] getPath(String paramString)
  {
    WidgetState localWidgetState = (WidgetState)this.state.get(paramString);
    int i = 'Ϩ' / 16;
    paramString = new float[i * 2];
    localWidgetState.motionControl.buildPath(paramString, i);
    return paramString;
  }
  
  public WidgetFrame getStart(ConstraintWidget paramConstraintWidget)
  {
    return getWidgetState(paramConstraintWidget.stringId, null, 0).start;
  }
  
  public WidgetFrame getStart(String paramString)
  {
    paramString = (WidgetState)this.state.get(paramString);
    if (paramString == null) {
      return null;
    }
    return paramString.start;
  }
  
  public boolean hasPositionKeyframes()
  {
    boolean bool;
    if (this.keyPositions.size() > 0) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public void interpolate(int paramInt1, int paramInt2, float paramFloat)
  {
    Object localObject = this.mEasing;
    float f = paramFloat;
    if (localObject != null) {
      f = (float)((Easing)localObject).get(paramFloat);
    }
    localObject = this.state.keySet().iterator();
    while (((Iterator)localObject).hasNext())
    {
      String str = (String)((Iterator)localObject).next();
      ((WidgetState)this.state.get(str)).interpolate(paramInt1, paramInt2, f, this);
    }
  }
  
  public boolean isEmpty()
  {
    return this.state.isEmpty();
  }
  
  public void setTransitionProperties(TypedBundle paramTypedBundle)
  {
    paramTypedBundle.applyDelta(this.mBundle);
    paramTypedBundle.applyDelta(this);
  }
  
  public boolean setValue(int paramInt, float paramFloat)
  {
    if (paramInt == 706) {
      this.mStagger = paramFloat;
    }
    return false;
  }
  
  public boolean setValue(int paramInt1, int paramInt2)
  {
    return false;
  }
  
  public boolean setValue(int paramInt, String paramString)
  {
    if (paramInt == 705)
    {
      this.mDefaultInterpolatorString = paramString;
      this.mEasing = Easing.getInterpolator(paramString);
    }
    return false;
  }
  
  public boolean setValue(int paramInt, boolean paramBoolean)
  {
    return false;
  }
  
  public void updateFrom(ConstraintWidgetContainer paramConstraintWidgetContainer, int paramInt)
  {
    ArrayList localArrayList = paramConstraintWidgetContainer.getChildren();
    int j = localArrayList.size();
    for (int i = 0; i < j; i++)
    {
      paramConstraintWidgetContainer = (ConstraintWidget)localArrayList.get(i);
      getWidgetState(paramConstraintWidgetContainer.stringId, null, paramInt).update(paramConstraintWidgetContainer, paramInt);
    }
  }
  
  static class KeyPosition
  {
    int frame;
    String target;
    int type;
    float x;
    float y;
    
    public KeyPosition(String paramString, int paramInt1, int paramInt2, float paramFloat1, float paramFloat2)
    {
      this.target = paramString;
      this.frame = paramInt1;
      this.type = paramInt2;
      this.x = paramFloat1;
      this.y = paramFloat2;
    }
  }
  
  static class WidgetState
  {
    WidgetFrame end = new WidgetFrame();
    WidgetFrame interpolated = new WidgetFrame();
    Motion motionControl;
    MotionWidget motionWidgetEnd = new MotionWidget(this.end);
    MotionWidget motionWidgetInterpolated = new MotionWidget(this.interpolated);
    MotionWidget motionWidgetStart = new MotionWidget(this.start);
    KeyCache myKeyCache = new KeyCache();
    int myParentHeight = -1;
    int myParentWidth = -1;
    WidgetFrame start = new WidgetFrame();
    
    public WidgetState()
    {
      Motion localMotion = new Motion(this.motionWidgetStart);
      this.motionControl = localMotion;
      localMotion.setStart(this.motionWidgetStart);
      this.motionControl.setEnd(this.motionWidgetEnd);
    }
    
    public WidgetFrame getFrame(int paramInt)
    {
      if (paramInt == 0) {
        return this.start;
      }
      if (paramInt == 1) {
        return this.end;
      }
      return this.interpolated;
    }
    
    public void interpolate(int paramInt1, int paramInt2, float paramFloat, Transition paramTransition)
    {
      this.myParentHeight = paramInt2;
      this.myParentWidth = paramInt1;
      this.motionControl.setup(paramInt1, paramInt2, 1.0F, System.nanoTime());
      WidgetFrame.interpolate(paramInt1, paramInt2, this.interpolated, this.start, this.end, paramTransition, paramFloat);
      this.interpolated.interpolatedPos = paramFloat;
      this.motionControl.interpolate(this.motionWidgetInterpolated, paramFloat, System.nanoTime(), this.myKeyCache);
    }
    
    public void setKeyAttribute(TypedBundle paramTypedBundle)
    {
      MotionKeyAttributes localMotionKeyAttributes = new MotionKeyAttributes();
      paramTypedBundle.applyDelta(localMotionKeyAttributes);
      this.motionControl.addKey(localMotionKeyAttributes);
    }
    
    public void setKeyCycle(TypedBundle paramTypedBundle)
    {
      MotionKeyCycle localMotionKeyCycle = new MotionKeyCycle();
      paramTypedBundle.applyDelta(localMotionKeyCycle);
      this.motionControl.addKey(localMotionKeyCycle);
    }
    
    public void setKeyPosition(TypedBundle paramTypedBundle)
    {
      MotionKeyPosition localMotionKeyPosition = new MotionKeyPosition();
      paramTypedBundle.applyDelta(localMotionKeyPosition);
      this.motionControl.addKey(localMotionKeyPosition);
    }
    
    public void update(ConstraintWidget paramConstraintWidget, int paramInt)
    {
      if (paramInt == 0)
      {
        this.start.update(paramConstraintWidget);
        this.motionControl.setStart(this.motionWidgetStart);
      }
      else if (paramInt == 1)
      {
        this.end.update(paramConstraintWidget);
        this.motionControl.setEnd(this.motionWidgetEnd);
      }
      this.myParentWidth = -1;
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/constraintlayout/core/state/Transition.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */