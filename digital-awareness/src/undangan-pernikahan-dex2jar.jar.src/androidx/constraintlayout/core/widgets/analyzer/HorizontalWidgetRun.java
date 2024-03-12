package androidx.constraintlayout.core.widgets.analyzer;

import androidx.constraintlayout.core.widgets.ConstraintAnchor;
import androidx.constraintlayout.core.widgets.ConstraintAnchor.Type;
import androidx.constraintlayout.core.widgets.ConstraintWidget;
import androidx.constraintlayout.core.widgets.ConstraintWidget.DimensionBehaviour;
import androidx.constraintlayout.core.widgets.Helper;
import java.util.List;

public class HorizontalWidgetRun
  extends WidgetRun
{
  private static int[] tempDimensions = new int[2];
  
  public HorizontalWidgetRun(ConstraintWidget paramConstraintWidget)
  {
    super(paramConstraintWidget);
    this.start.type = DependencyNode.Type.LEFT;
    this.end.type = DependencyNode.Type.RIGHT;
    this.orientation = 0;
  }
  
  private void computeInsetRatio(int[] paramArrayOfInt, int paramInt1, int paramInt2, int paramInt3, int paramInt4, float paramFloat, int paramInt5)
  {
    paramInt1 = paramInt2 - paramInt1;
    paramInt2 = paramInt4 - paramInt3;
    switch (paramInt5)
    {
    default: 
      break;
    case 1: 
      paramInt2 = (int)(paramInt1 * paramFloat + 0.5F);
      paramArrayOfInt[0] = paramInt1;
      paramArrayOfInt[1] = paramInt2;
      break;
    case 0: 
      paramArrayOfInt[0] = ((int)(paramInt2 * paramFloat + 0.5F));
      paramArrayOfInt[1] = paramInt2;
      break;
    case -1: 
      paramInt3 = (int)(paramInt2 * paramFloat + 0.5F);
      paramInt4 = (int)(paramInt1 / paramFloat + 0.5F);
      if ((paramInt3 <= paramInt1) && (paramInt2 <= paramInt2))
      {
        paramArrayOfInt[0] = paramInt3;
        paramArrayOfInt[1] = paramInt2;
      }
      else if ((paramInt1 <= paramInt1) && (paramInt4 <= paramInt2))
      {
        paramArrayOfInt[0] = paramInt1;
        paramArrayOfInt[1] = paramInt4;
      }
      break;
    }
  }
  
  void apply()
  {
    if (this.widget.measured) {
      this.dimension.resolve(this.widget.getWidth());
    }
    Object localObject;
    if (!this.dimension.resolved)
    {
      this.dimensionBehavior = this.widget.getHorizontalDimensionBehaviour();
      if (this.dimensionBehavior != ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT)
      {
        if (this.dimensionBehavior == ConstraintWidget.DimensionBehaviour.MATCH_PARENT)
        {
          localObject = this.widget.getParent();
          if ((localObject != null) && ((((ConstraintWidget)localObject).getHorizontalDimensionBehaviour() == ConstraintWidget.DimensionBehaviour.FIXED) || (((ConstraintWidget)localObject).getHorizontalDimensionBehaviour() == ConstraintWidget.DimensionBehaviour.MATCH_PARENT)))
          {
            int i = ((ConstraintWidget)localObject).getWidth();
            int k = this.widget.mLeft.getMargin();
            int j = this.widget.mRight.getMargin();
            addTarget(this.start, ((ConstraintWidget)localObject).horizontalRun.start, this.widget.mLeft.getMargin());
            addTarget(this.end, ((ConstraintWidget)localObject).horizontalRun.end, -this.widget.mRight.getMargin());
            this.dimension.resolve(i - k - j);
            return;
          }
        }
        if (this.dimensionBehavior == ConstraintWidget.DimensionBehaviour.FIXED) {
          this.dimension.resolve(this.widget.getWidth());
        }
      }
    }
    else if (this.dimensionBehavior == ConstraintWidget.DimensionBehaviour.MATCH_PARENT)
    {
      localObject = this.widget.getParent();
      if ((localObject != null) && ((((ConstraintWidget)localObject).getHorizontalDimensionBehaviour() == ConstraintWidget.DimensionBehaviour.FIXED) || (((ConstraintWidget)localObject).getHorizontalDimensionBehaviour() == ConstraintWidget.DimensionBehaviour.MATCH_PARENT)))
      {
        addTarget(this.start, ((ConstraintWidget)localObject).horizontalRun.start, this.widget.mLeft.getMargin());
        addTarget(this.end, ((ConstraintWidget)localObject).horizontalRun.end, -this.widget.mRight.getMargin());
        return;
      }
    }
    if ((this.dimension.resolved) && (this.widget.measured))
    {
      if ((this.widget.mListAnchors[0].mTarget != null) && (this.widget.mListAnchors[1].mTarget != null))
      {
        if (this.widget.isInHorizontalChain())
        {
          this.start.margin = this.widget.mListAnchors[0].getMargin();
          this.end.margin = (-this.widget.mListAnchors[1].getMargin());
        }
        else
        {
          localObject = getTarget(this.widget.mListAnchors[0]);
          if (localObject != null) {
            addTarget(this.start, (DependencyNode)localObject, this.widget.mListAnchors[0].getMargin());
          }
          localObject = getTarget(this.widget.mListAnchors[1]);
          if (localObject != null) {
            addTarget(this.end, (DependencyNode)localObject, -this.widget.mListAnchors[1].getMargin());
          }
          this.start.delegateToWidgetRun = true;
          this.end.delegateToWidgetRun = true;
        }
      }
      else if (this.widget.mListAnchors[0].mTarget != null)
      {
        localObject = getTarget(this.widget.mListAnchors[0]);
        if (localObject != null)
        {
          addTarget(this.start, (DependencyNode)localObject, this.widget.mListAnchors[0].getMargin());
          addTarget(this.end, this.start, this.dimension.value);
        }
      }
      else if (this.widget.mListAnchors[1].mTarget != null)
      {
        localObject = getTarget(this.widget.mListAnchors[1]);
        if (localObject != null)
        {
          addTarget(this.end, (DependencyNode)localObject, -this.widget.mListAnchors[1].getMargin());
          addTarget(this.start, this.end, -this.dimension.value);
        }
      }
      else if ((!(this.widget instanceof Helper)) && (this.widget.getParent() != null) && (this.widget.getAnchor(ConstraintAnchor.Type.CENTER).mTarget == null))
      {
        localObject = this.widget.getParent().horizontalRun.start;
        addTarget(this.start, (DependencyNode)localObject, this.widget.getX());
        addTarget(this.end, this.start, this.dimension.value);
      }
    }
    else
    {
      if (this.dimensionBehavior == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
        switch (this.widget.mMatchConstraintDefaultWidth)
        {
        case 1: 
        default: 
          break;
        case 3: 
          if (this.widget.mMatchConstraintDefaultHeight == 3)
          {
            this.start.updateDelegate = this;
            this.end.updateDelegate = this;
            this.widget.verticalRun.start.updateDelegate = this;
            this.widget.verticalRun.end.updateDelegate = this;
            this.dimension.updateDelegate = this;
            if (this.widget.isInVerticalChain())
            {
              this.dimension.targets.add(this.widget.verticalRun.dimension);
              this.widget.verticalRun.dimension.dependencies.add(this.dimension);
              this.widget.verticalRun.dimension.updateDelegate = this;
              this.dimension.targets.add(this.widget.verticalRun.start);
              this.dimension.targets.add(this.widget.verticalRun.end);
              this.widget.verticalRun.start.dependencies.add(this.dimension);
              this.widget.verticalRun.end.dependencies.add(this.dimension);
            }
            else if (this.widget.isInHorizontalChain())
            {
              this.widget.verticalRun.dimension.targets.add(this.dimension);
              this.dimension.dependencies.add(this.widget.verticalRun.dimension);
            }
            else
            {
              this.widget.verticalRun.dimension.targets.add(this.dimension);
            }
          }
          else
          {
            localObject = this.widget.verticalRun.dimension;
            this.dimension.targets.add(localObject);
            ((DependencyNode)localObject).dependencies.add(this.dimension);
            this.widget.verticalRun.start.dependencies.add(this.dimension);
            this.widget.verticalRun.end.dependencies.add(this.dimension);
            this.dimension.delegateToWidgetRun = true;
            this.dimension.dependencies.add(this.start);
            this.dimension.dependencies.add(this.end);
            this.start.targets.add(this.dimension);
            this.end.targets.add(this.dimension);
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
        }
      }
      if ((this.widget.mListAnchors[0].mTarget != null) && (this.widget.mListAnchors[1].mTarget != null))
      {
        if (this.widget.isInHorizontalChain())
        {
          this.start.margin = this.widget.mListAnchors[0].getMargin();
          this.end.margin = (-this.widget.mListAnchors[1].getMargin());
        }
        else
        {
          localObject = getTarget(this.widget.mListAnchors[0]);
          DependencyNode localDependencyNode = getTarget(this.widget.mListAnchors[1]);
          if (localObject != null) {
            ((DependencyNode)localObject).addDependency(this);
          }
          if (localDependencyNode != null) {
            localDependencyNode.addDependency(this);
          }
          this.mRunType = WidgetRun.RunType.CENTER;
        }
      }
      else if (this.widget.mListAnchors[0].mTarget != null)
      {
        localObject = getTarget(this.widget.mListAnchors[0]);
        if (localObject != null)
        {
          addTarget(this.start, (DependencyNode)localObject, this.widget.mListAnchors[0].getMargin());
          addTarget(this.end, this.start, 1, this.dimension);
        }
      }
      else if (this.widget.mListAnchors[1].mTarget != null)
      {
        localObject = getTarget(this.widget.mListAnchors[1]);
        if (localObject != null)
        {
          addTarget(this.end, (DependencyNode)localObject, -this.widget.mListAnchors[1].getMargin());
          addTarget(this.start, this.end, -1, this.dimension);
        }
      }
      else if ((!(this.widget instanceof Helper)) && (this.widget.getParent() != null))
      {
        localObject = this.widget.getParent().horizontalRun.start;
        addTarget(this.start, (DependencyNode)localObject, this.widget.getX());
        addTarget(this.end, this.start, 1, this.dimension);
      }
    }
  }
  
  public void applyToWidget()
  {
    if (this.start.resolved) {
      this.widget.setX(this.start.value);
    }
  }
  
  void clear()
  {
    this.runGroup = null;
    this.start.clear();
    this.end.clear();
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
    this.dimension.resolved = false;
  }
  
  boolean supportsWrapComputation()
  {
    if (this.dimensionBehavior == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
      return this.widget.mMatchConstraintDefaultWidth == 0;
    }
    return true;
  }
  
  public String toString()
  {
    return "HorizontalRun " + this.widget.getDebugName();
  }
  
  public void update(Dependency paramDependency)
  {
    switch (1.$SwitchMap$androidx$constraintlayout$core$widgets$analyzer$WidgetRun$RunType[this.mRunType.ordinal()])
    {
    default: 
      break;
    case 3: 
      updateRunCenter(paramDependency, this.widget.mLeft, this.widget.mRight, 0);
      return;
    case 2: 
      updateRunEnd(paramDependency);
      break;
    case 1: 
      updateRunStart(paramDependency);
    }
    int i;
    DependencyNode localDependencyNode;
    int j;
    int k;
    float f;
    if ((!this.dimension.resolved) && (this.dimensionBehavior == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT)) {
      switch (this.widget.mMatchConstraintDefaultWidth)
      {
      default: 
        break;
      case 3: 
        if ((this.widget.mMatchConstraintDefaultHeight != 0) && (this.widget.mMatchConstraintDefaultHeight != 3))
        {
          i = 0;
          switch (this.widget.getDimensionRatioSide())
          {
          default: 
            break;
          case 1: 
            i = (int)(this.widget.verticalRun.dimension.value * this.widget.getDimensionRatio() + 0.5F);
            break;
          case 0: 
            i = (int)(this.widget.verticalRun.dimension.value / this.widget.getDimensionRatio() + 0.5F);
            break;
          case -1: 
            i = (int)(this.widget.verticalRun.dimension.value * this.widget.getDimensionRatio() + 0.5F);
          }
          this.dimension.resolve(i);
        }
        else
        {
          localDependencyNode = this.widget.verticalRun.start;
          paramDependency = this.widget.verticalRun.end;
          if (this.widget.mLeft.mTarget != null) {
            i = 1;
          } else {
            i = 0;
          }
          if (this.widget.mTop.mTarget != null) {
            j = 1;
          } else {
            j = 0;
          }
          if (this.widget.mRight.mTarget != null) {
            k = 1;
          } else {
            k = 0;
          }
          int m;
          if (this.widget.mBottom.mTarget != null) {
            m = 1;
          } else {
            m = 0;
          }
          int n = this.widget.getDimensionRatioSide();
          if ((i != 0) && (j != 0) && (k != 0) && (m != 0))
          {
            f = this.widget.getDimensionRatio();
            int i3;
            int i4;
            int i2;
            int i1;
            if ((localDependencyNode.resolved) && (paramDependency.resolved))
            {
              if ((this.start.readyToSolve) && (this.end.readyToSolve))
              {
                i = ((DependencyNode)this.start.targets.get(0)).value;
                i3 = this.start.margin;
                m = ((DependencyNode)this.end.targets.get(0)).value;
                k = this.end.margin;
                i4 = localDependencyNode.value;
                i2 = localDependencyNode.margin;
                i1 = paramDependency.value;
                j = paramDependency.margin;
                computeInsetRatio(tempDimensions, i + i3, m - k, i4 + i2, i1 - j, f, n);
                this.dimension.resolve(tempDimensions[0]);
                this.widget.verticalRun.dimension.resolve(tempDimensions[1]);
                return;
              }
              return;
            }
            if ((this.start.resolved) && (this.end.resolved)) {
              if ((localDependencyNode.readyToSolve) && (paramDependency.readyToSolve))
              {
                i = this.start.value;
                i3 = this.start.margin;
                m = this.end.value;
                i4 = this.end.margin;
                j = ((DependencyNode)localDependencyNode.targets.get(0)).value;
                i1 = localDependencyNode.margin;
                k = ((DependencyNode)paramDependency.targets.get(0)).value;
                i2 = paramDependency.margin;
                computeInsetRatio(tempDimensions, i + i3, m - i4, j + i1, k - i2, f, n);
                this.dimension.resolve(tempDimensions[0]);
                this.widget.verticalRun.dimension.resolve(tempDimensions[1]);
              }
              else
              {
                return;
              }
            }
            if ((this.start.readyToSolve) && (this.end.readyToSolve) && (localDependencyNode.readyToSolve) && (paramDependency.readyToSolve))
            {
              m = ((DependencyNode)this.start.targets.get(0)).value;
              i1 = this.start.margin;
              i = ((DependencyNode)this.end.targets.get(0)).value;
              i2 = this.end.margin;
              j = ((DependencyNode)localDependencyNode.targets.get(0)).value;
              i4 = localDependencyNode.margin;
              k = ((DependencyNode)paramDependency.targets.get(0)).value;
              i3 = paramDependency.margin;
              computeInsetRatio(tempDimensions, m + i1, i - i2, j + i4, k - i3, f, n);
              this.dimension.resolve(tempDimensions[0]);
              this.widget.verticalRun.dimension.resolve(tempDimensions[1]);
            }
          }
          else
          {
            if ((i != 0) && (k != 0)) {
              if ((this.start.readyToSolve) && (this.end.readyToSolve))
              {
                f = this.widget.getDimensionRatio();
                j = ((DependencyNode)this.start.targets.get(0)).value + this.start.margin;
                i = ((DependencyNode)this.end.targets.get(0)).value - this.end.margin;
              }
            }
            switch (n)
            {
            default: 
              break;
            case 1: 
              i = getLimitedDimension(i - j, 0);
              k = (int)(i / f + 0.5F);
              j = getLimitedDimension(k, 1);
              if (k != j) {
                i = (int)(j * f + 0.5F);
              }
              this.dimension.resolve(i);
              this.widget.verticalRun.dimension.resolve(j);
              break;
            case -1: 
            case 0: 
              i = getLimitedDimension(i - j, 0);
              k = (int)(i * f + 0.5F);
              j = getLimitedDimension(k, 1);
              if (k != j) {
                i = (int)(j / f + 0.5F);
              }
              this.dimension.resolve(i);
              this.widget.verticalRun.dimension.resolve(j);
              break;
              return;
              if ((j != 0) && (m != 0))
              {
                if ((localDependencyNode.readyToSolve) && (paramDependency.readyToSolve))
                {
                  f = this.widget.getDimensionRatio();
                  i = ((DependencyNode)localDependencyNode.targets.get(0)).value + localDependencyNode.margin;
                  j = ((DependencyNode)paramDependency.targets.get(0)).value - paramDependency.margin;
                }
                switch (n)
                {
                default: 
                  break;
                case 0: 
                  i = getLimitedDimension(j - i, 1);
                  k = (int)(i * f + 0.5F);
                  j = getLimitedDimension(k, 0);
                  if (k != j) {
                    i = (int)(j / f + 0.5F);
                  }
                  this.dimension.resolve(j);
                  this.widget.verticalRun.dimension.resolve(i);
                  break;
                case -1: 
                case 1: 
                  i = getLimitedDimension(j - i, 1);
                  k = (int)(i / f + 0.5F);
                  j = getLimitedDimension(k, 0);
                  if (k != j) {
                    i = (int)(j * f + 0.5F);
                  }
                  this.dimension.resolve(j);
                  this.widget.verticalRun.dimension.resolve(i);
                  break;
                  return;
                }
              }
              break;
            }
          }
        }
        break;
      case 2: 
        paramDependency = this.widget.getParent();
        if ((paramDependency != null) && (paramDependency.horizontalRun.dimension.resolved))
        {
          f = this.widget.mMatchConstraintPercentWidth;
          i = (int)(paramDependency.horizontalRun.dimension.value * f + 0.5F);
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
      if ((!this.dimension.resolved) && (this.dimensionBehavior == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) && (this.widget.mMatchConstraintDefaultWidth == 0) && (!this.widget.isInHorizontalChain()))
      {
        localDependencyNode = (DependencyNode)this.start.targets.get(0);
        paramDependency = (DependencyNode)this.end.targets.get(0);
        i = localDependencyNode.value + this.start.margin;
        j = paramDependency.value + this.end.margin;
        this.start.resolve(i);
        this.end.resolve(j);
        this.dimension.resolve(j - i);
        return;
      }
      if ((!this.dimension.resolved) && (this.dimensionBehavior == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) && (this.matchConstraintsType == 1) && (this.start.targets.size() > 0) && (this.end.targets.size() > 0))
      {
        localDependencyNode = (DependencyNode)this.start.targets.get(0);
        paramDependency = (DependencyNode)this.end.targets.get(0);
        i = localDependencyNode.value;
        j = this.start.margin;
        i = Math.min(paramDependency.value + this.end.margin - (i + j), this.dimension.wrapValue);
        k = this.widget.mMatchConstraintMaxWidth;
        j = Math.max(this.widget.mMatchConstraintMinWidth, i);
        i = j;
        if (k > 0) {
          i = Math.min(k, j);
        }
        this.dimension.resolve(i);
      }
      if (!this.dimension.resolved) {
        return;
      }
      localDependencyNode = (DependencyNode)this.start.targets.get(0);
      paramDependency = (DependencyNode)this.end.targets.get(0);
      i = localDependencyNode.value + this.start.margin;
      j = paramDependency.value + this.end.margin;
      f = this.widget.getHorizontalBiasPercent();
      if (localDependencyNode == paramDependency)
      {
        i = localDependencyNode.value;
        j = paramDependency.value;
        f = 0.5F;
      }
      k = this.dimension.value;
      this.start.resolve((int)(i + 0.5F + (j - i - k) * f));
      this.end.resolve(this.start.value + this.dimension.value);
      return;
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/constraintlayout/core/widgets/analyzer/HorizontalWidgetRun.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */