package git.oversadboy.anotherpictures.ui.collections

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import git.oversadboy.anotherpictures.R
import git.oversadboy.anotherpictures.dagger.App
import git.oversadboy.anotherpictures.ui.base.BaseFragment

class CollectionsFragment : BaseFragment() {

    private lateinit var collectionsViewModel: CollectionsViewModel

    override fun inject() {
        App.appComponent.inject(this)
    }

    override val layoutId: Int
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        collectionsViewModel = ViewModelProviders.of(this).get(CollectionsViewModel::class.java)

        return inflater.inflate(R.layout.fragment_collections, container, false)
    }
}