package com.mss.instamat.view.imagelist;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.mss.instamat.R;
import com.mss.instamat.presenter.IRvImageListPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ImageListAdapter extends RecyclerView.Adapter<ImageListAdapter.ViewHolder> {

    private final IRvImageListPresenter rvPresenter;
    private OnItemClickListener onItemClickListener;


    public ImageListAdapter(@NonNull final IRvImageListPresenter rvPresenter) {
        this.rvPresenter = rvPresenter;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.imagelist_item, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        viewHolder.position = position;
        rvPresenter.bindView(viewHolder);
    }

    @Override
    public int getItemCount() {
        return rvPresenter.getItemCount();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }


    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements IImageListViewHolder {

        @BindView(R.id.ivItem)
        public ImageView ivItem;
        int position = 0;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @OnClick(R.id.ivItem)
        public void onImageViewClick(View view) {
            if (onItemClickListener != null) {
                onItemClickListener.onItemClick(view, getAdapterPosition());
            }
        }

        @Override
        public int getPos() {
            return position;
        }

        @Override
        public void setImage(int imageResourceId) {
            ivItem.setImageResource(imageResourceId);
        }
    }
}
