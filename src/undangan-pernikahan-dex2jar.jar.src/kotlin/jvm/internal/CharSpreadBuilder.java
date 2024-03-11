package kotlin.jvm.internal;

import kotlin.Metadata;

@Metadata(d1={"\000$\n\002\030\002\n\002\030\002\n\002\020\031\n\000\n\002\020\b\n\002\b\003\n\002\020\002\n\000\n\002\020\f\n\002\b\003\030\0002\b\022\004\022\0020\0020\001B\r\022\006\020\003\032\0020\004¢\006\002\020\005J\016\020\007\032\0020\b2\006\020\t\032\0020\nJ\006\020\013\032\0020\002J\f\020\f\032\0020\004*\0020\002H\024R\016\020\006\032\0020\002X\004¢\006\002\n\000¨\006\r"}, d2={"Lkotlin/jvm/internal/CharSpreadBuilder;", "Lkotlin/jvm/internal/PrimitiveSpreadBuilder;", "", "size", "", "(I)V", "values", "add", "", "value", "", "toArray", "getSize", "kotlin-stdlib"}, k=1, mv={1, 7, 1}, xi=48)
public final class CharSpreadBuilder
  extends PrimitiveSpreadBuilder<char[]>
{
  private final char[] values;
  
  public CharSpreadBuilder(int paramInt)
  {
    super(paramInt);
    this.values = new char[paramInt];
  }
  
  public final void add(char paramChar)
  {
    char[] arrayOfChar = this.values;
    int i = getPosition();
    setPosition(i + 1);
    arrayOfChar[i] = paramChar;
  }
  
  protected int getSize(char[] paramArrayOfChar)
  {
    Intrinsics.checkNotNullParameter(paramArrayOfChar, "<this>");
    return paramArrayOfChar.length;
  }
  
  public final char[] toArray()
  {
    return (char[])toArray(this.values, new char[size()]);
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlin/jvm/internal/CharSpreadBuilder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */