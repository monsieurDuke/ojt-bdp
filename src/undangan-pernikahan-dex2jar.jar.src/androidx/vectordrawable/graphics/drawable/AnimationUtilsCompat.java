package androidx.vectordrawable.graphics.drawable;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.Resources.Theme;
import android.util.Xml;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.AnticipateOvershootInterpolator;
import android.view.animation.BounceInterpolator;
import android.view.animation.CycleInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.OvershootInterpolator;
import java.io.IOException;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

public class AnimationUtilsCompat
{
  private static Interpolator createInterpolatorFromXml(Context paramContext, Resources paramResources, Resources.Theme paramTheme, XmlPullParser paramXmlPullParser)
    throws XmlPullParserException, IOException
  {
    paramResources = null;
    int i = paramXmlPullParser.getDepth();
    for (;;)
    {
      int j = paramXmlPullParser.next();
      if (((j == 3) && (paramXmlPullParser.getDepth() <= i)) || (j == 1)) {
        return paramResources;
      }
      if (j == 2)
      {
        paramTheme = Xml.asAttributeSet(paramXmlPullParser);
        paramResources = paramXmlPullParser.getName();
        if (paramResources.equals("linearInterpolator"))
        {
          paramResources = new LinearInterpolator();
        }
        else if (paramResources.equals("accelerateInterpolator"))
        {
          paramResources = new AccelerateInterpolator(paramContext, paramTheme);
        }
        else if (paramResources.equals("decelerateInterpolator"))
        {
          paramResources = new DecelerateInterpolator(paramContext, paramTheme);
        }
        else if (paramResources.equals("accelerateDecelerateInterpolator"))
        {
          paramResources = new AccelerateDecelerateInterpolator();
        }
        else if (paramResources.equals("cycleInterpolator"))
        {
          paramResources = new CycleInterpolator(paramContext, paramTheme);
        }
        else if (paramResources.equals("anticipateInterpolator"))
        {
          paramResources = new AnticipateInterpolator(paramContext, paramTheme);
        }
        else if (paramResources.equals("overshootInterpolator"))
        {
          paramResources = new OvershootInterpolator(paramContext, paramTheme);
        }
        else if (paramResources.equals("anticipateOvershootInterpolator"))
        {
          paramResources = new AnticipateOvershootInterpolator(paramContext, paramTheme);
        }
        else if (paramResources.equals("bounceInterpolator"))
        {
          paramResources = new BounceInterpolator();
        }
        else
        {
          if (!paramResources.equals("pathInterpolator")) {
            break;
          }
          paramResources = new PathInterpolatorCompat(paramContext, paramTheme, paramXmlPullParser);
        }
      }
    }
    throw new RuntimeException("Unknown interpolator name: " + paramXmlPullParser.getName());
    return paramResources;
  }
  
  /* Error */
  public static Interpolator loadInterpolator(Context paramContext, int paramInt)
    throws android.content.res.Resources.NotFoundException
  {
    // Byte code:
    //   0: getstatic 122	android/os/Build$VERSION:SDK_INT	I
    //   3: bipush 21
    //   5: if_icmplt +9 -> 14
    //   8: aload_0
    //   9: iload_1
    //   10: invokestatic 126	android/view/animation/AnimationUtils:loadInterpolator	(Landroid/content/Context;I)Landroid/view/animation/Interpolator;
    //   13: areturn
    //   14: aconst_null
    //   15: astore_3
    //   16: aconst_null
    //   17: astore 4
    //   19: aconst_null
    //   20: astore_2
    //   21: iload_1
    //   22: ldc 127
    //   24: if_icmpne +38 -> 62
    //   27: new 129	androidx/interpolator/view/animation/FastOutLinearInInterpolator
    //   30: dup
    //   31: invokespecial 130	androidx/interpolator/view/animation/FastOutLinearInInterpolator:<init>	()V
    //   34: astore_0
    //   35: iconst_0
    //   36: ifeq +11 -> 47
    //   39: new 132	java/lang/NullPointerException
    //   42: dup
    //   43: invokespecial 133	java/lang/NullPointerException:<init>	()V
    //   46: athrow
    //   47: aload_0
    //   48: areturn
    //   49: astore_0
    //   50: goto +290 -> 340
    //   53: astore 4
    //   55: goto +112 -> 167
    //   58: astore_3
    //   59: goto +191 -> 250
    //   62: iload_1
    //   63: ldc -122
    //   65: if_icmpne +25 -> 90
    //   68: new 136	androidx/interpolator/view/animation/FastOutSlowInInterpolator
    //   71: dup
    //   72: invokespecial 137	androidx/interpolator/view/animation/FastOutSlowInInterpolator:<init>	()V
    //   75: astore_0
    //   76: iconst_0
    //   77: ifeq +11 -> 88
    //   80: new 132	java/lang/NullPointerException
    //   83: dup
    //   84: invokespecial 133	java/lang/NullPointerException:<init>	()V
    //   87: athrow
    //   88: aload_0
    //   89: areturn
    //   90: iload_1
    //   91: ldc -118
    //   93: if_icmpne +25 -> 118
    //   96: new 140	androidx/interpolator/view/animation/LinearOutSlowInInterpolator
    //   99: dup
    //   100: invokespecial 141	androidx/interpolator/view/animation/LinearOutSlowInInterpolator:<init>	()V
    //   103: astore_0
    //   104: iconst_0
    //   105: ifeq +11 -> 116
    //   108: new 132	java/lang/NullPointerException
    //   111: dup
    //   112: invokespecial 133	java/lang/NullPointerException:<init>	()V
    //   115: athrow
    //   116: aload_0
    //   117: areturn
    //   118: aload_0
    //   119: invokevirtual 147	android/content/Context:getResources	()Landroid/content/res/Resources;
    //   122: iload_1
    //   123: invokevirtual 153	android/content/res/Resources:getAnimation	(I)Landroid/content/res/XmlResourceParser;
    //   126: astore 5
    //   128: aload 5
    //   130: astore_2
    //   131: aload 5
    //   133: astore_3
    //   134: aload 5
    //   136: astore 4
    //   138: aload_0
    //   139: aload_0
    //   140: invokevirtual 147	android/content/Context:getResources	()Landroid/content/res/Resources;
    //   143: aload_0
    //   144: invokevirtual 157	android/content/Context:getTheme	()Landroid/content/res/Resources$Theme;
    //   147: aload 5
    //   149: invokestatic 159	androidx/vectordrawable/graphics/drawable/AnimationUtilsCompat:createInterpolatorFromXml	(Landroid/content/Context;Landroid/content/res/Resources;Landroid/content/res/Resources$Theme;Lorg/xmlpull/v1/XmlPullParser;)Landroid/view/animation/Interpolator;
    //   152: astore_0
    //   153: aload 5
    //   155: ifnull +10 -> 165
    //   158: aload 5
    //   160: invokeinterface 164 1 0
    //   165: aload_0
    //   166: areturn
    //   167: aload_3
    //   168: astore_2
    //   169: new 116	android/content/res/Resources$NotFoundException
    //   172: astore_0
    //   173: aload_3
    //   174: astore_2
    //   175: new 98	java/lang/StringBuilder
    //   178: astore 5
    //   180: aload_3
    //   181: astore_2
    //   182: aload 5
    //   184: invokespecial 99	java/lang/StringBuilder:<init>	()V
    //   187: aload_3
    //   188: astore_2
    //   189: aload 5
    //   191: ldc -90
    //   193: invokevirtual 105	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   196: astore 5
    //   198: aload_3
    //   199: astore_2
    //   200: iload_1
    //   201: invokestatic 172	java/lang/Integer:toHexString	(I)Ljava/lang/String;
    //   204: astore 6
    //   206: aload 6
    //   208: invokestatic 178	mt/Log5ECF72:a	(Ljava/lang/Object;)V
    //   211: aload 6
    //   213: invokestatic 181	mt/LogE84000:a	(Ljava/lang/Object;)V
    //   216: aload 6
    //   218: invokestatic 184	mt/Log229316:a	(Ljava/lang/Object;)V
    //   221: aload_3
    //   222: astore_2
    //   223: aload_0
    //   224: aload 5
    //   226: aload 6
    //   228: invokevirtual 105	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   231: invokevirtual 108	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   234: invokespecial 185	android/content/res/Resources$NotFoundException:<init>	(Ljava/lang/String;)V
    //   237: aload_3
    //   238: astore_2
    //   239: aload_0
    //   240: aload 4
    //   242: invokevirtual 189	android/content/res/Resources$NotFoundException:initCause	(Ljava/lang/Throwable;)Ljava/lang/Throwable;
    //   245: pop
    //   246: aload_3
    //   247: astore_2
    //   248: aload_0
    //   249: athrow
    //   250: aload 4
    //   252: astore_2
    //   253: new 116	android/content/res/Resources$NotFoundException
    //   256: astore_0
    //   257: aload 4
    //   259: astore_2
    //   260: new 98	java/lang/StringBuilder
    //   263: astore 5
    //   265: aload 4
    //   267: astore_2
    //   268: aload 5
    //   270: invokespecial 99	java/lang/StringBuilder:<init>	()V
    //   273: aload 4
    //   275: astore_2
    //   276: aload 5
    //   278: ldc -90
    //   280: invokevirtual 105	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   283: astore 5
    //   285: aload 4
    //   287: astore_2
    //   288: iload_1
    //   289: invokestatic 172	java/lang/Integer:toHexString	(I)Ljava/lang/String;
    //   292: astore 6
    //   294: aload 6
    //   296: invokestatic 178	mt/Log5ECF72:a	(Ljava/lang/Object;)V
    //   299: aload 6
    //   301: invokestatic 181	mt/LogE84000:a	(Ljava/lang/Object;)V
    //   304: aload 6
    //   306: invokestatic 184	mt/Log229316:a	(Ljava/lang/Object;)V
    //   309: aload 4
    //   311: astore_2
    //   312: aload_0
    //   313: aload 5
    //   315: aload 6
    //   317: invokevirtual 105	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   320: invokevirtual 108	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   323: invokespecial 185	android/content/res/Resources$NotFoundException:<init>	(Ljava/lang/String;)V
    //   326: aload 4
    //   328: astore_2
    //   329: aload_0
    //   330: aload_3
    //   331: invokevirtual 189	android/content/res/Resources$NotFoundException:initCause	(Ljava/lang/Throwable;)Ljava/lang/Throwable;
    //   334: pop
    //   335: aload 4
    //   337: astore_2
    //   338: aload_0
    //   339: athrow
    //   340: aload_2
    //   341: ifnull +9 -> 350
    //   344: aload_2
    //   345: invokeinterface 164 1 0
    //   350: aload_0
    //   351: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	352	0	paramContext	Context
    //   0	352	1	paramInt	int
    //   20	325	2	localObject1	Object
    //   15	1	3	localObject2	Object
    //   58	1	3	localXmlPullParserException	XmlPullParserException
    //   133	198	3	localObject3	Object
    //   17	1	4	localObject4	Object
    //   53	1	4	localIOException	IOException
    //   136	200	4	localObject5	Object
    //   126	188	5	localObject6	Object
    //   204	112	6	str	String
    // Exception table:
    //   from	to	target	type
    //   27	35	49	finally
    //   68	76	49	finally
    //   96	104	49	finally
    //   118	128	49	finally
    //   138	153	49	finally
    //   169	173	49	finally
    //   175	180	49	finally
    //   182	187	49	finally
    //   189	198	49	finally
    //   200	206	49	finally
    //   223	237	49	finally
    //   239	246	49	finally
    //   248	250	49	finally
    //   253	257	49	finally
    //   260	265	49	finally
    //   268	273	49	finally
    //   276	285	49	finally
    //   288	294	49	finally
    //   312	326	49	finally
    //   329	335	49	finally
    //   338	340	49	finally
    //   27	35	53	java/io/IOException
    //   68	76	53	java/io/IOException
    //   96	104	53	java/io/IOException
    //   118	128	53	java/io/IOException
    //   138	153	53	java/io/IOException
    //   27	35	58	org/xmlpull/v1/XmlPullParserException
    //   68	76	58	org/xmlpull/v1/XmlPullParserException
    //   96	104	58	org/xmlpull/v1/XmlPullParserException
    //   118	128	58	org/xmlpull/v1/XmlPullParserException
    //   138	153	58	org/xmlpull/v1/XmlPullParserException
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/vectordrawable/graphics/drawable/AnimationUtilsCompat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */