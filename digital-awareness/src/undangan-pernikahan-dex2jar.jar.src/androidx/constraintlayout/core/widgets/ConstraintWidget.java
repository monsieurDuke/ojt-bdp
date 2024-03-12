package androidx.constraintlayout.core.widgets;

import androidx.constraintlayout.core.ArrayRow;
import androidx.constraintlayout.core.Cache;
import androidx.constraintlayout.core.LinearSystem;
import androidx.constraintlayout.core.Metrics;
import androidx.constraintlayout.core.SolverVariable;
import androidx.constraintlayout.core.state.WidgetFrame;
import androidx.constraintlayout.core.widgets.analyzer.ChainRun;
import androidx.constraintlayout.core.widgets.analyzer.DependencyNode;
import androidx.constraintlayout.core.widgets.analyzer.HorizontalWidgetRun;
import androidx.constraintlayout.core.widgets.analyzer.VerticalWidgetRun;
import androidx.constraintlayout.core.widgets.analyzer.WidgetRun;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

public class ConstraintWidget
{
  public static final int ANCHOR_BASELINE = 4;
  public static final int ANCHOR_BOTTOM = 3;
  public static final int ANCHOR_LEFT = 0;
  public static final int ANCHOR_RIGHT = 1;
  public static final int ANCHOR_TOP = 2;
  private static final boolean AUTOTAG_CENTER = false;
  public static final int BOTH = 2;
  public static final int CHAIN_PACKED = 2;
  public static final int CHAIN_SPREAD = 0;
  public static final int CHAIN_SPREAD_INSIDE = 1;
  public static float DEFAULT_BIAS = 0.5F;
  static final int DIMENSION_HORIZONTAL = 0;
  static final int DIMENSION_VERTICAL = 1;
  protected static final int DIRECT = 2;
  public static final int GONE = 8;
  public static final int HORIZONTAL = 0;
  public static final int INVISIBLE = 4;
  public static final int MATCH_CONSTRAINT_PERCENT = 2;
  public static final int MATCH_CONSTRAINT_RATIO = 3;
  public static final int MATCH_CONSTRAINT_RATIO_RESOLVED = 4;
  public static final int MATCH_CONSTRAINT_SPREAD = 0;
  public static final int MATCH_CONSTRAINT_WRAP = 1;
  protected static final int SOLVER = 1;
  public static final int UNKNOWN = -1;
  private static final boolean USE_WRAP_DIMENSION_FOR_SPREAD = false;
  public static final int VERTICAL = 1;
  public static final int VISIBLE = 0;
  private static final int WRAP = -2;
  public static final int WRAP_BEHAVIOR_HORIZONTAL_ONLY = 1;
  public static final int WRAP_BEHAVIOR_INCLUDED = 0;
  public static final int WRAP_BEHAVIOR_SKIPPED = 3;
  public static final int WRAP_BEHAVIOR_VERTICAL_ONLY = 2;
  private boolean OPTIMIZE_WRAP = false;
  private boolean OPTIMIZE_WRAP_ON_RESOLVED = true;
  public WidgetFrame frame = new WidgetFrame(this);
  private boolean hasBaseline = false;
  public ChainRun horizontalChainRun;
  public int horizontalGroup;
  public HorizontalWidgetRun horizontalRun = null;
  private boolean horizontalSolvingPass = false;
  private boolean inPlaceholder;
  public boolean[] isTerminalWidget = { 1, 1 };
  protected ArrayList<ConstraintAnchor> mAnchors;
  private boolean mAnimated;
  public ConstraintAnchor mBaseline = new ConstraintAnchor(this, ConstraintAnchor.Type.BASELINE);
  int mBaselineDistance;
  public ConstraintAnchor mBottom = new ConstraintAnchor(this, ConstraintAnchor.Type.BOTTOM);
  boolean mBottomHasCentered;
  public ConstraintAnchor mCenter;
  ConstraintAnchor mCenterX = new ConstraintAnchor(this, ConstraintAnchor.Type.CENTER_X);
  ConstraintAnchor mCenterY = new ConstraintAnchor(this, ConstraintAnchor.Type.CENTER_Y);
  private float mCircleConstraintAngle = 0.0F;
  private Object mCompanionWidget;
  private int mContainerItemSkip;
  private String mDebugName;
  public float mDimensionRatio;
  protected int mDimensionRatioSide;
  int mDistToBottom;
  int mDistToLeft;
  int mDistToRight;
  int mDistToTop;
  boolean mGroupsToSolver;
  int mHeight;
  private int mHeightOverride = -1;
  float mHorizontalBiasPercent;
  boolean mHorizontalChainFixedPosition;
  int mHorizontalChainStyle;
  ConstraintWidget mHorizontalNextWidget;
  public int mHorizontalResolution = -1;
  boolean mHorizontalWrapVisited;
  private boolean mInVirtualLayout = false;
  public boolean mIsHeightWrapContent;
  private boolean[] mIsInBarrier;
  public boolean mIsWidthWrapContent;
  private int mLastHorizontalMeasureSpec = 0;
  private int mLastVerticalMeasureSpec = 0;
  public ConstraintAnchor mLeft = new ConstraintAnchor(this, ConstraintAnchor.Type.LEFT);
  boolean mLeftHasCentered;
  public ConstraintAnchor[] mListAnchors;
  public DimensionBehaviour[] mListDimensionBehaviors;
  protected ConstraintWidget[] mListNextMatchConstraintsWidget;
  public int mMatchConstraintDefaultHeight = 0;
  public int mMatchConstraintDefaultWidth = 0;
  public int mMatchConstraintMaxHeight = 0;
  public int mMatchConstraintMaxWidth = 0;
  public int mMatchConstraintMinHeight = 0;
  public int mMatchConstraintMinWidth = 0;
  public float mMatchConstraintPercentHeight = 1.0F;
  public float mMatchConstraintPercentWidth = 1.0F;
  private int[] mMaxDimension = { Integer.MAX_VALUE, Integer.MAX_VALUE };
  private boolean mMeasureRequested = true;
  protected int mMinHeight;
  protected int mMinWidth;
  protected ConstraintWidget[] mNextChainWidget;
  protected int mOffsetX;
  protected int mOffsetY;
  public ConstraintWidget mParent;
  int mRelX;
  int mRelY;
  float mResolvedDimensionRatio = 1.0F;
  int mResolvedDimensionRatioSide = -1;
  boolean mResolvedHasRatio = false;
  public int[] mResolvedMatchConstraintDefault = new int[2];
  public ConstraintAnchor mRight = new ConstraintAnchor(this, ConstraintAnchor.Type.RIGHT);
  boolean mRightHasCentered;
  public ConstraintAnchor mTop = new ConstraintAnchor(this, ConstraintAnchor.Type.TOP);
  boolean mTopHasCentered;
  private String mType;
  float mVerticalBiasPercent;
  boolean mVerticalChainFixedPosition;
  int mVerticalChainStyle;
  ConstraintWidget mVerticalNextWidget;
  public int mVerticalResolution = -1;
  boolean mVerticalWrapVisited;
  private int mVisibility;
  public float[] mWeight;
  int mWidth;
  private int mWidthOverride = -1;
  private int mWrapBehaviorInParent = 0;
  protected int mX;
  protected int mY;
  public boolean measured = false;
  private boolean resolvedHorizontal = false;
  private boolean resolvedVertical = false;
  public WidgetRun[] run = new WidgetRun[2];
  public String stringId;
  public ChainRun verticalChainRun;
  public int verticalGroup;
  public VerticalWidgetRun verticalRun = null;
  private boolean verticalSolvingPass = false;
  
  public ConstraintWidget()
  {
    ConstraintAnchor localConstraintAnchor = new ConstraintAnchor(this, ConstraintAnchor.Type.CENTER);
    this.mCenter = localConstraintAnchor;
    this.mListAnchors = new ConstraintAnchor[] { this.mLeft, this.mRight, this.mTop, this.mBottom, this.mBaseline, localConstraintAnchor };
    this.mAnchors = new ArrayList();
    this.mIsInBarrier = new boolean[2];
    this.mListDimensionBehaviors = new DimensionBehaviour[] { DimensionBehaviour.FIXED, DimensionBehaviour.FIXED };
    this.mParent = null;
    this.mWidth = 0;
    this.mHeight = 0;
    this.mDimensionRatio = 0.0F;
    this.mDimensionRatioSide = -1;
    this.mX = 0;
    this.mY = 0;
    this.mRelX = 0;
    this.mRelY = 0;
    this.mOffsetX = 0;
    this.mOffsetY = 0;
    this.mBaselineDistance = 0;
    float f = DEFAULT_BIAS;
    this.mHorizontalBiasPercent = f;
    this.mVerticalBiasPercent = f;
    this.mContainerItemSkip = 0;
    this.mVisibility = 0;
    this.mAnimated = false;
    this.mDebugName = null;
    this.mType = null;
    this.mGroupsToSolver = false;
    this.mHorizontalChainStyle = 0;
    this.mVerticalChainStyle = 0;
    this.mWeight = new float[] { -1.0F, -1.0F };
    this.mListNextMatchConstraintsWidget = new ConstraintWidget[] { null, null };
    this.mNextChainWidget = new ConstraintWidget[] { null, null };
    this.mHorizontalNextWidget = null;
    this.mVerticalNextWidget = null;
    this.horizontalGroup = -1;
    this.verticalGroup = -1;
    addAnchors();
  }
  
  public ConstraintWidget(int paramInt1, int paramInt2)
  {
    this(0, 0, paramInt1, paramInt2);
  }
  
  public ConstraintWidget(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    ConstraintAnchor localConstraintAnchor = new ConstraintAnchor(this, ConstraintAnchor.Type.CENTER);
    this.mCenter = localConstraintAnchor;
    this.mListAnchors = new ConstraintAnchor[] { this.mLeft, this.mRight, this.mTop, this.mBottom, this.mBaseline, localConstraintAnchor };
    this.mAnchors = new ArrayList();
    this.mIsInBarrier = new boolean[2];
    this.mListDimensionBehaviors = new DimensionBehaviour[] { DimensionBehaviour.FIXED, DimensionBehaviour.FIXED };
    this.mParent = null;
    this.mWidth = 0;
    this.mHeight = 0;
    this.mDimensionRatio = 0.0F;
    this.mDimensionRatioSide = -1;
    this.mX = 0;
    this.mY = 0;
    this.mRelX = 0;
    this.mRelY = 0;
    this.mOffsetX = 0;
    this.mOffsetY = 0;
    this.mBaselineDistance = 0;
    float f = DEFAULT_BIAS;
    this.mHorizontalBiasPercent = f;
    this.mVerticalBiasPercent = f;
    this.mContainerItemSkip = 0;
    this.mVisibility = 0;
    this.mAnimated = false;
    this.mDebugName = null;
    this.mType = null;
    this.mGroupsToSolver = false;
    this.mHorizontalChainStyle = 0;
    this.mVerticalChainStyle = 0;
    this.mWeight = new float[] { -1.0F, -1.0F };
    this.mListNextMatchConstraintsWidget = new ConstraintWidget[] { null, null };
    this.mNextChainWidget = new ConstraintWidget[] { null, null };
    this.mHorizontalNextWidget = null;
    this.mVerticalNextWidget = null;
    this.horizontalGroup = -1;
    this.verticalGroup = -1;
    this.mX = paramInt1;
    this.mY = paramInt2;
    this.mWidth = paramInt3;
    this.mHeight = paramInt4;
    addAnchors();
  }
  
  public ConstraintWidget(String paramString)
  {
    ConstraintAnchor localConstraintAnchor = new ConstraintAnchor(this, ConstraintAnchor.Type.CENTER);
    this.mCenter = localConstraintAnchor;
    this.mListAnchors = new ConstraintAnchor[] { this.mLeft, this.mRight, this.mTop, this.mBottom, this.mBaseline, localConstraintAnchor };
    this.mAnchors = new ArrayList();
    this.mIsInBarrier = new boolean[2];
    this.mListDimensionBehaviors = new DimensionBehaviour[] { DimensionBehaviour.FIXED, DimensionBehaviour.FIXED };
    this.mParent = null;
    this.mWidth = 0;
    this.mHeight = 0;
    this.mDimensionRatio = 0.0F;
    this.mDimensionRatioSide = -1;
    this.mX = 0;
    this.mY = 0;
    this.mRelX = 0;
    this.mRelY = 0;
    this.mOffsetX = 0;
    this.mOffsetY = 0;
    this.mBaselineDistance = 0;
    float f = DEFAULT_BIAS;
    this.mHorizontalBiasPercent = f;
    this.mVerticalBiasPercent = f;
    this.mContainerItemSkip = 0;
    this.mVisibility = 0;
    this.mAnimated = false;
    this.mDebugName = null;
    this.mType = null;
    this.mGroupsToSolver = false;
    this.mHorizontalChainStyle = 0;
    this.mVerticalChainStyle = 0;
    this.mWeight = new float[] { -1.0F, -1.0F };
    this.mListNextMatchConstraintsWidget = new ConstraintWidget[] { null, null };
    this.mNextChainWidget = new ConstraintWidget[] { null, null };
    this.mHorizontalNextWidget = null;
    this.mVerticalNextWidget = null;
    this.horizontalGroup = -1;
    this.verticalGroup = -1;
    addAnchors();
    setDebugName(paramString);
  }
  
  public ConstraintWidget(String paramString, int paramInt1, int paramInt2)
  {
    this(paramInt1, paramInt2);
    setDebugName(paramString);
  }
  
  public ConstraintWidget(String paramString, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    this(paramInt1, paramInt2, paramInt3, paramInt4);
    setDebugName(paramString);
  }
  
  private void addAnchors()
  {
    this.mAnchors.add(this.mLeft);
    this.mAnchors.add(this.mTop);
    this.mAnchors.add(this.mRight);
    this.mAnchors.add(this.mBottom);
    this.mAnchors.add(this.mCenterX);
    this.mAnchors.add(this.mCenterY);
    this.mAnchors.add(this.mCenter);
    this.mAnchors.add(this.mBaseline);
  }
  
  private void applyConstraints(LinearSystem paramLinearSystem, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3, boolean paramBoolean4, SolverVariable paramSolverVariable1, SolverVariable paramSolverVariable2, DimensionBehaviour paramDimensionBehaviour, boolean paramBoolean5, ConstraintAnchor paramConstraintAnchor1, ConstraintAnchor paramConstraintAnchor2, int paramInt1, int paramInt2, int paramInt3, int paramInt4, float paramFloat1, boolean paramBoolean6, boolean paramBoolean7, boolean paramBoolean8, boolean paramBoolean9, boolean paramBoolean10, int paramInt5, int paramInt6, int paramInt7, int paramInt8, float paramFloat2, boolean paramBoolean11)
  {
    SolverVariable localSolverVariable3 = paramLinearSystem.createObjectVariable(paramConstraintAnchor1);
    SolverVariable localSolverVariable1 = paramLinearSystem.createObjectVariable(paramConstraintAnchor2);
    SolverVariable localSolverVariable4 = paramLinearSystem.createObjectVariable(paramConstraintAnchor1.getTarget());
    SolverVariable localSolverVariable2 = paramLinearSystem.createObjectVariable(paramConstraintAnchor2.getTarget());
    Object localObject1;
    if (LinearSystem.getMetrics() != null)
    {
      localObject1 = LinearSystem.getMetrics();
      ((Metrics)localObject1).nonresolvedWidgets += 1L;
    }
    boolean bool3 = paramConstraintAnchor1.isConnected();
    boolean bool1 = paramConstraintAnchor2.isConnected();
    boolean bool2 = this.mCenter.isConnected();
    int m = 0;
    int j = 0;
    if (bool3) {
      j = 0 + 1;
    }
    int i = j;
    if (bool1) {
      i = j + 1;
    }
    int k;
    if (bool2) {
      k = i + 1;
    } else {
      k = i;
    }
    if (paramBoolean6) {
      j = 3;
    } else {
      j = paramInt5;
    }
    switch (1.$SwitchMap$androidx$constraintlayout$core$widgets$ConstraintWidget$DimensionBehaviour[paramDimensionBehaviour.ordinal()])
    {
    default: 
      paramInt5 = m;
      break;
    case 4: 
      if (j != 4) {
        paramInt5 = 1;
      } else {
        paramInt5 = 0;
      }
      break;
    case 3: 
      paramInt5 = 0;
      break;
    case 2: 
      paramInt5 = 0;
      break;
    case 1: 
      paramInt5 = 0;
    }
    if ((this.mWidthOverride != -1) && (paramBoolean1))
    {
      paramInt5 = 0;
      paramInt2 = this.mWidthOverride;
      this.mWidthOverride = -1;
    }
    if ((this.mHeightOverride != -1) && (!paramBoolean1))
    {
      paramInt2 = 0;
      paramInt5 = this.mHeightOverride;
      this.mHeightOverride = -1;
    }
    else
    {
      i = paramInt5;
      paramInt5 = paramInt2;
      paramInt2 = i;
    }
    if (this.mVisibility == 8)
    {
      paramInt2 = 0;
      paramInt5 = 0;
    }
    else
    {
      i = paramInt5;
      paramInt5 = paramInt2;
      paramInt2 = i;
    }
    if (paramBoolean11) {
      if ((!bool3) && (!bool1) && (!bool2)) {
        paramLinearSystem.addEquality(localSolverVariable3, paramInt1);
      } else if ((bool3) && (!bool1)) {
        paramLinearSystem.addEquality(localSolverVariable3, localSolverVariable4, paramConstraintAnchor1.getMargin(), 8);
      }
    }
    Object localObject2;
    if (paramInt5 == 0)
    {
      if (paramBoolean5)
      {
        paramLinearSystem.addEquality(localSolverVariable1, localSolverVariable3, 0, 3);
        if (paramInt3 > 0) {
          paramLinearSystem.addGreaterThan(localSolverVariable1, localSolverVariable3, paramInt3, 8);
        }
        if (paramInt4 < Integer.MAX_VALUE) {
          paramLinearSystem.addLowerThan(localSolverVariable1, localSolverVariable3, paramInt4, 8);
        }
      }
      else
      {
        paramLinearSystem.addEquality(localSolverVariable1, localSolverVariable3, paramInt2, 8);
      }
      paramInt1 = paramInt8;
      i = paramInt5;
    }
    else if ((k != 2) && (!paramBoolean6) && ((j == 1) || (j == 0)))
    {
      i = 0;
      paramInt2 = Math.max(paramInt7, paramInt2);
      paramInt1 = paramInt2;
      if (paramInt8 > 0) {
        paramInt1 = Math.min(paramInt8, paramInt2);
      }
      paramLinearSystem.addEquality(localSolverVariable1, localSolverVariable3, paramInt1, 8);
      paramInt1 = paramInt8;
    }
    else
    {
      if (paramInt7 == -2) {
        paramInt1 = paramInt2;
      } else {
        paramInt1 = paramInt7;
      }
      if (paramInt8 == -2) {
        paramInt4 = paramInt2;
      } else {
        paramInt4 = paramInt8;
      }
      paramInt7 = paramInt2;
      if (paramInt2 > 0)
      {
        paramInt7 = paramInt2;
        if (j != 1) {
          paramInt7 = 0;
        }
      }
      paramInt2 = paramInt7;
      if (paramInt1 > 0)
      {
        paramLinearSystem.addGreaterThan(localSolverVariable1, localSolverVariable3, paramInt1, 8);
        paramInt2 = Math.max(paramInt7, paramInt1);
      }
      paramInt7 = paramInt2;
      if (paramInt4 > 0)
      {
        paramInt8 = 1;
        paramInt7 = paramInt8;
        if (paramBoolean2)
        {
          paramInt7 = paramInt8;
          if (j == 1) {
            paramInt7 = 0;
          }
        }
        if (paramInt7 != 0) {
          paramLinearSystem.addLowerThan(localSolverVariable1, localSolverVariable3, paramInt4, 8);
        }
        paramInt7 = Math.min(paramInt2, paramInt4);
      }
      if (j == 1)
      {
        if (paramBoolean2)
        {
          paramLinearSystem.addEquality(localSolverVariable1, localSolverVariable3, paramInt7, 8);
        }
        else if (paramBoolean8)
        {
          paramLinearSystem.addEquality(localSolverVariable1, localSolverVariable3, paramInt7, 5);
          paramLinearSystem.addLowerThan(localSolverVariable1, localSolverVariable3, paramInt7, 8);
        }
        else
        {
          paramLinearSystem.addEquality(localSolverVariable1, localSolverVariable3, paramInt7, 5);
          paramLinearSystem.addLowerThan(localSolverVariable1, localSolverVariable3, paramInt7, 8);
        }
        paramInt7 = paramInt1;
        i = paramInt5;
        paramInt1 = paramInt4;
      }
      else if (j == 2)
      {
        if ((paramConstraintAnchor1.getType() != ConstraintAnchor.Type.TOP) && (paramConstraintAnchor1.getType() != ConstraintAnchor.Type.BOTTOM))
        {
          paramDimensionBehaviour = paramLinearSystem.createObjectVariable(this.mParent.getAnchor(ConstraintAnchor.Type.LEFT));
          localObject1 = paramLinearSystem.createObjectVariable(this.mParent.getAnchor(ConstraintAnchor.Type.RIGHT));
        }
        else
        {
          paramDimensionBehaviour = paramLinearSystem.createObjectVariable(this.mParent.getAnchor(ConstraintAnchor.Type.TOP));
          localObject1 = paramLinearSystem.createObjectVariable(this.mParent.getAnchor(ConstraintAnchor.Type.BOTTOM));
        }
        localObject2 = paramLinearSystem.createRow();
        paramLinearSystem.addConstraint(((ArrayRow)localObject2).createRowDimensionRatio(localSolverVariable1, localSolverVariable3, (SolverVariable)localObject1, paramDimensionBehaviour, paramFloat2));
        if (paramBoolean2) {
          paramInt5 = 0;
        }
        paramInt7 = paramInt1;
        i = paramInt5;
        paramInt1 = paramInt4;
      }
      else
      {
        paramInt7 = paramInt1;
        paramBoolean4 = true;
        paramInt1 = paramInt4;
        i = paramInt5;
      }
    }
    if ((paramBoolean11) && (!paramBoolean8))
    {
      paramInt2 = 5;
      if ((bool3) || (bool1) || (bool2))
      {
        if ((bool3) && (!bool1))
        {
          paramSolverVariable1 = paramConstraintAnchor1.mTarget.mOwner;
          paramInt1 = paramInt2;
          if (paramBoolean2)
          {
            paramInt1 = paramInt2;
            if ((paramSolverVariable1 instanceof Barrier)) {
              paramInt1 = 8;
            }
          }
          break label2386;
        }
        if ((!bool3) && (bool1))
        {
          paramLinearSystem.addEquality(localSolverVariable1, localSolverVariable2, -paramConstraintAnchor2.getMargin(), 8);
          if (paramBoolean2)
          {
            if ((this.OPTIMIZE_WRAP) && (localSolverVariable3.isFinalValue))
            {
              paramDimensionBehaviour = this.mParent;
              if (paramDimensionBehaviour != null)
              {
                paramSolverVariable1 = (ConstraintWidgetContainer)paramDimensionBehaviour;
                if (paramBoolean1) {
                  paramSolverVariable1.addHorizontalWrapMinVariable(paramConstraintAnchor1);
                } else {
                  paramSolverVariable1.addVerticalWrapMinVariable(paramConstraintAnchor1);
                }
                break label2383;
              }
            }
            paramLinearSystem.addGreaterThan(localSolverVariable3, paramSolverVariable1, 0, 5);
          }
        }
        else if ((bool3) && (bool1))
        {
          paramInt8 = 0;
          paramInt2 = 0;
          paramInt4 = 4;
          k = 6;
          paramInt5 = 5;
          paramDimensionBehaviour = paramConstraintAnchor1.mTarget.mOwner;
          localObject1 = paramConstraintAnchor2.mTarget.mOwner;
          localObject2 = getParent();
          if (i != 0)
          {
            if (j == 0)
            {
              if ((paramInt1 == 0) && (paramInt7 == 0))
              {
                paramInt8 = 1;
                if ((localSolverVariable4.isFinalValue) && (localSolverVariable2.isFinalValue))
                {
                  paramLinearSystem.addEquality(localSolverVariable3, localSolverVariable4, paramConstraintAnchor1.getMargin(), 8);
                  paramLinearSystem.addEquality(localSolverVariable1, localSolverVariable2, -paramConstraintAnchor2.getMargin(), 8);
                  return;
                }
                paramInt2 = 0;
                paramInt5 = 8;
                paramInt4 = 8;
                paramInt1 = 0;
              }
              else
              {
                paramInt6 = 1;
                paramInt1 = 1;
                paramInt5 = 5;
                paramInt4 = 5;
                paramInt8 = paramInt2;
                paramInt2 = paramInt6;
              }
              if ((!(paramDimensionBehaviour instanceof Barrier)) && (!(localObject1 instanceof Barrier)))
              {
                paramInt6 = k;
              }
              else
              {
                paramInt4 = 4;
                paramInt6 = k;
              }
            }
            else if (j == 2)
            {
              paramInt5 = 5;
              paramInt4 = 5;
              if ((!(paramDimensionBehaviour instanceof Barrier)) && (!(localObject1 instanceof Barrier)))
              {
                paramInt2 = 1;
                paramInt1 = 1;
                paramInt6 = k;
              }
              else
              {
                paramInt4 = 4;
                paramInt2 = 1;
                paramInt1 = 1;
                paramInt6 = k;
              }
            }
            else if (j == 1)
            {
              paramInt5 = 8;
              paramInt2 = 1;
              paramInt1 = 1;
              paramInt6 = k;
            }
            else if (j == 3)
            {
              if (this.mResolvedDimensionRatioSide == -1)
              {
                paramInt8 = 1;
                paramInt5 = 8;
                paramInt4 = 5;
                if (paramBoolean9)
                {
                  paramInt4 = 5;
                  paramInt6 = 4;
                  if (paramBoolean2)
                  {
                    paramInt6 = 5;
                    paramInt2 = 1;
                    paramInt1 = 1;
                  }
                  else
                  {
                    paramInt2 = 1;
                    paramInt1 = 1;
                  }
                }
                else
                {
                  paramInt6 = 8;
                  paramInt2 = 1;
                  paramInt1 = 1;
                }
              }
              else
              {
                paramInt8 = 1;
                if (paramBoolean6)
                {
                  if ((paramInt6 != 2) && (paramInt6 != 1)) {
                    paramInt1 = 0;
                  } else {
                    paramInt1 = 1;
                  }
                  if (paramInt1 == 0)
                  {
                    paramInt5 = 8;
                    paramInt4 = 5;
                  }
                  paramInt2 = 1;
                  paramInt1 = 1;
                  paramInt6 = k;
                }
                else
                {
                  paramInt5 = 5;
                  if (paramInt1 > 0)
                  {
                    paramInt4 = 5;
                    paramInt2 = 1;
                    paramInt1 = 1;
                    paramInt6 = k;
                  }
                  else if ((paramInt1 == 0) && (paramInt7 == 0))
                  {
                    if (!paramBoolean9)
                    {
                      paramInt4 = 8;
                      paramInt2 = 1;
                      paramInt1 = 1;
                      paramInt6 = k;
                    }
                    else
                    {
                      if ((paramDimensionBehaviour != localObject2) && (localObject1 != localObject2)) {
                        paramInt5 = 4;
                      } else {
                        paramInt5 = 5;
                      }
                      paramInt4 = 4;
                      paramInt2 = 1;
                      paramInt1 = 1;
                      paramInt6 = k;
                    }
                  }
                  else
                  {
                    paramInt2 = 1;
                    paramInt1 = 1;
                    paramInt6 = k;
                  }
                }
              }
            }
            else
            {
              paramInt2 = 0;
              paramInt1 = 0;
              paramInt6 = k;
            }
          }
          else
          {
            paramInt2 = 1;
            if ((localSolverVariable4.isFinalValue) && (localSolverVariable2.isFinalValue))
            {
              paramLinearSystem.addCentering(localSolverVariable3, localSolverVariable4, paramConstraintAnchor1.getMargin(), paramFloat1, localSolverVariable2, localSolverVariable1, paramConstraintAnchor2.getMargin(), 8);
              if ((paramBoolean2) && (paramBoolean4))
              {
                paramInt1 = 0;
                if (paramConstraintAnchor2.mTarget != null) {
                  paramInt1 = paramConstraintAnchor2.getMargin();
                }
                if (localSolverVariable2 != paramSolverVariable2) {
                  paramLinearSystem.addGreaterThan(paramSolverVariable2, localSolverVariable1, paramInt1, 5);
                }
              }
              return;
            }
            paramInt1 = 1;
            paramInt6 = k;
          }
          m = 1;
          if ((paramInt1 != 0) && (localSolverVariable4 == localSolverVariable2) && (paramDimensionBehaviour != localObject2))
          {
            paramInt1 = 0;
            k = 0;
          }
          else
          {
            k = paramInt1;
            paramInt1 = m;
          }
          if (paramInt2 != 0)
          {
            if ((i == 0) && (!paramBoolean7) && (!paramBoolean9) && (localSolverVariable4 == paramSolverVariable1) && (localSolverVariable2 == paramSolverVariable2))
            {
              paramInt6 = 8;
              paramInt1 = 8;
              paramInt2 = 0;
              paramBoolean2 = false;
            }
            else
            {
              paramInt2 = paramInt1;
              paramInt1 = paramInt5;
            }
            paramInt5 = paramConstraintAnchor1.getMargin();
            m = paramConstraintAnchor2.getMargin();
            paramLinearSystem.addCentering(localSolverVariable3, localSolverVariable4, paramInt5, paramFloat1, localSolverVariable2, localSolverVariable1, m, paramInt6);
          }
          else
          {
            paramInt2 = paramInt1;
            paramInt1 = paramInt5;
          }
          paramInt6 = 5;
          if ((this.mVisibility == 8) && (!paramConstraintAnchor2.hasDependents())) {
            return;
          }
          if (k != 0)
          {
            if ((paramBoolean2) && (localSolverVariable4 != localSolverVariable2) && (i == 0))
            {
              if (!(paramDimensionBehaviour instanceof Barrier)) {
                if (!(localObject1 instanceof Barrier)) {
                  break label1976;
                }
              }
              paramInt1 = 6;
            }
            label1976:
            paramLinearSystem.addGreaterThan(localSolverVariable3, localSolverVariable4, paramConstraintAnchor1.getMargin(), paramInt1);
            paramLinearSystem.addLowerThan(localSolverVariable1, localSolverVariable2, -paramConstraintAnchor2.getMargin(), paramInt1);
          }
          if ((paramBoolean2) && (paramBoolean10) && (!(paramDimensionBehaviour instanceof Barrier)) && (!(localObject1 instanceof Barrier)) && (localObject1 != localObject2))
          {
            paramInt2 = 1;
            paramInt4 = 6;
            paramInt1 = 6;
          }
          else
          {
            paramInt5 = paramInt1;
            paramInt1 = paramInt4;
            paramInt4 = paramInt5;
          }
          if (paramInt2 != 0)
          {
            paramInt2 = paramInt1;
            if (paramInt8 != 0) {
              if (paramBoolean9)
              {
                paramInt2 = paramInt1;
                if (!paramBoolean3) {}
              }
              else
              {
                paramInt2 = paramInt1;
                if ((paramDimensionBehaviour == localObject2) || (localObject1 == localObject2)) {
                  paramInt2 = 6;
                }
                if (((paramDimensionBehaviour instanceof Guideline)) || ((localObject1 instanceof Guideline))) {
                  paramInt2 = 5;
                }
                if (((paramDimensionBehaviour instanceof Barrier)) || ((localObject1 instanceof Barrier))) {
                  paramInt2 = 5;
                }
                if (paramBoolean9) {
                  paramInt2 = 5;
                }
                paramInt2 = Math.max(paramInt2, paramInt1);
              }
            }
            paramInt1 = paramInt2;
            if (paramBoolean2)
            {
              paramInt2 = Math.min(paramInt4, paramInt2);
              paramInt1 = paramInt2;
              if (paramBoolean6)
              {
                paramInt1 = paramInt2;
                if (!paramBoolean9) {
                  if (paramDimensionBehaviour != localObject2)
                  {
                    paramInt1 = paramInt2;
                    if (localObject1 != localObject2) {}
                  }
                  else
                  {
                    paramInt1 = 4;
                  }
                }
              }
            }
            paramLinearSystem.addEquality(localSolverVariable3, localSolverVariable4, paramConstraintAnchor1.getMargin(), paramInt1);
            paramLinearSystem.addEquality(localSolverVariable1, localSolverVariable2, -paramConstraintAnchor2.getMargin(), paramInt1);
          }
          if (paramBoolean2)
          {
            paramInt1 = 0;
            if (paramSolverVariable1 == localSolverVariable4) {
              paramInt1 = paramConstraintAnchor1.getMargin();
            }
            if (localSolverVariable4 != paramSolverVariable1) {
              paramLinearSystem.addGreaterThan(localSolverVariable3, paramSolverVariable1, paramInt1, 5);
            }
          }
          if ((paramBoolean2) && (i != 0))
          {
            if ((paramInt3 == 0) && (paramInt7 == 0))
            {
              if (i != 0) {
                if (j == 3)
                {
                  paramLinearSystem.addGreaterThan(localSolverVariable1, localSolverVariable3, 0, 8);
                  paramInt1 = paramInt6;
                  break label2386;
                }
              }
              paramLinearSystem.addGreaterThan(localSolverVariable1, localSolverVariable3, 0, 5);
              paramInt1 = paramInt6;
              break label2386;
            }
            paramInt1 = paramInt6;
            break label2386;
          }
          paramInt1 = paramInt6;
          break label2386;
        }
      }
      label2383:
      paramInt1 = 5;
      label2386:
      if ((paramBoolean2) && (paramBoolean4))
      {
        paramInt2 = 0;
        if (paramConstraintAnchor2.mTarget != null) {
          paramInt2 = paramConstraintAnchor2.getMargin();
        }
        if (localSolverVariable2 != paramSolverVariable2)
        {
          if ((this.OPTIMIZE_WRAP) && (localSolverVariable1.isFinalValue))
          {
            paramSolverVariable1 = this.mParent;
            if (paramSolverVariable1 != null)
            {
              paramLinearSystem = (ConstraintWidgetContainer)paramSolverVariable1;
              if (paramBoolean1) {
                paramLinearSystem.addHorizontalWrapMaxVariable(paramConstraintAnchor2);
              } else {
                paramLinearSystem.addVerticalWrapMaxVariable(paramConstraintAnchor2);
              }
              return;
            }
          }
          paramLinearSystem.addGreaterThan(paramSolverVariable2, localSolverVariable1, paramInt2, paramInt1);
        }
      }
      return;
    }
    if ((k < 2) && (paramBoolean2) && (paramBoolean4))
    {
      paramLinearSystem.addGreaterThan(localSolverVariable3, paramSolverVariable1, 0, 8);
      if ((!paramBoolean1) && (this.mBaseline.mTarget != null)) {
        paramInt1 = 0;
      } else {
        paramInt1 = 1;
      }
      paramInt2 = paramInt1;
      paramInt1 = paramInt2;
      if (!paramBoolean1)
      {
        paramInt1 = paramInt2;
        if (this.mBaseline.mTarget != null)
        {
          paramSolverVariable1 = this.mBaseline.mTarget.mOwner;
          if ((paramSolverVariable1.mDimensionRatio != 0.0F) && (paramSolverVariable1.mListDimensionBehaviors[0] == DimensionBehaviour.MATCH_CONSTRAINT) && (paramSolverVariable1.mListDimensionBehaviors[1] == DimensionBehaviour.MATCH_CONSTRAINT)) {
            paramInt1 = 1;
          } else {
            paramInt1 = 0;
          }
        }
      }
      if (paramInt1 != 0) {
        paramLinearSystem.addGreaterThan(paramSolverVariable2, localSolverVariable1, 0, 8);
      }
    }
  }
  
  private void getSceneString(StringBuilder paramStringBuilder, String paramString, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, float paramFloat1, float paramFloat2)
  {
    paramStringBuilder.append(paramString);
    paramStringBuilder.append(" :  {\n");
    serializeAttribute(paramStringBuilder, "      size", paramInt1, 0);
    serializeAttribute(paramStringBuilder, "      min", paramInt2, 0);
    serializeAttribute(paramStringBuilder, "      max", paramInt3, Integer.MAX_VALUE);
    serializeAttribute(paramStringBuilder, "      matchMin", paramInt5, 0);
    serializeAttribute(paramStringBuilder, "      matchDef", paramInt6, 0);
    serializeAttribute(paramStringBuilder, "      matchPercent", paramFloat1, 1.0F);
    paramStringBuilder.append("    },\n");
  }
  
  private void getSceneString(StringBuilder paramStringBuilder, String paramString, ConstraintAnchor paramConstraintAnchor)
  {
    if (paramConstraintAnchor.mTarget == null) {
      return;
    }
    paramStringBuilder.append("    ");
    paramStringBuilder.append(paramString);
    paramStringBuilder.append(" : [ '");
    paramStringBuilder.append(paramConstraintAnchor.mTarget);
    paramStringBuilder.append("'");
    if ((paramConstraintAnchor.mGoneMargin != Integer.MIN_VALUE) || (paramConstraintAnchor.mMargin != 0))
    {
      paramStringBuilder.append(",");
      paramStringBuilder.append(paramConstraintAnchor.mMargin);
      if (paramConstraintAnchor.mGoneMargin != Integer.MIN_VALUE)
      {
        paramStringBuilder.append(",");
        paramStringBuilder.append(paramConstraintAnchor.mGoneMargin);
        paramStringBuilder.append(",");
      }
    }
    paramStringBuilder.append(" ] ,\n");
  }
  
  private boolean isChainHead(int paramInt)
  {
    paramInt *= 2;
    if (this.mListAnchors[paramInt].mTarget != null)
    {
      ConstraintAnchor localConstraintAnchor = this.mListAnchors[paramInt].mTarget.mTarget;
      ConstraintAnchor[] arrayOfConstraintAnchor = this.mListAnchors;
      if ((localConstraintAnchor != arrayOfConstraintAnchor[paramInt]) && (arrayOfConstraintAnchor[(paramInt + 1)].mTarget != null) && (this.mListAnchors[(paramInt + 1)].mTarget.mTarget == this.mListAnchors[(paramInt + 1)])) {
        return true;
      }
    }
    boolean bool = false;
    return bool;
  }
  
  private void serializeAnchor(StringBuilder paramStringBuilder, String paramString, ConstraintAnchor paramConstraintAnchor)
  {
    if (paramConstraintAnchor.mTarget == null) {
      return;
    }
    paramStringBuilder.append(paramString);
    paramStringBuilder.append(" : [ '");
    paramStringBuilder.append(paramConstraintAnchor.mTarget);
    paramStringBuilder.append("',");
    paramStringBuilder.append(paramConstraintAnchor.mMargin);
    paramStringBuilder.append(",");
    paramStringBuilder.append(paramConstraintAnchor.mGoneMargin);
    paramStringBuilder.append(",");
    paramStringBuilder.append(" ] ,\n");
  }
  
  private void serializeAttribute(StringBuilder paramStringBuilder, String paramString, float paramFloat1, float paramFloat2)
  {
    if (paramFloat1 == paramFloat2) {
      return;
    }
    paramStringBuilder.append(paramString);
    paramStringBuilder.append(" :   ");
    paramStringBuilder.append(paramFloat1);
    paramStringBuilder.append(",\n");
  }
  
  private void serializeAttribute(StringBuilder paramStringBuilder, String paramString, int paramInt1, int paramInt2)
  {
    if (paramInt1 == paramInt2) {
      return;
    }
    paramStringBuilder.append(paramString);
    paramStringBuilder.append(" :   ");
    paramStringBuilder.append(paramInt1);
    paramStringBuilder.append(",\n");
  }
  
  private void serializeCircle(StringBuilder paramStringBuilder, ConstraintAnchor paramConstraintAnchor, float paramFloat)
  {
    if (paramConstraintAnchor.mTarget == null) {
      return;
    }
    paramStringBuilder.append("circle : [ '");
    paramStringBuilder.append(paramConstraintAnchor.mTarget);
    paramStringBuilder.append("',");
    paramStringBuilder.append(paramConstraintAnchor.mMargin);
    paramStringBuilder.append(",");
    paramStringBuilder.append(paramFloat);
    paramStringBuilder.append(",");
    paramStringBuilder.append(" ] ,\n");
  }
  
  private void serializeDimensionRatio(StringBuilder paramStringBuilder, String paramString, float paramFloat, int paramInt)
  {
    if (paramFloat == 0.0F) {
      return;
    }
    paramStringBuilder.append(paramString);
    paramStringBuilder.append(" :  [");
    paramStringBuilder.append(paramFloat);
    paramStringBuilder.append(",");
    paramStringBuilder.append(paramInt);
    paramStringBuilder.append("");
    paramStringBuilder.append("],\n");
  }
  
  private void serializeSize(StringBuilder paramStringBuilder, String paramString, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, float paramFloat1, float paramFloat2)
  {
    paramStringBuilder.append(paramString);
    paramStringBuilder.append(" :  {\n");
    serializeAttribute(paramStringBuilder, "size", paramInt1, Integer.MIN_VALUE);
    serializeAttribute(paramStringBuilder, "min", paramInt2, 0);
    serializeAttribute(paramStringBuilder, "max", paramInt3, Integer.MAX_VALUE);
    serializeAttribute(paramStringBuilder, "matchMin", paramInt5, 0);
    serializeAttribute(paramStringBuilder, "matchDef", paramInt6, 0);
    serializeAttribute(paramStringBuilder, "matchPercent", paramInt6, 1);
    paramStringBuilder.append("},\n");
  }
  
  public void addChildrenToSolverByDependency(ConstraintWidgetContainer paramConstraintWidgetContainer, LinearSystem paramLinearSystem, HashSet<ConstraintWidget> paramHashSet, int paramInt, boolean paramBoolean)
  {
    if (paramBoolean)
    {
      if (!paramHashSet.contains(this)) {
        return;
      }
      Optimizer.checkMatchParent(paramConstraintWidgetContainer, paramLinearSystem, this);
      paramHashSet.remove(this);
      addToSolver(paramLinearSystem, paramConstraintWidgetContainer.optimizeFor(64));
    }
    Object localObject;
    if (paramInt == 0)
    {
      localObject = this.mLeft.getDependents();
      if (localObject != null)
      {
        localObject = ((HashSet)localObject).iterator();
        while (((Iterator)localObject).hasNext()) {
          ((ConstraintAnchor)((Iterator)localObject).next()).mOwner.addChildrenToSolverByDependency(paramConstraintWidgetContainer, paramLinearSystem, paramHashSet, paramInt, true);
        }
      }
      localObject = this.mRight.getDependents();
      if (localObject != null)
      {
        localObject = ((HashSet)localObject).iterator();
        while (((Iterator)localObject).hasNext()) {
          ((ConstraintAnchor)((Iterator)localObject).next()).mOwner.addChildrenToSolverByDependency(paramConstraintWidgetContainer, paramLinearSystem, paramHashSet, paramInt, true);
        }
      }
    }
    else
    {
      localObject = this.mTop.getDependents();
      if (localObject != null)
      {
        localObject = ((HashSet)localObject).iterator();
        while (((Iterator)localObject).hasNext()) {
          ((ConstraintAnchor)((Iterator)localObject).next()).mOwner.addChildrenToSolverByDependency(paramConstraintWidgetContainer, paramLinearSystem, paramHashSet, paramInt, true);
        }
      }
      localObject = this.mBottom.getDependents();
      if (localObject != null)
      {
        localObject = ((HashSet)localObject).iterator();
        while (((Iterator)localObject).hasNext()) {
          ((ConstraintAnchor)((Iterator)localObject).next()).mOwner.addChildrenToSolverByDependency(paramConstraintWidgetContainer, paramLinearSystem, paramHashSet, paramInt, true);
        }
      }
      localObject = this.mBaseline.getDependents();
      if (localObject != null)
      {
        localObject = ((HashSet)localObject).iterator();
        while (((Iterator)localObject).hasNext()) {
          ((ConstraintAnchor)((Iterator)localObject).next()).mOwner.addChildrenToSolverByDependency(paramConstraintWidgetContainer, paramLinearSystem, paramHashSet, paramInt, true);
        }
      }
    }
  }
  
  boolean addFirst()
  {
    boolean bool;
    if ((!(this instanceof VirtualLayout)) && (!(this instanceof Guideline))) {
      bool = false;
    } else {
      bool = true;
    }
    return bool;
  }
  
  public void addToSolver(LinearSystem paramLinearSystem, boolean paramBoolean)
  {
    SolverVariable localSolverVariable3 = paramLinearSystem.createObjectVariable(this.mLeft);
    SolverVariable localSolverVariable1 = paramLinearSystem.createObjectVariable(this.mRight);
    SolverVariable localSolverVariable4 = paramLinearSystem.createObjectVariable(this.mTop);
    SolverVariable localSolverVariable2 = paramLinearSystem.createObjectVariable(this.mBottom);
    Object localObject3 = paramLinearSystem.createObjectVariable(this.mBaseline);
    Object localObject1 = this.mParent;
    boolean bool2;
    boolean bool1;
    if (localObject1 != null)
    {
      if ((localObject1 != null) && (localObject1.mListDimensionBehaviors[0] == DimensionBehaviour.WRAP_CONTENT)) {
        bool2 = true;
      } else {
        bool2 = false;
      }
      localObject1 = this.mParent;
      if ((localObject1 != null) && (localObject1.mListDimensionBehaviors[1] == DimensionBehaviour.WRAP_CONTENT)) {
        bool1 = true;
      } else {
        bool1 = false;
      }
      switch (this.mWrapBehaviorInParent)
      {
      default: 
        break;
      case 3: 
        bool2 = false;
        bool1 = false;
        break;
      case 2: 
        bool2 = false;
        break;
      case 1: 
        bool1 = false;
        break;
      }
    }
    else
    {
      bool2 = false;
      bool1 = false;
    }
    if ((this.mVisibility == 8) && (!this.mAnimated) && (!hasDependencies()))
    {
      localObject1 = this.mIsInBarrier;
      if ((localObject1[0] == 0) && (localObject1[1] == 0)) {
        return;
      }
    }
    boolean bool3 = this.resolvedHorizontal;
    if ((bool3) || (this.resolvedVertical))
    {
      if (bool3)
      {
        paramLinearSystem.addEquality(localSolverVariable3, this.mX);
        paramLinearSystem.addEquality(localSolverVariable1, this.mX + this.mWidth);
        if (bool2)
        {
          localObject1 = this.mParent;
          if (localObject1 != null) {
            if (this.OPTIMIZE_WRAP_ON_RESOLVED)
            {
              localObject1 = (ConstraintWidgetContainer)localObject1;
              ((ConstraintWidgetContainer)localObject1).addHorizontalWrapMinVariable(this.mLeft);
              ((ConstraintWidgetContainer)localObject1).addHorizontalWrapMaxVariable(this.mRight);
            }
            else
            {
              paramLinearSystem.addGreaterThan(paramLinearSystem.createObjectVariable(((ConstraintWidget)localObject1).mRight), localSolverVariable1, 0, 5);
            }
          }
        }
      }
      if (this.resolvedVertical)
      {
        paramLinearSystem.addEquality(localSolverVariable4, this.mY);
        paramLinearSystem.addEquality(localSolverVariable2, this.mY + this.mHeight);
        if (this.mBaseline.hasDependents()) {
          paramLinearSystem.addEquality((SolverVariable)localObject3, this.mY + this.mBaselineDistance);
        }
        if (bool1)
        {
          localObject1 = this.mParent;
          if (localObject1 != null) {
            if (this.OPTIMIZE_WRAP_ON_RESOLVED)
            {
              localObject1 = (ConstraintWidgetContainer)localObject1;
              ((ConstraintWidgetContainer)localObject1).addVerticalWrapMinVariable(this.mTop);
              ((ConstraintWidgetContainer)localObject1).addVerticalWrapMaxVariable(this.mBottom);
            }
            else
            {
              paramLinearSystem.addGreaterThan(paramLinearSystem.createObjectVariable(((ConstraintWidget)localObject1).mBottom), localSolverVariable2, 0, 5);
            }
          }
        }
      }
      if ((this.resolvedHorizontal) && (this.resolvedVertical))
      {
        this.resolvedHorizontal = false;
        this.resolvedVertical = false;
        return;
      }
    }
    if (LinearSystem.sMetrics != null)
    {
      localObject1 = LinearSystem.sMetrics;
      ((Metrics)localObject1).widgets += 1L;
    }
    if (paramBoolean)
    {
      localObject1 = this.horizontalRun;
      if ((localObject1 != null) && (this.verticalRun != null) && (((HorizontalWidgetRun)localObject1).start.resolved) && (this.horizontalRun.end.resolved) && (this.verticalRun.start.resolved) && (this.verticalRun.end.resolved))
      {
        if (LinearSystem.sMetrics != null)
        {
          localObject1 = LinearSystem.sMetrics;
          ((Metrics)localObject1).graphSolved += 1L;
        }
        paramLinearSystem.addEquality(localSolverVariable3, this.horizontalRun.start.value);
        paramLinearSystem.addEquality(localSolverVariable1, this.horizontalRun.end.value);
        paramLinearSystem.addEquality(localSolverVariable4, this.verticalRun.start.value);
        paramLinearSystem.addEquality(localSolverVariable2, this.verticalRun.end.value);
        paramLinearSystem.addEquality((SolverVariable)localObject3, this.verticalRun.baseline.value);
        if (this.mParent != null)
        {
          if ((bool2) && (this.isTerminalWidget[0] != 0) && (!isInHorizontalChain())) {
            paramLinearSystem.addGreaterThan(paramLinearSystem.createObjectVariable(this.mParent.mRight), localSolverVariable1, 0, 8);
          }
          if ((bool1) && (this.isTerminalWidget[1] != 0) && (!isInVerticalChain())) {
            paramLinearSystem.addGreaterThan(paramLinearSystem.createObjectVariable(this.mParent.mBottom), localSolverVariable2, 0, 8);
          }
        }
        this.resolvedHorizontal = false;
        this.resolvedVertical = false;
        return;
      }
    }
    if (LinearSystem.sMetrics != null)
    {
      localObject1 = LinearSystem.sMetrics;
      ((Metrics)localObject1).linearSolved += 1L;
    }
    boolean bool4;
    boolean bool5;
    boolean bool6;
    if (this.mParent != null)
    {
      if (isChainHead(0))
      {
        ((ConstraintWidgetContainer)this.mParent).addChain(this, 0);
        bool3 = true;
      }
      else
      {
        bool3 = isInHorizontalChain();
      }
      if (isChainHead(1))
      {
        ((ConstraintWidgetContainer)this.mParent).addChain(this, 1);
        bool4 = true;
      }
      else
      {
        bool4 = isInVerticalChain();
      }
      if ((!bool3) && (bool2) && (this.mVisibility != 8) && (this.mLeft.mTarget == null) && (this.mRight.mTarget == null)) {
        paramLinearSystem.addGreaterThan(paramLinearSystem.createObjectVariable(this.mParent.mRight), localSolverVariable1, 0, 1);
      }
      if ((!bool4) && (bool1) && (this.mVisibility != 8) && (this.mTop.mTarget == null) && (this.mBottom.mTarget == null) && (this.mBaseline == null)) {
        paramLinearSystem.addGreaterThan(paramLinearSystem.createObjectVariable(this.mParent.mBottom), localSolverVariable2, 0, 1);
      }
      bool5 = bool3;
      bool6 = bool4;
    }
    else
    {
      bool5 = false;
      bool6 = false;
    }
    int i = this.mWidth;
    int k = i;
    if (i < this.mMinWidth) {
      k = this.mMinWidth;
    }
    i = this.mHeight;
    int m = i;
    if (i < this.mMinHeight) {
      m = this.mMinHeight;
    }
    if (this.mListDimensionBehaviors[0] != DimensionBehaviour.MATCH_CONSTRAINT) {
      bool3 = true;
    } else {
      bool3 = false;
    }
    if (this.mListDimensionBehaviors[1] != DimensionBehaviour.MATCH_CONSTRAINT) {
      bool4 = true;
    } else {
      bool4 = false;
    }
    boolean bool7 = false;
    this.mResolvedDimensionRatioSide = this.mDimensionRatioSide;
    float f = this.mDimensionRatio;
    this.mResolvedDimensionRatio = f;
    int n = this.mMatchConstraintDefaultWidth;
    int i1 = this.mMatchConstraintDefaultHeight;
    Object localObject2;
    if ((f > 0.0F) && (this.mVisibility != 8))
    {
      bool7 = true;
      i = n;
      if (this.mListDimensionBehaviors[0] == DimensionBehaviour.MATCH_CONSTRAINT)
      {
        i = n;
        if (n == 0) {
          i = 3;
        }
      }
      j = i1;
      if (this.mListDimensionBehaviors[1] == DimensionBehaviour.MATCH_CONSTRAINT)
      {
        j = i1;
        if (i1 == 0) {
          j = 3;
        }
      }
      localObject1 = this.mListDimensionBehaviors[0];
      localObject2 = DimensionBehaviour.MATCH_CONSTRAINT;
      if ((localObject1 == localObject2) && (this.mListDimensionBehaviors[1] == DimensionBehaviour.MATCH_CONSTRAINT) && (i == 3) && (j == 3))
      {
        setupDimensionRatio(bool2, bool1, bool3, bool4);
        i1 = j;
        bool3 = bool7;
        n = i;
      }
      else
      {
        if ((this.mListDimensionBehaviors[0] == DimensionBehaviour.MATCH_CONSTRAINT) && (i == 3))
        {
          this.mResolvedDimensionRatioSide = 0;
          i1 = (int)(this.mResolvedDimensionRatio * this.mHeight);
          if (this.mListDimensionBehaviors[1] != DimensionBehaviour.MATCH_CONSTRAINT)
          {
            i = i1;
            k = j;
            n = 4;
            bool3 = false;
            j = i;
            i = m;
            m = n;
            break label1569;
          }
          k = j;
          n = i;
          i = m;
          bool3 = true;
          j = i1;
          m = n;
          break label1569;
        }
        i1 = j;
        bool3 = bool7;
        n = i;
        if (this.mListDimensionBehaviors[1] == DimensionBehaviour.MATCH_CONSTRAINT)
        {
          i1 = j;
          bool3 = bool7;
          n = i;
          if (j == 3)
          {
            this.mResolvedDimensionRatioSide = 1;
            if (this.mDimensionRatioSide == -1) {
              this.mResolvedDimensionRatio = (1.0F / this.mResolvedDimensionRatio);
            }
            m = (int)(this.mResolvedDimensionRatio * this.mWidth);
            if (this.mListDimensionBehaviors[0] != DimensionBehaviour.MATCH_CONSTRAINT)
            {
              n = m;
              i1 = 4;
              m = i;
              j = k;
              bool3 = false;
              i = n;
              k = i1;
              break label1569;
            }
            n = j;
            i1 = i;
            j = k;
            bool3 = true;
            i = m;
            k = n;
            m = i1;
            break label1569;
          }
        }
      }
    }
    else
    {
      bool3 = bool7;
    }
    int j = k;
    i = m;
    m = n;
    k = i1;
    label1569:
    localObject1 = this.mResolvedMatchConstraintDefault;
    localObject1[0] = m;
    localObject1[1] = k;
    this.mResolvedHasRatio = bool3;
    if (bool3)
    {
      n = this.mResolvedDimensionRatioSide;
      if ((n == 0) || (n == -1))
      {
        bool8 = true;
        break label1624;
      }
    }
    boolean bool8 = false;
    label1624:
    if (bool3)
    {
      n = this.mResolvedDimensionRatioSide;
      if ((n == 1) || (n == -1))
      {
        bool7 = true;
        break label1656;
      }
    }
    bool7 = false;
    label1656:
    boolean bool9;
    if ((this.mListDimensionBehaviors[0] == DimensionBehaviour.WRAP_CONTENT) && ((this instanceof ConstraintWidgetContainer))) {
      bool9 = true;
    } else {
      bool9 = false;
    }
    if (bool9) {
      j = 0;
    }
    if (this.mCenter.isConnected()) {
      bool4 = false;
    } else {
      bool4 = true;
    }
    localObject1 = this.mIsInBarrier;
    int i6 = localObject1[0];
    int i4 = localObject1[1];
    DimensionBehaviour[] arrayOfDimensionBehaviour;
    Object localObject5;
    Object localObject4;
    if ((this.mHorizontalResolution != 2) && (!this.resolvedHorizontal))
    {
      if (paramBoolean)
      {
        localObject1 = this.horizontalRun;
        if ((localObject1 != null) && (((HorizontalWidgetRun)localObject1).start.resolved) && (this.horizontalRun.end.resolved))
        {
          if (paramBoolean)
          {
            paramLinearSystem.addEquality(localSolverVariable3, this.horizontalRun.start.value);
            paramLinearSystem.addEquality(localSolverVariable1, this.horizontalRun.end.value);
            if (this.mParent != null)
            {
              if ((bool2) && (this.isTerminalWidget[0] != 0) && (!isInHorizontalChain())) {
                paramLinearSystem.addGreaterThan(paramLinearSystem.createObjectVariable(this.mParent.mRight), localSolverVariable1, 0, 8);
              }
            }
            else {}
          }
          break label2081;
        }
      }
      localObject1 = this.mParent;
      if (localObject1 != null) {
        localObject1 = paramLinearSystem.createObjectVariable(((ConstraintWidget)localObject1).mRight);
      } else {
        localObject1 = null;
      }
      localObject2 = this.mParent;
      if (localObject2 != null) {
        localObject2 = paramLinearSystem.createObjectVariable(((ConstraintWidget)localObject2).mLeft);
      } else {
        localObject2 = null;
      }
      int i5 = this.isTerminalWidget[0];
      arrayOfDimensionBehaviour = this.mListDimensionBehaviors;
      localObject5 = arrayOfDimensionBehaviour[0];
      ConstraintAnchor localConstraintAnchor = this.mLeft;
      localObject4 = this.mRight;
      n = this.mX;
      i1 = this.mMinWidth;
      int i2 = this.mMaxDimension[0];
      f = this.mHorizontalBiasPercent;
      boolean bool10;
      if (arrayOfDimensionBehaviour[1] == DimensionBehaviour.MATCH_CONSTRAINT) {
        bool10 = true;
      } else {
        bool10 = false;
      }
      applyConstraints(paramLinearSystem, true, bool2, bool1, i5, (SolverVariable)localObject2, (SolverVariable)localObject1, (DimensionBehaviour)localObject5, bool9, localConstraintAnchor, (ConstraintAnchor)localObject4, n, j, i1, i2, f, bool8, bool10, bool5, bool6, i6, m, k, this.mMatchConstraintMinWidth, this.mMatchConstraintMaxWidth, this.mMatchConstraintPercentWidth, bool4);
    }
    label2081:
    j = 1;
    if (paramBoolean)
    {
      localObject1 = this.verticalRun;
      if ((localObject1 != null) && (((VerticalWidgetRun)localObject1).start.resolved) && (this.verticalRun.end.resolved))
      {
        j = this.verticalRun.start.value;
        paramLinearSystem.addEquality(localSolverVariable4, j);
        j = this.verticalRun.end.value;
        paramLinearSystem.addEquality(localSolverVariable2, j);
        paramLinearSystem.addEquality((SolverVariable)localObject3, this.verticalRun.baseline.value);
        localObject1 = this.mParent;
        if (localObject1 != null) {
          if ((!bool6) && (bool1)) {
            if (this.isTerminalWidget[1] != 0) {
              paramLinearSystem.addGreaterThan(paramLinearSystem.createObjectVariable(((ConstraintWidget)localObject1).mBottom), localSolverVariable2, 0, 8);
            } else {}
          }
        }
        j = 0;
      }
    }
    if (this.mVerticalResolution == 2) {
      j = 0;
    }
    if ((j != 0) && (!this.resolvedVertical))
    {
      if ((this.mListDimensionBehaviors[1] == DimensionBehaviour.WRAP_CONTENT) && ((this instanceof ConstraintWidgetContainer))) {
        paramBoolean = true;
      } else {
        paramBoolean = false;
      }
      if (paramBoolean) {
        i = 0;
      }
      localObject1 = this.mParent;
      if (localObject1 != null) {
        localObject1 = paramLinearSystem.createObjectVariable(((ConstraintWidget)localObject1).mBottom);
      } else {
        localObject1 = null;
      }
      localObject2 = this.mParent;
      if (localObject2 != null) {
        localObject2 = paramLinearSystem.createObjectVariable(((ConstraintWidget)localObject2).mTop);
      } else {
        localObject2 = null;
      }
      if ((this.mBaselineDistance > 0) || (this.mVisibility == 8)) {
        if (this.mBaseline.mTarget != null)
        {
          paramLinearSystem.addEquality((SolverVariable)localObject3, localSolverVariable4, getBaselineDistance(), 8);
          paramLinearSystem.addEquality((SolverVariable)localObject3, paramLinearSystem.createObjectVariable(this.mBaseline.mTarget), this.mBaseline.getMargin(), 8);
          if (bool1) {
            paramLinearSystem.addGreaterThan((SolverVariable)localObject1, paramLinearSystem.createObjectVariable(this.mBottom), 0, 5);
          }
          bool4 = false;
        }
        else if (this.mVisibility == 8)
        {
          paramLinearSystem.addEquality((SolverVariable)localObject3, localSolverVariable4, this.mBaseline.getMargin(), 8);
        }
        else
        {
          paramLinearSystem.addEquality((SolverVariable)localObject3, localSolverVariable4, getBaselineDistance(), 8);
        }
      }
      int i3 = this.isTerminalWidget[1];
      arrayOfDimensionBehaviour = this.mListDimensionBehaviors;
      localObject4 = arrayOfDimensionBehaviour[1];
      localObject3 = this.mTop;
      localObject5 = this.mBottom;
      j = this.mY;
      i1 = this.mMinHeight;
      n = this.mMaxDimension[1];
      f = this.mVerticalBiasPercent;
      if (arrayOfDimensionBehaviour[0] == DimensionBehaviour.MATCH_CONSTRAINT) {
        bool8 = true;
      } else {
        bool8 = false;
      }
      applyConstraints(paramLinearSystem, false, bool1, bool2, i3, (SolverVariable)localObject2, (SolverVariable)localObject1, (DimensionBehaviour)localObject4, paramBoolean, (ConstraintAnchor)localObject3, (ConstraintAnchor)localObject5, j, i, i1, n, f, bool7, bool8, bool6, bool5, i4, k, m, this.mMatchConstraintMinHeight, this.mMatchConstraintMaxHeight, this.mMatchConstraintPercentHeight, bool4);
    }
    if (bool3) {
      if (this.mResolvedDimensionRatioSide == 1) {
        paramLinearSystem.addRatio(localSolverVariable2, localSolverVariable4, localSolverVariable1, localSolverVariable3, this.mResolvedDimensionRatio, 8);
      } else {
        paramLinearSystem.addRatio(localSolverVariable1, localSolverVariable3, localSolverVariable2, localSolverVariable4, this.mResolvedDimensionRatio, 8);
      }
    }
    if (this.mCenter.isConnected()) {
      paramLinearSystem.addCenterPoint(this, this.mCenter.getTarget().getOwner(), (float)Math.toRadians(this.mCircleConstraintAngle + 90.0F), this.mCenter.getMargin());
    }
    this.resolvedHorizontal = false;
    this.resolvedVertical = false;
  }
  
  public boolean allowedInBarrier()
  {
    boolean bool;
    if (this.mVisibility != 8) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public void connect(ConstraintAnchor.Type paramType1, ConstraintWidget paramConstraintWidget, ConstraintAnchor.Type paramType2)
  {
    connect(paramType1, paramConstraintWidget, paramType2, 0);
  }
  
  public void connect(ConstraintAnchor.Type paramType1, ConstraintWidget paramConstraintWidget, ConstraintAnchor.Type paramType2, int paramInt)
  {
    ConstraintAnchor localConstraintAnchor1;
    if (paramType1 == ConstraintAnchor.Type.CENTER)
    {
      if (paramType2 == ConstraintAnchor.Type.CENTER)
      {
        localConstraintAnchor1 = getAnchor(ConstraintAnchor.Type.LEFT);
        paramType2 = getAnchor(ConstraintAnchor.Type.RIGHT);
        ConstraintAnchor localConstraintAnchor2 = getAnchor(ConstraintAnchor.Type.TOP);
        paramType1 = getAnchor(ConstraintAnchor.Type.BOTTOM);
        int i = 0;
        int j = 0;
        if (localConstraintAnchor1 != null)
        {
          paramInt = i;
          if (localConstraintAnchor1.isConnected()) {}
        }
        else if ((paramType2 != null) && (paramType2.isConnected()))
        {
          paramInt = i;
        }
        else
        {
          connect(ConstraintAnchor.Type.LEFT, paramConstraintWidget, ConstraintAnchor.Type.LEFT, 0);
          connect(ConstraintAnchor.Type.RIGHT, paramConstraintWidget, ConstraintAnchor.Type.RIGHT, 0);
          paramInt = 1;
        }
        if (localConstraintAnchor2 != null)
        {
          i = j;
          if (localConstraintAnchor2.isConnected()) {}
        }
        else if ((paramType1 != null) && (paramType1.isConnected()))
        {
          i = j;
        }
        else
        {
          connect(ConstraintAnchor.Type.TOP, paramConstraintWidget, ConstraintAnchor.Type.TOP, 0);
          connect(ConstraintAnchor.Type.BOTTOM, paramConstraintWidget, ConstraintAnchor.Type.BOTTOM, 0);
          i = 1;
        }
        if ((paramInt != 0) && (i != 0)) {
          getAnchor(ConstraintAnchor.Type.CENTER).connect(paramConstraintWidget.getAnchor(ConstraintAnchor.Type.CENTER), 0);
        } else if (paramInt != 0) {
          getAnchor(ConstraintAnchor.Type.CENTER_X).connect(paramConstraintWidget.getAnchor(ConstraintAnchor.Type.CENTER_X), 0);
        } else if (i != 0) {
          getAnchor(ConstraintAnchor.Type.CENTER_Y).connect(paramConstraintWidget.getAnchor(ConstraintAnchor.Type.CENTER_Y), 0);
        }
      }
      else if ((paramType2 != ConstraintAnchor.Type.LEFT) && (paramType2 != ConstraintAnchor.Type.RIGHT))
      {
        if ((paramType2 == ConstraintAnchor.Type.TOP) || (paramType2 == ConstraintAnchor.Type.BOTTOM))
        {
          connect(ConstraintAnchor.Type.TOP, paramConstraintWidget, paramType2, 0);
          connect(ConstraintAnchor.Type.BOTTOM, paramConstraintWidget, paramType2, 0);
          getAnchor(ConstraintAnchor.Type.CENTER).connect(paramConstraintWidget.getAnchor(paramType2), 0);
          return;
        }
      }
      else
      {
        connect(ConstraintAnchor.Type.LEFT, paramConstraintWidget, paramType2, 0);
        connect(ConstraintAnchor.Type.RIGHT, paramConstraintWidget, paramType2, 0);
        getAnchor(ConstraintAnchor.Type.CENTER).connect(paramConstraintWidget.getAnchor(paramType2), 0);
      }
    }
    else if ((paramType1 == ConstraintAnchor.Type.CENTER_X) && ((paramType2 == ConstraintAnchor.Type.LEFT) || (paramType2 == ConstraintAnchor.Type.RIGHT)))
    {
      paramType1 = getAnchor(ConstraintAnchor.Type.LEFT);
      paramType2 = paramConstraintWidget.getAnchor(paramType2);
      paramConstraintWidget = getAnchor(ConstraintAnchor.Type.RIGHT);
      paramType1.connect(paramType2, 0);
      paramConstraintWidget.connect(paramType2, 0);
      getAnchor(ConstraintAnchor.Type.CENTER_X).connect(paramType2, 0);
    }
    else if ((paramType1 == ConstraintAnchor.Type.CENTER_Y) && ((paramType2 == ConstraintAnchor.Type.TOP) || (paramType2 == ConstraintAnchor.Type.BOTTOM)))
    {
      paramType1 = paramConstraintWidget.getAnchor(paramType2);
      getAnchor(ConstraintAnchor.Type.TOP).connect(paramType1, 0);
      getAnchor(ConstraintAnchor.Type.BOTTOM).connect(paramType1, 0);
      getAnchor(ConstraintAnchor.Type.CENTER_Y).connect(paramType1, 0);
    }
    else if ((paramType1 == ConstraintAnchor.Type.CENTER_X) && (paramType2 == ConstraintAnchor.Type.CENTER_X))
    {
      getAnchor(ConstraintAnchor.Type.LEFT).connect(paramConstraintWidget.getAnchor(ConstraintAnchor.Type.LEFT), 0);
      getAnchor(ConstraintAnchor.Type.RIGHT).connect(paramConstraintWidget.getAnchor(ConstraintAnchor.Type.RIGHT), 0);
      getAnchor(ConstraintAnchor.Type.CENTER_X).connect(paramConstraintWidget.getAnchor(paramType2), 0);
    }
    else if ((paramType1 == ConstraintAnchor.Type.CENTER_Y) && (paramType2 == ConstraintAnchor.Type.CENTER_Y))
    {
      getAnchor(ConstraintAnchor.Type.TOP).connect(paramConstraintWidget.getAnchor(ConstraintAnchor.Type.TOP), 0);
      getAnchor(ConstraintAnchor.Type.BOTTOM).connect(paramConstraintWidget.getAnchor(ConstraintAnchor.Type.BOTTOM), 0);
      getAnchor(ConstraintAnchor.Type.CENTER_Y).connect(paramConstraintWidget.getAnchor(paramType2), 0);
    }
    else
    {
      localConstraintAnchor1 = getAnchor(paramType1);
      paramConstraintWidget = paramConstraintWidget.getAnchor(paramType2);
      if (localConstraintAnchor1.isValidConnection(paramConstraintWidget))
      {
        if (paramType1 == ConstraintAnchor.Type.BASELINE)
        {
          paramType2 = getAnchor(ConstraintAnchor.Type.TOP);
          paramType1 = getAnchor(ConstraintAnchor.Type.BOTTOM);
          if (paramType2 != null) {
            paramType2.reset();
          }
          if (paramType1 != null) {
            paramType1.reset();
          }
        }
        else
        {
          if ((paramType1 != ConstraintAnchor.Type.TOP) && (paramType1 != ConstraintAnchor.Type.BOTTOM)) {
            if ((paramType1 == ConstraintAnchor.Type.LEFT) || (paramType1 == ConstraintAnchor.Type.RIGHT)) {}
          }
          for (;;)
          {
            break;
            paramType2 = getAnchor(ConstraintAnchor.Type.CENTER);
            if (paramType2.getTarget() != paramConstraintWidget) {
              paramType2.reset();
            }
            paramType1 = getAnchor(paramType1).getOpposite();
            paramType2 = getAnchor(ConstraintAnchor.Type.CENTER_X);
            if (!paramType2.isConnected()) {
              break;
            }
            paramType1.reset();
            paramType2.reset();
            break;
            paramType2 = getAnchor(ConstraintAnchor.Type.BASELINE);
            if (paramType2 != null) {
              paramType2.reset();
            }
            paramType2 = getAnchor(ConstraintAnchor.Type.CENTER);
            if (paramType2.getTarget() != paramConstraintWidget) {
              paramType2.reset();
            }
            paramType1 = getAnchor(paramType1).getOpposite();
            paramType2 = getAnchor(ConstraintAnchor.Type.CENTER_Y);
            if (paramType2.isConnected())
            {
              paramType1.reset();
              paramType2.reset();
            }
          }
        }
        localConstraintAnchor1.connect(paramConstraintWidget, paramInt);
      }
    }
  }
  
  public void connect(ConstraintAnchor paramConstraintAnchor1, ConstraintAnchor paramConstraintAnchor2, int paramInt)
  {
    if (paramConstraintAnchor1.getOwner() == this) {
      connect(paramConstraintAnchor1.getType(), paramConstraintAnchor2.getOwner(), paramConstraintAnchor2.getType(), paramInt);
    }
  }
  
  public void connectCircularConstraint(ConstraintWidget paramConstraintWidget, float paramFloat, int paramInt)
  {
    immediateConnect(ConstraintAnchor.Type.CENTER, paramConstraintWidget, ConstraintAnchor.Type.CENTER, paramInt, 0);
    this.mCircleConstraintAngle = paramFloat;
  }
  
  public void copy(ConstraintWidget paramConstraintWidget, HashMap<ConstraintWidget, ConstraintWidget> paramHashMap)
  {
    this.mHorizontalResolution = paramConstraintWidget.mHorizontalResolution;
    this.mVerticalResolution = paramConstraintWidget.mVerticalResolution;
    this.mMatchConstraintDefaultWidth = paramConstraintWidget.mMatchConstraintDefaultWidth;
    this.mMatchConstraintDefaultHeight = paramConstraintWidget.mMatchConstraintDefaultHeight;
    Object localObject1 = this.mResolvedMatchConstraintDefault;
    int[] arrayOfInt = paramConstraintWidget.mResolvedMatchConstraintDefault;
    localObject1[0] = arrayOfInt[0];
    localObject1[1] = arrayOfInt[1];
    this.mMatchConstraintMinWidth = paramConstraintWidget.mMatchConstraintMinWidth;
    this.mMatchConstraintMaxWidth = paramConstraintWidget.mMatchConstraintMaxWidth;
    this.mMatchConstraintMinHeight = paramConstraintWidget.mMatchConstraintMinHeight;
    this.mMatchConstraintMaxHeight = paramConstraintWidget.mMatchConstraintMaxHeight;
    this.mMatchConstraintPercentHeight = paramConstraintWidget.mMatchConstraintPercentHeight;
    this.mIsWidthWrapContent = paramConstraintWidget.mIsWidthWrapContent;
    this.mIsHeightWrapContent = paramConstraintWidget.mIsHeightWrapContent;
    this.mResolvedDimensionRatioSide = paramConstraintWidget.mResolvedDimensionRatioSide;
    this.mResolvedDimensionRatio = paramConstraintWidget.mResolvedDimensionRatio;
    localObject1 = paramConstraintWidget.mMaxDimension;
    this.mMaxDimension = Arrays.copyOf((int[])localObject1, localObject1.length);
    this.mCircleConstraintAngle = paramConstraintWidget.mCircleConstraintAngle;
    this.hasBaseline = paramConstraintWidget.hasBaseline;
    this.inPlaceholder = paramConstraintWidget.inPlaceholder;
    this.mLeft.reset();
    this.mTop.reset();
    this.mRight.reset();
    this.mBottom.reset();
    this.mBaseline.reset();
    this.mCenterX.reset();
    this.mCenterY.reset();
    this.mCenter.reset();
    this.mListDimensionBehaviors = ((DimensionBehaviour[])Arrays.copyOf(this.mListDimensionBehaviors, 2));
    localObject1 = this.mParent;
    arrayOfInt = null;
    if (localObject1 == null) {
      localObject1 = null;
    } else {
      localObject1 = (ConstraintWidget)paramHashMap.get(paramConstraintWidget.mParent);
    }
    this.mParent = ((ConstraintWidget)localObject1);
    this.mWidth = paramConstraintWidget.mWidth;
    this.mHeight = paramConstraintWidget.mHeight;
    this.mDimensionRatio = paramConstraintWidget.mDimensionRatio;
    this.mDimensionRatioSide = paramConstraintWidget.mDimensionRatioSide;
    this.mX = paramConstraintWidget.mX;
    this.mY = paramConstraintWidget.mY;
    this.mRelX = paramConstraintWidget.mRelX;
    this.mRelY = paramConstraintWidget.mRelY;
    this.mOffsetX = paramConstraintWidget.mOffsetX;
    this.mOffsetY = paramConstraintWidget.mOffsetY;
    this.mBaselineDistance = paramConstraintWidget.mBaselineDistance;
    this.mMinWidth = paramConstraintWidget.mMinWidth;
    this.mMinHeight = paramConstraintWidget.mMinHeight;
    this.mHorizontalBiasPercent = paramConstraintWidget.mHorizontalBiasPercent;
    this.mVerticalBiasPercent = paramConstraintWidget.mVerticalBiasPercent;
    this.mCompanionWidget = paramConstraintWidget.mCompanionWidget;
    this.mContainerItemSkip = paramConstraintWidget.mContainerItemSkip;
    this.mVisibility = paramConstraintWidget.mVisibility;
    this.mAnimated = paramConstraintWidget.mAnimated;
    this.mDebugName = paramConstraintWidget.mDebugName;
    this.mType = paramConstraintWidget.mType;
    this.mDistToTop = paramConstraintWidget.mDistToTop;
    this.mDistToLeft = paramConstraintWidget.mDistToLeft;
    this.mDistToRight = paramConstraintWidget.mDistToRight;
    this.mDistToBottom = paramConstraintWidget.mDistToBottom;
    this.mLeftHasCentered = paramConstraintWidget.mLeftHasCentered;
    this.mRightHasCentered = paramConstraintWidget.mRightHasCentered;
    this.mTopHasCentered = paramConstraintWidget.mTopHasCentered;
    this.mBottomHasCentered = paramConstraintWidget.mBottomHasCentered;
    this.mHorizontalWrapVisited = paramConstraintWidget.mHorizontalWrapVisited;
    this.mVerticalWrapVisited = paramConstraintWidget.mVerticalWrapVisited;
    this.mHorizontalChainStyle = paramConstraintWidget.mHorizontalChainStyle;
    this.mVerticalChainStyle = paramConstraintWidget.mVerticalChainStyle;
    this.mHorizontalChainFixedPosition = paramConstraintWidget.mHorizontalChainFixedPosition;
    this.mVerticalChainFixedPosition = paramConstraintWidget.mVerticalChainFixedPosition;
    localObject1 = this.mWeight;
    Object localObject2 = paramConstraintWidget.mWeight;
    localObject1[0] = localObject2[0];
    localObject1[1] = localObject2[1];
    localObject2 = this.mListNextMatchConstraintsWidget;
    localObject1 = paramConstraintWidget.mListNextMatchConstraintsWidget;
    localObject2[0] = localObject1[0];
    localObject2[1] = localObject1[1];
    localObject2 = this.mNextChainWidget;
    localObject1 = paramConstraintWidget.mNextChainWidget;
    localObject2[0] = localObject1[0];
    localObject2[1] = localObject1[1];
    localObject1 = paramConstraintWidget.mHorizontalNextWidget;
    if (localObject1 == null) {
      localObject1 = null;
    } else {
      localObject1 = (ConstraintWidget)paramHashMap.get(localObject1);
    }
    this.mHorizontalNextWidget = ((ConstraintWidget)localObject1);
    paramConstraintWidget = paramConstraintWidget.mVerticalNextWidget;
    if (paramConstraintWidget == null) {
      paramConstraintWidget = arrayOfInt;
    } else {
      paramConstraintWidget = (ConstraintWidget)paramHashMap.get(paramConstraintWidget);
    }
    this.mVerticalNextWidget = paramConstraintWidget;
  }
  
  public void createObjectVariables(LinearSystem paramLinearSystem)
  {
    paramLinearSystem.createObjectVariable(this.mLeft);
    paramLinearSystem.createObjectVariable(this.mTop);
    paramLinearSystem.createObjectVariable(this.mRight);
    paramLinearSystem.createObjectVariable(this.mBottom);
    if (this.mBaselineDistance > 0) {
      paramLinearSystem.createObjectVariable(this.mBaseline);
    }
  }
  
  public void ensureMeasureRequested()
  {
    this.mMeasureRequested = true;
  }
  
  public void ensureWidgetRuns()
  {
    if (this.horizontalRun == null) {
      this.horizontalRun = new HorizontalWidgetRun(this);
    }
    if (this.verticalRun == null) {
      this.verticalRun = new VerticalWidgetRun(this);
    }
  }
  
  public ConstraintAnchor getAnchor(ConstraintAnchor.Type paramType)
  {
    switch (1.$SwitchMap$androidx$constraintlayout$core$widgets$ConstraintAnchor$Type[paramType.ordinal()])
    {
    default: 
      throw new AssertionError(paramType.name());
    case 9: 
      return null;
    case 8: 
      return this.mCenterY;
    case 7: 
      return this.mCenterX;
    case 6: 
      return this.mCenter;
    case 5: 
      return this.mBaseline;
    case 4: 
      return this.mBottom;
    case 3: 
      return this.mRight;
    case 2: 
      return this.mTop;
    }
    return this.mLeft;
  }
  
  public ArrayList<ConstraintAnchor> getAnchors()
  {
    return this.mAnchors;
  }
  
  public int getBaselineDistance()
  {
    return this.mBaselineDistance;
  }
  
  public float getBiasPercent(int paramInt)
  {
    if (paramInt == 0) {
      return this.mHorizontalBiasPercent;
    }
    if (paramInt == 1) {
      return this.mVerticalBiasPercent;
    }
    return -1.0F;
  }
  
  public int getBottom()
  {
    return getY() + this.mHeight;
  }
  
  public Object getCompanionWidget()
  {
    return this.mCompanionWidget;
  }
  
  public int getContainerItemSkip()
  {
    return this.mContainerItemSkip;
  }
  
  public String getDebugName()
  {
    return this.mDebugName;
  }
  
  public DimensionBehaviour getDimensionBehaviour(int paramInt)
  {
    if (paramInt == 0) {
      return getHorizontalDimensionBehaviour();
    }
    if (paramInt == 1) {
      return getVerticalDimensionBehaviour();
    }
    return null;
  }
  
  public float getDimensionRatio()
  {
    return this.mDimensionRatio;
  }
  
  public int getDimensionRatioSide()
  {
    return this.mDimensionRatioSide;
  }
  
  public boolean getHasBaseline()
  {
    return this.hasBaseline;
  }
  
  public int getHeight()
  {
    if (this.mVisibility == 8) {
      return 0;
    }
    return this.mHeight;
  }
  
  public float getHorizontalBiasPercent()
  {
    return this.mHorizontalBiasPercent;
  }
  
  public ConstraintWidget getHorizontalChainControlWidget()
  {
    Object localObject3 = null;
    Object localObject2 = null;
    if (isInHorizontalChain())
    {
      Object localObject1 = this;
      for (;;)
      {
        localObject3 = localObject2;
        if (localObject2 != null) {
          break;
        }
        localObject3 = localObject2;
        if (localObject1 == null) {
          break;
        }
        localObject3 = ((ConstraintWidget)localObject1).getAnchor(ConstraintAnchor.Type.LEFT);
        ConstraintAnchor localConstraintAnchor = null;
        if (localObject3 == null) {
          localObject3 = null;
        } else {
          localObject3 = ((ConstraintAnchor)localObject3).getTarget();
        }
        if (localObject3 == null) {
          localObject3 = null;
        } else {
          localObject3 = ((ConstraintAnchor)localObject3).getOwner();
        }
        if (localObject3 == getParent())
        {
          localObject3 = localObject1;
          break;
        }
        if (localObject3 != null) {
          localConstraintAnchor = ((ConstraintWidget)localObject3).getAnchor(ConstraintAnchor.Type.RIGHT).getTarget();
        }
        if ((localConstraintAnchor != null) && (localConstraintAnchor.getOwner() != localObject1)) {
          localObject2 = localObject1;
        } else {
          localObject1 = localObject3;
        }
      }
    }
    return (ConstraintWidget)localObject3;
  }
  
  public int getHorizontalChainStyle()
  {
    return this.mHorizontalChainStyle;
  }
  
  public DimensionBehaviour getHorizontalDimensionBehaviour()
  {
    return this.mListDimensionBehaviors[0];
  }
  
  public int getHorizontalMargin()
  {
    int i = 0;
    ConstraintAnchor localConstraintAnchor = this.mLeft;
    if (localConstraintAnchor != null) {
      i = 0 + localConstraintAnchor.mMargin;
    }
    localConstraintAnchor = this.mRight;
    int j = i;
    if (localConstraintAnchor != null) {
      j = i + localConstraintAnchor.mMargin;
    }
    return j;
  }
  
  public int getLastHorizontalMeasureSpec()
  {
    return this.mLastHorizontalMeasureSpec;
  }
  
  public int getLastVerticalMeasureSpec()
  {
    return this.mLastVerticalMeasureSpec;
  }
  
  public int getLeft()
  {
    return getX();
  }
  
  public int getLength(int paramInt)
  {
    if (paramInt == 0) {
      return getWidth();
    }
    if (paramInt == 1) {
      return getHeight();
    }
    return 0;
  }
  
  public int getMaxHeight()
  {
    return this.mMaxDimension[1];
  }
  
  public int getMaxWidth()
  {
    return this.mMaxDimension[0];
  }
  
  public int getMinHeight()
  {
    return this.mMinHeight;
  }
  
  public int getMinWidth()
  {
    return this.mMinWidth;
  }
  
  public ConstraintWidget getNextChainMember(int paramInt)
  {
    ConstraintAnchor localConstraintAnchor1;
    ConstraintAnchor localConstraintAnchor2;
    if (paramInt == 0)
    {
      if (this.mRight.mTarget != null)
      {
        localConstraintAnchor1 = this.mRight.mTarget.mTarget;
        localConstraintAnchor2 = this.mRight;
        if (localConstraintAnchor1 == localConstraintAnchor2) {
          return localConstraintAnchor2.mTarget.mOwner;
        }
      }
    }
    else if ((paramInt == 1) && (this.mBottom.mTarget != null))
    {
      localConstraintAnchor2 = this.mBottom.mTarget.mTarget;
      localConstraintAnchor1 = this.mBottom;
      if (localConstraintAnchor2 == localConstraintAnchor1) {
        return localConstraintAnchor1.mTarget.mOwner;
      }
    }
    return null;
  }
  
  public int getOptimizerWrapHeight()
  {
    int i = this.mHeight;
    int j = i;
    if (this.mListDimensionBehaviors[1] == DimensionBehaviour.MATCH_CONSTRAINT)
    {
      if (this.mMatchConstraintDefaultHeight == 1)
      {
        i = Math.max(this.mMatchConstraintMinHeight, i);
      }
      else if (this.mMatchConstraintMinHeight > 0)
      {
        i = this.mMatchConstraintMinHeight;
        this.mHeight = i;
      }
      else
      {
        i = 0;
      }
      int k = this.mMatchConstraintMaxHeight;
      j = i;
      if (k > 0)
      {
        j = i;
        if (k < i) {
          j = this.mMatchConstraintMaxHeight;
        }
      }
    }
    return j;
  }
  
  public int getOptimizerWrapWidth()
  {
    int i = this.mWidth;
    int j = i;
    if (this.mListDimensionBehaviors[0] == DimensionBehaviour.MATCH_CONSTRAINT)
    {
      if (this.mMatchConstraintDefaultWidth == 1)
      {
        i = Math.max(this.mMatchConstraintMinWidth, i);
      }
      else if (this.mMatchConstraintMinWidth > 0)
      {
        i = this.mMatchConstraintMinWidth;
        this.mWidth = i;
      }
      else
      {
        i = 0;
      }
      int k = this.mMatchConstraintMaxWidth;
      j = i;
      if (k > 0)
      {
        j = i;
        if (k < i) {
          j = this.mMatchConstraintMaxWidth;
        }
      }
    }
    return j;
  }
  
  public ConstraintWidget getParent()
  {
    return this.mParent;
  }
  
  public ConstraintWidget getPreviousChainMember(int paramInt)
  {
    ConstraintAnchor localConstraintAnchor1;
    ConstraintAnchor localConstraintAnchor2;
    if (paramInt == 0)
    {
      if (this.mLeft.mTarget != null)
      {
        localConstraintAnchor1 = this.mLeft.mTarget.mTarget;
        localConstraintAnchor2 = this.mLeft;
        if (localConstraintAnchor1 == localConstraintAnchor2) {
          return localConstraintAnchor2.mTarget.mOwner;
        }
      }
    }
    else if ((paramInt == 1) && (this.mTop.mTarget != null))
    {
      localConstraintAnchor1 = this.mTop.mTarget.mTarget;
      localConstraintAnchor2 = this.mTop;
      if (localConstraintAnchor1 == localConstraintAnchor2) {
        return localConstraintAnchor2.mTarget.mOwner;
      }
    }
    return null;
  }
  
  int getRelativePositioning(int paramInt)
  {
    if (paramInt == 0) {
      return this.mRelX;
    }
    if (paramInt == 1) {
      return this.mRelY;
    }
    return 0;
  }
  
  public int getRight()
  {
    return getX() + this.mWidth;
  }
  
  protected int getRootX()
  {
    return this.mX + this.mOffsetX;
  }
  
  protected int getRootY()
  {
    return this.mY + this.mOffsetY;
  }
  
  public WidgetRun getRun(int paramInt)
  {
    if (paramInt == 0) {
      return this.horizontalRun;
    }
    if (paramInt == 1) {
      return this.verticalRun;
    }
    return null;
  }
  
  public void getSceneString(StringBuilder paramStringBuilder)
  {
    paramStringBuilder.append("  " + this.stringId + ":{\n");
    paramStringBuilder.append("    actualWidth:" + this.mWidth);
    paramStringBuilder.append("\n");
    paramStringBuilder.append("    actualHeight:" + this.mHeight);
    paramStringBuilder.append("\n");
    paramStringBuilder.append("    actualLeft:" + this.mX);
    paramStringBuilder.append("\n");
    paramStringBuilder.append("    actualTop:" + this.mY);
    paramStringBuilder.append("\n");
    getSceneString(paramStringBuilder, "left", this.mLeft);
    getSceneString(paramStringBuilder, "top", this.mTop);
    getSceneString(paramStringBuilder, "right", this.mRight);
    getSceneString(paramStringBuilder, "bottom", this.mBottom);
    getSceneString(paramStringBuilder, "baseline", this.mBaseline);
    getSceneString(paramStringBuilder, "centerX", this.mCenterX);
    getSceneString(paramStringBuilder, "centerY", this.mCenterY);
    getSceneString(paramStringBuilder, "    width", this.mWidth, this.mMinWidth, this.mMaxDimension[0], this.mWidthOverride, this.mMatchConstraintMinWidth, this.mMatchConstraintDefaultWidth, this.mMatchConstraintPercentWidth, this.mWeight[0]);
    getSceneString(paramStringBuilder, "    height", this.mHeight, this.mMinHeight, this.mMaxDimension[1], this.mHeightOverride, this.mMatchConstraintMinHeight, this.mMatchConstraintDefaultHeight, this.mMatchConstraintPercentHeight, this.mWeight[1]);
    serializeDimensionRatio(paramStringBuilder, "    dimensionRatio", this.mDimensionRatio, this.mDimensionRatioSide);
    serializeAttribute(paramStringBuilder, "    horizontalBias", this.mHorizontalBiasPercent, DEFAULT_BIAS);
    serializeAttribute(paramStringBuilder, "    verticalBias", this.mVerticalBiasPercent, DEFAULT_BIAS);
    serializeAttribute(paramStringBuilder, "    horizontalChainStyle", this.mHorizontalChainStyle, 0);
    serializeAttribute(paramStringBuilder, "    verticalChainStyle", this.mVerticalChainStyle, 0);
    paramStringBuilder.append("  }");
  }
  
  public int getTop()
  {
    return getY();
  }
  
  public String getType()
  {
    return this.mType;
  }
  
  public float getVerticalBiasPercent()
  {
    return this.mVerticalBiasPercent;
  }
  
  public ConstraintWidget getVerticalChainControlWidget()
  {
    Object localObject3 = null;
    Object localObject2 = null;
    if (isInVerticalChain())
    {
      Object localObject1 = this;
      for (;;)
      {
        localObject3 = localObject2;
        if (localObject2 != null) {
          break;
        }
        localObject3 = localObject2;
        if (localObject1 == null) {
          break;
        }
        localObject3 = ((ConstraintWidget)localObject1).getAnchor(ConstraintAnchor.Type.TOP);
        ConstraintAnchor localConstraintAnchor = null;
        if (localObject3 == null) {
          localObject3 = null;
        } else {
          localObject3 = ((ConstraintAnchor)localObject3).getTarget();
        }
        if (localObject3 == null) {
          localObject3 = null;
        } else {
          localObject3 = ((ConstraintAnchor)localObject3).getOwner();
        }
        if (localObject3 == getParent())
        {
          localObject3 = localObject1;
          break;
        }
        if (localObject3 != null) {
          localConstraintAnchor = ((ConstraintWidget)localObject3).getAnchor(ConstraintAnchor.Type.BOTTOM).getTarget();
        }
        if ((localConstraintAnchor != null) && (localConstraintAnchor.getOwner() != localObject1)) {
          localObject2 = localObject1;
        } else {
          localObject1 = localObject3;
        }
      }
    }
    return (ConstraintWidget)localObject3;
  }
  
  public int getVerticalChainStyle()
  {
    return this.mVerticalChainStyle;
  }
  
  public DimensionBehaviour getVerticalDimensionBehaviour()
  {
    return this.mListDimensionBehaviors[1];
  }
  
  public int getVerticalMargin()
  {
    int i = 0;
    if (this.mLeft != null) {
      i = 0 + this.mTop.mMargin;
    }
    int j = i;
    if (this.mRight != null) {
      j = i + this.mBottom.mMargin;
    }
    return j;
  }
  
  public int getVisibility()
  {
    return this.mVisibility;
  }
  
  public int getWidth()
  {
    if (this.mVisibility == 8) {
      return 0;
    }
    return this.mWidth;
  }
  
  public int getWrapBehaviorInParent()
  {
    return this.mWrapBehaviorInParent;
  }
  
  public int getX()
  {
    ConstraintWidget localConstraintWidget = this.mParent;
    if ((localConstraintWidget != null) && ((localConstraintWidget instanceof ConstraintWidgetContainer))) {
      return ((ConstraintWidgetContainer)localConstraintWidget).mPaddingLeft + this.mX;
    }
    return this.mX;
  }
  
  public int getY()
  {
    ConstraintWidget localConstraintWidget = this.mParent;
    if ((localConstraintWidget != null) && ((localConstraintWidget instanceof ConstraintWidgetContainer))) {
      return ((ConstraintWidgetContainer)localConstraintWidget).mPaddingTop + this.mY;
    }
    return this.mY;
  }
  
  public boolean hasBaseline()
  {
    return this.hasBaseline;
  }
  
  public boolean hasDanglingDimension(int paramInt)
  {
    boolean bool1 = true;
    boolean bool2 = true;
    int i;
    if (paramInt == 0)
    {
      if (this.mLeft.mTarget != null) {
        paramInt = 1;
      } else {
        paramInt = 0;
      }
      if (this.mRight.mTarget != null) {
        i = 1;
      } else {
        i = 0;
      }
      if (paramInt + i < 2) {
        bool1 = bool2;
      } else {
        bool1 = false;
      }
      return bool1;
    }
    if (this.mTop.mTarget != null) {
      paramInt = 1;
    } else {
      paramInt = 0;
    }
    if (this.mBottom.mTarget != null) {
      i = 1;
    } else {
      i = 0;
    }
    int j;
    if (this.mBaseline.mTarget != null) {
      j = 1;
    } else {
      j = 0;
    }
    if (paramInt + i + j >= 2) {
      bool1 = false;
    }
    return bool1;
  }
  
  public boolean hasDependencies()
  {
    int i = 0;
    int j = this.mAnchors.size();
    while (i < j)
    {
      if (((ConstraintAnchor)this.mAnchors.get(i)).hasDependents()) {
        return true;
      }
      i++;
    }
    return false;
  }
  
  public boolean hasDimensionOverride()
  {
    boolean bool;
    if ((this.mWidthOverride == -1) && (this.mHeightOverride == -1)) {
      bool = false;
    } else {
      bool = true;
    }
    return bool;
  }
  
  public boolean hasResolvedTargets(int paramInt1, int paramInt2)
  {
    boolean bool1 = true;
    boolean bool2 = true;
    if (paramInt1 == 0)
    {
      if ((this.mLeft.mTarget != null) && (this.mLeft.mTarget.hasFinalValue()) && (this.mRight.mTarget != null) && (this.mRight.mTarget.hasFinalValue()))
      {
        if (this.mRight.mTarget.getFinalValue() - this.mRight.getMargin() - (this.mLeft.mTarget.getFinalValue() + this.mLeft.getMargin()) >= paramInt2) {
          bool1 = bool2;
        } else {
          bool1 = false;
        }
        return bool1;
      }
    }
    else if ((this.mTop.mTarget != null) && (this.mTop.mTarget.hasFinalValue()) && (this.mBottom.mTarget != null) && (this.mBottom.mTarget.hasFinalValue()))
    {
      if (this.mBottom.mTarget.getFinalValue() - this.mBottom.getMargin() - (this.mTop.mTarget.getFinalValue() + this.mTop.getMargin()) < paramInt2) {
        bool1 = false;
      }
      return bool1;
    }
    return false;
  }
  
  public void immediateConnect(ConstraintAnchor.Type paramType1, ConstraintWidget paramConstraintWidget, ConstraintAnchor.Type paramType2, int paramInt1, int paramInt2)
  {
    getAnchor(paramType1).connect(paramConstraintWidget.getAnchor(paramType2), paramInt1, paramInt2, true);
  }
  
  public boolean isAnimated()
  {
    return this.mAnimated;
  }
  
  public boolean isHeightWrapContent()
  {
    return this.mIsHeightWrapContent;
  }
  
  public boolean isHorizontalSolvingPassDone()
  {
    return this.horizontalSolvingPass;
  }
  
  public boolean isInBarrier(int paramInt)
  {
    return this.mIsInBarrier[paramInt];
  }
  
  public boolean isInHorizontalChain()
  {
    return ((this.mLeft.mTarget != null) && (this.mLeft.mTarget.mTarget == this.mLeft)) || ((this.mRight.mTarget != null) && (this.mRight.mTarget.mTarget == this.mRight));
  }
  
  public boolean isInPlaceholder()
  {
    return this.inPlaceholder;
  }
  
  public boolean isInVerticalChain()
  {
    return ((this.mTop.mTarget != null) && (this.mTop.mTarget.mTarget == this.mTop)) || ((this.mBottom.mTarget != null) && (this.mBottom.mTarget.mTarget == this.mBottom));
  }
  
  public boolean isInVirtualLayout()
  {
    return this.mInVirtualLayout;
  }
  
  public boolean isMeasureRequested()
  {
    boolean bool;
    if ((this.mMeasureRequested) && (this.mVisibility != 8)) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public boolean isResolvedHorizontally()
  {
    boolean bool;
    if ((!this.resolvedHorizontal) && ((!this.mLeft.hasFinalValue()) || (!this.mRight.hasFinalValue()))) {
      bool = false;
    } else {
      bool = true;
    }
    return bool;
  }
  
  public boolean isResolvedVertically()
  {
    boolean bool;
    if ((!this.resolvedVertical) && ((!this.mTop.hasFinalValue()) || (!this.mBottom.hasFinalValue()))) {
      bool = false;
    } else {
      bool = true;
    }
    return bool;
  }
  
  public boolean isRoot()
  {
    boolean bool;
    if (this.mParent == null) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public boolean isSpreadHeight()
  {
    int i = this.mMatchConstraintDefaultHeight;
    boolean bool = true;
    if ((i != 0) || (this.mDimensionRatio != 0.0F) || (this.mMatchConstraintMinHeight != 0) || (this.mMatchConstraintMaxHeight != 0) || (this.mListDimensionBehaviors[1] != DimensionBehaviour.MATCH_CONSTRAINT)) {
      bool = false;
    }
    return bool;
  }
  
  public boolean isSpreadWidth()
  {
    int i = this.mMatchConstraintDefaultWidth;
    boolean bool2 = false;
    boolean bool1 = bool2;
    if (i == 0)
    {
      bool1 = bool2;
      if (this.mDimensionRatio == 0.0F)
      {
        bool1 = bool2;
        if (this.mMatchConstraintMinWidth == 0)
        {
          bool1 = bool2;
          if (this.mMatchConstraintMaxWidth == 0)
          {
            bool1 = bool2;
            if (this.mListDimensionBehaviors[0] == DimensionBehaviour.MATCH_CONSTRAINT) {
              bool1 = true;
            }
          }
        }
      }
    }
    return bool1;
  }
  
  public boolean isVerticalSolvingPassDone()
  {
    return this.verticalSolvingPass;
  }
  
  public boolean isWidthWrapContent()
  {
    return this.mIsWidthWrapContent;
  }
  
  public void markHorizontalSolvingPassDone()
  {
    this.horizontalSolvingPass = true;
  }
  
  public void markVerticalSolvingPassDone()
  {
    this.verticalSolvingPass = true;
  }
  
  public boolean oppositeDimensionDependsOn(int paramInt)
  {
    boolean bool = true;
    int i;
    if (paramInt == 0) {
      i = 1;
    } else {
      i = 0;
    }
    Object localObject2 = this.mListDimensionBehaviors;
    Object localObject1 = localObject2[paramInt];
    localObject2 = localObject2[i];
    if ((localObject1 != DimensionBehaviour.MATCH_CONSTRAINT) || (localObject2 != DimensionBehaviour.MATCH_CONSTRAINT)) {
      bool = false;
    }
    return bool;
  }
  
  public boolean oppositeDimensionsTied()
  {
    DimensionBehaviour[] arrayOfDimensionBehaviour = this.mListDimensionBehaviors;
    boolean bool2 = false;
    boolean bool1 = bool2;
    if (arrayOfDimensionBehaviour[0] == DimensionBehaviour.MATCH_CONSTRAINT)
    {
      bool1 = bool2;
      if (this.mListDimensionBehaviors[1] == DimensionBehaviour.MATCH_CONSTRAINT) {
        bool1 = true;
      }
    }
    return bool1;
  }
  
  public void reset()
  {
    this.mLeft.reset();
    this.mTop.reset();
    this.mRight.reset();
    this.mBottom.reset();
    this.mBaseline.reset();
    this.mCenterX.reset();
    this.mCenterY.reset();
    this.mCenter.reset();
    this.mParent = null;
    this.mCircleConstraintAngle = 0.0F;
    this.mWidth = 0;
    this.mHeight = 0;
    this.mDimensionRatio = 0.0F;
    this.mDimensionRatioSide = -1;
    this.mX = 0;
    this.mY = 0;
    this.mOffsetX = 0;
    this.mOffsetY = 0;
    this.mBaselineDistance = 0;
    this.mMinWidth = 0;
    this.mMinHeight = 0;
    float f = DEFAULT_BIAS;
    this.mHorizontalBiasPercent = f;
    this.mVerticalBiasPercent = f;
    this.mListDimensionBehaviors[0] = DimensionBehaviour.FIXED;
    this.mListDimensionBehaviors[1] = DimensionBehaviour.FIXED;
    this.mCompanionWidget = null;
    this.mContainerItemSkip = 0;
    this.mVisibility = 0;
    this.mType = null;
    this.mHorizontalWrapVisited = false;
    this.mVerticalWrapVisited = false;
    this.mHorizontalChainStyle = 0;
    this.mVerticalChainStyle = 0;
    this.mHorizontalChainFixedPosition = false;
    this.mVerticalChainFixedPosition = false;
    Object localObject = this.mWeight;
    localObject[0] = -1.0F;
    localObject[1] = -1.0F;
    this.mHorizontalResolution = -1;
    this.mVerticalResolution = -1;
    localObject = this.mMaxDimension;
    localObject[0] = Integer.MAX_VALUE;
    localObject[1] = Integer.MAX_VALUE;
    this.mMatchConstraintDefaultWidth = 0;
    this.mMatchConstraintDefaultHeight = 0;
    this.mMatchConstraintPercentWidth = 1.0F;
    this.mMatchConstraintPercentHeight = 1.0F;
    this.mMatchConstraintMaxWidth = Integer.MAX_VALUE;
    this.mMatchConstraintMaxHeight = Integer.MAX_VALUE;
    this.mMatchConstraintMinWidth = 0;
    this.mMatchConstraintMinHeight = 0;
    this.mResolvedHasRatio = false;
    this.mResolvedDimensionRatioSide = -1;
    this.mResolvedDimensionRatio = 1.0F;
    this.mGroupsToSolver = false;
    localObject = this.isTerminalWidget;
    localObject[0] = 1;
    localObject[1] = 1;
    this.mInVirtualLayout = false;
    localObject = this.mIsInBarrier;
    localObject[0] = 0;
    localObject[1] = 0;
    this.mMeasureRequested = true;
    localObject = this.mResolvedMatchConstraintDefault;
    localObject[0] = 0;
    localObject[1] = 0;
    this.mWidthOverride = -1;
    this.mHeightOverride = -1;
  }
  
  public void resetAllConstraints()
  {
    resetAnchors();
    setVerticalBiasPercent(DEFAULT_BIAS);
    setHorizontalBiasPercent(DEFAULT_BIAS);
  }
  
  public void resetAnchor(ConstraintAnchor paramConstraintAnchor)
  {
    if ((getParent() != null) && ((getParent() instanceof ConstraintWidgetContainer)) && (((ConstraintWidgetContainer)getParent()).handlesInternalConstraints())) {
      return;
    }
    ConstraintAnchor localConstraintAnchor2 = getAnchor(ConstraintAnchor.Type.LEFT);
    ConstraintAnchor localConstraintAnchor1 = getAnchor(ConstraintAnchor.Type.RIGHT);
    ConstraintAnchor localConstraintAnchor3 = getAnchor(ConstraintAnchor.Type.TOP);
    ConstraintAnchor localConstraintAnchor5 = getAnchor(ConstraintAnchor.Type.BOTTOM);
    ConstraintAnchor localConstraintAnchor6 = getAnchor(ConstraintAnchor.Type.CENTER);
    ConstraintAnchor localConstraintAnchor7 = getAnchor(ConstraintAnchor.Type.CENTER_X);
    ConstraintAnchor localConstraintAnchor4 = getAnchor(ConstraintAnchor.Type.CENTER_Y);
    if (paramConstraintAnchor == localConstraintAnchor6)
    {
      if ((localConstraintAnchor2.isConnected()) && (localConstraintAnchor1.isConnected()) && (localConstraintAnchor2.getTarget() == localConstraintAnchor1.getTarget()))
      {
        localConstraintAnchor2.reset();
        localConstraintAnchor1.reset();
      }
      if ((localConstraintAnchor3.isConnected()) && (localConstraintAnchor5.isConnected()) && (localConstraintAnchor3.getTarget() == localConstraintAnchor5.getTarget()))
      {
        localConstraintAnchor3.reset();
        localConstraintAnchor5.reset();
      }
      this.mHorizontalBiasPercent = 0.5F;
      this.mVerticalBiasPercent = 0.5F;
    }
    else if (paramConstraintAnchor == localConstraintAnchor7)
    {
      if ((localConstraintAnchor2.isConnected()) && (localConstraintAnchor1.isConnected()) && (localConstraintAnchor2.getTarget().getOwner() == localConstraintAnchor1.getTarget().getOwner()))
      {
        localConstraintAnchor2.reset();
        localConstraintAnchor1.reset();
      }
      this.mHorizontalBiasPercent = 0.5F;
    }
    else if (paramConstraintAnchor == localConstraintAnchor4)
    {
      if ((localConstraintAnchor3.isConnected()) && (localConstraintAnchor5.isConnected()) && (localConstraintAnchor3.getTarget().getOwner() == localConstraintAnchor5.getTarget().getOwner()))
      {
        localConstraintAnchor3.reset();
        localConstraintAnchor5.reset();
      }
      this.mVerticalBiasPercent = 0.5F;
    }
    else if ((paramConstraintAnchor != localConstraintAnchor2) && (paramConstraintAnchor != localConstraintAnchor1))
    {
      if (((paramConstraintAnchor == localConstraintAnchor3) || (paramConstraintAnchor == localConstraintAnchor5)) && (localConstraintAnchor3.isConnected()) && (localConstraintAnchor3.getTarget() == localConstraintAnchor5.getTarget())) {
        localConstraintAnchor6.reset();
      }
    }
    else if ((localConstraintAnchor2.isConnected()) && (localConstraintAnchor2.getTarget() == localConstraintAnchor1.getTarget()))
    {
      localConstraintAnchor6.reset();
    }
    paramConstraintAnchor.reset();
  }
  
  public void resetAnchors()
  {
    ConstraintWidget localConstraintWidget = getParent();
    if ((localConstraintWidget != null) && ((localConstraintWidget instanceof ConstraintWidgetContainer)) && (((ConstraintWidgetContainer)getParent()).handlesInternalConstraints())) {
      return;
    }
    int i = 0;
    int j = this.mAnchors.size();
    while (i < j)
    {
      ((ConstraintAnchor)this.mAnchors.get(i)).reset();
      i++;
    }
  }
  
  public void resetFinalResolution()
  {
    this.resolvedHorizontal = false;
    this.resolvedVertical = false;
    this.horizontalSolvingPass = false;
    this.verticalSolvingPass = false;
    int i = 0;
    int j = this.mAnchors.size();
    while (i < j)
    {
      ((ConstraintAnchor)this.mAnchors.get(i)).resetFinalResolution();
      i++;
    }
  }
  
  public void resetSolverVariables(Cache paramCache)
  {
    this.mLeft.resetSolverVariable(paramCache);
    this.mTop.resetSolverVariable(paramCache);
    this.mRight.resetSolverVariable(paramCache);
    this.mBottom.resetSolverVariable(paramCache);
    this.mBaseline.resetSolverVariable(paramCache);
    this.mCenter.resetSolverVariable(paramCache);
    this.mCenterX.resetSolverVariable(paramCache);
    this.mCenterY.resetSolverVariable(paramCache);
  }
  
  public void resetSolvingPassFlag()
  {
    this.horizontalSolvingPass = false;
    this.verticalSolvingPass = false;
  }
  
  public StringBuilder serialize(StringBuilder paramStringBuilder)
  {
    paramStringBuilder.append("{\n");
    serializeAnchor(paramStringBuilder, "left", this.mLeft);
    serializeAnchor(paramStringBuilder, "top", this.mTop);
    serializeAnchor(paramStringBuilder, "right", this.mRight);
    serializeAnchor(paramStringBuilder, "bottom", this.mBottom);
    serializeAnchor(paramStringBuilder, "baseline", this.mBaseline);
    serializeAnchor(paramStringBuilder, "centerX", this.mCenterX);
    serializeAnchor(paramStringBuilder, "centerY", this.mCenterY);
    serializeCircle(paramStringBuilder, this.mCenter, this.mCircleConstraintAngle);
    serializeSize(paramStringBuilder, "width", this.mWidth, this.mMinWidth, this.mMaxDimension[0], this.mWidthOverride, this.mMatchConstraintMinWidth, this.mMatchConstraintDefaultWidth, this.mMatchConstraintPercentWidth, this.mWeight[0]);
    serializeSize(paramStringBuilder, "height", this.mHeight, this.mMinHeight, this.mMaxDimension[1], this.mHeightOverride, this.mMatchConstraintMinHeight, this.mMatchConstraintDefaultHeight, this.mMatchConstraintPercentHeight, this.mWeight[1]);
    serializeDimensionRatio(paramStringBuilder, "dimensionRatio", this.mDimensionRatio, this.mDimensionRatioSide);
    serializeAttribute(paramStringBuilder, "horizontalBias", this.mHorizontalBiasPercent, DEFAULT_BIAS);
    serializeAttribute(paramStringBuilder, "verticalBias", this.mVerticalBiasPercent, DEFAULT_BIAS);
    paramStringBuilder.append("}\n");
    return paramStringBuilder;
  }
  
  public void setAnimated(boolean paramBoolean)
  {
    this.mAnimated = paramBoolean;
  }
  
  public void setBaselineDistance(int paramInt)
  {
    this.mBaselineDistance = paramInt;
    boolean bool;
    if (paramInt > 0) {
      bool = true;
    } else {
      bool = false;
    }
    this.hasBaseline = bool;
  }
  
  public void setCompanionWidget(Object paramObject)
  {
    this.mCompanionWidget = paramObject;
  }
  
  public void setContainerItemSkip(int paramInt)
  {
    if (paramInt >= 0) {
      this.mContainerItemSkip = paramInt;
    } else {
      this.mContainerItemSkip = 0;
    }
  }
  
  public void setDebugName(String paramString)
  {
    this.mDebugName = paramString;
  }
  
  public void setDebugSolverName(LinearSystem paramLinearSystem, String paramString)
  {
    this.mDebugName = paramString;
    SolverVariable localSolverVariable4 = paramLinearSystem.createObjectVariable(this.mLeft);
    SolverVariable localSolverVariable3 = paramLinearSystem.createObjectVariable(this.mTop);
    SolverVariable localSolverVariable1 = paramLinearSystem.createObjectVariable(this.mRight);
    SolverVariable localSolverVariable2 = paramLinearSystem.createObjectVariable(this.mBottom);
    localSolverVariable4.setName(paramString + ".left");
    localSolverVariable3.setName(paramString + ".top");
    localSolverVariable1.setName(paramString + ".right");
    localSolverVariable2.setName(paramString + ".bottom");
    paramLinearSystem.createObjectVariable(this.mBaseline).setName(paramString + ".baseline");
  }
  
  public void setDimension(int paramInt1, int paramInt2)
  {
    this.mWidth = paramInt1;
    int i = this.mMinWidth;
    if (paramInt1 < i) {
      this.mWidth = i;
    }
    this.mHeight = paramInt2;
    paramInt1 = this.mMinHeight;
    if (paramInt2 < paramInt1) {
      this.mHeight = paramInt1;
    }
  }
  
  public void setDimensionRatio(float paramFloat, int paramInt)
  {
    this.mDimensionRatio = paramFloat;
    this.mDimensionRatioSide = paramInt;
  }
  
  public void setDimensionRatio(String paramString)
  {
    if ((paramString != null) && (paramString.length() != 0))
    {
      int i = -1;
      float f2 = 0.0F;
      float f4 = 0.0F;
      float f3 = 0.0F;
      int k = paramString.length();
      int j = paramString.indexOf(',');
      String str;
      if ((j > 0) && (j < k - 1))
      {
        str = paramString.substring(0, j);
        if (str.equalsIgnoreCase("W")) {
          i = 0;
        } else if (str.equalsIgnoreCase("H")) {
          i = 1;
        }
        j++;
      }
      else
      {
        j = 0;
      }
      int m = paramString.indexOf(':');
      float f1;
      if ((m >= 0) && (m < k - 1))
      {
        str = paramString.substring(j, m);
        paramString = paramString.substring(m + 1);
        f1 = f2;
        if (str.length() > 0)
        {
          f1 = f2;
          if (paramString.length() > 0) {
            try
            {
              float f5 = Float.parseFloat(str);
              f4 = Float.parseFloat(paramString);
              f1 = f3;
              if (f5 > 0.0F)
              {
                f1 = f3;
                if (f4 > 0.0F) {
                  if (i == 1) {
                    f1 = Math.abs(f4 / f5);
                  } else {
                    f1 = Math.abs(f5 / f4);
                  }
                }
              }
            }
            catch (NumberFormatException paramString)
            {
              f1 = f2;
            }
          }
        }
      }
      else
      {
        paramString = paramString.substring(j);
        f1 = f4;
        if (paramString.length() > 0) {
          try
          {
            f1 = Float.parseFloat(paramString);
          }
          catch (NumberFormatException paramString)
          {
            f1 = f4;
          }
        }
      }
      if (f1 > 0.0F)
      {
        this.mDimensionRatio = f1;
        this.mDimensionRatioSide = i;
      }
      return;
    }
    this.mDimensionRatio = 0.0F;
  }
  
  public void setFinalBaseline(int paramInt)
  {
    if (!this.hasBaseline) {
      return;
    }
    int j = paramInt - this.mBaselineDistance;
    int i = this.mHeight;
    this.mY = j;
    this.mTop.setFinalValue(j);
    this.mBottom.setFinalValue(i + j);
    this.mBaseline.setFinalValue(paramInt);
    this.resolvedVertical = true;
  }
  
  public void setFinalFrame(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6)
  {
    setFrame(paramInt1, paramInt2, paramInt3, paramInt4);
    setBaselineDistance(paramInt5);
    if (paramInt6 == 0)
    {
      this.resolvedHorizontal = true;
      this.resolvedVertical = false;
    }
    else if (paramInt6 == 1)
    {
      this.resolvedHorizontal = false;
      this.resolvedVertical = true;
    }
    else if (paramInt6 == 2)
    {
      this.resolvedHorizontal = true;
      this.resolvedVertical = true;
    }
    else
    {
      this.resolvedHorizontal = false;
      this.resolvedVertical = false;
    }
  }
  
  public void setFinalHorizontal(int paramInt1, int paramInt2)
  {
    if (this.resolvedHorizontal) {
      return;
    }
    this.mLeft.setFinalValue(paramInt1);
    this.mRight.setFinalValue(paramInt2);
    this.mX = paramInt1;
    this.mWidth = (paramInt2 - paramInt1);
    this.resolvedHorizontal = true;
  }
  
  public void setFinalLeft(int paramInt)
  {
    this.mLeft.setFinalValue(paramInt);
    this.mX = paramInt;
  }
  
  public void setFinalTop(int paramInt)
  {
    this.mTop.setFinalValue(paramInt);
    this.mY = paramInt;
  }
  
  public void setFinalVertical(int paramInt1, int paramInt2)
  {
    if (this.resolvedVertical) {
      return;
    }
    this.mTop.setFinalValue(paramInt1);
    this.mBottom.setFinalValue(paramInt2);
    this.mY = paramInt1;
    this.mHeight = (paramInt2 - paramInt1);
    if (this.hasBaseline) {
      this.mBaseline.setFinalValue(this.mBaselineDistance + paramInt1);
    }
    this.resolvedVertical = true;
  }
  
  public void setFrame(int paramInt1, int paramInt2, int paramInt3)
  {
    if (paramInt3 == 0) {
      setHorizontalDimension(paramInt1, paramInt2);
    } else if (paramInt3 == 1) {
      setVerticalDimension(paramInt1, paramInt2);
    }
  }
  
  public void setFrame(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    int i = paramInt3 - paramInt1;
    paramInt3 = paramInt4 - paramInt2;
    this.mX = paramInt1;
    this.mY = paramInt2;
    if (this.mVisibility == 8)
    {
      this.mWidth = 0;
      this.mHeight = 0;
      return;
    }
    paramInt1 = i;
    if (this.mListDimensionBehaviors[0] == DimensionBehaviour.FIXED)
    {
      paramInt1 = i;
      if (i < this.mWidth) {
        paramInt1 = this.mWidth;
      }
    }
    paramInt2 = paramInt3;
    if (this.mListDimensionBehaviors[1] == DimensionBehaviour.FIXED)
    {
      paramInt2 = paramInt3;
      if (paramInt3 < this.mHeight) {
        paramInt2 = this.mHeight;
      }
    }
    this.mWidth = paramInt1;
    this.mHeight = paramInt2;
    paramInt3 = this.mMinHeight;
    if (paramInt2 < paramInt3) {
      this.mHeight = paramInt3;
    }
    paramInt3 = this.mMinWidth;
    if (paramInt1 < paramInt3) {
      this.mWidth = paramInt3;
    }
    if ((this.mMatchConstraintMaxWidth > 0) && (this.mListDimensionBehaviors[0] == DimensionBehaviour.MATCH_CONSTRAINT)) {
      this.mWidth = Math.min(this.mWidth, this.mMatchConstraintMaxWidth);
    }
    if ((this.mMatchConstraintMaxHeight > 0) && (this.mListDimensionBehaviors[1] == DimensionBehaviour.MATCH_CONSTRAINT)) {
      this.mHeight = Math.min(this.mHeight, this.mMatchConstraintMaxHeight);
    }
    paramInt3 = this.mWidth;
    if (paramInt1 != paramInt3) {
      this.mWidthOverride = paramInt3;
    }
    paramInt1 = this.mHeight;
    if (paramInt2 != paramInt1) {
      this.mHeightOverride = paramInt1;
    }
  }
  
  public void setGoneMargin(ConstraintAnchor.Type paramType, int paramInt)
  {
    switch (1.$SwitchMap$androidx$constraintlayout$core$widgets$ConstraintAnchor$Type[paramType.ordinal()])
    {
    default: 
      break;
    case 5: 
      this.mBaseline.mGoneMargin = paramInt;
      break;
    case 4: 
      this.mBottom.mGoneMargin = paramInt;
      break;
    case 3: 
      this.mRight.mGoneMargin = paramInt;
      break;
    case 2: 
      this.mTop.mGoneMargin = paramInt;
      break;
    case 1: 
      this.mLeft.mGoneMargin = paramInt;
    }
  }
  
  public void setHasBaseline(boolean paramBoolean)
  {
    this.hasBaseline = paramBoolean;
  }
  
  public void setHeight(int paramInt)
  {
    this.mHeight = paramInt;
    int i = this.mMinHeight;
    if (paramInt < i) {
      this.mHeight = i;
    }
  }
  
  public void setHeightWrapContent(boolean paramBoolean)
  {
    this.mIsHeightWrapContent = paramBoolean;
  }
  
  public void setHorizontalBiasPercent(float paramFloat)
  {
    this.mHorizontalBiasPercent = paramFloat;
  }
  
  public void setHorizontalChainStyle(int paramInt)
  {
    this.mHorizontalChainStyle = paramInt;
  }
  
  public void setHorizontalDimension(int paramInt1, int paramInt2)
  {
    this.mX = paramInt1;
    paramInt1 = paramInt2 - paramInt1;
    this.mWidth = paramInt1;
    paramInt2 = this.mMinWidth;
    if (paramInt1 < paramInt2) {
      this.mWidth = paramInt2;
    }
  }
  
  public void setHorizontalDimensionBehaviour(DimensionBehaviour paramDimensionBehaviour)
  {
    this.mListDimensionBehaviors[0] = paramDimensionBehaviour;
  }
  
  public void setHorizontalMatchStyle(int paramInt1, int paramInt2, int paramInt3, float paramFloat)
  {
    this.mMatchConstraintDefaultWidth = paramInt1;
    this.mMatchConstraintMinWidth = paramInt2;
    if (paramInt3 == Integer.MAX_VALUE) {
      paramInt3 = 0;
    }
    this.mMatchConstraintMaxWidth = paramInt3;
    this.mMatchConstraintPercentWidth = paramFloat;
    if ((paramFloat > 0.0F) && (paramFloat < 1.0F) && (paramInt1 == 0)) {
      this.mMatchConstraintDefaultWidth = 2;
    }
  }
  
  public void setHorizontalWeight(float paramFloat)
  {
    this.mWeight[0] = paramFloat;
  }
  
  protected void setInBarrier(int paramInt, boolean paramBoolean)
  {
    this.mIsInBarrier[paramInt] = paramBoolean;
  }
  
  public void setInPlaceholder(boolean paramBoolean)
  {
    this.inPlaceholder = paramBoolean;
  }
  
  public void setInVirtualLayout(boolean paramBoolean)
  {
    this.mInVirtualLayout = paramBoolean;
  }
  
  public void setLastMeasureSpec(int paramInt1, int paramInt2)
  {
    this.mLastHorizontalMeasureSpec = paramInt1;
    this.mLastVerticalMeasureSpec = paramInt2;
    setMeasureRequested(false);
  }
  
  public void setLength(int paramInt1, int paramInt2)
  {
    if (paramInt2 == 0) {
      setWidth(paramInt1);
    } else if (paramInt2 == 1) {
      setHeight(paramInt1);
    }
  }
  
  public void setMaxHeight(int paramInt)
  {
    this.mMaxDimension[1] = paramInt;
  }
  
  public void setMaxWidth(int paramInt)
  {
    this.mMaxDimension[0] = paramInt;
  }
  
  public void setMeasureRequested(boolean paramBoolean)
  {
    this.mMeasureRequested = paramBoolean;
  }
  
  public void setMinHeight(int paramInt)
  {
    if (paramInt < 0) {
      this.mMinHeight = 0;
    } else {
      this.mMinHeight = paramInt;
    }
  }
  
  public void setMinWidth(int paramInt)
  {
    if (paramInt < 0) {
      this.mMinWidth = 0;
    } else {
      this.mMinWidth = paramInt;
    }
  }
  
  public void setOffset(int paramInt1, int paramInt2)
  {
    this.mOffsetX = paramInt1;
    this.mOffsetY = paramInt2;
  }
  
  public void setOrigin(int paramInt1, int paramInt2)
  {
    this.mX = paramInt1;
    this.mY = paramInt2;
  }
  
  public void setParent(ConstraintWidget paramConstraintWidget)
  {
    this.mParent = paramConstraintWidget;
  }
  
  void setRelativePositioning(int paramInt1, int paramInt2)
  {
    if (paramInt2 == 0) {
      this.mRelX = paramInt1;
    } else if (paramInt2 == 1) {
      this.mRelY = paramInt1;
    }
  }
  
  public void setType(String paramString)
  {
    this.mType = paramString;
  }
  
  public void setVerticalBiasPercent(float paramFloat)
  {
    this.mVerticalBiasPercent = paramFloat;
  }
  
  public void setVerticalChainStyle(int paramInt)
  {
    this.mVerticalChainStyle = paramInt;
  }
  
  public void setVerticalDimension(int paramInt1, int paramInt2)
  {
    this.mY = paramInt1;
    paramInt2 -= paramInt1;
    this.mHeight = paramInt2;
    paramInt1 = this.mMinHeight;
    if (paramInt2 < paramInt1) {
      this.mHeight = paramInt1;
    }
  }
  
  public void setVerticalDimensionBehaviour(DimensionBehaviour paramDimensionBehaviour)
  {
    this.mListDimensionBehaviors[1] = paramDimensionBehaviour;
  }
  
  public void setVerticalMatchStyle(int paramInt1, int paramInt2, int paramInt3, float paramFloat)
  {
    this.mMatchConstraintDefaultHeight = paramInt1;
    this.mMatchConstraintMinHeight = paramInt2;
    if (paramInt3 == Integer.MAX_VALUE) {
      paramInt2 = 0;
    } else {
      paramInt2 = paramInt3;
    }
    this.mMatchConstraintMaxHeight = paramInt2;
    this.mMatchConstraintPercentHeight = paramFloat;
    if ((paramFloat > 0.0F) && (paramFloat < 1.0F) && (paramInt1 == 0)) {
      this.mMatchConstraintDefaultHeight = 2;
    }
  }
  
  public void setVerticalWeight(float paramFloat)
  {
    this.mWeight[1] = paramFloat;
  }
  
  public void setVisibility(int paramInt)
  {
    this.mVisibility = paramInt;
  }
  
  public void setWidth(int paramInt)
  {
    this.mWidth = paramInt;
    int i = this.mMinWidth;
    if (paramInt < i) {
      this.mWidth = i;
    }
  }
  
  public void setWidthWrapContent(boolean paramBoolean)
  {
    this.mIsWidthWrapContent = paramBoolean;
  }
  
  public void setWrapBehaviorInParent(int paramInt)
  {
    if ((paramInt >= 0) && (paramInt <= 3)) {
      this.mWrapBehaviorInParent = paramInt;
    }
  }
  
  public void setX(int paramInt)
  {
    this.mX = paramInt;
  }
  
  public void setY(int paramInt)
  {
    this.mY = paramInt;
  }
  
  public void setupDimensionRatio(boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3, boolean paramBoolean4)
  {
    if (this.mResolvedDimensionRatioSide == -1) {
      if ((paramBoolean3) && (!paramBoolean4))
      {
        this.mResolvedDimensionRatioSide = 0;
      }
      else if ((!paramBoolean3) && (paramBoolean4))
      {
        this.mResolvedDimensionRatioSide = 1;
        if (this.mDimensionRatioSide == -1) {
          this.mResolvedDimensionRatio = (1.0F / this.mResolvedDimensionRatio);
        }
      }
    }
    if ((this.mResolvedDimensionRatioSide == 0) && ((!this.mTop.isConnected()) || (!this.mBottom.isConnected()))) {
      this.mResolvedDimensionRatioSide = 1;
    } else if ((this.mResolvedDimensionRatioSide == 1) && ((!this.mLeft.isConnected()) || (!this.mRight.isConnected()))) {
      this.mResolvedDimensionRatioSide = 0;
    }
    if ((this.mResolvedDimensionRatioSide == -1) && ((!this.mTop.isConnected()) || (!this.mBottom.isConnected()) || (!this.mLeft.isConnected()) || (!this.mRight.isConnected()))) {
      if ((this.mTop.isConnected()) && (this.mBottom.isConnected()))
      {
        this.mResolvedDimensionRatioSide = 0;
      }
      else if ((this.mLeft.isConnected()) && (this.mRight.isConnected()))
      {
        this.mResolvedDimensionRatio = (1.0F / this.mResolvedDimensionRatio);
        this.mResolvedDimensionRatioSide = 1;
      }
    }
    if (this.mResolvedDimensionRatioSide == -1)
    {
      int i = this.mMatchConstraintMinWidth;
      if ((i > 0) && (this.mMatchConstraintMinHeight == 0))
      {
        this.mResolvedDimensionRatioSide = 0;
      }
      else if ((i == 0) && (this.mMatchConstraintMinHeight > 0))
      {
        this.mResolvedDimensionRatio = (1.0F / this.mResolvedDimensionRatio);
        this.mResolvedDimensionRatioSide = 1;
      }
    }
  }
  
  public String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    Object localObject = this.mType;
    String str = "";
    if (localObject != null) {
      localObject = "type: " + this.mType + " ";
    } else {
      localObject = "";
    }
    localStringBuilder = localStringBuilder.append((String)localObject);
    localObject = str;
    if (this.mDebugName != null) {
      localObject = "id: " + this.mDebugName + " ";
    }
    return (String)localObject + "(" + this.mX + ", " + this.mY + ") - (" + this.mWidth + " x " + this.mHeight + ")";
  }
  
  public void updateFromRuns(boolean paramBoolean1, boolean paramBoolean2)
  {
    boolean bool2 = paramBoolean1 & this.horizontalRun.isResolved();
    boolean bool1 = paramBoolean2 & this.verticalRun.isResolved();
    int k = this.horizontalRun.start.value;
    int i = this.verticalRun.start.value;
    int m = this.horizontalRun.end.value;
    int n = this.verticalRun.end.value;
    int j;
    if ((m - k >= 0) && (n - i >= 0) && (k != Integer.MIN_VALUE) && (k != Integer.MAX_VALUE) && (i != Integer.MIN_VALUE) && (i != Integer.MAX_VALUE) && (m != Integer.MIN_VALUE) && (m != Integer.MAX_VALUE) && (n != Integer.MIN_VALUE))
    {
      j = n;
      if (n != Integer.MAX_VALUE) {}
    }
    else
    {
      k = 0;
      i = 0;
      m = 0;
      j = 0;
    }
    m -= k;
    j -= i;
    if (bool2) {
      this.mX = k;
    }
    if (bool1) {
      this.mY = i;
    }
    if (this.mVisibility == 8)
    {
      this.mWidth = 0;
      this.mHeight = 0;
      return;
    }
    if (bool2)
    {
      i = m;
      if (this.mListDimensionBehaviors[0] == DimensionBehaviour.FIXED)
      {
        i = m;
        if (m < this.mWidth) {
          i = this.mWidth;
        }
      }
      this.mWidth = i;
      k = this.mMinWidth;
      if (i < k) {
        this.mWidth = k;
      }
    }
    if (bool1)
    {
      i = j;
      if (this.mListDimensionBehaviors[1] == DimensionBehaviour.FIXED)
      {
        i = j;
        if (j < this.mHeight) {
          i = this.mHeight;
        }
      }
      this.mHeight = i;
      j = this.mMinHeight;
      if (i < j) {
        this.mHeight = j;
      }
    }
  }
  
  public void updateFromSolver(LinearSystem paramLinearSystem, boolean paramBoolean)
  {
    int j = paramLinearSystem.getObjectVariableValue(this.mLeft);
    int n = paramLinearSystem.getObjectVariableValue(this.mTop);
    int m = paramLinearSystem.getObjectVariableValue(this.mRight);
    int i1 = paramLinearSystem.getObjectVariableValue(this.mBottom);
    int k = j;
    int i = m;
    if (paramBoolean)
    {
      paramLinearSystem = this.horizontalRun;
      k = j;
      i = m;
      if (paramLinearSystem != null)
      {
        k = j;
        i = m;
        if (paramLinearSystem.start.resolved)
        {
          k = j;
          i = m;
          if (this.horizontalRun.end.resolved)
          {
            k = this.horizontalRun.start.value;
            i = this.horizontalRun.end.value;
          }
        }
      }
    }
    m = n;
    j = i1;
    if (paramBoolean)
    {
      paramLinearSystem = this.verticalRun;
      m = n;
      j = i1;
      if (paramLinearSystem != null)
      {
        m = n;
        j = i1;
        if (paramLinearSystem.start.resolved)
        {
          m = n;
          j = i1;
          if (this.verticalRun.end.resolved)
          {
            m = this.verticalRun.start.value;
            j = this.verticalRun.end.value;
          }
        }
      }
    }
    if ((i - k >= 0) && (j - m >= 0) && (k != Integer.MIN_VALUE) && (k != Integer.MAX_VALUE) && (m != Integer.MIN_VALUE) && (m != Integer.MAX_VALUE) && (i != Integer.MIN_VALUE) && (i != Integer.MAX_VALUE) && (j != Integer.MIN_VALUE))
    {
      n = i;
      i = j;
      if (j != Integer.MAX_VALUE) {}
    }
    else
    {
      k = 0;
      m = 0;
      n = 0;
      i = 0;
    }
    setFrame(k, m, n, i);
  }
  
  public static enum DimensionBehaviour
  {
    private static final DimensionBehaviour[] $VALUES;
    
    static
    {
      DimensionBehaviour localDimensionBehaviour3 = new DimensionBehaviour("FIXED", 0);
      FIXED = localDimensionBehaviour3;
      DimensionBehaviour localDimensionBehaviour4 = new DimensionBehaviour("WRAP_CONTENT", 1);
      WRAP_CONTENT = localDimensionBehaviour4;
      DimensionBehaviour localDimensionBehaviour2 = new DimensionBehaviour("MATCH_CONSTRAINT", 2);
      MATCH_CONSTRAINT = localDimensionBehaviour2;
      DimensionBehaviour localDimensionBehaviour1 = new DimensionBehaviour("MATCH_PARENT", 3);
      MATCH_PARENT = localDimensionBehaviour1;
      $VALUES = new DimensionBehaviour[] { localDimensionBehaviour3, localDimensionBehaviour4, localDimensionBehaviour2, localDimensionBehaviour1 };
    }
    
    private DimensionBehaviour() {}
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/constraintlayout/core/widgets/ConstraintWidget.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */