package com.push.PushMerchant.base;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.push.PushMerchant.R;
import com.push.PushMerchant.weight.impl.IRefreshListener;
import com.push.PushMerchant.weight.impl.ITitleBar;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

/**
 * Fragment
 */
public abstract class BaseFragment extends Fragment implements IRefreshListener,ITitleBar {
    protected Activity mActivity;
    protected PtrFrameLayout frame;
    protected PtrClassicFrameLayout ptrFrame;
    private ViewGroup container;
    private View mView;
    private Unbinder unbinder;
    private Bundle saveInstanceState;
    protected int page = 1;
    protected boolean isload;
    protected boolean hasNext;
    protected PtrClassicFrameLayout ptr;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = (Activity) context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.saveInstanceState = savedInstanceState;
        initBeforeView();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflateView(inflater);
        this.container = container;
        // fragmenttabhost 每次切换都要重走
        ViewGroup parent = (ViewGroup) rootView.getParent();
        if (parent != null) {
            parent.removeView(rootView);
        }
        return rootView;
    }

    protected View inflateView(LayoutInflater inflater) {
        if (mView == null) {
            mView = inflater.inflate(getLayoutId(), null);
        }
        return mView;
    }

    protected Bundle getSavedInstanceState() {
        return saveInstanceState;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        unbinder = ButterKnife.bind(this, view);
        initAfterView();

        ptr = (PtrClassicFrameLayout) view.findViewById(R.id.fragment_rotate_header_with_view_group_frame);
        if (ptr != null) {
            ptr.setPtrHandler(new PtrHandler() {
                @Override
                public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                    return canDoRefresh();
                }

                @Override
                public void onRefreshBegin(PtrFrameLayout fram) {
                    frame = fram;
                    refreshBegin(fram);
                }
            });
        }
    }

    @Override
    public View initRefresh(View v) {
        ViewGroup.LayoutParams params = v.getLayoutParams();
        ViewGroup ptrClassicFrameLayout = ((BaseToolbarActivity) mActivity).toolBarHelper.getmPtr();
        LinearLayout view_group_ll = (LinearLayout) ptrClassicFrameLayout.findViewById(R.id.view_group_ll);
        ptrFrame = (PtrClassicFrameLayout) ptrClassicFrameLayout.findViewById(R.id.fragment_rotate_header_with_view_group_frame);
        if (params != null) {
            container.removeView(ptrClassicFrameLayout);
            container.removeView(v);
        }
        view_group_ll.removeAllViews();
        view_group_ll.addView(v);
        ptrFrame.setPtrHandler(new PtrHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return canDoRefresh();
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout fram) {
                frame = fram;
                refreshBegin(fram);
            }
        });
        if (params != null) {
            container.addView(ptrClassicFrameLayout, params);
        }
        return ptrClassicFrameLayout;
    }

    @Override
    public boolean canDoRefresh() {
        return false;
    }

    @Override
    public void refreshBegin(PtrFrameLayout frame) {

    }

    @Override
    public void onBackButtonClick() {

    }

    @Override
    public void onFun2ButtonClick() {

    }

    @Override
    public void onFun3ButtonClick() {

    }

    @Override
    public int getFun2ButtonResource() {
        return 0;
    }

    @Override
    public int getFun3ButtonResource() {
        return 0;
    }

    @Override
    public String getFun2TextString() {
        return null;
    }

    @Override
    public void onFun2TextClick() {

    }

    @Override
    public String getTitleString() {
        return null;
    }

    @Override
    public int getTitleResource() {
        return 0;
    }

    @Override
    public void onEditorActionListener(TextView v) {

    }

    @Override
    public void addViewToTitleBar(View view, ViewGroup.LayoutParams params) {

    }

    public abstract int getLayoutId();
    protected abstract void initBeforeView();
    protected abstract void initAfterView();
}
