package com.iqgroup.coinview.api

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.iqgroup.coinview.model.data.BitcoinPriceResponse
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory
import retrofit2.http.GET

/**
 * Provides an API for fetching the current Bitcoin price from CoinDesk.
 * Companion object sets up the Retrofit instance and configures Jackson.
 *
 * NOTE: CoinDesk implements some kind of caching on their side,
 * hence subsequent requests might not show an updating time.
 * This behaviour is the same via Postman.
 */
interface CoinDeskAPI {

    @GET("v1/bpi/currentprice.json")
    suspend fun getCurrentPrice(): BitcoinPriceResponse

    companion object {

        private val objectMapper = ObjectMapper().registerModules(
            KotlinModule.Builder().build()
        )

        private val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("https://api.coindesk.com/")
            .addConverterFactory(JacksonConverterFactory.create(objectMapper))
            .build()

        val api: CoinDeskAPI = retrofit.create(CoinDeskAPI::class.java)
    }

}

