package ca.unb.mobiledev.gittogetherui.model

import ProjectListAdapter
import android.app.Application
import ca.unb.mobiledev.gittogetherui.ui.home.HomeActivity


class DataHolder : Application() {
    var unFilteredProjects: ArrayList<Project> = ArrayList()
    var projectList: ArrayList<Project> = ArrayList()
    var selectedProjectList : ArrayList<Project> = ArrayList()
    var focusedProject: Project = Project()
    var mainActivity: HomeActivity = HomeActivity()
    val availableTags: ArrayList<String> = ArrayList()
    private lateinit var projectListAdapter: ProjectListAdapter

    @JvmName("getAvailableTags1")
    fun getAvailableTags(): ArrayList<String> {
        if (availableTags.size < 2) {
            availableTags.add("C")
            availableTags.add("C#")
            availableTags.add("C++")
            availableTags.add("Java")
            availableTags.add("Kotlin")
            availableTags.add("Python")
            availableTags.add("Javascript")
            availableTags.add("CSS")
            availableTags.add("HTML")
            availableTags.add("React")
            availableTags.add("NodeJS")
            availableTags.add("Matlab")
            availableTags.add("English")
            availableTags.add("French")
            availableTags.add("Spanish")
            availableTags.add("Hindi")
            availableTags.add("Chinese")
            availableTags.add("Korean")
            availableTags.add("Japanese")
            availableTags.add("German")
        }
        return availableTags
    }

    fun filterProjects(tags: ArrayList<String>) {
        projectList = ArrayList()
        for (p: Project in unFilteredProjects) {
            for (t: String in tags) {
                if (p.tags?.contains(t) == true) {
                    projectList.add(p)
                    break
                }
            }
        }
    }

    fun addSelectedProject(p : Project) {
        selectedProjectList.add(p)
    }

    fun removeSelectedProject(p: Project) {
        selectedProjectList.remove(p)
    }

    fun getSelectedProjects(): ArrayList<Project> {
        return selectedProjectList
    }

    fun setSelectedProject(p: Project) {
        focusedProject = p
    }

    fun getSelectedProject(): Project {
        return focusedProject
    }

    fun addProject(p: Project) {
        projectList.add(p)
        unFilteredProjects.add(p)
    }

    @JvmName("getProjectList1")
    fun getProjectList(): ArrayList<Project> {
        return projectList
    }

    fun setActivity(a: HomeActivity) {
        mainActivity = a
    }

    fun getActivity(): HomeActivity {
        return mainActivity
    }

    fun setListAdapter(p: ProjectListAdapter) {
        projectListAdapter = p
    }
    fun getListAdapter(): ProjectListAdapter {
        return projectListAdapter
    }
}