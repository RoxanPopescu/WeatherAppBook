package com.example.weatherappbook.activities

import android.annotation.SuppressLint
import android.os.Build
import android.widget.Toolbar
import androidx.annotation.RequiresApi
import androidx.appcompat.graphics.drawable.DrawerArrowDrawable
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherappbook.App
import com.example.weatherappbook.R
import com.example.weatherappbook.extensions.ctx
import com.example.weatherappbook.extensions.slideEnter
import com.example.weatherappbook.extensions.slideExit
import org.jetbrains.anko.toast

interface ToolbarManager {
    val toolbar: Toolbar

    var toolbarTitle: String
        @SuppressLint("NewApi")
        get() = toolbar.title.toString()
        @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
        set(value) {
            toolbar.title = value
        }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    fun initToolbar() {
        toolbar.inflateMenu(R.menu.menu_main)
        toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.action_settings -> App.instance.toast("Settings")
                else -> App.instance.toast("Unknown option")
            }
            true
        }
    }

    //add a function that enables the navigation icon in the toolbar
    // sets an arrow icon and a listener that will be fired when the icon is pressed

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    fun enableHomeAsUp(up: () -> Unit) {
        toolbar.navigationIcon = createUpDrawable()
        toolbar.setNavigationOnClickListener { up() }
    }

    private fun createUpDrawable() =
        DrawerArrowDrawable(toolbar.ctx).apply { progress = 1f }

    fun attachToScroll(recyclerView: RecyclerView) {
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (dy > 0) toolbar.slideExit() else toolbar.slideEnter()
            }
        })
    }
}
