package androidx.constraintlayout.core.widgets.analyzer;

import androidx.constraintlayout.core.widgets.Barrier;
import androidx.constraintlayout.core.widgets.ConstraintAnchor;
import androidx.constraintlayout.core.widgets.ConstraintWidget;
import androidx.constraintlayout.core.widgets.ConstraintWidget.DimensionBehaviour;
import androidx.constraintlayout.core.widgets.ConstraintWidgetContainer;
import androidx.constraintlayout.core.widgets.Guideline;
import androidx.constraintlayout.core.widgets.HelperWidget;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

public class DependencyGraph
{
  private static final boolean USE_GROUPS = true;
  private ConstraintWidgetContainer container;
  private ConstraintWidgetContainer mContainer;
  ArrayList<RunGroup> mGroups = new ArrayList();
  private BasicMeasure.Measure mMeasure = new BasicMeasure.Measure();
  private BasicMeasure.Measurer mMeasurer = null;
  private boolean mNeedBuildGraph = true;
  private boolean mNeedRedoMeasures = true;
  private ArrayList<WidgetRun> mRuns = new ArrayList();
  private ArrayList<RunGroup> runGroups = new ArrayList();
  
  public DependencyGraph(ConstraintWidgetContainer paramConstraintWidgetContainer)
  {
    this.container = paramConstraintWidgetContainer;
    this.mContainer = paramConstraintWidgetContainer;
  }
  
  private void applyGroup(DependencyNode paramDependencyNode1, int paramInt1, int paramInt2, DependencyNode paramDependencyNode2, ArrayList<RunGroup> paramArrayList, RunGroup paramRunGroup)
  {
    WidgetRun localWidgetRun = paramDependencyNode1.run;
    if ((localWidgetRun.runGroup == null) && (localWidgetRun != this.container.horizontalRun) && (localWidgetRun != this.container.verticalRun))
    {
      if (paramRunGroup == null)
      {
        paramDependencyNode1 = new RunGroup(localWidgetRun, paramInt2);
        paramArrayList.add(paramDependencyNode1);
      }
      else
      {
        paramDependencyNode1 = paramRunGroup;
      }
      localWidgetRun.runGroup = paramDependencyNode1;
      paramDependencyNode1.add(localWidgetRun);
      paramRunGroup = localWidgetRun.start.dependencies.iterator();
      while (paramRunGroup.hasNext())
      {
        localObject = (Dependency)paramRunGroup.next();
        if ((localObject instanceof DependencyNode)) {
          applyGroup((DependencyNode)localObject, paramInt1, 0, paramDependencyNode2, paramArrayList, paramDependencyNode1);
        }
      }
      paramRunGroup = localWidgetRun.end.dependencies.iterator();
      while (paramRunGroup.hasNext())
      {
        localObject = (Dependency)paramRunGroup.next();
        if ((localObject instanceof DependencyNode)) {
          applyGroup((DependencyNode)localObject, paramInt1, 1, paramDependencyNode2, paramArrayList, paramDependencyNode1);
        }
      }
      if ((paramInt1 == 1) && ((localWidgetRun instanceof VerticalWidgetRun)))
      {
        paramRunGroup = ((VerticalWidgetRun)localWidgetRun).baseline.dependencies.iterator();
        while (paramRunGroup.hasNext())
        {
          localObject = (Dependency)paramRunGroup.next();
          if ((localObject instanceof DependencyNode)) {
            applyGroup((DependencyNode)localObject, paramInt1, 2, paramDependencyNode2, paramArrayList, paramDependencyNode1);
          }
        }
      }
      paramRunGroup = localWidgetRun.start.targets.iterator();
      while (paramRunGroup.hasNext())
      {
        localObject = (DependencyNode)paramRunGroup.next();
        if (localObject == paramDependencyNode2) {
          paramDependencyNode1.dual = true;
        }
        applyGroup((DependencyNode)localObject, paramInt1, 0, paramDependencyNode2, paramArrayList, paramDependencyNode1);
      }
      Object localObject = localWidgetRun.end.targets.iterator();
      while (((Iterator)localObject).hasNext())
      {
        paramRunGroup = (DependencyNode)((Iterator)localObject).next();
        if (paramRunGroup == paramDependencyNode2) {
          paramDependencyNode1.dual = true;
        }
        applyGroup(paramRunGroup, paramInt1, 1, paramDependencyNode2, paramArrayList, paramDependencyNode1);
      }
      if ((paramInt1 == 1) && ((localWidgetRun instanceof VerticalWidgetRun)))
      {
        paramRunGroup = ((VerticalWidgetRun)localWidgetRun).baseline.targets.iterator();
        while (paramRunGroup.hasNext()) {
          applyGroup((DependencyNode)paramRunGroup.next(), paramInt1, 2, paramDependencyNode2, paramArrayList, paramDependencyNode1);
        }
      }
      return;
    }
  }
  
  private boolean basicMeasureWidgets(ConstraintWidgetContainer paramConstraintWidgetContainer)
  {
    Iterator localIterator = paramConstraintWidgetContainer.mChildren.iterator();
    while (localIterator.hasNext())
    {
      ConstraintWidget localConstraintWidget = (ConstraintWidget)localIterator.next();
      ConstraintWidget.DimensionBehaviour localDimensionBehaviour1 = localConstraintWidget.mListDimensionBehaviors[0];
      ConstraintWidget.DimensionBehaviour localDimensionBehaviour2 = localConstraintWidget.mListDimensionBehaviors[1];
      if (localConstraintWidget.getVisibility() == 8)
      {
        localConstraintWidget.measured = true;
      }
      else
      {
        if ((localConstraintWidget.mMatchConstraintPercentWidth < 1.0F) && (localDimensionBehaviour1 == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT)) {
          localConstraintWidget.mMatchConstraintDefaultWidth = 2;
        }
        if ((localConstraintWidget.mMatchConstraintPercentHeight < 1.0F) && (localDimensionBehaviour2 == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT)) {
          localConstraintWidget.mMatchConstraintDefaultHeight = 2;
        }
        if (localConstraintWidget.getDimensionRatio() > 0.0F) {
          if ((localDimensionBehaviour1 == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) && ((localDimensionBehaviour2 == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT) || (localDimensionBehaviour2 == ConstraintWidget.DimensionBehaviour.FIXED)))
          {
            localConstraintWidget.mMatchConstraintDefaultWidth = 3;
          }
          else if ((localDimensionBehaviour2 == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) && ((localDimensionBehaviour1 == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT) || (localDimensionBehaviour1 == ConstraintWidget.DimensionBehaviour.FIXED)))
          {
            localConstraintWidget.mMatchConstraintDefaultHeight = 3;
          }
          else if ((localDimensionBehaviour1 == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) && (localDimensionBehaviour2 == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT))
          {
            if (localConstraintWidget.mMatchConstraintDefaultWidth == 0) {
              localConstraintWidget.mMatchConstraintDefaultWidth = 3;
            }
            if (localConstraintWidget.mMatchConstraintDefaultHeight == 0) {
              localConstraintWidget.mMatchConstraintDefaultHeight = 3;
            }
          }
        }
        if ((localDimensionBehaviour1 == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) && (localConstraintWidget.mMatchConstraintDefaultWidth == 1) && ((localConstraintWidget.mLeft.mTarget == null) || (localConstraintWidget.mRight.mTarget == null))) {
          localDimensionBehaviour1 = ConstraintWidget.DimensionBehaviour.WRAP_CONTENT;
        }
        if ((localDimensionBehaviour2 == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) && (localConstraintWidget.mMatchConstraintDefaultHeight == 1) && ((localConstraintWidget.mTop.mTarget == null) || (localConstraintWidget.mBottom.mTarget == null))) {
          localDimensionBehaviour2 = ConstraintWidget.DimensionBehaviour.WRAP_CONTENT;
        }
        localConstraintWidget.horizontalRun.dimensionBehavior = localDimensionBehaviour1;
        localConstraintWidget.horizontalRun.matchConstraintsType = localConstraintWidget.mMatchConstraintDefaultWidth;
        localConstraintWidget.verticalRun.dimensionBehavior = localDimensionBehaviour2;
        localConstraintWidget.verticalRun.matchConstraintsType = localConstraintWidget.mMatchConstraintDefaultHeight;
        int i;
        int j;
        if (((localDimensionBehaviour1 != ConstraintWidget.DimensionBehaviour.MATCH_PARENT) && (localDimensionBehaviour1 != ConstraintWidget.DimensionBehaviour.FIXED) && (localDimensionBehaviour1 != ConstraintWidget.DimensionBehaviour.WRAP_CONTENT)) || ((localDimensionBehaviour2 != ConstraintWidget.DimensionBehaviour.MATCH_PARENT) && (localDimensionBehaviour2 != ConstraintWidget.DimensionBehaviour.FIXED) && (localDimensionBehaviour2 != ConstraintWidget.DimensionBehaviour.WRAP_CONTENT)))
        {
          float f1;
          if ((localDimensionBehaviour1 == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) && ((localDimensionBehaviour2 == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT) || (localDimensionBehaviour2 == ConstraintWidget.DimensionBehaviour.FIXED)))
          {
            if (localConstraintWidget.mMatchConstraintDefaultWidth == 3)
            {
              if (localDimensionBehaviour2 == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT) {
                measure(localConstraintWidget, ConstraintWidget.DimensionBehaviour.WRAP_CONTENT, 0, ConstraintWidget.DimensionBehaviour.WRAP_CONTENT, 0);
              }
              i = localConstraintWidget.getHeight();
              j = (int)(i * localConstraintWidget.mDimensionRatio + 0.5F);
              measure(localConstraintWidget, ConstraintWidget.DimensionBehaviour.FIXED, j, ConstraintWidget.DimensionBehaviour.FIXED, i);
              localConstraintWidget.horizontalRun.dimension.resolve(localConstraintWidget.getWidth());
              localConstraintWidget.verticalRun.dimension.resolve(localConstraintWidget.getHeight());
              localConstraintWidget.measured = true;
              continue;
            }
            if (localConstraintWidget.mMatchConstraintDefaultWidth == 1)
            {
              measure(localConstraintWidget, ConstraintWidget.DimensionBehaviour.WRAP_CONTENT, 0, localDimensionBehaviour2, 0);
              localConstraintWidget.horizontalRun.dimension.wrapValue = localConstraintWidget.getWidth();
              continue;
            }
            if (localConstraintWidget.mMatchConstraintDefaultWidth == 2)
            {
              if ((paramConstraintWidgetContainer.mListDimensionBehaviors[0] == ConstraintWidget.DimensionBehaviour.FIXED) || (paramConstraintWidgetContainer.mListDimensionBehaviors[0] == ConstraintWidget.DimensionBehaviour.MATCH_PARENT))
              {
                f1 = localConstraintWidget.mMatchConstraintPercentWidth;
                i = (int)(paramConstraintWidgetContainer.getWidth() * f1 + 0.5F);
                j = localConstraintWidget.getHeight();
                measure(localConstraintWidget, ConstraintWidget.DimensionBehaviour.FIXED, i, localDimensionBehaviour2, j);
                localConstraintWidget.horizontalRun.dimension.resolve(localConstraintWidget.getWidth());
                localConstraintWidget.verticalRun.dimension.resolve(localConstraintWidget.getHeight());
                localConstraintWidget.measured = true;
              }
            }
            else if ((localConstraintWidget.mListAnchors[0].mTarget == null) || (localConstraintWidget.mListAnchors[1].mTarget == null))
            {
              measure(localConstraintWidget, ConstraintWidget.DimensionBehaviour.WRAP_CONTENT, 0, localDimensionBehaviour2, 0);
              localConstraintWidget.horizontalRun.dimension.resolve(localConstraintWidget.getWidth());
              localConstraintWidget.verticalRun.dimension.resolve(localConstraintWidget.getHeight());
              localConstraintWidget.measured = true;
              continue;
            }
          }
          if ((localDimensionBehaviour2 == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) && ((localDimensionBehaviour1 == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT) || (localDimensionBehaviour1 == ConstraintWidget.DimensionBehaviour.FIXED)))
          {
            if (localConstraintWidget.mMatchConstraintDefaultHeight == 3)
            {
              if (localDimensionBehaviour1 == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT) {
                measure(localConstraintWidget, ConstraintWidget.DimensionBehaviour.WRAP_CONTENT, 0, ConstraintWidget.DimensionBehaviour.WRAP_CONTENT, 0);
              }
              j = localConstraintWidget.getWidth();
              f1 = localConstraintWidget.mDimensionRatio;
              if (localConstraintWidget.getDimensionRatioSide() == -1) {
                f1 = 1.0F / f1;
              }
              i = (int)(j * f1 + 0.5F);
              measure(localConstraintWidget, ConstraintWidget.DimensionBehaviour.FIXED, j, ConstraintWidget.DimensionBehaviour.FIXED, i);
              localConstraintWidget.horizontalRun.dimension.resolve(localConstraintWidget.getWidth());
              localConstraintWidget.verticalRun.dimension.resolve(localConstraintWidget.getHeight());
              localConstraintWidget.measured = true;
              continue;
            }
            if (localConstraintWidget.mMatchConstraintDefaultHeight == 1)
            {
              measure(localConstraintWidget, localDimensionBehaviour1, 0, ConstraintWidget.DimensionBehaviour.WRAP_CONTENT, 0);
              localConstraintWidget.verticalRun.dimension.wrapValue = localConstraintWidget.getHeight();
              continue;
            }
            if (localConstraintWidget.mMatchConstraintDefaultHeight == 2)
            {
              if ((paramConstraintWidgetContainer.mListDimensionBehaviors[1] == ConstraintWidget.DimensionBehaviour.FIXED) || (paramConstraintWidgetContainer.mListDimensionBehaviors[1] == ConstraintWidget.DimensionBehaviour.MATCH_PARENT))
              {
                f1 = localConstraintWidget.mMatchConstraintPercentHeight;
                i = localConstraintWidget.getWidth();
                j = (int)(paramConstraintWidgetContainer.getHeight() * f1 + 0.5F);
                measure(localConstraintWidget, localDimensionBehaviour1, i, ConstraintWidget.DimensionBehaviour.FIXED, j);
                localConstraintWidget.horizontalRun.dimension.resolve(localConstraintWidget.getWidth());
                localConstraintWidget.verticalRun.dimension.resolve(localConstraintWidget.getHeight());
                localConstraintWidget.measured = true;
              }
            }
            else if ((localConstraintWidget.mListAnchors[2].mTarget == null) || (localConstraintWidget.mListAnchors[3].mTarget == null))
            {
              measure(localConstraintWidget, ConstraintWidget.DimensionBehaviour.WRAP_CONTENT, 0, localDimensionBehaviour2, 0);
              localConstraintWidget.horizontalRun.dimension.resolve(localConstraintWidget.getWidth());
              localConstraintWidget.verticalRun.dimension.resolve(localConstraintWidget.getHeight());
              localConstraintWidget.measured = true;
              continue;
            }
          }
          if ((localDimensionBehaviour1 == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) && (localDimensionBehaviour2 == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT)) {
            if ((localConstraintWidget.mMatchConstraintDefaultWidth != 1) && (localConstraintWidget.mMatchConstraintDefaultHeight != 1))
            {
              if ((localConstraintWidget.mMatchConstraintDefaultHeight == 2) && (localConstraintWidget.mMatchConstraintDefaultWidth == 2) && (paramConstraintWidgetContainer.mListDimensionBehaviors[0] == ConstraintWidget.DimensionBehaviour.FIXED) && (paramConstraintWidgetContainer.mListDimensionBehaviors[1] == ConstraintWidget.DimensionBehaviour.FIXED))
              {
                f1 = localConstraintWidget.mMatchConstraintPercentWidth;
                float f2 = localConstraintWidget.mMatchConstraintPercentHeight;
                i = (int)(paramConstraintWidgetContainer.getWidth() * f1 + 0.5F);
                j = (int)(paramConstraintWidgetContainer.getHeight() * f2 + 0.5F);
                measure(localConstraintWidget, ConstraintWidget.DimensionBehaviour.FIXED, i, ConstraintWidget.DimensionBehaviour.FIXED, j);
                localConstraintWidget.horizontalRun.dimension.resolve(localConstraintWidget.getWidth());
                localConstraintWidget.verticalRun.dimension.resolve(localConstraintWidget.getHeight());
                localConstraintWidget.measured = true;
              }
            }
            else
            {
              measure(localConstraintWidget, ConstraintWidget.DimensionBehaviour.WRAP_CONTENT, 0, ConstraintWidget.DimensionBehaviour.WRAP_CONTENT, 0);
              localConstraintWidget.horizontalRun.dimension.wrapValue = localConstraintWidget.getWidth();
              localConstraintWidget.verticalRun.dimension.wrapValue = localConstraintWidget.getHeight();
            }
          }
        }
        else
        {
          i = localConstraintWidget.getWidth();
          ConstraintWidget.DimensionBehaviour localDimensionBehaviour3 = localDimensionBehaviour1;
          if (localDimensionBehaviour1 == ConstraintWidget.DimensionBehaviour.MATCH_PARENT)
          {
            i = paramConstraintWidgetContainer.getWidth() - localConstraintWidget.mLeft.mMargin - localConstraintWidget.mRight.mMargin;
            localDimensionBehaviour3 = ConstraintWidget.DimensionBehaviour.FIXED;
          }
          j = localConstraintWidget.getHeight();
          if (localDimensionBehaviour2 == ConstraintWidget.DimensionBehaviour.MATCH_PARENT)
          {
            j = paramConstraintWidgetContainer.getHeight();
            int k = localConstraintWidget.mTop.mMargin;
            int m = localConstraintWidget.mBottom.mMargin;
            localDimensionBehaviour2 = ConstraintWidget.DimensionBehaviour.FIXED;
            j = j - k - m;
          }
          measure(localConstraintWidget, localDimensionBehaviour3, i, localDimensionBehaviour2, j);
          localConstraintWidget.horizontalRun.dimension.resolve(localConstraintWidget.getWidth());
          localConstraintWidget.verticalRun.dimension.resolve(localConstraintWidget.getHeight());
          localConstraintWidget.measured = true;
        }
      }
    }
    return false;
  }
  
  private int computeWrap(ConstraintWidgetContainer paramConstraintWidgetContainer, int paramInt)
  {
    int j = this.mGroups.size();
    long l = 0L;
    for (int i = 0; i < j; i++) {
      l = Math.max(l, ((RunGroup)this.mGroups.get(i)).computeWrapSize(paramConstraintWidgetContainer, paramInt));
    }
    return (int)l;
  }
  
  private void displayGraph()
  {
    String str = "digraph {\n";
    Iterator localIterator = this.mRuns.iterator();
    while (localIterator.hasNext()) {
      str = generateDisplayGraph((WidgetRun)localIterator.next(), str);
    }
    str = str + "\n}\n";
    System.out.println("content:<<\n" + str + "\n>>");
  }
  
  private void findGroup(WidgetRun paramWidgetRun, int paramInt, ArrayList<RunGroup> paramArrayList)
  {
    Object localObject1 = paramWidgetRun.start.dependencies.iterator();
    while (((Iterator)localObject1).hasNext())
    {
      localObject2 = (Dependency)((Iterator)localObject1).next();
      if ((localObject2 instanceof DependencyNode)) {
        applyGroup((DependencyNode)localObject2, paramInt, 0, paramWidgetRun.end, paramArrayList, null);
      } else if ((localObject2 instanceof WidgetRun)) {
        applyGroup(((WidgetRun)localObject2).start, paramInt, 0, paramWidgetRun.end, paramArrayList, null);
      }
    }
    Object localObject2 = paramWidgetRun.end.dependencies.iterator();
    while (((Iterator)localObject2).hasNext())
    {
      localObject1 = (Dependency)((Iterator)localObject2).next();
      if ((localObject1 instanceof DependencyNode)) {
        applyGroup((DependencyNode)localObject1, paramInt, 1, paramWidgetRun.start, paramArrayList, null);
      } else if ((localObject1 instanceof WidgetRun)) {
        applyGroup(((WidgetRun)localObject1).end, paramInt, 1, paramWidgetRun.start, paramArrayList, null);
      }
    }
    if (paramInt == 1)
    {
      localObject1 = ((VerticalWidgetRun)paramWidgetRun).baseline.dependencies.iterator();
      while (((Iterator)localObject1).hasNext())
      {
        paramWidgetRun = (Dependency)((Iterator)localObject1).next();
        if ((paramWidgetRun instanceof DependencyNode)) {
          applyGroup((DependencyNode)paramWidgetRun, paramInt, 2, null, paramArrayList, null);
        }
      }
    }
  }
  
  private String generateChainDisplayGraph(ChainRun paramChainRun, String paramString)
  {
    int i = paramChainRun.orientation;
    StringBuilder localStringBuilder = new StringBuilder("subgraph ");
    localStringBuilder.append("cluster_");
    localStringBuilder.append(paramChainRun.widget.getDebugName());
    if (i == 0) {
      localStringBuilder.append("_h");
    } else {
      localStringBuilder.append("_v");
    }
    localStringBuilder.append(" {\n");
    Object localObject = "";
    Iterator localIterator = paramChainRun.widgets.iterator();
    for (paramChainRun = (ChainRun)localObject; localIterator.hasNext(); paramChainRun = generateDisplayGraph((WidgetRun)localObject, paramChainRun))
    {
      localObject = (WidgetRun)localIterator.next();
      localStringBuilder.append(((WidgetRun)localObject).widget.getDebugName());
      if (i == 0) {
        localStringBuilder.append("_HORIZONTAL");
      } else {
        localStringBuilder.append("_VERTICAL");
      }
      localStringBuilder.append(";\n");
    }
    localStringBuilder.append("}\n");
    return paramString + paramChainRun + localStringBuilder;
  }
  
  private String generateDisplayGraph(WidgetRun paramWidgetRun, String paramString)
  {
    DependencyNode localDependencyNode1 = paramWidgetRun.start;
    DependencyNode localDependencyNode2 = paramWidgetRun.end;
    StringBuilder localStringBuilder = new StringBuilder(paramString);
    if ((!(paramWidgetRun instanceof HelperReferences)) && (localDependencyNode1.dependencies.isEmpty()) && ((localDependencyNode2.dependencies.isEmpty() & localDependencyNode1.targets.isEmpty())) && (localDependencyNode2.targets.isEmpty())) {
      return paramString;
    }
    localStringBuilder.append(nodeDefinition(paramWidgetRun));
    boolean bool = isCenteredConnection(localDependencyNode1, localDependencyNode2);
    Object localObject = generateDisplayNode(localDependencyNode2, bool, generateDisplayNode(localDependencyNode1, bool, paramString));
    paramString = (String)localObject;
    if ((paramWidgetRun instanceof VerticalWidgetRun)) {
      paramString = generateDisplayNode(((VerticalWidgetRun)paramWidgetRun).baseline, bool, (String)localObject);
    }
    if ((!(paramWidgetRun instanceof HorizontalWidgetRun)) && ((!(paramWidgetRun instanceof ChainRun)) || (((ChainRun)paramWidgetRun).orientation != 0))) {
      if (((paramWidgetRun instanceof VerticalWidgetRun)) || (((paramWidgetRun instanceof ChainRun)) && (((ChainRun)paramWidgetRun).orientation == 1))) {}
    }
    for (;;)
    {
      break;
      localObject = paramWidgetRun.widget.getVerticalDimensionBehaviour();
      if ((localObject != ConstraintWidget.DimensionBehaviour.FIXED) && (localObject != ConstraintWidget.DimensionBehaviour.WRAP_CONTENT))
      {
        if ((localObject == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) && (paramWidgetRun.widget.getDimensionRatio() > 0.0F))
        {
          localStringBuilder.append("\n");
          localStringBuilder.append(paramWidgetRun.widget.getDebugName());
          localStringBuilder.append("_VERTICAL -> ");
          localStringBuilder.append(paramWidgetRun.widget.getDebugName());
          localStringBuilder.append("_HORIZONTAL;\n");
        }
      }
      else if ((!localDependencyNode1.targets.isEmpty()) && (localDependencyNode2.targets.isEmpty()))
      {
        localStringBuilder.append("\n");
        localStringBuilder.append(localDependencyNode2.name());
        localStringBuilder.append(" -> ");
        localStringBuilder.append(localDependencyNode1.name());
        localStringBuilder.append("\n");
      }
      else if ((localDependencyNode1.targets.isEmpty()) && (!localDependencyNode2.targets.isEmpty()))
      {
        localStringBuilder.append("\n");
        localStringBuilder.append(localDependencyNode1.name());
        localStringBuilder.append(" -> ");
        localStringBuilder.append(localDependencyNode2.name());
        localStringBuilder.append("\n");
        break;
        localObject = paramWidgetRun.widget.getHorizontalDimensionBehaviour();
        if ((localObject != ConstraintWidget.DimensionBehaviour.FIXED) && (localObject != ConstraintWidget.DimensionBehaviour.WRAP_CONTENT))
        {
          if ((localObject != ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) || (paramWidgetRun.widget.getDimensionRatio() <= 0.0F)) {
            continue;
          }
          localStringBuilder.append("\n");
          localStringBuilder.append(paramWidgetRun.widget.getDebugName());
          localStringBuilder.append("_HORIZONTAL -> ");
          localStringBuilder.append(paramWidgetRun.widget.getDebugName());
          localStringBuilder.append("_VERTICAL;\n");
          continue;
        }
        if ((!localDependencyNode1.targets.isEmpty()) && (localDependencyNode2.targets.isEmpty()))
        {
          localStringBuilder.append("\n");
          localStringBuilder.append(localDependencyNode2.name());
          localStringBuilder.append(" -> ");
          localStringBuilder.append(localDependencyNode1.name());
          localStringBuilder.append("\n");
          continue;
        }
        if ((!localDependencyNode1.targets.isEmpty()) || (localDependencyNode2.targets.isEmpty())) {
          continue;
        }
        localStringBuilder.append("\n");
        localStringBuilder.append(localDependencyNode1.name());
        localStringBuilder.append(" -> ");
        localStringBuilder.append(localDependencyNode2.name());
        localStringBuilder.append("\n");
      }
    }
    if ((paramWidgetRun instanceof ChainRun)) {
      return generateChainDisplayGraph((ChainRun)paramWidgetRun, paramString);
    }
    return localStringBuilder.toString();
  }
  
  private String generateDisplayNode(DependencyNode paramDependencyNode, boolean paramBoolean, String paramString)
  {
    StringBuilder localStringBuilder = new StringBuilder(paramString);
    Iterator localIterator = paramDependencyNode.targets.iterator();
    while (localIterator.hasNext())
    {
      Object localObject = (DependencyNode)localIterator.next();
      paramString = "\n" + paramDependencyNode.name();
      localObject = paramString + " -> " + ((DependencyNode)localObject).name();
      if ((paramDependencyNode.margin <= 0) && (!paramBoolean))
      {
        paramString = (String)localObject;
        if (!(paramDependencyNode.run instanceof HelperReferences)) {}
      }
      else
      {
        localObject = (String)localObject + "[";
        paramString = (String)localObject;
        if (paramDependencyNode.margin > 0)
        {
          localObject = (String)localObject + "label=\"" + paramDependencyNode.margin + "\"";
          paramString = (String)localObject;
          if (paramBoolean) {
            paramString = (String)localObject + ",";
          }
        }
        localObject = paramString;
        if (paramBoolean) {
          localObject = paramString + " style=dashed ";
        }
        paramString = (String)localObject;
        if ((paramDependencyNode.run instanceof HelperReferences)) {
          paramString = (String)localObject + " style=bold,color=gray ";
        }
        paramString = paramString + "]";
      }
      localStringBuilder.append(paramString + "\n");
    }
    return localStringBuilder.toString();
  }
  
  private boolean isCenteredConnection(DependencyNode paramDependencyNode1, DependencyNode paramDependencyNode2)
  {
    int i = 0;
    int k = 0;
    Iterator localIterator = paramDependencyNode1.targets.iterator();
    while (localIterator.hasNext())
    {
      j = i;
      if ((DependencyNode)localIterator.next() != paramDependencyNode2) {
        j = i + 1;
      }
      i = j;
    }
    paramDependencyNode2 = paramDependencyNode2.targets.iterator();
    for (int j = k; paramDependencyNode2.hasNext(); j = k)
    {
      k = j;
      if ((DependencyNode)paramDependencyNode2.next() != paramDependencyNode1) {
        k = j + 1;
      }
    }
    boolean bool;
    if ((i > 0) && (j > 0)) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  private void measure(ConstraintWidget paramConstraintWidget, ConstraintWidget.DimensionBehaviour paramDimensionBehaviour1, int paramInt1, ConstraintWidget.DimensionBehaviour paramDimensionBehaviour2, int paramInt2)
  {
    this.mMeasure.horizontalBehavior = paramDimensionBehaviour1;
    this.mMeasure.verticalBehavior = paramDimensionBehaviour2;
    this.mMeasure.horizontalDimension = paramInt1;
    this.mMeasure.verticalDimension = paramInt2;
    this.mMeasurer.measure(paramConstraintWidget, this.mMeasure);
    paramConstraintWidget.setWidth(this.mMeasure.measuredWidth);
    paramConstraintWidget.setHeight(this.mMeasure.measuredHeight);
    paramConstraintWidget.setHasBaseline(this.mMeasure.measuredHasBaseline);
    paramConstraintWidget.setBaselineDistance(this.mMeasure.measuredBaseline);
  }
  
  private String nodeDefinition(WidgetRun paramWidgetRun)
  {
    boolean bool = paramWidgetRun instanceof VerticalWidgetRun;
    String str = paramWidgetRun.widget.getDebugName();
    StringBuilder localStringBuilder = new StringBuilder(str);
    Object localObject = paramWidgetRun.widget;
    if (!bool) {
      localObject = ((ConstraintWidget)localObject).getHorizontalDimensionBehaviour();
    } else {
      localObject = ((ConstraintWidget)localObject).getVerticalDimensionBehaviour();
    }
    RunGroup localRunGroup = paramWidgetRun.runGroup;
    if (!bool) {
      localStringBuilder.append("_HORIZONTAL");
    } else {
      localStringBuilder.append("_VERTICAL");
    }
    localStringBuilder.append(" [shape=none, label=<");
    localStringBuilder.append("<TABLE BORDER=\"0\" CELLSPACING=\"0\" CELLPADDING=\"2\">");
    localStringBuilder.append("  <TR>");
    if (!bool)
    {
      localStringBuilder.append("    <TD ");
      if (paramWidgetRun.start.resolved) {
        localStringBuilder.append(" BGCOLOR=\"green\"");
      }
      localStringBuilder.append(" PORT=\"LEFT\" BORDER=\"1\">L</TD>");
    }
    else
    {
      localStringBuilder.append("    <TD ");
      if (paramWidgetRun.start.resolved) {
        localStringBuilder.append(" BGCOLOR=\"green\"");
      }
      localStringBuilder.append(" PORT=\"TOP\" BORDER=\"1\">T</TD>");
    }
    localStringBuilder.append("    <TD BORDER=\"1\" ");
    if ((paramWidgetRun.dimension.resolved) && (!paramWidgetRun.widget.measured)) {
      localStringBuilder.append(" BGCOLOR=\"green\" ");
    } else if (paramWidgetRun.dimension.resolved) {
      localStringBuilder.append(" BGCOLOR=\"lightgray\" ");
    } else if (paramWidgetRun.widget.measured) {
      localStringBuilder.append(" BGCOLOR=\"yellow\" ");
    }
    if (localObject == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
      localStringBuilder.append("style=\"dashed\"");
    }
    localStringBuilder.append(">");
    localStringBuilder.append(str);
    if (localRunGroup != null)
    {
      localStringBuilder.append(" [");
      localStringBuilder.append(localRunGroup.groupIndex + 1);
      localStringBuilder.append("/");
      localStringBuilder.append(RunGroup.index);
      localStringBuilder.append("]");
    }
    localStringBuilder.append(" </TD>");
    if (!bool)
    {
      localStringBuilder.append("    <TD ");
      if (paramWidgetRun.end.resolved) {
        localStringBuilder.append(" BGCOLOR=\"green\"");
      }
      localStringBuilder.append(" PORT=\"RIGHT\" BORDER=\"1\">R</TD>");
    }
    else
    {
      localStringBuilder.append("    <TD ");
      if (((VerticalWidgetRun)paramWidgetRun).baseline.resolved) {
        localStringBuilder.append(" BGCOLOR=\"green\"");
      }
      localStringBuilder.append(" PORT=\"BASELINE\" BORDER=\"1\">b</TD>");
      localStringBuilder.append("    <TD ");
      if (paramWidgetRun.end.resolved) {
        localStringBuilder.append(" BGCOLOR=\"green\"");
      }
      localStringBuilder.append(" PORT=\"BOTTOM\" BORDER=\"1\">B</TD>");
    }
    localStringBuilder.append("  </TR></TABLE>");
    localStringBuilder.append(">];\n");
    return localStringBuilder.toString();
  }
  
  public void buildGraph()
  {
    buildGraph(this.mRuns);
    this.mGroups.clear();
    RunGroup.index = 0;
    findGroup(this.container.horizontalRun, 0, this.mGroups);
    findGroup(this.container.verticalRun, 1, this.mGroups);
    this.mNeedBuildGraph = false;
  }
  
  public void buildGraph(ArrayList<WidgetRun> paramArrayList)
  {
    paramArrayList.clear();
    this.mContainer.horizontalRun.clear();
    this.mContainer.verticalRun.clear();
    paramArrayList.add(this.mContainer.horizontalRun);
    paramArrayList.add(this.mContainer.verticalRun);
    Object localObject1 = null;
    Iterator localIterator = this.mContainer.mChildren.iterator();
    while (localIterator.hasNext())
    {
      ConstraintWidget localConstraintWidget = (ConstraintWidget)localIterator.next();
      if ((localConstraintWidget instanceof Guideline))
      {
        paramArrayList.add(new GuidelineReference(localConstraintWidget));
      }
      else
      {
        Object localObject2;
        if (localConstraintWidget.isInHorizontalChain())
        {
          if (localConstraintWidget.horizontalChainRun == null) {
            localConstraintWidget.horizontalChainRun = new ChainRun(localConstraintWidget, 0);
          }
          localObject2 = localObject1;
          if (localObject1 == null) {
            localObject2 = new HashSet();
          }
          ((HashSet)localObject2).add(localConstraintWidget.horizontalChainRun);
          localObject1 = localObject2;
        }
        else
        {
          paramArrayList.add(localConstraintWidget.horizontalRun);
        }
        if (localConstraintWidget.isInVerticalChain())
        {
          if (localConstraintWidget.verticalChainRun == null) {
            localConstraintWidget.verticalChainRun = new ChainRun(localConstraintWidget, 1);
          }
          localObject2 = localObject1;
          if (localObject1 == null) {
            localObject2 = new HashSet();
          }
          ((HashSet)localObject2).add(localConstraintWidget.verticalChainRun);
          localObject1 = localObject2;
        }
        else
        {
          paramArrayList.add(localConstraintWidget.verticalRun);
        }
        if ((localConstraintWidget instanceof HelperWidget)) {
          paramArrayList.add(new HelperReferences(localConstraintWidget));
        }
      }
    }
    if (localObject1 != null) {
      paramArrayList.addAll((Collection)localObject1);
    }
    localObject1 = paramArrayList.iterator();
    while (((Iterator)localObject1).hasNext()) {
      ((WidgetRun)((Iterator)localObject1).next()).clear();
    }
    paramArrayList = paramArrayList.iterator();
    while (paramArrayList.hasNext())
    {
      localObject1 = (WidgetRun)paramArrayList.next();
      if (((WidgetRun)localObject1).widget != this.mContainer) {
        ((WidgetRun)localObject1).apply();
      }
    }
  }
  
  public void defineTerminalWidgets(ConstraintWidget.DimensionBehaviour paramDimensionBehaviour1, ConstraintWidget.DimensionBehaviour paramDimensionBehaviour2)
  {
    if (this.mNeedBuildGraph)
    {
      buildGraph();
      int i = 0;
      Iterator localIterator = this.container.mChildren.iterator();
      Object localObject;
      while (localIterator.hasNext())
      {
        localObject = (ConstraintWidget)localIterator.next();
        ((ConstraintWidget)localObject).isTerminalWidget[0] = true;
        ((ConstraintWidget)localObject).isTerminalWidget[1] = true;
        if ((localObject instanceof Barrier)) {
          i = 1;
        }
      }
      if (i == 0)
      {
        localIterator = this.mGroups.iterator();
        while (localIterator.hasNext())
        {
          localObject = (RunGroup)localIterator.next();
          boolean bool1;
          if (paramDimensionBehaviour1 == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT) {
            bool1 = true;
          } else {
            bool1 = false;
          }
          boolean bool2;
          if (paramDimensionBehaviour2 == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT) {
            bool2 = true;
          } else {
            bool2 = false;
          }
          ((RunGroup)localObject).defineTerminalWidgets(bool1, bool2);
        }
      }
    }
  }
  
  public boolean directMeasure(boolean paramBoolean)
  {
    boolean bool2 = paramBoolean & true;
    if ((this.mNeedBuildGraph) || (this.mNeedRedoMeasures))
    {
      localObject2 = this.container.mChildren.iterator();
      while (((Iterator)localObject2).hasNext())
      {
        localObject1 = (ConstraintWidget)((Iterator)localObject2).next();
        ((ConstraintWidget)localObject1).ensureWidgetRuns();
        ((ConstraintWidget)localObject1).measured = false;
        ((ConstraintWidget)localObject1).horizontalRun.reset();
        ((ConstraintWidget)localObject1).verticalRun.reset();
      }
      this.container.ensureWidgetRuns();
      this.container.measured = false;
      this.container.horizontalRun.reset();
      this.container.verticalRun.reset();
      this.mNeedRedoMeasures = false;
    }
    if (basicMeasureWidgets(this.mContainer)) {
      return false;
    }
    this.container.setX(0);
    this.container.setY(0);
    Object localObject2 = this.container.getDimensionBehaviour(0);
    Object localObject1 = this.container.getDimensionBehaviour(1);
    if (this.mNeedBuildGraph) {
      buildGraph();
    }
    int k = this.container.getX();
    int j = this.container.getY();
    this.container.horizontalRun.start.resolve(k);
    this.container.verticalRun.start.resolve(j);
    measureWidgets();
    if ((localObject2 == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT) || (localObject1 == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT))
    {
      bool1 = bool2;
      if (bool2)
      {
        localObject3 = this.mRuns.iterator();
        for (;;)
        {
          bool1 = bool2;
          if (!((Iterator)localObject3).hasNext()) {
            break;
          }
          if (!((WidgetRun)((Iterator)localObject3).next()).supportsWrapComputation())
          {
            bool1 = false;
            break;
          }
        }
      }
      if ((bool1) && (localObject2 == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT))
      {
        this.container.setHorizontalDimensionBehaviour(ConstraintWidget.DimensionBehaviour.FIXED);
        localObject3 = this.container;
        ((ConstraintWidgetContainer)localObject3).setWidth(computeWrap((ConstraintWidgetContainer)localObject3, 0));
        this.container.horizontalRun.dimension.resolve(this.container.getWidth());
      }
      if ((bool1) && (localObject1 == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT))
      {
        this.container.setVerticalDimensionBehaviour(ConstraintWidget.DimensionBehaviour.FIXED);
        localObject3 = this.container;
        ((ConstraintWidgetContainer)localObject3).setHeight(computeWrap((ConstraintWidgetContainer)localObject3, 1));
        this.container.verticalRun.dimension.resolve(this.container.getHeight());
      }
    }
    boolean bool1 = false;
    int i;
    if ((this.container.mListDimensionBehaviors[0] == ConstraintWidget.DimensionBehaviour.FIXED) || (this.container.mListDimensionBehaviors[0] == ConstraintWidget.DimensionBehaviour.MATCH_PARENT))
    {
      i = this.container.getWidth() + k;
      this.container.horizontalRun.end.resolve(i);
      this.container.horizontalRun.dimension.resolve(i - k);
      measureWidgets();
      if ((this.container.mListDimensionBehaviors[1] == ConstraintWidget.DimensionBehaviour.FIXED) || (this.container.mListDimensionBehaviors[1] == ConstraintWidget.DimensionBehaviour.MATCH_PARENT))
      {
        i = this.container.getHeight() + j;
        this.container.verticalRun.end.resolve(i);
        this.container.verticalRun.dimension.resolve(i - j);
      }
      measureWidgets();
      i = 1;
    }
    Object localObject3 = this.mRuns.iterator();
    while (((Iterator)localObject3).hasNext())
    {
      localObject4 = (WidgetRun)((Iterator)localObject3).next();
      if ((((WidgetRun)localObject4).widget != this.container) || (((WidgetRun)localObject4).resolved)) {
        ((WidgetRun)localObject4).applyToWidget();
      }
    }
    boolean bool3 = true;
    Object localObject4 = this.mRuns.iterator();
    for (;;)
    {
      paramBoolean = bool3;
      if (!((Iterator)localObject4).hasNext()) {
        break;
      }
      localObject3 = (WidgetRun)((Iterator)localObject4).next();
      if ((i != 0) || (((WidgetRun)localObject3).widget != this.container))
      {
        if (!((WidgetRun)localObject3).start.resolved)
        {
          paramBoolean = false;
          break;
        }
        if ((!((WidgetRun)localObject3).end.resolved) && (!(localObject3 instanceof GuidelineReference)))
        {
          paramBoolean = false;
          break;
        }
        if ((!((WidgetRun)localObject3).dimension.resolved) && (!(localObject3 instanceof ChainRun)) && (!(localObject3 instanceof GuidelineReference)))
        {
          paramBoolean = false;
          break;
        }
      }
    }
    this.container.setHorizontalDimensionBehaviour((ConstraintWidget.DimensionBehaviour)localObject2);
    this.container.setVerticalDimensionBehaviour((ConstraintWidget.DimensionBehaviour)localObject1);
    return paramBoolean;
  }
  
  public boolean directMeasureSetup(boolean paramBoolean)
  {
    if (this.mNeedBuildGraph)
    {
      Iterator localIterator = this.container.mChildren.iterator();
      while (localIterator.hasNext())
      {
        ConstraintWidget localConstraintWidget = (ConstraintWidget)localIterator.next();
        localConstraintWidget.ensureWidgetRuns();
        localConstraintWidget.measured = false;
        localConstraintWidget.horizontalRun.dimension.resolved = false;
        localConstraintWidget.horizontalRun.resolved = false;
        localConstraintWidget.horizontalRun.reset();
        localConstraintWidget.verticalRun.dimension.resolved = false;
        localConstraintWidget.verticalRun.resolved = false;
        localConstraintWidget.verticalRun.reset();
      }
      this.container.ensureWidgetRuns();
      this.container.measured = false;
      this.container.horizontalRun.dimension.resolved = false;
      this.container.horizontalRun.resolved = false;
      this.container.horizontalRun.reset();
      this.container.verticalRun.dimension.resolved = false;
      this.container.verticalRun.resolved = false;
      this.container.verticalRun.reset();
      buildGraph();
    }
    if (basicMeasureWidgets(this.mContainer)) {
      return false;
    }
    this.container.setX(0);
    this.container.setY(0);
    this.container.horizontalRun.start.resolve(0);
    this.container.verticalRun.start.resolve(0);
    return true;
  }
  
  public boolean directMeasureWithOrientation(boolean paramBoolean, int paramInt)
  {
    boolean bool2 = paramBoolean & true;
    ConstraintWidget.DimensionBehaviour localDimensionBehaviour2 = this.container.getDimensionBehaviour(0);
    ConstraintWidget.DimensionBehaviour localDimensionBehaviour1 = this.container.getDimensionBehaviour(1);
    int j = this.container.getX();
    int k = this.container.getY();
    if ((bool2) && ((localDimensionBehaviour2 == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT) || (localDimensionBehaviour1 == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT)))
    {
      localObject2 = this.mRuns.iterator();
      for (;;)
      {
        bool1 = bool2;
        if (!((Iterator)localObject2).hasNext()) {
          break;
        }
        localObject1 = (WidgetRun)((Iterator)localObject2).next();
        if ((((WidgetRun)localObject1).orientation == paramInt) && (!((WidgetRun)localObject1).supportsWrapComputation()))
        {
          bool1 = false;
          break;
        }
      }
      if (paramInt == 0)
      {
        if ((bool1) && (localDimensionBehaviour2 == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT))
        {
          this.container.setHorizontalDimensionBehaviour(ConstraintWidget.DimensionBehaviour.FIXED);
          localObject1 = this.container;
          ((ConstraintWidgetContainer)localObject1).setWidth(computeWrap((ConstraintWidgetContainer)localObject1, 0));
          this.container.horizontalRun.dimension.resolve(this.container.getWidth());
        }
      }
      else if ((bool1) && (localDimensionBehaviour1 == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT))
      {
        this.container.setVerticalDimensionBehaviour(ConstraintWidget.DimensionBehaviour.FIXED);
        localObject1 = this.container;
        ((ConstraintWidgetContainer)localObject1).setHeight(computeWrap((ConstraintWidgetContainer)localObject1, 1));
        this.container.verticalRun.dimension.resolve(this.container.getHeight());
      }
    }
    boolean bool1 = false;
    int i;
    if (paramInt == 0)
    {
      if ((this.container.mListDimensionBehaviors[0] == ConstraintWidget.DimensionBehaviour.FIXED) || (this.container.mListDimensionBehaviors[0] == ConstraintWidget.DimensionBehaviour.MATCH_PARENT))
      {
        i = this.container.getWidth() + j;
        this.container.horizontalRun.end.resolve(i);
        this.container.horizontalRun.dimension.resolve(i - j);
        i = 1;
      }
    }
    else if ((this.container.mListDimensionBehaviors[1] == ConstraintWidget.DimensionBehaviour.FIXED) || (this.container.mListDimensionBehaviors[1] == ConstraintWidget.DimensionBehaviour.MATCH_PARENT))
    {
      i = this.container.getHeight() + k;
      this.container.verticalRun.end.resolve(i);
      this.container.verticalRun.dimension.resolve(i - k);
      i = 1;
    }
    measureWidgets();
    Object localObject1 = this.mRuns.iterator();
    while (((Iterator)localObject1).hasNext())
    {
      localObject2 = (WidgetRun)((Iterator)localObject1).next();
      if ((((WidgetRun)localObject2).orientation == paramInt) && ((((WidgetRun)localObject2).widget != this.container) || (((WidgetRun)localObject2).resolved))) {
        ((WidgetRun)localObject2).applyToWidget();
      }
    }
    boolean bool3 = true;
    Object localObject2 = this.mRuns.iterator();
    for (;;)
    {
      paramBoolean = bool3;
      if (!((Iterator)localObject2).hasNext()) {
        break;
      }
      localObject1 = (WidgetRun)((Iterator)localObject2).next();
      if ((((WidgetRun)localObject1).orientation == paramInt) && ((i != 0) || (((WidgetRun)localObject1).widget != this.container)))
      {
        if (!((WidgetRun)localObject1).start.resolved)
        {
          paramBoolean = false;
          break;
        }
        if (!((WidgetRun)localObject1).end.resolved)
        {
          paramBoolean = false;
          break;
        }
        if ((!(localObject1 instanceof ChainRun)) && (!((WidgetRun)localObject1).dimension.resolved))
        {
          paramBoolean = false;
          break;
        }
      }
    }
    this.container.setHorizontalDimensionBehaviour(localDimensionBehaviour2);
    this.container.setVerticalDimensionBehaviour(localDimensionBehaviour1);
    return paramBoolean;
  }
  
  public void invalidateGraph()
  {
    this.mNeedBuildGraph = true;
  }
  
  public void invalidateMeasures()
  {
    this.mNeedRedoMeasures = true;
  }
  
  public void measureWidgets()
  {
    Iterator localIterator = this.container.mChildren.iterator();
    while (localIterator.hasNext())
    {
      ConstraintWidget localConstraintWidget = (ConstraintWidget)localIterator.next();
      if (!localConstraintWidget.measured)
      {
        Object localObject = localConstraintWidget.mListDimensionBehaviors;
        int k = 0;
        localObject = localObject[0];
        ConstraintWidget.DimensionBehaviour localDimensionBehaviour = localConstraintWidget.mListDimensionBehaviors[1];
        int i = localConstraintWidget.mMatchConstraintDefaultWidth;
        int m = localConstraintWidget.mMatchConstraintDefaultHeight;
        if ((localObject != ConstraintWidget.DimensionBehaviour.WRAP_CONTENT) && ((localObject != ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) || (i != 1))) {
          i = 0;
        } else {
          i = 1;
        }
        int j;
        if (localDimensionBehaviour != ConstraintWidget.DimensionBehaviour.WRAP_CONTENT)
        {
          j = k;
          if (localDimensionBehaviour == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT)
          {
            j = k;
            if (m != 1) {}
          }
        }
        else
        {
          j = 1;
        }
        boolean bool2 = localConstraintWidget.horizontalRun.dimension.resolved;
        boolean bool1 = localConstraintWidget.verticalRun.dimension.resolved;
        if ((bool2) && (bool1))
        {
          measure(localConstraintWidget, ConstraintWidget.DimensionBehaviour.FIXED, localConstraintWidget.horizontalRun.dimension.value, ConstraintWidget.DimensionBehaviour.FIXED, localConstraintWidget.verticalRun.dimension.value);
          localConstraintWidget.measured = true;
        }
        else if ((bool2) && (j != 0))
        {
          measure(localConstraintWidget, ConstraintWidget.DimensionBehaviour.FIXED, localConstraintWidget.horizontalRun.dimension.value, ConstraintWidget.DimensionBehaviour.WRAP_CONTENT, localConstraintWidget.verticalRun.dimension.value);
          if (localDimensionBehaviour == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT)
          {
            localConstraintWidget.verticalRun.dimension.wrapValue = localConstraintWidget.getHeight();
          }
          else
          {
            localConstraintWidget.verticalRun.dimension.resolve(localConstraintWidget.getHeight());
            localConstraintWidget.measured = true;
          }
        }
        else if ((bool1) && (i != 0))
        {
          measure(localConstraintWidget, ConstraintWidget.DimensionBehaviour.WRAP_CONTENT, localConstraintWidget.horizontalRun.dimension.value, ConstraintWidget.DimensionBehaviour.FIXED, localConstraintWidget.verticalRun.dimension.value);
          if (localObject == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT)
          {
            localConstraintWidget.horizontalRun.dimension.wrapValue = localConstraintWidget.getWidth();
          }
          else
          {
            localConstraintWidget.horizontalRun.dimension.resolve(localConstraintWidget.getWidth());
            localConstraintWidget.measured = true;
          }
        }
        if ((localConstraintWidget.measured) && (localConstraintWidget.verticalRun.baselineDimension != null)) {
          localConstraintWidget.verticalRun.baselineDimension.resolve(localConstraintWidget.getBaselineDistance());
        }
      }
    }
  }
  
  public void setMeasurer(BasicMeasure.Measurer paramMeasurer)
  {
    this.mMeasurer = paramMeasurer;
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/constraintlayout/core/widgets/analyzer/DependencyGraph.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */