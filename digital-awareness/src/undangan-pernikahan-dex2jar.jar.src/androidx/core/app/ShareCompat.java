package androidx.core.app;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipData.Item;
import android.content.ComponentName;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Parcelable;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.view.ActionProvider;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ShareActionProvider;
import androidx.core.util.Preconditions;
import java.util.ArrayList;
import mt.Log229316;
import mt.Log5ECF72;
import mt.LogE84000;

public final class ShareCompat
{
  public static final String EXTRA_CALLING_ACTIVITY = "androidx.core.app.EXTRA_CALLING_ACTIVITY";
  public static final String EXTRA_CALLING_ACTIVITY_INTEROP = "android.support.v4.app.EXTRA_CALLING_ACTIVITY";
  public static final String EXTRA_CALLING_PACKAGE = "androidx.core.app.EXTRA_CALLING_PACKAGE";
  public static final String EXTRA_CALLING_PACKAGE_INTEROP = "android.support.v4.app.EXTRA_CALLING_PACKAGE";
  private static final String HISTORY_FILENAME_PREFIX = ".sharecompat_";
  
  @Deprecated
  public static void configureMenuItem(Menu paramMenu, int paramInt, IntentBuilder paramIntentBuilder)
  {
    paramMenu = paramMenu.findItem(paramInt);
    if (paramMenu != null)
    {
      configureMenuItem(paramMenu, paramIntentBuilder);
      return;
    }
    throw new IllegalArgumentException("Could not find menu item with id " + paramInt + " in the supplied menu");
  }
  
  @Deprecated
  public static void configureMenuItem(MenuItem paramMenuItem, IntentBuilder paramIntentBuilder)
  {
    Object localObject = paramMenuItem.getActionProvider();
    if (!(localObject instanceof ShareActionProvider)) {
      localObject = new ShareActionProvider(paramIntentBuilder.getContext());
    } else {
      localObject = (ShareActionProvider)localObject;
    }
    ((ShareActionProvider)localObject).setShareHistoryFileName(".sharecompat_" + paramIntentBuilder.getContext().getClass().getName());
    ((ShareActionProvider)localObject).setShareIntent(paramIntentBuilder.getIntent());
    paramMenuItem.setActionProvider((ActionProvider)localObject);
    if ((Build.VERSION.SDK_INT < 16) && (!paramMenuItem.hasSubMenu())) {
      paramMenuItem.setIntent(paramIntentBuilder.createChooserIntent());
    }
  }
  
  public static ComponentName getCallingActivity(Activity paramActivity)
  {
    Intent localIntent = paramActivity.getIntent();
    ComponentName localComponentName = paramActivity.getCallingActivity();
    paramActivity = localComponentName;
    if (localComponentName == null) {
      paramActivity = getCallingActivity(localIntent);
    }
    return paramActivity;
  }
  
  static ComponentName getCallingActivity(Intent paramIntent)
  {
    ComponentName localComponentName2 = (ComponentName)paramIntent.getParcelableExtra("androidx.core.app.EXTRA_CALLING_ACTIVITY");
    ComponentName localComponentName1 = localComponentName2;
    if (localComponentName2 == null) {
      localComponentName1 = (ComponentName)paramIntent.getParcelableExtra("android.support.v4.app.EXTRA_CALLING_ACTIVITY");
    }
    return localComponentName1;
  }
  
  public static String getCallingPackage(Activity paramActivity)
  {
    Intent localIntent = paramActivity.getIntent();
    String str = paramActivity.getCallingPackage();
    paramActivity = str;
    if (str == null)
    {
      paramActivity = str;
      if (localIntent != null)
      {
        paramActivity = getCallingPackage(localIntent);
        Log5ECF72.a(paramActivity);
        LogE84000.a(paramActivity);
        Log229316.a(paramActivity);
      }
    }
    return paramActivity;
  }
  
  static String getCallingPackage(Intent paramIntent)
  {
    String str2 = paramIntent.getStringExtra("androidx.core.app.EXTRA_CALLING_PACKAGE");
    String str1 = str2;
    if (str2 == null) {
      str1 = paramIntent.getStringExtra("android.support.v4.app.EXTRA_CALLING_PACKAGE");
    }
    return str1;
  }
  
  private static class Api16Impl
  {
    static String escapeHtml(CharSequence paramCharSequence)
    {
      paramCharSequence = Html.escapeHtml(paramCharSequence);
      Log5ECF72.a(paramCharSequence);
      LogE84000.a(paramCharSequence);
      Log229316.a(paramCharSequence);
      return paramCharSequence;
    }
    
    static void migrateExtraStreamToClipData(Intent paramIntent, ArrayList<Uri> paramArrayList)
    {
      CharSequence localCharSequence = paramIntent.getCharSequenceExtra("android.intent.extra.TEXT");
      Object localObject2 = paramIntent.getStringExtra("android.intent.extra.HTML_TEXT");
      Object localObject1 = paramIntent.getType();
      localObject2 = new ClipData.Item(localCharSequence, (String)localObject2, null, (Uri)paramArrayList.get(0));
      localObject1 = new ClipData(null, new String[] { localObject1 }, (ClipData.Item)localObject2);
      int i = 1;
      int j = paramArrayList.size();
      while (i < j)
      {
        ((ClipData)localObject1).addItem(new ClipData.Item((Uri)paramArrayList.get(i)));
        i++;
      }
      paramIntent.setClipData((ClipData)localObject1);
      paramIntent.addFlags(1);
    }
    
    static void removeClipData(Intent paramIntent)
    {
      paramIntent.setClipData(null);
      paramIntent.setFlags(paramIntent.getFlags() & 0xFFFFFFFE);
    }
  }
  
  public static class IntentBuilder
  {
    private ArrayList<String> mBccAddresses;
    private ArrayList<String> mCcAddresses;
    private CharSequence mChooserTitle;
    private final Context mContext;
    private final Intent mIntent;
    private ArrayList<Uri> mStreams;
    private ArrayList<String> mToAddresses;
    
    public IntentBuilder(Context paramContext)
    {
      this.mContext = ((Context)Preconditions.checkNotNull(paramContext));
      Object localObject1 = new Intent().setAction("android.intent.action.SEND");
      this.mIntent = ((Intent)localObject1);
      ((Intent)localObject1).putExtra("androidx.core.app.EXTRA_CALLING_PACKAGE", paramContext.getPackageName());
      ((Intent)localObject1).putExtra("android.support.v4.app.EXTRA_CALLING_PACKAGE", paramContext.getPackageName());
      ((Intent)localObject1).addFlags(524288);
      Object localObject2 = null;
      for (;;)
      {
        localObject1 = localObject2;
        if (!(paramContext instanceof ContextWrapper)) {
          break;
        }
        if ((paramContext instanceof Activity))
        {
          localObject1 = (Activity)paramContext;
          break;
        }
        paramContext = ((ContextWrapper)paramContext).getBaseContext();
      }
      if (localObject1 != null)
      {
        paramContext = ((Activity)localObject1).getComponentName();
        this.mIntent.putExtra("androidx.core.app.EXTRA_CALLING_ACTIVITY", paramContext);
        this.mIntent.putExtra("android.support.v4.app.EXTRA_CALLING_ACTIVITY", paramContext);
      }
    }
    
    private void combineArrayExtra(String paramString, ArrayList<String> paramArrayList)
    {
      String[] arrayOfString1 = this.mIntent.getStringArrayExtra(paramString);
      int i;
      if (arrayOfString1 != null) {
        i = arrayOfString1.length;
      } else {
        i = 0;
      }
      String[] arrayOfString2 = new String[paramArrayList.size() + i];
      paramArrayList.toArray(arrayOfString2);
      if (arrayOfString1 != null) {
        System.arraycopy(arrayOfString1, 0, arrayOfString2, paramArrayList.size(), i);
      }
      this.mIntent.putExtra(paramString, arrayOfString2);
    }
    
    private void combineArrayExtra(String paramString, String[] paramArrayOfString)
    {
      Intent localIntent = getIntent();
      String[] arrayOfString1 = localIntent.getStringArrayExtra(paramString);
      int i;
      if (arrayOfString1 != null) {
        i = arrayOfString1.length;
      } else {
        i = 0;
      }
      String[] arrayOfString2 = new String[paramArrayOfString.length + i];
      if (arrayOfString1 != null) {
        System.arraycopy(arrayOfString1, 0, arrayOfString2, 0, i);
      }
      System.arraycopy(paramArrayOfString, 0, arrayOfString2, i, paramArrayOfString.length);
      localIntent.putExtra(paramString, arrayOfString2);
    }
    
    @Deprecated
    public static IntentBuilder from(Activity paramActivity)
    {
      return new IntentBuilder(paramActivity);
    }
    
    public IntentBuilder addEmailBcc(String paramString)
    {
      if (this.mBccAddresses == null) {
        this.mBccAddresses = new ArrayList();
      }
      this.mBccAddresses.add(paramString);
      return this;
    }
    
    public IntentBuilder addEmailBcc(String[] paramArrayOfString)
    {
      combineArrayExtra("android.intent.extra.BCC", paramArrayOfString);
      return this;
    }
    
    public IntentBuilder addEmailCc(String paramString)
    {
      if (this.mCcAddresses == null) {
        this.mCcAddresses = new ArrayList();
      }
      this.mCcAddresses.add(paramString);
      return this;
    }
    
    public IntentBuilder addEmailCc(String[] paramArrayOfString)
    {
      combineArrayExtra("android.intent.extra.CC", paramArrayOfString);
      return this;
    }
    
    public IntentBuilder addEmailTo(String paramString)
    {
      if (this.mToAddresses == null) {
        this.mToAddresses = new ArrayList();
      }
      this.mToAddresses.add(paramString);
      return this;
    }
    
    public IntentBuilder addEmailTo(String[] paramArrayOfString)
    {
      combineArrayExtra("android.intent.extra.EMAIL", paramArrayOfString);
      return this;
    }
    
    public IntentBuilder addStream(Uri paramUri)
    {
      if (this.mStreams == null) {
        this.mStreams = new ArrayList();
      }
      this.mStreams.add(paramUri);
      return this;
    }
    
    public Intent createChooserIntent()
    {
      return Intent.createChooser(getIntent(), this.mChooserTitle);
    }
    
    Context getContext()
    {
      return this.mContext;
    }
    
    public Intent getIntent()
    {
      ArrayList localArrayList = this.mToAddresses;
      if (localArrayList != null)
      {
        combineArrayExtra("android.intent.extra.EMAIL", localArrayList);
        this.mToAddresses = null;
      }
      localArrayList = this.mCcAddresses;
      if (localArrayList != null)
      {
        combineArrayExtra("android.intent.extra.CC", localArrayList);
        this.mCcAddresses = null;
      }
      localArrayList = this.mBccAddresses;
      if (localArrayList != null)
      {
        combineArrayExtra("android.intent.extra.BCC", localArrayList);
        this.mBccAddresses = null;
      }
      localArrayList = this.mStreams;
      int i = 1;
      if ((localArrayList == null) || (localArrayList.size() <= 1)) {
        i = 0;
      }
      if (i == 0)
      {
        this.mIntent.setAction("android.intent.action.SEND");
        localArrayList = this.mStreams;
        if ((localArrayList != null) && (!localArrayList.isEmpty()))
        {
          this.mIntent.putExtra("android.intent.extra.STREAM", (Parcelable)this.mStreams.get(0));
          if (Build.VERSION.SDK_INT >= 16) {
            ShareCompat.Api16Impl.migrateExtraStreamToClipData(this.mIntent, this.mStreams);
          }
        }
        else
        {
          this.mIntent.removeExtra("android.intent.extra.STREAM");
          if (Build.VERSION.SDK_INT >= 16) {
            ShareCompat.Api16Impl.removeClipData(this.mIntent);
          }
        }
      }
      else
      {
        this.mIntent.setAction("android.intent.action.SEND_MULTIPLE");
        this.mIntent.putParcelableArrayListExtra("android.intent.extra.STREAM", this.mStreams);
        if (Build.VERSION.SDK_INT >= 16) {
          ShareCompat.Api16Impl.migrateExtraStreamToClipData(this.mIntent, this.mStreams);
        }
      }
      return this.mIntent;
    }
    
    public IntentBuilder setChooserTitle(int paramInt)
    {
      return setChooserTitle(this.mContext.getText(paramInt));
    }
    
    public IntentBuilder setChooserTitle(CharSequence paramCharSequence)
    {
      this.mChooserTitle = paramCharSequence;
      return this;
    }
    
    public IntentBuilder setEmailBcc(String[] paramArrayOfString)
    {
      this.mIntent.putExtra("android.intent.extra.BCC", paramArrayOfString);
      return this;
    }
    
    public IntentBuilder setEmailCc(String[] paramArrayOfString)
    {
      this.mIntent.putExtra("android.intent.extra.CC", paramArrayOfString);
      return this;
    }
    
    public IntentBuilder setEmailTo(String[] paramArrayOfString)
    {
      if (this.mToAddresses != null) {
        this.mToAddresses = null;
      }
      this.mIntent.putExtra("android.intent.extra.EMAIL", paramArrayOfString);
      return this;
    }
    
    public IntentBuilder setHtmlText(String paramString)
    {
      this.mIntent.putExtra("android.intent.extra.HTML_TEXT", paramString);
      if (!this.mIntent.hasExtra("android.intent.extra.TEXT")) {
        setText(Html.fromHtml(paramString));
      }
      return this;
    }
    
    public IntentBuilder setStream(Uri paramUri)
    {
      this.mStreams = null;
      if (paramUri != null) {
        addStream(paramUri);
      }
      return this;
    }
    
    public IntentBuilder setSubject(String paramString)
    {
      this.mIntent.putExtra("android.intent.extra.SUBJECT", paramString);
      return this;
    }
    
    public IntentBuilder setText(CharSequence paramCharSequence)
    {
      this.mIntent.putExtra("android.intent.extra.TEXT", paramCharSequence);
      return this;
    }
    
    public IntentBuilder setType(String paramString)
    {
      this.mIntent.setType(paramString);
      return this;
    }
    
    public void startChooser()
    {
      this.mContext.startActivity(createChooserIntent());
    }
  }
  
  public static class IntentReader
  {
    private static final String TAG = "IntentReader";
    private final ComponentName mCallingActivity;
    private final String mCallingPackage;
    private final Context mContext;
    private final Intent mIntent;
    private ArrayList<Uri> mStreams;
    
    public IntentReader(Activity paramActivity)
    {
      this((Context)Preconditions.checkNotNull(paramActivity), paramActivity.getIntent());
    }
    
    public IntentReader(Context paramContext, Intent paramIntent)
    {
      this.mContext = ((Context)Preconditions.checkNotNull(paramContext));
      this.mIntent = ((Intent)Preconditions.checkNotNull(paramIntent));
      paramContext = ShareCompat.getCallingPackage(paramIntent);
      Log5ECF72.a(paramContext);
      LogE84000.a(paramContext);
      Log229316.a(paramContext);
      this.mCallingPackage = paramContext;
      this.mCallingActivity = ShareCompat.getCallingActivity(paramIntent);
    }
    
    @Deprecated
    public static IntentReader from(Activity paramActivity)
    {
      return new IntentReader(paramActivity);
    }
    
    private static void withinStyle(StringBuilder paramStringBuilder, CharSequence paramCharSequence, int paramInt1, int paramInt2)
    {
      while (paramInt1 < paramInt2)
      {
        char c = paramCharSequence.charAt(paramInt1);
        if (c == '<') {
          paramStringBuilder.append("&lt;");
        } else if (c == '>') {
          paramStringBuilder.append("&gt;");
        } else if (c == '&') {
          paramStringBuilder.append("&amp;");
        } else if ((c <= '~') && (c >= ' '))
        {
          if (c == ' ')
          {
            while ((paramInt1 + 1 < paramInt2) && (paramCharSequence.charAt(paramInt1 + 1) == ' '))
            {
              paramStringBuilder.append("&nbsp;");
              paramInt1++;
            }
            paramStringBuilder.append(' ');
          }
          else
          {
            paramStringBuilder.append(c);
          }
        }
        else {
          paramStringBuilder.append("&#").append(c).append(";");
        }
        paramInt1++;
      }
    }
    
    public ComponentName getCallingActivity()
    {
      return this.mCallingActivity;
    }
    
    public Drawable getCallingActivityIcon()
    {
      if (this.mCallingActivity == null) {
        return null;
      }
      Object localObject = this.mContext.getPackageManager();
      try
      {
        localObject = ((PackageManager)localObject).getActivityIcon(this.mCallingActivity);
        return (Drawable)localObject;
      }
      catch (PackageManager.NameNotFoundException localNameNotFoundException)
      {
        Log.e("IntentReader", "Could not retrieve icon for calling activity", localNameNotFoundException);
      }
      return null;
    }
    
    public Drawable getCallingApplicationIcon()
    {
      if (this.mCallingPackage == null) {
        return null;
      }
      Object localObject = this.mContext.getPackageManager();
      try
      {
        localObject = ((PackageManager)localObject).getApplicationIcon(this.mCallingPackage);
        return (Drawable)localObject;
      }
      catch (PackageManager.NameNotFoundException localNameNotFoundException)
      {
        Log.e("IntentReader", "Could not retrieve icon for calling application", localNameNotFoundException);
      }
      return null;
    }
    
    public CharSequence getCallingApplicationLabel()
    {
      if (this.mCallingPackage == null) {
        return null;
      }
      Object localObject = this.mContext.getPackageManager();
      try
      {
        localObject = ((PackageManager)localObject).getApplicationLabel(((PackageManager)localObject).getApplicationInfo(this.mCallingPackage, 0));
        return (CharSequence)localObject;
      }
      catch (PackageManager.NameNotFoundException localNameNotFoundException)
      {
        Log.e("IntentReader", "Could not retrieve label for calling application", localNameNotFoundException);
      }
      return null;
    }
    
    public String getCallingPackage()
    {
      return this.mCallingPackage;
    }
    
    public String[] getEmailBcc()
    {
      return this.mIntent.getStringArrayExtra("android.intent.extra.BCC");
    }
    
    public String[] getEmailCc()
    {
      return this.mIntent.getStringArrayExtra("android.intent.extra.CC");
    }
    
    public String[] getEmailTo()
    {
      return this.mIntent.getStringArrayExtra("android.intent.extra.EMAIL");
    }
    
    public String getHtmlText()
    {
      String str = this.mIntent.getStringExtra("android.intent.extra.HTML_TEXT");
      Object localObject = str;
      if (str == null)
      {
        CharSequence localCharSequence = getText();
        if ((localCharSequence instanceof Spanned))
        {
          localObject = Html.toHtml((Spanned)localCharSequence);
          Log5ECF72.a(localObject);
          LogE84000.a(localObject);
          Log229316.a(localObject);
        }
        else
        {
          localObject = str;
          if (localCharSequence != null) {
            if (Build.VERSION.SDK_INT >= 16)
            {
              localObject = ShareCompat.Api16Impl.escapeHtml(localCharSequence);
              Log5ECF72.a(localObject);
              LogE84000.a(localObject);
              Log229316.a(localObject);
            }
            else
            {
              localObject = new StringBuilder();
              withinStyle((StringBuilder)localObject, localCharSequence, 0, localCharSequence.length());
              localObject = ((StringBuilder)localObject).toString();
            }
          }
        }
      }
      return (String)localObject;
    }
    
    public Uri getStream()
    {
      return (Uri)this.mIntent.getParcelableExtra("android.intent.extra.STREAM");
    }
    
    public Uri getStream(int paramInt)
    {
      if ((this.mStreams == null) && (isMultipleShare())) {
        this.mStreams = this.mIntent.getParcelableArrayListExtra("android.intent.extra.STREAM");
      }
      ArrayList localArrayList = this.mStreams;
      if (localArrayList != null) {
        return (Uri)localArrayList.get(paramInt);
      }
      if (paramInt == 0) {
        return (Uri)this.mIntent.getParcelableExtra("android.intent.extra.STREAM");
      }
      throw new IndexOutOfBoundsException("Stream items available: " + getStreamCount() + " index requested: " + paramInt);
    }
    
    public int getStreamCount()
    {
      if ((this.mStreams == null) && (isMultipleShare())) {
        this.mStreams = this.mIntent.getParcelableArrayListExtra("android.intent.extra.STREAM");
      }
      ArrayList localArrayList = this.mStreams;
      if (localArrayList != null) {
        return localArrayList.size();
      }
      return this.mIntent.hasExtra("android.intent.extra.STREAM");
    }
    
    public String getSubject()
    {
      return this.mIntent.getStringExtra("android.intent.extra.SUBJECT");
    }
    
    public CharSequence getText()
    {
      return this.mIntent.getCharSequenceExtra("android.intent.extra.TEXT");
    }
    
    public String getType()
    {
      return this.mIntent.getType();
    }
    
    public boolean isMultipleShare()
    {
      return "android.intent.action.SEND_MULTIPLE".equals(this.mIntent.getAction());
    }
    
    public boolean isShareIntent()
    {
      String str = this.mIntent.getAction();
      boolean bool;
      if ((!"android.intent.action.SEND".equals(str)) && (!"android.intent.action.SEND_MULTIPLE".equals(str))) {
        bool = false;
      } else {
        bool = true;
      }
      return bool;
    }
    
    public boolean isSingleShare()
    {
      return "android.intent.action.SEND".equals(this.mIntent.getAction());
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/core/app/ShareCompat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */