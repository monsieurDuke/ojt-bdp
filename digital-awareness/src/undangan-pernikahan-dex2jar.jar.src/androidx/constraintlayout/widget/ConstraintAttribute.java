package androidx.constraintlayout.widget;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.util.TypedValue;
import android.util.Xml;
import android.view.View;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import org.xmlpull.v1.XmlPullParser;

public class ConstraintAttribute
{
  private static final String TAG = "TransitionLayout";
  boolean mBooleanValue;
  private int mColorValue;
  private float mFloatValue;
  private int mIntegerValue;
  private boolean mMethod = false;
  String mName;
  private String mStringValue;
  private AttributeType mType;
  
  public ConstraintAttribute(ConstraintAttribute paramConstraintAttribute, Object paramObject)
  {
    this.mName = paramConstraintAttribute.mName;
    this.mType = paramConstraintAttribute.mType;
    setValue(paramObject);
  }
  
  public ConstraintAttribute(String paramString, AttributeType paramAttributeType)
  {
    this.mName = paramString;
    this.mType = paramAttributeType;
  }
  
  public ConstraintAttribute(String paramString, AttributeType paramAttributeType, Object paramObject, boolean paramBoolean)
  {
    this.mName = paramString;
    this.mType = paramAttributeType;
    this.mMethod = paramBoolean;
    setValue(paramObject);
  }
  
  private static int clamp(int paramInt)
  {
    paramInt = (paramInt & (paramInt >> 31 ^ 0xFFFFFFFF)) - 255;
    return (paramInt & paramInt >> 31) + 255;
  }
  
  public static HashMap<String, ConstraintAttribute> extractAttributes(HashMap<String, ConstraintAttribute> paramHashMap, View paramView)
  {
    HashMap localHashMap = new HashMap();
    Class localClass = paramView.getClass();
    Iterator localIterator = paramHashMap.keySet().iterator();
    while (localIterator.hasNext())
    {
      String str = (String)localIterator.next();
      ConstraintAttribute localConstraintAttribute = (ConstraintAttribute)paramHashMap.get(str);
      try
      {
        Object localObject1;
        if (str.equals("BackgroundColor"))
        {
          int i = ((ColorDrawable)paramView.getBackground()).getColor();
          localObject1 = new androidx/constraintlayout/widget/ConstraintAttribute;
          ((ConstraintAttribute)localObject1).<init>(localConstraintAttribute, Integer.valueOf(i));
          localHashMap.put(str, localObject1);
        }
        else
        {
          localObject1 = new java/lang/StringBuilder;
          ((StringBuilder)localObject1).<init>();
          Object localObject2 = localClass.getMethod("getMap" + str, new Class[0]).invoke(paramView, new Object[0]);
          localObject1 = new androidx/constraintlayout/widget/ConstraintAttribute;
          ((ConstraintAttribute)localObject1).<init>(localConstraintAttribute, localObject2);
          localHashMap.put(str, localObject1);
        }
      }
      catch (InvocationTargetException localInvocationTargetException)
      {
        localInvocationTargetException.printStackTrace();
      }
      catch (IllegalAccessException localIllegalAccessException)
      {
        localIllegalAccessException.printStackTrace();
      }
      catch (NoSuchMethodException localNoSuchMethodException)
      {
        localNoSuchMethodException.printStackTrace();
      }
    }
    return localHashMap;
  }
  
  public static void parse(Context paramContext, XmlPullParser paramXmlPullParser, HashMap<String, ConstraintAttribute> paramHashMap)
  {
    TypedArray localTypedArray = paramContext.obtainStyledAttributes(Xml.asAttributeSet(paramXmlPullParser), R.styleable.CustomAttribute);
    Object localObject3 = null;
    boolean bool2 = false;
    XmlPullParser localXmlPullParser = null;
    Object localObject4 = null;
    int m = localTypedArray.getIndexCount();
    int i = 0;
    while (i < m)
    {
      int n = localTypedArray.getIndex(i);
      Object localObject2;
      boolean bool1;
      Object localObject1;
      if (n == R.styleable.CustomAttribute_attributeName)
      {
        localObject3 = localTypedArray.getString(n);
        localObject2 = localObject3;
        bool1 = bool2;
        paramXmlPullParser = localXmlPullParser;
        localObject1 = localObject4;
        if (localObject3 != null)
        {
          localObject2 = localObject3;
          bool1 = bool2;
          paramXmlPullParser = localXmlPullParser;
          localObject1 = localObject4;
          if (((String)localObject3).length() > 0)
          {
            localObject2 = Character.toUpperCase(((String)localObject3).charAt(0)) + ((String)localObject3).substring(1);
            bool1 = bool2;
            paramXmlPullParser = localXmlPullParser;
            localObject1 = localObject4;
          }
        }
      }
      else if (n == R.styleable.CustomAttribute_methodName)
      {
        bool1 = true;
        localObject2 = localTypedArray.getString(n);
        paramXmlPullParser = localXmlPullParser;
        localObject1 = localObject4;
      }
      else if (n == R.styleable.CustomAttribute_customBoolean)
      {
        paramXmlPullParser = Boolean.valueOf(localTypedArray.getBoolean(n, false));
        localObject1 = AttributeType.BOOLEAN_TYPE;
        localObject2 = localObject3;
        bool1 = bool2;
      }
      else if (n == R.styleable.CustomAttribute_customColorValue)
      {
        localObject1 = AttributeType.COLOR_TYPE;
        paramXmlPullParser = Integer.valueOf(localTypedArray.getColor(n, 0));
        localObject2 = localObject3;
        bool1 = bool2;
      }
      else if (n == R.styleable.CustomAttribute_customColorDrawableValue)
      {
        localObject1 = AttributeType.COLOR_DRAWABLE_TYPE;
        paramXmlPullParser = Integer.valueOf(localTypedArray.getColor(n, 0));
        localObject2 = localObject3;
        bool1 = bool2;
      }
      else if (n == R.styleable.CustomAttribute_customPixelDimension)
      {
        localObject1 = AttributeType.DIMENSION_TYPE;
        paramXmlPullParser = Float.valueOf(TypedValue.applyDimension(1, localTypedArray.getDimension(n, 0.0F), paramContext.getResources().getDisplayMetrics()));
        localObject2 = localObject3;
        bool1 = bool2;
      }
      else if (n == R.styleable.CustomAttribute_customDimension)
      {
        localObject1 = AttributeType.DIMENSION_TYPE;
        paramXmlPullParser = Float.valueOf(localTypedArray.getDimension(n, 0.0F));
        localObject2 = localObject3;
        bool1 = bool2;
      }
      else if (n == R.styleable.CustomAttribute_customFloatValue)
      {
        localObject1 = AttributeType.FLOAT_TYPE;
        paramXmlPullParser = Float.valueOf(localTypedArray.getFloat(n, NaN.0F));
        localObject2 = localObject3;
        bool1 = bool2;
      }
      else if (n == R.styleable.CustomAttribute_customIntegerValue)
      {
        localObject1 = AttributeType.INT_TYPE;
        paramXmlPullParser = Integer.valueOf(localTypedArray.getInteger(n, -1));
        localObject2 = localObject3;
        bool1 = bool2;
      }
      else if (n == R.styleable.CustomAttribute_customStringValue)
      {
        localObject1 = AttributeType.STRING_TYPE;
        paramXmlPullParser = localTypedArray.getString(n);
        localObject2 = localObject3;
        bool1 = bool2;
      }
      else
      {
        localObject2 = localObject3;
        bool1 = bool2;
        paramXmlPullParser = localXmlPullParser;
        localObject1 = localObject4;
        if (n == R.styleable.CustomAttribute_customReference)
        {
          localObject1 = AttributeType.REFERENCE_TYPE;
          int k = localTypedArray.getResourceId(n, -1);
          int j = k;
          if (k == -1) {
            j = localTypedArray.getInt(n, -1);
          }
          paramXmlPullParser = Integer.valueOf(j);
          bool1 = bool2;
          localObject2 = localObject3;
        }
      }
      i++;
      localObject3 = localObject2;
      bool2 = bool1;
      localXmlPullParser = paramXmlPullParser;
      localObject4 = localObject1;
    }
    if ((localObject3 != null) && (localXmlPullParser != null)) {
      paramHashMap.put(localObject3, new ConstraintAttribute((String)localObject3, (AttributeType)localObject4, localXmlPullParser, bool2));
    }
    localTypedArray.recycle();
  }
  
  public static void setAttributes(View paramView, HashMap<String, ConstraintAttribute> paramHashMap)
  {
    Class localClass = paramView.getClass();
    Iterator localIterator = paramHashMap.keySet().iterator();
    while (localIterator.hasNext())
    {
      String str = (String)localIterator.next();
      ConstraintAttribute localConstraintAttribute = (ConstraintAttribute)paramHashMap.get(str);
      Object localObject2 = str;
      Object localObject1 = localObject2;
      if (!localConstraintAttribute.mMethod) {
        localObject1 = "set" + (String)localObject2;
      }
      try
      {
        switch (1.$SwitchMap$androidx$constraintlayout$widget$ConstraintAttribute$AttributeType[localConstraintAttribute.mType.ordinal()])
        {
        default: 
          break;
        case 8: 
          localClass.getMethod((String)localObject1, new Class[] { Float.TYPE }).invoke(paramView, new Object[] { Float.valueOf(localConstraintAttribute.mFloatValue) });
          break;
        case 7: 
          localClass.getMethod((String)localObject1, new Class[] { Float.TYPE }).invoke(paramView, new Object[] { Float.valueOf(localConstraintAttribute.mFloatValue) });
          break;
        case 6: 
          localClass.getMethod((String)localObject1, new Class[] { Integer.TYPE }).invoke(paramView, new Object[] { Integer.valueOf(localConstraintAttribute.mIntegerValue) });
          break;
        case 5: 
          Method localMethod = localClass.getMethod((String)localObject1, new Class[] { Drawable.class });
          localObject2 = new android/graphics/drawable/ColorDrawable;
          ((ColorDrawable)localObject2).<init>();
          ((ColorDrawable)localObject2).setColor(localConstraintAttribute.mColorValue);
          localMethod.invoke(paramView, new Object[] { localObject2 });
          break;
        case 4: 
          localClass.getMethod((String)localObject1, new Class[] { Integer.TYPE }).invoke(paramView, new Object[] { Integer.valueOf(localConstraintAttribute.mColorValue) });
          break;
        case 3: 
          localClass.getMethod((String)localObject1, new Class[] { CharSequence.class }).invoke(paramView, new Object[] { localConstraintAttribute.mStringValue });
          break;
        case 2: 
          localClass.getMethod((String)localObject1, new Class[] { Boolean.TYPE }).invoke(paramView, new Object[] { Boolean.valueOf(localConstraintAttribute.mBooleanValue) });
          break;
        case 1: 
          localClass.getMethod((String)localObject1, new Class[] { Integer.TYPE }).invoke(paramView, new Object[] { Integer.valueOf(localConstraintAttribute.mIntegerValue) });
        }
      }
      catch (InvocationTargetException localInvocationTargetException)
      {
        Log.e("TransitionLayout", " Custom Attribute \"" + str + "\" not found on " + localClass.getName());
        localInvocationTargetException.printStackTrace();
      }
      catch (IllegalAccessException localIllegalAccessException)
      {
        Log.e("TransitionLayout", " Custom Attribute \"" + str + "\" not found on " + localClass.getName());
        localIllegalAccessException.printStackTrace();
      }
      catch (NoSuchMethodException localNoSuchMethodException)
      {
        Log.e("TransitionLayout", localNoSuchMethodException.getMessage());
        Log.e("TransitionLayout", " Custom Attribute \"" + str + "\" not found on " + localClass.getName());
        Log.e("TransitionLayout", localClass.getName() + " must have a method " + localIllegalAccessException);
      }
    }
  }
  
  public void applyCustom(View paramView)
  {
    Class localClass = paramView.getClass();
    String str = this.mName;
    Object localObject2 = str;
    Object localObject1 = localObject2;
    if (!this.mMethod) {
      localObject1 = "set" + (String)localObject2;
    }
    try
    {
      switch (1.$SwitchMap$androidx$constraintlayout$widget$ConstraintAttribute$AttributeType[this.mType.ordinal()])
      {
      default: 
        break;
      case 8: 
        localClass.getMethod((String)localObject1, new Class[] { Float.TYPE }).invoke(paramView, new Object[] { Float.valueOf(this.mFloatValue) });
        break;
      case 7: 
        localClass.getMethod((String)localObject1, new Class[] { Float.TYPE }).invoke(paramView, new Object[] { Float.valueOf(this.mFloatValue) });
        break;
      case 5: 
        Method localMethod = localClass.getMethod((String)localObject1, new Class[] { Drawable.class });
        localObject2 = new android/graphics/drawable/ColorDrawable;
        ((ColorDrawable)localObject2).<init>();
        ((ColorDrawable)localObject2).setColor(this.mColorValue);
        localMethod.invoke(paramView, new Object[] { localObject2 });
        break;
      case 4: 
        localClass.getMethod((String)localObject1, new Class[] { Integer.TYPE }).invoke(paramView, new Object[] { Integer.valueOf(this.mColorValue) });
        break;
      case 3: 
        localClass.getMethod((String)localObject1, new Class[] { CharSequence.class }).invoke(paramView, new Object[] { this.mStringValue });
        break;
      case 2: 
        localClass.getMethod((String)localObject1, new Class[] { Boolean.TYPE }).invoke(paramView, new Object[] { Boolean.valueOf(this.mBooleanValue) });
        break;
      case 1: 
      case 6: 
        localClass.getMethod((String)localObject1, new Class[] { Integer.TYPE }).invoke(paramView, new Object[] { Integer.valueOf(this.mIntegerValue) });
      }
    }
    catch (InvocationTargetException paramView)
    {
      Log.e("TransitionLayout", " Custom Attribute \"" + str + "\" not found on " + localClass.getName());
      paramView.printStackTrace();
    }
    catch (IllegalAccessException paramView)
    {
      Log.e("TransitionLayout", " Custom Attribute \"" + str + "\" not found on " + localClass.getName());
      paramView.printStackTrace();
    }
    catch (NoSuchMethodException paramView)
    {
      Log.e("TransitionLayout", paramView.getMessage());
      Log.e("TransitionLayout", " Custom Attribute \"" + str + "\" not found on " + localClass.getName());
      Log.e("TransitionLayout", localClass.getName() + " must have a method " + (String)localObject1);
    }
  }
  
  public boolean diff(ConstraintAttribute paramConstraintAttribute)
  {
    boolean bool3 = false;
    boolean bool2 = false;
    boolean bool4 = false;
    boolean bool6 = false;
    boolean bool1 = false;
    boolean bool5 = false;
    if ((paramConstraintAttribute != null) && (this.mType == paramConstraintAttribute.mType))
    {
      switch (1.$SwitchMap$androidx$constraintlayout$widget$ConstraintAttribute$AttributeType[this.mType.ordinal()])
      {
      default: 
        return false;
      case 8: 
        bool1 = bool5;
        if (this.mFloatValue == paramConstraintAttribute.mFloatValue) {
          bool1 = true;
        }
        return bool1;
      case 7: 
        bool1 = bool3;
        if (this.mFloatValue == paramConstraintAttribute.mFloatValue) {
          bool1 = true;
        }
        return bool1;
      case 4: 
      case 5: 
        bool1 = bool2;
        if (this.mColorValue == paramConstraintAttribute.mColorValue) {
          bool1 = true;
        }
        return bool1;
      case 3: 
        bool1 = bool4;
        if (this.mIntegerValue == paramConstraintAttribute.mIntegerValue) {
          bool1 = true;
        }
        return bool1;
      case 2: 
        bool1 = bool6;
        if (this.mBooleanValue == paramConstraintAttribute.mBooleanValue) {
          bool1 = true;
        }
        return bool1;
      }
      if (this.mIntegerValue == paramConstraintAttribute.mIntegerValue) {
        bool1 = true;
      }
      return bool1;
    }
    return false;
  }
  
  public int getColorValue()
  {
    return this.mColorValue;
  }
  
  public float getFloatValue()
  {
    return this.mFloatValue;
  }
  
  public int getIntegerValue()
  {
    return this.mIntegerValue;
  }
  
  public String getName()
  {
    return this.mName;
  }
  
  public String getStringValue()
  {
    return this.mStringValue;
  }
  
  public AttributeType getType()
  {
    return this.mType;
  }
  
  public float getValueToInterpolate()
  {
    switch (1.$SwitchMap$androidx$constraintlayout$widget$ConstraintAttribute$AttributeType[this.mType.ordinal()])
    {
    default: 
      return NaN.0F;
    case 8: 
      return this.mFloatValue;
    case 7: 
      return this.mFloatValue;
    case 6: 
      return this.mIntegerValue;
    case 4: 
    case 5: 
      throw new RuntimeException("Color does not have a single color to interpolate");
    case 3: 
      throw new RuntimeException("Cannot interpolate String");
    }
    float f;
    if (this.mBooleanValue) {
      f = 1.0F;
    } else {
      f = 0.0F;
    }
    return f;
  }
  
  public void getValuesToInterpolate(float[] paramArrayOfFloat)
  {
    float f1;
    switch (1.$SwitchMap$androidx$constraintlayout$widget$ConstraintAttribute$AttributeType[this.mType.ordinal()])
    {
    default: 
      break;
    case 8: 
      paramArrayOfFloat[0] = this.mFloatValue;
      break;
    case 7: 
      paramArrayOfFloat[0] = this.mFloatValue;
      break;
    case 6: 
      paramArrayOfFloat[0] = this.mIntegerValue;
      break;
    case 4: 
    case 5: 
      int i = this.mColorValue;
      float f2 = (float)Math.pow((i >> 16 & 0xFF) / 255.0F, 2.2D);
      float f3 = (float)Math.pow((i >> 8 & 0xFF) / 255.0F, 2.2D);
      f1 = (float)Math.pow((i & 0xFF) / 255.0F, 2.2D);
      paramArrayOfFloat[0] = f2;
      paramArrayOfFloat[1] = f3;
      paramArrayOfFloat[2] = f1;
      paramArrayOfFloat[3] = ((i >> 24 & 0xFF) / 255.0F);
      break;
    case 3: 
      throw new RuntimeException("Color does not have a single color to interpolate");
    case 2: 
      if (this.mBooleanValue) {
        f1 = 1.0F;
      } else {
        f1 = 0.0F;
      }
      paramArrayOfFloat[0] = f1;
    }
  }
  
  public boolean isBooleanValue()
  {
    return this.mBooleanValue;
  }
  
  public boolean isContinuous()
  {
    switch (1.$SwitchMap$androidx$constraintlayout$widget$ConstraintAttribute$AttributeType[this.mType.ordinal()])
    {
    default: 
      return true;
    }
    return false;
  }
  
  public boolean isMethod()
  {
    return this.mMethod;
  }
  
  public int numberOfInterpolatedValues()
  {
    switch (1.$SwitchMap$androidx$constraintlayout$widget$ConstraintAttribute$AttributeType[this.mType.ordinal()])
    {
    default: 
      return 1;
    }
    return 4;
  }
  
  public void setColorValue(int paramInt)
  {
    this.mColorValue = paramInt;
  }
  
  public void setFloatValue(float paramFloat)
  {
    this.mFloatValue = paramFloat;
  }
  
  public void setIntValue(int paramInt)
  {
    this.mIntegerValue = paramInt;
  }
  
  public void setStringValue(String paramString)
  {
    this.mStringValue = paramString;
  }
  
  public void setValue(Object paramObject)
  {
    switch (1.$SwitchMap$androidx$constraintlayout$widget$ConstraintAttribute$AttributeType[this.mType.ordinal()])
    {
    default: 
      break;
    case 8: 
      this.mFloatValue = ((Float)paramObject).floatValue();
      break;
    case 7: 
      this.mFloatValue = ((Float)paramObject).floatValue();
      break;
    case 4: 
    case 5: 
      this.mColorValue = ((Integer)paramObject).intValue();
      break;
    case 3: 
      this.mStringValue = ((String)paramObject);
      break;
    case 2: 
      this.mBooleanValue = ((Boolean)paramObject).booleanValue();
      break;
    case 1: 
    case 6: 
      this.mIntegerValue = ((Integer)paramObject).intValue();
    }
  }
  
  public void setValue(float[] paramArrayOfFloat)
  {
    int i = 1.$SwitchMap$androidx$constraintlayout$widget$ConstraintAttribute$AttributeType[this.mType.ordinal()];
    boolean bool = false;
    switch (i)
    {
    default: 
      break;
    case 8: 
      this.mFloatValue = paramArrayOfFloat[0];
      break;
    case 7: 
      this.mFloatValue = paramArrayOfFloat[0];
      break;
    case 4: 
    case 5: 
      i = Color.HSVToColor(paramArrayOfFloat);
      this.mColorValue = i;
      this.mColorValue = (i & 0xFFFFFF | clamp((int)(paramArrayOfFloat[3] * 255.0F)) << 24);
      break;
    case 3: 
      throw new RuntimeException("Color does not have a single color to interpolate");
    case 2: 
      if (paramArrayOfFloat[0] > 0.5D) {
        bool = true;
      }
      this.mBooleanValue = bool;
      break;
    case 1: 
    case 6: 
      this.mIntegerValue = ((int)paramArrayOfFloat[0]);
    }
  }
  
  public static enum AttributeType
  {
    private static final AttributeType[] $VALUES = $values();
    
    static
    {
      FLOAT_TYPE = new AttributeType("FLOAT_TYPE", 1);
      COLOR_TYPE = new AttributeType("COLOR_TYPE", 2);
      COLOR_DRAWABLE_TYPE = new AttributeType("COLOR_DRAWABLE_TYPE", 3);
      STRING_TYPE = new AttributeType("STRING_TYPE", 4);
      BOOLEAN_TYPE = new AttributeType("BOOLEAN_TYPE", 5);
      DIMENSION_TYPE = new AttributeType("DIMENSION_TYPE", 6);
      REFERENCE_TYPE = new AttributeType("REFERENCE_TYPE", 7);
    }
    
    private AttributeType() {}
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/constraintlayout/widget/ConstraintAttribute.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */