package androidx.constraintlayout.core.widgets.analyzer;

import java.util.Iterator;
import java.util.List;

class DimensionDependency
  extends DependencyNode
{
  public int wrapValue;
  
  public DimensionDependency(WidgetRun paramWidgetRun)
  {
    super(paramWidgetRun);
    if ((paramWidgetRun instanceof HorizontalWidgetRun)) {
      this.type = DependencyNode.Type.HORIZONTAL_DIMENSION;
    } else {
      this.type = DependencyNode.Type.VERTICAL_DIMENSION;
    }
  }
  
  public void resolve(int paramInt)
  {
    if (this.resolved) {
      return;
    }
    this.resolved = true;
    this.value = paramInt;
    Iterator localIterator = this.dependencies.iterator();
    while (localIterator.hasNext())
    {
      Dependency localDependency = (Dependency)localIterator.next();
      localDependency.update(localDependency);
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/constraintlayout/core/widgets/analyzer/DimensionDependency.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */