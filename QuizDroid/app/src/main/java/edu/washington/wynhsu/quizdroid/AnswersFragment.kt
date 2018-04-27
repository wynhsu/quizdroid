package edu.washington.wynhsu.quizdroid

import android.app.Fragment
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.activity_answers.*
import kotlinx.android.synthetic.main.activity_answers.view.*

class AnswersFragment : Fragment () {

    lateinit var activityCommander: AnswersListener

    interface AnswersListener {
        fun createQuestionView(t: Topic, q: Int, correct: Int, incorrect: Int)
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        try {
            activityCommander = context as AnswersListener
        } catch (e: ClassCastException) {
            throw ClassCastException(context.toString())
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view = inflater.inflate(R.layout.activity_answers, container, false)

        val topic = arguments!!.getParcelable<Topic>("topic")
        var qNumb = arguments!!.getInt("question")
        val answer = arguments!!.getCharSequence("answer")
        var correct = arguments!!.getInt("correct")
        var incorrect = arguments!!.getInt("incorrect")

        view.txtsubTitle2.text = topic.name
        val ca = "Correct Answer: " + topic.questions[qNumb].options[topic.questions[qNumb].answer]
        view.txtCA.text = ca
        val ua = "Your Answer: $answer"
        txtUA.text = ua
        val count = topic.questions.size
        val score = "You have $correct out of $count correct."
        txtScore.text = score
        qNumb++

        if (qNumb == count) {
            btnNext.text = "Finish"
            btnNext.setOnClickListener {
//                val intent = Intent(this, MainActivity::class.java)
//                startActivity(intent)
            }
        } else {
            btnNext.setOnClickListener {
                activityCommander.createQuestionView(topic, qNumb, correct, incorrect)
            }
        }
        return view
    }
}