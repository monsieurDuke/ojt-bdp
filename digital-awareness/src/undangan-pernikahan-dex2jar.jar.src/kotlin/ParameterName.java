package kotlin;

import java.lang.annotation.Annotation;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import kotlin.annotation.MustBeDocumented;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@java.lang.annotation.Target({})
@Metadata(d1={"\000\022\n\002\030\002\n\002\020\033\n\000\n\002\020\016\n\002\b\002\b\002\030\0002\0020\001B\b\022\006\020\002\032\0020\003R\017\020\002\032\0020\003¢\006\006\032\004\b\002\020\004¨\006\005"}, d2={"Lkotlin/ParameterName;", "", "name", "", "()Ljava/lang/String;", "kotlin-stdlib"}, k=1, mv={1, 7, 1}, xi=48)
@MustBeDocumented
@kotlin.annotation.Target(allowedTargets={kotlin.annotation.AnnotationTarget.TYPE})
public @interface ParameterName
{
  String name();
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlin/ParameterName.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */