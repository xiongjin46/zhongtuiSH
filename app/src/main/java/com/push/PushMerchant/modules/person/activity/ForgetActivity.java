package com.push.PushMerchant.modules.person.activity;

import android.graphics.Color;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.push.PushMerchant.R;
import com.push.PushMerchant.base.BaseResp;
import com.push.PushMerchant.base.BaseToolbarActivity;
import com.push.PushMerchant.network.engine.UserEngine;
import com.push.PushMerchant.network.impl.UserCallback;
import com.push.PushMerchant.util.CommonUtil;
import com.push.PushMerchant.util.DeviceUtils;
import com.push.PushMerchant.util.Md5Utils;
import com.push.PushMerchant.util.ToastUtils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/6/26 0026.
 * 忘记密码
 */

public class ForgetActivity extends BaseToolbarActivity implements View.OnClickListener {
    @BindView(R.id.tv_regist_getNum)
    TextView GetNum;
    @BindView(R.id.et_regist_num)
    TextView et_regist_num;
    @BindView(R.id.et_regist_psw)
    TextView et_regist_psw;
    @BindView(R.id.et_code)
    EditText etCode;
    private CountDownTimer countDownTimer;
    private UserEngine mUserEngine;
    private UserSub mUserSub;

    @Override
    protected int loadViewLayout() {
        return R.layout.ac_forget;
    }

    @Override
    protected void initDataBeforeView() {

    }

    @Override
    protected void initDataAfterView() {
        setDefaultBack("");
        setTitleBackground(0);
        DeviceUtils.setTvColorAlpha(toolbar_back_tv, Color.WHITE, Color.BLACK, 0);
        mUserEngine = new UserEngine();
        mUserSub = new UserSub();
        bindCallback(mUserSub, mUserEngine);
    }

    @OnClick({R.id.tv_regist_getNum, R.id.tv_save})
    public void onClick(View view) {
        String registNum = et_regist_num.getText().toString();
        String code = etCode.getText().toString();
        String psw = et_regist_psw.getText().toString();
        switch (view.getId()) {
            case R.id.tv_regist_getNum:
                if (TextUtils.isEmpty(registNum)) {
                    Toast.makeText(this, "电话号码不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                GetNum.setEnabled(false);
                countDownTimer = CommonUtil.countDownTimer(GetNum);
                countDownTimer.start();
                mUserEngine.getCapcha(registNum, UserEngine.CapchaType_NoPsd);
                break;
            case R.id.tv_save:
                if (TextUtils.isEmpty(registNum)) {
                    ToastUtils.showToast("电话号码不能为空");
                    return;
                }
                if (TextUtils.isEmpty(code)) {
                    ToastUtils.showToast("验证码不能为空");
                    return;
                }
                if (TextUtils.isEmpty(psw)) {
                    ToastUtils.showToast("密码不能为空");
                    return;
                }
                showProgressDlg();
                mUserEngine.getForgitPsd(registNum, code, Md5Utils.encode(psw));
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (countDownTimer == null) {
            return;
        }
        countDownTimer.cancel();
    }

    class UserSub extends UserCallback.Stub {

        @Override
        public void onGetForgitPasSuccess(BaseResp rsp) {
            super.onGetForgitPasSuccess(rsp);
            dissProgressDlg();
            ToastUtils.showToast("修改成功");
            finish();
        }

        @Override
        public void onGetForgitPasFail(int errCode, String msg) {
            super.onGetForgitPasFail(errCode, msg);
            dissProgressDlg();
        }
    }
}
