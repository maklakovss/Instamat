package com.mss.imagesearcher.view.info

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.MvpAppCompatFragment
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.mss.imagesearcher.App
import com.mss.imagesearcher.R
import com.mss.imagesearcher.model.entity.ImageInfo
import com.mss.imagesearcher.presenter.info.InfoPresenter
import kotlinx.android.synthetic.main.fragment_info.*
import javax.inject.Inject

class InfoFragment : MvpAppCompatFragment(), InfoView {

    @Inject
    @InjectPresenter
    lateinit var presenter: InfoPresenter

    init {
        App.appComponent.inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_info, container, false)
    }

    @ProvidePresenter
    fun providePresenter(): InfoPresenter {
        return presenter
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

    override fun clearInfo() {
        tvSize!!.text = ""
        tvType!!.text = ""
        tvTags!!.text = ""
        tvLikes!!.text = ""
        tvViews!!.text = ""
        tvComments!!.text = ""
        tvUrl!!.text = ""
    }
}
