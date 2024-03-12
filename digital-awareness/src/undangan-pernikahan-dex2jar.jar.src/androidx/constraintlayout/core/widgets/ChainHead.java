package androidx.constraintlayout.core.widgets;

import java.util.ArrayList;

public class ChainHead
{
  private boolean mDefined;
  protected ConstraintWidget mFirst;
  protected ConstraintWidget mFirstMatchConstraintWidget;
  protected ConstraintWidget mFirstVisibleWidget;
  protected boolean mHasComplexMatchWeights;
  protected boolean mHasDefinedWeights;
  protected boolean mHasRatio;
  protected boolean mHasUndefinedWeights;
  protected ConstraintWidget mHead;
  private boolean mIsRtl = false;
  protected ConstraintWidget mLast;
  protected ConstraintWidget mLastMatchConstraintWidget;
  protected ConstraintWidget mLastVisibleWidget;
  boolean mOptimizable;
  private int mOrientation;
  int mTotalMargins;
  int mTotalSize;
  protected float mTotalWeight = 0.0F;
  int mVisibleWidgets;
  protected ArrayList<ConstraintWidget> mWeightedMatchConstraintsWidgets;
  protected int mWidgetsCount;
  protected int mWidgetsMatchCount;
  
  public ChainHead(ConstraintWidget paramConstraintWidget, int paramInt, boolean paramBoolean)
  {
    this.mFirst = paramConstraintWidget;
    this.mOrientation = paramInt;
    this.mIsRtl = paramBoolean;
  }
  
  private void defineChainProperties()
  {
    int j = this.mOrientation * 2;
    Object localObject3 = this.mFirst;
    boolean bool = true;
    this.mOptimizable = true;
    Object localObject2 = this.mFirst;
    Object localObject1 = this.mFirst;
    int i = 0;
    while (i == 0)
    {
      this.mWidgetsCount += 1;
      ((ConstraintWidget)localObject2).mNextChainWidget[this.mOrientation] = null;
      ((ConstraintWidget)localObject2).mListNextMatchConstraintsWidget[this.mOrientation] = null;
      if (((ConstraintWidget)localObject2).getVisibility() != 8)
      {
        this.mVisibleWidgets += 1;
        if (((ConstraintWidget)localObject2).getDimensionBehaviour(this.mOrientation) != ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
          this.mTotalSize += ((ConstraintWidget)localObject2).getLength(this.mOrientation);
        }
        int k = this.mTotalSize + localObject2.mListAnchors[j].getMargin();
        this.mTotalSize = k;
        this.mTotalSize = (k + localObject2.mListAnchors[(j + 1)].getMargin());
        k = this.mTotalMargins + localObject2.mListAnchors[j].getMargin();
        this.mTotalMargins = k;
        this.mTotalMargins = (k + localObject2.mListAnchors[(j + 1)].getMargin());
        if (this.mFirstVisibleWidget == null) {
          this.mFirstVisibleWidget = ((ConstraintWidget)localObject2);
        }
        this.mLastVisibleWidget = ((ConstraintWidget)localObject2);
        if (localObject2.mListDimensionBehaviors[this.mOrientation] == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT)
        {
          if ((localObject2.mResolvedMatchConstraintDefault[this.mOrientation] == 0) || (localObject2.mResolvedMatchConstraintDefault[this.mOrientation] == 3) || (localObject2.mResolvedMatchConstraintDefault[this.mOrientation] == 2))
          {
            this.mWidgetsMatchCount += 1;
            float f = localObject2.mWeight[this.mOrientation];
            if (f > 0.0F) {
              this.mTotalWeight += localObject2.mWeight[this.mOrientation];
            }
            if (isMatchConstraintEqualityCandidate((ConstraintWidget)localObject2, this.mOrientation))
            {
              if (f < 0.0F) {
                this.mHasUndefinedWeights = true;
              } else {
                this.mHasDefinedWeights = true;
              }
              if (this.mWeightedMatchConstraintsWidgets == null) {
                this.mWeightedMatchConstraintsWidgets = new ArrayList();
              }
              this.mWeightedMatchConstraintsWidgets.add(localObject2);
            }
            if (this.mFirstMatchConstraintWidget == null) {
              this.mFirstMatchConstraintWidget = ((ConstraintWidget)localObject2);
            }
            localObject1 = this.mLastMatchConstraintWidget;
            if (localObject1 != null) {
              ((ConstraintWidget)localObject1).mListNextMatchConstraintsWidget[this.mOrientation] = localObject2;
            }
            this.mLastMatchConstraintWidget = ((ConstraintWidget)localObject2);
          }
          if (this.mOrientation == 0)
          {
            if (((ConstraintWidget)localObject2).mMatchConstraintDefaultWidth != 0) {
              this.mOptimizable = false;
            } else if ((((ConstraintWidget)localObject2).mMatchConstraintMinWidth != 0) || (((ConstraintWidget)localObject2).mMatchConstraintMaxWidth != 0)) {
              this.mOptimizable = false;
            }
          }
          else if (((ConstraintWidget)localObject2).mMatchConstraintDefaultHeight != 0) {
            this.mOptimizable = false;
          } else if ((((ConstraintWidget)localObject2).mMatchConstraintMinHeight != 0) || (((ConstraintWidget)localObject2).mMatchConstraintMaxHeight != 0)) {
            this.mOptimizable = false;
          }
          if (((ConstraintWidget)localObject2).mDimensionRatio != 0.0F)
          {
            this.mOptimizable = false;
            this.mHasRatio = true;
          }
        }
      }
      if (localObject3 != localObject2) {
        ((ConstraintWidget)localObject3).mNextChainWidget[this.mOrientation] = localObject2;
      }
      localObject3 = localObject2;
      localObject1 = localObject2.mListAnchors[(j + 1)].mTarget;
      if (localObject1 != null)
      {
        ConstraintWidget localConstraintWidget = ((ConstraintAnchor)localObject1).mOwner;
        if (localConstraintWidget.mListAnchors[j].mTarget != null)
        {
          localObject1 = localConstraintWidget;
          if (localConstraintWidget.mListAnchors[j].mTarget.mOwner == localObject2) {}
        }
        else
        {
          localObject1 = null;
        }
      }
      else
      {
        localObject1 = null;
      }
      if (localObject1 != null) {
        localObject2 = localObject1;
      } else {
        i = 1;
      }
    }
    localObject1 = this.mFirstVisibleWidget;
    if (localObject1 != null) {
      this.mTotalSize -= localObject1.mListAnchors[j].getMargin();
    }
    localObject1 = this.mLastVisibleWidget;
    if (localObject1 != null) {
      this.mTotalSize -= localObject1.mListAnchors[(j + 1)].getMargin();
    }
    this.mLast = ((ConstraintWidget)localObject2);
    if ((this.mOrientation == 0) && (this.mIsRtl)) {
      this.mHead = ((ConstraintWidget)localObject2);
    } else {
      this.mHead = this.mFirst;
    }
    if ((!this.mHasDefinedWeights) || (!this.mHasUndefinedWeights)) {
      bool = false;
    }
    this.mHasComplexMatchWeights = bool;
  }
  
  private static boolean isMatchConstraintEqualityCandidate(ConstraintWidget paramConstraintWidget, int paramInt)
  {
    boolean bool;
    if ((paramConstraintWidget.getVisibility() != 8) && (paramConstraintWidget.mListDimensionBehaviors[paramInt] == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) && ((paramConstraintWidget.mResolvedMatchConstraintDefault[paramInt] == 0) || (paramConstraintWidget.mResolvedMatchConstraintDefault[paramInt] == 3))) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public void define()
  {
    if (!this.mDefined) {
      defineChainProperties();
    }
    this.mDefined = true;
  }
  
  public ConstraintWidget getFirst()
  {
    return this.mFirst;
  }
  
  public ConstraintWidget getFirstMatchConstraintWidget()
  {
    return this.mFirstMatchConstraintWidget;
  }
  
  public ConstraintWidget getFirstVisibleWidget()
  {
    return this.mFirstVisibleWidget;
  }
  
  public ConstraintWidget getHead()
  {
    return this.mHead;
  }
  
  public ConstraintWidget getLast()
  {
    return this.mLast;
  }
  
  public ConstraintWidget getLastMatchConstraintWidget()
  {
    return this.mLastMatchConstraintWidget;
  }
  
  public ConstraintWidget getLastVisibleWidget()
  {
    return this.mLastVisibleWidget;
  }
  
  public float getTotalWeight()
  {
    return this.mTotalWeight;
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/constraintlayout/core/widgets/ChainHead.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */