package kotlin.collections.builders;

import java.util.Arrays;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

@Metadata(d1={"\0002\n\000\n\002\020\021\n\002\b\002\n\002\020\b\n\002\b\006\n\002\020\002\n\002\b\007\n\002\020\013\n\002\b\003\n\002\020 \n\002\b\004\n\002\020\016\n\002\b\002\032!\020\000\032\b\022\004\022\002H\0020\001\"\004\b\000\020\0022\006\020\003\032\0020\004H\000¢\006\002\020\005\032+\020\006\032\b\022\004\022\002H\0070\001\"\004\b\000\020\007*\b\022\004\022\002H\0070\0012\006\020\b\032\0020\004H\000¢\006\002\020\t\032%\020\n\032\0020\013\"\004\b\000\020\002*\b\022\004\022\002H\0020\0012\006\020\f\032\0020\004H\000¢\006\002\020\r\032-\020\016\032\0020\013\"\004\b\000\020\002*\b\022\004\022\002H\0020\0012\006\020\017\032\0020\0042\006\020\020\032\0020\004H\000¢\006\002\020\021\0329\020\022\032\0020\023\"\004\b\000\020\007*\b\022\004\022\002H\0070\0012\006\020\024\032\0020\0042\006\020\025\032\0020\0042\n\020\026\032\006\022\002\b\0030\027H\002¢\006\002\020\030\032-\020\031\032\0020\004\"\004\b\000\020\007*\b\022\004\022\002H\0070\0012\006\020\024\032\0020\0042\006\020\025\032\0020\004H\002¢\006\002\020\032\032/\020\033\032\0020\034\"\004\b\000\020\007*\n\022\006\b\001\022\002H\0070\0012\006\020\024\032\0020\0042\006\020\025\032\0020\004H\002¢\006\002\020\035¨\006\036"}, d2={"arrayOfUninitializedElements", "", "E", "size", "", "(I)[Ljava/lang/Object;", "copyOfUninitializedElements", "T", "newSize", "([Ljava/lang/Object;I)[Ljava/lang/Object;", "resetAt", "", "index", "([Ljava/lang/Object;I)V", "resetRange", "fromIndex", "toIndex", "([Ljava/lang/Object;II)V", "subarrayContentEquals", "", "offset", "length", "other", "", "([Ljava/lang/Object;IILjava/util/List;)Z", "subarrayContentHashCode", "([Ljava/lang/Object;II)I", "subarrayContentToString", "", "([Ljava/lang/Object;II)Ljava/lang/String;", "kotlin-stdlib"}, k=2, mv={1, 7, 1}, xi=48)
public final class ListBuilderKt
{
  public static final <E> E[] arrayOfUninitializedElements(int paramInt)
  {
    int i;
    if (paramInt >= 0) {
      i = 1;
    } else {
      i = 0;
    }
    if (i != 0) {
      return new Object[paramInt];
    }
    throw new IllegalArgumentException("capacity must be non-negative.".toString());
  }
  
  public static final <T> T[] copyOfUninitializedElements(T[] paramArrayOfT, int paramInt)
  {
    Intrinsics.checkNotNullParameter(paramArrayOfT, "<this>");
    paramArrayOfT = Arrays.copyOf(paramArrayOfT, paramInt);
    Intrinsics.checkNotNullExpressionValue(paramArrayOfT, "copyOf(this, newSize)");
    Intrinsics.checkNotNull(paramArrayOfT, "null cannot be cast to non-null type kotlin.Array<T of kotlin.collections.builders.ListBuilderKt.copyOfUninitializedElements>");
    return paramArrayOfT;
  }
  
  public static final <E> void resetAt(E[] paramArrayOfE, int paramInt)
  {
    Intrinsics.checkNotNullParameter(paramArrayOfE, "<this>");
    paramArrayOfE[paramInt] = null;
  }
  
  public static final <E> void resetRange(E[] paramArrayOfE, int paramInt1, int paramInt2)
  {
    Intrinsics.checkNotNullParameter(paramArrayOfE, "<this>");
    while (paramInt1 < paramInt2)
    {
      resetAt(paramArrayOfE, paramInt1);
      paramInt1++;
    }
  }
  
  private static final <T> boolean subarrayContentEquals(T[] paramArrayOfT, int paramInt1, int paramInt2, List<?> paramList)
  {
    if (paramInt2 != paramList.size()) {
      return false;
    }
    for (int i = 0; i < paramInt2; i++) {
      if (!Intrinsics.areEqual(paramArrayOfT[(paramInt1 + i)], paramList.get(i))) {
        return false;
      }
    }
    return true;
  }
  
  private static final <T> int subarrayContentHashCode(T[] paramArrayOfT, int paramInt1, int paramInt2)
  {
    int j = 1;
    for (int i = 0; i < paramInt2; i++)
    {
      T ? = paramArrayOfT[(paramInt1 + i)];
      int k;
      if (? != null) {
        k = ?.hashCode();
      } else {
        k = 0;
      }
      j = j * 31 + k;
    }
    return j;
  }
  
  private static final <T> String subarrayContentToString(T[] paramArrayOfT, int paramInt1, int paramInt2)
  {
    StringBuilder localStringBuilder = new StringBuilder(paramInt2 * 3 + 2);
    localStringBuilder.append("[");
    for (int i = 0; i < paramInt2; i++)
    {
      if (i > 0) {
        localStringBuilder.append(", ");
      }
      localStringBuilder.append(paramArrayOfT[(paramInt1 + i)]);
    }
    localStringBuilder.append("]");
    paramArrayOfT = localStringBuilder.toString();
    Intrinsics.checkNotNullExpressionValue(paramArrayOfT, "sb.toString()");
    return paramArrayOfT;
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlin/collections/builders/ListBuilderKt.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */