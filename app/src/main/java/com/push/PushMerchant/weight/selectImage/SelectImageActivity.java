package com.push.PushMerchant.weight.selectImage;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.push.PushMerchant.R;
import com.push.PushMerchant.constants.IntentFlag;
import com.push.PushMerchant.util.ImageLoader;
import com.push.PushMerchant.util.VersionUtils;
import com.push.PushMerchant.view.IconFontTextView;
import com.push.PushMerchant.view.impl.IOnItemClickListener;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by Administrator on 2015/11/27.
 */
public class SelectImageActivity extends FragmentActivity implements View.OnClickListener {

    /**
     * 最多选择图片的个数
     */
    public static int MAX_NUM = 0;
    private static final int TAKE_PICTURE = 520;


    private RecyclerView gridview;
    private PictureAdapter adapter;
    /**
     * 临时的辅助类，用于防止同一个文件夹的多次扫描
     */
    private HashMap<String, Integer> tmpDir = new HashMap<String, Integer>();
    private ArrayList<ImageFloder> mDirPaths = new ArrayList<ImageFloder>();

    private ContentResolver mContentResolver;
    private Button btn_select, btn_ok;
    private ImageFloder imageAll, currentImageFolder;
    final private int REQUEST_CODE_ASK_PERMISSIONS = 0x0002;

    /**
     * 已选择的图片
     */
    private ArrayList<ImageItem> selectedPicture;
    private String cameraPath = null;
    private ImagePagerFragment imagePagerFragment;
    private RelativeLayout bottom;
    private TextView tv_preview;
    private DialogAdapter dialogadapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (null != savedInstanceState) {
            MAX_NUM = savedInstanceState.getInt("MAX_NUM");
            selectedPicture = (ArrayList<ImageItem>) savedInstanceState.getSerializable("selectedPicture");

        } else {
            MAX_NUM = 0;
        }
        loadViewLayout();
        initDataBeforeView();
        findViewById();
        initDataAfterView();
        setListener();
    }

    //    @Override
    protected void initDataBeforeView() {
        if (0 == MAX_NUM)
            MAX_NUM = getIntent().getIntExtra(IntentFlag.INTENT_NUM, 6);
        if (null == selectedPicture)
            selectedPicture = new ArrayList<ImageItem>();
        if (null == mContentResolver)
            mContentResolver = getContentResolver();
    }

    //    @Override
    protected void loadViewLayout() {
        setContentView(R.layout.activity_select);
    }

    //    @Override
    protected void findViewById() {
        btn_ok = (Button) findViewById(R.id.btn_ok);
        btn_select = (Button) findViewById(R.id.btn_select);
        bottom = (RelativeLayout) findViewById(R.id.bottom);
        gridview = (RecyclerView) findViewById(R.id.gridview);
        tv_preview = (TextView) findViewById(R.id.tv_preview);

    }

    //    @Override
    protected void initDataAfterView() {
        imageAll = new ImageFloder();
        imageAll.setDir("/所有图片");
        currentImageFolder = imageAll;
        mDirPaths.add(imageAll);
        btn_ok.setText("完成0/" + MAX_NUM);
        gridview.setLayoutManager(new GridLayoutManager(this, 3));
        adapter = new PictureAdapter(this,
                currentImageFolder, selectedPicture);
        load();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_CODE_ASK_PERMISSIONS:
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    goCamare();
                } else {
                    return;
                }
                break;
        }
    }


    //    @Override
    protected void setListener() {
        tv_preview.setOnClickListener(this);
        btn_select.setOnClickListener(this);
        btn_ok.setOnClickListener(this);
        adapter.setOnItemClickLitener(new IOnItemClickListener() {

            @Override
            public void onItemClick(View view, int position) {
                if (position == 0) {
                    if (VersionUtils.isM()) {
                        int hasWriteContactsPermission = checkSelfPermission(Manifest.permission.CAMERA);
                        if (hasWriteContactsPermission != PackageManager.PERMISSION_GRANTED) {
                            requestPermissions(new String[]{Manifest.permission.CAMERA},
                                    REQUEST_CODE_ASK_PERMISSIONS);
                            return;
                        }
                    }
                    goCamare();
                } else {
                    position = position - 1;
                    int[] screenLocation = new int[2];
                    view.getLocationOnScreen(screenLocation);
                    ImagePagerFragment imagePagerFragment = ImagePagerFragment
                            .newInstance(
                                    ImageItemToString(currentImageFolder.images),
                                    position, screenLocation, view.getWidth(),
                                    view.getHeight());

                    addImagePagerFragment(imagePagerFragment, R.id.container);
                    bottom.setVisibility(View.GONE);
                }
            }

        });
        gridview.setAdapter(adapter);
    }

    /**
     * 获取数据
     */
    private void load() {
        Cursor mCursor = mContentResolver.query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                new String[]{MediaStore.Images.ImageColumns.DATA}, "", null,
                MediaStore.MediaColumns.DATE_ADDED + " DESC");
        // Log.e("TAG", mCursor.getCount() + "");
        if (mCursor != null && mCursor.moveToNext()) {
            int _date = mCursor.getColumnIndex(MediaStore.Images.Media.DATA);
            do {
                // 获取图片的路径
                String path = mCursor.getString(_date);
                if (TextUtils.isEmpty(path)) continue;
                // Log.e("TAG", path);
                imageAll.images.add(new ImageItem(path));
                // 获取该图片的父路径名
                File parentFile = new File(path).getParentFile();
                if (parentFile == null) {
                    continue;
                }
                ImageFloder imageFloder = null;
                String dirPath = parentFile.getAbsolutePath();
                if (null == tmpDir) {
                    tmpDir = new HashMap<String, Integer>();
                }
                if (!tmpDir.containsKey(dirPath)) {
                    // 初始化imageFloder
                    imageFloder = new ImageFloder();
                    imageFloder.setDir(dirPath);
                    imageFloder.setFirstImagePath(path);
                    mDirPaths.add(imageFloder);
                    // Log.d("zyh", dirPath + "," + path);
                    tmpDir.put(dirPath, mDirPaths.indexOf(imageFloder));
                } else {
                    imageFloder = mDirPaths.get(tmpDir.get(dirPath));
                }
                imageFloder.images.add(new ImageItem(path));
            }
            while (mCursor.moveToNext());
        }
        mCursor.close();
        tmpDir = null;
    }

    /**
     * 使用相机拍照
     *
     * @version 1.0
     */
    protected void goCamare() {
        if (selectedPicture.size() + 1 > MAX_NUM) {
            Toast.makeText(this, "最多选择" + MAX_NUM + "张", Toast.LENGTH_SHORT)
                    .show();
            return;
        }

        Intent openCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        Uri imageUri = getOutputMediaFileUri();
        openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(openCameraIntent, TAKE_PICTURE);
    }

    /**
     * 用于拍照时获取输出的Uri
     *
     * @return
     * @version 1.0
     */
    @SuppressLint("SimpleDateFormat")
    protected Uri getOutputMediaFileUri() {
        File mediaStorageDir = new File(
                Environment
                        .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                "Night");
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d("MyCameraApp", "failed to create directory");
                return null;
            }
        }
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss")
                .format(new Date());
        File mediaFile = new File(mediaStorageDir.getPath() + File.separator
                + "IMG_" + timeStamp + ".jpg");
        cameraPath = mediaFile.getAbsolutePath();
        return Uri.fromFile(mediaFile);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == TAKE_PICTURE && cameraPath != null) {
            ArrayList<String> imageItemToString = ImageItemToString(selectedPicture);
            imageItemToString.add(cameraPath);
            Intent data2 = new Intent();
            data2.putExtra(IntentFlag.INTENT_SELECTED_PICTURE, imageItemToString);
            setResult(RESULT_OK, data2);
            this.finish();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("cameraPath", cameraPath);
        outState.putInt("MAX_NUM", MAX_NUM);
        outState.putSerializable("selectedPicture", selectedPicture);

    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        MAX_NUM = savedInstanceState.getInt("MAX_NUM");
        selectedPicture = (ArrayList<ImageItem>) savedInstanceState.getSerializable("selectedPicture");
        cameraPath = savedInstanceState.getString("cameraPath");
    }

    public void back(View v) {
        onBackPressed();
    }

    // public View getView(int id) {
    // View view = findViewById(id);
    // return view;
    // }

    /**
     * 查看预览界面
     *
     * @param imagePagerFragment
     */
    public void addImagePagerFragment(ImagePagerFragment imagePagerFragment, int id) {
        this.imagePagerFragment = imagePagerFragment;
        getSupportFragmentManager().beginTransaction()
                .replace(id, this.imagePagerFragment)
                .addToBackStack(null).commit();
    }

    @Override
    /**
     * 关闭预览界面
     */
    public void onBackPressed() {
        if (imagePagerFragment != null && imagePagerFragment.isVisible()) {
            imagePagerFragment.runExitAnimation(new Runnable() {
                public void run() {
                    if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
                        getSupportFragmentManager().popBackStack();
                    }
                }
            });
            PreviewClick();
            bottom.setVisibility(View.VISIBLE);
        } else {
            super.onBackPressed();
        }
    }

    /**
     * 没有选中时,预览按钮不能选中
     */
    public void PreviewClick() {
        if (adapter.getSelectedPicture().isEmpty()) {
            tv_preview.setTextColor(Color.parseColor("#55ffffff"));
            tv_preview.setClickable(false);
        } else {
            tv_preview.setTextColor(Color.parseColor("#ffffff"));
            tv_preview.setClickable(true);
        }
    }

    /**
     * 获取当前选中的集合
     */
    public ArrayList<ImageItem> getSelectedPicture() {
        return adapter.getSelectedPicture();
    }

    /**
     * 将当前图片添加到选中图片集合中
     *
     * @param imgUrl
     */
    public void addSelectedPicture(String imgUrl) {
        adapter.addSelectedPicture(imgUrl);

    }

    /**
     * 将当前图片从选中图片中移除
     *
     * @param imgUrl
     */
    public void removeSelectedPicture(String imgUrl) {
        adapter.removeSelectPicture(imgUrl);

    }


    /**
     * 获取图片预览Fragment
     *
     * @return
     */
    public Fragment getFragment() {
        return imagePagerFragment;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_preview:
                if (adapter.getSelectedPicture().size() > 0) {
                    ArrayList<String> selecteds = ImageItemToString(adapter
                            .getSelectedPicture());
                    int position = adapter.getPosition(selecteds.get(0));
                    // int[] screenLocation = new int[2];
                    // view.getLocationOnScreen(screenLocation);
                    ImagePagerFragment imagePagerFragment = ImagePagerFragment
                            .newInstance(selecteds, position,
                                    new int[]{240, 350}, 0, 0);

                    addImagePagerFragment(imagePagerFragment, R.id.container);
                }
                break;
            case R.id.btn_select:
                final AlertDialog dialog = new AlertDialog.Builder(SelectImageActivity.this).create();
                RecyclerView lv = new RecyclerView(SelectImageActivity.this);
                lv.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 0.5f));
                lv.setLayoutManager(new LinearLayoutManager(SelectImageActivity.this));
                lv.setFadingEdgeLength(0);
                lv.setVerticalScrollBarEnabled(false);
                dialog.setView(lv);
                Window window = dialog.getWindow();
                window.setGravity(Gravity.BOTTOM | Gravity.START);  //此处可以设置dialog显示的位置
                if (dialogadapter == null)
                    dialogadapter = new DialogAdapter();
                lv.setAdapter(dialogadapter);
                dialog.show();

                dialogadapter.setOnItemClickLitener(new IOnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        adapter.setDataType(mDirPaths.get(position));
                        btn_select.setText(mDirPaths.get(position).getName());
                        currentImageFolder = mDirPaths.get(position);
                        adapter.notifyDataSetChanged();
                        dialog.dismiss();
                    }

                });
                break;
            case R.id.btn_ok:
                Intent data = new Intent();
                data.putExtra(IntentFlag.INTENT_SELECTED_PICTURE,
                        ImageItemToString(selectedPicture));
                setResult(RESULT_OK, data);
                this.finish();
                break;
        }
    }

    public ArrayList<String> ImageItemToString(ArrayList<ImageItem> imageItems) {
        ArrayList<String> arrayList = new ArrayList<String>();
        for (ImageItem items : imageItems) {
            arrayList.add(items.getPath());
        }
        return arrayList;
    }

    /**
     * 传递路径,获取该路径在选中集合中的position
     *
     * @param url
     * @return
     */
    public int getImageItemPos(String url) {
        ArrayList<ImageItem> selected = adapter.getSelectedPicture();
        for (int i = 0; i < selected.size(); i++) {
            if (selected.get(i).getPath().equals(url)) {
                return i;
            }
        }
        return -1;
    }

    /**
     * 修改当前图片库路径名
     *
     * @param name
     */
    public void setBtnSelectText(String name) {
        btn_select.setText(name);
    }

    /**
     * 修改当前图片库信息
     *
     * @param currentImageFolder
     */
    public void setCurrentImageFolder(ImageFloder currentImageFolder) {
        this.currentImageFolder = currentImageFolder;
    }

    /**
     * 修改顶部控件根据选中项改变
     */
    public void setBtnOk(ArrayList<ImageItem> selectedPicture) {
        btn_ok.setEnabled(selectedPicture.size() > 0);
        btn_ok.setText("完成" + selectedPicture.size() + "/" + MAX_NUM);
    }

    /**
     * 当前是否是选中图片集合中
     *
     * @param uri
     * @return
     */
    public boolean isSelectedPicture(String uri) {
        for (ImageItem imageItem : getSelectedPicture()) {
            if (uri.contains(imageItem.getPath())) {
                return true;
            }
        }
        return false;

    }

    public void setDataType(ImageFloder currentImageFolder2) {
        adapter.setDataType(currentImageFolder2);
    }

    class DialogAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        private IOnItemClickListener mOnItemClickLitener;

        public void setOnItemClickLitener(IOnItemClickListener mOnItemClickLitener) {
            this.mOnItemClickLitener = mOnItemClickLitener;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(SelectImageActivity.this).inflate(
                    R.layout.row_dir_item, parent, false);
            return new MViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
            ImageFloder item = mDirPaths.get(position);
            ImageLoader.load(item.getFirstImagePath(), ((MViewHolder) holder).id_dir_item_image, SelectImageActivity.this);
            ((MViewHolder) holder).id_dir_item_count.setText(item.images.size() + "张");
            ((MViewHolder) holder).id_dir_item_name.setText(item.getName());
            ((MViewHolder) holder).choose.setVisibility(currentImageFolder == item ? View.VISIBLE : View.GONE);
            if (mOnItemClickLitener != null) {
                ((MViewHolder) holder).itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        mOnItemClickLitener.onItemClick(holder.itemView, position);
                    }
                });
            }
        }

        @Override
        public int getItemCount() {
            return mDirPaths.size();
        }


        class MViewHolder extends RecyclerView.ViewHolder {
            ImageView id_dir_item_image;
            IconFontTextView choose;
            TextView id_dir_item_name;
            TextView id_dir_item_count;

            public MViewHolder(View itemView) {
                super(itemView);
                id_dir_item_image = (ImageView) itemView
                        .findViewById(R.id.id_dir_item_image);
                id_dir_item_name = (TextView) itemView
                        .findViewById(R.id.id_dir_item_name);
                id_dir_item_count = (TextView) itemView
                        .findViewById(R.id.id_dir_item_count);
                choose = (IconFontTextView) itemView.findViewById(R.id.choose);
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Glide.get(SelectImageActivity.this).clearMemory();
    }
}
