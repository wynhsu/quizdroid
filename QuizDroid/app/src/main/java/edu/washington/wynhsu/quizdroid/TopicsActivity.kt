package edu.washington.wynhsu.quizdroid

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_topics.*

const val EXTRA_MARVEL_Q = "edu.washington.wynhsu.quizdroid.MARVEL_Q"

class TopicsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_topics)
    }

    override fun onResume() {
        super.onResume()
        val topic = intent.getParcelableExtra<Topic>(EXTRA_MARVEL)
        txtTitle.setText(topic.name)
        txtDescr.setText(topic.descr)
        val count = "Total Questions: " + topic.questions.size.toString()
        txtCount.setText(count)

        btnBegin.setOnClickListener {
            val intent = Intent(this, QuestionsActivity::class.java).apply {
                putExtra(EXTRA_MARVEL_Q, "")
            }
            startActivity(intent)
        }
    }
}
