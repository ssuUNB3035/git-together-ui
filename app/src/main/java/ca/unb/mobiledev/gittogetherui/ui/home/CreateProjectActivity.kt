package ca.unb.mobiledev.gittogetherui.ui.home

import ProjectListAdapter
import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import ca.unb.mobiledev.gittogetherui.R
import ca.unb.mobiledev.gittogetherui.model.Project

class CreateProjectActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create)

        var back = findViewById<Button>(R.id.create_back_button)
        back.setOnClickListener {
            finish()
        }
        var cancel = findViewById<Button>(R.id.cancel_project_button)
        cancel.setOnClickListener {
            finish()
        }

    }
}
