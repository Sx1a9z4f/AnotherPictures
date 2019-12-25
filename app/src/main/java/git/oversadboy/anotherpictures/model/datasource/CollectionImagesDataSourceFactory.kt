package git.oversadboy.anotherpictures.model.datasource

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import androidx.paging.PageKeyedDataSource
import git.oversadboy.anotherpictures.model.api.Api
import git.oversadboy.anotherpictures.model.pojo.Image

class CollectionImagesDataSourceFactory(private val id: String, private val api: Api) :
    DataSource.Factory<Int, Image>() {

    private val collectionImagesLiveDataSource = MutableLiveData<PageKeyedDataSource<Int, Image>>()

    override fun create(): DataSource<Int, Image> {
        val collectionImagesDataSource = CollectionImagesDataSource(id, api)
        collectionImagesLiveDataSource.postValue(collectionImagesDataSource)
        return collectionImagesDataSource
    }
}
