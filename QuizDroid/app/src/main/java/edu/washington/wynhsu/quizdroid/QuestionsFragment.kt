package edu.washington.wynhsu.quizdroid

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.activity_questions.view.*

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
        view.btnSubmit.isEnabled = false

        val topic = arguments.getParcelable<Topic>("topic")
        val qNumb = arguments.getInt("question")
        var correct = arguments.getInt("correct")
        var incorrect = arguments.getInt("incorrect")

        view.txtCorrect.text = "Correct: " + correct.toString()
        view.txtIncorrect.text = "Incorrect: " + incorrect.toString()
        view.txtsubTitle.text = topic.title
        view.txtQuestion.text = topic.questions[qNumb].text
        view.btnSubmit.isEnabled = view.rdGrp.checkedRadioButtonId != -1

        val optArray = topic.questions[qNumb].answers
        view.rdBtn1.text = optArray[0]
        view.rdBtn2.text = optArray[1]
        view.rdBtn3.text = optArray[2]
        view.rdBtn4.text = optArray[3]

        val btnArray = arrayOf(view.rdBtn1, view.rdBtn2, view.rdBtn3, view.rdBtn4)
        for (btn in btnArray) {
            btn.setOnClickListener {
                view.btnSubmit.isEnabled = true
            }
        }

        var answer: CharSequence
        view.btnSubmit.setOnClickListener {
            answer = when {
                view.rdBtn1.isChecked -> view.rdBtn1.text
                view.rdBtn2.isChecked -> view.rdBtn2.text
                view.rdBtn3.isChecked -> view.rdBtn3.text
                else -> view.rdBtn4.text
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