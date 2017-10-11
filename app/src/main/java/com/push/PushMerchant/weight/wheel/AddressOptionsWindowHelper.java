package com.push.PushMerchant.weight.wheel;

import android.app.Activity;

import com.push.PushMerchant.weight.wheel.pickview.CharacterPickerView;
import com.push.PushMerchant.weight.wheel.pickview.CharacterPickerWindow;
import com.push.PushMerchant.weight.wheel.pickview.OnOptionChangedListener;

import java.util.ArrayList;


/**
 * 地址选择器
 *
 * @version 0.1 king 2015-10
 */
public class AddressOptionsWindowHelper {

    private static ArrayList<String> options1Items = null;
    private static ArrayList<ArrayList<String>> options2Items = null;
    private static ArrayList<ArrayList<ArrayList<String>>> options3Items = null;

    private static int op1, op2, op3;

    public static void location(int one, int two, int three) {
        op1 = one;
        op2 = two;
        op3 = three;
    }

    public static void setOptions1Items(ArrayList<String> options1Items) {
        AddressOptionsWindowHelper.options1Items = options1Items;
    }

    public static void setOptions2Items(ArrayList<ArrayList<String>> options2Items) {
        AddressOptionsWindowHelper.options2Items = options2Items;
    }

    public static void setOptions3Items(ArrayList<ArrayList<ArrayList<String>>> options3Items) {
        AddressOptionsWindowHelper.options3Items = options3Items;
    }

    public interface OnOptionsSelectListener {
        void onOptionsSelect(String province, String city, String area, int one, int two, int three);
    }

    public AddressOptionsWindowHelper() {
    }

    public static CharacterPickerWindow builder(Activity activity, final OnOptionsSelectListener listener) {
        //选项选择器
        CharacterPickerWindow mOptions = new CharacterPickerWindow(activity);
        //初始化选项数据
        setPickerData(mOptions.getPickerView());
        //设置默认选中的三级项目
        mOptions.setSelectOptions(op1, op2, op3);
        //监听确定选择按钮
        mOptions.setOnoptionsSelectListener(new OnOptionChangedListener() {
            @Override
            public void onOptionChanged(int option1, int option2, int option3, int tag) {
                if (tag == 0) {
                    if (listener != null) {
                        String province = options1Items.get(option1);
                        String city = "";
                        if (options2Items != null && !options2Items.isEmpty() && options2Items.get(option1) != null && !options2Items.get(option1).isEmpty()) {
                            city = options2Items.get(option1).get(option2);
                        }
                        String area = "";
                        if (options3Items != null && !options3Items.isEmpty()
                                && options3Items.get(option1) != null && !options3Items.get(option1).isEmpty()
                                && options3Items.get(option1).get(option2) != null && !options3Items.get(option1).get(option2).isEmpty()) {
                            area = options3Items.get(option1).get(option2).get(option3);
                        } else {
                            area = "";
                        }
                        listener.onOptionsSelect(province, city, area, option1, option2, option3);
                    }
                }
                if (options1Items != null) {
                    options1Items.clear();
                }
                if (options2Items != null) {
                    options2Items.clear();
                }
                if (options3Items != null) {
                    options3Items.clear();
                }
            }
        });
        return mOptions;
    }

    /**
     * 初始化选项数据
     */
    public static void setPickerData(CharacterPickerView view) {
        if (options1Items == null) {
            options1Items = new ArrayList<>();
            options2Items = new ArrayList<>();
            options3Items = new ArrayList<>();
//
//            final Map<String, Map<String, List<String>>> allCitys = ArrayDataDemo.getAll();
//            for (Map.Entry<String, Map<String, List<String>>> entry1 : allCitys.entrySet()) {
//                String key1 = entry1.getKey();
//                Map<String, List<String>> value1 = entry1.getValue();
//
//                options1Items.add(key1);
//
//                List<String> options2Items01 = new ArrayList<>();
//                List<List<String>> options3Items01 = new ArrayList<>();
//                for (Map.Entry<String, List<String>> entry2 : value1.entrySet()) {
//                    String key2 = entry2.getKey();
//                    List<String> value2 = entry2.getValue();
//
//                    options2Items01.add(key2);
//                    Collections.sort(value2);
//                    options3Items01.add(new ArrayList<>(value2));
//                }
//                Collections.sort(options2Items01);
//                options2Items.add(options2Items01);
//                options3Items.add(options3Items01);
//            }
//            Collections.sort(options1Items);
        }

        //三级联动效果
        view.setPicker(options1Items, options2Items, options3Items);

    }

}
