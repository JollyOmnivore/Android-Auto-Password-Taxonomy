package com.example.exampractice1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.DatePicker
import android.widget.EditText
import android.widget.TextView
import com.google.android.material.textfield.TextInputEditText
import kotlin.math.abs

lateinit var rootEditText: EditText
lateinit var modifierEditText: EditText
lateinit var dateOfBirth: DatePicker
lateinit var submitButton : Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Ensure these IDs map to TextInputEditText in your layout file
        rootEditText = findViewById(R.id.rootEdit)
        modifierEditText = findViewById(R.id.ModifierEdit)
        dateOfBirth = findViewById(R.id.datePicker)
        submitButton = findViewById(R.id.submitButton)


        submitButton.setOnClickListener {
            val root = rootEditText.text.toString()
            val company = modifierEditText.text.toString()
            val dayOfBirth = dateOfBirth.dayOfMonth.toInt()
            val monthOfBirth = dateOfBirth.month.toInt()
            val yearOfBirth = dateOfBirth.year.toInt()

            val distanceFrom2000 = abs(2000 - yearOfBirth)

            var moddedDate = dayOfBirth+distanceFrom2000
            var moddedMonth = monthOfBirth+distanceFrom2000

            val companyModifier = "${company[0]}${company[((company.length/2).toInt())]}"

            var newPassword = "${companyModifier.uppercase()}${root}${moddedMonth}?${moddedDate}!"


            intent = Intent(applicationContext, PasswordDisplayActivity::class.java)
            intent.putExtra("NEWPASSWORD", newPassword)
            startActivity(intent)

        }
    }



}
