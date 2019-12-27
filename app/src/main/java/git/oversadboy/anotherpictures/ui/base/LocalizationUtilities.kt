package git.oversadboy.anotherpictures.ui.base

import android.content.Context
import androidx.annotation.StringRes

class LocalizationUtilities(
    private val context: Context
) {
    fun getString(@StringRes id: Int) = context.getString(id)
}