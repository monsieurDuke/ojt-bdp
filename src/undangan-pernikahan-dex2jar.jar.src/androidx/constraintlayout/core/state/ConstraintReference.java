package androidx.constraintlayout.core.state;

import androidx.constraintlayout.core.state.helpers.Facade;
import androidx.constraintlayout.core.widgets.ConstraintAnchor;
import androidx.constraintlayout.core.widgets.ConstraintAnchor.Type;
import androidx.constraintlayout.core.widgets.ConstraintWidget;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

public class ConstraintReference
  implements Reference
{
  private Object key;
  float mAlpha = NaN.0F;
  Object mBaselineToBaseline = null;
  Object mBaselineToBottom = null;
  Object mBaselineToTop = null;
  protected Object mBottomToBottom = null;
  protected Object mBottomToTop = null;
  private float mCircularAngle;
  Object mCircularConstraint = null;
  private float mCircularDistance;
  private ConstraintWidget mConstraintWidget;
  private HashMap<String, Integer> mCustomColors = new HashMap();
  private HashMap<String, Float> mCustomFloats = new HashMap();
  protected Object mEndToEnd = null;
  protected Object mEndToStart = null;
  Facade mFacade = null;
  float mHorizontalBias = 0.5F;
  int mHorizontalChainStyle = 0;
  float mHorizontalChainWeight = -1.0F;
  Dimension mHorizontalDimension = Dimension.Fixed(Dimension.WRAP_DIMENSION);
  State.Constraint mLast = null;
  protected Object mLeftToLeft = null;
  protected Object mLeftToRight = null;
  int mMarginBaseline = 0;
  int mMarginBaselineGone = 0;
  protected int mMarginBottom = 0;
  protected int mMarginBottomGone = 0;
  protected int mMarginEnd = 0;
  protected int mMarginEndGone = 0;
  protected int mMarginLeft = 0;
  protected int mMarginLeftGone = 0;
  protected int mMarginRight = 0;
  protected int mMarginRightGone = 0;
  protected int mMarginStart = 0;
  protected int mMarginStartGone = 0;
  protected int mMarginTop = 0;
  protected int mMarginTopGone = 0;
  float mPivotX = NaN.0F;
  float mPivotY = NaN.0F;
  protected Object mRightToLeft = null;
  protected Object mRightToRight = null;
  float mRotationX = NaN.0F;
  float mRotationY = NaN.0F;
  float mRotationZ = NaN.0F;
  float mScaleX = NaN.0F;
  float mScaleY = NaN.0F;
  protected Object mStartToEnd = null;
  protected Object mStartToStart = null;
  final State mState;
  String mTag = null;
  protected Object mTopToBottom = null;
  protected Object mTopToTop = null;
  float mTranslationX = NaN.0F;
  float mTranslationY = NaN.0F;
  float mTranslationZ = NaN.0F;
  float mVerticalBias = 0.5F;
  int mVerticalChainStyle = 0;
  float mVerticalChainWeight = -1.0F;
  Dimension mVerticalDimension = Dimension.Fixed(Dimension.WRAP_DIMENSION);
  private Object mView;
  int mVisibility = 0;
  
  public ConstraintReference(State paramState)
  {
    this.mState = paramState;
  }
  
  private void applyConnection(ConstraintWidget paramConstraintWidget, Object paramObject, State.Constraint paramConstraint)
  {
    paramObject = getTarget(paramObject);
    if (paramObject == null) {
      return;
    }
    int i = 1.$SwitchMap$androidx$constraintlayout$core$state$State$Constraint[paramConstraint.ordinal()];
    switch (1.$SwitchMap$androidx$constraintlayout$core$state$State$Constraint[paramConstraint.ordinal()])
    {
    default: 
      break;
    case 16: 
      paramConstraintWidget.connectCircularConstraint((ConstraintWidget)paramObject, this.mCircularAngle, (int)this.mCircularDistance);
      break;
    case 15: 
      paramConstraintWidget.immediateConnect(ConstraintAnchor.Type.BASELINE, (ConstraintWidget)paramObject, ConstraintAnchor.Type.BASELINE, this.mMarginBaseline, this.mMarginBaselineGone);
      break;
    case 14: 
      paramConstraintWidget.immediateConnect(ConstraintAnchor.Type.BASELINE, (ConstraintWidget)paramObject, ConstraintAnchor.Type.TOP, this.mMarginBaseline, this.mMarginBaselineGone);
      break;
    case 13: 
      paramConstraintWidget.immediateConnect(ConstraintAnchor.Type.BASELINE, (ConstraintWidget)paramObject, ConstraintAnchor.Type.BOTTOM, this.mMarginBaseline, this.mMarginBaselineGone);
      break;
    case 12: 
      paramConstraintWidget.getAnchor(ConstraintAnchor.Type.BOTTOM).connect(((ConstraintWidget)paramObject).getAnchor(ConstraintAnchor.Type.BOTTOM), this.mMarginBottom, this.mMarginBottomGone, false);
      break;
    case 11: 
      paramConstraintWidget.getAnchor(ConstraintAnchor.Type.BOTTOM).connect(((ConstraintWidget)paramObject).getAnchor(ConstraintAnchor.Type.TOP), this.mMarginBottom, this.mMarginBottomGone, false);
      break;
    case 10: 
      paramConstraintWidget.getAnchor(ConstraintAnchor.Type.TOP).connect(((ConstraintWidget)paramObject).getAnchor(ConstraintAnchor.Type.BOTTOM), this.mMarginTop, this.mMarginTopGone, false);
      break;
    case 9: 
      paramConstraintWidget.getAnchor(ConstraintAnchor.Type.TOP).connect(((ConstraintWidget)paramObject).getAnchor(ConstraintAnchor.Type.TOP), this.mMarginTop, this.mMarginTopGone, false);
      break;
    case 8: 
      paramConstraintWidget.getAnchor(ConstraintAnchor.Type.RIGHT).connect(((ConstraintWidget)paramObject).getAnchor(ConstraintAnchor.Type.RIGHT), this.mMarginEnd, this.mMarginEndGone, false);
      break;
    case 7: 
      paramConstraintWidget.getAnchor(ConstraintAnchor.Type.RIGHT).connect(((ConstraintWidget)paramObject).getAnchor(ConstraintAnchor.Type.LEFT), this.mMarginEnd, this.mMarginEndGone, false);
      break;
    case 6: 
      paramConstraintWidget.getAnchor(ConstraintAnchor.Type.LEFT).connect(((ConstraintWidget)paramObject).getAnchor(ConstraintAnchor.Type.RIGHT), this.mMarginStart, this.mMarginStartGone, false);
      break;
    case 5: 
      paramConstraintWidget.getAnchor(ConstraintAnchor.Type.LEFT).connect(((ConstraintWidget)paramObject).getAnchor(ConstraintAnchor.Type.LEFT), this.mMarginStart, this.mMarginStartGone, false);
      break;
    case 4: 
      paramConstraintWidget.getAnchor(ConstraintAnchor.Type.RIGHT).connect(((ConstraintWidget)paramObject).getAnchor(ConstraintAnchor.Type.RIGHT), this.mMarginRight, this.mMarginRightGone, false);
      break;
    case 3: 
      paramConstraintWidget.getAnchor(ConstraintAnchor.Type.RIGHT).connect(((ConstraintWidget)paramObject).getAnchor(ConstraintAnchor.Type.LEFT), this.mMarginRight, this.mMarginRightGone, false);
      break;
    case 2: 
      paramConstraintWidget.getAnchor(ConstraintAnchor.Type.LEFT).connect(((ConstraintWidget)paramObject).getAnchor(ConstraintAnchor.Type.RIGHT), this.mMarginLeft, this.mMarginLeftGone, false);
      break;
    case 1: 
      paramConstraintWidget.getAnchor(ConstraintAnchor.Type.LEFT).connect(((ConstraintWidget)paramObject).getAnchor(ConstraintAnchor.Type.LEFT), this.mMarginLeft, this.mMarginLeftGone, false);
    }
  }
  
  private void dereference()
  {
    this.mLeftToLeft = get(this.mLeftToLeft);
    this.mLeftToRight = get(this.mLeftToRight);
    this.mRightToLeft = get(this.mRightToLeft);
    this.mRightToRight = get(this.mRightToRight);
    this.mStartToStart = get(this.mStartToStart);
    this.mStartToEnd = get(this.mStartToEnd);
    this.mEndToStart = get(this.mEndToStart);
    this.mEndToEnd = get(this.mEndToEnd);
    this.mTopToTop = get(this.mTopToTop);
    this.mTopToBottom = get(this.mTopToBottom);
    this.mBottomToTop = get(this.mBottomToTop);
    this.mBottomToBottom = get(this.mBottomToBottom);
    this.mBaselineToBaseline = get(this.mBaselineToBaseline);
    this.mBaselineToTop = get(this.mBaselineToTop);
    this.mBaselineToBottom = get(this.mBaselineToBottom);
  }
  
  private Object get(Object paramObject)
  {
    if (paramObject == null) {
      return null;
    }
    if (!(paramObject instanceof ConstraintReference)) {
      return this.mState.reference(paramObject);
    }
    return paramObject;
  }
  
  private ConstraintWidget getTarget(Object paramObject)
  {
    if ((paramObject instanceof Reference)) {
      return ((Reference)paramObject).getConstraintWidget();
    }
    return null;
  }
  
  public void addCustomColor(String paramString, int paramInt)
  {
    this.mCustomColors.put(paramString, Integer.valueOf(paramInt));
  }
  
  public void addCustomFloat(String paramString, float paramFloat)
  {
    if (this.mCustomFloats == null) {
      this.mCustomFloats = new HashMap();
    }
    this.mCustomFloats.put(paramString, Float.valueOf(paramFloat));
  }
  
  public ConstraintReference alpha(float paramFloat)
  {
    this.mAlpha = paramFloat;
    return this;
  }
  
  public void apply()
  {
    if (this.mConstraintWidget == null) {
      return;
    }
    Object localObject = this.mFacade;
    if (localObject != null) {
      ((Facade)localObject).apply();
    }
    this.mHorizontalDimension.apply(this.mState, this.mConstraintWidget, 0);
    this.mVerticalDimension.apply(this.mState, this.mConstraintWidget, 1);
    dereference();
    applyConnection(this.mConstraintWidget, this.mLeftToLeft, State.Constraint.LEFT_TO_LEFT);
    applyConnection(this.mConstraintWidget, this.mLeftToRight, State.Constraint.LEFT_TO_RIGHT);
    applyConnection(this.mConstraintWidget, this.mRightToLeft, State.Constraint.RIGHT_TO_LEFT);
    applyConnection(this.mConstraintWidget, this.mRightToRight, State.Constraint.RIGHT_TO_RIGHT);
    applyConnection(this.mConstraintWidget, this.mStartToStart, State.Constraint.START_TO_START);
    applyConnection(this.mConstraintWidget, this.mStartToEnd, State.Constraint.START_TO_END);
    applyConnection(this.mConstraintWidget, this.mEndToStart, State.Constraint.END_TO_START);
    applyConnection(this.mConstraintWidget, this.mEndToEnd, State.Constraint.END_TO_END);
    applyConnection(this.mConstraintWidget, this.mTopToTop, State.Constraint.TOP_TO_TOP);
    applyConnection(this.mConstraintWidget, this.mTopToBottom, State.Constraint.TOP_TO_BOTTOM);
    applyConnection(this.mConstraintWidget, this.mBottomToTop, State.Constraint.BOTTOM_TO_TOP);
    applyConnection(this.mConstraintWidget, this.mBottomToBottom, State.Constraint.BOTTOM_TO_BOTTOM);
    applyConnection(this.mConstraintWidget, this.mBaselineToBaseline, State.Constraint.BASELINE_TO_BASELINE);
    applyConnection(this.mConstraintWidget, this.mBaselineToTop, State.Constraint.BASELINE_TO_TOP);
    applyConnection(this.mConstraintWidget, this.mBaselineToBottom, State.Constraint.BASELINE_TO_BOTTOM);
    applyConnection(this.mConstraintWidget, this.mCircularConstraint, State.Constraint.CIRCULAR_CONSTRAINT);
    int i = this.mHorizontalChainStyle;
    if (i != 0) {
      this.mConstraintWidget.setHorizontalChainStyle(i);
    }
    i = this.mVerticalChainStyle;
    if (i != 0) {
      this.mConstraintWidget.setVerticalChainStyle(i);
    }
    float f = this.mHorizontalChainWeight;
    if (f != -1.0F) {
      this.mConstraintWidget.setHorizontalWeight(f);
    }
    f = this.mVerticalChainWeight;
    if (f != -1.0F) {
      this.mConstraintWidget.setVerticalWeight(f);
    }
    this.mConstraintWidget.setHorizontalBiasPercent(this.mHorizontalBias);
    this.mConstraintWidget.setVerticalBiasPercent(this.mVerticalBias);
    this.mConstraintWidget.frame.pivotX = this.mPivotX;
    this.mConstraintWidget.frame.pivotY = this.mPivotY;
    this.mConstraintWidget.frame.rotationX = this.mRotationX;
    this.mConstraintWidget.frame.rotationY = this.mRotationY;
    this.mConstraintWidget.frame.rotationZ = this.mRotationZ;
    this.mConstraintWidget.frame.translationX = this.mTranslationX;
    this.mConstraintWidget.frame.translationY = this.mTranslationY;
    this.mConstraintWidget.frame.translationZ = this.mTranslationZ;
    this.mConstraintWidget.frame.scaleX = this.mScaleX;
    this.mConstraintWidget.frame.scaleY = this.mScaleY;
    this.mConstraintWidget.frame.alpha = this.mAlpha;
    this.mConstraintWidget.frame.visibility = this.mVisibility;
    this.mConstraintWidget.setVisibility(this.mVisibility);
    localObject = this.mCustomColors;
    Iterator localIterator;
    if (localObject != null)
    {
      localIterator = ((HashMap)localObject).keySet().iterator();
      while (localIterator.hasNext())
      {
        String str = (String)localIterator.next();
        localObject = (Integer)this.mCustomColors.get(str);
        this.mConstraintWidget.frame.setCustomAttribute(str, 902, ((Integer)localObject).intValue());
      }
    }
    localObject = this.mCustomFloats;
    if (localObject != null)
    {
      localIterator = ((HashMap)localObject).keySet().iterator();
      while (localIterator.hasNext())
      {
        localObject = (String)localIterator.next();
        f = ((Float)this.mCustomFloats.get(localObject)).floatValue();
        this.mConstraintWidget.frame.setCustomAttribute((String)localObject, 901, f);
      }
    }
  }
  
  public ConstraintReference baseline()
  {
    this.mLast = State.Constraint.BASELINE_TO_BASELINE;
    return this;
  }
  
  public ConstraintReference baselineToBaseline(Object paramObject)
  {
    this.mLast = State.Constraint.BASELINE_TO_BASELINE;
    this.mBaselineToBaseline = paramObject;
    return this;
  }
  
  public ConstraintReference baselineToBottom(Object paramObject)
  {
    this.mLast = State.Constraint.BASELINE_TO_BOTTOM;
    this.mBaselineToBottom = paramObject;
    return this;
  }
  
  public ConstraintReference baselineToTop(Object paramObject)
  {
    this.mLast = State.Constraint.BASELINE_TO_TOP;
    this.mBaselineToTop = paramObject;
    return this;
  }
  
  public ConstraintReference bias(float paramFloat)
  {
    if (this.mLast == null) {
      return this;
    }
    switch (1.$SwitchMap$androidx$constraintlayout$core$state$State$Constraint[this.mLast.ordinal()])
    {
    case 13: 
    case 14: 
    case 15: 
    case 16: 
    default: 
      break;
    case 9: 
    case 10: 
    case 11: 
    case 12: 
    case 18: 
      this.mVerticalBias = paramFloat;
      break;
    case 1: 
    case 2: 
    case 3: 
    case 4: 
    case 5: 
    case 6: 
    case 7: 
    case 8: 
    case 17: 
      this.mHorizontalBias = paramFloat;
    }
    return this;
  }
  
  public ConstraintReference bottom()
  {
    if (this.mBottomToTop != null) {
      this.mLast = State.Constraint.BOTTOM_TO_TOP;
    } else {
      this.mLast = State.Constraint.BOTTOM_TO_BOTTOM;
    }
    return this;
  }
  
  public ConstraintReference bottomToBottom(Object paramObject)
  {
    this.mLast = State.Constraint.BOTTOM_TO_BOTTOM;
    this.mBottomToBottom = paramObject;
    return this;
  }
  
  public ConstraintReference bottomToTop(Object paramObject)
  {
    this.mLast = State.Constraint.BOTTOM_TO_TOP;
    this.mBottomToTop = paramObject;
    return this;
  }
  
  public ConstraintReference centerHorizontally(Object paramObject)
  {
    paramObject = get(paramObject);
    this.mStartToStart = paramObject;
    this.mEndToEnd = paramObject;
    this.mLast = State.Constraint.CENTER_HORIZONTALLY;
    this.mHorizontalBias = 0.5F;
    return this;
  }
  
  public ConstraintReference centerVertically(Object paramObject)
  {
    paramObject = get(paramObject);
    this.mTopToTop = paramObject;
    this.mBottomToBottom = paramObject;
    this.mLast = State.Constraint.CENTER_VERTICALLY;
    this.mVerticalBias = 0.5F;
    return this;
  }
  
  public ConstraintReference circularConstraint(Object paramObject, float paramFloat1, float paramFloat2)
  {
    this.mCircularConstraint = get(paramObject);
    this.mCircularAngle = paramFloat1;
    this.mCircularDistance = paramFloat2;
    this.mLast = State.Constraint.CIRCULAR_CONSTRAINT;
    return this;
  }
  
  public ConstraintReference clear()
  {
    if (this.mLast != null)
    {
      switch (1.$SwitchMap$androidx$constraintlayout$core$state$State$Constraint[this.mLast.ordinal()])
      {
      case 13: 
      case 14: 
      default: 
        break;
      case 16: 
        this.mCircularConstraint = null;
        break;
      case 15: 
        this.mBaselineToBaseline = null;
        break;
      case 11: 
      case 12: 
        this.mBottomToTop = null;
        this.mBottomToBottom = null;
        this.mMarginBottom = 0;
        this.mMarginBottomGone = 0;
        break;
      case 9: 
      case 10: 
        this.mTopToTop = null;
        this.mTopToBottom = null;
        this.mMarginTop = 0;
        this.mMarginTopGone = 0;
        break;
      case 7: 
      case 8: 
        this.mEndToStart = null;
        this.mEndToEnd = null;
        this.mMarginEnd = 0;
        this.mMarginEndGone = 0;
        break;
      case 5: 
      case 6: 
        this.mStartToStart = null;
        this.mStartToEnd = null;
        this.mMarginStart = 0;
        this.mMarginStartGone = 0;
        break;
      case 3: 
      case 4: 
        this.mRightToLeft = null;
        this.mRightToRight = null;
        this.mMarginRight = 0;
        this.mMarginRightGone = 0;
        break;
      case 1: 
      case 2: 
        this.mLeftToLeft = null;
        this.mLeftToRight = null;
        this.mMarginLeft = 0;
        this.mMarginLeftGone = 0;
        break;
      }
    }
    else
    {
      this.mLeftToLeft = null;
      this.mLeftToRight = null;
      this.mMarginLeft = 0;
      this.mRightToLeft = null;
      this.mRightToRight = null;
      this.mMarginRight = 0;
      this.mStartToStart = null;
      this.mStartToEnd = null;
      this.mMarginStart = 0;
      this.mEndToStart = null;
      this.mEndToEnd = null;
      this.mMarginEnd = 0;
      this.mTopToTop = null;
      this.mTopToBottom = null;
      this.mMarginTop = 0;
      this.mBottomToTop = null;
      this.mBottomToBottom = null;
      this.mMarginBottom = 0;
      this.mBaselineToBaseline = null;
      this.mCircularConstraint = null;
      this.mHorizontalBias = 0.5F;
      this.mVerticalBias = 0.5F;
      this.mMarginLeftGone = 0;
      this.mMarginRightGone = 0;
      this.mMarginStartGone = 0;
      this.mMarginEndGone = 0;
      this.mMarginTopGone = 0;
      this.mMarginBottomGone = 0;
    }
    return this;
  }
  
  public ConstraintReference clearHorizontal()
  {
    start().clear();
    end().clear();
    left().clear();
    right().clear();
    return this;
  }
  
  public ConstraintReference clearVertical()
  {
    top().clear();
    baseline().clear();
    bottom().clear();
    return this;
  }
  
  public ConstraintWidget createConstraintWidget()
  {
    return new ConstraintWidget(getWidth().getValue(), getHeight().getValue());
  }
  
  public ConstraintReference end()
  {
    if (this.mEndToStart != null) {
      this.mLast = State.Constraint.END_TO_START;
    } else {
      this.mLast = State.Constraint.END_TO_END;
    }
    return this;
  }
  
  public ConstraintReference endToEnd(Object paramObject)
  {
    this.mLast = State.Constraint.END_TO_END;
    this.mEndToEnd = paramObject;
    return this;
  }
  
  public ConstraintReference endToStart(Object paramObject)
  {
    this.mLast = State.Constraint.END_TO_START;
    this.mEndToStart = paramObject;
    return this;
  }
  
  public float getAlpha()
  {
    return this.mAlpha;
  }
  
  public ConstraintWidget getConstraintWidget()
  {
    if (this.mConstraintWidget == null)
    {
      ConstraintWidget localConstraintWidget = createConstraintWidget();
      this.mConstraintWidget = localConstraintWidget;
      localConstraintWidget.setCompanionWidget(this.mView);
    }
    return this.mConstraintWidget;
  }
  
  public Facade getFacade()
  {
    return this.mFacade;
  }
  
  public Dimension getHeight()
  {
    return this.mVerticalDimension;
  }
  
  public int getHorizontalChainStyle()
  {
    return this.mHorizontalChainStyle;
  }
  
  public float getHorizontalChainWeight()
  {
    return this.mHorizontalChainWeight;
  }
  
  public Object getKey()
  {
    return this.key;
  }
  
  public float getPivotX()
  {
    return this.mPivotX;
  }
  
  public float getPivotY()
  {
    return this.mPivotY;
  }
  
  public float getRotationX()
  {
    return this.mRotationX;
  }
  
  public float getRotationY()
  {
    return this.mRotationY;
  }
  
  public float getRotationZ()
  {
    return this.mRotationZ;
  }
  
  public float getScaleX()
  {
    return this.mScaleX;
  }
  
  public float getScaleY()
  {
    return this.mScaleY;
  }
  
  public String getTag()
  {
    return this.mTag;
  }
  
  public float getTranslationX()
  {
    return this.mTranslationX;
  }
  
  public float getTranslationY()
  {
    return this.mTranslationY;
  }
  
  public float getTranslationZ()
  {
    return this.mTranslationZ;
  }
  
  public int getVerticalChainStyle(int paramInt)
  {
    return this.mVerticalChainStyle;
  }
  
  public float getVerticalChainWeight()
  {
    return this.mVerticalChainWeight;
  }
  
  public Object getView()
  {
    return this.mView;
  }
  
  public Dimension getWidth()
  {
    return this.mHorizontalDimension;
  }
  
  public ConstraintReference height(Dimension paramDimension)
  {
    return setHeight(paramDimension);
  }
  
  public ConstraintReference horizontalBias(float paramFloat)
  {
    this.mHorizontalBias = paramFloat;
    return this;
  }
  
  public ConstraintReference left()
  {
    if (this.mLeftToLeft != null) {
      this.mLast = State.Constraint.LEFT_TO_LEFT;
    } else {
      this.mLast = State.Constraint.LEFT_TO_RIGHT;
    }
    return this;
  }
  
  public ConstraintReference leftToLeft(Object paramObject)
  {
    this.mLast = State.Constraint.LEFT_TO_LEFT;
    this.mLeftToLeft = paramObject;
    return this;
  }
  
  public ConstraintReference leftToRight(Object paramObject)
  {
    this.mLast = State.Constraint.LEFT_TO_RIGHT;
    this.mLeftToRight = paramObject;
    return this;
  }
  
  public ConstraintReference margin(int paramInt)
  {
    if (this.mLast != null)
    {
      switch (1.$SwitchMap$androidx$constraintlayout$core$state$State$Constraint[this.mLast.ordinal()])
      {
      default: 
        break;
      case 16: 
        this.mCircularDistance = paramInt;
        break;
      case 13: 
      case 14: 
      case 15: 
        this.mMarginBaseline = paramInt;
        break;
      case 11: 
      case 12: 
        this.mMarginBottom = paramInt;
        break;
      case 9: 
      case 10: 
        this.mMarginTop = paramInt;
        break;
      case 7: 
      case 8: 
        this.mMarginEnd = paramInt;
        break;
      case 5: 
      case 6: 
        this.mMarginStart = paramInt;
        break;
      case 3: 
      case 4: 
        this.mMarginRight = paramInt;
        break;
      case 1: 
      case 2: 
        this.mMarginLeft = paramInt;
        break;
      }
    }
    else
    {
      this.mMarginLeft = paramInt;
      this.mMarginRight = paramInt;
      this.mMarginStart = paramInt;
      this.mMarginEnd = paramInt;
      this.mMarginTop = paramInt;
      this.mMarginBottom = paramInt;
    }
    return this;
  }
  
  public ConstraintReference margin(Object paramObject)
  {
    return margin(this.mState.convertDimension(paramObject));
  }
  
  public ConstraintReference marginGone(int paramInt)
  {
    if (this.mLast != null)
    {
      switch (1.$SwitchMap$androidx$constraintlayout$core$state$State$Constraint[this.mLast.ordinal()])
      {
      default: 
        break;
      case 13: 
      case 14: 
      case 15: 
        this.mMarginBaselineGone = paramInt;
        break;
      case 11: 
      case 12: 
        this.mMarginBottomGone = paramInt;
        break;
      case 9: 
      case 10: 
        this.mMarginTopGone = paramInt;
        break;
      case 7: 
      case 8: 
        this.mMarginEndGone = paramInt;
        break;
      case 5: 
      case 6: 
        this.mMarginStartGone = paramInt;
        break;
      case 3: 
      case 4: 
        this.mMarginRightGone = paramInt;
        break;
      case 1: 
      case 2: 
        this.mMarginLeftGone = paramInt;
        break;
      }
    }
    else
    {
      this.mMarginLeftGone = paramInt;
      this.mMarginRightGone = paramInt;
      this.mMarginStartGone = paramInt;
      this.mMarginEndGone = paramInt;
      this.mMarginTopGone = paramInt;
      this.mMarginBottomGone = paramInt;
    }
    return this;
  }
  
  public ConstraintReference marginGone(Object paramObject)
  {
    return marginGone(this.mState.convertDimension(paramObject));
  }
  
  public ConstraintReference pivotX(float paramFloat)
  {
    this.mPivotX = paramFloat;
    return this;
  }
  
  public ConstraintReference pivotY(float paramFloat)
  {
    this.mPivotY = paramFloat;
    return this;
  }
  
  public ConstraintReference right()
  {
    if (this.mRightToLeft != null) {
      this.mLast = State.Constraint.RIGHT_TO_LEFT;
    } else {
      this.mLast = State.Constraint.RIGHT_TO_RIGHT;
    }
    return this;
  }
  
  public ConstraintReference rightToLeft(Object paramObject)
  {
    this.mLast = State.Constraint.RIGHT_TO_LEFT;
    this.mRightToLeft = paramObject;
    return this;
  }
  
  public ConstraintReference rightToRight(Object paramObject)
  {
    this.mLast = State.Constraint.RIGHT_TO_RIGHT;
    this.mRightToRight = paramObject;
    return this;
  }
  
  public ConstraintReference rotationX(float paramFloat)
  {
    this.mRotationX = paramFloat;
    return this;
  }
  
  public ConstraintReference rotationY(float paramFloat)
  {
    this.mRotationY = paramFloat;
    return this;
  }
  
  public ConstraintReference rotationZ(float paramFloat)
  {
    this.mRotationZ = paramFloat;
    return this;
  }
  
  public ConstraintReference scaleX(float paramFloat)
  {
    this.mScaleX = paramFloat;
    return this;
  }
  
  public ConstraintReference scaleY(float paramFloat)
  {
    this.mScaleY = paramFloat;
    return this;
  }
  
  public void setConstraintWidget(ConstraintWidget paramConstraintWidget)
  {
    if (paramConstraintWidget == null) {
      return;
    }
    this.mConstraintWidget = paramConstraintWidget;
    paramConstraintWidget.setCompanionWidget(this.mView);
  }
  
  public void setFacade(Facade paramFacade)
  {
    this.mFacade = paramFacade;
    if (paramFacade != null) {
      setConstraintWidget(paramFacade.getConstraintWidget());
    }
  }
  
  public ConstraintReference setHeight(Dimension paramDimension)
  {
    this.mVerticalDimension = paramDimension;
    return this;
  }
  
  public void setHorizontalChainStyle(int paramInt)
  {
    this.mHorizontalChainStyle = paramInt;
  }
  
  public void setHorizontalChainWeight(float paramFloat)
  {
    this.mHorizontalChainWeight = paramFloat;
  }
  
  public void setKey(Object paramObject)
  {
    this.key = paramObject;
  }
  
  public void setTag(String paramString)
  {
    this.mTag = paramString;
  }
  
  public void setVerticalChainStyle(int paramInt)
  {
    this.mVerticalChainStyle = paramInt;
  }
  
  public void setVerticalChainWeight(float paramFloat)
  {
    this.mVerticalChainWeight = paramFloat;
  }
  
  public void setView(Object paramObject)
  {
    this.mView = paramObject;
    ConstraintWidget localConstraintWidget = this.mConstraintWidget;
    if (localConstraintWidget != null) {
      localConstraintWidget.setCompanionWidget(paramObject);
    }
  }
  
  public ConstraintReference setWidth(Dimension paramDimension)
  {
    this.mHorizontalDimension = paramDimension;
    return this;
  }
  
  public ConstraintReference start()
  {
    if (this.mStartToStart != null) {
      this.mLast = State.Constraint.START_TO_START;
    } else {
      this.mLast = State.Constraint.START_TO_END;
    }
    return this;
  }
  
  public ConstraintReference startToEnd(Object paramObject)
  {
    this.mLast = State.Constraint.START_TO_END;
    this.mStartToEnd = paramObject;
    return this;
  }
  
  public ConstraintReference startToStart(Object paramObject)
  {
    this.mLast = State.Constraint.START_TO_START;
    this.mStartToStart = paramObject;
    return this;
  }
  
  public ConstraintReference top()
  {
    if (this.mTopToTop != null) {
      this.mLast = State.Constraint.TOP_TO_TOP;
    } else {
      this.mLast = State.Constraint.TOP_TO_BOTTOM;
    }
    return this;
  }
  
  public ConstraintReference topToBottom(Object paramObject)
  {
    this.mLast = State.Constraint.TOP_TO_BOTTOM;
    this.mTopToBottom = paramObject;
    return this;
  }
  
  public ConstraintReference topToTop(Object paramObject)
  {
    this.mLast = State.Constraint.TOP_TO_TOP;
    this.mTopToTop = paramObject;
    return this;
  }
  
  public ConstraintReference translationX(float paramFloat)
  {
    this.mTranslationX = paramFloat;
    return this;
  }
  
  public ConstraintReference translationY(float paramFloat)
  {
    this.mTranslationY = paramFloat;
    return this;
  }
  
  public ConstraintReference translationZ(float paramFloat)
  {
    this.mTranslationZ = paramFloat;
    return this;
  }
  
  public void validate()
    throws ConstraintReference.IncorrectConstraintException
  {
    ArrayList localArrayList = new ArrayList();
    if ((this.mLeftToLeft != null) && (this.mLeftToRight != null)) {
      localArrayList.add("LeftToLeft and LeftToRight both defined");
    }
    if ((this.mRightToLeft != null) && (this.mRightToRight != null)) {
      localArrayList.add("RightToLeft and RightToRight both defined");
    }
    if ((this.mStartToStart != null) && (this.mStartToEnd != null)) {
      localArrayList.add("StartToStart and StartToEnd both defined");
    }
    if ((this.mEndToStart != null) && (this.mEndToEnd != null)) {
      localArrayList.add("EndToStart and EndToEnd both defined");
    }
    if (((this.mLeftToLeft != null) || (this.mLeftToRight != null) || (this.mRightToLeft != null) || (this.mRightToRight != null)) && ((this.mStartToStart != null) || (this.mStartToEnd != null) || (this.mEndToStart != null) || (this.mEndToEnd != null))) {
      localArrayList.add("Both left/right and start/end constraints defined");
    }
    if (localArrayList.size() <= 0) {
      return;
    }
    throw new IncorrectConstraintException(localArrayList);
  }
  
  public ConstraintReference verticalBias(float paramFloat)
  {
    this.mVerticalBias = paramFloat;
    return this;
  }
  
  public ConstraintReference visibility(int paramInt)
  {
    this.mVisibility = paramInt;
    return this;
  }
  
  public ConstraintReference width(Dimension paramDimension)
  {
    return setWidth(paramDimension);
  }
  
  public static abstract interface ConstraintReferenceFactory
  {
    public abstract ConstraintReference create(State paramState);
  }
  
  static class IncorrectConstraintException
    extends Exception
  {
    private final ArrayList<String> mErrors;
    
    public IncorrectConstraintException(ArrayList<String> paramArrayList)
    {
      this.mErrors = paramArrayList;
    }
    
    public ArrayList<String> getErrors()
    {
      return this.mErrors;
    }
    
    public String toString()
    {
      return "IncorrectConstraintException: " + this.mErrors.toString();
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/constraintlayout/core/state/ConstraintReference.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */