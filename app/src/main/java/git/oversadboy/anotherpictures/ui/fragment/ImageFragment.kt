package git.oversadboy.anotherpictures.ui.fragment

import android.Manifest
import android.app.DownloadManager
import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import git.oversadboy.anotherpictures.R
import git.oversadboy.anotherpictures.dagger.App
import git.oversadboy.anotherpictures.model.pojo.Image
import kotlinx.android.synthetic.main.fragment_image.view.*
import java.io.File

class ImageFragment(
    private val image: Image
) : BaseFragment() {
    override fun inject() {
        App.appComponent.inject(this)
    }

    override val layoutId: Int = R.layout.fragment_image

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(layoutId, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.apply {
            val width = image.width
            val height = image.height
            size.text = "$width x $height"
            description.text = image.description
            Glide.with(context).load(image.urls.regular)
                .apply(
                    RequestOptions().dontTransform()
                )
                .thumbnail(0.03f).diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(full_image)
            download.setOnClickListener {
                downloadPhoto(image.urls.regular,image.id)
            }
        }
    }

    companion object {
        const val MAIN_FOLDER_NAME = "/UnsplashClient"
        val MAIN_FOLDER =
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).path + MAIN_FOLDER_NAME
        const val WRITE_EXTERNAL_PERMISSION_CODE = 1

        const val UNSPLASH_LOGIN_CALLBACK = "unsplash-auth-callback"
    }

    private fun downloadPhoto(url: String?, name: String) {
        if (checkPermissions()) {
            val direct = File("$MAIN_FOLDER/$name")
            if (!direct.exists()) {
                direct.mkdir()
                val dm =
                    requireContext().getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
                val downloadUri = Uri.parse(url)
                val request = DownloadManager.Request(downloadUri)
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
            } else {
            }
        }
    }

    private fun checkPermissions(): Boolean {
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            activity?.let {
                ActivityCompat.requestPermissions(
                    it,
                    arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                    WRITE_EXTERNAL_PERMISSION_CODE
                )
            }
            return false
        } else {
            return true
        }
    }
}