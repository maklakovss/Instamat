package com.mss.imagesearcher.view.imagelist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mss.imagesearcher.App
import com.mss.imagesearcher.R
import com.mss.imagesearcher.presenter.imagelist.IRvImageListPresenter
import com.mss.imagesearcher.view.helpers.ImageLoader
import kotlinx.android.synthetic.main.imagelist_list_item.view.*
import javax.inject.Inject

class ImageListAdapter(private val rvPresenter: IRvImageListPresenter) : RecyclerView.Adapter<ImageListAdapter.ViewHolder>() {

    private var onItemClickListener: OnItemClickListener? = null

    @Inject
    lateinit var imageLoader: ImageLoader

    init {
        App.appComponent.inject(this)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ViewHolder {
        return ViewHolder(LayoutInflater
                .from(viewGroup.context)
                .inflate(R.layout.imagelist_list_item, viewGroup, false))
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        rvPresenter.bindView(viewHolder)
    }

    override fun getItemCount(): Int {
        return rvPresenter.itemCount
    }

    fun setOnItemClickListener(onItemClickListener: OnItemClickListener) {
        this.onItemClickListener = onItemClickListener
    }


    interface OnItemClickListener {
        fun onItemClick(view: View, position: Int)
    }


    inner class ViewHolder constructor(itemView: View) : RecyclerView.ViewHolder(itemView), IImageListViewHolder {

        override val pos: Int
            get() = adapterPosition

        init {
            itemView.ivItem.setOnClickListener { onItemClickListener?.onItemClick(it, adapterPosition) }
        }

        override fun setImage(imageURL: String) {
            imageLoader.load(itemView.context,
                    imageURL,
                    itemView.ivItem!!,
                    { rvPresenter.onImageLoaded(this@ViewHolder) },
                    { rvPresenter.onImageLoadFailed(this@ViewHolder) })
        }

        override fun showProgress(visible: Boolean) {
            if (visible) {
                itemView.pbItem!!.visibility = View.VISIBLE
            } else {
                itemView.pbItem!!.visibility = View.GONE
            }
        }
    }
}
