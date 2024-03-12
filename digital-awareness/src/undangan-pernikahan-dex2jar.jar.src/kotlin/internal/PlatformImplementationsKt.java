package kotlin.internal;

import kotlin.KotlinVersion;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import mt.Log229316;
import mt.Log5ECF72;
import mt.LogE84000;

@Metadata(d1={"\000\036\n\000\n\002\030\002\n\000\n\002\020\013\n\000\n\002\020\b\n\002\b\004\n\002\020\000\n\002\b\004\032 \020\002\032\0020\0032\006\020\004\032\0020\0052\006\020\006\032\0020\0052\006\020\007\032\0020\005H\001\032\"\020\b\032\002H\t\"\n\b\000\020\t\030\001*\0020\n2\006\020\013\032\0020\nH\b¢\006\002\020\f\032\b\020\r\032\0020\005H\002\"\020\020\000\032\0020\0018\000X\004¢\006\002\n\000¨\006\016"}, d2={"IMPLEMENTATIONS", "Lkotlin/internal/PlatformImplementations;", "apiVersionIsAtLeast", "", "major", "", "minor", "patch", "castToBaseType", "T", "", "instance", "(Ljava/lang/Object;)Ljava/lang/Object;", "getJavaVersion", "kotlin-stdlib"}, k=2, mv={1, 7, 1}, xi=48)
public final class PlatformImplementationsKt
{
  public static final PlatformImplementations IMPLEMENTATIONS;
  
  static
  {
    int i = getJavaVersion();
    Object localObject1;
    label59:
    ClassLoader localClassLoader;
    ClassNotFoundException localClassNotFoundException5;
    Object localObject2;
    if ((i >= 65544) || (i < 65536))
    {
      try
      {
        localObject1 = Class.forName("kotlin.internal.jdk8.JDK8PlatformImplementations").newInstance();
        Intrinsics.checkNotNullExpressionValue(localObject1, "forName(\"kotlin.internal…entations\").newInstance()");
        if (localObject1 != null)
        {
          try
          {
            PlatformImplementations localPlatformImplementations1 = (PlatformImplementations)localObject1;
          }
          catch (ClassCastException localClassCastException1)
          {
            break label59;
          }
        }
        else
        {
          localNullPointerException1 = new java/lang/NullPointerException;
          localNullPointerException1.<init>("null cannot be cast to non-null type kotlin.internal.PlatformImplementations");
          throw localNullPointerException1;
        }
      }
      catch (ClassNotFoundException localClassNotFoundException1)
      {
        for (;;)
        {
          NullPointerException localNullPointerException2;
          try
          {
            NullPointerException localNullPointerException1;
            localObject1 = Class.forName("kotlin.internal.JRE8PlatformImplementations").newInstance();
            Intrinsics.checkNotNullExpressionValue(localObject1, "forName(\"kotlin.internal…entations\").newInstance()");
            if (localObject1 != null)
            {
              try
              {
                PlatformImplementations localPlatformImplementations2 = (PlatformImplementations)localObject1;
              }
              catch (ClassCastException localClassCastException2)
              {
                break label177;
              }
            }
            else
            {
              localNullPointerException2 = new java/lang/NullPointerException;
              localNullPointerException2.<init>("null cannot be cast to non-null type kotlin.internal.PlatformImplementations");
              throw localNullPointerException2;
            }
          }
          catch (ClassNotFoundException localClassNotFoundException2) {}
        }
        localClassLoader = localObject1.getClass().getClassLoader();
        localObject1 = PlatformImplementations.class.getClassLoader();
        if (Intrinsics.areEqual(localClassLoader, localObject1)) {
          break label249;
        }
        localClassNotFoundException5 = new java/lang/ClassNotFoundException;
        localObject2 = new java/lang/StringBuilder;
        ((StringBuilder)localObject2).<init>();
        localClassNotFoundException5.<init>("Instance class was loaded from a different classloader: " + localClassLoader + ", base type classloader: " + localObject1, (Throwable)localNullPointerException2);
        throw localClassNotFoundException5;
        throw localNullPointerException2;
      }
      localObject1 = localObject1.getClass().getClassLoader();
      localClassLoader = PlatformImplementations.class.getClassLoader();
      if (!Intrinsics.areEqual(localObject1, localClassLoader))
      {
        localClassNotFoundException5 = new java/lang/ClassNotFoundException;
        localObject2 = new java/lang/StringBuilder;
        ((StringBuilder)localObject2).<init>();
        localClassNotFoundException5.<init>("Instance class was loaded from a different classloader: " + localObject1 + ", base type classloader: " + localClassLoader, (Throwable)localNullPointerException1);
        throw localClassNotFoundException5;
      }
      throw localNullPointerException1;
    }
    label177:
    label249:
    if ((i >= 65543) || (i < 65536))
    {
      try
      {
        localObject1 = Class.forName("kotlin.internal.jdk7.JDK7PlatformImplementations").newInstance();
        Intrinsics.checkNotNullExpressionValue(localObject1, "forName(\"kotlin.internal…entations\").newInstance()");
        if (localObject1 != null)
        {
          try
          {
            PlatformImplementations localPlatformImplementations3 = (PlatformImplementations)localObject1;
          }
          catch (ClassCastException localClassCastException3)
          {
            break label307;
          }
        }
        else
        {
          localNullPointerException3 = new java/lang/NullPointerException;
          localNullPointerException3.<init>("null cannot be cast to non-null type kotlin.internal.PlatformImplementations");
          throw localNullPointerException3;
        }
      }
      catch (ClassNotFoundException localClassNotFoundException3)
      {
        for (;;)
        {
          label307:
          NullPointerException localNullPointerException4;
          try
          {
            NullPointerException localNullPointerException3;
            localObject1 = Class.forName("kotlin.internal.JRE7PlatformImplementations").newInstance();
            Intrinsics.checkNotNullExpressionValue(localObject1, "forName(\"kotlin.internal…entations\").newInstance()");
            if (localObject1 != null)
            {
              try
              {
                PlatformImplementations localPlatformImplementations4 = (PlatformImplementations)localObject1;
              }
              catch (ClassCastException localClassCastException4)
              {
                break label425;
              }
            }
            else
            {
              localNullPointerException4 = new java/lang/NullPointerException;
              localNullPointerException4.<init>("null cannot be cast to non-null type kotlin.internal.PlatformImplementations");
              throw localNullPointerException4;
            }
          }
          catch (ClassNotFoundException localClassNotFoundException4) {}
        }
        localClassLoader = localObject1.getClass().getClassLoader();
        localObject2 = PlatformImplementations.class.getClassLoader();
        if (Intrinsics.areEqual(localClassLoader, localObject2)) {
          break label497;
        }
        localClassNotFoundException5 = new java/lang/ClassNotFoundException;
        localObject1 = new java/lang/StringBuilder;
        ((StringBuilder)localObject1).<init>();
        localClassNotFoundException5.<init>("Instance class was loaded from a different classloader: " + localClassLoader + ", base type classloader: " + localObject2, (Throwable)localNullPointerException4);
        throw localClassNotFoundException5;
        throw localNullPointerException4;
      }
      localClassLoader = localObject1.getClass().getClassLoader();
      localObject2 = PlatformImplementations.class.getClassLoader();
      if (!Intrinsics.areEqual(localClassLoader, localObject2))
      {
        localClassNotFoundException5 = new java/lang/ClassNotFoundException;
        localObject1 = new java/lang/StringBuilder;
        ((StringBuilder)localObject1).<init>();
        localClassNotFoundException5.<init>("Instance class was loaded from a different classloader: " + localClassLoader + ", base type classloader: " + localObject2, (Throwable)localNullPointerException3);
        throw localClassNotFoundException5;
      }
      throw localNullPointerException3;
    }
    label425:
    label497:
    PlatformImplementations localPlatformImplementations5 = new PlatformImplementations();
    IMPLEMENTATIONS = localPlatformImplementations5;
  }
  
  public static final boolean apiVersionIsAtLeast(int paramInt1, int paramInt2, int paramInt3)
  {
    return KotlinVersion.CURRENT.isAtLeast(paramInt1, paramInt2, paramInt3);
  }
  
  private static final int getJavaVersion()
  {
    String str2 = System.getProperty("java.specification.version");
    Log5ECF72.a(str2);
    LogE84000.a(str2);
    Log229316.a(str2);
    if (str2 == null) {
      return 65542;
    }
    int k = StringsKt.indexOf$default((CharSequence)str2, '.', 0, false, 6, null);
    if (k < 0)
    {
      try
      {
        i = Integer.parseInt(str2);
        i *= 65536;
      }
      catch (NumberFormatException localNumberFormatException1)
      {
        i = 65542;
      }
      return i;
    }
    int j = StringsKt.indexOf$default((CharSequence)str2, '.', k + 1, false, 4, null);
    int i = j;
    if (j < 0) {
      i = str2.length();
    }
    String str1 = str2.substring(0, k);
    Intrinsics.checkNotNullExpressionValue(str1, "this as java.lang.String…ing(startIndex, endIndex)");
    str2 = str2.substring(k + 1, i);
    Intrinsics.checkNotNullExpressionValue(str2, "this as java.lang.String…ing(startIndex, endIndex)");
    try
    {
      i = Integer.parseInt(str1);
      j = Integer.parseInt(str2);
      i = i * 65536 + j;
    }
    catch (NumberFormatException localNumberFormatException2)
    {
      i = 65542;
    }
    return i;
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlin/internal/PlatformImplementationsKt.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */