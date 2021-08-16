package com.android.image.firstkotlin

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import java.util.*
import java.util.regex.Pattern
import kotlin.collections.HashMap

class SecondActivity : AppCompatActivity() {

    lateinit var name: TextInputLayout
    lateinit var email: TextInputLayout
    lateinit var password: TextInputLayout
    lateinit var confirm_pass: TextInputLayout
    lateinit var Btn_submit: Button
    lateinit var change_btn: TextView
    lateinit var welcome_text: TextView
    lateinit var name_edit:TextInputEditText
    lateinit var email_edit:TextInputEditText
    lateinit var pass_edit:TextInputEditText
    lateinit var confirm_edit:TextInputEditText
    var change: Boolean = false
    lateinit var parent_lay:ConstraintLayout
    private lateinit var auth:FirebaseAuth
    private lateinit var db:FirebaseFirestore
    lateinit var progressBar: ProgressBar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        name = findViewById(R.id.Sign_name)
        email = findViewById(R.id.Sign_email)
        password = findViewById(R.id.Password)
        confirm_pass = findViewById(R.id.Confirm_pass)
        Btn_submit = findViewById(R.id.button_sub)
        change_btn = findViewById(R.id.change_signup)
        welcome_text = findViewById(R.id.Welcome_text)
        name_edit=findViewById(R.id.name_edit)
        email_edit=findViewById(R.id.email_edit)
        pass_edit=findViewById(R.id.pass_edit)
        confirm_edit=findViewById(R.id.confirm_edit)
        parent_lay=findViewById(R.id.sign_parent)
        progressBar=findViewById(R.id.sign_progress)

        db= FirebaseFirestore.getInstance()


        auth= FirebaseAuth.getInstance()


        change_btn.setOnClickListener {



            if (!change) {
                name.visibility = View.VISIBLE
                confirm_pass.visibility = View.VISIBLE
                welcome_text.text = "Don't Have Account Sign up Here!!"
                change_btn.text="Have Account? Sign In"
                change = true
                name_edit.text?.clear()
                email_edit.text?.clear()
                pass_edit.text?.clear()
                confirm_edit.text?.clear()
                setErrorInitial()

            } else {
                setErrorInitial()
                name.visibility = View.GONE
                confirm_pass.visibility = View.GONE
                welcome_text.text = "Already Have an Account?"
                change_btn.text="New User ? Sign Up"
                change=false
            }

        }

        lisener()

        Btn_submit.setOnClickListener{
            val check=requiredChecked()
            if (check){
                progressBar.visibility= View.VISIBLE
             if (change){

                 signUpWithEmail()

             }else{

                 signInwithEmail()

             }
            }
        }




    }

    fun lisener(){

        name_edit.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(editable: Editable?) {
                if (editable.toString().length < 4) {
                    name.error = "Atleast 3 letter needed!!"
                } else {
                    name.error = null
                }

            }

        })

        email_edit.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(p0: Editable?) {
               if (change){
                   if (isValidEmailId(p0.toString())){
                       email.error=null
                   }
                   else{
                       email.error="Please provide a valid Email"
                   }
               }

            }

        })

        pass_edit.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(p0: Editable?) {
               if (change){
                   if (p0.toString().length<8){
                       password.error="Length must be atleast 8 character"
                   }
                   else{
                       password.error=null
                   }
               }

            }

        })

        confirm_edit.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(p0: Editable?) {

               if (password.error.toString().equals("null")){
                   if (p0.toString().equals(pass_edit.text.toString())){
                       confirm_pass.error=null
                   }
                   else{
                       confirm_pass.error="Password Does't Match"
                   }
               }
                else{
                   confirm_pass.error="Password Does't Match"
                }

            }

        })

    }

    private fun isValidEmailId(email: String): Boolean {
        return Pattern.compile(
            "^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
                    + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                    + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                    + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                    + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
                    + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$"
        ).matcher(email).matches()
    }

    fun setErrorInitial(){
        email.error=null
        name.error=null
        password.error=null
        confirm_pass.error=null
    }

    fun requiredChecked():Boolean{

       if (change){
           if (name_edit.text.toString().isEmpty()||email_edit.text.toString().isEmpty()||
               pass_edit.text.toString().isEmpty()){

               displaySnakbar("Fill all the field")
               return false
           }
           else{
               if (confirm_pass.error.toString().equals("null")&&name.error.toString().equals("null")&&
                   email.error.toString().equals("null")&&password.error.toString().equals("null")){
                   displaySnakbar("Successfull")
                   return true
               }
               else{
                   displaySnakbar("Fill all the field correctly")
                   return false
               }
           }
       }
        else{
           if (email_edit.text.toString().isEmpty()||
               pass_edit.text.toString().isEmpty()){

               displaySnakbar("Fill all the field")
               return false
           }
           else{
              return true
           }
       }
    }

    fun displaySnakbar(msg:String){
        val snackbar:Snackbar= Snackbar.make(this,parent_lay,msg,Snackbar.LENGTH_SHORT)
        snackbar.show()
    }

    fun signUpWithEmail(){

        auth.createUserWithEmailAndPassword(email_edit.text.toString(),pass_edit.text.toString()).addOnSuccessListener {


            val string_arr= listOf("check","check2")
            val usermap= hashMapOf(
                "name" to name_edit.text.toString(),
                "email" to email_edit.text.toString(),
                "uid" to (auth.currentUser?.uid ?: "empty"),
                "choise_topics" to string_arr
            )


            auth.currentUser?.let {
                    it1 -> db.collection("Users").document(it1.uid).set(usermap).addOnSuccessListener {

                displaySnakbar("Successfully Created Account")
                val intent:Intent= Intent(this,MainActivity::class.java)
                intent.putExtra("account","true")
                progressBar.visibility= View.GONE
                startActivity(intent)
                this.finish()

            }.addOnFailureListener{
                progressBar.visibility= View.GONE
                Log.w("permission",it.localizedMessage)
                displaySnakbar(it.localizedMessage)
            }
            }



        }.addOnFailureListener {

            progressBar.visibility= View.GONE
            displaySnakbar(it.localizedMessage)
        }

    }

    fun signInwithEmail(){
        auth.signInWithEmailAndPassword(email_edit.text.toString(),pass_edit.text.toString()).addOnSuccessListener {
            val intent:Intent= Intent(this,MainActivity::class.java)
            intent.putExtra("account","true")
            progressBar.visibility= View.GONE
            startActivity(intent)
            this.finish()

        }.addOnFailureListener{
            progressBar.visibility= View.GONE
            displaySnakbar(it.localizedMessage)
        }
    }
}