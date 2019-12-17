package com.example.lab2_4


import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import kotlinx.android.synthetic.main.activity_thread.*

const val DELAY = 1000L

class ThreadActivity : AppCompatActivity() {
    private lateinit var sharedPreferences: SharedPreferences
    private var elapsed: Int = 0
    private val handler = Handler()
    private val PREFS_THREAD = "PREFS_THREAD"
    private val ELLAPSED_THREAD = "ELLAPSED_THREAD"

    @SuppressLint("SetTextI18n")
    override fun onStart() {
        super.onStart()
        if (sharedPreferences.contains(ELLAPSED_THREAD)) {
            elapsed = sharedPreferences.getInt(ELLAPSED_THREAD, 0)
            activity_thread_seconds_elapsed_tv.text = "Seconds elapsed: $elapsed"
            elapsed++
        }
    }

    override fun onResume() {
        super.onResume()
        handler.postDelayed(runnable, DELAY)
    }

    override fun onPause() {
        handler.removeCallbacks(runnable)
        super.onPause()
    }

    override fun onStop() {
        super.onStop()
        sharedPreferences.edit().putInt(ELLAPSED_THREAD, elapsed).apply()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_thread)
        sharedPreferences = getSharedPreferences(PREFS_THREAD, Context.MODE_PRIVATE)

        activity_thread_to_asynс_button.setOnClickListener {
            startActivity(
                Intent
                    (this, AsyncActivity::class.java)
            )
        }
    }

    private val runnable: Runnable = object : Runnable {
        @SuppressLint("SetTextI18n")
        override fun run() {
            activity_thread_seconds_elapsed_tv.post {
                activity_thread_seconds_elapsed_tv.text = "Seconds elapsed: $elapsed"
                elapsed++
            }
            handler.postDelayed(this, DELAY)
        }
    }
}