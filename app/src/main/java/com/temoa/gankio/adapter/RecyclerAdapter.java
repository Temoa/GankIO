package com.temoa.gankio.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.temoa.gankio.R;
import com.temoa.gankio.bean.NewGankData;
import com.temoa.gankio.Constants;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Temoa
 * on 2016/8/1 17:23
 */
public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerHolder> {

    private static final int TYPE_ITEM_CLICK = 0;
    private static final int TYPE_CHILD_CLICK = 1;


    private Context mContext;
    private List<NewGankData.Results> realData = new ArrayList<>();

    private ItemClickListener mItemClickListener;
    private ItemChildClickListener mItemChildClickListener;
    private ItemLongClickListener mItemLongClickListener;
    private LoadMoreListener mLoadMoreListener;

    private boolean isTypeReplaceAuthor = false;

    private boolean isLoadMore = false;
    private boolean isScrollDown;

    public RecyclerAdapter(Context context, RecyclerView recyclerView, List<NewGankData.Results> data) {
        this.mContext = context;
        realData = data == null ? new ArrayList<NewGankData.Results>() : data;

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                isScrollDown = dy > 0;
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    // 停止滑动
                    int lastVisibilityItemPos;
                    RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
                    if (layoutManager instanceof LinearLayoutManager) {
                        lastVisibilityItemPos
                                = ((LinearLayoutManager) layoutManager).findLastVisibleItemPosition();
                    } else {
                        throw new RuntimeException("Unsupported LayoutManager used");
                    }

                    int totalItemCount = layoutManager.getItemCount();
                    if (lastVisibilityItemPos >= totalItemCount - 2 && isScrollDown && isLoadMore) {
                        mLoadMoreListener.onLoadMore();
                    }
                }
            }
        });
    }

    @Override
    public RecyclerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View root = inflater.inflate(R.layout.common_item, parent, false);
        return new RecyclerHolder(root);
    }

    @Override
    public void onBindViewHolder(RecyclerHolder holder, int position) {
        NewGankData.Results result = realData.get(position);

        holder.setText(R.id.item_title, result.getDesc());
        holder.setText(R.id.item_author, result.getWho());

        String url = result.getUrl();

        if (result.getImages() != null && result.getImages().size() != 0) {
            holder.setImageByUrl(R.id.item_img, mContext, formatUrl(result.getImages().get(0)));
        } else if (url.contains(Constants.TYPE_GITHUB)) {
            holder.setImageResource(R.id.item_img, R.drawable.github);
        } else if (url.contains(Constants.TYPE_CSDN)) {
            holder.setImageResource(R.id.item_img, R.drawable.csdn);
        } else if (url.contains(Constants.TYPE_JIANSHU)) {
            holder.setImageResource(R.id.item_img, R.drawable.jianshu);
        } else {
            holder.setImageResource(R.id.item_img, R.drawable.other);
        }

        TextView tv = holder.getView(R.id.item_author);
        typeReplaceAuthor(isTypeReplaceAuthor, tv, result.getType());

        ImageView img = holder.getView(R.id.item_img);
        img.setOnClickListener(getOnClickListener(TYPE_CHILD_CLICK, position));

        holder.itemView.setOnClickListener(getOnClickListener(TYPE_ITEM_CLICK, position));
        holder.itemView.setOnLongClickListener(getOnLongClickListener(position));
    }

    @Override
    public int getItemCount() {
        return realData.size();
    }

    private View.OnClickListener getOnClickListener(final int type, final int position) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (type) {
                    case TYPE_ITEM_CLICK:
                        mItemClickListener.onItemClick(view, realData.get(position), position);
                        break;
                    case TYPE_CHILD_CLICK:
                        mItemChildClickListener.onItemChildClick(view, realData.get(position), position);
                }
            }
        };
    }

    private View.OnLongClickListener getOnLongClickListener(final int position) {
        return new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                mItemLongClickListener.onItemLongClick(view, realData.get(position), position);
                return true;
            }
        };
    }

    private String formatUrl(String origin) {
        String data = "?imageView2/0/w/100";
        return origin + data;
    }

    public void setNewData(List<NewGankData.Results> data) {
        realData = data;
        notifyDataSetChanged();
    }

    public void addData(List<NewGankData.Results> data) {
        int originalSize = realData.size();
        realData.addAll(data);
        notifyItemRangeInserted(originalSize, data.size());
    }

    public void addItemClickListener(ItemClickListener l) {
        mItemClickListener = l;
    }

    public void addItemChildClickListener(ItemChildClickListener l) {
        mItemChildClickListener = l;
    }

    public void addItemLongClickListener(ItemLongClickListener l) {
        mItemLongClickListener = l;
    }

    public void setLoadMord(boolean value) {
        isLoadMore = value;
    }

    public void addLoadMoreListener(LoadMoreListener l) {
        mLoadMoreListener = l;
    }

    public void isTypeReplaceAuthor(boolean value) {
        isTypeReplaceAuthor = value;
    }

    private void typeReplaceAuthor(boolean value, TextView tv, String type) {
        if (value) {
            switch (type) {
                case Constants.TYPE_ANDROID:
                    tv.setText("  " + Constants.TYPE_ANDROID + "  ");
                    tv.setBackgroundColor(Color.parseColor("#8BC34A"));
                    tv.setTextColor(Color.WHITE);
                    break;
                case Constants.TYPE_IOS:
                    tv.setText("  " + Constants.TYPE_IOS + "  ");
                    tv.setBackgroundColor(Color.parseColor("#212121"));
                    tv.setTextColor(Color.WHITE);
                    break;
                case Constants.TYPE_WEB:
                    tv.setText("  " + Constants.TYPE_WEB + "  ");
                    tv.setBackgroundColor(Color.parseColor("#F57C00"));
                    tv.setTextColor(Color.WHITE);
                    break;
            }
        }
    }

    public interface ItemClickListener {
        void onItemClick(View v, NewGankData.Results data, int position);
    }

    public interface ItemChildClickListener {
        void onItemChildClick(View v, NewGankData.Results data, int position);
    }

    public interface ItemLongClickListener {
        void onItemLongClick(View v, NewGankData.Results data, int position);
    }

    public interface LoadMoreListener {
        void onLoadMore();
    }
}
