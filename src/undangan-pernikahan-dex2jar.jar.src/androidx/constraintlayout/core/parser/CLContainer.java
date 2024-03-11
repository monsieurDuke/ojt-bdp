package androidx.constraintlayout.core.parser;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Iterator;

public class CLContainer
  extends CLElement
{
  ArrayList<CLElement> mElements = new ArrayList();
  
  public CLContainer(char[] paramArrayOfChar)
  {
    super(paramArrayOfChar);
  }
  
  public static CLElement allocate(char[] paramArrayOfChar)
  {
    return new CLContainer(paramArrayOfChar);
  }
  
  public void add(CLElement paramCLElement)
  {
    this.mElements.add(paramCLElement);
    if (CLParser.DEBUG) {
      System.out.println("added element " + paramCLElement + " to " + this);
    }
  }
  
  public CLElement get(int paramInt)
    throws CLParsingException
  {
    if ((paramInt >= 0) && (paramInt < this.mElements.size())) {
      return (CLElement)this.mElements.get(paramInt);
    }
    throw new CLParsingException("no element at index " + paramInt, this);
  }
  
  public CLElement get(String paramString)
    throws CLParsingException
  {
    Iterator localIterator = this.mElements.iterator();
    while (localIterator.hasNext())
    {
      CLKey localCLKey = (CLKey)localIterator.next();
      if (localCLKey.content().equals(paramString)) {
        return localCLKey.getValue();
      }
    }
    throw new CLParsingException("no element for key <" + paramString + ">", this);
  }
  
  public CLArray getArray(int paramInt)
    throws CLParsingException
  {
    CLElement localCLElement = get(paramInt);
    if ((localCLElement instanceof CLArray)) {
      return (CLArray)localCLElement;
    }
    throw new CLParsingException("no array at index " + paramInt, this);
  }
  
  public CLArray getArray(String paramString)
    throws CLParsingException
  {
    CLElement localCLElement = get(paramString);
    if ((localCLElement instanceof CLArray)) {
      return (CLArray)localCLElement;
    }
    throw new CLParsingException("no array found for key <" + paramString + ">, found [" + localCLElement.getStrClass() + "] : " + localCLElement, this);
  }
  
  public CLArray getArrayOrNull(String paramString)
  {
    paramString = getOrNull(paramString);
    if ((paramString instanceof CLArray)) {
      return (CLArray)paramString;
    }
    return null;
  }
  
  public boolean getBoolean(int paramInt)
    throws CLParsingException
  {
    CLElement localCLElement = get(paramInt);
    if ((localCLElement instanceof CLToken)) {
      return ((CLToken)localCLElement).getBoolean();
    }
    throw new CLParsingException("no boolean at index " + paramInt, this);
  }
  
  public boolean getBoolean(String paramString)
    throws CLParsingException
  {
    CLElement localCLElement = get(paramString);
    if ((localCLElement instanceof CLToken)) {
      return ((CLToken)localCLElement).getBoolean();
    }
    throw new CLParsingException("no boolean found for key <" + paramString + ">, found [" + localCLElement.getStrClass() + "] : " + localCLElement, this);
  }
  
  public float getFloat(int paramInt)
    throws CLParsingException
  {
    CLElement localCLElement = get(paramInt);
    if (localCLElement != null) {
      return localCLElement.getFloat();
    }
    throw new CLParsingException("no float at index " + paramInt, this);
  }
  
  public float getFloat(String paramString)
    throws CLParsingException
  {
    CLElement localCLElement = get(paramString);
    if (localCLElement != null) {
      return localCLElement.getFloat();
    }
    throw new CLParsingException("no float found for key <" + paramString + ">, found [" + localCLElement.getStrClass() + "] : " + localCLElement, this);
  }
  
  public float getFloatOrNaN(String paramString)
  {
    paramString = getOrNull(paramString);
    if ((paramString instanceof CLNumber)) {
      return paramString.getFloat();
    }
    return NaN.0F;
  }
  
  public int getInt(int paramInt)
    throws CLParsingException
  {
    CLElement localCLElement = get(paramInt);
    if (localCLElement != null) {
      return localCLElement.getInt();
    }
    throw new CLParsingException("no int at index " + paramInt, this);
  }
  
  public int getInt(String paramString)
    throws CLParsingException
  {
    CLElement localCLElement = get(paramString);
    if (localCLElement != null) {
      return localCLElement.getInt();
    }
    throw new CLParsingException("no int found for key <" + paramString + ">, found [" + localCLElement.getStrClass() + "] : " + localCLElement, this);
  }
  
  public CLObject getObject(int paramInt)
    throws CLParsingException
  {
    CLElement localCLElement = get(paramInt);
    if ((localCLElement instanceof CLObject)) {
      return (CLObject)localCLElement;
    }
    throw new CLParsingException("no object at index " + paramInt, this);
  }
  
  public CLObject getObject(String paramString)
    throws CLParsingException
  {
    CLElement localCLElement = get(paramString);
    if ((localCLElement instanceof CLObject)) {
      return (CLObject)localCLElement;
    }
    throw new CLParsingException("no object found for key <" + paramString + ">, found [" + localCLElement.getStrClass() + "] : " + localCLElement, this);
  }
  
  public CLObject getObjectOrNull(String paramString)
  {
    paramString = getOrNull(paramString);
    if ((paramString instanceof CLObject)) {
      return (CLObject)paramString;
    }
    return null;
  }
  
  public CLElement getOrNull(int paramInt)
  {
    if ((paramInt >= 0) && (paramInt < this.mElements.size())) {
      return (CLElement)this.mElements.get(paramInt);
    }
    return null;
  }
  
  public CLElement getOrNull(String paramString)
  {
    Iterator localIterator = this.mElements.iterator();
    while (localIterator.hasNext())
    {
      CLKey localCLKey = (CLKey)localIterator.next();
      if (localCLKey.content().equals(paramString)) {
        return localCLKey.getValue();
      }
    }
    return null;
  }
  
  public String getString(int paramInt)
    throws CLParsingException
  {
    CLElement localCLElement = get(paramInt);
    if ((localCLElement instanceof CLString)) {
      return localCLElement.content();
    }
    throw new CLParsingException("no string at index " + paramInt, this);
  }
  
  public String getString(String paramString)
    throws CLParsingException
  {
    CLElement localCLElement = get(paramString);
    if ((localCLElement instanceof CLString)) {
      return localCLElement.content();
    }
    String str = null;
    if (localCLElement != null) {
      str = localCLElement.getStrClass();
    }
    throw new CLParsingException("no string found for key <" + paramString + ">, found [" + str + "] : " + localCLElement, this);
  }
  
  public String getStringOrNull(int paramInt)
  {
    CLElement localCLElement = getOrNull(paramInt);
    if ((localCLElement instanceof CLString)) {
      return localCLElement.content();
    }
    return null;
  }
  
  public String getStringOrNull(String paramString)
  {
    paramString = getOrNull(paramString);
    if ((paramString instanceof CLString)) {
      return paramString.content();
    }
    return null;
  }
  
  public boolean has(String paramString)
  {
    Iterator localIterator = this.mElements.iterator();
    while (localIterator.hasNext())
    {
      CLElement localCLElement = (CLElement)localIterator.next();
      if (((localCLElement instanceof CLKey)) && (((CLKey)localCLElement).content().equals(paramString))) {
        return true;
      }
    }
    return false;
  }
  
  public ArrayList<String> names()
  {
    ArrayList localArrayList = new ArrayList();
    Iterator localIterator = this.mElements.iterator();
    while (localIterator.hasNext())
    {
      CLElement localCLElement = (CLElement)localIterator.next();
      if ((localCLElement instanceof CLKey)) {
        localArrayList.add(((CLKey)localCLElement).content());
      }
    }
    return localArrayList;
  }
  
  public void put(String paramString, CLElement paramCLElement)
  {
    Iterator localIterator = this.mElements.iterator();
    while (localIterator.hasNext())
    {
      CLKey localCLKey = (CLKey)localIterator.next();
      if (localCLKey.content().equals(paramString))
      {
        localCLKey.set(paramCLElement);
        return;
      }
    }
    paramString = (CLKey)CLKey.allocate(paramString, paramCLElement);
    this.mElements.add(paramString);
  }
  
  public void putNumber(String paramString, float paramFloat)
  {
    put(paramString, new CLNumber(paramFloat));
  }
  
  public void remove(String paramString)
  {
    Object localObject = new ArrayList();
    Iterator localIterator = this.mElements.iterator();
    while (localIterator.hasNext())
    {
      CLElement localCLElement = (CLElement)localIterator.next();
      if (((CLKey)localCLElement).content().equals(paramString)) {
        ((ArrayList)localObject).add(localCLElement);
      }
    }
    paramString = ((ArrayList)localObject).iterator();
    while (paramString.hasNext())
    {
      localObject = (CLElement)paramString.next();
      this.mElements.remove(localObject);
    }
  }
  
  public int size()
  {
    return this.mElements.size();
  }
  
  public String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    Iterator localIterator = this.mElements.iterator();
    while (localIterator.hasNext())
    {
      CLElement localCLElement = (CLElement)localIterator.next();
      if (localStringBuilder.length() > 0) {
        localStringBuilder.append("; ");
      }
      localStringBuilder.append(localCLElement);
    }
    return super.toString() + " = <" + localStringBuilder + " >";
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/constraintlayout/core/parser/CLContainer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */