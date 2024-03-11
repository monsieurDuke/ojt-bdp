package androidx.constraintlayout.core.widgets.analyzer;

import androidx.constraintlayout.core.LinearSystem;
import androidx.constraintlayout.core.Metrics;
import androidx.constraintlayout.core.widgets.Barrier;
import androidx.constraintlayout.core.widgets.ConstraintAnchor;
import androidx.constraintlayout.core.widgets.ConstraintAnchor.Type;
import androidx.constraintlayout.core.widgets.ConstraintWidget;
import androidx.constraintlayout.core.widgets.ConstraintWidget.DimensionBehaviour;
import androidx.constraintlayout.core.widgets.ConstraintWidgetContainer;
import androidx.constraintlayout.core.widgets.Guideline;
import androidx.constraintlayout.core.widgets.Helper;
import androidx.constraintlayout.core.widgets.Optimizer;
import androidx.constraintlayout.core.widgets.VirtualLayout;
import java.util.ArrayList;

public class BasicMeasure
{
  public static final int AT_MOST = Integer.MIN_VALUE;
  private static final boolean DEBUG = false;
  public static final int EXACTLY = 1073741824;
  public static final int FIXED = -3;
  public static final int MATCH_PARENT = -1;
  private static final int MODE_SHIFT = 30;
  public static final int UNSPECIFIED = 0;
  public static final int WRAP_CONTENT = -2;
  private ConstraintWidgetContainer constraintWidgetContainer;
  private Measure mMeasure = new Measure();
  private final ArrayList<ConstraintWidget> mVariableDimensionsWidgets = new ArrayList();
  
  public BasicMeasure(ConstraintWidgetContainer paramConstraintWidgetContainer)
  {
    this.constraintWidgetContainer = paramConstraintWidgetContainer;
  }
  
  private boolean measure(Measurer paramMeasurer, ConstraintWidget paramConstraintWidget, int paramInt)
  {
    this.mMeasure.horizontalBehavior = paramConstraintWidget.getHorizontalDimensionBehaviour();
    this.mMeasure.verticalBehavior = paramConstraintWidget.getVerticalDimensionBehaviour();
    this.mMeasure.horizontalDimension = paramConstraintWidget.getWidth();
    this.mMeasure.verticalDimension = paramConstraintWidget.getHeight();
    this.mMeasure.measuredNeedsSolverPass = false;
    this.mMeasure.measureStrategy = paramInt;
    int i;
    if (this.mMeasure.horizontalBehavior == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
      i = 1;
    } else {
      i = 0;
    }
    if (this.mMeasure.verticalBehavior == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
      paramInt = 1;
    } else {
      paramInt = 0;
    }
    if ((i != 0) && (paramConstraintWidget.mDimensionRatio > 0.0F)) {
      i = 1;
    } else {
      i = 0;
    }
    if ((paramInt != 0) && (paramConstraintWidget.mDimensionRatio > 0.0F)) {
      paramInt = 1;
    } else {
      paramInt = 0;
    }
    if ((i != 0) && (paramConstraintWidget.mResolvedMatchConstraintDefault[0] == 4)) {
      this.mMeasure.horizontalBehavior = ConstraintWidget.DimensionBehaviour.FIXED;
    }
    if ((paramInt != 0) && (paramConstraintWidget.mResolvedMatchConstraintDefault[1] == 4)) {
      this.mMeasure.verticalBehavior = ConstraintWidget.DimensionBehaviour.FIXED;
    }
    paramMeasurer.measure(paramConstraintWidget, this.mMeasure);
    paramConstraintWidget.setWidth(this.mMeasure.measuredWidth);
    paramConstraintWidget.setHeight(this.mMeasure.measuredHeight);
    paramConstraintWidget.setHasBaseline(this.mMeasure.measuredHasBaseline);
    paramConstraintWidget.setBaselineDistance(this.mMeasure.measuredBaseline);
    this.mMeasure.measureStrategy = Measure.SELF_DIMENSIONS;
    return this.mMeasure.measuredNeedsSolverPass;
  }
  
  private void measureChildren(ConstraintWidgetContainer paramConstraintWidgetContainer)
  {
    int m = paramConstraintWidgetContainer.mChildren.size();
    boolean bool = paramConstraintWidgetContainer.optimizeFor(64);
    Measurer localMeasurer = paramConstraintWidgetContainer.getMeasurer();
    for (int k = 0; k < m; k++)
    {
      ConstraintWidget localConstraintWidget = (ConstraintWidget)paramConstraintWidgetContainer.mChildren.get(k);
      if ((!(localConstraintWidget instanceof Guideline)) && (!(localConstraintWidget instanceof Barrier)) && (!localConstraintWidget.isInVirtualLayout()) && ((!bool) || (localConstraintWidget.horizontalRun == null) || (localConstraintWidget.verticalRun == null) || (!localConstraintWidget.horizontalRun.dimension.resolved) || (!localConstraintWidget.verticalRun.dimension.resolved)))
      {
        int j = 0;
        Object localObject = localConstraintWidget.getDimensionBehaviour(0);
        ConstraintWidget.DimensionBehaviour localDimensionBehaviour = localConstraintWidget.getDimensionBehaviour(1);
        int i = j;
        if (localObject == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT)
        {
          i = j;
          if (localConstraintWidget.mMatchConstraintDefaultWidth != 1)
          {
            i = j;
            if (localDimensionBehaviour == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT)
            {
              i = j;
              if (localConstraintWidget.mMatchConstraintDefaultHeight != 1) {
                i = 1;
              }
            }
          }
        }
        j = i;
        if (i == 0)
        {
          j = i;
          if (paramConstraintWidgetContainer.optimizeFor(1))
          {
            j = i;
            if (!(localConstraintWidget instanceof VirtualLayout))
            {
              j = i;
              if (localObject == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT)
              {
                j = i;
                if (localConstraintWidget.mMatchConstraintDefaultWidth == 0)
                {
                  j = i;
                  if (localDimensionBehaviour != ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT)
                  {
                    j = i;
                    if (!localConstraintWidget.isInHorizontalChain()) {
                      j = 1;
                    }
                  }
                }
              }
              i = j;
              if (localDimensionBehaviour == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT)
              {
                i = j;
                if (localConstraintWidget.mMatchConstraintDefaultHeight == 0)
                {
                  i = j;
                  if (localObject != ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT)
                  {
                    i = j;
                    if (!localConstraintWidget.isInHorizontalChain()) {
                      i = 1;
                    }
                  }
                }
              }
              if (localObject != ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT)
              {
                j = i;
                if (localDimensionBehaviour != ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {}
              }
              else
              {
                j = i;
                if (localConstraintWidget.mDimensionRatio > 0.0F) {
                  j = 1;
                }
              }
            }
          }
        }
        if (j == 0)
        {
          measure(localMeasurer, localConstraintWidget, Measure.SELF_DIMENSIONS);
          if (paramConstraintWidgetContainer.mMetrics != null)
          {
            localObject = paramConstraintWidgetContainer.mMetrics;
            ((Metrics)localObject).measuredWidgets += 1L;
          }
        }
      }
    }
    localMeasurer.didMeasures();
  }
  
  private void solveLinearSystem(ConstraintWidgetContainer paramConstraintWidgetContainer, String paramString, int paramInt1, int paramInt2, int paramInt3)
  {
    int i = paramConstraintWidgetContainer.getMinWidth();
    int j = paramConstraintWidgetContainer.getMinHeight();
    paramConstraintWidgetContainer.setMinWidth(0);
    paramConstraintWidgetContainer.setMinHeight(0);
    paramConstraintWidgetContainer.setWidth(paramInt2);
    paramConstraintWidgetContainer.setHeight(paramInt3);
    paramConstraintWidgetContainer.setMinWidth(i);
    paramConstraintWidgetContainer.setMinHeight(j);
    this.constraintWidgetContainer.setPass(paramInt1);
    this.constraintWidgetContainer.layout();
  }
  
  public long solverMeasure(ConstraintWidgetContainer paramConstraintWidgetContainer, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8, int paramInt9)
  {
    Measurer localMeasurer = paramConstraintWidgetContainer.getMeasurer();
    long l = 0L;
    paramInt9 = paramConstraintWidgetContainer.mChildren.size();
    int m = paramConstraintWidgetContainer.getWidth();
    int k = paramConstraintWidgetContainer.getHeight();
    boolean bool2 = Optimizer.enabled(paramInt1, 128);
    if ((!bool2) && (!Optimizer.enabled(paramInt1, 64))) {
      paramInt1 = 0;
    } else {
      paramInt1 = 1;
    }
    Object localObject1;
    if (paramInt1 != 0)
    {
      paramInt2 = 0;
      while (paramInt2 < paramInt9)
      {
        localObject1 = (ConstraintWidget)paramConstraintWidgetContainer.mChildren.get(paramInt2);
        if (((ConstraintWidget)localObject1).getHorizontalDimensionBehaviour() == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
          paramInt3 = 1;
        } else {
          paramInt3 = 0;
        }
        if (((ConstraintWidget)localObject1).getVerticalDimensionBehaviour() == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
          paramInt8 = 1;
        } else {
          paramInt8 = 0;
        }
        if ((paramInt3 != 0) && (paramInt8 != 0) && (((ConstraintWidget)localObject1).getDimensionRatio() > 0.0F)) {
          paramInt3 = 1;
        } else {
          paramInt3 = 0;
        }
        if ((((ConstraintWidget)localObject1).isInHorizontalChain()) && (paramInt3 != 0))
        {
          paramInt1 = 0;
        }
        else if ((((ConstraintWidget)localObject1).isInVerticalChain()) && (paramInt3 != 0))
        {
          paramInt1 = 0;
        }
        else if ((localObject1 instanceof VirtualLayout))
        {
          paramInt1 = 0;
        }
        else
        {
          if ((!((ConstraintWidget)localObject1).isInHorizontalChain()) && (!((ConstraintWidget)localObject1).isInVerticalChain()))
          {
            paramInt2++;
            continue;
          }
          paramInt1 = 0;
        }
      }
    }
    if ((paramInt1 != 0) && (LinearSystem.sMetrics != null))
    {
      localObject1 = LinearSystem.sMetrics;
      ((Metrics)localObject1).measures += 1L;
    }
    if (((paramInt4 == 1073741824) && (paramInt6 == 1073741824)) || (bool2)) {
      paramInt2 = 1;
    } else {
      paramInt2 = 0;
    }
    int n = paramInt1 & paramInt2;
    paramInt1 = 0;
    boolean bool1;
    if (n != 0)
    {
      paramInt2 = Math.min(paramConstraintWidgetContainer.getMaxWidth(), paramInt5);
      paramInt3 = Math.min(paramConstraintWidgetContainer.getMaxHeight(), paramInt7);
      if ((paramInt4 == 1073741824) && (paramConstraintWidgetContainer.getWidth() != paramInt2))
      {
        paramConstraintWidgetContainer.setWidth(paramInt2);
        paramConstraintWidgetContainer.invalidateGraph();
      }
      if ((paramInt6 == 1073741824) && (paramConstraintWidgetContainer.getHeight() != paramInt3))
      {
        paramConstraintWidgetContainer.setHeight(paramInt3);
        paramConstraintWidgetContainer.invalidateGraph();
      }
      if ((paramInt4 == 1073741824) && (paramInt6 == 1073741824))
      {
        bool1 = paramConstraintWidgetContainer.directMeasure(bool2);
        paramInt1 = 2;
      }
      else
      {
        bool1 = paramConstraintWidgetContainer.directMeasureSetup(bool2);
        if (paramInt4 == 1073741824)
        {
          bool1 &= paramConstraintWidgetContainer.directMeasureWithOrientation(bool2, 0);
          paramInt1 = 0 + 1;
        }
        if (paramInt6 == 1073741824)
        {
          bool1 &= paramConstraintWidgetContainer.directMeasureWithOrientation(bool2, 1);
          paramInt1++;
        }
      }
      bool2 = true;
      if (bool1)
      {
        if (paramInt4 != 1073741824) {
          bool2 = false;
        }
        boolean bool3;
        if (paramInt6 == 1073741824) {
          bool3 = true;
        } else {
          bool3 = false;
        }
        paramConstraintWidgetContainer.updateFromRuns(bool2, bool3);
      }
    }
    else
    {
      bool1 = false;
      paramInt1 = 0;
    }
    if ((bool1) && (paramInt1 == 2))
    {
      l = 0L;
    }
    else
    {
      int i1 = paramConstraintWidgetContainer.getOptimizationLevel();
      if (paramInt9 > 0) {
        measureChildren(paramConstraintWidgetContainer);
      }
      updateHierarchy(paramConstraintWidgetContainer);
      paramInt4 = this.mVariableDimensionsWidgets.size();
      if (paramInt9 > 0) {
        solveLinearSystem(paramConstraintWidgetContainer, "First pass", 0, m, k);
      }
      if (paramInt4 > 0)
      {
        paramInt3 = 0;
        if (paramConstraintWidgetContainer.getHorizontalDimensionBehaviour() == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT) {
          paramInt6 = 1;
        } else {
          paramInt6 = 0;
        }
        if (paramConstraintWidgetContainer.getVerticalDimensionBehaviour() == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT) {
          paramInt7 = 1;
        } else {
          paramInt7 = 0;
        }
        paramInt2 = Math.max(paramConstraintWidgetContainer.getWidth(), this.constraintWidgetContainer.getMinWidth());
        paramInt1 = Math.max(paramConstraintWidgetContainer.getHeight(), this.constraintWidgetContainer.getMinHeight());
        paramInt8 = 0;
        paramInt5 = paramInt9;
        int j;
        Object localObject2;
        int i2;
        int i;
        while (paramInt8 < paramInt4)
        {
          localObject1 = (ConstraintWidget)this.mVariableDimensionsWidgets.get(paramInt8);
          if ((localObject1 instanceof VirtualLayout))
          {
            j = ((ConstraintWidget)localObject1).getWidth();
            paramInt9 = ((ConstraintWidget)localObject1).getHeight();
            paramInt3 |= measure(localMeasurer, (ConstraintWidget)localObject1, Measure.TRY_GIVEN_DIMENSIONS);
            if (paramConstraintWidgetContainer.mMetrics != null)
            {
              localObject2 = paramConstraintWidgetContainer.mMetrics;
              ((Metrics)localObject2).measuredMatchWidgets += 1L;
            }
            i2 = ((ConstraintWidget)localObject1).getWidth();
            i = ((ConstraintWidget)localObject1).getHeight();
            if (i2 != j)
            {
              ((ConstraintWidget)localObject1).setWidth(i2);
              if ((paramInt6 != 0) && (((ConstraintWidget)localObject1).getRight() > paramInt2)) {
                paramInt2 = Math.max(paramInt2, ((ConstraintWidget)localObject1).getRight() + ((ConstraintWidget)localObject1).getAnchor(ConstraintAnchor.Type.RIGHT).getMargin());
              }
              paramInt3 = 1;
            }
            if (i != paramInt9)
            {
              ((ConstraintWidget)localObject1).setHeight(i);
              if ((paramInt7 != 0) && (((ConstraintWidget)localObject1).getBottom() > paramInt1)) {
                paramInt1 = Math.max(paramInt1, ((ConstraintWidget)localObject1).getBottom() + ((ConstraintWidget)localObject1).getAnchor(ConstraintAnchor.Type.BOTTOM).getMargin());
              }
              paramInt3 = 1;
            }
            paramInt3 |= ((VirtualLayout)localObject1).needSolverPass();
          }
          paramInt8++;
        }
        paramInt8 = 0;
        while (paramInt8 < 2)
        {
          paramInt9 = 0;
          paramInt5 = paramInt3;
          paramInt3 = paramInt4;
          while (paramInt9 < paramInt3)
          {
            localObject2 = (ConstraintWidget)this.mVariableDimensionsWidgets.get(paramInt9);
            if ((((localObject2 instanceof Helper)) && (!(localObject2 instanceof VirtualLayout))) || ((localObject2 instanceof Guideline)) || (((ConstraintWidget)localObject2).getVisibility() == 8) || ((n != 0) && (((ConstraintWidget)localObject2).horizontalRun.dimension.resolved) && (((ConstraintWidget)localObject2).verticalRun.dimension.resolved)) || ((localObject2 instanceof VirtualLayout)))
            {
              paramInt4 = paramInt5;
              i = paramInt2;
              j = paramInt1;
            }
            else
            {
              j = ((ConstraintWidget)localObject2).getWidth();
              i = ((ConstraintWidget)localObject2).getHeight();
              i2 = ((ConstraintWidget)localObject2).getBaselineDistance();
              paramInt4 = Measure.TRY_GIVEN_DIMENSIONS;
              if (paramInt8 == 2 - 1) {
                paramInt4 = Measure.USE_GIVEN_DIMENSIONS;
              }
              paramInt4 = paramInt5 | measure(localMeasurer, (ConstraintWidget)localObject2, paramInt4);
              if (paramConstraintWidgetContainer.mMetrics != null)
              {
                localObject1 = paramConstraintWidgetContainer.mMetrics;
                ((Metrics)localObject1).measuredMatchWidgets += 1L;
              }
              int i3 = ((ConstraintWidget)localObject2).getWidth();
              paramInt5 = ((ConstraintWidget)localObject2).getHeight();
              if (i3 != j)
              {
                ((ConstraintWidget)localObject2).setWidth(i3);
                if ((paramInt6 != 0) && (((ConstraintWidget)localObject2).getRight() > paramInt2)) {
                  paramInt2 = Math.max(paramInt2, ((ConstraintWidget)localObject2).getRight() + ((ConstraintWidget)localObject2).getAnchor(ConstraintAnchor.Type.RIGHT).getMargin());
                }
                paramInt4 = 1;
              }
              if (paramInt5 != i)
              {
                ((ConstraintWidget)localObject2).setHeight(paramInt5);
                if ((paramInt7 != 0) && (((ConstraintWidget)localObject2).getBottom() > paramInt1)) {
                  paramInt1 = Math.max(paramInt1, ((ConstraintWidget)localObject2).getBottom() + ((ConstraintWidget)localObject2).getAnchor(ConstraintAnchor.Type.BOTTOM).getMargin());
                }
                paramInt5 = 1;
              }
              else
              {
                paramInt5 = paramInt4;
              }
              paramInt4 = paramInt5;
              i = paramInt2;
              j = paramInt1;
              if (((ConstraintWidget)localObject2).hasBaseline())
              {
                paramInt4 = paramInt5;
                i = paramInt2;
                j = paramInt1;
                if (i2 != ((ConstraintWidget)localObject2).getBaselineDistance())
                {
                  paramInt4 = 1;
                  j = paramInt1;
                  i = paramInt2;
                }
              }
            }
            paramInt9++;
            paramInt5 = paramInt4;
            paramInt2 = i;
            paramInt1 = j;
          }
          if (paramInt5 != 0)
          {
            solveLinearSystem(paramConstraintWidgetContainer, "intermediate pass", paramInt8 + 1, m, k);
            paramInt5 = 0;
            paramInt8++;
            paramInt4 = paramInt3;
            paramInt3 = paramInt5;
          }
          else
          {
            break;
          }
        }
      }
      else
      {
        l = 0L;
      }
      paramConstraintWidgetContainer.setOptimizationLevel(i1);
    }
    return l;
  }
  
  public void updateHierarchy(ConstraintWidgetContainer paramConstraintWidgetContainer)
  {
    this.mVariableDimensionsWidgets.clear();
    int j = paramConstraintWidgetContainer.mChildren.size();
    for (int i = 0; i < j; i++)
    {
      ConstraintWidget localConstraintWidget = (ConstraintWidget)paramConstraintWidgetContainer.mChildren.get(i);
      if ((localConstraintWidget.getHorizontalDimensionBehaviour() == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) || (localConstraintWidget.getVerticalDimensionBehaviour() == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT)) {
        this.mVariableDimensionsWidgets.add(localConstraintWidget);
      }
    }
    paramConstraintWidgetContainer.invalidateGraph();
  }
  
  public static class Measure
  {
    public static int SELF_DIMENSIONS = 0;
    public static int TRY_GIVEN_DIMENSIONS = 1;
    public static int USE_GIVEN_DIMENSIONS = 2;
    public ConstraintWidget.DimensionBehaviour horizontalBehavior;
    public int horizontalDimension;
    public int measureStrategy;
    public int measuredBaseline;
    public boolean measuredHasBaseline;
    public int measuredHeight;
    public boolean measuredNeedsSolverPass;
    public int measuredWidth;
    public ConstraintWidget.DimensionBehaviour verticalBehavior;
    public int verticalDimension;
  }
  
  public static abstract interface Measurer
  {
    public abstract void didMeasures();
    
    public abstract void measure(ConstraintWidget paramConstraintWidget, BasicMeasure.Measure paramMeasure);
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/constraintlayout/core/widgets/analyzer/BasicMeasure.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */