package edu.washington.wynhsu.quizdroid

import android.app.Fragment
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.activity_answers.view.*

class AnswersFragment : Fragment () {

    lateinit var activityCommander: AnswersListener

    interface AnswersListener {
        fun createQuestionsView(t: Topic, q: Int, correct: Int, incorrect: Int)
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

        val topic = arguments.getParcelable<Topic>("topic")
        var qNumb = arguments.getInt("question")
        val answer = arguments.getCharSequence("answer")
        var correct = arguments.getInt("correct")
        var incorrect = arguments.getInt("incorrect")

        view.txtsubTitle2.text = topic.title
        view.txtCA.text = "Correct Answer: " + topic.questions[qNumb].answers[topic.questions[qNumb].answer-1]
        view.txtUA.text = "Your Answer: $answer"
        val count = topic.questions.size
        view.txtScore.text = "You have $correct out of $count correct."
        qNumb++

        if (qNumb == count) {
            view.btnNext.text = "Finish"
            view.btnNext.setOnClickListener {
                val intent = Intent(this.activity, MainActivity::class.java)
                startActivity(intent)
            }
        } else {
            view.btnNext.setOnClickListener {
                activityCommander.createQuestionsView(topic, qNumb, correct, incorrect)
            }
        }
        return view
    }
}