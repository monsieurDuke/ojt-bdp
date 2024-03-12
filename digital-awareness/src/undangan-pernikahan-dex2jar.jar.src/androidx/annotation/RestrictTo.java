package androidx.annotation;

import java.lang.annotation.Annotation;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Retention(RetentionPolicy.CLASS)
@Target({java.lang.annotation.ElementType.ANNOTATION_TYPE, java.lang.annotation.ElementType.TYPE, java.lang.annotation.ElementType.METHOD, java.lang.annotation.ElementType.CONSTRUCTOR, java.lang.annotation.ElementType.FIELD, java.lang.annotation.ElementType.PACKAGE})
public @interface RestrictTo
{
  Scope[] value();
  
  public static enum Scope
  {
    private static final Scope[] $VALUES;
    
    static
    {
      Scope localScope4 = new Scope("LIBRARY", 0);
      LIBRARY = localScope4;
      Scope localScope3 = new Scope("LIBRARY_GROUP", 1);
      LIBRARY_GROUP = localScope3;
      Scope localScope6 = new Scope("LIBRARY_GROUP_PREFIX", 2);
      LIBRARY_GROUP_PREFIX = localScope6;
      Scope localScope5 = new Scope("GROUP_ID", 3);
      GROUP_ID = localScope5;
      Scope localScope1 = new Scope("TESTS", 4);
      TESTS = localScope1;
      Scope localScope2 = new Scope("SUBCLASSES", 5);
      SUBCLASSES = localScope2;
      $VALUES = new Scope[] { localScope4, localScope3, localScope6, localScope5, localScope1, localScope2 };
    }
    
    private Scope() {}
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/annotation/RestrictTo.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */