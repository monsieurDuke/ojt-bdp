package androidx.constraintlayout.core.widgets.analyzer;

import androidx.constraintlayout.core.LinearSystem;
import androidx.constraintlayout.core.widgets.Barrier;
import androidx.constraintlayout.core.widgets.ChainHead;
import androidx.constraintlayout.core.widgets.ConstraintAnchor;
import androidx.constraintlayout.core.widgets.ConstraintAnchor.Type;
import androidx.constraintlayout.core.widgets.ConstraintWidget;
import androidx.constraintlayout.core.widgets.ConstraintWidget.DimensionBehaviour;
import androidx.constraintlayout.core.widgets.ConstraintWidgetContainer;
import androidx.constraintlayout.core.widgets.Guideline;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

public class Direct
{
  private static final boolean APPLY_MATCH_PARENT = false;
  private static final boolean DEBUG = false;
  private static final boolean EARLY_TERMINATION = true;
  private static int hcount = 0;
  private static BasicMeasure.Measure measure = new BasicMeasure.Measure();
  private static int vcount = 0;
  
  private static boolean canMeasure(int paramInt, ConstraintWidget paramConstraintWidget)
  {
    ConstraintWidget.DimensionBehaviour localDimensionBehaviour2 = paramConstraintWidget.getHorizontalDimensionBehaviour();
    ConstraintWidget.DimensionBehaviour localDimensionBehaviour1 = paramConstraintWidget.getVerticalDimensionBehaviour();
    ConstraintWidgetContainer localConstraintWidgetContainer;
    if (paramConstraintWidget.getParent() != null) {
      localConstraintWidgetContainer = (ConstraintWidgetContainer)paramConstraintWidget.getParent();
    } else {
      localConstraintWidgetContainer = null;
    }
    boolean bool2 = false;
    if (((localConstraintWidgetContainer == null) || (localConstraintWidgetContainer.getHorizontalDimensionBehaviour() == ConstraintWidget.DimensionBehaviour.FIXED)) || (((localConstraintWidgetContainer == null) || (localConstraintWidgetContainer.getVerticalDimensionBehaviour() == ConstraintWidget.DimensionBehaviour.FIXED)) || ((localDimensionBehaviour2 != ConstraintWidget.DimensionBehaviour.FIXED) && (!paramConstraintWidget.isResolvedHorizontally()) && (localDimensionBehaviour2 != ConstraintWidget.DimensionBehaviour.WRAP_CONTENT) && ((localDimensionBehaviour2 != ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) || (paramConstraintWidget.mMatchConstraintDefaultWidth != 0) || (paramConstraintWidget.mDimensionRatio != 0.0F) || (!paramConstraintWidget.hasDanglingDimension(0))) && ((localDimensionBehaviour2 != ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) || (paramConstraintWidget.mMatchConstraintDefaultWidth != 1) || (!paramConstraintWidget.hasResolvedTargets(0, paramConstraintWidget.getWidth())))))) {
      paramInt = 0;
    } else {
      paramInt = 1;
    }
    int i;
    if ((localDimensionBehaviour1 != ConstraintWidget.DimensionBehaviour.FIXED) && (!paramConstraintWidget.isResolvedVertically()) && (localDimensionBehaviour1 != ConstraintWidget.DimensionBehaviour.WRAP_CONTENT) && ((localDimensionBehaviour1 != ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) || (paramConstraintWidget.mMatchConstraintDefaultHeight != 0) || (paramConstraintWidget.mDimensionRatio != 0.0F) || (!paramConstraintWidget.hasDanglingDimension(1))) && ((localDimensionBehaviour1 != ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) || (paramConstraintWidget.mMatchConstraintDefaultHeight != 1) || (!paramConstraintWidget.hasResolvedTargets(1, paramConstraintWidget.getHeight())))) {
      i = 0;
    } else {
      i = 1;
    }
    if ((paramConstraintWidget.mDimensionRatio > 0.0F) && ((paramInt != 0) || (i != 0))) {
      return true;
    }
    boolean bool1 = bool2;
    if (paramInt != 0)
    {
      bool1 = bool2;
      if (i != 0) {
        bool1 = true;
      }
    }
    return bool1;
  }
  
  private static void horizontalSolvingPass(int paramInt, ConstraintWidget paramConstraintWidget, BasicMeasure.Measurer paramMeasurer, boolean paramBoolean)
  {
    if (paramConstraintWidget.isHorizontalSolvingPassDone()) {
      return;
    }
    hcount += 1;
    if ((!(paramConstraintWidget instanceof ConstraintWidgetContainer)) && (paramConstraintWidget.isMeasureRequested()) && (canMeasure(paramInt + 1, paramConstraintWidget))) {
      ConstraintWidgetContainer.measure(paramInt + 1, paramConstraintWidget, paramMeasurer, new BasicMeasure.Measure(), BasicMeasure.Measure.SELF_DIMENSIONS);
    }
    Object localObject2 = paramConstraintWidget.getAnchor(ConstraintAnchor.Type.LEFT);
    Object localObject1 = paramConstraintWidget.getAnchor(ConstraintAnchor.Type.RIGHT);
    int k = ((ConstraintAnchor)localObject2).getFinalValue();
    int j = ((ConstraintAnchor)localObject1).getFinalValue();
    Object localObject3;
    boolean bool;
    int i;
    if ((((ConstraintAnchor)localObject2).getDependents() != null) && (((ConstraintAnchor)localObject2).hasFinalValue()))
    {
      localObject3 = ((ConstraintAnchor)localObject2).getDependents().iterator();
      while (((Iterator)localObject3).hasNext())
      {
        ConstraintAnchor localConstraintAnchor = (ConstraintAnchor)((Iterator)localObject3).next();
        localObject2 = localConstraintAnchor.mOwner;
        bool = canMeasure(paramInt + 1, (ConstraintWidget)localObject2);
        if ((((ConstraintWidget)localObject2).isMeasureRequested()) && (bool)) {
          ConstraintWidgetContainer.measure(paramInt + 1, (ConstraintWidget)localObject2, paramMeasurer, new BasicMeasure.Measure(), BasicMeasure.Measure.SELF_DIMENSIONS);
        }
        if (((localConstraintAnchor == ((ConstraintWidget)localObject2).mLeft) && (((ConstraintWidget)localObject2).mRight.mTarget != null) && (((ConstraintWidget)localObject2).mRight.mTarget.hasFinalValue())) || ((localConstraintAnchor == ((ConstraintWidget)localObject2).mRight) && (((ConstraintWidget)localObject2).mLeft.mTarget != null) && (((ConstraintWidget)localObject2).mLeft.mTarget.hasFinalValue()))) {
          i = 1;
        } else {
          i = 0;
        }
        if ((((ConstraintWidget)localObject2).getHorizontalDimensionBehaviour() == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) && (!bool))
        {
          if ((((ConstraintWidget)localObject2).getHorizontalDimensionBehaviour() == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) && (((ConstraintWidget)localObject2).mMatchConstraintMaxWidth >= 0) && (((ConstraintWidget)localObject2).mMatchConstraintMinWidth >= 0) && ((((ConstraintWidget)localObject2).getVisibility() == 8) || ((((ConstraintWidget)localObject2).mMatchConstraintDefaultWidth == 0) && (((ConstraintWidget)localObject2).getDimensionRatio() == 0.0F))) && (!((ConstraintWidget)localObject2).isInHorizontalChain()) && (!((ConstraintWidget)localObject2).isInVirtualLayout()) && (i != 0) && (!((ConstraintWidget)localObject2).isInHorizontalChain())) {
            solveHorizontalMatchConstraint(paramInt + 1, paramConstraintWidget, paramMeasurer, (ConstraintWidget)localObject2, paramBoolean);
          }
        }
        else if (!((ConstraintWidget)localObject2).isMeasureRequested()) {
          if ((localConstraintAnchor == ((ConstraintWidget)localObject2).mLeft) && (((ConstraintWidget)localObject2).mRight.mTarget == null))
          {
            i = ((ConstraintWidget)localObject2).mLeft.getMargin() + k;
            ((ConstraintWidget)localObject2).setFinalHorizontal(i, ((ConstraintWidget)localObject2).getWidth() + i);
            horizontalSolvingPass(paramInt + 1, (ConstraintWidget)localObject2, paramMeasurer, paramBoolean);
          }
          else if ((localConstraintAnchor == ((ConstraintWidget)localObject2).mRight) && (((ConstraintWidget)localObject2).mLeft.mTarget == null))
          {
            i = k - ((ConstraintWidget)localObject2).mRight.getMargin();
            ((ConstraintWidget)localObject2).setFinalHorizontal(i - ((ConstraintWidget)localObject2).getWidth(), i);
            horizontalSolvingPass(paramInt + 1, (ConstraintWidget)localObject2, paramMeasurer, paramBoolean);
          }
          else if ((i != 0) && (!((ConstraintWidget)localObject2).isInHorizontalChain()))
          {
            solveHorizontalCenterConstraints(paramInt + 1, paramMeasurer, (ConstraintWidget)localObject2, paramBoolean);
          }
        }
      }
    }
    if ((paramConstraintWidget instanceof Guideline)) {
      return;
    }
    if ((((ConstraintAnchor)localObject1).getDependents() != null) && (((ConstraintAnchor)localObject1).hasFinalValue()))
    {
      localObject1 = ((ConstraintAnchor)localObject1).getDependents().iterator();
      label1015:
      while (((Iterator)localObject1).hasNext())
      {
        localObject3 = (ConstraintAnchor)((Iterator)localObject1).next();
        localObject2 = ((ConstraintAnchor)localObject3).mOwner;
        bool = canMeasure(paramInt + 1, (ConstraintWidget)localObject2);
        if ((((ConstraintWidget)localObject2).isMeasureRequested()) && (bool)) {
          ConstraintWidgetContainer.measure(paramInt + 1, (ConstraintWidget)localObject2, paramMeasurer, new BasicMeasure.Measure(), BasicMeasure.Measure.SELF_DIMENSIONS);
        }
        if (((localObject3 == ((ConstraintWidget)localObject2).mLeft) && (((ConstraintWidget)localObject2).mRight.mTarget != null) && (((ConstraintWidget)localObject2).mRight.mTarget.hasFinalValue())) || ((localObject3 == ((ConstraintWidget)localObject2).mRight) && (((ConstraintWidget)localObject2).mLeft.mTarget != null) && (((ConstraintWidget)localObject2).mLeft.mTarget.hasFinalValue()))) {
          i = 1;
        } else {
          i = 0;
        }
        if ((((ConstraintWidget)localObject2).getHorizontalDimensionBehaviour() == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) && (!bool))
        {
          if ((((ConstraintWidget)localObject2).getHorizontalDimensionBehaviour() == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) && (((ConstraintWidget)localObject2).mMatchConstraintMaxWidth >= 0) && (((ConstraintWidget)localObject2).mMatchConstraintMinWidth >= 0))
          {
            if (((ConstraintWidget)localObject2).getVisibility() != 8) {
              if (((ConstraintWidget)localObject2).mMatchConstraintDefaultWidth == 0) {
                if (((ConstraintWidget)localObject2).getDimensionRatio() != 0.0F) {
                  break label1015;
                }
              } else {
                break label1015;
              }
            }
            if ((((ConstraintWidget)localObject2).isInHorizontalChain()) || (((ConstraintWidget)localObject2).isInVirtualLayout()) || (i == 0) || (((ConstraintWidget)localObject2).isInHorizontalChain())) {
              break label1015;
            }
            solveHorizontalMatchConstraint(paramInt + 1, paramConstraintWidget, paramMeasurer, (ConstraintWidget)localObject2, paramBoolean);
          }
        }
        else if (!((ConstraintWidget)localObject2).isMeasureRequested()) {
          if ((localObject3 == ((ConstraintWidget)localObject2).mLeft) && (((ConstraintWidget)localObject2).mRight.mTarget == null))
          {
            i = ((ConstraintWidget)localObject2).mLeft.getMargin() + j;
            ((ConstraintWidget)localObject2).setFinalHorizontal(i, ((ConstraintWidget)localObject2).getWidth() + i);
            horizontalSolvingPass(paramInt + 1, (ConstraintWidget)localObject2, paramMeasurer, paramBoolean);
          }
          else if ((localObject3 == ((ConstraintWidget)localObject2).mRight) && (((ConstraintWidget)localObject2).mLeft.mTarget == null))
          {
            i = j - ((ConstraintWidget)localObject2).mRight.getMargin();
            ((ConstraintWidget)localObject2).setFinalHorizontal(i - ((ConstraintWidget)localObject2).getWidth(), i);
            horizontalSolvingPass(paramInt + 1, (ConstraintWidget)localObject2, paramMeasurer, paramBoolean);
          }
          else if ((i != 0) && (!((ConstraintWidget)localObject2).isInHorizontalChain()))
          {
            solveHorizontalCenterConstraints(paramInt + 1, paramMeasurer, (ConstraintWidget)localObject2, paramBoolean);
          }
        }
      }
    }
    paramConstraintWidget.markHorizontalSolvingPassDone();
  }
  
  public static String ls(int paramInt)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    for (int i = 0; i < paramInt; i++) {
      localStringBuilder.append("  ");
    }
    localStringBuilder.append("+-(" + paramInt + ") ");
    return localStringBuilder.toString();
  }
  
  private static void solveBarrier(int paramInt1, Barrier paramBarrier, BasicMeasure.Measurer paramMeasurer, int paramInt2, boolean paramBoolean)
  {
    if (paramBarrier.allSolved()) {
      if (paramInt2 == 0) {
        horizontalSolvingPass(paramInt1 + 1, paramBarrier, paramMeasurer, paramBoolean);
      } else {
        verticalSolvingPass(paramInt1 + 1, paramBarrier, paramMeasurer);
      }
    }
  }
  
  public static boolean solveChain(ConstraintWidgetContainer paramConstraintWidgetContainer, LinearSystem paramLinearSystem, int paramInt1, int paramInt2, ChainHead paramChainHead, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3)
  {
    if (paramBoolean3) {
      return false;
    }
    if (paramInt1 == 0)
    {
      if (!paramConstraintWidgetContainer.isResolvedHorizontally()) {
        return false;
      }
    }
    else if (!paramConstraintWidgetContainer.isResolvedVertically()) {
      return false;
    }
    paramBoolean3 = paramConstraintWidgetContainer.isRtl();
    ConstraintWidget localConstraintWidget1 = paramChainHead.getFirst();
    ConstraintWidget localConstraintWidget2 = paramChainHead.getLast();
    ConstraintWidget localConstraintWidget4 = paramChainHead.getFirstVisibleWidget();
    ConstraintWidget localConstraintWidget5 = paramChainHead.getLastVisibleWidget();
    ConstraintWidget localConstraintWidget6 = paramChainHead.getHead();
    Object localObject3 = localConstraintWidget1;
    int i = 0;
    Object localObject1 = localConstraintWidget1.mListAnchors[paramInt2];
    paramChainHead = localConstraintWidget2.mListAnchors[(paramInt2 + 1)];
    if ((((ConstraintAnchor)localObject1).mTarget != null) && (paramChainHead.mTarget != null))
    {
      if ((((ConstraintAnchor)localObject1).mTarget.hasFinalValue()) && (paramChainHead.mTarget.hasFinalValue()))
      {
        if ((localConstraintWidget4 != null) && (localConstraintWidget5 != null))
        {
          int n = ((ConstraintAnchor)localObject1).mTarget.getFinalValue() + localConstraintWidget4.mListAnchors[paramInt2].getMargin();
          int i1 = paramChainHead.mTarget.getFinalValue() - localConstraintWidget5.mListAnchors[(paramInt2 + 1)].getMargin();
          int i2 = i1 - n;
          if (i2 <= 0) {
            return false;
          }
          int m = 0;
          Object localObject2 = new BasicMeasure.Measure();
          int k = 0;
          int j = 0;
          while (i == 0)
          {
            if (!canMeasure(0 + 1, (ConstraintWidget)localObject3)) {
              return false;
            }
            if (localObject3.mListDimensionBehaviors[paramInt1] == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
              return false;
            }
            if (((ConstraintWidget)localObject3).isMeasureRequested()) {
              ConstraintWidgetContainer.measure(0 + 1, (ConstraintWidget)localObject3, paramConstraintWidgetContainer.getMeasurer(), (BasicMeasure.Measure)localObject2, BasicMeasure.Measure.SELF_DIMENSIONS);
            }
            m += localObject3.mListAnchors[paramInt2].getMargin();
            if (paramInt1 == 0) {
              m += ((ConstraintWidget)localObject3).getWidth();
            } else {
              m += ((ConstraintWidget)localObject3).getHeight();
            }
            m += localObject3.mListAnchors[(paramInt2 + 1)].getMargin();
            k++;
            if (((ConstraintWidget)localObject3).getVisibility() != 8) {
              j++;
            }
            paramChainHead = localObject3.mListAnchors[(paramInt2 + 1)].mTarget;
            if (paramChainHead != null)
            {
              ConstraintWidget localConstraintWidget3 = paramChainHead.mOwner;
              if (localConstraintWidget3.mListAnchors[paramInt2].mTarget != null)
              {
                paramChainHead = localConstraintWidget3;
                if (localConstraintWidget3.mListAnchors[paramInt2].mTarget.mOwner == localObject3) {}
              }
              else
              {
                paramChainHead = null;
              }
            }
            else
            {
              paramChainHead = null;
            }
            if (paramChainHead != null) {
              localObject3 = paramChainHead;
            } else {
              i = 1;
            }
          }
          if (j == 0) {
            return false;
          }
          if (j != k) {
            return false;
          }
          if (i2 < m) {
            return false;
          }
          i = i2 - m;
          if (paramBoolean1) {
            i /= (j + 1);
          } else if (paramBoolean2) {
            if (j > 2) {
              i = i / j - 1;
            } else {}
          }
          if (j == 1)
          {
            float f;
            if (paramInt1 == 0) {
              f = localConstraintWidget6.getHorizontalBiasPercent();
            } else {
              f = localConstraintWidget6.getVerticalBiasPercent();
            }
            paramInt2 = (int)(n + 0.5F + i * f);
            if (paramInt1 == 0) {
              localConstraintWidget4.setFinalHorizontal(paramInt2, localConstraintWidget4.getWidth() + paramInt2);
            } else {
              localConstraintWidget4.setFinalVertical(paramInt2, localConstraintWidget4.getHeight() + paramInt2);
            }
            horizontalSolvingPass(0 + 1, localConstraintWidget4, paramConstraintWidgetContainer.getMeasurer(), paramBoolean3);
            return true;
          }
          if (paramBoolean1)
          {
            j = 0;
            k = n + i;
            localObject1 = localConstraintWidget1;
            while (j == 0)
            {
              if (((ConstraintWidget)localObject1).getVisibility() == 8)
              {
                if (paramInt1 == 0)
                {
                  ((ConstraintWidget)localObject1).setFinalHorizontal(k, k);
                  horizontalSolvingPass(0 + 1, (ConstraintWidget)localObject1, paramConstraintWidgetContainer.getMeasurer(), paramBoolean3);
                }
                else
                {
                  ((ConstraintWidget)localObject1).setFinalVertical(k, k);
                  verticalSolvingPass(0 + 1, (ConstraintWidget)localObject1, paramConstraintWidgetContainer.getMeasurer());
                }
              }
              else
              {
                k += localObject1.mListAnchors[paramInt2].getMargin();
                if (paramInt1 == 0)
                {
                  ((ConstraintWidget)localObject1).setFinalHorizontal(k, ((ConstraintWidget)localObject1).getWidth() + k);
                  horizontalSolvingPass(0 + 1, (ConstraintWidget)localObject1, paramConstraintWidgetContainer.getMeasurer(), paramBoolean3);
                  k += ((ConstraintWidget)localObject1).getWidth();
                }
                else
                {
                  ((ConstraintWidget)localObject1).setFinalVertical(k, ((ConstraintWidget)localObject1).getHeight() + k);
                  verticalSolvingPass(0 + 1, (ConstraintWidget)localObject1, paramConstraintWidgetContainer.getMeasurer());
                  k += ((ConstraintWidget)localObject1).getHeight();
                }
                k = k + localObject1.mListAnchors[(paramInt2 + 1)].getMargin() + i;
              }
              ((ConstraintWidget)localObject1).addToSolver(paramLinearSystem, false);
              paramChainHead = localObject1.mListAnchors[(paramInt2 + 1)].mTarget;
              if (paramChainHead != null)
              {
                localObject2 = paramChainHead.mOwner;
                if (localObject2.mListAnchors[paramInt2].mTarget != null)
                {
                  paramChainHead = (ChainHead)localObject2;
                  if (localObject2.mListAnchors[paramInt2].mTarget.mOwner == localObject1) {}
                }
                else
                {
                  paramChainHead = null;
                }
              }
              else
              {
                paramChainHead = null;
              }
              if (paramChainHead != null) {
                localObject1 = paramChainHead;
              } else {
                j = 1;
              }
            }
          }
          else if (paramBoolean2)
          {
            if (j == 2)
            {
              if (paramInt1 == 0)
              {
                localConstraintWidget4.setFinalHorizontal(n, localConstraintWidget4.getWidth() + n);
                localConstraintWidget5.setFinalHorizontal(i1 - localConstraintWidget5.getWidth(), i1);
                horizontalSolvingPass(0 + 1, localConstraintWidget4, paramConstraintWidgetContainer.getMeasurer(), paramBoolean3);
                horizontalSolvingPass(0 + 1, localConstraintWidget5, paramConstraintWidgetContainer.getMeasurer(), paramBoolean3);
              }
              else
              {
                localConstraintWidget4.setFinalVertical(n, localConstraintWidget4.getHeight() + n);
                localConstraintWidget5.setFinalVertical(i1 - localConstraintWidget5.getHeight(), i1);
                verticalSolvingPass(0 + 1, localConstraintWidget4, paramConstraintWidgetContainer.getMeasurer());
                verticalSolvingPass(0 + 1, localConstraintWidget5, paramConstraintWidgetContainer.getMeasurer());
              }
              return true;
            }
            return false;
          }
          return true;
        }
        return false;
      }
      return false;
    }
    return false;
  }
  
  private static void solveHorizontalCenterConstraints(int paramInt, BasicMeasure.Measurer paramMeasurer, ConstraintWidget paramConstraintWidget, boolean paramBoolean)
  {
    float f = paramConstraintWidget.getHorizontalBiasPercent();
    int m = paramConstraintWidget.mLeft.mTarget.getFinalValue();
    int k = paramConstraintWidget.mRight.mTarget.getFinalValue();
    int i = paramConstraintWidget.mLeft.getMargin() + m;
    int j = k - paramConstraintWidget.mRight.getMargin();
    if (m == k)
    {
      f = 0.5F;
      i = m;
      j = k;
    }
    int i1 = paramConstraintWidget.getWidth();
    k = j - i - i1;
    if (i > j) {
      k = i - j - i1;
    }
    if (k > 0) {
      k = (int)(k * f + 0.5F);
    } else {
      k = (int)(k * f);
    }
    m = i + k;
    int n = m + i1;
    if (i > j)
    {
      m = i + k;
      n = m - i1;
    }
    paramConstraintWidget.setFinalHorizontal(m, n);
    horizontalSolvingPass(paramInt + 1, paramConstraintWidget, paramMeasurer, paramBoolean);
  }
  
  private static void solveHorizontalMatchConstraint(int paramInt, ConstraintWidget paramConstraintWidget1, BasicMeasure.Measurer paramMeasurer, ConstraintWidget paramConstraintWidget2, boolean paramBoolean)
  {
    float f = paramConstraintWidget2.getHorizontalBiasPercent();
    int k = paramConstraintWidget2.mLeft.mTarget.getFinalValue() + paramConstraintWidget2.mLeft.getMargin();
    int m = paramConstraintWidget2.mRight.mTarget.getFinalValue() - paramConstraintWidget2.mRight.getMargin();
    if (m >= k)
    {
      int j = paramConstraintWidget2.getWidth();
      int i = j;
      if (paramConstraintWidget2.getVisibility() != 8)
      {
        if (paramConstraintWidget2.mMatchConstraintDefaultWidth == 2)
        {
          if ((paramConstraintWidget1 instanceof ConstraintWidgetContainer)) {
            i = paramConstraintWidget1.getWidth();
          } else {
            i = paramConstraintWidget1.getParent().getWidth();
          }
          i = (int)(paramConstraintWidget2.getHorizontalBiasPercent() * 0.5F * i);
        }
        else
        {
          i = j;
          if (paramConstraintWidget2.mMatchConstraintDefaultWidth == 0) {
            i = m - k;
          }
        }
        j = Math.max(paramConstraintWidget2.mMatchConstraintMinWidth, i);
        i = j;
        if (paramConstraintWidget2.mMatchConstraintMaxWidth > 0) {
          i = Math.min(paramConstraintWidget2.mMatchConstraintMaxWidth, j);
        }
      }
      j = k + (int)((m - k - i) * f + 0.5F);
      paramConstraintWidget2.setFinalHorizontal(j, j + i);
      horizontalSolvingPass(paramInt + 1, paramConstraintWidget2, paramMeasurer, paramBoolean);
    }
  }
  
  private static void solveVerticalCenterConstraints(int paramInt, BasicMeasure.Measurer paramMeasurer, ConstraintWidget paramConstraintWidget)
  {
    float f = paramConstraintWidget.getVerticalBiasPercent();
    int m = paramConstraintWidget.mTop.mTarget.getFinalValue();
    int k = paramConstraintWidget.mBottom.mTarget.getFinalValue();
    int i = paramConstraintWidget.mTop.getMargin() + m;
    int j = k - paramConstraintWidget.mBottom.getMargin();
    if (m == k)
    {
      f = 0.5F;
      i = m;
      j = k;
    }
    int i1 = paramConstraintWidget.getHeight();
    k = j - i - i1;
    if (i > j) {
      k = i - j - i1;
    }
    if (k > 0) {
      k = (int)(k * f + 0.5F);
    } else {
      k = (int)(k * f);
    }
    int n = i + k;
    m = n + i1;
    if (i > j)
    {
      n = i - k;
      m = n - i1;
    }
    paramConstraintWidget.setFinalVertical(n, m);
    verticalSolvingPass(paramInt + 1, paramConstraintWidget, paramMeasurer);
  }
  
  private static void solveVerticalMatchConstraint(int paramInt, ConstraintWidget paramConstraintWidget1, BasicMeasure.Measurer paramMeasurer, ConstraintWidget paramConstraintWidget2)
  {
    float f = paramConstraintWidget2.getVerticalBiasPercent();
    int m = paramConstraintWidget2.mTop.mTarget.getFinalValue() + paramConstraintWidget2.mTop.getMargin();
    int k = paramConstraintWidget2.mBottom.mTarget.getFinalValue() - paramConstraintWidget2.mBottom.getMargin();
    if (k >= m)
    {
      int j = paramConstraintWidget2.getHeight();
      int i = j;
      if (paramConstraintWidget2.getVisibility() != 8)
      {
        if (paramConstraintWidget2.mMatchConstraintDefaultHeight == 2)
        {
          if ((paramConstraintWidget1 instanceof ConstraintWidgetContainer)) {
            i = paramConstraintWidget1.getHeight();
          } else {
            i = paramConstraintWidget1.getParent().getHeight();
          }
          i = (int)(f * 0.5F * i);
        }
        else
        {
          i = j;
          if (paramConstraintWidget2.mMatchConstraintDefaultHeight == 0) {
            i = k - m;
          }
        }
        j = Math.max(paramConstraintWidget2.mMatchConstraintMinHeight, i);
        i = j;
        if (paramConstraintWidget2.mMatchConstraintMaxHeight > 0) {
          i = Math.min(paramConstraintWidget2.mMatchConstraintMaxHeight, j);
        }
      }
      j = m + (int)((k - m - i) * f + 0.5F);
      paramConstraintWidget2.setFinalVertical(j, j + i);
      verticalSolvingPass(paramInt + 1, paramConstraintWidget2, paramMeasurer);
    }
  }
  
  public static void solvingPass(ConstraintWidgetContainer paramConstraintWidgetContainer, BasicMeasure.Measurer paramMeasurer)
  {
    Object localObject2 = paramConstraintWidgetContainer.getHorizontalDimensionBehaviour();
    Object localObject1 = paramConstraintWidgetContainer.getVerticalDimensionBehaviour();
    hcount = 0;
    vcount = 0;
    paramConstraintWidgetContainer.resetFinalResolution();
    ArrayList localArrayList = paramConstraintWidgetContainer.getChildren();
    int n = localArrayList.size();
    for (int i = 0; i < n; i++) {
      ((ConstraintWidget)localArrayList.get(i)).resetFinalResolution();
    }
    boolean bool = paramConstraintWidgetContainer.isRtl();
    if (localObject2 == ConstraintWidget.DimensionBehaviour.FIXED) {
      paramConstraintWidgetContainer.setFinalHorizontal(0, paramConstraintWidgetContainer.getWidth());
    } else {
      paramConstraintWidgetContainer.setFinalLeft(0);
    }
    i = 0;
    int k = 0;
    int m = 0;
    int j;
    while (m < n)
    {
      localObject2 = (ConstraintWidget)localArrayList.get(m);
      if ((localObject2 instanceof Guideline))
      {
        localObject2 = (Guideline)localObject2;
        j = i;
        if (((Guideline)localObject2).getOrientation() == 1)
        {
          if (((Guideline)localObject2).getRelativeBegin() != -1) {
            ((Guideline)localObject2).setFinalValue(((Guideline)localObject2).getRelativeBegin());
          } else if ((((Guideline)localObject2).getRelativeEnd() != -1) && (paramConstraintWidgetContainer.isResolvedHorizontally())) {
            ((Guideline)localObject2).setFinalValue(paramConstraintWidgetContainer.getWidth() - ((Guideline)localObject2).getRelativeEnd());
          } else if (paramConstraintWidgetContainer.isResolvedHorizontally()) {
            ((Guideline)localObject2).setFinalValue((int)(((Guideline)localObject2).getRelativePercent() * paramConstraintWidgetContainer.getWidth() + 0.5F));
          }
          j = 1;
        }
      }
      for (;;)
      {
        break;
        j = i;
        if ((localObject2 instanceof Barrier))
        {
          j = i;
          if (((Barrier)localObject2).getOrientation() == 0)
          {
            k = 1;
            j = i;
          }
        }
      }
      m++;
      i = j;
    }
    if (i != 0) {
      for (i = 0; i < n; i++)
      {
        localObject2 = (ConstraintWidget)localArrayList.get(i);
        if ((localObject2 instanceof Guideline))
        {
          localObject2 = (Guideline)localObject2;
          if (((Guideline)localObject2).getOrientation() == 1) {
            horizontalSolvingPass(0, (ConstraintWidget)localObject2, paramMeasurer, bool);
          }
        }
      }
    }
    horizontalSolvingPass(0, paramConstraintWidgetContainer, paramMeasurer, bool);
    if (k != 0) {
      for (i = 0; i < n; i++)
      {
        localObject2 = (ConstraintWidget)localArrayList.get(i);
        if ((localObject2 instanceof Barrier))
        {
          localObject2 = (Barrier)localObject2;
          if (((Barrier)localObject2).getOrientation() == 0) {
            solveBarrier(0, (Barrier)localObject2, paramMeasurer, 0, bool);
          }
        }
      }
    }
    if (localObject1 == ConstraintWidget.DimensionBehaviour.FIXED) {
      paramConstraintWidgetContainer.setFinalVertical(0, paramConstraintWidgetContainer.getHeight());
    } else {
      paramConstraintWidgetContainer.setFinalTop(0);
    }
    i = 0;
    k = 0;
    m = 0;
    while (m < n)
    {
      localObject1 = (ConstraintWidget)localArrayList.get(m);
      if ((localObject1 instanceof Guideline))
      {
        localObject1 = (Guideline)localObject1;
        if (((Guideline)localObject1).getOrientation() == 0)
        {
          if (((Guideline)localObject1).getRelativeBegin() != -1) {
            ((Guideline)localObject1).setFinalValue(((Guideline)localObject1).getRelativeBegin());
          } else if ((((Guideline)localObject1).getRelativeEnd() != -1) && (paramConstraintWidgetContainer.isResolvedVertically())) {
            ((Guideline)localObject1).setFinalValue(paramConstraintWidgetContainer.getHeight() - ((Guideline)localObject1).getRelativeEnd());
          } else if (paramConstraintWidgetContainer.isResolvedVertically()) {
            ((Guideline)localObject1).setFinalValue((int)(((Guideline)localObject1).getRelativePercent() * paramConstraintWidgetContainer.getHeight() + 0.5F));
          }
          j = 1;
        }
        else
        {
          j = i;
        }
      }
      for (;;)
      {
        break;
        j = i;
        if ((localObject1 instanceof Barrier))
        {
          j = i;
          if (((Barrier)localObject1).getOrientation() == 1)
          {
            k = 1;
            j = i;
          }
        }
      }
      m++;
      i = j;
    }
    if (i != 0) {
      for (i = 0; i < n; i++)
      {
        localObject1 = (ConstraintWidget)localArrayList.get(i);
        if ((localObject1 instanceof Guideline))
        {
          localObject1 = (Guideline)localObject1;
          if (((Guideline)localObject1).getOrientation() == 0) {
            verticalSolvingPass(1, (ConstraintWidget)localObject1, paramMeasurer);
          }
        }
      }
    }
    verticalSolvingPass(0, paramConstraintWidgetContainer, paramMeasurer);
    if (k != 0) {
      for (i = 0; i < n; i++)
      {
        paramConstraintWidgetContainer = (ConstraintWidget)localArrayList.get(i);
        if ((paramConstraintWidgetContainer instanceof Barrier))
        {
          paramConstraintWidgetContainer = (Barrier)paramConstraintWidgetContainer;
          if (paramConstraintWidgetContainer.getOrientation() == 1) {
            solveBarrier(0, paramConstraintWidgetContainer, paramMeasurer, 1, bool);
          }
        }
      }
    }
    for (i = 0; i < n; i++)
    {
      paramConstraintWidgetContainer = (ConstraintWidget)localArrayList.get(i);
      if ((paramConstraintWidgetContainer.isMeasureRequested()) && (canMeasure(0, paramConstraintWidgetContainer)))
      {
        ConstraintWidgetContainer.measure(0, paramConstraintWidgetContainer, paramMeasurer, measure, BasicMeasure.Measure.SELF_DIMENSIONS);
        if ((paramConstraintWidgetContainer instanceof Guideline))
        {
          if (((Guideline)paramConstraintWidgetContainer).getOrientation() == 0) {
            verticalSolvingPass(0, paramConstraintWidgetContainer, paramMeasurer);
          } else {
            horizontalSolvingPass(0, paramConstraintWidgetContainer, paramMeasurer, bool);
          }
        }
        else
        {
          horizontalSolvingPass(0, paramConstraintWidgetContainer, paramMeasurer, bool);
          verticalSolvingPass(0, paramConstraintWidgetContainer, paramMeasurer);
        }
      }
    }
  }
  
  private static void verticalSolvingPass(int paramInt, ConstraintWidget paramConstraintWidget, BasicMeasure.Measurer paramMeasurer)
  {
    if (paramConstraintWidget.isVerticalSolvingPassDone()) {
      return;
    }
    vcount += 1;
    if ((!(paramConstraintWidget instanceof ConstraintWidgetContainer)) && (paramConstraintWidget.isMeasureRequested()) && (canMeasure(paramInt + 1, paramConstraintWidget))) {
      ConstraintWidgetContainer.measure(paramInt + 1, paramConstraintWidget, paramMeasurer, new BasicMeasure.Measure(), BasicMeasure.Measure.SELF_DIMENSIONS);
    }
    Object localObject2 = paramConstraintWidget.getAnchor(ConstraintAnchor.Type.TOP);
    Object localObject1 = paramConstraintWidget.getAnchor(ConstraintAnchor.Type.BOTTOM);
    int k = ((ConstraintAnchor)localObject2).getFinalValue();
    int j = ((ConstraintAnchor)localObject1).getFinalValue();
    Object localObject3;
    boolean bool;
    int i;
    if ((((ConstraintAnchor)localObject2).getDependents() != null) && (((ConstraintAnchor)localObject2).hasFinalValue()))
    {
      localObject3 = ((ConstraintAnchor)localObject2).getDependents().iterator();
      while (((Iterator)localObject3).hasNext())
      {
        localObject2 = (ConstraintAnchor)((Iterator)localObject3).next();
        ConstraintWidget localConstraintWidget = ((ConstraintAnchor)localObject2).mOwner;
        bool = canMeasure(paramInt + 1, localConstraintWidget);
        if ((localConstraintWidget.isMeasureRequested()) && (bool)) {
          ConstraintWidgetContainer.measure(paramInt + 1, localConstraintWidget, paramMeasurer, new BasicMeasure.Measure(), BasicMeasure.Measure.SELF_DIMENSIONS);
        }
        if (((localObject2 == localConstraintWidget.mTop) && (localConstraintWidget.mBottom.mTarget != null) && (localConstraintWidget.mBottom.mTarget.hasFinalValue())) || ((localObject2 == localConstraintWidget.mBottom) && (localConstraintWidget.mTop.mTarget != null) && (localConstraintWidget.mTop.mTarget.hasFinalValue()))) {
          i = 1;
        } else {
          i = 0;
        }
        if ((localConstraintWidget.getVerticalDimensionBehaviour() == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) && (!bool))
        {
          if ((localConstraintWidget.getVerticalDimensionBehaviour() == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) && (localConstraintWidget.mMatchConstraintMaxHeight >= 0) && (localConstraintWidget.mMatchConstraintMinHeight >= 0) && ((localConstraintWidget.getVisibility() == 8) || ((localConstraintWidget.mMatchConstraintDefaultHeight == 0) && (localConstraintWidget.getDimensionRatio() == 0.0F))) && (!localConstraintWidget.isInVerticalChain()) && (!localConstraintWidget.isInVirtualLayout()) && (i != 0) && (!localConstraintWidget.isInVerticalChain())) {
            solveVerticalMatchConstraint(paramInt + 1, paramConstraintWidget, paramMeasurer, localConstraintWidget);
          }
        }
        else if (!localConstraintWidget.isMeasureRequested()) {
          if ((localObject2 == localConstraintWidget.mTop) && (localConstraintWidget.mBottom.mTarget == null))
          {
            i = localConstraintWidget.mTop.getMargin() + k;
            localConstraintWidget.setFinalVertical(i, localConstraintWidget.getHeight() + i);
            verticalSolvingPass(paramInt + 1, localConstraintWidget, paramMeasurer);
          }
          else if ((localObject2 == localConstraintWidget.mBottom) && (localConstraintWidget.mTop.mTarget == null))
          {
            i = k - localConstraintWidget.mBottom.getMargin();
            localConstraintWidget.setFinalVertical(i - localConstraintWidget.getHeight(), i);
            verticalSolvingPass(paramInt + 1, localConstraintWidget, paramMeasurer);
          }
          else if ((i != 0) && (!localConstraintWidget.isInVerticalChain()))
          {
            solveVerticalCenterConstraints(paramInt + 1, paramMeasurer, localConstraintWidget);
          }
        }
      }
    }
    if ((paramConstraintWidget instanceof Guideline)) {
      return;
    }
    if ((((ConstraintAnchor)localObject1).getDependents() != null) && (((ConstraintAnchor)localObject1).hasFinalValue()))
    {
      localObject3 = ((ConstraintAnchor)localObject1).getDependents().iterator();
      label987:
      while (((Iterator)localObject3).hasNext())
      {
        localObject1 = (ConstraintAnchor)((Iterator)localObject3).next();
        localObject2 = ((ConstraintAnchor)localObject1).mOwner;
        bool = canMeasure(paramInt + 1, (ConstraintWidget)localObject2);
        if ((((ConstraintWidget)localObject2).isMeasureRequested()) && (bool)) {
          ConstraintWidgetContainer.measure(paramInt + 1, (ConstraintWidget)localObject2, paramMeasurer, new BasicMeasure.Measure(), BasicMeasure.Measure.SELF_DIMENSIONS);
        }
        if (((localObject1 == ((ConstraintWidget)localObject2).mTop) && (((ConstraintWidget)localObject2).mBottom.mTarget != null) && (((ConstraintWidget)localObject2).mBottom.mTarget.hasFinalValue())) || ((localObject1 == ((ConstraintWidget)localObject2).mBottom) && (((ConstraintWidget)localObject2).mTop.mTarget != null) && (((ConstraintWidget)localObject2).mTop.mTarget.hasFinalValue()))) {
          i = 1;
        } else {
          i = 0;
        }
        if ((((ConstraintWidget)localObject2).getVerticalDimensionBehaviour() == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) && (!bool))
        {
          if ((((ConstraintWidget)localObject2).getVerticalDimensionBehaviour() == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) && (((ConstraintWidget)localObject2).mMatchConstraintMaxHeight >= 0) && (((ConstraintWidget)localObject2).mMatchConstraintMinHeight >= 0))
          {
            if (((ConstraintWidget)localObject2).getVisibility() != 8) {
              if (((ConstraintWidget)localObject2).mMatchConstraintDefaultHeight == 0) {
                if (((ConstraintWidget)localObject2).getDimensionRatio() != 0.0F) {
                  break label987;
                }
              } else {
                break label987;
              }
            }
            if ((((ConstraintWidget)localObject2).isInVerticalChain()) || (((ConstraintWidget)localObject2).isInVirtualLayout()) || (i == 0) || (((ConstraintWidget)localObject2).isInVerticalChain())) {
              break label987;
            }
            solveVerticalMatchConstraint(paramInt + 1, paramConstraintWidget, paramMeasurer, (ConstraintWidget)localObject2);
          }
        }
        else if (!((ConstraintWidget)localObject2).isMeasureRequested()) {
          if ((localObject1 == ((ConstraintWidget)localObject2).mTop) && (((ConstraintWidget)localObject2).mBottom.mTarget == null))
          {
            i = ((ConstraintWidget)localObject2).mTop.getMargin() + j;
            ((ConstraintWidget)localObject2).setFinalVertical(i, ((ConstraintWidget)localObject2).getHeight() + i);
            verticalSolvingPass(paramInt + 1, (ConstraintWidget)localObject2, paramMeasurer);
          }
          else if ((localObject1 == ((ConstraintWidget)localObject2).mBottom) && (((ConstraintWidget)localObject2).mTop.mTarget == null))
          {
            i = j - ((ConstraintWidget)localObject2).mBottom.getMargin();
            ((ConstraintWidget)localObject2).setFinalVertical(i - ((ConstraintWidget)localObject2).getHeight(), i);
            verticalSolvingPass(paramInt + 1, (ConstraintWidget)localObject2, paramMeasurer);
          }
          else if ((i != 0) && (!((ConstraintWidget)localObject2).isInVerticalChain()))
          {
            solveVerticalCenterConstraints(paramInt + 1, paramMeasurer, (ConstraintWidget)localObject2);
          }
        }
      }
    }
    localObject1 = paramConstraintWidget.getAnchor(ConstraintAnchor.Type.BASELINE);
    if ((((ConstraintAnchor)localObject1).getDependents() != null) && (((ConstraintAnchor)localObject1).hasFinalValue()))
    {
      i = ((ConstraintAnchor)localObject1).getFinalValue();
      localObject2 = ((ConstraintAnchor)localObject1).getDependents().iterator();
      while (((Iterator)localObject2).hasNext())
      {
        localObject3 = (ConstraintAnchor)((Iterator)localObject2).next();
        localObject1 = ((ConstraintAnchor)localObject3).mOwner;
        bool = canMeasure(paramInt + 1, (ConstraintWidget)localObject1);
        if ((((ConstraintWidget)localObject1).isMeasureRequested()) && (bool)) {
          ConstraintWidgetContainer.measure(paramInt + 1, (ConstraintWidget)localObject1, paramMeasurer, new BasicMeasure.Measure(), BasicMeasure.Measure.SELF_DIMENSIONS);
        }
        if ((((ConstraintWidget)localObject1).getVerticalDimensionBehaviour() != ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) || (bool)) {
          if (!((ConstraintWidget)localObject1).isMeasureRequested()) {
            if (localObject3 == ((ConstraintWidget)localObject1).mBaseline)
            {
              ((ConstraintWidget)localObject1).setFinalBaseline(((ConstraintAnchor)localObject3).getMargin() + i);
              verticalSolvingPass(paramInt + 1, (ConstraintWidget)localObject1, paramMeasurer);
            }
          }
        }
      }
    }
    paramConstraintWidget.markVerticalSolvingPassDone();
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/constraintlayout/core/widgets/analyzer/Direct.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */