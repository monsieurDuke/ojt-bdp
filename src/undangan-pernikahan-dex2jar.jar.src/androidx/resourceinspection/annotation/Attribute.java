package androidx.resourceinspection.annotation;

import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.SOURCE)
@Target({java.lang.annotation.ElementType.METHOD})
public @interface Attribute
{
  IntMap[] intMapping() default {};
  
  String value();
  
  @Retention(RetentionPolicy.SOURCE)
  @Target({})
  public static @interface IntMap
  {
    int mask() default 0;
    
    String name();
    
    int value();
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/resourceinspection/annotation/Attribute.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */