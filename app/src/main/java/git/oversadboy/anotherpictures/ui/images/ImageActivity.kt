package git.oversadboy.anotherpictures.ui.images

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Environment
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import git.oversadboy.anotherpictures.R
import git.oversadboy.anotherpictures.dagger.App
import git.oversadboy.anotherpictures.model.pojo.Image
import git.oversadboy.anotherpictures.ui.base.BaseActivity
import git.oversadboy.anotherpictures.utils.observe
import kotlinx.android.synthetic.main.fragment_image.*

class ImageActivity : BaseActivity() {

    companion object {
        const val IMAGE_KEY = "image"
        const val MAIN_FOLDER_NAME = "/AnotherPictures"
        val MAIN_FOLDER =
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).path + MAIN_FOLDER_NAME
        const val WRITE_EXTERNAL_PERMISSION_CODE = 1

        fun intent(context: Context, image: Image) =
            Intent(context, ImageActivity::class.java).apply {
                putExtra(IMAGE_KEY, image)
            }

    }

    override fun inject() {
        App.appComponent.inject(this)
    }

    private val imagesViewModel: ImagesViewModel by viewModels { viewModelFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_image)
        inject()
        observers()
        val image: Image = intent.extras?.getParcelable(IMAGE_KEY)!!
        imagesViewModel.initImage(image)
        download.setOnClickListener {
            imagesViewModel.clickDownload(image.urls.full, image.id)
        }
    }

    private fun observers() {
        with(imagesViewModel) {
            checkPermission.observe(this@ImageActivity) {
                checkPermissionFor(it.first, it.second)
            }
            imageLiveData.observe(this@ImageActivity) {
                Glide.with(this@ImageActivity).load(it.urls.regular)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(full_image)
            }
            descriptionLiveData.observe(this@ImageActivity) {
                description.text = it
            }
            sizeLiveData.observe(this@ImageActivity) {
                size.text = it
            }
        }
    }

    private fun checkPermissionFor(url: String?, name: String) {
        if (checkPermissions()) {
            imagesViewModel.permissionGrantedFor(url, name)
        }
    }

    private fun checkPermissions(): Boolean {
        return if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                WRITE_EXTERNAL_PERMISSION_CODE
            )
            false
        } else {
            true
        }
    }
}