package com.example.chatapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import kotlinx.android.synthetic.main.activity_user_settings.*

class UserSettingsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_settings)
        var user=FirebaseAuth.getInstance().currentUser!!

        editTextDetailName.setText(user.displayName.toString())
        editTextDetailMail.setText(user.email.toString())

        btnPasswordSend.setOnClickListener {
            FirebaseAuth.getInstance().sendPasswordResetEmail(FirebaseAuth.getInstance().currentUser?.email.toString())
                .addOnCompleteListener { task ->
                    if (task.isSuccessful)
                    {
                        Toast.makeText(this,"Password recovery mail has been sent.", Toast.LENGTH_SHORT).show()

                    }else{
                        Toast.makeText(this,"An error occurred :"+task.exception?.message, Toast.LENGTH_SHORT).show()


                    }
                }
        }

        btnSaveChanges.setOnClickListener {
            if (editTextDetailName.text.toString().isNotEmpty() && editTextDetailMail.text.toString().isNotEmpty())
            {
                if (!editTextDetailName.text.toString().equals(user.displayName.toString()))
                    {
                        var updateInformation=UserProfileChangeRequest.Builder()
                            .setDisplayName(editTextDetailName.text.toString())
                            .build()
                        user.updateProfile(updateInformation)
                            .addOnCompleteListener { task ->
                                if (task.isSuccessful)
                                {
                                    Toast.makeText(this,"Changes were made.", Toast.LENGTH_SHORT).show()

                                }
                            }

                    }
            }
            else{
                Toast.makeText(this,"Fill in  the blank fields.", Toast.LENGTH_SHORT).show()
            }
        }
    }
}


