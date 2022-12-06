package ca.unb.mobiledev.gittogetherui.ui.home

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import ca.unb.mobiledev.gittogetherui.R
import ca.unb.mobiledev.gittogetherui.model.DataHolder
import com.google.android.material.textfield.TextInputEditText
import java.lang.Thread.sleep

class AddProjectsFragment: DialogFragment() {
    lateinit var data: DataHolder

    override fun onCreateView(
        inflator: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var rootView: View = inflator!!.inflate(R.layout.addprojectfragment, container, false)

        var addProjectsInput = rootView.findViewById<TextInputEditText>(R.id.addProjectsInput)
        var cancelButton = rootView.findViewById<Button>(R.id.addProjectsCancel)
        cancelButton.setOnClickListener {
            dismiss()
        }

        var addButton = rootView.findViewById<Button>(R.id.addProjectsAdd)
        addButton.setOnClickListener {
            // Code to send api UPDATE call
            val jsonUtils: JsonUtils = JsonUtils(data.getActivity())
            jsonUtils.findJSONviaHash(addProjectsInput.text.toString())
            Toast.makeText(context, "Project is Requested", Toast.LENGTH_SHORT).show()
            sleep(100)
            activity?.finish()
            startActivity(activity?.intent)

            dismiss()
        }

        return rootView
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        data = context.applicationContext as DataHolder
    }
}