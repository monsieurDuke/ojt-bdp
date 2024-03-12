package kotlinx.coroutines;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

@Metadata(d1={"\0008\n\002\030\002\n\002\020\000\n\002\b\002\n\002\030\002\n\002\020\003\n\002\030\002\n\002\b\002\n\002\020\002\n\002\b\005\n\002\020\013\n\002\b\002\n\002\020\b\n\000\n\002\020\016\n\000\b\b\030\0002\0020\001B2\022\b\020\002\032\004\030\0010\001\022!\020\003\032\035\022\023\022\0210\005¢\006\f\b\006\022\b\b\007\022\004\b\b(\b\022\004\022\0020\t0\004¢\006\002\020\nJ\013\020\013\032\004\030\0010\001HÆ\003J$\020\f\032\035\022\023\022\0210\005¢\006\f\b\006\022\b\b\007\022\004\b\b(\b\022\004\022\0020\t0\004HÆ\003J:\020\r\032\0020\0002\n\b\002\020\002\032\004\030\0010\0012#\b\002\020\003\032\035\022\023\022\0210\005¢\006\f\b\006\022\b\b\007\022\004\b\b(\b\022\004\022\0020\t0\004HÆ\001J\023\020\016\032\0020\0172\b\020\020\032\004\030\0010\001HÖ\003J\t\020\021\032\0020\022HÖ\001J\t\020\023\032\0020\024HÖ\001R+\020\003\032\035\022\023\022\0210\005¢\006\f\b\006\022\b\b\007\022\004\b\b(\b\022\004\022\0020\t0\0048\006X\004¢\006\002\n\000R\022\020\002\032\004\030\0010\0018\006X\004¢\006\002\n\000¨\006\025"}, d2={"Lkotlinx/coroutines/CompletedWithCancellation;", "", "result", "onCancellation", "Lkotlin/Function1;", "", "Lkotlin/ParameterName;", "name", "cause", "", "(Ljava/lang/Object;Lkotlin/jvm/functions/Function1;)V", "component1", "component2", "copy", "equals", "", "other", "hashCode", "", "toString", "", "kotlinx-coroutines-core"}, k=1, mv={1, 6, 0}, xi=48)
public final class CompletedWithCancellation
{
  public final Function1<Throwable, Unit> onCancellation;
  public final Object result;
  
  public CompletedWithCancellation(Object paramObject, Function1<? super Throwable, Unit> paramFunction1)
  {
    this.result = paramObject;
    this.onCancellation = paramFunction1;
  }
  
  public final Object component1()
  {
    return this.result;
  }
  
  public final Function1<Throwable, Unit> component2()
  {
    return this.onCancellation;
  }
  
  public final CompletedWithCancellation copy(Object paramObject, Function1<? super Throwable, Unit> paramFunction1)
  {
    return new CompletedWithCancellation(paramObject, paramFunction1);
  }
  
  public boolean equals(Object paramObject)
  {
    if (this == paramObject) {
      return true;
    }
    if (!(paramObject instanceof CompletedWithCancellation)) {
      return false;
    }
    paramObject = (CompletedWithCancellation)paramObject;
    if (!Intrinsics.areEqual(this.result, ((CompletedWithCancellation)paramObject).result)) {
      return false;
    }
    return Intrinsics.areEqual(this.onCancellation, ((CompletedWithCancellation)paramObject).onCancellation);
  }
  
  public int hashCode()
  {
    Object localObject = this.result;
    int i;
    if (localObject == null) {
      i = 0;
    } else {
      i = localObject.hashCode();
    }
    return i * 31 + this.onCancellation.hashCode();
  }
  
  public String toString()
  {
    return "CompletedWithCancellation(result=" + this.result + ", onCancellation=" + this.onCancellation + ')';
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlinx/coroutines/CompletedWithCancellation.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */