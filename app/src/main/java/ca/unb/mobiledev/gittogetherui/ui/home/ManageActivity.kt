package ca.unb.mobiledev.gittogetherui.ui.home

import ProjectListAdapter
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import ca.unb.mobiledev.gittogetherui.R
import ca.unb.mobiledev.gittogetherui.model.DataHolder
import ca.unb.mobiledev.gittogetherui.model.Project


class ManageActivity : AppCompatActivity() {
    private lateinit var projectsRecyclerView: RecyclerView
    private lateinit var projectsListAdapter: ProjectListAdapter
    private var liveProjectList = ArrayList<Project>()
    lateinit var data: DataHolder

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_manage)
        data = applicationContext as DataHolder
        projectsRecyclerView = findViewById<RecyclerView>(R.id.project_list)
        projectsListAdapter = ProjectListAdapter(applicationContext)
        projectsRecyclerView.adapter = projectsListAdapter
        data.setListAdapter(projectsListAdapter)

        projectsListAdapter.setProjectsList(data.getSelectedProjects())

        data.getSelectedProjects()

        var back = findViewById<Button>(R.id.manage_back_button)
        back.setOnClickListener {
            finish()
        }

        var addProject = findViewById<Button>(R.id.addProjectButton)
        addProject.setOnClickListener {
            var dialog = AddProjectsFragment()

            dialog.show(supportFragmentManager, "addProjectDialog")
        }

    }
}
