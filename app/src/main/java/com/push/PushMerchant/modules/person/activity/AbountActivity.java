package com.push.PushMerchant.modules.person.activity;

import android.widget.ImageView;
import android.widget.TextView;

import com.push.PushMerchant.R;
import com.push.PushMerchant.base.BaseToolbarActivity;
import com.push.PushMerchant.constants.GConstants;
import com.push.PushMerchant.util.Global;
import com.push.PushMerchant.util.JumpUtil;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/7/21 0021.
 */

public class AbountActivity extends BaseToolbarActivity {
    @BindView(R.id.about_round)
    ImageView aboutRound;
    @BindView(R.id.app_version)
    TextView appVersion;
    @BindView(R.id.app_content)
    TextView appContent;
    @BindView(R.id.app_proto)
    TextView appProto;

    @Override
    protected int loadViewLayout() {
        return R.layout.ac_about;
    }

    @Override
    protected void initDataBeforeView() {

    }

    @Override
    protected void initDataAfterView() {
        setDefaultBack("");
        setTitleString("关于我们");
        appVersion.setText("版本：" + Global.getVersionName(this));
    }

    @OnClick(R.id.app_proto)
    public void onClick() {
        JumpUtil.toBrowser(this, GConstants.AGREEMENT);
    }
}
