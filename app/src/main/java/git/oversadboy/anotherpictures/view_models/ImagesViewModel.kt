package git.oversadboy.anotherpictures.view_models

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import git.oversadboy.anotherpictures.repository.api.Api
import git.oversadboy.anotherpictures.repository.pojo.Image
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import javax.inject.Inject

class ImagesViewModel @Inject constructor(
    private val api: Api
) : ViewModel() {

    private val configPageList = PagedList.Config.Builder()
        .setEnablePlaceholders(false)
        .setPageSize(10)
        .build()

    private val mutableImages = MutableLiveData<List<Image>>()
    val images: LiveData<List<Image>> = mutableImages

    private val imageExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        Log.e("Error", throwable.localizedMessage, throwable)
    }

    fun load() {
        viewModelScope.launch(imageExceptionHandler) {
            val photos = api.getPhotos(2)
            mutableImages.value = photos
        }
    }


    // var photoPagedList: LiveData<PagedList<Photo>>


}