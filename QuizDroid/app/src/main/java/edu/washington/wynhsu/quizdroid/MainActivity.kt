package edu.washington.wynhsu.quizdroid

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import edu.washington.wynhsu.quizdroid.R.id.*
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    val topicList = QuizApp.getInstance().get()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        listView.adapter = CustomAdapter(this, topicList)
    }

    override fun onResume() {
        super.onResume()
        listView.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
            val selected = topicList.get(position)
            getView(view, selected)
        }
    }

    fun getView(view: View, t: Topic) {

        val intent = Intent(this, FragmentActivity::class.java).apply {
            putExtra("topic", t)
        }
        startActivity(intent)
    }

    private class CustomAdapter(context: Context, topic: MutableList<Topic>): BaseAdapter() {

        private val mContext: Context
        private val mTopic: MutableList<Topic>

        init {
            mContext = context
            mTopic = topic
        }

         override fun getCount(): Int {
            return mTopic.size
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun getItem(position: Int): Any {
            return "TEST"
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            val layoutInflater = LayoutInflater.from(mContext)
            val listItem = layoutInflater.inflate(R.layout.list_item, parent, false)

            val icon = listItem.findViewById<ImageView>(R.id.imgIcon)
            // implement when there is an actual icon
            //icon.setImageResource()

            val catName = listItem.findViewById<TextView>(R.id.text1)
            catName.text = mTopic.get(position).name

            val subName = listItem.findViewById<TextView>(R.id.text2)
            subName.text = mTopic.get(position).sub

            return listItem
        }
    }
}
