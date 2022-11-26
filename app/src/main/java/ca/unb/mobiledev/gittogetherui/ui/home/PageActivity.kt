package ca.unb.mobiledev.gittogetherui.ui.home

import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import ca.unb.mobiledev.gittogetherui.R
import ca.unb.mobiledev.gittogetherui.model.DataHolder

class PageActivity : AppCompatActivity(){
    lateinit var data: DataHolder
    lateinit var projectName: TextView
    lateinit var codeLink: Button
    lateinit var description: ScrollView
    lateinit var memberList: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_page)
        data = applicationContext as DataHolder
        val selectedProject = data.getSelectedProject()

        projectName = findViewById(R.id.project_name)
        codeLink = findViewById(R.id.code_link)
        description = findViewById(R.id.descriptionScroll)
        memberList = findViewById(R.id.member_list)
        projectName.text = selectedProject.title
        //description. = selectedProject.description


        var back = findViewById<Button>(R.id.back_button)
        back.setOnClickListener {
            finish()
        }

    }
}