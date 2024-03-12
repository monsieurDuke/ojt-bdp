package androidx.constraintlayout.core.motion.utils;

import java.io.PrintStream;
import java.util.Arrays;
import mt.Log229316;
import mt.Log5ECF72;
import mt.LogE84000;

public class Easing
{
  private static final String ACCELERATE = "cubic(0.4, 0.05, 0.8, 0.7)";
  private static final String ACCELERATE_NAME = "accelerate";
  private static final String ANTICIPATE = "cubic(0.36, 0, 0.66, -0.56)";
  private static final String ANTICIPATE_NAME = "anticipate";
  private static final String DECELERATE = "cubic(0.0, 0.0, 0.2, 0.95)";
  private static final String DECELERATE_NAME = "decelerate";
  private static final String LINEAR = "cubic(1, 1, 0, 0)";
  private static final String LINEAR_NAME = "linear";
  public static String[] NAMED_EASING = { "standard", "accelerate", "decelerate", "linear" };
  private static final String OVERSHOOT = "cubic(0.34, 1.56, 0.64, 1)";
  private static final String OVERSHOOT_NAME = "overshoot";
  private static final String STANDARD = "cubic(0.4, 0.0, 0.2, 1)";
  private static final String STANDARD_NAME = "standard";
  static Easing sDefault = new Easing();
  String str = "identity";
  
  public static Easing getInterpolator(String paramString)
  {
    if (paramString == null) {
      return null;
    }
    if (paramString.startsWith("cubic")) {
      return new CubicEasing(paramString);
    }
    if (paramString.startsWith("spline")) {
      return new StepCurve(paramString);
    }
    if (paramString.startsWith("Schlick")) {
      return new Schlick(paramString);
    }
    int i = -1;
    switch (paramString.hashCode())
    {
    }
    for (;;)
    {
      break;
      if (paramString.equals("standard"))
      {
        i = 0;
        break;
        if (paramString.equals("overshoot"))
        {
          i = 5;
          break;
          if (paramString.equals("linear"))
          {
            i = 3;
            break;
            if (paramString.equals("anticipate"))
            {
              i = 4;
              break;
              if (paramString.equals("decelerate"))
              {
                i = 2;
                break;
                if (paramString.equals("accelerate")) {
                  i = 1;
                }
              }
            }
          }
        }
      }
    }
    switch (i)
    {
    default: 
      PrintStream localPrintStream = System.err;
      StringBuilder localStringBuilder = new StringBuilder().append("transitionEasing syntax error syntax:transitionEasing=\"cubic(1.0,0.5,0.0,0.6)\" or ");
      paramString = Arrays.toString(NAMED_EASING);
      Log5ECF72.a(paramString);
      LogE84000.a(paramString);
      Log229316.a(paramString);
      localPrintStream.println(paramString);
      return sDefault;
    case 5: 
      return new CubicEasing("cubic(0.34, 1.56, 0.64, 1)");
    case 4: 
      return new CubicEasing("cubic(0.36, 0, 0.66, -0.56)");
    case 3: 
      return new CubicEasing("cubic(1, 1, 0, 0)");
    case 2: 
      return new CubicEasing("cubic(0.0, 0.0, 0.2, 0.95)");
    case 1: 
      return new CubicEasing("cubic(0.4, 0.05, 0.8, 0.7)");
    }
    return new CubicEasing("cubic(0.4, 0.0, 0.2, 1)");
  }
  
  public double get(double paramDouble)
  {
    return paramDouble;
  }
  
  public double getDiff(double paramDouble)
  {
    return 1.0D;
  }
  
  public String toString()
  {
    return this.str;
  }
  
  static class CubicEasing
    extends Easing
  {
    private static double d_error = 1.0E-4D;
    private static double error = 0.01D;
    double x1;
    double x2;
    double y1;
    double y2;
    
    public CubicEasing(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4)
    {
      setup(paramDouble1, paramDouble2, paramDouble3, paramDouble4);
    }
    
    CubicEasing(String paramString)
    {
      this.str = paramString;
      int j = paramString.indexOf('(');
      int i = paramString.indexOf(',', j);
      this.x1 = Double.parseDouble(paramString.substring(j + 1, i).trim());
      j = paramString.indexOf(',', i + 1);
      this.y1 = Double.parseDouble(paramString.substring(i + 1, j).trim());
      i = paramString.indexOf(',', j + 1);
      this.x2 = Double.parseDouble(paramString.substring(j + 1, i).trim());
      this.y2 = Double.parseDouble(paramString.substring(i + 1, paramString.indexOf(')', i + 1)).trim());
    }
    
    private double getDiffX(double paramDouble)
    {
      double d3 = 1.0D - paramDouble;
      double d2 = this.x1;
      double d1 = this.x2;
      return d3 * 3.0D * d3 * d2 + 6.0D * d3 * paramDouble * (d1 - d2) + 3.0D * paramDouble * paramDouble * (1.0D - d1);
    }
    
    private double getDiffY(double paramDouble)
    {
      double d1 = 1.0D - paramDouble;
      double d3 = this.y1;
      double d2 = this.y2;
      return d1 * 3.0D * d1 * d3 + 6.0D * d1 * paramDouble * (d2 - d3) + 3.0D * paramDouble * paramDouble * (1.0D - d2);
    }
    
    private double getX(double paramDouble)
    {
      double d = 1.0D - paramDouble;
      return this.x1 * (d * 3.0D * d * paramDouble) + this.x2 * (3.0D * d * paramDouble * paramDouble) + paramDouble * paramDouble * paramDouble;
    }
    
    private double getY(double paramDouble)
    {
      double d = 1.0D - paramDouble;
      return this.y1 * (d * 3.0D * d * paramDouble) + this.y2 * (3.0D * d * paramDouble * paramDouble) + paramDouble * paramDouble * paramDouble;
    }
    
    public double get(double paramDouble)
    {
      if (paramDouble <= 0.0D) {
        return 0.0D;
      }
      if (paramDouble >= 1.0D) {
        return 1.0D;
      }
      double d2 = 0.5D;
      for (double d1 = 0.5D; d1 > error; d1 = d3)
      {
        d4 = getX(d2);
        d3 = d1 * 0.5D;
        if (d4 < paramDouble) {
          d1 = d2 + d3;
        } else {
          d1 = d2 - d3;
        }
        d2 = d1;
      }
      double d3 = getX(d2 - d1);
      double d5 = getX(d2 + d1);
      double d4 = getY(d2 - d1);
      return (getY(d2 + d1) - d4) * (paramDouble - d3) / (d5 - d3) + d4;
    }
    
    public double getDiff(double paramDouble)
    {
      double d1 = 0.5D;
      double d2 = 0.5D;
      while (d2 > d_error)
      {
        d3 = getX(d1);
        d2 *= 0.5D;
        if (d3 < paramDouble) {
          d1 += d2;
        } else {
          d1 -= d2;
        }
      }
      paramDouble = getX(d1 - d2);
      double d3 = getX(d1 + d2);
      double d4 = getY(d1 - d2);
      return (getY(d1 + d2) - d4) / (d3 - paramDouble);
    }
    
    void setup(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4)
    {
      this.x1 = paramDouble1;
      this.y1 = paramDouble2;
      this.x2 = paramDouble3;
      this.y2 = paramDouble4;
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/constraintlayout/core/motion/utils/Easing.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */