package androidx.print;

import android.content.ContentResolver;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.pdf.PdfDocument.Page;
import android.graphics.pdf.PdfDocument.PageInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.os.CancellationSignal.OnCancelListener;
import android.os.ParcelFileDescriptor;
import android.print.PageRange;
import android.print.PrintAttributes;
import android.print.PrintAttributes.Builder;
import android.print.PrintAttributes.Margins;
import android.print.PrintAttributes.MediaSize;
import android.print.PrintDocumentAdapter;
import android.print.PrintDocumentAdapter.LayoutResultCallback;
import android.print.PrintDocumentAdapter.WriteResultCallback;
import android.print.PrintDocumentInfo.Builder;
import android.print.PrintManager;
import android.print.pdf.PrintedPdfDocument;
import android.util.Log;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public final class PrintHelper
{
  public static final int COLOR_MODE_COLOR = 2;
  public static final int COLOR_MODE_MONOCHROME = 1;
  static final boolean IS_MIN_MARGINS_HANDLING_CORRECT;
  private static final String LOG_TAG = "PrintHelper";
  private static final int MAX_PRINT_SIZE = 3500;
  public static final int ORIENTATION_LANDSCAPE = 1;
  public static final int ORIENTATION_PORTRAIT = 2;
  static final boolean PRINT_ACTIVITY_RESPECTS_ORIENTATION;
  public static final int SCALE_MODE_FILL = 2;
  public static final int SCALE_MODE_FIT = 1;
  int mColorMode = 2;
  final Context mContext;
  BitmapFactory.Options mDecodeOptions = null;
  final Object mLock = new Object();
  int mOrientation = 1;
  int mScaleMode = 2;
  
  static
  {
    int i = Build.VERSION.SDK_INT;
    boolean bool2 = false;
    if ((i >= 20) && (Build.VERSION.SDK_INT <= 23)) {
      bool1 = false;
    } else {
      bool1 = true;
    }
    PRINT_ACTIVITY_RESPECTS_ORIENTATION = bool1;
    boolean bool1 = bool2;
    if (Build.VERSION.SDK_INT != 23) {
      bool1 = true;
    }
    IS_MIN_MARGINS_HANDLING_CORRECT = bool1;
  }
  
  public PrintHelper(Context paramContext)
  {
    this.mContext = paramContext;
  }
  
  static Bitmap convertBitmapForColorMode(Bitmap paramBitmap, int paramInt)
  {
    if (paramInt != 1) {
      return paramBitmap;
    }
    Bitmap localBitmap = Bitmap.createBitmap(paramBitmap.getWidth(), paramBitmap.getHeight(), Bitmap.Config.ARGB_8888);
    Canvas localCanvas = new Canvas(localBitmap);
    Paint localPaint = new Paint();
    ColorMatrix localColorMatrix = new ColorMatrix();
    localColorMatrix.setSaturation(0.0F);
    localPaint.setColorFilter(new ColorMatrixColorFilter(localColorMatrix));
    localCanvas.drawBitmap(paramBitmap, 0.0F, 0.0F, localPaint);
    localCanvas.setBitmap(null);
    return localBitmap;
  }
  
  private static PrintAttributes.Builder copyAttributes(PrintAttributes paramPrintAttributes)
  {
    PrintAttributes.Builder localBuilder = new PrintAttributes.Builder().setMediaSize(paramPrintAttributes.getMediaSize()).setResolution(paramPrintAttributes.getResolution()).setMinMargins(paramPrintAttributes.getMinMargins());
    if (paramPrintAttributes.getColorMode() != 0) {
      localBuilder.setColorMode(paramPrintAttributes.getColorMode());
    }
    if ((Build.VERSION.SDK_INT >= 23) && (paramPrintAttributes.getDuplexMode() != 0)) {
      localBuilder.setDuplexMode(paramPrintAttributes.getDuplexMode());
    }
    return localBuilder;
  }
  
  static Matrix getMatrix(int paramInt1, int paramInt2, RectF paramRectF, int paramInt3)
  {
    Matrix localMatrix = new Matrix();
    float f = paramRectF.width() / paramInt1;
    if (paramInt3 == 2) {
      f = Math.max(f, paramRectF.height() / paramInt2);
    } else {
      f = Math.min(f, paramRectF.height() / paramInt2);
    }
    localMatrix.postScale(f, f);
    localMatrix.postTranslate((paramRectF.width() - paramInt1 * f) / 2.0F, (paramRectF.height() - paramInt2 * f) / 2.0F);
    return localMatrix;
  }
  
  static boolean isPortrait(Bitmap paramBitmap)
  {
    boolean bool;
    if (paramBitmap.getWidth() <= paramBitmap.getHeight()) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  private Bitmap loadBitmap(Uri paramUri, BitmapFactory.Options paramOptions)
    throws FileNotFoundException
  {
    if (paramUri != null)
    {
      Context localContext = this.mContext;
      if (localContext != null)
      {
        Uri localUri = null;
        try
        {
          paramUri = localContext.getContentResolver().openInputStream(paramUri);
          localUri = paramUri;
          paramOptions = BitmapFactory.decodeStream(paramUri, null, paramOptions);
          return paramOptions;
        }
        finally
        {
          if (localUri != null) {
            try
            {
              localUri.close();
            }
            catch (IOException paramOptions)
            {
              Log.w("PrintHelper", "close fail ", paramOptions);
            }
          }
        }
      }
    }
    throw new IllegalArgumentException("bad argument to loadBitmap");
  }
  
  public static boolean systemSupportsPrint()
  {
    boolean bool;
    if (Build.VERSION.SDK_INT >= 19) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public int getColorMode()
  {
    return this.mColorMode;
  }
  
  public int getOrientation()
  {
    if ((Build.VERSION.SDK_INT >= 19) && (this.mOrientation == 0)) {
      return 1;
    }
    return this.mOrientation;
  }
  
  public int getScaleMode()
  {
    return this.mScaleMode;
  }
  
  /* Error */
  Bitmap loadConstrainedBitmap(Uri arg1)
    throws FileNotFoundException
  {
    // Byte code:
    //   0: aload_1
    //   1: ifnull +228 -> 229
    //   4: aload_0
    //   5: getfield 74	androidx/print/PrintHelper:mContext	Landroid/content/Context;
    //   8: ifnull +221 -> 229
    //   11: new 255	android/graphics/BitmapFactory$Options
    //   14: dup
    //   15: invokespecial 256	android/graphics/BitmapFactory$Options:<init>	()V
    //   18: astore 6
    //   20: aload 6
    //   22: iconst_1
    //   23: putfield 259	android/graphics/BitmapFactory$Options:inJustDecodeBounds	Z
    //   26: aload_0
    //   27: aload_1
    //   28: aload 6
    //   30: invokespecial 261	androidx/print/PrintHelper:loadBitmap	(Landroid/net/Uri;Landroid/graphics/BitmapFactory$Options;)Landroid/graphics/Bitmap;
    //   33: pop
    //   34: aload 6
    //   36: getfield 264	android/graphics/BitmapFactory$Options:outWidth	I
    //   39: istore 4
    //   41: aload 6
    //   43: getfield 267	android/graphics/BitmapFactory$Options:outHeight	I
    //   46: istore 5
    //   48: iload 4
    //   50: ifle +177 -> 227
    //   53: iload 5
    //   55: ifgt +6 -> 61
    //   58: goto +169 -> 227
    //   61: iload 4
    //   63: iload 5
    //   65: invokestatic 270	java/lang/Math:max	(II)I
    //   68: istore_3
    //   69: iconst_1
    //   70: istore_2
    //   71: iload_3
    //   72: sipush 3500
    //   75: if_icmple +14 -> 89
    //   78: iload_3
    //   79: iconst_1
    //   80: iushr
    //   81: istore_3
    //   82: iload_2
    //   83: iconst_1
    //   84: ishl
    //   85: istore_2
    //   86: goto -15 -> 71
    //   89: iload_2
    //   90: ifle +135 -> 225
    //   93: iload 4
    //   95: iload 5
    //   97: invokestatic 272	java/lang/Math:min	(II)I
    //   100: iload_2
    //   101: idiv
    //   102: ifgt +6 -> 108
    //   105: goto +120 -> 225
    //   108: aload_0
    //   109: getfield 66	androidx/print/PrintHelper:mLock	Ljava/lang/Object;
    //   112: astore 6
    //   114: aload 6
    //   116: monitorenter
    //   117: new 255	android/graphics/BitmapFactory$Options
    //   120: astore 7
    //   122: aload 7
    //   124: invokespecial 256	android/graphics/BitmapFactory$Options:<init>	()V
    //   127: aload_0
    //   128: aload 7
    //   130: putfield 64	androidx/print/PrintHelper:mDecodeOptions	Landroid/graphics/BitmapFactory$Options;
    //   133: aload 7
    //   135: iconst_1
    //   136: putfield 275	android/graphics/BitmapFactory$Options:inMutable	Z
    //   139: aload_0
    //   140: getfield 64	androidx/print/PrintHelper:mDecodeOptions	Landroid/graphics/BitmapFactory$Options;
    //   143: iload_2
    //   144: putfield 278	android/graphics/BitmapFactory$Options:inSampleSize	I
    //   147: aload_0
    //   148: getfield 64	androidx/print/PrintHelper:mDecodeOptions	Landroid/graphics/BitmapFactory$Options;
    //   151: astore 7
    //   153: aload 6
    //   155: monitorexit
    //   156: aload_0
    //   157: aload_1
    //   158: aload 7
    //   160: invokespecial 261	androidx/print/PrintHelper:loadBitmap	(Landroid/net/Uri;Landroid/graphics/BitmapFactory$Options;)Landroid/graphics/Bitmap;
    //   163: astore 6
    //   165: aload_0
    //   166: getfield 66	androidx/print/PrintHelper:mLock	Ljava/lang/Object;
    //   169: astore_1
    //   170: aload_1
    //   171: monitorenter
    //   172: aload_0
    //   173: aconst_null
    //   174: putfield 64	androidx/print/PrintHelper:mDecodeOptions	Landroid/graphics/BitmapFactory$Options;
    //   177: aload_1
    //   178: monitorexit
    //   179: aload 6
    //   181: areturn
    //   182: astore 6
    //   184: aload_1
    //   185: monitorexit
    //   186: aload 6
    //   188: athrow
    //   189: astore 6
    //   191: aload_0
    //   192: getfield 66	androidx/print/PrintHelper:mLock	Ljava/lang/Object;
    //   195: astore_1
    //   196: aload_1
    //   197: monitorenter
    //   198: aload_0
    //   199: aconst_null
    //   200: putfield 64	androidx/print/PrintHelper:mDecodeOptions	Landroid/graphics/BitmapFactory$Options;
    //   203: aload_1
    //   204: monitorexit
    //   205: aload 6
    //   207: athrow
    //   208: astore 6
    //   210: aload_1
    //   211: monitorexit
    //   212: aload 6
    //   214: athrow
    //   215: astore_1
    //   216: aload 6
    //   218: monitorexit
    //   219: aload_1
    //   220: athrow
    //   221: astore_1
    //   222: goto -6 -> 216
    //   225: aconst_null
    //   226: areturn
    //   227: aconst_null
    //   228: areturn
    //   229: new 241	java/lang/IllegalArgumentException
    //   232: dup
    //   233: ldc_w 280
    //   236: invokespecial 246	java/lang/IllegalArgumentException:<init>	(Ljava/lang/String;)V
    //   239: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	240	0	this	PrintHelper
    //   70	74	2	i	int
    //   68	14	3	j	int
    //   39	55	4	k	int
    //   46	50	5	m	int
    //   18	162	6	localObject1	Object
    //   182	5	6	localObject2	Object
    //   189	17	6	localObject3	Object
    //   208	9	6	localObject4	Object
    //   120	39	7	localOptions	BitmapFactory.Options
    // Exception table:
    //   from	to	target	type
    //   172	179	182	finally
    //   184	186	182	finally
    //   156	165	189	finally
    //   198	205	208	finally
    //   210	212	208	finally
    //   117	153	215	finally
    //   153	156	221	finally
    //   216	219	221	finally
  }
  
  public void printBitmap(String paramString, Bitmap paramBitmap)
  {
    printBitmap(paramString, paramBitmap, null);
  }
  
  public void printBitmap(String paramString, Bitmap paramBitmap, OnPrintFinishCallback paramOnPrintFinishCallback)
  {
    if ((Build.VERSION.SDK_INT >= 19) && (paramBitmap != null))
    {
      PrintManager localPrintManager = (PrintManager)this.mContext.getSystemService("print");
      if (isPortrait(paramBitmap)) {
        localObject = PrintAttributes.MediaSize.UNKNOWN_PORTRAIT;
      } else {
        localObject = PrintAttributes.MediaSize.UNKNOWN_LANDSCAPE;
      }
      Object localObject = new PrintAttributes.Builder().setMediaSize((PrintAttributes.MediaSize)localObject).setColorMode(this.mColorMode).build();
      localPrintManager.print(paramString, new PrintBitmapAdapter(paramString, this.mScaleMode, paramBitmap, paramOnPrintFinishCallback), (PrintAttributes)localObject);
      return;
    }
  }
  
  public void printBitmap(String paramString, Uri paramUri)
    throws FileNotFoundException
  {
    printBitmap(paramString, paramUri, null);
  }
  
  public void printBitmap(String paramString, Uri paramUri, OnPrintFinishCallback paramOnPrintFinishCallback)
    throws FileNotFoundException
  {
    if (Build.VERSION.SDK_INT < 19) {
      return;
    }
    paramUri = new PrintUriAdapter(paramString, paramUri, paramOnPrintFinishCallback, this.mScaleMode);
    paramOnPrintFinishCallback = (PrintManager)this.mContext.getSystemService("print");
    PrintAttributes.Builder localBuilder = new PrintAttributes.Builder();
    localBuilder.setColorMode(this.mColorMode);
    int i = this.mOrientation;
    if ((i != 1) && (i != 0))
    {
      if (i == 2) {
        localBuilder.setMediaSize(PrintAttributes.MediaSize.UNKNOWN_PORTRAIT);
      }
    }
    else {
      localBuilder.setMediaSize(PrintAttributes.MediaSize.UNKNOWN_LANDSCAPE);
    }
    paramOnPrintFinishCallback.print(paramString, paramUri, localBuilder.build());
  }
  
  public void setColorMode(int paramInt)
  {
    this.mColorMode = paramInt;
  }
  
  public void setOrientation(int paramInt)
  {
    this.mOrientation = paramInt;
  }
  
  public void setScaleMode(int paramInt)
  {
    this.mScaleMode = paramInt;
  }
  
  void writeBitmap(final PrintAttributes paramPrintAttributes, final int paramInt, final Bitmap paramBitmap, final ParcelFileDescriptor paramParcelFileDescriptor, final CancellationSignal paramCancellationSignal, final PrintDocumentAdapter.WriteResultCallback paramWriteResultCallback)
  {
    final PrintAttributes localPrintAttributes;
    if (IS_MIN_MARGINS_HANDLING_CORRECT) {
      localPrintAttributes = paramPrintAttributes;
    } else {
      localPrintAttributes = copyAttributes(paramPrintAttributes).setMinMargins(new PrintAttributes.Margins(0, 0, 0, 0)).build();
    }
    new AsyncTask()
    {
      protected Throwable doInBackground(Void... paramAnonymousVarArgs)
      {
        try
        {
          if (paramCancellationSignal.isCanceled()) {
            return null;
          }
          Object localObject1 = new android/print/pdf/PrintedPdfDocument;
          ((PrintedPdfDocument)localObject1).<init>(PrintHelper.this.mContext, localPrintAttributes);
          Bitmap localBitmap = PrintHelper.convertBitmapForColorMode(paramBitmap, localPrintAttributes.getColorMode());
          boolean bool = paramCancellationSignal.isCanceled();
          if (bool) {
            return null;
          }
          try
          {
            PdfDocument.Page localPage = ((PrintedPdfDocument)localObject1).startPage(1);
            if (PrintHelper.IS_MIN_MARGINS_HANDLING_CORRECT)
            {
              paramAnonymousVarArgs = new android/graphics/RectF;
              paramAnonymousVarArgs.<init>(localPage.getInfo().getContentRect());
            }
            else
            {
              PrintedPdfDocument localPrintedPdfDocument = new android/print/pdf/PrintedPdfDocument;
              localPrintedPdfDocument.<init>(PrintHelper.this.mContext, paramPrintAttributes);
              localObject2 = localPrintedPdfDocument.startPage(1);
              paramAnonymousVarArgs = new android/graphics/RectF;
              paramAnonymousVarArgs.<init>(((PdfDocument.Page)localObject2).getInfo().getContentRect());
              localPrintedPdfDocument.finishPage((PdfDocument.Page)localObject2);
              localPrintedPdfDocument.close();
            }
            Object localObject2 = PrintHelper.getMatrix(localBitmap.getWidth(), localBitmap.getHeight(), paramAnonymousVarArgs, paramInt);
            if (!PrintHelper.IS_MIN_MARGINS_HANDLING_CORRECT)
            {
              ((Matrix)localObject2).postTranslate(paramAnonymousVarArgs.left, paramAnonymousVarArgs.top);
              localPage.getCanvas().clipRect(paramAnonymousVarArgs);
            }
            localPage.getCanvas().drawBitmap(localBitmap, (Matrix)localObject2, null);
            ((PrintedPdfDocument)localObject1).finishPage(localPage);
            bool = paramCancellationSignal.isCanceled();
            if (bool)
            {
              ((PrintedPdfDocument)localObject1).close();
              paramAnonymousVarArgs = paramParcelFileDescriptor;
              if (paramAnonymousVarArgs != null) {
                try
                {
                  paramAnonymousVarArgs.close();
                }
                catch (IOException paramAnonymousVarArgs) {}
              }
              if (localBitmap != paramBitmap) {
                localBitmap.recycle();
              }
              return null;
            }
            paramAnonymousVarArgs = new java/io/FileOutputStream;
            paramAnonymousVarArgs.<init>(paramParcelFileDescriptor.getFileDescriptor());
            ((PrintedPdfDocument)localObject1).writeTo(paramAnonymousVarArgs);
            ((PrintedPdfDocument)localObject1).close();
            paramAnonymousVarArgs = paramParcelFileDescriptor;
            if (paramAnonymousVarArgs != null) {
              try
              {
                paramAnonymousVarArgs.close();
              }
              catch (IOException paramAnonymousVarArgs) {}
            }
            if (localBitmap != paramBitmap) {
              localBitmap.recycle();
            }
            return null;
          }
          finally
          {
            ((PrintedPdfDocument)localObject1).close();
            localObject1 = paramParcelFileDescriptor;
            if (localObject1 != null) {
              try
              {
                ((ParcelFileDescriptor)localObject1).close();
              }
              catch (IOException localIOException) {}
            }
            if (localBitmap != paramBitmap) {
              localBitmap.recycle();
            }
          }
          return paramAnonymousVarArgs;
        }
        finally {}
      }
      
      protected void onPostExecute(Throwable paramAnonymousThrowable)
      {
        if (paramCancellationSignal.isCanceled())
        {
          paramWriteResultCallback.onWriteCancelled();
        }
        else if (paramAnonymousThrowable == null)
        {
          paramWriteResultCallback.onWriteFinished(new PageRange[] { PageRange.ALL_PAGES });
        }
        else
        {
          Log.e("PrintHelper", "Error writing printed content", paramAnonymousThrowable);
          paramWriteResultCallback.onWriteFailed(null);
        }
      }
    }.execute(new Void[0]);
  }
  
  public static abstract interface OnPrintFinishCallback
  {
    public abstract void onFinish();
  }
  
  private class PrintBitmapAdapter
    extends PrintDocumentAdapter
  {
    private PrintAttributes mAttributes;
    private final Bitmap mBitmap;
    private final PrintHelper.OnPrintFinishCallback mCallback;
    private final int mFittingMode;
    private final String mJobName;
    
    PrintBitmapAdapter(String paramString, int paramInt, Bitmap paramBitmap, PrintHelper.OnPrintFinishCallback paramOnPrintFinishCallback)
    {
      this.mJobName = paramString;
      this.mFittingMode = paramInt;
      this.mBitmap = paramBitmap;
      this.mCallback = paramOnPrintFinishCallback;
    }
    
    public void onFinish()
    {
      PrintHelper.OnPrintFinishCallback localOnPrintFinishCallback = this.mCallback;
      if (localOnPrintFinishCallback != null) {
        localOnPrintFinishCallback.onFinish();
      }
    }
    
    public void onLayout(PrintAttributes paramPrintAttributes1, PrintAttributes paramPrintAttributes2, CancellationSignal paramCancellationSignal, PrintDocumentAdapter.LayoutResultCallback paramLayoutResultCallback, Bundle paramBundle)
    {
      this.mAttributes = paramPrintAttributes2;
      paramLayoutResultCallback.onLayoutFinished(new PrintDocumentInfo.Builder(this.mJobName).setContentType(1).setPageCount(1).build(), true ^ paramPrintAttributes2.equals(paramPrintAttributes1));
    }
    
    public void onWrite(PageRange[] paramArrayOfPageRange, ParcelFileDescriptor paramParcelFileDescriptor, CancellationSignal paramCancellationSignal, PrintDocumentAdapter.WriteResultCallback paramWriteResultCallback)
    {
      PrintHelper.this.writeBitmap(this.mAttributes, this.mFittingMode, this.mBitmap, paramParcelFileDescriptor, paramCancellationSignal, paramWriteResultCallback);
    }
  }
  
  private class PrintUriAdapter
    extends PrintDocumentAdapter
  {
    PrintAttributes mAttributes;
    Bitmap mBitmap;
    final PrintHelper.OnPrintFinishCallback mCallback;
    final int mFittingMode;
    final Uri mImageFile;
    final String mJobName;
    AsyncTask<Uri, Boolean, Bitmap> mLoadBitmap;
    
    PrintUriAdapter(String paramString, Uri paramUri, PrintHelper.OnPrintFinishCallback paramOnPrintFinishCallback, int paramInt)
    {
      this.mJobName = paramString;
      this.mImageFile = paramUri;
      this.mCallback = paramOnPrintFinishCallback;
      this.mFittingMode = paramInt;
      this.mBitmap = null;
    }
    
    void cancelLoad()
    {
      synchronized (PrintHelper.this.mLock)
      {
        if (PrintHelper.this.mDecodeOptions != null)
        {
          if (Build.VERSION.SDK_INT < 24) {
            PrintHelper.this.mDecodeOptions.requestCancelDecode();
          }
          PrintHelper.this.mDecodeOptions = null;
        }
        return;
      }
    }
    
    public void onFinish()
    {
      super.onFinish();
      cancelLoad();
      Object localObject = this.mLoadBitmap;
      if (localObject != null) {
        ((AsyncTask)localObject).cancel(true);
      }
      localObject = this.mCallback;
      if (localObject != null) {
        ((PrintHelper.OnPrintFinishCallback)localObject).onFinish();
      }
      localObject = this.mBitmap;
      if (localObject != null)
      {
        ((Bitmap)localObject).recycle();
        this.mBitmap = null;
      }
    }
    
    public void onLayout(final PrintAttributes paramPrintAttributes1, final PrintAttributes paramPrintAttributes2, final CancellationSignal paramCancellationSignal, final PrintDocumentAdapter.LayoutResultCallback paramLayoutResultCallback, Bundle paramBundle)
    {
      try
      {
        this.mAttributes = paramPrintAttributes2;
        if (paramCancellationSignal.isCanceled())
        {
          paramLayoutResultCallback.onLayoutCancelled();
          return;
        }
        if (this.mBitmap != null)
        {
          paramLayoutResultCallback.onLayoutFinished(new PrintDocumentInfo.Builder(this.mJobName).setContentType(1).setPageCount(1).build(), true ^ paramPrintAttributes2.equals(paramPrintAttributes1));
          return;
        }
        this.mLoadBitmap = new AsyncTask()
        {
          protected Bitmap doInBackground(Uri... paramAnonymousVarArgs)
          {
            try
            {
              paramAnonymousVarArgs = PrintHelper.this.loadConstrainedBitmap(PrintHelper.PrintUriAdapter.this.mImageFile);
              return paramAnonymousVarArgs;
            }
            catch (FileNotFoundException paramAnonymousVarArgs) {}
            return null;
          }
          
          protected void onCancelled(Bitmap paramAnonymousBitmap)
          {
            paramLayoutResultCallback.onLayoutCancelled();
            PrintHelper.PrintUriAdapter.this.mLoadBitmap = null;
          }
          
          protected void onPostExecute(Bitmap paramAnonymousBitmap)
          {
            super.onPostExecute(paramAnonymousBitmap);
            Object localObject = paramAnonymousBitmap;
            if (paramAnonymousBitmap != null) {
              if (PrintHelper.PRINT_ACTIVITY_RESPECTS_ORIENTATION)
              {
                localObject = paramAnonymousBitmap;
                if (PrintHelper.this.mOrientation != 0) {}
              }
              else
              {
                try
                {
                  PrintAttributes.MediaSize localMediaSize = PrintHelper.PrintUriAdapter.this.mAttributes.getMediaSize();
                  localObject = paramAnonymousBitmap;
                  if (localMediaSize != null)
                  {
                    localObject = paramAnonymousBitmap;
                    if (localMediaSize.isPortrait() != PrintHelper.isPortrait(paramAnonymousBitmap))
                    {
                      localObject = new Matrix();
                      ((Matrix)localObject).postRotate(90.0F);
                      localObject = Bitmap.createBitmap(paramAnonymousBitmap, 0, 0, paramAnonymousBitmap.getWidth(), paramAnonymousBitmap.getHeight(), (Matrix)localObject, true);
                    }
                  }
                }
                finally {}
              }
            }
            PrintHelper.PrintUriAdapter.this.mBitmap = ((Bitmap)localObject);
            if (localObject != null)
            {
              paramAnonymousBitmap = new PrintDocumentInfo.Builder(PrintHelper.PrintUriAdapter.this.mJobName).setContentType(1).setPageCount(1).build();
              boolean bool = paramPrintAttributes2.equals(paramPrintAttributes1);
              paramLayoutResultCallback.onLayoutFinished(paramAnonymousBitmap, true ^ bool);
            }
            else
            {
              paramLayoutResultCallback.onLayoutFailed(null);
            }
            PrintHelper.PrintUriAdapter.this.mLoadBitmap = null;
          }
          
          protected void onPreExecute()
          {
            paramCancellationSignal.setOnCancelListener(new CancellationSignal.OnCancelListener()
            {
              public void onCancel()
              {
                PrintHelper.PrintUriAdapter.this.cancelLoad();
                PrintHelper.PrintUriAdapter.1.this.cancel(false);
              }
            });
          }
        }.execute(new Uri[0]);
        return;
      }
      finally {}
    }
    
    public void onWrite(PageRange[] paramArrayOfPageRange, ParcelFileDescriptor paramParcelFileDescriptor, CancellationSignal paramCancellationSignal, PrintDocumentAdapter.WriteResultCallback paramWriteResultCallback)
    {
      PrintHelper.this.writeBitmap(this.mAttributes, this.mFittingMode, this.mBitmap, paramParcelFileDescriptor, paramCancellationSignal, paramWriteResultCallback);
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/print/PrintHelper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */