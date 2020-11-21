package com.example.weatherappbook.activities

import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.Toolbar
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weatherappbook.R
import com.example.weatherappbook.adapters.ForecastListAdapter
import com.example.weatherappbook.domain.commands.RequestForecastCommand
import com.example.weatherappbook.extensions.DelegatesExt
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.find
import org.jetbrains.anko.uiThread
import org.jetbrains.anko.startActivity

class MainActivity : CoroutineScopeActivity(), ToolbarManager {

    private var zipCode: Long by DelegatesExt.preference(
        this,
        SettingsActivity.ZIP_CODE,
        SettingsActivity.DEFAULT_ZIP
    )

    override val toolbar by lazy{ find<Toolbar>(R.id.toolbar)}

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initToolbar()

        forecast_list.layoutManager = LinearLayoutManager(this)
        attachToScroll(forecast_list)
    }

    override fun onResume() {
        super.onResume()
        loadForecast()
    }

    private fun loadForecast() =  launch {
        val result = withContext(Dispatchers.IO) { RequestForecastCommand(zipCode).execute() }
        val adapter = ForecastListAdapter(result) {
            startActivity<DetailActivity>(
                    DetailActivity.ID to it.id,
                    DetailActivity.CITY_NAME to result.city
                )
        }
        forecast_list.adapter = adapter
        toolbarTitle = "${result.city} (${result.country})"
    }

}
