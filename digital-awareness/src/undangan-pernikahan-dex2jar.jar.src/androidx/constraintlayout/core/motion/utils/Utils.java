package androidx.constraintlayout.core.motion.utils;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Socket;
import mt.Log229316;
import mt.Log5ECF72;
import mt.LogE84000;

public class Utils
{
  static DebugHandle ourHandle;
  
  private static int clamp(int paramInt)
  {
    paramInt = (paramInt & (paramInt >> 31 ^ 0xFFFFFFFF)) - 255;
    return (paramInt & paramInt >> 31) + 255;
  }
  
  public static void log(String paramString)
  {
    Object localObject = new Throwable().getStackTrace()[1];
    String str1 = ((StackTraceElement)localObject).getMethodName();
    str1 = (str1 + "                  ").substring(0, 17);
    String str2 = Integer.toString(((StackTraceElement)localObject).getLineNumber());
    Log5ECF72.a(str2);
    LogE84000.a(str2);
    Log229316.a(str2);
    str2 = "    ".substring(str2.length());
    str1 = ".(" + ((StackTraceElement)localObject).getFileName() + ":" + ((StackTraceElement)localObject).getLineNumber() + ")" + str2 + str1;
    System.out.println(str1 + " " + paramString);
    localObject = ourHandle;
    if (localObject != null) {
      ((DebugHandle)localObject).message(str1 + " " + paramString);
    }
  }
  
  public static void log(String paramString1, String paramString2)
  {
    System.out.println(paramString1 + " : " + paramString2);
  }
  
  public static void logStack(String paramString, int paramInt)
  {
    StackTraceElement[] arrayOfStackTraceElement = new Throwable().getStackTrace();
    String str = " ";
    int i = Math.min(paramInt, arrayOfStackTraceElement.length - 1);
    for (paramInt = 1; paramInt <= i; paramInt++)
    {
      Object localObject = arrayOfStackTraceElement[paramInt];
      localObject = ".(" + arrayOfStackTraceElement[paramInt].getFileName() + ":" + arrayOfStackTraceElement[paramInt].getLineNumber() + ") " + arrayOfStackTraceElement[paramInt].getMethodName();
      str = str + " ";
      System.out.println(paramString + str + (String)localObject + str);
    }
  }
  
  public static void loge(String paramString1, String paramString2)
  {
    System.err.println(paramString1 + " : " + paramString2);
  }
  
  public static int rgbaTocColor(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
  {
    int k = clamp((int)(paramFloat1 * 255.0F));
    int j = clamp((int)(paramFloat2 * 255.0F));
    int i = clamp((int)(paramFloat3 * 255.0F));
    return clamp((int)(255.0F * paramFloat4)) << 24 | k << 16 | j << 8 | i;
  }
  
  public static void setDebugHandle(DebugHandle paramDebugHandle)
  {
    ourHandle = paramDebugHandle;
  }
  
  public static void socketSend(String paramString)
  {
    try
    {
      Object localObject = new java/net/Socket;
      ((Socket)localObject).<init>("127.0.0.1", 5327);
      localObject = ((Socket)localObject).getOutputStream();
      ((OutputStream)localObject).write(paramString.getBytes());
      ((OutputStream)localObject).close();
    }
    catch (IOException paramString)
    {
      paramString.printStackTrace();
    }
  }
  
  public int getInterpolatedColor(float[] paramArrayOfFloat)
  {
    int j = clamp((int)((float)Math.pow(paramArrayOfFloat[0], 0.45454545454545453D) * 255.0F));
    int k = clamp((int)((float)Math.pow(paramArrayOfFloat[1], 0.45454545454545453D) * 255.0F));
    int i = clamp((int)((float)Math.pow(paramArrayOfFloat[2], 0.45454545454545453D) * 255.0F));
    return clamp((int)(paramArrayOfFloat[3] * 255.0F)) << 24 | j << 16 | k << 8 | i;
  }
  
  public static abstract interface DebugHandle
  {
    public abstract void message(String paramString);
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/constraintlayout/core/motion/utils/Utils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */