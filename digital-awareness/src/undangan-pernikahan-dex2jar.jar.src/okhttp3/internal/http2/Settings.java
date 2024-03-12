package okhttp3.internal.http2;

import kotlin.Metadata;
import kotlin.collections.ArraysKt;
import kotlin.jvm.internal.Intrinsics;

@Metadata(bv={1, 0, 3}, d1={"\000*\n\002\030\002\n\002\020\000\n\002\b\002\n\002\020\b\n\002\b\006\n\002\020\025\n\000\n\002\020\002\n\002\b\003\n\002\020\013\n\002\b\013\030\000 \0332\0020\001:\001\033B\005¢\006\002\020\002J\006\020\f\032\0020\rJ\021\020\016\032\0020\0042\006\020\017\032\0020\004H\002J\016\020\020\032\0020\0212\006\020\022\032\0020\021J\006\020\023\032\0020\004J\016\020\024\032\0020\0042\006\020\022\032\0020\004J\016\020\025\032\0020\0042\006\020\022\032\0020\004J\016\020\026\032\0020\0212\006\020\017\032\0020\004J\016\020\027\032\0020\r2\006\020\030\032\0020\000J\031\020\t\032\0020\0002\006\020\017\032\0020\0042\006\020\031\032\0020\004H\002J\006\020\032\032\0020\004R\021\020\003\032\0020\0048F¢\006\006\032\004\b\005\020\006R\021\020\007\032\0020\0048F¢\006\006\032\004\b\b\020\006R\016\020\t\032\0020\004X\016¢\006\002\n\000R\016\020\n\032\0020\013X\004¢\006\002\n\000¨\006\034"}, d2={"Lokhttp3/internal/http2/Settings;", "", "()V", "headerTableSize", "", "getHeaderTableSize", "()I", "initialWindowSize", "getInitialWindowSize", "set", "values", "", "clear", "", "get", "id", "getEnablePush", "", "defaultValue", "getMaxConcurrentStreams", "getMaxFrameSize", "getMaxHeaderListSize", "isSet", "merge", "other", "value", "size", "Companion", "okhttp"}, k=1, mv={1, 4, 0})
public final class Settings
{
  public static final int COUNT = 10;
  public static final Companion Companion = new Companion(null);
  public static final int DEFAULT_INITIAL_WINDOW_SIZE = 65535;
  public static final int ENABLE_PUSH = 2;
  public static final int HEADER_TABLE_SIZE = 1;
  public static final int INITIAL_WINDOW_SIZE = 7;
  public static final int MAX_CONCURRENT_STREAMS = 4;
  public static final int MAX_FRAME_SIZE = 5;
  public static final int MAX_HEADER_LIST_SIZE = 6;
  private int set;
  private final int[] values = new int[10];
  
  public final void clear()
  {
    this.set = 0;
    ArraysKt.fill$default(this.values, 0, 0, 0, 6, null);
  }
  
  public final int get(int paramInt)
  {
    return this.values[paramInt];
  }
  
  public final boolean getEnablePush(boolean paramBoolean)
  {
    int i = this.set;
    boolean bool = true;
    if ((i & 0x4) != 0) {
      if (this.values[2] == 1) {
        paramBoolean = bool;
      } else {
        paramBoolean = false;
      }
    }
    return paramBoolean;
  }
  
  public final int getHeaderTableSize()
  {
    int i;
    if ((this.set & 0x2) != 0) {
      i = this.values[1];
    } else {
      i = -1;
    }
    return i;
  }
  
  public final int getInitialWindowSize()
  {
    int i;
    if ((this.set & 0x80) != 0) {
      i = this.values[7];
    } else {
      i = 65535;
    }
    return i;
  }
  
  public final int getMaxConcurrentStreams()
  {
    int i;
    if ((this.set & 0x10) != 0) {
      i = this.values[4];
    } else {
      i = Integer.MAX_VALUE;
    }
    return i;
  }
  
  public final int getMaxFrameSize(int paramInt)
  {
    if ((this.set & 0x20) != 0) {
      paramInt = this.values[5];
    }
    return paramInt;
  }
  
  public final int getMaxHeaderListSize(int paramInt)
  {
    if ((this.set & 0x40) != 0) {
      paramInt = this.values[6];
    }
    return paramInt;
  }
  
  public final boolean isSet(int paramInt)
  {
    boolean bool = true;
    if ((this.set & 1 << paramInt) == 0) {
      bool = false;
    }
    return bool;
  }
  
  public final void merge(Settings paramSettings)
  {
    Intrinsics.checkNotNullParameter(paramSettings, "other");
    for (int i = 0; i < 10; i++) {
      if (paramSettings.isSet(i)) {
        set(i, paramSettings.get(i));
      }
    }
  }
  
  public final Settings set(int paramInt1, int paramInt2)
  {
    if (paramInt1 >= 0)
    {
      int[] arrayOfInt = this.values;
      if (paramInt1 < arrayOfInt.length)
      {
        this.set |= 1 << paramInt1;
        arrayOfInt[paramInt1] = paramInt2;
        return this;
      }
    }
    return this;
  }
  
  public final int size()
  {
    return Integer.bitCount(this.set);
  }
  
  @Metadata(bv={1, 0, 3}, d1={"\000\024\n\002\030\002\n\002\020\000\n\002\b\002\n\002\020\b\n\002\b\b\b\003\030\0002\0020\001B\007\b\002¢\006\002\020\002R\016\020\003\032\0020\004XT¢\006\002\n\000R\016\020\005\032\0020\004XT¢\006\002\n\000R\016\020\006\032\0020\004XT¢\006\002\n\000R\016\020\007\032\0020\004XT¢\006\002\n\000R\016\020\b\032\0020\004XT¢\006\002\n\000R\016\020\t\032\0020\004XT¢\006\002\n\000R\016\020\n\032\0020\004XT¢\006\002\n\000R\016\020\013\032\0020\004XT¢\006\002\n\000¨\006\f"}, d2={"Lokhttp3/internal/http2/Settings$Companion;", "", "()V", "COUNT", "", "DEFAULT_INITIAL_WINDOW_SIZE", "ENABLE_PUSH", "HEADER_TABLE_SIZE", "INITIAL_WINDOW_SIZE", "MAX_CONCURRENT_STREAMS", "MAX_FRAME_SIZE", "MAX_HEADER_LIST_SIZE", "okhttp"}, k=1, mv={1, 4, 0})
  public static final class Companion {}
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/okhttp3/internal/http2/Settings.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */