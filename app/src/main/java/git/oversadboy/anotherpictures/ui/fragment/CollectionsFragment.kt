package git.oversadboy.anotherpictures.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import git.oversadboy.anotherpictures.R

class CollectionsFragment : Fragment() {

    private lateinit var collectionsViewModel: CollectionsViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        collectionsViewModel = ViewModelProviders.of(this).get(CollectionsViewModel::class.java)
        //        val textView: TextView = root.findViewById(R.id.text_dashboard)
//        collectionsViewModel.text.observe(this, Observer {
//            textView.text = it
//        })
        return inflater.inflate(R.layout.fragment_collections, container, false)
    }
}