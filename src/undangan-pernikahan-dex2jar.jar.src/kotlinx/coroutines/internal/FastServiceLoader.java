package kotlinx.coroutines.internal;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Constructor;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.jar.JarFile;
import java.util.zip.ZipEntry;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.io.CloseableKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import mt.Log229316;
import mt.Log5ECF72;
import mt.LogE84000;

@Metadata(d1={"\000N\n\002\030\002\n\002\020\000\n\002\b\002\n\002\020\016\n\000\n\002\030\002\n\000\n\002\030\002\n\002\b\005\n\002\030\002\n\002\b\003\n\002\020 \n\002\b\006\n\002\030\002\n\002\b\002\n\002\030\002\n\002\b\002\n\002\030\002\n\000\n\002\030\002\n\002\b\002\bÀ\002\030\0002\0020\001B\007\b\002¢\006\002\020\002J!\020\005\032\004\030\0010\0062\f\020\007\032\b\022\004\022\0020\0060\b2\006\020\t\032\0020\004H\bJ1\020\n\032\002H\013\"\004\b\000\020\0132\006\020\f\032\0020\0042\006\020\r\032\0020\0162\f\020\017\032\b\022\004\022\002H\0130\bH\002¢\006\002\020\020J*\020\021\032\b\022\004\022\002H\0130\022\"\004\b\000\020\0132\f\020\017\032\b\022\004\022\002H\0130\b2\006\020\r\032\0020\016H\002J\023\020\023\032\b\022\004\022\0020\0060\022H\000¢\006\002\b\024J/\020\025\032\b\022\004\022\002H\0130\022\"\004\b\000\020\0132\f\020\017\032\b\022\004\022\002H\0130\b2\006\020\r\032\0020\016H\000¢\006\002\b\026J\026\020\027\032\b\022\004\022\0020\0040\0222\006\020\030\032\0020\031H\002J\026\020\032\032\b\022\004\022\0020\0040\0222\006\020\033\032\0020\034H\002J,\020\035\032\002H\036\"\004\b\000\020\036*\0020\0372\022\020 \032\016\022\004\022\0020\037\022\004\022\002H\0360!H\b¢\006\002\020\"R\016\020\003\032\0020\004XT¢\006\002\n\000¨\006#"}, d2={"Lkotlinx/coroutines/internal/FastServiceLoader;", "", "()V", "PREFIX", "", "createInstanceOf", "Lkotlinx/coroutines/internal/MainDispatcherFactory;", "baseClass", "Ljava/lang/Class;", "serviceClass", "getProviderInstance", "S", "name", "loader", "Ljava/lang/ClassLoader;", "service", "(Ljava/lang/String;Ljava/lang/ClassLoader;Ljava/lang/Class;)Ljava/lang/Object;", "load", "", "loadMainDispatcherFactory", "loadMainDispatcherFactory$kotlinx_coroutines_core", "loadProviders", "loadProviders$kotlinx_coroutines_core", "parse", "url", "Ljava/net/URL;", "parseFile", "r", "Ljava/io/BufferedReader;", "use", "R", "Ljava/util/jar/JarFile;", "block", "Lkotlin/Function1;", "(Ljava/util/jar/JarFile;Lkotlin/jvm/functions/Function1;)Ljava/lang/Object;", "kotlinx-coroutines-core"}, k=1, mv={1, 6, 0}, xi=48)
public final class FastServiceLoader
{
  public static final FastServiceLoader INSTANCE = new FastServiceLoader();
  private static final String PREFIX = "META-INF/services/";
  
  private final MainDispatcherFactory createInstanceOf(Class<MainDispatcherFactory> paramClass, String paramString)
  {
    try
    {
      paramClass = (MainDispatcherFactory)paramClass.cast(Class.forName(paramString, true, paramClass.getClassLoader()).getDeclaredConstructor(new Class[0]).newInstance(new Object[0]));
    }
    catch (ClassNotFoundException paramClass)
    {
      paramClass = null;
      paramString = (MainDispatcherFactory)null;
    }
    return paramClass;
  }
  
  private final <S> S getProviderInstance(String paramString, ClassLoader paramClassLoader, Class<S> paramClass)
  {
    paramString = Class.forName(paramString, false, paramClassLoader);
    if (paramClass.isAssignableFrom(paramString)) {
      return (S)paramClass.cast(paramString.getDeclaredConstructor(new Class[0]).newInstance(new Object[0]));
    }
    throw new IllegalArgumentException(("Expected service of class " + paramClass + ", but found " + paramString).toString());
  }
  
  /* Error */
  private final <S> List<S> load(Class<S> paramClass, ClassLoader paramClassLoader)
  {
    // Byte code:
    //   0: aload_0
    //   1: aload_1
    //   2: aload_2
    //   3: invokevirtual 125	kotlinx/coroutines/internal/FastServiceLoader:loadProviders$kotlinx_coroutines_core	(Ljava/lang/Class;Ljava/lang/ClassLoader;)Ljava/util/List;
    //   6: astore_3
    //   7: aload_3
    //   8: astore_1
    //   9: goto +16 -> 25
    //   12: astore_3
    //   13: aload_1
    //   14: aload_2
    //   15: invokestatic 130	java/util/ServiceLoader:load	(Ljava/lang/Class;Ljava/lang/ClassLoader;)Ljava/util/ServiceLoader;
    //   18: checkcast 132	java/lang/Iterable
    //   21: invokestatic 138	kotlin/collections/CollectionsKt:toList	(Ljava/lang/Iterable;)Ljava/util/List;
    //   24: astore_1
    //   25: aload_1
    //   26: areturn
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	27	0	this	FastServiceLoader
    //   0	27	1	paramClass	Class<S>
    //   0	27	2	paramClassLoader	ClassLoader
    //   6	2	3	localList	List
    //   12	1	3	localObject	Object
    // Exception table:
    //   from	to	target	type
    //   0	7	12	finally
  }
  
  private final List<String> parse(URL paramURL)
  {
    Object localObject1 = paramURL.toString();
    if (StringsKt.startsWith$default((String)localObject1, "jar", false, 2, null))
    {
      paramURL = StringsKt.substringAfter$default((String)localObject1, "jar:file:", null, 2, null);
      Log5ECF72.a(paramURL);
      LogE84000.a(paramURL);
      Log229316.a(paramURL);
      paramURL = StringsKt.substringBefore$default(paramURL, '!', null, 2, null);
      Log5ECF72.a(paramURL);
      LogE84000.a(paramURL);
      Log229316.a(paramURL);
      localObject1 = StringsKt.substringAfter$default((String)localObject1, "!/", null, 2, null);
      Log5ECF72.a(localObject1);
      LogE84000.a(localObject1);
      Log229316.a(localObject1);
      paramURL = new JarFile(paramURL, false);
      try
      {
        BufferedReader localBufferedReader = new java/io/BufferedReader;
        Object localObject3 = new java/io/InputStreamReader;
        ZipEntry localZipEntry = new java/util/zip/ZipEntry;
        localZipEntry.<init>((String)localObject1);
        ((InputStreamReader)localObject3).<init>(paramURL.getInputStream(localZipEntry), "UTF-8");
        localBufferedReader.<init>((Reader)localObject3);
        localObject1 = (Closeable)localBufferedReader;
        try
        {
          localObject3 = (BufferedReader)localObject1;
          localObject3 = INSTANCE.parseFile((BufferedReader)localObject3);
          CloseableKt.closeFinally((Closeable)localObject1, null);
          try
          {
            paramURL.close();
            return (List<String>)localObject3;
          }
          finally {}
          localThrowable1 = finally;
        }
        finally {}
        paramURL = (Closeable)new BufferedReader((Reader)new InputStreamReader(paramURL.openStream()));
      }
      finally
      {
        try
        {
          throw localThrowable1;
        }
        finally {}
      }
    }
    try
    {
      Object localObject2 = (BufferedReader)paramURL;
      localObject2 = INSTANCE.parseFile((BufferedReader)localObject2);
      CloseableKt.closeFinally(paramURL, null);
      return (List<String>)localObject2;
    }
    finally
    {
      try
      {
        throw localThrowable2;
      }
      finally
      {
        CloseableKt.closeFinally(paramURL, localThrowable2);
      }
    }
  }
  
  private final List<String> parseFile(BufferedReader paramBufferedReader)
  {
    Set localSet = (Set)new LinkedHashSet();
    String str;
    for (;;)
    {
      str = paramBufferedReader.readLine();
      if (str == null) {
        return CollectionsKt.toList((Iterable)localSet);
      }
      str = StringsKt.substringBefore$default(str, "#", null, 2, null);
      Log5ECF72.a(str);
      LogE84000.a(str);
      Log229316.a(str);
      str = StringsKt.trim((CharSequence)str).toString();
      CharSequence localCharSequence = (CharSequence)str;
      int k = 0;
      int j = 0;
      while (j < localCharSequence.length())
      {
        char c = localCharSequence.charAt(j);
        j++;
        if ((c != '.') && (!Character.isJavaIdentifierPart(c))) {
          i = 0;
        } else {
          i = 1;
        }
        if (i == 0)
        {
          i = 0;
          break label144;
        }
      }
      int i = 1;
      label144:
      if (i == 0) {
        break;
      }
      i = k;
      if (((CharSequence)str).length() > 0) {
        i = 1;
      }
      if (i != 0) {
        localSet.add(str);
      }
    }
    paramBufferedReader = Intrinsics.stringPlus("Illegal service provider class name: ", str);
    Log5ECF72.a(paramBufferedReader);
    LogE84000.a(paramBufferedReader);
    Log229316.a(paramBufferedReader);
    throw new IllegalArgumentException(paramBufferedReader.toString());
  }
  
  /* Error */
  private final <R> R use(JarFile paramJarFile, kotlin.jvm.functions.Function1<? super JarFile, ? extends R> paramFunction1)
  {
    // Byte code:
    //   0: aload_2
    //   1: aload_1
    //   2: invokeinterface 279 2 0
    //   7: astore_2
    //   8: iconst_1
    //   9: invokestatic 285	kotlin/jvm/internal/InlineMarker:finallyStart	(I)V
    //   12: aload_1
    //   13: invokevirtual 215	java/util/jar/JarFile:close	()V
    //   16: iconst_1
    //   17: invokestatic 288	kotlin/jvm/internal/InlineMarker:finallyEnd	(I)V
    //   20: aload_2
    //   21: areturn
    //   22: astore_1
    //   23: aload_1
    //   24: athrow
    //   25: astore_2
    //   26: aload_2
    //   27: athrow
    //   28: astore_3
    //   29: iconst_1
    //   30: invokestatic 285	kotlin/jvm/internal/InlineMarker:finallyStart	(I)V
    //   33: aload_1
    //   34: invokevirtual 215	java/util/jar/JarFile:close	()V
    //   37: iconst_1
    //   38: invokestatic 288	kotlin/jvm/internal/InlineMarker:finallyEnd	(I)V
    //   41: aload_3
    //   42: athrow
    //   43: astore_1
    //   44: aload_2
    //   45: aload_1
    //   46: invokestatic 221	kotlin/ExceptionsKt:addSuppressed	(Ljava/lang/Throwable;Ljava/lang/Throwable;)V
    //   49: aload_2
    //   50: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	51	0	this	FastServiceLoader
    //   0	51	1	paramJarFile	JarFile
    //   0	51	2	paramFunction1	kotlin.jvm.functions.Function1<? super JarFile, ? extends R>
    //   28	14	3	localObject	Object
    // Exception table:
    //   from	to	target	type
    //   12	16	22	finally
    //   0	8	25	finally
    //   26	28	28	finally
    //   33	37	43	finally
  }
  
  public final List<MainDispatcherFactory> loadMainDispatcherFactory$kotlinx_coroutines_core()
  {
    if (!FastServiceLoaderKt.getANDROID_DETECTED()) {
      return load(MainDispatcherFactory.class, MainDispatcherFactory.class.getClassLoader());
    }
    List localList;
    try
    {
      ArrayList localArrayList = new java/util/ArrayList;
      localArrayList.<init>(2);
      Object localObject3 = null;
      MainDispatcherFactory localMainDispatcherFactory2;
      try
      {
        MainDispatcherFactory localMainDispatcherFactory1 = (MainDispatcherFactory)MainDispatcherFactory.class.cast(Class.forName("kotlinx.coroutines.android.AndroidDispatcherFactory", true, MainDispatcherFactory.class.getClassLoader()).getDeclaredConstructor(new Class[0]).newInstance(new Object[0]));
      }
      catch (ClassNotFoundException localClassNotFoundException1)
      {
        localMainDispatcherFactory2 = (MainDispatcherFactory)null;
        localMainDispatcherFactory2 = null;
      }
      if (localMainDispatcherFactory2 != null) {
        localArrayList.add(localMainDispatcherFactory2);
      }
      try
      {
        localMainDispatcherFactory2 = (MainDispatcherFactory)MainDispatcherFactory.class.cast(Class.forName("kotlinx.coroutines.test.internal.TestMainDispatcherFactory", true, MainDispatcherFactory.class.getClassLoader()).getDeclaredConstructor(new Class[0]).newInstance(new Object[0]));
      }
      catch (ClassNotFoundException localClassNotFoundException2)
      {
        localObject1 = (MainDispatcherFactory)null;
        localObject1 = localObject3;
      }
      if (localObject1 != null) {
        localArrayList.add(localObject1);
      }
      Object localObject1 = (List)localArrayList;
    }
    finally
    {
      localList = load(MainDispatcherFactory.class, MainDispatcherFactory.class.getClassLoader());
    }
    return localList;
  }
  
  public final <S> List<S> loadProviders$kotlinx_coroutines_core(Class<S> paramClass, ClassLoader paramClassLoader)
  {
    Object localObject1 = Intrinsics.stringPlus("META-INF/services/", paramClass.getName());
    Log5ECF72.a(localObject1);
    LogE84000.a(localObject1);
    Log229316.a(localObject1);
    localObject1 = Collections.list(paramClassLoader.getResources((String)localObject1));
    Intrinsics.checkNotNullExpressionValue(localObject1, "list(this)");
    Object localObject2 = (Iterable)localObject1;
    localObject1 = (Collection)new ArrayList();
    Object localObject3 = ((Iterable)localObject2).iterator();
    while (((Iterator)localObject3).hasNext())
    {
      localObject2 = (URL)((Iterator)localObject3).next();
      CollectionsKt.addAll((Collection)localObject1, (Iterable)INSTANCE.parse((URL)localObject2));
    }
    localObject1 = (List)localObject1;
    localObject1 = CollectionsKt.toSet((Iterable)localObject1);
    if ((((Collection)localObject1).isEmpty() ^ true))
    {
      localObject2 = (Iterable)localObject1;
      localObject1 = (Collection)new ArrayList(CollectionsKt.collectionSizeOrDefault((Iterable)localObject2, 10));
      localObject2 = ((Iterable)localObject2).iterator();
      while (((Iterator)localObject2).hasNext())
      {
        localObject3 = (String)((Iterator)localObject2).next();
        ((Collection)localObject1).add(INSTANCE.getProviderInstance((String)localObject3, paramClassLoader, paramClass));
      }
      paramClass = (List)localObject1;
      return paramClass;
    }
    throw new IllegalArgumentException("No providers were loaded with FastServiceLoader".toString());
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/kotlinx/coroutines/internal/FastServiceLoader.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */