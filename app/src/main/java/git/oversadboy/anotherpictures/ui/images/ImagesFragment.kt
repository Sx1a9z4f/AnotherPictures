package git.oversadboy.anotherpictures.ui.images

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.paging.PagedList
import androidx.recyclerview.widget.LinearLayoutManager
import git.oversadboy.anotherpictures.R
import git.oversadboy.anotherpictures.model.response.Photo
import kotlinx.android.synthetic.main.fragment_images.*

class ImagesFragment : Fragment() {

    private lateinit var imagesViewModel: ImagesViewModel
    private lateinit var adapterImage: ImageRecyclerAdapter


    private lateinit var photo: ArrayList<Photo>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        observers()
    }

    private fun observers(){
        imagesViewModel.list.observe(this, Observer <PagedList<Photo>> {adapterImage.submitList(it)})
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        imagesViewModel = ViewModelProviders.of(this).get(ImagesViewModel::class.java)
        return inflater.inflate(R.layout.fragment_images, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        image_recycler.layoutManager = LinearLayoutManager(context)
        image_recycler.adapter = adapterImage
    }
}