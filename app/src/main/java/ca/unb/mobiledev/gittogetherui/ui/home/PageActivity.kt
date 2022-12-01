package ca.unb.mobiledev.gittogetherui.ui.home

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import ca.unb.mobiledev.gittogetherui.R
import ca.unb.mobiledev.gittogetherui.model.DataHolder
import org.w3c.dom.Text

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
        codeLink.setOnClickListener{
            selectedProject.link?.let { it1 -> goToRepo(it1) }
        }

        description = findViewById(R.id.descriptionScroll)
        memberList = findViewById(R.id.member_list)

        projectName.text = selectedProject.name

        val descText: TextView = TextView(this)
        descText.text = selectedProject.description
        description.addView(descText)

        codeLink.text = selectedProject.link


        var back = findViewById<Button>(R.id.back_button)
        back.setOnClickListener {
            finish()
        }
    }

    fun goToRepo(url: String) {
        val webpage: Uri = Uri.parse(url)
        val intent = Intent(Intent.ACTION_VIEW, webpage)
        if (intent.resolveActivity(packageManager) != null) {
            startActivity(intent)
        }
    }
}