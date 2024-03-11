package androidx.constraintlayout.motion.widget;

import android.util.Pair;
import android.view.View;
import android.view.ViewGroup;
import androidx.constraintlayout.widget.ConstraintSet;
import java.io.PrintStream;
import java.util.HashMap;

public class DesignTool
  implements ProxyInterface
{
  private static final boolean DEBUG = false;
  private static final String TAG = "DesignTool";
  static final HashMap<Pair<Integer, Integer>, String> allAttributes;
  static final HashMap<String, String> allMargins;
  private String mLastEndState = null;
  private int mLastEndStateId = -1;
  private String mLastStartState = null;
  private int mLastStartStateId = -1;
  private final MotionLayout mMotionLayout;
  private MotionScene mSceneCache;
  
  static
  {
    HashMap localHashMap2 = new HashMap();
    allAttributes = localHashMap2;
    HashMap localHashMap1 = new HashMap();
    allMargins = localHashMap1;
    Integer localInteger2 = Integer.valueOf(4);
    localHashMap2.put(Pair.create(localInteger2, localInteger2), "layout_constraintBottom_toBottomOf");
    Integer localInteger1 = Integer.valueOf(3);
    localHashMap2.put(Pair.create(localInteger2, localInteger1), "layout_constraintBottom_toTopOf");
    localHashMap2.put(Pair.create(localInteger1, localInteger2), "layout_constraintTop_toBottomOf");
    localHashMap2.put(Pair.create(localInteger1, localInteger1), "layout_constraintTop_toTopOf");
    localInteger1 = Integer.valueOf(6);
    localHashMap2.put(Pair.create(localInteger1, localInteger1), "layout_constraintStart_toStartOf");
    localInteger2 = Integer.valueOf(7);
    localHashMap2.put(Pair.create(localInteger1, localInteger2), "layout_constraintStart_toEndOf");
    localHashMap2.put(Pair.create(localInteger2, localInteger1), "layout_constraintEnd_toStartOf");
    localHashMap2.put(Pair.create(localInteger2, localInteger2), "layout_constraintEnd_toEndOf");
    localInteger2 = Integer.valueOf(1);
    localHashMap2.put(Pair.create(localInteger2, localInteger2), "layout_constraintLeft_toLeftOf");
    localInteger1 = Integer.valueOf(2);
    localHashMap2.put(Pair.create(localInteger2, localInteger1), "layout_constraintLeft_toRightOf");
    localHashMap2.put(Pair.create(localInteger1, localInteger1), "layout_constraintRight_toRightOf");
    localHashMap2.put(Pair.create(localInteger1, localInteger2), "layout_constraintRight_toLeftOf");
    localInteger1 = Integer.valueOf(5);
    localHashMap2.put(Pair.create(localInteger1, localInteger1), "layout_constraintBaseline_toBaselineOf");
    localHashMap1.put("layout_constraintBottom_toBottomOf", "layout_marginBottom");
    localHashMap1.put("layout_constraintBottom_toTopOf", "layout_marginBottom");
    localHashMap1.put("layout_constraintTop_toBottomOf", "layout_marginTop");
    localHashMap1.put("layout_constraintTop_toTopOf", "layout_marginTop");
    localHashMap1.put("layout_constraintStart_toStartOf", "layout_marginStart");
    localHashMap1.put("layout_constraintStart_toEndOf", "layout_marginStart");
    localHashMap1.put("layout_constraintEnd_toStartOf", "layout_marginEnd");
    localHashMap1.put("layout_constraintEnd_toEndOf", "layout_marginEnd");
    localHashMap1.put("layout_constraintLeft_toLeftOf", "layout_marginLeft");
    localHashMap1.put("layout_constraintLeft_toRightOf", "layout_marginLeft");
    localHashMap1.put("layout_constraintRight_toRightOf", "layout_marginRight");
    localHashMap1.put("layout_constraintRight_toLeftOf", "layout_marginRight");
  }
  
  public DesignTool(MotionLayout paramMotionLayout)
  {
    this.mMotionLayout = paramMotionLayout;
  }
  
  private static void Connect(int paramInt1, ConstraintSet paramConstraintSet, View paramView, HashMap<String, String> paramHashMap, int paramInt2, int paramInt3)
  {
    String str2 = (String)allAttributes.get(Pair.create(Integer.valueOf(paramInt2), Integer.valueOf(paramInt3)));
    String str1 = (String)paramHashMap.get(str2);
    if (str1 != null)
    {
      int i = 0;
      str2 = (String)allMargins.get(str2);
      if (str2 != null) {
        paramInt1 = GetPxFromDp(paramInt1, (String)paramHashMap.get(str2));
      } else {
        paramInt1 = i;
      }
      i = Integer.parseInt(str1);
      paramConstraintSet.connect(paramView.getId(), paramInt2, i, paramInt3, paramInt1);
    }
  }
  
  private static int GetPxFromDp(int paramInt, String paramString)
  {
    if (paramString == null) {
      return 0;
    }
    int i = paramString.indexOf('d');
    if (i == -1) {
      return 0;
    }
    return (int)(Integer.valueOf(paramString.substring(0, i)).intValue() * paramInt / 160.0F);
  }
  
  private static void SetAbsolutePositions(int paramInt, ConstraintSet paramConstraintSet, View paramView, HashMap<String, String> paramHashMap)
  {
    String str = (String)paramHashMap.get("layout_editor_absoluteX");
    if (str != null) {
      paramConstraintSet.setEditorAbsoluteX(paramView.getId(), GetPxFromDp(paramInt, str));
    }
    paramHashMap = (String)paramHashMap.get("layout_editor_absoluteY");
    if (paramHashMap != null) {
      paramConstraintSet.setEditorAbsoluteY(paramView.getId(), GetPxFromDp(paramInt, paramHashMap));
    }
  }
  
  private static void SetBias(ConstraintSet paramConstraintSet, View paramView, HashMap<String, String> paramHashMap, int paramInt)
  {
    String str = "layout_constraintHorizontal_bias";
    if (paramInt == 1) {
      str = "layout_constraintVertical_bias";
    }
    paramHashMap = (String)paramHashMap.get(str);
    if (paramHashMap != null) {
      if (paramInt == 0) {
        paramConstraintSet.setHorizontalBias(paramView.getId(), Float.parseFloat(paramHashMap));
      } else if (paramInt == 1) {
        paramConstraintSet.setVerticalBias(paramView.getId(), Float.parseFloat(paramHashMap));
      }
    }
  }
  
  private static void SetDimensions(int paramInt1, ConstraintSet paramConstraintSet, View paramView, HashMap<String, String> paramHashMap, int paramInt2)
  {
    String str = "layout_width";
    if (paramInt2 == 1) {
      str = "layout_height";
    }
    paramHashMap = (String)paramHashMap.get(str);
    if (paramHashMap != null)
    {
      int i = -2;
      if (!paramHashMap.equalsIgnoreCase("wrap_content")) {
        i = GetPxFromDp(paramInt1, paramHashMap);
      }
      if (paramInt2 == 0) {
        paramConstraintSet.constrainWidth(paramView.getId(), i);
      } else {
        paramConstraintSet.constrainHeight(paramView.getId(), i);
      }
    }
  }
  
  public int designAccess(int paramInt1, String paramString, Object paramObject, float[] paramArrayOfFloat1, int paramInt2, float[] paramArrayOfFloat2, int paramInt3)
  {
    paramArrayOfFloat1 = (View)paramObject;
    paramObject = null;
    if (paramInt1 != 0)
    {
      if (this.mMotionLayout.mScene == null) {
        return -1;
      }
      if (paramArrayOfFloat1 != null)
      {
        paramArrayOfFloat1 = (MotionController)this.mMotionLayout.mFrameArrayList.get(paramArrayOfFloat1);
        paramObject = paramArrayOfFloat1;
        if (paramArrayOfFloat1 == null) {
          return -1;
        }
      }
      else
      {
        return -1;
      }
    }
    switch (paramInt1)
    {
    default: 
      return -1;
    case 3: 
      paramInt1 = this.mMotionLayout.mScene.getDuration() / 16;
      return ((MotionController)paramObject).getAttributeValues(paramString, paramArrayOfFloat2, paramInt3);
    case 2: 
      paramInt1 = this.mMotionLayout.mScene.getDuration() / 16;
      ((MotionController)paramObject).buildKeyFrames(paramArrayOfFloat2, null);
      return paramInt1;
    case 1: 
      paramInt1 = this.mMotionLayout.mScene.getDuration() / 16;
      ((MotionController)paramObject).buildPath(paramArrayOfFloat2, paramInt1);
      return paramInt1;
    }
    return 1;
  }
  
  public void disableAutoTransition(boolean paramBoolean)
  {
    this.mMotionLayout.disableAutoTransition(paramBoolean);
  }
  
  public void dumpConstraintSet(String paramString)
  {
    if (this.mMotionLayout.mScene == null) {
      this.mMotionLayout.mScene = this.mSceneCache;
    }
    int i = this.mMotionLayout.lookUpConstraintId(paramString);
    System.out.println(" dumping  " + paramString + " (" + i + ")");
    try
    {
      this.mMotionLayout.mScene.getConstraintSet(i).dump(this.mMotionLayout.mScene, new int[0]);
    }
    catch (Exception paramString)
    {
      paramString.printStackTrace();
    }
  }
  
  public int getAnimationKeyFrames(Object paramObject, float[] paramArrayOfFloat)
  {
    if (this.mMotionLayout.mScene == null) {
      return -1;
    }
    int i = this.mMotionLayout.mScene.getDuration() / 16;
    paramObject = (MotionController)this.mMotionLayout.mFrameArrayList.get(paramObject);
    if (paramObject == null) {
      return 0;
    }
    ((MotionController)paramObject).buildKeyFrames(paramArrayOfFloat, null);
    return i;
  }
  
  public int getAnimationPath(Object paramObject, float[] paramArrayOfFloat, int paramInt)
  {
    if (this.mMotionLayout.mScene == null) {
      return -1;
    }
    paramObject = (MotionController)this.mMotionLayout.mFrameArrayList.get(paramObject);
    if (paramObject == null) {
      return 0;
    }
    ((MotionController)paramObject).buildPath(paramArrayOfFloat, paramInt);
    return paramInt;
  }
  
  public void getAnimationRectangles(Object paramObject, float[] paramArrayOfFloat)
  {
    if (this.mMotionLayout.mScene == null) {
      return;
    }
    int i = this.mMotionLayout.mScene.getDuration() / 16;
    paramObject = (MotionController)this.mMotionLayout.mFrameArrayList.get(paramObject);
    if (paramObject == null) {
      return;
    }
    ((MotionController)paramObject).buildRectangles(paramArrayOfFloat, i);
  }
  
  public String getEndState()
  {
    int i = this.mMotionLayout.getEndState();
    if (this.mLastEndStateId == i) {
      return this.mLastEndState;
    }
    String str = this.mMotionLayout.getConstraintSetNames(i);
    if (str != null)
    {
      this.mLastEndState = str;
      this.mLastEndStateId = i;
    }
    return str;
  }
  
  public int getKeyFrameInfo(Object paramObject, int paramInt, int[] paramArrayOfInt)
  {
    paramObject = (MotionController)this.mMotionLayout.mFrameArrayList.get((View)paramObject);
    if (paramObject == null) {
      return 0;
    }
    return ((MotionController)paramObject).getKeyFrameInfo(paramInt, paramArrayOfInt);
  }
  
  public float getKeyFramePosition(Object paramObject, int paramInt, float paramFloat1, float paramFloat2)
  {
    if (!(paramObject instanceof View)) {
      return 0.0F;
    }
    paramObject = (MotionController)this.mMotionLayout.mFrameArrayList.get((View)paramObject);
    if (paramObject == null) {
      return 0.0F;
    }
    return ((MotionController)paramObject).getKeyFrameParameter(paramInt, paramFloat1, paramFloat2);
  }
  
  public int getKeyFramePositions(Object paramObject, int[] paramArrayOfInt, float[] paramArrayOfFloat)
  {
    paramObject = (MotionController)this.mMotionLayout.mFrameArrayList.get((View)paramObject);
    if (paramObject == null) {
      return 0;
    }
    return ((MotionController)paramObject).getKeyFramePositions(paramArrayOfInt, paramArrayOfFloat);
  }
  
  public Object getKeyframe(int paramInt1, int paramInt2, int paramInt3)
  {
    if (this.mMotionLayout.mScene == null) {
      return null;
    }
    return this.mMotionLayout.mScene.getKeyFrame(this.mMotionLayout.getContext(), paramInt1, paramInt2, paramInt3);
  }
  
  public Object getKeyframe(Object paramObject, int paramInt1, int paramInt2)
  {
    if (this.mMotionLayout.mScene == null) {
      return null;
    }
    int i = ((View)paramObject).getId();
    return this.mMotionLayout.mScene.getKeyFrame(this.mMotionLayout.getContext(), paramInt1, i, paramInt2);
  }
  
  public Object getKeyframeAtLocation(Object paramObject, float paramFloat1, float paramFloat2)
  {
    Object localObject = (View)paramObject;
    if (this.mMotionLayout.mScene == null) {
      return Integer.valueOf(-1);
    }
    if (localObject != null)
    {
      paramObject = (MotionController)this.mMotionLayout.mFrameArrayList.get(localObject);
      if (paramObject == null) {
        return null;
      }
      localObject = (ViewGroup)((View)localObject).getParent();
      return ((MotionController)paramObject).getPositionKeyframe(((ViewGroup)localObject).getWidth(), ((ViewGroup)localObject).getHeight(), paramFloat1, paramFloat2);
    }
    return null;
  }
  
  public Boolean getPositionKeyframe(Object paramObject1, Object paramObject2, float paramFloat1, float paramFloat2, String[] paramArrayOfString, float[] paramArrayOfFloat)
  {
    if ((paramObject1 instanceof KeyPositionBase))
    {
      paramObject1 = (KeyPositionBase)paramObject1;
      ((MotionController)this.mMotionLayout.mFrameArrayList.get((View)paramObject2)).positionKeyframe((View)paramObject2, (KeyPositionBase)paramObject1, paramFloat1, paramFloat2, paramArrayOfString, paramArrayOfFloat);
      this.mMotionLayout.rebuildScene();
      this.mMotionLayout.mInTransition = true;
      return Boolean.valueOf(true);
    }
    return Boolean.valueOf(false);
  }
  
  public float getProgress()
  {
    return this.mMotionLayout.getProgress();
  }
  
  public String getStartState()
  {
    int i = this.mMotionLayout.getStartState();
    if (this.mLastStartStateId == i) {
      return this.mLastStartState;
    }
    String str = this.mMotionLayout.getConstraintSetNames(i);
    if (str != null)
    {
      this.mLastStartState = str;
      this.mLastStartStateId = i;
    }
    return this.mMotionLayout.getConstraintSetNames(i);
  }
  
  public String getState()
  {
    if ((this.mLastStartState != null) && (this.mLastEndState != null))
    {
      float f = getProgress();
      if (f <= 0.01F) {
        return this.mLastStartState;
      }
      if (f >= 1.0F - 0.01F) {
        return this.mLastEndState;
      }
    }
    return this.mLastStartState;
  }
  
  public long getTransitionTimeMs()
  {
    return this.mMotionLayout.getTransitionTimeMs();
  }
  
  public boolean isInTransition()
  {
    boolean bool;
    if ((this.mLastStartState != null) && (this.mLastEndState != null)) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public void setAttributes(int paramInt, String paramString, Object paramObject1, Object paramObject2)
  {
    paramObject1 = (View)paramObject1;
    paramObject2 = (HashMap)paramObject2;
    int i = this.mMotionLayout.lookUpConstraintId(paramString);
    paramString = this.mMotionLayout.mScene.getConstraintSet(i);
    if (paramString == null) {
      return;
    }
    paramString.clear(((View)paramObject1).getId());
    SetDimensions(paramInt, paramString, (View)paramObject1, (HashMap)paramObject2, 0);
    SetDimensions(paramInt, paramString, (View)paramObject1, (HashMap)paramObject2, 1);
    Connect(paramInt, paramString, (View)paramObject1, (HashMap)paramObject2, 6, 6);
    Connect(paramInt, paramString, (View)paramObject1, (HashMap)paramObject2, 6, 7);
    Connect(paramInt, paramString, (View)paramObject1, (HashMap)paramObject2, 7, 7);
    Connect(paramInt, paramString, (View)paramObject1, (HashMap)paramObject2, 7, 6);
    Connect(paramInt, paramString, (View)paramObject1, (HashMap)paramObject2, 1, 1);
    Connect(paramInt, paramString, (View)paramObject1, (HashMap)paramObject2, 1, 2);
    Connect(paramInt, paramString, (View)paramObject1, (HashMap)paramObject2, 2, 2);
    Connect(paramInt, paramString, (View)paramObject1, (HashMap)paramObject2, 2, 1);
    Connect(paramInt, paramString, (View)paramObject1, (HashMap)paramObject2, 3, 3);
    Connect(paramInt, paramString, (View)paramObject1, (HashMap)paramObject2, 3, 4);
    Connect(paramInt, paramString, (View)paramObject1, (HashMap)paramObject2, 4, 3);
    Connect(paramInt, paramString, (View)paramObject1, (HashMap)paramObject2, 4, 4);
    Connect(paramInt, paramString, (View)paramObject1, (HashMap)paramObject2, 5, 5);
    SetBias(paramString, (View)paramObject1, (HashMap)paramObject2, 0);
    SetBias(paramString, (View)paramObject1, (HashMap)paramObject2, 1);
    SetAbsolutePositions(paramInt, paramString, (View)paramObject1, (HashMap)paramObject2);
    this.mMotionLayout.updateState(i, paramString);
    this.mMotionLayout.requestLayout();
  }
  
  public void setKeyFrame(Object paramObject1, int paramInt, String paramString, Object paramObject2)
  {
    if (this.mMotionLayout.mScene != null)
    {
      this.mMotionLayout.mScene.setKeyframe((View)paramObject1, paramInt, paramString, paramObject2);
      this.mMotionLayout.mTransitionGoalPosition = (paramInt / 100.0F);
      this.mMotionLayout.mTransitionLastPosition = 0.0F;
      this.mMotionLayout.rebuildScene();
      this.mMotionLayout.evaluate(true);
    }
  }
  
  public boolean setKeyFramePosition(Object paramObject, int paramInt1, int paramInt2, float paramFloat1, float paramFloat2)
  {
    if (!(paramObject instanceof View)) {
      return false;
    }
    if (this.mMotionLayout.mScene != null)
    {
      MotionController localMotionController = (MotionController)this.mMotionLayout.mFrameArrayList.get(paramObject);
      paramInt1 = (int)(this.mMotionLayout.mTransitionPosition * 100.0F);
      if ((localMotionController != null) && (this.mMotionLayout.mScene.hasKeyFramePosition((View)paramObject, paramInt1)))
      {
        float f = localMotionController.getKeyFrameParameter(2, paramFloat1, paramFloat2);
        paramFloat1 = localMotionController.getKeyFrameParameter(5, paramFloat1, paramFloat2);
        this.mMotionLayout.mScene.setKeyframe((View)paramObject, paramInt1, "motion:percentX", Float.valueOf(f));
        this.mMotionLayout.mScene.setKeyframe((View)paramObject, paramInt1, "motion:percentY", Float.valueOf(paramFloat1));
        this.mMotionLayout.rebuildScene();
        this.mMotionLayout.evaluate(true);
        this.mMotionLayout.invalidate();
        return true;
      }
    }
    return false;
  }
  
  public void setKeyframe(Object paramObject1, String paramString, Object paramObject2)
  {
    if ((paramObject1 instanceof Key))
    {
      ((Key)paramObject1).setValue(paramString, paramObject2);
      this.mMotionLayout.rebuildScene();
      this.mMotionLayout.mInTransition = true;
    }
  }
  
  public void setState(String paramString)
  {
    String str = paramString;
    if (paramString == null) {
      str = "motion_base";
    }
    if (this.mLastStartState == str) {
      return;
    }
    this.mLastStartState = str;
    this.mLastEndState = null;
    if (this.mMotionLayout.mScene == null) {
      this.mMotionLayout.mScene = this.mSceneCache;
    }
    int i = this.mMotionLayout.lookUpConstraintId(str);
    this.mLastStartStateId = i;
    if (i != 0) {
      if (i == this.mMotionLayout.getStartState())
      {
        this.mMotionLayout.setProgress(0.0F);
      }
      else if (i == this.mMotionLayout.getEndState())
      {
        this.mMotionLayout.setProgress(1.0F);
      }
      else
      {
        this.mMotionLayout.transitionToState(i);
        this.mMotionLayout.setProgress(1.0F);
      }
    }
    this.mMotionLayout.requestLayout();
  }
  
  public void setToolPosition(float paramFloat)
  {
    if (this.mMotionLayout.mScene == null) {
      this.mMotionLayout.mScene = this.mSceneCache;
    }
    this.mMotionLayout.setProgress(paramFloat);
    this.mMotionLayout.evaluate(true);
    this.mMotionLayout.requestLayout();
    this.mMotionLayout.invalidate();
  }
  
  public void setTransition(String paramString1, String paramString2)
  {
    if (this.mMotionLayout.mScene == null) {
      this.mMotionLayout.mScene = this.mSceneCache;
    }
    int j = this.mMotionLayout.lookUpConstraintId(paramString1);
    int i = this.mMotionLayout.lookUpConstraintId(paramString2);
    this.mMotionLayout.setTransition(j, i);
    this.mLastStartStateId = j;
    this.mLastEndStateId = i;
    this.mLastStartState = paramString1;
    this.mLastEndState = paramString2;
  }
  
  public void setViewDebug(Object paramObject, int paramInt)
  {
    if (!(paramObject instanceof View)) {
      return;
    }
    paramObject = (MotionController)this.mMotionLayout.mFrameArrayList.get(paramObject);
    if (paramObject != null)
    {
      ((MotionController)paramObject).setDrawPath(paramInt);
      this.mMotionLayout.invalidate();
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/constraintlayout/motion/widget/DesignTool.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */