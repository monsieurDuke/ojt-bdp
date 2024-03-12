package androidx.constraintlayout.core.parser;

public class CLToken
  extends CLElement
{
  int index = 0;
  char[] tokenFalse = "false".toCharArray();
  char[] tokenNull = "null".toCharArray();
  char[] tokenTrue = "true".toCharArray();
  Type type = Type.UNKNOWN;
  
  public CLToken(char[] paramArrayOfChar)
  {
    super(paramArrayOfChar);
  }
  
  public static CLElement allocate(char[] paramArrayOfChar)
  {
    return new CLToken(paramArrayOfChar);
  }
  
  public boolean getBoolean()
    throws CLParsingException
  {
    if (this.type == Type.TRUE) {
      return true;
    }
    if (this.type == Type.FALSE) {
      return false;
    }
    throw new CLParsingException("this token is not a boolean: <" + content() + ">", this);
  }
  
  public Type getType()
  {
    return this.type;
  }
  
  public boolean isNull()
    throws CLParsingException
  {
    if (this.type == Type.NULL) {
      return true;
    }
    throw new CLParsingException("this token is not a null: <" + content() + ">", this);
  }
  
  protected String toFormattedJSON(int paramInt1, int paramInt2)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    addIndent(localStringBuilder, paramInt1);
    localStringBuilder.append(content());
    return localStringBuilder.toString();
  }
  
  protected String toJSON()
  {
    if (CLParser.DEBUG) {
      return "<" + content() + ">";
    }
    return content();
  }
  
  public boolean validate(char paramChar, long paramLong)
  {
    boolean bool4 = false;
    int i = 1.$SwitchMap$androidx$constraintlayout$core$parser$CLToken$Type[this.type.ordinal()];
    boolean bool3 = false;
    boolean bool1 = false;
    boolean bool2 = false;
    char[] arrayOfChar;
    switch (i)
    {
    default: 
      bool1 = bool4;
      break;
    case 4: 
      arrayOfChar = this.tokenTrue;
      i = this.index;
      if (arrayOfChar[i] == paramChar)
      {
        this.type = Type.TRUE;
        bool1 = true;
      }
      else if (this.tokenFalse[i] == paramChar)
      {
        this.type = Type.FALSE;
        bool1 = true;
      }
      else
      {
        bool1 = bool4;
        if (this.tokenNull[i] == paramChar)
        {
          this.type = Type.NULL;
          bool1 = true;
        }
      }
      break;
    case 3: 
      arrayOfChar = this.tokenNull;
      i = this.index;
      bool1 = bool2;
      if (arrayOfChar[i] == paramChar) {
        bool1 = true;
      }
      bool2 = bool1;
      bool1 = bool2;
      if (bool2)
      {
        bool1 = bool2;
        if (i + 1 == arrayOfChar.length)
        {
          setEnd(paramLong);
          bool1 = bool2;
        }
      }
      break;
    case 2: 
      arrayOfChar = this.tokenFalse;
      i = this.index;
      bool1 = bool3;
      if (arrayOfChar[i] == paramChar) {
        bool1 = true;
      }
      bool2 = bool1;
      bool1 = bool2;
      if (bool2)
      {
        bool1 = bool2;
        if (i + 1 == arrayOfChar.length)
        {
          setEnd(paramLong);
          bool1 = bool2;
        }
      }
      break;
    case 1: 
      arrayOfChar = this.tokenTrue;
      i = this.index;
      if (arrayOfChar[i] == paramChar) {
        bool1 = true;
      }
      bool2 = bool1;
      bool1 = bool2;
      if (bool2)
      {
        bool1 = bool2;
        if (i + 1 == arrayOfChar.length)
        {
          setEnd(paramLong);
          bool1 = bool2;
        }
      }
      break;
    }
    this.index += 1;
    return bool1;
  }
  
  static enum Type
  {
    private static final Type[] $VALUES;
    
    static
    {
      Type localType2 = new Type("UNKNOWN", 0);
      UNKNOWN = localType2;
      Type localType4 = new Type("TRUE", 1);
      TRUE = localType4;
      Type localType3 = new Type("FALSE", 2);
      FALSE = localType3;
      Type localType1 = new Type("NULL", 3);
      NULL = localType1;
      $VALUES = new Type[] { localType2, localType4, localType3, localType1 };
    }
    
    private Type() {}
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/constraintlayout/core/parser/CLToken.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */