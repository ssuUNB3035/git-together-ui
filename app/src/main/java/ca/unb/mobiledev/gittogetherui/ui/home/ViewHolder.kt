import android.content.Context
import android.content.Intent
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import ca.unb.mobiledev.gittogetherui.R
import ca.unb.mobiledev.gittogetherui.model.DataHolder
import ca.unb.mobiledev.gittogetherui.model.Project
import ca.unb.mobiledev.gittogetherui.ui.home.ManageActivity

class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val projectName: TextView = itemView.findViewById(R.id.project_name_row)
    private lateinit var bindedProject: Project
    private val removeButton: Button = itemView.findViewById(R.id.remove_project_button)
    private lateinit var data: DataHolder
    var onItemClick: ((Project) -> Unit)? = null

    fun bind(project: Project, context: Context) {
        projectName.text = project.name
        bindedProject = project
        data = context as DataHolder

        removeButton.setOnClickListener {
            data.removeSelectedProject(bindedProject)
            data.getListAdapter().setProjectsList(data.getSelectedProjects())
        }

        bindOrHideTextView(projectName, project.name)
    }

    private fun bindOrHideTextView(textView: TextView, data: String?) {
        if (data == null) {
            textView.visibility = View.GONE
        } else {
            textView.text = data
            textView.visibility = View.VISIBLE
        }
    }
    init {
        itemView.setOnClickListener{
            onItemClick?.invoke(data.getSelectedProjects()[adapterPosition])
        }
    }
}