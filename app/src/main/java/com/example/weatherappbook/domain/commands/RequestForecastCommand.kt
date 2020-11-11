package com.example.weatherappbook.domain.commands

import com.example.weatherappbook.data.server.ForecastRequest
import com.example.weatherappbook.domain.mappers.ForecastDataMapper
import com.example.weatherappbook.domain.model.ForecastList

class RequestForecastCommand(private val zipCode: Long) : Command<ForecastList> {

    override fun execute(): ForecastList {
        val forecastRequest = ForecastRequest(zipCode)
        return ForecastDataMapper().convertFromDataModel(
            zipCode, forecastRequest.execute()
        )
    }
}