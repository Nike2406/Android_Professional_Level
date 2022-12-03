package com.bukin.androidprofessionallevel

import android.os.Bundle
import android.os.Handler
import android.os.Looper
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

    /*
    * Handler
    * Для того, чтобы разные потоки могли общаться друг с другом
    * и передвать между собой данные, в android был добавлен
    * класс Handler.
    * Объект этого класса можно создать на главном потоке. Затем
    * из любого потока ему можно передавать объекты Runnable и
    * тогда метод run() будет вызван на главном потоке.
    *
    * Handler отправляет сообщения в Looper и, соответсвенно
    * H не может быть вызван не из главного потока без Looper.prepare()
    * */

    // CallbackHell
    private fun loadData() {
        Log.d("MainActivity", "Load started: $this")
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
        Log.d("MainActivity", "Load finished: $this")
    }

    private fun loadCity(callback: (String) -> Unit) {
        // Не главный поток
        thread {
            Thread.sleep(5000)
            // Для явного указания потока, в handler
            // нужно передать Looper с соотв. типом
            runOnUiThread {
                // Метод будет вызван в главном потоке через handler,
                // (т.к. handler был объявлен на гл-м потоке)
                callback.invoke("Moscow")
            }
            // Если H вызывать не из главного потоко,
            // то следует использовать Looper.myLooper()?,
            // обработать null и вызв-ть Looper.prepare()
        }
    }

    private fun loadTemperature(city: String, callback: (Int) -> Unit) {
        thread {
            // Handler(Looper.getMainLooper()).post{} -> runOnUiThread{}
            runOnUiThread {
                Toast.makeText(
                    this,
                    getString(R.string.loading_temperature_toast, city),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
        Thread.sleep(5000)
        Handler(Looper.getMainLooper()).post {
            callback.invoke(17)
        }
    }
}