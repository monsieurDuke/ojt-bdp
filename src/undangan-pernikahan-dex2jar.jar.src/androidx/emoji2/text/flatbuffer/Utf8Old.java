package androidx.emoji2.text.flatbuffer;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.CoderResult;
import java.nio.charset.StandardCharsets;

public class Utf8Old
  extends Utf8
{
  private static final ThreadLocal<Cache> CACHE = ThreadLocal.withInitial(new Utf8Old..ExternalSyntheticLambda0());
  
  public String decodeUtf8(ByteBuffer paramByteBuffer, int paramInt1, int paramInt2)
  {
    CharsetDecoder localCharsetDecoder = ((Cache)CACHE.get()).decoder;
    localCharsetDecoder.reset();
    paramByteBuffer = paramByteBuffer.duplicate();
    paramByteBuffer.position(paramInt1);
    paramByteBuffer.limit(paramInt1 + paramInt2);
    try
    {
      paramByteBuffer = localCharsetDecoder.decode(paramByteBuffer).toString();
      return paramByteBuffer;
    }
    catch (CharacterCodingException paramByteBuffer)
    {
      throw new IllegalArgumentException("Bad encoding", paramByteBuffer);
    }
  }
  
  public void encodeUtf8(CharSequence paramCharSequence, ByteBuffer paramByteBuffer)
  {
    Cache localCache = (Cache)CACHE.get();
    if (localCache.lastInput != paramCharSequence) {
      encodedLength(paramCharSequence);
    }
    paramByteBuffer.put(localCache.lastOutput);
  }
  
  public int encodedLength(CharSequence paramCharSequence)
  {
    Cache localCache = (Cache)CACHE.get();
    int i = (int)(paramCharSequence.length() * localCache.encoder.maxBytesPerChar());
    if ((localCache.lastOutput == null) || (localCache.lastOutput.capacity() < i)) {
      localCache.lastOutput = ByteBuffer.allocate(Math.max(128, i));
    }
    localCache.lastOutput.clear();
    localCache.lastInput = paramCharSequence;
    if ((paramCharSequence instanceof CharBuffer)) {
      paramCharSequence = (CharBuffer)paramCharSequence;
    } else {
      paramCharSequence = CharBuffer.wrap(paramCharSequence);
    }
    paramCharSequence = localCache.encoder.encode(paramCharSequence, localCache.lastOutput, true);
    if (paramCharSequence.isError()) {
      try
      {
        paramCharSequence.throwException();
      }
      catch (CharacterCodingException paramCharSequence)
      {
        throw new IllegalArgumentException("bad character encoding", paramCharSequence);
      }
    }
    localCache.lastOutput.flip();
    return localCache.lastOutput.remaining();
  }
  
  private static class Cache
  {
    final CharsetDecoder decoder = StandardCharsets.UTF_8.newDecoder();
    final CharsetEncoder encoder = StandardCharsets.UTF_8.newEncoder();
    CharSequence lastInput = null;
    ByteBuffer lastOutput = null;
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/androidx/emoji2/text/flatbuffer/Utf8Old.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */