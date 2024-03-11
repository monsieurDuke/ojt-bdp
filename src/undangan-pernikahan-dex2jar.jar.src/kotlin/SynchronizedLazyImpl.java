package kotlin;

import java.io.Serializable;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import mt.Log229316;
import mt.Log5ECF72;
import mt.LogE84000;

@Metadata(d1={"\0000\n\002\030\002\n\000\n\002\030\002\n\002\030\002\n\002\030\002\n\000\n\002\030\002\n\000\n\002\020\000\n\002\b\006\n\002\020\013\n\000\n\002\020\016\n\002\b\002\b\002\030\000*\006\b\000\020\001 \0012\b\022\004\022\002H\0010\0022\0060\003j\002`\004B\037\022\f\020\005\032\b\022\004\022\0028\0000\006\022\n\b\002\020\007\032\004\030\0010\b¢\006\002\020\tJ\b\020\016\032\0020\017H\026J\b\020\020\032\0020\021H\026J\b\020\022\032\0020\bH\002R\020\020\n\032\004\030\0010\bX\016¢\006\002\n\000R\026\020\005\032\n\022\004\022\0028\000\030\0010\006X\016¢\006\002\n\000R\016\020\007\032\0020\bX\004¢\006\002\n\000R\024\020\013\032\0028\0008VX\004¢\006\006\032\004\b\f\020\r¨\006\023"}, d2={"Lkotlin/SynchronizedLazyImpl;", "T", "Lkotlin/Lazy;", "Ljava/io/Serializable;", "Lkotlin/io/Serializable;", "initializer", "Lkotlin/Function0;", "lock", "", "(Lkotlin/jvm/functions/Function0;Ljava/lang/Object;)V", "_value", "value", "getValue", "()Ljava/lang/Object;", "isInitialized", "", "toString", "", "writeReplace", "kotlin-stdlib"}, k=1, mv={1, 7, 1}, xi=48)
final class SynchronizedLazyImpl<T>
  implements Lazy<T>, Serializable
{
  private volatile Object _value;
  private Function0<? extends T> initializer;
  private final Object lock;
  
  public SynchronizedLazyImpl(Function0<? extends T> paramFunction0, Object paramObject)
  {
    this.initializer = paramFunction0;
    this._value = UNINITIALIZED_VALUE.INSTANCE;
    if (paramObject == null) {
      paramFunction0 = this;
    } else {
      paramFunction0 = (Function0<? extends T>)paramObject;
    }
    this.lock = paramFunction0;
  }
  
  private final Object writeReplace()
  {
    return new InitializedLazyImpl(getValue());
  }
  
  public T getValue()
  {
    Object localObject1 = this._value;
    if (localObject1 != UNINITIALIZED_VALUE.INSTANCE) {
      return (T)localObject1;
    }
    synchronized (this.lock)
    {
      localObject1 = this._value;
      if (localObject1 == UNINITIALIZED_VALUE.INSTANCE)
      {
        localObject1 = this.initializer;
        Intrinsics.checkNotNull(localObject1);
        localObject1 = ((Function0)localObject1).invoke();
        this._value = localObject1;
        this.initializer = null;
      }
      return (T)localObject1;
    }
  }
  
  public boolean isInitialized()
  {
    boolean bool;
    if (this._value != UNINITIALIZED_VALUE.INSTANCE) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public String toString()
  {
    String str;
    if (isInitialized())
    {
      str = String.valueOf(getValue());
      Log5ECF72.a(str);
      LogE84000.a(str);
      Log229316.a(str);
    }
    else
    {
      str = "Lazy value not initialized yet.";
    }
    return str;
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlin/SynchronizedLazyImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */