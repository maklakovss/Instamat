package com.mss.imagesearcher.view.info

import android.os.Bundle
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.InterstitialAd
import com.mss.imagesearcher.App
import com.mss.imagesearcher.R
import com.mss.imagesearcher.domain.models.ImageInfo
import com.mss.imagesearcher.presenter.info.InfoPresenter
import timber.log.Timber
import javax.inject.Inject

class InfoActivity : MvpAppCompatActivity(), InfoView {

    companion object {
        val PARAMETER_POSITION_TAG = "PARAMETER_POSITION_TAG"
    }

    @Inject
    @InjectPresenter
    lateinit var presenter: InfoPresenter

    @BindView(R.id.adView)
    var adView: AdView? = null

    @BindView(R.id.tvSize)
    var tvSize: TextView? = null

    @BindView(R.id.tvType)
    var tvType: TextView? = null

    @BindView(R.id.tvTags)
    var tvTags: TextView? = null

    @BindView(R.id.tvLikes)
    var tvLikes: TextView? = null

    @BindView(R.id.tvViews)
    var tvViews: TextView? = null

    @BindView(R.id.tvComments)
    var tvComments: TextView? = null

    @BindView(R.id.tvUrl)
    var tvUrl: TextView? = null

    private var interstitialAd: InterstitialAd? = null
    private var position = 0

    @ProvidePresenter
    fun providePresenter(): InfoPresenter {
        return presenter
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        Timber.d("onCreate")
        App.appComponent.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info)
        ButterKnife.bind(this)
        setTitle(R.string.info_activity_title)

        getParameters()
        presenter.onCreate(position)
    }

    override fun initAdMob() {
        interstitialAd = InterstitialAd(applicationContext)
        interstitialAd!!.adListener = object : AdListener() {
            override fun onAdClosed() {
                super.onAdClosed()
                Timber.d("onAdClosed")
            }

            override fun onAdFailedToLoad(i: Int) {
                super.onAdFailedToLoad(i)
                Timber.d("onAdFailedToLoad %d", i)
            }

            override fun onAdLoaded() {
                super.onAdLoaded()
                Timber.d("onAdLoaded")
                presenter.onAdLoaded()
            }

            override fun onAdClicked() {
                super.onAdClicked()
                Timber.d("onAdClicked")
            }
        }
        interstitialAd!!.adUnitId = getString(R.string.banner_between_page_id)
        val adRequest = AdRequest.Builder().build()
        interstitialAd!!.loadAd(adRequest)

        adView!!.loadAd(AdRequest.Builder().build())
    }

    override fun showFullScreenAd() {
        if (interstitialAd != null) {
            if (interstitialAd!!.isLoaded) {
                interstitialAd!!.show()
            }
        }
    }

    private fun getParameters() {
        val bundle = intent.extras
        if (bundle != null) {
            position = bundle.getInt(PARAMETER_POSITION_TAG, 0)
        }
    }

    override fun showInfo(imageInfo: ImageInfo) {
        tvSize!!.text = String.format("%d x %d", imageInfo.height, imageInfo.width)
        tvType!!.text = imageInfo.type
        tvTags!!.text = imageInfo.tags
        tvLikes!!.text = imageInfo.likes.toString()
        tvViews!!.text = imageInfo.views.toString()
        tvComments!!.text = imageInfo.comments.toString()
        tvUrl!!.text = imageInfo.pageUrl
    }
}
