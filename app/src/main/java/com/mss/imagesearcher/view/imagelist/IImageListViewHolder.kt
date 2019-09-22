package com.mss.imagesearcher.view.imagelist

interface IImageListViewHolder {

    val pos: Int

    fun setImage(imageURL: String)

    fun showProgress(visible: Boolean)
}
