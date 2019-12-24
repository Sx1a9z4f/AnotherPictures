package git.oversadboy.anotherpictures.ui.collections

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import git.oversadboy.anotherpictures.model.api.Api
import git.oversadboy.anotherpictures.model.datasource.CollectionsDataSourceFactory
import git.oversadboy.anotherpictures.model.pojo.CollectionImage
import javax.inject.Inject

class CollectionsViewModel @Inject constructor(
    val api: Api
) : ViewModel() {

    var collection: LiveData<PagedList<CollectionImage>> = LivePagedListBuilder(
        CollectionsDataSourceFactory(api),
        PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setPageSize(10)
            .build()
    )
        .build()
}