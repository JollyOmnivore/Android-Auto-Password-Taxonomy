package com.example.exampractice1
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.content.ClipboardManager
import android.content.ClipData
import android.content.Context
import android.widget.Toast


class PasswordDisplayActivity : AppCompatActivity() {

     lateinit var passwordView: TextView
     lateinit var newPass: TextView
     lateinit var copyPass : Button
     lateinit var addToVault:Button


    lateinit var databaseHelper : DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_password_display)

        databaseHelper = DatabaseHelper(applicationContext)

        addToVault = findViewById(R.id.addToVault)
        passwordView = findViewById(R.id.PasswordView)
        newPass = findViewById(R.id.newPass)
        copyPass = findViewById(R.id.copyPass)

        //only way to get here is with an intent



        var password = ""
        password = intent.getStringExtra("NEWPASSWORD").toString()
        passwordView.text = password



        var companyName = ""
        companyName = intent.getStringExtra("COMPANY").toString()
        newPass.text = "New Password For ${companyName}"


        copyPass.setOnClickListener {
            val clipboard = this.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val password = passwordView.text.toString()
            val clip = ClipData.newPlainText("Password", password)
            // Set the clip to the clipboard
            clipboard.setPrimaryClip(clip)
            Toast.makeText(this,"Password Copied to Clipboard", Toast.LENGTH_SHORT).show()

        }
        addToVault.setOnClickListener {
            if (companyName != "" || password != ""){
                    databaseHelper.insertItem(companyName,password)
                }

            else{
                Toast.makeText(this,"Failed to add to vault", Toast.LENGTH_SHORT).show()
            }
            }





        }



}




