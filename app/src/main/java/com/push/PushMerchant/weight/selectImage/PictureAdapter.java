package com.push.PushMerchant.weight.selectImage;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;


import com.push.PushMerchant.R;
import com.push.PushMerchant.util.ImageLoader;
import com.push.PushMerchant.util.ToastUtils;
import com.push.PushMerchant.util.VersionUtils;
import com.push.PushMerchant.view.impl.IOnItemClickListener;

import java.util.ArrayList;


/**
 * 缩略图适配
 *
 * @author Administrator
 */
public class PictureAdapter extends RecyclerView.Adapter<MViewHolder> {
    private Context context;
    private ImageFloder currentImageFolder;
    private ArrayList<ImageItem> selectedPicture;

    private IOnItemClickListener mOnItemClickLitener;
    private int pos;

    public void setOnItemClickLitener(IOnItemClickListener mOnItemClickLitener) {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }

    public PictureAdapter(Context context, ImageFloder currentImageFolder,
                          ArrayList<ImageItem> selectedPicture) {
        super();
        this.context = context;
        this.currentImageFolder = currentImageFolder;
        this.selectedPicture = selectedPicture;
    }

    @Override
    public int getItemCount() {
        return currentImageFolder.images.size() + 1;
    }

    @Override
    public void onBindViewHolder(final MViewHolder hol, int position) {
        if (position == 0) {
            try {
                hol.iv.setImageResource(R.drawable.pickphotos_to_camera_normal);
            } catch (OutOfMemoryError e) {
                e.printStackTrace();
            }
            hol.checkBox.setVisibility(View.INVISIBLE);
        } else {
            if (!VersionUtils.isLOLLIPOP()) {
                hol.checkBox.setButtonDrawable(R.drawable.btn_checkbox_selector);
            }
            position = position - 1;
            hol.checkBox.setVisibility(View.VISIBLE);
            final ImageItem item = currentImageFolder.images.get(position);
            item.setPosition(position);
            ImageLoader.load("file://" + item.getPath(), hol.iv,context);
            boolean isSelected = ((SelectImageActivity) context)
                    .ImageItemToString(selectedPicture)
                    .contains(item.getPath());
            if (isSelected) {
                hol.iv.setColorFilter(Color.parseColor("#77000000"));
                hol.checkBox.setChecked(true);
            } else {
                hol.iv.setColorFilter(null);
                hol.checkBox.setChecked(false);
            }
            ((SelectImageActivity) context).setBtnOk(selectedPicture);

            hol.checkBox.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (hol.checkBox.isChecked()) {
                        if (selectedPicture.size() + 1 > SelectImageActivity.MAX_NUM) {
                            ToastUtils.showToast("最多选择" + SelectImageActivity.MAX_NUM + "张");
                            hol.checkBox.setChecked(false);
                            return;
                        }
                    }
                    ArrayList<String> imageItemToString = ((SelectImageActivity) context)
                            .ImageItemToString(selectedPicture);
                    if (imageItemToString.contains(item.getPath())) {
                        selectedPicture.remove(((SelectImageActivity) context)
                                .getImageItemPos(item.getPath()));
                        hol.checkBox.setChecked(false);
                        hol.iv.setColorFilter(null);
                    } else {
                        selectedPicture.add(new ImageItem(item.getPath()));
                        hol.checkBox.setChecked(true);
                        hol.iv.setColorFilter(Color.parseColor("#77000000"));
                    }

                    ((SelectImageActivity) context).setBtnOk(selectedPicture);
                    hol.checkBox.setSelected(selectedPicture.contains(item.getPath()));
                    ((SelectImageActivity) context).PreviewClick();
                }
            });

        }
        if (mOnItemClickLitener != null) {
            hol.iv.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    pos = hol.getLayoutPosition();
                    mOnItemClickLitener.onItemClick(hol.iv, pos);
                }
            });
        }
    }

    @Override
    public MViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MViewHolder holder = new MViewHolder(LayoutInflater.from(context).inflate(
                R.layout.row_item_picture, parent, false));
        return holder;
    }


    /**
     * 获取当前选中的集合
     *
     * @return
     */
    public ArrayList<ImageItem> getSelectedPicture() {
        return selectedPicture;
    }

    /**
     * 将图片添加到选中集合中
     */
    public void addSelectedPicture(String uri) {
        if (!((SelectImageActivity) context).ImageItemToString(
                selectedPicture).contains(uri)) {
            selectedPicture.add(new ImageItem(uri));
            notifyDataSetChanged();
        }
    }

    /**
     * 获取当前图片在缩率图中的角标
     */
    public int getPosition(String uri) {
        for (ImageItem Item : currentImageFolder.images) {
            if (uri.contains(Item.getPath())) {
                return Item.getPosition();
            }
        }
        return -1;
    }

    /**
     * 修改图片库的时候修改界面
     *
     * @param currentImageFolder2
     */
    public void setDataType(ImageFloder currentImageFolder2) {
        this.currentImageFolder = currentImageFolder2;
        this.notifyDataSetChanged();
    }

    /**
     * 删除选中集合中的图片信息
     *
     * @param uri
     */
    public void removeSelectPicture(String uri) {
        if (((SelectImageActivity) context)
                .ImageItemToString(selectedPicture).contains(uri)) {
            selectedPicture.remove(((SelectImageActivity) context)
                    .getImageItemPos(uri));
            notifyDataSetChanged();
        }
    }

}

class MViewHolder extends RecyclerView.ViewHolder {
    ImageView iv;
    CheckBox checkBox;

    public MViewHolder(View view) {
        super(view);
        iv = (ImageView) view.findViewById(R.id.iv);
        checkBox = (CheckBox) view.findViewById(R.id.check);
    }

}
