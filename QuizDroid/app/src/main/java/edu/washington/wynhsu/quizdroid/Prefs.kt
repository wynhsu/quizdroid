package edu.washington.wynhsu.quizdroid

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.os.Environment
import android.os.SystemClock
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.prefs.*
import okhttp3.*
import java.io.File
import java.io.IOException
import java.io.PrintWriter

class Prefs : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.prefs)

        btnSave.setOnClickListener {
            val url = txtURL.text.toString()
            val frequency = txtDL.text.toString()

            if (url != "" && frequency != "") {
                val intent = Intent("edu.washington.wynhsu.quizdroid").apply {
                    putExtra("url", url)
                    putExtra("fetch", 0)
                }
                val intentFilter = IntentFilter("edu.washington.wynhsu.quizdroid")
                registerReceiver(AlarmReceiver(), intentFilter)
                val pendingIntent = PendingIntent.getBroadcast(this, 0, intent, 0)
                val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager

                alarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP,
                        SystemClock.elapsedRealtime() + (frequency.toInt() * 1000).toLong(),
                        (frequency.toInt() * 1000).toLong(), pendingIntent)
            }
//            val switch = Intent(this, MainActivity::class.java)
//            startActivity(switch)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(AlarmReceiver())
    }
}