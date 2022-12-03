package com.bukin.androidprofessionallevel

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.bukin.androidprofessionallevel.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch

/*
* Написать приложение, которое на одном активити отсчитывает таймер и когда он дойдет до 1,
* открыть второе активити.
* */
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    lateinit var channel: Channel<Int>
    lateinit var scope: CoroutineScope

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        runBlocking {
            val channel = Channel<Int>()
            CoroutineScope(Dispatchers.IO).launch {
                // this might be heavy CPU-consuming computation or async logic, we'll just send five squares
                produce(channel)
            // here we print five received integers:
            }
        CoroutineScope(Dispatchers.Default).launch {
            consume(channel)
        }
    }

    suspend fun produce(channel: Channel<Int>) {
        for (i in 0..REPEAT_TIMES) {
            Log.d("FUCK", "Sent $i")
            channel.send(i)
        }
    }

    suspend fun consume(channel: Channel<Int>) {
        repeat(REPEAT_TIMES) {
            Log.d("FUCK", "Received ${channel.receive()}")
        }
    }

    companion object {
        const val REPEAT_TIMES = 10
    }
}