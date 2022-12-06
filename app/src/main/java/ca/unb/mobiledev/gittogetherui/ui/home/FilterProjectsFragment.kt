package ca.unb.mobiledev.gittogetherui.ui.home

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.DialogFragment
import ca.unb.mobiledev.gittogetherui.R
import ca.unb.mobiledev.gittogetherui.model.DataHolder
import java.util.ArrayList

/*
    Filter Projects Dialog
    Users may select tags to filter the projects on the homepage
 */
class FilterProjectsFragment: DialogFragment() {
    lateinit var data: DataHolder
    private var selectedTags: ArrayList<String> = ArrayList()

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflator: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var rootView: View = inflator!!.inflate(R.layout.filterprojectsfragment, container, false)

        var tagDropdown: Spinner = rootView.findViewById<Spinner>(R.id.filtertagdropdown)
        var tagLayout: LinearLayout = rootView.findViewById<LinearLayout>(R.id.filterProjectsTagLayout)

        var cancelButton = rootView.findViewById<Button>(R.id.filterProjectsCancel)
        cancelButton.setOnClickListener {
            dismiss()
        }

        var filterButton = rootView.findViewById<Button>(R.id.filterProjectsFilter)
        filterButton.setOnClickListener {
            data.filterProjects(selectedTags)
            data.getActivity().updateArrayAdapter()
            Toast.makeText(context, "Filtered Projects", Toast.LENGTH_SHORT).show()

            dismiss()
        }

        val adapter: ArrayAdapter<String> = ArrayAdapter<String>(
            data.applicationContext,
            android.R.layout.simple_spinner_dropdown_item,
            data.getAvailableTags()
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        tagDropdown.setAdapter(adapter)

        tagDropdown.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            @SuppressLint("ResourceType")
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                if (!selectedTags.contains(data.availableTags.get(position))) {
                    var tagText = data.availableTags.get(position)
                    var btn = Button(context)
                    btn.text = tagText
                    btn.setBackgroundColor(Color.BLUE)
                    btn.setTextColor(Color.WHITE)
                    btn.width = ViewGroup.LayoutParams.WRAP_CONTENT
                    btn.height = ViewGroup.LayoutParams.WRAP_CONTENT

                    selectedTags.add(tagText)
                    tagLayout.addView(btn)
                    var space: Space = Space(context)
                    space.minimumWidth = 5
                    tagLayout.addView(space)
                    btn.setOnClickListener {
                        tagLayout.removeView(btn)
                        tagLayout.removeView(space)
                        selectedTags.remove(tagText)
                    }
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                // Nothing
            }
        }

        return rootView
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        data = context.applicationContext as DataHolder
    }
}