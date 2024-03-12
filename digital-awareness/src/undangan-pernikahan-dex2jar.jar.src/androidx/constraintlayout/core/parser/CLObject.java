package androidx.constraintlayout.core.parser;

import java.util.ArrayList;
import java.util.Iterator;

public class CLObject
  extends CLContainer
  implements Iterable<CLKey>
{
  public CLObject(char[] paramArrayOfChar)
  {
    super(paramArrayOfChar);
  }
  
  public static CLObject allocate(char[] paramArrayOfChar)
  {
    return new CLObject(paramArrayOfChar);
  }
  
  public Iterator iterator()
  {
    return new CLObjectIterator(this);
  }
  
  public String toFormattedJSON()
  {
    return toFormattedJSON(0, 0);
  }
  
  public String toFormattedJSON(int paramInt1, int paramInt2)
  {
    StringBuilder localStringBuilder = new StringBuilder(getDebugName());
    localStringBuilder.append("{\n");
    int i = 1;
    Iterator localIterator = this.mElements.iterator();
    while (localIterator.hasNext())
    {
      CLElement localCLElement = (CLElement)localIterator.next();
      if (i == 0) {
        localStringBuilder.append(",\n");
      } else {
        i = 0;
      }
      localStringBuilder.append(localCLElement.toFormattedJSON(BASE_INDENT + paramInt1, paramInt2 - 1));
    }
    localStringBuilder.append("\n");
    addIndent(localStringBuilder, paramInt1);
    localStringBuilder.append("}");
    return localStringBuilder.toString();
  }
  
  public String toJSON()
  {
    StringBuilder localStringBuilder = new StringBuilder(getDebugName() + "{ ");
    int i = 1;
    Iterator localIterator = this.mElements.iterator();
    while (localIterator.hasNext())
    {
      CLElement localCLElement = (CLElement)localIterator.next();
      if (i == 0) {
        localStringBuilder.append(", ");
      } else {
        i = 0;
      }
      localStringBuilder.append(localCLElement.toJSON());
    }
    localStringBuilder.append(" }");
    return localStringBuilder.toString();
  }
  
  private class CLObjectIterator
    implements Iterator
  {
    int index = 0;
    CLObject myObject;
    
    public CLObjectIterator(CLObject paramCLObject)
    {
      this.myObject = paramCLObject;
    }
    
    public boolean hasNext()
    {
      boolean bool;
      if (this.index < this.myObject.size()) {
        bool = true;
      } else {
        bool = false;
      }
      return bool;
    }
    
    public Object next()
    {
      CLKey localCLKey = (CLKey)this.myObject.mElements.get(this.index);
      this.index += 1;
      return localCLKey;
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/constraintlayout/core/parser/CLObject.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */