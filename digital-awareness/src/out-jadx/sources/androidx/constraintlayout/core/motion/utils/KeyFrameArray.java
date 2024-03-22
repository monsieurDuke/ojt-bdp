package androidx.constraintlayout.core.motion.utils;

import androidx.constraintlayout.core.motion.CustomAttribute;
import androidx.constraintlayout.core.motion.CustomVariable;
import java.io.PrintStream;
import java.util.Arrays;
import mt.Log229316;
import mt.Log5ECF72;
import mt.LogE84000;
import okhttp3.HttpUrl;

/* loaded from: classes.dex */
public class KeyFrameArray {

    /* compiled from: 0010.java */
    /* loaded from: classes.dex */
    public static class CustomArray {
        private static final int EMPTY = 999;
        int count;
        int[] keys = new int[101];
        CustomAttribute[] values = new CustomAttribute[101];

        public CustomArray() {
            clear();
        }

        public void append(int position, CustomAttribute value) {
            if (this.values[position] != null) {
                remove(position);
            }
            this.values[position] = value;
            int[] iArr = this.keys;
            int i = this.count;
            this.count = i + 1;
            iArr[i] = position;
            Arrays.sort(iArr);
        }

        public void clear() {
            Arrays.fill(this.keys, 999);
            Arrays.fill(this.values, (Object) null);
            this.count = 0;
        }

        /* JADX WARN: Failed to parse debug info
        jadx.core.utils.exceptions.JadxRuntimeException: Failed to parse type string: null
        	at jadx.core.dex.instructions.args.ArgType.parse(ArgType.java:736)
        	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.getVarType(DebugInfoAttachVisitor.java:139)
        	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.attachDebugInfo(DebugInfoAttachVisitor.java:94)
        	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.processDebugInfo(DebugInfoAttachVisitor.java:51)
        	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.visit(DebugInfoAttachVisitor.java:41)
         */
        public void dump() {
            PrintStream printStream = System.out;
            StringBuilder append = new StringBuilder().append("V: ");
            String arrays = Arrays.toString(Arrays.copyOf(this.keys, this.count));
            Log5ECF72.a(arrays);
            LogE84000.a(arrays);
            Log229316.a(arrays);
            printStream.println(append.append(arrays).toString());
            System.out.print("K: [");
            int i = 0;
            while (i < this.count) {
                System.out.print((i == 0 ? HttpUrl.FRAGMENT_ENCODE_SET : ", ") + valueAt(i));
                i++;
            }
            System.out.println("]");
        }

        public int keyAt(int i) {
            return this.keys[i];
        }

        public void remove(int position) {
            this.values[position] = null;
            int i = 0;
            int i2 = 0;
            while (true) {
                int i3 = this.count;
                if (i2 >= i3) {
                    this.count = i3 - 1;
                    return;
                }
                int[] iArr = this.keys;
                if (position == iArr[i2]) {
                    iArr[i2] = 999;
                    i++;
                }
                if (i2 != i) {
                    iArr[i2] = iArr[i];
                }
                i++;
                i2++;
            }
        }

        public int size() {
            return this.count;
        }

        public CustomAttribute valueAt(int i) {
            return this.values[this.keys[i]];
        }
    }

    /* compiled from: 0011.java */
    /* loaded from: classes.dex */
    public static class CustomVar {
        private static final int EMPTY = 999;
        int count;
        int[] keys = new int[101];
        CustomVariable[] values = new CustomVariable[101];

        public CustomVar() {
            clear();
        }

        public void append(int position, CustomVariable value) {
            if (this.values[position] != null) {
                remove(position);
            }
            this.values[position] = value;
            int[] iArr = this.keys;
            int i = this.count;
            this.count = i + 1;
            iArr[i] = position;
            Arrays.sort(iArr);
        }

        public void clear() {
            Arrays.fill(this.keys, 999);
            Arrays.fill(this.values, (Object) null);
            this.count = 0;
        }

        /* JADX WARN: Failed to parse debug info
        jadx.core.utils.exceptions.JadxRuntimeException: Failed to parse type string: null
        	at jadx.core.dex.instructions.args.ArgType.parse(ArgType.java:736)
        	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.getVarType(DebugInfoAttachVisitor.java:139)
        	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.attachDebugInfo(DebugInfoAttachVisitor.java:94)
        	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.processDebugInfo(DebugInfoAttachVisitor.java:51)
        	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.visit(DebugInfoAttachVisitor.java:41)
         */
        public void dump() {
            PrintStream printStream = System.out;
            StringBuilder append = new StringBuilder().append("V: ");
            String arrays = Arrays.toString(Arrays.copyOf(this.keys, this.count));
            Log5ECF72.a(arrays);
            LogE84000.a(arrays);
            Log229316.a(arrays);
            printStream.println(append.append(arrays).toString());
            System.out.print("K: [");
            int i = 0;
            while (i < this.count) {
                System.out.print((i == 0 ? HttpUrl.FRAGMENT_ENCODE_SET : ", ") + valueAt(i));
                i++;
            }
            System.out.println("]");
        }

        public int keyAt(int i) {
            return this.keys[i];
        }

        public void remove(int position) {
            this.values[position] = null;
            int i = 0;
            int i2 = 0;
            while (true) {
                int i3 = this.count;
                if (i2 >= i3) {
                    this.count = i3 - 1;
                    return;
                }
                int[] iArr = this.keys;
                if (position == iArr[i2]) {
                    iArr[i2] = 999;
                    i++;
                }
                if (i2 != i) {
                    iArr[i2] = iArr[i];
                }
                i++;
                i2++;
            }
        }

        public int size() {
            return this.count;
        }

        public CustomVariable valueAt(int i) {
            return this.values[this.keys[i]];
        }
    }

    /* compiled from: 0012.java */
    /* loaded from: classes.dex */
    static class FloatArray {
        private static final int EMPTY = 999;
        int count;
        int[] keys = new int[101];
        float[][] values = new float[101];

        public FloatArray() {
            clear();
        }

        public void append(int position, float[] value) {
            if (this.values[position] != null) {
                remove(position);
            }
            this.values[position] = value;
            int[] iArr = this.keys;
            int i = this.count;
            this.count = i + 1;
            iArr[i] = position;
            Arrays.sort(iArr);
        }

        public void clear() {
            Arrays.fill(this.keys, 999);
            Arrays.fill(this.values, (Object) null);
            this.count = 0;
        }

        /* JADX WARN: Failed to parse debug info
        jadx.core.utils.exceptions.JadxRuntimeException: Failed to parse type string: null
        	at jadx.core.dex.instructions.args.ArgType.parse(ArgType.java:736)
        	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.getVarType(DebugInfoAttachVisitor.java:139)
        	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.attachDebugInfo(DebugInfoAttachVisitor.java:94)
        	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.processDebugInfo(DebugInfoAttachVisitor.java:51)
        	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.visit(DebugInfoAttachVisitor.java:41)
         */
        public void dump() {
            PrintStream printStream = System.out;
            StringBuilder append = new StringBuilder().append("V: ");
            String arrays = Arrays.toString(Arrays.copyOf(this.keys, this.count));
            Log5ECF72.a(arrays);
            LogE84000.a(arrays);
            Log229316.a(arrays);
            printStream.println(append.append(arrays).toString());
            System.out.print("K: [");
            int i = 0;
            while (i < this.count) {
                PrintStream printStream2 = System.out;
                StringBuilder append2 = new StringBuilder().append(i == 0 ? HttpUrl.FRAGMENT_ENCODE_SET : ", ");
                String arrays2 = Arrays.toString(valueAt(i));
                Log5ECF72.a(arrays2);
                LogE84000.a(arrays2);
                Log229316.a(arrays2);
                printStream2.print(append2.append(arrays2).toString());
                i++;
            }
            System.out.println("]");
        }

        public int keyAt(int i) {
            return this.keys[i];
        }

        public void remove(int position) {
            this.values[position] = null;
            int i = 0;
            int i2 = 0;
            while (true) {
                int i3 = this.count;
                if (i2 >= i3) {
                    this.count = i3 - 1;
                    return;
                }
                int[] iArr = this.keys;
                if (position == iArr[i2]) {
                    iArr[i2] = 999;
                    i++;
                }
                if (i2 != i) {
                    iArr[i2] = iArr[i];
                }
                i++;
                i2++;
            }
        }

        public int size() {
            return this.count;
        }

        public float[] valueAt(int i) {
            return this.values[this.keys[i]];
        }
    }
}
