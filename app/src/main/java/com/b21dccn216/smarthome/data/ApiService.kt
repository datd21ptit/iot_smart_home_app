package com.b21dccn216.smarthome.data

import com.b21dccn216.smarthome.model.DashboarUiState
import com.b21dccn216.smarthome.model.TableResponse
import kotlinx.coroutines.flow.Flow
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

private const val BASE_URL =
    "http://192.168.1.10:3001"

/**
 * Use the Retrofit builder to build a retrofit object using a kotlinx.serialization converter
 */
val retrofit: Retrofit = Retrofit.Builder()
    .baseUrl(BASE_URL)
    .addConverterFactory(GsonConverterFactory.create())
    .client(OkHttpClient.Builder().build()) // Optional: Customize OkHttp
    .build()

interface ApiService {
    @GET("/dashboard")
    suspend fun getSensorData() : List<DashboarUiState>

    @POST("/dashboard")
    suspend fun sendAction(
        @Query("led") led: String,
        @Query("relay") relay: String,
        @Query("fan") fan: String
    ): Response<Void>

//    @GET("/chart")
//    suspend fun getChartData(
//        @Query("type") type: String
//    ): List<Int>

    @GET("table/sensor")
    suspend fun getSensorTable(
        @Query("page") page: String,
        @Query("temp") temp: String,
        @Query("humid") humid: String,
        @Query("light") light: String,
        @Query("time") time: String,
    ): TableResponse

    @GET("table/action")
    suspend fun getActionTable(
        @Query("page") page: String,
        @Query("temp") led:String,
        @Query("humid") fan: String,
        @Query("light") relay: String,
        @Query("time") time: String,
    ): TableResponse
}





