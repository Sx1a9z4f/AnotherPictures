package git.oversadboy.anotherpictures.ui.images

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import git.oversadboy.anotherpictures.model.api.Api
import git.oversadboy.anotherpictures.model.datasourse.ImageDataSourceFactory
import git.oversadboy.anotherpictures.model.pojo.Image

class ImagesViewModel(val api: Api) : ViewModel() {


    private val configPageList = PagedList.Config.Builder()
        .setEnablePlaceholders(false)
        .setPageSize(10)
        .build()

    var liveData: LiveData<PagedList<Image>> =
        LivePagedListBuilder(ImageDataSourceFactory(),configPageList)
            .build()
    // var photoPagedList: LiveData<PagedList<Photo>>


}