package edu.washington.wynhsu.quizdroid

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.activity_questions.*

class QuestionsFragment : Fragment() {

    lateinit var activityCommander: QuestionsListener

    interface QuestionsListener {
        fun createAnswersView(t: Topic, q: Int, a: CharSequence, correct: Int, incorrect: Int)
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        try {
            activityCommander = context as QuestionsListener
        } catch (e: ClassCastException) {
            throw ClassCastException(context.toString())
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.activity_questions, container, false)
        btnSubmit.isEnabled = false

        val topic = arguments!!.getParcelable<Topic>("topic")
        val qNumb = arguments!!.getInt("question")
        var correct = arguments!!.getInt("correct")
        var incorrect = arguments!!.getInt("incorrect")

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
            } else {
                incorrect++
            }

            activityCommander.createAnswersView(topic, qNumb, answer, correct, incorrect)
        }
        return view
    }

}