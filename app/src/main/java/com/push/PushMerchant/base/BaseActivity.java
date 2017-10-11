package com.push.PushMerchant.base;

import android.app.Dialog;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import com.bumptech.glide.Glide;
import com.bumptech.glide.MemoryCategory;
import com.push.PushMerchant.R;
import com.push.PushMerchant.util.DialogHelper;
import com.push.PushMerchant.util.NetworkUtil;
import com.push.PushMerchant.util.ToastUtils;

/**
 * Created by Administrator on 2017/6/19 0019.
 * 所有类的基类
 */

public abstract class BaseActivity extends AppCompatActivity {
    private Dialog mProgressDlg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (null != savedInstanceState) {
            restoreState(savedInstanceState);
        }
        PushApplicationContext.addActivity(this);
        //强制竖屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        //动态调整滑动时的内存占用您的应用程序的某些阶段
        Glide.get(this).setMemoryCategory(MemoryCategory.LOW);
    }

    protected BaseFragment getCurrentFrame() {
        return null;
    }

    public Dialog getProgressDlg(boolean isFinish) {
        if (mProgressDlg == null) {
            mProgressDlg = DialogHelper.showCommonProgressDialog(this, isFinish);
        }
        return mProgressDlg;
    }

    public Dialog getProgressDlg() {
        return getProgressDlg(false);
    }

    public void showProgressDlg() {
        if (getProgressDlg() != null) {
            getProgressDlg().show();
        }
    }

    public void dissProgressDlg() {
        if (mProgressDlg != null && mProgressDlg.isShowing()) {
            mProgressDlg.dismiss();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!NetworkUtil.isNetworkActive()) {
            ToastUtils.showToast(getApplication().getResources().getString(R.string.net_outage));
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Glide.get(this).clearMemory();
        PushApplicationContext.finishActivity(this);
    }

    protected void restoreState(Bundle savedInstanceState) {

    }
    protected abstract int loadViewLayout();
    protected abstract void initDataBeforeView();
    protected abstract void initDataAfterView();
}
