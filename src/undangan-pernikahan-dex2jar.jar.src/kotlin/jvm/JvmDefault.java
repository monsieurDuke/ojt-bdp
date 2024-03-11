package kotlin.jvm;

import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import kotlin.Deprecated;
import kotlin.Metadata;

@Retention(RetentionPolicy.RUNTIME)
@java.lang.annotation.Target({java.lang.annotation.ElementType.METHOD})
@Deprecated(message="Switch to new -Xjvm-default modes: `all` or `all-compatibility`")
@Metadata(d1={"\000\n\n\002\030\002\n\002\020\033\n\000\b\002\030\0002\0020\001B\000ø\001\000\002\007\n\005\b(0\001¨\006\002"}, d2={"Lkotlin/jvm/JvmDefault;", "", "kotlin-stdlib"}, k=1, mv={1, 7, 1}, xi=48)
@kotlin.annotation.Target(allowedTargets={kotlin.annotation.AnnotationTarget.FUNCTION, kotlin.annotation.AnnotationTarget.PROPERTY})
public @interface JvmDefault {}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlin/jvm/JvmDefault.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */