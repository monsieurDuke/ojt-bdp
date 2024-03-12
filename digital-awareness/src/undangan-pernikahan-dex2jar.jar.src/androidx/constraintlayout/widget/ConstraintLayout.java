package androidx.constraintlayout.widget;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.res.Resources;
import android.content.res.Resources.NotFoundException;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Build.VERSION;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.util.SparseIntArray;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewGroup.MarginLayoutParams;
import androidx.constraintlayout.core.Metrics;
import androidx.constraintlayout.core.widgets.ConstraintAnchor;
import androidx.constraintlayout.core.widgets.ConstraintAnchor.Type;
import androidx.constraintlayout.core.widgets.ConstraintWidget;
import androidx.constraintlayout.core.widgets.ConstraintWidget.DimensionBehaviour;
import androidx.constraintlayout.core.widgets.ConstraintWidgetContainer;
import androidx.constraintlayout.core.widgets.Optimizer;
import androidx.constraintlayout.core.widgets.analyzer.BasicMeasure.Measure;
import androidx.constraintlayout.core.widgets.analyzer.BasicMeasure.Measurer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class ConstraintLayout
  extends ViewGroup
{
  private static final boolean DEBUG = false;
  private static final boolean DEBUG_DRAW_CONSTRAINTS = false;
  public static final int DESIGN_INFO_ID = 0;
  private static final boolean MEASURE = false;
  private static final boolean OPTIMIZE_HEIGHT_CHANGE = false;
  private static final String TAG = "ConstraintLayout";
  private static final boolean USE_CONSTRAINTS_HELPER = true;
  public static final String VERSION = "ConstraintLayout-2.1.4";
  private static SharedValues sSharedValues = null;
  SparseArray<View> mChildrenByIds = new SparseArray();
  private ArrayList<ConstraintHelper> mConstraintHelpers = new ArrayList(4);
  protected ConstraintLayoutStates mConstraintLayoutSpec = null;
  private ConstraintSet mConstraintSet = null;
  private int mConstraintSetId = -1;
  private ConstraintsChangedListener mConstraintsChangedListener;
  private HashMap<String, Integer> mDesignIds = new HashMap();
  protected boolean mDirtyHierarchy = true;
  private int mLastMeasureHeight = -1;
  int mLastMeasureHeightMode = 0;
  int mLastMeasureHeightSize = -1;
  private int mLastMeasureWidth = -1;
  int mLastMeasureWidthMode = 0;
  int mLastMeasureWidthSize = -1;
  protected ConstraintWidgetContainer mLayoutWidget = new ConstraintWidgetContainer();
  private int mMaxHeight = Integer.MAX_VALUE;
  private int mMaxWidth = Integer.MAX_VALUE;
  Measurer mMeasurer = new Measurer(this);
  private Metrics mMetrics;
  private int mMinHeight = 0;
  private int mMinWidth = 0;
  private int mOnMeasureHeightMeasureSpec = 0;
  private int mOnMeasureWidthMeasureSpec = 0;
  private int mOptimizationLevel = 257;
  private SparseArray<ConstraintWidget> mTempMapIdToWidget = new SparseArray();
  
  public ConstraintLayout(Context paramContext)
  {
    super(paramContext);
    init(null, 0, 0);
  }
  
  public ConstraintLayout(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    init(paramAttributeSet, 0, 0);
  }
  
  public ConstraintLayout(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
    init(paramAttributeSet, paramInt, 0);
  }
  
  public ConstraintLayout(Context paramContext, AttributeSet paramAttributeSet, int paramInt1, int paramInt2)
  {
    super(paramContext, paramAttributeSet, paramInt1, paramInt2);
    init(paramAttributeSet, paramInt1, paramInt2);
  }
  
  private int getPaddingWidth()
  {
    int j = Math.max(0, getPaddingLeft()) + Math.max(0, getPaddingRight());
    int i = 0;
    if (Build.VERSION.SDK_INT >= 17) {
      i = Math.max(0, getPaddingStart()) + Math.max(0, getPaddingEnd());
    }
    if (i > 0) {
      j = i;
    }
    return j;
  }
  
  public static SharedValues getSharedValues()
  {
    if (sSharedValues == null) {
      sSharedValues = new SharedValues();
    }
    return sSharedValues;
  }
  
  private final ConstraintWidget getTargetWidget(int paramInt)
  {
    if (paramInt == 0) {
      return this.mLayoutWidget;
    }
    View localView = (View)this.mChildrenByIds.get(paramInt);
    Object localObject = localView;
    if (localView == null)
    {
      localView = findViewById(paramInt);
      localObject = localView;
      if (localView != null)
      {
        localObject = localView;
        if (localView != this)
        {
          localObject = localView;
          if (localView.getParent() == this)
          {
            onViewAdded(localView);
            localObject = localView;
          }
        }
      }
    }
    if (localObject == this) {
      return this.mLayoutWidget;
    }
    if (localObject == null) {
      localObject = null;
    } else {
      localObject = ((LayoutParams)((View)localObject).getLayoutParams()).widget;
    }
    return (ConstraintWidget)localObject;
  }
  
  private void init(AttributeSet paramAttributeSet, int paramInt1, int paramInt2)
  {
    this.mLayoutWidget.setCompanionWidget(this);
    this.mLayoutWidget.setMeasurer(this.mMeasurer);
    this.mChildrenByIds.put(getId(), this);
    this.mConstraintSet = null;
    if (paramAttributeSet != null)
    {
      paramAttributeSet = getContext().obtainStyledAttributes(paramAttributeSet, R.styleable.ConstraintLayout_Layout, paramInt1, paramInt2);
      paramInt2 = paramAttributeSet.getIndexCount();
      for (paramInt1 = 0; paramInt1 < paramInt2; paramInt1++)
      {
        int i = paramAttributeSet.getIndex(paramInt1);
        if (i == R.styleable.ConstraintLayout_Layout_android_minWidth)
        {
          this.mMinWidth = paramAttributeSet.getDimensionPixelOffset(i, this.mMinWidth);
        }
        else if (i == R.styleable.ConstraintLayout_Layout_android_minHeight)
        {
          this.mMinHeight = paramAttributeSet.getDimensionPixelOffset(i, this.mMinHeight);
        }
        else if (i == R.styleable.ConstraintLayout_Layout_android_maxWidth)
        {
          this.mMaxWidth = paramAttributeSet.getDimensionPixelOffset(i, this.mMaxWidth);
        }
        else if (i == R.styleable.ConstraintLayout_Layout_android_maxHeight)
        {
          this.mMaxHeight = paramAttributeSet.getDimensionPixelOffset(i, this.mMaxHeight);
        }
        else if (i == R.styleable.ConstraintLayout_Layout_layout_optimizationLevel)
        {
          this.mOptimizationLevel = paramAttributeSet.getInt(i, this.mOptimizationLevel);
        }
        else if (i == R.styleable.ConstraintLayout_Layout_layoutDescription)
        {
          i = paramAttributeSet.getResourceId(i, 0);
          if (i != 0) {
            try
            {
              parseLayoutDescription(i);
            }
            catch (Resources.NotFoundException localNotFoundException1)
            {
              this.mConstraintLayoutSpec = null;
            }
          }
        }
        else if (i == R.styleable.ConstraintLayout_Layout_constraintSet)
        {
          i = paramAttributeSet.getResourceId(i, 0);
          try
          {
            ConstraintSet localConstraintSet = new androidx/constraintlayout/widget/ConstraintSet;
            localConstraintSet.<init>();
            this.mConstraintSet = localConstraintSet;
            localConstraintSet.load(getContext(), i);
          }
          catch (Resources.NotFoundException localNotFoundException2)
          {
            this.mConstraintSet = null;
          }
          this.mConstraintSetId = i;
        }
      }
      paramAttributeSet.recycle();
    }
    this.mLayoutWidget.setOptimizationLevel(this.mOptimizationLevel);
  }
  
  private void markHierarchyDirty()
  {
    this.mDirtyHierarchy = true;
    this.mLastMeasureWidth = -1;
    this.mLastMeasureHeight = -1;
    this.mLastMeasureWidthSize = -1;
    this.mLastMeasureHeightSize = -1;
    this.mLastMeasureWidthMode = 0;
    this.mLastMeasureHeightMode = 0;
  }
  
  private void setChildrenConstraints()
  {
    boolean bool = isInEditMode();
    int j = getChildCount();
    Object localObject1;
    for (int i = 0; i < j; i++)
    {
      localObject1 = getViewWidget(getChildAt(i));
      if (localObject1 != null) {
        ((ConstraintWidget)localObject1).reset();
      }
    }
    View localView;
    Object localObject3;
    if (bool) {
      for (i = 0; i < j; i++)
      {
        localView = getChildAt(i);
        try
        {
          localObject3 = getResources().getResourceName(localView.getId());
          setDesignInformation(0, localObject3, Integer.valueOf(localView.getId()));
          k = ((String)localObject3).indexOf('/');
          localObject1 = localObject3;
          if (k != -1) {
            localObject1 = ((String)localObject3).substring(k + 1);
          }
          getTargetWidget(localView.getId()).setDebugName((String)localObject1);
        }
        catch (Resources.NotFoundException localNotFoundException) {}
      }
    }
    if (this.mConstraintSetId != -1) {
      for (i = 0; i < j; i++)
      {
        localObject2 = getChildAt(i);
        if ((((View)localObject2).getId() == this.mConstraintSetId) && ((localObject2 instanceof Constraints))) {
          this.mConstraintSet = ((Constraints)localObject2).getConstraintSet();
        }
      }
    }
    Object localObject2 = this.mConstraintSet;
    if (localObject2 != null) {
      ((ConstraintSet)localObject2).applyToInternal(this, true);
    }
    this.mLayoutWidget.removeAllChildren();
    int k = this.mConstraintHelpers.size();
    if (k > 0) {
      for (i = 0; i < k; i++) {
        ((ConstraintHelper)this.mConstraintHelpers.get(i)).updatePreLayout(this);
      }
    }
    for (i = 0; i < j; i++)
    {
      localObject2 = getChildAt(i);
      if ((localObject2 instanceof Placeholder)) {
        ((Placeholder)localObject2).updatePreLayout(this);
      }
    }
    this.mTempMapIdToWidget.clear();
    this.mTempMapIdToWidget.put(0, this.mLayoutWidget);
    this.mTempMapIdToWidget.put(getId(), this.mLayoutWidget);
    for (i = 0; i < j; i++)
    {
      localObject3 = getChildAt(i);
      localObject2 = getViewWidget((View)localObject3);
      this.mTempMapIdToWidget.put(((View)localObject3).getId(), localObject2);
    }
    for (i = 0; i < j; i++)
    {
      localView = getChildAt(i);
      localObject3 = getViewWidget(localView);
      if (localObject3 != null)
      {
        localObject2 = (LayoutParams)localView.getLayoutParams();
        this.mLayoutWidget.add((ConstraintWidget)localObject3);
        applyConstraintsFromLayoutParams(bool, localView, (ConstraintWidget)localObject3, (LayoutParams)localObject2, this.mTempMapIdToWidget);
      }
    }
  }
  
  private void setWidgetBaseline(ConstraintWidget paramConstraintWidget, LayoutParams paramLayoutParams, SparseArray<ConstraintWidget> paramSparseArray, int paramInt, ConstraintAnchor.Type paramType)
  {
    Object localObject = (View)this.mChildrenByIds.get(paramInt);
    paramSparseArray = (ConstraintWidget)paramSparseArray.get(paramInt);
    if ((paramSparseArray != null) && (localObject != null) && ((((View)localObject).getLayoutParams() instanceof LayoutParams)))
    {
      paramLayoutParams.needsBaseline = true;
      if (paramType == ConstraintAnchor.Type.BASELINE)
      {
        localObject = (LayoutParams)((View)localObject).getLayoutParams();
        ((LayoutParams)localObject).needsBaseline = true;
        ((LayoutParams)localObject).widget.setHasBaseline(true);
      }
      paramConstraintWidget.getAnchor(ConstraintAnchor.Type.BASELINE).connect(paramSparseArray.getAnchor(paramType), paramLayoutParams.baselineMargin, paramLayoutParams.goneBaselineMargin, true);
      paramConstraintWidget.setHasBaseline(true);
      paramConstraintWidget.getAnchor(ConstraintAnchor.Type.TOP).reset();
      paramConstraintWidget.getAnchor(ConstraintAnchor.Type.BOTTOM).reset();
    }
  }
  
  private boolean updateHierarchy()
  {
    int j = getChildCount();
    boolean bool2 = false;
    boolean bool1;
    for (int i = 0;; i++)
    {
      bool1 = bool2;
      if (i >= j) {
        break;
      }
      if (getChildAt(i).isLayoutRequested())
      {
        bool1 = true;
        break;
      }
    }
    if (bool1) {
      setChildrenConstraints();
    }
    return bool1;
  }
  
  protected void applyConstraintsFromLayoutParams(boolean paramBoolean, View paramView, ConstraintWidget paramConstraintWidget, LayoutParams paramLayoutParams, SparseArray<ConstraintWidget> paramSparseArray)
  {
    paramLayoutParams.validate();
    paramLayoutParams.helped = false;
    paramConstraintWidget.setVisibility(paramView.getVisibility());
    if (paramLayoutParams.isInPlaceholder)
    {
      paramConstraintWidget.setInPlaceholder(true);
      paramConstraintWidget.setVisibility(8);
    }
    paramConstraintWidget.setCompanionWidget(paramView);
    if ((paramView instanceof ConstraintHelper)) {
      ((ConstraintHelper)paramView).resolveRtl(paramConstraintWidget, this.mLayoutWidget.isRtl());
    }
    int i;
    int j;
    float f;
    if (paramLayoutParams.isGuideline)
    {
      paramView = (androidx.constraintlayout.core.widgets.Guideline)paramConstraintWidget;
      i = paramLayoutParams.resolvedGuideBegin;
      j = paramLayoutParams.resolvedGuideEnd;
      f = paramLayoutParams.resolvedGuidePercent;
      if (Build.VERSION.SDK_INT < 17)
      {
        i = paramLayoutParams.guideBegin;
        j = paramLayoutParams.guideEnd;
        f = paramLayoutParams.guidePercent;
      }
      if (f != -1.0F) {
        paramView.setGuidePercent(f);
      } else if (i != -1) {
        paramView.setGuideBegin(i);
      } else if (j != -1) {
        paramView.setGuideEnd(j);
      }
    }
    else
    {
      j = paramLayoutParams.resolvedLeftToLeft;
      int k = paramLayoutParams.resolvedLeftToRight;
      int m = paramLayoutParams.resolvedRightToLeft;
      i = paramLayoutParams.resolvedRightToRight;
      int n = paramLayoutParams.resolveGoneLeftMargin;
      int i1 = paramLayoutParams.resolveGoneRightMargin;
      f = paramLayoutParams.resolvedHorizontalBias;
      if (Build.VERSION.SDK_INT < 17)
      {
        j = paramLayoutParams.leftToLeft;
        i = paramLayoutParams.leftToRight;
        int i2 = paramLayoutParams.rightToLeft;
        n = paramLayoutParams.rightToRight;
        m = paramLayoutParams.goneLeftMargin;
        k = paramLayoutParams.goneRightMargin;
        f = paramLayoutParams.horizontalBias;
        if ((j == -1) && (i == -1)) {
          if (paramLayoutParams.startToStart != -1) {
            j = paramLayoutParams.startToStart;
          } else if (paramLayoutParams.startToEnd != -1) {
            i = paramLayoutParams.startToEnd;
          }
        }
        if ((i2 == -1) && (n == -1))
        {
          if (paramLayoutParams.endToStart != -1)
          {
            i3 = paramLayoutParams.endToStart;
            i1 = m;
            i2 = k;
            k = i;
            i = n;
            m = i3;
            n = i1;
            i1 = i2;
            break label468;
          }
          if (paramLayoutParams.endToEnd != -1)
          {
            i3 = paramLayoutParams.endToEnd;
            n = m;
            i1 = k;
            m = i3;
            k = i;
            i = m;
            m = i2;
            break label468;
          }
        }
        int i3 = i2;
        i2 = m;
        i1 = k;
        k = i;
        i = n;
        m = i3;
        n = i2;
      }
      label468:
      if (paramLayoutParams.circleConstraint != -1)
      {
        paramView = (ConstraintWidget)paramSparseArray.get(paramLayoutParams.circleConstraint);
        if (paramView != null) {
          paramConstraintWidget.connectCircularConstraint(paramView, paramLayoutParams.circleAngle, paramLayoutParams.circleRadius);
        }
      }
      else
      {
        if (j != -1)
        {
          paramView = (ConstraintWidget)paramSparseArray.get(j);
          if (paramView != null) {
            paramConstraintWidget.immediateConnect(ConstraintAnchor.Type.LEFT, paramView, ConstraintAnchor.Type.LEFT, paramLayoutParams.leftMargin, n);
          }
        }
        else if (k != -1)
        {
          paramView = (ConstraintWidget)paramSparseArray.get(k);
          if (paramView != null) {
            paramConstraintWidget.immediateConnect(ConstraintAnchor.Type.LEFT, paramView, ConstraintAnchor.Type.RIGHT, paramLayoutParams.leftMargin, n);
          }
        }
        if (m != -1)
        {
          paramView = (ConstraintWidget)paramSparseArray.get(m);
          if (paramView != null) {
            paramConstraintWidget.immediateConnect(ConstraintAnchor.Type.RIGHT, paramView, ConstraintAnchor.Type.LEFT, paramLayoutParams.rightMargin, i1);
          }
        }
        else if (i != -1)
        {
          paramView = (ConstraintWidget)paramSparseArray.get(i);
          if (paramView != null) {
            paramConstraintWidget.immediateConnect(ConstraintAnchor.Type.RIGHT, paramView, ConstraintAnchor.Type.RIGHT, paramLayoutParams.rightMargin, i1);
          } else {}
        }
        if (paramLayoutParams.topToTop != -1)
        {
          paramView = (ConstraintWidget)paramSparseArray.get(paramLayoutParams.topToTop);
          if (paramView != null) {
            paramConstraintWidget.immediateConnect(ConstraintAnchor.Type.TOP, paramView, ConstraintAnchor.Type.TOP, paramLayoutParams.topMargin, paramLayoutParams.goneTopMargin);
          }
        }
        else if (paramLayoutParams.topToBottom != -1)
        {
          paramView = (ConstraintWidget)paramSparseArray.get(paramLayoutParams.topToBottom);
          if (paramView != null) {
            paramConstraintWidget.immediateConnect(ConstraintAnchor.Type.TOP, paramView, ConstraintAnchor.Type.BOTTOM, paramLayoutParams.topMargin, paramLayoutParams.goneTopMargin);
          }
        }
        if (paramLayoutParams.bottomToTop != -1)
        {
          paramView = (ConstraintWidget)paramSparseArray.get(paramLayoutParams.bottomToTop);
          if (paramView != null) {
            paramConstraintWidget.immediateConnect(ConstraintAnchor.Type.BOTTOM, paramView, ConstraintAnchor.Type.TOP, paramLayoutParams.bottomMargin, paramLayoutParams.goneBottomMargin);
          }
        }
        else if (paramLayoutParams.bottomToBottom != -1)
        {
          paramView = (ConstraintWidget)paramSparseArray.get(paramLayoutParams.bottomToBottom);
          if (paramView != null) {
            paramConstraintWidget.immediateConnect(ConstraintAnchor.Type.BOTTOM, paramView, ConstraintAnchor.Type.BOTTOM, paramLayoutParams.bottomMargin, paramLayoutParams.goneBottomMargin);
          }
        }
        if (paramLayoutParams.baselineToBaseline != -1) {
          setWidgetBaseline(paramConstraintWidget, paramLayoutParams, paramSparseArray, paramLayoutParams.baselineToBaseline, ConstraintAnchor.Type.BASELINE);
        } else if (paramLayoutParams.baselineToTop != -1) {
          setWidgetBaseline(paramConstraintWidget, paramLayoutParams, paramSparseArray, paramLayoutParams.baselineToTop, ConstraintAnchor.Type.TOP);
        } else if (paramLayoutParams.baselineToBottom != -1) {
          setWidgetBaseline(paramConstraintWidget, paramLayoutParams, paramSparseArray, paramLayoutParams.baselineToBottom, ConstraintAnchor.Type.BOTTOM);
        }
        if (f >= 0.0F) {
          paramConstraintWidget.setHorizontalBiasPercent(f);
        }
        if (paramLayoutParams.verticalBias >= 0.0F) {
          paramConstraintWidget.setVerticalBiasPercent(paramLayoutParams.verticalBias);
        }
      }
      if ((paramBoolean) && ((paramLayoutParams.editorAbsoluteX != -1) || (paramLayoutParams.editorAbsoluteY != -1))) {
        paramConstraintWidget.setOrigin(paramLayoutParams.editorAbsoluteX, paramLayoutParams.editorAbsoluteY);
      }
      if (!paramLayoutParams.horizontalDimensionFixed)
      {
        if (paramLayoutParams.width == -1)
        {
          if (paramLayoutParams.constrainedWidth) {
            paramConstraintWidget.setHorizontalDimensionBehaviour(ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT);
          } else {
            paramConstraintWidget.setHorizontalDimensionBehaviour(ConstraintWidget.DimensionBehaviour.MATCH_PARENT);
          }
          paramConstraintWidget.getAnchor(ConstraintAnchor.Type.LEFT).mMargin = paramLayoutParams.leftMargin;
          paramConstraintWidget.getAnchor(ConstraintAnchor.Type.RIGHT).mMargin = paramLayoutParams.rightMargin;
        }
        else
        {
          paramConstraintWidget.setHorizontalDimensionBehaviour(ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT);
          paramConstraintWidget.setWidth(0);
        }
      }
      else
      {
        paramConstraintWidget.setHorizontalDimensionBehaviour(ConstraintWidget.DimensionBehaviour.FIXED);
        paramConstraintWidget.setWidth(paramLayoutParams.width);
        if (paramLayoutParams.width == -2) {
          paramConstraintWidget.setHorizontalDimensionBehaviour(ConstraintWidget.DimensionBehaviour.WRAP_CONTENT);
        }
      }
      if (!paramLayoutParams.verticalDimensionFixed)
      {
        if (paramLayoutParams.height == -1)
        {
          if (paramLayoutParams.constrainedHeight) {
            paramConstraintWidget.setVerticalDimensionBehaviour(ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT);
          } else {
            paramConstraintWidget.setVerticalDimensionBehaviour(ConstraintWidget.DimensionBehaviour.MATCH_PARENT);
          }
          paramConstraintWidget.getAnchor(ConstraintAnchor.Type.TOP).mMargin = paramLayoutParams.topMargin;
          paramConstraintWidget.getAnchor(ConstraintAnchor.Type.BOTTOM).mMargin = paramLayoutParams.bottomMargin;
        }
        else
        {
          paramConstraintWidget.setVerticalDimensionBehaviour(ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT);
          paramConstraintWidget.setHeight(0);
        }
      }
      else
      {
        paramConstraintWidget.setVerticalDimensionBehaviour(ConstraintWidget.DimensionBehaviour.FIXED);
        paramConstraintWidget.setHeight(paramLayoutParams.height);
        if (paramLayoutParams.height == -2) {
          paramConstraintWidget.setVerticalDimensionBehaviour(ConstraintWidget.DimensionBehaviour.WRAP_CONTENT);
        }
      }
      paramConstraintWidget.setDimensionRatio(paramLayoutParams.dimensionRatio);
      paramConstraintWidget.setHorizontalWeight(paramLayoutParams.horizontalWeight);
      paramConstraintWidget.setVerticalWeight(paramLayoutParams.verticalWeight);
      paramConstraintWidget.setHorizontalChainStyle(paramLayoutParams.horizontalChainStyle);
      paramConstraintWidget.setVerticalChainStyle(paramLayoutParams.verticalChainStyle);
      paramConstraintWidget.setWrapBehaviorInParent(paramLayoutParams.wrapBehaviorInParent);
      paramConstraintWidget.setHorizontalMatchStyle(paramLayoutParams.matchConstraintDefaultWidth, paramLayoutParams.matchConstraintMinWidth, paramLayoutParams.matchConstraintMaxWidth, paramLayoutParams.matchConstraintPercentWidth);
      paramConstraintWidget.setVerticalMatchStyle(paramLayoutParams.matchConstraintDefaultHeight, paramLayoutParams.matchConstraintMinHeight, paramLayoutParams.matchConstraintMaxHeight, paramLayoutParams.matchConstraintPercentHeight);
    }
  }
  
  protected boolean checkLayoutParams(ViewGroup.LayoutParams paramLayoutParams)
  {
    return paramLayoutParams instanceof LayoutParams;
  }
  
  protected void dispatchDraw(Canvas paramCanvas)
  {
    Object localObject = this.mConstraintHelpers;
    int j;
    int i;
    if (localObject != null)
    {
      j = ((ArrayList)localObject).size();
      if (j > 0) {
        for (i = 0; i < j; i++) {
          ((ConstraintHelper)this.mConstraintHelpers.get(i)).updatePreDraw(this);
        }
      }
    }
    super.dispatchDraw(paramCanvas);
    if (isInEditMode())
    {
      float f3 = getWidth();
      float f2 = getHeight();
      float f1 = 1080.0F;
      j = getChildCount();
      for (i = 0; i < j; i++)
      {
        localObject = getChildAt(i);
        if (((View)localObject).getVisibility() != 8)
        {
          localObject = ((View)localObject).getTag();
          if ((localObject != null) && ((localObject instanceof String)))
          {
            localObject = ((String)localObject).split(",");
            if (localObject.length == 4)
            {
              int m = Integer.parseInt(localObject[0]);
              int n = Integer.parseInt(localObject[1]);
              int i1 = Integer.parseInt(localObject[2]);
              int k = Integer.parseInt(localObject[3]);
              m = (int)(m / f1 * f3);
              n = (int)(n / 1920.0F * f2);
              i1 = (int)(i1 / f1 * f3);
              k = (int)(k / 1920.0F * f2);
              localObject = new Paint();
              ((Paint)localObject).setColor(-65536);
              paramCanvas.drawLine(m, n, m + i1, n, (Paint)localObject);
              paramCanvas.drawLine(m + i1, n, m + i1, n + k, (Paint)localObject);
              paramCanvas.drawLine(m + i1, n + k, m, n + k, (Paint)localObject);
              paramCanvas.drawLine(m, n + k, m, n, (Paint)localObject);
              ((Paint)localObject).setColor(-16711936);
              paramCanvas.drawLine(m, n, m + i1, n + k, (Paint)localObject);
              paramCanvas.drawLine(m, n + k, m + i1, n, (Paint)localObject);
            }
            else {}
          }
        }
      }
    }
  }
  
  public void fillMetrics(Metrics paramMetrics)
  {
    this.mMetrics = paramMetrics;
    this.mLayoutWidget.fillMetrics(paramMetrics);
  }
  
  public void forceLayout()
  {
    markHierarchyDirty();
    super.forceLayout();
  }
  
  protected LayoutParams generateDefaultLayoutParams()
  {
    return new LayoutParams(-2, -2);
  }
  
  protected ViewGroup.LayoutParams generateLayoutParams(ViewGroup.LayoutParams paramLayoutParams)
  {
    return new LayoutParams(paramLayoutParams);
  }
  
  public LayoutParams generateLayoutParams(AttributeSet paramAttributeSet)
  {
    return new LayoutParams(getContext(), paramAttributeSet);
  }
  
  public Object getDesignInformation(int paramInt, Object paramObject)
  {
    if ((paramInt == 0) && ((paramObject instanceof String)))
    {
      String str = (String)paramObject;
      paramObject = this.mDesignIds;
      if ((paramObject != null) && (((HashMap)paramObject).containsKey(str))) {
        return this.mDesignIds.get(str);
      }
    }
    return null;
  }
  
  public int getMaxHeight()
  {
    return this.mMaxHeight;
  }
  
  public int getMaxWidth()
  {
    return this.mMaxWidth;
  }
  
  public int getMinHeight()
  {
    return this.mMinHeight;
  }
  
  public int getMinWidth()
  {
    return this.mMinWidth;
  }
  
  public int getOptimizationLevel()
  {
    return this.mLayoutWidget.getOptimizationLevel();
  }
  
  public String getSceneString()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    int i;
    Object localObject;
    if (this.mLayoutWidget.stringId == null)
    {
      i = getId();
      if (i != -1)
      {
        localObject = getContext().getResources().getResourceEntryName(i);
        this.mLayoutWidget.stringId = ((String)localObject);
      }
      else
      {
        this.mLayoutWidget.stringId = "parent";
      }
    }
    if (this.mLayoutWidget.getDebugName() == null)
    {
      localObject = this.mLayoutWidget;
      ((ConstraintWidgetContainer)localObject).setDebugName(((ConstraintWidgetContainer)localObject).stringId);
      Log.v("ConstraintLayout", " setDebugName " + this.mLayoutWidget.getDebugName());
    }
    Iterator localIterator = this.mLayoutWidget.getChildren().iterator();
    while (localIterator.hasNext())
    {
      localObject = (ConstraintWidget)localIterator.next();
      View localView = (View)((ConstraintWidget)localObject).getCompanionWidget();
      if (localView != null)
      {
        if (((ConstraintWidget)localObject).stringId == null)
        {
          i = localView.getId();
          if (i != -1) {
            ((ConstraintWidget)localObject).stringId = getContext().getResources().getResourceEntryName(i);
          }
        }
        if (((ConstraintWidget)localObject).getDebugName() == null)
        {
          ((ConstraintWidget)localObject).setDebugName(((ConstraintWidget)localObject).stringId);
          Log.v("ConstraintLayout", " setDebugName " + ((ConstraintWidget)localObject).getDebugName());
        }
      }
    }
    this.mLayoutWidget.getSceneString(localStringBuilder);
    return localStringBuilder.toString();
  }
  
  public View getViewById(int paramInt)
  {
    return (View)this.mChildrenByIds.get(paramInt);
  }
  
  public final ConstraintWidget getViewWidget(View paramView)
  {
    if (paramView == this) {
      return this.mLayoutWidget;
    }
    if (paramView != null)
    {
      if ((paramView.getLayoutParams() instanceof LayoutParams)) {
        return ((LayoutParams)paramView.getLayoutParams()).widget;
      }
      paramView.setLayoutParams(generateLayoutParams(paramView.getLayoutParams()));
      if ((paramView.getLayoutParams() instanceof LayoutParams)) {
        return ((LayoutParams)paramView.getLayoutParams()).widget;
      }
    }
    return null;
  }
  
  protected boolean isRtl()
  {
    int i = Build.VERSION.SDK_INT;
    boolean bool2 = false;
    if (i >= 17)
    {
      if ((getContext().getApplicationInfo().flags & 0x400000) != 0) {
        i = 1;
      } else {
        i = 0;
      }
      boolean bool1 = bool2;
      if (i != 0)
      {
        bool1 = bool2;
        if (1 == getLayoutDirection()) {
          bool1 = true;
        }
      }
      return bool1;
    }
    return false;
  }
  
  public void loadLayoutDescription(int paramInt)
  {
    if (paramInt != 0) {
      try
      {
        ConstraintLayoutStates localConstraintLayoutStates = new androidx/constraintlayout/widget/ConstraintLayoutStates;
        localConstraintLayoutStates.<init>(getContext(), this, paramInt);
        this.mConstraintLayoutSpec = localConstraintLayoutStates;
      }
      catch (Resources.NotFoundException localNotFoundException)
      {
        this.mConstraintLayoutSpec = null;
      }
    } else {
      this.mConstraintLayoutSpec = null;
    }
  }
  
  protected void onLayout(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    paramInt2 = getChildCount();
    paramBoolean = isInEditMode();
    for (paramInt1 = 0; paramInt1 < paramInt2; paramInt1++)
    {
      View localView = getChildAt(paramInt1);
      LayoutParams localLayoutParams = (LayoutParams)localView.getLayoutParams();
      ConstraintWidget localConstraintWidget = localLayoutParams.widget;
      if (((localView.getVisibility() != 8) || (localLayoutParams.isGuideline) || (localLayoutParams.isHelper) || (localLayoutParams.isVirtualGroup) || (paramBoolean)) && (!localLayoutParams.isInPlaceholder))
      {
        paramInt4 = localConstraintWidget.getX();
        int j = localConstraintWidget.getY();
        paramInt3 = localConstraintWidget.getWidth() + paramInt4;
        int i = localConstraintWidget.getHeight() + j;
        localView.layout(paramInt4, j, paramInt3, i);
        if ((localView instanceof Placeholder))
        {
          localView = ((Placeholder)localView).getContent();
          if (localView != null)
          {
            localView.setVisibility(0);
            localView.layout(paramInt4, j, paramInt3, i);
          }
        }
      }
    }
    paramInt2 = this.mConstraintHelpers.size();
    if (paramInt2 > 0) {
      for (paramInt1 = 0; paramInt1 < paramInt2; paramInt1++) {
        ((ConstraintHelper)this.mConstraintHelpers.get(paramInt1)).updatePostLayout(this);
      }
    }
  }
  
  protected void onMeasure(int paramInt1, int paramInt2)
  {
    if (((this.mOnMeasureWidthMeasureSpec != paramInt1) || (this.mOnMeasureHeightMeasureSpec == paramInt2)) || ((!this.mDirtyHierarchy) && (0 == 0)))
    {
      int j = getChildCount();
      for (int i = 0; i < j; i++) {
        if (getChildAt(i).isLayoutRequested())
        {
          this.mDirtyHierarchy = true;
          break;
        }
      }
    }
    if ((!this.mDirtyHierarchy) && (0 != 0))
    {
      resolveMeasuredDimension(paramInt1, paramInt2, this.mLayoutWidget.getWidth(), this.mLayoutWidget.getHeight(), this.mLayoutWidget.isWidthMeasuredTooSmall(), this.mLayoutWidget.isHeightMeasuredTooSmall());
      return;
    }
    this.mOnMeasureWidthMeasureSpec = paramInt1;
    this.mOnMeasureHeightMeasureSpec = paramInt2;
    this.mLayoutWidget.setRtl(isRtl());
    if (this.mDirtyHierarchy)
    {
      this.mDirtyHierarchy = false;
      if (updateHierarchy()) {
        this.mLayoutWidget.updateHierarchy();
      }
    }
    resolveSystem(this.mLayoutWidget, this.mOptimizationLevel, paramInt1, paramInt2);
    resolveMeasuredDimension(paramInt1, paramInt2, this.mLayoutWidget.getWidth(), this.mLayoutWidget.getHeight(), this.mLayoutWidget.isWidthMeasuredTooSmall(), this.mLayoutWidget.isHeightMeasuredTooSmall());
  }
  
  public void onViewAdded(View paramView)
  {
    super.onViewAdded(paramView);
    Object localObject = getViewWidget(paramView);
    if (((paramView instanceof Guideline)) && (!(localObject instanceof androidx.constraintlayout.core.widgets.Guideline)))
    {
      localObject = (LayoutParams)paramView.getLayoutParams();
      ((LayoutParams)localObject).widget = new androidx.constraintlayout.core.widgets.Guideline();
      ((LayoutParams)localObject).isGuideline = true;
      ((androidx.constraintlayout.core.widgets.Guideline)((LayoutParams)localObject).widget).setOrientation(((LayoutParams)localObject).orientation);
    }
    if ((paramView instanceof ConstraintHelper))
    {
      localObject = (ConstraintHelper)paramView;
      ((ConstraintHelper)localObject).validateParams();
      ((LayoutParams)paramView.getLayoutParams()).isHelper = true;
      if (!this.mConstraintHelpers.contains(localObject)) {
        this.mConstraintHelpers.add(localObject);
      }
    }
    this.mChildrenByIds.put(paramView.getId(), paramView);
    this.mDirtyHierarchy = true;
  }
  
  public void onViewRemoved(View paramView)
  {
    super.onViewRemoved(paramView);
    this.mChildrenByIds.remove(paramView.getId());
    ConstraintWidget localConstraintWidget = getViewWidget(paramView);
    this.mLayoutWidget.remove(localConstraintWidget);
    this.mConstraintHelpers.remove(paramView);
    this.mDirtyHierarchy = true;
  }
  
  protected void parseLayoutDescription(int paramInt)
  {
    this.mConstraintLayoutSpec = new ConstraintLayoutStates(getContext(), this, paramInt);
  }
  
  public void requestLayout()
  {
    markHierarchyDirty();
    super.requestLayout();
  }
  
  protected void resolveMeasuredDimension(int paramInt1, int paramInt2, int paramInt3, int paramInt4, boolean paramBoolean1, boolean paramBoolean2)
  {
    int i = this.mMeasurer.paddingHeight;
    paramInt1 = resolveSizeAndState(paramInt3 + this.mMeasurer.paddingWidth, paramInt1, 0);
    paramInt3 = resolveSizeAndState(paramInt4 + i, paramInt2, 0 << 16);
    paramInt2 = Math.min(this.mMaxWidth, paramInt1 & 0xFFFFFF);
    paramInt3 = Math.min(this.mMaxHeight, paramInt3 & 0xFFFFFF);
    paramInt1 = paramInt2;
    if (paramBoolean1) {
      paramInt1 = paramInt2 | 0x1000000;
    }
    paramInt2 = paramInt3;
    if (paramBoolean2) {
      paramInt2 = paramInt3 | 0x1000000;
    }
    setMeasuredDimension(paramInt1, paramInt2);
    this.mLastMeasureWidth = paramInt1;
    this.mLastMeasureHeight = paramInt2;
  }
  
  protected void resolveSystem(ConstraintWidgetContainer paramConstraintWidgetContainer, int paramInt1, int paramInt2, int paramInt3)
  {
    int k = View.MeasureSpec.getMode(paramInt2);
    int i1 = View.MeasureSpec.getSize(paramInt2);
    int i = View.MeasureSpec.getMode(paramInt3);
    int n = View.MeasureSpec.getSize(paramInt3);
    int j = Math.max(0, getPaddingTop());
    int i3 = Math.max(0, getPaddingBottom());
    int m = j + i3;
    int i2 = getPaddingWidth();
    this.mMeasurer.captureLayoutInfo(paramInt2, paramInt3, j, i3, i2, m);
    if (Build.VERSION.SDK_INT >= 17)
    {
      paramInt2 = Math.max(0, getPaddingStart());
      paramInt3 = Math.max(0, getPaddingEnd());
      if ((paramInt2 <= 0) && (paramInt3 <= 0)) {
        paramInt2 = Math.max(0, getPaddingLeft());
      } else if (isRtl()) {
        paramInt2 = paramInt3;
      }
    }
    else
    {
      paramInt2 = Math.max(0, getPaddingLeft());
    }
    paramInt3 = i1 - i2;
    m = n - m;
    setSelfDimensionBehaviour(paramConstraintWidgetContainer, k, paramInt3, i, m);
    paramConstraintWidgetContainer.measure(paramInt1, k, paramInt3, i, m, this.mLastMeasureWidth, this.mLastMeasureHeight, paramInt2, j);
  }
  
  public void setConstraintSet(ConstraintSet paramConstraintSet)
  {
    this.mConstraintSet = paramConstraintSet;
  }
  
  public void setDesignInformation(int paramInt, Object paramObject1, Object paramObject2)
  {
    if ((paramInt == 0) && ((paramObject1 instanceof String)) && ((paramObject2 instanceof Integer)))
    {
      if (this.mDesignIds == null) {
        this.mDesignIds = new HashMap();
      }
      String str = (String)paramObject1;
      paramInt = str.indexOf("/");
      paramObject1 = str;
      if (paramInt != -1) {
        paramObject1 = str.substring(paramInt + 1);
      }
      paramInt = ((Integer)paramObject2).intValue();
      this.mDesignIds.put(paramObject1, Integer.valueOf(paramInt));
    }
  }
  
  public void setId(int paramInt)
  {
    this.mChildrenByIds.remove(getId());
    super.setId(paramInt);
    this.mChildrenByIds.put(getId(), this);
  }
  
  public void setMaxHeight(int paramInt)
  {
    if (paramInt == this.mMaxHeight) {
      return;
    }
    this.mMaxHeight = paramInt;
    requestLayout();
  }
  
  public void setMaxWidth(int paramInt)
  {
    if (paramInt == this.mMaxWidth) {
      return;
    }
    this.mMaxWidth = paramInt;
    requestLayout();
  }
  
  public void setMinHeight(int paramInt)
  {
    if (paramInt == this.mMinHeight) {
      return;
    }
    this.mMinHeight = paramInt;
    requestLayout();
  }
  
  public void setMinWidth(int paramInt)
  {
    if (paramInt == this.mMinWidth) {
      return;
    }
    this.mMinWidth = paramInt;
    requestLayout();
  }
  
  public void setOnConstraintsChanged(ConstraintsChangedListener paramConstraintsChangedListener)
  {
    this.mConstraintsChangedListener = paramConstraintsChangedListener;
    ConstraintLayoutStates localConstraintLayoutStates = this.mConstraintLayoutSpec;
    if (localConstraintLayoutStates != null) {
      localConstraintLayoutStates.setOnConstraintsChanged(paramConstraintsChangedListener);
    }
  }
  
  public void setOptimizationLevel(int paramInt)
  {
    this.mOptimizationLevel = paramInt;
    this.mLayoutWidget.setOptimizationLevel(paramInt);
  }
  
  protected void setSelfDimensionBehaviour(ConstraintWidgetContainer paramConstraintWidgetContainer, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    int k = this.mMeasurer.paddingHeight;
    int m = this.mMeasurer.paddingWidth;
    Object localObject1 = ConstraintWidget.DimensionBehaviour.FIXED;
    Object localObject2 = ConstraintWidget.DimensionBehaviour.FIXED;
    int j = 0;
    int i = 0;
    int n = getChildCount();
    ConstraintWidget.DimensionBehaviour localDimensionBehaviour;
    switch (paramInt1)
    {
    default: 
      paramInt1 = j;
      break;
    case 1073741824: 
      paramInt1 = Math.min(this.mMaxWidth - m, paramInt2);
      break;
    case 0: 
      localDimensionBehaviour = ConstraintWidget.DimensionBehaviour.WRAP_CONTENT;
      localObject1 = localDimensionBehaviour;
      paramInt1 = j;
      if (n == 0)
      {
        paramInt1 = Math.max(0, this.mMinWidth);
        localObject1 = localDimensionBehaviour;
      }
      break;
    case -2147483648: 
      localDimensionBehaviour = ConstraintWidget.DimensionBehaviour.WRAP_CONTENT;
      paramInt1 = paramInt2;
      localObject1 = localDimensionBehaviour;
      if (n == 0)
      {
        paramInt1 = Math.max(0, this.mMinWidth);
        localObject1 = localDimensionBehaviour;
      }
      break;
    }
    switch (paramInt3)
    {
    default: 
      paramInt2 = i;
      break;
    case 1073741824: 
      paramInt2 = Math.min(this.mMaxHeight - k, paramInt4);
      break;
    case 0: 
      localDimensionBehaviour = ConstraintWidget.DimensionBehaviour.WRAP_CONTENT;
      localObject2 = localDimensionBehaviour;
      paramInt2 = i;
      if (n == 0)
      {
        paramInt2 = Math.max(0, this.mMinHeight);
        localObject2 = localDimensionBehaviour;
      }
      break;
    case -2147483648: 
      localDimensionBehaviour = ConstraintWidget.DimensionBehaviour.WRAP_CONTENT;
      paramInt2 = paramInt4;
      localObject2 = localDimensionBehaviour;
      if (n == 0)
      {
        paramInt2 = Math.max(0, this.mMinHeight);
        localObject2 = localDimensionBehaviour;
      }
      break;
    }
    if ((paramInt1 != paramConstraintWidgetContainer.getWidth()) || (paramInt2 != paramConstraintWidgetContainer.getHeight())) {
      paramConstraintWidgetContainer.invalidateMeasures();
    }
    paramConstraintWidgetContainer.setX(0);
    paramConstraintWidgetContainer.setY(0);
    paramConstraintWidgetContainer.setMaxWidth(this.mMaxWidth - m);
    paramConstraintWidgetContainer.setMaxHeight(this.mMaxHeight - k);
    paramConstraintWidgetContainer.setMinWidth(0);
    paramConstraintWidgetContainer.setMinHeight(0);
    paramConstraintWidgetContainer.setHorizontalDimensionBehaviour((ConstraintWidget.DimensionBehaviour)localObject1);
    paramConstraintWidgetContainer.setWidth(paramInt1);
    paramConstraintWidgetContainer.setVerticalDimensionBehaviour((ConstraintWidget.DimensionBehaviour)localObject2);
    paramConstraintWidgetContainer.setHeight(paramInt2);
    paramConstraintWidgetContainer.setMinWidth(this.mMinWidth - m);
    paramConstraintWidgetContainer.setMinHeight(this.mMinHeight - k);
  }
  
  public void setState(int paramInt1, int paramInt2, int paramInt3)
  {
    ConstraintLayoutStates localConstraintLayoutStates = this.mConstraintLayoutSpec;
    if (localConstraintLayoutStates != null) {
      localConstraintLayoutStates.updateConstraints(paramInt1, paramInt2, paramInt3);
    }
  }
  
  public boolean shouldDelayChildPressedState()
  {
    return false;
  }
  
  public static class LayoutParams
    extends ViewGroup.MarginLayoutParams
  {
    public static final int BASELINE = 5;
    public static final int BOTTOM = 4;
    public static final int CHAIN_PACKED = 2;
    public static final int CHAIN_SPREAD = 0;
    public static final int CHAIN_SPREAD_INSIDE = 1;
    public static final int CIRCLE = 8;
    public static final int END = 7;
    public static final int GONE_UNSET = Integer.MIN_VALUE;
    public static final int HORIZONTAL = 0;
    public static final int LEFT = 1;
    public static final int MATCH_CONSTRAINT = 0;
    public static final int MATCH_CONSTRAINT_PERCENT = 2;
    public static final int MATCH_CONSTRAINT_SPREAD = 0;
    public static final int MATCH_CONSTRAINT_WRAP = 1;
    public static final int PARENT_ID = 0;
    public static final int RIGHT = 2;
    public static final int START = 6;
    public static final int TOP = 3;
    public static final int UNSET = -1;
    public static final int VERTICAL = 1;
    public static final int WRAP_BEHAVIOR_HORIZONTAL_ONLY = 1;
    public static final int WRAP_BEHAVIOR_INCLUDED = 0;
    public static final int WRAP_BEHAVIOR_SKIPPED = 3;
    public static final int WRAP_BEHAVIOR_VERTICAL_ONLY = 2;
    public int baselineMargin = 0;
    public int baselineToBaseline = -1;
    public int baselineToBottom = -1;
    public int baselineToTop = -1;
    public int bottomToBottom = -1;
    public int bottomToTop = -1;
    public float circleAngle = 0.0F;
    public int circleConstraint = -1;
    public int circleRadius = 0;
    public boolean constrainedHeight = false;
    public boolean constrainedWidth = false;
    public String constraintTag = null;
    public String dimensionRatio = null;
    int dimensionRatioSide = 1;
    float dimensionRatioValue = 0.0F;
    public int editorAbsoluteX = -1;
    public int editorAbsoluteY = -1;
    public int endToEnd = -1;
    public int endToStart = -1;
    public int goneBaselineMargin = Integer.MIN_VALUE;
    public int goneBottomMargin = Integer.MIN_VALUE;
    public int goneEndMargin = Integer.MIN_VALUE;
    public int goneLeftMargin = Integer.MIN_VALUE;
    public int goneRightMargin = Integer.MIN_VALUE;
    public int goneStartMargin = Integer.MIN_VALUE;
    public int goneTopMargin = Integer.MIN_VALUE;
    public int guideBegin = -1;
    public int guideEnd = -1;
    public float guidePercent = -1.0F;
    public boolean guidelineUseRtl = true;
    boolean heightSet = true;
    public boolean helped = false;
    public float horizontalBias = 0.5F;
    public int horizontalChainStyle = 0;
    boolean horizontalDimensionFixed = true;
    public float horizontalWeight = -1.0F;
    boolean isGuideline = false;
    boolean isHelper = false;
    boolean isInPlaceholder = false;
    boolean isVirtualGroup = false;
    public int leftToLeft = -1;
    public int leftToRight = -1;
    public int matchConstraintDefaultHeight = 0;
    public int matchConstraintDefaultWidth = 0;
    public int matchConstraintMaxHeight = 0;
    public int matchConstraintMaxWidth = 0;
    public int matchConstraintMinHeight = 0;
    public int matchConstraintMinWidth = 0;
    public float matchConstraintPercentHeight = 1.0F;
    public float matchConstraintPercentWidth = 1.0F;
    boolean needsBaseline = false;
    public int orientation = -1;
    int resolveGoneLeftMargin = Integer.MIN_VALUE;
    int resolveGoneRightMargin = Integer.MIN_VALUE;
    int resolvedGuideBegin;
    int resolvedGuideEnd;
    float resolvedGuidePercent;
    float resolvedHorizontalBias = 0.5F;
    int resolvedLeftToLeft = -1;
    int resolvedLeftToRight = -1;
    int resolvedRightToLeft = -1;
    int resolvedRightToRight = -1;
    public int rightToLeft = -1;
    public int rightToRight = -1;
    public int startToEnd = -1;
    public int startToStart = -1;
    public int topToBottom = -1;
    public int topToTop = -1;
    public float verticalBias = 0.5F;
    public int verticalChainStyle = 0;
    boolean verticalDimensionFixed = true;
    public float verticalWeight = -1.0F;
    ConstraintWidget widget = new ConstraintWidget();
    boolean widthSet = true;
    public int wrapBehaviorInParent = 0;
    
    public LayoutParams(int paramInt1, int paramInt2)
    {
      super(paramInt2);
    }
    
    public LayoutParams(Context paramContext, AttributeSet paramAttributeSet)
    {
      super(paramAttributeSet);
      paramContext = paramContext.obtainStyledAttributes(paramAttributeSet, R.styleable.ConstraintLayout_Layout);
      int j = paramContext.getIndexCount();
      for (int i = 0; i < j; i++)
      {
        int k = paramContext.getIndex(i);
        int m;
        switch (Table.map.get(k))
        {
        case 43: 
        case 56: 
        case 57: 
        case 58: 
        case 59: 
        case 60: 
        case 61: 
        case 62: 
        case 63: 
        default: 
          break;
        case 67: 
          this.guidelineUseRtl = paramContext.getBoolean(k, this.guidelineUseRtl);
          break;
        case 66: 
          this.wrapBehaviorInParent = paramContext.getInt(k, this.wrapBehaviorInParent);
          break;
        case 65: 
          ConstraintSet.parseDimensionConstraints(this, paramContext, k, 1);
          this.heightSet = true;
          break;
        case 64: 
          ConstraintSet.parseDimensionConstraints(this, paramContext, k, 0);
          this.widthSet = true;
          break;
        case 55: 
          this.goneBaselineMargin = paramContext.getDimensionPixelSize(k, this.goneBaselineMargin);
          break;
        case 54: 
          this.baselineMargin = paramContext.getDimensionPixelSize(k, this.baselineMargin);
          break;
        case 53: 
          m = paramContext.getResourceId(k, this.baselineToBottom);
          this.baselineToBottom = m;
          if (m == -1) {
            this.baselineToBottom = paramContext.getInt(k, -1);
          }
          break;
        case 52: 
          m = paramContext.getResourceId(k, this.baselineToTop);
          this.baselineToTop = m;
          if (m == -1) {
            this.baselineToTop = paramContext.getInt(k, -1);
          }
          break;
        case 51: 
          this.constraintTag = paramContext.getString(k);
          break;
        case 50: 
          this.editorAbsoluteY = paramContext.getDimensionPixelOffset(k, this.editorAbsoluteY);
          break;
        case 49: 
          this.editorAbsoluteX = paramContext.getDimensionPixelOffset(k, this.editorAbsoluteX);
          break;
        case 48: 
          this.verticalChainStyle = paramContext.getInt(k, 0);
          break;
        case 47: 
          this.horizontalChainStyle = paramContext.getInt(k, 0);
          break;
        case 46: 
          this.verticalWeight = paramContext.getFloat(k, this.verticalWeight);
          break;
        case 45: 
          this.horizontalWeight = paramContext.getFloat(k, this.horizontalWeight);
          break;
        case 44: 
          ConstraintSet.parseDimensionRatioString(this, paramContext.getString(k));
          break;
        case 42: 
          break;
        case 41: 
          break;
        case 40: 
          break;
        case 39: 
          break;
        case 38: 
          this.matchConstraintPercentHeight = Math.max(0.0F, paramContext.getFloat(k, this.matchConstraintPercentHeight));
          this.matchConstraintDefaultHeight = 2;
          break;
        case 37: 
          try
          {
            this.matchConstraintMaxHeight = paramContext.getDimensionPixelSize(k, this.matchConstraintMaxHeight);
          }
          catch (Exception paramAttributeSet)
          {
            if (paramContext.getInt(k, this.matchConstraintMaxHeight) == -2) {
              this.matchConstraintMaxHeight = -2;
            }
          }
        case 36: 
          try
          {
            this.matchConstraintMinHeight = paramContext.getDimensionPixelSize(k, this.matchConstraintMinHeight);
          }
          catch (Exception paramAttributeSet)
          {
            if (paramContext.getInt(k, this.matchConstraintMinHeight) == -2) {
              this.matchConstraintMinHeight = -2;
            }
          }
        case 35: 
          this.matchConstraintPercentWidth = Math.max(0.0F, paramContext.getFloat(k, this.matchConstraintPercentWidth));
          this.matchConstraintDefaultWidth = 2;
          break;
        case 34: 
          try
          {
            this.matchConstraintMaxWidth = paramContext.getDimensionPixelSize(k, this.matchConstraintMaxWidth);
          }
          catch (Exception paramAttributeSet)
          {
            if (paramContext.getInt(k, this.matchConstraintMaxWidth) == -2) {
              this.matchConstraintMaxWidth = -2;
            }
          }
        case 33: 
          try
          {
            this.matchConstraintMinWidth = paramContext.getDimensionPixelSize(k, this.matchConstraintMinWidth);
          }
          catch (Exception paramAttributeSet)
          {
            if (paramContext.getInt(k, this.matchConstraintMinWidth) == -2) {
              this.matchConstraintMinWidth = -2;
            }
          }
        case 32: 
          k = paramContext.getInt(k, 0);
          this.matchConstraintDefaultHeight = k;
          if (k == 1) {
            Log.e("ConstraintLayout", "layout_constraintHeight_default=\"wrap\" is deprecated.\nUse layout_height=\"WRAP_CONTENT\" and layout_constrainedHeight=\"true\" instead.");
          }
          break;
        case 31: 
          k = paramContext.getInt(k, 0);
          this.matchConstraintDefaultWidth = k;
          if (k == 1) {
            Log.e("ConstraintLayout", "layout_constraintWidth_default=\"wrap\" is deprecated.\nUse layout_width=\"WRAP_CONTENT\" and layout_constrainedWidth=\"true\" instead.");
          }
          break;
        case 30: 
          this.verticalBias = paramContext.getFloat(k, this.verticalBias);
          break;
        case 29: 
          this.horizontalBias = paramContext.getFloat(k, this.horizontalBias);
          break;
        case 28: 
          this.constrainedHeight = paramContext.getBoolean(k, this.constrainedHeight);
          break;
        case 27: 
          this.constrainedWidth = paramContext.getBoolean(k, this.constrainedWidth);
          break;
        case 26: 
          this.goneEndMargin = paramContext.getDimensionPixelSize(k, this.goneEndMargin);
          break;
        case 25: 
          this.goneStartMargin = paramContext.getDimensionPixelSize(k, this.goneStartMargin);
          break;
        case 24: 
          this.goneBottomMargin = paramContext.getDimensionPixelSize(k, this.goneBottomMargin);
          break;
        case 23: 
          this.goneRightMargin = paramContext.getDimensionPixelSize(k, this.goneRightMargin);
          break;
        case 22: 
          this.goneTopMargin = paramContext.getDimensionPixelSize(k, this.goneTopMargin);
          break;
        case 21: 
          this.goneLeftMargin = paramContext.getDimensionPixelSize(k, this.goneLeftMargin);
          break;
        case 20: 
          m = paramContext.getResourceId(k, this.endToEnd);
          this.endToEnd = m;
          if (m == -1) {
            this.endToEnd = paramContext.getInt(k, -1);
          }
          break;
        case 19: 
          m = paramContext.getResourceId(k, this.endToStart);
          this.endToStart = m;
          if (m == -1) {
            this.endToStart = paramContext.getInt(k, -1);
          }
          break;
        case 18: 
          m = paramContext.getResourceId(k, this.startToStart);
          this.startToStart = m;
          if (m == -1) {
            this.startToStart = paramContext.getInt(k, -1);
          }
          break;
        case 17: 
          m = paramContext.getResourceId(k, this.startToEnd);
          this.startToEnd = m;
          if (m == -1) {
            this.startToEnd = paramContext.getInt(k, -1);
          }
          break;
        case 16: 
          m = paramContext.getResourceId(k, this.baselineToBaseline);
          this.baselineToBaseline = m;
          if (m == -1) {
            this.baselineToBaseline = paramContext.getInt(k, -1);
          }
          break;
        case 15: 
          m = paramContext.getResourceId(k, this.bottomToBottom);
          this.bottomToBottom = m;
          if (m == -1) {
            this.bottomToBottom = paramContext.getInt(k, -1);
          }
          break;
        case 14: 
          m = paramContext.getResourceId(k, this.bottomToTop);
          this.bottomToTop = m;
          if (m == -1) {
            this.bottomToTop = paramContext.getInt(k, -1);
          }
          break;
        case 13: 
          m = paramContext.getResourceId(k, this.topToBottom);
          this.topToBottom = m;
          if (m == -1) {
            this.topToBottom = paramContext.getInt(k, -1);
          }
          break;
        case 12: 
          m = paramContext.getResourceId(k, this.topToTop);
          this.topToTop = m;
          if (m == -1) {
            this.topToTop = paramContext.getInt(k, -1);
          }
          break;
        case 11: 
          m = paramContext.getResourceId(k, this.rightToRight);
          this.rightToRight = m;
          if (m == -1) {
            this.rightToRight = paramContext.getInt(k, -1);
          }
          break;
        case 10: 
          m = paramContext.getResourceId(k, this.rightToLeft);
          this.rightToLeft = m;
          if (m == -1) {
            this.rightToLeft = paramContext.getInt(k, -1);
          }
          break;
        case 9: 
          m = paramContext.getResourceId(k, this.leftToRight);
          this.leftToRight = m;
          if (m == -1) {
            this.leftToRight = paramContext.getInt(k, -1);
          }
          break;
        case 8: 
          m = paramContext.getResourceId(k, this.leftToLeft);
          this.leftToLeft = m;
          if (m == -1) {
            this.leftToLeft = paramContext.getInt(k, -1);
          }
          break;
        case 7: 
          this.guidePercent = paramContext.getFloat(k, this.guidePercent);
          break;
        case 6: 
          this.guideEnd = paramContext.getDimensionPixelOffset(k, this.guideEnd);
          break;
        case 5: 
          this.guideBegin = paramContext.getDimensionPixelOffset(k, this.guideBegin);
          break;
        case 4: 
          float f = paramContext.getFloat(k, this.circleAngle) % 360.0F;
          this.circleAngle = f;
          if (f < 0.0F) {
            this.circleAngle = ((360.0F - f) % 360.0F);
          }
          break;
        case 3: 
          this.circleRadius = paramContext.getDimensionPixelSize(k, this.circleRadius);
          break;
        case 2: 
          m = paramContext.getResourceId(k, this.circleConstraint);
          this.circleConstraint = m;
          if (m == -1) {
            this.circleConstraint = paramContext.getInt(k, -1);
          }
          break;
        case 1: 
          this.orientation = paramContext.getInt(k, this.orientation);
        }
      }
      paramContext.recycle();
      validate();
    }
    
    public LayoutParams(ViewGroup.LayoutParams paramLayoutParams)
    {
      super();
    }
    
    public LayoutParams(LayoutParams paramLayoutParams)
    {
      super();
      this.guideBegin = paramLayoutParams.guideBegin;
      this.guideEnd = paramLayoutParams.guideEnd;
      this.guidePercent = paramLayoutParams.guidePercent;
      this.guidelineUseRtl = paramLayoutParams.guidelineUseRtl;
      this.leftToLeft = paramLayoutParams.leftToLeft;
      this.leftToRight = paramLayoutParams.leftToRight;
      this.rightToLeft = paramLayoutParams.rightToLeft;
      this.rightToRight = paramLayoutParams.rightToRight;
      this.topToTop = paramLayoutParams.topToTop;
      this.topToBottom = paramLayoutParams.topToBottom;
      this.bottomToTop = paramLayoutParams.bottomToTop;
      this.bottomToBottom = paramLayoutParams.bottomToBottom;
      this.baselineToBaseline = paramLayoutParams.baselineToBaseline;
      this.baselineToTop = paramLayoutParams.baselineToTop;
      this.baselineToBottom = paramLayoutParams.baselineToBottom;
      this.circleConstraint = paramLayoutParams.circleConstraint;
      this.circleRadius = paramLayoutParams.circleRadius;
      this.circleAngle = paramLayoutParams.circleAngle;
      this.startToEnd = paramLayoutParams.startToEnd;
      this.startToStart = paramLayoutParams.startToStart;
      this.endToStart = paramLayoutParams.endToStart;
      this.endToEnd = paramLayoutParams.endToEnd;
      this.goneLeftMargin = paramLayoutParams.goneLeftMargin;
      this.goneTopMargin = paramLayoutParams.goneTopMargin;
      this.goneRightMargin = paramLayoutParams.goneRightMargin;
      this.goneBottomMargin = paramLayoutParams.goneBottomMargin;
      this.goneStartMargin = paramLayoutParams.goneStartMargin;
      this.goneEndMargin = paramLayoutParams.goneEndMargin;
      this.goneBaselineMargin = paramLayoutParams.goneBaselineMargin;
      this.baselineMargin = paramLayoutParams.baselineMargin;
      this.horizontalBias = paramLayoutParams.horizontalBias;
      this.verticalBias = paramLayoutParams.verticalBias;
      this.dimensionRatio = paramLayoutParams.dimensionRatio;
      this.dimensionRatioValue = paramLayoutParams.dimensionRatioValue;
      this.dimensionRatioSide = paramLayoutParams.dimensionRatioSide;
      this.horizontalWeight = paramLayoutParams.horizontalWeight;
      this.verticalWeight = paramLayoutParams.verticalWeight;
      this.horizontalChainStyle = paramLayoutParams.horizontalChainStyle;
      this.verticalChainStyle = paramLayoutParams.verticalChainStyle;
      this.constrainedWidth = paramLayoutParams.constrainedWidth;
      this.constrainedHeight = paramLayoutParams.constrainedHeight;
      this.matchConstraintDefaultWidth = paramLayoutParams.matchConstraintDefaultWidth;
      this.matchConstraintDefaultHeight = paramLayoutParams.matchConstraintDefaultHeight;
      this.matchConstraintMinWidth = paramLayoutParams.matchConstraintMinWidth;
      this.matchConstraintMaxWidth = paramLayoutParams.matchConstraintMaxWidth;
      this.matchConstraintMinHeight = paramLayoutParams.matchConstraintMinHeight;
      this.matchConstraintMaxHeight = paramLayoutParams.matchConstraintMaxHeight;
      this.matchConstraintPercentWidth = paramLayoutParams.matchConstraintPercentWidth;
      this.matchConstraintPercentHeight = paramLayoutParams.matchConstraintPercentHeight;
      this.editorAbsoluteX = paramLayoutParams.editorAbsoluteX;
      this.editorAbsoluteY = paramLayoutParams.editorAbsoluteY;
      this.orientation = paramLayoutParams.orientation;
      this.horizontalDimensionFixed = paramLayoutParams.horizontalDimensionFixed;
      this.verticalDimensionFixed = paramLayoutParams.verticalDimensionFixed;
      this.needsBaseline = paramLayoutParams.needsBaseline;
      this.isGuideline = paramLayoutParams.isGuideline;
      this.resolvedLeftToLeft = paramLayoutParams.resolvedLeftToLeft;
      this.resolvedLeftToRight = paramLayoutParams.resolvedLeftToRight;
      this.resolvedRightToLeft = paramLayoutParams.resolvedRightToLeft;
      this.resolvedRightToRight = paramLayoutParams.resolvedRightToRight;
      this.resolveGoneLeftMargin = paramLayoutParams.resolveGoneLeftMargin;
      this.resolveGoneRightMargin = paramLayoutParams.resolveGoneRightMargin;
      this.resolvedHorizontalBias = paramLayoutParams.resolvedHorizontalBias;
      this.constraintTag = paramLayoutParams.constraintTag;
      this.wrapBehaviorInParent = paramLayoutParams.wrapBehaviorInParent;
      this.widget = paramLayoutParams.widget;
      this.widthSet = paramLayoutParams.widthSet;
      this.heightSet = paramLayoutParams.heightSet;
    }
    
    public String getConstraintTag()
    {
      return this.constraintTag;
    }
    
    public ConstraintWidget getConstraintWidget()
    {
      return this.widget;
    }
    
    public void reset()
    {
      ConstraintWidget localConstraintWidget = this.widget;
      if (localConstraintWidget != null) {
        localConstraintWidget.reset();
      }
    }
    
    public void resolveLayoutDirection(int paramInt)
    {
      int j = this.leftMargin;
      int k = this.rightMargin;
      int i = 0;
      if (Build.VERSION.SDK_INT >= 17)
      {
        super.resolveLayoutDirection(paramInt);
        if (1 == getLayoutDirection()) {
          paramInt = 1;
        } else {
          paramInt = 0;
        }
        i = paramInt;
      }
      this.resolvedRightToLeft = -1;
      this.resolvedRightToRight = -1;
      this.resolvedLeftToLeft = -1;
      this.resolvedLeftToRight = -1;
      this.resolveGoneLeftMargin = -1;
      this.resolveGoneRightMargin = -1;
      this.resolveGoneLeftMargin = this.goneLeftMargin;
      this.resolveGoneRightMargin = this.goneRightMargin;
      float f1 = this.horizontalBias;
      this.resolvedHorizontalBias = f1;
      int m = this.guideBegin;
      this.resolvedGuideBegin = m;
      int n = this.guideEnd;
      this.resolvedGuideEnd = n;
      float f2 = this.guidePercent;
      this.resolvedGuidePercent = f2;
      if (i != 0)
      {
        paramInt = 0;
        i = this.startToEnd;
        if (i != -1)
        {
          this.resolvedRightToLeft = i;
          paramInt = 1;
        }
        else
        {
          i = this.startToStart;
          if (i != -1)
          {
            this.resolvedRightToRight = i;
            paramInt = 1;
          }
        }
        i = this.endToStart;
        if (i != -1)
        {
          this.resolvedLeftToRight = i;
          paramInt = 1;
        }
        i = this.endToEnd;
        if (i != -1)
        {
          this.resolvedLeftToLeft = i;
          paramInt = 1;
        }
        i = this.goneStartMargin;
        if (i != Integer.MIN_VALUE) {
          this.resolveGoneRightMargin = i;
        }
        i = this.goneEndMargin;
        if (i != Integer.MIN_VALUE) {
          this.resolveGoneLeftMargin = i;
        }
        if (paramInt != 0) {
          this.resolvedHorizontalBias = (1.0F - f1);
        }
        if ((this.isGuideline) && (this.orientation == 1) && (this.guidelineUseRtl)) {
          if (f2 != -1.0F)
          {
            this.resolvedGuidePercent = (1.0F - f2);
            this.resolvedGuideBegin = -1;
            this.resolvedGuideEnd = -1;
          }
          else if (m != -1)
          {
            this.resolvedGuideEnd = m;
            this.resolvedGuideBegin = -1;
            this.resolvedGuidePercent = -1.0F;
          }
          else if (n != -1)
          {
            this.resolvedGuideBegin = n;
            this.resolvedGuideEnd = -1;
            this.resolvedGuidePercent = -1.0F;
          }
        }
      }
      else
      {
        paramInt = this.startToEnd;
        if (paramInt != -1) {
          this.resolvedLeftToRight = paramInt;
        }
        paramInt = this.startToStart;
        if (paramInt != -1) {
          this.resolvedLeftToLeft = paramInt;
        }
        paramInt = this.endToStart;
        if (paramInt != -1) {
          this.resolvedRightToLeft = paramInt;
        }
        paramInt = this.endToEnd;
        if (paramInt != -1) {
          this.resolvedRightToRight = paramInt;
        }
        paramInt = this.goneStartMargin;
        if (paramInt != Integer.MIN_VALUE) {
          this.resolveGoneLeftMargin = paramInt;
        }
        paramInt = this.goneEndMargin;
        if (paramInt != Integer.MIN_VALUE) {
          this.resolveGoneRightMargin = paramInt;
        }
      }
      if ((this.endToStart == -1) && (this.endToEnd == -1) && (this.startToStart == -1) && (this.startToEnd == -1))
      {
        paramInt = this.rightToLeft;
        if (paramInt != -1)
        {
          this.resolvedRightToLeft = paramInt;
          if ((this.rightMargin <= 0) && (k > 0)) {
            this.rightMargin = k;
          }
        }
        else
        {
          paramInt = this.rightToRight;
          if (paramInt != -1)
          {
            this.resolvedRightToRight = paramInt;
            if ((this.rightMargin <= 0) && (k > 0)) {
              this.rightMargin = k;
            }
          }
        }
        paramInt = this.leftToLeft;
        if (paramInt != -1)
        {
          this.resolvedLeftToLeft = paramInt;
          if ((this.leftMargin <= 0) && (j > 0)) {
            this.leftMargin = j;
          }
        }
        else
        {
          paramInt = this.leftToRight;
          if (paramInt != -1)
          {
            this.resolvedLeftToRight = paramInt;
            if ((this.leftMargin <= 0) && (j > 0)) {
              this.leftMargin = j;
            }
          }
        }
      }
    }
    
    public void setWidgetDebugName(String paramString)
    {
      this.widget.setDebugName(paramString);
    }
    
    public void validate()
    {
      this.isGuideline = false;
      this.horizontalDimensionFixed = true;
      this.verticalDimensionFixed = true;
      if ((this.width == -2) && (this.constrainedWidth))
      {
        this.horizontalDimensionFixed = false;
        if (this.matchConstraintDefaultWidth == 0) {
          this.matchConstraintDefaultWidth = 1;
        }
      }
      if ((this.height == -2) && (this.constrainedHeight))
      {
        this.verticalDimensionFixed = false;
        if (this.matchConstraintDefaultHeight == 0) {
          this.matchConstraintDefaultHeight = 1;
        }
      }
      if ((this.width == 0) || (this.width == -1))
      {
        this.horizontalDimensionFixed = false;
        if ((this.width == 0) && (this.matchConstraintDefaultWidth == 1))
        {
          this.width = -2;
          this.constrainedWidth = true;
        }
      }
      if ((this.height == 0) || (this.height == -1))
      {
        this.verticalDimensionFixed = false;
        if ((this.height == 0) && (this.matchConstraintDefaultHeight == 1))
        {
          this.height = -2;
          this.constrainedHeight = true;
        }
      }
      if ((this.guidePercent != -1.0F) || (this.guideBegin != -1) || (this.guideEnd != -1))
      {
        this.isGuideline = true;
        this.horizontalDimensionFixed = true;
        this.verticalDimensionFixed = true;
        if (!(this.widget instanceof androidx.constraintlayout.core.widgets.Guideline)) {
          this.widget = new androidx.constraintlayout.core.widgets.Guideline();
        }
        ((androidx.constraintlayout.core.widgets.Guideline)this.widget).setOrientation(this.orientation);
      }
    }
    
    private static class Table
    {
      public static final int ANDROID_ORIENTATION = 1;
      public static final int GUIDELINE_USE_RTL = 67;
      public static final int LAYOUT_CONSTRAINED_HEIGHT = 28;
      public static final int LAYOUT_CONSTRAINED_WIDTH = 27;
      public static final int LAYOUT_CONSTRAINT_BASELINE_CREATOR = 43;
      public static final int LAYOUT_CONSTRAINT_BASELINE_TO_BASELINE_OF = 16;
      public static final int LAYOUT_CONSTRAINT_BASELINE_TO_BOTTOM_OF = 53;
      public static final int LAYOUT_CONSTRAINT_BASELINE_TO_TOP_OF = 52;
      public static final int LAYOUT_CONSTRAINT_BOTTOM_CREATOR = 42;
      public static final int LAYOUT_CONSTRAINT_BOTTOM_TO_BOTTOM_OF = 15;
      public static final int LAYOUT_CONSTRAINT_BOTTOM_TO_TOP_OF = 14;
      public static final int LAYOUT_CONSTRAINT_CIRCLE = 2;
      public static final int LAYOUT_CONSTRAINT_CIRCLE_ANGLE = 4;
      public static final int LAYOUT_CONSTRAINT_CIRCLE_RADIUS = 3;
      public static final int LAYOUT_CONSTRAINT_DIMENSION_RATIO = 44;
      public static final int LAYOUT_CONSTRAINT_END_TO_END_OF = 20;
      public static final int LAYOUT_CONSTRAINT_END_TO_START_OF = 19;
      public static final int LAYOUT_CONSTRAINT_GUIDE_BEGIN = 5;
      public static final int LAYOUT_CONSTRAINT_GUIDE_END = 6;
      public static final int LAYOUT_CONSTRAINT_GUIDE_PERCENT = 7;
      public static final int LAYOUT_CONSTRAINT_HEIGHT = 65;
      public static final int LAYOUT_CONSTRAINT_HEIGHT_DEFAULT = 32;
      public static final int LAYOUT_CONSTRAINT_HEIGHT_MAX = 37;
      public static final int LAYOUT_CONSTRAINT_HEIGHT_MIN = 36;
      public static final int LAYOUT_CONSTRAINT_HEIGHT_PERCENT = 38;
      public static final int LAYOUT_CONSTRAINT_HORIZONTAL_BIAS = 29;
      public static final int LAYOUT_CONSTRAINT_HORIZONTAL_CHAINSTYLE = 47;
      public static final int LAYOUT_CONSTRAINT_HORIZONTAL_WEIGHT = 45;
      public static final int LAYOUT_CONSTRAINT_LEFT_CREATOR = 39;
      public static final int LAYOUT_CONSTRAINT_LEFT_TO_LEFT_OF = 8;
      public static final int LAYOUT_CONSTRAINT_LEFT_TO_RIGHT_OF = 9;
      public static final int LAYOUT_CONSTRAINT_RIGHT_CREATOR = 41;
      public static final int LAYOUT_CONSTRAINT_RIGHT_TO_LEFT_OF = 10;
      public static final int LAYOUT_CONSTRAINT_RIGHT_TO_RIGHT_OF = 11;
      public static final int LAYOUT_CONSTRAINT_START_TO_END_OF = 17;
      public static final int LAYOUT_CONSTRAINT_START_TO_START_OF = 18;
      public static final int LAYOUT_CONSTRAINT_TAG = 51;
      public static final int LAYOUT_CONSTRAINT_TOP_CREATOR = 40;
      public static final int LAYOUT_CONSTRAINT_TOP_TO_BOTTOM_OF = 13;
      public static final int LAYOUT_CONSTRAINT_TOP_TO_TOP_OF = 12;
      public static final int LAYOUT_CONSTRAINT_VERTICAL_BIAS = 30;
      public static final int LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE = 48;
      public static final int LAYOUT_CONSTRAINT_VERTICAL_WEIGHT = 46;
      public static final int LAYOUT_CONSTRAINT_WIDTH = 64;
      public static final int LAYOUT_CONSTRAINT_WIDTH_DEFAULT = 31;
      public static final int LAYOUT_CONSTRAINT_WIDTH_MAX = 34;
      public static final int LAYOUT_CONSTRAINT_WIDTH_MIN = 33;
      public static final int LAYOUT_CONSTRAINT_WIDTH_PERCENT = 35;
      public static final int LAYOUT_EDITOR_ABSOLUTEX = 49;
      public static final int LAYOUT_EDITOR_ABSOLUTEY = 50;
      public static final int LAYOUT_GONE_MARGIN_BASELINE = 55;
      public static final int LAYOUT_GONE_MARGIN_BOTTOM = 24;
      public static final int LAYOUT_GONE_MARGIN_END = 26;
      public static final int LAYOUT_GONE_MARGIN_LEFT = 21;
      public static final int LAYOUT_GONE_MARGIN_RIGHT = 23;
      public static final int LAYOUT_GONE_MARGIN_START = 25;
      public static final int LAYOUT_GONE_MARGIN_TOP = 22;
      public static final int LAYOUT_MARGIN_BASELINE = 54;
      public static final int LAYOUT_WRAP_BEHAVIOR_IN_PARENT = 66;
      public static final int UNUSED = 0;
      public static final SparseIntArray map;
      
      static
      {
        SparseIntArray localSparseIntArray = new SparseIntArray();
        map = localSparseIntArray;
        localSparseIntArray.append(R.styleable.ConstraintLayout_Layout_layout_constraintWidth, 64);
        localSparseIntArray.append(R.styleable.ConstraintLayout_Layout_layout_constraintHeight, 65);
        localSparseIntArray.append(R.styleable.ConstraintLayout_Layout_layout_constraintLeft_toLeftOf, 8);
        localSparseIntArray.append(R.styleable.ConstraintLayout_Layout_layout_constraintLeft_toRightOf, 9);
        localSparseIntArray.append(R.styleable.ConstraintLayout_Layout_layout_constraintRight_toLeftOf, 10);
        localSparseIntArray.append(R.styleable.ConstraintLayout_Layout_layout_constraintRight_toRightOf, 11);
        localSparseIntArray.append(R.styleable.ConstraintLayout_Layout_layout_constraintTop_toTopOf, 12);
        localSparseIntArray.append(R.styleable.ConstraintLayout_Layout_layout_constraintTop_toBottomOf, 13);
        localSparseIntArray.append(R.styleable.ConstraintLayout_Layout_layout_constraintBottom_toTopOf, 14);
        localSparseIntArray.append(R.styleable.ConstraintLayout_Layout_layout_constraintBottom_toBottomOf, 15);
        localSparseIntArray.append(R.styleable.ConstraintLayout_Layout_layout_constraintBaseline_toBaselineOf, 16);
        localSparseIntArray.append(R.styleable.ConstraintLayout_Layout_layout_constraintBaseline_toTopOf, 52);
        localSparseIntArray.append(R.styleable.ConstraintLayout_Layout_layout_constraintBaseline_toBottomOf, 53);
        localSparseIntArray.append(R.styleable.ConstraintLayout_Layout_layout_constraintCircle, 2);
        localSparseIntArray.append(R.styleable.ConstraintLayout_Layout_layout_constraintCircleRadius, 3);
        localSparseIntArray.append(R.styleable.ConstraintLayout_Layout_layout_constraintCircleAngle, 4);
        localSparseIntArray.append(R.styleable.ConstraintLayout_Layout_layout_editor_absoluteX, 49);
        localSparseIntArray.append(R.styleable.ConstraintLayout_Layout_layout_editor_absoluteY, 50);
        localSparseIntArray.append(R.styleable.ConstraintLayout_Layout_layout_constraintGuide_begin, 5);
        localSparseIntArray.append(R.styleable.ConstraintLayout_Layout_layout_constraintGuide_end, 6);
        localSparseIntArray.append(R.styleable.ConstraintLayout_Layout_layout_constraintGuide_percent, 7);
        localSparseIntArray.append(R.styleable.ConstraintLayout_Layout_guidelineUseRtl, 67);
        localSparseIntArray.append(R.styleable.ConstraintLayout_Layout_android_orientation, 1);
        localSparseIntArray.append(R.styleable.ConstraintLayout_Layout_layout_constraintStart_toEndOf, 17);
        localSparseIntArray.append(R.styleable.ConstraintLayout_Layout_layout_constraintStart_toStartOf, 18);
        localSparseIntArray.append(R.styleable.ConstraintLayout_Layout_layout_constraintEnd_toStartOf, 19);
        localSparseIntArray.append(R.styleable.ConstraintLayout_Layout_layout_constraintEnd_toEndOf, 20);
        localSparseIntArray.append(R.styleable.ConstraintLayout_Layout_layout_goneMarginLeft, 21);
        localSparseIntArray.append(R.styleable.ConstraintLayout_Layout_layout_goneMarginTop, 22);
        localSparseIntArray.append(R.styleable.ConstraintLayout_Layout_layout_goneMarginRight, 23);
        localSparseIntArray.append(R.styleable.ConstraintLayout_Layout_layout_goneMarginBottom, 24);
        localSparseIntArray.append(R.styleable.ConstraintLayout_Layout_layout_goneMarginStart, 25);
        localSparseIntArray.append(R.styleable.ConstraintLayout_Layout_layout_goneMarginEnd, 26);
        localSparseIntArray.append(R.styleable.ConstraintLayout_Layout_layout_goneMarginBaseline, 55);
        localSparseIntArray.append(R.styleable.ConstraintLayout_Layout_layout_marginBaseline, 54);
        localSparseIntArray.append(R.styleable.ConstraintLayout_Layout_layout_constraintHorizontal_bias, 29);
        localSparseIntArray.append(R.styleable.ConstraintLayout_Layout_layout_constraintVertical_bias, 30);
        localSparseIntArray.append(R.styleable.ConstraintLayout_Layout_layout_constraintDimensionRatio, 44);
        localSparseIntArray.append(R.styleable.ConstraintLayout_Layout_layout_constraintHorizontal_weight, 45);
        localSparseIntArray.append(R.styleable.ConstraintLayout_Layout_layout_constraintVertical_weight, 46);
        localSparseIntArray.append(R.styleable.ConstraintLayout_Layout_layout_constraintHorizontal_chainStyle, 47);
        localSparseIntArray.append(R.styleable.ConstraintLayout_Layout_layout_constraintVertical_chainStyle, 48);
        localSparseIntArray.append(R.styleable.ConstraintLayout_Layout_layout_constrainedWidth, 27);
        localSparseIntArray.append(R.styleable.ConstraintLayout_Layout_layout_constrainedHeight, 28);
        localSparseIntArray.append(R.styleable.ConstraintLayout_Layout_layout_constraintWidth_default, 31);
        localSparseIntArray.append(R.styleable.ConstraintLayout_Layout_layout_constraintHeight_default, 32);
        localSparseIntArray.append(R.styleable.ConstraintLayout_Layout_layout_constraintWidth_min, 33);
        localSparseIntArray.append(R.styleable.ConstraintLayout_Layout_layout_constraintWidth_max, 34);
        localSparseIntArray.append(R.styleable.ConstraintLayout_Layout_layout_constraintWidth_percent, 35);
        localSparseIntArray.append(R.styleable.ConstraintLayout_Layout_layout_constraintHeight_min, 36);
        localSparseIntArray.append(R.styleable.ConstraintLayout_Layout_layout_constraintHeight_max, 37);
        localSparseIntArray.append(R.styleable.ConstraintLayout_Layout_layout_constraintHeight_percent, 38);
        localSparseIntArray.append(R.styleable.ConstraintLayout_Layout_layout_constraintLeft_creator, 39);
        localSparseIntArray.append(R.styleable.ConstraintLayout_Layout_layout_constraintTop_creator, 40);
        localSparseIntArray.append(R.styleable.ConstraintLayout_Layout_layout_constraintRight_creator, 41);
        localSparseIntArray.append(R.styleable.ConstraintLayout_Layout_layout_constraintBottom_creator, 42);
        localSparseIntArray.append(R.styleable.ConstraintLayout_Layout_layout_constraintBaseline_creator, 43);
        localSparseIntArray.append(R.styleable.ConstraintLayout_Layout_layout_constraintTag, 51);
        localSparseIntArray.append(R.styleable.ConstraintLayout_Layout_layout_wrapBehaviorInParent, 66);
      }
    }
  }
  
  class Measurer
    implements BasicMeasure.Measurer
  {
    ConstraintLayout layout;
    int layoutHeightSpec;
    int layoutWidthSpec;
    int paddingBottom;
    int paddingHeight;
    int paddingTop;
    int paddingWidth;
    
    public Measurer(ConstraintLayout paramConstraintLayout)
    {
      this.layout = paramConstraintLayout;
    }
    
    private boolean isSimilarSpec(int paramInt1, int paramInt2, int paramInt3)
    {
      if (paramInt1 == paramInt2) {
        return true;
      }
      int i = View.MeasureSpec.getMode(paramInt1);
      View.MeasureSpec.getSize(paramInt1);
      paramInt1 = View.MeasureSpec.getMode(paramInt2);
      paramInt2 = View.MeasureSpec.getSize(paramInt2);
      return (paramInt1 == 1073741824) && ((i == Integer.MIN_VALUE) || (i == 0)) && (paramInt3 == paramInt2);
    }
    
    public void captureLayoutInfo(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6)
    {
      this.paddingTop = paramInt3;
      this.paddingBottom = paramInt4;
      this.paddingWidth = paramInt5;
      this.paddingHeight = paramInt6;
      this.layoutWidthSpec = paramInt1;
      this.layoutHeightSpec = paramInt2;
    }
    
    public final void didMeasures()
    {
      int j = this.layout.getChildCount();
      for (int i = 0; i < j; i++)
      {
        View localView = this.layout.getChildAt(i);
        if ((localView instanceof Placeholder)) {
          ((Placeholder)localView).updatePostMeasure(this.layout);
        }
      }
      j = this.layout.mConstraintHelpers.size();
      if (j > 0) {
        for (i = 0; i < j; i++) {
          ((ConstraintHelper)this.layout.mConstraintHelpers.get(i)).updatePostMeasure(this.layout);
        }
      }
    }
    
    public final void measure(ConstraintWidget paramConstraintWidget, BasicMeasure.Measure paramMeasure)
    {
      if (paramConstraintWidget == null) {
        return;
      }
      if ((paramConstraintWidget.getVisibility() == 8) && (!paramConstraintWidget.isInPlaceholder()))
      {
        paramMeasure.measuredWidth = 0;
        paramMeasure.measuredHeight = 0;
        paramMeasure.measuredBaseline = 0;
        return;
      }
      if (paramConstraintWidget.getParent() == null) {
        return;
      }
      Object localObject1 = paramMeasure.horizontalBehavior;
      ConstraintWidget.DimensionBehaviour localDimensionBehaviour = paramMeasure.verticalBehavior;
      int m = paramMeasure.horizontalDimension;
      int n = paramMeasure.verticalDimension;
      int i = 0;
      int k = 0;
      int i1 = this.paddingTop + this.paddingBottom;
      int j = this.paddingWidth;
      View localView = (View)paramConstraintWidget.getCompanionWidget();
      switch (ConstraintLayout.1.$SwitchMap$androidx$constraintlayout$core$widgets$ConstraintWidget$DimensionBehaviour[localObject1.ordinal()])
      {
      default: 
        break;
      case 4: 
        m = ViewGroup.getChildMeasureSpec(this.layoutWidthSpec, j, -2);
        if (paramConstraintWidget.mMatchConstraintDefaultWidth == 1) {
          j = 1;
        } else {
          j = 0;
        }
        if (paramMeasure.measureStrategy != BasicMeasure.Measure.TRY_GIVEN_DIMENSIONS)
        {
          i = m;
          if (paramMeasure.measureStrategy != BasicMeasure.Measure.USE_GIVEN_DIMENSIONS) {
            break;
          }
        }
        else
        {
          if (localView.getMeasuredHeight() == paramConstraintWidget.getHeight()) {
            i = 1;
          } else {
            i = 0;
          }
          if ((paramMeasure.measureStrategy != BasicMeasure.Measure.USE_GIVEN_DIMENSIONS) && (j != 0) && ((j == 0) || (i == 0)) && (!(localView instanceof Placeholder)) && (!paramConstraintWidget.isResolvedHorizontally())) {
            i = 0;
          } else {
            i = 1;
          }
          if (i != 0) {
            i = View.MeasureSpec.makeMeasureSpec(paramConstraintWidget.getWidth(), 1073741824);
          } else {
            i = m;
          }
        }
        break;
      case 3: 
        i = ViewGroup.getChildMeasureSpec(this.layoutWidthSpec, paramConstraintWidget.getHorizontalMargin() + j, -1);
        break;
      case 2: 
        i = ViewGroup.getChildMeasureSpec(this.layoutWidthSpec, j, -2);
        break;
      case 1: 
        i = View.MeasureSpec.makeMeasureSpec(m, 1073741824);
      }
      switch (ConstraintLayout.1.$SwitchMap$androidx$constraintlayout$core$widgets$ConstraintWidget$DimensionBehaviour[localDimensionBehaviour.ordinal()])
      {
      default: 
        j = k;
        break;
      case 4: 
        m = ViewGroup.getChildMeasureSpec(this.layoutHeightSpec, i1, -2);
        if (paramConstraintWidget.mMatchConstraintDefaultHeight == 1) {
          k = 1;
        } else {
          k = 0;
        }
        if (paramMeasure.measureStrategy != BasicMeasure.Measure.TRY_GIVEN_DIMENSIONS)
        {
          j = m;
          if (paramMeasure.measureStrategy != BasicMeasure.Measure.USE_GIVEN_DIMENSIONS) {
            break;
          }
        }
        else
        {
          if (localView.getMeasuredWidth() == paramConstraintWidget.getWidth()) {
            j = 1;
          } else {
            j = 0;
          }
          if ((paramMeasure.measureStrategy != BasicMeasure.Measure.USE_GIVEN_DIMENSIONS) && (k != 0) && ((k == 0) || (j == 0)) && (!(localView instanceof Placeholder)) && (!paramConstraintWidget.isResolvedVertically())) {
            j = 0;
          } else {
            j = 1;
          }
          if (j != 0) {
            j = View.MeasureSpec.makeMeasureSpec(paramConstraintWidget.getHeight(), 1073741824);
          } else {
            j = m;
          }
        }
        break;
      case 3: 
        j = ViewGroup.getChildMeasureSpec(this.layoutHeightSpec, paramConstraintWidget.getVerticalMargin() + i1, -1);
        break;
      case 2: 
        j = ViewGroup.getChildMeasureSpec(this.layoutHeightSpec, i1, -2);
        break;
      case 1: 
        j = View.MeasureSpec.makeMeasureSpec(n, 1073741824);
      }
      Object localObject2 = (ConstraintWidgetContainer)paramConstraintWidget.getParent();
      if ((localObject2 != null) && (Optimizer.enabled(ConstraintLayout.this.mOptimizationLevel, 256)) && (localView.getMeasuredWidth() == paramConstraintWidget.getWidth()) && (localView.getMeasuredWidth() < ((ConstraintWidgetContainer)localObject2).getWidth()) && (localView.getMeasuredHeight() == paramConstraintWidget.getHeight()) && (localView.getMeasuredHeight() < ((ConstraintWidgetContainer)localObject2).getHeight()) && (localView.getBaseline() == paramConstraintWidget.getBaselineDistance()) && (!paramConstraintWidget.isMeasureRequested()))
      {
        if ((isSimilarSpec(paramConstraintWidget.getLastHorizontalMeasureSpec(), i, paramConstraintWidget.getWidth())) && (isSimilarSpec(paramConstraintWidget.getLastVerticalMeasureSpec(), j, paramConstraintWidget.getHeight()))) {
          k = 1;
        } else {
          k = 0;
        }
        if (k != 0)
        {
          paramMeasure.measuredWidth = paramConstraintWidget.getWidth();
          paramMeasure.measuredHeight = paramConstraintWidget.getHeight();
          paramMeasure.measuredBaseline = paramConstraintWidget.getBaselineDistance();
          return;
        }
      }
      if (localObject1 == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
        k = 1;
      } else {
        k = 0;
      }
      if (localDimensionBehaviour == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
        m = 1;
      } else {
        m = 0;
      }
      if ((localDimensionBehaviour != ConstraintWidget.DimensionBehaviour.MATCH_PARENT) && (localDimensionBehaviour != ConstraintWidget.DimensionBehaviour.FIXED)) {
        i1 = 0;
      } else {
        i1 = 1;
      }
      int i2;
      if ((localObject1 != ConstraintWidget.DimensionBehaviour.MATCH_PARENT) && (localObject1 != ConstraintWidget.DimensionBehaviour.FIXED)) {
        i2 = 0;
      } else {
        i2 = 1;
      }
      int i4;
      if ((k != 0) && (paramConstraintWidget.mDimensionRatio > 0.0F)) {
        i4 = 1;
      } else {
        i4 = 0;
      }
      int i3;
      if ((m != 0) && (paramConstraintWidget.mDimensionRatio > 0.0F)) {
        i3 = 1;
      } else {
        i3 = 0;
      }
      if (localView == null) {
        return;
      }
      localObject1 = (ConstraintLayout.LayoutParams)localView.getLayoutParams();
      if ((paramMeasure.measureStrategy != BasicMeasure.Measure.TRY_GIVEN_DIMENSIONS) && (paramMeasure.measureStrategy != BasicMeasure.Measure.USE_GIVEN_DIMENSIONS) && (k != 0) && (paramConstraintWidget.mMatchConstraintDefaultWidth == 0) && (m != 0) && (paramConstraintWidget.mMatchConstraintDefaultHeight == 0))
      {
        i = 0;
        m = 0;
        j = 0;
      }
      else
      {
        if (((localView instanceof VirtualLayout)) && ((paramConstraintWidget instanceof androidx.constraintlayout.core.widgets.VirtualLayout)))
        {
          localObject2 = (androidx.constraintlayout.core.widgets.VirtualLayout)paramConstraintWidget;
          ((VirtualLayout)localView).onMeasure((androidx.constraintlayout.core.widgets.VirtualLayout)localObject2, i, j);
        }
        else
        {
          localView.measure(i, j);
        }
        paramConstraintWidget.setLastMeasureSpec(i, j);
        int i7 = localView.getMeasuredWidth();
        int i5 = localView.getMeasuredHeight();
        int i6 = localView.getBaseline();
        if (paramConstraintWidget.mMatchConstraintMinWidth > 0) {
          m = Math.max(paramConstraintWidget.mMatchConstraintMinWidth, i7);
        } else {
          m = i7;
        }
        k = m;
        if (paramConstraintWidget.mMatchConstraintMaxWidth > 0) {
          k = Math.min(paramConstraintWidget.mMatchConstraintMaxWidth, m);
        }
        if (paramConstraintWidget.mMatchConstraintMinHeight > 0) {
          m = Math.max(paramConstraintWidget.mMatchConstraintMinHeight, i5);
        } else {
          m = i5;
        }
        n = m;
        if (paramConstraintWidget.mMatchConstraintMaxHeight > 0) {
          n = Math.min(paramConstraintWidget.mMatchConstraintMaxHeight, m);
        }
        if (!Optimizer.enabled(ConstraintLayout.this.mOptimizationLevel, 1))
        {
          float f;
          if ((i4 != 0) && (i1 != 0))
          {
            f = paramConstraintWidget.mDimensionRatio;
            i1 = (int)(n * f + 0.5F);
            m = n;
          }
          else
          {
            i1 = k;
            m = n;
            if (i3 != 0)
            {
              i1 = k;
              m = n;
              if (i2 != 0)
              {
                f = paramConstraintWidget.mDimensionRatio;
                m = (int)(k / f + 0.5F);
                i1 = k;
              }
            }
          }
        }
        else
        {
          m = n;
          i1 = k;
        }
        if ((i7 == i1) && (i5 == m))
        {
          i = i1;
          j = i6;
        }
        else
        {
          if (i7 != i1) {
            i = View.MeasureSpec.makeMeasureSpec(i1, 1073741824);
          }
          if (i5 != m) {
            j = View.MeasureSpec.makeMeasureSpec(m, 1073741824);
          }
          localView.measure(i, j);
          paramConstraintWidget.setLastMeasureSpec(i, j);
          i = localView.getMeasuredWidth();
          m = localView.getMeasuredHeight();
          j = localView.getBaseline();
        }
      }
      boolean bool1;
      if (j != -1) {
        bool1 = true;
      } else {
        bool1 = false;
      }
      boolean bool2;
      if ((i == paramMeasure.horizontalDimension) && (m == paramMeasure.verticalDimension)) {
        bool2 = false;
      } else {
        bool2 = true;
      }
      paramMeasure.measuredNeedsSolverPass = bool2;
      if (((ConstraintLayout.LayoutParams)localObject1).needsBaseline) {
        bool1 = true;
      }
      if ((bool1) && (j != -1) && (paramConstraintWidget.getBaselineDistance() != j)) {
        paramMeasure.measuredNeedsSolverPass = true;
      }
      paramMeasure.measuredWidth = i;
      paramMeasure.measuredHeight = m;
      paramMeasure.measuredHasBaseline = bool1;
      paramMeasure.measuredBaseline = j;
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/constraintlayout/widget/ConstraintLayout.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */