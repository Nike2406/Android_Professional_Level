package com.bukin.androidprofessionallevel.example2.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bukin.androidprofessionallevel.R
import com.bukin.androidprofessionallevel.example2.di.DaggerApplicationComponent
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModel: ExampleViewModel

    private val component by lazy {
        DaggerApplicationComponent.builder()
            .context(application)
            .timeInMillis(System.currentTimeMillis())
            .build()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        // Как правило inject лучше вставлять перед super.onCreate()
        component.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel.method()
    }
}