package edu.washington.wynhsu.quizdroid

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_topics.*

const val EXTRA_MARVEL_Q = "edu.washington.wynhsu.quizdroid.MARVEL_Q"

class TopicsActivity : AppCompatActivity() {

    val q1a = arrayOf("Black Panther", "Wolverine", "Doctor Strange", "Scarlet Witch")
    val q2a = arrayOf("Mind Stone", "Power Stone", "Time Stone", "Space Stone")
    val marvelQs = hashMapOf(1 to q1a, 2 to q2a)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_topics)
    }

    override fun onResume() {
        super.onResume()

        btnBegin.setOnClickListener {
            val intent = Intent(this, QuestionsActivity::class.java).apply {
                putExtra(EXTRA_MARVEL_Q, "")
            }
            startActivity(intent)
        }
    }
}
