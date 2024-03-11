package androidx.emoji2.text;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.ProviderInfo;
import android.content.pm.ResolveInfo;
import android.content.pm.Signature;
import android.os.Build.VERSION;
import android.util.Log;
import androidx.core.provider.FontRequest;
import androidx.core.util.Preconditions;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public final class DefaultEmojiCompatConfig
{
  public static FontRequestEmojiCompatConfig create(Context paramContext)
  {
    return (FontRequestEmojiCompatConfig)new DefaultEmojiCompatConfigFactory(null).create(paramContext);
  }
  
  public static class DefaultEmojiCompatConfigFactory
  {
    private static final String DEFAULT_EMOJI_QUERY = "emojicompat-emoji-font";
    private static final String INTENT_LOAD_EMOJI_FONT = "androidx.content.action.LOAD_EMOJI_FONT";
    private static final String TAG = "emoji2.text.DefaultEmojiConfig";
    private final DefaultEmojiCompatConfig.DefaultEmojiCompatConfigHelper mHelper;
    
    public DefaultEmojiCompatConfigFactory(DefaultEmojiCompatConfig.DefaultEmojiCompatConfigHelper paramDefaultEmojiCompatConfigHelper)
    {
      if (paramDefaultEmojiCompatConfigHelper == null) {
        paramDefaultEmojiCompatConfigHelper = getHelperForApi();
      }
      this.mHelper = paramDefaultEmojiCompatConfigHelper;
    }
    
    private EmojiCompat.Config configOrNull(Context paramContext, FontRequest paramFontRequest)
    {
      if (paramFontRequest == null) {
        return null;
      }
      return new FontRequestEmojiCompatConfig(paramContext, paramFontRequest);
    }
    
    private List<List<byte[]>> convertToByteArray(Signature[] paramArrayOfSignature)
    {
      ArrayList localArrayList = new ArrayList();
      int j = paramArrayOfSignature.length;
      for (int i = 0; i < j; i++) {
        localArrayList.add(paramArrayOfSignature[i].toByteArray());
      }
      return Collections.singletonList(localArrayList);
    }
    
    private FontRequest generateFontRequestFrom(ProviderInfo paramProviderInfo, PackageManager paramPackageManager)
      throws PackageManager.NameNotFoundException
    {
      String str = paramProviderInfo.authority;
      paramProviderInfo = paramProviderInfo.packageName;
      return new FontRequest(str, paramProviderInfo, "emojicompat-emoji-font", convertToByteArray(this.mHelper.getSigningSignatures(paramPackageManager, paramProviderInfo)));
    }
    
    private static DefaultEmojiCompatConfig.DefaultEmojiCompatConfigHelper getHelperForApi()
    {
      if (Build.VERSION.SDK_INT >= 28) {
        return new DefaultEmojiCompatConfig.DefaultEmojiCompatConfigHelper_API28();
      }
      if (Build.VERSION.SDK_INT >= 19) {
        return new DefaultEmojiCompatConfig.DefaultEmojiCompatConfigHelper_API19();
      }
      return new DefaultEmojiCompatConfig.DefaultEmojiCompatConfigHelper();
    }
    
    private boolean hasFlagSystem(ProviderInfo paramProviderInfo)
    {
      boolean bool = true;
      if ((paramProviderInfo == null) || (paramProviderInfo.applicationInfo == null) || ((paramProviderInfo.applicationInfo.flags & 0x1) != 1)) {
        bool = false;
      }
      return bool;
    }
    
    private ProviderInfo queryDefaultInstalledContentProvider(PackageManager paramPackageManager)
    {
      paramPackageManager = this.mHelper.queryIntentContentProviders(paramPackageManager, new Intent("androidx.content.action.LOAD_EMOJI_FONT"), 0).iterator();
      while (paramPackageManager.hasNext())
      {
        Object localObject = (ResolveInfo)paramPackageManager.next();
        localObject = this.mHelper.getProviderInfo((ResolveInfo)localObject);
        if (hasFlagSystem((ProviderInfo)localObject)) {
          return (ProviderInfo)localObject;
        }
      }
      return null;
    }
    
    public EmojiCompat.Config create(Context paramContext)
    {
      return configOrNull(paramContext, queryForDefaultFontRequest(paramContext));
    }
    
    FontRequest queryForDefaultFontRequest(Context paramContext)
    {
      paramContext = paramContext.getPackageManager();
      Preconditions.checkNotNull(paramContext, "Package manager required to locate emoji font provider");
      ProviderInfo localProviderInfo = queryDefaultInstalledContentProvider(paramContext);
      if (localProviderInfo == null) {
        return null;
      }
      try
      {
        paramContext = generateFontRequestFrom(localProviderInfo, paramContext);
        return paramContext;
      }
      catch (PackageManager.NameNotFoundException paramContext)
      {
        Log.wtf("emoji2.text.DefaultEmojiConfig", paramContext);
      }
      return null;
    }
  }
  
  public static class DefaultEmojiCompatConfigHelper
  {
    public ProviderInfo getProviderInfo(ResolveInfo paramResolveInfo)
    {
      throw new IllegalStateException("Unable to get provider info prior to API 19");
    }
    
    public Signature[] getSigningSignatures(PackageManager paramPackageManager, String paramString)
      throws PackageManager.NameNotFoundException
    {
      return paramPackageManager.getPackageInfo(paramString, 64).signatures;
    }
    
    public List<ResolveInfo> queryIntentContentProviders(PackageManager paramPackageManager, Intent paramIntent, int paramInt)
    {
      return Collections.emptyList();
    }
  }
  
  public static class DefaultEmojiCompatConfigHelper_API19
    extends DefaultEmojiCompatConfig.DefaultEmojiCompatConfigHelper
  {
    public ProviderInfo getProviderInfo(ResolveInfo paramResolveInfo)
    {
      return paramResolveInfo.providerInfo;
    }
    
    public List<ResolveInfo> queryIntentContentProviders(PackageManager paramPackageManager, Intent paramIntent, int paramInt)
    {
      return paramPackageManager.queryIntentContentProviders(paramIntent, paramInt);
    }
  }
  
  public static class DefaultEmojiCompatConfigHelper_API28
    extends DefaultEmojiCompatConfig.DefaultEmojiCompatConfigHelper_API19
  {
    public Signature[] getSigningSignatures(PackageManager paramPackageManager, String paramString)
      throws PackageManager.NameNotFoundException
    {
      return paramPackageManager.getPackageInfo(paramString, 64).signatures;
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/emoji2/text/DefaultEmojiCompatConfig.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */