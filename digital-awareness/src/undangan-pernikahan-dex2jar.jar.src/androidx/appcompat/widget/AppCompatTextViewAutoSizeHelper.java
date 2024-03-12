package androidx.appcompat.widget;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.RectF;
import android.os.Build.VERSION;
import android.text.Layout.Alignment;
import android.text.StaticLayout;
import android.text.StaticLayout.Builder;
import android.text.TextDirectionHeuristic;
import android.text.TextDirectionHeuristics;
import android.text.TextPaint;
import android.text.method.TransformationMethod;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.R.styleable;
import androidx.core.view.ViewCompat;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import mt.Log229316;
import mt.Log5ECF72;
import mt.LogE84000;

class AppCompatTextViewAutoSizeHelper
{
  private static final int DEFAULT_AUTO_SIZE_GRANULARITY_IN_PX = 1;
  private static final int DEFAULT_AUTO_SIZE_MAX_TEXT_SIZE_IN_SP = 112;
  private static final int DEFAULT_AUTO_SIZE_MIN_TEXT_SIZE_IN_SP = 12;
  private static final String TAG = "ACTVAutoSizeHelper";
  private static final RectF TEMP_RECTF = new RectF();
  static final float UNSET_AUTO_SIZE_UNIFORM_CONFIGURATION_VALUE = -1.0F;
  private static final int VERY_WIDE = 1048576;
  private static ConcurrentHashMap<String, Field> sTextViewFieldByNameCache = new ConcurrentHashMap();
  private static ConcurrentHashMap<String, Method> sTextViewMethodByNameCache = new ConcurrentHashMap();
  private float mAutoSizeMaxTextSizeInPx = -1.0F;
  private float mAutoSizeMinTextSizeInPx = -1.0F;
  private float mAutoSizeStepGranularityInPx = -1.0F;
  private int[] mAutoSizeTextSizesInPx = new int[0];
  private int mAutoSizeTextType = 0;
  private final Context mContext;
  private boolean mHasPresetAutoSizeValues = false;
  private final Impl mImpl;
  private boolean mNeedsAutoSizeText = false;
  private TextPaint mTempTextPaint;
  private final TextView mTextView;
  
  AppCompatTextViewAutoSizeHelper(TextView paramTextView)
  {
    this.mTextView = paramTextView;
    this.mContext = paramTextView.getContext();
    if (Build.VERSION.SDK_INT >= 29) {
      this.mImpl = new Impl29();
    } else if (Build.VERSION.SDK_INT >= 23) {
      this.mImpl = new Impl23();
    } else {
      this.mImpl = new Impl();
    }
  }
  
  private static <T> T accessAndReturnWithDefault(Object paramObject, String paramString, T paramT)
  {
    try
    {
      Field localField = getTextViewField(paramString);
      if (localField == null) {
        return paramT;
      }
      paramObject = localField.get(paramObject);
      return (T)paramObject;
    }
    catch (IllegalAccessException paramObject)
    {
      Log.w("ACTVAutoSizeHelper", "Failed to access TextView#" + paramString + " member", (Throwable)paramObject);
    }
    return paramT;
  }
  
  private int[] cleanupAutoSizePresetSizes(int[] paramArrayOfInt)
  {
    int j = paramArrayOfInt.length;
    if (j == 0) {
      return paramArrayOfInt;
    }
    Arrays.sort(paramArrayOfInt);
    ArrayList localArrayList = new ArrayList();
    for (int i = 0; i < j; i++)
    {
      int k = paramArrayOfInt[i];
      if ((k > 0) && (Collections.binarySearch(localArrayList, Integer.valueOf(k)) < 0)) {
        localArrayList.add(Integer.valueOf(k));
      }
    }
    if (j == localArrayList.size()) {
      return paramArrayOfInt;
    }
    j = localArrayList.size();
    paramArrayOfInt = new int[j];
    for (i = 0; i < j; i++) {
      paramArrayOfInt[i] = ((Integer)localArrayList.get(i)).intValue();
    }
    return paramArrayOfInt;
  }
  
  private void clearAutoSizeConfiguration()
  {
    this.mAutoSizeTextType = 0;
    this.mAutoSizeMinTextSizeInPx = -1.0F;
    this.mAutoSizeMaxTextSizeInPx = -1.0F;
    this.mAutoSizeStepGranularityInPx = -1.0F;
    this.mAutoSizeTextSizesInPx = new int[0];
    this.mNeedsAutoSizeText = false;
  }
  
  private StaticLayout createStaticLayoutForMeasuringPre16(CharSequence paramCharSequence, Layout.Alignment paramAlignment, int paramInt)
  {
    float f2 = ((Float)accessAndReturnWithDefault(this.mTextView, "mSpacingMult", Float.valueOf(1.0F))).floatValue();
    float f1 = ((Float)accessAndReturnWithDefault(this.mTextView, "mSpacingAdd", Float.valueOf(0.0F))).floatValue();
    boolean bool = ((Boolean)accessAndReturnWithDefault(this.mTextView, "mIncludePad", Boolean.valueOf(true))).booleanValue();
    return new StaticLayout(paramCharSequence, this.mTempTextPaint, paramInt, paramAlignment, f2, f1, bool);
  }
  
  private int findLargestTextSizeWhichFits(RectF paramRectF)
  {
    int k = this.mAutoSizeTextSizesInPx.length;
    if (k != 0)
    {
      int j = 0;
      int i = 0 + 1;
      k--;
      while (i <= k)
      {
        j = (i + k) / 2;
        if (suggestedSizeFitsInSpace(this.mAutoSizeTextSizesInPx[j], paramRectF))
        {
          int m = j + 1;
          j = i;
          i = m;
        }
        else
        {
          k = j - 1;
          j = k;
        }
      }
      return this.mAutoSizeTextSizesInPx[j];
    }
    throw new IllegalStateException("No available text sizes to choose from.");
  }
  
  private static Field getTextViewField(String paramString)
  {
    try
    {
      Field localField2 = (Field)sTextViewFieldByNameCache.get(paramString);
      Field localField1 = localField2;
      if (localField2 == null)
      {
        localField2 = TextView.class.getDeclaredField(paramString);
        localField1 = localField2;
        if (localField2 != null)
        {
          localField2.setAccessible(true);
          sTextViewFieldByNameCache.put(paramString, localField2);
          localField1 = localField2;
        }
      }
      return localField1;
    }
    catch (NoSuchFieldException localNoSuchFieldException)
    {
      Log.w("ACTVAutoSizeHelper", "Failed to access TextView#" + paramString + " member", localNoSuchFieldException);
    }
    return null;
  }
  
  private static Method getTextViewMethod(String paramString)
  {
    try
    {
      Method localMethod2 = (Method)sTextViewMethodByNameCache.get(paramString);
      Method localMethod1 = localMethod2;
      if (localMethod2 == null)
      {
        localMethod2 = TextView.class.getDeclaredMethod(paramString, new Class[0]);
        localMethod1 = localMethod2;
        if (localMethod2 != null)
        {
          localMethod2.setAccessible(true);
          sTextViewMethodByNameCache.put(paramString, localMethod2);
          localMethod1 = localMethod2;
        }
      }
      return localMethod1;
    }
    catch (Exception localException)
    {
      Log.w("ACTVAutoSizeHelper", "Failed to retrieve TextView#" + paramString + "() method", localException);
    }
    return null;
  }
  
  /* Error */
  static <T> T invokeAndReturnWithDefault(Object paramObject, String paramString, T paramT)
  {
    // Byte code:
    //   0: aconst_null
    //   1: astore 5
    //   3: iconst_0
    //   4: istore_3
    //   5: aload_1
    //   6: invokestatic 274	androidx/appcompat/widget/AppCompatTextViewAutoSizeHelper:getTextViewMethod	(Ljava/lang/String;)Ljava/lang/reflect/Method;
    //   9: aload_0
    //   10: iconst_0
    //   11: anewarray 4	java/lang/Object
    //   14: invokevirtual 278	java/lang/reflect/Method:invoke	(Ljava/lang/Object;[Ljava/lang/Object;)Ljava/lang/Object;
    //   17: astore_0
    //   18: aload_0
    //   19: astore_1
    //   20: aload_1
    //   21: astore_0
    //   22: aload_1
    //   23: ifnonnull +85 -> 108
    //   26: aload_1
    //   27: astore_0
    //   28: iconst_0
    //   29: ifeq +79 -> 108
    //   32: aload_2
    //   33: astore_0
    //   34: goto +74 -> 108
    //   37: astore_0
    //   38: goto +72 -> 110
    //   41: astore 6
    //   43: iconst_1
    //   44: istore 4
    //   46: iload 4
    //   48: istore_3
    //   49: new 132	java/lang/StringBuilder
    //   52: astore_0
    //   53: iload 4
    //   55: istore_3
    //   56: aload_0
    //   57: invokespecial 133	java/lang/StringBuilder:<init>	()V
    //   60: iload 4
    //   62: istore_3
    //   63: ldc 33
    //   65: aload_0
    //   66: ldc_w 280
    //   69: invokevirtual 139	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   72: aload_1
    //   73: invokevirtual 139	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   76: ldc_w 271
    //   79: invokevirtual 139	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   82: invokevirtual 145	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   85: aload 6
    //   87: invokestatic 151	android/util/Log:w	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I
    //   90: pop
    //   91: aload 5
    //   93: astore_0
    //   94: iconst_0
    //   95: ifne +13 -> 108
    //   98: aload 5
    //   100: astore_0
    //   101: iconst_1
    //   102: ifeq +6 -> 108
    //   105: goto -73 -> 32
    //   108: aload_0
    //   109: areturn
    //   110: iconst_0
    //   111: ifne +7 -> 118
    //   114: iload_3
    //   115: ifeq +3 -> 118
    //   118: aload_0
    //   119: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	120	0	paramObject	Object
    //   0	120	1	paramString	String
    //   0	120	2	paramT	T
    //   4	111	3	i	int
    //   44	17	4	j	int
    //   1	98	5	localObject	Object
    //   41	45	6	localException	Exception
    // Exception table:
    //   from	to	target	type
    //   5	18	37	finally
    //   49	53	37	finally
    //   56	60	37	finally
    //   63	91	37	finally
    //   5	18	41	java/lang/Exception
  }
  
  private void setRawTextSize(float paramFloat)
  {
    if (paramFloat != this.mTextView.getPaint().getTextSize())
    {
      this.mTextView.getPaint().setTextSize(paramFloat);
      boolean bool = false;
      if (Build.VERSION.SDK_INT >= 18) {
        bool = Api18Impl.isInLayout(this.mTextView);
      }
      if (this.mTextView.getLayout() != null)
      {
        this.mNeedsAutoSizeText = false;
        try
        {
          Method localMethod = getTextViewMethod("nullLayouts");
          if (localMethod != null) {
            localMethod.invoke(this.mTextView, new Object[0]);
          }
        }
        catch (Exception localException)
        {
          Log.w("ACTVAutoSizeHelper", "Failed to invoke TextView#nullLayouts() method", localException);
        }
        if (!bool) {
          this.mTextView.requestLayout();
        } else {
          this.mTextView.forceLayout();
        }
        this.mTextView.invalidate();
      }
    }
  }
  
  private boolean setupAutoSizeText()
  {
    if ((supportsAutoSizeText()) && (this.mAutoSizeTextType == 1))
    {
      if ((!this.mHasPresetAutoSizeValues) || (this.mAutoSizeTextSizesInPx.length == 0))
      {
        int j = (int)Math.floor((this.mAutoSizeMaxTextSizeInPx - this.mAutoSizeMinTextSizeInPx) / this.mAutoSizeStepGranularityInPx) + 1;
        int[] arrayOfInt = new int[j];
        for (int i = 0; i < j; i++) {
          arrayOfInt[i] = Math.round(this.mAutoSizeMinTextSizeInPx + i * this.mAutoSizeStepGranularityInPx);
        }
        this.mAutoSizeTextSizesInPx = cleanupAutoSizePresetSizes(arrayOfInt);
      }
      this.mNeedsAutoSizeText = true;
    }
    else
    {
      this.mNeedsAutoSizeText = false;
    }
    return this.mNeedsAutoSizeText;
  }
  
  private void setupAutoSizeUniformPresetSizes(TypedArray paramTypedArray)
  {
    int j = paramTypedArray.length();
    int[] arrayOfInt = new int[j];
    if (j > 0)
    {
      for (int i = 0; i < j; i++) {
        arrayOfInt[i] = paramTypedArray.getDimensionPixelSize(i, -1);
      }
      this.mAutoSizeTextSizesInPx = cleanupAutoSizePresetSizes(arrayOfInt);
      setupAutoSizeUniformPresetSizesConfiguration();
    }
  }
  
  private boolean setupAutoSizeUniformPresetSizesConfiguration()
  {
    int[] arrayOfInt = this.mAutoSizeTextSizesInPx;
    int i = arrayOfInt.length;
    boolean bool;
    if (i > 0) {
      bool = true;
    } else {
      bool = false;
    }
    this.mHasPresetAutoSizeValues = bool;
    if (bool)
    {
      this.mAutoSizeTextType = 1;
      this.mAutoSizeMinTextSizeInPx = arrayOfInt[0];
      this.mAutoSizeMaxTextSizeInPx = arrayOfInt[(i - 1)];
      this.mAutoSizeStepGranularityInPx = -1.0F;
    }
    return bool;
  }
  
  private boolean suggestedSizeFitsInSpace(int paramInt, RectF paramRectF)
  {
    Object localObject2 = this.mTextView.getText();
    Object localObject3 = this.mTextView.getTransformationMethod();
    Object localObject1 = localObject2;
    if (localObject3 != null)
    {
      localObject3 = ((TransformationMethod)localObject3).getTransformation((CharSequence)localObject2, this.mTextView);
      localObject1 = localObject2;
      if (localObject3 != null) {
        localObject1 = localObject3;
      }
    }
    int i;
    if (Build.VERSION.SDK_INT >= 16) {
      i = Api16Impl.getMaxLines(this.mTextView);
    } else {
      i = -1;
    }
    initTempTextPaint(paramInt);
    localObject2 = createLayout((CharSequence)localObject1, (Layout.Alignment)invokeAndReturnWithDefault(this.mTextView, "getLayoutAlignment", Layout.Alignment.ALIGN_NORMAL), Math.round(paramRectF.right), i);
    if ((i != -1) && ((((StaticLayout)localObject2).getLineCount() > i) || (((StaticLayout)localObject2).getLineEnd(((StaticLayout)localObject2).getLineCount() - 1) != ((CharSequence)localObject1).length()))) {
      return false;
    }
    return ((StaticLayout)localObject2).getHeight() <= paramRectF.bottom;
  }
  
  private boolean supportsAutoSizeText()
  {
    return this.mTextView instanceof AppCompatEditText ^ true;
  }
  
  private void validateAndSetAutoSizeTextTypeUniformConfiguration(float paramFloat1, float paramFloat2, float paramFloat3)
    throws IllegalArgumentException
  {
    if (paramFloat1 > 0.0F)
    {
      if (paramFloat2 > paramFloat1)
      {
        if (paramFloat3 > 0.0F)
        {
          this.mAutoSizeTextType = 1;
          this.mAutoSizeMinTextSizeInPx = paramFloat1;
          this.mAutoSizeMaxTextSizeInPx = paramFloat2;
          this.mAutoSizeStepGranularityInPx = paramFloat3;
          this.mHasPresetAutoSizeValues = false;
          return;
        }
        throw new IllegalArgumentException("The auto-size step granularity (" + paramFloat3 + "px) is less or equal to (0px)");
      }
      throw new IllegalArgumentException("Maximum auto-size text size (" + paramFloat2 + "px) is less or equal to minimum auto-size text size (" + paramFloat1 + "px)");
    }
    throw new IllegalArgumentException("Minimum auto-size text size (" + paramFloat1 + "px) is less or equal to (0px)");
  }
  
  void autoSizeText()
  {
    if (!isAutoSizeEnabled()) {
      return;
    }
    if (this.mNeedsAutoSizeText)
    {
      if ((this.mTextView.getMeasuredHeight() > 0) && (this.mTextView.getMeasuredWidth() > 0))
      {
        int i;
        if (this.mImpl.isHorizontallyScrollable(this.mTextView)) {
          i = 1048576;
        } else {
          i = this.mTextView.getMeasuredWidth() - this.mTextView.getTotalPaddingLeft() - this.mTextView.getTotalPaddingRight();
        }
        int j = this.mTextView.getHeight() - this.mTextView.getCompoundPaddingBottom() - this.mTextView.getCompoundPaddingTop();
        if ((i > 0) && (j > 0)) {
          synchronized (TEMP_RECTF)
          {
            ???.setEmpty();
            ???.right = i;
            ???.bottom = j;
            float f = findLargestTextSizeWhichFits(???);
            if (f != this.mTextView.getTextSize()) {
              setTextSizeInternal(0, f);
            }
          }
        }
        return;
      }
      return;
    }
    this.mNeedsAutoSizeText = true;
  }
  
  StaticLayout createLayout(CharSequence paramCharSequence, Layout.Alignment paramAlignment, int paramInt1, int paramInt2)
  {
    if (Build.VERSION.SDK_INT >= 23) {
      return Api23Impl.createStaticLayoutForMeasuring(paramCharSequence, paramAlignment, paramInt1, paramInt2, this.mTextView, this.mTempTextPaint, this.mImpl);
    }
    if (Build.VERSION.SDK_INT >= 16) {
      return Api16Impl.createStaticLayoutForMeasuring(paramCharSequence, paramAlignment, paramInt1, this.mTextView, this.mTempTextPaint);
    }
    return createStaticLayoutForMeasuringPre16(paramCharSequence, paramAlignment, paramInt1);
  }
  
  int getAutoSizeMaxTextSize()
  {
    return Math.round(this.mAutoSizeMaxTextSizeInPx);
  }
  
  int getAutoSizeMinTextSize()
  {
    return Math.round(this.mAutoSizeMinTextSizeInPx);
  }
  
  int getAutoSizeStepGranularity()
  {
    return Math.round(this.mAutoSizeStepGranularityInPx);
  }
  
  int[] getAutoSizeTextAvailableSizes()
  {
    return this.mAutoSizeTextSizesInPx;
  }
  
  int getAutoSizeTextType()
  {
    return this.mAutoSizeTextType;
  }
  
  void initTempTextPaint(int paramInt)
  {
    TextPaint localTextPaint = this.mTempTextPaint;
    if (localTextPaint == null) {
      this.mTempTextPaint = new TextPaint();
    } else {
      localTextPaint.reset();
    }
    this.mTempTextPaint.set(this.mTextView.getPaint());
    this.mTempTextPaint.setTextSize(paramInt);
  }
  
  boolean isAutoSizeEnabled()
  {
    boolean bool;
    if ((supportsAutoSizeText()) && (this.mAutoSizeTextType != 0)) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  void loadFromAttributes(AttributeSet paramAttributeSet, int paramInt)
  {
    float f2 = -1.0F;
    float f3 = -1.0F;
    float f1 = -1.0F;
    TypedArray localTypedArray = this.mContext.obtainStyledAttributes(paramAttributeSet, R.styleable.AppCompatTextView, paramInt, 0);
    TextView localTextView = this.mTextView;
    ViewCompat.saveAttributeDataForStyleable(localTextView, localTextView.getContext(), R.styleable.AppCompatTextView, paramAttributeSet, localTypedArray, paramInt, 0);
    if (localTypedArray.hasValue(R.styleable.AppCompatTextView_autoSizeTextType)) {
      this.mAutoSizeTextType = localTypedArray.getInt(R.styleable.AppCompatTextView_autoSizeTextType, 0);
    }
    if (localTypedArray.hasValue(R.styleable.AppCompatTextView_autoSizeStepGranularity)) {
      f1 = localTypedArray.getDimension(R.styleable.AppCompatTextView_autoSizeStepGranularity, -1.0F);
    }
    if (localTypedArray.hasValue(R.styleable.AppCompatTextView_autoSizeMinTextSize)) {
      f2 = localTypedArray.getDimension(R.styleable.AppCompatTextView_autoSizeMinTextSize, -1.0F);
    }
    if (localTypedArray.hasValue(R.styleable.AppCompatTextView_autoSizeMaxTextSize)) {
      f3 = localTypedArray.getDimension(R.styleable.AppCompatTextView_autoSizeMaxTextSize, -1.0F);
    }
    if (localTypedArray.hasValue(R.styleable.AppCompatTextView_autoSizePresetSizes))
    {
      paramInt = localTypedArray.getResourceId(R.styleable.AppCompatTextView_autoSizePresetSizes, 0);
      if (paramInt > 0)
      {
        paramAttributeSet = localTypedArray.getResources().obtainTypedArray(paramInt);
        setupAutoSizeUniformPresetSizes(paramAttributeSet);
        paramAttributeSet.recycle();
      }
    }
    localTypedArray.recycle();
    if (supportsAutoSizeText())
    {
      if (this.mAutoSizeTextType == 1)
      {
        if (!this.mHasPresetAutoSizeValues)
        {
          paramAttributeSet = this.mContext.getResources().getDisplayMetrics();
          float f4 = f2;
          if (f2 == -1.0F) {
            f4 = TypedValue.applyDimension(2, 12.0F, paramAttributeSet);
          }
          f2 = f3;
          if (f3 == -1.0F) {
            f2 = TypedValue.applyDimension(2, 112.0F, paramAttributeSet);
          }
          f3 = f1;
          if (f1 == -1.0F) {
            f3 = 1.0F;
          }
          validateAndSetAutoSizeTextTypeUniformConfiguration(f4, f2, f3);
        }
        setupAutoSizeText();
      }
    }
    else {
      this.mAutoSizeTextType = 0;
    }
  }
  
  void setAutoSizeTextTypeUniformWithConfiguration(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
    throws IllegalArgumentException
  {
    if (supportsAutoSizeText())
    {
      DisplayMetrics localDisplayMetrics = this.mContext.getResources().getDisplayMetrics();
      validateAndSetAutoSizeTextTypeUniformConfiguration(TypedValue.applyDimension(paramInt4, paramInt1, localDisplayMetrics), TypedValue.applyDimension(paramInt4, paramInt2, localDisplayMetrics), TypedValue.applyDimension(paramInt4, paramInt3, localDisplayMetrics));
      if (setupAutoSizeText()) {
        autoSizeText();
      }
    }
  }
  
  void setAutoSizeTextTypeUniformWithPresetSizes(int[] paramArrayOfInt, int paramInt)
    throws IllegalArgumentException
  {
    if (supportsAutoSizeText())
    {
      int j = paramArrayOfInt.length;
      if (j > 0)
      {
        int[] arrayOfInt = new int[j];
        Object localObject;
        if (paramInt == 0)
        {
          localObject = Arrays.copyOf(paramArrayOfInt, j);
        }
        else
        {
          DisplayMetrics localDisplayMetrics = this.mContext.getResources().getDisplayMetrics();
          for (int i = 0;; i++)
          {
            localObject = arrayOfInt;
            if (i >= j) {
              break;
            }
            arrayOfInt[i] = Math.round(TypedValue.applyDimension(paramInt, paramArrayOfInt[i], localDisplayMetrics));
          }
        }
        this.mAutoSizeTextSizesInPx = cleanupAutoSizePresetSizes((int[])localObject);
        if (!setupAutoSizeUniformPresetSizesConfiguration())
        {
          localObject = new StringBuilder().append("None of the preset sizes is valid: ");
          paramArrayOfInt = Arrays.toString(paramArrayOfInt);
          Log5ECF72.a(paramArrayOfInt);
          LogE84000.a(paramArrayOfInt);
          Log229316.a(paramArrayOfInt);
          throw new IllegalArgumentException(paramArrayOfInt);
        }
      }
      else
      {
        this.mHasPresetAutoSizeValues = false;
      }
      if (setupAutoSizeText()) {
        autoSizeText();
      }
    }
  }
  
  void setAutoSizeTextTypeWithDefaults(int paramInt)
  {
    if (supportsAutoSizeText()) {
      switch (paramInt)
      {
      default: 
        throw new IllegalArgumentException("Unknown auto-size text type: " + paramInt);
      case 1: 
        DisplayMetrics localDisplayMetrics = this.mContext.getResources().getDisplayMetrics();
        validateAndSetAutoSizeTextTypeUniformConfiguration(TypedValue.applyDimension(2, 12.0F, localDisplayMetrics), TypedValue.applyDimension(2, 112.0F, localDisplayMetrics), 1.0F);
        if (setupAutoSizeText()) {
          autoSizeText();
        }
        break;
      case 0: 
        clearAutoSizeConfiguration();
      }
    }
  }
  
  void setTextSizeInternal(int paramInt, float paramFloat)
  {
    Object localObject = this.mContext;
    if (localObject == null) {
      localObject = Resources.getSystem();
    } else {
      localObject = ((Context)localObject).getResources();
    }
    setRawTextSize(TypedValue.applyDimension(paramInt, paramFloat, ((Resources)localObject).getDisplayMetrics()));
  }
  
  private static final class Api16Impl
  {
    static StaticLayout createStaticLayoutForMeasuring(CharSequence paramCharSequence, Layout.Alignment paramAlignment, int paramInt, TextView paramTextView, TextPaint paramTextPaint)
    {
      return new StaticLayout(paramCharSequence, paramTextPaint, paramInt, paramAlignment, paramTextView.getLineSpacingMultiplier(), paramTextView.getLineSpacingExtra(), paramTextView.getIncludeFontPadding());
    }
    
    static int getMaxLines(TextView paramTextView)
    {
      return paramTextView.getMaxLines();
    }
  }
  
  private static final class Api18Impl
  {
    static boolean isInLayout(View paramView)
    {
      return paramView.isInLayout();
    }
  }
  
  private static final class Api23Impl
  {
    static StaticLayout createStaticLayoutForMeasuring(CharSequence paramCharSequence, Layout.Alignment paramAlignment, int paramInt1, int paramInt2, TextView paramTextView, TextPaint paramTextPaint, AppCompatTextViewAutoSizeHelper.Impl paramImpl)
    {
      paramCharSequence = StaticLayout.Builder.obtain(paramCharSequence, 0, paramCharSequence.length(), paramTextPaint, paramInt1);
      paramAlignment = paramCharSequence.setAlignment(paramAlignment).setLineSpacing(paramTextView.getLineSpacingExtra(), paramTextView.getLineSpacingMultiplier()).setIncludePad(paramTextView.getIncludeFontPadding()).setBreakStrategy(paramTextView.getBreakStrategy()).setHyphenationFrequency(paramTextView.getHyphenationFrequency());
      if (paramInt2 == -1) {
        paramInt1 = Integer.MAX_VALUE;
      } else {
        paramInt1 = paramInt2;
      }
      paramAlignment.setMaxLines(paramInt1);
      try
      {
        paramImpl.computeAndSetTextDirection(paramCharSequence, paramTextView);
      }
      catch (ClassCastException paramAlignment)
      {
        Log.w("ACTVAutoSizeHelper", "Failed to obtain TextDirectionHeuristic, auto size may be incorrect");
      }
      return paramCharSequence.build();
    }
  }
  
  private static class Impl
  {
    void computeAndSetTextDirection(StaticLayout.Builder paramBuilder, TextView paramTextView) {}
    
    boolean isHorizontallyScrollable(TextView paramTextView)
    {
      return ((Boolean)AppCompatTextViewAutoSizeHelper.invokeAndReturnWithDefault(paramTextView, "getHorizontallyScrolling", Boolean.valueOf(false))).booleanValue();
    }
  }
  
  private static class Impl23
    extends AppCompatTextViewAutoSizeHelper.Impl
  {
    void computeAndSetTextDirection(StaticLayout.Builder paramBuilder, TextView paramTextView)
    {
      paramBuilder.setTextDirection((TextDirectionHeuristic)AppCompatTextViewAutoSizeHelper.invokeAndReturnWithDefault(paramTextView, "getTextDirectionHeuristic", TextDirectionHeuristics.FIRSTSTRONG_LTR));
    }
  }
  
  private static class Impl29
    extends AppCompatTextViewAutoSizeHelper.Impl23
  {
    void computeAndSetTextDirection(StaticLayout.Builder paramBuilder, TextView paramTextView)
    {
      paramBuilder.setTextDirection(paramTextView.getTextDirectionHeuristic());
    }
    
    boolean isHorizontallyScrollable(TextView paramTextView)
    {
      return paramTextView.isHorizontallyScrollable();
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/appcompat/widget/AppCompatTextViewAutoSizeHelper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */