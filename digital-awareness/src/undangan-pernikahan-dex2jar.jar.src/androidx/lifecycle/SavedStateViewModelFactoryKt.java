package androidx.lifecycle;

import android.app.Application;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;

@Metadata(d1={"\000*\n\000\n\002\020 \n\002\030\002\n\002\b\002\n\002\030\002\n\002\b\004\n\002\030\002\n\002\b\002\n\002\020\021\n\002\020\000\n\002\b\002\0326\020\004\032\n\022\004\022\002H\006\030\0010\005\"\004\b\000\020\0062\f\020\007\032\b\022\004\022\002H\0060\0022\020\020\b\032\f\022\b\022\006\022\002\b\0030\0020\001H\000\032I\020\t\032\002H\006\"\n\b\000\020\006*\004\030\0010\n2\f\020\007\032\b\022\004\022\002H\0060\0022\f\020\013\032\b\022\004\022\002H\0060\0052\022\020\f\032\n\022\006\b\001\022\0020\0160\r\"\0020\016H\000¢\006\002\020\017\"\030\020\000\032\f\022\b\022\006\022\002\b\0030\0020\001X\004¢\006\002\n\000\"\030\020\003\032\f\022\b\022\006\022\002\b\0030\0020\001X\004¢\006\002\n\000¨\006\020"}, d2={"ANDROID_VIEWMODEL_SIGNATURE", "", "Ljava/lang/Class;", "VIEWMODEL_SIGNATURE", "findMatchingConstructor", "Ljava/lang/reflect/Constructor;", "T", "modelClass", "signature", "newInstance", "Landroidx/lifecycle/ViewModel;", "constructor", "params", "", "", "(Ljava/lang/Class;Ljava/lang/reflect/Constructor;[Ljava/lang/Object;)Landroidx/lifecycle/ViewModel;", "lifecycle-viewmodel-savedstate_release"}, k=2, mv={1, 6, 0}, xi=48)
public final class SavedStateViewModelFactoryKt
{
  private static final List<Class<?>> ANDROID_VIEWMODEL_SIGNATURE = CollectionsKt.listOf(new Class[] { Application.class, SavedStateHandle.class });
  private static final List<Class<?>> VIEWMODEL_SIGNATURE = CollectionsKt.listOf(SavedStateHandle.class);
  
  public static final <T> Constructor<T> findMatchingConstructor(Class<T> paramClass, List<? extends Class<?>> paramList)
  {
    Intrinsics.checkNotNullParameter(paramClass, "modelClass");
    Intrinsics.checkNotNullParameter(paramList, "signature");
    Constructor[] arrayOfConstructor = paramClass.getConstructors();
    Intrinsics.checkNotNullExpressionValue(arrayOfConstructor, "modelClass.constructors");
    int j = arrayOfConstructor.length;
    for (int i = 0; i < j; i++)
    {
      Constructor localConstructor = arrayOfConstructor[i];
      Object localObject = localConstructor.getParameterTypes();
      Intrinsics.checkNotNullExpressionValue(localObject, "constructor.parameterTypes");
      localObject = ArraysKt.toList((Object[])localObject);
      if (Intrinsics.areEqual(paramList, localObject))
      {
        if (localConstructor != null) {
          return localConstructor;
        }
        throw new NullPointerException("null cannot be cast to non-null type java.lang.reflect.Constructor<T of androidx.lifecycle.SavedStateViewModelFactoryKt.findMatchingConstructor>");
      }
      if ((paramList.size() == ((List)localObject).size()) && (((List)localObject).containsAll((Collection)paramList)))
      {
        paramClass = new StringBuilder().append("Class ").append(paramClass.getSimpleName()).append(" must have parameters in the proper order: ");
        throw new UnsupportedOperationException(paramList);
      }
    }
    return null;
  }
  
  public static final <T extends ViewModel> T newInstance(Class<T> paramClass, Constructor<T> paramConstructor, Object... paramVarArgs)
  {
    Intrinsics.checkNotNullParameter(paramClass, "modelClass");
    Intrinsics.checkNotNullParameter(paramConstructor, "constructor");
    Intrinsics.checkNotNullParameter(paramVarArgs, "params");
    try
    {
      paramConstructor = (ViewModel)paramConstructor.newInstance(Arrays.copyOf(paramVarArgs, paramVarArgs.length));
      return paramConstructor;
    }
    catch (InvocationTargetException paramConstructor)
    {
      throw new RuntimeException("An exception happened in constructor of " + paramClass, paramConstructor.getCause());
    }
    catch (InstantiationException paramConstructor)
    {
      throw new RuntimeException("A " + paramClass + " cannot be instantiated.", (Throwable)paramConstructor);
    }
    catch (IllegalAccessException paramConstructor)
    {
      throw new RuntimeException("Failed to access " + paramClass, (Throwable)paramConstructor);
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/lifecycle/SavedStateViewModelFactoryKt.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */