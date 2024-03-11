package kotlinx.coroutines.channels;

import kotlin.Metadata;
import kotlin.jvm.JvmInline;
import kotlin.jvm.internal.Intrinsics;
import mt.Log229316;
import mt.Log5ECF72;
import mt.LogE84000;

@Metadata(d1={"\000.\n\002\030\002\n\000\n\002\020\000\n\002\b\006\n\002\020\013\n\002\b\013\n\002\020\003\n\002\b\007\n\002\020\b\n\002\b\003\n\002\020\016\n\002\b\006\b@\030\000 %*\006\b\000\020\001 \0012\0020\002:\003$%&B\026\b\001\022\b\020\003\032\004\030\0010\002ø\001\000¢\006\004\b\004\020\005J\032\020\020\032\0020\t2\b\020\021\032\004\030\0010\002HÖ\003¢\006\004\b\022\020\023J\017\020\024\032\004\030\0010\025¢\006\004\b\026\020\027J\017\020\030\032\004\030\0018\000¢\006\004\b\031\020\005J\r\020\032\032\0028\000¢\006\004\b\033\020\005J\020\020\034\032\0020\035HÖ\001¢\006\004\b\036\020\037J\017\020 \032\0020!H\026¢\006\004\b\"\020#R\030\020\003\032\004\030\0010\0028\000X\004¢\006\b\n\000\022\004\b\006\020\007R\021\020\b\032\0020\t8F¢\006\006\032\004\b\n\020\013R\021\020\f\032\0020\t8F¢\006\006\032\004\b\r\020\013R\021\020\016\032\0020\t8F¢\006\006\032\004\b\017\020\013\001\003\001\004\030\0010\002ø\001\000\002\004\n\002\b\031¨\006'"}, d2={"Lkotlinx/coroutines/channels/ChannelResult;", "T", "", "holder", "constructor-impl", "(Ljava/lang/Object;)Ljava/lang/Object;", "getHolder$annotations", "()V", "isClosed", "", "isClosed-impl", "(Ljava/lang/Object;)Z", "isFailure", "isFailure-impl", "isSuccess", "isSuccess-impl", "equals", "other", "equals-impl", "(Ljava/lang/Object;Ljava/lang/Object;)Z", "exceptionOrNull", "", "exceptionOrNull-impl", "(Ljava/lang/Object;)Ljava/lang/Throwable;", "getOrNull", "getOrNull-impl", "getOrThrow", "getOrThrow-impl", "hashCode", "", "hashCode-impl", "(Ljava/lang/Object;)I", "toString", "", "toString-impl", "(Ljava/lang/Object;)Ljava/lang/String;", "Closed", "Companion", "Failed", "kotlinx-coroutines-core"}, k=1, mv={1, 6, 0}, xi=48)
@JvmInline
public final class ChannelResult<T>
{
  public static final Companion Companion = new Companion(null);
  private static final Failed failed = new Failed();
  private final Object holder;
  
  public static <T> Object constructor-impl(Object paramObject)
  {
    return paramObject;
  }
  
  public static boolean equals-impl(Object paramObject1, Object paramObject2)
  {
    if (!(paramObject2 instanceof ChannelResult)) {
      return false;
    }
    return Intrinsics.areEqual(paramObject1, ((ChannelResult)paramObject2).unbox-impl());
  }
  
  public static final boolean equals-impl0(Object paramObject1, Object paramObject2)
  {
    return Intrinsics.areEqual(paramObject1, paramObject2);
  }
  
  public static final Throwable exceptionOrNull-impl(Object paramObject)
  {
    boolean bool = paramObject instanceof Closed;
    Object localObject = null;
    if (bool) {
      paramObject = (Closed)paramObject;
    } else {
      paramObject = null;
    }
    if (paramObject == null) {
      paramObject = localObject;
    } else {
      paramObject = ((Closed)paramObject).cause;
    }
    return (Throwable)paramObject;
  }
  
  public static final T getOrNull-impl(Object paramObject)
  {
    if ((paramObject instanceof Failed)) {
      paramObject = null;
    }
    return (T)paramObject;
  }
  
  public static final T getOrThrow-impl(Object paramObject)
  {
    if (!(paramObject instanceof Failed)) {
      return (T)paramObject;
    }
    if (((paramObject instanceof Closed)) && (((Closed)paramObject).cause != null)) {
      throw ((Closed)paramObject).cause;
    }
    paramObject = Intrinsics.stringPlus("Trying to call 'getOrThrow' on a failed channel result: ", paramObject);
    Log5ECF72.a(paramObject);
    LogE84000.a(paramObject);
    Log229316.a(paramObject);
    throw new IllegalStateException(paramObject.toString());
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
  
  public static final boolean isClosed-impl(Object paramObject)
  {
    return paramObject instanceof Closed;
  }
  
  public static final boolean isFailure-impl(Object paramObject)
  {
    return paramObject instanceof Failed;
  }
  
  public static final boolean isSuccess-impl(Object paramObject)
  {
    return paramObject instanceof Failed ^ true;
  }
  
  public static String toString-impl(Object paramObject)
  {
    if ((paramObject instanceof Closed)) {
      paramObject = ((Closed)paramObject).toString();
    } else {
      paramObject = "Value(" + paramObject + ')';
    }
    return (String)paramObject;
  }
  
  public boolean equals(Object paramObject)
  {
    return equals-impl(this.holder, paramObject);
  }
  
  public int hashCode()
  {
    return hashCode-impl(this.holder);
  }
  
  public String toString()
  {
    String str = toString-impl(this.holder);
    Log5ECF72.a(str);
    LogE84000.a(str);
    Log229316.a(str);
    return str;
  }
  
  @Metadata(d1={"\000*\n\002\030\002\n\002\030\002\n\000\n\002\020\003\n\002\b\002\n\002\020\013\n\000\n\002\020\000\n\000\n\002\020\b\n\000\n\002\020\016\n\000\b\000\030\0002\0020\001B\017\022\b\020\002\032\004\030\0010\003¢\006\002\020\004J\023\020\005\032\0020\0062\b\020\007\032\004\030\0010\bH\002J\b\020\t\032\0020\nH\026J\b\020\013\032\0020\fH\026R\022\020\002\032\004\030\0010\0038\006X\004¢\006\002\n\000¨\006\r"}, d2={"Lkotlinx/coroutines/channels/ChannelResult$Closed;", "Lkotlinx/coroutines/channels/ChannelResult$Failed;", "cause", "", "(Ljava/lang/Throwable;)V", "equals", "", "other", "", "hashCode", "", "toString", "", "kotlinx-coroutines-core"}, k=1, mv={1, 6, 0}, xi=48)
  public static final class Closed
    extends ChannelResult.Failed
  {
    public final Throwable cause;
    
    public Closed(Throwable paramThrowable)
    {
      this.cause = paramThrowable;
    }
    
    public boolean equals(Object paramObject)
    {
      boolean bool;
      if (((paramObject instanceof Closed)) && (Intrinsics.areEqual(this.cause, ((Closed)paramObject).cause))) {
        bool = true;
      } else {
        bool = false;
      }
      return bool;
    }
    
    public int hashCode()
    {
      Throwable localThrowable = this.cause;
      int i;
      if (localThrowable == null) {
        i = 0;
      } else {
        i = localThrowable.hashCode();
      }
      return i;
    }
    
    public String toString()
    {
      return "Closed(" + this.cause + ')';
    }
  }
  
  @Metadata(d1={"\000\"\n\002\030\002\n\002\020\000\n\002\b\002\n\002\030\002\n\000\n\002\030\002\n\002\b\002\n\002\020\003\n\002\b\n\b\003\030\0002\0020\001B\007\b\002¢\006\002\020\002J.\020\005\032\b\022\004\022\002H\0070\006\"\004\b\001\020\0072\b\020\b\032\004\030\0010\tH\007ø\001\000ø\001\001ø\001\002¢\006\004\b\n\020\013J$\020\f\032\b\022\004\022\002H\0070\006\"\004\b\001\020\007H\007ø\001\000ø\001\001ø\001\002¢\006\004\b\r\020\016J,\020\017\032\b\022\004\022\002H\0070\006\"\004\b\001\020\0072\006\020\020\032\002H\007H\007ø\001\000ø\001\001ø\001\002¢\006\004\b\021\020\022R\016\020\003\032\0020\004X\004¢\006\002\n\000\002\017\n\002\b\031\n\002\b!\n\005\b¡\0360\001¨\006\023"}, d2={"Lkotlinx/coroutines/channels/ChannelResult$Companion;", "", "()V", "failed", "Lkotlinx/coroutines/channels/ChannelResult$Failed;", "closed", "Lkotlinx/coroutines/channels/ChannelResult;", "E", "cause", "", "closed-JP2dKIU", "(Ljava/lang/Throwable;)Ljava/lang/Object;", "failure", "failure-PtdJZtk", "()Ljava/lang/Object;", "success", "value", "success-JP2dKIU", "(Ljava/lang/Object;)Ljava/lang/Object;", "kotlinx-coroutines-core"}, k=1, mv={1, 6, 0}, xi=48)
  public static final class Companion
  {
    public final <E> Object closed-JP2dKIU(Throwable paramThrowable)
    {
      return ChannelResult.constructor-impl(new ChannelResult.Closed(paramThrowable));
    }
    
    public final <E> Object failure-PtdJZtk()
    {
      return ChannelResult.constructor-impl(ChannelResult.access$getFailed$cp());
    }
    
    public final <E> Object success-JP2dKIU(E paramE)
    {
      return ChannelResult.constructor-impl(paramE);
    }
  }
  
  @Metadata(d1={"\000\022\n\002\030\002\n\002\020\000\n\002\b\002\n\002\020\016\n\000\b\020\030\0002\0020\001B\005¢\006\002\020\002J\b\020\003\032\0020\004H\026¨\006\005"}, d2={"Lkotlinx/coroutines/channels/ChannelResult$Failed;", "", "()V", "toString", "", "kotlinx-coroutines-core"}, k=1, mv={1, 6, 0}, xi=48)
  public static class Failed
  {
    public String toString()
    {
      return "Failed";
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlinx/coroutines/channels/ChannelResult.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */