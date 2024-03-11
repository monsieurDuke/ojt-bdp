package kotlin;

import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

@Metadata(d1={"\000\032\n\000\n\002\030\002\n\000\n\002\020\b\n\000\n\002\030\002\n\002\030\002\n\002\b\006\0320\020\000\032\0020\0012\006\020\002\032\0020\0032\022\020\004\032\016\022\004\022\0020\003\022\004\022\0020\0060\005H\bø\001\000ø\001\001¢\006\002\020\007\032\037\020\b\032\0020\0012\n\020\t\032\0020\001\"\0020\006H\bø\001\001¢\006\004\b\n\020\013\002\013\n\005\b20\001\n\002\b\031¨\006\f"}, d2={"UByteArray", "Lkotlin/UByteArray;", "size", "", "init", "Lkotlin/Function1;", "Lkotlin/UByte;", "(ILkotlin/jvm/functions/Function1;)[B", "ubyteArrayOf", "elements", "ubyteArrayOf-GBYM_sE", "([B)[B", "kotlin-stdlib"}, k=2, mv={1, 7, 1}, xi=48)
public final class UByteArrayKt
{
  private static final byte[] UByteArray(int paramInt, Function1<? super Integer, UByte> paramFunction1)
  {
    Intrinsics.checkNotNullParameter(paramFunction1, "init");
    byte[] arrayOfByte = new byte[paramInt];
    for (int i = 0; i < paramInt; i++) {
      arrayOfByte[i] = ((UByte)paramFunction1.invoke(Integer.valueOf(i))).unbox-impl();
    }
    return UByteArray.constructor-impl(arrayOfByte);
  }
  
  private static final byte[] ubyteArrayOf-GBYM_sE(byte... paramVarArgs)
  {
    Intrinsics.checkNotNullParameter(paramVarArgs, "elements");
    return paramVarArgs;
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlin/UByteArrayKt.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */