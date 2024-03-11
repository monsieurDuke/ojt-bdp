package androidx.core.app;

import android.content.ClipData;
import android.content.ClipData.Item;
import android.content.ClipDescription;
import android.content.Intent;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Bundle;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import mt.Log229316;
import mt.Log5ECF72;
import mt.LogE84000;

public final class RemoteInput
{
  public static final int EDIT_CHOICES_BEFORE_SENDING_AUTO = 0;
  public static final int EDIT_CHOICES_BEFORE_SENDING_DISABLED = 1;
  public static final int EDIT_CHOICES_BEFORE_SENDING_ENABLED = 2;
  private static final String EXTRA_DATA_TYPE_RESULTS_DATA = "android.remoteinput.dataTypeResultsData";
  public static final String EXTRA_RESULTS_DATA = "android.remoteinput.resultsData";
  private static final String EXTRA_RESULTS_SOURCE = "android.remoteinput.resultsSource";
  public static final String RESULTS_CLIP_LABEL = "android.remoteinput.results";
  public static final int SOURCE_CHOICE = 1;
  public static final int SOURCE_FREE_FORM_INPUT = 0;
  private final boolean mAllowFreeFormTextInput;
  private final Set<String> mAllowedDataTypes;
  private final CharSequence[] mChoices;
  private final int mEditChoicesBeforeSending;
  private final Bundle mExtras;
  private final CharSequence mLabel;
  private final String mResultKey;
  
  RemoteInput(String paramString, CharSequence paramCharSequence, CharSequence[] paramArrayOfCharSequence, boolean paramBoolean, int paramInt, Bundle paramBundle, Set<String> paramSet)
  {
    this.mResultKey = paramString;
    this.mLabel = paramCharSequence;
    this.mChoices = paramArrayOfCharSequence;
    this.mAllowFreeFormTextInput = paramBoolean;
    this.mEditChoicesBeforeSending = paramInt;
    this.mExtras = paramBundle;
    this.mAllowedDataTypes = paramSet;
    if ((getEditChoicesBeforeSending() == 2) && (!getAllowFreeFormInput())) {
      throw new IllegalArgumentException("setEditChoicesBeforeSending requires setAllowFreeFormInput");
    }
  }
  
  public static void addDataResultToIntent(RemoteInput paramRemoteInput, Intent paramIntent, Map<String, Uri> paramMap)
  {
    if (Build.VERSION.SDK_INT >= 26)
    {
      Api26Impl.addDataResultToIntent(paramRemoteInput, paramIntent, paramMap);
    }
    else if (Build.VERSION.SDK_INT >= 16)
    {
      Object localObject2 = getClipDataIntentFromIntent(paramIntent);
      Object localObject1 = localObject2;
      if (localObject2 == null) {
        localObject1 = new Intent();
      }
      Iterator localIterator = paramMap.entrySet().iterator();
      while (localIterator.hasNext())
      {
        paramMap = (Map.Entry)localIterator.next();
        String str = (String)paramMap.getKey();
        Uri localUri = (Uri)paramMap.getValue();
        if (str != null)
        {
          paramMap = getExtraResultsKeyForData(str);
          Log5ECF72.a(paramMap);
          LogE84000.a(paramMap);
          Log229316.a(paramMap);
          localObject2 = ((Intent)localObject1).getBundleExtra(paramMap);
          paramMap = (Map<String, Uri>)localObject2;
          if (localObject2 == null) {
            paramMap = new Bundle();
          }
          paramMap.putString(paramRemoteInput.getResultKey(), localUri.toString());
          localObject2 = getExtraResultsKeyForData(str);
          Log5ECF72.a(localObject2);
          LogE84000.a(localObject2);
          Log229316.a(localObject2);
          ((Intent)localObject1).putExtra((String)localObject2, paramMap);
        }
      }
      Api16Impl.setClipData(paramIntent, ClipData.newIntent("android.remoteinput.results", (Intent)localObject1));
    }
  }
  
  public static void addResultsToIntent(RemoteInput[] paramArrayOfRemoteInput, Intent paramIntent, Bundle paramBundle)
  {
    if (Build.VERSION.SDK_INT >= 26)
    {
      Api20Impl.addResultsToIntent(fromCompat(paramArrayOfRemoteInput), paramIntent, paramBundle);
    }
    else
    {
      int j = Build.VERSION.SDK_INT;
      int i = 0;
      Object localObject1;
      Object localObject2;
      if (j >= 20)
      {
        localObject1 = getResultsFromIntent(paramIntent);
        j = getResultsSource(paramIntent);
        if (localObject1 != null)
        {
          ((Bundle)localObject1).putAll(paramBundle);
          paramBundle = (Bundle)localObject1;
        }
        int k = paramArrayOfRemoteInput.length;
        for (i = 0; i < k; i++)
        {
          localObject1 = paramArrayOfRemoteInput[i];
          localObject2 = getDataResultsFromIntent(paramIntent, ((RemoteInput)localObject1).getResultKey());
          Api20Impl.addResultsToIntent(fromCompat(new RemoteInput[] { localObject1 }), paramIntent, paramBundle);
          if (localObject2 != null) {
            addDataResultToIntent((RemoteInput)localObject1, paramIntent, (Map)localObject2);
          }
        }
        setResultsSource(paramIntent, j);
      }
      else if (Build.VERSION.SDK_INT >= 16)
      {
        localObject2 = getClipDataIntentFromIntent(paramIntent);
        localObject1 = localObject2;
        if (localObject2 == null) {
          localObject1 = new Intent();
        }
        Object localObject3 = ((Intent)localObject1).getBundleExtra("android.remoteinput.resultsData");
        localObject2 = localObject3;
        if (localObject3 == null) {
          localObject2 = new Bundle();
        }
        j = paramArrayOfRemoteInput.length;
        while (i < j)
        {
          localObject3 = paramArrayOfRemoteInput[i];
          Object localObject4 = paramBundle.get(((RemoteInput)localObject3).getResultKey());
          if ((localObject4 instanceof CharSequence)) {
            ((Bundle)localObject2).putCharSequence(((RemoteInput)localObject3).getResultKey(), (CharSequence)localObject4);
          }
          i++;
        }
        ((Intent)localObject1).putExtra("android.remoteinput.resultsData", (Bundle)localObject2);
        Api16Impl.setClipData(paramIntent, ClipData.newIntent("android.remoteinput.results", (Intent)localObject1));
      }
    }
  }
  
  static android.app.RemoteInput fromCompat(RemoteInput paramRemoteInput)
  {
    return Api20Impl.fromCompat(paramRemoteInput);
  }
  
  static android.app.RemoteInput[] fromCompat(RemoteInput[] paramArrayOfRemoteInput)
  {
    if (paramArrayOfRemoteInput == null) {
      return null;
    }
    android.app.RemoteInput[] arrayOfRemoteInput = new android.app.RemoteInput[paramArrayOfRemoteInput.length];
    for (int i = 0; i < paramArrayOfRemoteInput.length; i++) {
      arrayOfRemoteInput[i] = fromCompat(paramArrayOfRemoteInput[i]);
    }
    return arrayOfRemoteInput;
  }
  
  static RemoteInput fromPlatform(android.app.RemoteInput paramRemoteInput)
  {
    return Api20Impl.fromPlatform(paramRemoteInput);
  }
  
  private static Intent getClipDataIntentFromIntent(Intent paramIntent)
  {
    paramIntent = Api16Impl.getClipData(paramIntent);
    if (paramIntent == null) {
      return null;
    }
    ClipDescription localClipDescription = paramIntent.getDescription();
    if (!localClipDescription.hasMimeType("text/vnd.android.intent")) {
      return null;
    }
    if (!localClipDescription.getLabel().toString().contentEquals("android.remoteinput.results")) {
      return null;
    }
    return paramIntent.getItemAt(0).getIntent();
  }
  
  public static Map<String, Uri> getDataResultsFromIntent(Intent paramIntent, String paramString)
  {
    if (Build.VERSION.SDK_INT >= 26) {
      return Api26Impl.getDataResultsFromIntent(paramIntent, paramString);
    }
    int i = Build.VERSION.SDK_INT;
    Object localObject = null;
    if (i >= 16)
    {
      Intent localIntent = getClipDataIntentFromIntent(paramIntent);
      if (localIntent == null) {
        return null;
      }
      paramIntent = new HashMap();
      Iterator localIterator = localIntent.getExtras().keySet().iterator();
      while (localIterator.hasNext())
      {
        String str2 = (String)localIterator.next();
        if (str2.startsWith("android.remoteinput.dataTypeResultsData"))
        {
          String str1 = str2.substring("android.remoteinput.dataTypeResultsData".length());
          if (!str1.isEmpty())
          {
            str2 = localIntent.getBundleExtra(str2).getString(paramString);
            if ((str2 != null) && (!str2.isEmpty())) {
              paramIntent.put(str1, Uri.parse(str2));
            }
          }
        }
      }
      if (paramIntent.isEmpty()) {
        paramIntent = (Intent)localObject;
      }
      return paramIntent;
    }
    return null;
  }
  
  private static String getExtraResultsKeyForData(String paramString)
  {
    return "android.remoteinput.dataTypeResultsData" + paramString;
  }
  
  public static Bundle getResultsFromIntent(Intent paramIntent)
  {
    if (Build.VERSION.SDK_INT >= 20) {
      return Api20Impl.getResultsFromIntent(paramIntent);
    }
    if (Build.VERSION.SDK_INT >= 16)
    {
      paramIntent = getClipDataIntentFromIntent(paramIntent);
      if (paramIntent == null) {
        return null;
      }
      return (Bundle)paramIntent.getExtras().getParcelable("android.remoteinput.resultsData");
    }
    return null;
  }
  
  public static int getResultsSource(Intent paramIntent)
  {
    if (Build.VERSION.SDK_INT >= 28) {
      return Api28Impl.getResultsSource(paramIntent);
    }
    if (Build.VERSION.SDK_INT >= 16)
    {
      paramIntent = getClipDataIntentFromIntent(paramIntent);
      if (paramIntent == null) {
        return 0;
      }
      return paramIntent.getExtras().getInt("android.remoteinput.resultsSource", 0);
    }
    return 0;
  }
  
  public static void setResultsSource(Intent paramIntent, int paramInt)
  {
    if (Build.VERSION.SDK_INT >= 28)
    {
      Api28Impl.setResultsSource(paramIntent, paramInt);
    }
    else if (Build.VERSION.SDK_INT >= 16)
    {
      Intent localIntent2 = getClipDataIntentFromIntent(paramIntent);
      Intent localIntent1 = localIntent2;
      if (localIntent2 == null) {
        localIntent1 = new Intent();
      }
      localIntent1.putExtra("android.remoteinput.resultsSource", paramInt);
      Api16Impl.setClipData(paramIntent, ClipData.newIntent("android.remoteinput.results", localIntent1));
    }
  }
  
  public boolean getAllowFreeFormInput()
  {
    return this.mAllowFreeFormTextInput;
  }
  
  public Set<String> getAllowedDataTypes()
  {
    return this.mAllowedDataTypes;
  }
  
  public CharSequence[] getChoices()
  {
    return this.mChoices;
  }
  
  public int getEditChoicesBeforeSending()
  {
    return this.mEditChoicesBeforeSending;
  }
  
  public Bundle getExtras()
  {
    return this.mExtras;
  }
  
  public CharSequence getLabel()
  {
    return this.mLabel;
  }
  
  public String getResultKey()
  {
    return this.mResultKey;
  }
  
  public boolean isDataOnly()
  {
    boolean bool;
    if ((!getAllowFreeFormInput()) && ((getChoices() == null) || (getChoices().length == 0)) && (getAllowedDataTypes() != null) && (!getAllowedDataTypes().isEmpty())) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  static class Api16Impl
  {
    static ClipData getClipData(Intent paramIntent)
    {
      return paramIntent.getClipData();
    }
    
    static void setClipData(Intent paramIntent, ClipData paramClipData)
    {
      paramIntent.setClipData(paramClipData);
    }
  }
  
  static class Api20Impl
  {
    static void addResultsToIntent(Object paramObject, Intent paramIntent, Bundle paramBundle)
    {
      android.app.RemoteInput.addResultsToIntent((android.app.RemoteInput[])paramObject, paramIntent, paramBundle);
    }
    
    public static android.app.RemoteInput fromCompat(RemoteInput paramRemoteInput)
    {
      android.app.RemoteInput.Builder localBuilder = new android.app.RemoteInput.Builder(paramRemoteInput.getResultKey()).setLabel(paramRemoteInput.getLabel()).setChoices(paramRemoteInput.getChoices()).setAllowFreeFormInput(paramRemoteInput.getAllowFreeFormInput()).addExtras(paramRemoteInput.getExtras());
      if (Build.VERSION.SDK_INT >= 26)
      {
        Object localObject = paramRemoteInput.getAllowedDataTypes();
        if (localObject != null)
        {
          localObject = ((Set)localObject).iterator();
          while (((Iterator)localObject).hasNext()) {
            RemoteInput.Api26Impl.setAllowDataType(localBuilder, (String)((Iterator)localObject).next(), true);
          }
        }
      }
      if (Build.VERSION.SDK_INT >= 29) {
        RemoteInput.Api29Impl.setEditChoicesBeforeSending(localBuilder, paramRemoteInput.getEditChoicesBeforeSending());
      }
      return localBuilder.build();
    }
    
    static RemoteInput fromPlatform(Object paramObject)
    {
      paramObject = (android.app.RemoteInput)paramObject;
      RemoteInput.Builder localBuilder = new RemoteInput.Builder(((android.app.RemoteInput)paramObject).getResultKey()).setLabel(((android.app.RemoteInput)paramObject).getLabel()).setChoices(((android.app.RemoteInput)paramObject).getChoices()).setAllowFreeFormInput(((android.app.RemoteInput)paramObject).getAllowFreeFormInput()).addExtras(((android.app.RemoteInput)paramObject).getExtras());
      if (Build.VERSION.SDK_INT >= 26)
      {
        Object localObject = RemoteInput.Api26Impl.getAllowedDataTypes(paramObject);
        if (localObject != null)
        {
          localObject = ((Set)localObject).iterator();
          while (((Iterator)localObject).hasNext()) {
            localBuilder.setAllowDataType((String)((Iterator)localObject).next(), true);
          }
        }
      }
      if (Build.VERSION.SDK_INT >= 29) {
        localBuilder.setEditChoicesBeforeSending(RemoteInput.Api29Impl.getEditChoicesBeforeSending(paramObject));
      }
      return localBuilder.build();
    }
    
    static Bundle getResultsFromIntent(Intent paramIntent)
    {
      return android.app.RemoteInput.getResultsFromIntent(paramIntent);
    }
  }
  
  static class Api26Impl
  {
    static void addDataResultToIntent(RemoteInput paramRemoteInput, Intent paramIntent, Map<String, Uri> paramMap)
    {
      android.app.RemoteInput.addDataResultToIntent(RemoteInput.fromCompat(paramRemoteInput), paramIntent, paramMap);
    }
    
    static Set<String> getAllowedDataTypes(Object paramObject)
    {
      return ((android.app.RemoteInput)paramObject).getAllowedDataTypes();
    }
    
    static Map<String, Uri> getDataResultsFromIntent(Intent paramIntent, String paramString)
    {
      return android.app.RemoteInput.getDataResultsFromIntent(paramIntent, paramString);
    }
    
    static android.app.RemoteInput.Builder setAllowDataType(android.app.RemoteInput.Builder paramBuilder, String paramString, boolean paramBoolean)
    {
      return paramBuilder.setAllowDataType(paramString, paramBoolean);
    }
  }
  
  static class Api28Impl
  {
    static int getResultsSource(Intent paramIntent)
    {
      return android.app.RemoteInput.getResultsSource(paramIntent);
    }
    
    static void setResultsSource(Intent paramIntent, int paramInt)
    {
      android.app.RemoteInput.setResultsSource(paramIntent, paramInt);
    }
  }
  
  static class Api29Impl
  {
    static int getEditChoicesBeforeSending(Object paramObject)
    {
      return ((android.app.RemoteInput)paramObject).getEditChoicesBeforeSending();
    }
    
    static android.app.RemoteInput.Builder setEditChoicesBeforeSending(android.app.RemoteInput.Builder paramBuilder, int paramInt)
    {
      return paramBuilder.setEditChoicesBeforeSending(paramInt);
    }
  }
  
  public static final class Builder
  {
    private boolean mAllowFreeFormTextInput = true;
    private final Set<String> mAllowedDataTypes = new HashSet();
    private CharSequence[] mChoices;
    private int mEditChoicesBeforeSending = 0;
    private final Bundle mExtras = new Bundle();
    private CharSequence mLabel;
    private final String mResultKey;
    
    public Builder(String paramString)
    {
      if (paramString != null)
      {
        this.mResultKey = paramString;
        return;
      }
      throw new IllegalArgumentException("Result key can't be null");
    }
    
    public Builder addExtras(Bundle paramBundle)
    {
      if (paramBundle != null) {
        this.mExtras.putAll(paramBundle);
      }
      return this;
    }
    
    public RemoteInput build()
    {
      return new RemoteInput(this.mResultKey, this.mLabel, this.mChoices, this.mAllowFreeFormTextInput, this.mEditChoicesBeforeSending, this.mExtras, this.mAllowedDataTypes);
    }
    
    public Bundle getExtras()
    {
      return this.mExtras;
    }
    
    public Builder setAllowDataType(String paramString, boolean paramBoolean)
    {
      if (paramBoolean) {
        this.mAllowedDataTypes.add(paramString);
      } else {
        this.mAllowedDataTypes.remove(paramString);
      }
      return this;
    }
    
    public Builder setAllowFreeFormInput(boolean paramBoolean)
    {
      this.mAllowFreeFormTextInput = paramBoolean;
      return this;
    }
    
    public Builder setChoices(CharSequence[] paramArrayOfCharSequence)
    {
      this.mChoices = paramArrayOfCharSequence;
      return this;
    }
    
    public Builder setEditChoicesBeforeSending(int paramInt)
    {
      this.mEditChoicesBeforeSending = paramInt;
      return this;
    }
    
    public Builder setLabel(CharSequence paramCharSequence)
    {
      this.mLabel = paramCharSequence;
      return this;
    }
  }
  
  @Retention(RetentionPolicy.SOURCE)
  public static @interface EditChoicesBeforeSending {}
  
  @Retention(RetentionPolicy.SOURCE)
  public static @interface Source {}
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/core/app/RemoteInput.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */