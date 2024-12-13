package com.example.exampractice1



import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Intent
import android.icu.util.Calendar
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.Toast
import androidx.fragment.app.DialogFragment


class DateFragment : DialogFragment(), DatePickerDialog.OnDateSetListener {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_date, container, false)




        return view
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val calender = Calendar.getInstance()
        val year = calender.get(Calendar.YEAR)
        val month = calender.get(Calendar.MONTH)
        val day = calender.get(Calendar.DAY_OF_MONTH)
        return DatePickerDialog(requireActivity(), this, year, month,day)



    }

    override fun onDateSet(view: DatePicker, year: Int, month: Int, dayOfMonth: Int) {
        val fixedMonth = month+1
        FormData.yearOfBirth = year.toString()
        FormData.monthOfBirth = fixedMonth.toString()//starts at 0
        FormData.dayOfBirth = dayOfMonth.toString()

        val intent = Intent(requireContext(), MainActivity::class.java)
        startActivity(intent)
        }




}