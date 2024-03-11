package kotlin.properties;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.KProperty;

@Metadata(d1={"\0002\n\002\030\002\n\002\020\000\n\002\b\002\n\002\030\002\n\002\b\004\n\002\030\002\n\002\030\002\n\002\030\002\n\002\b\004\n\002\020\002\n\002\b\002\n\002\020\013\n\000\bÆ\002\030\0002\0020\001B\007\b\002¢\006\002\020\002J\036\020\003\032\020\022\006\022\004\030\0010\001\022\004\022\002H\0050\004\"\b\b\000\020\005*\0020\001J\001\020\006\032\020\022\006\022\004\030\0010\001\022\004\022\002H\0050\004\"\004\b\000\020\0052\006\020\007\032\002H\0052Q\b\004\020\b\032K\022\027\022\025\022\002\b\0030\n¢\006\f\b\013\022\b\b\f\022\004\b\b(\r\022\023\022\021H\005¢\006\f\b\013\022\b\b\f\022\004\b\b(\016\022\023\022\021H\005¢\006\f\b\013\022\b\b\f\022\004\b\b(\017\022\004\022\0020\0200\tH\bø\001\000¢\006\002\020\021J\001\020\022\032\020\022\006\022\004\030\0010\001\022\004\022\002H\0050\004\"\004\b\000\020\0052\006\020\007\032\002H\0052Q\b\004\020\b\032K\022\027\022\025\022\002\b\0030\n¢\006\f\b\013\022\b\b\f\022\004\b\b(\r\022\023\022\021H\005¢\006\f\b\013\022\b\b\f\022\004\b\b(\016\022\023\022\021H\005¢\006\f\b\013\022\b\b\f\022\004\b\b(\017\022\004\022\0020\0230\tH\bø\001\000¢\006\002\020\021\002\007\n\005\b20\001¨\006\024"}, d2={"Lkotlin/properties/Delegates;", "", "()V", "notNull", "Lkotlin/properties/ReadWriteProperty;", "T", "observable", "initialValue", "onChange", "Lkotlin/Function3;", "Lkotlin/reflect/KProperty;", "Lkotlin/ParameterName;", "name", "property", "oldValue", "newValue", "", "(Ljava/lang/Object;Lkotlin/jvm/functions/Function3;)Lkotlin/properties/ReadWriteProperty;", "vetoable", "", "kotlin-stdlib"}, k=1, mv={1, 7, 1}, xi=48)
public final class Delegates
{
  public static final Delegates INSTANCE = new Delegates();
  
  public final <T> ReadWriteProperty<Object, T> notNull()
  {
    return (ReadWriteProperty)new NotNullVar();
  }
  
  public final <T> ReadWriteProperty<Object, T> observable(T paramT, final Function3<? super KProperty<?>, ? super T, ? super T, Unit> paramFunction3)
  {
    Intrinsics.checkNotNullParameter(paramFunction3, "onChange");
    (ReadWriteProperty)new ObservableProperty(paramT)
    {
      protected void afterChange(KProperty<?> paramAnonymousKProperty, T paramAnonymousT1, T paramAnonymousT2)
      {
        Intrinsics.checkNotNullParameter(paramAnonymousKProperty, "property");
        paramFunction3.invoke(paramAnonymousKProperty, paramAnonymousT1, paramAnonymousT2);
      }
    };
  }
  
  public final <T> ReadWriteProperty<Object, T> vetoable(T paramT, final Function3<? super KProperty<?>, ? super T, ? super T, Boolean> paramFunction3)
  {
    Intrinsics.checkNotNullParameter(paramFunction3, "onChange");
    (ReadWriteProperty)new ObservableProperty(paramT)
    {
      protected boolean beforeChange(KProperty<?> paramAnonymousKProperty, T paramAnonymousT1, T paramAnonymousT2)
      {
        Intrinsics.checkNotNullParameter(paramAnonymousKProperty, "property");
        return ((Boolean)paramFunction3.invoke(paramAnonymousKProperty, paramAnonymousT1, paramAnonymousT2)).booleanValue();
      }
    };
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlin/properties/Delegates.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */