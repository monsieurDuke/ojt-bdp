package androidx.constraintlayout.core.widgets;

import androidx.constraintlayout.core.widgets.analyzer.Grouping;
import androidx.constraintlayout.core.widgets.analyzer.WidgetGroup;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class HelperWidget
  extends ConstraintWidget
  implements Helper
{
  public ConstraintWidget[] mWidgets = new ConstraintWidget[4];
  public int mWidgetsCount = 0;
  
  public void add(ConstraintWidget paramConstraintWidget)
  {
    if ((paramConstraintWidget != this) && (paramConstraintWidget != null))
    {
      int i = this.mWidgetsCount;
      ConstraintWidget[] arrayOfConstraintWidget = this.mWidgets;
      if (i + 1 > arrayOfConstraintWidget.length) {
        this.mWidgets = ((ConstraintWidget[])Arrays.copyOf(arrayOfConstraintWidget, arrayOfConstraintWidget.length * 2));
      }
      arrayOfConstraintWidget = this.mWidgets;
      i = this.mWidgetsCount;
      arrayOfConstraintWidget[i] = paramConstraintWidget;
      this.mWidgetsCount = (i + 1);
      return;
    }
  }
  
  public void addDependents(ArrayList<WidgetGroup> paramArrayList, int paramInt, WidgetGroup paramWidgetGroup)
  {
    for (int i = 0; i < this.mWidgetsCount; i++) {
      paramWidgetGroup.add(this.mWidgets[i]);
    }
    for (i = 0; i < this.mWidgetsCount; i++) {
      Grouping.findDependents(this.mWidgets[i], paramInt, paramArrayList, paramWidgetGroup);
    }
  }
  
  public void copy(ConstraintWidget paramConstraintWidget, HashMap<ConstraintWidget, ConstraintWidget> paramHashMap)
  {
    super.copy(paramConstraintWidget, paramHashMap);
    paramConstraintWidget = (HelperWidget)paramConstraintWidget;
    this.mWidgetsCount = 0;
    int j = paramConstraintWidget.mWidgetsCount;
    for (int i = 0; i < j; i++) {
      add((ConstraintWidget)paramHashMap.get(paramConstraintWidget.mWidgets[i]));
    }
  }
  
  public int findGroupInDependents(int paramInt)
  {
    for (int i = 0; i < this.mWidgetsCount; i++)
    {
      ConstraintWidget localConstraintWidget = this.mWidgets[i];
      if ((paramInt == 0) && (localConstraintWidget.horizontalGroup != -1)) {
        return localConstraintWidget.horizontalGroup;
      }
      if ((paramInt == 1) && (localConstraintWidget.verticalGroup != -1)) {
        return localConstraintWidget.verticalGroup;
      }
    }
    return -1;
  }
  
  public void removeAllIds()
  {
    this.mWidgetsCount = 0;
    Arrays.fill(this.mWidgets, null);
  }
  
  public void updateConstraints(ConstraintWidgetContainer paramConstraintWidgetContainer) {}
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/constraintlayout/core/widgets/HelperWidget.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */