package edu.washington.wynhsu.quizdroid

import android.app.Activity
import android.app.Fragment
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.activity_topics.view.*

class TopicsFragment : Fragment() {

    lateinit var activityCommander: TopicsListener

    interface TopicsListener {
        fun beginQuestionsView(t: Topic)
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        try {
            activityCommander = context as TopicsListener
        } catch (e: ClassCastException) {
            throw ClassCastException(context.toString())
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view = inflater.inflate(R.layout.activity_topics, container, false)

        val topic = arguments.getParcelable<Topic>("topic")
        Log.i("fragment topic", topic.name)

        view.txtTitle.text = topic.name
        view.txtDescr.text = topic.descr
        view.txtCount.text = "Total Questions: " + topic.questions.size.toString()

        view.btnBegin.setOnClickListener{
            activityCommander.beginQuestionsView(topic)
        }

        return view
    }
}