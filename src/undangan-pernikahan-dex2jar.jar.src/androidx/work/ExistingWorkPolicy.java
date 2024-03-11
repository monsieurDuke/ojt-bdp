package androidx.work;

public enum ExistingWorkPolicy
{
  private static final ExistingWorkPolicy[] $VALUES;
  
  static
  {
    ExistingWorkPolicy localExistingWorkPolicy3 = new ExistingWorkPolicy("REPLACE", 0);
    REPLACE = localExistingWorkPolicy3;
    ExistingWorkPolicy localExistingWorkPolicy1 = new ExistingWorkPolicy("KEEP", 1);
    KEEP = localExistingWorkPolicy1;
    ExistingWorkPolicy localExistingWorkPolicy2 = new ExistingWorkPolicy("APPEND", 2);
    APPEND = localExistingWorkPolicy2;
    ExistingWorkPolicy localExistingWorkPolicy4 = new ExistingWorkPolicy("APPEND_OR_REPLACE", 3);
    APPEND_OR_REPLACE = localExistingWorkPolicy4;
    $VALUES = new ExistingWorkPolicy[] { localExistingWorkPolicy3, localExistingWorkPolicy1, localExistingWorkPolicy2, localExistingWorkPolicy4 };
  }
  
  private ExistingWorkPolicy() {}
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/work/ExistingWorkPolicy.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */