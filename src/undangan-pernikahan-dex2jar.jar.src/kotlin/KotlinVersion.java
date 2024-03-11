package kotlin;

import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.IntRange;

@Metadata(d1={"\000&\n\002\030\002\n\002\020\017\n\000\n\002\020\b\n\002\b\f\n\002\020\013\n\002\020\000\n\002\b\003\n\002\020\016\n\002\b\003\b\007\030\000 \0272\b\022\004\022\0020\0000\001:\001\027B\027\b\026\022\006\020\002\032\0020\003\022\006\020\004\032\0020\003¢\006\002\020\005B\035\022\006\020\002\032\0020\003\022\006\020\004\032\0020\003\022\006\020\006\032\0020\003¢\006\002\020\007J\021\020\r\032\0020\0032\006\020\016\032\0020\000H\002J\023\020\017\032\0020\0202\b\020\016\032\004\030\0010\021H\002J\b\020\022\032\0020\003H\026J\026\020\023\032\0020\0202\006\020\002\032\0020\0032\006\020\004\032\0020\003J\036\020\023\032\0020\0202\006\020\002\032\0020\0032\006\020\004\032\0020\0032\006\020\006\032\0020\003J\b\020\024\032\0020\025H\026J \020\026\032\0020\0032\006\020\002\032\0020\0032\006\020\004\032\0020\0032\006\020\006\032\0020\003H\002R\021\020\002\032\0020\003¢\006\b\n\000\032\004\b\b\020\tR\021\020\004\032\0020\003¢\006\b\n\000\032\004\b\n\020\tR\021\020\006\032\0020\003¢\006\b\n\000\032\004\b\013\020\tR\016\020\f\032\0020\003X\004¢\006\002\n\000¨\006\030"}, d2={"Lkotlin/KotlinVersion;", "", "major", "", "minor", "(II)V", "patch", "(III)V", "getMajor", "()I", "getMinor", "getPatch", "version", "compareTo", "other", "equals", "", "", "hashCode", "isAtLeast", "toString", "", "versionOf", "Companion", "kotlin-stdlib"}, k=1, mv={1, 7, 1}, xi=48)
public final class KotlinVersion
  implements Comparable<KotlinVersion>
{
  public static final KotlinVersion CURRENT = KotlinVersionCurrentValue.get();
  public static final Companion Companion = new Companion(null);
  public static final int MAX_COMPONENT_VALUE = 255;
  private final int major;
  private final int minor;
  private final int patch;
  private final int version;
  
  public KotlinVersion(int paramInt1, int paramInt2)
  {
    this(paramInt1, paramInt2, 0);
  }
  
  public KotlinVersion(int paramInt1, int paramInt2, int paramInt3)
  {
    this.major = paramInt1;
    this.minor = paramInt2;
    this.patch = paramInt3;
    this.version = versionOf(paramInt1, paramInt2, paramInt3);
  }
  
  private final int versionOf(int paramInt1, int paramInt2, int paramInt3)
  {
    int j = 0;
    int i = j;
    if (new IntRange(0, 255).contains(paramInt1))
    {
      i = j;
      if (new IntRange(0, 255).contains(paramInt2))
      {
        i = j;
        if (new IntRange(0, 255).contains(paramInt3)) {
          i = 1;
        }
      }
    }
    if (i != 0) {
      return (paramInt1 << 16) + (paramInt2 << 8) + paramInt3;
    }
    throw new IllegalArgumentException(("Version components are out of range: " + paramInt1 + '.' + paramInt2 + '.' + paramInt3).toString());
  }
  
  public int compareTo(KotlinVersion paramKotlinVersion)
  {
    Intrinsics.checkNotNullParameter(paramKotlinVersion, "other");
    return this.version - paramKotlinVersion.version;
  }
  
  public boolean equals(Object paramObject)
  {
    boolean bool = true;
    if (this == paramObject) {
      return true;
    }
    if ((paramObject instanceof KotlinVersion)) {
      paramObject = (KotlinVersion)paramObject;
    } else {
      paramObject = null;
    }
    if (paramObject == null) {
      return false;
    }
    if (this.version != ((KotlinVersion)paramObject).version) {
      bool = false;
    }
    return bool;
  }
  
  public final int getMajor()
  {
    return this.major;
  }
  
  public final int getMinor()
  {
    return this.minor;
  }
  
  public final int getPatch()
  {
    return this.patch;
  }
  
  public int hashCode()
  {
    return this.version;
  }
  
  public final boolean isAtLeast(int paramInt1, int paramInt2)
  {
    int i = this.major;
    boolean bool;
    if ((i <= paramInt1) && ((i != paramInt1) || (this.minor < paramInt2))) {
      bool = false;
    } else {
      bool = true;
    }
    return bool;
  }
  
  public final boolean isAtLeast(int paramInt1, int paramInt2, int paramInt3)
  {
    int i = this.major;
    if (i <= paramInt1) {
      if (i == paramInt1)
      {
        paramInt1 = this.minor;
        if ((paramInt1 > paramInt2) || ((paramInt1 == paramInt2) && (this.patch >= paramInt3))) {}
      }
      else
      {
        bool = false;
        break label53;
      }
    }
    boolean bool = true;
    label53:
    return bool;
  }
  
  public String toString()
  {
    return this.major + '.' + this.minor + '.' + this.patch;
  }
  
  @Metadata(d1={"\000\030\n\002\030\002\n\002\020\000\n\002\b\002\n\002\030\002\n\000\n\002\020\b\n\000\b\003\030\0002\0020\001B\007\b\002¢\006\002\020\002R\020\020\003\032\0020\0048\006X\004¢\006\002\n\000R\016\020\005\032\0020\006XT¢\006\002\n\000¨\006\007"}, d2={"Lkotlin/KotlinVersion$Companion;", "", "()V", "CURRENT", "Lkotlin/KotlinVersion;", "MAX_COMPONENT_VALUE", "", "kotlin-stdlib"}, k=1, mv={1, 7, 1}, xi=48)
  public static final class Companion {}
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlin/KotlinVersion.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */