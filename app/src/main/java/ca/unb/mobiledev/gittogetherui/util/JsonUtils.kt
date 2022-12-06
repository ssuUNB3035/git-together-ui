package ca.unb.mobiledev.gittogetherui.ui.home
import android.content.Context
import android.os.Handler
import android.os.Looper
import android.util.Log
import ca.unb.mobiledev.gittogetherui.model.DataHolder
import ca.unb.mobiledev.gittogetherui.model.Project
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.*
import java.lang.Thread.sleep
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL

class JsonUtils(context: Context) {
    // Getter method for geoDataArray
    private lateinit var projectList: ArrayList<Project>
    lateinit var data: DataHolder

        private fun processJSON(context: Context) {
            data = context.applicationContext as DataHolder
        // Initialize the data array
        projectList = ArrayList()

        // Process the JSON response from the URL
            loadJSONFromURL()
        try {
            val gson = GsonBuilder().create()
            projectList = gson.fromJson(loadJSONFromFile(), object :
                TypeToken<ArrayList<Project>>(){}.type)
        } catch (e: Exception) {
            e.printStackTrace()
    }
        for (i in projectList) {
            if (!data.getProjectList().contains(i)) {
                data.addProject(i)
            }
        }
    }

    private fun loadJSONFromURL() {
        GlobalScope.launch(Dispatchers.IO) {
            val url = URL(REQUEST_URL)
            val myURLConnection = url.openConnection() as HttpURLConnection
            try {

                myURLConnection.requestMethod = "GET"
                myURLConnection.setRequestProperty("Authorization", "Bearer 1|Phme82wLS3u8n4zrCVupBwXRWy3BHX09KhSDMeYb")
                myURLConnection.setRequestProperty("Accept", "application/json")
                myURLConnection.doInput = true
                myURLConnection.doOutput = false

                val responseCode = myURLConnection.responseCode
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    val streamIn = BufferedInputStream(myURLConnection.inputStream)
                    val returntest = convertStreamToString(streamIn)
                    var tempList: ArrayList<Project> = ArrayList()
                    try {
                        val gson = GsonBuilder().create()
                        tempList = gson.fromJson(returntest, object :
                            TypeToken<ArrayList<Project>>(){}.type)
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                    for (i in tempList) {
                        if (!data.getProjectList().contains(i)) {
                            data.addProject(i)
                        }
                    }
                    data.getActivity().updateArrayAdapter()

                }
            } catch (exception: MalformedURLException) {
                Log.e(TAG, "MalformedURLException")
            } catch (exception: IOException) {
                Log.e(TAG, "IOException")
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                myURLConnection?.disconnect()
            }
        }
    }

    // Dummy Method
    private fun loadJSONFromFile(): String? {
        val jsonString: String
        try {
            jsonString = """[{"name":"New Project #1","description":"1 Teeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeext","tags":"C# C++","id":"35dcfd0a-9da6-4d92-80b9-14296d6914b8", "link":"https://developer.android.com/kotlin"},{"name":"New Project #2","description":"2 Teeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeext","tags":"Java C++","id":"e1bbc215-6ac0-48bf-a701-2dfc3dbb788c","link":"https://developer.android.com/kotlin"},{"name":"New Project #3","description":"3 Teeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeext","tags":"Kotlin","id":"6b0b4a70-2073-4cf0-a485-77678d5086c7","link":"https://developer.android.com/kotlin"}]"""

        } catch (ioException: IOException) {
            ioException.printStackTrace()
            return null
        }

        return jsonString
    }

    private fun convertStreamToString(`in`: InputStream): String {
        val data = StringBuilder()
        try {
            BufferedReader(InputStreamReader(`in`)).use { reader ->
                var line: String?
                while (reader.readLine().also { line = it } != null) {
                    data.append(line)
                }
            }
        } catch (e: IOException) {
            Log.e(TAG, "IOException")
        }
        return data.toString()
    }

    // Initializer to read our data source (JSON file) into an array of course objects
    init {
        processJSON(context)
    }

    companion object {
        private const val TAG = "JsonUtils"
        private const val REQUEST_URL =
            "http://192.168.2.79/api/projects"
        private const val JSON_KEY_TITLE = "title"
        private const val JSON_KEY_COORDINATES = "coordinates"
    }
}