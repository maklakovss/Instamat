package com.mss.imagesearcher.view.settings


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.MvpAppCompatFragment
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.mss.imagesearcher.App
import com.mss.imagesearcher.R
import com.mss.imagesearcher.model.entity.QueryParams
import com.mss.imagesearcher.presenter.settings.SettingsPresenter
import com.mss.imagesearcher.view.helpers.TranslateHelper
import kotlinx.android.synthetic.main.fragment_settings.*
import timber.log.Timber
import javax.inject.Inject


class SettingsFragment : MvpAppCompatFragment(), SettingsView {

    @Inject
    @InjectPresenter
    lateinit var presenter: SettingsPresenter

    @Inject
    lateinit var translateHelper: TranslateHelper

    init {
        App.appComponent.inject(this)
    }

    @ProvidePresenter
    fun providePresenter(): SettingsPresenter {
        return presenter
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_settings, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        Timber.d("onCreate")

        btnApply.setOnClickListener { presenter.updateQuery(getQuery()) }
        btnClear.setOnClickListener { presenter.onClearClick() }
    }

    override fun showQuery(queryParams: QueryParams?) {
        queryParams?.let {
            spImageType.setSelection(getSpinnerPosition(R.array.image_type, it.imageType))
            spOrientation.setSelection(getSpinnerPosition(R.array.image_orientation, it.orientation))
            spCategory.setSelection(getSpinnerPosition(R.array.image_category, it.category))
            it.minHeight?.let { minHeight -> etMinHeight.setText(minHeight.toString()) }
            it.minWidth?.let { minWidth -> etMinHeight.setText(minWidth.toString()) }
            spColors.setSelection(getSpinnerPosition(R.array.image_colors, it.colors))
            spImageOrder.setSelection(getSpinnerPosition(R.array.image_order, it.imageOrder))
        }
    }

    private fun getQuery(): QueryParams {
        val queryParams = QueryParams()
        translateHelper.let {
            queryParams.imageType = it.getEngValueFromArray(R.array.image_type, spImageType.selectedItemPosition)
            queryParams.orientation = it.getEngValueFromArray(R.array.image_orientation, spOrientation.selectedItemPosition)
            queryParams.category = it.getEngValueFromArray(R.array.image_category, spCategory.selectedItemPosition)
            queryParams.minWidth = etMinWidth.text.toString().toIntOrNull()
            queryParams.minHeight = etMinHeight.text.toString().toIntOrNull()
            queryParams.colors = it.getEngValueFromArray(R.array.image_colors, spColors.selectedItemPosition)
            queryParams.imageOrder = it.getEngValueFromArray(R.array.image_order, spImageOrder.selectedItemPosition)
        }
        return queryParams
    }

    private fun getSpinnerPosition(idRes: Int, value: String?): Int {
        return if (value != null) {
            translateHelper.getPositionEngValueFromArray(idRes, value)
        } else {
            0
        }
    }
}
