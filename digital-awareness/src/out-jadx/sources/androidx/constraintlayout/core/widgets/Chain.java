package androidx.constraintlayout.core.widgets;

import androidx.constraintlayout.core.LinearSystem;
import java.util.ArrayList;

/* loaded from: classes.dex */
public class Chain {
    private static final boolean DEBUG = false;
    public static final boolean USE_CHAIN_OPTIMIZATION = false;

    /* JADX WARN: Removed duplicated region for block: B:216:0x069a  */
    /* JADX WARN: Removed duplicated region for block: B:219:0x06a6  */
    /* JADX WARN: Removed duplicated region for block: B:222:0x06b1  */
    /* JADX WARN: Removed duplicated region for block: B:224:0x06b9  */
    /* JADX WARN: Removed duplicated region for block: B:229:0x06d5  */
    /* JADX WARN: Removed duplicated region for block: B:234:0x06e2  */
    /* JADX WARN: Removed duplicated region for block: B:236:0x06d1  */
    /* JADX WARN: Removed duplicated region for block: B:237:0x06b6  */
    /* JADX WARN: Removed duplicated region for block: B:238:0x06ab  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    static void applyChainConstraints(androidx.constraintlayout.core.widgets.ConstraintWidgetContainer r44, androidx.constraintlayout.core.LinearSystem r45, int r46, int r47, androidx.constraintlayout.core.widgets.ChainHead r48) {
        /*
            Method dump skipped, instructions count: 1817
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.constraintlayout.core.widgets.Chain.applyChainConstraints(androidx.constraintlayout.core.widgets.ConstraintWidgetContainer, androidx.constraintlayout.core.LinearSystem, int, int, androidx.constraintlayout.core.widgets.ChainHead):void");
    }

    public static void applyChainConstraints(ConstraintWidgetContainer constraintWidgetContainer, LinearSystem system, ArrayList<ConstraintWidget> arrayList, int orientation) {
        int i;
        int i2;
        ChainHead[] chainHeadArr;
        if (orientation == 0) {
            i = 0;
            i2 = constraintWidgetContainer.mHorizontalChainsSize;
            chainHeadArr = constraintWidgetContainer.mHorizontalChainsArray;
        } else {
            i = 2;
            i2 = constraintWidgetContainer.mVerticalChainsSize;
            chainHeadArr = constraintWidgetContainer.mVerticalChainsArray;
        }
        for (int i3 = 0; i3 < i2; i3++) {
            ChainHead chainHead = chainHeadArr[i3];
            chainHead.define();
            if (arrayList == null || (arrayList != null && arrayList.contains(chainHead.mFirst))) {
                applyChainConstraints(constraintWidgetContainer, system, orientation, i, chainHead);
            }
        }
    }
}
