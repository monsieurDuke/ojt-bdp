package kotlinx.coroutines.internal;

import java.lang.reflect.Constructor;
import java.lang.reflect.Constructor<*>;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.Result;
import kotlin.Result.Companion;
import kotlin.ResultKt;
import kotlin.collections.ArraysKt;
import kotlin.comparisons.ComparisonsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;

@Metadata(d1={"\000.\n\000\n\002\030\002\n\000\n\002\020\b\n\000\n\002\030\002\n\002\020\003\n\002\030\002\n\002\b\002\n\002\030\002\n\002\b\002\n\002\030\002\n\002\b\013\0322\020\004\032\024\022\004\022\0020\006\022\006\022\004\030\0010\0060\005j\002`\007\"\b\b\000\020\b*\0020\0062\f\020\t\032\b\022\004\022\002H\b0\nH\002\032*\020\013\032\030\022\004\022\0020\006\022\006\022\004\030\0010\006\030\0010\005j\004\030\001`\0072\n\020\f\032\006\022\002\b\0030\rH\002\0321\020\016\032\024\022\004\022\0020\006\022\006\022\004\030\0010\0060\005j\002`\0072\024\b\004\020\017\032\016\022\004\022\0020\006\022\004\022\0020\0060\005H\b\032!\020\020\032\004\030\001H\b\"\b\b\000\020\b*\0020\0062\006\020\021\032\002H\bH\000¢\006\002\020\022\032\033\020\023\032\0020\003*\006\022\002\b\0030\n2\b\b\002\020\024\032\0020\003H\020\032\030\020\025\032\0020\003*\006\022\002\b\0030\n2\006\020\026\032\0020\003H\002\"\016\020\000\032\0020\001X\004¢\006\002\n\000\"\016\020\002\032\0020\003X\004¢\006\002\n\000*(\b\002\020\027\"\020\022\004\022\0020\006\022\006\022\004\030\0010\0060\0052\020\022\004\022\0020\006\022\006\022\004\030\0010\0060\005¨\006\030"}, d2={"ctorCache", "Lkotlinx/coroutines/internal/CtorCache;", "throwableFields", "", "createConstructor", "Lkotlin/Function1;", "", "Lkotlinx/coroutines/internal/Ctor;", "E", "clz", "Ljava/lang/Class;", "createSafeConstructor", "constructor", "Ljava/lang/reflect/Constructor;", "safeCtor", "block", "tryCopyException", "exception", "(Ljava/lang/Throwable;)Ljava/lang/Throwable;", "fieldsCount", "accumulator", "fieldsCountOrDefault", "defaultValue", "Ctor", "kotlinx-coroutines-core"}, k=2, mv={1, 6, 0}, xi=48)
public final class ExceptionsConstructorKt
{
  private static final CtorCache ctorCache;
  private static final int throwableFields = fieldsCountOrDefault(Throwable.class, -1);
  
  static
  {
    CtorCache localCtorCache2;
    try
    {
      if (FastServiceLoaderKt.getANDROID_DETECTED()) {
        localCtorCache1 = (CtorCache)WeakMapCtorCache.INSTANCE;
      }
    }
    finally
    {
      CtorCache localCtorCache1;
      localCtorCache2 = (CtorCache)WeakMapCtorCache.INSTANCE;
    }
    ctorCache = localCtorCache2;
  }
  
  private static final <E extends Throwable> Function1<Throwable, Throwable> createConstructor(Class<E> paramClass)
  {
    Function1 localFunction1 = (Function1)createConstructor.nullResult.1.INSTANCE;
    if (throwableFields != fieldsCountOrDefault(paramClass, 0)) {
      return localFunction1;
    }
    paramClass = ArraysKt.sortedWith(paramClass.getConstructors(), (Comparator)new Comparator()
    {
      public final int compare(T paramAnonymousT1, T paramAnonymousT2)
      {
        return ComparisonsKt.compareValues((Comparable)Integer.valueOf(((Constructor)paramAnonymousT2).getParameterTypes().length), (Comparable)Integer.valueOf(((Constructor)paramAnonymousT1).getParameterTypes().length));
      }
    });
    Iterator localIterator = paramClass.iterator();
    while (localIterator.hasNext())
    {
      paramClass = createSafeConstructor((Constructor)localIterator.next());
      if (paramClass != null) {
        return paramClass;
      }
    }
    return localFunction1;
  }
  
  private static final Function1<Throwable, Throwable> createSafeConstructor(Constructor<?> paramConstructor)
  {
    Object localObject2 = paramConstructor.getParameterTypes();
    int i = localObject2.length;
    Object localObject1 = null;
    switch (i)
    {
    default: 
      paramConstructor = (Constructor<?>)localObject1;
      break;
    case 2: 
      if ((Intrinsics.areEqual(localObject2[0], String.class)) && (Intrinsics.areEqual(localObject2[1], Throwable.class))) {
        paramConstructor = (Function1)new Lambda(paramConstructor)
        {
          final Constructor $constructor$inlined;
          
          public final Throwable invoke(Throwable paramAnonymousThrowable)
          {
            try
            {
              Result.Companion localCompanion = Result.Companion;
              paramAnonymousThrowable = this.$constructor$inlined.newInstance(new Object[] { paramAnonymousThrowable.getMessage(), paramAnonymousThrowable });
              if (paramAnonymousThrowable != null)
              {
                paramAnonymousThrowable = Result.constructor-impl((Throwable)paramAnonymousThrowable);
              }
              else
              {
                paramAnonymousThrowable = new java/lang/NullPointerException;
                paramAnonymousThrowable.<init>("null cannot be cast to non-null type kotlin.Throwable");
                throw paramAnonymousThrowable;
              }
            }
            finally
            {
              paramAnonymousThrowable = Result.Companion;
              paramAnonymousThrowable = Result.constructor-impl(ResultKt.createFailure(localThrowable1));
              Throwable localThrowable2 = paramAnonymousThrowable;
              if (Result.isFailure-impl(paramAnonymousThrowable)) {
                localThrowable2 = null;
              }
              return (Throwable)localThrowable2;
            }
          }
        };
      } else {
        paramConstructor = (Constructor<?>)localObject1;
      }
      break;
    case 1: 
      localObject2 = localObject2[0];
      if (Intrinsics.areEqual(localObject2, Throwable.class)) {
        paramConstructor = (Function1)new Lambda(paramConstructor)
        {
          final Constructor $constructor$inlined;
          
          public final Throwable invoke(Throwable paramAnonymousThrowable)
          {
            try
            {
              Result.Companion localCompanion = Result.Companion;
              paramAnonymousThrowable = this.$constructor$inlined.newInstance(new Object[] { paramAnonymousThrowable });
              if (paramAnonymousThrowable != null)
              {
                paramAnonymousThrowable = Result.constructor-impl((Throwable)paramAnonymousThrowable);
              }
              else
              {
                paramAnonymousThrowable = new java/lang/NullPointerException;
                paramAnonymousThrowable.<init>("null cannot be cast to non-null type kotlin.Throwable");
                throw paramAnonymousThrowable;
              }
            }
            finally
            {
              paramAnonymousThrowable = Result.Companion;
              paramAnonymousThrowable = Result.constructor-impl(ResultKt.createFailure(localThrowable1));
              Throwable localThrowable2 = paramAnonymousThrowable;
              if (Result.isFailure-impl(paramAnonymousThrowable)) {
                localThrowable2 = null;
              }
              return (Throwable)localThrowable2;
            }
          }
        };
      } else if (Intrinsics.areEqual(localObject2, String.class)) {
        paramConstructor = (Function1)new Lambda(paramConstructor)
        {
          final Constructor $constructor$inlined;
          
          public final Throwable invoke(Throwable paramAnonymousThrowable)
          {
            Object localObject;
            try
            {
              localObject = Result.Companion;
              localObject = this.$constructor$inlined.newInstance(new Object[] { paramAnonymousThrowable.getMessage() });
              if (localObject != null)
              {
                localObject = (Throwable)localObject;
                ((Throwable)localObject).initCause(paramAnonymousThrowable);
                paramAnonymousThrowable = Result.constructor-impl(localObject);
              }
              else
              {
                paramAnonymousThrowable = new java/lang/NullPointerException;
                paramAnonymousThrowable.<init>("null cannot be cast to non-null type kotlin.Throwable");
                throw paramAnonymousThrowable;
              }
            }
            finally
            {
              localObject = Result.Companion;
              paramAnonymousThrowable = Result.constructor-impl(ResultKt.createFailure(paramAnonymousThrowable));
              localObject = paramAnonymousThrowable;
              if (Result.isFailure-impl(paramAnonymousThrowable)) {
                localObject = null;
              }
            }
            return (Throwable)localObject;
          }
        };
      } else {
        paramConstructor = (Constructor<?>)localObject1;
      }
      break;
    case 0: 
      paramConstructor = (Function1)new Lambda(paramConstructor)
      {
        final Constructor $constructor$inlined;
        
        public final Throwable invoke(Throwable paramAnonymousThrowable)
        {
          Object localObject;
          try
          {
            localObject = Result.Companion;
            localObject = this.$constructor$inlined.newInstance(new Object[0]);
            if (localObject != null)
            {
              localObject = (Throwable)localObject;
              ((Throwable)localObject).initCause(paramAnonymousThrowable);
              paramAnonymousThrowable = Result.constructor-impl(localObject);
            }
            else
            {
              paramAnonymousThrowable = new java/lang/NullPointerException;
              paramAnonymousThrowable.<init>("null cannot be cast to non-null type kotlin.Throwable");
              throw paramAnonymousThrowable;
            }
          }
          finally
          {
            localObject = Result.Companion;
            paramAnonymousThrowable = Result.constructor-impl(ResultKt.createFailure(paramAnonymousThrowable));
            localObject = paramAnonymousThrowable;
            if (Result.isFailure-impl(paramAnonymousThrowable)) {
              localObject = null;
            }
          }
          return (Throwable)localObject;
        }
      };
    }
    return paramConstructor;
  }
  
  private static final int fieldsCount(Class<?> paramClass, int paramInt)
  {
    for (;;)
    {
      Field[] arrayOfField = paramClass.getDeclaredFields();
      int j = 0;
      int i = 0;
      int m = arrayOfField.length;
      while (i < m)
      {
        Field localField = arrayOfField[i];
        int k = i + 1;
        i = k;
        if ((Modifier.isStatic(localField.getModifiers()) ^ true))
        {
          j++;
          i = k;
        }
      }
      paramInt += j;
      paramClass = paramClass.getSuperclass();
      if (paramClass == null) {
        return paramInt;
      }
    }
  }
  
  /* Error */
  private static final int fieldsCountOrDefault(Class<?> paramClass, int paramInt)
  {
    // Byte code:
    //   0: aload_0
    //   1: invokestatic 184	kotlin/jvm/JvmClassMappingKt:getKotlinClass	(Ljava/lang/Class;)Lkotlin/reflect/KClass;
    //   4: pop
    //   5: getstatic 190	kotlin/Result:Companion	Lkotlin/Result$Companion;
    //   8: astore_2
    //   9: aload_0
    //   10: iconst_0
    //   11: iconst_1
    //   12: aconst_null
    //   13: invokestatic 192	kotlinx/coroutines/internal/ExceptionsConstructorKt:fieldsCount$default	(Ljava/lang/Class;IILjava/lang/Object;)I
    //   16: invokestatic 198	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
    //   19: invokestatic 202	kotlin/Result:constructor-impl	(Ljava/lang/Object;)Ljava/lang/Object;
    //   22: astore_0
    //   23: goto +16 -> 39
    //   26: astore_2
    //   27: getstatic 190	kotlin/Result:Companion	Lkotlin/Result$Companion;
    //   30: astore_0
    //   31: aload_2
    //   32: invokestatic 208	kotlin/ResultKt:createFailure	(Ljava/lang/Throwable;)Ljava/lang/Object;
    //   35: invokestatic 202	kotlin/Result:constructor-impl	(Ljava/lang/Object;)Ljava/lang/Object;
    //   38: astore_0
    //   39: aload_0
    //   40: astore_2
    //   41: aload_0
    //   42: invokestatic 212	kotlin/Result:isFailure-impl	(Ljava/lang/Object;)Z
    //   45: ifeq +8 -> 53
    //   48: iload_1
    //   49: invokestatic 198	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
    //   52: astore_2
    //   53: aload_2
    //   54: checkcast 214	java/lang/Number
    //   57: invokevirtual 217	java/lang/Number:intValue	()I
    //   60: ireturn
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	61	0	paramClass	Class<?>
    //   0	61	1	paramInt	int
    //   8	1	2	localCompanion	Result.Companion
    //   26	6	2	localThrowable	Throwable
    //   40	14	2	localObject	Object
    // Exception table:
    //   from	to	target	type
    //   5	23	26	finally
  }
  
  private static final Function1<Throwable, Throwable> safeCtor(Function1<? super Throwable, ? extends Throwable> paramFunction1)
  {
    (Function1)new Lambda(paramFunction1)
    {
      final Function1<Throwable, Throwable> $block;
      
      /* Error */
      public final Throwable invoke(Throwable paramAnonymousThrowable)
      {
        // Byte code:
        //   0: aload_0
        //   1: getfield 36	kotlinx/coroutines/internal/ExceptionsConstructorKt$safeCtor$1:$block	Lkotlin/jvm/functions/Function1;
        //   4: astore_3
        //   5: getstatic 53	kotlin/Result:Companion	Lkotlin/Result$Companion;
        //   8: astore_2
        //   9: aload_3
        //   10: aload_1
        //   11: invokeinterface 55 2 0
        //   16: checkcast 44	java/lang/Throwable
        //   19: invokestatic 58	kotlin/Result:constructor-impl	(Ljava/lang/Object;)Ljava/lang/Object;
        //   22: astore_1
        //   23: goto +16 -> 39
        //   26: astore_2
        //   27: getstatic 53	kotlin/Result:Companion	Lkotlin/Result$Companion;
        //   30: astore_1
        //   31: aload_2
        //   32: invokestatic 64	kotlin/ResultKt:createFailure	(Ljava/lang/Throwable;)Ljava/lang/Object;
        //   35: invokestatic 58	kotlin/Result:constructor-impl	(Ljava/lang/Object;)Ljava/lang/Object;
        //   38: astore_1
        //   39: aload_1
        //   40: astore_2
        //   41: aload_1
        //   42: invokestatic 68	kotlin/Result:isFailure-impl	(Ljava/lang/Object;)Z
        //   45: ifeq +5 -> 50
        //   48: aconst_null
        //   49: astore_2
        //   50: aload_2
        //   51: checkcast 44	java/lang/Throwable
        //   54: areturn
        // Local variable table:
        //   start	length	slot	name	signature
        //   0	55	0	this	1
        //   0	55	1	paramAnonymousThrowable	Throwable
        //   8	1	2	localCompanion	Result.Companion
        //   26	6	2	localThrowable1	Throwable
        //   40	11	2	localThrowable2	Throwable
        //   4	6	3	localFunction1	Function1
        // Exception table:
        //   from	to	target	type
        //   5	23	26	finally
      }
    };
  }
  
  /* Error */
  public static final <E extends Throwable> E tryCopyException(E paramE)
  {
    // Byte code:
    //   0: aload_0
    //   1: instanceof 225
    //   4: ifeq +52 -> 56
    //   7: getstatic 190	kotlin/Result:Companion	Lkotlin/Result$Companion;
    //   10: astore_1
    //   11: aload_0
    //   12: checkcast 225	kotlinx/coroutines/CopyableThrowable
    //   15: invokeinterface 229 1 0
    //   20: invokestatic 202	kotlin/Result:constructor-impl	(Ljava/lang/Object;)Ljava/lang/Object;
    //   23: astore_0
    //   24: goto +16 -> 40
    //   27: astore_0
    //   28: getstatic 190	kotlin/Result:Companion	Lkotlin/Result$Companion;
    //   31: astore_1
    //   32: aload_0
    //   33: invokestatic 208	kotlin/ResultKt:createFailure	(Ljava/lang/Throwable;)Ljava/lang/Object;
    //   36: invokestatic 202	kotlin/Result:constructor-impl	(Ljava/lang/Object;)Ljava/lang/Object;
    //   39: astore_0
    //   40: aload_0
    //   41: astore_1
    //   42: aload_0
    //   43: invokestatic 212	kotlin/Result:isFailure-impl	(Ljava/lang/Object;)Z
    //   46: ifeq +5 -> 51
    //   49: aconst_null
    //   50: astore_1
    //   51: aload_1
    //   52: checkcast 59	java/lang/Throwable
    //   55: areturn
    //   56: getstatic 85	kotlinx/coroutines/internal/ExceptionsConstructorKt:ctorCache	Lkotlinx/coroutines/internal/CtorCache;
    //   59: aload_0
    //   60: invokevirtual 232	java/lang/Object:getClass	()Ljava/lang/Class;
    //   63: invokevirtual 235	kotlinx/coroutines/internal/CtorCache:get	(Ljava/lang/Class;)Lkotlin/jvm/functions/Function1;
    //   66: aload_0
    //   67: invokeinterface 238 2 0
    //   72: checkcast 59	java/lang/Throwable
    //   75: areturn
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	76	0	paramE	E
    //   10	42	1	localObject	Object
    // Exception table:
    //   from	to	target	type
    //   7	24	27	finally
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlinx/coroutines/internal/ExceptionsConstructorKt.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */