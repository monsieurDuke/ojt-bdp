package androidx.emoji2.text;

import androidx.emoji2.text.flatbuffer.MetadataList;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

class MetadataListReader
{
  private static final int EMJI_TAG = 1164798569;
  private static final int EMJI_TAG_DEPRECATED = 1701669481;
  private static final int META_TABLE_NAME = 1835365473;
  
  private static OffsetInfo findOffsetInfo(OpenTypeReader paramOpenTypeReader)
    throws IOException
  {
    paramOpenTypeReader.skip(4);
    int j = paramOpenTypeReader.readUnsignedShort();
    if (j <= 100)
    {
      paramOpenTypeReader.skip(6);
      long l2 = -1L;
      long l1;
      for (int i = 0;; i++)
      {
        l1 = l2;
        if (i >= j) {
          break;
        }
        int k = paramOpenTypeReader.readTag();
        paramOpenTypeReader.skip(4);
        l1 = paramOpenTypeReader.readUnsignedInt();
        paramOpenTypeReader.skip(4);
        if (1835365473 == k) {
          break;
        }
      }
      if (l1 != -1L)
      {
        paramOpenTypeReader.skip((int)(l1 - paramOpenTypeReader.getPosition()));
        paramOpenTypeReader.skip(12);
        long l3 = paramOpenTypeReader.readUnsignedInt();
        i = 0;
        while (i < l3)
        {
          j = paramOpenTypeReader.readTag();
          l2 = paramOpenTypeReader.readUnsignedInt();
          long l4 = paramOpenTypeReader.readUnsignedInt();
          if ((1164798569 != j) && (1701669481 != j)) {
            i++;
          } else {
            return new OffsetInfo(l2 + l1, l4);
          }
        }
      }
      throw new IOException("Cannot read metadata.");
    }
    throw new IOException("Cannot read metadata.");
  }
  
  /* Error */
  static MetadataList read(android.content.res.AssetManager paramAssetManager, String paramString)
    throws IOException
  {
    // Byte code:
    //   0: aload_0
    //   1: aload_1
    //   2: invokevirtual 69	android/content/res/AssetManager:open	(Ljava/lang/String;)Ljava/io/InputStream;
    //   5: astore_1
    //   6: aload_1
    //   7: invokestatic 72	androidx/emoji2/text/MetadataListReader:read	(Ljava/io/InputStream;)Landroidx/emoji2/text/flatbuffer/MetadataList;
    //   10: astore_0
    //   11: aload_1
    //   12: ifnull +7 -> 19
    //   15: aload_1
    //   16: invokevirtual 77	java/io/InputStream:close	()V
    //   19: aload_0
    //   20: areturn
    //   21: astore_0
    //   22: aload_1
    //   23: ifnull +16 -> 39
    //   26: aload_1
    //   27: invokevirtual 77	java/io/InputStream:close	()V
    //   30: goto +9 -> 39
    //   33: astore_1
    //   34: aload_0
    //   35: aload_1
    //   36: invokevirtual 83	java/lang/Throwable:addSuppressed	(Ljava/lang/Throwable;)V
    //   39: aload_0
    //   40: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	41	0	paramAssetManager	android.content.res.AssetManager
    //   0	41	1	paramString	String
    // Exception table:
    //   from	to	target	type
    //   6	11	21	finally
    //   26	30	33	finally
  }
  
  static MetadataList read(InputStream paramInputStream)
    throws IOException
  {
    Object localObject = new InputStreamOpenTypeReader(paramInputStream);
    OffsetInfo localOffsetInfo = findOffsetInfo((OpenTypeReader)localObject);
    ((OpenTypeReader)localObject).skip((int)(localOffsetInfo.getStartOffset() - ((OpenTypeReader)localObject).getPosition()));
    localObject = ByteBuffer.allocate((int)localOffsetInfo.getLength());
    int i = paramInputStream.read(((ByteBuffer)localObject).array());
    if (i == localOffsetInfo.getLength()) {
      return MetadataList.getRootAsMetadataList((ByteBuffer)localObject);
    }
    throw new IOException("Needed " + localOffsetInfo.getLength() + " bytes, got " + i);
  }
  
  static MetadataList read(ByteBuffer paramByteBuffer)
    throws IOException
  {
    paramByteBuffer = paramByteBuffer.duplicate();
    paramByteBuffer.position((int)findOffsetInfo(new ByteBufferReader(paramByteBuffer)).getStartOffset());
    return MetadataList.getRootAsMetadataList(paramByteBuffer);
  }
  
  static long toUnsignedInt(int paramInt)
  {
    return paramInt & 0xFFFFFFFF;
  }
  
  static int toUnsignedShort(short paramShort)
  {
    return 0xFFFF & paramShort;
  }
  
  private static class ByteBufferReader
    implements MetadataListReader.OpenTypeReader
  {
    private final ByteBuffer mByteBuffer;
    
    ByteBufferReader(ByteBuffer paramByteBuffer)
    {
      this.mByteBuffer = paramByteBuffer;
      paramByteBuffer.order(ByteOrder.BIG_ENDIAN);
    }
    
    public long getPosition()
    {
      return this.mByteBuffer.position();
    }
    
    public int readTag()
      throws IOException
    {
      return this.mByteBuffer.getInt();
    }
    
    public long readUnsignedInt()
      throws IOException
    {
      return MetadataListReader.toUnsignedInt(this.mByteBuffer.getInt());
    }
    
    public int readUnsignedShort()
      throws IOException
    {
      return MetadataListReader.toUnsignedShort(this.mByteBuffer.getShort());
    }
    
    public void skip(int paramInt)
      throws IOException
    {
      ByteBuffer localByteBuffer = this.mByteBuffer;
      localByteBuffer.position(localByteBuffer.position() + paramInt);
    }
  }
  
  private static class InputStreamOpenTypeReader
    implements MetadataListReader.OpenTypeReader
  {
    private final byte[] mByteArray;
    private final ByteBuffer mByteBuffer;
    private final InputStream mInputStream;
    private long mPosition = 0L;
    
    InputStreamOpenTypeReader(InputStream paramInputStream)
    {
      this.mInputStream = paramInputStream;
      paramInputStream = new byte[4];
      this.mByteArray = paramInputStream;
      paramInputStream = ByteBuffer.wrap(paramInputStream);
      this.mByteBuffer = paramInputStream;
      paramInputStream.order(ByteOrder.BIG_ENDIAN);
    }
    
    private void read(int paramInt)
      throws IOException
    {
      if (this.mInputStream.read(this.mByteArray, 0, paramInt) == paramInt)
      {
        this.mPosition += paramInt;
        return;
      }
      throw new IOException("read failed");
    }
    
    public long getPosition()
    {
      return this.mPosition;
    }
    
    public int readTag()
      throws IOException
    {
      this.mByteBuffer.position(0);
      read(4);
      return this.mByteBuffer.getInt();
    }
    
    public long readUnsignedInt()
      throws IOException
    {
      this.mByteBuffer.position(0);
      read(4);
      return MetadataListReader.toUnsignedInt(this.mByteBuffer.getInt());
    }
    
    public int readUnsignedShort()
      throws IOException
    {
      this.mByteBuffer.position(0);
      read(2);
      return MetadataListReader.toUnsignedShort(this.mByteBuffer.getShort());
    }
    
    public void skip(int paramInt)
      throws IOException
    {
      while (paramInt > 0)
      {
        int i = (int)this.mInputStream.skip(paramInt);
        if (i >= 1)
        {
          paramInt -= i;
          this.mPosition += i;
        }
        else
        {
          throw new IOException("Skip didn't move at least 1 byte forward");
        }
      }
    }
  }
  
  private static class OffsetInfo
  {
    private final long mLength;
    private final long mStartOffset;
    
    OffsetInfo(long paramLong1, long paramLong2)
    {
      this.mStartOffset = paramLong1;
      this.mLength = paramLong2;
    }
    
    long getLength()
    {
      return this.mLength;
    }
    
    long getStartOffset()
    {
      return this.mStartOffset;
    }
  }
  
  private static abstract interface OpenTypeReader
  {
    public static final int UINT16_BYTE_COUNT = 2;
    public static final int UINT32_BYTE_COUNT = 4;
    
    public abstract long getPosition();
    
    public abstract int readTag()
      throws IOException;
    
    public abstract long readUnsignedInt()
      throws IOException;
    
    public abstract int readUnsignedShort()
      throws IOException;
    
    public abstract void skip(int paramInt)
      throws IOException;
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/emoji2/text/MetadataListReader.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */