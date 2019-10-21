package com.mss.imagesearcher.view.history

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mss.imagesearcher.R
import com.mss.imagesearcher.presenter.history.IRvHistoryPresenter
import kotlinx.android.synthetic.main.history_list_item.view.*

class HistoryAdapter(private val rvPresenter: IRvHistoryPresenter) : RecyclerView.Adapter<HistoryAdapter.ViewHolder>() {

    private var onItemClickListener: OnItemClickListener? = null

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ViewHolder {
        return ViewHolder(LayoutInflater
                .from(viewGroup.context)
                .inflate(R.layout.history_list_item, viewGroup, false))
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        rvPresenter.bindView(viewHolder)
    }

    override fun getItemCount(): Int {
        return rvPresenter.getItemCount()
    }

    fun setOnItemClickListener(onItemClickListener: OnItemClickListener) {
        this.onItemClickListener = onItemClickListener
    }


    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }


    inner class ViewHolder constructor(itemView: View) : RecyclerView.ViewHolder(itemView), IHistoryViewHolder {

        override val pos: Int
            get() = adapterPosition

        init {
            itemView.clHistoryItem.setOnClickListener { onItemClickListener?.onItemClick(adapterPosition) }
        }

        override fun setText(text: String) {
            itemView.historyItemText.text = text
        }

        override fun setDescription(description: String) {
            itemView.historyItemDescription.text = description
        }

    }
}
