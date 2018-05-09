package edu.washington.wynhsu.quizdroid

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.util.Log
import android.view.*
import android.widget.*
import edu.washington.wynhsu.quizdroid.R.id.*
import kotlinx.android.synthetic.main.activity_main.*
import java.io.File

class MainActivity : AppCompatActivity() {

    private val topicList = QuizApp.getInstance().get()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(my_toolbar)
        listView.adapter = CustomAdapter(this, topicList)
        permissions()
    }

    override fun onResume() {
        super.onResume()
        listView.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
            val selected = topicList[position]
            getView(view, selected)
        }
    }

    private fun getView(view: View, t: Topic) {

        val intent = Intent(this, FragmentActivity::class.java).apply {
            putExtra("topic", t)
        }
        startActivity(intent)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.toolbar, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        action_settings -> {
            val intent = Intent(this, Prefs::class.java)
            startActivity(intent)
            true
        }
        else -> {
            super.onOptionsItemSelected(item)
        }
    }

    private fun permissions() {
        val permission = ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE)
        if(permission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE), 1)
        }
    }

    private class CustomAdapter(context: Context, topic: MutableList<Topic>): BaseAdapter() {

        private val mContext: Context = context
        private val mTopic: MutableList<Topic> = topic

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
            catName.text = mTopic[position].name

            val subName = listItem.findViewById<TextView>(R.id.text2)
            subName.text = mTopic[position].sub

            return listItem
        }
    }
}
