package androidx.fragment.app;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TabWidget;
import java.util.ArrayList;
import mt.Log229316;
import mt.Log5ECF72;
import mt.LogE84000;

@Deprecated
/* loaded from: classes.dex */
public class FragmentTabHost extends TabHost implements TabHost.OnTabChangeListener {
    private boolean mAttached;
    private int mContainerId;
    private Context mContext;
    private FragmentManager mFragmentManager;
    private TabInfo mLastTab;
    private TabHost.OnTabChangeListener mOnTabChangeListener;
    private FrameLayout mRealTabContent;
    private final ArrayList<TabInfo> mTabs;

    /* loaded from: classes.dex */
    static class DummyTabFactory implements TabHost.TabContentFactory {
        private final Context mContext;

        public DummyTabFactory(Context context) {
            this.mContext = context;
        }

        @Override // android.widget.TabHost.TabContentFactory
        public View createTabContent(String tag) {
            View view = new View(this.mContext);
            view.setMinimumWidth(0);
            view.setMinimumHeight(0);
            return view;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* compiled from: 007D.java */
    /* loaded from: classes.dex */
    public static class SavedState extends View.BaseSavedState {
        public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.Creator<SavedState>() { // from class: androidx.fragment.app.FragmentTabHost.SavedState.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public SavedState createFromParcel(Parcel in) {
                return new SavedState(in);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public SavedState[] newArray(int size) {
                return new SavedState[size];
            }
        };
        String curTab;

        SavedState(Parcel in) {
            super(in);
            this.curTab = in.readString();
        }

        SavedState(Parcelable superState) {
            super(superState);
        }

        /* JADX WARN: Failed to parse debug info
        jadx.core.utils.exceptions.JadxRuntimeException: Failed to parse type string: null
        	at jadx.core.dex.instructions.args.ArgType.parse(ArgType.java:736)
        	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.getVarType(DebugInfoAttachVisitor.java:139)
        	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.attachDebugInfo(DebugInfoAttachVisitor.java:94)
        	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.processDebugInfo(DebugInfoAttachVisitor.java:51)
        	at jadx.core.dex.visitors.debuginfo.DebugInfoAttachVisitor.visit(DebugInfoAttachVisitor.java:41)
         */
        public String toString() {
            StringBuilder append = new StringBuilder().append("FragmentTabHost.SavedState{");
            String hexString = Integer.toHexString(System.identityHashCode(this));
            Log5ECF72.a(hexString);
            LogE84000.a(hexString);
            Log229316.a(hexString);
            return append.append(hexString).append(" curTab=").append(this.curTab).append("}").toString();
        }

        @Override // android.view.View.BaseSavedState, android.view.AbsSavedState, android.os.Parcelable
        public void writeToParcel(Parcel out, int flags) {
            super.writeToParcel(out, flags);
            out.writeString(this.curTab);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public static final class TabInfo {
        final Bundle args;
        final Class<?> clss;
        Fragment fragment;
        final String tag;

        TabInfo(String _tag, Class<?> cls, Bundle _args) {
            this.tag = _tag;
            this.clss = cls;
            this.args = _args;
        }
    }

    @Deprecated
    public FragmentTabHost(Context context) {
        super(context, null);
        this.mTabs = new ArrayList<>();
        initFragmentTabHost(context, null);
    }

    @Deprecated
    public FragmentTabHost(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mTabs = new ArrayList<>();
        initFragmentTabHost(context, attrs);
    }

    private FragmentTransaction doTabChanged(String tag, FragmentTransaction ft) {
        TabInfo tabInfoForTag = getTabInfoForTag(tag);
        if (this.mLastTab != tabInfoForTag) {
            if (ft == null) {
                ft = this.mFragmentManager.beginTransaction();
            }
            TabInfo tabInfo = this.mLastTab;
            if (tabInfo != null && tabInfo.fragment != null) {
                ft.detach(this.mLastTab.fragment);
            }
            if (tabInfoForTag != null) {
                if (tabInfoForTag.fragment == null) {
                    tabInfoForTag.fragment = this.mFragmentManager.getFragmentFactory().instantiate(this.mContext.getClassLoader(), tabInfoForTag.clss.getName());
                    tabInfoForTag.fragment.setArguments(tabInfoForTag.args);
                    ft.add(this.mContainerId, tabInfoForTag.fragment, tabInfoForTag.tag);
                } else {
                    ft.attach(tabInfoForTag.fragment);
                }
            }
            this.mLastTab = tabInfoForTag;
        }
        return ft;
    }

    private void ensureContent() {
        if (this.mRealTabContent == null) {
            FrameLayout frameLayout = (FrameLayout) findViewById(this.mContainerId);
            this.mRealTabContent = frameLayout;
            if (frameLayout == null) {
                throw new IllegalStateException("No tab content FrameLayout found for id " + this.mContainerId);
            }
        }
    }

    private void ensureHierarchy(Context context) {
        if (findViewById(16908307) == null) {
            LinearLayout linearLayout = new LinearLayout(context);
            linearLayout.setOrientation(1);
            addView(linearLayout, new FrameLayout.LayoutParams(-1, -1));
            TabWidget tabWidget = new TabWidget(context);
            tabWidget.setId(16908307);
            tabWidget.setOrientation(0);
            linearLayout.addView(tabWidget, new LinearLayout.LayoutParams(-1, -2, 0.0f));
            FrameLayout frameLayout = new FrameLayout(context);
            frameLayout.setId(16908305);
            linearLayout.addView(frameLayout, new LinearLayout.LayoutParams(0, 0, 0.0f));
            FrameLayout frameLayout2 = new FrameLayout(context);
            this.mRealTabContent = frameLayout2;
            frameLayout2.setId(this.mContainerId);
            linearLayout.addView(frameLayout2, new LinearLayout.LayoutParams(-1, 0, 1.0f));
        }
    }

    private TabInfo getTabInfoForTag(String tabId) {
        int size = this.mTabs.size();
        for (int i = 0; i < size; i++) {
            TabInfo tabInfo = this.mTabs.get(i);
            if (tabInfo.tag.equals(tabId)) {
                return tabInfo;
            }
        }
        return null;
    }

    private void initFragmentTabHost(Context context, AttributeSet attrs) {
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attrs, new int[]{16842995}, 0, 0);
        this.mContainerId = obtainStyledAttributes.getResourceId(0, 0);
        obtainStyledAttributes.recycle();
        super.setOnTabChangedListener(this);
    }

    @Deprecated
    public void addTab(TabHost.TabSpec tabSpec, Class<?> cls, Bundle args) {
        tabSpec.setContent(new DummyTabFactory(this.mContext));
        String tag = tabSpec.getTag();
        TabInfo tabInfo = new TabInfo(tag, cls, args);
        if (this.mAttached) {
            tabInfo.fragment = this.mFragmentManager.findFragmentByTag(tag);
            if (tabInfo.fragment != null && !tabInfo.fragment.isDetached()) {
                FragmentTransaction beginTransaction = this.mFragmentManager.beginTransaction();
                beginTransaction.detach(tabInfo.fragment);
                beginTransaction.commit();
            }
        }
        this.mTabs.add(tabInfo);
        addTab(tabSpec);
    }

    @Override // android.view.ViewGroup, android.view.View
    @Deprecated
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        String currentTabTag = getCurrentTabTag();
        FragmentTransaction fragmentTransaction = null;
        int size = this.mTabs.size();
        for (int i = 0; i < size; i++) {
            TabInfo tabInfo = this.mTabs.get(i);
            tabInfo.fragment = this.mFragmentManager.findFragmentByTag(tabInfo.tag);
            if (tabInfo.fragment != null && !tabInfo.fragment.isDetached()) {
                if (tabInfo.tag.equals(currentTabTag)) {
                    this.mLastTab = tabInfo;
                } else {
                    if (fragmentTransaction == null) {
                        fragmentTransaction = this.mFragmentManager.beginTransaction();
                    }
                    fragmentTransaction.detach(tabInfo.fragment);
                }
            }
        }
        this.mAttached = true;
        FragmentTransaction doTabChanged = doTabChanged(currentTabTag, fragmentTransaction);
        if (doTabChanged != null) {
            doTabChanged.commit();
            this.mFragmentManager.executePendingTransactions();
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    @Deprecated
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.mAttached = false;
    }

    @Override // android.view.View
    @Deprecated
    protected void onRestoreInstanceState(Parcelable state) {
        if (!(state instanceof SavedState)) {
            super.onRestoreInstanceState(state);
            return;
        }
        SavedState savedState = (SavedState) state;
        super.onRestoreInstanceState(savedState.getSuperState());
        setCurrentTabByTag(savedState.curTab);
    }

    @Override // android.view.View
    @Deprecated
    protected Parcelable onSaveInstanceState() {
        SavedState savedState = new SavedState(super.onSaveInstanceState());
        savedState.curTab = getCurrentTabTag();
        return savedState;
    }

    @Override // android.widget.TabHost.OnTabChangeListener
    @Deprecated
    public void onTabChanged(String tabId) {
        FragmentTransaction doTabChanged;
        if (this.mAttached && (doTabChanged = doTabChanged(tabId, null)) != null) {
            doTabChanged.commit();
        }
        TabHost.OnTabChangeListener onTabChangeListener = this.mOnTabChangeListener;
        if (onTabChangeListener != null) {
            onTabChangeListener.onTabChanged(tabId);
        }
    }

    @Override // android.widget.TabHost
    @Deprecated
    public void setOnTabChangedListener(TabHost.OnTabChangeListener l) {
        this.mOnTabChangeListener = l;
    }

    @Override // android.widget.TabHost
    @Deprecated
    public void setup() {
        throw new IllegalStateException("Must call setup() that takes a Context and FragmentManager");
    }

    @Deprecated
    public void setup(Context context, FragmentManager manager) {
        ensureHierarchy(context);
        super.setup();
        this.mContext = context;
        this.mFragmentManager = manager;
        ensureContent();
    }

    @Deprecated
    public void setup(Context context, FragmentManager manager, int containerId) {
        ensureHierarchy(context);
        super.setup();
        this.mContext = context;
        this.mFragmentManager = manager;
        this.mContainerId = containerId;
        ensureContent();
        this.mRealTabContent.setId(containerId);
        if (getId() == -1) {
            setId(16908306);
        }
    }
}
