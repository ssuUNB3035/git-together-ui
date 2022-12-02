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
    private lateinit var prevString: String
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

    fun getProjectList(): ArrayList<Project> {
        return projectList
    }

    private fun loadJSONFromURL() {
        GlobalScope.launch(Dispatchers.IO) {
            val url = URL(REQUEST_URL)
            val myURLConnection = url.openConnection() as HttpURLConnection
            try {

                myURLConnection.requestMethod = "GET"
                myURLConnection.setRequestProperty("Authorization", "Bearer 1|aOvSGKamniIrHsnGvEDEVCxpiLi9Yvmasw5Ead3B")
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
                        tempList = gson.fromJson(loadJSONFromFile(), object :
                            TypeToken<ArrayList<Project>>(){}.type)
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                    for (i in tempList) {
                        if (!data.getProjectList().contains(i)) {
                            data.addProject(i)
                        }
                    }

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
            jsonString = """[{"name":"New Project #1","description":"A new project!","tags":"c# c++","id":"35dcfd0a-9da6-4d92-80b9-14296d6914b8"},{"name":"New Project #2","description":"A second new project!","tags":"java c++","id":"e1bbc215-6ac0-48bf-a701-2dfc3dbb788c"},{"name":"New Project #3","description":"A third new project!","tags":"kotlin","id":"6b0b4a70-2073-4cf0-a485-77678d5086c7"}]"""

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
            "https://gentle-ravine-38100.herokuapp.com/api/projects"
        private const val JSON_KEY_TITLE = "title"
        private const val JSON_KEY_COORDINATES = "coordinates"
    }
}