package androidx.room;

import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.CLASS)
@Target({java.lang.annotation.ElementType.TYPE})
public @interface Entity
{
  ForeignKey[] foreignKeys() default {};
  
  String[] ignoredColumns() default {};
  
  Index[] indices() default {};
  
  boolean inheritSuperIndices() default false;
  
  String[] primaryKeys() default {};
  
  String tableName() default "";
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/room/Entity.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */