package com.example.exampractice1


import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import android.widget.Toast
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    var isInserting = false

    companion object {
        private const val DATABASE_NAME = "items.db"
        private const val DATABASE_VERSION = 1

    }

    override fun onCreate(db: SQLiteDatabase) {
        val query = """
            CREATE TABLE passwordVault(
            _id INTEGER PRIMARY KEY AUTOINCREMENT,
            Company TEXT,
            Password TEXT
            );
        """.trimIndent()
        db.execSQL(query)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {

    }
    /*
// backup in case I broke the better insert
    fun insertItem(company: String,password: String) {
        val db = this.writableDatabase
        isInserting = true

        CoroutineScope(Dispatchers.IO).launch {
            val passwordInfo = ContentValues().apply {
                put("Company", company)
                put("Password", password)
            }
            db.insertWithOnConflict("passwordVault", null, passwordInfo, SQLiteDatabase.CONFLICT_REPLACE)// this doesnt work

            withContext(Dispatchers.Main) {
                db.close()
                isInserting = false
                Log.d("CR", "Finished inserting Password")


            }

        }
    }

     */





fun insertItem(company: String, password: String) { // better insert which overrides vault passwords of matching company's
    val db = this.writableDatabase
    isInserting = true
    CoroutineScope(Dispatchers.IO).launch {
        // Checking if the company is in the table
        val cursor = db.query(
            "passwordVault",
            arrayOf("_id"),
            "Company = ?",
            arrayOf(company),
            null,
            null,
            null
        )

        val contentValues = ContentValues().apply {
            put("Company", company)
            put("Password", password)
        }

        if (cursor.moveToFirst()) {
            // found matching company name
            //note for later add casing check like Github vs GitHub
            db.update(
                "passwordVault",
                contentValues,
                "Company = ?",
                arrayOf(company)
            )
        }
        else {
            // not found
            db.insert("passwordVault", null, contentValues)
        }
        cursor.close()

        withContext(Dispatchers.Main) {
            db.close()
            isInserting = false
            Log.d("DB", "Insert/Update operation completed for Company: $company")
        }

    }
}



    fun getAllItems(): Cursor {
        val db = this.readableDatabase
        val cursor = db.query(
            "passwordVault",
            arrayOf("_id", "Company", "Password"),
            null,
            null,
            null,
            null,
            null
        )
        return cursor
        //finish
    }


    fun getAscendingCompany(): Cursor {// not needed yet turn it into sort alphabetically
        val db = this.readableDatabase
        val cursor = db.query(
            "passwordVault",
            arrayOf("_id", "Company", "Password"),
            null,
            null,
            null,
            null,
            "Company DESC" // https://www.sqlitetutorial.net/sqlite-order-by/
        )
        return cursor
    }


    fun clearDatabase() {
        val db = this.writableDatabase
        db.delete("passwordVault", null, null)  // Deletes all rows in the colors table
        db.close()

    }

    fun getPasswordByPrimaryKey(position: Int, sortType: String): Array<String> {
        Log.d("DB", position.toString())
        var password = ""


        if(sortType == "recent") {
            val db = this.readableDatabase
            val cursor = db.query(
                "passwordVault",
                arrayOf("_id", "Company", "Password"),
                null,
                null,
                null,
                null,
                null
            )
            cursor.moveToPosition(position)
            val passwordLocation = cursor.getColumnIndexOrThrow("Password")
            val companyLocation = cursor.getColumnIndexOrThrow("Company")
            Log.d("DB", passwordLocation.toString())
            val selectedPassword = cursor.getString(passwordLocation)
            val selectedCompany = cursor.getString(companyLocation)

            Log.d("DB", selectedPassword.toString())
            Log.d("DB", selectedCompany.toString())
            //cursor.close()
            db.close()
            return arrayOf(selectedCompany, selectedPassword)
        }

        else if (sortType == "DESC"){
            val db = this.readableDatabase
            val cursor = db.query(
                "passwordVault",
                arrayOf("_id", "Company", "Password"),
                null,
                null,
                null,
                null,
                "Company DESC" // https://www.sqlitetutorial.net/sqlite-order-by/
            )
            //this section took embarrassingly long
            cursor.moveToPosition(position)
            val passwordLocation = cursor.getColumnIndexOrThrow("Password")
            val companyLocation = cursor.getColumnIndexOrThrow("Company")
            Log.d("DB", passwordLocation.toString())
            val selectedPassword = cursor.getString(passwordLocation)
            val selectedCompany = cursor.getString(companyLocation)

            Log.d("DB", selectedPassword.toString())
            Log.d("DB", selectedCompany.toString())
            //cursor.close()
            db.close()
            return arrayOf(selectedCompany, selectedPassword)
        }


        return arrayOf("error", "error")
    }













}