package git.oversadboy.anotherpictures.model.datasourse

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import androidx.paging.PageKeyedDataSource
import git.oversadboy.anotherpictures.model.response.Photo

class ImageDataSourceFactory : DataSource.Factory<Int, Photo>() {
    private val imageLiveDataSource = MutableLiveData<PageKeyedDataSource<Int, Photo>>()

    override fun create(): DataSource<Int, Photo> {
        val photoDataSource = ImageDataSource()
        imageLiveDataSource.postValue(photoDataSource)
        return photoDataSource
    }
}