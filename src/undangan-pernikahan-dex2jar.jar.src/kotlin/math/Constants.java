package kotlin.math;

import kotlin.Metadata;

@Metadata(d1={"\000\024\n\002\030\002\n\002\020\000\n\002\b\002\n\002\020\006\n\002\b\006\bÂ\002\030\0002\0020\001B\007\b\002¢\006\002\020\002R\020\020\003\032\0020\0048\000X\004¢\006\002\n\000R\020\020\005\032\0020\0048\000X\004¢\006\002\n\000R\020\020\006\032\0020\0048\000X\004¢\006\002\n\000R\020\020\007\032\0020\0048\000X\004¢\006\002\n\000R\020\020\b\032\0020\0048\000X\004¢\006\002\n\000R\020\020\t\032\0020\0048\000X\004¢\006\002\n\000¨\006\n"}, d2={"Lkotlin/math/Constants;", "", "()V", "LN2", "", "epsilon", "taylor_2_bound", "taylor_n_bound", "upper_taylor_2_bound", "upper_taylor_n_bound", "kotlin-stdlib"}, k=1, mv={1, 7, 1}, xi=48)
final class Constants
{
  public static final Constants INSTANCE = new Constants();
  public static final double LN2 = Math.log(2.0D);
  public static final double epsilon;
  public static final double taylor_2_bound;
  public static final double taylor_n_bound;
  public static final double upper_taylor_2_bound;
  public static final double upper_taylor_n_bound;
  
  static
  {
    double d1 = Math.ulp(1.0D);
    epsilon = d1;
    double d3 = Math.sqrt(d1);
    taylor_2_bound = d3;
    d1 = Math.sqrt(d3);
    taylor_n_bound = d1;
    double d2 = 1;
    upper_taylor_2_bound = d2 / d3;
    upper_taylor_n_bound = d2 / d1;
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlin/math/Constants.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */