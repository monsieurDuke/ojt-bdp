package androidx.constraintlayout.core.widgets.analyzer;

import androidx.constraintlayout.core.Metrics;
import androidx.constraintlayout.core.widgets.Barrier;
import androidx.constraintlayout.core.widgets.ConstraintAnchor;
import androidx.constraintlayout.core.widgets.ConstraintAnchor.Type;
import androidx.constraintlayout.core.widgets.ConstraintWidget;
import androidx.constraintlayout.core.widgets.ConstraintWidget.DimensionBehaviour;
import androidx.constraintlayout.core.widgets.ConstraintWidgetContainer;
import androidx.constraintlayout.core.widgets.Flow;
import androidx.constraintlayout.core.widgets.Guideline;
import androidx.constraintlayout.core.widgets.HelperWidget;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

public class Grouping
{
  private static final boolean DEBUG = false;
  private static final boolean DEBUG_GROUPING = false;
  
  public static WidgetGroup findDependents(ConstraintWidget paramConstraintWidget, int paramInt, ArrayList<WidgetGroup> paramArrayList, WidgetGroup paramWidgetGroup)
  {
    int i;
    if (paramInt == 0) {
      i = paramConstraintWidget.horizontalGroup;
    } else {
      i = paramConstraintWidget.verticalGroup;
    }
    int j;
    Object localObject;
    if ((i != -1) && ((paramWidgetGroup == null) || (i != paramWidgetGroup.id)))
    {
      for (j = 0;; j++)
      {
        localObject = paramWidgetGroup;
        if (j >= paramArrayList.size()) {
          break;
        }
        localObject = (WidgetGroup)paramArrayList.get(j);
        if (((WidgetGroup)localObject).getId() == i)
        {
          if (paramWidgetGroup != null)
          {
            paramWidgetGroup.moveTo(paramInt, (WidgetGroup)localObject);
            paramArrayList.remove(paramWidgetGroup);
          }
          break;
        }
      }
    }
    else
    {
      localObject = paramWidgetGroup;
      if (i != -1) {
        return paramWidgetGroup;
      }
    }
    paramWidgetGroup = (WidgetGroup)localObject;
    if (localObject == null)
    {
      paramWidgetGroup = (WidgetGroup)localObject;
      if ((paramConstraintWidget instanceof HelperWidget))
      {
        j = ((HelperWidget)paramConstraintWidget).findGroupInDependents(paramInt);
        paramWidgetGroup = (WidgetGroup)localObject;
        if (j != -1) {
          for (i = 0;; i++)
          {
            paramWidgetGroup = (WidgetGroup)localObject;
            if (i >= paramArrayList.size()) {
              break;
            }
            paramWidgetGroup = (WidgetGroup)paramArrayList.get(i);
            if (paramWidgetGroup.getId() == j) {
              break;
            }
          }
        }
      }
      localObject = paramWidgetGroup;
      if (paramWidgetGroup == null) {
        localObject = new WidgetGroup(paramInt);
      }
      paramArrayList.add(localObject);
      paramWidgetGroup = (WidgetGroup)localObject;
    }
    if (paramWidgetGroup.add(paramConstraintWidget))
    {
      if ((paramConstraintWidget instanceof Guideline))
      {
        Guideline localGuideline = (Guideline)paramConstraintWidget;
        localObject = localGuideline.getAnchor();
        if (localGuideline.getOrientation() == 0) {
          i = 1;
        } else {
          i = 0;
        }
        ((ConstraintAnchor)localObject).findDependents(i, paramArrayList, paramWidgetGroup);
      }
      if (paramInt == 0)
      {
        paramConstraintWidget.horizontalGroup = paramWidgetGroup.getId();
        paramConstraintWidget.mLeft.findDependents(paramInt, paramArrayList, paramWidgetGroup);
        paramConstraintWidget.mRight.findDependents(paramInt, paramArrayList, paramWidgetGroup);
      }
      else
      {
        paramConstraintWidget.verticalGroup = paramWidgetGroup.getId();
        paramConstraintWidget.mTop.findDependents(paramInt, paramArrayList, paramWidgetGroup);
        paramConstraintWidget.mBaseline.findDependents(paramInt, paramArrayList, paramWidgetGroup);
        paramConstraintWidget.mBottom.findDependents(paramInt, paramArrayList, paramWidgetGroup);
      }
      paramConstraintWidget.mCenter.findDependents(paramInt, paramArrayList, paramWidgetGroup);
    }
    return paramWidgetGroup;
  }
  
  private static WidgetGroup findGroup(ArrayList<WidgetGroup> paramArrayList, int paramInt)
  {
    int j = paramArrayList.size();
    for (int i = 0; i < j; i++)
    {
      WidgetGroup localWidgetGroup = (WidgetGroup)paramArrayList.get(i);
      if (paramInt == localWidgetGroup.id) {
        return localWidgetGroup;
      }
    }
    return null;
  }
  
  public static boolean simpleSolvingPass(ConstraintWidgetContainer paramConstraintWidgetContainer, BasicMeasure.Measurer paramMeasurer)
  {
    ArrayList localArrayList = paramConstraintWidgetContainer.getChildren();
    int j = localArrayList.size();
    Object localObject4 = null;
    Object localObject2 = null;
    Object localObject3 = null;
    Object localObject1 = null;
    Object localObject6 = null;
    Object localObject5 = null;
    for (int i = 0; i < j; i++)
    {
      localObject7 = (ConstraintWidget)localArrayList.get(i);
      if (!validInGroup(paramConstraintWidgetContainer.getHorizontalDimensionBehaviour(), paramConstraintWidgetContainer.getVerticalDimensionBehaviour(), ((ConstraintWidget)localObject7).getHorizontalDimensionBehaviour(), ((ConstraintWidget)localObject7).getVerticalDimensionBehaviour())) {
        return false;
      }
      if ((localObject7 instanceof Flow)) {
        return false;
      }
    }
    if (paramConstraintWidgetContainer.mMetrics != null)
    {
      localObject7 = paramConstraintWidgetContainer.mMetrics;
      ((Metrics)localObject7).grouping += 1L;
    }
    i = 0;
    Object localObject8;
    while (i < j)
    {
      ConstraintWidget localConstraintWidget = (ConstraintWidget)localArrayList.get(i);
      if (!validInGroup(paramConstraintWidgetContainer.getHorizontalDimensionBehaviour(), paramConstraintWidgetContainer.getVerticalDimensionBehaviour(), localConstraintWidget.getHorizontalDimensionBehaviour(), localConstraintWidget.getVerticalDimensionBehaviour())) {
        ConstraintWidgetContainer.measure(0, localConstraintWidget, paramMeasurer, paramConstraintWidgetContainer.mMeasure, BasicMeasure.Measure.SELF_DIMENSIONS);
      }
      Object localObject10 = localObject4;
      Object localObject9 = localObject2;
      if ((localConstraintWidget instanceof Guideline))
      {
        localObject8 = (Guideline)localConstraintWidget;
        localObject7 = localObject2;
        if (((Guideline)localObject8).getOrientation() == 0)
        {
          localObject7 = localObject2;
          if (localObject2 == null) {
            localObject7 = new ArrayList();
          }
          ((ArrayList)localObject7).add(localObject8);
        }
        localObject10 = localObject4;
        localObject9 = localObject7;
        if (((Guideline)localObject8).getOrientation() == 1)
        {
          localObject2 = localObject4;
          if (localObject4 == null) {
            localObject2 = new ArrayList();
          }
          ((ArrayList)localObject2).add(localObject8);
          localObject9 = localObject7;
          localObject10 = localObject2;
        }
      }
      localObject8 = localObject3;
      localObject7 = localObject1;
      if ((localConstraintWidget instanceof HelperWidget)) {
        if ((localConstraintWidget instanceof Barrier))
        {
          localObject2 = (Barrier)localConstraintWidget;
          localObject8 = localObject3;
          if (((Barrier)localObject2).getOrientation() == 0)
          {
            localObject8 = localObject3;
            if (localObject3 == null) {
              localObject8 = new ArrayList();
            }
            ((ArrayList)localObject8).add(localObject2);
          }
          localObject7 = localObject1;
          if (((Barrier)localObject2).getOrientation() == 1)
          {
            localObject7 = localObject1;
            if (localObject1 == null) {
              localObject7 = new ArrayList();
            }
            ((ArrayList)localObject7).add(localObject2);
          }
        }
        else
        {
          localObject2 = (HelperWidget)localConstraintWidget;
          localObject8 = localObject3;
          if (localObject3 == null) {
            localObject8 = new ArrayList();
          }
          ((ArrayList)localObject8).add(localObject2);
          localObject7 = localObject1;
          if (localObject1 == null) {
            localObject7 = new ArrayList();
          }
          ((ArrayList)localObject7).add(localObject2);
        }
      }
      Object localObject11 = localObject6;
      if (localConstraintWidget.mLeft.mTarget == null)
      {
        localObject11 = localObject6;
        if (localConstraintWidget.mRight.mTarget == null)
        {
          localObject11 = localObject6;
          if (!(localConstraintWidget instanceof Guideline))
          {
            localObject11 = localObject6;
            if (!(localConstraintWidget instanceof Barrier))
            {
              localObject1 = localObject6;
              if (localObject6 == null) {
                localObject1 = new ArrayList();
              }
              ((ArrayList)localObject1).add(localConstraintWidget);
              localObject11 = localObject1;
            }
          }
        }
      }
      Object localObject12 = localObject5;
      if (localConstraintWidget.mTop.mTarget == null)
      {
        localObject12 = localObject5;
        if (localConstraintWidget.mBottom.mTarget == null)
        {
          localObject12 = localObject5;
          if (localConstraintWidget.mBaseline.mTarget == null)
          {
            localObject12 = localObject5;
            if (!(localConstraintWidget instanceof Guideline))
            {
              localObject12 = localObject5;
              if (!(localConstraintWidget instanceof Barrier))
              {
                localObject1 = localObject5;
                if (localObject5 == null) {
                  localObject1 = new ArrayList();
                }
                ((ArrayList)localObject1).add(localConstraintWidget);
                localObject12 = localObject1;
              }
            }
          }
        }
      }
      i++;
      localObject4 = localObject10;
      localObject2 = localObject9;
      localObject3 = localObject8;
      localObject1 = localObject7;
      localObject6 = localObject11;
      localObject5 = localObject12;
    }
    Object localObject7 = new ArrayList();
    if (localObject4 != null)
    {
      paramMeasurer = ((ArrayList)localObject4).iterator();
      while (paramMeasurer.hasNext()) {
        findDependents((Guideline)paramMeasurer.next(), 0, (ArrayList)localObject7, null);
      }
    }
    if (localObject3 != null)
    {
      paramMeasurer = ((ArrayList)localObject3).iterator();
      while (paramMeasurer.hasNext())
      {
        localObject8 = (HelperWidget)paramMeasurer.next();
        localObject3 = findDependents((ConstraintWidget)localObject8, 0, (ArrayList)localObject7, null);
        ((HelperWidget)localObject8).addDependents((ArrayList)localObject7, 0, (WidgetGroup)localObject3);
        ((WidgetGroup)localObject3).cleanup((ArrayList)localObject7);
      }
    }
    paramMeasurer = paramConstraintWidgetContainer.getAnchor(ConstraintAnchor.Type.LEFT);
    if (paramMeasurer.getDependents() != null)
    {
      localObject3 = paramMeasurer.getDependents().iterator();
      while (((Iterator)localObject3).hasNext()) {
        findDependents(((ConstraintAnchor)((Iterator)localObject3).next()).mOwner, 0, (ArrayList)localObject7, null);
      }
    }
    paramMeasurer = paramConstraintWidgetContainer.getAnchor(ConstraintAnchor.Type.RIGHT);
    if (paramMeasurer.getDependents() != null)
    {
      localObject3 = paramMeasurer.getDependents().iterator();
      while (((Iterator)localObject3).hasNext()) {
        findDependents(((ConstraintAnchor)((Iterator)localObject3).next()).mOwner, 0, (ArrayList)localObject7, null);
      }
    }
    paramMeasurer = paramConstraintWidgetContainer.getAnchor(ConstraintAnchor.Type.CENTER);
    if (paramMeasurer.getDependents() != null)
    {
      localObject3 = paramMeasurer.getDependents().iterator();
      while (((Iterator)localObject3).hasNext()) {
        findDependents(((ConstraintAnchor)((Iterator)localObject3).next()).mOwner, 0, (ArrayList)localObject7, null);
      }
    }
    if (localObject6 != null)
    {
      paramMeasurer = ((ArrayList)localObject6).iterator();
      while (paramMeasurer.hasNext()) {
        findDependents((ConstraintWidget)paramMeasurer.next(), 0, (ArrayList)localObject7, null);
      }
    }
    if (localObject2 != null)
    {
      paramMeasurer = ((ArrayList)localObject2).iterator();
      while (paramMeasurer.hasNext()) {
        findDependents((Guideline)paramMeasurer.next(), 1, (ArrayList)localObject7, null);
      }
    }
    if (localObject1 != null)
    {
      localObject1 = ((ArrayList)localObject1).iterator();
      while (((Iterator)localObject1).hasNext())
      {
        localObject3 = (HelperWidget)((Iterator)localObject1).next();
        paramMeasurer = findDependents((ConstraintWidget)localObject3, 1, (ArrayList)localObject7, null);
        ((HelperWidget)localObject3).addDependents((ArrayList)localObject7, 1, paramMeasurer);
        paramMeasurer.cleanup((ArrayList)localObject7);
      }
    }
    paramMeasurer = paramConstraintWidgetContainer.getAnchor(ConstraintAnchor.Type.TOP);
    if (paramMeasurer.getDependents() != null)
    {
      paramMeasurer = paramMeasurer.getDependents().iterator();
      while (paramMeasurer.hasNext()) {
        findDependents(((ConstraintAnchor)paramMeasurer.next()).mOwner, 1, (ArrayList)localObject7, null);
      }
    }
    paramMeasurer = paramConstraintWidgetContainer.getAnchor(ConstraintAnchor.Type.BASELINE);
    if (paramMeasurer.getDependents() != null)
    {
      localObject1 = paramMeasurer.getDependents().iterator();
      while (((Iterator)localObject1).hasNext()) {
        findDependents(((ConstraintAnchor)((Iterator)localObject1).next()).mOwner, 1, (ArrayList)localObject7, null);
      }
    }
    paramMeasurer = paramConstraintWidgetContainer.getAnchor(ConstraintAnchor.Type.BOTTOM);
    if (paramMeasurer.getDependents() != null)
    {
      localObject1 = paramMeasurer.getDependents().iterator();
      while (((Iterator)localObject1).hasNext()) {
        findDependents(((ConstraintAnchor)((Iterator)localObject1).next()).mOwner, 1, (ArrayList)localObject7, null);
      }
    }
    paramMeasurer = paramConstraintWidgetContainer.getAnchor(ConstraintAnchor.Type.CENTER);
    if (paramMeasurer.getDependents() != null)
    {
      localObject1 = paramMeasurer.getDependents().iterator();
      while (((Iterator)localObject1).hasNext()) {
        findDependents(((ConstraintAnchor)((Iterator)localObject1).next()).mOwner, 1, (ArrayList)localObject7, null);
      }
    }
    if (localObject5 != null)
    {
      paramMeasurer = ((ArrayList)localObject5).iterator();
      while (paramMeasurer.hasNext()) {
        findDependents((ConstraintWidget)paramMeasurer.next(), 1, (ArrayList)localObject7, null);
      }
    }
    for (i = 0; i < j; i++)
    {
      localObject1 = (ConstraintWidget)localArrayList.get(i);
      if (((ConstraintWidget)localObject1).oppositeDimensionsTied())
      {
        paramMeasurer = findGroup((ArrayList)localObject7, ((ConstraintWidget)localObject1).horizontalGroup);
        localObject1 = findGroup((ArrayList)localObject7, ((ConstraintWidget)localObject1).verticalGroup);
        if ((paramMeasurer != null) && (localObject1 != null))
        {
          paramMeasurer.moveTo(0, (WidgetGroup)localObject1);
          ((WidgetGroup)localObject1).setOrientation(2);
          ((ArrayList)localObject7).remove(paramMeasurer);
        }
      }
    }
    if (((ArrayList)localObject7).size() <= 1) {
      return false;
    }
    localObject3 = null;
    localObject2 = null;
    int k;
    if (paramConstraintWidgetContainer.getHorizontalDimensionBehaviour() == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT)
    {
      i = 0;
      paramMeasurer = null;
      localObject5 = ((ArrayList)localObject7).iterator();
      localObject1 = localArrayList;
      while (((Iterator)localObject5).hasNext())
      {
        localObject4 = (WidgetGroup)((Iterator)localObject5).next();
        if (((WidgetGroup)localObject4).getOrientation() != 1)
        {
          ((WidgetGroup)localObject4).setAuthoritative(false);
          k = ((WidgetGroup)localObject4).measureWrap(paramConstraintWidgetContainer.getSystem(), 0);
          j = i;
          if (k > i)
          {
            j = k;
            paramMeasurer = (BasicMeasure.Measurer)localObject4;
          }
          i = j;
        }
      }
      localObject1 = localObject3;
      if (paramMeasurer != null)
      {
        paramConstraintWidgetContainer.setHorizontalDimensionBehaviour(ConstraintWidget.DimensionBehaviour.FIXED);
        paramConstraintWidgetContainer.setWidth(i);
        paramMeasurer.setAuthoritative(true);
        localObject1 = paramMeasurer;
      }
    }
    else
    {
      localObject1 = localObject3;
    }
    if (paramConstraintWidgetContainer.getVerticalDimensionBehaviour() == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT)
    {
      i = 0;
      paramMeasurer = null;
      localObject4 = ((ArrayList)localObject7).iterator();
      while (((Iterator)localObject4).hasNext())
      {
        localObject3 = (WidgetGroup)((Iterator)localObject4).next();
        if (((WidgetGroup)localObject3).getOrientation() != 0)
        {
          ((WidgetGroup)localObject3).setAuthoritative(false);
          k = ((WidgetGroup)localObject3).measureWrap(paramConstraintWidgetContainer.getSystem(), 1);
          j = i;
          if (k > i)
          {
            paramMeasurer = (BasicMeasure.Measurer)localObject3;
            j = k;
          }
          i = j;
        }
      }
      if (paramMeasurer != null)
      {
        paramConstraintWidgetContainer.setVerticalDimensionBehaviour(ConstraintWidget.DimensionBehaviour.FIXED);
        paramConstraintWidgetContainer.setHeight(i);
        paramMeasurer.setAuthoritative(true);
      }
      else
      {
        paramMeasurer = (BasicMeasure.Measurer)localObject2;
      }
    }
    else
    {
      paramMeasurer = (BasicMeasure.Measurer)localObject2;
    }
    boolean bool;
    if ((localObject1 == null) && (paramMeasurer == null)) {
      bool = false;
    } else {
      bool = true;
    }
    return bool;
  }
  
  public static boolean validInGroup(ConstraintWidget.DimensionBehaviour paramDimensionBehaviour1, ConstraintWidget.DimensionBehaviour paramDimensionBehaviour2, ConstraintWidget.DimensionBehaviour paramDimensionBehaviour3, ConstraintWidget.DimensionBehaviour paramDimensionBehaviour4)
  {
    int i;
    if ((paramDimensionBehaviour3 != ConstraintWidget.DimensionBehaviour.FIXED) && (paramDimensionBehaviour3 != ConstraintWidget.DimensionBehaviour.WRAP_CONTENT) && ((paramDimensionBehaviour3 != ConstraintWidget.DimensionBehaviour.MATCH_PARENT) || (paramDimensionBehaviour1 == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT))) {
      i = 0;
    } else {
      i = 1;
    }
    int j;
    if ((paramDimensionBehaviour4 != ConstraintWidget.DimensionBehaviour.FIXED) && (paramDimensionBehaviour4 != ConstraintWidget.DimensionBehaviour.WRAP_CONTENT) && ((paramDimensionBehaviour4 != ConstraintWidget.DimensionBehaviour.MATCH_PARENT) || (paramDimensionBehaviour2 == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT))) {
      j = 0;
    } else {
      j = 1;
    }
    return (i != 0) || (j != 0);
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/constraintlayout/core/widgets/analyzer/Grouping.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */