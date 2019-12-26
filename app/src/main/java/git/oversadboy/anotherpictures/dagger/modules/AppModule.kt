package git.oversadboy.anotherpictures.dagger.modules

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import git.oversadboy.anotherpictures.model.api.Api
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
class AppModule(private val app: Application) {

    companion object {
        private const val BASE_URL = "https://api.unsplash.com/"
        private const val ACCESS_KEY =
            "8ca469738bc1505a8f089eb6a904a4bdb380755aaddf6ab49b7d16ce427b2b78"
    }

    @Provides
    fun provideContext(): Context {
        return app
    }

    @Provides
    fun provideSP(app: Application): SharedPreferences =
        PreferenceManager.getDefaultSharedPreferences(app)

    @Singleton
    @Provides
    fun provideOkHttp(): OkHttpClient {
        val interceptor = Interceptor { chain ->
            val url = chain.request()
                .url
                .newBuilder()
                .addQueryParameter(
                    "client_id",
                    ACCESS_KEY
                )
                .build()

            val request = chain.request()
                .newBuilder()
                .header("Accept-Version", "v1")
                .url(url)
                .build()

            return@Interceptor chain.proceed(request)
        }

        return OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BASIC
            })
            .build()
    }

    @Singleton
    @Provides
    fun provideGsonConverterFactory(): GsonConverterFactory =
        GsonConverterFactory.create(
            GsonBuilder()
                .setLenient()
                .create()
        )

    @Singleton
    @Provides
    fun provideApi(okHttpClient: OkHttpClient, gsonConverterFactory: GsonConverterFactory): Api =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(gsonConverterFactory)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
            .create(Api::class.java)

}