package com.example.guide

import android.app.Application
import com.example.guide.data.AppContainer
import com.example.guide.data.AppDataContainer

class GuideApplication : Application() {

    /**
     * AppContainer instance used by the rest of classes to obtain dependencies
     */
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = AppDataContainer(this)
    }
}
