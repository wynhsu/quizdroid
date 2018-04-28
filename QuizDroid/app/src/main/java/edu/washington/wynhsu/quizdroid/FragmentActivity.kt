package edu.washington.wynhsu.quizdroid

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

class FragmentActivity : AppCompatActivity(), TopicsFragment.TopicsListener, QuestionsFragment.QuestionsListener, AnswersFragment.AnswersListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fragment)
    }

    override fun beginQuestionsView(t: Topic) {
        val bundle = Bundle()
        bundle.putParcelable("topic", t)
        bundle.putInt("question", 0)
        bundle.putInt("correct", 0)
        bundle.putInt("incorrect", 0)
        val qFragment = QuestionsFragment()
        qFragment.arguments = bundle
        val fragTrans = fragmentManager.beginTransaction()
        fragTrans.replace(R.id.fragmentLayout, qFragment).commit()
    }

    override fun createAnswersView(t: Topic, q: Int, a: CharSequence, correct: Int, incorrect: Int) {
        val bundle = Bundle()
        bundle.putParcelable("topic", t)
        bundle.putInt("question", q)
        bundle.putCharSequence("answer", a)
        bundle.putInt("correct", correct)
        bundle.putInt("incorrect", incorrect)
        val aFragment = AnswersFragment()
        aFragment.arguments = bundle
        val fragTrans = fragmentManager.beginTransaction()
        fragTrans.replace(R.id.fragmentLayout, aFragment).commit()
    }

    override fun createQuestionsView(t: Topic, q: Int, correct: Int, incorrect: Int) {
        val bundle = Bundle()
        bundle.putParcelable("topic", t)
        bundle.putInt("question", q)
        bundle.putInt("correct", correct)
        bundle.putInt("incorrect", incorrect)
        val qFragment = QuestionsFragment()
        qFragment.arguments = bundle
        val fragTrans = fragmentManager.beginTransaction()
        fragTrans.replace(R.id.fragmentLayout, qFragment).commit()
    }

    override fun onResume() {
        super.onResume()
        val topic = intent.getParcelableExtra<Topic>("topic")
        Log.i("topic", topic.name)
        val bundle = Bundle()
        bundle.putParcelable("topic", topic)
        val tFragment = TopicsFragment()
        tFragment.arguments = bundle
        val fragTrans = fragmentManager.beginTransaction()
        fragTrans.add(R.id.fragmentLayout, tFragment).commit()
    }
}
