package androidx.constraintlayout.core.widgets;

import androidx.constraintlayout.core.LinearSystem;
import androidx.constraintlayout.core.Metrics;
import androidx.constraintlayout.core.SolverVariable;
import androidx.constraintlayout.core.widgets.analyzer.BasicMeasure;
import androidx.constraintlayout.core.widgets.analyzer.BasicMeasure.Measure;
import androidx.constraintlayout.core.widgets.analyzer.BasicMeasure.Measurer;
import androidx.constraintlayout.core.widgets.analyzer.DependencyGraph;
import androidx.constraintlayout.core.widgets.analyzer.Direct;
import androidx.constraintlayout.core.widgets.analyzer.Grouping;
import java.io.PrintStream;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;

public class ConstraintWidgetContainer
  extends WidgetContainer
{
  private static final boolean DEBUG = false;
  static final boolean DEBUG_GRAPH = false;
  private static final boolean DEBUG_LAYOUT = false;
  private static final int MAX_ITERATIONS = 8;
  static int myCounter = 0;
  private WeakReference<ConstraintAnchor> horizontalWrapMax = null;
  private WeakReference<ConstraintAnchor> horizontalWrapMin = null;
  BasicMeasure mBasicMeasureSolver = new BasicMeasure(this);
  int mDebugSolverPassCount = 0;
  public DependencyGraph mDependencyGraph = new DependencyGraph(this);
  public boolean mGroupsWrapOptimized = false;
  private boolean mHeightMeasuredTooSmall = false;
  ChainHead[] mHorizontalChainsArray = new ChainHead[4];
  public int mHorizontalChainsSize = 0;
  public boolean mHorizontalWrapOptimized = false;
  private boolean mIsRtl = false;
  public BasicMeasure.Measure mMeasure = new BasicMeasure.Measure();
  protected BasicMeasure.Measurer mMeasurer = null;
  public Metrics mMetrics;
  private int mOptimizationLevel = 257;
  int mPaddingBottom;
  int mPaddingLeft;
  int mPaddingRight;
  int mPaddingTop;
  public boolean mSkipSolver = false;
  protected LinearSystem mSystem = new LinearSystem();
  ChainHead[] mVerticalChainsArray = new ChainHead[4];
  public int mVerticalChainsSize = 0;
  public boolean mVerticalWrapOptimized = false;
  private boolean mWidthMeasuredTooSmall = false;
  public int mWrapFixedHeight = 0;
  public int mWrapFixedWidth = 0;
  private int pass;
  private WeakReference<ConstraintAnchor> verticalWrapMax = null;
  private WeakReference<ConstraintAnchor> verticalWrapMin = null;
  HashSet<ConstraintWidget> widgetsToAdd = new HashSet();
  
  public ConstraintWidgetContainer() {}
  
  public ConstraintWidgetContainer(int paramInt1, int paramInt2)
  {
    super(paramInt1, paramInt2);
  }
  
  public ConstraintWidgetContainer(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    super(paramInt1, paramInt2, paramInt3, paramInt4);
  }
  
  public ConstraintWidgetContainer(String paramString, int paramInt1, int paramInt2)
  {
    super(paramInt1, paramInt2);
    setDebugName(paramString);
  }
  
  private void addHorizontalChain(ConstraintWidget paramConstraintWidget)
  {
    int i = this.mHorizontalChainsSize;
    ChainHead[] arrayOfChainHead = this.mHorizontalChainsArray;
    if (i + 1 >= arrayOfChainHead.length) {
      this.mHorizontalChainsArray = ((ChainHead[])Arrays.copyOf(arrayOfChainHead, arrayOfChainHead.length * 2));
    }
    this.mHorizontalChainsArray[this.mHorizontalChainsSize] = new ChainHead(paramConstraintWidget, 0, isRtl());
    this.mHorizontalChainsSize += 1;
  }
  
  private void addMaxWrap(ConstraintAnchor paramConstraintAnchor, SolverVariable paramSolverVariable)
  {
    paramConstraintAnchor = this.mSystem.createObjectVariable(paramConstraintAnchor);
    this.mSystem.addGreaterThan(paramSolverVariable, paramConstraintAnchor, 0, 5);
  }
  
  private void addMinWrap(ConstraintAnchor paramConstraintAnchor, SolverVariable paramSolverVariable)
  {
    paramConstraintAnchor = this.mSystem.createObjectVariable(paramConstraintAnchor);
    this.mSystem.addGreaterThan(paramConstraintAnchor, paramSolverVariable, 0, 5);
  }
  
  private void addVerticalChain(ConstraintWidget paramConstraintWidget)
  {
    int i = this.mVerticalChainsSize;
    ChainHead[] arrayOfChainHead = this.mVerticalChainsArray;
    if (i + 1 >= arrayOfChainHead.length) {
      this.mVerticalChainsArray = ((ChainHead[])Arrays.copyOf(arrayOfChainHead, arrayOfChainHead.length * 2));
    }
    this.mVerticalChainsArray[this.mVerticalChainsSize] = new ChainHead(paramConstraintWidget, 1, isRtl());
    this.mVerticalChainsSize += 1;
  }
  
  public static boolean measure(int paramInt1, ConstraintWidget paramConstraintWidget, BasicMeasure.Measurer paramMeasurer, BasicMeasure.Measure paramMeasure, int paramInt2)
  {
    if (paramMeasurer == null) {
      return false;
    }
    if ((paramConstraintWidget.getVisibility() != 8) && (!(paramConstraintWidget instanceof Guideline)) && (!(paramConstraintWidget instanceof Barrier)))
    {
      paramMeasure.horizontalBehavior = paramConstraintWidget.getHorizontalDimensionBehaviour();
      paramMeasure.verticalBehavior = paramConstraintWidget.getVerticalDimensionBehaviour();
      paramMeasure.horizontalDimension = paramConstraintWidget.getWidth();
      paramMeasure.verticalDimension = paramConstraintWidget.getHeight();
      paramMeasure.measuredNeedsSolverPass = false;
      paramMeasure.measureStrategy = paramInt2;
      if (paramMeasure.horizontalBehavior == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
        paramInt2 = 1;
      } else {
        paramInt2 = 0;
      }
      if (paramMeasure.verticalBehavior == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
        paramInt1 = 1;
      } else {
        paramInt1 = 0;
      }
      int k;
      if ((paramInt2 != 0) && (paramConstraintWidget.mDimensionRatio > 0.0F)) {
        k = 1;
      } else {
        k = 0;
      }
      int j;
      if ((paramInt1 != 0) && (paramConstraintWidget.mDimensionRatio > 0.0F)) {
        j = 1;
      } else {
        j = 0;
      }
      int i = paramInt2;
      if (paramInt2 != 0)
      {
        i = paramInt2;
        if (paramConstraintWidget.hasDanglingDimension(0))
        {
          i = paramInt2;
          if (paramConstraintWidget.mMatchConstraintDefaultWidth == 0)
          {
            i = paramInt2;
            if (k == 0)
            {
              paramInt2 = 0;
              paramMeasure.horizontalBehavior = ConstraintWidget.DimensionBehaviour.WRAP_CONTENT;
              i = paramInt2;
              if (paramInt1 != 0)
              {
                i = paramInt2;
                if (paramConstraintWidget.mMatchConstraintDefaultHeight == 0)
                {
                  paramMeasure.horizontalBehavior = ConstraintWidget.DimensionBehaviour.FIXED;
                  i = paramInt2;
                }
              }
            }
          }
        }
      }
      paramInt2 = paramInt1;
      if (paramInt1 != 0)
      {
        paramInt2 = paramInt1;
        if (paramConstraintWidget.hasDanglingDimension(1))
        {
          paramInt2 = paramInt1;
          if (paramConstraintWidget.mMatchConstraintDefaultHeight == 0)
          {
            paramInt2 = paramInt1;
            if (j == 0)
            {
              paramInt1 = 0;
              paramMeasure.verticalBehavior = ConstraintWidget.DimensionBehaviour.WRAP_CONTENT;
              paramInt2 = paramInt1;
              if (i != 0)
              {
                paramInt2 = paramInt1;
                if (paramConstraintWidget.mMatchConstraintDefaultWidth == 0)
                {
                  paramMeasure.verticalBehavior = ConstraintWidget.DimensionBehaviour.FIXED;
                  paramInt2 = paramInt1;
                }
              }
            }
          }
        }
      }
      if (paramConstraintWidget.isResolvedHorizontally())
      {
        i = 0;
        paramMeasure.horizontalBehavior = ConstraintWidget.DimensionBehaviour.FIXED;
      }
      if (paramConstraintWidget.isResolvedVertically())
      {
        paramInt2 = 0;
        paramMeasure.verticalBehavior = ConstraintWidget.DimensionBehaviour.FIXED;
      }
      if (k != 0) {
        if (paramConstraintWidget.mResolvedMatchConstraintDefault[0] == 4)
        {
          paramMeasure.horizontalBehavior = ConstraintWidget.DimensionBehaviour.FIXED;
        }
        else if (paramInt2 == 0)
        {
          if (paramMeasure.verticalBehavior == ConstraintWidget.DimensionBehaviour.FIXED)
          {
            paramInt1 = paramMeasure.verticalDimension;
          }
          else
          {
            paramMeasure.horizontalBehavior = ConstraintWidget.DimensionBehaviour.WRAP_CONTENT;
            paramMeasurer.measure(paramConstraintWidget, paramMeasure);
            paramInt1 = paramMeasure.measuredHeight;
          }
          paramMeasure.horizontalBehavior = ConstraintWidget.DimensionBehaviour.FIXED;
          paramMeasure.horizontalDimension = ((int)(paramConstraintWidget.getDimensionRatio() * paramInt1));
        }
      }
      if (j != 0) {
        if (paramConstraintWidget.mResolvedMatchConstraintDefault[1] == 4)
        {
          paramMeasure.verticalBehavior = ConstraintWidget.DimensionBehaviour.FIXED;
        }
        else if (i == 0)
        {
          if (paramMeasure.horizontalBehavior == ConstraintWidget.DimensionBehaviour.FIXED)
          {
            paramInt1 = paramMeasure.horizontalDimension;
          }
          else
          {
            paramMeasure.verticalBehavior = ConstraintWidget.DimensionBehaviour.WRAP_CONTENT;
            paramMeasurer.measure(paramConstraintWidget, paramMeasure);
            paramInt1 = paramMeasure.measuredWidth;
          }
          paramMeasure.verticalBehavior = ConstraintWidget.DimensionBehaviour.FIXED;
          if (paramConstraintWidget.getDimensionRatioSide() == -1) {
            paramMeasure.verticalDimension = ((int)(paramInt1 / paramConstraintWidget.getDimensionRatio()));
          } else {
            paramMeasure.verticalDimension = ((int)(paramConstraintWidget.getDimensionRatio() * paramInt1));
          }
        }
      }
      paramMeasurer.measure(paramConstraintWidget, paramMeasure);
      paramConstraintWidget.setWidth(paramMeasure.measuredWidth);
      paramConstraintWidget.setHeight(paramMeasure.measuredHeight);
      paramConstraintWidget.setHasBaseline(paramMeasure.measuredHasBaseline);
      paramConstraintWidget.setBaselineDistance(paramMeasure.measuredBaseline);
      paramMeasure.measureStrategy = BasicMeasure.Measure.SELF_DIMENSIONS;
      return paramMeasure.measuredNeedsSolverPass;
    }
    paramMeasure.measuredWidth = 0;
    paramMeasure.measuredHeight = 0;
    return false;
  }
  
  private void resetChains()
  {
    this.mHorizontalChainsSize = 0;
    this.mVerticalChainsSize = 0;
  }
  
  void addChain(ConstraintWidget paramConstraintWidget, int paramInt)
  {
    if (paramInt == 0) {
      addHorizontalChain(paramConstraintWidget);
    } else if (paramInt == 1) {
      addVerticalChain(paramConstraintWidget);
    }
  }
  
  public boolean addChildrenToSolver(LinearSystem paramLinearSystem)
  {
    boolean bool = optimizeFor(64);
    addToSolver(paramLinearSystem, bool);
    int k = this.mChildren.size();
    int j = 0;
    Object localObject1;
    for (int i = 0; i < k; i++)
    {
      localObject1 = (ConstraintWidget)this.mChildren.get(i);
      ((ConstraintWidget)localObject1).setInBarrier(0, false);
      ((ConstraintWidget)localObject1).setInBarrier(1, false);
      if ((localObject1 instanceof Barrier)) {
        j = 1;
      }
    }
    if (j != 0) {
      for (i = 0; i < k; i++)
      {
        localObject1 = (ConstraintWidget)this.mChildren.get(i);
        if ((localObject1 instanceof Barrier)) {
          ((Barrier)localObject1).markWidgets();
        }
      }
    }
    this.widgetsToAdd.clear();
    for (i = 0; i < k; i++)
    {
      localObject1 = (ConstraintWidget)this.mChildren.get(i);
      if (((ConstraintWidget)localObject1).addFirst()) {
        if ((localObject1 instanceof VirtualLayout)) {
          this.widgetsToAdd.add(localObject1);
        } else {
          ((ConstraintWidget)localObject1).addToSolver(paramLinearSystem, bool);
        }
      }
    }
    Object localObject2;
    while (this.widgetsToAdd.size() > 0)
    {
      i = this.widgetsToAdd.size();
      localObject1 = this.widgetsToAdd.iterator();
      while (((Iterator)localObject1).hasNext())
      {
        localObject2 = (VirtualLayout)((Iterator)localObject1).next();
        if (((VirtualLayout)localObject2).contains(this.widgetsToAdd))
        {
          ((VirtualLayout)localObject2).addToSolver(paramLinearSystem, bool);
          this.widgetsToAdd.remove(localObject2);
          break;
        }
      }
      if (i == this.widgetsToAdd.size())
      {
        localObject1 = this.widgetsToAdd.iterator();
        while (((Iterator)localObject1).hasNext()) {
          ((ConstraintWidget)((Iterator)localObject1).next()).addToSolver(paramLinearSystem, bool);
        }
        this.widgetsToAdd.clear();
      }
    }
    if (LinearSystem.USE_DEPENDENCY_ORDERING)
    {
      localObject2 = new HashSet();
      for (i = 0; i < k; i++)
      {
        localObject1 = (ConstraintWidget)this.mChildren.get(i);
        if (!((ConstraintWidget)localObject1).addFirst()) {
          ((HashSet)localObject2).add(localObject1);
        }
      }
      if (getHorizontalDimensionBehaviour() == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT) {
        i = 0;
      } else {
        i = 1;
      }
      addChildrenToSolverByDependency(this, paramLinearSystem, (HashSet)localObject2, i, false);
      localObject2 = ((HashSet)localObject2).iterator();
      while (((Iterator)localObject2).hasNext())
      {
        localObject1 = (ConstraintWidget)((Iterator)localObject2).next();
        Optimizer.checkMatchParent(this, paramLinearSystem, (ConstraintWidget)localObject1);
        ((ConstraintWidget)localObject1).addToSolver(paramLinearSystem, bool);
      }
    }
    else
    {
      for (i = 0; i < k; i++)
      {
        localObject1 = (ConstraintWidget)this.mChildren.get(i);
        if ((localObject1 instanceof ConstraintWidgetContainer))
        {
          ConstraintWidget.DimensionBehaviour localDimensionBehaviour = localObject1.mListDimensionBehaviors[0];
          localObject2 = localObject1.mListDimensionBehaviors[1];
          if (localDimensionBehaviour == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT) {
            ((ConstraintWidget)localObject1).setHorizontalDimensionBehaviour(ConstraintWidget.DimensionBehaviour.FIXED);
          }
          if (localObject2 == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT) {
            ((ConstraintWidget)localObject1).setVerticalDimensionBehaviour(ConstraintWidget.DimensionBehaviour.FIXED);
          }
          ((ConstraintWidget)localObject1).addToSolver(paramLinearSystem, bool);
          if (localDimensionBehaviour == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT) {
            ((ConstraintWidget)localObject1).setHorizontalDimensionBehaviour(localDimensionBehaviour);
          }
          if (localObject2 == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT) {
            ((ConstraintWidget)localObject1).setVerticalDimensionBehaviour((ConstraintWidget.DimensionBehaviour)localObject2);
          }
        }
        else
        {
          Optimizer.checkMatchParent(this, paramLinearSystem, (ConstraintWidget)localObject1);
          if (!((ConstraintWidget)localObject1).addFirst()) {
            ((ConstraintWidget)localObject1).addToSolver(paramLinearSystem, bool);
          }
        }
      }
    }
    if (this.mHorizontalChainsSize > 0) {
      Chain.applyChainConstraints(this, paramLinearSystem, null, 0);
    }
    if (this.mVerticalChainsSize > 0) {
      Chain.applyChainConstraints(this, paramLinearSystem, null, 1);
    }
    return true;
  }
  
  public void addHorizontalWrapMaxVariable(ConstraintAnchor paramConstraintAnchor)
  {
    WeakReference localWeakReference = this.horizontalWrapMax;
    if ((localWeakReference == null) || (localWeakReference.get() == null) || (paramConstraintAnchor.getFinalValue() > ((ConstraintAnchor)this.horizontalWrapMax.get()).getFinalValue())) {
      this.horizontalWrapMax = new WeakReference(paramConstraintAnchor);
    }
  }
  
  public void addHorizontalWrapMinVariable(ConstraintAnchor paramConstraintAnchor)
  {
    WeakReference localWeakReference = this.horizontalWrapMin;
    if ((localWeakReference == null) || (localWeakReference.get() == null) || (paramConstraintAnchor.getFinalValue() > ((ConstraintAnchor)this.horizontalWrapMin.get()).getFinalValue())) {
      this.horizontalWrapMin = new WeakReference(paramConstraintAnchor);
    }
  }
  
  void addVerticalWrapMaxVariable(ConstraintAnchor paramConstraintAnchor)
  {
    WeakReference localWeakReference = this.verticalWrapMax;
    if ((localWeakReference == null) || (localWeakReference.get() == null) || (paramConstraintAnchor.getFinalValue() > ((ConstraintAnchor)this.verticalWrapMax.get()).getFinalValue())) {
      this.verticalWrapMax = new WeakReference(paramConstraintAnchor);
    }
  }
  
  void addVerticalWrapMinVariable(ConstraintAnchor paramConstraintAnchor)
  {
    WeakReference localWeakReference = this.verticalWrapMin;
    if ((localWeakReference == null) || (localWeakReference.get() == null) || (paramConstraintAnchor.getFinalValue() > ((ConstraintAnchor)this.verticalWrapMin.get()).getFinalValue())) {
      this.verticalWrapMin = new WeakReference(paramConstraintAnchor);
    }
  }
  
  public void defineTerminalWidgets()
  {
    this.mDependencyGraph.defineTerminalWidgets(getHorizontalDimensionBehaviour(), getVerticalDimensionBehaviour());
  }
  
  public boolean directMeasure(boolean paramBoolean)
  {
    return this.mDependencyGraph.directMeasure(paramBoolean);
  }
  
  public boolean directMeasureSetup(boolean paramBoolean)
  {
    return this.mDependencyGraph.directMeasureSetup(paramBoolean);
  }
  
  public boolean directMeasureWithOrientation(boolean paramBoolean, int paramInt)
  {
    return this.mDependencyGraph.directMeasureWithOrientation(paramBoolean, paramInt);
  }
  
  public void fillMetrics(Metrics paramMetrics)
  {
    this.mMetrics = paramMetrics;
    this.mSystem.fillMetrics(paramMetrics);
  }
  
  public ArrayList<Guideline> getHorizontalGuidelines()
  {
    ArrayList localArrayList = new ArrayList();
    int i = 0;
    int j = this.mChildren.size();
    while (i < j)
    {
      Object localObject = (ConstraintWidget)this.mChildren.get(i);
      if ((localObject instanceof Guideline))
      {
        localObject = (Guideline)localObject;
        if (((Guideline)localObject).getOrientation() == 0) {
          localArrayList.add(localObject);
        }
      }
      i++;
    }
    return localArrayList;
  }
  
  public BasicMeasure.Measurer getMeasurer()
  {
    return this.mMeasurer;
  }
  
  public int getOptimizationLevel()
  {
    return this.mOptimizationLevel;
  }
  
  public void getSceneString(StringBuilder paramStringBuilder)
  {
    paramStringBuilder.append(this.stringId + ":{\n");
    paramStringBuilder.append("  actualWidth:" + this.mWidth);
    paramStringBuilder.append("\n");
    paramStringBuilder.append("  actualHeight:" + this.mHeight);
    paramStringBuilder.append("\n");
    Iterator localIterator = getChildren().iterator();
    while (localIterator.hasNext())
    {
      ((ConstraintWidget)localIterator.next()).getSceneString(paramStringBuilder);
      paramStringBuilder.append(",\n");
    }
    paramStringBuilder.append("}");
  }
  
  public LinearSystem getSystem()
  {
    return this.mSystem;
  }
  
  public String getType()
  {
    return "ConstraintLayout";
  }
  
  public ArrayList<Guideline> getVerticalGuidelines()
  {
    ArrayList localArrayList = new ArrayList();
    int i = 0;
    int j = this.mChildren.size();
    while (i < j)
    {
      Object localObject = (ConstraintWidget)this.mChildren.get(i);
      if ((localObject instanceof Guideline))
      {
        localObject = (Guideline)localObject;
        if (((Guideline)localObject).getOrientation() == 1) {
          localArrayList.add(localObject);
        }
      }
      i++;
    }
    return localArrayList;
  }
  
  public boolean handlesInternalConstraints()
  {
    return false;
  }
  
  public void invalidateGraph()
  {
    this.mDependencyGraph.invalidateGraph();
  }
  
  public void invalidateMeasures()
  {
    this.mDependencyGraph.invalidateMeasures();
  }
  
  public boolean isHeightMeasuredTooSmall()
  {
    return this.mHeightMeasuredTooSmall;
  }
  
  public boolean isRtl()
  {
    return this.mIsRtl;
  }
  
  public boolean isWidthMeasuredTooSmall()
  {
    return this.mWidthMeasuredTooSmall;
  }
  
  public void layout()
  {
    this.mX = 0;
    this.mY = 0;
    this.mWidthMeasuredTooSmall = false;
    this.mHeightMeasuredTooSmall = false;
    int i5 = this.mChildren.size();
    int i = Math.max(0, getWidth());
    int k = Math.max(0, getHeight());
    ConstraintWidget.DimensionBehaviour localDimensionBehaviour2 = this.mListDimensionBehaviors[1];
    ConstraintWidget.DimensionBehaviour localDimensionBehaviour1 = this.mListDimensionBehaviors[0];
    Object localObject1 = this.mMetrics;
    if (localObject1 != null) {
      ((Metrics)localObject1).layouts += 1L;
    }
    Object localObject2;
    int m;
    if ((this.pass == 0) && (Optimizer.enabled(this.mOptimizationLevel, 1)))
    {
      Direct.solvingPass(this, getMeasurer());
      for (j = 0; j < i5; j++)
      {
        localObject1 = (ConstraintWidget)this.mChildren.get(j);
        if ((((ConstraintWidget)localObject1).isMeasureRequested()) && (!(localObject1 instanceof Guideline)) && (!(localObject1 instanceof Barrier)) && (!(localObject1 instanceof VirtualLayout)) && (!((ConstraintWidget)localObject1).isInVirtualLayout()))
        {
          ConstraintWidget.DimensionBehaviour localDimensionBehaviour3 = ((ConstraintWidget)localObject1).getDimensionBehaviour(0);
          localObject2 = ((ConstraintWidget)localObject1).getDimensionBehaviour(1);
          if ((localDimensionBehaviour3 == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) && (((ConstraintWidget)localObject1).mMatchConstraintDefaultWidth != 1) && (localObject2 == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) && (((ConstraintWidget)localObject1).mMatchConstraintDefaultHeight != 1)) {
            m = 1;
          } else {
            m = 0;
          }
          if (m == 0)
          {
            localObject2 = new BasicMeasure.Measure();
            measure(0, (ConstraintWidget)localObject1, this.mMeasurer, (BasicMeasure.Measure)localObject2, BasicMeasure.Measure.SELF_DIMENSIONS);
          }
        }
      }
    }
    int n;
    if ((i5 > 2) && ((localDimensionBehaviour1 == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT) || (localDimensionBehaviour2 == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT)) && (Optimizer.enabled(this.mOptimizationLevel, 1024)) && (Grouping.simpleSolvingPass(this, getMeasurer())))
    {
      j = i;
      if (localDimensionBehaviour1 == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT) {
        if ((i < getWidth()) && (i > 0))
        {
          setWidth(i);
          this.mWidthMeasuredTooSmall = true;
          j = i;
        }
        else
        {
          j = getWidth();
        }
      }
      i = k;
      if (localDimensionBehaviour2 == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT) {
        if ((k < getHeight()) && (k > 0))
        {
          setHeight(k);
          this.mHeightMeasuredTooSmall = true;
          i = k;
        }
        else
        {
          i = getHeight();
        }
      }
      k = 1;
      m = i;
      i = k;
      n = j;
    }
    else
    {
      j = 0;
      m = k;
      n = i;
      i = j;
    }
    if ((!optimizeFor(64)) && (!optimizeFor(128))) {
      j = 0;
    } else {
      j = 1;
    }
    this.mSystem.graphOptimizer = false;
    this.mSystem.newgraphOptimizer = false;
    if ((this.mOptimizationLevel != 0) && (j != 0)) {
      this.mSystem.newgraphOptimizer = true;
    }
    localObject1 = this.mChildren;
    int i1;
    if ((getHorizontalDimensionBehaviour() != ConstraintWidget.DimensionBehaviour.WRAP_CONTENT) && (getVerticalDimensionBehaviour() != ConstraintWidget.DimensionBehaviour.WRAP_CONTENT)) {
      i1 = 0;
    } else {
      i1 = 1;
    }
    resetChains();
    for (k = 0; k < i5; k++)
    {
      localObject2 = (ConstraintWidget)this.mChildren.get(k);
      if ((localObject2 instanceof WidgetContainer)) {
        ((WidgetContainer)localObject2).layout();
      }
    }
    boolean bool4 = optimizeFor(64);
    k = 0;
    boolean bool2 = true;
    int i2 = j;
    int i4;
    for (int j = k; bool2; j = i4)
    {
      i4 = j + 1;
      boolean bool1 = bool2;
      try
      {
        this.mSystem.reset();
        bool1 = bool2;
        resetChains();
        bool1 = bool2;
        createObjectVariables(this.mSystem);
        for (j = 0; j < i5; j++)
        {
          bool1 = bool2;
          ((ConstraintWidget)this.mChildren.get(j)).createObjectVariables(this.mSystem);
        }
        bool1 = bool2;
        bool2 = addChildrenToSolver(this.mSystem);
        bool1 = bool2;
        localObject2 = this.verticalWrapMin;
        if (localObject2 != null)
        {
          bool1 = bool2;
          if (((WeakReference)localObject2).get() != null)
          {
            bool1 = bool2;
            addMinWrap((ConstraintAnchor)this.verticalWrapMin.get(), this.mSystem.createObjectVariable(this.mTop));
            bool1 = bool2;
            this.verticalWrapMin = null;
          }
        }
        bool1 = bool2;
        localObject2 = this.verticalWrapMax;
        if (localObject2 != null)
        {
          bool1 = bool2;
          if (((WeakReference)localObject2).get() != null)
          {
            bool1 = bool2;
            addMaxWrap((ConstraintAnchor)this.verticalWrapMax.get(), this.mSystem.createObjectVariable(this.mBottom));
            bool1 = bool2;
            this.verticalWrapMax = null;
          }
        }
        bool1 = bool2;
        localObject2 = this.horizontalWrapMin;
        if (localObject2 != null)
        {
          bool1 = bool2;
          if (((WeakReference)localObject2).get() != null)
          {
            bool1 = bool2;
            addMinWrap((ConstraintAnchor)this.horizontalWrapMin.get(), this.mSystem.createObjectVariable(this.mLeft));
            bool1 = bool2;
            this.horizontalWrapMin = null;
          }
        }
        bool1 = bool2;
        localObject2 = this.horizontalWrapMax;
        if (localObject2 != null)
        {
          bool1 = bool2;
          if (((WeakReference)localObject2).get() != null)
          {
            bool1 = bool2;
            addMaxWrap((ConstraintAnchor)this.horizontalWrapMax.get(), this.mSystem.createObjectVariable(this.mRight));
            bool1 = bool2;
            this.horizontalWrapMax = null;
          }
        }
        if (bool2)
        {
          bool1 = bool2;
          this.mSystem.minimize();
        }
        bool1 = bool2;
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
        System.out.println("EXCEPTION : " + localException);
      }
      if (bool1)
      {
        bool1 = updateChildrenFromSolver(this.mSystem, Optimizer.flags);
      }
      else
      {
        updateFromSolver(this.mSystem, bool4);
        for (j = 0; j < i5; j++) {
          ((ConstraintWidget)this.mChildren.get(j)).updateFromSolver(this.mSystem, bool4);
        }
        bool1 = false;
      }
      if ((i1 != 0) && (i4 < 8) && (Optimizer.flags[2] != 0))
      {
        int i3 = 0;
        j = 0;
        for (k = 0; k < i5; k++)
        {
          ConstraintWidget localConstraintWidget = (ConstraintWidget)this.mChildren.get(k);
          i3 = Math.max(i3, localConstraintWidget.mX + localConstraintWidget.getWidth());
          j = Math.max(j, localConstraintWidget.mY + localConstraintWidget.getHeight());
        }
        bool2 = bool1;
        i3 = Math.max(this.mMinWidth, i3);
        k = Math.max(this.mMinHeight, j);
        j = i;
        bool1 = bool2;
        if (localDimensionBehaviour1 == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT)
        {
          j = i;
          bool1 = bool2;
          if (getWidth() < i3)
          {
            setWidth(i3);
            this.mListDimensionBehaviors[0] = ConstraintWidget.DimensionBehaviour.WRAP_CONTENT;
            j = 1;
            bool1 = true;
          }
        }
        i = j;
        bool2 = bool1;
        if (localDimensionBehaviour2 == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT)
        {
          i = j;
          bool2 = bool1;
          if (getHeight() < k)
          {
            setHeight(k);
            this.mListDimensionBehaviors[1] = ConstraintWidget.DimensionBehaviour.WRAP_CONTENT;
            i = 1;
            bool2 = true;
          }
        }
      }
      else
      {
        bool2 = bool1;
      }
      k = Math.max(this.mMinWidth, getWidth());
      j = i;
      if (k > getWidth())
      {
        setWidth(k);
        this.mListDimensionBehaviors[0] = ConstraintWidget.DimensionBehaviour.FIXED;
        j = 1;
        bool2 = true;
      }
      i = Math.max(this.mMinHeight, getHeight());
      if (i > getHeight())
      {
        setHeight(i);
        this.mListDimensionBehaviors[1] = ConstraintWidget.DimensionBehaviour.FIXED;
        j = 1;
        bool2 = true;
      }
      bool1 = bool2;
      i = j;
      if (j == 0)
      {
        boolean bool3 = bool2;
        k = j;
        if (this.mListDimensionBehaviors[0] == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT)
        {
          bool3 = bool2;
          k = j;
          if (n > 0)
          {
            bool3 = bool2;
            k = j;
            if (getWidth() > n)
            {
              this.mWidthMeasuredTooSmall = true;
              k = 1;
              this.mListDimensionBehaviors[0] = ConstraintWidget.DimensionBehaviour.FIXED;
              setWidth(n);
              bool3 = true;
            }
          }
        }
        bool1 = bool3;
        i = k;
        if (this.mListDimensionBehaviors[1] == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT)
        {
          bool1 = bool3;
          i = k;
          if (m > 0)
          {
            bool1 = bool3;
            i = k;
            if (getHeight() > m)
            {
              this.mHeightMeasuredTooSmall = true;
              i = 1;
              this.mListDimensionBehaviors[1] = ConstraintWidget.DimensionBehaviour.FIXED;
              setHeight(m);
              bool1 = true;
            }
          }
        }
      }
      if (i4 > 8) {
        bool2 = false;
      } else {
        bool2 = bool1;
      }
    }
    this.mChildren = ((ArrayList)localObject1);
    if (i != 0)
    {
      this.mListDimensionBehaviors[0] = localDimensionBehaviour1;
      this.mListDimensionBehaviors[1] = localDimensionBehaviour2;
    }
    resetSolverVariables(this.mSystem.getCache());
  }
  
  public long measure(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8, int paramInt9)
  {
    this.mPaddingLeft = paramInt8;
    this.mPaddingTop = paramInt9;
    return this.mBasicMeasureSolver.solverMeasure(this, paramInt1, paramInt8, paramInt9, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6, paramInt7);
  }
  
  public boolean optimizeFor(int paramInt)
  {
    boolean bool;
    if ((this.mOptimizationLevel & paramInt) == paramInt) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public void reset()
  {
    this.mSystem.reset();
    this.mPaddingLeft = 0;
    this.mPaddingRight = 0;
    this.mPaddingTop = 0;
    this.mPaddingBottom = 0;
    this.mSkipSolver = false;
    super.reset();
  }
  
  public void setMeasurer(BasicMeasure.Measurer paramMeasurer)
  {
    this.mMeasurer = paramMeasurer;
    this.mDependencyGraph.setMeasurer(paramMeasurer);
  }
  
  public void setOptimizationLevel(int paramInt)
  {
    this.mOptimizationLevel = paramInt;
    LinearSystem.USE_DEPENDENCY_ORDERING = optimizeFor(512);
  }
  
  public void setPadding(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    this.mPaddingLeft = paramInt1;
    this.mPaddingTop = paramInt2;
    this.mPaddingRight = paramInt3;
    this.mPaddingBottom = paramInt4;
  }
  
  public void setPass(int paramInt)
  {
    this.pass = paramInt;
  }
  
  public void setRtl(boolean paramBoolean)
  {
    this.mIsRtl = paramBoolean;
  }
  
  public boolean updateChildrenFromSolver(LinearSystem paramLinearSystem, boolean[] paramArrayOfBoolean)
  {
    paramArrayOfBoolean[2] = false;
    boolean bool2 = optimizeFor(64);
    updateFromSolver(paramLinearSystem, bool2);
    int j = this.mChildren.size();
    boolean bool1 = false;
    for (int i = 0; i < j; i++)
    {
      paramArrayOfBoolean = (ConstraintWidget)this.mChildren.get(i);
      paramArrayOfBoolean.updateFromSolver(paramLinearSystem, bool2);
      if (paramArrayOfBoolean.hasDimensionOverride()) {
        bool1 = true;
      }
    }
    return bool1;
  }
  
  public void updateFromRuns(boolean paramBoolean1, boolean paramBoolean2)
  {
    super.updateFromRuns(paramBoolean1, paramBoolean2);
    int j = this.mChildren.size();
    for (int i = 0; i < j; i++) {
      ((ConstraintWidget)this.mChildren.get(i)).updateFromRuns(paramBoolean1, paramBoolean2);
    }
  }
  
  public void updateHierarchy()
  {
    this.mBasicMeasureSolver.updateHierarchy(this);
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/constraintlayout/core/widgets/ConstraintWidgetContainer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */