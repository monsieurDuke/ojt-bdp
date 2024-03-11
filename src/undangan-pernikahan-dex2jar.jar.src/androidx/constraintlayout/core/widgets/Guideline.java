package androidx.constraintlayout.core.widgets;

import androidx.constraintlayout.core.LinearSystem;
import androidx.constraintlayout.core.SolverVariable;
import java.util.ArrayList;
import java.util.HashMap;

public class Guideline
  extends ConstraintWidget
{
  public static final int HORIZONTAL = 0;
  public static final int RELATIVE_BEGIN = 1;
  public static final int RELATIVE_END = 2;
  public static final int RELATIVE_PERCENT = 0;
  public static final int RELATIVE_UNKNOWN = -1;
  public static final int VERTICAL = 1;
  protected boolean guidelineUseRtl = true;
  private ConstraintAnchor mAnchor = this.mTop;
  private int mMinimumPosition = 0;
  private int mOrientation = 0;
  protected int mRelativeBegin = -1;
  protected int mRelativeEnd = -1;
  protected float mRelativePercent = -1.0F;
  private boolean resolved;
  
  public Guideline()
  {
    this.mAnchors.clear();
    this.mAnchors.add(this.mAnchor);
    int j = this.mListAnchors.length;
    for (int i = 0; i < j; i++) {
      this.mListAnchors[i] = this.mAnchor;
    }
  }
  
  public void addToSolver(LinearSystem paramLinearSystem, boolean paramBoolean)
  {
    Object localObject2 = (ConstraintWidgetContainer)getParent();
    if (localObject2 == null) {
      return;
    }
    ConstraintAnchor localConstraintAnchor = ((ConstraintWidgetContainer)localObject2).getAnchor(ConstraintAnchor.Type.LEFT);
    Object localObject1 = ((ConstraintWidgetContainer)localObject2).getAnchor(ConstraintAnchor.Type.RIGHT);
    ConstraintWidget localConstraintWidget = this.mParent;
    int j = 1;
    int i;
    if ((localConstraintWidget != null) && (this.mParent.mListDimensionBehaviors[0] == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT)) {
      i = 1;
    } else {
      i = 0;
    }
    if (this.mOrientation == 0)
    {
      localConstraintAnchor = ((ConstraintWidgetContainer)localObject2).getAnchor(ConstraintAnchor.Type.TOP);
      localObject1 = ((ConstraintWidgetContainer)localObject2).getAnchor(ConstraintAnchor.Type.BOTTOM);
      if ((this.mParent != null) && (this.mParent.mListDimensionBehaviors[1] == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT)) {
        i = j;
      } else {
        i = 0;
      }
    }
    if ((this.resolved) && (this.mAnchor.hasFinalValue()))
    {
      localObject2 = paramLinearSystem.createObjectVariable(this.mAnchor);
      paramLinearSystem.addEquality((SolverVariable)localObject2, this.mAnchor.getFinalValue());
      if (this.mRelativeBegin != -1)
      {
        if (i != 0) {
          paramLinearSystem.addGreaterThan(paramLinearSystem.createObjectVariable(localObject1), (SolverVariable)localObject2, 0, 5);
        }
      }
      else if ((this.mRelativeEnd != -1) && (i != 0))
      {
        localObject1 = paramLinearSystem.createObjectVariable(localObject1);
        paramLinearSystem.addGreaterThan((SolverVariable)localObject2, paramLinearSystem.createObjectVariable(localConstraintAnchor), 0, 5);
        paramLinearSystem.addGreaterThan((SolverVariable)localObject1, (SolverVariable)localObject2, 0, 5);
      }
      this.resolved = false;
      return;
    }
    if (this.mRelativeBegin != -1)
    {
      localObject2 = paramLinearSystem.createObjectVariable(this.mAnchor);
      paramLinearSystem.addEquality((SolverVariable)localObject2, paramLinearSystem.createObjectVariable(localConstraintAnchor), this.mRelativeBegin, 8);
      if (i != 0) {
        paramLinearSystem.addGreaterThan(paramLinearSystem.createObjectVariable(localObject1), (SolverVariable)localObject2, 0, 5);
      }
    }
    else if (this.mRelativeEnd != -1)
    {
      localObject2 = paramLinearSystem.createObjectVariable(this.mAnchor);
      localObject1 = paramLinearSystem.createObjectVariable(localObject1);
      paramLinearSystem.addEquality((SolverVariable)localObject2, (SolverVariable)localObject1, -this.mRelativeEnd, 8);
      if (i != 0)
      {
        paramLinearSystem.addGreaterThan((SolverVariable)localObject2, paramLinearSystem.createObjectVariable(localConstraintAnchor), 0, 5);
        paramLinearSystem.addGreaterThan((SolverVariable)localObject1, (SolverVariable)localObject2, 0, 5);
      }
    }
    else if (this.mRelativePercent != -1.0F)
    {
      paramLinearSystem.addConstraint(LinearSystem.createRowDimensionPercent(paramLinearSystem, paramLinearSystem.createObjectVariable(this.mAnchor), paramLinearSystem.createObjectVariable(localObject1), this.mRelativePercent));
    }
  }
  
  public boolean allowedInBarrier()
  {
    return true;
  }
  
  public void copy(ConstraintWidget paramConstraintWidget, HashMap<ConstraintWidget, ConstraintWidget> paramHashMap)
  {
    super.copy(paramConstraintWidget, paramHashMap);
    paramConstraintWidget = (Guideline)paramConstraintWidget;
    this.mRelativePercent = paramConstraintWidget.mRelativePercent;
    this.mRelativeBegin = paramConstraintWidget.mRelativeBegin;
    this.mRelativeEnd = paramConstraintWidget.mRelativeEnd;
    this.guidelineUseRtl = paramConstraintWidget.guidelineUseRtl;
    setOrientation(paramConstraintWidget.mOrientation);
  }
  
  public void cyclePosition()
  {
    if (this.mRelativeBegin != -1) {
      inferRelativePercentPosition();
    } else if (this.mRelativePercent != -1.0F) {
      inferRelativeEndPosition();
    } else if (this.mRelativeEnd != -1) {
      inferRelativeBeginPosition();
    }
  }
  
  public ConstraintAnchor getAnchor()
  {
    return this.mAnchor;
  }
  
  public ConstraintAnchor getAnchor(ConstraintAnchor.Type paramType)
  {
    switch (1.$SwitchMap$androidx$constraintlayout$core$widgets$ConstraintAnchor$Type[paramType.ordinal()])
    {
    default: 
      break;
    case 5: 
    case 6: 
    case 7: 
    case 8: 
    case 9: 
      return null;
    case 3: 
    case 4: 
      if (this.mOrientation == 0) {
        return this.mAnchor;
      }
      break;
    case 1: 
    case 2: 
      if (this.mOrientation == 1) {
        return this.mAnchor;
      }
      break;
    }
    return null;
  }
  
  public int getOrientation()
  {
    return this.mOrientation;
  }
  
  public int getRelativeBegin()
  {
    return this.mRelativeBegin;
  }
  
  public int getRelativeBehaviour()
  {
    if (this.mRelativePercent != -1.0F) {
      return 0;
    }
    if (this.mRelativeBegin != -1) {
      return 1;
    }
    if (this.mRelativeEnd != -1) {
      return 2;
    }
    return -1;
  }
  
  public int getRelativeEnd()
  {
    return this.mRelativeEnd;
  }
  
  public float getRelativePercent()
  {
    return this.mRelativePercent;
  }
  
  public String getType()
  {
    return "Guideline";
  }
  
  void inferRelativeBeginPosition()
  {
    int i = getX();
    if (this.mOrientation == 0) {
      i = getY();
    }
    setGuideBegin(i);
  }
  
  void inferRelativeEndPosition()
  {
    int i = getParent().getWidth() - getX();
    if (this.mOrientation == 0) {
      i = getParent().getHeight() - getY();
    }
    setGuideEnd(i);
  }
  
  void inferRelativePercentPosition()
  {
    float f = getX() / getParent().getWidth();
    if (this.mOrientation == 0) {
      f = getY() / getParent().getHeight();
    }
    setGuidePercent(f);
  }
  
  public boolean isPercent()
  {
    boolean bool;
    if ((this.mRelativePercent != -1.0F) && (this.mRelativeBegin == -1) && (this.mRelativeEnd == -1)) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public boolean isResolvedHorizontally()
  {
    return this.resolved;
  }
  
  public boolean isResolvedVertically()
  {
    return this.resolved;
  }
  
  public void setFinalValue(int paramInt)
  {
    this.mAnchor.setFinalValue(paramInt);
    this.resolved = true;
  }
  
  public void setGuideBegin(int paramInt)
  {
    if (paramInt > -1)
    {
      this.mRelativePercent = -1.0F;
      this.mRelativeBegin = paramInt;
      this.mRelativeEnd = -1;
    }
  }
  
  public void setGuideEnd(int paramInt)
  {
    if (paramInt > -1)
    {
      this.mRelativePercent = -1.0F;
      this.mRelativeBegin = -1;
      this.mRelativeEnd = paramInt;
    }
  }
  
  public void setGuidePercent(float paramFloat)
  {
    if (paramFloat > -1.0F)
    {
      this.mRelativePercent = paramFloat;
      this.mRelativeBegin = -1;
      this.mRelativeEnd = -1;
    }
  }
  
  public void setGuidePercent(int paramInt)
  {
    setGuidePercent(paramInt / 100.0F);
  }
  
  public void setMinimumPosition(int paramInt)
  {
    this.mMinimumPosition = paramInt;
  }
  
  public void setOrientation(int paramInt)
  {
    if (this.mOrientation == paramInt) {
      return;
    }
    this.mOrientation = paramInt;
    this.mAnchors.clear();
    if (this.mOrientation == 1) {
      this.mAnchor = this.mLeft;
    } else {
      this.mAnchor = this.mTop;
    }
    this.mAnchors.add(this.mAnchor);
    int i = this.mListAnchors.length;
    for (paramInt = 0; paramInt < i; paramInt++) {
      this.mListAnchors[paramInt] = this.mAnchor;
    }
  }
  
  public void updateFromSolver(LinearSystem paramLinearSystem, boolean paramBoolean)
  {
    if (getParent() == null) {
      return;
    }
    int i = paramLinearSystem.getObjectVariableValue(this.mAnchor);
    if (this.mOrientation == 1)
    {
      setX(i);
      setY(0);
      setHeight(getParent().getHeight());
      setWidth(0);
    }
    else
    {
      setX(0);
      setY(i);
      setWidth(getParent().getWidth());
      setHeight(0);
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/constraintlayout/core/widgets/Guideline.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */