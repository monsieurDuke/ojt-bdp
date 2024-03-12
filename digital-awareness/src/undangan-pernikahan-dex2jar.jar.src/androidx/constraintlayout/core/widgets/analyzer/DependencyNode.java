package androidx.constraintlayout.core.widgets.analyzer;

import androidx.constraintlayout.core.widgets.ConstraintWidget;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class DependencyNode
  implements Dependency
{
  public boolean delegateToWidgetRun = false;
  List<Dependency> dependencies = new ArrayList();
  int margin;
  DimensionDependency marginDependency = null;
  int marginFactor = 1;
  public boolean readyToSolve = false;
  public boolean resolved = false;
  WidgetRun run;
  List<DependencyNode> targets = new ArrayList();
  Type type = Type.UNKNOWN;
  public Dependency updateDelegate = null;
  public int value;
  
  public DependencyNode(WidgetRun paramWidgetRun)
  {
    this.run = paramWidgetRun;
  }
  
  public void addDependency(Dependency paramDependency)
  {
    this.dependencies.add(paramDependency);
    if (this.resolved) {
      paramDependency.update(paramDependency);
    }
  }
  
  public void clear()
  {
    this.targets.clear();
    this.dependencies.clear();
    this.resolved = false;
    this.value = 0;
    this.readyToSolve = false;
    this.delegateToWidgetRun = false;
  }
  
  public String name()
  {
    String str = this.run.widget.getDebugName();
    if ((this.type != Type.LEFT) && (this.type != Type.RIGHT)) {
      str = str + "_VERTICAL";
    } else {
      str = str + "_HORIZONTAL";
    }
    return str + ":" + this.type.name();
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
  
  public String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder().append(this.run.widget.getDebugName()).append(":").append(this.type).append("(");
    Object localObject;
    if (this.resolved) {
      localObject = Integer.valueOf(this.value);
    } else {
      localObject = "unresolved";
    }
    return localObject + ") <t=" + this.targets.size() + ":d=" + this.dependencies.size() + ">";
  }
  
  public void update(Dependency paramDependency)
  {
    paramDependency = this.targets.iterator();
    while (paramDependency.hasNext()) {
      if (!((DependencyNode)paramDependency.next()).resolved) {
        return;
      }
    }
    this.readyToSolve = true;
    paramDependency = this.updateDelegate;
    if (paramDependency != null) {
      paramDependency.update(this);
    }
    if (this.delegateToWidgetRun)
    {
      this.run.update(this);
      return;
    }
    paramDependency = null;
    int i = 0;
    Iterator localIterator = this.targets.iterator();
    Object localObject;
    while (localIterator.hasNext())
    {
      localObject = (DependencyNode)localIterator.next();
      if (!(localObject instanceof DimensionDependency))
      {
        paramDependency = (Dependency)localObject;
        i++;
      }
    }
    if ((paramDependency != null) && (i == 1) && (paramDependency.resolved))
    {
      localObject = this.marginDependency;
      if (localObject != null) {
        if (((DimensionDependency)localObject).resolved) {
          this.margin = (this.marginFactor * this.marginDependency.value);
        } else {
          return;
        }
      }
      resolve(paramDependency.value + this.margin);
    }
    paramDependency = this.updateDelegate;
    if (paramDependency != null) {
      paramDependency.update(this);
    }
  }
  
  static enum Type
  {
    private static final Type[] $VALUES;
    
    static
    {
      Type localType5 = new Type("UNKNOWN", 0);
      UNKNOWN = localType5;
      Type localType6 = new Type("HORIZONTAL_DIMENSION", 1);
      HORIZONTAL_DIMENSION = localType6;
      Type localType2 = new Type("VERTICAL_DIMENSION", 2);
      VERTICAL_DIMENSION = localType2;
      Type localType3 = new Type("LEFT", 3);
      LEFT = localType3;
      Type localType4 = new Type("RIGHT", 4);
      RIGHT = localType4;
      Type localType1 = new Type("TOP", 5);
      TOP = localType1;
      Type localType7 = new Type("BOTTOM", 6);
      BOTTOM = localType7;
      Type localType8 = new Type("BASELINE", 7);
      BASELINE = localType8;
      $VALUES = new Type[] { localType5, localType6, localType2, localType3, localType4, localType1, localType7, localType8 };
    }
    
    private Type() {}
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/constraintlayout/core/widgets/analyzer/DependencyNode.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */