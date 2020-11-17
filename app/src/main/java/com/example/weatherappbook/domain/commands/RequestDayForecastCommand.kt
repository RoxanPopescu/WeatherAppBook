package com.example.weatherappbook.domain.commands

import com.example.weatherappbook.domain.datasource.ForecastProvider
import com.example.weatherappbook.domain.model.Forecast

class RequestDayForecastCommand(
    val id: Long,
    private val forecastProvider: ForecastProvider = ForecastProvider()
) :
    Command<Forecast> {

    override fun execute() = forecastProvider.requestForecast(id)
}