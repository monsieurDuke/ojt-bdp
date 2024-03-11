package androidx.room;

import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.CLASS)
public @interface OnConflictStrategy
{
  public static final int ABORT = 3;
  @Deprecated
  public static final int FAIL = 4;
  public static final int IGNORE = 5;
  public static final int REPLACE = 1;
  @Deprecated
  public static final int ROLLBACK = 2;
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/room/OnConflictStrategy.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */