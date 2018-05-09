package edu.washington.wynhsu.quizdroid

import android.content.pm.PackageManager
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.util.Log
import com.google.gson.GsonBuilder
import java.io.File


class QuizApp constructor(): android.app.Application() {

    companion object QuizApp: TopicRepository {
        private val INSTANCE = QuizApp
        val data = mutableListOf<Topic>()

        override fun add(t: Topic) {
            data.add(t)
        }

        override fun remove(t: Topic) {
            data.remove(t)
        }

        override fun get(): MutableList<Topic> {
            return data
        }

        fun getInstance(): QuizApp {
            return INSTANCE
        }
    }

    init {
        val mshQ1 = Questions("Question 1: Which character isn't part of The Avengers in the MCU?", 1,
                arrayOf("Black Panther", "Wolverine", "Doctor Strange", "Scarlet Witch"))
        val mshQ2 = Questions("Question 2: Which Infinity Stone is mounted on top of The vision's forehead?", 0,
                arrayOf("Mind Stone", "Power Stone", "Time Stone", "Space Stone"))
        val mshT = Topic("Marvel Super Heroes", "Avengers Assemble!",
                "Think you know everything about the Marvel Cinematic Universe? Take this quiz and find out!",
                "ICON", arrayOf(mshQ1, mshQ2))
        INSTANCE.add(mshT)

        val mathQ1 = Questions("Question 1: What is the name for the longest side of a right triangle?", 3,
                arrayOf("Pascale", "Adjacent", "Opposite", "Hypotenuse"))
        val mathQ2 = Questions("Question 2: How many sides are on a heptagon?", 2,
                arrayOf("11", "6", "7", "9"))
        val mathT =Topic("Math", "Logic & Computation",
                "Self-proclaimed math genius? Find out with this trivia challenge!",
                "ICON", arrayOf(mathQ1, mathQ2))
        INSTANCE.add(mathT)

        val physQ1 = Questions("Question 1: What is the term used to denote the tendency of an object to remain" +
                " in a state of rest until acted upon by an external force?", 1,
                arrayOf("Acceleration", "Inertia", "Momentum", "Friction"))
        val physQ2 = Questions("Question 2: What can be expressed as the number of cycles of a vibration occurring" +
                " per unit of time?", 0,
                arrayOf("Frequency", "Wave", "Period", "Pitch"))
        val physT = Topic("Physics", "Science & Engineering",
                "I know, nobody likes physics..but here's a quiz on it anyway!",
                "ICON", arrayOf(physQ1, physQ2))
        INSTANCE.add(physT)
    }

    override fun onCreate() {
        super.onCreate()
        Log.i("loaded", "QuizApp loaded")
        permissions()
        fetchJSON()
    }

    private fun permissions() {
        val permission = ContextCompat.checkSelfPermission(applicationContext, android.Manifest.permission.READ_EXTERNAL_STORAGE)
        if(permission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity(), arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE), 1)
        }
    }

    private fun fetchJSON() {
        val json = File("./sdcard/questions.json")
        val gson = GsonBuilder().setPrettyPrinting().create()
        val topics = gson.fromJson(json.reader(), TopicII::class.java)
    }
}

class TopicII(val title: String,
            val desc: String,
            val questions: Array<Questions>)