package com.mss.imagesearcher.view.history

interface IHistoryViewHolder {

    val pos: Int

    fun setText(text: String)

    fun setDescription(description: String)

    fun getStringByRes(idRes: Int): String

}
