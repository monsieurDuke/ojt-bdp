package com.google.android.material.color;

import com.google.android.material.R;

/* loaded from: classes.dex */
public class HarmonizedColorsOptions {
    private final int colorAttributeToHarmonizeWith;
    private final HarmonizedColorAttributes colorAttributes;
    private final int[] colorResourceIds;

    /* loaded from: classes.dex */
    public static class Builder {
        private HarmonizedColorAttributes colorAttributes;
        private int[] colorResourceIds = new int[0];
        private int colorAttributeToHarmonizeWith = R.attr.colorPrimary;

        public HarmonizedColorsOptions build() {
            return new HarmonizedColorsOptions(this);
        }

        public Builder setColorAttributeToHarmonizeWith(int colorAttributeToHarmonizeWith) {
            this.colorAttributeToHarmonizeWith = colorAttributeToHarmonizeWith;
            return this;
        }

        public Builder setColorAttributes(HarmonizedColorAttributes colorAttributes) {
            this.colorAttributes = colorAttributes;
            return this;
        }

        public Builder setColorResourceIds(int[] colorResourceIds) {
            this.colorResourceIds = colorResourceIds;
            return this;
        }
    }

    private HarmonizedColorsOptions(Builder builder) {
        this.colorResourceIds = builder.colorResourceIds;
        this.colorAttributes = builder.colorAttributes;
        this.colorAttributeToHarmonizeWith = builder.colorAttributeToHarmonizeWith;
    }

    public static HarmonizedColorsOptions createMaterialDefaults() {
        return new Builder().setColorAttributes(HarmonizedColorAttributes.createMaterialDefaults()).build();
    }

    public int getColorAttributeToHarmonizeWith() {
        return this.colorAttributeToHarmonizeWith;
    }

    public HarmonizedColorAttributes getColorAttributes() {
        return this.colorAttributes;
    }

    public int[] getColorResourceIds() {
        return this.colorResourceIds;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public int getThemeOverlayResourceId(int defaultThemeOverlay) {
        HarmonizedColorAttributes harmonizedColorAttributes = this.colorAttributes;
        return (harmonizedColorAttributes == null || harmonizedColorAttributes.getThemeOverlay() == 0) ? defaultThemeOverlay : this.colorAttributes.getThemeOverlay();
    }
}
