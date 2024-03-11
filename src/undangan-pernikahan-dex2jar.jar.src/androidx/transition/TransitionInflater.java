package androidx.transition;

import android.content.Context;
import android.content.res.Resources.NotFoundException;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.InflateException;
import android.view.ViewGroup;
import androidx.collection.ArrayMap;
import androidx.core.content.res.TypedArrayUtils;
import java.io.IOException;
import java.lang.reflect.Constructor;
import mt.Log229316;
import mt.Log5ECF72;
import mt.LogE84000;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

public class TransitionInflater
{
  private static final ArrayMap<String, Constructor<?>> CONSTRUCTORS = new ArrayMap();
  private static final Class<?>[] CONSTRUCTOR_SIGNATURE = { Context.class, AttributeSet.class };
  private final Context mContext;
  
  private TransitionInflater(Context paramContext)
  {
    this.mContext = paramContext;
  }
  
  private Object createCustom(AttributeSet paramAttributeSet, Class<?> paramClass, String paramString)
  {
    String str = paramAttributeSet.getAttributeValue(null, "class");
    if (str != null) {
      try
      {
        synchronized (CONSTRUCTORS)
        {
          Constructor localConstructor = (Constructor)???.get(str);
          paramString = localConstructor;
          if (localConstructor == null)
          {
            Class localClass = Class.forName(str, false, this.mContext.getClassLoader()).asSubclass(paramClass);
            paramString = localConstructor;
            if (localClass != null)
            {
              paramString = localClass.getConstructor(CONSTRUCTOR_SIGNATURE);
              paramString.setAccessible(true);
              ???.put(str, paramString);
            }
          }
          paramAttributeSet = paramString.newInstance(new Object[] { this.mContext, paramAttributeSet });
          return paramAttributeSet;
        }
        throw new InflateException(paramString + " tag must have a 'class' attribute");
      }
      catch (Exception paramAttributeSet)
      {
        throw new InflateException("Could not instantiate " + paramClass + " class " + str, paramAttributeSet);
      }
    }
  }
  
  private Transition createTransitionFromXml(XmlPullParser paramXmlPullParser, AttributeSet paramAttributeSet, Transition paramTransition)
    throws XmlPullParserException, IOException
  {
    Object localObject1 = null;
    int j = paramXmlPullParser.getDepth();
    TransitionSet localTransitionSet;
    if ((paramTransition instanceof TransitionSet)) {
      localTransitionSet = (TransitionSet)paramTransition;
    } else {
      localTransitionSet = null;
    }
    for (;;)
    {
      int i = paramXmlPullParser.next();
      if (((i == 3) && (paramXmlPullParser.getDepth() <= j)) || (i == 1)) {
        break label627;
      }
      if (i == 2)
      {
        Object localObject2 = paramXmlPullParser.getName();
        if ("fade".equals(localObject2))
        {
          localObject1 = new Fade(this.mContext, paramAttributeSet);
        }
        else if ("changeBounds".equals(localObject2))
        {
          localObject1 = new ChangeBounds(this.mContext, paramAttributeSet);
        }
        else if ("slide".equals(localObject2))
        {
          localObject1 = new Slide(this.mContext, paramAttributeSet);
        }
        else if ("explode".equals(localObject2))
        {
          localObject1 = new Explode(this.mContext, paramAttributeSet);
        }
        else if ("changeImageTransform".equals(localObject2))
        {
          localObject1 = new ChangeImageTransform(this.mContext, paramAttributeSet);
        }
        else if ("changeTransform".equals(localObject2))
        {
          localObject1 = new ChangeTransform(this.mContext, paramAttributeSet);
        }
        else if ("changeClipBounds".equals(localObject2))
        {
          localObject1 = new ChangeClipBounds(this.mContext, paramAttributeSet);
        }
        else if ("autoTransition".equals(localObject2))
        {
          localObject1 = new AutoTransition(this.mContext, paramAttributeSet);
        }
        else if ("changeScroll".equals(localObject2))
        {
          localObject1 = new ChangeScroll(this.mContext, paramAttributeSet);
        }
        else if ("transitionSet".equals(localObject2))
        {
          localObject1 = new TransitionSet(this.mContext, paramAttributeSet);
        }
        else if ("transition".equals(localObject2))
        {
          localObject1 = (Transition)createCustom(paramAttributeSet, Transition.class, "transition");
        }
        else if ("targets".equals(localObject2))
        {
          getTargetIds(paramXmlPullParser, paramAttributeSet, paramTransition);
        }
        else if ("arcMotion".equals(localObject2))
        {
          if (paramTransition != null) {
            paramTransition.setPathMotion(new ArcMotion(this.mContext, paramAttributeSet));
          } else {
            throw new RuntimeException("Invalid use of arcMotion element");
          }
        }
        else if ("pathMotion".equals(localObject2))
        {
          if (paramTransition != null) {
            paramTransition.setPathMotion((PathMotion)createCustom(paramAttributeSet, PathMotion.class, "pathMotion"));
          } else {
            throw new RuntimeException("Invalid use of pathMotion element");
          }
        }
        else
        {
          if (!"patternPathMotion".equals(localObject2)) {
            break label595;
          }
          if (paramTransition == null) {
            break;
          }
          paramTransition.setPathMotion(new PatternPathMotion(this.mContext, paramAttributeSet));
        }
        localObject2 = localObject1;
        if (localObject1 != null)
        {
          if (!paramXmlPullParser.isEmptyElementTag()) {
            createTransitionFromXml(paramXmlPullParser, paramAttributeSet, (Transition)localObject1);
          }
          if (localTransitionSet != null)
          {
            localTransitionSet.addTransition((Transition)localObject1);
            localObject2 = null;
          }
          else if (paramTransition == null)
          {
            localObject2 = localObject1;
          }
          else
          {
            throw new InflateException("Could not add transition to another transition.");
          }
        }
        localObject1 = localObject2;
      }
    }
    throw new RuntimeException("Invalid use of patternPathMotion element");
    label595:
    throw new RuntimeException("Unknown scene name: " + paramXmlPullParser.getName());
    label627:
    return (Transition)localObject1;
  }
  
  private TransitionManager createTransitionManagerFromXml(XmlPullParser paramXmlPullParser, AttributeSet paramAttributeSet, ViewGroup paramViewGroup)
    throws XmlPullParserException, IOException
  {
    int i = paramXmlPullParser.getDepth();
    TransitionManager localTransitionManager = null;
    for (;;)
    {
      int j = paramXmlPullParser.next();
      if (((j == 3) && (paramXmlPullParser.getDepth() <= i)) || (j == 1)) {
        break label140;
      }
      if (j == 2)
      {
        String str = paramXmlPullParser.getName();
        if (str.equals("transitionManager"))
        {
          localTransitionManager = new TransitionManager();
        }
        else
        {
          if ((!str.equals("transition")) || (localTransitionManager == null)) {
            break;
          }
          loadTransition(paramAttributeSet, paramXmlPullParser, paramViewGroup, localTransitionManager);
        }
      }
    }
    throw new RuntimeException("Unknown scene name: " + paramXmlPullParser.getName());
    label140:
    return localTransitionManager;
  }
  
  public static TransitionInflater from(Context paramContext)
  {
    return new TransitionInflater(paramContext);
  }
  
  private void getTargetIds(XmlPullParser paramXmlPullParser, AttributeSet paramAttributeSet, Transition paramTransition)
    throws XmlPullParserException, IOException
  {
    int i = paramXmlPullParser.getDepth();
    for (;;)
    {
      int j = paramXmlPullParser.next();
      if (((j == 3) && (paramXmlPullParser.getDepth() <= i)) || (j == 1)) {
        return;
      }
      if (j == 2) {
        if (paramXmlPullParser.getName().equals("target"))
        {
          TypedArray localTypedArray = this.mContext.obtainStyledAttributes(paramAttributeSet, Styleable.TRANSITION_TARGET);
          j = TypedArrayUtils.getNamedResourceId(localTypedArray, paramXmlPullParser, "targetId", 1, 0);
          Object localObject1;
          Object localObject2;
          if (j != 0)
          {
            paramTransition.addTarget(j);
          }
          else
          {
            j = TypedArrayUtils.getNamedResourceId(localTypedArray, paramXmlPullParser, "excludeId", 2, 0);
            if (j != 0)
            {
              paramTransition.excludeTarget(j, true);
            }
            else
            {
              localObject1 = TypedArrayUtils.getNamedString(localTypedArray, paramXmlPullParser, "targetName", 4);
              Log5ECF72.a(localObject1);
              LogE84000.a(localObject1);
              Log229316.a(localObject1);
              if (localObject1 != null)
              {
                paramTransition.addTarget((String)localObject1);
              }
              else
              {
                localObject1 = TypedArrayUtils.getNamedString(localTypedArray, paramXmlPullParser, "excludeName", 5);
                Log5ECF72.a(localObject1);
                LogE84000.a(localObject1);
                Log229316.a(localObject1);
                if (localObject1 != null)
                {
                  paramTransition.excludeTarget((String)localObject1, true);
                }
                else
                {
                  localObject2 = TypedArrayUtils.getNamedString(localTypedArray, paramXmlPullParser, "excludeClass", 3);
                  Log5ECF72.a(localObject2);
                  LogE84000.a(localObject2);
                  Log229316.a(localObject2);
                  if (localObject2 != null) {
                    localObject1 = localObject2;
                  }
                }
              }
            }
          }
          try
          {
            paramTransition.excludeTarget(Class.forName((String)localObject2), true);
            break label322;
            localObject1 = localObject2;
            String str = TypedArrayUtils.getNamedString(localTypedArray, paramXmlPullParser, "targetClass", 0);
            Log5ECF72.a(str);
            LogE84000.a(str);
            Log229316.a(str);
            localObject2 = str;
            if (str != null)
            {
              localObject1 = localObject2;
              paramTransition.addTarget(Class.forName((String)localObject2));
            }
            label322:
            localTypedArray.recycle();
          }
          catch (ClassNotFoundException paramXmlPullParser)
          {
            localTypedArray.recycle();
            throw new RuntimeException("Could not create " + (String)localObject1, paramXmlPullParser);
          }
        }
      }
    }
    throw new RuntimeException("Unknown scene name: " + paramXmlPullParser.getName());
  }
  
  private void loadTransition(AttributeSet paramAttributeSet, XmlPullParser paramXmlPullParser, ViewGroup paramViewGroup, TransitionManager paramTransitionManager)
    throws Resources.NotFoundException
  {
    TypedArray localTypedArray = this.mContext.obtainStyledAttributes(paramAttributeSet, Styleable.TRANSITION_MANAGER);
    int i = TypedArrayUtils.getNamedResourceId(localTypedArray, paramXmlPullParser, "transition", 2, -1);
    int j = TypedArrayUtils.getNamedResourceId(localTypedArray, paramXmlPullParser, "fromScene", 0, -1);
    Object localObject = null;
    if (j < 0) {
      paramAttributeSet = null;
    } else {
      paramAttributeSet = Scene.getSceneForLayout(paramViewGroup, j, this.mContext);
    }
    j = TypedArrayUtils.getNamedResourceId(localTypedArray, paramXmlPullParser, "toScene", 1, -1);
    if (j < 0) {
      paramXmlPullParser = (XmlPullParser)localObject;
    } else {
      paramXmlPullParser = Scene.getSceneForLayout(paramViewGroup, j, this.mContext);
    }
    if (i >= 0)
    {
      paramViewGroup = inflateTransition(i);
      if (paramViewGroup != null) {
        if (paramXmlPullParser != null)
        {
          if (paramAttributeSet == null) {
            paramTransitionManager.setTransition(paramXmlPullParser, paramViewGroup);
          } else {
            paramTransitionManager.setTransition(paramAttributeSet, paramXmlPullParser, paramViewGroup);
          }
        }
        else {
          throw new RuntimeException("No toScene for transition ID " + i);
        }
      }
    }
    localTypedArray.recycle();
  }
  
  /* Error */
  public Transition inflateTransition(int paramInt)
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 34	androidx/transition/TransitionInflater:mContext	Landroid/content/Context;
    //   4: invokevirtual 366	android/content/Context:getResources	()Landroid/content/res/Resources;
    //   7: iload_1
    //   8: invokevirtual 372	android/content/res/Resources:getXml	(I)Landroid/content/res/XmlResourceParser;
    //   11: astore_2
    //   12: aload_0
    //   13: aload_2
    //   14: aload_2
    //   15: invokestatic 378	android/util/Xml:asAttributeSet	(Lorg/xmlpull/v1/XmlPullParser;)Landroid/util/AttributeSet;
    //   18: aconst_null
    //   19: invokespecial 227	androidx/transition/TransitionInflater:createTransitionFromXml	(Lorg/xmlpull/v1/XmlPullParser;Landroid/util/AttributeSet;Landroidx/transition/Transition;)Landroidx/transition/Transition;
    //   22: astore_3
    //   23: aload_2
    //   24: invokeinterface 383 1 0
    //   29: aload_3
    //   30: areturn
    //   31: astore_3
    //   32: goto +74 -> 106
    //   35: astore 5
    //   37: new 81	android/view/InflateException
    //   40: astore_3
    //   41: new 83	java/lang/StringBuilder
    //   44: astore 4
    //   46: aload 4
    //   48: invokespecial 84	java/lang/StringBuilder:<init>	()V
    //   51: aload_3
    //   52: aload 4
    //   54: aload_2
    //   55: invokeinterface 386 1 0
    //   60: invokevirtual 90	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   63: ldc_w 388
    //   66: invokevirtual 90	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   69: aload 5
    //   71: invokevirtual 391	java/io/IOException:getMessage	()Ljava/lang/String;
    //   74: invokevirtual 90	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   77: invokevirtual 99	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   80: aload 5
    //   82: invokespecial 102	android/view/InflateException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   85: aload_3
    //   86: athrow
    //   87: astore 4
    //   89: new 81	android/view/InflateException
    //   92: astore_3
    //   93: aload_3
    //   94: aload 4
    //   96: invokevirtual 392	org/xmlpull/v1/XmlPullParserException:getMessage	()Ljava/lang/String;
    //   99: aload 4
    //   101: invokespecial 102	android/view/InflateException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   104: aload_3
    //   105: athrow
    //   106: aload_2
    //   107: invokeinterface 383 1 0
    //   112: aload_3
    //   113: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	114	0	this	TransitionInflater
    //   0	114	1	paramInt	int
    //   11	96	2	localXmlResourceParser	android.content.res.XmlResourceParser
    //   22	8	3	localTransition	Transition
    //   31	1	3	localObject	Object
    //   40	73	3	localInflateException	InflateException
    //   44	9	4	localStringBuilder	StringBuilder
    //   87	13	4	localXmlPullParserException	XmlPullParserException
    //   35	46	5	localIOException	IOException
    // Exception table:
    //   from	to	target	type
    //   12	23	31	finally
    //   37	87	31	finally
    //   89	106	31	finally
    //   12	23	35	java/io/IOException
    //   12	23	87	org/xmlpull/v1/XmlPullParserException
  }
  
  /* Error */
  public TransitionManager inflateTransitionManager(int paramInt, ViewGroup paramViewGroup)
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 34	androidx/transition/TransitionInflater:mContext	Landroid/content/Context;
    //   4: invokevirtual 366	android/content/Context:getResources	()Landroid/content/res/Resources;
    //   7: iload_1
    //   8: invokevirtual 372	android/content/res/Resources:getXml	(I)Landroid/content/res/XmlResourceParser;
    //   11: astore_3
    //   12: aload_0
    //   13: aload_3
    //   14: aload_3
    //   15: invokestatic 378	android/util/Xml:asAttributeSet	(Lorg/xmlpull/v1/XmlPullParser;)Landroid/util/AttributeSet;
    //   18: aload_2
    //   19: invokespecial 396	androidx/transition/TransitionInflater:createTransitionManagerFromXml	(Lorg/xmlpull/v1/XmlPullParser;Landroid/util/AttributeSet;Landroid/view/ViewGroup;)Landroidx/transition/TransitionManager;
    //   22: astore_2
    //   23: aload_3
    //   24: invokeinterface 383 1 0
    //   29: aload_2
    //   30: areturn
    //   31: astore_2
    //   32: goto +85 -> 117
    //   35: astore 5
    //   37: new 81	android/view/InflateException
    //   40: astore_2
    //   41: new 83	java/lang/StringBuilder
    //   44: astore 4
    //   46: aload 4
    //   48: invokespecial 84	java/lang/StringBuilder:<init>	()V
    //   51: aload_2
    //   52: aload 4
    //   54: aload_3
    //   55: invokeinterface 386 1 0
    //   60: invokevirtual 90	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   63: ldc_w 388
    //   66: invokevirtual 90	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   69: aload 5
    //   71: invokevirtual 391	java/io/IOException:getMessage	()Ljava/lang/String;
    //   74: invokevirtual 90	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   77: invokevirtual 99	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   80: invokespecial 107	android/view/InflateException:<init>	(Ljava/lang/String;)V
    //   83: aload_2
    //   84: aload 5
    //   86: invokevirtual 400	android/view/InflateException:initCause	(Ljava/lang/Throwable;)Ljava/lang/Throwable;
    //   89: pop
    //   90: aload_2
    //   91: athrow
    //   92: astore_2
    //   93: new 81	android/view/InflateException
    //   96: astore 4
    //   98: aload 4
    //   100: aload_2
    //   101: invokevirtual 392	org/xmlpull/v1/XmlPullParserException:getMessage	()Ljava/lang/String;
    //   104: invokespecial 107	android/view/InflateException:<init>	(Ljava/lang/String;)V
    //   107: aload 4
    //   109: aload_2
    //   110: invokevirtual 400	android/view/InflateException:initCause	(Ljava/lang/Throwable;)Ljava/lang/Throwable;
    //   113: pop
    //   114: aload 4
    //   116: athrow
    //   117: aload_3
    //   118: invokeinterface 383 1 0
    //   123: aload_2
    //   124: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	125	0	this	TransitionInflater
    //   0	125	1	paramInt	int
    //   0	125	2	paramViewGroup	ViewGroup
    //   11	107	3	localXmlResourceParser	android.content.res.XmlResourceParser
    //   44	71	4	localObject	Object
    //   35	50	5	localIOException	IOException
    // Exception table:
    //   from	to	target	type
    //   12	23	31	finally
    //   37	90	31	finally
    //   90	92	31	finally
    //   93	114	31	finally
    //   114	117	31	finally
    //   12	23	35	java/io/IOException
    //   12	23	92	org/xmlpull/v1/XmlPullParserException
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/transition/TransitionInflater.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */