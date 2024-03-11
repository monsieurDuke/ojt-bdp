package androidx.emoji2.text;

import android.content.Context;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import androidx.collection.ArraySet;
import androidx.core.util.Preconditions;
import androidx.emoji2.text.flatbuffer.MetadataList;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class EmojiCompat
{
  private static final Object CONFIG_LOCK = new Object();
  public static final String EDITOR_INFO_METAVERSION_KEY = "android.support.text.emoji.emojiCompat_metadataVersion";
  public static final String EDITOR_INFO_REPLACE_ALL_KEY = "android.support.text.emoji.emojiCompat_replaceAll";
  static final int EMOJI_COUNT_UNLIMITED = Integer.MAX_VALUE;
  public static final int EMOJI_FALLBACK = 2;
  public static final int EMOJI_SUPPORTED = 1;
  public static final int EMOJI_UNSUPPORTED = 0;
  private static final Object INSTANCE_LOCK = new Object();
  public static final int LOAD_STATE_DEFAULT = 3;
  public static final int LOAD_STATE_FAILED = 2;
  public static final int LOAD_STATE_LOADING = 0;
  public static final int LOAD_STATE_SUCCEEDED = 1;
  public static final int LOAD_STRATEGY_DEFAULT = 0;
  public static final int LOAD_STRATEGY_MANUAL = 1;
  private static final String NOT_INITIALIZED_ERROR_TEXT = "EmojiCompat is not initialized.\n\nYou must initialize EmojiCompat prior to referencing the EmojiCompat instance.\n\nThe most likely cause of this error is disabling the EmojiCompatInitializer\neither explicitly in AndroidManifest.xml, or by including\nandroidx.emoji2:emoji2-bundled.\n\nAutomatic initialization is typically performed by EmojiCompatInitializer. If\nyou are not expecting to initialize EmojiCompat manually in your application,\nplease check to ensure it has not been removed from your APK's manifest. You can\ndo this in Android Studio using Build > Analyze APK.\n\nIn the APK Analyzer, ensure that the startup entry for\nEmojiCompatInitializer and InitializationProvider is present in\n AndroidManifest.xml. If it is missing or contains tools:node=\"remove\", and you\nintend to use automatic configuration, verify:\n\n  1. Your application does not include emoji2-bundled\n  2. All modules do not contain an exclusion manifest rule for\n     EmojiCompatInitializer or InitializationProvider. For more information\n     about manifest exclusions see the documentation for the androidx startup\n     library.\n\nIf you intend to use emoji2-bundled, please call EmojiCompat.init. You can\nlearn more in the documentation for BundledEmojiCompatConfig.\n\nIf you intended to perform manual configuration, it is recommended that you call\nEmojiCompat.init immediately on application startup.\n\nIf you still cannot resolve this issue, please open a bug with your specific\nconfiguration to help improve error message.";
  public static final int REPLACE_STRATEGY_ALL = 1;
  public static final int REPLACE_STRATEGY_DEFAULT = 0;
  public static final int REPLACE_STRATEGY_NON_EXISTENT = 2;
  private static volatile boolean sHasDoneDefaultConfigLookup;
  private static volatile EmojiCompat sInstance;
  final int[] mEmojiAsDefaultStyleExceptions;
  private final int mEmojiSpanIndicatorColor;
  private final boolean mEmojiSpanIndicatorEnabled;
  private final GlyphChecker mGlyphChecker;
  private final CompatInternal mHelper;
  private final Set<InitCallback> mInitCallbacks;
  private final ReadWriteLock mInitLock = new ReentrantReadWriteLock();
  private volatile int mLoadState = 3;
  private final Handler mMainHandler;
  private final int mMetadataLoadStrategy;
  final MetadataRepoLoader mMetadataLoader;
  final boolean mReplaceAll;
  final boolean mUseEmojiAsDefaultStyle;
  
  private EmojiCompat(Config paramConfig)
  {
    this.mReplaceAll = paramConfig.mReplaceAll;
    this.mUseEmojiAsDefaultStyle = paramConfig.mUseEmojiAsDefaultStyle;
    this.mEmojiAsDefaultStyleExceptions = paramConfig.mEmojiAsDefaultStyleExceptions;
    this.mEmojiSpanIndicatorEnabled = paramConfig.mEmojiSpanIndicatorEnabled;
    this.mEmojiSpanIndicatorColor = paramConfig.mEmojiSpanIndicatorColor;
    this.mMetadataLoader = paramConfig.mMetadataLoader;
    this.mMetadataLoadStrategy = paramConfig.mMetadataLoadStrategy;
    this.mGlyphChecker = paramConfig.mGlyphChecker;
    this.mMainHandler = new Handler(Looper.getMainLooper());
    ArraySet localArraySet = new ArraySet();
    this.mInitCallbacks = localArraySet;
    if ((paramConfig.mInitCallbacks != null) && (!paramConfig.mInitCallbacks.isEmpty())) {
      localArraySet.addAll(paramConfig.mInitCallbacks);
    }
    if (Build.VERSION.SDK_INT < 19) {
      paramConfig = new CompatInternal(this);
    } else {
      paramConfig = new CompatInternal19(this);
    }
    this.mHelper = paramConfig;
    loadMetadata();
  }
  
  public static EmojiCompat get()
  {
    synchronized (INSTANCE_LOCK)
    {
      EmojiCompat localEmojiCompat = sInstance;
      boolean bool;
      if (localEmojiCompat != null) {
        bool = true;
      } else {
        bool = false;
      }
      Preconditions.checkState(bool, "EmojiCompat is not initialized.\n\nYou must initialize EmojiCompat prior to referencing the EmojiCompat instance.\n\nThe most likely cause of this error is disabling the EmojiCompatInitializer\neither explicitly in AndroidManifest.xml, or by including\nandroidx.emoji2:emoji2-bundled.\n\nAutomatic initialization is typically performed by EmojiCompatInitializer. If\nyou are not expecting to initialize EmojiCompat manually in your application,\nplease check to ensure it has not been removed from your APK's manifest. You can\ndo this in Android Studio using Build > Analyze APK.\n\nIn the APK Analyzer, ensure that the startup entry for\nEmojiCompatInitializer and InitializationProvider is present in\n AndroidManifest.xml. If it is missing or contains tools:node=\"remove\", and you\nintend to use automatic configuration, verify:\n\n  1. Your application does not include emoji2-bundled\n  2. All modules do not contain an exclusion manifest rule for\n     EmojiCompatInitializer or InitializationProvider. For more information\n     about manifest exclusions see the documentation for the androidx startup\n     library.\n\nIf you intend to use emoji2-bundled, please call EmojiCompat.init. You can\nlearn more in the documentation for BundledEmojiCompatConfig.\n\nIf you intended to perform manual configuration, it is recommended that you call\nEmojiCompat.init immediately on application startup.\n\nIf you still cannot resolve this issue, please open a bug with your specific\nconfiguration to help improve error message.");
      return localEmojiCompat;
    }
  }
  
  public static boolean handleDeleteSurroundingText(InputConnection paramInputConnection, Editable paramEditable, int paramInt1, int paramInt2, boolean paramBoolean)
  {
    if (Build.VERSION.SDK_INT >= 19) {
      return EmojiProcessor.handleDeleteSurroundingText(paramInputConnection, paramEditable, paramInt1, paramInt2, paramBoolean);
    }
    return false;
  }
  
  public static boolean handleOnKeyDown(Editable paramEditable, int paramInt, KeyEvent paramKeyEvent)
  {
    if (Build.VERSION.SDK_INT >= 19) {
      return EmojiProcessor.handleOnKeyDown(paramEditable, paramInt, paramKeyEvent);
    }
    return false;
  }
  
  public static EmojiCompat init(Context paramContext)
  {
    return init(paramContext, null);
  }
  
  public static EmojiCompat init(Context arg0, DefaultEmojiCompatConfig.DefaultEmojiCompatConfigFactory paramDefaultEmojiCompatConfigFactory)
  {
    if (sHasDoneDefaultConfigLookup) {
      return sInstance;
    }
    if (paramDefaultEmojiCompatConfigFactory == null) {
      paramDefaultEmojiCompatConfigFactory = new DefaultEmojiCompatConfig.DefaultEmojiCompatConfigFactory(null);
    }
    paramDefaultEmojiCompatConfigFactory = paramDefaultEmojiCompatConfigFactory.create(???);
    synchronized (CONFIG_LOCK)
    {
      if (!sHasDoneDefaultConfigLookup)
      {
        if (paramDefaultEmojiCompatConfigFactory != null) {
          init(paramDefaultEmojiCompatConfigFactory);
        }
        sHasDoneDefaultConfigLookup = true;
      }
      paramDefaultEmojiCompatConfigFactory = sInstance;
      return paramDefaultEmojiCompatConfigFactory;
    }
  }
  
  public static EmojiCompat init(Config paramConfig)
  {
    EmojiCompat localEmojiCompat2 = sInstance;
    EmojiCompat localEmojiCompat1 = localEmojiCompat2;
    if (localEmojiCompat2 == null) {
      synchronized (INSTANCE_LOCK)
      {
        localEmojiCompat2 = sInstance;
        localEmojiCompat1 = localEmojiCompat2;
        if (localEmojiCompat2 == null)
        {
          localEmojiCompat1 = new androidx/emoji2/text/EmojiCompat;
          localEmojiCompat1.<init>(paramConfig);
          sInstance = localEmojiCompat1;
        }
      }
    }
    return localEmojiCompat1;
  }
  
  public static boolean isConfigured()
  {
    boolean bool;
    if (sInstance != null) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  private boolean isInitialized()
  {
    int i = getLoadState();
    boolean bool = true;
    if (i != 1) {
      bool = false;
    }
    return bool;
  }
  
  private void loadMetadata()
  {
    this.mInitLock.writeLock().lock();
    try
    {
      if (this.mMetadataLoadStrategy == 0) {
        this.mLoadState = 0;
      }
      this.mInitLock.writeLock().unlock();
      if (getLoadState() == 0) {
        this.mHelper.loadMetadata();
      }
      return;
    }
    finally
    {
      this.mInitLock.writeLock().unlock();
    }
  }
  
  public static EmojiCompat reset(Config paramConfig)
  {
    synchronized (INSTANCE_LOCK)
    {
      EmojiCompat localEmojiCompat = new androidx/emoji2/text/EmojiCompat;
      localEmojiCompat.<init>(paramConfig);
      sInstance = localEmojiCompat;
      return localEmojiCompat;
    }
  }
  
  public static EmojiCompat reset(EmojiCompat paramEmojiCompat)
  {
    synchronized (INSTANCE_LOCK)
    {
      sInstance = paramEmojiCompat;
      paramEmojiCompat = sInstance;
      return paramEmojiCompat;
    }
  }
  
  public static void skipDefaultConfigurationLookup(boolean paramBoolean)
  {
    synchronized (CONFIG_LOCK)
    {
      sHasDoneDefaultConfigLookup = paramBoolean;
      return;
    }
  }
  
  public String getAssetSignature()
  {
    Preconditions.checkState(isInitialized(), "Not initialized yet");
    return this.mHelper.getAssetSignature();
  }
  
  public int getEmojiMatch(CharSequence paramCharSequence, int paramInt)
  {
    Preconditions.checkState(isInitialized(), "Not initialized yet");
    Preconditions.checkNotNull(paramCharSequence, "sequence cannot be null");
    return this.mHelper.getEmojiMatch(paramCharSequence, paramInt);
  }
  
  public int getEmojiSpanIndicatorColor()
  {
    return this.mEmojiSpanIndicatorColor;
  }
  
  public int getLoadState()
  {
    this.mInitLock.readLock().lock();
    try
    {
      int i = this.mLoadState;
      return i;
    }
    finally
    {
      this.mInitLock.readLock().unlock();
    }
  }
  
  @Deprecated
  public boolean hasEmojiGlyph(CharSequence paramCharSequence)
  {
    Preconditions.checkState(isInitialized(), "Not initialized yet");
    Preconditions.checkNotNull(paramCharSequence, "sequence cannot be null");
    return this.mHelper.hasEmojiGlyph(paramCharSequence);
  }
  
  @Deprecated
  public boolean hasEmojiGlyph(CharSequence paramCharSequence, int paramInt)
  {
    Preconditions.checkState(isInitialized(), "Not initialized yet");
    Preconditions.checkNotNull(paramCharSequence, "sequence cannot be null");
    return this.mHelper.hasEmojiGlyph(paramCharSequence, paramInt);
  }
  
  public boolean isEmojiSpanIndicatorEnabled()
  {
    return this.mEmojiSpanIndicatorEnabled;
  }
  
  public void load()
  {
    int i = this.mMetadataLoadStrategy;
    boolean bool = true;
    if (i != 1) {
      bool = false;
    }
    Preconditions.checkState(bool, "Set metadataLoadStrategy to LOAD_STRATEGY_MANUAL to execute manual loading");
    if (isInitialized()) {
      return;
    }
    this.mInitLock.writeLock().lock();
    try
    {
      i = this.mLoadState;
      if (i == 0) {
        return;
      }
      this.mLoadState = 0;
      this.mInitLock.writeLock().unlock();
      this.mHelper.loadMetadata();
      return;
    }
    finally
    {
      this.mInitLock.writeLock().unlock();
    }
  }
  
  void onMetadataLoadFailed(Throwable paramThrowable)
  {
    ArrayList localArrayList = new ArrayList();
    this.mInitLock.writeLock().lock();
    try
    {
      this.mLoadState = 2;
      localArrayList.addAll(this.mInitCallbacks);
      this.mInitCallbacks.clear();
      this.mInitLock.writeLock().unlock();
      this.mMainHandler.post(new ListenerDispatcher(localArrayList, this.mLoadState, paramThrowable));
      return;
    }
    finally
    {
      this.mInitLock.writeLock().unlock();
    }
  }
  
  void onMetadataLoadSuccess()
  {
    ArrayList localArrayList = new ArrayList();
    this.mInitLock.writeLock().lock();
    try
    {
      this.mLoadState = 1;
      localArrayList.addAll(this.mInitCallbacks);
      this.mInitCallbacks.clear();
      this.mInitLock.writeLock().unlock();
      this.mMainHandler.post(new ListenerDispatcher(localArrayList, this.mLoadState));
      return;
    }
    finally
    {
      this.mInitLock.writeLock().unlock();
    }
  }
  
  public CharSequence process(CharSequence paramCharSequence)
  {
    int i;
    if (paramCharSequence == null) {
      i = 0;
    } else {
      i = paramCharSequence.length();
    }
    return process(paramCharSequence, 0, i);
  }
  
  public CharSequence process(CharSequence paramCharSequence, int paramInt1, int paramInt2)
  {
    return process(paramCharSequence, paramInt1, paramInt2, Integer.MAX_VALUE);
  }
  
  public CharSequence process(CharSequence paramCharSequence, int paramInt1, int paramInt2, int paramInt3)
  {
    return process(paramCharSequence, paramInt1, paramInt2, paramInt3, 0);
  }
  
  public CharSequence process(CharSequence paramCharSequence, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    Preconditions.checkState(isInitialized(), "Not initialized yet");
    Preconditions.checkArgumentNonnegative(paramInt1, "start cannot be negative");
    Preconditions.checkArgumentNonnegative(paramInt2, "end cannot be negative");
    Preconditions.checkArgumentNonnegative(paramInt3, "maxEmojiCount cannot be negative");
    boolean bool2 = true;
    boolean bool1;
    if (paramInt1 <= paramInt2) {
      bool1 = true;
    } else {
      bool1 = false;
    }
    Preconditions.checkArgument(bool1, "start should be <= than end");
    if (paramCharSequence == null) {
      return null;
    }
    if (paramInt1 <= paramCharSequence.length()) {
      bool1 = true;
    } else {
      bool1 = false;
    }
    Preconditions.checkArgument(bool1, "start should be < than charSequence length");
    if (paramInt2 <= paramCharSequence.length()) {
      bool1 = bool2;
    } else {
      bool1 = false;
    }
    Preconditions.checkArgument(bool1, "end should be < than charSequence length");
    if ((paramCharSequence.length() != 0) && (paramInt1 != paramInt2))
    {
      switch (paramInt4)
      {
      default: 
        bool1 = this.mReplaceAll;
        break;
      case 2: 
        bool1 = false;
        break;
      case 1: 
        bool1 = true;
      }
      return this.mHelper.process(paramCharSequence, paramInt1, paramInt2, paramInt3, bool1);
    }
    return paramCharSequence;
  }
  
  public void registerInitCallback(InitCallback paramInitCallback)
  {
    Preconditions.checkNotNull(paramInitCallback, "initCallback cannot be null");
    this.mInitLock.writeLock().lock();
    try
    {
      if ((this.mLoadState != 1) && (this.mLoadState != 2))
      {
        this.mInitCallbacks.add(paramInitCallback);
      }
      else
      {
        Handler localHandler = this.mMainHandler;
        ListenerDispatcher localListenerDispatcher = new androidx/emoji2/text/EmojiCompat$ListenerDispatcher;
        localListenerDispatcher.<init>(paramInitCallback, this.mLoadState);
        localHandler.post(localListenerDispatcher);
      }
      return;
    }
    finally
    {
      this.mInitLock.writeLock().unlock();
    }
  }
  
  public void unregisterInitCallback(InitCallback paramInitCallback)
  {
    Preconditions.checkNotNull(paramInitCallback, "initCallback cannot be null");
    this.mInitLock.writeLock().lock();
    try
    {
      this.mInitCallbacks.remove(paramInitCallback);
      return;
    }
    finally
    {
      this.mInitLock.writeLock().unlock();
    }
  }
  
  public void updateEditorInfo(EditorInfo paramEditorInfo)
  {
    if ((isInitialized()) && (paramEditorInfo != null))
    {
      if (paramEditorInfo.extras == null) {
        paramEditorInfo.extras = new Bundle();
      }
      this.mHelper.updateEditorInfoAttrs(paramEditorInfo);
      return;
    }
  }
  
  @Retention(RetentionPolicy.SOURCE)
  public static @interface CodepointSequenceMatchResult {}
  
  private static class CompatInternal
  {
    final EmojiCompat mEmojiCompat;
    
    CompatInternal(EmojiCompat paramEmojiCompat)
    {
      this.mEmojiCompat = paramEmojiCompat;
    }
    
    String getAssetSignature()
    {
      return "";
    }
    
    public int getEmojiMatch(CharSequence paramCharSequence, int paramInt)
    {
      return 0;
    }
    
    boolean hasEmojiGlyph(CharSequence paramCharSequence)
    {
      return false;
    }
    
    boolean hasEmojiGlyph(CharSequence paramCharSequence, int paramInt)
    {
      return false;
    }
    
    void loadMetadata()
    {
      this.mEmojiCompat.onMetadataLoadSuccess();
    }
    
    CharSequence process(CharSequence paramCharSequence, int paramInt1, int paramInt2, int paramInt3, boolean paramBoolean)
    {
      return paramCharSequence;
    }
    
    void updateEditorInfoAttrs(EditorInfo paramEditorInfo) {}
  }
  
  private static final class CompatInternal19
    extends EmojiCompat.CompatInternal
  {
    private volatile MetadataRepo mMetadataRepo;
    private volatile EmojiProcessor mProcessor;
    
    CompatInternal19(EmojiCompat paramEmojiCompat)
    {
      super();
    }
    
    String getAssetSignature()
    {
      String str = this.mMetadataRepo.getMetadataList().sourceSha();
      if (str == null) {
        str = "";
      }
      return str;
    }
    
    public int getEmojiMatch(CharSequence paramCharSequence, int paramInt)
    {
      return this.mProcessor.getEmojiMatch(paramCharSequence, paramInt);
    }
    
    boolean hasEmojiGlyph(CharSequence paramCharSequence)
    {
      int i = this.mProcessor.getEmojiMatch(paramCharSequence);
      boolean bool = true;
      if (i != 1) {
        bool = false;
      }
      return bool;
    }
    
    boolean hasEmojiGlyph(CharSequence paramCharSequence, int paramInt)
    {
      paramInt = this.mProcessor.getEmojiMatch(paramCharSequence, paramInt);
      boolean bool = true;
      if (paramInt != 1) {
        bool = false;
      }
      return bool;
    }
    
    /* Error */
    void loadMetadata()
    {
      // Byte code:
      //   0: new 9	androidx/emoji2/text/EmojiCompat$CompatInternal19$1
      //   3: astore_1
      //   4: aload_1
      //   5: aload_0
      //   6: invokespecial 54	androidx/emoji2/text/EmojiCompat$CompatInternal19$1:<init>	(Landroidx/emoji2/text/EmojiCompat$CompatInternal19;)V
      //   9: aload_0
      //   10: getfield 58	androidx/emoji2/text/EmojiCompat$CompatInternal19:mEmojiCompat	Landroidx/emoji2/text/EmojiCompat;
      //   13: getfield 62	androidx/emoji2/text/EmojiCompat:mMetadataLoader	Landroidx/emoji2/text/EmojiCompat$MetadataRepoLoader;
      //   16: aload_1
      //   17: invokeinterface 68 2 0
      //   22: goto +12 -> 34
      //   25: astore_1
      //   26: aload_0
      //   27: getfield 58	androidx/emoji2/text/EmojiCompat$CompatInternal19:mEmojiCompat	Landroidx/emoji2/text/EmojiCompat;
      //   30: aload_1
      //   31: invokevirtual 72	androidx/emoji2/text/EmojiCompat:onMetadataLoadFailed	(Ljava/lang/Throwable;)V
      //   34: return
      // Local variable table:
      //   start	length	slot	name	signature
      //   0	35	0	this	CompatInternal19
      //   3	14	1	local1	1
      //   25	6	1	localThrowable	Throwable
      // Exception table:
      //   from	to	target	type
      //   0	22	25	finally
    }
    
    void onMetadataLoadSuccess(MetadataRepo paramMetadataRepo)
    {
      if (paramMetadataRepo == null)
      {
        this.mEmojiCompat.onMetadataLoadFailed(new IllegalArgumentException("metadataRepo cannot be null"));
        return;
      }
      this.mMetadataRepo = paramMetadataRepo;
      this.mProcessor = new EmojiProcessor(this.mMetadataRepo, new EmojiCompat.SpanFactory(), this.mEmojiCompat.mGlyphChecker, this.mEmojiCompat.mUseEmojiAsDefaultStyle, this.mEmojiCompat.mEmojiAsDefaultStyleExceptions);
      this.mEmojiCompat.onMetadataLoadSuccess();
    }
    
    CharSequence process(CharSequence paramCharSequence, int paramInt1, int paramInt2, int paramInt3, boolean paramBoolean)
    {
      return this.mProcessor.process(paramCharSequence, paramInt1, paramInt2, paramInt3, paramBoolean);
    }
    
    void updateEditorInfoAttrs(EditorInfo paramEditorInfo)
    {
      paramEditorInfo.extras.putInt("android.support.text.emoji.emojiCompat_metadataVersion", this.mMetadataRepo.getMetadataVersion());
      paramEditorInfo.extras.putBoolean("android.support.text.emoji.emojiCompat_replaceAll", this.mEmojiCompat.mReplaceAll);
    }
  }
  
  public static abstract class Config
  {
    int[] mEmojiAsDefaultStyleExceptions;
    int mEmojiSpanIndicatorColor = -16711936;
    boolean mEmojiSpanIndicatorEnabled;
    EmojiCompat.GlyphChecker mGlyphChecker = new DefaultGlyphChecker();
    Set<EmojiCompat.InitCallback> mInitCallbacks;
    int mMetadataLoadStrategy = 0;
    final EmojiCompat.MetadataRepoLoader mMetadataLoader;
    boolean mReplaceAll;
    boolean mUseEmojiAsDefaultStyle;
    
    protected Config(EmojiCompat.MetadataRepoLoader paramMetadataRepoLoader)
    {
      Preconditions.checkNotNull(paramMetadataRepoLoader, "metadataLoader cannot be null.");
      this.mMetadataLoader = paramMetadataRepoLoader;
    }
    
    protected final EmojiCompat.MetadataRepoLoader getMetadataRepoLoader()
    {
      return this.mMetadataLoader;
    }
    
    public Config registerInitCallback(EmojiCompat.InitCallback paramInitCallback)
    {
      Preconditions.checkNotNull(paramInitCallback, "initCallback cannot be null");
      if (this.mInitCallbacks == null) {
        this.mInitCallbacks = new ArraySet();
      }
      this.mInitCallbacks.add(paramInitCallback);
      return this;
    }
    
    public Config setEmojiSpanIndicatorColor(int paramInt)
    {
      this.mEmojiSpanIndicatorColor = paramInt;
      return this;
    }
    
    public Config setEmojiSpanIndicatorEnabled(boolean paramBoolean)
    {
      this.mEmojiSpanIndicatorEnabled = paramBoolean;
      return this;
    }
    
    public Config setGlyphChecker(EmojiCompat.GlyphChecker paramGlyphChecker)
    {
      Preconditions.checkNotNull(paramGlyphChecker, "GlyphChecker cannot be null");
      this.mGlyphChecker = paramGlyphChecker;
      return this;
    }
    
    public Config setMetadataLoadStrategy(int paramInt)
    {
      this.mMetadataLoadStrategy = paramInt;
      return this;
    }
    
    public Config setReplaceAll(boolean paramBoolean)
    {
      this.mReplaceAll = paramBoolean;
      return this;
    }
    
    public Config setUseEmojiAsDefaultStyle(boolean paramBoolean)
    {
      return setUseEmojiAsDefaultStyle(paramBoolean, null);
    }
    
    public Config setUseEmojiAsDefaultStyle(boolean paramBoolean, List<Integer> paramList)
    {
      this.mUseEmojiAsDefaultStyle = paramBoolean;
      if ((paramBoolean) && (paramList != null))
      {
        this.mEmojiAsDefaultStyleExceptions = new int[paramList.size()];
        int i = 0;
        Iterator localIterator = paramList.iterator();
        while (localIterator.hasNext())
        {
          paramList = (Integer)localIterator.next();
          this.mEmojiAsDefaultStyleExceptions[i] = paramList.intValue();
          i++;
        }
        Arrays.sort(this.mEmojiAsDefaultStyleExceptions);
      }
      else
      {
        this.mEmojiAsDefaultStyleExceptions = null;
      }
      return this;
    }
    
    public Config unregisterInitCallback(EmojiCompat.InitCallback paramInitCallback)
    {
      Preconditions.checkNotNull(paramInitCallback, "initCallback cannot be null");
      Set localSet = this.mInitCallbacks;
      if (localSet != null) {
        localSet.remove(paramInitCallback);
      }
      return this;
    }
  }
  
  public static abstract interface GlyphChecker
  {
    public abstract boolean hasGlyph(CharSequence paramCharSequence, int paramInt1, int paramInt2, int paramInt3);
  }
  
  public static abstract class InitCallback
  {
    public void onFailed(Throwable paramThrowable) {}
    
    public void onInitialized() {}
  }
  
  private static class ListenerDispatcher
    implements Runnable
  {
    private final List<EmojiCompat.InitCallback> mInitCallbacks;
    private final int mLoadState;
    private final Throwable mThrowable;
    
    ListenerDispatcher(EmojiCompat.InitCallback paramInitCallback, int paramInt)
    {
      this(Arrays.asList(new EmojiCompat.InitCallback[] { (EmojiCompat.InitCallback)Preconditions.checkNotNull(paramInitCallback, "initCallback cannot be null") }), paramInt, null);
    }
    
    ListenerDispatcher(Collection<EmojiCompat.InitCallback> paramCollection, int paramInt)
    {
      this(paramCollection, paramInt, null);
    }
    
    ListenerDispatcher(Collection<EmojiCompat.InitCallback> paramCollection, int paramInt, Throwable paramThrowable)
    {
      Preconditions.checkNotNull(paramCollection, "initCallbacks cannot be null");
      this.mInitCallbacks = new ArrayList(paramCollection);
      this.mLoadState = paramInt;
      this.mThrowable = paramThrowable;
    }
    
    public void run()
    {
      int j = this.mInitCallbacks.size();
      int i;
      switch (this.mLoadState)
      {
      default: 
        i = 0;
        break;
      case 1: 
        for (i = 0; i < j; i++) {
          ((EmojiCompat.InitCallback)this.mInitCallbacks.get(i)).onInitialized();
        }
        break;
      }
      while (i < j)
      {
        ((EmojiCompat.InitCallback)this.mInitCallbacks.get(i)).onFailed(this.mThrowable);
        i++;
      }
    }
  }
  
  @Retention(RetentionPolicy.SOURCE)
  public static @interface LoadStrategy {}
  
  public static abstract interface MetadataRepoLoader
  {
    public abstract void load(EmojiCompat.MetadataRepoLoaderCallback paramMetadataRepoLoaderCallback);
  }
  
  public static abstract class MetadataRepoLoaderCallback
  {
    public abstract void onFailed(Throwable paramThrowable);
    
    public abstract void onLoaded(MetadataRepo paramMetadataRepo);
  }
  
  @Retention(RetentionPolicy.SOURCE)
  public static @interface ReplaceStrategy {}
  
  static class SpanFactory
  {
    EmojiSpan createSpan(EmojiMetadata paramEmojiMetadata)
    {
      return new TypefaceEmojiSpan(paramEmojiMetadata);
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/emoji2/text/EmojiCompat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */