package androidx.constraintlayout.core.widgets;

import androidx.constraintlayout.core.ArrayRow;
import androidx.constraintlayout.core.LinearSystem;
import androidx.constraintlayout.core.SolverVariable;
import java.util.ArrayList;

public class Chain
{
  private static final boolean DEBUG = false;
  public static final boolean USE_CHAIN_OPTIMIZATION = false;
  
  static void applyChainConstraints(ConstraintWidgetContainer paramConstraintWidgetContainer, LinearSystem paramLinearSystem, int paramInt1, int paramInt2, ChainHead paramChainHead)
  {
    ConstraintWidget localConstraintWidget1 = paramChainHead.mFirst;
    ConstraintWidget localConstraintWidget3 = paramChainHead.mLast;
    Object localObject2 = paramChainHead.mFirstVisibleWidget;
    ConstraintWidget localConstraintWidget2 = paramChainHead.mLastVisibleWidget;
    Object localObject4 = paramChainHead.mHead;
    float f1 = paramChainHead.mTotalWeight;
    Object localObject5 = paramChainHead.mFirstMatchConstraintWidget;
    Object localObject6 = paramChainHead.mLastMatchConstraintWidget;
    int i1;
    if (paramConstraintWidgetContainer.mListDimensionBehaviors[paramInt1] == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT) {
      i1 = 1;
    } else {
      i1 = 0;
    }
    int i;
    int j;
    int m;
    int k;
    Object localObject1;
    int n;
    int i2;
    if (paramInt1 == 0)
    {
      if (((ConstraintWidget)localObject4).mHorizontalChainStyle == 0) {
        i = 1;
      } else {
        i = 0;
      }
      j = ((ConstraintWidget)localObject4).mHorizontalChainStyle;
      m = i;
      if (j == 1) {
        i = 1;
      } else {
        i = 0;
      }
      if (((ConstraintWidget)localObject4).mHorizontalChainStyle == 2) {
        j = 1;
      } else {
        j = 0;
      }
      k = 0;
      localObject1 = localConstraintWidget1;
      n = i;
      i2 = j;
      i = k;
    }
    else
    {
      if (((ConstraintWidget)localObject4).mVerticalChainStyle == 0) {
        i = 1;
      } else {
        i = 0;
      }
      j = ((ConstraintWidget)localObject4).mVerticalChainStyle;
      m = i;
      if (j == 1) {
        i = 1;
      } else {
        i = 0;
      }
      if (((ConstraintWidget)localObject4).mVerticalChainStyle == 2) {
        j = 1;
      } else {
        j = 0;
      }
      k = 0;
      localObject1 = localConstraintWidget1;
      n = i;
      i = k;
      i2 = j;
    }
    Object localObject3;
    while (i == 0)
    {
      localObject3 = localObject1.mListAnchors[paramInt2];
      j = 4;
      if (i2 != 0) {
        j = 1;
      }
      int i3 = ((ConstraintAnchor)localObject3).getMargin();
      int i4;
      if ((localObject1.mListDimensionBehaviors[paramInt1] == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) && (localObject1.mResolvedMatchConstraintDefault[paramInt1] == 0)) {
        i4 = 1;
      } else {
        i4 = 0;
      }
      if ((((ConstraintAnchor)localObject3).mTarget != null) && (localObject1 != localConstraintWidget1)) {
        i3 += ((ConstraintAnchor)localObject3).mTarget.getMargin();
      }
      k = j;
      if (i2 != 0)
      {
        k = j;
        if (localObject1 != localConstraintWidget1)
        {
          k = j;
          if (localObject1 != localObject2) {
            k = 8;
          }
        }
      }
      if (((ConstraintAnchor)localObject3).mTarget != null)
      {
        if (localObject1 == localObject2) {
          paramLinearSystem.addGreaterThan(((ConstraintAnchor)localObject3).mSolverVariable, ((ConstraintAnchor)localObject3).mTarget.mSolverVariable, i3, 6);
        } else {
          paramLinearSystem.addGreaterThan(((ConstraintAnchor)localObject3).mSolverVariable, ((ConstraintAnchor)localObject3).mTarget.mSolverVariable, i3, 8);
        }
        j = k;
        if (i4 != 0)
        {
          j = k;
          if (i2 == 0) {
            j = 5;
          }
        }
        if ((localObject1 == localObject2) && (i2 != 0) && (((ConstraintWidget)localObject1).isInBarrier(paramInt1))) {
          j = 5;
        }
        paramLinearSystem.addEquality(((ConstraintAnchor)localObject3).mSolverVariable, ((ConstraintAnchor)localObject3).mTarget.mSolverVariable, i3, j);
      }
      if (i1 != 0)
      {
        if ((((ConstraintWidget)localObject1).getVisibility() != 8) && (localObject1.mListDimensionBehaviors[paramInt1] == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT)) {
          paramLinearSystem.addGreaterThan(localObject1.mListAnchors[(paramInt2 + 1)].mSolverVariable, localObject1.mListAnchors[paramInt2].mSolverVariable, 0, 5);
        }
        paramLinearSystem.addGreaterThan(localObject1.mListAnchors[paramInt2].mSolverVariable, paramConstraintWidgetContainer.mListAnchors[paramInt2].mSolverVariable, 0, 8);
      }
      localObject3 = localObject1.mListAnchors[(paramInt2 + 1)].mTarget;
      if (localObject3 != null)
      {
        localObject3 = ((ConstraintAnchor)localObject3).mOwner;
        if ((localObject3.mListAnchors[paramInt2].mTarget != null) && (localObject3.mListAnchors[paramInt2].mTarget.mOwner == localObject1)) {
          break label667;
        }
        localObject3 = null;
      }
      else
      {
        localObject3 = null;
      }
      label667:
      if (localObject3 != null) {
        localObject1 = localObject3;
      } else {
        i = 1;
      }
    }
    if ((localConstraintWidget2 != null) && (localConstraintWidget3.mListAnchors[(paramInt2 + 1)].mTarget != null))
    {
      localObject3 = localConstraintWidget2.mListAnchors[(paramInt2 + 1)];
      if ((localConstraintWidget2.mListDimensionBehaviors[paramInt1] == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) && (localConstraintWidget2.mResolvedMatchConstraintDefault[paramInt1] == 0)) {
        i = 1;
      } else {
        i = 0;
      }
      if ((i != 0) && (i2 == 0) && (((ConstraintAnchor)localObject3).mTarget.mOwner == paramConstraintWidgetContainer)) {
        paramLinearSystem.addEquality(((ConstraintAnchor)localObject3).mSolverVariable, ((ConstraintAnchor)localObject3).mTarget.mSolverVariable, -((ConstraintAnchor)localObject3).getMargin(), 5);
      } else if ((i2 != 0) && (((ConstraintAnchor)localObject3).mTarget.mOwner == paramConstraintWidgetContainer)) {
        paramLinearSystem.addEquality(((ConstraintAnchor)localObject3).mSolverVariable, ((ConstraintAnchor)localObject3).mTarget.mSolverVariable, -((ConstraintAnchor)localObject3).getMargin(), 4);
      }
      paramLinearSystem.addLowerThan(((ConstraintAnchor)localObject3).mSolverVariable, localConstraintWidget3.mListAnchors[(paramInt2 + 1)].mTarget.mSolverVariable, -((ConstraintAnchor)localObject3).getMargin(), 6);
    }
    if (i1 != 0) {
      paramLinearSystem.addGreaterThan(paramConstraintWidgetContainer.mListAnchors[(paramInt2 + 1)].mSolverVariable, localConstraintWidget3.mListAnchors[(paramInt2 + 1)].mSolverVariable, localConstraintWidget3.mListAnchors[(paramInt2 + 1)].getMargin(), 8);
    }
    paramConstraintWidgetContainer = paramChainHead.mWeightedMatchConstraintsWidgets;
    SolverVariable localSolverVariable1;
    Object localObject7;
    if (paramConstraintWidgetContainer != null)
    {
      i = paramConstraintWidgetContainer.size();
      if (i > 1)
      {
        localObject3 = null;
        float f3 = 0.0F;
        float f2;
        if ((paramChainHead.mHasUndefinedWeights) && (!paramChainHead.mHasComplexMatchWeights)) {
          f2 = paramChainHead.mWidgetsMatchCount;
        } else {
          f2 = f1;
        }
        j = 0;
        while (j < i)
        {
          localObject5 = (ConstraintWidget)paramConstraintWidgetContainer.get(j);
          f1 = localObject5.mWeight[paramInt1];
          if (f1 < 0.0F)
          {
            if (paramChainHead.mHasComplexMatchWeights)
            {
              paramLinearSystem.addEquality(localObject5.mListAnchors[(paramInt2 + 1)].mSolverVariable, localObject5.mListAnchors[paramInt2].mSolverVariable, 0, 4);
              f1 = f3;
              break label1204;
            }
            f1 = 1.0F;
          }
          if (f1 == 0.0F)
          {
            paramLinearSystem.addEquality(localObject5.mListAnchors[(paramInt2 + 1)].mSolverVariable, localObject5.mListAnchors[paramInt2].mSolverVariable, 0, 8);
            f1 = f3;
          }
          else
          {
            if (localObject3 != null)
            {
              localObject6 = localObject3.mListAnchors[paramInt2].mSolverVariable;
              SolverVariable localSolverVariable2 = localObject3.mListAnchors[(paramInt2 + 1)].mSolverVariable;
              localSolverVariable1 = localObject5.mListAnchors[paramInt2].mSolverVariable;
              localObject3 = localObject5.mListAnchors[(paramInt2 + 1)].mSolverVariable;
              localObject7 = paramLinearSystem.createRow();
              ((ArrayRow)localObject7).createRowEqualMatchDimensions(f3, f2, f1, (SolverVariable)localObject6, localSolverVariable2, localSolverVariable1, (SolverVariable)localObject3);
              paramLinearSystem.addConstraint((ArrayRow)localObject7);
            }
            localObject3 = localObject5;
          }
          label1204:
          j++;
          f3 = f1;
        }
        localObject3 = paramConstraintWidgetContainer;
        paramConstraintWidgetContainer = (ConstraintWidgetContainer)localObject4;
        localObject1 = localObject3;
      }
      else
      {
        localObject3 = paramConstraintWidgetContainer;
        paramConstraintWidgetContainer = (ConstraintWidgetContainer)localObject4;
        localObject1 = localObject3;
      }
    }
    else
    {
      localObject3 = localObject1;
      localObject1 = paramConstraintWidgetContainer;
      paramConstraintWidgetContainer = (ConstraintWidgetContainer)localObject4;
      localObject1 = localObject3;
    }
    if (localObject2 != null)
    {
      if ((localObject2 != localConstraintWidget2) && (i2 == 0)) {
        break label1446;
      }
      paramChainHead = localConstraintWidget1.mListAnchors[paramInt2];
      localObject3 = localConstraintWidget3.mListAnchors[(paramInt2 + 1)];
      if (paramChainHead.mTarget != null) {
        paramChainHead = paramChainHead.mTarget.mSolverVariable;
      } else {
        paramChainHead = null;
      }
      if (((ConstraintAnchor)localObject3).mTarget != null) {
        localObject1 = ((ConstraintAnchor)localObject3).mTarget.mSolverVariable;
      } else {
        localObject1 = null;
      }
      localObject4 = localObject2.mListAnchors[paramInt2];
      if (localConstraintWidget2 != null) {
        localObject3 = localConstraintWidget2.mListAnchors[(paramInt2 + 1)];
      }
      if ((paramChainHead != null) && (localObject1 != null))
      {
        if (paramInt1 == 0) {
          f1 = paramConstraintWidgetContainer.mHorizontalBiasPercent;
        } else {
          f1 = paramConstraintWidgetContainer.mVerticalBiasPercent;
        }
        i = ((ConstraintAnchor)localObject4).getMargin();
        paramInt1 = ((ConstraintAnchor)localObject3).getMargin();
        paramLinearSystem.addCentering(((ConstraintAnchor)localObject4).mSolverVariable, paramChainHead, i, f1, (SolverVariable)localObject1, ((ConstraintAnchor)localObject3).mSolverVariable, paramInt1, 7);
      }
      break label2487;
    }
    label1446:
    if ((m != 0) && (localObject2 != null))
    {
      if ((paramChainHead.mWidgetsMatchCount > 0) && (paramChainHead.mWidgetsCount == paramChainHead.mWidgetsMatchCount)) {
        j = 1;
      } else {
        j = 0;
      }
      localObject1 = localObject2;
      paramChainHead = (ChainHead)localObject2;
      while (localObject1 != null)
      {
        for (paramConstraintWidgetContainer = localObject1.mNextChainWidget[paramInt1]; (paramConstraintWidgetContainer != null) && (paramConstraintWidgetContainer.getVisibility() == 8); paramConstraintWidgetContainer = paramConstraintWidgetContainer.mNextChainWidget[paramInt1]) {}
        if ((paramConstraintWidgetContainer == null) && (localObject1 != localConstraintWidget2)) {
          break label1887;
        }
        localObject4 = localObject1.mListAnchors[paramInt2];
        localObject6 = ((ConstraintAnchor)localObject4).mSolverVariable;
        if (((ConstraintAnchor)localObject4).mTarget != null) {
          localObject3 = ((ConstraintAnchor)localObject4).mTarget.mSolverVariable;
        } else {
          localObject3 = null;
        }
        if (paramChainHead != localObject1) {
          localObject3 = paramChainHead.mListAnchors[(paramInt2 + 1)].mSolverVariable;
        } else if (localObject1 == localObject2) {
          if (localConstraintWidget1.mListAnchors[paramInt2].mTarget != null) {
            localObject3 = localConstraintWidget1.mListAnchors[paramInt2].mTarget.mSolverVariable;
          } else {
            localObject3 = null;
          }
        }
        k = ((ConstraintAnchor)localObject4).getMargin();
        i = localObject1.mListAnchors[(paramInt2 + 1)].getMargin();
        if (paramConstraintWidgetContainer != null)
        {
          localObject4 = paramConstraintWidgetContainer.mListAnchors[paramInt2];
          localObject5 = ((ConstraintAnchor)localObject4).mSolverVariable;
        }
        else
        {
          localObject4 = localConstraintWidget3.mListAnchors[(paramInt2 + 1)].mTarget;
          if (localObject4 != null) {
            localObject5 = ((ConstraintAnchor)localObject4).mSolverVariable;
          } else {
            localObject5 = null;
          }
        }
        localSolverVariable1 = localObject1.mListAnchors[(paramInt2 + 1)].mSolverVariable;
        if (localObject4 != null) {
          i += ((ConstraintAnchor)localObject4).getMargin();
        }
        i1 = paramChainHead.mListAnchors[(paramInt2 + 1)].getMargin();
        if ((localObject6 != null) && (localObject3 != null) && (localObject5 != null) && (localSolverVariable1 != null))
        {
          if (localObject1 == localObject2) {
            k = localObject2.mListAnchors[paramInt2].getMargin();
          } else {
            k += i1;
          }
          if (localObject1 == localConstraintWidget2) {
            i = localConstraintWidget2.mListAnchors[(paramInt2 + 1)].getMargin();
          }
          if (j != 0) {
            i1 = 8;
          } else {
            i1 = 5;
          }
          paramLinearSystem.addCentering((SolverVariable)localObject6, (SolverVariable)localObject3, k, 0.5F, (SolverVariable)localObject5, localSolverVariable1, i, i1);
        }
        label1887:
        if (((ConstraintWidget)localObject1).getVisibility() != 8) {
          paramChainHead = (ChainHead)localObject1;
        }
        localObject1 = paramConstraintWidgetContainer;
      }
    }
    else
    {
      i = 8;
      if ((n != 0) && (localObject2 != null))
      {
        if ((paramChainHead.mWidgetsMatchCount > 0) && (paramChainHead.mWidgetsCount == paramChainHead.mWidgetsMatchCount)) {
          j = 1;
        } else {
          j = 0;
        }
        paramChainHead = (ChainHead)localObject2;
        localObject1 = localObject2;
        while (paramChainHead != null)
        {
          for (paramConstraintWidgetContainer = paramChainHead.mNextChainWidget[paramInt1]; (paramConstraintWidgetContainer != null) && (paramConstraintWidgetContainer.getVisibility() == i); paramConstraintWidgetContainer = paramConstraintWidgetContainer.mNextChainWidget[paramInt1]) {}
          if ((paramChainHead != localObject2) && (paramChainHead != localConstraintWidget2) && (paramConstraintWidgetContainer != null))
          {
            if (paramConstraintWidgetContainer == localConstraintWidget2) {
              paramConstraintWidgetContainer = null;
            }
            localObject4 = paramChainHead.mListAnchors[paramInt2];
            localSolverVariable1 = ((ConstraintAnchor)localObject4).mSolverVariable;
            if (((ConstraintAnchor)localObject4).mTarget != null) {
              localObject3 = ((ConstraintAnchor)localObject4).mTarget.mSolverVariable;
            }
            localObject7 = localObject1.mListAnchors[(paramInt2 + 1)].mSolverVariable;
            localObject3 = null;
            i1 = ((ConstraintAnchor)localObject4).getMargin();
            i = paramChainHead.mListAnchors[(paramInt2 + 1)].getMargin();
            if (paramConstraintWidgetContainer != null)
            {
              localObject6 = paramConstraintWidgetContainer.mListAnchors[paramInt2];
              localObject5 = ((ConstraintAnchor)localObject6).mSolverVariable;
              if (((ConstraintAnchor)localObject6).mTarget != null) {
                localObject3 = ((ConstraintAnchor)localObject6).mTarget.mSolverVariable;
              } else {
                localObject3 = null;
              }
              localObject4 = localObject3;
              localObject3 = localObject5;
              localObject5 = localObject4;
              localObject4 = localObject6;
            }
            else
            {
              localObject4 = localConstraintWidget2.mListAnchors[paramInt2];
              if (localObject4 != null) {
                localObject3 = ((ConstraintAnchor)localObject4).mSolverVariable;
              }
              localObject5 = paramChainHead.mListAnchors[(paramInt2 + 1)].mSolverVariable;
            }
            if (localObject4 != null) {
              i += ((ConstraintAnchor)localObject4).getMargin();
            }
            i2 = localObject1.mListAnchors[(paramInt2 + 1)].getMargin();
            if (j != 0) {
              k = 8;
            } else {
              k = 4;
            }
            if ((localSolverVariable1 != null) && (localObject7 != null) && (localObject3 != null) && (localObject5 != null)) {
              paramLinearSystem.addCentering(localSolverVariable1, (SolverVariable)localObject7, i1 + i2, 0.5F, (SolverVariable)localObject3, (SolverVariable)localObject5, i, k);
            }
            i = 8;
          }
          if (paramChainHead.getVisibility() != i) {
            localObject1 = paramChainHead;
          }
          paramChainHead = paramConstraintWidgetContainer;
        }
        paramConstraintWidgetContainer = localObject2.mListAnchors[paramInt2];
        paramChainHead = localConstraintWidget1.mListAnchors[paramInt2].mTarget;
        localObject1 = localConstraintWidget2.mListAnchors[(paramInt2 + 1)];
        localObject3 = localConstraintWidget3.mListAnchors[(paramInt2 + 1)].mTarget;
        if (paramChainHead != null) {
          if (localObject2 != localConstraintWidget2) {
            paramLinearSystem.addEquality(paramConstraintWidgetContainer.mSolverVariable, paramChainHead.mSolverVariable, paramConstraintWidgetContainer.getMargin(), 5);
          } else if (localObject3 != null) {
            paramLinearSystem.addCentering(paramConstraintWidgetContainer.mSolverVariable, paramChainHead.mSolverVariable, paramConstraintWidgetContainer.getMargin(), 0.5F, ((ConstraintAnchor)localObject1).mSolverVariable, ((ConstraintAnchor)localObject3).mSolverVariable, ((ConstraintAnchor)localObject1).getMargin(), 5);
          } else {}
        }
        if ((localObject3 != null) && (localObject2 != localConstraintWidget2)) {
          paramLinearSystem.addEquality(((ConstraintAnchor)localObject1).mSolverVariable, ((ConstraintAnchor)localObject3).mSolverVariable, -((ConstraintAnchor)localObject1).getMargin(), 5);
        }
      }
    }
    label2487:
    paramChainHead = localConstraintWidget2;
    if (m == 0)
    {
      paramConstraintWidgetContainer = paramChainHead;
      if (n == 0) {}
    }
    else
    {
      paramConstraintWidgetContainer = paramChainHead;
      if (localObject2 != null)
      {
        paramConstraintWidgetContainer = paramChainHead;
        if (localObject2 != paramChainHead)
        {
          localObject3 = localObject2.mListAnchors[paramInt2];
          paramConstraintWidgetContainer = paramChainHead;
          if (paramChainHead == null) {
            paramConstraintWidgetContainer = (ConstraintWidgetContainer)localObject2;
          }
          localObject4 = paramConstraintWidgetContainer.mListAnchors[(paramInt2 + 1)];
          if (((ConstraintAnchor)localObject3).mTarget != null) {
            localObject1 = ((ConstraintAnchor)localObject3).mTarget.mSolverVariable;
          } else {
            localObject1 = null;
          }
          if (((ConstraintAnchor)localObject4).mTarget != null) {
            paramChainHead = ((ConstraintAnchor)localObject4).mTarget.mSolverVariable;
          } else {
            paramChainHead = null;
          }
          if (localConstraintWidget3 != paramConstraintWidgetContainer)
          {
            paramChainHead = localConstraintWidget3.mListAnchors[(paramInt2 + 1)];
            if (paramChainHead.mTarget != null) {
              paramChainHead = paramChainHead.mTarget.mSolverVariable;
            } else {
              paramChainHead = null;
            }
          }
          if (localObject2 == paramConstraintWidgetContainer)
          {
            localObject3 = localObject2.mListAnchors[paramInt2];
            localObject4 = localObject2.mListAnchors[(paramInt2 + 1)];
            localObject2 = localObject3;
            localObject3 = localObject4;
          }
          else
          {
            localObject2 = localObject3;
            localObject3 = localObject4;
          }
          if ((localObject1 != null) && (paramChainHead != null))
          {
            paramInt1 = ((ConstraintAnchor)localObject2).getMargin();
            paramInt2 = paramConstraintWidgetContainer.mListAnchors[(paramInt2 + 1)].getMargin();
            paramLinearSystem.addCentering(((ConstraintAnchor)localObject2).mSolverVariable, (SolverVariable)localObject1, paramInt1, 0.5F, paramChainHead, ((ConstraintAnchor)localObject3).mSolverVariable, paramInt2, 5);
          }
        }
      }
    }
  }
  
  public static void applyChainConstraints(ConstraintWidgetContainer paramConstraintWidgetContainer, LinearSystem paramLinearSystem, ArrayList<ConstraintWidget> paramArrayList, int paramInt)
  {
    int i;
    int j;
    ChainHead[] arrayOfChainHead;
    if (paramInt == 0)
    {
      i = 0;
      j = paramConstraintWidgetContainer.mHorizontalChainsSize;
      arrayOfChainHead = paramConstraintWidgetContainer.mHorizontalChainsArray;
    }
    else
    {
      i = 2;
      j = paramConstraintWidgetContainer.mVerticalChainsSize;
      arrayOfChainHead = paramConstraintWidgetContainer.mVerticalChainsArray;
    }
    for (int k = 0; k < j; k++)
    {
      ChainHead localChainHead = arrayOfChainHead[k];
      localChainHead.define();
      if ((paramArrayList == null) || ((paramArrayList != null) && (paramArrayList.contains(localChainHead.mFirst)))) {
        applyChainConstraints(paramConstraintWidgetContainer, paramLinearSystem, paramInt, i, localChainHead);
      }
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/constraintlayout/core/widgets/Chain.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */