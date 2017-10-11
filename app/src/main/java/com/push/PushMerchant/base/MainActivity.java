package com.push.PushMerchant.base;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.push.PushMerchant.R;
import com.push.PushMerchant.constants.IntentFlag;
import com.push.PushMerchant.modules.AccountManager;
import com.push.PushMerchant.modules.advert.fragment.AdvertFragment;
import com.push.PushMerchant.modules.home.bean.AppVersionBean;
import com.push.PushMerchant.modules.map.fragment.MapFragment;
import com.push.PushMerchant.modules.person.fragment.PersonalFragment;
import com.push.PushMerchant.modules.home.fragment.HomeFragment;
import com.push.PushMerchant.util.DialogHelper;
import com.push.PushMerchant.util.FragmentController;
import com.push.PushMerchant.util.Global;
import com.push.PushMerchant.util.SharePrefUtil;
import com.push.PushMerchant.view.MyRadioButton;
import com.push.PushMerchant.view.handler.EventEnum;
import com.push.PushMerchant.view.handler.UIEventListener;
import com.push.PushMerchant.weight.dialog.DialogPlus;


public class MainActivity extends BaseToolbarActivity implements UIEventListener {

    private MyRadioButton rb_tab1;
    private MyRadioButton rb_tab2;
    private MyRadioButton rb_tab3;
    private MyRadioButton rb_tab4;
    private RadioGroup radioGroup;
    private String[] fragmentTags = {"home", " task", "team", "personal"};
    private Class[] cla = {HomeFragment.class, AdvertFragment.class, MapFragment.class, PersonalFragment.class};
    private FragmentController mFragmentController;
    private long exitTime = 0;
    private AppVersionBean appVersion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();
    }

    @Override
    protected int loadViewLayout() {
        return 0;
    }

    @Override
    protected void initDataBeforeView() {
    }

    @Override
    protected void initDataAfterView() {

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
    }


    private void initData() {
        if (appVersion == null) {
            updataVision();
        }
    }

    private void updataVision() {
        //根据application中的缓存APPversion信息判断升级与否
        appVersion = PushApplicationContext.appVersion;
        if (PushApplicationContext.appVersion != null) {
            String versionName = PushApplicationContext.appVersion.getShAppVersion();
            int versionCode = PushApplicationContext.appVersion.getShVersionCode();
            String spVersion = SharePrefUtil.getStringSetting(IntentFlag.CODE_TYPE, "");

            if (!versionName.equals(Global.getVersionName(this)) && !versionName.equals(spVersion) && versionCode > Global.getVersionCode(this)) {
                DialogHelper.showConfirmDiaLog(MainActivity.this, "发现新版本:\n" + PushApplicationContext.appVersion.getShAppVersion(), "立即更新", PushApplicationContext.appVersion.getIsForce() == 0 ? "暂不更新" : null, PushApplicationContext.appVersion.getShAppDescription(), PushApplicationContext.appVersion.getIsForce() == 0, new DialogHelper.OnConfirmDiaLogListener() {
                    @Override
                    public void onCancel(DialogPlus dialogPlus) {
//                        SharePrefUtil.putBooleanSetting(IntentFlag.INTENT_FLAG_TYPE, false);
                        SharePrefUtil.putStringSetting(IntentFlag.CODE_TYPE, PushApplicationContext.appVersion.getAppVersion());
                    }

                    @Override
                    public void onAccept(DialogPlus dialogPlus) {
                        if (null != PushApplicationContext.appVersion) {
                            String url = PushApplicationContext.appVersion.getShDownloadUrl();
                            if (TextUtils.isEmpty(url)) return;
                            Uri uri = Uri.parse(url);
                            SharePrefUtil.putStringSetting(IntentFlag.CODE_TYPE, "");
                            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                            startActivity(intent);
                            //强制更新之后重新登录
                            if (PushApplicationContext.appVersion.getIsForce() == 1) {
                                if (!Global.isDev()) {
                                    AccountManager.getInstance().logout();
                                }
                            }
                        }
                    }
                });
            }
        }
    }

    private void initView() {
        mFragmentController = new FragmentController(getSupportFragmentManager(), R.id.realtabcontent,
                fragmentTags, this);
        rb_tab1 = (MyRadioButton) findViewById(R.id.rb_tab1);
        rb_tab2 = (MyRadioButton) findViewById(R.id.rb_tab2);
        rb_tab3 = (MyRadioButton) findViewById(R.id.rb_tab3);
        rb_tab4 = (MyRadioButton) findViewById(R.id.rb_tab4);
        radioGroup = (RadioGroup) findViewById(R.id.rg);
        rb_tab1.setChecked(true);
        mFragmentController.add(AdvertFragment.class, fragmentTags[1], null);
        mFragmentController.add(HomeFragment.class, fragmentTags[0], null);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_tab1:
                        setFragment(0);
                        break;
                    case R.id.rb_tab2:
                        setFragment(1);
                        break;
                    case R.id.rb_tab3:
                        setFragment(2);
                        break;
                    case R.id.rb_tab4:
                        setFragment(3);
                        break;
                }
            }
        });
    }

    public void setFragment(int tag) {
        mFragmentController.add(cla[tag], fragmentTags[tag], null);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                onBackPressed();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onResume() {
        super.onResume();
        PushApplicationContext.sEventController.addUIEventListener(EventEnum.EVENT_MESSAGE_PRIVATE, this);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        PushApplicationContext.sEventController.removeUIEventListener(EventEnum.EVENT_MESSAGE_PRIVATE, this);
    }

    @Override
    public void handleUIEvent(Message msg) {
        rb_tab2.setChecked(true);
    }
}
