package git.oversadboy.anotherpictures.utils

import android.content.Context
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT

fun showErrorToast(context: Context, msg: String) {
    Toast.makeText(context, msg, LENGTH_SHORT)
}