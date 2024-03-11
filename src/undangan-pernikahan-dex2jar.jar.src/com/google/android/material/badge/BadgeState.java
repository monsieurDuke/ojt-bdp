package com.google.android.material.badge;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.os.Build.VERSION;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.util.AttributeSet;
import com.google.android.material.R.dimen;
import com.google.android.material.R.plurals;
import com.google.android.material.R.string;
import com.google.android.material.R.style;
import com.google.android.material.R.styleable;
import com.google.android.material.drawable.DrawableUtils;
import com.google.android.material.internal.ThemeEnforcement;
import com.google.android.material.resources.MaterialResources;
import com.google.android.material.resources.TextAppearance;
import java.util.Locale;
import java.util.Locale.Category;

public final class BadgeState
{
  private static final String BADGE_RESOURCE_TAG = "badge";
  private static final int DEFAULT_MAX_BADGE_CHARACTER_COUNT = 4;
  final float badgeRadius;
  final float badgeWidePadding;
  final float badgeWithTextRadius;
  private final State currentState;
  private final State overridingState;
  
  BadgeState(Context paramContext, int paramInt1, int paramInt2, int paramInt3, State paramState)
  {
    State localState2 = new State();
    this.currentState = localState2;
    State localState1 = paramState;
    if (paramState == null) {
      localState1 = new State();
    }
    if (paramInt1 != 0) {
      State.access$002(localState1, paramInt1);
    }
    TypedArray localTypedArray = generateTypedArray(paramContext, localState1.badgeResId, paramInt2, paramInt3);
    paramState = paramContext.getResources();
    this.badgeRadius = localTypedArray.getDimensionPixelSize(R.styleable.Badge_badgeRadius, paramState.getDimensionPixelSize(R.dimen.mtrl_badge_radius));
    this.badgeWidePadding = localTypedArray.getDimensionPixelSize(R.styleable.Badge_badgeWidePadding, paramState.getDimensionPixelSize(R.dimen.mtrl_badge_long_text_horizontal_padding));
    this.badgeWithTextRadius = localTypedArray.getDimensionPixelSize(R.styleable.Badge_badgeWithTextRadius, paramState.getDimensionPixelSize(R.dimen.mtrl_badge_with_text_radius));
    if (localState1.alpha == -2) {
      paramInt1 = 255;
    } else {
      paramInt1 = localState1.alpha;
    }
    State.access$102(localState2, paramInt1);
    if (localState1.contentDescriptionNumberless == null) {
      paramState = paramContext.getString(R.string.mtrl_badge_numberless_content_description);
    } else {
      paramState = localState1.contentDescriptionNumberless;
    }
    State.access$202(localState2, paramState);
    if (localState1.contentDescriptionQuantityStrings == 0) {
      paramInt1 = R.plurals.mtrl_badge_content_description;
    } else {
      paramInt1 = localState1.contentDescriptionQuantityStrings;
    }
    State.access$302(localState2, paramInt1);
    if (localState1.contentDescriptionExceedsMaxBadgeNumberRes == 0) {
      paramInt1 = R.string.mtrl_exceed_max_badge_number_content_description;
    } else {
      paramInt1 = localState1.contentDescriptionExceedsMaxBadgeNumberRes;
    }
    State.access$402(localState2, paramInt1);
    paramState = localState1.isVisible;
    paramInt2 = 0;
    boolean bool;
    if ((paramState != null) && (!localState1.isVisible.booleanValue())) {
      bool = false;
    } else {
      bool = true;
    }
    State.access$502(localState2, Boolean.valueOf(bool));
    if (localState1.maxCharacterCount == -2) {
      paramInt1 = localTypedArray.getInt(R.styleable.Badge_maxCharacterCount, 4);
    } else {
      paramInt1 = localState1.maxCharacterCount;
    }
    State.access$602(localState2, paramInt1);
    if (localState1.number != -2) {
      State.access$702(localState2, localState1.number);
    } else if (localTypedArray.hasValue(R.styleable.Badge_number)) {
      State.access$702(localState2, localTypedArray.getInt(R.styleable.Badge_number, 0));
    } else {
      State.access$702(localState2, -1);
    }
    if (localState1.backgroundColor == null) {
      paramInt1 = readColorFromAttributes(paramContext, localTypedArray, R.styleable.Badge_backgroundColor);
    } else {
      paramInt1 = localState1.backgroundColor.intValue();
    }
    State.access$802(localState2, Integer.valueOf(paramInt1));
    if (localState1.badgeTextColor != null) {
      State.access$902(localState2, localState1.badgeTextColor);
    } else if (localTypedArray.hasValue(R.styleable.Badge_badgeTextColor)) {
      State.access$902(localState2, Integer.valueOf(readColorFromAttributes(paramContext, localTypedArray, R.styleable.Badge_badgeTextColor)));
    } else {
      State.access$902(localState2, Integer.valueOf(new TextAppearance(paramContext, R.style.TextAppearance_MaterialComponents_Badge).getTextColor().getDefaultColor()));
    }
    if (localState1.badgeGravity == null) {
      paramInt1 = localTypedArray.getInt(R.styleable.Badge_badgeGravity, 8388661);
    } else {
      paramInt1 = localState1.badgeGravity.intValue();
    }
    State.access$1002(localState2, Integer.valueOf(paramInt1));
    if (localState1.horizontalOffsetWithoutText == null) {
      paramInt1 = localTypedArray.getDimensionPixelOffset(R.styleable.Badge_horizontalOffset, 0);
    } else {
      paramInt1 = localState1.horizontalOffsetWithoutText.intValue();
    }
    State.access$1102(localState2, Integer.valueOf(paramInt1));
    if (localState1.horizontalOffsetWithoutText == null) {
      paramInt1 = localTypedArray.getDimensionPixelOffset(R.styleable.Badge_verticalOffset, 0);
    } else {
      paramInt1 = localState1.verticalOffsetWithoutText.intValue();
    }
    State.access$1202(localState2, Integer.valueOf(paramInt1));
    if (localState1.horizontalOffsetWithText == null) {
      paramInt1 = localTypedArray.getDimensionPixelOffset(R.styleable.Badge_horizontalOffsetWithText, localState2.horizontalOffsetWithoutText.intValue());
    } else {
      paramInt1 = localState1.horizontalOffsetWithText.intValue();
    }
    State.access$1302(localState2, Integer.valueOf(paramInt1));
    if (localState1.verticalOffsetWithText == null) {
      paramInt1 = localTypedArray.getDimensionPixelOffset(R.styleable.Badge_verticalOffsetWithText, localState2.verticalOffsetWithoutText.intValue());
    } else {
      paramInt1 = localState1.verticalOffsetWithText.intValue();
    }
    State.access$1402(localState2, Integer.valueOf(paramInt1));
    if (localState1.additionalHorizontalOffset == null) {
      paramInt1 = 0;
    } else {
      paramInt1 = localState1.additionalHorizontalOffset.intValue();
    }
    State.access$1502(localState2, Integer.valueOf(paramInt1));
    if (localState1.additionalVerticalOffset == null) {
      paramInt1 = paramInt2;
    } else {
      paramInt1 = localState1.additionalVerticalOffset.intValue();
    }
    State.access$1602(localState2, Integer.valueOf(paramInt1));
    localTypedArray.recycle();
    if (localState1.numberLocale == null)
    {
      if (Build.VERSION.SDK_INT >= 24) {
        paramContext = Locale.getDefault(Locale.Category.FORMAT);
      } else {
        paramContext = Locale.getDefault();
      }
      State.access$1702(localState2, paramContext);
    }
    else
    {
      State.access$1702(localState2, localState1.numberLocale);
    }
    this.overridingState = localState1;
  }
  
  private TypedArray generateTypedArray(Context paramContext, int paramInt1, int paramInt2, int paramInt3)
  {
    AttributeSet localAttributeSet = null;
    int i = 0;
    if (paramInt1 != 0)
    {
      localAttributeSet = DrawableUtils.parseDrawableXml(paramContext, paramInt1, "badge");
      i = localAttributeSet.getStyleAttribute();
    }
    paramInt1 = i;
    if (i == 0) {
      paramInt1 = paramInt3;
    }
    return ThemeEnforcement.obtainStyledAttributes(paramContext, localAttributeSet, R.styleable.Badge, paramInt2, paramInt1, new int[0]);
  }
  
  private static int readColorFromAttributes(Context paramContext, TypedArray paramTypedArray, int paramInt)
  {
    return MaterialResources.getColorStateList(paramContext, paramTypedArray, paramInt).getDefaultColor();
  }
  
  void clearNumber()
  {
    setNumber(-1);
  }
  
  int getAdditionalHorizontalOffset()
  {
    return this.currentState.additionalHorizontalOffset.intValue();
  }
  
  int getAdditionalVerticalOffset()
  {
    return this.currentState.additionalVerticalOffset.intValue();
  }
  
  int getAlpha()
  {
    return this.currentState.alpha;
  }
  
  int getBackgroundColor()
  {
    return this.currentState.backgroundColor.intValue();
  }
  
  int getBadgeGravity()
  {
    return this.currentState.badgeGravity.intValue();
  }
  
  int getBadgeTextColor()
  {
    return this.currentState.badgeTextColor.intValue();
  }
  
  int getContentDescriptionExceedsMaxBadgeNumberStringResource()
  {
    return this.currentState.contentDescriptionExceedsMaxBadgeNumberRes;
  }
  
  CharSequence getContentDescriptionNumberless()
  {
    return this.currentState.contentDescriptionNumberless;
  }
  
  int getContentDescriptionQuantityStrings()
  {
    return this.currentState.contentDescriptionQuantityStrings;
  }
  
  int getHorizontalOffsetWithText()
  {
    return this.currentState.horizontalOffsetWithText.intValue();
  }
  
  int getHorizontalOffsetWithoutText()
  {
    return this.currentState.horizontalOffsetWithoutText.intValue();
  }
  
  int getMaxCharacterCount()
  {
    return this.currentState.maxCharacterCount;
  }
  
  int getNumber()
  {
    return this.currentState.number;
  }
  
  Locale getNumberLocale()
  {
    return this.currentState.numberLocale;
  }
  
  State getOverridingState()
  {
    return this.overridingState;
  }
  
  int getVerticalOffsetWithText()
  {
    return this.currentState.verticalOffsetWithText.intValue();
  }
  
  int getVerticalOffsetWithoutText()
  {
    return this.currentState.verticalOffsetWithoutText.intValue();
  }
  
  boolean hasNumber()
  {
    boolean bool;
    if (this.currentState.number != -1) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  boolean isVisible()
  {
    return this.currentState.isVisible.booleanValue();
  }
  
  void setAdditionalHorizontalOffset(int paramInt)
  {
    State.access$1502(this.overridingState, Integer.valueOf(paramInt));
    State.access$1502(this.currentState, Integer.valueOf(paramInt));
  }
  
  void setAdditionalVerticalOffset(int paramInt)
  {
    State.access$1602(this.overridingState, Integer.valueOf(paramInt));
    State.access$1602(this.currentState, Integer.valueOf(paramInt));
  }
  
  void setAlpha(int paramInt)
  {
    State.access$102(this.overridingState, paramInt);
    State.access$102(this.currentState, paramInt);
  }
  
  void setBackgroundColor(int paramInt)
  {
    State.access$802(this.overridingState, Integer.valueOf(paramInt));
    State.access$802(this.currentState, Integer.valueOf(paramInt));
  }
  
  void setBadgeGravity(int paramInt)
  {
    State.access$1002(this.overridingState, Integer.valueOf(paramInt));
    State.access$1002(this.currentState, Integer.valueOf(paramInt));
  }
  
  void setBadgeTextColor(int paramInt)
  {
    State.access$902(this.overridingState, Integer.valueOf(paramInt));
    State.access$902(this.currentState, Integer.valueOf(paramInt));
  }
  
  void setContentDescriptionExceedsMaxBadgeNumberStringResource(int paramInt)
  {
    State.access$402(this.overridingState, paramInt);
    State.access$402(this.currentState, paramInt);
  }
  
  void setContentDescriptionNumberless(CharSequence paramCharSequence)
  {
    State.access$202(this.overridingState, paramCharSequence);
    State.access$202(this.currentState, paramCharSequence);
  }
  
  void setContentDescriptionQuantityStringsResource(int paramInt)
  {
    State.access$302(this.overridingState, paramInt);
    State.access$302(this.currentState, paramInt);
  }
  
  void setHorizontalOffsetWithText(int paramInt)
  {
    State.access$1302(this.overridingState, Integer.valueOf(paramInt));
    State.access$1302(this.currentState, Integer.valueOf(paramInt));
  }
  
  void setHorizontalOffsetWithoutText(int paramInt)
  {
    State.access$1102(this.overridingState, Integer.valueOf(paramInt));
    State.access$1102(this.currentState, Integer.valueOf(paramInt));
  }
  
  void setMaxCharacterCount(int paramInt)
  {
    State.access$602(this.overridingState, paramInt);
    State.access$602(this.currentState, paramInt);
  }
  
  void setNumber(int paramInt)
  {
    State.access$702(this.overridingState, paramInt);
    State.access$702(this.currentState, paramInt);
  }
  
  void setNumberLocale(Locale paramLocale)
  {
    State.access$1702(this.overridingState, paramLocale);
    State.access$1702(this.currentState, paramLocale);
  }
  
  void setVerticalOffsetWithText(int paramInt)
  {
    State.access$1402(this.overridingState, Integer.valueOf(paramInt));
    State.access$1402(this.currentState, Integer.valueOf(paramInt));
  }
  
  void setVerticalOffsetWithoutText(int paramInt)
  {
    State.access$1202(this.overridingState, Integer.valueOf(paramInt));
    State.access$1202(this.currentState, Integer.valueOf(paramInt));
  }
  
  void setVisible(boolean paramBoolean)
  {
    State.access$502(this.overridingState, Boolean.valueOf(paramBoolean));
    State.access$502(this.currentState, Boolean.valueOf(paramBoolean));
  }
  
  public static final class State
    implements Parcelable
  {
    private static final int BADGE_NUMBER_NONE = -1;
    public static final Parcelable.Creator<State> CREATOR = new Parcelable.Creator()
    {
      public BadgeState.State createFromParcel(Parcel paramAnonymousParcel)
      {
        return new BadgeState.State(paramAnonymousParcel);
      }
      
      public BadgeState.State[] newArray(int paramAnonymousInt)
      {
        return new BadgeState.State[paramAnonymousInt];
      }
    };
    private static final int NOT_SET = -2;
    private Integer additionalHorizontalOffset;
    private Integer additionalVerticalOffset;
    private int alpha = 255;
    private Integer backgroundColor;
    private Integer badgeGravity;
    private int badgeResId;
    private Integer badgeTextColor;
    private int contentDescriptionExceedsMaxBadgeNumberRes;
    private CharSequence contentDescriptionNumberless;
    private int contentDescriptionQuantityStrings;
    private Integer horizontalOffsetWithText;
    private Integer horizontalOffsetWithoutText;
    private Boolean isVisible = Boolean.valueOf(true);
    private int maxCharacterCount = -2;
    private int number = -2;
    private Locale numberLocale;
    private Integer verticalOffsetWithText;
    private Integer verticalOffsetWithoutText;
    
    public State() {}
    
    State(Parcel paramParcel)
    {
      this.badgeResId = paramParcel.readInt();
      this.backgroundColor = ((Integer)paramParcel.readSerializable());
      this.badgeTextColor = ((Integer)paramParcel.readSerializable());
      this.alpha = paramParcel.readInt();
      this.number = paramParcel.readInt();
      this.maxCharacterCount = paramParcel.readInt();
      this.contentDescriptionNumberless = paramParcel.readString();
      this.contentDescriptionQuantityStrings = paramParcel.readInt();
      this.badgeGravity = ((Integer)paramParcel.readSerializable());
      this.horizontalOffsetWithoutText = ((Integer)paramParcel.readSerializable());
      this.verticalOffsetWithoutText = ((Integer)paramParcel.readSerializable());
      this.horizontalOffsetWithText = ((Integer)paramParcel.readSerializable());
      this.verticalOffsetWithText = ((Integer)paramParcel.readSerializable());
      this.additionalHorizontalOffset = ((Integer)paramParcel.readSerializable());
      this.additionalVerticalOffset = ((Integer)paramParcel.readSerializable());
      this.isVisible = ((Boolean)paramParcel.readSerializable());
      this.numberLocale = ((Locale)paramParcel.readSerializable());
    }
    
    public int describeContents()
    {
      return 0;
    }
    
    public void writeToParcel(Parcel paramParcel, int paramInt)
    {
      paramParcel.writeInt(this.badgeResId);
      paramParcel.writeSerializable(this.backgroundColor);
      paramParcel.writeSerializable(this.badgeTextColor);
      paramParcel.writeInt(this.alpha);
      paramParcel.writeInt(this.number);
      paramParcel.writeInt(this.maxCharacterCount);
      Object localObject = this.contentDescriptionNumberless;
      if (localObject == null) {
        localObject = null;
      } else {
        localObject = localObject.toString();
      }
      paramParcel.writeString((String)localObject);
      paramParcel.writeInt(this.contentDescriptionQuantityStrings);
      paramParcel.writeSerializable(this.badgeGravity);
      paramParcel.writeSerializable(this.horizontalOffsetWithoutText);
      paramParcel.writeSerializable(this.verticalOffsetWithoutText);
      paramParcel.writeSerializable(this.horizontalOffsetWithText);
      paramParcel.writeSerializable(this.verticalOffsetWithText);
      paramParcel.writeSerializable(this.additionalHorizontalOffset);
      paramParcel.writeSerializable(this.additionalVerticalOffset);
      paramParcel.writeSerializable(this.isVisible);
      paramParcel.writeSerializable(this.numberLocale);
    }
  }
}


/* Location:              /home/icat/Documents/digital-awareness/src/undangan-pernikahan-dex2jar.jar!/com/google/android/material/badge/BadgeState.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */