package kotlin.reflect;

import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.Intrinsics;
import mt.Log229316;
import mt.Log5ECF72;
import mt.LogE84000;

@Metadata(d1={"\000.\n\002\030\002\n\002\020\000\n\000\n\002\030\002\n\000\n\002\030\002\n\002\b\t\n\002\020\013\n\002\b\002\n\002\020\b\n\000\n\002\020\016\n\002\b\002\b\b\030\000 \0252\0020\001:\001\025B\031\022\b\020\002\032\004\030\0010\003\022\b\020\004\032\004\030\0010\005¢\006\002\020\006J\013\020\013\032\004\030\0010\003HÆ\003J\013\020\f\032\004\030\0010\005HÆ\003J!\020\r\032\0020\0002\n\b\002\020\002\032\004\030\0010\0032\n\b\002\020\004\032\004\030\0010\005HÆ\001J\023\020\016\032\0020\0172\b\020\020\032\004\030\0010\001HÖ\003J\t\020\021\032\0020\022HÖ\001J\b\020\023\032\0020\024H\026R\023\020\004\032\004\030\0010\005¢\006\b\n\000\032\004\b\007\020\bR\023\020\002\032\004\030\0010\003¢\006\b\n\000\032\004\b\t\020\n¨\006\026"}, d2={"Lkotlin/reflect/KTypeProjection;", "", "variance", "Lkotlin/reflect/KVariance;", "type", "Lkotlin/reflect/KType;", "(Lkotlin/reflect/KVariance;Lkotlin/reflect/KType;)V", "getType", "()Lkotlin/reflect/KType;", "getVariance", "()Lkotlin/reflect/KVariance;", "component1", "component2", "copy", "equals", "", "other", "hashCode", "", "toString", "", "Companion", "kotlin-stdlib"}, k=1, mv={1, 7, 1}, xi=48)
public final class KTypeProjection
{
  public static final Companion Companion = new Companion(null);
  public static final KTypeProjection star = new KTypeProjection(null, null);
  private final KType type;
  private final KVariance variance;
  
  public KTypeProjection(KVariance paramKVariance, KType paramKType)
  {
    this.variance = paramKVariance;
    this.type = paramKType;
    int k = 1;
    int i;
    if (paramKVariance == null) {
      i = 1;
    } else {
      i = 0;
    }
    int j;
    if (paramKType == null) {
      j = 1;
    } else {
      j = 0;
    }
    if (i == j) {
      i = k;
    } else {
      i = 0;
    }
    if (i == 0)
    {
      if (paramKVariance == null) {
        paramKVariance = "Star projection must have no type specified.";
      } else {
        paramKVariance = "The projection variance " + paramKVariance + " requires type to be specified.";
      }
      throw new IllegalArgumentException(paramKVariance.toString());
    }
  }
  
  @JvmStatic
  public static final KTypeProjection contravariant(KType paramKType)
  {
    return Companion.contravariant(paramKType);
  }
  
  @JvmStatic
  public static final KTypeProjection covariant(KType paramKType)
  {
    return Companion.covariant(paramKType);
  }
  
  @JvmStatic
  public static final KTypeProjection invariant(KType paramKType)
  {
    return Companion.invariant(paramKType);
  }
  
  public final KVariance component1()
  {
    return this.variance;
  }
  
  public final KType component2()
  {
    return this.type;
  }
  
  public final KTypeProjection copy(KVariance paramKVariance, KType paramKType)
  {
    return new KTypeProjection(paramKVariance, paramKType);
  }
  
  public boolean equals(Object paramObject)
  {
    if (this == paramObject) {
      return true;
    }
    if (!(paramObject instanceof KTypeProjection)) {
      return false;
    }
    paramObject = (KTypeProjection)paramObject;
    if (this.variance != ((KTypeProjection)paramObject).variance) {
      return false;
    }
    return Intrinsics.areEqual(this.type, ((KTypeProjection)paramObject).type);
  }
  
  public final KType getType()
  {
    return this.type;
  }
  
  public final KVariance getVariance()
  {
    return this.variance;
  }
  
  public int hashCode()
  {
    Object localObject = this.variance;
    int j = 0;
    int i;
    if (localObject == null) {
      i = 0;
    } else {
      i = ((KVariance)localObject).hashCode();
    }
    localObject = this.type;
    if (localObject != null) {
      j = localObject.hashCode();
    }
    return i * 31 + j;
  }
  
  public String toString()
  {
    Object localObject = this.variance;
    int i;
    if (localObject == null) {
      i = -1;
    } else {
      i = WhenMappings.$EnumSwitchMapping$0[localObject.ordinal()];
    }
    switch (i)
    {
    case 0: 
    default: 
      throw new NoWhenBranchMatchedException();
    case 3: 
      localObject = "out " + this.type;
      break;
    case 2: 
      localObject = "in " + this.type;
      break;
    case 1: 
      localObject = String.valueOf(this.type);
      Log5ECF72.a(localObject);
      LogE84000.a(localObject);
      Log229316.a(localObject);
      break;
    case -1: 
      localObject = "*";
    }
    return (String)localObject;
  }
  
  @Metadata(d1={"\000\034\n\002\030\002\n\002\020\000\n\002\b\002\n\002\030\002\n\002\b\006\n\002\030\002\n\002\b\003\b\003\030\0002\0020\001B\007\b\002¢\006\002\020\002J\020\020\t\032\0020\0042\006\020\n\032\0020\013H\007J\020\020\f\032\0020\0042\006\020\n\032\0020\013H\007J\020\020\r\032\0020\0042\006\020\n\032\0020\013H\007R\021\020\003\032\0020\0048F¢\006\006\032\004\b\005\020\006R\026\020\007\032\0020\0048\000X\004¢\006\b\n\000\022\004\b\b\020\002¨\006\016"}, d2={"Lkotlin/reflect/KTypeProjection$Companion;", "", "()V", "STAR", "Lkotlin/reflect/KTypeProjection;", "getSTAR", "()Lkotlin/reflect/KTypeProjection;", "star", "getStar$annotations", "contravariant", "type", "Lkotlin/reflect/KType;", "covariant", "invariant", "kotlin-stdlib"}, k=1, mv={1, 7, 1}, xi=48)
  public static final class Companion
  {
    @JvmStatic
    public final KTypeProjection contravariant(KType paramKType)
    {
      Intrinsics.checkNotNullParameter(paramKType, "type");
      return new KTypeProjection(KVariance.IN, paramKType);
    }
    
    @JvmStatic
    public final KTypeProjection covariant(KType paramKType)
    {
      Intrinsics.checkNotNullParameter(paramKType, "type");
      return new KTypeProjection(KVariance.OUT, paramKType);
    }
    
    public final KTypeProjection getSTAR()
    {
      return KTypeProjection.star;
    }
    
    @JvmStatic
    public final KTypeProjection invariant(KType paramKType)
    {
      Intrinsics.checkNotNullParameter(paramKType, "type");
      return new KTypeProjection(KVariance.INVARIANT, paramKType);
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlin/reflect/KTypeProjection.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */