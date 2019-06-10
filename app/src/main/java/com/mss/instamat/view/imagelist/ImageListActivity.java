package com.mss.instamat.view.imagelist;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ProgressBar;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.mss.instamat.App;
import com.mss.instamat.R;
import com.mss.instamat.presenter.imagelist.ImageListPresenter;
import com.mss.instamat.view.detail.DetailActivity;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ImageListActivity extends MvpAppCompatActivity implements ImageListView, ImageListAdapter.OnItemClickListener {

    private static final int PERMISSION_REQUEST_CODE = 777;

    @Inject
    @InjectPresenter
    ImageListPresenter presenter;

    @BindView(R.id.rvImages)
    public RecyclerView rvImages;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.etSearch)
    TextInputEditText etSearch;

    @BindView(R.id.pbList)
    ProgressBar pbList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        App.getAppComponent().inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imagelist);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        rvImages.setItemAnimator(new DefaultItemAnimator());
        rvImages.setHasFixedSize(true);
        rvImages.setLayoutManager(new GridLayoutManager(this, 2));
        rvImages.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0) {
                    int offsetOfEnd = recyclerView.computeVerticalScrollRange()
                            - recyclerView.computeVerticalScrollOffset();

                    if (offsetOfEnd < 2 * recyclerView.getHeight()) {
                        presenter.onNeedNextPage(etSearch.getText().toString());
                    }
                }
            }
        });

        etSearch.setOnEditorActionListener((textView, actionId, keyEvent) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH
                    || keyEvent.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
                presenter.onNeedNextPage(etSearch.getText().toString());
            }
            return false;
        });

        checkNetworkPermissions();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull final String[] permissions,
                                           @NonNull final int[] grantResults) {
        if (requestCode == PERMISSION_REQUEST_CODE
                && grantResults.length == 2) {
            finish();
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @NonNull
    @ProvidePresenter
    ImageListPresenter providePresenter() {
        return presenter;
    }

    @Override
    public void refreshImageList() {
        if (rvImages.getAdapter() == null) {
            final ImageListAdapter adapter = new ImageListAdapter(presenter.getRvPresenter());
            adapter.setOnItemClickListener(this);
            rvImages.setAdapter(adapter);
        } else {
            rvImages.getAdapter().notifyDataSetChanged();
        }
    }

    @Override
    public void openDetailActivity(int position) {
        final Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra(DetailActivity.PARAMETER_POSITION_TAG, position);
        startActivity(intent);
    }

    @Override
    public void showProgress(boolean visible) {
        if (visible) {
            pbList.setVisibility(View.VISIBLE);
        } else {
            pbList.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void showNotFoundMessage() {
        Snackbar.make(rvImages, getString(R.string.not_found_message), Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void onItemClick(View view, int position) {
        presenter.onItemClick(position);
    }

    private void checkNetworkPermissions() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET)
                != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_NETWORK_STATE)
                        != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{
                            Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE
                    },
                    PERMISSION_REQUEST_CODE);
        }
    }
}
