package git.oversadboy.anotherpictures.dager.modules

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import git.oversadboy.anotherpictures.model.api.Unsplash
import git.oversadboy.anotherpictures.repository.api.Api
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
class AppModule(private val app: Application) {

    @Provides
    fun provideContext(): Context {
        return app
    }

    @Singleton
    @Provides
    fun provideOkHttp(): OkHttpClient =
        OkHttpClient.Builder()
            .build()

    @Singleton
    @Provides
    fun provideGsonConverterFactory(): GsonConverterFactory =
        GsonConverterFactory.create()

    @Singleton
    @Provides
    fun provideApi(okHttpClient: OkHttpClient): Api =
        Retrofit.Builder()
            .baseUrl(Unsplash.BASE_URL_POST)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(Api::class.java)

}