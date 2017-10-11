package com.push.PushMerchant.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.push.PushMerchant.R;


/**
 * @ClassName: EmptyView
 * @Description:
 * @author: YYL
 * <p>
 * create at 2016/11/17 10:30
 */
public class EmptyView extends LinearLayout implements View.OnClickListener {


    private ImageView icon;

    /**
     * 回调监听
     */
    public interface OnClickEmptyListener {
        /**
         * 重新请求或者别的操作
         */
        void onClickRecon();
    }


    private Button btn_recon;
    private TextView tv_hint;

    private OnClickEmptyListener mOnClickEmptyListener;

    public void setOnClickEmptyListener(OnClickEmptyListener mOnClickEmptyListener) {
        this.mOnClickEmptyListener = mOnClickEmptyListener;
    }

    public EmptyView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public EmptyView(Context context) {
        super(context);
        init(context);
    }

    public EmptyView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.view_empty, this, true);
        icon = (ImageView) findViewById(R.id.icon);
        btn_recon = (Button) findViewById(R.id.btn_reconnect);
        tv_hint = (TextView) findViewById(R.id.tv_hint);
        btn_recon.setOnClickListener(this);
    }

    public void setHint(String text) {
        tv_hint.setText(text);
    }

    public void setHint(int res) {
        setHint(getResources().getString(res));
    }

    public void setIcon(int res) {
        icon.setImageResource(res);
    }

    public void setBtn_recon(int res) {
        btn_recon.setText(res);
    }

    @Override
    public void onClick(View v) {

        if (mOnClickEmptyListener == null) {
            return;
        }
        switch (v.getId()) {
            case R.id.btn_reconnect:
                mOnClickEmptyListener.onClickRecon();
                break;
            default:
                break;
        }
    }

}
