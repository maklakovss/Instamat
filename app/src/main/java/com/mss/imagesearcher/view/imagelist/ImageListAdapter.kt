package com.mss.imagesearcher.view.imagelist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.recyclerview.widget.RecyclerView
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import com.mss.imagesearcher.App
import com.mss.imagesearcher.R
import com.mss.imagesearcher.presenter.imagelist.IRvImageListPresenter
import com.mss.imagesearcher.view.helpers.ImageLoader
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

        @BindView(R.id.pbItem)
        var pbItem: ProgressBar? = null

        @BindView(R.id.ivItem)
        var ivItem: ImageView? = null

        override val pos: Int
            get() = adapterPosition

        init {
            ButterKnife.bind(this, itemView)
        }

        @OnClick(R.id.ivItem)
        fun onImageViewClick(view: View) {
            onItemClickListener?.onItemClick(view, adapterPosition)
        }

        override fun setImage(imageURL: String) {
            imageLoader.load(itemView.context,
                    imageURL,
                    ivItem!!,
                    { rvPresenter.onImageLoaded(this@ViewHolder) },
                    { rvPresenter.onImageLoadFailed(this@ViewHolder) })
        }

        override fun showProgress(visible: Boolean) {
            if (visible) {
                pbItem!!.visibility = View.VISIBLE
            } else {
                pbItem!!.visibility = View.GONE
            }
        }
    }
}
