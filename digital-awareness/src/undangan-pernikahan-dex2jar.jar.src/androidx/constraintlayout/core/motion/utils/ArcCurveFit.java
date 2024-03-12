package androidx.constraintlayout.core.motion.utils;

import java.util.Arrays;

public class ArcCurveFit
  extends CurveFit
{
  public static final int ARC_START_FLIP = 3;
  public static final int ARC_START_HORIZONTAL = 2;
  public static final int ARC_START_LINEAR = 0;
  public static final int ARC_START_VERTICAL = 1;
  private static final int START_HORIZONTAL = 2;
  private static final int START_LINEAR = 3;
  private static final int START_VERTICAL = 1;
  Arc[] mArcs;
  private boolean mExtrapolate = true;
  private final double[] mTime;
  
  public ArcCurveFit(int[] paramArrayOfInt, double[] paramArrayOfDouble, double[][] paramArrayOfDouble1)
  {
    this.mTime = paramArrayOfDouble;
    this.mArcs = new Arc[paramArrayOfDouble.length - 1];
    int i = 1;
    int j = 1;
    for (int k = 0;; k++)
    {
      Arc[] arrayOfArc = this.mArcs;
      if (k >= arrayOfArc.length) {
        break;
      }
      int n = paramArrayOfInt[k];
      int m = 2;
      switch (n)
      {
      default: 
        break;
      case 3: 
        if (j == 1) {
          i = m;
        } else {
          i = 1;
        }
        j = i;
        break;
      case 2: 
        i = 2;
        j = 2;
        break;
      case 1: 
        i = 1;
        j = 1;
        break;
      case 0: 
        i = 3;
      }
      arrayOfArc[k] = new Arc(i, paramArrayOfDouble[k], paramArrayOfDouble[(k + 1)], paramArrayOfDouble1[k][0], paramArrayOfDouble1[k][1], paramArrayOfDouble1[(k + 1)][0], paramArrayOfDouble1[(k + 1)][1]);
    }
  }
  
  public double getPos(double paramDouble, int paramInt)
  {
    double d;
    Arc[] arrayOfArc;
    if (this.mExtrapolate)
    {
      if (paramDouble < this.mArcs[0].mTime1)
      {
        d = this.mArcs[0].mTime1;
        paramDouble -= this.mArcs[0].mTime1;
        if (this.mArcs[0].linear)
        {
          if (paramInt == 0) {
            return this.mArcs[0].getLinearX(d) + this.mArcs[0].getLinearDX(d) * paramDouble;
          }
          return this.mArcs[0].getLinearY(d) + this.mArcs[0].getLinearDY(d) * paramDouble;
        }
        this.mArcs[0].setPoint(d);
        if (paramInt == 0) {
          return this.mArcs[0].getX() + this.mArcs[0].getDX() * paramDouble;
        }
        return this.mArcs[0].getY() + this.mArcs[0].getDY() * paramDouble;
      }
      arrayOfArc = this.mArcs;
      d = paramDouble;
      if (paramDouble > arrayOfArc[(arrayOfArc.length - 1)].mTime2)
      {
        arrayOfArc = this.mArcs;
        d = arrayOfArc[(arrayOfArc.length - 1)].mTime2;
        paramDouble -= d;
        arrayOfArc = this.mArcs;
        i = arrayOfArc.length - 1;
        if (paramInt == 0) {
          return arrayOfArc[i].getLinearX(d) + this.mArcs[i].getLinearDX(d) * paramDouble;
        }
        return arrayOfArc[i].getLinearY(d) + this.mArcs[i].getLinearDY(d) * paramDouble;
      }
    }
    else if (paramDouble < this.mArcs[0].mTime1)
    {
      d = this.mArcs[0].mTime1;
    }
    else
    {
      arrayOfArc = this.mArcs;
      d = paramDouble;
      if (paramDouble > arrayOfArc[(arrayOfArc.length - 1)].mTime2)
      {
        arrayOfArc = this.mArcs;
        d = arrayOfArc[(arrayOfArc.length - 1)].mTime2;
      }
    }
    for (int i = 0;; i++)
    {
      arrayOfArc = this.mArcs;
      if (i >= arrayOfArc.length) {
        break;
      }
      if (d <= arrayOfArc[i].mTime2)
      {
        if (this.mArcs[i].linear)
        {
          if (paramInt == 0) {
            return this.mArcs[i].getLinearX(d);
          }
          return this.mArcs[i].getLinearY(d);
        }
        this.mArcs[i].setPoint(d);
        if (paramInt == 0) {
          return this.mArcs[i].getX();
        }
        return this.mArcs[i].getY();
      }
    }
    return NaN.0D;
  }
  
  public void getPos(double paramDouble, double[] paramArrayOfDouble)
  {
    double d1;
    Arc[] arrayOfArc;
    double d2;
    if (this.mExtrapolate)
    {
      if (paramDouble < this.mArcs[0].mTime1)
      {
        d1 = this.mArcs[0].mTime1;
        paramDouble -= this.mArcs[0].mTime1;
        if (this.mArcs[0].linear)
        {
          paramArrayOfDouble[0] = (this.mArcs[0].getLinearX(d1) + this.mArcs[0].getLinearDX(d1) * paramDouble);
          paramArrayOfDouble[1] = (this.mArcs[0].getLinearY(d1) + this.mArcs[0].getLinearDY(d1) * paramDouble);
        }
        else
        {
          this.mArcs[0].setPoint(d1);
          paramArrayOfDouble[0] = (this.mArcs[0].getX() + this.mArcs[0].getDX() * paramDouble);
          paramArrayOfDouble[1] = (this.mArcs[0].getY() + this.mArcs[0].getDY() * paramDouble);
        }
        return;
      }
      arrayOfArc = this.mArcs;
      d1 = paramDouble;
      if (paramDouble > arrayOfArc[(arrayOfArc.length - 1)].mTime2)
      {
        arrayOfArc = this.mArcs;
        d1 = arrayOfArc[(arrayOfArc.length - 1)].mTime2;
        d2 = paramDouble - d1;
        arrayOfArc = this.mArcs;
        i = arrayOfArc.length - 1;
        if (arrayOfArc[i].linear)
        {
          paramArrayOfDouble[0] = (this.mArcs[i].getLinearX(d1) + this.mArcs[i].getLinearDX(d1) * d2);
          paramArrayOfDouble[1] = (this.mArcs[i].getLinearY(d1) + this.mArcs[i].getLinearDY(d1) * d2);
        }
        else
        {
          this.mArcs[i].setPoint(paramDouble);
          paramArrayOfDouble[0] = (this.mArcs[i].getX() + this.mArcs[i].getDX() * d2);
          paramArrayOfDouble[1] = (this.mArcs[i].getY() + this.mArcs[i].getDY() * d2);
        }
      }
    }
    else
    {
      d2 = paramDouble;
      if (paramDouble < this.mArcs[0].mTime1) {
        d2 = this.mArcs[0].mTime1;
      }
      arrayOfArc = this.mArcs;
      d1 = d2;
      if (d2 > arrayOfArc[(arrayOfArc.length - 1)].mTime2)
      {
        arrayOfArc = this.mArcs;
        d1 = arrayOfArc[(arrayOfArc.length - 1)].mTime2;
      }
    }
    for (int i = 0;; i++)
    {
      arrayOfArc = this.mArcs;
      if (i >= arrayOfArc.length) {
        break;
      }
      if (d1 <= arrayOfArc[i].mTime2)
      {
        if (this.mArcs[i].linear)
        {
          paramArrayOfDouble[0] = this.mArcs[i].getLinearX(d1);
          paramArrayOfDouble[1] = this.mArcs[i].getLinearY(d1);
          return;
        }
        this.mArcs[i].setPoint(d1);
        paramArrayOfDouble[0] = this.mArcs[i].getX();
        paramArrayOfDouble[1] = this.mArcs[i].getY();
        return;
      }
    }
  }
  
  public void getPos(double paramDouble, float[] paramArrayOfFloat)
  {
    double d1;
    Arc[] arrayOfArc;
    if (this.mExtrapolate)
    {
      if (paramDouble < this.mArcs[0].mTime1)
      {
        d1 = this.mArcs[0].mTime1;
        paramDouble -= this.mArcs[0].mTime1;
        if (this.mArcs[0].linear)
        {
          paramArrayOfFloat[0] = ((float)(this.mArcs[0].getLinearX(d1) + this.mArcs[0].getLinearDX(d1) * paramDouble));
          paramArrayOfFloat[1] = ((float)(this.mArcs[0].getLinearY(d1) + this.mArcs[0].getLinearDY(d1) * paramDouble));
        }
        else
        {
          this.mArcs[0].setPoint(d1);
          paramArrayOfFloat[0] = ((float)(this.mArcs[0].getX() + this.mArcs[0].getDX() * paramDouble));
          paramArrayOfFloat[1] = ((float)(this.mArcs[0].getY() + this.mArcs[0].getDY() * paramDouble));
        }
        return;
      }
      arrayOfArc = this.mArcs;
      d1 = paramDouble;
      if (paramDouble > arrayOfArc[(arrayOfArc.length - 1)].mTime2)
      {
        arrayOfArc = this.mArcs;
        double d2 = arrayOfArc[(arrayOfArc.length - 1)].mTime2;
        d1 = paramDouble - d2;
        arrayOfArc = this.mArcs;
        i = arrayOfArc.length - 1;
        if (arrayOfArc[i].linear)
        {
          paramArrayOfFloat[0] = ((float)(this.mArcs[i].getLinearX(d2) + this.mArcs[i].getLinearDX(d2) * d1));
          paramArrayOfFloat[1] = ((float)(this.mArcs[i].getLinearY(d2) + this.mArcs[i].getLinearDY(d2) * d1));
        }
        else
        {
          this.mArcs[i].setPoint(paramDouble);
          paramArrayOfFloat[0] = ((float)this.mArcs[i].getX());
          paramArrayOfFloat[1] = ((float)this.mArcs[i].getY());
        }
      }
    }
    else if (paramDouble < this.mArcs[0].mTime1)
    {
      d1 = this.mArcs[0].mTime1;
    }
    else
    {
      arrayOfArc = this.mArcs;
      d1 = paramDouble;
      if (paramDouble > arrayOfArc[(arrayOfArc.length - 1)].mTime2)
      {
        arrayOfArc = this.mArcs;
        d1 = arrayOfArc[(arrayOfArc.length - 1)].mTime2;
      }
    }
    for (int i = 0;; i++)
    {
      arrayOfArc = this.mArcs;
      if (i >= arrayOfArc.length) {
        break;
      }
      if (d1 <= arrayOfArc[i].mTime2)
      {
        if (this.mArcs[i].linear)
        {
          paramArrayOfFloat[0] = ((float)this.mArcs[i].getLinearX(d1));
          paramArrayOfFloat[1] = ((float)this.mArcs[i].getLinearY(d1));
          return;
        }
        this.mArcs[i].setPoint(d1);
        paramArrayOfFloat[0] = ((float)this.mArcs[i].getX());
        paramArrayOfFloat[1] = ((float)this.mArcs[i].getY());
        return;
      }
    }
  }
  
  public double getSlope(double paramDouble, int paramInt)
  {
    double d = paramDouble;
    if (paramDouble < this.mArcs[0].mTime1) {
      d = this.mArcs[0].mTime1;
    }
    Arc[] arrayOfArc = this.mArcs;
    paramDouble = d;
    if (d > arrayOfArc[(arrayOfArc.length - 1)].mTime2)
    {
      arrayOfArc = this.mArcs;
      paramDouble = arrayOfArc[(arrayOfArc.length - 1)].mTime2;
    }
    for (int i = 0;; i++)
    {
      arrayOfArc = this.mArcs;
      if (i >= arrayOfArc.length) {
        break;
      }
      if (paramDouble <= arrayOfArc[i].mTime2)
      {
        if (this.mArcs[i].linear)
        {
          if (paramInt == 0) {
            return this.mArcs[i].getLinearDX(paramDouble);
          }
          return this.mArcs[i].getLinearDY(paramDouble);
        }
        this.mArcs[i].setPoint(paramDouble);
        if (paramInt == 0) {
          return this.mArcs[i].getDX();
        }
        return this.mArcs[i].getDY();
      }
    }
    return NaN.0D;
  }
  
  public void getSlope(double paramDouble, double[] paramArrayOfDouble)
  {
    double d;
    Arc[] arrayOfArc;
    if (paramDouble < this.mArcs[0].mTime1)
    {
      d = this.mArcs[0].mTime1;
    }
    else
    {
      arrayOfArc = this.mArcs;
      d = paramDouble;
      if (paramDouble > arrayOfArc[(arrayOfArc.length - 1)].mTime2)
      {
        arrayOfArc = this.mArcs;
        d = arrayOfArc[(arrayOfArc.length - 1)].mTime2;
      }
    }
    for (int i = 0;; i++)
    {
      arrayOfArc = this.mArcs;
      if (i >= arrayOfArc.length) {
        break;
      }
      if (d <= arrayOfArc[i].mTime2)
      {
        if (this.mArcs[i].linear)
        {
          paramArrayOfDouble[0] = this.mArcs[i].getLinearDX(d);
          paramArrayOfDouble[1] = this.mArcs[i].getLinearDY(d);
          return;
        }
        this.mArcs[i].setPoint(d);
        paramArrayOfDouble[0] = this.mArcs[i].getDX();
        paramArrayOfDouble[1] = this.mArcs[i].getDY();
        return;
      }
    }
  }
  
  public double[] getTimePoints()
  {
    return this.mTime;
  }
  
  private static class Arc
  {
    private static final double EPSILON = 0.001D;
    private static final String TAG = "Arc";
    private static double[] ourPercent = new double[91];
    boolean linear;
    double mArcDistance;
    double mArcVelocity;
    double mEllipseA;
    double mEllipseB;
    double mEllipseCenterX;
    double mEllipseCenterY;
    double[] mLut;
    double mOneOverDeltaTime;
    double mTime1;
    double mTime2;
    double mTmpCosAngle;
    double mTmpSinAngle;
    boolean mVertical;
    double mX1;
    double mX2;
    double mY1;
    double mY2;
    
    Arc(int paramInt, double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, double paramDouble5, double paramDouble6)
    {
      boolean bool = false;
      this.linear = false;
      int i = 1;
      if (paramInt == 1) {
        bool = true;
      }
      this.mVertical = bool;
      this.mTime1 = paramDouble1;
      this.mTime2 = paramDouble2;
      this.mOneOverDeltaTime = (1.0D / (paramDouble2 - paramDouble1));
      if (3 == paramInt) {
        this.linear = true;
      }
      paramDouble2 = paramDouble5 - paramDouble3;
      paramDouble1 = paramDouble6 - paramDouble4;
      if ((!this.linear) && (Math.abs(paramDouble2) >= 0.001D) && (Math.abs(paramDouble1) >= 0.001D))
      {
        this.mLut = new double[101];
        bool = this.mVertical;
        paramInt = i;
        if (bool) {
          paramInt = -1;
        }
        this.mEllipseA = (paramInt * paramDouble2);
        if (bool) {
          paramInt = 1;
        } else {
          paramInt = -1;
        }
        this.mEllipseB = (paramInt * paramDouble1);
        if (bool) {
          paramDouble1 = paramDouble5;
        } else {
          paramDouble1 = paramDouble3;
        }
        this.mEllipseCenterX = paramDouble1;
        if (bool) {
          paramDouble1 = paramDouble4;
        } else {
          paramDouble1 = paramDouble6;
        }
        this.mEllipseCenterY = paramDouble1;
        buildTable(paramDouble3, paramDouble4, paramDouble5, paramDouble6);
        this.mArcVelocity = (this.mArcDistance * this.mOneOverDeltaTime);
        return;
      }
      this.linear = true;
      this.mX1 = paramDouble3;
      this.mX2 = paramDouble5;
      this.mY1 = paramDouble4;
      this.mY2 = paramDouble6;
      paramDouble3 = Math.hypot(paramDouble1, paramDouble2);
      this.mArcDistance = paramDouble3;
      this.mArcVelocity = (paramDouble3 * this.mOneOverDeltaTime);
      paramDouble3 = this.mTime2;
      paramDouble4 = this.mTime1;
      this.mEllipseCenterX = (paramDouble2 / (paramDouble3 - paramDouble4));
      this.mEllipseCenterY = (paramDouble1 / (paramDouble3 - paramDouble4));
    }
    
    private void buildTable(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4)
    {
      paramDouble3 -= paramDouble1;
      paramDouble2 -= paramDouble4;
      double d1 = 0.0D;
      paramDouble4 = 0.0D;
      paramDouble1 = 0.0D;
      double[] arrayOfDouble;
      for (int i = 0;; i++)
      {
        arrayOfDouble = ourPercent;
        if (i >= arrayOfDouble.length) {
          break;
        }
        double d2 = Math.toRadians(i * 90.0D / (arrayOfDouble.length - 1));
        double d3 = Math.sin(d2);
        d2 = Math.cos(d2);
        d3 = paramDouble3 * d3;
        d2 = paramDouble2 * d2;
        if (i > 0)
        {
          paramDouble1 = Math.hypot(d3 - d1, d2 - paramDouble4) + paramDouble1;
          ourPercent[i] = paramDouble1;
        }
        d1 = d3;
        paramDouble4 = d2;
      }
      this.mArcDistance = paramDouble1;
      for (i = 0;; i++)
      {
        arrayOfDouble = ourPercent;
        if (i >= arrayOfDouble.length) {
          break;
        }
        arrayOfDouble[i] /= paramDouble1;
      }
      for (i = 0;; i++)
      {
        arrayOfDouble = this.mLut;
        if (i >= arrayOfDouble.length) {
          break;
        }
        paramDouble2 = i / (arrayOfDouble.length - 1);
        int k = Arrays.binarySearch(ourPercent, paramDouble2);
        if (k >= 0)
        {
          this.mLut[i] = (k / (ourPercent.length - 1));
        }
        else if (k == -1)
        {
          this.mLut[i] = 0.0D;
        }
        else
        {
          int j = -k - 2;
          k = -k;
          paramDouble3 = j;
          arrayOfDouble = ourPercent;
          paramDouble1 = arrayOfDouble[j];
          paramDouble1 = (paramDouble3 + (paramDouble2 - paramDouble1) / (arrayOfDouble[(k - 1)] - paramDouble1)) / (arrayOfDouble.length - 1);
          this.mLut[i] = paramDouble1;
        }
      }
    }
    
    double getDX()
    {
      double d1 = this.mEllipseA * this.mTmpCosAngle;
      double d3 = -this.mEllipseB;
      double d2 = this.mTmpSinAngle;
      d2 = this.mArcVelocity / Math.hypot(d1, d3 * d2);
      if (this.mVertical) {
        d1 = -d1 * d2;
      } else {
        d1 *= d2;
      }
      return d1;
    }
    
    double getDY()
    {
      double d3 = this.mEllipseA;
      double d2 = this.mTmpCosAngle;
      double d1 = -this.mEllipseB * this.mTmpSinAngle;
      d2 = this.mArcVelocity / Math.hypot(d3 * d2, d1);
      if (this.mVertical) {
        d1 = -d1 * d2;
      } else {
        d1 *= d2;
      }
      return d1;
    }
    
    public double getLinearDX(double paramDouble)
    {
      return this.mEllipseCenterX;
    }
    
    public double getLinearDY(double paramDouble)
    {
      return this.mEllipseCenterY;
    }
    
    public double getLinearX(double paramDouble)
    {
      double d3 = this.mTime1;
      double d2 = this.mOneOverDeltaTime;
      double d1 = this.mX1;
      return d1 + (this.mX2 - d1) * ((paramDouble - d3) * d2);
    }
    
    public double getLinearY(double paramDouble)
    {
      double d1 = this.mTime1;
      double d2 = this.mOneOverDeltaTime;
      double d3 = this.mY1;
      return d3 + (this.mY2 - d3) * ((paramDouble - d1) * d2);
    }
    
    double getX()
    {
      return this.mEllipseCenterX + this.mEllipseA * this.mTmpSinAngle;
    }
    
    double getY()
    {
      return this.mEllipseCenterY + this.mEllipseB * this.mTmpCosAngle;
    }
    
    double lookup(double paramDouble)
    {
      if (paramDouble <= 0.0D) {
        return 0.0D;
      }
      if (paramDouble >= 1.0D) {
        return 1.0D;
      }
      double[] arrayOfDouble = this.mLut;
      paramDouble = (arrayOfDouble.length - 1) * paramDouble;
      int i = (int)paramDouble;
      double d2 = (int)paramDouble;
      double d1 = arrayOfDouble[i];
      return d1 + (arrayOfDouble[(i + 1)] - d1) * (paramDouble - d2);
    }
    
    void setPoint(double paramDouble)
    {
      if (this.mVertical) {
        paramDouble = this.mTime2 - paramDouble;
      } else {
        paramDouble -= this.mTime1;
      }
      paramDouble = lookup(paramDouble * this.mOneOverDeltaTime) * 1.5707963267948966D;
      this.mTmpSinAngle = Math.sin(paramDouble);
      this.mTmpCosAngle = Math.cos(paramDouble);
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/constraintlayout/core/motion/utils/ArcCurveFit.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */