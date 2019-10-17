package com.mss.imagesearcher.view.history


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.arellomobile.mvp.MvpAppCompatFragment
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.mss.imagesearcher.App
import com.mss.imagesearcher.R
import com.mss.imagesearcher.model.entity.QueryParams
import com.mss.imagesearcher.presenter.history.HistoryPresenter
import kotlinx.android.synthetic.main.fragment_history.*
import timber.log.Timber
import javax.inject.Inject

class HistoryFragment : MvpAppCompatFragment(), HistoryView, HistoryAdapter.OnItemClickListener {

    @Inject
    @InjectPresenter
    lateinit var presenter: HistoryPresenter

    init {
        App.appComponent.inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_history, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        Timber.d("onCreate")

        recyclerViewInit()
        bthClearHistory.setOnClickListener { presenter.onClearHistoryClick() }
    }

    @ProvidePresenter
    fun providePresenter(): HistoryPresenter {
        return presenter
    }

    override fun refreshHistoryList(value: List<QueryParams>?) {
        rvHistory.adapter?.notifyDataSetChanged()
    }

    private fun recyclerViewInit() {
        rvHistory!!.layoutManager = LinearLayoutManager(requireContext())
        val adapter = HistoryAdapter(presenter.rvPresenter)
        adapter.setOnItemClickListener(this)
        rvHistory!!.adapter = adapter
    }

    override fun onItemClick(position: Int) {
        presenter.onItemClick(position)
    }

}
