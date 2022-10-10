package ca.unb.mobiledev.gittogetherui.ui.home

import android.app.Activity
import android.widget.ArrayAdapter
import android.os.Bundle
import android.util.Log
import android.view.View
import ca.unb.mobiledev.gittogetherui.R
import com.lorentzos.flingswipe.SwipeFlingAdapterView
import com.lorentzos.flingswipe.SwipeFlingAdapterView.onFlingListener
import android.widget.Toast
import java.util.ArrayList

class HomeActivity : Activity() {
    private var al: ArrayList<String>? = null
    private var arrayAdapter: ArrayAdapter<String>? = null
    private var i = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        // Adding the projects to the swipe cards
        al = ArrayList()
        al!!.add("Project #1")
        al!!.add("Project #2")
        al!!.add("Project #3")
        al!!.add("Project #4")
        al!!.add("Project #5")
        al!!.add("Project #6")
        al!!.add("Project #7")
        al!!.add("Project #8")

        arrayAdapter = ArrayAdapter(this, R.layout.item, R.id.helloText, al!!)

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
                Toast.makeText(this@HomeActivity, "Left!", Toast.LENGTH_SHORT).show()
            }

            override fun onRightCardExit(dataObject: Any) {
                Toast.makeText(this@HomeActivity, "Right!", Toast.LENGTH_SHORT).show()
            }

            override fun onAdapterAboutToEmpty(itemsInAdapter: Int) {
                // Ask for more data here
                al!!.add("XML $i")
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