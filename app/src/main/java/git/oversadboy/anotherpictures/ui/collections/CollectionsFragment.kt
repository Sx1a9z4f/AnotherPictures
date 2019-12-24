package git.oversadboy.anotherpictures.ui.collections

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import git.oversadboy.anotherpictures.R
import git.oversadboy.anotherpictures.dagger.App
import git.oversadboy.anotherpictures.model.pojo.CollectionImage
import git.oversadboy.anotherpictures.ui.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_collections.*

class CollectionsFragment : BaseFragment() {

    override fun inject() {
        App.appComponent.inject(this)
    }

    private lateinit var refresh: SwipeRefreshLayout
    private lateinit var adapter: CollectionsPagedListAdapter


    private val collectionsViewModel: CollectionsViewModel by viewModels { viewModelFactory }
    override val layoutId: Int = R.layout.fragment_collections


    private fun observers() {
        collectionsViewModel.collection.observe(this, Observer { adapter.submitList(it) })
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_collections, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = CollectionsPagedListAdapter(
            context!!,
            collectionClickListener = { image -> collectionClick(image) }
        )
        collection_recycler.layoutManager = LinearLayoutManager(context)
        collection_recycler.adapter = adapter

        observers()
    }

    private fun collectionClick(image: CollectionImage) {

    }

}