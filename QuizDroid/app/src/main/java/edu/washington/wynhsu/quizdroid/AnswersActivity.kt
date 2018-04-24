package edu.washington.wynhsu.quizdroid

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_answers.*

class AnswersActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_answers)
    }

    override fun onResume() {
        super.onResume()
        val topic = intent.getParcelableExtra<Topic>("topic")
        var qNumb = intent.getIntExtra("question", 0)
        val correct = intent.getIntExtra("correct", 0)
        val incorrect = intent.getIntExtra("incorrect", 0)

        txtsubTitle2.text = topic.name
        txtCA.text = "Correct Answer: " + topic.questions[qNumb].options[topic.questions[qNumb].answer]
        txtUA.text = "Your Answer: " + intent.getStringExtra("input")
        val count = topic.questions.size
        txtScore.text = "You have " + correct + " out of " + count + " correct."
        qNumb++

        if (qNumb == count) {
            btnNext.text = "Finish"
            btnNext.setOnClickListener {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
        } else {
            btnNext.setOnClickListener {
                val intent = Intent(this, QuestionsActivity::class.java).apply {
                    putExtra("topic", topic)
                    putExtra("question", qNumb)
                    putExtra("correct", correct)
                    putExtra("incorrect", incorrect)
                }
                startActivity(intent)
            }
        }
    }
}
