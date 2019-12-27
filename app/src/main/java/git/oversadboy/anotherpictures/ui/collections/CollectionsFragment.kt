package git.oversadboy.anotherpictures.ui.collections

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import git.oversadboy.anotherpictures.R
import git.oversadboy.anotherpictures.dagger.App
import git.oversadboy.anotherpictures.ui.base.BaseFragment
import git.oversadboy.anotherpictures.utils.observe
import kotlinx.android.synthetic.main.fragment_collections.*

class CollectionsFragment : BaseFragment() {

    override val layoutId: Int = R.layout.fragment_collections

    override fun inject() {
        App.appComponent.inject(this)
    }

    private lateinit var adapter: CollectionsPagedListAdapter
    private val collectionsViewModel: CollectionsViewModel by viewModels { viewModelFactory }

    private fun observers() {
        with(collectionsViewModel) {
            collection.observe(this@CollectionsFragment) {
                adapter.submitList(it)
            }
            openCollection.observe(this@CollectionsFragment) {
                val collectionImagesFragment =
                    CollectionImagesFragment.newInstance(it.toString())

                //TODO
                activity?.supportFragmentManager?.beginTransaction()
                    ?.replace(R.id.nav_host_fragment, collectionImagesFragment)
                    ?.addToBackStack(null)
                    ?.commit()
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = CollectionsPagedListAdapter(
            context!!,
            collectionClickListener = { image ->
                collectionsViewModel.clickCollection(
                    image.id!!
                )
            }
        )
        collection_recycler.layoutManager = LinearLayoutManager(context)
        collection_recycler.adapter = adapter
        collection_refresh.setOnRefreshListener {
            collectionsViewModel.onRefresh()
            collection_refresh.isRefreshing = false
        }
        observers()
    }
}