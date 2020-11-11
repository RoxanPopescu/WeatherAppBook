package com.example.weatherappbook.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weatherappbook.R
import com.example.weatherappbook.adapters.ForecastListAdapter
import com.example.weatherappbook.domain.commands.RequestForecastCommand
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.toast
import org.jetbrains.anko.uiThread
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        forecast_list.layoutManager = LinearLayoutManager(this)

        doAsync {
            val result = RequestForecastCommand("94043").execute()
            uiThread {
                forecast_list.adapter = ForecastListAdapter(result) {
                    toast(it.date)
                }
            }
        }
    }
}
