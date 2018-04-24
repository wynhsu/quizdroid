package edu.washington.wynhsu.quizdroid

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.activity_main.*

//const val EXTRA_MARVEL = "edu.washington.wynhsu.quizdroid.MARVEL"
//const val EXTRA_MATH = "edu.washington.wynhsu.quizdroid.MATH"
//const val EXTRA_PHYSICS = "edu.washington.wynhsu.quizdroid.PHYSICS"

class MainActivity : AppCompatActivity() {

    val categories = arrayOf("Math", "Physics", "Marvel Super Heroes")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        listView.adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, categories)

    }

    override fun onResume() {
        super.onResume()
        listView.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
            val selected = categories[position]
            getView(view, selected)
        }
    }

    fun getView(view: View, name: String) {
        val mshQ1 = Questions("Question 1: Which character isn't part of The Avengers in the MCU?",
                arrayOf("Black Panther", "Wolverine", "Doctor Strange", "Scarlet Witch"), 1)
        val mshQ2 = Questions("Question 2: Which Infinity Stone is mounted on top of The vision's forehead?",
                arrayOf("Mind Stone", "Power Stone", "Time Stone", "Space Stone"), 0)
        val mshT = Topic("Marvel Super Heroes",
                "Think you know everything about the Marvel Cinematic Universe? Take this quiz and find out!",
                arrayOf(mshQ1, mshQ2))

        val mathQ1 = Questions("Question 1: What is the name for the longest side of a right triangle?",
                arrayOf("Pascale", "Adjacent", "Opposite", "Hypotenuse"), 3)
        val mathQ2 = Questions("Question 2: How many sides are on a heptagon?",
                arrayOf("11", "6", "7", "9"), 2)
        val mathT =Topic("Math",
                "Self-proclaimed math genius? Find out with this trivia challenge!",
                arrayOf(mathQ1, mathQ2))

        val physQ1 = Questions("Question 1: What is the term used to denote the tendency of an object to remain" +
                " in a state of rest until acted upon by an external force?",
                arrayOf("Acceleration", "Inertia", "Momentum", "Friction"), 1)
        val physQ2 = Questions("Question 2: What can be expressed as the number of cycles of a vibration occurring" +
                " per unit of time?",
                arrayOf("Frequency", "Wave", "Period", "Pitch"), 0)
        val physT = Topic("Physics",
                "I know, nobody likes physics..but here's a quiz on it anyway!",
                arrayOf(physQ1, physQ2))

        val intent = Intent(this, TopicsActivity::class.java).apply {
            when (name) {
                "Marvel Super Heroes" -> putExtra("topic", mshT)
                "Math" -> putExtra("topic", mathT)
                else -> putExtra("topic", physT)
            }
        }
        startActivity(intent)
    }
}
