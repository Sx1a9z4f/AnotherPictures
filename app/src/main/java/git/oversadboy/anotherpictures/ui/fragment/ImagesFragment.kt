package git.oversadboy.anotherpictures.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import git.oversadboy.anotherpictures.R
import git.oversadboy.anotherpictures.dagger.App
import git.oversadboy.anotherpictures.model.pojo.Image
import git.oversadboy.anotherpictures.ui.adapter.ImageRecyclerAdapter
import kotlinx.android.synthetic.main.fragment_images.*

class ImagesFragment : BaseFragment() {

    override fun inject() {
        App.appComponent.inject(this)
    }

    override val layoutId = R.layout.fragment_images

    private lateinit var refresh: SwipeRefreshLayout

    private val imagesViewModel: ImagesViewModel by viewModels { viewModelFactory }
    private lateinit var adapterImage: ImageRecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        observers()
        imagesViewModel.load()
    }

    private fun observers() {
        imagesViewModel.images.observe(this, Observer<List<Image>> { adapterImage.submitList(it) })
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(layoutId, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapterImage = ImageRecyclerAdapter()
        image_recycler.layoutManager = GridLayoutManager(requireContext(), 2)
        image_recycler.adapter = adapterImage
        refresh = view.findViewById(R.id.image_refresh)
        refresh.setOnRefreshListener {
            imagesViewModel.load()
            refresh.isRefreshing = false
        }
    }
}