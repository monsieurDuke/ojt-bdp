package androidx.room;

import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.CLASS)
public @interface Update
{
  Class<?> entity() default Object.class;
  
  int onConflict() default 3;
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/room/Update.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */