package com.example.weatherappbook.domain.datasource

import com.example.weatherappbook.domain.model.Forecast
import com.example.weatherappbook.domain.model.ForecastList

interface ForecastDataSource {
    fun requestForecastByZipCode(zipCode: Long, date: Long): ForecastList?
    fun requestDayForecast(id: Long): Forecast?
}