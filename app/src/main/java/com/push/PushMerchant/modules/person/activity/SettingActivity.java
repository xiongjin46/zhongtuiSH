package com.push.PushMerchant.modules.person.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.push.PushMerchant.R;
import com.push.PushMerchant.base.BaseResp;
import com.push.PushMerchant.base.BaseToolbarActivity;
import com.push.PushMerchant.base.PushApplicationContext;
import com.push.PushMerchant.constants.IntentFlag;
import com.push.PushMerchant.modules.AccountManager;
import com.push.PushMerchant.modules.OssManager;
import com.push.PushMerchant.modules.person.bean.UserProfile;
import com.push.PushMerchant.network.ThreadManager;
import com.push.PushMerchant.network.engine.UserEngine;
import com.push.PushMerchant.network.impl.UserCallback;
import com.push.PushMerchant.util.ImageLoader;
import com.push.PushMerchant.util.JumpUtil;
import com.push.PushMerchant.util.ToastUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/7/21 0021.
 * 个人资料
 */

public class SettingActivity extends BaseToolbarActivity implements View.OnClickListener, OssManager.OnCompletedCallback {
    @BindView(R.id.tv_store_name)
    TextView tv_store_name;
    @BindView(R.id.tv_phone)
    TextView tv_phone;
    @BindView(R.id.tv_trade)
    TextView tv_trade;
    @BindView(R.id.tv_state)
    TextView tv_state;
    @BindView(R.id.iv_logo)
    ImageView iv_logo;
    @BindView(R.id.tv_store_address)
    TextView tv_store_address;
    private UserEngine userEngine;
    private UserSub userSub;
    private UserProfile.ObjectEntity object;
    private final int PICTURECODE = 0x0001;
    private final int TYPE_NICK = 0x0002;
    private String realName;
    private ArrayList<String> selectedPicture;
    private String iconPath;
    private String trade;
    private String address;

    @Override
    protected int loadViewLayout() {
        return R.layout.ac_my;
    }

    @Override
    protected void initDataBeforeView() {

    }

    @Override
    protected void initDataAfterView() {
        setDefaultBack("");
        setTitleString("个人资料");
        userEngine = new UserEngine();
        userSub = new UserSub();
        bindCallback(userSub, userEngine);
        initData();
    }

    private void initData() {
        object = AccountManager.getInstance().getUserEntity().getObject();
        tv_store_name.setText(TextUtils.isEmpty(object.getRealName()) ? "" : object.getRealName());
        tv_phone.setText(TextUtils.isEmpty(object.getMobile()) ? "" : object.getMobile());
        tv_trade.setText(object.getTrades());
        tv_store_address.setText(object.getAddress());
        ImageLoader.dongdefaultImage(TextUtils.isEmpty(object.getLogoUrl()) ? "" :
                object.getLogoUrl(), iv_logo, R.mipmap.error_icon, 84, 84);
        int state = object.getState();
        if (state == 0) {
            tv_state.setText("审核不通过");
        } else {
            tv_state.setText("审核通过");
        }
    }

    @OnClick({R.id.rl_state, R.id.rl_logo, R.id.rl_name, R.id.rl_trade, R.id.ll_address})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_state:
                if (object.getState() == 1) {
                    return;
                } else {
                    JumpUtil.toAuditData(this);
                }
                break;
            case R.id.rl_logo:
                JumpUtil.toPicture(this, 1, PICTURECODE);
                break;
            case R.id.rl_name:
                JumpUtil.jumpToRemark(this, "名字", tv_store_name.getText().toString().trim(), 20, TYPE_NICK);
                break;
            case R.id.rl_trade:
                JumpUtil.jumpToRemark(this, "行业", tv_trade.getText().toString().trim(), 20, 1003);
                break;
            case R.id.ll_address:
                JumpUtil.jumpToRemark(this, "地址", tv_store_address.getText().toString().trim(), 20, 1004);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == TYPE_NICK) {
                realName = data.getStringExtra(IntentFlag.INTENT_FLAG_OBJ);
                if (realName == null) return;
                tv_store_name.setText(realName);
                showProgressDlg();
                userEngine.updataUser("", "", "", realName);
            } else if (requestCode == PICTURECODE) {
                selectedPicture = (ArrayList<String>) data
                        .getSerializableExtra(IntentFlag.INTENT_SELECTED_PICTURE);
                if (selectedPicture.size() > 0) {
                    showProgressDlg();
                    OssManager.getInstance().uploadHead(selectedPicture.get(0));
                } else {
                    OssManager.getInstance().uploadHead("");
                }
            } else if (requestCode == 1003) {
                trade = data.getStringExtra(IntentFlag.INTENT_FLAG_OBJ);
                if (trade == null) return;
                tv_trade.setText(trade);
                showProgressDlg();
                userEngine.updateMerchant("", "", "", trade);
            } else {
                address = data.getStringExtra(IntentFlag.INTENT_FLAG_OBJ);
                if (address == null) return;
                tv_store_address.setText(address);
                showProgressDlg();
                userEngine.updateMerchant(address, "", "", "");
            }
        }
    }

    @Override
    protected void restoreState(Bundle savedInstanceState) {
        super.restoreState(savedInstanceState);
        selectedPicture = savedInstanceState.getStringArrayList("selectedPicture");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putStringArrayList("selectedPicture", selectedPicture);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        selectedPicture = savedInstanceState.getStringArrayList("selectedPicture");
    }

    @Override
    protected void onResume() {
        super.onResume();
        OssManager.getInstance().registerCallback(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        OssManager.getInstance().unregisterCallback(this);
    }


    @Override
    public void onSuccess(int sort, final String path) {
        iconPath = path;
        ThreadManager.getMainThreadHandler().post(new Runnable() {
            @Override
            public void run() {
                ImageLoader.dongdefaultImage(path, iv_logo, R.mipmap.error_icon, 84, 84);
            }
        });
        userEngine.updataUser("", path, "", "");
    }


    @Override
    public void onFailure(int sort, String errMsg) {
        dissProgressDlg();
        ToastUtils.showToast("修改头像失败 errMsg==" + errMsg + "  sort==" + sort);
    }

    class UserSub extends UserCallback.Stub {
        @Override
        public void onLogOutSuccess(BaseResp rsp) {
            super.onLogOutSuccess(rsp);
            dissProgressDlg();
            AccountManager.getInstance().logout();
            JumpUtil.toLogin(SettingActivity.this);
            PushApplicationContext.closeAllActivitys();
        }

        @Override
        public void onLogOutFail(int errCode, String msg) {
            super.onLogOutFail(errCode, msg);
            dissProgressDlg();
        }

        @Override
        public void onUpdataUserSuccess(BaseResp rsp) {
            super.onUpdataUserSuccess(rsp);
            setResult(RESULT_OK, new Intent());
            isload = false;
            dissProgressDlg();
            UserProfile userEntity = AccountManager.getInstance().getUserEntity();
            UserProfile.ObjectEntity object = userEntity.getObject();
            if (!TextUtils.isEmpty(iconPath)) {
                object.setLogoUrl(iconPath);
            }
            if (!TextUtils.isEmpty(realName)) {
                object.setRealName(realName);
            }
            AccountManager.getInstance().setUserEntity(userEntity);
        }

        @Override
        public void onUpdataUserFail(int errCode, String msg) {
            super.onUpdataUserFail(errCode, msg);
            isload = false;
            dissProgressDlg();
        }

        @Override
        public void onGetMerchantSuccess(BaseResp rsp) {
            super.onGetMerchantSuccess(rsp);
            isload = false;
            dissProgressDlg();
            UserProfile userEntity = AccountManager.getInstance().getUserEntity();
            UserProfile.ObjectEntity object = userEntity.getObject();
            if (!TextUtils.isEmpty(trade)) {
                object.setTrades(trade);
            }
            if (!TextUtils.isEmpty(address)) {
                object.setAddress(address);
            }
            AccountManager.getInstance().setUserEntity(userEntity);
        }

        @Override
        public void onGetMerchantFail(int errCode, String msg) {
            super.onGetMerchantFail(errCode, msg);
            isload = false;
            dissProgressDlg();
        }
    }
}
