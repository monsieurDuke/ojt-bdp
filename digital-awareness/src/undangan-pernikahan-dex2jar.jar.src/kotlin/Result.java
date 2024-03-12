package kotlin;

import java.io.Serializable;
import kotlin.jvm.JvmInline;
import kotlin.jvm.internal.Intrinsics;
import mt.Log229316;
import mt.Log5ECF72;
import mt.LogE84000;

@Metadata(d1={"\0008\n\002\030\002\n\000\n\002\030\002\n\002\030\002\n\000\n\002\020\000\n\002\b\003\n\002\020\013\n\002\b\013\n\002\020\003\n\002\b\005\n\002\020\b\n\002\b\003\n\002\020\016\n\002\b\005\b@\030\000 \"*\006\b\000\020\001 \0012\0060\002j\002`\003:\002\"#B\026\b\001\022\b\020\004\032\004\030\0010\005ø\001\000¢\006\004\b\006\020\007J\032\020\020\032\0020\t2\b\020\021\032\004\030\0010\005HÖ\003¢\006\004\b\022\020\023J\017\020\024\032\004\030\0010\025¢\006\004\b\026\020\027J\022\020\030\032\004\030\0018\000H\b¢\006\004\b\031\020\007J\020\020\032\032\0020\033HÖ\001¢\006\004\b\034\020\035J\017\020\036\032\0020\037H\026¢\006\004\b \020!R\021\020\b\032\0020\t8F¢\006\006\032\004\b\n\020\013R\021\020\f\032\0020\t8F¢\006\006\032\004\b\r\020\013R\030\020\004\032\004\030\0010\0058\000X\004¢\006\b\n\000\022\004\b\016\020\017\001\004\001\004\030\0010\005ø\001\000\002\004\n\002\b\031¨\006$"}, d2={"Lkotlin/Result;", "T", "Ljava/io/Serializable;", "Lkotlin/io/Serializable;", "value", "", "constructor-impl", "(Ljava/lang/Object;)Ljava/lang/Object;", "isFailure", "", "isFailure-impl", "(Ljava/lang/Object;)Z", "isSuccess", "isSuccess-impl", "getValue$annotations", "()V", "equals", "other", "equals-impl", "(Ljava/lang/Object;Ljava/lang/Object;)Z", "exceptionOrNull", "", "exceptionOrNull-impl", "(Ljava/lang/Object;)Ljava/lang/Throwable;", "getOrNull", "getOrNull-impl", "hashCode", "", "hashCode-impl", "(Ljava/lang/Object;)I", "toString", "", "toString-impl", "(Ljava/lang/Object;)Ljava/lang/String;", "Companion", "Failure", "kotlin-stdlib"}, k=1, mv={1, 7, 1}, xi=48)
@JvmInline
public final class Result<T>
  implements Serializable
{
  public static final Companion Companion = new Companion(null);
  private final Object value;
  
  public static <T> Object constructor-impl(Object paramObject)
  {
    return paramObject;
  }
  
  public static boolean equals-impl(Object paramObject1, Object paramObject2)
  {
    if (!(paramObject2 instanceof Result)) {
      return false;
    }
    return Intrinsics.areEqual(paramObject1, ((Result)paramObject2).unbox-impl());
  }
  
  public static final boolean equals-impl0(Object paramObject1, Object paramObject2)
  {
    return Intrinsics.areEqual(paramObject1, paramObject2);
  }
  
  public static final Throwable exceptionOrNull-impl(Object paramObject)
  {
    if ((paramObject instanceof Failure)) {
      paramObject = ((Failure)paramObject).exception;
    } else {
      paramObject = null;
    }
    return (Throwable)paramObject;
  }
  
  private static final T getOrNull-impl(Object paramObject)
  {
    if (isFailure-impl(paramObject)) {
      paramObject = null;
    }
    return (T)paramObject;
  }
  
  public static int hashCode-impl(Object paramObject)
  {
    int i;
    if (paramObject == null) {
      i = 0;
    } else {
      i = paramObject.hashCode();
    }
    return i;
  }
  
  public static final boolean isFailure-impl(Object paramObject)
  {
    return paramObject instanceof Failure;
  }
  
  public static final boolean isSuccess-impl(Object paramObject)
  {
    return paramObject instanceof Failure ^ true;
  }
  
  public static String toString-impl(Object paramObject)
  {
    if ((paramObject instanceof Failure)) {
      paramObject = ((Failure)paramObject).toString();
    } else {
      paramObject = "Success(" + paramObject + ')';
    }
    return (String)paramObject;
  }
  
  public boolean equals(Object paramObject)
  {
    return equals-impl(this.value, paramObject);
  }
  
  public int hashCode()
  {
    return hashCode-impl(this.value);
  }
  
  public String toString()
  {
    String str = toString-impl(this.value);
    Log5ECF72.a(str);
    LogE84000.a(str);
    Log229316.a(str);
    return str;
  }
  
  @Metadata(d1={"\000\034\n\002\030\002\n\002\020\000\n\002\b\002\n\002\030\002\n\002\b\002\n\002\020\003\n\002\b\005\b\003\030\0002\0020\001B\007\b\002¢\006\002\020\002J%\020\003\032\b\022\004\022\002H\0050\004\"\004\b\001\020\0052\006\020\006\032\0020\007H\bø\001\000¢\006\002\020\bJ%\020\t\032\b\022\004\022\002H\0050\004\"\004\b\001\020\0052\006\020\n\032\002H\005H\bø\001\000¢\006\002\020\013\002\004\n\002\b\031¨\006\f"}, d2={"Lkotlin/Result$Companion;", "", "()V", "failure", "Lkotlin/Result;", "T", "exception", "", "(Ljava/lang/Throwable;)Ljava/lang/Object;", "success", "value", "(Ljava/lang/Object;)Ljava/lang/Object;", "kotlin-stdlib"}, k=1, mv={1, 7, 1}, xi=48)
  public static final class Companion
  {
    private final <T> Object failure(Throwable paramThrowable)
    {
      Intrinsics.checkNotNullParameter(paramThrowable, "exception");
      return Result.constructor-impl(ResultKt.createFailure(paramThrowable));
    }
    
    private final <T> Object success(T paramT)
    {
      return Result.constructor-impl(paramT);
    }
  }
  
  @Metadata(d1={"\000.\n\002\030\002\n\002\030\002\n\002\030\002\n\000\n\002\020\003\n\002\b\002\n\002\020\013\n\000\n\002\020\000\n\000\n\002\020\b\n\000\n\002\020\016\n\000\b\000\030\0002\0060\001j\002`\002B\r\022\006\020\003\032\0020\004¢\006\002\020\005J\023\020\006\032\0020\0072\b\020\b\032\004\030\0010\tH\002J\b\020\n\032\0020\013H\026J\b\020\f\032\0020\rH\026R\020\020\003\032\0020\0048\006X\004¢\006\002\n\000¨\006\016"}, d2={"Lkotlin/Result$Failure;", "Ljava/io/Serializable;", "Lkotlin/io/Serializable;", "exception", "", "(Ljava/lang/Throwable;)V", "equals", "", "other", "", "hashCode", "", "toString", "", "kotlin-stdlib"}, k=1, mv={1, 7, 1}, xi=48)
  public static final class Failure
    implements Serializable
  {
    public final Throwable exception;
    
    public Failure(Throwable paramThrowable)
    {
      this.exception = paramThrowable;
    }
    
    public boolean equals(Object paramObject)
    {
      boolean bool;
      if (((paramObject instanceof Failure)) && (Intrinsics.areEqual(this.exception, ((Failure)paramObject).exception))) {
        bool = true;
      } else {
        bool = false;
      }
      return bool;
    }
    
    public int hashCode()
    {
      return this.exception.hashCode();
    }
    
    public String toString()
    {
      return "Failure(" + this.exception + ')';
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlin/Result.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */