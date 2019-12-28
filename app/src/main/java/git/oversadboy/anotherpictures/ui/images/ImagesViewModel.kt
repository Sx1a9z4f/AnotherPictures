package git.oversadboy.anotherpictures.ui.images

import android.app.DownloadManager
import android.net.Uri
import android.os.Environment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.hadilq.liveevent.LiveEvent
import git.oversadboy.anotherpictures.R
import git.oversadboy.anotherpictures.model.api.Api
import git.oversadboy.anotherpictures.model.datasource.ImageDataSourceFactory
import git.oversadboy.anotherpictures.model.datasource.SearchDataSourceFactory
import git.oversadboy.anotherpictures.model.pojo.Image
import git.oversadboy.anotherpictures.ui.base.LocalizationUtilities
import java.io.File
import javax.inject.Inject

class ImagesViewModel @Inject constructor(
    private val api: Api,
    private val localizationUtilities: LocalizationUtilities,
    private val downloadManager: DownloadManager
) : ViewModel() {

    companion object {
        private const val PAGE_SIZE = 10
        private const val MIME_TYPE = "image/jpeg"
    }

    private val pagedConfig = PagedList.Config.Builder()
        .setEnablePlaceholders(false)
        .setPageSize(PAGE_SIZE)
        .build()

    val images: LiveData<PagedList<Image>> = LivePagedListBuilder(
        ImageDataSourceFactory(api), pagedConfig
    ).build()

    lateinit var imagesSearch: LiveData<PagedList<Image>>

    fun searchImages(query: String) {
        imagesSearch = LivePagedListBuilder(
            SearchDataSourceFactory(query, api), pagedConfig
        ).build()
    }

    private val eventOpenImage = LiveEvent<Image>()
    val openImage: LiveData<Image> = eventOpenImage

    fun clickImage(image: Image) {
        eventOpenImage.value = image
    }

    private val eventCheckPermission = LiveEvent<Pair<String?, String>>()
    val checkPermission: LiveData<Pair<String?, String>> = eventCheckPermission

    fun permissionGrantedFor(url: String?, name: String) {
        url?.also {
            downloadImage(it, name)
        }
    }

    fun clickDownload(url: String?, name: String) {
        eventCheckPermission.value = Pair(url, name)
    }

    private fun downloadImage(url: String, name: String) {
        val direct = File("${ImageActivity.MAIN_FOLDER}/$name")
        if (!direct.exists()) {
            direct.mkdir()
            val request = DownloadManager.Request(Uri.parse(url))
            request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI or DownloadManager.Request.NETWORK_MOBILE)
                .setAllowedOverRoaming(false)
                .setTitle(name)
                .setMimeType(MIME_TYPE)
                .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                .setDestinationInExternalPublicDir(
                    Environment.DIRECTORY_PICTURES,
                    File.separator + ImageActivity.MAIN_FOLDER_NAME + File.separator + name
                )
            downloadManager.enqueue(request)
        }
    }

    private val mutableImage = MutableLiveData<Image>()
    val imageLiveData: LiveData<Image> = mutableImage

    private val mutableSize = MutableLiveData<String>()
    val sizeLiveData: LiveData<String> = mutableSize

    private val mutableDescription = MutableLiveData<String>()
    val descriptionLiveData: LiveData<String> = mutableDescription

    fun initImage(image: Image) {
        mutableImage.value = image
        mutableDescription.value = image.description?.let { getDescription(it) }
        mutableSize.value = getSize(image.width, image.height)
    }

    private fun getDescription(description: String): String =
        if (description.isNotEmpty())
            description
        else
            localizationUtilities.getString(R.string.no_description)

    private fun getSize(width: Int, height: Int): String =
        "$width x $height"

    fun onRefresh() {
        images.value?.dataSource?.invalidate()
    }
}