package androidx.work;

public enum OutOfQuotaPolicy
{
  private static final OutOfQuotaPolicy[] $VALUES;
  
  static
  {
    OutOfQuotaPolicy localOutOfQuotaPolicy2 = new OutOfQuotaPolicy("RUN_AS_NON_EXPEDITED_WORK_REQUEST", 0);
    RUN_AS_NON_EXPEDITED_WORK_REQUEST = localOutOfQuotaPolicy2;
    OutOfQuotaPolicy localOutOfQuotaPolicy1 = new OutOfQuotaPolicy("DROP_WORK_REQUEST", 1);
    DROP_WORK_REQUEST = localOutOfQuotaPolicy1;
    $VALUES = new OutOfQuotaPolicy[] { localOutOfQuotaPolicy2, localOutOfQuotaPolicy1 };
  }
  
  private OutOfQuotaPolicy() {}
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/work/OutOfQuotaPolicy.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */