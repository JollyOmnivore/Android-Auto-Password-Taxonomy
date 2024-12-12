package com.example.exampractice1

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class PasswordVaultActivity : AppCompatActivity() {
    lateinit var databaseHelper : DatabaseHelper
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ItemAdapter
    lateinit var clearVault: Button



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)


        setContentView(R.layout.activity_password_vault)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

// secret sauce
        databaseHelper = DatabaseHelper(applicationContext)
        clearVault = findViewById(R.id.clearVault)
        recyclerView = findViewById(R.id.recyclerView)
        adapter = ItemAdapter(databaseHelper.getAllItems())
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)



        clearVault.setOnClickListener {
            clearDatabase()
        }



    }//end of onCreate




    fun clearDatabase() {
        databaseHelper.clearDatabase()  // Clear the database
        val newCursor = databaseHelper.getAllItems()
        adapter.changeCursor(newCursor)
    }













    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.my_menu,menu)
        return true
    }

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
}