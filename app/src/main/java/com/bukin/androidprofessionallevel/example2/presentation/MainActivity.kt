package com.bukin.androidprofessionallevel.example2.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bukin.androidprofessionallevel.R
import com.bukin.androidprofessionallevel.example1.Activity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val activity = Activity()
        activity.keyboard.toString()
    }
}