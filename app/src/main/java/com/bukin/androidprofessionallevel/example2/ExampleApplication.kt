package com.bukin.androidprofessionallevel.example2

import android.app.Application
import com.bukin.androidprofessionallevel.example2.di.DaggerApplicationComponent

class ExampleApplication : Application() {

    val component by lazy {
        DaggerApplicationComponent.factory()
            .create(this, System.currentTimeMillis())
    }
}