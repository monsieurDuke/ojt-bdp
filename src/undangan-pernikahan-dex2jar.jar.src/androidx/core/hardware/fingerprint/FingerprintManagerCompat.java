package androidx.core.hardware.fingerprint;

import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.fingerprint.FingerprintManager;
import android.hardware.fingerprint.FingerprintManager.AuthenticationCallback;
import android.hardware.fingerprint.FingerprintManager.AuthenticationResult;
import android.hardware.fingerprint.FingerprintManager.CryptoObject;
import android.os.Build.VERSION;
import android.os.Handler;
import java.security.Signature;
import javax.crypto.Cipher;
import javax.crypto.Mac;

@Deprecated
public class FingerprintManagerCompat
{
  private final Context mContext;
  
  private FingerprintManagerCompat(Context paramContext)
  {
    this.mContext = paramContext;
  }
  
  public static FingerprintManagerCompat from(Context paramContext)
  {
    return new FingerprintManagerCompat(paramContext);
  }
  
  private static FingerprintManager getFingerprintManagerOrNull(Context paramContext)
  {
    return Api23Impl.getFingerprintManagerOrNull(paramContext);
  }
  
  static CryptoObject unwrapCryptoObject(FingerprintManager.CryptoObject paramCryptoObject)
  {
    return Api23Impl.unwrapCryptoObject(paramCryptoObject);
  }
  
  private static FingerprintManager.AuthenticationCallback wrapCallback(AuthenticationCallback paramAuthenticationCallback)
  {
    new FingerprintManager.AuthenticationCallback()
    {
      public void onAuthenticationError(int paramAnonymousInt, CharSequence paramAnonymousCharSequence)
      {
        FingerprintManagerCompat.this.onAuthenticationError(paramAnonymousInt, paramAnonymousCharSequence);
      }
      
      public void onAuthenticationFailed()
      {
        FingerprintManagerCompat.this.onAuthenticationFailed();
      }
      
      public void onAuthenticationHelp(int paramAnonymousInt, CharSequence paramAnonymousCharSequence)
      {
        FingerprintManagerCompat.this.onAuthenticationHelp(paramAnonymousInt, paramAnonymousCharSequence);
      }
      
      public void onAuthenticationSucceeded(FingerprintManager.AuthenticationResult paramAnonymousAuthenticationResult)
      {
        FingerprintManagerCompat.this.onAuthenticationSucceeded(new FingerprintManagerCompat.AuthenticationResult(FingerprintManagerCompat.unwrapCryptoObject(FingerprintManagerCompat.Api23Impl.getCryptoObject(paramAnonymousAuthenticationResult))));
      }
    };
  }
  
  private static FingerprintManager.CryptoObject wrapCryptoObject(CryptoObject paramCryptoObject)
  {
    return Api23Impl.wrapCryptoObject(paramCryptoObject);
  }
  
  public void authenticate(CryptoObject paramCryptoObject, int paramInt, androidx.core.os.CancellationSignal paramCancellationSignal, AuthenticationCallback paramAuthenticationCallback, Handler paramHandler)
  {
    if (Build.VERSION.SDK_INT >= 23)
    {
      FingerprintManager localFingerprintManager = getFingerprintManagerOrNull(this.mContext);
      if (localFingerprintManager != null)
      {
        if (paramCancellationSignal != null) {
          paramCancellationSignal = (android.os.CancellationSignal)paramCancellationSignal.getCancellationSignalObject();
        } else {
          paramCancellationSignal = null;
        }
        Api23Impl.authenticate(localFingerprintManager, wrapCryptoObject(paramCryptoObject), paramCancellationSignal, paramInt, wrapCallback(paramAuthenticationCallback), paramHandler);
      }
    }
  }
  
  public boolean hasEnrolledFingerprints()
  {
    int i = Build.VERSION.SDK_INT;
    boolean bool2 = false;
    if (i >= 23)
    {
      FingerprintManager localFingerprintManager = getFingerprintManagerOrNull(this.mContext);
      boolean bool1 = bool2;
      if (localFingerprintManager != null)
      {
        bool1 = bool2;
        if (Api23Impl.hasEnrolledFingerprints(localFingerprintManager)) {
          bool1 = true;
        }
      }
      return bool1;
    }
    return false;
  }
  
  public boolean isHardwareDetected()
  {
    int i = Build.VERSION.SDK_INT;
    boolean bool2 = false;
    if (i >= 23)
    {
      FingerprintManager localFingerprintManager = getFingerprintManagerOrNull(this.mContext);
      boolean bool1 = bool2;
      if (localFingerprintManager != null)
      {
        bool1 = bool2;
        if (Api23Impl.isHardwareDetected(localFingerprintManager)) {
          bool1 = true;
        }
      }
      return bool1;
    }
    return false;
  }
  
  static class Api23Impl
  {
    static void authenticate(Object paramObject1, Object paramObject2, android.os.CancellationSignal paramCancellationSignal, int paramInt, Object paramObject3, Handler paramHandler)
    {
      ((FingerprintManager)paramObject1).authenticate((FingerprintManager.CryptoObject)paramObject2, paramCancellationSignal, paramInt, (FingerprintManager.AuthenticationCallback)paramObject3, paramHandler);
    }
    
    static FingerprintManager.CryptoObject getCryptoObject(Object paramObject)
    {
      return ((FingerprintManager.AuthenticationResult)paramObject).getCryptoObject();
    }
    
    public static FingerprintManager getFingerprintManagerOrNull(Context paramContext)
    {
      if (Build.VERSION.SDK_INT == 23) {
        return (FingerprintManager)paramContext.getSystemService(FingerprintManager.class);
      }
      if ((Build.VERSION.SDK_INT > 23) && (paramContext.getPackageManager().hasSystemFeature("android.hardware.fingerprint"))) {
        return (FingerprintManager)paramContext.getSystemService(FingerprintManager.class);
      }
      return null;
    }
    
    static boolean hasEnrolledFingerprints(Object paramObject)
    {
      return ((FingerprintManager)paramObject).hasEnrolledFingerprints();
    }
    
    static boolean isHardwareDetected(Object paramObject)
    {
      return ((FingerprintManager)paramObject).isHardwareDetected();
    }
    
    public static FingerprintManagerCompat.CryptoObject unwrapCryptoObject(Object paramObject)
    {
      paramObject = (FingerprintManager.CryptoObject)paramObject;
      if (paramObject == null) {
        return null;
      }
      if (((FingerprintManager.CryptoObject)paramObject).getCipher() != null) {
        return new FingerprintManagerCompat.CryptoObject(((FingerprintManager.CryptoObject)paramObject).getCipher());
      }
      if (((FingerprintManager.CryptoObject)paramObject).getSignature() != null) {
        return new FingerprintManagerCompat.CryptoObject(((FingerprintManager.CryptoObject)paramObject).getSignature());
      }
      if (((FingerprintManager.CryptoObject)paramObject).getMac() != null) {
        return new FingerprintManagerCompat.CryptoObject(((FingerprintManager.CryptoObject)paramObject).getMac());
      }
      return null;
    }
    
    public static FingerprintManager.CryptoObject wrapCryptoObject(FingerprintManagerCompat.CryptoObject paramCryptoObject)
    {
      if (paramCryptoObject == null) {
        return null;
      }
      if (paramCryptoObject.getCipher() != null) {
        return new FingerprintManager.CryptoObject(paramCryptoObject.getCipher());
      }
      if (paramCryptoObject.getSignature() != null) {
        return new FingerprintManager.CryptoObject(paramCryptoObject.getSignature());
      }
      if (paramCryptoObject.getMac() != null) {
        return new FingerprintManager.CryptoObject(paramCryptoObject.getMac());
      }
      return null;
    }
  }
  
  public static abstract class AuthenticationCallback
  {
    public void onAuthenticationError(int paramInt, CharSequence paramCharSequence) {}
    
    public void onAuthenticationFailed() {}
    
    public void onAuthenticationHelp(int paramInt, CharSequence paramCharSequence) {}
    
    public void onAuthenticationSucceeded(FingerprintManagerCompat.AuthenticationResult paramAuthenticationResult) {}
  }
  
  public static final class AuthenticationResult
  {
    private final FingerprintManagerCompat.CryptoObject mCryptoObject;
    
    public AuthenticationResult(FingerprintManagerCompat.CryptoObject paramCryptoObject)
    {
      this.mCryptoObject = paramCryptoObject;
    }
    
    public FingerprintManagerCompat.CryptoObject getCryptoObject()
    {
      return this.mCryptoObject;
    }
  }
  
  public static class CryptoObject
  {
    private final Cipher mCipher;
    private final Mac mMac;
    private final Signature mSignature;
    
    public CryptoObject(Signature paramSignature)
    {
      this.mSignature = paramSignature;
      this.mCipher = null;
      this.mMac = null;
    }
    
    public CryptoObject(Cipher paramCipher)
    {
      this.mCipher = paramCipher;
      this.mSignature = null;
      this.mMac = null;
    }
    
    public CryptoObject(Mac paramMac)
    {
      this.mMac = paramMac;
      this.mCipher = null;
      this.mSignature = null;
    }
    
    public Cipher getCipher()
    {
      return this.mCipher;
    }
    
    public Mac getMac()
    {
      return this.mMac;
    }
    
    public Signature getSignature()
    {
      return this.mSignature;
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/core/hardware/fingerprint/FingerprintManagerCompat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */