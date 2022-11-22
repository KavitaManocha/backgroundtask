package com.trial

import android.app.Service
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.util.Log
import android.widget.Toast
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking

class MainService : Service() {
    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        sampleRunBlocking()
        Toast.makeText(this, " MyService Created ", Toast.LENGTH_LONG).show()
        Log.i("Started", "Service Started")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return super.onStartCommand(intent, flags, startId)

        return START_STICKY;
    }

    override fun onTaskRemoved(rootIntent: Intent?) {
        super.onTaskRemoved(rootIntent)
        var restartServiceIntent = Intent(applicationContext, this::class.java)
        restartServiceIntent.`package`
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startService(restartServiceIntent)
        }

    }

    fun sampleRunBlocking() {
        var i = 0
        Log.i("Hello", i.toString())
        runBlocking {
            for (i in 0..10) {
                var j = i + 1
                delay(5000)
                Log.i("Hello", j.toString())
//                delay(5000)

            }

        }

    }
}