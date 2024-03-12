package kotlin.time;

import kotlin.Metadata;
import mt.Log229316;
import mt.Log5ECF72;
import mt.LogE84000;

@Metadata(d1={"\000 \n\002\030\002\n\002\030\002\n\002\b\002\n\002\020\t\n\000\n\002\020\002\n\000\n\002\030\002\n\002\b\006\b\007\030\0002\0020\001B\005¢\006\002\020\002J\032\020\005\032\0020\0062\006\020\007\032\0020\bH\002ø\001\000¢\006\004\b\t\020\nJ\033\020\013\032\0020\0062\006\020\007\032\0020\bH\002ø\001\000¢\006\004\b\f\020\nJ\b\020\r\032\0020\004H\024R\016\020\003\032\0020\004X\016¢\006\002\n\000\002\004\n\002\b\031¨\006\016"}, d2={"Lkotlin/time/TestTimeSource;", "Lkotlin/time/AbstractLongTimeSource;", "()V", "reading", "", "overflow", "", "duration", "Lkotlin/time/Duration;", "overflow-LRDsOJo", "(J)V", "plusAssign", "plusAssign-LRDsOJo", "read", "kotlin-stdlib"}, k=1, mv={1, 7, 1}, xi=48)
public final class TestTimeSource
  extends AbstractLongTimeSource
{
  private long reading;
  
  public TestTimeSource()
  {
    super(DurationUnit.NANOSECONDS);
  }
  
  private final void overflow-LRDsOJo(long paramLong)
  {
    StringBuilder localStringBuilder = new StringBuilder().append("TestTimeSource will overflow if its reading ").append(this.reading).append("ns is advanced by ");
    String str = Duration.toString-impl(paramLong);
    Log5ECF72.a(str);
    LogE84000.a(str);
    Log229316.a(str);
    throw new IllegalStateException(str + '.');
  }
  
  public final void plusAssign-LRDsOJo(long paramLong)
  {
    long l3 = Duration.toLong-impl(paramLong, getUnit());
    if ((l3 != Long.MIN_VALUE) && (l3 != Long.MAX_VALUE))
    {
      long l2 = this.reading;
      long l1 = l2 + l3;
      if (((l2 ^ l3) >= 0L) && ((l2 ^ l1) < 0L)) {
        overflow-LRDsOJo(paramLong);
      }
      paramLong = l1;
    }
    else
    {
      double d = Duration.toDouble-impl(paramLong, getUnit());
      d = this.reading + d;
      if ((d > 9.223372036854776E18D) || (d < -9.223372036854776E18D)) {
        overflow-LRDsOJo(paramLong);
      }
      paramLong = d;
    }
    this.reading = paramLong;
  }
  
  protected long read()
  {
    return this.reading;
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlin/time/TestTimeSource.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */