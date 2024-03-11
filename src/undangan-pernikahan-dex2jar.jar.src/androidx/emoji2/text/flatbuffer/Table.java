package androidx.emoji2.text.flatbuffer;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;
import java.util.Comparator;
import mt.Log229316;
import mt.Log5ECF72;
import mt.LogE84000;

public class Table
{
  protected ByteBuffer bb;
  protected int bb_pos;
  Utf8 utf8 = Utf8.getDefault();
  private int vtable_size;
  private int vtable_start;
  
  protected static boolean __has_identifier(ByteBuffer paramByteBuffer, String paramString)
  {
    if (paramString.length() == 4)
    {
      for (int i = 0; i < 4; i++) {
        if (paramString.charAt(i) != (char)paramByteBuffer.get(paramByteBuffer.position() + 4 + i)) {
          return false;
        }
      }
      return true;
    }
    throw new AssertionError("FlatBuffers: file identifier must be length 4");
  }
  
  protected static int __indirect(int paramInt, ByteBuffer paramByteBuffer)
  {
    return paramByteBuffer.getInt(paramInt) + paramInt;
  }
  
  protected static int __offset(int paramInt1, int paramInt2, ByteBuffer paramByteBuffer)
  {
    paramInt2 = paramByteBuffer.capacity() - paramInt2;
    return paramByteBuffer.getShort(paramInt2 + paramInt1 - paramByteBuffer.getInt(paramInt2)) + paramInt2;
  }
  
  protected static String __string(int paramInt, ByteBuffer paramByteBuffer, Utf8 paramUtf8)
  {
    paramInt += paramByteBuffer.getInt(paramInt);
    return paramUtf8.decodeUtf8(paramByteBuffer, paramInt + 4, paramByteBuffer.getInt(paramInt));
  }
  
  protected static Table __union(Table paramTable, int paramInt, ByteBuffer paramByteBuffer)
  {
    paramTable.__reset(__indirect(paramInt, paramByteBuffer), paramByteBuffer);
    return paramTable;
  }
  
  protected static int compareStrings(int paramInt1, int paramInt2, ByteBuffer paramByteBuffer)
  {
    int j = paramInt1 + paramByteBuffer.getInt(paramInt1);
    paramInt1 = paramInt2 + paramByteBuffer.getInt(paramInt2);
    paramInt2 = paramByteBuffer.getInt(j);
    int i = paramByteBuffer.getInt(paramInt1);
    j += 4;
    int k = paramInt1 + 4;
    int m = Math.min(paramInt2, i);
    for (paramInt1 = 0; paramInt1 < m; paramInt1++) {
      if (paramByteBuffer.get(paramInt1 + j) != paramByteBuffer.get(paramInt1 + k)) {
        return paramByteBuffer.get(paramInt1 + j) - paramByteBuffer.get(paramInt1 + k);
      }
    }
    return paramInt2 - i;
  }
  
  protected static int compareStrings(int paramInt, byte[] paramArrayOfByte, ByteBuffer paramByteBuffer)
  {
    paramInt += paramByteBuffer.getInt(paramInt);
    int j = paramByteBuffer.getInt(paramInt);
    int i = paramArrayOfByte.length;
    int k = paramInt + 4;
    int m = Math.min(j, i);
    for (paramInt = 0; paramInt < m; paramInt++) {
      if (paramByteBuffer.get(paramInt + k) != paramArrayOfByte[paramInt]) {
        return paramByteBuffer.get(paramInt + k) - paramArrayOfByte[paramInt];
      }
    }
    return j - i;
  }
  
  protected int __indirect(int paramInt)
  {
    return this.bb.getInt(paramInt) + paramInt;
  }
  
  protected int __offset(int paramInt)
  {
    if (paramInt < this.vtable_size) {
      paramInt = this.bb.getShort(this.vtable_start + paramInt);
    } else {
      paramInt = 0;
    }
    return paramInt;
  }
  
  public void __reset()
  {
    __reset(0, null);
  }
  
  protected void __reset(int paramInt, ByteBuffer paramByteBuffer)
  {
    this.bb = paramByteBuffer;
    if (paramByteBuffer != null)
    {
      this.bb_pos = paramInt;
      paramInt -= paramByteBuffer.getInt(paramInt);
      this.vtable_start = paramInt;
      this.vtable_size = this.bb.getShort(paramInt);
    }
    else
    {
      this.bb_pos = 0;
      this.vtable_start = 0;
      this.vtable_size = 0;
    }
  }
  
  protected String __string(int paramInt)
  {
    String str = __string(paramInt, this.bb, this.utf8);
    Log5ECF72.a(str);
    LogE84000.a(str);
    Log229316.a(str);
    return str;
  }
  
  protected Table __union(Table paramTable, int paramInt)
  {
    return __union(paramTable, paramInt, this.bb);
  }
  
  protected int __vector(int paramInt)
  {
    paramInt += this.bb_pos;
    return this.bb.getInt(paramInt) + paramInt + 4;
  }
  
  protected ByteBuffer __vector_as_bytebuffer(int paramInt1, int paramInt2)
  {
    int i = __offset(paramInt1);
    if (i == 0) {
      return null;
    }
    ByteBuffer localByteBuffer = this.bb.duplicate().order(ByteOrder.LITTLE_ENDIAN);
    paramInt1 = __vector(i);
    localByteBuffer.position(paramInt1);
    localByteBuffer.limit(__vector_len(i) * paramInt2 + paramInt1);
    return localByteBuffer;
  }
  
  protected ByteBuffer __vector_in_bytebuffer(ByteBuffer paramByteBuffer, int paramInt1, int paramInt2)
  {
    paramInt1 = __offset(paramInt1);
    if (paramInt1 == 0) {
      return null;
    }
    int i = __vector(paramInt1);
    paramByteBuffer.rewind();
    paramByteBuffer.limit(__vector_len(paramInt1) * paramInt2 + i);
    paramByteBuffer.position(i);
    return paramByteBuffer;
  }
  
  protected int __vector_len(int paramInt)
  {
    int i = paramInt + this.bb_pos;
    paramInt = this.bb.getInt(i);
    return this.bb.getInt(i + paramInt);
  }
  
  public ByteBuffer getByteBuffer()
  {
    return this.bb;
  }
  
  protected int keysCompare(Integer paramInteger1, Integer paramInteger2, ByteBuffer paramByteBuffer)
  {
    return 0;
  }
  
  protected void sortTables(int[] paramArrayOfInt, final ByteBuffer paramByteBuffer)
  {
    Integer[] arrayOfInteger = new Integer[paramArrayOfInt.length];
    for (int i = 0; i < paramArrayOfInt.length; i++) {
      arrayOfInteger[i] = Integer.valueOf(paramArrayOfInt[i]);
    }
    Arrays.sort(arrayOfInteger, new Comparator()
    {
      public int compare(Integer paramAnonymousInteger1, Integer paramAnonymousInteger2)
      {
        return Table.this.keysCompare(paramAnonymousInteger1, paramAnonymousInteger2, paramByteBuffer);
      }
    });
    for (i = 0; i < paramArrayOfInt.length; i++) {
      paramArrayOfInt[i] = arrayOfInteger[i].intValue();
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/emoji2/text/flatbuffer/Table.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */