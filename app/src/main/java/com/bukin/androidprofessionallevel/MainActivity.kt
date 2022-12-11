package com.bukin.androidprofessionallevel

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.bukin.androidprofessionallevel.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
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
        val timer = binding.tvFirst
            val cor1 = CoroutineScope(Dispatchers.IO).launch {
                for (i in 5 downTo 1)  {
                    delay(1000)
                    CoroutineScope(Dispatchers.Main).launch {timer.text = i.toString()  }

                }
//                CoroutineScope(Dispatchers.Main).launch {
                    val intent: Intent = Intent(this@MainActivity, MainActivity2::class.java)
                    startActivity(intent)
//                }
                // this might be heavy CPU-consuming computation or async logic, we'll just send five squares
//                produce(channel)
            // here we print five received integers:
            }
//        CoroutineScope(Dispatchers.IO).launch{
//            cor1.join()
//            val intent: Intent = Intent(this@MainActivity, MainActivity2::class.java)
//            startActivity(intent)
//        }
//        CoroutineScope(Dispatchers.Default).launch {
//            consume(channel)
//        }
    }

    suspend fun produce(channel: Channel<Int>) {
        for (i in 0..REPEAT_TIMES) {
            Log.d("FUCK", "Sent $i")
            delay(1000)
//            channel.send(i)
        }
    }

    suspend fun consume(channel: Channel<Int>) {
        repeat(REPEAT_TIMES) {
            Log.d(TAG, "Received ${channel.receive()}")
        }
    }

    companion object {
        const val REPEAT_TIMES = 10
        const val TAG = "FUCK"
    }
}