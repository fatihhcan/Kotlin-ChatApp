package com.example.chatapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.DialogFragment


class DialogueFragment : DialogFragment() {

    lateinit var emailEditText: EditText
    lateinit var passwordEditText: EditText

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view =inflater.inflate(R.layout.fragment_dialogue, container, false) //gerekli yapilara erisebilecek (buton vs.)
        emailEditText=view.findViewById(R.id.editTextDialogueEmail)
        passwordEditText=view.findViewById(R.id.editTextDialoguePassword)

        var btnCancel = view.findViewById<Button>(R.id.btnCancel) //butonlarin idlerini bulduk
        btnCancel.setOnClickListener {
            dialog?.dismiss()
        }




        var btnSend = view.findViewById<Button>(R.id.btnSend)
        btnSend.setOnClickListener {
            Toast.makeText(activity,"Send button clicked.", Toast.LENGTH_SHORT).show()
        }
        return view
    }


}