package kotlin;

@Metadata(d1={"\000\016\n\000\n\002\020\b\n\002\020\f\n\002\b\006\032\021\020\007\032\0020\0022\006\020\000\032\0020\001H\b\"\037\020\000\032\0020\001*\0020\0028Æ\002X\004¢\006\f\022\004\b\003\020\004\032\004\b\005\020\006¨\006\b"}, d2={"code", "", "", "getCode$annotations", "(C)V", "getCode", "(C)I", "Char", "kotlin-stdlib"}, k=2, mv={1, 7, 1}, xi=48)
public final class CharCodeKt
{
  private static final char Char(int paramInt)
  {
    if ((paramInt >= 0) && (paramInt <= 65535)) {
      return (char)paramInt;
    }
    throw new IllegalArgumentException("Invalid Char code: " + paramInt);
  }
  
  private static final int getCode(char paramChar)
  {
    return paramChar;
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlin/CharCodeKt.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */