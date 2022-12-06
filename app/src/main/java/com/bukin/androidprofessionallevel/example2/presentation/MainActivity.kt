package com.bukin.androidprofessionallevel.example2.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bukin.androidprofessionallevel.R
import com.bukin.androidprofessionallevel.example2.ExampleApplication
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModel: ExampleViewModel

    private val component by lazy {
        (application as ExampleApplication).component
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        // Как правило inject лучше вставлять перед super.onCreate()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel.method()
    }
}