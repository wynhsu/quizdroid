package edu.washington.wynhsu.quizdroid

import android.util.Log

class QuizApp private constructor(): android.app.Application(), TopicRepository {

    private val INSTANCE = QuizApp()

//    private QuizApp(){}

    fun getInstance(): QuizApp {
        return (INSTANCE)
    }

    override fun add(t: Topic) {}
    override fun update(t: Topic) {}
    override fun remove(t: Topic) {}

    override fun onCreate() {
        super.onCreate()
        Log.i("loaded", "QuizApp loaded")
    }
}