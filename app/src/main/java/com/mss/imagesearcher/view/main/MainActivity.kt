package com.mss.imagesearcher.view.main

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.KeyEvent
import android.view.Menu
import android.view.MenuItem
import androidx.fragment.app.Fragment
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds
import com.mss.imagesearcher.App
import com.mss.imagesearcher.R
import com.mss.imagesearcher.presenter.main.MainPresenter
import com.mss.imagesearcher.view.detail.DetailFragment
import com.mss.imagesearcher.view.history.HistoryFragment
import com.mss.imagesearcher.view.imagelist.ImageListFragment
import com.mss.imagesearcher.view.info.InfoFragment
import com.mss.imagesearcher.view.settings.SettingsFragment
import kotlinx.android.synthetic.main.activity_main.*
import timber.log.Timber
import javax.inject.Inject

class MainActivity : MvpAppCompatActivity(), MainActivityView {

    private var currentFragment: Fragment? = null

    @Inject
    @InjectPresenter
    lateinit var presenter: MainPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        App.appComponent.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Timber.d("onCreate")
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        etSearch!!.setOnEditorActionListener { _, actionId, keyEvent -> onAction(actionId, keyEvent) }

        bottomNavigationView.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.npHistory -> showFragment<HistoryFragment>()
                R.id.npSettings -> showFragment<SettingsFragment>()
                R.id.npResults -> showFragment<ImageListFragment>()
                R.id.npImage -> showFragment<DetailFragment>()
                R.id.npInfo -> showFragment<InfoFragment>()
            }
            true
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.imagelist_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.miPrivacyPolicy -> {
                presenter.onPrivacyPolicyClick()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    @ProvidePresenter
    fun providePresenter(): MainPresenter {
        return presenter
    }

    override fun initAdMob() {
        MobileAds.initialize(this, "ca-app-pub-8601890205077009~6067851844")

        val adRequest = AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build()

        adView.loadAd(adRequest)
    }

    override fun showPrivacyPolicy() {
        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.privacy_policy_url)))
        startActivity(browserIntent)
    }

    override fun goToList() {
        bottomNavigationView.selectedItemId = R.id.npResults
    }

    private inline fun <reified T : Fragment> showFragment() {
        val fragments = supportFragmentManager.fragments.filterIsInstance<T>()
        val ft = supportFragmentManager.beginTransaction()
        currentFragment?.let { ft.hide(it) }
        if (fragments.isEmpty()) {
            currentFragment = T::class.java.newInstance()
            ft.add(R.id.flFragmentsContainer, currentFragment!!)
        } else {
            currentFragment = fragments[0]
            ft.show(currentFragment!!)
        }
        ft.commit()
    }

    private fun onAction(actionId: Int, keyEvent: KeyEvent?): Boolean {
        var keyCode = 0
        if (keyEvent != null) {
            keyCode = keyEvent.keyCode
        }
        Timber.d("setOnEditorActionListener actionId = %d keyCode = %d", actionId, keyCode)
        if (keyCode == KeyEvent.KEYCODE_ENTER) {
            presenter.onSearchClick(etSearch!!.text!!.toString())
        }
        return false
    }

}
