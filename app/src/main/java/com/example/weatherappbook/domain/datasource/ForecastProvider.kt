package com.example.weatherappbook.domain.datasource

import com.example.weatherappbook.data.db.ForecastDb
import com.example.weatherappbook.data.server.ForecastServer
import com.example.weatherappbook.domain.model.ForecastList
import com.example.weatherappbook.extensionsl.firstResult

class ForecastProvider(private val sources: List<ForecastDataSource> = ForecastProvider.SOURCES as List<ForecastDataSource>)
{
    companion object {
        const val DAY_IN_MILLIS = 1000 * 60 * 60 * 24
        val SOURCES = listOf(ForecastDb(), ForecastServer())
    }

    fun requestByZipCode(zipCode: Long, days: Int): ForecastList
            = sources.firstResult { requestSource(it, days, zipCode) }

    private fun requestSource(source: ForecastDataSource, days: Int, zipCode: Long): ForecastList? {
        val res = source.requestForecastByZipCode(zipCode, todayTimeSpan())
        return if (res != null && res.size >= days) res else null
    }
    //compute time in miliseconds for the current day
    private fun todayTimeSpan() = System.currentTimeMillis() / DAY_IN_MILLIS * DAY_IN_MILLIS
}