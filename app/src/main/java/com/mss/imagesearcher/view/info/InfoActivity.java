package com.mss.imagesearcher.view.info;

import android.os.Bundle;

import androidx.annotation.NonNull;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.mss.imagesearcher.App;
import com.mss.imagesearcher.R;
import com.mss.imagesearcher.domain.models.ImageInfo;
import com.mss.imagesearcher.presenter.info.InfoPresenter;

import javax.annotation.Nonnull;
import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

public class InfoActivity extends MvpAppCompatActivity implements InfoView {

    public static final String PARAMETER_POSITION_TAG = "PARAMETER_POSITION_TAG";
    @Inject
    @InjectPresenter
    InfoPresenter presenter;
    @BindView(R.id.adView)
    AdView adView;
    private InterstitialAd interstitialAd;
    private int position = 0;

    @NonNull
    @ProvidePresenter
    InfoPresenter providePresenter() {
        return presenter;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Timber.d("onCreate");
        App.getAppComponent().inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        ButterKnife.bind(this);

        getParameters();
        presenter.onCreate(position);
    }

    @Override
    public void initAdMob() {
        interstitialAd = new InterstitialAd(getApplicationContext());
        interstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                super.onAdClosed();
                Timber.d("onAdClosed");
            }

            @Override
            public void onAdFailedToLoad(int i) {
                super.onAdFailedToLoad(i);
                Timber.d("onAdFailedToLoad %d", i);
            }

            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
                Timber.d("onAdLoaded");
                presenter.onAdLoaded();
            }

            @Override
            public void onAdClicked() {
                super.onAdClicked();
                Timber.d("onAdClicked");
            }
        });
        interstitialAd.setAdUnitId(getString(R.string.banner_between_page_id));
        AdRequest adRequest = new AdRequest.Builder().build();
        interstitialAd.loadAd(adRequest);

        adView.loadAd(new AdRequest.Builder().build());
    }

    @Override
    public void showFullScreenAd() {
        if (interstitialAd != null) {
            if (interstitialAd.isLoaded()) {
                interstitialAd.show();
            }
        }
    }


    private void getParameters() {
        final Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            position = bundle.getInt(PARAMETER_POSITION_TAG, 0);
        }
    }

    @Override
    public void showInfo(@Nonnull ImageInfo imageInfo) {

    }
}
