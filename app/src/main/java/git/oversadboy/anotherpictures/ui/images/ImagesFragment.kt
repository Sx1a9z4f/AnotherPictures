package git.oversadboy.anotherpictures.ui.images

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
import git.oversadboy.anotherpictures.utils.observe
import git.oversadboy.anotherpictures.utils.setOnQueryTextSubmit
import kotlinx.android.synthetic.main.fragment_images.*

class ImagesFragment : BaseFragment() {

    override fun inject() {
        App.appComponent.inject(this)
    }

    override val layoutId = R.layout.fragment_images

    private val imagesViewModel: ImagesViewModel by viewModels { viewModelFactory }

    private val adapterObserver = Observer<PagedList<Image>> { adapterImage.submitList(it) }
    private lateinit var adapterImage: ImageRecyclerAdapter

    private fun observers() {
        with(imagesViewModel) {
            images.observe(
                this@ImagesFragment, adapterObserver
            )
            openImage.observe(this@ImagesFragment) {
                startActivity(ImageActivity.intent(context!!, it))
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapterImage =
            ImageRecyclerAdapter(
                imageClickListener = { image -> imagesViewModel.clickImage(image) })
        image_recycler.layoutManager = GridLayoutManager(requireContext(), 2)
        image_recycler.adapter = adapterImage
        image_refresh.setOnRefreshListener {
            imagesViewModel.onRefresh()
            image_refresh.isRefreshing = false
        }
        observers()
        search_view.apply {
            setOnQueryTextSubmit {
                with(imagesViewModel) {
                    it?.let { searchImages(it) }
                    images.removeObservers(this@ImagesFragment)
                    imagesSearch.observe(viewLifecycleOwner, adapterObserver)
                }
            }

            setOnCloseListener {
                onActionViewCollapsed()
                with(imagesViewModel) {
                    imagesSearch.removeObservers(this@ImagesFragment)
                    images.observe(viewLifecycleOwner, adapterObserver)
                    imagesViewModel.onRefresh()
                }
                true
            }
        }
    }

//    override fun onResume() {
//        super.onResume()
//        imagesViewModel.onRefresh()
//    }
}