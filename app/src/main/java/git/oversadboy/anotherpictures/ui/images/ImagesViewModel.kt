package git.oversadboy.anotherpictures.ui.images

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import git.oversadboy.anotherpictures.model.api.Api
import git.oversadboy.anotherpictures.model.datasourse.ImageDataSourceFactory
import git.oversadboy.anotherpictures.model.response.Photo

class ImagesViewModel(val api: Api) : ViewModel() {


    private val configPageList = PagedList.Config.Builder()
        .setEnablePlaceholders(false)
        .setPageSize(10)
        .build()

    var list: LiveData<PagedList<Photo>> =
        LivePagedListBuilder(ImageDataSourceFactory(),configPageList)
            .build()
    // var photoPagedList: LiveData<PagedList<Photo>>


}