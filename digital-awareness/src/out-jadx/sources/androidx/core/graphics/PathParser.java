package androidx.core.graphics;

import android.graphics.Path;
import android.util.Log;
import java.util.ArrayList;

/* loaded from: classes.dex */
public class PathParser {
    private static final String LOGTAG = "PathParser";

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public static class ExtractFloatResult {
        int mEndPosition;
        boolean mEndWithNegOrDot;

        ExtractFloatResult() {
        }
    }

    /* loaded from: classes.dex */
    public static class PathDataNode {
        public float[] mParams;
        public char mType;

        PathDataNode(char type, float[] params) {
            this.mType = type;
            this.mParams = params;
        }

        PathDataNode(PathDataNode n) {
            this.mType = n.mType;
            float[] fArr = n.mParams;
            this.mParams = PathParser.copyOfRange(fArr, 0, fArr.length);
        }

        private static void addCommand(Path path, float[] current, char previousCmd, char cmd, float[] val) {
            int i;
            int i2;
            float f;
            float f2;
            float f3;
            float f4;
            Path path2 = path;
            float f5 = current[0];
            float f6 = current[1];
            float f7 = current[2];
            float f8 = current[3];
            float f9 = current[4];
            float f10 = current[5];
            switch (cmd) {
                case 'A':
                case 'a':
                    i = 7;
                    break;
                case 'C':
                case 'c':
                    i = 6;
                    break;
                case 'H':
                case 'V':
                case 'h':
                case 'v':
                    i = 1;
                    break;
                case 'L':
                case 'M':
                case 'T':
                case 'l':
                case 'm':
                case 't':
                    i = 2;
                    break;
                case 'Q':
                case 'S':
                case 'q':
                case 's':
                    i = 4;
                    break;
                case 'Z':
                case 'z':
                    path.close();
                    f5 = f9;
                    f6 = f10;
                    f7 = f9;
                    f8 = f10;
                    path2.moveTo(f5, f6);
                    i = 2;
                    break;
                default:
                    i = 2;
                    break;
            }
            char c = previousCmd;
            int i3 = 0;
            float f11 = f5;
            float f12 = f7;
            float f13 = f8;
            float f14 = f9;
            float f15 = f10;
            float f16 = f6;
            while (i3 < val.length) {
                switch (cmd) {
                    case 'A':
                        i2 = i3;
                        drawArc(path, f11, f16, val[i2 + 5], val[i2 + 6], val[i2 + 0], val[i2 + 1], val[i2 + 2], val[i2 + 3] != 0.0f, val[i2 + 4] != 0.0f);
                        float f17 = val[i2 + 5];
                        float f18 = val[i2 + 6];
                        f11 = f17;
                        f16 = f18;
                        f12 = f17;
                        f13 = f18;
                        break;
                    case 'C':
                        i2 = i3;
                        path.cubicTo(val[i2 + 0], val[i2 + 1], val[i2 + 2], val[i2 + 3], val[i2 + 4], val[i2 + 5]);
                        f11 = val[i2 + 4];
                        f16 = val[i2 + 5];
                        f12 = val[i2 + 2];
                        f13 = val[i2 + 3];
                        break;
                    case 'H':
                        i2 = i3;
                        path2.lineTo(val[i2 + 0], f16);
                        f11 = val[i2 + 0];
                        break;
                    case 'L':
                        i2 = i3;
                        path2.lineTo(val[i2 + 0], val[i2 + 1]);
                        f11 = val[i2 + 0];
                        f16 = val[i2 + 1];
                        break;
                    case 'M':
                        i2 = i3;
                        float f19 = val[i2 + 0];
                        float f20 = val[i2 + 1];
                        if (i2 <= 0) {
                            path2.moveTo(val[i2 + 0], val[i2 + 1]);
                            f11 = f19;
                            f16 = f20;
                            f14 = f19;
                            f15 = f20;
                            break;
                        } else {
                            path2.lineTo(val[i2 + 0], val[i2 + 1]);
                            f11 = f19;
                            f16 = f20;
                            break;
                        }
                    case 'Q':
                        i2 = i3;
                        path2.quadTo(val[i2 + 0], val[i2 + 1], val[i2 + 2], val[i2 + 3]);
                        f12 = val[i2 + 0];
                        f13 = val[i2 + 1];
                        f11 = val[i2 + 2];
                        f16 = val[i2 + 3];
                        break;
                    case 'S':
                        float f21 = f16;
                        i2 = i3;
                        char c2 = c;
                        float f22 = f11;
                        if (c2 == 'c' || c2 == 's' || c2 == 'C' || c2 == 'S') {
                            f = (f22 * 2.0f) - f12;
                            f2 = (f21 * 2.0f) - f13;
                        } else {
                            f = f22;
                            f2 = f21;
                        }
                        path.cubicTo(f, f2, val[i2 + 0], val[i2 + 1], val[i2 + 2], val[i2 + 3]);
                        f12 = val[i2 + 0];
                        f13 = val[i2 + 1];
                        f11 = val[i2 + 2];
                        f16 = val[i2 + 3];
                        break;
                    case 'T':
                        float f23 = f16;
                        i2 = i3;
                        char c3 = c;
                        float f24 = f11;
                        float f25 = f24;
                        float f26 = f23;
                        if (c3 == 'q' || c3 == 't' || c3 == 'Q' || c3 == 'T') {
                            f25 = (f24 * 2.0f) - f12;
                            f26 = (f23 * 2.0f) - f13;
                        }
                        path2.quadTo(f25, f26, val[i2 + 0], val[i2 + 1]);
                        f12 = f25;
                        f13 = f26;
                        f11 = val[i2 + 0];
                        f16 = val[i2 + 1];
                        break;
                    case 'V':
                        float f27 = f11;
                        i2 = i3;
                        path2 = path;
                        path2.lineTo(f27, val[i2 + 0]);
                        f16 = val[i2 + 0];
                        f11 = f27;
                        break;
                    case 'a':
                        float f28 = f16;
                        i2 = i3;
                        drawArc(path, f11, f28, val[i3 + 5] + f11, val[i3 + 6] + f28, val[i3 + 0], val[i3 + 1], val[i3 + 2], val[i3 + 3] != 0.0f, val[i3 + 4] != 0.0f);
                        f11 += val[i2 + 5];
                        f16 = f28 + val[i2 + 6];
                        path2 = path;
                        f12 = f11;
                        f13 = f16;
                        break;
                    case 'c':
                        float f29 = f16;
                        path.rCubicTo(val[i3 + 0], val[i3 + 1], val[i3 + 2], val[i3 + 3], val[i3 + 4], val[i3 + 5]);
                        float f30 = val[i3 + 2] + f11;
                        float f31 = f29 + val[i3 + 3];
                        f11 += val[i3 + 4];
                        f12 = f30;
                        f13 = f31;
                        i2 = i3;
                        f16 = val[i3 + 5] + f29;
                        break;
                    case 'h':
                        path2.rLineTo(val[i3 + 0], 0.0f);
                        f11 += val[i3 + 0];
                        i2 = i3;
                        break;
                    case 'l':
                        path2.rLineTo(val[i3 + 0], val[i3 + 1]);
                        f11 += val[i3 + 0];
                        f16 += val[i3 + 1];
                        i2 = i3;
                        break;
                    case 'm':
                        f11 += val[i3 + 0];
                        f16 += val[i3 + 1];
                        if (i3 <= 0) {
                            path2.rMoveTo(val[i3 + 0], val[i3 + 1]);
                            f14 = f11;
                            f15 = f16;
                            i2 = i3;
                            break;
                        } else {
                            path2.rLineTo(val[i3 + 0], val[i3 + 1]);
                            i2 = i3;
                            break;
                        }
                    case 'q':
                        float f32 = f16;
                        path2.rQuadTo(val[i3 + 0], val[i3 + 1], val[i3 + 2], val[i3 + 3]);
                        float f33 = val[i3 + 0] + f11;
                        float f34 = f32 + val[i3 + 1];
                        f11 += val[i3 + 2];
                        f12 = f33;
                        f13 = f34;
                        i2 = i3;
                        f16 = val[i3 + 3] + f32;
                        break;
                    case 's':
                        if (c == 'c' || c == 's' || c == 'C' || c == 'S') {
                            f3 = f11 - f12;
                            f4 = f16 - f13;
                        } else {
                            f3 = 0.0f;
                            f4 = 0.0f;
                        }
                        float f35 = f16;
                        path.rCubicTo(f3, f4, val[i3 + 0], val[i3 + 1], val[i3 + 2], val[i3 + 3]);
                        float f36 = val[i3 + 0] + f11;
                        float f37 = f35 + val[i3 + 1];
                        f11 += val[i3 + 2];
                        f12 = f36;
                        f13 = f37;
                        i2 = i3;
                        f16 = val[i3 + 3] + f35;
                        break;
                    case 't':
                        float f38 = 0.0f;
                        float f39 = 0.0f;
                        if (c == 'q' || c == 't' || c == 'Q' || c == 'T') {
                            f38 = f11 - f12;
                            f39 = f16 - f13;
                        }
                        path2.rQuadTo(f38, f39, val[i3 + 0], val[i3 + 1]);
                        float f40 = f11 + f38;
                        float f41 = f16 + f39;
                        f11 += val[i3 + 0];
                        f16 += val[i3 + 1];
                        f12 = f40;
                        f13 = f41;
                        i2 = i3;
                        break;
                    case 'v':
                        path2.rLineTo(0.0f, val[i3 + 0]);
                        f16 += val[i3 + 0];
                        i2 = i3;
                        break;
                    default:
                        i2 = i3;
                        break;
                }
                c = cmd;
                i3 = i2 + i;
            }
            current[0] = f11;
            current[1] = f16;
            current[2] = f12;
            current[3] = f13;
            current[4] = f14;
            current[5] = f15;
        }

        private static void arcToBezier(Path p, double cx, double cy, double a, double b, double e1x, double e1y, double theta, double start, double sweep) {
            double d = a;
            int ceil = (int) Math.ceil(Math.abs((sweep * 4.0d) / 3.141592653589793d));
            double cos = Math.cos(theta);
            double sin = Math.sin(theta);
            double cos2 = Math.cos(start);
            double sin2 = Math.sin(start);
            double d2 = ((-d) * sin * sin2) + (b * cos * cos2);
            double d3 = sweep / ceil;
            double d4 = start;
            int i = 0;
            double d5 = e1x;
            double d6 = (((-d) * cos) * sin2) - ((b * sin) * cos2);
            double d7 = e1y;
            while (i < ceil) {
                double d8 = d4 + d3;
                double sin3 = Math.sin(d8);
                double cos3 = Math.cos(d8);
                double d9 = d3;
                double d10 = (cx + ((d * cos) * cos3)) - ((b * sin) * sin3);
                double d11 = cos2;
                double e1x2 = cy + (d * sin * cos3) + (b * cos * sin3);
                double d12 = sin2;
                double d13 = (((-d) * cos) * sin3) - ((b * sin) * cos3);
                double e1x3 = ((-d) * sin * sin3) + (b * cos * cos3);
                double tan = Math.tan((d8 - d4) / 2.0d);
                double sin4 = (Math.sin(d8 - d4) * (Math.sqrt(((tan * 3.0d) * tan) + 4.0d) - 1.0d)) / 3.0d;
                p.rLineTo(0.0f, 0.0f);
                p.cubicTo((float) (d5 + (sin4 * d6)), (float) (d7 + (sin4 * d2)), (float) (d10 - (sin4 * d13)), (float) (e1x2 - (sin4 * e1x3)), (float) d10, (float) e1x2);
                d4 = d8;
                d5 = d10;
                d7 = e1x2;
                d6 = d13;
                d2 = e1x3;
                i++;
                ceil = ceil;
                sin2 = d12;
                d3 = d9;
                cos2 = d11;
                cos = cos;
                sin = sin;
                d = a;
            }
        }

        private static void drawArc(Path p, float x0, float y0, float x1, float y1, float a, float b, float theta, boolean isMoreThanHalf, boolean isPositiveArc) {
            double d;
            double d2;
            double radians = Math.toRadians(theta);
            double cos = Math.cos(radians);
            double sin = Math.sin(radians);
            double d3 = ((x0 * cos) + (y0 * sin)) / a;
            double d4 = (((-x0) * sin) + (y0 * cos)) / b;
            double d5 = ((x1 * cos) + (y1 * sin)) / a;
            double d6 = (((-x1) * sin) + (y1 * cos)) / b;
            double d7 = d3 - d5;
            double d8 = d4 - d6;
            double d9 = (d3 + d5) / 2.0d;
            double d10 = (d4 + d6) / 2.0d;
            double d11 = (d7 * d7) + (d8 * d8);
            if (d11 == 0.0d) {
                Log.w(PathParser.LOGTAG, " Points are coincident");
                return;
            }
            double d12 = (1.0d / d11) - 0.25d;
            if (d12 < 0.0d) {
                Log.w(PathParser.LOGTAG, "Points are too far apart " + d11);
                float sqrt = (float) (Math.sqrt(d11) / 1.99999d);
                drawArc(p, x0, y0, x1, y1, a * sqrt, b * sqrt, theta, isMoreThanHalf, isPositiveArc);
                return;
            }
            double sqrt2 = Math.sqrt(d12);
            double d13 = sqrt2 * d7;
            double d14 = sqrt2 * d8;
            if (isMoreThanHalf == isPositiveArc) {
                d = d9 - d14;
                d2 = d10 + d13;
            } else {
                d = d9 + d14;
                d2 = d10 - d13;
            }
            double atan2 = Math.atan2(d4 - d2, d3 - d);
            double atan22 = Math.atan2(d6 - d2, d5 - d) - atan2;
            if (isPositiveArc != (atan22 >= 0.0d)) {
                atan22 = atan22 > 0.0d ? atan22 - 6.283185307179586d : atan22 + 6.283185307179586d;
            }
            double d15 = d * a;
            double d16 = b * d2;
            arcToBezier(p, (d15 * cos) - (d16 * sin), (d15 * sin) + (d16 * cos), a, b, x0, y0, radians, atan2, atan22);
        }

        public static void nodesToPath(PathDataNode[] node, Path path) {
            float[] fArr = new float[6];
            char c = 'm';
            for (int i = 0; i < node.length; i++) {
                addCommand(path, fArr, c, node[i].mType, node[i].mParams);
                c = node[i].mType;
            }
        }

        public void interpolatePathDataNode(PathDataNode nodeFrom, PathDataNode nodeTo, float fraction) {
            this.mType = nodeFrom.mType;
            int i = 0;
            while (true) {
                float[] fArr = nodeFrom.mParams;
                if (i >= fArr.length) {
                    return;
                }
                this.mParams[i] = (fArr[i] * (1.0f - fraction)) + (nodeTo.mParams[i] * fraction);
                i++;
            }
        }
    }

    private PathParser() {
    }

    private static void addNode(ArrayList<PathDataNode> arrayList, char cmd, float[] val) {
        arrayList.add(new PathDataNode(cmd, val));
    }

    public static boolean canMorph(PathDataNode[] nodesFrom, PathDataNode[] nodesTo) {
        if (nodesFrom == null || nodesTo == null || nodesFrom.length != nodesTo.length) {
            return false;
        }
        for (int i = 0; i < nodesFrom.length; i++) {
            if (nodesFrom[i].mType != nodesTo[i].mType || nodesFrom[i].mParams.length != nodesTo[i].mParams.length) {
                return false;
            }
        }
        return true;
    }

    static float[] copyOfRange(float[] original, int start, int end) {
        if (start > end) {
            throw new IllegalArgumentException();
        }
        int length = original.length;
        if (start < 0 || start > length) {
            throw new ArrayIndexOutOfBoundsException();
        }
        int i = end - start;
        float[] fArr = new float[i];
        System.arraycopy(original, start, fArr, 0, Math.min(i, length - start));
        return fArr;
    }

    public static PathDataNode[] createNodesFromPathData(String pathData) {
        if (pathData == null) {
            return null;
        }
        int i = 0;
        int i2 = 1;
        ArrayList arrayList = new ArrayList();
        while (i2 < pathData.length()) {
            int nextStart = nextStart(pathData, i2);
            String trim = pathData.substring(i, nextStart).trim();
            if (trim.length() > 0) {
                addNode(arrayList, trim.charAt(0), getFloats(trim));
            }
            i = nextStart;
            i2 = nextStart + 1;
        }
        if (i2 - i == 1 && i < pathData.length()) {
            addNode(arrayList, pathData.charAt(i), new float[0]);
        }
        return (PathDataNode[]) arrayList.toArray(new PathDataNode[arrayList.size()]);
    }

    public static Path createPathFromPathData(String pathData) {
        Path path = new Path();
        PathDataNode[] createNodesFromPathData = createNodesFromPathData(pathData);
        if (createNodesFromPathData == null) {
            return null;
        }
        try {
            PathDataNode.nodesToPath(createNodesFromPathData, path);
            return path;
        } catch (RuntimeException e) {
            throw new RuntimeException("Error in parsing " + pathData, e);
        }
    }

    public static PathDataNode[] deepCopyNodes(PathDataNode[] source) {
        if (source == null) {
            return null;
        }
        PathDataNode[] pathDataNodeArr = new PathDataNode[source.length];
        for (int i = 0; i < source.length; i++) {
            pathDataNodeArr[i] = new PathDataNode(source[i]);
        }
        return pathDataNodeArr;
    }

    /* JADX WARN: Failed to find 'out' block for switch in B:5:0x0014. Please report as an issue. */
    private static void extract(String s, int start, ExtractFloatResult result) {
        boolean z = false;
        result.mEndWithNegOrDot = false;
        boolean z2 = false;
        boolean z3 = false;
        for (int i = start; i < s.length(); i++) {
            boolean z4 = z3;
            z3 = false;
            switch (s.charAt(i)) {
                case ' ':
                case ',':
                    z = true;
                    break;
                case '-':
                    if (i != start && !z4) {
                        z = true;
                        result.mEndWithNegOrDot = true;
                        break;
                    }
                    break;
                case '.':
                    if (z2) {
                        z = true;
                        result.mEndWithNegOrDot = true;
                        break;
                    } else {
                        z2 = true;
                        break;
                    }
                case 'E':
                case 'e':
                    z3 = true;
                    break;
            }
            if (z) {
                result.mEndPosition = i;
            }
        }
        result.mEndPosition = i;
    }

    private static float[] getFloats(String s) {
        if (s.charAt(0) == 'z' || s.charAt(0) == 'Z') {
            return new float[0];
        }
        try {
            float[] fArr = new float[s.length()];
            int i = 0;
            int i2 = 1;
            ExtractFloatResult extractFloatResult = new ExtractFloatResult();
            int length = s.length();
            while (i2 < length) {
                extract(s, i2, extractFloatResult);
                int i3 = extractFloatResult.mEndPosition;
                if (i2 < i3) {
                    fArr[i] = Float.parseFloat(s.substring(i2, i3));
                    i++;
                }
                i2 = extractFloatResult.mEndWithNegOrDot ? i3 : i3 + 1;
            }
            return copyOfRange(fArr, 0, i);
        } catch (NumberFormatException e) {
            throw new RuntimeException("error in parsing \"" + s + "\"", e);
        }
    }

    public static boolean interpolatePathDataNodes(PathDataNode[] target, PathDataNode[] from, PathDataNode[] to, float fraction) {
        if (target == null || from == null || to == null) {
            throw new IllegalArgumentException("The nodes to be interpolated and resulting nodes cannot be null");
        }
        if (target.length != from.length || from.length != to.length) {
            throw new IllegalArgumentException("The nodes to be interpolated and resulting nodes must have the same length");
        }
        if (!canMorph(from, to)) {
            return false;
        }
        for (int i = 0; i < target.length; i++) {
            target[i].interpolatePathDataNode(from[i], to[i], fraction);
        }
        return true;
    }

    private static int nextStart(String s, int end) {
        while (end < s.length()) {
            char charAt = s.charAt(end);
            if (((charAt - 'A') * (charAt - 'Z') <= 0 || (charAt - 'a') * (charAt - 'z') <= 0) && charAt != 'e' && charAt != 'E') {
                return end;
            }
            end++;
        }
        return end;
    }

    public static void updateNodes(PathDataNode[] target, PathDataNode[] source) {
        for (int i = 0; i < source.length; i++) {
            target[i].mType = source[i].mType;
            for (int i2 = 0; i2 < source[i].mParams.length; i2++) {
                target[i].mParams[i2] = source[i].mParams[i2];
            }
        }
    }
}
