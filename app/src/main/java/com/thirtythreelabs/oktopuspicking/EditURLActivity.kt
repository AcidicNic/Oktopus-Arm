package com.thirtythreelabs.oktopuspicking

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.thirtythreelabs.oktopuspicking.utils.Config

class EditURLActivity : AppCompatActivity() {

    private lateinit var urlInput: EditText
    private lateinit var submitBtn: Button
    private lateinit var backBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_url)

        urlInput = findViewById(R.id.url_input)
        submitBtn = findViewById(R.id.url_submit)
        backBtn = findViewById(R.id.back_btn)

        urlInput.setText(Config.URL)

        submitBtn.setOnClickListener {
            val newURL = urlInput.text.toString()
            Config.updateURL(newURL)
            finish()
        }

        backBtn.setOnClickListener {
            finish()
        }
    }
}
