package kotlin;

import java.lang.annotation.Annotation;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import kotlin.annotation.MustBeDocumented;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@java.lang.annotation.Target({java.lang.annotation.ElementType.TYPE, java.lang.annotation.ElementType.METHOD, java.lang.annotation.ElementType.CONSTRUCTOR, java.lang.annotation.ElementType.ANNOTATION_TYPE})
@Metadata(d1={"\000\022\n\002\030\002\n\002\020\033\n\000\n\002\020\016\n\002\b\004\b\002\030\0002\0020\001B\036\022\b\b\002\020\002\032\0020\003\022\b\b\002\020\004\032\0020\003\022\b\b\002\020\005\032\0020\003R\017\020\004\032\0020\003¢\006\006\032\004\b\004\020\006R\017\020\005\032\0020\003¢\006\006\032\004\b\005\020\006R\017\020\002\032\0020\003¢\006\006\032\004\b\002\020\006¨\006\007"}, d2={"Lkotlin/DeprecatedSinceKotlin;", "", "warningSince", "", "errorSince", "hiddenSince", "()Ljava/lang/String;", "kotlin-stdlib"}, k=1, mv={1, 7, 1}, xi=48)
@MustBeDocumented
@kotlin.annotation.Target(allowedTargets={kotlin.annotation.AnnotationTarget.CLASS, kotlin.annotation.AnnotationTarget.FUNCTION, kotlin.annotation.AnnotationTarget.PROPERTY, kotlin.annotation.AnnotationTarget.ANNOTATION_CLASS, kotlin.annotation.AnnotationTarget.CONSTRUCTOR, kotlin.annotation.AnnotationTarget.PROPERTY_SETTER, kotlin.annotation.AnnotationTarget.PROPERTY_GETTER, kotlin.annotation.AnnotationTarget.TYPEALIAS})
public @interface DeprecatedSinceKotlin
{
  String errorSince() default "";
  
  String hiddenSince() default "";
  
  String warningSince() default "";
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlin/DeprecatedSinceKotlin.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */