package git.oversadboy.anotherpictures.ui.images

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.hadilq.liveevent.LiveEvent
import git.oversadboy.anotherpictures.model.api.Api
import git.oversadboy.anotherpictures.model.datasource.ImageDataSourceFactory
import git.oversadboy.anotherpictures.model.pojo.Image
import javax.inject.Inject

class ImagesViewModel @Inject constructor(
    api: Api
) : ViewModel() {

    val images: LiveData<PagedList<Image>> = LivePagedListBuilder(
        ImageDataSourceFactory(api),
        PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setPageSize(10)
            .build()
    )
        .build()

    private val eventOpenImage = LiveEvent<Image>()
    val openImage: LiveData<Image> = eventOpenImage

    fun clickImage(image: Image) {
        eventOpenImage.value = image
    }

    private val eventDownloadImage = LiveEvent<Pair<String?, String>>()
    val downloadImage: LiveData<Pair<String?, String>> = eventDownloadImage

    fun clickDownload(url: String?, name: String) {
        eventDownloadImage.value = Pair(url,name)
    }


}