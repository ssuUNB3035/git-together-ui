package ca.unb.mobiledev.gittogetherui.ui.home

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import ca.unb.mobiledev.gittogetherui.model.Project
import ca.unb.mobiledev.gittogetherui.R

class CardAdapter(private val mContext: Context, list: ArrayList<Project>) :
    ArrayAdapter<Project>(mContext, 0, list) {
    private var projectList: List<Project> = ArrayList()

    init {
        projectList = list
    }

    // Overrides the getView for array adapter
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var listItem: View? = convertView
        if (listItem == null) {
            listItem = LayoutInflater.from(mContext).inflate(R.layout.item,parent,false);
        }

        val titleView = listItem!!.findViewById(R.id.card_title) as TextView
        val descView = listItem!!.findViewById(R.id.card_description) as TextView
        val tagsView = listItem!!.findViewById(R.id.card_tags) as TextView

        // Need to add location
        titleView.text = projectList[position]?.title
        descView.text = projectList[position]?.description
        tagsView.text = projectList[position]?.tags

        return listItem!!
    }
}