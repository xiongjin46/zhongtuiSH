package com.push.PushMerchant.util;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.AnimationDrawable;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.push.PushMerchant.R;
import com.push.PushMerchant.modules.map.bean.StoreByCityResp;
import com.push.PushMerchant.weight.dialog.DialogPlus;
import com.push.PushMerchant.weight.dialog.OnClickListener;
import com.push.PushMerchant.weight.dialog.ViewHolder;

/**
 * Created by Administrator on 2017/6/19 0019.
 */

public class DialogHelper {
    //dialog宽度
    public static final int DP_VAL = 300;
    private static DialogPlus dialogPlus;

    /**
     * 显示加载dialog
     *
     * @param context
     * @return
     */
    public static Dialog showCommonProgressDialog(final Context context, final boolean isFinish) {
        final Dialog dialog = new Dialog(context, R.style.MyDialogStyle);
        Window window = dialog.getWindow();
        window.setGravity(Gravity.CENTER);
        window.setContentView(R.layout.dialog_layout_progressbar);

        WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = DeviceUtils.dp2px(context, 150);
        window.setAttributes(lp);

        ImageView progressbar_img = (ImageView) window.findViewById(R.id.progressbar_img);
        final AnimationDrawable anim = (AnimationDrawable) context.getResources().getDrawable(R.drawable.anim_loading_progress);
        progressbar_img.setBackgroundDrawable(anim);

        dialog.setCancelable(!isFinish);
        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                if (anim != null)
                    anim.stop();
            }
        });
        dialog.show();

        progressbar_img.post(new Runnable() {
            @Override
            public void run() {
                anim.start();
            }
        });
        return dialog;
    }


    public static void showStoreDiaLog(Context context, final StoreByCityResp.ObjectBean bean, final OnConfirmDiaLogListener1 onConfirmDiaLogListener) {
        final Dialog dialog = new Dialog(context, R.style.MyDialogStyle);
        Window window = dialog.getWindow();
        window.setGravity(Gravity.CENTER | Gravity.BOTTOM);
        dialog.show();
        dialog.setCanceledOnTouchOutside(true);
        dialog.getWindow().setDimAmount(0.6f);
        window.setContentView(R.layout.dialog_layout_store);
        TextView tv_store_name = (TextView) window.findViewById(R.id.tv_store_name);
        TextView tv_store_address = (TextView) window.findViewById(R.id.tv_store_address);
        TextView tv_price = (TextView) window.findViewById(R.id.tv_price);
        TextView tv_sup_size = (TextView) window.findViewById(R.id.tv_sup_size);
        ImageView iv_store = (ImageView) window.findViewById(R.id.iv_store);
        RelativeLayout ll = (RelativeLayout) window.findViewById(R.id.ll);
        ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onConfirmDiaLogListener.onClick(bean.getId());
                dialog.dismiss();
            }
        });
        if (bean.getTimeoutType() == null) {
            tv_sup_size.setVisibility(View.GONE);
        } else if (bean.getTimeoutType() == 0) {
            tv_sup_size.setText("小");
        } else if (bean.getTimeoutType() == 1) {
            tv_sup_size.setText("中小");
        } else if (bean.getTimeoutType() == 2) {
            tv_sup_size.setText("中");
        } else if (bean.getTimeoutType() == 3) {
            tv_sup_size.setText("中大");
        } else {
            tv_sup_size.setText("大");
        }
        ImageLoader.dongdefaultImage(bean.getStoreUrl(), iv_store, R.mipmap.error_icon, 128, 128);
        tv_price.setText("¥ " + bean.getPrice() / 100 + "/" + bean.getManFlow() + "次人");
        tv_store_address.setText(bean.getFullAddress());
        tv_store_name.setText(bean.getName());
        WindowManager.LayoutParams lp = window.getAttributes();
        window.setAttributes(lp);
    }

    public static void showConfirmDiaLog(Context context, String message, OnConfirmDiaLogListener onConfirmDiaLogListener) {
        showConfirmDiaLog(context, null, null, null, message, onConfirmDiaLogListener);
    }

    public static void showConfirmDiaLog(Context context, String title, String enter, String cancle, String message, final OnConfirmDiaLogListener onConfirmDiaLogListener) {
        showConfirmDiaLog(context, title, enter, cancle, message, true, onConfirmDiaLogListener);
    }

    /**
     * @param context
     * @param title
     * @param enter
     * @param cancle
     * @param message
     * @param isCancelable            是否返回取消
     * @param onConfirmDiaLogListener
     */
    public static void showConfirmDiaLog(Context context, String title, String enter, String cancle, String message, final boolean isCancelable, final OnConfirmDiaLogListener onConfirmDiaLogListener) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.dialog_layout_confirm1, null);
        TextView tvTitle = (TextView) inflate.findViewById(R.id.tv_title);
        TextView btn_cancle = (TextView) inflate.findViewById(R.id.btn_cancle);
        TextView btn_enter = (TextView) inflate.findViewById(R.id.btn_enter);
        if (title == null && enter == null && cancle == null) {
            tvTitle.setText("提醒");
            btn_cancle.setText("取消");
            btn_enter.setText("确定");
        } else if (!isCancelable && cancle == null) {
            tvTitle.setText(title);
            btn_enter.setText(enter);
            btn_cancle.setVisibility(View.GONE);
        } else {
            tvTitle.setText(title);
            btn_cancle.setText(cancle);
            btn_enter.setText(enter);
        }
        TextView tvContent = (TextView) inflate.findViewById(R.id.tv_content);
        if (!TextUtils.isEmpty(message)) {
            tvContent.setText(message);
        }
        dialogPlus = DialogPlus.newDialog(context)
                .setGravity(Gravity.CENTER)
                .setContentHolder(new ViewHolder(inflate))
                .setExpanded(false)
                .setCancelable(false)
                .setContentBackgroundResource(R.color.transparent)
                .setContentWidth(DeviceUtils.dp2px(context, DP_VAL))
                .setOverlayBackgroundResource(R.color.colorDialogOverlay)
                .setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(DialogPlus dialog, View view) {
                        switch (view.getId()) {
                            case R.id.btn_enter:
                                onConfirmDiaLogListener.onAccept(dialog);
                                if (isCancelable) {
                                    dialog.dismiss();
                                    dialogPlus = null;
                                }
                                break;
                            case R.id.btn_cancle:
                                onConfirmDiaLogListener.onCancel(dialog);
                                dialog.dismiss();
                                dialogPlus = null;
                                break;
                        }

                    }
                })
                .create();
        dialogPlus.show();
    }

    public static void showDiaLog(Context context, final OnConfirmDiaLogListener2 onConfirmDiaLogListener) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.dialog_layout_confirm, null);
        final EditText et_name = (EditText) inflate.findViewById(R.id.et_name);
        final EditText et_phone = (EditText) inflate.findViewById(R.id.et_phone);
        dialogPlus = DialogPlus.newDialog(context)
                .setGravity(Gravity.CENTER)
                .setContentHolder(new ViewHolder(inflate))
                .setExpanded(false)
                .setCancelable(false)
                .setContentBackgroundResource(R.color.transparent)
                .setContentWidth(DeviceUtils.dp2px(context, DP_VAL))
                .setOverlayBackgroundResource(R.color.colorDialogOverlay)
                .setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(DialogPlus dialog, View view) {
                        switch (view.getId()) {
                            case R.id.btn_enter:
                                onConfirmDiaLogListener.onAccept(dialog,
                                        et_phone.getText().toString(), et_name.getText().toString());
                                dialogPlus = null;
                                break;
                            case R.id.btn_cancle:
                                onConfirmDiaLogListener.onCancel(dialog);
                                dialog.dismiss();
                                dialogPlus = null;
                                break;
                        }

                    }
                })
                .create();
        dialogPlus.show();
    }

    public interface OnConfirmDiaLogListener {

        void onCancel(DialogPlus dialogPlus);

        void onAccept(DialogPlus dialogPlus);
    }

    public interface OnConfirmDiaLogListener1 {
        void onClick(int id);
    }

    public interface OnConfirmDiaLogListener2 {

        void onCancel(DialogPlus dialogPlus);

        void onAccept(DialogPlus dialogPlus, String phone, String name);
    }
}
