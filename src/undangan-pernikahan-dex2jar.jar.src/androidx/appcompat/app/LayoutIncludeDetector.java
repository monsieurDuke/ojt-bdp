package androidx.appcompat.app;

import android.util.AttributeSet;
import java.lang.ref.WeakReference;
import java.util.ArrayDeque;
import java.util.Deque;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

class LayoutIncludeDetector
{
  private final Deque<WeakReference<XmlPullParser>> mXmlParserStack = new ArrayDeque();
  
  private static boolean isParserOutdated(XmlPullParser paramXmlPullParser)
  {
    boolean bool = true;
    if (paramXmlPullParser != null) {
      try
      {
        if (paramXmlPullParser.getEventType() != 3)
        {
          int i = paramXmlPullParser.getEventType();
          if (i != 1) {
            bool = false;
          }
        }
      }
      catch (XmlPullParserException paramXmlPullParser)
      {
        return true;
      }
    }
    return bool;
  }
  
  private static XmlPullParser popOutdatedAttrHolders(Deque<WeakReference<XmlPullParser>> paramDeque)
  {
    while (!paramDeque.isEmpty())
    {
      XmlPullParser localXmlPullParser = (XmlPullParser)((WeakReference)paramDeque.peek()).get();
      if (isParserOutdated(localXmlPullParser)) {
        paramDeque.pop();
      } else {
        return localXmlPullParser;
      }
    }
    return null;
  }
  
  private static boolean shouldInheritContext(XmlPullParser paramXmlPullParser1, XmlPullParser paramXmlPullParser2)
  {
    if ((paramXmlPullParser2 != null) && (paramXmlPullParser1 != paramXmlPullParser2)) {
      try
      {
        if (paramXmlPullParser2.getEventType() == 2)
        {
          boolean bool = "include".equals(paramXmlPullParser2.getName());
          return bool;
        }
      }
      catch (XmlPullParserException paramXmlPullParser1) {}
    }
    return false;
  }
  
  boolean detect(AttributeSet paramAttributeSet)
  {
    if ((paramAttributeSet instanceof XmlPullParser))
    {
      paramAttributeSet = (XmlPullParser)paramAttributeSet;
      if (paramAttributeSet.getDepth() == 1)
      {
        XmlPullParser localXmlPullParser = popOutdatedAttrHolders(this.mXmlParserStack);
        this.mXmlParserStack.push(new WeakReference(paramAttributeSet));
        if (shouldInheritContext(paramAttributeSet, localXmlPullParser)) {
          return true;
        }
      }
    }
    return false;
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/appcompat/app/LayoutIncludeDetector.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */