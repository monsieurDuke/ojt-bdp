package androidx.constraintlayout.core.widgets.analyzer;

import androidx.constraintlayout.core.widgets.Barrier;
import androidx.constraintlayout.core.widgets.ConstraintWidget;
import java.util.Iterator;
import java.util.List;

class HelperReferences
  extends WidgetRun
{
  public HelperReferences(ConstraintWidget paramConstraintWidget)
  {
    super(paramConstraintWidget);
  }
  
  private void addDependency(DependencyNode paramDependencyNode)
  {
    this.start.dependencies.add(paramDependencyNode);
    paramDependencyNode.targets.add(this.start);
  }
  
  void apply()
  {
    if ((this.widget instanceof Barrier))
    {
      this.start.delegateToWidgetRun = true;
      Barrier localBarrier = (Barrier)this.widget;
      int i = localBarrier.getBarrierType();
      boolean bool = localBarrier.getAllowsGoneWidget();
      Object localObject;
      switch (i)
      {
      default: 
        break;
      case 3: 
        this.start.type = DependencyNode.Type.BOTTOM;
        for (i = 0; i < localBarrier.mWidgetsCount; i++)
        {
          localObject = localBarrier.mWidgets[i];
          if ((bool) || (((ConstraintWidget)localObject).getVisibility() != 8))
          {
            localObject = ((ConstraintWidget)localObject).verticalRun.end;
            ((DependencyNode)localObject).dependencies.add(this.start);
            this.start.targets.add(localObject);
          }
        }
        addDependency(this.widget.verticalRun.start);
        addDependency(this.widget.verticalRun.end);
        break;
      case 2: 
        this.start.type = DependencyNode.Type.TOP;
        for (i = 0; i < localBarrier.mWidgetsCount; i++)
        {
          localObject = localBarrier.mWidgets[i];
          if ((bool) || (((ConstraintWidget)localObject).getVisibility() != 8))
          {
            localObject = ((ConstraintWidget)localObject).verticalRun.start;
            ((DependencyNode)localObject).dependencies.add(this.start);
            this.start.targets.add(localObject);
          }
        }
        addDependency(this.widget.verticalRun.start);
        addDependency(this.widget.verticalRun.end);
        break;
      case 1: 
        this.start.type = DependencyNode.Type.RIGHT;
        for (i = 0; i < localBarrier.mWidgetsCount; i++)
        {
          localObject = localBarrier.mWidgets[i];
          if ((bool) || (((ConstraintWidget)localObject).getVisibility() != 8))
          {
            localObject = ((ConstraintWidget)localObject).horizontalRun.end;
            ((DependencyNode)localObject).dependencies.add(this.start);
            this.start.targets.add(localObject);
          }
        }
        addDependency(this.widget.horizontalRun.start);
        addDependency(this.widget.horizontalRun.end);
        break;
      case 0: 
        this.start.type = DependencyNode.Type.LEFT;
        for (i = 0; i < localBarrier.mWidgetsCount; i++)
        {
          localObject = localBarrier.mWidgets[i];
          if ((bool) || (((ConstraintWidget)localObject).getVisibility() != 8))
          {
            localObject = ((ConstraintWidget)localObject).horizontalRun.start;
            ((DependencyNode)localObject).dependencies.add(this.start);
            this.start.targets.add(localObject);
          }
        }
        addDependency(this.widget.horizontalRun.start);
        addDependency(this.widget.horizontalRun.end);
      }
    }
  }
  
  public void applyToWidget()
  {
    if ((this.widget instanceof Barrier))
    {
      int i = ((Barrier)this.widget).getBarrierType();
      if ((i != 0) && (i != 1)) {
        this.widget.setY(this.start.value);
      } else {
        this.widget.setX(this.start.value);
      }
    }
  }
  
  void clear()
  {
    this.runGroup = null;
    this.start.clear();
  }
  
  void reset()
  {
    this.start.resolved = false;
  }
  
  boolean supportsWrapComputation()
  {
    return false;
  }
  
  public void update(Dependency paramDependency)
  {
    Barrier localBarrier = (Barrier)this.widget;
    int i1 = localBarrier.getBarrierType();
    int n = -1;
    int i = 0;
    paramDependency = this.start.targets.iterator();
    while (paramDependency.hasNext())
    {
      int k = ((DependencyNode)paramDependency.next()).value;
      int j;
      if (n != -1)
      {
        j = n;
        if (k >= n) {}
      }
      else
      {
        j = k;
      }
      int m = i;
      if (i < k) {
        m = k;
      }
      n = j;
      i = m;
    }
    if ((i1 != 0) && (i1 != 2)) {
      this.start.resolve(localBarrier.getMargin() + i);
    } else {
      this.start.resolve(localBarrier.getMargin() + n);
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/constraintlayout/core/widgets/analyzer/HelperReferences.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */