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
import git.oversadboy.anotherpictures.ui.images.ImageActivity
import git.oversadboy.anotherpictures.ui.images.ImageRecyclerAdapter
import git.oversadboy.anotherpictures.utils.observe
import kotlinx.android.synthetic.main.fragment_collection_images.*

class CollectionImagesFragment : BaseFragment() {

    companion object {

        private const val ID_KEY = "id"
        private const val NAME_KEY = "name"

        fun newInstance(id: String, name: String) =
            CollectionImagesFragment().apply {
                arguments = Bundle().apply {
                    putString(ID_KEY, id)
                    putString(NAME_KEY, name)
                }
            }
    }

    override val layoutId: Int = R.layout.fragment_collection_images

    override fun inject() {
        App.appComponent.inject(this)
    }

    private val collectionsImageViewModel: CollectionImagesViewModel by viewModels { viewModelFactory }
    private lateinit var adapterImage: ImageRecyclerAdapter
    private val adapterObserver = Observer<PagedList<Image>> { adapterImage.submitList(it) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        collectionsImageViewModel.ready(arguments!!.getString(ID_KEY).toString())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapterImage =
            ImageRecyclerAdapter(
                imageClickListener = { image -> collectionsImageViewModel.clickImage(image) })
        collection_image_recycler.layoutManager = GridLayoutManager(requireContext(), 2)
        collection_image_recycler.adapter = adapterImage
        collection_image_refresh.setOnRefreshListener {
            collectionsImageViewModel.onRefresh()
            collection_image_refresh.isRefreshing = false
        }
        observers()
    }

    private fun observers() {
        with(collectionsImageViewModel) {
            images.observe(
                this@CollectionImagesFragment, adapterObserver
            )
            openImage.observe(this@CollectionImagesFragment){
                startActivity(ImageActivity.intent(context!!, it))
            }
        }
    }
}
