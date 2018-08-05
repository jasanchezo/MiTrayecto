package com.mtw.josealejandrosanchezortega.mitrayecto.api

import io.reactivex.Observable
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface VehiclesApiService {
    // PARTE DE LA URL QUE ES VARIABLE
    @GET("getVehicles")
    fun getVehicles() : Observable<List<VehiclesModel.VehicleModel>>

    companion object {
        fun create() : VehiclesApiService {
            val retrofit = Retrofit.Builder()
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl("http://192.168.1.78:3000/")
                    .build()

            return retrofit.create(VehiclesApiService::class.java)
        }
    }
}