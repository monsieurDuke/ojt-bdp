package androidx.emoji2.text.flatbuffer;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public final class MetadataList
  extends Table
{
  public static void ValidateVersion() {}
  
  public static void addList(FlatBufferBuilder paramFlatBufferBuilder, int paramInt)
  {
    paramFlatBufferBuilder.addOffset(1, paramInt, 0);
  }
  
  public static void addSourceSha(FlatBufferBuilder paramFlatBufferBuilder, int paramInt)
  {
    paramFlatBufferBuilder.addOffset(2, paramInt, 0);
  }
  
  public static void addVersion(FlatBufferBuilder paramFlatBufferBuilder, int paramInt)
  {
    paramFlatBufferBuilder.addInt(0, paramInt, 0);
  }
  
  public static int createListVector(FlatBufferBuilder paramFlatBufferBuilder, int[] paramArrayOfInt)
  {
    paramFlatBufferBuilder.startVector(4, paramArrayOfInt.length, 4);
    for (int i = paramArrayOfInt.length - 1; i >= 0; i--) {
      paramFlatBufferBuilder.addOffset(paramArrayOfInt[i]);
    }
    return paramFlatBufferBuilder.endVector();
  }
  
  public static int createMetadataList(FlatBufferBuilder paramFlatBufferBuilder, int paramInt1, int paramInt2, int paramInt3)
  {
    paramFlatBufferBuilder.startTable(3);
    addSourceSha(paramFlatBufferBuilder, paramInt3);
    addList(paramFlatBufferBuilder, paramInt2);
    addVersion(paramFlatBufferBuilder, paramInt1);
    return endMetadataList(paramFlatBufferBuilder);
  }
  
  public static int endMetadataList(FlatBufferBuilder paramFlatBufferBuilder)
  {
    return paramFlatBufferBuilder.endTable();
  }
  
  public static void finishMetadataListBuffer(FlatBufferBuilder paramFlatBufferBuilder, int paramInt)
  {
    paramFlatBufferBuilder.finish(paramInt);
  }
  
  public static void finishSizePrefixedMetadataListBuffer(FlatBufferBuilder paramFlatBufferBuilder, int paramInt)
  {
    paramFlatBufferBuilder.finishSizePrefixed(paramInt);
  }
  
  public static MetadataList getRootAsMetadataList(ByteBuffer paramByteBuffer)
  {
    return getRootAsMetadataList(paramByteBuffer, new MetadataList());
  }
  
  public static MetadataList getRootAsMetadataList(ByteBuffer paramByteBuffer, MetadataList paramMetadataList)
  {
    paramByteBuffer.order(ByteOrder.LITTLE_ENDIAN);
    return paramMetadataList.__assign(paramByteBuffer.getInt(paramByteBuffer.position()) + paramByteBuffer.position(), paramByteBuffer);
  }
  
  public static void startListVector(FlatBufferBuilder paramFlatBufferBuilder, int paramInt)
  {
    paramFlatBufferBuilder.startVector(4, paramInt, 4);
  }
  
  public static void startMetadataList(FlatBufferBuilder paramFlatBufferBuilder)
  {
    paramFlatBufferBuilder.startTable(3);
  }
  
  public MetadataList __assign(int paramInt, ByteBuffer paramByteBuffer)
  {
    __init(paramInt, paramByteBuffer);
    return this;
  }
  
  public void __init(int paramInt, ByteBuffer paramByteBuffer)
  {
    __reset(paramInt, paramByteBuffer);
  }
  
  public MetadataItem list(int paramInt)
  {
    return list(new MetadataItem(), paramInt);
  }
  
  public MetadataItem list(MetadataItem paramMetadataItem, int paramInt)
  {
    int i = __offset(6);
    if (i != 0) {
      paramMetadataItem = paramMetadataItem.__assign(__indirect(__vector(i) + paramInt * 4), this.bb);
    } else {
      paramMetadataItem = null;
    }
    return paramMetadataItem;
  }
  
  public int listLength()
  {
    int i = __offset(6);
    if (i != 0) {
      i = __vector_len(i);
    } else {
      i = 0;
    }
    return i;
  }
  
  public MetadataItem.Vector listVector()
  {
    return listVector(new MetadataItem.Vector());
  }
  
  public MetadataItem.Vector listVector(MetadataItem.Vector paramVector)
  {
    int i = __offset(6);
    if (i != 0) {
      paramVector = paramVector.__assign(__vector(i), 4, this.bb);
    } else {
      paramVector = null;
    }
    return paramVector;
  }
  
  public String sourceSha()
  {
    int i = __offset(8);
    String str;
    if (i != 0) {
      str = __string(this.bb_pos + i);
    } else {
      str = null;
    }
    return str;
  }
  
  public ByteBuffer sourceShaAsByteBuffer()
  {
    return __vector_as_bytebuffer(8, 1);
  }
  
  public ByteBuffer sourceShaInByteBuffer(ByteBuffer paramByteBuffer)
  {
    return __vector_in_bytebuffer(paramByteBuffer, 8, 1);
  }
  
  public int version()
  {
    int i = __offset(4);
    if (i != 0) {
      i = this.bb.getInt(this.bb_pos + i);
    } else {
      i = 0;
    }
    return i;
  }
  
  public static final class Vector
    extends BaseVector
  {
    public Vector __assign(int paramInt1, int paramInt2, ByteBuffer paramByteBuffer)
    {
      __reset(paramInt1, paramInt2, paramByteBuffer);
      return this;
    }
    
    public MetadataList get(int paramInt)
    {
      return get(new MetadataList(), paramInt);
    }
    
    public MetadataList get(MetadataList paramMetadataList, int paramInt)
    {
      return paramMetadataList.__assign(MetadataList.__indirect(__element(paramInt), this.bb), this.bb);
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/emoji2/text/flatbuffer/MetadataList.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */