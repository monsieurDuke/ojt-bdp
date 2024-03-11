package kotlin.collections;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.UByteArray;
import kotlin.UIntArray;
import kotlin.ULongArray;
import kotlin.UShortArray;
import kotlin.collections.unsigned.UArraysKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import mt.Log229316;
import mt.Log5ECF72;
import mt.LogE84000;

@Metadata(d1={"\000H\n\000\n\002\020\013\n\000\n\002\020\021\n\002\b\004\n\002\020\016\n\002\b\003\n\002\020\002\n\000\n\002\030\002\n\002\030\002\n\000\n\002\020!\n\002\b\003\n\002\020 \n\002\b\005\n\002\030\002\n\002\b\004\n\002\030\002\n\002\b\002\0325\020\000\032\0020\001\"\004\b\000\020\002*\f\022\006\b\001\022\002H\002\030\0010\0032\020\020\004\032\f\022\006\b\001\022\002H\002\030\0010\003H\001¢\006\004\b\005\020\006\032#\020\007\032\0020\b\"\004\b\000\020\002*\f\022\006\b\001\022\002H\002\030\0010\003H\001¢\006\004\b\t\020\n\032?\020\013\032\0020\f\"\004\b\000\020\002*\n\022\006\b\001\022\002H\0020\0032\n\020\r\032\0060\016j\002`\0172\020\020\020\032\f\022\b\022\006\022\002\b\0030\0030\021H\002¢\006\004\b\022\020\023\032+\020\024\032\b\022\004\022\002H\0020\025\"\004\b\000\020\002*\022\022\016\b\001\022\n\022\006\b\001\022\002H\0020\0030\003¢\006\002\020\026\032;\020\027\032\002H\030\"\020\b\000\020\031*\006\022\002\b\0030\003*\002H\030\"\004\b\001\020\030*\002H\0312\f\020\032\032\b\022\004\022\002H\0300\033H\bø\001\000¢\006\002\020\034\032)\020\035\032\0020\001*\b\022\002\b\003\030\0010\003H\b\002\016\n\f\b\000\022\002\030\001\032\004\b\003\020\000¢\006\002\020\036\032G\020\037\032\032\022\n\022\b\022\004\022\002H\0020\025\022\n\022\b\022\004\022\002H\0300\0250 \"\004\b\000\020\002\"\004\b\001\020\030*\026\022\022\b\001\022\016\022\004\022\002H\002\022\004\022\002H\0300 0\003¢\006\002\020!\002\007\n\005\b20\001¨\006\""}, d2={"contentDeepEqualsImpl", "", "T", "", "other", "contentDeepEquals", "([Ljava/lang/Object;[Ljava/lang/Object;)Z", "contentDeepToStringImpl", "", "contentDeepToString", "([Ljava/lang/Object;)Ljava/lang/String;", "contentDeepToStringInternal", "", "result", "Ljava/lang/StringBuilder;", "Lkotlin/text/StringBuilder;", "processed", "", "contentDeepToStringInternal$ArraysKt__ArraysKt", "([Ljava/lang/Object;Ljava/lang/StringBuilder;Ljava/util/List;)V", "flatten", "", "([[Ljava/lang/Object;)Ljava/util/List;", "ifEmpty", "R", "C", "defaultValue", "Lkotlin/Function0;", "([Ljava/lang/Object;Lkotlin/jvm/functions/Function0;)Ljava/lang/Object;", "isNullOrEmpty", "([Ljava/lang/Object;)Z", "unzip", "Lkotlin/Pair;", "([Lkotlin/Pair;)Lkotlin/Pair;", "kotlin-stdlib"}, k=5, mv={1, 7, 1}, xi=49, xs="kotlin/collections/ArraysKt")
class ArraysKt__ArraysKt
  extends ArraysKt__ArraysJVMKt
{
  public static final <T> boolean contentDeepEquals(T[] paramArrayOfT1, T[] paramArrayOfT2)
  {
    if (paramArrayOfT1 == paramArrayOfT2) {
      return true;
    }
    if ((paramArrayOfT1 != null) && (paramArrayOfT2 != null) && (paramArrayOfT1.length == paramArrayOfT2.length))
    {
      int i = 0;
      int j = paramArrayOfT1.length;
      while (i < j)
      {
        T ? = paramArrayOfT1[i];
        T ? = paramArrayOfT2[i];
        if (? != ?)
        {
          if ((? == null) || (? == null)) {
            break label552;
          }
          if (((? instanceof Object[])) && ((? instanceof Object[])))
          {
            if (!ArraysKt.contentDeepEquals((Object[])?, (Object[])?)) {
              return false;
            }
          }
          else if (((? instanceof byte[])) && ((? instanceof byte[])))
          {
            if (!Arrays.equals((byte[])?, (byte[])?)) {
              return false;
            }
          }
          else if (((? instanceof short[])) && ((? instanceof short[])))
          {
            if (!Arrays.equals((short[])?, (short[])?)) {
              return false;
            }
          }
          else if (((? instanceof int[])) && ((? instanceof int[])))
          {
            if (!Arrays.equals((int[])?, (int[])?)) {
              return false;
            }
          }
          else if (((? instanceof long[])) && ((? instanceof long[])))
          {
            if (!Arrays.equals((long[])?, (long[])?)) {
              return false;
            }
          }
          else if (((? instanceof float[])) && ((? instanceof float[])))
          {
            if (!Arrays.equals((float[])?, (float[])?)) {
              return false;
            }
          }
          else if (((? instanceof double[])) && ((? instanceof double[])))
          {
            if (!Arrays.equals((double[])?, (double[])?)) {
              return false;
            }
          }
          else if (((? instanceof char[])) && ((? instanceof char[])))
          {
            if (!Arrays.equals((char[])?, (char[])?)) {
              return false;
            }
          }
          else if (((? instanceof boolean[])) && ((? instanceof boolean[])))
          {
            if (!Arrays.equals((boolean[])?, (boolean[])?)) {
              return false;
            }
          }
          else if (((? instanceof UByteArray)) && ((? instanceof UByteArray)))
          {
            if (!UArraysKt.contentEquals-kV0jMPg(((UByteArray)?).unbox-impl(), ((UByteArray)?).unbox-impl())) {
              return false;
            }
          }
          else if (((? instanceof UShortArray)) && ((? instanceof UShortArray)))
          {
            if (!UArraysKt.contentEquals-FGO6Aew(((UShortArray)?).unbox-impl(), ((UShortArray)?).unbox-impl())) {
              return false;
            }
          }
          else if (((? instanceof UIntArray)) && ((? instanceof UIntArray)))
          {
            if (!UArraysKt.contentEquals-KJPZfPQ(((UIntArray)?).unbox-impl(), ((UIntArray)?).unbox-impl())) {
              return false;
            }
          }
          else if (((? instanceof ULongArray)) && ((? instanceof ULongArray)))
          {
            if (!UArraysKt.contentEquals-lec5QzE(((ULongArray)?).unbox-impl(), ((ULongArray)?).unbox-impl())) {
              return false;
            }
          }
          else if (!Intrinsics.areEqual(?, ?)) {
            return false;
          }
        }
        i++;
        continue;
        label552:
        return false;
      }
      return true;
    }
    return false;
  }
  
  public static final <T> String contentDeepToString(T[] paramArrayOfT)
  {
    if (paramArrayOfT == null) {
      return "null";
    }
    StringBuilder localStringBuilder = new StringBuilder(RangesKt.coerceAtMost(paramArrayOfT.length, 429496729) * 5 + 2);
    contentDeepToStringInternal$ArraysKt__ArraysKt(paramArrayOfT, localStringBuilder, (List)new ArrayList());
    paramArrayOfT = localStringBuilder.toString();
    Intrinsics.checkNotNullExpressionValue(paramArrayOfT, "StringBuilder(capacity).…builderAction).toString()");
    return paramArrayOfT;
  }
  
  private static final <T> void contentDeepToStringInternal$ArraysKt__ArraysKt(T[] paramArrayOfT, StringBuilder paramStringBuilder, List<Object[]> paramList)
  {
    if (paramList.contains(paramArrayOfT))
    {
      paramStringBuilder.append("[...]");
      return;
    }
    paramList.add(paramArrayOfT);
    paramStringBuilder.append('[');
    int i = 0;
    int j = paramArrayOfT.length;
    while (i < j)
    {
      if (i != 0) {
        paramStringBuilder.append(", ");
      }
      T ? = paramArrayOfT[i];
      if (? == null)
      {
        paramStringBuilder.append("null");
      }
      else if ((? instanceof Object[]))
      {
        contentDeepToStringInternal$ArraysKt__ArraysKt((Object[])?, paramStringBuilder, paramList);
      }
      else
      {
        Object localObject1;
        if ((? instanceof byte[]))
        {
          localObject1 = Arrays.toString((byte[])?);
          Log5ECF72.a(localObject1);
          LogE84000.a(localObject1);
          Log229316.a(localObject1);
          Intrinsics.checkNotNullExpressionValue(localObject1, "toString(this)");
          paramStringBuilder.append((String)localObject1);
        }
        else if ((? instanceof short[]))
        {
          localObject1 = Arrays.toString((short[])?);
          Log5ECF72.a(localObject1);
          LogE84000.a(localObject1);
          Log229316.a(localObject1);
          Intrinsics.checkNotNullExpressionValue(localObject1, "toString(this)");
          paramStringBuilder.append((String)localObject1);
        }
        else if ((? instanceof int[]))
        {
          localObject1 = Arrays.toString((int[])?);
          Log5ECF72.a(localObject1);
          LogE84000.a(localObject1);
          Log229316.a(localObject1);
          Intrinsics.checkNotNullExpressionValue(localObject1, "toString(this)");
          paramStringBuilder.append((String)localObject1);
        }
        else if ((? instanceof long[]))
        {
          localObject1 = Arrays.toString((long[])?);
          Log5ECF72.a(localObject1);
          LogE84000.a(localObject1);
          Log229316.a(localObject1);
          Intrinsics.checkNotNullExpressionValue(localObject1, "toString(this)");
          paramStringBuilder.append((String)localObject1);
        }
        else if ((? instanceof float[]))
        {
          localObject1 = Arrays.toString((float[])?);
          Log5ECF72.a(localObject1);
          LogE84000.a(localObject1);
          Log229316.a(localObject1);
          Intrinsics.checkNotNullExpressionValue(localObject1, "toString(this)");
          paramStringBuilder.append((String)localObject1);
        }
        else if ((? instanceof double[]))
        {
          localObject1 = Arrays.toString((double[])?);
          Log5ECF72.a(localObject1);
          LogE84000.a(localObject1);
          Log229316.a(localObject1);
          Intrinsics.checkNotNullExpressionValue(localObject1, "toString(this)");
          paramStringBuilder.append((String)localObject1);
        }
        else if ((? instanceof char[]))
        {
          localObject1 = Arrays.toString((char[])?);
          Log5ECF72.a(localObject1);
          LogE84000.a(localObject1);
          Log229316.a(localObject1);
          Intrinsics.checkNotNullExpressionValue(localObject1, "toString(this)");
          paramStringBuilder.append((String)localObject1);
        }
        else if ((? instanceof boolean[]))
        {
          localObject1 = Arrays.toString((boolean[])?);
          Log5ECF72.a(localObject1);
          LogE84000.a(localObject1);
          Log229316.a(localObject1);
          Intrinsics.checkNotNullExpressionValue(localObject1, "toString(this)");
          paramStringBuilder.append((String)localObject1);
        }
        else
        {
          boolean bool = ? instanceof UByteArray;
          Object localObject3 = null;
          ULongArray localULongArray = null;
          Object localObject2 = null;
          localObject1 = null;
          if (bool)
          {
            localObject2 = (UByteArray)?;
            if (localObject2 != null) {
              localObject1 = ((UByteArray)localObject2).unbox-impl();
            }
            localObject1 = UArraysKt.contentToString-2csIQuQ((byte[])localObject1);
            Log5ECF72.a(localObject1);
            LogE84000.a(localObject1);
            Log229316.a(localObject1);
            paramStringBuilder.append((String)localObject1);
          }
          else if ((? instanceof UShortArray))
          {
            localObject2 = (UShortArray)?;
            localObject1 = localObject3;
            if (localObject2 != null) {
              localObject1 = ((UShortArray)localObject2).unbox-impl();
            }
            localObject1 = UArraysKt.contentToString-d-6D3K8((short[])localObject1);
            Log5ECF72.a(localObject1);
            LogE84000.a(localObject1);
            Log229316.a(localObject1);
            paramStringBuilder.append((String)localObject1);
          }
          else if ((? instanceof UIntArray))
          {
            localObject2 = (UIntArray)?;
            localObject1 = localULongArray;
            if (localObject2 != null) {
              localObject1 = ((UIntArray)localObject2).unbox-impl();
            }
            localObject1 = UArraysKt.contentToString-XUkPCBk((int[])localObject1);
            Log5ECF72.a(localObject1);
            LogE84000.a(localObject1);
            Log229316.a(localObject1);
            paramStringBuilder.append((String)localObject1);
          }
          else if ((? instanceof ULongArray))
          {
            localULongArray = (ULongArray)?;
            localObject1 = localObject2;
            if (localULongArray != null) {
              localObject1 = localULongArray.unbox-impl();
            }
            localObject1 = UArraysKt.contentToString-uLth9ew((long[])localObject1);
            Log5ECF72.a(localObject1);
            LogE84000.a(localObject1);
            Log229316.a(localObject1);
            paramStringBuilder.append((String)localObject1);
          }
          else
          {
            paramStringBuilder.append(?.toString());
          }
        }
      }
      i++;
    }
    paramStringBuilder.append(']');
    paramList.remove(CollectionsKt.getLastIndex(paramList));
  }
  
  public static final <T> List<T> flatten(T[][] paramArrayOfT)
  {
    Intrinsics.checkNotNullParameter(paramArrayOfT, "<this>");
    Object localObject1 = (Object[])paramArrayOfT;
    int m = localObject1.length;
    int k = 0;
    int j = 0;
    int i = 0;
    Object localObject2;
    while (j < m)
    {
      localObject2 = (Object[])localObject1[j];
      i += localObject2.length;
      j++;
    }
    localObject1 = new ArrayList(i);
    j = ((Object[])paramArrayOfT).length;
    for (i = k; i < j; i++)
    {
      localObject2 = paramArrayOfT[i];
      CollectionsKt.addAll((Collection)localObject1, (Object[])localObject2);
    }
    return (List)localObject1;
  }
  
  private static final <C extends Object[],  extends R, R> R ifEmpty(C paramC, Function0<? extends R> paramFunction0)
  {
    Intrinsics.checkNotNullParameter(paramFunction0, "defaultValue");
    int i;
    if (paramC.length == 0) {
      i = 1;
    } else {
      i = 0;
    }
    if (i != 0) {
      paramC = paramFunction0.invoke();
    }
    return paramC;
  }
  
  private static final boolean isNullOrEmpty(Object[] paramArrayOfObject)
  {
    boolean bool = false;
    if (paramArrayOfObject != null)
    {
      int i;
      if (paramArrayOfObject.length == 0) {
        i = 1;
      } else {
        i = 0;
      }
      if (i == 0) {}
    }
    else
    {
      bool = true;
    }
    return bool;
  }
  
  public static final <T, R> Pair<List<T>, List<R>> unzip(Pair<? extends T, ? extends R>[] paramArrayOfPair)
  {
    Intrinsics.checkNotNullParameter(paramArrayOfPair, "<this>");
    ArrayList localArrayList2 = new ArrayList(paramArrayOfPair.length);
    ArrayList localArrayList1 = new ArrayList(paramArrayOfPair.length);
    int j = paramArrayOfPair.length;
    for (int i = 0; i < j; i++)
    {
      Pair<? extends T, ? extends R> localPair = paramArrayOfPair[i];
      localArrayList2.add(localPair.getFirst());
      localArrayList1.add(localPair.getSecond());
    }
    return TuplesKt.to(localArrayList2, localArrayList1);
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlin/collections/ArraysKt__ArraysKt.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */