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

    lateinit var mAuthStateListener : FirebaseAuth.AuthStateListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        initMyAuthStateListener()

        textViewRegister.setOnClickListener{
            var intent=Intent(this,RegisterActivity::class.java)
            startActivity(intent) //tiklandiginda gidilen sayfa
        }

        textViewSendConfirmationMailAgain.setOnClickListener{
            var dialogueView = DialogueFragment()
            dialogueView.show(supportFragmentManager, "viewDialog")
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
                               if (!p0.result?.user?.isEmailVerified!!)
                               {
                                   FirebaseAuth.getInstance().signOut()

                               }

                             //  Toast.makeText(this@LoginActivity,"Successful login :"+FirebaseAuth.getInstance().currentUser?.email,Toast.LENGTH_SHORT).show()


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

    private fun initMyAuthStateListener()
    {
        mAuthStateListener=object :FirebaseAuth.AuthStateListener
        {
            override fun onAuthStateChanged(p0: FirebaseAuth) {
                var user=p0.currentUser
                if (user != null)
                {
                    if (user.isEmailVerified)
                    {
                        Toast.makeText(this@LoginActivity,"Mail approved login can be made.",Toast.LENGTH_SHORT).show()
                        var intent=Intent(this@LoginActivity, MainActivity::class.java)
                        startActivity(intent)
                        finish()

                    }
                    else
                    {
                        Toast.makeText(this@LoginActivity,"Confirm your e-mail address.",Toast.LENGTH_SHORT).show()
                    }
                }

            }

        }
    }

    override fun onStart()
    {
        super.onStart()
        FirebaseAuth.getInstance().addAuthStateListener(mAuthStateListener)
    }
    override fun onStop()
    {
        super.onStop()
        FirebaseAuth.getInstance().removeAuthStateListener(mAuthStateListener)
    }
}