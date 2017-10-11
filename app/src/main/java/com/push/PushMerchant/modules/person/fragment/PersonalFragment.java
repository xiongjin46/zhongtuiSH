package com.push.PushMerchant.modules.person.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.push.PushMerchant.R;
import com.push.PushMerchant.base.BaseFragment;
import com.push.PushMerchant.base.BaseResp;
import com.push.PushMerchant.base.BaseToolbarActivity;
import com.push.PushMerchant.constants.GConstants;
import com.push.PushMerchant.modules.AccountManager;
import com.push.PushMerchant.modules.person.bean.PersonBeanResp;
import com.push.PushMerchant.modules.person.bean.UserProfile;
import com.push.PushMerchant.network.engine.UserEngine;
import com.push.PushMerchant.network.impl.UserCallback;
import com.push.PushMerchant.util.ImageLoader;
import com.push.PushMerchant.util.JumpUtil;
import com.push.PushMerchant.util.SharePrefUtil;

import butterknife.BindView;
import butterknife.OnClick;

import static android.app.Activity.RESULT_OK;

/**
 * Created by Administrator on 2017/6/19 0019.
 * 我的
 */

public class PersonalFragment extends BaseFragment implements View.OnClickListener {
    @BindView(R.id.iv_bg)
    ImageView iv_bg;
    @BindView(R.id.iv_head)
    ImageView iv_head;
    @BindView(R.id.tv_name)
    TextView tv_name;
    private UserEngine userEngine;
    private UserSub userSub;
    private final static int TOUSERSETTING = 0x0001;    //用户设置

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fg_person;
    }

    @Override
    protected void initBeforeView() {

    }

    @Override
    protected void initAfterView() {
        ((BaseToolbarActivity) mActivity).hintBar1();
        userEngine = new UserEngine();
        userSub = new UserSub();
        ((BaseToolbarActivity) mActivity).bindCallback(userSub, userEngine);

        String mAccessToken = SharePrefUtil.getStringUser(GConstants.KEY_ACCESSTOKEN, "");
        if (TextUtils.isEmpty(mAccessToken)) {
            JumpUtil.toLogin(mActivity);
        }
        initData();
    }

    private void initData() {
        UserProfile.ObjectEntity bean = AccountManager.getInstance().getUserEntity().getObject();
        tv_name.setText(TextUtils.isEmpty(bean.getRealName()) ? "" : bean.getRealName());
        ImageLoader.loadSmallRoundImage(TextUtils.isEmpty(bean.getLogoUrl()) ? "" : bean.getLogoUrl(),
                iv_head, mActivity);
        ImageLoader.load(TextUtils.isEmpty(bean.getLogoUrl()) ? "" : bean.getLogoUrl(), iv_bg, mActivity);
    }

    @OnClick({R.id.rl_my_detail, R.id.rl_my_project, R.id.rl_abount, R.id.rl_login_out})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_my_detail:
                JumpUtil.toMyDetail(PersonalFragment.this, TOUSERSETTING);
                break;
            case R.id.rl_my_project:
                JumpUtil.toMyProject(mActivity);
                break;
            case R.id.rl_abount:
                JumpUtil.toAbout(mActivity);
                break;
            case R.id.rl_login_out:
                ((BaseToolbarActivity) mActivity).showProgressDlg();
                userEngine.logout();
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == TOUSERSETTING) {
                String photoUrl = AccountManager.getInstance().getUserEntity().getObject().getLogoUrl();
                ImageLoader.loadSmallRoundImage(!TextUtils.isEmpty(photoUrl) ? photoUrl : "", iv_head, mActivity);
                String realName = AccountManager.getInstance().getUserEntity().getObject().getRealName();
                tv_name.setText(realName);
                ImageLoader.load(!TextUtils.isEmpty(photoUrl) ? photoUrl : "", iv_bg, mActivity);
            }
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            String mAccessToken = SharePrefUtil.getStringUser(GConstants.KEY_ACCESSTOKEN, "");
            if (TextUtils.isEmpty(mAccessToken)) {
                JumpUtil.toLogin(mActivity);
            } else {
                isload = true;
                userEngine.updateSession();
            }
        }
    }

    private void initUser() {
        UserProfile userEntity = AccountManager.getInstance().getUserEntity();
        if (userEntity != null && userEntity.getObject() != null) {
            String photoUrl = userEntity.getObject().getLogoUrl();
            String realName = userEntity.getObject().getRealName();
            ImageLoader.loadSmallRoundImage(!TextUtils.isEmpty(photoUrl) ? photoUrl : "", iv_head, mActivity);
            ImageLoader.load(!TextUtils.isEmpty(photoUrl) ? photoUrl : "", iv_bg, mActivity);
            tv_name.setText(realName);
        }
    }

    class UserSub extends UserCallback.Stub {
        @Override
        public void onLogOutSuccess(BaseResp rsp) {
            super.onLogOutSuccess(rsp);
            ((BaseToolbarActivity) mActivity).dissProgressDlg();
            AccountManager.getInstance().logout();
            JumpUtil.toLogin(mActivity);
        }

        @Override
        public void onLogOutFail(int errCode, String msg) {
            super.onLogOutFail(errCode, msg);
        }

        @Override
        public void onUpdateSessionSuccess(PersonBeanResp rsp) {
            super.onUpdateSessionSuccess(rsp);
            isload = false;
            ((BaseToolbarActivity) mActivity).dissProgressDlg();
            initUser();
        }

        @Override
        public void onUpdateSessionFail(int errCode, String msg) {
            super.onUpdateSessionFail(errCode, msg);
            isload = false;
            ((BaseToolbarActivity) mActivity).dissProgressDlg();
        }
    }

}
