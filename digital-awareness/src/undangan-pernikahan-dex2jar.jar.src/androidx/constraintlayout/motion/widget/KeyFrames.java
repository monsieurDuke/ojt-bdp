package androidx.constraintlayout.motion.widget;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;
import android.util.Xml;
import android.view.View;
import androidx.constraintlayout.widget.ConstraintAttribute;
import androidx.constraintlayout.widget.ConstraintLayout.LayoutParams;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

public class KeyFrames
{
  private static final String CUSTOM_ATTRIBUTE = "CustomAttribute";
  private static final String CUSTOM_METHOD = "CustomMethod";
  private static final String TAG = "KeyFrames";
  public static final int UNSET = -1;
  static HashMap<String, Constructor<? extends Key>> sKeyMakers;
  private HashMap<Integer, ArrayList<Key>> mFramesMap = new HashMap();
  
  static
  {
    HashMap localHashMap = new HashMap();
    sKeyMakers = localHashMap;
    try
    {
      localHashMap.put("KeyAttribute", KeyAttributes.class.getConstructor(new Class[0]));
      sKeyMakers.put("KeyPosition", KeyPosition.class.getConstructor(new Class[0]));
      sKeyMakers.put("KeyCycle", KeyCycle.class.getConstructor(new Class[0]));
      sKeyMakers.put("KeyTimeCycle", KeyTimeCycle.class.getConstructor(new Class[0]));
      sKeyMakers.put("KeyTrigger", KeyTrigger.class.getConstructor(new Class[0]));
    }
    catch (NoSuchMethodException localNoSuchMethodException)
    {
      Log.e("KeyFrames", "unable to load", localNoSuchMethodException);
    }
  }
  
  public KeyFrames() {}
  
  public KeyFrames(Context paramContext, XmlPullParser paramXmlPullParser)
  {
    Key localKey = null;
    try
    {
      int i = paramXmlPullParser.getEventType();
      while (i != 1)
      {
        Object localObject1;
        switch (i)
        {
        case 1: 
        default: 
          localObject1 = localKey;
          break;
        case 3: 
          localObject1 = localKey;
          if ("KeyFrameSet".equals(paramXmlPullParser.getName())) {
            return;
          }
          break;
        case 2: 
          String str = paramXmlPullParser.getName();
          boolean bool = sKeyMakers.containsKey(str);
          if (bool)
          {
            localObject1 = localKey;
            try
            {
              Object localObject3 = (Constructor)sKeyMakers.get(str);
              if (localObject3 != null)
              {
                localObject1 = localKey;
                localKey = (Key)((Constructor)localObject3).newInstance(new Object[0]);
                localObject1 = localKey;
                localKey.load(paramContext, Xml.asAttributeSet(paramXmlPullParser));
                localObject1 = localKey;
                addKey(localKey);
                localObject1 = localKey;
                break;
              }
              localObject1 = localKey;
              localObject3 = new java/lang/NullPointerException;
              localObject1 = localKey;
              StringBuilder localStringBuilder = new java/lang/StringBuilder;
              localObject1 = localKey;
              localStringBuilder.<init>();
              localObject1 = localKey;
              ((NullPointerException)localObject3).<init>("Keymaker for " + str + " not found");
              localObject1 = localKey;
              throw ((Throwable)localObject3);
            }
            catch (Exception localException)
            {
              Log.e("KeyFrames", "unable to create ", localException);
            }
          }
          if (str.equalsIgnoreCase("CustomAttribute"))
          {
            localObject1 = localException;
            if (localException != null)
            {
              localObject1 = localException;
              if (localException.mCustomConstraints != null)
              {
                ConstraintAttribute.parse(paramContext, paramXmlPullParser, localException.mCustomConstraints);
                localObject1 = localException;
              }
            }
          }
          else
          {
            localObject1 = localException;
            if (str.equalsIgnoreCase("CustomMethod"))
            {
              localObject1 = localException;
              if (localException != null)
              {
                localObject1 = localException;
                if (localException.mCustomConstraints != null)
                {
                  ConstraintAttribute.parse(paramContext, paramXmlPullParser, localException.mCustomConstraints);
                  localObject1 = localException;
                }
              }
            }
          }
          break;
        case 0: 
          localObject1 = localException;
        }
        i = paramXmlPullParser.next();
        Object localObject2 = localObject1;
      }
    }
    catch (IOException paramContext)
    {
      paramContext.printStackTrace();
    }
    catch (XmlPullParserException paramContext)
    {
      paramContext.printStackTrace();
    }
  }
  
  static String name(int paramInt, Context paramContext)
  {
    return paramContext.getResources().getResourceEntryName(paramInt);
  }
  
  public void addAllFrames(MotionController paramMotionController)
  {
    ArrayList localArrayList = (ArrayList)this.mFramesMap.get(Integer.valueOf(-1));
    if (localArrayList != null) {
      paramMotionController.addKeys(localArrayList);
    }
  }
  
  public void addFrames(MotionController paramMotionController)
  {
    Object localObject = (ArrayList)this.mFramesMap.get(Integer.valueOf(paramMotionController.mId));
    if (localObject != null) {
      paramMotionController.addKeys((ArrayList)localObject);
    }
    localObject = (ArrayList)this.mFramesMap.get(Integer.valueOf(-1));
    if (localObject != null)
    {
      Iterator localIterator = ((ArrayList)localObject).iterator();
      while (localIterator.hasNext())
      {
        localObject = (Key)localIterator.next();
        if (((Key)localObject).matches(((ConstraintLayout.LayoutParams)paramMotionController.mView.getLayoutParams()).constraintTag)) {
          paramMotionController.addKey((Key)localObject);
        }
      }
    }
  }
  
  public void addKey(Key paramKey)
  {
    if (!this.mFramesMap.containsKey(Integer.valueOf(paramKey.mTargetId))) {
      this.mFramesMap.put(Integer.valueOf(paramKey.mTargetId), new ArrayList());
    }
    ArrayList localArrayList = (ArrayList)this.mFramesMap.get(Integer.valueOf(paramKey.mTargetId));
    if (localArrayList != null) {
      localArrayList.add(paramKey);
    }
  }
  
  public ArrayList<Key> getKeyFramesForView(int paramInt)
  {
    return (ArrayList)this.mFramesMap.get(Integer.valueOf(paramInt));
  }
  
  public Set<Integer> getKeys()
  {
    return this.mFramesMap.keySet();
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/constraintlayout/motion/widget/KeyFrames.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */