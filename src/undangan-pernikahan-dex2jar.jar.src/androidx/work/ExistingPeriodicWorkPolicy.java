package androidx.work;

public enum ExistingPeriodicWorkPolicy
{
  private static final ExistingPeriodicWorkPolicy[] $VALUES;
  
  static
  {
    ExistingPeriodicWorkPolicy localExistingPeriodicWorkPolicy2 = new ExistingPeriodicWorkPolicy("REPLACE", 0);
    REPLACE = localExistingPeriodicWorkPolicy2;
    ExistingPeriodicWorkPolicy localExistingPeriodicWorkPolicy1 = new ExistingPeriodicWorkPolicy("KEEP", 1);
    KEEP = localExistingPeriodicWorkPolicy1;
    $VALUES = new ExistingPeriodicWorkPolicy[] { localExistingPeriodicWorkPolicy2, localExistingPeriodicWorkPolicy1 };
  }
  
  private ExistingPeriodicWorkPolicy() {}
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/work/ExistingPeriodicWorkPolicy.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */