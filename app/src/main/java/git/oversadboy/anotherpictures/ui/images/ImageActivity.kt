package git.oversadboy.anotherpictures.ui.images

import android.Manifest
import android.app.DownloadManager
import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import git.oversadboy.anotherpictures.R
import git.oversadboy.anotherpictures.dagger.App
import git.oversadboy.anotherpictures.model.pojo.Image
import git.oversadboy.anotherpictures.ui.base.BaseActivity
import kotlinx.android.synthetic.main.fragment_image.*
import java.io.File

class ImageActivity : BaseActivity() {

    override fun inject() {
        App.appComponent.inject(this)
    }

    private val imagesViewModel: ImagesViewModel by viewModels { viewModelFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_image)
        inject()
        observers()
        val image: Image = intent.extras?.getParcelable("image")!!
        size.text = getImageSize(image)
        description.text = setDescription(image)
        Glide.with(this).load(image.urls.regular)
            .apply(
                RequestOptions().dontTransform()
            )
            .thumbnail(0.03f).diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(full_image)
        download.setOnClickListener {
            imagesViewModel.clickDownload(image.urls.regular, image.id)
        }
    }

    private fun getImageSize(image: Image) = "${image.width} x ${image.height}"

    private fun setDescription(image: Image): String =
        if (image.description.isNullOrEmpty())
            image.description!!
        else
            "no description"


    private fun observers() {
        with(imagesViewModel) {
            downloadImage.observe(
                this@ImageActivity,
                Observer {
                    downloadImage(it.first, it.second)
                })
        }
    }

    companion object {
        const val MAIN_FOLDER_NAME = "/AnotherPictures"
        val MAIN_FOLDER =
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).path + MAIN_FOLDER_NAME
        const val WRITE_EXTERNAL_PERMISSION_CODE = 1
    }

    private fun downloadImage(url: String?, name: String) {
        if (checkPermissions()) {
            val direct = File("$MAIN_FOLDER/$name")
            if (!direct.exists()) {
                direct.mkdir()
                val dm =
                    this.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
                val request = DownloadManager.Request(Uri.parse(url))
                request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI or DownloadManager.Request.NETWORK_MOBILE)
                    .setAllowedOverRoaming(false)
                    .setTitle(name)
                    .setMimeType("image/jpeg")
                    .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                    .setDestinationInExternalPublicDir(
                        Environment.DIRECTORY_PICTURES,
                        File.separator + MAIN_FOLDER_NAME + File.separator + name
                    )
                dm.enqueue(request)
            }
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