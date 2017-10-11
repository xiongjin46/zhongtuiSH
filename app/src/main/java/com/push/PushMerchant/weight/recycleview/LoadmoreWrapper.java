package com.push.PushMerchant.weight.recycleview;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.push.PushMerchant.R;

/**
 * Created by zhy on 16/6/23.
 */
public class LoadmoreWrapper<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static final int ITEM_TYPE_LOAD_MORE = Integer.MAX_VALUE - 2;

    private RecyclerView.Adapter mInnerAdapter;
    private View mLoadMoreView;
    private int mLoadMoreLayoutId;
//    private boolean hasLoad = true;

    public final static int HOLDER_LOADIN = 0;
    public final static int HOLDER_LOADOVER = 1;
    public final static int HOLDER_LOADEMPTY = 2;
    private int HOLDER_DEFAULT = HOLDER_LOADIN;

    ViewHolder holder;

//    public void setHasLoad(boolean hasLoad) {
//        this.hasLoad = hasLoad;
//    }
//    public boolean isHasLoad() {
//        return hasLoad;
//    }

    public void setHolderLoad(int type) {
        HOLDER_DEFAULT = type;
    }

    public int getHOLDER_DEFAULT() {
        return HOLDER_DEFAULT;
    }

    public LoadmoreWrapper(RecyclerView.Adapter adapter) {
        mInnerAdapter = adapter;
    }

    private boolean hasLoadMore() {
        return mLoadMoreView != null || mLoadMoreLayoutId != 0;
    }


    private boolean isShowLoadMore(int position) {
        return hasLoadMore() && (position >= mInnerAdapter.getItemCount());
    }

    @Override
    public int getItemViewType(int position) {
        if (isShowLoadMore(position)) {
            return ITEM_TYPE_LOAD_MORE;
        }
        return mInnerAdapter.getItemViewType(position);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == ITEM_TYPE_LOAD_MORE) {

            if (mLoadMoreView != null) {
                holder = ViewHolder.createViewHolder(parent.getContext(), mLoadMoreView);
            } else {
                holder = ViewHolder.createViewHolder(parent.getContext(), parent, mLoadMoreLayoutId);
            }
            return holder;
        }
        return mInnerAdapter.onCreateViewHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder orderholder, int position) {
        if (isShowLoadMore(position)) {
            if (mOnLoadMoreListener != null) {
                if (holder != null) {
                    View view = holder.getView(R.id.tv_loading_tips);
                    View view1 = holder.getView(R.id.pb_loading);
                    if (view != null && view1 != null) {
                        String loadStr = "";
                        int showProgress = View.VISIBLE;
                        switch (HOLDER_DEFAULT) {
                            case HOLDER_LOADIN:
                                loadStr = "正在加载...";
                                showProgress = View.VISIBLE;
                                break;
                            case HOLDER_LOADOVER:
                                loadStr = "已经全部加载完毕";
                                showProgress = View.GONE;
                                break;
                            case HOLDER_LOADEMPTY:
                                loadStr = "暂无数据";
                                showProgress = View.GONE;
                                break;
                        }
                        ((TextView) view).setText(loadStr);
                        view1.setVisibility(showProgress);
                    }

                }
                mOnLoadMoreListener.onLoadMoreRequested();
            }
            return;
        }
        mInnerAdapter.onBindViewHolder(orderholder, position);
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        WrapperUtils.onAttachedToRecyclerView(mInnerAdapter, recyclerView, new WrapperUtils.SpanSizeCallback() {
            @Override
            public int getSpanSize(GridLayoutManager layoutManager, GridLayoutManager.SpanSizeLookup oldLookup, int position) {
                if (isShowLoadMore(position)) {
                    return layoutManager.getSpanCount();
                }
                if (oldLookup != null) {
                    return oldLookup.getSpanSize(position);
                }
                return 1;
            }
        });
    }


    @Override
    public void onViewAttachedToWindow(RecyclerView.ViewHolder holder) {
        mInnerAdapter.onViewAttachedToWindow(holder);

        if (isShowLoadMore(holder.getLayoutPosition())) {
            setFullSpan(holder);
        }
    }

    private void setFullSpan(RecyclerView.ViewHolder holder) {
        ViewGroup.LayoutParams lp = holder.itemView.getLayoutParams();

        if (lp != null
                && lp instanceof StaggeredGridLayoutManager.LayoutParams) {
            StaggeredGridLayoutManager.LayoutParams p = (StaggeredGridLayoutManager.LayoutParams) lp;

            p.setFullSpan(true);
        }
    }

    @Override
    public int getItemCount() {
        return mInnerAdapter.getItemCount() + (hasLoadMore() ? 1 : 0);
    }


    public interface OnLoadMoreListener {
        void onLoadMoreRequested();
    }

    private OnLoadMoreListener mOnLoadMoreListener;

    public LoadmoreWrapper setOnLoadMoreListener(OnLoadMoreListener loadMoreListener) {
        if (loadMoreListener != null) {
            mOnLoadMoreListener = loadMoreListener;
        }
        return this;
    }

    public LoadmoreWrapper setLoadMoreView(View loadMoreView) {
        mLoadMoreView = loadMoreView;
        return this;
    }

    public LoadmoreWrapper setLoadMoreView(int layoutId) {
        mLoadMoreLayoutId = layoutId;
        return this;
    }
}
