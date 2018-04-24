package edu.washington.wynhsu.quizdroid

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_topics.*

class TopicsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_topics)
    }

    override fun onResume() {
        super.onResume()
        val topic = intent.getParcelableExtra<Topic>("topic")
        txtTitle.text = topic.name
        txtDescr.text = topic.descr
        val count = "Total Questions: " + topic.questions.size.toString()
        txtCount.text = count

        btnBegin.setOnClickListener {
            val intent = Intent(this, QuestionsActivity::class.java).apply {
                putExtra("topic", topic)
                putExtra("question", 0)
                putExtra("correct", 0)
                putExtra("incorrect", 0)
            }
            startActivity(intent)
        }
    }
}
