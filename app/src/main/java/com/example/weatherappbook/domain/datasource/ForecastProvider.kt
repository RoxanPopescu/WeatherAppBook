package com.example.weatherappbook.domain.datasource

import com.example.weatherappbook.data.db.ForecastDb
import com.example.weatherappbook.data.server.ForecastServer
import com.example.weatherappbook.domain.model.Forecast
import com.example.weatherappbook.domain.model.ForecastList
import com.example.weatherappbook.extensionsl.firstResult

class ForecastProvider(private val sources: List<ForecastDataSource> = SOURCES) {
    companion object {
        const val DAY_IN_MILLIS = 1000 * 60 * 60 * 24
        val SOURCES = listOf(ForecastDb(), ForecastServer())
    }

    fun requestByZipCode(zipCode: Long, days: Int): ForecastList = requestToSources {
        val res = it.requestForecastByZipCode(zipCode, todayTimeSpan())
        if (res != null && res.size >= days) res else null
    }

    fun requestForecast(id: Long): Forecast = requestToSources { it.requestDayForecast(id) }

    //function that returns the requested forecast by id
    private fun <T : Any> requestToSources(f: (ForecastDataSource) -> T?): T =
        sources.firstResult { f(it) }

    //compute time in miliseconds for the current day
    private fun todayTimeSpan() = System.currentTimeMillis() / DAY_IN_MILLIS * DAY_IN_MILLIS
}