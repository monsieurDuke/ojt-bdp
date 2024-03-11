package kotlin.reflect;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

@Metadata(d1={"\000\016\n\000\n\002\020\016\n\002\030\002\n\002\b\003\"\037\020\000\032\004\030\0010\001*\006\022\002\b\0030\0028À\002X\004¢\006\006\032\004\b\003\020\004¨\006\005"}, d2={"qualifiedOrSimpleName", "", "Lkotlin/reflect/KClass;", "getQualifiedOrSimpleName", "(Lkotlin/reflect/KClass;)Ljava/lang/String;", "kotlin-stdlib"}, k=2, mv={1, 7, 1}, xi=48)
public final class KClassesImplKt
{
  public static final String getQualifiedOrSimpleName(KClass<?> paramKClass)
  {
    Intrinsics.checkNotNullParameter(paramKClass, "<this>");
    return paramKClass.getQualifiedName();
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlin/reflect/KClassesImplKt.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */