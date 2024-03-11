package androidx.constraintlayout.core.widgets.analyzer;

import androidx.constraintlayout.core.widgets.ConstraintWidget;
import androidx.constraintlayout.core.widgets.ConstraintWidgetContainer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

class RunGroup
{
  public static final int BASELINE = 2;
  public static final int END = 1;
  public static final int START = 0;
  public static int index;
  int direction;
  public boolean dual = false;
  WidgetRun firstRun = null;
  int groupIndex = 0;
  WidgetRun lastRun = null;
  public int position = 0;
  ArrayList<WidgetRun> runs = new ArrayList();
  
  public RunGroup(WidgetRun paramWidgetRun, int paramInt)
  {
    int i = index;
    this.groupIndex = i;
    index = i + 1;
    this.firstRun = paramWidgetRun;
    this.lastRun = paramWidgetRun;
    this.direction = paramInt;
  }
  
  private boolean defineTerminalWidget(WidgetRun paramWidgetRun, int paramInt)
  {
    if (paramWidgetRun.widget.isTerminalWidget[paramInt] == 0) {
      return false;
    }
    Iterator localIterator = paramWidgetRun.start.dependencies.iterator();
    Object localObject1;
    Object localObject2;
    while (localIterator.hasNext())
    {
      localObject1 = (Dependency)localIterator.next();
      if ((localObject1 instanceof DependencyNode))
      {
        localObject2 = (DependencyNode)localObject1;
        if (((DependencyNode)localObject2).run != paramWidgetRun) {
          if (localObject2 == ((DependencyNode)localObject2).run.start)
          {
            if ((paramWidgetRun instanceof ChainRun))
            {
              localObject1 = ((ChainRun)paramWidgetRun).widgets.iterator();
              while (((Iterator)localObject1).hasNext()) {
                defineTerminalWidget((WidgetRun)((Iterator)localObject1).next(), paramInt);
              }
            }
            else if (!(paramWidgetRun instanceof HelperReferences))
            {
              paramWidgetRun.widget.isTerminalWidget[paramInt] = false;
            }
            defineTerminalWidget(((DependencyNode)localObject2).run, paramInt);
          }
        }
      }
    }
    localIterator = paramWidgetRun.end.dependencies.iterator();
    while (localIterator.hasNext())
    {
      localObject1 = (Dependency)localIterator.next();
      if ((localObject1 instanceof DependencyNode))
      {
        localObject1 = (DependencyNode)localObject1;
        if (((DependencyNode)localObject1).run != paramWidgetRun) {
          if (localObject1 == ((DependencyNode)localObject1).run.start)
          {
            if ((paramWidgetRun instanceof ChainRun))
            {
              localObject2 = ((ChainRun)paramWidgetRun).widgets.iterator();
              while (((Iterator)localObject2).hasNext()) {
                defineTerminalWidget((WidgetRun)((Iterator)localObject2).next(), paramInt);
              }
            }
            else if (!(paramWidgetRun instanceof HelperReferences))
            {
              paramWidgetRun.widget.isTerminalWidget[paramInt] = false;
            }
            defineTerminalWidget(((DependencyNode)localObject1).run, paramInt);
          }
        }
      }
    }
    return false;
  }
  
  private long traverseEnd(DependencyNode paramDependencyNode, long paramLong)
  {
    WidgetRun localWidgetRun = paramDependencyNode.run;
    if ((localWidgetRun instanceof HelperReferences)) {
      return paramLong;
    }
    long l1 = paramLong;
    int j = paramDependencyNode.dependencies.size();
    int i = 0;
    while (i < j)
    {
      Object localObject = (Dependency)paramDependencyNode.dependencies.get(i);
      l2 = l1;
      if ((localObject instanceof DependencyNode))
      {
        localObject = (DependencyNode)localObject;
        if (((DependencyNode)localObject).run == localWidgetRun) {
          l2 = l1;
        } else {
          l2 = Math.min(l1, traverseEnd((DependencyNode)localObject, ((DependencyNode)localObject).margin + paramLong));
        }
      }
      i++;
      l1 = l2;
    }
    long l2 = l1;
    if (paramDependencyNode == localWidgetRun.end)
    {
      l2 = localWidgetRun.getWrapDimension();
      l2 = Math.min(Math.min(l1, traverseEnd(localWidgetRun.start, paramLong - l2)), paramLong - l2 - localWidgetRun.start.margin);
    }
    return l2;
  }
  
  private long traverseStart(DependencyNode paramDependencyNode, long paramLong)
  {
    WidgetRun localWidgetRun = paramDependencyNode.run;
    if ((localWidgetRun instanceof HelperReferences)) {
      return paramLong;
    }
    long l1 = paramLong;
    int j = paramDependencyNode.dependencies.size();
    int i = 0;
    while (i < j)
    {
      Object localObject = (Dependency)paramDependencyNode.dependencies.get(i);
      l2 = l1;
      if ((localObject instanceof DependencyNode))
      {
        localObject = (DependencyNode)localObject;
        if (((DependencyNode)localObject).run == localWidgetRun) {
          l2 = l1;
        } else {
          l2 = Math.max(l1, traverseStart((DependencyNode)localObject, ((DependencyNode)localObject).margin + paramLong));
        }
      }
      i++;
      l1 = l2;
    }
    long l2 = l1;
    if (paramDependencyNode == localWidgetRun.start)
    {
      l2 = localWidgetRun.getWrapDimension();
      l2 = Math.max(Math.max(l1, traverseStart(localWidgetRun.end, paramLong + l2)), paramLong + l2 - localWidgetRun.end.margin);
    }
    return l2;
  }
  
  public void add(WidgetRun paramWidgetRun)
  {
    this.runs.add(paramWidgetRun);
    this.lastRun = paramWidgetRun;
  }
  
  public long computeWrapSize(ConstraintWidgetContainer paramConstraintWidgetContainer, int paramInt)
  {
    Object localObject = this.firstRun;
    if ((localObject instanceof ChainRun))
    {
      if (((ChainRun)localObject).orientation != paramInt) {
        return 0L;
      }
    }
    else if (paramInt == 0)
    {
      if (!(localObject instanceof HorizontalWidgetRun)) {
        return 0L;
      }
    }
    else if (!(localObject instanceof VerticalWidgetRun)) {
      return 0L;
    }
    if (paramInt == 0) {
      localObject = paramConstraintWidgetContainer.horizontalRun.start;
    } else {
      localObject = paramConstraintWidgetContainer.verticalRun.start;
    }
    if (paramInt == 0) {
      paramConstraintWidgetContainer = paramConstraintWidgetContainer.horizontalRun.end;
    } else {
      paramConstraintWidgetContainer = paramConstraintWidgetContainer.verticalRun.end;
    }
    boolean bool1 = this.firstRun.start.targets.contains(localObject);
    boolean bool2 = this.firstRun.end.targets.contains(paramConstraintWidgetContainer);
    long l4 = this.firstRun.getWrapDimension();
    long l1;
    long l2;
    if ((bool1) && (bool2))
    {
      l1 = traverseStart(this.firstRun.start, 0L);
      long l3 = traverseEnd(this.firstRun.end, 0L);
      l2 = l1 - l4;
      l1 = l2;
      if (l2 >= -this.firstRun.end.margin) {
        l1 = l2 + this.firstRun.end.margin;
      }
      l3 = -l3 - l4 - this.firstRun.start.margin;
      l2 = l3;
      if (l3 >= this.firstRun.start.margin) {
        l2 = l3 - this.firstRun.start.margin;
      }
      float f = this.firstRun.widget.getBiasPercent(paramInt);
      if (f > 0.0F) {
        l1 = ((float)l2 / f + (float)l1 / (1.0F - f));
      } else {
        l1 = 0L;
      }
      l2 = ((float)l1 * f + 0.5F);
      l1 = ((float)l1 * (1.0F - f) + 0.5F);
      l1 = this.firstRun.start.margin + (l2 + l4 + l1) - this.firstRun.end.margin;
    }
    else if (bool1)
    {
      l1 = Math.max(traverseStart(this.firstRun.start, this.firstRun.start.margin), this.firstRun.start.margin + l4);
    }
    else if (bool2)
    {
      l2 = traverseEnd(this.firstRun.end, this.firstRun.end.margin);
      l1 = -this.firstRun.end.margin;
      l1 = Math.max(-l2, l1 + l4);
    }
    else
    {
      l1 = this.firstRun.start.margin + this.firstRun.getWrapDimension() - this.firstRun.end.margin;
    }
    return l1;
  }
  
  public void defineTerminalWidgets(boolean paramBoolean1, boolean paramBoolean2)
  {
    WidgetRun localWidgetRun;
    if (paramBoolean1)
    {
      localWidgetRun = this.firstRun;
      if ((localWidgetRun instanceof HorizontalWidgetRun)) {
        defineTerminalWidget(localWidgetRun, 0);
      }
    }
    if (paramBoolean2)
    {
      localWidgetRun = this.firstRun;
      if ((localWidgetRun instanceof VerticalWidgetRun)) {
        defineTerminalWidget(localWidgetRun, 1);
      }
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/constraintlayout/core/widgets/analyzer/RunGroup.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */