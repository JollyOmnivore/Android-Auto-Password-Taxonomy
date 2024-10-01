package com.example.exampractice1
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.content.ClipboardManager
import android.content.ClipData
import android.content.Context


class PasswordDisplayActivity : AppCompatActivity() {

     lateinit var passwordView: TextView
     lateinit var copyPass : Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_password_display)
        passwordView = findViewById(R.id.PasswordView)
        passwordView.text = intent.getStringExtra("NEWPASSWORD")

        copyPass = findViewById(R.id.copyPass)
        copyPass.setOnClickListener {
            val clipboard = this.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val password = passwordView.text.toString()
            val clip = ClipData.newPlainText("Password", password)
            // Set the clip to the clipboard
            clipboard.setPrimaryClip(clip)

        }
    }




}