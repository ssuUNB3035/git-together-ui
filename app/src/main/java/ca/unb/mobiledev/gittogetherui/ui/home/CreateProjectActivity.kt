package ca.unb.mobiledev.gittogetherui.ui.home
import android.Manifest
import android.annotation.SuppressLint
import android.view.ViewGroup.LayoutParams;
import android.app.Activity
import android.content.pm.PackageManager
import android.graphics.Color
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import ca.unb.mobiledev.gittogetherui.R
import ca.unb.mobiledev.gittogetherui.model.DataHolder
import ca.unb.mobiledev.gittogetherui.model.Project
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.material.textfield.TextInputEditText
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.io.DataOutputStream
import java.io.IOException
import java.io.OutputStreamWriter
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL
import java.util.*


class CreateProjectActivity : Activity() {
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    private lateinit var titleEntry: TextInputEditText
    private lateinit var descEntry: TextInputEditText
    private lateinit var locationEntry: TextInputEditText
    private lateinit var tagDropdown: Spinner
    private lateinit var tagLayout: LinearLayout
    private var selectedTags: ArrayList<String> = ArrayList()

    lateinit var data: DataHolder

    var context = this

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create)

        data = applicationContext as DataHolder

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        var back = findViewById<Button>(R.id.create_back_button)
        back.setOnClickListener {
            finish()
        }
        var cancel = findViewById<Button>(R.id.cancel_project_button)
        cancel.setOnClickListener {
            finish()
        }
        var create = findViewById<Button>(R.id.create_project_button)
        create.setOnClickListener {
            createProject()
        }

        titleEntry = findViewById(R.id.title_field)
        descEntry = findViewById(R.id.desc_field)
        locationEntry = findViewById(R.id.location_field)
        tagDropdown = findViewById(R.id.tag_dropdown)
        tagLayout = findViewById(R.id.tag_layout)

        getCurrentLocation()

        val adapter: ArrayAdapter<String> = ArrayAdapter<String>(
            applicationContext,
            android.R.layout.simple_spinner_dropdown_item,
            data.getAvailableTags()
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        tagDropdown.setAdapter(adapter)

        tagDropdown.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            @SuppressLint("ResourceType")
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                if (!selectedTags.contains(data.availableTags.get(position))) {
                    var tagText = data.availableTags.get(position)
                    var btn = Button(context)
                    btn.text = tagText
                    btn.setBackgroundColor(Color.BLUE)
                    btn.setTextColor(Color.WHITE)
                    btn.width = LayoutParams.WRAP_CONTENT
                    btn.height = LayoutParams.WRAP_CONTENT

                    selectedTags.add(tagText)
                    tagLayout.addView(btn)
                    var space: Space = Space(context)
                    space.minimumWidth = 5
                    tagLayout.addView(space)
                    btn.setOnClickListener {
                        tagLayout.removeView(btn)
                        tagLayout.removeView(space)
                        selectedTags.remove(tagText)
                    }
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                // Nothing
            }

        }
    }

    fun createProject() {
        if (titleEntry.text.toString() == ""
            || descEntry.text.toString() == ""
            || locationEntry.text.toString() == ""
            || selectedTags.size == 0) {
            Toast.makeText(context, "Please fill out all fields", Toast.LENGTH_SHORT)
        }
        else {
            var newProject = Project()
            newProject.name = titleEntry.text.toString()
            newProject.description = descEntry.text.toString()
            newProject.location = locationEntry.text.toString()
            newProject.tags = selectedTags.toString() // plz fix
            newProject.link = "https://github.com/ssuUNB3035/git-together-ui/tree/samtest"
            data.addSelectedProject(newProject)

            postCall(newProject)
            finish()
        }
    }

    private fun postCall(project: Project) {
        GlobalScope.launch(Dispatchers.IO) {
            val url = URL(REQUEST_URL)
            val myURLConnection = url.openConnection() as HttpURLConnection
            try {

                myURLConnection.requestMethod = "POST"
                myURLConnection.setRequestProperty("Authorization", "Bearer " + data.user.bearer)
                myURLConnection.setRequestProperty("Content-Type", "application/json")
                myURLConnection.setRequestProperty("Accept", "application/json")
                myURLConnection.doInput = true
                myURLConnection.doOutput = false

                val jsonObject = JSONObject()
                jsonObject.put("name", project.name)
                jsonObject.put("description", project.description)
                jsonObject.put("location", project.location)
                jsonObject.put("tags", project.tags)
                jsonObject.put("link", project.link)

                val outputStreamWriter = OutputStreamWriter(myURLConnection.outputStream)
                outputStreamWriter.write(jsonObject.toString())
                outputStreamWriter.flush()

//                val jsonObject: String = Gson().toJson(project, Project::class.java)
//                DataOutputStream(myURLConnection.outputStream).use { it.writeBytes(jsonObject) }

                val responseCode = myURLConnection.responseCode
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    if (!data.getProjectList().contains(project)) {
                        data.addSelectedProject(project)
                    }
                }
            } catch (exception: MalformedURLException) {
                Log.e("Create Error", "MalformedURLException")
            } catch (exception: IOException) {
                Log.e("Create Error", "IOException")
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                myURLConnection?.disconnect()
            }
        }
    }

    @SuppressLint("SetTextI18n")
    fun getCurrentLocation() {
        if (androidx.core.app.ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && androidx.core.app.ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this@CreateProjectActivity,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {
                ActivityCompat.requestPermissions(this@CreateProjectActivity,
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 1)
            } else {
                ActivityCompat.requestPermissions(this@CreateProjectActivity,
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 1)
            }
        }
        fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
            if (location == null)
                    Toast.makeText(context, "Cannot get location.", Toast.LENGTH_SHORT).show()
                else {
                    GlobalScope.launch(Dispatchers.IO) {
                        val lat = location.latitude
                        val lon = location.longitude

                        val geocoder = Geocoder(context, Locale.getDefault())
                        val addresses: List<Address> = geocoder.getFromLocation(lat, lon, 1)
                        val cityName: String = addresses[0].locality
                        val stateName: String = addresses[0].adminArea
                        val countryName: String = addresses[0].countryName

                        withContext(Dispatchers.Main) {
                            locationEntry.setText("$cityName, $stateName, $countryName")
                        }
                    }
                }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>,
                                            grantResults: IntArray) {
        when (requestCode) {
            1 -> {
                if (grantResults.isNotEmpty() && grantResults[0] ==
                    PackageManager.PERMISSION_GRANTED) {
                    if ((ContextCompat.checkSelfPermission(this@CreateProjectActivity,
                            Manifest.permission.ACCESS_FINE_LOCATION) ===
                                PackageManager.PERMISSION_GRANTED)) {
                        Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show()
                }
                return
            }
        }
    }

    companion object {
        private const val TAG = "CreateProject"
        private const val REQUEST_URL =
            "http://conan.cloud/api/projects"
    }
}
