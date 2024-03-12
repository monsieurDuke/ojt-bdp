package androidx.constraintlayout.core.widgets;

import androidx.constraintlayout.core.LinearSystem;
import androidx.constraintlayout.core.SolverVariable;
import java.util.HashMap;

public class Barrier
  extends HelperWidget
{
  public static final int BOTTOM = 3;
  public static final int LEFT = 0;
  public static final int RIGHT = 1;
  public static final int TOP = 2;
  private static final boolean USE_RELAX_GONE = false;
  private static final boolean USE_RESOLUTION = true;
  private boolean mAllowsGoneWidget = true;
  private int mBarrierType = 0;
  private int mMargin = 0;
  boolean resolved = false;
  
  public Barrier() {}
  
  public Barrier(String paramString)
  {
    setDebugName(paramString);
  }
  
  public void addToSolver(LinearSystem paramLinearSystem, boolean paramBoolean)
  {
    this.mListAnchors[0] = this.mLeft;
    this.mListAnchors[2] = this.mTop;
    this.mListAnchors[1] = this.mRight;
    this.mListAnchors[3] = this.mBottom;
    for (int i = 0; i < this.mListAnchors.length; i++) {
      this.mListAnchors[i].mSolverVariable = paramLinearSystem.createObjectVariable(this.mListAnchors[i]);
    }
    i = this.mBarrierType;
    if ((i >= 0) && (i < 4))
    {
      ConstraintAnchor localConstraintAnchor = this.mListAnchors[this.mBarrierType];
      if (!this.resolved) {
        allSolved();
      }
      if (this.resolved)
      {
        this.resolved = false;
        i = this.mBarrierType;
        if ((i != 0) && (i != 1))
        {
          if ((i == 2) || (i == 3))
          {
            paramLinearSystem.addEquality(this.mTop.mSolverVariable, this.mY);
            paramLinearSystem.addEquality(this.mBottom.mSolverVariable, this.mY);
          }
        }
        else
        {
          paramLinearSystem.addEquality(this.mLeft.mSolverVariable, this.mX);
          paramLinearSystem.addEquality(this.mRight.mSolverVariable, this.mX);
        }
        return;
      }
      boolean bool = false;
      ConstraintWidget localConstraintWidget;
      for (i = 0;; i++)
      {
        paramBoolean = bool;
        if (i >= this.mWidgetsCount) {
          break;
        }
        localConstraintWidget = this.mWidgets[i];
        if ((this.mAllowsGoneWidget) || (localConstraintWidget.allowedInBarrier()))
        {
          j = this.mBarrierType;
          if (((j == 0) || (j == 1)) && (localConstraintWidget.getHorizontalDimensionBehaviour() == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) && (localConstraintWidget.mLeft.mTarget != null) && (localConstraintWidget.mRight.mTarget != null))
          {
            paramBoolean = true;
            break;
          }
          j = this.mBarrierType;
          if (((j == 2) || (j == 3)) && (localConstraintWidget.getVerticalDimensionBehaviour() == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) && (localConstraintWidget.mTop.mTarget != null) && (localConstraintWidget.mBottom.mTarget != null))
          {
            paramBoolean = true;
            break;
          }
        }
      }
      if ((!this.mLeft.hasCenteredDependents()) && (!this.mRight.hasCenteredDependents())) {
        i = 0;
      } else {
        i = 1;
      }
      if ((!this.mTop.hasCenteredDependents()) && (!this.mBottom.hasCenteredDependents())) {
        j = 0;
      } else {
        j = 1;
      }
      int k;
      if (!paramBoolean)
      {
        k = this.mBarrierType;
        if (((k == 0) && (i != 0)) || ((k == 2) && (j != 0)) || ((k == 1) && (i != 0)) || ((k == 3) && (j != 0)))
        {
          j = 1;
          break label497;
        }
      }
      int j = 0;
      label497:
      i = 5;
      if (j == 0) {
        i = 4;
      }
      for (j = 0; j < this.mWidgetsCount; j++)
      {
        localConstraintWidget = this.mWidgets[j];
        if ((this.mAllowsGoneWidget) || (localConstraintWidget.allowedInBarrier()))
        {
          SolverVariable localSolverVariable = paramLinearSystem.createObjectVariable(localConstraintWidget.mListAnchors[this.mBarrierType]);
          localConstraintWidget.mListAnchors[this.mBarrierType].mSolverVariable = localSolverVariable;
          int m = 0;
          k = m;
          if (localConstraintWidget.mListAnchors[this.mBarrierType].mTarget != null)
          {
            k = m;
            if (localConstraintWidget.mListAnchors[this.mBarrierType].mTarget.mOwner == this) {
              k = 0 + localConstraintWidget.mListAnchors[this.mBarrierType].mMargin;
            }
          }
          m = this.mBarrierType;
          if ((m != 0) && (m != 2)) {
            paramLinearSystem.addGreaterBarrier(localConstraintAnchor.mSolverVariable, localSolverVariable, this.mMargin + k, paramBoolean);
          } else {
            paramLinearSystem.addLowerBarrier(localConstraintAnchor.mSolverVariable, localSolverVariable, this.mMargin - k, paramBoolean);
          }
          paramLinearSystem.addEquality(localConstraintAnchor.mSolverVariable, localSolverVariable, this.mMargin + k, i);
        }
      }
      i = this.mBarrierType;
      if (i == 0)
      {
        paramLinearSystem.addEquality(this.mRight.mSolverVariable, this.mLeft.mSolverVariable, 0, 8);
        paramLinearSystem.addEquality(this.mLeft.mSolverVariable, this.mParent.mRight.mSolverVariable, 0, 4);
        paramLinearSystem.addEquality(this.mLeft.mSolverVariable, this.mParent.mLeft.mSolverVariable, 0, 0);
      }
      else if (i == 1)
      {
        paramLinearSystem.addEquality(this.mLeft.mSolverVariable, this.mRight.mSolverVariable, 0, 8);
        paramLinearSystem.addEquality(this.mLeft.mSolverVariable, this.mParent.mLeft.mSolverVariable, 0, 4);
        paramLinearSystem.addEquality(this.mLeft.mSolverVariable, this.mParent.mRight.mSolverVariable, 0, 0);
      }
      else if (i == 2)
      {
        paramLinearSystem.addEquality(this.mBottom.mSolverVariable, this.mTop.mSolverVariable, 0, 8);
        paramLinearSystem.addEquality(this.mTop.mSolverVariable, this.mParent.mBottom.mSolverVariable, 0, 4);
        paramLinearSystem.addEquality(this.mTop.mSolverVariable, this.mParent.mTop.mSolverVariable, 0, 0);
      }
      else if (i == 3)
      {
        paramLinearSystem.addEquality(this.mTop.mSolverVariable, this.mBottom.mSolverVariable, 0, 8);
        paramLinearSystem.addEquality(this.mTop.mSolverVariable, this.mParent.mTop.mSolverVariable, 0, 4);
        paramLinearSystem.addEquality(this.mTop.mSolverVariable, this.mParent.mBottom.mSolverVariable, 0, 0);
      }
      return;
    }
  }
  
  public boolean allSolved()
  {
    int k = 1;
    int j = 0;
    ConstraintWidget localConstraintWidget;
    int i;
    int m;
    while (j < this.mWidgetsCount)
    {
      localConstraintWidget = this.mWidgets[j];
      if ((!this.mAllowsGoneWidget) && (!localConstraintWidget.allowedInBarrier()))
      {
        i = k;
      }
      else
      {
        i = this.mBarrierType;
        if (((i == 0) || (i == 1)) && (!localConstraintWidget.isResolvedHorizontally()))
        {
          i = 0;
        }
        else
        {
          m = this.mBarrierType;
          if (m != 2)
          {
            i = k;
            if (m != 3) {}
          }
          else
          {
            i = k;
            if (!localConstraintWidget.isResolvedVertically()) {
              i = 0;
            }
          }
        }
      }
      j++;
      k = i;
    }
    if ((k != 0) && (this.mWidgetsCount > 0))
    {
      i = 0;
      m = 0;
      for (int n = 0; n < this.mWidgetsCount; n++)
      {
        localConstraintWidget = this.mWidgets[n];
        if ((this.mAllowsGoneWidget) || (localConstraintWidget.allowedInBarrier()))
        {
          k = i;
          j = m;
          if (m == 0)
          {
            j = this.mBarrierType;
            if (j == 0) {
              i = localConstraintWidget.getAnchor(ConstraintAnchor.Type.LEFT).getFinalValue();
            } else if (j == 1) {
              i = localConstraintWidget.getAnchor(ConstraintAnchor.Type.RIGHT).getFinalValue();
            } else if (j == 2) {
              i = localConstraintWidget.getAnchor(ConstraintAnchor.Type.TOP).getFinalValue();
            } else if (j == 3) {
              i = localConstraintWidget.getAnchor(ConstraintAnchor.Type.BOTTOM).getFinalValue();
            }
            j = 1;
            k = i;
          }
          int i1 = this.mBarrierType;
          if (i1 == 0)
          {
            i = Math.min(k, localConstraintWidget.getAnchor(ConstraintAnchor.Type.LEFT).getFinalValue());
            m = j;
          }
          else if (i1 == 1)
          {
            i = Math.max(k, localConstraintWidget.getAnchor(ConstraintAnchor.Type.RIGHT).getFinalValue());
            m = j;
          }
          else if (i1 == 2)
          {
            i = Math.min(k, localConstraintWidget.getAnchor(ConstraintAnchor.Type.TOP).getFinalValue());
            m = j;
          }
          else
          {
            i = k;
            m = j;
            if (i1 == 3)
            {
              i = Math.max(k, localConstraintWidget.getAnchor(ConstraintAnchor.Type.BOTTOM).getFinalValue());
              m = j;
            }
          }
        }
      }
      i += this.mMargin;
      j = this.mBarrierType;
      if ((j != 0) && (j != 1)) {
        setFinalVertical(i, i);
      } else {
        setFinalHorizontal(i, i);
      }
      this.resolved = true;
      return true;
    }
    return false;
  }
  
  public boolean allowedInBarrier()
  {
    return true;
  }
  
  @Deprecated
  public boolean allowsGoneWidget()
  {
    return this.mAllowsGoneWidget;
  }
  
  public void copy(ConstraintWidget paramConstraintWidget, HashMap<ConstraintWidget, ConstraintWidget> paramHashMap)
  {
    super.copy(paramConstraintWidget, paramHashMap);
    paramConstraintWidget = (Barrier)paramConstraintWidget;
    this.mBarrierType = paramConstraintWidget.mBarrierType;
    this.mAllowsGoneWidget = paramConstraintWidget.mAllowsGoneWidget;
    this.mMargin = paramConstraintWidget.mMargin;
  }
  
  public boolean getAllowsGoneWidget()
  {
    return this.mAllowsGoneWidget;
  }
  
  public int getBarrierType()
  {
    return this.mBarrierType;
  }
  
  public int getMargin()
  {
    return this.mMargin;
  }
  
  public int getOrientation()
  {
    switch (this.mBarrierType)
    {
    default: 
      return -1;
    case 2: 
    case 3: 
      return 1;
    }
    return 0;
  }
  
  public boolean isResolvedHorizontally()
  {
    return this.resolved;
  }
  
  public boolean isResolvedVertically()
  {
    return this.resolved;
  }
  
  protected void markWidgets()
  {
    for (int i = 0; i < this.mWidgetsCount; i++)
    {
      ConstraintWidget localConstraintWidget = this.mWidgets[i];
      if ((this.mAllowsGoneWidget) || (localConstraintWidget.allowedInBarrier()))
      {
        int j = this.mBarrierType;
        if ((j != 0) && (j != 1))
        {
          if ((j == 2) || (j == 3)) {
            localConstraintWidget.setInBarrier(1, true);
          }
        }
        else {
          localConstraintWidget.setInBarrier(0, true);
        }
      }
    }
  }
  
  public void setAllowsGoneWidget(boolean paramBoolean)
  {
    this.mAllowsGoneWidget = paramBoolean;
  }
  
  public void setBarrierType(int paramInt)
  {
    this.mBarrierType = paramInt;
  }
  
  public void setMargin(int paramInt)
  {
    this.mMargin = paramInt;
  }
  
  public String toString()
  {
    String str1 = "[Barrier] " + getDebugName() + " {";
    for (int i = 0; i < this.mWidgetsCount; i++)
    {
      ConstraintWidget localConstraintWidget = this.mWidgets[i];
      String str2 = str1;
      if (i > 0) {
        str2 = str1 + ", ";
      }
      str1 = str2 + localConstraintWidget.getDebugName();
    }
    return str1 + "}";
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/constraintlayout/core/widgets/Barrier.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */