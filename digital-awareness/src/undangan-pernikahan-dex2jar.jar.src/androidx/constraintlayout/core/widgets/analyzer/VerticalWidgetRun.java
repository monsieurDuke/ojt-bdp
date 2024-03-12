package androidx.constraintlayout.core.widgets.analyzer;

import androidx.constraintlayout.core.widgets.ConstraintAnchor;
import androidx.constraintlayout.core.widgets.ConstraintAnchor.Type;
import androidx.constraintlayout.core.widgets.ConstraintWidget;
import androidx.constraintlayout.core.widgets.ConstraintWidget.DimensionBehaviour;
import androidx.constraintlayout.core.widgets.Helper;
import java.util.List;

public class VerticalWidgetRun
  extends WidgetRun
{
  public DependencyNode baseline = new DependencyNode(this);
  DimensionDependency baselineDimension = null;
  
  public VerticalWidgetRun(ConstraintWidget paramConstraintWidget)
  {
    super(paramConstraintWidget);
    this.start.type = DependencyNode.Type.TOP;
    this.end.type = DependencyNode.Type.BOTTOM;
    this.baseline.type = DependencyNode.Type.BASELINE;
    this.orientation = 1;
  }
  
  void apply()
  {
    if (this.widget.measured) {
      this.dimension.resolve(this.widget.getHeight());
    }
    Object localObject;
    if (!this.dimension.resolved)
    {
      this.dimensionBehavior = this.widget.getVerticalDimensionBehaviour();
      if (this.widget.hasBaseline()) {
        this.baselineDimension = new BaselineDimensionDependency(this);
      }
      if (this.dimensionBehavior != ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT)
      {
        if (this.dimensionBehavior == ConstraintWidget.DimensionBehaviour.MATCH_PARENT)
        {
          localObject = this.widget.getParent();
          if ((localObject != null) && (((ConstraintWidget)localObject).getVerticalDimensionBehaviour() == ConstraintWidget.DimensionBehaviour.FIXED))
          {
            int j = ((ConstraintWidget)localObject).getHeight();
            int i = this.widget.mTop.getMargin();
            int k = this.widget.mBottom.getMargin();
            addTarget(this.start, ((ConstraintWidget)localObject).verticalRun.start, this.widget.mTop.getMargin());
            addTarget(this.end, ((ConstraintWidget)localObject).verticalRun.end, -this.widget.mBottom.getMargin());
            this.dimension.resolve(j - i - k);
            return;
          }
        }
        if (this.dimensionBehavior == ConstraintWidget.DimensionBehaviour.FIXED) {
          this.dimension.resolve(this.widget.getHeight());
        }
      }
    }
    else if (this.dimensionBehavior == ConstraintWidget.DimensionBehaviour.MATCH_PARENT)
    {
      localObject = this.widget.getParent();
      if ((localObject != null) && (((ConstraintWidget)localObject).getVerticalDimensionBehaviour() == ConstraintWidget.DimensionBehaviour.FIXED))
      {
        addTarget(this.start, ((ConstraintWidget)localObject).verticalRun.start, this.widget.mTop.getMargin());
        addTarget(this.end, ((ConstraintWidget)localObject).verticalRun.end, -this.widget.mBottom.getMargin());
        return;
      }
    }
    if ((this.dimension.resolved) && (this.widget.measured))
    {
      if ((this.widget.mListAnchors[2].mTarget != null) && (this.widget.mListAnchors[3].mTarget != null))
      {
        if (this.widget.isInVerticalChain())
        {
          this.start.margin = this.widget.mListAnchors[2].getMargin();
          this.end.margin = (-this.widget.mListAnchors[3].getMargin());
        }
        else
        {
          localObject = getTarget(this.widget.mListAnchors[2]);
          if (localObject != null) {
            addTarget(this.start, (DependencyNode)localObject, this.widget.mListAnchors[2].getMargin());
          }
          localObject = getTarget(this.widget.mListAnchors[3]);
          if (localObject != null) {
            addTarget(this.end, (DependencyNode)localObject, -this.widget.mListAnchors[3].getMargin());
          }
          this.start.delegateToWidgetRun = true;
          this.end.delegateToWidgetRun = true;
        }
        if (this.widget.hasBaseline()) {
          addTarget(this.baseline, this.start, this.widget.getBaselineDistance());
        }
      }
      else if (this.widget.mListAnchors[2].mTarget != null)
      {
        localObject = getTarget(this.widget.mListAnchors[2]);
        if (localObject != null)
        {
          addTarget(this.start, (DependencyNode)localObject, this.widget.mListAnchors[2].getMargin());
          addTarget(this.end, this.start, this.dimension.value);
          if (this.widget.hasBaseline()) {
            addTarget(this.baseline, this.start, this.widget.getBaselineDistance());
          }
        }
      }
      else if (this.widget.mListAnchors[3].mTarget != null)
      {
        localObject = getTarget(this.widget.mListAnchors[3]);
        if (localObject != null)
        {
          addTarget(this.end, (DependencyNode)localObject, -this.widget.mListAnchors[3].getMargin());
          addTarget(this.start, this.end, -this.dimension.value);
        }
        if (this.widget.hasBaseline()) {
          addTarget(this.baseline, this.start, this.widget.getBaselineDistance());
        }
      }
      else if (this.widget.mListAnchors[4].mTarget != null)
      {
        localObject = getTarget(this.widget.mListAnchors[4]);
        if (localObject != null)
        {
          addTarget(this.baseline, (DependencyNode)localObject, 0);
          addTarget(this.start, this.baseline, -this.widget.getBaselineDistance());
          addTarget(this.end, this.start, this.dimension.value);
        }
      }
      else if ((!(this.widget instanceof Helper)) && (this.widget.getParent() != null) && (this.widget.getAnchor(ConstraintAnchor.Type.CENTER).mTarget == null))
      {
        localObject = this.widget.getParent().verticalRun.start;
        addTarget(this.start, (DependencyNode)localObject, this.widget.getY());
        addTarget(this.end, this.start, this.dimension.value);
        if (this.widget.hasBaseline()) {
          addTarget(this.baseline, this.start, this.widget.getBaselineDistance());
        }
      }
    }
    else
    {
      if ((!this.dimension.resolved) && (this.dimensionBehavior == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT)) {}
      switch (this.widget.mMatchConstraintDefaultHeight)
      {
      case 1: 
      default: 
        break;
      case 3: 
        if ((!this.widget.isInVerticalChain()) && (this.widget.mMatchConstraintDefaultWidth != 3))
        {
          localObject = this.widget.horizontalRun.dimension;
          this.dimension.targets.add(localObject);
          ((DependencyNode)localObject).dependencies.add(this.dimension);
          this.dimension.delegateToWidgetRun = true;
          this.dimension.dependencies.add(this.start);
          this.dimension.dependencies.add(this.end);
        }
        break;
      case 2: 
        localObject = this.widget.getParent();
        if (localObject != null)
        {
          localObject = ((ConstraintWidget)localObject).verticalRun.dimension;
          this.dimension.targets.add(localObject);
          ((DependencyNode)localObject).dependencies.add(this.dimension);
          this.dimension.delegateToWidgetRun = true;
          this.dimension.dependencies.add(this.start);
          this.dimension.dependencies.add(this.end);
        }
        break;
      case 0: 
        break;
        this.dimension.addDependency(this);
      }
      if ((this.widget.mListAnchors[2].mTarget != null) && (this.widget.mListAnchors[3].mTarget != null))
      {
        if (this.widget.isInVerticalChain())
        {
          this.start.margin = this.widget.mListAnchors[2].getMargin();
          this.end.margin = (-this.widget.mListAnchors[3].getMargin());
        }
        else
        {
          DependencyNode localDependencyNode = getTarget(this.widget.mListAnchors[2]);
          localObject = getTarget(this.widget.mListAnchors[3]);
          if (localDependencyNode != null) {
            localDependencyNode.addDependency(this);
          }
          if (localObject != null) {
            ((DependencyNode)localObject).addDependency(this);
          }
          this.mRunType = WidgetRun.RunType.CENTER;
        }
        if (this.widget.hasBaseline()) {
          addTarget(this.baseline, this.start, 1, this.baselineDimension);
        }
      }
      else if (this.widget.mListAnchors[2].mTarget != null)
      {
        localObject = getTarget(this.widget.mListAnchors[2]);
        if (localObject != null)
        {
          addTarget(this.start, (DependencyNode)localObject, this.widget.mListAnchors[2].getMargin());
          addTarget(this.end, this.start, 1, this.dimension);
          if (this.widget.hasBaseline()) {
            addTarget(this.baseline, this.start, 1, this.baselineDimension);
          }
          if ((this.dimensionBehavior == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) && (this.widget.getDimensionRatio() > 0.0F) && (this.widget.horizontalRun.dimensionBehavior == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT))
          {
            this.widget.horizontalRun.dimension.dependencies.add(this.dimension);
            this.dimension.targets.add(this.widget.horizontalRun.dimension);
            this.dimension.updateDelegate = this;
          }
        }
      }
      else if (this.widget.mListAnchors[3].mTarget != null)
      {
        localObject = getTarget(this.widget.mListAnchors[3]);
        if (localObject != null)
        {
          addTarget(this.end, (DependencyNode)localObject, -this.widget.mListAnchors[3].getMargin());
          addTarget(this.start, this.end, -1, this.dimension);
          if (this.widget.hasBaseline()) {
            addTarget(this.baseline, this.start, 1, this.baselineDimension);
          }
        }
      }
      else if (this.widget.mListAnchors[4].mTarget != null)
      {
        localObject = getTarget(this.widget.mListAnchors[4]);
        if (localObject != null)
        {
          addTarget(this.baseline, (DependencyNode)localObject, 0);
          addTarget(this.start, this.baseline, -1, this.baselineDimension);
          addTarget(this.end, this.start, 1, this.dimension);
        }
      }
      else if ((!(this.widget instanceof Helper)) && (this.widget.getParent() != null))
      {
        localObject = this.widget.getParent().verticalRun.start;
        addTarget(this.start, (DependencyNode)localObject, this.widget.getY());
        addTarget(this.end, this.start, 1, this.dimension);
        if (this.widget.hasBaseline()) {
          addTarget(this.baseline, this.start, 1, this.baselineDimension);
        }
        if ((this.dimensionBehavior == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) && (this.widget.getDimensionRatio() > 0.0F) && (this.widget.horizontalRun.dimensionBehavior == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT))
        {
          this.widget.horizontalRun.dimension.dependencies.add(this.dimension);
          this.dimension.targets.add(this.widget.horizontalRun.dimension);
          this.dimension.updateDelegate = this;
        }
      }
      if (this.dimension.targets.size() == 0) {
        this.dimension.readyToSolve = true;
      }
    }
  }
  
  public void applyToWidget()
  {
    if (this.start.resolved) {
      this.widget.setY(this.start.value);
    }
  }
  
  void clear()
  {
    this.runGroup = null;
    this.start.clear();
    this.end.clear();
    this.baseline.clear();
    this.dimension.clear();
    this.resolved = false;
  }
  
  void reset()
  {
    this.resolved = false;
    this.start.clear();
    this.start.resolved = false;
    this.end.clear();
    this.end.resolved = false;
    this.baseline.clear();
    this.baseline.resolved = false;
    this.dimension.resolved = false;
  }
  
  boolean supportsWrapComputation()
  {
    if (this.dimensionBehavior == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
      return this.widget.mMatchConstraintDefaultHeight == 0;
    }
    return true;
  }
  
  public String toString()
  {
    return "VerticalRun " + this.widget.getDebugName();
  }
  
  public void update(Dependency paramDependency)
  {
    switch (1.$SwitchMap$androidx$constraintlayout$core$widgets$analyzer$WidgetRun$RunType[this.mRunType.ordinal()])
    {
    default: 
      break;
    case 3: 
      updateRunCenter(paramDependency, this.widget.mTop, this.widget.mBottom, 1);
      return;
    case 2: 
      updateRunEnd(paramDependency);
      break;
    case 1: 
      updateRunStart(paramDependency);
    }
    int i;
    float f;
    if ((this.dimension.readyToSolve) && (!this.dimension.resolved) && (this.dimensionBehavior == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT)) {
      switch (this.widget.mMatchConstraintDefaultHeight)
      {
      default: 
        break;
      case 3: 
        if (this.widget.horizontalRun.dimension.resolved)
        {
          i = 0;
          switch (this.widget.getDimensionRatioSide())
          {
          default: 
            break;
          case 1: 
            i = (int)(this.widget.horizontalRun.dimension.value / this.widget.getDimensionRatio() + 0.5F);
            break;
          case 0: 
            i = (int)(this.widget.horizontalRun.dimension.value * this.widget.getDimensionRatio() + 0.5F);
            break;
          case -1: 
            i = (int)(this.widget.horizontalRun.dimension.value / this.widget.getDimensionRatio() + 0.5F);
          }
          this.dimension.resolve(i);
        }
        break;
      case 2: 
        paramDependency = this.widget.getParent();
        if ((paramDependency != null) && (paramDependency.verticalRun.dimension.resolved))
        {
          f = this.widget.mMatchConstraintPercentHeight;
          i = (int)(paramDependency.verticalRun.dimension.value * f + 0.5F);
          this.dimension.resolve(i);
        }
        break;
      }
    }
    if ((this.start.readyToSolve) && (this.end.readyToSolve))
    {
      if ((this.start.resolved) && (this.end.resolved) && (this.dimension.resolved)) {
        return;
      }
      DependencyNode localDependencyNode;
      int j;
      if ((!this.dimension.resolved) && (this.dimensionBehavior == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) && (this.widget.mMatchConstraintDefaultWidth == 0) && (!this.widget.isInVerticalChain()))
      {
        localDependencyNode = (DependencyNode)this.start.targets.get(0);
        paramDependency = (DependencyNode)this.end.targets.get(0);
        j = localDependencyNode.value + this.start.margin;
        i = paramDependency.value + this.end.margin;
        this.start.resolve(j);
        this.end.resolve(i);
        this.dimension.resolve(i - j);
        return;
      }
      if ((!this.dimension.resolved) && (this.dimensionBehavior == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) && (this.matchConstraintsType == 1) && (this.start.targets.size() > 0) && (this.end.targets.size() > 0))
      {
        localDependencyNode = (DependencyNode)this.start.targets.get(0);
        paramDependency = (DependencyNode)this.end.targets.get(0);
        i = localDependencyNode.value;
        j = this.start.margin;
        i = paramDependency.value + this.end.margin - (i + j);
        if (i < this.dimension.wrapValue) {
          this.dimension.resolve(i);
        } else {
          this.dimension.resolve(this.dimension.wrapValue);
        }
      }
      if (!this.dimension.resolved) {
        return;
      }
      if ((this.start.targets.size() > 0) && (this.end.targets.size() > 0))
      {
        paramDependency = (DependencyNode)this.start.targets.get(0);
        localDependencyNode = (DependencyNode)this.end.targets.get(0);
        j = paramDependency.value + this.start.margin;
        i = localDependencyNode.value + this.end.margin;
        f = this.widget.getVerticalBiasPercent();
        if (paramDependency == localDependencyNode)
        {
          j = paramDependency.value;
          i = localDependencyNode.value;
          f = 0.5F;
        }
        int k = this.dimension.value;
        this.start.resolve((int)(j + 0.5F + (i - j - k) * f));
        this.end.resolve(this.start.value + this.dimension.value);
      }
      return;
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/constraintlayout/core/widgets/analyzer/VerticalWidgetRun.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */