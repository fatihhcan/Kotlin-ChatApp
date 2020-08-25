package com.example.chatapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_register.*

class MainActivity : AppCompatActivity() {

    lateinit var myAuthStateListener:FirebaseAuth.AuthStateListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initAuthStateListener()


    }

    private fun initAuthStateListener() {
        myAuthStateListener=object :FirebaseAuth.AuthStateListener{
            override fun onAuthStateChanged(p0: FirebaseAuth) {
                var user= p0.currentUser
                if (user != null)
                {

                }else
                {
                    var intent= Intent(this@MainActivity,LoginActivity::class.java)
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                    startActivity(intent)
                    finish()
                }
            }

        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.home_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when(item?.itemId){
            R.id.menuExit -> {
                logOut()
                return true
            }
        }

        return super.onOptionsItemSelected(item)
    }

    private fun logOut() {
        FirebaseAuth.getInstance().signOut()
    }

    override fun onResume() {
        super.onResume()
        userControl()
    }

    private fun userControl() {
        var user = FirebaseAuth.getInstance().currentUser

        if (user == null){
            var intent= Intent(this@MainActivity,LoginActivity::class.java)
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            startActivity(intent)
            finish()
        }
    }

    override fun onStart() {
        super.onStart()
        FirebaseAuth.getInstance().addAuthStateListener(myAuthStateListener)
    }

    override fun onStop() {
        super.onStop()
        if (myAuthStateListener!=null)
        {
            FirebaseAuth.getInstance().removeAuthStateListener(myAuthStateListener)
        }
    }
}