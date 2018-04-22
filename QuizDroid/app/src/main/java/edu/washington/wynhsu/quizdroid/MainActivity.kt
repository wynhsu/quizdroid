package edu.washington.wynhsu.quizdroid

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.activity_main.*

const val EXTRA_MARVEL = "edu.washington.wynhsu.quizdroid.MARVEL"

class MainActivity : AppCompatActivity() {

    val categories = arrayOf("Math", "Physics", "Marvel Super Heroes")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        listView.adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, categories)

    }

    override fun onResume() {
        super.onResume()
        listView.onItemClickListener = object: AdapterView.OnItemClickListener {
            override fun onItemClick(parent: AdapterView<*>?, view: View, position: Int, id: Long) {
                val selected = categories.get(position)
                getView(view, selected)
            }
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

        if (name.equals("Marvel Super Heroes")) {
            val intent = Intent(this, TopicsActivity::class.java).apply {
                putExtra(EXTRA_MARVEL, mshT)
            }
            startActivity(intent)
        }
    }
}
