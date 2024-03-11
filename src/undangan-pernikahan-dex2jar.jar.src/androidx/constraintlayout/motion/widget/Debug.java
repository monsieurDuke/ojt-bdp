package androidx.constraintlayout.motion.widget;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.Resources.NotFoundException;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import java.io.PrintStream;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.nio.CharBuffer;
import mt.Log229316;
import mt.Log5ECF72;
import mt.LogE84000;

public class Debug
{
  public static void dumpLayoutParams(ViewGroup.LayoutParams paramLayoutParams, String paramString)
  {
    Object localObject1 = new Throwable().getStackTrace()[1];
    localObject1 = ".(" + ((StackTraceElement)localObject1).getFileName() + ":" + ((StackTraceElement)localObject1).getLineNumber() + ") " + paramString + "  ";
    System.out.println(" >>>>>>>>>>>>>>>>>>. dump " + (String)localObject1 + "  " + paramLayoutParams.getClass().getName());
    paramString = paramLayoutParams.getClass().getFields();
    for (int i = 0; i < paramString.length; i++)
    {
      StringBuilder localStringBuilder = paramString[i];
      try
      {
        Object localObject2 = localStringBuilder.get(paramLayoutParams);
        String str = localStringBuilder.getName();
        if ((str.contains("To")) && (!localObject2.toString().equals("-1")))
        {
          PrintStream localPrintStream = System.out;
          localStringBuilder = new java/lang/StringBuilder;
          localStringBuilder.<init>();
          localPrintStream.println((String)localObject1 + "       " + str + " " + localObject2);
        }
      }
      catch (IllegalAccessException localIllegalAccessException) {}
    }
    System.out.println(" <<<<<<<<<<<<<<<<< dump " + (String)localObject1);
  }
  
  public static void dumpLayoutParams(ViewGroup paramViewGroup, String paramString)
  {
    Object localObject1 = new Throwable().getStackTrace()[1];
    localObject1 = ".(" + ((StackTraceElement)localObject1).getFileName() + ":" + ((StackTraceElement)localObject1).getLineNumber() + ") " + paramString + "  ";
    int k = paramViewGroup.getChildCount();
    System.out.println(paramString + " children " + k);
    for (int i = 0; i < k; i++)
    {
      Object localObject3 = paramViewGroup.getChildAt(i);
      paramString = System.out;
      Object localObject4 = new StringBuilder().append((String)localObject1).append("     ");
      Object localObject2 = getName((View)localObject3);
      Log5ECF72.a(localObject2);
      LogE84000.a(localObject2);
      Log229316.a(localObject2);
      paramString.println((String)localObject2);
      paramString = ((View)localObject3).getLayoutParams();
      localObject2 = paramString.getClass().getFields();
      for (int j = 0; j < localObject2.length; j++)
      {
        Object localObject5 = localObject2[j];
        try
        {
          localObject4 = ((Field)localObject5).get(paramString);
          if ((((Field)localObject5).getName().contains("To")) && (!localObject4.toString().equals("-1")))
          {
            PrintStream localPrintStream = System.out;
            localObject3 = new java/lang/StringBuilder;
            ((StringBuilder)localObject3).<init>();
            localPrintStream.println((String)localObject1 + "       " + ((Field)localObject5).getName() + " " + localObject4);
          }
        }
        catch (IllegalAccessException localIllegalAccessException) {}
      }
    }
  }
  
  public static void dumpPoc(Object paramObject)
  {
    Object localObject1 = new Throwable().getStackTrace()[1];
    localObject1 = ".(" + ((StackTraceElement)localObject1).getFileName() + ":" + ((StackTraceElement)localObject1).getLineNumber() + ")";
    Class localClass = paramObject.getClass();
    System.out.println((String)localObject1 + "------------- " + localClass.getName() + " --------------------");
    Field[] arrayOfField = localClass.getFields();
    for (int i = 0; i < arrayOfField.length; i++)
    {
      Field localField = arrayOfField[i];
      try
      {
        Object localObject2 = localField.get(paramObject);
        if ((localField.getName().startsWith("layout_constraint")) && ((!(localObject2 instanceof Integer)) || (!localObject2.toString().equals("-1"))) && ((!(localObject2 instanceof Integer)) || (!localObject2.toString().equals("0"))) && ((!(localObject2 instanceof Float)) || (!localObject2.toString().equals("1.0"))) && ((!(localObject2 instanceof Float)) || (!localObject2.toString().equals("0.5"))))
        {
          PrintStream localPrintStream = System.out;
          StringBuilder localStringBuilder = new java/lang/StringBuilder;
          localStringBuilder.<init>();
          localPrintStream.println((String)localObject1 + "    " + localField.getName() + " " + localObject2);
        }
      }
      catch (IllegalAccessException localIllegalAccessException) {}
    }
    System.out.println((String)localObject1 + "------------- " + localClass.getSimpleName() + " --------------------");
  }
  
  public static String getActionType(MotionEvent paramMotionEvent)
  {
    int j = paramMotionEvent.getAction();
    paramMotionEvent = MotionEvent.class.getFields();
    for (int i = 0; i < paramMotionEvent.length; i++)
    {
      String str = paramMotionEvent[i];
      try
      {
        if ((Modifier.isStatic(str.getModifiers())) && (str.getType().equals(Integer.TYPE)) && (str.getInt(null) == j))
        {
          str = str.getName();
          return str;
        }
      }
      catch (IllegalAccessException localIllegalAccessException) {}
    }
    return "---";
  }
  
  public static String getCallFrom(int paramInt)
  {
    StackTraceElement localStackTraceElement = new Throwable().getStackTrace()[(paramInt + 2)];
    return ".(" + localStackTraceElement.getFileName() + ":" + localStackTraceElement.getLineNumber() + ")";
  }
  
  public static String getLoc()
  {
    StackTraceElement localStackTraceElement = new Throwable().getStackTrace()[1];
    return ".(" + localStackTraceElement.getFileName() + ":" + localStackTraceElement.getLineNumber() + ") " + localStackTraceElement.getMethodName() + "()";
  }
  
  public static String getLocation()
  {
    StackTraceElement localStackTraceElement = new Throwable().getStackTrace()[1];
    return ".(" + localStackTraceElement.getFileName() + ":" + localStackTraceElement.getLineNumber() + ")";
  }
  
  public static String getLocation2()
  {
    StackTraceElement localStackTraceElement = new Throwable().getStackTrace()[2];
    return ".(" + localStackTraceElement.getFileName() + ":" + localStackTraceElement.getLineNumber() + ")";
  }
  
  public static String getName(Context paramContext, int paramInt)
  {
    if (paramInt != -1) {}
    try
    {
      return paramContext.getResources().getResourceEntryName(paramInt);
    }
    catch (Exception paramContext) {}
    return "UNKNOWN";
    return "?" + paramInt;
  }
  
  public static String getName(Context paramContext, int[] paramArrayOfInt)
  {
    try
    {
      Object localObject1 = new java/lang/StringBuilder;
      ((StringBuilder)localObject1).<init>();
      localObject1 = paramArrayOfInt.length + "[";
      Object localObject2;
      for (int i = 0; i < paramArrayOfInt.length; i++)
      {
        Object localObject3 = new java/lang/StringBuilder;
        ((StringBuilder)localObject3).<init>();
        localObject3 = ((StringBuilder)localObject3).append((String)localObject1);
        if (i == 0) {
          localObject1 = "";
        } else {
          localObject1 = " ";
        }
        localObject3 = (String)localObject1;
        try
        {
          localObject1 = paramContext.getResources().getResourceEntryName(paramArrayOfInt[i]);
        }
        catch (Resources.NotFoundException localNotFoundException)
        {
          localObject2 = new java/lang/StringBuilder;
          ((StringBuilder)localObject2).<init>();
          localObject2 = "? " + paramArrayOfInt[i] + " ";
        }
        StringBuilder localStringBuilder = new java/lang/StringBuilder;
        localStringBuilder.<init>();
        localObject2 = (String)localObject3 + (String)localObject2;
      }
      paramContext = new java/lang/StringBuilder;
      paramContext.<init>();
      paramContext = (String)localObject2 + "]";
      return paramContext;
    }
    catch (Exception paramContext)
    {
      Log.v("DEBUG", paramContext.toString());
    }
    return "UNKNOWN";
  }
  
  public static String getName(View paramView)
  {
    try
    {
      paramView = paramView.getContext().getResources().getResourceEntryName(paramView.getId());
      return paramView;
    }
    catch (Exception paramView) {}
    return "UNKNOWN";
  }
  
  public static String getState(MotionLayout paramMotionLayout, int paramInt)
  {
    paramMotionLayout = getState(paramMotionLayout, paramInt, -1);
    Log5ECF72.a(paramMotionLayout);
    LogE84000.a(paramMotionLayout);
    Log229316.a(paramMotionLayout);
    return paramMotionLayout;
  }
  
  public static String getState(MotionLayout paramMotionLayout, int paramInt1, int paramInt2)
  {
    if (paramInt1 == -1) {
      return "UNDEFINED";
    }
    String str = paramMotionLayout.getContext().getResources().getResourceEntryName(paramInt1);
    Object localObject = str;
    if (paramInt2 != -1)
    {
      paramMotionLayout = str;
      if (str.length() > paramInt2) {
        paramMotionLayout = str.replaceAll("([^_])[aeiou]+", "$1");
      }
      localObject = paramMotionLayout;
      if (paramMotionLayout.length() > paramInt2)
      {
        paramInt1 = paramMotionLayout.replaceAll("[^_]", "").length();
        localObject = paramMotionLayout;
        if (paramInt1 > 0)
        {
          paramInt1 = (paramMotionLayout.length() - paramInt2) / paramInt1;
          localObject = paramMotionLayout.replaceAll(CharBuffer.allocate(paramInt1).toString().replace('\000', '.') + "_", "_");
        }
      }
    }
    return (String)localObject;
  }
  
  public static void logStack(String paramString1, String paramString2, int paramInt)
  {
    StackTraceElement[] arrayOfStackTraceElement = new Throwable().getStackTrace();
    String str = " ";
    int i = Math.min(paramInt, arrayOfStackTraceElement.length - 1);
    for (paramInt = 1; paramInt <= i; paramInt++)
    {
      Object localObject = arrayOfStackTraceElement[paramInt];
      localObject = ".(" + arrayOfStackTraceElement[paramInt].getFileName() + ":" + arrayOfStackTraceElement[paramInt].getLineNumber() + ") " + arrayOfStackTraceElement[paramInt].getMethodName();
      str = str + " ";
      Log.v(paramString1, paramString2 + str + (String)localObject + str);
    }
  }
  
  public static void printStack(String paramString, int paramInt)
  {
    StackTraceElement[] arrayOfStackTraceElement = new Throwable().getStackTrace();
    String str = " ";
    int i = Math.min(paramInt, arrayOfStackTraceElement.length - 1);
    for (paramInt = 1; paramInt <= i; paramInt++)
    {
      Object localObject = arrayOfStackTraceElement[paramInt];
      localObject = ".(" + arrayOfStackTraceElement[paramInt].getFileName() + ":" + arrayOfStackTraceElement[paramInt].getLineNumber() + ") ";
      str = str + " ";
      System.out.println(paramString + str + (String)localObject + str);
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/constraintlayout/motion/widget/Debug.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */