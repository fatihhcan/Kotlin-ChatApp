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
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth


class DialogueFragment : DialogFragment() {

    lateinit var emailEditText: EditText
    lateinit var passwordEditText: EditText
    lateinit var mContext: FragmentActivity

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view =inflater.inflate(R.layout.fragment_dialogue, container, false) //gerekli yapilara erisebilecek (buton vs.)
        emailEditText=view.findViewById(R.id.editTextDialogueEmail)
        passwordEditText=view.findViewById(R.id.editTextDialoguePassword)
        mContext= activity!!

        var btnCancel = view.findViewById<Button>(R.id.btnCancel) //butonlarin idlerini bulduk
        btnCancel.setOnClickListener {
            dialog?.dismiss()
        }




        var btnSend = view.findViewById<Button>(R.id.btnSend)
        btnSend.setOnClickListener {
            if (emailEditText.text.toString().isNotEmpty() && passwordEditText.text.toString().isNotEmpty())
            {
                loginAndResendConfirmationEmail(emailEditText.text.toString(),passwordEditText.text.toString())
            }
            else
            {
                Toast.makeText(mContext,"Fill in the blank fields.", Toast.LENGTH_SHORT).show()
            }
        }
        return view
    }

    private fun loginAndResendConfirmationEmail(email: String, password: String) {
        var credential= EmailAuthProvider.getCredential(email,password)
        FirebaseAuth.getInstance().signInWithCredential(credential)
            .addOnCompleteListener{task->
                if (task.isSuccessful)
                {
                    resendConfirmationMail()
                    dialog?.dismiss()
                }
                else
                {
                    Toast.makeText(mContext,"Email or password is incorrect.", Toast.LENGTH_SHORT).show()

                }

            }
    }

    private fun resendConfirmationMail() {
        var user = FirebaseAuth.getInstance().currentUser
        if (user != null)
        {
            user.sendEmailVerification()
                .addOnCompleteListener(object : OnCompleteListener<Void> {
                    override fun onComplete(p0: Task<Void>) {
                        if (p0.isSuccessful)
                        {
                            Toast.makeText(mContext,"Check and confirm your mailbox.", Toast.LENGTH_SHORT).show() //maili kontrol et
                        }
                        else
                        {
                            Toast.makeText(mContext,"There was an error sending mail.", Toast.LENGTH_SHORT).show()// mail gonderilirken hata olsutu

                        }
                    }

                })
        }
    }


}