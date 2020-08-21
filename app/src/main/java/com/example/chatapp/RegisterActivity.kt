package com.example.chatapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        btnRegister.setOnClickListener {
            if (editTextEmail.text.isNotEmpty()&&editTextPassword.text.isNotEmpty()&&editTextAgainPassword.text.isNotEmpty()) //Alanlar bos kalmasin
            {
                if(editTextPassword.text.toString().equals(editTextAgainPassword.text.toString()))
                {
                    newUserAccount(editTextEmail.text.toString(), editTextPassword.text.toString())
                }
                else
                {
                    Toast.makeText(this,"Passwords are not the same.", Toast.LENGTH_SHORT).show() //Sifreler ayni degil

                }
            }
            else
            {
                Toast.makeText(this,"Fill in the blank fields.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun newUserAccount(mail: String, password: String) {
        progressBarView()
        //firebase islemleri yeni uye kayit
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(mail,password)
            .addOnCompleteListener(object: OnCompleteListener<AuthResult> { // yapilan islem basarili ise burasi tetiklenicek
                override fun onComplete(p0: Task<AuthResult>) {
                    if (p0.isSuccessful)
                    {
                        Toast.makeText(this@RegisterActivity,"New account registration successful."+FirebaseAuth.getInstance().currentUser?.email, Toast.LENGTH_SHORT).show() // yeni kayit basarili
                        sendConfirmationMail()
                        FirebaseAuth.getInstance().signOut() //kayit olduktan sonra var olan kullanici sistemden atilir
                    }
                    else
                    {
                        Toast.makeText(this@RegisterActivity,"Problem with new account registration."+p0.exception?.message, Toast.LENGTH_SHORT).show() // yeni kayit sorun olustu
                    }
                }
            })
        progressBarHide()
    }
    private fun sendConfirmationMail()
    {
        var user = FirebaseAuth.getInstance().currentUser
        if (user != null)
        {
            user.sendEmailVerification()
                .addOnCompleteListener(object :OnCompleteListener<Void>{
                    override fun onComplete(p0: Task<Void>) {
                        if (p0.isSuccessful)
                        {
                            Toast.makeText(this@RegisterActivity,"Check and confirm your mailbox.", Toast.LENGTH_SHORT).show() //maili kontrol et
                        }
                        else
                        {
                            Toast.makeText(this@RegisterActivity,"There was an error sending mail.", Toast.LENGTH_SHORT).show()// mail gonderilirken hata olsutu

                        }
                    }

                })
        }
    }
    private fun progressBarView(){
        progressBar.visibility= View.VISIBLE
    }
    private fun progressBarHide(){
        progressBar.visibility= View.INVISIBLE
    }
}