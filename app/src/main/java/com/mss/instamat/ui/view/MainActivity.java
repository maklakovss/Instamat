package com.mss.instamat.ui.view;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.mss.instamat.R;
import com.mss.instamat.ui.presenter.ImageListPresenter;

import java.util.List;

public class MainActivity extends MvpAppCompatActivity implements ImageListView, ImageListAdapter.OnItemClickListener {

    @InjectPresenter
    ImageListPresenter presenter;

    private RecyclerView rvImages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rvImages = findViewById(R.id.rvImages);
        rvImages.setItemAnimator(new DefaultItemAnimator());
        rvImages.setHasFixedSize(true);
        rvImages.setLayoutManager(new GridLayoutManager(this, 2));
    }

    @ProvidePresenter
    ImageListPresenter providePresenter() {
        return new ImageListPresenter();
    }

    @Override
    public void initImageList(List<Integer> images) {
        ImageListAdapter adapter = new ImageListAdapter(images);
        adapter.setOnItemClickListener(this);
        rvImages.setAdapter(adapter);
    }

    @Override
    public void onItemClick(View view, int position) {
        presenter.onItemClick(position);
    }
}
