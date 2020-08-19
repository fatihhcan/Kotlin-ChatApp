package com.example.chatapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*


class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        textViewRegister.setOnClickListener{
            var intent=Intent(this,RegisterActivity::class.java)
            startActivity(intent) //tiklandiginda gidilen sayfa
        }
        btnLogin.setOnClickListener {
            if(editTextEmail.text.isNotEmpty() && editTextPassword.text.isNotEmpty())
            {
                progressBarView()

                FirebaseAuth.getInstance().signInWithEmailAndPassword(editTextEmail.text.toString(), editTextPassword.text.toString())
                    .addOnCompleteListener(object:OnCompleteListener<AuthResult>{
                        override fun onComplete(p0: Task<AuthResult>) {
                           if (p0.isSuccessful)
                           {
                               progressBarHide()
                               Toast.makeText(this@LoginActivity,"Successful login :"+FirebaseAuth.getInstance().currentUser?.email,Toast.LENGTH_SHORT).show()
                               FirebaseAuth.getInstance().signOut()
                           }
                            else
                           {
                               progressBarHide()
                               Toast.makeText(this@LoginActivity,"Incorrect entry :"+p0.exception?.message,Toast.LENGTH_SHORT).show()

                           }
                        }

                    } )

            }
            else
            {
                Toast.makeText(this@LoginActivity,"Fill in the blank fields.",Toast.LENGTH_SHORT).show()
            }
        }
    }
    private fun progressBarView(){
        progressBarLogin.visibility= View.VISIBLE
    }
    private fun progressBarHide(){
        progressBarLogin.visibility= View.INVISIBLE
    }
}