package okio;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.RandomAccess;
import kotlin.Metadata;
import kotlin.collections.AbstractList;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.Intrinsics;

@Metadata(bv={1, 0, 3}, d1={"\000,\n\002\030\002\n\002\030\002\n\002\030\002\n\002\030\002\n\002\030\002\n\000\n\002\020\021\n\000\n\002\020\025\n\002\b\005\n\002\020\b\n\002\b\b\030\000 \0252\b\022\004\022\0020\0020\0012\0060\003j\002`\004:\001\025B\037\b\002\022\016\020\005\032\n\022\006\b\001\022\0020\0020\006\022\006\020\007\032\0020\b¢\006\002\020\tJ\021\020\023\032\0020\0022\006\020\024\032\0020\016H\002R\036\020\005\032\n\022\006\b\001\022\0020\0020\006X\004¢\006\n\n\002\020\f\032\004\b\n\020\013R\024\020\r\032\0020\0168VX\004¢\006\006\032\004\b\017\020\020R\024\020\007\032\0020\bX\004¢\006\b\n\000\032\004\b\021\020\022¨\006\026"}, d2={"Lokio/Options;", "Lkotlin/collections/AbstractList;", "Lokio/ByteString;", "Ljava/util/RandomAccess;", "Lkotlin/collections/RandomAccess;", "byteStrings", "", "trie", "", "([Lokio/ByteString;[I)V", "getByteStrings$okio", "()[Lokio/ByteString;", "[Lokio/ByteString;", "size", "", "getSize", "()I", "getTrie$okio", "()[I", "get", "index", "Companion", "okio"}, k=1, mv={1, 4, 0})
public final class Options
  extends AbstractList<ByteString>
  implements RandomAccess
{
  public static final Companion Companion = new Companion(null);
  private final ByteString[] byteStrings;
  private final int[] trie;
  
  private Options(ByteString[] paramArrayOfByteString, int[] paramArrayOfInt)
  {
    this.byteStrings = paramArrayOfByteString;
    this.trie = paramArrayOfInt;
  }
  
  @JvmStatic
  public static final Options of(ByteString... paramVarArgs)
  {
    return Companion.of(paramVarArgs);
  }
  
  public ByteString get(int paramInt)
  {
    return this.byteStrings[paramInt];
  }
  
  public final ByteString[] getByteStrings$okio()
  {
    return this.byteStrings;
  }
  
  public int getSize()
  {
    return this.byteStrings.length;
  }
  
  public final int[] getTrie$okio()
  {
    return this.trie;
  }
  
  @Metadata(bv={1, 0, 3}, d1={"\000>\n\002\030\002\n\002\020\000\n\002\b\002\n\002\020\t\n\002\030\002\n\002\b\003\n\002\020\002\n\002\b\003\n\002\020\b\n\000\n\002\020 \n\002\030\002\n\002\b\004\n\002\030\002\n\002\020\021\n\002\b\002\b\003\030\0002\0020\001B\007\b\002¢\006\002\020\002JT\020\b\032\0020\t2\b\b\002\020\n\032\0020\0042\006\020\013\032\0020\0052\b\b\002\020\f\032\0020\r2\f\020\016\032\b\022\004\022\0020\0200\0172\b\b\002\020\021\032\0020\r2\b\b\002\020\022\032\0020\r2\f\020\023\032\b\022\004\022\0020\r0\017H\002J!\020\024\032\0020\0252\022\020\016\032\n\022\006\b\001\022\0020\0200\026\"\0020\020H\007¢\006\002\020\027R\030\020\003\032\0020\004*\0020\0058BX\004¢\006\006\032\004\b\006\020\007¨\006\030"}, d2={"Lokio/Options$Companion;", "", "()V", "intCount", "", "Lokio/Buffer;", "getIntCount", "(Lokio/Buffer;)J", "buildTrieRecursive", "", "nodeOffset", "node", "byteStringOffset", "", "byteStrings", "", "Lokio/ByteString;", "fromIndex", "toIndex", "indexes", "of", "Lokio/Options;", "", "([Lokio/ByteString;)Lokio/Options;", "okio"}, k=1, mv={1, 4, 0})
  public static final class Companion
  {
    private final void buildTrieRecursive(long paramLong, Buffer paramBuffer, int paramInt1, List<? extends ByteString> paramList, int paramInt2, int paramInt3, List<Integer> paramList1)
    {
      int m = 0;
      int i;
      if (paramInt2 < paramInt3) {
        i = 1;
      } else {
        i = 0;
      }
      if (i != 0)
      {
        i = paramInt2;
        int j;
        while (i < paramInt3)
        {
          if (((ByteString)paramList.get(i)).size() >= paramInt1) {
            j = 1;
          } else {
            j = 0;
          }
          if (j != 0) {
            i++;
          } else {
            throw ((Throwable)new IllegalArgumentException("Failed requirement.".toString()));
          }
        }
        Object localObject1 = (ByteString)paramList.get(paramInt2);
        Object localObject2 = (ByteString)paramList.get(paramInt3 - 1);
        if (paramInt1 == ((ByteString)localObject1).size())
        {
          i = ((Number)paramList1.get(paramInt2)).intValue();
          paramInt2++;
          localObject1 = (ByteString)paramList.get(paramInt2);
        }
        else
        {
          i = -1;
        }
        int k;
        int n;
        if (((ByteString)localObject1).getByte(paramInt1) != ((ByteString)localObject2).getByte(paramInt1))
        {
          m = paramInt2 + 1;
          for (j = 1; m < paramInt3; j = k)
          {
            k = j;
            if (((ByteString)paramList.get(m - 1)).getByte(paramInt1) != ((ByteString)paramList.get(m)).getByte(paramInt1)) {
              k = j + 1;
            }
            m++;
          }
          paramLong = paramLong + ((Companion)this).getIntCount(paramBuffer) + 2 + j * 2;
          paramBuffer.writeInt(j);
          paramBuffer.writeInt(i);
          for (k = paramInt2; k < paramInt3; k++)
          {
            m = ((ByteString)paramList.get(k)).getByte(paramInt1);
            if ((k == paramInt2) || (m != ((ByteString)paramList.get(k - 1)).getByte(paramInt1))) {
              paramBuffer.writeInt(0xFF & m);
            }
          }
          localObject2 = new Buffer();
          n = paramInt2;
          m = paramInt2;
          k = i;
          while (n < paramInt3)
          {
            int i1 = ((ByteString)paramList.get(n)).getByte(paramInt1);
            paramInt2 = paramInt3;
            for (i = n + 1; i < paramInt3; i++) {
              if (i1 != ((ByteString)paramList.get(i)).getByte(paramInt1)) {
                break label474;
              }
            }
            i = paramInt2;
            label474:
            if ((n + 1 == i) && (paramInt1 + 1 == ((ByteString)paramList.get(n)).size()))
            {
              paramBuffer.writeInt(((Number)paramList1.get(n)).intValue());
            }
            else
            {
              paramBuffer.writeInt((int)(paramLong + ((Companion)this).getIntCount((Buffer)localObject2)) * -1);
              Companion localCompanion = (Companion)this;
              localCompanion.buildTrieRecursive(paramLong, (Buffer)localObject2, paramInt1 + 1, paramList, n, i, paramList1);
            }
            n = i;
          }
          paramBuffer.writeAll((Source)localObject2);
        }
        else
        {
          n = Math.min(((ByteString)localObject1).size(), ((ByteString)localObject2).size());
          j = 0;
          k = paramInt1;
          while (k < n) {
            if (((ByteString)localObject1).getByte(k) == ((ByteString)localObject2).getByte(k))
            {
              j++;
              k++;
            }
            else {}
          }
          paramLong = paramLong + ((Companion)this).getIntCount(paramBuffer) + 2 + j + 1L;
          paramBuffer.writeInt(-j);
          paramBuffer.writeInt(i);
          for (i = paramInt1; i < paramInt1 + j; i++) {
            paramBuffer.writeInt(((ByteString)localObject1).getByte(i) & 0xFF);
          }
          if (paramInt2 + 1 == paramInt3)
          {
            paramInt3 = m;
            if (paramInt1 + j == ((ByteString)paramList.get(paramInt2)).size()) {
              paramInt3 = 1;
            }
            if (paramInt3 != 0) {
              paramBuffer.writeInt(((Number)paramList1.get(paramInt2)).intValue());
            } else {
              throw ((Throwable)new IllegalStateException("Check failed.".toString()));
            }
          }
          else
          {
            localObject2 = new Buffer();
            paramBuffer.writeInt((int)(paramLong + ((Companion)this).getIntCount((Buffer)localObject2)) * -1);
            localObject1 = (Companion)this;
            ((Companion)localObject1).buildTrieRecursive(paramLong, (Buffer)localObject2, paramInt1 + j, paramList, paramInt2, paramInt3, paramList1);
            paramBuffer.writeAll((Source)localObject2);
          }
        }
        return;
      }
      throw ((Throwable)new IllegalArgumentException("Failed requirement.".toString()));
    }
    
    private final long getIntCount(Buffer paramBuffer)
    {
      return paramBuffer.size() / 4;
    }
    
    @JvmStatic
    public final Options of(ByteString... paramVarArgs)
    {
      Intrinsics.checkNotNullParameter(paramVarArgs, "byteStrings");
      if (paramVarArgs.length == 0) {
        i = 1;
      } else {
        i = 0;
      }
      if (i != 0) {
        return new Options(new ByteString[0], new int[] { 0, -1 }, null);
      }
      Object localObject1 = ArraysKt.toMutableList(paramVarArgs);
      CollectionsKt.sort((List)localObject1);
      Object localObject2 = (Collection)new ArrayList(paramVarArgs.length);
      int j = paramVarArgs.length;
      Object localObject3;
      for (int i = 0; i < j; i++)
      {
        localObject3 = paramVarArgs[i];
        ((Collection)localObject2).add(Integer.valueOf(-1));
      }
      localObject2 = ((Collection)localObject2).toArray(new Integer[0]);
      if (localObject2 != null)
      {
        localObject2 = (Integer[])localObject2;
        localObject2 = CollectionsKt.mutableListOf((Integer[])Arrays.copyOf((Object[])localObject2, localObject2.length));
        i = 0;
        int k = paramVarArgs.length;
        j = 0;
        while (j < k)
        {
          ((List)localObject2).set(CollectionsKt.binarySearch$default((List)localObject1, (Comparable)paramVarArgs[j], 0, 0, 6, null), Integer.valueOf(i));
          j++;
          i++;
        }
        if (((ByteString)((List)localObject1).get(0)).size() > 0) {
          i = 1;
        } else {
          i = 0;
        }
        if (i != 0)
        {
          for (i = 0; i < ((List)localObject1).size(); i++)
          {
            localObject3 = (ByteString)((List)localObject1).get(i);
            j = i + 1;
            while (j < ((List)localObject1).size())
            {
              ByteString localByteString = (ByteString)((List)localObject1).get(j);
              if (localByteString.startsWith((ByteString)localObject3))
              {
                if (localByteString.size() != ((ByteString)localObject3).size()) {
                  k = 1;
                } else {
                  k = 0;
                }
                if (k != 0)
                {
                  if (((Number)((List)localObject2).get(j)).intValue() > ((Number)((List)localObject2).get(i)).intValue())
                  {
                    ((List)localObject1).remove(j);
                    ((List)localObject2).remove(j);
                  }
                  else
                  {
                    j++;
                  }
                }
                else {
                  throw ((Throwable)new IllegalArgumentException(("duplicate option: " + localByteString).toString()));
                }
              }
            }
          }
          localObject3 = new Buffer();
          buildTrieRecursive$default((Companion)this, 0L, (Buffer)localObject3, 0, (List)localObject1, 0, 0, (List)localObject2, 53, null);
          localObject1 = new int[(int)((Companion)this).getIntCount((Buffer)localObject3)];
          for (i = 0; !((Buffer)localObject3).exhausted(); i++) {
            localObject1[i] = ((Buffer)localObject3).readInt();
          }
          paramVarArgs = Arrays.copyOf(paramVarArgs, paramVarArgs.length);
          Intrinsics.checkNotNullExpressionValue(paramVarArgs, "java.util.Arrays.copyOf(this, size)");
          return new Options((ByteString[])paramVarArgs, (int[])localObject1, null);
        }
        throw ((Throwable)new IllegalArgumentException("the empty byte string is not a supported option".toString()));
      }
      throw new NullPointerException("null cannot be cast to non-null type kotlin.Array<T>");
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/okio/Options.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */