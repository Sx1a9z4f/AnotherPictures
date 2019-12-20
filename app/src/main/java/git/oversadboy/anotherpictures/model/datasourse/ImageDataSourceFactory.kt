package git.oversadboy.anotherpictures.model.datasourse

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import androidx.paging.PageKeyedDataSource
import git.oversadboy.anotherpictures.model.pojo.Image

class ImageDataSourceFactory : DataSource.Factory<Int, Image>() {
    private val imageLiveDataSource = MutableLiveData<PageKeyedDataSource<Int, Image>>()

    override fun create(): DataSource<Int, Image> {
        val photoDataSource = ImageDataSource()
        imageLiveDataSource.postValue(photoDataSource)
        return photoDataSource
    }
}