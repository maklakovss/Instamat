package com.mss.instamat.view.imagelist;

import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.mss.instamat.R;
import com.mss.instamat.presenter.imagelist.IRvImageListPresenter;

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
            Glide
                    .with(itemView)
                    .load(imageURL)
                    .listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(
                                @Nullable GlideException e,
                                Object model,
                                Target<Drawable> target,
                                boolean isFirstResource) {
                            rvPresenter.onImageLoadFailed(ViewHolder.this);
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(
                                Drawable resource,
                                Object model,
                                Target<Drawable> target,
                                DataSource dataSource,
                                boolean isFirstResource) {
                            rvPresenter.onImageLoaded(ViewHolder.this);
                            return false;
                        }
                    })
                    .into(ivItem);
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
