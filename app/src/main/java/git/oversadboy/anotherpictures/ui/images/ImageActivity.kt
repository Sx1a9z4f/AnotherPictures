package git.oversadboy.anotherpictures.ui.images

import android.Manifest
import android.app.DownloadManager
import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import androidx.core.app.ActivityCompat
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
    fun inject() {
        App.appComponent.inject(this)
    }

    private lateinit var image: Image

    private val layoutId: Int = R.layout.fragment_image

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutId)
        image = intent.extras?.getParcelable("image")!!
        val width = image.width
        val height = image.height
        size.text =
            "$width x $height"
        description.text = image.description
        Glide.with(this).load(image.urls.regular)
            .apply(
                RequestOptions().dontTransform()
            )
            .thumbnail(0.03f).diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(full_image)
        download.setOnClickListener {
            downloadPhoto(image.urls.regular, image.id)
        }
    }

    companion object {
        const val MAIN_FOLDER_NAME = "/AnotherPictures"
        val MAIN_FOLDER =
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).path + MAIN_FOLDER_NAME
        const val WRITE_EXTERNAL_PERMISSION_CODE = 1
    }

    private fun downloadPhoto(url: String?, name: String) {
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