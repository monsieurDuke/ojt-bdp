package androidx.constraintlayout.core.parser;

import java.util.ArrayList;

public class CLKey
  extends CLContainer
{
  private static ArrayList<String> sections;
  
  static
  {
    ArrayList localArrayList = new ArrayList();
    sections = localArrayList;
    localArrayList.add("ConstraintSets");
    sections.add("Variables");
    sections.add("Generate");
    sections.add("Transitions");
    sections.add("KeyFrames");
    sections.add("KeyAttributes");
    sections.add("KeyPositions");
    sections.add("KeyCycles");
  }
  
  public CLKey(char[] paramArrayOfChar)
  {
    super(paramArrayOfChar);
  }
  
  public static CLElement allocate(String paramString, CLElement paramCLElement)
  {
    CLKey localCLKey = new CLKey(paramString.toCharArray());
    localCLKey.setStart(0L);
    localCLKey.setEnd(paramString.length() - 1);
    localCLKey.set(paramCLElement);
    return localCLKey;
  }
  
  public static CLElement allocate(char[] paramArrayOfChar)
  {
    return new CLKey(paramArrayOfChar);
  }
  
  public String getName()
  {
    return content();
  }
  
  public CLElement getValue()
  {
    if (this.mElements.size() > 0) {
      return (CLElement)this.mElements.get(0);
    }
    return null;
  }
  
  public void set(CLElement paramCLElement)
  {
    if (this.mElements.size() > 0) {
      this.mElements.set(0, paramCLElement);
    } else {
      this.mElements.add(paramCLElement);
    }
  }
  
  protected String toFormattedJSON(int paramInt1, int paramInt2)
  {
    StringBuilder localStringBuilder = new StringBuilder(getDebugName());
    addIndent(localStringBuilder, paramInt1);
    String str = content();
    if (this.mElements.size() > 0)
    {
      localStringBuilder.append(str);
      localStringBuilder.append(": ");
      if (sections.contains(str)) {
        paramInt2 = 3;
      }
      if (paramInt2 > 0)
      {
        localStringBuilder.append(((CLElement)this.mElements.get(0)).toFormattedJSON(paramInt1, paramInt2 - 1));
      }
      else
      {
        str = ((CLElement)this.mElements.get(0)).toJSON();
        if (str.length() + paramInt1 < MAX_LINE) {
          localStringBuilder.append(str);
        } else {
          localStringBuilder.append(((CLElement)this.mElements.get(0)).toFormattedJSON(paramInt1, paramInt2 - 1));
        }
      }
      return localStringBuilder.toString();
    }
    return str + ": <> ";
  }
  
  protected String toJSON()
  {
    if (this.mElements.size() > 0) {
      return getDebugName() + content() + ": " + ((CLElement)this.mElements.get(0)).toJSON();
    }
    return getDebugName() + content() + ": <> ";
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/constraintlayout/core/parser/CLKey.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */