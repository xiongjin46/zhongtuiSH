package com.push.PushMerchant.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import com.push.PushMerchant.base.MainActivity;
import com.push.PushMerchant.constants.IntentFlag;
import com.push.PushMerchant.modules.advert.activity.AdvertDetailActivity;
import com.push.PushMerchant.modules.map.activity.CityActivty;
import com.push.PushMerchant.modules.person.activity.AbountActivity;
import com.push.PushMerchant.modules.person.activity.AddRemarkActivity;
import com.push.PushMerchant.modules.person.activity.AuditData1Activity;
import com.push.PushMerchant.modules.person.activity.AuditDataActivity;
import com.push.PushMerchant.modules.person.activity.DataInformationActivity;
import com.push.PushMerchant.modules.person.activity.ForgetActivity;
import com.push.PushMerchant.modules.person.activity.LoginActivity;
import com.push.PushMerchant.modules.person.activity.MaterielDetailActivity;
import com.push.PushMerchant.modules.person.activity.ProjectDetailActivity;
import com.push.PushMerchant.modules.person.activity.SettingActivity;
import com.push.PushMerchant.modules.person.activity.MyProjectActivity;
import com.push.PushMerchant.modules.person.activity.RegistActivity;
import com.push.PushMerchant.modules.person.activity.SuperDetailActivity;
import com.push.PushMerchant.modules.person.webview.BrowserActivity;
import com.push.PushMerchant.weight.selectImage.SelectImageActivity;

/**
 * Created by Administrator on 2017/6/23 0023.
 */

public class JumpUtil {
    /**
     * 进入主页
     *
     * @param context
     */
    public static void toHome(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
    }

    /**
     * 进入主页
     *
     * @param context
     */
    public static void toHome(Context context,int areaId,int cityId,int proviceId,int productId,int index) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra("areaId",areaId);
        intent.putExtra("cityId",cityId);
        intent.putExtra("proviceId",proviceId);
        intent.putExtra("productId",productId);
        intent.putExtra("index",index);
        context.startActivity(intent);
    }

    /**
     * 进入登录页
     *
     * @param context
     */
    public static void toLogin(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);
    }

    /**
     * 进入注册页
     *
     * @param context
     */
    public static void toRegist(Context context) {
        Intent intent = new Intent(context, RegistActivity.class);
        context.startActivity(intent);
    }

    /**
     * 忘记密码
     *
     * @param context
     */
    public static void toForgetPsw(Context context) {
        Intent intent = new Intent(context, ForgetActivity.class);
        context.startActivity(intent);
    }

    /**
     * 个人详情
     *
     * @param fragment
     */
    public static void toMyDetail(Fragment fragment, int result) {
        Intent intent = new Intent(fragment.getContext(), SettingActivity.class);
        fragment.startActivityForResult(intent, result);
    }

    /**
     * 我的项目
     *
     * @param context
     */
    public static void toMyProject(Context context) {
        Intent intent = new Intent(context, MyProjectActivity.class);
        context.startActivity(intent);
    }

    /**
     * 我的项目
     *
     * @param context
     */
    public static void toAbout(Context context) {
        Intent intent = new Intent(context, AbountActivity.class);
        context.startActivity(intent);
    }

    /**
     * 资料审核
     *
     * @param context
     */
    public static void toAuditData(Context context) {
        Intent intent = new Intent(context, AuditDataActivity.class);
        context.startActivity(intent);
    }

    /**
     * 资料审核1
     *
     * @param context
     */
    public static void toAuditData1(Context context) {
        Intent intent = new Intent(context, AuditData1Activity.class);
        context.startActivity(intent);
    }

    /**
     * 资料信息
     *
     * @param context
     */
    public static void toDataInfontion(Context context) {
        Intent intent = new Intent(context, DataInformationActivity.class);
        context.startActivity(intent);
    }

    /**
     * 广告详情
     *
     * @param context
     * @param id
     */
    public static void toAdvertDetail(Context context, int id) {
        Intent intent = new Intent(context, AdvertDetailActivity.class);
        intent.putExtra("id", id);
        context.startActivity(intent);
    }

    /**
     * 图片选择器
     *
     * @param context
     * @param maxNum
     * @param result
     */
    public static void toPicture(Context context, int maxNum, int result) {
        Intent intent = new Intent(context, SelectImageActivity.class);
        intent.putExtra(IntentFlag.INTENT_NUM, maxNum);
        ((Activity) context).startActivityForResult(intent, result);
    }

    /**
     * 进入编辑界面
     *
     * @param context
     * @param remark
     */
    public static void jumpToRemark(Context context, String title, String remark, int maxLeng, int request) {
        Intent intent = new Intent(context, AddRemarkActivity.class);
        intent.putExtra(IntentFlag.TITLE_STRING, title);
        intent.putExtra(IntentFlag.INTENT_FLAG_COUNT, maxLeng);
        intent.putExtra(IntentFlag.REMARK, remark);
        ((Activity) context).startActivityForResult(intent, request);
    }

    /**
     * 项目详情
     *
     * @param context
     */
    public static void toProjectDetail(Context context, int taskid) {
        Intent intent = new Intent(context, ProjectDetailActivity.class);
        intent.putExtra("taskid", taskid);
        context.startActivity(intent);
    }

    /**
     * 超市详情
     *
     * @param context
     */
    public static void toSuperDetail(Context context,int storeId,int taskId) {
        Intent intent = new Intent(context, SuperDetailActivity.class);
        intent.putExtra("storeId",storeId);
        intent.putExtra("taskId",taskId);
        context.startActivity(intent);
    }

    /**
     * 城市列表
     **/
    public static void toCity(Fragment fragment,int result) {
        Intent intent = new Intent(fragment.getContext(), CityActivty.class);
        fragment.startActivityForResult(intent, result);
    }
    /**
     * 打开web
     *
     * @param context
     * @param url
     */
    public static void toBrowser(Context context, String url) {
        Intent intent = new Intent(context, BrowserActivity.class);
        intent.putExtra(IntentFlag.KEY_URL, url);

        if (!Activity.class.isInstance(context)) {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }

        try {
            context.startActivity(intent);
        } catch (Throwable e) {
        }

    }

    /**
     * 物料详情
     *
     * @param context
     */
    public static void toMaterielDetail(Context context, String name, String pic, String introduce) {
        Intent intent = new Intent(context, MaterielDetailActivity.class);
        intent.putExtra("name", name);
        intent.putExtra("pic", pic);
        intent.putExtra("introduce", introduce);
        context.startActivity(intent);
    }
}
