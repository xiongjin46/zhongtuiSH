package com.push.PushMerchant.modules.person.activity;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import com.push.PushMerchant.R;
import com.push.PushMerchant.base.BaseResp;
import com.push.PushMerchant.base.BaseToolbarActivity;
import com.push.PushMerchant.constants.IntentFlag;
import com.push.PushMerchant.modules.OssManager;
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
 * Created by Administrator on 2017/7/24 0024.\
 * 审核资料
 */

public class AuditData1Activity extends BaseToolbarActivity implements View.OnClickListener, OssManager.OnCompletedCallback {
    @BindView(R.id.iv_1)
    ImageView iv_1;
    @BindView(R.id.iv_2)
    ImageView iv_2;
    @BindView(R.id.rl_1)
    RelativeLayout rl_1;
    @BindView(R.id.rl_2)
    RelativeLayout rl_2;
    private final int PICTURECODE = 0x0001;
    private final int TYPE_NICK = 0x0002;
    private ArrayList<String> selectedPicture;
    private ArrayList<String> selectedPicture2;
    private String iconPath;
    private String iconPath2;
    private int tag;
    private UserEngine userEngine;
    private UserSub userSub;

    @Override
    protected int loadViewLayout() {
        return R.layout.ac_audit_data1;
    }

    @Override
    protected void initDataBeforeView() {

    }

    @Override
    protected void initDataAfterView() {
        setTitleString("资料审核");
        setDefaultBack("");
        userEngine = new UserEngine();
        userSub = new UserSub();
        bindCallback(userSub, userEngine);
    }

    @OnClick({R.id.ll_1, R.id.ll_2, R.id.tv_reflect})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_1:
                JumpUtil.toPicture(this, 1, PICTURECODE);
                break;
            case R.id.ll_2:
                JumpUtil.toPicture(this, 1, TYPE_NICK);
                break;
            case R.id.tv_reflect:
                showProgressDlg();
                userEngine.SaveMerchantInfo(iconPath, iconPath2);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == PICTURECODE) {
                tag = 1;
                rl_1.setVisibility(View.GONE);
                selectedPicture = (ArrayList<String>) data
                        .getSerializableExtra(IntentFlag.INTENT_SELECTED_PICTURE);
                if (selectedPicture.size() > 0) {
                    showProgressDlg();
                    OssManager.getInstance().uploadHead(selectedPicture.get(0));
                } else {
                    OssManager.getInstance().uploadHead("");
                }
            } else if (requestCode == TYPE_NICK) {
                tag = 2;
                rl_2.setVisibility(View.GONE);
                selectedPicture2 = (ArrayList<String>) data
                        .getSerializableExtra(IntentFlag.INTENT_SELECTED_PICTURE);
                if (selectedPicture.size() > 0) {
                    showProgressDlg();
                    OssManager.getInstance().uploadHead(selectedPicture2.get(0));
                } else {
                    OssManager.getInstance().uploadHead("");
                }
            }
        }
    }

    @Override
    public void onSuccess(int sort, final String path) {
        dissProgressDlg();
        if (tag == 1) {
            iconPath = path;
            ThreadManager.getMainThreadHandler().post(new Runnable() {
                @Override
                public void run() {
                    ImageLoader.load(iconPath, iv_1, AuditData1Activity.this);
                }
            });
        } else {
            iconPath2 = path;
            ThreadManager.getMainThreadHandler().post(new Runnable() {
                @Override
                public void run() {
                    ImageLoader.load(iconPath2, iv_2, AuditData1Activity.this);
                }
            });
        }
    }

    @Override
    public void onFailure(int sort, String errMsg) {
        dissProgressDlg();
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

    class UserSub extends UserCallback.Stub {

        @Override
        public void onGetSaveMerchatSuccess(BaseResp rsp) {
            super.onGetSaveMerchatSuccess(rsp);
            dissProgressDlg();
            ToastUtils.showToast("上传成功");
            finish();
        }

        @Override
        public void onGetSaveMerchatFail(int errCode, String msg) {
            super.onGetSaveMerchatFail(errCode, msg);
            dissProgressDlg();
        }
    }
}
