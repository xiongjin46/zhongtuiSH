package com.push.PushMerchant.modules.person.activity;

import com.push.PushMerchant.R;
import com.push.PushMerchant.base.BaseToolbarActivity;

/**
 * Created by Administrator on 2017/7/24 0024.
 */

public class DataInformationActivity extends BaseToolbarActivity {
    @Override
    protected int loadViewLayout() {
        return R.layout.ac_data_information;
    }

    @Override
    protected void initDataBeforeView() {

    }

    @Override
    protected void initDataAfterView() {
        setDefaultBack("");
        setTitleString("资料信息");
    }

}
