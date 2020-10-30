package dev.juanfe.instaflix.data.remote

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dev.juanfe.instaflix.data.models.Movie
import dev.juanfe.instaflix.data.remote.responses.MovieResponse
import io.reactivex.Single
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import org.koin.java.KoinJavaComponent
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import java.util.concurrent.TimeUnit
const val API_KEY = "6fad5634762f57f5c4c61d1fb085f5a7"
const val URL = "https://api.themoviedb.org/3/"
const val IMAGE_URL = "http://image.tmdb.org/t/p/w342"
const val FIRST_PAGE = 1
const val POST_PER_PAGE = 20
interface ApiCalls {
    @GET("/movie/popular")
    fun getMovies(@Query("page") page: Int) : Single<MovieResponse>

    companion object{
        operator fun invoke(
            networkConnectionInterceptor: NetworkConnectionInterceptor
        ) : ApiCalls {



            //interceptor to add api key
            val requestInterceptor = Interceptor { chain ->


                val url = chain.request()
                    .url()
                    .newBuilder()
                    .addQueryParameter("api_key", API_KEY)
                    .build()

                val request = chain.request()
                    .newBuilder()
                    .url(url)
                    .build()

                return@Interceptor chain.proceed(request)   //explicitly return a value from whit @ annotation. lambda always returns the value of the last expression implicitly
            }

            val moshi = Moshi.Builder()
                .add(KotlinJsonAdapterFactory())
                .build()

            //we defined client to add interceptor and timeout
            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(requestInterceptor)
                .addInterceptor(networkConnectionInterceptor)
                .connectTimeout(30, TimeUnit.SECONDS)
                .build()

            return Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(URL)
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .build()
                .create(ApiCalls::class.java)
        }
    }
}