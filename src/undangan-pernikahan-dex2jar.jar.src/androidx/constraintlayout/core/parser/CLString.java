package androidx.constraintlayout.core.parser;

public class CLString
  extends CLElement
{
  public CLString(char[] paramArrayOfChar)
  {
    super(paramArrayOfChar);
  }
  
  public static CLElement allocate(char[] paramArrayOfChar)
  {
    return new CLString(paramArrayOfChar);
  }
  
  protected String toFormattedJSON(int paramInt1, int paramInt2)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    addIndent(localStringBuilder, paramInt1);
    localStringBuilder.append("'");
    localStringBuilder.append(content());
    localStringBuilder.append("'");
    return localStringBuilder.toString();
  }
  
  protected String toJSON()
  {
    return "'" + content() + "'";
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/constraintlayout/core/parser/CLString.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */