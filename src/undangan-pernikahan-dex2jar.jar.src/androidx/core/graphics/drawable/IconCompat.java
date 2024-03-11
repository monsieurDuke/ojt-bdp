package androidx.core.graphics.drawable;

import android.app.ActivityManager;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.Intent.ShortcutIconResource;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.Resources.NotFoundException;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.Shader.TileMode;
import android.graphics.drawable.AdaptiveIconDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.Log;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.util.ObjectsCompat;
import androidx.core.util.Preconditions;
import androidx.versionedparcelable.CustomVersionedParcelable;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.charset.Charset;
import mt.Log229316;
import mt.Log5ECF72;
import mt.LogE84000;

public class IconCompat
  extends CustomVersionedParcelable
{
  private static final float ADAPTIVE_ICON_INSET_FACTOR = 0.25F;
  private static final int AMBIENT_SHADOW_ALPHA = 30;
  private static final float BLUR_FACTOR = 0.010416667F;
  static final PorterDuff.Mode DEFAULT_TINT_MODE = PorterDuff.Mode.SRC_IN;
  private static final float DEFAULT_VIEW_PORT_SCALE = 0.6666667F;
  static final String EXTRA_INT1 = "int1";
  static final String EXTRA_INT2 = "int2";
  static final String EXTRA_OBJ = "obj";
  static final String EXTRA_STRING1 = "string1";
  static final String EXTRA_TINT_LIST = "tint_list";
  static final String EXTRA_TINT_MODE = "tint_mode";
  static final String EXTRA_TYPE = "type";
  private static final float ICON_DIAMETER_FACTOR = 0.9166667F;
  private static final int KEY_SHADOW_ALPHA = 61;
  private static final float KEY_SHADOW_OFFSET_FACTOR = 0.020833334F;
  private static final String TAG = "IconCompat";
  public static final int TYPE_ADAPTIVE_BITMAP = 5;
  public static final int TYPE_BITMAP = 1;
  public static final int TYPE_DATA = 3;
  public static final int TYPE_RESOURCE = 2;
  public static final int TYPE_UNKNOWN = -1;
  public static final int TYPE_URI = 4;
  public static final int TYPE_URI_ADAPTIVE_BITMAP = 6;
  public byte[] mData = null;
  public int mInt1 = 0;
  public int mInt2 = 0;
  Object mObj1;
  public Parcelable mParcelable = null;
  public String mString1;
  public ColorStateList mTintList = null;
  PorterDuff.Mode mTintMode = DEFAULT_TINT_MODE;
  public String mTintModeStr = null;
  public int mType = -1;
  
  public IconCompat() {}
  
  IconCompat(int paramInt)
  {
    this.mType = paramInt;
  }
  
  public static IconCompat createFromBundle(Bundle paramBundle)
  {
    int i = paramBundle.getInt("type");
    IconCompat localIconCompat = new IconCompat(i);
    localIconCompat.mInt1 = paramBundle.getInt("int1");
    localIconCompat.mInt2 = paramBundle.getInt("int2");
    localIconCompat.mString1 = paramBundle.getString("string1");
    if (paramBundle.containsKey("tint_list")) {
      localIconCompat.mTintList = ((ColorStateList)paramBundle.getParcelable("tint_list"));
    }
    if (paramBundle.containsKey("tint_mode")) {
      localIconCompat.mTintMode = PorterDuff.Mode.valueOf(paramBundle.getString("tint_mode"));
    }
    switch (i)
    {
    case 0: 
    default: 
      Log.w("IconCompat", "Unknown type " + i);
      return null;
    case 3: 
      localIconCompat.mObj1 = paramBundle.getByteArray("obj");
      break;
    case 2: 
    case 4: 
    case 6: 
      localIconCompat.mObj1 = paramBundle.getString("obj");
      break;
    case -1: 
    case 1: 
    case 5: 
      localIconCompat.mObj1 = paramBundle.getParcelable("obj");
    }
    return localIconCompat;
  }
  
  public static IconCompat createFromIcon(Context paramContext, Icon paramIcon)
  {
    Preconditions.checkNotNull(paramIcon);
    return Api23Impl.createFromIcon(paramContext, paramIcon);
  }
  
  public static IconCompat createFromIcon(Icon paramIcon)
  {
    return Api23Impl.createFromIconInner(paramIcon);
  }
  
  public static IconCompat createFromIconOrNullIfZeroResId(Icon paramIcon)
  {
    if ((Api23Impl.getType(paramIcon) == 2) && (Api23Impl.getResId(paramIcon) == 0)) {
      return null;
    }
    return Api23Impl.createFromIconInner(paramIcon);
  }
  
  static Bitmap createLegacyIconFromAdaptiveIcon(Bitmap paramBitmap, boolean paramBoolean)
  {
    int i = (int)(Math.min(paramBitmap.getWidth(), paramBitmap.getHeight()) * 0.6666667F);
    Bitmap localBitmap = Bitmap.createBitmap(i, i, Bitmap.Config.ARGB_8888);
    Canvas localCanvas = new Canvas(localBitmap);
    Paint localPaint = new Paint(3);
    float f1 = i * 0.5F;
    float f2 = 0.9166667F * f1;
    if (paramBoolean)
    {
      float f3 = i * 0.010416667F;
      localPaint.setColor(0);
      localPaint.setShadowLayer(f3, 0.0F, i * 0.020833334F, 1023410176);
      localCanvas.drawCircle(f1, f1, f2, localPaint);
      localPaint.setShadowLayer(f3, 0.0F, 0.0F, 503316480);
      localCanvas.drawCircle(f1, f1, f2, localPaint);
      localPaint.clearShadowLayer();
    }
    localPaint.setColor(-16777216);
    BitmapShader localBitmapShader = new BitmapShader(paramBitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
    Matrix localMatrix = new Matrix();
    localMatrix.setTranslate(-(paramBitmap.getWidth() - i) / 2.0F, -(paramBitmap.getHeight() - i) / 2.0F);
    localBitmapShader.setLocalMatrix(localMatrix);
    localPaint.setShader(localBitmapShader);
    localCanvas.drawCircle(f1, f1, f2, localPaint);
    localCanvas.setBitmap(null);
    return localBitmap;
  }
  
  public static IconCompat createWithAdaptiveBitmap(Bitmap paramBitmap)
  {
    ObjectsCompat.requireNonNull(paramBitmap);
    IconCompat localIconCompat = new IconCompat(5);
    localIconCompat.mObj1 = paramBitmap;
    return localIconCompat;
  }
  
  public static IconCompat createWithAdaptiveBitmapContentUri(Uri paramUri)
  {
    ObjectsCompat.requireNonNull(paramUri);
    return createWithAdaptiveBitmapContentUri(paramUri.toString());
  }
  
  public static IconCompat createWithAdaptiveBitmapContentUri(String paramString)
  {
    ObjectsCompat.requireNonNull(paramString);
    IconCompat localIconCompat = new IconCompat(6);
    localIconCompat.mObj1 = paramString;
    return localIconCompat;
  }
  
  public static IconCompat createWithBitmap(Bitmap paramBitmap)
  {
    ObjectsCompat.requireNonNull(paramBitmap);
    IconCompat localIconCompat = new IconCompat(1);
    localIconCompat.mObj1 = paramBitmap;
    return localIconCompat;
  }
  
  public static IconCompat createWithContentUri(Uri paramUri)
  {
    ObjectsCompat.requireNonNull(paramUri);
    return createWithContentUri(paramUri.toString());
  }
  
  public static IconCompat createWithContentUri(String paramString)
  {
    ObjectsCompat.requireNonNull(paramString);
    IconCompat localIconCompat = new IconCompat(4);
    localIconCompat.mObj1 = paramString;
    return localIconCompat;
  }
  
  public static IconCompat createWithData(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
  {
    ObjectsCompat.requireNonNull(paramArrayOfByte);
    IconCompat localIconCompat = new IconCompat(3);
    localIconCompat.mObj1 = paramArrayOfByte;
    localIconCompat.mInt1 = paramInt1;
    localIconCompat.mInt2 = paramInt2;
    return localIconCompat;
  }
  
  public static IconCompat createWithResource(Context paramContext, int paramInt)
  {
    ObjectsCompat.requireNonNull(paramContext);
    return createWithResource(paramContext.getResources(), paramContext.getPackageName(), paramInt);
  }
  
  public static IconCompat createWithResource(Resources paramResources, String paramString, int paramInt)
  {
    ObjectsCompat.requireNonNull(paramString);
    if (paramInt != 0)
    {
      IconCompat localIconCompat = new IconCompat(2);
      localIconCompat.mInt1 = paramInt;
      if (paramResources != null) {
        try
        {
          localIconCompat.mObj1 = paramResources.getResourceName(paramInt);
        }
        catch (Resources.NotFoundException paramResources)
        {
          throw new IllegalArgumentException("Icon resource cannot be found");
        }
      } else {
        localIconCompat.mObj1 = paramString;
      }
      localIconCompat.mString1 = paramString;
      return localIconCompat;
    }
    throw new IllegalArgumentException("Drawable resource ID must not be 0");
  }
  
  static Resources getResources(Context paramContext, String paramString)
  {
    if ("android".equals(paramString)) {
      return Resources.getSystem();
    }
    paramContext = paramContext.getPackageManager();
    try
    {
      ApplicationInfo localApplicationInfo = paramContext.getApplicationInfo(paramString, 8192);
      if (localApplicationInfo != null)
      {
        paramContext = paramContext.getResourcesForApplication(localApplicationInfo);
        return paramContext;
      }
      return null;
    }
    catch (PackageManager.NameNotFoundException paramContext)
    {
      paramString = String.format("Unable to find pkg=%s for icon", new Object[] { paramString });
      Log5ECF72.a(paramString);
      LogE84000.a(paramString);
      Log229316.a(paramString);
      Log.e("IconCompat", paramString, paramContext);
    }
    return null;
  }
  
  private Drawable loadDrawableInner(Context paramContext)
  {
    Object localObject;
    switch (this.mType)
    {
    default: 
      break;
    case 6: 
      localObject = getUriInputStream(paramContext);
      if (localObject != null)
      {
        if (Build.VERSION.SDK_INT >= 26) {
          return Api26Impl.createAdaptiveIconDrawable(null, new BitmapDrawable(paramContext.getResources(), BitmapFactory.decodeStream((InputStream)localObject)));
        }
        return new BitmapDrawable(paramContext.getResources(), createLegacyIconFromAdaptiveIcon(BitmapFactory.decodeStream((InputStream)localObject), false));
      }
      break;
    case 5: 
      return new BitmapDrawable(paramContext.getResources(), createLegacyIconFromAdaptiveIcon((Bitmap)this.mObj1, false));
    case 4: 
      localObject = getUriInputStream(paramContext);
      if (localObject != null) {
        return new BitmapDrawable(paramContext.getResources(), BitmapFactory.decodeStream((InputStream)localObject));
      }
      break;
    case 3: 
      return new BitmapDrawable(paramContext.getResources(), BitmapFactory.decodeByteArray((byte[])this.mObj1, this.mInt1, this.mInt2));
    case 2: 
      String str = getResPackage();
      localObject = str;
      if (TextUtils.isEmpty(str)) {
        localObject = paramContext.getPackageName();
      }
      localObject = getResources(paramContext, (String)localObject);
      try
      {
        paramContext = ResourcesCompat.getDrawable((Resources)localObject, this.mInt1, paramContext.getTheme());
        return paramContext;
      }
      catch (RuntimeException localRuntimeException)
      {
        paramContext = String.format("Unable to load resource 0x%08x from pkg=%s", new Object[] { Integer.valueOf(this.mInt1), this.mObj1 });
        Log5ECF72.a(paramContext);
        LogE84000.a(paramContext);
        Log229316.a(paramContext);
        Log.e("IconCompat", paramContext, localRuntimeException);
      }
    case 1: 
      return new BitmapDrawable(paramContext.getResources(), (Bitmap)this.mObj1);
    }
    return null;
  }
  
  private static String typeToString(int paramInt)
  {
    switch (paramInt)
    {
    default: 
      return "UNKNOWN";
    case 6: 
      return "URI_MASKABLE";
    case 5: 
      return "BITMAP_MASKABLE";
    case 4: 
      return "URI";
    case 3: 
      return "DATA";
    case 2: 
      return "RESOURCE";
    }
    return "BITMAP";
  }
  
  public void addToShortcutIntent(Intent paramIntent, Drawable paramDrawable, Context paramContext)
  {
    checkResource(paramContext);
    Object localObject;
    int i;
    switch (this.mType)
    {
    case 3: 
    case 4: 
    default: 
      throw new IllegalArgumentException("Icon type not supported for intent shortcuts");
    case 5: 
      paramContext = createLegacyIconFromAdaptiveIcon((Bitmap)this.mObj1, true);
      break;
    case 2: 
      try
      {
        paramContext = paramContext.createPackageContext(getResPackage(), 0);
        if (paramDrawable == null)
        {
          paramIntent.putExtra("android.intent.extra.shortcut.ICON_RESOURCE", Intent.ShortcutIconResource.fromContext(paramContext, this.mInt1));
          return;
        }
        localObject = ContextCompat.getDrawable(paramContext, this.mInt1);
        if ((((Drawable)localObject).getIntrinsicWidth() > 0) && (((Drawable)localObject).getIntrinsicHeight() > 0))
        {
          paramContext = Bitmap.createBitmap(((Drawable)localObject).getIntrinsicWidth(), ((Drawable)localObject).getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        }
        else
        {
          i = ((ActivityManager)paramContext.getSystemService("activity")).getLauncherLargeIconSize();
          paramContext = Bitmap.createBitmap(i, i, Bitmap.Config.ARGB_8888);
        }
        ((Drawable)localObject).setBounds(0, 0, paramContext.getWidth(), paramContext.getHeight());
        Canvas localCanvas = new android/graphics/Canvas;
        localCanvas.<init>(paramContext);
        ((Drawable)localObject).draw(localCanvas);
      }
      catch (PackageManager.NameNotFoundException paramIntent)
      {
        throw new IllegalArgumentException("Can't find package " + this.mObj1, paramIntent);
      }
    case 1: 
      localObject = (Bitmap)this.mObj1;
      paramContext = (Context)localObject;
      if (paramDrawable != null) {
        paramContext = ((Bitmap)localObject).copy(((Bitmap)localObject).getConfig(), true);
      }
      break;
    }
    if (paramDrawable != null)
    {
      int j = paramContext.getWidth();
      i = paramContext.getHeight();
      paramDrawable.setBounds(j / 2, i / 2, j, i);
      paramDrawable.draw(new Canvas(paramContext));
    }
    paramIntent.putExtra("android.intent.extra.shortcut.ICON", paramContext);
  }
  
  public void checkResource(Context paramContext)
  {
    if (this.mType == 2)
    {
      Object localObject = this.mObj1;
      if (localObject != null)
      {
        localObject = (String)localObject;
        if (!((String)localObject).contains(":")) {
          return;
        }
        String str2 = localObject.split(":", -1)[1];
        String str1 = str2.split("/", -1)[0];
        str2 = str2.split("/", -1)[1];
        String str3 = localObject.split(":", -1)[0];
        if ("0_resource_name_obfuscated".equals(str2))
        {
          Log.i("IconCompat", "Found obfuscated resource, not trying to update resource id for it");
          return;
        }
        String str4 = getResPackage();
        int i = getResources(paramContext, str4).getIdentifier(str2, str1, str3);
        if (this.mInt1 != i)
        {
          Log.i("IconCompat", "Id has changed for " + str4 + " " + (String)localObject);
          this.mInt1 = i;
        }
      }
    }
  }
  
  public Bitmap getBitmap()
  {
    if ((this.mType == -1) && (Build.VERSION.SDK_INT >= 23))
    {
      Object localObject = this.mObj1;
      if ((localObject instanceof Bitmap)) {
        return (Bitmap)localObject;
      }
      return null;
    }
    int i = this.mType;
    if (i == 1) {
      return (Bitmap)this.mObj1;
    }
    if (i == 5) {
      return createLegacyIconFromAdaptiveIcon((Bitmap)this.mObj1, true);
    }
    throw new IllegalStateException("called getBitmap() on " + this);
  }
  
  public int getResId()
  {
    if ((this.mType == -1) && (Build.VERSION.SDK_INT >= 23)) {
      return Api23Impl.getResId(this.mObj1);
    }
    if (this.mType == 2) {
      return this.mInt1;
    }
    throw new IllegalStateException("called getResId() on " + this);
  }
  
  public String getResPackage()
  {
    String str;
    if ((this.mType == -1) && (Build.VERSION.SDK_INT >= 23))
    {
      str = Api23Impl.getResPackage(this.mObj1);
      Log5ECF72.a(str);
      LogE84000.a(str);
      Log229316.a(str);
      return str;
    }
    if (this.mType == 2)
    {
      str = this.mString1;
      if ((str != null) && (!TextUtils.isEmpty(str))) {
        return this.mString1;
      }
      return ((String)this.mObj1).split(":", -1)[0];
    }
    throw new IllegalStateException("called getResPackage() on " + this);
  }
  
  public int getType()
  {
    if ((this.mType == -1) && (Build.VERSION.SDK_INT >= 23)) {
      return Api23Impl.getType(this.mObj1);
    }
    return this.mType;
  }
  
  public Uri getUri()
  {
    if ((this.mType == -1) && (Build.VERSION.SDK_INT >= 23)) {
      return Api23Impl.getUri(this.mObj1);
    }
    int i = this.mType;
    if ((i != 4) && (i != 6)) {
      throw new IllegalStateException("called getUri() on " + this);
    }
    return Uri.parse((String)this.mObj1);
  }
  
  public InputStream getUriInputStream(Context paramContext)
  {
    Uri localUri = getUri();
    String str = localUri.getScheme();
    if ((!"content".equals(str)) && (!"file".equals(str))) {
      try
      {
        paramContext = new java/io/File;
        paramContext.<init>((String)this.mObj1);
        paramContext = new FileInputStream(paramContext);
        return paramContext;
      }
      catch (FileNotFoundException paramContext)
      {
        Log.w("IconCompat", "Unable to load image from path: " + localUri, paramContext);
      }
    } else {
      try
      {
        paramContext = paramContext.getContentResolver().openInputStream(localUri);
        return paramContext;
      }
      catch (Exception paramContext)
      {
        Log.w("IconCompat", "Unable to load image from URI: " + localUri, paramContext);
      }
    }
    return null;
  }
  
  public Drawable loadDrawable(Context paramContext)
  {
    checkResource(paramContext);
    if (Build.VERSION.SDK_INT >= 23) {
      return Api23Impl.loadDrawable(toIcon(paramContext), paramContext);
    }
    paramContext = loadDrawableInner(paramContext);
    if ((paramContext != null) && ((this.mTintList != null) || (this.mTintMode != DEFAULT_TINT_MODE)))
    {
      paramContext.mutate();
      DrawableCompat.setTintList(paramContext, this.mTintList);
      DrawableCompat.setTintMode(paramContext, this.mTintMode);
    }
    return paramContext;
  }
  
  public void onPostParceling()
  {
    this.mTintMode = PorterDuff.Mode.valueOf(this.mTintModeStr);
    Object localObject;
    switch (this.mType)
    {
    case 0: 
    default: 
      break;
    case 3: 
      this.mObj1 = this.mData;
      break;
    case 2: 
    case 4: 
    case 6: 
      String str = new String(this.mData, Charset.forName("UTF-16"));
      Log5ECF72.a(str);
      LogE84000.a(str);
      Log229316.a(str);
      this.mObj1 = str;
      if ((this.mType == 2) && (this.mString1 == null))
      {
        localObject = (String)str;
        this.mString1 = str.split(":", -1)[0];
      }
      break;
    case 1: 
    case 5: 
      localObject = this.mParcelable;
      if (localObject != null)
      {
        this.mObj1 = localObject;
      }
      else
      {
        localObject = this.mData;
        this.mObj1 = localObject;
        this.mType = 3;
        this.mInt1 = 0;
        this.mInt2 = localObject.length;
      }
      break;
    case -1: 
      localObject = this.mParcelable;
      if (localObject != null) {
        this.mObj1 = localObject;
      } else {
        throw new IllegalArgumentException("Invalid icon");
      }
      break;
    }
  }
  
  public void onPreParceling(boolean paramBoolean)
  {
    this.mTintModeStr = this.mTintMode.name();
    switch (this.mType)
    {
    case 0: 
    default: 
      break;
    case 4: 
    case 6: 
      this.mData = this.mObj1.toString().getBytes(Charset.forName("UTF-16"));
      break;
    case 3: 
      this.mData = ((byte[])this.mObj1);
      break;
    case 2: 
      this.mData = ((String)this.mObj1).getBytes(Charset.forName("UTF-16"));
      break;
    case 1: 
    case 5: 
      if (paramBoolean)
      {
        Bitmap localBitmap = (Bitmap)this.mObj1;
        ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
        localBitmap.compress(Bitmap.CompressFormat.PNG, 90, localByteArrayOutputStream);
        this.mData = localByteArrayOutputStream.toByteArray();
      }
      else
      {
        this.mParcelable = ((Parcelable)this.mObj1);
      }
      break;
    case -1: 
      if (!paramBoolean) {
        this.mParcelable = ((Parcelable)this.mObj1);
      } else {
        throw new IllegalArgumentException("Can't serialize Icon created with IconCompat#createFromIcon");
      }
      break;
    }
  }
  
  public IconCompat setTint(int paramInt)
  {
    return setTintList(ColorStateList.valueOf(paramInt));
  }
  
  public IconCompat setTintList(ColorStateList paramColorStateList)
  {
    this.mTintList = paramColorStateList;
    return this;
  }
  
  public IconCompat setTintMode(PorterDuff.Mode paramMode)
  {
    this.mTintMode = paramMode;
    return this;
  }
  
  public Bundle toBundle()
  {
    Bundle localBundle = new Bundle();
    switch (this.mType)
    {
    case 0: 
    default: 
      throw new IllegalArgumentException("Invalid icon");
    case 3: 
      localBundle.putByteArray("obj", (byte[])this.mObj1);
      break;
    case 2: 
    case 4: 
    case 6: 
      localBundle.putString("obj", (String)this.mObj1);
      break;
    case 1: 
    case 5: 
      localBundle.putParcelable("obj", (Bitmap)this.mObj1);
      break;
    case -1: 
      localBundle.putParcelable("obj", (Parcelable)this.mObj1);
    }
    localBundle.putInt("type", this.mType);
    localBundle.putInt("int1", this.mInt1);
    localBundle.putInt("int2", this.mInt2);
    localBundle.putString("string1", this.mString1);
    Object localObject = this.mTintList;
    if (localObject != null) {
      localBundle.putParcelable("tint_list", (Parcelable)localObject);
    }
    localObject = this.mTintMode;
    if (localObject != DEFAULT_TINT_MODE) {
      localBundle.putString("tint_mode", ((PorterDuff.Mode)localObject).name());
    }
    return localBundle;
  }
  
  @Deprecated
  public Icon toIcon()
  {
    return toIcon(null);
  }
  
  public Icon toIcon(Context paramContext)
  {
    if (Build.VERSION.SDK_INT >= 23) {
      return Api23Impl.toIcon(this, paramContext);
    }
    throw new UnsupportedOperationException("This method is only supported on API level 23+");
  }
  
  public String toString()
  {
    if (this.mType == -1)
    {
      localObject = String.valueOf(this.mObj1);
      Log5ECF72.a(localObject);
      LogE84000.a(localObject);
      Log229316.a(localObject);
      return (String)localObject;
    }
    Object localObject = new StringBuilder("Icon(typ=");
    String str = typeToString(this.mType);
    Log5ECF72.a(str);
    LogE84000.a(str);
    Log229316.a(str);
    StringBuilder localStringBuilder = ((StringBuilder)localObject).append(str);
    switch (this.mType)
    {
    default: 
      break;
    case 4: 
    case 6: 
      localStringBuilder.append(" uri=").append(this.mObj1);
      break;
    case 3: 
      localStringBuilder.append(" len=").append(this.mInt1);
      if (this.mInt2 != 0) {
        localStringBuilder.append(" off=").append(this.mInt2);
      }
      break;
    case 2: 
      localObject = localStringBuilder.append(" pkg=").append(this.mString1).append(" id=");
      str = String.format("0x%08x", new Object[] { Integer.valueOf(getResId()) });
      Log5ECF72.a(str);
      LogE84000.a(str);
      Log229316.a(str);
      ((StringBuilder)localObject).append(str);
      break;
    case 1: 
    case 5: 
      localStringBuilder.append(" size=").append(((Bitmap)this.mObj1).getWidth()).append("x").append(((Bitmap)this.mObj1).getHeight());
    }
    if (this.mTintList != null)
    {
      localStringBuilder.append(" tint=");
      localStringBuilder.append(this.mTintList);
    }
    if (this.mTintMode != DEFAULT_TINT_MODE) {
      localStringBuilder.append(" mode=").append(this.mTintMode);
    }
    localStringBuilder.append(")");
    return localStringBuilder.toString();
  }
  
  static class Api23Impl
  {
    static IconCompat createFromIcon(Context paramContext, Icon paramIcon)
    {
      switch (getType(paramIcon))
      {
      case 3: 
      case 5: 
      default: 
        paramContext = new IconCompat(-1);
        paramContext.mObj1 = paramIcon;
        return paramContext;
      case 6: 
        return IconCompat.createWithAdaptiveBitmapContentUri(getUri(paramIcon));
      case 4: 
        return IconCompat.createWithContentUri(getUri(paramIcon));
      }
      String str = getResPackage(paramIcon);
      Log5ECF72.a(str);
      LogE84000.a(str);
      Log229316.a(str);
      try
      {
        paramContext = IconCompat.createWithResource(IconCompat.getResources(paramContext, str), str, getResId(paramIcon));
        return paramContext;
      }
      catch (Resources.NotFoundException paramContext)
      {
        throw new IllegalArgumentException("Icon resource cannot be found");
      }
    }
    
    static IconCompat createFromIconInner(Object paramObject)
    {
      Preconditions.checkNotNull(paramObject);
      switch (getType(paramObject))
      {
      case 3: 
      case 5: 
      default: 
        localObject = new IconCompat(-1);
        ((IconCompat)localObject).mObj1 = paramObject;
        return (IconCompat)localObject;
      case 6: 
        return IconCompat.createWithAdaptiveBitmapContentUri(getUri(paramObject));
      case 4: 
        return IconCompat.createWithContentUri(getUri(paramObject));
      }
      Object localObject = getResPackage(paramObject);
      Log5ECF72.a(localObject);
      LogE84000.a(localObject);
      Log229316.a(localObject);
      return IconCompat.createWithResource(null, (String)localObject, getResId(paramObject));
    }
    
    static int getResId(Object paramObject)
    {
      if (Build.VERSION.SDK_INT >= 28) {
        return IconCompat.Api28Impl.getResId(paramObject);
      }
      try
      {
        int i = ((Integer)paramObject.getClass().getMethod("getResId", new Class[0]).invoke(paramObject, new Object[0])).intValue();
        return i;
      }
      catch (NoSuchMethodException paramObject)
      {
        Log.e("IconCompat", "Unable to get icon resource", (Throwable)paramObject);
        return 0;
      }
      catch (InvocationTargetException paramObject)
      {
        Log.e("IconCompat", "Unable to get icon resource", (Throwable)paramObject);
        return 0;
      }
      catch (IllegalAccessException paramObject)
      {
        Log.e("IconCompat", "Unable to get icon resource", (Throwable)paramObject);
      }
      return 0;
    }
    
    static String getResPackage(Object paramObject)
    {
      if (Build.VERSION.SDK_INT >= 28)
      {
        paramObject = IconCompat.Api28Impl.getResPackage(paramObject);
        Log5ECF72.a(paramObject);
        LogE84000.a(paramObject);
        Log229316.a(paramObject);
        return (String)paramObject;
      }
      try
      {
        paramObject = (String)paramObject.getClass().getMethod("getResPackage", new Class[0]).invoke(paramObject, new Object[0]);
        return (String)paramObject;
      }
      catch (NoSuchMethodException paramObject)
      {
        Log.e("IconCompat", "Unable to get icon package", (Throwable)paramObject);
        return null;
      }
      catch (InvocationTargetException paramObject)
      {
        Log.e("IconCompat", "Unable to get icon package", (Throwable)paramObject);
        return null;
      }
      catch (IllegalAccessException paramObject)
      {
        Log.e("IconCompat", "Unable to get icon package", (Throwable)paramObject);
      }
      return null;
    }
    
    static int getType(Object paramObject)
    {
      if (Build.VERSION.SDK_INT >= 28) {
        return IconCompat.Api28Impl.getType(paramObject);
      }
      try
      {
        int i = ((Integer)paramObject.getClass().getMethod("getType", new Class[0]).invoke(paramObject, new Object[0])).intValue();
        return i;
      }
      catch (NoSuchMethodException localNoSuchMethodException)
      {
        Log.e("IconCompat", "Unable to get icon type " + paramObject, localNoSuchMethodException);
        return -1;
      }
      catch (InvocationTargetException localInvocationTargetException)
      {
        Log.e("IconCompat", "Unable to get icon type " + paramObject, localInvocationTargetException);
        return -1;
      }
      catch (IllegalAccessException localIllegalAccessException)
      {
        Log.e("IconCompat", "Unable to get icon type " + paramObject, localIllegalAccessException);
      }
      return -1;
    }
    
    static Uri getUri(Object paramObject)
    {
      if (Build.VERSION.SDK_INT >= 28) {
        return IconCompat.Api28Impl.getUri(paramObject);
      }
      try
      {
        paramObject = (Uri)paramObject.getClass().getMethod("getUri", new Class[0]).invoke(paramObject, new Object[0]);
        return (Uri)paramObject;
      }
      catch (NoSuchMethodException paramObject)
      {
        Log.e("IconCompat", "Unable to get icon uri", (Throwable)paramObject);
        return null;
      }
      catch (InvocationTargetException paramObject)
      {
        Log.e("IconCompat", "Unable to get icon uri", (Throwable)paramObject);
        return null;
      }
      catch (IllegalAccessException paramObject)
      {
        Log.e("IconCompat", "Unable to get icon uri", (Throwable)paramObject);
      }
      return null;
    }
    
    static Drawable loadDrawable(Icon paramIcon, Context paramContext)
    {
      return paramIcon.loadDrawable(paramContext);
    }
    
    static Icon toIcon(IconCompat paramIconCompat, Context paramContext)
    {
      switch (paramIconCompat.mType)
      {
      case 0: 
      default: 
        throw new IllegalArgumentException("Unknown type");
      case 6: 
        if (Build.VERSION.SDK_INT >= 30)
        {
          paramContext = IconCompat.Api30Impl.createWithAdaptiveBitmapContentUri(paramIconCompat.getUri());
        }
        else if (paramContext != null)
        {
          paramContext = paramIconCompat.getUriInputStream(paramContext);
          if (paramContext != null)
          {
            if (Build.VERSION.SDK_INT >= 26) {
              paramContext = IconCompat.Api26Impl.createWithAdaptiveBitmap(BitmapFactory.decodeStream(paramContext));
            } else {
              paramContext = Icon.createWithBitmap(IconCompat.createLegacyIconFromAdaptiveIcon(BitmapFactory.decodeStream(paramContext), false));
            }
          }
          else {
            throw new IllegalStateException("Cannot load adaptive icon from uri: " + paramIconCompat.getUri());
          }
        }
        else
        {
          throw new IllegalArgumentException("Context is required to resolve the file uri of the icon: " + paramIconCompat.getUri());
        }
      case 5: 
        if (Build.VERSION.SDK_INT >= 26) {
          paramContext = IconCompat.Api26Impl.createWithAdaptiveBitmap((Bitmap)paramIconCompat.mObj1);
        } else {
          paramContext = Icon.createWithBitmap(IconCompat.createLegacyIconFromAdaptiveIcon((Bitmap)paramIconCompat.mObj1, false));
        }
        break;
      case 4: 
        paramContext = Icon.createWithContentUri((String)paramIconCompat.mObj1);
        break;
      case 3: 
        paramContext = Icon.createWithData((byte[])paramIconCompat.mObj1, paramIconCompat.mInt1, paramIconCompat.mInt2);
        break;
      case 2: 
        paramContext = Icon.createWithResource(paramIconCompat.getResPackage(), paramIconCompat.mInt1);
        break;
      case 1: 
        paramContext = Icon.createWithBitmap((Bitmap)paramIconCompat.mObj1);
        if (paramIconCompat.mTintList != null) {
          paramContext.setTintList(paramIconCompat.mTintList);
        }
        if (paramIconCompat.mTintMode != IconCompat.DEFAULT_TINT_MODE) {
          paramContext.setTintMode(paramIconCompat.mTintMode);
        }
        return paramContext;
      }
      return (Icon)paramIconCompat.mObj1;
    }
  }
  
  static class Api26Impl
  {
    static Drawable createAdaptiveIconDrawable(Drawable paramDrawable1, Drawable paramDrawable2)
    {
      return new AdaptiveIconDrawable(paramDrawable1, paramDrawable2);
    }
    
    static Icon createWithAdaptiveBitmap(Bitmap paramBitmap)
    {
      return Icon.createWithAdaptiveBitmap(paramBitmap);
    }
  }
  
  static class Api28Impl
  {
    static int getResId(Object paramObject)
    {
      return ((Icon)paramObject).getResId();
    }
    
    static String getResPackage(Object paramObject)
    {
      return ((Icon)paramObject).getResPackage();
    }
    
    static int getType(Object paramObject)
    {
      return ((Icon)paramObject).getType();
    }
    
    static Uri getUri(Object paramObject)
    {
      return ((Icon)paramObject).getUri();
    }
  }
  
  static class Api30Impl
  {
    static Icon createWithAdaptiveBitmapContentUri(Uri paramUri)
    {
      return Icon.createWithAdaptiveBitmapContentUri(paramUri);
    }
  }
  
  @Retention(RetentionPolicy.SOURCE)
  public static @interface IconType {}
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/core/graphics/drawable/IconCompat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */