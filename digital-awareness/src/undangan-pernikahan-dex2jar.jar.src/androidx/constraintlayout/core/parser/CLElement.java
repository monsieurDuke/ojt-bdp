package androidx.constraintlayout.core.parser;

import java.io.PrintStream;
import mt.Log229316;
import mt.Log5ECF72;
import mt.LogE84000;

public class CLElement
{
  protected static int BASE_INDENT = 2;
  protected static int MAX_LINE = 80;
  protected long end = Long.MAX_VALUE;
  private int line;
  protected CLContainer mContainer;
  private final char[] mContent;
  protected long start = -1L;
  
  public CLElement(char[] paramArrayOfChar)
  {
    this.mContent = paramArrayOfChar;
  }
  
  protected void addIndent(StringBuilder paramStringBuilder, int paramInt)
  {
    for (int i = 0; i < paramInt; i++) {
      paramStringBuilder.append(' ');
    }
  }
  
  public String content()
  {
    String str = new String(this.mContent);
    Log5ECF72.a(str);
    LogE84000.a(str);
    Log229316.a(str);
    long l2 = this.end;
    if (l2 != Long.MAX_VALUE)
    {
      l1 = this.start;
      if (l2 >= l1) {
        return str.substring((int)l1, (int)l2 + 1);
      }
    }
    long l1 = this.start;
    return str.substring((int)l1, (int)l1 + 1);
  }
  
  public CLElement getContainer()
  {
    return this.mContainer;
  }
  
  protected String getDebugName()
  {
    if (CLParser.DEBUG) {
      return getStrClass() + " -> ";
    }
    return "";
  }
  
  public long getEnd()
  {
    return this.end;
  }
  
  public float getFloat()
  {
    if ((this instanceof CLNumber)) {
      return ((CLNumber)this).getFloat();
    }
    return NaN.0F;
  }
  
  public int getInt()
  {
    if ((this instanceof CLNumber)) {
      return ((CLNumber)this).getInt();
    }
    return 0;
  }
  
  public int getLine()
  {
    return this.line;
  }
  
  public long getStart()
  {
    return this.start;
  }
  
  protected String getStrClass()
  {
    String str = getClass().toString();
    return str.substring(str.lastIndexOf('.') + 1);
  }
  
  public boolean isDone()
  {
    boolean bool;
    if (this.end != Long.MAX_VALUE) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public boolean isStarted()
  {
    boolean bool;
    if (this.start > -1L) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public boolean notStarted()
  {
    boolean bool;
    if (this.start == -1L) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public void setContainer(CLContainer paramCLContainer)
  {
    this.mContainer = paramCLContainer;
  }
  
  public void setEnd(long paramLong)
  {
    if (this.end != Long.MAX_VALUE) {
      return;
    }
    this.end = paramLong;
    if (CLParser.DEBUG) {
      System.out.println("closing " + hashCode() + " -> " + this);
    }
    CLContainer localCLContainer = this.mContainer;
    if (localCLContainer != null) {
      localCLContainer.add(this);
    }
  }
  
  public void setLine(int paramInt)
  {
    this.line = paramInt;
  }
  
  public void setStart(long paramLong)
  {
    this.start = paramLong;
  }
  
  protected String toFormattedJSON(int paramInt1, int paramInt2)
  {
    return "";
  }
  
  protected String toJSON()
  {
    return "";
  }
  
  public String toString()
  {
    long l1 = this.start;
    long l2 = this.end;
    if ((l1 <= l2) && (l2 != Long.MAX_VALUE))
    {
      String str = new String(this.mContent);
      Log5ECF72.a(str);
      LogE84000.a(str);
      Log229316.a(str);
      str = str.substring((int)this.start, (int)this.end + 1);
      return getStrClass() + " (" + this.start + " : " + this.end + ") <<" + str + ">>";
    }
    return getClass() + " (INVALID, " + this.start + "-" + this.end + ")";
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/constraintlayout/core/parser/CLElement.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */