package ru.spbstu.icc.kspt.lab2.continuewatch

import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class JavaThreads : AppCompatActivity() {
    var secondsElapsed: Int = 0
    var work = true
    private lateinit var state: SharedPreferences
    var backgroundThread = Thread {
        while (work) {
            Thread.sleep(1000)
            textSecondsElapsed.post {
                textSecondsElapsed.setText("Seconds elapsed: " + secondsElapsed++)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.d("test", "onCreate()")
        backgroundThread.start()
        state = applicationContext.getSharedPreferences("state",
            MODE_PRIVATE)
    }

    override fun onResume() {
        super.onResume()
        Log.d("test", "onResume()")
        secondsElapsed = state.getInt("seconds", 0)
        work = true

    }

    override fun onPause() {
        super.onPause()
        Log.d("test", "onPause()")
        work = false
        val save = state.edit()
        save.putInt("seconds", secondsElapsed)
        save.apply()
    }
}
