package androidx.constraintlayout.core.widgets.analyzer;

import androidx.constraintlayout.core.LinearSystem;
import androidx.constraintlayout.core.widgets.Chain;
import androidx.constraintlayout.core.widgets.ConstraintWidget;
import androidx.constraintlayout.core.widgets.ConstraintWidget.DimensionBehaviour;
import androidx.constraintlayout.core.widgets.ConstraintWidgetContainer;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;

public class WidgetGroup
{
  private static final boolean DEBUG = false;
  static int count = 0;
  boolean authoritative = false;
  int id = -1;
  private int moveTo = -1;
  int orientation = 0;
  ArrayList<MeasureResult> results = null;
  ArrayList<ConstraintWidget> widgets = new ArrayList();
  
  public WidgetGroup(int paramInt)
  {
    int i = count;
    count = i + 1;
    this.id = i;
    this.orientation = paramInt;
  }
  
  private boolean contains(ConstraintWidget paramConstraintWidget)
  {
    return this.widgets.contains(paramConstraintWidget);
  }
  
  private String getOrientationString()
  {
    int i = this.orientation;
    if (i == 0) {
      return "Horizontal";
    }
    if (i == 1) {
      return "Vertical";
    }
    if (i == 2) {
      return "Both";
    }
    return "Unknown";
  }
  
  private int measureWrap(int paramInt, ConstraintWidget paramConstraintWidget)
  {
    ConstraintWidget.DimensionBehaviour localDimensionBehaviour = paramConstraintWidget.getDimensionBehaviour(paramInt);
    if ((localDimensionBehaviour != ConstraintWidget.DimensionBehaviour.WRAP_CONTENT) && (localDimensionBehaviour != ConstraintWidget.DimensionBehaviour.MATCH_PARENT) && (localDimensionBehaviour != ConstraintWidget.DimensionBehaviour.FIXED)) {
      return -1;
    }
    if (paramInt == 0) {
      paramInt = paramConstraintWidget.getWidth();
    } else {
      paramInt = paramConstraintWidget.getHeight();
    }
    return paramInt;
  }
  
  private int solverMeasure(LinearSystem paramLinearSystem, ArrayList<ConstraintWidget> paramArrayList, int paramInt)
  {
    ConstraintWidgetContainer localConstraintWidgetContainer = (ConstraintWidgetContainer)((ConstraintWidget)paramArrayList.get(0)).getParent();
    paramLinearSystem.reset();
    localConstraintWidgetContainer.addToSolver(paramLinearSystem, false);
    for (int i = 0; i < paramArrayList.size(); i++) {
      ((ConstraintWidget)paramArrayList.get(i)).addToSolver(paramLinearSystem, false);
    }
    if ((paramInt == 0) && (localConstraintWidgetContainer.mHorizontalChainsSize > 0)) {
      Chain.applyChainConstraints(localConstraintWidgetContainer, paramLinearSystem, paramArrayList, 0);
    }
    if ((paramInt == 1) && (localConstraintWidgetContainer.mVerticalChainsSize > 0)) {
      Chain.applyChainConstraints(localConstraintWidgetContainer, paramLinearSystem, paramArrayList, 1);
    }
    try
    {
      paramLinearSystem.minimize();
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    this.results = new ArrayList();
    for (i = 0; i < paramArrayList.size(); i++)
    {
      MeasureResult localMeasureResult = new MeasureResult((ConstraintWidget)paramArrayList.get(i), paramLinearSystem, paramInt);
      this.results.add(localMeasureResult);
    }
    if (paramInt == 0)
    {
      paramInt = paramLinearSystem.getObjectVariableValue(localConstraintWidgetContainer.mLeft);
      i = paramLinearSystem.getObjectVariableValue(localConstraintWidgetContainer.mRight);
      paramLinearSystem.reset();
      return i - paramInt;
    }
    paramInt = paramLinearSystem.getObjectVariableValue(localConstraintWidgetContainer.mTop);
    i = paramLinearSystem.getObjectVariableValue(localConstraintWidgetContainer.mBottom);
    paramLinearSystem.reset();
    return i - paramInt;
  }
  
  public boolean add(ConstraintWidget paramConstraintWidget)
  {
    if (this.widgets.contains(paramConstraintWidget)) {
      return false;
    }
    this.widgets.add(paramConstraintWidget);
    return true;
  }
  
  public void apply()
  {
    if (this.results == null) {
      return;
    }
    if (!this.authoritative) {
      return;
    }
    for (int i = 0; i < this.results.size(); i++) {
      ((MeasureResult)this.results.get(i)).apply();
    }
  }
  
  public void cleanup(ArrayList<WidgetGroup> paramArrayList)
  {
    int j = this.widgets.size();
    if ((this.moveTo != -1) && (j > 0)) {
      for (int i = 0; i < paramArrayList.size(); i++)
      {
        WidgetGroup localWidgetGroup = (WidgetGroup)paramArrayList.get(i);
        if (this.moveTo == localWidgetGroup.id) {
          moveTo(this.orientation, localWidgetGroup);
        }
      }
    }
    if (j == 0)
    {
      paramArrayList.remove(this);
      return;
    }
  }
  
  public void clear()
  {
    this.widgets.clear();
  }
  
  public int getId()
  {
    return this.id;
  }
  
  public int getOrientation()
  {
    return this.orientation;
  }
  
  public boolean intersectWith(WidgetGroup paramWidgetGroup)
  {
    for (int i = 0; i < this.widgets.size(); i++) {
      if (paramWidgetGroup.contains((ConstraintWidget)this.widgets.get(i))) {
        return true;
      }
    }
    return false;
  }
  
  public boolean isAuthoritative()
  {
    return this.authoritative;
  }
  
  public int measureWrap(LinearSystem paramLinearSystem, int paramInt)
  {
    if (this.widgets.size() == 0) {
      return 0;
    }
    return solverMeasure(paramLinearSystem, this.widgets, paramInt);
  }
  
  public void moveTo(int paramInt, WidgetGroup paramWidgetGroup)
  {
    Iterator localIterator = this.widgets.iterator();
    while (localIterator.hasNext())
    {
      ConstraintWidget localConstraintWidget = (ConstraintWidget)localIterator.next();
      paramWidgetGroup.add(localConstraintWidget);
      if (paramInt == 0) {
        localConstraintWidget.horizontalGroup = paramWidgetGroup.getId();
      } else {
        localConstraintWidget.verticalGroup = paramWidgetGroup.getId();
      }
    }
    this.moveTo = paramWidgetGroup.id;
  }
  
  public void setAuthoritative(boolean paramBoolean)
  {
    this.authoritative = paramBoolean;
  }
  
  public void setOrientation(int paramInt)
  {
    this.orientation = paramInt;
  }
  
  public int size()
  {
    return this.widgets.size();
  }
  
  public String toString()
  {
    String str = getOrientationString() + " [" + this.id + "] <";
    Iterator localIterator = this.widgets.iterator();
    while (localIterator.hasNext())
    {
      ConstraintWidget localConstraintWidget = (ConstraintWidget)localIterator.next();
      str = str + " " + localConstraintWidget.getDebugName();
    }
    return str + " >";
  }
  
  class MeasureResult
  {
    int baseline;
    int bottom;
    int left;
    int orientation;
    int right;
    int top;
    WeakReference<ConstraintWidget> widgetRef;
    
    public MeasureResult(ConstraintWidget paramConstraintWidget, LinearSystem paramLinearSystem, int paramInt)
    {
      this.widgetRef = new WeakReference(paramConstraintWidget);
      this.left = paramLinearSystem.getObjectVariableValue(paramConstraintWidget.mLeft);
      this.top = paramLinearSystem.getObjectVariableValue(paramConstraintWidget.mTop);
      this.right = paramLinearSystem.getObjectVariableValue(paramConstraintWidget.mRight);
      this.bottom = paramLinearSystem.getObjectVariableValue(paramConstraintWidget.mBottom);
      this.baseline = paramLinearSystem.getObjectVariableValue(paramConstraintWidget.mBaseline);
      this.orientation = paramInt;
    }
    
    public void apply()
    {
      ConstraintWidget localConstraintWidget = (ConstraintWidget)this.widgetRef.get();
      if (localConstraintWidget != null) {
        localConstraintWidget.setFinalFrame(this.left, this.top, this.right, this.bottom, this.baseline, this.orientation);
      }
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/constraintlayout/core/widgets/analyzer/WidgetGroup.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */