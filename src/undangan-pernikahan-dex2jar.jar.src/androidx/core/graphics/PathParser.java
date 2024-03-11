package androidx.core.graphics;

import android.graphics.Path;
import android.util.Log;
import java.util.ArrayList;

public class PathParser
{
  private static final String LOGTAG = "PathParser";
  
  private static void addNode(ArrayList<PathDataNode> paramArrayList, char paramChar, float[] paramArrayOfFloat)
  {
    paramArrayList.add(new PathDataNode(paramChar, paramArrayOfFloat));
  }
  
  public static boolean canMorph(PathDataNode[] paramArrayOfPathDataNode1, PathDataNode[] paramArrayOfPathDataNode2)
  {
    if ((paramArrayOfPathDataNode1 != null) && (paramArrayOfPathDataNode2 != null))
    {
      if (paramArrayOfPathDataNode1.length != paramArrayOfPathDataNode2.length) {
        return false;
      }
      int i = 0;
      while (i < paramArrayOfPathDataNode1.length) {
        if ((paramArrayOfPathDataNode1[i].mType == paramArrayOfPathDataNode2[i].mType) && (paramArrayOfPathDataNode1[i].mParams.length == paramArrayOfPathDataNode2[i].mParams.length)) {
          i++;
        } else {
          return false;
        }
      }
      return true;
    }
    return false;
  }
  
  static float[] copyOfRange(float[] paramArrayOfFloat, int paramInt1, int paramInt2)
  {
    if (paramInt1 <= paramInt2)
    {
      int i = paramArrayOfFloat.length;
      if ((paramInt1 >= 0) && (paramInt1 <= i))
      {
        paramInt2 -= paramInt1;
        i = Math.min(paramInt2, i - paramInt1);
        float[] arrayOfFloat = new float[paramInt2];
        System.arraycopy(paramArrayOfFloat, paramInt1, arrayOfFloat, 0, i);
        return arrayOfFloat;
      }
      throw new ArrayIndexOutOfBoundsException();
    }
    throw new IllegalArgumentException();
  }
  
  public static PathDataNode[] createNodesFromPathData(String paramString)
  {
    if (paramString == null) {
      return null;
    }
    int i = 0;
    int j = 1;
    ArrayList localArrayList = new ArrayList();
    while (j < paramString.length())
    {
      j = nextStart(paramString, j);
      String str = paramString.substring(i, j).trim();
      if (str.length() > 0)
      {
        float[] arrayOfFloat = getFloats(str);
        addNode(localArrayList, str.charAt(0), arrayOfFloat);
      }
      i = j;
      j++;
    }
    if ((j - i == 1) && (i < paramString.length())) {
      addNode(localArrayList, paramString.charAt(i), new float[0]);
    }
    return (PathDataNode[])localArrayList.toArray(new PathDataNode[localArrayList.size()]);
  }
  
  public static Path createPathFromPathData(String paramString)
  {
    Path localPath = new Path();
    PathDataNode[] arrayOfPathDataNode = createNodesFromPathData(paramString);
    if (arrayOfPathDataNode != null) {
      try
      {
        PathDataNode.nodesToPath(arrayOfPathDataNode, localPath);
        return localPath;
      }
      catch (RuntimeException localRuntimeException)
      {
        throw new RuntimeException("Error in parsing " + paramString, localRuntimeException);
      }
    }
    return null;
  }
  
  public static PathDataNode[] deepCopyNodes(PathDataNode[] paramArrayOfPathDataNode)
  {
    if (paramArrayOfPathDataNode == null) {
      return null;
    }
    PathDataNode[] arrayOfPathDataNode = new PathDataNode[paramArrayOfPathDataNode.length];
    for (int i = 0; i < paramArrayOfPathDataNode.length; i++) {
      arrayOfPathDataNode[i] = new PathDataNode(paramArrayOfPathDataNode[i]);
    }
    return arrayOfPathDataNode;
  }
  
  private static void extract(String paramString, int paramInt, ExtractFloatResult paramExtractFloatResult)
  {
    int m = paramInt;
    int i2 = 0;
    paramExtractFloatResult.mEndWithNegOrDot = false;
    int k = 0;
    int j;
    for (int n = 0; m < paramString.length(); n = j)
    {
      int i3 = 0;
      int i;
      int i1;
      switch (paramString.charAt(m))
      {
      default: 
        i = i2;
        i1 = k;
        j = i3;
        break;
      case 'E': 
      case 'e': 
        j = 1;
        i = i2;
        i1 = k;
        break;
      case '.': 
        if (k == 0)
        {
          i1 = 1;
          i = i2;
          j = i3;
        }
        else
        {
          i = 1;
          paramExtractFloatResult.mEndWithNegOrDot = true;
          i1 = k;
          j = i3;
        }
        break;
      case '-': 
        i = i2;
        i1 = k;
        j = i3;
        if (m != paramInt)
        {
          i = i2;
          i1 = k;
          j = i3;
          if (n == 0)
          {
            i = 1;
            paramExtractFloatResult.mEndWithNegOrDot = true;
            i1 = k;
            j = i3;
          }
        }
        break;
      case ' ': 
      case ',': 
        i = 1;
        j = i3;
        i1 = k;
      }
      if (i != 0) {
        break;
      }
      m++;
      i2 = i;
      k = i1;
    }
    paramExtractFloatResult.mEndPosition = m;
  }
  
  private static float[] getFloats(String paramString)
  {
    if ((paramString.charAt(0) != 'z') && (paramString.charAt(0) != 'Z')) {
      try
      {
        float[] arrayOfFloat = new float[paramString.length()];
        int k = 0;
        int i = 1;
        ExtractFloatResult localExtractFloatResult = new androidx/core/graphics/PathParser$ExtractFloatResult;
        localExtractFloatResult.<init>();
        int n = paramString.length();
        while (i < n)
        {
          extract(paramString, i, localExtractFloatResult);
          int m = localExtractFloatResult.mEndPosition;
          int j = k;
          if (i < m)
          {
            arrayOfFloat[k] = Float.parseFloat(paramString.substring(i, m));
            j = k + 1;
          }
          if (localExtractFloatResult.mEndWithNegOrDot)
          {
            i = m;
            k = j;
          }
          else
          {
            i = m + 1;
            k = j;
          }
        }
        arrayOfFloat = copyOfRange(arrayOfFloat, 0, k);
        return arrayOfFloat;
      }
      catch (NumberFormatException localNumberFormatException)
      {
        throw new RuntimeException("error in parsing \"" + paramString + "\"", localNumberFormatException);
      }
    }
    return new float[0];
  }
  
  public static boolean interpolatePathDataNodes(PathDataNode[] paramArrayOfPathDataNode1, PathDataNode[] paramArrayOfPathDataNode2, PathDataNode[] paramArrayOfPathDataNode3, float paramFloat)
  {
    if ((paramArrayOfPathDataNode1 != null) && (paramArrayOfPathDataNode2 != null) && (paramArrayOfPathDataNode3 != null))
    {
      if ((paramArrayOfPathDataNode1.length == paramArrayOfPathDataNode2.length) && (paramArrayOfPathDataNode2.length == paramArrayOfPathDataNode3.length))
      {
        if (!canMorph(paramArrayOfPathDataNode2, paramArrayOfPathDataNode3)) {
          return false;
        }
        for (int i = 0; i < paramArrayOfPathDataNode1.length; i++) {
          paramArrayOfPathDataNode1[i].interpolatePathDataNode(paramArrayOfPathDataNode2[i], paramArrayOfPathDataNode3[i], paramFloat);
        }
        return true;
      }
      throw new IllegalArgumentException("The nodes to be interpolated and resulting nodes must have the same length");
    }
    throw new IllegalArgumentException("The nodes to be interpolated and resulting nodes cannot be null");
  }
  
  private static int nextStart(String paramString, int paramInt)
  {
    while (paramInt < paramString.length())
    {
      int i = paramString.charAt(paramInt);
      if ((((i - 65) * (i - 90) <= 0) || ((i - 97) * (i - 122) <= 0)) && (i != 101) && (i != 69)) {
        return paramInt;
      }
      paramInt++;
    }
    return paramInt;
  }
  
  public static void updateNodes(PathDataNode[] paramArrayOfPathDataNode1, PathDataNode[] paramArrayOfPathDataNode2)
  {
    for (int i = 0; i < paramArrayOfPathDataNode2.length; i++)
    {
      paramArrayOfPathDataNode1[i].mType = paramArrayOfPathDataNode2[i].mType;
      for (int j = 0; j < paramArrayOfPathDataNode2[i].mParams.length; j++) {
        paramArrayOfPathDataNode1[i].mParams[j] = paramArrayOfPathDataNode2[i].mParams[j];
      }
    }
  }
  
  private static class ExtractFloatResult
  {
    int mEndPosition;
    boolean mEndWithNegOrDot;
  }
  
  public static class PathDataNode
  {
    public float[] mParams;
    public char mType;
    
    PathDataNode(char paramChar, float[] paramArrayOfFloat)
    {
      this.mType = paramChar;
      this.mParams = paramArrayOfFloat;
    }
    
    PathDataNode(PathDataNode paramPathDataNode)
    {
      this.mType = paramPathDataNode.mType;
      paramPathDataNode = paramPathDataNode.mParams;
      this.mParams = PathParser.copyOfRange(paramPathDataNode, 0, paramPathDataNode.length);
    }
    
    private static void addCommand(Path paramPath, float[] paramArrayOfFloat1, char paramChar1, char paramChar2, float[] paramArrayOfFloat2)
    {
      Path localPath = paramPath;
      float f4 = paramArrayOfFloat1[0];
      float f3 = paramArrayOfFloat1[1];
      float f5 = paramArrayOfFloat1[2];
      float f6 = paramArrayOfFloat1[3];
      float f2 = paramArrayOfFloat1[4];
      float f1 = paramArrayOfFloat1[5];
      int i;
      switch (paramChar2)
      {
      default: 
        i = 2;
        break;
      case 'Z': 
      case 'z': 
        paramPath.close();
        f4 = f2;
        f3 = f1;
        f5 = f2;
        f6 = f1;
        localPath.moveTo(f4, f3);
        i = 2;
        break;
      case 'Q': 
      case 'S': 
      case 'q': 
      case 's': 
        i = 4;
        break;
      case 'L': 
      case 'M': 
      case 'T': 
      case 'l': 
      case 'm': 
      case 't': 
        i = 2;
        break;
      case 'H': 
      case 'V': 
      case 'h': 
      case 'v': 
        i = 1;
        break;
      case 'C': 
      case 'c': 
        i = 6;
        break;
      case 'A': 
      case 'a': 
        i = 7;
      }
      int j = 0;
      float f8 = f5;
      float f7 = f6;
      f5 = f2;
      f2 = f3;
      f6 = f1;
      f1 = f4;
      while (j < paramArrayOfFloat2.length)
      {
        float f9;
        boolean bool1;
        boolean bool2;
        switch (paramChar2)
        {
        default: 
          f3 = f8;
          f4 = f7;
          break;
        case 'v': 
          localPath.rLineTo(0.0F, paramArrayOfFloat2[(j + 0)]);
          f2 += paramArrayOfFloat2[(j + 0)];
          f3 = f8;
          f4 = f7;
          break;
        case 't': 
          f4 = 0.0F;
          f3 = 0.0F;
          if ((paramChar1 == 'q') || (paramChar1 == 't') || (paramChar1 == 'Q') || (paramChar1 == 'T'))
          {
            f4 = f1 - f8;
            f3 = f2 - f7;
          }
          localPath.rQuadTo(f4, f3, paramArrayOfFloat2[(j + 0)], paramArrayOfFloat2[(j + 1)]);
          f7 = f1 + paramArrayOfFloat2[(j + 0)];
          f8 = f2 + paramArrayOfFloat2[(j + 1)];
          f4 = f1 + f4;
          f9 = f2 + f3;
          f2 = f8;
          f1 = f7;
          f3 = f4;
          f4 = f9;
          break;
        case 's': 
          if ((paramChar1 != 'c') && (paramChar1 != 's') && (paramChar1 != 'C') && (paramChar1 != 'S'))
          {
            f3 = 0.0F;
            f4 = 0.0F;
          }
          else
          {
            f3 = f1 - f8;
            f4 = f2 - f7;
          }
          paramPath.rCubicTo(f3, f4, paramArrayOfFloat2[(j + 0)], paramArrayOfFloat2[(j + 1)], paramArrayOfFloat2[(j + 2)], paramArrayOfFloat2[(j + 3)]);
          f7 = paramArrayOfFloat2[(j + 0)];
          f4 = paramArrayOfFloat2[(j + 1)];
          f3 = f1 + paramArrayOfFloat2[(j + 2)];
          f8 = paramArrayOfFloat2[(j + 3)];
          f7 += f1;
          f4 = f2 + f4;
          f2 = f8 + f2;
          f1 = f3;
          f3 = f7;
          break;
        case 'q': 
          localPath.rQuadTo(paramArrayOfFloat2[(j + 0)], paramArrayOfFloat2[(j + 1)], paramArrayOfFloat2[(j + 2)], paramArrayOfFloat2[(j + 3)]);
          f4 = paramArrayOfFloat2[(j + 0)];
          f7 = paramArrayOfFloat2[(j + 1)];
          f3 = f1 + paramArrayOfFloat2[(j + 2)];
          f8 = paramArrayOfFloat2[(j + 3)];
          f4 += f1;
          f7 = f2 + f7;
          f2 = f8 + f2;
          f1 = f3;
          f3 = f4;
          f4 = f7;
          break;
        case 'm': 
          f1 += paramArrayOfFloat2[(j + 0)];
          f2 += paramArrayOfFloat2[(j + 1)];
          if (j > 0)
          {
            localPath.rLineTo(paramArrayOfFloat2[(j + 0)], paramArrayOfFloat2[(j + 1)]);
            f3 = f8;
            f4 = f7;
          }
          else
          {
            localPath.rMoveTo(paramArrayOfFloat2[(j + 0)], paramArrayOfFloat2[(j + 1)]);
            f5 = f1;
            f6 = f2;
            f3 = f8;
            f4 = f7;
          }
          break;
        case 'l': 
          localPath.rLineTo(paramArrayOfFloat2[(j + 0)], paramArrayOfFloat2[(j + 1)]);
          f1 += paramArrayOfFloat2[(j + 0)];
          f2 += paramArrayOfFloat2[(j + 1)];
          f3 = f8;
          f4 = f7;
          break;
        case 'h': 
          localPath.rLineTo(paramArrayOfFloat2[(j + 0)], 0.0F);
          f1 += paramArrayOfFloat2[(j + 0)];
          f3 = f8;
          f4 = f7;
          break;
        case 'c': 
          paramPath.rCubicTo(paramArrayOfFloat2[(j + 0)], paramArrayOfFloat2[(j + 1)], paramArrayOfFloat2[(j + 2)], paramArrayOfFloat2[(j + 3)], paramArrayOfFloat2[(j + 4)], paramArrayOfFloat2[(j + 5)]);
          f4 = paramArrayOfFloat2[(j + 2)];
          f7 = paramArrayOfFloat2[(j + 3)];
          f3 = f1 + paramArrayOfFloat2[(j + 4)];
          f8 = paramArrayOfFloat2[(j + 5)];
          f4 += f1;
          f7 = f2 + f7;
          f2 = f8 + f2;
          f1 = f3;
          f3 = f4;
          f4 = f7;
          break;
        case 'a': 
          f4 = paramArrayOfFloat2[(j + 5)];
          f9 = paramArrayOfFloat2[(j + 6)];
          f8 = paramArrayOfFloat2[(j + 0)];
          f7 = paramArrayOfFloat2[(j + 1)];
          f3 = paramArrayOfFloat2[(j + 2)];
          if (paramArrayOfFloat2[(j + 3)] != 0.0F) {
            bool1 = true;
          } else {
            bool1 = false;
          }
          if (paramArrayOfFloat2[(j + 4)] != 0.0F) {
            bool2 = true;
          } else {
            bool2 = false;
          }
          drawArc(paramPath, f1, f2, f4 + f1, f9 + f2, f8, f7, f3, bool1, bool2);
          f1 += paramArrayOfFloat2[(j + 5)];
          f2 += paramArrayOfFloat2[(j + 6)];
          localPath = paramPath;
          f3 = f1;
          f4 = f2;
          break;
        case 'V': 
          f2 = paramArrayOfFloat2[(j + 0)];
          localPath = paramPath;
          localPath.lineTo(f1, f2);
          f2 = paramArrayOfFloat2[(j + 0)];
          f3 = f8;
          f4 = f7;
          break;
        case 'T': 
          f4 = f1;
          f3 = f2;
          if ((paramChar1 == 'q') || (paramChar1 == 't') || (paramChar1 == 'Q') || (paramChar1 == 'T'))
          {
            f4 = f1 * 2.0F - f8;
            f3 = f2 * 2.0F - f7;
          }
          localPath.quadTo(f4, f3, paramArrayOfFloat2[(j + 0)], paramArrayOfFloat2[(j + 1)]);
          f1 = paramArrayOfFloat2[(j + 0)];
          f2 = paramArrayOfFloat2[(j + 1)];
          f7 = f4;
          f4 = f3;
          f3 = f7;
          break;
        case 'S': 
          if ((paramChar1 != 'c') && (paramChar1 != 's') && (paramChar1 != 'C') && (paramChar1 != 'S')) {
            break label1558;
          }
          f1 = f1 * 2.0F - f8;
          f2 = f2 * 2.0F - f7;
          paramPath.cubicTo(f1, f2, paramArrayOfFloat2[(j + 0)], paramArrayOfFloat2[(j + 1)], paramArrayOfFloat2[(j + 2)], paramArrayOfFloat2[(j + 3)]);
          f3 = paramArrayOfFloat2[(j + 0)];
          f4 = paramArrayOfFloat2[(j + 1)];
          f1 = paramArrayOfFloat2[(j + 2)];
          f2 = paramArrayOfFloat2[(j + 3)];
          break;
        case 'Q': 
          localPath.quadTo(paramArrayOfFloat2[(j + 0)], paramArrayOfFloat2[(j + 1)], paramArrayOfFloat2[(j + 2)], paramArrayOfFloat2[(j + 3)]);
          f3 = paramArrayOfFloat2[(j + 0)];
          f4 = paramArrayOfFloat2[(j + 1)];
          f1 = paramArrayOfFloat2[(j + 2)];
          f2 = paramArrayOfFloat2[(j + 3)];
          break;
        case 'M': 
          f2 = paramArrayOfFloat2[(j + 0)];
          f1 = paramArrayOfFloat2[(j + 1)];
          if (j > 0)
          {
            localPath.lineTo(paramArrayOfFloat2[(j + 0)], paramArrayOfFloat2[(j + 1)]);
            f3 = f2;
            f2 = f1;
            f1 = f3;
            f3 = f8;
            f4 = f7;
          }
          else
          {
            localPath.moveTo(paramArrayOfFloat2[(j + 0)], paramArrayOfFloat2[(j + 1)]);
            f3 = f2;
            f4 = f1;
            f5 = f2;
            f6 = f1;
            f2 = f4;
            f1 = f3;
            f3 = f8;
            f4 = f7;
          }
          break;
        case 'L': 
          localPath.lineTo(paramArrayOfFloat2[(j + 0)], paramArrayOfFloat2[(j + 1)]);
          f1 = paramArrayOfFloat2[(j + 0)];
          f2 = paramArrayOfFloat2[(j + 1)];
          f3 = f8;
          f4 = f7;
          break;
        case 'H': 
          localPath.lineTo(paramArrayOfFloat2[(j + 0)], f2);
          f1 = paramArrayOfFloat2[(j + 0)];
          f3 = f8;
          f4 = f7;
          break;
        case 'C': 
          paramPath.cubicTo(paramArrayOfFloat2[(j + 0)], paramArrayOfFloat2[(j + 1)], paramArrayOfFloat2[(j + 2)], paramArrayOfFloat2[(j + 3)], paramArrayOfFloat2[(j + 4)], paramArrayOfFloat2[(j + 5)]);
          f1 = paramArrayOfFloat2[(j + 4)];
          f2 = paramArrayOfFloat2[(j + 5)];
          f3 = paramArrayOfFloat2[(j + 2)];
          f4 = paramArrayOfFloat2[(j + 3)];
          break;
        case 'A': 
          label1558:
          f3 = paramArrayOfFloat2[(j + 5)];
          f7 = paramArrayOfFloat2[(j + 6)];
          f9 = paramArrayOfFloat2[(j + 0)];
          f4 = paramArrayOfFloat2[(j + 1)];
          f8 = paramArrayOfFloat2[(j + 2)];
          if (paramArrayOfFloat2[(j + 3)] != 0.0F) {
            bool1 = true;
          } else {
            bool1 = false;
          }
          if (paramArrayOfFloat2[(j + 4)] != 0.0F) {
            bool2 = true;
          } else {
            bool2 = false;
          }
          drawArc(paramPath, f1, f2, f3, f7, f9, f4, f8, bool1, bool2);
          f3 = paramArrayOfFloat2[(j + 5)];
          f4 = paramArrayOfFloat2[(j + 6)];
          f1 = f3;
          f2 = f4;
        }
        paramChar1 = paramChar2;
        j += i;
        f8 = f3;
        f7 = f4;
      }
      paramArrayOfFloat1[0] = f1;
      paramArrayOfFloat1[1] = f2;
      paramArrayOfFloat1[2] = f8;
      paramArrayOfFloat1[3] = f7;
      paramArrayOfFloat1[4] = f5;
      paramArrayOfFloat1[5] = f6;
    }
    
    private static void arcToBezier(Path paramPath, double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, double paramDouble5, double paramDouble6, double paramDouble7, double paramDouble8, double paramDouble9)
    {
      int i = (int)Math.ceil(Math.abs(paramDouble9 * 4.0D / 3.141592653589793D));
      double d4 = Math.cos(paramDouble7);
      double d7 = Math.sin(paramDouble7);
      paramDouble7 = Math.cos(paramDouble8);
      double d8 = Math.sin(paramDouble8);
      double d3 = -paramDouble3;
      double d2 = -paramDouble3 * d7 * d8 + paramDouble4 * d4 * paramDouble7;
      double d6 = paramDouble9 / i;
      int j = 0;
      double d1 = paramDouble5;
      d3 = d3 * d4 * d8 - paramDouble4 * d7 * paramDouble7;
      double d5 = paramDouble8;
      paramDouble9 = d2;
      d2 = paramDouble6;
      paramDouble8 = d8;
      paramDouble5 = d7;
      paramDouble6 = d4;
      d4 = d6;
      while (j < i)
      {
        double d10 = d5 + d4;
        double d11 = Math.sin(d10);
        d8 = Math.cos(d10);
        double d9 = paramDouble1 + paramDouble3 * paramDouble6 * d8 - paramDouble4 * paramDouble5 * d11;
        d7 = paramDouble2 + paramDouble3 * paramDouble5 * d8 + paramDouble4 * paramDouble6 * d11;
        d6 = -paramDouble3 * paramDouble6 * d11 - paramDouble4 * paramDouble5 * d8;
        d8 = -paramDouble3 * paramDouble5 * d11 + paramDouble4 * paramDouble6 * d8;
        d11 = Math.tan((d10 - d5) / 2.0D);
        d5 = Math.sin(d10 - d5) * (Math.sqrt(d11 * 3.0D * d11 + 4.0D) - 1.0D) / 3.0D;
        paramPath.rLineTo(0.0F, 0.0F);
        paramPath.cubicTo((float)(d1 + d5 * d3), (float)(d2 + d5 * paramDouble9), (float)(d9 - d5 * d6), (float)(d7 - d5 * d8), (float)d9, (float)d7);
        d5 = d10;
        d1 = d9;
        d2 = d7;
        d3 = d6;
        paramDouble9 = d8;
        j++;
      }
    }
    
    private static void drawArc(Path paramPath, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6, float paramFloat7, boolean paramBoolean1, boolean paramBoolean2)
    {
      double d6 = Math.toRadians(paramFloat7);
      double d7 = Math.cos(d6);
      double d5 = Math.sin(d6);
      double d9 = (paramFloat1 * d7 + paramFloat2 * d5) / paramFloat5;
      double d8 = (-paramFloat1 * d5 + paramFloat2 * d7) / paramFloat6;
      double d1 = (paramFloat3 * d7 + paramFloat4 * d5) / paramFloat5;
      double d4 = (-paramFloat3 * d5 + paramFloat4 * d7) / paramFloat6;
      double d10 = d9 - d1;
      double d11 = d8 - d4;
      double d3 = (d9 + d1) / 2.0D;
      double d2 = (d8 + d4) / 2.0D;
      double d13 = d10 * d10 + d11 * d11;
      if (d13 == 0.0D)
      {
        Log.w("PathParser", " Points are coincident");
        return;
      }
      double d12 = 1.0D / d13 - 0.25D;
      if (d12 < 0.0D)
      {
        Log.w("PathParser", "Points are too far apart " + d13);
        float f = (float)(Math.sqrt(d13) / 1.99999D);
        drawArc(paramPath, paramFloat1, paramFloat2, paramFloat3, paramFloat4, paramFloat5 * f, paramFloat6 * f, paramFloat7, paramBoolean1, paramBoolean2);
        return;
      }
      d12 = Math.sqrt(d12);
      d10 = d12 * d10;
      d11 = d12 * d11;
      if (paramBoolean1 == paramBoolean2)
      {
        d3 -= d11;
        d2 += d10;
      }
      else
      {
        d3 += d11;
        d2 -= d10;
      }
      d8 = Math.atan2(d8 - d2, d9 - d3);
      d4 = Math.atan2(d4 - d2, d1 - d3) - d8;
      if (d4 >= 0.0D) {
        paramBoolean1 = true;
      } else {
        paramBoolean1 = false;
      }
      d1 = d4;
      if (paramBoolean2 != paramBoolean1) {
        if (d4 > 0.0D) {
          d1 = d4 - 6.283185307179586D;
        } else {
          d1 = d4 + 6.283185307179586D;
        }
      }
      d3 *= paramFloat5;
      d2 = paramFloat6 * d2;
      arcToBezier(paramPath, d3 * d7 - d2 * d5, d3 * d5 + d2 * d7, paramFloat5, paramFloat6, paramFloat1, paramFloat2, d6, d8, d1);
    }
    
    public static void nodesToPath(PathDataNode[] paramArrayOfPathDataNode, Path paramPath)
    {
      float[] arrayOfFloat = new float[6];
      char c = 'm';
      for (int i = 0; i < paramArrayOfPathDataNode.length; i++)
      {
        addCommand(paramPath, arrayOfFloat, c, paramArrayOfPathDataNode[i].mType, paramArrayOfPathDataNode[i].mParams);
        c = paramArrayOfPathDataNode[i].mType;
      }
    }
    
    public void interpolatePathDataNode(PathDataNode paramPathDataNode1, PathDataNode paramPathDataNode2, float paramFloat)
    {
      this.mType = paramPathDataNode1.mType;
      for (int i = 0;; i++)
      {
        float[] arrayOfFloat = paramPathDataNode1.mParams;
        if (i >= arrayOfFloat.length) {
          break;
        }
        this.mParams[i] = (arrayOfFloat[i] * (1.0F - paramFloat) + paramPathDataNode2.mParams[i] * paramFloat);
      }
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/core/graphics/PathParser.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */