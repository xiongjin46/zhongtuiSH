package com.push.PushMerchant.modules.person.activity;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.push.PushMerchant.R;
import com.push.PushMerchant.base.BaseToolbarActivity;
import com.push.PushMerchant.constants.IntentFlag;
import com.push.PushMerchant.util.ToastUtils;

import butterknife.BindView;

/**
 * @ClassName: AddRemarkActivity
 * @Description: 编辑信息
 * @author: YYL
 * <p>
 * create at 2016/11/22 14:25
 */

public class AddRemarkActivity extends BaseToolbarActivity {
    @BindView(R.id.id_product_name)
    EditText idProductName;
    @BindView(R.id.id_confirm)
    TextView idConfirm;
    private String title;
    private int maxLeng;
    private String defContent;

    @Override
    protected void initDataBeforeView() {
        title = getIntent().getStringExtra(IntentFlag.TITLE_STRING);
        maxLeng = getIntent().getIntExtra(IntentFlag.INTENT_FLAG_COUNT, 40);
        defContent = getIntent().getStringExtra(IntentFlag.REMARK);
    }

    @Override
    protected int loadViewLayout() {
        return R.layout.ac_add_remark;
    }


    @Override
    protected void initDataAfterView() {
        setDefaultBack("");
        setTitleString(title);
        String format = getResources().getString(R.string.tv_remark);
        String result = String.format(format, title);
        idProductName.setHint(result);
        if (!TextUtils.isEmpty(defContent)) {
            idProductName.setText(defContent);
            idProductName.setSelection(defContent.length());
        }

        idConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(idProductName.getText().toString().trim())) {
                    ToastUtils.showToast(title + "不可为空");
                    return;

                } else if (idProductName.getText().toString().trim().length() > maxLeng) {
                    ToastUtils.showToast(title + "长度不可超过" + maxLeng);
                    return;
                } else {
                    Intent intent = new Intent();
                    intent.putExtra(IntentFlag.INTENT_FLAG_OBJ, idProductName.getText().toString().trim());
                    setResult(RESULT_OK, intent);
//                    UserProfile userEntity = AccountManager.getInstance().getUserEntity();
//                    if (userEntity != null) {
//                        userEntity.getObject().setRealName(idProductName.getText().toString().trim());
//                        AccountManager.getInstance().setUserEntity(userEntity);
//                    }
                }
                finish();
            }
        });
    }

}
