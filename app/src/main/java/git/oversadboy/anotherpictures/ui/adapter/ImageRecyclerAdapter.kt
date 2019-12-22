package git.oversadboy.anotherpictures.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import git.oversadboy.anotherpictures.R
import git.oversadboy.anotherpictures.model.pojo.Image
import kotlinx.android.synthetic.main.list_image.view.*
import javax.inject.Inject

class ImageRecyclerAdapter : ListAdapter<Image, ImageViewHolder>(DiffAlbum()) {

    @Inject
    lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder =
        ImageViewHolder(parent)

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}


class DiffAlbum : DiffUtil.ItemCallback<Image>() {
    override fun areItemsTheSame(oldItem: Image, newItem: Image): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Image, newItem: Image): Boolean =
        oldItem.urls == newItem.urls
}

class ImageViewHolder(parent: ViewGroup) :
    RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.list_image, parent, false)
    ) {
    fun bind(image: Image) {
        itemView.apply {
            image_for_list.transitionName = image.urls.regular
            Glide.with(context).load(image.urls.regular)
                .apply(
                    RequestOptions().dontTransform()
                )
                .thumbnail(0.03f).diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(image_for_list)
        }
    }

}
