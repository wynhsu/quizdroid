package edu.washington.wynhsu.quizdroid

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.util.Log
import android.view.*
import android.widget.*
import com.google.gson.GsonBuilder
import edu.washington.wynhsu.quizdroid.R.id.*
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.*
import java.io.File
import java.io.IOException

class MainActivity : AppCompatActivity() {

//    private val topicList = QuizApp.getInstance().get()
    private lateinit var topicList: Array<Topic>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(my_toolbar)

        permissions()
        fetchJSON(this)
        if (!::topicList.isInitialized) {
            topicList = fetchLocal()
        }

        listView.adapter = CustomAdapter(this, topicList)
    }

    override fun onResume() {
        super.onResume()
        listView.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
            val selected = topicList[position]
            val intent = Intent(this, FragmentActivity::class.java).apply {
                putExtra("topic", selected)
            }
            startActivity(intent)
        }
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
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE), 0)
        }
    }

    private fun fetchJSON(context: Context){
        val url = "http://tednewardsandbox.site44.com/questions.json" //txtURL.toString()
        val request = Request.Builder().url(url).build()
        val client = OkHttpClient()
        client.newCall(request).enqueue(object: Callback {
            override fun onResponse(call: Call?, response: Response?){
                val body = response?.body()?.string()?.reader()
                val gson = GsonBuilder().setPrettyPrinting().create()
                val json = gson.fromJson(body, Array<Topic>::class.java).toString().toByteArray()
                if (isExternalStorageWritable()) {
                    val sdcard = Environment.getExternalStorageDirectory()
                    val file = File(sdcard, "questions.json")
                    file.writeBytes(json)
                }
//                runOnUiThread {
//                    listView.adapter = CustomAdapter(context, json)
//                }
            }
            override fun onFailure(call: Call?, e: IOException?) {
                Log.i("error", "no network connection")
            }
        })
    }

    fun isExternalStorageWritable(): Boolean {
        return Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED
    }

    private fun fetchLocal(): Array<Topic> {
        val json = File("./sdcard/questions.json")
        val gson = GsonBuilder().setPrettyPrinting().create()
        return gson.fromJson(json.reader(), Array<Topic>::class.java)
    }

    private class CustomAdapter(context: Context, topic: Array<Topic>): BaseAdapter() {

        private val mContext: Context = context
        private val mTopic: Array<Topic> = topic

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
            catName.text = mTopic[position].title

            val subName = listItem.findViewById<TextView>(R.id.text2)
            subName.text = mTopic[position].desc

            return listItem
        }
    }
}
