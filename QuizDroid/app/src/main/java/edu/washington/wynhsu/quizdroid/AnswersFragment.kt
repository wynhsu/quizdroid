package edu.washington.wynhsu.quizdroid

import android.app.Fragment
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

class AnswersFragment : Fragment () {

    lateinit var activityCommander: AnswersListener


    interface AnswersListener {
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
        val topic = arguments!!.getParcelable<Topic>("topic")
        val qNumb = arguments!!.getInt("question")
        val answer = arguments!!.getCharSequence("")
        var correct = arguments!!.getInt("correct")
        var incorrect = arguments!!.getInt("incorrect")

        return inflater.inflate(R.layout.activity_answers, container, false)
    }
}