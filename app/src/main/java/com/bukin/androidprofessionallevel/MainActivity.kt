package com.bukin.androidprofessionallevel

import MyService
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bukin.androidprofessionallevel.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.simpleService.setOnClickListener {
            startService(MyService.newIntent(this))
        }
    }
}