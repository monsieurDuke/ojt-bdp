package androidx.constraintlayout.core.widgets;

import androidx.constraintlayout.core.Cache;
import androidx.constraintlayout.core.SolverVariable;
import androidx.constraintlayout.core.SolverVariable.Type;
import androidx.constraintlayout.core.widgets.analyzer.Grouping;
import androidx.constraintlayout.core.widgets.analyzer.WidgetGroup;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

public class ConstraintAnchor
{
  private static final boolean ALLOW_BINARY = false;
  private static final int UNSET_GONE_MARGIN = Integer.MIN_VALUE;
  private HashSet<ConstraintAnchor> mDependents = null;
  private int mFinalValue;
  int mGoneMargin = Integer.MIN_VALUE;
  private boolean mHasFinalValue;
  public int mMargin = 0;
  public final ConstraintWidget mOwner;
  SolverVariable mSolverVariable;
  public ConstraintAnchor mTarget;
  public final Type mType;
  
  public ConstraintAnchor(ConstraintWidget paramConstraintWidget, Type paramType)
  {
    this.mOwner = paramConstraintWidget;
    this.mType = paramType;
  }
  
  private boolean isConnectionToMe(ConstraintWidget paramConstraintWidget, HashSet<ConstraintWidget> paramHashSet)
  {
    if (paramHashSet.contains(paramConstraintWidget)) {
      return false;
    }
    paramHashSet.add(paramConstraintWidget);
    if (paramConstraintWidget == getOwner()) {
      return true;
    }
    ArrayList localArrayList = paramConstraintWidget.getAnchors();
    int i = 0;
    int j = localArrayList.size();
    while (i < j)
    {
      paramConstraintWidget = (ConstraintAnchor)localArrayList.get(i);
      if ((paramConstraintWidget.isSimilarDimensionConnection(this)) && (paramConstraintWidget.isConnected()) && (isConnectionToMe(paramConstraintWidget.getTarget().getOwner(), paramHashSet))) {
        return true;
      }
      i++;
    }
    return false;
  }
  
  public boolean connect(ConstraintAnchor paramConstraintAnchor, int paramInt)
  {
    return connect(paramConstraintAnchor, paramInt, Integer.MIN_VALUE, false);
  }
  
  public boolean connect(ConstraintAnchor paramConstraintAnchor, int paramInt1, int paramInt2, boolean paramBoolean)
  {
    if (paramConstraintAnchor == null)
    {
      reset();
      return true;
    }
    if ((!paramBoolean) && (!isValidConnection(paramConstraintAnchor))) {
      return false;
    }
    this.mTarget = paramConstraintAnchor;
    if (paramConstraintAnchor.mDependents == null) {
      paramConstraintAnchor.mDependents = new HashSet();
    }
    paramConstraintAnchor = this.mTarget.mDependents;
    if (paramConstraintAnchor != null) {
      paramConstraintAnchor.add(this);
    }
    this.mMargin = paramInt1;
    this.mGoneMargin = paramInt2;
    return true;
  }
  
  public void copyFrom(ConstraintAnchor paramConstraintAnchor, HashMap<ConstraintWidget, ConstraintWidget> paramHashMap)
  {
    Object localObject = this.mTarget;
    if (localObject != null)
    {
      localObject = ((ConstraintAnchor)localObject).mDependents;
      if (localObject != null) {
        ((HashSet)localObject).remove(this);
      }
    }
    localObject = paramConstraintAnchor.mTarget;
    if (localObject != null)
    {
      localObject = ((ConstraintAnchor)localObject).getType();
      this.mTarget = ((ConstraintWidget)paramHashMap.get(paramConstraintAnchor.mTarget.mOwner)).getAnchor((Type)localObject);
    }
    else
    {
      this.mTarget = null;
    }
    paramHashMap = this.mTarget;
    if (paramHashMap != null)
    {
      if (paramHashMap.mDependents == null) {
        paramHashMap.mDependents = new HashSet();
      }
      this.mTarget.mDependents.add(this);
    }
    this.mMargin = paramConstraintAnchor.mMargin;
    this.mGoneMargin = paramConstraintAnchor.mGoneMargin;
  }
  
  public void findDependents(int paramInt, ArrayList<WidgetGroup> paramArrayList, WidgetGroup paramWidgetGroup)
  {
    Object localObject = this.mDependents;
    if (localObject != null)
    {
      localObject = ((HashSet)localObject).iterator();
      while (((Iterator)localObject).hasNext()) {
        Grouping.findDependents(((ConstraintAnchor)((Iterator)localObject).next()).mOwner, paramInt, paramArrayList, paramWidgetGroup);
      }
    }
  }
  
  public HashSet<ConstraintAnchor> getDependents()
  {
    return this.mDependents;
  }
  
  public int getFinalValue()
  {
    if (!this.mHasFinalValue) {
      return 0;
    }
    return this.mFinalValue;
  }
  
  public int getMargin()
  {
    if (this.mOwner.getVisibility() == 8) {
      return 0;
    }
    if (this.mGoneMargin != Integer.MIN_VALUE)
    {
      ConstraintAnchor localConstraintAnchor = this.mTarget;
      if ((localConstraintAnchor != null) && (localConstraintAnchor.mOwner.getVisibility() == 8)) {
        return this.mGoneMargin;
      }
    }
    return this.mMargin;
  }
  
  public final ConstraintAnchor getOpposite()
  {
    switch (1.$SwitchMap$androidx$constraintlayout$core$widgets$ConstraintAnchor$Type[this.mType.ordinal()])
    {
    default: 
      throw new AssertionError(this.mType.name());
    case 5: 
      return this.mOwner.mTop;
    case 4: 
      return this.mOwner.mBottom;
    case 3: 
      return this.mOwner.mLeft;
    case 2: 
      return this.mOwner.mRight;
    }
    return null;
  }
  
  public ConstraintWidget getOwner()
  {
    return this.mOwner;
  }
  
  public SolverVariable getSolverVariable()
  {
    return this.mSolverVariable;
  }
  
  public ConstraintAnchor getTarget()
  {
    return this.mTarget;
  }
  
  public Type getType()
  {
    return this.mType;
  }
  
  public boolean hasCenteredDependents()
  {
    Object localObject = this.mDependents;
    if (localObject == null) {
      return false;
    }
    localObject = ((HashSet)localObject).iterator();
    while (((Iterator)localObject).hasNext()) {
      if (((ConstraintAnchor)((Iterator)localObject).next()).getOpposite().isConnected()) {
        return true;
      }
    }
    return false;
  }
  
  public boolean hasDependents()
  {
    HashSet localHashSet = this.mDependents;
    boolean bool = false;
    if (localHashSet == null) {
      return false;
    }
    if (localHashSet.size() > 0) {
      bool = true;
    }
    return bool;
  }
  
  public boolean hasFinalValue()
  {
    return this.mHasFinalValue;
  }
  
  public boolean isConnected()
  {
    boolean bool;
    if (this.mTarget != null) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public boolean isConnectionAllowed(ConstraintWidget paramConstraintWidget)
  {
    if (isConnectionToMe(paramConstraintWidget, new HashSet())) {
      return false;
    }
    ConstraintWidget localConstraintWidget = getOwner().getParent();
    if (localConstraintWidget == paramConstraintWidget) {
      return true;
    }
    return paramConstraintWidget.getParent() == localConstraintWidget;
  }
  
  public boolean isConnectionAllowed(ConstraintWidget paramConstraintWidget, ConstraintAnchor paramConstraintAnchor)
  {
    return isConnectionAllowed(paramConstraintWidget);
  }
  
  public boolean isSideAnchor()
  {
    switch (1.$SwitchMap$androidx$constraintlayout$core$widgets$ConstraintAnchor$Type[this.mType.ordinal()])
    {
    default: 
      throw new AssertionError(this.mType.name());
    case 2: 
    case 3: 
    case 4: 
    case 5: 
      return true;
    }
    return false;
  }
  
  public boolean isSimilarDimensionConnection(ConstraintAnchor paramConstraintAnchor)
  {
    paramConstraintAnchor = paramConstraintAnchor.getType();
    Type localType = this.mType;
    boolean bool2 = true;
    boolean bool1 = true;
    boolean bool3 = true;
    if (paramConstraintAnchor == localType) {
      return true;
    }
    switch (1.$SwitchMap$androidx$constraintlayout$core$widgets$ConstraintAnchor$Type[this.mType.ordinal()])
    {
    default: 
      throw new AssertionError(this.mType.name());
    case 9: 
      return false;
    case 4: 
    case 5: 
    case 6: 
    case 8: 
      bool1 = bool3;
      if (paramConstraintAnchor != Type.TOP)
      {
        bool1 = bool3;
        if (paramConstraintAnchor != Type.BOTTOM)
        {
          bool1 = bool3;
          if (paramConstraintAnchor != Type.CENTER_Y) {
            if (paramConstraintAnchor == Type.BASELINE) {
              bool1 = bool3;
            } else {
              bool1 = false;
            }
          }
        }
      }
      return bool1;
    case 2: 
    case 3: 
    case 7: 
      bool1 = bool2;
      if (paramConstraintAnchor != Type.LEFT)
      {
        bool1 = bool2;
        if (paramConstraintAnchor != Type.RIGHT) {
          if (paramConstraintAnchor == Type.CENTER_X) {
            bool1 = bool2;
          } else {
            bool1 = false;
          }
        }
      }
      return bool1;
    }
    if (paramConstraintAnchor == Type.BASELINE) {
      bool1 = false;
    }
    return bool1;
  }
  
  public boolean isValidConnection(ConstraintAnchor paramConstraintAnchor)
  {
    boolean bool3 = false;
    boolean bool2 = false;
    boolean bool4 = false;
    if (paramConstraintAnchor == null) {
      return false;
    }
    Type localType2 = paramConstraintAnchor.getType();
    Type localType1 = this.mType;
    if (localType2 == localType1) {
      return (localType1 != Type.BASELINE) || ((paramConstraintAnchor.getOwner().hasBaseline()) && (getOwner().hasBaseline()));
    }
    switch (1.$SwitchMap$androidx$constraintlayout$core$widgets$ConstraintAnchor$Type[this.mType.ordinal()])
    {
    default: 
      throw new AssertionError(this.mType.name());
    case 7: 
    case 8: 
    case 9: 
      return false;
    case 6: 
      return (localType2 != Type.LEFT) && (localType2 != Type.RIGHT);
    case 4: 
    case 5: 
      if ((localType2 != Type.TOP) && (localType2 != Type.BOTTOM)) {
        bool1 = false;
      } else {
        bool1 = true;
      }
      bool2 = bool1;
      if ((paramConstraintAnchor.getOwner() instanceof Guideline))
      {
        if (!bool1)
        {
          bool1 = bool4;
          if (localType2 != Type.CENTER_Y) {}
        }
        else
        {
          bool1 = true;
        }
        bool2 = bool1;
      }
      return bool2;
    case 2: 
    case 3: 
      if ((localType2 != Type.LEFT) && (localType2 != Type.RIGHT)) {
        bool1 = false;
      } else {
        bool1 = true;
      }
      bool2 = bool1;
      if ((paramConstraintAnchor.getOwner() instanceof Guideline))
      {
        if (!bool1)
        {
          bool1 = bool3;
          if (localType2 != Type.CENTER_X) {}
        }
        else
        {
          bool1 = true;
        }
        bool2 = bool1;
      }
      return bool2;
    }
    boolean bool1 = bool2;
    if (localType2 != Type.BASELINE)
    {
      bool1 = bool2;
      if (localType2 != Type.CENTER_X)
      {
        bool1 = bool2;
        if (localType2 != Type.CENTER_Y) {
          bool1 = true;
        }
      }
    }
    return bool1;
  }
  
  public boolean isVerticalAnchor()
  {
    switch (1.$SwitchMap$androidx$constraintlayout$core$widgets$ConstraintAnchor$Type[this.mType.ordinal()])
    {
    default: 
      throw new AssertionError(this.mType.name());
    case 4: 
    case 5: 
    case 6: 
    case 8: 
    case 9: 
      return true;
    }
    return false;
  }
  
  public void reset()
  {
    Object localObject = this.mTarget;
    if (localObject != null)
    {
      localObject = ((ConstraintAnchor)localObject).mDependents;
      if (localObject != null)
      {
        ((HashSet)localObject).remove(this);
        if (this.mTarget.mDependents.size() == 0) {
          this.mTarget.mDependents = null;
        }
      }
    }
    this.mDependents = null;
    this.mTarget = null;
    this.mMargin = 0;
    this.mGoneMargin = Integer.MIN_VALUE;
    this.mHasFinalValue = false;
    this.mFinalValue = 0;
  }
  
  public void resetFinalResolution()
  {
    this.mHasFinalValue = false;
    this.mFinalValue = 0;
  }
  
  public void resetSolverVariable(Cache paramCache)
  {
    paramCache = this.mSolverVariable;
    if (paramCache == null) {
      this.mSolverVariable = new SolverVariable(SolverVariable.Type.UNRESTRICTED, null);
    } else {
      paramCache.reset();
    }
  }
  
  public void setFinalValue(int paramInt)
  {
    this.mFinalValue = paramInt;
    this.mHasFinalValue = true;
  }
  
  public void setGoneMargin(int paramInt)
  {
    if (isConnected()) {
      this.mGoneMargin = paramInt;
    }
  }
  
  public void setMargin(int paramInt)
  {
    if (isConnected()) {
      this.mMargin = paramInt;
    }
  }
  
  public String toString()
  {
    return this.mOwner.getDebugName() + ":" + this.mType.toString();
  }
  
  public static enum Type
  {
    private static final Type[] $VALUES;
    
    static
    {
      Type localType3 = new Type("NONE", 0);
      NONE = localType3;
      Type localType1 = new Type("LEFT", 1);
      LEFT = localType1;
      Type localType2 = new Type("TOP", 2);
      TOP = localType2;
      Type localType9 = new Type("RIGHT", 3);
      RIGHT = localType9;
      Type localType5 = new Type("BOTTOM", 4);
      BOTTOM = localType5;
      Type localType7 = new Type("BASELINE", 5);
      BASELINE = localType7;
      Type localType4 = new Type("CENTER", 6);
      CENTER = localType4;
      Type localType6 = new Type("CENTER_X", 7);
      CENTER_X = localType6;
      Type localType8 = new Type("CENTER_Y", 8);
      CENTER_Y = localType8;
      $VALUES = new Type[] { localType3, localType1, localType2, localType9, localType5, localType7, localType4, localType6, localType8 };
    }
    
    private Type() {}
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/constraintlayout/core/widgets/ConstraintAnchor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */