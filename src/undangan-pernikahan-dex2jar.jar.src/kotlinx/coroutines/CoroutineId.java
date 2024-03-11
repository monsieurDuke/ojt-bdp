package kotlinx.coroutines;

import kotlin.Metadata;
import kotlin.coroutines.AbstractCoroutineContextElement;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.CoroutineContext.Key;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;

@Metadata(d1={"\000:\n\002\030\002\n\002\030\002\n\002\020\016\n\002\030\002\n\000\n\002\020\t\n\002\b\006\n\002\020\013\n\000\n\002\020\000\n\000\n\002\020\b\n\000\n\002\020\002\n\000\n\002\030\002\n\002\b\005\b\b\030\000 \0302\b\022\004\022\0020\0020\0012\0020\003:\001\030B\r\022\006\020\004\032\0020\005¢\006\002\020\006J\t\020\t\032\0020\005HÆ\003J\023\020\n\032\0020\0002\b\b\002\020\004\032\0020\005HÆ\001J\023\020\013\032\0020\f2\b\020\r\032\004\030\0010\016HÖ\003J\t\020\017\032\0020\020HÖ\001J\030\020\021\032\0020\0222\006\020\023\032\0020\0242\006\020\025\032\0020\002H\026J\b\020\026\032\0020\002H\026J\020\020\027\032\0020\0022\006\020\023\032\0020\024H\026R\021\020\004\032\0020\005¢\006\b\n\000\032\004\b\007\020\b¨\006\031"}, d2={"Lkotlinx/coroutines/CoroutineId;", "Lkotlinx/coroutines/ThreadContextElement;", "", "Lkotlin/coroutines/AbstractCoroutineContextElement;", "id", "", "(J)V", "getId", "()J", "component1", "copy", "equals", "", "other", "", "hashCode", "", "restoreThreadContext", "", "context", "Lkotlin/coroutines/CoroutineContext;", "oldState", "toString", "updateThreadContext", "Key", "kotlinx-coroutines-core"}, k=1, mv={1, 6, 0}, xi=48)
public final class CoroutineId
  extends AbstractCoroutineContextElement
  implements ThreadContextElement<String>
{
  public static final Key Key = new Key(null);
  private final long id;
  
  public CoroutineId(long paramLong)
  {
    super((CoroutineContext.Key)Key);
    this.id = paramLong;
  }
  
  public final long component1()
  {
    return this.id;
  }
  
  public final CoroutineId copy(long paramLong)
  {
    return new CoroutineId(paramLong);
  }
  
  public boolean equals(Object paramObject)
  {
    if (this == paramObject) {
      return true;
    }
    if (!(paramObject instanceof CoroutineId)) {
      return false;
    }
    paramObject = (CoroutineId)paramObject;
    return this.id == ((CoroutineId)paramObject).id;
  }
  
  public final long getId()
  {
    return this.id;
  }
  
  public int hashCode()
  {
    return Long.hashCode(this.id);
  }
  
  public void restoreThreadContext(CoroutineContext paramCoroutineContext, String paramString)
  {
    Thread.currentThread().setName(paramString);
  }
  
  public String toString()
  {
    return "CoroutineId(" + this.id + ')';
  }
  
  public String updateThreadContext(CoroutineContext paramCoroutineContext)
  {
    Object localObject = (CoroutineName)paramCoroutineContext.get((CoroutineContext.Key)CoroutineName.Key);
    paramCoroutineContext = "coroutine";
    if (localObject != null)
    {
      do
      {
        localObject = ((CoroutineName)localObject).getName();
      } while (localObject == null);
      paramCoroutineContext = (CoroutineContext)localObject;
    }
    Thread localThread = Thread.currentThread();
    localObject = localThread.getName();
    int j = StringsKt.lastIndexOf$default((CharSequence)localObject, " @", 0, false, 6, null);
    int i = j;
    if (j < 0) {
      i = ((String)localObject).length();
    }
    StringBuilder localStringBuilder = new StringBuilder(paramCoroutineContext.length() + i + 10);
    String str = ((String)localObject).substring(0, i);
    Intrinsics.checkNotNullExpressionValue(str, "this as java.lang.String…ing(startIndex, endIndex)");
    localStringBuilder.append(str);
    localStringBuilder.append(" @");
    localStringBuilder.append(paramCoroutineContext);
    localStringBuilder.append('#');
    localStringBuilder.append(getId());
    paramCoroutineContext = localStringBuilder.toString();
    Intrinsics.checkNotNullExpressionValue(paramCoroutineContext, "StringBuilder(capacity).…builderAction).toString()");
    localThread.setName(paramCoroutineContext);
    return (String)localObject;
  }
  
  @Metadata(d1={"\000\020\n\002\030\002\n\002\030\002\n\002\030\002\n\002\b\002\b\003\030\0002\b\022\004\022\0020\0020\001B\007\b\002¢\006\002\020\003¨\006\004"}, d2={"Lkotlinx/coroutines/CoroutineId$Key;", "Lkotlin/coroutines/CoroutineContext$Key;", "Lkotlinx/coroutines/CoroutineId;", "()V", "kotlinx-coroutines-core"}, k=1, mv={1, 6, 0}, xi=48)
  public static final class Key
    implements CoroutineContext.Key<CoroutineId>
  {}
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlinx/coroutines/CoroutineId.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */