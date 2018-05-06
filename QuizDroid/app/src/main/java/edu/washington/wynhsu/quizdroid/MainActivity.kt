package edu.washington.wynhsu.quizdroid

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.SimpleAdapter
import edu.washington.wynhsu.quizdroid.R.id.*
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

//    val categories = arrayOf("Math", "Physics", "Marvel Super Heroes")
    val topicList = QuizApp.getInstance().get()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        listView.adapter = ArrayAdapter<Topic>(this, android.R.layout.simple_list_item_1, topicList)
//        listView.adapter = SimpleAdapter<String>(this, )
    }

    override fun onResume() {
        super.onResume()
        listView.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
            val selected = topicList.get(position)
            getView(view, selected)
        }
    }

    fun getView(view: View, t: Topic) {

        val intent = Intent(this, FragmentActivity::class.java).apply {
            putExtra("topic", t)
        }
        startActivity(intent)
    }
}
