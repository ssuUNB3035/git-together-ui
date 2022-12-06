package ca.unb.mobiledev.gittogetherui.ui.home

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import ca.unb.mobiledev.gittogetherui.R
import ca.unb.mobiledev.gittogetherui.model.DataHolder
import ca.unb.mobiledev.gittogetherui.model.Project
import ca.unb.mobiledev.gittogetherui.model.User
import com.lorentzos.flingswipe.SwipeFlingAdapterView
import com.lorentzos.flingswipe.SwipeFlingAdapterView.onFlingListener
import java.util.*
import kotlin.collections.ArrayList

/*
    Homepage of the Application
    Users may access the 'Edit Profile' page, 'Manage Profile' page, and 'Create Project' page here
 */
class HomeActivity : AppCompatActivity() {
    private var al: ArrayList<Project>? = null
    private var arrayAdapter: CardAdapter? = null
    lateinit var data: DataHolder
    private var i = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        data = applicationContext as DataHolder
        data.setActivity(this)

        // Dummy User data
        var newUser: User = User()
        newUser.id = UUID.fromString("b0ff4b6a-74ef-11ed-a1eb-0242ac120002")
        newUser.bearer = "1|Phme82wLS3u8n4zrCVupBwXRWy3BHX09KhSDMeYb"
        newUser.name = "Samuel Su"
        newUser.bio = "Hello!"
        newUser.email = "ssu@unb.ca"
        newUser.location = "Fredericton, New Brunswick, Canada"

        //Setting the button events
        var profileButton = findViewById<Button>(R.id.buttonProfile)
        profileButton.setOnClickListener {
            val intent = Intent(this@HomeActivity, EditProfileActivity::class.java)
            startActivity(intent)
        }

        var manageButton = findViewById<Button>(R.id.button_manage_home)
        manageButton.setOnClickListener {
            val intent = Intent(this@HomeActivity, ManageActivity::class.java)
            startActivity(intent)
        }

        var createButton = findViewById<Button>(R.id.buttonCreateProjects)
        createButton.setOnClickListener {
            val intent = Intent(this@HomeActivity, CreateProjectActivity::class.java)
            startActivity(intent)
        }

        // Adding the projects to the swipe cards
        al = ArrayList()
        val jsonUtils = JsonUtils(this)
        jsonUtils.processJSON()

        arrayAdapter = CardAdapter(this, data.getProjectList()!!)

        val flingContainer = findViewById<View>(R.id.frame) as SwipeFlingAdapterView
        flingContainer.adapter = arrayAdapter

        flingContainer.setFlingListener(object : onFlingListener {
            override fun removeFirstObjectInAdapter() {
                // Nothing
            }

            override fun onLeftCardExit(dataObject: Any) {
                arrayAdapter!!.remove(dataObject as Project)
            }

            override fun onRightCardExit(dataObject: Any) {
                if (data.getProjectList().contains(dataObject as Project)
                    && !data.selectedProjectList.contains(dataObject as Project)) {
                    data.addSelectedProject(dataObject as Project)
                }
                arrayAdapter!!.remove(dataObject as Project)
            }

            override fun onAdapterAboutToEmpty(itemsInAdapter: Int) {
                if (itemsInAdapter < 2) {
                    var tempArray: ArrayList<Project> = ArrayList()
                    for (p: Project in data.unFilteredProjects) {
                        if (!data.selectedProjectList.contains(p)) {
                            tempArray.add(p)
                        }
                    }
                    data.projectList.addAll(tempArray)
                    arrayAdapter!!.notifyDataSetChanged()
                    Log.d("LIST", "notified")
                    i++
                }
            }

            override fun onScroll(scrollProgressPercent: Float) {}
        })

        flingContainer.setOnItemClickListener { itemPosition, dataObject ->
            Toast.makeText(
                this@HomeActivity,
                "Clicked!",
                Toast.LENGTH_SHORT
            ).show()
        }

        var filterButton = findViewById<Button>(R.id.filterProjectsHome)
        filterButton.setOnClickListener {
            var dialog = FilterProjectsFragment()

            dialog.show(supportFragmentManager, "filterProjectsFragment")
        }
    }

    fun inviteMember(name: String, hashString: String, email: String) {
        val emailAddress = arrayOf(email)
        val emailSubject = "Invitation to "
        val emailText = "You've been invited to:"
        val intent = Intent(Intent.ACTION_SENDTO).apply {
            data = Uri.parse("mailto:")
            putExtra(Intent.EXTRA_EMAIL, emailAddress)
            putExtra(Intent.EXTRA_SUBJECT, emailSubject)
            putExtra(Intent.EXTRA_TEXT, emailText)
        }
        startActivity(intent)
    }
}