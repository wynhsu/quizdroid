package edu.washington.wynhsu.quizdroid

import android.app.AlarmManager
import android.app.AlertDialog
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.os.SystemClock
import android.provider.Settings
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.util.Log
import android.view.*
import android.widget.*
import com.google.gson.GsonBuilder
import edu.washington.wynhsu.quizdroid.R.id.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.prefs.*
import okhttp3.*
import java.io.File
import java.io.IOException
import java.io.PrintWriter

class MainActivity : AppCompatActivity() {

    //    private val topicList = QuizApp.getInstance().get()
    private lateinit var topicList: Array<Topic>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(my_toolbar)

        if (isAirplaneModeOn(this)) {
            val builder = AlertDialog.Builder(this)
            builder.setMessage("Airplane mode is currently on. Would you like to turn it off?")
                    .setPositiveButton("Yes", { dialog, id ->
                        startActivityForResult(Intent(android.provider.Settings.ACTION_SETTINGS), 0);
                    })
                    .setNegativeButton("No", { dialog, id ->
                    })
            builder.create().show()
        } else if (!isNetworkAvailable()) {
            Toast.makeText(this, "No network services detected. Please try again.", Toast.LENGTH_SHORT).show()
        }

        getPermissions()
        topicList = fetchLocal()
        listView.adapter = CustomAdapter(this, topicList)
//        if (!::topicList.isInitialized) {
//            topicList = fetchLocal()
//            listView.adapter = CustomAdapter(this, topicList)
//        }
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

    private fun getPermissions() {
        val read = ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE)
        if (read != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE), 1)
        }
        val write = ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
        if (write != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE), 1)
        }
    }

    private fun isNetworkAvailable(): Boolean {
        val manager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = manager.activeNetworkInfo
        var isAvailable = false
        if (networkInfo != null && networkInfo.isConnected) {
            isAvailable = true
        }
        return isAvailable
    }

    private fun isAirplaneModeOn(context: Context): Boolean {
        return Settings.Global.getInt(context.contentResolver,
                Settings.Global.AIRPLANE_MODE_ON) != 0

    }

    private fun fetchLocal(): Array<Topic> {
        val dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS)
        val json = File(dir, "questions.json")
        try {
            val gson = GsonBuilder().setPrettyPrinting().create()
            return gson.fromJson(json.reader(), Array<Topic>::class.java)
        }  catch (e: Exception) {
            Log.i("error", "no path exists")
        }
        return arrayOf<Topic>()
    }
}

fun fetchJSON() {
    val url = "http://tednewardsandbox.site44.com/questions.json" // txtURL.text.toString()
    val request = Request.Builder().url(url).build()
    val client = OkHttpClient()
    client.newCall(request).enqueue(object : Callback {
        override fun onResponse(call: Call?, response: Response?) {
            val body = response?.body()?.string()?.reader()
            val gson = GsonBuilder().setPrettyPrinting().create()
            val json = gson.fromJson(body, Array<Topic>::class.java)
            if (isExternalStorageWritable()) {
                val dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS)
                var success = true
                if (!dir.exists()) {
                    success = dir.mkdir()
                }
                if (success) {
                    val file = File(dir, "questions.json")
                    if (!file.exists()) {
                        success = file.mkdir()
                    }
                    if (success) {
                        val path = File(file, "questions.json")
                        try {
                            PrintWriter(path).use { out -> out.println(json) }
                        } catch (e: Exception) {
                            Log.e("error", "didn't print")
                        }
                    }
                }
//                    Log.i("path", dir.toString())
//                    val file = File(dir, "questions.json")
//                    if (!file.exists()) { file.mkdir() }
//                    try {
//                        PrintWriter(file).use { out -> out.println(json) }
//                    } catch (e: Exception) {
//                        Log.e("error", "didn't print")
//                    }
            }
        }

        override fun onFailure(call: Call?, e: IOException?) {
            Log.i("error", "no network connection")
        }
    })
}

fun isExternalStorageWritable(): Boolean {
    return Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED
}

class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        val msg = intent!!.getStringExtra("url")
        val json = intent.getIntExtra("fetch", 1)
        Log.i("alarm call", msg)
        if (json == 0) {
            fetchJSON()
            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
        }
    }
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
