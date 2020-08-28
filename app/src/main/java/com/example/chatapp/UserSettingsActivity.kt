package com.example.chatapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import kotlinx.android.synthetic.main.activity_user_settings.*

class UserSettingsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_settings)
        var user=FirebaseAuth.getInstance().currentUser!!

        editTextDetailName.setText(user.displayName.toString())


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
            if (editTextDetailName.text.toString().isNotEmpty())
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

        btnPasswordMailUpdate.setOnClickListener {
            if (editTextDetailPassword.text.toString().isNotEmpty())
            {
                var credential=EmailAuthProvider.getCredential(user.email.toString(),editTextDetailPassword.text.toString())
                user.reauthenticate(credential)
                    .addOnCompleteListener { task->
                        if (task.isSuccessful){
                                updateLayout.visibility= View.VISIBLE
                            btnUpdateMail.setOnClickListener {
                                mailAddressUpdate()
                            }

                            btnUpdatePassword.setOnClickListener {
                                passwordUpdate()
                            }
                        }else{
                            Toast.makeText(this,"You entered your current password incorrectly.", Toast.LENGTH_SHORT).show()
                            updateLayout.visibility= View.INVISIBLE

                        }
                    }
            }else{
                Toast.makeText(this,"Enter your valid password for updates.", Toast.LENGTH_SHORT).show()

            }
        }
    }

    private fun passwordUpdate() {

        var user=FirebaseAuth.getInstance().currentUser!!

        if (user !=null){
            user.updatePassword(editTextNewPassword.text.toString())
                .addOnCompleteListener { task ->
                    Toast.makeText(this,"Password has been changed.", Toast.LENGTH_SHORT).show()
                    FirebaseAuth.getInstance().signOut()
                    loginPageRoute()
                }
        }
    }

    private fun mailAddressUpdate() {
        var user=FirebaseAuth.getInstance().currentUser!!

        if (user !=null){




            user.updateEmail(editTextNewMail.text.toString())
                .addOnCompleteListener { task ->

                    Toast.makeText(this,"Mail address has been changed.", Toast.LENGTH_SHORT).show()
                    FirebaseAuth.getInstance().signOut()
                    loginPageRoute()


                }
        }
    }
    fun loginPageRoute(){
        var intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }
}


