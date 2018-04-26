package edu.washington.wynhsu.quizdroid

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.activity_topics.*

class TopicsFragment : Fragment() {

    TopicsListener: activityCommander

    interface TopicsListener

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        btnBegin.setOnClickListener {  }

        return inflater.inflate(R.layout.activity_topics, container, false)
//        return super.onCreateView(inflater, container, savedInstanceState)

    }
}