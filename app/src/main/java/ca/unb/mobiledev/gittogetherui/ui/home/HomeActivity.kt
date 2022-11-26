package ca.unb.mobiledev.gittogetherui.ui.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import ca.unb.mobiledev.gittogetherui.R
import ca.unb.mobiledev.gittogetherui.model.DataHolder
import ca.unb.mobiledev.gittogetherui.model.Project
import com.lorentzos.flingswipe.SwipeFlingAdapterView
import com.lorentzos.flingswipe.SwipeFlingAdapterView.onFlingListener


class HomeActivity : AppCompatActivity() {
    private var al: ArrayList<Project>? = null
    private var arrayAdapter: CardAdapter? = null
    private var i = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        val data = applicationContext as DataHolder
        data.setActivity(this)

        //Setting the button events
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
        al = jsonUtils.getProjectList()

        arrayAdapter = CardAdapter(this, al!!)

        val flingContainer = findViewById<View>(R.id.frame) as SwipeFlingAdapterView
        flingContainer.adapter = arrayAdapter

        flingContainer.setFlingListener(object : onFlingListener {
            override fun removeFirstObjectInAdapter() {
                // this is the simplest way to delete an object from the Adapter (/AdapterView)
                Log.d("LIST", "removed object!")
                al!!.removeAt(0)
                arrayAdapter!!.notifyDataSetChanged()
            }

            override fun onLeftCardExit(dataObject: Any) {
                //Do something on the left!
                //You also have access to the original object.
                //If you want to use it just cast it (String) dataObject
                //Toast.makeText(this@HomeActivity, "Left!", Toast.LENGTH_SHORT).show()
            }

            override fun onRightCardExit(dataObject: Any) {
                data.addSelectedProject(dataObject as Project)
                //Toast.makeText(this@HomeActivity, "Right!", Toast.LENGTH_SHORT).show()
            }

            override fun onAdapterAboutToEmpty(itemsInAdapter: Int) {
                // Ask for more data here
                //al!!.add("XML $i")
                arrayAdapter!!.notifyDataSetChanged()
                Log.d("LIST", "notified")
                i++
            }

            override fun onScroll(scrollProgressPercent: Float) {}
        })


        // Optionally add an OnItemClickListener
        flingContainer.setOnItemClickListener { itemPosition, dataObject ->
            Toast.makeText(
                this@HomeActivity,
                "Clicked!",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}