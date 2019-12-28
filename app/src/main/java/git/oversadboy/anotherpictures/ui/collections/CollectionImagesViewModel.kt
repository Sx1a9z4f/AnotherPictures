package git.oversadboy.anotherpictures.ui.collections

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.hadilq.liveevent.LiveEvent
import git.oversadboy.anotherpictures.model.api.Api
import git.oversadboy.anotherpictures.model.datasource.CollectionImagesDataSourceFactory
import git.oversadboy.anotherpictures.model.pojo.Image
import javax.inject.Inject

class CollectionImagesViewModel @Inject constructor(
    private val api: Api
) : ViewModel() {

    companion object {
        private const val PAGE_SIZE = 10
    }

    private val pagedConfig = PagedList.Config.Builder()
        .setEnablePlaceholders(false)
        .setPageSize(PAGE_SIZE)
        .build()

    lateinit var images: LiveData<PagedList<Image>>

    fun ready(id: String) {
        images = LivePagedListBuilder(
            CollectionImagesDataSourceFactory(id, api), pagedConfig
        ).build()
    }

    private val eventOpenImage = LiveEvent<Image>()
    val openImage: LiveData<Image> = eventOpenImage

    fun clickImage(image: Image) {
        eventOpenImage.value = image
    }
}