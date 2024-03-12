package okhttp3.internal.ws;

import kotlin.Metadata;
import okio.ByteString;
import okio.ByteString.Companion;

@Metadata(bv={1, 0, 3}, d1={"\000\016\n\000\n\002\030\002\n\000\n\002\020\b\n\000\"\016\020\000\032\0020\001X\004¢\006\002\n\000\"\016\020\002\032\0020\003XT¢\006\002\n\000¨\006\004"}, d2={"EMPTY_DEFLATE_BLOCK", "Lokio/ByteString;", "LAST_OCTETS_COUNT_TO_REMOVE_AFTER_DEFLATION", "", "okhttp"}, k=2, mv={1, 4, 0})
public final class MessageDeflaterKt
{
  private static final ByteString EMPTY_DEFLATE_BLOCK = ByteString.Companion.decodeHex("000000ffff");
  private static final int LAST_OCTETS_COUNT_TO_REMOVE_AFTER_DEFLATION = 4;
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/okhttp3/internal/ws/MessageDeflaterKt.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */