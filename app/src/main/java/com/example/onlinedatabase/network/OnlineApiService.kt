package com.example.onlinedatabase.network


import com.example.onlinedatabase.database.GoogleUser
import com.example.onlinedatabase.database.Users
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.concurrent.TimeUnit


private const val BASE_URL = "http://shik-shop.ir/loginapp/"

interface OnlineApiService {

    @GET("register.php")
    fun createUser(
        @Query("name") name: String,
        @Query("username") username: String,
        @Query("password") password: String
    ): retrofit2.Call<Users>

    @GET("login.php")
    fun findUser(
        @Query("username") username: String,
        @Query("password") password: String
    ): retrofit2.Call<Users>

    @GET("google_register.php")
    fun google_CreateUser(
        @Query("email") email: String,
        @Query("name") name: String,
        @Query("image_url") image_url: String
    ): Call<GoogleUser>

    @GET("google_login.php")
    fun google_FindEmail(@Query("email") email: String): Call<GoogleUser>

    object OnlineApi {
        val retrofitService: OnlineApiService by lazy {
            UnsafeOkHttpClient()
                .getRetrofit().create(OnlineApiService::class.java)
        }
    }

    class UnsafeOkHttpClient {
        fun getRetrofit(): Retrofit {
            return Retrofit.Builder().baseUrl(BASE_URL)
                .client(initOkHttp())
                .addConverterFactory(
                    MoshiConverterFactory.create()
                ).build()
        }

        private fun initOkHttp(): OkHttpClient {
            val REQUEST_TIMEOUT = 60
            val httpClient = OkHttpClient().newBuilder()
                .connectTimeout(REQUEST_TIMEOUT.toLong(), TimeUnit.SECONDS)
                .readTimeout(REQUEST_TIMEOUT.toLong(), TimeUnit.SECONDS)
                .writeTimeout(REQUEST_TIMEOUT.toLong(), TimeUnit.SECONDS)
            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY
            httpClient.addInterceptor(interceptor)
            httpClient.addInterceptor { chain ->
                val original: Request = chain.request()
                val requestBuilder: Request.Builder = original.newBuilder()
                    .addHeader("Accept", "application/json")
                    .addHeader("Content-Type", "application/json")
                val request: Request = requestBuilder.build()
                chain.proceed(request)
            }
            return httpClient.build()
        }
    }
}