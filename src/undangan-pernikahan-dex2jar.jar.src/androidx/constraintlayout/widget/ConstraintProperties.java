package androidx.constraintlayout.widget;

import android.os.Build.VERSION;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;

public class ConstraintProperties
{
  public static final int BASELINE = 5;
  public static final int BOTTOM = 4;
  public static final int END = 7;
  public static final int LEFT = 1;
  public static final int MATCH_CONSTRAINT = 0;
  public static final int MATCH_CONSTRAINT_SPREAD = 0;
  public static final int MATCH_CONSTRAINT_WRAP = 1;
  public static final int PARENT_ID = 0;
  public static final int RIGHT = 2;
  public static final int START = 6;
  public static final int TOP = 3;
  public static final int UNSET = -1;
  public static final int WRAP_CONTENT = -2;
  ConstraintLayout.LayoutParams mParams;
  View mView;
  
  public ConstraintProperties(View paramView)
  {
    ViewGroup.LayoutParams localLayoutParams = paramView.getLayoutParams();
    if ((localLayoutParams instanceof ConstraintLayout.LayoutParams))
    {
      this.mParams = ((ConstraintLayout.LayoutParams)localLayoutParams);
      this.mView = paramView;
      return;
    }
    throw new RuntimeException("Only children of ConstraintLayout.LayoutParams supported");
  }
  
  private String sideToString(int paramInt)
  {
    switch (paramInt)
    {
    default: 
      return "undefined";
    case 7: 
      return "end";
    case 6: 
      return "start";
    case 5: 
      return "baseline";
    case 4: 
      return "bottom";
    case 3: 
      return "top";
    case 2: 
      return "right";
    }
    return "left";
  }
  
  public ConstraintProperties addToHorizontalChain(int paramInt1, int paramInt2)
  {
    int i;
    if (paramInt1 == 0) {
      i = 1;
    } else {
      i = 2;
    }
    connect(1, paramInt1, i, 0);
    if (paramInt2 == 0) {
      i = 2;
    } else {
      i = 1;
    }
    connect(2, paramInt2, i, 0);
    if (paramInt1 != 0) {
      new ConstraintProperties(((ViewGroup)this.mView.getParent()).findViewById(paramInt1)).connect(2, this.mView.getId(), 1, 0);
    }
    if (paramInt2 != 0) {
      new ConstraintProperties(((ViewGroup)this.mView.getParent()).findViewById(paramInt2)).connect(1, this.mView.getId(), 2, 0);
    }
    return this;
  }
  
  public ConstraintProperties addToHorizontalChainRTL(int paramInt1, int paramInt2)
  {
    int i;
    if (paramInt1 == 0) {
      i = 6;
    } else {
      i = 7;
    }
    connect(6, paramInt1, i, 0);
    if (paramInt2 == 0) {
      i = 7;
    } else {
      i = 6;
    }
    connect(7, paramInt2, i, 0);
    if (paramInt1 != 0) {
      new ConstraintProperties(((ViewGroup)this.mView.getParent()).findViewById(paramInt1)).connect(7, this.mView.getId(), 6, 0);
    }
    if (paramInt2 != 0) {
      new ConstraintProperties(((ViewGroup)this.mView.getParent()).findViewById(paramInt2)).connect(6, this.mView.getId(), 7, 0);
    }
    return this;
  }
  
  public ConstraintProperties addToVerticalChain(int paramInt1, int paramInt2)
  {
    int i;
    if (paramInt1 == 0) {
      i = 3;
    } else {
      i = 4;
    }
    connect(3, paramInt1, i, 0);
    if (paramInt2 == 0) {
      i = 4;
    } else {
      i = 3;
    }
    connect(4, paramInt2, i, 0);
    if (paramInt1 != 0) {
      new ConstraintProperties(((ViewGroup)this.mView.getParent()).findViewById(paramInt1)).connect(4, this.mView.getId(), 3, 0);
    }
    if (paramInt2 != 0) {
      new ConstraintProperties(((ViewGroup)this.mView.getParent()).findViewById(paramInt2)).connect(3, this.mView.getId(), 4, 0);
    }
    return this;
  }
  
  public ConstraintProperties alpha(float paramFloat)
  {
    this.mView.setAlpha(paramFloat);
    return this;
  }
  
  public void apply() {}
  
  public ConstraintProperties center(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, float paramFloat)
  {
    if (paramInt3 >= 0)
    {
      if (paramInt6 >= 0)
      {
        if ((paramFloat > 0.0F) && (paramFloat <= 1.0F))
        {
          if ((paramInt2 != 1) && (paramInt2 != 2))
          {
            if ((paramInt2 != 6) && (paramInt2 != 7))
            {
              connect(3, paramInt1, paramInt2, paramInt3);
              connect(4, paramInt4, paramInt5, paramInt6);
              this.mParams.verticalBias = paramFloat;
            }
            else
            {
              connect(6, paramInt1, paramInt2, paramInt3);
              connect(7, paramInt4, paramInt5, paramInt6);
              this.mParams.horizontalBias = paramFloat;
            }
          }
          else
          {
            connect(1, paramInt1, paramInt2, paramInt3);
            connect(2, paramInt4, paramInt5, paramInt6);
            this.mParams.horizontalBias = paramFloat;
          }
          return this;
        }
        throw new IllegalArgumentException("bias must be between 0 and 1 inclusive");
      }
      throw new IllegalArgumentException("margin must be > 0");
    }
    throw new IllegalArgumentException("margin must be > 0");
  }
  
  public ConstraintProperties centerHorizontally(int paramInt)
  {
    if (paramInt == 0) {
      center(0, 1, 0, 0, 2, 0, 0.5F);
    } else {
      center(paramInt, 2, 0, paramInt, 1, 0, 0.5F);
    }
    return this;
  }
  
  public ConstraintProperties centerHorizontally(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, float paramFloat)
  {
    connect(1, paramInt1, paramInt2, paramInt3);
    connect(2, paramInt4, paramInt5, paramInt6);
    this.mParams.horizontalBias = paramFloat;
    return this;
  }
  
  public ConstraintProperties centerHorizontallyRtl(int paramInt)
  {
    if (paramInt == 0) {
      center(0, 6, 0, 0, 7, 0, 0.5F);
    } else {
      center(paramInt, 7, 0, paramInt, 6, 0, 0.5F);
    }
    return this;
  }
  
  public ConstraintProperties centerHorizontallyRtl(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, float paramFloat)
  {
    connect(6, paramInt1, paramInt2, paramInt3);
    connect(7, paramInt4, paramInt5, paramInt6);
    this.mParams.horizontalBias = paramFloat;
    return this;
  }
  
  public ConstraintProperties centerVertically(int paramInt)
  {
    if (paramInt == 0) {
      center(0, 3, 0, 0, 4, 0, 0.5F);
    } else {
      center(paramInt, 4, 0, paramInt, 3, 0, 0.5F);
    }
    return this;
  }
  
  public ConstraintProperties centerVertically(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, float paramFloat)
  {
    connect(3, paramInt1, paramInt2, paramInt3);
    connect(4, paramInt4, paramInt5, paramInt6);
    this.mParams.verticalBias = paramFloat;
    return this;
  }
  
  public ConstraintProperties connect(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    switch (paramInt1)
    {
    default: 
      throw new IllegalArgumentException(sideToString(paramInt1) + " to " + sideToString(paramInt3) + " unknown");
    case 7: 
      if (paramInt3 == 7)
      {
        this.mParams.endToEnd = paramInt2;
        this.mParams.endToStart = -1;
      }
      else
      {
        if (paramInt3 != 6) {
          break label155;
        }
        this.mParams.endToStart = paramInt2;
        this.mParams.endToEnd = -1;
      }
      if (Build.VERSION.SDK_INT >= 17)
      {
        this.mParams.setMarginEnd(paramInt4);
        break;
        throw new IllegalArgumentException("right to " + sideToString(paramInt3) + " undefined");
      }
      break;
    case 6: 
      if (paramInt3 == 6)
      {
        this.mParams.startToStart = paramInt2;
        this.mParams.startToEnd = -1;
      }
      else
      {
        if (paramInt3 != 7) {
          break label258;
        }
        this.mParams.startToEnd = paramInt2;
        this.mParams.startToStart = -1;
      }
      if (Build.VERSION.SDK_INT >= 17)
      {
        this.mParams.setMarginStart(paramInt4);
        break;
        throw new IllegalArgumentException("right to " + sideToString(paramInt3) + " undefined");
      }
      break;
    case 5: 
      if (paramInt3 == 5)
      {
        this.mParams.baselineToBaseline = paramInt2;
        this.mParams.bottomToBottom = -1;
        this.mParams.bottomToTop = -1;
        this.mParams.topToTop = -1;
        this.mParams.topToBottom = -1;
      }
      if (paramInt3 == 3)
      {
        this.mParams.baselineToTop = paramInt2;
        this.mParams.bottomToBottom = -1;
        this.mParams.bottomToTop = -1;
        this.mParams.topToTop = -1;
        this.mParams.topToBottom = -1;
      }
      else
      {
        if (paramInt3 != 4) {
          break label444;
        }
        this.mParams.baselineToBottom = paramInt2;
        this.mParams.bottomToBottom = -1;
        this.mParams.bottomToTop = -1;
        this.mParams.topToTop = -1;
        this.mParams.topToBottom = -1;
      }
      this.mParams.baselineMargin = paramInt4;
      break;
      throw new IllegalArgumentException("right to " + sideToString(paramInt3) + " undefined");
    case 4: 
      if (paramInt3 == 4)
      {
        this.mParams.bottomToBottom = paramInt2;
        this.mParams.bottomToTop = -1;
        this.mParams.baselineToBaseline = -1;
        this.mParams.baselineToTop = -1;
        this.mParams.baselineToBottom = -1;
      }
      else
      {
        if (paramInt3 != 3) {
          break label585;
        }
        this.mParams.bottomToTop = paramInt2;
        this.mParams.bottomToBottom = -1;
        this.mParams.baselineToBaseline = -1;
        this.mParams.baselineToTop = -1;
        this.mParams.baselineToBottom = -1;
      }
      this.mParams.bottomMargin = paramInt4;
      break;
      throw new IllegalArgumentException("right to " + sideToString(paramInt3) + " undefined");
    case 3: 
      if (paramInt3 == 3)
      {
        this.mParams.topToTop = paramInt2;
        this.mParams.topToBottom = -1;
        this.mParams.baselineToBaseline = -1;
        this.mParams.baselineToTop = -1;
        this.mParams.baselineToBottom = -1;
      }
      else
      {
        if (paramInt3 != 4) {
          break label726;
        }
        this.mParams.topToBottom = paramInt2;
        this.mParams.topToTop = -1;
        this.mParams.baselineToBaseline = -1;
        this.mParams.baselineToTop = -1;
        this.mParams.baselineToBottom = -1;
      }
      this.mParams.topMargin = paramInt4;
      break;
      throw new IllegalArgumentException("right to " + sideToString(paramInt3) + " undefined");
    case 2: 
      if (paramInt3 == 1)
      {
        this.mParams.rightToLeft = paramInt2;
        this.mParams.rightToRight = -1;
      }
      else
      {
        if (paramInt3 != 2) {
          break label819;
        }
        this.mParams.rightToRight = paramInt2;
        this.mParams.rightToLeft = -1;
      }
      this.mParams.rightMargin = paramInt4;
      break;
      throw new IllegalArgumentException("right to " + sideToString(paramInt3) + " undefined");
    case 1: 
      label155:
      label258:
      label444:
      label585:
      label726:
      label819:
      if (paramInt3 == 1)
      {
        this.mParams.leftToLeft = paramInt2;
        this.mParams.leftToRight = -1;
      }
      else
      {
        if (paramInt3 != 2) {
          break label911;
        }
        this.mParams.leftToRight = paramInt2;
        this.mParams.leftToLeft = -1;
      }
      this.mParams.leftMargin = paramInt4;
    }
    return this;
    label911:
    throw new IllegalArgumentException("Left to " + sideToString(paramInt3) + " undefined");
  }
  
  public ConstraintProperties constrainDefaultHeight(int paramInt)
  {
    this.mParams.matchConstraintDefaultHeight = paramInt;
    return this;
  }
  
  public ConstraintProperties constrainDefaultWidth(int paramInt)
  {
    this.mParams.matchConstraintDefaultWidth = paramInt;
    return this;
  }
  
  public ConstraintProperties constrainHeight(int paramInt)
  {
    this.mParams.height = paramInt;
    return this;
  }
  
  public ConstraintProperties constrainMaxHeight(int paramInt)
  {
    this.mParams.matchConstraintMaxHeight = paramInt;
    return this;
  }
  
  public ConstraintProperties constrainMaxWidth(int paramInt)
  {
    this.mParams.matchConstraintMaxWidth = paramInt;
    return this;
  }
  
  public ConstraintProperties constrainMinHeight(int paramInt)
  {
    this.mParams.matchConstraintMinHeight = paramInt;
    return this;
  }
  
  public ConstraintProperties constrainMinWidth(int paramInt)
  {
    this.mParams.matchConstraintMinWidth = paramInt;
    return this;
  }
  
  public ConstraintProperties constrainWidth(int paramInt)
  {
    this.mParams.width = paramInt;
    return this;
  }
  
  public ConstraintProperties dimensionRatio(String paramString)
  {
    this.mParams.dimensionRatio = paramString;
    return this;
  }
  
  public ConstraintProperties elevation(float paramFloat)
  {
    if (Build.VERSION.SDK_INT >= 21) {
      this.mView.setElevation(paramFloat);
    }
    return this;
  }
  
  public ConstraintProperties goneMargin(int paramInt1, int paramInt2)
  {
    switch (paramInt1)
    {
    default: 
      throw new IllegalArgumentException("unknown constraint");
    case 7: 
      this.mParams.goneEndMargin = paramInt2;
      break;
    case 6: 
      this.mParams.goneStartMargin = paramInt2;
      break;
    case 5: 
      throw new IllegalArgumentException("baseline does not support margins");
    case 4: 
      this.mParams.goneBottomMargin = paramInt2;
      break;
    case 3: 
      this.mParams.goneTopMargin = paramInt2;
      break;
    case 2: 
      this.mParams.goneRightMargin = paramInt2;
      break;
    case 1: 
      this.mParams.goneLeftMargin = paramInt2;
    }
    return this;
  }
  
  public ConstraintProperties horizontalBias(float paramFloat)
  {
    this.mParams.horizontalBias = paramFloat;
    return this;
  }
  
  public ConstraintProperties horizontalChainStyle(int paramInt)
  {
    this.mParams.horizontalChainStyle = paramInt;
    return this;
  }
  
  public ConstraintProperties horizontalWeight(float paramFloat)
  {
    this.mParams.horizontalWeight = paramFloat;
    return this;
  }
  
  public ConstraintProperties margin(int paramInt1, int paramInt2)
  {
    switch (paramInt1)
    {
    default: 
      throw new IllegalArgumentException("unknown constraint");
    case 7: 
      this.mParams.setMarginEnd(paramInt2);
      break;
    case 6: 
      this.mParams.setMarginStart(paramInt2);
      break;
    case 5: 
      throw new IllegalArgumentException("baseline does not support margins");
    case 4: 
      this.mParams.bottomMargin = paramInt2;
      break;
    case 3: 
      this.mParams.topMargin = paramInt2;
      break;
    case 2: 
      this.mParams.rightMargin = paramInt2;
      break;
    case 1: 
      this.mParams.leftMargin = paramInt2;
    }
    return this;
  }
  
  public ConstraintProperties removeConstraints(int paramInt)
  {
    switch (paramInt)
    {
    default: 
      throw new IllegalArgumentException("unknown constraint");
    case 7: 
      this.mParams.endToStart = -1;
      this.mParams.endToEnd = -1;
      this.mParams.setMarginEnd(-1);
      this.mParams.goneEndMargin = Integer.MIN_VALUE;
      break;
    case 6: 
      this.mParams.startToEnd = -1;
      this.mParams.startToStart = -1;
      this.mParams.setMarginStart(-1);
      this.mParams.goneStartMargin = Integer.MIN_VALUE;
      break;
    case 5: 
      this.mParams.baselineToBaseline = -1;
      break;
    case 4: 
      this.mParams.bottomToTop = -1;
      this.mParams.bottomToBottom = -1;
      this.mParams.bottomMargin = -1;
      this.mParams.goneBottomMargin = Integer.MIN_VALUE;
      break;
    case 3: 
      this.mParams.topToBottom = -1;
      this.mParams.topToTop = -1;
      this.mParams.topMargin = -1;
      this.mParams.goneTopMargin = Integer.MIN_VALUE;
      break;
    case 2: 
      this.mParams.rightToRight = -1;
      this.mParams.rightToLeft = -1;
      this.mParams.rightMargin = -1;
      this.mParams.goneRightMargin = Integer.MIN_VALUE;
      break;
    case 1: 
      this.mParams.leftToRight = -1;
      this.mParams.leftToLeft = -1;
      this.mParams.leftMargin = -1;
      this.mParams.goneLeftMargin = Integer.MIN_VALUE;
    }
    return this;
  }
  
  public ConstraintProperties removeFromHorizontalChain()
  {
    int i = this.mParams.leftToRight;
    int j = this.mParams.rightToLeft;
    Object localObject1 = this.mParams;
    Object localObject2;
    ConstraintLayout.LayoutParams localLayoutParams;
    if ((i == -1) && (j == -1))
    {
      int k = ((ConstraintLayout.LayoutParams)localObject1).startToEnd;
      j = this.mParams.endToStart;
      if ((k != -1) || (j != -1))
      {
        localObject2 = new ConstraintProperties(((ViewGroup)this.mView.getParent()).findViewById(k));
        localObject1 = new ConstraintProperties(((ViewGroup)this.mView.getParent()).findViewById(j));
        localLayoutParams = this.mParams;
        if ((k != -1) && (j != -1))
        {
          ((ConstraintProperties)localObject2).connect(7, j, 6, 0);
          ((ConstraintProperties)localObject1).connect(6, i, 7, 0);
        }
        else if ((i != -1) || (j != -1))
        {
          i = localLayoutParams.rightToRight;
          localLayoutParams = this.mParams;
          if (i != -1)
          {
            ((ConstraintProperties)localObject2).connect(7, localLayoutParams.rightToRight, 7, 0);
          }
          else
          {
            i = localLayoutParams.leftToLeft;
            localObject2 = this.mParams;
            if (i != -1) {
              ((ConstraintProperties)localObject1).connect(6, ((ConstraintLayout.LayoutParams)localObject2).leftToLeft, 6, 0);
            }
          }
        }
      }
      removeConstraints(6);
      removeConstraints(7);
    }
    else
    {
      localObject2 = new ConstraintProperties(((ViewGroup)this.mView.getParent()).findViewById(i));
      localObject1 = new ConstraintProperties(((ViewGroup)this.mView.getParent()).findViewById(j));
      localLayoutParams = this.mParams;
      if ((i != -1) && (j != -1))
      {
        ((ConstraintProperties)localObject2).connect(2, j, 1, 0);
        ((ConstraintProperties)localObject1).connect(1, i, 2, 0);
      }
      else if ((i != -1) || (j != -1))
      {
        i = localLayoutParams.rightToRight;
        localLayoutParams = this.mParams;
        if (i != -1)
        {
          ((ConstraintProperties)localObject2).connect(2, localLayoutParams.rightToRight, 2, 0);
        }
        else
        {
          i = localLayoutParams.leftToLeft;
          localObject2 = this.mParams;
          if (i != -1) {
            ((ConstraintProperties)localObject1).connect(1, ((ConstraintLayout.LayoutParams)localObject2).leftToLeft, 1, 0);
          }
        }
      }
      removeConstraints(1);
      removeConstraints(2);
    }
    return this;
  }
  
  public ConstraintProperties removeFromVerticalChain()
  {
    int i = this.mParams.topToBottom;
    int j = this.mParams.bottomToTop;
    if ((i != -1) || (j != -1))
    {
      Object localObject = new ConstraintProperties(((ViewGroup)this.mView.getParent()).findViewById(i));
      ConstraintProperties localConstraintProperties = new ConstraintProperties(((ViewGroup)this.mView.getParent()).findViewById(j));
      ConstraintLayout.LayoutParams localLayoutParams = this.mParams;
      if ((i != -1) && (j != -1))
      {
        ((ConstraintProperties)localObject).connect(4, j, 3, 0);
        localConstraintProperties.connect(3, i, 4, 0);
      }
      else if ((i != -1) || (j != -1))
      {
        i = localLayoutParams.bottomToBottom;
        localLayoutParams = this.mParams;
        if (i != -1)
        {
          ((ConstraintProperties)localObject).connect(4, localLayoutParams.bottomToBottom, 4, 0);
        }
        else
        {
          i = localLayoutParams.topToTop;
          localObject = this.mParams;
          if (i != -1) {
            localConstraintProperties.connect(3, ((ConstraintLayout.LayoutParams)localObject).topToTop, 3, 0);
          }
        }
      }
    }
    removeConstraints(3);
    removeConstraints(4);
    return this;
  }
  
  public ConstraintProperties rotation(float paramFloat)
  {
    this.mView.setRotation(paramFloat);
    return this;
  }
  
  public ConstraintProperties rotationX(float paramFloat)
  {
    this.mView.setRotationX(paramFloat);
    return this;
  }
  
  public ConstraintProperties rotationY(float paramFloat)
  {
    this.mView.setRotationY(paramFloat);
    return this;
  }
  
  public ConstraintProperties scaleX(float paramFloat)
  {
    this.mView.setScaleY(paramFloat);
    return this;
  }
  
  public ConstraintProperties scaleY(float paramFloat)
  {
    return this;
  }
  
  public ConstraintProperties transformPivot(float paramFloat1, float paramFloat2)
  {
    this.mView.setPivotX(paramFloat1);
    this.mView.setPivotY(paramFloat2);
    return this;
  }
  
  public ConstraintProperties transformPivotX(float paramFloat)
  {
    this.mView.setPivotX(paramFloat);
    return this;
  }
  
  public ConstraintProperties transformPivotY(float paramFloat)
  {
    this.mView.setPivotY(paramFloat);
    return this;
  }
  
  public ConstraintProperties translation(float paramFloat1, float paramFloat2)
  {
    this.mView.setTranslationX(paramFloat1);
    this.mView.setTranslationY(paramFloat2);
    return this;
  }
  
  public ConstraintProperties translationX(float paramFloat)
  {
    this.mView.setTranslationX(paramFloat);
    return this;
  }
  
  public ConstraintProperties translationY(float paramFloat)
  {
    this.mView.setTranslationY(paramFloat);
    return this;
  }
  
  public ConstraintProperties translationZ(float paramFloat)
  {
    if (Build.VERSION.SDK_INT >= 21) {
      this.mView.setTranslationZ(paramFloat);
    }
    return this;
  }
  
  public ConstraintProperties verticalBias(float paramFloat)
  {
    this.mParams.verticalBias = paramFloat;
    return this;
  }
  
  public ConstraintProperties verticalChainStyle(int paramInt)
  {
    this.mParams.verticalChainStyle = paramInt;
    return this;
  }
  
  public ConstraintProperties verticalWeight(float paramFloat)
  {
    this.mParams.verticalWeight = paramFloat;
    return this;
  }
  
  public ConstraintProperties visibility(int paramInt)
  {
    this.mView.setVisibility(paramInt);
    return this;
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/constraintlayout/widget/ConstraintProperties.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */