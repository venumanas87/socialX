package xyz.v.socialx.network

import retrofit2.Response
import retrofit2.http.*
import xyz.v.socialx.models.News

interface ApiService {

    @Headers("x-api-key:e50630e12e244a4781090bf2e7a8054e")
    @GET("everything")
    suspend fun fetchAll(@Query("q") q:String): Response<News>
}