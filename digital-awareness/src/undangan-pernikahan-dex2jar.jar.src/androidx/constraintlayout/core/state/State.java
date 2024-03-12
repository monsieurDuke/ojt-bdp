package androidx.constraintlayout.core.state;

import androidx.constraintlayout.core.state.helpers.AlignHorizontallyReference;
import androidx.constraintlayout.core.state.helpers.AlignVerticallyReference;
import androidx.constraintlayout.core.state.helpers.BarrierReference;
import androidx.constraintlayout.core.state.helpers.Facade;
import androidx.constraintlayout.core.state.helpers.GuidelineReference;
import androidx.constraintlayout.core.state.helpers.HorizontalChainReference;
import androidx.constraintlayout.core.state.helpers.VerticalChainReference;
import androidx.constraintlayout.core.widgets.ConstraintWidget;
import androidx.constraintlayout.core.widgets.ConstraintWidgetContainer;
import androidx.constraintlayout.core.widgets.HelperWidget;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

public class State
{
  static final int CONSTRAINT_RATIO = 2;
  static final int CONSTRAINT_SPREAD = 0;
  static final int CONSTRAINT_WRAP = 1;
  public static final Integer PARENT = Integer.valueOf(0);
  static final int UNKNOWN = -1;
  protected HashMap<Object, HelperReference> mHelperReferences = new HashMap();
  public final ConstraintReference mParent;
  protected HashMap<Object, Reference> mReferences = new HashMap();
  HashMap<String, ArrayList<String>> mTags = new HashMap();
  private int numHelpers;
  
  public State()
  {
    ConstraintReference localConstraintReference = new ConstraintReference(this);
    this.mParent = localConstraintReference;
    this.numHelpers = 0;
    this.mReferences.put(PARENT, localConstraintReference);
  }
  
  private String createHelperKey()
  {
    StringBuilder localStringBuilder = new StringBuilder().append("__HELPER_KEY_");
    int i = this.numHelpers;
    this.numHelpers = (i + 1);
    return i + "__";
  }
  
  public void apply(ConstraintWidgetContainer paramConstraintWidgetContainer)
  {
    paramConstraintWidgetContainer.removeAllChildren();
    this.mParent.getWidth().apply(this, paramConstraintWidgetContainer, 0);
    this.mParent.getHeight().apply(this, paramConstraintWidgetContainer, 1);
    Object localObject4 = this.mHelperReferences.keySet().iterator();
    Object localObject5;
    Object localObject2;
    while (((Iterator)localObject4).hasNext())
    {
      localObject5 = ((Iterator)localObject4).next();
      localObject3 = ((HelperReference)this.mHelperReferences.get(localObject5)).getHelperWidget();
      if (localObject3 != null)
      {
        localObject2 = (Reference)this.mReferences.get(localObject5);
        localObject1 = localObject2;
        if (localObject2 == null) {
          localObject1 = constraints(localObject5);
        }
        ((Reference)localObject1).setConstraintWidget((ConstraintWidget)localObject3);
      }
    }
    Object localObject3 = this.mReferences.keySet().iterator();
    while (((Iterator)localObject3).hasNext())
    {
      localObject5 = ((Iterator)localObject3).next();
      localObject1 = (Reference)this.mReferences.get(localObject5);
      if ((localObject1 != this.mParent) && ((((Reference)localObject1).getFacade() instanceof HelperReference)))
      {
        localObject4 = ((HelperReference)((Reference)localObject1).getFacade()).getHelperWidget();
        if (localObject4 != null)
        {
          localObject2 = (Reference)this.mReferences.get(localObject5);
          localObject1 = localObject2;
          if (localObject2 == null) {
            localObject1 = constraints(localObject5);
          }
          ((Reference)localObject1).setConstraintWidget((ConstraintWidget)localObject4);
        }
      }
    }
    Object localObject1 = this.mReferences.keySet().iterator();
    while (((Iterator)localObject1).hasNext())
    {
      localObject2 = ((Iterator)localObject1).next();
      localObject2 = (Reference)this.mReferences.get(localObject2);
      if (localObject2 != this.mParent)
      {
        localObject3 = ((Reference)localObject2).getConstraintWidget();
        ((ConstraintWidget)localObject3).setDebugName(((Reference)localObject2).getKey().toString());
        ((ConstraintWidget)localObject3).setParent(null);
        if ((((Reference)localObject2).getFacade() instanceof GuidelineReference)) {
          ((Reference)localObject2).apply();
        }
        paramConstraintWidgetContainer.add((ConstraintWidget)localObject3);
      }
      else
      {
        ((Reference)localObject2).setConstraintWidget(paramConstraintWidgetContainer);
      }
    }
    paramConstraintWidgetContainer = this.mHelperReferences.keySet().iterator();
    while (paramConstraintWidgetContainer.hasNext())
    {
      localObject1 = paramConstraintWidgetContainer.next();
      localObject1 = (HelperReference)this.mHelperReferences.get(localObject1);
      if (((HelperReference)localObject1).getHelperWidget() != null)
      {
        localObject2 = ((HelperReference)localObject1).mReferences.iterator();
        while (((Iterator)localObject2).hasNext())
        {
          localObject3 = ((Iterator)localObject2).next();
          localObject3 = (Reference)this.mReferences.get(localObject3);
          ((HelperReference)localObject1).getHelperWidget().add(((Reference)localObject3).getConstraintWidget());
        }
        ((HelperReference)localObject1).apply();
      }
      else
      {
        ((HelperReference)localObject1).apply();
      }
    }
    paramConstraintWidgetContainer = this.mReferences.keySet().iterator();
    while (paramConstraintWidgetContainer.hasNext())
    {
      localObject1 = paramConstraintWidgetContainer.next();
      localObject1 = (Reference)this.mReferences.get(localObject1);
      if ((localObject1 != this.mParent) && ((((Reference)localObject1).getFacade() instanceof HelperReference)))
      {
        localObject3 = (HelperReference)((Reference)localObject1).getFacade();
        localObject2 = ((HelperReference)localObject3).getHelperWidget();
        if (localObject2 != null)
        {
          localObject4 = ((HelperReference)localObject3).mReferences.iterator();
          while (((Iterator)localObject4).hasNext())
          {
            localObject5 = ((Iterator)localObject4).next();
            localObject3 = (Reference)this.mReferences.get(localObject5);
            if (localObject3 != null) {
              ((HelperWidget)localObject2).add(((Reference)localObject3).getConstraintWidget());
            } else if ((localObject5 instanceof Reference)) {
              ((HelperWidget)localObject2).add(((Reference)localObject5).getConstraintWidget());
            } else {
              System.out.println("couldn't find reference for " + localObject5);
            }
          }
          ((Reference)localObject1).apply();
        }
      }
    }
    paramConstraintWidgetContainer = this.mReferences.keySet().iterator();
    while (paramConstraintWidgetContainer.hasNext())
    {
      localObject1 = paramConstraintWidgetContainer.next();
      localObject2 = (Reference)this.mReferences.get(localObject1);
      ((Reference)localObject2).apply();
      localObject2 = ((Reference)localObject2).getConstraintWidget();
      if ((localObject2 != null) && (localObject1 != null)) {
        ((ConstraintWidget)localObject2).stringId = localObject1.toString();
      }
    }
  }
  
  public BarrierReference barrier(Object paramObject, Direction paramDirection)
  {
    ConstraintReference localConstraintReference = constraints(paramObject);
    if ((localConstraintReference.getFacade() == null) || (!(localConstraintReference.getFacade() instanceof BarrierReference)))
    {
      paramObject = new BarrierReference(this);
      ((BarrierReference)paramObject).setBarrierDirection(paramDirection);
      localConstraintReference.setFacade((Facade)paramObject);
    }
    return (BarrierReference)localConstraintReference.getFacade();
  }
  
  public AlignHorizontallyReference centerHorizontally(Object... paramVarArgs)
  {
    AlignHorizontallyReference localAlignHorizontallyReference = (AlignHorizontallyReference)helper(null, Helper.ALIGN_HORIZONTALLY);
    localAlignHorizontallyReference.add(paramVarArgs);
    return localAlignHorizontallyReference;
  }
  
  public AlignVerticallyReference centerVertically(Object... paramVarArgs)
  {
    AlignVerticallyReference localAlignVerticallyReference = (AlignVerticallyReference)helper(null, Helper.ALIGN_VERTICALLY);
    localAlignVerticallyReference.add(paramVarArgs);
    return localAlignVerticallyReference;
  }
  
  public ConstraintReference constraints(Object paramObject)
  {
    Reference localReference = (Reference)this.mReferences.get(paramObject);
    Object localObject = localReference;
    if (localReference == null)
    {
      localObject = createConstraintReference(paramObject);
      this.mReferences.put(paramObject, localObject);
      ((Reference)localObject).setKey(paramObject);
    }
    if ((localObject instanceof ConstraintReference)) {
      return (ConstraintReference)localObject;
    }
    return null;
  }
  
  public int convertDimension(Object paramObject)
  {
    if ((paramObject instanceof Float)) {
      return ((Float)paramObject).intValue();
    }
    if ((paramObject instanceof Integer)) {
      return ((Integer)paramObject).intValue();
    }
    return 0;
  }
  
  public ConstraintReference createConstraintReference(Object paramObject)
  {
    return new ConstraintReference(this);
  }
  
  public void directMapping()
  {
    Iterator localIterator = this.mReferences.keySet().iterator();
    while (localIterator.hasNext())
    {
      Object localObject = localIterator.next();
      ConstraintReference localConstraintReference = constraints(localObject);
      if ((localConstraintReference instanceof ConstraintReference)) {
        ((ConstraintReference)localConstraintReference).setView(localObject);
      }
    }
  }
  
  public ArrayList<String> getIdsForTag(String paramString)
  {
    if (this.mTags.containsKey(paramString)) {
      return (ArrayList)this.mTags.get(paramString);
    }
    return null;
  }
  
  public GuidelineReference guideline(Object paramObject, int paramInt)
  {
    ConstraintReference localConstraintReference = constraints(paramObject);
    if ((localConstraintReference.getFacade() == null) || (!(localConstraintReference.getFacade() instanceof GuidelineReference)))
    {
      GuidelineReference localGuidelineReference = new GuidelineReference(this);
      localGuidelineReference.setOrientation(paramInt);
      localGuidelineReference.setKey(paramObject);
      localConstraintReference.setFacade(localGuidelineReference);
    }
    return (GuidelineReference)localConstraintReference.getFacade();
  }
  
  public State height(Dimension paramDimension)
  {
    return setHeight(paramDimension);
  }
  
  public HelperReference helper(Object paramObject, Helper paramHelper)
  {
    Object localObject = paramObject;
    if (paramObject == null) {
      localObject = createHelperKey();
    }
    HelperReference localHelperReference = (HelperReference)this.mHelperReferences.get(localObject);
    paramObject = localHelperReference;
    if (localHelperReference == null)
    {
      switch (1.$SwitchMap$androidx$constraintlayout$core$state$State$Helper[paramHelper.ordinal()])
      {
      default: 
        paramObject = new HelperReference(this, paramHelper);
        break;
      case 5: 
        paramObject = new BarrierReference(this);
        break;
      case 4: 
        paramObject = new AlignVerticallyReference(this);
        break;
      case 3: 
        paramObject = new AlignHorizontallyReference(this);
        break;
      case 2: 
        paramObject = new VerticalChainReference(this);
        break;
      case 1: 
        paramObject = new HorizontalChainReference(this);
      }
      ((HelperReference)paramObject).setKey(localObject);
      this.mHelperReferences.put(localObject, paramObject);
    }
    return (HelperReference)paramObject;
  }
  
  public HorizontalChainReference horizontalChain()
  {
    return (HorizontalChainReference)helper(null, Helper.HORIZONTAL_CHAIN);
  }
  
  public HorizontalChainReference horizontalChain(Object... paramVarArgs)
  {
    HorizontalChainReference localHorizontalChainReference = (HorizontalChainReference)helper(null, Helper.HORIZONTAL_CHAIN);
    localHorizontalChainReference.add(paramVarArgs);
    return localHorizontalChainReference;
  }
  
  public GuidelineReference horizontalGuideline(Object paramObject)
  {
    return guideline(paramObject, 0);
  }
  
  public void map(Object paramObject1, Object paramObject2)
  {
    paramObject1 = constraints(paramObject1);
    if ((paramObject1 instanceof ConstraintReference)) {
      ((ConstraintReference)paramObject1).setView(paramObject2);
    }
  }
  
  Reference reference(Object paramObject)
  {
    return (Reference)this.mReferences.get(paramObject);
  }
  
  public void reset()
  {
    this.mHelperReferences.clear();
    this.mTags.clear();
  }
  
  public boolean sameFixedHeight(int paramInt)
  {
    return this.mParent.getHeight().equalsFixedValue(paramInt);
  }
  
  public boolean sameFixedWidth(int paramInt)
  {
    return this.mParent.getWidth().equalsFixedValue(paramInt);
  }
  
  public State setHeight(Dimension paramDimension)
  {
    this.mParent.setHeight(paramDimension);
    return this;
  }
  
  public void setTag(String paramString1, String paramString2)
  {
    Object localObject = constraints(paramString1);
    if ((localObject instanceof ConstraintReference))
    {
      ((ConstraintReference)localObject).setTag(paramString2);
      if (!this.mTags.containsKey(paramString2))
      {
        localObject = new ArrayList();
        this.mTags.put(paramString2, localObject);
        paramString2 = (String)localObject;
      }
      else
      {
        paramString2 = (ArrayList)this.mTags.get(paramString2);
      }
      paramString2.add(paramString1);
    }
  }
  
  public State setWidth(Dimension paramDimension)
  {
    this.mParent.setWidth(paramDimension);
    return this;
  }
  
  public VerticalChainReference verticalChain()
  {
    return (VerticalChainReference)helper(null, Helper.VERTICAL_CHAIN);
  }
  
  public VerticalChainReference verticalChain(Object... paramVarArgs)
  {
    VerticalChainReference localVerticalChainReference = (VerticalChainReference)helper(null, Helper.VERTICAL_CHAIN);
    localVerticalChainReference.add(paramVarArgs);
    return localVerticalChainReference;
  }
  
  public GuidelineReference verticalGuideline(Object paramObject)
  {
    return guideline(paramObject, 1);
  }
  
  public State width(Dimension paramDimension)
  {
    return setWidth(paramDimension);
  }
  
  public static enum Chain
  {
    private static final Chain[] $VALUES;
    
    static
    {
      Chain localChain1 = new Chain("SPREAD", 0);
      SPREAD = localChain1;
      Chain localChain3 = new Chain("SPREAD_INSIDE", 1);
      SPREAD_INSIDE = localChain3;
      Chain localChain2 = new Chain("PACKED", 2);
      PACKED = localChain2;
      $VALUES = new Chain[] { localChain1, localChain3, localChain2 };
    }
    
    private Chain() {}
  }
  
  public static enum Constraint
  {
    private static final Constraint[] $VALUES;
    
    static
    {
      Constraint localConstraint6 = new Constraint("LEFT_TO_LEFT", 0);
      LEFT_TO_LEFT = localConstraint6;
      Constraint localConstraint13 = new Constraint("LEFT_TO_RIGHT", 1);
      LEFT_TO_RIGHT = localConstraint13;
      Constraint localConstraint8 = new Constraint("RIGHT_TO_LEFT", 2);
      RIGHT_TO_LEFT = localConstraint8;
      Constraint localConstraint5 = new Constraint("RIGHT_TO_RIGHT", 3);
      RIGHT_TO_RIGHT = localConstraint5;
      Constraint localConstraint15 = new Constraint("START_TO_START", 4);
      START_TO_START = localConstraint15;
      Constraint localConstraint4 = new Constraint("START_TO_END", 5);
      START_TO_END = localConstraint4;
      Constraint localConstraint3 = new Constraint("END_TO_START", 6);
      END_TO_START = localConstraint3;
      Constraint localConstraint16 = new Constraint("END_TO_END", 7);
      END_TO_END = localConstraint16;
      Constraint localConstraint17 = new Constraint("TOP_TO_TOP", 8);
      TOP_TO_TOP = localConstraint17;
      Constraint localConstraint2 = new Constraint("TOP_TO_BOTTOM", 9);
      TOP_TO_BOTTOM = localConstraint2;
      Constraint localConstraint18 = new Constraint("BOTTOM_TO_TOP", 10);
      BOTTOM_TO_TOP = localConstraint18;
      Constraint localConstraint11 = new Constraint("BOTTOM_TO_BOTTOM", 11);
      BOTTOM_TO_BOTTOM = localConstraint11;
      Constraint localConstraint9 = new Constraint("BASELINE_TO_BASELINE", 12);
      BASELINE_TO_BASELINE = localConstraint9;
      Constraint localConstraint7 = new Constraint("BASELINE_TO_TOP", 13);
      BASELINE_TO_TOP = localConstraint7;
      Constraint localConstraint1 = new Constraint("BASELINE_TO_BOTTOM", 14);
      BASELINE_TO_BOTTOM = localConstraint1;
      Constraint localConstraint10 = new Constraint("CENTER_HORIZONTALLY", 15);
      CENTER_HORIZONTALLY = localConstraint10;
      Constraint localConstraint12 = new Constraint("CENTER_VERTICALLY", 16);
      CENTER_VERTICALLY = localConstraint12;
      Constraint localConstraint14 = new Constraint("CIRCULAR_CONSTRAINT", 17);
      CIRCULAR_CONSTRAINT = localConstraint14;
      $VALUES = new Constraint[] { localConstraint6, localConstraint13, localConstraint8, localConstraint5, localConstraint15, localConstraint4, localConstraint3, localConstraint16, localConstraint17, localConstraint2, localConstraint18, localConstraint11, localConstraint9, localConstraint7, localConstraint1, localConstraint10, localConstraint12, localConstraint14 };
    }
    
    private Constraint() {}
  }
  
  public static enum Direction
  {
    private static final Direction[] $VALUES;
    
    static
    {
      Direction localDirection4 = new Direction("LEFT", 0);
      LEFT = localDirection4;
      Direction localDirection1 = new Direction("RIGHT", 1);
      RIGHT = localDirection1;
      Direction localDirection2 = new Direction("START", 2);
      START = localDirection2;
      Direction localDirection3 = new Direction("END", 3);
      END = localDirection3;
      Direction localDirection5 = new Direction("TOP", 4);
      TOP = localDirection5;
      Direction localDirection6 = new Direction("BOTTOM", 5);
      BOTTOM = localDirection6;
      $VALUES = new Direction[] { localDirection4, localDirection1, localDirection2, localDirection3, localDirection5, localDirection6 };
    }
    
    private Direction() {}
  }
  
  public static enum Helper
  {
    private static final Helper[] $VALUES;
    
    static
    {
      Helper localHelper2 = new Helper("HORIZONTAL_CHAIN", 0);
      HORIZONTAL_CHAIN = localHelper2;
      Helper localHelper3 = new Helper("VERTICAL_CHAIN", 1);
      VERTICAL_CHAIN = localHelper3;
      Helper localHelper6 = new Helper("ALIGN_HORIZONTALLY", 2);
      ALIGN_HORIZONTALLY = localHelper6;
      Helper localHelper1 = new Helper("ALIGN_VERTICALLY", 3);
      ALIGN_VERTICALLY = localHelper1;
      Helper localHelper4 = new Helper("BARRIER", 4);
      BARRIER = localHelper4;
      Helper localHelper7 = new Helper("LAYER", 5);
      LAYER = localHelper7;
      Helper localHelper5 = new Helper("FLOW", 6);
      FLOW = localHelper5;
      $VALUES = new Helper[] { localHelper2, localHelper3, localHelper6, localHelper1, localHelper4, localHelper7, localHelper5 };
    }
    
    private Helper() {}
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/constraintlayout/core/state/State.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */