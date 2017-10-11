package com.push.PushMerchant.modules.person.activity;


import android.webkit.WebView;
import android.widget.ImageView;

import com.push.PushMerchant.R;
import com.push.PushMerchant.base.BaseToolbarActivity;
import com.push.PushMerchant.constants.GConstants;
import com.push.PushMerchant.util.ImageLoader;

import butterknife.BindView;

/**
 * Created by Administrator on 2017/7/20 0020.
 */

public class MaterielDetailActivity extends BaseToolbarActivity {
    private String name;
    @BindView(R.id.iv_materiel)
    ImageView iv_materiel;
    @BindView(R.id.materiel_webview)
    WebView materiel_webview;
    private String pic;
    private String introduce;

    @Override
    protected int loadViewLayout() {
        return R.layout.ac_materiel_detail;
    }

    @Override
    protected void initDataBeforeView() {
        name = getIntent().getStringExtra("name");
        pic = getIntent().getStringExtra("pic");
        introduce = getIntent().getStringExtra("introduce");
    }

    @Override
    protected void initDataAfterView() {
        setDefaultBack("");
        setTitleString(name);
        ImageLoader.load(pic, iv_materiel, this);
        materiel_webview.loadDataWithBaseURL(null, GConstants.HTML_KEY + introduce +
                "</body></html>", "text/html", "UTF-8", null);
    }
}
