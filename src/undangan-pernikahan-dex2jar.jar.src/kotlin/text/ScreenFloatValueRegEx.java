package kotlin.text;

import kotlin.Metadata;

@Metadata(d1={"\000\022\n\002\030\002\n\002\020\000\n\002\b\002\n\002\030\002\n\000\bÂ\002\030\0002\0020\001B\007\b\002¢\006\002\020\002R\020\020\003\032\0020\0048\006X\004¢\006\002\n\000¨\006\005"}, d2={"Lkotlin/text/ScreenFloatValueRegEx;", "", "()V", "value", "Lkotlin/text/Regex;", "kotlin-stdlib"}, k=1, mv={1, 7, 1}, xi=48)
final class ScreenFloatValueRegEx
{
  public static final ScreenFloatValueRegEx INSTANCE = new ScreenFloatValueRegEx();
  public static final Regex value;
  
  static
  {
    Object localObject1 = INSTANCE;
    localObject1 = "[eE][+-]?" + "(\\p{Digit}+)";
    Object localObject2 = new StringBuilder().append("(0[xX]").append("(\\p{XDigit}+)").append("(\\.)?)|(0[xX]");
    localObject2 = ((StringBuilder)localObject2).append("(\\p{XDigit}+)");
    localObject2 = ((StringBuilder)localObject2).append("?(\\.)");
    localObject2 = "(\\p{XDigit}+)" + ')';
    StringBuilder localStringBuilder = new StringBuilder().append('(').append("(\\p{Digit}+)").append("(\\.)?(").append("(\\p{Digit}+)").append("?)(").append((String)localObject1).append(")?)|(\\.(");
    localStringBuilder = localStringBuilder.append("(\\p{Digit}+)");
    localStringBuilder = localStringBuilder.append(")(");
    localObject1 = localStringBuilder.append((String)localObject1);
    localObject1 = ((StringBuilder)localObject1).append(")?)|((");
    localObject1 = ((StringBuilder)localObject1).append((String)localObject2);
    localObject1 = ((StringBuilder)localObject1).append(")[pP][+-]?");
    localObject1 = "(\\p{Digit}+)" + ')';
    value = new Regex("[\\x00-\\x20]*[+-]?(NaN|Infinity|((" + (String)localObject1 + ")[fFdD]?))[\\x00-\\x20]*");
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlin/text/ScreenFloatValueRegEx.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */