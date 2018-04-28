package edu.washington.wynhsu.quizdroid

import android.util.Log

class QuizApp: android.app.Application() {

    override fun onCreate() {
        super.onCreate()
        Log.i("loaded", "QuizApp loaded")
    }

    interface TopicRepository {

    }
}