package androidx.emoji2.text.flatbuffer;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

public class FlexBuffers
{
  static final boolean $assertionsDisabled = false;
  private static final ReadBuf EMPTY_BB = new ArrayReadWriteBuf(new byte[] { 0 }, 1);
  public static final int FBT_BLOB = 25;
  public static final int FBT_BOOL = 26;
  public static final int FBT_FLOAT = 3;
  public static final int FBT_INDIRECT_FLOAT = 8;
  public static final int FBT_INDIRECT_INT = 6;
  public static final int FBT_INDIRECT_UINT = 7;
  public static final int FBT_INT = 1;
  public static final int FBT_KEY = 4;
  public static final int FBT_MAP = 9;
  public static final int FBT_NULL = 0;
  public static final int FBT_STRING = 5;
  public static final int FBT_UINT = 2;
  public static final int FBT_VECTOR = 10;
  public static final int FBT_VECTOR_BOOL = 36;
  public static final int FBT_VECTOR_FLOAT = 13;
  public static final int FBT_VECTOR_FLOAT2 = 18;
  public static final int FBT_VECTOR_FLOAT3 = 21;
  public static final int FBT_VECTOR_FLOAT4 = 24;
  public static final int FBT_VECTOR_INT = 11;
  public static final int FBT_VECTOR_INT2 = 16;
  public static final int FBT_VECTOR_INT3 = 19;
  public static final int FBT_VECTOR_INT4 = 22;
  public static final int FBT_VECTOR_KEY = 14;
  public static final int FBT_VECTOR_STRING_DEPRECATED = 15;
  public static final int FBT_VECTOR_UINT = 12;
  public static final int FBT_VECTOR_UINT2 = 17;
  public static final int FBT_VECTOR_UINT3 = 20;
  public static final int FBT_VECTOR_UINT4 = 23;
  
  public static Reference getRoot(ReadBuf paramReadBuf)
  {
    int j = paramReadBuf.limit() - 1;
    int i = paramReadBuf.get(j);
    j--;
    return new Reference(paramReadBuf, j - i, i, Unsigned.byteToUnsignedInt(paramReadBuf.get(j)));
  }
  
  @Deprecated
  public static Reference getRoot(ByteBuffer paramByteBuffer)
  {
    if (paramByteBuffer.hasArray()) {
      paramByteBuffer = new ArrayReadWriteBuf(paramByteBuffer.array(), paramByteBuffer.limit());
    } else {
      paramByteBuffer = new ByteBufferReadWriteBuf(paramByteBuffer);
    }
    return getRoot(paramByteBuffer);
  }
  
  private static int indirect(ReadBuf paramReadBuf, int paramInt1, int paramInt2)
  {
    return (int)(paramInt1 - readUInt(paramReadBuf, paramInt1, paramInt2));
  }
  
  static boolean isTypeInline(int paramInt)
  {
    boolean bool;
    if ((paramInt > 3) && (paramInt != 26)) {
      bool = false;
    } else {
      bool = true;
    }
    return bool;
  }
  
  static boolean isTypedVector(int paramInt)
  {
    boolean bool;
    if (((paramInt >= 11) && (paramInt <= 15)) || (paramInt == 36)) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  static boolean isTypedVectorElementType(int paramInt)
  {
    boolean bool = true;
    if (((paramInt >= 1) && (paramInt <= 4)) || (paramInt != 26)) {
      bool = false;
    }
    return bool;
  }
  
  private static double readDouble(ReadBuf paramReadBuf, int paramInt1, int paramInt2)
  {
    switch (paramInt2)
    {
    default: 
      return -1.0D;
    case 8: 
      return paramReadBuf.getDouble(paramInt1);
    }
    return paramReadBuf.getFloat(paramInt1);
  }
  
  private static int readInt(ReadBuf paramReadBuf, int paramInt1, int paramInt2)
  {
    return (int)readLong(paramReadBuf, paramInt1, paramInt2);
  }
  
  private static long readLong(ReadBuf paramReadBuf, int paramInt1, int paramInt2)
  {
    switch (paramInt2)
    {
    default: 
      return -1L;
    case 8: 
      return paramReadBuf.getLong(paramInt1);
    case 4: 
      return paramReadBuf.getInt(paramInt1);
    case 2: 
      return paramReadBuf.getShort(paramInt1);
    }
    return paramReadBuf.get(paramInt1);
  }
  
  private static long readUInt(ReadBuf paramReadBuf, int paramInt1, int paramInt2)
  {
    switch (paramInt2)
    {
    default: 
      return -1L;
    case 8: 
      return paramReadBuf.getLong(paramInt1);
    case 4: 
      return Unsigned.intToUnsignedLong(paramReadBuf.getInt(paramInt1));
    case 2: 
      return Unsigned.shortToUnsignedInt(paramReadBuf.getShort(paramInt1));
    }
    return Unsigned.byteToUnsignedInt(paramReadBuf.get(paramInt1));
  }
  
  static int toTypedVector(int paramInt1, int paramInt2)
  {
    if (isTypedVectorElementType(paramInt1))
    {
      switch (paramInt2)
      {
      case 1: 
      default: 
        break;
      case 4: 
        return paramInt1 - 1 + 22;
      case 3: 
        return paramInt1 - 1 + 19;
      case 2: 
        return paramInt1 - 1 + 16;
      case 0: 
        return paramInt1 - 1 + 11;
      }
      throw new AssertionError();
    }
    throw new AssertionError();
  }
  
  static int toTypedVectorElementType(int paramInt)
  {
    return paramInt - 11 + 1;
  }
  
  public static class Blob
    extends FlexBuffers.Sized
  {
    static final boolean $assertionsDisabled = false;
    static final Blob EMPTY = new Blob(FlexBuffers.EMPTY_BB, 1, 1);
    
    Blob(ReadBuf paramReadBuf, int paramInt1, int paramInt2)
    {
      super(paramInt1, paramInt2);
    }
    
    public static Blob empty()
    {
      return EMPTY;
    }
    
    public ByteBuffer data()
    {
      ByteBuffer localByteBuffer = ByteBuffer.wrap(this.bb.data());
      localByteBuffer.position(this.end);
      localByteBuffer.limit(this.end + size());
      return localByteBuffer.asReadOnlyBuffer().slice();
    }
    
    public byte get(int paramInt)
    {
      if ((paramInt >= 0) && (paramInt <= size())) {
        return this.bb.get(this.end + paramInt);
      }
      throw new AssertionError();
    }
    
    public byte[] getBytes()
    {
      int j = size();
      byte[] arrayOfByte = new byte[j];
      for (int i = 0; i < j; i++) {
        arrayOfByte[i] = this.bb.get(this.end + i);
      }
      return arrayOfByte;
    }
    
    public String toString()
    {
      return this.bb.getString(this.end, size());
    }
    
    public StringBuilder toString(StringBuilder paramStringBuilder)
    {
      paramStringBuilder.append('"');
      paramStringBuilder.append(this.bb.getString(this.end, size()));
      return paramStringBuilder.append('"');
    }
  }
  
  public static class FlexBufferException
    extends RuntimeException
  {
    FlexBufferException(String paramString)
    {
      super();
    }
  }
  
  public static class Key
    extends FlexBuffers.Object
  {
    private static final Key EMPTY = new Key(FlexBuffers.EMPTY_BB, 0, 0);
    
    Key(ReadBuf paramReadBuf, int paramInt1, int paramInt2)
    {
      super(paramInt1, paramInt2);
    }
    
    public static Key empty()
    {
      return EMPTY;
    }
    
    int compareTo(byte[] paramArrayOfByte)
    {
      int j = this.end;
      int i = 0;
      for (;;)
      {
        int k = this.bb.get(j);
        int m = paramArrayOfByte[i];
        if (k == 0) {
          return k - m;
        }
        j++;
        i++;
        if (i == paramArrayOfByte.length) {
          return k - m;
        }
        if (k != m) {
          return k - m;
        }
      }
    }
    
    public boolean equals(Object paramObject)
    {
      boolean bool1 = paramObject instanceof Key;
      boolean bool2 = false;
      if (!bool1) {
        return false;
      }
      bool1 = bool2;
      if (((Key)paramObject).end == this.end)
      {
        bool1 = bool2;
        if (((Key)paramObject).byteWidth == this.byteWidth) {
          bool1 = true;
        }
      }
      return bool1;
    }
    
    public int hashCode()
    {
      return this.end ^ this.byteWidth;
    }
    
    public String toString()
    {
      for (int i = this.end;; i++) {
        if (this.bb.get(i) == 0)
        {
          int j = this.end;
          return this.bb.getString(this.end, i - j);
        }
      }
    }
    
    public StringBuilder toString(StringBuilder paramStringBuilder)
    {
      return paramStringBuilder.append(toString());
    }
  }
  
  public static class KeyVector
  {
    private final FlexBuffers.TypedVector vec;
    
    KeyVector(FlexBuffers.TypedVector paramTypedVector)
    {
      this.vec = paramTypedVector;
    }
    
    public FlexBuffers.Key get(int paramInt)
    {
      if (paramInt >= size()) {
        return FlexBuffers.Key.EMPTY;
      }
      int j = this.vec.end;
      int i = this.vec.byteWidth;
      return new FlexBuffers.Key(this.vec.bb, FlexBuffers.indirect(this.vec.bb, j + i * paramInt, this.vec.byteWidth), 1);
    }
    
    public int size()
    {
      return this.vec.size();
    }
    
    public String toString()
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append('[');
      for (int i = 0; i < this.vec.size(); i++)
      {
        this.vec.get(i).toString(localStringBuilder);
        if (i != this.vec.size() - 1) {
          localStringBuilder.append(", ");
        }
      }
      return "]";
    }
  }
  
  public static class Map
    extends FlexBuffers.Vector
  {
    private static final Map EMPTY_MAP = new Map(FlexBuffers.EMPTY_BB, 1, 1);
    
    Map(ReadBuf paramReadBuf, int paramInt1, int paramInt2)
    {
      super(paramInt1, paramInt2);
    }
    
    private int binarySearch(FlexBuffers.KeyVector paramKeyVector, byte[] paramArrayOfByte)
    {
      int j = 0;
      int i = paramKeyVector.size() - 1;
      while (j <= i)
      {
        int m = j + i >>> 1;
        int k = paramKeyVector.get(m).compareTo(paramArrayOfByte);
        if (k < 0)
        {
          j = m + 1;
        }
        else
        {
          if (k <= 0) {
            break label63;
          }
          i = m - 1;
        }
        continue;
        label63:
        return m;
      }
      return -(j + 1);
    }
    
    public static Map empty()
    {
      return EMPTY_MAP;
    }
    
    public FlexBuffers.Reference get(String paramString)
    {
      return get(paramString.getBytes(StandardCharsets.UTF_8));
    }
    
    public FlexBuffers.Reference get(byte[] paramArrayOfByte)
    {
      FlexBuffers.KeyVector localKeyVector = keys();
      int j = localKeyVector.size();
      int i = binarySearch(localKeyVector, paramArrayOfByte);
      if ((i >= 0) && (i < j)) {
        return get(i);
      }
      return FlexBuffers.Reference.access$600();
    }
    
    public FlexBuffers.KeyVector keys()
    {
      int i = this.end - this.byteWidth * 3;
      return new FlexBuffers.KeyVector(new FlexBuffers.TypedVector(this.bb, FlexBuffers.indirect(this.bb, i, this.byteWidth), FlexBuffers.readInt(this.bb, this.byteWidth + i, this.byteWidth), 4));
    }
    
    public StringBuilder toString(StringBuilder paramStringBuilder)
    {
      paramStringBuilder.append("{ ");
      FlexBuffers.KeyVector localKeyVector = keys();
      int j = size();
      FlexBuffers.Vector localVector = values();
      for (int i = 0; i < j; i++)
      {
        paramStringBuilder.append('"').append(localKeyVector.get(i).toString()).append("\" : ");
        paramStringBuilder.append(localVector.get(i).toString());
        if (i != j - 1) {
          paramStringBuilder.append(", ");
        }
      }
      paramStringBuilder.append(" }");
      return paramStringBuilder;
    }
    
    public FlexBuffers.Vector values()
    {
      return new FlexBuffers.Vector(this.bb, this.end, this.byteWidth);
    }
  }
  
  private static abstract class Object
  {
    ReadBuf bb;
    int byteWidth;
    int end;
    
    Object(ReadBuf paramReadBuf, int paramInt1, int paramInt2)
    {
      this.bb = paramReadBuf;
      this.end = paramInt1;
      this.byteWidth = paramInt2;
    }
    
    public String toString()
    {
      return toString(new StringBuilder(128)).toString();
    }
    
    public abstract StringBuilder toString(StringBuilder paramStringBuilder);
  }
  
  public static class Reference
  {
    private static final Reference NULL_REFERENCE = new Reference(FlexBuffers.EMPTY_BB, 0, 1, 0);
    private ReadBuf bb;
    private int byteWidth;
    private int end;
    private int parentWidth;
    private int type;
    
    Reference(ReadBuf paramReadBuf, int paramInt1, int paramInt2, int paramInt3)
    {
      this(paramReadBuf, paramInt1, paramInt2, 1 << (paramInt3 & 0x3), paramInt3 >> 2);
    }
    
    Reference(ReadBuf paramReadBuf, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
    {
      this.bb = paramReadBuf;
      this.end = paramInt1;
      this.parentWidth = paramInt2;
      this.byteWidth = paramInt3;
      this.type = paramInt4;
    }
    
    public FlexBuffers.Blob asBlob()
    {
      if ((!isBlob()) && (!isString())) {
        return FlexBuffers.Blob.empty();
      }
      ReadBuf localReadBuf = this.bb;
      return new FlexBuffers.Blob(localReadBuf, FlexBuffers.indirect(localReadBuf, this.end, this.parentWidth), this.byteWidth);
    }
    
    public boolean asBoolean()
    {
      boolean bool3 = isBoolean();
      boolean bool1 = true;
      boolean bool2 = true;
      if (bool3)
      {
        if (this.bb.get(this.end) != 0) {
          bool1 = bool2;
        } else {
          bool1 = false;
        }
        return bool1;
      }
      if (asUInt() == 0L) {
        bool1 = false;
      }
      return bool1;
    }
    
    public double asFloat()
    {
      int i = this.type;
      if (i == 3) {
        return FlexBuffers.readDouble(this.bb, this.end, this.parentWidth);
      }
      ReadBuf localReadBuf;
      switch (i)
      {
      default: 
        return 0.0D;
      case 10: 
        return asVector().size();
      case 8: 
        localReadBuf = this.bb;
        return FlexBuffers.readDouble(localReadBuf, FlexBuffers.access$200(localReadBuf, this.end, this.parentWidth), this.byteWidth);
      case 7: 
        localReadBuf = this.bb;
        return FlexBuffers.readUInt(localReadBuf, FlexBuffers.access$200(localReadBuf, this.end, this.parentWidth), this.byteWidth);
      case 6: 
        localReadBuf = this.bb;
        return FlexBuffers.readInt(localReadBuf, FlexBuffers.access$200(localReadBuf, this.end, this.parentWidth), this.byteWidth);
      case 5: 
        return Double.parseDouble(asString());
      case 2: 
      case 26: 
        return FlexBuffers.readUInt(this.bb, this.end, this.parentWidth);
      case 1: 
        return FlexBuffers.readInt(this.bb, this.end, this.parentWidth);
      }
      return 0.0D;
    }
    
    public int asInt()
    {
      int i = this.type;
      if (i == 1) {
        return FlexBuffers.readInt(this.bb, this.end, this.parentWidth);
      }
      ReadBuf localReadBuf;
      switch (i)
      {
      default: 
        return 0;
      case 26: 
        return FlexBuffers.readInt(this.bb, this.end, this.parentWidth);
      case 10: 
        return asVector().size();
      case 8: 
        localReadBuf = this.bb;
        return (int)FlexBuffers.readDouble(localReadBuf, FlexBuffers.access$200(localReadBuf, this.end, this.parentWidth), this.byteWidth);
      case 7: 
        localReadBuf = this.bb;
        return (int)FlexBuffers.readUInt(localReadBuf, FlexBuffers.access$200(localReadBuf, this.end, this.parentWidth), this.parentWidth);
      case 6: 
        localReadBuf = this.bb;
        return FlexBuffers.readInt(localReadBuf, FlexBuffers.access$200(localReadBuf, this.end, this.parentWidth), this.byteWidth);
      case 5: 
        return Integer.parseInt(asString());
      case 3: 
        return (int)FlexBuffers.readDouble(this.bb, this.end, this.parentWidth);
      case 2: 
        return (int)FlexBuffers.readUInt(this.bb, this.end, this.parentWidth);
      }
      return 0;
    }
    
    public FlexBuffers.Key asKey()
    {
      if (isKey())
      {
        ReadBuf localReadBuf = this.bb;
        return new FlexBuffers.Key(localReadBuf, FlexBuffers.indirect(localReadBuf, this.end, this.parentWidth), this.byteWidth);
      }
      return FlexBuffers.Key.empty();
    }
    
    public long asLong()
    {
      int i = this.type;
      if (i == 1) {
        return FlexBuffers.readLong(this.bb, this.end, this.parentWidth);
      }
      ReadBuf localReadBuf;
      switch (i)
      {
      default: 
        return 0L;
      case 26: 
        return FlexBuffers.readInt(this.bb, this.end, this.parentWidth);
      case 10: 
        return asVector().size();
      case 8: 
        localReadBuf = this.bb;
        return FlexBuffers.readDouble(localReadBuf, FlexBuffers.access$200(localReadBuf, this.end, this.parentWidth), this.byteWidth);
      case 7: 
        localReadBuf = this.bb;
        return FlexBuffers.readUInt(localReadBuf, FlexBuffers.access$200(localReadBuf, this.end, this.parentWidth), this.parentWidth);
      case 6: 
        localReadBuf = this.bb;
        return FlexBuffers.readLong(localReadBuf, FlexBuffers.access$200(localReadBuf, this.end, this.parentWidth), this.byteWidth);
      case 5: 
        try
        {
          long l = Long.parseLong(asString());
          return l;
        }
        catch (NumberFormatException localNumberFormatException)
        {
          return 0L;
        }
      case 3: 
        return FlexBuffers.readDouble(this.bb, this.end, this.parentWidth);
      case 2: 
        return FlexBuffers.readUInt(this.bb, this.end, this.parentWidth);
      }
      return 0L;
    }
    
    public FlexBuffers.Map asMap()
    {
      if (isMap())
      {
        ReadBuf localReadBuf = this.bb;
        return new FlexBuffers.Map(localReadBuf, FlexBuffers.indirect(localReadBuf, this.end, this.parentWidth), this.byteWidth);
      }
      return FlexBuffers.Map.empty();
    }
    
    public String asString()
    {
      int i;
      int j;
      if (isString())
      {
        i = FlexBuffers.indirect(this.bb, this.end, this.parentWidth);
        ReadBuf localReadBuf = this.bb;
        j = this.byteWidth;
        j = (int)FlexBuffers.readUInt(localReadBuf, i - j, j);
        return this.bb.getString(i, j);
      }
      if (isKey())
      {
        j = FlexBuffers.indirect(this.bb, this.end, this.byteWidth);
        for (i = j;; i++) {
          if (this.bb.get(i) == 0) {
            return this.bb.getString(j, i - j);
          }
        }
      }
      return "";
    }
    
    public long asUInt()
    {
      int i = this.type;
      if (i == 2) {
        return FlexBuffers.readUInt(this.bb, this.end, this.parentWidth);
      }
      ReadBuf localReadBuf;
      switch (i)
      {
      default: 
        return 0L;
      case 26: 
        return FlexBuffers.readInt(this.bb, this.end, this.parentWidth);
      case 10: 
        return asVector().size();
      case 8: 
        localReadBuf = this.bb;
        return FlexBuffers.readDouble(localReadBuf, FlexBuffers.access$200(localReadBuf, this.end, this.parentWidth), this.parentWidth);
      case 7: 
        localReadBuf = this.bb;
        return FlexBuffers.readUInt(localReadBuf, FlexBuffers.access$200(localReadBuf, this.end, this.parentWidth), this.byteWidth);
      case 6: 
        localReadBuf = this.bb;
        return FlexBuffers.readLong(localReadBuf, FlexBuffers.access$200(localReadBuf, this.end, this.parentWidth), this.byteWidth);
      case 5: 
        return Long.parseLong(asString());
      case 3: 
        return FlexBuffers.readDouble(this.bb, this.end, this.parentWidth);
      case 1: 
        return FlexBuffers.readLong(this.bb, this.end, this.parentWidth);
      }
      return 0L;
    }
    
    public FlexBuffers.Vector asVector()
    {
      ReadBuf localReadBuf;
      if (isVector())
      {
        localReadBuf = this.bb;
        return new FlexBuffers.Vector(localReadBuf, FlexBuffers.indirect(localReadBuf, this.end, this.parentWidth), this.byteWidth);
      }
      int i = this.type;
      if (i == 15)
      {
        localReadBuf = this.bb;
        return new FlexBuffers.TypedVector(localReadBuf, FlexBuffers.indirect(localReadBuf, this.end, this.parentWidth), this.byteWidth, 4);
      }
      if (FlexBuffers.isTypedVector(i))
      {
        localReadBuf = this.bb;
        return new FlexBuffers.TypedVector(localReadBuf, FlexBuffers.indirect(localReadBuf, this.end, this.parentWidth), this.byteWidth, FlexBuffers.toTypedVectorElementType(this.type));
      }
      return FlexBuffers.Vector.empty();
    }
    
    public int getType()
    {
      return this.type;
    }
    
    public boolean isBlob()
    {
      boolean bool;
      if (this.type == 25) {
        bool = true;
      } else {
        bool = false;
      }
      return bool;
    }
    
    public boolean isBoolean()
    {
      boolean bool;
      if (this.type == 26) {
        bool = true;
      } else {
        bool = false;
      }
      return bool;
    }
    
    public boolean isFloat()
    {
      int i = this.type;
      boolean bool;
      if ((i != 3) && (i != 8)) {
        bool = false;
      } else {
        bool = true;
      }
      return bool;
    }
    
    public boolean isInt()
    {
      int i = this.type;
      boolean bool2 = true;
      boolean bool1 = bool2;
      if (i != 1) {
        if (i == 6) {
          bool1 = bool2;
        } else {
          bool1 = false;
        }
      }
      return bool1;
    }
    
    public boolean isIntOrUInt()
    {
      boolean bool;
      if ((!isInt()) && (!isUInt())) {
        bool = false;
      } else {
        bool = true;
      }
      return bool;
    }
    
    public boolean isKey()
    {
      boolean bool;
      if (this.type == 4) {
        bool = true;
      } else {
        bool = false;
      }
      return bool;
    }
    
    public boolean isMap()
    {
      boolean bool;
      if (this.type == 9) {
        bool = true;
      } else {
        bool = false;
      }
      return bool;
    }
    
    public boolean isNull()
    {
      boolean bool;
      if (this.type == 0) {
        bool = true;
      } else {
        bool = false;
      }
      return bool;
    }
    
    public boolean isNumeric()
    {
      boolean bool;
      if ((!isIntOrUInt()) && (!isFloat())) {
        bool = false;
      } else {
        bool = true;
      }
      return bool;
    }
    
    public boolean isString()
    {
      boolean bool;
      if (this.type == 5) {
        bool = true;
      } else {
        bool = false;
      }
      return bool;
    }
    
    public boolean isTypedVector()
    {
      return FlexBuffers.isTypedVector(this.type);
    }
    
    public boolean isUInt()
    {
      int i = this.type;
      boolean bool;
      if ((i != 2) && (i != 7)) {
        bool = false;
      } else {
        bool = true;
      }
      return bool;
    }
    
    public boolean isVector()
    {
      int i = this.type;
      boolean bool;
      if ((i != 10) && (i != 9)) {
        bool = false;
      } else {
        bool = true;
      }
      return bool;
    }
    
    public String toString()
    {
      return toString(new StringBuilder(128)).toString();
    }
    
    StringBuilder toString(StringBuilder paramStringBuilder)
    {
      switch (this.type)
      {
      case 27: 
      case 28: 
      case 29: 
      case 30: 
      case 31: 
      case 32: 
      case 33: 
      case 34: 
      case 35: 
      default: 
        return paramStringBuilder;
      case 26: 
        return paramStringBuilder.append(asBoolean());
      case 25: 
        return asBlob().toString(paramStringBuilder);
      case 16: 
      case 17: 
      case 18: 
      case 19: 
      case 20: 
      case 21: 
      case 22: 
      case 23: 
      case 24: 
        throw new FlexBuffers.FlexBufferException("not_implemented:" + this.type);
      case 11: 
      case 12: 
      case 13: 
      case 14: 
      case 15: 
      case 36: 
        return paramStringBuilder.append(asVector());
      case 10: 
        return asVector().toString(paramStringBuilder);
      case 9: 
        return asMap().toString(paramStringBuilder);
      case 5: 
        return paramStringBuilder.append('"').append(asString()).append('"');
      case 4: 
        return asKey().toString(paramStringBuilder.append('"')).append('"');
      case 3: 
      case 8: 
        return paramStringBuilder.append(asFloat());
      case 2: 
      case 7: 
        return paramStringBuilder.append(asUInt());
      case 1: 
      case 6: 
        return paramStringBuilder.append(asLong());
      }
      return paramStringBuilder.append("null");
    }
  }
  
  private static abstract class Sized
    extends FlexBuffers.Object
  {
    protected final int size;
    
    Sized(ReadBuf paramReadBuf, int paramInt1, int paramInt2)
    {
      super(paramInt1, paramInt2);
      this.size = FlexBuffers.readInt(this.bb, paramInt1 - paramInt2, paramInt2);
    }
    
    public int size()
    {
      return this.size;
    }
  }
  
  public static class TypedVector
    extends FlexBuffers.Vector
  {
    private static final TypedVector EMPTY_VECTOR = new TypedVector(FlexBuffers.EMPTY_BB, 1, 1, 1);
    private final int elemType;
    
    TypedVector(ReadBuf paramReadBuf, int paramInt1, int paramInt2, int paramInt3)
    {
      super(paramInt1, paramInt2);
      this.elemType = paramInt3;
    }
    
    public static TypedVector empty()
    {
      return EMPTY_VECTOR;
    }
    
    public FlexBuffers.Reference get(int paramInt)
    {
      if (paramInt >= size()) {
        return FlexBuffers.Reference.NULL_REFERENCE;
      }
      int j = this.end;
      int i = this.byteWidth;
      return new FlexBuffers.Reference(this.bb, j + i * paramInt, this.byteWidth, 1, this.elemType);
    }
    
    public int getElemType()
    {
      return this.elemType;
    }
    
    public boolean isEmptyVector()
    {
      boolean bool;
      if (this == EMPTY_VECTOR) {
        bool = true;
      } else {
        bool = false;
      }
      return bool;
    }
  }
  
  static class Unsigned
  {
    static int byteToUnsignedInt(byte paramByte)
    {
      return paramByte & 0xFF;
    }
    
    static long intToUnsignedLong(int paramInt)
    {
      return paramInt & 0xFFFFFFFF;
    }
    
    static int shortToUnsignedInt(short paramShort)
    {
      return 0xFFFF & paramShort;
    }
  }
  
  public static class Vector
    extends FlexBuffers.Sized
  {
    private static final Vector EMPTY_VECTOR = new Vector(FlexBuffers.EMPTY_BB, 1, 1);
    
    Vector(ReadBuf paramReadBuf, int paramInt1, int paramInt2)
    {
      super(paramInt1, paramInt2);
    }
    
    public static Vector empty()
    {
      return EMPTY_VECTOR;
    }
    
    public FlexBuffers.Reference get(int paramInt)
    {
      long l = size();
      if (paramInt >= l) {
        return FlexBuffers.Reference.NULL_REFERENCE;
      }
      int j = FlexBuffers.Unsigned.byteToUnsignedInt(this.bb.get((int)(this.end + this.byteWidth * l + paramInt)));
      int k = this.end;
      int i = this.byteWidth;
      return new FlexBuffers.Reference(this.bb, k + i * paramInt, this.byteWidth, j);
    }
    
    public boolean isEmpty()
    {
      boolean bool;
      if (this == EMPTY_VECTOR) {
        bool = true;
      } else {
        bool = false;
      }
      return bool;
    }
    
    public StringBuilder toString(StringBuilder paramStringBuilder)
    {
      paramStringBuilder.append("[ ");
      int j = size();
      for (int i = 0; i < j; i++)
      {
        get(i).toString(paramStringBuilder);
        if (i != j - 1) {
          paramStringBuilder.append(", ");
        }
      }
      paramStringBuilder.append(" ]");
      return paramStringBuilder;
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/emoji2/text/flatbuffer/FlexBuffers.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */