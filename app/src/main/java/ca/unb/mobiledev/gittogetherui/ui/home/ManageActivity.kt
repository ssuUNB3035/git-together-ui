package ca.unb.mobiledev.gittogetherui.ui.home

import android.app.Activity
import android.os.Bundle
import android.widget.Button
import ca.unb.mobiledev.gittogetherui.R

class ManageActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_manage)

        var back = findViewById<Button>(R.id.manage_back_button)
        back.setOnClickListener {
            finish()
        }

    }
}