package com.bukin.androidprofessionallevel

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.bukin.androidprofessionallevel.databinding.ActivityMainBinding
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.buttonLoad.setOnClickListener {
            loadData()
        }
    }

    // CallbackHell
    private fun loadData() {
        binding.progress.isVisible = true
        binding.buttonLoad.isEnabled = false
        // Код внутри коллбека будет асинхнонным, т.е.
        // вызываться после окончания загрузки
        loadCity { it1 ->
            binding.tvLocation.text = it1
            loadTemperature(it1) {
                // Далее, новые коллбеки также будут
                // вложенными
                binding.tvTemperature.text = it.toString()
                binding.progress.isVisible = false
                binding.buttonLoad.isEnabled = true
            }
        }
        // После коллбека код будет вызываться мговенно
        Log.d("FUCK", "Here is Jonny!")
    }

    private fun loadCity(callback: (String) -> Unit) {
        thread {
            Thread.sleep(5000)
            callback.invoke("Moscow")
        }
    }

    private fun loadTemperature(city: String, callback: (Int) -> Unit) {
        thread {
            Toast.makeText(
                this,
                getString(R.string.loading_temperature_toast, city),
                Toast.LENGTH_SHORT
            ).show()
        }
        Thread.sleep(5000)
        callback.invoke(17)
    }
}