package androidx.constraintlayout.core.motion.parse;

import androidx.constraintlayout.core.motion.utils.TypedBundle;
import androidx.constraintlayout.core.parser.CLElement;
import androidx.constraintlayout.core.parser.CLKey;
import androidx.constraintlayout.core.parser.CLObject;
import androidx.constraintlayout.core.parser.CLParser;
import androidx.constraintlayout.core.parser.CLParsingException;
import java.io.PrintStream;

public class KeyParser
{
  public static void main(String[] paramArrayOfString)
  {
    parseAttributes("{frame:22,\ntarget:'widget1',\neasing:'easeIn',\ncurveFit:'spline',\nprogress:0.3,\nalpha:0.2,\nelevation:0.7,\nrotationZ:23,\nrotationX:25.0,\nrotationY:27.0,\npivotX:15,\npivotY:17,\npivotTarget:'32',\npathRotate:23,\nscaleX:0.5,\nscaleY:0.7,\ntranslationX:5,\ntranslationY:7,\ntranslationZ:11,\n}");
  }
  
  private static TypedBundle parse(String paramString, Ids paramIds, DataType paramDataType)
  {
    TypedBundle localTypedBundle = new TypedBundle();
    try
    {
      paramString = CLParser.parse(paramString);
      int j = paramString.size();
      for (int i = 0; i < j; i++)
      {
        Object localObject1 = (CLKey)paramString.get(i);
        String str = ((CLKey)localObject1).content();
        localObject1 = ((CLKey)localObject1).getValue();
        int k = paramIds.get(str);
        Object localObject2;
        if (k == -1)
        {
          localObject1 = System.err;
          localObject2 = new java/lang/StringBuilder;
          ((StringBuilder)localObject2).<init>();
          ((PrintStream)localObject1).println("unknown type " + str);
        }
        else
        {
          int m = paramDataType.get(k);
          Object localObject3;
          switch (m)
          {
          default: 
            break;
          case 8: 
            localTypedBundle.add(k, ((CLElement)localObject1).content());
            localObject2 = System.out;
            localObject3 = new java/lang/StringBuilder;
            ((StringBuilder)localObject3).<init>();
            ((PrintStream)localObject2).println("parse " + str + " STRING_MASK > " + ((CLElement)localObject1).content());
            break;
          case 4: 
            localTypedBundle.add(k, ((CLElement)localObject1).getFloat());
            localObject3 = System.out;
            localObject2 = new java/lang/StringBuilder;
            ((StringBuilder)localObject2).<init>();
            ((PrintStream)localObject3).println("parse " + str + " FLOAT_MASK > " + ((CLElement)localObject1).getFloat());
            break;
          case 2: 
            localTypedBundle.add(k, ((CLElement)localObject1).getInt());
            localObject2 = System.out;
            localObject3 = new java/lang/StringBuilder;
            ((StringBuilder)localObject3).<init>();
            ((PrintStream)localObject2).println("parse " + str + " INT_MASK > " + ((CLElement)localObject1).getInt());
            break;
          case 1: 
            localTypedBundle.add(k, paramString.getBoolean(i));
          }
        }
      }
    }
    catch (CLParsingException paramString)
    {
      paramString.printStackTrace();
    }
    return localTypedBundle;
  }
  
  public static TypedBundle parseAttributes(String paramString)
  {
    return parse(paramString, new KeyParser..ExternalSyntheticLambda0(), new KeyParser..ExternalSyntheticLambda1());
  }
  
  private static abstract interface DataType
  {
    public abstract int get(int paramInt);
  }
  
  private static abstract interface Ids
  {
    public abstract int get(String paramString);
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/constraintlayout/core/motion/parse/KeyParser.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */