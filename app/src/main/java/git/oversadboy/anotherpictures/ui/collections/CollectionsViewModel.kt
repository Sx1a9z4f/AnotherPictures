package git.oversadboy.anotherpictures.ui.collections

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.hadilq.liveevent.LiveEvent
import git.oversadboy.anotherpictures.model.api.Api
import git.oversadboy.anotherpictures.model.datasource.CollectionsDataSourceFactory
import git.oversadboy.anotherpictures.model.pojo.CollectionImage
import javax.inject.Inject

class CollectionsViewModel @Inject constructor(
    api: Api
) : ViewModel() {

    companion object {
        private const val PAGE_SIZE = 10
    }

    var collection: LiveData<PagedList<CollectionImage>> = LivePagedListBuilder(
        CollectionsDataSourceFactory(api),
        PagedList.Config.Builder()
            .setPageSize(PAGE_SIZE)
            .build()
    )
        .build()

    private val eventOpenCollection = LiveEvent<Pair<Int, String>>()
    val openCollection: LiveData<Pair<Int, String>> = eventOpenCollection

    fun onRefresh() {
        collection.value?.dataSource?.invalidate()
    }

    fun clickCollection(id: Int, name: String) {
        eventOpenCollection.value = Pair(id, name)
    }
}