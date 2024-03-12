package androidx.constraintlayout.core.widgets;

import androidx.constraintlayout.core.Cache;
import java.util.ArrayList;

public class WidgetContainer
  extends ConstraintWidget
{
  public ArrayList<ConstraintWidget> mChildren = new ArrayList();
  
  public WidgetContainer() {}
  
  public WidgetContainer(int paramInt1, int paramInt2)
  {
    super(paramInt1, paramInt2);
  }
  
  public WidgetContainer(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    super(paramInt1, paramInt2, paramInt3, paramInt4);
  }
  
  public void add(ConstraintWidget paramConstraintWidget)
  {
    this.mChildren.add(paramConstraintWidget);
    if (paramConstraintWidget.getParent() != null) {
      ((WidgetContainer)paramConstraintWidget.getParent()).remove(paramConstraintWidget);
    }
    paramConstraintWidget.setParent(this);
  }
  
  public void add(ConstraintWidget... paramVarArgs)
  {
    int j = paramVarArgs.length;
    for (int i = 0; i < j; i++) {
      add(paramVarArgs[i]);
    }
  }
  
  public ArrayList<ConstraintWidget> getChildren()
  {
    return this.mChildren;
  }
  
  public ConstraintWidgetContainer getRootConstraintContainer()
  {
    ConstraintWidget localConstraintWidget2 = getParent();
    ConstraintWidgetContainer localConstraintWidgetContainer = null;
    ConstraintWidget localConstraintWidget1 = localConstraintWidget2;
    if ((this instanceof ConstraintWidgetContainer))
    {
      localConstraintWidgetContainer = (ConstraintWidgetContainer)this;
      localConstraintWidget1 = localConstraintWidget2;
    }
    for (;;)
    {
      ConstraintWidget localConstraintWidget3 = localConstraintWidget1;
      if (localConstraintWidget3 == null) {
        break;
      }
      localConstraintWidget2 = localConstraintWidget3.getParent();
      localConstraintWidget1 = localConstraintWidget2;
      if ((localConstraintWidget3 instanceof ConstraintWidgetContainer))
      {
        localConstraintWidgetContainer = (ConstraintWidgetContainer)localConstraintWidget3;
        localConstraintWidget1 = localConstraintWidget2;
      }
    }
    return localConstraintWidgetContainer;
  }
  
  public void layout()
  {
    Object localObject = this.mChildren;
    if (localObject == null) {
      return;
    }
    int j = ((ArrayList)localObject).size();
    for (int i = 0; i < j; i++)
    {
      localObject = (ConstraintWidget)this.mChildren.get(i);
      if ((localObject instanceof WidgetContainer)) {
        ((WidgetContainer)localObject).layout();
      }
    }
  }
  
  public void remove(ConstraintWidget paramConstraintWidget)
  {
    this.mChildren.remove(paramConstraintWidget);
    paramConstraintWidget.reset();
  }
  
  public void removeAllChildren()
  {
    this.mChildren.clear();
  }
  
  public void reset()
  {
    this.mChildren.clear();
    super.reset();
  }
  
  public void resetSolverVariables(Cache paramCache)
  {
    super.resetSolverVariables(paramCache);
    int j = this.mChildren.size();
    for (int i = 0; i < j; i++) {
      ((ConstraintWidget)this.mChildren.get(i)).resetSolverVariables(paramCache);
    }
  }
  
  public void setOffset(int paramInt1, int paramInt2)
  {
    super.setOffset(paramInt1, paramInt2);
    paramInt2 = this.mChildren.size();
    for (paramInt1 = 0; paramInt1 < paramInt2; paramInt1++) {
      ((ConstraintWidget)this.mChildren.get(paramInt1)).setOffset(getRootX(), getRootY());
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/constraintlayout/core/widgets/WidgetContainer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */