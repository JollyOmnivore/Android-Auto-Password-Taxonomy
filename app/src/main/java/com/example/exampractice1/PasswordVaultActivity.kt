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
import android.content.ClipboardManager
import android.content.ClipData
import android.content.Context
import android.util.Log
import androidx.appcompat.app.AlertDialog

class PasswordVaultActivity : AppCompatActivity(),ItemAdapter.ItemAdapterListener {
    lateinit var databaseHelper : DatabaseHelper
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ItemAdapter
    lateinit var clearVault: Button
    lateinit var passwordSort :String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()


        setContentView(R.layout.activity_password_vault)
        //toolbar setup
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

// secret sauce
        passwordSort = "recent"
        databaseHelper = DatabaseHelper(applicationContext)
        clearVault = findViewById(R.id.clearVault)
        recyclerView = findViewById(R.id.recyclerView)
        adapter = ItemAdapter(databaseHelper.getAllItems(),this)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)



        clearVault.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Clear Vault")
            builder.setMessage("This Cannot be Undone")

            builder.setPositiveButton("Yes") { dialog, which ->
                clearDatabase()
            }
            builder.setNegativeButton("No") { dialog, which ->
                //nothing happens
            }
            builder.show()
        }



    }//end of onCreate




    fun clearDatabase() {
        databaseHelper.clearDatabase()  // Clear the database
        val newCursor = databaseHelper.getAllItems()
        adapter.changeCursor(newCursor)
    }



    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.vault_menu,menu)
        return true
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.itemA -> {
                //Toast.makeText(this,"A was clicked", Toast.LENGTH_SHORT).show()
                val intent = Intent(applicationContext, FAQActivity::class.java)
                startActivity(intent)
                return true
            }
            R.id.itemB -> {
                //Toast.makeText(this,"B was clicked", Toast.LENGTH_SHORT).show()
                showSortAlertDialog()
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }


    fun showSortAlertDialog() {
        Log.d("Sort", "showSortAlertDialog: ")
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Sort Passwords")
        val options = arrayOf("Recent", "By Company")

        builder.setItems(options) { dialog, which ->
            val selectedOption = options[which]
            if (selectedOption == "Recent"){
                Toast.makeText(this, "Sorted by Most Recent", Toast.LENGTH_SHORT).show()
                val newCursor = databaseHelper.getAllItems()
                adapter.changeCursor(newCursor)
                passwordSort= "recent"
            }
            else if(selectedOption == "By Company"){
                Toast.makeText(this, "Sorted by Company", Toast.LENGTH_SHORT).show()
                val newCursor = databaseHelper.getAscendingCompany()
                adapter.changeCursor(newCursor)
                passwordSort = "DESC"
            }
        }
        builder.setNegativeButton("Cancel") { dialog, _ ->
            dialog.dismiss()
        }
        builder.show()
    }
    override fun click(position: Int) {
        val itemData = databaseHelper.getPasswordByPrimaryKey(position,passwordSort)
        val clickedCompany = itemData[0]
        val clickedPassword = itemData[1]
        val clipboard = this.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText("Password", clickedPassword)
        clipboard.setPrimaryClip(clip)
        Toast.makeText(this,"Password for $clickedCompany was Copied to Clipboard", Toast.LENGTH_SHORT).show()


    }
}