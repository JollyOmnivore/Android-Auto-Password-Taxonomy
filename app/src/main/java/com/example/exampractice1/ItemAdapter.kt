package com.example.exampractice1


import android.database.Cursor
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ItemAdapter(var cursor: Cursor): RecyclerView.Adapter<ItemAdapter.ViewHolder>() {

    lateinit var databaseHelper : DatabaseHelper
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {


        //private val textViewName: TextView = itemView.findViewById(R.id.textViewName)

        private val companyTextview: TextView = itemView.findViewById(R.id.company)
        private val passwordTextview: TextView = itemView.findViewById(R.id.password)




        fun update(cursor: Cursor) {
            val company = cursor.getColumnIndexOrThrow("Company")
            val password = cursor.getColumnIndexOrThrow("Password")

            companyTextview.text = cursor.getString(company)
            passwordTextview.text = cursor.getString(password)


        }


    }
    fun changeCursor(newCursor: Cursor) {//swap needed to update the recycler view
        if (this.cursor != newCursor) {
            this.cursor.close()
            this.cursor = newCursor
            notifyDataSetChanged()
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // no more
        cursor.moveToPosition(position)
        holder.update(cursor)
    }

    override fun getItemCount(): Int {
        return cursor.count

    }


}
