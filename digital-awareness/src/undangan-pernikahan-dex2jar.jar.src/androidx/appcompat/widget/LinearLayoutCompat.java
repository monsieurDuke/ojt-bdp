package androidx.appcompat.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewGroup.MarginLayoutParams;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.LinearLayout.LayoutParams;
import androidx.appcompat.R.styleable;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class LinearLayoutCompat
  extends ViewGroup
{
  private static final String ACCESSIBILITY_CLASS_NAME = "androidx.appcompat.widget.LinearLayoutCompat";
  public static final int HORIZONTAL = 0;
  private static final int INDEX_BOTTOM = 2;
  private static final int INDEX_CENTER_VERTICAL = 0;
  private static final int INDEX_FILL = 3;
  private static final int INDEX_TOP = 1;
  public static final int SHOW_DIVIDER_BEGINNING = 1;
  public static final int SHOW_DIVIDER_END = 4;
  public static final int SHOW_DIVIDER_MIDDLE = 2;
  public static final int SHOW_DIVIDER_NONE = 0;
  public static final int VERTICAL = 1;
  private static final int VERTICAL_GRAVITY_COUNT = 4;
  private boolean mBaselineAligned = true;
  private int mBaselineAlignedChildIndex = -1;
  private int mBaselineChildTop = 0;
  private Drawable mDivider;
  private int mDividerHeight;
  private int mDividerPadding;
  private int mDividerWidth;
  private int mGravity = 8388659;
  private int[] mMaxAscent;
  private int[] mMaxDescent;
  private int mOrientation;
  private int mShowDividers;
  private int mTotalLength;
  private boolean mUseLargestChild;
  private float mWeightSum;
  
  public LinearLayoutCompat(Context paramContext)
  {
    this(paramContext, null);
  }
  
  public LinearLayoutCompat(Context paramContext, AttributeSet paramAttributeSet)
  {
    this(paramContext, paramAttributeSet, 0);
  }
  
  public LinearLayoutCompat(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
    TintTypedArray localTintTypedArray = TintTypedArray.obtainStyledAttributes(paramContext, paramAttributeSet, R.styleable.LinearLayoutCompat, paramInt, 0);
    ViewCompat.saveAttributeDataForStyleable(this, paramContext, R.styleable.LinearLayoutCompat, paramAttributeSet, localTintTypedArray.getWrappedTypeArray(), paramInt, 0);
    paramInt = localTintTypedArray.getInt(R.styleable.LinearLayoutCompat_android_orientation, -1);
    if (paramInt >= 0) {
      setOrientation(paramInt);
    }
    paramInt = localTintTypedArray.getInt(R.styleable.LinearLayoutCompat_android_gravity, -1);
    if (paramInt >= 0) {
      setGravity(paramInt);
    }
    boolean bool = localTintTypedArray.getBoolean(R.styleable.LinearLayoutCompat_android_baselineAligned, true);
    if (!bool) {
      setBaselineAligned(bool);
    }
    this.mWeightSum = localTintTypedArray.getFloat(R.styleable.LinearLayoutCompat_android_weightSum, -1.0F);
    this.mBaselineAlignedChildIndex = localTintTypedArray.getInt(R.styleable.LinearLayoutCompat_android_baselineAlignedChildIndex, -1);
    this.mUseLargestChild = localTintTypedArray.getBoolean(R.styleable.LinearLayoutCompat_measureWithLargestChild, false);
    setDividerDrawable(localTintTypedArray.getDrawable(R.styleable.LinearLayoutCompat_divider));
    this.mShowDividers = localTintTypedArray.getInt(R.styleable.LinearLayoutCompat_showDividers, 0);
    this.mDividerPadding = localTintTypedArray.getDimensionPixelSize(R.styleable.LinearLayoutCompat_dividerPadding, 0);
    localTintTypedArray.recycle();
  }
  
  private void forceUniformHeight(int paramInt1, int paramInt2)
  {
    int j = View.MeasureSpec.makeMeasureSpec(getMeasuredHeight(), 1073741824);
    for (int i = 0; i < paramInt1; i++)
    {
      View localView = getVirtualChildAt(i);
      if (localView.getVisibility() != 8)
      {
        LayoutParams localLayoutParams = (LayoutParams)localView.getLayoutParams();
        if (localLayoutParams.height == -1)
        {
          int k = localLayoutParams.width;
          localLayoutParams.width = localView.getMeasuredWidth();
          measureChildWithMargins(localView, paramInt2, 0, j, 0);
          localLayoutParams.width = k;
        }
      }
    }
  }
  
  private void forceUniformWidth(int paramInt1, int paramInt2)
  {
    int j = View.MeasureSpec.makeMeasureSpec(getMeasuredWidth(), 1073741824);
    for (int i = 0; i < paramInt1; i++)
    {
      View localView = getVirtualChildAt(i);
      if (localView.getVisibility() != 8)
      {
        LayoutParams localLayoutParams = (LayoutParams)localView.getLayoutParams();
        if (localLayoutParams.width == -1)
        {
          int k = localLayoutParams.height;
          localLayoutParams.height = localView.getMeasuredHeight();
          measureChildWithMargins(localView, j, 0, paramInt2, 0);
          localLayoutParams.height = k;
        }
      }
    }
  }
  
  private void setChildFrame(View paramView, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    paramView.layout(paramInt1, paramInt2, paramInt1 + paramInt3, paramInt2 + paramInt4);
  }
  
  protected boolean checkLayoutParams(ViewGroup.LayoutParams paramLayoutParams)
  {
    return paramLayoutParams instanceof LayoutParams;
  }
  
  void drawDividersHorizontal(Canvas paramCanvas)
  {
    int k = getVirtualChildCount();
    boolean bool = ViewUtils.isLayoutRtl(this);
    Object localObject2;
    Object localObject1;
    for (int i = 0; i < k; i++)
    {
      localObject2 = getVirtualChildAt(i);
      if ((localObject2 != null) && (((View)localObject2).getVisibility() != 8) && (hasDividerBeforeChildAt(i)))
      {
        localObject1 = (LayoutParams)((View)localObject2).getLayoutParams();
        int j;
        if (bool) {
          j = ((View)localObject2).getRight() + ((LayoutParams)localObject1).rightMargin;
        } else {
          j = ((View)localObject2).getLeft() - ((LayoutParams)localObject1).leftMargin - this.mDividerWidth;
        }
        drawVerticalDivider(paramCanvas, j);
      }
    }
    if (hasDividerBeforeChildAt(k))
    {
      localObject1 = getVirtualChildAt(k - 1);
      if (localObject1 == null)
      {
        if (bool) {
          i = getPaddingLeft();
        } else {
          i = getWidth() - getPaddingRight() - this.mDividerWidth;
        }
      }
      else
      {
        localObject2 = (LayoutParams)((View)localObject1).getLayoutParams();
        if (bool) {
          i = ((View)localObject1).getLeft() - ((LayoutParams)localObject2).leftMargin - this.mDividerWidth;
        } else {
          i = ((View)localObject1).getRight() + ((LayoutParams)localObject2).rightMargin;
        }
      }
      drawVerticalDivider(paramCanvas, i);
    }
  }
  
  void drawDividersVertical(Canvas paramCanvas)
  {
    int j = getVirtualChildCount();
    Object localObject2;
    Object localObject1;
    for (int i = 0; i < j; i++)
    {
      localObject2 = getVirtualChildAt(i);
      if ((localObject2 != null) && (((View)localObject2).getVisibility() != 8) && (hasDividerBeforeChildAt(i)))
      {
        localObject1 = (LayoutParams)((View)localObject2).getLayoutParams();
        drawHorizontalDivider(paramCanvas, ((View)localObject2).getTop() - ((LayoutParams)localObject1).topMargin - this.mDividerHeight);
      }
    }
    if (hasDividerBeforeChildAt(j))
    {
      localObject1 = getVirtualChildAt(j - 1);
      if (localObject1 == null)
      {
        i = getHeight() - getPaddingBottom() - this.mDividerHeight;
      }
      else
      {
        localObject2 = (LayoutParams)((View)localObject1).getLayoutParams();
        i = ((View)localObject1).getBottom() + ((LayoutParams)localObject2).bottomMargin;
      }
      drawHorizontalDivider(paramCanvas, i);
    }
  }
  
  void drawHorizontalDivider(Canvas paramCanvas, int paramInt)
  {
    this.mDivider.setBounds(getPaddingLeft() + this.mDividerPadding, paramInt, getWidth() - getPaddingRight() - this.mDividerPadding, this.mDividerHeight + paramInt);
    this.mDivider.draw(paramCanvas);
  }
  
  void drawVerticalDivider(Canvas paramCanvas, int paramInt)
  {
    this.mDivider.setBounds(paramInt, getPaddingTop() + this.mDividerPadding, this.mDividerWidth + paramInt, getHeight() - getPaddingBottom() - this.mDividerPadding);
    this.mDivider.draw(paramCanvas);
  }
  
  protected LayoutParams generateDefaultLayoutParams()
  {
    int i = this.mOrientation;
    if (i == 0) {
      return new LayoutParams(-2, -2);
    }
    if (i == 1) {
      return new LayoutParams(-1, -2);
    }
    return null;
  }
  
  public LayoutParams generateLayoutParams(AttributeSet paramAttributeSet)
  {
    return new LayoutParams(getContext(), paramAttributeSet);
  }
  
  protected LayoutParams generateLayoutParams(ViewGroup.LayoutParams paramLayoutParams)
  {
    return new LayoutParams(paramLayoutParams);
  }
  
  public int getBaseline()
  {
    if (this.mBaselineAlignedChildIndex < 0) {
      return super.getBaseline();
    }
    int i = getChildCount();
    int j = this.mBaselineAlignedChildIndex;
    if (i > j)
    {
      View localView = getChildAt(j);
      int k = localView.getBaseline();
      if (k == -1)
      {
        if (this.mBaselineAlignedChildIndex == 0) {
          return -1;
        }
        throw new RuntimeException("mBaselineAlignedChildIndex of LinearLayout points to a View that doesn't know how to get its baseline.");
      }
      j = this.mBaselineChildTop;
      i = j;
      if (this.mOrientation == 1)
      {
        int m = this.mGravity & 0x70;
        i = j;
        if (m != 48) {
          switch (m)
          {
          default: 
            i = j;
            break;
          case 80: 
            i = getBottom() - getTop() - getPaddingBottom() - this.mTotalLength;
            break;
          case 16: 
            i = j + (getBottom() - getTop() - getPaddingTop() - getPaddingBottom() - this.mTotalLength) / 2;
          }
        }
      }
      return ((LayoutParams)localView.getLayoutParams()).topMargin + i + k;
    }
    throw new RuntimeException("mBaselineAlignedChildIndex of LinearLayout set to an index that is out of bounds.");
  }
  
  public int getBaselineAlignedChildIndex()
  {
    return this.mBaselineAlignedChildIndex;
  }
  
  int getChildrenSkipCount(View paramView, int paramInt)
  {
    return 0;
  }
  
  public Drawable getDividerDrawable()
  {
    return this.mDivider;
  }
  
  public int getDividerPadding()
  {
    return this.mDividerPadding;
  }
  
  public int getDividerWidth()
  {
    return this.mDividerWidth;
  }
  
  public int getGravity()
  {
    return this.mGravity;
  }
  
  int getLocationOffset(View paramView)
  {
    return 0;
  }
  
  int getNextLocationOffset(View paramView)
  {
    return 0;
  }
  
  public int getOrientation()
  {
    return this.mOrientation;
  }
  
  public int getShowDividers()
  {
    return this.mShowDividers;
  }
  
  View getVirtualChildAt(int paramInt)
  {
    return getChildAt(paramInt);
  }
  
  int getVirtualChildCount()
  {
    return getChildCount();
  }
  
  public float getWeightSum()
  {
    return this.mWeightSum;
  }
  
  protected boolean hasDividerBeforeChildAt(int paramInt)
  {
    boolean bool1 = false;
    boolean bool2 = false;
    if (paramInt == 0)
    {
      bool1 = bool2;
      if ((this.mShowDividers & 0x1) != 0) {
        bool1 = true;
      }
      return bool1;
    }
    if (paramInt == getChildCount())
    {
      if ((this.mShowDividers & 0x4) != 0) {
        bool1 = true;
      }
      return bool1;
    }
    if ((this.mShowDividers & 0x2) != 0)
    {
      bool2 = false;
      paramInt--;
      for (;;)
      {
        bool1 = bool2;
        if (paramInt < 0) {
          break;
        }
        if (getChildAt(paramInt).getVisibility() != 8)
        {
          bool1 = true;
          break;
        }
        paramInt--;
      }
      return bool1;
    }
    return false;
  }
  
  public boolean isBaselineAligned()
  {
    return this.mBaselineAligned;
  }
  
  public boolean isMeasureWithLargestChildEnabled()
  {
    return this.mUseLargestChild;
  }
  
  void layoutHorizontal(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    boolean bool1 = ViewUtils.isLayoutRtl(this);
    int i2 = getPaddingTop();
    int i3 = paramInt4 - paramInt2;
    int m = i3 - getPaddingBottom();
    int i6 = getPaddingBottom();
    int i5 = getVirtualChildCount();
    int i4 = this.mGravity;
    boolean bool2 = this.mBaselineAligned;
    int[] arrayOfInt1 = this.mMaxAscent;
    int[] arrayOfInt2 = this.mMaxDescent;
    int n = ViewCompat.getLayoutDirection(this);
    switch (GravityCompat.getAbsoluteGravity(i4 & 0x800007, n))
    {
    default: 
      paramInt1 = getPaddingLeft();
      break;
    case 5: 
      paramInt1 = getPaddingLeft() + paramInt3 - paramInt1 - this.mTotalLength;
      break;
    case 1: 
      paramInt1 = getPaddingLeft() + (paramInt3 - paramInt1 - this.mTotalLength) / 2;
    }
    int j;
    int k;
    if (bool1)
    {
      j = i5 - 1;
      k = -1;
    }
    else
    {
      j = 0;
      k = 1;
    }
    paramInt2 = 0;
    int i = i3;
    paramInt3 = i2;
    paramInt4 = paramInt1;
    while (paramInt2 < i5)
    {
      int i8 = j + k * paramInt2;
      View localView = getVirtualChildAt(i8);
      if (localView == null)
      {
        paramInt4 += measureNullChild(i8);
      }
      else if (localView.getVisibility() != 8)
      {
        int i7 = localView.getMeasuredWidth();
        int i9 = localView.getMeasuredHeight();
        LayoutParams localLayoutParams = (LayoutParams)localView.getLayoutParams();
        if ((bool2) && (localLayoutParams.height != -1)) {
          paramInt1 = localView.getBaseline();
        } else {
          paramInt1 = -1;
        }
        int i1 = localLayoutParams.gravity;
        if (i1 < 0) {
          i1 = i4 & 0x70;
        }
        switch (i1 & 0x70)
        {
        default: 
          paramInt1 = paramInt3;
          break;
        case 80: 
          i1 = m - i9 - localLayoutParams.bottomMargin;
          if (paramInt1 != -1)
          {
            int i10 = localView.getMeasuredHeight();
            paramInt1 = i1 - (arrayOfInt2[2] - (i10 - paramInt1));
          }
          else
          {
            paramInt1 = i1;
          }
          break;
        case 48: 
          i1 = localLayoutParams.topMargin + paramInt3;
          if (paramInt1 != -1) {
            paramInt1 = i1 + (arrayOfInt1[1] - paramInt1);
          } else {
            paramInt1 = i1;
          }
          break;
        case 16: 
          paramInt1 = (i3 - i2 - i6 - i9) / 2 + paramInt3 + localLayoutParams.topMargin - localLayoutParams.bottomMargin;
        }
        i1 = paramInt4;
        if (hasDividerBeforeChildAt(i8)) {
          i1 = paramInt4 + this.mDividerWidth;
        }
        paramInt4 = i1 + localLayoutParams.leftMargin;
        setChildFrame(localView, paramInt4 + getLocationOffset(localView), paramInt1, i7, i9);
        paramInt1 = localLayoutParams.rightMargin;
        i1 = getNextLocationOffset(localView);
        paramInt2 += getChildrenSkipCount(localView, i8);
        paramInt4 += i7 + paramInt1 + i1;
      }
      paramInt2++;
    }
  }
  
  void layoutVertical(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    int j = getPaddingLeft();
    int i1 = paramInt3 - paramInt1;
    int i2 = getPaddingRight();
    int k = getPaddingRight();
    int m = getVirtualChildCount();
    int n = this.mGravity;
    switch (n & 0x70)
    {
    default: 
      paramInt1 = getPaddingTop();
      break;
    case 80: 
      paramInt1 = getPaddingTop() + paramInt4 - paramInt2 - this.mTotalLength;
      break;
    case 16: 
      paramInt1 = getPaddingTop() + (paramInt4 - paramInt2 - this.mTotalLength) / 2;
    }
    paramInt2 = 0;
    for (paramInt3 = j;; paramInt3 = paramInt4)
    {
      paramInt4 = paramInt3;
      if (paramInt2 >= m) {
        break;
      }
      View localView = getVirtualChildAt(paramInt2);
      if (localView == null)
      {
        paramInt1 += measureNullChild(paramInt2);
      }
      else if (localView.getVisibility() != 8)
      {
        int i4 = localView.getMeasuredWidth();
        int i3 = localView.getMeasuredHeight();
        LayoutParams localLayoutParams = (LayoutParams)localView.getLayoutParams();
        paramInt3 = localLayoutParams.gravity;
        if (paramInt3 < 0) {
          paramInt3 = n & 0x800007;
        }
        switch (GravityCompat.getAbsoluteGravity(paramInt3, ViewCompat.getLayoutDirection(this)) & 0x7)
        {
        default: 
          paramInt3 = localLayoutParams.leftMargin + paramInt4;
          break;
        case 5: 
          paramInt3 = i1 - i2 - i4 - localLayoutParams.rightMargin;
          break;
        case 1: 
          paramInt3 = (i1 - j - k - i4) / 2 + paramInt4 + localLayoutParams.leftMargin - localLayoutParams.rightMargin;
        }
        int i = paramInt1;
        if (hasDividerBeforeChildAt(paramInt2)) {
          i = paramInt1 + this.mDividerHeight;
        }
        paramInt1 = i + localLayoutParams.topMargin;
        setChildFrame(localView, paramInt3, paramInt1 + getLocationOffset(localView), i4, i3);
        paramInt3 = localLayoutParams.bottomMargin;
        i = getNextLocationOffset(localView);
        paramInt2 += getChildrenSkipCount(localView, paramInt2);
        paramInt1 += i3 + paramInt3 + i;
      }
      paramInt2++;
    }
  }
  
  void measureChildBeforeLayout(View paramView, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5)
  {
    measureChildWithMargins(paramView, paramInt2, paramInt3, paramInt4, paramInt5);
  }
  
  void measureHorizontal(int paramInt1, int paramInt2)
  {
    this.mTotalLength = 0;
    int i3 = getVirtualChildCount();
    int i2 = View.MeasureSpec.getMode(paramInt1);
    int i12 = View.MeasureSpec.getMode(paramInt2);
    if ((this.mMaxAscent == null) || (this.mMaxDescent == null))
    {
      this.mMaxAscent = new int[4];
      this.mMaxDescent = new int[4];
    }
    int[] arrayOfInt = this.mMaxAscent;
    Object localObject1 = this.mMaxDescent;
    arrayOfInt[3] = -1;
    arrayOfInt[2] = -1;
    arrayOfInt[1] = -1;
    arrayOfInt[0] = -1;
    localObject1[3] = -1;
    localObject1[2] = -1;
    localObject1[1] = -1;
    localObject1[0] = -1;
    boolean bool1 = this.mBaselineAligned;
    boolean bool2 = this.mUseLargestChild;
    int i7;
    if (i2 == 1073741824) {
      i7 = 1;
    } else {
      i7 = 0;
    }
    int i4 = 0;
    int i6 = 0;
    float f1 = 0.0F;
    int n = 0;
    int i = 0;
    int i5 = 0;
    int i1 = 0;
    int m = 1;
    int j = 0;
    int k = 0;
    Object localObject2;
    Object localObject3;
    int i9;
    int i11;
    while (i4 < i3)
    {
      localObject2 = getVirtualChildAt(i4);
      if (localObject2 == null)
      {
        this.mTotalLength += measureNullChild(i4);
      }
      else if (((View)localObject2).getVisibility() == 8)
      {
        i4 += getChildrenSkipCount((View)localObject2, i4);
      }
      else
      {
        if (hasDividerBeforeChildAt(i4)) {
          this.mTotalLength += this.mDividerWidth;
        }
        localObject3 = (LayoutParams)((View)localObject2).getLayoutParams();
        f1 += ((LayoutParams)localObject3).weight;
        if ((i2 == 1073741824) && (((LayoutParams)localObject3).width == 0) && (((LayoutParams)localObject3).weight > 0.0F))
        {
          if (i7 != 0)
          {
            this.mTotalLength += ((LayoutParams)localObject3).leftMargin + ((LayoutParams)localObject3).rightMargin;
          }
          else
          {
            i8 = this.mTotalLength;
            this.mTotalLength = Math.max(i8, ((LayoutParams)localObject3).leftMargin + i8 + ((LayoutParams)localObject3).rightMargin);
          }
          if (bool1)
          {
            i8 = View.MeasureSpec.makeMeasureSpec(0, 0);
            ((View)localObject2).measure(i8, i8);
            i8 = j;
          }
          else
          {
            i5 = 1;
            i8 = j;
          }
        }
        else
        {
          if ((((LayoutParams)localObject3).width == 0) && (((LayoutParams)localObject3).weight > 0.0F))
          {
            ((LayoutParams)localObject3).width = -2;
            i8 = 0;
          }
          else
          {
            i8 = Integer.MIN_VALUE;
          }
          if (f1 == 0.0F) {
            i9 = this.mTotalLength;
          } else {
            i9 = 0;
          }
          i10 = j;
          measureChildBeforeLayout((View)localObject2, i4, paramInt1, i9, paramInt2, 0);
          if (i8 != Integer.MIN_VALUE) {
            ((LayoutParams)localObject3).width = i8;
          }
          i9 = ((View)localObject2).getMeasuredWidth();
          if (i7 != 0)
          {
            this.mTotalLength += ((LayoutParams)localObject3).leftMargin + i9 + ((LayoutParams)localObject3).rightMargin + getNextLocationOffset((View)localObject2);
          }
          else
          {
            i8 = this.mTotalLength;
            this.mTotalLength = Math.max(i8, i8 + i9 + ((LayoutParams)localObject3).leftMargin + ((LayoutParams)localObject3).rightMargin + getNextLocationOffset((View)localObject2));
          }
          if (bool2) {
            i = Math.max(i9, i);
          }
        }
        i8 = j;
        i10 = 0;
        i9 = i10;
        j = i1;
        if (i12 != 1073741824)
        {
          i9 = i10;
          j = i1;
          if (((LayoutParams)localObject3).height == -1)
          {
            j = 1;
            i9 = 1;
          }
        }
        i1 = ((LayoutParams)localObject3).topMargin + ((LayoutParams)localObject3).bottomMargin;
        i10 = ((View)localObject2).getMeasuredHeight() + i1;
        i11 = View.combineMeasuredStates(n, ((View)localObject2).getMeasuredState());
        if (bool1)
        {
          int i13 = ((View)localObject2).getBaseline();
          if (i13 != -1)
          {
            if (((LayoutParams)localObject3).gravity < 0) {
              n = this.mGravity;
            } else {
              n = ((LayoutParams)localObject3).gravity;
            }
            n = ((n & 0x70) >> 4 & 0xFFFFFFFE) >> 1;
            arrayOfInt[n] = Math.max(arrayOfInt[n], i13);
            localObject1[n] = Math.max(localObject1[n], i10 - i13);
          }
          else {}
        }
        i6 = Math.max(i6, i10);
        if ((m != 0) && (((LayoutParams)localObject3).height == -1)) {
          m = 1;
        } else {
          m = 0;
        }
        if (((LayoutParams)localObject3).weight > 0.0F)
        {
          if (i9 != 0) {
            i10 = i1;
          }
          k = Math.max(k, i10);
          n = i8;
        }
        else
        {
          if (i9 != 0) {
            n = i1;
          } else {
            n = i10;
          }
          n = Math.max(i8, n);
        }
        i4 += getChildrenSkipCount((View)localObject2, i4);
        i8 = i11;
        i9 = n;
        i1 = j;
        n = i8;
        j = i9;
      }
      i4++;
    }
    i4 = k;
    int i8 = i;
    if ((this.mTotalLength > 0) && (hasDividerBeforeChildAt(i3))) {
      this.mTotalLength += this.mDividerWidth;
    }
    if ((arrayOfInt[1] == -1) && (arrayOfInt[0] == -1) && (arrayOfInt[2] == -1) && (arrayOfInt[3] == -1)) {
      i = i6;
    } else {
      i = Math.max(i6, Math.max(arrayOfInt[3], Math.max(arrayOfInt[0], Math.max(arrayOfInt[1], arrayOfInt[2]))) + Math.max(localObject1[3], Math.max(localObject1[0], Math.max(localObject1[1], localObject1[2]))));
    }
    if (bool2)
    {
      if ((i2 != Integer.MIN_VALUE) && (i2 != 0)) {
        break label1215;
      }
      this.mTotalLength = 0;
      for (k = 0; k < i3; k++)
      {
        localObject3 = getVirtualChildAt(k);
        if (localObject3 == null)
        {
          this.mTotalLength += measureNullChild(k);
        }
        else if (((View)localObject3).getVisibility() == 8)
        {
          k += getChildrenSkipCount((View)localObject3, k);
        }
        else
        {
          localObject2 = (LayoutParams)((View)localObject3).getLayoutParams();
          if (i7 != 0)
          {
            this.mTotalLength += ((LayoutParams)localObject2).leftMargin + i8 + ((LayoutParams)localObject2).rightMargin + getNextLocationOffset((View)localObject3);
          }
          else
          {
            i6 = this.mTotalLength;
            this.mTotalLength = Math.max(i6, i6 + i8 + ((LayoutParams)localObject2).leftMargin + ((LayoutParams)localObject2).rightMargin + getNextLocationOffset((View)localObject3));
          }
        }
      }
    }
    label1215:
    i6 = i2;
    this.mTotalLength += getPaddingLeft() + getPaddingRight();
    k = View.resolveSizeAndState(Math.max(this.mTotalLength, getSuggestedMinimumWidth()), paramInt1, 0);
    i2 = k & 0xFFFFFF;
    int i10 = i2 - this.mTotalLength;
    if ((i5 == 0) && ((i10 == 0) || (f1 <= 0.0F)))
    {
      j = Math.max(j, i4);
      if (bool2) {
        if (i6 != 1073741824) {
          for (i4 = 0; i4 < i3; i4++)
          {
            localObject1 = getVirtualChildAt(i4);
            if ((localObject1 != null) && (((View)localObject1).getVisibility() != 8)) {
              if (((LayoutParams)((View)localObject1).getLayoutParams()).weight > 0.0F) {
                ((View)localObject1).measure(View.MeasureSpec.makeMeasureSpec(i8, 1073741824), View.MeasureSpec.makeMeasureSpec(((View)localObject1).getMeasuredHeight(), 1073741824));
              } else {}
            }
          }
        } else {}
      }
      i2 = j;
      j = i;
      i = i2;
      i2 = n;
    }
    else
    {
      i8 = j;
      float f2 = this.mWeightSum;
      if (f2 > 0.0F) {
        f1 = f2;
      }
      arrayOfInt[3] = -1;
      arrayOfInt[2] = -1;
      arrayOfInt[1] = -1;
      arrayOfInt[0] = -1;
      localObject1[3] = -1;
      localObject1[2] = -1;
      localObject1[1] = -1;
      localObject1[0] = -1;
      this.mTotalLength = 0;
      i9 = 0;
      i = i10;
      i2 = -1;
      j = n;
      i5 = i6;
      n = i8;
      for (i6 = i9; i6 < i3; i6++)
      {
        localObject2 = getVirtualChildAt(i6);
        if ((localObject2 != null) && (((View)localObject2).getVisibility() != 8))
        {
          localObject3 = (LayoutParams)((View)localObject2).getLayoutParams();
          f2 = ((LayoutParams)localObject3).weight;
          if (f2 > 0.0F)
          {
            i9 = (int)(i * f2 / f1);
            i11 = getChildMeasureSpec(paramInt2, getPaddingTop() + getPaddingBottom() + ((LayoutParams)localObject3).topMargin + ((LayoutParams)localObject3).bottomMargin, ((LayoutParams)localObject3).height);
            if ((((LayoutParams)localObject3).width == 0) && (i5 == 1073741824))
            {
              if (i9 > 0) {
                i8 = i9;
              } else {
                i8 = 0;
              }
              ((View)localObject2).measure(View.MeasureSpec.makeMeasureSpec(i8, 1073741824), i11);
            }
            else
            {
              i10 = ((View)localObject2).getMeasuredWidth() + i9;
              i8 = i10;
              if (i10 < 0) {
                i8 = 0;
              }
              ((View)localObject2).measure(View.MeasureSpec.makeMeasureSpec(i8, 1073741824), i11);
            }
            j = View.combineMeasuredStates(j, ((View)localObject2).getMeasuredState() & 0xFF000000);
            f1 -= f2;
            i -= i9;
          }
          if (i7 != 0)
          {
            this.mTotalLength += ((View)localObject2).getMeasuredWidth() + ((LayoutParams)localObject3).leftMargin + ((LayoutParams)localObject3).rightMargin + getNextLocationOffset((View)localObject2);
          }
          else
          {
            i8 = this.mTotalLength;
            this.mTotalLength = Math.max(i8, ((View)localObject2).getMeasuredWidth() + i8 + ((LayoutParams)localObject3).leftMargin + ((LayoutParams)localObject3).rightMargin + getNextLocationOffset((View)localObject2));
          }
          if ((i12 != 1073741824) && (((LayoutParams)localObject3).height == -1)) {
            i8 = 1;
          } else {
            i8 = 0;
          }
          i11 = ((LayoutParams)localObject3).topMargin + ((LayoutParams)localObject3).bottomMargin;
          i10 = ((View)localObject2).getMeasuredHeight() + i11;
          i9 = Math.max(i2, i10);
          if (i8 != 0) {
            i2 = i11;
          } else {
            i2 = i10;
          }
          i2 = Math.max(n, i2);
          if ((m != 0) && (((LayoutParams)localObject3).height == -1)) {
            m = 1;
          } else {
            m = 0;
          }
          if (bool1)
          {
            i8 = ((View)localObject2).getBaseline();
            if (i8 != -1)
            {
              if (((LayoutParams)localObject3).gravity < 0) {
                n = this.mGravity;
              } else {
                n = ((LayoutParams)localObject3).gravity;
              }
              n = ((n & 0x70) >> 4 & 0xFFFFFFFE) >> 1;
              arrayOfInt[n] = Math.max(arrayOfInt[n], i8);
              localObject1[n] = Math.max(localObject1[n], i10 - i8);
            }
            else {}
          }
          n = i2;
          i2 = i9;
        }
      }
      this.mTotalLength += getPaddingLeft() + getPaddingRight();
      if ((arrayOfInt[1] == -1) && (arrayOfInt[0] == -1) && (arrayOfInt[2] == -1) && (arrayOfInt[3] == -1)) {
        i = i2;
      } else {
        i = Math.max(i2, Math.max(arrayOfInt[3], Math.max(arrayOfInt[0], Math.max(arrayOfInt[1], arrayOfInt[2]))) + Math.max(localObject1[3], Math.max(localObject1[0], Math.max(localObject1[1], localObject1[2]))));
      }
      i2 = j;
      j = i;
      i = n;
    }
    n = j;
    if (m == 0)
    {
      n = j;
      if (i12 != 1073741824) {
        n = i;
      }
    }
    setMeasuredDimension(k | 0xFF000000 & i2, View.resolveSizeAndState(Math.max(n + (getPaddingTop() + getPaddingBottom()), getSuggestedMinimumHeight()), paramInt2, i2 << 16));
    if (i1 != 0) {
      forceUniformHeight(i3, paramInt1);
    }
  }
  
  int measureNullChild(int paramInt)
  {
    return 0;
  }
  
  void measureVertical(int paramInt1, int paramInt2)
  {
    this.mTotalLength = 0;
    int i11 = getVirtualChildCount();
    int i12 = View.MeasureSpec.getMode(paramInt1);
    int i8 = View.MeasureSpec.getMode(paramInt2);
    int i9 = this.mBaselineAlignedChildIndex;
    boolean bool = this.mUseLargestChild;
    int i2 = 0;
    int m = 0;
    float f1 = 0.0F;
    int n = 0;
    int i3 = 0;
    int i1 = 0;
    int i4 = 0;
    int i = 0;
    int j = 0;
    int k = 1;
    View localView;
    LayoutParams localLayoutParams;
    int i6;
    int i7;
    while (i3 < i11)
    {
      localView = getVirtualChildAt(i3);
      if (localView == null)
      {
        this.mTotalLength += measureNullChild(i3);
      }
      else if (localView.getVisibility() == 8)
      {
        i3 += getChildrenSkipCount(localView, i3);
      }
      else
      {
        if (hasDividerBeforeChildAt(i3)) {
          this.mTotalLength += this.mDividerHeight;
        }
        localLayoutParams = (LayoutParams)localView.getLayoutParams();
        f1 += localLayoutParams.weight;
        if ((i8 == 1073741824) && (localLayoutParams.height == 0) && (localLayoutParams.weight > 0.0F))
        {
          i2 = this.mTotalLength;
          this.mTotalLength = Math.max(i2, localLayoutParams.topMargin + i2 + localLayoutParams.bottomMargin);
          i2 = 1;
        }
        else
        {
          if ((localLayoutParams.height == 0) && (localLayoutParams.weight > 0.0F))
          {
            localLayoutParams.height = -2;
            i5 = 0;
          }
          else
          {
            i5 = Integer.MIN_VALUE;
          }
          if (f1 == 0.0F) {
            i6 = this.mTotalLength;
          } else {
            i6 = 0;
          }
          measureChildBeforeLayout(localView, i3, paramInt1, 0, paramInt2, i6);
          if (i5 != Integer.MIN_VALUE) {
            localLayoutParams.height = i5;
          }
          i5 = localView.getMeasuredHeight();
          i6 = this.mTotalLength;
          this.mTotalLength = Math.max(i6, i6 + i5 + localLayoutParams.topMargin + localLayoutParams.bottomMargin + getNextLocationOffset(localView));
          if (bool) {
            j = Math.max(i5, j);
          }
        }
        if ((i9 >= 0) && (i9 == i3 + 1)) {
          this.mBaselineChildTop = this.mTotalLength;
        }
        if ((i3 < i9) && (localLayoutParams.weight > 0.0F)) {
          throw new RuntimeException("A child of LinearLayout with index less than mBaselineAlignedChildIndex has weight > 0, which won't work.  Either remove the weight, or don't set mBaselineAlignedChildIndex.");
        }
        i5 = 0;
        if ((i12 != 1073741824) && (localLayoutParams.width == -1))
        {
          i1 = 1;
          i5 = 1;
        }
        i7 = localLayoutParams.leftMargin + localLayoutParams.rightMargin;
        i6 = localView.getMeasuredWidth() + i7;
        i10 = Math.max(m, i6);
        i4 = View.combineMeasuredStates(i4, localView.getMeasuredState());
        if ((k != 0) && (localLayoutParams.width == -1)) {
          k = 1;
        } else {
          k = 0;
        }
        if (localLayoutParams.weight > 0.0F)
        {
          if (i5 != 0) {
            i6 = i7;
          }
          i = Math.max(i, i6);
          m = n;
        }
        else
        {
          if (i5 != 0) {
            m = i7;
          } else {
            m = i6;
          }
          m = Math.max(n, m);
        }
        i3 += getChildrenSkipCount(localView, i3);
        n = m;
        m = i10;
      }
      i3++;
    }
    int i5 = j;
    j = i4;
    i3 = i;
    if ((this.mTotalLength > 0) && (hasDividerBeforeChildAt(i11))) {
      this.mTotalLength += this.mDividerHeight;
    }
    if (bool)
    {
      if ((i8 != Integer.MIN_VALUE) && (i8 != 0))
      {
        i = j;
      }
      else
      {
        this.mTotalLength = 0;
        for (i = 0; i < i11; i++)
        {
          localView = getVirtualChildAt(i);
          if (localView == null)
          {
            this.mTotalLength += measureNullChild(i);
          }
          else if (localView.getVisibility() == 8)
          {
            i += getChildrenSkipCount(localView, i);
          }
          else
          {
            localLayoutParams = (LayoutParams)localView.getLayoutParams();
            i4 = this.mTotalLength;
            this.mTotalLength = Math.max(i4, i4 + i5 + localLayoutParams.topMargin + localLayoutParams.bottomMargin + getNextLocationOffset(localView));
          }
        }
        i = j;
      }
    }
    else {
      i = j;
    }
    this.mTotalLength += getPaddingTop() + getPaddingBottom();
    int i10 = View.resolveSizeAndState(Math.max(this.mTotalLength, getSuggestedMinimumHeight()), paramInt2, 0);
    i4 = i10 & 0xFFFFFF;
    j = i4 - this.mTotalLength;
    if ((i2 == 0) && ((j == 0) || (f1 <= 0.0F)))
    {
      n = Math.max(n, i3);
      if ((bool) && (i8 != 1073741824))
      {
        i3 = 0;
        i2 = i4;
        while (i3 < i11)
        {
          localView = getVirtualChildAt(i3);
          if ((localView != null) && (localView.getVisibility() != 8)) {
            if (((LayoutParams)localView.getLayoutParams()).weight > 0.0F) {
              localView.measure(View.MeasureSpec.makeMeasureSpec(localView.getMeasuredWidth(), 1073741824), View.MeasureSpec.makeMeasureSpec(i5, 1073741824));
            } else {}
          }
          i3++;
        }
        i2 = n;
        n = j;
        j = i2;
        i2 = n;
      }
      else
      {
        i2 = j;
        j = n;
      }
      n = j;
      j = i;
      i = n;
    }
    else
    {
      float f2 = this.mWeightSum;
      if (f2 > 0.0F) {
        f1 = f2;
      }
      this.mTotalLength = 0;
      i7 = 0;
      i6 = n;
      n = j;
      i2 = i9;
      i4 = i8;
      j = i6;
      for (i6 = i7; i6 < i11; i6++)
      {
        localView = getVirtualChildAt(i6);
        if (localView.getVisibility() != 8)
        {
          localLayoutParams = (LayoutParams)localView.getLayoutParams();
          f2 = localLayoutParams.weight;
          if (f2 > 0.0F)
          {
            i8 = (int)(n * f2 / f1);
            int i13 = getChildMeasureSpec(paramInt1, getPaddingLeft() + getPaddingRight() + localLayoutParams.leftMargin + localLayoutParams.rightMargin, localLayoutParams.width);
            if ((localLayoutParams.height == 0) && (i4 == 1073741824))
            {
              if (i8 > 0) {
                i7 = i8;
              } else {
                i7 = 0;
              }
              localView.measure(i13, View.MeasureSpec.makeMeasureSpec(i7, 1073741824));
            }
            else
            {
              i9 = localView.getMeasuredHeight() + i8;
              i7 = i9;
              if (i9 < 0) {
                i7 = 0;
              }
              localView.measure(i13, View.MeasureSpec.makeMeasureSpec(i7, 1073741824));
            }
            i = View.combineMeasuredStates(i, localView.getMeasuredState() & 0xFF00);
            n -= i8;
            f1 -= f2;
          }
          i9 = localLayoutParams.leftMargin + localLayoutParams.rightMargin;
          i8 = localView.getMeasuredWidth() + i9;
          i7 = Math.max(m, i8);
          if ((i12 != 1073741824) && (localLayoutParams.width == -1)) {
            m = 1;
          } else {
            m = 0;
          }
          if (m != 0) {
            m = i9;
          } else {
            m = i8;
          }
          m = Math.max(j, m);
          if ((k != 0) && (localLayoutParams.width == -1)) {
            j = 1;
          } else {
            j = 0;
          }
          k = this.mTotalLength;
          this.mTotalLength = Math.max(k, k + localView.getMeasuredHeight() + localLayoutParams.topMargin + localLayoutParams.bottomMargin + getNextLocationOffset(localView));
          k = j;
          j = m;
          m = i7;
        }
      }
      this.mTotalLength += getPaddingTop() + getPaddingBottom();
      n = j;
      j = i;
      i = n;
    }
    n = m;
    if (k == 0)
    {
      n = m;
      if (i12 != 1073741824) {
        n = i;
      }
    }
    setMeasuredDimension(View.resolveSizeAndState(Math.max(n + (getPaddingLeft() + getPaddingRight()), getSuggestedMinimumWidth()), paramInt1, j), i10);
    if (i1 != 0) {
      forceUniformWidth(i11, paramInt2);
    }
  }
  
  protected void onDraw(Canvas paramCanvas)
  {
    if (this.mDivider == null) {
      return;
    }
    if (this.mOrientation == 1) {
      drawDividersVertical(paramCanvas);
    } else {
      drawDividersHorizontal(paramCanvas);
    }
  }
  
  public void onInitializeAccessibilityEvent(AccessibilityEvent paramAccessibilityEvent)
  {
    super.onInitializeAccessibilityEvent(paramAccessibilityEvent);
    paramAccessibilityEvent.setClassName("androidx.appcompat.widget.LinearLayoutCompat");
  }
  
  public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo paramAccessibilityNodeInfo)
  {
    super.onInitializeAccessibilityNodeInfo(paramAccessibilityNodeInfo);
    paramAccessibilityNodeInfo.setClassName("androidx.appcompat.widget.LinearLayoutCompat");
  }
  
  protected void onLayout(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    if (this.mOrientation == 1) {
      layoutVertical(paramInt1, paramInt2, paramInt3, paramInt4);
    } else {
      layoutHorizontal(paramInt1, paramInt2, paramInt3, paramInt4);
    }
  }
  
  protected void onMeasure(int paramInt1, int paramInt2)
  {
    if (this.mOrientation == 1) {
      measureVertical(paramInt1, paramInt2);
    } else {
      measureHorizontal(paramInt1, paramInt2);
    }
  }
  
  public void setBaselineAligned(boolean paramBoolean)
  {
    this.mBaselineAligned = paramBoolean;
  }
  
  public void setBaselineAlignedChildIndex(int paramInt)
  {
    if ((paramInt >= 0) && (paramInt < getChildCount()))
    {
      this.mBaselineAlignedChildIndex = paramInt;
      return;
    }
    throw new IllegalArgumentException("base aligned child index out of range (0, " + getChildCount() + ")");
  }
  
  public void setDividerDrawable(Drawable paramDrawable)
  {
    if (paramDrawable == this.mDivider) {
      return;
    }
    this.mDivider = paramDrawable;
    boolean bool = false;
    if (paramDrawable != null)
    {
      this.mDividerWidth = paramDrawable.getIntrinsicWidth();
      this.mDividerHeight = paramDrawable.getIntrinsicHeight();
    }
    else
    {
      this.mDividerWidth = 0;
      this.mDividerHeight = 0;
    }
    if (paramDrawable == null) {
      bool = true;
    }
    setWillNotDraw(bool);
    requestLayout();
  }
  
  public void setDividerPadding(int paramInt)
  {
    this.mDividerPadding = paramInt;
  }
  
  public void setGravity(int paramInt)
  {
    if (this.mGravity != paramInt)
    {
      int i = paramInt;
      if ((0x800007 & paramInt) == 0) {
        i = paramInt | 0x800003;
      }
      paramInt = i;
      if ((i & 0x70) == 0) {
        paramInt = i | 0x30;
      }
      this.mGravity = paramInt;
      requestLayout();
    }
  }
  
  public void setHorizontalGravity(int paramInt)
  {
    paramInt &= 0x800007;
    int i = this.mGravity;
    if ((0x800007 & i) != paramInt)
    {
      this.mGravity = (0xFF7FFFF8 & i | paramInt);
      requestLayout();
    }
  }
  
  public void setMeasureWithLargestChildEnabled(boolean paramBoolean)
  {
    this.mUseLargestChild = paramBoolean;
  }
  
  public void setOrientation(int paramInt)
  {
    if (this.mOrientation != paramInt)
    {
      this.mOrientation = paramInt;
      requestLayout();
    }
  }
  
  public void setShowDividers(int paramInt)
  {
    if (paramInt != this.mShowDividers) {
      requestLayout();
    }
    this.mShowDividers = paramInt;
  }
  
  public void setVerticalGravity(int paramInt)
  {
    paramInt &= 0x70;
    int i = this.mGravity;
    if ((i & 0x70) != paramInt)
    {
      this.mGravity = (i & 0xFFFFFF8F | paramInt);
      requestLayout();
    }
  }
  
  public void setWeightSum(float paramFloat)
  {
    this.mWeightSum = Math.max(0.0F, paramFloat);
  }
  
  public boolean shouldDelayChildPressedState()
  {
    return false;
  }
  
  @Retention(RetentionPolicy.SOURCE)
  public static @interface DividerMode {}
  
  public static class LayoutParams
    extends LinearLayout.LayoutParams
  {
    public LayoutParams(int paramInt1, int paramInt2)
    {
      super(paramInt2);
    }
    
    public LayoutParams(int paramInt1, int paramInt2, float paramFloat)
    {
      super(paramInt2, paramFloat);
    }
    
    public LayoutParams(Context paramContext, AttributeSet paramAttributeSet)
    {
      super(paramAttributeSet);
    }
    
    public LayoutParams(ViewGroup.LayoutParams paramLayoutParams)
    {
      super();
    }
    
    public LayoutParams(ViewGroup.MarginLayoutParams paramMarginLayoutParams)
    {
      super();
    }
  }
  
  @Retention(RetentionPolicy.SOURCE)
  public static @interface OrientationMode {}
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/appcompat/widget/LinearLayoutCompat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */