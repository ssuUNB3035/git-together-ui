package ca.unb.mobiledev.gittogetherui.ui.home
import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.widget.*
import ca.unb.mobiledev.gittogetherui.R
import ca.unb.mobiledev.gittogetherui.model.DataHolder
import ca.unb.mobiledev.gittogetherui.model.Project
import ca.unb.mobiledev.gittogetherui.model.User
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.json.JSONObject
import java.io.IOException
import java.io.OutputStreamWriter
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL
import java.util.*


class EditProfileActivity : Activity() {

    private lateinit var nameEntry: TextInputEditText
    private lateinit var bioEntry: TextInputEditText
    private lateinit var locationEntry: TextInputEditText
    private lateinit var emailEntry: TextInputEditText

    lateinit var data: DataHolder

    var context = this

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        data = applicationContext as DataHolder

        var user: User = data.getUser()

        var nameField = findViewById<TextInputEditText>(R.id.profileName)
        nameField.setText(user.name)

        var bioField = findViewById<TextInputEditText>(R.id.profileBio)
        bioField.setText(user.bio)

        var locationField = findViewById<TextInputEditText>(R.id.profileLocation)
        locationField.setText(user.location)

        var emailField = findViewById<TextInputEditText>(R.id.emailField)
        emailField.setText(user.email)

        var back = findViewById<Button>(R.id.profileBackButton)
        back.setOnClickListener {
            finish()
        }
        var cancel = findViewById<Button>(R.id.cancelProfileChanges)
        cancel.setOnClickListener {
            finish()
        }
        var create = findViewById<Button>(R.id.profileSave)
        create.setOnClickListener {
            saveUser()
        }

        nameEntry = findViewById(R.id.profileName)
        bioEntry = findViewById(R.id.profileBio)
        locationEntry = findViewById(R.id.profileLocation)
        emailEntry = findViewById(R.id.emailField)

    }
    fun saveUser() {
        val user: User = User()
        user.id = data.getUser().id
        user.name = nameEntry.text.toString()
        user.bio = bioEntry.text.toString()
        user.location = locationEntry.text.toString()
        user.email = emailEntry.text.toString()

        putCall(user)

        while(!httpCompleted);

        if(result) {
            data.setUser(user)
            Toast.makeText(context, "Saved changes.", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, "Cannot save changes. Please try again.", Toast.LENGTH_SHORT).show()
        }

        //Toast changes saved
        //Call API to update user
    }

    private fun putCall(user: User) {
        GlobalScope.launch(Dispatchers.IO) {
            val url = URL(EditProfileActivity.REQUEST_URL)
            val myURLConnection = url.openConnection() as HttpURLConnection
            try {

                myURLConnection.requestMethod = "PUT"
                myURLConnection.setRequestProperty("Authorization", "Bearer 1|Phme82wLS3u8n4zrCVupBwXRWy3BHX09KhSDMeYb") // TODO: Change the hard coded value
                myURLConnection.setRequestProperty("Content-Type", "application/json")
                myURLConnection.setRequestProperty("Accept", "application/json")
                myURLConnection.doInput = true
                myURLConnection.doOutput = true

                val jsonObject = JSONObject()
                jsonObject.put("name", user.name)
                jsonObject.put("bio", user.bio)
                jsonObject.put("location", user.location)
                jsonObject.put("email", user.email)

                val outputStreamWriter = OutputStreamWriter(myURLConnection.outputStream)
                outputStreamWriter.write(jsonObject.toString())
                outputStreamWriter.flush()


                val responseCode = myURLConnection.responseCode

                result = responseCode == HttpURLConnection.HTTP_OK
            } catch (exception: MalformedURLException) {
                Log.e("Create Error", "MalformedURLException")
            } catch (exception: IOException) {
                Log.e("Create Error", "IOException")
                exception.printStackTrace()
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                myURLConnection.disconnect()
                httpCompleted = true
            }
        }
    }

    companion object {
        private var result: Boolean = false
        private var httpCompleted: Boolean = false
        private const val TAG = "UpdateUser"
        private const val REQUEST_URL =
            "http://conan.cloud/api/user/"

    }


}