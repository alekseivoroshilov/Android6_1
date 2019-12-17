package com.example.lab2_4

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.AsyncTask
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_async.*
import java.util.concurrent.TimeUnit

class AsyncActivity : AppCompatActivity() {

    private lateinit var tv: TextView
    private lateinit var sharedPreferences: SharedPreferences
    var elapsed: Int = 0
    private lateinit var timerTask: TimerTask
    private val PREFS_ASYNK = "PREFS_ASYNK"
    private val ELLAPSED_ASYNK = "ELLAPSED_ASYNK"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_async)
        tv = findViewById(R.id.activity_asynс_seconds_elapsed_tv)
        sharedPreferences = getSharedPreferences(
            PREFS_ASYNK,
            Context.MODE_PRIVATE
        )

        activity_asynс_to_coroutine_button.setOnClickListener {
            startActivity(Intent(this, CoroutineActivity::class.java))
        }
    }

    override fun onPause() {
        timerTask.cancel(true)
        super.onPause()
    }

    override fun onResume() {
        timerTask = TimerTask()
        timerTask.execute()
        super.onResume()
    }

    override fun onStop() {
        super.onStop()
        sharedPreferences.edit().putInt(ELLAPSED_ASYNK, elapsed).apply()
    }

    @SuppressLint("SetTextI18n")
    override fun onStart() {
        super.onStart()
        if (sharedPreferences.contains(ELLAPSED_ASYNK)) {
            elapsed = sharedPreferences.getInt(ELLAPSED_ASYNK, 0)
            tv.text = "Seconds elapsed: $elapsed"
            elapsed++
        }
    }


    @SuppressLint("StaticFieldLeak")
    private inner class TimerTask internal constructor() : AsyncTask<Void, Void, Void>() {

        override fun doInBackground(vararg params: Void?): Void? {
            while (!isCancelled) {
                TimeUnit.SECONDS.sleep(1)
                publishProgress()
            }
            return null
        }

        @SuppressLint("SetTextI18n")
        override fun onProgressUpdate(vararg values: Void?) {
            super.onProgressUpdate(*values)
            tv.text = "Seconds elapsed: $elapsed"
            elapsed++
        }
    }
}
