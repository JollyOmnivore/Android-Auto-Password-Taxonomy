package com.example.exampractice1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.DatePicker
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import com.google.android.material.textfield.TextInputEditText
import kotlin.math.abs

lateinit var rootEditText: EditText
lateinit var modifierEditText: EditText
lateinit var dateOfBirth: DatePicker
lateinit var submitButton : Button
lateinit var pickDate : Button
lateinit var resultTextView: TextView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        // Ensure these IDs map to TextInputEditText in your layout file
        rootEditText = findViewById(R.id.rootEdit)
        modifierEditText = findViewById(R.id.ModifierEdit)
        //dateOfBirth = findViewById(R.id.datePicker)
        submitButton = findViewById(R.id.submitButton)
        pickDate = findViewById(R.id.pickDate)
        resultTextView = findViewById(R.id.resultTextView)


        if(FormData.dayOfBirth != ""){
            rootEditText.setText(FormData.root)
            modifierEditText.setText(FormData.company)
        }




        pickDate.setOnClickListener {
            //Toast.makeText(this,"Test1", Toast.LENGTH_SHORT).show()

            val DateFragment = DateFragment()
            DateFragment.show(supportFragmentManager,null)
            val root = rootEditText.text.toString()
            val company = modifierEditText.text.toString()
            FormData.root = root
            FormData.company = company

        }

        submitButton.setOnClickListener {



            val root = rootEditText.text.toString()
            val company = modifierEditText.text.toString()

            if(FormData.dayOfBirth != ""){
                val dayOfBirth = FormData.dayOfBirth.toInt()
                val monthOfBirth = FormData.monthOfBirth.toInt()
                val yearOfBirth = FormData.yearOfBirth.toInt()

                if(root == "" || company == ""){
                    Toast.makeText(this,"Please enter Root or Company", Toast.LENGTH_SHORT).show()
                }
                else{
                    val distanceFrom2000 = abs(2000 - yearOfBirth)

                    val moddedDate = dayOfBirth+distanceFrom2000
                    val moddedMonth = monthOfBirth+distanceFrom2000

                    var companyModifier = ""
                    if(company.length < 4){// if the modifier is less than 4 letters only use the first letter
                        companyModifier = company[0].toString()
                    }
                    else {
                        companyModifier = "${company[0]}${company[3]}"
                    }
                    val newPassword = "${companyModifier.uppercase()}${root}${moddedMonth}?${moddedDate}!"
                    intent = Intent(applicationContext, PasswordDisplayActivity::class.java)
                    intent.putExtra("NEWPASSWORD", newPassword)
                    intent.putExtra("COMPANY", company)
                    startActivity(intent)

                }




            }
            else{
                Toast.makeText(this,"Please enter Data of Birth", Toast.LENGTH_SHORT).show()
            }// end of fail statement

        }// end of submit

    }// end of on create





    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        return when(item.itemId){
            R.id.itemA -> {
                Toast.makeText(this,"A was clicked", Toast.LENGTH_SHORT).show()
                val intent = Intent(applicationContext, FAQActivity::class.java)
                startActivity(intent)
                return true
            }
            R.id.itemB -> {
                Toast.makeText(this,"A was clicked", Toast.LENGTH_SHORT).show()
                val intent = Intent(applicationContext, PasswordVaultActivity::class.java)
                startActivity(intent)
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }


    fun clearFormDataClass(){
        FormData.company = ""
        FormData.root = ""
        FormData.dayOfBirth = ""
        FormData.monthOfBirth = ""
        FormData.yearOfBirth = ""
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.my_menu,menu)
        return true
    }




}
