package ca.unb.mobiledev.gittogetherui.ui.home

import android.annotation.SuppressLint
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

class InviteMemberFragment: DialogFragment() {
    lateinit var data: DataHolder

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflator: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var rootView: View = inflator!!.inflate(R.layout.invitefragment, container, false)

        var input = rootView.findViewById<TextInputEditText>(R.id.inviteInput)
        var cancelButton = rootView.findViewById<Button>(R.id.inviteCancel)
        cancelButton.setOnClickListener {
            dismiss()
        }

        var addButton = rootView.findViewById<Button>(R.id.inviteButton)
        addButton.setOnClickListener {
            // Code to send api UPDATE call
            val jsonUtils: JsonUtils = JsonUtils(data.getActivity())
            data.getSelectedProject().name?.let { it1 -> data.getSelectedProject().hash?.let { it2 ->
                data.getActivity().inviteMember(it1,
                    it2, input.text.toString())
            } }
            Toast.makeText(context, "Email Invite Sent", Toast.LENGTH_SHORT).show()

            dismiss()
        }

        return rootView
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        data = context.applicationContext as DataHolder
    }
}