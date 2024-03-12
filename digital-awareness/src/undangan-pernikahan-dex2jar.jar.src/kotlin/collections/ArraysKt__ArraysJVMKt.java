package kotlin.collections;

import java.lang.reflect.Array;
import java.nio.charset.Charset;
import java.util.Arrays;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import mt.Log229316;
import mt.Log5ECF72;
import mt.LogE84000;

@Metadata(d1={"\0002\n\000\n\002\020\021\n\002\b\003\n\002\020\b\n\002\b\002\n\002\020\002\n\002\b\007\n\002\020\016\n\002\020\022\n\000\n\002\030\002\n\000\n\002\020\036\n\002\b\002\032/\020\000\032\b\022\004\022\002H\0020\001\"\004\b\000\020\0022\f\020\003\032\b\022\004\022\002H\0020\0012\006\020\004\032\0020\005H\000¢\006\002\020\006\032\030\020\007\032\0020\b2\006\020\t\032\0020\0052\006\020\004\032\0020\005H\001\032#\020\n\032\0020\005\"\004\b\000\020\002*\f\022\006\b\001\022\002H\002\030\0010\001H\001¢\006\004\b\013\020\f\032,\020\r\032\n\022\006\b\001\022\002H\0020\001\"\006\b\000\020\002\030\001*\f\022\006\b\001\022\002H\002\030\0010\001H\b¢\006\002\020\016\032\025\020\017\032\0020\020*\0020\0212\006\020\022\032\0020\023H\b\032&\020\024\032\b\022\004\022\002H\0020\001\"\006\b\000\020\002\030\001*\b\022\004\022\002H\0020\025H\b¢\006\002\020\026¨\006\027"}, d2={"arrayOfNulls", "", "T", "reference", "size", "", "([Ljava/lang/Object;I)[Ljava/lang/Object;", "copyOfRangeToIndexCheck", "", "toIndex", "contentDeepHashCodeImpl", "contentDeepHashCode", "([Ljava/lang/Object;)I", "orEmpty", "([Ljava/lang/Object;)[Ljava/lang/Object;", "toString", "", "", "charset", "Ljava/nio/charset/Charset;", "toTypedArray", "", "(Ljava/util/Collection;)[Ljava/lang/Object;", "kotlin-stdlib"}, k=5, mv={1, 7, 1}, xi=49, xs="kotlin/collections/ArraysKt")
class ArraysKt__ArraysJVMKt
{
  public static final <T> T[] arrayOfNulls(T[] paramArrayOfT, int paramInt)
  {
    Intrinsics.checkNotNullParameter(paramArrayOfT, "reference");
    paramArrayOfT = Array.newInstance(paramArrayOfT.getClass().getComponentType(), paramInt);
    Intrinsics.checkNotNull(paramArrayOfT, "null cannot be cast to non-null type kotlin.Array<T of kotlin.collections.ArraysKt__ArraysJVMKt.arrayOfNulls>");
    return (Object[])paramArrayOfT;
  }
  
  public static final <T> int contentDeepHashCode(T[] paramArrayOfT)
  {
    return Arrays.deepHashCode(paramArrayOfT);
  }
  
  public static final void copyOfRangeToIndexCheck(int paramInt1, int paramInt2)
  {
    if (paramInt1 <= paramInt2) {
      return;
    }
    throw new IndexOutOfBoundsException("toIndex (" + paramInt1 + ") is greater than size (" + paramInt2 + ").");
  }
  
  private static final String toString(byte[] paramArrayOfByte, Charset paramCharset)
  {
    Intrinsics.checkNotNullParameter(paramArrayOfByte, "<this>");
    Intrinsics.checkNotNullParameter(paramCharset, "charset");
    paramArrayOfByte = new String(paramArrayOfByte, paramCharset);
    Log5ECF72.a(paramArrayOfByte);
    LogE84000.a(paramArrayOfByte);
    Log229316.a(paramArrayOfByte);
    return paramArrayOfByte;
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlin/collections/ArraysKt__ArraysJVMKt.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */