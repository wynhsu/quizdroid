package edu.washington.wynhsu.quizdroid

import android.util.Log

class QuizApp private constructor(): android.app.Application() {

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

//    private QuizApp(){}
    init {
        val mshQ1 = Questions("Question 1: Which character isn't part of The Avengers in the MCU?",
                arrayOf("Black Panther", "Wolverine", "Doctor Strange", "Scarlet Witch"), 1)
        val mshQ2 = Questions("Question 2: Which Infinity Stone is mounted on top of The vision's forehead?",
                arrayOf("Mind Stone", "Power Stone", "Time Stone", "Space Stone"), 0)
        val mshT = Topic("Marvel Super Heroes", "Avengers Assemble!",
                "Think you know everything about the Marvel Cinematic Universe? Take this quiz and find out!",
                arrayOf(mshQ1, mshQ2))
        INSTANCE.add(mshT)

        val mathQ1 = Questions("Question 1: What is the name for the longest side of a right triangle?",
                arrayOf("Pascale", "Adjacent", "Opposite", "Hypotenuse"), 3)
        val mathQ2 = Questions("Question 2: How many sides are on a heptagon?",
                arrayOf("11", "6", "7", "9"), 2)
        val mathT =Topic("Math", "Logic & Computation",
                "Self-proclaimed math genius? Find out with this trivia challenge!",
                arrayOf(mathQ1, mathQ2))
        INSTANCE.add(mathT)

        val physQ1 = Questions("Question 1: What is the term used to denote the tendency of an object to remain" +
                " in a state of rest until acted upon by an external force?",
                arrayOf("Acceleration", "Inertia", "Momentum", "Friction"), 1)
        val physQ2 = Questions("Question 2: What can be expressed as the number of cycles of a vibration occurring" +
                " per unit of time?",
                arrayOf("Frequency", "Wave", "Period", "Pitch"), 0)
        val physT = Topic("Physics", "Science & Engineering",
                "I know, nobody likes physics..but here's a quiz on it anyway!",
                arrayOf(physQ1, physQ2))
        INSTANCE.add(physT)
    }

    override fun onCreate() {
        super.onCreate()
        Log.i("loaded", "QuizApp loaded")
    }
}