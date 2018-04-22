package edu.washington.wynhsu.quizdroid

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
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
                when (selected) {
                    "Marvel Super Heroes" -> getMarvel(view)
                }
            }
        }
    }

    fun getMarvel(view: View) {
        val intent = Intent(this, TopicsActivity::class.java).apply {
            putExtra(EXTRA_MARVEL, "")
        }
        startActivity(intent)
    }
}
