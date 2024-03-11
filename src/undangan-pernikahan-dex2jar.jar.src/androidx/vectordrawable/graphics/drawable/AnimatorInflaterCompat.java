package androidx.vectordrawable.graphics.drawable;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.animation.Keyframe;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.Resources.NotFoundException;
import android.content.res.Resources.Theme;
import android.content.res.TypedArray;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.os.Build.VERSION;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.util.Xml;
import android.view.InflateException;
import androidx.core.content.res.TypedArrayUtils;
import androidx.core.graphics.PathParser;
import androidx.core.graphics.PathParser.PathDataNode;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import mt.Log229316;
import mt.Log5ECF72;
import mt.LogE84000;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

public class AnimatorInflaterCompat
{
  private static final boolean DBG_ANIMATOR_INFLATER = false;
  private static final int MAX_NUM_POINTS = 100;
  private static final String TAG = "AnimatorInflater";
  private static final int TOGETHER = 0;
  private static final int VALUE_TYPE_COLOR = 3;
  private static final int VALUE_TYPE_FLOAT = 0;
  private static final int VALUE_TYPE_INT = 1;
  private static final int VALUE_TYPE_PATH = 2;
  private static final int VALUE_TYPE_UNDEFINED = 4;
  
  private static Animator createAnimatorFromXml(Context paramContext, Resources paramResources, Resources.Theme paramTheme, XmlPullParser paramXmlPullParser, float paramFloat)
    throws XmlPullParserException, IOException
  {
    return createAnimatorFromXml(paramContext, paramResources, paramTheme, paramXmlPullParser, Xml.asAttributeSet(paramXmlPullParser), null, 0, paramFloat);
  }
  
  private static Animator createAnimatorFromXml(Context paramContext, Resources paramResources, Resources.Theme paramTheme, XmlPullParser paramXmlPullParser, AttributeSet paramAttributeSet, AnimatorSet paramAnimatorSet, int paramInt, float paramFloat)
    throws XmlPullParserException, IOException
  {
    int j = paramXmlPullParser.getDepth();
    Object localObject1 = null;
    Object localObject2 = null;
    int i;
    for (;;)
    {
      i = paramXmlPullParser.next();
      if ((i == 3) && (paramXmlPullParser.getDepth() <= j)) {
        break label326;
      }
      if (i == 1) {
        break label326;
      }
      if (i == 2)
      {
        Object localObject3 = paramXmlPullParser.getName();
        i = 0;
        if (((String)localObject3).equals("objectAnimator"))
        {
          localObject1 = loadObjectAnimator(paramContext, paramResources, paramTheme, paramAttributeSet, paramFloat, paramXmlPullParser);
        }
        else if (((String)localObject3).equals("animator"))
        {
          localObject1 = loadAnimator(paramContext, paramResources, paramTheme, paramAttributeSet, null, paramFloat, paramXmlPullParser);
        }
        else if (((String)localObject3).equals("set"))
        {
          localObject1 = new AnimatorSet();
          localObject3 = TypedArrayUtils.obtainAttributes(paramResources, paramTheme, paramAttributeSet, AndroidResources.STYLEABLE_ANIMATOR_SET);
          int k = TypedArrayUtils.getNamedInt((TypedArray)localObject3, paramXmlPullParser, "ordering", 0, 0);
          createAnimatorFromXml(paramContext, paramResources, paramTheme, paramXmlPullParser, paramAttributeSet, (AnimatorSet)localObject1, k, paramFloat);
          ((TypedArray)localObject3).recycle();
        }
        else
        {
          if (!((String)localObject3).equals("propertyValuesHolder")) {
            break;
          }
          localObject3 = loadValues(paramContext, paramResources, paramTheme, paramXmlPullParser, Xml.asAttributeSet(paramXmlPullParser));
          if ((localObject3 != null) && ((localObject1 instanceof ValueAnimator))) {
            ((ValueAnimator)localObject1).setValues((PropertyValuesHolder[])localObject3);
          }
          i = 1;
        }
        localObject3 = localObject2;
        if (paramAnimatorSet != null)
        {
          localObject3 = localObject2;
          if (i == 0)
          {
            localObject3 = localObject2;
            if (localObject2 == null) {
              localObject3 = new ArrayList();
            }
            ((ArrayList)localObject3).add(localObject1);
          }
        }
        localObject2 = localObject3;
      }
    }
    throw new RuntimeException("Unknown animator name: " + paramXmlPullParser.getName());
    label326:
    if ((paramAnimatorSet != null) && (localObject2 != null))
    {
      paramContext = new Animator[((ArrayList)localObject2).size()];
      i = 0;
      paramResources = ((ArrayList)localObject2).iterator();
      while (paramResources.hasNext())
      {
        paramContext[i] = ((Animator)paramResources.next());
        i++;
      }
      if (paramInt == 0) {
        paramAnimatorSet.playTogether(paramContext);
      } else {
        paramAnimatorSet.playSequentially(paramContext);
      }
    }
    return (Animator)localObject1;
  }
  
  private static Keyframe createNewKeyframe(Keyframe paramKeyframe, float paramFloat)
  {
    if (paramKeyframe.getType() == Float.TYPE) {
      paramKeyframe = Keyframe.ofFloat(paramFloat);
    } else if (paramKeyframe.getType() == Integer.TYPE) {
      paramKeyframe = Keyframe.ofInt(paramFloat);
    } else {
      paramKeyframe = Keyframe.ofObject(paramFloat);
    }
    return paramKeyframe;
  }
  
  private static void distributeKeyframes(Keyframe[] paramArrayOfKeyframe, float paramFloat, int paramInt1, int paramInt2)
  {
    paramFloat /= (paramInt2 - paramInt1 + 2);
    while (paramInt1 <= paramInt2)
    {
      paramArrayOfKeyframe[paramInt1].setFraction(paramArrayOfKeyframe[(paramInt1 - 1)].getFraction() + paramFloat);
      paramInt1++;
    }
  }
  
  private static void dumpKeyframes(Object[] paramArrayOfObject, String paramString)
  {
    if ((paramArrayOfObject != null) && (paramArrayOfObject.length != 0))
    {
      Log.d("AnimatorInflater", paramString);
      int j = paramArrayOfObject.length;
      for (int i = 0; i < j; i++)
      {
        Keyframe localKeyframe = (Keyframe)paramArrayOfObject[i];
        StringBuilder localStringBuilder = new StringBuilder().append("Keyframe ").append(i).append(": fraction ");
        float f = localKeyframe.getFraction();
        String str = "null";
        if (f < 0.0F) {
          paramString = "null";
        } else {
          paramString = Float.valueOf(localKeyframe.getFraction());
        }
        localStringBuilder = localStringBuilder.append(paramString).append(", , value : ");
        paramString = str;
        if (localKeyframe.hasValue()) {
          paramString = localKeyframe.getValue();
        }
        Log.d("AnimatorInflater", paramString);
      }
      return;
    }
  }
  
  private static PropertyValuesHolder getPVH(TypedArray paramTypedArray, int paramInt1, int paramInt2, int paramInt3, String paramString)
  {
    Object localObject = paramTypedArray.peekValue(paramInt2);
    int i;
    if (localObject != null) {
      i = 1;
    } else {
      i = 0;
    }
    int m;
    if (i != 0) {
      m = ((TypedValue)localObject).type;
    } else {
      m = 0;
    }
    localObject = paramTypedArray.peekValue(paramInt3);
    int j;
    if (localObject != null) {
      j = 1;
    } else {
      j = 0;
    }
    int k;
    if (j != 0) {
      k = ((TypedValue)localObject).type;
    } else {
      k = 0;
    }
    if (paramInt1 == 4) {
      if (((i != 0) && (isColorType(m))) || ((j != 0) && (isColorType(k)))) {
        paramInt1 = 3;
      } else {
        paramInt1 = 0;
      }
    }
    int n;
    if (paramInt1 == 0) {
      n = 1;
    } else {
      n = 0;
    }
    if (paramInt1 == 2)
    {
      localObject = paramTypedArray.getString(paramInt2);
      paramTypedArray = paramTypedArray.getString(paramInt3);
      PathParser.PathDataNode[] arrayOfPathDataNode1 = PathParser.createNodesFromPathData((String)localObject);
      PathParser.PathDataNode[] arrayOfPathDataNode2 = PathParser.createNodesFromPathData(paramTypedArray);
      if ((arrayOfPathDataNode1 == null) && (arrayOfPathDataNode2 == null)) {
        break label322;
      }
      if (arrayOfPathDataNode1 != null)
      {
        PathDataEvaluator localPathDataEvaluator = new PathDataEvaluator();
        if (arrayOfPathDataNode2 != null)
        {
          if (PathParser.canMorph(arrayOfPathDataNode1, arrayOfPathDataNode2)) {
            paramTypedArray = PropertyValuesHolder.ofObject(paramString, localPathDataEvaluator, new Object[] { arrayOfPathDataNode1, arrayOfPathDataNode2 });
          } else {
            throw new InflateException(" Can't morph from " + (String)localObject + " to " + paramTypedArray);
          }
        }
        else {
          paramTypedArray = PropertyValuesHolder.ofObject(paramString, localPathDataEvaluator, new Object[] { arrayOfPathDataNode1 });
        }
      }
      else if (arrayOfPathDataNode2 != null)
      {
        paramTypedArray = PropertyValuesHolder.ofObject(paramString, new PathDataEvaluator(), new Object[] { arrayOfPathDataNode2 });
      }
      else
      {
        label322:
        paramTypedArray = null;
      }
      paramString = paramTypedArray;
    }
    else
    {
      localObject = null;
      if (paramInt1 == 3) {
        localObject = ArgbEvaluator.getInstance();
      }
      if (n != 0)
      {
        float f1;
        if (i != 0)
        {
          if (m == 5) {
            f1 = paramTypedArray.getDimension(paramInt2, 0.0F);
          } else {
            f1 = paramTypedArray.getFloat(paramInt2, 0.0F);
          }
          if (j != 0)
          {
            float f2;
            if (k == 5) {
              f2 = paramTypedArray.getDimension(paramInt3, 0.0F);
            } else {
              f2 = paramTypedArray.getFloat(paramInt3, 0.0F);
            }
            paramTypedArray = PropertyValuesHolder.ofFloat(paramString, new float[] { f1, f2 });
          }
          else
          {
            paramTypedArray = PropertyValuesHolder.ofFloat(paramString, new float[] { f1 });
          }
        }
        else
        {
          if (k == 5) {
            f1 = paramTypedArray.getDimension(paramInt3, 0.0F);
          } else {
            f1 = paramTypedArray.getFloat(paramInt3, 0.0F);
          }
          paramTypedArray = PropertyValuesHolder.ofFloat(paramString, new float[] { f1 });
        }
      }
      else if (i != 0)
      {
        if (m == 5) {
          paramInt1 = (int)paramTypedArray.getDimension(paramInt2, 0.0F);
        } else if (isColorType(m)) {
          paramInt1 = paramTypedArray.getColor(paramInt2, 0);
        } else {
          paramInt1 = paramTypedArray.getInt(paramInt2, 0);
        }
        if (j != 0)
        {
          if (k == 5) {
            paramInt2 = (int)paramTypedArray.getDimension(paramInt3, 0.0F);
          } else if (isColorType(k)) {
            paramInt2 = paramTypedArray.getColor(paramInt3, 0);
          } else {
            paramInt2 = paramTypedArray.getInt(paramInt3, 0);
          }
          paramTypedArray = PropertyValuesHolder.ofInt(paramString, new int[] { paramInt1, paramInt2 });
        }
        else
        {
          paramTypedArray = PropertyValuesHolder.ofInt(paramString, new int[] { paramInt1 });
        }
      }
      else if (j != 0)
      {
        if (k == 5) {
          paramInt1 = (int)paramTypedArray.getDimension(paramInt3, 0.0F);
        } else if (isColorType(k)) {
          paramInt1 = paramTypedArray.getColor(paramInt3, 0);
        } else {
          paramInt1 = paramTypedArray.getInt(paramInt3, 0);
        }
        paramTypedArray = PropertyValuesHolder.ofInt(paramString, new int[] { paramInt1 });
      }
      else
      {
        paramTypedArray = null;
      }
      paramString = paramTypedArray;
      if (paramTypedArray != null)
      {
        paramString = paramTypedArray;
        if (localObject != null)
        {
          paramTypedArray.setEvaluator((TypeEvaluator)localObject);
          paramString = paramTypedArray;
        }
      }
    }
    return paramString;
  }
  
  private static int inferValueTypeFromValues(TypedArray paramTypedArray, int paramInt1, int paramInt2)
  {
    TypedValue localTypedValue = paramTypedArray.peekValue(paramInt1);
    int k = 1;
    int j = 0;
    if (localTypedValue != null) {
      paramInt1 = 1;
    } else {
      paramInt1 = 0;
    }
    int i;
    if (paramInt1 != 0) {
      i = localTypedValue.type;
    } else {
      i = 0;
    }
    paramTypedArray = paramTypedArray.peekValue(paramInt2);
    if (paramTypedArray != null) {
      paramInt2 = k;
    } else {
      paramInt2 = 0;
    }
    if (paramInt2 != 0) {
      j = paramTypedArray.type;
    }
    if (((paramInt1 != 0) && (isColorType(i))) || ((paramInt2 != 0) && (isColorType(j)))) {
      paramInt1 = 3;
    } else {
      paramInt1 = 0;
    }
    return paramInt1;
  }
  
  private static int inferValueTypeOfKeyframe(Resources paramResources, Resources.Theme paramTheme, AttributeSet paramAttributeSet, XmlPullParser paramXmlPullParser)
  {
    paramResources = TypedArrayUtils.obtainAttributes(paramResources, paramTheme, paramAttributeSet, AndroidResources.STYLEABLE_KEYFRAME);
    int i = 0;
    paramTheme = TypedArrayUtils.peekNamedValue(paramResources, paramXmlPullParser, "value", 0);
    if (paramTheme != null) {
      i = 1;
    }
    if ((i != 0) && (isColorType(paramTheme.type))) {
      i = 3;
    } else {
      i = 0;
    }
    paramResources.recycle();
    return i;
  }
  
  private static boolean isColorType(int paramInt)
  {
    boolean bool;
    if ((paramInt >= 28) && (paramInt <= 31)) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public static Animator loadAnimator(Context paramContext, int paramInt)
    throws Resources.NotFoundException
  {
    if (Build.VERSION.SDK_INT >= 24) {
      paramContext = AnimatorInflater.loadAnimator(paramContext, paramInt);
    } else {
      paramContext = loadAnimator(paramContext, paramContext.getResources(), paramContext.getTheme(), paramInt);
    }
    return paramContext;
  }
  
  public static Animator loadAnimator(Context paramContext, Resources paramResources, Resources.Theme paramTheme, int paramInt)
    throws Resources.NotFoundException
  {
    return loadAnimator(paramContext, paramResources, paramTheme, paramInt, 1.0F);
  }
  
  /* Error */
  public static Animator loadAnimator(Context paramContext, Resources paramResources, Resources.Theme paramTheme, int paramInt, float paramFloat)
    throws Resources.NotFoundException
  {
    // Byte code:
    //   0: aconst_null
    //   1: astore 6
    //   3: aconst_null
    //   4: astore 7
    //   6: aconst_null
    //   7: astore 5
    //   9: aload_1
    //   10: iload_3
    //   11: invokevirtual 355	android/content/res/Resources:getAnimation	(I)Landroid/content/res/XmlResourceParser;
    //   14: astore 8
    //   16: aload 8
    //   18: astore 5
    //   20: aload 8
    //   22: astore 6
    //   24: aload 8
    //   26: astore 7
    //   28: aload_0
    //   29: aload_1
    //   30: aload_2
    //   31: aload 8
    //   33: fload 4
    //   35: invokestatic 357	androidx/vectordrawable/graphics/drawable/AnimatorInflaterCompat:createAnimatorFromXml	(Landroid/content/Context;Landroid/content/res/Resources;Landroid/content/res/Resources$Theme;Lorg/xmlpull/v1/XmlPullParser;F)Landroid/animation/Animator;
    //   38: astore_0
    //   39: aload 8
    //   41: ifnull +10 -> 51
    //   44: aload 8
    //   46: invokeinterface 362 1 0
    //   51: aload_0
    //   52: areturn
    //   53: astore_0
    //   54: goto +190 -> 244
    //   57: astore_1
    //   58: aload 6
    //   60: astore 5
    //   62: new 324	android/content/res/Resources$NotFoundException
    //   65: astore_0
    //   66: aload 6
    //   68: astore 5
    //   70: new 129	java/lang/StringBuilder
    //   73: astore_2
    //   74: aload 6
    //   76: astore 5
    //   78: aload_2
    //   79: invokespecial 130	java/lang/StringBuilder:<init>	()V
    //   82: aload 6
    //   84: astore 5
    //   86: aload_2
    //   87: ldc_w 364
    //   90: invokevirtual 136	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   93: astore 7
    //   95: aload 6
    //   97: astore 5
    //   99: iload_3
    //   100: invokestatic 367	java/lang/Integer:toHexString	(I)Ljava/lang/String;
    //   103: astore_2
    //   104: aload_2
    //   105: invokestatic 373	mt/Log5ECF72:a	(Ljava/lang/Object;)V
    //   108: aload_2
    //   109: invokestatic 376	mt/LogE84000:a	(Ljava/lang/Object;)V
    //   112: aload_2
    //   113: invokestatic 379	mt/Log229316:a	(Ljava/lang/Object;)V
    //   116: aload 6
    //   118: astore 5
    //   120: aload_0
    //   121: aload 7
    //   123: aload_2
    //   124: invokevirtual 136	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   127: invokevirtual 139	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   130: invokespecial 380	android/content/res/Resources$NotFoundException:<init>	(Ljava/lang/String;)V
    //   133: aload 6
    //   135: astore 5
    //   137: aload_0
    //   138: aload_1
    //   139: invokevirtual 384	android/content/res/Resources$NotFoundException:initCause	(Ljava/lang/Throwable;)Ljava/lang/Throwable;
    //   142: pop
    //   143: aload 6
    //   145: astore 5
    //   147: aload_0
    //   148: athrow
    //   149: astore_1
    //   150: aload 7
    //   152: astore 5
    //   154: new 324	android/content/res/Resources$NotFoundException
    //   157: astore_0
    //   158: aload 7
    //   160: astore 5
    //   162: new 129	java/lang/StringBuilder
    //   165: astore_2
    //   166: aload 7
    //   168: astore 5
    //   170: aload_2
    //   171: invokespecial 130	java/lang/StringBuilder:<init>	()V
    //   174: aload 7
    //   176: astore 5
    //   178: aload_2
    //   179: ldc_w 364
    //   182: invokevirtual 136	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   185: astore_2
    //   186: aload 7
    //   188: astore 5
    //   190: iload_3
    //   191: invokestatic 367	java/lang/Integer:toHexString	(I)Ljava/lang/String;
    //   194: astore 6
    //   196: aload 6
    //   198: invokestatic 373	mt/Log5ECF72:a	(Ljava/lang/Object;)V
    //   201: aload 6
    //   203: invokestatic 376	mt/LogE84000:a	(Ljava/lang/Object;)V
    //   206: aload 6
    //   208: invokestatic 379	mt/Log229316:a	(Ljava/lang/Object;)V
    //   211: aload 7
    //   213: astore 5
    //   215: aload_0
    //   216: aload_2
    //   217: aload 6
    //   219: invokevirtual 136	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   222: invokevirtual 139	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   225: invokespecial 380	android/content/res/Resources$NotFoundException:<init>	(Ljava/lang/String;)V
    //   228: aload 7
    //   230: astore 5
    //   232: aload_0
    //   233: aload_1
    //   234: invokevirtual 384	android/content/res/Resources$NotFoundException:initCause	(Ljava/lang/Throwable;)Ljava/lang/Throwable;
    //   237: pop
    //   238: aload 7
    //   240: astore 5
    //   242: aload_0
    //   243: athrow
    //   244: aload 5
    //   246: ifnull +10 -> 256
    //   249: aload 5
    //   251: invokeinterface 362 1 0
    //   256: aload_0
    //   257: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	258	0	paramContext	Context
    //   0	258	1	paramResources	Resources
    //   0	258	2	paramTheme	Resources.Theme
    //   0	258	3	paramInt	int
    //   0	258	4	paramFloat	float
    //   7	243	5	localObject1	Object
    //   1	217	6	localObject2	Object
    //   4	235	7	localObject3	Object
    //   14	31	8	localXmlResourceParser	android.content.res.XmlResourceParser
    // Exception table:
    //   from	to	target	type
    //   9	16	53	finally
    //   28	39	53	finally
    //   62	66	53	finally
    //   70	74	53	finally
    //   78	82	53	finally
    //   86	95	53	finally
    //   99	104	53	finally
    //   120	133	53	finally
    //   137	143	53	finally
    //   147	149	53	finally
    //   154	158	53	finally
    //   162	166	53	finally
    //   170	174	53	finally
    //   178	186	53	finally
    //   190	196	53	finally
    //   215	228	53	finally
    //   232	238	53	finally
    //   242	244	53	finally
    //   9	16	57	java/io/IOException
    //   28	39	57	java/io/IOException
    //   9	16	149	org/xmlpull/v1/XmlPullParserException
    //   28	39	149	org/xmlpull/v1/XmlPullParserException
  }
  
  private static ValueAnimator loadAnimator(Context paramContext, Resources paramResources, Resources.Theme paramTheme, AttributeSet paramAttributeSet, ValueAnimator paramValueAnimator, float paramFloat, XmlPullParser paramXmlPullParser)
    throws Resources.NotFoundException
  {
    TypedArray localTypedArray = TypedArrayUtils.obtainAttributes(paramResources, paramTheme, paramAttributeSet, AndroidResources.STYLEABLE_ANIMATOR);
    paramTheme = TypedArrayUtils.obtainAttributes(paramResources, paramTheme, paramAttributeSet, AndroidResources.STYLEABLE_PROPERTY_ANIMATOR);
    paramResources = paramValueAnimator;
    if (paramValueAnimator == null) {
      paramResources = new ValueAnimator();
    }
    parseAnimatorFromTypeArray(paramResources, localTypedArray, paramTheme, paramFloat, paramXmlPullParser);
    int i = TypedArrayUtils.getNamedResourceId(localTypedArray, paramXmlPullParser, "interpolator", 0, 0);
    if (i > 0) {
      paramResources.setInterpolator(AnimationUtilsCompat.loadInterpolator(paramContext, i));
    }
    localTypedArray.recycle();
    if (paramTheme != null) {
      paramTheme.recycle();
    }
    return paramResources;
  }
  
  private static Keyframe loadKeyframe(Context paramContext, Resources paramResources, Resources.Theme paramTheme, AttributeSet paramAttributeSet, int paramInt, XmlPullParser paramXmlPullParser)
    throws XmlPullParserException, IOException
  {
    paramTheme = TypedArrayUtils.obtainAttributes(paramResources, paramTheme, paramAttributeSet, AndroidResources.STYLEABLE_KEYFRAME);
    paramResources = null;
    float f = TypedArrayUtils.getNamedFloat(paramTheme, paramXmlPullParser, "fraction", 3, -1.0F);
    paramAttributeSet = TypedArrayUtils.peekNamedValue(paramTheme, paramXmlPullParser, "value", 0);
    int j;
    if (paramAttributeSet != null) {
      j = 1;
    } else {
      j = 0;
    }
    int i = paramInt;
    if (paramInt == 4) {
      if ((j != 0) && (isColorType(paramAttributeSet.type))) {
        i = 3;
      } else {
        i = 0;
      }
    }
    if (j != 0) {
      switch (i)
      {
      case 2: 
      default: 
        break;
      case 1: 
      case 3: 
        paramResources = Keyframe.ofInt(f, TypedArrayUtils.getNamedInt(paramTheme, paramXmlPullParser, "value", 0, 0));
        break;
      case 0: 
        paramResources = Keyframe.ofFloat(f, TypedArrayUtils.getNamedFloat(paramTheme, paramXmlPullParser, "value", 0, 0.0F));
      }
    } else if (i == 0) {
      paramResources = Keyframe.ofFloat(f);
    } else {
      paramResources = Keyframe.ofInt(f);
    }
    paramInt = TypedArrayUtils.getNamedResourceId(paramTheme, paramXmlPullParser, "interpolator", 1, 0);
    if (paramInt > 0) {
      paramResources.setInterpolator(AnimationUtilsCompat.loadInterpolator(paramContext, paramInt));
    }
    paramTheme.recycle();
    return paramResources;
  }
  
  private static ObjectAnimator loadObjectAnimator(Context paramContext, Resources paramResources, Resources.Theme paramTheme, AttributeSet paramAttributeSet, float paramFloat, XmlPullParser paramXmlPullParser)
    throws Resources.NotFoundException
  {
    ObjectAnimator localObjectAnimator = new ObjectAnimator();
    loadAnimator(paramContext, paramResources, paramTheme, paramAttributeSet, localObjectAnimator, paramFloat, paramXmlPullParser);
    return localObjectAnimator;
  }
  
  private static PropertyValuesHolder loadPvh(Context paramContext, Resources paramResources, Resources.Theme paramTheme, XmlPullParser paramXmlPullParser, String paramString, int paramInt)
    throws XmlPullParserException, IOException
  {
    int i2 = 0;
    Object localObject1 = null;
    int i = paramInt;
    int k;
    for (;;)
    {
      paramInt = paramXmlPullParser.next();
      k = paramInt;
      if ((paramInt == 3) || (k == 1)) {
        break;
      }
      if (paramXmlPullParser.getName().equals("keyframe"))
      {
        if (i == 4) {
          i = inferValueTypeOfKeyframe(paramResources, paramTheme, Xml.asAttributeSet(paramXmlPullParser), paramXmlPullParser);
        }
        Keyframe localKeyframe = loadKeyframe(paramContext, paramResources, paramTheme, Xml.asAttributeSet(paramXmlPullParser), i, paramXmlPullParser);
        Object localObject2 = localObject1;
        if (localKeyframe != null)
        {
          localObject2 = localObject1;
          if (localObject1 == null) {
            localObject2 = new ArrayList();
          }
          ((ArrayList)localObject2).add(localKeyframe);
        }
        paramXmlPullParser.next();
        localObject1 = localObject2;
      }
    }
    if (localObject1 != null)
    {
      paramInt = ((ArrayList)localObject1).size();
      int j = paramInt;
      if (paramInt > 0)
      {
        paramContext = (Keyframe)((ArrayList)localObject1).get(0);
        paramResources = (Keyframe)((ArrayList)localObject1).get(j - 1);
        float f2 = paramResources.getFraction();
        float f1 = 0.0F;
        paramInt = j;
        if (f2 < 1.0F) {
          if (f2 < 0.0F)
          {
            paramResources.setFraction(1.0F);
            paramInt = j;
          }
          else
          {
            ((ArrayList)localObject1).add(((ArrayList)localObject1).size(), createNewKeyframe(paramResources, 1.0F));
            paramInt = j + 1;
          }
        }
        f2 = paramContext.getFraction();
        int m = paramInt;
        if (f2 != 0.0F) {
          if (f2 < 0.0F)
          {
            paramContext.setFraction(0.0F);
            m = paramInt;
          }
          else
          {
            ((ArrayList)localObject1).add(0, createNewKeyframe(paramContext, 0.0F));
            m = paramInt + 1;
          }
        }
        paramContext = new Keyframe[m];
        ((ArrayList)localObject1).toArray(paramContext);
        j = 0;
        paramInt = k;
        while (j < m)
        {
          paramResources = paramContext[j];
          if (paramResources.getFraction() < f1) {
            if (j == 0)
            {
              paramResources.setFraction(f1);
            }
            else if (j == m - 1)
            {
              paramResources.setFraction(1.0F);
              f1 = 0.0F;
            }
            else
            {
              int i1 = j + 1;
              int n = j;
              k = paramInt;
              for (paramInt = i1; (paramInt < m - 1) && (paramContext[paramInt].getFraction() < 0.0F); paramInt++) {
                n = paramInt;
              }
              f1 = 0.0F;
              distributeKeyframes(paramContext, paramContext[(n + 1)].getFraction() - paramContext[(j - 1)].getFraction(), j, n);
              paramInt = k;
            }
          }
          j++;
        }
        paramResources = PropertyValuesHolder.ofKeyframe(paramString, paramContext);
        paramContext = paramResources;
        if (i != 3) {
          return paramContext;
        }
        paramResources.setEvaluator(ArgbEvaluator.getInstance());
        return paramResources;
      }
    }
    paramContext = null;
    return paramContext;
  }
  
  private static PropertyValuesHolder[] loadValues(Context paramContext, Resources paramResources, Resources.Theme paramTheme, XmlPullParser paramXmlPullParser, AttributeSet paramAttributeSet)
    throws XmlPullParserException, IOException
  {
    Object localObject1 = null;
    int i;
    for (;;)
    {
      i = paramXmlPullParser.getEventType();
      if ((i == 3) || (i == 1)) {
        break;
      }
      if (i != 2)
      {
        paramXmlPullParser.next();
      }
      else
      {
        if (paramXmlPullParser.getName().equals("propertyValuesHolder"))
        {
          TypedArray localTypedArray = TypedArrayUtils.obtainAttributes(paramResources, paramTheme, paramAttributeSet, AndroidResources.STYLEABLE_PROPERTY_VALUES_HOLDER);
          Object localObject2 = TypedArrayUtils.getNamedString(localTypedArray, paramXmlPullParser, "propertyName", 3);
          Log5ECF72.a(localObject2);
          LogE84000.a(localObject2);
          Log229316.a(localObject2);
          i = TypedArrayUtils.getNamedInt(localTypedArray, paramXmlPullParser, "valueType", 2, 4);
          PropertyValuesHolder localPropertyValuesHolder = loadPvh(paramContext, paramResources, paramTheme, paramXmlPullParser, (String)localObject2, i);
          if (localPropertyValuesHolder == null) {
            localPropertyValuesHolder = getPVH(localTypedArray, i, 0, 1, (String)localObject2);
          }
          localObject2 = localObject1;
          if (localPropertyValuesHolder != null)
          {
            localObject2 = localObject1;
            if (localObject1 == null) {
              localObject2 = new ArrayList();
            }
            ((ArrayList)localObject2).add(localPropertyValuesHolder);
          }
          localTypedArray.recycle();
          localObject1 = localObject2;
        }
        paramXmlPullParser.next();
      }
    }
    paramContext = null;
    if (localObject1 != null)
    {
      int j = ((ArrayList)localObject1).size();
      paramResources = new PropertyValuesHolder[j];
      for (i = 0;; i++)
      {
        paramContext = paramResources;
        if (i >= j) {
          break;
        }
        paramResources[i] = ((PropertyValuesHolder)((ArrayList)localObject1).get(i));
      }
    }
    return paramContext;
  }
  
  private static void parseAnimatorFromTypeArray(ValueAnimator paramValueAnimator, TypedArray paramTypedArray1, TypedArray paramTypedArray2, float paramFloat, XmlPullParser paramXmlPullParser)
  {
    long l1 = TypedArrayUtils.getNamedInt(paramTypedArray1, paramXmlPullParser, "duration", 1, 300);
    long l2 = TypedArrayUtils.getNamedInt(paramTypedArray1, paramXmlPullParser, "startOffset", 2, 0);
    int k = TypedArrayUtils.getNamedInt(paramTypedArray1, paramXmlPullParser, "valueType", 7, 4);
    int j = k;
    if (TypedArrayUtils.hasAttribute(paramXmlPullParser, "valueFrom"))
    {
      j = k;
      if (TypedArrayUtils.hasAttribute(paramXmlPullParser, "valueTo"))
      {
        int i = k;
        if (k == 4) {
          i = inferValueTypeFromValues(paramTypedArray1, 5, 6);
        }
        PropertyValuesHolder localPropertyValuesHolder = getPVH(paramTypedArray1, i, 5, 6, "");
        j = i;
        if (localPropertyValuesHolder != null)
        {
          paramValueAnimator.setValues(new PropertyValuesHolder[] { localPropertyValuesHolder });
          j = i;
        }
      }
    }
    paramValueAnimator.setDuration(l1);
    paramValueAnimator.setStartDelay(l2);
    paramValueAnimator.setRepeatCount(TypedArrayUtils.getNamedInt(paramTypedArray1, paramXmlPullParser, "repeatCount", 3, 0));
    paramValueAnimator.setRepeatMode(TypedArrayUtils.getNamedInt(paramTypedArray1, paramXmlPullParser, "repeatMode", 4, 1));
    if (paramTypedArray2 != null) {
      setupObjectAnimator(paramValueAnimator, paramTypedArray2, j, paramFloat, paramXmlPullParser);
    }
  }
  
  private static void setupObjectAnimator(ValueAnimator paramValueAnimator, TypedArray paramTypedArray, int paramInt, float paramFloat, XmlPullParser paramXmlPullParser)
  {
    paramValueAnimator = (ObjectAnimator)paramValueAnimator;
    String str1 = TypedArrayUtils.getNamedString(paramTypedArray, paramXmlPullParser, "pathData", 1);
    Log5ECF72.a(str1);
    LogE84000.a(str1);
    Log229316.a(str1);
    if (str1 != null)
    {
      String str2 = TypedArrayUtils.getNamedString(paramTypedArray, paramXmlPullParser, "propertyXName", 2);
      Log5ECF72.a(str2);
      LogE84000.a(str2);
      Log229316.a(str2);
      paramXmlPullParser = TypedArrayUtils.getNamedString(paramTypedArray, paramXmlPullParser, "propertyYName", 3);
      Log5ECF72.a(paramXmlPullParser);
      LogE84000.a(paramXmlPullParser);
      Log229316.a(paramXmlPullParser);
      if (((paramInt == 2) || (paramInt != 4)) || ((str2 == null) && (paramXmlPullParser == null))) {
        throw new InflateException(paramTypedArray.getPositionDescription() + " propertyXName or propertyYName is needed for PathData");
      }
      setupPathMotion(PathParser.createPathFromPathData(str1), paramValueAnimator, 0.5F * paramFloat, str2, paramXmlPullParser);
    }
    else
    {
      paramTypedArray = TypedArrayUtils.getNamedString(paramTypedArray, paramXmlPullParser, "propertyName", 0);
      Log5ECF72.a(paramTypedArray);
      LogE84000.a(paramTypedArray);
      Log229316.a(paramTypedArray);
      paramValueAnimator.setPropertyName(paramTypedArray);
    }
  }
  
  private static void setupPathMotion(Path paramPath, ObjectAnimator paramObjectAnimator, float paramFloat, String paramString1, String paramString2)
  {
    Object localObject = new PathMeasure(paramPath, false);
    float f = 0.0F;
    ArrayList localArrayList = new ArrayList();
    localArrayList.add(Float.valueOf(0.0F));
    for (;;)
    {
      f += ((PathMeasure)localObject).getLength();
      localArrayList.add(Float.valueOf(f));
      if (!((PathMeasure)localObject).nextContour())
      {
        PathMeasure localPathMeasure = new PathMeasure(paramPath, false);
        int m = Math.min(100, (int)(f / paramFloat) + 1);
        float[] arrayOfFloat = new float[m];
        localObject = new float[m];
        paramPath = new float[2];
        int j = 0;
        f /= (m - 1);
        paramFloat = 0.0F;
        int i = 0;
        while (i < m)
        {
          localPathMeasure.getPosTan(paramFloat - ((Float)localArrayList.get(j)).floatValue(), paramPath, null);
          arrayOfFloat[i] = paramPath[0];
          localObject[i] = paramPath[1];
          paramFloat += f;
          int k = j;
          if (j + 1 < localArrayList.size())
          {
            k = j;
            if (paramFloat > ((Float)localArrayList.get(j + 1)).floatValue())
            {
              k = j + 1;
              localPathMeasure.nextContour();
            }
          }
          i++;
          j = k;
        }
        paramPath = null;
        localArrayList = null;
        if (paramString1 != null) {
          paramPath = PropertyValuesHolder.ofFloat(paramString1, arrayOfFloat);
        }
        paramString1 = localArrayList;
        if (paramString2 != null) {
          paramString1 = PropertyValuesHolder.ofFloat(paramString2, (float[])localObject);
        }
        if (paramPath == null) {
          paramObjectAnimator.setValues(new PropertyValuesHolder[] { paramString1 });
        } else if (paramString1 == null) {
          paramObjectAnimator.setValues(new PropertyValuesHolder[] { paramPath });
        } else {
          paramObjectAnimator.setValues(new PropertyValuesHolder[] { paramPath, paramString1 });
        }
        return;
      }
    }
  }
  
  private static class PathDataEvaluator
    implements TypeEvaluator<PathParser.PathDataNode[]>
  {
    private PathParser.PathDataNode[] mNodeArray;
    
    PathDataEvaluator() {}
    
    PathDataEvaluator(PathParser.PathDataNode[] paramArrayOfPathDataNode)
    {
      this.mNodeArray = paramArrayOfPathDataNode;
    }
    
    public PathParser.PathDataNode[] evaluate(float paramFloat, PathParser.PathDataNode[] paramArrayOfPathDataNode1, PathParser.PathDataNode[] paramArrayOfPathDataNode2)
    {
      if (PathParser.canMorph(paramArrayOfPathDataNode1, paramArrayOfPathDataNode2))
      {
        if (!PathParser.canMorph(this.mNodeArray, paramArrayOfPathDataNode1)) {
          this.mNodeArray = PathParser.deepCopyNodes(paramArrayOfPathDataNode1);
        }
        for (int i = 0; i < paramArrayOfPathDataNode1.length; i++) {
          this.mNodeArray[i].interpolatePathDataNode(paramArrayOfPathDataNode1[i], paramArrayOfPathDataNode2[i], paramFloat);
        }
        return this.mNodeArray;
      }
      throw new IllegalArgumentException("Can't interpolate between two incompatible pathData");
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/vectordrawable/graphics/drawable/AnimatorInflaterCompat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */