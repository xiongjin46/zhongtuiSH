package com.push.PushMerchant.modules.person.activity;

import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import com.push.PushMerchant.R;
import com.push.PushMerchant.base.BaseToolbarActivity;
import com.push.PushMerchant.constants.IntentFlag;
import com.push.PushMerchant.modules.AccountManager;
import com.push.PushMerchant.modules.person.bean.UserProfile;
import com.push.PushMerchant.network.engine.UserEngine;
import com.push.PushMerchant.network.impl.UserCallback;
import com.push.PushMerchant.util.DeviceUtils;
import com.push.PushMerchant.util.JumpUtil;
import com.push.PushMerchant.util.SharePrefUtil;
import com.push.PushMerchant.util.ToastUtils;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/6/24 0024.
 * 登录
 */

public class LoginActivity extends BaseToolbarActivity implements View.OnClickListener {
    @BindView(R.id.tv_login)
    TextView tvLogin;
    @BindView(R.id.tv_login_regist)
    TextView tvLoginRegist;
    @BindView(R.id.et_login_num)
    EditText etLoginNum;
    @BindView(R.id.et_login_psw)
    EditText etLoginPsw;
    private UserEngine mUserEngine;
    private UserSub mUserSub;

    @Override
    protected int loadViewLayout() {
        return R.layout.ac_login;
    }

    @Override
    protected void initDataBeforeView() {

    }

    @Override
    protected void initDataAfterView() {
        hintBar1();
        mUserEngine = new UserEngine();
        UserSub mUserSub = new UserSub();
        bindCallback(mUserSub, mUserEngine);
    }

    @OnClick({R.id.tv_login_regist, R.id.tv_login, R.id.tv_login_forget})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_login:
                String LoginNum = etLoginNum.getText().toString().trim();
                String LoginPsw = etLoginPsw.getText().toString().trim();
                if (TextUtils.isEmpty(LoginNum)) {
                    ToastUtils.showToast("用户名不能为空");
                    return;
                }
                if (TextUtils.isEmpty(LoginPsw)) {
                    ToastUtils.showToast("密码不能为空");
                    return;
                }
                showProgressDlg();
                mUserEngine.login(LoginNum, LoginPsw);
                break;
            case R.id.tv_login_regist:
                JumpUtil.toRegist(this);
                break;
            case R.id.tv_login_forget:
                JumpUtil.toForgetPsw(this);
                break;
        }
    }

    class UserSub extends UserCallback.Stub {
        @Override
        public void onLoginSuccess(UserProfile rsp) {
            super.onLoginSuccess(rsp);
            dissProgressDlg();
            AccountManager.getInstance().setUserEntity(rsp);
            SharePrefUtil.putBooleanSetting(IntentFlag.OUTTOKEN, false);
            JumpUtil.toHome(LoginActivity.this);
            DeviceUtils.hideKeyboard(LoginActivity.this);
            finish();
        }

        @Override
        public void onLoginFail(int errCode, String msg) {
            super.onLoginFail(errCode, msg);
            dissProgressDlg();
            ToastUtils.showToast(msg);
        }
    }
}
