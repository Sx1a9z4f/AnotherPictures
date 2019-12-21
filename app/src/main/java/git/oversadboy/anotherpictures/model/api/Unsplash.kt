package git.oversadboy.anotherpictures.model.api

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException

object Unsplash {
    private const val BASE_URL = "https://api.unsplash.com/"
    const val BASE_URL_POST = "https://unsplash.com/"
    const val SECRET_KEY = "2c0c9ffb15a127e17f40d329ef5413a0366395cc9df9259a396749cf10f4942e"
    const val REDIRECT_URI = "urn:ietf:wg:oauth:2.0:oob"
    const val ACCESS_KEY = "8ca469738bc1505a8f089eb6a904a4bdb380755aaddf6ab49b7d16ce427b2b78"
    const val UNSPLASH_UPLOAD_URL = "https://unsplash.com/submit"

}

class HeaderInterceptorPost internal constructor(private val token: String) : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        request = request.newBuilder()
            .addHeader("Authorization", "Bearer $token")
            .build()
        return chain.proceed(request)
    }
}
