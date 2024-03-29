package androidx.versionedparcelable;

import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.SOURCE)
@Target({java.lang.annotation.ElementType.FIELD})
public @interface ParcelField
{
  String defaultValue() default "";
  
  int value();
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/versionedparcelable/ParcelField.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */