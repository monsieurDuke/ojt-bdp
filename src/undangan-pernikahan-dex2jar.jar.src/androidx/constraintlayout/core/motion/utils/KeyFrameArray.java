package androidx.constraintlayout.core.motion.utils;

import androidx.constraintlayout.core.motion.CustomAttribute;
import androidx.constraintlayout.core.motion.CustomVariable;
import java.io.PrintStream;
import java.util.Arrays;
import mt.Log229316;
import mt.Log5ECF72;
import mt.LogE84000;

public class KeyFrameArray
{
  public static class CustomArray
  {
    private static final int EMPTY = 999;
    int count;
    int[] keys = new int[101];
    CustomAttribute[] values = new CustomAttribute[101];
    
    public CustomArray()
    {
      clear();
    }
    
    public void append(int paramInt, CustomAttribute paramCustomAttribute)
    {
      if (this.values[paramInt] != null) {
        remove(paramInt);
      }
      this.values[paramInt] = paramCustomAttribute;
      paramCustomAttribute = this.keys;
      int i = this.count;
      this.count = (i + 1);
      paramCustomAttribute[i] = paramInt;
      Arrays.sort(paramCustomAttribute);
    }
    
    public void clear()
    {
      Arrays.fill(this.keys, 999);
      Arrays.fill(this.values, null);
      this.count = 0;
    }
    
    public void dump()
    {
      Object localObject2 = System.out;
      Object localObject1 = new StringBuilder().append("V: ");
      String str = Arrays.toString(Arrays.copyOf(this.keys, this.count));
      Log5ECF72.a(str);
      LogE84000.a(str);
      Log229316.a(str);
      ((PrintStream)localObject2).println(str);
      System.out.print("K: [");
      for (int i = 0; i < this.count; i++)
      {
        localObject1 = System.out;
        localObject2 = new StringBuilder();
        if (i == 0) {
          str = "";
        } else {
          str = ", ";
        }
        ((PrintStream)localObject1).print(str + valueAt(i));
      }
      System.out.println("]");
    }
    
    public int keyAt(int paramInt)
    {
      return this.keys[paramInt];
    }
    
    public void remove(int paramInt)
    {
      this.values[paramInt] = null;
      int i = 0;
      int k;
      for (int j = 0;; j++)
      {
        k = this.count;
        if (j >= k) {
          break;
        }
        int[] arrayOfInt = this.keys;
        k = i;
        if (paramInt == arrayOfInt[j])
        {
          arrayOfInt[j] = 999;
          k = i + 1;
        }
        if (j != k) {
          arrayOfInt[j] = arrayOfInt[k];
        }
        i = k + 1;
      }
      this.count = (k - 1);
    }
    
    public int size()
    {
      return this.count;
    }
    
    public CustomAttribute valueAt(int paramInt)
    {
      return this.values[this.keys[paramInt]];
    }
  }
  
  public static class CustomVar
  {
    private static final int EMPTY = 999;
    int count;
    int[] keys = new int[101];
    CustomVariable[] values = new CustomVariable[101];
    
    public CustomVar()
    {
      clear();
    }
    
    public void append(int paramInt, CustomVariable paramCustomVariable)
    {
      if (this.values[paramInt] != null) {
        remove(paramInt);
      }
      this.values[paramInt] = paramCustomVariable;
      paramCustomVariable = this.keys;
      int i = this.count;
      this.count = (i + 1);
      paramCustomVariable[i] = paramInt;
      Arrays.sort(paramCustomVariable);
    }
    
    public void clear()
    {
      Arrays.fill(this.keys, 999);
      Arrays.fill(this.values, null);
      this.count = 0;
    }
    
    public void dump()
    {
      Object localObject1 = System.out;
      Object localObject2 = new StringBuilder().append("V: ");
      String str = Arrays.toString(Arrays.copyOf(this.keys, this.count));
      Log5ECF72.a(str);
      LogE84000.a(str);
      Log229316.a(str);
      ((PrintStream)localObject1).println(str);
      System.out.print("K: [");
      for (int i = 0; i < this.count; i++)
      {
        localObject2 = System.out;
        localObject1 = new StringBuilder();
        if (i == 0) {
          str = "";
        } else {
          str = ", ";
        }
        ((PrintStream)localObject2).print(str + valueAt(i));
      }
      System.out.println("]");
    }
    
    public int keyAt(int paramInt)
    {
      return this.keys[paramInt];
    }
    
    public void remove(int paramInt)
    {
      this.values[paramInt] = null;
      int i = 0;
      int k;
      for (int j = 0;; j++)
      {
        k = this.count;
        if (j >= k) {
          break;
        }
        int[] arrayOfInt = this.keys;
        k = i;
        if (paramInt == arrayOfInt[j])
        {
          arrayOfInt[j] = 999;
          k = i + 1;
        }
        if (j != k) {
          arrayOfInt[j] = arrayOfInt[k];
        }
        i = k + 1;
      }
      this.count = (k - 1);
    }
    
    public int size()
    {
      return this.count;
    }
    
    public CustomVariable valueAt(int paramInt)
    {
      return this.values[this.keys[paramInt]];
    }
  }
  
  static class FloatArray
  {
    private static final int EMPTY = 999;
    int count;
    int[] keys = new int[101];
    float[][] values = new float[101][];
    
    public FloatArray()
    {
      clear();
    }
    
    public void append(int paramInt, float[] paramArrayOfFloat)
    {
      if (this.values[paramInt] != null) {
        remove(paramInt);
      }
      this.values[paramInt] = paramArrayOfFloat;
      paramArrayOfFloat = this.keys;
      int i = this.count;
      this.count = (i + 1);
      paramArrayOfFloat[i] = paramInt;
      Arrays.sort(paramArrayOfFloat);
    }
    
    public void clear()
    {
      Arrays.fill(this.keys, 999);
      Arrays.fill(this.values, null);
      this.count = 0;
    }
    
    public void dump()
    {
      PrintStream localPrintStream = System.out;
      Object localObject1 = new StringBuilder().append("V: ");
      Object localObject2 = Arrays.toString(Arrays.copyOf(this.keys, this.count));
      Log5ECF72.a(localObject2);
      LogE84000.a(localObject2);
      Log229316.a(localObject2);
      localPrintStream.println((String)localObject2);
      System.out.print("K: [");
      for (int i = 0; i < this.count; i++)
      {
        localPrintStream = System.out;
        localObject2 = new StringBuilder();
        if (i == 0) {
          localObject1 = "";
        } else {
          localObject1 = ", ";
        }
        localObject1 = ((StringBuilder)localObject2).append((String)localObject1);
        localObject2 = Arrays.toString(valueAt(i));
        Log5ECF72.a(localObject2);
        LogE84000.a(localObject2);
        Log229316.a(localObject2);
        localPrintStream.print((String)localObject2);
      }
      System.out.println("]");
    }
    
    public int keyAt(int paramInt)
    {
      return this.keys[paramInt];
    }
    
    public void remove(int paramInt)
    {
      this.values[paramInt] = null;
      int i = 0;
      int k;
      for (int j = 0;; j++)
      {
        k = this.count;
        if (j >= k) {
          break;
        }
        int[] arrayOfInt = this.keys;
        k = i;
        if (paramInt == arrayOfInt[j])
        {
          arrayOfInt[j] = 999;
          k = i + 1;
        }
        if (j != k) {
          arrayOfInt[j] = arrayOfInt[k];
        }
        i = k + 1;
      }
      this.count = (k - 1);
    }
    
    public int size()
    {
      return this.count;
    }
    
    public float[] valueAt(int paramInt)
    {
      return this.values[this.keys[paramInt]];
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/constraintlayout/core/motion/utils/KeyFrameArray.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */