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
import com.bumptech.glide.request.RequestOptions
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
        size.text = getImageSize(image) //TODO
        Glide.with(this).load(image.urls.regular) //TODO
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(full_image)
        download.setOnClickListener {
            imagesViewModel.clickDownload(image.urls.regular, image.id)
        }
    }

    private fun getImageSize(image: Image) = "${image.width} x ${image.height}"

    private fun observers() {
        with(imagesViewModel) {
            checkPermission.observe(this@ImageActivity) {
                checkPermissionFor(it.first, it.second)
            }
            descriptionLiveData.observe(this@ImageActivity) {
                description.text = it
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