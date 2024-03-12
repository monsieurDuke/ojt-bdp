package androidx.emoji2.viewsintegration;

import android.text.Editable;
import android.text.Editable.Factory;
import androidx.emoji2.text.SpannableBuilder;

final class EmojiEditableFactory
  extends Editable.Factory
{
  private static final Object INSTANCE_LOCK = new Object();
  private static volatile Editable.Factory sInstance;
  private static Class<?> sWatcherClass;
  
  /* Error */
  private EmojiEditableFactory()
  {
    // Byte code:
    //   0: aload_0
    //   1: invokespecial 22	android/text/Editable$Factory:<init>	()V
    //   4: ldc 24
    //   6: iconst_0
    //   7: aload_0
    //   8: invokevirtual 28	java/lang/Object:getClass	()Ljava/lang/Class;
    //   11: invokevirtual 34	java/lang/Class:getClassLoader	()Ljava/lang/ClassLoader;
    //   14: invokestatic 38	java/lang/Class:forName	(Ljava/lang/String;ZLjava/lang/ClassLoader;)Ljava/lang/Class;
    //   17: putstatic 40	androidx/emoji2/viewsintegration/EmojiEditableFactory:sWatcherClass	Ljava/lang/Class;
    //   20: goto +4 -> 24
    //   23: astore_1
    //   24: return
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	25	0	this	EmojiEditableFactory
    //   23	1	1	localObject	Object
    // Exception table:
    //   from	to	target	type
    //   4	20	23	finally
  }
  
  public static Editable.Factory getInstance()
  {
    if (sInstance == null) {
      synchronized (INSTANCE_LOCK)
      {
        if (sInstance == null)
        {
          EmojiEditableFactory localEmojiEditableFactory = new androidx/emoji2/viewsintegration/EmojiEditableFactory;
          localEmojiEditableFactory.<init>();
          sInstance = localEmojiEditableFactory;
        }
      }
    }
    return sInstance;
  }
  
  public Editable newEditable(CharSequence paramCharSequence)
  {
    Class localClass = sWatcherClass;
    if (localClass != null) {
      return SpannableBuilder.create(localClass, paramCharSequence);
    }
    return super.newEditable(paramCharSequence);
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/emoji2/viewsintegration/EmojiEditableFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */