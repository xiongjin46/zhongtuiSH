package com.push.PushMerchant.weight.selectImage;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.push.PushMerchant.R;
import com.push.PushMerchant.util.ImageLoader;
import com.push.PushMerchant.view.IconFontTextView;
import com.push.PushMerchant.view.impl.IOnItemClickListener;

import java.util.ArrayList;

/**
 * 类说明：图片路径切换适配
 *
 * @author AMeng
 * @version 1.0
 * @date 2015-11-17
 */
public class FolderAdapter extends RecyclerView.Adapter<fViewHolder> {
    private Context mContext;
    private ArrayList<ImageFloder> mDirPaths;
    private ImageFloder currentImageFolder;

    private IOnItemClickListener mOnItemClickLitener;

    public void setOnItemClickLitener(IOnItemClickListener mOnItemClickLitener) {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }

    public FolderAdapter(Context mContext, ArrayList<ImageFloder> mDirPaths,
                         ImageFloder currentImageFolder) {
        this.mContext = mContext;
        this.mDirPaths = mDirPaths;
        this.currentImageFolder = currentImageFolder;
    }

    @Override
    public int getItemCount() {
        return mDirPaths.size();
    }

    @Override
    public void onBindViewHolder(final fViewHolder holder, final int position) {
        ImageFloder item = mDirPaths.get(position);
        ImageLoader.load("file://" + item.getFirstImagePath(), holder.id_dir_item_image, mContext);
        holder.id_dir_item_count.setText(item.images.size() + "张");
        holder.id_dir_item_name.setText(item.getName());
        holder.choose.setVisibility(currentImageFolder == item ? 0 : 8);
        if (mOnItemClickLitener != null) {
            holder.itemView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickLitener.onItemClick(holder.itemView, position);
                }
            });
        }

    }

    @Override
    public fViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext).inflate(
                R.layout.row_dir_item, parent, false);
        return new fViewHolder(itemView);
    }

    public void setCurrentImageFolder(ImageFloder currentImageFolder) {
        this.currentImageFolder = currentImageFolder;
    }

}

class fViewHolder extends RecyclerView.ViewHolder {
    ImageView id_dir_item_image;
    IconFontTextView choose;
    TextView id_dir_item_name;
    TextView id_dir_item_count;

    public fViewHolder(View convertView) {
        super(convertView);
        id_dir_item_image = (ImageView) convertView
                .findViewById(R.id.id_dir_item_image);
        id_dir_item_name = (TextView) convertView
                .findViewById(R.id.id_dir_item_name);
        id_dir_item_count = (TextView) convertView
                .findViewById(R.id.id_dir_item_count);
        choose = (IconFontTextView) convertView.findViewById(R.id.choose);
    }

}