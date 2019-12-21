package git.oversadboy.anotherpictures.ui.fragment

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import git.oversadboy.anotherpictures.model.api.Api
import git.oversadboy.anotherpictures.model.pojo.Image
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import javax.inject.Inject

class ImagesViewModel @Inject constructor(
    private val api: Api
) : ViewModel() {

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



}