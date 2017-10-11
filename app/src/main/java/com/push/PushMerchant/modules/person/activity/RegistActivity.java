package com.push.PushMerchant.modules.person.activity;

import android.graphics.Color;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.push.PushMerchant.R;
import com.push.PushMerchant.base.BaseToolbarActivity;
import com.push.PushMerchant.util.CommonUtil;
import com.push.PushMerchant.util.DeviceUtils;
import com.push.PushMerchant.util.JumpUtil;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/6/24 0024.
 * 注册
 */

public class RegistActivity extends BaseToolbarActivity implements View.OnClickListener{
    @BindView(R.id.tv_regist_getNum)
    TextView tvGetNum;
    @BindView(R.id.et_regist_num)
    TextView etRegistNum;
    @BindView(R.id.et_regist_psw)
    TextView etRegistPsw;
    private CountDownTimer countDownTimer;

    @Override
    protected void initDataBeforeView() {

    }

    @Override
    protected int loadViewLayout() {
        return R.layout.ac_regist;
    }

    @Override
    protected void initDataAfterView() {
        setTitleString("免费注册");
        setDefaultBack("");
        DeviceUtils.setTvColorAlpha(toolbar_back_tv, Color.WHITE, Color.BLACK, 0);
        DeviceUtils.setTvColorAlpha(toolbar_back_last_tv, Color.WHITE, Color.BLACK, 0);
    }

    @OnClick({R.id.tv_regist_getNum,R.id.tv_next})
    public void onClick(View view) {
        String registNum = etRegistNum.getText().toString().trim();
        switch (view.getId()){
            case R.id.tv_regist_getNum:
                if (TextUtils.isEmpty(registNum)){
                    Toast.makeText(this,"电话号码不能为空",Toast.LENGTH_SHORT).show();
                    return;
                }
                tvGetNum.setEnabled(false);
                countDownTimer = CommonUtil.countDownTimer(tvGetNum);
                countDownTimer.start();
                break;
            case R.id.tv_next:
                JumpUtil.toAuditData(this);
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
}
