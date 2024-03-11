package androidx.work;

public enum BackoffPolicy
{
  private static final BackoffPolicy[] $VALUES;
  
  static
  {
    BackoffPolicy localBackoffPolicy1 = new BackoffPolicy("EXPONENTIAL", 0);
    EXPONENTIAL = localBackoffPolicy1;
    BackoffPolicy localBackoffPolicy2 = new BackoffPolicy("LINEAR", 1);
    LINEAR = localBackoffPolicy2;
    $VALUES = new BackoffPolicy[] { localBackoffPolicy1, localBackoffPolicy2 };
  }
  
  private BackoffPolicy() {}
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/work/BackoffPolicy.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */