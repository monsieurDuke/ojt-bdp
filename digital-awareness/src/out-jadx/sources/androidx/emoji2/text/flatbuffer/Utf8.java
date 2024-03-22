package androidx.emoji2.text.flatbuffer;

import java.nio.ByteBuffer;

/* loaded from: classes.dex */
public abstract class Utf8 {
    private static Utf8 DEFAULT;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public static class DecodeUtil {
        DecodeUtil() {
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        public static void handleFourBytes(byte byte1, byte byte2, byte byte3, byte byte4, char[] resultArr, int resultPos) throws IllegalArgumentException {
            if (isNotTrailingByte(byte2) || (((byte1 << 28) + (byte2 + 112)) >> 30) != 0 || isNotTrailingByte(byte3) || isNotTrailingByte(byte4)) {
                throw new IllegalArgumentException("Invalid UTF-8");
            }
            int trailingByteValue = ((byte1 & 7) << 18) | (trailingByteValue(byte2) << 12) | (trailingByteValue(byte3) << 6) | trailingByteValue(byte4);
            resultArr[resultPos] = highSurrogate(trailingByteValue);
            resultArr[resultPos + 1] = lowSurrogate(trailingByteValue);
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        public static void handleOneByte(byte byte1, char[] resultArr, int resultPos) {
            resultArr[resultPos] = (char) byte1;
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        public static void handleThreeBytes(byte byte1, byte byte2, byte byte3, char[] resultArr, int resultPos) throws IllegalArgumentException {
            if (isNotTrailingByte(byte2) || ((byte1 == -32 && byte2 < -96) || ((byte1 == -19 && byte2 >= -96) || isNotTrailingByte(byte3)))) {
                throw new IllegalArgumentException("Invalid UTF-8");
            }
            resultArr[resultPos] = (char) (((byte1 & 15) << 12) | (trailingByteValue(byte2) << 6) | trailingByteValue(byte3));
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        public static void handleTwoBytes(byte byte1, byte byte2, char[] resultArr, int resultPos) throws IllegalArgumentException {
            if (byte1 < -62) {
                throw new IllegalArgumentException("Invalid UTF-8: Illegal leading byte in 2 bytes utf");
            }
            if (isNotTrailingByte(byte2)) {
                throw new IllegalArgumentException("Invalid UTF-8: Illegal trailing byte in 2 bytes utf");
            }
            resultArr[resultPos] = (char) (((byte1 & 31) << 6) | trailingByteValue(byte2));
        }

        private static char highSurrogate(int codePoint) {
            return (char) ((codePoint >>> 10) + okio.Utf8.HIGH_SURROGATE_HEADER);
        }

        private static boolean isNotTrailingByte(byte b) {
            return b > -65;
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        public static boolean isOneByte(byte b) {
            return b >= 0;
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        public static boolean isThreeBytes(byte b) {
            return b < -16;
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        public static boolean isTwoBytes(byte b) {
            return b < -32;
        }

        private static char lowSurrogate(int codePoint) {
            return (char) ((codePoint & 1023) + okio.Utf8.LOG_SURROGATE_HEADER);
        }

        private static int trailingByteValue(byte b) {
            return b & okio.Utf8.REPLACEMENT_BYTE;
        }
    }

    /* loaded from: classes.dex */
    static class UnpairedSurrogateException extends IllegalArgumentException {
        UnpairedSurrogateException(int index, int length) {
            super("Unpaired surrogate at index " + index + " of " + length);
        }
    }

    public static Utf8 getDefault() {
        if (DEFAULT == null) {
            DEFAULT = new Utf8Safe();
        }
        return DEFAULT;
    }

    public static void setDefault(Utf8 instance) {
        DEFAULT = instance;
    }

    public abstract String decodeUtf8(ByteBuffer byteBuffer, int i, int i2);

    public abstract void encodeUtf8(CharSequence charSequence, ByteBuffer byteBuffer);

    public abstract int encodedLength(CharSequence charSequence);
}
