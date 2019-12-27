package git.oversadboy.anotherpictures.model.datasource

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import androidx.paging.PageKeyedDataSource
import git.oversadboy.anotherpictures.model.api.Api
import git.oversadboy.anotherpictures.model.pojo.Image

class SearchDataSourceFactory(private val query: String, private val api: Api) :
    DataSource.Factory<Int, Image>() {

    private val searchLiveDataSource = MutableLiveData<PageKeyedDataSource<Int, Image>>()

    override fun create(): DataSource<Int, Image> {
        val searchDataSource = SearchDataSource(query, api)
        searchLiveDataSource.postValue(searchDataSource)
        return searchDataSource
    }
}
