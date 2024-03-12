package kotlin;

import java.lang.annotation.Annotation;
import java.lang.annotation.RetentionPolicy;
import kotlin.annotation.AnnotationRetention;

@java.lang.annotation.Retention(RetentionPolicy.RUNTIME)
@java.lang.annotation.Target({java.lang.annotation.ElementType.TYPE})
@Metadata(d1={"\000$\n\002\030\002\n\002\020\033\n\000\n\002\020\b\n\000\n\002\020\025\n\002\b\002\n\002\020\021\n\002\020\016\n\002\b\025\b\002\030\0002\0020\001B\\\022\b\b\002\020\002\032\0020\003\022\b\b\002\020\004\032\0020\005\022\b\b\002\020\006\032\0020\005\022\016\b\002\020\007\032\b\022\004\022\0020\t0\b\022\016\b\002\020\n\032\b\022\004\022\0020\t0\b\022\b\b\002\020\013\032\0020\t\022\b\b\002\020\f\032\0020\t\022\b\b\002\020\r\032\0020\003R\030\020\006\032\0020\005X\004¢\006\f\022\004\b\016\020\017\032\004\b\020\020\021R\027\020\007\032\b\022\004\022\0020\t0\b8\007¢\006\006\032\004\b\022\020\023R\027\020\n\032\b\022\004\022\0020\t0\b8\007¢\006\006\032\004\b\024\020\023R\030\020\r\032\0020\003X\004¢\006\f\022\004\b\025\020\017\032\004\b\026\020\027R\021\020\013\032\0020\t8\007¢\006\006\032\004\b\030\020\031R\021\020\002\032\0020\0038\007¢\006\006\032\004\b\032\020\027R\021\020\004\032\0020\0058\007¢\006\006\032\004\b\033\020\021R\030\020\f\032\0020\tX\004¢\006\f\022\004\b\034\020\017\032\004\b\035\020\031¨\006\036"}, d2={"Lkotlin/Metadata;", "", "kind", "", "metadataVersion", "", "bytecodeVersion", "data1", "", "", "data2", "extraString", "packageName", "extraInt", "bv$annotations", "()V", "bv", "()[I", "d1", "()[Ljava/lang/String;", "d2", "xi$annotations", "xi", "()I", "xs", "()Ljava/lang/String;", "k", "mv", "pn$annotations", "pn", "kotlin-stdlib"}, k=1, mv={1, 7, 1}, xi=48)
@kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
@kotlin.annotation.Target(allowedTargets={kotlin.annotation.AnnotationTarget.CLASS})
public @interface Metadata
{
  int[] bv() default {1, 0, 3};
  
  String[] d1() default {};
  
  String[] d2() default {};
  
  int k() default 1;
  
  int[] mv() default {};
  
  String pn() default "";
  
  int xi() default 0;
  
  String xs() default "";
  
  @Metadata(k=3, mv={1, 7, 1}, xi=48)
  public static final class DefaultImpls {}
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlin/Metadata.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */