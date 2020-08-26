package com.example.chatapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentActivity
import com.google.firebase.auth.FirebaseAuth


class ForgotPasswordFragment : DialogFragment() {

    lateinit var emailEditText: EditText

    lateinit var mContext: FragmentActivity

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view =inflater.inflate(R.layout.fragment_forgot_password, container, false)
        mContext= activity!!
        emailEditText=view.findViewById(R.id.editTextResendPassword)

        var btnCancel= view.findViewById<Button>(R.id.btnForgotPasswordCancel)
        btnCancel.setOnClickListener {
            dialog?.dismiss()
        }
        var btnSend=view.findViewById<Button>(R.id.btnForgotPasswordSend)
        btnSend.setOnClickListener {
        FirebaseAuth.getInstance().sendPasswordResetEmail(emailEditText.text.toString())
            .addOnCompleteListener { task ->
                if (task.isSuccessful)
                {
                    Toast.makeText(mContext,"Password recovery mail has been sent.", Toast.LENGTH_SHORT).show()
                    dialog?.dismiss()
                }else{
                    Toast.makeText(mContext,"An error occurred :"+task.exception?.message, Toast.LENGTH_SHORT).show()
                    dialog?.dismiss()

                }
            }
        }
        return view
    }


}