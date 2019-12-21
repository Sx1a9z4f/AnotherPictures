package git.oversadboy.anotherpictures.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import git.oversadboy.anotherpictures.R
import git.oversadboy.anotherpictures.model.pojo.Image
import kotlinx.android.synthetic.main.list_image.view.*

class ImageRecyclerAdapter : ListAdapter<Image, ImageViewHolder>(DiffAlbum()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder =
        ImageViewHolder(parent)

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {

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

    private val imageView = itemView.image

    fun bind() {
//        itemView.setOnClickListener { clickListener(textName) }
//        textName.text = album.name
//        Glide.with(itemView.context)
//            .load(album.preview)
//            .into(imageView)
    }

}