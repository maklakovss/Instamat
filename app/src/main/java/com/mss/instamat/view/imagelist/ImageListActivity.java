package com.mss.instamat.view.imagelist;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.mss.instamat.R;
import com.mss.instamat.presenter.ImageListPresenter;
import com.mss.instamat.view.detail.DetailActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ImageListActivity extends MvpAppCompatActivity implements ImageListView, ImageListAdapter.OnItemClickListener {

    @InjectPresenter
    ImageListPresenter presenter;

    @BindView(R.id.rvImages)
    public RecyclerView rvImages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        rvImages.setItemAnimator(new DefaultItemAnimator());
        rvImages.setHasFixedSize(true);
        rvImages.setLayoutManager(new GridLayoutManager(this, 2));
    }

    @NonNull
    @ProvidePresenter
    ImageListPresenter providePresenter() {
        return new ImageListPresenter();
    }

    @Override
    public void initImageList(@NonNull List<Integer> images) {
        ImageListAdapter adapter = new ImageListAdapter(presenter.getRvPresenter());
        adapter.setOnItemClickListener(this);
        rvImages.setAdapter(adapter);
    }

    @Override
    public void openDetailActivity(int position) {
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra(DetailActivity.POSITION_TAG, position);
        startActivity(intent);

    }

    @Override
    public void onItemClick(View view, int position) {
        presenter.onItemClick(position);
    }
}
