package androidx.lifecycle;

import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Deprecated
@Retention(RetentionPolicy.RUNTIME)
@Target({java.lang.annotation.ElementType.METHOD})
public @interface OnLifecycleEvent
{
  Lifecycle.Event value();
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/lifecycle/OnLifecycleEvent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */