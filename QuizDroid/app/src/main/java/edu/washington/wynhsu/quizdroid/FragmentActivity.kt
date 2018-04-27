package edu.washington.wynhsu.quizdroid

import android.app.FragmentTransaction
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.FragmentActivity;
import android.util.Log
import kotlinx.android.synthetic.main.activity_topics.*

class FragmentActivity : AppCompatActivity(), TopicsFragment.TopicsListener, QuestionsFragment.QuestionsListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fragment)
    }

    override fun createQuestionsView(t: Topic) {
//        btnBegin.setOnClickListener {
//            //send intent from topic activity to question fragment
            val bundle = Bundle()
            bundle.putParcelable("topic", t)
            bundle.putInt("question", 0)
            bundle.putInt("correct", 0)
            bundle.putInt("incorrect", 0)
            val qFragment = QuestionsFragment()
            qFragment.arguments = bundle
            val fragTrans = fragmentManager.beginTransaction()
            fragTrans.replace(R.id.fragmentLayout, qFragment).commit()
//        }
    }

    override fun createAnswersView(t: Topic, q: Int, a: CharSequence, correct: Int, incorrect: Int) {

    }

    override fun onResume() {
        super.onResume()
        val topic = intent.getParcelableExtra<Topic>("topic")
        val bundle = Bundle()
        bundle.putParcelable("topic", topic)
        val tFragment = TopicsFragment()
        tFragment.arguments = bundle
        val fragTrans = fragmentManager.beginTransaction()
        fragTrans.add(R.id.fragmentLayout, tFragment).commit()
    }
}
