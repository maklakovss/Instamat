package com.mss.imagesearcher.view.imagelist;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.mss.imagesearcher.App;
import com.mss.imagesearcher.R;
import com.mss.imagesearcher.presenter.imagelist.ImageListPresenter;
import com.mss.imagesearcher.view.detail.DetailActivity;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import pub.devrel.easypermissions.EasyPermissions;
import timber.log.Timber;

public class ImageListActivity extends MvpAppCompatActivity implements ImageListView, ImageListAdapter.OnItemClickListener {

    public static final int PERMISSION_REQUEST_CODE = 777;
    public static final String[] NETWORK_PERMISSIONS = {Manifest.permission.INTERNET, Manifest.permission.ACCESS_NETWORK_STATE};

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

    @BindView(R.id.srlImages)
    SwipeRefreshLayout swipeRefreshLayout;

    @BindView(R.id.adView)
    AdView adView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        App.getAppComponent().inject(this);
        super.onCreate(savedInstanceState);
        Timber.d("onCreate");
        setContentView(R.layout.activity_imagelist);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        recyclerViewInit();

        etSearch.setOnEditorActionListener(this::onAction);
        swipeRefreshLayout.setOnRefreshListener(this::onRefresh);

        checkNetworkPermissions();

        presenter.onCreate();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull final String[] permissions,
                                           @NonNull final int[] grantResults) {
        Timber.d("onRequestPermissionsResult requestCode = %d, grantResultsSize = %s", requestCode, grantResults.length);
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
        if (!EasyPermissions.hasPermissions(this, NETWORK_PERMISSIONS)) {
            Timber.d("Access deny Network");
            finish();
        }

    }

    @NonNull
    @ProvidePresenter
    ImageListPresenter providePresenter() {
        return presenter;
    }

    @Override
    public void refreshImageList() {
        Timber.d("refreshImageList");
        if (rvImages.getAdapter() == null) {
            Timber.d("Create adapter");
            final ImageListAdapter adapter = new ImageListAdapter(presenter.getRvPresenter());
            adapter.setOnItemClickListener(this);
            rvImages.setAdapter(adapter);
        } else {
            Timber.d("Adapter notifyDataSetChanged");
            rvImages.getAdapter().notifyDataSetChanged();
        }
    }

    @Override
    public void openDetailActivity(int position) {
        Timber.d("openDetailActivity");
        final Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra(DetailActivity.PARAMETER_POSITION_TAG, position);
        startActivity(intent);
    }

    @Override
    public void showProgress(boolean visible) {
        Timber.d("showProgress %b", visible);
        if (visible) {
            pbList.setVisibility(View.VISIBLE);
        } else {
            pbList.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void showNotFoundMessage() {
        Timber.d("showNotFoundMessage");
        Snackbar.make(rvImages, getString(R.string.not_found_message), Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void stopRefreshing() {
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onItemClick(View view, int position) {
        Timber.d("onItemClick");
        presenter.onItemClick(position);
    }

    private void checkNetworkPermissions() {
        if (EasyPermissions.hasPermissions(this, NETWORK_PERMISSIONS)) {
            Timber.d("Need NetworkPermissions");
            EasyPermissions.requestPermissions(this, getString(R.string.storage_rationale), PERMISSION_REQUEST_CODE, NETWORK_PERMISSIONS);
        }
    }

    private boolean onAction(@NonNull TextView textView, int actionId, @Nullable KeyEvent keyEvent) {
        int keyCode = 0;
        if (keyEvent != null) {
            keyCode = keyEvent.getKeyCode();
        }
        Timber.d("setOnEditorActionListener actionId = %d keyCode = %d", actionId, keyCode);
        if (actionId == EditorInfo.IME_ACTION_SEARCH || keyCode == KeyEvent.KEYCODE_ENTER) {
            presenter.onSearchClick(etSearch.getText().toString());
        }
        return false;
    }

    private void onRefresh() {
        Timber.d("onRefresh");
        presenter.onRefresh(etSearch.getText().toString());
    }

    private void recyclerViewInit() {
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
                        presenter.onNeedNextPage();
                    }
                }
            }
        });
    }

    @Override
    public void initAdMob() {
        MobileAds.initialize(this, "ca-app-pub-8601890205077009~6067851844");
        final AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();
        adView.loadAd(adRequest);
    }
}
