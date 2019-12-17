package com.example.lab2_4

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_coroutine.*
import kotlinx.coroutines.*

class CoroutineActivity : AppCompatActivity() {

    private val viewScope = CoroutineScope(Dispatchers.Main)
    private lateinit var sharedPreferences: SharedPreferences
    private var elapsed: Int = 0
    private lateinit var jobTimew: Job
    private val PREFS_COROUTINE = "PREFS_COROUTINE"
    private val ELAPSED_COROUTINE = "ELAPSED_COROUTINE"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coroutine)

        sharedPreferences = getSharedPreferences(
            PREFS_COROUTINE,
            Context.MODE_PRIVATE
        )
    }

    override fun onPause() {
        viewScope.cancel()
        super.onPause()
    }

    override fun onResume() {
        jobTimew = getTimer()
        super.onResume()
    }

    override fun onStop() {
        super.onStop()
        sharedPreferences.edit().putInt(ELAPSED_COROUTINE, elapsed).apply()
    }

    @SuppressLint("SetTextI18n")
    override fun onStart() {
        super.onStart()
        if (sharedPreferences.contains(ELAPSED_COROUTINE)) {
            elapsed = sharedPreferences.getInt(ELAPSED_COROUTINE, 0)
            activity_coroutine_seconds_elapsed_tv.text = "Seconds elapsed: $elapsed"
            elapsed++
        }
    }

    @SuppressLint("SetTextI18n")
    private fun getTimer() = startTimer {
        activity_coroutine_seconds_elapsed_tv.text = "Seconds elapsed: $elapsed"
        elapsed++
    }

    private fun startTimer(action: () -> Unit) =
        viewScope.launch {
            while (true) {
                delay(DELAY)
                action()
            }
        }
}
