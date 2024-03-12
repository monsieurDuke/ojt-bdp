package androidx.core.view.inputmethod;

import android.content.ClipDescription;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.view.inputmethod.InputConnectionWrapper;
import android.view.inputmethod.InputContentInfo;
import androidx.core.util.ObjectsCompat;
import androidx.core.util.Preconditions;

public final class InputConnectionCompat
{
  private static final String COMMIT_CONTENT_ACTION = "androidx.core.view.inputmethod.InputConnectionCompat.COMMIT_CONTENT";
  private static final String COMMIT_CONTENT_CONTENT_URI_INTEROP_KEY = "android.support.v13.view.inputmethod.InputConnectionCompat.CONTENT_URI";
  private static final String COMMIT_CONTENT_CONTENT_URI_KEY = "androidx.core.view.inputmethod.InputConnectionCompat.CONTENT_URI";
  private static final String COMMIT_CONTENT_DESCRIPTION_INTEROP_KEY = "android.support.v13.view.inputmethod.InputConnectionCompat.CONTENT_DESCRIPTION";
  private static final String COMMIT_CONTENT_DESCRIPTION_KEY = "androidx.core.view.inputmethod.InputConnectionCompat.CONTENT_DESCRIPTION";
  private static final String COMMIT_CONTENT_FLAGS_INTEROP_KEY = "android.support.v13.view.inputmethod.InputConnectionCompat.CONTENT_FLAGS";
  private static final String COMMIT_CONTENT_FLAGS_KEY = "androidx.core.view.inputmethod.InputConnectionCompat.CONTENT_FLAGS";
  private static final String COMMIT_CONTENT_INTEROP_ACTION = "android.support.v13.view.inputmethod.InputConnectionCompat.COMMIT_CONTENT";
  private static final String COMMIT_CONTENT_LINK_URI_INTEROP_KEY = "android.support.v13.view.inputmethod.InputConnectionCompat.CONTENT_LINK_URI";
  private static final String COMMIT_CONTENT_LINK_URI_KEY = "androidx.core.view.inputmethod.InputConnectionCompat.CONTENT_LINK_URI";
  private static final String COMMIT_CONTENT_OPTS_INTEROP_KEY = "android.support.v13.view.inputmethod.InputConnectionCompat.CONTENT_OPTS";
  private static final String COMMIT_CONTENT_OPTS_KEY = "androidx.core.view.inputmethod.InputConnectionCompat.CONTENT_OPTS";
  private static final String COMMIT_CONTENT_RESULT_INTEROP_RECEIVER_KEY = "android.support.v13.view.inputmethod.InputConnectionCompat.CONTENT_RESULT_RECEIVER";
  private static final String COMMIT_CONTENT_RESULT_RECEIVER_KEY = "androidx.core.view.inputmethod.InputConnectionCompat.CONTENT_RESULT_RECEIVER";
  private static final String EXTRA_INPUT_CONTENT_INFO = "androidx.core.view.extra.INPUT_CONTENT_INFO";
  public static final int INPUT_CONTENT_GRANT_READ_URI_PERMISSION = 1;
  private static final String LOG_TAG = "InputConnectionCompat";
  
  public static boolean commitContent(InputConnection paramInputConnection, EditorInfo paramEditorInfo, InputContentInfoCompat paramInputContentInfoCompat, int paramInt, Bundle paramBundle)
  {
    if (Build.VERSION.SDK_INT >= 25) {
      return Api25Impl.commitContent(paramInputConnection, (InputContentInfo)paramInputContentInfoCompat.unwrap(), paramInt, paramBundle);
    }
    int i;
    switch (EditorInfoCompat.getProtocol(paramEditorInfo))
    {
    default: 
      return false;
    case 3: 
    case 4: 
      i = 0;
      break;
    case 2: 
      i = 1;
    }
    Bundle localBundle = new Bundle();
    if (i != 0) {
      paramEditorInfo = "android.support.v13.view.inputmethod.InputConnectionCompat.CONTENT_URI";
    } else {
      paramEditorInfo = "androidx.core.view.inputmethod.InputConnectionCompat.CONTENT_URI";
    }
    localBundle.putParcelable(paramEditorInfo, paramInputContentInfoCompat.getContentUri());
    if (i != 0) {
      paramEditorInfo = "android.support.v13.view.inputmethod.InputConnectionCompat.CONTENT_DESCRIPTION";
    } else {
      paramEditorInfo = "androidx.core.view.inputmethod.InputConnectionCompat.CONTENT_DESCRIPTION";
    }
    localBundle.putParcelable(paramEditorInfo, paramInputContentInfoCompat.getDescription());
    if (i != 0) {
      paramEditorInfo = "android.support.v13.view.inputmethod.InputConnectionCompat.CONTENT_LINK_URI";
    } else {
      paramEditorInfo = "androidx.core.view.inputmethod.InputConnectionCompat.CONTENT_LINK_URI";
    }
    localBundle.putParcelable(paramEditorInfo, paramInputContentInfoCompat.getLinkUri());
    if (i != 0) {
      paramEditorInfo = "android.support.v13.view.inputmethod.InputConnectionCompat.CONTENT_FLAGS";
    } else {
      paramEditorInfo = "androidx.core.view.inputmethod.InputConnectionCompat.CONTENT_FLAGS";
    }
    localBundle.putInt(paramEditorInfo, paramInt);
    if (i != 0) {
      paramEditorInfo = "android.support.v13.view.inputmethod.InputConnectionCompat.CONTENT_OPTS";
    } else {
      paramEditorInfo = "androidx.core.view.inputmethod.InputConnectionCompat.CONTENT_OPTS";
    }
    localBundle.putParcelable(paramEditorInfo, paramBundle);
    if (i != 0) {
      paramEditorInfo = "android.support.v13.view.inputmethod.InputConnectionCompat.COMMIT_CONTENT";
    } else {
      paramEditorInfo = "androidx.core.view.inputmethod.InputConnectionCompat.COMMIT_CONTENT";
    }
    return paramInputConnection.performPrivateCommand(paramEditorInfo, localBundle);
  }
  
  private static OnCommitContentListener createOnCommitContentListenerUsingPerformReceiveContent(View paramView)
  {
    Preconditions.checkNotNull(paramView);
    return new InputConnectionCompat..ExternalSyntheticLambda0(paramView);
  }
  
  public static InputConnection createWrapper(View paramView, InputConnection paramInputConnection, EditorInfo paramEditorInfo)
  {
    return createWrapper(paramInputConnection, paramEditorInfo, createOnCommitContentListenerUsingPerformReceiveContent(paramView));
  }
  
  @Deprecated
  public static InputConnection createWrapper(InputConnection paramInputConnection, EditorInfo paramEditorInfo, final OnCommitContentListener paramOnCommitContentListener)
  {
    ObjectsCompat.requireNonNull(paramInputConnection, "inputConnection must be non-null");
    ObjectsCompat.requireNonNull(paramEditorInfo, "editorInfo must be non-null");
    ObjectsCompat.requireNonNull(paramOnCommitContentListener, "onCommitContentListener must be non-null");
    if (Build.VERSION.SDK_INT >= 25) {
      new InputConnectionWrapper(paramInputConnection, false)
      {
        public boolean commitContent(InputContentInfo paramAnonymousInputContentInfo, int paramAnonymousInt, Bundle paramAnonymousBundle)
        {
          if (paramOnCommitContentListener.onCommitContent(InputContentInfoCompat.wrap(paramAnonymousInputContentInfo), paramAnonymousInt, paramAnonymousBundle)) {
            return true;
          }
          return super.commitContent(paramAnonymousInputContentInfo, paramAnonymousInt, paramAnonymousBundle);
        }
      };
    }
    if (EditorInfoCompat.getContentMimeTypes(paramEditorInfo).length == 0) {
      return paramInputConnection;
    }
    new InputConnectionWrapper(paramInputConnection, false)
    {
      public boolean performPrivateCommand(String paramAnonymousString, Bundle paramAnonymousBundle)
      {
        if (InputConnectionCompat.handlePerformPrivateCommand(paramAnonymousString, paramAnonymousBundle, paramOnCommitContentListener)) {
          return true;
        }
        return super.performPrivateCommand(paramAnonymousString, paramAnonymousBundle);
      }
    };
  }
  
  static boolean handlePerformPrivateCommand(String paramString, Bundle paramBundle, OnCommitContentListener paramOnCommitContentListener)
  {
    int j = 0;
    if (paramBundle == null) {
      return false;
    }
    int i;
    if (TextUtils.equals("androidx.core.view.inputmethod.InputConnectionCompat.COMMIT_CONTENT", paramString))
    {
      i = 0;
    }
    else
    {
      if (!TextUtils.equals("android.support.v13.view.inputmethod.InputConnectionCompat.COMMIT_CONTENT", paramString)) {
        break label299;
      }
      i = 1;
    }
    paramString = null;
    boolean bool2 = false;
    Object localObject;
    if (i != 0) {
      localObject = "android.support.v13.view.inputmethod.InputConnectionCompat.CONTENT_RESULT_RECEIVER";
    } else {
      localObject = "androidx.core.view.inputmethod.InputConnectionCompat.CONTENT_RESULT_RECEIVER";
    }
    try
    {
      ResultReceiver localResultReceiver = (ResultReceiver)paramBundle.getParcelable((String)localObject);
      if (i != 0) {
        localObject = "android.support.v13.view.inputmethod.InputConnectionCompat.CONTENT_URI";
      } else {
        localObject = "androidx.core.view.inputmethod.InputConnectionCompat.CONTENT_URI";
      }
      paramString = localResultReceiver;
      Uri localUri1 = (Uri)paramBundle.getParcelable((String)localObject);
      if (i != 0) {
        localObject = "android.support.v13.view.inputmethod.InputConnectionCompat.CONTENT_DESCRIPTION";
      } else {
        localObject = "androidx.core.view.inputmethod.InputConnectionCompat.CONTENT_DESCRIPTION";
      }
      paramString = localResultReceiver;
      ClipDescription localClipDescription = (ClipDescription)paramBundle.getParcelable((String)localObject);
      if (i != 0) {
        localObject = "android.support.v13.view.inputmethod.InputConnectionCompat.CONTENT_LINK_URI";
      } else {
        localObject = "androidx.core.view.inputmethod.InputConnectionCompat.CONTENT_LINK_URI";
      }
      paramString = localResultReceiver;
      Uri localUri2 = (Uri)paramBundle.getParcelable((String)localObject);
      if (i != 0) {
        localObject = "android.support.v13.view.inputmethod.InputConnectionCompat.CONTENT_FLAGS";
      } else {
        localObject = "androidx.core.view.inputmethod.InputConnectionCompat.CONTENT_FLAGS";
      }
      paramString = localResultReceiver;
      int k = paramBundle.getInt((String)localObject);
      if (i != 0) {
        localObject = "android.support.v13.view.inputmethod.InputConnectionCompat.CONTENT_OPTS";
      } else {
        localObject = "androidx.core.view.inputmethod.InputConnectionCompat.CONTENT_OPTS";
      }
      paramString = localResultReceiver;
      localObject = (Bundle)paramBundle.getParcelable((String)localObject);
      boolean bool1 = bool2;
      if (localUri1 != null)
      {
        bool1 = bool2;
        if (localClipDescription != null)
        {
          paramString = localResultReceiver;
          paramBundle = new androidx/core/view/inputmethod/InputContentInfoCompat;
          paramString = localResultReceiver;
          paramBundle.<init>(localUri1, localClipDescription, localUri2);
          paramString = localResultReceiver;
          bool1 = paramOnCommitContentListener.onCommitContent(paramBundle, k, (Bundle)localObject);
        }
      }
      if (localResultReceiver != null)
      {
        i = j;
        if (bool1) {
          i = 1;
        }
        localResultReceiver.send(i, null);
      }
      return bool1;
    }
    finally
    {
      if (paramString != null) {
        paramString.send(0, null);
      }
    }
    label299:
    return false;
  }
  
  static class Api25Impl
  {
    static boolean commitContent(InputConnection paramInputConnection, InputContentInfo paramInputContentInfo, int paramInt, Bundle paramBundle)
    {
      return paramInputConnection.commitContent(paramInputContentInfo, paramInt, paramBundle);
    }
  }
  
  public static abstract interface OnCommitContentListener
  {
    public abstract boolean onCommitContent(InputContentInfoCompat paramInputContentInfoCompat, int paramInt, Bundle paramBundle);
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/core/view/inputmethod/InputConnectionCompat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */