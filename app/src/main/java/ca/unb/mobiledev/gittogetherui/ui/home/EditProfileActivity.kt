package ca.unb.mobiledev.gittogetherui.ui.home
import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import android.widget.*
import ca.unb.mobiledev.gittogetherui.R
import ca.unb.mobiledev.gittogetherui.model.DataHolder
import ca.unb.mobiledev.gittogetherui.model.User
import com.google.android.material.textfield.TextInputEditText
import java.util.*


class EditProfileActivity : Activity() {

    private lateinit var nameEntry: TextInputEditText
    private lateinit var bioEntry: TextInputEditText
    private lateinit var locationEntry: TextInputEditText

    lateinit var data: DataHolder

    var context = this

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        data = applicationContext as DataHolder


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

    }
    fun saveUser() {
        var user: User = User()
        user.id = data.getUser().id
        user.name = nameEntry.text.toString()
        user.bio = bioEntry.text.toString()
        user.location = locationEntry.text.toString()

        data.setUser(user)
    }

}