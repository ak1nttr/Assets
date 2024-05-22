package com.cse234.assets.screens

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import kotlin.math.log

class LoginViewModel(private val context : Context , private val navController: NavHostController) : ViewModel() {
    fun signUp(email: String , password: String ,firstName : String, lastName : String){
        Log.d("signUp_procedure","inside signUp")
        createUserInFirebase(email, password , firstName, lastName)

    }
    private fun createUserInFirebase(email : String , password : String , firstName : String, lastName : String){
        val auth = FirebaseAuth.getInstance()
        val db = Firebase.firestore

        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                Log.d("SIGNUP", "createUserWithEmail --->")
                Log.d("SIGNUP", "Is Successful : ${it.isSuccessful}")

                val user = FirebaseAuth.getInstance().currentUser
                val userId = user?.uid
                Log.d("SIGNUP", "User ID : $userId")

                val userInfo = hashMapOf(
                    "firstName" to firstName,
                    "lastName" to lastName,
                    "email" to email
                )


                userId?.let {
                    db.collection("users")
                        .document(it)
                        .set(userInfo)
                        .addOnSuccessListener {
                            Log.d("db_signUp","user credentials have been stored in the database")
                            Toast.makeText(context, "User Registered Successfully", Toast.LENGTH_SHORT).show()
                            navController.navigate("HomeScreen")
                        }
                        .addOnFailureListener {
                            Log.d("db_signUp","user credentials could not be stored in the database")
                            Log.d("db_signUp","error : ${it.message}")
                            Toast.makeText(context, "User have been created but could not load into database", Toast.LENGTH_SHORT).show()
                        }

                }



            }
            .addOnFailureListener {
                Log.d("SIGNUP", "createUserWithEmail:failure")
                Log.d("SIGNUP", "${it.message}")
                Toast.makeText(context, "User Registration Failed", Toast.LENGTH_SHORT).show()
            }
    }

    fun signIn(email: String , password: String){
        if (isValidEmail(email)){
            Log.d("signIn_procedure","inside signIn , email is valid")
            signInUserInFirebase(email, password)
        }else{
            Log.d("signIn_procedure","Invalid Email")
        }

    }
    private fun signInUserInFirebase(email : String , password : String){
        FirebaseAuth.getInstance()
            .signInWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                Log.d("SIGNIN", "signInWithEmail --->")
                Log.d("SIGNIN", "Is Successful : ${it.isSuccessful}")
                Toast.makeText(context, "Successful", Toast.LENGTH_SHORT).show()
                navController.navigate("HomeScreen")
            }
            .addOnFailureListener {
                Log.d("SIGNIN", "signInWithEmail:failure")
                Log.d("SIGNIN", "${it.message}")
                Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show()

            }
    }

    private fun isValidEmail(email: String): Boolean {
        val emailRegex = "^[A-Za-z](.*)([@]{1})(.{1,})(\\.)(.{1,})".toRegex()
        return email.matches(emailRegex)
    }
}