package ca.unb.mobiledev.gittogetherui.ui.home
import android.content.Context
import android.util.Log
import ca.unb.mobiledev.gittogetherui.model.Project
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import javax.json.stream.JsonParser
import javax.json.Json
import java.io.*
import java.lang.Exception
import java.lang.StringBuilder
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL
import java.util.ArrayList

class JsonUtils(context: Context) {
    // Getter method for geoDataArray
    private lateinit var projectList: ArrayList<Project>

    private fun processJSON(context: Context) {
        // Initialize the data array
        projectList = ArrayList()

        // Process the JSON response from the URL
        //val jsonString = loadJSONFromURL()

        val jsonString = loadJSONFromFile()
        try {
            val gson = GsonBuilder().create()
            projectList = gson.fromJson(jsonString, object :
                TypeToken<ArrayList<Project>>(){}.type)
        } catch (e: Exception) {
            e.printStackTrace()
        }
//        try {
//            val parser = Json.createParser(StringReader(jsonString))
//            var titleTrigger = false
//            var coordinateTrigger = false
//            var count = 0
//            var coordinateCount = 0
//
//            while (parser.hasNext()) {
//                when (parser.next()) {
//                    JsonParser.Event.KEY_NAME -> if (parser.string == JSON_KEY_TITLE) {
//                        titleTrigger = true
//                    } else if (parser.string == JSON_KEY_COORDINATES) {
//                        coordinateTrigger = true
//                    }
//                    JsonParser.Event.VALUE_STRING -> if (titleTrigger && parser.string.startsWith("M")) {
//                        val project = Project()
//                        project.title = parser.string
//                        projectList.add(project)
//                        titleTrigger = false
//                    }
//                    JsonParser.Event.VALUE_NUMBER -> {
//                        if (coordinateTrigger && coordinateCount == 0) {
//                            val geoData = projectList[count]
//                            geoData.longitude = parser.string
//                            coordinateCount++
//                        } else if (!coordinateTrigger && coordinateCount == 1) {
//                            val geoData = projectList[count]
//                            geoData.latitude = parser.string
//                            coordinateCount = 0
//                            count++
//                        }
//                        coordinateTrigger = false
//                    }
//                    else -> {}
//                }
//            }
//        } catch (e: Exception) {
//            e.printStackTrace()
//        }
    }

    fun getProjectList(): ArrayList<Project> {
        return projectList
    }

    private fun loadJSONFromURL(): String? {
        val url = URL(REQUEST_URL)
        var connection: HttpURLConnection? = null
        try {
           // TODO
            //  Establish an HttpURLConnection to REQUEST_URL (defined as a constant)
            //  Hint: 
            //    See https://github.com/hpowell20/cs2063-fall-2022-examples/tree/master/Lecture6/NetworkingURL
            //    for an example of how to do this
            //    Also see documentation here: http://developer.android.com/training/basics/network-ops/connecting.html
            // DONE
            connection = url.openConnection() as HttpURLConnection?
            val streamIn = BufferedInputStream(connection?.getInputStream())
            return convertStreamToString(streamIn)
        } catch (exception: MalformedURLException) {
            Log.e(TAG, "MalformedURLException")
            return null
        } catch (exception: IOException) {
            Log.e(TAG, "IOException")
            return null
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        } finally {
            connection?.disconnect()
        }
    }

    private fun loadJSONFromFile(): String? {
        val jsonString: String
        try {
            jsonString = """[{"title":"New Project #1","description":"A new project!","tags":"c# c++","id":"35dcfd0a-9da6-4d92-80b9-14296d6914b8"},{"title":"New Project #2","description":"A second new project!","tags":"java c++","id":"e1bbc215-6ac0-48bf-a701-2dfc3dbb788c"},{"title":"New Project #3","description":"A third new project!","tags":"kotlin","id":"6b0b4a70-2073-4cf0-a485-77678d5086c7"}]"""

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
            "https://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/all_day.geojson"
        private const val JSON_KEY_TITLE = "title"
        private const val JSON_KEY_COORDINATES = "coordinates"
    }
}