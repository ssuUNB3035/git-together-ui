package ca.unb.mobiledev.gittogetherui.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import ca.unb.mobiledev.gittogetherui.R

class AddProjectsFragment: DialogFragment() {

    override fun onCreateView(
        inflator: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var rootView: View = inflator!!.inflate(R.layout.addprojectfragment, container, false)

        var cancelButton = rootView.findViewById<Button>(R.id.addProjectsCancel)
        cancelButton.setOnClickListener {
            dismiss()
        }

        var addButton = rootView.findViewById<Button>(R.id.addProjectsAdd)
        addButton.setOnClickListener {
            // Code to send api UPDATE call
            Toast.makeText(context, "Project is Requested", Toast.LENGTH_SHORT).show()

            dismiss()
        }

        return rootView
    }
}