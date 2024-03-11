package androidx.constraintlayout.core.widgets.analyzer;

import androidx.constraintlayout.core.widgets.ConstraintAnchor;
import androidx.constraintlayout.core.widgets.ConstraintWidget;
import androidx.constraintlayout.core.widgets.ConstraintWidget.DimensionBehaviour;
import java.util.List;

public abstract class WidgetRun
  implements Dependency
{
  DimensionDependency dimension = new DimensionDependency(this);
  protected ConstraintWidget.DimensionBehaviour dimensionBehavior;
  public DependencyNode end = new DependencyNode(this);
  protected RunType mRunType = RunType.NONE;
  public int matchConstraintsType;
  public int orientation = 0;
  boolean resolved = false;
  RunGroup runGroup;
  public DependencyNode start = new DependencyNode(this);
  ConstraintWidget widget;
  
  public WidgetRun(ConstraintWidget paramConstraintWidget)
  {
    this.widget = paramConstraintWidget;
  }
  
  private void resolveDimension(int paramInt1, int paramInt2)
  {
    Object localObject;
    float f;
    switch (this.matchConstraintsType)
    {
    default: 
      break;
    case 3: 
      if ((this.widget.horizontalRun.dimensionBehavior != ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) || (this.widget.horizontalRun.matchConstraintsType != 3) || (this.widget.verticalRun.dimensionBehavior != ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) || (this.widget.verticalRun.matchConstraintsType != 3))
      {
        localObject = this.widget;
        if (paramInt1 == 0) {
          localObject = ((ConstraintWidget)localObject).verticalRun;
        } else {
          localObject = ((ConstraintWidget)localObject).horizontalRun;
        }
        if (((WidgetRun)localObject).dimension.resolved)
        {
          f = this.widget.getDimensionRatio();
          if (paramInt1 == 1) {
            paramInt1 = (int)(((WidgetRun)localObject).dimension.value / f + 0.5F);
          } else {
            paramInt1 = (int)(((WidgetRun)localObject).dimension.value * f + 0.5F);
          }
          this.dimension.resolve(paramInt1);
        }
      }
      break;
    case 2: 
      localObject = this.widget.getParent();
      if (localObject != null)
      {
        if (paramInt1 == 0) {
          localObject = ((ConstraintWidget)localObject).horizontalRun;
        } else {
          localObject = ((ConstraintWidget)localObject).verticalRun;
        }
        if (((WidgetRun)localObject).dimension.resolved)
        {
          ConstraintWidget localConstraintWidget = this.widget;
          if (paramInt1 == 0) {
            f = localConstraintWidget.mMatchConstraintPercentWidth;
          } else {
            f = localConstraintWidget.mMatchConstraintPercentHeight;
          }
          paramInt2 = (int)(((WidgetRun)localObject).dimension.value * f + 0.5F);
          this.dimension.resolve(getLimitedDimension(paramInt2, paramInt1));
        }
      }
      break;
    case 1: 
      paramInt1 = getLimitedDimension(this.dimension.wrapValue, paramInt1);
      this.dimension.resolve(Math.min(paramInt1, paramInt2));
      break;
    case 0: 
      this.dimension.resolve(getLimitedDimension(paramInt2, paramInt1));
    }
  }
  
  protected final void addTarget(DependencyNode paramDependencyNode1, DependencyNode paramDependencyNode2, int paramInt)
  {
    paramDependencyNode1.targets.add(paramDependencyNode2);
    paramDependencyNode1.margin = paramInt;
    paramDependencyNode2.dependencies.add(paramDependencyNode1);
  }
  
  protected final void addTarget(DependencyNode paramDependencyNode1, DependencyNode paramDependencyNode2, int paramInt, DimensionDependency paramDimensionDependency)
  {
    paramDependencyNode1.targets.add(paramDependencyNode2);
    paramDependencyNode1.targets.add(this.dimension);
    paramDependencyNode1.marginFactor = paramInt;
    paramDependencyNode1.marginDependency = paramDimensionDependency;
    paramDependencyNode2.dependencies.add(paramDependencyNode1);
    paramDimensionDependency.dependencies.add(paramDependencyNode1);
  }
  
  abstract void apply();
  
  abstract void applyToWidget();
  
  abstract void clear();
  
  protected final int getLimitedDimension(int paramInt1, int paramInt2)
  {
    int i;
    if (paramInt2 == 0)
    {
      i = this.widget.mMatchConstraintMaxWidth;
      paramInt2 = Math.max(this.widget.mMatchConstraintMinWidth, paramInt1);
      if (i > 0) {
        paramInt2 = Math.min(i, paramInt1);
      }
      i = paramInt1;
      if (paramInt2 != paramInt1) {
        i = paramInt2;
      }
    }
    else
    {
      i = this.widget.mMatchConstraintMaxHeight;
      paramInt2 = Math.max(this.widget.mMatchConstraintMinHeight, paramInt1);
      if (i > 0) {
        paramInt2 = Math.min(i, paramInt1);
      }
      i = paramInt1;
      if (paramInt2 != paramInt1) {
        i = paramInt2;
      }
    }
    return i;
  }
  
  protected final DependencyNode getTarget(ConstraintAnchor paramConstraintAnchor)
  {
    if (paramConstraintAnchor.mTarget == null) {
      return null;
    }
    Object localObject = null;
    ConstraintWidget localConstraintWidget = paramConstraintAnchor.mTarget.mOwner;
    paramConstraintAnchor = paramConstraintAnchor.mTarget.mType;
    switch (1.$SwitchMap$androidx$constraintlayout$core$widgets$ConstraintAnchor$Type[paramConstraintAnchor.ordinal()])
    {
    default: 
      paramConstraintAnchor = (ConstraintAnchor)localObject;
      break;
    case 5: 
      paramConstraintAnchor = localConstraintWidget.verticalRun.end;
      break;
    case 4: 
      paramConstraintAnchor = localConstraintWidget.verticalRun.baseline;
      break;
    case 3: 
      paramConstraintAnchor = localConstraintWidget.verticalRun.start;
      break;
    case 2: 
      paramConstraintAnchor = localConstraintWidget.horizontalRun.end;
      break;
    case 1: 
      paramConstraintAnchor = localConstraintWidget.horizontalRun.start;
    }
    return paramConstraintAnchor;
  }
  
  protected final DependencyNode getTarget(ConstraintAnchor paramConstraintAnchor, int paramInt)
  {
    if (paramConstraintAnchor.mTarget == null) {
      return null;
    }
    Object localObject2 = null;
    Object localObject1 = paramConstraintAnchor.mTarget.mOwner;
    if (paramInt == 0) {
      localObject1 = ((ConstraintWidget)localObject1).horizontalRun;
    } else {
      localObject1 = ((ConstraintWidget)localObject1).verticalRun;
    }
    paramConstraintAnchor = paramConstraintAnchor.mTarget.mType;
    switch (1.$SwitchMap$androidx$constraintlayout$core$widgets$ConstraintAnchor$Type[paramConstraintAnchor.ordinal()])
    {
    case 4: 
    default: 
      paramConstraintAnchor = (ConstraintAnchor)localObject2;
      break;
    case 2: 
    case 5: 
      paramConstraintAnchor = ((WidgetRun)localObject1).end;
      break;
    case 1: 
    case 3: 
      paramConstraintAnchor = ((WidgetRun)localObject1).start;
    }
    return paramConstraintAnchor;
  }
  
  public long getWrapDimension()
  {
    if (this.dimension.resolved) {
      return this.dimension.value;
    }
    return 0L;
  }
  
  public boolean isCenterConnection()
  {
    int i = 0;
    int m = this.start.targets.size();
    int j = 0;
    while (j < m)
    {
      k = i;
      if (((DependencyNode)this.start.targets.get(j)).run != this) {
        k = i + 1;
      }
      j++;
      i = k;
    }
    m = this.end.targets.size();
    j = 0;
    for (int k = i; j < m; k = i)
    {
      i = k;
      if (((DependencyNode)this.end.targets.get(j)).run != this) {
        i = k + 1;
      }
      j++;
    }
    boolean bool;
    if (k >= 2) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public boolean isDimensionResolved()
  {
    return this.dimension.resolved;
  }
  
  public boolean isResolved()
  {
    return this.resolved;
  }
  
  abstract void reset();
  
  abstract boolean supportsWrapComputation();
  
  public void update(Dependency paramDependency) {}
  
  protected void updateRunCenter(Dependency paramDependency, ConstraintAnchor paramConstraintAnchor1, ConstraintAnchor paramConstraintAnchor2, int paramInt)
  {
    paramDependency = getTarget(paramConstraintAnchor1);
    DependencyNode localDependencyNode = getTarget(paramConstraintAnchor2);
    if ((paramDependency.resolved) && (localDependencyNode.resolved))
    {
      int i = paramDependency.value + paramConstraintAnchor1.getMargin();
      int j = localDependencyNode.value - paramConstraintAnchor2.getMargin();
      int k = j - i;
      if ((!this.dimension.resolved) && (this.dimensionBehavior == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT)) {
        resolveDimension(paramInt, k);
      }
      if (!this.dimension.resolved) {
        return;
      }
      if (this.dimension.value == k)
      {
        this.start.resolve(i);
        this.end.resolve(j);
        return;
      }
      paramConstraintAnchor1 = this.widget;
      float f;
      if (paramInt == 0) {
        f = paramConstraintAnchor1.getHorizontalBiasPercent();
      } else {
        f = paramConstraintAnchor1.getVerticalBiasPercent();
      }
      paramInt = j;
      if (paramDependency == localDependencyNode)
      {
        i = paramDependency.value;
        paramInt = localDependencyNode.value;
        f = 0.5F;
      }
      j = this.dimension.value;
      this.start.resolve((int)(i + 0.5F + (paramInt - i - j) * f));
      this.end.resolve(this.start.value + this.dimension.value);
      return;
    }
  }
  
  protected void updateRunEnd(Dependency paramDependency) {}
  
  protected void updateRunStart(Dependency paramDependency) {}
  
  public long wrapSize(int paramInt)
  {
    if (this.dimension.resolved)
    {
      long l = this.dimension.value;
      if (isCenterConnection()) {
        l += this.start.margin - this.end.margin;
      } else if (paramInt == 0) {
        l += this.start.margin;
      } else {
        l -= this.end.margin;
      }
      return l;
    }
    return 0L;
  }
  
  static enum RunType
  {
    private static final RunType[] $VALUES;
    
    static
    {
      RunType localRunType2 = new RunType("NONE", 0);
      NONE = localRunType2;
      RunType localRunType4 = new RunType("START", 1);
      START = localRunType4;
      RunType localRunType1 = new RunType("END", 2);
      END = localRunType1;
      RunType localRunType3 = new RunType("CENTER", 3);
      CENTER = localRunType3;
      $VALUES = new RunType[] { localRunType2, localRunType4, localRunType1, localRunType3 };
    }
    
    private RunType() {}
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/constraintlayout/core/widgets/analyzer/WidgetRun.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */