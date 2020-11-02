package dev.juanfe.instaflix

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dev.juanfe.instaflix.data.models.Movie
import dev.juanfe.instaflix.data.remote.API_KEY
import dev.juanfe.instaflix.data.remote.ApiCalls
import dev.juanfe.instaflix.data.remote.NetworkConnectionInterceptor
import dev.juanfe.instaflix.data.remote.responses.MovieResponse
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import java.net.HttpURLConnection
import java.util.concurrent.TimeUnit

class ApiTest {
    private var mockWebServer = MockWebServer()
    private lateinit var apiService: ApiCalls
    val requestInterceptor = Interceptor { chain ->


        val url = chain.request()
            .url
            .newBuilder()
            .addQueryParameter("api_key", API_KEY)
            .build()

        val request = chain.request()
            .newBuilder()
            .url(url)
            .build()

        return@Interceptor chain.proceed(request)   //explicitly return a value from whit @ annotation. lambda always returns the value of the last expression implicitly
    }
    val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(requestInterceptor)
        .connectTimeout(60, TimeUnit.SECONDS)
        .build()
    val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    @Before
    fun setup() {
        mockWebServer.start()
        apiService = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(okHttpClient)
            .build()
            .create(ApiCalls::class.java)
    }

    @After
    fun teardown() {
        mockWebServer.shutdown()
    }

    @Test
    fun testDetailsMovie() {
        // Assign
        val response = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_OK)
            .setBody("{\n" +
                    "  \"id\": 760075,\n" +
                    "  \"overview\": \"\",\n" +
                    "  \"poster_path\": null,\n" +
                    "  \"release_date\": \"\",\n" +
                    "  \"title\": \"MAL DE CHIEN\",\n" +
                    "  \"vote_average\": 0\n" +
                    "}")
        mockWebServer.enqueue(response)
        // Act
        val movie: Movie = apiService.getMovieDetails(760075).blockingGet()
        // Assert
        assert(movie.id == 760075)
    }

    @Test
    fun testGetMovies() {
        // Assign
        val response = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_OK)
            .setBody("{\n" +
                    "  \"page\": 1,\n" +
                    "  \"total_results\": 10000,\n" +
                    "  \"total_pages\": 500,\n" +
                    "  \"results\": [\n" +
                    "    {\n" +
                    "      \"popularity\": 3089.11,\n" +
                    "      \"vote_count\": 50,\n" +
                    "      \"video\": false,\n" +
                    "      \"poster_path\": \"/ugZW8ocsrfgI95pnQ7wrmKDxIe.jpg\",\n" +
                    "      \"id\": 724989,\n" +
                    "      \"adult\": false,\n" +
                    "      \"backdrop_path\": \"/86L8wqGMDbwURPni2t7FQ0nDjsH.jpg\",\n" +
                    "      \"original_language\": \"en\",\n" +
                    "      \"original_title\": \"Hard Kill\",\n" +
                    "      \"genre_ids\": [\n" +
                    "        28,\n" +
                    "        53\n" +
                    "      ],\n" +
                    "      \"title\": \"Hard Kill\",\n" +
                    "      \"vote_average\": 4.3,\n" +
                    "      \"overview\": \"The work of billionaire tech CEO Donovan Chalmers is so valuable that he hires mercenaries to protect it, and a terrorist group kidnaps his daughter just to get it.\",\n" +
                    "      \"release_date\": \"2020-10-23\"\n" +
                    "    },\n" +
                    "    {\n" +
                    "      \"popularity\": 2968.909,\n" +
                    "      \"vote_count\": 425,\n" +
                    "      \"video\": false,\n" +
                    "      \"poster_path\": \"/betExZlgK0l7CZ9CsCBVcwO1OjL.jpg\",\n" +
                    "      \"id\": 531219,\n" +
                    "      \"adult\": false,\n" +
                    "      \"backdrop_path\": \"/8rIoyM6zYXJNjzGseT3MRusMPWl.jpg\",\n" +
                    "      \"original_language\": \"en\",\n" +
                    "      \"original_title\": \"Roald Dahl's The Witches\",\n" +
                    "      \"genre_ids\": [\n" +
                    "        14,\n" +
                    "        10751,\n" +
                    "        12,\n" +
                    "        35,\n" +
                    "        27\n" +
                    "      ],\n" +
                    "      \"title\": \"Roald Dahl's The Witches\",\n" +
                    "      \"vote_average\": 7.2,\n" +
                    "      \"overview\": \"In late 1967, a young orphaned boy goes to live with his loving grandma in the rural Alabama town of Demopolis. As the boy and his grandmother encounter some deceptively glamorous but thoroughly diabolical witches, she wisely whisks him away to a seaside resort. Regrettably, they arrive at precisely the same time that the world's Grand High Witch has gathered.\",\n" +
                    "      \"release_date\": \"2020-10-26\"\n" +
                    "    }\n" +
                    "  ]\n" +
                    "}")
        mockWebServer.enqueue(response)
        // Act
        val movieResponse: MovieResponse = apiService.getMovies(1).blockingGet()
        // Assert
        assert(movieResponse.page == 1)
        assert(movieResponse.movieGeneralList.isNotEmpty())
    }


}