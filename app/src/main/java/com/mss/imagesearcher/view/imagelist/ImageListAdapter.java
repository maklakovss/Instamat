package com.mss.imagesearcher.view.imagelist;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mss.imagesearcher.App;
import com.mss.imagesearcher.R;
import com.mss.imagesearcher.presenter.main.IRvImageListPresenter;
import com.mss.imagesearcher.view.helpers.ImageLoader;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ImageListAdapter extends RecyclerView.Adapter<ImageListAdapter.ViewHolder> {

    private final IRvImageListPresenter rvPresenter;
    private OnItemClickListener onItemClickListener;

    @Inject
    ImageLoader imageLoader;

    public ImageListAdapter(@NonNull final IRvImageListPresenter rvPresenter) {
        App.getAppComponent().inject(this);
        this.rvPresenter = rvPresenter;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater
                .from(viewGroup.getContext())
                .inflate(R.layout.imagelist_list_item, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
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
        void onItemClick(@NonNull View view, int position);
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements IImageListViewHolder {

        @BindView(R.id.pbItem)
        public ProgressBar pbItem;

        @BindView(R.id.ivItem)
        public ImageView ivItem;

        ViewHolder(@NonNull final View itemView) {
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
            return getAdapterPosition();
        }

        @Override
        public void setImage(String imageURL) {
            imageLoader.load(itemView.getContext(),
                    imageURL,
                    ivItem,
                    () -> rvPresenter.onImageLoaded(ViewHolder.this),
                    () -> rvPresenter.onImageLoadFailed(ViewHolder.this));
        }

        @Override
        public void showProgress(boolean visible) {
            if (visible) {
                pbItem.setVisibility(View.VISIBLE);
            } else {
                pbItem.setVisibility(View.GONE);
            }
        }
    }
}
