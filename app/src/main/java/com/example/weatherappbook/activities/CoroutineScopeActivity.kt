/*
 * Game of Pods - Copyright (c) Cognizant Softvision 2020.
 * All rights reserved.
 */

package com.example.weatherappbook.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

abstract class CoroutineScopeActivity : AppCompatActivity(), CoroutineScope {

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    lateinit var job: Job

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        job = Job()
    }

    override fun onDestroy() {
        job.cancel()
        super.onDestroy()
    }
}