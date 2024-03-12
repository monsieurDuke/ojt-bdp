package androidx.constraintlayout.core.widgets.analyzer;

import androidx.constraintlayout.core.widgets.ConstraintWidget;

class BaselineDimensionDependency
  extends DimensionDependency
{
  public BaselineDimensionDependency(WidgetRun paramWidgetRun)
  {
    super(paramWidgetRun);
  }
  
  public void update(DependencyNode paramDependencyNode)
  {
    ((VerticalWidgetRun)this.run).baseline.margin = this.run.widget.getBaselineDistance();
    this.resolved = true;
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/constraintlayout/core/widgets/analyzer/BaselineDimensionDependency.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */