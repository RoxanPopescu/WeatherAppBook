package com.example.weatherappbook.domain.commands

import com.example.weatherappbook.domain.datasource.ForecastProvider
import com.example.weatherappbook.domain.model.Forecast
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RequestDayForecastCommand(
    val id: Long,
    private val forecastProvider: ForecastProvider = ForecastProvider()
) :
    Command<Forecast> {

    override suspend fun execute() = withContext(Dispatchers.IO) {
        forecastProvider.requestForecast(id)
    }
}