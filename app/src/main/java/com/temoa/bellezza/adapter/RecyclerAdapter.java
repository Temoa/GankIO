package com.temoa.bellezza.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jude.rollviewpager.RollPagerView;
import com.temoa.bellezza.R;

import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static OnRecyclerViewItemClickListener onRecyclerViewItemClickListener = null;
    private static final int TYPE_ROLLVIEW = 2;
    private static final int TYPE_ITEM = 1;
    private List<String> titleList;
    private RollViewPagerAdapter adapter;

    public RecyclerAdapter(List<String> titleList, RollViewPagerAdapter adapter) {
        this.titleList = titleList;
        this.adapter = adapter;
    }

    public void setOnRecyclerViewItemClikcListener(OnRecyclerViewItemClickListener itemClickListener) {
        onRecyclerViewItemClickListener = itemClickListener;
    }

    public interface OnRecyclerViewItemClickListener {
        void onItemClick(View view, int position);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        if (viewType == TYPE_ITEM) {
            final View view = LayoutInflater.from(context).inflate(R.layout.item, parent, false);
            return RecyclerItemViewHolder.newInstance(view);
        } else if (viewType == TYPE_ROLLVIEW) {
            final View view = LayoutInflater.from(context).inflate(R.layout.rollviewpager, parent, false);
            return RecyclerRollViewHolder.newInstance(view);
        }
        throw new RuntimeException("There is no type that matches the type " + viewType);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (isPositionRollView(position)) {
            RecyclerRollViewHolder rollViewHolder = (RecyclerRollViewHolder) holder;
            rollViewHolder.setAdapter(adapter);
        } else if (!isPositionRollView(position)) {
            RecyclerItemViewHolder itemViewHolder = (RecyclerItemViewHolder) holder;
            String itemText = titleList.get(position - 1);
            itemViewHolder.setTitleText(itemText);
        }
    }

    @Override
    public int getItemCount() {
        return getBasicItemCount() + 1;
    }

    public int getBasicItemCount() {
        return titleList == null ? 0 : titleList.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (isPositionRollView(position)) {
            return TYPE_ROLLVIEW;
        }
        return TYPE_ITEM;
    }

    private boolean isPositionRollView(int position) {
        return position == 0;
    }

    /*
    item对应的viewHolder
     */
    public static class RecyclerItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final TextView textTitle;

        public RecyclerItemViewHolder(View parent, TextView textTitle) {
            super(parent);
            this.textTitle = textTitle;
            parent.setOnClickListener(this);
        }

        public static RecyclerItemViewHolder newInstance(View parent) {
            TextView textTitle = (TextView) parent.findViewById(R.id.itemTextView);
            return new RecyclerItemViewHolder(parent, textTitle);
        }

        public void setTitleText(String text) {
            textTitle.setText(text);
        }

        @Override
        public void onClick(View v) {
            if (onRecyclerViewItemClickListener != null) {
                onRecyclerViewItemClickListener.onItemClick(v, getPosition());
            }
        }
    }

    /*
    RollPagerView对应的viewHolder
     */
    public static class RecyclerRollViewHolder extends RecyclerView.ViewHolder {
        private final RollPagerView rollPagerView;

        public RecyclerRollViewHolder(View parent, RollPagerView rollPagerView) {
            super(parent);
            this.rollPagerView = rollPagerView;
        }

        public static RecyclerRollViewHolder newInstance(View parent) {
            RollPagerView rollPagerView = (RollPagerView) parent.findViewById(R.id.rollViewPager);
            return new RecyclerRollViewHolder(parent, rollPagerView);
        }

        public void setAdapter(RollViewPagerAdapter adapter) {
            rollPagerView.setAdapter(adapter);
        }
    }
}
