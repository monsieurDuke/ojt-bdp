package kotlin;

import java.lang.annotation.Annotation;
import java.lang.annotation.RetentionPolicy;
import kotlin.annotation.AnnotationRetention;

@java.lang.annotation.Retention(RetentionPolicy.SOURCE)
@java.lang.annotation.Target({java.lang.annotation.ElementType.TYPE, java.lang.annotation.ElementType.METHOD, java.lang.annotation.ElementType.PARAMETER, java.lang.annotation.ElementType.CONSTRUCTOR, java.lang.annotation.ElementType.LOCAL_VARIABLE})
@Deprecated(message="Please use OptIn instead.", replaceWith=@ReplaceWith(expression="OptIn(*markerClass)", imports={"kotlin.OptIn"}))
@DeprecatedSinceKotlin(errorSince="1.6", warningSince="1.4")
@Metadata(d1={"\000\026\n\002\030\002\n\002\020\033\n\000\n\002\020\021\n\002\030\002\n\002\b\002\b\002\030\0002\0020\001B$\022\"\020\002\032\022\022\016\b\001\022\n\022\006\b\001\022\0020\0010\0040\003\"\n\022\006\b\001\022\0020\0010\004R\037\020\002\032\022\022\016\b\001\022\n\022\006\b\001\022\0020\0010\0040\003¢\006\006\032\004\b\002\020\005ø\001\000\002\007\n\005\b20\001¨\006\006"}, d2={"Lkotlin/UseExperimental;", "", "markerClass", "", "Lkotlin/reflect/KClass;", "()[Ljava/lang/Class;", "kotlin-stdlib"}, k=1, mv={1, 7, 1}, xi=48)
@kotlin.annotation.Retention(AnnotationRetention.SOURCE)
@kotlin.annotation.Target(allowedTargets={kotlin.annotation.AnnotationTarget.CLASS, kotlin.annotation.AnnotationTarget.PROPERTY, kotlin.annotation.AnnotationTarget.LOCAL_VARIABLE, kotlin.annotation.AnnotationTarget.VALUE_PARAMETER, kotlin.annotation.AnnotationTarget.CONSTRUCTOR, kotlin.annotation.AnnotationTarget.FUNCTION, kotlin.annotation.AnnotationTarget.PROPERTY_GETTER, kotlin.annotation.AnnotationTarget.PROPERTY_SETTER, kotlin.annotation.AnnotationTarget.EXPRESSION, kotlin.annotation.AnnotationTarget.FILE, kotlin.annotation.AnnotationTarget.TYPEALIAS})
public @interface UseExperimental
{
  Class<? extends Annotation>[] markerClass();
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlin/UseExperimental.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */