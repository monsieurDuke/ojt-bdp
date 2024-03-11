package androidx.core.content.pm;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.XmlResourceParser;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import mt.Log229316;
import mt.Log5ECF72;
import mt.LogE84000;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

public class ShortcutXmlParser
{
  private static final String ATTR_SHORTCUT_ID = "shortcutId";
  private static final Object GET_INSTANCE_LOCK = new Object();
  private static final String META_DATA_APP_SHORTCUTS = "android.app.shortcuts";
  private static final String TAG = "ShortcutXmlParser";
  private static final String TAG_SHORTCUT = "shortcut";
  private static volatile ArrayList<String> sShortcutIds;
  
  private static String getAttributeValue(XmlPullParser paramXmlPullParser, String paramString)
  {
    String str2 = paramXmlPullParser.getAttributeValue("http://schemas.android.com/apk/res/android", paramString);
    String str1 = str2;
    if (str2 == null) {
      str1 = paramXmlPullParser.getAttributeValue(null, paramString);
    }
    return str1;
  }
  
  public static List<String> getShortcutIds(Context paramContext)
  {
    if (sShortcutIds == null) {
      synchronized (GET_INSTANCE_LOCK)
      {
        if (sShortcutIds == null)
        {
          ArrayList localArrayList = new java/util/ArrayList;
          localArrayList.<init>();
          sShortcutIds = localArrayList;
          sShortcutIds.addAll(parseShortcutIds(paramContext));
        }
      }
    }
    return sShortcutIds;
  }
  
  private static XmlResourceParser getXmlResourceParser(Context paramContext, ActivityInfo paramActivityInfo)
  {
    paramContext = paramActivityInfo.loadXmlMetaData(paramContext.getPackageManager(), "android.app.shortcuts");
    if (paramContext != null) {
      return paramContext;
    }
    throw new IllegalArgumentException("Failed to open android.app.shortcuts meta-data resource of " + paramActivityInfo.name);
  }
  
  public static List<String> parseShortcutIds(XmlPullParser paramXmlPullParser)
    throws IOException, XmlPullParserException
  {
    ArrayList localArrayList = new ArrayList(1);
    for (;;)
    {
      int j = paramXmlPullParser.next();
      if ((j == 1) || ((j == 3) && (paramXmlPullParser.getDepth() <= 0))) {
        break;
      }
      int i = paramXmlPullParser.getDepth();
      String str = paramXmlPullParser.getName();
      if ((j == 2) && (i == 2) && ("shortcut".equals(str)))
      {
        str = getAttributeValue(paramXmlPullParser, "shortcutId");
        Log5ECF72.a(str);
        LogE84000.a(str);
        Log229316.a(str);
        if (str != null) {
          localArrayList.add(str);
        }
      }
    }
    return localArrayList;
  }
  
  /* Error */
  private static java.util.Set<String> parseShortcutIds(Context paramContext)
  {
    // Byte code:
    //   0: new 141	java/util/HashSet
    //   3: dup
    //   4: invokespecial 142	java/util/HashSet:<init>	()V
    //   7: astore_1
    //   8: new 144	android/content/Intent
    //   11: dup
    //   12: ldc -110
    //   14: invokespecial 147	android/content/Intent:<init>	(Ljava/lang/String;)V
    //   17: astore_2
    //   18: aload_2
    //   19: ldc -107
    //   21: invokevirtual 153	android/content/Intent:addCategory	(Ljava/lang/String;)Landroid/content/Intent;
    //   24: pop
    //   25: aload_2
    //   26: aload_0
    //   27: invokevirtual 156	android/content/Context:getPackageName	()Ljava/lang/String;
    //   30: invokevirtual 159	android/content/Intent:setPackage	(Ljava/lang/String;)Landroid/content/Intent;
    //   33: pop
    //   34: aload_0
    //   35: invokevirtual 64	android/content/Context:getPackageManager	()Landroid/content/pm/PackageManager;
    //   38: aload_2
    //   39: sipush 128
    //   42: invokevirtual 165	android/content/pm/PackageManager:queryIntentActivities	(Landroid/content/Intent;I)Ljava/util/List;
    //   45: astore_2
    //   46: aload_2
    //   47: ifnull +135 -> 182
    //   50: aload_2
    //   51: invokeinterface 168 1 0
    //   56: ifne +6 -> 62
    //   59: goto +123 -> 182
    //   62: aload_2
    //   63: invokeinterface 172 1 0
    //   68: astore_2
    //   69: aload_2
    //   70: invokeinterface 178 1 0
    //   75: ifeq +92 -> 167
    //   78: aload_2
    //   79: invokeinterface 181 1 0
    //   84: checkcast 183	android/content/pm/ResolveInfo
    //   87: getfield 187	android/content/pm/ResolveInfo:activityInfo	Landroid/content/pm/ActivityInfo;
    //   90: astore_3
    //   91: aload_3
    //   92: getfield 191	android/content/pm/ActivityInfo:metaData	Landroid/os/Bundle;
    //   95: astore 4
    //   97: aload 4
    //   99: ifnull +65 -> 164
    //   102: aload 4
    //   104: ldc 13
    //   106: invokevirtual 197	android/os/Bundle:containsKey	(Ljava/lang/String;)Z
    //   109: ifeq +55 -> 164
    //   112: aload_0
    //   113: aload_3
    //   114: invokestatic 199	androidx/core/content/pm/ShortcutXmlParser:getXmlResourceParser	(Landroid/content/Context;Landroid/content/pm/ActivityInfo;)Landroid/content/res/XmlResourceParser;
    //   117: astore_3
    //   118: aload_1
    //   119: aload_3
    //   120: invokestatic 201	androidx/core/content/pm/ShortcutXmlParser:parseShortcutIds	(Lorg/xmlpull/v1/XmlPullParser;)Ljava/util/List;
    //   123: invokeinterface 204 2 0
    //   128: pop
    //   129: aload_3
    //   130: ifnull +34 -> 164
    //   133: aload_3
    //   134: invokeinterface 209 1 0
    //   139: goto +25 -> 164
    //   142: astore_0
    //   143: aload_3
    //   144: ifnull +18 -> 162
    //   147: aload_3
    //   148: invokeinterface 209 1 0
    //   153: goto +9 -> 162
    //   156: astore_2
    //   157: aload_0
    //   158: aload_2
    //   159: invokevirtual 215	java/lang/Throwable:addSuppressed	(Ljava/lang/Throwable;)V
    //   162: aload_0
    //   163: athrow
    //   164: goto -95 -> 69
    //   167: goto +13 -> 180
    //   170: astore_0
    //   171: ldc 16
    //   173: ldc -39
    //   175: aload_0
    //   176: invokestatic 223	android/util/Log:e	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I
    //   179: pop
    //   180: aload_1
    //   181: areturn
    //   182: aload_1
    //   183: areturn
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	184	0	paramContext	Context
    //   7	176	1	localHashSet	java.util.HashSet
    //   17	62	2	localObject1	Object
    //   156	3	2	localThrowable	Throwable
    //   90	58	3	localObject2	Object
    //   95	8	4	localBundle	android.os.Bundle
    // Exception table:
    //   from	to	target	type
    //   118	129	142	finally
    //   147	153	156	finally
    //   62	69	170	java/lang/Exception
    //   69	97	170	java/lang/Exception
    //   102	118	170	java/lang/Exception
    //   133	139	170	java/lang/Exception
    //   157	162	170	java/lang/Exception
    //   162	164	170	java/lang/Exception
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/core/content/pm/ShortcutXmlParser.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */