package androidx.constraintlayout.core.parser;

import java.util.ArrayList;
import java.util.Iterator;

public class CLArray
  extends CLContainer
{
  public CLArray(char[] paramArrayOfChar)
  {
    super(paramArrayOfChar);
  }
  
  public static CLElement allocate(char[] paramArrayOfChar)
  {
    return new CLArray(paramArrayOfChar);
  }
  
  protected String toFormattedJSON(int paramInt1, int paramInt2)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    Object localObject = toJSON();
    if ((paramInt2 <= 0) && (((String)localObject).length() + paramInt1 < MAX_LINE))
    {
      localStringBuilder.append((String)localObject);
    }
    else
    {
      localStringBuilder.append("[\n");
      int i = 1;
      Iterator localIterator = this.mElements.iterator();
      while (localIterator.hasNext())
      {
        localObject = (CLElement)localIterator.next();
        if (i == 0) {
          localStringBuilder.append(",\n");
        } else {
          i = 0;
        }
        addIndent(localStringBuilder, BASE_INDENT + paramInt1);
        localStringBuilder.append(((CLElement)localObject).toFormattedJSON(BASE_INDENT + paramInt1, paramInt2 - 1));
      }
      localStringBuilder.append("\n");
      addIndent(localStringBuilder, paramInt1);
      localStringBuilder.append("]");
    }
    return localStringBuilder.toString();
  }
  
  protected String toJSON()
  {
    StringBuilder localStringBuilder = new StringBuilder(getDebugName() + "[");
    int j = 1;
    for (int i = 0; i < this.mElements.size(); i++)
    {
      if (j == 0) {
        localStringBuilder.append(", ");
      } else {
        j = 0;
      }
      localStringBuilder.append(((CLElement)this.mElements.get(i)).toJSON());
    }
    return localStringBuilder + "]";
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/constraintlayout/core/parser/CLArray.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */