package androidx.core.content;

import android.content.ContentProvider;
import android.content.Context;

public final class ContentProviderCompat
{
  public static Context requireContext(ContentProvider paramContentProvider)
  {
    paramContentProvider = paramContentProvider.getContext();
    if (paramContentProvider != null) {
      return paramContentProvider;
    }
    throw new IllegalStateException("Cannot find context from the provider.");
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/core/content/ContentProviderCompat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */