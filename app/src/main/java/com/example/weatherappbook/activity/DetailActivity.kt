package com.example.weatherappbook.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.weatherappbook.R
import com.example.weatherappbook.domain.commands.RequestDayForecastCommand
import com.example.weatherappbook.domain.model.Forecast
import com.example.weatherappbook.extensions.color
import com.example.weatherappbook.extensions.textColor
import com.example.weatherappbook.extensions.toDateString
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.item_forecast.icon
import kotlinx.android.synthetic.main.item_forecast.maxTemperature
import kotlinx.android.synthetic.main.item_forecast.minTemperature
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import java.text.DateFormat

class DetailActivity : AppCompatActivity() {

    companion object {
        const val ID = "DetailActivity:id"
        const val CITY_NAME = "DetailActivity:cityName"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        title = intent.getStringExtra(CITY_NAME)

        doAsync {
            val result = RequestDayForecastCommand(intent.getLongExtra(ID, -1)).execute()
            uiThread { bindForecast(result) }
        }
    }

    private fun bindForecast(forecast: Forecast) = with(forecast) {
        Picasso.with(this@DetailActivity).load(iconUrl).into(icon)
        supportActionBar?.subtitle = date.toDateString(DateFormat.FULL)
        weatherDescription.text = description
        bindWeather(high to maxTemperature, low to minTemperature)
    }

    @SuppressLint("SetTextI18n")
    private fun bindWeather(vararg views: Pair<Int, TextView>) = views.forEach {
        it.second.text = "${it.first}º"
        it.second.textColor = color(
            when (it.first) {
                in -50..0 -> android.R.color.holo_red_dark
                in 0..15 -> android.R.color.holo_orange_dark
                else -> android.R.color.holo_green_dark
            }
        )
    }
}
