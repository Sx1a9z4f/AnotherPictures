package git.oversadboy.anotherpictures.utils

import androidx.appcompat.widget.SearchView

inline fun SearchView.setOnQueryTextSubmit(crossinline listener: (String?) -> Unit) {
    setOnQueryTextListener(object : SearchView.OnQueryTextListener {
        override fun onQueryTextSubmit(query: String?): Boolean {
            listener(query)
            return false
        }

        override fun onQueryTextChange(newText: String?): Boolean {
            return false
        }

    })
}