package androidx.core.util;

import android.util.Log;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class AtomicFile
{
  private static final String LOG_TAG = "AtomicFile";
  private final File mBaseName;
  private final File mLegacyBackupName;
  private final File mNewName;
  
  public AtomicFile(File paramFile)
  {
    this.mBaseName = paramFile;
    this.mNewName = new File(paramFile.getPath() + ".new");
    this.mLegacyBackupName = new File(paramFile.getPath() + ".bak");
  }
  
  private static void rename(File paramFile1, File paramFile2)
  {
    if ((paramFile2.isDirectory()) && (!paramFile2.delete())) {
      Log.e("AtomicFile", "Failed to delete file which is a directory " + paramFile2);
    }
    if (!paramFile1.renameTo(paramFile2)) {
      Log.e("AtomicFile", "Failed to rename " + paramFile1 + " to " + paramFile2);
    }
  }
  
  private static boolean sync(FileOutputStream paramFileOutputStream)
  {
    try
    {
      paramFileOutputStream.getFD().sync();
      return true;
    }
    catch (IOException paramFileOutputStream) {}
    return false;
  }
  
  public void delete()
  {
    this.mBaseName.delete();
    this.mNewName.delete();
    this.mLegacyBackupName.delete();
  }
  
  public void failWrite(FileOutputStream paramFileOutputStream)
  {
    if (paramFileOutputStream == null) {
      return;
    }
    if (!sync(paramFileOutputStream)) {
      Log.e("AtomicFile", "Failed to sync file output stream");
    }
    try
    {
      paramFileOutputStream.close();
    }
    catch (IOException paramFileOutputStream)
    {
      Log.e("AtomicFile", "Failed to close file output stream", paramFileOutputStream);
    }
    if (!this.mNewName.delete()) {
      Log.e("AtomicFile", "Failed to delete new file " + this.mNewName);
    }
  }
  
  public void finishWrite(FileOutputStream paramFileOutputStream)
  {
    if (paramFileOutputStream == null) {
      return;
    }
    if (!sync(paramFileOutputStream)) {
      Log.e("AtomicFile", "Failed to sync file output stream");
    }
    try
    {
      paramFileOutputStream.close();
    }
    catch (IOException paramFileOutputStream)
    {
      Log.e("AtomicFile", "Failed to close file output stream", paramFileOutputStream);
    }
    rename(this.mNewName, this.mBaseName);
  }
  
  public File getBaseFile()
  {
    return this.mBaseName;
  }
  
  public FileInputStream openRead()
    throws FileNotFoundException
  {
    if (this.mLegacyBackupName.exists()) {
      rename(this.mLegacyBackupName, this.mBaseName);
    }
    if ((this.mNewName.exists()) && (this.mBaseName.exists()) && (!this.mNewName.delete())) {
      Log.e("AtomicFile", "Failed to delete outdated new file " + this.mNewName);
    }
    return new FileInputStream(this.mBaseName);
  }
  
  /* Error */
  public byte[] readFully()
    throws IOException
  {
    // Byte code:
    //   0: aload_0
    //   1: invokevirtual 128	androidx/core/util/AtomicFile:openRead	()Ljava/io/FileInputStream;
    //   4: astore 5
    //   6: iconst_0
    //   7: istore_1
    //   8: aload 5
    //   10: invokevirtual 132	java/io/FileInputStream:available	()I
    //   13: newarray <illegal type>
    //   15: astore_3
    //   16: aload 5
    //   18: aload_3
    //   19: iload_1
    //   20: aload_3
    //   21: arraylength
    //   22: iload_1
    //   23: isub
    //   24: invokevirtual 136	java/io/FileInputStream:read	([BII)I
    //   27: istore_2
    //   28: iload_2
    //   29: ifgt +10 -> 39
    //   32: aload 5
    //   34: invokevirtual 137	java/io/FileInputStream:close	()V
    //   37: aload_3
    //   38: areturn
    //   39: iload_1
    //   40: iload_2
    //   41: iadd
    //   42: istore_1
    //   43: aload 5
    //   45: invokevirtual 132	java/io/FileInputStream:available	()I
    //   48: istore_2
    //   49: aload_3
    //   50: astore 4
    //   52: iload_2
    //   53: aload_3
    //   54: arraylength
    //   55: iload_1
    //   56: isub
    //   57: if_icmple +19 -> 76
    //   60: iload_1
    //   61: iload_2
    //   62: iadd
    //   63: newarray <illegal type>
    //   65: astore 4
    //   67: aload_3
    //   68: iconst_0
    //   69: aload 4
    //   71: iconst_0
    //   72: iload_1
    //   73: invokestatic 143	java/lang/System:arraycopy	(Ljava/lang/Object;ILjava/lang/Object;II)V
    //   76: aload 4
    //   78: astore_3
    //   79: goto -63 -> 16
    //   82: astore_3
    //   83: aload 5
    //   85: invokevirtual 137	java/io/FileInputStream:close	()V
    //   88: aload_3
    //   89: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	90	0	this	AtomicFile
    //   7	66	1	i	int
    //   27	36	2	j	int
    //   15	64	3	localObject1	Object
    //   82	7	3	localObject2	Object
    //   50	27	4	localObject3	Object
    //   4	80	5	localFileInputStream	FileInputStream
    // Exception table:
    //   from	to	target	type
    //   8	16	82	finally
    //   16	28	82	finally
    //   43	49	82	finally
    //   52	76	82	finally
  }
  
  public FileOutputStream startWrite()
    throws IOException
  {
    if (this.mLegacyBackupName.exists()) {
      rename(this.mLegacyBackupName, this.mBaseName);
    }
    try
    {
      FileOutputStream localFileOutputStream1 = new FileOutputStream(this.mNewName);
      return localFileOutputStream1;
    }
    catch (FileNotFoundException localFileNotFoundException1)
    {
      if (this.mNewName.getParentFile().mkdirs()) {
        try
        {
          FileOutputStream localFileOutputStream2 = new FileOutputStream(this.mNewName);
          return localFileOutputStream2;
        }
        catch (FileNotFoundException localFileNotFoundException2)
        {
          throw new IOException("Failed to create new file " + this.mNewName, localFileNotFoundException2);
        }
      }
      throw new IOException("Failed to create directory for " + this.mNewName);
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/core/util/AtomicFile.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */