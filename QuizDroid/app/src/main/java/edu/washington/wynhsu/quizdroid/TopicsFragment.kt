package edu.washington.wynhsu.quizdroid

import android.app.Activity
import android.app.Fragment
import android.support.v4.app.FragmentActivity;
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.activity_topics.*

class TopicsFragment : Fragment() {

    lateinit var activityCommander: TopicsListener

    interface TopicsListener {
        fun createQuestionsView(t: Topic)
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

        txtTitle.text = topic.name
        txtDescr.text = topic.descr
        val count = "Total Questions: " + topic.questions.size.toString()
        txtCount.text = count

        btnBegin.setOnClickListener{
            activityCommander.createQuestionsView(topic)
        }

        return view
    }
}