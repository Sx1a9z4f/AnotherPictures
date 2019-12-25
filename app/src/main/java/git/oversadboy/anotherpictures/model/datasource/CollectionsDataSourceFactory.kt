package git.oversadboy.anotherpictures.model.datasource

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import androidx.paging.PageKeyedDataSource
import git.oversadboy.anotherpictures.model.api.Api
import git.oversadboy.anotherpictures.model.pojo.CollectionImage

class CollectionsDataSourceFactory(private val api: Api) : DataSource.Factory<Int, CollectionImage>() {

    private val collectionLiveDataSource =
        MutableLiveData<PageKeyedDataSource<Int, CollectionImage>>()

    override fun create(): DataSource<Int, CollectionImage> {
        val collectionDataSource = CollectionsDataSource(api)
        collectionLiveDataSource.postValue(collectionDataSource)
        return collectionDataSource
    }
}