package com.mss.imagesearcher.presenter.history

import com.mss.imagesearcher.view.history.IHistoryViewHolder

interface IRvHistoryPresenter {

    fun bindView(viewHolder: IHistoryViewHolder)

    fun getItemCount(): Int

}
