package com.thirtythreelabs.oktopuspicking

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.thirtythreelabs.oktopuspicking.utils.Config
import com.thirtythreelabs.oktopuspicking.utils.API
import com.thirtythreelabs.oktopuspicking.utils.GlobalVars
import com.thirtythreelabs.oktopuspicking.utils.User
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import org.json.JSONArray

class LoginActivity : AppCompatActivity() {

    private lateinit var testBanner: TextView
    private lateinit var editURLBtn: Button
    private lateinit var usernameEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var loginBtn: Button
    private lateinit var loadingSpinner: ProgressBar
    private var logTag = "LoginActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        testBanner = findViewById(R.id.test_banner)
        editURLBtn = findViewById(R.id.edit_url)
        usernameEditText = findViewById(R.id.name_input)
        passwordEditText = findViewById(R.id.password_input)
        loginBtn = findViewById(R.id.login_submit)
        loadingSpinner = findViewById(R.id.loading_spinner)

        updateURLBanner()

        loginBtn.setOnClickListener {
            loading(true)

            val username = usernameEditText.text.toString()
            val password = passwordEditText.text.toString()

            if (username.isEmpty()) {
                usernameEditText.error = getString(R.string.required)
                Toast.makeText(this@LoginActivity, "${getString(R.string.name)} ${getString(R.string.required)}", Toast.LENGTH_LONG).show()
                loading(false)
                return@setOnClickListener
            }
            if (password.isEmpty()) {
                passwordEditText.error = getString(R.string.required)
                Toast.makeText(this@LoginActivity, "${getString(R.string.password)} ${getString(R.string.required)}", Toast.LENGTH_LONG).show()
                loading(false)
                return@setOnClickListener
            }

            lifecycleScope.launch {
                Log.d(logTag, "Making login request...")

                val newUser = async { handleLogin(username, password) }.await()
                if (newUser != null) {
                    GlobalVars.user = newUser
                }

                if (GlobalVars.user != null) {
                    Log.d(logTag, GlobalVars.user!!.prettyStr())
                    startActivity(Intent(this@LoginActivity, OrdersActivity::class.java))
                    clearInputs()
                    loading(false)
                    finish()
                } else {
                    loading(false)
                    Toast.makeText(this@LoginActivity, getString(R.string.login_failed_msg), Toast.LENGTH_SHORT).show()
                }
            }
        }

        testBanner.setOnClickListener {
            startActivity(Intent(this, EditURLActivity::class.java))
        }

        editURLBtn.setOnClickListener {
            startActivity(Intent(this, EditURLActivity::class.java))
        }

    }
    override fun onResume() {
        super.onResume()
        updateURLBanner()
    }

    private fun loading(enabled: Boolean) {
        if (enabled) {
            loadingSpinner.visibility = View.VISIBLE
            loginBtn.visibility = View.GONE
            usernameEditText.isEnabled = false
            passwordEditText.isEnabled = false
        } else {
            loadingSpinner.visibility = View.GONE
            loginBtn.visibility = View.VISIBLE
            usernameEditText.isEnabled = true
            passwordEditText.isEnabled = true
        }
    }

    private fun clearInputs() {
        // hide spinner, show login btn, enable inputs
        usernameEditText.text.clear()
        passwordEditText.text.clear()
    }

    private fun updateURLBanner() {
        if (Config.URL != Config.DEFAULT_URL) {
            testBanner.visibility = View.VISIBLE
            testBanner.text = getString(R.string.test_banner_text, Config.URL)
        } else {
            testBanner.visibility = View.GONE
        }
    }

    private suspend fun handleLogin(username: String, password: String): User? {
        val jsonRes = API.handlePostRequest(
            mapOf(
                "CompanyId" to Config.COMPANY_ID,
                "ArmLogin" to username,
                "ArmPwd" to password
            ),
            "LOGIN")

        if (jsonRes == null) {
            return null
        }
        try {
            val user = User(jsonRes, username)
            return user
        } catch (e: Exception) {
            Log.e(logTag, "Error parsing JSON: $e")
            return null
        }
    }

}
