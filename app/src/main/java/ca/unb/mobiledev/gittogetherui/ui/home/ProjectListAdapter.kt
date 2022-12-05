import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ca.unb.mobiledev.gittogetherui.R
import ca.unb.mobiledev.gittogetherui.model.DataHolder
import ca.unb.mobiledev.gittogetherui.model.Project
import ca.unb.mobiledev.gittogetherui.ui.home.PageActivity

class ProjectListAdapter(private val context: Context) : RecyclerView.Adapter<ViewHolder>() {
    private lateinit var allProjects: ArrayList<Project>
    private lateinit var data: DataHolder

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        data = context as DataHolder
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.recyclerview_row, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(allProjects!![position], context.applicationContext)
        holder.itemView.setOnClickListener {
            data.setSelectedProject(allProjects!![position])
            val intent = Intent(context, PageActivity::class.java)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return if (allProjects == null) 0 else allProjects!!.size
    }

    fun setProjectsList(projects: ArrayList<Project>) {
        allProjects = projects
        notifyDataSetChanged()
    }
}