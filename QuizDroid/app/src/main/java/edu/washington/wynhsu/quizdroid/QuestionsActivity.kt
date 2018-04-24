package edu.washington.wynhsu.quizdroid

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_questions.*

class QuestionsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_questions)
        btnSubmit.isEnabled = false
    }

    override fun onResume() {
        super.onResume()
        val topic = intent.getParcelableExtra<Topic>("topic")
        val qNumb = intent.getIntExtra("question", 0)
        var correct = intent.getIntExtra("correct", 0)
        var incorrect = intent.getIntExtra("incorrect", 0)

        txtCorrect.text = "Correct: " + correct.toString()
        txtIncorrect.text = "Incorrect: " + incorrect.toString()

        txtsubTitle.text = topic.name
        val q1 = topic.questions[qNumb].question
        txtQuestion.text = q1
        btnSubmit.isEnabled = rdGrp.checkedRadioButtonId != -1
        val optArray = topic.questions[qNumb].options

        val opt1 = optArray[0]
        rdBtn1.text = opt1
        val opt2 = optArray[1]
        rdBtn2.text = opt2
        val opt3 = optArray[2]
        rdBtn3.text = opt3
        val opt4 = optArray[3]
        rdBtn4.text = opt4

        val btnArray = arrayOf(rdBtn1, rdBtn2, rdBtn3, rdBtn4)
        for (btn in btnArray) {
            btn.setOnClickListener {
                btnSubmit.isEnabled = true
            }
        }

        var answer: CharSequence
        btnSubmit.setOnClickListener {
            answer = when {
                rdBtn1.isChecked -> rdBtn1.text
                rdBtn2.isChecked -> rdBtn2.text
                rdBtn3.isChecked -> rdBtn3.text
                else -> rdBtn4.text
            }

            if (answer.toString() == optArray[topic.questions[qNumb].answer]) {
                correct++
                txtCorrect.text = "Correct: " + correct.toString()
            } else {
                incorrect++
                txtIncorrect.text = "Incorrect: " + incorrect.toString()
            }

            val intent = Intent(this, AnswersActivity::class.java).apply {
                putExtra("topic", topic)
                putExtra("question", qNumb)
                putExtra("input", answer)
                putExtra("correct", correct)
                putExtra("incorrect", incorrect)
            }
            startActivity(intent)
        }
    }
}
