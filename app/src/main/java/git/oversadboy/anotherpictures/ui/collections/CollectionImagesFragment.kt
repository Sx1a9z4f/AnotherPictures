package git.oversadboy.anotherpictures.ui.collections

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import androidx.recyclerview.widget.GridLayoutManager
import git.oversadboy.anotherpictures.R
import git.oversadboy.anotherpictures.dagger.App
import git.oversadboy.anotherpictures.model.pojo.Image
import git.oversadboy.anotherpictures.ui.base.BaseFragment
import git.oversadboy.anotherpictures.ui.images.ImageRecyclerAdapter
import kotlinx.android.synthetic.main.collection_images_fragment.*

class CollectionImagesFragment : BaseFragment() {

    override val layoutId: Int = R.layout.collection_images_fragment

    override fun inject() {
        App.appComponent.inject(this)
    }

    private val collectionsImageViewModel: CollectionImagesViewModel by viewModels { viewModelFactory }
    private lateinit var adapterImage: ImageRecyclerAdapter
    private val adapterObserver = Observer<PagedList<Image>> { adapterImage.submitList(it) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        collectionsImageViewModel.ready(arguments!!.getString("id").toString())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapterImage =
            ImageRecyclerAdapter(
                imageClickListener = { image -> collectionsImageViewModel.clickImage(image) })
        collection_image_recycler.layoutManager = GridLayoutManager(requireContext(), 2)
        collection_image_recycler.adapter = adapterImage
        collection_image_refresh.setOnRefreshListener {
            collectionsImageViewModel.images.value?.dataSource?.invalidate()
            collection_image_refresh.isRefreshing = false
        }
        observers()

    }

    private fun observers() {
        with(collectionsImageViewModel) {
            images.observe(
                this@CollectionImagesFragment, adapterObserver
            )
            openImage.observe(this@CollectionImagesFragment, Observer {
                val intent = android.content.Intent(
                    context,
                    git.oversadboy.anotherpictures.ui.images.ImageActivity::class.java
                )
                intent.putExtra("image", it)
                startActivity(intent)
            })
        }
    }
}
