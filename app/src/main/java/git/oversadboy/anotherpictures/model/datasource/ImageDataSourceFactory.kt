package git.oversadboy.anotherpictures.model.datasource

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import androidx.paging.PageKeyedDataSource
import git.oversadboy.anotherpictures.model.api.Api
import git.oversadboy.anotherpictures.model.pojo.Image

class ImageDataSourceFactory(val api: Api) : DataSource.Factory<Int, Image>() {

    private val imageLiveDataSource =
        MutableLiveData<PageKeyedDataSource<Int, Image>>()

    override fun create(): DataSource<Int, Image> {
        val imageDataSource = ImageDataSource(api)
        imageLiveDataSource.postValue(imageDataSource)
        return imageDataSource
    }
}