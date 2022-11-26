package ca.unb.mobiledev.gittogetherui.model

import ProjectListAdapter
import android.app.Application
import ca.unb.mobiledev.gittogetherui.ui.home.HomeActivity


class DataHolder : Application() {
    var selectedProjectList : ArrayList<Project> = ArrayList()
    var focusedProject: Project = Project()
    var mainActivity: HomeActivity = HomeActivity()
    private lateinit var projectListAdapter: ProjectListAdapter

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