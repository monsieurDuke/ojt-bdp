package androidx.constraintlayout.core.motion.utils;

import java.io.PrintStream;
import java.util.Arrays;

public class StepCurve
  extends Easing
{
  private static final boolean DEBUG = false;
  MonotonicCurveFit mCurveFit;
  
  StepCurve(String paramString)
  {
    this.str = paramString;
    double[] arrayOfDouble = new double[this.str.length() / 2];
    int j = paramString.indexOf('(') + 1;
    int k = paramString.indexOf(',', j);
    for (int i = 0; k != -1; i++)
    {
      arrayOfDouble[i] = Double.parseDouble(paramString.substring(j, k).trim());
      k++;
      j = k;
      k = paramString.indexOf(',', k);
    }
    arrayOfDouble[i] = Double.parseDouble(paramString.substring(j, paramString.indexOf(41, j)).trim());
    this.mCurveFit = genSpline(Arrays.copyOf(arrayOfDouble, i + 1));
  }
  
  private static MonotonicCurveFit genSpline(String paramString)
  {
    paramString = paramString.split("\\s+");
    double[] arrayOfDouble = new double[paramString.length];
    for (int i = 0; i < arrayOfDouble.length; i++) {
      arrayOfDouble[i] = Double.parseDouble(paramString[i]);
    }
    return genSpline(arrayOfDouble);
  }
  
  private static MonotonicCurveFit genSpline(double[] paramArrayOfDouble)
  {
    int i = paramArrayOfDouble.length * 3 - 2;
    int j = paramArrayOfDouble.length - 1;
    double d1 = 1.0D / j;
    double[][] arrayOfDouble = new double[i][1];
    double[] arrayOfDouble1 = new double[i];
    for (i = 0; i < paramArrayOfDouble.length; i++)
    {
      double d2 = paramArrayOfDouble[i];
      arrayOfDouble[(i + j)][0] = d2;
      arrayOfDouble1[(i + j)] = (i * d1);
      if (i > 0)
      {
        arrayOfDouble[(j * 2 + i)][0] = (d2 + 1.0D);
        arrayOfDouble1[(j * 2 + i)] = (i * d1 + 1.0D);
        arrayOfDouble[(i - 1)][0] = (d2 - 1.0D - d1);
        arrayOfDouble1[(i - 1)] = (i * d1 - 1.0D - d1);
      }
    }
    paramArrayOfDouble = new MonotonicCurveFit(arrayOfDouble1, arrayOfDouble);
    System.out.println(" 0 " + paramArrayOfDouble.getPos(0.0D, 0));
    System.out.println(" 1 " + paramArrayOfDouble.getPos(1.0D, 0));
    return paramArrayOfDouble;
  }
  
  public double get(double paramDouble)
  {
    return this.mCurveFit.getPos(paramDouble, 0);
  }
  
  public double getDiff(double paramDouble)
  {
    return this.mCurveFit.getSlope(paramDouble, 0);
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/constraintlayout/core/motion/utils/StepCurve.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */