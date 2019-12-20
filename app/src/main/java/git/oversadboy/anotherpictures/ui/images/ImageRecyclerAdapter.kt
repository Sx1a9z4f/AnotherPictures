package git.oversadboy.anotherpictures.ui.images

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import git.oversadboy.anotherpictures.R
import git.oversadboy.anotherpictures.model.pojo.Image

class ImageRecyclerAdapter(
) : PagedListAdapter<Image, ImageViewHolder>(PhotoDiff()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        return ImageViewHolder(parent)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }
}

class ImageViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
) {

    fun bind(image: Image) {

    }

}

class PhotoDiff : DiffUtil.ItemCallback<Image>() {
    override fun areItemsTheSame(oldItem: Image, newItem: Image): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Image, newItem: Image): Boolean {
        return oldItem.urls.regular == newItem.urls.regular
    }
}